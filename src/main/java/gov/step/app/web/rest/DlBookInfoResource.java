package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlBookInfo;
import gov.step.app.domain.DlBookIssue;
import gov.step.app.domain.DlContCatSet;
import gov.step.app.domain.DlFileType;
import gov.step.app.repository.DlBookInfoRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.SisStudentInfoRepository;
import gov.step.app.repository.search.DlBookInfoSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.*;
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
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DlBookInfo.
 */
@RestController
@RequestMapping("/api")
public class DlBookInfoResource {

    private final Logger log = LoggerFactory.getLogger(DlBookInfoResource.class);

    @Inject
    private DlBookInfoRepository dlBookInfoRepository;

    @Inject
    private DlBookInfoSearchRepository dlBookInfoSearchRepository;
    @Inject
    private InstituteRepository instituteRepository;
@Inject
    private SisStudentInfoRepository sisStudentInfoRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    TransactionIdResource transactionId = new TransactionIdResource();
    DateResource dr = new DateResource();
    String filepath ="/backup/dlImages/";


    /**
     * POST  /dlBookInfos -> Create a new dlBookInfo.
     */
    @RequestMapping(value = "/dlBookInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookInfo> createDlBookInfo(@RequestBody DlBookInfo dlBookInfo) throws Exception {
        log.debug("REST request to save DlBookInfo : {}", dlBookInfo);
        if (dlBookInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlBookInfo cannot already have an ID").body(null);
        }


        String filename="dlBookInfo";

        String extension = ".png";

        String fileContent = dlBookInfo.getBookImgContentType();


        if(dlBookInfo.getBookImgContentType()!=null &&  dlBookInfo.getBookImgContentType().equals("application/pdf")){
            dlBookInfo.setBookImgContentType(dlBookInfo.getBookImgName() + ".pdf");

            extension = ".pdf";
        }
        else {
            extension = ".png";
        }


        if(dlBookInfo.getBookImg() != null) {
            dlBookInfo.setBookImgName(AttachmentUtil.saveDlAttachment(filepath, null, extension, dlBookInfo.getBookImg()));
        }
        dlBookInfo.setInstitute(instituteRepository.findOneByUserIsCurrentUser());
        dlBookInfo.setBookImg(null);

        DlBookInfo result = dlBookInfoRepository.save(dlBookInfo);
        dlBookInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlBookInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlBookInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlBookInfos -> Updates an existing dlBookInfo.
     */
    @RequestMapping(value = "/dlBookInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookInfo> updateDlBookInfo(@RequestBody DlBookInfo dlBookInfo) throws URISyntaxException {
        log.debug("REST request to update DlBookInfo : {}", dlBookInfo);
        if (dlBookInfo.getId() == null) {
            return null;
        }
        DlBookInfo result = dlBookInfoRepository.save(dlBookInfo);
        dlBookInfoSearchRepository.save(dlBookInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlBookInfo", dlBookInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlBookInfos -> get all the dlBookInfos.
     */
    @RequestMapping(value = "/dlBookInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlBookInfo>> getAllDlBookInfos(Pageable pageable)


        throws Exception {
        Page<DlBookInfo> page = dlBookInfoRepository.findAll(pageable);
        for(DlBookInfo each: page){

            if(each.getBookImgName() != null && AttachmentUtil.retriveAttachment(filepath, each.getBookImgName()) != null){
                each.setBookImg(AttachmentUtil.retriveAttachment(filepath, each.getBookImgName()));
            }
            /*if(each.getContentName() != null && AttachmentUtil.retriveAttachment(filepath, each.getContentName()) != null){
                each.setContent(AttachmentUtil.retriveAttachment(filepath, each.getContentName()));
            }*/

        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlBookInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlBookInfos/:id -> get the "id" dlBookInfo.
     */
    @RequestMapping(value = "/dlBookInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookInfo> getDlBookInfo(@PathVariable Long id) throws  Exception{
        log.debug("REST request to get DlBookInfo : {}", id);
        DlBookInfo dlBookInfo = dlBookInfoRepository.findOne(id);

        try {

            if (dlBookInfo.getBookImgName() != null) {
                dlBookInfo.setBookImg(AttachmentUtil.retriveAttachment(filepath, dlBookInfo.getBookImgName()));
            }

        }catch (NoSuchFileException exception){
            log.debug("Exception is " + exception.getMessage());
        }

          /*  .map(dlBookInfo -> new ResponseEntity<>(
                dlBookInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/

        if (dlBookInfo != null) {
            return new ResponseEntity<>(dlBookInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @RequestMapping(value = "/dlBookInfos/FindDlBookInfosByInstId",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DlBookInfo> getFindDlBookInfosByInstId() throws Exception {
        /*String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();
        System.out.println("=====================Current User id====================");
        System.out.println(userId);
        System.out.println("===================== Only id====================");
        System.out.println(userName);*/
        List<DlBookInfo> dlBookInfos = dlBookInfoRepository.findAllBookInfosByUserIsCurrentUser();
        for(DlBookInfo each: dlBookInfos){
            if(each.getBookImgName() != null && AttachmentUtil.retriveAttachment(filepath, each.getBookImgName()) != null){
                each.setBookImg(AttachmentUtil.retriveAttachment(filepath, each.getBookImgName()));
            }
        }


       /* List<Map<String,Object>> ByInspIdlistRpt = rptJdbcDao.findDlBookInfosByInstId(userName);

        for(DlBookInfo each: page){

            if(each.getBookImgName() != null && AttachmentUtil.retriveAttachment(filepath, each.getBookImgName()) != null){
                each.setBookImg(AttachmentUtil.retriveAttachment(filepath, each.getBookImgName()));
            }
            *//*if(each.getContentName() != null && AttachmentUtil.retriveAttachment(filepath, each.getContentName()) != null){
                each.setContent(AttachmentUtil.retriveAttachment(filepath, each.getContentName()));
            }*//*

        }*/


        return dlBookInfos;
    }

    /**
     * DELETE  /dlBookInfos/:id -> delete the "id" dlBookInfo.
     */
    @RequestMapping(value = "/dlBookInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlBookInfo(@PathVariable Long id) {
        log.debug("REST request to delete DlBookInfo : {}", id);
        dlBookInfoRepository.delete(id);
        dlBookInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlBookInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlBookInfos/:query -> search for the dlBookInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlBookInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookInfo> searchDlBookInfos(@PathVariable String query) {
        return StreamSupport
            .stream(dlBookInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }



    // Find Book information by isbn No
    @RequestMapping(value = "/dlBookInfos/findBookInfoByBookId/{bookId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookInfo> findBookInfoByBookId(@PathVariable String bookId) {

        Long instId = instituteRepository.findOneByUserIsCurrentUser().getId();

        return Optional.ofNullable(dlBookInfoRepository.findBookInfoByBookId(bookId, instId))
            .map(dlBookInfo -> new ResponseEntity<>(
                dlBookInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "/dlBookInfo/dlBookInfoisbnvalidation/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> validationforisbn( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Long instId = instituteRepository.findOneByUserIsCurrentUser().getId();
        Optional<DlBookInfo> dlBookInfo = dlBookInfoRepository.validationforisbn(value,instId);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlBookInfo)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/dlBookInfo/dlBookInfoBookIdValidation/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> validationForBookId( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Long instId = instituteRepository.findOneByUserIsCurrentUser().getId();
        Optional<DlBookInfo> dlBookInfo = dlBookInfoRepository.validationForBookId(value, instId);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlBookInfo)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }





    @RequestMapping(value = "dlBookInfos/findByallType/{dlContCatSet}/{dlContTypeSet}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookInfo> findByallType(@PathVariable Long dlContCatSet, @PathVariable Long dlContTypeSet) {
        List dlBookInfo = dlBookInfoRepository.findByallType( dlContCatSet, dlContTypeSet);
        return dlBookInfo;
    }


    @RequestMapping(value = "dlBookInfos/getAuthorListByTitle/{title}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookInfo> getAuthorListByTitle(@PathVariable String title) {
        List dlBookInfo = dlBookInfoRepository.getAuthorListByTitle(title);
        return dlBookInfo;
    }






    @RequestMapping(value = "/dlBookInfos/getAllBookInfoByScatAndInstitute/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookInfo> getAllBookInfoByScatAndInstitute(@PathVariable Long id) {
        Long instId = sisStudentInfoRepository.findOneByUserIsCurrentUser().getInstitute().getId();
        List <DlBookInfo> ans= dlBookInfoRepository.getAllBookInfoByScatAndInstitute(id,instId);
        return ans;
    }

//    @RequestMapping(value = "dlBookInfos/getEditionListByAuthorName/{authorName}",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<DlBookInfo> getEditionListByAuthorName(@PathVariable String authorName) {
//        List dlBookInfo = dlBookInfoRepository.getEditionListByAuthorName(authorName);
//        return dlBookInfo;
//    }

}
