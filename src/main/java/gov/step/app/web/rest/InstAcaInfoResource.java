package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstAcaInfo;
import gov.step.app.domain.InstituteStatus;
import gov.step.app.repository.InstAcaInfoRepository;
import gov.step.app.repository.InstGenInfoRepository;
import gov.step.app.repository.InstituteStatusRepository;
import gov.step.app.repository.search.InstAcaInfoSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
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
 * REST controller for managing InstAcaInfo.
 */
@RestController
@RequestMapping("/api")
public class InstAcaInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstAcaInfoResource.class);

    @Inject
    private InstAcaInfoRepository instAcaInfoRepository;

    @Inject
    private InstAcaInfoSearchRepository instAcaInfoSearchRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    /**
     * POST  /instAcaInfos -> Create a new instAcaInfo.
     */
    @RequestMapping(value = "/instAcaInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAcaInfo> createInstAcaInfo(@RequestBody InstAcaInfo instAcaInfo) throws URISyntaxException {
        log.debug("REST request to save InstAcaInfo : {}", instAcaInfo);
        if (instAcaInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instAcaInfo cannot already have an ID").body(null);
        }
        InstAcaInfo result = instAcaInfoRepository.save(instAcaInfo);
        instAcaInfoSearchRepository.save(result);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setAcaInfo(1);
            instituteStatusRepository.save(instituteStatus);
        }

        return ResponseEntity.created(new URI("/api/instAcaInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instAcaInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instAcaInfos -> Updates an existing instAcaInfo.
     */
    @RequestMapping(value = "/instAcaInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAcaInfo> updateInstAcaInfo(@RequestBody InstAcaInfo instAcaInfo) throws URISyntaxException {
        log.debug("REST request to update InstAcaInfo : {}", instAcaInfo);
        if (instAcaInfo.getId() == null) {
            return createInstAcaInfo(instAcaInfo);
        }
        InstAcaInfo result = instAcaInfoRepository.save(instAcaInfo);
        instAcaInfoSearchRepository.save(instAcaInfo);

        if(SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")){
            InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
            if(instituteStatus != null){
                instituteStatus.setAcaInfo(1);
                instituteStatusRepository.save(instituteStatus);
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instAcaInfo", instAcaInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instAcaInfos -> get all the instAcaInfos.
     */
    @RequestMapping(value = "/instAcaInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstAcaInfo>> getAllInstAcaInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstAcaInfo> page = instAcaInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instAcaInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instAcaInfos/:id -> get the "id" instAcaInfo.
     */
    @RequestMapping(value = "/instAcaInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAcaInfo> getInstAcaInfo(@PathVariable Long id) {
        log.debug("REST request to get InstAcaInfo : {}", id);
        return Optional.ofNullable(instAcaInfoRepository.findOne(id))
            .map(instAcaInfo -> new ResponseEntity<>(
                instAcaInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instAcaInfos/:id -> delete the "id" instAcaInfo.
     */
    @RequestMapping(value = "/instAcaInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstAcaInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstAcaInfo : {}", id);
        instAcaInfoRepository.delete(id);
        instAcaInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instAcaInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instAcaInfos/:query -> search for the instAcaInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instAcaInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstAcaInfo> searchInstAcaInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instAcaInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
