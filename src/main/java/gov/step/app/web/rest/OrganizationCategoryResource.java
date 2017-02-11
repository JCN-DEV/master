package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Country;
import gov.step.app.domain.OrganizationCategory;
import gov.step.app.repository.OrganizationCategoryRepository;
import gov.step.app.repository.search.OrganizationCategorySearchRepository;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing OrganizationCategory.
 */
@RestController
@RequestMapping("/api")
public class OrganizationCategoryResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationCategoryResource.class);

    @Inject
    private OrganizationCategoryRepository organizationCategoryRepository;

    @Inject
    private OrganizationCategorySearchRepository organizationCategorySearchRepository;

    /**
     * POST  /organizationCategorys -> Create a new organizationCategory.
     */
    @RequestMapping(value = "/organizationCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationCategory> createOrganizationCategory(@Valid @RequestBody OrganizationCategory organizationCategory) throws URISyntaxException {
        log.debug("REST request to save OrganizationCategory : {}", organizationCategory);
        if (organizationCategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new organizationCategory cannot already have an ID").body(null);
        }
        OrganizationCategory result = organizationCategoryRepository.save(organizationCategory);
        organizationCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/organizationCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("organizationCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organizationCategorys -> Updates an existing organizationCategory.
     */
    @RequestMapping(value = "/organizationCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationCategory> updateOrganizationCategory(@Valid @RequestBody OrganizationCategory organizationCategory) throws URISyntaxException {
        log.debug("REST request to update OrganizationCategory : {}", organizationCategory);
        if (organizationCategory.getId() == null) {
            return createOrganizationCategory(organizationCategory);
        }
        OrganizationCategory result = organizationCategoryRepository.save(organizationCategory);
        organizationCategorySearchRepository.save(organizationCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("organizationCategory", organizationCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organizationCategorys -> get all the organizationCategorys.
     */
    @RequestMapping(value = "/organizationCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrganizationCategory>> getAllOrganizationCategorys(Pageable pageable)
        throws URISyntaxException {
        Page<OrganizationCategory> page = organizationCategoryRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizationCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /organizationCategorys -> get all the organizationCategorys.
     */
    @RequestMapping(value = "/organizationCategorys/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrganizationCategory>> getAllActiveOrganizationCategorys(Pageable pageable)
        throws URISyntaxException {
        Page<OrganizationCategory> page = organizationCategoryRepository.findAllActiveOrgCat(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizationCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /organizationCategorys/:id -> get the "id" organizationCategory.
     */
    @RequestMapping(value = "/organizationCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationCategory> getOrganizationCategory(@PathVariable Long id) {
        log.debug("REST request to get OrganizationCategory : {}", id);
        return Optional.ofNullable(organizationCategoryRepository.findOne(id))
            .map(organizationCategory -> new ResponseEntity<>(
                organizationCategory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /organizationCategorys/:id -> delete the "id" organizationCategory.
     */
    @RequestMapping(value = "/organizationCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOrganizationCategory(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationCategory : {}", id);
        organizationCategoryRepository.delete(id);
        organizationCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organizationCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/organizationCategorys/:query -> search for the organizationCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/organizationCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OrganizationCategory> searchOrganizationCategorys(@PathVariable String query) {
        return StreamSupport
            .stream(organizationCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /*check category exists by name*/
    @RequestMapping(value = "/organizationCategory/checkByName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkCategoryByName(@RequestParam String value) {

        OrganizationCategory organizationCategory = organizationCategoryRepository.findOneByName(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(organizationCategory == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }

}
