package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.MpoCommitteePersonInfo;
import gov.step.app.repository.MpoCommitteePersonInfoRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.MpoCommitteePersonInfoSearchRepository;
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
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MpoCommitteePersonInfo.
 */
@RestController
@RequestMapping("/api")
public class MpoCommitteePersonInfoResource {

    private final Logger log = LoggerFactory.getLogger(MpoCommitteePersonInfoResource.class);

    @Inject
    private MpoCommitteePersonInfoRepository mpoCommitteePersonInfoRepository;

    @Inject
    private MpoCommitteePersonInfoSearchRepository mpoCommitteePersonInfoSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /mpoCommitteePersonInfos -> Create a new mpoCommitteePersonInfo.
     */
    @RequestMapping(value = "/mpoCommitteePersonInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteePersonInfo> createMpoCommitteePersonInfo(@Valid @RequestBody MpoCommitteePersonInfo mpoCommitteePersonInfo) throws URISyntaxException {
        log.debug("REST request to save MpoCommitteePersonInfo : {}", mpoCommitteePersonInfo);
        if (mpoCommitteePersonInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoCommitteePersonInfo cannot already have an ID").body(null);
        }
        mpoCommitteePersonInfo.setDateCrated(LocalDate.now());
        mpoCommitteePersonInfo.setDateModified(LocalDate.now());
        //mpoCommitteePersonInfo.setActivated(true);
        mpoCommitteePersonInfo.setCreatedBy(userRepository.findOne(SecurityUtils.getCurrentUser().getId()));
        MpoCommitteePersonInfo result = mpoCommitteePersonInfoRepository.save(mpoCommitteePersonInfo);
        mpoCommitteePersonInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mpoCommitteePersonInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoCommitteePersonInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoCommitteePersonInfos -> Updates an existing mpoCommitteePersonInfo.
     */
    @RequestMapping(value = "/mpoCommitteePersonInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteePersonInfo> updateMpoCommitteePersonInfo(@Valid @RequestBody MpoCommitteePersonInfo mpoCommitteePersonInfo) throws URISyntaxException {
        log.debug("REST request to update MpoCommitteePersonInfo : {}", mpoCommitteePersonInfo);
        if (mpoCommitteePersonInfo.getId() == null) {
            return createMpoCommitteePersonInfo(mpoCommitteePersonInfo);
        }
        MpoCommitteePersonInfo result = mpoCommitteePersonInfoRepository.save(mpoCommitteePersonInfo);
        mpoCommitteePersonInfoSearchRepository.save(mpoCommitteePersonInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoCommitteePersonInfo", mpoCommitteePersonInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mpoCommitteePersonInfos -> get all the mpoCommitteePersonInfos.
     */
    @RequestMapping(value = "/mpoCommitteePersonInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoCommitteePersonInfo>> getAllMpoCommitteePersonInfos(Pageable pageable)
        throws URISyntaxException {
        //Page<MpoCommitteePersonInfo> page = mpoCommitteePersonInfoRepository.findAll(pageable);
        Page<MpoCommitteePersonInfo> page = mpoCommitteePersonInfoRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoCommitteePersonInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoCommitteePersonInfos -> get all the mpoCommitteePersonInfos.
     */
    @RequestMapping(value = "/mpoCommitteePersonInfos/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoCommitteePersonInfo>> getAllActiveMpoCommitteePersonInfos(Pageable pageable)
        throws URISyntaxException {
        Page<MpoCommitteePersonInfo> page = mpoCommitteePersonInfoRepository.findByActivated(true,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoCommitteePersonInfos/active");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoCommitteePersonInfos/:id -> get the "id" mpoCommitteePersonInfo.
     */
    @RequestMapping(value = "/mpoCommitteePersonInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteePersonInfo> getMpoCommitteePersonInfo(@PathVariable Long id) {
        log.debug("REST request to get MpoCommitteePersonInfo : {}", id);
        return Optional.ofNullable(mpoCommitteePersonInfoRepository.findOne(id))
            .map(mpoCommitteePersonInfo -> new ResponseEntity<>(
                mpoCommitteePersonInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * GET  /mpoCommitteePersonInfos/:email -> get the "email" mpoCommitteePersonInfo.
     */
    @RequestMapping(value = "/mpoCommitteePersonInfos/email/{email:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteePersonInfo> getMpoCommitteePersonInfoByEmail(@PathVariable String email) throws UnsupportedEncodingException {
        log.debug("REST request to get MpoCommitteePersonInfo by email: {}", email);
        return Optional.ofNullable(mpoCommitteePersonInfoRepository.findPersonByEmail(email))
            .map(mpoCommitteePersonInfo -> new ResponseEntity<>(
                mpoCommitteePersonInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mpoCommitteePersonInfos/:id -> delete the "id" mpoCommitteePersonInfo.
     */
    @RequestMapping(value = "/mpoCommitteePersonInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoCommitteePersonInfo(@PathVariable Long id) {
        log.debug("REST request to delete MpoCommitteePersonInfo : {}", id);
        mpoCommitteePersonInfoRepository.delete(id);
        mpoCommitteePersonInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoCommitteePersonInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoCommitteePersonInfos/:query -> search for the mpoCommitteePersonInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoCommitteePersonInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoCommitteePersonInfo> searchMpoCommitteePersonInfos(@PathVariable String query) {
        return StreamSupport
            .stream(mpoCommitteePersonInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
