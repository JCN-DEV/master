package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.Direction;
import gov.step.app.repository.*;
import gov.step.app.repository.search.InstInfraInfoSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.dto.InstInfraDto;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstInfraInfo.
 */
@RestController
@RequestMapping("/api")
public class InstInfraInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstInfraInfoResource.class);

    @Inject
    private InstInfraInfoRepository instInfraInfoRepository;

    @Inject
    private InstInfraInfoSearchRepository instInfraInfoSearchRepository;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private InstBuildingRepository instBuildingRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstLandRepository instLandRepository;

    @Inject
    private InstLabInfoRepository instLabInfoRepository;

    @Inject
    private InstShopInfoRepository instShopInfoRepository;

    @Inject
    private InstPlayGroundInfoRepository instPlayGroundInfoRepository;

    @Inject
    private InstituteLabInfoRepository instituteLabInfoRepository;

    @Inject
    private InstituteShopInfoRepository instituteShopInfoRepository;

    @Inject
    private InstitutePlayGroundInfoRepository institutePlayGroundInfoRepository;

    @Inject
    private InstituteInfraInfoRepository instituteInfraInfoRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;


    /**
     * POST  /instInfraInfos -> Create a new instInfraInfo.
     */
    @RequestMapping(value = "/instInfraInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstInfraInfo> createInstInfraInfo(@RequestBody InstInfraInfo instInfraInfo) throws URISyntaxException {
        log.debug("REST request to save InstInfraInfo : {}", instInfraInfo);
        if (instInfraInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instInfraInfo cannot already have an ID").body(null);
        }
        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        instInfraInfo.setInstitute(instGenInfo.getInstitute());
        instInfraInfo.setInstBuilding(instBuildingRepository.save(instInfraInfo.getInstBuilding()));
        instInfraInfo.setInstLand(instLandRepository.save(instInfraInfo.getInstLand()));
        InstInfraInfo result = instInfraInfoRepository.save(instInfraInfo);
        instGenInfo.setStatus((instGenInfo.getStatus()+1));
        instGenInfoRepository.save(instGenInfo);
        instInfraInfoSearchRepository.save(result);


        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setInfraInfo(1);
            instituteStatusRepository.save(instituteStatus);
        }

        return ResponseEntity.created(new URI("/api/instInfraInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instInfraInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instInfraInfos -> Updates an existing instInfraInfo.
     */
    @RequestMapping(value = "/instInfraInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstInfraInfo> updateInstInfraInfo(@RequestBody InstInfraInfo instInfraInfo) throws URISyntaxException {
        log.debug("REST request to update InstInfraInfo : {}", instInfraInfo);
        if (instInfraInfo.getId() == null) {
            return createInstInfraInfo(instInfraInfo);
        }
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(SecurityUtils.getCurrentUserLogin());
        InstInfraInfo tmpInstInfraInfo=instInfraInfoRepository.findOne(instInfraInfo.getId());
        Institute institute=null;
        InstInfraInfo result=null;
        InstituteInfraInfo instituteInfraInfo=null;
        List<InstLabInfo> instLabInfoList=null;
        List<InstPlayGroundInfo> instPlayGroundInfoList=null;
        List<InstShopInfo> instShopInfoList=null;
        if(instGenInfo!=null){

            if(tmpInstInfraInfo.getStatus()!=null && tmpInstInfraInfo.getStatus()==1){
                log.debug(" changing institute gen info status------------------");
                instGenInfo.setStatus((instGenInfo.getStatus()+1));
                instGenInfoRepository.save(instGenInfo);
                instInfraInfo.setStatus(0);
            }

            result = instInfraInfoRepository.save(instInfraInfo);
        }else{

            instGenInfo=instGenInfoRepository.findByInstituteId(instInfraInfo.getInstitute().getId());
            instituteInfraInfo=instituteInfraInfoRepository.findOneByInstituteId(instInfraInfo.getInstitute().getId());
            if(instInfraInfo.getStatus()==1){
                result=instInfraInfoRepository.findOne(instInfraInfo.getId());
                if(result.getStatus()==null || result.getStatus()!=1){
                    /*result=null;*/

                    if(tmpInstInfraInfo.getStatus() !=null && tmpInstInfraInfo.getStatus() !=1){
                        instGenInfo.setStatus((instGenInfo.getStatus()-1));
                        instGenInfoRepository.save(instGenInfo);
                    }


                    institute=instituteRepository.findOne(result.getInstitute().getId());
                }
                result = instInfraInfoRepository.save(instInfraInfo);
                instLabInfoList=instLabInfoRepository.findListByInstituteId(instInfraInfo.getInstitute().getId());
                if(instLabInfoList !=null && instLabInfoList.size()>0){
                    InstituteLabInfo instituteLabInfo=null;
                    for(InstLabInfo instLabInfoListparm:instLabInfoList){
                        instituteLabInfo=null;
                        if(instLabInfoListparm.getInstituteLabInfo() !=null
                            && instLabInfoListparm.getInstituteLabInfo().getId()>0 ){
                            instituteLabInfo=instituteLabInfoRepository.findOne(instLabInfoListparm.getInstituteLabInfo().getId());
                            instituteLabInfo=getCopyOfInstLabInfo(instituteLabInfo, instLabInfoListparm);
                            instituteLabInfo.setInstituteInfraInfo(instituteInfraInfo);
                            instituteLabInfoRepository.save(instituteLabInfo);
                        }else{
                            instituteLabInfo=getNewCopyOfInstLabInfo(instLabInfoListparm);
                            instituteLabInfo.setInstituteInfraInfo(instituteInfraInfo);
                            instituteLabInfo=instituteLabInfoRepository.save(instituteLabInfo);
                            instLabInfoListparm.setInstituteLabInfo(instituteLabInfo);
                            instLabInfoRepository.save(instLabInfoListparm);
                        }
                    }
                }
                instShopInfoList=instShopInfoRepository.findListByInstituteId(instInfraInfo.getInstitute().getId());
                if(instShopInfoList !=null && instShopInfoList.size()>0){
                    InstituteShopInfo instituteShopInfo=null;
                    for(InstShopInfo instShopInfoListparm:instShopInfoList){
                        instituteShopInfo=null;
                        if(instShopInfoListparm.getInstituteShopInfo() !=null
                            && instShopInfoListparm.getInstituteShopInfo().getId()>0 ){
                            instituteShopInfo=instituteShopInfoRepository.findOne(instShopInfoListparm.getInstituteShopInfo().getId());
                            instituteShopInfo=getCopyOfInstShopInfo(instituteShopInfo, instShopInfoListparm);
                            instituteShopInfo.setInstituteInfraInfo(instituteInfraInfo);
                            instituteShopInfoRepository.save(instituteShopInfo);
                        }else{
                            instituteShopInfo=getNewCopyOfInstShopInfo(instShopInfoListparm);
                            instituteShopInfo.setInstituteInfraInfo(instituteInfraInfo);
                            instituteShopInfo=instituteShopInfoRepository.save(instituteShopInfo);
                            instShopInfoListparm.setInstituteShopInfo(instituteShopInfo);
                            instShopInfoRepository.save(instShopInfoListparm);

                        }
                    }
                }
                instPlayGroundInfoList=instPlayGroundInfoRepository.findListByInstituteId(instInfraInfo.getInstitute().getId());
                if(instPlayGroundInfoList !=null && instPlayGroundInfoList.size()>0){
                    InstitutePlayGroundInfo institutePlayGroundInfo=null;
                    for(InstPlayGroundInfo instPlayGroundInfoParm:instPlayGroundInfoList){
                        institutePlayGroundInfo=null;
                        if(instPlayGroundInfoParm.getInstitutePlayGroundInfo() !=null
                            && instPlayGroundInfoParm.getInstitutePlayGroundInfo().getId()>0 ){
                            institutePlayGroundInfo=institutePlayGroundInfoRepository.findOne(instPlayGroundInfoParm.getInstitutePlayGroundInfo().getId());
                            institutePlayGroundInfo=getCopyOfInstPlayGroundInfo(institutePlayGroundInfo, instPlayGroundInfoParm);
                            institutePlayGroundInfo.setInstituteInfraInfo(instituteInfraInfo);
                            institutePlayGroundInfoRepository.save(institutePlayGroundInfo);
                        }else{
                            institutePlayGroundInfo=getNewCopyOfInstPlayGroundInfo(instPlayGroundInfoParm);
                            institutePlayGroundInfo.setInstituteInfraInfo(instituteInfraInfo);
                            institutePlayGroundInfo=institutePlayGroundInfoRepository.save(institutePlayGroundInfo);
                            instPlayGroundInfoParm.setInstitutePlayGroundInfo(institutePlayGroundInfo);
                            instPlayGroundInfoRepository.save(instPlayGroundInfoParm);
                        }
                    }
                }
            }
            if(instInfraInfo.getStatus()==2){
                instGenInfo.setStatus((instGenInfo.getStatus()-1));
                instGenInfoRepository.save(instGenInfo);
                instInfraInfo.setStatus(1);
            }
            if(instInfraInfo.getStatus()==3){
                instInfraInfo.setStatus(2);
            }
        }
       /* instInfraInfo.setInstBuilding(instBuildingRepository.save(instInfraInfo.getInstBuilding()));
        instInfraInfo.setInstLand(instLandRepository.save(instInfraInfo.getInstLand()));*/

        instInfraInfoSearchRepository.save(instInfraInfo);

        if(SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")){
            InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
            if(instituteStatus != null){
                instituteStatus.setInfraInfo(1);
                instituteStatusRepository.save(instituteStatus);
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instInfraInfo", instInfraInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instInfraInfos -> get all the instInfraInfos.
     */
    @RequestMapping(value = "/instInfraInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstInfraInfo>> getAllInstInfraInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstInfraInfo> page = instInfraInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instInfraInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instInfraInfos/:id -> get the "id" instInfraInfo.
     */
    @RequestMapping(value = "/instInfraInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstInfraInfo> getInstInfraInfo(@PathVariable Long id) {
        log.debug("REST request to get InstInfraInfo : {}", id);
        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)){
            //List<InstInfraInfo> instInfraInfoList=instInfraInfoRepository.findByInstituteId(instGenInfo.getInstitute().getId());
           // log.debug("------list" + instInfraInfoList.size());
            /*return Optional.ofNullable((InstInfraInfo)instInfraInfoList.get(0))
                .map(instInfraInfo -> new ResponseEntity<>(
                    instInfraInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
            return Optional.ofNullable((InstInfraInfo)instInfraInfoRepository.findOneByInstitute(instGenInfo.getInstitute()))
                .map(instInfraInfo -> new ResponseEntity<>(
                    instInfraInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }else {
            return Optional.ofNullable(instInfraInfoRepository.findOne(id))
                .map(instInfraInfo -> new ResponseEntity<>(
                    instInfraInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

    }

    /**
     * DELETE  /instInfraInfos/:id -> delete the "id" instInfraInfo.
     */
    @RequestMapping(value = "/instInfraInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstInfraInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstInfraInfo : {}", id);
        instInfraInfoRepository.delete(id);
        instInfraInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instInfraInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instInfraInfos/:query -> search for the instInfraInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instInfraInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstInfraInfo> searchInstInfraInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instInfraInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    /**
     * GET  /instInfraInfos/:id -> get the "id" instInfraInfo.
     */
    @RequestMapping(value = "/instInfraInfoByInstituteId/{instituteId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstInfraInfo> getInstInfraInfoByInstituteId(@PathVariable Long instituteId) {
        log.debug("REST request to get InstInfraInfo : {}", instituteId);
        Institute institute=instituteRepository.findOne(instituteId);
        return Optional.ofNullable((InstInfraInfo)instInfraInfoRepository.findOneByInstitute(institute))
            .map(instInfraInfo -> new ResponseEntity<>(
                instInfraInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/instInfraInfos/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstInfraInfo> declineMpoApplication(@PathVariable Long id,@RequestBody String cause)
        throws URISyntaxException {
        InstInfraInfo instInfraInfo=null;
        if(id>0){
            instInfraInfo=instInfraInfoRepository.findOne(id);
        }
        log.debug("REST request to update instAdmInfo : {}--------", instInfraInfo);
        log.debug("REST request to update cause : {}--------", cause);
        if (instInfraInfo == null) {
            return ResponseEntity.badRequest().header("Failure", "A new inst AdmInfo cannot decline ").body(null);
        }else{
            instInfraInfo.setDeclineRemarks(cause);
            instInfraInfo.setStatus(2);
            InstInfraInfo result= instInfraInfoRepository.save(instInfraInfo);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("result", result.getId().toString()))
                .body(null);
        }
    }
    /**
     * GET  / Building Direction List
     */
    @RequestMapping(value = "/instInfraBuildingDirections",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Direction>> getDirectionList()
        throws URISyntaxException {

        return new ResponseEntity<List<Direction>> (Direction.getDirectionList(),HttpStatus.OK);
    }
    /**
     * GET  /instInfraInfos/:id -> get the "id" instInfraInfo.
     */
    @RequestMapping(value = "/instInfraInfosAllInfo/{institueid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstInfraDto> getInstInfraInfosAllInfo(@PathVariable Long institueid) {

        InstInfraDto instInfraDto = new InstInfraDto();

        instInfraDto.setInstInfraInfo(instInfraInfoRepository.findByInstituteId(institueid));
        instInfraDto.setInstLabInfoList(instLabInfoRepository.findListByInstituteId(institueid));
        instInfraDto.setInstShopInfoList(instShopInfoRepository.findListByInstituteId(institueid));
        instInfraDto.setInstPlayGroundInfoList(instPlayGroundInfoRepository.findListByInstituteId(institueid));

        return new ResponseEntity<>(instInfraDto, HttpStatus.OK);
    }

    private InstituteShopInfo getNewCopyOfInstShopInfo(InstShopInfo instShopInfoparm){
        InstituteShopInfo instituteShopInfo=new InstituteShopInfo();
        instituteShopInfo.setNameOrNumber(instShopInfoparm.getBuildingNameOrNumber());
        instituteShopInfo.setBuildingNameOrNumber(instShopInfoparm.getBuildingNameOrNumber());
        instituteShopInfo.setLength(instShopInfoparm.getLength());
        instituteShopInfo.setWidth(instShopInfoparm.getWidth());
        return instituteShopInfo;
    }
    private InstituteLabInfo getNewCopyOfInstLabInfo(InstLabInfo instLabInfoparm){
        InstituteLabInfo instituteLabInfo=new InstituteLabInfo();
        instituteLabInfo.setNameOrNumber(instLabInfoparm.getBuildingNameOrNumber());
        instituteLabInfo.setBuildingNameOrNumber(instLabInfoparm.getBuildingNameOrNumber());
        instituteLabInfo.setLength(instLabInfoparm.getLength());
        instituteLabInfo.setWidth(instLabInfoparm.getWidth());
        instituteLabInfo.setTotalBooks(instLabInfoparm.getTotalBooks());
        return instituteLabInfo;
    }
    private InstitutePlayGroundInfo getNewCopyOfInstPlayGroundInfo(InstPlayGroundInfo instPlayGroundInfoparm){
        InstitutePlayGroundInfo institutePlayGroundInfo= new InstitutePlayGroundInfo();
        institutePlayGroundInfo.setPlaygroundNo(instPlayGroundInfoparm.getPlaygroundNo());
        institutePlayGroundInfo.setArea(instPlayGroundInfoparm.getArea());
        return institutePlayGroundInfo;
    }

    private InstituteShopInfo getCopyOfInstShopInfo(InstituteShopInfo instituteShopInfo,InstShopInfo instShopInfoparm){
        instituteShopInfo.setNameOrNumber(instShopInfoparm.getBuildingNameOrNumber());
        instituteShopInfo.setBuildingNameOrNumber(instShopInfoparm.getBuildingNameOrNumber());
        instituteShopInfo.setLength(instShopInfoparm.getLength());
        instituteShopInfo.setWidth(instShopInfoparm.getWidth());
        return instituteShopInfo;
    }
    private InstituteLabInfo getCopyOfInstLabInfo(InstituteLabInfo instituteLabInfo,InstLabInfo instLabInfoparm){
        instituteLabInfo.setNameOrNumber(instLabInfoparm.getBuildingNameOrNumber());
        instituteLabInfo.setBuildingNameOrNumber(instLabInfoparm.getBuildingNameOrNumber());
        instituteLabInfo.setLength(instLabInfoparm.getLength());
        instituteLabInfo.setWidth(instLabInfoparm.getWidth());
        instituteLabInfo.setTotalBooks(instLabInfoparm.getTotalBooks());
        return instituteLabInfo;
    }
    private InstitutePlayGroundInfo getCopyOfInstPlayGroundInfo(InstitutePlayGroundInfo institutePlayGroundInfo
                                                                ,InstPlayGroundInfo instPlayGroundInfoparm){
        institutePlayGroundInfo.setPlaygroundNo(instPlayGroundInfoparm.getPlaygroundNo());
        institutePlayGroundInfo.setArea(instPlayGroundInfoparm.getArea());
        return institutePlayGroundInfo;
    }
}
