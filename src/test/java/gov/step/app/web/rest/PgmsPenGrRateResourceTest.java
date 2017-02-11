package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsPenGrRate;
import gov.step.app.repository.PgmsPenGrRateRepository;
import gov.step.app.repository.search.PgmsPenGrRateSearchRepository;

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
 * Test class for the PgmsPenGrRateResource REST controller.
 *
 * @see PgmsPenGrRateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsPenGrRateResourceTest {


    private static final Long DEFAULT_PEN_GR_SET_ID = 1L;
    private static final Long UPDATED_PEN_GR_SET_ID = 2L;

    private static final Long DEFAULT_WORKING_YEAR = 1L;
    private static final Long UPDATED_WORKING_YEAR = 2L;

    private static final Long DEFAULT_RATE_OF_PEN_GR = 1L;
    private static final Long UPDATED_RATE_OF_PEN_GR = 2L;

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    @Inject
    private PgmsPenGrRateRepository pgmsPenGrRateRepository;

    @Inject
    private PgmsPenGrRateSearchRepository pgmsPenGrRateSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsPenGrRateMockMvc;

    private PgmsPenGrRate pgmsPenGrRate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsPenGrRateResource pgmsPenGrRateResource = new PgmsPenGrRateResource();
        ReflectionTestUtils.setField(pgmsPenGrRateResource, "pgmsPenGrRateRepository", pgmsPenGrRateRepository);
        ReflectionTestUtils.setField(pgmsPenGrRateResource, "pgmsPenGrRateSearchRepository", pgmsPenGrRateSearchRepository);
        this.restPgmsPenGrRateMockMvc = MockMvcBuilders.standaloneSetup(pgmsPenGrRateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsPenGrRate = new PgmsPenGrRate();
        pgmsPenGrRate.setPenGrSetId(DEFAULT_PEN_GR_SET_ID);
        pgmsPenGrRate.setWorkingYear(DEFAULT_WORKING_YEAR);
        pgmsPenGrRate.setRateOfPenGr(DEFAULT_RATE_OF_PEN_GR);
        pgmsPenGrRate.setActiveStatus(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void createPgmsPenGrRate() throws Exception {
        int databaseSizeBeforeCreate = pgmsPenGrRateRepository.findAll().size();

        // Create the PgmsPenGrRate

        restPgmsPenGrRateMockMvc.perform(post("/api/pgmsPenGrRates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrRate)))
                .andExpect(status().isCreated());

        // Validate the PgmsPenGrRate in the database
        List<PgmsPenGrRate> pgmsPenGrRates = pgmsPenGrRateRepository.findAll();
        assertThat(pgmsPenGrRates).hasSize(databaseSizeBeforeCreate + 1);
        PgmsPenGrRate testPgmsPenGrRate = pgmsPenGrRates.get(pgmsPenGrRates.size() - 1);
        assertThat(testPgmsPenGrRate.getPenGrSetId()).isEqualTo(DEFAULT_PEN_GR_SET_ID);
        assertThat(testPgmsPenGrRate.getWorkingYear()).isEqualTo(DEFAULT_WORKING_YEAR);
        assertThat(testPgmsPenGrRate.getRateOfPenGr()).isEqualTo(DEFAULT_RATE_OF_PEN_GR);
        assertThat(testPgmsPenGrRate.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void checkPenGrSetIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsPenGrRateRepository.findAll().size();
        // set the field null
        pgmsPenGrRate.setPenGrSetId(null);

        // Create the PgmsPenGrRate, which fails.

        restPgmsPenGrRateMockMvc.perform(post("/api/pgmsPenGrRates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrRate)))
                .andExpect(status().isBadRequest());

        List<PgmsPenGrRate> pgmsPenGrRates = pgmsPenGrRateRepository.findAll();
        assertThat(pgmsPenGrRates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWorkingYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsPenGrRateRepository.findAll().size();
        // set the field null
        pgmsPenGrRate.setWorkingYear(null);

        // Create the PgmsPenGrRate, which fails.

        restPgmsPenGrRateMockMvc.perform(post("/api/pgmsPenGrRates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrRate)))
                .andExpect(status().isBadRequest());

        List<PgmsPenGrRate> pgmsPenGrRates = pgmsPenGrRateRepository.findAll();
        assertThat(pgmsPenGrRates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateOfPenGrIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsPenGrRateRepository.findAll().size();
        // set the field null
        pgmsPenGrRate.setRateOfPenGr(null);

        // Create the PgmsPenGrRate, which fails.

        restPgmsPenGrRateMockMvc.perform(post("/api/pgmsPenGrRates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrRate)))
                .andExpect(status().isBadRequest());

        List<PgmsPenGrRate> pgmsPenGrRates = pgmsPenGrRateRepository.findAll();
        assertThat(pgmsPenGrRates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsPenGrRates() throws Exception {
        // Initialize the database
        pgmsPenGrRateRepository.saveAndFlush(pgmsPenGrRate);

        // Get all the pgmsPenGrRates
        restPgmsPenGrRateMockMvc.perform(get("/api/pgmsPenGrRates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsPenGrRate.getId().intValue())))
                .andExpect(jsonPath("$.[*].penGrSetId").value(hasItem(DEFAULT_PEN_GR_SET_ID.intValue())))
                .andExpect(jsonPath("$.[*].workingYear").value(hasItem(DEFAULT_WORKING_YEAR.intValue())))
                .andExpect(jsonPath("$.[*].rateOfPenGr").value(hasItem(DEFAULT_RATE_OF_PEN_GR.intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getPgmsPenGrRate() throws Exception {
        // Initialize the database
        pgmsPenGrRateRepository.saveAndFlush(pgmsPenGrRate);

        // Get the pgmsPenGrRate
        restPgmsPenGrRateMockMvc.perform(get("/api/pgmsPenGrRates/{id}", pgmsPenGrRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsPenGrRate.getId().intValue()))
            .andExpect(jsonPath("$.penGrSetId").value(DEFAULT_PEN_GR_SET_ID.intValue()))
            .andExpect(jsonPath("$.workingYear").value(DEFAULT_WORKING_YEAR.intValue()))
            .andExpect(jsonPath("$.rateOfPenGr").value(DEFAULT_RATE_OF_PEN_GR.intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsPenGrRate() throws Exception {
        // Get the pgmsPenGrRate
        restPgmsPenGrRateMockMvc.perform(get("/api/pgmsPenGrRates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsPenGrRate() throws Exception {
        // Initialize the database
        pgmsPenGrRateRepository.saveAndFlush(pgmsPenGrRate);

		int databaseSizeBeforeUpdate = pgmsPenGrRateRepository.findAll().size();

        // Update the pgmsPenGrRate
        pgmsPenGrRate.setPenGrSetId(UPDATED_PEN_GR_SET_ID);
        pgmsPenGrRate.setWorkingYear(UPDATED_WORKING_YEAR);
        pgmsPenGrRate.setRateOfPenGr(UPDATED_RATE_OF_PEN_GR);
        pgmsPenGrRate.setActiveStatus(UPDATED_ACTIVE_STATUS);

        restPgmsPenGrRateMockMvc.perform(put("/api/pgmsPenGrRates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrRate)))
                .andExpect(status().isOk());

        // Validate the PgmsPenGrRate in the database
        List<PgmsPenGrRate> pgmsPenGrRates = pgmsPenGrRateRepository.findAll();
        assertThat(pgmsPenGrRates).hasSize(databaseSizeBeforeUpdate);
        PgmsPenGrRate testPgmsPenGrRate = pgmsPenGrRates.get(pgmsPenGrRates.size() - 1);
        assertThat(testPgmsPenGrRate.getPenGrSetId()).isEqualTo(UPDATED_PEN_GR_SET_ID);
        assertThat(testPgmsPenGrRate.getWorkingYear()).isEqualTo(UPDATED_WORKING_YEAR);
        assertThat(testPgmsPenGrRate.getRateOfPenGr()).isEqualTo(UPDATED_RATE_OF_PEN_GR);
        assertThat(testPgmsPenGrRate.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void deletePgmsPenGrRate() throws Exception {
        // Initialize the database
        pgmsPenGrRateRepository.saveAndFlush(pgmsPenGrRate);

		int databaseSizeBeforeDelete = pgmsPenGrRateRepository.findAll().size();

        // Get the pgmsPenGrRate
        restPgmsPenGrRateMockMvc.perform(delete("/api/pgmsPenGrRates/{id}", pgmsPenGrRate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsPenGrRate> pgmsPenGrRates = pgmsPenGrRateRepository.findAll();
        assertThat(pgmsPenGrRates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
