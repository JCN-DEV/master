package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AclSid;
import gov.step.app.repository.AclSidRepository;
import java.util.stream.StreamSupport;
import gov.step.app.repository.search.AclSidSearchRepository;
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
 * REST controller for managing AclSid.
 */
@RestController
@RequestMapping("/api")
public class AclSidResource {

    private final Logger log = LoggerFactory.getLogger(AclSidResource.class);

    @Inject
    private AclSidRepository aclSidRepository;

    @Inject
    private AclSidSearchRepository aclSidSearchRepository;

    /**
     * POST  /aclSids -> Create a new aclSid.
     */
    @RequestMapping(value = "/aclSids",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclSid> createAclSid(@Valid @RequestBody AclSid aclSid) throws URISyntaxException {
        log.debug("REST request to save AclSid : {}", aclSid);
        if (aclSid.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aclSid cannot already have an ID").body(null);
        }
        AclSid result = aclSidRepository.save(aclSid);
        aclSidSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aclSids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aclSid", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aclSids -> Updates an existing aclSid.
     */
    @RequestMapping(value = "/aclSids",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclSid> updateAclSid(@Valid @RequestBody AclSid aclSid) throws URISyntaxException {
        log.debug("REST request to update AclSid : {}", aclSid);
        if (aclSid.getId() == null) {
            return createAclSid(aclSid);
        }
        AclSid result = aclSidRepository.save(aclSid);
        aclSidSearchRepository.save(aclSid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aclSid", aclSid.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aclSids -> get all the aclSids.
     */
    @RequestMapping(value = "/aclSids",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AclSid>> getAllAclSids(Pageable pageable)
        throws URISyntaxException {
        Page<AclSid> page = aclSidRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aclSids");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aclSids/:id -> get the "id" aclSid.
     */
    @RequestMapping(value = "/aclSids/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclSid> getAclSid(@PathVariable Long id) {
        log.debug("REST request to get AclSid : {}", id);
        return Optional.ofNullable(aclSidRepository.findOne(id))
            .map(aclSid -> new ResponseEntity<>(
                aclSid,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aclSids/:id -> delete the "id" aclSid.
     */
    @RequestMapping(value = "/aclSids/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAclSid(@PathVariable Long id) {
        log.debug("REST request to delete AclSid : {}", id);
        aclSidRepository.delete(id);
        aclSidSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aclSid", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aclSids/:query -> search for the aclSid corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aclSids/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AclSid> searchAclSids(@PathVariable String query) {
        return StreamSupport
            .stream(aclSidSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    

}
