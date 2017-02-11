package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EmployeeLoanRulesSetup;
import gov.step.app.repository.EmployeeLoanRulesSetupRepository;
import gov.step.app.repository.search.EmployeeLoanRulesSetupSearchRepository;

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
 * Test class for the EmployeeLoanRulesSetupResource REST controller.
 *
 * @see EmployeeLoanRulesSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeLoanRulesSetupResourceTest {

    private static final String DEFAULT_LOAN_NAME = "AAAAA";
    private static final String UPDATED_LOAN_NAME = "BBBBB";
    private static final String DEFAULT_LOAN_RULES_DESCRIPTION = "AAAAA";
    private static final String UPDATED_LOAN_RULES_DESCRIPTION = "BBBBB";

    private static final Long DEFAULT_MAXIMUM_WITHDRAWAL = 1L;
    private static final Long UPDATED_MAXIMUM_WITHDRAWAL = 2L;

    private static final Long DEFAULT_MINIMUM_AMOUNT_BASIC = 1L;
    private static final Long UPDATED_MINIMUM_AMOUNT_BASIC = 2L;

    private static final Long DEFAULT_INTEREST = 1L;
    private static final Long UPDATED_INTEREST = 2L;

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
    private EmployeeLoanRulesSetupRepository employeeLoanRulesSetupRepository;

    @Inject
    private EmployeeLoanRulesSetupSearchRepository employeeLoanRulesSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeLoanRulesSetupMockMvc;

    private EmployeeLoanRulesSetup employeeLoanRulesSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeLoanRulesSetupResource employeeLoanRulesSetupResource = new EmployeeLoanRulesSetupResource();
        ReflectionTestUtils.setField(employeeLoanRulesSetupResource, "employeeLoanRulesSetupRepository", employeeLoanRulesSetupRepository);
        ReflectionTestUtils.setField(employeeLoanRulesSetupResource, "employeeLoanRulesSetupSearchRepository", employeeLoanRulesSetupSearchRepository);
        this.restEmployeeLoanRulesSetupMockMvc = MockMvcBuilders.standaloneSetup(employeeLoanRulesSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employeeLoanRulesSetup = new EmployeeLoanRulesSetup();
        employeeLoanRulesSetup.setLoanName(DEFAULT_LOAN_NAME);
        employeeLoanRulesSetup.setLoanRulesDescription(DEFAULT_LOAN_RULES_DESCRIPTION);
        employeeLoanRulesSetup.setMaximumWithdrawal(DEFAULT_MAXIMUM_WITHDRAWAL);
        employeeLoanRulesSetup.setMinimumAmountBasic(DEFAULT_MINIMUM_AMOUNT_BASIC);
        employeeLoanRulesSetup.setInterest(DEFAULT_INTEREST);
        employeeLoanRulesSetup.setStatus(DEFAULT_STATUS);
        employeeLoanRulesSetup.setCreateDate(DEFAULT_CREATE_DATE);
        employeeLoanRulesSetup.setCreateBy(DEFAULT_CREATE_BY);
        employeeLoanRulesSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        employeeLoanRulesSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createEmployeeLoanRulesSetup() throws Exception {
        int databaseSizeBeforeCreate = employeeLoanRulesSetupRepository.findAll().size();

        // Create the EmployeeLoanRulesSetup

        restEmployeeLoanRulesSetupMockMvc.perform(post("/api/employeeLoanRulesSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRulesSetup)))
                .andExpect(status().isCreated());

        // Validate the EmployeeLoanRulesSetup in the database
        List<EmployeeLoanRulesSetup> employeeLoanRulesSetups = employeeLoanRulesSetupRepository.findAll();
        assertThat(employeeLoanRulesSetups).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLoanRulesSetup testEmployeeLoanRulesSetup = employeeLoanRulesSetups.get(employeeLoanRulesSetups.size() - 1);
        assertThat(testEmployeeLoanRulesSetup.getLoanName()).isEqualTo(DEFAULT_LOAN_NAME);
        assertThat(testEmployeeLoanRulesSetup.getLoanRulesDescription()).isEqualTo(DEFAULT_LOAN_RULES_DESCRIPTION);
        assertThat(testEmployeeLoanRulesSetup.getMaximumWithdrawal()).isEqualTo(DEFAULT_MAXIMUM_WITHDRAWAL);
        assertThat(testEmployeeLoanRulesSetup.getMinimumAmountBasic()).isEqualTo(DEFAULT_MINIMUM_AMOUNT_BASIC);
        assertThat(testEmployeeLoanRulesSetup.getInterest()).isEqualTo(DEFAULT_INTEREST);
        assertThat(testEmployeeLoanRulesSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeLoanRulesSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testEmployeeLoanRulesSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testEmployeeLoanRulesSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testEmployeeLoanRulesSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkLoanNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanRulesSetupRepository.findAll().size();
        // set the field null
        employeeLoanRulesSetup.setLoanName(null);

        // Create the EmployeeLoanRulesSetup, which fails.

        restEmployeeLoanRulesSetupMockMvc.perform(post("/api/employeeLoanRulesSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRulesSetup)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanRulesSetup> employeeLoanRulesSetups = employeeLoanRulesSetupRepository.findAll();
        assertThat(employeeLoanRulesSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaximumWithdrawalIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanRulesSetupRepository.findAll().size();
        // set the field null
        employeeLoanRulesSetup.setMaximumWithdrawal(null);

        // Create the EmployeeLoanRulesSetup, which fails.

        restEmployeeLoanRulesSetupMockMvc.perform(post("/api/employeeLoanRulesSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRulesSetup)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanRulesSetup> employeeLoanRulesSetups = employeeLoanRulesSetupRepository.findAll();
        assertThat(employeeLoanRulesSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinimumAmountBasicIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanRulesSetupRepository.findAll().size();
        // set the field null
        employeeLoanRulesSetup.setMinimumAmountBasic(null);

        // Create the EmployeeLoanRulesSetup, which fails.

        restEmployeeLoanRulesSetupMockMvc.perform(post("/api/employeeLoanRulesSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRulesSetup)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanRulesSetup> employeeLoanRulesSetups = employeeLoanRulesSetupRepository.findAll();
        assertThat(employeeLoanRulesSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInterestIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanRulesSetupRepository.findAll().size();
        // set the field null
        employeeLoanRulesSetup.setInterest(null);

        // Create the EmployeeLoanRulesSetup, which fails.

        restEmployeeLoanRulesSetupMockMvc.perform(post("/api/employeeLoanRulesSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRulesSetup)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanRulesSetup> employeeLoanRulesSetups = employeeLoanRulesSetupRepository.findAll();
        assertThat(employeeLoanRulesSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeLoanRulesSetups() throws Exception {
        // Initialize the database
        employeeLoanRulesSetupRepository.saveAndFlush(employeeLoanRulesSetup);

        // Get all the employeeLoanRulesSetups
        restEmployeeLoanRulesSetupMockMvc.perform(get("/api/employeeLoanRulesSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLoanRulesSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].loanName").value(hasItem(DEFAULT_LOAN_NAME.toString())))
                .andExpect(jsonPath("$.[*].loanRulesDescription").value(hasItem(DEFAULT_LOAN_RULES_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].maximumWithdrawal").value(hasItem(DEFAULT_MAXIMUM_WITHDRAWAL.intValue())))
                .andExpect(jsonPath("$.[*].minimumAmountBasic").value(hasItem(DEFAULT_MINIMUM_AMOUNT_BASIC.intValue())))
                .andExpect(jsonPath("$.[*].interest").value(hasItem(DEFAULT_INTEREST.intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getEmployeeLoanRulesSetup() throws Exception {
        // Initialize the database
        employeeLoanRulesSetupRepository.saveAndFlush(employeeLoanRulesSetup);

        // Get the employeeLoanRulesSetup
        restEmployeeLoanRulesSetupMockMvc.perform(get("/api/employeeLoanRulesSetups/{id}", employeeLoanRulesSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employeeLoanRulesSetup.getId().intValue()))
            .andExpect(jsonPath("$.loanName").value(DEFAULT_LOAN_NAME.toString()))
            .andExpect(jsonPath("$.loanRulesDescription").value(DEFAULT_LOAN_RULES_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.maximumWithdrawal").value(DEFAULT_MAXIMUM_WITHDRAWAL.intValue()))
            .andExpect(jsonPath("$.minimumAmountBasic").value(DEFAULT_MINIMUM_AMOUNT_BASIC.intValue()))
            .andExpect(jsonPath("$.interest").value(DEFAULT_INTEREST.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeLoanRulesSetup() throws Exception {
        // Get the employeeLoanRulesSetup
        restEmployeeLoanRulesSetupMockMvc.perform(get("/api/employeeLoanRulesSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLoanRulesSetup() throws Exception {
        // Initialize the database
        employeeLoanRulesSetupRepository.saveAndFlush(employeeLoanRulesSetup);

		int databaseSizeBeforeUpdate = employeeLoanRulesSetupRepository.findAll().size();

        // Update the employeeLoanRulesSetup
        employeeLoanRulesSetup.setLoanName(UPDATED_LOAN_NAME);
        employeeLoanRulesSetup.setLoanRulesDescription(UPDATED_LOAN_RULES_DESCRIPTION);
        employeeLoanRulesSetup.setMaximumWithdrawal(UPDATED_MAXIMUM_WITHDRAWAL);
        employeeLoanRulesSetup.setMinimumAmountBasic(UPDATED_MINIMUM_AMOUNT_BASIC);
        employeeLoanRulesSetup.setInterest(UPDATED_INTEREST);
        employeeLoanRulesSetup.setStatus(UPDATED_STATUS);
        employeeLoanRulesSetup.setCreateDate(UPDATED_CREATE_DATE);
        employeeLoanRulesSetup.setCreateBy(UPDATED_CREATE_BY);
        employeeLoanRulesSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        employeeLoanRulesSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restEmployeeLoanRulesSetupMockMvc.perform(put("/api/employeeLoanRulesSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRulesSetup)))
                .andExpect(status().isOk());

        // Validate the EmployeeLoanRulesSetup in the database
        List<EmployeeLoanRulesSetup> employeeLoanRulesSetups = employeeLoanRulesSetupRepository.findAll();
        assertThat(employeeLoanRulesSetups).hasSize(databaseSizeBeforeUpdate);
        EmployeeLoanRulesSetup testEmployeeLoanRulesSetup = employeeLoanRulesSetups.get(employeeLoanRulesSetups.size() - 1);
        assertThat(testEmployeeLoanRulesSetup.getLoanName()).isEqualTo(UPDATED_LOAN_NAME);
        assertThat(testEmployeeLoanRulesSetup.getLoanRulesDescription()).isEqualTo(UPDATED_LOAN_RULES_DESCRIPTION);
        assertThat(testEmployeeLoanRulesSetup.getMaximumWithdrawal()).isEqualTo(UPDATED_MAXIMUM_WITHDRAWAL);
        assertThat(testEmployeeLoanRulesSetup.getMinimumAmountBasic()).isEqualTo(UPDATED_MINIMUM_AMOUNT_BASIC);
        assertThat(testEmployeeLoanRulesSetup.getInterest()).isEqualTo(UPDATED_INTEREST);
        assertThat(testEmployeeLoanRulesSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeLoanRulesSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testEmployeeLoanRulesSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testEmployeeLoanRulesSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testEmployeeLoanRulesSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteEmployeeLoanRulesSetup() throws Exception {
        // Initialize the database
        employeeLoanRulesSetupRepository.saveAndFlush(employeeLoanRulesSetup);

		int databaseSizeBeforeDelete = employeeLoanRulesSetupRepository.findAll().size();

        // Get the employeeLoanRulesSetup
        restEmployeeLoanRulesSetupMockMvc.perform(delete("/api/employeeLoanRulesSetups/{id}", employeeLoanRulesSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeLoanRulesSetup> employeeLoanRulesSetups = employeeLoanRulesSetupRepository.findAll();
        assertThat(employeeLoanRulesSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
