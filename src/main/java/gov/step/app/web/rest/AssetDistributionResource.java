package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AssetDistribution;
import gov.step.app.domain.AssetRequisition;
import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.repository.AssetDistributionRepository;
import gov.step.app.repository.AssetRequisitionRepository;
import gov.step.app.repository.HrEmployeeInfoRepository;
import gov.step.app.repository.search.AssetDistributionSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AssetDistribution.
 */
@RestController
@RequestMapping("/api")
public class AssetDistributionResource {

    private final Logger log = LoggerFactory.getLogger(AssetDistributionResource.class);

    @Inject
    private AssetDistributionRepository assetDistributionRepository;

    @Inject
    private AssetDistributionSearchRepository assetDistributionSearchRepository;

    @Inject
    private AssetRequisitionRepository assetRequisitionRepository;

    /**
     * POST  /assetDistributions -> Create a new assetDistribution.
     */
    @RequestMapping(value = "/assetDistributions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetDistribution> createAssetDistribution(@Valid @RequestBody AssetDistribution assetDistribution) throws URISyntaxException {
        log.debug("REST request to save AssetDistribution : {}", assetDistribution);
        if (assetDistribution.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new assetDistribution cannot already have an ID").body(null);
        }
        System.out.println("\n Got here");

        assetDistribution.setCreateBy(SecurityUtils.getCurrentUserId());
        assetDistribution.setCreateDate(DateResource.getDateAsLocalDate());
        //TransactionIdResource transactionIdResource  = new TransactionIdResource();
        //assetDistribution.setTransferRef(transactionIdResource.getGeneratedid("Dist"));

        //HrEmployeeInfo hrEmployeeInfo  = new  HrEmployeeInfo();
        //hrEmployeeInfo.setId(assetDistribution.getEmploeeId());
        //assetDistribution.setHrEmployeeInfo(hrEmployeeInfo);
        //System.out.println("\n hrEmployeeInfo : "+assetDistribution.getEmploeeId());


        AssetDistribution result = assetDistributionRepository.save(assetDistribution);
        assetDistributionSearchRepository.save(result);
        System.out.println("\n Got here");
        if(result != null){
            AssetRequisition assetRequisition  = assetRequisitionRepository.findRequisitionByEmployeeId(assetDistribution.getRequisitonId());
            assetRequisition.setStatus(false);
            AssetRequisition reqResult = assetRequisitionRepository.save(assetRequisition);
            System.out.println("\n Got here");
        }
        return ResponseEntity.created(new URI("/api/assetDistributions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assetDistribution", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetDistributions -> Updates an existing assetDistribution.
     */
    @RequestMapping(value = "/assetDistributions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetDistribution> updateAssetDistribution(@Valid @RequestBody AssetDistribution assetDistribution) throws URISyntaxException {
        log.debug("REST request to update AssetDistribution : {}", assetDistribution);
        if (assetDistribution.getId() == null) {
            return createAssetDistribution(assetDistribution);
        }
        AssetDistribution result = assetDistributionRepository.save(assetDistribution);
        assetDistributionSearchRepository.save(assetDistribution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assetDistribution", assetDistribution.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetDistributions -> get all the assetDistributions.
     */
    @RequestMapping(value = "/assetDistributions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AssetDistribution>> getAllAssetDistributions(Pageable pageable)
        throws URISyntaxException {
        Page<AssetDistribution> page = assetDistributionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetDistributions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetDistributions/:id -> get the "id" assetDistribution.
     */
    @RequestMapping(value = "/assetDistributions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssetDistribution> getAssetDistribution(@PathVariable Long id) {
        log.debug("REST request to get AssetDistribution : {}", id);
        return Optional.ofNullable(assetDistributionRepository.findOne(id))
            .map(assetDistribution -> new ResponseEntity<>(
                assetDistribution,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assetDistributions/:id -> delete the "id" assetDistribution.
     */
    @RequestMapping(value = "/assetDistributions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssetDistribution(@PathVariable Long id) {
        log.debug("REST request to delete AssetDistribution : {}", id);
        assetDistributionRepository.delete(id);
        assetDistributionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assetDistribution", id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetDistributions/:query -> search for the assetDistribution corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/assetDistributions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AssetDistribution> searchAssetDistributions(@PathVariable String query) {
        return StreamSupport
            .stream(assetDistributionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    //////////////////////////////////////////////////////
//    @RequestMapping(value = "/assetDistributions/checkEmployeeDuplicate/",
//        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<Map> getFeePaymentTypeSetupsByName(@RequestParam String value) {
//
//        log.debug("REST request to get digiSignaturess by Employee Id : {}", value);
//
//        Map map = new HashMap();
//        map.put("value", value);
//
//        HrEmployeeInfo hrEmployeeInfos = hrEmployeeInfoRepository.findByEmployeeId(value);
//
//        HrEmployeeInfo hrEmployeeInfo = new HrEmployeeInfo();
//        //hrEmployeeInfo.setId(Long.parseLong(value));
//        hrEmployeeInfo.setId(hrEmployeeInfos.getId());
//
//        if(assetDistributionRepository.findByEmployeeId(hrEmployeeInfo) != null){
//            map.put("isValid", false);
//            return new ResponseEntity<Map>(map, \.OK);
//        }else{
//            map.put("isValid", true);
//            return new ResponseEntity<Map>(map, HttpStatus.OK);
//        }
//    }




}
