package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JasperReport;
import gov.step.app.repository.JasperReportRepository;
import gov.step.app.repository.search.JasperReportSearchRepository;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import org.codehaus.groovy.runtime.powerassert.SourceText;
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
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing JasperReport.
 */
@RestController
@RequestMapping("/api")
public class JasperReportResource {

    private final Logger log = LoggerFactory.getLogger(JasperReportResource.class);

    @Inject
    private JasperReportRepository jasperReportRepository;

    @Inject
    private JasperReportSearchRepository jasperReportSearchRepository;

    /**
     * POST  /jasperReports -> Create a new jasperReport.
     */
    @RequestMapping(value = "/jasperReports",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JasperReport> createJasperReport(@Valid @RequestBody JasperReport jasperReport) throws URISyntaxException {
        log.debug("REST request to save JasperReport : {}", jasperReport);
        if (jasperReport.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jasperReport cannot already have an ID").body(null);
        }
        jasperReport.setCreatedDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        JasperReport result = jasperReportRepository.save(jasperReport);
        jasperReportSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jasperReports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jasperReport", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jasperReports -> Updates an existing jasperReport.
     */
    @RequestMapping(value = "/jasperReports",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JasperReport> updateJasperReport(@Valid @RequestBody JasperReport jasperReport) throws URISyntaxException {
        log.debug("REST request to update JasperReport : {}", jasperReport);
        if (jasperReport.getId() == null) {
            return createJasperReport(jasperReport);
        }
        JasperReport result = jasperReportRepository.save(jasperReport);
        jasperReportSearchRepository.save(jasperReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jasperReport", jasperReport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jasperReports -> get all the jasperReports.
     */
    @RequestMapping(value = "/jasperReports",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JasperReport>> getAllJasperReports(Pageable pageable)
        throws URISyntaxException {
        Page<JasperReport> page = jasperReportRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jasperReports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jasperReports/:id -> get the "id" jasperReport.
     */
    @RequestMapping(value = "/jasperReports/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JasperReport> getJasperReport(@PathVariable Long id) {
        log.debug("REST request to get JasperReport : {}", id);
        return Optional.ofNullable(jasperReportRepository.findOne(id))
            .map(jasperReport -> new ResponseEntity<>(
                jasperReport,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jasperReports/:id -> delete the "id" jasperReport.
     */
    @RequestMapping(value = "/jasperReports/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJasperReport(@PathVariable Long id) {
        log.debug("REST request to delete JasperReport : {}", id);
        jasperReportRepository.delete(id);
        jasperReportSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jasperReport", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jasperReports/:query -> search for the jasperReport corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jasperReports/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JasperReport> searchJasperReports(@PathVariable String query) {
        return StreamSupport
            .stream(jasperReportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /reportByModule/:module -> get all JasperReport by module
     */
    @RequestMapping(value = "/reportByModule/{module}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JasperReport> getAllJasperReportByModule(@PathVariable String  module)
        throws URISyntaxException {

        log.debug("REST request to Get all JasperReport by Module Name : {}", module);
        List<JasperReport> jasperReports = jasperReportRepository.findJasperReportByModule(module);
        return jasperReports;
    }

    /**
     * GET  /moduleReports/:module -> get all JasperReport by module
     */
    @RequestMapping(value = "/moduleReports/{module}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String findReportsByModule() {
        //@PathVariable String  module
        File file=new File("jasperFile");
        try {
            OutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.write(100);
            outputStream.flush();
            outputStream.close();
            String getAbsolutePath=file.getAbsolutePath();
            boolean isFile=file.isFile();
            String a=file.getPath();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<");
            System.out.println(getAbsolutePath+", "+isFile+", "+a);
            return getAbsolutePath+", "+isFile+", "+a;


        }catch (IOException exc){
            System.out.println("File not found or write >>>>>>>>>>>>>>>>>>.");
        }

//        log.debug("REST request to Get all JasperReport by Module Name : {}", module);
//        List<JasperReport> jasperReports = jasperReportRepository.findJasperReportByModule(module);
//        return jasperReports;
        return null;
    }
}
