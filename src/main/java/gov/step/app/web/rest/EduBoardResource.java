package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.EduBoard;
import gov.step.app.domain.OrganizationType;
import gov.step.app.repository.EduBoardRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.EduBoardSearchRepository;
import gov.step.app.security.SecurityUtils;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing EduBoard.
 */
@RestController
@RequestMapping("/api")
public class EduBoardResource {

    private final Logger log = LoggerFactory.getLogger(EduBoardResource.class);

    @Inject
    private EduBoardRepository eduBoardRepository;

    @Inject
    private EduBoardSearchRepository eduBoardSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /eduBoards -> Create a new eduBoard.
     */
    @RequestMapping(value = "/eduBoards",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EduBoard> createEduBoard(@Valid @RequestBody EduBoard eduBoard) throws URISyntaxException {
        log.debug("REST request to save EduBoard : {}", eduBoard);
        if (eduBoard.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new eduBoard cannot already have an ID").body(null);
        }
        eduBoard.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        eduBoard.setCreateDate(LocalDate.now());
        EduBoard result = eduBoardRepository.save(eduBoard);
        eduBoardSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/eduBoards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("eduBoard", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eduBoards -> Updates an existing eduBoard.
     */
    @RequestMapping(value = "/eduBoards",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EduBoard> updateEduBoard(@Valid @RequestBody EduBoard eduBoard) throws URISyntaxException {
        log.debug("REST request to update EduBoard : {}", eduBoard);
        if (eduBoard.getId() == null) {
            return createEduBoard(eduBoard);
        }
        eduBoard.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        eduBoard.setUpdateDate(LocalDate.now());
        EduBoard result = eduBoardRepository.save(eduBoard);
        eduBoardSearchRepository.save(eduBoard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("eduBoard", eduBoard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eduBoards -> get all the eduBoards.
     */
    @RequestMapping(value = "/eduBoards",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EduBoard>> getAllEduBoards(Pageable pageable)
        throws URISyntaxException {
        Page<EduBoard> page = eduBoardRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eduBoards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /eduBoards -> get all the eduBoards.
     */
    @RequestMapping(value = "/eduBoards/boardType/{boardType}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EduBoard> getAllEduBoards(@PathVariable String boardType)
        throws URISyntaxException {
        return eduBoardRepository.findAllActiveByType(boardType);
    }

    /**
     * GET  /eduBoards/:id -> get the "id" eduBoard.
     */
    @RequestMapping(value = "/eduBoards/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EduBoard> getEduBoard(@PathVariable Long id) {
        log.debug("REST request to get EduBoard : {}", id);
        return Optional.ofNullable(eduBoardRepository.findOne(id))
            .map(eduBoard -> new ResponseEntity<>(
                eduBoard,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /eduBoards/:id -> delete the "id" eduBoard.
     */
    @RequestMapping(value = "/eduBoards/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEduBoard(@PathVariable Long id) {
        log.debug("REST request to delete EduBoard : {}", id);
        eduBoardRepository.delete(id);
        eduBoardSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("eduBoard", id.toString())).build();
    }

    /**
     * SEARCH  /_search/eduBoards/:query -> search for the eduBoard corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/eduBoards/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EduBoard> searchEduBoards(@PathVariable String query) {
        return StreamSupport
            .stream(eduBoardSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /*check Education Board exists by name*/
    @RequestMapping(value = "/eduBoard/checkByName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkEduBoardByName(@RequestParam String value) {

        EduBoard eduBoard = eduBoardRepository.findOneByName(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(eduBoard == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }
}
