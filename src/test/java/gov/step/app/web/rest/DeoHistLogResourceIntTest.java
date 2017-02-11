package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DeoHistLog;
import gov.step.app.repository.DeoHistLogRepository;
import gov.step.app.repository.search.DeoHistLogSearchRepository;

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
 * Test class for the DeoHistLogResource REST controller.
 *
 * @see DeoHistLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeoHistLogResourceIntTest {


    private static final LocalDate DEFAULT_DATE_CRATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CRATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    @Inject
    private DeoHistLogRepository deoHistLogRepository;

    @Inject
    private DeoHistLogSearchRepository deoHistLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeoHistLogMockMvc;

    private DeoHistLog deoHistLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeoHistLogResource deoHistLogResource = new DeoHistLogResource();
        ReflectionTestUtils.setField(deoHistLogResource, "deoHistLogRepository", deoHistLogRepository);
        ReflectionTestUtils.setField(deoHistLogResource, "deoHistLogSearchRepository", deoHistLogSearchRepository);
        this.restDeoHistLogMockMvc = MockMvcBuilders.standaloneSetup(deoHistLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        deoHistLog = new DeoHistLog();
        deoHistLog.setDateCrated(DEFAULT_DATE_CRATED);
        deoHistLog.setDateModified(DEFAULT_DATE_MODIFIED);
        deoHistLog.setStatus(DEFAULT_STATUS);
        deoHistLog.setActivated(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void createDeoHistLog() throws Exception {
        int databaseSizeBeforeCreate = deoHistLogRepository.findAll().size();

        // Create the DeoHistLog

        restDeoHistLogMockMvc.perform(post("/api/deoHistLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deoHistLog)))
                .andExpect(status().isCreated());

        // Validate the DeoHistLog in the database
        List<DeoHistLog> deoHistLogs = deoHistLogRepository.findAll();
        assertThat(deoHistLogs).hasSize(databaseSizeBeforeCreate + 1);
        DeoHistLog testDeoHistLog = deoHistLogs.get(deoHistLogs.size() - 1);
        assertThat(testDeoHistLog.getDateCrated()).isEqualTo(DEFAULT_DATE_CRATED);
        assertThat(testDeoHistLog.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testDeoHistLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDeoHistLog.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllDeoHistLogs() throws Exception {
        // Initialize the database
        deoHistLogRepository.saveAndFlush(deoHistLog);

        // Get all the deoHistLogs
        restDeoHistLogMockMvc.perform(get("/api/deoHistLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(deoHistLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateCrated").value(hasItem(DEFAULT_DATE_CRATED.toString())))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
    }

    @Test
    @Transactional
    public void getDeoHistLog() throws Exception {
        // Initialize the database
        deoHistLogRepository.saveAndFlush(deoHistLog);

        // Get the deoHistLog
        restDeoHistLogMockMvc.perform(get("/api/deoHistLogs/{id}", deoHistLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(deoHistLog.getId().intValue()))
            .andExpect(jsonPath("$.dateCrated").value(DEFAULT_DATE_CRATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDeoHistLog() throws Exception {
        // Get the deoHistLog
        restDeoHistLogMockMvc.perform(get("/api/deoHistLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeoHistLog() throws Exception {
        // Initialize the database
        deoHistLogRepository.saveAndFlush(deoHistLog);

		int databaseSizeBeforeUpdate = deoHistLogRepository.findAll().size();

        // Update the deoHistLog
        deoHistLog.setDateCrated(UPDATED_DATE_CRATED);
        deoHistLog.setDateModified(UPDATED_DATE_MODIFIED);
        deoHistLog.setStatus(UPDATED_STATUS);
        deoHistLog.setActivated(UPDATED_ACTIVATED);

        restDeoHistLogMockMvc.perform(put("/api/deoHistLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deoHistLog)))
                .andExpect(status().isOk());

        // Validate the DeoHistLog in the database
        List<DeoHistLog> deoHistLogs = deoHistLogRepository.findAll();
        assertThat(deoHistLogs).hasSize(databaseSizeBeforeUpdate);
        DeoHistLog testDeoHistLog = deoHistLogs.get(deoHistLogs.size() - 1);
        assertThat(testDeoHistLog.getDateCrated()).isEqualTo(UPDATED_DATE_CRATED);
        assertThat(testDeoHistLog.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testDeoHistLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeoHistLog.getActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void deleteDeoHistLog() throws Exception {
        // Initialize the database
        deoHistLogRepository.saveAndFlush(deoHistLog);

		int databaseSizeBeforeDelete = deoHistLogRepository.findAll().size();

        // Get the deoHistLog
        restDeoHistLogMockMvc.perform(delete("/api/deoHistLogs/{id}", deoHistLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DeoHistLog> deoHistLogs = deoHistLogRepository.findAll();
        assertThat(deoHistLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
