package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.OrganizationCategory;
import gov.step.app.domain.OrganizationType;
import gov.step.app.repository.OrganizationTypeRepository;
import gov.step.app.repository.search.OrganizationTypeSearchRepository;
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
 * REST controller for managing OrganizationType.
 */
@RestController
@RequestMapping("/api")
public class OrganizationTypeResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationTypeResource.class);

    @Inject
    private OrganizationTypeRepository organizationTypeRepository;

    @Inject
    private OrganizationTypeSearchRepository organizationTypeSearchRepository;

    /**
     * POST  /organizationTypes -> Create a new organizationType.
     */
    @RequestMapping(value = "/organizationTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationType> createOrganizationType(@Valid @RequestBody OrganizationType organizationType) throws URISyntaxException {
        log.debug("REST request to save OrganizationType : {}", organizationType);
        if (organizationType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new organizationType cannot already have an ID").body(null);
        }
        OrganizationType result = organizationTypeRepository.save(organizationType);
        organizationTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/organizationTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("organizationType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organizationTypes -> Updates an existing organizationType.
     */
    @RequestMapping(value = "/organizationTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationType> updateOrganizationType(@Valid @RequestBody OrganizationType organizationType) throws URISyntaxException {
        log.debug("REST request to update OrganizationType : {}", organizationType);
        if (organizationType.getId() == null) {
            return createOrganizationType(organizationType);
        }
        OrganizationType result = organizationTypeRepository.save(organizationType);
        organizationTypeSearchRepository.save(organizationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("organizationType", organizationType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organizationTypes -> get all the organizationTypes.
     */
    @RequestMapping(value = "/organizationTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrganizationType>> getAllOrganizationTypes(Pageable pageable)
        throws URISyntaxException {
        Page<OrganizationType> page = organizationTypeRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizationTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /organizationTypes/:id -> get the "id" organizationType.
     */
    @RequestMapping(value = "/organizationTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationType> getOrganizationType(@PathVariable Long id) {
        log.debug("REST request to get OrganizationType : {}", id);
        return Optional.ofNullable(organizationTypeRepository.findOne(id))
            .map(organizationType -> new ResponseEntity<>(
                organizationType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /organizationTypes/:id -> delete the "id" organizationType.
     */
    @RequestMapping(value = "/organizationTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOrganizationType(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationType : {}", id);
        organizationTypeRepository.delete(id);
        organizationTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organizationType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/organizationTypes/:query -> search for the organizationType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/organizationTypes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OrganizationType> searchOrganizationTypes(@PathVariable String query) {
        return StreamSupport
            .stream(organizationTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }



    /**
     * GET  /organizationCategorys -> get all the organizationCategorys.
     */
    @RequestMapping(value = "/organizationTypes/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrganizationType>> getAllActiveOrganizationType(Pageable pageable)
        throws URISyntaxException {
        Page<OrganizationType> page = organizationTypeRepository.findAllActiveOrgType(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizationCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /*check organization type exists by name*/
    @RequestMapping(value = "/organizationType/checkByName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkOrgTypeByName(@RequestParam String value) {

        OrganizationType organizationType = organizationTypeRepository.findOneByName(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(organizationType == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }
}
