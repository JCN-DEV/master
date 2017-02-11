package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.APScaleApplicationStatusLog;
import gov.step.app.repository.APScaleApplicationStatusLogRepository;
import gov.step.app.repository.search.APScaleApplicationStatusLogSearchRepository;

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
 * Test class for the APScaleApplicationStatusLogResource REST controller.
 *
 * @see APScaleApplicationStatusLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class APScaleApplicationStatusLogResourceIntTest {


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
    private APScaleApplicationStatusLogRepository aPScaleApplicationStatusLogRepository;

    @Inject
    private APScaleApplicationStatusLogSearchRepository aPScaleApplicationStatusLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAPScaleApplicationStatusLogMockMvc;

    private APScaleApplicationStatusLog aPScaleApplicationStatusLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        APScaleApplicationStatusLogResource aPScaleApplicationStatusLogResource = new APScaleApplicationStatusLogResource();
        ReflectionTestUtils.setField(aPScaleApplicationStatusLogResource, "aPScaleApplicationStatusLogRepository", aPScaleApplicationStatusLogRepository);
        ReflectionTestUtils.setField(aPScaleApplicationStatusLogResource, "aPScaleApplicationStatusLogSearchRepository", aPScaleApplicationStatusLogSearchRepository);
        this.restAPScaleApplicationStatusLogMockMvc = MockMvcBuilders.standaloneSetup(aPScaleApplicationStatusLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aPScaleApplicationStatusLog = new APScaleApplicationStatusLog();
        aPScaleApplicationStatusLog.setStatus(DEFAULT_STATUS);
        aPScaleApplicationStatusLog.setRemarks(DEFAULT_REMARKS);
        aPScaleApplicationStatusLog.setFromDate(DEFAULT_FROM_DATE);
        aPScaleApplicationStatusLog.setToDate(DEFAULT_TO_DATE);
        aPScaleApplicationStatusLog.setCause(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void createAPScaleApplicationStatusLog() throws Exception {
        int databaseSizeBeforeCreate = aPScaleApplicationStatusLogRepository.findAll().size();

        // Create the APScaleApplicationStatusLog

        restAPScaleApplicationStatusLogMockMvc.perform(post("/api/aPScaleApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aPScaleApplicationStatusLog)))
                .andExpect(status().isCreated());

        // Validate the APScaleApplicationStatusLog in the database
        List<APScaleApplicationStatusLog> aPScaleApplicationStatusLogs = aPScaleApplicationStatusLogRepository.findAll();
        assertThat(aPScaleApplicationStatusLogs).hasSize(databaseSizeBeforeCreate + 1);
        APScaleApplicationStatusLog testAPScaleApplicationStatusLog = aPScaleApplicationStatusLogs.get(aPScaleApplicationStatusLogs.size() - 1);
        assertThat(testAPScaleApplicationStatusLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAPScaleApplicationStatusLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAPScaleApplicationStatusLog.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testAPScaleApplicationStatusLog.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testAPScaleApplicationStatusLog.getCause()).isEqualTo(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void getAllAPScaleApplicationStatusLogs() throws Exception {
        // Initialize the database
        aPScaleApplicationStatusLogRepository.saveAndFlush(aPScaleApplicationStatusLog);

        // Get all the aPScaleApplicationStatusLogs
        restAPScaleApplicationStatusLogMockMvc.perform(get("/api/aPScaleApplicationStatusLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aPScaleApplicationStatusLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE.toString())));
    }

    @Test
    @Transactional
    public void getAPScaleApplicationStatusLog() throws Exception {
        // Initialize the database
        aPScaleApplicationStatusLogRepository.saveAndFlush(aPScaleApplicationStatusLog);

        // Get the aPScaleApplicationStatusLog
        restAPScaleApplicationStatusLogMockMvc.perform(get("/api/aPScaleApplicationStatusLogs/{id}", aPScaleApplicationStatusLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aPScaleApplicationStatusLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAPScaleApplicationStatusLog() throws Exception {
        // Get the aPScaleApplicationStatusLog
        restAPScaleApplicationStatusLogMockMvc.perform(get("/api/aPScaleApplicationStatusLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAPScaleApplicationStatusLog() throws Exception {
        // Initialize the database
        aPScaleApplicationStatusLogRepository.saveAndFlush(aPScaleApplicationStatusLog);

		int databaseSizeBeforeUpdate = aPScaleApplicationStatusLogRepository.findAll().size();

        // Update the aPScaleApplicationStatusLog
        aPScaleApplicationStatusLog.setStatus(UPDATED_STATUS);
        aPScaleApplicationStatusLog.setRemarks(UPDATED_REMARKS);
        aPScaleApplicationStatusLog.setFromDate(UPDATED_FROM_DATE);
        aPScaleApplicationStatusLog.setToDate(UPDATED_TO_DATE);
        aPScaleApplicationStatusLog.setCause(UPDATED_CAUSE);

        restAPScaleApplicationStatusLogMockMvc.perform(put("/api/aPScaleApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aPScaleApplicationStatusLog)))
                .andExpect(status().isOk());

        // Validate the APScaleApplicationStatusLog in the database
        List<APScaleApplicationStatusLog> aPScaleApplicationStatusLogs = aPScaleApplicationStatusLogRepository.findAll();
        assertThat(aPScaleApplicationStatusLogs).hasSize(databaseSizeBeforeUpdate);
        APScaleApplicationStatusLog testAPScaleApplicationStatusLog = aPScaleApplicationStatusLogs.get(aPScaleApplicationStatusLogs.size() - 1);
        assertThat(testAPScaleApplicationStatusLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAPScaleApplicationStatusLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAPScaleApplicationStatusLog.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testAPScaleApplicationStatusLog.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testAPScaleApplicationStatusLog.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    public void deleteAPScaleApplicationStatusLog() throws Exception {
        // Initialize the database
        aPScaleApplicationStatusLogRepository.saveAndFlush(aPScaleApplicationStatusLog);

		int databaseSizeBeforeDelete = aPScaleApplicationStatusLogRepository.findAll().size();

        // Get the aPScaleApplicationStatusLog
        restAPScaleApplicationStatusLogMockMvc.perform(delete("/api/aPScaleApplicationStatusLogs/{id}", aPScaleApplicationStatusLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<APScaleApplicationStatusLog> aPScaleApplicationStatusLogs = aPScaleApplicationStatusLogRepository.findAll();
        assertThat(aPScaleApplicationStatusLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
