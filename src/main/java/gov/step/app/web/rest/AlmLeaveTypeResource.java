package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmLeaveType;
import gov.step.app.repository.AlmLeaveTypeRepository;
import gov.step.app.repository.search.AlmLeaveTypeSearchRepository;
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
 * REST controller for managing AlmLeaveType.
 */
@RestController
@RequestMapping("/api")
public class AlmLeaveTypeResource {

    private final Logger log = LoggerFactory.getLogger(AlmLeaveTypeResource.class);

    @Inject
    private AlmLeaveTypeRepository almLeaveTypeRepository;

    @Inject
    private AlmLeaveTypeSearchRepository almLeaveTypeSearchRepository;

    /**
     * POST  /almLeaveTypes -> Create a new almLeaveType.
     */
    @RequestMapping(value = "/almLeaveTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeaveType> createAlmLeaveType(@Valid @RequestBody AlmLeaveType almLeaveType) throws URISyntaxException {
        log.debug("REST request to save AlmLeaveType : {}", almLeaveType);
        if (almLeaveType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almLeaveType cannot already have an ID").body(null);
        }
        AlmLeaveType result = almLeaveTypeRepository.save(almLeaveType);
        almLeaveTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almLeaveTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almLeaveType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almLeaveTypes -> Updates an existing almLeaveType.
     */
    @RequestMapping(value = "/almLeaveTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeaveType> updateAlmLeaveType(@Valid @RequestBody AlmLeaveType almLeaveType) throws URISyntaxException {
        log.debug("REST request to update AlmLeaveType : {}", almLeaveType);
        if (almLeaveType.getId() == null) {
            return createAlmLeaveType(almLeaveType);
        }
        AlmLeaveType result = almLeaveTypeRepository.save(almLeaveType);
        almLeaveTypeSearchRepository.save(almLeaveType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almLeaveType", almLeaveType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almLeaveTypes -> get all the almLeaveTypes.
     */
    @RequestMapping(value = "/almLeaveTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmLeaveType>> getAllAlmLeaveTypes(Pageable pageable)
        throws URISyntaxException {
        Page<AlmLeaveType> page = almLeaveTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almLeaveTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almLeaveTypes/:id -> get the "id" almLeaveType.
     */
    @RequestMapping(value = "/almLeaveTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeaveType> getAlmLeaveType(@PathVariable Long id) {
        log.debug("REST request to get AlmLeaveType : {}", id);
        return Optional.ofNullable(almLeaveTypeRepository.findOne(id))
            .map(almLeaveType -> new ResponseEntity<>(
                almLeaveType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almLeaveTypes/:id -> delete the "id" almLeaveType.
     */
    @RequestMapping(value = "/almLeaveTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmLeaveType(@PathVariable Long id) {
        log.debug("REST request to delete AlmLeaveType : {}", id);
        almLeaveTypeRepository.delete(id);
        almLeaveTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almLeaveType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almLeaveTypes/:query -> search for the almLeaveType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almLeaveTypes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmLeaveType> searchAlmLeaveTypes(@PathVariable String query) {
        return StreamSupport
            .stream(almLeaveTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/almLeaveTypes/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeName(@RequestParam String value)
    {
        Optional<AlmLeaveType> almLeaveType = almLeaveTypeRepository.findOneByLeaveTypeName(value.toLowerCase());
        log.debug("almLeaveTypes by name: "+value+", Stat: "+Optional.empty().equals(almLeaveType));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(almLeaveType))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
