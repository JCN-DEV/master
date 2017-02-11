package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PfmsDeduction;
import gov.step.app.repository.PfmsDeductionRepository;
import gov.step.app.repository.search.PfmsDeductionSearchRepository;

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
 * Test class for the PfmsDeductionResource REST controller.
 *
 * @see PfmsDeductionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PfmsDeductionResourceTest {

    private static final String DEFAULT_ACCOUNT_NO = "AAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBB";

    private static final Double DEFAULT_DEDUCTION_AMOUNT = 1D;
    private static final Double UPDATED_DEDUCTION_AMOUNT = 2D;

    private static final LocalDate DEFAULT_DEDUCTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEDUCTION_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private PfmsDeductionRepository pfmsDeductionRepository;

    @Inject
    private PfmsDeductionSearchRepository pfmsDeductionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPfmsDeductionMockMvc;

    private PfmsDeduction pfmsDeduction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PfmsDeductionResource pfmsDeductionResource = new PfmsDeductionResource();
        ReflectionTestUtils.setField(pfmsDeductionResource, "pfmsDeductionRepository", pfmsDeductionRepository);
        ReflectionTestUtils.setField(pfmsDeductionResource, "pfmsDeductionSearchRepository", pfmsDeductionSearchRepository);
        this.restPfmsDeductionMockMvc = MockMvcBuilders.standaloneSetup(pfmsDeductionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pfmsDeduction = new PfmsDeduction();
        pfmsDeduction.setAccountNo(DEFAULT_ACCOUNT_NO);
        pfmsDeduction.setDeductionAmount(DEFAULT_DEDUCTION_AMOUNT);
        pfmsDeduction.setDeductionDate(DEFAULT_DEDUCTION_DATE);
        pfmsDeduction.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pfmsDeduction.setCreateDate(DEFAULT_CREATE_DATE);
        pfmsDeduction.setCreateBy(DEFAULT_CREATE_BY);
        pfmsDeduction.setUpdateDate(DEFAULT_UPDATE_DATE);
        pfmsDeduction.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPfmsDeduction() throws Exception {
        int databaseSizeBeforeCreate = pfmsDeductionRepository.findAll().size();

        // Create the PfmsDeduction

        restPfmsDeductionMockMvc.perform(post("/api/pfmsDeductions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsDeduction)))
                .andExpect(status().isCreated());

        // Validate the PfmsDeduction in the database
        List<PfmsDeduction> pfmsDeductions = pfmsDeductionRepository.findAll();
        assertThat(pfmsDeductions).hasSize(databaseSizeBeforeCreate + 1);
        PfmsDeduction testPfmsDeduction = pfmsDeductions.get(pfmsDeductions.size() - 1);
        assertThat(testPfmsDeduction.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testPfmsDeduction.getDeductionAmount()).isEqualTo(DEFAULT_DEDUCTION_AMOUNT);
        assertThat(testPfmsDeduction.getDeductionDate()).isEqualTo(DEFAULT_DEDUCTION_DATE);
        assertThat(testPfmsDeduction.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPfmsDeduction.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPfmsDeduction.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPfmsDeduction.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPfmsDeduction.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkAccountNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsDeductionRepository.findAll().size();
        // set the field null
        pfmsDeduction.setAccountNo(null);

        // Create the PfmsDeduction, which fails.

        restPfmsDeductionMockMvc.perform(post("/api/pfmsDeductions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsDeduction)))
                .andExpect(status().isBadRequest());

        List<PfmsDeduction> pfmsDeductions = pfmsDeductionRepository.findAll();
        assertThat(pfmsDeductions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeductionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsDeductionRepository.findAll().size();
        // set the field null
        pfmsDeduction.setDeductionAmount(null);

        // Create the PfmsDeduction, which fails.

        restPfmsDeductionMockMvc.perform(post("/api/pfmsDeductions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsDeduction)))
                .andExpect(status().isBadRequest());

        List<PfmsDeduction> pfmsDeductions = pfmsDeductionRepository.findAll();
        assertThat(pfmsDeductions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeductionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsDeductionRepository.findAll().size();
        // set the field null
        pfmsDeduction.setDeductionDate(null);

        // Create the PfmsDeduction, which fails.

        restPfmsDeductionMockMvc.perform(post("/api/pfmsDeductions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsDeduction)))
                .andExpect(status().isBadRequest());

        List<PfmsDeduction> pfmsDeductions = pfmsDeductionRepository.findAll();
        assertThat(pfmsDeductions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsDeductionRepository.findAll().size();
        // set the field null
        pfmsDeduction.setActiveStatus(null);

        // Create the PfmsDeduction, which fails.

        restPfmsDeductionMockMvc.perform(post("/api/pfmsDeductions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsDeduction)))
                .andExpect(status().isBadRequest());

        List<PfmsDeduction> pfmsDeductions = pfmsDeductionRepository.findAll();
        assertThat(pfmsDeductions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPfmsDeductions() throws Exception {
        // Initialize the database
        pfmsDeductionRepository.saveAndFlush(pfmsDeduction);

        // Get all the pfmsDeductions
        restPfmsDeductionMockMvc.perform(get("/api/pfmsDeductions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pfmsDeduction.getId().intValue())))
                .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.toString())))
                .andExpect(jsonPath("$.[*].deductionAmount").value(hasItem(DEFAULT_DEDUCTION_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].deductionDate").value(hasItem(DEFAULT_DEDUCTION_DATE.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPfmsDeduction() throws Exception {
        // Initialize the database
        pfmsDeductionRepository.saveAndFlush(pfmsDeduction);

        // Get the pfmsDeduction
        restPfmsDeductionMockMvc.perform(get("/api/pfmsDeductions/{id}", pfmsDeduction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pfmsDeduction.getId().intValue()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.deductionAmount").value(DEFAULT_DEDUCTION_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.deductionDate").value(DEFAULT_DEDUCTION_DATE.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPfmsDeduction() throws Exception {
        // Get the pfmsDeduction
        restPfmsDeductionMockMvc.perform(get("/api/pfmsDeductions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePfmsDeduction() throws Exception {
        // Initialize the database
        pfmsDeductionRepository.saveAndFlush(pfmsDeduction);

		int databaseSizeBeforeUpdate = pfmsDeductionRepository.findAll().size();

        // Update the pfmsDeduction
        pfmsDeduction.setAccountNo(UPDATED_ACCOUNT_NO);
        pfmsDeduction.setDeductionAmount(UPDATED_DEDUCTION_AMOUNT);
        pfmsDeduction.setDeductionDate(UPDATED_DEDUCTION_DATE);
        pfmsDeduction.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pfmsDeduction.setCreateDate(UPDATED_CREATE_DATE);
        pfmsDeduction.setCreateBy(UPDATED_CREATE_BY);
        pfmsDeduction.setUpdateDate(UPDATED_UPDATE_DATE);
        pfmsDeduction.setUpdateBy(UPDATED_UPDATE_BY);

        restPfmsDeductionMockMvc.perform(put("/api/pfmsDeductions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsDeduction)))
                .andExpect(status().isOk());

        // Validate the PfmsDeduction in the database
        List<PfmsDeduction> pfmsDeductions = pfmsDeductionRepository.findAll();
        assertThat(pfmsDeductions).hasSize(databaseSizeBeforeUpdate);
        PfmsDeduction testPfmsDeduction = pfmsDeductions.get(pfmsDeductions.size() - 1);
        assertThat(testPfmsDeduction.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testPfmsDeduction.getDeductionAmount()).isEqualTo(UPDATED_DEDUCTION_AMOUNT);
        assertThat(testPfmsDeduction.getDeductionDate()).isEqualTo(UPDATED_DEDUCTION_DATE);
        assertThat(testPfmsDeduction.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPfmsDeduction.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPfmsDeduction.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPfmsDeduction.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPfmsDeduction.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePfmsDeduction() throws Exception {
        // Initialize the database
        pfmsDeductionRepository.saveAndFlush(pfmsDeduction);

		int databaseSizeBeforeDelete = pfmsDeductionRepository.findAll().size();

        // Get the pfmsDeduction
        restPfmsDeductionMockMvc.perform(delete("/api/pfmsDeductions/{id}", pfmsDeduction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PfmsDeduction> pfmsDeductions = pfmsDeductionRepository.findAll();
        assertThat(pfmsDeductions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
