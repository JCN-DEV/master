package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsPenGrSetup;
import gov.step.app.repository.PgmsPenGrSetupRepository;
import gov.step.app.repository.search.PgmsPenGrSetupSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PgmsPenGrSetupResource REST controller.
 *
 * @see PgmsPenGrSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsPenGrSetupResourceTest {

    private static final String DEFAULT_SETUP_TYPE = "AAAAA";
    private static final String UPDATED_SETUP_TYPE = "BBBBB";

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_SETUP_VERSION = "AAAAA";
    private static final String UPDATED_SETUP_VERSION = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private PgmsPenGrSetupRepository pgmsPenGrSetupRepository;

    @Inject
    private PgmsPenGrSetupSearchRepository pgmsPenGrSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsPenGrSetupMockMvc;

    private PgmsPenGrSetup pgmsPenGrSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsPenGrSetupResource pgmsPenGrSetupResource = new PgmsPenGrSetupResource();
        ReflectionTestUtils.setField(pgmsPenGrSetupResource, "pgmsPenGrSetupRepository", pgmsPenGrSetupRepository);
        ReflectionTestUtils.setField(pgmsPenGrSetupResource, "pgmsPenGrSetupSearchRepository", pgmsPenGrSetupSearchRepository);
        this.restPgmsPenGrSetupMockMvc = MockMvcBuilders.standaloneSetup(pgmsPenGrSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsPenGrSetup = new PgmsPenGrSetup();
        pgmsPenGrSetup.setSetupType(DEFAULT_SETUP_TYPE);
        pgmsPenGrSetup.setEffectiveDate(DEFAULT_EFFECTIVE_DATE);
        pgmsPenGrSetup.setSetupVersion(DEFAULT_SETUP_VERSION);
        pgmsPenGrSetup.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pgmsPenGrSetup.setCreateDate(DEFAULT_CREATE_DATE);
        pgmsPenGrSetup.setCreateBy(DEFAULT_CREATE_BY);
        pgmsPenGrSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        pgmsPenGrSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPgmsPenGrSetup() throws Exception {
        int databaseSizeBeforeCreate = pgmsPenGrSetupRepository.findAll().size();

        // Create the PgmsPenGrSetup

        restPgmsPenGrSetupMockMvc.perform(post("/api/pgmsPenGrSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrSetup)))
                .andExpect(status().isCreated());

        // Validate the PgmsPenGrSetup in the database
        List<PgmsPenGrSetup> pgmsPenGrSetups = pgmsPenGrSetupRepository.findAll();
        assertThat(pgmsPenGrSetups).hasSize(databaseSizeBeforeCreate + 1);
        PgmsPenGrSetup testPgmsPenGrSetup = pgmsPenGrSetups.get(pgmsPenGrSetups.size() - 1);
        assertThat(testPgmsPenGrSetup.getSetupType()).isEqualTo(DEFAULT_SETUP_TYPE);
        assertThat(testPgmsPenGrSetup.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testPgmsPenGrSetup.getSetupVersion()).isEqualTo(DEFAULT_SETUP_VERSION);
        assertThat(testPgmsPenGrSetup.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPgmsPenGrSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPgmsPenGrSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPgmsPenGrSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPgmsPenGrSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkSetupTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsPenGrSetupRepository.findAll().size();
        // set the field null
        pgmsPenGrSetup.setSetupType(null);

        // Create the PgmsPenGrSetup, which fails.

        restPgmsPenGrSetupMockMvc.perform(post("/api/pgmsPenGrSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrSetup)))
                .andExpect(status().isBadRequest());

        List<PgmsPenGrSetup> pgmsPenGrSetups = pgmsPenGrSetupRepository.findAll();
        assertThat(pgmsPenGrSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEffectiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsPenGrSetupRepository.findAll().size();
        // set the field null
        pgmsPenGrSetup.setEffectiveDate(null);

        // Create the PgmsPenGrSetup, which fails.

        restPgmsPenGrSetupMockMvc.perform(post("/api/pgmsPenGrSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrSetup)))
                .andExpect(status().isBadRequest());

        List<PgmsPenGrSetup> pgmsPenGrSetups = pgmsPenGrSetupRepository.findAll();
        assertThat(pgmsPenGrSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSetupVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsPenGrSetupRepository.findAll().size();
        // set the field null
        pgmsPenGrSetup.setSetupVersion(null);

        // Create the PgmsPenGrSetup, which fails.

        restPgmsPenGrSetupMockMvc.perform(post("/api/pgmsPenGrSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrSetup)))
                .andExpect(status().isBadRequest());

        List<PgmsPenGrSetup> pgmsPenGrSetups = pgmsPenGrSetupRepository.findAll();
        assertThat(pgmsPenGrSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsPenGrSetups() throws Exception {
        // Initialize the database
        pgmsPenGrSetupRepository.saveAndFlush(pgmsPenGrSetup);

        // Get all the pgmsPenGrSetups
        restPgmsPenGrSetupMockMvc.perform(get("/api/pgmsPenGrSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsPenGrSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].setupType").value(hasItem(DEFAULT_SETUP_TYPE.toString())))
                .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].setupVersion").value(hasItem(DEFAULT_SETUP_VERSION.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPgmsPenGrSetup() throws Exception {
        // Initialize the database
        pgmsPenGrSetupRepository.saveAndFlush(pgmsPenGrSetup);

        // Get the pgmsPenGrSetup
        restPgmsPenGrSetupMockMvc.perform(get("/api/pgmsPenGrSetups/{id}", pgmsPenGrSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsPenGrSetup.getId().intValue()))
            .andExpect(jsonPath("$.setupType").value(DEFAULT_SETUP_TYPE.toString()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.setupVersion").value(DEFAULT_SETUP_VERSION.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsPenGrSetup() throws Exception {
        // Get the pgmsPenGrSetup
        restPgmsPenGrSetupMockMvc.perform(get("/api/pgmsPenGrSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsPenGrSetup() throws Exception {
        // Initialize the database
        pgmsPenGrSetupRepository.saveAndFlush(pgmsPenGrSetup);

		int databaseSizeBeforeUpdate = pgmsPenGrSetupRepository.findAll().size();

        // Update the pgmsPenGrSetup
        pgmsPenGrSetup.setSetupType(UPDATED_SETUP_TYPE);
        pgmsPenGrSetup.setEffectiveDate(UPDATED_EFFECTIVE_DATE);
        pgmsPenGrSetup.setSetupVersion(UPDATED_SETUP_VERSION);
        pgmsPenGrSetup.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pgmsPenGrSetup.setCreateDate(UPDATED_CREATE_DATE);
        pgmsPenGrSetup.setCreateBy(UPDATED_CREATE_BY);
        pgmsPenGrSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        pgmsPenGrSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restPgmsPenGrSetupMockMvc.perform(put("/api/pgmsPenGrSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrSetup)))
                .andExpect(status().isOk());

        // Validate the PgmsPenGrSetup in the database
        List<PgmsPenGrSetup> pgmsPenGrSetups = pgmsPenGrSetupRepository.findAll();
        assertThat(pgmsPenGrSetups).hasSize(databaseSizeBeforeUpdate);
        PgmsPenGrSetup testPgmsPenGrSetup = pgmsPenGrSetups.get(pgmsPenGrSetups.size() - 1);
        assertThat(testPgmsPenGrSetup.getSetupType()).isEqualTo(UPDATED_SETUP_TYPE);
        assertThat(testPgmsPenGrSetup.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testPgmsPenGrSetup.getSetupVersion()).isEqualTo(UPDATED_SETUP_VERSION);
        assertThat(testPgmsPenGrSetup.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPgmsPenGrSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPgmsPenGrSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPgmsPenGrSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPgmsPenGrSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePgmsPenGrSetup() throws Exception {
        // Initialize the database
        pgmsPenGrSetupRepository.saveAndFlush(pgmsPenGrSetup);

		int databaseSizeBeforeDelete = pgmsPenGrSetupRepository.findAll().size();

        // Get the pgmsPenGrSetup
        restPgmsPenGrSetupMockMvc.perform(delete("/api/pgmsPenGrSetups/{id}", pgmsPenGrSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsPenGrSetup> pgmsPenGrSetups = pgmsPenGrSetupRepository.findAll();
        assertThat(pgmsPenGrSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
