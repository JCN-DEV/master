package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmEarningFrequency;
import gov.step.app.repository.AlmEarningFrequencyRepository;
import gov.step.app.repository.search.AlmEarningFrequencySearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing AlmEarningFrequency.
 */
@RestController
@RequestMapping("/api")
public class AlmEarningFrequencyResource {

    private final Logger log = LoggerFactory.getLogger(AlmEarningFrequencyResource.class);

    @Inject
    private AlmEarningFrequencyRepository almEarningFrequencyRepository;

    @Inject
    private AlmEarningFrequencySearchRepository almEarningFrequencySearchRepository;

    /**
     * POST  /almEarningFrequencys -> Create a new almEarningFrequency.
     */
    @RequestMapping(value = "/almEarningFrequencys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEarningFrequency> createAlmEarningFrequency(@Valid @RequestBody AlmEarningFrequency almEarningFrequency) throws URISyntaxException {
        log.debug("REST request to save AlmEarningFrequency : {}", almEarningFrequency);
        if (almEarningFrequency.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almEarningFrequency cannot already have an ID").body(null);
        }
        AlmEarningFrequency result = almEarningFrequencyRepository.save(almEarningFrequency);
        almEarningFrequencySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almEarningFrequencys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almEarningFrequency", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almEarningFrequencys -> Updates an existing almEarningFrequency.
     */
    @RequestMapping(value = "/almEarningFrequencys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEarningFrequency> updateAlmEarningFrequency(@Valid @RequestBody AlmEarningFrequency almEarningFrequency) throws URISyntaxException {
        log.debug("REST request to update AlmEarningFrequency : {}", almEarningFrequency);
        if (almEarningFrequency.getId() == null) {
            return createAlmEarningFrequency(almEarningFrequency);
        }
        AlmEarningFrequency result = almEarningFrequencyRepository.save(almEarningFrequency);
        almEarningFrequencySearchRepository.save(almEarningFrequency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almEarningFrequency", almEarningFrequency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almEarningFrequencys -> get all the almEarningFrequencys.
     */
    @RequestMapping(value = "/almEarningFrequencys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmEarningFrequency>> getAllAlmEarningFrequencys(Pageable pageable)
        throws URISyntaxException {
        Page<AlmEarningFrequency> page = almEarningFrequencyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almEarningFrequencys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almEarningFrequencys/:id -> get the "id" almEarningFrequency.
     */
    @RequestMapping(value = "/almEarningFrequencys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEarningFrequency> getAlmEarningFrequency(@PathVariable Long id) {
        log.debug("REST request to get AlmEarningFrequency : {}", id);
        return Optional.ofNullable(almEarningFrequencyRepository.findOne(id))
            .map(almEarningFrequency -> new ResponseEntity<>(
                almEarningFrequency,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almEarningFrequencys/:id -> delete the "id" almEarningFrequency.
     */
    @RequestMapping(value = "/almEarningFrequencys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmEarningFrequency(@PathVariable Long id) {
        log.debug("REST request to delete AlmEarningFrequency : {}", id);
        almEarningFrequencyRepository.delete(id);
        almEarningFrequencySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almEarningFrequency", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almEarningFrequencys/:query -> search for the almEarningFrequency corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almEarningFrequencys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmEarningFrequency> searchAlmEarningFrequencys(@PathVariable String query) {
        return StreamSupport
            .stream(almEarningFrequencySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/almEarningFrequencys/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeName(@RequestParam String value)
    {
        Optional<AlmEarningFrequency> almEarningFrequency = almEarningFrequencyRepository.findOneByEarningFrequencyName(value.toLowerCase());
        log.debug("almEarningFrequencys by name: "+value+", Stat: "+Optional.empty().equals(almEarningFrequency));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(almEarningFrequency))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
