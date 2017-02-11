package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmplTraining;
import gov.step.app.domain.InstEmployee;
import gov.step.app.repository.InstEmplTrainingRepository;
import gov.step.app.repository.InstEmployeeRepository;
import gov.step.app.repository.search.InstEmplTrainingSearchRepository;
import gov.step.app.security.SecurityUtils;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstEmplTraining.
 */
@RestController
@RequestMapping("/api")
public class InstEmplTrainingResource {

    private final Logger log = LoggerFactory.getLogger(InstEmplTrainingResource.class);

    @Inject
    private InstEmplTrainingRepository instEmplTrainingRepository;

    @Inject
    private InstEmplTrainingSearchRepository instEmplTrainingSearchRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    /**
     * POST  /instEmplTrainings -> Create a new instEmplTraining.
     */
    @RequestMapping(value = "/instEmplTrainings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplTraining> createInstEmplTraining(@Valid @RequestBody InstEmplTraining instEmplTraining) throws URISyntaxException,Exception {
        log.debug("REST request to save InstEmplTraining : {}", instEmplTraining);
        if (instEmplTraining.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmplTraining cannot already have an ID").body(null);
        }

        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        instEmplTraining.setInstEmployee(instEmployeeresult);

        String filepath="/backup/teacher_info/";
        log.debug("file name test--"+instEmplTraining.getAttachmentName());
        log.debug("file image test--"+instEmplTraining.getAttachment());
        if(instEmplTraining.getAttachmentName() !=null && instEmplTraining.getAttachment() !=null){
            instEmplTraining.setAttachmentName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplTraining.getAttachmentName().replace("/", "_"), instEmplTraining.getAttachment()));
        }
        instEmplTraining.setAttachment(null);

        InstEmplTraining result = instEmplTrainingRepository.save(instEmplTraining);
        instEmplTrainingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmplTrainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmplTraining", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmplTrainings -> Updates an existing instEmplTraining.
     */
    @RequestMapping(value = "/instEmplTrainings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplTraining> updateInstEmplTraining(@Valid @RequestBody InstEmplTraining instEmplTraining) throws URISyntaxException, Exception {
        log.debug("REST request to update InstEmplTraining : {}", instEmplTraining);
        if (instEmplTraining.getId() == null) {
            return createInstEmplTraining(instEmplTraining);
        }
        String filepath="/backup/teacher_info/";
        log.debug("file name test--"+instEmplTraining.getAttachmentName());
        log.debug("file image test--"+instEmplTraining.getAttachment());
        InstEmplTraining prevInstEmplTraining= instEmplTrainingRepository.findOne(instEmplTraining.getId());
        /*if(instEmplTraining.getAttachmentName() !=null && instEmplTraining.getAttachment() !=null){
            instEmplTraining.setAttachmentName(AttachmentUtil.saveAttachment(filepath, instEmplTraining.getAttachmentName().replace("/", "_"), instEmplTraining.getAttachment()));
        }*/
        if(instEmplTraining.getAttachmentName() !=null && instEmplTraining.getAttachment() !=null){
            if(prevInstEmplTraining.getAttachmentName() !=null && prevInstEmplTraining.getAttachmentName().length()>0){
                instEmplTraining.setAttachmentName(AttachmentUtil.replaceAttachment(filepath, prevInstEmplTraining.getAttachmentName(), instEmplTraining.getAttachmentName().replace("/", "_"), instEmplTraining.getAttachment()));
            }else{
                instEmplTraining.setAttachmentName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmplTraining.getAttachmentName().replace("/", "_"), instEmplTraining.getAttachment()));
            }
        }
        instEmplTraining.setAttachment(null);

        InstEmplTraining result = instEmplTrainingRepository.save(instEmplTraining);
        instEmplTrainingSearchRepository.save(instEmplTraining);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmplTraining", instEmplTraining.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmplTrainings -> get all the instEmplTrainings.
     */
    @RequestMapping(value = "/instEmplTrainings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplTraining>> getAllInstEmplTrainings(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmplTraining> page = instEmplTrainingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplTrainings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmplTrainings/:id -> get the "id" instEmplTraining.
     */
    @RequestMapping(value = "/instEmplTrainings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplTraining> getInstEmplTraining(@PathVariable Long id) {
        log.debug("REST request to get InstEmplTraining : {}", id);
        return Optional.ofNullable(instEmplTrainingRepository.findOne(id))
            .map(instEmplTraining -> new ResponseEntity<>(
                instEmplTraining,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * GET  /instEmplTrainingsCurrent -> get the "id" instEmplTraining.
     */
    @RequestMapping(value = "/instEmplTrainingsCurrent",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplTraining> getInstEmplTrainingCurrent() throws Exception{
        log.debug("REST request to get InstEmplTraining : {}");

        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);

        List<InstEmplTraining> instEmpEduQualilist=instEmplTrainingRepository.findInstEmplTrainingsByEmployeeId(instEmployeeresult.getId());
/*
        for(InstEmplTraining instEmplTraining:instEmpEduQualilist){
            instEmplTraining.setAttachment(AttachmentUtil.retriveAttachment("/backup/teacher_info/", instEmplTraining.getAttachmentName()));
            instEmplTraining.setAttachmentName(instEmplTraining.getAttachmentName().substring(0, (instEmplTraining.getAttachmentName().length() - 17)));
        }*/
        for(InstEmplTraining instEmplTraining:instEmpEduQualilist){
            if(instEmplTraining.getAttachmentName()!=null){
                instEmplTraining.setAttachment(AttachmentUtil.retriveAttachment("/backup/teacher_info/", instEmplTraining.getAttachmentName()));
                //instEmplTraining.setAttachmentName(instEmplTraining.getAttachmentName().substring(0, (instEmplTraining.getAttachmentName().length() - 17)));
                instEmplTraining.setAttachmentName(instEmplTraining.getAttachmentName());
            }
        }

        //return instEmplTrainingRepository.findInstEmplTrainingsByEmployeeId(instEmployeeresult.getId());
        return instEmpEduQualilist;
    }
    /**
     * DELETE  /instEmplTrainings/:id -> delete the "id" instEmplTraining.
     */
    @RequestMapping(value = "/instEmplTrainings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmplTraining(@PathVariable Long id) {
        log.debug("REST request to delete InstEmplTraining : {}", id);
        instEmplTrainingRepository.delete(id);
        instEmplTrainingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmplTraining", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmplTrainings/:query -> search for the instEmplTraining corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmplTrainings/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplTraining> searchInstEmplTrainings(@PathVariable String query) {
        return StreamSupport
            .stream(instEmplTrainingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
