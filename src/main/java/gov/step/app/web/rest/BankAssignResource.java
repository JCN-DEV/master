package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.BankAssign;
import gov.step.app.repository.BankAssignRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.BankAssignSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BankAssign.
 */
@RestController
@RequestMapping("/api")
public class BankAssignResource {

    private final Logger log = LoggerFactory.getLogger(BankAssignResource.class);

    @Inject
    private BankAssignRepository bankAssignRepository;

    @Inject
    private BankAssignSearchRepository bankAssignSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /bankAssigns -> Create a new bankAssign.
     */
    @RequestMapping(value = "/bankAssigns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankAssign> createBankAssign(@RequestBody BankAssign bankAssign) throws URISyntaxException {
        log.debug("REST request to save BankAssign : {}", bankAssign);
        if (bankAssign.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bankAssign cannot already have an ID").body(null);
        }
        bankAssign.setCreatedBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        bankAssign.setCreatedDate(LocalDate.now());
        bankAssign.setStatus(true);
        BankAssign result = bankAssignRepository.save(bankAssign);
        bankAssignSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bankAssigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bankAssign", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bankAssigns -> Updates an existing bankAssign.
     */
    @RequestMapping(value = "/bankAssigns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankAssign> updateBankAssign(@RequestBody BankAssign bankAssign) throws URISyntaxException {
        log.debug("REST request to update BankAssign : {}", bankAssign);
        if (bankAssign.getId() == null) {
            return createBankAssign(bankAssign);
        }
        bankAssign.setUpdatedBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        bankAssign.setModifiedDate(LocalDate.now());
        BankAssign result = bankAssignRepository.save(bankAssign);
        bankAssignSearchRepository.save(bankAssign);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bankAssign", bankAssign.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bankAssigns -> get all the bankAssigns.
     */
    @RequestMapping(value = "/bankAssigns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BankAssign>> getAllBankAssigns(Pageable pageable)
        throws URISyntaxException {
        Page<BankAssign> page = bankAssignRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bankAssigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bankAssigns/:id -> get the "id" bankAssign.
     */
    @RequestMapping(value = "/bankAssigns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankAssign> getBankAssign(@PathVariable Long id) {
        log.debug("REST request to get BankAssign : {}", id);
        return Optional.ofNullable(bankAssignRepository.findOne(id))
            .map(bankAssign -> new ResponseEntity<>(
                bankAssign,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bankAssigns/:id -> delete the "id" bankAssign.
     */
    @RequestMapping(value = "/bankAssigns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBankAssign(@PathVariable Long id) {
        log.debug("REST request to delete BankAssign : {}", id);
        bankAssignRepository.delete(id);
        bankAssignSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bankAssign", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bankAssigns/:query -> search for the bankAssign corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bankAssigns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BankAssign> searchBankAssigns(@PathVariable String query) {
        return StreamSupport
            .stream(bankAssignSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
