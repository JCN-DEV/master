package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmDutySide;
import gov.step.app.repository.AlmDutySideRepository;
import gov.step.app.repository.search.AlmDutySideSearchRepository;
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
 * REST controller for managing AlmDutySide.
 */
@RestController
@RequestMapping("/api")
public class AlmDutySideResource {

    private final Logger log = LoggerFactory.getLogger(AlmDutySideResource.class);

    @Inject
    private AlmDutySideRepository almDutySideRepository;

    @Inject
    private AlmDutySideSearchRepository almDutySideSearchRepository;

    /**
     * POST  /almDutySides -> Create a new almDutySide.
     */
    @RequestMapping(value = "/almDutySides",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmDutySide> createAlmDutySide(@Valid @RequestBody AlmDutySide almDutySide) throws URISyntaxException {
        log.debug("REST request to save AlmDutySide : {}", almDutySide);
        if (almDutySide.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almDutySide cannot already have an ID").body(null);
        }
        AlmDutySide result = almDutySideRepository.save(almDutySide);
        almDutySideSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almDutySides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almDutySide", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almDutySides -> Updates an existing almDutySide.
     */
    @RequestMapping(value = "/almDutySides",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmDutySide> updateAlmDutySide(@Valid @RequestBody AlmDutySide almDutySide) throws URISyntaxException {
        log.debug("REST request to update AlmDutySide : {}", almDutySide);
        if (almDutySide.getId() == null) {
            return createAlmDutySide(almDutySide);
        }
        AlmDutySide result = almDutySideRepository.save(almDutySide);
        almDutySideSearchRepository.save(almDutySide);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almDutySide", almDutySide.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almDutySides -> get all the almDutySides.
     */
    @RequestMapping(value = "/almDutySides",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmDutySide>> getAllAlmDutySides(Pageable pageable)
        throws URISyntaxException {
        Page<AlmDutySide> page = almDutySideRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almDutySides");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almDutySides/:id -> get the "id" almDutySide.
     */
    @RequestMapping(value = "/almDutySides/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmDutySide> getAlmDutySide(@PathVariable Long id) {
        log.debug("REST request to get AlmDutySide : {}", id);
        return Optional.ofNullable(almDutySideRepository.findOne(id))
            .map(almDutySide -> new ResponseEntity<>(
                almDutySide,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almDutySides/:id -> delete the "id" almDutySide.
     */
    @RequestMapping(value = "/almDutySides/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmDutySide(@PathVariable Long id) {
        log.debug("REST request to delete AlmDutySide : {}", id);
        almDutySideRepository.delete(id);
        almDutySideSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almDutySide", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almDutySides/:query -> search for the almDutySide corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almDutySides/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmDutySide> searchAlmDutySides(@PathVariable String query) {
        return StreamSupport
            .stream(almDutySideSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @RequestMapping(value = "/almDutySides/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeName(@RequestParam String value)
    {
        Optional<AlmDutySide> almDutySide = almDutySideRepository.findOneBySideName(value.toLowerCase());
        log.debug("almDutySides by name: "+value+", Stat: "+Optional.empty().equals(almDutySide));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(almDutySide))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
