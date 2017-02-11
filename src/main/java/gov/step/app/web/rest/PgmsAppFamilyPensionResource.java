package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.domain.PgmsAppFamilyAttach;
import gov.step.app.domain.PgmsAppFamilyPension;
import gov.step.app.domain.PgmsRetirmntAttachInfo;
import gov.step.app.repository.HrEmployeeInfoRepository;
import gov.step.app.repository.PgmsAppFamilyAttachRepository;
import gov.step.app.repository.PgmsRetirmntAttachInfoRepository;
import gov.step.app.repository.PgmsAppFamilyPensionRepository;
import gov.step.app.repository.search.PgmsAppFamilyPensionSearchRepository;
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
 * REST controller for managing PgmsAppFamilyPension.
 */
@RestController
@RequestMapping("/api")
public class PgmsAppFamilyPensionResource {

    private final Logger log = LoggerFactory.getLogger(PgmsAppFamilyPensionResource.class);

    @Inject
    private PgmsAppFamilyPensionRepository pgmsAppFamilyPensionRepository;

    @Inject
    private PgmsAppFamilyPensionSearchRepository pgmsAppFamilyPensionSearchRepository;

    @Inject
    private PgmsAppFamilyAttachRepository pgmsAppFamilyAttachRepository;

    @Inject
    private PgmsRetirmntAttachInfoRepository pgmsRetirmntAttachInfoRepository;

