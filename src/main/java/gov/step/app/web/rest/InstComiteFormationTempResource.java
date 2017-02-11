package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstComiteFormationTemp;
import gov.step.app.domain.InstituteStatus;
import gov.step.app.repository.InstComiteFormationTempRepository;
import gov.step.app.repository.InstituteStatusRepository;
import gov.step.app.repository.search.InstComiteFormationTempSearchRepository;
import gov.step.app.security.SecurityUtils;
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
 * REST controller for managing InstComiteFormationTemp.
 */
@RestController
@RequestMapping("/api")
public class InstComiteFormationTempResource {

    private final Logger log = LoggerFactory.getLogger(InstComiteFormationTempResource.class);

    @Inject
    private InstComiteFormationTempRepository instComiteFormationTempRepository;

    @Inject
    private InstComiteFormationTempSearchRepository instComiteFormationTempSearchRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    /**
     * POST  /instComiteFormationTemps -> Create a new instComiteFormationTemp.
     */
    @RequestMapping(value = "/instComiteFormationTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstComiteFormationTemp> createInstComiteFormationTemp(@RequestBody InstComiteFormationTemp instComiteFormationTemp) throws URISyntaxException {
        log.debug("REST request to save InstComiteFormationTemp : {}", instComiteFormationTemp);
        if (instComiteFormationTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instComiteFormationTemp cannot already have an ID").body(null);
        }
        InstComiteFormationTemp result = instComiteFormationTempRepository.save(instComiteFormationTemp);
        instComiteFormationTempSearchRepository.save(result);

        if(SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")){
            InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
            if(instituteStatus != null) {
                instituteStatus.setComiteeMember(1);
                instituteStatus.setUpdatedDate(LocalDate.now());
                instituteStatusRepository.save(instituteStatus);
            }
        }

        return ResponseEntity.created(new URI("/api/instComiteFormationTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instComiteFormationTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instComiteFormationTemps -> Updates an existing instComiteFormationTemp.
     */
    @RequestMapping(value = "/instComiteFormationTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstComiteFormationTemp> updateInstComiteFormationTemp(@RequestBody InstComiteFormationTemp instComiteFormationTemp) throws URISyntaxException {
        log.debug("REST request to update InstComiteFormationTemp : {}", instComiteFormationTemp);
        if (instComiteFormationTemp.getId() == null) {
            return createInstComiteFormationTemp(instComiteFormationTemp);
        }
        InstComiteFormationTemp result = instComiteFormationTempRepository.save(instComiteFormationTemp);
        instComiteFormationTempSearchRepository.save(instComiteFormationTemp);
        if(SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")){
            InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
            if(instituteStatus != null) {
                instituteStatus.setComiteeMember(1);
                instituteStatus.setUpdatedDate(LocalDate.now());
                instituteStatusRepository.save(instituteStatus);
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instComiteFormationTemp", instComiteFormationTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instComiteFormationTemps -> get all the instComiteFormationTemps.
     */
    @RequestMapping(value = "/instComiteFormationTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstComiteFormationTemp>> getAllInstComiteFormationTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstComiteFormationTemp> page = instComiteFormationTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instComiteFormationTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instComiteFormationTemps/:id -> get the "id" instComiteFormationTemp.
     */
    @RequestMapping(value = "/instComiteFormationTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstComiteFormationTemp> getInstComiteFormationTemp(@PathVariable Long id) {
        log.debug("REST request to get InstComiteFormationTemp : {}", id);
        return Optional.ofNullable(instComiteFormationTempRepository.findOne(id))
            .map(instComiteFormationTemp -> new ResponseEntity<>(
                instComiteFormationTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instComiteFormationTemps/:id -> delete the "id" instComiteFormationTemp.
     */
    @RequestMapping(value = "/instComiteFormationTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstComiteFormationTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstComiteFormationTemp : {}", id);
        instComiteFormationTempRepository.delete(id);
        instComiteFormationTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instComiteFormationTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instComiteFormationTemps/:query -> search for the instComiteFormationTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instComiteFormationTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstComiteFormationTemp> searchInstComiteFormationTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instComiteFormationTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
