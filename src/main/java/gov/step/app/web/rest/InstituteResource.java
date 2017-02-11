package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Cat;
import gov.step.app.domain.Institute;
import gov.step.app.domain.InstituteStatus;
import gov.step.app.domain.enumeration.InstituteType;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.InstituteStatusRepository;
import gov.step.app.repository.search.InstituteSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.InstituteService;
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
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Institute.
 */
@RestController
@RequestMapping("/api")
public class InstituteResource {

    private final Logger log = LoggerFactory.getLogger(InstituteResource.class);

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstituteSearchRepository instituteSearchRepository;

    @Inject
    private InstituteService instituteService;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    /**
     * POST  /institutes -> Create a new institute.
     */
    @RequestMapping(value = "/institutes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Institute> createInstitute(@Valid @RequestBody Institute institute) throws URISyntaxException {
        log.debug("REST request to save Institute : {}", institute);
        if (institute.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new institute cannot already have an ID").body(null);
        }
        institute.setInfoEditStatus("Pending");
        Institute result = instituteRepository.save(institute);
        if (result != null) {
            InstituteStatus resultInstituteStatus = null;
            InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
            if(instituteStatus != null){
                instituteStatus = new InstituteStatus();
            }
//
//            instituteStatus.setGenInfo(1);
            instituteStatus.setInstitute(institute);
            instituteStatus.setAcademicInfo(0);
            instituteStatus.setAcaInfo(0);
            instituteStatus.setAdmInfo(0);
            instituteStatus.setBuilding(0);
            instituteStatus.setCmtMemInfo(0);
            instituteStatus.setComiteeInfo(0);
            instituteStatus.setComiteeMember(0);
            instituteStatus.setInfraInfo(0);
            instituteStatus.setFinancialInfo(0);
            instituteStatus.setShopInfo(0);
            instituteStatus.setGovBodyAccess(0);
            instituteStatus.setGovernBody(0);
            instituteStatus.setLabInfo(0);
            instituteStatus.setLand(0);
            instituteStatus.setPlayGroundInfo(0);
            instituteStatus.setCreateBy(SecurityUtils.getCurrentUserId());
            instituteStatus.setCreateDate(LocalDate.now());
            resultInstituteStatus = instituteStatusRepository.save(instituteStatus);
        }
        instituteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/institutes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("institute", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /institutes -> Updates an existing institute.
     */
    @RequestMapping(value = "/institutes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Institute> updateInstitute(@Valid @RequestBody Institute institute) throws URISyntaxException {
        log.debug("REST request to update Institute : {}", institute);
        if (institute.getId() == null) {
            return createInstitute(institute);
        }
        institute.setLastModifiedDate(ZonedDateTime.now());
        Institute result = instituteRepository.save(institute);
        instituteSearchRepository.save(institute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("institute", institute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /institutes -> get all the institutes.
     */
    @RequestMapping(value = "/institutes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Institute>> getAllInstitutes(Pageable pageable)
        throws URISyntaxException {

        Page<Institute> page = instituteRepository.findAll(pageable);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER))
            page = instituteRepository.findByUserIsCurrentUser(pageable);

        log.debug("the role of current is INSTITUTE " + SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE));
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE))
            page = instituteRepository.findByUserIsCurrentUser(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/institutes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /institutes/my -> get my institute
     */
    @RequestMapping(value = "/institute/my", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Institute> getMyInstitute() {
        log.debug("REST request to get current users Institute");
        return Optional.ofNullable(instituteService.getInstituteWithCourses())
            .map(institute -> new ResponseEntity<>(institute, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /institute/current -> get my current institute.
     */
    @RequestMapping(value = "/institute/current",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Institute getMyCurrentInstitute() {
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        return institute;
        /*return Optional.ofNullable(instituteRepository.findOneByUserIsCurrentUser())
            .map(institute -> new ResponseEntity<>(
                institute,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
    }


    /**
     * GET  /institutes/:id -> get the "id" institute.
     */
    @RequestMapping(value = "/institutes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Institute> getInstitute(@PathVariable Long id) {
        log.debug("REST request to get Institute : {}", id);
        return Optional.ofNullable(instituteRepository.findOneWithEagerRelationships(id))
            .map(institute -> new ResponseEntity<>(
                institute,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /institutes/:id -> get the "id" institute.
     */
    @RequestMapping(value = "/institutes/upazila/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Institute> getInstitutesByUpazila(@PathVariable Long id) {
        log.debug("REST request to get Institute : {}", id);

        return instituteRepository.findInstitutesByUpazilla(id);
    }

    /**
     * GET  /institutesByCat/:id -> get the "cat" institute.
     */
    @RequestMapping(value = "/institutesByCat/{cat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Institute> getInstitutesByCategory(@PathVariable Long cat) {
        log.debug("REST request to get Institute by category: {}", cat);

        return instituteRepository.findInstitutesByCategory(cat);
    }

    /**
     * GET  /institutesByCat/:id -> get the "cat" institute.
     */
    @RequestMapping(value = "/institute/mpoEnlistedinstitutes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Institute> getAllMpoEnlistedInstitute() {
        log.debug("REST request to get Institutes mpo enlisted: {}");

        return instituteRepository.findInstitutesByMpoEnlisting(true);
    }

    /**
     * GET  /institutesByCat/:id -> get the "cat" institute.
     */
    @RequestMapping(value = "/institute/instLevel/name/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Institute> getAllMpoEnlistedInstitute(@PathVariable String name) {
        log.debug("REST request to get Institutes mpo enlisted: {}");

        return instituteRepository.findInstitutesByInstLevel(name);
    }
/**
     * GET  /status/:id -> get the "cat" institute.
     */
    @RequestMapping(value = "/institute/instituteStatus/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public InstituteStatus getInstituteStatusByInstitute(@PathVariable Long id) {
        log.debug("REST request to get InstituteStatus institute id: {}"+id);

        return instituteStatusRepository.findOneByInstitute(id);
    }

    /**
     * GET  /institutesByCat/:id -> get the "cat" institute.
     */
    @RequestMapping(value = "/institutes/byType/government",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Institute> getInstitutesByInstType() {
        log.debug("REST request to get Institute by type : {}");

        return instituteRepository.findInstitutesByType(InstituteType.Government);
    }

    /**
     * DELETE  /institutes/:id -> delete the "id" institute.
     */
    @RequestMapping(value = "/institutes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstitute(@PathVariable Long id) {
        log.debug("REST request to delete Institute : {}", id);
        instituteRepository.delete(id);
        instituteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("institute", id.toString())).build();
    }

    /**
     * SEARCH  /_search/institutes/:query -> search for the institute corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/institutes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Institute> searchInstitutes(@PathVariable String query) {
        return StreamSupport
            .stream(instituteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @RequestMapping(value = "/institutes/checkInstituteByName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkInstituteByName(@RequestParam String value) {

        Institute institute = instituteRepository.findOneByName(value.toLowerCase());

        Map map = new HashMap();

        map.put("value", value);

        if (institute == null) {
            map.put("isValid", true);
            return new ResponseEntity<Map>(map, HttpStatus.OK);

        } else {
            map.put("isValid", false);
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        }

    }


}
