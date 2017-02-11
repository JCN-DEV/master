package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstFinancialInfo;
import gov.step.app.repository.InstFinancialInfoRepository;
import gov.step.app.repository.search.InstFinancialInfoSearchRepository;

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
 * Test class for the InstFinancialInfoResource REST controller.
 *
 * @see InstFinancialInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstFinancialInfoResourceTest {

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
    private InstFinancialInfoRepository instFinancialInfoRepository;

    @Inject
    private InstFinancialInfoSearchRepository instFinancialInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstFinancialInfoMockMvc;

    private InstFinancialInfo instFinancialInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstFinancialInfoResource instFinancialInfoResource = new InstFinancialInfoResource();
        ReflectionTestUtils.setField(instFinancialInfoResource, "instFinancialInfoRepository", instFinancialInfoRepository);
        ReflectionTestUtils.setField(instFinancialInfoResource, "instFinancialInfoSearchRepository", instFinancialInfoSearchRepository);
        this.restInstFinancialInfoMockMvc = MockMvcBuilders.standaloneSetup(instFinancialInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instFinancialInfo = new InstFinancialInfo();
        instFinancialInfo.setBankName(DEFAULT_BANK_NAME);
        instFinancialInfo.setBranchName(DEFAULT_BRANCH_NAME);
        instFinancialInfo.setAccountType(DEFAULT_ACCOUNT_TYPE);
        instFinancialInfo.setAccountNo(DEFAULT_ACCOUNT_NO);
        instFinancialInfo.setIssueDate(DEFAULT_ISSUE_DATE);
        instFinancialInfo.setExpireDate(DEFAULT_EXPIRE_DATE);
        instFinancialInfo.setAmount(DEFAULT_AMOUNT);
        instFinancialInfo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstFinancialInfo() throws Exception {
        int databaseSizeBeforeCreate = instFinancialInfoRepository.findAll().size();

        // Create the InstFinancialInfo

        restInstFinancialInfoMockMvc.perform(post("/api/instFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfo)))
                .andExpect(status().isCreated());

        // Validate the InstFinancialInfo in the database
        List<InstFinancialInfo> instFinancialInfos = instFinancialInfoRepository.findAll();
        assertThat(instFinancialInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstFinancialInfo testInstFinancialInfo = instFinancialInfos.get(instFinancialInfos.size() - 1);
        assertThat(testInstFinancialInfo.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testInstFinancialInfo.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testInstFinancialInfo.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testInstFinancialInfo.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testInstFinancialInfo.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testInstFinancialInfo.getExpireDate()).isEqualTo(DEFAULT_EXPIRE_DATE);
        assertThat(testInstFinancialInfo.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInstFinancialInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instFinancialInfoRepository.findAll().size();
        // set the field null
        instFinancialInfo.setBankName(null);

        // Create the InstFinancialInfo, which fails.

        restInstFinancialInfoMockMvc.perform(post("/api/instFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfo)))
                .andExpect(status().isBadRequest());

        List<InstFinancialInfo> instFinancialInfos = instFinancialInfoRepository.findAll();
        assertThat(instFinancialInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBranchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instFinancialInfoRepository.findAll().size();
        // set the field null
        instFinancialInfo.setBranchName(null);

        // Create the InstFinancialInfo, which fails.

        restInstFinancialInfoMockMvc.perform(post("/api/instFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfo)))
                .andExpect(status().isBadRequest());

        List<InstFinancialInfo> instFinancialInfos = instFinancialInfoRepository.findAll();
        assertThat(instFinancialInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instFinancialInfoRepository.findAll().size();
        // set the field null
        instFinancialInfo.setAccountType(null);

        // Create the InstFinancialInfo, which fails.

        restInstFinancialInfoMockMvc.perform(post("/api/instFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfo)))
                .andExpect(status().isBadRequest());

        List<InstFinancialInfo> instFinancialInfos = instFinancialInfoRepository.findAll();
        assertThat(instFinancialInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instFinancialInfoRepository.findAll().size();
        // set the field null
        instFinancialInfo.setAccountNo(null);

        // Create the InstFinancialInfo, which fails.

        restInstFinancialInfoMockMvc.perform(post("/api/instFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfo)))
                .andExpect(status().isBadRequest());

        List<InstFinancialInfo> instFinancialInfos = instFinancialInfoRepository.findAll();
        assertThat(instFinancialInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = instFinancialInfoRepository.findAll().size();
        // set the field null
        instFinancialInfo.setAmount(null);

        // Create the InstFinancialInfo, which fails.

        restInstFinancialInfoMockMvc.perform(post("/api/instFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfo)))
                .andExpect(status().isBadRequest());

        List<InstFinancialInfo> instFinancialInfos = instFinancialInfoRepository.findAll();
        assertThat(instFinancialInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstFinancialInfos() throws Exception {
        // Initialize the database
        instFinancialInfoRepository.saveAndFlush(instFinancialInfo);

        // Get all the instFinancialInfos
        restInstFinancialInfoMockMvc.perform(get("/api/instFinancialInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instFinancialInfo.getId().intValue())))
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
    public void getInstFinancialInfo() throws Exception {
        // Initialize the database
        instFinancialInfoRepository.saveAndFlush(instFinancialInfo);

        // Get the instFinancialInfo
        restInstFinancialInfoMockMvc.perform(get("/api/instFinancialInfos/{id}", instFinancialInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instFinancialInfo.getId().intValue()))
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
    public void getNonExistingInstFinancialInfo() throws Exception {
        // Get the instFinancialInfo
        restInstFinancialInfoMockMvc.perform(get("/api/instFinancialInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstFinancialInfo() throws Exception {
        // Initialize the database
        instFinancialInfoRepository.saveAndFlush(instFinancialInfo);

		int databaseSizeBeforeUpdate = instFinancialInfoRepository.findAll().size();

        // Update the instFinancialInfo
        instFinancialInfo.setBankName(UPDATED_BANK_NAME);
        instFinancialInfo.setBranchName(UPDATED_BRANCH_NAME);
        instFinancialInfo.setAccountType(UPDATED_ACCOUNT_TYPE);
        instFinancialInfo.setAccountNo(UPDATED_ACCOUNT_NO);
        instFinancialInfo.setIssueDate(UPDATED_ISSUE_DATE);
        instFinancialInfo.setExpireDate(UPDATED_EXPIRE_DATE);
        instFinancialInfo.setAmount(UPDATED_AMOUNT);
        instFinancialInfo.setStatus(UPDATED_STATUS);

        restInstFinancialInfoMockMvc.perform(put("/api/instFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instFinancialInfo)))
                .andExpect(status().isOk());

        // Validate the InstFinancialInfo in the database
        List<InstFinancialInfo> instFinancialInfos = instFinancialInfoRepository.findAll();
        assertThat(instFinancialInfos).hasSize(databaseSizeBeforeUpdate);
        InstFinancialInfo testInstFinancialInfo = instFinancialInfos.get(instFinancialInfos.size() - 1);
        assertThat(testInstFinancialInfo.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testInstFinancialInfo.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testInstFinancialInfo.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testInstFinancialInfo.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testInstFinancialInfo.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testInstFinancialInfo.getExpireDate()).isEqualTo(UPDATED_EXPIRE_DATE);
        assertThat(testInstFinancialInfo.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInstFinancialInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstFinancialInfo() throws Exception {
        // Initialize the database
        instFinancialInfoRepository.saveAndFlush(instFinancialInfo);

		int databaseSizeBeforeDelete = instFinancialInfoRepository.findAll().size();

        // Get the instFinancialInfo
        restInstFinancialInfoMockMvc.perform(delete("/api/instFinancialInfos/{id}", instFinancialInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstFinancialInfo> instFinancialInfos = instFinancialInfoRepository.findAll();
        assertThat(instFinancialInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
