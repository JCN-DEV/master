package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AssetAuctionInformation;
import gov.step.app.repository.AssetAuctionInformationRepository;
import gov.step.app.repository.search.AssetAuctionInformationSearchRepository;
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
 * REST controller for managing AssetAuctionInformation.
 */
@RestController
@RequestMapping("/api")
public class AssetAuctionInformationResource {

    private final Logger log = LoggerFactory.getLogger(AssetAuctionInformationResource.class);

    @Inject
    private AssetAuctionInformationRepository assetAuctionInformationRepository;

    @Inject
    private AssetAuctionInformationSearchRepository assetAuctionInformationSearchRepository;

    /**
     * POST  /assetAuctionInformations -> Create a new assetAuctionInformation.
     */
    @RequestMapping(value = "/assetAuctionInformations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetAuctionInformation> createAssetAuctionInformation(@RequestBody AssetAuctionInformation assetAuctionInformation) throws URISyntaxException {
        log.debug("REST request to save AssetAuctionInformation : {}", assetAuctionInformation);
        if (assetAuctionInformation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetAuctionInformation cannot already have an ID").body(null);
        }
        AssetAuctionInformation result = assetAuctionInformationRepository.save(assetAuctionInformation);
        assetAuctionInformationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetAuctionInformations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetAuctionInformation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetAuctionInformations -> Updates an existing assetAuctionInformation.
     */
    @RequestMapping(value = "/assetAuctionInformations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetAuctionInformation> updateAssetAuctionInformation(@RequestBody AssetAuctionInformation assetAuctionInformation) throws URISyntaxException {
        log.debug("REST request to update AssetAuctionInformation : {}", assetAuctionInformation);
        if (assetAuctionInformation.getId() == null) {
            return createAssetAuctionInformation(assetAuctionInformation);
        }
        AssetAuctionInformation result = assetAuctionInformationRepository.save(assetAuctionInformation);
        assetAuctionInformationSearchRepository.save(assetAuctionInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetAuctionInformation", assetAuctionInformation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetAuctionInformations -> get all the assetAuctionInformations.
     */
    @RequestMapping(value = "/assetAuctionInformations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetAuctionInformation>> getAllAssetAuctionInformations(Pageable pageable)
        throws URISyntaxException {
        Page<AssetAuctionInformation> page = assetAuctionInformationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetAuctionInformations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetAuctionInformations/:id -> get the "id" assetAuctionInformation.
     */
    @RequestMapping(value = "/assetAuctionInformations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetAuctionInformation> getAssetAuctionInformation(@PathVariable Long id) {
        log.debug("REST request to get AssetAuctionInformation : {}", id);
        return Optional.ofNullable(assetAuctionInformationRepository.findOne(id))
            .map(assetAuctionInformation -> new ResponseEntity<>(
                assetAuctionInformation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetAuctionInformations/:id -> delete the "id" assetAuctionInformation.
     */
    @RequestMapping(value = "/assetAuctionInformations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetAuctionInformation(@PathVariable Long id) {
        log.debug("REST request to delete AssetAuctionInformation : {}", id);
        assetAuctionInformationRepository.delete(id);
        assetAuctionInformationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetAuctionInformation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetAuctionInformations/:query -> search for the assetAuctionInformation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetAuctionInformations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetAuctionInformation> searchAssetAuctionInformations(@PathVariable String query) {
        return StreamSupport
            .stream(assetAuctionInformationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
