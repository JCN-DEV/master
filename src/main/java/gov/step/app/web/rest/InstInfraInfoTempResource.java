package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.repository.*;
import gov.step.app.repository.search.InstInfraInfoTempSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.dto.InstInfraDto;
import gov.step.app.web.rest.dto.InstInfraTempDto;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstInfraInfoTemp.
 */
@RestController
@RequestMapping("/api")
public class InstInfraInfoTempResource {

    private final Logger log = LoggerFactory.getLogger(InstInfraInfoTempResource.class);

    @Inject
    private InstInfraInfoTempRepository instInfraInfoTempRepository;

    @Inject
    private InstInfraInfoTempSearchRepository instInfraInfoTempSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstLabInfoTempRepository instLabInfoTempRepository;

    @Inject
    private InstShopInfoTempRepository instShopInfoTempRepository;

    @Inject
    private InstPlayGroundInfoTempRepository instPlayGroundInfoTempRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    /**
     * POST  /instInfraInfoTemps -> Create a new instInfraInfoTemp.
     */
    @RequestMapping(value = "/instInfraInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstInfraInfoTemp> createInstInfraInfoTemp(@RequestBody InstInfraInfoTemp instInfraInfoTemp) throws URISyntaxException {
        log.debug("REST request to save InstInfraInfoTemp : {}", instInfraInfoTemp);
        if (instInfraInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instInfraInfoTemp cannot already have an ID").body(null);
        }

        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setInfraInfo(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }


        instInfraInfoTemp.setInstitute(institute);
        /*instInfraInfo.setInstBuilding(instBuildingRepository.save(instInfraInfo.getInstBuilding()));
        instInfraInfo.setInstLand(instLandRepository.save(instInfraInfo.getInstLand()));*//*
        InstInfraInfo result = instInfraInfoRepository.save(instInfraInfo);
        instGenInfo.setStatus((instGenInfo.getStatus()+1));
        instGenInfoRepository.save(instGenInfo);*/

        InstInfraInfoTemp result = instInfraInfoTempRepository.save(instInfraInfoTemp);
        instInfraInfoTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instInfraInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instInfraInfoTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instInfraInfoTemps -> Updates an existing instInfraInfoTemp.
     */
    @RequestMapping(value = "/instInfraInfoTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstInfraInfoTemp> updateInstInfraInfoTemp(@RequestBody InstInfraInfoTemp instInfraInfoTemp) throws URISyntaxException {
        log.debug("REST request to update InstInfraInfoTemp : {}", instInfraInfoTemp);
        if (instInfraInfoTemp.getId() == null) {
            return createInstInfraInfoTemp(instInfraInfoTemp);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        instituteStatus.setInfraInfo(1);
        instituteStatus.setUpdatedDate(LocalDate.now());
        instituteStatusRepository.save(instituteStatus);

        InstInfraInfoTemp result = instInfraInfoTempRepository.save(instInfraInfoTemp);
        instInfraInfoTempSearchRepository.save(instInfraInfoTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instInfraInfoTemp", instInfraInfoTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instInfraInfoTemps -> get all the instInfraInfoTemps.
     */
    @RequestMapping(value = "/instInfraInfoTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstInfraInfoTemp>> getAllInstInfraInfoTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstInfraInfoTemp> page = instInfraInfoTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instInfraInfoTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instInfraInfoTemps/:id -> get the "id" instInfraInfoTemp.
     */
    @RequestMapping(value = "/instInfraInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstInfraInfoTemp> getInstInfraInfoTemp(@PathVariable Long id) {
        log.debug("REST request to get InstInfraInfoTemp : {}", id);
        return Optional.ofNullable(instInfraInfoTempRepository.findOne(id))
            .map(instInfraInfoTemp -> new ResponseEntity<>(
                instInfraInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instInfraInfoTemps/:id -> delete the "id" instInfraInfoTemp.
     */
    @RequestMapping(value = "/instInfraInfoTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstInfraInfoTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstInfraInfoTemp : {}", id);
        instInfraInfoTempRepository.delete(id);
        instInfraInfoTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instInfraInfoTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instInfraInfoTemps/:query -> search for the instInfraInfoTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instInfraInfoTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstInfraInfoTemp> searchInstInfraInfoTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instInfraInfoTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /instInfraInfos/:id -> get the "id" instInfraInfo.
     */
    @RequestMapping(value = "/instInfraInfoTemp/AllInfo/{institueid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstInfraTempDto> getInstInfraInfosAllInfo(@PathVariable Long institueid) {

        InstInfraTempDto instInfraDto = new InstInfraTempDto();
        instInfraDto.setInstInfraInfo(instInfraInfoTempRepository.findByInstituteId(institueid));
        instInfraDto.setInstLabInfoList(instLabInfoTempRepository.findListByInstituteId(institueid));
        instInfraDto.setInstShopInfoList(instShopInfoTempRepository.findListByInstituteId(institueid));
        instInfraDto.setInstPlayGroundInfoList(instPlayGroundInfoTempRepository.findListByInstituteId(institueid));

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
