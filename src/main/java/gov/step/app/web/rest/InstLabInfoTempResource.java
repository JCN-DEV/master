package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstLabInfoTemp;
import gov.step.app.domain.Institute;
import gov.step.app.domain.InstituteStatus;
import gov.step.app.repository.InstLabInfoTempRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.InstituteStatusRepository;
import gov.step.app.repository.search.InstLabInfoTempSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstLabInfoTemp.
 */
@RestController
@RequestMapping("/api")
public class InstLabInfoTempResource {

    private final Logger log = LoggerFactory.getLogger(InstLabInfoTempResource.class);

    @Inject
    private InstLabInfoTempRepository instLabInfoTempRepository;

    @Inject
    private InstLabInfoTempSearchRepository instLabInfoTempSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    /**
     * POST  /instLabInfoTemps -> Create a new instLabInfoTemp.
     */
    @RequestMapping(value = "/instLabInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLabInfoTemp> createInstLabInfoTemp(@RequestBody InstLabInfoTemp instLabInfoTemp) throws URISyntaxException {
        log.debug("REST request to save InstLabInfoTemp : {}", instLabInfoTemp);
        if (instLabInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instLabInfoTemp cannot already have an ID").body(null);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setLabInfo(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }


        InstLabInfoTemp result = instLabInfoTempRepository.save(instLabInfoTemp);
        instLabInfoTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instLabInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instLabInfoTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instLabInfoTemps -> Updates an existing instLabInfoTemp.
     */
    @RequestMapping(value = "/instLabInfoTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLabInfoTemp> updateInstLabInfoTemp(@RequestBody InstLabInfoTemp instLabInfoTemp) throws URISyntaxException {
        log.debug("REST request to update InstLabInfoTemp : {}", instLabInfoTemp);
        if (instLabInfoTemp.getId() == null) {
            return createInstLabInfoTemp(instLabInfoTemp);
        }

        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setLabInfo(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }


        InstLabInfoTemp result = instLabInfoTempRepository.save(instLabInfoTemp);
        instLabInfoTempSearchRepository.save(instLabInfoTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instLabInfoTemp", instLabInfoTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instLabInfoTemps -> get all the instLabInfoTemps.
     */
    @RequestMapping(value = "/instLabInfoTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstLabInfoTemp>> getAllInstLabInfoTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstLabInfoTemp> page = instLabInfoTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instLabInfoTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instLabInfoTemps/:id -> get the "id" instLabInfoTemp.
     */
    @RequestMapping(value = "/instLabInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLabInfoTemp> getInstLabInfoTemp(@PathVariable Long id) {
        log.debug("REST request to get InstLabInfoTemp : {}", id);
        return Optional.ofNullable(instLabInfoTempRepository.findOne(id))
            .map(instLabInfoTemp -> new ResponseEntity<>(
                instLabInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instLabInfoTemps/:id -> delete the "id" instLabInfoTemp.
     */
    @RequestMapping(value = "/instLabInfoTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstLabInfoTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstLabInfoTemp : {}", id);
        instLabInfoTempRepository.delete(id);
        instLabInfoTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instLabInfoTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instLabInfoTemps/:query -> search for the instLabInfoTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instLabInfoTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstLabInfoTemp> searchInstLabInfoTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instLabInfoTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
