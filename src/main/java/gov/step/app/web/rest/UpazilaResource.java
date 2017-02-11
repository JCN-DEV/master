package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Upazila;
import gov.step.app.repository.UpazilaRepository;
import gov.step.app.repository.search.UpazilaSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Upazila.
 */
@RestController
@RequestMapping("/api")
public class UpazilaResource {

    private final Logger log = LoggerFactory.getLogger(UpazilaResource.class);

    @Inject
    private UpazilaRepository upazilaRepository;

    @Inject
    private UpazilaSearchRepository upazilaSearchRepository;

    /**
     * POST /upazilas -> Create a new upazila.
     */
    @RequestMapping(value = "/upazilas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Upazila> createUpazila(@Valid @RequestBody Upazila upazila) throws URISyntaxException {
        log.debug("REST request to save Upazila : {}", upazila);
        if (upazila.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new upazila cannot already have an ID").body(null);
        }
        Upazila result = upazilaRepository.save(upazila);
        upazilaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/upazilas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("upazila", result.getId().toString())).body(result);
    }

    /**
     * PUT /upazilas -> Updates an existing upazila.
     */
    @RequestMapping(value = "/upazilas", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Upazila> updateUpazila(@Valid @RequestBody Upazila upazila) throws URISyntaxException {
        log.debug("REST request to update Upazila : {}", upazila);
        if (upazila.getId() == null) {
            return createUpazila(upazila);
        }
        Upazila result = upazilaRepository.save(upazila);
        upazilaSearchRepository.save(upazila);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("upazila", upazila.getId().toString()))
            .body(result);
    }

    /**
     * GET /upazilas -> get all the upazilas.
     */
    @RequestMapping(value = "/upazilas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Upazila>> getAllUpazilas(Pageable pageable) throws URISyntaxException {
        Page<Upazila> page = upazilaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/upazilas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /upazilas -> get all the upazilas.
     */
    @RequestMapping(value = "/upazilas/district/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Upazila>> getAllUpazilasByDistrict(Pageable pageable, @PathVariable Long id) throws URISyntaxException {
        Page<Upazila> page = upazilaRepository.findUpazilasByDistrictIdOrderByName(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/upazilas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /upazilas/:id -> get the "id" upazila.
     */
    @RequestMapping(value = "/upazilas/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Upazila> getUpazila(@PathVariable Long id) {
        log.debug("REST request to get Upazila : {}", id);
        return Optional.ofNullable(upazilaRepository.findOne(id))
            .map(upazila -> new ResponseEntity<>(upazila, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /upazilas/:id -> delete the "id" upazila.
     */
    @RequestMapping(value = "/upazilas/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUpazila(@PathVariable Long id) {
        log.debug("REST request to delete Upazila : {}", id);
        upazilaRepository.delete(id);
        upazilaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("upazila", id.toString())).build();
    }

    /**
     * SEARCH /_search/upazilas/:query -> search for the upazila corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/upazilas/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Upazila> searchUpazilas(@PathVariable String query) {
        return StreamSupport.stream(upazilaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
