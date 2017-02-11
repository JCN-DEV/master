package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InsAcademicInfoTemp;
import gov.step.app.repository.InsAcademicInfoTempRepository;
import gov.step.app.repository.search.InsAcademicInfoTempSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InsAcademicInfoTemp.
 */
@RestController
@RequestMapping("/api")
public class InsAcademicInfoTempResource {

    private final Logger log = LoggerFactory.getLogger(InsAcademicInfoTempResource.class);

    @Inject
    private InsAcademicInfoTempRepository insAcademicInfoTempRepository;

    @Inject
    private InsAcademicInfoTempSearchRepository insAcademicInfoTempSearchRepository;

    /**
     * POST  /insAcademicInfoTemps -> Create a new insAcademicInfoTemp.
     */
    @RequestMapping(value = "/insAcademicInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InsAcademicInfoTemp> createInsAcademicInfoTemp(@RequestBody InsAcademicInfoTemp insAcademicInfoTemp) throws URISyntaxException {
        log.debug("REST request to save InsAcademicInfoTemp : {}", insAcademicInfoTemp);
        if (insAcademicInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new insAcademicInfoTemp cannot already have an ID").body(null);
        }
        InsAcademicInfoTemp result = insAcademicInfoTempRepository.save(insAcademicInfoTemp);
        insAcademicInfoTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/insAcademicInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("insAcademicInfoTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insAcademicInfoTemps -> Updates an existing insAcademicInfoTemp.
     */
    @RequestMapping(value = "/insAcademicInfoTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InsAcademicInfoTemp> updateInsAcademicInfoTemp(@RequestBody InsAcademicInfoTemp insAcademicInfoTemp) throws URISyntaxException {
        log.debug("REST request to update InsAcademicInfoTemp : {}", insAcademicInfoTemp);
        if (insAcademicInfoTemp.getId() == null) {
            return createInsAcademicInfoTemp(insAcademicInfoTemp);
        }
        InsAcademicInfoTemp result = insAcademicInfoTempRepository.save(insAcademicInfoTemp);
        insAcademicInfoTempSearchRepository.save(insAcademicInfoTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("insAcademicInfoTemp", insAcademicInfoTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insAcademicInfoTemps -> get all the insAcademicInfoTemps.
     */
    @RequestMapping(value = "/insAcademicInfoTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InsAcademicInfoTemp>> getAllInsAcademicInfoTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InsAcademicInfoTemp> page = insAcademicInfoTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/insAcademicInfoTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /insAcademicInfoTemps/:id -> get the "id" insAcademicInfoTemp.
     */
    @RequestMapping(value = "/insAcademicInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InsAcademicInfoTemp> getInsAcademicInfoTemp(@PathVariable Long id) {
        log.debug("REST request to get InsAcademicInfoTemp : {}", id);
        return Optional.ofNullable(insAcademicInfoTempRepository.findOne(id))
            .map(insAcademicInfoTemp -> new ResponseEntity<>(
                insAcademicInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /insAcademicInfoTemps/:id -> delete the "id" insAcademicInfoTemp.
     */
    @RequestMapping(value = "/insAcademicInfoTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInsAcademicInfoTemp(@PathVariable Long id) {
        log.debug("REST request to delete InsAcademicInfoTemp : {}", id);
        insAcademicInfoTempRepository.delete(id);
        insAcademicInfoTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("insAcademicInfoTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/insAcademicInfoTemps/:query -> search for the insAcademicInfoTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/insAcademicInfoTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InsAcademicInfoTemp> searchInsAcademicInfoTemps(@PathVariable String query) {
        return StreamSupport
            .stream(insAcademicInfoTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
