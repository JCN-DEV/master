package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.SisStudentInfoRepository;
import gov.step.app.repository.search.SisStudentInfoSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.MailService;
import gov.step.app.service.UserService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SisStudentInfo.
 */
@RestController
@RequestMapping("/api")
public class SisStudentInfoResource {

    private final Logger log = LoggerFactory.getLogger(SisStudentInfoResource.class);

    @Inject
    private InstituteRepository instituteRepository;
    @Inject
    private SisStudentInfoRepository sisStudentInfoRepository;

    @Inject
    private SisStudentInfoSearchRepository sisStudentInfoSearchRepository;

    @Inject
    private MailService mailService;

    @Inject
    private UserService userService;

    /**
     * POST  /sisStudentInfos -> Create a new sisStudentInfo.
     */
    @RequestMapping(value = "/sisStudentInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentInfo> createSisStudentInfo(@Valid @RequestBody SisStudentInfo sisStudentInfo) throws URISyntaxException {
        log.debug("REST request to save SisStudentInfo : {}", sisStudentInfo);
        if (sisStudentInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new sisStudentInfo cannot already have an ID").body(null);
        }

        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
       // String instituteId = "36649151454";
        sisStudentInfo.setInstitute(institute);
        SisStudentInfo result = sisStudentInfoRepository.save(sisStudentInfo);

        if(result != null){
            try {
                Boolean emailStatus = false;

                if(sisStudentInfo.getEmailAddress() != null){
                    String emailBuilder = sisStudentInfo.getEmailAddress();
                    User user = userService.createCustomUserInformationByUserModule(sisStudentInfo.getEmailAddress(), "123456", sisStudentInfo.getName(), null, emailBuilder, "en", "ROLE_GOVT_STUDENT", true, 1L);
                    sisStudentInfo.setUser(user);
                    sisStudentInfoRepository.save(sisStudentInfo);

                    if(user != null){
                        //Email email = new Email();
                        //emailStatus = email.Send(emails);
                        String emailSubject="Student Registration";
                        String emailBody="Dear "+sisStudentInfo.getName()+", <br><br> Please find your credentials. " + "<br><br> User Name : " + sisStudentInfo.getEmailAddress() + "<br><br> User Password : 123456 <br><br> Regards, <br>DTE Admin";
                        mailService.sendEmail(sisStudentInfo.getEmailAddress(), emailSubject, emailBody, false, true);
                        log.debug("Email Send Status of USER ROLE ASSIGN : " + emailStatus);
                    }
                }
            }catch (Exception e){
                log.debug("Exception in save role at JHI_USER entity");
            }
        }
        sisStudentInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/sisStudentInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sisStudentInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sisStudentInfos -> Updates an existing sisStudentInfo.
     */
    @RequestMapping(value = "/sisStudentInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentInfo> updateSisStudentInfo(@Valid @RequestBody SisStudentInfo sisStudentInfo) throws URISyntaxException {
        log.debug("REST request to update SisStudentInfo : {}", sisStudentInfo);
        if (sisStudentInfo.getId() == null) {
            return createSisStudentInfo(sisStudentInfo);
        }
        SisStudentInfo result = sisStudentInfoRepository.save(sisStudentInfo);
        sisStudentInfoSearchRepository.save(sisStudentInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sisStudentInfo", sisStudentInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sisStudentInfos -> get all the sisStudentInfos.
     */
    @RequestMapping(value = "/sisStudentInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SisStudentInfo>> getAllSisStudentInfos(Pageable pageable)
        throws URISyntaxException {
        Page<SisStudentInfo> page = sisStudentInfoRepository.findAll(pageable);
        if(SecurityUtils.isCurrentUserInRole("ROLE_GOVT_STUDENT")){
            page = sisStudentInfoRepository.findStudentsByEmailId(pageable, SecurityUtils.getCurrentUser().getUsername());
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sisStudentInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sisStudentInfos/:id -> get the "id" sisStudentInfo.
     */
    @RequestMapping(value = "/sisStudentInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentInfo> getSisStudentInfo(@PathVariable Long id) {
        log.debug("REST request to get SisStudentInfo : {}", id);
        return Optional.ofNullable(sisStudentInfoRepository.findOne(id))
            .map(sisStudentInfo -> new ResponseEntity<>(
                sisStudentInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "/sisStudentInfos/findStudentInfoByInstitute/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentInfo> findStudentInfoByInstituteWise(@PathVariable Long id) {

        Long instId = instituteRepository.findOneByUserIsCurrentUser().getId();

        return Optional.ofNullable(sisStudentInfoRepository.findStudentInfoByInstituteWise(id, instId))
            .map(sisStudentInfo -> new ResponseEntity<>(
                sisStudentInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }




    // Find Student information by sid No
    @RequestMapping(value = "/sisStudentInfos/findStudentInfoById/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentInfo> findStudentInfoById(@PathVariable Long id) {

        Long instId = instituteRepository.findOneByUserIsCurrentUser().getId();
        log.debug(">>>>>>>>>>>>>>> instId >>>>>>>>>>> :"+instId);
        return Optional.ofNullable(sisStudentInfoRepository.findStudentInfoById(id,instId))
            .map(sisStudentInfo -> new ResponseEntity<>(
                sisStudentInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sisStudentInfos/:id -> delete the "id" sisStudentInfo.
     */
    @RequestMapping(value = "/sisStudentInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSisStudentInfo(@PathVariable Long id) {
        log.debug("REST request to delete SisStudentInfo : {}", id);
        sisStudentInfoRepository.delete(id);
        sisStudentInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sisStudentInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sisStudentInfos/:query -> search for the sisStudentInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/sisStudentInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SisStudentInfo> searchSisStudentInfos(@PathVariable String query) {
        return StreamSupport
            .stream(sisStudentInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
