package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TimeScaleApplicationEditLog;
import gov.step.app.repository.TimeScaleApplicationEditLogRepository;
import gov.step.app.repository.search.TimeScaleApplicationEditLogSearchRepository;

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
 * Test class for the TimeScaleApplicationEditLogResource REST controller.
 *
 * @see TimeScaleApplicationEditLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TimeScaleApplicationEditLogResourceIntTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private TimeScaleApplicationEditLogRepository timeScaleApplicationEditLogRepository;

    @Inject
    private TimeScaleApplicationEditLogSearchRepository timeScaleApplicationEditLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimeScaleApplicationEditLogMockMvc;

    private TimeScaleApplicationEditLog timeScaleApplicationEditLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeScaleApplicationEditLogResource timeScaleApplicationEditLogResource = new TimeScaleApplicationEditLogResource();
        ReflectionTestUtils.setField(timeScaleApplicationEditLogResource, "timeScaleApplicationEditLogRepository", timeScaleApplicationEditLogRepository);
        ReflectionTestUtils.setField(timeScaleApplicationEditLogResource, "timeScaleApplicationEditLogSearchRepository", timeScaleApplicationEditLogSearchRepository);
        this.restTimeScaleApplicationEditLogMockMvc = MockMvcBuilders.standaloneSetup(timeScaleApplicationEditLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timeScaleApplicationEditLog = new TimeScaleApplicationEditLog();
        timeScaleApplicationEditLog.setStatus(DEFAULT_STATUS);
        timeScaleApplicationEditLog.setRemarks(DEFAULT_REMARKS);
        timeScaleApplicationEditLog.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createTimeScaleApplicationEditLog() throws Exception {
        int databaseSizeBeforeCreate = timeScaleApplicationEditLogRepository.findAll().size();

        // Create the TimeScaleApplicationEditLog

        restTimeScaleApplicationEditLogMockMvc.perform(post("/api/timeScaleApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeScaleApplicationEditLog)))
                .andExpect(status().isCreated());

        // Validate the TimeScaleApplicationEditLog in the database
        List<TimeScaleApplicationEditLog> timeScaleApplicationEditLogs = timeScaleApplicationEditLogRepository.findAll();
        assertThat(timeScaleApplicationEditLogs).hasSize(databaseSizeBeforeCreate + 1);
        TimeScaleApplicationEditLog testTimeScaleApplicationEditLog = timeScaleApplicationEditLogs.get(timeScaleApplicationEditLogs.size() - 1);
        assertThat(testTimeScaleApplicationEditLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTimeScaleApplicationEditLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testTimeScaleApplicationEditLog.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllTimeScaleApplicationEditLogs() throws Exception {
        // Initialize the database
        timeScaleApplicationEditLogRepository.saveAndFlush(timeScaleApplicationEditLog);

        // Get all the timeScaleApplicationEditLogs
        restTimeScaleApplicationEditLogMockMvc.perform(get("/api/timeScaleApplicationEditLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timeScaleApplicationEditLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getTimeScaleApplicationEditLog() throws Exception {
        // Initialize the database
        timeScaleApplicationEditLogRepository.saveAndFlush(timeScaleApplicationEditLog);

        // Get the timeScaleApplicationEditLog
        restTimeScaleApplicationEditLogMockMvc.perform(get("/api/timeScaleApplicationEditLogs/{id}", timeScaleApplicationEditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(timeScaleApplicationEditLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeScaleApplicationEditLog() throws Exception {
        // Get the timeScaleApplicationEditLog
        restTimeScaleApplicationEditLogMockMvc.perform(get("/api/timeScaleApplicationEditLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeScaleApplicationEditLog() throws Exception {
        // Initialize the database
        timeScaleApplicationEditLogRepository.saveAndFlush(timeScaleApplicationEditLog);

		int databaseSizeBeforeUpdate = timeScaleApplicationEditLogRepository.findAll().size();

        // Update the timeScaleApplicationEditLog
        timeScaleApplicationEditLog.setStatus(UPDATED_STATUS);
        timeScaleApplicationEditLog.setRemarks(UPDATED_REMARKS);
        timeScaleApplicationEditLog.setDate(UPDATED_DATE);

        restTimeScaleApplicationEditLogMockMvc.perform(put("/api/timeScaleApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeScaleApplicationEditLog)))
                .andExpect(status().isOk());

        // Validate the TimeScaleApplicationEditLog in the database
        List<TimeScaleApplicationEditLog> timeScaleApplicationEditLogs = timeScaleApplicationEditLogRepository.findAll();
        assertThat(timeScaleApplicationEditLogs).hasSize(databaseSizeBeforeUpdate);
        TimeScaleApplicationEditLog testTimeScaleApplicationEditLog = timeScaleApplicationEditLogs.get(timeScaleApplicationEditLogs.size() - 1);
        assertThat(testTimeScaleApplicationEditLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTimeScaleApplicationEditLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testTimeScaleApplicationEditLog.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteTimeScaleApplicationEditLog() throws Exception {
        // Initialize the database
        timeScaleApplicationEditLogRepository.saveAndFlush(timeScaleApplicationEditLog);

		int databaseSizeBeforeDelete = timeScaleApplicationEditLogRepository.findAll().size();

        // Get the timeScaleApplicationEditLog
        restTimeScaleApplicationEditLogMockMvc.perform(delete("/api/timeScaleApplicationEditLogs/{id}", timeScaleApplicationEditLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeScaleApplicationEditLog> timeScaleApplicationEditLogs = timeScaleApplicationEditLogRepository.findAll();
        assertThat(timeScaleApplicationEditLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
