package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.NameCnclApplicationStatusLog;
import gov.step.app.repository.NameCnclApplicationStatusLogRepository;
import gov.step.app.repository.search.NameCnclApplicationStatusLogSearchRepository;

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
 * Test class for the NameCnclApplicationStatusLogResource REST controller.
 *
 * @see NameCnclApplicationStatusLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NameCnclApplicationStatusLogResourceIntTest {


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
    private NameCnclApplicationStatusLogRepository nameCnclApplicationStatusLogRepository;

    @Inject
    private NameCnclApplicationStatusLogSearchRepository nameCnclApplicationStatusLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNameCnclApplicationStatusLogMockMvc;

    private NameCnclApplicationStatusLog nameCnclApplicationStatusLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NameCnclApplicationStatusLogResource nameCnclApplicationStatusLogResource = new NameCnclApplicationStatusLogResource();
        ReflectionTestUtils.setField(nameCnclApplicationStatusLogResource, "nameCnclApplicationStatusLogRepository", nameCnclApplicationStatusLogRepository);
        ReflectionTestUtils.setField(nameCnclApplicationStatusLogResource, "nameCnclApplicationStatusLogSearchRepository", nameCnclApplicationStatusLogSearchRepository);
        this.restNameCnclApplicationStatusLogMockMvc = MockMvcBuilders.standaloneSetup(nameCnclApplicationStatusLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        nameCnclApplicationStatusLog = new NameCnclApplicationStatusLog();
        nameCnclApplicationStatusLog.setStatus(DEFAULT_STATUS);
        nameCnclApplicationStatusLog.setRemarks(DEFAULT_REMARKS);
        nameCnclApplicationStatusLog.setFromDate(DEFAULT_FROM_DATE);
        nameCnclApplicationStatusLog.setToDate(DEFAULT_TO_DATE);
        nameCnclApplicationStatusLog.setCause(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void createNameCnclApplicationStatusLog() throws Exception {
        int databaseSizeBeforeCreate = nameCnclApplicationStatusLogRepository.findAll().size();

        // Create the NameCnclApplicationStatusLog

        restNameCnclApplicationStatusLogMockMvc.perform(post("/api/nameCnclApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nameCnclApplicationStatusLog)))
                .andExpect(status().isCreated());

        // Validate the NameCnclApplicationStatusLog in the database
        List<NameCnclApplicationStatusLog> nameCnclApplicationStatusLogs = nameCnclApplicationStatusLogRepository.findAll();
        assertThat(nameCnclApplicationStatusLogs).hasSize(databaseSizeBeforeCreate + 1);
        NameCnclApplicationStatusLog testNameCnclApplicationStatusLog = nameCnclApplicationStatusLogs.get(nameCnclApplicationStatusLogs.size() - 1);
        assertThat(testNameCnclApplicationStatusLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNameCnclApplicationStatusLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testNameCnclApplicationStatusLog.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testNameCnclApplicationStatusLog.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testNameCnclApplicationStatusLog.getCause()).isEqualTo(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void getAllNameCnclApplicationStatusLogs() throws Exception {
        // Initialize the database
        nameCnclApplicationStatusLogRepository.saveAndFlush(nameCnclApplicationStatusLog);

        // Get all the nameCnclApplicationStatusLogs
        restNameCnclApplicationStatusLogMockMvc.perform(get("/api/nameCnclApplicationStatusLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(nameCnclApplicationStatusLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE.toString())));
    }

    @Test
    @Transactional
    public void getNameCnclApplicationStatusLog() throws Exception {
        // Initialize the database
        nameCnclApplicationStatusLogRepository.saveAndFlush(nameCnclApplicationStatusLog);

        // Get the nameCnclApplicationStatusLog
        restNameCnclApplicationStatusLogMockMvc.perform(get("/api/nameCnclApplicationStatusLogs/{id}", nameCnclApplicationStatusLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(nameCnclApplicationStatusLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNameCnclApplicationStatusLog() throws Exception {
        // Get the nameCnclApplicationStatusLog
        restNameCnclApplicationStatusLogMockMvc.perform(get("/api/nameCnclApplicationStatusLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNameCnclApplicationStatusLog() throws Exception {
        // Initialize the database
        nameCnclApplicationStatusLogRepository.saveAndFlush(nameCnclApplicationStatusLog);

		int databaseSizeBeforeUpdate = nameCnclApplicationStatusLogRepository.findAll().size();

        // Update the nameCnclApplicationStatusLog
        nameCnclApplicationStatusLog.setStatus(UPDATED_STATUS);
        nameCnclApplicationStatusLog.setRemarks(UPDATED_REMARKS);
        nameCnclApplicationStatusLog.setFromDate(UPDATED_FROM_DATE);
        nameCnclApplicationStatusLog.setToDate(UPDATED_TO_DATE);
        nameCnclApplicationStatusLog.setCause(UPDATED_CAUSE);

        restNameCnclApplicationStatusLogMockMvc.perform(put("/api/nameCnclApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nameCnclApplicationStatusLog)))
                .andExpect(status().isOk());

        // Validate the NameCnclApplicationStatusLog in the database
        List<NameCnclApplicationStatusLog> nameCnclApplicationStatusLogs = nameCnclApplicationStatusLogRepository.findAll();
        assertThat(nameCnclApplicationStatusLogs).hasSize(databaseSizeBeforeUpdate);
        NameCnclApplicationStatusLog testNameCnclApplicationStatusLog = nameCnclApplicationStatusLogs.get(nameCnclApplicationStatusLogs.size() - 1);
        assertThat(testNameCnclApplicationStatusLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNameCnclApplicationStatusLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testNameCnclApplicationStatusLog.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testNameCnclApplicationStatusLog.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testNameCnclApplicationStatusLog.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    public void deleteNameCnclApplicationStatusLog() throws Exception {
        // Initialize the database
        nameCnclApplicationStatusLogRepository.saveAndFlush(nameCnclApplicationStatusLog);

		int databaseSizeBeforeDelete = nameCnclApplicationStatusLogRepository.findAll().size();

        // Get the nameCnclApplicationStatusLog
        restNameCnclApplicationStatusLogMockMvc.perform(delete("/api/nameCnclApplicationStatusLogs/{id}", nameCnclApplicationStatusLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<NameCnclApplicationStatusLog> nameCnclApplicationStatusLogs = nameCnclApplicationStatusLogRepository.findAll();
        assertThat(nameCnclApplicationStatusLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
