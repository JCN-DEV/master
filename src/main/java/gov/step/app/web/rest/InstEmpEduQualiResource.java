package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmpEduQuali;
import gov.step.app.domain.InstEmployee;
import gov.step.app.repository.InstEmpEduQualiRepository;
import gov.step.app.repository.InstEmployeeRepository;
import gov.step.app.repository.search.InstEmpEduQualiSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstEmpEduQuali.
 */
@RestController
@RequestMapping("/api")
public class InstEmpEduQualiResource {

    private final Logger log = LoggerFactory.getLogger(InstEmpEduQualiResource.class);

    @Inject
    private InstEmpEduQualiRepository instEmpEduQualiRepository;

    @Inject
    private InstEmpEduQualiSearchRepository instEmpEduQualiSearchRepository;

    private RptJdbcDao rptJdbcDao;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    /**
     * POST  /instEmpEduQualis -> Create a new instEmpEduQuali.
     */
    @RequestMapping(value = "/instEmpEduQualis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpEduQuali> createInstEmpEduQuali(@RequestBody InstEmpEduQuali instEmpEduQuali) throws URISyntaxException, Exception {
        log.debug("REST request to save InstEmpEduQuali : {}", instEmpEduQuali);
        if (instEmpEduQuali.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmpEduQuali cannot already have an ID").body(null);
        }
        String filepath="/backup/teacher_info/";
        log.debug("file name test--"+instEmpEduQuali.getCertificateCopyName());
        log.debug("file image test--"+instEmpEduQuali.getCertificateCopy());
        if(instEmpEduQuali.getCertificateCopyName() !=null && instEmpEduQuali.getCertificateCopy() !=null){
            instEmpEduQuali.setCertificateCopyName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmpEduQuali.getCertificateCopyName().replace("/", "_"), instEmpEduQuali.getCertificateCopy()));
        }
        instEmpEduQuali.setCertificateCopy(null);

        InstEmpEduQuali result = instEmpEduQualiRepository.save(instEmpEduQuali);
        instEmpEduQualiSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmpEduQualis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmpEduQuali", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmpEduQualis -> Updates an existing instEmpEduQuali.
     */
    @RequestMapping(value = "/instEmpEduQualis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpEduQuali> updateInstEmpEduQuali(@RequestBody InstEmpEduQuali instEmpEduQuali) throws URISyntaxException, Exception {
        log.debug("REST request to update InstEmpEduQuali : {}", instEmpEduQuali);
        if (instEmpEduQuali.getId() == null) {
            return createInstEmpEduQuali(instEmpEduQuali);
        }

        String filepath="/backup/teacher_info/";
        InstEmpEduQuali prevInstEmpEduQuali1=instEmpEduQualiRepository.findOne(instEmpEduQuali.getId());
        log.debug("file name test--"+instEmpEduQuali.getCertificateCopyName());
        log.debug("file image test--"+instEmpEduQuali.getCertificateCopy());

       /* if(instEmployee.getNidImageName() !=null && instEmployee.getNidImage() !=null){

            if(previnstEmployee.getImageName() !=null && previnstEmployee.getImageName().length()>0){
                log.debug(" NidImageName replace trigger----------------------------------------------");
                instEmployee.setNidImageName(AttachmentUtil.replaceAttachment(filepath, previnstEmployee.getNidImageName(),instEmployee.getNidImageName().replace("/", "_"), instEmployee.getNidImage()));
            }else{
                instEmployee.setNidImageName(AttachmentUtil.saveAttachment(filepath, instEmployee.getNidImageName().replace("/", "_"), instEmployee.getNidImage()));
            }

        }*/


        if(instEmpEduQuali.getCertificateCopyName() !=null && instEmpEduQuali.getCertificateCopy() !=null){
            if(prevInstEmpEduQuali1.getCertificateCopyName() !=null && prevInstEmpEduQuali1.getCertificateCopyName().length()>0){
                instEmpEduQuali.setCertificateCopyName(AttachmentUtil.replaceAttachment(filepath,prevInstEmpEduQuali1.getCertificateCopyName(), instEmpEduQuali.getCertificateCopyName().replace("/", "_"), instEmpEduQuali.getCertificateCopy()));
            }else{
                instEmpEduQuali.setCertificateCopyName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, instEmpEduQuali.getCertificateCopyName().replace("/", "_"), instEmpEduQuali.getCertificateCopy()));
            }
        }
        instEmpEduQuali.setCertificateCopy(null);
        InstEmpEduQuali result = instEmpEduQualiRepository.save(instEmpEduQuali);
        instEmpEduQualiSearchRepository.save(instEmpEduQuali);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmpEduQuali", instEmpEduQuali.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmpEduQualis -> get all the instEmpEduQualis.
     */
    @RequestMapping(value = "/instEmpEduQualis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmpEduQuali>> getAllInstEmpEduQualis(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmpEduQuali> page = instEmpEduQualiRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmpEduQualis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmpEduQualis/:id -> get the "id" instEmpEduQuali.
     */
    @RequestMapping(value = "/instEmpEduQualis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpEduQuali> getInstEmpEduQuali(@PathVariable Long id) {
        log.debug("REST request to get InstEmpEduQuali : {}", id);
        return Optional.ofNullable(instEmpEduQualiRepository.findOne(id))
            .map(instEmpEduQuali -> new ResponseEntity<>(
                instEmpEduQuali,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instEmpEduQualis/:id -> delete the "id" instEmpEduQuali.
     */
    @RequestMapping(value = "/instEmpEduQualis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmpEduQuali(@PathVariable Long id) {
        log.debug("REST request to delete InstEmpEduQuali : {}", id);
        instEmpEduQualiRepository.delete(id);
        instEmpEduQualiSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmpEduQuali", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmpEduQualis/:query -> search for the instEmpEduQuali corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmpEduQualis/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmpEduQuali> searchInstEmpEduQualis(@PathVariable String query) {
        return StreamSupport
            .stream(instEmpEduQualiSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * get  /api/instEmpEduQualis/currentLogin -> search for the instEmpEduQuali corresponding
     * to the query.
     */
    @RequestMapping(value = "/instEmpEduQualis/currentLogin",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmpEduQuali> currentInstEmpEduQuali() throws Exception{
   // public List<InstEmpEduQuali> currentInstEmpEduQuali() {
        log.debug("REST request to get InstEmpSpouseInfo : {}");
        //return instEmpEduQualiRepository.findAllByLogin();
        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        List<InstEmpEduQuali> instEmpEduQualilist=null;
        /*if(instEmployeeresult !=null && instEmployeeresult.getId()>0){
            log.debug("--------------------------------employee result id"+instEmployeeresult.getId());
             instEmpEduQualilist=rptJdbcDao.findListByInstEmployeeId(instEmployeeresult.getId());
        }
        if(instEmpEduQualilist !=null && instEmpEduQualilist.size()>0){
            return instEmpEduQualilist;
        }else{
            return new ArrayList<>();
        }*/
        log.debug("-------------userName---------"+userName+"----------employee result id"+instEmployeeresult.getId());
        //return rptJdbcDao.findListByInstEmployeeId(instEmployeeresult.getId());
        instEmpEduQualilist=instEmpEduQualiRepository.findListByInstEmployeeId(instEmployeeresult.getId());
        /*for(InstEmpEduQuali instEmpEduQuali:instEmpEduQualilist){
            instEmpEduQuali.setCertificateCopy(AttachmentUtil.retriveAttachment("/backup/teacher_info/",instEmpEduQuali.getCertificateCopyName()));
            instEmpEduQuali.setCertificateCopyName(instEmpEduQuali.getCertificateCopyName().substring(0, (instEmpEduQuali.getCertificateCopyName().length() - 17)));
        }*/
        for(InstEmpEduQuali instEmpEduQuali:instEmpEduQualilist){
            if(instEmpEduQuali.getCertificateCopyName()!=null){
                instEmpEduQuali.setCertificateCopy(AttachmentUtil.retriveAttachment("/backup/teacher_info/",instEmpEduQuali.getCertificateCopyName()));
                //instEmpEduQuali.setCertificateCopyName(instEmpEduQuali.getCertificateCopyName().substring(0, (instEmpEduQuali.getCertificateCopyName().length() - 17)));
                instEmpEduQuali.setCertificateCopyName(instEmpEduQuali.getCertificateCopyName());
            }
        }
        return instEmpEduQualilist;

    }
}
