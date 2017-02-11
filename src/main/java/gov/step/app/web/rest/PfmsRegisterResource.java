package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PfmsRegister;
import gov.step.app.repository.PfmsRegisterRepository;
import gov.step.app.repository.search.PfmsRegisterSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing PfmsRegister.
 */
@RestController
@RequestMapping("/api")
public class PfmsRegisterResource {

    private final Logger log = LoggerFactory.getLogger(PfmsRegisterResource.class);

    @Inject
    private PfmsRegisterRepository pfmsRegisterRepository;

    @Inject
    private PfmsRegisterSearchRepository pfmsRegisterSearchRepository;

    /**
     * POST  /pfmsRegisters -> Create a new pfmsRegister.
     */
    @RequestMapping(value = "/pfmsRegisters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsRegister> createPfmsRegister(@Valid @RequestBody PfmsRegister pfmsRegister) throws URISyntaxException {
        log.debug("REST request to save PfmsRegister : {}", pfmsRegister);
        if (pfmsRegister.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pfmsRegister cannot already have an ID").body(null);
        }
        PfmsRegister result = pfmsRegisterRepository.save(pfmsRegister);
        pfmsRegisterSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pfmsRegisters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pfmsRegister", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pfmsRegisters -> Updates an existing pfmsRegister.
     */
    @RequestMapping(value = "/pfmsRegisters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsRegister> updatePfmsRegister(@Valid @RequestBody PfmsRegister pfmsRegister) throws URISyntaxException {
        log.debug("REST request to update PfmsRegister : {}", pfmsRegister);
        if (pfmsRegister.getId() == null) {
            return createPfmsRegister(pfmsRegister);
        }
        PfmsRegister result = pfmsRegisterRepository.save(pfmsRegister);
        pfmsRegisterSearchRepository.save(pfmsRegister);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pfmsRegister", pfmsRegister.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pfmsRegisters -> get all the pfmsRegisters.
     */
    @RequestMapping(value = "/pfmsRegisters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PfmsRegister>> getAllPfmsRegisters(Pageable pageable)
        throws URISyntaxException {
        Page<PfmsRegister> page = pfmsRegisterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pfmsRegisters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pfmsRegisters/:id -> get the "id" pfmsRegister.
     */
    @RequestMapping(value = "/pfmsRegisters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsRegister> getPfmsRegister(@PathVariable Long id) {
        log.debug("REST request to get PfmsRegister : {}", id);
        return Optional.ofNullable(pfmsRegisterRepository.findOne(id))
            .map(pfmsRegister -> new ResponseEntity<>(
                pfmsRegister,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pfmsRegisters/:id -> delete the "id" pfmsRegister.
     */
    @RequestMapping(value = "/pfmsRegisters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePfmsRegister(@PathVariable Long id) {
        log.debug("REST request to delete PfmsRegister : {}", id);
        pfmsRegisterRepository.delete(id);
        pfmsRegisterSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pfmsRegister", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pfmsRegisters/:query -> search for the pfmsRegister corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pfmsRegisters/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsRegister> searchPfmsRegisters(@PathVariable String query) {
        return StreamSupport
            .stream(pfmsRegisterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /pfmsRegisterListByEmployeeByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsRegisterListByEmployee/{employeeInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsRegister> getPfmsRegisterListByEmployee(@PathVariable long employeeInfoId) throws Exception {
        log.debug("REST request to pfmsRegisterByEmployee List : employeeInfo: {} ", employeeInfoId);
        return pfmsRegisterRepository.getPfmsRegisterListByEmployee(employeeInfoId);

    }
}
