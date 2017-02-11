package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AclClass;
import gov.step.app.repository.AclClassRepository;
import java.util.stream.StreamSupport;
import gov.step.app.repository.search.AclClassSearchRepository;
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
 * REST controller for managing AclClass.
 */
@RestController
@RequestMapping("/api")
public class AclClassResource {

    private final Logger log = LoggerFactory.getLogger(AclClassResource.class);

    @Inject
    private AclClassRepository aclClassRepository;

    @Inject
    private AclClassSearchRepository aclClassSearchRepository;

    /**
     * POST  /aclClasss -> Create a new aclClass.
     */
    @RequestMapping(value = "/aclClasss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclClass> createAclClass(@Valid @RequestBody AclClass aclClass) throws URISyntaxException {
        log.debug("REST request to save AclClass : {}", aclClass);
        if (aclClass.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aclClass cannot already have an ID").body(null);
        }
        AclClass result = aclClassRepository.save(aclClass);
        aclClassSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aclClasss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aclClass", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aclClasss -> Updates an existing aclClass.
     */
    @RequestMapping(value = "/aclClasss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclClass> updateAclClass(@Valid @RequestBody AclClass aclClass) throws URISyntaxException {
        log.debug("REST request to update AclClass : {}", aclClass);
        if (aclClass.getId() == null) {
            return createAclClass(aclClass);
        }
        AclClass result = aclClassRepository.save(aclClass);
        aclClassSearchRepository.save(aclClass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aclClass", aclClass.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aclClasss -> get all the aclClasss.
     */
    @RequestMapping(value = "/aclClasss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AclClass>> getAllAclClasss(Pageable pageable)
        throws URISyntaxException {
        Page<AclClass> page = aclClassRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aclClasss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aclClasss/:id -> get the "id" aclClass.
     */
    @RequestMapping(value = "/aclClasss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclClass> getAclClass(@PathVariable Long id) {
        log.debug("REST request to get AclClass : {}", id);
        return Optional.ofNullable(aclClassRepository.findOne(id))
            .map(aclClass -> new ResponseEntity<>(
                aclClass,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aclClasss/:id -> delete the "id" aclClass.
     */
    @RequestMapping(value = "/aclClasss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAclClass(@PathVariable Long id) {
        log.debug("REST request to delete AclClass : {}", id);
        aclClassRepository.delete(id);
        aclClassSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aclClass", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aclClasss/:query -> search for the aclClass corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aclClasss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AclClass> searchAclClasss(@PathVariable String query) {
        return StreamSupport
            .stream(aclClassSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    

}
