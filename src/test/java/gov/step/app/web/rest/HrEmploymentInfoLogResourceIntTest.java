package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmploymentInfoLog;
import gov.step.app.repository.HrEmploymentInfoLogRepository;
import gov.step.app.repository.search.HrEmploymentInfoLogSearchRepository;
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
 * Test class for the HrEmploymentInfoLogResource REST controller.
 *
 * @see HrEmploymentInfoLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmploymentInfoLogResourceIntTest {

    private static final String DEFAULT_PRESENT_INSTITUTE = "AAAAA";
    private static final String UPDATED_PRESENT_INSTITUTE = "BBBBB";

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REGULARIZATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGULARIZATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_JOB_CONF_NOTICE_NO = "AAAAA";
    private static final String UPDATED_JOB_CONF_NOTICE_NO = "BBBBB";

    private static final LocalDate DEFAULT_CONFIRMATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONFIRMATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_OFFICE_ORDER_NO = "AAAAA";
    private static final String UPDATED_OFFICE_ORDER_NO = "BBBBB";

    private static final LocalDate DEFAULT_OFFICE_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OFFICE_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Long DEFAULT_LOG_STATUS = 1L;
    private static final Long UPDATED_LOG_STATUS = 2L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_ACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ACTION_BY = 1L;
    private static final Long UPDATED_ACTION_BY = 2L;
    private static final String DEFAULT_ACTION_COMMENTS = "AAAAA";
    private static final String UPDATED_ACTION_COMMENTS = "BBBBB";

    @Inject
    private HrEmploymentInfoLogRepository hrEmploymentInfoLogRepository;

    @Inject
    private HrEmploymentInfoLogSearchRepository hrEmploymentInfoLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmploymentInfoLogMockMvc;

    private HrEmploymentInfoLog hrEmploymentInfoLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmploymentInfoLogResource hrEmploymentInfoLogResource = new HrEmploymentInfoLogResource();
        ReflectionTestUtils.setField(hrEmploymentInfoLogResource, "hrEmploymentInfoLogSearchRepository", hrEmploymentInfoLogSearchRepository);
        ReflectionTestUtils.setField(hrEmploymentInfoLogResource, "hrEmploymentInfoLogRepository", hrEmploymentInfoLogRepository);
        this.restHrEmploymentInfoLogMockMvc = MockMvcBuilders.standaloneSetup(hrEmploymentInfoLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmploymentInfoLog = new HrEmploymentInfoLog();
        hrEmploymentInfoLog.setPresentInstitute(DEFAULT_PRESENT_INSTITUTE);
        hrEmploymentInfoLog.setJoiningDate(DEFAULT_JOINING_DATE);
        hrEmploymentInfoLog.setRegularizationDate(DEFAULT_REGULARIZATION_DATE);
        hrEmploymentInfoLog.setJobConfNoticeNo(DEFAULT_JOB_CONF_NOTICE_NO);
        hrEmploymentInfoLog.setConfirmationDate(DEFAULT_CONFIRMATION_DATE);
        hrEmploymentInfoLog.setOfficeOrderNo(DEFAULT_OFFICE_ORDER_NO);
        hrEmploymentInfoLog.setOfficeOrderDate(DEFAULT_OFFICE_ORDER_DATE);
        hrEmploymentInfoLog.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmploymentInfoLog.setParentId(DEFAULT_PARENT_ID);
        hrEmploymentInfoLog.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmploymentInfoLog.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmploymentInfoLog.setCreateBy(DEFAULT_CREATE_BY);
        hrEmploymentInfoLog.setActionDate(DEFAULT_ACTION_DATE);
        hrEmploymentInfoLog.setActionBy(DEFAULT_ACTION_BY);
        hrEmploymentInfoLog.setActionComments(DEFAULT_ACTION_COMMENTS);
    }

    @Test
    @Transactional
    public void createHrEmploymentInfoLog() throws Exception {
        int databaseSizeBeforeCreate = hrEmploymentInfoLogRepository.findAll().size();

        // Create the HrEmploymentInfoLog

        restHrEmploymentInfoLogMockMvc.perform(post("/api/hrEmploymentInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfoLog)))
                .andExpect(status().isCreated());

        // Validate the HrEmploymentInfoLog in the database
        List<HrEmploymentInfoLog> hrEmploymentInfoLogs = hrEmploymentInfoLogRepository.findAll();
        assertThat(hrEmploymentInfoLogs).hasSize(databaseSizeBeforeCreate + 1);
        HrEmploymentInfoLog testHrEmploymentInfoLog = hrEmploymentInfoLogs.get(hrEmploymentInfoLogs.size() - 1);
        assertThat(testHrEmploymentInfoLog.getPresentInstitute()).isEqualTo(DEFAULT_PRESENT_INSTITUTE);
        assertThat(testHrEmploymentInfoLog.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testHrEmploymentInfoLog.getRegularizationDate()).isEqualTo(DEFAULT_REGULARIZATION_DATE);
        assertThat(testHrEmploymentInfoLog.getJobConfNoticeNo()).isEqualTo(DEFAULT_JOB_CONF_NOTICE_NO);
        assertThat(testHrEmploymentInfoLog.getConfirmationDate()).isEqualTo(DEFAULT_CONFIRMATION_DATE);
        assertThat(testHrEmploymentInfoLog.getOfficeOrderNo()).isEqualTo(DEFAULT_OFFICE_ORDER_NO);
        assertThat(testHrEmploymentInfoLog.getOfficeOrderDate()).isEqualTo(DEFAULT_OFFICE_ORDER_DATE);
        assertThat(testHrEmploymentInfoLog.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmploymentInfoLog.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testHrEmploymentInfoLog.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmploymentInfoLog.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmploymentInfoLog.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmploymentInfoLog.getActionDate()).isEqualTo(DEFAULT_ACTION_DATE);
        assertThat(testHrEmploymentInfoLog.getActionBy()).isEqualTo(DEFAULT_ACTION_BY);
        assertThat(testHrEmploymentInfoLog.getActionComments()).isEqualTo(DEFAULT_ACTION_COMMENTS);
    }

    @Test
    @Transactional
    public void checkPresentInstituteIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmploymentInfoLogRepository.findAll().size();
        // set the field null
        hrEmploymentInfoLog.setPresentInstitute(null);

        // Create the HrEmploymentInfoLog, which fails.

        restHrEmploymentInfoLogMockMvc.perform(post("/api/hrEmploymentInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfoLog)))
                .andExpect(status().isBadRequest());

        List<HrEmploymentInfoLog> hrEmploymentInfoLogs = hrEmploymentInfoLogRepository.findAll();
        assertThat(hrEmploymentInfoLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJoiningDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmploymentInfoLogRepository.findAll().size();
        // set the field null
        hrEmploymentInfoLog.setJoiningDate(null);

        // Create the HrEmploymentInfoLog, which fails.

        restHrEmploymentInfoLogMockMvc.perform(post("/api/hrEmploymentInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfoLog)))
                .andExpect(status().isBadRequest());

        List<HrEmploymentInfoLog> hrEmploymentInfoLogs = hrEmploymentInfoLogRepository.findAll();
        assertThat(hrEmploymentInfoLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegularizationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmploymentInfoLogRepository.findAll().size();
        // set the field null
        hrEmploymentInfoLog.setRegularizationDate(null);

        // Create the HrEmploymentInfoLog, which fails.

        restHrEmploymentInfoLogMockMvc.perform(post("/api/hrEmploymentInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfoLog)))
                .andExpect(status().isBadRequest());

        List<HrEmploymentInfoLog> hrEmploymentInfoLogs = hrEmploymentInfoLogRepository.findAll();
        assertThat(hrEmploymentInfoLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmploymentInfoLogRepository.findAll().size();
        // set the field null
        hrEmploymentInfoLog.setActiveStatus(null);

        // Create the HrEmploymentInfoLog, which fails.

        restHrEmploymentInfoLogMockMvc.perform(post("/api/hrEmploymentInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfoLog)))
                .andExpect(status().isBadRequest());

        List<HrEmploymentInfoLog> hrEmploymentInfoLogs = hrEmploymentInfoLogRepository.findAll();
        assertThat(hrEmploymentInfoLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmploymentInfoLogs() throws Exception {
        // Initialize the database
        hrEmploymentInfoLogRepository.saveAndFlush(hrEmploymentInfoLog);

        // Get all the hrEmploymentInfoLogs
        restHrEmploymentInfoLogMockMvc.perform(get("/api/hrEmploymentInfoLogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmploymentInfoLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].presentInstitute").value(hasItem(DEFAULT_PRESENT_INSTITUTE.toString())))
                .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
                .andExpect(jsonPath("$.[*].regularizationDate").value(hasItem(DEFAULT_REGULARIZATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].jobConfNoticeNo").value(hasItem(DEFAULT_JOB_CONF_NOTICE_NO.toString())))
                .andExpect(jsonPath("$.[*].confirmationDate").value(hasItem(DEFAULT_CONFIRMATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].officeOrderNo").value(hasItem(DEFAULT_OFFICE_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].officeOrderDate").value(hasItem(DEFAULT_OFFICE_ORDER_DATE.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
                .andExpect(jsonPath("$.[*].logStatus").value(hasItem(DEFAULT_LOG_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].actionDate").value(hasItem(DEFAULT_ACTION_DATE.toString())))
                .andExpect(jsonPath("$.[*].actionBy").value(hasItem(DEFAULT_ACTION_BY.intValue())))
                .andExpect(jsonPath("$.[*].actionComments").value(hasItem(DEFAULT_ACTION_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getHrEmploymentInfoLog() throws Exception {
        // Initialize the database
        hrEmploymentInfoLogRepository.saveAndFlush(hrEmploymentInfoLog);

        // Get the hrEmploymentInfoLog
        restHrEmploymentInfoLogMockMvc.perform(get("/api/hrEmploymentInfoLogs/{id}", hrEmploymentInfoLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmploymentInfoLog.getId().intValue()))
            .andExpect(jsonPath("$.presentInstitute").value(DEFAULT_PRESENT_INSTITUTE.toString()))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.regularizationDate").value(DEFAULT_REGULARIZATION_DATE.toString()))
            .andExpect(jsonPath("$.jobConfNoticeNo").value(DEFAULT_JOB_CONF_NOTICE_NO.toString()))
            .andExpect(jsonPath("$.confirmationDate").value(DEFAULT_CONFIRMATION_DATE.toString()))
            .andExpect(jsonPath("$.officeOrderNo").value(DEFAULT_OFFICE_ORDER_NO.toString()))
            .andExpect(jsonPath("$.officeOrderDate").value(DEFAULT_OFFICE_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.logStatus").value(DEFAULT_LOG_STATUS.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.actionDate").value(DEFAULT_ACTION_DATE.toString()))
            .andExpect(jsonPath("$.actionBy").value(DEFAULT_ACTION_BY.intValue()))
            .andExpect(jsonPath("$.actionComments").value(DEFAULT_ACTION_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmploymentInfoLog() throws Exception {
        // Get the hrEmploymentInfoLog
        restHrEmploymentInfoLogMockMvc.perform(get("/api/hrEmploymentInfoLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmploymentInfoLog() throws Exception {
        // Initialize the database
        hrEmploymentInfoLogRepository.saveAndFlush(hrEmploymentInfoLog);

		int databaseSizeBeforeUpdate = hrEmploymentInfoLogRepository.findAll().size();

        // Update the hrEmploymentInfoLog
        hrEmploymentInfoLog.setPresentInstitute(UPDATED_PRESENT_INSTITUTE);
        hrEmploymentInfoLog.setJoiningDate(UPDATED_JOINING_DATE);
        hrEmploymentInfoLog.setRegularizationDate(UPDATED_REGULARIZATION_DATE);
        hrEmploymentInfoLog.setJobConfNoticeNo(UPDATED_JOB_CONF_NOTICE_NO);
        hrEmploymentInfoLog.setConfirmationDate(UPDATED_CONFIRMATION_DATE);
        hrEmploymentInfoLog.setOfficeOrderNo(UPDATED_OFFICE_ORDER_NO);
        hrEmploymentInfoLog.setOfficeOrderDate(UPDATED_OFFICE_ORDER_DATE);
        hrEmploymentInfoLog.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmploymentInfoLog.setParentId(UPDATED_PARENT_ID);
        hrEmploymentInfoLog.setLogStatus(UPDATED_LOG_STATUS);
        hrEmploymentInfoLog.setCreateDate(UPDATED_CREATE_DATE);
        hrEmploymentInfoLog.setCreateBy(UPDATED_CREATE_BY);
        hrEmploymentInfoLog.setActionDate(UPDATED_ACTION_DATE);
        hrEmploymentInfoLog.setActionBy(UPDATED_ACTION_BY);
        hrEmploymentInfoLog.setActionComments(UPDATED_ACTION_COMMENTS);

        restHrEmploymentInfoLogMockMvc.perform(put("/api/hrEmploymentInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfoLog)))
                .andExpect(status().isOk());

        // Validate the HrEmploymentInfoLog in the database
        List<HrEmploymentInfoLog> hrEmploymentInfoLogs = hrEmploymentInfoLogRepository.findAll();
        assertThat(hrEmploymentInfoLogs).hasSize(databaseSizeBeforeUpdate);
        HrEmploymentInfoLog testHrEmploymentInfoLog = hrEmploymentInfoLogs.get(hrEmploymentInfoLogs.size() - 1);
        assertThat(testHrEmploymentInfoLog.getPresentInstitute()).isEqualTo(UPDATED_PRESENT_INSTITUTE);
        assertThat(testHrEmploymentInfoLog.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testHrEmploymentInfoLog.getRegularizationDate()).isEqualTo(UPDATED_REGULARIZATION_DATE);
        assertThat(testHrEmploymentInfoLog.getJobConfNoticeNo()).isEqualTo(UPDATED_JOB_CONF_NOTICE_NO);
        assertThat(testHrEmploymentInfoLog.getConfirmationDate()).isEqualTo(UPDATED_CONFIRMATION_DATE);
        assertThat(testHrEmploymentInfoLog.getOfficeOrderNo()).isEqualTo(UPDATED_OFFICE_ORDER_NO);
        assertThat(testHrEmploymentInfoLog.getOfficeOrderDate()).isEqualTo(UPDATED_OFFICE_ORDER_DATE);
        assertThat(testHrEmploymentInfoLog.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmploymentInfoLog.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testHrEmploymentInfoLog.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmploymentInfoLog.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmploymentInfoLog.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmploymentInfoLog.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testHrEmploymentInfoLog.getActionBy()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testHrEmploymentInfoLog.getActionComments()).isEqualTo(UPDATED_ACTION_COMMENTS);
    }

    @Test
    @Transactional
    public void deleteHrEmploymentInfoLog() throws Exception {
        // Initialize the database
        hrEmploymentInfoLogRepository.saveAndFlush(hrEmploymentInfoLog);

		int databaseSizeBeforeDelete = hrEmploymentInfoLogRepository.findAll().size();

        // Get the hrEmploymentInfoLog
        restHrEmploymentInfoLogMockMvc.perform(delete("/api/hrEmploymentInfoLogs/{id}", hrEmploymentInfoLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmploymentInfoLog> hrEmploymentInfoLogs = hrEmploymentInfoLogRepository.findAll();
        assertThat(hrEmploymentInfoLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
