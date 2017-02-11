package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsTrade;
import gov.step.app.domain.IisCourseInfo;
import gov.step.app.domain.Institute;
import gov.step.app.repository.IisCourseInfoRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.IisCourseInfoSearchRepository;
import gov.step.app.security.SecurityUtils;
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
 * REST controller for managing IisCourseInfo.
 */
@RestController
@RequestMapping("/api")
public class IisCourseInfoResource {

    private final Logger log = LoggerFactory.getLogger(IisCourseInfoResource.class);

    @Inject
    private IisCourseInfoRepository iisCourseInfoRepository;

    @Inject
    private IisCourseInfoSearchRepository iisCourseInfoSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /iisCourseInfos -> Create a new iisCourseInfo.
     */
    @RequestMapping(value = "/iisCourseInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCourseInfo> createIisCourseInfo(@RequestBody IisCourseInfo iisCourseInfo) throws URISyntaxException {
        log.debug("REST request to save IisCourseInfo : {}", iisCourseInfo);
        if (iisCourseInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new iisCourseInfo cannot already have an ID").body(null);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);
        iisCourseInfo.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        iisCourseInfo.setCreateDate(LocalDate.now());
        iisCourseInfo.setVacancyRoleApplied(false);
        IisCourseInfo result = iisCourseInfoRepository.save(iisCourseInfo);
        iisCourseInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/iisCourseInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("iisCourseInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /iisCourseInfos -> Updates an existing iisCourseInfo.
     */
    @RequestMapping(value = "/iisCourseInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCourseInfo> updateIisCourseInfo(@RequestBody IisCourseInfo iisCourseInfo) throws URISyntaxException {
        log.debug("REST request to update IisCourseInfo : {}", iisCourseInfo);
        if (iisCourseInfo.getId() == null) {
            return createIisCourseInfo(iisCourseInfo);
        }

        iisCourseInfo.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        iisCourseInfo.setUpdateDate(LocalDate.now());
        IisCourseInfo result = iisCourseInfoRepository.save(iisCourseInfo);
        iisCourseInfoSearchRepository.save(iisCourseInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("iisCourseInfo", iisCourseInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /iisCourseInfos -> get all the iisCourseInfos.
     */
    @RequestMapping(value = "/iisCourseInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCourseInfo>> getAllIisCourseInfos(Pageable pageable)
        throws URISyntaxException {
        Page<IisCourseInfo> page = iisCourseInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCourseInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCourseInfos/:id -> get the "id" iisCourseInfo.
     */
    @RequestMapping(value = "/iisCourseInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCourseInfo> getIisCourseInfo(@PathVariable Long id) {
        log.debug("REST request to get IisCourseInfo : {}", id);
        return Optional.ofNullable(iisCourseInfoRepository.findOne(id))
            .map(iisCourseInfo -> new ResponseEntity<>(
                iisCourseInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/iisCourseInfos/FindCourseByInstId",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getFindCourseByInstId() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();
        System.out.println("=====================Current User id====================");
        System.out.println(userId);
        System.out.println("===================== Only id====================");
        System.out.println(userName);

        List<Map<String,Object>> ByInspIdlistRpt = rptJdbcDao.findCourseByInstId(userName);

        return ByInspIdlistRpt;
    }



    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCourseInfos/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCourseInfo>> getAllIisCoursesOfCurrent(Pageable pageable)
        throws URISyntaxException {
        Page<IisCourseInfo> page = iisCourseInfoRepository.findAllCourseByCurrentInstitute(instituteRepository.findOneByUserIsCurrentUser().getId(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCourseInfos/currentInstitute");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

//to find mpo enlisted courses
    /**
     * GET  /iisCurriculumInfos/mpoEnlisted -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCourseInfos/mpoEnlisted",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCourseInfo>> getAllMpoEnlistedIisCourses(Pageable pageable)
        throws URISyntaxException {
        Page<IisCourseInfo> page = iisCourseInfoRepository.findAllMpoEnlistedCourse(Boolean.TRUE, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCourseInfos/mpoEnlisted");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCourseInfos/currentInstitute/trade/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCourseInfo> getIisCoursesOfCurrentInstByTrade(@PathVariable Long id)
        throws URISyntaxException {
        return Optional.ofNullable(iisCourseInfoRepository.findByInstituteIdAndCmsCourseId(instituteRepository.findOneByUserIsCurrentUser().getId(), id))
            .map(iisCourseInfo -> new ResponseEntity<>(
                iisCourseInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCourseInfos/cmsTrades/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsTrade>> getAllTradesOfCurrent(Pageable pageable)
        throws URISyntaxException {
        Page<CmsTrade> page = iisCourseInfoRepository.findAllTradeByInstituteId(instituteRepository.findOneByUserIsCurrentUser().getId(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCourseInfos/cmsTrades/currentInstitute");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

/**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCourseInfos/cmsTrades/institute/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsTrade>> getAllTradesOfInstitute(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<CmsTrade> page = iisCourseInfoRepository.findAllTradeByInstituteId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCourseInfos/cmsTrades/institute/"+id);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * DELETE  /iisCourseInfos/:id -> delete the "id" iisCourseInfo.
     */
    @RequestMapping(value = "/iisCourseInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIisCourseInfo(@PathVariable Long id) {
        log.debug("REST request to delete IisCourseInfo : {}", id);
        iisCourseInfoRepository.delete(id);
        iisCourseInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("iisCourseInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/iisCourseInfos/:query -> search for the iisCourseInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/iisCourseInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<IisCourseInfo> searchIisCourseInfos(@PathVariable String query) {
        return StreamSupport
            .stream(iisCourseInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
