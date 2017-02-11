package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.repository.DlContUpldRepository;
import gov.step.app.repository.InstEmployeeRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.DlContUpldSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.*;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DlContUpld.
 */
@RestController
@RequestMapping("/api")
public class DlContUpldResource {

    private final Logger log = LoggerFactory.getLogger(DlContUpldResource.class);

    @Autowired
    ServletContext context;

    @Inject
    private DlContUpldRepository dlContUpldRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private InstEmployeeRepository instEmployeeRepository;
    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private DlContUpldSearchRepository dlContUpldSearchRepository;

    String filepath ="/backup/dlImages/";

    /**
     * POST  /dlContUplds -> Create a new dlContUpld.
     */
    @RequestMapping(value = "/dlContUplds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContUpld> createDlContUpld(@Valid @RequestBody DlContUpld dlContUpld) throws URISyntaxException,Exception {
        log.debug("REST request to save DlContUpld : {}", dlContUpld);
        if (dlContUpld.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlContUpld cannot already have an ID").body(null);
        }


        String extension = "";
        String filename=dlContUpld.getContentName();
        String fileName=dlContUpld.getContentImageName();
        String fileContent = dlContUpld.getContentContentType();// .getContentImageContentType();
        TransactionIdResource trnId =new TransactionIdResource();
       // String filepath = context.getRealPath("/") + Constants.ATTACHMENT_FILE_DIR_DIGITALLIBRARY;
        if((fileContent.equals("application/pdf")) && (!fileContent.equals(".png"))) {

            dlContUpld.setCode(trnId.getGeneratedid("PDF"));
            }else{
            dlContUpld.setCode(trnId.getGeneratedid("IMG"));
            }
        if((fileContent.equals("audio/mp3")) && (!fileContent.equals(".mp4"))) {

            dlContUpld.setCode(trnId.getGeneratedid("MP3"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("MP4"));
        }
        if((fileContent.equals("application/doc")) && (!fileContent.equals(".docx"))) {

            dlContUpld.setCode(trnId.getGeneratedid("DOC"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("DOCX"));
        }
        if((fileContent.equals("image/gif")) && (!fileContent.equals(".csv"))) {

            dlContUpld.setCode(trnId.getGeneratedid("GIF"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("CSV"));
        }

        if((fileContent.equals("video/3gp")) && (!fileContent.equals("..webm"))) {

            dlContUpld.setCode(trnId.getGeneratedid("3GP"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("WebM"));
        }
        if((fileContent.equals("video/flv")) && (!fileContent.equals(".avi"))) {

            dlContUpld.setCode(trnId.getGeneratedid("FLV"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("AVI"));
        }
        if((fileContent.equals("video/wmv")) && (!fileContent.equals(".mov"))) {

            dlContUpld.setCode(trnId.getGeneratedid("WMV"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("MOV"));
        }

        if((fileContent.equals("application/ppt")) && (!fileContent.equals(".zip"))) {

            dlContUpld.setCode(trnId.getGeneratedid("PPT"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("ZIP"));
        }
        if((fileContent.equals("application/rar")) && (!fileContent.equals(".odt"))) {

            dlContUpld.setCode(trnId.getGeneratedid("RAR"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("ODT"));
        }
        if((fileContent.equals("application/cab")) && (!fileContent.equals(".arj"))) {

            dlContUpld.setCode(trnId.getGeneratedid("CAB"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("ARJ"));
        }
        if((fileContent.equals("application/gz")) && (!fileContent.equals(".ace"))) {

            dlContUpld.setCode(trnId.getGeneratedid("GZ"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("ACE"));
        }
        if((fileContent.equals("application/tar")) && (!fileContent.equals(".uue"))) {

            dlContUpld.setCode(trnId.getGeneratedid("TAR"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("UUE"));
        }
        if((fileContent.equals("application/jar")) && (!fileContent.equals(".iso"))) {

            dlContUpld.setCode(trnId.getGeneratedid("JAR"));
        }else{
            dlContUpld.setCode(trnId.getGeneratedid("ISO"));
        } /* */

        if((fileContent.equals("application/pdf")) && (!fileContent.equals(".png"))) {
            extension=".pdf";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".pdf");

        }else{
            extension=".png";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".png");
        }

        if((fileContent.equals("audio/mp3")) && (!fileContent.equals(".mp4"))) {
            extension=".mp3";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".mp3");

        }else{
            extension=".mp4";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".mp4");
        }
        if((fileContent.equals("application/doc")) && (!fileContent.equals(".docx"))) {
            extension=".doc";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".doc");
        }else{
            extension=".docx";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".docx");
        }
        if((fileContent.equals("application/ppt")) && (!fileContent.equals(".csv"))) {
            extension=".ppt";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".ppt");
        }else{
            extension=".csv";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".csv");
        }

        if((fileContent.equals("video/3gp")) && (!fileContent.equals("..webm"))) {
            extension=".3gp";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".3gp");
        }else{
            extension=".webm";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".webm");
        }
        if((fileContent.equals("video/flv")) && (!fileContent.equals(".avi"))) {
            extension=".flv";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".flv");
        }else{
            extension=".avi";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".avi");
        }
        if((fileContent.equals("video/wmv")) && (!fileContent.equals(".mov"))) {
            extension=".wmv";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".wmv");
        }else{
            extension=".mov";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".mov");
        }
        if((fileContent.equals("application/rar")) && (!fileContent.equals(".zip"))) {
            extension=".rar";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".rar");
        }else{
            extension=".zip";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".zip");
        }
        if((fileContent.equals("application/xlsx")) && (!fileContent.equals(".odt"))) {
            extension=".xlsx";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".xlsx");
        }else{
            extension=".odt";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".odt");
        }
        if((fileContent.equals("application/cab")) && (!fileContent.equals(".arj"))) {
            extension=".cab";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".cab");
        }else{
            extension=".arj";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".arj");
        }
        if((fileContent.equals("application/gz")) && (!fileContent.equals(".ace"))) {
            extension=".gz";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".gz");
        }else{
            extension=".ace";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".ace");
        }
        if((fileContent.equals("application/tar")) && (!fileContent.equals(".uue"))) {
            extension=".tar";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".tar");
        }else{
            extension=".uue";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".uue");
        }
        if((fileContent.equals("application/jar")) && (!fileContent.equals(".iso"))) {
            extension=".jar";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".jar");
        }else{
            extension=".iso";
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".iso");
        }
