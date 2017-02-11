package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.config.JHipsterProperties;
import gov.step.app.domain.AssetRequisition;
import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.domain.User;
import gov.step.app.repository.AssetRequisitionRepository;
import gov.step.app.repository.HrEmployeeInfoRepository;
import gov.step.app.repository.search.AssetRequisitionSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import gov.step.app.web.rest.util.TransactionIdResource;
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
 * REST controller for managing AssetRequisition.
 */
@RestController
@RequestMapping("/api")
public class AssetRequisitionResource {

    private final Logger log = LoggerFactory.getLogger(AssetRequisitionResource.class);

    @Inject
    private AssetRequisitionRepository assetRequisitionRepository;

    @Inject
    private AssetRequisitionSearchRepository assetRequisitionSearchRepository;

    @Inject
    private HrEmployeeInfoRepository hrEmployeeInfoRepository;

    /**
     * POST  /assetRequisitions -> Create a new assetRequisition.
     */
    @RequestMapping(value = "/assetRequisitions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRequisition> createAssetRequisition(@Valid @RequestBody AssetRequisition assetRequisition) throws URISyntaxException {
        log.debug("REST request to save AssetRequisition : {}", assetRequisition);
        if (assetRequisition.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetRequisition cannot already have an ID").body(null);
        }
        TransactionIdResource transactionIdResource = new TransactionIdResource();
        assetRequisition.setRequisitionId(transactionIdResource.getGeneratedid("REQ#"));
        assetRequisition.setRequisitionDate(DateResource.getDateAsLocalDate());
        assetRequisition.setCreateBy(SecurityUtils.getCurrentUserId());
        assetRequisition.setCreateDate(DateResource.getDateAsLocalDate());
        assetRequisition.setUpdateDate(DateResource.getDateAsLocalDate());

        User user = new User();
        user.setId(SecurityUtils.getCurrentUserId());
        HrEmployeeInfo hrEmployeeInfo = new HrEmployeeInfo();
        hrEmployeeInfo = hrEmployeeInfoRepository.findEmployeeDetailsById(user);

        assetRequisition.setEmpId(Long.parseLong(hrEmployeeInfo.getEmployeeId()));
        assetRequisition.setEmpName(hrEmployeeInfo.getFullName());
        if(hrEmployeeInfo.getDepartmentInfo().getDepartmentInfo() != null){
            if(hrEmployeeInfo.getDepartmentInfo().getDepartmentInfo().getDepartmentName() != null){
                assetRequisition.setDepartment("Not Found");
            } else {
                assetRequisition.setDepartment("Not Found");
            }
        } else {
            assetRequisition.setDepartment("Not Found");
        }

        if(hrEmployeeInfo.getDesignationInfo().getDesignationInfo() != null){
            if(hrEmployeeInfo.getDesignationInfo().getDesignationInfo().getDesignationName() != null){
                assetRequisition.setDesignation("Not Found");
            } else {
                assetRequisition.setDesignation("Not Found");
            }
        } else {
            assetRequisition.setDesignation("Not Found");
        }

        System.out.println("\n teste >>>>>>>>>>>>>>>>>>>> ");
        AssetRequisition result = assetRequisitionRepository.save(assetRequisition);
        assetRequisitionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetRequisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetRequisition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetRequisitions -> Updates an existing assetRequisition.
     */
    @RequestMapping(value = "/assetRequisitions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRequisition> updateAssetRequisition(@Valid @RequestBody AssetRequisition assetRequisition) throws URISyntaxException {
        log.debug("REST request to update AssetRequisition : {}", assetRequisition);
        if (assetRequisition.getId() == null) {
            return createAssetRequisition(assetRequisition);
        }
        AssetRequisition result = assetRequisitionRepository.save(assetRequisition);
        assetRequisitionSearchRepository.save(assetRequisition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetRequisition", assetRequisition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetRequisitions -> get all the assetRequisitions.
     */
    @RequestMapping(value = "/assetRequisitions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetRequisition>> getAllAssetRequisitions(Pageable pageable)
        throws URISyntaxException {
        Page<AssetRequisition> page = assetRequisitionRepository.findAll(pageable);
        if(SecurityUtils.isCurrentUserInRole("ROLE_USER") && !SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")){
            page = assetRequisitionRepository.findRequisitionByUserId(pageable, SecurityUtils.getCurrentUser().getId());
        } else if(SecurityUtils.isCurrentUserInRole("ROLE_USER") && SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")){
            page = assetRequisitionRepository.findRequisitionByStatus(pageable, true);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetRequisitions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetRequisitions/:id -> get the "id" assetRequisition.
     */
    @RequestMapping(value = "/assetRequisitions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRequisition> getAssetRequisition(@PathVariable Long id) {
        log.debug("REST request to get AssetRequisition : {}", id);
        return Optional.ofNullable(assetRequisitionRepository.findOne(id))
            .map(assetRequisition -> new ResponseEntity<>(
                assetRequisition,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /assetRequisitionsByRefId/:id -> get the "id" assetRequisitionsByRefId.
     */
    @RequestMapping(value = "/assetRequisitionsByRefId/{refId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetRequisition> getAssetRequisitionByRefId(@PathVariable Long refId) {
        log.debug("REST request to get AssetRequisition : {}", refId);
        return Optional.ofNullable(assetRequisitionRepository.findRequisitionByEmployeeId(refId))
            .map(assetRequisition -> new ResponseEntity<>(
                assetRequisition,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetRequisitions/:id -> delete the "id" assetRequisition.
     */
    @RequestMapping(value = "/assetRequisitions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetRequisition(@PathVariable Long id) {
        log.debug("REST request to delete AssetRequisition : {}", id);
        assetRequisitionRepository.delete(id);
        assetRequisitionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetRequisition", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetRequisitions/:query -> search for the assetRequisition corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetRequisitions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetRequisition> searchAssetRequisitions(@PathVariable String query) {
        return StreamSupport
            .stream(assetRequisitionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
