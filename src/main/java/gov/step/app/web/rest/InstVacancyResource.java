package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmpTypes;
import gov.step.app.domain.enumeration.VacancyRoleType;
import gov.step.app.repository.*;
import gov.step.app.repository.search.InstVacancySearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstVacancy.
 */
@RestController
@RequestMapping("/api")
public class InstVacancyResource {

    private final Logger log = LoggerFactory.getLogger(InstVacancyResource.class);

    @Inject
    private InstVacancyRepository instVacancyRepository;

    @Inject
    private InstVacancySearchRepository instVacancySearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private MpoVacancyRoleRepository mpoVacancyRoleRepository;

    @Inject
    private IisCurriculumInfoRepository iisCurriculumInfoRepository;

    @Inject
    private MpoVacancyRoleDesgnationsRepository mpoVacancyRoleDesgnationsRepository;

    @Inject
    private IisCourseInfoRepository iisCourseInfoRepository;

    @Inject
    private MpoVacancyRoleTradeRepository mpoVacancyRoleTradeRepository;

    /**
     * POST  /instVacancys -> Create a new instVacancy.
     */
    @RequestMapping(value = "/instVacancys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancy> createInstVacancy(@RequestBody InstVacancy instVacancy) throws URISyntaxException {
        log.debug("REST request to save InstVacancy : {}", instVacancy);
        if (instVacancy.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instVacancy cannot already have an ID").body(null);
        }
       /* if(instVacancy.getEmpType().equals(EmpTypes.Staff) && instVacancyRepository.findOneByDesignation(instVacancy.getInstitute().getId(),instVacancy.getInstEmplDesignation().getId()) != null){
            return ResponseEntity.badRequest().header("Failure", "A new instVacancy cannot already have an ID").body(null);

        }else if(instVacancy.getEmpType().equals(EmpTypes.Teacher) && instVacancyRepository.findOneByInstituteAndTradeAndSubject(instVacancy.getInstitute().getId(),instVacancy.getCmsTrade().getId(),instVacancy.getCmsSubject().getId()) != null){
            return ResponseEntity.badRequest().header("Failure", "A new instVacancy cannot already have an ID").body(null);
        }else{*/
            instVacancy.setDateCreated(LocalDate.now());
            instVacancy.setDateModified(LocalDate.now());
            instVacancy.setStatus(true);
            instVacancy.setFilledUpVacancy(0);
            if(instVacancy.getInstitute() == null){
                instVacancy.setInstitute(instituteRepository.findOneByUserIsCurrentUser());
            }

            InstVacancy result = instVacancyRepository.save(instVacancy);
            instVacancySearchRepository.save(result);
            return ResponseEntity.created(new URI("/api/instVacancys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("instVacancy", result.getId().toString()))
                .body(result);
        //}

    }

    /**
     * POST  /instVacancys/generalRole/apply -> Create a new instVacancy.
     */
    @RequestMapping(value = "/instVacancys/generalRole/apply",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Institute> createInstVacancyWithGeneralRole(@RequestBody Institute institute) throws URISyntaxException {
        log.debug("REST request to save InstVacancy : {}", institute);
        List<MpoVacancyRole> vacancyRoles = new ArrayList<>();
        List<CmsCurriculum> instCurriculums = iisCurriculumInfoRepository.findAllCurriculumByInstituteId(institute.getId());
        for(CmsCurriculum each:instCurriculums){
           // System.out.println("inst curriculum id :"+each.getId());
            vacancyRoles.addAll(mpoVacancyRoleRepository.findGeneralRolesByCurriculum(each.getId(), VacancyRoleType.General));
        }
        for(MpoVacancyRole each: vacancyRoles){
           // System.out.println("mpo vacancy role id :"+each.getId());
           List<MpoVacancyRoleDesgnations> mvrd =  mpoVacancyRoleDesgnationsRepository.findByMpoVacancyRole(each.getId());
            for(MpoVacancyRoleDesgnations one: mvrd){
               // System.out.println("MpoVacancyRoleDesgnationsid :"+one.getId());
                InstVacancy instVacancy = new InstVacancy();
                instVacancy.setDesignationSetup(one.getDesignationSetup());
                instVacancy.setCmsSubject(one.getCmsSubject());
                instVacancy.setTotalVacancy(one.getTotalPost());
                instVacancy.setDateCreated(LocalDate.now());
                instVacancy.setStatus(true);
                instVacancy.setFilledUpVacancy(0);
                instVacancy.setInstitute(institute);
                InstVacancy result = instVacancyRepository.save(instVacancy);
                instVacancySearchRepository.save(result);
            }
        }

        institute.setVacancyAssigned(true);
        instituteRepository.save(institute);
        return ResponseEntity.ok(institute);

    }

    /**
     * PUT  /instVacancys -> Updates an existing instVacancy.
     */
    @RequestMapping(value = "/instVacancys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancy> updateInstVacancy(@RequestBody InstVacancy instVacancy) throws URISyntaxException {
        log.debug("REST request to update InstVacancy : {}", instVacancy);
        if (instVacancy.getId() == null) {
            return createInstVacancy(instVacancy);
        }
        InstVacancy result = instVacancyRepository.save(instVacancy);
        instVacancySearchRepository.save(instVacancy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instVacancy", instVacancy.getId().toString()))
            .body(result);
    }
/*
   *//**
     * PUT  /instVacancys -> Updates an existing instVacancy.
     *//*
    @RequestMapping(value = "/instVacancys/generalVacancy/institutes/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancy> addGeneralVacancy(@RequestBody InstVacancy instVacancy) throws URISyntaxException {
        log.debug("REST request to add InstVacancy : {}", instVacancy);
        if (instVacancy.getId() == null) {
            return createInstVacancy(instVacancy);
        }
        InstVacancy result = instVacancyRepository.save(instVacancy);
        instVacancySearchRepository.save(instVacancy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instVacancy", instVacancy.getId().toString()))
            .body(result);
    }*/

    /**
     * GET  /instVacancys -> get all the instVacancys.
     */
    @RequestMapping(value = "/instVacancys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstVacancy>> getAllInstVacancys(Pageable pageable)
        throws URISyntaxException {
        Page<InstVacancy> page = instVacancyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instVacancys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instVacancys/:id -> get the "id" instVacancy.
     */
    @RequestMapping(value = "/instVacancys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancy> getInstVacancy(@PathVariable Long id) {
        log.debug("REST request to get InstVacancy : {}", id);
        return Optional.ofNullable(instVacancyRepository.findOne(id))
            .map(instVacancy -> new ResponseEntity<>(
                instVacancy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  instVacancy by current institute, trade id and subject id
     */
    @RequestMapping(value = "/instVacancys/currentInstitute/{tradeId}/{subjectId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancy> getInstVacancyCurrentBytradeAndSubject(@PathVariable Long tradeId,@PathVariable Long subjectId) {
        log.debug("REST request to get InstVacancy : {}", tradeId);
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.debug(instituteRepository.findOneByUserIsCurrentUser().getId()+"<><><><>"+tradeId+"<><><>"+subjectId);
        return Optional.ofNullable(instVacancyRepository.findOneByInstituteAndTradeAndSubject(instituteRepository.findOneByUserIsCurrentUser().getId(),tradeId,subjectId))
            .map(instVacancy -> new ResponseEntity<>(
                instVacancy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  instVacancy by current institute, trade id and subject id
     */
    @RequestMapping(value = "/instVacancys/currentInstitute/subjects/{subjectId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancy> getInstVacancyCurrentBySubject(@PathVariable Long subjectId) {
        log.debug("REST request to get InstVacancy by subject: {}", subjectId);
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.debug(instituteRepository.findOneByUserIsCurrentUser().getId()+"<><><><>"+" subject "+"<><><>"+subjectId);
        return Optional.ofNullable(instVacancyRepository.findOneByInstituteAndSubject(instituteRepository.findOneByUserIsCurrentUser().getId(),subjectId))
            .map(instVacancy -> new ResponseEntity<>(
                instVacancy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  instVacancy by current institute, trade id and subject id
     */
    @RequestMapping(value = "/instVacancys/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstVacancy>> getInstVacancyCurrentInstitute() {
        log.debug("REST request to get InstVacancy : {}");
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return Optional.ofNullable(instVacancyRepository.findAllByInstitute(instituteRepository.findOneByUserIsCurrentUser().getId()))
            .map(instVacancys -> new ResponseEntity<>(
                instVacancys,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  instVacancy by current institute, trade id and subject id
     */
    @RequestMapping(value = "/instVacancys/institute/{instituteId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstVacancy>> getInstVacancyCurrentInstitute(@PathVariable Long instituteId) {
        log.debug("REST request to get InstVacancy : {}");
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return Optional.ofNullable(instVacancyRepository.findAllByInstitute(instituteId))
            .map(instVacancys -> new ResponseEntity<>(
                instVacancys,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  instVacancy by institute id, trade id and subject id
     */
    @RequestMapping(value = "/instVacancys/institute/trade/subject/{instId}/{tradeId}/{subjectId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancy> getInstVacancyByInstitueAndradeAndSubject(@PathVariable Long instId,@PathVariable Long tradeId,@PathVariable Long subjectId) {
        log.debug("REST request to get InstVacancy institute: {}", instId);
        return Optional.ofNullable(instVacancyRepository.findOneByInstituteAndTradeAndSubject(instId,tradeId,subjectId))
            .map(instVacancy -> new ResponseEntity<>(
                instVacancy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  instVacancy by institute id and designation id
     */
    @RequestMapping(value = "/instVacancys/institute/designation/{instId}/{desigId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancy> getInstVacancyByInstitueAndDesignation(@PathVariable Long instId,@PathVariable Long desigId) {
        log.debug("REST request to get InstVacancy institute: {}", instId);
        return Optional.ofNullable(instVacancyRepository.findOneByDesignation(instId,desigId))
            .map(instVacancy -> new ResponseEntity<>(
                instVacancy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  instVacancy by current institute and instEmplDesignation id
     */
    @RequestMapping(value = "/instVacancys/currentInstitute/designation/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancy> getInstVacancyCurrentByDesignation(@PathVariable Long id) {
        log.debug("REST request to get InstVacancy : {}", id);
        return Optional.ofNullable(instVacancyRepository.findOneByDesignation(instituteRepository.findOneByUserIsCurrentUser().getId(), id))
            .map(instVacancy -> new ResponseEntity<>(
                instVacancy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instVacancys/:id -> delete the "id" instVacancy.
     */
    @RequestMapping(value = "/instVacancys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstVacancy(@PathVariable Long id) {
        log.debug("REST request to delete InstVacancy : {}", id);
        instVacancyRepository.delete(id);
        instVacancySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instVacancy", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instVacancys/:query -> search for the instVacancy corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instVacancys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstVacancy> searchInstVacancys(@PathVariable String query) {
        return StreamSupport
            .stream(instVacancySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    // this method to apply special role of mpo vacancy

    @RequestMapping(value = "/instVacancys/specialRoles/iisCourse",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IisCourseInfo> applySpecialVacancyRole(@RequestBody IisCourseInfo iisCourseInfo) throws URISyntaxException {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.debug("comes to apply special role");

        if(iisCourseInfo != null && (iisCourseInfo.getVacancyRoleApplied() == null || !iisCourseInfo.getVacancyRoleApplied())){
            Institute institute = iisCourseInfo.getInstitute();

            //getting all trades of institute
            List<CmsTrade> cmsTrades = iisCourseInfoRepository.findAllTradesByInstitute(institute.getId());
//        List<MpoVacancyRoleTrade> mpoVacancyRoleTrades = mpoVacancyRoleTradeRepository.findByCmstrades(cmsTrades);

            //getting mpoVacancyRole matching with institute trades
            List<MpoVacancyRole> mpoVacancyRoles = mpoVacancyRoleRepository.findSpecialRolesByTrades(cmsTrades);
            for(MpoVacancyRole each: mpoVacancyRoles){
                // log.debug("id :"+each.getId());
            /*for(MpoVacancyRoleTrade trade :each.getMpoVacancyRoleTrades()){
                log.debug("printing vacTrade: "+trade.toString());
            }*/
                List<CmsTrade> mpoRoleTrades = mpoVacancyRoleTradeRepository.findTradesByMpoVacancyRole(each.getId());
                //checking all mpoRoleTrades of mpoVacancyRole contains in institute trades
                if(mpoRoleTrades.containsAll(cmsTrades)){
                    // log.debug("contains all elements");
                    List<MpoVacancyRoleDesgnations> mpoVacancyRoleDesgnationses = mpoVacancyRoleDesgnationsRepository.findByMpoVacancyRole(each.getId());

                    for(MpoVacancyRoleDesgnations one: mpoVacancyRoleDesgnationses){
                        System.out.println(">>>>>>>>>>>>>>> : comes to create new vacancy");
                        InstVacancy instVacancy = new InstVacancy();
                        instVacancy.setDesignationSetup(one.getDesignationSetup());
                        instVacancy.setCmsSubject(one.getCmsSubject());
                        instVacancy.setTotalVacancy(one.getTotalPost());
                        instVacancy.setDateCreated(LocalDate.now());
                        instVacancy.setStatus(true);
                        instVacancy.setFilledUpVacancy(0);
                        instVacancy.setInstitute(institute);
                        InstVacancy result = instVacancyRepository.save(instVacancy);
                        instVacancySearchRepository.save(result);
                    }
                }/*else {
                log.debug("all trades not found");
            }*/
            }
       /* log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.debug("cms trades size :"+cmsTrades.size());
        log.debug("special vacancy size :" + mpoVacancyRoles.size());*/
            iisCourseInfo.setVacancyRoleApplied(true);
            IisCourseInfo result = iisCourseInfoRepository.save(iisCourseInfo);
            return ResponseEntity.created(new URI("/instVacancys/specialRoles/iisCourse" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("iisCourseInfo", result.getId().toString()))
                .body(result);
        }
        return ResponseEntity.created(new URI("/instVacancys/specialRoles/iisCourse" + iisCourseInfo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("iisCourseInfo", iisCourseInfo.getId().toString()))
            .body(iisCourseInfo);

    }
}
