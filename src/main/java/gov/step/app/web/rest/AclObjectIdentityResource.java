package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AclObjectIdentity;
import gov.step.app.repository.AclObjectIdentityRepository;
import java.util.stream.StreamSupport;
import gov.step.app.repository.search.AclObjectIdentitySearchRepository;
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
 * REST controller for managing AclObjectIdentity.
 */
@RestController
@RequestMapping("/api")
public class AclObjectIdentityResource {

    private final Logger log = LoggerFactory.getLogger(AclObjectIdentityResource.class);

    @Inject
    private AclObjectIdentityRepository aclObjectIdentityRepository;

    @Inject
    private AclObjectIdentitySearchRepository aclObjectIdentitySearchRepository;

    /**
     * POST  /aclObjectIdentitys -> Create a new aclObjectIdentity.
     */
    @RequestMapping(value = "/aclObjectIdentitys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclObjectIdentity> createAclObjectIdentity(@Valid @RequestBody AclObjectIdentity aclObjectIdentity) throws URISyntaxException {
        log.debug("REST request to save AclObjectIdentity : {}", aclObjectIdentity);
        if (aclObjectIdentity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aclObjectIdentity cannot already have an ID").body(null);
        }
        AclObjectIdentity result = aclObjectIdentityRepository.save(aclObjectIdentity);
        aclObjectIdentitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aclObjectIdentitys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aclObjectIdentity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aclObjectIdentitys -> Updates an existing aclObjectIdentity.
     */
    @RequestMapping(value = "/aclObjectIdentitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclObjectIdentity> updateAclObjectIdentity(@Valid @RequestBody AclObjectIdentity aclObjectIdentity) throws URISyntaxException {
        log.debug("REST request to update AclObjectIdentity : {}", aclObjectIdentity);
        if (aclObjectIdentity.getId() == null) {
            return createAclObjectIdentity(aclObjectIdentity);
        }
        AclObjectIdentity result = aclObjectIdentityRepository.save(aclObjectIdentity);
        aclObjectIdentitySearchRepository.save(aclObjectIdentity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aclObjectIdentity", aclObjectIdentity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aclObjectIdentitys -> get all the aclObjectIdentitys.
     */
    @RequestMapping(value = "/aclObjectIdentitys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AclObjectIdentity>> getAllAclObjectIdentitys(Pageable pageable)
        throws URISyntaxException {
        Page<AclObjectIdentity> page = aclObjectIdentityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aclObjectIdentitys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aclObjectIdentitys/:id -> get the "id" aclObjectIdentity.
     */
    @RequestMapping(value = "/aclObjectIdentitys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclObjectIdentity> getAclObjectIdentity(@PathVariable Long id) {
        log.debug("REST request to get AclObjectIdentity : {}", id);
        return Optional.ofNullable(aclObjectIdentityRepository.findOne(id))
            .map(aclObjectIdentity -> new ResponseEntity<>(
                aclObjectIdentity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aclObjectIdentitys/:id -> delete the "id" aclObjectIdentity.
     */
    @RequestMapping(value = "/aclObjectIdentitys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAclObjectIdentity(@PathVariable Long id) {
        log.debug("REST request to delete AclObjectIdentity : {}", id);
        aclObjectIdentityRepository.delete(id);
        aclObjectIdentitySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aclObjectIdentity", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aclObjectIdentitys/:query -> search for the aclObjectIdentity corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aclObjectIdentitys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AclObjectIdentity> searchAclObjectIdentitys(@PathVariable String query) {
        return StreamSupport
            .stream(aclObjectIdentitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    

}
