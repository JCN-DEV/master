package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.ProfessorApplicationStatusLog;
import gov.step.app.repository.ProfessorApplicationStatusLogRepository;
import gov.step.app.repository.search.ProfessorApplicationStatusLogSearchRepository;

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
 * Test class for the ProfessorApplicationStatusLogResource REST controller.
 *
 * @see ProfessorApplicationStatusLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProfessorApplicationStatusLogResourceIntTest {


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
    private ProfessorApplicationStatusLogRepository professorApplicationStatusLogRepository;

    @Inject
    private ProfessorApplicationStatusLogSearchRepository professorApplicationStatusLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProfessorApplicationStatusLogMockMvc;

    private ProfessorApplicationStatusLog professorApplicationStatusLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfessorApplicationStatusLogResource professorApplicationStatusLogResource = new ProfessorApplicationStatusLogResource();
        ReflectionTestUtils.setField(professorApplicationStatusLogResource, "professorApplicationStatusLogRepository", professorApplicationStatusLogRepository);
        ReflectionTestUtils.setField(professorApplicationStatusLogResource, "professorApplicationStatusLogSearchRepository", professorApplicationStatusLogSearchRepository);
        this.restProfessorApplicationStatusLogMockMvc = MockMvcBuilders.standaloneSetup(professorApplicationStatusLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        professorApplicationStatusLog = new ProfessorApplicationStatusLog();
        professorApplicationStatusLog.setStatus(DEFAULT_STATUS);
        professorApplicationStatusLog.setRemarks(DEFAULT_REMARKS);
        professorApplicationStatusLog.setFromDate(DEFAULT_FROM_DATE);
        professorApplicationStatusLog.setToDate(DEFAULT_TO_DATE);
        professorApplicationStatusLog.setCause(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void createProfessorApplicationStatusLog() throws Exception {
        int databaseSizeBeforeCreate = professorApplicationStatusLogRepository.findAll().size();

        // Create the ProfessorApplicationStatusLog

        restProfessorApplicationStatusLogMockMvc.perform(post("/api/professorApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorApplicationStatusLog)))
                .andExpect(status().isCreated());

        // Validate the ProfessorApplicationStatusLog in the database
        List<ProfessorApplicationStatusLog> professorApplicationStatusLogs = professorApplicationStatusLogRepository.findAll();
        assertThat(professorApplicationStatusLogs).hasSize(databaseSizeBeforeCreate + 1);
        ProfessorApplicationStatusLog testProfessorApplicationStatusLog = professorApplicationStatusLogs.get(professorApplicationStatusLogs.size() - 1);
        assertThat(testProfessorApplicationStatusLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProfessorApplicationStatusLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testProfessorApplicationStatusLog.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testProfessorApplicationStatusLog.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testProfessorApplicationStatusLog.getCause()).isEqualTo(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void getAllProfessorApplicationStatusLogs() throws Exception {
        // Initialize the database
        professorApplicationStatusLogRepository.saveAndFlush(professorApplicationStatusLog);

        // Get all the professorApplicationStatusLogs
        restProfessorApplicationStatusLogMockMvc.perform(get("/api/professorApplicationStatusLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(professorApplicationStatusLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE.toString())));
    }

    @Test
    @Transactional
    public void getProfessorApplicationStatusLog() throws Exception {
        // Initialize the database
        professorApplicationStatusLogRepository.saveAndFlush(professorApplicationStatusLog);

        // Get the professorApplicationStatusLog
        restProfessorApplicationStatusLogMockMvc.perform(get("/api/professorApplicationStatusLogs/{id}", professorApplicationStatusLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(professorApplicationStatusLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfessorApplicationStatusLog() throws Exception {
        // Get the professorApplicationStatusLog
        restProfessorApplicationStatusLogMockMvc.perform(get("/api/professorApplicationStatusLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessorApplicationStatusLog() throws Exception {
        // Initialize the database
        professorApplicationStatusLogRepository.saveAndFlush(professorApplicationStatusLog);

		int databaseSizeBeforeUpdate = professorApplicationStatusLogRepository.findAll().size();

        // Update the professorApplicationStatusLog
        professorApplicationStatusLog.setStatus(UPDATED_STATUS);
        professorApplicationStatusLog.setRemarks(UPDATED_REMARKS);
        professorApplicationStatusLog.setFromDate(UPDATED_FROM_DATE);
        professorApplicationStatusLog.setToDate(UPDATED_TO_DATE);
        professorApplicationStatusLog.setCause(UPDATED_CAUSE);

        restProfessorApplicationStatusLogMockMvc.perform(put("/api/professorApplicationStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorApplicationStatusLog)))
                .andExpect(status().isOk());

        // Validate the ProfessorApplicationStatusLog in the database
        List<ProfessorApplicationStatusLog> professorApplicationStatusLogs = professorApplicationStatusLogRepository.findAll();
        assertThat(professorApplicationStatusLogs).hasSize(databaseSizeBeforeUpdate);
        ProfessorApplicationStatusLog testProfessorApplicationStatusLog = professorApplicationStatusLogs.get(professorApplicationStatusLogs.size() - 1);
        assertThat(testProfessorApplicationStatusLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProfessorApplicationStatusLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testProfessorApplicationStatusLog.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testProfessorApplicationStatusLog.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testProfessorApplicationStatusLog.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    public void deleteProfessorApplicationStatusLog() throws Exception {
        // Initialize the database
        professorApplicationStatusLogRepository.saveAndFlush(professorApplicationStatusLog);

		int databaseSizeBeforeDelete = professorApplicationStatusLogRepository.findAll().size();

        // Get the professorApplicationStatusLog
        restProfessorApplicationStatusLogMockMvc.perform(delete("/api/professorApplicationStatusLogs/{id}", professorApplicationStatusLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProfessorApplicationStatusLog> professorApplicationStatusLogs = professorApplicationStatusLogRepository.findAll();
        assertThat(professorApplicationStatusLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
