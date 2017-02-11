package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstCategory;
import gov.step.app.repository.InstCategoryRepository;
import gov.step.app.repository.search.InstCategorySearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstCategory.
 */
@RestController
@RequestMapping("/api")
public class InstCategoryResource {

    private final Logger log = LoggerFactory.getLogger(InstCategoryResource.class);

    @Inject
    private InstCategoryRepository instCategoryRepository;

    @Inject
    private InstCategorySearchRepository instCategorySearchRepository;

    /**
     * POST  /instCategorys -> Create a new instCategory.
     */
    @RequestMapping(value = "/instCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstCategory> createInstCategory(@RequestBody InstCategory instCategory) throws URISyntaxException {
        log.debug("REST request to save InstCategory : {}", instCategory);
        if (instCategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instCategory cannot already have an ID").body(null);
        }
        InstCategory result = instCategoryRepository.save(instCategory);
        instCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instCategorys -> Updates an existing instCategory.
     */
    @RequestMapping(value = "/instCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstCategory> updateInstCategory(@RequestBody InstCategory instCategory) throws URISyntaxException {
        log.debug("REST request to update InstCategory : {}", instCategory);
        if (instCategory.getId() == null) {
            return createInstCategory(instCategory);
        }
        InstCategory result = instCategoryRepository.save(instCategory);
        instCategorySearchRepository.save(instCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instCategory", instCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instCategorys -> get all the instCategorys.
     */
    @RequestMapping(value = "/instCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstCategory>> getAllInstCategorys(Pageable pageable)
        throws URISyntaxException {
        Page<InstCategory> page = instCategoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instCategorys -> get all the instCategorys.
     */
    @RequestMapping(value = "/instCategorys/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstCategory>> getAllActiveInstCategorys(Pageable pageable)
        throws URISyntaxException {
        Page<InstCategory> page = instCategoryRepository.findAllInstCategoriesByType(Boolean.TRUE, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instCategorys/active");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instCategorys/:id -> get the "id" instCategory.
     */
    @RequestMapping(value = "/instCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstCategory> getInstCategory(@PathVariable Long id) {
        log.debug("REST request to get InstCategory : {}", id);
        return Optional.ofNullable(instCategoryRepository.findOne(id))
            .map(instCategory -> new ResponseEntity<>(
                instCategory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instCategorys/:id -> delete the "id" instCategory.
     */
    @RequestMapping(value = "/instCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstCategory(@PathVariable Long id) {
        log.debug("REST request to delete InstCategory : {}", id);
        instCategoryRepository.delete(id);
        instCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instCategorys/:query -> search for the instCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstCategory> searchInstCategorys(@PathVariable String query) {
        return StreamSupport
            .stream(instCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Unique  /instCategory/code/ -> search for the instCategory Unique
     * to the query.
     * Md. Amanur Rahman
     */

    @RequestMapping(value = "/instCategory/code/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> instUniqueCategory(@RequestParam String value) {
        log.debug("REST request to get instUniqueCategory by Code : {}", value);
        InstCategory instCategory = instCategoryRepository.instUniqueCategory(value);
        Map map =new HashMap();
        map.put("value",value);
        if(instCategory == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    /**
     * Unique  /instCategory/code/ -> search for the instCategory Unique
     * to the query.
     * Md. Amanur Rahman
     */

    @RequestMapping(value = "/instCategory/name/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> instCategoryUniqueName(@RequestParam String value) {
        log.debug("REST request to get instUniqueCategory by name : {}", value);
        InstCategory instCategory = instCategoryRepository.instCategoryUniqueName(value);
        Map map =new HashMap();
        map.put("value",value);
        if(instCategory == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }




}
