package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.Authority;
import gov.step.app.domain.InstEmployee;
import gov.step.app.domain.InstGenInfo;
import gov.step.app.domain.User;
import gov.step.app.domain.enumeration.LocalityType;
import gov.step.app.repository.*;
import gov.step.app.repository.search.InstGenInfoSearchRepository;
import gov.step.app.repository.search.InstituteSearchRepository;
import gov.step.app.repository.search.UserSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.MailService;
import gov.step.app.service.UserService;
import gov.step.app.service.util.InstEmployeeInfo;
import gov.step.app.service.util.RandomUtil;
import gov.step.app.web.rest.dto.InstituteDto;
import gov.step.app.web.rest.dto.InstituteTempDto;
import gov.step.app.web.rest.jdbc.dao.InsttituteJdbcDao;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstGenInfo.
 */
@RestController
@RequestMapping("/api")
public class InstGenInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstGenInfoResource.class);

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private InstGenInfoSearchRepository instGenInfoSearchRepository;

    @Inject
    private UserService userService;
    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private InsAcademicInfoRepository insAcademicInfoRepository;

    @Inject
    private InstAdmInfoRepository instAdmInfoRepository;

    @Inject
    private InstInfraInfoRepository instInfraInfoRepository;

    @Inject
    private InstEmpCountRepository instEmpCountRepository;

    @Inject
    private InstFinancialInfoRepository instFinancialInfoRepository;

    @Inject
    private InstGovernBodyRepository instGovernBodyRepository;

    @Inject
    private IisCourseInfoRepository iisCourseInfoRepository;

    @Inject
    private IisCurriculumInfoRepository iisCurriculumInfoRepository;

    @Inject
    private InsAcademicInfoTempRepository insAcademicInfoTempRepository;

    @Inject
    private InstAdmInfoTempRepository instAdmInfoTempRepository;

    @Inject
    private InstInfraInfoTempRepository instInfraInfoTempRepository;

    @Inject
    private InstFinancialInfoTempRepository instFinancialInfoTempRepository;

    @Inject
    private InstGovernBodyTempRepository instGovernBodyTempRepository;

    @Inject
    private IisCourseInfoTempRepository iisCourseInfoTempRepository;

    @Inject
    private IisCurriculumInfoTempRepository iisCurriculumInfoTempRepository;

    @Inject
    private InstBuildingRepository instBuildingRepository;

    @Inject
    private InstBuildingTempRepository instBuildingTempRepository;

    @Inject
    private InstLandRepository instLandRepository;

    @Inject
    private InstLandTempRepository instLandTempRepository;

    @Inject
    private InstShopInfoRepository instShopInfoRepository;

    @Inject
    private InstShopInfoTempRepository instShopInfoTempRepository;

    @Inject
    private InstLabInfoRepository instLabInfoRepository;

    @Inject
    private InstLabInfoTempRepository instLabInfoTempRepository;

    @Inject
    private InstPlayGroundInfoRepository instPlayGroundInfoRepository;

    @Inject
    private InstPlayGroundInfoTempRepository instPlayGroundInfoTempRepository;

    @Inject
    private MailService mailService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    @Inject
    private InstituteSearchRepository instituteSearchRepository;


    /**
     * POST  /instGenInfos -> Create a new instGenInfo.
     */
    @RequestMapping(value = "/instGenInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGenInfo> createInstGenInfo(@Valid @RequestBody InstGenInfo instGenInfo) throws URISyntaxException {
        log.debug("REST request to save InstGenInfo : {}", instGenInfo);
        if (instGenInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instGenInfo cannot already have an ID").body(null);
        }
        InstGenInfo result = null;
        boolean hasError = false;
        String errorMassage = "";

        /*is the institute with this code exist*/
        result = instGenInfoRepository.findByCodeIgnoreCase(instGenInfo.getCode());
        if (result != null) {
            hasError = true;
            errorMassage = "Institute code " + result.getCode() + " already Used";
        }

        /*is the institute with this email exist*/
        result = instGenInfoRepository.findByEmailIgnoreCase(instGenInfo.getEmail());
        if (result != null) {
            hasError = true;
            if (errorMassage.equals("")) {
                errorMassage = "Email " + result.getEmail() + " already Used";
            } else {
                errorMassage = "\nEmail " + result.getEmail() + " already Used";
            }
        }
        if (hasError) {
            return ResponseEntity.badRequest().header("Failure", errorMassage).body(null);
        }
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSSSSS Z");
        LocalDateTime ldt = LocalDateTime.parse(LocalDateTime.now().format(formatter),formatter);
        *//*LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());*//*
        instGenInfo.setSubmitedDate(ldt);*/

        result = instGenInfoRepository.save(instGenInfo);
        /*if(result != null){
            InstituteStatus resultInstituteStatus = null;
            InstituteStatus instituteStatus =  new InstituteStatus();
            instituteStatus.setGenInfo(true);
            instituteStatus.setInstitute(result.getInstitute());
            instituteStatus.setAcademicInfo(false);
            instituteStatus.setAcaInfo(false);
            instituteStatus.setAdmInfo(false);
            instituteStatus.setBuilding(false);
            instituteStatus.setCmtMemInfo(false);
            instituteStatus.setComiteeInfo(false);
            instituteStatus.setComiteeMember(false);
            instituteStatus.setInfraInfo(false);
            instituteStatus.setFinancialInfo(false);
            instituteStatus.setShopInfo(false);
            instituteStatus.setGenInfo(false);
            instituteStatus.setGovBodyAccess(false);
            instituteStatus.setGovBodyAccess(false);
            instituteStatus.setLabInfo(false);
            instituteStatus.setLand(false);
            instituteStatus.setPlayGroundInfo(false);
            instituteStatus.setCreateBy(SecurityUtils.getCurrentUserId());
            instituteStatus.setCreateDate(LocalDate.now());
            resultInstituteStatus = instituteStatusRepository.save(instituteStatus);
        }*/
        instGenInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instGenInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instGenInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instGenInfos -> Updates an existing instGenInfo.
     */
    @RequestMapping(value = "/instGenInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGenInfo> updateInstGenInfo(@Valid @RequestBody InstGenInfo instGenInfo) throws URISyntaxException {
        log.debug("REST request to update InstGenInfo : {}", instGenInfo);
        if (instGenInfo.getId() == null) {
            return createInstGenInfo(instGenInfo);
        }
        InstGenInfo prevInstGenInfo = instGenInfoRepository.findOne(instGenInfo.getId());
        InstGenInfo result = instGenInfoRepository.save(instGenInfo);
        Institute institute = null;
        if(result != null){
            institute=instGenInfo.getInstitute();
            institute.setInfoEditStatus("Pending");
            instituteRepository.save(institute);

            InstituteStatus resultInstituteStatus = null;
            InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();

            if(instituteStatus == null){
               instituteStatus = new InstituteStatus();
            }

            instituteStatus.setGenInfo(1);
            instituteStatus.setInstitute(institute);
            instituteStatus.setUpdatedBy(SecurityUtils.getCurrentUserId());
            instituteStatus.setUpdatedDate(LocalDate.now());
            resultInstituteStatus = instituteStatusRepository.save(instituteStatus);
        }
        institute = null;
        User user=null;
        if ((prevInstGenInfo.getStatus()==null ||  prevInstGenInfo.getStatus() == 0 ) && instGenInfo.getStatus() == 1) {
/*            (String login, String password, String firstName, String lastName, String email,
                String langKey,String userRole);*/
            // userService.createCustomUserInformation(result.getCode().substring(0, 3) + result.getName().trim().substring(0, 3), "123456", "Institute", null, result.getEmail(), "en", "ROLE_INSTITUTE");
             //user=userService.createCustomUserInformation(result.getCode(), "123456", "Institute", null, result.getEmail(), "en", "ROLE_INSTITUTE",true);
             user=userService.createCustomUserInformation(result.getCode().toLowerCase(), "123456", result.getName(), null, result.getEmail(), "en", "ROLE_INSTITUTE",true);
            String msilContent="Hello Institute Admin,\n" +
                "\n" +
                "Your institute general information approved.Please Update Your further information by \n" +
                "signing in DIRECTORATE OF TECHNICAL EDUCATION Website. Your User Name:"+result.getCode()+" and password: 123456" +
                "\n" +
                "this is a system generated mail.Please contrct DIRECTORATE OF TECHNICAL EDUCATION for any query.";
            mailService.sendEmail(result.getEmail(),"Institute User login Credential",msilContent,false,false);
            log.debug(user.toString());
            institute=instGenInfo.getInstitute();
            institute.setUser(user);
            instituteRepository.save(institute);
        }
        instGenInfoSearchRepository.save(instGenInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instGenInfo", instGenInfo.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /institutes -> Updates an existing institute.
     */
    @RequestMapping(value = "/instGenInfos/approveAllInfo",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGenInfo> approveAllInfosOfInstitute(@Valid @RequestBody InstGenInfo instGenInfo) throws URISyntaxException {
        log.debug("REST request to update Institute : {}", instGenInfo);
        if (instGenInfo.getId() == null) {
            return createInstGenInfo(instGenInfo);
        }

        InstGenInfo prevInstGenInfo = instGenInfoRepository.findOne(instGenInfo.getId());

        InstGenInfo result = instGenInfoRepository.save(instGenInfo);
        Institute institute = instGenInfo.getInstitute();
        if(institute == null){
            institute = new Institute();
        }

        if(instGenInfo.getStatus() == 0){
            institute = instituteRepository.save(convertInstGenToInstitute(instGenInfo,institute));

            instGenInfo.setInstitute(institute);
            instGenInfo.setStatus(1);
            instGenInfoRepository.save(instGenInfo);
            InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();

            if(instituteStatus == null){
                instituteStatus = new InstituteStatus();
            }

            instituteStatus.setGenInfo(2);
            instituteStatus.setInstitute(institute);
            instituteStatus.setFinancialInfo(0);
            instituteStatus.setBuilding(0);
            instituteStatus.setAdmInfo(0);
            instituteStatus.setCourse(0);
            instituteStatus.setCurriculum(0);
            instituteStatus.setAcademicInfo(0);
            instituteStatus.setAcaInfo(0);
            instituteStatus.setCmtMemInfo(0);
            instituteStatus.setComiteeMember(0);
            instituteStatus.setGovBodyAccess(0);
            instituteStatus.setGovernBody(0);
            instituteStatus.setInfraInfo(0);
            instituteStatus.setLabInfo(0);
            instituteStatus.setLand(0);
            instituteStatus.setPlayGroundInfo(0);
            instituteStatus.setShopInfo(0);
            instituteStatus.setCreateBy(SecurityUtils.getCurrentUserId());
            instituteStatus.setCreateDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);

            User user=userService.createCustomUserInformation(result.getCode().toLowerCase(), "123456", result.getName(), null, result.getEmail(), "en", "ROLE_INSTITUTE",true);
            String msilContent="Hello Institute Admin,\n" +
                "\n" +
                "Your institute general information approved.Please Update Your further information by \n" +
                "signing in DIRECTORATE OF TECHNICAL EDUCATION Website. Your User Name:"+result.getCode()+" and password: 123456" +
                "\n" +
                "this is a system generated mail.Please contrct DIRECTORATE OF TECHNICAL EDUCATION for any query.";
            mailService.sendEmail(result.getEmail(),"Institute User login Credential",msilContent,false,false);
            log.debug(user.toString());
            institute=instGenInfo.getInstitute();
            institute.setUser(user);
            instituteRepository.save(institute);

        }else{
        if(instGenInfo.getInstitute() != null){
        InstituteStatus instituteStatus = instituteStatusRepository.findOneByInstitute(instGenInfo.getInstitute().getId());
        if(instituteStatus != null){
            if(instituteStatus.getGenInfo() == 1){
                instituteRepository.save(convertInstGenToInstitute(instGenInfo,institute));

                instituteStatus.setGenInfo(2);
            }

            if(instituteStatus.getAdmInfo() == 1){
                InstAdmInfoTemp instAdmInfoTemp = instAdmInfoTempRepository.findOneByInstituteId(institute.getId());
                //status 2 = approved
                instAdmInfoTemp.setStatus(2);
                instAdmInfoTempRepository.save(instAdmInfoTemp);
                InstAdmInfo instAdmInfo = instAdmInfoRepository.findOneByInstituteId(institute.getId());
                if(instAdmInfo == null){
                    instAdmInfo = new InstAdmInfo();
                }

                instAdmInfo.setAdminCounselorName(instAdmInfoTemp.getAdminCounselorName());
                instAdmInfo.setCounselorMobileNo(instAdmInfoTemp.getCounselorMobileNo());
                instAdmInfo.setDeoMobileNo(instAdmInfoTemp.getDeoMobileNo());
                instAdmInfo.setDeoName(instAdmInfoTemp.getDeoName());
                instAdmInfo.setInsHeadName(instAdmInfoTemp.getInsHeadName());
                instAdmInfo.setInstitute(instAdmInfoTemp.getInstitute());
                instAdmInfo.setInsHeadMobileNo(instAdmInfoTemp.getInsHeadMobileNo());
                instAdmInfoRepository.save(instAdmInfo);

                instituteStatus.setAdmInfo(2);

            }

            if(instituteStatus.getCourse() == 1){
                List<IisCourseInfoTemp> iisCourseInfoTemps = iisCourseInfoTempRepository.findAllPendingIisCourseByInstituteId(institute.getId());
                for(IisCourseInfoTemp each: iisCourseInfoTemps){
                    each.setStatus(2);
                    iisCourseInfoTempRepository.save(each);

                    IisCourseInfo iisCourseInfo = iisCourseInfoRepository.findByInstituteIdAndCmsCourseId(each.getInstitute().getId(),each.getCmsTrade().getId());
                    if(iisCourseInfo == null){
                        iisCourseInfo = new IisCourseInfo();
                    }
                    iisCourseInfo.setCreateBy(each.getCreateBy());
                    iisCourseInfo.setUpdateBy(each.getUpdateBy());
                    iisCourseInfo.setCreateDate(LocalDate.now());
                    iisCourseInfo.setCmsSubject(each.getCmsSubject());
                    iisCourseInfo.setMpoEnlisted(each.getMpoEnlisted());
                    iisCourseInfo.setUpdateDate(each.getUpdateDate());
                    iisCourseInfo.setCmsTrade(each.getCmsTrade());
                    //iisCourseInfo.setDateMpo(each.getDateMpo());
                    iisCourseInfo.setIisCurriculumInfo(each.getIisCurriculumInfo());
                    iisCourseInfo.setInstitute(each.getInstitute());
//                    iisCourseInfo.setMemoNo(each.getme);
                    iisCourseInfo.setSeatNo(each.getSeatNo());
                    iisCourseInfo.setShift(each.getShift());
                    iisCourseInfo.setPerDateBteb(each.getPerDateBteb());
                    iisCourseInfo.setPerDateEdu(each.getPerDateEdu());

                    iisCourseInfoRepository.save(iisCourseInfo);

                }

                instituteStatus.setCourse(2);

            }

            if(instituteStatus.getCurriculum() == 1){
                List<IisCurriculumInfoTemp> iisCurriculumInfoTemps = iisCurriculumInfoTempRepository.findAllPendingIisCurriculumByInstituteId(institute.getId());
                for(IisCurriculumInfoTemp each: iisCurriculumInfoTemps){
                    each.setStatus(2);
                    iisCurriculumInfoTempRepository.save(each);
                    IisCurriculumInfo iisCurriculumInfo = iisCurriculumInfoRepository.findOneByInstituteIdAndCurriculumId(each.getInstitute().getId(), each.getCurId());

                    System.out.println("Institute Id Of curriculum " +each.getInstitute().getId());
                    System.out.println("curriculum Id Of curriculum " +each.getCmsCurriculum().getId());

                    if(iisCurriculumInfo == null){
                        System.out.println("Entered into new curriculum " +iisCurriculumInfo);

                        iisCurriculumInfo = new IisCurriculumInfo();
                    }
                    iisCurriculumInfo.setCreateBy(each.getCreateBy());
                    iisCurriculumInfo.setStatus(1);
                    iisCurriculumInfo.setCreateDate(LocalDate.now());
                    iisCurriculumInfo.setCmsCurriculum(each.getCmsCurriculum());
                    iisCurriculumInfo.setInstitute(each.getInstitute());
                    iisCurriculumInfo.setLastDate(each.getLastDate());
                    iisCurriculumInfo.setFirstDate(each.getFirstDate());
                    iisCurriculumInfo.setLastMpoDate(each.getLastMpoDate());
                    iisCurriculumInfo.setLastRecNo(each.getLastRecNo());
                    iisCurriculumInfo.setMpoDate(each.getMpoDate());
                    iisCurriculumInfo.setCurId(each.getCurId());
                    iisCurriculumInfo.setMpoEnlisted(each.getMpoEnlisted());
                    iisCurriculumInfoRepository.save(iisCurriculumInfo);

                }

                instituteStatus.setCurriculum(2);

            }

            if(instituteStatus.getFinancialInfo() == 1){
                InstFinancialInfoTemp instFinancialInfoTemp = instFinancialInfoTempRepository.findOneByInstituteId(institute.getId());
                instFinancialInfoTemp.setStatus(2);
                instFinancialInfoTempRepository.save(instFinancialInfoTemp);
                InstFinancialInfo instFinancialInfo = instFinancialInfoRepository.findByInstituteId(institute.getId());
                if(instFinancialInfo == null){
                    instFinancialInfo = new InstFinancialInfo();
                }

                instFinancialInfo.setInstitute(instFinancialInfoTemp.getInstitute());
                instFinancialInfo.setAccountNo(instFinancialInfoTemp.getAccountNo());
                instFinancialInfo.setAccountType(instFinancialInfoTemp.getAccountType());
                instFinancialInfo.setAmount(instFinancialInfoTemp.getAmount());
                instFinancialInfo.setBankBranch(instFinancialInfoTemp.getBankBranch());
                instFinancialInfo.setBankSetup(instFinancialInfoTemp.getBankSetup());
                instFinancialInfo.setExpireDate(instFinancialInfoTemp.getExpireDate());
                instFinancialInfo.setIssueDate(instFinancialInfoTemp.getIssueDate());
                instFinancialInfo.setStatus(1);

                instFinancialInfoRepository.save(instFinancialInfo);

                instituteStatus.setFinancialInfo(2);

            }

            if(instituteStatus.getInfraInfo() == 1){
                InstInfraInfoTemp instInfraInfoTemp = instInfraInfoTempRepository.findByInstituteId(institute.getId());
                instInfraInfoTemp.setStatus(2);
                instInfraInfoTempRepository.save(instInfraInfoTemp);
                /*InstBuildingTemp instBuildingTemp = instBuildingTempRepository.;
                InstBuilding instBuilding = instBuildingRepository.;*/
                InstLand instLand = null;
                InstLandTemp instLandTemp = instInfraInfoTemp.getInstLandTemp();
                InstBuilding instBuilding = null;
                InstBuildingTemp instBuildingTemp = instInfraInfoTemp.getInstBuildingTemp();
                //InstLabInfo instLabInfo = null;
                    InstInfraInfo instInfraInfo = instInfraInfoRepository.findByInstituteId(institute.getId());
                if(instInfraInfo == null){
                    instInfraInfo = new InstInfraInfo();
                    instBuilding = new InstBuilding();
                    instLand = new InstLand();
                    //instLabInfo = new InstLabInfo();

                    instInfraInfo.setInstitute(instInfraInfoTemp.getInstitute());
                }else{
                    instBuilding = instInfraInfo.getInstBuilding();
                    instLand = instInfraInfo.getInstLand();
                }

               //updating instBuilding if exists and adding if not exists
                if(instBuildingTemp != null){
                    instBuildingTemp.setdStatus(true);
                    instBuildingTempRepository.save(instBuildingTemp);

                    instBuilding.setAuditoriumRoom(instBuildingTemp.getAuditoriumRoom());
                    instBuilding.setClassRoom(instBuildingTemp.getClassRoom());
                    instBuilding.setCommonRoom(instBuildingTemp.getCommonRoom());
                    instBuilding.setCounselingRoom(instBuildingTemp.getCounselingRoom());
                    instBuilding.setdFwash(instBuildingTemp.getdFwash());
                    instBuilding.setDirection(instBuildingTemp.getDirection());
                    instBuilding.setdMwash(instBuildingTemp.getdMwash());
                    instBuilding.setdName(instBuildingTemp.getdName());
                    instBuilding.setFemaleBathroom(instBuildingTemp.getFemaleBathroom());
                    instBuilding.setLabNo(instBuildingTemp.getLabNo());
                    instBuilding.setLabRoom(instBuildingTemp.getLabRoom());
                    instBuilding.setMaleBathroom(instBuildingTemp.getMaleBathroom());
                    instBuilding.setName(instBuildingTemp.getName());
                    instBuilding.setMeetingRoom(instBuildingTemp.getMeetingRoom());
                    instBuilding.setOfficeRoom(instBuildingTemp.getOfficeRoom());
                    instBuilding.setOtherRoom(instBuildingTemp.getOtherRoom());
                    instBuilding.setResearchRoom(instBuildingTemp.getResearchRoom());
                    instBuilding.setStoreRoom(instBuildingTemp.getStoreRoom());
                    instBuilding.setTeachersRoom(instBuildingTemp.getTeachersRoom());
                    instBuilding.setTotalArea(instBuildingTemp.getTotalArea());
                    instBuilding.setTotalLibraryRoom(instBuildingTemp.getTotalLibraryRoom());
                    instBuilding.setTotalRoom(instBuildingTemp.getTotalRoom());
                    instBuilding.setTotalShop(instBuildingTemp.getTotalShop());
                    instBuilding.setTrainingRoom(instBuildingTemp.getTrainingRoom());
                    instBuilding.setResearchRoom(instBuildingTemp.getResearchRoom());
                    instBuilding.setWorkshopRoom(instBuildingTemp.getWorkshopRoom());

                    instBuildingRepository.save(instBuilding);
                    instituteStatus.setBuilding(2);
                }




                //instLand adding if not exists and updating if exists
                if(instBuildingTemp != null){
                    instLand.setAmountOfLand(instLandTemp.getAmountOfLand());
                    instLand.setBoundaryEast(instLandTemp.getBoundaryEast());
                    instLand.setBoundaryNorth(instLandTemp.getBoundaryNorth());
                    instLand.setBoundarySouth(instLandTemp.getBoundarySouth());
                    instLand.setBoundaryWest(instLandTemp.getBoundaryWest());
                    instLand.setDagNo(instLandTemp.getDagNo());
                    instLand.setDeedNo(instLandTemp.getDeedNo());
                    instLand.setDistncFromDistHQ(instLandTemp.getDistncFromDistHQ());
                    instLand.setDistncFromMainRd(instLandTemp.getDistncFromMainRd());
                    instLand.setHeight(instLandTemp.getHeight());
                    instLand.setGiverName(instLandTemp.getGiverName());
                    instLand.setJlNo(instLandTemp.getJlNo());
                    instLand.setLandRegistrationDate(instLandTemp.getLandRegistrationDate());
                    instLand.setLastTaxPaymentDate(instLandTemp.getLastTaxPaymentDate());
                    instLand.setLedgerNo(instLandTemp.getLedgerNo());
                    instLand.setMouja(instLandTemp.getMouja());
                    instLand.setMutationDate(instLandTemp.getMutationDate());
                    instLand.setLandRegistrationLedgerNo(instLandTemp.getLandRegistrationLedgerNo());
                    instLand.setPorcha(instLandTemp.getPorcha());
                    instLand.setReceiverDate(instLandTemp.getReceiverDate());
                    instLand.setSurvey(instLandTemp.getSurvey());
                    instLand.setWidth(instLandTemp.getWidth());
                    instLandRepository.save(instLand);
                    instituteStatus.setLand(2);
                }


                instInfraInfo.setInstBuilding(instBuilding);
                instInfraInfo.setInstLand(instLand);
                instInfraInfoRepository.save(instInfraInfo);

                instituteStatus.setInfraInfo(2);

            }

            /*if(instituteStatus.getFinancialInfo() == 1){
                InstFinancialInfoTemp instFinancialInfoTemp = instFinancialInfoTempRepository.findOneByInstituteId(institute.getId());
                InstFinancialInfo instFinancialInfo = instFinancialInfoRepository.findByInstituteId(institute.getId());
                if(instFinancialInfo == null){
                    instFinancialInfo = new InstFinancialInfo();
                    instFinancialInfo.setInstitute(instFinancialInfoTemp.getInstitute());
                    //InstBuilding instBuilding
                }
                instFinancialInfo.setIssueDate(instFinancialInfoTemp.getIssueDate());
                instFinancialInfo.setExpireDate(instFinancialInfo.getExpireDate());
                instFinancialInfo.setBankSetup(instFinancialInfoTemp.getBankSetup());
                instFinancialInfo.setAccountNo(instFinancialInfoTemp.getAccountNo());
                instFinancialInfo.setAccountType(instFinancialInfoTemp.getAccountType());
                instFinancialInfo.setBankBranch(instFinancialInfoTemp.getBankBranch());

                instFinancialInfoRepository.save(instFinancialInfo);

                instituteStatus.setFinancialInfo(2);
            }*/
        }

            instituteStatusRepository.save(instituteStatus);

            //updating institute's main change status 'approved'
            institute.setInfoEditStatus("Approved");
            Institute instResult = instituteRepository.save(institute);
            instituteSearchRepository.save(instResult);

        }
    }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instGenInfo", instGenInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instGenInfos -> get all the instGenInfos.
     */
    @RequestMapping(value = "/instGenInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstGenInfo>> getAllInstGenInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstGenInfo> page = instGenInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instGenInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instGenInfosByCurrentUser -> get all the instGenInfosByCurrentUser.
     */
    @RequestMapping(value = "/instGenInfosByCurrentUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getinstGenInfosByCurrentUser() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();

        Long inst = instituteRepository.findOneByUserIsCurrentUserID();
        System.out.println("=====Shuvo Da===========");
        System.out.println(inst);
        log.debug("Institute id-------------------------" + inst);
        List<Map<String,Object>> ByInspIdlistRpt = rptJdbcDao.findAllGeninfoBycurrentUser(inst);
        return ByInspIdlistRpt;

        /*Optional<InstGenInfo> InstitutebyUser = instGenInfoRepository.findAllGeninfoBycurrentUser(inst);
        System.out.println("=====Shuvo Da===========");
        System.out.println(InstitutebyUser);
        System.out.println("=====Shuvo Da===========");*/
    }

    /**
     * GET  /instGenInfos/:id -> get the "id" instGenInfo.
     */
    @RequestMapping(value = "/instGenInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGenInfo> getInstGenInfo(@PathVariable Long id) {
        log.debug("REST request to get InstGenInfo : {}", id);
        String userName = SecurityUtils.getCurrentUserLogin();
        /*Long userId = SecurityUtils.getCurrentUserId();

        Long inst = instituteRepository.findOneByUserIsCurrentUserID();

        log.debug("Institute id-------------------------" + inst);
        log.debug("userName id-------------------------" + userId);
        log.debug("code id-------------------------" + userName);*/
        Page<InstGenInfo> page = null;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)) {
            return Optional.ofNullable(instGenInfoRepository.findByCodeIgnoreCase(userName))
                .map(instGenInfo -> new ResponseEntity<>(
                    instGenInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return Optional.ofNullable(instGenInfoRepository.findOne(id))
                .map(instGenInfo -> new ResponseEntity<>(
                    instGenInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

    }

    @RequestMapping(value = "/instGenInfosByStatus/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstGenInfo>> getInstGenInfo(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get Approved InstGenInfo : {}", status + "----" + status);
        /*
            * status 1 for approve and 0 for pending list
            * */
        Page<InstGenInfo> page =null;;
        if (status) {
            //this getting only for status 1 but we need all whose status > 1
            //page = instGenInfoRepository.findInstgenInfoByStatus(pageable, 1, 1);

            //this to get all institute whose status > 1. that means all approved institutes
            page = instGenInfoRepository.findAllApprovedInstitutes(pageable, 1);

            //
        } else {
            page = instGenInfoRepository.findInstgenInfopendingListForApproval(pageable);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instGenInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/instGenInfosPending",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstGenInfo>> getInstGenInfoPending(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get Approved InstGenInfo : {}");

        Page<InstGenInfo> page = instGenInfoRepository.findInstgenInfopendingListForApproval(pageable);;

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instGenInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/instGenInfos/pendingInfo",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstGenInfo>> getInstGenInfoApprovalPending(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get Approved InstGenInfo : {}");
        /*
            * status 1 for approve and 0 for pending list
            * */
        //Line added to get "Pending" status institute
        //Page<InstGenInfo> page = instGenInfoRepository.findInstgenInfopendingInfoUpdateList(pageable,1);
        Page<InstGenInfo> page = instGenInfoRepository.findInstgenInfopendingInfoUpdateListByStatus(pageable, "Pending");


        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instGenInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/instGenInfos/rejectedList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstGenInfo>> getInstGenInfoRejectedList(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get Approved InstGenInfo : {}");
        /*
            * status 1 for approve and 0 for pending list
            * */
        Page<InstGenInfo> page = instGenInfoRepository.findInstgenInfoRejectedList(pageable);;

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instGenInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * DELETE  /instGenInfos/:id -> delete the "id" instGenInfo.
     */
    @RequestMapping(value = "/instGenInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstGenInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstGenInfo : {}", id);
        instGenInfoRepository.delete(id);
        instGenInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instGenInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instGenInfos/:query -> search for the instGenInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instGenInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstGenInfo> searchInstGenInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instGenInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    @RequestMapping(value = "/instGenInfo/instituteAllInfo/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteDto> getInstEmployeeByCode(@PathVariable Long id) {
        log.debug("REST request to get Inst All info for : {}", id);
        String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();
        //InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        InstGenInfo instGenInfo=instGenInfoRepository.findByInstituteId(id);

        InstituteDto instituteDtoInfo= null;

        if(instGenInfo !=null && instGenInfo.getInstitute() !=null){
            instituteDtoInfo=new InstituteDto();
            instituteDtoInfo.setInstGenInfo(instGenInfo);
            instituteDtoInfo.setInstAdmInfo(instAdmInfoRepository.findOneByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInsAcademicInfo(insAcademicInfoRepository.findByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstInfraInfo(instInfraInfoRepository.findByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstEmpCount(instEmpCountRepository.findOneByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstFinancialInfo(instFinancialInfoRepository.findListByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstGovernBody(instGovernBodyRepository.findListByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstCurriculums(iisCurriculumInfoRepository.findListByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstCourses(iisCourseInfoRepository.findListByInstituteId(instGenInfo.getInstitute().getId()));
        }
        return Optional.ofNullable(instituteDtoInfo)
            .map(instituteAllInfo -> new ResponseEntity<>(
                    instituteAllInfo,
                    HttpStatus.OK)
            )
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/instGenInfo/instituteAllInfo/temp/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteTempDto> getAllInstituteGeninfoTemp(@PathVariable Long id) {
        log.debug("REST request to get Inst All info for : {}", id);
        String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();
        //InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        InstGenInfo instGenInfo=instGenInfoRepository.findByInstituteId(id);

        InstituteTempDto instituteDtoInfo= null;

        if(instGenInfo !=null && instGenInfo.getInstitute() !=null){
            instituteDtoInfo=new InstituteTempDto();
            instituteDtoInfo.setInstGenInfo(instGenInfo);
            instituteDtoInfo.setInstAdmInfo(instAdmInfoTempRepository.findOneByInstituteId(instGenInfo.getInstitute().getId()));
            //instituteDtoInfo.setInsAcademicInfo(insAcademicInfoTempRepository.findByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstInfraInfo(instInfraInfoTempRepository.findByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstEmpCount(instEmpCountRepository.findOneByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstFinancialInfo(instFinancialInfoTempRepository.findListByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstGovernBody(instGovernBodyTempRepository.findListByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstCurriculums(iisCurriculumInfoTempRepository.findListByInstituteId(instGenInfo.getInstitute().getId()));
            instituteDtoInfo.setInstCourses(iisCourseInfoTempRepository.findListByInstituteId(instGenInfo.getInstitute().getId()));
        }
        return Optional.ofNullable(instituteDtoInfo)
            .map(instituteAllInfo -> new ResponseEntity<>(
                    instituteAllInfo,
                    HttpStatus.OK)
            )
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * GET /account/sessions -> get the current open sessions.
     */
    @RequestMapping(value = "/instGenInfo/eiin/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkEiin( @RequestParam String value) {

        Optional<InstGenInfo> instGenInfo = instGenInfoRepository.findByEiin(value);

        log.debug("user on check for----"+value);
        log.debug("user on check login----"+Optional.empty().equals(instGenInfo));

        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(instGenInfo)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }

    /**
     * GET /account/sessions -> get the current open sessions.
     */
    @RequestMapping(value = "/instGenInfo/checkDuplicateCode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkLogin( @RequestParam String value) {

        Optional<User> user = userRepository.findOneByLogin(value);
        Optional<InstGenInfo> instGenInfo = instGenInfoRepository.findBycode(value);

        log.debug("user on check for----"+value);
        log.debug("user on check login----"+Optional.empty().equals(user));

        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(user) || Optional.empty().equals(instGenInfo)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/instGenInfo/checkDuplicateEmail/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkEmail(@RequestParam String value) {

        Optional<User> user = userRepository.findOneByEmail(value);
        Optional<InstGenInfo> instGenInfo = instGenInfoRepository.findOneByEmail(value);
        log.debug("user on check email----"+Optional.empty().equals(user));

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


    @RequestMapping(value = "/instGenInfo/checkDuplicateMpoCode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkMpoCode(@RequestParam String value) {

        InstGenInfo instGenInfo = instGenInfoRepository.findOneBympoCode(value);
        log.debug("user on check mpo code----"+instGenInfo);

        Map map =new HashMap();

        map.put("value", value);

        if(instGenInfo == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }

    /**
     * GET  /instGenInfosLocality -> get instGenInfo locality.
     */
    @RequestMapping(value = "/instGenInfosLocalitys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LocalityType> instGenInfosLocalitys() {
        log.debug("REST request to get InstGenInfo locality: {}");
        return LocalityType.getLocalityTypeList();
    }

    public Institute convertInstGenToInstitute(InstGenInfo instGenInfo, Institute institute){
        institute.setCode(instGenInfo.getCode());
        institute.setInstCategory(instGenInfo.getInstCategory());
        institute.setEiin(instGenInfo.getEiin());
        institute.setCenterCode(instGenInfo.getCenterCode());
        institute.setName(instGenInfo.getName());
        institute.setFirstAffiliationDate(instGenInfo.getFirstAffiliationDate());
        institute.setInstLevel(instGenInfo.getInstLevel());
        institute.setMpoEnlisted(instGenInfo.getMpoEnlisted());
        institute.setDateOfMpo(instGenInfo.getDateOfMpo());
        institute.setDateOfEstablishment(instGenInfo.getPublicationDate());
        institute.setLocality(instGenInfo.getLocality());
        institute.setOwnerType(instGenInfo.getOwnerType());
        institute.setMobile(instGenInfo.getMobileNo());
        institute.setAltMobile(instGenInfo.getAltMobileNo());
        institute.setType(instGenInfo.getType());
//            institute.setvillage = instGenInfo.village;
        institute.setPostOffice(instGenInfo.getPostOffice());
//            institute.postCode = instGenInfo.postCode;
        institute.setLandPhone(instGenInfo.getLandPhone());
//            institute.setMobileNmobileNo = instGenInfo.mobileNo;
        institute.setEmail(instGenInfo.getEmail());
//            institute.setwwebsite = instGenInfo.website;
//            institute.setFfaxNum = instGenInfo.faxNum;
        institute.setConstituencyArea(instGenInfo.getConsArea());
//            institute.setD = instGenInfo.division;
        institute.setInstCategory(instGenInfo.getInstCategory());
        institute.setUpazila(instGenInfo.getUpazila());

        return institute;
    }

}
