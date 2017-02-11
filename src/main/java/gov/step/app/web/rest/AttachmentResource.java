package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Attachment;
import gov.step.app.repository.AttachmentRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.AttachmentSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.AttachmentUtil;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import org.apache.commons.io.IOUtils;
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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Attachment.
 */
@RestController
@RequestMapping("/api")
public class AttachmentResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentResource.class);

    @Inject
    private AttachmentRepository attachmentRepository;

    @Inject
    private AttachmentSearchRepository attachmentSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /attachments -> Create a new attachment.
     */
    @RequestMapping(value = "/attachments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attachment> createAttachment(@Valid @RequestBody Attachment attachment) throws URISyntaxException,Exception {
        log.debug("REST request to save Attachment : {}", attachment);
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ::::");
        log.debug(attachment.getInstEmployee() != null ? "NO Employee found ":attachment.getInstEmployee().toString());
        if (attachment.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new attachment cannot already have an ID").body(null);
        }

        attachment.setCreateDate(LocalDate.now());
        attachment.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        String filepath="/backup/mpo/";
         //AttachmentUtil.saveAttachment(filepath,filename,attachment.getFile());
        if(attachment.getFile() !=null){
            String filename=attachment.getAttachmentCategory().getAttachmentName().replace("/", "_")+attachment.getFileName().replace("/", "_");
            //attachment.setFileContentName(AttachmentUtil.saveAttachment(filepath,filename,attachment.getFile()));
            attachment.setFileContentName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, attachment.getFile()));
        }else{
            //attachment.setFileContentName(attachment.getAttachmentCategory().getAttachmentName());
            attachment.setFileContentName(null);
        }

        attachment.setFile(null);
        Attachment result = attachmentRepository.save(attachment);
        attachmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("attachment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attachments -> Updates an existing attachment.
     */
    @RequestMapping(value = "/attachments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attachment> updateAttachment(@Valid @RequestBody Attachment attachment) throws URISyntaxException,Exception {
        log.debug("REST request to update Attachment : {}", attachment);
        if (attachment.getId() == null) {
            return createAttachment(attachment);
        }
        attachment.setUpdateDate(LocalDate.now());
        attachment.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        String filepath="/backup/mpo/";
        if(attachment.getFile() !=null){
            String filename=attachment.getAttachmentCategory().getAttachmentName().replace("/", "_")+attachment.getFileName().replace("/", "_");
            attachment.setFileContentName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, attachment.getFile()));
        }else{
            attachment.setFileContentName(null);
        }

        Attachment result = attachmentRepository.save(attachment);
        attachmentSearchRepository.save(attachment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("attachment", attachment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attachments -> get all the attachments.
     */
    @RequestMapping(value = "/attachments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Attachment>> getAllAttachments(Pageable pageable)
        throws URISyntaxException {
        Page<Attachment> page = attachmentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attachments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /attachments/employee/:id -> get the all attachment by employee
     */
    @RequestMapping(value = "/attachments/employee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Attachment> getAttachmentByEmployee(@PathVariable Long id)
        throws URISyntaxException, Exception {
        List<Attachment> attachments = attachmentRepository.findByEmployee(id);

        String filepath="/backup/mpo/";
        for(Attachment attachment:attachments){
            if(attachment.getFileContentName() !=null && attachment.getFileContentName().length()>0){
                attachment.setFile(AttachmentUtil.retriveAttachment(filepath,attachment.getFileContentName()));
            }
        }
        //List<Attachment> attachments = attachmentRepository.findByEmployeeAndApplicationName(id);
        return attachments;
    }

    /**
     * GET  /attachments/employee/:id -> get the all attachment by employee
     */
    @RequestMapping(value = "/attachments/employee/{id}/{attachmentName}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Attachment> getAttachmentByEmployeeAndApplicationName(@PathVariable Long id, @PathVariable String attachmentName)
        throws URISyntaxException, Exception {
       // List<Attachment> attachments = attachmentRepository.findByEmployee(id);
        List<Attachment> attachments = attachmentRepository.findByEmployeeAndApplicationName(id, attachmentName);
        String filepath="/backup/mpo/";
        for(Attachment attachment:attachments){
            if(attachment.getFileContentName() !=null && attachment.getFileContentName().length()>0){
                attachment.setFile(AttachmentUtil.retriveAttachment(filepath,attachment.getFileContentName()));
            }
        }
        return attachments;
    }
    /**
     * GET  /attachments/:id -> get the "id" attachment.
     */
    @RequestMapping(value = "/attachments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attachment> getAttachment(@PathVariable Long id) {
        log.debug("REST request to get Attachment : {}", id);
        return Optional.ofNullable(attachmentRepository.findOne(id))
            .map(attachment -> new ResponseEntity<>(
                attachment,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /attachments/:id -> delete the "id" attachment.
     */
    @RequestMapping(value = "/attachments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        log.debug("REST request to delete Attachment : {}", id);
        attachmentRepository.delete(id);
        attachmentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("attachment", id.toString())).build();
    }

    /**
     * SEARCH  /_search/attachments/:query -> search for the attachment corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/attachments/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Attachment> searchAttachments(@PathVariable String query) {
        return StreamSupport
            .stream(attachmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    @RequestMapping(value = "/attachmentByname/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attachment> getAttachMentFileByName(@RequestBody Attachment attachmentFile) throws URISyntaxException,Exception {
        log.debug("REST request to save Attachment : {}", attachmentFile);
        /*if (name == null || name.equals("")) {
            return null;
        }*/
        //Attachment attachmentFile=attachmentRepository.findOne(id);
        String filepath="/backup/mpo/";
        String filename=attachmentFile.getFileContentName();
        attachmentFile.setFile(AttachmentUtil.retriveAttachment(filepath,filename));

        /*Attachment result = attachmentRepository.save(attachment);
        attachmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("attachment", result.getId().toString()))
            .body(result);*/
        /*if(file!=null){
            return new ResponseEntity(file,HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }*/
        return Optional.ofNullable(attachmentFile)
            .map(attachment -> new ResponseEntity<>(
                attachment,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
