package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AssetDestroy;
import gov.step.app.repository.AssetDestroyRepository;
import gov.step.app.repository.search.AssetDestroySearchRepository;
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
 * REST controller for managing AssetDestroy.
 */
@RestController
@RequestMapping("/api")
public class AssetDestroyResource {

    private final Logger log = LoggerFactory.getLogger(AssetDestroyResource.class);

    @Inject
    private AssetDestroyRepository assetDestroyRepository;

    @Inject
    private AssetDestroySearchRepository assetDestroySearchRepository;

    /**
     * POST  /assetDestroys -> Create a new assetDestroy.
     */
    @RequestMapping(value = "/assetDestroys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetDestroy> createAssetDestroy(@Valid @RequestBody AssetDestroy assetDestroy) throws URISyntaxException {
        log.debug("REST request to save AssetDestroy : {}", assetDestroy);
        if (assetDestroy.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetDestroy cannot already have an ID").body(null);
        }
        AssetDestroy result = assetDestroyRepository.save(assetDestroy);
        assetDestroySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetDestroys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetDestroy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetDestroys -> Updates an existing assetDestroy.
     */
    @RequestMapping(value = "/assetDestroys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetDestroy> updateAssetDestroy(@Valid @RequestBody AssetDestroy assetDestroy) throws URISyntaxException {
        log.debug("REST request to update AssetDestroy : {}", assetDestroy);
        if (assetDestroy.getId() == null) {
            return createAssetDestroy(assetDestroy);
        }
        AssetDestroy result = assetDestroyRepository.save(assetDestroy);
        assetDestroySearchRepository.save(assetDestroy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetDestroy", assetDestroy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetDestroys -> get all the assetDestroys.
     */
    @RequestMapping(value = "/assetDestroys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetDestroy>> getAllAssetDestroys(Pageable pageable)
        throws URISyntaxException {
        Page<AssetDestroy> page = assetDestroyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetDestroys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetDestroys/:id -> get the "id" assetDestroy.
     */
    @RequestMapping(value = "/assetDestroys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetDestroy> getAssetDestroy(@PathVariable Long id) {
        log.debug("REST request to get AssetDestroy : {}", id);
        return Optional.ofNullable(assetDestroyRepository.findOne(id))
            .map(assetDestroy -> new ResponseEntity<>(
                assetDestroy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetDestroys/:id -> delete the "id" assetDestroy.
     */
    @RequestMapping(value = "/assetDestroys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetDestroy(@PathVariable Long id) {
        log.debug("REST request to delete AssetDestroy : {}", id);
        assetDestroyRepository.delete(id);
        assetDestroySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetDestroy", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetDestroys/:query -> search for the assetDestroy corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetDestroys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetDestroy> searchAssetDestroys(@PathVariable String query) {
        return StreamSupport
            .stream(assetDestroySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
