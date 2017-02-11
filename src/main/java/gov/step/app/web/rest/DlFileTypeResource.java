package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlContUpld;
import gov.step.app.domain.DlFileType;
import gov.step.app.domain.DlSourceSetUp;
import gov.step.app.repository.DlFileTypeRepository;
import gov.step.app.repository.search.DlFileTypeSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.AttachmentUtil;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DlFileType.
 */
@RestController
@RequestMapping("/api")
public class DlFileTypeResource {

    private final Logger log = LoggerFactory.getLogger(DlFileTypeResource.class);
    @Autowired
    ServletContext context;
    @Inject
    private DlFileTypeRepository dlFileTypeRepository;

    @Inject
    private DlFileTypeSearchRepository dlFileTypeSearchRepository;
    String filepath = "/backup/dlImages/";

    /**
     * POST  /dlFileTypes -> Create a new dlFileType.
     */
    @RequestMapping(value = "/dlFileTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlFileType> createDlFileType(@RequestBody DlFileType dlFileType) throws URISyntaxException, Exception {
        log.debug("REST request to save DlFileType : {}", dlFileType);
        if (dlFileType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlFileType cannot already have an ID").body(null);
        }

        String filename = "dlFileType";

        String extension = ".png";

        String fileContent = dlFileType.getFileImgContentType();


        if (dlFileType.getFileImgContentType() != null && dlFileType.getFileImgContentType().equals("application/pdf")) {
            dlFileType.setFileImgContentType(dlFileType.getFileImgName() + ".pdf");

            extension = ".pdf";
        } else {
            extension = ".png";
        }


        if (dlFileType.getFileImg() != null) {
            dlFileType.setFileImgName(AttachmentUtil.saveDlAttachment(filepath, null, extension, dlFileType.getFileImg()));

        }


        String userName = SecurityUtils.getCurrentUserLogin();

        if (userName == "anonymousUser") {
            dlFileType.setFileImg(null);

            System.out.println("====================================" + userName);

            DlFileType result = dlFileTypeRepository.save(dlFileType);
            dlFileTypeSearchRepository.save(result);
            return ResponseEntity.created(new URI("/api/dlFileTypes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("dlFileType", result.getId().toString()))
                .body(result);
        } else {

            dlFileType.setFileImg(null);

            DlFileType result = dlFileTypeRepository.save(dlFileType);
            dlFileTypeSearchRepository.save(result);


            return ResponseEntity.created(new URI("/api/dlFileTypes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("dlFileType", result.getId().toString()))
                .body(result);
        }


    }

    /**
     * PUT  /dlFileTypes -> Updates an existing dlFileType.
     */
    @RequestMapping(value = "/dlFileTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlFileType> updateDlFileType(@RequestBody DlFileType dlFileType) throws URISyntaxException, Exception {
        log.debug("REST request to update DlFileType : {}", dlFileType);
        if (dlFileType.getId() == null) {
            return createDlFileType(dlFileType);
        }
        DlFileType result = dlFileTypeRepository.save(dlFileType);
        dlFileTypeSearchRepository.save(dlFileType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlFileType", dlFileType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlFileTypes -> get all the dlFileTypes.
     */
    @RequestMapping(value = "/dlFileTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlFileType>> getAllDlFileTypes(Pageable pageable)
        throws Exception {
        Page<DlFileType> page = dlFileTypeRepository.findAll(pageable);
        for (DlFileType each : page) {

            if (each.getFileImgName() != null && AttachmentUtil.retriveAttachment(filepath, each.getFileImgName()) != null) {
                each.setFileImg(AttachmentUtil.retriveAttachment(filepath, each.getFileImgName()));
            }
            /*if(each.getContentName() != null && AttachmentUtil.retriveAttachment(filepath, each.getContentName()) != null){
                each.setContent(AttachmentUtil.retriveAttachment(filepath, each.getContentName()));
            }*/

        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlFileTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/dlFileType/allFileType",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlFileType>> activeFileType(Pageable pageable)
        throws Exception {
        Page<DlFileType> page = dlFileTypeRepository.activeFileType(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "api/dlFileType/allFileType");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /dlFileTypes/:id -> get the "id" dlFileType.
     */
    @RequestMapping(value = "/dlFileTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlFileType> getDlFileType(@PathVariable Long id) {
        log.debug("REST request to get DlFileType : {}", id);
        return Optional.ofNullable(dlFileTypeRepository.findOne(id))
            .map(dlFileType -> new ResponseEntity<>(
                dlFileType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /* @RequestMapping(value = "/dlFileTypes/dlFileTypeUnique/",
         method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
     @Timed
     public ResponseEntity<java.util.Map> getDlFileTypeUnique( @RequestParam String value) {
         log.debug("REST request to get cmsTrade by code : {}", value);
         Optional<DlFileType>dlFileType = dlFileTypeRepository.findOneByfileType(value);
         java.util.Map map =new HashMap();
         map.put("value",value);
         if(Optional.empty().equals(dlFileType)){
             map.put("isValid",true);
             return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
         }else{
             map.put("isValid",false);
             return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
         }
     }*/
    @RequestMapping(value = "/dlFileTypes/dlFileTypeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getDlFileTypeUnique(@RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<DlContUpld> dlFileType = dlFileTypeRepository.findOneByfileType(value);
        java.util.Map map = new HashMap();
        map.put("value", value);
        if (Optional.empty().equals(dlFileType)) {
            map.put("isValid", true);
            return new ResponseEntity<java.util.Map>(map, HttpStatus.OK);
        } else {
            map.put("isValid", false);
            return new ResponseEntity<java.util.Map>(map, HttpStatus.OK);
        }
    }


    /**
     * DELETE  /dlFileTypes/:id -> delete the "id" dlFileType.
     */
    @RequestMapping(value = "/dlFileTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlFileType(@PathVariable Long id) {
        log.debug("REST request to delete DlFileType : {}", id);
        dlFileTypeRepository.delete(id);
        dlFileTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlFileType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlFileTypes/:query -> search for the dlFileType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlFileTypes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlFileType> searchDlFileTypes(@PathVariable String query) {
        return StreamSupport
            .stream(dlFileTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
