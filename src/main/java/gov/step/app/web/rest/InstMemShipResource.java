package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstLevel;
import gov.step.app.domain.InstMemShip;
import gov.step.app.domain.User;
import gov.step.app.repository.InstMemShipRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.InstMemShipSearchRepository;
import gov.step.app.service.UserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstMemShip.
 */
@RestController
@RequestMapping("/api")
public class InstMemShipResource {

    private final Logger log = LoggerFactory.getLogger(InstMemShipResource.class);

    @Inject
    private InstMemShipRepository instMemShipRepository;

    @Inject
    private InstMemShipSearchRepository instMemShipSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private UserService userService;

    /**
     * POST  /instMemShips -> Create a new instMemShip.
     */
    @RequestMapping(value = "/instMemShips",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstMemShip> createInstMemShip(@RequestBody InstMemShip instMemShip) throws URISyntaxException {
        log.debug("REST request to save InstMemShip : {}", instMemShip);
        if (instMemShip.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instMemShip cannot already have an ID").body(null);
        }

        /*User user = instMemShip.getUser();
        user.setActivated(true);
        userService.activateRegistration(user.getActivationKey());

        List<User> users = instMemShipRepository.findAllActiveUserByInstitute(instituteRepository.findOneByUserIsCurrentUser().getId());
        for(User preUser : users){
            preUser.setActivated(false);
            userRepository.save(preUser);
        }*/
        instMemShip.setInstitute(instituteRepository.findOneByUserIsCurrentUser());
        InstMemShip result = instMemShipRepository.save(instMemShip);
        instMemShipSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instMemShips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instMemShip", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instMemShips -> Updates an existing instMemShip.
     */
    @RequestMapping(value = "/instMemShips",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstMemShip> updateInstMemShip(@RequestBody InstMemShip instMemShip) throws URISyntaxException {
        log.debug("REST request to update InstMemShip : {}", instMemShip);
        if (instMemShip.getId() == null) {
            return createInstMemShip(instMemShip);
        }
        InstMemShip result = instMemShipRepository.save(instMemShip);
        instMemShipSearchRepository.save(instMemShip);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instMemShip", instMemShip.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instMemShips -> get all the instMemShips.
     */
    @RequestMapping(value = "/instMemShips",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstMemShip>> getAllInstMemShips(Pageable pageable)
        throws URISyntaxException {
        //Page<InstMemShip> page = instMemShipRepository.findAll(pageable);
        Page<InstMemShip> page = instMemShipRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instMemShips");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /instMemShips -> get all the instMemShips.
     */
    @RequestMapping(value = "/instMemShips/users/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<User> getAllInstMemShips()
        throws URISyntaxException {
        //Page<InstMemShip> page = instMemShipRepository.findAll(pageable);
        return instMemShipRepository.findAllActiveUserByInstitute(instituteRepository.findOneByUserIsCurrentUser().getId());
    }

    /**
     * GET  /instMemShips -> get all the instMemShips.
     */
    @RequestMapping(value = "/instMemShips/institute/current",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstMemShip>> getAllInstMemShipsOfCurrent(Pageable pageable)
        throws URISyntaxException {
        Page<InstMemShip> page = instMemShipRepository.findAllByInstitute(pageable, instituteRepository.findOneByUserIsCurrentUser().getId());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instMemShips/institute/current");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instMemShips -> get one by email and current institute .
     */
    @RequestMapping(value = "/instMemShips/institute/current/findMember/{email:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstMemShip> getAllInstMemShipsOfCurrentFindMember(@PathVariable String email)
        throws URISyntaxException {
        //InstMemShip instMemShip = ;
        return Optional.ofNullable(instMemShipRepository.findOneByInstituteAndEmail(instituteRepository.findOneByUserIsCurrentUser().getId(), email))
            .map(instMemShip -> new ResponseEntity<>(
                instMemShip,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /instMemShips/:id -> get the "id" instMemShip.
     */
    @RequestMapping(value = "/instMemShips/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstMemShip> getInstMemShip(@PathVariable Long id) {
        log.debug("REST request to get InstMemShip : {}", id);
        return Optional.ofNullable(instMemShipRepository.findOne(id))
            .map(instMemShip -> new ResponseEntity<>(
                instMemShip,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/instMemShips/instEmailUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getInstEmailUnique( @RequestParam String value) {

        log.debug("REST request to get instMemShip by email : {}", value);

        Optional<InstMemShip> instMemShip = instMemShipRepository.findOneByEmail(value);


        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(instMemShip)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    /**
     * DELETE  /instMemShips/:id -> delete the "id" instMemShip.
     */
    @RequestMapping(value = "/instMemShips/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstMemShip(@PathVariable Long id) {
        log.debug("REST request to delete InstMemShip : {}", id);
        instMemShipRepository.delete(id);
        instMemShipSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instMemShip", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instMemShips/:query -> search for the instMemShip corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instMemShips/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstMemShip> searchInstMemShips(@PathVariable String query) {
        return StreamSupport
            .stream(instMemShipSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
