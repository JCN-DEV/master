package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.BEdApplicationStatusLog;
import gov.step.app.repository.BEdApplicationStatusLogRepository;
import gov.step.app.repository.search.BEdApplicationStatusLogSearchRepository;

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
 * Test class for the BEdApplicationStatusLogResource REST controller.
 *
 * @see BEdApplicationStatusLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BEdApplicationStatusLogResourceIntTest {


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
    private BEdApplicationStatusLogRepository bEdApplicationStatusLogRepository;

    @Inject
    private BEdApplicationStatusLogSearchRepository bEdApplicationStatusLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBEdApplicationStatusLogMockMvc;

    private BEdApplicationStatusLog bEdApplicationStatusLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BEdApplicationStatusLogResource bEdApplicationStatusLogResource = new BEdApplicationStatusLogResource();
        ReflectionTestUtils.setField(bEdApplicationStatusLogResource, "bEdApplicationStatusLogRepository", bEdApplicationStatusLogRepository);
        ReflectionTestUtils.setField(bEdApplicationStatusLogResource, "bEdApplicationStatusLogSearchRepository", bEdApplicationStatusLogSearchRepository);
        this.restBEdApplicationStatusLogMockMvc = MockMvcBuilders.standaloneSetup(bEdApplicationStatusLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bEdApplicationStatusLog = new BEdApplicationStatusLog();
        bEdApplicationStatusLog.setStatus(DEFAULT_STATUS);
        bEdApplicationStatusLog.setRemarks(DEFAULT_REMARKS);
        bEdApplicationStatusLog.setFromDate(DEFAULT_FROM_DATE);
        bEdApplicationStatusLog.setToDate(DEFAULT_TO_DATE);
        bEdApplicationStatusLog.setCause(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void createBEdApplicationStatusLog() throws Exception {
        int databaseSizeBeforeCreate = bEdApplicationStatusLogRepository.findAll().size();

        // Create the BEdApplicationStatusLog

        restBEdApplicationStatusLogMockMvc.perform(post("/api/bEdApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bEdApplicationStatusLog)))
                .andExpect(status().isCreated());

        // Validate the BEdApplicationStatusLog in the database
        List<BEdApplicationStatusLog> bEdApplicationStatusLogs = bEdApplicationStatusLogRepository.findAll();
        assertThat(bEdApplicationStatusLogs).hasSize(databaseSizeBeforeCreate + 1);
        BEdApplicationStatusLog testBEdApplicationStatusLog = bEdApplicationStatusLogs.get(bEdApplicationStatusLogs.size() - 1);
        assertThat(testBEdApplicationStatusLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBEdApplicationStatusLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testBEdApplicationStatusLog.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testBEdApplicationStatusLog.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testBEdApplicationStatusLog.getCause()).isEqualTo(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void getAllBEdApplicationStatusLogs() throws Exception {
        // Initialize the database
        bEdApplicationStatusLogRepository.saveAndFlush(bEdApplicationStatusLog);

        // Get all the bEdApplicationStatusLogs
        restBEdApplicationStatusLogMockMvc.perform(get("/api/bEdApplicationStatusLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bEdApplicationStatusLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE.toString())));
    }

    @Test
    @Transactional
    public void getBEdApplicationStatusLog() throws Exception {
        // Initialize the database
        bEdApplicationStatusLogRepository.saveAndFlush(bEdApplicationStatusLog);

        // Get the bEdApplicationStatusLog
        restBEdApplicationStatusLogMockMvc.perform(get("/api/bEdApplicationStatusLogs/{id}", bEdApplicationStatusLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bEdApplicationStatusLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBEdApplicationStatusLog() throws Exception {
        // Get the bEdApplicationStatusLog
        restBEdApplicationStatusLogMockMvc.perform(get("/api/bEdApplicationStatusLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBEdApplicationStatusLog() throws Exception {
        // Initialize the database
        bEdApplicationStatusLogRepository.saveAndFlush(bEdApplicationStatusLog);

		int databaseSizeBeforeUpdate = bEdApplicationStatusLogRepository.findAll().size();

        // Update the bEdApplicationStatusLog
        bEdApplicationStatusLog.setStatus(UPDATED_STATUS);
        bEdApplicationStatusLog.setRemarks(UPDATED_REMARKS);
        bEdApplicationStatusLog.setFromDate(UPDATED_FROM_DATE);
        bEdApplicationStatusLog.setToDate(UPDATED_TO_DATE);
        bEdApplicationStatusLog.setCause(UPDATED_CAUSE);

        restBEdApplicationStatusLogMockMvc.perform(put("/api/bEdApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bEdApplicationStatusLog)))
                .andExpect(status().isOk());

        // Validate the BEdApplicationStatusLog in the database
        List<BEdApplicationStatusLog> bEdApplicationStatusLogs = bEdApplicationStatusLogRepository.findAll();
        assertThat(bEdApplicationStatusLogs).hasSize(databaseSizeBeforeUpdate);
        BEdApplicationStatusLog testBEdApplicationStatusLog = bEdApplicationStatusLogs.get(bEdApplicationStatusLogs.size() - 1);
        assertThat(testBEdApplicationStatusLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBEdApplicationStatusLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testBEdApplicationStatusLog.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testBEdApplicationStatusLog.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testBEdApplicationStatusLog.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    public void deleteBEdApplicationStatusLog() throws Exception {
        // Initialize the database
        bEdApplicationStatusLogRepository.saveAndFlush(bEdApplicationStatusLog);

		int databaseSizeBeforeDelete = bEdApplicationStatusLogRepository.findAll().size();

        // Get the bEdApplicationStatusLog
        restBEdApplicationStatusLogMockMvc.perform(delete("/api/bEdApplicationStatusLogs/{id}", bEdApplicationStatusLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BEdApplicationStatusLog> bEdApplicationStatusLogs = bEdApplicationStatusLogRepository.findAll();
        assertThat(bEdApplicationStatusLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