    @Inject
    private HrEmployeeInfoRepository hrEmployeeInfoRepository;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /pgmsAppFamilyPensions -> Create a new pgmsAppFamilyPension.
     */
    @RequestMapping(value = "/pgmsAppFamilyPensions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppFamilyPension> createPgmsAppFamilyPension(@Valid @RequestBody PgmsAppFamilyPension pgmsAppFamilyPension) throws URISyntaxException {
        log.debug("REST request to save PgmsAppFamilyPension : {}", pgmsAppFamilyPension);
        if (pgmsAppFamilyPension.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsAppFamilyPension cannot already have an ID").body(null);
        }
        PgmsAppFamilyPension result = pgmsAppFamilyPensionRepository.save(pgmsAppFamilyPension);
        pgmsAppFamilyPensionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsAppFamilyPensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsAppFamilyPension", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsAppFamilyPensions -> Updates an existing pgmsAppFamilyPension.
     */
    @RequestMapping(value = "/pgmsAppFamilyPensions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppFamilyPension> updatePgmsAppFamilyPension(@Valid @RequestBody PgmsAppFamilyPension pgmsAppFamilyPension) throws URISyntaxException {
        log.debug("REST request to update PgmsAppFamilyPension : {}", pgmsAppFamilyPension);
        if (pgmsAppFamilyPension.getId() == null) {
            return createPgmsAppFamilyPension(pgmsAppFamilyPension);
        }
        PgmsAppFamilyPension result = pgmsAppFamilyPensionRepository.save(pgmsAppFamilyPension);
        pgmsAppFamilyPensionSearchRepository.save(pgmsAppFamilyPension);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsAppFamilyPension", pgmsAppFamilyPension.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsAppFamilyPensions -> get all the pgmsAppFamilyPensions.
     */
    @RequestMapping(value = "/pgmsAppFamilyPensions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsAppFamilyPension>> getAllPgmsAppFamilyPensions(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsAppFamilyPension> page = pgmsAppFamilyPensionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsAppFamilyPensions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsAppFamilyPensions/:id -> get the "id" pgmsAppFamilyPension.
     */
    @RequestMapping(value = "/pgmsAppFamilyPensions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppFamilyPension> getPgmsAppFamilyPension(@PathVariable Long id) {
        log.debug("REST request to get PgmsAppFamilyPension : {}", id);
        return Optional.ofNullable(pgmsAppFamilyPensionRepository.findOne(id))
            .map(pgmsAppFamilyPension -> new ResponseEntity<>(
                pgmsAppFamilyPension,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsAppFamilyPensions/:id -> delete the "id" pgmsAppFamilyPension.
     */
    @RequestMapping(value = "/pgmsAppFamilyPensions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsAppFamilyPension(@PathVariable Long id) {
        log.debug("REST request to delete PgmsAppFamilyPension : {}", id);
        pgmsAppFamilyPensionRepository.delete(id);
        pgmsAppFamilyPensionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsAppFamilyPension", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsAppFamilyPensions/:query -> search for the pgmsAppFamilyPension corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsAppFamilyPensions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppFamilyPension> searchPgmsAppFamilyPensions(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsAppFamilyPensionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /pgmsAppFamilyAttachsByTypeAndPension -> get all the pgmsAppFamilyAttachsByTypeAndPension.
     */
    @RequestMapping(value = "/pgmsAppFamilyAttachsByTypeAndPen/{attacheType}/{familyPensionId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppFamilyAttach> getAttachementListByTypeAndPensionId(@PathVariable String attacheType, @PathVariable Long familyPensionId)
    {
        log.debug("REST request to pgmsAppFamilyAttachsByTypeAndPen : attachType: {} , familyPension: {}", attacheType, familyPensionId);

        List<PgmsRetirmntAttachInfo> attachmentList = pgmsRetirmntAttachInfoRepository.findAllByAttachType(attacheType);
        List<PgmsAppFamilyAttach> familyAttachList = null;
        if (familyPensionId !=0) {
            familyAttachList = pgmsAppFamilyAttachRepository.findAllByAppFamilyPenId(familyPensionId);
        }
        List<PgmsAppFamilyAttach> familyAttachListNew = new ArrayList<PgmsAppFamilyAttach>();

        for(PgmsRetirmntAttachInfo attachInfo: attachmentList)
        {
            boolean hasAttachment = false;
            if (familyPensionId !=0) {
                for (PgmsAppFamilyAttach familyAttach : familyAttachList) {
                    if (familyAttach.getAttachName().equals(attachInfo)) {
                        familyAttachListNew.add(familyAttach);
                        hasAttachment = true;
                    }

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

    /**
     * Search HREMPLOYEE Information
     * to the query.
     */
    @RequestMapping(value = "/pgmsAppFamilyPenEmpInfo/{employeeId}/{nid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrEmployeeInfo getHrEmployeeInfoByEmployeeIDAndNationalId(@PathVariable String employeeId, @PathVariable String nid) {
        log.debug("REST request to get pgmsAppFamilyPenEmpInfo : employeeId: {}, nid: {}", employeeId,nid);
       HrEmployeeInfo PgmsHrEmpInfo = null;
        if(employeeId.equals(null))
        {
            PgmsHrEmpInfo = hrEmployeeInfoRepository.findByNationalId(nid);
        }
        else
        {
            PgmsHrEmpInfo = hrEmployeeInfoRepository.findByEmployeeIdAndNationalId(employeeId,nid);
        }
        //HrEmployeeInfo PgmsHrEmpInfo = hrEmployeeInfoRepository.findByNationalId(nid);
        return PgmsHrEmpInfo;
    }

    /**
     * Search Family Application Pending  Information
     * to the query.
     */
    @RequestMapping(value = "/pgmsAppFamilyPemdings/{statusType}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppFamilyPension> getFamilyApplicatinPendingList(@PathVariable String statusType) {
        log.debug("REST request to get pgmsAppFamilyPemdings : statusType: {}", statusType);
        List<PgmsAppFamilyPension> pendingList = pgmsAppFamilyPensionRepository.findAllByAprvStatusOrderByIdAsc(statusType);
        return pendingList;
    }

    /**
     * GET  /pgmsAppFamilyAttachsByTypeAndPension -> get all the pgmsAppFamilyAttachsByPension Id.
     */
    @RequestMapping(value = "/pgmsAppFamilyAttachsByPenId/{familyPensionId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppFamilyAttach> getAttachementListByPensionId(@PathVariable Long familyPensionId)
    {
        log.debug("REST request to pgmsAppFamilyAttachsByPenId : familyPension: {}", familyPensionId);

        List<PgmsAppFamilyAttach> familyAttachList = pgmsAppFamilyAttachRepository.findAllByAppFamilyPenId(familyPensionId);
        for (PgmsAppFamilyAttach modelInfo : familyAttachList) {
            MiscFileInfo empPhoto = new MiscFileInfo();
            empPhoto.fileName(modelInfo.getAttachDocName())
                .contentType(modelInfo.getAttachmentContentType())
                .filePath(PGMSManagementConstant.PGM_FALILY_ATTACH_FILE_DIR);
            empPhoto = fileUtils.readFileAsByte(empPhoto);
            modelInfo.setAttachment(empPhoto.fileData());
        }

        return familyAttachList;
    }
}
