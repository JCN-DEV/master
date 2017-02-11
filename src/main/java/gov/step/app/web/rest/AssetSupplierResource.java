package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AssetSupplier;
import gov.step.app.repository.AssetSupplierRepository;
import gov.step.app.repository.search.AssetSupplierSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import gov.step.app.web.rest.util.TransactionIdResource;
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
 * REST controller for managing AssetSupplier.
 */
@RestController
@RequestMapping("/api")
public class AssetSupplierResource {

    private final Logger log = LoggerFactory.getLogger(AssetSupplierResource.class);

    @Inject
    private AssetSupplierRepository assetSupplierRepository;

    @Inject
    private AssetSupplierSearchRepository assetSupplierSearchRepository;

    /**
     * POST  /assetSuppliers -> Create a new assetSupplier.
     */
    @RequestMapping(value = "/assetSuppliers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetSupplier> createAssetSupplier(@Valid @RequestBody AssetSupplier assetSupplier) throws URISyntaxException {
        log.debug("REST request to save AssetSupplier : {}", assetSupplier);
        if (assetSupplier.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetSupplier cannot already have an ID").body(null);
        }
        TransactionIdResource transactionIdResource = new TransactionIdResource();
        assetSupplier.setSupplierId(transactionIdResource.getGeneratedid("SUP"));

        AssetSupplier result = assetSupplierRepository.save(assetSupplier);
        assetSupplierSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetSuppliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetSupplier", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetSuppliers -> Updates an existing assetSupplier.
     */
    @RequestMapping(value = "/assetSuppliers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetSupplier> updateAssetSupplier(@Valid @RequestBody AssetSupplier assetSupplier) throws URISyntaxException {
        log.debug("REST request to update AssetSupplier : {}", assetSupplier);
        if (assetSupplier.getId() == null) {
            return createAssetSupplier(assetSupplier);
        }
        AssetSupplier result = assetSupplierRepository.save(assetSupplier);
        assetSupplierSearchRepository.save(assetSupplier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetSupplier", assetSupplier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetSuppliers -> get all the assetSuppliers.
     */
    @RequestMapping(value = "/assetSuppliers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetSupplier>> getAllAssetSuppliers(Pageable pageable)
        throws URISyntaxException {
        Page<AssetSupplier> page = assetSupplierRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetSuppliers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetSuppliers/:id -> get the "id" assetSupplier.
     */
    @RequestMapping(value = "/assetSuppliers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetSupplier> getAssetSupplier(@PathVariable Long id) {
        log.debug("REST request to get AssetSupplier : {}", id);
        return Optional.ofNullable(assetSupplierRepository.findOne(id))
            .map(assetSupplier -> new ResponseEntity<>(
                assetSupplier,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetSuppliers/:id -> delete the "id" assetSupplier.
     */
    @RequestMapping(value = "/assetSuppliers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetSupplier(@PathVariable Long id) {
        log.debug("REST request to delete AssetSupplier : {}", id);
        assetSupplierRepository.delete(id);
        assetSupplierSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetSupplier", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetSuppliers/:query -> search for the assetSupplier corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetSuppliers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetSupplier> searchAssetSuppliers(@PathVariable String query) {
        return StreamSupport
            .stream(assetSupplierSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
