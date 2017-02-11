package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.repository.IisCourseInfoTempRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.InstituteStatusRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.IisCourseInfoTempSearchRepository;
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
 * REST controller for managing IisCourseInfoTemp.
 */
@RestController
@RequestMapping("/api")
public class IisCourseInfoTempResource {

    private final Logger log = LoggerFactory.getLogger(IisCourseInfoTempResource.class);

    @Inject
    private IisCourseInfoTempRepository iisCourseInfoTempRepository;

    @Inject
    private IisCourseInfoTempSearchRepository iisCourseInfoTempSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private UserRepository userRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    /**
     * POST  /iisCourseInfoTemps -> Create a new iisCourseInfoTemp.
     */
    @RequestMapping(value = "/iisCourseInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCourseInfoTemp> createIisCourseInfoTemp(@RequestBody IisCourseInfoTemp iisCourseInfoTemp) throws URISyntaxException {
        log.debug("REST request to save IisCourseInfoTemp : {}", iisCourseInfoTemp);
        if (iisCourseInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new iisCourseInfoTemp cannot already have an ID").body(null);
        }
        iisCourseInfoTemp.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        iisCourseInfoTemp.setCreateDate(LocalDate.now());
        IisCourseInfoTemp result = iisCourseInfoTempRepository.save(iisCourseInfoTemp);
        if(result != null){
            Institute institute = instituteRepository.findOneByUserIsCurrentUser();
            institute.setInfoEditStatus("Pending");
            Institute instituteSaveResult = instituteRepository.save(institute);
            if(instituteSaveResult != null ){
                InstituteStatus instituteStatus =  instituteStatusRepository.findOneByCurrentInstitute();
                if(instituteStatus != null){
                    instituteStatus.setCourse(1);
                    InstituteStatus instituteStatusSaveResult =  instituteStatusRepository.save(instituteStatus);
                }

            }
        }
        iisCourseInfoTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/iisCourseInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("iisCourseInfoTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /iisCourseInfoTemps -> Updates an existing iisCourseInfoTemp.
     */
    @RequestMapping(value = "/iisCourseInfoTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCourseInfoTemp> updateIisCourseInfoTemp(@RequestBody IisCourseInfoTemp iisCourseInfoTemp) throws URISyntaxException {
        log.debug("REST request to update IisCourseInfoTemp : {}", iisCourseInfoTemp);
        if (iisCourseInfoTemp.getId() == null) {
            return createIisCourseInfoTemp(iisCourseInfoTemp);
        }
        iisCourseInfoTemp.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        iisCourseInfoTemp.setUpdateDate(LocalDate.now());
        IisCourseInfoTemp result = iisCourseInfoTempRepository.save(iisCourseInfoTemp);
        iisCourseInfoTempSearchRepository.save(iisCourseInfoTemp);
        if(result != null){
            Institute institute = instituteRepository.findOneByUserIsCurrentUser();
            institute.setInfoEditStatus("Pending");
            Institute instituteSaveResult = instituteRepository.save(institute);
            if(instituteSaveResult != null ){
                InstituteStatus instituteStatus =  instituteStatusRepository.findOneByCurrentInstitute();
                instituteStatus.setCourse(1);
                InstituteStatus instituteStatusSaveResult =  instituteStatusRepository.save(instituteStatus);
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("iisCourseInfoTemp", iisCourseInfoTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /iisCourseInfoTemps -> get all the iisCourseInfoTemps.
     */
    @RequestMapping(value = "/iisCourseInfoTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCourseInfoTemp>> getAllIisCourseInfoTemps(Pageable pageable)
        throws URISyntaxException {
        Page<IisCourseInfoTemp> page = iisCourseInfoTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCourseInfoTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCourseInfoTemps/:id -> get the "id" iisCourseInfoTemp.
     */
    @RequestMapping(value = "/iisCourseInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCourseInfoTemp> getIisCourseInfoTemp(@PathVariable Long id) {
        log.debug("REST request to get IisCourseInfoTemp : {}", id);
        return Optional.ofNullable(iisCourseInfoTempRepository.findOne(id))
            .map(iisCourseInfoTemp -> new ResponseEntity<>(
                iisCourseInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/iisCourseInfoTemps/FindCourseByInstId",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getFindCourseByInstId() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();
        System.out.println("=====================Current User id====================");
        System.out.println(userId);
        System.out.println("===================== Only id====================");
        System.out.println(userName);

        List<Map<String,Object>> ByInspIdlistRpt = rptJdbcDao.findCourseByInstIdfromTemp(userName);

        return ByInspIdlistRpt;
    }


    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCourseInfoTemps/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCourseInfoTemp>> getAllIisCoursesOfCurrent(Pageable pageable)
        throws URISyntaxException {
        Page<IisCourseInfoTemp> page = iisCourseInfoTempRepository.findAllCourseByCurrentInstitute(instituteRepository.findOneByUserIsCurrentUser().getId(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCourseInfoTemps/currentInstitute");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCourseInfoTemps/cmsTrades/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsTrade>> getAllTradesOfCurrent(Pageable pageable)
        throws URISyntaxException {
        Page<CmsTrade> page = iisCourseInfoTempRepository.findAllTradeByInstituteId(instituteRepository.findOneByUserIsCurrentUser().getId(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCourseInfoTemps/cmsTrades/currentInstitute");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCourseInfoTemps/cmsTrades/institute/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsTrade>> getAllTradesOfInstitute(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<CmsTrade> page = iisCourseInfoTempRepository.findAllTradeByInstituteId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCourseInfoTemps/cmsTrades/institute/"+id);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * DELETE  /iisCourseInfoTemps/:id -> delete the "id" iisCourseInfoTemp.
     */
    @RequestMapping(value = "/iisCourseInfoTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIisCourseInfoTemp(@PathVariable Long id) {
        log.debug("REST request to delete IisCourseInfoTemp : {}", id);
        iisCourseInfoTempRepository.delete(id);
        iisCourseInfoTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("iisCourseInfoTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/iisCourseInfoTemps/:query -> search for the iisCourseInfoTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/iisCourseInfoTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<IisCourseInfoTemp> searchIisCourseInfoTemps(@PathVariable String query) {
        return StreamSupport
            .stream(iisCourseInfoTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
