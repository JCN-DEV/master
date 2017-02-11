package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlEmpReg;
import gov.step.app.repository.DlEmpRegRepository;
import gov.step.app.repository.search.DlEmpRegSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DlEmpReg.
 */
@RestController
@RequestMapping("/api")
public class DlEmpRegResource {

    private final Logger log = LoggerFactory.getLogger(DlEmpRegResource.class);

    @Inject
    private DlEmpRegRepository dlEmpRegRepository;

    @Inject
    private DlEmpRegSearchRepository dlEmpRegSearchRepository;

    /**
     * POST  /dlEmpRegs -> Create a new dlEmpReg.
     */
    @RequestMapping(value = "/dlEmpRegs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlEmpReg> createDlEmpReg(@Valid @RequestBody DlEmpReg dlEmpReg) throws URISyntaxException {
        log.debug("REST request to save DlEmpReg : {}", dlEmpReg);
        if (dlEmpReg.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlEmpReg cannot already have an ID").body(null);
        }
        DlEmpReg result = dlEmpRegRepository.save(dlEmpReg);
        dlEmpRegSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlEmpRegs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlEmpReg", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlEmpRegs -> Updates an existing dlEmpReg.
     */
    @RequestMapping(value = "/dlEmpRegs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlEmpReg> updateDlEmpReg(@Valid @RequestBody DlEmpReg dlEmpReg) throws URISyntaxException {
        log.debug("REST request to update DlEmpReg : {}", dlEmpReg);
        if (dlEmpReg.getId() == null) {
            return createDlEmpReg(dlEmpReg);
        }
        DlEmpReg result = dlEmpRegRepository.save(dlEmpReg);
        dlEmpRegSearchRepository.save(dlEmpReg);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlEmpReg", dlEmpReg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlEmpRegs -> get all the dlEmpRegs.
     */
    @RequestMapping(value = "/dlEmpRegs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlEmpReg>> getAllDlEmpRegs(Pageable pageable)
        throws URISyntaxException {
        Page<DlEmpReg> page = dlEmpRegRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlEmpRegs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlEmpRegs/:id -> get the "id" dlEmpReg.
     */
    @RequestMapping(value = "/dlEmpRegs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlEmpReg> getDlEmpReg(@PathVariable Long id) {
        log.debug("REST request to get DlEmpReg : {}", id);
        return Optional.ofNullable(dlEmpRegRepository.findOne(id))
            .map(dlEmpReg -> new ResponseEntity<>(
                dlEmpReg,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlEmpRegs/:id -> delete the "id" dlEmpReg.
     */
    @RequestMapping(value = "/dlEmpRegs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlEmpReg(@PathVariable Long id) {
        log.debug("REST request to delete DlEmpReg : {}", id);
        dlEmpRegRepository.delete(id);
        dlEmpRegSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlEmpReg", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlEmpRegs/:query -> search for the dlEmpReg corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlEmpRegs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlEmpReg> searchDlEmpRegs(@PathVariable String query) {
        return StreamSupport
            .stream(dlEmpRegSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
