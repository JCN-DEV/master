package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JobPlacementInfo;
import gov.step.app.repository.JobPlacementInfoRepository;
import gov.step.app.repository.search.JobPlacementInfoSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import gov.step.app.web.rest.util.TransactionIdResource;
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
 * REST controller for managing JobPlacementInfo.
 */
@RestController
@RequestMapping("/api")
public class JobPlacementInfoResource {

    private final Logger log = LoggerFactory.getLogger(JobPlacementInfoResource.class);

    @Inject
    private JobPlacementInfoRepository jobPlacementInfoRepository;

    @Inject
    private JobPlacementInfoSearchRepository jobPlacementInfoSearchRepository;

    /**
     * POST  /jobPlacementInfos -> Create a new jobPlacementInfo.
     */
    @RequestMapping(value = "/jobPlacementInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobPlacementInfo> createJobPlacementInfo(@RequestBody JobPlacementInfo jobPlacementInfo) throws URISyntaxException {
        log.debug("REST request to save JobPlacementInfo : {}", jobPlacementInfo);
        if (jobPlacementInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jobPlacementInfo cannot already have an ID").body(null);
        }

        jobPlacementInfo.setCreateBy(SecurityUtils.getCurrentUserId());
        jobPlacementInfo.setUpdateBy(SecurityUtils.getCurrentUserId());
        TransactionIdResource transactionId =new TransactionIdResource();
        jobPlacementInfo.setJobId(transactionId.getGeneratedid("Job-"));
        DateResource dr=new DateResource();
        jobPlacementInfo.setCreateDate(dr.getDateAsLocalDate());
        jobPlacementInfo.setUpdateDate(dr.getDateAsLocalDate());
         System.out.println(" Hi---I'm ready to save!!!!!!");
        JobPlacementInfo result = jobPlacementInfoRepository.save(jobPlacementInfo);
        jobPlacementInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jobPlacementInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobPlacementInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jobPlacementInfos -> Updates an existing jobPlacementInfo.
     */
    @RequestMapping(value = "/jobPlacementInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobPlacementInfo> updateJobPlacementInfo(@RequestBody JobPlacementInfo jobPlacementInfo) throws URISyntaxException {
        log.debug("REST request to update JobPlacementInfo : {}", jobPlacementInfo);
        if (jobPlacementInfo.getId() == null) {
            return createJobPlacementInfo(jobPlacementInfo);
        }
        JobPlacementInfo result = jobPlacementInfoRepository.save(jobPlacementInfo);
        jobPlacementInfoSearchRepository.save(jobPlacementInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobPlacementInfo", jobPlacementInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jobPlacementInfos -> get all the jobPlacementInfos.
     */
    @RequestMapping(value = "/jobPlacementInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JobPlacementInfo>> getAllJobPlacementInfos(Pageable pageable)
        throws URISyntaxException {
        Page<JobPlacementInfo> page = jobPlacementInfoRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobPlacementInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jobPlacementInfos/:id -> get the "id" jobPlacementInfo.
     */
    @RequestMapping(value = "/jobPlacementInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobPlacementInfo> getJobPlacementInfo(@PathVariable Long id) {
        log.debug("REST request to get JobPlacementInfo : {}", id);
        return Optional.ofNullable(jobPlacementInfoRepository.findOne(id))
            .map(jobPlacementInfo -> new ResponseEntity<>(
                jobPlacementInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jobPlacementInfos/:id -> delete the "id" jobPlacementInfo.
     */
    @RequestMapping(value = "/jobPlacementInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJobPlacementInfo(@PathVariable Long id) {
        log.debug("REST request to delete JobPlacementInfo : {}", id);
        jobPlacementInfoRepository.delete(id);
        jobPlacementInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jobPlacementInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jobPlacementInfos/:query -> search for the jobPlacementInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jobPlacementInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JobPlacementInfo> searchJobPlacementInfos(@PathVariable String query) {
        return StreamSupport
            .stream(jobPlacementInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
