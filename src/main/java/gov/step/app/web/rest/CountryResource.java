package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Country;
import gov.step.app.domain.User;
import gov.step.app.repository.CountryRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.CountrySearchRepository;
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
 * REST controller for managing Country.
 */
@RestController
@RequestMapping("/api")
public class CountryResource {

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    @Inject
    private CountryRepository countryRepository;

    @Inject
    private CountrySearchRepository countrySearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /countrys -> Create a new country.
     */
    @RequestMapping(value = "/countrys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Country> createCountry(@Valid @RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to save Country : {}", country);
        if (country.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new country cannot already have an ID").body(null);
        }
        country.setCreateDate(LocalDate.now());
        country.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        Country result = countryRepository.save(country);
        countrySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/countrys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("country", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /countrys -> Updates an existing country.
     */
    @RequestMapping(value = "/countrys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Country> updateCountry(@Valid @RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to update Country : {}", country);
        if (country.getId() == null) {
            return createCountry(country);
        }
        country.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        country.setUpdateDate(LocalDate.now());
        Country result = countryRepository.save(country);
        countrySearchRepository.save(country);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("country", country.getId().toString()))
            .body(result);
    }

    /**
     * GET  /countrys -> get all the countrys.
     */
    @RequestMapping(value = "/countrys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Country>> getAllCountrys(Pageable pageable)
        throws URISyntaxException {
        Page<Country> page = countryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/countrys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /countrys -> get all the countrys.
     */
    @RequestMapping(value = "/countrys/byName",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Country> getAllCountrysOrderByName()
        throws URISyntaxException {
        return countryRepository.findAllOrderByName();
        /*HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/countrys/byName");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);*/
    }

    /**
     * GET  /countrys/:id -> get the "id" country.
     */
    @RequestMapping(value = "/countrys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Country> getCountry(@PathVariable Long id) {
        log.debug("REST request to get Country : {}", id);
        return Optional.ofNullable(countryRepository.findOne(id))
            .map(country -> new ResponseEntity<>(
                country,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /countrys/:id -> delete the "id" country.
     */
    @RequestMapping(value = "/countrys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        log.debug("REST request to delete Country : {}", id);
        countryRepository.delete(id);
        countrySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("country", id.toString())).build();
    }

    /**
     * SEARCH  /_search/countrys/:query -> search for the country corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/countrys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Country> searchCountrys(@PathVariable String query) {
        return StreamSupport
            .stream(countrySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/country/checkCountryByName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkCountry(@RequestParam String value) {

        Country country = countryRepository.findOneByName(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(country == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/country/checkCountryByCallingCode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkCountryByCallingCode(@RequestParam String value) {

        Country country = countryRepository.findOneByCallingCode(value);

        Map map =new HashMap();

        map.put("value", value);

        if(country == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/country/checkCountryByIsoCode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkCountryByIsoCode(@RequestParam String value) {

        Country country = countryRepository.findOneByIsoCode(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(country == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }@RequestMapping(value = "/country/checkCountryByCapital/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkCountryByCapital(@RequestParam String value) {

        Country country = countryRepository.findOneByCapital(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(country == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }
}
