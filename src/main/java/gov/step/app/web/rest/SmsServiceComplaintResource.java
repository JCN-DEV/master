package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SmsServiceAssign;
import gov.step.app.domain.SmsServiceComplaint;
import gov.step.app.repository.SmsServiceAssignRepository;
import gov.step.app.repository.SmsServiceComplaintRepository;
import gov.step.app.repository.search.SmsServiceComplaintSearchRepository;
import gov.step.app.service.constnt.ServiceManagementConstants;
import gov.step.app.service.util.MiscUtilities;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.Calendar;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SmsServiceComplaint.
 */
@RestController
@RequestMapping("/api")
public class SmsServiceComplaintResource {

    private final Logger log = LoggerFactory.getLogger(SmsServiceComplaintResource.class);

    @Inject
    private SmsServiceComplaintRepository smsServiceComplaintRepository;

    @Inject
    private SmsServiceComplaintSearchRepository smsServiceComplaintSearchRepository;

    @Inject
    private SmsServiceAssignRepository smsServiceAssignRepository;

    MiscUtilities miscUtil = new MiscUtilities();

    /**
     * POST  /smsServiceComplaints -> Create a new smsServiceComplaint.
     */
    @RequestMapping(value = "/smsServiceComplaints",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceComplaint> createSmsServiceComplaint(@Valid @RequestBody SmsServiceComplaint smsServiceComplaint) throws URISyntaxException {
        log.debug("REST request to save SmsServiceComplaint : {}", smsServiceComplaint);
        if (smsServiceComplaint.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("smsServiceComplaint", "idexists", "A new smsServiceComplaint cannot already have an ID")).body(null);
        }

         // Saving file into directory
        saveImageToDirectory(smsServiceComplaint);
        smsServiceComplaint.setComplaintDoc(new byte[2]);
        smsServiceComplaint.setComplaintCode(miscUtil.getRandomUniqueId());
        log.debug("newFile: "+smsServiceComplaint.getComplaintDocFileName()+", compCode: "+smsServiceComplaint.getComplaintCode());

        //Date date = new Date();
        //LocalDate localDate = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) );

        SmsServiceComplaint result = smsServiceComplaintRepository.save(smsServiceComplaint);
        smsServiceComplaintSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/smsServiceComplaints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("smsServiceComplaint", result.getId().toString()))
            .body(result);
    }

    public void saveImageToDirectory(SmsServiceComplaint smsServiceComplaint)
    {
        OutputStream fileout = null;
        try
        {
            if(smsServiceComplaint.getComplaintDocContentType() == null)
            {
                smsServiceComplaint.setComplaintDocFileName("");
                smsServiceComplaint.setComplaintDocContentType("");
                return;
            }

            byte[] fileBytes = smsServiceComplaint.getComplaintDoc();
            String contentType = smsServiceComplaint.getComplaintDocContentType();

            String extension = FilenameUtils.getExtension(smsServiceComplaint.getComplaintDocFileName());

            String fileName = miscUtil.getRandomFileName()+"."+extension;
            smsServiceComplaint.setComplaintDocFileName(fileName);

            int fileBytesLen = fileBytes.length;

            fileout = new FileOutputStream(new File(ServiceManagementConstants.ATTACHMENT_FILE_DIR + File.separator+ fileName));
            log.debug("fileName: "+fileName+", contentType: "+contentType+", byteLen: "+fileBytesLen+", actualFile: "+smsServiceComplaint.getComplaintDocFileName()+", ext: "+extension);
            fileout.write(fileBytes);
            fileout.close();

            log.debug("File being uploaded to {1}", new Object[]{fileName});
        }
        catch (FileNotFoundException fne)
        {
            log.error("FileNotFoundException: "+fne.getMessage());
        }
        catch (IOException ioex)
        {
            log.error("IOException: "+ioex.getMessage());
        }
    }

    /**
     * PUT  /smsServiceComplaints -> Updates an existing smsServiceComplaint.
     */
    @RequestMapping(value = "/smsServiceComplaints",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceComplaint> updateSmsServiceComplaint(@Valid @RequestBody SmsServiceComplaint smsServiceComplaint) throws URISyntaxException {
        log.debug("REST request to update SmsServiceComplaint : {}", smsServiceComplaint);
        if (smsServiceComplaint.getId() == null) {
            return createSmsServiceComplaint(smsServiceComplaint);
        }
        SmsServiceComplaint result = smsServiceComplaintRepository.save(smsServiceComplaint);
        smsServiceComplaintSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("smsServiceComplaint", smsServiceComplaint.getId().toString()))
            .body(result);
    }

    /**
     * GET  /smsServiceComplaints -> get all the smsServiceComplaints.
     */
    @RequestMapping(value = "/smsServiceComplaints",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SmsServiceComplaint>> getAllSmsServiceComplaints(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SmsServiceComplaints custom");
        Page<SmsServiceComplaint> page = smsServiceComplaintRepository.findByActiveStatus(true, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/smsServiceComplaints");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /userServiceComplaints/ -> get the all service complaints by the logged in user.
     */
    @RequestMapping(value = "/userServiceComplaints",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceComplaint> getServiceComplaintByLoggedInUser()
        throws URISyntaxException
    {
        log.debug("REST request to get userServiceComplaints");
        List<SmsServiceComplaint> serviceComplaints = null;
        try
        {
            SmsServiceAssign smsAssign = null;

            List<SmsServiceAssign> serviceAssignList = smsServiceAssignRepository.findListByEmployeeUser();
            serviceComplaints = new ArrayList<SmsServiceComplaint>();
            for(SmsServiceAssign serviceAssign: serviceAssignList)
            {
                serviceComplaints.addAll(smsServiceComplaintRepository.findByDepartment(serviceAssign.getSmsServiceDepartment().getId()));
            }
        }
        catch(Exception ex)
        {
            log.error("userservicelist", ex);
        }

        return  serviceComplaints;
    }

    /**
     * GET  /smsServiceComplaints/:id -> get the "id" smsServiceComplaint.
     */
    @RequestMapping(value = "/smsServiceComplaints/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceComplaint> getSmsServiceComplaint(@PathVariable Long id) {
        log.debug("REST request to get SmsServiceComplaint : {}", id);
        SmsServiceComplaint smsServiceComplaint = smsServiceComplaintRepository.findOne(id);

        //read file from local drive
        getFileBytes(smsServiceComplaint);

        return Optional.ofNullable(smsServiceComplaint)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private void getFileBytes(SmsServiceComplaint smsServiceComplaint)
    {
        byte[] data = null;
        try
        {
            String filePath = ServiceManagementConstants.ATTACHMENT_FILE_DIR + File.separator+ smsServiceComplaint.getComplaintDocFileName();
            log.debug("readfile: "+filePath);
            Path path = Paths.get(filePath);
            data = Files.readAllBytes(path);

            smsServiceComplaint.setComplaintDoc(data);
        }
        catch(Exception ex)
        {
            log.error("ReadFileMsg: "+ex.getMessage());
        }
    }

    /**
     * DELETE  /smsServiceComplaints/:id -> close the "id" smsServiceComplaint.
     */
    @RequestMapping(value = "/smsServiceComplaints/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSmsServiceComplaint(@PathVariable Long id) {
        log.debug("REST request to close SmsServiceComplaint : {}", id);
        smsServiceComplaintRepository.updateComplaintByActiveStatus(id);
        //smsServiceComplaintSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("smsServiceComplaint", id.toString())).build();
    }

    /**
     * SEARCH  /_search/smsServiceComplaints/:query -> search for the smsServiceComplaint corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/smsServiceComplaints/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceComplaint> searchSmsServiceComplaints(@PathVariable String query) {
        log.debug("REST request to search SmsServiceComplaints for query {}", query);
        return StreamSupport
            .stream(smsServiceComplaintSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /smsServiceComplaints/department/:id -> get the all complaints by department.
     */
    @RequestMapping(value = "/smsServiceComplaints/department/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceComplaint> getServiceComplaintByDepartment(@PathVariable Long id)
        throws URISyntaxException {
        List<SmsServiceComplaint> serviceComplaints = smsServiceComplaintRepository.findByDepartment(id);
        return  serviceComplaints;

    }

}
