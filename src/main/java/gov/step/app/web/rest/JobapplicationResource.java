package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CvSearchCriteria;
import gov.step.app.domain.Jobapplication;
import gov.step.app.domain.JpEmployee;
import gov.step.app.domain.NotificationStep;
import gov.step.app.domain.enumeration.EmployeeGender;
import gov.step.app.domain.enumeration.JobApplicationStatus;
import gov.step.app.repository.JobapplicationRepository;
import gov.step.app.repository.NotificationStepRepository;
import gov.step.app.repository.search.JobapplicationSearchRepository;
import gov.step.app.web.rest.dto.JobApplicatonDto;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.AttachmentUtil;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import oracle.sql.DATE;
import org.elasticsearch.common.joda.time.DateTime;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Jobapplication.
 */
@RestController
@RequestMapping("/api")
public class JobapplicationResource {

    private final Logger log = LoggerFactory.getLogger(JobapplicationResource.class);

    @Inject
    private JobapplicationRepository jobapplicationRepository;

    @Inject
    private JobapplicationSearchRepository jobapplicationSearchRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    String filepath="/backup/jobportal/";

    /**
     * POST /jobapplications -> Create a new jobapplication.
     */
    @RequestMapping(value = "/jobapplications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Jobapplication> createJobapplication(@RequestBody Jobapplication jobapplication)
        throws URISyntaxException {
        log.debug("REST request to save Jobapplication : {}", jobapplication);
        if (jobapplication.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jobapplication cannot already have an ID")
                .body(null);
        }
        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification(jobapplication.getJpEmployee().getName()+" has applied for  "+ jobapplication.getJob().getCat().getCat());
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("employer.job-list");
        notificationSteps.setUserId(jobapplication.getJob().getEmployer().getUser().getId());
        notificationStepRepository.save(notificationSteps);
        Jobapplication result = jobapplicationRepository.save(jobapplication);
        jobapplicationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jobapplications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobapplication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT /jobapplications -> Updates an existing jobapplication.
     */
    @RequestMapping(value = "/jobapplications", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Jobapplication> updateJobapplication(@RequestBody Jobapplication jobapplication)
        throws URISyntaxException {
        log.debug("REST request to update Jobapplication : {}", jobapplication);
        if (jobapplication.getId() == null) {
            return createJobapplication(jobapplication);
        }
        Jobapplication preInfoApplication = jobapplicationRepository.findOne(jobapplication.getId());
        if(preInfoApplication.getCvViewed() == false && jobapplication.getCvViewed() == true){
            NotificationStep notificationSteps = new NotificationStep();
            notificationSteps.setNotification(jobapplication.getJob().getEmployer().getName()+" has viewed your CV.");
            notificationSteps.setStatus(true);
            notificationSteps.setUrls("appliedJobs");
            notificationSteps.setUserId(jobapplication.getJpEmployee().getUser().getId());
            notificationStepRepository.save(notificationSteps);
        }

        if(preInfoApplication.getApplicantStatus().equals(JobApplicationStatus.awaiting) && (jobapplication.getApplicantStatus().equals(JobApplicationStatus.interviewed) || jobapplication.getApplicantStatus().equals(JobApplicationStatus.selected) || jobapplication.getApplicantStatus().equals(JobApplicationStatus.shortlisted))){
            NotificationStep notificationSteps = new NotificationStep();
            notificationSteps.setNotification(jobapplication.getJob().getEmployer().getName()+" has added you to "+ jobapplication.getApplicantStatus() + " list.");
            notificationSteps.setStatus(true);
            notificationSteps.setUrls("appliedJobs");
            notificationSteps.setUserId(jobapplication.getJpEmployee().getUser().getId());
            notificationStepRepository.save(notificationSteps);
        }
        Jobapplication result = jobapplicationRepository.save(jobapplication);
        jobapplicationSearchRepository.save(jobapplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobapplication", jobapplication.getId().toString()))
            .body(result);
    }

    /**
     * GET /jobapplications -> get all the jobapplications.
     */
    @RequestMapping(value = "/jobapplications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Jobapplication>> getAllJobapplications(Pageable pageable) throws URISyntaxException {
        Page<Jobapplication> page = jobapplicationRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobapplications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET /jobapplications/job/id -> get all the jobapplications.
     */
    @RequestMapping(value = "/jobapplications/jobs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Jobapplication>> getAllJobapplicationsByJob(Pageable pageable,@PathVariable Long id) throws Exception {
        Page<Jobapplication> page = jobapplicationRepository.findAllApplicationByJob(pageable, id);
        for(Jobapplication each:page.getContent()){
            if(each.getJpEmployee().getPictureName() !=null && each.getJpEmployee().getPictureName().length() > 0 ){
                log.debug("><><><><><><><><<><><><<<><><>><>>< "+" comes to access picture");
                if(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()) != null){
                    each.getJpEmployee().setPicture(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()));
                }

            }
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobapplications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**

     * GET /jobapplications -> get all the jobapplications.
     */

    //find list of all job applications by current user without pageable
    @RequestMapping(
        value = "/employeeApplications/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Jobapplication> getAllEmployeeJobapplications() throws URISyntaxException {
        List<Jobapplication> employeeApplications=jobapplicationRepository.findByUserIsCurrentUser();
        return employeeApplications;
    }


    /**
     * GET /jobapplications/:id -> get the "id" jobapplication.
     */
    @RequestMapping(value = "/jobapplications/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Jobapplication> getJobapplication(@PathVariable Long id) throws Exception {
        log.debug("REST request to get Jobapplication : {}", id);
        Jobapplication jobapplication2 = jobapplicationRepository.findOne(id);
        if(AttachmentUtil.retriveAttachment(filepath, jobapplication2.getJpEmployee().getPictureName()) != null){
            jobapplication2.getJpEmployee().setPicture(AttachmentUtil.retriveAttachment(filepath, jobapplication2.getJpEmployee().getPictureName()));
        }
        if(AttachmentUtil.retriveAttachment(filepath, jobapplication2.getJpEmployee().getCvName()) != null){
            jobapplication2.getJpEmployee().setCv(AttachmentUtil.retriveAttachment(filepath, jobapplication2.getJpEmployee().getCvName()));
        }
        return Optional.ofNullable(jobapplication2)
            .map(jobapplication -> new ResponseEntity<>(jobapplication, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET /jobapplications/job/:id -> get the all jobapplication by job.
     */
    @RequestMapping(value = "/jobapplications/job/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Jobapplication> getAllJobapplicationByJob(@PathVariable Long id)
        throws Exception {
        System.out.println();

        List<Jobapplication> jobapplications = jobapplicationRepository.findByJob(id);

        for(Jobapplication each:jobapplications){
            if(each.getJpEmployee().getPictureName() !=null && each.getJpEmployee().getPictureName().length() > 0 ){
                log.debug("><><><><><><><><<><><><<<><><>><>>< "+" comes to access picture");
                if(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()) != null){
                    each.getJpEmployee().setPicture(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()));
                }

            }
        }



        System.out.println(">>>>>>>>>>"+jobapplications.size());
        return  jobapplications;

    }

    /**
     * DELETE /jobapplications/:id -> delete the "id" jobapplication.
     */
    @RequestMapping(value = "/jobapplications/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJobapplication(@PathVariable Long id) {
        log.debug("REST request to delete Jobapplication : {}", id);
        jobapplicationRepository.delete(id);
        jobapplicationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jobapplication", id.toString()))
            .build();
    }

    /**
     * SEARCH /_search/jobapplications/:query -> search for the jobapplication
     * corresponding to the query.
     */
    @RequestMapping(value = "/_search/jobapplications/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Jobapplication> searchJobapplications(@PathVariable String query) {
        return StreamSupport.stream(jobapplicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/jobapplications/job/viewed/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Jobapplication> getApplicatonCvViewed(@PathVariable Long id)
        throws Exception {
        List<Jobapplication> jobapplications = jobapplicationRepository.findByJobAndViewType(id, true);
        for(Jobapplication each:jobapplications){
            if(each.getJpEmployee().getPictureName() !=null && each.getJpEmployee().getPictureName().length() > 0 ){
                log.debug("><><><><><><><><<><><><<<><><>><>>< "+" comes to access picture");
                if(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()) != null){
                    each.getJpEmployee().setPicture(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()));
                }

            }
        }

        return  jobapplications;

    }

    @RequestMapping(value = "/jobapplications/job/unviewed/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Jobapplication> getApplicatonCvUniewed(@PathVariable Long id)
        throws Exception {
        List<Jobapplication> jobapplications = jobapplicationRepository.findByJobAndViewType(id, false);
        for(Jobapplication each:jobapplications){
            if(each.getJpEmployee().getPictureName() !=null && each.getJpEmployee().getPictureName().length() > 0 ){
                log.debug("><><><><><><><><<><><><<<><><>><>>< "+" comes to access picture");
                if(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()) != null){
                    each.getJpEmployee().setPicture(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()));
                }

            }
        }
        return  jobapplications;

    }
    @RequestMapping(value = "/jobapplications/job/shortListed/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Jobapplication> getShortlistedList(@PathVariable Long id)
        throws Exception {
        List<Jobapplication> jobapplications = jobapplicationRepository.findJobApplicationByStatus(id, JobApplicationStatus.shortlisted);
        for(Jobapplication each:jobapplications){
            if(each.getJpEmployee().getPictureName() !=null && each.getJpEmployee().getPictureName().length() > 0 ){
                log.debug("><><><><><><><><<><><><<<><><>><>>< "+" comes to access picture");
                if(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()) != null){
                    each.getJpEmployee().setPicture(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()));
                }

            }
        }
        return  jobapplications;

    }

    @RequestMapping(value = "/jobapplications/job/finalSelected/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Jobapplication> getSelectedList(@PathVariable Long id)
        throws Exception {
        List<Jobapplication> jobapplications = jobapplicationRepository.findJobApplicationByStatus(id, JobApplicationStatus.selected);
        for(Jobapplication each:jobapplications){
            if(each.getJpEmployee().getPictureName() !=null && each.getJpEmployee().getPictureName().length() > 0 ){
                log.debug("><><><><><><><><<><><><<<><><>><>>< "+" comes to access picture");
                if(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()) != null){
                    each.getJpEmployee().setPicture(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()));
                }

            }
        }
        return  jobapplications;

    }
    @RequestMapping(value = "/jobapplications/job/interviewed/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Jobapplication> getInterviewList(@PathVariable Long id)
        throws Exception {
        List<Jobapplication> jobapplications = jobapplicationRepository.findJobApplicationByStatus(id, JobApplicationStatus.interviewed);
        for(Jobapplication each:jobapplications){
            if(each.getJpEmployee().getPictureName() !=null && each.getJpEmployee().getPictureName().length() > 0 ){
                log.debug("><><><><><><><><<><><><<<><><>><>>< "+" comes to access picture");
                if(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()) != null){
                    each.getJpEmployee().setPicture(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()));
                }

            }
        }
        return  jobapplications;

    }

    @RequestMapping(value = "/jobapplications/job/rejected/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Jobapplication> getRejectedList(@PathVariable Long id)
        throws Exception {
        List<Jobapplication> jobapplications = jobapplicationRepository.findJobApplicationByStatus(id, JobApplicationStatus.rejected);
        for(Jobapplication each:jobapplications){
            if(each.getJpEmployee().getPictureName() !=null && each.getJpEmployee().getPictureName().length() > 0 ){
                log.debug("><><><><><><><><<><><><<<><><>><>>< "+" comes to access picture");
                if(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()) != null){
                    each.getJpEmployee().setPicture(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()));
                }

            }
        }
        return  jobapplications;

    }

    /**
     * GET /jobapplications/job/:id -> get the all jobapplication by job.
     */
    @RequestMapping(value = "/jobapplicationsDto/job/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public JobApplicatonDto getDtoByJob(@PathVariable Long id)
        throws URISyntaxException {
        JobApplicatonDto jobApplicatonDto = new JobApplicatonDto();
        //jobApplicatonDto.setJobapplications(jobapplicationRepository.findByJob(id));
        jobApplicatonDto.setCvViewed(jobapplicationRepository.findCvViewedCounter(id, true));
        jobApplicatonDto.setCvNotViewed(jobapplicationRepository.findCvViewedCounter(id, false));
        jobApplicatonDto.setInterviewed(jobapplicationRepository.findCounterByStatus(id, JobApplicationStatus.interviewed));
        jobApplicatonDto.setRejected(jobapplicationRepository.findCounterByStatus(id, JobApplicationStatus.rejected));
        jobApplicatonDto.setSelected(jobapplicationRepository.findCounterByStatus(id, JobApplicationStatus.selected));
        jobApplicatonDto.setShortListed(jobapplicationRepository.findCounterByStatus(id, JobApplicationStatus.shortlisted));

        return  jobApplicatonDto;

    }

    @RequestMapping(value = "/jobapplications/job/totalApplication/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Integer getApplicationCounter(@PathVariable Long id)
        throws URISyntaxException {
        Integer totalApplication = jobapplicationRepository.findTotalApplicationByJob(id);
        return  totalApplication;

    }

    @RequestMapping(value = "/jobapplications/experience/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Map<String,Object> getTotalExperience (@PathVariable Long id) throws Exception{

            rptJdbcDao.getSortedCv("Dhaka", 1, "Galib Mia", "Male", 5, 20000, "", "Dhaka", 30, 35000);
        return rptJdbcDao.getTotalExperienceJP(id);

    }

    /**
     * POST /jobapplications -> Create a new jobapplication.
     */
    @RequestMapping(value = "/jobapplications/cvSorting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Jobapplication> searchCvSortingJobapplication(@RequestBody CvSearchCriteria cvSearchCriteria)
        throws Exception {
        log.debug("REST request to save Jobapplication cv Search: {}", cvSearchCriteria);
        //Calendar cal = Calendar.getInstance();
        //Date today = cal.getTime();
        //Calendar startAge = cal.add(Calendar.DATE, (int) (365 * cvSearchCriteria.getAgeStart())); // to get previous year add -1

       /* DateTime dobStart = new DateTime().minusDays((int) (365 * cvSearchCriteria.getAgeStart()));
        DateTime dobEnd = new DateTime().minusDays((int) (365 * cvSearchCriteria.getAgeEnd()));
        LocalDate d1 = dobEnd.toLocalDate();*/
/*
        log.debug("birthdate start :"+dobStart);
        log.debug("birthdate end :"+dobEnd);*/
        if(cvSearchCriteria.getExpStart() == null){
            cvSearchCriteria.setExpStart(0.0);
        }

        if(cvSearchCriteria.getExpEnd() == null){
            cvSearchCriteria.setExpEnd(100.0);
        }

        if(cvSearchCriteria.getAgeStart() == null){
            cvSearchCriteria.setAgeStart(0.0);
        }

        if(cvSearchCriteria.getAgeEnd() == null){
            cvSearchCriteria.setAgeEnd(100.0);
        }


        List<Jobapplication> jobApplications = new ArrayList<>();
        EmployeeGender gend = null;
        if(cvSearchCriteria.getGender().equals("All")){
            cvSearchCriteria.setGender(null);
        }else if(cvSearchCriteria.getGender().equals("Male")){
            gend = EmployeeGender.Male;
        }else if(cvSearchCriteria.getGender().equals("Female")){
            gend = EmployeeGender.Female;
        }

        /*if((cvSearchCriteria.getGender() == null || cvSearchCriteria.getGender().equals("All")) && cvSearchCriteria.getDistrictId() == null){
            jobApplications = jobapplicationRepository.findSortedCvWithoutGender(cvSearchCriteria.getJobId(), cvSearchCriteria.getExpStart(), cvSearchCriteria.getExpEnd(), cvSearchCriteria.getAgeStart(), cvSearchCriteria.getAgeEnd());

        }else{

            jobApplications = jobapplicationRepository.findSortedCv(cvSearchCriteria.getJobId(), gend, cvSearchCriteria.getExpStart(), cvSearchCriteria.getExpEnd(), cvSearchCriteria.getAgeStart(), cvSearchCriteria.getAgeEnd());

        }*/
        if(cvSearchCriteria.getGender() != null  && cvSearchCriteria.getDistrictId() != null) {
            jobApplications = jobapplicationRepository.findSortedCvWithDistrict(cvSearchCriteria.getJobId(), gend, cvSearchCriteria.getExpStart(), cvSearchCriteria.getExpEnd(), cvSearchCriteria.getAgeStart(), cvSearchCriteria.getAgeEnd(), cvSearchCriteria.getDistrictId());

        }else if (cvSearchCriteria.getGender() != null  && cvSearchCriteria.getDistrictId() == null){
            jobApplications = jobapplicationRepository.findSortedCv(cvSearchCriteria.getJobId(), gend, cvSearchCriteria.getExpStart(), cvSearchCriteria.getExpEnd(), cvSearchCriteria.getAgeStart(), cvSearchCriteria.getAgeEnd());

        }else if (cvSearchCriteria.getGender() == null  && cvSearchCriteria.getDistrictId() != null){
            jobApplications = jobapplicationRepository.findSortedCvWithDistrictWithoutGender(cvSearchCriteria.getJobId(), cvSearchCriteria.getExpStart(), cvSearchCriteria.getExpEnd(), cvSearchCriteria.getAgeStart(), cvSearchCriteria.getAgeEnd(), cvSearchCriteria.getDistrictId());

        }else {
            jobApplications = jobapplicationRepository.findSortedCvWithoutGender(cvSearchCriteria.getJobId(), cvSearchCriteria.getExpStart(), cvSearchCriteria.getExpEnd(), cvSearchCriteria.getAgeStart(), cvSearchCriteria.getAgeEnd());
        }


        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>total jobs :"+jobApplications.size());
        for(Jobapplication each:jobApplications){
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>.  :"+each.getId());
        }
        for(Jobapplication each:jobApplications){
            if(each.getJpEmployee().getPictureName() !=null && each.getJpEmployee().getPictureName().length() > 0 ){
                log.debug("><><><><><><><><<><><><<<><><>><>>< "+" comes to access picture");
                if(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()) != null){
                    each.getJpEmployee().setPicture(AttachmentUtil.retriveAttachment(filepath, each.getJpEmployee().getPictureName()));
                }

            }
        }
        return jobApplications;
       /* return ResponseEntity.created(new URI("/api/jobapplications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobapplication", result.getId().toString()))
            .body(Jobapplication);*/
    }
}
