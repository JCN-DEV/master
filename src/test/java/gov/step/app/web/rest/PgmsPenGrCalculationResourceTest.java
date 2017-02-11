package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsPenGrCalculation;
import gov.step.app.repository.PgmsPenGrCalculationRepository;
import gov.step.app.repository.search.PgmsPenGrCalculationSearchRepository;

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

import gov.step.app.domain.enumeration.WithdrawnType;

/**
 * Test class for the PgmsPenGrCalculationResource REST controller.
 *
 * @see PgmsPenGrCalculationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsPenGrCalculationResourceTest {



private static final WithdrawnType DEFAULT_WITHDRAWN_TYPE = WithdrawnType.Contemporary;
    private static final WithdrawnType UPDATED_WITHDRAWN_TYPE = WithdrawnType.Regular;
    private static final String DEFAULT_CATEGORY_TYPE = "AAAAA";
    private static final String UPDATED_CATEGORY_TYPE = "BBBBB";

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
    private PgmsPenGrCalculationRepository pgmsPenGrCalculationRepository;

    @Inject
    private PgmsPenGrCalculationSearchRepository pgmsPenGrCalculationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsPenGrCalculationMockMvc;

    private PgmsPenGrCalculation pgmsPenGrCalculation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsPenGrCalculationResource pgmsPenGrCalculationResource = new PgmsPenGrCalculationResource();
        ReflectionTestUtils.setField(pgmsPenGrCalculationResource, "pgmsPenGrCalculationRepository", pgmsPenGrCalculationRepository);
        ReflectionTestUtils.setField(pgmsPenGrCalculationResource, "pgmsPenGrCalculationSearchRepository", pgmsPenGrCalculationSearchRepository);
        this.restPgmsPenGrCalculationMockMvc = MockMvcBuilders.standaloneSetup(pgmsPenGrCalculationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsPenGrCalculation = new PgmsPenGrCalculation();
        pgmsPenGrCalculation.setWithdrawnType(DEFAULT_WITHDRAWN_TYPE);
        pgmsPenGrCalculation.setCategoryType(DEFAULT_CATEGORY_TYPE);
        pgmsPenGrCalculation.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pgmsPenGrCalculation.setCreateDate(DEFAULT_CREATE_DATE);
        pgmsPenGrCalculation.setCreateBy(DEFAULT_CREATE_BY);
        pgmsPenGrCalculation.setUpdateDate(DEFAULT_UPDATE_DATE);
        pgmsPenGrCalculation.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPgmsPenGrCalculation() throws Exception {
        int databaseSizeBeforeCreate = pgmsPenGrCalculationRepository.findAll().size();

        // Create the PgmsPenGrCalculation

        restPgmsPenGrCalculationMockMvc.perform(post("/api/pgmsPenGrCalculations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrCalculation)))
                .andExpect(status().isCreated());

        // Validate the PgmsPenGrCalculation in the database
        List<PgmsPenGrCalculation> pgmsPenGrCalculations = pgmsPenGrCalculationRepository.findAll();
        assertThat(pgmsPenGrCalculations).hasSize(databaseSizeBeforeCreate + 1);
        PgmsPenGrCalculation testPgmsPenGrCalculation = pgmsPenGrCalculations.get(pgmsPenGrCalculations.size() - 1);
        assertThat(testPgmsPenGrCalculation.getWithdrawnType()).isEqualTo(DEFAULT_WITHDRAWN_TYPE);
        assertThat(testPgmsPenGrCalculation.getCategoryType()).isEqualTo(DEFAULT_CATEGORY_TYPE);
        assertThat(testPgmsPenGrCalculation.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPgmsPenGrCalculation.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPgmsPenGrCalculation.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPgmsPenGrCalculation.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPgmsPenGrCalculation.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkCategoryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsPenGrCalculationRepository.findAll().size();
        // set the field null
        pgmsPenGrCalculation.setCategoryType(null);

        // Create the PgmsPenGrCalculation, which fails.

        restPgmsPenGrCalculationMockMvc.perform(post("/api/pgmsPenGrCalculations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrCalculation)))
                .andExpect(status().isBadRequest());

        List<PgmsPenGrCalculation> pgmsPenGrCalculations = pgmsPenGrCalculationRepository.findAll();
        assertThat(pgmsPenGrCalculations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsPenGrCalculations() throws Exception {
        // Initialize the database
        pgmsPenGrCalculationRepository.saveAndFlush(pgmsPenGrCalculation);

        // Get all the pgmsPenGrCalculations
        restPgmsPenGrCalculationMockMvc.perform(get("/api/pgmsPenGrCalculations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsPenGrCalculation.getId().intValue())))
                .andExpect(jsonPath("$.[*].withdrawnType").value(hasItem(DEFAULT_WITHDRAWN_TYPE.toString())))
                .andExpect(jsonPath("$.[*].categoryType").value(hasItem(DEFAULT_CATEGORY_TYPE.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPgmsPenGrCalculation() throws Exception {
        // Initialize the database
        pgmsPenGrCalculationRepository.saveAndFlush(pgmsPenGrCalculation);

        // Get the pgmsPenGrCalculation
        restPgmsPenGrCalculationMockMvc.perform(get("/api/pgmsPenGrCalculations/{id}", pgmsPenGrCalculation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsPenGrCalculation.getId().intValue()))
            .andExpect(jsonPath("$.withdrawnType").value(DEFAULT_WITHDRAWN_TYPE.toString()))
            .andExpect(jsonPath("$.categoryType").value(DEFAULT_CATEGORY_TYPE.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsPenGrCalculation() throws Exception {
        // Get the pgmsPenGrCalculation
        restPgmsPenGrCalculationMockMvc.perform(get("/api/pgmsPenGrCalculations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsPenGrCalculation() throws Exception {
        // Initialize the database
        pgmsPenGrCalculationRepository.saveAndFlush(pgmsPenGrCalculation);

		int databaseSizeBeforeUpdate = pgmsPenGrCalculationRepository.findAll().size();

        // Update the pgmsPenGrCalculation
        pgmsPenGrCalculation.setWithdrawnType(UPDATED_WITHDRAWN_TYPE);
        pgmsPenGrCalculation.setCategoryType(UPDATED_CATEGORY_TYPE);
        pgmsPenGrCalculation.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pgmsPenGrCalculation.setCreateDate(UPDATED_CREATE_DATE);
        pgmsPenGrCalculation.setCreateBy(UPDATED_CREATE_BY);
        pgmsPenGrCalculation.setUpdateDate(UPDATED_UPDATE_DATE);
        pgmsPenGrCalculation.setUpdateBy(UPDATED_UPDATE_BY);

        restPgmsPenGrCalculationMockMvc.perform(put("/api/pgmsPenGrCalculations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenGrCalculation)))
                .andExpect(status().isOk());

        // Validate the PgmsPenGrCalculation in the database
        List<PgmsPenGrCalculation> pgmsPenGrCalculations = pgmsPenGrCalculationRepository.findAll();
        assertThat(pgmsPenGrCalculations).hasSize(databaseSizeBeforeUpdate);
        PgmsPenGrCalculation testPgmsPenGrCalculation = pgmsPenGrCalculations.get(pgmsPenGrCalculations.size() - 1);
        assertThat(testPgmsPenGrCalculation.getWithdrawnType()).isEqualTo(UPDATED_WITHDRAWN_TYPE);
        assertThat(testPgmsPenGrCalculation.getCategoryType()).isEqualTo(UPDATED_CATEGORY_TYPE);
        assertThat(testPgmsPenGrCalculation.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPgmsPenGrCalculation.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPgmsPenGrCalculation.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPgmsPenGrCalculation.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPgmsPenGrCalculation.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePgmsPenGrCalculation() throws Exception {
        // Initialize the database
        pgmsPenGrCalculationRepository.saveAndFlush(pgmsPenGrCalculation);

		int databaseSizeBeforeDelete = pgmsPenGrCalculationRepository.findAll().size();

        // Get the pgmsPenGrCalculation
        restPgmsPenGrCalculationMockMvc.perform(delete("/api/pgmsPenGrCalculations/{id}", pgmsPenGrCalculation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsPenGrCalculation> pgmsPenGrCalculations = pgmsPenGrCalculationRepository.findAll();
        assertThat(pgmsPenGrCalculations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
