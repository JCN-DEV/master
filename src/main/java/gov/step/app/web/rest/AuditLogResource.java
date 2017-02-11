package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AuditLog;
import gov.step.app.repository.AuditLogRepository;
import gov.step.app.repository.search.AuditLogSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AuditLog.
 */
@RestController
@RequestMapping("/api")
public class AuditLogResource {

    private final Logger log = LoggerFactory.getLogger(AuditLogResource.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Inject
    private AuditLogRepository auditLogRepository;

    @Inject
    private AuditLogSearchRepository auditLogSearchRepository;

    /**
     * POST  /auditLogs -> Create a new auditLog.
     */
    @RequestMapping(value = "/auditLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuditLog> createAuditLog(@RequestBody AuditLog auditLog) throws URISyntaxException {
        log.debug("REST request to save AuditLog : {}", auditLog);
        if (auditLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new auditLog cannot already have an ID").body(null);
        }

       /*
        IP Address
         */
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        /*
        MAC Address
         */

        /*StringBuilder sb = new StringBuilder();
        InetAddress ip;
        try {

            //ip = InetAddress.getLocalHost();
            ip = InetAddress.getByName(ipAddress);
            System.out.println("Current IP address : " + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");


            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());

        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (SocketException e){

            e.printStackTrace();

        }*/

        /*
        Host Name
         */
        String hostName = request.getRemoteHost();
        try {
            if (hostName.equals(request.getRemoteAddr())) {
                InetAddress addr = InetAddress.getByName(request.getRemoteAddr());
                hostName = addr.getHostName();
            }

            if (InetAddress.getLocalHost().getHostAddress().equals(request.getRemoteAddr())) {
                hostName = "Local Host";
            }

        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("\n Tired"+ipAddress+"---"+hostName);
        auditLog.setUserId(SecurityUtils.getCurrentUserId().toString());
        DateResource dateResource = new DateResource();
        auditLog.setCreateDate(dateResource.getDateAsLocalDate());
        auditLog.setCreateBy(SecurityUtils.getCurrentUserId());
        auditLog.setStatus(true);
        //auditLog.setUserName(SecurityUtils.getCurrentUser().getUsername());
        auditLog.setUserName(SecurityUtils.getCurrentUser().getUsername());

        //System.out.println("\n User Name >>>>>>>>>>>>>>>>>>> "+SecurityUtils.getCurrentUser().getUsername());

        auditLog.setEventTime(dateResource.getDateAsLocalDate());
        auditLog.setUserIpAddress(ipAddress);
        auditLog.setDeviceName(hostName);
        //auditLog.setUserMacAddress(sb.toString());


        AuditLog result = auditLogRepository.save(auditLog);
        auditLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/auditLogs/" + result.getId()))
            //.headers(HeaderUtil.createEntityCreationAlert("auditLog", result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /auditLogs -> Create a new auditLog.
     */
    @RequestMapping(value = "/uiAuditLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuditLog> createUiAuditLog(@RequestBody AuditLog auditLog) throws URISyntaxException {
        log.debug("REST request to save AuditLog : {}", auditLog);
        if (auditLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new auditLog cannot already have an ID").body(null);
        }
        AuditLog result = auditLogRepository.save(auditLog);
        auditLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/auditLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("auditLog", result.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/createAuditLogUI",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> createAuditLogUI( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        System.out.println("\n value :: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+value);

        /*Optional<AuditLog> dlContTypeSet = dlContTypeSetRepository.findOneByCode(value);

        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlContTypeSet)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }*/
        return null;
    }

    /**
     * PUT  /auditLogs -> Updates an existing auditLog.
     */
    @RequestMapping(value = "/auditLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuditLog> updateAuditLog(@RequestBody AuditLog auditLog) throws URISyntaxException {
        log.debug("REST request to update AuditLog : {}", auditLog);
        if (auditLog.getId() == null) {
            return createAuditLog(auditLog);
        }
        AuditLog result = auditLogRepository.save(auditLog);
        auditLogSearchRepository.save(auditLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("auditLog", auditLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auditLogs -> get all the auditLogs.
     */
    @RequestMapping(value = "/auditLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AuditLog>> getAllAuditLogs(Pageable pageable)
        throws URISyntaxException {
        Page<AuditLog> page = auditLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auditLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /auditLogs/:id -> get the "id" auditLog.
     */
    @RequestMapping(value = "/auditLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuditLog> getAuditLog(@PathVariable Long id) {
        log.debug("REST request to get AuditLog : {}", id);
        return Optional.ofNullable(auditLogRepository.findOne(id))
            .map(auditLog -> new ResponseEntity<>(
                auditLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /auditLogs/:id -> delete the "id" auditLog.
     */
    @RequestMapping(value = "/auditLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAuditLog(@PathVariable Long id) {
        log.debug("REST request to delete AuditLog : {}", id);
        auditLogRepository.delete(id);
        auditLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("auditLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/auditLogs/:query -> search for the auditLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/auditLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuditLog> searchAuditLogs(@PathVariable String query) {
        return StreamSupport
            .stream(auditLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
