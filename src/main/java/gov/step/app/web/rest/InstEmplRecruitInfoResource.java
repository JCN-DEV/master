package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmplRecruitInfo;
import gov.step.app.domain.InstEmployee;
import gov.step.app.repository.InstEmplRecruitInfoRepository;
import gov.step.app.repository.InstEmployeeRepository;
import gov.step.app.repository.search.InstEmplRecruitInfoSearchRepository;
import gov.step.app.security.SecurityUtils;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstEmplRecruitInfo.
 */
@RestController
@RequestMapping("/api")
public class InstEmplRecruitInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstEmplRecruitInfoResource.class);

    @Inject
    private InstEmplRecruitInfoRepository instEmplRecruitInfoRepository;

    @Inject
    private InstEmplRecruitInfoSearchRepository instEmplRecruitInfoSearchRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    /**
     * POST  /instEmplRecruitInfos -> Create a new instEmplRecruitInfo.
     */
    @RequestMapping(value = "/instEmplRecruitInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplRecruitInfo> createInstEmplRecruitInfo(@Valid @RequestBody InstEmplRecruitInfo instEmplRecruitInfo) throws URISyntaxException,Exception {
        log.debug("REST request to save InstEmplRecruitInfo : {}", instEmplRecruitInfo);
        if (instEmplRecruitInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmplRecruitInfo cannot already have an ID").body(null);
        }
        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        instEmplRecruitInfo.setInstEmployee(instEmployeeresult);

        String filepath="/backup/teacher_info/";

        /*-------------------saving AppoinmentLtr to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getAppoinmentLtr());
        log.debug("file image test--"+instEmplRecruitInfo.getAppoinmentLtrName());
        if(instEmplRecruitInfo.getAppoinmentLtr() !=null && instEmplRecruitInfo.getAppoinmentLtrName() !=null){
            instEmplRecruitInfo.setAppoinmentLtrName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getAppoinmentLtrName().replace("/", "_"), instEmplRecruitInfo.getAppoinmentLtr()));
        }
        instEmplRecruitInfo.setAppoinmentLtr(null);

        /*-------------------saving JoiningLetter to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getJoiningLetter());
        log.debug("file image test--"+instEmplRecruitInfo.getJoiningLetterName());
        if(instEmplRecruitInfo.getJoiningLetter() !=null && instEmplRecruitInfo.getJoiningLetterName() !=null){
            instEmplRecruitInfo.setJoiningLetterName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getJoiningLetterName().replace("/", "_"), instEmplRecruitInfo.getJoiningLetter()));
        }
        instEmplRecruitInfo.setJoiningLetter(null);


        /*-------------------saving RecruitResult to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getRecruitResult());
        log.debug("file image test--"+instEmplRecruitInfo.getRecruitResultName());
        if(instEmplRecruitInfo.getRecruitResult() !=null && instEmplRecruitInfo.getRecruitResultName() !=null){
            instEmplRecruitInfo.setRecruitResultName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getRecruitResultName().replace("/", "_"), instEmplRecruitInfo.getRecruitResult()));
        }
        instEmplRecruitInfo.setRecruitResult(null);


        /*-------------------saving RecruitNewsLocal to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getRecruitNewsLocal());
        log.debug("file image test--"+instEmplRecruitInfo.getRecruitNewsLocalName());
        if(instEmplRecruitInfo.getRecruitNewsLocal() !=null && instEmplRecruitInfo.getRecruitNewsLocalName() !=null){
            instEmplRecruitInfo.setRecruitNewsLocalName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getRecruitNewsLocalName().replace("/", "_"), instEmplRecruitInfo.getRecruitNewsLocal()));
        }
        instEmplRecruitInfo.setRecruitNewsLocal(null);

        /*-------------------saving RecruitNewsDaily to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getRecruitNewsDaily());
        log.debug("file image test--"+instEmplRecruitInfo.getRecruitNewsDailyName());
        if(instEmplRecruitInfo.getRecruitNewsDaily() !=null && instEmplRecruitInfo.getRecruitNewsDailyName() !=null){
            instEmplRecruitInfo.setRecruitNewsDailyName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getRecruitNewsDailyName().replace("/", "_"), instEmplRecruitInfo.getRecruitNewsDaily()));
        }
        instEmplRecruitInfo.setRecruitNewsDaily(null);


        /*-------------------saving GoResulaton to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getGoResulaton());
        log.debug("file image test--"+instEmplRecruitInfo.getGoResulatonName());
        if(instEmplRecruitInfo.getGoResulaton() !=null && instEmplRecruitInfo.getGoResulatonName() !=null){
            instEmplRecruitInfo.setGoResulatonName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getGoResulatonName().replace("/", "_"), instEmplRecruitInfo.getGoResulaton()));
        }
        instEmplRecruitInfo.setGoResulaton(null);

        /*-------------------saving Committee to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getCommittee());
        log.debug("file image test--"+instEmplRecruitInfo.getCommitteeName());
        if(instEmplRecruitInfo.getCommittee() !=null && instEmplRecruitInfo.getCommitteeName() !=null){
            instEmplRecruitInfo.setCommitteeName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getCommitteeName().replace("/", "_"), instEmplRecruitInfo.getCommittee()));
        }
        instEmplRecruitInfo.setCommittee(null);

        /*-------------------saving Recommendation to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getRecommendation());
        log.debug("file image test--"+instEmplRecruitInfo.getRecommendationName());
        if(instEmplRecruitInfo.getRecommendation() !=null && instEmplRecruitInfo.getRecommendationName() !=null){
            instEmplRecruitInfo.setRecommendationName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getRecommendationName().replace("/", "_"), instEmplRecruitInfo.getRecommendation()));
        }
        instEmplRecruitInfo.setRecommendation(null);

        /*-------------------saving Sanction to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getSanction());
        log.debug("file image test--"+instEmplRecruitInfo.getSanctionName());
        if(instEmplRecruitInfo.getSanction() !=null && instEmplRecruitInfo.getSanctionName() !=null){
            instEmplRecruitInfo.setSanctionName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getSanctionName().replace("/", "_"), instEmplRecruitInfo.getSanction()));
        }
        instEmplRecruitInfo.setSanction(null);



        InstEmplRecruitInfo result = instEmplRecruitInfoRepository.save(instEmplRecruitInfo);
        instEmplRecruitInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmplRecruitInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmplRecruitInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmplRecruitInfos -> Updates an existing instEmplRecruitInfo.
     */
    @RequestMapping(value = "/instEmplRecruitInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplRecruitInfo> updateInstEmplRecruitInfo(@Valid @RequestBody InstEmplRecruitInfo instEmplRecruitInfo) throws URISyntaxException, Exception {
        log.debug("REST request to update InstEmplRecruitInfo : {}", instEmplRecruitInfo);
        if (instEmplRecruitInfo.getId() == null) {
            return createInstEmplRecruitInfo(instEmplRecruitInfo);
        }
        String filepath="/backup/teacher_info/";

        InstEmplRecruitInfo prevInstEmplRecruitInfo = instEmplRecruitInfoRepository.findOne(instEmplRecruitInfo.getId());

        /*-------------------saving AppoinmentLtr to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getAppoinmentLtr());
        log.debug("file image test--"+instEmplRecruitInfo.getAppoinmentLtrName());
        if(instEmplRecruitInfo.getAppoinmentLtr() !=null && instEmplRecruitInfo.getAppoinmentLtrName() !=null){
        //    instEmplRecruitInfo.setAppoinmentLtrName(AttachmentUtil.saveAttachment(filepath, instEmplRecruitInfo.getAppoinmentLtrName().replace("/", "_"), instEmplRecruitInfo.getAppoinmentLtr()));
            if(prevInstEmplRecruitInfo.getAppoinmentLtrName() !=null && prevInstEmplRecruitInfo.getAppoinmentLtrName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
                instEmplRecruitInfo.setAppoinmentLtrName(AttachmentUtil.replaceAttachment(filepath, prevInstEmplRecruitInfo.getAppoinmentLtrName(),instEmplRecruitInfo.getAppoinmentLtrName().replace("/", "_"), instEmplRecruitInfo.getAppoinmentLtr()));
            } else {
                instEmplRecruitInfo.setAppoinmentLtrName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getAppoinmentLtrName().replace("/", "_"), instEmplRecruitInfo.getAppoinmentLtr()));
            }
        }

        instEmplRecruitInfo.setAppoinmentLtr(null);

        /*-------------------saving JoiningLetter to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getJoiningLetter());
        log.debug("file image test--"+instEmplRecruitInfo.getJoiningLetterName());
        if(instEmplRecruitInfo.getJoiningLetter() !=null && instEmplRecruitInfo.getJoiningLetterName() !=null){
            //instEmplRecruitInfo.setJoiningLetterName(AttachmentUtil.saveAttachment(filepath, instEmplRecruitInfo.getJoiningLetterName().replace("/", "_"), instEmplRecruitInfo.getJoiningLetter()));
            if(prevInstEmplRecruitInfo.getJoiningLetterName() !=null && prevInstEmplRecruitInfo.getJoiningLetterName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
                instEmplRecruitInfo.setJoiningLetterName(AttachmentUtil.replaceAttachment(filepath, prevInstEmplRecruitInfo.getJoiningLetterName(), instEmplRecruitInfo.getJoiningLetterName().replace("/", "_"), instEmplRecruitInfo.getJoiningLetter()));
            } else {
                instEmplRecruitInfo.setJoiningLetterName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getJoiningLetterName().replace("/", "_"), instEmplRecruitInfo.getJoiningLetter()));
            }
        }
        instEmplRecruitInfo.setJoiningLetter(null);


        /*-------------------saving RecruitResult to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getRecruitResult());
        log.debug("file image test--"+instEmplRecruitInfo.getRecruitResultName());
        if(instEmplRecruitInfo.getRecruitResult() !=null && instEmplRecruitInfo.getRecruitResultName() !=null){
            //instEmplRecruitInfo.setRecruitResultName(AttachmentUtil.saveAttachment(filepath, instEmplRecruitInfo.getRecruitResultName().replace("/", "_"), instEmplRecruitInfo.getRecruitResult()));
            if(prevInstEmplRecruitInfo.getRecruitResultName() !=null && prevInstEmplRecruitInfo.getRecruitResultName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
                instEmplRecruitInfo.setRecruitResultName(AttachmentUtil.replaceAttachment(filepath, prevInstEmplRecruitInfo.getRecruitResultName(),instEmplRecruitInfo.getRecruitResultName().replace("/", "_"), instEmplRecruitInfo.getRecruitResult()));
            } else {
                instEmplRecruitInfo.setRecruitResultName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getRecruitResultName().replace("/", "_"), instEmplRecruitInfo.getRecruitResult()));
            }
        }
        instEmplRecruitInfo.setRecruitResult(null);


        /*-------------------saving RecruitNewsLocal to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getRecruitNewsLocal());
        log.debug("file image test--"+instEmplRecruitInfo.getRecruitNewsLocalName());
        if(instEmplRecruitInfo.getRecruitNewsLocal() !=null && instEmplRecruitInfo.getRecruitNewsLocalName() !=null){
            //instEmplRecruitInfo.setRecruitNewsLocalName(AttachmentUtil.saveAttachment(filepath, instEmplRecruitInfo.getRecruitNewsLocalName().replace("/", "_"), instEmplRecruitInfo.getRecruitNewsLocal()));
            if(prevInstEmplRecruitInfo.getRecruitNewsLocalName() !=null && prevInstEmplRecruitInfo.getRecruitNewsLocalName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
                instEmplRecruitInfo.setRecruitNewsLocalName(AttachmentUtil.replaceAttachment(filepath, prevInstEmplRecruitInfo.getRecruitNewsLocalName(),instEmplRecruitInfo.getRecruitNewsLocalName().replace("/", "_"), instEmplRecruitInfo.getRecruitNewsLocal()));
            } else {
                instEmplRecruitInfo.setRecruitNewsLocalName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getRecruitNewsLocalName().replace("/", "_"), instEmplRecruitInfo.getRecruitNewsLocal()));
            }
        }
        instEmplRecruitInfo.setRecruitNewsLocal(null);

        /*-------------------saving RecruitNewsDaily to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getRecruitNewsDaily());
        log.debug("file image test--"+instEmplRecruitInfo.getRecruitNewsDailyName());
        if(instEmplRecruitInfo.getRecruitNewsDaily() !=null && instEmplRecruitInfo.getRecruitNewsDailyName() !=null){
        //    instEmplRecruitInfo.setRecruitNewsDailyName(AttachmentUtil.saveAttachment(filepath, instEmplRecruitInfo.getRecruitNewsDailyName().replace("/", "_"), instEmplRecruitInfo.getRecruitNewsDaily()));
            if(prevInstEmplRecruitInfo.getRecruitNewsDailyName() !=null && prevInstEmplRecruitInfo.getRecruitNewsDailyName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
                instEmplRecruitInfo.setRecruitNewsDailyName(AttachmentUtil.replaceAttachment(filepath, prevInstEmplRecruitInfo.getRecruitNewsDailyName(), instEmplRecruitInfo.getRecruitNewsDailyName().replace("/", "_"), instEmplRecruitInfo.getRecruitNewsDaily()));
            } else {
                instEmplRecruitInfo.setRecruitNewsDailyName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getRecruitNewsDailyName().replace("/", "_"), instEmplRecruitInfo.getRecruitNewsDaily()));
            }
        }
        instEmplRecruitInfo.setRecruitNewsDaily(null);


        /*-------------------saving GoResulaton to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getGoResulaton());
        log.debug("file image test--"+instEmplRecruitInfo.getGoResulatonName());
        if(instEmplRecruitInfo.getGoResulaton() !=null && instEmplRecruitInfo.getGoResulatonName() !=null){
            //instEmplRecruitInfo.setGoResulatonName(AttachmentUtil.saveAttachment(filepath, instEmplRecruitInfo.getGoResulatonName().replace("/", "_"), instEmplRecruitInfo.getGoResulaton()));
            if(prevInstEmplRecruitInfo.getGoResulatonName() !=null && prevInstEmplRecruitInfo.getGoResulatonName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
                instEmplRecruitInfo.setGoResulatonName(AttachmentUtil.replaceAttachment(filepath, prevInstEmplRecruitInfo.getGoResulatonName(),instEmplRecruitInfo.getGoResulatonName().replace("/", "_"), instEmplRecruitInfo.getGoResulaton()));
            } else {
                instEmplRecruitInfo.setGoResulatonName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getGoResulatonName().replace("/", "_"), instEmplRecruitInfo.getGoResulaton()));
            }
        }
        instEmplRecruitInfo.setGoResulaton(null);

        /*-------------------saving Committee to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getCommittee());
        log.debug("file image test--"+instEmplRecruitInfo.getCommitteeName());
        if(instEmplRecruitInfo.getCommittee() !=null && instEmplRecruitInfo.getCommitteeName() !=null){
            //instEmplRecruitInfo.setCommitteeName(AttachmentUtil.saveAttachment(filepath, instEmplRecruitInfo.getCommitteeName().replace("/", "_"), instEmplRecruitInfo.getCommittee()));
            if(prevInstEmplRecruitInfo.getCommitteeName() !=null && prevInstEmplRecruitInfo.getCommitteeName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
                instEmplRecruitInfo.setCommitteeName(AttachmentUtil.replaceAttachment(filepath, prevInstEmplRecruitInfo.getCommitteeName(), instEmplRecruitInfo.getCommitteeName().replace("/", "_"), instEmplRecruitInfo.getCommittee()));
            } else {
                instEmplRecruitInfo.setCommitteeName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getCommitteeName().replace("/", "_"), instEmplRecruitInfo.getCommittee()));
            }
        }
        instEmplRecruitInfo.setCommittee(null);

        /*-------------------saving Recommendation to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getRecommendation());
        log.debug("file image test--"+instEmplRecruitInfo.getRecommendationName());
        if(instEmplRecruitInfo.getRecommendation() !=null && instEmplRecruitInfo.getRecommendationName() !=null){
            //instEmplRecruitInfo.setRecommendationName(AttachmentUtil.saveAttachment(filepath, instEmplRecruitInfo.getRecommendationName().replace("/", "_"), instEmplRecruitInfo.getRecommendation()));
            if(prevInstEmplRecruitInfo.getRecommendationName() !=null && prevInstEmplRecruitInfo.getRecommendationName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
                instEmplRecruitInfo.setRecommendationName(AttachmentUtil.replaceAttachment(filepath,prevInstEmplRecruitInfo.getRecommendationName(), instEmplRecruitInfo.getRecommendationName().replace("/", "_"), instEmplRecruitInfo.getRecommendation()));
            } else {
                instEmplRecruitInfo.setRecommendationName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getRecommendationName().replace("/", "_"), instEmplRecruitInfo.getRecommendation()));
            }
        }
        instEmplRecruitInfo.setRecommendation(null);

        /*-------------------saving Sanction to local disk------ */
        log.debug("file name test--"+instEmplRecruitInfo.getSanction());
        log.debug("file image test--"+instEmplRecruitInfo.getSanctionName());
        if(instEmplRecruitInfo.getSanction() !=null && instEmplRecruitInfo.getSanctionName() !=null){
            //instEmplRecruitInfo.setSanctionName(AttachmentUtil.saveAttachment(filepath, instEmplRecruitInfo.getSanctionName().replace("/", "_"), instEmplRecruitInfo.getSanction()));
            if(prevInstEmplRecruitInfo.getSanctionName() !=null && prevInstEmplRecruitInfo.getSanctionName().length()>0){
                log.debug(" image replace trigger----------------------------------------------");
                instEmplRecruitInfo.setSanctionName(AttachmentUtil.replaceAttachment(filepath, prevInstEmplRecruitInfo.getSanctionName(), instEmplRecruitInfo.getSanctionName().replace("/", "_"), instEmplRecruitInfo.getSanction()));
            } else {
                instEmplRecruitInfo.setSanctionName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplRecruitInfo.getSanctionName().replace("/", "_"), instEmplRecruitInfo.getSanction()));
            }
        }
        instEmplRecruitInfo.setSanction(null);


        InstEmplRecruitInfo result = instEmplRecruitInfoRepository.save(instEmplRecruitInfo);
        instEmplRecruitInfoSearchRepository.save(instEmplRecruitInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmplRecruitInfo", instEmplRecruitInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmplRecruitInfos -> get all the instEmplRecruitInfos.
     */
    @RequestMapping(value = "/instEmplRecruitInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplRecruitInfo>> getAllInstEmplRecruitInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmplRecruitInfo> page = instEmplRecruitInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplRecruitInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmplRecruitInfos/:id -> get the "id" instEmplRecruitInfo.
     */
    @RequestMapping(value = "/instEmplRecruitInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplRecruitInfo> getInstEmplRecruitInfo(@PathVariable Long id) {
        log.debug("REST request to get InstEmplRecruitInfo : {}", id);
        return Optional.ofNullable(instEmplRecruitInfoRepository.findOne(id))
            .map(instEmplRecruitInfo -> new ResponseEntity<>(
                instEmplRecruitInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /instEmplRecruitInfos/:id -> get the "id" instEmplRecruitInfo.
     */
    @RequestMapping(value = "/instEmplRecruitInfos/instEmployee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplRecruitInfo> getInstEmplRecruitInfoByEmployee(@PathVariable Long id) {
        log.debug("REST request to get InstEmplRecruitInfo : {}", id);
        return Optional.ofNullable(instEmplRecruitInfoRepository.findByInstEmployeeId(id))
            .map(instEmplRecruitInfo -> new ResponseEntity<>(
                instEmplRecruitInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * GET  /instEmplRecruitInfos/:id -> get the "id" instEmplRecruitInfo.
     */
    @RequestMapping(value = "/instEmplRecruitInfosCurrent",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplRecruitInfo> getInstEmplRecruitInfoCurrent() throws Exception{
        log.debug("REST request to get InstEmplRecruitInfo : {}");
        String filepath="/backup/teacher_info/";
        InstEmplRecruitInfo instEmplRecruitInfoResult=instEmplRecruitInfoRepository.findOneByCurrent();

        if(instEmplRecruitInfoResult !=null){
        if(instEmplRecruitInfoResult.getAppoinmentLtrName() !=null && instEmplRecruitInfoResult.getAppoinmentLtrName().length()>0){
            instEmplRecruitInfoResult.setAppoinmentLtr(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getAppoinmentLtrName()));
            instEmplRecruitInfoResult.setAppoinmentLtrName(instEmplRecruitInfoResult.getAppoinmentLtrName().substring(0, (instEmplRecruitInfoResult.getAppoinmentLtrName().length() - 17)));
        }

        if(instEmplRecruitInfoResult.getJoiningLetterName() !=null && instEmplRecruitInfoResult.getJoiningLetterName().length()>0){
            instEmplRecruitInfoResult.setJoiningLetter(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getJoiningLetterName()));
            instEmplRecruitInfoResult.setJoiningLetterName(instEmplRecruitInfoResult.getJoiningLetterName().substring(0, (instEmplRecruitInfoResult.getJoiningLetterName().length() - 17)));
        }

        if(instEmplRecruitInfoResult.getRecruitResultName() !=null && instEmplRecruitInfoResult.getRecruitResultName().length()>0){
            instEmplRecruitInfoResult.setRecruitResult(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitResultName()));
            instEmplRecruitInfoResult.setRecruitResultName(instEmplRecruitInfoResult.getRecruitResultName().substring(0, (instEmplRecruitInfoResult.getRecruitResultName().length() - 17)));
        }

        if(instEmplRecruitInfoResult.getRecruitNewsLocalName() !=null && instEmplRecruitInfoResult.getRecruitNewsLocalName().length()>0){
            instEmplRecruitInfoResult.setRecruitNewsLocal(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitNewsLocalName()));
            instEmplRecruitInfoResult.setRecruitNewsLocalName(instEmplRecruitInfoResult.getRecruitNewsLocalName().substring(0, (instEmplRecruitInfoResult.getRecruitNewsLocalName().length() - 17)));
        }

        if(instEmplRecruitInfoResult.getRecruitNewsDailyName() !=null && instEmplRecruitInfoResult.getRecruitNewsDailyName().length()>0){
            instEmplRecruitInfoResult.setRecruitNewsDaily(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecruitNewsDailyName()));
            instEmplRecruitInfoResult.setRecruitNewsDailyName(instEmplRecruitInfoResult.getRecruitNewsDailyName().substring(0, (instEmplRecruitInfoResult.getRecruitNewsDailyName().length() - 17)));
        }

        if(instEmplRecruitInfoResult.getGoResulatonName() !=null && instEmplRecruitInfoResult.getGoResulatonName().length()>0){
            instEmplRecruitInfoResult.setGoResulaton(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getGoResulatonName()));
            instEmplRecruitInfoResult.setGoResulatonName(instEmplRecruitInfoResult.getGoResulatonName().substring(0, (instEmplRecruitInfoResult.getGoResulatonName().length() - 17)));
        }

        if(instEmplRecruitInfoResult.getCommitteeName() !=null && instEmplRecruitInfoResult.getCommitteeName().length()>0){
            instEmplRecruitInfoResult.setCommittee(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getCommitteeName()));
            instEmplRecruitInfoResult.setCommitteeName(instEmplRecruitInfoResult.getCommitteeName().substring(0, (instEmplRecruitInfoResult.getCommitteeName().length() - 17)));
        }

        if(instEmplRecruitInfoResult.getRecommendationName() !=null && instEmplRecruitInfoResult.getRecommendationName().length()>0){
            instEmplRecruitInfoResult.setRecommendation(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getRecommendationName()));
            instEmplRecruitInfoResult.setRecommendationName(instEmplRecruitInfoResult.getRecommendationName().substring(0, (instEmplRecruitInfoResult.getRecommendationName().length() - 17)));
        }

        if(instEmplRecruitInfoResult.getSanctionName() !=null && instEmplRecruitInfoResult.getSanctionName().length()>0){
            instEmplRecruitInfoResult.setSanction(AttachmentUtil.retriveAttachment(filepath, instEmplRecruitInfoResult.getSanctionName()));
            instEmplRecruitInfoResult.setSanctionName(instEmplRecruitInfoResult.getSanctionName().substring(0, (instEmplRecruitInfoResult.getSanctionName().length() - 17)));
        }
        }


       /* return Optional.ofNullable(instEmplRecruitInfoRepository.findOneByCurrent())
            .map(instEmplRecruitInfo -> new ResponseEntity<>(
                instEmplRecruitInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        return Optional.ofNullable(instEmplRecruitInfoResult)
            .map(instEmplRecruitInfo -> new ResponseEntity<>(
                instEmplRecruitInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    /**
     * DELETE  /instEmplRecruitInfos/:id -> delete the "id" instEmplRecruitInfo.
     */
    @RequestMapping(value = "/instEmplRecruitInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmplRecruitInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstEmplRecruitInfo : {}", id);
        instEmplRecruitInfoRepository.delete(id);
        instEmplRecruitInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmplRecruitInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmplRecruitInfos/:query -> search for the instEmplRecruitInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmplRecruitInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplRecruitInfo> searchInstEmplRecruitInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instEmplRecruitInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
