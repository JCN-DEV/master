package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpAdvIncrementInfo;
import gov.step.app.repository.HrEmpAdvIncrementInfoRepository;
import gov.step.app.repository.search.HrEmpAdvIncrementInfoSearchRepository;
import gov.step.app.service.constnt.HRMManagementConstant;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing HrEmpAdvIncrementInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpAdvIncrementInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpAdvIncrementInfoResource.class);

    @Inject
    private HrEmpAdvIncrementInfoRepository hrEmpAdvIncrementInfoRepository;

    @Inject
    private HrEmpAdvIncrementInfoSearchRepository hrEmpAdvIncrementInfoSearchRepository;

    MiscFileUtilities fileUtils = new MiscFileUtilities();
    /**
     * POST  /hrEmpAdvIncrementInfos -> Create a new hrEmpAdvIncrementInfo.
     */
    @RequestMapping(value = "/hrEmpAdvIncrementInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAdvIncrementInfo> createHrEmpAdvIncrementInfo(@Valid @RequestBody HrEmpAdvIncrementInfo hrEmpAdvIncrementInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpAdvIncrementInfo : {}", hrEmpAdvIncrementInfo);
        if (hrEmpAdvIncrementInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpAdvIncrementInfo", "idexists", "A new hrEmpAdvIncrementInfo cannot already have an ID")).body(null);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpAdvIncrementInfo.getGoDoc())
            .fileName(hrEmpAdvIncrementInfo.getGoDocName())
            .contentType(hrEmpAdvIncrementInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.ADV_INCREMENT_FILE_DIR);

        goFile = fileUtils.saveFileAsByte(goFile);
        hrEmpAdvIncrementInfo.setGoDoc(new byte[1]);
        hrEmpAdvIncrementInfo.setGoDocName(goFile.fileName());

        HrEmpAdvIncrementInfo result = hrEmpAdvIncrementInfoRepository.save(hrEmpAdvIncrementInfo);
        hrEmpAdvIncrementInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpAdvIncrementInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpAdvIncrementInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpAdvIncrementInfos -> Updates an existing hrEmpAdvIncrementInfo.
     */
    @RequestMapping(value = "/hrEmpAdvIncrementInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAdvIncrementInfo> updateHrEmpAdvIncrementInfo(@Valid @RequestBody HrEmpAdvIncrementInfo hrEmpAdvIncrementInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpAdvIncrementInfo : {}", hrEmpAdvIncrementInfo);
        if (hrEmpAdvIncrementInfo.getId() == null) {
            return createHrEmpAdvIncrementInfo(hrEmpAdvIncrementInfo);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpAdvIncrementInfo.getGoDoc())
            .fileName(hrEmpAdvIncrementInfo.getGoDocName())
            .contentType(hrEmpAdvIncrementInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.ADV_INCREMENT_FILE_DIR);

        goFile = fileUtils.updateFileAsByte(goFile);
        hrEmpAdvIncrementInfo.setGoDoc(new byte[1]);
        hrEmpAdvIncrementInfo.setGoDocName(goFile.fileName());

        HrEmpAdvIncrementInfo result = hrEmpAdvIncrementInfoRepository.save(hrEmpAdvIncrementInfo);
        hrEmpAdvIncrementInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpAdvIncrementInfo", hrEmpAdvIncrementInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpAdvIncrementInfos -> get all the hrEmpAdvIncrementInfos.
     */
    @RequestMapping(value = "/hrEmpAdvIncrementInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpAdvIncrementInfo>> getAllHrEmpAdvIncrementInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpAdvIncrementInfos");
        Page<HrEmpAdvIncrementInfo> page = hrEmpAdvIncrementInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpAdvIncrementInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpAdvIncrementInfos/:id -> get the "id" hrEmpAdvIncrementInfo.
     */
    @RequestMapping(value = "/hrEmpAdvIncrementInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAdvIncrementInfo> getHrEmpAdvIncrementInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpAdvIncrementInfo : {}", id);
        HrEmpAdvIncrementInfo hrEmpAdvIncrementInfo = hrEmpAdvIncrementInfoRepository.findOne(id);

        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(hrEmpAdvIncrementInfo.getGoDocName())
            .contentType(hrEmpAdvIncrementInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.ADV_INCREMENT_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        hrEmpAdvIncrementInfo.setGoDoc(goFile.fileData());

        return Optional.ofNullable(hrEmpAdvIncrementInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpAdvIncrementInfos/:id -> delete the "id" hrEmpAdvIncrementInfo.
     */
    @RequestMapping(value = "/hrEmpAdvIncrementInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpAdvIncrementInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpAdvIncrementInfo : {}", id);
        hrEmpAdvIncrementInfoRepository.delete(id);
        hrEmpAdvIncrementInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpAdvIncrementInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpAdvIncrementInfos/:query -> search for the hrEmpAdvIncrementInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpAdvIncrementInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpAdvIncrementInfo> searchHrEmpAdvIncrementInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpAdvIncrementInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpAdvIncrementInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpAdvIncrementInfos/my -> get the current logged in hrEmpAdvIncrementInfos.
     */
    @RequestMapping(value = "/hrEmpAdvIncrementInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAdvIncrementInfo> getHrModelInfoByCurrentEmployee() {
        log.debug("REST request to get HrEmpAdvIncrementInfo by current logged in ");
        HrEmpAdvIncrementInfo modelInfo = hrEmpAdvIncrementInfoRepository.findOneByEmployeeIsCurrentUser();

        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(modelInfo.getGoDocName())
            .contentType(modelInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.ADV_INCREMENT_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        modelInfo.setGoDoc(goFile.fileData());

        return Optional.ofNullable(modelInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
