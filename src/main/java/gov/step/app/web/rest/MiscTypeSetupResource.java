package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.MiscTypeSetup;
import gov.step.app.domain.enumeration.miscTypeCategory;
import gov.step.app.repository.MiscTypeSetupRepository;
import gov.step.app.repository.search.MiscTypeSetupSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MiscTypeSetup.
 */
@RestController
@RequestMapping("/api")
public class MiscTypeSetupResource {

    private final Logger log = LoggerFactory.getLogger(MiscTypeSetupResource.class);

    @Inject
    private MiscTypeSetupRepository miscTypeSetupRepository;

    @Inject
    private MiscTypeSetupSearchRepository miscTypeSetupSearchRepository;

    /**
     * POST  /miscTypeSetups -> Create a new miscTypeSetup.
     */
    @RequestMapping(value = "/miscTypeSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MiscTypeSetup> createMiscTypeSetup(@Valid @RequestBody MiscTypeSetup miscTypeSetup) throws URISyntaxException {
        log.debug("REST request to save MiscTypeSetup : {}", miscTypeSetup);
        if (miscTypeSetup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("miscTypeSetup", "idexists", "A new miscTypeSetup cannot already have an ID")).body(null);
        }
        MiscTypeSetup result = miscTypeSetupRepository.save(miscTypeSetup);
        miscTypeSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/miscTypeSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("miscTypeSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /miscTypeSetups -> Updates an existing miscTypeSetup.
     */
    @RequestMapping(value = "/miscTypeSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MiscTypeSetup> updateMiscTypeSetup(@Valid @RequestBody MiscTypeSetup miscTypeSetup) throws URISyntaxException {
        log.debug("REST request to update MiscTypeSetup : {}", miscTypeSetup);
        if (miscTypeSetup.getId() == null) {
            return createMiscTypeSetup(miscTypeSetup);
        }
        MiscTypeSetup result = miscTypeSetupRepository.save(miscTypeSetup);
        miscTypeSetupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("miscTypeSetup", miscTypeSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /miscTypeSetups -> get all the miscTypeSetups.
     */
    @RequestMapping(value = "/miscTypeSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MiscTypeSetup>> getAllMiscTypeSetups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MiscTypeSetups");
        Page<MiscTypeSetup> page = miscTypeSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/miscTypeSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /miscTypeSetups -> get all the miscTypeSetups by Category
     */
    @RequestMapping(value = "/miscTypeSetupsByCat/{cat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MiscTypeSetup>> getAllMiscTypeSetupsByCategory(@PathVariable String cat, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MiscTypeSetups by cat: "+cat);

        Page<MiscTypeSetup> page = miscTypeSetupRepository.findByTypeCategory(cat, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/miscTypeSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /miscTypeSetups -> get all the miscTypeSetups by Category and Active Status
     */
    @RequestMapping(value = "/miscTypeSetupsByCat/{cat}/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MiscTypeSetup>> getAllMiscTypeSetupsByCategoryAndStatus(@PathVariable String cat, @PathVariable boolean stat , Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MiscTypeSetups by cat:{} and stat:{}: ",cat, stat);

        Page<MiscTypeSetup> page = miscTypeSetupRepository.findByTypeCategoryAndActiveStatus(cat, stat, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/miscTypeSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /miscTypeSetups/:id -> get the "id" miscTypeSetup.
     */
    @RequestMapping(value = "/miscTypeSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MiscTypeSetup> getMiscTypeSetup(@PathVariable Long id) {
        log.debug("REST request to get MiscTypeSetup : {}", id);
        MiscTypeSetup miscTypeSetup = miscTypeSetupRepository.findOne(id);
        return Optional.ofNullable(miscTypeSetup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /miscTypeSetups/:id -> delete the "id" miscTypeSetup.
     */
    @RequestMapping(value = "/miscTypeSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMiscTypeSetup(@PathVariable Long id) {
        log.debug("REST request to delete MiscTypeSetup : {}", id);
        miscTypeSetupRepository.delete(id);
        miscTypeSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("miscTypeSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/miscTypeSetups/:query -> search for the miscTypeSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/miscTypeSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MiscTypeSetup> searchMiscTypeSetups(@PathVariable String query) {
        log.debug("REST request to search MiscTypeSetups for query {}", query);
        return StreamSupport
            .stream(miscTypeSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/miscTypeSetupsByCatNameTitle/{chktype}/{cat}/{value}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getMiscTypeByCategoryAndNameOrTitle(@PathVariable String chktype,@PathVariable String cat, @PathVariable String value)
    {
        List<MiscTypeSetup> modelInfoList = null;
        if(chktype.equalsIgnoreCase("name"))
        {
            modelInfoList = miscTypeSetupRepository.findOneByCategoryAndName(cat, value);
        }
        else
        {
            modelInfoList = miscTypeSetupRepository.findOneByCategoryAndTitle(cat, value);
        }
        log.debug("miscTypeSetupsByCatNameTitle by  cat: "+cat+", value: "+value);
        Map map =new HashMap();
        map.put("chkType", chktype);
        map.put("category", cat);
        map.put("value", value);
        if(modelInfoList!=null) map.put("total", modelInfoList.size());

        if(modelInfoList!=null && modelInfoList.size()>0)
        {
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
