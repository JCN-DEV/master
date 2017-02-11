package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Employee;
import gov.step.app.domain.JpEmployee;
import gov.step.app.repository.JpEmployeeRepository;
import gov.step.app.repository.search.JpEmployeeSearchRepository;
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
 * REST controller for managing JpEmployee.
 */
@RestController
@RequestMapping("/api")
public class JpEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(JpEmployeeResource.class);

    @Inject
    private JpEmployeeRepository jpEmployeeRepository;

    @Inject
    private JpEmployeeSearchRepository jpEmployeeSearchRepository;

    /**
     * POST  /jpEmployees -> Create a new jpEmployee.
     */
    @RequestMapping(value = "/jpEmployees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployee> createJpEmployee(@Valid @RequestBody JpEmployee jpEmployee) throws URISyntaxException {
        log.debug("REST request to save JpEmployee : {}", jpEmployee);
        if (jpEmployee.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jpEmployee cannot already have an ID").body(null);
        }

        String filepath="/backup/jobportal/";


        //AttachmentUtil.saveAttachment(filepath,filename,attachment.getFile());
        if(jpEmployee.getPicture() !=null){
            String filename=jpEmployee.getPictureName();
            try {
                //jpEmployee.setPictureName(AttachmentUtil.saveAttachment(filepath, filename, jpEmployee.getPicture()));
                jpEmployee.setPictureName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, jpEmployee.getPicture()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //attachment.setFileContentName(attachment.getAttachmentCategory().getAttachmentName());
            jpEmployee.setPicture(null);
        }

        //AttachmentUtil.saveAttachment(filepath,filename,attachment.getFile());
        if(jpEmployee.getCv() !=null){
            String cvName=jpEmployee.getCvName();
            try {
                jpEmployee.setCvName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, cvName, jpEmployee.getCv()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //attachment.setFileContentName(attachment.getAttachmentCategory().getAttachmentName());
            jpEmployee.setCv(null);
        }
        JpEmployee result = jpEmployeeRepository.save(jpEmployee);
        jpEmployeeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jpEmployees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jpEmployee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jpEmployees -> Updates an existing jpEmployee.
     */
    @RequestMapping(value = "/jpEmployees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployee> updateJpEmployee(@Valid @RequestBody JpEmployee jpEmployee) throws URISyntaxException {
        log.debug("REST request to update JpEmployee : {}", jpEmployee);
        if (jpEmployee.getId() == null) {
            return createJpEmployee(jpEmployee);
        }
        String filepath="/backup/jobportal/";
        String filename=jpEmployee.getPictureName();
        String cvName=jpEmployee.getCvName();
        //AttachmentUtil.saveAttachment(filepath,filename,attachment.getFile());
        if(jpEmployee.getPicture() != null){
            try {
                //jpEmployee.setPictureName(AttachmentUtil.saveAttachment(filepath, filename, jpEmployee.getPicture()));
                jpEmployee.setPictureName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, jpEmployee.getPicture()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //attachment.setFileContentName(attachment.getAttachmentCategory().getAttachmentName());
            jpEmployee.setPicture(null);
        }
        jpEmployee.setPicture(null);
        //AttachmentUtil.saveAttachment(filepath,filename,attachment.getFile());
        if(jpEmployee.getCv() !=null){
            try {
                //jpEmployee.setCvName(AttachmentUtil.saveAttachment(filepath, cvName, jpEmployee.getCv()));
                jpEmployee.setCvName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, cvName, jpEmployee.getCv()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //attachment.setFileContentName(attachment.getAttachmentCategory().getAttachmentName());
            jpEmployee.setCv(null);
        }
        jpEmployee.setCv(null);
        JpEmployee result = jpEmployeeRepository.save(jpEmployee);
        jpEmployeeSearchRepository.save(jpEmployee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jpEmployee", jpEmployee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jpEmployees -> get all the jpEmployees.
     */
    @RequestMapping(value = "/jpEmployees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpEmployee>> getAllJpEmployees(Pageable pageable)
        throws URISyntaxException {
        Page<JpEmployee> page = jpEmployeeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpEmployees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpEmployees/:id -> get the "id" jpEmployee.
     */
    @RequestMapping(value = "/jpEmployees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployee> getJpEmployee(@PathVariable Long id) throws Exception {
        log.debug("REST request to get JpEmployee : {}", id);

        JpEmployee employee = jpEmployeeRepository.findOne(id);
        String filepath="/backup/jobportal/";

        if(employee.getPictureName() !=null && employee.getPictureName().length()>0){
            employee.setPicture(AttachmentUtil.retriveAttachment(filepath, employee.getPictureName()));
        }

        if(employee.getCvName() !=null && employee.getCvName().length()>0){
            employee.setCv(AttachmentUtil.retriveAttachment(filepath, employee.getCvName()));
        }

        return Optional.ofNullable(employee)
            .map(jpEmployee -> new ResponseEntity<>(
                jpEmployee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jpEmployees/:id -> delete the "id" jpEmployee.
     */
    @RequestMapping(value = "/jpEmployees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJpEmployee(@PathVariable Long id) {
        log.debug("REST request to delete JpEmployee : {}", id);
        jpEmployeeRepository.delete(id);
        jpEmployeeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jpEmployee", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jpEmployees/:query -> search for the jpEmployee corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jpEmployees/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpEmployee> searchJpEmployees(@PathVariable String query) {
        return StreamSupport
            .stream(jpEmployeeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    /**
     * GET  /employees/my -> get my employee.
     */
    @RequestMapping(value = "/jpEmployees/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployee> getMyEmployee() throws Exception {

        JpEmployee jpEmployee = jpEmployeeRepository.findOneByEmployeeUserIsCurrentUser();
        String filepath="/backup/jobportal/";
        if(jpEmployee != null){
            if(jpEmployee.getPictureName() !=null && jpEmployee.getPictureName().length()>0){
                jpEmployee.setPicture(AttachmentUtil.retriveAttachment(filepath, jpEmployee.getPictureName()));
            }

            if(jpEmployee.getCvName() !=null && jpEmployee.getCvName().length()>0){
                jpEmployee.setCv(AttachmentUtil.retriveAttachment(filepath, jpEmployee.getCvName()));
            }
        }

        return Optional.ofNullable(jpEmployee)
            .map(employee -> new ResponseEntity<>(
                employee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
