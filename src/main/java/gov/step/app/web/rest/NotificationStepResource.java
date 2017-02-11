package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.NotificationStep;
import gov.step.app.repository.NotificationStepRepository;
import gov.step.app.repository.search.NotificationStepSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
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
 * REST controller for managing NotificationStep.
 */
@RestController
@RequestMapping("/api")
public class NotificationStepResource {

    private final Logger log = LoggerFactory.getLogger(NotificationStepResource.class);

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private NotificationStepSearchRepository notificationStepSearchRepository;

    /**
     * POST  /notificationSteps -> Create a new notificationStep.
     */
    @RequestMapping(value = "/notificationSteps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NotificationStep> createNotificationStep(@RequestBody NotificationStep notificationStep) throws URISyntaxException {
        log.debug("REST request to save NotificationStep : {}", notificationStep);
        if (notificationStep.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new notificationStep cannot already have an ID").body(null);
        }
        NotificationStep result = notificationStepRepository.save(notificationStep);
        notificationStepSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/notificationSteps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("notificationStep", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notificationSteps -> Updates an existing notificationStep.
     */
    @RequestMapping(value = "/notificationSteps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NotificationStep> updateNotificationStep(@RequestBody NotificationStep notificationStep) throws URISyntaxException {
        log.debug("REST request to update NotificationStep : {}", notificationStep);
        if (notificationStep.getId() == null) {
            return createNotificationStep(notificationStep);
        }
        NotificationStep result = notificationStepRepository.save(notificationStep);
        notificationStepSearchRepository.save(notificationStep);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("notificationStep", notificationStep.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notificationSteps -> get all the notificationSteps By ID.
     */
    @RequestMapping(value = "/notificationSteps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<NotificationStep>> getAllNotificationSteps(Pageable pageable)
        throws URISyntaxException {
        Page<NotificationStep> page = notificationStepRepository.findAll(pageable);
        Long userId = SecurityUtils.getCurrentUserId();

        System.out.println("\n User ID >>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+userId);

        page = notificationStepRepository.findAllnotificationByuserId(pageable, userId, true);

        System.out.println("\n Page >>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+page.getTotalPages());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notificationSteps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /notificationSteps -> get all the notificationSteps.
     */
    /*@RequestMapping(value = "/notificationSteps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<NotificationStep>> getAllNotificationSteps(Pageable pageable)
        throws URISyntaxException {
        Page<NotificationStep> page = notificationStepRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notificationSteps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }*/

    /**
     * GET  /notificationSteps/:id -> get the "id" notificationStep.
     */
    @RequestMapping(value = "/notificationSteps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NotificationStep> getNotificationStep(@PathVariable Long id) {
        log.debug("REST request to get NotificationStep : {}", id);
        return Optional.ofNullable(notificationStepRepository.findOne(id))
            .map(notificationStep -> new ResponseEntity<>(
                notificationStep,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /notificationSteps/:id -> delete the "id" notificationStep.
     */
    @RequestMapping(value = "/notificationSteps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNotificationStep(@PathVariable Long id) {
        log.debug("REST request to delete NotificationStep : {}", id);
        notificationStepRepository.delete(id);
        notificationStepSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("notificationStep", id.toString())).build();
    }

    /**
     * SEARCH  /_search/notificationSteps/:query -> search for the notificationStep corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/notificationSteps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NotificationStep> searchNotificationSteps(@PathVariable String query) {
        return StreamSupport
            .stream(notificationStepSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    public Boolean notificationRequest(NotificationStep notificationStep) throws URISyntaxException {

        /*NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification("Notification");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("mpo.dashboard");
        notificationSteps.setUserId(SecurityUtils.getCurrentUserId());*/


        Boolean notificationReturns = false;
        notificationStep.setCreateBy(SecurityUtils.getCurrentUserId());
        notificationStep.setCreateDate(DateResource.getDateAsLocalDate());

        log.debug("REST request to save NotificationStep : {}", notificationStep);
        if (notificationStep.getId() != null) {
            return notificationReturns;
        }
        NotificationStep result = notificationStepRepository.save(notificationStep);
        notificationStepSearchRepository.save(result);
        if (result != null){
            notificationReturns = true;
        }
        return notificationReturns;
    }
}
