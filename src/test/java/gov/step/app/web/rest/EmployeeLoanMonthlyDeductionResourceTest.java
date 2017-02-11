package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EmployeeLoanMonthlyDeduction;
import gov.step.app.repository.EmployeeLoanMonthlyDeductionRepository;
import gov.step.app.repository.search.EmployeeLoanMonthlyDeductionSearchRepository;

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
 * Test class for the EmployeeLoanMonthlyDeductionResource REST controller.
 *
 * @see EmployeeLoanMonthlyDeductionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeLoanMonthlyDeductionResourceTest {


    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Double DEFAULT_DEDUCTED_AMOUNT = 1D;
    private static final Double UPDATED_DEDUCTED_AMOUNT = 2D;
    private static final String DEFAULT_REASON = "AAAAA";
    private static final String UPDATED_REASON = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private EmployeeLoanMonthlyDeductionRepository employeeLoanMonthlyDeductionRepository;

    @Inject
    private EmployeeLoanMonthlyDeductionSearchRepository employeeLoanMonthlyDeductionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeLoanMonthlyDeductionMockMvc;

    private EmployeeLoanMonthlyDeduction employeeLoanMonthlyDeduction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeLoanMonthlyDeductionResource employeeLoanMonthlyDeductionResource = new EmployeeLoanMonthlyDeductionResource();
        ReflectionTestUtils.setField(employeeLoanMonthlyDeductionResource, "employeeLoanMonthlyDeductionRepository", employeeLoanMonthlyDeductionRepository);
        ReflectionTestUtils.setField(employeeLoanMonthlyDeductionResource, "employeeLoanMonthlyDeductionSearchRepository", employeeLoanMonthlyDeductionSearchRepository);
        this.restEmployeeLoanMonthlyDeductionMockMvc = MockMvcBuilders.standaloneSetup(employeeLoanMonthlyDeductionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employeeLoanMonthlyDeduction = new EmployeeLoanMonthlyDeduction();
        employeeLoanMonthlyDeduction.setMonth(DEFAULT_MONTH);
        employeeLoanMonthlyDeduction.setYear(DEFAULT_YEAR);
        employeeLoanMonthlyDeduction.setMonthlyDeductionAmount(DEFAULT_DEDUCTED_AMOUNT);
        employeeLoanMonthlyDeduction.setReason(DEFAULT_REASON);
        employeeLoanMonthlyDeduction.setStatus(DEFAULT_STATUS);
        employeeLoanMonthlyDeduction.setCreateDate(DEFAULT_CREATE_DATE);
        employeeLoanMonthlyDeduction.setCreateBy(DEFAULT_CREATE_BY);
        employeeLoanMonthlyDeduction.setUpdateDate(DEFAULT_UPDATE_DATE);
        employeeLoanMonthlyDeduction.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createEmployeeLoanMonthlyDeduction() throws Exception {
        int databaseSizeBeforeCreate = employeeLoanMonthlyDeductionRepository.findAll().size();

        // Create the EmployeeLoanMonthlyDeduction

        restEmployeeLoanMonthlyDeductionMockMvc.perform(post("/api/employeeLoanMonthlyDeductions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanMonthlyDeduction)))
                .andExpect(status().isCreated());

        // Validate the EmployeeLoanMonthlyDeduction in the database
        List<EmployeeLoanMonthlyDeduction> employeeLoanMonthlyDeductions = employeeLoanMonthlyDeductionRepository.findAll();
        assertThat(employeeLoanMonthlyDeductions).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLoanMonthlyDeduction testEmployeeLoanMonthlyDeduction = employeeLoanMonthlyDeductions.get(employeeLoanMonthlyDeductions.size() - 1);
        assertThat(testEmployeeLoanMonthlyDeduction.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testEmployeeLoanMonthlyDeduction.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testEmployeeLoanMonthlyDeduction.getMonthlyDeductionAmount()).isEqualTo(DEFAULT_DEDUCTED_AMOUNT);
        assertThat(testEmployeeLoanMonthlyDeduction.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testEmployeeLoanMonthlyDeduction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeLoanMonthlyDeduction.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testEmployeeLoanMonthlyDeduction.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testEmployeeLoanMonthlyDeduction.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testEmployeeLoanMonthlyDeduction.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanMonthlyDeductionRepository.findAll().size();
        // set the field null
        employeeLoanMonthlyDeduction.setMonth(null);

        // Create the EmployeeLoanMonthlyDeduction, which fails.

        restEmployeeLoanMonthlyDeductionMockMvc.perform(post("/api/employeeLoanMonthlyDeductions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanMonthlyDeduction)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanMonthlyDeduction> employeeLoanMonthlyDeductions = employeeLoanMonthlyDeductionRepository.findAll();
        assertThat(employeeLoanMonthlyDeductions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanMonthlyDeductionRepository.findAll().size();
        // set the field null
        employeeLoanMonthlyDeduction.setYear(null);

        // Create the EmployeeLoanMonthlyDeduction, which fails.

        restEmployeeLoanMonthlyDeductionMockMvc.perform(post("/api/employeeLoanMonthlyDeductions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanMonthlyDeduction)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanMonthlyDeduction> employeeLoanMonthlyDeductions = employeeLoanMonthlyDeductionRepository.findAll();
        assertThat(employeeLoanMonthlyDeductions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeLoanMonthlyDeductions() throws Exception {
        // Initialize the database
        employeeLoanMonthlyDeductionRepository.saveAndFlush(employeeLoanMonthlyDeduction);

        // Get all the employeeLoanMonthlyDeductions
        restEmployeeLoanMonthlyDeductionMockMvc.perform(get("/api/employeeLoanMonthlyDeductions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLoanMonthlyDeduction.getId().intValue())))
                .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
                .andExpect(jsonPath("$.[*].deductedAmount").value(hasItem(DEFAULT_DEDUCTED_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getEmployeeLoanMonthlyDeduction() throws Exception {
        // Initialize the database
        employeeLoanMonthlyDeductionRepository.saveAndFlush(employeeLoanMonthlyDeduction);

        // Get the employeeLoanMonthlyDeduction
        restEmployeeLoanMonthlyDeductionMockMvc.perform(get("/api/employeeLoanMonthlyDeductions/{id}", employeeLoanMonthlyDeduction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employeeLoanMonthlyDeduction.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.deductedAmount").value(DEFAULT_DEDUCTED_AMOUNT.intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeLoanMonthlyDeduction() throws Exception {
        // Get the employeeLoanMonthlyDeduction
        restEmployeeLoanMonthlyDeductionMockMvc.perform(get("/api/employeeLoanMonthlyDeductions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLoanMonthlyDeduction() throws Exception {
        // Initialize the database
        employeeLoanMonthlyDeductionRepository.saveAndFlush(employeeLoanMonthlyDeduction);

		int databaseSizeBeforeUpdate = employeeLoanMonthlyDeductionRepository.findAll().size();

        // Update the employeeLoanMonthlyDeduction
        employeeLoanMonthlyDeduction.setMonth(UPDATED_MONTH);
        employeeLoanMonthlyDeduction.setYear(UPDATED_YEAR);
        employeeLoanMonthlyDeduction.setMonthlyDeductionAmount(UPDATED_DEDUCTED_AMOUNT);
        employeeLoanMonthlyDeduction.setReason(UPDATED_REASON);
        employeeLoanMonthlyDeduction.setStatus(UPDATED_STATUS);
        employeeLoanMonthlyDeduction.setCreateDate(UPDATED_CREATE_DATE);
        employeeLoanMonthlyDeduction.setCreateBy(UPDATED_CREATE_BY);
        employeeLoanMonthlyDeduction.setUpdateDate(UPDATED_UPDATE_DATE);
        employeeLoanMonthlyDeduction.setUpdateBy(UPDATED_UPDATE_BY);

        restEmployeeLoanMonthlyDeductionMockMvc.perform(put("/api/employeeLoanMonthlyDeductions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanMonthlyDeduction)))
                .andExpect(status().isOk());

        // Validate the EmployeeLoanMonthlyDeduction in the database
        List<EmployeeLoanMonthlyDeduction> employeeLoanMonthlyDeductions = employeeLoanMonthlyDeductionRepository.findAll();
        assertThat(employeeLoanMonthlyDeductions).hasSize(databaseSizeBeforeUpdate);
        EmployeeLoanMonthlyDeduction testEmployeeLoanMonthlyDeduction = employeeLoanMonthlyDeductions.get(employeeLoanMonthlyDeductions.size() - 1);
        assertThat(testEmployeeLoanMonthlyDeduction.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testEmployeeLoanMonthlyDeduction.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testEmployeeLoanMonthlyDeduction.getMonthlyDeductionAmount()).isEqualTo(UPDATED_DEDUCTED_AMOUNT);
        assertThat(testEmployeeLoanMonthlyDeduction.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testEmployeeLoanMonthlyDeduction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeLoanMonthlyDeduction.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testEmployeeLoanMonthlyDeduction.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testEmployeeLoanMonthlyDeduction.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testEmployeeLoanMonthlyDeduction.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteEmployeeLoanMonthlyDeduction() throws Exception {
        // Initialize the database
        employeeLoanMonthlyDeductionRepository.saveAndFlush(employeeLoanMonthlyDeduction);

		int databaseSizeBeforeDelete = employeeLoanMonthlyDeductionRepository.findAll().size();

        // Get the employeeLoanMonthlyDeduction
        restEmployeeLoanMonthlyDeductionMockMvc.perform(delete("/api/employeeLoanMonthlyDeductions/{id}", employeeLoanMonthlyDeduction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeLoanMonthlyDeduction> employeeLoanMonthlyDeductions = employeeLoanMonthlyDeductionRepository.findAll();
        assertThat(employeeLoanMonthlyDeductions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
