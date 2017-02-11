package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.domain.PgmsNotification;
import gov.step.app.repository.PgmsNotificationRepository;
import gov.step.app.repository.HrEmployeeInfoRepository;
import gov.step.app.repository.search.PgmsNotificationSearchRepository;
import gov.step.app.web.rest.dto.NotificationDto;
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
import gov.step.app.web.rest.jdbc.dao.PGMSJdbcDao;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PgmsNotification.
 */
@RestController
@RequestMapping("/api")
public class PgmsNotificationResource {

    private final Logger log = LoggerFactory.getLogger(PgmsNotificationResource.class);

    @Inject
    private PgmsNotificationRepository pgmsNotificationRepository;

    @Inject
    private PgmsNotificationSearchRepository pgmsNotificationSearchRepository;

    @Inject
    private HrEmployeeInfoRepository hrEmployeeInfoRepository;

    @Inject
    private PGMSJdbcDao pgmsJdbcDao;

    /**
     * POST  /pgmsNotifications -> Create a new pgmsNotification.
     */
    @RequestMapping(value = "/pgmsNotifications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsNotification> createPgmsNotification(@Valid @RequestBody PgmsNotification pgmsNotification) throws URISyntaxException {
        log.debug("REST request to save PgmsNotification : {}", pgmsNotification);
        if (pgmsNotification.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsNotification cannot already have an ID").body(null);
        }
        PgmsNotification result = pgmsNotificationRepository.save(pgmsNotification);
        pgmsNotificationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsNotifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsNotification", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsNotifications -> Updates an existing pgmsNotification.
     */
    @RequestMapping(value = "/pgmsNotifications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsNotification> updatePgmsNotification(@Valid @RequestBody PgmsNotification pgmsNotification) throws URISyntaxException {
        log.debug("REST request to update PgmsNotification : {}", pgmsNotification);
        if (pgmsNotification.getId() == null) {
            return createPgmsNotification(pgmsNotification);
        }
        PgmsNotification result = pgmsNotificationRepository.save(pgmsNotification);
        pgmsNotificationSearchRepository.save(pgmsNotification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsNotification", pgmsNotification.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsNotifications -> get all the pgmsNotifications.
     */
    @RequestMapping(value = "/pgmsNotifications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsNotification>> getAllPgmsNotifications(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsNotification> page = pgmsNotificationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsNotifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsNotifications/:id -> get the "id" pgmsNotification.
     */
    @RequestMapping(value = "/pgmsNotifications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsNotification> getPgmsNotification(@PathVariable Long id) {
        log.debug("REST request to get PgmsNotification : {}", id);
        return Optional.ofNullable(pgmsNotificationRepository.findOne(id))
            .map(pgmsNotification -> new ResponseEntity<>(
                pgmsNotification,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsNotifications/:id -> delete the "id" pgmsNotification.
     */
    @RequestMapping(value = "/pgmsNotifications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsNotification(@PathVariable Long id) {
        log.debug("REST request to delete PgmsNotification : {}", id);
        pgmsNotificationRepository.delete(id);
        pgmsNotificationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsNotification", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsNotifications/:query -> search for the pgmsNotification corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsNotifications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsNotification> searchPgmsNotifications(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsNotificationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Search Hr Employee Info
     * to the query.
     */

    /*@RequestMapping(value = "/pgmsNotificationsHrEmpInfo",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmployeeInfo> getHrEmployeeRetirementNoticeInfo() {
        log.debug("REST request to get pgmsNotificationsHrEmpInfo");
        List<HrEmployeeInfo> hrEmpListList = pgmsJdbcDao.findAll();
        //List<HrEmployeeInfo> hrEmpListList = hrEmployeeInfoRepository.findAll();

        log.debug("REST request to get pgmsNotificationsHrEmpInfo size: "+hrEmpListList.size());


        return hrEmpListList;
    }*/

    @RequestMapping(value = "/pgmsNotificationsHrEmpInfo",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmployeeInfo> getHrEmployeeRetirementNoticeInfo()
    {
        log.debug("REST request to get pgmsNotificationsHrEmpInfo:");

        List<HrEmployeeInfo> hrInfoList = hrEmployeeInfoRepository.findAll();
       // List<HrEmployeeInfo> hrInfoList = pgmsJdbcDao.findAllByRetirementDate(startDate,endDate);

        List<PgmsNotification> notificationListInfo = pgmsNotificationRepository.findAll();

        List<HrEmployeeInfo> notificationListNew = new ArrayList<HrEmployeeInfo>();

        for(HrEmployeeInfo hrInfo: hrInfoList)
        {
            boolean hasAttachment = false;
            for(PgmsNotification notificationInfo: notificationListInfo)
            {
                if(notificationInfo.getEmpId().equals(hrInfo.getId()))
                {
                   // notificationListNew.add(hrInfo);
                    hasAttachment = true;
                }

            }
            if(hasAttachment==false)
            {
                notificationListNew.add(hrInfo);
            }
        }

        log.debug("ListSize: "+notificationListNew.size());
        return notificationListNew;
    }



    /**
     * GET  /hrEmpPublicationInfosApprover/ -> process the approval request.
     */
    /*@RequestMapping(value = "/hrEmpPublicationInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody NotificationDto notificationDto) {
        log.debug("REST request to Approve hrEmpPublicationInfos POST: Type: {} ID: {}, comment : {}",notificationDto.getComments());

        for(PgmsNotification notification: notificationDto.getNotificationList())
        {

        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpPublicationInfo", null)).build();
    }*/
}
