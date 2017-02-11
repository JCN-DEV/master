package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JasperReportParameter;
import gov.step.app.repository.JasperReportParameterRepository;
import gov.step.app.repository.search.JasperReportParameterSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing JasperReportParameter.
 */
@RestController
@RequestMapping("/api")
public class JasperReportParameterResource {

    private final Logger log = LoggerFactory.getLogger(JasperReportParameterResource.class);

    @Inject
    private JasperReportParameterRepository jasperReportParameterRepository;

    @Inject
    private JasperReportParameterSearchRepository jasperReportParameterSearchRepository;

    /**
     * POST  /jasperReportParameters -> Create a new jasperReportParameter.
     */
    @RequestMapping(value = "/jasperReportParameters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JasperReportParameter> createJasperReportParameter(@Valid @RequestBody JasperReportParameter jasperReportParameter) throws URISyntaxException {
        log.debug("REST request to save JasperReportParameter : {}", jasperReportParameter);
        if (jasperReportParameter.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jasperReportParameter cannot already have an ID").body(null);
        }
        JasperReportParameter result = jasperReportParameterRepository.save(jasperReportParameter);
        jasperReportParameterSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jasperReportParameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jasperReportParameter", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jasperReportParameters -> Updates an existing jasperReportParameter.
     */
    @RequestMapping(value = "/jasperReportParameters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JasperReportParameter> updateJasperReportParameter(@Valid @RequestBody JasperReportParameter jasperReportParameter) throws URISyntaxException {
        log.debug("REST request to update JasperReportParameter : {}", jasperReportParameter);
        if (jasperReportParameter.getId() == null) {
            return createJasperReportParameter(jasperReportParameter);
        }
        JasperReportParameter result = jasperReportParameterRepository.save(jasperReportParameter);
        jasperReportParameterSearchRepository.save(jasperReportParameter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jasperReportParameter", jasperReportParameter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jasperReportParameters -> get all the jasperReportParameters.
     */
    @RequestMapping(value = "/jasperReportParameters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JasperReportParameter>> getAllJasperReportParameters(Pageable pageable)
        throws URISyntaxException {
        Page<JasperReportParameter> page = jasperReportParameterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jasperReportParameters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jasperReportParameters/:id -> get the "id" jasperReportParameter.
     */
    @RequestMapping(value = "/jasperReportParameters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JasperReportParameter> getJasperReportParameter(@PathVariable Long id) {
        log.debug("REST request to get JasperReportParameter : {}", id);
        return Optional.ofNullable(jasperReportParameterRepository.findOne(id))
            .map(jasperReportParameter -> new ResponseEntity<>(
                jasperReportParameter,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jasperReportParameters/:id -> delete the "id" jasperReportParameter.
     */
    @RequestMapping(value = "/jasperReportParameters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJasperReportParameter(@PathVariable Long id) {
        log.debug("REST request to delete JasperReportParameter : {}", id);
        jasperReportParameterRepository.delete(id);
        jasperReportParameterSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jasperReportParameter", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jasperReportParameters/:query -> search for the jasperReportParameter corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jasperReportParameters/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JasperReportParameter> searchJasperReportParameters(@PathVariable String query) {
        return StreamSupport
            .stream(jasperReportParameterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /jasperReportParameter/jasperReport/:id -> get all JasperReportParameter by JasperReport
     */
    @RequestMapping(value = "/report/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JasperReportParameter> getJasperParamByJasperReport(@PathVariable Long id)
        throws URISyntaxException {
        List<JasperReportParameter> jasperReportParameters = jasperReportParameterRepository.findByJasperReport(id);
        return jasperReportParameters;
    }

}
