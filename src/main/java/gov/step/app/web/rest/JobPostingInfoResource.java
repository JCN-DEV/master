package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Job;
import gov.step.app.domain.JobPostingInfo;
import gov.step.app.repository.JobPostingInfoRepository;
import gov.step.app.repository.search.JobPostingInfoSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.ServletContext;
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
 * REST controller for managing JobPostingInfo.
 */
@RestController
@RequestMapping("/api")
public class JobPostingInfoResource {

    private final Logger log = LoggerFactory.getLogger(JobPostingInfoResource.class);

    @Autowired
    ServletContext context;
    @Inject
    private JobPostingInfoRepository jobPostingInfoRepository;

    @Inject
    private JobPostingInfoSearchRepository jobPostingInfoSearchRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    /**
     * POST  /jobPostingInfos -> Create a new jobPostingInfo.
     */
    @RequestMapping(value = "/jobPostingInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobPostingInfo> createJobPostingInfo(@RequestBody JobPostingInfo jobPostingInfo) throws URISyntaxException {
        log.debug("REST request to save JobPostingInfo : {}", jobPostingInfo);
        if (jobPostingInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jobPostingInfo cannot already have an ID").body(null);
        }

        jobPostingInfo.setCreateBy(SecurityUtils.getCurrentUserId());
        jobPostingInfo.setUpdateBy(SecurityUtils.getCurrentUserId());
        TransactionIdResource transactionId =new TransactionIdResource();
        /*jobPostingInfo.setJobPostId(transactionId.getGeneratedid("Job"));*/
        DateResource dr=new DateResource();
        jobPostingInfo.setCreateDate(dr.getDateAsLocalDate());
        jobPostingInfo.setUpdateDate(dr.getDateAsLocalDate());
        System.out.println(" Hi---I'm ready to save!!!!!!");

        String fileContent=jobPostingInfo.getUploadContentType();
        if((fileContent.equals("application/pdf")) && (!fileContent.equals(".png"))){
            jobPostingInfo.setJobPostId(transactionId.getGeneratedid("PDF-"));
        }else{
            jobPostingInfo.setJobPostId(transactionId.getGeneratedid("IMG-"));
        }
        String filepath = context.getRealPath("/") + Constants.ATTACHMENT_FILE_DIR_JOBPOSTING;
        String filename=jobPostingInfo.getJobPostId();
        String extn=".png";

        if(fileContent.equals("application/pdf")){
            jobPostingInfo.setJobFileName(jobPostingInfo.getJobPostId() + ".pdf");
            extn=".pdf";
        }else{
            jobPostingInfo.setJobFileName(jobPostingInfo.getJobPostId() + ".png");
        }
        try{
           AttachmentUtil.saveAttachment(filepath,filename,extn,jobPostingInfo.getUpload());
        }catch(Exception e){
            e.printStackTrace();
        }

        jobPostingInfo.setUpload(null);
        JobPostingInfo result = jobPostingInfoRepository.save(jobPostingInfo);
        jobPostingInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jobPostingInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobPostingInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jobPostingInfos -> Updates an existing jobPostingInfo.
     */
    @RequestMapping(value = "/jobPostingInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobPostingInfo> updateJobPostingInfo(@RequestBody JobPostingInfo jobPostingInfo) throws URISyntaxException {
        log.debug("REST request to update JobPostingInfo : {}", jobPostingInfo);
        if (jobPostingInfo.getId() == null) {
            return createJobPostingInfo(jobPostingInfo);
        }

        jobPostingInfo.setCreateBy(SecurityUtils.getCurrentUserId());
        jobPostingInfo.setUpdateBy(SecurityUtils.getCurrentUserId());
        TransactionIdResource transactionId =new TransactionIdResource();
        /*jobPostingInfo.setJobPostId(transactionId.getGeneratedid("Job"));*/
        DateResource dr=new DateResource();
        jobPostingInfo.setCreateDate(dr.getDateAsLocalDate());
        jobPostingInfo.setUpdateDate(dr.getDateAsLocalDate());
        System.out.println(" Hi---I'm ready to save!!!!!!");

        String fileContent=jobPostingInfo.getUploadContentType();
        if((fileContent.equals("application/pdf")) && (!fileContent.equals(".png"))){
            jobPostingInfo.setJobPostId(transactionId.getGeneratedid("PDF-"));
        }else{
            jobPostingInfo.setJobPostId(transactionId.getGeneratedid("IMG-"));
        }
        String filepath = context.getRealPath("/") + Constants.ATTACHMENT_FILE_DIR_JOBPOSTING;
        String filename=jobPostingInfo.getJobPostId();
        String extn=".png";

        if(fileContent.equals("application/pdf")){
            jobPostingInfo.setJobFileName(jobPostingInfo.getJobPostId() + ".pdf");
            extn=".pdf";
        }else{
            jobPostingInfo.setJobFileName(jobPostingInfo.getJobPostId() + ".png");
        }
        try{
            AttachmentUtil.saveAttachment(filepath,filename,extn,jobPostingInfo.getUpload());
        }catch(Exception e){
            e.printStackTrace();
        }

        jobPostingInfo.setUpload(null);


        JobPostingInfo result = jobPostingInfoRepository.save(jobPostingInfo);
        jobPostingInfoSearchRepository.save(jobPostingInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobPostingInfo", jobPostingInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jobPostingInfos -> get all the jobPostingInfos.
     */
    @RequestMapping(value = "/jobPostingInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JobPostingInfo>> getAllJobPostingInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get JobPostingInfo list ");
        Page<JobPostingInfo> page = jobPostingInfoRepository.findAllByOrderByIdDesc(pageable);
        byte[] fileData = null;
        String filepath = context.getRealPath("/") + Constants.ATTACHMENT_FILE_DIR_JOBPOSTING;
        log.debug("loc: "+filepath);
        for(JobPostingInfo jobPosting : page.getContent())
        {
            try
            {
                fileData = AttachmentUtil.retriveAttachment(filepath, jobPosting.getJobFileName());
            }catch (Exception e){
                e.printStackTrace();
            }
            jobPosting.setUpload(fileData);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobPostingInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jobPostingInfos/:id -> get the "id" jobPostingInfo.
     */
    @RequestMapping(value = "/jobPostingInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobPostingInfo> getJobPostingInfo(@PathVariable Long id) {
        log.debug("REST request to get JobPostingInfo : {}", id);

        JobPostingInfo jobPosting = jobPostingInfoRepository.findOne(id);

        String filepath = context.getRealPath("/") + Constants.ATTACHMENT_FILE_DIR_JOBPOSTING;
        log.debug("loc: "+filepath+", name: "+jobPosting.getJobFileName());
        byte[] fileData = null;
        try
        {
            fileData = AttachmentUtil.retriveAttachment(filepath, jobPosting.getJobFileName());
        }catch (Exception e){
            e.printStackTrace();
        }
        jobPosting.setUpload(fileData);

        return Optional.ofNullable(jobPosting)
            .map(jobPostingInfo -> new ResponseEntity<>(
                jobPostingInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /contentUploads -> get byCategory the contentUploads.
     */
    @RequestMapping(value = "/jobPostingInfos/byCategory",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JobPostingInfo>> getbyCategory(Pageable pageable)
        throws URISyntaxException {
        Page<JobPostingInfo> page = jobPostingInfoRepository.findAllContentByCategory(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobPostingInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contentUploads -> get byJob the contentUploads.
     */
    @RequestMapping(value = "/jobPostingInfos/byJob/{catId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Job>> CatByJobs(Pageable pageable, @PathVariable Long catId)
        throws URISyntaxException {
        Page<Job> page = jobPostingInfoRepository.findAllJobsByCat(pageable, catId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobPostingInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /*@RequestMapping(value = "/jobPostingInfos/byJob",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getbyJob(Pageable pageable) {
        List<Map<String,Object>> listpRpt = rptJdbcDao.CatByJobs();
        return listpRpt;
    }*/

    /**
     * DELETE  /jobPostingInfos/:id -> delete the "id" jobPostingInfo.
     */
    @RequestMapping(value = "/jobPostingInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJobPostingInfo(@PathVariable Long id) {
        log.debug("REST request to delete JobPostingInfo : {}", id);
        jobPostingInfoRepository.delete(id);
        jobPostingInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jobPostingInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jobPostingInfos/:query -> search for the jobPostingInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jobPostingInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JobPostingInfo> searchJobPostingInfos(@PathVariable String query) {
        return StreamSupport
            .stream(jobPostingInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
