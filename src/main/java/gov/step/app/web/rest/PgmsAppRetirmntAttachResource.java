package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PgmsAppRetirmntAttach;
import gov.step.app.domain.PgmsRetirmntAttachInfo;
import gov.step.app.repository.PgmsAppRetirmntAttachRepository;
import gov.step.app.repository.PgmsRetirmntAttachInfoRepository;
import gov.step.app.repository.search.PgmsAppRetirmntAttachSearchRepository;
import gov.step.app.service.constnt.PGMSManagementConstant;
import gov.step.app.service.util.MiscFileInfo;
import gov.step.app.service.util.MiscFileUtilities;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PgmsAppRetirmntAttach.
 */
@RestController
@RequestMapping("/api")
public class PgmsAppRetirmntAttachResource {

    private final Logger log = LoggerFactory.getLogger(PgmsAppRetirmntAttachResource.class);

    @Inject
    private PgmsAppRetirmntAttachRepository pgmsAppRetirmntAttachRepository;

    @Inject
    private PgmsAppRetirmntAttachSearchRepository pgmsAppRetirmntAttachSearchRepository;

    @Inject
    private PgmsRetirmntAttachInfoRepository pgmsRetirmntAttachInfoRepository;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /pgmsAppRetirmntAttachs -> Create a new pgmsAppRetirmntAttach.
     */
    @RequestMapping(value = "/pgmsAppRetirmntAttachs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppRetirmntAttach> createPgmsAppRetirmntAttach(@Valid @RequestBody PgmsAppRetirmntAttach pgmsAppRetirmntAttach) throws URISyntaxException {
        log.debug("REST request to save PgmsAppRetirmntAttach : {}", pgmsAppRetirmntAttach);
        if (pgmsAppRetirmntAttach.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsAppRetirmntAttach cannot already have an ID").body(null);
        }

        //Saving App Retirmnt Attach to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(pgmsAppRetirmntAttach.getAttachment())
            .fileName(pgmsAppRetirmntAttach.getAttachDocName())
            .contentType(pgmsAppRetirmntAttach.getAttachmentContentType())
            .filePath(PGMSManagementConstant.PGM_RETIREMENT_ATTACH_FILE_DIR);

        goFile = fileUtils.saveFileAsByte(goFile);
        pgmsAppRetirmntAttach.setAttachment(new byte[1]);
        pgmsAppRetirmntAttach.setAttachDocName(goFile.fileName());

        PgmsAppRetirmntAttach result = pgmsAppRetirmntAttachRepository.save(pgmsAppRetirmntAttach);
        pgmsAppRetirmntAttachSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsAppRetirmntAttachs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsAppRetirmntAttach", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsAppRetirmntAttachs -> Updates an existing pgmsAppRetirmntAttach.
     */
    @RequestMapping(value = "/pgmsAppRetirmntAttachs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppRetirmntAttach> updatePgmsAppRetirmntAttach(@Valid @RequestBody PgmsAppRetirmntAttach pgmsAppRetirmntAttach) throws URISyntaxException {
        log.debug("REST request to update PgmsAppRetirmntAttach : {}", pgmsAppRetirmntAttach);
        if (pgmsAppRetirmntAttach.getId() == null) {
            return createPgmsAppRetirmntAttach(pgmsAppRetirmntAttach);
        }

        //Saving Family Attachement to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(pgmsAppRetirmntAttach.getAttachment())
            .fileName(pgmsAppRetirmntAttach.getAttachDocName())
            .contentType(pgmsAppRetirmntAttach.getAttachmentContentType())
            .filePath(PGMSManagementConstant.PGM_RETIREMENT_ATTACH_FILE_DIR);

        goFile = fileUtils.updateFileAsByte(goFile);
        pgmsAppRetirmntAttach.setAttachment(new byte[1]);
        pgmsAppRetirmntAttach.setAttachDocName(goFile.fileName());

        PgmsAppRetirmntAttach result = pgmsAppRetirmntAttachRepository.save(pgmsAppRetirmntAttach);
        pgmsAppRetirmntAttachSearchRepository.save(pgmsAppRetirmntAttach);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsAppRetirmntAttach", pgmsAppRetirmntAttach.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsAppRetirmntAttachs -> get all the pgmsAppRetirmntAttachs.
     */
    @RequestMapping(value = "/pgmsAppRetirmntAttachs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsAppRetirmntAttach>> getAllPgmsAppRetirmntAttachs(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsAppRetirmntAttach> page = pgmsAppRetirmntAttachRepository.findAll(pageable);
        for (PgmsAppRetirmntAttach modelInfo : page.getContent()) {
            MiscFileInfo empPhoto = new MiscFileInfo();
            empPhoto.fileName(modelInfo.getAttachDocName())
            .contentType(modelInfo.getAttachmentContentType())
                .filePath(PGMSManagementConstant.PGM_RETIREMENT_ATTACH_FILE_DIR);
            empPhoto = fileUtils.readFileAsByte(empPhoto);
            modelInfo.setAttachment(empPhoto.fileData());
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsAppRetirmntAttachs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsAppRetirmntAttachs/:id -> get the "id" pgmsAppRetirmntAttach.
     */
    @RequestMapping(value = "/pgmsAppRetirmntAttachs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppRetirmntAttach> getPgmsAppRetirmntAttach(@PathVariable Long id) {
        log.debug("REST request to get PgmsAppRetirmntAttach : {}", id);
        return Optional.ofNullable(pgmsAppRetirmntAttachRepository.findOne(id))
            .map(pgmsAppRetirmntAttach -> new ResponseEntity<>(
                pgmsAppRetirmntAttach,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsAppRetirmntAttachs/:id -> delete the "id" pgmsAppRetirmntAttach.
     */
    @RequestMapping(value = "/pgmsAppRetirmntAttachs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsAppRetirmntAttach(@PathVariable Long id) {
        log.debug("REST request to delete PgmsAppRetirmntAttach : {}", id);
        pgmsAppRetirmntAttachRepository.delete(id);
        pgmsAppRetirmntAttachSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsAppRetirmntAttach", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsAppRetirmntAttachs/:query -> search for the pgmsAppRetirmntAttach corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsAppRetirmntAttachs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppRetirmntAttach> searchPgmsAppRetirmntAttachs(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsAppRetirmntAttachSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
