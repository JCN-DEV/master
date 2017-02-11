package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Country;
import gov.step.app.repository.CountryRepository;
import gov.step.app.repository.search.CountrySearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CountryResource REST controller.
 *
 * @see CountryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CountryResourceTest {

    private static final String DEFAULT_ISO_CODE3 = "AAAAA";
    private static final String UPDATED_ISO_CODE3 = "BBBBB";
    private static final String DEFAULT_ISO_CODE2 = "AAAAA";
    private static final String UPDATED_ISO_CODE2 = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CONTINENT = "AAAAA";
    private static final String UPDATED_CONTINENT = "BBBBB";
    private static final String DEFAULT_REGION = "AAAAA";
    private static final String UPDATED_REGION = "BBBBB";
    private static final String DEFAULT_SURFACE_AREA = "AAAAA";
    private static final String UPDATED_SURFACE_AREA = "BBBBB";
    private static final String DEFAULT_INDEP_YEAR = "AAAAA";
    private static final String UPDATED_INDEP_YEAR = "BBBBB";
    private static final String DEFAULT_POPULATION = "AAAAA";
    private static final String UPDATED_POPULATION = "BBBBB";
    private static final String DEFAULT_LIFE_EXPECTANCY = "AAAAA";
    private static final String UPDATED_LIFE_EXPECTANCY = "BBBBB";
    private static final String DEFAULT_GNP = "AAAAA";
    private static final String UPDATED_GNP = "BBBBB";
    private static final String DEFAULT_GNP_OLD = "AAAAA";
    private static final String UPDATED_GNP_OLD = "BBBBB";
    private static final String DEFAULT_LOCAL_NAME = "AAAAA";
    private static final String UPDATED_LOCAL_NAME = "BBBBB";
    private static final String DEFAULT_GOVERNMENTFORM = "AAAAA";
    private static final String UPDATED_GOVERNMENTFORM = "BBBBB";
    private static final String DEFAULT_CAPITAL = "AAAAA";
    private static final String UPDATED_CAPITAL = "BBBBB";
    private static final String DEFAULT_HEAD_OF_STATE = "AAAAA";
    private static final String UPDATED_HEAD_OF_STATE = "BBBBB";
    private static final String DEFAULT_CALLING_CODE = "AAAAA";
    private static final String UPDATED_CALLING_CODE = "BBBBB";

    @Inject
    private CountryRepository countryRepository;

    @Inject
    private CountrySearchRepository countrySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCountryMockMvc;

    private Country country;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CountryResource countryResource = new CountryResource();
        ReflectionTestUtils.setField(countryResource, "countryRepository", countryRepository);
        ReflectionTestUtils.setField(countryResource, "countrySearchRepository", countrySearchRepository);
        this.restCountryMockMvc = MockMvcBuilders.standaloneSetup(countryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        country = new Country();
        country.setIsoCode3(DEFAULT_ISO_CODE3);
        country.setIsoCode2(DEFAULT_ISO_CODE2);
        country.setName(DEFAULT_NAME);
        country.setContinent(DEFAULT_CONTINENT);
        country.setRegion(DEFAULT_REGION);
        country.setSurfaceArea(DEFAULT_SURFACE_AREA);
        country.setIndepYear(DEFAULT_INDEP_YEAR);
        country.setPopulation(DEFAULT_POPULATION);
        country.setLifeExpectancy(DEFAULT_LIFE_EXPECTANCY);
        country.setGnp(DEFAULT_GNP);
        country.setGnpOld(DEFAULT_GNP_OLD);
        country.setLocalName(DEFAULT_LOCAL_NAME);
        country.setGovernmentform(DEFAULT_GOVERNMENTFORM);
        country.setCapital(DEFAULT_CAPITAL);
        country.setHeadOfState(DEFAULT_HEAD_OF_STATE);
        country.setCallingCode(DEFAULT_CALLING_CODE);
    }

    @Test
    @Transactional
    public void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country

        restCountryMockMvc.perform(post("/api/countrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(country)))
                .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countrys.get(countrys.size() - 1);
        assertThat(testCountry.getIsoCode3()).isEqualTo(DEFAULT_ISO_CODE3);
        assertThat(testCountry.getIsoCode2()).isEqualTo(DEFAULT_ISO_CODE2);
        assertThat(testCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountry.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testCountry.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testCountry.getSurfaceArea()).isEqualTo(DEFAULT_SURFACE_AREA);
        assertThat(testCountry.getIndepYear()).isEqualTo(DEFAULT_INDEP_YEAR);
        assertThat(testCountry.getPopulation()).isEqualTo(DEFAULT_POPULATION);
        assertThat(testCountry.getLifeExpectancy()).isEqualTo(DEFAULT_LIFE_EXPECTANCY);
        assertThat(testCountry.getGnp()).isEqualTo(DEFAULT_GNP);
        assertThat(testCountry.getGnpOld()).isEqualTo(DEFAULT_GNP_OLD);
        assertThat(testCountry.getLocalName()).isEqualTo(DEFAULT_LOCAL_NAME);
        assertThat(testCountry.getGovernmentform()).isEqualTo(DEFAULT_GOVERNMENTFORM);
        assertThat(testCountry.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testCountry.getHeadOfState()).isEqualTo(DEFAULT_HEAD_OF_STATE);
        assertThat(testCountry.getCallingCode()).isEqualTo(DEFAULT_CALLING_CODE);
    }

    @Test
    @Transactional
    public void checkIsoCode3IsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setIsoCode3(null);

        // Create the Country, which fails.

        restCountryMockMvc.perform(post("/api/countrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(country)))
                .andExpect(status().isBadRequest());

        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setName(null);

        // Create the Country, which fails.

        restCountryMockMvc.perform(post("/api/countrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(country)))
                .andExpect(status().isBadRequest());

        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContinentIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setContinent(null);

        // Create the Country, which fails.

        restCountryMockMvc.perform(post("/api/countrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(country)))
                .andExpect(status().isBadRequest());

        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapitalIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCapital(null);

        // Create the Country, which fails.

        restCountryMockMvc.perform(post("/api/countrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(country)))
                .andExpect(status().isBadRequest());

        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCallingCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCallingCode(null);

        // Create the Country, which fails.

        restCountryMockMvc.perform(post("/api/countrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(country)))
                .andExpect(status().isBadRequest());

        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountrys() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countrys
        restCountryMockMvc.perform(get("/api/countrys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
                .andExpect(jsonPath("$.[*].isoCode3").value(hasItem(DEFAULT_ISO_CODE3.toString())))
                .andExpect(jsonPath("$.[*].isoCode2").value(hasItem(DEFAULT_ISO_CODE2.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT.toString())))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
                .andExpect(jsonPath("$.[*].surfaceArea").value(hasItem(DEFAULT_SURFACE_AREA.toString())))
                .andExpect(jsonPath("$.[*].indepYear").value(hasItem(DEFAULT_INDEP_YEAR.toString())))
                .andExpect(jsonPath("$.[*].population").value(hasItem(DEFAULT_POPULATION.toString())))
                .andExpect(jsonPath("$.[*].lifeExpectancy").value(hasItem(DEFAULT_LIFE_EXPECTANCY.toString())))
                .andExpect(jsonPath("$.[*].gnp").value(hasItem(DEFAULT_GNP.toString())))
                .andExpect(jsonPath("$.[*].gnpOld").value(hasItem(DEFAULT_GNP_OLD.toString())))
                .andExpect(jsonPath("$.[*].localName").value(hasItem(DEFAULT_LOCAL_NAME.toString())))
                .andExpect(jsonPath("$.[*].governmentform").value(hasItem(DEFAULT_GOVERNMENTFORM.toString())))
                .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.toString())))
                .andExpect(jsonPath("$.[*].headOfState").value(hasItem(DEFAULT_HEAD_OF_STATE.toString())))
                .andExpect(jsonPath("$.[*].callingCode").value(hasItem(DEFAULT_CALLING_CODE.toString())));
    }

    @Test
    @Transactional
    public void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc.perform(get("/api/countrys/{id}", country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(country.getId().intValue()))
            .andExpect(jsonPath("$.isoCode3").value(DEFAULT_ISO_CODE3.toString()))
            .andExpect(jsonPath("$.isoCode2").value(DEFAULT_ISO_CODE2.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.continent").value(DEFAULT_CONTINENT.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.surfaceArea").value(DEFAULT_SURFACE_AREA.toString()))
            .andExpect(jsonPath("$.indepYear").value(DEFAULT_INDEP_YEAR.toString()))
            .andExpect(jsonPath("$.population").value(DEFAULT_POPULATION.toString()))
            .andExpect(jsonPath("$.lifeExpectancy").value(DEFAULT_LIFE_EXPECTANCY.toString()))
            .andExpect(jsonPath("$.gnp").value(DEFAULT_GNP.toString()))
            .andExpect(jsonPath("$.gnpOld").value(DEFAULT_GNP_OLD.toString()))
            .andExpect(jsonPath("$.localName").value(DEFAULT_LOCAL_NAME.toString()))
            .andExpect(jsonPath("$.governmentform").value(DEFAULT_GOVERNMENTFORM.toString()))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL.toString()))
            .andExpect(jsonPath("$.headOfState").value(DEFAULT_HEAD_OF_STATE.toString()))
            .andExpect(jsonPath("$.callingCode").value(DEFAULT_CALLING_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get("/api/countrys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

		int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        country.setIsoCode3(UPDATED_ISO_CODE3);
        country.setIsoCode2(UPDATED_ISO_CODE2);
        country.setName(UPDATED_NAME);
        country.setContinent(UPDATED_CONTINENT);
        country.setRegion(UPDATED_REGION);
        country.setSurfaceArea(UPDATED_SURFACE_AREA);
        country.setIndepYear(UPDATED_INDEP_YEAR);
        country.setPopulation(UPDATED_POPULATION);
        country.setLifeExpectancy(UPDATED_LIFE_EXPECTANCY);
        country.setGnp(UPDATED_GNP);
        country.setGnpOld(UPDATED_GNP_OLD);
        country.setLocalName(UPDATED_LOCAL_NAME);
        country.setGovernmentform(UPDATED_GOVERNMENTFORM);
        country.setCapital(UPDATED_CAPITAL);
        country.setHeadOfState(UPDATED_HEAD_OF_STATE);
        country.setCallingCode(UPDATED_CALLING_CODE);

        restCountryMockMvc.perform(put("/api/countrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(country)))
                .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countrys.get(countrys.size() - 1);
        assertThat(testCountry.getIsoCode3()).isEqualTo(UPDATED_ISO_CODE3);
        assertThat(testCountry.getIsoCode2()).isEqualTo(UPDATED_ISO_CODE2);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testCountry.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testCountry.getSurfaceArea()).isEqualTo(UPDATED_SURFACE_AREA);
        assertThat(testCountry.getIndepYear()).isEqualTo(UPDATED_INDEP_YEAR);
        assertThat(testCountry.getPopulation()).isEqualTo(UPDATED_POPULATION);
        assertThat(testCountry.getLifeExpectancy()).isEqualTo(UPDATED_LIFE_EXPECTANCY);
        assertThat(testCountry.getGnp()).isEqualTo(UPDATED_GNP);
        assertThat(testCountry.getGnpOld()).isEqualTo(UPDATED_GNP_OLD);
        assertThat(testCountry.getLocalName()).isEqualTo(UPDATED_LOCAL_NAME);
        assertThat(testCountry.getGovernmentform()).isEqualTo(UPDATED_GOVERNMENTFORM);
        assertThat(testCountry.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testCountry.getHeadOfState()).isEqualTo(UPDATED_HEAD_OF_STATE);
        assertThat(testCountry.getCallingCode()).isEqualTo(UPDATED_CALLING_CODE);
    }

    @Test
    @Transactional
    public void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

		int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Get the country
        restCountryMockMvc.perform(delete("/api/countrys/{id}", country.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
