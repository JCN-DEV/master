package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.UmracRightsLog;
import gov.step.app.repository.UmracRightsLogRepository;
import gov.step.app.repository.search.UmracRightsLogSearchRepository;

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
 * Test class for the UmracRightsLogResource REST controller.
 *
 * @see UmracRightsLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UmracRightsLogResourceTest {

    private static final String DEFAULT_RIGHT_ID = "AAAAA";
    private static final String UPDATED_RIGHT_ID = "BBBBB";

    private static final Long DEFAULT_ROLE_ID = 1L;
    private static final Long UPDATED_ROLE_ID = 2L;

    private static final Long DEFAULT_MODULE_ID = 1L;
    private static final Long UPDATED_MODULE_ID = 2L;

    private static final Long DEFAULT_SUB_MODULE_ID = 1L;
    private static final Long UPDATED_SUB_MODULE_ID = 2L;
    private static final String DEFAULT_RIGHTS = "AAAAA";
    private static final String UPDATED_RIGHTS = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_CHANGE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHANGE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CHANGE_BY = 1L;
    private static final Long UPDATED_CHANGE_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final LocalDate DEFAULT_UPDATED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private UmracRightsLogRepository umracRightsLogRepository;

    @Inject
    private UmracRightsLogSearchRepository umracRightsLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmracRightsLogMockMvc;

    private UmracRightsLog umracRightsLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmracRightsLogResource umracRightsLogResource = new UmracRightsLogResource();
        ReflectionTestUtils.setField(umracRightsLogResource, "umracRightsLogRepository", umracRightsLogRepository);
        ReflectionTestUtils.setField(umracRightsLogResource, "umracRightsLogSearchRepository", umracRightsLogSearchRepository);
        this.restUmracRightsLogMockMvc = MockMvcBuilders.standaloneSetup(umracRightsLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umracRightsLog = new UmracRightsLog();
        umracRightsLog.setRightId(DEFAULT_RIGHT_ID);
        umracRightsLog.setRoleId(DEFAULT_ROLE_ID);
        umracRightsLog.setModule_id(DEFAULT_MODULE_ID);
        umracRightsLog.setSubModule_id(DEFAULT_SUB_MODULE_ID);
        umracRightsLog.setRights(DEFAULT_RIGHTS);
        umracRightsLog.setDescription(DEFAULT_DESCRIPTION);
        umracRightsLog.setStatus(DEFAULT_STATUS);
        umracRightsLog.setChangeDate(DEFAULT_CHANGE_DATE);
        umracRightsLog.setChangeBy(DEFAULT_CHANGE_BY);
        umracRightsLog.setUpdatedBy(DEFAULT_UPDATED_BY);
        umracRightsLog.setUpdatedTime(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createUmracRightsLog() throws Exception {
        int databaseSizeBeforeCreate = umracRightsLogRepository.findAll().size();

        // Create the UmracRightsLog

        restUmracRightsLogMockMvc.perform(post("/api/umracRightsLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracRightsLog)))
                .andExpect(status().isCreated());

        // Validate the UmracRightsLog in the database
        List<UmracRightsLog> umracRightsLogs = umracRightsLogRepository.findAll();
        assertThat(umracRightsLogs).hasSize(databaseSizeBeforeCreate + 1);
        UmracRightsLog testUmracRightsLog = umracRightsLogs.get(umracRightsLogs.size() - 1);
        assertThat(testUmracRightsLog.getRightId()).isEqualTo(DEFAULT_RIGHT_ID);
        assertThat(testUmracRightsLog.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testUmracRightsLog.getModule_id()).isEqualTo(DEFAULT_MODULE_ID);
        assertThat(testUmracRightsLog.getSubModule_id()).isEqualTo(DEFAULT_SUB_MODULE_ID);
        assertThat(testUmracRightsLog.getRights()).isEqualTo(DEFAULT_RIGHTS);
        assertThat(testUmracRightsLog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUmracRightsLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUmracRightsLog.getChangeDate()).isEqualTo(DEFAULT_CHANGE_DATE);
        assertThat(testUmracRightsLog.getChangeBy()).isEqualTo(DEFAULT_CHANGE_BY);
        assertThat(testUmracRightsLog.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUmracRightsLog.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllUmracRightsLogs() throws Exception {
        // Initialize the database
        umracRightsLogRepository.saveAndFlush(umracRightsLog);

        // Get all the umracRightsLogs
        restUmracRightsLogMockMvc.perform(get("/api/umracRightsLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umracRightsLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].rightId").value(hasItem(DEFAULT_RIGHT_ID.toString())))
                .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID.intValue())))
                .andExpect(jsonPath("$.[*].module_id").value(hasItem(DEFAULT_MODULE_ID.intValue())))
                .andExpect(jsonPath("$.[*].subModule_id").value(hasItem(DEFAULT_SUB_MODULE_ID.intValue())))
                .andExpect(jsonPath("$.[*].rights").value(hasItem(DEFAULT_RIGHTS.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].changeDate").value(hasItem(DEFAULT_CHANGE_DATE.toString())))
                .andExpect(jsonPath("$.[*].changeBy").value(hasItem(DEFAULT_CHANGE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getUmracRightsLog() throws Exception {
        // Initialize the database
        umracRightsLogRepository.saveAndFlush(umracRightsLog);

        // Get the umracRightsLog
        restUmracRightsLogMockMvc.perform(get("/api/umracRightsLogs/{id}", umracRightsLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umracRightsLog.getId().intValue()))
            .andExpect(jsonPath("$.rightId").value(DEFAULT_RIGHT_ID.toString()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID.intValue()))
            .andExpect(jsonPath("$.module_id").value(DEFAULT_MODULE_ID.intValue()))
            .andExpect(jsonPath("$.subModule_id").value(DEFAULT_SUB_MODULE_ID.intValue()))
            .andExpect(jsonPath("$.rights").value(DEFAULT_RIGHTS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.changeDate").value(DEFAULT_CHANGE_DATE.toString()))
            .andExpect(jsonPath("$.changeBy").value(DEFAULT_CHANGE_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUmracRightsLog() throws Exception {
        // Get the umracRightsLog
        restUmracRightsLogMockMvc.perform(get("/api/umracRightsLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmracRightsLog() throws Exception {
        // Initialize the database
        umracRightsLogRepository.saveAndFlush(umracRightsLog);

		int databaseSizeBeforeUpdate = umracRightsLogRepository.findAll().size();

        // Update the umracRightsLog
        umracRightsLog.setRightId(UPDATED_RIGHT_ID);
        umracRightsLog.setRoleId(UPDATED_ROLE_ID);
        umracRightsLog.setModule_id(UPDATED_MODULE_ID);
        umracRightsLog.setSubModule_id(UPDATED_SUB_MODULE_ID);
        umracRightsLog.setRights(UPDATED_RIGHTS);
        umracRightsLog.setDescription(UPDATED_DESCRIPTION);
        umracRightsLog.setStatus(UPDATED_STATUS);
        umracRightsLog.setChangeDate(UPDATED_CHANGE_DATE);
        umracRightsLog.setChangeBy(UPDATED_CHANGE_BY);
        umracRightsLog.setUpdatedBy(UPDATED_UPDATED_BY);
        umracRightsLog.setUpdatedTime(UPDATED_UPDATED_TIME);

        restUmracRightsLogMockMvc.perform(put("/api/umracRightsLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracRightsLog)))
                .andExpect(status().isOk());

        // Validate the UmracRightsLog in the database
        List<UmracRightsLog> umracRightsLogs = umracRightsLogRepository.findAll();
        assertThat(umracRightsLogs).hasSize(databaseSizeBeforeUpdate);
        UmracRightsLog testUmracRightsLog = umracRightsLogs.get(umracRightsLogs.size() - 1);
        assertThat(testUmracRightsLog.getRightId()).isEqualTo(UPDATED_RIGHT_ID);
        assertThat(testUmracRightsLog.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testUmracRightsLog.getModule_id()).isEqualTo(UPDATED_MODULE_ID);
        assertThat(testUmracRightsLog.getSubModule_id()).isEqualTo(UPDATED_SUB_MODULE_ID);
        assertThat(testUmracRightsLog.getRights()).isEqualTo(UPDATED_RIGHTS);
        assertThat(testUmracRightsLog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUmracRightsLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUmracRightsLog.getChangeDate()).isEqualTo(UPDATED_CHANGE_DATE);
        assertThat(testUmracRightsLog.getChangeBy()).isEqualTo(UPDATED_CHANGE_BY);
        assertThat(testUmracRightsLog.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUmracRightsLog.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteUmracRightsLog() throws Exception {
        // Initialize the database
        umracRightsLogRepository.saveAndFlush(umracRightsLog);

		int databaseSizeBeforeDelete = umracRightsLogRepository.findAll().size();

        // Get the umracRightsLog
        restUmracRightsLogMockMvc.perform(delete("/api/umracRightsLogs/{id}", umracRightsLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmracRightsLog> umracRightsLogs = umracRightsLogRepository.findAll();
        assertThat(umracRightsLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
