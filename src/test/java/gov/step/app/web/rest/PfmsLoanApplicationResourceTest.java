package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PfmsLoanApplication;
import gov.step.app.repository.PfmsLoanApplicationRepository;
import gov.step.app.repository.search.PfmsLoanApplicationSearchRepository;

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
 * Test class for the PfmsLoanApplicationResource REST controller.
 *
 * @see PfmsLoanApplicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PfmsLoanApplicationResourceTest {

    private static final Long DEFAULT_TIMES_OF_WITHDRAW = 1L;
    private static final Long UPDATED_TIMES_OF_WITHDRAW = 1L;

    private static final Double DEFAULT_LOAN_AMOUNT = 1D;
    private static final Double UPDATED_LOAN_AMOUNT = 2D;

    private static final Long DEFAULT_NO_OF_INSTALLMENT = 1L;
    private static final Long UPDATED_NO_OF_INSTALLMENT = 2L;
    private static final String DEFAULT_REASON_OF_WITHDRAWAL = "AAAAA";
    private static final String UPDATED_REASON_OF_WITHDRAWAL = "BBBBB";

    private static final Boolean DEFAULT_IS_REPAY_FIRST_WITHDRAW = false;
    private static final Boolean UPDATED_IS_REPAY_FIRST_WITHDRAW = true;

    private static final Long DEFAULT_NO_OF_INSTALLMENT_LEFT = 1L;
    private static final Long UPDATED_NO_OF_INSTALLMENT_LEFT = 2L;

    private static final LocalDate DEFAULT_LAST_INSTALLMENT_RE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_INSTALLMENT_RE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APPLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private PfmsLoanApplicationRepository pfmsLoanApplicationRepository;

    @Inject
    private PfmsLoanApplicationSearchRepository pfmsLoanApplicationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPfmsLoanApplicationMockMvc;

    private PfmsLoanApplication pfmsLoanApplication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PfmsLoanApplicationResource pfmsLoanApplicationResource = new PfmsLoanApplicationResource();
        ReflectionTestUtils.setField(pfmsLoanApplicationResource, "pfmsLoanApplicationRepository", pfmsLoanApplicationRepository);
        ReflectionTestUtils.setField(pfmsLoanApplicationResource, "pfmsLoanApplicationSearchRepository", pfmsLoanApplicationSearchRepository);
        this.restPfmsLoanApplicationMockMvc = MockMvcBuilders.standaloneSetup(pfmsLoanApplicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pfmsLoanApplication = new PfmsLoanApplication();
        pfmsLoanApplication.setLoanAmount(DEFAULT_LOAN_AMOUNT);
        pfmsLoanApplication.setNoOfInstallment(DEFAULT_NO_OF_INSTALLMENT);
        pfmsLoanApplication.setReasonOfWithdrawal(DEFAULT_REASON_OF_WITHDRAWAL);
        pfmsLoanApplication.setIsRepayFirstWithdraw(DEFAULT_IS_REPAY_FIRST_WITHDRAW);
        pfmsLoanApplication.setApplicationDate(DEFAULT_APPLICATION_DATE);
        pfmsLoanApplication.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pfmsLoanApplication.setCreateDate(DEFAULT_CREATE_DATE);
        pfmsLoanApplication.setCreateBy(DEFAULT_CREATE_BY);
        pfmsLoanApplication.setUpdateDate(DEFAULT_UPDATE_DATE);
        pfmsLoanApplication.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPfmsLoanApplication() throws Exception {
        int databaseSizeBeforeCreate = pfmsLoanApplicationRepository.findAll().size();

        // Create the PfmsLoanApplication

        restPfmsLoanApplicationMockMvc.perform(post("/api/pfmsLoanApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsLoanApplication)))
                .andExpect(status().isCreated());

        // Validate the PfmsLoanApplication in the database
        List<PfmsLoanApplication> pfmsLoanApplications = pfmsLoanApplicationRepository.findAll();
        assertThat(pfmsLoanApplications).hasSize(databaseSizeBeforeCreate + 1);
        PfmsLoanApplication testPfmsLoanApplication = pfmsLoanApplications.get(pfmsLoanApplications.size() - 1);
        assertThat(testPfmsLoanApplication.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        assertThat(testPfmsLoanApplication.getNoOfInstallment()).isEqualTo(DEFAULT_NO_OF_INSTALLMENT);
        assertThat(testPfmsLoanApplication.getReasonOfWithdrawal()).isEqualTo(DEFAULT_REASON_OF_WITHDRAWAL);
        assertThat(testPfmsLoanApplication.getIsRepayFirstWithdraw()).isEqualTo(DEFAULT_IS_REPAY_FIRST_WITHDRAW);
        assertThat(testPfmsLoanApplication.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testPfmsLoanApplication.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPfmsLoanApplication.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPfmsLoanApplication.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPfmsLoanApplication.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPfmsLoanApplication.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }



    @Test
    @Transactional
    public void checkLoanAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsLoanApplicationRepository.findAll().size();
        // set the field null
        pfmsLoanApplication.setLoanAmount(null);

        // Create the PfmsLoanApplication, which fails.

        restPfmsLoanApplicationMockMvc.perform(post("/api/pfmsLoanApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsLoanApplication)))
                .andExpect(status().isBadRequest());

        List<PfmsLoanApplication> pfmsLoanApplications = pfmsLoanApplicationRepository.findAll();
        assertThat(pfmsLoanApplications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoOfInstallmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsLoanApplicationRepository.findAll().size();
        // set the field null
        pfmsLoanApplication.setNoOfInstallment(null);

        // Create the PfmsLoanApplication, which fails.

        restPfmsLoanApplicationMockMvc.perform(post("/api/pfmsLoanApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsLoanApplication)))
                .andExpect(status().isBadRequest());

        List<PfmsLoanApplication> pfmsLoanApplications = pfmsLoanApplicationRepository.findAll();
        assertThat(pfmsLoanApplications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonOfWithdrawalIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsLoanApplicationRepository.findAll().size();
        // set the field null
        pfmsLoanApplication.setReasonOfWithdrawal(null);

        // Create the PfmsLoanApplication, which fails.

        restPfmsLoanApplicationMockMvc.perform(post("/api/pfmsLoanApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsLoanApplication)))
                .andExpect(status().isBadRequest());

        List<PfmsLoanApplication> pfmsLoanApplications = pfmsLoanApplicationRepository.findAll();
        assertThat(pfmsLoanApplications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPfmsLoanApplications() throws Exception {
        // Initialize the database
        pfmsLoanApplicationRepository.saveAndFlush(pfmsLoanApplication);

        // Get all the pfmsLoanApplications
        restPfmsLoanApplicationMockMvc.perform(get("/api/pfmsLoanApplications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pfmsLoanApplication.getId().intValue())))
                .andExpect(jsonPath("$.[*].timesOfWithdraw").value(hasItem(DEFAULT_TIMES_OF_WITHDRAW.toString())))
                .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].noOfInstallment").value(hasItem(DEFAULT_NO_OF_INSTALLMENT.intValue())))
                .andExpect(jsonPath("$.[*].reasonOfWithdrawal").value(hasItem(DEFAULT_REASON_OF_WITHDRAWAL.toString())))
                .andExpect(jsonPath("$.[*].isRepayFirstWithdraw").value(hasItem(DEFAULT_IS_REPAY_FIRST_WITHDRAW.booleanValue())))
                .andExpect(jsonPath("$.[*].noOfInstallmentLeft").value(hasItem(DEFAULT_NO_OF_INSTALLMENT_LEFT.intValue())))
                .andExpect(jsonPath("$.[*].lastInstallmentReDate").value(hasItem(DEFAULT_LAST_INSTALLMENT_RE_DATE.toString())))
                .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPfmsLoanApplication() throws Exception {
        // Initialize the database
        pfmsLoanApplicationRepository.saveAndFlush(pfmsLoanApplication);

        // Get the pfmsLoanApplication
        restPfmsLoanApplicationMockMvc.perform(get("/api/pfmsLoanApplications/{id}", pfmsLoanApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pfmsLoanApplication.getId().intValue()))
            .andExpect(jsonPath("$.timesOfWithdraw").value(DEFAULT_TIMES_OF_WITHDRAW.toString()))
            .andExpect(jsonPath("$.loanAmount").value(DEFAULT_LOAN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.noOfInstallment").value(DEFAULT_NO_OF_INSTALLMENT.intValue()))
            .andExpect(jsonPath("$.reasonOfWithdrawal").value(DEFAULT_REASON_OF_WITHDRAWAL.toString()))
            .andExpect(jsonPath("$.isRepayFirstWithdraw").value(DEFAULT_IS_REPAY_FIRST_WITHDRAW.booleanValue()))
            .andExpect(jsonPath("$.noOfInstallmentLeft").value(DEFAULT_NO_OF_INSTALLMENT_LEFT.intValue()))
            .andExpect(jsonPath("$.lastInstallmentReDate").value(DEFAULT_LAST_INSTALLMENT_RE_DATE.toString()))
            .andExpect(jsonPath("$.applicationDate").value(DEFAULT_APPLICATION_DATE.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPfmsLoanApplication() throws Exception {
        // Get the pfmsLoanApplication
        restPfmsLoanApplicationMockMvc.perform(get("/api/pfmsLoanApplications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePfmsLoanApplication() throws Exception {
        // Initialize the database
        pfmsLoanApplicationRepository.saveAndFlush(pfmsLoanApplication);

		int databaseSizeBeforeUpdate = pfmsLoanApplicationRepository.findAll().size();

        // Update the pfmsLoanApplication
        pfmsLoanApplication.setLoanAmount(UPDATED_LOAN_AMOUNT);
        pfmsLoanApplication.setNoOfInstallment(UPDATED_NO_OF_INSTALLMENT);
        pfmsLoanApplication.setReasonOfWithdrawal(UPDATED_REASON_OF_WITHDRAWAL);
        pfmsLoanApplication.setIsRepayFirstWithdraw(UPDATED_IS_REPAY_FIRST_WITHDRAW);
        pfmsLoanApplication.setApplicationDate(UPDATED_APPLICATION_DATE);
        pfmsLoanApplication.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pfmsLoanApplication.setCreateDate(UPDATED_CREATE_DATE);
        pfmsLoanApplication.setCreateBy(UPDATED_CREATE_BY);
        pfmsLoanApplication.setUpdateDate(UPDATED_UPDATE_DATE);
        pfmsLoanApplication.setUpdateBy(UPDATED_UPDATE_BY);

        restPfmsLoanApplicationMockMvc.perform(put("/api/pfmsLoanApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsLoanApplication)))
                .andExpect(status().isOk());

        // Validate the PfmsLoanApplication in the database
        List<PfmsLoanApplication> pfmsLoanApplications = pfmsLoanApplicationRepository.findAll();
        assertThat(pfmsLoanApplications).hasSize(databaseSizeBeforeUpdate);
        PfmsLoanApplication testPfmsLoanApplication = pfmsLoanApplications.get(pfmsLoanApplications.size() - 1);
        assertThat(testPfmsLoanApplication.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testPfmsLoanApplication.getNoOfInstallment()).isEqualTo(UPDATED_NO_OF_INSTALLMENT);
        assertThat(testPfmsLoanApplication.getReasonOfWithdrawal()).isEqualTo(UPDATED_REASON_OF_WITHDRAWAL);
        assertThat(testPfmsLoanApplication.getIsRepayFirstWithdraw()).isEqualTo(UPDATED_IS_REPAY_FIRST_WITHDRAW);
        assertThat(testPfmsLoanApplication.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testPfmsLoanApplication.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPfmsLoanApplication.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPfmsLoanApplication.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPfmsLoanApplication.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPfmsLoanApplication.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePfmsLoanApplication() throws Exception {
        // Initialize the database
        pfmsLoanApplicationRepository.saveAndFlush(pfmsLoanApplication);

		int databaseSizeBeforeDelete = pfmsLoanApplicationRepository.findAll().size();

        // Get the pfmsLoanApplication
        restPfmsLoanApplicationMockMvc.perform(delete("/api/pfmsLoanApplications/{id}", pfmsLoanApplication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PfmsLoanApplication> pfmsLoanApplications = pfmsLoanApplicationRepository.findAll();
        assertThat(pfmsLoanApplications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
