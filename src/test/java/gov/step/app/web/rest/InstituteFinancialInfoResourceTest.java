package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstituteFinancialInfo;
import gov.step.app.repository.InstituteFinancialInfoRepository;
import gov.step.app.repository.search.InstituteFinancialInfoSearchRepository;

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
 * Test class for the InstituteFinancialInfoResource REST controller.
 *
 * @see InstituteFinancialInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstituteFinancialInfoResourceTest {

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
    private InstituteFinancialInfoRepository instituteFinancialInfoRepository;

    @Inject
    private InstituteFinancialInfoSearchRepository instituteFinancialInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstituteFinancialInfoMockMvc;

    private InstituteFinancialInfo instituteFinancialInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteFinancialInfoResource instituteFinancialInfoResource = new InstituteFinancialInfoResource();
        ReflectionTestUtils.setField(instituteFinancialInfoResource, "instituteFinancialInfoRepository", instituteFinancialInfoRepository);
        ReflectionTestUtils.setField(instituteFinancialInfoResource, "instituteFinancialInfoSearchRepository", instituteFinancialInfoSearchRepository);
        this.restInstituteFinancialInfoMockMvc = MockMvcBuilders.standaloneSetup(instituteFinancialInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instituteFinancialInfo = new InstituteFinancialInfo();
        instituteFinancialInfo.setBankName(DEFAULT_BANK_NAME);
        instituteFinancialInfo.setBranchName(DEFAULT_BRANCH_NAME);
        instituteFinancialInfo.setAccountType(DEFAULT_ACCOUNT_TYPE);
        instituteFinancialInfo.setAccountNo(DEFAULT_ACCOUNT_NO);
        instituteFinancialInfo.setIssueDate(DEFAULT_ISSUE_DATE);
        instituteFinancialInfo.setExpireDate(DEFAULT_EXPIRE_DATE);
        instituteFinancialInfo.setAmount(DEFAULT_AMOUNT);
        instituteFinancialInfo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstituteFinancialInfo() throws Exception {
        int databaseSizeBeforeCreate = instituteFinancialInfoRepository.findAll().size();

        // Create the InstituteFinancialInfo

        restInstituteFinancialInfoMockMvc.perform(post("/api/instituteFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteFinancialInfo)))
                .andExpect(status().isCreated());

        // Validate the InstituteFinancialInfo in the database
        List<InstituteFinancialInfo> instituteFinancialInfos = instituteFinancialInfoRepository.findAll();
        assertThat(instituteFinancialInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstituteFinancialInfo testInstituteFinancialInfo = instituteFinancialInfos.get(instituteFinancialInfos.size() - 1);
        assertThat(testInstituteFinancialInfo.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testInstituteFinancialInfo.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testInstituteFinancialInfo.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testInstituteFinancialInfo.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testInstituteFinancialInfo.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testInstituteFinancialInfo.getExpireDate()).isEqualTo(DEFAULT_EXPIRE_DATE);
        assertThat(testInstituteFinancialInfo.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInstituteFinancialInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteFinancialInfoRepository.findAll().size();
        // set the field null
        instituteFinancialInfo.setBankName(null);

        // Create the InstituteFinancialInfo, which fails.

        restInstituteFinancialInfoMockMvc.perform(post("/api/instituteFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteFinancialInfo)))
                .andExpect(status().isBadRequest());

        List<InstituteFinancialInfo> instituteFinancialInfos = instituteFinancialInfoRepository.findAll();
        assertThat(instituteFinancialInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBranchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteFinancialInfoRepository.findAll().size();
        // set the field null
        instituteFinancialInfo.setBranchName(null);

        // Create the InstituteFinancialInfo, which fails.

        restInstituteFinancialInfoMockMvc.perform(post("/api/instituteFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteFinancialInfo)))
                .andExpect(status().isBadRequest());

        List<InstituteFinancialInfo> instituteFinancialInfos = instituteFinancialInfoRepository.findAll();
        assertThat(instituteFinancialInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteFinancialInfoRepository.findAll().size();
        // set the field null
        instituteFinancialInfo.setAccountType(null);

        // Create the InstituteFinancialInfo, which fails.

        restInstituteFinancialInfoMockMvc.perform(post("/api/instituteFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteFinancialInfo)))
                .andExpect(status().isBadRequest());

        List<InstituteFinancialInfo> instituteFinancialInfos = instituteFinancialInfoRepository.findAll();
        assertThat(instituteFinancialInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteFinancialInfoRepository.findAll().size();
        // set the field null
        instituteFinancialInfo.setAccountNo(null);

        // Create the InstituteFinancialInfo, which fails.

        restInstituteFinancialInfoMockMvc.perform(post("/api/instituteFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteFinancialInfo)))
                .andExpect(status().isBadRequest());

        List<InstituteFinancialInfo> instituteFinancialInfos = instituteFinancialInfoRepository.findAll();
        assertThat(instituteFinancialInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteFinancialInfoRepository.findAll().size();
        // set the field null
        instituteFinancialInfo.setAmount(null);

        // Create the InstituteFinancialInfo, which fails.

        restInstituteFinancialInfoMockMvc.perform(post("/api/instituteFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteFinancialInfo)))
                .andExpect(status().isBadRequest());

        List<InstituteFinancialInfo> instituteFinancialInfos = instituteFinancialInfoRepository.findAll();
        assertThat(instituteFinancialInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstituteFinancialInfos() throws Exception {
        // Initialize the database
        instituteFinancialInfoRepository.saveAndFlush(instituteFinancialInfo);

        // Get all the instituteFinancialInfos
        restInstituteFinancialInfoMockMvc.perform(get("/api/instituteFinancialInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instituteFinancialInfo.getId().intValue())))
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
    public void getInstituteFinancialInfo() throws Exception {
        // Initialize the database
        instituteFinancialInfoRepository.saveAndFlush(instituteFinancialInfo);

        // Get the instituteFinancialInfo
        restInstituteFinancialInfoMockMvc.perform(get("/api/instituteFinancialInfos/{id}", instituteFinancialInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instituteFinancialInfo.getId().intValue()))
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
    public void getNonExistingInstituteFinancialInfo() throws Exception {
        // Get the instituteFinancialInfo
        restInstituteFinancialInfoMockMvc.perform(get("/api/instituteFinancialInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituteFinancialInfo() throws Exception {
        // Initialize the database
        instituteFinancialInfoRepository.saveAndFlush(instituteFinancialInfo);

		int databaseSizeBeforeUpdate = instituteFinancialInfoRepository.findAll().size();

        // Update the instituteFinancialInfo
        instituteFinancialInfo.setBankName(UPDATED_BANK_NAME);
        instituteFinancialInfo.setBranchName(UPDATED_BRANCH_NAME);
        instituteFinancialInfo.setAccountType(UPDATED_ACCOUNT_TYPE);
        instituteFinancialInfo.setAccountNo(UPDATED_ACCOUNT_NO);
        instituteFinancialInfo.setIssueDate(UPDATED_ISSUE_DATE);
        instituteFinancialInfo.setExpireDate(UPDATED_EXPIRE_DATE);
        instituteFinancialInfo.setAmount(UPDATED_AMOUNT);
        instituteFinancialInfo.setStatus(UPDATED_STATUS);

        restInstituteFinancialInfoMockMvc.perform(put("/api/instituteFinancialInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteFinancialInfo)))
                .andExpect(status().isOk());

        // Validate the InstituteFinancialInfo in the database
        List<InstituteFinancialInfo> instituteFinancialInfos = instituteFinancialInfoRepository.findAll();
        assertThat(instituteFinancialInfos).hasSize(databaseSizeBeforeUpdate);
        InstituteFinancialInfo testInstituteFinancialInfo = instituteFinancialInfos.get(instituteFinancialInfos.size() - 1);
        assertThat(testInstituteFinancialInfo.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testInstituteFinancialInfo.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testInstituteFinancialInfo.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testInstituteFinancialInfo.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testInstituteFinancialInfo.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testInstituteFinancialInfo.getExpireDate()).isEqualTo(UPDATED_EXPIRE_DATE);
        assertThat(testInstituteFinancialInfo.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInstituteFinancialInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstituteFinancialInfo() throws Exception {
        // Initialize the database
        instituteFinancialInfoRepository.saveAndFlush(instituteFinancialInfo);

		int databaseSizeBeforeDelete = instituteFinancialInfoRepository.findAll().size();

        // Get the instituteFinancialInfo
        restInstituteFinancialInfoMockMvc.perform(delete("/api/instituteFinancialInfos/{id}", instituteFinancialInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstituteFinancialInfo> instituteFinancialInfos = instituteFinancialInfoRepository.findAll();
        assertThat(instituteFinancialInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
