package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.repository.*;
import gov.step.app.repository.search.UmracRoleAssignUserSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.MailService;
import gov.step.app.service.UserService;
import gov.step.app.web.rest.util.Email;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UmracRoleAssignUser.
 */
@RestController
@RequestMapping("/api")
public class UmracRoleAssignUserResource {

    private final Logger log = LoggerFactory.getLogger(UmracRoleAssignUserResource.class);

    @Inject
    private UmracRoleAssignUserRepository umracRoleAssignUserRepository;

    @Inject
    private UmracRoleAssignUserSearchRepository umracRoleAssignUserSearchRepository;

    @Inject
    private UserService userService;

    @Inject
    private UmracIdentitySetupRepository umracIdentitySetupRepository;

    @Inject
    private UmracRoleSetupRepository umracRoleSetupRepository;

    @Inject
    private UmracRightsSetupRepository umracRightsSetupRepository;

    /*@Inject
    private JhiUserAuthorityRepository jhiUserAuthorityRepository;*/


    @Inject
    private MailService mailService;

    /**
     * POST  /umracRoleAssignUsers -> Create a new umracRoleAssignUser.
     */
    @RequestMapping(value = "/umracRoleAssignUsers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRoleAssignUser> createUmracRoleAssignUser(@RequestBody UmracRoleAssignUser umracRoleAssignUser) throws URISyntaxException {
        log.debug("REST request to save UmracRoleAssignUser : {}", umracRoleAssignUser);
        if (umracRoleAssignUser.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new umracRoleAssignUser cannot already have an ID").body(null);
        }
        //UmracRoleAssignUser checkDuplicate = umracRoleAssignUserRepository.findByUserId(umracRoleAssignUser.getUserId().getId());

        /*if(checkDuplicate.getId() != null){

            UmracIdentitySetup umracIdentitySetup = new UmracIdentitySetup();
            umracIdentitySetup = umracIdentitySetupRepository.findOne(umracRoleAssignUser.getUserId().getId());

            UmracRoleAssignUser result = umracRoleAssignUserRepository.save(umracRoleAssignUser);
            //User userRoleSave=null;
            System.out.println("Role ID :"+result.getRoleId().getId());
            try {
                String emailBuilder = umracIdentitySetup.getUserName()+"@gmail.com";
                userService.createCustomUserInformationByUserModule(umracIdentitySetup.getUserName(), umracIdentitySetup.getuPw(), umracIdentitySetup.getEmpId(), null, emailBuilder, "en", "ROLE_MODULE", true, result.getRoleId().getId());
            }catch (Exception e){
                log.debug("Exception in save role at JHI_USER entity");
            }

            umracRoleAssignUserSearchRepository.save(result);
            return ResponseEntity.created(new URI("/api/umracRoleAssignUsers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("umracRoleAssignUser", result.getId().toString()))
                .body(result);
        } else {
            return ResponseEntity.created(new URI("/api/umracRoleAssignUsers/" + checkDuplicate.getId()))
                .headers(HeaderUtil.createEntityDuplicateAlert("umracRoleAssignUser", checkDuplicate.getId().toString()))
                .body(checkDuplicate);
        }*/

        UmracIdentitySetup umracIdentitySetup = new UmracIdentitySetup();
        umracIdentitySetup = umracIdentitySetupRepository.findOne(umracRoleAssignUser.getUserId().getId());

        UmracRoleAssignUser result = umracRoleAssignUserRepository.save(umracRoleAssignUser);
        //User userRoleSave=null;
        System.out.println("\n Role ID : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+result.getRoleId().getId());



        try {
            List<UmracRightsSetup> umracRightsSetupList = umracRightsSetupRepository.findByRoleId(result.getRoleId().getId());
            Boolean emailStatus = false;
            Emails emails =  new Emails();
            emails.setTo(umracIdentitySetup.getEmail().trim());
            //emails.setTo("zoynobtumpa@gmail.com");
            //emails.setCc("ranaarchive99@gmail.com");
            emails.setSubject("Test Email");
            emails.setEmailBody("Test email by STEP as a example. " + "<br><br> Regards, <br>STEP Admin");
            emails.setLogged_user_id(SecurityUtils.getCurrentUserId());
            emails.setEmail_type("Text Email");

            //String emailBuilder = umracIdentitySetup.getUserName()+"@gmail.com";
            String emailBuilder = umracIdentitySetup.getEmail().trim();

            boolean roleProcess = false;
            String roleName="ROLE_MODULE";
            for(UmracRightsSetup a :umracRightsSetupList){
                System.out.println("\n Sub module id : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+a.getSubModule_id().toString());
                String submoduleId = a.getSubModule_id().toString();
                if(submoduleId.equals("165492151454")){
                    roleProcess = true;
                    roleName="ROLE_TIS_DASH";
                }else if(submoduleId.equals("165496151454")){
                    roleProcess = true;
                    roleName="ROLE_TIS_DASH";
                }else if(submoduleId.equals("165504151454")){
                    roleProcess = true;
                    roleName="ROLE_TIS_DASH";
                }else if(submoduleId.equals("165500151454")){
                    roleProcess = true;
                    roleName="ROLE_TIS_DASH";
                }
            }
            System.out.println("\n roleProcess : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+roleProcess);
            System.out.println("\n roleName : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+roleName);
            User user = userService.createCustomUserInformationByUserModule(umracIdentitySetup.getUserName(), umracIdentitySetup.getuPw(), umracIdentitySetup.getEmpId(), null, emailBuilder, "en", roleName, true, result.getRoleId().getId());
            if(user != null){
                mailService.sendEmail(emails.getTo(), emails.getSubject(), emails.getEmailBody(), false, true);
                log.debug("Email Send Status of USER ROLE ASSIGN : " + emailStatus);
            }
            /*if(roleProcess == true){
                User user = userService.createCustomUserInformationByUserModule(umracIdentitySetup.getUserName(), umracIdentitySetup.getuPw(), umracIdentitySetup.getEmpId(), null, emailBuilder, "en", roleName, true, result.getRoleId().getId());
                if(user != null){
                    mailService.sendEmail(emails.getTo(), emails.getSubject(), emails.getEmailBody(), false, true);
                    log.debug("Email Send Status of USER ROLE ASSIGN : " + emailStatus);
                }
            }else{
                User user = userService.createCustomUserInformationByUserModule(umracIdentitySetup.getUserName(), umracIdentitySetup.getuPw(), umracIdentitySetup.getEmpId(), null, emailBuilder, "en", "ROLE_MODULE", true, result.getRoleId().getId());
                if(user != null){
                    mailService.sendEmail(emails.getTo(), emails.getSubject(), emails.getEmailBody(), false, true);
                    log.debug("Email Send Status of USER ROLE ASSIGN : " + emailStatus);
                }
            }*/
            // each module submodule role
            /*User user = userService.createCustomUserInformationByUserModule(umracIdentitySetup.getUserName(), umracIdentitySetup.getuPw(), umracIdentitySetup.getEmpId(), null, emailBuilder, "en", "ROLE_MODULE", true, result.getRoleId().getId());
            //User user = userService.createCustomUserInformationByUserModule(umracIdentitySetup.getUserName(), umracIdentitySetup.getuPw(), umracIdentitySetup.getEmpId(), null, emailBuilder, "en", "ROLE_MODULE", true, result.getRoleId().getId());
            if(user != null){
                //Email email = new Email();
                //emailStatus = email.Send(emails);

                for(UmracRightsSetup a :umracRightsSetupList){
                    String roleName="";
                    boolean roleProcess = false;
                    System.out.println("\n Sub module id : "+a.getSubModule_id().toString());
                    if(a.getSubModule_id().equals("165492151454") && a.getStatus()==true){
                        roleName= "ROLE_TIS_DASH";
                        roleProcess =true;
                    }else if(a.getSubModule_id().equals("165496151454") && a.getStatus()==true){
                        roleName= "ROLE_TIS_TRAINING_REQ";
                        roleProcess =true;
                    }else if(a.getSubModule_id().equals("165504151454") && a.getStatus()==true){
                        roleName= "ROLE_TIS_TRAINER_INFORMATION";
                        roleProcess =true;
                    }else if(a.getSubModule_id().equals("165500151454") && a.getStatus()==true){
                        roleName= "ROLE_TIS_TRAINER_EVALUATION";
                        roleProcess =true;
                    }else{
                        roleName= "ROLE_MODULE";
                    }

                    *//*if(a.getStatus()==true && roleProcess ==true){
                        JhiUserAuthoritys jhiUserAuthoritys = new JhiUserAuthoritys();
                        jhiUserAuthoritys.setUserId(user.getId());
                        jhiUserAuthoritys.setAuthorityName(roleName);
                        jhiUserAuthorityRepository.save(jhiUserAuthoritys);
                    }*//*
                }
                mailService.sendEmail(emails.getTo(), emails.getSubject(), emails.getEmailBody(), false, true);
                log.debug("Email Send Status of USER ROLE ASSIGN : " + emailStatus);
            }*/


        }catch (Exception e){
            log.debug("Exception in save role at JHI_USER entity");
        }

        umracRoleAssignUserSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umracRoleAssignUsers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umracRoleAssignUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umracRoleAssignUsers -> Updates an existing umracRoleAssignUser.
     */
    @RequestMapping(value = "/umracRoleAssignUsers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRoleAssignUser> updateUmracRoleAssignUser(@RequestBody UmracRoleAssignUser umracRoleAssignUser) throws URISyntaxException {
        log.debug("REST request to update UmracRoleAssignUser : {}", umracRoleAssignUser);
        if (umracRoleAssignUser.getId() == null) {
            return createUmracRoleAssignUser(umracRoleAssignUser);
        }
        UmracRoleAssignUser result = umracRoleAssignUserRepository.save(umracRoleAssignUser);
        umracRoleAssignUserSearchRepository.save(umracRoleAssignUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umracRoleAssignUser", umracRoleAssignUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umracRoleAssignUsers -> get all the umracRoleAssignUsers.
     */
    @RequestMapping(value = "/umracRoleAssignUsers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmracRoleAssignUser>> getAllUmracRoleAssignUsers(Pageable pageable)
        throws URISyntaxException {
        Page<UmracRoleAssignUser> page = umracRoleAssignUserRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umracRoleAssignUsers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umracRoleAssignUsers/:id -> get the "id" umracRoleAssignUser.
     */
    @RequestMapping(value = "/umracRoleAssignUsers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRoleAssignUser> getUmracRoleAssignUser(@PathVariable Long id) {
        log.debug("REST request to get UmracRoleAssignUser : {}", id);
        return Optional.ofNullable(umracRoleAssignUserRepository.findOne(id))
            .map(umracRoleAssignUser -> new ResponseEntity<>(
                umracRoleAssignUser,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /umracRoleAssignUsers/:id -> delete the "id" umracRoleAssignUser.
     */
    @RequestMapping(value = "/umracRoleAssignUsers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmracRoleAssignUser(@PathVariable Long id) {
        log.debug("REST request to delete UmracRoleAssignUser : {}", id);
        umracRoleAssignUserRepository.delete(id);
        umracRoleAssignUserSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umracRoleAssignUser", id.toString())).build();
    }

    /**
     * SEARCH  /_search/umracRoleAssignUsers/:query -> search for the umracRoleAssignUser corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/umracRoleAssignUsers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UmracRoleAssignUser> searchUmracRoleAssignUsers(@PathVariable String query) {
        return StreamSupport
            .stream(umracRoleAssignUserSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

//    @RequestMapping(value = "/umracRoleAssignUsers/userName/",
//        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<Map> getRoleByName(@RequestParam Long value) {
//
//        log.debug("REST request to get umracRoleAssignUsers by name : {}", value);
//
//        Optional<UmracRoleAssignUser> umracRoleSetup = umracRoleAssignUserRepository.findOneByUser(value);
//
//       /* log.debug("user on check for----"+value);
//        log.debug("cmsCurriculum on check code----"+Optional.empty().equals(cmsCurriculum));*/
//        //generateAllRightsJson();
//        Map map = new HashMap();
//
//        map.put("value", value);
//
//        if (Optional.empty().equals(umracRoleSetup)) {
//            map.put("isValid", true);
//            return new ResponseEntity<Map>(map, HttpStatus.OK);
//
//        } else {
//            map.put("isValid", false);
//            return new ResponseEntity<Map>(map, HttpStatus.OK);
//        }
//    }

    @RequestMapping(value = "/umracRoleAssignUsers/userId/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRoleAssignUser> getUsedUserById(@RequestParam Long value) {

        log.debug("REST request to get umracRoleAssignUsers by name : {}", value);

        return Optional.ofNullable(umracRoleAssignUserRepository.findOne(value))
            .map(umracRoleAssignUser -> new ResponseEntity<>(
                umracRoleAssignUser,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
