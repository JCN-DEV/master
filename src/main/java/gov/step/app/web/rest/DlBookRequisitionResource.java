package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlBookRequisition;
import gov.step.app.domain.MailTemplate;
import gov.step.app.repository.DlBookRequisitionRepository;
import gov.step.app.repository.SisStudentInfoRepository;
import gov.step.app.repository.search.DlBookRequisitionSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.MailService;
import gov.step.app.service.UserService;
import gov.step.app.web.rest.util.DateResource;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import gov.step.app.web.rest.util.TransactionIdResource;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DlBookRequisition.
 */
@RestController
@RequestMapping("/api")
public class DlBookRequisitionResource {

    private final Logger log = LoggerFactory.getLogger(DlBookRequisitionResource.class);

    @Inject
    private UserService userService;
    @Inject
    private DlBookRequisitionRepository dlBookRequisitionRepository;

    @Inject
    private DlBookRequisitionSearchRepository dlBookRequisitionSearchRepository;

    @Inject
    private SisStudentInfoRepository sisStudentInfoRepository;

    @Inject
        private MailService mailService;

    private MailTemplate template=new MailTemplate();


    /**
     * POST  /dlBookRequisitions -> Create a new dlBookRequisition.
     */
    TransactionIdResource transactionId = new TransactionIdResource();
    DateResource dr = new DateResource();


    @RequestMapping(value = "/dlBookRequisitions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookRequisition> createDlBookRequisition(@RequestBody DlBookRequisition dlBookRequisition) throws URISyntaxException {
        log.debug("REST request to save DlBookRequisition : {}", dlBookRequisition);
        dlBookRequisition.setCreateBy(SecurityUtils.getCurrentUserId());//here set to Create By.
        dlBookRequisition.setCreateDate(dr.getDateAsLocalDate());//here set to Create Date.

        if (SecurityUtils.isCurrentUserInRole("ROLE_GOVT_STUDENT")) {
            dlBookRequisition.setSisStudentInfo(sisStudentInfoRepository.findOneByUserIsCurrentUser());
        }

        if (dlBookRequisition.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlBookRequisition cannot already have an ID").body(null);
        }
        DlBookRequisition result = dlBookRequisitionRepository.save(dlBookRequisition);
        dlBookRequisitionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlBookRequisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlBookRequisition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlBookRequisitions -> Updates an existing dlBookRequisition.
     */
    @RequestMapping(value = "/dlBookRequisitions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookRequisition> updateDlBookRequisition(@RequestBody DlBookRequisition dlBookRequisition) throws URISyntaxException {
        log.debug("Entity is updating <<<<------------->>>>>>> : {}", dlBookRequisition);
        try{
            dlBookRequisition.setUpdateBy(SecurityUtils.getCurrentUserId());
            dlBookRequisition.setUpdateDate(dr.getDateAsLocalDate());

        }catch (Exception e){
            e.printStackTrace();
        }
        if (dlBookRequisition.getId() == null) {
            return createDlBookRequisition(dlBookRequisition);
        }
        String body="Your Requested Book is Available Now. Your Requested Book Information  is  ...<br><br><b>Book Title : </b>"+dlBookRequisition.getTitle()+"<br><b>Author Name </b>: "+dlBookRequisition.getAuthorName()+"<br><b>Edition :</b>"+dlBookRequisition.getEdition();
        log.debug("Email<<<<<<<<<<------------------->>>>>>>>>>>>>>: {}", dlBookRequisition.getSisStudentInfo().getEmailAddress());
        mailService.sendEmail(dlBookRequisition.getSisStudentInfo().getEmailAddress(), "Book Added Confirmation", template.createMailTemplate(dlBookRequisition.getSisStudentInfo().getName(), body),false, true );

        DlBookRequisition result = dlBookRequisitionRepository.save(dlBookRequisition);
        dlBookRequisitionSearchRepository.save(dlBookRequisition);





        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlBookRequisition", dlBookRequisition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlBookRequisitions -> get all the dlBookRequisitions.
     */
    @RequestMapping(value = "/dlBookRequisitions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlBookRequisition>> getAllDlBookRequisitions(Pageable pageable)
        throws URISyntaxException {
        Page<DlBookRequisition> page = dlBookRequisitionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlBookRequisitions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }




    @RequestMapping(value = "/DlBookRequisition/getBookByUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DlBookRequisition>> findAllbyBookUser(Pageable pageable) throws Exception {
        log.debug("yyyyyyyyyyyyyyyyyyyyyy");
        Page<DlBookRequisition> page = dlBookRequisitionRepository.findAllbyBookUser(pageable);
        System.out.println(page);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/DlBookRequisition/getBookByUser");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /dlBookRequisitions/:id -> get the "id" dlBookRequisition.
     */
    @RequestMapping(value = "/dlBookRequisitions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookRequisition> getDlBookRequisition(@PathVariable Long id) {
        log.debug("REST request to get DlBookRequisition : {}", id);
        return Optional.ofNullable(dlBookRequisitionRepository.findOne(id))
            .map(dlBookRequisition -> new ResponseEntity<>(
                dlBookRequisition,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlBookRequisitions/:id -> delete the "id" dlBookRequisition.
     */
    @RequestMapping(value = "/dlBookRequisitions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlBookRequisition(@PathVariable Long id) {
        log.debug("REST request to delete DlBookRequisition : {}", id);
        dlBookRequisitionRepository.delete(id);
        dlBookRequisitionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlBookRequisition", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlBookRequisitions/:query -> search for the dlBookRequisition corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlBookRequisitions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookRequisition> searchDlBookRequisitions(@PathVariable String query) {
        return StreamSupport
            .stream(dlBookRequisitionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
