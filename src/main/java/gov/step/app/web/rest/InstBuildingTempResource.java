package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstBuildingTemp;
import gov.step.app.domain.Institute;
import gov.step.app.domain.InstituteStatus;
import gov.step.app.repository.InstBuildingTempRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.InstituteStatusRepository;
import gov.step.app.repository.search.InstBuildingTempSearchRepository;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstBuildingTemp.
 */
@RestController
@RequestMapping("/api")
public class InstBuildingTempResource {

    private final Logger log = LoggerFactory.getLogger(InstBuildingTempResource.class);

    @Inject
    private InstBuildingTempRepository instBuildingTempRepository;

    @Inject
    private InstBuildingTempSearchRepository instBuildingTempSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    /**
     * POST  /instBuildingTemps -> Create a new instBuildingTemp.
     */
    @RequestMapping(value = "/instBuildingTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstBuildingTemp> createInstBuildingTemp(@Valid @RequestBody InstBuildingTemp instBuildingTemp) throws URISyntaxException {
        log.debug("REST request to save InstBuildingTemp : {}", instBuildingTemp);
        if (instBuildingTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instBuildingTemp cannot already have an ID").body(null);
        }

        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null) {
            instituteStatus.setBuilding(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }
        InstBuildingTemp result = instBuildingTempRepository.save(instBuildingTemp);
        instBuildingTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instBuildingTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instBuildingTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instBuildingTemps -> Updates an existing instBuildingTemp.
     */
    @RequestMapping(value = "/instBuildingTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstBuildingTemp> updateInstBuildingTemp(@Valid @RequestBody InstBuildingTemp instBuildingTemp) throws URISyntaxException {
        log.debug("REST request to update InstBuildingTemp : {}", instBuildingTemp);
        if (instBuildingTemp.getId() == null) {
            return createInstBuildingTemp(instBuildingTemp);
        }
        InstBuildingTemp result = instBuildingTempRepository.save(instBuildingTemp);
        instBuildingTempSearchRepository.save(instBuildingTemp);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null) {
            instituteStatus.setAdmInfo(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instBuildingTemp", instBuildingTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instBuildingTemps -> get all the instBuildingTemps.
     */
    @RequestMapping(value = "/instBuildingTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstBuildingTemp>> getAllInstBuildingTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstBuildingTemp> page = instBuildingTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instBuildingTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instBuildingTemps/:id -> get the "id" instBuildingTemp.
     */
    @RequestMapping(value = "/instBuildingTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstBuildingTemp> getInstBuildingTemp(@PathVariable Long id) {
        log.debug("REST request to get InstBuildingTemp : {}", id);
        return Optional.ofNullable(instBuildingTempRepository.findOne(id))
            .map(instBuildingTemp -> new ResponseEntity<>(
                instBuildingTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instBuildingTemps/:id -> delete the "id" instBuildingTemp.
     */
    @RequestMapping(value = "/instBuildingTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstBuildingTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstBuildingTemp : {}", id);
        instBuildingTempRepository.delete(id);
        instBuildingTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instBuildingTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instBuildingTemps/:query -> search for the instBuildingTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instBuildingTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstBuildingTemp> searchInstBuildingTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instBuildingTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