/**/


        if(dlContUpld.getContentContentType()!=null &&  dlContUpld.getContentContentType().equals("application/pdf")){
            extension = ".pdf";
        }
        else {
            extension = ".png";
        }

        if(dlContUpld.getContent() != null) {
            dlContUpld.setContentName(AttachmentUtil.saveDlAttachment(filepath, filename, extension, dlContUpld.getContent()));

        }
        /*if(dlContUpld.getContentImageContentType() != null && dlContUpld.getContentImageContentType().equals("application/pdf")){
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".pdf");
            extension = ".pdf";
        }
        else {
            dlContUpld.setContentImageContentType(dlContUpld.getContentImageName() + ".png");
            extension = ".png";

        }*/




        /*if(dlContUpld.getContentImage() != null) {
            dlContUpld.setContentImageName(AttachmentUtil.saveDlAttachment(filepath,null,extension, dlContUpld.getContentImage()));

        }
      */


        try{
            //dlContUpld.setContentImageName(AttachmentUtil.saveAttachment(filepath,filename,extension,dlContUpld.getContentImage()));
            dlContUpld.setContentImageName(AttachmentUtil.saveAttachment(filepath, fileName, extension, dlContUpld.getContentImage()));
        }catch(Exception e){
            e.printStackTrace();
        }
       /* String userName = SecurityUtils.getCurrentUserLogin();

        if(userName == "anonymousUser"){
            dlContUpld.setContent(null);
            dlContUpld.setContentImage(null);
            dlContUpld.setUser(userRepository.findOne(SecurityUtils.getCurrentUserId()));

            System.out.println("====================================" + userName);

            DlContUpld result = dlContUpldRepository.save(dlContUpld);
            dlContUpldSearchRepository.save(result);
            return ResponseEntity.created(new URI("/api/dlContUplds/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("dlContUpld", result.getId().toString()))
                .body(result);
        }else {*/

            dlContUpld.setContent(null);
            dlContUpld.setContentImage(null);
           /* User u = new User();
            dlContUpld.setUser(u);*/
            dlContUpld.setUser(userRepository.findOne(SecurityUtils.getCurrentUserId()));
            dlContUpld.setInstEmployee(instEmployeeRepository.findCurrentOne());
        if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
            dlContUpld.setStatus(5);


        }
            DlContUpld result = dlContUpldRepository.save(dlContUpld);
            dlContUpldSearchRepository.save(result);


            return ResponseEntity.created(new URI("/api/dlContUplds/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("dlContUpld", result.getId().toString()))
                .body(result);
       // }

    }



    /*public ResponseEntity<DlContUpld> createDlContUpld(@RequestBody DlContUpld dlContUpld) throws URISyntaxException {
        log.debug("REST request to save DlContUpld : {}", dlContUpld);
        if (dlContUpld.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlContUpld cannot already have an ID").body(null);
        }
        DlContUpld result = dlContUpldRepository.save(dlContUpld);
        dlContUpldSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlContUplds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlContUpld", result.getId().toString()))
            .body(result);
    }*/






    /**
     * PUT  /dlContUplds -> Updates an existing dlContUpld.
     */
    @RequestMapping(value = "/dlContUplds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContUpld> updateDlContUpld(@RequestBody DlContUpld dlContUpld) throws URISyntaxException,Exception {
        log.debug("REST request to update DlContUpld : {}", dlContUpld);
        if (dlContUpld.getId() == null) {
            return createDlContUpld(dlContUpld);
        }
        DlContUpld result = dlContUpldRepository.save(dlContUpld);
        dlContUpldSearchRepository.save(dlContUpld);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlContUpld", dlContUpld.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlContUplds -> get all the dlContUplds.
     */
    @RequestMapping(value = "/dlContUplds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContUpld>> getAllDlContUplds(Pageable pageable)
        throws URISyntaxException {
        Page<DlContUpld> page = dlContUpldRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlContUplds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/dlContUplds/byAdmin",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContUpld>> getAllDlContUpldsbyAdmin(Pageable pageable)
        throws Exception {
        Page<DlContUpld> page = dlContUpldRepository.findAllbyAdmin(pageable);
        for(DlContUpld each: page){

            if(each.getContentImageName() != null && AttachmentUtil.retriveAttachment(filepath, each.getContentImageName()) != null){
                each.setContentImage(AttachmentUtil.retriveAttachment(filepath, each.getContentImageName()));
            }
            /*if(each.getContentName() != null && AttachmentUtil.retriveAttachment(filepath, each.getContentName()) != null){
                each.setContent(AttachmentUtil.retriveAttachment(filepath, each.getContentName()));
            }*/

        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlContUplds/byAdmin");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/dlContUplds/FindDlContUpldsByInstId",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DlContUpld> getFindDlContUpldsByInstId() throws Exception {
        /*String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();
        System.out.println("=====================Current User id====================");
        System.out.println(userId);
        System.out.println("===================== Only id====================");
        System.out.println(userName);*/
        List<DlContUpld> dlContUplds = dlContUpldRepository.findAllContUpldsByUserIsCurrentUser();
        for(DlContUpld each: dlContUplds){
            if(each.getContentImageName() != null && AttachmentUtil.retriveAttachment(filepath, each.getContentImageName()) != null){
                each.setContentImage(AttachmentUtil.retriveAttachment(filepath, each.getContentImageName()));
            }
        }





        return dlContUplds;
    }


   /* @RequestMapping(value = "/dlContUplds/byAdmin",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getAllDlContUpldsbyAdmin() {
        List<Map<String,Object>> allllDlContUpldsbyAdmin = rptJdbcDao.findGeneralAppList();
        return allllDlContUpldsbyAdmin;
    }*/

    @RequestMapping(value = "/dlContUplds/byUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContUpld>> getAllDlContUpldsbyUser(Pageable pageable)
        throws Exception {
        Page<DlContUpld> page = dlContUpldRepository.findAllbyUser(pageable);
        for(DlContUpld each: page){

            if(each.getContentImageName() != null && AttachmentUtil.retriveAttachment(filepath, each.getContentImageName()) != null){
                each.setContentImage(AttachmentUtil.retriveAttachment(filepath, each.getContentImageName()));
            }


        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlContUplds/byUser");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlContUplds/:id -> get the "id" dlContUpld.
     */
    @RequestMapping(value = "/dlContUplds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContUpld> getDlContUpld(@PathVariable Long id) throws Exception {
        log.debug("REST request to get DlContUpld : {}", id);
        DlContUpld dlContUpld =dlContUpldRepository.findOne(id);
        if(dlContUpld.getContentImageName() != null && AttachmentUtil.retriveAttachment(filepath, dlContUpld.getContentImageName()) != null){
            dlContUpld.setContentImage(AttachmentUtil.retriveAttachment(filepath, dlContUpld.getContentImageName()));
        }
        if(dlContUpld.getContentName() != null && AttachmentUtil.retriveAttachment(filepath, dlContUpld.getContentName()) != null){
            dlContUpld.setContent(AttachmentUtil.retriveAttachment(filepath, dlContUpld.getContentName()));
        }
        return Optional.ofNullable(dlContUpld)
            .map(dlContUpld2 -> new ResponseEntity<>(
                dlContUpld,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "/dlContUplds/contUpldCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getDlContUpldCodeUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<DlContUpld> dlContUpld = dlContUpldRepository.findOneByCode(value);
        Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlContUpld)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
/////Anik
    @RequestMapping(value = "/dlContUplds/dlContupldsetisbn/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> findOneByIsbn( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<DlContUpld> dlContUpld = dlContUpldRepository.findOneByIsbn(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlContUpld)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }




    /**
     * DELETE  /dlContUplds/:id -> delete the "id" dlContUpld.
     */
    @RequestMapping(value = "/dlContUplds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlContUpld(@PathVariable Long id) {
        log.debug("REST request to delete DlContUpld : {}", id);
        dlContUpldRepository.delete(id);
        dlContUpldSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlContUpld", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlContUplds/:query -> search for the dlContUpld corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlContUplds/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlContUpld> searchDlContUplds(@PathVariable String query) {
        return StreamSupport
            .stream(dlContUpldSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "dlContUplds/findAllByAllType/{dlContCatSet}/{dlContSCatSet}/{dlContTypeSet}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlContUpld> findAllByAllType(@PathVariable Long dlContCatSet, @PathVariable Long dlContSCatSet,@PathVariable Long dlContTypeSet) {
        List dlContUpld = dlContUpldRepository.findAllByAllType( dlContCatSet, dlContSCatSet, dlContTypeSet);
        return dlContUpld;
    }
//get all by id

    @RequestMapping(value = "/dlContUplds/findallbyid/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContUpld>> findallBYid(@PathVariable Long id) {

        log.debug("REST request to get all by id : {}", id);
        return Optional.ofNullable(dlContUpldRepository.findAllById(id))
            .map(dlContUpld -> new ResponseEntity<>(
                dlContUpld,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @RequestMapping(value = "/dlContUplds/findallbysid/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContUpld>> findAllBySCategory(@PathVariable Long id) {

        log.debug("REST request to get all by id : {}", id);
        return Optional.ofNullable(dlContUpldRepository.findAllBySCategory(id))
            .map(dlContUpld -> new ResponseEntity<>(
                dlContUpld,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }




    @RequestMapping(value = "/dlContUplds/findallbytype/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContUpld>> findAllByType(@PathVariable Long id) {

        log.debug("REST request to get all by id : {}", id);
        return Optional.ofNullable(dlContUpldRepository.findAllByType(id))
            .map(dlContUpld -> new ResponseEntity<>(
                dlContUpld,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }




}
