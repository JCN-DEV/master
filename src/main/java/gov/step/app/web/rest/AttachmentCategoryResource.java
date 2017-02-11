package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AttachmentCategory;
import gov.step.app.repository.AttachmentCategoryRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.AttachmentCategorySearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AttachmentCategory.
 */
@RestController
@RequestMapping("/api")
public class AttachmentCategoryResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentCategoryResource.class);

    @Inject
    private AttachmentCategoryRepository attachmentCategoryRepository;

    @Inject
    private AttachmentCategorySearchRepository attachmentCategorySearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /attachmentCategorys -> Create a new attachmentCategory.
     */
    @RequestMapping(value = "/attachmentCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AttachmentCategory> createAttachmentCategory(@Valid @RequestBody AttachmentCategory attachmentCategory) throws URISyntaxException {
        log.debug("REST request to save AttachmentCategory : {}", attachmentCategory);
        if (attachmentCategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new attachmentCategory cannot already have an ID").body(null);
        }
        attachmentCategory.setCreateDate(LocalDate.now());
        attachmentCategory.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        attachmentCategory.setDateCreated(LocalDate.now());
        AttachmentCategory result = attachmentCategoryRepository.save(attachmentCategory);
        attachmentCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/attachmentCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("attachmentCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attachmentCategorys -> Updates an existing attachmentCategory.
     */
    @RequestMapping(value = "/attachmentCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AttachmentCategory> updateAttachmentCategory(@Valid @RequestBody AttachmentCategory attachmentCategory) throws URISyntaxException {
        log.debug("REST request to update AttachmentCategory : {}", attachmentCategory);
        if (attachmentCategory.getId() == null) {
            return createAttachmentCategory(attachmentCategory);
        }
        attachmentCategory.setUpdateDate(LocalDate.now());
        attachmentCategory.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        AttachmentCategory result = attachmentCategoryRepository.save(attachmentCategory);
        attachmentCategorySearchRepository.save(attachmentCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("attachmentCategory", attachmentCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attachmentCategorys -> get all the attachmentCategorys.
     */
    @RequestMapping(value = "/attachmentCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AttachmentCategory>> getAllAttachmentCategorys(Pageable pageable)
        throws URISyntaxException {
        Page<AttachmentCategory> page = attachmentCategoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attachmentCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /attachmentCategorys/module/:id -> get the all attachmentCategory by module
     */
    @RequestMapping(value = "/attachmentCategorys/module/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AttachmentCategory> getAttachmentCategoryByModule(@PathVariable Long id)
        throws URISyntaxException {
            List<AttachmentCategory> attachmentCategories = attachmentCategoryRepository.findByModule(id);
            return  attachmentCategories;
    }

    /**
     * GET  /attachmentCategorys/module/:id -> get the all attachmentCategory by module
     */
    @RequestMapping(value = "/attachmentCategorys/applicationName/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AttachmentCategory> getAttachmentCategoryByApplicationName(@PathVariable String name)
        throws URISyntaxException {
        List<AttachmentCategory> attachmentCategories = attachmentCategoryRepository.findByApplicationName(name);
        return  attachmentCategories;
    }

    /**
     * GET  /attachmentCategorys/module/:id -> get the all attachmentCategory by module
     */
    @RequestMapping(value = "/attachmentCategorys/applicationName/type/{name}/{designationId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AttachmentCategory> getAttachmentCategoryByApplicationName(@PathVariable String name,@PathVariable Long designationId)
        throws URISyntaxException {
        List<AttachmentCategory> attachmentCategories = attachmentCategoryRepository.findByApplicationNameAndDesignation(name, designationId);
        return  attachmentCategories;
    }

    /**
     * GET  /attachmentCategorys/:id -> get the "id" attachmentCategory.
     */
    @RequestMapping(value = "/attachmentCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AttachmentCategory> getAttachmentCategory(@PathVariable Long id) {
        log.debug("REST request to get AttachmentCategory : {}", id);
        return Optional.ofNullable(attachmentCategoryRepository.findOne(id))
            .map(attachmentCategory -> new ResponseEntity<>(
                attachmentCategory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /attachmentCategorys/:id -> delete the "id" attachmentCategory.
     */
    @RequestMapping(value = "/attachmentCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAttachmentCategory(@PathVariable Long id) {
        log.debug("REST request to delete AttachmentCategory : {}", id);
        attachmentCategoryRepository.delete(id);
        attachmentCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("attachmentCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/attachmentCategorys/:query -> search for the attachmentCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/attachmentCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AttachmentCategory> searchAttachmentCategorys(@PathVariable String query) {
        return StreamSupport
            .stream(attachmentCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
