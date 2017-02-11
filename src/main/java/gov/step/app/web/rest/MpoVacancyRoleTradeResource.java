package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.MpoVacancyRoleTrade;
import gov.step.app.repository.MpoVacancyRoleTradeRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.MpoVacancyRoleTradeSearchRepository;
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
 * REST controller for managing MpoVacancyRoleTrade.
 */
@RestController
@RequestMapping("/api")
public class MpoVacancyRoleTradeResource {

    private final Logger log = LoggerFactory.getLogger(MpoVacancyRoleTradeResource.class);

    @Inject
    private MpoVacancyRoleTradeRepository mpoVacancyRoleTradeRepository;

    @Inject
    private MpoVacancyRoleTradeSearchRepository mpoVacancyRoleTradeSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /mpoVacancyRoleTrades -> Create a new mpoVacancyRoleTrade.
     */
    @RequestMapping(value = "/mpoVacancyRoleTrades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoVacancyRoleTrade> createMpoVacancyRoleTrade(@RequestBody MpoVacancyRoleTrade mpoVacancyRoleTrade) throws URISyntaxException {
        log.debug("REST request to save MpoVacancyRoleTrade : {}", mpoVacancyRoleTrade);
        if (mpoVacancyRoleTrade.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoVacancyRoleTrade cannot already have an ID").body(null);
        }
        mpoVacancyRoleTrade.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        mpoVacancyRoleTrade.setCrateDate(LocalDate.now());
        mpoVacancyRoleTrade.setStatus(true);
        MpoVacancyRoleTrade result = mpoVacancyRoleTradeRepository.save(mpoVacancyRoleTrade);
        mpoVacancyRoleTradeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mpoVacancyRoleTrades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoVacancyRoleTrade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoVacancyRoleTrades -> Updates an existing mpoVacancyRoleTrade.
     */
    @RequestMapping(value = "/mpoVacancyRoleTrades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoVacancyRoleTrade> updateMpoVacancyRoleTrade(@RequestBody MpoVacancyRoleTrade mpoVacancyRoleTrade) throws URISyntaxException {
        log.debug("REST request to update MpoVacancyRoleTrade : {}", mpoVacancyRoleTrade);
        if (mpoVacancyRoleTrade.getId() == null) {
            return createMpoVacancyRoleTrade(mpoVacancyRoleTrade);
        }
        mpoVacancyRoleTrade.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        mpoVacancyRoleTrade.setUpdateDate(LocalDate.now());
        MpoVacancyRoleTrade result = mpoVacancyRoleTradeRepository.save(mpoVacancyRoleTrade);
        mpoVacancyRoleTradeSearchRepository.save(mpoVacancyRoleTrade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoVacancyRoleTrade", mpoVacancyRoleTrade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mpoVacancyRoleTrades -> get all the mpoVacancyRoleTrades.
     */
    @RequestMapping(value = "/mpoVacancyRoleTrades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoVacancyRoleTrade>> getAllMpoVacancyRoleTrades(Pageable pageable)
        throws URISyntaxException {
        Page<MpoVacancyRoleTrade> page = mpoVacancyRoleTradeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoVacancyRoleTrades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoVacancyRoleTrades/mpoVacancyRoles/id -> get all the mpoVacancyRoleTrades.
     */
    @RequestMapping(value = "/mpoVacancyRoleTrades/mpoVacancyRole/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoVacancyRoleTrade>> getAllMpoVacancyRoleTradesByRole(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<MpoVacancyRoleTrade> page = mpoVacancyRoleTradeRepository.findByMpoVacancyRole(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoVacancyRoleTrades/mpoVacancyRole/"+id);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoVacancyRoleTrades/:id -> get the "id" mpoVacancyRoleTrade.
     */
    @RequestMapping(value = "/mpoVacancyRoleTrades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoVacancyRoleTrade> getMpoVacancyRoleTrade(@PathVariable Long id) {
        log.debug("REST request to get MpoVacancyRoleTrade : {}", id);
        return Optional.ofNullable(mpoVacancyRoleTradeRepository.findOne(id))
            .map(mpoVacancyRoleTrade -> new ResponseEntity<>(
                mpoVacancyRoleTrade,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mpoVacancyRoleTrades/:id -> delete the "id" mpoVacancyRoleTrade.
     */
    @RequestMapping(value = "/mpoVacancyRoleTrades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoVacancyRoleTrade(@PathVariable Long id) {
        log.debug("REST request to delete MpoVacancyRoleTrade : {}", id);
        mpoVacancyRoleTradeRepository.delete(id);
        mpoVacancyRoleTradeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoVacancyRoleTrade", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoVacancyRoleTrades/:query -> search for the mpoVacancyRoleTrade corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoVacancyRoleTrades/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoVacancyRoleTrade> searchMpoVacancyRoleTrades(@PathVariable String query) {
        return StreamSupport
            .stream(mpoVacancyRoleTradeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
