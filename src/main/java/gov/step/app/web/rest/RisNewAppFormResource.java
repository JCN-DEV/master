package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.RisNewAppForm;
import gov.step.app.domain.User;
import gov.step.app.repository.RisNewAppFormRepository;
import gov.step.app.repository.RisNewAppFormTwoRepository;
import gov.step.app.repository.search.RisNewAppFormSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.MailService;
import gov.step.app.service.UserService;
import gov.step.app.web.rest.dto.*;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.AttachmentUtil;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import gov.step.app.web.rest.util.SmsPushPull;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RisNewAppForm.
 */
@RestController
@RequestMapping("/api")
public class RisNewAppFormResource {

    private final Logger log = LoggerFactory.getLogger(RisNewAppFormResource.class);

    @Inject
    private RisNewAppFormRepository risNewAppFormRepository;

    @Inject
    private RisNewAppFormSearchRepository risNewAppFormSearchRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private RisNewAppFormTwoRepository risNewAppFormTwoRepository;

    @Inject
    private MailService mailService;

    @Inject
    private UserService userService;

    @Autowired
    ServletContext context;


    private SmsPushPull smsPushPull = new SmsPushPull();


    /**
     * POST  /risNewAppForms -> Create a new risNewAppForm.
     */
    @RequestMapping(value = "/risNewAppForms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisNewAppForm> createRisNewAppForm(@Valid @RequestBody RisNewAppForm risNewAppForm) throws Exception {
        log.debug("REST request to save RisNewAppForm : {}", risNewAppForm);
        if (risNewAppForm.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new risNewAppForm cannot already have an ID").body(null);
        }
        String filepath = context.getRealPath("/") + "assets/dlms/";

        String filename = "risType";

        String extension = ".png";
        String bExtension = ".png";

        String applicantImg = risNewAppForm.getApplicantImgContentType();


        if (risNewAppForm.getApplicantImgContentType() != null && risNewAppForm.getApplicantImgContentType().equals("application/pdf")) {
            extension = ".pdf";
        } else {
            extension = ".png";
        }

        if (risNewAppForm.getApplicantImg() != null) {
            risNewAppForm.setApplicantImgName(AttachmentUtil.saveRisApplicant(filepath, risNewAppForm.getApplicantImgName(), extension, risNewAppForm.getApplicantImg()));
        }

        risNewAppForm.setApplicantImg(null);

        risNewAppForm.setCreateBy(SecurityUtils.getCurrentUserId());
        risNewAppForm.setCreateDate(LocalDate.now());
        risNewAppForm.setUpdateBy(SecurityUtils.getCurrentUserId());
        risNewAppForm.setUpdateDate(LocalDate.now());

        RisNewAppForm result = risNewAppFormRepository.save(risNewAppForm);

        risNewAppFormSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/risNewAppForms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("risNewAppForm", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /risNewAppForms -> Updates an existing risNewAppForm.
     */
    @RequestMapping(value = "/risNewAppForms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisNewAppForm> updateRisNewAppForm(@Valid @RequestBody RisNewAppForm risNewAppForm) throws Exception {
        log.debug("REST request to update RisNewAppForm : {}", risNewAppForm);
        if (risNewAppForm.getId() == null) {
            return createRisNewAppForm(risNewAppForm);
        }
        RisNewAppForm result = risNewAppFormRepository.save(risNewAppForm);
        risNewAppFormSearchRepository.save(risNewAppForm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("risNewAppForm", risNewAppForm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /risNewAppForms -> get all the risNewAppForms.
     */
    @RequestMapping(value = "/risNewAppForms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RisNewAppForm>> getAllRisNewAppForms(Pageable pageable)
        throws URISyntaxException {

        Page<RisNewAppForm> page = risNewAppFormRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/risNewAppForms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /risNewAppForms -> get all the risNewAppForms by logged in user.
     */
    @RequestMapping(value = "/risNewAppFormsbyuser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RisNewAppForm>> getapplicationsbyuser(Pageable pageable)
        throws URISyntaxException {

        Page<RisNewAppForm> page = (Page<RisNewAppForm>) risNewAppFormRepository.findbyuserloggedin(pageable, SecurityUtils.getCurrentUserId());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/risNewAppFormsbyuser");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /risNewAppForms/:id -> get the "id" risNewAppForm.
     */
    @RequestMapping(value = "/risNewAppForms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisNewAppForm> getRisNewAppForm(@PathVariable Long id) {
        log.debug("REST request to get RisNewAppForm : {}", id);
        return Optional.ofNullable(risNewAppFormRepository.findOne(id))
            .map(risNewAppForm -> new ResponseEntity<>(
                risNewAppForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /risNewAppForms/:id -> delete the "id" risNewAppForm.
     */
    @RequestMapping(value = "/risNewAppForms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRisNewAppForm(@PathVariable Long id) {
        log.debug("REST request to delete RisNewAppForm : {}", id);
        risNewAppFormRepository.delete(id);
        risNewAppFormSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("risNewAppForm", id.toString())).build();
    }


    @RequestMapping(value = "/risNewAppForms/findRisAppDetailByID/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public RisNewAppDetail findRisAppDetailByID(@PathVariable Long id) {

        RisNewAppDetail risNewAppDetail = new RisNewAppDetail();
        risNewAppDetail.setRisNewAppForm(risNewAppFormRepository.findRisAppDetailById(id));
        risNewAppDetail.setRisNewAppFormTwo(risNewAppFormTwoRepository.findRisAppDetailById(id));
        return risNewAppDetail;
    }

    /**
     * SEARCH  /_search/risNewAppForms/:query -> search for the risNewAppForm corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/risNewAppForms/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RisNewAppForm> searchRisNewAppForms(@PathVariable String query) {
        return StreamSupport
            .stream(risNewAppFormSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @RequestMapping(value = "/_search/risNewAppForms/getEmployed",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> findingTotalEmployed() {
        List<Map<String, Object>> approveApp = rptJdbcDao.findingTotalEmployed();
        return approveApp;
    }


    //service for finding applicants with designation
    @RequestMapping(value = "/risNewAppForms/designation/{designation}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RisNewAppForm>> getApplicantsByDesignation(Pageable pageable, @PathVariable String designation)
        throws URISyntaxException, UnsupportedEncodingException {
        Page<RisNewAppForm> page = risNewAppFormRepository.findApplicantByDesignation(pageable, designation);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/risNewAppForms/designation/" + URLEncoder.encode(designation, "UTF-8"));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    //api for returning certain objects from top numbers
    @RequestMapping(value = "/risNewAppForms/smsWritten/{seat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> smsWritten(@PathVariable Long seat)
        throws URISyntaxException, UnsupportedEncodingException {
        return rptJdbcDao.smsWritten(seat);

    }


    //api for returning list with corresponded status number
    @RequestMapping(value = "/risNewAppForms/getApplicantsByStatus/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getApplicantsByStatus(@PathVariable Long status)
        throws URISyntaxException, UnsupportedEncodingException {
        return rptJdbcDao.getApplicantsByStatus(status);

    }

    //get job request by status
    @RequestMapping(value = "/risNewAppForms/getjobRequest/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getjobRequest(@PathVariable Long status)
        throws URISyntaxException, UnsupportedEncodingException {
        System.out.println("===================T" + status + "T====================");
        List getJobList = rptJdbcDao.getjobRequest(status);

        return getJobList;
    }

    //circular number list
    @RequestMapping(value = "/risNewAppForms/getCircularNumber",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getCircularNumber()
        throws URISyntaxException, UnsupportedEncodingException {
        System.out.println("===================Circular Number getting ====================");
        List getJobList = rptJdbcDao.getCircularNumber();

        return getJobList;
    }

    //designation number list
    @RequestMapping(value = "/risNewAppForms/getDesignation",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getDesignation()
        throws URISyntaxException, UnsupportedEncodingException {
        System.out.println("===================Designation Number getting ====================");
        List getJobList = rptJdbcDao.getDesignation();

        return getJobList;
    }


    //job request status update
    @RequestMapping(value = "/risNewAppForms/jobRequestUpdate/{position}/{department}/{status}/{circularno}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public int jobRequestUpdate(@PathVariable String position, @PathVariable String department, @PathVariable Integer status, @PathVariable String circularno)
        throws URISyntaxException, UnsupportedEncodingException {
        rptJdbcDao.jobRequestUpdate(position, department, status, circularno);
        return 1;
    }

    //job request status update with circular number
    @RequestMapping(value = "/risNewAppForms/jobRequestUpdateByCircularNo",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public int jobRequestUpdateByCircularNo(@Valid @RequestBody RisJobStatusUDTO risJobStatusUDTO)
        throws URISyntaxException, UnsupportedEncodingException {
        rptJdbcDao.jobRequestUpdateByCircularNo(risJobStatusUDTO.getCircularNo(), risJobStatusUDTO.getStatus());
        return 1;
    }


    //getting applicant by circular and status
    @RequestMapping(value = "/risNewAppForms/getapplicantbycircularandstatus",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getapplicantbycircularandstatus(@Valid @RequestBody RisJobStatusUDTO risJobStatusUDTO)
        throws URISyntaxException, UnsupportedEncodingException {
        return rptJdbcDao.getapplicantbycircularandstatus(risJobStatusUDTO.getCircularNo(), risJobStatusUDTO.getStatus());

    }


    //api for returning one applicant corresponded status number
    @RequestMapping(value = "/risNewAppForms/getOneApplicantsByStatusid/{status}/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getOneApplicantByStatus(@PathVariable String status, @PathVariable String id)
        throws URISyntaxException, UnsupportedEncodingException {
        return rptJdbcDao.getOneApplicantByStatus(id, status);

    }


    //api for returning list with corresponded status number with amount of certain number
    @RequestMapping(value = "/risNewAppForms/getApplicantsByStatusWithNumber/{status}/{number}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getApplicantsByStatusWithNumber(@PathVariable Long status, @PathVariable Long number)
        throws URISyntaxException, UnsupportedEncodingException {
        return rptJdbcDao.getApplicantsByStatusWithNumber(status, number);

    }


    //api for returning certain objects from top numbers
    @RequestMapping(value = "/risNewAppForms/findById/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RisNewAppForm> findbyId(@PathVariable Long id)
        throws URISyntaxException, UnsupportedEncodingException {
        return risNewAppFormRepository.findById(id);
    }


    //email and sms send general
    @RequestMapping(value = "/risNewAppForms/emailSend/{id}/{messagebody}/{messageSubject}/{venue_name}/{exam_date}/{exam_time}/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)

    @Timed
    public int emailSend(@PathVariable String id, @PathVariable String messagebody, @PathVariable String messageSubject, @PathVariable String venue_name, @PathVariable String exam_date, @PathVariable String exam_time, @PathVariable String status)
        throws URISyntaxException, UnsupportedEncodingException {
        System.out.println("received" + id);
        System.out.println("Received messagebody " + messagebody);
        System.out.println("Received messageSubject " + messageSubject);
        System.out.println("Received venue name " + venue_name);
        System.out.println("Received exam date " + exam_date);
        System.out.println("Received exam time " + exam_time);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        List<Map<String, Object>> v = rptJdbcDao.smsandemail(id);
        System.out.println(v.size());
        for (Map<String, Object> map : v) {
            String tempAPplicantName = map.get("APPLICANTS_NAME_EN").toString();
            String emailofApplicant = map.get("EMAIL").toString();
            String phoneofApplicant = map.get("CONTACT_PHONE").toString();
            System.out.println("Applicant Name English " + tempAPplicantName + emailofApplicant);
            System.out.println("Applicant Email " + emailofApplicant);
            System.out.println("Applicant phone " + phoneofApplicant);
            String emailTemplateBody = "<html><body>" +
                "<center><table border='0'cellpadding='' cellspacing='0' style='margin:0;padding:0;max-width:700px;width:100%;height:150px;'><tbody>" +
                "<tr style='background-color:green;'><td style='text-align:right'></td>" +
                "<td style='background-color:green;font-size:1.7em;color:white;text-align:left'> Directorate of Technical Education</td> </tr>" +

                "<tr style='background-color:white; height:300px; vertical-align:top;'><td colspan='2'><p>Dear " + tempAPplicantName + " your viva exam is on " + exam_date + " at " + exam_time + "viva venue name is :" + venue_name + "" + " </p></td></tr>" +
                "<tr style='background-color:green;'><td colspan='2'' align='center'><p></p> </td></tr>" +
                "</table></center</body></html>";
            //turn on off email and sms option
            mailService.sendEmail(emailofApplicant, messageSubject, emailTemplateBody, false, true);
           /*  try {
                System.out.println("sms body"+messagebody);
                smsPushPull.sendGet(phoneofApplicant,messagebody);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Execution in sms"+e.toString());
            }*/
        }
        //currenlty i turned off saving the sms
        /*rptJdbcDao.updateForWrittenExam(id, status, venue_name, exam_date, exam_time);*/

        return 1;
    }


    @RequestMapping(value = "/risNewAppForms/sendingEmail/{id}/{messagebody}/{messageSubject}/{venue_name}/{exam_date}/{exam_time}/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public int sendingEmail(@PathVariable String id, @PathVariable String messagebody, @PathVariable String messageSubject, @PathVariable String venue_name, @PathVariable String exam_date, @PathVariable String exam_time, @PathVariable String status)
        throws URISyntaxException, UnsupportedEncodingException {
        System.out.println("received" + id);
        System.out.println("Received messagebody " + messagebody);
        System.out.println("Received messageSubject " + messageSubject);
        System.out.println("Received venue name " + venue_name);
        System.out.println("Received exam date " + exam_date);
        System.out.println("Received exam time " + exam_time);
        List<Map<String, Object>> v = rptJdbcDao.smsandemail(id);
        System.out.println(v.size());
        for (Map<String, Object> map : v) {
            String tempAPplicantName = map.get("APPLICANTS_NAME_EN").toString();
            String emailofApplicant = map.get("EMAIL").toString();
            String phoneofApplicant = map.get("CONTACT_PHONE").toString();
            System.out.println("Applicant Name English " + tempAPplicantName + emailofApplicant);
            System.out.println("Applicant Email " + emailofApplicant);
            System.out.println("Applicant phone " + phoneofApplicant);

            String emailTemplateBody = "<html><body>" +
                "<center><table border='0'cellpadding='' cellspacing='0' style='margin:0;padding:0;max-width:700px;width:100%;height:150px;'><tbody>" +
                "<tr style='background-color:green;'><td style='text-align:right'></td>" +
                "<td style='background-color:green;font-size:1.7em;color:white;text-align:left'> Directorate of Technical Education</td> </tr>" +

                "<tr style='background-color:white; height:300px; vertical-align:top;'><td colspan='2'><p>Congratulations " + tempAPplicantName + " !!<br> You have been selected for written exam. <br><br> Exam Details: <br> Venue Name : " + venue_name + "<br>Date :" + exam_date + "<br>Time:" + exam_time + "<br><br>Best of Luck !!" + " </p></td></tr>" +
                "<tr style='background-color:green;'><td colspan='2'' align='center'><p></p> </td></tr>" +
                "</table></center</body></html>";
            //turn on off email and sms option
            mailService.sendEmail(emailofApplicant, messageSubject, emailTemplateBody, false, true);

            /*String emailTemplateBody = "<html><body>" +
                "<center><table border='0'cellpadding='' cellspacing='0' style='margin:0;padding:0;max-width:700px;width:100%;height:150px;'><tbody>" +
                "<tr style='background-color:green;'><td style='text-align:right'></td>" +
                "<td style='background-color:green;font-size:1.7em;color:white;text-align:left'>Directorate of Technical Education</td> </tr>" +

                "<tr style='background-color:white; height:300px; vertical-align:top;'><td colspan='2'><p>Dear " + tempAPplicantName + " your written exam is on " + exam_date + " at" + exam_time + "viva venue name is :" + venue_name + "" + " </p></td></tr>" +
                "<tr style='background-color:green;'><td colspan='2'' align='center'><p></p> </td></tr>" +
                "</table></center</body></html>";
            //turn on off email and sms option
            mailService.sendEmail(emailofApplicant, messageSubject, messagebody, false, true);*/
            /*try {
                System.out.println("sms body"+messagebody);
                smsPushPull.sendGet(phoneofApplicant,messagebody);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Execution in sms"+e.toString());
            }*/
        }
        rptJdbcDao.updateForWrittenExam(id, status, venue_name, exam_date, exam_time);

        return 1;
    }


    @RequestMapping(value = "/risNewAppForms/sendingEmailAppointmentLetter/{regNo}/{messagebody}/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public int sendingEmailAppointmentLetter(@PathVariable String regNo, @PathVariable String messagebody, @PathVariable String status)
        throws URISyntaxException, UnsupportedEncodingException {

        System.out.println("received" + regNo);
        System.out.println("Received messagebody " + messagebody);
        String messageSubject = "Appointment Letter";
        List<Map<String, Object>> v = rptJdbcDao.smsandemail(regNo);
        System.out.println(v.size());


        for (Map<String, Object> map : v) {
            String tempAPplicantName = map.get("APPLICANTS_NAME_EN").toString();
            String emailofApplicant = map.get("EMAIL").toString();
            String phoneofApplicant = map.get("CONTACT_PHONE").toString();
            System.out.println("Applicant Name English " + tempAPplicantName + emailofApplicant);
            System.out.println("Applicant Email " + emailofApplicant);
            System.out.println("Applicant phone " + phoneofApplicant);
            //turn on off email and sms option
            mailService.sendEmail(emailofApplicant, messageSubject, messagebody, false, true);
           /* try {
                System.out.println("sms body"+messagebody);
                smsPushPull.sendGet(phoneofApplicant,messagebody);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Execution in sms"+e.toString());
            }*/
        }
        rptJdbcDao.updateforAppointmentLetter(regNo, status);
        return 1;
    }



    /*//job request controller
    @RequestMapping(value = "/risNewAppForms/jobRequest/{position}/{department}/{allocated}/{currentEmployee}/{availableVacancy}/{stataus}/{circularno:.*}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public int jobRequest(@PathVariable String position,@PathVariable String department,@PathVariable Integer allocated ,@PathVariable Integer currentEmployee,@PathVariable Integer availableVacancy, @PathVariable Integer stataus, @PathVariable String circularno)
        throws URISyntaxException, UnsupportedEncodingException {
      rptJdbcDao.putjobRequest(position,department,allocated,currentEmployee,availableVacancy,stataus,URLEncoder.encode(circularno,"UTF-8"));
        return 1;
    }*/
//request body

    @RequestMapping(value = "/risNewAppForms/jobRequest",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public int jobRequest(@Valid @RequestBody RisJobRequestDTO risJobRequestDTO)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>" + risJobRequestDTO.toString());
        rptJdbcDao.putjobRequest(risJobRequestDTO.getPosition(), risJobRequestDTO.getDepartment(), risJobRequestDTO.getAllocated(), risJobRequestDTO.getCurrentEmployee(), risJobRequestDTO.getAvailableVacancy(), risJobRequestDTO.getStataus(), risJobRequestDTO.getCircularno(), risJobRequestDTO.getJob_id());
        return 1;
    }


    //getting list with circular and seat number
    @RequestMapping(value = "/risNewAppForms/seatwithcircular",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> seatwithcircular(@Valid @RequestBody RISSeatwithCircularDTO risSeatwithCircularDTO)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>" + risSeatwithCircularDTO.toString());
        return rptJdbcDao.seatwithcircular(risSeatwithCircularDTO.getSeat(), risSeatwithCircularDTO.getCircularNo());

    }


    //for inserting written exam marks in to a application
    @RequestMapping(value = "/risNewAppForms/writtenmarksentry/{regNo}/{marks}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public int writtenmarksentry(@PathVariable String regNo, @PathVariable String marks)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>" + regNo + "marks  >>>>> " + marks);
        return rptJdbcDao.writtenexamentry(regNo, marks);

    }


    //for returning positions with corresponded circular number
    @RequestMapping(value = "/risNewAppForms/positionbycircular",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> positionbycircular(@Valid @RequestBody RisCircularNo risCircularNo)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>positionbycircular>>>>>>>>>>>>" + risCircularNo.toString());
        return rptJdbcDao.positionbycircular(risCircularNo.getCircularNo());

    }

    /* //for creating new job posting
     @RequestMapping(value = "/risNewAppForms/jobPosting/{EDUCATIONAL_QUALIFICATION}/{OTHER_QUALIFICATION}/{REMARKS}/{PUBLISH_DATE}/{APPLICATION_DATE}/{ATTACHMENT}/{ATTACHMENT_CONTENT_TYPE}/{CREATED_DATE}/{UPDATED_DATE}/{CREATED_BY}/{UPDATED_BY}/{STATUS}/{ATTACHMENT_IMG_NAME}/{POSITION_NAME}/{VACANT_POSITIONS}/{DEPARTMENT}",
         method = RequestMethod.POST,
         produces = MediaType.APPLICATION_JSON_VALUE)
     @Timed
     *//*List<Map<String, Object>>*//*
    public int jobPosting(@Valid @RequestBody RisCircularNo risCircularNo, @PathVariable String EDUCATIONAL_QUALIFICATION, @PathVariable String OTHER_QUALIFICATION, @PathVariable String REMARKS, @PathVariable String PUBLISH_DATE, @PathVariable String APPLICATION_DATE, @PathVariable String ATTACHMENT, @PathVariable String ATTACHMENT_CONTENT_TYPE, @PathVariable String CREATED_DATE, @PathVariable String UPDATED_DATE, @PathVariable Integer CREATED_BY, @PathVariable Integer UPDATED_BY, @PathVariable Integer STATUS, @PathVariable String ATTACHMENT_IMG_NAME, @PathVariable String POSITION_NAME, @PathVariable Integer VACANT_POSITIONS, @PathVariable String DEPARTMENT)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>jobPosting>>>>>>>>>>>>" + risCircularNo.toString() + VACANT_POSITIONS + POSITION_NAME + STATUS + EDUCATIONAL_QUALIFICATION);
        rptJdbcDao.jobPosting(risCircularNo.getCircularNo(), EDUCATIONAL_QUALIFICATION, OTHER_QUALIFICATION, REMARKS, PUBLISH_DATE, APPLICATION_DATE, ATTACHMENT, ATTACHMENT_CONTENT_TYPE, CREATED_DATE, UPDATED_DATE, CREATED_BY, UPDATED_BY, STATUS, ATTACHMENT_IMG_NAME, POSITION_NAME, VACANT_POSITIONS,DEPARTMENT);
        return 1;

    }*/
    //for creating new job posting with DTO method
    @RequestMapping(value = "/risNewAppForms/jobPosting",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    /*List<Map<String, Object>>*/
    public int jobPosting(@Valid @RequestBody RisJobPostingDTO risJobPostingDTO)
        throws URISyntaxException, UnsupportedEncodingException {
        String filepath = context.getRealPath("/") + "assets/dlms/";

        String filename = "risType";

        String extension = ".png";
        String bExtension = ".png";
        risJobPostingDTO.setAttachmentImageName(null);

        String applicantImg = risJobPostingDTO.getAttachmentContentType();


        if (risJobPostingDTO.getAttachmentContentType() != null && risJobPostingDTO.getAttachmentContentType().equals("application/pdf")) {
            extension = ".pdf";
        } else {
            extension = ".png";
        }

        if (risJobPostingDTO.getAttachment() != null) {
            try {
                System.out.println("Image Found");
                risJobPostingDTO.setAttachmentImageName(AttachmentUtil.saveRisApplicant(filepath, risJobPostingDTO.getAttachmentImageName(), extension, risJobPostingDTO.getAttachment()));
            } catch (Exception e) {
                System.out.println("Image Not Found");
                e.printStackTrace();
            }
        }





       /* log.debug(">>>>>>>>>>>jobPosting>>>>>>>>>>>>" + risCircularNo.toString() + VACANT_POSITIONS + POSITION_NAME + STATUS + EDUCATIONAL_QUALIFICATION);*/
        /*log.debug(">>>>>>>>>>>jobPosting>>>>>>>>>>>>" + risJobPostingDTO.toString());*/
        log.debug("comes to risjobposting >>>>>", risJobPostingDTO);
        System.out.println(risJobPostingDTO.toString());
        rptJdbcDao.jobPosting(risJobPostingDTO);
        /*rptJdbcDao.jobPosting(risCircularNo.getCircularNo(), EDUCATIONAL_QUALIFICATION, OTHER_QUALIFICATION, REMARKS, PUBLISH_DATE, APPLICATION_DATE, ATTACHMENT, ATTACHMENT_CONTENT_TYPE, CREATED_DATE, UPDATED_DATE, CREATED_BY, UPDATED_BY, STATUS, ATTACHMENT_IMG_NAME, POSITION_NAME, VACANT_POSITIONS,DEPARTMENT);*/
      /*  rptJdbcDao.jobPosting(risJobPostingDTO.getCircularno(), risJobPostingDTO.getEDUCATIONAL_QUALIFICATION(), risJobPostingDTO.getOTHER_QUALIFICATION(), risJobPostingDTO.getREMARKS(), risJobPostingDTO.getPUBLISH_DATE(), risJobPostingDTO.getAPPLICATION_DATE(), risJobPostingDTO.getATTACHMENT(), risJobPostingDTO.getATTACHMENT_CONTENT_TYPE(), risJobPostingDTO.getCREATED_DATE(), risJobPostingDTO.getUPDATED_DATE(), risJobPostingDTO.getSTATUS(), risJobPostingDTO.getATTACHMENT_IMG_NAME(), risJobPostingDTO.getPOSITION_NAME(), risJobPostingDTO.getVACANT_POSITIONS(), risJobPostingDTO.getDEPARTMENT());*/
        return 1;

    }

    //for creating new job posting with DTO method
    @RequestMapping(value = "/risNewAppForms/hello",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    /*List<Map<String, Object>>*/
    public int hello(@Valid @RequestBody RisCircularNo risJobPostingDTO)
        throws URISyntaxException, UnsupportedEncodingException {
        System.out.println("From hello with love");
        System.out.println(risJobPostingDTO.getCircularNo());
        System.out.println("From hello with love ends");
        return 1;

    }

    //getting posted job info retrieval of jobPosting >>>>>> search with circular number for getting the job posting to portal
    @RequestMapping(value = "/risNewAppForms/getjobPosting",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getjobPosting(@Valid @RequestBody RisCircularNo risCircularNo)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>" + risCircularNo.toString());
        return rptJdbcDao.getjobPosting(risCircularNo.getCircularNo());

    }

    //getting posted job info retrieval of jobPosting >>>>>> search with circular number for getting the job posting to portal
    @RequestMapping(value = "/risNewAppForms/getalljobPosting",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getalljobPosting()
        throws URISyntaxException, UnsupportedEncodingException {
        return rptJdbcDao.getalljobPosting();

    }


    //getting list with circular number
    @RequestMapping(value = "/risNewAppForms/gettingwithcircular",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> gettingwithcircular(@Valid @RequestBody RisCircularNo risCircularNo)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>" + risCircularNo.toString());
        return rptJdbcDao.gettingwithcircular(risCircularNo.getCircularNo());

    }

    //getting job by circularnumber
    @RequestMapping(value = "/risNewAppForms/getJobByCircular",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getJobByCircular(@Valid @RequestBody RisCircularNo risCircularNo)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>" + risCircularNo.toString());
        return rptJdbcDao.getJobByCircular(risCircularNo.getCircularNo());

    }

    //getting job by circularnumber with status
    @RequestMapping(value = "/risNewAppForms/getJobByCircularStatus/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getJobByCircularStatus(@PathVariable Integer status)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>getJobByCircularStatus: >> " + status);
        return rptJdbcDao.getJobByCircularStatus(status);

    }


    //getting applicant by reg_no of application
    @RequestMapping(value = "/risNewAppForms/getApplicantByRegNo/{regno}/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getApplicantByRegNo(@PathVariable Long regno, @PathVariable Integer status)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>getJobByCircularStatus: >> " + regno + status);
        return rptJdbcDao.getApplicantByRegNo(regno, status);

    }

    //getting list with circular number
    @RequestMapping(value = "/risNewAppForms/getJobCircular",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> getJobCircular()
        throws URISyntaxException, UnsupportedEncodingException {

        return rptJdbcDao.getJobCircular();

    }


    //updating applicant status one or multiple
    @RequestMapping(value = "/risNewAppForms/selection/{regNo}/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public boolean selection(@PathVariable String regNo, @PathVariable String status)
        throws URISyntaxException, UnsupportedEncodingException {
        rptJdbcDao.selection(regNo, status);
        return true;
    }

    /*User user = userService.createCustomUserInformationByUserModule(umracIdentitySetup.getUserName(), umracIdentitySetup.getuPw(), umracIdentitySetup.getEmpId(), null, emailBuilder, "en", "ROLE_MODULE", true, result.getRoleId().getId());*/

    //user registration
    @RequestMapping(value = "/risNewAppForms/userregistration/{username}/{password}/{firstname}/{lastname}/{email:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public int userregistration(@PathVariable String username, @PathVariable String password, @PathVariable String firstname, @PathVariable String lastname, @PathVariable String email, HttpServletRequest request)
        throws URISyntaxException, UnsupportedEncodingException {
        String langkey = "en";
        String user_role = "ROLE_APPLICANT";
        try {
            User user = userService.createCustomUserInformation(username, password, firstname, lastname, email, langkey, user_role);
            if (user != null) {
            /*Email email = new Email();*/
         /*     emailStatus = email.Send(emails);*/
                String baseUrl = request.getScheme() + // "http"
                    "://" + // "://"
                    request.getServerName() + // "myhost"
                    ":" + // ":"
                    request.getServerPort() + // "80"
                    request.getContextPath(); // "/myContextPath"
                String emailTemplate = "<html><body>" +
                    "<center><table border='0'cellpadding='' cellspacing='0' style='margin:0;padding:0;max-width:700px;width:100%;height:150px;'><tbody>" +
                    "<tr style='background-color:green;'><td style='text-align:right'></td>" +
                    "<td style='background-color:green;font-size:1.7em;color:white;text-align:left'>Directorate Of Technical Education</td> </tr>" +

                    "<tr style='background-color:white; height:300px; vertical-align:top;'><td colspan='2'><p>Dear " + firstname + " " + lastname + "<br>Click <a href='" + baseUrl + "/api/mail/active/" + user.getLogin() + "/" + user.getActivationKey() + "'> here</a> to activate your account. </p></td></tr>" +
                    "<tr style='background-color:green;'><td colspan='2'' align='center'><p></p> </td></tr>" +
                    "</table></center</body></html>";

                System.out.println("User Created");


                mailService.sendEmail(email, "Registration completed", emailTemplate, false, true);
               /* mailService.sendEmail(email, "User Created", "Dear " + firstname + " " + lastname + " you user name has ben create", false, true);*/
            /*log.debug("Email Send Status of USER ROLE ASSIGN : " + emailStatus);*/
            }

        } catch (Exception e) {
            log.debug("Exception in save role at JHI_USER entity");
        }

        return 1;
    }


    @RequestMapping(value = "/risNewAppForms/uniqueCircular",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> uniqueCircular(@Valid @RequestBody RisCircularNo risCircularNo)
        throws URISyntaxException, UnsupportedEncodingException {
        log.debug(">>>>>>>>>>>uniqueCircular>>>>>>>>>>>>" + risCircularNo.toString());
        return rptJdbcDao.uniqueCircular(risCircularNo.getCircularNo());

    }
}
