package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmWorkingUnit;
import gov.step.app.repository.AlmWorkingUnitRepository;
import gov.step.app.repository.search.AlmWorkingUnitSearchRepository;
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
 * REST controller for managing AlmWorkingUnit.
 */
@RestController
@RequestMapping("/api")
public class AlmWorkingUnitResource {

    private final Logger log = LoggerFactory.getLogger(AlmWorkingUnitResource.class);

    @Inject
    private AlmWorkingUnitRepository almWorkingUnitRepository;

    @Inject
    private AlmWorkingUnitSearchRepository almWorkingUnitSearchRepository;

    /**
     * POST  /almWorkingUnits -> Create a new almWorkingUnit.
     */
    @RequestMapping(value = "/almWorkingUnits",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmWorkingUnit> createAlmWorkingUnit(@Valid @RequestBody AlmWorkingUnit almWorkingUnit) throws URISyntaxException {
        log.debug("REST request to save AlmWorkingUnit : {}", almWorkingUnit);
        if (almWorkingUnit.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almWorkingUnit cannot already have an ID").body(null);
        }
        AlmWorkingUnit result = almWorkingUnitRepository.save(almWorkingUnit);
        almWorkingUnitSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almWorkingUnits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almWorkingUnit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almWorkingUnits -> Updates an existing almWorkingUnit.
     */
    @RequestMapping(value = "/almWorkingUnits",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmWorkingUnit> updateAlmWorkingUnit(@Valid @RequestBody AlmWorkingUnit almWorkingUnit) throws URISyntaxException {
        log.debug("REST request to update AlmWorkingUnit : {}", almWorkingUnit);
        if (almWorkingUnit.getId() == null) {
            return createAlmWorkingUnit(almWorkingUnit);
        }
        AlmWorkingUnit result = almWorkingUnitRepository.save(almWorkingUnit);
        almWorkingUnitSearchRepository.save(almWorkingUnit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almWorkingUnit", almWorkingUnit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almWorkingUnits -> get all the almWorkingUnits.
     */
    @RequestMapping(value = "/almWorkingUnits",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmWorkingUnit>> getAllAlmWorkingUnits(Pageable pageable)
        throws URISyntaxException {
        Page<AlmWorkingUnit> page = almWorkingUnitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almWorkingUnits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almWorkingUnits/:id -> get the "id" almWorkingUnit.
     */
    @RequestMapping(value = "/almWorkingUnits/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmWorkingUnit> getAlmWorkingUnit(@PathVariable Long id) {
        log.debug("REST request to get AlmWorkingUnit : {}", id);
        return Optional.ofNullable(almWorkingUnitRepository.findOne(id))
            .map(almWorkingUnit -> new ResponseEntity<>(
                almWorkingUnit,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almWorkingUnits/:id -> delete the "id" almWorkingUnit.
     */
    @RequestMapping(value = "/almWorkingUnits/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmWorkingUnit(@PathVariable Long id) {
        log.debug("REST request to delete AlmWorkingUnit : {}", id);
        almWorkingUnitRepository.delete(id);
        almWorkingUnitSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almWorkingUnit", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almWorkingUnits/:query -> search for the almWorkingUnit corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almWorkingUnits/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmWorkingUnit> searchAlmWorkingUnits(@PathVariable String query) {
        return StreamSupport
            .stream(almWorkingUnitSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
