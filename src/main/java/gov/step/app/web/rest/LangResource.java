package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Lang;
import gov.step.app.repository.LangRepository;
import gov.step.app.repository.search.LangSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Lang.
 */
@RestController
@RequestMapping("/api")
public class LangResource {

    private final Logger log = LoggerFactory.getLogger(LangResource.class);

    @Inject
    private LangRepository langRepository;

    @Inject
    private LangSearchRepository langSearchRepository;

    /**
     * POST  /langs -> Create a new lang.
     */
    @RequestMapping(value = "/langs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lang> createLang(@Valid @RequestBody Lang lang) throws URISyntaxException {
        log.debug("REST request to save Lang : {}", lang);
        if (lang.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new lang cannot already have an ID").body(null);
        }
        Lang result = langRepository.save(lang);
        langSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/langs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lang", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /langs -> Updates an existing lang.
     */
    @RequestMapping(value = "/langs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lang> updateLang(@Valid @RequestBody Lang lang) throws URISyntaxException {
        log.debug("REST request to update Lang : {}", lang);
        if (lang.getId() == null) {
            return createLang(lang);
        }
        Lang result = langRepository.save(lang);
        langSearchRepository.save(lang);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lang", lang.getId().toString()))
            .body(result);
    }

    /**
     * GET  /langs -> get all the langs.
     */
    @RequestMapping(value = "/langs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Lang>> getAllLangs(Pageable pageable)
        throws URISyntaxException {
        Page<Lang> page = langRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/langs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /langs/employee/:id -> get all lang by employee
     */
    @RequestMapping(value = "/langs/employee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lang> getAllLangByEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<Lang> langs = langRepository.findByEmployee(id);
        return  langs;

    }

    /**
     * GET  /langs/:id -> get the "id" lang.
     */
    @RequestMapping(value = "/langs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lang> getLang(@PathVariable Long id) {
        log.debug("REST request to get Lang : {}", id);
        return Optional.ofNullable(langRepository.findOne(id))
            .map(lang -> new ResponseEntity<>(
                lang,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /langs/:id -> delete the "id" lang.
     */
    @RequestMapping(value = "/langs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLang(@PathVariable Long id) {
        log.debug("REST request to delete Lang : {}", id);
        langRepository.delete(id);
        langSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lang", id.toString())).build();
    }

    /**
     * SEARCH  /_search/langs/:query -> search for the lang corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/langs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lang> searchLangs(@PathVariable String query) {
        return StreamSupport
            .stream(langSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
