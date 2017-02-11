package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmHoliday;
import gov.step.app.repository.AlmHolidayRepository;
import gov.step.app.repository.search.AlmHolidaySearchRepository;
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
 * REST controller for managing AlmHoliday.
 */
@RestController
@RequestMapping("/api")
public class AlmHolidayResource {

    private final Logger log = LoggerFactory.getLogger(AlmHolidayResource.class);

    @Inject
    private AlmHolidayRepository almHolidayRepository;

    @Inject
    private AlmHolidaySearchRepository almHolidaySearchRepository;

    /**
     * POST  /almHolidays -> Create a new almHoliday.
     */
    @RequestMapping(value = "/almHolidays",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmHoliday> createAlmHoliday(@Valid @RequestBody AlmHoliday almHoliday) throws URISyntaxException {
        log.debug("REST request to save AlmHoliday : {}", almHoliday);
        if (almHoliday.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almHoliday cannot already have an ID").body(null);
        }
        AlmHoliday result = almHolidayRepository.save(almHoliday);
        almHolidaySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almHolidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almHoliday", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almHolidays -> Updates an existing almHoliday.
     */
    @RequestMapping(value = "/almHolidays",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmHoliday> updateAlmHoliday(@Valid @RequestBody AlmHoliday almHoliday) throws URISyntaxException {
        log.debug("REST request to update AlmHoliday : {}", almHoliday);
        if (almHoliday.getId() == null) {
            return createAlmHoliday(almHoliday);
        }
        AlmHoliday result = almHolidayRepository.save(almHoliday);
        almHolidaySearchRepository.save(almHoliday);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almHoliday", almHoliday.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almHolidays -> get all the almHolidays.
     */
    @RequestMapping(value = "/almHolidays",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmHoliday>> getAllAlmHolidays(Pageable pageable)
        throws URISyntaxException {
        Page<AlmHoliday> page = almHolidayRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almHolidays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almHolidays/:id -> get the "id" almHoliday.
     */
    @RequestMapping(value = "/almHolidays/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmHoliday> getAlmHoliday(@PathVariable Long id) {
        log.debug("REST request to get AlmHoliday : {}", id);
        return Optional.ofNullable(almHolidayRepository.findOne(id))
            .map(almHoliday -> new ResponseEntity<>(
                almHoliday,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almHolidays/:id -> delete the "id" almHoliday.
     */
    @RequestMapping(value = "/almHolidays/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmHoliday(@PathVariable Long id) {
        log.debug("REST request to delete AlmHoliday : {}", id);
        almHolidayRepository.delete(id);
        almHolidaySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almHoliday", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almHolidays/:query -> search for the almHoliday corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almHolidays/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmHoliday> searchAlmHolidays(@PathVariable String query) {
        return StreamSupport
            .stream(almHolidaySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/almHolidays/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeName(@RequestParam String value)
    {
        Optional<AlmHoliday> almHoliday = almHolidayRepository.findOneByTypeName(value.toLowerCase());
        log.debug("almHolidays by name: "+value+", Stat: "+Optional.empty().equals(almHoliday));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(almHoliday))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
