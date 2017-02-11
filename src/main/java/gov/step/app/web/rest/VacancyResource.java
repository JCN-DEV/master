package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Vacancy;
import gov.step.app.repository.VacancyRepository;
import gov.step.app.repository.search.VacancySearchRepository;
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
 * REST controller for managing Vacancy.
 */
@RestController
@RequestMapping("/api")
public class VacancyResource {

    private final Logger log = LoggerFactory.getLogger(VacancyResource.class);

    @Inject
    private VacancyRepository vacancyRepository;

    @Inject
    private VacancySearchRepository vacancySearchRepository;

    /**
     * POST  /vacancys -> Create a new vacancy.
     */
    @RequestMapping(value = "/vacancys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Vacancy> createVacancy(@Valid @RequestBody Vacancy vacancy) throws URISyntaxException {
        log.debug("REST request to save Vacancy : {}", vacancy);
        if (vacancy.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new vacancy cannot already have an ID").body(null);
        }
        Vacancy result = vacancyRepository.save(vacancy);
        vacancySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/vacancys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vacancy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vacancys -> Updates an existing vacancy.
     */
    @RequestMapping(value = "/vacancys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Vacancy> updateVacancy(@Valid @RequestBody Vacancy vacancy) throws URISyntaxException {
        log.debug("REST request to update Vacancy : {}", vacancy);
        if (vacancy.getId() == null) {
            return createVacancy(vacancy);
        }
        Vacancy result = vacancyRepository.save(vacancy);
        vacancySearchRepository.save(vacancy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vacancy", vacancy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vacancys -> get all the vacancys.
     */
    @RequestMapping(value = "/vacancys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Vacancy>> getAllVacancys(Pageable pageable)
        throws URISyntaxException {
        Page<Vacancy> page = vacancyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vacancys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vacancys/:id -> get the "id" vacancy.
     */
    @RequestMapping(value = "/vacancys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Vacancy> getVacancy(@PathVariable Long id) {
        log.debug("REST request to get Vacancy : {}", id);
        return Optional.ofNullable(vacancyRepository.findOne(id))
            .map(vacancy -> new ResponseEntity<>(
                vacancy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vacancys/:id -> delete the "id" vacancy.
     */
    @RequestMapping(value = "/vacancys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        log.debug("REST request to delete Vacancy : {}", id);
        vacancyRepository.delete(id);
        vacancySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vacancy", id.toString())).build();
    }

    /**
     * SEARCH  /_search/vacancys/:query -> search for the vacancy corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/vacancys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Vacancy> searchVacancys(@PathVariable String query) {
        return StreamSupport
            .stream(vacancySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
