package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.TempEmployerStatus;
import gov.step.app.repository.*;
import gov.step.app.repository.search.TempEmployerSearchRepository;
import gov.step.app.repository.search.UserSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.service.MailService;
import gov.step.app.web.rest.util.AttachmentUtil;
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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.net.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TempEmployer.
 */
@RestController
@RequestMapping("/api")
public class TempEmployerResource {

    private final Logger log = LoggerFactory.getLogger(TempEmployerResource.class);

    @Inject
    private TempEmployerRepository tempEmployerRepository;

    @Inject
    private TempEmployerSearchRepository tempEmployerSearchRepository;

    @Inject
    private MailService mailService;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserSearchRepository userSearchRepository;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    /**
     * POST  /tempEmployers -> Create a new tempEmployer.
     */
    @RequestMapping(value = "/tempEmployers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TempEmployer> createTempEmployer(@Valid @RequestBody TempEmployer tempEmployer) throws URISyntaxException {
        log.debug("REST request to save TempEmployer : {}", tempEmployer);
        if (tempEmployer.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tempEmployer cannot already have an ID").body(null);
        }

        String filepath="/backup/jobportal/";
        String filename="";
        if(tempEmployer.getCompanyLogoName() != null){
            filename=tempEmployer.getCompanyLogoName();
        }

        //AttachmentUtil.saveAttachment(filepath,filename,attachment.getFile());
        if(tempEmployer.getCompanyLogo() !=null){
            try {
                tempEmployer.setCompanyLogoName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, tempEmployer.getCompanyLogo()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //attachment.setFileContentName(attachment.getAttachmentCategory().getAttachmentName());
            tempEmployer.setCompanyLogoName(null);
        }

        tempEmployer.setCompanyLogo(null);
        tempEmployer.setDateCrated(LocalDate.now());
        tempEmployer.setDateModified(LocalDate.now());

        String emailTemplate="<html><body>" +
            "<center><table border='0'cellpadding='' cellspacing='0' style='margin:0;padding:0;max-width:700px;width:100%;height:100px;'><tbody>"+
            "<tr style='background-color:green;'><td style='text-align:right'></td>"+
            "<td style='background-color:green;font-size:1.7em;color:white;text-align:left'>Directorate of Technical Education</td> </tr>" +

            "<tr style='background-color:white; height:350px; vertical-align:top;'><td colspan='2'><p>Dear "+tempEmployer.getName()+"<br>Thank you for registration. <br> Please, wait for approval.</p></td></tr>"+
            "<tr style='background-color:green;'><td colspan='2'' align='center'><p></p> </td></tr>"+

            "</table></center</body></html>";
        mailService.sendEmail(tempEmployer.getUser().getEmail(), "Registration completed",emailTemplate, false, true);

        TempEmployer result = tempEmployerRepository.save(tempEmployer);
        tempEmployerSearchRepository.save(result);

        if(instEmployeeRepository.findAllJpAdminOfInstitute(tempEmployer.getInstitute().getId()).size() > 0){
            NotificationStep notificationSteps = new NotificationStep();
            notificationSteps.setNotification("A new organization is pending for approval");
            notificationSteps.setStatus(true);
            notificationSteps.setUrls("employer-management");
            notificationSteps.setUserId(instEmployeeRepository.findAllJpAdminOfInstitute(tempEmployer.getInstitute().getId()).get(0).getUser().getId());
            notificationStepRepository.save(notificationSteps);
        }

        return ResponseEntity.created(new URI("/api/tempEmployers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tempEmployer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tempEmployers -> Updates an existing tempEmployer.
     */
    @RequestMapping(value = "/tempEmployers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TempEmployer> updateTempEmployer(@Valid @RequestBody TempEmployer tempEmployer) throws URISyntaxException {
        log.debug("REST request to update TempEmployer : {}", tempEmployer);
        if (tempEmployer.getId() == null) {
            return createTempEmployer(tempEmployer);
        }

        log.debug("temp employer status :"+tempEmployer.getStatus());

        String filepath="/backup/jobportal/";
        String filename="";
        if(tempEmployer.getCompanyLogoName() != null){
            filename=tempEmployer.getCompanyLogoName();
        }

        //AttachmentUtil.saveAttachment(filepath,filename,attachment.getFile());
        if(tempEmployer.getCompanyLogo() !=null){
            try {
                tempEmployer.setCompanyLogoName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, tempEmployer.getCompanyLogo()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //attachment.setFileContentName(attachment.getAttachmentCategory().getAttachmentName());
            tempEmployer.setCompanyLogoName(null);
        }

        tempEmployer.setCompanyLogo(null);
        User user = userRepository.findOneByLogin(tempEmployer.getUser().getLogin()).get();
        Set<Authority> authorities = user.getAuthorities();
        if(tempEmployer.getStatus().equals(TempEmployerStatus.approved)){
            user.setActivated(true);
            /*if(authorities.contains(authorityRepository.findOne(AuthoritiesConstants.EMPLOYER))){
                Authority authority = authorityRepository.findOne(AuthoritiesConstants.EMPLOYER);
                Set<Authority> authorities2 = new HashSet<>();
                authorities.add(authority);
                user.setAuthorities(authorities2);
            }*/

            log.debug("user with auth >>>>>>>>>>>>> "+user.toString());

        }else if(tempEmployer.getStatus().equals(TempEmployerStatus.rejected) || tempEmployer.getStatus().equals(TempEmployerStatus.deActivated)){
            user.setActivated(false);
        }

        userRepository.save(user);
        userSearchRepository.save(user);

        TempEmployer result = tempEmployerRepository.save(tempEmployer);
        tempEmployerSearchRepository.save(result);

        if(tempEmployer.getStatus().equals(TempEmployerStatus.approved)) {
            String emailTemplate = "<html><body>" +
                "<center><table border='0'cellpadding='' cellspacing='0' style='margin:0;padding:0;max-width:700px;width:100%;height:100px;'><tbody>" +
                "<tr style='background-color:green;'><td style='text-align:right'></td>" +
                "<td style='background-color:green;font-size:1.7em;color:white;text-align:left'>Directorate of Technical Education</td> </tr>" +

                "<tr style='background-color:white; height:350px; vertical-align:top;'><td colspan='2'><p>Dear " + tempEmployer.getName() + "<br>Your organization has approved by admin. You can login now. </p></td></tr>" +
                "<tr style='background-color:green;'><td colspan='2'' align='center'><p></p> </td></tr>" +

                "</table></center</body></html>";
            mailService.sendEmail(tempEmployer.getUser().getEmail(), "Registration completed", emailTemplate, false, true);
        }



        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tempEmployer", tempEmployer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tempEmployers -> get all the tempEmployers.
     */
    @RequestMapping(value = "/tempEmployers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TempEmployer>> getAllTempEmployers(Pageable pageable)
        throws URISyntaxException {
        Page<TempEmployer> page = tempEmployerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tempEmployers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tempEmployers/:id -> get the "id" tempEmployer.
     */
    @RequestMapping(value = "/tempEmployers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TempEmployer> getTempEmployer(@PathVariable Long id) throws Exception {
        log.debug("REST request to get TempEmployer : {}", id);

        TempEmployer tempEmployer1 = tempEmployerRepository.findOne(id);
        String filepath="/backup/jobportal/";
        if(tempEmployer1.getCompanyLogoName() !=null && tempEmployer1.getCompanyLogoName().length()>0){
            tempEmployer1.setCompanyLogo(AttachmentUtil.retriveAttachment(filepath, tempEmployer1.getCompanyLogoName()));
        }

        return Optional.ofNullable(tempEmployer1)
            .map(tempEmployer -> new ResponseEntity<>(
                tempEmployer,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /tempEmployers/user/:id -> get the tempEmployers by user id
     */
    @RequestMapping(value = "/tempEmployers/user/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TempEmployer> getTempEmployerByUserId(@PathVariable Long id) {
        log.debug("REST request to get TempEmployer : {}", id);
        return Optional.ofNullable(tempEmployerRepository.findOneByStatus(id, TempEmployerStatus.pending,TempEmployerStatus.update))
            .map(tempEmployer -> new ResponseEntity<>(
                tempEmployer,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tempEmployers/:id -> delete the "id" tempEmployer.
     */
    @RequestMapping(value = "/tempEmployers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTempEmployer(@PathVariable Long id) {
        log.debug("REST request to delete TempEmployer : {}", id);
        tempEmployerRepository.delete(id);
        tempEmployerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tempEmployer", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tempEmployers/:query -> search for the tempEmployer corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/tempEmployers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempEmployer> searchTempEmployers(@PathVariable String query) {
        return StreamSupport
            .stream(tempEmployerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @RequestMapping(value = "/tempEmployers/approved",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempEmployer> getApprovedEmployers() {
        return tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.approved);
    }

    @RequestMapping(value = "/tempEmployers/approved2/institute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempEmployer> getApprovedEmployersOfInstitute() {
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        if(institute == null){
            institute = instEmployeeRepository.findInstituteByUserIsCurrentUser();
        }
        List<TempEmployer> tempEmployers = new ArrayList<TempEmployer>();
        tempEmployers.addAll(tempEmployerRepository.findEmployerByInstituteAndStatus(TempEmployerStatus.deActivated, institute.getId()));
        tempEmployers.addAll(tempEmployerRepository.findEmployerByInstituteAndStatus(TempEmployerStatus.approved, institute.getId()));

        return tempEmployers;
    }

    @RequestMapping(value = "/tempEmployers/approved/institute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TempEmployer>> getApprovedEmployersOfInstitute1(Pageable pageable) throws URISyntaxException {
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        if(institute == null){
            institute = instEmployeeRepository.findInstituteByUserIsCurrentUser();
        }
        Page<TempEmployer> page = tempEmployerRepository.findApprovedEmployerByInstituteAndStatus(pageable, TempEmployerStatus.approved,TempEmployerStatus.deActivated, institute.getId());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tempEmployers/approved/institute");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/tempEmployers/rejected",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempEmployer> getRejectedEmployers() {
        List<TempEmployer> tempEmployers = new ArrayList<TempEmployer>();
        tempEmployers.addAll(tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.rejected));
        tempEmployers.addAll(tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.deActivated));
        tempEmployers.addAll(tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.updateReject));
        return tempEmployers;
    }

    @RequestMapping(value = "/tempEmployers/rejected/institute2",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempEmployer> getRejectedEmployersInstitute() {

        Institute institute = instituteRepository.findOneByUserIsCurrentUser();

        if(institute == null){
            institute = instEmployeeRepository.findInstituteByUserIsCurrentUser();
        }

        //List<TempEmployer> tempEmployers = new ArrayList<TempEmployer>();
        /*tempEmployers.addAll(tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.rejected));
        tempEmployers.addAll(tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.deActivated));
        tempEmployers.addAll(tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.updateReject));*/

        //tempEmployers.addAll();
        //tempEmployers.addAll(tempEmployerRepository.findEmployerByInstituteAndStatus(TempEmployerStatus.updateReject, institute.getId()));
        return tempEmployerRepository.findEmployerByInstituteAndStatus(TempEmployerStatus.rejected, institute.getId());
    }

    @RequestMapping(value = "/tempEmployers/pending",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempEmployer> getPendingEmployers() {

        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        List<TempEmployer> tempEmployers = new ArrayList<TempEmployer>();
        tempEmployers.addAll(tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.pending));
        tempEmployers.addAll(tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.update));


        return tempEmployers;
    }

    @RequestMapping(value = "/tempEmployers/pending/institute2",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempEmployer> getPendingEmployersInstitute(Pageable pageable) {

        //InstEmployee instEmployee = instEmployeeRepository.findCurrentOne();
        Institute institute = instEmployeeRepository.findCurrentOne().getInstitute();
        List<TempEmployer> tempEmployers = new ArrayList<TempEmployer>();
        if(tempEmployerRepository.findEmployerByInstituteAndStatus(TempEmployerStatus.pending, institute.getId()) != null){
            tempEmployers.addAll(tempEmployerRepository.findEmployerByInstituteAndStatus(TempEmployerStatus.pending, institute.getId()));
        }
        if(tempEmployerRepository.findEmployerByInstituteAndStatus(TempEmployerStatus.update, institute.getId()) != null){
            tempEmployers.addAll(tempEmployerRepository.findEmployerByInstituteAndStatus(TempEmployerStatus.update, institute.getId()));
        }

        return tempEmployers;
    }


    @RequestMapping(value = "/tempEmployers/pending/institute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TempEmployer>> getPendingEmployersOfInstitute1(Pageable pageable) throws URISyntaxException {
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        if(institute == null){
            institute = instEmployeeRepository.findInstituteByUserIsCurrentUser();
        }
        Page<TempEmployer> page = tempEmployerRepository.findApprovedEmployerByInstituteAndStatus(pageable, TempEmployerStatus.pending,TempEmployerStatus.update, institute.getId());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tempEmployers/pending/institute");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/tempEmployers/rejected/institute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TempEmployer>> getRejectedEmployersOfInstitute1(Pageable pageable) throws URISyntaxException {
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        if(institute == null){
            institute = instEmployeeRepository.findInstituteByUserIsCurrentUser();
        }
        Page<TempEmployer> page = tempEmployerRepository.findRejectedEmployerByInstituteAndStatus(pageable, TempEmployerStatus.rejected, institute.getId());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tempEmployers/rejected/institute");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/tempEmployersByStatus/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempEmployer> getEmployersByStatus(@PathVariable String status) {
        return tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.valueOf(status));
    }

    @RequestMapping(value = "/tempEmployersByInstituteAndStatus/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempEmployer> getEmployersByInstituteAndStatus(@PathVariable String status) {
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        return tempEmployerRepository.findEmployerByInstituteAndStatus(TempEmployerStatus.valueOf(status),institute.getId());
    }


    @RequestMapping(value = "/tempEmployer/checkCompanyName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkCompanyByName(@RequestParam String value) {

        List<TempEmployer> tempEmployers = tempEmployerRepository.findEmployerByName(value.toLowerCase());

        //Cat category = catRepository.findOneByName(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(tempEmployers.size() < 1){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }

    /*@RequestMapping(value = "/tempEmployers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempEmployer> searchTempEmployersByStatus(@PathVariable String query) {

        try {
            query = URLDecoder.decode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("*********** "+query);
        List<String> items = Arrays.asList(query.split("\\s*,\\s*"));

        List<TempEmployer> tempEmployers = new ArrayList<TempEmployer>();
        for(String item: items){
            tempEmployers.addAll(tempEmployerRepository.findEmployerByStatus(TempEmployerStatus.valueOf(item)));
        }
        return tempEmployers;
    }*/


}
