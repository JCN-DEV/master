package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoApplicationStatusLog;
import gov.step.app.repository.MpoApplicationStatusLogRepository;
import gov.step.app.repository.search.MpoApplicationStatusLogSearchRepository;

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
 * Test class for the MpoApplicationStatusLogResource REST controller.
 *
 * @see MpoApplicationStatusLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoApplicationStatusLogResourceIntTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private MpoApplicationStatusLogRepository mpoApplicationStatusLogRepository;

    @Inject
    private MpoApplicationStatusLogSearchRepository mpoApplicationStatusLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoApplicationStatusLogMockMvc;

    private MpoApplicationStatusLog mpoApplicationStatusLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoApplicationStatusLogResource mpoApplicationStatusLogResource = new MpoApplicationStatusLogResource();
        ReflectionTestUtils.setField(mpoApplicationStatusLogResource, "mpoApplicationStatusLogRepository", mpoApplicationStatusLogRepository);
        ReflectionTestUtils.setField(mpoApplicationStatusLogResource, "mpoApplicationStatusLogSearchRepository", mpoApplicationStatusLogSearchRepository);
        this.restMpoApplicationStatusLogMockMvc = MockMvcBuilders.standaloneSetup(mpoApplicationStatusLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoApplicationStatusLog = new MpoApplicationStatusLog();
        mpoApplicationStatusLog.setStatus(DEFAULT_STATUS);
        mpoApplicationStatusLog.setRemarks(DEFAULT_REMARKS);
        mpoApplicationStatusLog.setFromDate(DEFAULT_FROM_DATE);
        mpoApplicationStatusLog.setToDate(DEFAULT_TO_DATE);
    }

    @Test
    @Transactional
    public void createMpoApplicationStatusLog() throws Exception {
        int databaseSizeBeforeCreate = mpoApplicationStatusLogRepository.findAll().size();

        // Create the MpoApplicationStatusLog

        restMpoApplicationStatusLogMockMvc.perform(post("/api/mpoApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoApplicationStatusLog)))
                .andExpect(status().isCreated());

        // Validate the MpoApplicationStatusLog in the database
        List<MpoApplicationStatusLog> mpoApplicationStatusLogs = mpoApplicationStatusLogRepository.findAll();
        assertThat(mpoApplicationStatusLogs).hasSize(databaseSizeBeforeCreate + 1);
        MpoApplicationStatusLog testMpoApplicationStatusLog = mpoApplicationStatusLogs.get(mpoApplicationStatusLogs.size() - 1);
        assertThat(testMpoApplicationStatusLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMpoApplicationStatusLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testMpoApplicationStatusLog.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testMpoApplicationStatusLog.getToDate()).isEqualTo(DEFAULT_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllMpoApplicationStatusLogs() throws Exception {
        // Initialize the database
        mpoApplicationStatusLogRepository.saveAndFlush(mpoApplicationStatusLog);

        // Get all the mpoApplicationStatusLogs
        restMpoApplicationStatusLogMockMvc.perform(get("/api/mpoApplicationStatusLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoApplicationStatusLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMpoApplicationStatusLog() throws Exception {
        // Initialize the database
        mpoApplicationStatusLogRepository.saveAndFlush(mpoApplicationStatusLog);

        // Get the mpoApplicationStatusLog
        restMpoApplicationStatusLogMockMvc.perform(get("/api/mpoApplicationStatusLogs/{id}", mpoApplicationStatusLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoApplicationStatusLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMpoApplicationStatusLog() throws Exception {
        // Get the mpoApplicationStatusLog
        restMpoApplicationStatusLogMockMvc.perform(get("/api/mpoApplicationStatusLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoApplicationStatusLog() throws Exception {
        // Initialize the database
        mpoApplicationStatusLogRepository.saveAndFlush(mpoApplicationStatusLog);

		int databaseSizeBeforeUpdate = mpoApplicationStatusLogRepository.findAll().size();

        // Update the mpoApplicationStatusLog
        mpoApplicationStatusLog.setStatus(UPDATED_STATUS);
        mpoApplicationStatusLog.setRemarks(UPDATED_REMARKS);
        mpoApplicationStatusLog.setFromDate(UPDATED_FROM_DATE);
        mpoApplicationStatusLog.setToDate(UPDATED_TO_DATE);

        restMpoApplicationStatusLogMockMvc.perform(put("/api/mpoApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoApplicationStatusLog)))
                .andExpect(status().isOk());

        // Validate the MpoApplicationStatusLog in the database
        List<MpoApplicationStatusLog> mpoApplicationStatusLogs = mpoApplicationStatusLogRepository.findAll();
        assertThat(mpoApplicationStatusLogs).hasSize(databaseSizeBeforeUpdate);
        MpoApplicationStatusLog testMpoApplicationStatusLog = mpoApplicationStatusLogs.get(mpoApplicationStatusLogs.size() - 1);
        assertThat(testMpoApplicationStatusLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMpoApplicationStatusLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testMpoApplicationStatusLog.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testMpoApplicationStatusLog.getToDate()).isEqualTo(UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void deleteMpoApplicationStatusLog() throws Exception {
        // Initialize the database
        mpoApplicationStatusLogRepository.saveAndFlush(mpoApplicationStatusLog);

		int databaseSizeBeforeDelete = mpoApplicationStatusLogRepository.findAll().size();

        // Get the mpoApplicationStatusLog
        restMpoApplicationStatusLogMockMvc.perform(delete("/api/mpoApplicationStatusLogs/{id}", mpoApplicationStatusLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoApplicationStatusLog> mpoApplicationStatusLogs = mpoApplicationStatusLogRepository.findAll();
        assertThat(mpoApplicationStatusLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
