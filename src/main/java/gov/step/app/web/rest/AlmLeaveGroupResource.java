package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmLeaveGroup;
import gov.step.app.repository.AlmLeaveGroupRepository;
import gov.step.app.repository.search.AlmLeaveGroupSearchRepository;
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
 * REST controller for managing AlmLeaveGroup.
 */
@RestController
@RequestMapping("/api")
public class AlmLeaveGroupResource {

    private final Logger log = LoggerFactory.getLogger(AlmLeaveGroupResource.class);

    @Inject
    private AlmLeaveGroupRepository almLeaveGroupRepository;

    @Inject
    private AlmLeaveGroupSearchRepository almLeaveGroupSearchRepository;

    /**
     * POST  /almLeaveGroups -> Create a new almLeaveGroup.
     */
    @RequestMapping(value = "/almLeaveGroups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeaveGroup> createAlmLeaveGroup(@Valid @RequestBody AlmLeaveGroup almLeaveGroup) throws URISyntaxException {
        log.debug("REST request to save AlmLeaveGroup : {}", almLeaveGroup);
        if (almLeaveGroup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almLeaveGroup cannot already have an ID").body(null);
        }
        AlmLeaveGroup result = almLeaveGroupRepository.save(almLeaveGroup);
        almLeaveGroupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almLeaveGroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almLeaveGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almLeaveGroups -> Updates an existing almLeaveGroup.
     */
    @RequestMapping(value = "/almLeaveGroups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeaveGroup> updateAlmLeaveGroup(@Valid @RequestBody AlmLeaveGroup almLeaveGroup) throws URISyntaxException {
        log.debug("REST request to update AlmLeaveGroup : {}", almLeaveGroup);
        if (almLeaveGroup.getId() == null) {
            return createAlmLeaveGroup(almLeaveGroup);
        }
        AlmLeaveGroup result = almLeaveGroupRepository.save(almLeaveGroup);
        almLeaveGroupSearchRepository.save(almLeaveGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almLeaveGroup", almLeaveGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almLeaveGroups -> get all the almLeaveGroups.
     */
    @RequestMapping(value = "/almLeaveGroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmLeaveGroup>> getAllAlmLeaveGroups(Pageable pageable)
        throws URISyntaxException {
        Page<AlmLeaveGroup> page = almLeaveGroupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almLeaveGroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almLeaveGroups/:id -> get the "id" almLeaveGroup.
     */
    @RequestMapping(value = "/almLeaveGroups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeaveGroup> getAlmLeaveGroup(@PathVariable Long id) {
        log.debug("REST request to get AlmLeaveGroup : {}", id);
        return Optional.ofNullable(almLeaveGroupRepository.findOne(id))
            .map(almLeaveGroup -> new ResponseEntity<>(
                almLeaveGroup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almLeaveGroups/:id -> delete the "id" almLeaveGroup.
     */
    @RequestMapping(value = "/almLeaveGroups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmLeaveGroup(@PathVariable Long id) {
        log.debug("REST request to delete AlmLeaveGroup : {}", id);
        almLeaveGroupRepository.delete(id);
        almLeaveGroupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almLeaveGroup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almLeaveGroups/:query -> search for the almLeaveGroup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almLeaveGroups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmLeaveGroup> searchAlmLeaveGroups(@PathVariable String query) {
        return StreamSupport
            .stream(almLeaveGroupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/almLeaveGroups/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeName(@RequestParam String value)
    {
        Optional<AlmLeaveGroup> almLeaveGroup = almLeaveGroupRepository.findOneByLeaveGroupName(value.toLowerCase());
        log.debug("almLeaveGroups by name: "+value+", Stat: "+Optional.empty().equals(almLeaveGroup));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(almLeaveGroup))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
