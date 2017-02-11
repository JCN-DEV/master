package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrNomineeInfo;
import gov.step.app.domain.HrEmpBankAccountInfo;
import gov.step.app.domain.PgmsAppRetirmntNmine;
import gov.step.app.domain.PgmsRetirmntAttachInfo;
import gov.step.app.domain.PgmsAppRetirmntPen;
import gov.step.app.domain.PgmsAppRetirmntAttach;
import gov.step.app.repository.HrNomineeInfoRepository;
import gov.step.app.repository.HrEmpBankAccountInfoRepository;
import gov.step.app.repository.PgmsAppRetirmntAttachRepository;
import gov.step.app.repository.PgmsAppRetirmntNmineRepository;
import gov.step.app.repository.PgmsAppRetirmntPenRepository;
import gov.step.app.repository.PgmsRetirmntAttachInfoRepository;
import gov.step.app.repository.search.PgmsAppRetirmntPenSearchRepository;
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
 * REST controller for managing PgmsAppRetirmntPen.
 */
@RestController
@RequestMapping("/api")
public class PgmsAppRetirmntPenResource {

    private final Logger log = LoggerFactory.getLogger(PgmsAppRetirmntPenResource.class);

    @Inject
    private PgmsAppRetirmntPenRepository pgmsAppRetirmntPenRepository;

    @Inject
    private PgmsAppRetirmntPenSearchRepository pgmsAppRetirmntPenSearchRepository;

    @Inject
    private PgmsAppRetirmntNmineRepository pgmsAppRetirmntNmineRepository;

    @Inject
    private HrNomineeInfoRepository hrNomineeInfoRepository;

    @Inject
    private HrEmpBankAccountInfoRepository hrEmpBankAccountInfoRepository;


    @Inject
    private PgmsRetirmntAttachInfoRepository pgmsRetirmntAttachInfoRepository;

    @Inject
    private PgmsAppRetirmntAttachRepository pgmsAppRetirmntAttachRepository;

    MiscFileUtilities fileUtils = new MiscFileUtilities();


