/*
package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsSyllabus;
import gov.step.app.repository.CmsSyllabusRepository;
import gov.step.app.repository.search.CmsSyllabusSearchRepository;
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

*/
/**
 * REST controller for managing CmsSyllabus.
 *//*

@RestController
@RequestMapping("/api")
public class CmsSyllabusResource {

    private final Logger log = LoggerFactory.getLogger(CmsSyllabusResource.class);

    @Inject
    private CmsSyllabusRepository cmsSyllabusRepository;

    @Inject
    private CmsSyllabusSearchRepository cmsSyllabusSearchRepository;

    */
/**
     * POST  /cmsSyllabuss -> Create a new cmsSyllabus.
     *//*

    @RequestMapping(value = "/cmsSyllabuss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> createCmsSyllabus(@Valid @RequestBody CmsSyllabus cmsSyllabus) throws URISyntaxException {
        log.debug("REST request to save CmsSyllabus : {}", cmsSyllabus);
        if (cmsSyllabus.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsSyllabus cannot already have an ID").body(null);
        }
        CmsSyllabus result = cmsSyllabusRepository.save(cmsSyllabus);
        cmsSyllabusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsSyllabuss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsSyllabus", result.getId().toString()))
            .body(result);
    }

    */
/**
     * PUT  /cmsSyllabuss -> Updates an existing cmsSyllabus.
     *//*

    @RequestMapping(value = "/cmsSyllabuss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> updateCmsSyllabus(@Valid @RequestBody CmsSyllabus cmsSyllabus) throws URISyntaxException {
        log.debug("REST request to update CmsSyllabus : {}", cmsSyllabus);
        if (cmsSyllabus.getId() == null) {
            return createCmsSyllabus(cmsSyllabus);
        }
        CmsSyllabus result = cmsSyllabusRepository.save(cmsSyllabus);
        cmsSyllabusSearchRepository.save(cmsSyllabus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsSyllabus", cmsSyllabus.getId().toString()))
            .body(result);
    }

    */
/**
     * GET  /cmsSyllabuss -> get all the cmsSyllabuss.
     *//*

    @RequestMapping(value = "/cmsSyllabuss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSyllabus>> getAllCmsSyllabuss(Pageable pageable)
        throws URISyntaxException {
        Page<CmsSyllabus> page = cmsSyllabusRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSyllabuss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    */
/**
     * GET  /cmsSyllabuss/:id -> get the "id" cmsSyllabus.
     *//*

    @RequestMapping(value = "/cmsSyllabuss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> getCmsSyllabus(@PathVariable Long id) {
        log.debug("REST request to get CmsSyllabus : {}", id);
        return Optional.ofNullable(cmsSyllabusRepository.findOne(id))
            .map(cmsSyllabus -> new ResponseEntity<>(
                cmsSyllabus,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

     */
/**
     * GET  /cmsSyllabuss/getCmsSyllabusByNameAndVersion/:cmsCurriculumId/:name/:version -> get the "cmsCurriculumId", "name", "version" cmsSyllabus.
     *//*

    @RequestMapping(value = "/cmsSyllabuss/getCmsSyllabusByNameAndVersion/{cmsCurriculumId}/{name}/{version}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> getCmsSyllabusByNameAndVersion(@PathVariable Long cmsCurriculumId, @PathVariable String name, @PathVariable String version) {
        log.debug("REST request to get CmsSyllabus by cmsCurriculumId, version, name, cmsCurriculumId : {}, version : {}, name : {}", cmsCurriculumId, name,version);
        return Optional.ofNullable(cmsSyllabusRepository.findOneByNameAndVersion(cmsCurriculumId,  version,name))
            .map(cmsSyllabus -> new ResponseEntity<>(
                cmsSyllabus,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    */
/**
     * DELETE  /cmsSyllabuss/:id -> delete the "id" cmsSyllabus.
     *//*

    @RequestMapping(value = "/cmsSyllabuss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsSyllabus(@PathVariable Long id) {
        log.debug("REST request to delete CmsSyllabus : {}", id);
        cmsSyllabusRepository.delete(id);
        cmsSyllabusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsSyllabus", id.toString())).build();
    }

    */
/**
     * SEARCH  /_search/cmsSyllabuss/:query -> search for the cmsSyllabus corresponding
     * to the query.
     *//*

    @RequestMapping(value = "/_search/cmsSyllabuss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsSyllabus> searchCmsSyllabuss(@PathVariable String query) {
        return StreamSupport
            .stream(cmsSyllabusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
*/


