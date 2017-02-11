package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmployee;
import gov.step.app.domain.Institute;
import gov.step.app.domain.MpoSalaryFlow;
import gov.step.app.domain.NotificationStep;
import gov.step.app.repository.InstEmployeeRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.MpoSalaryFlowRepository;
import gov.step.app.repository.NotificationStepRepository;
import gov.step.app.repository.search.MpoSalaryFlowSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.MailService;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.Emails;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MpoSalaryFlow.
 */
@RestController
@RequestMapping("/api")
public class MpoSalaryFlowResource {

    private final Logger log = LoggerFactory.getLogger(MpoSalaryFlowResource.class);

    @Inject
    private MpoSalaryFlowRepository mpoSalaryFlowRepository;

    @Inject
    private MpoSalaryFlowSearchRepository mpoSalaryFlowSearchRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private MailService mailService;


    /**
     * POST  /mpoSalaryFlows -> Create a new mpoSalaryFlow.
     */
    @RequestMapping(value = "/mpoSalaryFlows",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoSalaryFlow> createMpoSalaryFlow(@RequestBody MpoSalaryFlow mpoSalaryFlow) throws URISyntaxException {
        log.debug("REST request to save MpoSalaryFlow : {}", mpoSalaryFlow);
        if (mpoSalaryFlow.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoSalaryFlow cannot already have an ID").body(null);
        }
        MpoSalaryFlow result = mpoSalaryFlowRepository.save(mpoSalaryFlow);
        mpoSalaryFlowSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mpoSalaryFlows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoSalaryFlow", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoSalaryFlows -> Updates an existing mpoSalaryFlow.
     */
    @RequestMapping(value = "/mpoSalaryFlows",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoSalaryFlow> updateMpoSalaryFlow(@RequestBody MpoSalaryFlow mpoSalaryFlow) throws URISyntaxException {
        log.debug("REST request to update MpoSalaryFlow : {}", mpoSalaryFlow);
        if (mpoSalaryFlow.getId() == null) {
            return createMpoSalaryFlow(mpoSalaryFlow);
        }

        MpoSalaryFlow result = mpoSalaryFlowRepository.save(mpoSalaryFlow);
        mpoSalaryFlowSearchRepository.save(mpoSalaryFlow);

        if (!mpoSalaryFlow.getLevels().toString().equalsIgnoreCase("7")) {
        /*Add Notification*/

            NotificationStep notificationSteps = new NotificationStep();
            notificationSteps.setNotification(" Report for Month - " + mpoSalaryFlow.getReportMonth().toString().trim() + ", Year - " + mpoSalaryFlow.getReportYear().toString().trim() + " Approve status -> "+mpoSalaryFlow.getApproveStatus());
            notificationSteps.setStatus(true);
            notificationSteps.setUrls("mpo.dashboard");
            notificationSteps.setUserId(SecurityUtils.getCurrentUserId());
            notificationStepRepository.save(notificationSteps);
        }


        if (mpoSalaryFlow.getLevels().toString().equalsIgnoreCase("7")) {
            if (mpoSalaryFlow.getReportName().toString().equalsIgnoreCase("MPO Sheet")) {
                /*
                select distinct mpo_salary_dtl.institute_id
                from mpo_salary_mst
                left join mpo_salary_dtl on mpo_salary_mst.id = mpo_salary_dtl.salary_mst_id
                where mpo_salary_mst.for_the_month='September' and mpo_salary_mst.for_the_year=2016;


                select mpo_salary_dtl.inst_emp_id
                from mpo_salary_mst
                left join mpo_salary_dtl on mpo_salary_mst.id = mpo_salary_dtl.salary_mst_id
                where mpo_salary_mst.for_the_month='September' and mpo_salary_mst.for_the_year=2016;
                 */
                System.out.println("\n IN : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + mpoSalaryFlow.getReportName().toString());
                List<Map<String, Object>> instituteIds = null;
                System.out.println("\n parameters : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+ mpoSalaryFlow.getReportMonth().toString().trim() +" --- "+Integer.parseInt(mpoSalaryFlow.getReportYear().toString().trim()));
                instituteIds = rptJdbcDao.findInstituteIdsByMonthAndYearMPOSheet(mpoSalaryFlow.getReportMonth().toString().trim(), Integer.parseInt(mpoSalaryFlow.getReportYear().toString().trim()));

                for (int i = 0; i <= instituteIds.size(); i++) {
                    System.out.println("\n Institute ID : "+instituteIds.get(i).values().toString().substring(1,instituteIds.get(i).values().toString().length()-1));
                    String instituteProcessedId = instituteIds.get(i).values().toString().substring(1,instituteIds.get(i).values().toString().length()-1);
                    Institute institute = instituteRepository.findOne(Long.parseLong(instituteProcessedId));
                    //institute.getUser();
                    //institute.getEmail();
                    /*
                    Add Notification
                     */
                    NotificationStep notificationSteps = new NotificationStep();
                    notificationSteps.setNotification("Dear " + institute.getName() + ", Your institute MPO sheet processed for Month - " + mpoSalaryFlow.getReportMonth().toString().trim() + ", Year - " + mpoSalaryFlow.getReportYear().toString().trim());
                    notificationSteps.setStatus(true);
                    notificationSteps.setUrls("mpo.dashboard");
                    notificationSteps.setUserId(institute.getUser().getId());
                    notificationStepRepository.save(notificationSteps);
                    System.out.println("\n save notfication : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + institute.getUser().getId());
                    /*
                    Send Email
                     */
                    try {
                        Boolean emailStatus = false;
                        Emails emails = new Emails();
                        emails.setTo(institute.getEmail());
                        //emails.setCc("ranaarchive99@gmail.com");
                        emails.setSubject("MPO Sheet for Month - " + mpoSalaryFlow.getReportMonth().toString().trim() + ", Year - " + mpoSalaryFlow.getReportYear().toString().trim());
                        emails.setEmailBody("Dear " + institute.getName() + ", Your institute MPO sheet processed for Month - " + mpoSalaryFlow.getReportMonth().toString().trim() + ", Year - " + mpoSalaryFlow.getReportYear().toString().trim() + "<br><br> Regards, <br>STEP Admin");
                        emails.setLogged_user_id(SecurityUtils.getCurrentUserId());
                        emails.setEmail_type("Text Email");

                        String emailBuilder = institute.getEmail();
                        mailService.sendEmail(emails.getTo(), emails.getSubject(), emails.getEmailBody(), false, true);
                        log.debug("Email Send TO Institute : " + institute.getName());
                        System.out.println("\n Email Send TO Institute : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + institute.getName());

                    } catch (Exception e) {
                        log.debug("Exception in send and notify (Institute) at mpoSalaryFlow entity");
                    }

                }

                List<Map<String, Object>> teacherIds = null;
                teacherIds = rptJdbcDao.findTeacherIdsByMonthAndYearMPOSheet(mpoSalaryFlow.getReportMonth().toString().trim(), Integer.parseInt(mpoSalaryFlow.getReportYear().toString().trim()));

                for (int i = 0; i <= teacherIds.size(); i++) {

                    String teacherProcessedId = teacherIds.get(i).values().toString().substring(1,teacherIds.get(i).values().toString().length()-1);
                    InstEmployee instEmployee = instEmployeeRepository.findOne(Long.parseLong(teacherProcessedId));
                    //institute.getUser();
                    //institute.getEmail();

                    /*Add Notification*/

                    NotificationStep notificationSteps = new NotificationStep();
                    notificationSteps.setNotification("Dear " + instEmployee.getName() + ", Your MPO sheet processed for Month - " + mpoSalaryFlow.getReportMonth().toString().trim() + ", Year - " + mpoSalaryFlow.getReportYear().toString().trim());
                    notificationSteps.setStatus(true);
                    notificationSteps.setUrls("mpo.dashboard");
                    notificationSteps.setUserId(instEmployee.getUser().getId());
                    notificationStepRepository.save(notificationSteps);

                    /*Send Email*/

                    try {
                        Boolean emailStatus = false;
                        Emails emails = new Emails();
                        emails.setTo(instEmployee.getEmail());
                        //emails.setCc("ranaarchive99@gmail.com");
                        emails.setSubject("MPO Sheet for Month - " + mpoSalaryFlow.getReportMonth().toString().trim() + ", Year - " + mpoSalaryFlow.getReportYear().toString().trim());
                        emails.setEmailBody("Dear " + instEmployee.getName() + ", Your MPO sheet processed for Month - " + mpoSalaryFlow.getReportMonth().toString().trim() + ", Year - " + mpoSalaryFlow.getReportYear().toString().trim() + "<br><br> Regards, <br>STEP Admin");
                        emails.setLogged_user_id(SecurityUtils.getCurrentUserId());
                        emails.setEmail_type("Text Email");

                        String emailBuilder = instEmployee.getEmail();
                        mailService.sendEmail(emails.getTo(), emails.getSubject(), emails.getEmailBody(), false, true);
                        log.debug("Email Send TO Teacher : " + instEmployee.getName());

                    } catch (Exception e) {
                        log.debug("Exception in send and notify (Teacher) at mpoSalaryFlow entity");
                    }

                }

                /*Add Notification*/

                NotificationStep notificationSteps = new NotificationStep();
                notificationSteps.setNotification(" Your Requested MPO sheet processed for Month - " + mpoSalaryFlow.getReportMonth().toString().trim() + ", Year - " + mpoSalaryFlow.getReportYear().toString().trim());
                notificationSteps.setStatus(true);
                notificationSteps.setUrls("mpo.dashboard");
                notificationSteps.setUserId(SecurityUtils.getCurrentUserId());
                notificationStepRepository.save(notificationSteps);
            }
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoSalaryFlow", mpoSalaryFlow.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mpoSalaryFlows -> get all the mpoSalaryFlows.
     */
    @RequestMapping(value = "/mpoSalaryFlows",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoSalaryFlow>> getAllMpoSalaryFlows(Pageable pageable)
        throws URISyntaxException {

        Page<MpoSalaryFlow> page = mpoSalaryFlowRepository.findAll(pageable);

        if (SecurityUtils.isCurrentUserInRole("ROLE_MINISTRY")) {
            page = mpoSalaryFlowRepository.findAllRequestByRole(pageable, "ROLE_MINISTRY");
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_AG")) {
            page = mpoSalaryFlowRepository.findAllRequestByRole(pageable, "ROLE_AG");
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_BANK")) {
            page = mpoSalaryFlowRepository.findAllRequestByRole(pageable, "ROLE_BANK");
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_MPOADMIN")) {
            page = mpoSalaryFlowRepository.findAllRequestByRole(pageable, "ROLE_MPOADMIN");
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
            page = mpoSalaryFlowRepository.findAllRequestByRole(pageable, "ROLE_INSTITUTE");
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoSalaryFlows");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoSalaryFlows/:id -> get the "id" mpoSalaryFlow.
     */
    @RequestMapping(value = "/mpoSalaryFlows/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoSalaryFlow> getMpoSalaryFlow(@PathVariable Long id) {
        log.debug("REST request to get MpoSalaryFlow : {}", id);


        return Optional.ofNullable(mpoSalaryFlowRepository.findOne(id))
            .map(mpoSalaryFlow -> new ResponseEntity<>(
                mpoSalaryFlow,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mpoSalaryFlows/:id -> delete the "id" mpoSalaryFlow.
     */
    @RequestMapping(value = "/mpoSalaryFlows/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoSalaryFlow(@PathVariable Long id) {
        log.debug("REST request to delete MpoSalaryFlow : {}", id);
        mpoSalaryFlowRepository.delete(id);
        mpoSalaryFlowSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoSalaryFlow", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoSalaryFlows/:query -> search for the mpoSalaryFlow corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoSalaryFlows/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoSalaryFlow> searchMpoSalaryFlows(@PathVariable String query) {
        return StreamSupport
            .stream(mpoSalaryFlowSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
