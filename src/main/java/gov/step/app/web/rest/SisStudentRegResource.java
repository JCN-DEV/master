package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SisStudentReg;
import gov.step.app.repository.SisStudentRegRepository;
import gov.step.app.repository.search.SisStudentRegSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SisStudentReg.
 */
@RestController
@RequestMapping("/api")
public class SisStudentRegResource {

    private final Logger log = LoggerFactory.getLogger(SisStudentRegResource.class);

    @Inject
    private SisStudentRegRepository sisStudentRegRepository;

    @Inject
    private SisStudentRegSearchRepository sisStudentRegSearchRepository;

    /**
     * POST  /sisStudentRegs -> Create a new sisStudentReg.
     */
    @RequestMapping(value = "/sisStudentRegs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentReg> createSisStudentReg(@RequestBody SisStudentReg sisStudentReg) throws URISyntaxException {
        log.debug("REST request to save SisStudentReg : {}", sisStudentReg);
        if (sisStudentReg.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new sisStudentReg cannot already have an ID").body(null);
        }
        SisStudentReg result = sisStudentRegRepository.save(sisStudentReg);
        sisStudentRegSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/sisStudentRegs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sisStudentReg", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sisStudentRegs -> Updates an existing sisStudentReg.
     */
    @RequestMapping(value = "/sisStudentRegs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentReg> updateSisStudentReg(@RequestBody SisStudentReg sisStudentReg) throws URISyntaxException {
        log.debug("REST request to update SisStudentReg : {}", sisStudentReg);
        if (sisStudentReg.getId() == null) {
            return createSisStudentReg(sisStudentReg);
        }
        SisStudentReg result = sisStudentRegRepository.save(sisStudentReg);
        sisStudentRegSearchRepository.save(sisStudentReg);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sisStudentReg", sisStudentReg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sisStudentRegs -> get all the sisStudentRegs.
     */
    @RequestMapping(value = "/sisStudentRegs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SisStudentReg>> getAllSisStudentRegs(Pageable pageable)
        throws URISyntaxException {
        Page<SisStudentReg> page = sisStudentRegRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sisStudentRegs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sisStudentRegs/:id -> get the "id" sisStudentReg.
     */
    @RequestMapping(value = "/sisStudentRegs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentReg> getSisStudentReg(@PathVariable Long id) {
        log.debug("REST request to get SisStudentReg : {}", id);
        return Optional.ofNullable(sisStudentRegRepository.findOne(id))
            .map(sisStudentReg -> new ResponseEntity<>(
                sisStudentReg,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /sisStudentRegs/appid/:applicationId -> get the "applicationId" sisStudentReg.
     */
    @RequestMapping(value = "/sisStudentRegs/appid/{applicationId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentReg> getSisStudentRegByAppId(@PathVariable String applicationId) {
        log.debug("REST request to get SisStudentReg : {}", applicationId);
        SisStudentReg sisStudentRegs  = sisStudentRegRepository.findAllRegByAppId(Long.parseLong(applicationId));
        return Optional.ofNullable(sisStudentRegRepository.findOne(sisStudentRegs.getId()))
            .map(sisStudentReg -> new ResponseEntity<>(
                sisStudentReg,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sisStudentRegs/:id -> delete the "id" sisStudentReg.
     */
    @RequestMapping(value = "/sisStudentRegs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSisStudentReg(@PathVariable Long id) {
        log.debug("REST request to delete SisStudentReg : {}", id);
        sisStudentRegRepository.delete(id);
        sisStudentRegSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sisStudentReg", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sisStudentRegs/:query -> search for the sisStudentReg corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/sisStudentRegs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SisStudentReg> searchSisStudentRegs(@PathVariable String query) {
        return StreamSupport
            .stream(sisStudentRegSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
