package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmAttendanceStatus;
import gov.step.app.repository.AlmAttendanceStatusRepository;
import gov.step.app.repository.search.AlmAttendanceStatusSearchRepository;
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
 * REST controller for managing AlmAttendanceStatus.
 */
@RestController
@RequestMapping("/api")
public class AlmAttendanceStatusResource {

    private final Logger log = LoggerFactory.getLogger(AlmAttendanceStatusResource.class);

    @Inject
    private AlmAttendanceStatusRepository almAttendanceStatusRepository;

    @Inject
    private AlmAttendanceStatusSearchRepository almAttendanceStatusSearchRepository;

    /**
     * POST  /almAttendanceStatuss -> Create a new almAttendanceStatus.
     */
    @RequestMapping(value = "/almAttendanceStatuss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmAttendanceStatus> createAlmAttendanceStatus(@Valid @RequestBody AlmAttendanceStatus almAttendanceStatus) throws URISyntaxException {
        log.debug("REST request to save AlmAttendanceStatus : {}", almAttendanceStatus);
        if (almAttendanceStatus.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almAttendanceStatus cannot already have an ID").body(null);
        }
        AlmAttendanceStatus result = almAttendanceStatusRepository.save(almAttendanceStatus);
        almAttendanceStatusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almAttendanceStatuss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almAttendanceStatus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almAttendanceStatuss -> Updates an existing almAttendanceStatus.
     */
    @RequestMapping(value = "/almAttendanceStatuss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmAttendanceStatus> updateAlmAttendanceStatus(@Valid @RequestBody AlmAttendanceStatus almAttendanceStatus) throws URISyntaxException {
        log.debug("REST request to update AlmAttendanceStatus : {}", almAttendanceStatus);
        if (almAttendanceStatus.getId() == null) {
            return createAlmAttendanceStatus(almAttendanceStatus);
        }
        AlmAttendanceStatus result = almAttendanceStatusRepository.save(almAttendanceStatus);
        almAttendanceStatusSearchRepository.save(almAttendanceStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almAttendanceStatus", almAttendanceStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almAttendanceStatuss -> get all the almAttendanceStatuss.
     */
    @RequestMapping(value = "/almAttendanceStatuss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmAttendanceStatus>> getAllAlmAttendanceStatuss(Pageable pageable)
        throws URISyntaxException {
        Page<AlmAttendanceStatus> page = almAttendanceStatusRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almAttendanceStatuss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almAttendanceStatuss/:id -> get the "id" almAttendanceStatus.
     */
    @RequestMapping(value = "/almAttendanceStatuss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmAttendanceStatus> getAlmAttendanceStatus(@PathVariable Long id) {
        log.debug("REST request to get AlmAttendanceStatus : {}", id);
        return Optional.ofNullable(almAttendanceStatusRepository.findOne(id))
            .map(almAttendanceStatus -> new ResponseEntity<>(
                almAttendanceStatus,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almAttendanceStatuss/:id -> delete the "id" almAttendanceStatus.
     */
    @RequestMapping(value = "/almAttendanceStatuss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmAttendanceStatus(@PathVariable Long id) {
        log.debug("REST request to delete AlmAttendanceStatus : {}", id);
        almAttendanceStatusRepository.delete(id);
        almAttendanceStatusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almAttendanceStatus", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almAttendanceStatuss/:query -> search for the almAttendanceStatus corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almAttendanceStatuss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmAttendanceStatus> searchAlmAttendanceStatuss(@PathVariable String query) {
        return StreamSupport
            .stream(almAttendanceStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/almAttendanceStatuss/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeName(@RequestParam String value)
    {
        Optional<AlmAttendanceStatus> almAttendanceStatus = almAttendanceStatusRepository.findOneByAttendanceStatusName(value.toLowerCase());
        log.debug("almAttendanceStatuss by name: "+value+", Stat: "+Optional.empty().equals(almAttendanceStatus));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(almAttendanceStatus))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/almAttendanceStatuss/checkCode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByCode(@RequestParam String value)
    {
        Optional<AlmAttendanceStatus> almAttendanceStatus = almAttendanceStatusRepository.findOneByAttendanceCode(value.toLowerCase());
        log.debug("almAttendanceStatuss by name: "+value+", Stat: "+Optional.empty().equals(almAttendanceStatus));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(almAttendanceStatus))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/almAttendanceStatuss/checkShortCode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByShortCode(@RequestParam String value)
    {
        Optional<AlmAttendanceStatus> almAttendanceStatus = almAttendanceStatusRepository.findOneByShortCode(value.toLowerCase());
        log.debug("almAttendanceStatuss by name: "+value+", Stat: "+Optional.empty().equals(almAttendanceStatus));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(almAttendanceStatus))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
