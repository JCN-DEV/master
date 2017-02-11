package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PfmsEmpMonthlyAdj;
import gov.step.app.repository.PfmsEmpMonthlyAdjRepository;
import gov.step.app.repository.search.PfmsEmpMonthlyAdjSearchRepository;

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
 * Test class for the PfmsEmpMonthlyAdjResource REST controller.
 *
 * @see PfmsEmpMonthlyAdjResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PfmsEmpMonthlyAdjResourceTest {


    /*private static final String DEFAULT_ADJ_MONTH = "AAAAA";

    private static final Long DEFAULT_ADJ_YEAR = 1L;
    private static final Long UPDATED_ADJ_YEAR = 2L;
    private static final String UPDATED_ADJ_MONTH = "BBBBB";

    private static final Double DEFAULT_DEDUCTED_AMOUNT = 1D;
    private static final Double UPDATED_DEDUCTED_AMOUNT = 2D;

    private static final Double DEFAULT_MODIFIED_AMOUNT = 1D;
    private static final Double UPDATED_MODIFIED_AMOUNT = 2D;
    private static final String DEFAULT_REASON = "AAAAA";
    private static final String UPDATED_REASON = "BBBBB";

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
    private PfmsEmpMonthlyAdjRepository pfmsEmpMonthlyAdjRepository;

    @Inject
    private PfmsEmpMonthlyAdjSearchRepository pfmsEmpMonthlyAdjSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPfmsEmpMonthlyAdjMockMvc;

    private PfmsEmpMonthlyAdj pfmsEmpMonthlyAdj;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PfmsEmpMonthlyAdjResource pfmsEmpMonthlyAdjResource = new PfmsEmpMonthlyAdjResource();
        ReflectionTestUtils.setField(pfmsEmpMonthlyAdjResource, "pfmsEmpMonthlyAdjRepository", pfmsEmpMonthlyAdjRepository);
        ReflectionTestUtils.setField(pfmsEmpMonthlyAdjResource, "pfmsEmpMonthlyAdjSearchRepository", pfmsEmpMonthlyAdjSearchRepository);
        this.restPfmsEmpMonthlyAdjMockMvc = MockMvcBuilders.standaloneSetup(pfmsEmpMonthlyAdjResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pfmsEmpMonthlyAdj = new PfmsEmpMonthlyAdj();
        pfmsEmpMonthlyAdj.setAdjYear(DEFAULT_ADJ_YEAR);
        pfmsEmpMonthlyAdj.setAdjMonth(DEFAULT_ADJ_MONTH);
        pfmsEmpMonthlyAdj.setDeductedAmount(DEFAULT_DEDUCTED_AMOUNT);
        pfmsEmpMonthlyAdj.setModifiedAmount(DEFAULT_MODIFIED_AMOUNT);
        pfmsEmpMonthlyAdj.setReason(DEFAULT_REASON);
        pfmsEmpMonthlyAdj.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pfmsEmpMonthlyAdj.setCreateDate(DEFAULT_CREATE_DATE);
        pfmsEmpMonthlyAdj.setCreateBy(DEFAULT_CREATE_BY);
        pfmsEmpMonthlyAdj.setUpdateDate(DEFAULT_UPDATE_DATE);
        pfmsEmpMonthlyAdj.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPfmsEmpMonthlyAdj() throws Exception {
        int databaseSizeBeforeCreate = pfmsEmpMonthlyAdjRepository.findAll().size();

        // Create the PfmsEmpMonthlyAdj

        restPfmsEmpMonthlyAdjMockMvc.perform(post("/api/pfmsEmpMonthlyAdjs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMonthlyAdj)))
                .andExpect(status().isCreated());

        // Validate the PfmsEmpMonthlyAdj in the database
        List<PfmsEmpMonthlyAdj> pfmsEmpMonthlyAdjs = pfmsEmpMonthlyAdjRepository.findAll();
        assertThat(pfmsEmpMonthlyAdjs).hasSize(databaseSizeBeforeCreate + 1);
        PfmsEmpMonthlyAdj testPfmsEmpMonthlyAdj = pfmsEmpMonthlyAdjs.get(pfmsEmpMonthlyAdjs.size() - 1);
        assertThat(testPfmsEmpMonthlyAdj.getAdjYear()).isEqualTo(DEFAULT_ADJ_YEAR);
        assertThat(testPfmsEmpMonthlyAdj.getAdjMonth()).isEqualTo(DEFAULT_ADJ_MONTH);
        assertThat(testPfmsEmpMonthlyAdj.getDeductedAmount()).isEqualTo(DEFAULT_DEDUCTED_AMOUNT);
        assertThat(testPfmsEmpMonthlyAdj.getModifiedAmount()).isEqualTo(DEFAULT_MODIFIED_AMOUNT);
        assertThat(testPfmsEmpMonthlyAdj.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testPfmsEmpMonthlyAdj.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPfmsEmpMonthlyAdj.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPfmsEmpMonthlyAdj.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPfmsEmpMonthlyAdj.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPfmsEmpMonthlyAdj.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkAdjYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMonthlyAdjRepository.findAll().size();
        // set the field null
        pfmsEmpMonthlyAdj.setAdjYear(null);

        // Create the PfmsEmpMonthlyAdj, which fails.

        restPfmsEmpMonthlyAdjMockMvc.perform(post("/api/pfmsEmpMonthlyAdjs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMonthlyAdj)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMonthlyAdj> pfmsEmpMonthlyAdjs = pfmsEmpMonthlyAdjRepository.findAll();
        assertThat(pfmsEmpMonthlyAdjs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdjMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMonthlyAdjRepository.findAll().size();
        // set the field null
        pfmsEmpMonthlyAdj.setAdjMonth(null);

        // Create the PfmsEmpMonthlyAdj, which fails.

        restPfmsEmpMonthlyAdjMockMvc.perform(post("/api/pfmsEmpMonthlyAdjs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMonthlyAdj)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMonthlyAdj> pfmsEmpMonthlyAdjs = pfmsEmpMonthlyAdjRepository.findAll();
        assertThat(pfmsEmpMonthlyAdjs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeductedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMonthlyAdjRepository.findAll().size();
        // set the field null
        pfmsEmpMonthlyAdj.setDeductedAmount(null);

        // Create the PfmsEmpMonthlyAdj, which fails.

        restPfmsEmpMonthlyAdjMockMvc.perform(post("/api/pfmsEmpMonthlyAdjs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMonthlyAdj)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMonthlyAdj> pfmsEmpMonthlyAdjs = pfmsEmpMonthlyAdjRepository.findAll();
        assertThat(pfmsEmpMonthlyAdjs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModifiedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMonthlyAdjRepository.findAll().size();
        // set the field null
        pfmsEmpMonthlyAdj.setModifiedAmount(null);

        // Create the PfmsEmpMonthlyAdj, which fails.

        restPfmsEmpMonthlyAdjMockMvc.perform(post("/api/pfmsEmpMonthlyAdjs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMonthlyAdj)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMonthlyAdj> pfmsEmpMonthlyAdjs = pfmsEmpMonthlyAdjRepository.findAll();
        assertThat(pfmsEmpMonthlyAdjs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMonthlyAdjRepository.findAll().size();
        // set the field null
        pfmsEmpMonthlyAdj.setReason(null);

        // Create the PfmsEmpMonthlyAdj, which fails.

        restPfmsEmpMonthlyAdjMockMvc.perform(post("/api/pfmsEmpMonthlyAdjs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMonthlyAdj)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMonthlyAdj> pfmsEmpMonthlyAdjs = pfmsEmpMonthlyAdjRepository.findAll();
        assertThat(pfmsEmpMonthlyAdjs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMonthlyAdjRepository.findAll().size();
        // set the field null
        pfmsEmpMonthlyAdj.setActiveStatus(null);

        // Create the PfmsEmpMonthlyAdj, which fails.

        restPfmsEmpMonthlyAdjMockMvc.perform(post("/api/pfmsEmpMonthlyAdjs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMonthlyAdj)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMonthlyAdj> pfmsEmpMonthlyAdjs = pfmsEmpMonthlyAdjRepository.findAll();
        assertThat(pfmsEmpMonthlyAdjs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPfmsEmpMonthlyAdjs() throws Exception {
        // Initialize the database
        pfmsEmpMonthlyAdjRepository.saveAndFlush(pfmsEmpMonthlyAdj);

        // Get all the pfmsEmpMonthlyAdjs
        restPfmsEmpMonthlyAdjMockMvc.perform(get("/api/pfmsEmpMonthlyAdjs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pfmsEmpMonthlyAdj.getId().intValue())))
                .andExpect(jsonPath("$.[*].adjYear").value(hasItem(DEFAULT_ADJ_YEAR.intValue())))
                .andExpect(jsonPath("$.[*].adjMonth").value(hasItem(DEFAULT_ADJ_MONTH.toString())))
                .andExpect(jsonPath("$.[*].deductedAmount").value(hasItem(DEFAULT_DEDUCTED_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].modifiedAmount").value(hasItem(DEFAULT_MODIFIED_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPfmsEmpMonthlyAdj() throws Exception {
        // Initialize the database
        pfmsEmpMonthlyAdjRepository.saveAndFlush(pfmsEmpMonthlyAdj);

        // Get the pfmsEmpMonthlyAdj
        restPfmsEmpMonthlyAdjMockMvc.perform(get("/api/pfmsEmpMonthlyAdjs/{id}", pfmsEmpMonthlyAdj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pfmsEmpMonthlyAdj.getId().intValue()))
            .andExpect(jsonPath("$.adjYear").value(DEFAULT_ADJ_YEAR.intValue()))
            .andExpect(jsonPath("$.adjMonth").value(DEFAULT_ADJ_MONTH))
            .andExpect(jsonPath("$.deductedAmount").value(DEFAULT_DEDUCTED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.modifiedAmount").value(DEFAULT_MODIFIED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPfmsEmpMonthlyAdj() throws Exception {
        // Get the pfmsEmpMonthlyAdj
        restPfmsEmpMonthlyAdjMockMvc.perform(get("/api/pfmsEmpMonthlyAdjs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePfmsEmpMonthlyAdj() throws Exception {
        // Initialize the database
        pfmsEmpMonthlyAdjRepository.saveAndFlush(pfmsEmpMonthlyAdj);

		int databaseSizeBeforeUpdate = pfmsEmpMonthlyAdjRepository.findAll().size();

        // Update the pfmsEmpMonthlyAdj
        pfmsEmpMonthlyAdj.setAdjYear(UPDATED_ADJ_YEAR);
        pfmsEmpMonthlyAdj.setAdjMonth(UPDATED_ADJ_MONTH);
        pfmsEmpMonthlyAdj.setDeductedAmount(UPDATED_DEDUCTED_AMOUNT);
        pfmsEmpMonthlyAdj.setModifiedAmount(UPDATED_MODIFIED_AMOUNT);
        pfmsEmpMonthlyAdj.setReason(UPDATED_REASON);
        pfmsEmpMonthlyAdj.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pfmsEmpMonthlyAdj.setCreateDate(UPDATED_CREATE_DATE);
        pfmsEmpMonthlyAdj.setCreateBy(UPDATED_CREATE_BY);
        pfmsEmpMonthlyAdj.setUpdateDate(UPDATED_UPDATE_DATE);
        pfmsEmpMonthlyAdj.setUpdateBy(UPDATED_UPDATE_BY);

        restPfmsEmpMonthlyAdjMockMvc.perform(put("/api/pfmsEmpMonthlyAdjs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMonthlyAdj)))
                .andExpect(status().isOk());

        // Validate the PfmsEmpMonthlyAdj in the database
        List<PfmsEmpMonthlyAdj> pfmsEmpMonthlyAdjs = pfmsEmpMonthlyAdjRepository.findAll();
        assertThat(pfmsEmpMonthlyAdjs).hasSize(databaseSizeBeforeUpdate);
        PfmsEmpMonthlyAdj testPfmsEmpMonthlyAdj = pfmsEmpMonthlyAdjs.get(pfmsEmpMonthlyAdjs.size() - 1);
        assertThat(testPfmsEmpMonthlyAdj.getAdjYear()).isEqualTo(UPDATED_ADJ_YEAR);
        assertThat(testPfmsEmpMonthlyAdj.getAdjMonth()).isEqualTo(UPDATED_ADJ_MONTH);
        assertThat(testPfmsEmpMonthlyAdj.getDeductedAmount()).isEqualTo(UPDATED_DEDUCTED_AMOUNT);
        assertThat(testPfmsEmpMonthlyAdj.getModifiedAmount()).isEqualTo(UPDATED_MODIFIED_AMOUNT);
        assertThat(testPfmsEmpMonthlyAdj.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPfmsEmpMonthlyAdj.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPfmsEmpMonthlyAdj.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPfmsEmpMonthlyAdj.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPfmsEmpMonthlyAdj.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPfmsEmpMonthlyAdj.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePfmsEmpMonthlyAdj() throws Exception {
        // Initialize the database
        pfmsEmpMonthlyAdjRepository.saveAndFlush(pfmsEmpMonthlyAdj);

		int databaseSizeBeforeDelete = pfmsEmpMonthlyAdjRepository.findAll().size();

        // Get the pfmsEmpMonthlyAdj
        restPfmsEmpMonthlyAdjMockMvc.perform(delete("/api/pfmsEmpMonthlyAdjs/{id}", pfmsEmpMonthlyAdj.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PfmsEmpMonthlyAdj> pfmsEmpMonthlyAdjs = pfmsEmpMonthlyAdjRepository.findAll();
        assertThat(pfmsEmpMonthlyAdjs).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}
