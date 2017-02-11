package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstLandTemp;
import gov.step.app.domain.Institute;
import gov.step.app.domain.InstituteStatus;
import gov.step.app.repository.InstLandTempRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.InstituteStatusRepository;
import gov.step.app.repository.search.InstLandTempSearchRepository;
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
 * REST controller for managing InstLandTemp.
 */
@RestController
@RequestMapping("/api")
public class InstLandTempResource {

    private final Logger log = LoggerFactory.getLogger(InstLandTempResource.class);

    @Inject
    private InstLandTempRepository instLandTempRepository;

    @Inject
    private InstLandTempSearchRepository instLandTempSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    /**
     * POST  /instLandTemps -> Create a new instLandTemp.
     */
    @RequestMapping(value = "/instLandTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLandTemp> createInstLandTemp(@Valid @RequestBody InstLandTemp instLandTemp) throws URISyntaxException {
        log.debug("REST request to save InstLandTemp : {}", instLandTemp);
        if (instLandTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instLandTemp cannot already have an ID").body(null);
        }

        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setLand(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }


        InstLandTemp result = instLandTempRepository.save(instLandTemp);
        instLandTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instLandTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instLandTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instLandTemps -> Updates an existing instLandTemp.
     */
    @RequestMapping(value = "/instLandTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLandTemp> updateInstLandTemp(@Valid @RequestBody InstLandTemp instLandTemp) throws URISyntaxException {
        log.debug("REST request to update InstLandTemp : {}", instLandTemp);
        if (instLandTemp.getId() == null) {
            return createInstLandTemp(instLandTemp);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setLand(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }


        InstLandTemp result = instLandTempRepository.save(instLandTemp);
        instLandTempSearchRepository.save(instLandTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instLandTemp", instLandTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instLandTemps -> get all the instLandTemps.
     */
    @RequestMapping(value = "/instLandTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstLandTemp>> getAllInstLandTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstLandTemp> page = instLandTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instLandTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instLandTemps/:id -> get the "id" instLandTemp.
     */
    @RequestMapping(value = "/instLandTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLandTemp> getInstLandTemp(@PathVariable Long id) {
        log.debug("REST request to get InstLandTemp : {}", id);
        return Optional.ofNullable(instLandTempRepository.findOne(id))
            .map(instLandTemp -> new ResponseEntity<>(
                instLandTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instLandTemps/:id -> delete the "id" instLandTemp.
     */
    @RequestMapping(value = "/instLandTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstLandTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstLandTemp : {}", id);
        instLandTempRepository.delete(id);
        instLandTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instLandTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instLandTemps/:query -> search for the instLandTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instLandTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstLandTemp> searchInstLandTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instLandTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
