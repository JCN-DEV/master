package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstGenInfo;
import gov.step.app.domain.MpoApplication;
import gov.step.app.domain.TimeScale;
import gov.step.app.domain.User;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.InstGenInfoRepository;
import gov.step.app.repository.TimeScaleRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.TimeScaleSearchRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing TimeScale.
 */
@RestController
@RequestMapping("/api")
public class TimeScaleResource {

    private final Logger log = LoggerFactory.getLogger(TimeScaleResource.class);

    @Inject
    private TimeScaleRepository timeScaleRepository;

    @Inject
    private TimeScaleSearchRepository timeScaleSearchRepository;
    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private UserRepository userRepository;

    /**
     * POST /timeScales -> Create a new timeScale.
     */
    @RequestMapping(value = "/timeScales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScale> createTimeScale(@Valid @RequestBody TimeScale timeScale)
        throws URISyntaxException {
        log.debug("REST request to save TimeScale : {}", timeScale);
        if (timeScale.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new timeScale cannot already have an ID")
                .body(null);
        }
        TimeScale result = timeScaleRepository.save(timeScale);
        timeScaleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/timeScales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeScale", result.getId().toString())).body(result);
    }

    /**
     * PUT /timeScales -> Updates an existing timeScale.
     */
    @RequestMapping(value = "/timeScales", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScale> updateTimeScale(@Valid @RequestBody TimeScale timeScale)
        throws URISyntaxException {
        log.debug("REST request to update TimeScale : {}", timeScale);
        if (timeScale.getId() == null) {
            return createTimeScale(timeScale);
        }
        TimeScale result = timeScaleRepository.save(timeScale);
        timeScaleSearchRepository.save(timeScale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeScale", timeScale.getId().toString())).body(result);
    }

    /**
     * GET /timeScales -> get all the timeScales.
     */
    @RequestMapping(value = "/timeScales", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TimeScale>> getAllTimeScales(Pageable pageable) throws URISyntaxException {
        Page<TimeScale> page = timeScaleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timeScales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/timeScaleApplications/mpoList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getMpoList(Pageable pageable, @PathVariable Boolean status)
        //public ResponseEntity<List<MpoApplication>> getMpoList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get pending MPO list");
        //Page<MpoApplication> page=null;
        List<Map<String,Object>> page=null;

        List<MpoApplication> mpoList = new ArrayList<MpoApplication>();

        String currentUserStatus = getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = null;
        User deo=null;
        String  userName= SecurityUtils.getCurrentUserLogin();
        InstGenInfo instGenInfo = null;

        if (currentUserStatus == null) {
            //*blunk list return*//*
            page = null;
        } else {
            if (status) {
                mpoStatus = MpoApplicationStatusType.currentRoleMpoApplicationStatusType(getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
            /*blunk list return*/
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        log.debug("-------------mpo status code----------------"+mpoStatus.getCode());
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            //page = mpoApplicationRepository.findPendingListByInstituteId(pageable, mpoStatus.getCode(),instGenInfo.getInstitute().getId());
                            //page = rptJdbcDao.findPendingListByInstituteId(instGenInfo.getInstitute().getId(),mpoStatus.getCode());
                            //page = rptJdbcDao.findApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScaleApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deo=(userRepository.findOneByLogin(userName)).get();
                        if(deo!=null && deo.getDistrict() !=null && deo.getDistrict().getId()>0){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            //page = rptJdbcDao.findApprovedListByDistrictId(deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScaleApprovedListByDistrictId(deo.getDistrict().getId(), mpoStatus.getCode());
                        }
                    }else{
                        //page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findTimeScaleApproveListByStatus(mpoStatus.getCode());
                    }

                    /*page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());*/

                }
            } else {
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
           /* blunk list return*/
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            // page = mpoApplicationRepository.findApprovedListByInstituteId(pageable, instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            //page = rptJdbcDao.findPendingListByInstituteId(instGenInfo.getInstitute().getId(),mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScalePendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deo=(userRepository.findOneByLogin(userName)).get();
                        if(deo!=null){
                            //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScalePendingListByDistrictId(deo.getDistrict().getId(), mpoStatus.getCode());
                        }
                    }else{
                        //page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findTimeScalePendingListByStatus(mpoStatus.getCode());
                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findTimeScaleApproveListByStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                // page = mpoApplicationRepository.findMpoPendingListForAdmin(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findTimeScalePendingListByStatus( MpoApplicationStatusType.APPROVEDBYDG.getCode());
            }

        }
        //return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        if(page !=null){
            //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplications/mpoList");
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }

    /**
     * GET /timeScales/:id -> get the "id" timeScale.
     */
    @RequestMapping(value = "/timeScales/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScale> getTimeScale(@PathVariable Long id) {
        log.debug("REST request to get TimeScale : {}", id);
        return Optional.ofNullable(timeScaleRepository.findOne(id))
            .map(timeScale -> new ResponseEntity<>(timeScale, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /timeScales/:id -> delete the "id" timeScale.
     */
    @RequestMapping(value = "/timeScales/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTimeScale(@PathVariable Long id) {
        log.debug("REST request to delete TimeScale : {}", id);
        timeScaleRepository.delete(id);
        timeScaleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("timeScale", id.toString())).build();
    }

    /**
     * SEARCH /_search/timeScales/:query -> search for the timeScale
     * corresponding to the query.
     */
    @RequestMapping(value = "/_search/timeScales/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TimeScale> searchTimeScales(@PathVariable String query) {
        return StreamSupport.stream(timeScaleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    public String getCurrentUserRoleForMpo() {
        if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
            return "ROLE_INSTITUTE";
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
            return "ROLE_DEO";
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_FRONTDESK")) {
            return "ROLE_FRONTDESK";
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_AD")) {
            return "ROLE_AD";
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR")) {
            return "ROLE_DIRECTOR";
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            return "ROLE_DG";
        } else {
            return null;
        }

    }
}
