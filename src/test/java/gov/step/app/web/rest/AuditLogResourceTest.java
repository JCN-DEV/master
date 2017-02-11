package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AuditLog;
import gov.step.app.repository.AuditLogRepository;
import gov.step.app.repository.search.AuditLogSearchRepository;

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
 * Test class for the AuditLogResource REST controller.
 *
 * @see AuditLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AuditLogResourceTest {


    private static final LocalDate DEFAULT_EVENT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_EVENT = "AAAAA";
    private static final String UPDATED_EVENT = "BBBBB";
    private static final String DEFAULT_EVENT_TYPE = "AAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";
    private static final String DEFAULT_USER_ID = "AAAAA";
    private static final String UPDATED_USER_ID = "BBBBB";
    private static final String DEFAULT_USER_IP_ADDRESS = "AAAAA";
    private static final String UPDATED_USER_IP_ADDRESS = "BBBBB";
    private static final String DEFAULT_USER_MAC_ADDRESS = "AAAAA";
    private static final String UPDATED_USER_MAC_ADDRESS = "BBBBB";
    private static final String DEFAULT_DEVICE_NAME = "AAAAA";
    private static final String UPDATED_DEVICE_NAME = "BBBBB";
    private static final String DEFAULT_STATUS_ACTION = "AAAAA";
    private static final String UPDATED_STATUS_ACTION = "BBBBB";
    private static final String DEFAULT_TYPES = "AAAAA";
    private static final String UPDATED_TYPES = "BBBBB";
    private static final String DEFAULT_USER_BROWSER = "AAAAA";
    private static final String UPDATED_USER_BROWSER = "BBBBB";

    @Inject
    private AuditLogRepository auditLogRepository;

    @Inject
    private AuditLogSearchRepository auditLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAuditLogMockMvc;

    private AuditLog auditLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuditLogResource auditLogResource = new AuditLogResource();
        ReflectionTestUtils.setField(auditLogResource, "auditLogRepository", auditLogRepository);
        ReflectionTestUtils.setField(auditLogResource, "auditLogSearchRepository", auditLogSearchRepository);
        this.restAuditLogMockMvc = MockMvcBuilders.standaloneSetup(auditLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        auditLog = new AuditLog();
        auditLog.setEventTime(DEFAULT_EVENT_TIME);
        auditLog.setEvent(DEFAULT_EVENT);
        auditLog.setEventType(DEFAULT_EVENT_TYPE);
        auditLog.setStatus(DEFAULT_STATUS);
        auditLog.setCreateBy(DEFAULT_CREATE_BY);
        auditLog.setCreateDate(DEFAULT_CREATE_DATE);
        auditLog.setUpdateBy(DEFAULT_UPDATE_BY);
        auditLog.setUpdateDate(DEFAULT_UPDATE_DATE);
        auditLog.setRemarks(DEFAULT_REMARKS);
        auditLog.setUserId(DEFAULT_USER_ID);
        auditLog.setUserIpAddress(DEFAULT_USER_IP_ADDRESS);
        auditLog.setUserMacAddress(DEFAULT_USER_MAC_ADDRESS);
        auditLog.setDeviceName(DEFAULT_DEVICE_NAME);
        auditLog.setStatusAction(DEFAULT_STATUS_ACTION);
        auditLog.setTypes(DEFAULT_TYPES);
        auditLog.setUserBrowser(DEFAULT_USER_BROWSER);
    }

    @Test
    @Transactional
    public void createAuditLog() throws Exception {
        int databaseSizeBeforeCreate = auditLogRepository.findAll().size();

        // Create the AuditLog

        restAuditLogMockMvc.perform(post("/api/auditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(auditLog)))
                .andExpect(status().isCreated());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        assertThat(auditLogs).hasSize(databaseSizeBeforeCreate + 1);
        AuditLog testAuditLog = auditLogs.get(auditLogs.size() - 1);
        assertThat(testAuditLog.getEventTime()).isEqualTo(DEFAULT_EVENT_TIME);
        assertThat(testAuditLog.getEvent()).isEqualTo(DEFAULT_EVENT);
        assertThat(testAuditLog.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testAuditLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAuditLog.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testAuditLog.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testAuditLog.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testAuditLog.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testAuditLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAuditLog.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAuditLog.getUserIpAddress()).isEqualTo(DEFAULT_USER_IP_ADDRESS);
        assertThat(testAuditLog.getUserMacAddress()).isEqualTo(DEFAULT_USER_MAC_ADDRESS);
        assertThat(testAuditLog.getDeviceName()).isEqualTo(DEFAULT_DEVICE_NAME);
        assertThat(testAuditLog.getStatusAction()).isEqualTo(DEFAULT_STATUS_ACTION);
        assertThat(testAuditLog.getTypes()).isEqualTo(DEFAULT_TYPES);
        assertThat(testAuditLog.getUserBrowser()).isEqualTo(DEFAULT_USER_BROWSER);
    }

    @Test
    @Transactional
    public void getAllAuditLogs() throws Exception {
        // Initialize the database
        auditLogRepository.saveAndFlush(auditLog);

        // Get all the auditLogs
        restAuditLogMockMvc.perform(get("/api/auditLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(auditLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].eventTime").value(hasItem(DEFAULT_EVENT_TIME.toString())))
                .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT.toString())))
                .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
                .andExpect(jsonPath("$.[*].userIpAddress").value(hasItem(DEFAULT_USER_IP_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].userMacAddress").value(hasItem(DEFAULT_USER_MAC_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())))
                .andExpect(jsonPath("$.[*].statusAction").value(hasItem(DEFAULT_STATUS_ACTION.toString())))
                .andExpect(jsonPath("$.[*].types").value(hasItem(DEFAULT_TYPES.toString())))
                .andExpect(jsonPath("$.[*].userBrowser").value(hasItem(DEFAULT_USER_BROWSER.toString())));
    }

    @Test
    @Transactional
    public void getAuditLog() throws Exception {
        // Initialize the database
        auditLogRepository.saveAndFlush(auditLog);

        // Get the auditLog
        restAuditLogMockMvc.perform(get("/api/auditLogs/{id}", auditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(auditLog.getId().intValue()))
            .andExpect(jsonPath("$.eventTime").value(DEFAULT_EVENT_TIME.toString()))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT.toString()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.userIpAddress").value(DEFAULT_USER_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.userMacAddress").value(DEFAULT_USER_MAC_ADDRESS.toString()))
            .andExpect(jsonPath("$.deviceName").value(DEFAULT_DEVICE_NAME.toString()))
            .andExpect(jsonPath("$.statusAction").value(DEFAULT_STATUS_ACTION.toString()))
            .andExpect(jsonPath("$.types").value(DEFAULT_TYPES.toString()))
            .andExpect(jsonPath("$.userBrowser").value(DEFAULT_USER_BROWSER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuditLog() throws Exception {
        // Get the auditLog
        restAuditLogMockMvc.perform(get("/api/auditLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditLog() throws Exception {
        // Initialize the database
        auditLogRepository.saveAndFlush(auditLog);

		int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();

        // Update the auditLog
        auditLog.setEventTime(UPDATED_EVENT_TIME);
        auditLog.setEvent(UPDATED_EVENT);
        auditLog.setEventType(UPDATED_EVENT_TYPE);
        auditLog.setStatus(UPDATED_STATUS);
        auditLog.setCreateBy(UPDATED_CREATE_BY);
        auditLog.setCreateDate(UPDATED_CREATE_DATE);
        auditLog.setUpdateBy(UPDATED_UPDATE_BY);
        auditLog.setUpdateDate(UPDATED_UPDATE_DATE);
        auditLog.setRemarks(UPDATED_REMARKS);
        auditLog.setUserId(UPDATED_USER_ID);
        auditLog.setUserIpAddress(UPDATED_USER_IP_ADDRESS);
        auditLog.setUserMacAddress(UPDATED_USER_MAC_ADDRESS);
        auditLog.setDeviceName(UPDATED_DEVICE_NAME);
        auditLog.setStatusAction(UPDATED_STATUS_ACTION);
        auditLog.setTypes(UPDATED_TYPES);
        auditLog.setUserBrowser(UPDATED_USER_BROWSER);

        restAuditLogMockMvc.perform(put("/api/auditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(auditLog)))
                .andExpect(status().isOk());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        assertThat(auditLogs).hasSize(databaseSizeBeforeUpdate);
        AuditLog testAuditLog = auditLogs.get(auditLogs.size() - 1);
        assertThat(testAuditLog.getEventTime()).isEqualTo(UPDATED_EVENT_TIME);
        assertThat(testAuditLog.getEvent()).isEqualTo(UPDATED_EVENT);
        assertThat(testAuditLog.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testAuditLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuditLog.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testAuditLog.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testAuditLog.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testAuditLog.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testAuditLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAuditLog.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAuditLog.getUserIpAddress()).isEqualTo(UPDATED_USER_IP_ADDRESS);
        assertThat(testAuditLog.getUserMacAddress()).isEqualTo(UPDATED_USER_MAC_ADDRESS);
        assertThat(testAuditLog.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);
        assertThat(testAuditLog.getStatusAction()).isEqualTo(UPDATED_STATUS_ACTION);
        assertThat(testAuditLog.getTypes()).isEqualTo(UPDATED_TYPES);
        assertThat(testAuditLog.getUserBrowser()).isEqualTo(UPDATED_USER_BROWSER);
    }

    @Test
    @Transactional
    public void deleteAuditLog() throws Exception {
        // Initialize the database
        auditLogRepository.saveAndFlush(auditLog);

		int databaseSizeBeforeDelete = auditLogRepository.findAll().size();

        // Get the auditLog
        restAuditLogMockMvc.perform(delete("/api/auditLogs/{id}", auditLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        assertThat(auditLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
