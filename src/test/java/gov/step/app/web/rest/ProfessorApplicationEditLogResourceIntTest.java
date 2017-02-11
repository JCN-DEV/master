package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.ProfessorApplicationEditLog;
import gov.step.app.repository.ProfessorApplicationEditLogRepository;
import gov.step.app.repository.search.ProfessorApplicationEditLogSearchRepository;

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
 * Test class for the ProfessorApplicationEditLogResource REST controller.
 *
 * @see ProfessorApplicationEditLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProfessorApplicationEditLogResourceIntTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ProfessorApplicationEditLogRepository professorApplicationEditLogRepository;

    @Inject
    private ProfessorApplicationEditLogSearchRepository professorApplicationEditLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProfessorApplicationEditLogMockMvc;

    private ProfessorApplicationEditLog professorApplicationEditLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfessorApplicationEditLogResource professorApplicationEditLogResource = new ProfessorApplicationEditLogResource();
        ReflectionTestUtils.setField(professorApplicationEditLogResource, "professorApplicationEditLogRepository", professorApplicationEditLogRepository);
        ReflectionTestUtils.setField(professorApplicationEditLogResource, "professorApplicationEditLogSearchRepository", professorApplicationEditLogSearchRepository);
        this.restProfessorApplicationEditLogMockMvc = MockMvcBuilders.standaloneSetup(professorApplicationEditLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        professorApplicationEditLog = new ProfessorApplicationEditLog();
        professorApplicationEditLog.setStatus(DEFAULT_STATUS);
        professorApplicationEditLog.setRemarks(DEFAULT_REMARKS);
        professorApplicationEditLog.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createProfessorApplicationEditLog() throws Exception {
        int databaseSizeBeforeCreate = professorApplicationEditLogRepository.findAll().size();

        // Create the ProfessorApplicationEditLog

        restProfessorApplicationEditLogMockMvc.perform(post("/api/professorApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorApplicationEditLog)))
                .andExpect(status().isCreated());

        // Validate the ProfessorApplicationEditLog in the database
        List<ProfessorApplicationEditLog> professorApplicationEditLogs = professorApplicationEditLogRepository.findAll();
        assertThat(professorApplicationEditLogs).hasSize(databaseSizeBeforeCreate + 1);
        ProfessorApplicationEditLog testProfessorApplicationEditLog = professorApplicationEditLogs.get(professorApplicationEditLogs.size() - 1);
        assertThat(testProfessorApplicationEditLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProfessorApplicationEditLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testProfessorApplicationEditLog.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllProfessorApplicationEditLogs() throws Exception {
        // Initialize the database
        professorApplicationEditLogRepository.saveAndFlush(professorApplicationEditLog);

        // Get all the professorApplicationEditLogs
        restProfessorApplicationEditLogMockMvc.perform(get("/api/professorApplicationEditLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(professorApplicationEditLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getProfessorApplicationEditLog() throws Exception {
        // Initialize the database
        professorApplicationEditLogRepository.saveAndFlush(professorApplicationEditLog);

        // Get the professorApplicationEditLog
        restProfessorApplicationEditLogMockMvc.perform(get("/api/professorApplicationEditLogs/{id}", professorApplicationEditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(professorApplicationEditLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfessorApplicationEditLog() throws Exception {
        // Get the professorApplicationEditLog
        restProfessorApplicationEditLogMockMvc.perform(get("/api/professorApplicationEditLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessorApplicationEditLog() throws Exception {
        // Initialize the database
        professorApplicationEditLogRepository.saveAndFlush(professorApplicationEditLog);

		int databaseSizeBeforeUpdate = professorApplicationEditLogRepository.findAll().size();

        // Update the professorApplicationEditLog
        professorApplicationEditLog.setStatus(UPDATED_STATUS);
        professorApplicationEditLog.setRemarks(UPDATED_REMARKS);
        professorApplicationEditLog.setDate(UPDATED_DATE);

        restProfessorApplicationEditLogMockMvc.perform(put("/api/professorApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorApplicationEditLog)))
                .andExpect(status().isOk());

        // Validate the ProfessorApplicationEditLog in the database
        List<ProfessorApplicationEditLog> professorApplicationEditLogs = professorApplicationEditLogRepository.findAll();
        assertThat(professorApplicationEditLogs).hasSize(databaseSizeBeforeUpdate);
        ProfessorApplicationEditLog testProfessorApplicationEditLog = professorApplicationEditLogs.get(professorApplicationEditLogs.size() - 1);
        assertThat(testProfessorApplicationEditLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProfessorApplicationEditLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testProfessorApplicationEditLog.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteProfessorApplicationEditLog() throws Exception {
        // Initialize the database
        professorApplicationEditLogRepository.saveAndFlush(professorApplicationEditLog);

		int databaseSizeBeforeDelete = professorApplicationEditLogRepository.findAll().size();

        // Get the professorApplicationEditLog
        restProfessorApplicationEditLogMockMvc.perform(delete("/api/professorApplicationEditLogs/{id}", professorApplicationEditLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProfessorApplicationEditLog> professorApplicationEditLogs = professorApplicationEditLogRepository.findAll();
        assertThat(professorApplicationEditLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