package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsCurriculum;
import gov.step.app.domain.CmsSyllabus;
import gov.step.app.domain.CmsTrade;
import gov.step.app.domain.SmsServiceComplaint;
import gov.step.app.repository.CmsSyllabusRepository;
import gov.step.app.repository.search.CmsSyllabusSearchRepository;
import gov.step.app.service.constnt.ServiceManagementConstants;
import gov.step.app.service.util.MiscUtilities;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.AttachmentUtil;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CmsSyllabus.
 */
@RestController
@RequestMapping("/api")
public class CmsSyllabusResource {

    private final Logger log = LoggerFactory.getLogger(CmsSyllabusResource.class);

    @Autowired
    ServletContext context;

    @Inject
    private CmsSyllabusRepository cmsSyllabusRepository;

    @Inject
    private CmsSyllabusSearchRepository cmsSyllabusSearchRepository;
    MiscUtilities miscUtil = new MiscUtilities();
    @Inject
    private RptJdbcDao rptJdbcDao;

    /**
     * POST  /cmsSyllabuss -> Create a new cmsSyllabus.
     */
    /*@RequestMapping(value = "/cmsSyllabuss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> createCmsSyllabus(@Valid @RequestBody CmsSyllabus cmsSyllabus) throws URISyntaxException {
        log.debug("REST request to save CmsSyllabus : {}", cmsSyllabus);
        if (cmsSyllabus.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsSyllabus cannot already have an ID").body(null);
        }*/
    @RequestMapping(value = "/cmsSyllabuss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> createCmsSyllabus(@Valid @RequestBody CmsSyllabus cmsSyllabus) throws URISyntaxException {
        log.debug("REST request to save CmsSyllabus : {}", cmsSyllabus);
        if (cmsSyllabus.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsSyllabus cannot already have an ID").body(null);
        }

        // Saving file into directory
     /*   saveImageToDirectory(cmsSyllabus);
        cmsSyllabus.setSyllabus(new byte[2]);
        cmsSyllabus.setComplaintCode(miscUtil.getRandomUniqueId());
        log.debug("newFile: "+cmsSyllabus.getName()+", compCode: "+cmsSyllabus.getComplaintCode());
*/
      /*  CmsSyllabus result = cmsSyllabusRepository.save(cmsSyllabus);
        cmsSyllabusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsSyllabuss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsSyllabus", result.getId().toString()))
            .body(result);
    }*/
        //String filepath="/backup/";
        String filepath = context.getRealPath("/") + "assets/qrcodefiles/";

        String filename="cmsSyllabus";
        //AttachmentUtil.saveAttachment(filepath,filename,attachment.getFile());

        String extension = ".png";
        if(cmsSyllabus.getSyllabus() != null){
            String fileContent = cmsSyllabus.getSyllabusContentType();
            if(fileContent.equals("application/pdf")){
                cmsSyllabus.setSyllabusContent(cmsSyllabus.getName() + ".pdf");
                extension = ".pdf";
            }
            else {
                cmsSyllabus.setSyllabusContent(cmsSyllabus.getName() + ".png");
            }

            try {
                cmsSyllabus.setName(AttachmentUtil.saveAttachment(filepath, filename,extension, cmsSyllabus.getSyllabus()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        cmsSyllabus.setSyllabus(null);

        System.out.println(cmsSyllabus);
        CmsSyllabus result = cmsSyllabusRepository.save(cmsSyllabus);
        cmsSyllabusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsSyllabuss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsSyllabus", result.getId().toString()))
            .body(result);
    }


   /* public void saveImageToDirectory(CmsSyllabus cmsSyllabus)
    {
        OutputStream fileout = null;
        try
        {
            if(cmsSyllabus.getSyllabusContentType() == null)
            {
                cmsSyllabus.setName("");
                cmsSyllabus.setSyllabusContentType("");
                return;
            }

            byte[] fileBytes = cmsSyllabus.getSyllabus();
            String contentType = cmsSyllabus.getSyllabusContentType();

            String extension = FilenameUtils.getExtension(cmsSyllabus.getName());

            String fileName = miscUtil.getRandomFileName()+"."+extension;
            cmsSyllabus.setName(fileName);

            int fileBytesLen = fileBytes.length;

            fileout = new FileOutputStream(new File(ServiceManagementConstants.ATTACHMENT_FILE_DIR + File.separator+ fileName));
            log.debug("fileName: "+fileName+", contentType: "+contentType+", byteLen: "+fileBytesLen+", actualFile: "+cmsSyllabus.getName()+", ext: "+extension);
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

*/

    @RequestMapping(value = "/cmsSyllabuss/findAllcmsSyllabusByOrderID",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSyllabus>> findAllcmsCurriculumByOrderID(Pageable pageable) throws Exception{
    /*public ResponseEntity<gov.step.app.domain.CmsCurriculum> findAllcmsCurriculumByOrderID() {*/
        log.debug("REST request to get CmsCurriculum order by ID");/*
        return Optional.ofNullable(cmsCurriculumRepository.findAllcmsCurriculumByOrderID())
            .map(cmsCurriculum -> new ResponseEntity<>(
                cmsCurriculum,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        Page<CmsSyllabus> page = cmsSyllabusRepository.findAllcmsSyllabusByOrderID(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/cmsSyllabuss/findAllcmsSyllabusByOrderID");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * PUT  /cmsSyllabuss -> Updates an existing cmsSyllabus.
     */
    @RequestMapping(value = "/cmsSyllabuss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> updateCmsSyllabus(@Valid @RequestBody CmsSyllabus cmsSyllabus) throws URISyntaxException {
        log.debug("REST request to update CmsSyllabus : {}", cmsSyllabus);
        if (cmsSyllabus.getId() == null) {
            return createCmsSyllabus(cmsSyllabus);
        }
        CmsSyllabus result = cmsSyllabusRepository.save(cmsSyllabus);
        cmsSyllabusSearchRepository.save(cmsSyllabus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsSyllabus", cmsSyllabus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cmsSyllabuss -> get all the cmsSyllabuss.
     */
    @RequestMapping(value = "/cmsSyllabuss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSyllabus>> getAllCmsSyllabuss(Pageable pageable)
        throws URISyntaxException {
        Page<CmsSyllabus> page = cmsSyllabusRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSyllabuss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cmsSyllabuss/:id -> get the "id" cmsSyllabus.
     */
    @RequestMapping(value = "/cmsSyllabuss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> getCmsSyllabus(@PathVariable Long id) {
        log.debug("REST request to get CmsSyllabus : {}", id);
        return Optional.ofNullable(cmsSyllabusRepository.findOne(id))
            .map(cmsSyllabus -> new ResponseEntity<>(
                cmsSyllabus,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /cmsSyllabuss/getCmsSyllabusByNameAndVersion/:cmsCurriculumId/:name/:version -> get the "cmsCurriculumId", "name", "version" cmsSyllabus.
     */
    @RequestMapping(value = "/cmsSyllabuss/getCmsSyllabusByNameAndVersion/{cmsCurriculumId}/{name}/{version}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> getCmsSyllabusByNameAndVersion(@PathVariable Long cmsCurriculumId, @PathVariable String name, @PathVariable String version) {
        log.debug("REST request to get CmsSyllabus by cmsCurriculumId, version, name, cmsCurriculumId : {}, version : {}, name : {}", cmsCurriculumId, name,version);
        return Optional.ofNullable(cmsSyllabusRepository.findOneByNameAndVersion(cmsCurriculumId,  version,name))
            .map(cmsSyllabus -> new ResponseEntity<>(
                cmsSyllabus,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/cmsSyllabuss/cmsCurriculum/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSyllabus>> getAllCmsSyllabus(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<CmsSyllabus> page = cmsSyllabusRepository.findAllSyllabusByCurriculum(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSyllabuss/cmsCurriculum");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * DELETE  /cmsSyllabuss/:id -> delete the "id" cmsSyllabus.
     */
    @RequestMapping(value = "/cmsSyllabuss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsSyllabus(@PathVariable Long id) {
        log.debug("REST request to delete CmsSyllabus : {}", id);
        cmsSyllabusRepository.delete(id);
        cmsSyllabusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsSyllabus", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cmsSyllabuss/:query -> search for the cmsSyllabus corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cmsSyllabuss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsSyllabus> searchCmsSyllabuss(@PathVariable String query) {
        return StreamSupport
            .stream(cmsSyllabusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/cmsSyllabuss/findCustomColumnOfCmsSyllabuss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getAppList() {

        List<Map<String,Object>> listpRpt = rptJdbcDao.findCustomColumnOfCmsSyllabuss();

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(listpRpt.size() + " List 0 index Data: " + listpRpt.get(0));

        return listpRpt;

    }
}

