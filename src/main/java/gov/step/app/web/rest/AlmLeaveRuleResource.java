package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmLeaveRule;
import gov.step.app.repository.AlmLeaveRuleRepository;
import gov.step.app.repository.search.AlmLeaveRuleSearchRepository;
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
 * REST controller for managing AlmLeaveRule.
 */
@RestController
@RequestMapping("/api")
public class AlmLeaveRuleResource {

    private final Logger log = LoggerFactory.getLogger(AlmLeaveRuleResource.class);

    @Inject
    private AlmLeaveRuleRepository almLeaveRuleRepository;

    @Inject
    private AlmLeaveRuleSearchRepository almLeaveRuleSearchRepository;

    /**
     * POST  /almLeaveRules -> Create a new almLeaveRule.
     */
    @RequestMapping(value = "/almLeaveRules",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeaveRule> createAlmLeaveRule(@Valid @RequestBody AlmLeaveRule almLeaveRule) throws URISyntaxException {
        log.debug("REST request to save AlmLeaveRule : {}", almLeaveRule);
        if (almLeaveRule.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almLeaveRule cannot already have an ID").body(null);
        }
        AlmLeaveRule result = almLeaveRuleRepository.save(almLeaveRule);
        almLeaveRuleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almLeaveRules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almLeaveRule", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almLeaveRules -> Updates an existing almLeaveRule.
     */
    @RequestMapping(value = "/almLeaveRules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeaveRule> updateAlmLeaveRule(@Valid @RequestBody AlmLeaveRule almLeaveRule) throws URISyntaxException {
        log.debug("REST request to update AlmLeaveRule : {}", almLeaveRule);
        if (almLeaveRule.getId() == null) {
            return createAlmLeaveRule(almLeaveRule);
        }
        AlmLeaveRule result = almLeaveRuleRepository.save(almLeaveRule);
        almLeaveRuleSearchRepository.save(almLeaveRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almLeaveRule", almLeaveRule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almLeaveRules -> get all the almLeaveRules.
     */
    @RequestMapping(value = "/almLeaveRules",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmLeaveRule>> getAllAlmLeaveRules(Pageable pageable)
        throws URISyntaxException {
        Page<AlmLeaveRule> page = almLeaveRuleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almLeaveRules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almLeaveRules/:id -> get the "id" almLeaveRule.
     */
    @RequestMapping(value = "/almLeaveRules/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeaveRule> getAlmLeaveRule(@PathVariable Long id) {
        log.debug("REST request to get AlmLeaveRule : {}", id);
        return Optional.ofNullable(almLeaveRuleRepository.findOne(id))
            .map(almLeaveRule -> new ResponseEntity<>(
                almLeaveRule,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almLeaveRules/:id -> delete the "id" almLeaveRule.
     */
    @RequestMapping(value = "/almLeaveRules/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmLeaveRule(@PathVariable Long id) {
        log.debug("REST request to delete AlmLeaveRule : {}", id);
        almLeaveRuleRepository.delete(id);
        almLeaveRuleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almLeaveRule", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almLeaveRules/:query -> search for the almLeaveRule corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almLeaveRules/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmLeaveRule> searchAlmLeaveRules(@PathVariable String query) {
        return StreamSupport
            .stream(almLeaveRuleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/almLeaveRulesCheckLeaveGroupAndType/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByLeaveGroupAndType(@RequestParam Long groupId, @RequestParam Long leaveTypeId)
    {
        Optional<AlmLeaveRule> almLeaveRule = almLeaveRuleRepository.findOneByAlmLeaveGroupAndAlmLeaveType(groupId,leaveTypeId);
        log.debug("almLeaveRules by name: "+leaveTypeId+", Stat: "+Optional.empty().equals(almLeaveRule));
        Map map =new HashMap();
        map.put("groupId", groupId);
        map.put("leaveTypeId", leaveTypeId);
        if(Optional.empty().equals(almLeaveRule))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
