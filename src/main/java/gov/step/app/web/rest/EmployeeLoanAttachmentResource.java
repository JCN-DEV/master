package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.EmployeeLoanAttachment;
import gov.step.app.repository.EmployeeLoanAttachmentRepository;
import gov.step.app.repository.search.EmployeeLoanAttachmentSearchRepository;
import gov.step.app.web.rest.jdbc.dao.CommonFolderCreator;
import gov.step.app.web.rest.util.AttachmentUtil;
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
 * REST controller for managing EmployeeLoanAttachment.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLoanAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLoanAttachmentResource.class);

    @Inject
    private EmployeeLoanAttachmentRepository employeeLoanAttachmentRepository;

    @Inject
    private EmployeeLoanAttachmentSearchRepository employeeLoanAttachmentSearchRepository;

    @Inject
    private  CommonFolderCreator commonFolderCreator;

    /**
     * POST  /employeeLoanAttachments -> Create a new employeeLoanAttachment.
     */
    @RequestMapping(value = "/employeeLoanAttachments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanAttachment> createEmployeeLoanAttachment(@Valid @RequestBody EmployeeLoanAttachment employeeLoanAttachment) throws URISyntaxException,Exception {
        log.debug("REST request to save EmployeeLoanAttachment : {}", employeeLoanAttachment);
        log.debug(employeeLoanAttachment.getHrEmployeeInfo() != null ? "NO Employee found ":employeeLoanAttachment.getHrEmployeeInfo().toString());

        if (employeeLoanAttachment.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeeLoanAttachment cannot already have an ID").body(null);
        }


        String fileName = "employeeLoan";
        String filepath = commonFolderCreator.folderCreation(fileName);

        if(employeeLoanAttachment.getFile() !=null){
            String filename=employeeLoanAttachment.getAttachmentCategory().getAttachmentName().replace("/", "_")+employeeLoanAttachment.getFileName().replace("/", "_");

            employeeLoanAttachment.setFileContentName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, employeeLoanAttachment.getFile()));
        }else{
            employeeLoanAttachment.setFileContentName(null);
        }
        employeeLoanAttachment.setFile(null);
        employeeLoanAttachment.setStatus(true);
        EmployeeLoanAttachment result = employeeLoanAttachmentRepository.save(employeeLoanAttachment);
        employeeLoanAttachmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employeeLoanAttachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanAttachment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employeeLoanAttachments -> Updates an existing employeeLoanAttachment.
     */
    @RequestMapping(value = "/employeeLoanAttachments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanAttachment> updateEmployeeLoanAttachment(@Valid @RequestBody EmployeeLoanAttachment employeeLoanAttachment) throws URISyntaxException,Exception {
        log.debug("REST request to update EmployeeLoanAttachment : {}", employeeLoanAttachment);
        if (employeeLoanAttachment.getId() == null) {
            return createEmployeeLoanAttachment(employeeLoanAttachment);
        }
        String filepath="/backup/employeeLoan/";
        if(employeeLoanAttachment.getFile() !=null){
            String filename=employeeLoanAttachment.getAttachmentCategory().getAttachmentName().replace("/", "_")+employeeLoanAttachment.getFileName().replace("/", "_");
            employeeLoanAttachment.setFileContentName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, employeeLoanAttachment.getFile()));
        }else{
            employeeLoanAttachment.setFileContentName(null);
        }

        EmployeeLoanAttachment result = employeeLoanAttachmentRepository.save(employeeLoanAttachment);
        employeeLoanAttachmentSearchRepository.save(employeeLoanAttachment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employeeLoanAttachment", employeeLoanAttachment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employeeLoanAttachments -> get all the employeeLoanAttachments.
     */
    @RequestMapping(value = "/employeeLoanAttachments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanAttachment>> getAllEmployeeLoanAttachments(Pageable pageable)
        throws URISyntaxException {
        Page<EmployeeLoanAttachment> page = employeeLoanAttachmentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employeeLoanAttachments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employeeLoanAttachments/:id -> get the "id" employeeLoanAttachment.
     */
    @RequestMapping(value = "/employeeLoanAttachments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanAttachment> getEmployeeLoanAttachment(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLoanAttachment : {}", id);
        return Optional.ofNullable(employeeLoanAttachmentRepository.findOne(id))
            .map(employeeLoanAttachment -> new ResponseEntity<>(
                employeeLoanAttachment,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employeeLoanAttachments/:id -> delete the "id" employeeLoanAttachment.
     */
    @RequestMapping(value = "/employeeLoanAttachments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployeeLoanAttachment(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLoanAttachment : {}", id);
        employeeLoanAttachmentRepository.delete(id);
        employeeLoanAttachmentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employeeLoanAttachment", id.toString())).build();
    }

    /**
     * SEARCH  /_search/employeeLoanAttachments/:query -> search for the employeeLoanAttachment corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/employeeLoanAttachments/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanAttachment> searchEmployeeLoanAttachments(@PathVariable String query) {
        return StreamSupport
            .stream(employeeLoanAttachmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /Employee loan attachments/employee/:id -> get the all attachment by employee
     */
    @RequestMapping(value = "/employeeLoanAttachments/employee/{id}/{applicationName}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanAttachment> getAttachmentByEmployeeAndApplicationName(@PathVariable Long id, @PathVariable String applicationName)
        throws URISyntaxException, Exception {

        List<EmployeeLoanAttachment> attachments = employeeLoanAttachmentRepository.findByEmployeeAndApplicationName(id, applicationName);
        String filepath="/backup/employeeLoan/";
        for(EmployeeLoanAttachment attachment:attachments){
            if(attachment.getFileContentName() !=null && attachment.getFileContentName().length()>0){
                attachment.setFile(AttachmentUtil.retriveAttachment(filepath,attachment.getFileContentName()));
            }
        }
        return attachments;
    }

    @RequestMapping(value = "/employeeLoanAttachments/loanAttachment/{employeeInfoId}/{applicationName}/{requisitionId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanAttachment> getByEmployeeAndAppNameAndRequisitionId(@PathVariable Long employeeInfoId, @PathVariable String applicationName,@PathVariable Long requisitionId)
        throws URISyntaxException, Exception {
        log.debug("REST request to get EmployeeLoanAttachment By Requisition ID : {}", requisitionId);
        List<EmployeeLoanAttachment> attachments = employeeLoanAttachmentRepository.findByEmployeeAndAppNameAndRequisitionId(employeeInfoId, applicationName,requisitionId);
        String filepath="/backup/employeeLoan/";
        for(EmployeeLoanAttachment attachment:attachments){
            if(attachment.getFileContentName() !=null && attachment.getFileContentName().length()>0){
                attachment.setFile(AttachmentUtil.retriveAttachment(filepath,attachment.getFileContentName()));
            }
        }
        return attachments;
    }
}
