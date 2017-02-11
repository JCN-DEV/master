package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JobType;
import gov.step.app.domain.OrganizationType;
import gov.step.app.repository.JobTypeRepository;
import gov.step.app.repository.search.JobTypeSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing JobType.
 */
@RestController
@RequestMapping("/api")
public class JobTypeResource {

    private final Logger log = LoggerFactory.getLogger(JobTypeResource.class);

    @Inject
    private JobTypeRepository jobTypeRepository;

    @Inject
    private JobTypeSearchRepository jobTypeSearchRepository;

    /**
     * POST  /jobTypes -> Create a new jobType.
     */
    @RequestMapping(value = "/jobTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobType> createJobType(@Valid @RequestBody JobType jobType) throws URISyntaxException {
        log.debug("REST request to save JobType : {}", jobType);
        if (jobType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jobType cannot already have an ID").body(null);
        }
        JobType result = jobTypeRepository.save(jobType);
        jobTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jobTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jobTypes -> Updates an existing jobType.
     */
    @RequestMapping(value = "/jobTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobType> updateJobType(@Valid @RequestBody JobType jobType) throws URISyntaxException {
        log.debug("REST request to update JobType : {}", jobType);
        if (jobType.getId() == null) {
            return createJobType(jobType);
        }
        JobType result = jobTypeRepository.save(jobType);
        jobTypeSearchRepository.save(jobType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobType", jobType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jobTypes -> get all the jobTypes.
     */
    @RequestMapping(value = "/jobTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JobType>> getAllJobTypes(Pageable pageable)
        throws URISyntaxException {
        Page<JobType> page = jobTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jobTypes -> get all the jobTypes.
     */
    @RequestMapping(value = "/jobTypes/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JobType>> getAllActiveJobTypes(Pageable pageable)
        throws URISyntaxException {
        Page<JobType> page = jobTypeRepository.allActiveJobType(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jobTypes/:id -> get the "id" jobType.
     */
    @RequestMapping(value = "/jobTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobType> getJobType(@PathVariable Long id) {
        log.debug("REST request to get JobType : {}", id);
        return Optional.ofNullable(jobTypeRepository.findOne(id))
            .map(jobType -> new ResponseEntity<>(
                jobType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jobTypes/:id -> delete the "id" jobType.
     */
    @RequestMapping(value = "/jobTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJobType(@PathVariable Long id) {
        log.debug("REST request to delete JobType : {}", id);
        jobTypeRepository.delete(id);
        jobTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jobType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jobTypes/:query -> search for the jobType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jobTypes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JobType> searchJobTypes(@PathVariable String query) {
        return StreamSupport
            .stream(jobTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /*check organization type exists by name*/

    @RequestMapping(value = "/jobType/checkByName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkJobTypeByName(@RequestParam String value) {

        JobType jobType = jobTypeRepository.findOneByName(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(jobType == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }
}
