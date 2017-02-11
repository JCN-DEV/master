package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TimeScaleApplicationStatusLog;
import gov.step.app.repository.TimeScaleApplicationStatusLogRepository;
import gov.step.app.repository.search.TimeScaleApplicationStatusLogSearchRepository;

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
 * Test class for the TimeScaleApplicationStatusLogResource REST controller.
 *
 * @see TimeScaleApplicationStatusLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TimeScaleApplicationStatusLogResourceIntTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CAUSE = "AAAAA";
    private static final String UPDATED_CAUSE = "BBBBB";

    @Inject
    private TimeScaleApplicationStatusLogRepository timeScaleApplicationStatusLogRepository;

    @Inject
    private TimeScaleApplicationStatusLogSearchRepository timeScaleApplicationStatusLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimeScaleApplicationStatusLogMockMvc;

    private TimeScaleApplicationStatusLog timeScaleApplicationStatusLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeScaleApplicationStatusLogResource timeScaleApplicationStatusLogResource = new TimeScaleApplicationStatusLogResource();
        ReflectionTestUtils.setField(timeScaleApplicationStatusLogResource, "timeScaleApplicationStatusLogRepository", timeScaleApplicationStatusLogRepository);
        ReflectionTestUtils.setField(timeScaleApplicationStatusLogResource, "timeScaleApplicationStatusLogSearchRepository", timeScaleApplicationStatusLogSearchRepository);
        this.restTimeScaleApplicationStatusLogMockMvc = MockMvcBuilders.standaloneSetup(timeScaleApplicationStatusLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timeScaleApplicationStatusLog = new TimeScaleApplicationStatusLog();
        timeScaleApplicationStatusLog.setStatus(DEFAULT_STATUS);
        timeScaleApplicationStatusLog.setRemarks(DEFAULT_REMARKS);
        timeScaleApplicationStatusLog.setFromDate(DEFAULT_FROM_DATE);
        timeScaleApplicationStatusLog.setToDate(DEFAULT_TO_DATE);
        timeScaleApplicationStatusLog.setCause(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void createTimeScaleApplicationStatusLog() throws Exception {
        int databaseSizeBeforeCreate = timeScaleApplicationStatusLogRepository.findAll().size();

        // Create the TimeScaleApplicationStatusLog

        restTimeScaleApplicationStatusLogMockMvc.perform(post("/api/timeScaleApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeScaleApplicationStatusLog)))
                .andExpect(status().isCreated());

        // Validate the TimeScaleApplicationStatusLog in the database
        List<TimeScaleApplicationStatusLog> timeScaleApplicationStatusLogs = timeScaleApplicationStatusLogRepository.findAll();
        assertThat(timeScaleApplicationStatusLogs).hasSize(databaseSizeBeforeCreate + 1);
        TimeScaleApplicationStatusLog testTimeScaleApplicationStatusLog = timeScaleApplicationStatusLogs.get(timeScaleApplicationStatusLogs.size() - 1);
        assertThat(testTimeScaleApplicationStatusLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTimeScaleApplicationStatusLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testTimeScaleApplicationStatusLog.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testTimeScaleApplicationStatusLog.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testTimeScaleApplicationStatusLog.getCause()).isEqualTo(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void getAllTimeScaleApplicationStatusLogs() throws Exception {
        // Initialize the database
        timeScaleApplicationStatusLogRepository.saveAndFlush(timeScaleApplicationStatusLog);

        // Get all the timeScaleApplicationStatusLogs
        restTimeScaleApplicationStatusLogMockMvc.perform(get("/api/timeScaleApplicationStatusLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timeScaleApplicationStatusLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE.toString())));
    }

    @Test
    @Transactional
    public void getTimeScaleApplicationStatusLog() throws Exception {
        // Initialize the database
        timeScaleApplicationStatusLogRepository.saveAndFlush(timeScaleApplicationStatusLog);

        // Get the timeScaleApplicationStatusLog
        restTimeScaleApplicationStatusLogMockMvc.perform(get("/api/timeScaleApplicationStatusLogs/{id}", timeScaleApplicationStatusLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(timeScaleApplicationStatusLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeScaleApplicationStatusLog() throws Exception {
        // Get the timeScaleApplicationStatusLog
        restTimeScaleApplicationStatusLogMockMvc.perform(get("/api/timeScaleApplicationStatusLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeScaleApplicationStatusLog() throws Exception {
        // Initialize the database
        timeScaleApplicationStatusLogRepository.saveAndFlush(timeScaleApplicationStatusLog);

		int databaseSizeBeforeUpdate = timeScaleApplicationStatusLogRepository.findAll().size();

        // Update the timeScaleApplicationStatusLog
        timeScaleApplicationStatusLog.setStatus(UPDATED_STATUS);
        timeScaleApplicationStatusLog.setRemarks(UPDATED_REMARKS);
        timeScaleApplicationStatusLog.setFromDate(UPDATED_FROM_DATE);
        timeScaleApplicationStatusLog.setToDate(UPDATED_TO_DATE);
        timeScaleApplicationStatusLog.setCause(UPDATED_CAUSE);

        restTimeScaleApplicationStatusLogMockMvc.perform(put("/api/timeScaleApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeScaleApplicationStatusLog)))
                .andExpect(status().isOk());

        // Validate the TimeScaleApplicationStatusLog in the database
        List<TimeScaleApplicationStatusLog> timeScaleApplicationStatusLogs = timeScaleApplicationStatusLogRepository.findAll();
        assertThat(timeScaleApplicationStatusLogs).hasSize(databaseSizeBeforeUpdate);
        TimeScaleApplicationStatusLog testTimeScaleApplicationStatusLog = timeScaleApplicationStatusLogs.get(timeScaleApplicationStatusLogs.size() - 1);
        assertThat(testTimeScaleApplicationStatusLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTimeScaleApplicationStatusLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testTimeScaleApplicationStatusLog.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testTimeScaleApplicationStatusLog.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testTimeScaleApplicationStatusLog.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    public void deleteTimeScaleApplicationStatusLog() throws Exception {
        // Initialize the database
        timeScaleApplicationStatusLogRepository.saveAndFlush(timeScaleApplicationStatusLog);

		int databaseSizeBeforeDelete = timeScaleApplicationStatusLogRepository.findAll().size();

        // Get the timeScaleApplicationStatusLog
        restTimeScaleApplicationStatusLogMockMvc.perform(delete("/api/timeScaleApplicationStatusLogs/{id}", timeScaleApplicationStatusLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeScaleApplicationStatusLog> timeScaleApplicationStatusLogs = timeScaleApplicationStatusLogRepository.findAll();
        assertThat(timeScaleApplicationStatusLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
