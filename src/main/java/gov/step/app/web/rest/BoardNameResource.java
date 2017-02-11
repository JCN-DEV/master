package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.BoardName;
import gov.step.app.repository.BoardNameRepository;
import gov.step.app.repository.search.BoardNameSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BoardName.
 */
@RestController
@RequestMapping("/api")
public class BoardNameResource {

    private final Logger log = LoggerFactory.getLogger(BoardNameResource.class);

    @Inject
    private BoardNameRepository boardNameRepository;

    @Inject
    private BoardNameSearchRepository boardNameSearchRepository;

    /**
     * POST  /boardNames -> Create a new boardName.
     */
    @RequestMapping(value = "/boardNames",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BoardName> createBoardName(@RequestBody BoardName boardName) throws URISyntaxException {
        log.debug("REST request to save BoardName : {}", boardName);
        if (boardName.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new boardName cannot already have an ID").body(null);
        }
        BoardName result = boardNameRepository.save(boardName);
        boardNameSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/boardNames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("boardName", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /boardNames -> Updates an existing boardName.
     */
    @RequestMapping(value = "/boardNames",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BoardName> updateBoardName(@RequestBody BoardName boardName) throws URISyntaxException {
        log.debug("REST request to update BoardName : {}", boardName);
        if (boardName.getId() == null) {
            return createBoardName(boardName);
        }
        BoardName result = boardNameRepository.save(boardName);
        boardNameSearchRepository.save(boardName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("boardName", boardName.getId().toString()))
            .body(result);
    }

    /**
     * GET  /boardNames -> get all the boardNames.
     */
    @RequestMapping(value = "/boardNames",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BoardName>> getAllBoardNames(Pageable pageable)
        throws URISyntaxException {
        Page<BoardName> page = boardNameRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/boardNames");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /boardNames/:id -> get the "id" boardName.
     */
    @RequestMapping(value = "/boardNames/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BoardName> getBoardName(@PathVariable Long id) {
        log.debug("REST request to get BoardName : {}", id);
        return Optional.ofNullable(boardNameRepository.findOne(id))
            .map(boardName -> new ResponseEntity<>(
                boardName,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /boardNames/:id -> delete the "id" boardName.
     */
    @RequestMapping(value = "/boardNames/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBoardName(@PathVariable Long id) {
        log.debug("REST request to delete BoardName : {}", id);
        boardNameRepository.delete(id);
        boardNameSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("boardName", id.toString())).build();
    }

    /**
     * SEARCH  /_search/boardNames/:query -> search for the boardName corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/boardNames/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BoardName> searchBoardNames(@PathVariable String query) {
        return StreamSupport
            .stream(boardNameSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
