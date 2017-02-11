package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpBankAccountInfo;
import gov.step.app.repository.HrEmpBankAccountInfoRepository;
import gov.step.app.repository.search.HrEmpBankAccountInfoSearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrEmpBankAccountInfoResource REST controller.
 *
 * @see HrEmpBankAccountInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpBankAccountInfoResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBB";
    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBB";
    private static final String DEFAULT_BRANCH_NAME = "AAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_SALARY_ACCOUNT = false;
    private static final Boolean UPDATED_SALARY_ACCOUNT = true;

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final Long DEFAULT_LOG_ID = 1L;
    private static final Long UPDATED_LOG_ID = 2L;

    private static final Long DEFAULT_LOG_STATUS = 1L;
    private static final Long UPDATED_LOG_STATUS = 2L;
    private static final String DEFAULT_LOG_COMMENTS = "AAAAA";
    private static final String UPDATED_LOG_COMMENTS = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private HrEmpBankAccountInfoRepository hrEmpBankAccountInfoRepository;

    @Inject
    private HrEmpBankAccountInfoSearchRepository hrEmpBankAccountInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpBankAccountInfoMockMvc;

    private HrEmpBankAccountInfo hrEmpBankAccountInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpBankAccountInfoResource hrEmpBankAccountInfoResource = new HrEmpBankAccountInfoResource();
        ReflectionTestUtils.setField(hrEmpBankAccountInfoResource, "hrEmpBankAccountInfoSearchRepository", hrEmpBankAccountInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpBankAccountInfoResource, "hrEmpBankAccountInfoRepository", hrEmpBankAccountInfoRepository);
        this.restHrEmpBankAccountInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpBankAccountInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpBankAccountInfo = new HrEmpBankAccountInfo();
        hrEmpBankAccountInfo.setAccountName(DEFAULT_ACCOUNT_NAME);
        hrEmpBankAccountInfo.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
        hrEmpBankAccountInfo.setBranchName(DEFAULT_BRANCH_NAME);
        hrEmpBankAccountInfo.setDescription(DEFAULT_DESCRIPTION);
        hrEmpBankAccountInfo.setSalaryAccount(DEFAULT_SALARY_ACCOUNT);
        hrEmpBankAccountInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpBankAccountInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpBankAccountInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpBankAccountInfo.setLogComments(DEFAULT_LOG_COMMENTS);
        hrEmpBankAccountInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpBankAccountInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpBankAccountInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpBankAccountInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpBankAccountInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpBankAccountInfoRepository.findAll().size();

        // Create the HrEmpBankAccountInfo

        restHrEmpBankAccountInfoMockMvc.perform(post("/api/hrEmpBankAccountInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpBankAccountInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpBankAccountInfo in the database
        List<HrEmpBankAccountInfo> hrEmpBankAccountInfos = hrEmpBankAccountInfoRepository.findAll();
        assertThat(hrEmpBankAccountInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpBankAccountInfo testHrEmpBankAccountInfo = hrEmpBankAccountInfos.get(hrEmpBankAccountInfos.size() - 1);
        assertThat(testHrEmpBankAccountInfo.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testHrEmpBankAccountInfo.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testHrEmpBankAccountInfo.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testHrEmpBankAccountInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHrEmpBankAccountInfo.getSalaryAccount()).isEqualTo(DEFAULT_SALARY_ACCOUNT);
        assertThat(testHrEmpBankAccountInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpBankAccountInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpBankAccountInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpBankAccountInfo.getLogComments()).isEqualTo(DEFAULT_LOG_COMMENTS);
        assertThat(testHrEmpBankAccountInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpBankAccountInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpBankAccountInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpBankAccountInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpBankAccountInfoRepository.findAll().size();
        // set the field null
        hrEmpBankAccountInfo.setAccountName(null);

        // Create the HrEmpBankAccountInfo, which fails.

        restHrEmpBankAccountInfoMockMvc.perform(post("/api/hrEmpBankAccountInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpBankAccountInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpBankAccountInfo> hrEmpBankAccountInfos = hrEmpBankAccountInfoRepository.findAll();
        assertThat(hrEmpBankAccountInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaryAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpBankAccountInfoRepository.findAll().size();
        // set the field null
        hrEmpBankAccountInfo.setSalaryAccount(null);

        // Create the HrEmpBankAccountInfo, which fails.

        restHrEmpBankAccountInfoMockMvc.perform(post("/api/hrEmpBankAccountInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpBankAccountInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpBankAccountInfo> hrEmpBankAccountInfos = hrEmpBankAccountInfoRepository.findAll();
        assertThat(hrEmpBankAccountInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpBankAccountInfoRepository.findAll().size();
        // set the field null
        hrEmpBankAccountInfo.setActiveStatus(null);

        // Create the HrEmpBankAccountInfo, which fails.

        restHrEmpBankAccountInfoMockMvc.perform(post("/api/hrEmpBankAccountInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpBankAccountInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpBankAccountInfo> hrEmpBankAccountInfos = hrEmpBankAccountInfoRepository.findAll();
        assertThat(hrEmpBankAccountInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpBankAccountInfos() throws Exception {
        // Initialize the database
        hrEmpBankAccountInfoRepository.saveAndFlush(hrEmpBankAccountInfo);

        // Get all the hrEmpBankAccountInfos
        restHrEmpBankAccountInfoMockMvc.perform(get("/api/hrEmpBankAccountInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpBankAccountInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
                .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].salaryAccount").value(hasItem(DEFAULT_SALARY_ACCOUNT.booleanValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].logId").value(hasItem(DEFAULT_LOG_ID.intValue())))
                .andExpect(jsonPath("$.[*].logStatus").value(hasItem(DEFAULT_LOG_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].logComments").value(hasItem(DEFAULT_LOG_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmpBankAccountInfo() throws Exception {
        // Initialize the database
        hrEmpBankAccountInfoRepository.saveAndFlush(hrEmpBankAccountInfo);

        // Get the hrEmpBankAccountInfo
        restHrEmpBankAccountInfoMockMvc.perform(get("/api/hrEmpBankAccountInfos/{id}", hrEmpBankAccountInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpBankAccountInfo.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.salaryAccount").value(DEFAULT_SALARY_ACCOUNT.booleanValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.logId").value(DEFAULT_LOG_ID.intValue()))
            .andExpect(jsonPath("$.logStatus").value(DEFAULT_LOG_STATUS.intValue()))
            .andExpect(jsonPath("$.logComments").value(DEFAULT_LOG_COMMENTS.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmpBankAccountInfo() throws Exception {
        // Get the hrEmpBankAccountInfo
        restHrEmpBankAccountInfoMockMvc.perform(get("/api/hrEmpBankAccountInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpBankAccountInfo() throws Exception {
        // Initialize the database
        hrEmpBankAccountInfoRepository.saveAndFlush(hrEmpBankAccountInfo);

		int databaseSizeBeforeUpdate = hrEmpBankAccountInfoRepository.findAll().size();

        // Update the hrEmpBankAccountInfo
        hrEmpBankAccountInfo.setAccountName(UPDATED_ACCOUNT_NAME);
        hrEmpBankAccountInfo.setAccountNumber(UPDATED_ACCOUNT_NUMBER);
        hrEmpBankAccountInfo.setBranchName(UPDATED_BRANCH_NAME);
        hrEmpBankAccountInfo.setDescription(UPDATED_DESCRIPTION);
        hrEmpBankAccountInfo.setSalaryAccount(UPDATED_SALARY_ACCOUNT);
        hrEmpBankAccountInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpBankAccountInfo.setLogId(UPDATED_LOG_ID);
        hrEmpBankAccountInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpBankAccountInfo.setLogComments(UPDATED_LOG_COMMENTS);
        hrEmpBankAccountInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpBankAccountInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpBankAccountInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpBankAccountInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpBankAccountInfoMockMvc.perform(put("/api/hrEmpBankAccountInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpBankAccountInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpBankAccountInfo in the database
        List<HrEmpBankAccountInfo> hrEmpBankAccountInfos = hrEmpBankAccountInfoRepository.findAll();
        assertThat(hrEmpBankAccountInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpBankAccountInfo testHrEmpBankAccountInfo = hrEmpBankAccountInfos.get(hrEmpBankAccountInfos.size() - 1);
        assertThat(testHrEmpBankAccountInfo.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testHrEmpBankAccountInfo.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testHrEmpBankAccountInfo.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testHrEmpBankAccountInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHrEmpBankAccountInfo.getSalaryAccount()).isEqualTo(UPDATED_SALARY_ACCOUNT);
        assertThat(testHrEmpBankAccountInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpBankAccountInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpBankAccountInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpBankAccountInfo.getLogComments()).isEqualTo(UPDATED_LOG_COMMENTS);
        assertThat(testHrEmpBankAccountInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpBankAccountInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpBankAccountInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpBankAccountInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpBankAccountInfo() throws Exception {
        // Initialize the database
        hrEmpBankAccountInfoRepository.saveAndFlush(hrEmpBankAccountInfo);

		int databaseSizeBeforeDelete = hrEmpBankAccountInfoRepository.findAll().size();

        // Get the hrEmpBankAccountInfo
        restHrEmpBankAccountInfoMockMvc.perform(delete("/api/hrEmpBankAccountInfos/{id}", hrEmpBankAccountInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpBankAccountInfo> hrEmpBankAccountInfos = hrEmpBankAccountInfoRepository.findAll();
        assertThat(hrEmpBankAccountInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
