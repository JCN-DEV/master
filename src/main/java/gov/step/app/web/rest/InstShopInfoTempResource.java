package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstShopInfoTemp;
import gov.step.app.domain.Institute;
import gov.step.app.domain.InstituteStatus;
import gov.step.app.repository.InstShopInfoTempRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.InstituteStatusRepository;
import gov.step.app.repository.search.InstShopInfoTempSearchRepository;
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
 * REST controller for managing InstShopInfoTemp.
 */
@RestController
@RequestMapping("/api")
public class InstShopInfoTempResource {

    private final Logger log = LoggerFactory.getLogger(InstShopInfoTempResource.class);

    @Inject
    private InstShopInfoTempRepository instShopInfoTempRepository;

    @Inject
    private InstShopInfoTempSearchRepository instShopInfoTempSearchRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    @Inject
    private InstituteRepository instituteRepository;

    /**
     * POST  /instShopInfoTemps -> Create a new instShopInfoTemp.
     */
    @RequestMapping(value = "/instShopInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstShopInfoTemp> createInstShopInfoTemp(@RequestBody InstShopInfoTemp instShopInfoTemp) throws URISyntaxException {
        log.debug("REST request to save InstShopInfoTemp : {}", instShopInfoTemp);
        if (instShopInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instShopInfoTemp cannot already have an ID").body(null);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setShopInfo(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }



        InstShopInfoTemp result = instShopInfoTempRepository.save(instShopInfoTemp);
        instShopInfoTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instShopInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instShopInfoTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instShopInfoTemps -> Updates an existing instShopInfoTemp.
     */
    @RequestMapping(value = "/instShopInfoTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstShopInfoTemp> updateInstShopInfoTemp(@RequestBody InstShopInfoTemp instShopInfoTemp) throws URISyntaxException {
        log.debug("REST request to update InstShopInfoTemp : {}", instShopInfoTemp);
        if (instShopInfoTemp.getId() == null) {
            return createInstShopInfoTemp(instShopInfoTemp);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setShopInfo(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }



        InstShopInfoTemp result = instShopInfoTempRepository.save(instShopInfoTemp);
        instShopInfoTempSearchRepository.save(instShopInfoTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instShopInfoTemp", instShopInfoTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instShopInfoTemps -> get all the instShopInfoTemps.
     */
    @RequestMapping(value = "/instShopInfoTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstShopInfoTemp>> getAllInstShopInfoTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstShopInfoTemp> page = instShopInfoTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instShopInfoTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instShopInfoTemps/:id -> get the "id" instShopInfoTemp.
     */
    @RequestMapping(value = "/instShopInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstShopInfoTemp> getInstShopInfoTemp(@PathVariable Long id) {
        log.debug("REST request to get InstShopInfoTemp : {}", id);
        return Optional.ofNullable(instShopInfoTempRepository.findOne(id))
            .map(instShopInfoTemp -> new ResponseEntity<>(
                instShopInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instShopInfoTemps/:id -> delete the "id" instShopInfoTemp.
     */
    @RequestMapping(value = "/instShopInfoTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstShopInfoTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstShopInfoTemp : {}", id);
        instShopInfoTempRepository.delete(id);
        instShopInfoTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instShopInfoTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instShopInfoTemps/:query -> search for the instShopInfoTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instShopInfoTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstShopInfoTemp> searchInstShopInfoTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instShopInfoTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
