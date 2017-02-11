package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.RisAppFormEduQ;
import gov.step.app.repository.RisAppFormEduQRepository;
import gov.step.app.repository.search.RisAppFormEduQSearchRepository;
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
 * REST controller for managing RisAppFormEduQ.
 */
@RestController
@RequestMapping("/api")
public class RisAppFormEduQResource {

    private final Logger log = LoggerFactory.getLogger(RisAppFormEduQResource.class);

    @Inject
    private RisAppFormEduQRepository risAppFormEduQRepository;

    @Inject
    private RisAppFormEduQSearchRepository risAppFormEduQSearchRepository;

    /**
     * POST  /risAppFormEduQs -> Create a new risAppFormEduQ.
     */
    @RequestMapping(value = "/risAppFormEduQs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisAppFormEduQ> createRisAppFormEduQ(@Valid @RequestBody RisAppFormEduQ risAppFormEduQ) throws URISyntaxException {
        log.debug("REST request to save RisAppFormEduQ : {}", risAppFormEduQ);
        if (risAppFormEduQ.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new risAppFormEduQ cannot already have an ID").body(null);
        }
        RisAppFormEduQ result = risAppFormEduQRepository.save(risAppFormEduQ);
        risAppFormEduQSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/risAppFormEduQs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("risAppFormEduQ", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /risAppFormEduQs -> Updates an existing risAppFormEduQ.
     */
    @RequestMapping(value = "/risAppFormEduQs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisAppFormEduQ> updateRisAppFormEduQ(@Valid @RequestBody RisAppFormEduQ risAppFormEduQ) throws URISyntaxException {
        log.debug("REST request to update RisAppFormEduQ : {}", risAppFormEduQ);
        if (risAppFormEduQ.getId() == null) {
            return createRisAppFormEduQ(risAppFormEduQ);
        }
        RisAppFormEduQ result = risAppFormEduQRepository.save(risAppFormEduQ);
        risAppFormEduQSearchRepository.save(risAppFormEduQ);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("risAppFormEduQ", risAppFormEduQ.getId().toString()))
            .body(result);
    }

    /**
     * GET  /risAppFormEduQs -> get all the risAppFormEduQs.
     */
    @RequestMapping(value = "/risAppFormEduQs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RisAppFormEduQ>> getAllRisAppFormEduQs(Pageable pageable)
        throws URISyntaxException {
        Page<RisAppFormEduQ> page = risAppFormEduQRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/risAppFormEduQs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /risAppFormEduQs/:id -> get the "id" risAppFormEduQ.
     */
    @RequestMapping(value = "/risAppFormEduQs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisAppFormEduQ> getRisAppFormEduQ(@PathVariable Long id) {
        log.debug("REST request to get RisAppFormEduQ : {}", id);
        return Optional.ofNullable(risAppFormEduQRepository.findOne(id))
            .map(risAppFormEduQ -> new ResponseEntity<>(
                risAppFormEduQ,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /risAppFormEduQs/:id -> delete the "id" risAppFormEduQ.
     */
    @RequestMapping(value = "/risAppFormEduQs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRisAppFormEduQ(@PathVariable Long id) {
        log.debug("REST request to delete RisAppFormEduQ : {}", id);
        risAppFormEduQRepository.delete(id);
        risAppFormEduQSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("risAppFormEduQ", id.toString())).build();
    }

    /**
     * SEARCH  /_search/risAppFormEduQs/:query -> search for the risAppFormEduQ corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/risAppFormEduQs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RisAppFormEduQ> searchRisAppFormEduQs(@PathVariable String query) {
        return StreamSupport
            .stream(risAppFormEduQSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

//getting eduacation by id risnewappformid
    @RequestMapping(value = "/risAppFormEduQs/getEducations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RisAppFormEduQ> getAllEducation(@PathVariable Long id) {
        return risAppFormEduQRepository.getAllEducationByAppId(id);
    }

}
