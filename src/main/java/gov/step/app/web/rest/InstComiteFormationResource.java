package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstComiteFormation;
import gov.step.app.domain.InstMemShip;
import gov.step.app.domain.User;
import gov.step.app.repository.InstComiteFormationRepository;
import gov.step.app.repository.InstMemShipRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.InstComiteFormationSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstComiteFormation.
 */
@RestController
@RequestMapping("/api")
public class InstComiteFormationResource {

    private final Logger log = LoggerFactory.getLogger(InstComiteFormationResource.class);

    @Inject
    private InstComiteFormationRepository instComiteFormationRepository;

    @Inject
    private InstComiteFormationSearchRepository instComiteFormationSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstMemShipRepository instMemShipRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /instComiteFormations -> Create a new instComiteFormation.
     */
    @RequestMapping(value = "/instComiteFormations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstComiteFormation> createInstComiteFormation(@RequestBody InstComiteFormation instComiteFormation) throws URISyntaxException {
        log.debug("REST request to save InstComiteFormation : {}", instComiteFormation);
        if (instComiteFormation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instComiteFormation cannot already have an ID").body(null);
        }

        instComiteFormation.setInstitute(instituteRepository.findOneByUserIsCurrentUser());
        InstComiteFormation result = instComiteFormationRepository.save(instComiteFormation);
        instComiteFormationSearchRepository.save(result);

        return ResponseEntity.created(new URI("/api/instComiteFormations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instComiteFormation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instComiteFormations -> Updates an existing instComiteFormation.
     */
    @RequestMapping(value = "/instComiteFormations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstComiteFormation> updateInstComiteFormation(@RequestBody InstComiteFormation instComiteFormation) throws URISyntaxException {
        log.debug("REST request to update InstComiteFormation : {}", instComiteFormation);
        //log.debug("REST request to update InstComiteFormation instmembership : {}", instComiteFormation.getInstMemShipsnstMemShip().size());
        if (instComiteFormation.getId() == null) {
            return createInstComiteFormation(instComiteFormation);
        }
        InstComiteFormation result = instComiteFormationRepository.save(instComiteFormation);
        instComiteFormationSearchRepository.save(instComiteFormation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instComiteFormation", instComiteFormation.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instComiteFormations -> Updates an existing instComiteFormation.
     */
    @RequestMapping(value = "/instComiteFormations/instMemShip/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstComiteFormation> updateInstComiteFormationWithInstMem(@RequestBody InstMemShip instMemShip, @PathVariable Long id) throws URISyntaxException {
        //InstComiteFormation instComiteFormation = instComiteFormationRepository.findByIdAndFetchRolesEagerly(id);
        InstComiteFormation instComiteFormation = instComiteFormationRepository.findOne(id);
        log.debug("REST request to update InstComiteFormation : {}", instComiteFormation);
        if (instComiteFormation.getId() == null) {
            return createInstComiteFormation(instComiteFormation);
        }
       // InstMemShip instMemShip = instMemShipRepository.findOne(instMemShipId);
        /*Set<InstMemShip> instMemShipSet = instComiteFormation.getInstMemShipsnstMemShip();
        instMemShipSet.add(instMemShip);

        instComiteFormation.setInstMemShipsnstMemShip(instMemShipSet);*/
        InstComiteFormation result = instComiteFormationRepository.save(instComiteFormation);
        instComiteFormationSearchRepository.save(instComiteFormation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instComiteFormation", instComiteFormation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instComiteFormations -> get all the instComiteFormations.
     */
    @RequestMapping(value = "/instComiteFormations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstComiteFormation>> getAllInstComiteFormations(Pageable pageable)
        throws URISyntaxException {
        Page<InstComiteFormation> page = instComiteFormationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instComiteFormations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instMemShips -> get all the instMemShips.
     */
    @RequestMapping(value = "/instComiteFormations/institute/current",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstComiteFormation>> getAllInstCmtFormationOfCurrent(Pageable pageable)
        throws URISyntaxException {
        Page<InstComiteFormation> page = instComiteFormationRepository.findAllByInstitute(pageable, instituteRepository.findOneByUserIsCurrentUser().getId());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instComiteFormations/institute/current");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instComiteFormations/:id -> get the "id" instComiteFormation.
     */
    @RequestMapping(value = "/instComiteFormations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstComiteFormation> getInstComiteFormation(@PathVariable Long id) {
        log.debug("REST request to get InstComiteFormation : {}", id);
        return Optional.ofNullable(instComiteFormationRepository.findOne(id))
            .map(instComiteFormation -> new ResponseEntity<>(
                instComiteFormation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /instComiteFormations/:id -> get the "id" instComiteFormation.
     */
    @RequestMapping(value = "/instComiteFormations/withMembsers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstComiteFormation> getInstComiteFormationWithMembers(@PathVariable Long id) {
        log.debug("REST request to get InstComiteFormation : {}", id);
        //InstComiteFormation instComiteFormation1 = instComiteFormationRepository.findByIdAndFetchRolesEagerly(id);
        InstComiteFormation instComiteFormation1 = instComiteFormationRepository.findOne(id);
        //instComiteFormation1.getInstMemShipsnstMemShip().size();
        return Optional.ofNullable(instComiteFormation1)
            .map(instComiteFormation -> new ResponseEntity<>(
                instComiteFormation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instComiteFormations/:id -> delete the "id" instComiteFormation.
     */
    @RequestMapping(value = "/instComiteFormations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstComiteFormation(@PathVariable Long id) {
        log.debug("REST request to delete InstComiteFormation : {}", id);
        instComiteFormationRepository.delete(id);
        instComiteFormationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instComiteFormation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instComiteFormations/:query -> search for the instComiteFormation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instComiteFormations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstComiteFormation> searchInstComiteFormations(@PathVariable String query) {
        return StreamSupport
            .stream(instComiteFormationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
