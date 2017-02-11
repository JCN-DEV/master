package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AssetAccuisitionSetup;
import gov.step.app.domain.AssetRecord;
import gov.step.app.repository.AssetRecordRepository;
import gov.step.app.repository.search.AssetRecordSearchRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AssetRecord.
 */
@RestController
@RequestMapping("/api")
public class AssetRecordResource {

    private final Logger log = LoggerFactory.getLogger(AssetRecordResource.class);

    @Inject
    private AssetRecordRepository assetRecordRepository;

    @Inject
    private AssetRecordSearchRepository assetRecordSearchRepository;

    /**
     * POST  /assetRecords -> Create a new assetRecord.
     */
    @RequestMapping(value = "/assetRecords",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRecord> createAssetRecord(@Valid @RequestBody AssetRecord assetRecord) throws URISyntaxException {
        log.debug("REST request to save AssetRecord : {}", assetRecord);
        if (assetRecord.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetRecord cannot already have an ID").body(null);
        }
        TransactionIdResource tir = new TransactionIdResource();
        assetRecord.setRecordCode(tir.getGeneratedid("ARC"));
        assetRecord.setReferenceNo(tir.getGeneratedid("REF"));

        DateResource dr = new DateResource();
        //assetRecord.setCreateDate(dr.getDateAsLocalDate());
        AssetRecord result = assetRecordRepository.save(assetRecord);
        assetRecordSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetRecords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetRecord", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetRecords -> Updates an existing assetRecord.
     */
    @RequestMapping(value = "/assetRecords",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRecord> updateAssetRecord(@Valid @RequestBody AssetRecord assetRecord) throws URISyntaxException {
        log.debug("REST request to update AssetRecord : {}", assetRecord);
        if (assetRecord.getId() == null) {
            return createAssetRecord(assetRecord);
        }
        AssetRecord result = assetRecordRepository.save(assetRecord);
        assetRecordSearchRepository.save(assetRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetRecord", assetRecord.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetRecords -> get all the assetRecords.
     */
    @RequestMapping(value = "/assetRecords",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetRecord>> getAllAssetRecords(Pageable pageable)
        throws URISyntaxException {
        Page<AssetRecord> page = assetRecordRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetRecords");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetRecords/:id -> get the "id" assetRecord.
     */
    @RequestMapping(value = "/assetRecords/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRecord> getAssetRecord(@PathVariable Long id) {
        log.debug("REST request to get AssetRecord : {}", id);
        return Optional.ofNullable(assetRecordRepository.findOne(id))
            .map(assetRecord -> new ResponseEntity<>(
                assetRecord,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetRecords/:id -> delete the "id" assetRecord.
     */
    @RequestMapping(value = "/assetRecords/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetRecord(@PathVariable Long id) {
        log.debug("REST request to delete AssetRecord : {}", id);
        assetRecordRepository.delete(id);
        assetRecordSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetRecord", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetRecords/:query -> search for the assetRecord corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetRecords/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetRecord> searchAssetRecords(@PathVariable String query) {
        return StreamSupport
            .stream(assetRecordSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /assetCategorySetups/:id -> get the "id" assetCategorySetup.
     */
    @RequestMapping(value = "/assetRecords/getByTypeAndCategory/{typeId}/{categoryId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetRecord> getAssetRecordsByTypeAndCategory(@PathVariable Long typeId, @PathVariable Long categoryId) {
        log.debug("REST request to get AssetCategorySetup : {}", typeId);
        List assetRecords = new ArrayList<>();
        assetRecords = assetRecordRepository.findAllByTypeIdAndCategoryId(typeId, categoryId);
        return assetRecords;
    }
}
