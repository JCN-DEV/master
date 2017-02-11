package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.MpoVacancyRole;
import gov.step.app.repository.MpoVacancyRoleRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.MpoVacancyRoleSearchRepository;
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
 * REST controller for managing MpoVacancyRole.
 */
@RestController
@RequestMapping("/api")
public class MpoVacancyRoleResource {

    private final Logger log = LoggerFactory.getLogger(MpoVacancyRoleResource.class);

    @Inject
    private MpoVacancyRoleRepository mpoVacancyRoleRepository;

    @Inject
    private MpoVacancyRoleSearchRepository mpoVacancyRoleSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /mpoVacancyRoles -> Create a new mpoVacancyRole.
     */
    @RequestMapping(value = "/mpoVacancyRoles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoVacancyRole> createMpoVacancyRole(@RequestBody MpoVacancyRole mpoVacancyRole) throws URISyntaxException {
        log.debug("REST request to save MpoVacancyRole : {}", mpoVacancyRole);
        if (mpoVacancyRole.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoVacancyRole cannot already have an ID").body(null);
        }
        mpoVacancyRole.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        mpoVacancyRole.setCrateDate(LocalDate.now());
        MpoVacancyRole result = mpoVacancyRoleRepository.save(mpoVacancyRole);
        mpoVacancyRoleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mpoVacancyRoles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoVacancyRole", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoVacancyRoles -> Updates an existing mpoVacancyRole.
     */
    @RequestMapping(value = "/mpoVacancyRoles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoVacancyRole> updateMpoVacancyRole(@RequestBody MpoVacancyRole mpoVacancyRole) throws URISyntaxException {
        log.debug("REST request to update MpoVacancyRole : {}", mpoVacancyRole);
        if (mpoVacancyRole.getId() == null) {
            return createMpoVacancyRole(mpoVacancyRole);
        }
        mpoVacancyRole.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        mpoVacancyRole.setUpdateDate(LocalDate.now());
        MpoVacancyRole result = mpoVacancyRoleRepository.save(mpoVacancyRole);
        mpoVacancyRoleSearchRepository.save(mpoVacancyRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoVacancyRole", mpoVacancyRole.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mpoVacancyRoles -> get all the mpoVacancyRoles.
     */
    @RequestMapping(value = "/mpoVacancyRoles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoVacancyRole>> getAllMpoVacancyRoles(Pageable pageable)
        throws URISyntaxException {
        Page<MpoVacancyRole> page = mpoVacancyRoleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoVacancyRoles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoVacancyRoles/:id -> get the "id" mpoVacancyRole.
     */
    @RequestMapping(value = "/mpoVacancyRoles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoVacancyRole> getMpoVacancyRole(@PathVariable Long id) {
        log.debug("REST request to get MpoVacancyRole : {}", id);
        return Optional.ofNullable(mpoVacancyRoleRepository.findOne(id))
            .map(mpoVacancyRole -> new ResponseEntity<>(
                mpoVacancyRole,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mpoVacancyRoles/:id -> delete the "id" mpoVacancyRole.
     */
    @RequestMapping(value = "/mpoVacancyRoles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoVacancyRole(@PathVariable Long id) {
        log.debug("REST request to delete MpoVacancyRole : {}", id);
        mpoVacancyRoleRepository.delete(id);
        mpoVacancyRoleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoVacancyRole", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoVacancyRoles/:query -> search for the mpoVacancyRole corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoVacancyRoles/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoVacancyRole> searchMpoVacancyRoles(@PathVariable String query) {
        return StreamSupport
            .stream(mpoVacancyRoleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
