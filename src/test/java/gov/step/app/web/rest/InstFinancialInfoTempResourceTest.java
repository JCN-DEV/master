package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstFinancialInfoTemp;
import gov.step.app.repository.InstFinancialInfoTempRepository;
import gov.step.app.repository.search.InstFinancialInfoTempSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.step.app.domain.enumeration.accountType;

/**
 * Test class for the InstFinancialInfoTempResource REST controller.
 *
 * @see InstFinancialInfoTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstFinancialInfoTempResourceTest {

    private static final String DEFAULT_BANK_NAME = "AAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBB";
    private static final String DEFAULT_BRANCH_NAME = "AAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBB";


private static final accountType DEFAULT_ACCOUNT_TYPE = accountType.General_Fund;
    private static final accountType UPDATED_ACCOUNT_TYPE = accountType.Preserved_Fund;
    private static final String DEFAULT_ACCOUNT_NO = "AAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBB";

    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPIRE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstFinancialInfoTempRepository instFinancialInfoTempRepository;

    @Inject
    private InstFinancialInfoTempSearchRepository instFinancialInfoTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstFinancialInfoTempMockMvc;

    private InstFinancialInfoTemp instFinancialInfoTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstFinancialInfoTempResource instFinancialInfoTempResource = new InstFinancialInfoTempResource();
        ReflectionTestUtils.setField(instFinancialInfoTempResource, "instFinancialInfoTempRepository", instFinancialInfoTempRepository);
        ReflectionTestUtils.setField(instFinancialInfoTempResource, "instFinancialInfoTempSearchRepository", instFinancialInfoTempSearchRepository);
        this.restInstFinancialInfoTempMockMvc = MockMvcBuilders.standaloneSetup(instFinancialInfoTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instFinancialInfoTemp = new InstFinancialInfoTemp();
        instFinancialInfoTemp.setBankName(DEFAULT_BANK_NAME);
        instFinancialInfoTemp.setBranchName(DEFAULT_BRANCH_NAME);
        instFinancialInfoTemp.setAccountType(DEFAULT_ACCOUNT_TYPE);
        instFinancialInfoTemp.setAccountNo(DEFAULT_ACCOUNT_NO);
        instFinancialInfoTemp.setIssueDate(DEFAULT_ISSUE_DATE);
        instFinancialInfoTemp.setExpireDate(DEFAULT_EXPIRE_DATE);
        instFinancialInfoTemp.setAmount(DEFAULT_AMOUNT);
        instFinancialInfoTemp.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstFinancialInfoTemp() throws Exception {
        int databaseSizeBeforeCreate = instFinancialInfoTempRepository.findAll().size();

        // Create the InstFinancialInfoTemp

        restInstFinancialInfoTempMockMvc.perform(post("/api/instFinancialInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfoTemp)))
                .andExpect(status().isCreated());

        // Validate the InstFinancialInfoTemp in the database
        List<InstFinancialInfoTemp> instFinancialInfoTemps = instFinancialInfoTempRepository.findAll();
        assertThat(instFinancialInfoTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstFinancialInfoTemp testInstFinancialInfoTemp = instFinancialInfoTemps.get(instFinancialInfoTemps.size() - 1);
        assertThat(testInstFinancialInfoTemp.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testInstFinancialInfoTemp.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testInstFinancialInfoTemp.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testInstFinancialInfoTemp.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testInstFinancialInfoTemp.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testInstFinancialInfoTemp.getExpireDate()).isEqualTo(DEFAULT_EXPIRE_DATE);
        assertThat(testInstFinancialInfoTemp.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInstFinancialInfoTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instFinancialInfoTempRepository.findAll().size();
        // set the field null
        instFinancialInfoTemp.setBankName(null);

        // Create the InstFinancialInfoTemp, which fails.

        restInstFinancialInfoTempMockMvc.perform(post("/api/instFinancialInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfoTemp)))
                .andExpect(status().isBadRequest());

        List<InstFinancialInfoTemp> instFinancialInfoTemps = instFinancialInfoTempRepository.findAll();
        assertThat(instFinancialInfoTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBranchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instFinancialInfoTempRepository.findAll().size();
        // set the field null
        instFinancialInfoTemp.setBranchName(null);

        // Create the InstFinancialInfoTemp, which fails.

        restInstFinancialInfoTempMockMvc.perform(post("/api/instFinancialInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfoTemp)))
                .andExpect(status().isBadRequest());

        List<InstFinancialInfoTemp> instFinancialInfoTemps = instFinancialInfoTempRepository.findAll();
        assertThat(instFinancialInfoTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instFinancialInfoTempRepository.findAll().size();
        // set the field null
        instFinancialInfoTemp.setAccountType(null);

        // Create the InstFinancialInfoTemp, which fails.

        restInstFinancialInfoTempMockMvc.perform(post("/api/instFinancialInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfoTemp)))
                .andExpect(status().isBadRequest());

        List<InstFinancialInfoTemp> instFinancialInfoTemps = instFinancialInfoTempRepository.findAll();
        assertThat(instFinancialInfoTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instFinancialInfoTempRepository.findAll().size();
        // set the field null
        instFinancialInfoTemp.setAccountNo(null);

        // Create the InstFinancialInfoTemp, which fails.

        restInstFinancialInfoTempMockMvc.perform(post("/api/instFinancialInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfoTemp)))
                .andExpect(status().isBadRequest());

        List<InstFinancialInfoTemp> instFinancialInfoTemps = instFinancialInfoTempRepository.findAll();
        assertThat(instFinancialInfoTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = instFinancialInfoTempRepository.findAll().size();
        // set the field null
        instFinancialInfoTemp.setAmount(null);

        // Create the InstFinancialInfoTemp, which fails.

        restInstFinancialInfoTempMockMvc.perform(post("/api/instFinancialInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfoTemp)))
                .andExpect(status().isBadRequest());

        List<InstFinancialInfoTemp> instFinancialInfoTemps = instFinancialInfoTempRepository.findAll();
        assertThat(instFinancialInfoTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstFinancialInfoTemps() throws Exception {
        // Initialize the database
        instFinancialInfoTempRepository.saveAndFlush(instFinancialInfoTemp);

        // Get all the instFinancialInfoTemps
        restInstFinancialInfoTempMockMvc.perform(get("/api/instFinancialInfoTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instFinancialInfoTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
                .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME.toString())))
                .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.toString())))
                .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
                .andExpect(jsonPath("$.[*].expireDate").value(hasItem(DEFAULT_EXPIRE_DATE.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstFinancialInfoTemp() throws Exception {
        // Initialize the database
        instFinancialInfoTempRepository.saveAndFlush(instFinancialInfoTemp);

        // Get the instFinancialInfoTemp
        restInstFinancialInfoTempMockMvc.perform(get("/api/instFinancialInfoTemps/{id}", instFinancialInfoTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instFinancialInfoTemp.getId().intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME.toString()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.expireDate").value(DEFAULT_EXPIRE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstFinancialInfoTemp() throws Exception {
        // Get the instFinancialInfoTemp
        restInstFinancialInfoTempMockMvc.perform(get("/api/instFinancialInfoTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstFinancialInfoTemp() throws Exception {
        // Initialize the database
        instFinancialInfoTempRepository.saveAndFlush(instFinancialInfoTemp);

		int databaseSizeBeforeUpdate = instFinancialInfoTempRepository.findAll().size();

        // Update the instFinancialInfoTemp
        instFinancialInfoTemp.setBankName(UPDATED_BANK_NAME);
        instFinancialInfoTemp.setBranchName(UPDATED_BRANCH_NAME);
        instFinancialInfoTemp.setAccountType(UPDATED_ACCOUNT_TYPE);
        instFinancialInfoTemp.setAccountNo(UPDATED_ACCOUNT_NO);
        instFinancialInfoTemp.setIssueDate(UPDATED_ISSUE_DATE);
        instFinancialInfoTemp.setExpireDate(UPDATED_EXPIRE_DATE);
        instFinancialInfoTemp.setAmount(UPDATED_AMOUNT);
        instFinancialInfoTemp.setStatus(UPDATED_STATUS);

        restInstFinancialInfoTempMockMvc.perform(put("/api/instFinancialInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfoTemp)))
                .andExpect(status().isOk());

        // Validate the InstFinancialInfoTemp in the database
        List<InstFinancialInfoTemp> instFinancialInfoTemps = instFinancialInfoTempRepository.findAll();
        assertThat(instFinancialInfoTemps).hasSize(databaseSizeBeforeUpdate);
        InstFinancialInfoTemp testInstFinancialInfoTemp = instFinancialInfoTemps.get(instFinancialInfoTemps.size() - 1);
        assertThat(testInstFinancialInfoTemp.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testInstFinancialInfoTemp.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testInstFinancialInfoTemp.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testInstFinancialInfoTemp.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testInstFinancialInfoTemp.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testInstFinancialInfoTemp.getExpireDate()).isEqualTo(UPDATED_EXPIRE_DATE);
        assertThat(testInstFinancialInfoTemp.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInstFinancialInfoTemp.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstFinancialInfoTemp() throws Exception {
        // Initialize the database
        instFinancialInfoTempRepository.saveAndFlush(instFinancialInfoTemp);

		int databaseSizeBeforeDelete = instFinancialInfoTempRepository.findAll().size();

        // Get the instFinancialInfoTemp
        restInstFinancialInfoTempMockMvc.perform(delete("/api/instFinancialInfoTemps/{id}", instFinancialInfoTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstFinancialInfoTemp> instFinancialInfoTemps = instFinancialInfoTempRepository.findAll();
        assertThat(instFinancialInfoTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
