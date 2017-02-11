package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsTrade;
import gov.step.app.domain.MpoTrade;
import gov.step.app.repository.MpoTradeRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.MpoTradeSearchRepository;
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
 * REST controller for managing MpoTrade.
 */
@RestController
@RequestMapping("/api")
public class MpoTradeResource {

    private final Logger log = LoggerFactory.getLogger(MpoTradeResource.class);

    @Inject
    private MpoTradeRepository mpoTradeRepository;

    @Inject
    private MpoTradeSearchRepository mpoTradeSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /mpoTrades -> Create a new mpoTrade.
     */
    @RequestMapping(value = "/mpoTrades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoTrade> createMpoTrade(@RequestBody MpoTrade mpoTrade) throws URISyntaxException {
        log.debug("REST request to save MpoTrade : {}", mpoTrade);
        if (mpoTrade.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoTrade cannot already have an ID").body(null);
        }
        mpoTrade.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        mpoTrade.setCratedDate(LocalDate.now());
        MpoTrade result = mpoTradeRepository.save(mpoTrade);
        mpoTradeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mpoTrades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoTrade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoTrades -> Updates an existing mpoTrade.
     */
    @RequestMapping(value = "/mpoTrades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoTrade> updateMpoTrade(@RequestBody MpoTrade mpoTrade) throws URISyntaxException {
        log.debug("REST request to update MpoTrade : {}", mpoTrade);
        if (mpoTrade.getId() == null) {
            return createMpoTrade(mpoTrade);
        }
        mpoTrade.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        mpoTrade.setUpdateDate(LocalDate.now());
        MpoTrade result = mpoTradeRepository.save(mpoTrade);
        mpoTradeSearchRepository.save(mpoTrade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoTrade", mpoTrade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mpoTrades -> get all the mpoTrades.
     */
    @RequestMapping(value = "/mpoTrades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoTrade>> getAllMpoTrades(Pageable pageable)
        throws URISyntaxException {
        Page<MpoTrade> page = mpoTradeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoTrades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoTrades/:id -> get the "id" mpoTrade.
     */
    @RequestMapping(value = "/mpoTrades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoTrade> getMpoTrade(@PathVariable Long id) {
        log.debug("REST request to get MpoTrade : {}", id);
        return Optional.ofNullable(mpoTradeRepository.findOne(id))
            .map(mpoTrade -> new ResponseEntity<>(
                mpoTrade,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
/**
     * GET  /mpoTrades/cmsTrades/:id -> get the "id" mpoTrade.
     */
    @RequestMapping(value = "/mpoTrades/cmsTrades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoTrade> getMpoTradeByCmsTrade(@PathVariable Long id) {
        log.debug("REST request to get MpoTrade : {}", id);
        return Optional.ofNullable(mpoTradeRepository.findOneByTrade(id))
            .map(mpoTrade -> new ResponseEntity<>(
                mpoTrade,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /mpoTrades -> get all the mpoTrades.
     */
    @RequestMapping(value = "/mpoTrades/cmsTrades/cmsCurriculum/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsTrade>> getAllMpoTrades(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<CmsTrade> page = mpoTradeRepository.findAllCmsTradesByCurriculum(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoTrades/cmsTrades/cmsCurriculum/"+id);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * DELETE  /mpoTrades/:id -> delete the "id" mpoTrade.
     */
    @RequestMapping(value = "/mpoTrades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoTrade(@PathVariable Long id) {
        log.debug("REST request to delete MpoTrade : {}", id);
        mpoTradeRepository.delete(id);
        mpoTradeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoTrade", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoTrades/:query -> search for the mpoTrade corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoTrades/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoTrade> searchMpoTrades(@PathVariable String query) {
        return StreamSupport
            .stream(mpoTradeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
