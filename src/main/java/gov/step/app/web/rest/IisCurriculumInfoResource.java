package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.repository.IisCurriculumInfoRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.InstituteStatusRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.IisCurriculumInfoSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.DateResource;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing IisCurriculumInfo.
 */
@RestController
@RequestMapping("/api")
public class IisCurriculumInfoResource {

    private final Logger log = LoggerFactory.getLogger(IisCurriculumInfoResource.class);

    @Inject
    private IisCurriculumInfoRepository iisCurriculumInfoRepository;

    @Inject
    private IisCurriculumInfoSearchRepository iisCurriculumInfoSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private UserRepository userRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    /**
     * POST  /iisCurriculumInfos -> Create a new iisCurriculumInfo.
     */
    @RequestMapping(value = "/iisCurriculumInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCurriculumInfo> createIisCurriculumInfo(@RequestBody IisCurriculumInfo iisCurriculumInfo) throws URISyntaxException {
        log.debug("REST request to save IisCurriculumInfo : {}", iisCurriculumInfo);
        //log.debug("\n BAD CODE ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        if (iisCurriculumInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new iisCurriculumInfo cannot already have an ID").body(null);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null) {
            instituteStatus.setCurriculum(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }
        iisCurriculumInfo.setInstitute(institute);
        iisCurriculumInfo.setStatus(0);
        //iisCurriculumInfo.setMpoEnlisted(true);
        iisCurriculumInfo.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        iisCurriculumInfo.setCreateDate(LocalDate.now());
        //log.debug("\n iisCurriculumInfo ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+iisCurriculumInfo);
        IisCurriculumInfo result = iisCurriculumInfoRepository.save(iisCurriculumInfo);
        iisCurriculumInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/iisCurriculumInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("iisCurriculumInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /iisCurriculumInfos -> Updates an existing iisCurriculumInfo.
     */
    @RequestMapping(value = "/iisCurriculumInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCurriculumInfo> updateIisCurriculumInfo(@RequestBody IisCurriculumInfo iisCurriculumInfo) throws URISyntaxException {
        log.debug("REST request to update IisCurriculumInfo : {}", iisCurriculumInfo);
        if (iisCurriculumInfo.getId() == null) {
            return createIisCurriculumInfo(iisCurriculumInfo);
        }

        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null) {
            instituteStatus.setCurriculum(1);
            instituteStatus.setUpdatedDate(LocalDate.now());

            instituteStatusRepository.save(instituteStatus);


        }
        iisCurriculumInfo.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        iisCurriculumInfo.setUpdateDate(LocalDate.now());
        IisCurriculumInfo result = iisCurriculumInfoRepository.save(iisCurriculumInfo);
        iisCurriculumInfoSearchRepository.save(iisCurriculumInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("iisCurriculumInfo", iisCurriculumInfo.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /iisCurriculumInfos -> Updates an existing iisCurriculumInfo.
     */
    @RequestMapping(value = "/iisCurriculumInfos/mpoDate/provide",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCurriculumInfo> updateIisCurriculumInfoWithMpoDate(@RequestBody IisCurriculumInfo iisCurriculumInfo) throws URISyntaxException {
        log.debug("REST request to update IisCurriculumInfo : {}", iisCurriculumInfo);
        if (iisCurriculumInfo.getId() == null) {
            return createIisCurriculumInfo(iisCurriculumInfo);
        }

        iisCurriculumInfo.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        iisCurriculumInfo.setUpdateDate(LocalDate.now());
        IisCurriculumInfo result = iisCurriculumInfoRepository.save(iisCurriculumInfo);
        iisCurriculumInfoSearchRepository.save(iisCurriculumInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("iisCurriculumInfo", iisCurriculumInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCurriculumInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCurriculumInfo>> getAllIisCurriculumInfos(Pageable pageable)
        throws URISyntaxException {
        Page<IisCurriculumInfo> page = iisCurriculumInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCurriculumInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCurriculumInfos/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCurriculumInfo>> getAllIisCurriculumInfosOfCurrent(Pageable pageable)
        throws URISyntaxException {
        Page<IisCurriculumInfo> page = iisCurriculumInfoRepository.findAllCurriculumByUserIsCurrentUser(instituteRepository.findOneByUserIsCurrentUser() != null? instituteRepository.findOneByUserIsCurrentUser().getId(): 0, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCurriculumInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCurriculumInfos/curriculum/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsCurriculum>> getAllIisCurriculumsOfCurren(Pageable pageable)
        throws URISyntaxException {
        Page<CmsCurriculum> page = iisCurriculumInfoRepository.findAllCurriculumByInstituteId(instituteRepository.findOneByUserIsCurrentUser().getId(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCurriculumInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCurriculumInfos/curriculums/{instituteId}/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCurriculumInfo>> getAllCurriculumsInstituteWithStatus(Pageable pageable, @PathVariable Long instituteId, @PathVariable Integer status)
        throws URISyntaxException {
        Page<IisCurriculumInfo> page = iisCurriculumInfoRepository.findListByInstituteIdAndStatus(pageable, instituteId, status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCurriculumInfos/curriculums/"+instituteId+"/"+status);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCurriculumInfos/:id -> get the "id" iisCurriculumInfo.
     */
    @RequestMapping(value = "/iisCurriculumInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCurriculumInfo> getIisCurriculumInfo(@PathVariable Long id) {
        log.debug("REST request to get IisCurriculumInfo : {}", id);
        return Optional.ofNullable(iisCurriculumInfoRepository.findOne(id))
            .map(iisCurriculumInfo -> new ResponseEntity<>(
                iisCurriculumInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @RequestMapping(value = "/iisCurriculumInfos/FindCurriculumByInstId",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getFindCurriculumByInstId() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();
        System.out.println("=====================Current User id====================");
        System.out.println(userId);
        System.out.println("===================== Only id====================");
        System.out.println(userName);

        List<Map<String,Object>> ByInspIdlistRpt = rptJdbcDao.findCurriculumByInstId(userName);

        return ByInspIdlistRpt;
    }

    /**
     * DELETE  /iisCurriculumInfos/:id -> delete the "id" iisCurriculumInfo.
     */
    @RequestMapping(value = "/iisCurriculumInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIisCurriculumInfo(@PathVariable Long id) {
        log.debug("REST request to delete IisCurriculumInfo : {}", id);
        iisCurriculumInfoRepository.delete(id);
        iisCurriculumInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("iisCurriculumInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/iisCurriculumInfos/:query -> search for the iisCurriculumInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/iisCurriculumInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<IisCurriculumInfo> searchIisCurriculumInfos(@PathVariable String query) {
        return StreamSupport
            .stream(iisCurriculumInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


//to find mpo enlisted curriculums
    /**
     * GET  /iisCurriculumInfos/mpoEnlisted -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCurriculumInfos/mpoEnlisted",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCurriculumInfo>> getAllMpoEnlistedIisCurriculum(Pageable pageable)
        throws URISyntaxException {
        Page<IisCurriculumInfo> page = iisCurriculumInfoRepository.findAllMpoEnlistedCCurriculum(Boolean.TRUE, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCourseInfos/mpoEnlisted");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
