package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AssetAccuisitionSetup;
import gov.step.app.repository.AssetAccuisitionSetupRepository;
import gov.step.app.repository.search.AssetAccuisitionSetupSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AssetAccuisitionSetup.
 */
@RestController
@RequestMapping("/api")
public class AssetAccuisitionSetupResource {

    private final Logger log = LoggerFactory.getLogger(AssetAccuisitionSetupResource.class);

    @Inject
    private AssetAccuisitionSetupRepository assetAccuisitionSetupRepository;

    @Inject
    private AssetAccuisitionSetupSearchRepository assetAccuisitionSetupSearchRepository;

    /**
     * POST  /assetAccuisitionSetups -> Create a new assetAccuisitionSetup.
     */
    @RequestMapping(value = "/assetAccuisitionSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetAccuisitionSetup> createAssetAccuisitionSetup(@Valid @RequestBody AssetAccuisitionSetup assetAccuisitionSetup) throws URISyntaxException {
        log.debug("REST request to save AssetAccuisitionSetup : {}", assetAccuisitionSetup);
        if (assetAccuisitionSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetAccuisitionSetup cannot already have an ID").body(null);
        }
        AssetAccuisitionSetup result = assetAccuisitionSetupRepository.save(assetAccuisitionSetup);
        assetAccuisitionSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetAccuisitionSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetAccuisitionSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetAccuisitionSetups -> Updates an existing assetAccuisitionSetup.
     */
    @RequestMapping(value = "/assetAccuisitionSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetAccuisitionSetup> updateAssetAccuisitionSetup(@Valid @RequestBody AssetAccuisitionSetup assetAccuisitionSetup) throws URISyntaxException {
        log.debug("REST request to update AssetAccuisitionSetup : {}", assetAccuisitionSetup);
        if (assetAccuisitionSetup.getId() == null) {
            return createAssetAccuisitionSetup(assetAccuisitionSetup);
        }
        AssetAccuisitionSetup result = assetAccuisitionSetupRepository.save(assetAccuisitionSetup);
        assetAccuisitionSetupSearchRepository.save(assetAccuisitionSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetAccuisitionSetup", assetAccuisitionSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetAccuisitionSetups -> get all the assetAccuisitionSetups.
     */
    @RequestMapping(value = "/assetAccuisitionSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetAccuisitionSetup>> getAllAssetAccuisitionSetups(Pageable pageable)
        throws URISyntaxException {
        Page<AssetAccuisitionSetup> page = assetAccuisitionSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetAccuisitionSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetAccuisitionSetups/:id -> get the "id" assetAccuisitionSetup.
     */
    @RequestMapping(value = "/assetAccuisitionSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetAccuisitionSetup> getAssetAccuisitionSetup(@PathVariable Long id) {
        log.debug("REST request to get AssetAccuisitionSetup : {}", id);
        return Optional.ofNullable(assetAccuisitionSetupRepository.findOne(id))
            .map(assetAccuisitionSetup -> new ResponseEntity<>(
                assetAccuisitionSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetAccuisitionSetups/:id -> delete the "id" assetAccuisitionSetup.
     */
    @RequestMapping(value = "/assetAccuisitionSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetAccuisitionSetup(@PathVariable Long id) {
        log.debug("REST request to delete AssetAccuisitionSetup : {}", id);
        assetAccuisitionSetupRepository.delete(id);
        assetAccuisitionSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetAccuisitionSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetAccuisitionSetups/:query -> search for the assetAccuisitionSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetAccuisitionSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetAccuisitionSetup> searchAssetAccuisitionSetups(@PathVariable String query) {
        return StreamSupport
            .stream(assetAccuisitionSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/assetAccuisitionSetups/assetAccuisitionSetupCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getAssetAccuisitionSetupCodeUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<AssetAccuisitionSetup> assetAccuisitionSetup = assetAccuisitionSetupRepository.findOneByCode(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(assetAccuisitionSetup)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/assetAccuisitionSetups/assetAccuisitionSetupNameUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getAssetAccuisitionSetupNameUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<AssetAccuisitionSetup> assetAccuisitionSetup = assetAccuisitionSetupRepository.findOneByCode(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(assetAccuisitionSetup)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }
}
