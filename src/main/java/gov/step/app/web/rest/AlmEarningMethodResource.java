package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmEarningMethod;
import gov.step.app.repository.AlmEarningMethodRepository;
import gov.step.app.repository.search.AlmEarningMethodSearchRepository;
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
 * REST controller for managing AlmEarningMethod.
 */
@RestController
@RequestMapping("/api")
public class AlmEarningMethodResource {

    private final Logger log = LoggerFactory.getLogger(AlmEarningMethodResource.class);

    @Inject
    private AlmEarningMethodRepository almEarningMethodRepository;

    @Inject
    private AlmEarningMethodSearchRepository almEarningMethodSearchRepository;

    /**
     * POST  /almEarningMethods -> Create a new almEarningMethod.
     */
    @RequestMapping(value = "/almEarningMethods",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEarningMethod> createAlmEarningMethod(@Valid @RequestBody AlmEarningMethod almEarningMethod) throws URISyntaxException {
        log.debug("REST request to save AlmEarningMethod : {}", almEarningMethod);
        if (almEarningMethod.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almEarningMethod cannot already have an ID").body(null);
        }
        AlmEarningMethod result = almEarningMethodRepository.save(almEarningMethod);
        almEarningMethodSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almEarningMethods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almEarningMethod", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almEarningMethods -> Updates an existing almEarningMethod.
     */
    @RequestMapping(value = "/almEarningMethods",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEarningMethod> updateAlmEarningMethod(@Valid @RequestBody AlmEarningMethod almEarningMethod) throws URISyntaxException {
        log.debug("REST request to update AlmEarningMethod : {}", almEarningMethod);
        if (almEarningMethod.getId() == null) {
            return createAlmEarningMethod(almEarningMethod);
        }
        AlmEarningMethod result = almEarningMethodRepository.save(almEarningMethod);
        almEarningMethodSearchRepository.save(almEarningMethod);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almEarningMethod", almEarningMethod.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almEarningMethods -> get all the almEarningMethods.
     */
    @RequestMapping(value = "/almEarningMethods",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmEarningMethod>> getAllAlmEarningMethods(Pageable pageable)
        throws URISyntaxException {
        Page<AlmEarningMethod> page = almEarningMethodRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almEarningMethods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almEarningMethods/:id -> get the "id" almEarningMethod.
     */
    @RequestMapping(value = "/almEarningMethods/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEarningMethod> getAlmEarningMethod(@PathVariable Long id) {
        log.debug("REST request to get AlmEarningMethod : {}", id);
        return Optional.ofNullable(almEarningMethodRepository.findOne(id))
            .map(almEarningMethod -> new ResponseEntity<>(
                almEarningMethod,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almEarningMethods/:id -> delete the "id" almEarningMethod.
     */
    @RequestMapping(value = "/almEarningMethods/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmEarningMethod(@PathVariable Long id) {
        log.debug("REST request to delete AlmEarningMethod : {}", id);
        almEarningMethodRepository.delete(id);
        almEarningMethodSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almEarningMethod", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almEarningMethods/:query -> search for the almEarningMethod corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almEarningMethods/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmEarningMethod> searchAlmEarningMethods(@PathVariable String query) {
        return StreamSupport
            .stream(almEarningMethodSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/almEarningMethods/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeName(@RequestParam String value)
    {
        Optional<AlmEarningMethod> almEarningMethod = almEarningMethodRepository.findOneByEarningMethodName(value.toLowerCase());
        log.debug("almEarningMethods by name: "+value+", Stat: "+Optional.empty().equals(almEarningMethod));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(almEarningMethod))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
