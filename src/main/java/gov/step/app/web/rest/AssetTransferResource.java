package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AssetTransfer;
import gov.step.app.repository.AssetTransferRepository;
import gov.step.app.repository.search.AssetTransferSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AssetTransfer.
 */
@RestController
@RequestMapping("/api")
public class AssetTransferResource {

    private final Logger log = LoggerFactory.getLogger(AssetTransferResource.class);

    @Inject
    private AssetTransferRepository assetTransferRepository;

    @Inject
    private AssetTransferSearchRepository assetTransferSearchRepository;

    /**
     * POST  /assetTransfers -> Create a new assetTransfer.
     */
    @RequestMapping(value = "/assetTransfers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetTransfer> createAssetTransfer(@RequestBody AssetTransfer assetTransfer) throws URISyntaxException {
        log.debug("REST request to save AssetTransfer : {}", assetTransfer);
        if (assetTransfer.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetTransfer cannot already have an ID").body(null);
        }
        AssetTransfer result = assetTransferRepository.save(assetTransfer);
        assetTransferSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetTransfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetTransfer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetTransfers -> Updates an existing assetTransfer.
     */
    @RequestMapping(value = "/assetTransfers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetTransfer> updateAssetTransfer(@RequestBody AssetTransfer assetTransfer) throws URISyntaxException {
        log.debug("REST request to update AssetTransfer : {}", assetTransfer);
        if (assetTransfer.getId() == null) {
            return createAssetTransfer(assetTransfer);
        }
        AssetTransfer result = assetTransferRepository.save(assetTransfer);
        assetTransferSearchRepository.save(assetTransfer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetTransfer", assetTransfer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetTransfers -> get all the assetTransfers.
     */
    @RequestMapping(value = "/assetTransfers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetTransfer>> getAllAssetTransfers(Pageable pageable)
        throws URISyntaxException {
        Page<AssetTransfer> page = assetTransferRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetTransfers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetTransfers/:id -> get the "id" assetTransfer.
     */
    @RequestMapping(value = "/assetTransfers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetTransfer> getAssetTransfer(@PathVariable Long id) {
        log.debug("REST request to get AssetTransfer : {}", id);
        return Optional.ofNullable(assetTransferRepository.findOne(id))
            .map(assetTransfer -> new ResponseEntity<>(
                assetTransfer,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetTransfers/:id -> delete the "id" assetTransfer.
     */
    @RequestMapping(value = "/assetTransfers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetTransfer(@PathVariable Long id) {
        log.debug("REST request to delete AssetTransfer : {}", id);
        assetTransferRepository.delete(id);
        assetTransferSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetTransfer", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetTransfers/:query -> search for the assetTransfer corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetTransfers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetTransfer> searchAssetTransfers(@PathVariable String query) {
        return StreamSupport
            .stream(assetTransferSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
