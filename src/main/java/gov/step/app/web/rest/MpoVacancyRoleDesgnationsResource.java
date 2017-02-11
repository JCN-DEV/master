package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.MpoVacancyRoleDesgnations;
import gov.step.app.repository.MpoVacancyRoleDesgnationsRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.MpoVacancyRoleDesgnationsSearchRepository;
import gov.step.app.security.SecurityUtils;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MpoVacancyRoleDesgnations.
 */
@RestController
@RequestMapping("/api")
public class MpoVacancyRoleDesgnationsResource {

    private final Logger log = LoggerFactory.getLogger(MpoVacancyRoleDesgnationsResource.class);

    @Inject
    private MpoVacancyRoleDesgnationsRepository mpoVacancyRoleDesgnationsRepository;

    @Inject
    private MpoVacancyRoleDesgnationsSearchRepository mpoVacancyRoleDesgnationsSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /mpoVacancyRoleDesgnationss -> Create a new mpoVacancyRoleDesgnations.
     */
    @RequestMapping(value = "/mpoVacancyRoleDesgnationss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoVacancyRoleDesgnations> createMpoVacancyRoleDesgnations(@RequestBody MpoVacancyRoleDesgnations mpoVacancyRoleDesgnations) throws URISyntaxException {
        log.debug("REST request to save MpoVacancyRoleDesgnations : {}", mpoVacancyRoleDesgnations);
        if (mpoVacancyRoleDesgnations.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoVacancyRoleDesgnations cannot already have an ID").body(null);
        }
        mpoVacancyRoleDesgnations.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        mpoVacancyRoleDesgnations.setCrateDate(LocalDate.now());
        MpoVacancyRoleDesgnations result = mpoVacancyRoleDesgnationsRepository.save(mpoVacancyRoleDesgnations);
        mpoVacancyRoleDesgnationsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mpoVacancyRoleDesgnationss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoVacancyRoleDesgnations", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoVacancyRoleDesgnationss -> Updates an existing mpoVacancyRoleDesgnations.
     */
    @RequestMapping(value = "/mpoVacancyRoleDesgnationss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoVacancyRoleDesgnations> updateMpoVacancyRoleDesgnations(@RequestBody MpoVacancyRoleDesgnations mpoVacancyRoleDesgnations) throws URISyntaxException {
        log.debug("REST request to update MpoVacancyRoleDesgnations : {}", mpoVacancyRoleDesgnations);
        if (mpoVacancyRoleDesgnations.getId() == null) {
            return createMpoVacancyRoleDesgnations(mpoVacancyRoleDesgnations);
        }
        mpoVacancyRoleDesgnations.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        mpoVacancyRoleDesgnations.setUpdateDate(LocalDate.now());
        MpoVacancyRoleDesgnations result = mpoVacancyRoleDesgnationsRepository.save(mpoVacancyRoleDesgnations);
        mpoVacancyRoleDesgnationsSearchRepository.save(mpoVacancyRoleDesgnations);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoVacancyRoleDesgnations", mpoVacancyRoleDesgnations.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mpoVacancyRoleDesgnationss -> get all the mpoVacancyRoleDesgnationss.
     */
    @RequestMapping(value = "/mpoVacancyRoleDesgnationss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoVacancyRoleDesgnations>> getAllMpoVacancyRoleDesgnationss(Pageable pageable)
        throws URISyntaxException {
        Page<MpoVacancyRoleDesgnations> page = mpoVacancyRoleDesgnationsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoVacancyRoleDesgnationss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

  /**
     * GET  /mpoVacancyRoleDesgnations/mpoVacancyRole/id -> get all the mpoVacancyRoleDesgnationss.
     */
    @RequestMapping(value = "/mpoVacancyRoleDesgnationss/mpoVacancyRoles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoVacancyRoleDesgnations>> getAllMpoVacancyRoleDesgnationsByRoleId(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<MpoVacancyRoleDesgnations> page = mpoVacancyRoleDesgnationsRepository.findByMpoVacancyRole(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoVacancyRoleDesgnationss/mpoVacancyRoles/"+id);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoVacancyRoleDesgnationss/:id -> get the "id" mpoVacancyRoleDesgnations.
     */
    @RequestMapping(value = "/mpoVacancyRoleDesgnationss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoVacancyRoleDesgnations> getMpoVacancyRoleDesgnations(@PathVariable Long id) {
        log.debug("REST request to get MpoVacancyRoleDesgnations : {}", id);
        return Optional.ofNullable(mpoVacancyRoleDesgnationsRepository.findOne(id))
            .map(mpoVacancyRoleDesgnations -> new ResponseEntity<>(
                mpoVacancyRoleDesgnations,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mpoVacancyRoleDesgnationss/:id -> delete the "id" mpoVacancyRoleDesgnations.
     */
    @RequestMapping(value = "/mpoVacancyRoleDesgnationss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoVacancyRoleDesgnations(@PathVariable Long id) {
        log.debug("REST request to delete MpoVacancyRoleDesgnations : {}", id);
        mpoVacancyRoleDesgnationsRepository.delete(id);
        mpoVacancyRoleDesgnationsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoVacancyRoleDesgnations", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoVacancyRoleDesgnationss/:query -> search for the mpoVacancyRoleDesgnations corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoVacancyRoleDesgnationss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoVacancyRoleDesgnations> searchMpoVacancyRoleDesgnationss(@PathVariable String query) {
        return StreamSupport
            .stream(mpoVacancyRoleDesgnationsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
