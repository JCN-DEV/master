package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SmsServiceType;
import gov.step.app.repository.SmsServiceTypeRepository;
import gov.step.app.repository.search.SmsServiceTypeSearchRepository;
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
 * REST controller for managing SmsServiceType.
 */
@RestController
@RequestMapping("/api")
public class SmsServiceTypeResource {

    private final Logger log = LoggerFactory.getLogger(SmsServiceTypeResource.class);

    @Inject
    private SmsServiceTypeRepository smsServiceTypeRepository;

    @Inject
    private SmsServiceTypeSearchRepository smsServiceTypeSearchRepository;

    /**
     * POST  /smsServiceTypes -> Create a new smsServiceType.
     */
    @RequestMapping(value = "/smsServiceTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceType> createSmsServiceType(@Valid @RequestBody SmsServiceType smsServiceType) throws URISyntaxException {
        log.debug("REST request to save SmsServiceType : {}", smsServiceType);
        if (smsServiceType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("smsServiceType", "idexists", "A new smsServiceType cannot already have an ID")).body(null);
        }
        SmsServiceType result = smsServiceTypeRepository.save(smsServiceType);
        smsServiceTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/smsServiceTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("smsServiceType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /smsServiceTypes -> Updates an existing smsServiceType.
     */
    @RequestMapping(value = "/smsServiceTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceType> updateSmsServiceType(@Valid @RequestBody SmsServiceType smsServiceType) throws URISyntaxException {
        log.debug("REST request to update SmsServiceType : {}", smsServiceType);
        if (smsServiceType.getId() == null) {
            return createSmsServiceType(smsServiceType);
        }
        SmsServiceType result = smsServiceTypeRepository.save(smsServiceType);
        smsServiceTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("smsServiceType", smsServiceType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /smsServiceTypes -> get all the smsServiceTypes.
     */
    @RequestMapping(value = "/smsServiceTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SmsServiceType>> getAllSmsServiceTypes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SmsServiceTypes");
        Page<SmsServiceType> page = smsServiceTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/smsServiceTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /smsServiceTypes/:id -> get the "id" smsServiceType.
     */
    @RequestMapping(value = "/smsServiceTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceType> getSmsServiceType(@PathVariable Long id) {
        log.debug("REST request to get SmsServiceType : {}", id);
        SmsServiceType smsServiceType = smsServiceTypeRepository.findOne(id);
        return Optional.ofNullable(smsServiceType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /smsServiceTypelist/:stat -> get the all service type by active status.
     */
    @RequestMapping(value = "/smsServiceTypesByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed

    public List<SmsServiceType> getAllServiceTypeByActiveStatus(@PathVariable boolean stat)
        throws URISyntaxException {
        log.debug("REST all service types by status : {}", stat);
        List<SmsServiceType> serviceTypes = smsServiceTypeRepository.findAllByActiveStatus(stat);
        return  serviceTypes;

    }

    /**
     * DELETE  /smsServiceTypes/:id -> delete the "id" smsServiceType.
     */
    @RequestMapping(value = "/smsServiceTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSmsServiceType(@PathVariable Long id) {
        log.debug("REST request to delete SmsServiceType : {}", id);
        smsServiceTypeRepository.delete(id);
        smsServiceTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("smsServiceType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/smsServiceTypes/:query -> search for the smsServiceType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/smsServiceTypes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceType> searchSmsServiceTypes(@PathVariable String query) {
        log.debug("REST request to search SmsServiceTypes for query {}", query);
        return StreamSupport
            .stream(smsServiceTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
