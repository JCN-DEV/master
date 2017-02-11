package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PersonalPay;
import gov.step.app.repository.PersonalPayRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.PersonalPaySearchRepository;
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
 * REST controller for managing PersonalPay.
 */
@RestController
@RequestMapping("/api")
public class PersonalPayResource {

    private final Logger log = LoggerFactory.getLogger(PersonalPayResource.class);

    @Inject
    private PersonalPayRepository personalPayRepository;

    @Inject
    private PersonalPaySearchRepository personalPaySearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /personalPays -> Create a new personalPay.
     */
    @RequestMapping(value = "/personalPays",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PersonalPay> createPersonalPay(@RequestBody PersonalPay personalPay) throws URISyntaxException {
        log.debug("REST request to save PersonalPay : {}", personalPay);
        if (personalPay.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new personalPay cannot already have an ID").body(null);
        }
        personalPay.setDateModified(LocalDate.now());
        personalPay.setDateCreated(LocalDate.now());
        personalPay.setCreatedBy(userRepository.findOne(SecurityUtils.getCurrentUser().getId()));
        PersonalPay result = personalPayRepository.save(personalPay);
        personalPaySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/personalPays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personalPay", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personalPays -> Updates an existing personalPay.
     */
    @RequestMapping(value = "/personalPays",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PersonalPay> updatePersonalPay(@RequestBody PersonalPay personalPay) throws URISyntaxException {
        log.debug("REST request to update PersonalPay : {}", personalPay);
        if (personalPay.getId() == null) {
            return createPersonalPay(personalPay);
        }
        personalPay.setDateModified(LocalDate.now());
        personalPay.setUpdatedBy(userRepository.findOne(SecurityUtils.getCurrentUser().getId()));
        PersonalPay result = personalPayRepository.save(personalPay);
        personalPaySearchRepository.save(personalPay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personalPay", personalPay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personalPays -> get all the personalPays.
     */
    @RequestMapping(value = "/personalPays",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PersonalPay>> getAllPersonalPays(Pageable pageable)
        throws URISyntaxException {
        Page<PersonalPay> page = personalPayRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personalPays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /personalPays/:id -> get the "id" personalPay.
     */
    @RequestMapping(value = "/personalPays/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PersonalPay> getPersonalPay(@PathVariable Long id) {
        log.debug("REST request to get PersonalPay : {}", id);
        return Optional.ofNullable(personalPayRepository.findOne(id))
            .map(personalPay -> new ResponseEntity<>(
                personalPay,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /personalPays/:id -> delete the "id" personalPay.
     */
    @RequestMapping(value = "/personalPays/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePersonalPay(@PathVariable Long id) {
        log.debug("REST request to delete PersonalPay : {}", id);
        personalPayRepository.delete(id);
        personalPaySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personalPay", id.toString())).build();
    }

    /**
     * SEARCH  /_search/personalPays/:query -> search for the personalPay corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/personalPays/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PersonalPay> searchPersonalPays(@PathVariable String query) {
        return StreamSupport
            .stream(personalPaySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
