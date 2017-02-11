package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlBookIssue;
import gov.step.app.domain.DlBookReturn;
import gov.step.app.repository.DlBookIssueRepository;
import gov.step.app.repository.DlBookReturnRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.search.DlBookReturnSearchRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DlBookReturn.
 */
@RestController
@RequestMapping("/api")
public class DlBookReturnResource {

    private final Logger log = LoggerFactory.getLogger(DlBookReturnResource.class);

    @Inject
    private DlBookReturnRepository dlBookReturnRepository;

    @Inject
    private DlBookReturnSearchRepository dlBookReturnSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;
    @Inject
    private DlBookIssueRepository dlBookIssueRepository;

    /**
     * POST  /dlBookReturns -> Create a new dlBookReturn.
     */
    @RequestMapping(value = "/dlBookReturns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookReturn> createDlBookReturn(@RequestBody DlBookReturn dlBookReturn) throws URISyntaxException {
        log.debug("REST request to save DlBookReturn : {}", dlBookReturn);
        if (dlBookReturn.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlBookReturn cannot already have an ID").body(null);
        }
        dlBookReturn.setInstitute(instituteRepository.findOneByUserIsCurrentUser());

        DlBookReturn result = dlBookReturnRepository.save(dlBookReturn);
        dlBookReturnSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlBookReturns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlBookReturn", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlBookReturns -> Updates an existing dlBookReturn.
     */
    @RequestMapping(value = "/dlBookReturns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookReturn> updateDlBookReturn(@RequestBody DlBookReturn dlBookReturn) throws URISyntaxException {
        log.debug("REST request to update DlBookReturn : {}", dlBookReturn);
        if (dlBookReturn.getId() == null) {
            return createDlBookReturn(dlBookReturn);
        }
        DlBookReturn result = dlBookReturnRepository.save(dlBookReturn);
        dlBookReturnSearchRepository.save(dlBookReturn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlBookReturn", dlBookReturn.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlBookReturns -> get all the dlBookReturns.
     */
    @RequestMapping(value = "/dlBookReturns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlBookReturn>> getAllDlBookReturns(Pageable pageable)
        throws URISyntaxException {
        Page<DlBookReturn> page = dlBookReturnRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlBookReturns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlBookReturns/:id -> get the "id" dlBookReturn.
     */
    @RequestMapping(value = "/dlBookReturns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookReturn> getDlBookReturn(@PathVariable Long id) {
        log.debug("REST request to get DlBookReturn : {}", id);
        return Optional.ofNullable(dlBookReturnRepository.findOne(id))
            .map(dlBookReturn -> new ResponseEntity<>(
                dlBookReturn,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "/dlBookReturns/FindDlBookReturneByInstId",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DlBookReturn> findAllBookReturnByUserIsCurrentUser() throws Exception {
        /*String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();
        System.out.println("=====================Current User id====================");
        System.out.println(userId);
        System.out.println("===================== Only id====================");
        System.out.println(userName);*/
        List<DlBookReturn> dlBookReturn = dlBookReturnRepository.findAllBookReturnByUserIsCurrentUser();
        return dlBookReturn;
    }

    @RequestMapping(value = "/dlBookReturns/findReturnInfoByStuid/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlBookReturn>> findReturnInfoByStuid(@PathVariable Long id) {
        List<DlBookReturn> bookReturnList = new ArrayList<>();
        List<DlBookIssue> list=dlBookIssueRepository.findStudentInfoByStudentId(id) ;

            for(DlBookIssue dlIssue : list){

              if(dlBookReturnRepository.findReturnInfoByStuid(dlIssue.getId()) != null){
                  bookReturnList.add(dlBookReturnRepository.findReturnInfoByStuid(dlIssue.getId()));


              }
            }

//        Page<DlBookReturn> page = bookReturnList.;
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlBookReturns");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

/*
         return bookReturnList;
*/
        return Optional.ofNullable(bookReturnList)
            .map(employeeLoanRequisitionForm -> new ResponseEntity<>(
                employeeLoanRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    /**
     * DELETE  /dlBookReturns/:id -> delete the "id" dlBookReturn.
     */
    @RequestMapping(value = "/dlBookReturns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlBookReturn(@PathVariable Long id) {
        log.debug("REST request to delete DlBookReturn : {}", id);
        dlBookReturnRepository.delete(id);
        dlBookReturnSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlBookReturn", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlBookReturns/:query -> search for the dlBookReturn corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlBookReturns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookReturn> searchDlBookReturns(@PathVariable String query) {
        return StreamSupport
            .stream(dlBookReturnSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
