package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstGenInfo;
import gov.step.app.domain.InstGovernBody;
import gov.step.app.repository.InstGenInfoRepository;
import gov.step.app.repository.InstGovernBodyRepository;
import gov.step.app.repository.search.InstGovernBodySearchRepository;
import gov.step.app.security.SecurityUtils;
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
 * REST controller for managing InstGovernBody.
 */
@RestController
@RequestMapping("/api")
public class InstGovernBodyResource {

    private final Logger log = LoggerFactory.getLogger(InstGovernBodyResource.class);

    @Inject
    private InstGovernBodyRepository instGovernBodyRepository;

    @Inject
    private InstGovernBodySearchRepository instGovernBodySearchRepository;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    /**
     * POST  /instGovernBodys -> Create a new instGovernBody.
     */
    @RequestMapping(value = "/instGovernBodys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovernBody> createInstGovernBody(@Valid @RequestBody InstGovernBody instGovernBody) throws URISyntaxException {
        log.debug("REST request to save InstGovernBody : {}", instGovernBody);
        if (instGovernBody.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instGovernBody cannot already have an ID").body(null);
        }
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(SecurityUtils.getCurrentUserLogin());
        if(instGenInfo==null){
            return ResponseEntity.badRequest().header("Failure", "instGovernBody cannot save without Institute").body(null);
        }
        instGovernBody.setInstitute(instGenInfo.getInstitute());
        InstGovernBody result = instGovernBodyRepository.save(instGovernBody);

        instGovernBodySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instGovernBodys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instGovernBody", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instGovernBodys -> Updates an existing instGovernBody.
     */
    @RequestMapping(value = "/instGovernBodys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovernBody> updateInstGovernBody(@Valid @RequestBody InstGovernBody instGovernBody) throws URISyntaxException {
        log.debug("REST request to update InstGovernBody : {}", instGovernBody);
        if (instGovernBody.getId() == null) {
            return createInstGovernBody(instGovernBody);
        }
        InstGovernBody result = instGovernBodyRepository.save(instGovernBody);
        instGovernBodySearchRepository.save(instGovernBody);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instGovernBody", instGovernBody.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instGovernBodys -> get all the instGovernBodys.
     */
    @RequestMapping(value = "/instGovernBodys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstGovernBody>> getAllInstGovernBodys(Pageable pageable)
        throws URISyntaxException {
        Page<InstGovernBody> page = instGovernBodyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instGovernBodys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instGovernBodys/:id -> get the "id" instGovernBody.
     */
    @RequestMapping(value = "/instGovernBodys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovernBody> getInstGovernBody(@PathVariable Long id) {
        log.debug("REST request to get InstGovernBody : {}", id);
        return Optional.ofNullable(instGovernBodyRepository.findOne(id))
            .map(instGovernBody -> new ResponseEntity<>(
                instGovernBody,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instGovernBodys/:id -> delete the "id" instGovernBody.
     */
    @RequestMapping(value = "/instGovernBodys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstGovernBody(@PathVariable Long id) {
        log.debug("REST request to delete InstGovernBody : {}", id);
        instGovernBodyRepository.delete(id);
        instGovernBodySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instGovernBody", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instGovernBodys/:query -> search for the instGovernBody corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instGovernBodys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstGovernBody> searchInstGovernBodys(@PathVariable String query) {
        return StreamSupport
            .stream(instGovernBodySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
