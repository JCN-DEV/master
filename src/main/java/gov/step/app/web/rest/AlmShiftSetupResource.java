package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmShiftSetup;
import gov.step.app.repository.AlmShiftSetupRepository;
import gov.step.app.repository.search.AlmShiftSetupSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing AlmShiftSetup.
 */
@RestController
@RequestMapping("/api")
public class AlmShiftSetupResource {

    private final Logger log = LoggerFactory.getLogger(AlmShiftSetupResource.class);

    @Inject
    private AlmShiftSetupRepository almShiftSetupRepository;

    @Inject
    private AlmShiftSetupSearchRepository almShiftSetupSearchRepository;

    /**
     * POST  /almShiftSetups -> Create a new almShiftSetup.
     */
    @RequestMapping(value = "/almShiftSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmShiftSetup> createAlmShiftSetup(@Valid @RequestBody AlmShiftSetup almShiftSetup) throws URISyntaxException {
        log.debug("REST request to save AlmShiftSetup : {}", almShiftSetup);
        if (almShiftSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almShiftSetup cannot already have an ID").body(null);
        }
        AlmShiftSetup result = almShiftSetupRepository.save(almShiftSetup);
        almShiftSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almShiftSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almShiftSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almShiftSetups -> Updates an existing almShiftSetup.
     */
    @RequestMapping(value = "/almShiftSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmShiftSetup> updateAlmShiftSetup(@Valid @RequestBody AlmShiftSetup almShiftSetup) throws URISyntaxException {
        log.debug("REST request to update AlmShiftSetup : {}", almShiftSetup);
        if (almShiftSetup.getId() == null) {
            return createAlmShiftSetup(almShiftSetup);
        }
        AlmShiftSetup result = almShiftSetupRepository.save(almShiftSetup);
        almShiftSetupSearchRepository.save(almShiftSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almShiftSetup", almShiftSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almShiftSetups -> get all the almShiftSetups.
     */
    @RequestMapping(value = "/almShiftSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmShiftSetup>> getAllAlmShiftSetups(Pageable pageable)
        throws URISyntaxException {
        Page<AlmShiftSetup> page = almShiftSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almShiftSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almShiftSetups/:id -> get the "id" almShiftSetup.
     */
    @RequestMapping(value = "/almShiftSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmShiftSetup> getAlmShiftSetup(@PathVariable Long id) {
        log.debug("REST request to get AlmShiftSetup : {}", id);
        return Optional.ofNullable(almShiftSetupRepository.findOne(id))
            .map(almShiftSetup -> new ResponseEntity<>(
                almShiftSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almShiftSetups/:id -> delete the "id" almShiftSetup.
     */
    @RequestMapping(value = "/almShiftSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmShiftSetup(@PathVariable Long id) {
        log.debug("REST request to delete AlmShiftSetup : {}", id);
        almShiftSetupRepository.delete(id);
        almShiftSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almShiftSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almShiftSetups/:query -> search for the almShiftSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almShiftSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmShiftSetup> searchAlmShiftSetups(@PathVariable String query) {
        return StreamSupport
            .stream(almShiftSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/almShiftSetups/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeName(@RequestParam String value)
    {
        Optional<AlmShiftSetup> almShiftSetup = almShiftSetupRepository.findOneByShiftName(value.toLowerCase());
        log.debug("almShiftSetups by name: "+value+", Stat: "+Optional.empty().equals(almShiftSetup));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(almShiftSetup))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
