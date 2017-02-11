package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Relationship;
import gov.step.app.repository.RelationshipRepository;
import gov.step.app.repository.search.RelationshipSearchRepository;
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
 * REST controller for managing Relationship.
 */
@RestController
@RequestMapping("/api")
public class RelationshipResource {

    private final Logger log = LoggerFactory.getLogger(RelationshipResource.class);

    @Inject
    private RelationshipRepository relationshipRepository;

    @Inject
    private RelationshipSearchRepository relationshipSearchRepository;

    /**
     * POST  /relationships -> Create a new relationship.
     */
    @RequestMapping(value = "/relationships",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Relationship> createRelationship(@Valid @RequestBody Relationship relationship) throws URISyntaxException {
        log.debug("REST request to save Relationship : {}", relationship);
        if (relationship.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new relationship cannot already have an ID").body(null);
        }
        Relationship result = relationshipRepository.save(relationship);
        relationshipSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("relationship", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /relationships -> Updates an existing relationship.
     */
    @RequestMapping(value = "/relationships",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Relationship> updateRelationship(@Valid @RequestBody Relationship relationship) throws URISyntaxException {
        log.debug("REST request to update Relationship : {}", relationship);
        if (relationship.getId() == null) {
            return createRelationship(relationship);
        }
        Relationship result = relationshipRepository.save(relationship);
        relationshipSearchRepository.save(relationship);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("relationship", relationship.getId().toString()))
            .body(result);
    }

    /**
     * GET  /relationships -> get all the relationships.
     */
    @RequestMapping(value = "/relationships",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Relationship>> getAllRelationships(Pageable pageable)
        throws URISyntaxException {
        Page<Relationship> page = relationshipRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/relationships");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /relationships/:id -> get the "id" relationship.
     */
    @RequestMapping(value = "/relationships/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Relationship> getRelationship(@PathVariable Long id) {
        log.debug("REST request to get Relationship : {}", id);
        return Optional.ofNullable(relationshipRepository.findOne(id))
            .map(relationship -> new ResponseEntity<>(
                relationship,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /relationships/:id -> delete the "id" relationship.
     */
    @RequestMapping(value = "/relationships/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        log.debug("REST request to delete Relationship : {}", id);
        relationshipRepository.delete(id);
        relationshipSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("relationship", id.toString())).build();
    }

    /**
     * SEARCH  /_search/relationships/:query -> search for the relationship corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/relationships/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Relationship> searchRelationships(@PathVariable String query) {
        return StreamSupport
            .stream(relationshipSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
