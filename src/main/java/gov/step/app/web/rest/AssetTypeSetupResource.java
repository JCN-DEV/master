package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AssetCategorySetup;
import gov.step.app.domain.AssetTypeSetup;
import gov.step.app.repository.AssetTypeSetupRepository;
import gov.step.app.repository.search.AssetTypeSetupSearchRepository;
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
 * REST controller for managing AssetTypeSetup.
 */
@RestController
@RequestMapping("/api")
public class AssetTypeSetupResource {

    private final Logger log = LoggerFactory.getLogger(AssetTypeSetupResource.class);

    @Inject
    private AssetTypeSetupRepository assetTypeSetupRepository;

    @Inject
    private AssetTypeSetupSearchRepository assetTypeSetupSearchRepository;

    /**
     * POST  /assetTypeSetups -> Create a new assetTypeSetup.
     */
    @RequestMapping(value = "/assetTypeSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetTypeSetup> createAssetTypeSetup(@Valid @RequestBody AssetTypeSetup assetTypeSetup) throws URISyntaxException {
        log.debug("REST request to save AssetTypeSetup : {}", assetTypeSetup);
        if (assetTypeSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetTypeSetup cannot already have an ID").body(null);
        }
        if(assetTypeSetup.getDescription() == null){
            assetTypeSetup.setDescription(assetTypeSetup.getTypeName());
        }
        AssetTypeSetup result = assetTypeSetupRepository.save(assetTypeSetup);
        assetTypeSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetTypeSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetTypeSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetTypeSetups -> Updates an existing assetTypeSetup.
     */
    @RequestMapping(value = "/assetTypeSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetTypeSetup> updateAssetTypeSetup(@Valid @RequestBody AssetTypeSetup assetTypeSetup) throws URISyntaxException {
        log.debug("REST request to update AssetTypeSetup : {}", assetTypeSetup);
        if (assetTypeSetup.getId() == null) {
            return createAssetTypeSetup(assetTypeSetup);
        }
        if(assetTypeSetup.getDescription() == null){
            assetTypeSetup.setDescription(assetTypeSetup.getTypeName());
        }
        AssetTypeSetup result = assetTypeSetupRepository.save(assetTypeSetup);
        assetTypeSetupSearchRepository.save(assetTypeSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetTypeSetup", assetTypeSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetTypeSetups -> get all the assetTypeSetups.
     */
    @RequestMapping(value = "/assetTypeSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetTypeSetup>> getAllAssetTypeSetups(Pageable pageable)
        throws URISyntaxException {
        Page<AssetTypeSetup> page = assetTypeSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetTypeSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetTypeSetups/:id -> get the "id" assetTypeSetup.
     */
    @RequestMapping(value = "/assetTypeSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetTypeSetup> getAssetTypeSetup(@PathVariable Long id) {
        log.debug("REST request to get AssetTypeSetup : {}", id);
        return Optional.ofNullable(assetTypeSetupRepository.findOne(id))
            .map(assetTypeSetup -> new ResponseEntity<>(
                assetTypeSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /assetTypeSetups/code/:code -> get the assetTypeSetups by type code
     */
    @RequestMapping(value = "/assetTypeSetups/code/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetTypeSetup> getAssetTypeSetupByTypeCode(@PathVariable String code) {
        log.debug("REST request to get AssetTypeSetup : {}", code);
        return Optional.ofNullable(assetTypeSetupRepository.findOneByTypeCode(code))
            .map(assetTypeSetup -> new ResponseEntity<>(
                assetTypeSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetTypeSetups/:id -> delete the "id" assetTypeSetup.
     */
    @RequestMapping(value = "/assetTypeSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetTypeSetup(@PathVariable Long id) {
        log.debug("REST request to delete AssetTypeSetup : {}", id);
        assetTypeSetupRepository.delete(id);
        assetTypeSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetTypeSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetTypeSetups/:query -> search for the assetTypeSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetTypeSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetTypeSetup> searchAssetTypeSetups(@PathVariable String query) {
        return StreamSupport
            .stream(assetTypeSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }



    @RequestMapping(value = "/assetTypeset/assetTypesetTypeCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getDlFileTypeUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<AssetTypeSetup> assetTypeSet = assetTypeSetupRepository.findAllByTypeCode(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(assetTypeSet)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/assetTypeset/assetTypesetTypeNameUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getAssetTypeName( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<AssetTypeSetup> assetTypeSet = assetTypeSetupRepository.findAllByTypeName(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(assetTypeSet)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }



}
