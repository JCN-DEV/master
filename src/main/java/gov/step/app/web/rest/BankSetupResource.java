package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.BankSetup;
import gov.step.app.repository.BankSetupRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.BankSetupSearchRepository;
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
 * REST controller for managing BankSetup.
 */
@RestController
@RequestMapping("/api")
public class BankSetupResource {

    private final Logger log = LoggerFactory.getLogger(BankSetupResource.class);

    @Inject
    private BankSetupRepository bankSetupRepository;

    @Inject
    private BankSetupSearchRepository bankSetupSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /bankSetups -> Create a new bankSetup.
     */
    @RequestMapping(value = "/bankSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankSetup> createBankSetup(@RequestBody BankSetup bankSetup) throws URISyntaxException {
        log.debug("REST request to save BankSetup : {}", bankSetup);
        if (bankSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bankSetup cannot already have an ID").body(null);
        }
        bankSetup.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        bankSetup.setCreateDate(LocalDate.now());
        BankSetup result = bankSetupRepository.save(bankSetup);
        bankSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bankSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bankSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bankSetups -> Updates an existing bankSetup.
     */
    @RequestMapping(value = "/bankSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankSetup> updateBankSetup(@RequestBody BankSetup bankSetup) throws URISyntaxException {
        log.debug("REST request to update BankSetup : {}", bankSetup);
        if (bankSetup.getId() == null) {
            return createBankSetup(bankSetup);
        }
        bankSetup.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        bankSetup.setUpdateDate(LocalDate.now());
        BankSetup result = bankSetupRepository.save(bankSetup);
        bankSetupSearchRepository.save(bankSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bankSetup", bankSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bankSetups -> get all the bankSetups.
     */
    @RequestMapping(value = "/bankSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BankSetup>> getAllBankSetups(Pageable pageable)
        throws URISyntaxException {
        Page<BankSetup> page = bankSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bankSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bankSetups/:id -> get the "id" bankSetup.
     */
    @RequestMapping(value = "/bankSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankSetup> getBankSetup(@PathVariable Long id) {
        log.debug("REST request to get BankSetup : {}", id);
        return Optional.ofNullable(bankSetupRepository.findOne(id))
            .map(bankSetup -> new ResponseEntity<>(
                bankSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bankSetups/:id -> delete the "id" bankSetup.
     */
    @RequestMapping(value = "/bankSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBankSetup(@PathVariable Long id) {
        log.debug("REST request to delete BankSetup : {}", id);
        bankSetupRepository.delete(id);
        bankSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bankSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bankSetups/:query -> search for the bankSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bankSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BankSetup> searchBankSetups(@PathVariable String query) {
        return StreamSupport
            .stream(bankSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
