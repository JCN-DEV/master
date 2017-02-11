package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.BankBranch;
import gov.step.app.domain.BankSetup;
import gov.step.app.repository.BankBranchRepository;
import gov.step.app.repository.BankSetupRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.BankBranchSearchRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BankBranch.
 */
@RestController
@RequestMapping("/api")
public class BankBranchResource {

    private final Logger log = LoggerFactory.getLogger(BankBranchResource.class);

    @Inject
    private BankBranchRepository bankBranchRepository;

    @Inject
    private BankBranchSearchRepository bankBranchSearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private BankSetupRepository bankSetupRepository;

    /**
     * POST  /bankBranchs -> Create a new bankBranch.
     */
    @RequestMapping(value = "/bankBranchs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankBranch> createBankBranch(@Valid @RequestBody BankBranch bankBranch) throws URISyntaxException {
        log.debug("REST request to save BankBranch : {}", bankBranch);
        if (bankBranch.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bankBranch cannot already have an ID").body(null);
        }
        bankBranch.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        bankBranch.setCreateDate(LocalDate.now());
        BankBranch result = bankBranchRepository.save(bankBranch);
        bankBranchSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bankBranchs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bankBranch", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bankBranchs -> Updates an existing bankBranch.
     */
    @RequestMapping(value = "/bankBranchs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankBranch> updateBankBranch(@Valid @RequestBody BankBranch bankBranch) throws URISyntaxException {
        log.debug("REST request to update BankBranch : {}", bankBranch);
        if (bankBranch.getId() == null) {
            return createBankBranch(bankBranch);
        }
        bankBranch.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        bankBranch.setUpdateDate(LocalDate.now());
        BankBranch result = bankBranchRepository.save(bankBranch);
        bankBranchSearchRepository.save(bankBranch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bankBranch", bankBranch.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bankBranchs -> get all the bankBranchs.
     */
    @RequestMapping(value = "/bankBranchs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BankBranch>> getAllBankBranchs(Pageable pageable)
        throws URISyntaxException {
        Page<BankBranch> page = bankBranchRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bankBranchs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    //api to find all bankBranches of a bank of a district by bankSetup
 /**
     * GET  /bankBranchs/district/banksetup/id -> get all the bankBranchs by upazila
     */
    @RequestMapping(value = "/bankBranchs/district/{districtId}/bankSetup/{bankSetupId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BankBranch>> getAllBankBranchsByDistrictId(Pageable pageable, @PathVariable Long districtId, @PathVariable Long bankSetupId)
        throws URISyntaxException {
        BankSetup bankSetup = bankSetupRepository.findOne(bankSetupId);
        Page<BankBranch> page = bankBranchRepository.findBankBranchByDistrictAndBankOrderByName(pageable,districtId, bankSetupId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bankBranchs/district/"+districtId+"/bankSetup/"+bankSetupId);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    //api to find all bankBranches of a bank of a upazila by bankSetup
 /**
     * GET  /bankBranchs/district/banksetup/id -> get all the bankBranchs by upazila
     */
    @RequestMapping(value = "/bankBranchs/upazila/{upazilaId}/bankSetup/{bankSetupId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BankBranch>> getAllBankBranchsByUpaziaIdAndBankId(Pageable pageable, @PathVariable Long upazilaId, @PathVariable Long bankSetupId)
        throws URISyntaxException {
        BankSetup bankSetup = bankSetupRepository.findOne(bankSetupId);
        Page<BankBranch> page = bankBranchRepository.findBankBranchByUpazilaAndBankOrderByName(pageable,upazilaId, bankSetupId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bankBranchs/district/"+upazilaId+"/bankSetup/"+bankSetupId);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bankBranchs/:id -> get the "id" bankBranch.
     */
    @RequestMapping(value = "/bankBranchs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankBranch> getBankBranch(@PathVariable Long id) {
        log.debug("REST request to get BankBranch : {}", id);
        return Optional.ofNullable(bankBranchRepository.findOne(id))
            .map(bankBranch -> new ResponseEntity<>(
                bankBranch,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bankBranchs/:id -> delete the "id" bankBranch.
     */
    @RequestMapping(value = "/bankBranchs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBankBranch(@PathVariable Long id) {
        log.debug("REST request to delete BankBranch : {}", id);
        bankBranchRepository.delete(id);
        bankBranchSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bankBranch", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bankBranchs/:query -> search for the bankBranch corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bankBranchs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BankBranch> searchBankBranchs(@PathVariable String query) {
        return StreamSupport
            .stream(bankBranchSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
