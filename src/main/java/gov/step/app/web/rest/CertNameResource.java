package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CertName;
import gov.step.app.repository.CertNameRepository;
import gov.step.app.repository.search.CertNameSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CertName.
 */
@RestController
@RequestMapping("/api")
public class CertNameResource {

    private final Logger log = LoggerFactory.getLogger(CertNameResource.class);

    @Inject
    private CertNameRepository certNameRepository;

    @Inject
    private CertNameSearchRepository certNameSearchRepository;

    /**
     * POST  /certNames -> Create a new certName.
     */
    @RequestMapping(value = "/certNames",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CertName> createCertName(@RequestBody CertName certName) throws URISyntaxException {
        log.debug("REST request to save CertName : {}", certName);
        if (certName.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new certName cannot already have an ID").body(null);
        }
        CertName result = certNameRepository.save(certName);
        certNameSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/certNames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("certName", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /certNames -> Updates an existing certName.
     */
    @RequestMapping(value = "/certNames",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CertName> updateCertName(@RequestBody CertName certName) throws URISyntaxException {
        log.debug("REST request to update CertName : {}", certName);
        if (certName.getId() == null) {
            return createCertName(certName);
        }
        CertName result = certNameRepository.save(certName);
        certNameSearchRepository.save(certName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("certName", certName.getId().toString()))
            .body(result);
    }

    /**
     * GET  /certNames -> get all the certNames.
     */
    @RequestMapping(value = "/certNames",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CertName>> getAllCertNames(Pageable pageable)
        throws URISyntaxException {
        Page<CertName> page = certNameRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/certNames");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /certNames/:id -> get the "id" certName.
     */
    @RequestMapping(value = "/certNames/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CertName> getCertName(@PathVariable Long id) {
        log.debug("REST request to get CertName : {}", id);
        return Optional.ofNullable(certNameRepository.findOne(id))
            .map(certName -> new ResponseEntity<>(
                certName,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /certNames/:id -> delete the "id" certName.
     */
    @RequestMapping(value = "/certNames/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCertName(@PathVariable Long id) {
        log.debug("REST request to delete CertName : {}", id);
        certNameRepository.delete(id);
        certNameSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("certName", id.toString())).build();
    }

    /**
     * SEARCH  /_search/certNames/:query -> search for the certName corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/certNames/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CertName> searchCertNames(@PathVariable String query) {
        return StreamSupport
            .stream(certNameSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
