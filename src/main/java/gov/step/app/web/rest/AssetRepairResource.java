package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AssetRepair;
import gov.step.app.repository.AssetRepairRepository;
import gov.step.app.repository.search.AssetRepairSearchRepository;
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
 * REST controller for managing AssetRepair.
 */
@RestController
@RequestMapping("/api")
public class AssetRepairResource {

    private final Logger log = LoggerFactory.getLogger(AssetRepairResource.class);

    @Inject
    private AssetRepairRepository assetRepairRepository;

    @Inject
    private AssetRepairSearchRepository assetRepairSearchRepository;

    /**
     * POST  /assetRepairs -> Create a new assetRepair.
     */
    @RequestMapping(value = "/assetRepairs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRepair> createAssetRepair(@Valid @RequestBody AssetRepair assetRepair) throws URISyntaxException {
        log.debug("REST request to save AssetRepair : {}", assetRepair);
        if (assetRepair.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetRepair cannot already have an ID").body(null);
        }
        AssetRepair result = assetRepairRepository.save(assetRepair);
        assetRepairSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetRepairs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetRepair", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetRepairs -> Updates an existing assetRepair.
     */
    @RequestMapping(value = "/assetRepairs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRepair> updateAssetRepair(@Valid @RequestBody AssetRepair assetRepair) throws URISyntaxException {
        log.debug("REST request to update AssetRepair : {}", assetRepair);
        if (assetRepair.getId() == null) {
            return createAssetRepair(assetRepair);
        }
        AssetRepair result = assetRepairRepository.save(assetRepair);
        assetRepairSearchRepository.save(assetRepair);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetRepair", assetRepair.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetRepairs -> get all the assetRepairs.
     */
    @RequestMapping(value = "/assetRepairs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetRepair>> getAllAssetRepairs(Pageable pageable)
        throws URISyntaxException {
        Page<AssetRepair> page = assetRepairRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetRepairs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetRepairs/:id -> get the "id" assetRepair.
     */
    @RequestMapping(value = "/assetRepairs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRepair> getAssetRepair(@PathVariable Long id) {
        log.debug("REST request to get AssetRepair : {}", id);
        return Optional.ofNullable(assetRepairRepository.findOne(id))
            .map(assetRepair -> new ResponseEntity<>(
                assetRepair,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /assetRepairs/appid/:assetCode -> get the "applicationId" sisStudentReg.
     */

    @RequestMapping(value = "/assetRepairs/findAssetRecordAssetCode/{assetCode}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRepair> findAllRecordCodeassetCode(@PathVariable String assetCode) {
        return Optional.ofNullable(assetRepairRepository.findOne(Long.parseLong(assetCode)))
            .map(assetRepair -> new ResponseEntity<>(
                assetRepair,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetRepairs/:id -> delete the "id" assetRepair.
     */
    @RequestMapping(value = "/assetRepairs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetRepair(@PathVariable Long id) {
        log.debug("REST request to delete AssetRepair : {}", id);
        assetRepairRepository.delete(id);
        assetRepairSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetRepair", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetRepairs/:query -> search for the assetRepair corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetRepairs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetRepair> searchAssetRepairs(@PathVariable String query) {
        return StreamSupport
            .stream(assetRepairSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
