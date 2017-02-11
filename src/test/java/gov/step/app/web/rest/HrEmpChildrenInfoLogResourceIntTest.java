package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpChildrenInfoLog;
import gov.step.app.domain.enumeration.Gender;
import gov.step.app.repository.HrEmpChildrenInfoLogRepository;
import gov.step.app.repository.search.HrEmpChildrenInfoLogSearchRepository;
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
 * Test class for the HrEmpChildrenInfoLogResource REST controller.
 *
 * @see HrEmpChildrenInfoLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpChildrenInfoLogResourceIntTest {

    private static final String DEFAULT_CHILDREN_NAME = "AAAAA";
    private static final String UPDATED_CHILDREN_NAME = "BBBBB";
    private static final String DEFAULT_CHILDREN_NAME_BN = "AAAAA";
    private static final String UPDATED_CHILDREN_NAME_BN = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());


    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;

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
    private HrEmpChildrenInfoLogRepository hrEmpChildrenInfoLogRepository;

    @Inject
    private HrEmpChildrenInfoLogSearchRepository hrEmpChildrenInfoLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpChildrenInfoLogMockMvc;

    private HrEmpChildrenInfoLog hrEmpChildrenInfoLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpChildrenInfoLogResource hrEmpChildrenInfoLogResource = new HrEmpChildrenInfoLogResource();
        ReflectionTestUtils.setField(hrEmpChildrenInfoLogResource, "hrEmpChildrenInfoLogSearchRepository", hrEmpChildrenInfoLogSearchRepository);
        ReflectionTestUtils.setField(hrEmpChildrenInfoLogResource, "hrEmpChildrenInfoLogRepository", hrEmpChildrenInfoLogRepository);
        this.restHrEmpChildrenInfoLogMockMvc = MockMvcBuilders.standaloneSetup(hrEmpChildrenInfoLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpChildrenInfoLog = new HrEmpChildrenInfoLog();
        hrEmpChildrenInfoLog.setChildrenName(DEFAULT_CHILDREN_NAME);
        hrEmpChildrenInfoLog.setChildrenNameBn(DEFAULT_CHILDREN_NAME_BN);
        hrEmpChildrenInfoLog.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
        hrEmpChildrenInfoLog.setGender(DEFAULT_GENDER);
        hrEmpChildrenInfoLog.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpChildrenInfoLog.setParentId(DEFAULT_PARENT_ID);
        hrEmpChildrenInfoLog.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpChildrenInfoLog.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpChildrenInfoLog.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpChildrenInfoLog.setActionDate(DEFAULT_ACTION_DATE);
        hrEmpChildrenInfoLog.setActionBy(DEFAULT_ACTION_BY);
        hrEmpChildrenInfoLog.setActionComments(DEFAULT_ACTION_COMMENTS);
    }

    @Test
    @Transactional
    public void createHrEmpChildrenInfoLog() throws Exception {
        int databaseSizeBeforeCreate = hrEmpChildrenInfoLogRepository.findAll().size();

        // Create the HrEmpChildrenInfoLog

        restHrEmpChildrenInfoLogMockMvc.perform(post("/api/hrEmpChildrenInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfoLog)))
                .andExpect(status().isCreated());

        // Validate the HrEmpChildrenInfoLog in the database
        List<HrEmpChildrenInfoLog> hrEmpChildrenInfoLogs = hrEmpChildrenInfoLogRepository.findAll();
        assertThat(hrEmpChildrenInfoLogs).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpChildrenInfoLog testHrEmpChildrenInfoLog = hrEmpChildrenInfoLogs.get(hrEmpChildrenInfoLogs.size() - 1);
        assertThat(testHrEmpChildrenInfoLog.getChildrenName()).isEqualTo(DEFAULT_CHILDREN_NAME);
        assertThat(testHrEmpChildrenInfoLog.getChildrenNameBn()).isEqualTo(DEFAULT_CHILDREN_NAME_BN);
        assertThat(testHrEmpChildrenInfoLog.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testHrEmpChildrenInfoLog.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testHrEmpChildrenInfoLog.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpChildrenInfoLog.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testHrEmpChildrenInfoLog.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpChildrenInfoLog.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpChildrenInfoLog.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpChildrenInfoLog.getActionDate()).isEqualTo(DEFAULT_ACTION_DATE);
        assertThat(testHrEmpChildrenInfoLog.getActionBy()).isEqualTo(DEFAULT_ACTION_BY);
        assertThat(testHrEmpChildrenInfoLog.getActionComments()).isEqualTo(DEFAULT_ACTION_COMMENTS);
    }

    @Test
    @Transactional
    public void checkChildrenNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpChildrenInfoLogRepository.findAll().size();
        // set the field null
        hrEmpChildrenInfoLog.setChildrenName(null);

        // Create the HrEmpChildrenInfoLog, which fails.

        restHrEmpChildrenInfoLogMockMvc.perform(post("/api/hrEmpChildrenInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfoLog)))
                .andExpect(status().isBadRequest());

        List<HrEmpChildrenInfoLog> hrEmpChildrenInfoLogs = hrEmpChildrenInfoLogRepository.findAll();
        assertThat(hrEmpChildrenInfoLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpChildrenInfoLogRepository.findAll().size();
        // set the field null
        hrEmpChildrenInfoLog.setDateOfBirth(null);

        // Create the HrEmpChildrenInfoLog, which fails.

        restHrEmpChildrenInfoLogMockMvc.perform(post("/api/hrEmpChildrenInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfoLog)))
                .andExpect(status().isBadRequest());

        List<HrEmpChildrenInfoLog> hrEmpChildrenInfoLogs = hrEmpChildrenInfoLogRepository.findAll();
        assertThat(hrEmpChildrenInfoLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpChildrenInfoLogRepository.findAll().size();
        // set the field null
        hrEmpChildrenInfoLog.setGender(null);

        // Create the HrEmpChildrenInfoLog, which fails.

        restHrEmpChildrenInfoLogMockMvc.perform(post("/api/hrEmpChildrenInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfoLog)))
                .andExpect(status().isBadRequest());

        List<HrEmpChildrenInfoLog> hrEmpChildrenInfoLogs = hrEmpChildrenInfoLogRepository.findAll();
        assertThat(hrEmpChildrenInfoLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpChildrenInfoLogRepository.findAll().size();
        // set the field null
        hrEmpChildrenInfoLog.setActiveStatus(null);

        // Create the HrEmpChildrenInfoLog, which fails.

        restHrEmpChildrenInfoLogMockMvc.perform(post("/api/hrEmpChildrenInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfoLog)))
                .andExpect(status().isBadRequest());

        List<HrEmpChildrenInfoLog> hrEmpChildrenInfoLogs = hrEmpChildrenInfoLogRepository.findAll();
        assertThat(hrEmpChildrenInfoLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpChildrenInfoLogs() throws Exception {
        // Initialize the database
        hrEmpChildrenInfoLogRepository.saveAndFlush(hrEmpChildrenInfoLog);

        // Get all the hrEmpChildrenInfoLogs
        restHrEmpChildrenInfoLogMockMvc.perform(get("/api/hrEmpChildrenInfoLogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpChildrenInfoLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].childrenName").value(hasItem(DEFAULT_CHILDREN_NAME.toString())))
                .andExpect(jsonPath("$.[*].childrenNameBn").value(hasItem(DEFAULT_CHILDREN_NAME_BN.toString())))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
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
    public void getHrEmpChildrenInfoLog() throws Exception {
        // Initialize the database
        hrEmpChildrenInfoLogRepository.saveAndFlush(hrEmpChildrenInfoLog);

        // Get the hrEmpChildrenInfoLog
        restHrEmpChildrenInfoLogMockMvc.perform(get("/api/hrEmpChildrenInfoLogs/{id}", hrEmpChildrenInfoLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpChildrenInfoLog.getId().intValue()))
            .andExpect(jsonPath("$.childrenName").value(DEFAULT_CHILDREN_NAME.toString()))
            .andExpect(jsonPath("$.childrenNameBn").value(DEFAULT_CHILDREN_NAME_BN.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
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
    public void getNonExistingHrEmpChildrenInfoLog() throws Exception {
        // Get the hrEmpChildrenInfoLog
        restHrEmpChildrenInfoLogMockMvc.perform(get("/api/hrEmpChildrenInfoLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpChildrenInfoLog() throws Exception {
        // Initialize the database
        hrEmpChildrenInfoLogRepository.saveAndFlush(hrEmpChildrenInfoLog);

		int databaseSizeBeforeUpdate = hrEmpChildrenInfoLogRepository.findAll().size();

        // Update the hrEmpChildrenInfoLog
        hrEmpChildrenInfoLog.setChildrenName(UPDATED_CHILDREN_NAME);
        hrEmpChildrenInfoLog.setChildrenNameBn(UPDATED_CHILDREN_NAME_BN);
        hrEmpChildrenInfoLog.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        hrEmpChildrenInfoLog.setGender(UPDATED_GENDER);
        hrEmpChildrenInfoLog.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpChildrenInfoLog.setParentId(UPDATED_PARENT_ID);
        hrEmpChildrenInfoLog.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpChildrenInfoLog.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpChildrenInfoLog.setCreateBy(UPDATED_CREATE_BY);
        hrEmpChildrenInfoLog.setActionDate(UPDATED_ACTION_DATE);
        hrEmpChildrenInfoLog.setActionBy(UPDATED_ACTION_BY);
        hrEmpChildrenInfoLog.setActionComments(UPDATED_ACTION_COMMENTS);

        restHrEmpChildrenInfoLogMockMvc.perform(put("/api/hrEmpChildrenInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfoLog)))
                .andExpect(status().isOk());

        // Validate the HrEmpChildrenInfoLog in the database
        List<HrEmpChildrenInfoLog> hrEmpChildrenInfoLogs = hrEmpChildrenInfoLogRepository.findAll();
        assertThat(hrEmpChildrenInfoLogs).hasSize(databaseSizeBeforeUpdate);
        HrEmpChildrenInfoLog testHrEmpChildrenInfoLog = hrEmpChildrenInfoLogs.get(hrEmpChildrenInfoLogs.size() - 1);
        assertThat(testHrEmpChildrenInfoLog.getChildrenName()).isEqualTo(UPDATED_CHILDREN_NAME);
        assertThat(testHrEmpChildrenInfoLog.getChildrenNameBn()).isEqualTo(UPDATED_CHILDREN_NAME_BN);
        assertThat(testHrEmpChildrenInfoLog.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testHrEmpChildrenInfoLog.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testHrEmpChildrenInfoLog.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpChildrenInfoLog.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testHrEmpChildrenInfoLog.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpChildrenInfoLog.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpChildrenInfoLog.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpChildrenInfoLog.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testHrEmpChildrenInfoLog.getActionBy()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testHrEmpChildrenInfoLog.getActionComments()).isEqualTo(UPDATED_ACTION_COMMENTS);
    }

    @Test
    @Transactional
    public void deleteHrEmpChildrenInfoLog() throws Exception {
        // Initialize the database
        hrEmpChildrenInfoLogRepository.saveAndFlush(hrEmpChildrenInfoLog);

		int databaseSizeBeforeDelete = hrEmpChildrenInfoLogRepository.findAll().size();

        // Get the hrEmpChildrenInfoLog
        restHrEmpChildrenInfoLogMockMvc.perform(delete("/api/hrEmpChildrenInfoLogs/{id}", hrEmpChildrenInfoLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpChildrenInfoLog> hrEmpChildrenInfoLogs = hrEmpChildrenInfoLogRepository.findAll();
        assertThat(hrEmpChildrenInfoLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
