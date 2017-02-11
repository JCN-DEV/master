package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpAddressInfoLog;
import gov.step.app.domain.enumeration.addressTypes;
import gov.step.app.repository.HrEmpAddressInfoLogRepository;
import gov.step.app.repository.search.HrEmpAddressInfoLogSearchRepository;
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
 * Test class for the HrEmpAddressInfoLogResource REST controller.
 *
 * @see HrEmpAddressInfoLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpAddressInfoLogResourceIntTest {



    private static final addressTypes DEFAULT_ADDRESS_TYPE = addressTypes.Permanent;
    private static final addressTypes UPDATED_ADDRESS_TYPE = addressTypes.Present;
    private static final String DEFAULT_HOUSE_NUMBER = "AAAAA";
    private static final String UPDATED_HOUSE_NUMBER = "BBBBB";
    private static final String DEFAULT_ROAD_NUMBER = "AAAAA";
    private static final String UPDATED_ROAD_NUMBER = "BBBBB";
    private static final String DEFAULT_VILLAGE_NAME = "AAAAA";
    private static final String UPDATED_VILLAGE_NAME = "BBBBB";
    private static final String DEFAULT_POST_OFFICE = "AAAAA";
    private static final String UPDATED_POST_OFFICE = "BBBBB";
    private static final String DEFAULT_POLICE_STATION = "AAAAA";
    private static final String UPDATED_POLICE_STATION = "BBBBB";
    private static final String DEFAULT_DISTRICT = "AAAAA";
    private static final String UPDATED_DISTRICT = "BBBBB";
    private static final String DEFAULT_CONTACT_NUMBER = "AAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBB";

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
    private HrEmpAddressInfoLogRepository hrEmpAddressInfoLogRepository;

    @Inject
    private HrEmpAddressInfoLogSearchRepository hrEmpAddressInfoLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpAddressInfoLogMockMvc;

    private HrEmpAddressInfoLog hrEmpAddressInfoLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpAddressInfoLogResource hrEmpAddressInfoLogResource = new HrEmpAddressInfoLogResource();
        ReflectionTestUtils.setField(hrEmpAddressInfoLogResource, "hrEmpAddressInfoLogSearchRepository", hrEmpAddressInfoLogSearchRepository);
        ReflectionTestUtils.setField(hrEmpAddressInfoLogResource, "hrEmpAddressInfoLogRepository", hrEmpAddressInfoLogRepository);
        this.restHrEmpAddressInfoLogMockMvc = MockMvcBuilders.standaloneSetup(hrEmpAddressInfoLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpAddressInfoLog = new HrEmpAddressInfoLog();
        hrEmpAddressInfoLog.setAddressType(DEFAULT_ADDRESS_TYPE);
        hrEmpAddressInfoLog.setHouseNumber(DEFAULT_HOUSE_NUMBER);
        hrEmpAddressInfoLog.setRoadNumber(DEFAULT_ROAD_NUMBER);
        hrEmpAddressInfoLog.setVillageName(DEFAULT_VILLAGE_NAME);
        hrEmpAddressInfoLog.setPostOffice(DEFAULT_POST_OFFICE);
        hrEmpAddressInfoLog.setContactNumber(DEFAULT_CONTACT_NUMBER);
        hrEmpAddressInfoLog.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpAddressInfoLog.setParentId(DEFAULT_PARENT_ID);
        hrEmpAddressInfoLog.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpAddressInfoLog.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpAddressInfoLog.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpAddressInfoLog.setActionDate(DEFAULT_ACTION_DATE);
        hrEmpAddressInfoLog.setActionBy(DEFAULT_ACTION_BY);
        hrEmpAddressInfoLog.setActionComments(DEFAULT_ACTION_COMMENTS);
    }

    @Test
    @Transactional
    public void createHrEmpAddressInfoLog() throws Exception {
        int databaseSizeBeforeCreate = hrEmpAddressInfoLogRepository.findAll().size();

        // Create the HrEmpAddressInfoLog

        restHrEmpAddressInfoLogMockMvc.perform(post("/api/hrEmpAddressInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAddressInfoLog)))
                .andExpect(status().isCreated());

        // Validate the HrEmpAddressInfoLog in the database
        List<HrEmpAddressInfoLog> hrEmpAddressInfoLogs = hrEmpAddressInfoLogRepository.findAll();
        assertThat(hrEmpAddressInfoLogs).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpAddressInfoLog testHrEmpAddressInfoLog = hrEmpAddressInfoLogs.get(hrEmpAddressInfoLogs.size() - 1);
        assertThat(testHrEmpAddressInfoLog.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
        assertThat(testHrEmpAddressInfoLog.getHouseNumber()).isEqualTo(DEFAULT_HOUSE_NUMBER);
        assertThat(testHrEmpAddressInfoLog.getRoadNumber()).isEqualTo(DEFAULT_ROAD_NUMBER);
        assertThat(testHrEmpAddressInfoLog.getVillageName()).isEqualTo(DEFAULT_VILLAGE_NAME);
        assertThat(testHrEmpAddressInfoLog.getPostOffice()).isEqualTo(DEFAULT_POST_OFFICE);
        assertThat(testHrEmpAddressInfoLog.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testHrEmpAddressInfoLog.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpAddressInfoLog.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testHrEmpAddressInfoLog.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpAddressInfoLog.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpAddressInfoLog.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpAddressInfoLog.getActionDate()).isEqualTo(DEFAULT_ACTION_DATE);
        assertThat(testHrEmpAddressInfoLog.getActionBy()).isEqualTo(DEFAULT_ACTION_BY);
        assertThat(testHrEmpAddressInfoLog.getActionComments()).isEqualTo(DEFAULT_ACTION_COMMENTS);
    }

    @Test
    @Transactional
    public void checkAddressTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAddressInfoLogRepository.findAll().size();
        // set the field null
        hrEmpAddressInfoLog.setAddressType(null);

        // Create the HrEmpAddressInfoLog, which fails.

        restHrEmpAddressInfoLogMockMvc.perform(post("/api/hrEmpAddressInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAddressInfoLog)))
                .andExpect(status().isBadRequest());

        List<HrEmpAddressInfoLog> hrEmpAddressInfoLogs = hrEmpAddressInfoLogRepository.findAll();
        assertThat(hrEmpAddressInfoLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAddressInfoLogRepository.findAll().size();
        // set the field null
        hrEmpAddressInfoLog.setActiveStatus(null);

        // Create the HrEmpAddressInfoLog, which fails.

        restHrEmpAddressInfoLogMockMvc.perform(post("/api/hrEmpAddressInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAddressInfoLog)))
                .andExpect(status().isBadRequest());

        List<HrEmpAddressInfoLog> hrEmpAddressInfoLogs = hrEmpAddressInfoLogRepository.findAll();
        assertThat(hrEmpAddressInfoLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpAddressInfoLogs() throws Exception {
        // Initialize the database
        hrEmpAddressInfoLogRepository.saveAndFlush(hrEmpAddressInfoLog);

        // Get all the hrEmpAddressInfoLogs
        restHrEmpAddressInfoLogMockMvc.perform(get("/api/hrEmpAddressInfoLogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpAddressInfoLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
                .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].roadNumber").value(hasItem(DEFAULT_ROAD_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME.toString())))
                .andExpect(jsonPath("$.[*].postOffice").value(hasItem(DEFAULT_POST_OFFICE.toString())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
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
    public void getHrEmpAddressInfoLog() throws Exception {
        // Initialize the database
        hrEmpAddressInfoLogRepository.saveAndFlush(hrEmpAddressInfoLog);

        // Get the hrEmpAddressInfoLog
        restHrEmpAddressInfoLogMockMvc.perform(get("/api/hrEmpAddressInfoLogs/{id}", hrEmpAddressInfoLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpAddressInfoLog.getId().intValue()))
            .andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER.toString()))
            .andExpect(jsonPath("$.roadNumber").value(DEFAULT_ROAD_NUMBER.toString()))
            .andExpect(jsonPath("$.villageName").value(DEFAULT_VILLAGE_NAME.toString()))
            .andExpect(jsonPath("$.postOffice").value(DEFAULT_POST_OFFICE.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
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
    public void getNonExistingHrEmpAddressInfoLog() throws Exception {
        // Get the hrEmpAddressInfoLog
        restHrEmpAddressInfoLogMockMvc.perform(get("/api/hrEmpAddressInfoLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpAddressInfoLog() throws Exception {
        // Initialize the database
        hrEmpAddressInfoLogRepository.saveAndFlush(hrEmpAddressInfoLog);

		int databaseSizeBeforeUpdate = hrEmpAddressInfoLogRepository.findAll().size();

        // Update the hrEmpAddressInfoLog
        hrEmpAddressInfoLog.setAddressType(UPDATED_ADDRESS_TYPE);
        hrEmpAddressInfoLog.setHouseNumber(UPDATED_HOUSE_NUMBER);
        hrEmpAddressInfoLog.setRoadNumber(UPDATED_ROAD_NUMBER);
        hrEmpAddressInfoLog.setVillageName(UPDATED_VILLAGE_NAME);
        hrEmpAddressInfoLog.setPostOffice(UPDATED_POST_OFFICE);
        hrEmpAddressInfoLog.setContactNumber(UPDATED_CONTACT_NUMBER);
        hrEmpAddressInfoLog.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpAddressInfoLog.setParentId(UPDATED_PARENT_ID);
        hrEmpAddressInfoLog.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpAddressInfoLog.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpAddressInfoLog.setCreateBy(UPDATED_CREATE_BY);
        hrEmpAddressInfoLog.setActionDate(UPDATED_ACTION_DATE);
        hrEmpAddressInfoLog.setActionBy(UPDATED_ACTION_BY);
        hrEmpAddressInfoLog.setActionComments(UPDATED_ACTION_COMMENTS);

        restHrEmpAddressInfoLogMockMvc.perform(put("/api/hrEmpAddressInfoLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAddressInfoLog)))
                .andExpect(status().isOk());

        // Validate the HrEmpAddressInfoLog in the database
        List<HrEmpAddressInfoLog> hrEmpAddressInfoLogs = hrEmpAddressInfoLogRepository.findAll();
        assertThat(hrEmpAddressInfoLogs).hasSize(databaseSizeBeforeUpdate);
        HrEmpAddressInfoLog testHrEmpAddressInfoLog = hrEmpAddressInfoLogs.get(hrEmpAddressInfoLogs.size() - 1);
        assertThat(testHrEmpAddressInfoLog.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testHrEmpAddressInfoLog.getHouseNumber()).isEqualTo(UPDATED_HOUSE_NUMBER);
        assertThat(testHrEmpAddressInfoLog.getRoadNumber()).isEqualTo(UPDATED_ROAD_NUMBER);
        assertThat(testHrEmpAddressInfoLog.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testHrEmpAddressInfoLog.getPostOffice()).isEqualTo(UPDATED_POST_OFFICE);
        assertThat(testHrEmpAddressInfoLog.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testHrEmpAddressInfoLog.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpAddressInfoLog.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testHrEmpAddressInfoLog.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpAddressInfoLog.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpAddressInfoLog.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpAddressInfoLog.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testHrEmpAddressInfoLog.getActionBy()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testHrEmpAddressInfoLog.getActionComments()).isEqualTo(UPDATED_ACTION_COMMENTS);
    }

    @Test
    @Transactional
    public void deleteHrEmpAddressInfoLog() throws Exception {
        // Initialize the database
        hrEmpAddressInfoLogRepository.saveAndFlush(hrEmpAddressInfoLog);

		int databaseSizeBeforeDelete = hrEmpAddressInfoLogRepository.findAll().size();

        // Get the hrEmpAddressInfoLog
        restHrEmpAddressInfoLogMockMvc.perform(delete("/api/hrEmpAddressInfoLogs/{id}", hrEmpAddressInfoLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpAddressInfoLog> hrEmpAddressInfoLogs = hrEmpAddressInfoLogRepository.findAll();
        assertThat(hrEmpAddressInfoLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
