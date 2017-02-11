package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Employer;
import gov.step.app.domain.NotificationStep;
import gov.step.app.repository.EmployerRepository;
import gov.step.app.repository.NotificationStepRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.EmployerSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.AttachmentUtil;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Employer.
 */
@RestController
@RequestMapping("/api")
public class EmployerResource {

    private final Logger log = LoggerFactory.getLogger(EmployerResource.class);

    @Inject
    private EmployerRepository employerRepository;

    @Inject
    private EmployerSearchRepository employerSearchRepository;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private UserRepository userRepository;
    /**
     * POST  /employers -> Create a new employer.
     */
    @RequestMapping(value = "/employers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Employer> createEmployer(@Valid @RequestBody Employer employer) throws URISyntaxException {
        log.debug("REST request to save Employer : {}", employer);
        if (employer.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employer cannot already have an ID").body(null);
        }

        employer.setDateCrated(LocalDate.now());
        employer.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification("Your organization has approved by admin");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("employer.job-list");
        notificationSteps.setUserId(employer.getUser().getId());
        notificationStepRepository.save(notificationSteps);

        Employer result = employerRepository.save(employer);
        employerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employers -> Updates an existing employer.
     */
    @RequestMapping(value = "/employers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Employer> updateEmployer(@Valid @RequestBody Employer employer) throws URISyntaxException {
        log.debug("REST request to update Employer : {}", employer);
        if (employer.getId() == null) {
            return createEmployer(employer);
        }
        employer.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        employer.setDateModified(LocalDate.now());
        Employer result = employerRepository.save(employer);
        employerSearchRepository.save(employer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employer", employer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employers -> get all the employers.
     */
    @RequestMapping(value = "/employers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Employer>> getAllEmployers(Pageable pageable)
        throws URISyntaxException {
        Page<Employer> page = employerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employers/my -> get my employer.
     */
    @RequestMapping(value = "/employers/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Employer> getMyEmployer() {
        Employer employer = employerRepository.findOneByUserIsCurrentUser();
        String filepath="/backup/jobportal/";
        if(employer.getCompanyLogoName() !=null && employer.getCompanyLogoName().length()>0){
            try {
                employer.setCompanyLogo(AttachmentUtil.retriveAttachment(filepath, employer.getCompanyLogoName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(employer)
            .map(employer1 -> new ResponseEntity<>(
                employer,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(
        value = "/employers/name",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Employer findExistingEmployer(@PathVariable String name) throws URISyntaxException {
        Employer employeeApplications=employerRepository.findExistingEmployer(name);
        return employeeApplications;
    }

    /**
     * GET  /employers/:id -> get the "id" employer.
     */
    @RequestMapping(value = "/employers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Employer> getEmployer(@PathVariable Long id) {
        log.debug("REST request to get Employer : {}", id);
        return Optional.ofNullable(employerRepository.findOne(id))
            .map(employer -> new ResponseEntity<>(
                employer,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employers/:id -> delete the "id" employer.
     */
    @RequestMapping(value = "/employers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        log.debug("REST request to delete Employer : {}", id);
        employerRepository.delete(id);
        employerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employer", id.toString())).build();
    }

    /**
     * GET  /employers/user/:id -> get the employer by user id
     */
    @RequestMapping(value = "/employers/user/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Employer> getEmployerByUserId(@PathVariable Long id) {
        log.debug("REST request to get Employer : {}", id);

        Employer employer = employerRepository.findOneByUserId(id);
        String filepath="/backup/jobportal/";
        if(employer != null && employer.getCompanyLogoName() != null){
            try {
                employer.setCompanyLogo(AttachmentUtil.retriveAttachment(filepath, employer.getCompanyLogoName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(employer)
            .map(employer1 -> new ResponseEntity<>(
                employer,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * SEARCH  /_search/employers/:query -> search for the employer corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/employers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Employer> searchEmployers(@PathVariable String query) {
        return StreamSupport
            .stream(employerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