    /**
     * POST  /pgmsAppRetirmntPens -> Create a new pgmsAppRetirmntPen.
     */
    @RequestMapping(value = "/pgmsAppRetirmntPens",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppRetirmntPen> createPgmsAppRetirmntPen(@Valid @RequestBody PgmsAppRetirmntPen pgmsAppRetirmntPen) throws URISyntaxException {
        log.debug("REST request to save PgmsAppRetirmntPen : {}", pgmsAppRetirmntPen);
        if (pgmsAppRetirmntPen.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsAppRetirmntPen cannot already have an ID").body(null);
        }
        PgmsAppRetirmntPen result = pgmsAppRetirmntPenRepository.save(pgmsAppRetirmntPen);
        pgmsAppRetirmntPenSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsAppRetirmntPens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsAppRetirmntPen", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsAppRetirmntPens -> Updates an existing pgmsAppRetirmntPen.
     */
    @RequestMapping(value = "/pgmsAppRetirmntPens",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppRetirmntPen> updatePgmsAppRetirmntPen(@Valid @RequestBody PgmsAppRetirmntPen pgmsAppRetirmntPen) throws URISyntaxException {
        log.debug("REST request to update PgmsAppRetirmntPen : {}", pgmsAppRetirmntPen);
        if (pgmsAppRetirmntPen.getId() == null) {
            return createPgmsAppRetirmntPen(pgmsAppRetirmntPen);
        }
        PgmsAppRetirmntPen result = pgmsAppRetirmntPenRepository.save(pgmsAppRetirmntPen);
        pgmsAppRetirmntPenSearchRepository.save(pgmsAppRetirmntPen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsAppRetirmntPen", pgmsAppRetirmntPen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsAppRetirmntPens -> get all the pgmsAppRetirmntPens.
     */
    @RequestMapping(value = "/pgmsAppRetirmntPens",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsAppRetirmntPen>> getAllPgmsAppRetirmntPens(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsAppRetirmntPen> page = pgmsAppRetirmntPenRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsAppRetirmntPens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsAppRetirmntPens/:id -> get the "id" pgmsAppRetirmntPen.
     */
    @RequestMapping(value = "/pgmsAppRetirmntPens/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppRetirmntPen> getPgmsAppRetirmntPen(@PathVariable Long id) {
        log.debug("REST request to get PgmsAppRetirmntPen : {}", id);
        return Optional.ofNullable(pgmsAppRetirmntPenRepository.findOne(id))
            .map(pgmsAppRetirmntPen -> new ResponseEntity<>(
                pgmsAppRetirmntPen,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsAppRetirmntPens/:id -> delete the "id" pgmsAppRetirmntPen.
     */
    @RequestMapping(value = "/pgmsAppRetirmntPens/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsAppRetirmntPen(@PathVariable Long id) {
        log.debug("REST request to delete PgmsAppRetirmntPen : {}", id);
        pgmsAppRetirmntPenRepository.delete(id);
        pgmsAppRetirmntPenSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsAppRetirmntPen", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsAppRetirmntPens/:query -> search for the pgmsAppRetirmntPen corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsAppRetirmntPens/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppRetirmntPen> searchPgmsAppRetirmntPens(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsAppRetirmntPenSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    /**
     * Hr Nominee Infor List for PGMS Retirement Pension Application
     * to the query.
     */
    @RequestMapping(value = "/HrRetirmntNminesInfos/{empId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrNomineeInfo> getHrNomineeInfoByemployeeInfoId(@PathVariable long empId) {
        log.debug("REST request to get HrRetirmntNminesInfos : empId: {}", empId );
        List<HrNomineeInfo> hrNomineInfos = hrNomineeInfoRepository.findAllByEmployeeInfo(empId);
        return hrNomineInfos;
    }

    /**
     * Hr Nominee Infor List for PGMS Retirement Pension Application
     * to the query.
     */
    @RequestMapping(value = "/RetirementNomineesInfos/{penId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppRetirmntNmine> getRetiremntNomineInfosInfoByPensionId(@PathVariable long penId) {
        log.debug("REST request to get RetirementNomineesInfos : penId: {}", penId );
        List<PgmsAppRetirmntNmine> retiremntNomineInfos = pgmsAppRetirmntNmineRepository.findAllByAppRetirmntPenIdOrderByIdAsc(penId);
        return retiremntNomineInfos;
    }

    /**
     * GET  /pgmsAppRetirementAttachsByTypeAndPension -> get all the pgmsAppRetiremeintAttachsByTypeAndPension.
     */
    @RequestMapping(value = "/pgmsAppRetirmntPensByTypeAndPen/{attacheType}/{retirementPenId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppRetirmntAttach> getRetirementAttachementListByTypeAndPensionId(@PathVariable String attacheType, @PathVariable Long retirementPenId)
    {
        log.debug("REST request to pgmsAppRetirmntPensByTypeAndPen : attachType: {} , retirementPenId: {}", attacheType, retirementPenId);

        List<PgmsRetirmntAttachInfo> attachmentList = pgmsRetirmntAttachInfoRepository.findAllByAttachType(attacheType);
        List<PgmsAppRetirmntAttach> retirmntAttachList = null;
        if (retirementPenId !=0) {
            retirmntAttachList = pgmsAppRetirmntAttachRepository.findAllByAppRetirmntPenId(retirementPenId);
        }
        List<PgmsAppRetirmntAttach> retirmntAttachListNew = new ArrayList<PgmsAppRetirmntAttach>();

        for(PgmsRetirmntAttachInfo attachInfo: attachmentList)
        {
            boolean hasAttachment = false;
            if (retirementPenId !=0) {
                for (PgmsAppRetirmntAttach retireAttach : retirmntAttachList) {
                    if (retireAttach.getAttachName().equals(attachInfo)) {
                        retirmntAttachListNew.add(retireAttach);
                        hasAttachment = true;
                    }

                }
            }
            if(hasAttachment==false)
            {
                PgmsAppRetirmntAttach tempRetireAttach = new PgmsAppRetirmntAttach();
                tempRetireAttach.setAttachName(attachInfo);
                retirmntAttachListNew.add(tempRetireAttach);
            }
        }
        log.debug("ListSize: "+retirmntAttachListNew.size());
        return retirmntAttachListNew;
    }

    /**
     *   Delete Retirement Nominee Infos by AppRetirementPenId
     */
    @RequestMapping(value = "/deleteRetirementNomineesInfos/{penId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    void deleteRetirementNomineeInfoByAppRetirmntPenId(@PathVariable Long penId) {
        log.debug("Delete Retirement Nominee Info by AppRetirmntPenId: {} ",penId);
        pgmsAppRetirmntNmineRepository.deleteByAppRetirmntPenId(penId);
    }

    /**
     *   Delete Retirement Attach Infos by AppRetirementPenId
     */
    @RequestMapping(value = "/deleteRetirementAttachInfos/{penId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    void deleteRetirementAttachInfoByAppRetirmntPenId(@PathVariable Long penId) {
        log.debug("delete Retirement Attach Infos: {} ",penId);
        pgmsAppRetirmntAttachRepository.deleteByAppRetirmntPenId(penId);
    }

    /**
     * Search Retirement Application Pending  Information
     * to the query.
     */
    @RequestMapping(value = "/pgmsAppRetirementPemdings/{statusType}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppRetirmntPen> getRetirementPensionApplicatinPendingList(@PathVariable String statusType) {
        log.debug("REST request to get pgmsAppRetirementPemdings : statusType: {}", statusType);
        List<PgmsAppRetirmntPen> retPendingList = pgmsAppRetirmntPenRepository.findAllByAprvStatusOrderByIdAsc(statusType);
        return retPendingList;
    }

    /**
     * GET  /pgmsAppRetirementAttachsByTypeAndPension -> get all the pgmsAppRetiremeintAttachsByTypeAndPension.
     */
    @RequestMapping(value = "/pgmsAppRetirmntPensByPenId/{retirementPenId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppRetirmntAttach> getRetirementAttachementListByPensionId(@PathVariable Long retirementPenId)
    {
        log.debug("REST request to pgmsAppRetirmntPensByPenId : retirementPenId: {}", retirementPenId);
        List<PgmsAppRetirmntAttach> retirmntAttachList = pgmsAppRetirmntAttachRepository.findAllByAppRetirmntPenId(retirementPenId);
        for (PgmsAppRetirmntAttach modelInfo : retirmntAttachList) {
            MiscFileInfo empPhoto = new MiscFileInfo();
            empPhoto.fileName(modelInfo.getAttachDocName())
                .contentType(modelInfo.getAttachmentContentType())
                .filePath(PGMSManagementConstant.PGM_RETIREMENT_ATTACH_FILE_DIR);
            empPhoto = fileUtils.readFileAsByte(empPhoto);
            modelInfo.setAttachment(empPhoto.fileData());
        }
        return retirmntAttachList;
    }

    /**
     * Hr Employee Bank Info for PGMS Retirement Pension Application
     * to the query.
     */
    @RequestMapping(value = "/bankInfoByEmpId/{empId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrEmpBankAccountInfo getEmpBankInfoByEmpId(@PathVariable long empId) {
        log.debug("REST request to get BankInfoByEmpId : empId: {}", empId);
        HrEmpBankAccountInfo bankInfo = hrEmpBankAccountInfoRepository.findByEmployeeInfo(empId);
        return bankInfo;
    }
}
