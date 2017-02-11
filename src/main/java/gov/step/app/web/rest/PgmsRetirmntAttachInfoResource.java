package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PgmsRetirmntAttachInfo;
import gov.step.app.repository.PgmsRetirmntAttachInfoRepository;
import gov.step.app.repository.search.PgmsRetirmntAttachInfoSearchRepository;
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
 * REST controller for managing PgmsRetirmntAttachInfo.
 */
@RestController
@RequestMapping("/api")
public class PgmsRetirmntAttachInfoResource {

    private final Logger log = LoggerFactory.getLogger(PgmsRetirmntAttachInfoResource.class);

    @Inject
    private PgmsRetirmntAttachInfoRepository pgmsRetirmntAttachInfoRepository;

    @Inject
    private PgmsRetirmntAttachInfoSearchRepository pgmsRetirmntAttachInfoSearchRepository;

    /**
     * POST  /pgmsRetirmntAttachInfos -> Create a new pgmsRetirmntAttachInfo.
     */
    @RequestMapping(value = "/pgmsRetirmntAttachInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsRetirmntAttachInfo> createPgmsRetirmntAttachInfo(@Valid @RequestBody PgmsRetirmntAttachInfo pgmsRetirmntAttachInfo) throws URISyntaxException {
        log.debug("REST request to save PgmsRetirmntAttachInfo : {}", pgmsRetirmntAttachInfo);
        if (pgmsRetirmntAttachInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsRetirmntAttachInfo cannot already have an ID").body(null);
        }
        PgmsRetirmntAttachInfo result = pgmsRetirmntAttachInfoRepository.save(pgmsRetirmntAttachInfo);
        pgmsRetirmntAttachInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsRetirmntAttachInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsRetirmntAttachInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsRetirmntAttachInfos -> Updates an existing pgmsRetirmntAttachInfo.
     */
    @RequestMapping(value = "/pgmsRetirmntAttachInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsRetirmntAttachInfo> updatePgmsRetirmntAttachInfo(@Valid @RequestBody PgmsRetirmntAttachInfo pgmsRetirmntAttachInfo) throws URISyntaxException {
        log.debug("REST request to update PgmsRetirmntAttachInfo : {}", pgmsRetirmntAttachInfo);
        if (pgmsRetirmntAttachInfo.getId() == null) {
            return createPgmsRetirmntAttachInfo(pgmsRetirmntAttachInfo);
        }
        PgmsRetirmntAttachInfo result = pgmsRetirmntAttachInfoRepository.save(pgmsRetirmntAttachInfo);
        pgmsRetirmntAttachInfoSearchRepository.save(pgmsRetirmntAttachInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsRetirmntAttachInfo", pgmsRetirmntAttachInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsRetirmntAttachInfos -> get all the pgmsRetirmntAttachInfos.
     */
    @RequestMapping(value = "/pgmsRetirmntAttachInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsRetirmntAttachInfo>> getAllPgmsRetirmntAttachInfos(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsRetirmntAttachInfo> page = pgmsRetirmntAttachInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsRetirmntAttachInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsRetirmntAttachInfos/:id -> get the "id" pgmsRetirmntAttachInfo.
     */
    @RequestMapping(value = "/pgmsRetirmntAttachInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsRetirmntAttachInfo> getPgmsRetirmntAttachInfo(@PathVariable Long id) {
        log.debug("REST request to get PgmsRetirmntAttachInfo : {}", id);
        return Optional.ofNullable(pgmsRetirmntAttachInfoRepository.findOne(id))
            .map(pgmsRetirmntAttachInfo -> new ResponseEntity<>(
                pgmsRetirmntAttachInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsRetirmntAttachInfos/:id -> delete the "id" pgmsRetirmntAttachInfo.
     */
    @RequestMapping(value = "/pgmsRetirmntAttachInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsRetirmntAttachInfo(@PathVariable Long id) {
        log.debug("REST request to delete PgmsRetirmntAttachInfo : {}", id);
        pgmsRetirmntAttachInfoRepository.delete(id);
        pgmsRetirmntAttachInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsRetirmntAttachInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsRetirmntAttachInfos/:query -> search for the pgmsRetirmntAttachInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsRetirmntAttachInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsRetirmntAttachInfo> searchPgmsRetirmntAttachInfos(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsRetirmntAttachInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
