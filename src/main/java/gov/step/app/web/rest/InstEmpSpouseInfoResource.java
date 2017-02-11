package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmpSpouseInfo;
import gov.step.app.domain.InstEmployee;
import gov.step.app.repository.InstEmpSpouseInfoRepository;
import gov.step.app.repository.InstEmployeeRepository;
import gov.step.app.repository.search.InstEmpSpouseInfoSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstEmpSpouseInfo.
 */
@RestController
@RequestMapping("/api")
public class InstEmpSpouseInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstEmpSpouseInfoResource.class);

    @Inject
    private InstEmpSpouseInfoRepository instEmpSpouseInfoRepository;

    @Inject
    private InstEmpSpouseInfoSearchRepository instEmpSpouseInfoSearchRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    /**
     * POST  /instEmpSpouseInfos -> Create a new instEmpSpouseInfo.
     */
    @RequestMapping(value = "/instEmpSpouseInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpSpouseInfo> createInstEmpSpouseInfo(@Valid @RequestBody InstEmpSpouseInfo instEmpSpouseInfo) throws URISyntaxException {
        log.debug("REST request to save InstEmpSpouseInfo : {}", instEmpSpouseInfo);
        if (instEmpSpouseInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmpSpouseInfo cannot already have an ID").body(null);
        }

        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        instEmpSpouseInfo.setInstEmployee(instEmployeeresult);
        InstEmpSpouseInfo result = instEmpSpouseInfoRepository.save(instEmpSpouseInfo);
        instEmpSpouseInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmpSpouseInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmpSpouseInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmpSpouseInfos -> Updates an existing instEmpSpouseInfo.
     */
    @RequestMapping(value = "/instEmpSpouseInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpSpouseInfo> updateInstEmpSpouseInfo(@Valid @RequestBody InstEmpSpouseInfo instEmpSpouseInfo) throws URISyntaxException {
        log.debug("REST request to update InstEmpSpouseInfo : {}", instEmpSpouseInfo);
        if (instEmpSpouseInfo.getId() == null) {
            return createInstEmpSpouseInfo(instEmpSpouseInfo);
        }
        InstEmpSpouseInfo result = instEmpSpouseInfoRepository.save(instEmpSpouseInfo);
        instEmpSpouseInfoSearchRepository.save(instEmpSpouseInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmpSpouseInfo", instEmpSpouseInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmpSpouseInfos -> get all the instEmpSpouseInfos.
     */
    @RequestMapping(value = "/instEmpSpouseInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmpSpouseInfo>> getAllInstEmpSpouseInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmpSpouseInfo> page = instEmpSpouseInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmpSpouseInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmpSpouseInfos/:id -> get the "id" instEmpSpouseInfo.
     */
    @RequestMapping(value = "/instEmpSpouseInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpSpouseInfo> getInstEmpSpouseInfo(@PathVariable Long id) {
        log.debug("REST request to get InstEmpSpouseInfo : {}", id);
        return Optional.ofNullable(instEmpSpouseInfoRepository.findOne(id))
            .map(instEmpSpouseInfo -> new ResponseEntity<>(
                instEmpSpouseInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * GET  /instEmpSpouseInfo/current ->
     */
    @RequestMapping(value = "/instEmpSpouseInfo/current",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpSpouseInfo> getInstEmpSpouseInfo() {
        log.debug("REST request to get InstEmpSpouseInfo : {}");
        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        List<Map<String, Object>> instEmplExperiences=null;
        return Optional.ofNullable(instEmpSpouseInfoRepository.findByInstEmployeeId(instEmployeeresult.getId()))
            .map(instEmpSpouseInfo -> new ResponseEntity<>(
                instEmpSpouseInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instEmpSpouseInfos/:id -> delete the "id" instEmpSpouseInfo.
     */
    @RequestMapping(value = "/instEmpSpouseInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmpSpouseInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstEmpSpouseInfo : {}", id);
        instEmpSpouseInfoRepository.delete(id);
        instEmpSpouseInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmpSpouseInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmpSpouseInfos/:query -> search for the instEmpSpouseInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmpSpouseInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmpSpouseInfo> searchInstEmpSpouseInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instEmpSpouseInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
