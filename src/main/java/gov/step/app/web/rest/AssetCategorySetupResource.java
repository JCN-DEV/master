package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AssetCategorySetup;
import gov.step.app.domain.TempEmployer;
import gov.step.app.domain.enumeration.TempEmployerStatus;
import gov.step.app.repository.AssetCategorySetupRepository;
import gov.step.app.repository.search.AssetCategorySetupSearchRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AssetCategorySetup.
 */
@RestController
@RequestMapping("/api")
public class AssetCategorySetupResource {

    private final Logger log = LoggerFactory.getLogger(AssetCategorySetupResource.class);

    @Inject
    private AssetCategorySetupRepository assetCategorySetupRepository;

    @Inject
    private AssetCategorySetupSearchRepository assetCategorySetupSearchRepository;

    /**
     * POST  /assetCategorySetups -> Create a new assetCategorySetup.
     */
    @RequestMapping(value = "/assetCategorySetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetCategorySetup> createAssetCategorySetup(@Valid @RequestBody AssetCategorySetup assetCategorySetup) throws URISyntaxException {
        log.debug("REST request to save AssetCategorySetup : {}", assetCategorySetup);
        if (assetCategorySetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetCategorySetup cannot already have an ID").body(null);
        }
        AssetCategorySetup result = assetCategorySetupRepository.save(assetCategorySetup);
        assetCategorySetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetCategorySetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetCategorySetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetCategorySetups -> Updates an existing assetCategorySetup.
     */
    @RequestMapping(value = "/assetCategorySetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetCategorySetup> updateAssetCategorySetup(@Valid @RequestBody AssetCategorySetup assetCategorySetup) throws URISyntaxException {
        log.debug("REST request to update AssetCategorySetup : {}", assetCategorySetup);
        if (assetCategorySetup.getId() == null) {
            return createAssetCategorySetup(assetCategorySetup);
        }
        AssetCategorySetup result = assetCategorySetupRepository.save(assetCategorySetup);
        assetCategorySetupSearchRepository.save(assetCategorySetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetCategorySetup", assetCategorySetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetCategorySetups -> get all the assetCategorySetups.
     */
    @RequestMapping(value = "/assetCategorySetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetCategorySetup>> getAllAssetCategorySetups(Pageable pageable)
        throws URISyntaxException {
        Page<AssetCategorySetup> page = assetCategorySetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetCategorySetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetCategorySetups/:id -> get the "id" assetCategorySetup.
     */
    @RequestMapping(value = "/assetCategorySetups/getByType/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetCategorySetup> getAssetCategorySetupByType(@PathVariable Long id) {
        log.debug("REST request to get AssetCategorySetup : {}", id);
        List assetCategorySetup = new ArrayList<>();
        assetCategorySetup = assetCategorySetupRepository.findAllByTypeId(id);
        return assetCategorySetup;
    }

    /**
     * GET  /assetCategorySetups/:id -> get the "id" assetCategorySetup.
     */
    @RequestMapping(value = "/assetCategorySetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetCategorySetup> getAssetCategorySetup(@PathVariable Long id) {
        log.debug("REST request to get AssetCategorySetup : {}", id);
        return Optional.ofNullable(assetCategorySetupRepository.findOne(id))
            .map(assetCategorySetup -> new ResponseEntity<>(
                assetCategorySetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetCategorySetups/:id -> delete the "id" assetCategorySetup.
     */
    @RequestMapping(value = "/assetCategorySetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetCategorySetup(@PathVariable Long id) {
        log.debug("REST request to delete AssetCategorySetup : {}", id);
        assetCategorySetupRepository.delete(id);
        assetCategorySetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetCategorySetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetCategorySetups/:query -> search for the assetCategorySetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetCategorySetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetCategorySetup> searchAssetCategorySetups(@PathVariable String query) {
        return StreamSupport
            .stream(assetCategorySetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/assetCategorySetups/assetCategorySetupNameUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getDlContTypeNameUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<AssetCategorySetup> assetCategorySetup = assetCategorySetupRepository.findOneByName(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(assetCategorySetup)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/assetCategorySetups/assetCategorySetupCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getDlContTypeCodeUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<AssetCategorySetup> assetCategorySetup = assetCategorySetupRepository.findOneByCode(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(assetCategorySetup)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }
}
