package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Cat;
import gov.step.app.domain.Institute;
import gov.step.app.domain.Job;
import gov.step.app.domain.enumeration.JobApplicationStatus;
import gov.step.app.repository.EmployerRepository;
import gov.step.app.repository.InstEmployeeRepository;
import gov.step.app.repository.JobRepository;
import gov.step.app.repository.JobapplicationRepository;
import gov.step.app.repository.search.JobSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.dto.JobDTO;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import jdk.nashorn.api.scripting.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Job.
 */
@RestController
@RequestMapping("/api")
public class JobResource {

    private final Logger log = LoggerFactory.getLogger(JobResource.class);

    @Inject
    private JobRepository jobRepository;

    @Inject
    private JobSearchRepository jobSearchRepository;

    @Inject
    private JobapplicationRepository jobapplicationRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private EmployerRepository employerRepository;

    /**
     * POST /jobs -> Create a new job.
     */
    @RequestMapping(value = "/jobs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Job> createJob(@Valid @RequestBody Job job) throws URISyntaxException {
        log.debug("REST request to save Job : {}", job);
        if (job.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new job cannot already have an ID").body(null);
        }
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.debug(String.valueOf(job.getApplicationDeadline()));
        Job result = jobRepository.save(job);
        jobSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("job", result.getId().toString())).body(result);
    }

    /**
     * PUT /jobs -> Updates an existing job.
     */
    @RequestMapping(value = "/jobs", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Job> updateJob(@Valid @RequestBody Job job) throws URISyntaxException {
        log.debug("REST request to update Job : {}", job);
        if (job.getId() == null) {
            return createJob(job);
        }
        Job result = jobRepository.save(job);
        jobSearchRepository.save(job);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("job", job.getId().toString()))
            .body(result);
    }

    /**
     * GET /jobs -> get all the jobs.
     */
    @RequestMapping(value = "/jobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Job>> getAllJobs(Pageable pageable) throws URISyntaxException {

        Page<Job> page = jobRepository.findAll(pageable);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.EMPLOYER))
            page = jobRepository.findByEmployerUserIsCurrentUser(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

//    @RequestMapping(value = "/jobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<List<Job>> getPostedJobByEmployer(Pageable pageable) throws URISyntaxException {
//
//        Page<Job> page = jobRepository.findAll(pageable);
//
//        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.EMPLOYER))
//            page = jobRepository.findByEmployerUserIsCurrentUser(pageable);
//
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobs");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }


    /**
     * GET /jobs/employer/:id -> get all job by employer
     */
    @RequestMapping(value = "/jobs/employer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Job> getAllJobByEmployer(@PathVariable Long id)
        throws URISyntaxException {
        List<Job> jobs = jobRepository.findByEmployer(id);
        return  jobs;
    }

    /**
     * GET /jobs/:id -> get the "id" job.
     */
    @RequestMapping(value = "/jobs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Job> getJob(@PathVariable Long id) {
        log.debug("REST request to get Job : {}", id);
        return Optional.ofNullable(jobRepository.findOneWithEagerRelationships(id))
            .map(job -> new ResponseEntity<>(job, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /jobs/:id -> delete the "id" job.
     */
    @RequestMapping(value = "/jobs/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log.debug("REST request to delete Job : {}", id);
        jobRepository.delete(id);
        jobSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("job", id.toString())).build();
    }

    /**
     * SEARCH /_search/jobs/:query -> search for the job corresponding to the
     * query.
     */
    @RequestMapping(value = "/_search/jobs/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Job> searchJobs(@PathVariable String query) {
        log.debug("query :"+query);
        return StreamSupport.stream(jobSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * SEARCH /_search/jobs/:query -> search for the job corresponding to the
     * query.
     */
    @RequestMapping(value = "/_search/curJobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Page<Job> searchPresentJobs(Pageable pageable) {
        return jobSearchRepository.findByApplicationDeadlineGreaterThan(new Date(), pageable);
    }

    /**
     * SEARCH /_search/jobs/:query -> search for the job corresponding to the
     * query.
     */
    @RequestMapping(value = "/_search/curJobs/location/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    /*public Page<Job> searchPresentJobsByLocation(@PathVariable String location, Pageable pageable) throws UnsupportedEncodingException {
        return jobSearchRepository.findByLocationLikeAndApplicationDeadlineGreaterThan(URLEncoder.encode(location, "UTF-8"),new Date(), pageable);
    }*/
    public ResponseEntity<List<Job>> searchPresentJobsByLocation(@PathVariable String location, Pageable pageable) throws Exception {

        Page<Job> page = jobSearchRepository.findByLocationLikeAndApplicationDeadlineGreaterThan(URLEncoder.encode(location, "UTF-8"), new Date(), pageable);
        ;

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/curJobs/location/" + URLEncoder.encode(location, "UTF-8"));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * SEARCH /_search/jobs/:query -> search for the job corresponding to the
     * query.
     */
    @RequestMapping(value = "/_search/curJobs/cat/{cat}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public  ResponseEntity<List<Job>> searchPresentJobsByCat(@PathVariable String cat, Pageable pageable) throws Exception {

        Page<Job> page = jobSearchRepository.findByCatCatLikeAndApplicationDeadlineGreaterThan(URLEncoder.encode(cat, "UTF-8"), new Date(), pageable);
       // Page<Job> page2 = jobRepository.findByCatCatLikeAndApplicationDeadlineGreaterThan(URLEncoder.encode(cat, "UTF-8"), LocalDate.now(), pageable);
        //Page<Job> page3 = jobRepository.findJobsByCategoryName(pageable,LocalDate.now(), URLEncoder.encode(cat, "UTF-8"));
//        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
//        log.debug(page2.getContent().toString());
//        log.debug(page2.toString());
//        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>1111111111111111111111111111111");
//        log.debug(page3.getContent().toString());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/curJobs/cat/" + URLEncoder.encode(cat, "UTF-8"));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * SEARCH /_search/jobs/:query -> search for the job corresponding to the
     * query.
     *//*
    @RequestMapping(value = "/jobs/curJobs/cat/{cat}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Job> searchPresentJobsByCat(@PathVariable String cat) throws Exception {

       // Page<Job> page = jobSearchRepository.findByCat_CatLikeAndApplicationDeadlineGreaterThan(URLEncoder.encode(cat, "UTF-8"), new Date(), pageable);
       // Page<Job> page2 = jobRepository.findByCatCatLikeAndApplicationDeadlineGreaterThan(URLEncoder.encode(cat, "UTF-8"), LocalDate.now(), pageable);
       return jobRepository.findJobsByCategoryName(LocalDate.now(), URLEncoder.encode(cat, "UTF-8").toLowerCase());

    }*/
/**
     * SEARCH /_search/jobs/:query -> search for the job corresponding to the
     * query.
     */
    @RequestMapping(value = "/jobs/curJobs/cat/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Job> searchPresentJobsByCat(@PathVariable Long id) throws Exception {

       // Page<Job> page = jobSearchRepository.findByCat_CatLikeAndApplicationDeadlineGreaterThan(URLEncoder.encode(cat, "UTF-8"), new Date(), pageable);
       // Page<Job> page2 = jobRepository.findByCatCatLikeAndApplicationDeadlineGreaterThan(URLEncoder.encode(cat, "UTF-8"), LocalDate.now(), pageable);
       return jobRepository.findJobsByCategoryId(LocalDate.now(), id);

    }

    /**
     * SEARCH /_search/jobs/:query -> search for the job corresponding to the
     * query.
     */
    @RequestMapping(value = "/_search/curJobs/employer/{employerName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public  ResponseEntity<List<Job>> searchPresentJobsByEmployer(@PathVariable String employerName, Pageable pageable) throws Exception {

        Page<Job> page = jobSearchRepository.findByEmployer_NameLikeAndApplicationDeadlineGreaterThan(URLEncoder.encode(employerName, "UTF-8"), new Date(), pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/curJobs/employer/" + URLEncoder.encode(employerName, "UTF-8"));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * SEARCH /_search/jobs/:query -> search for the job corresponding to the
     * query.
     */
    @RequestMapping(value = "/_search/curJobs/employers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public  ResponseEntity<List<Job>> searchPresentJobsByEmployerId(@PathVariable Long id, Pageable pageable) throws Exception {

        Page<Job> page = jobRepository.findJobsByEmployerId(LocalDate.now(), id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/curJobs/employers/" + id);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /*@RequestMapping(value = "/jobs/availableJobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Job> availableJobs() {
        return jobRepository.findAvailableJobs(LocalDate.now());
    }*/

    @RequestMapping(value = "/jobs/availableJobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public  ResponseEntity<List<Job>> availableJobs(Pageable pageable) throws URISyntaxException{
        log.debug("Jobs available Jobs:{}");
        Page<Job> page = jobRepository.findAvailableJobsByPage(pageable,LocalDate.now());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobs/availableJobs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }

    /**
     * GET /jobs/archive -> get archive jobs
     */
    @RequestMapping(value = "/jobs/archive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
    public ResponseEntity<List<Job>> archiveJobs(Pageable pageable) throws URISyntaxException {
        Page<Job> page = null;

        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            page = jobRepository.findArchivedJobs(pageable,LocalDate.now());
        }else if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.EMPLOYER)){

            Long employerId = employerRepository.findOneByUserIsCurrentUser().getId();
            page = jobRepository.findArchivedJobsByEmployerId(LocalDate.now(),employerId,pageable);
        }else {
            log.debug("Have No Archive Job");
        }



        /*if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.EMPLOYER))
            page = jobRepository.findByEmployerUserIsCurrentUser(pageable);*/

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobs/archive");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
      /**
     * GET /jobs/archive/jpadmin -> get archive jobs for jp admin
     */
    @RequestMapping(value = "/jobs/archive/jpadmin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
    public ResponseEntity<List<Job>> archiveJobsForJpadmin(Pageable pageable) throws URISyntaxException {

        Institute institute = instEmployeeRepository.findInstituteByUserIsCurrentUser();
        Page<Job> page = jobRepository.findArchivedJobsByInstitute(pageable, institute.getId(), LocalDate.now());

        /*if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.EMPLOYER))
            page = jobRepository.findByEmployerUserIsCurrentUser(pageable);*/

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobs/archive");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
 /**
     * GET  get all cats which job is available
     */
    @RequestMapping(value = "/jobs/cats/activeJobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
    public ResponseEntity<List<Cat>> categoriesByActiveJob(Pageable pageable) throws URISyntaxException {

        Page<Cat> page = jobRepository.findAvailableCatsByActiveJobs(pageable, LocalDate.now());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobs/cats/activeJobs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
        /*public List<Job> archiveJobs() {
            return jobRepository.findArchivedJobs(LocalDate.now());
        }*/


    /**
     * GET /jobs/employer/:id -> get all job by employer
     */
    @RequestMapping(value = "/jobsInfo/employer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JobDTO> getAllJobInfoByEmployer(@PathVariable Long id)
        throws URISyntaxException {

        List<JobDTO> jobInfos = new ArrayList<JobDTO>();

        List<Job> jobs = jobRepository.findByEmployer(id);

        for(Job job:jobs){

            JobDTO jobDto = new JobDTO();
            jobDto.setJob(job);
            jobDto.setTotalApplications(jobapplicationRepository.findTotalApplicationByJob(job.getId()));
            jobDto.setCvViewed(jobapplicationRepository.findCvViewedCounter(job.getId(), true));
            jobDto.setCvNotViewed(jobapplicationRepository.findCvViewedCounter(job.getId(), false));
            jobDto.setShortListed(jobapplicationRepository.findCounterByStatus(job.getId(), JobApplicationStatus.shortlisted));
            System.out.println("shortlist check : "+jobDto.getShortListed());
            jobInfos.add(jobDto);
        }

        return  jobInfos;
    }


}
