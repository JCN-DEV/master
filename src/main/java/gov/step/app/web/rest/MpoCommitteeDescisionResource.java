package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.MpoApplication;
import gov.step.app.domain.MpoCommitteeDescision;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.MpoApplicationRepository;
import gov.step.app.repository.MpoCommitteeDescisionRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.MpoCommitteeDescisionSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MpoCommitteeDescision.
 */
@RestController
@RequestMapping("/api")
public class MpoCommitteeDescisionResource {

    private final Logger log = LoggerFactory.getLogger(MpoCommitteeDescisionResource.class);

    @Inject
    private MpoCommitteeDescisionRepository mpoCommitteeDescisionRepository;

    @Inject
    private MpoCommitteeDescisionSearchRepository mpoCommitteeDescisionSearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MpoApplicationRepository mpoApplicationRepository;

    /**
     * POST  /mpoCommitteeDescisions -> Create a new mpoCommitteeDescision.
     */
    @RequestMapping(value = "/mpoCommitteeDescisions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteeDescision> createMpoCommitteeDescision(@RequestBody MpoCommitteeDescision mpoCommitteeDescision) throws URISyntaxException {
        log.debug("REST request to save MpoCommitteeDescision : {}", mpoCommitteeDescision);
        if (mpoCommitteeDescision.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoCommitteeDescision cannot already have an ID").body(null);
        }
        SecurityUtils.getCurrentUserId();
        mpoCommitteeDescision.setUser(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        MpoCommitteeDescision result = mpoCommitteeDescisionRepository.save(mpoCommitteeDescision);
        mpoCommitteeDescisionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mpoCommitteeDescisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoCommitteeDescision", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoCommitteeDescisions -> Updates an existing mpoCommitteeDescision.
     */
    @RequestMapping(value = "/mpoCommitteeDescisions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteeDescision> updateMpoCommitteeDescision(@RequestBody MpoCommitteeDescision mpoCommitteeDescision) throws URISyntaxException {
        log.debug("REST request to update MpoCommitteeDescision : {}", mpoCommitteeDescision);
        if (mpoCommitteeDescision.getId() == null) {
            return createMpoCommitteeDescision(mpoCommitteeDescision);
        }
        MpoCommitteeDescision result = mpoCommitteeDescisionRepository.save(mpoCommitteeDescision);
        mpoCommitteeDescisionSearchRepository.save(mpoCommitteeDescision);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoCommitteeDescision", mpoCommitteeDescision.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mpoCommitteeDescisions -> get all the mpoCommitteeDescisions.
     */
    @RequestMapping(value = "/mpoCommitteeDescisions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoCommitteeDescision>> getAllMpoCommitteeDescisions(Pageable pageable)
        throws URISyntaxException {
        Page<MpoCommitteeDescision> page = mpoCommitteeDescisionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoCommitteeDescisions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoCommitteeDescisions/:id -> get the "id" mpoCommitteeDescision.
     */
    @RequestMapping(value = "/mpoCommitteeDescisions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteeDescision> getMpoCommitteeDescision(@PathVariable Long id) {
        log.debug("REST request to get MpoCommitteeDescision : {}", id);
        return Optional.ofNullable(mpoCommitteeDescisionRepository.findOne(id))
            .map(mpoCommitteeDescision -> new ResponseEntity<>(
                mpoCommitteeDescision,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /mpoCommitteeDescisions/:id -> get the "id" mpoCommitteeDescision.
     */
    @RequestMapping(value = "/mpoCommitteeDescisions/current/{mpoApplicationid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public MpoCommitteeDescision getMpoCommitteeDescisionByCurrentUserAndMpoApplication(@PathVariable Long mpoApplicationid) {
        log.debug("REST request to get MpoCommitteeDescision mpo application id: {}", mpoApplicationid);
        return mpoCommitteeDescisionRepository.findDescisionByApplicationAndMember(mpoApplicationid, SecurityUtils.getCurrentUserId());

    }
    /**
     * GET  /mpoCommitteeDescisions/:id -> get the "id" mpoCommitteeDescision.
     */
    @RequestMapping(value = "/mpoCommitteeDescisions/mpoApplication/{mpoApplicationid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoCommitteeDescision> getMpoCommitteeDescisionByMpoApplication(@PathVariable Long mpoApplicationid) {
        log.debug("REST request to get MpoCommitteeDescision mpo application id: {}", mpoApplicationid);
        return mpoCommitteeDescisionRepository.findDescisionByApplication(mpoApplicationid);

    }

    /**
     * GET  /mpoCommitteeDescisions/:id -> get the "id" mpoCommitteeDescision.
     */
    @RequestMapping(value = "/mpoCommitteeDescisions/currentLogin",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoCommitteeDescision> getMpoCommitteeDescisionByUser() {
        log.debug("REST request to get MpoCommitteeDescisions by current user");
        List<MpoApplication> mpoApplications = mpoApplicationRepository.getMpoListByStatus(MpoApplicationStatusType.COMPLITE.getCode());

        return mpoCommitteeDescisionRepository.findDescisionsByCurrentUser();

    }


    /**
     * DELETE  /mpoCommitteeDescisions/:id -> delete the "id" mpoCommitteeDescision.
     */
    @RequestMapping(value = "/mpoCommitteeDescisions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoCommitteeDescision(@PathVariable Long id) {
        log.debug("REST request to delete MpoCommitteeDescision : {}", id);
        mpoCommitteeDescisionRepository.delete(id);
        mpoCommitteeDescisionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoCommitteeDescision", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoCommitteeDescisions/:query -> search for the mpoCommitteeDescision corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoCommitteeDescisions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoCommitteeDescision> searchMpoCommitteeDescisions(@PathVariable String query) {
        return StreamSupport
            .stream(mpoCommitteeDescisionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
