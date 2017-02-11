package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlBookInfo;
import gov.step.app.domain.DlBookIssue;
import gov.step.app.domain.DlContUpld;
import gov.step.app.domain.SisStudentInfo;
import gov.step.app.repository.DlBookIssueRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.SisStudentInfoRepository;
import gov.step.app.repository.search.DlBookIssueSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.AttachmentUtil;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import org.apache.catalina.security.SecurityUtil;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DlBookIssue.
 */
@RestController
@RequestMapping("/api")
public class DlBookIssueResource {

    private final Logger log = LoggerFactory.getLogger(DlBookIssueResource.class);

    @Inject
    private DlBookIssueRepository dlBookIssueRepository;

    @Inject
    private DlBookIssueSearchRepository dlBookIssueSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;
    @Inject
    private SisStudentInfoRepository sisStudentInfoRepository;
    @Inject
    private RptJdbcDao rptJdbcDao;


    /**
     * POST  /dlBookIssues -> Create a new dlBookIssue.
     */
    @RequestMapping(value = "/dlBookIssues",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookIssue> createDlBookIssue(@RequestBody DlBookIssue dlBookIssue) throws URISyntaxException {
        log.debug("REST request to save DlBookIssue : {}", dlBookIssue);
        if (dlBookIssue.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlBookIssue cannot already have an ID").body(null);
        }

/*
        dlBookIssue.setStatus(1);
*/
        dlBookIssue.setInstitute(instituteRepository.findOneByUserIsCurrentUser());
        if (SecurityUtils.isCurrentUserInRole("ROLE_GOVT_STUDENT")) {

            dlBookIssue.setSisStudentInfo(sisStudentInfoRepository.findOneByUserIsCurrentUser());

        }

        DlBookIssue result = dlBookIssueRepository.save(dlBookIssue);
        dlBookIssueSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlBookIssues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlBookIssue", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlBookIssues -> Updates an existing dlBookIssue.
     */
    @RequestMapping(value = "/dlBookIssues",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookIssue> updateDlBookIssue(@RequestBody DlBookIssue dlBookIssue) throws URISyntaxException {
        log.debug("REST request to update DlBookIssue : {}", dlBookIssue);
        if (dlBookIssue.getId() == null) {
            return createDlBookIssue(dlBookIssue);
        }
        dlBookIssue.setInstitute(instituteRepository.findOneByUserIsCurrentUser());
        DlBookIssue result = dlBookIssueRepository.save(dlBookIssue);
        dlBookIssueSearchRepository.save(dlBookIssue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlBookIssue", dlBookIssue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlBookIssues -> get all the dlBookIssues.
     */
    @RequestMapping(value = "/dlBookIssues",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlBookIssue>> getAllDlBookIssues(Pageable pageable)
        throws URISyntaxException {
        Page<DlBookIssue> page = dlBookIssueRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlBookIssues");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }





    @RequestMapping(value = "/dlBookIssue/findBookIssueForStudentRole/{bookId}/{BookEditionId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookIssue> findBookIssueForStudentRole(@PathVariable String bookId,@PathVariable Long BookEditionId) {

        Long StudentId = sisStudentInfoRepository.findOneByUserIsCurrentUser().getId();

        return Optional.ofNullable(dlBookIssueRepository.findBookIssueForStudentRole(StudentId,bookId,BookEditionId))
            .map(dlBookIssue -> new ResponseEntity<>(
                dlBookIssue,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }




    /**
     * GET  /dlBookIssues/:id -> get the "id" dlBookIssue.
     */
    @RequestMapping(value = "/dlBookIssues/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookIssue> getDlBookIssue(@PathVariable Long id) {
        log.debug("REST request to get DlBookIssue : {}", id);
        return Optional.ofNullable(dlBookIssueRepository.findOne(id))
            .map(dlBookIssue -> new ResponseEntity<>(
                dlBookIssue,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "/dlBookIssues/byUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DlBookIssue> getAllDlContUpldsbyUser() throws Exception {
        List<DlBookIssue> dlBookIssue = dlBookIssueRepository.findAllbyUser();
        return dlBookIssue;
    }


    @RequestMapping(value = "/dlBookIssues/findIssueInfoByid/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map<String, Object>> findIssueInfoByid(@PathVariable Long id) {
        List<Map<String, Object>> m = rptJdbcDao.findIssueInfoByid(id);
        if (m.size() > 0) {
            return new ResponseEntity<>(
                m.get(0),
                HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/dlBookIssues/findIssueInfoByStuid/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookIssue> getfindIssueInfoByStuid(@PathVariable Long id) throws Exception {
        List<DlBookIssue> dlBookIssue = dlBookIssueRepository.findIssueInfoByStuid(id);
        return dlBookIssue;
    }

    //    @RequestMapping(value = "/dlBookIssues/findIssueInfoByIssueId/{id}",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//     public DlBookIssue findIssueInfoByIssueId(@PathVariable Long id) throws Exception {
//         DlBookIssue dlBookIssue = dlBookIssueRepository.findIssueInfoByIssueId(id);
//         return dlBookIssue;
//     }
    @RequestMapping(value = "/dlBookIssues/findIssueInfoByIssueId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookIssue> findBookInfoByBookId(@PathVariable Long id) {

        return Optional.ofNullable(dlBookIssueRepository.findIssueInfoByIssueId(id))
            .map(dlBookIssue -> new ResponseEntity<>(
                dlBookIssue,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/dlBookIssues/findAllBookIssue/{sisId}/{BookInfoId}/{BookEdiId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookIssue> findAllBookIssue(@PathVariable Long sisId,@PathVariable String BookInfoId,@PathVariable Long BookEdiId) {

        return Optional.ofNullable(dlBookIssueRepository.findAllBookIssue(sisId,BookInfoId,BookEdiId))
            .map(dlBookIssue -> new ResponseEntity<>(
                dlBookIssue,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * DELETE  /dlBookIssues/:id -> delete the "id" dlBookIssue.
     */
    @RequestMapping(value = "/dlBookIssues/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlBookIssue(@PathVariable Long id) {
        log.debug("REST request to delete DlBookIssue : {}", id);
        dlBookIssueRepository.delete(id);
        dlBookIssueSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlBookIssue", id.toString())).build();
    }


    /**
     * SEARCH  /_search/dlBookIssues/:query -> search for the dlBookIssue corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlBookIssues/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookIssue> searchDlBookIssues(@PathVariable String query) {
        return StreamSupport
            .stream(dlBookIssueSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @RequestMapping(value = "/dlBookIssues/FindDlBookIssueByInstId",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DlBookIssue> findAllBookIssueByUserIsCurrentUser() throws Exception {
        /*String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();
        System.out.println("=====================Current User id====================");
        System.out.println(userId);
        System.out.println("===================== Only id====================");
        System.out.println(userName);*/
        List<DlBookIssue> dlBookIssue = dlBookIssueRepository.findAllBookIssueByUserIsCurrentUser();
        return dlBookIssue;
    }


}
