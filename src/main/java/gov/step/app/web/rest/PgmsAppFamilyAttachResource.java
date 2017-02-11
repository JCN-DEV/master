package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PgmsAppFamilyAttach;
import gov.step.app.domain.PgmsRetirmntAttachInfo;
import gov.step.app.repository.PgmsAppFamilyAttachRepository;
import gov.step.app.repository.PgmsRetirmntAttachInfoRepository;
import gov.step.app.repository.search.PgmsAppFamilyAttachSearchRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PgmsAppFamilyAttach.
 */
@RestController
@RequestMapping("/api")
public class PgmsAppFamilyAttachResource {

    private final Logger log = LoggerFactory.getLogger(PgmsAppFamilyAttachResource.class);

    @Inject
    private PgmsAppFamilyAttachRepository pgmsAppFamilyAttachRepository;

    @Inject
    private PgmsAppFamilyAttachSearchRepository pgmsAppFamilyAttachSearchRepository;

    @Inject
    private PgmsRetirmntAttachInfoRepository pgmsRetirmntAttachInfoRepository;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /pgmsAppFamilyAttachs -> Create a new pgmsAppFamilyAttach.
     */
    @RequestMapping(value = "/pgmsAppFamilyAttachs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppFamilyAttach> createPgmsAppFamilyAttach(@Valid @RequestBody PgmsAppFamilyAttach pgmsAppFamilyAttach) throws URISyntaxException {
        log.debug("REST request to save PgmsAppFamilyAttach : {}", pgmsAppFamilyAttach);
        if (pgmsAppFamilyAttach.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsAppFamilyAttach cannot already have an ID").body(null);
        }

        //Saving Family Attachement to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(pgmsAppFamilyAttach.getAttachment())
            .fileName(pgmsAppFamilyAttach.getAttachDocName())
            .contentType(pgmsAppFamilyAttach.getAttachmentContentType())
            .filePath(PGMSManagementConstant.PGM_FALILY_ATTACH_FILE_DIR);

        goFile = fileUtils.saveFileAsByte(goFile);
        pgmsAppFamilyAttach.setAttachment(new byte[1]);
        pgmsAppFamilyAttach.setAttachDocName(goFile.fileName());

        PgmsAppFamilyAttach result = pgmsAppFamilyAttachRepository.save(pgmsAppFamilyAttach);
        pgmsAppFamilyAttachSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsAppFamilyAttachs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsAppFamilyAttach", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsAppFamilyAttachs -> Updates an existing pgmsAppFamilyAttach.
     */
    @RequestMapping(value = "/pgmsAppFamilyAttachs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppFamilyAttach> updatePgmsAppFamilyAttach(@Valid @RequestBody PgmsAppFamilyAttach pgmsAppFamilyAttach) throws URISyntaxException {
        log.debug("REST request to update PgmsAppFamilyAttach : {}", pgmsAppFamilyAttach);
        if (pgmsAppFamilyAttach.getId() == null) {
            return createPgmsAppFamilyAttach(pgmsAppFamilyAttach);
        }

        //Saving Family Attachement to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(pgmsAppFamilyAttach.getAttachment())
            .fileName(pgmsAppFamilyAttach.getAttachDocName())
            .contentType(pgmsAppFamilyAttach.getAttachmentContentType())
            .filePath(PGMSManagementConstant.PGM_FALILY_ATTACH_FILE_DIR);

        goFile = fileUtils.updateFileAsByte(goFile);
        pgmsAppFamilyAttach.setAttachment(new byte[1]);
        pgmsAppFamilyAttach.setAttachDocName(goFile.fileName());

        PgmsAppFamilyAttach result = pgmsAppFamilyAttachRepository.save(pgmsAppFamilyAttach);
        pgmsAppFamilyAttachSearchRepository.save(pgmsAppFamilyAttach);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsAppFamilyAttach", pgmsAppFamilyAttach.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsAppFamilyAttachs -> get all the pgmsAppFamilyAttachs.
     */
    @RequestMapping(value = "/pgmsAppFamilyAttachs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsAppFamilyAttach>> getAllPgmsAppFamilyAttachs(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsAppFamilyAttach> page = pgmsAppFamilyAttachRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsAppFamilyAttachs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsAppFamilyAttachs/:id -> get the "id" pgmsAppFamilyAttach.
     */
    @RequestMapping(value = "/pgmsAppFamilyAttachs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppFamilyAttach> getPgmsAppFamilyAttach(@PathVariable Long id) {
        log.debug("REST request to get PgmsAppFamilyAttach : {}", id);

        PgmsAppFamilyAttach pgmsAppFamilyAttach = pgmsAppFamilyAttachRepository.findOne(id);

        // Read file from Disk and assign to object.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(pgmsAppFamilyAttach.getAttachDocName())
            .contentType(pgmsAppFamilyAttach.getAttachmentContentType())
            .filePath(PGMSManagementConstant.PGM_FALILY_ATTACH_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        pgmsAppFamilyAttach.setAttachment(goFile.fileData());

        return Optional.ofNullable(pgmsAppFamilyAttach)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsAppFamilyAttachs/:id -> delete the "id" pgmsAppFamilyAttach.
     */
    @RequestMapping(value = "/pgmsAppFamilyAttachs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsAppFamilyAttach(@PathVariable Long id) {
        log.debug("REST request to delete PgmsAppFamilyAttach : {}", id);
        pgmsAppFamilyAttachRepository.delete(id);
        pgmsAppFamilyAttachSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsAppFamilyAttach", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsAppFamilyAttachs/:query -> search for the pgmsAppFamilyAttach corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsAppFamilyAttachs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppFamilyAttach> searchPgmsAppFamilyAttachs(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsAppFamilyAttachSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /pgmsAppFamilyAttachsByTypeAndPension -> get all the pgmsAppFamilyAttachsByTypeAndPension.
     */
    @RequestMapping(value = "/pgmsAppFamilyAttachsByTypeAndPension/{attacheType}/{familyPensionId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppFamilyAttach> getAttachementListByTypeAndId(@PathVariable String attacheType, @PathVariable Long familyPensionId)
    {
        log.debug("REST request to pgmsAppFamilyAttachsByTypeAndPension : attachType: {} , familyPension: {}", attacheType, familyPensionId);

        List<PgmsRetirmntAttachInfo> attachmentList = pgmsRetirmntAttachInfoRepository.findAllByAttachType(attacheType);

        List<PgmsAppFamilyAttach> familyAttachList = pgmsAppFamilyAttachRepository.findAllByAppFamilyPenId(familyPensionId);
        List<PgmsAppFamilyAttach> familyAttachListNew = new ArrayList<PgmsAppFamilyAttach>();

        for(PgmsRetirmntAttachInfo attachInfo: attachmentList)
        {
            boolean hasAttachment = false;
            for(PgmsAppFamilyAttach familyAttach: familyAttachList)
            {
                if(familyAttach.getAttachName().equals(attachInfo))
                {
                    familyAttachListNew.add(familyAttach);
                    hasAttachment = true;
                }

            }
            if(hasAttachment==false)
            {
                PgmsAppFamilyAttach tempFamilyAttach = new PgmsAppFamilyAttach();
                tempFamilyAttach.setAttachName(attachInfo);
                familyAttachListNew.add(tempFamilyAttach);
            }
        }
        log.debug("ListSize: "+familyAttachListNew.size());
        return familyAttachListNew;
    }
}
