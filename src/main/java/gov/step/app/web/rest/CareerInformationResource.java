package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CareerInformation;
import gov.step.app.repository.CareerInformationRepository;
import gov.step.app.repository.search.CareerInformationSearchRepository;
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
 * REST controller for managing CareerInformation.
 */
@RestController
@RequestMapping("/api")
public class CareerInformationResource {

    private final Logger log = LoggerFactory.getLogger(CareerInformationResource.class);

    @Inject
    private CareerInformationRepository careerInformationRepository;

    @Inject
    private CareerInformationSearchRepository careerInformationSearchRepository;

    /**
     * POST  /careerInformations -> Create a new careerInformation.
     */
    @RequestMapping(value = "/careerInformations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CareerInformation> createCareerInformation(@Valid @RequestBody CareerInformation careerInformation) throws URISyntaxException {
        log.debug("REST request to save CareerInformation : {}", careerInformation);
        if (careerInformation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new careerInformation cannot already have an ID").body(null);
        }
        CareerInformation result = careerInformationRepository.save(careerInformation);
        careerInformationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/careerInformations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("careerInformation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /careerInformations -> Updates an existing careerInformation.
     */
    @RequestMapping(value = "/careerInformations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CareerInformation> updateCareerInformation(@Valid @RequestBody CareerInformation careerInformation) throws URISyntaxException {
        log.debug("REST request to update CareerInformation : {}", careerInformation);
        if (careerInformation.getId() == null) {
            return createCareerInformation(careerInformation);
        }
        CareerInformation result = careerInformationRepository.save(careerInformation);
        careerInformationSearchRepository.save(careerInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("careerInformation", careerInformation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /careerInformations -> get all the careerInformations.
     */
    @RequestMapping(value = "/careerInformations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CareerInformation>> getAllCareerInformations(Pageable pageable)
        throws URISyntaxException {
        Page<CareerInformation> page = careerInformationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/careerInformations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /careerInformations/:id -> get the "id" careerInformation.
     */
    @RequestMapping(value = "/careerInformations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CareerInformation> getCareerInformation(@PathVariable Long id) {
        log.debug("REST request to get CareerInformation : {}", id);
        return Optional.ofNullable(careerInformationRepository.findOne(id))
            .map(careerInformation -> new ResponseEntity<>(
                careerInformation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /careerInformations/:id -> delete the "id" careerInformation.
     */
    @RequestMapping(value = "/careerInformations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCareerInformation(@PathVariable Long id) {
        log.debug("REST request to delete CareerInformation : {}", id);
        careerInformationRepository.delete(id);
        careerInformationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("careerInformation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/careerInformations/:query -> search for the careerInformation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/careerInformations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CareerInformation> searchCareerInformations(@PathVariable String query) {
        return StreamSupport
            .stream(careerInformationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
