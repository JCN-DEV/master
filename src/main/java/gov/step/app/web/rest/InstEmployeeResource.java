package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmployeeMpoApplicationStatus;
import gov.step.app.domain.enumeration.InstEmployeeDocumentStatus;
import gov.step.app.repository.*;
import gov.step.app.repository.search.InstEmployeeSearchRepository;
import gov.step.app.repository.search.UserSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.MailService;
import gov.step.app.service.UserService;
import gov.step.app.service.util.InstEmployeeInfo;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstEmployee.
 */
@RestController
@RequestMapping("/api")
public class InstEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(InstEmployeeResource.class);

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private InstEmployeeSearchRepository instEmployeeSearchRepository;

    @Inject
    private UserService userService;

    @Inject
    private InstEmpAddressRepository instEmpAddressRepository;

    @Inject
    private InstEmpEduQualiRepository instEmpEduQualiRepository;

    @Inject
    private InstEmplTrainingRepository instEmplTrainingRepository;

    @Inject
    private InstEmplRecruitInfoRepository instEmplRecruitInfoRepository;

    @Inject
    private InstEmplBankInfoRepository instEmplBankInfoRepository;

    @Inject
    private InstEmplExperienceRepository instEmplExperienceRepository;

    @Inject
    private InstEmpSpouseInfoRepository instEmpSpouseInfoRepository;

    @Inject
    private MpoApplicationRepository mpoApplicationRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MailService mailService;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private UserSearchRepository userSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    NotificationStepRepository notificationStepRepository;


    /**
     * POST  /instEmployees -> Create a new instEmployee.
     */
    @RequestMapping(value = "/instEmployees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> createInstEmployee(@Valid @RequestBody InstEmployee instEmployee) throws URISyntaxException, Exception {
        log.debug("REST request to save InstEmployee : {}", instEmployee);
        if (instEmployee.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmployee cannot already have an ID").body(null);
        }

        /*String filepath="/backUp/teacher_info/";*/
        instEmployee.setCode(instEmployee.getCode().toLowerCase());
        User user=userService.createCustomUserInformation(instEmployee.getCode().toLowerCase(), "123456", instEmployee.getName(), null, instEmployee.getEmail(), "en", "ROLE_INSTEMP",true);
        instEmployee.setUser(user);

        String filepath="/backup/teacher_info/";
        log.debug("file name test--"+instEmployee.getImageName());
        //log.debug("file image test--"+instEmployee.getImage());
        if(instEmployee.getQuotaCertName() !=null && instEmployee.getQuotaCert() !=null){
            if(instEmployee.getQuotaCertName() !=null && instEmployee.getQuotaCertName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
//                instEmployee.setQuotaCertName(AttachmentUtil.replaceAttachment(filepath, instEmployee.getQuotaCertName(),instEmployee.getQuotaCertName().replace("/", "_"), instEmployee.getQuotaCert()));
                instEmployee.setQuotaCertName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmployee.getQuotaCertName().replace("/", "_"), instEmployee.getQuotaCert()));
            }else{
                //instEmployee.setImageName(AttachmentUtil.saveAttachment(filepath, instEmployee.getImageName().replace("/", "_"), instEmployee.getImage()));
                instEmployee.setQuotaCertName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmployee.getQuotaCertName().replace("/", "_"), instEmployee.getQuotaCert()));
            }

        }
        instEmployee.setQuotaCert(null);
        if(instEmployee.getIsJPAdmin()){
            //user = userService.getUserWithAuthoritiesByLogin(instEmployee.getCode()).get();

            Authority authority = authorityRepository.findOne(AuthoritiesConstants.JPADMIN);
            boolean addAuthority=true;
            if(authority==null){
                authority=new Authority();
                authority.setName(AuthoritiesConstants.JPADMIN);
                authorityRepository.save(authority);
            }

            user.getAuthorities().add(authority);

            user=userRepository.save(user);
            userSearchRepository.save(user);
        }
        /*instEmployee.setImageName(AttachmentUtil.saveAttachment(filepath, instEmployee.getImageName().replace("/", "_"), instEmployee.getImage()));
        instEmployee.setImage(null);
        instEmployee.setNidImageName(AttachmentUtil.saveAttachment(filepath, instEmployee.getNidImageName().replace("/", "_"), instEmployee.getNidImage()));
        instEmployee.setNidImage(null);
        instEmployee.setBirthCertNoName(AttachmentUtil.saveAttachment(filepath, instEmployee.getBirthCertNoName().replace("/", "_"), instEmployee.getBirthCertImage()));
        instEmployee.setBirthCertImage(null);*/

        InstEmployee result = instEmployeeRepository.save(instEmployee);
        String msilContent="Hello " +result.getName()+","+
            "\n" +
            "You are registered. \n" +
            "Signing in DIRECTORATE OF TECHNICAL EDUCATION Website. Your User Name:"+result.getCode()+" and password: 123456" +
            "\n" +
            "this is a system generated mail.Please contrct DIRECTORATE OF TECHNICAL EDUCATION for any query.";
        mailService.sendEmail(result.getEmail(),"Employee User login Credential",msilContent,false,false);

        instEmployeeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmployees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmployee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmployees -> Updates an existing instEmployee.
     */
    @RequestMapping(value = "/instEmployees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> updateInstEmployee(@Valid @RequestBody InstEmployee instEmployee) throws URISyntaxException, Exception {
        log.debug("REST request to update InstEmployee : {}", instEmployee);
        if (instEmployee.getId() == null) {
            return createInstEmployee(instEmployee);
        }
        InstEmployee previnstEmployee= instEmployeeRepository.findOne(instEmployee.getId());
        String filepath="/backup/teacher_info/";
        log.debug("file name test--"+instEmployee.getImageName());
        //log.debug("file image test--"+instEmployee.getImage());
        if(instEmployee.getImageName() !=null && instEmployee.getImage() !=null){
            if(previnstEmployee.getImageName() !=null && previnstEmployee.getImageName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
                instEmployee.setImageName(AttachmentUtil.replaceAttachment(filepath, previnstEmployee.getImageName(),instEmployee.getImageName().replace("/", "_"), instEmployee.getImage()));
            }else{
                //instEmployee.setImageName(AttachmentUtil.saveAttachment(filepath, instEmployee.getImageName().replace("/", "_"), instEmployee.getImage()));
                instEmployee.setImageName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmployee.getImageName().replace("/", "_"), instEmployee.getImage()));
            }

        }
        instEmployee.setImage(null);
        log.debug("nid file name test--"+instEmployee.getNidImageName());
        //log.debug("nid file image test--"+instEmployee.getNidImage());
        if(instEmployee.getNidImageName() !=null && instEmployee.getNidImage() !=null){

            if(previnstEmployee.getNidImageName() !=null && previnstEmployee.getNidImageName().length()>0){
                log.debug(" NidImageName replace trigger----------------------------------------------");
                instEmployee.setNidImageName(AttachmentUtil.replaceAttachment(filepath, previnstEmployee.getNidImageName(),instEmployee.getNidImageName().replace("/", "_"), instEmployee.getNidImage()));
            }else{
                //instEmployee.setNidImageName(AttachmentUtil.saveAttachment(filepath, instEmployee.getNidImageName().replace("/", "_"), instEmployee.getNidImage()));
                instEmployee.setNidImageName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmployee.getNidImageName().replace("/", "_"), instEmployee.getNidImage()));
            }

        }
        instEmployee.setNidImage(null);
        log.debug(" birth file name test--"+instEmployee.getBirthCertNoName());
        //log.debug("birth file image test--"+instEmployee.getBirthCertImage());
        if(instEmployee.getBirthCertNoName() !=null && instEmployee.getBirthCertImage() !=null){

            if(previnstEmployee.getBirthCertNoName() !=null && previnstEmployee.getBirthCertNoName().length()>0){
                log.debug(" BirthCertNoName replace trigger----------------------------------------------");
                instEmployee.setBirthCertNoName(AttachmentUtil.replaceAttachment(filepath, previnstEmployee.getBirthCertNoName(),instEmployee.getBirthCertNoName().replace("/", "_"), instEmployee.getBirthCertImage()));
            }else{
                //instEmployee.setBirthCertNoName(AttachmentUtil.saveAttachment(filepath, instEmployee.getBirthCertNoName().replace("/", "_"), instEmployee.getBirthCertImage()));
                instEmployee.setBirthCertNoName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmployee.getBirthCertNoName().replace("/", "_"), instEmployee.getBirthCertImage()));
            }

        }
        instEmployee.setBirthCertImage(null);
        InstEmployee result = instEmployeeRepository.save(instEmployee);
        instEmployeeSearchRepository.save(instEmployee);


        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification(instEmployee.getName()+" has updated his information.");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("employeeInfo.approve");
        notificationSteps.setUserId(instEmployee.getInstitute().getUser().getId());
        notificationStepRepository.save(notificationSteps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmployee", instEmployee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmployees -> get all the instEmployees.
     */
    @RequestMapping(value = "/instEmployees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmployee>> getAllInstEmployees(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmployee> page = instEmployeeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmployees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
   /* @RequestMapping(value = "/instEmployees/genInfoByCurrentUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmployee>> getAllInstEmployees(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmployee> page = instEmployeeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmployees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }*/

    /**
     * GET  /instEmployees/:id -> get the "id" instEmployee.
     */
    @RequestMapping(value = "/instEmployees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> getInstEmployee(@PathVariable Long id) {
        log.debug("REST request to get InstEmployee : {}", id);
        return Optional.ofNullable(instEmployeeRepository.findOne(id))
            .map(instEmployee -> new ResponseEntity<>(
                instEmployee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /instEmployees/:id -> get the "id" instEmployee.
     */
    @RequestMapping(value = "/instEmployees/institute/employee/{code:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> getInstEmployeeByInstituteAndCode(@PathVariable String code) {
        log.debug("REST request to get InstEmployee by institute and code: {}", code);
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        if(institute == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return Optional.ofNullable(instEmployeeRepository.findByInstituteAndEmployeecode(institute.getId(),code))
            .map(instEmployee -> new ResponseEntity<>(
                instEmployee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /instEmployees/:id -> get the "id" instEmployee.
     */
    @RequestMapping(value = "/instEmployees/institute/current/employee/{index}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> getInstEmployeeByInstituteAndIndex(@PathVariable String index) {
        log.debug("REST request to get InstEmployee by institute and index: {}", index);
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        if(institute == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return Optional.ofNullable(instEmployeeRepository.findByInstituteAndEmployeeIndex(institute.getId(), index))
            .map(instEmployee -> new ResponseEntity<>(
                instEmployee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * GET  /instEmployees/:index -> get the "index" instEmployee.
     */
    @RequestMapping(value = "/instEmployees/employee/{indexNo}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> getInstEmployeeByIndex(@PathVariable String indexNo) {
        log.debug("REST request to get InstEmployee by institute and index: {}", indexNo);

        return Optional.ofNullable(instEmployeeRepository.findOneByEmployeeIndexNo(indexNo))
            .map(instEmployee -> new ResponseEntity<>(
                instEmployee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * GET  /instEmployees/current -> Current Logged in Employee.
     */
    @RequestMapping(value = "/instEmployees/current",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> getCurrentInstEmployee() {
        log.debug("REST request to get InstEmployee : {}");
        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        /*return Optional.ofNullable(instEmployeeRepository.findCurrentOne())
            .map(instEmployee -> new ResponseEntity<>(
                instEmployee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        return Optional.ofNullable(instEmployeeresult)
            .map(instEmployee -> new ResponseEntity<>(
                instEmployee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /*
    * GET  /instEmployees/:id -> get the "id" instEmployee.
    */
    @RequestMapping(value = "/instEmployees/code/{code:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployeeInfo> getInstEmployeeByCode(@PathVariable String code) throws Exception{
        log.debug("REST request to get InstEmployee : {}", code);
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(code);
        InstEmployeeInfo instEmployeeInfores= new InstEmployeeInfo();
        String filepath="/backup/teacher_info/";

        if(instEmployeeresult.getImageName() != null) {
            if(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getImageName()) != null){
                instEmployeeresult.setImage(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getImageName()));
                instEmployeeresult.setImageName(instEmployeeresult.getImageName().substring(0, (instEmployeeresult.getImageName().length() - 17)));

            }
//            instEmployeeresult.setImage(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getImageName()));
//            instEmployeeresult.setImageName(instEmployeeresult.getImageName().substring(0, (instEmployeeresult.getImageName().length() - 17)));
        }
        if(instEmployeeresult.getNidImageName() != null && AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getNidImageName()) != null) {
            instEmployeeresult.setNidImage(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getNidImageName()));
            instEmployeeresult.setNidImageName(instEmployeeresult.getNidImageName().substring(0, (instEmployeeresult.getNidImageName().length() - 17)));
        }
        if(instEmployeeresult.getBirthCertNoName() !=null) {
            if(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getBirthCertNoName()) != null){
                instEmployeeresult.setBirthCertImage(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getBirthCertNoName()));

            }
           /* if(instEmployeeresult.getBirthCertNoName() != null){
                instEmployeeresult.setBirthCertNoName(instEmployeeresult.getBirthCertNoName().substring(0, (instEmployeeresult.getBirthCertNoName().length() - 17)));

            }*/
        }

        if(instEmployeeresult !=null){
            instEmployeeInfores.setInstEmployee(instEmployeeresult);
            instEmployeeInfores.setInstEmpAddress(instEmpAddressRepository.findOneByInstEmployeeId(instEmployeeresult.getId()));
            //instEmployeeInfores.setInstEmpEduQualis(instEmpEduQualiRepository.findListByInstEmployeeId(instEmployeeresult.getId()));
            log.debug("REST request to get InstEmployee : {}");
            List<InstEmpEduQuali> instEmpEduQualilist=null;
            instEmpEduQualilist=instEmpEduQualiRepository.findListByInstEmployeeId(instEmployeeresult.getId());
            for(InstEmpEduQuali instEmpEduQuali:instEmpEduQualilist){
                if(instEmpEduQuali.getCertificateCopyName()!=null && AttachmentUtil.retriveAttachment("/backup/teacher_info/",instEmpEduQuali.getCertificateCopyName()) != null){
                instEmpEduQuali.setCertificateCopy(AttachmentUtil.retriveAttachment("/backup/teacher_info/",instEmpEduQuali.getCertificateCopyName()));
                instEmpEduQuali.setCertificateCopyName(instEmpEduQuali.getCertificateCopyName().substring(0, (instEmpEduQuali.getCertificateCopyName().length() - 17)));
                }
            }

            // Commented out by yzaman 19th April 2016
            //instEmployeeInfores.setInstEmpEduQualis(rptJdbcDao.findListByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setInstEmpEduQualis(instEmpEduQualilist);

            List<InstEmplTraining> instEmpTraininglist=instEmplTrainingRepository.findInstEmplTrainingsByEmployeeId(instEmployeeresult.getId());

            for(InstEmplTraining instEmplTraining:instEmpTraininglist){
                if(instEmplTraining.getAttachmentName() !=null && AttachmentUtil.retriveAttachment("/backup/teacher_info/", instEmplTraining.getAttachmentName()) != null){
                instEmplTraining.setAttachment(AttachmentUtil.retriveAttachment("/backup/teacher_info/", instEmplTraining.getAttachmentName()));
                instEmplTraining.setAttachmentName(instEmplTraining.getAttachmentName().substring(0, (instEmplTraining.getAttachmentName().length() - 17)));
                }
            }

            instEmployeeInfores.setInstEmplTrainings(instEmpTraininglist);
            //instEmployeeInfores.setInstEmplTrainings(instEmplTrainingRepository.findInstEmplTrainingsByEmployeeId(instEmployeeresult.getId()));

            InstEmplRecruitInfo instEmplRecruitInfoResult=instEmplRecruitInfoRepository.findByInstEmployeeId(instEmployeeresult.getId());

            if(instEmplRecruitInfoResult !=null ) {
                if (instEmplRecruitInfoResult.getAppoinmentLtrName() != null && instEmplRecruitInfoResult.getAppoinmentLtrName().length() > 0 && AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getAppoinmentLtrName()) != null) {
                    instEmplRecruitInfoResult.setAppoinmentLtr(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getAppoinmentLtrName()));
                    instEmplRecruitInfoResult.setAppoinmentLtrName(instEmplRecruitInfoResult.getAppoinmentLtrName().substring(0, (instEmplRecruitInfoResult.getAppoinmentLtrName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getJoiningLetterName() != null && instEmplRecruitInfoResult.getJoiningLetterName().length() > 0 && AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getJoiningLetterName()) != null) {
                    instEmplRecruitInfoResult.setJoiningLetter(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getJoiningLetterName()));
                    instEmplRecruitInfoResult.setJoiningLetterName(instEmplRecruitInfoResult.getJoiningLetterName().substring(0, (instEmplRecruitInfoResult.getJoiningLetterName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getRecruitResultName() != null && instEmplRecruitInfoResult.getRecruitResultName().length() > 0 && AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitResultName()) != null) {
                    instEmplRecruitInfoResult.setRecruitResult(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitResultName()));
                    instEmplRecruitInfoResult.setRecruitResultName(instEmplRecruitInfoResult.getRecruitResultName().substring(0, (instEmplRecruitInfoResult.getRecruitResultName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getRecruitNewsLocalName() != null && instEmplRecruitInfoResult.getRecruitNewsLocalName().length() > 0 && AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitNewsLocalName()) != null) {
                    instEmplRecruitInfoResult.setRecruitNewsLocal(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitNewsLocalName()));
                    instEmplRecruitInfoResult.setRecruitNewsLocalName(instEmplRecruitInfoResult.getRecruitNewsLocalName().substring(0, (instEmplRecruitInfoResult.getRecruitNewsLocalName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getRecruitNewsDailyName() != null && instEmplRecruitInfoResult.getRecruitNewsDailyName().length() > 0 && AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitNewsDailyName()) != null) {
                    instEmplRecruitInfoResult.setRecruitNewsDaily(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitNewsDailyName()));
                    instEmplRecruitInfoResult.setRecruitNewsDailyName(instEmplRecruitInfoResult.getRecruitNewsDailyName().substring(0, (instEmplRecruitInfoResult.getRecruitNewsDailyName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getGoResulatonName() != null && instEmplRecruitInfoResult.getGoResulatonName().length() > 0 && AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getGoResulatonName()) != null) {
                    instEmplRecruitInfoResult.setGoResulaton(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getGoResulatonName()));
                    instEmplRecruitInfoResult.setGoResulatonName(instEmplRecruitInfoResult.getGoResulatonName().substring(0, (instEmplRecruitInfoResult.getGoResulatonName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getCommitteeName() != null && instEmplRecruitInfoResult.getCommitteeName().length() > 0 && AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getCommitteeName()) != null) {
                    instEmplRecruitInfoResult.setCommittee(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getCommitteeName()));
                    instEmplRecruitInfoResult.setCommitteeName(instEmplRecruitInfoResult.getCommitteeName().substring(0, (instEmplRecruitInfoResult.getCommitteeName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getRecommendationName() != null && instEmplRecruitInfoResult.getRecommendationName().length() > 0 && AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecommendationName()) != null) {
                    instEmplRecruitInfoResult.setRecommendation(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecommendationName()));
                    instEmplRecruitInfoResult.setRecommendationName(instEmplRecruitInfoResult.getRecommendationName().substring(0, (instEmplRecruitInfoResult.getRecommendationName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getSanctionName() != null && instEmplRecruitInfoResult.getSanctionName().length() > 0 && AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getSanctionName()) != null) {
                    instEmplRecruitInfoResult.setSanction(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getSanctionName()));
                    instEmplRecruitInfoResult.setSanctionName(instEmplRecruitInfoResult.getSanctionName().substring(0, (instEmplRecruitInfoResult.getSanctionName().length() - 17)));
                }
            }

            instEmployeeInfores.setInstEmplRecruitInfo(instEmplRecruitInfoResult);
            //instEmployeeInfores.setInstEmplRecruitInfo(instEmplRecruitInfoRepository.findByInstEmployeeId(instEmployeeresult.getId()));
            //instEmployeeInfores.setInstEmplBankInfo(instEmplBankInfoRepository.findByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setInstEmplBankInfo(instEmplBankInfoRepository.findByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setInstEmpSpouseInfo(instEmpSpouseInfoRepository.findByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setInstEmplExperiences(instEmplExperienceRepository.findByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setSalaryMap(rptJdbcDao.generateSalary());
        }
        return Optional.ofNullable(instEmployeeInfores)
            .map(instEmployeeInfo -> new ResponseEntity<>(
                    instEmployeeInfo,
                    HttpStatus.OK)
            )
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /*
    * GET  /instEmployees/:id -> get the "id" instEmployee.
    */
    @RequestMapping(value = "/instEmployees/indexNo/{indexNo}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployeeInfo> getInstEmployeeByIndexNo(@PathVariable String indexNo) throws Exception{
        log.debug("REST request to get InstEmployee : {}", indexNo);
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeIndexNo(indexNo);
        InstEmployeeInfo instEmployeeInfores= new InstEmployeeInfo();
        String filepath="/backup/teacher_info/";

        if(instEmployeeresult.getImageName() !=null) {
            instEmployeeresult.setImage(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getImageName()));
            instEmployeeresult.setImageName(instEmployeeresult.getImageName().substring(0, (instEmployeeresult.getImageName().length() - 17)));
        }
        if(instEmployeeresult.getNidImageName() !=null) {
            instEmployeeresult.setNidImage(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getNidImageName()));
            instEmployeeresult.setNidImageName(instEmployeeresult.getNidImageName().substring(0, (instEmployeeresult.getNidImageName().length() - 17)));
        }
        if(instEmployeeresult.getImageName() !=null) {
            instEmployeeresult.setBirthCertImage(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getBirthCertNoName()));
            instEmployeeresult.setBirthCertNoName(instEmployeeresult.getBirthCertNoName().substring(0, (instEmployeeresult.getBirthCertNoName().length() - 17)));
        }

        if(instEmployeeresult !=null){
            instEmployeeInfores.setInstEmployee(instEmployeeresult);
            instEmployeeInfores.setInstEmpAddress(instEmpAddressRepository.findOneByInstEmployeeId(instEmployeeresult.getId()));
            //instEmployeeInfores.setInstEmpEduQualis(instEmpEduQualiRepository.findListByInstEmployeeId(instEmployeeresult.getId()));
            log.debug("REST request to get InstEmployee : {}");
            List<InstEmpEduQuali> instEmpEduQualilist=null;
            instEmpEduQualilist=instEmpEduQualiRepository.findListByInstEmployeeId(instEmployeeresult.getId());
            for(InstEmpEduQuali instEmpEduQuali:instEmpEduQualilist){
                if(instEmpEduQuali.getCertificateCopyName()!=null){
                instEmpEduQuali.setCertificateCopy(AttachmentUtil.retriveAttachment("/backup/teacher_info/",instEmpEduQuali.getCertificateCopyName()));
                instEmpEduQuali.setCertificateCopyName(instEmpEduQuali.getCertificateCopyName().substring(0, (instEmpEduQuali.getCertificateCopyName().length() - 17)));
                }
            }

            // Commented out by yzaman 19th April 2016
            //instEmployeeInfores.setInstEmpEduQualis(rptJdbcDao.findListByInstEmployeeId(instEmployeeresult.getId()));
            //instEmployeeInfores.setInstEmpEduQualisList(instEmpEduQualilist);

            List<InstEmplTraining> instEmpTraininglist=instEmplTrainingRepository.findInstEmplTrainingsByEmployeeId(instEmployeeresult.getId());

            for(InstEmplTraining instEmplTraining:instEmpTraininglist){
                if(instEmplTraining.getAttachmentName()!=null){
                instEmplTraining.setAttachment(AttachmentUtil.retriveAttachment("/backup/teacher_info/", instEmplTraining.getAttachmentName()));
                instEmplTraining.setAttachmentName(instEmplTraining.getAttachmentName().substring(0, (instEmplTraining.getAttachmentName().length() - 17)));
                }
            }

            instEmployeeInfores.setInstEmplTrainings(instEmpTraininglist);
            //instEmployeeInfores.setInstEmplTrainings(instEmplTrainingRepository.findInstEmplTrainingsByEmployeeId(instEmployeeresult.getId()));

            InstEmplRecruitInfo instEmplRecruitInfoResult=instEmplRecruitInfoRepository.findByInstEmployeeId(instEmployeeresult.getId());

            if(instEmplRecruitInfoResult !=null) {
                if (instEmplRecruitInfoResult.getAppoinmentLtrName() != null && instEmplRecruitInfoResult.getAppoinmentLtrName().length() > 0) {
                    instEmplRecruitInfoResult.setAppoinmentLtr(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getAppoinmentLtrName()));
                    instEmplRecruitInfoResult.setAppoinmentLtrName(instEmplRecruitInfoResult.getAppoinmentLtrName().substring(0, (instEmplRecruitInfoResult.getAppoinmentLtrName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getJoiningLetterName() != null && instEmplRecruitInfoResult.getJoiningLetterName().length() > 0) {
                    instEmplRecruitInfoResult.setJoiningLetter(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getJoiningLetterName()));
                    instEmplRecruitInfoResult.setJoiningLetterName(instEmplRecruitInfoResult.getJoiningLetterName().substring(0, (instEmplRecruitInfoResult.getJoiningLetterName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getRecruitResultName() != null && instEmplRecruitInfoResult.getRecruitResultName().length() > 0) {
                    instEmplRecruitInfoResult.setRecruitResult(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitResultName()));
                    instEmplRecruitInfoResult.setRecruitResultName(instEmplRecruitInfoResult.getRecruitResultName().substring(0, (instEmplRecruitInfoResult.getRecruitResultName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getRecruitNewsLocalName() != null && instEmplRecruitInfoResult.getRecruitNewsLocalName().length() > 0) {
                    instEmplRecruitInfoResult.setRecruitNewsLocal(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitNewsLocalName()));
                    instEmplRecruitInfoResult.setRecruitNewsLocalName(instEmplRecruitInfoResult.getRecruitNewsLocalName().substring(0, (instEmplRecruitInfoResult.getRecruitNewsLocalName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getRecruitNewsDailyName() != null && instEmplRecruitInfoResult.getRecruitNewsDailyName().length() > 0) {
                    instEmplRecruitInfoResult.setRecruitNewsDaily(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitNewsDailyName()));
                    instEmplRecruitInfoResult.setRecruitNewsDailyName(instEmplRecruitInfoResult.getRecruitNewsDailyName().substring(0, (instEmplRecruitInfoResult.getRecruitNewsDailyName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getGoResulatonName() != null && instEmplRecruitInfoResult.getGoResulatonName().length() > 0) {
                    instEmplRecruitInfoResult.setGoResulaton(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getGoResulatonName()));
                    instEmplRecruitInfoResult.setGoResulatonName(instEmplRecruitInfoResult.getGoResulatonName().substring(0, (instEmplRecruitInfoResult.getGoResulatonName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getCommitteeName() != null && instEmplRecruitInfoResult.getCommitteeName().length() > 0) {
                    instEmplRecruitInfoResult.setCommittee(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getCommitteeName()));
                    instEmplRecruitInfoResult.setCommitteeName(instEmplRecruitInfoResult.getCommitteeName().substring(0, (instEmplRecruitInfoResult.getCommitteeName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getRecommendationName() != null && instEmplRecruitInfoResult.getRecommendationName().length() > 0) {
                    instEmplRecruitInfoResult.setRecommendation(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecommendationName()));
                    instEmplRecruitInfoResult.setRecommendationName(instEmplRecruitInfoResult.getRecommendationName().substring(0, (instEmplRecruitInfoResult.getRecommendationName().length() - 17)));
                }

                if (instEmplRecruitInfoResult.getSanctionName() != null && instEmplRecruitInfoResult.getSanctionName().length() > 0) {
                    instEmplRecruitInfoResult.setSanction(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getSanctionName()));
                    instEmplRecruitInfoResult.setSanctionName(instEmplRecruitInfoResult.getSanctionName().substring(0, (instEmplRecruitInfoResult.getSanctionName().length() - 17)));
                }
            }

            instEmployeeInfores.setInstEmplRecruitInfo(instEmplRecruitInfoResult);
            //instEmployeeInfores.setInstEmplRecruitInfo(instEmplRecruitInfoRepository.findByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setInstEmplBankInfo(instEmplBankInfoRepository.findByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setInstEmplExperiences(instEmplExperienceRepository.findByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setSalaryMap(rptJdbcDao.generateSalary());
        }
        return Optional.ofNullable(instEmployeeInfores)
            .map(instEmployeeInfo -> new ResponseEntity<>(
                    instEmployeeInfo,
                    HttpStatus.OK)
            )
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /*
   * GET  /instEmployees/:id -> get the "id" instEmployee.
   */




    @RequestMapping(value = "/instEmployeesAllForEmployee/code/{code:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployeeInfo> getInstEmployeByCodeForEmployeeApprove(@PathVariable String code) throws Exception{
        log.debug("REST request to get InstEmployee : {}", code);
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(code);

        String filepath="/backup/teacher_info/";

        instEmployeeresult.setImage(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getImageName() ));
        instEmployeeresult.setBirthCertImage(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getBirthCertNoName() ));
        instEmployeeresult.setNidImage(AttachmentUtil.retriveAttachment(filepath, instEmployeeresult.getNidImageName() ));

        instEmployeeresult.setImageName(instEmployeeresult.getImageName().substring(0, (instEmployeeresult.getImageName().length()-21)));
        instEmployeeresult.setNidImageName(instEmployeeresult.getNidImageName().substring(0, (instEmployeeresult.getNidImageName().length()-21)));
        instEmployeeresult.setBirthCertNoName(instEmployeeresult.getBirthCertNoName().substring(0, (instEmployeeresult.getBirthCertNoName().length() - 21)));


        InstEmployeeInfo instEmployeeInfores= new InstEmployeeInfo();

        if(instEmployeeresult !=null){
            instEmployeeInfores.setInstEmployee(instEmployeeresult);
            instEmployeeInfores.setInstEmpAddress(instEmpAddressRepository.findOneByInstEmployeeId(instEmployeeresult.getId()));
            //instEmployeeInfores.setInstEmpEduQualis(instEmpEduQualiRepository.findListByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setInstEmplTrainings(instEmplTrainingRepository.findInstEmplTrainingsByEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setInstEmplRecruitInfo(instEmplRecruitInfoRepository.findByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setInstEmplBankInfo(instEmplBankInfoRepository.findByInstEmployeeId(instEmployeeresult.getId()));
            instEmployeeInfores.setInstEmplExperiences(instEmplExperienceRepository.findByInstEmployeeId(instEmployeeresult.getId()));

            // Commented out by yzaman 19th April 2016
            //instEmployeeInfores.setInstEmpEduQualis(rptJdbcDao.findListByInstEmployeeId(instEmployeeresult.getId()));
            //instEmployeeInfores.setInstEmpSpouseInfo(instEmpSpouseInfoRepository.findByInstEmployeeId(instEmployeeresult.getId()));

            instEmployeeInfores.setSalaryMap(rptJdbcDao.generateSalary());
        }
        return Optional.ofNullable(instEmployeeInfores)
            .map(instEmployeeInfo -> new ResponseEntity<>(
                    instEmployeeInfo,
                    HttpStatus.OK)
            )
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /*
   * GET  /instEmployees/:id -> get the "id" instEmployee.
   */
    @RequestMapping(value = "/instEmployees/institute/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmployee>> getInstEmployeeByInstitute(Pageable pageable,@PathVariable Long id)  throws URISyntaxException {
        log.debug("REST request to get InstEmployee : {}", id);

        return Optional.ofNullable(instEmployeeRepository.findOneByInstitueId(id))
            .map(instEmployeelist -> new ResponseEntity<>(
                instEmployeelist,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

     /*
   * GET  /instEmployees/:id -> get the "id" instEmployee.
   */
    @RequestMapping(value = "/instEmployees/jpadmin/institute/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmployee> getJpAdminsByInstitute(Pageable pageable,@PathVariable Long id)  throws URISyntaxException {
        log.debug("REST request to get jpadmin of institute : {}", id);

        return instEmployeeRepository.findAllJpAdminOfInstitute(id);
    }


    /**
     * DELETE  /instEmployees/:id -> delete the "id" instEmployee.
     */
    @RequestMapping(value = "/instEmployees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmployee(@PathVariable Long id) {
        log.debug("REST request to delete InstEmployee : {}", id);
        instEmployeeRepository.delete(id);
        instEmployeeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmployee", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmployees/:query -> search for the instEmployee corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmployees/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmployee> searchInstEmployees(@PathVariable String query) {
        return StreamSupport
            .stream(instEmployeeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET /account/sessions -> get the current open sessions.
     */
    @RequestMapping(value = "/instEmployee/checkDuplicateCode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkLogin( @RequestParam String value) {
        Optional<User> user = userRepository.findOneByLogin(value);
        Optional<InstEmployee> instEmployee = instEmployeeRepository.findBycode(value);
        log.debug("user on check for----"+value);
        log.debug("user on check login----"+Optional.empty().equals(user));
        Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(user) || Optional.empty().equals(instEmployee)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/instEmployee/checkDuplicateEmail/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkEmail(@RequestParam String value) {

        Optional<User> user = userRepository.findOneByEmail(value);
        log.debug("user on check email----"+Optional.empty().equals(user));

        Optional<InstEmployee> instGenInfo = instEmployeeRepository.findByEmail(value);

        Map map =new HashMap();

        map.put("value", value);

        if(Optional.empty().equals(user) || Optional.empty().equals(instGenInfo)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }
    @RequestMapping(value = "/instEmployees/pendingList/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmployee> getPendingEmployeesByINstitute(@PathVariable Long id)
        throws URISyntaxException {
        log.debug("Inst titute employeee for institute>>>>>>>>>>>>>>>"+id);
        return instEmployeeRepository.findPendingListByInstitueId(id);
    }
    @RequestMapping(value = "/instEmployees/declinedList/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmployee> getDeclinedEmployeesByINstitute(@PathVariable Long id)
        throws URISyntaxException {
        log.debug("Inst titute employeee for institute>>>>>>>>>>>>>>>"+id);
        return instEmployeeRepository.findDeclinedListByInstitueId(id);
    }


    @RequestMapping(value = "/instEmployees/approveList/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmployee> getApprovedEmployeesByINstitute(@PathVariable Long id)
        throws URISyntaxException {
        log.debug("Inst titute employeee for institute>>>>>>>>>>>>>>>"+id);
        return instEmployeeRepository.findApprovedListByInstitueId(id);
    }

    //find all mpo enlisted employee of current institute
    @RequestMapping(value = "/instEmployees/current/mpoEnlisted",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmployee> getMpoEnlistedEmployeesOfCurrent()
        throws URISyntaxException {
        log.debug("Inst titute employeee for institute>>>>>>>>>>>>>>>");
        return instEmployeeRepository.findListByInstitueIdAndMpoStatus(instituteRepository.findOneByUserIsCurrentUser().getId(),EmployeeMpoApplicationStatus.APPROVED.getCode());
    }
    @RequestMapping(value = "/instEmployees/staffApproveList/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmployee> getStasffApprovedEmployeesByINstitute(@PathVariable Long id)
        throws URISyntaxException {
        log.debug("Inst titute employeee for institute>>>>>>>>>>>>>>>"+id);
        return instEmployeeRepository.findApprovedStaffListByInstitueId(id);
    }
    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/instEmployees/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> declineEmplyee(@PathVariable Long id,@RequestBody String cause)
        throws URISyntaxException {
        InstEmployee instEmployee=null;
        log.debug("REST request to update instEmployee : {}--------", id);
        if(id>0){
            instEmployee=instEmployeeRepository.findOne(id);
        }else{
            return ResponseEntity.badRequest().header("Failure", "A new inst Employee cannot decline ").body(null);
        }
        log.debug("REST request to update instEmployee : {}--------", instEmployee);
        log.debug("REST request to update cause : {}--------", cause);
        if (instEmployee == null) {
            return ResponseEntity.badRequest().header("Failure", "A new inst Employee cannot decline ").body(null);
        }else{
            instEmployee.setRemarks(cause);
            instEmployee.setStatus(InstEmployeeDocumentStatus.DECLINED.getCode());
            InstEmployee result= instEmployeeRepository.save(instEmployee);
            instEmployeeSearchRepository.save(result);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("result", result.getId().toString()))
                .body(null);
        }
    }
    /**
     * PUT  /instEmployees -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/instEmployees/approve/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> approveEmployee(@PathVariable Long id)
        throws URISyntaxException {
        InstEmployee instEmployee=null;
        log.debug("REST request to update instEmployee : {}--------", id);
        if(id>0){
            instEmployee=instEmployeeRepository.findOne(id);
        }else{
            return ResponseEntity.badRequest().header("Failure", "A new inst Employee cannot Approved ").body(null);
        }
        log.debug("REST request to update instEmployee : {}--------", instEmployee);
        if (instEmployee == null) {
            return ResponseEntity.badRequest().header("Failure", "A new inst Employee cannot Approved ").body(null);
        }else{

            if(instEmployee.getIsJPAdmin()){
                User user = userService.getUserWithAuthoritiesByLogin(instEmployee.getCode()).get();

                Authority authority = authorityRepository.findOne(AuthoritiesConstants.JPADMIN);
                boolean addAuthority=true;
                if(authority==null){
                    authority=new Authority();
                    authority.setName(AuthoritiesConstants.JPADMIN);
                    authorityRepository.save(authority);
                }

                user.getAuthorities().add(authority);

                user=userRepository.save(user);
                userSearchRepository.save(user);
            }


            instEmployee.setStatus(InstEmployeeDocumentStatus.APPROVED.getCode());
            InstEmployee result= instEmployeeRepository.save(instEmployee);
            instEmployeeSearchRepository.save(result);

            NotificationStep notificationSteps = new NotificationStep();
            notificationSteps.setNotification("You information has approved by institute");
            notificationSteps.setStatus(true);
            notificationSteps.setUrls("employeeInfo.personalInfo");
            notificationSteps.setUserId(instEmployee.getUser().getId());
            notificationStepRepository.save(notificationSteps);

            log.debug("ststud after save------------------------------------"+result.getStatus());
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("result", result.getId().toString()))
                .body(null);
        }
    }
    /**
     * PUT  instEmployees/eligibleForMpo -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/instEmployees/eligibleForMpo/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> eligibleEmployeeForMpo(@PathVariable Long id)
        throws URISyntaxException {
        InstEmployee instEmployee=null;
        log.debug("REST request to update instEmployee : {}--------", id);
        if(id>0){
            instEmployee=instEmployeeRepository.findOne(id);
        }else{
            return ResponseEntity.badRequest().header("Failure", "A new inst Employee failed to eligible ").body(null);
        }
        log.debug("REST request to update instEmployee : {}--------", instEmployee);
        if (instEmployee == null) {
            return ResponseEntity.badRequest().header("Failure", "A new inst Employee failed to eligible ").body(null);
        }else{
            instEmployee.setMpoAppStatus(EmployeeMpoApplicationStatus.ELIGIBLE.getCode());
            InstEmployee result= instEmployeeRepository.save(instEmployee);
            instEmployeeSearchRepository.save(result);

            NotificationStep notificationSteps = new NotificationStep();
            notificationSteps.setNotification("You are eligible for mpo application");
            notificationSteps.setStatus(true);
            notificationSteps.setUrls("mpo.application");
            notificationSteps.setUserId(instEmployee.getUser().getId());
            notificationStepRepository.save(notificationSteps);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("result", result.getId().toString()))
                .body(null);
        }
    }
    //get empl info by code
    @RequestMapping(value = "/instEmployees/getEmplInfoByCode/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmployee> getEmplInfoByCode(@PathVariable String code)
        throws URISyntaxException {
        return Optional.ofNullable(instEmployeeRepository.findEmplInfoBycode(code))
            .map(dlBookInfo -> new ResponseEntity<>(
                dlBookInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/instEmployees/findInstituteEmpByInstitute/{instituteID}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmployee>> findInstituteEmpByInstitute(@PathVariable Long instituteID)
        throws URISyntaxException {
        return Optional.ofNullable(instEmployeeRepository.findInstituteEmpByInstitute(instituteID))
            .map(dlBookInfo -> new ResponseEntity<>(
                dlBookInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
