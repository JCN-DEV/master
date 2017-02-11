package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.RisNewAppFormTwo;
import gov.step.app.repository.RisNewAppFormTwoRepository;
import gov.step.app.repository.search.RisNewAppFormTwoSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.AttachmentUtil;
import gov.step.app.web.rest.util.Constants;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RisNewAppFormTwo.
 */
@RestController
@RequestMapping("/api")
public class RisNewAppFormTwoResource {

    private final Logger log = LoggerFactory.getLogger(RisNewAppFormTwoResource.class);

    @Inject
    private RisNewAppFormTwoRepository risNewAppFormTwoRepository;

    @Inject
    private RisNewAppFormTwoSearchRepository risNewAppFormTwoSearchRepository;
    @Autowired
    ServletContext context;

    /**
     * POST  /risNewAppFormTwos -> Create a new risNewAppFormTwo.
     */
    @RequestMapping(value = "/risNewAppFormTwos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisNewAppFormTwo> createRisNewAppFormTwo(@Valid @RequestBody RisNewAppFormTwo risNewAppFormTwo) throws Exception {
        log.debug("REST request to save RisNewAppFormTwo : {}", risNewAppFormTwo);
        if (risNewAppFormTwo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new risNewAppFormTwo cannot already have an ID").body(null);
        }


        String filepath = context.getRealPath("/") + "assets/dlms/";

        String filename="risType";

        String extension = ".png";
        String bExtension = ".png";

        String signature = risNewAppFormTwo.getSignatureContentType();
        String bankInvoice = risNewAppFormTwo.getBankInvoiceContentType();


        if(risNewAppFormTwo.getSignatureContentType()!=null &&  risNewAppFormTwo.getSignatureContentType().equals("application/pdf")){
            extension = ".pdf";
        }
        else {
            extension = ".png";
        }
        if(risNewAppFormTwo.getBankInvoiceContentType()!=null &&  risNewAppFormTwo.getBankInvoiceContentType().equals("application/pdf")){
            bExtension = ".pdf";
        }
        else {
            bExtension = ".png";
        }

        if(risNewAppFormTwo.getSignature() != null) {
            risNewAppFormTwo.setSignatureImgName(AttachmentUtil.saveRisAttachment(filepath,risNewAppFormTwo.getSignatureImgName(),extension,risNewAppFormTwo.getSignature()));
        }
        if(risNewAppFormTwo.getBankInvoice() != null) {
            risNewAppFormTwo.setBankInvoiceName(AttachmentUtil.saveRisAttachment(filepath,risNewAppFormTwo.getBankInvoiceName(),bExtension,risNewAppFormTwo.getBankInvoice()));
        }

        risNewAppFormTwo.setSignature(null);
        risNewAppFormTwo.setBankInvoice(null);


        RisNewAppFormTwo result = risNewAppFormTwoRepository.save(risNewAppFormTwo);
        risNewAppFormTwoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/risNewAppFormTwos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("risNewAppFormTwo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /risNewAppFormTwos -> Updates an existing risNewAppFormTwo.
     */
    @RequestMapping(value = "/risNewAppFormTwos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisNewAppFormTwo> updateRisNewAppFormTwo(@Valid @RequestBody RisNewAppFormTwo risNewAppFormTwo) throws Exception {
        log.debug("REST request to update RisNewAppFormTwo : {}", risNewAppFormTwo);
        if (risNewAppFormTwo.getId() == null) {
            return createRisNewAppFormTwo(risNewAppFormTwo);
        }
        RisNewAppFormTwo result = risNewAppFormTwoRepository.save(risNewAppFormTwo);
        risNewAppFormTwoSearchRepository.save(risNewAppFormTwo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("risNewAppFormTwo", risNewAppFormTwo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /risNewAppFormTwos -> get all the risNewAppFormTwos.
     */
    @RequestMapping(value = "/risNewAppFormTwos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RisNewAppFormTwo>> getAllRisNewAppFormTwos(Pageable pageable)
        throws URISyntaxException {
        Page<RisNewAppFormTwo> page = risNewAppFormTwoRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/risNewAppFormTwos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /risNewAppFormTwos/:id -> get the "id" risNewAppFormTwo.
     */
    @RequestMapping(value = "/risNewAppFormTwos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisNewAppFormTwo> getRisNewAppFormTwo(@PathVariable Long id) {
        log.debug("REST request to get RisNewAppFormTwo : {}", id);
        return Optional.ofNullable(risNewAppFormTwoRepository.findOne(id))
            .map(risNewAppFormTwo -> new ResponseEntity<>(
                risNewAppFormTwo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/risNewAppFormTwoDetail/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisNewAppFormTwo> getRisAppFormById(@PathVariable Long id) {
        log.debug("REST request to get IamsInfra : {}", id);
        return Optional.ofNullable(risNewAppFormTwoRepository.findRisAppDetailById(id))
            .map(risNewAppFormTwoi -> new ResponseEntity<>(
                risNewAppFormTwoi,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /risNewAppFormTwos/:id -> delete the "id" risNewAppFormTwo.
     */
    @RequestMapping(value = "/risNewAppFormTwos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRisNewAppFormTwo(@PathVariable Long id) {
        log.debug("REST request to delete RisNewAppFormTwo : {}", id);
        risNewAppFormTwoRepository.delete(id);
        risNewAppFormTwoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("risNewAppFormTwo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/risNewAppFormTwos/:query -> search for the risNewAppFormTwo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/risNewAppFormTwos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RisNewAppFormTwo> searchRisNewAppFormTwos(@PathVariable String query) {
        return StreamSupport
            .stream(risNewAppFormTwoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
