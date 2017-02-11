package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.repository.IisCurriculumInfoTempRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.InstituteStatusRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.IisCurriculumInfoTempSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import gov.step.app.web.rest.util.TransactionIdResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing IisCurriculumInfoTemp.
 */
@RestController
@RequestMapping("/api")
public class IisCurriculumInfoTempResource {

    private final Logger log = LoggerFactory.getLogger(IisCurriculumInfoTempResource.class);

    @Inject
    private IisCurriculumInfoTempRepository iisCurriculumInfoTempRepository;

    @Inject
    private IisCurriculumInfoTempSearchRepository iisCurriculumInfoTempSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    @Autowired
    private RptJdbcDao rptJdbcDao;

    /**
     * POST  /iisCurriculumInfoTemps -> Create a new iisCurriculumInfoTemp.
     */
    @RequestMapping(value = "/iisCurriculumInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCurriculumInfoTemp> createIisCurriculumInfoTemp(@RequestBody IisCurriculumInfoTemp iisCurriculumInfoTemp) throws URISyntaxException {
        log.debug("REST request to save IisCurriculumInfoTemp : {}", iisCurriculumInfoTemp);
        if (iisCurriculumInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new iisCurriculumInfoTemp cannot already have an ID").body(null);
        }

        iisCurriculumInfoTemp.setInstitute(instituteRepository.findOneByUserIsCurrentUser());
        iisCurriculumInfoTemp.setStatus(0);
        iisCurriculumInfoTemp.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        iisCurriculumInfoTemp.setCreateDate(LocalDate.now());
        TransactionIdResource transactionIdResource = new TransactionIdResource();
        iisCurriculumInfoTemp.setCurId(transactionIdResource.getGeneratedid("cur-id-"));
        IisCurriculumInfoTemp result = iisCurriculumInfoTempRepository.save(iisCurriculumInfoTemp);

        iisCurriculumInfoTempSearchRepository.save(result);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setCurriculum(1);
            instituteStatusRepository.save(instituteStatus);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        return ResponseEntity.created(new URI("/api/iisCurriculumInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("iisCurriculumInfoTemp", result.getId().toString()))
            .body(result);

    }

    /**
     * PUT  /iisCurriculumInfoTemps -> Updates an existing iisCurriculumInfoTemp.
     */
    @RequestMapping(value = "/iisCurriculumInfoTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCurriculumInfoTemp> updateIisCurriculumInfoTemp(@RequestBody IisCurriculumInfoTemp iisCurriculumInfoTemp) throws URISyntaxException {
        log.debug("REST request to update IisCurriculumInfoTemp : {}", iisCurriculumInfoTemp);
        if (iisCurriculumInfoTemp.getId() == null) {
            return createIisCurriculumInfoTemp(iisCurriculumInfoTemp);
        }
        iisCurriculumInfoTemp.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        iisCurriculumInfoTemp.setUpdateDate(LocalDate.now());
        //add for put effect on updating  temp date:03-12-16:9:35 pm by shuvo
        iisCurriculumInfoTemp.setStatus(0);

        IisCurriculumInfoTemp result = iisCurriculumInfoTempRepository.save(iisCurriculumInfoTemp);
        iisCurriculumInfoTempSearchRepository.save(iisCurriculumInfoTemp);

        if(SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")){
            InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
            if(instituteStatus != null){
                log.debug("set institute status one for update : {}", instituteStatus);
                log.debug("=================curriculum status========", instituteStatus);

                instituteStatus.setCurriculum(1);
                log.debug("after update curriculum : {}", instituteStatus);

                instituteStatusRepository.save(instituteStatus);
            }
            Institute institute = instituteRepository.findOneByUserIsCurrentUser();
            institute.setInfoEditStatus("Pending");
            instituteRepository.save(institute);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("iisCurriculumInfoTemp", iisCurriculumInfoTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /iisCurriculumInfoTemps -> get all the iisCurriculumInfoTemps.
     */
    @RequestMapping(value = "/iisCurriculumInfoTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCurriculumInfoTemp>> getAllIisCurriculumInfoTemps(Pageable pageable)
        throws URISyntaxException {
        Page<IisCurriculumInfoTemp> page = iisCurriculumInfoTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCurriculumInfoTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/iisCurriculumInfoTemps/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCurriculumInfoTemp>> getAllIisCurriculumInfosOfCurrent(Pageable pageable)
        throws URISyntaxException {
        Page<IisCurriculumInfoTemp> page = iisCurriculumInfoTempRepository.findAllCurriculumByUserIsCurrentUser(instituteRepository.findOneByUserIsCurrentUser() != null? instituteRepository.findOneByUserIsCurrentUser().getId(): 0, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCurriculumInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCurriculumInfoTemps/curriculum/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsCurriculum>> getAllIisCurriculumsOfCurren(Pageable pageable)
        throws URISyntaxException {
        Page<CmsCurriculum> page = iisCurriculumInfoTempRepository.findAllCurriculumByInstituteId(instituteRepository.findOneByUserIsCurrentUser().getId(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCurriculumInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCurriculumInfos -> get all the iisCurriculumInfos.
     */
    @RequestMapping(value = "/iisCurriculumInfoTemps/curriculums/{instituteId}/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IisCurriculumInfoTemp>> getAllCurriculumsInstituteWithStatus(Pageable pageable, @PathVariable Long instituteId, @PathVariable Integer status)
        throws URISyntaxException {
        Page<IisCurriculumInfoTemp> page = iisCurriculumInfoTempRepository.findListByInstituteIdAndStatus(pageable, instituteId, status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iisCurriculumInfoTemps/curriculums/"+instituteId+"/"+status);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /iisCurriculumInfoTemps/:id -> get the "id" iisCurriculumInfoTemp.
     */
    @RequestMapping(value = "/iisCurriculumInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCurriculumInfoTemp> getIisCurriculumInfoTemp(@PathVariable Long id) {
        log.debug("REST request to get IisCurriculumInfoTemp : {}", id);
        return Optional.ofNullable(iisCurriculumInfoTempRepository.findOne(id))
            .map(iisCurriculumInfoTemp -> new ResponseEntity<>(
                iisCurriculumInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @RequestMapping(value = "/iisCurriculumInfoTemps/curriculums/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getFindCurriculumByInstId() {

        return rptJdbcDao.findCurriculumTempByInstId(SecurityUtils.getCurrentUserLogin());
    }

    /**
     * DELETE  /iisCurriculumInfoTemps/:id -> delete the "id" iisCurriculumInfoTemp.
     */
    @RequestMapping(value = "/iisCurriculumInfoTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIisCurriculumInfoTemp(@PathVariable Long id) {
        log.debug("REST request to delete IisCurriculumInfoTemp : {}", id);
        iisCurriculumInfoTempRepository.delete(id);
        iisCurriculumInfoTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("iisCurriculumInfoTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/iisCurriculumInfoTemps/:query -> search for the iisCurriculumInfoTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/iisCurriculumInfoTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<IisCurriculumInfoTemp> searchIisCurriculumInfoTemps(@PathVariable String query) {
        return StreamSupport
            .stream(iisCurriculumInfoTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
