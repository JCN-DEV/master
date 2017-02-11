package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.NameCnclApplicationEditLog;
import gov.step.app.repository.NameCnclApplicationEditLogRepository;
import gov.step.app.repository.search.NameCnclApplicationEditLogSearchRepository;

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
 * Test class for the NameCnclApplicationEditLogResource REST controller.
 *
 * @see NameCnclApplicationEditLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NameCnclApplicationEditLogResourceIntTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private NameCnclApplicationEditLogRepository nameCnclApplicationEditLogRepository;

    @Inject
    private NameCnclApplicationEditLogSearchRepository nameCnclApplicationEditLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNameCnclApplicationEditLogMockMvc;

    private NameCnclApplicationEditLog nameCnclApplicationEditLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NameCnclApplicationEditLogResource nameCnclApplicationEditLogResource = new NameCnclApplicationEditLogResource();
        ReflectionTestUtils.setField(nameCnclApplicationEditLogResource, "nameCnclApplicationEditLogRepository", nameCnclApplicationEditLogRepository);
        ReflectionTestUtils.setField(nameCnclApplicationEditLogResource, "nameCnclApplicationEditLogSearchRepository", nameCnclApplicationEditLogSearchRepository);
        this.restNameCnclApplicationEditLogMockMvc = MockMvcBuilders.standaloneSetup(nameCnclApplicationEditLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        nameCnclApplicationEditLog = new NameCnclApplicationEditLog();
        nameCnclApplicationEditLog.setStatus(DEFAULT_STATUS);
        nameCnclApplicationEditLog.setRemarks(DEFAULT_REMARKS);
        nameCnclApplicationEditLog.setCreated_date(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createNameCnclApplicationEditLog() throws Exception {
        int databaseSizeBeforeCreate = nameCnclApplicationEditLogRepository.findAll().size();

        // Create the NameCnclApplicationEditLog

        restNameCnclApplicationEditLogMockMvc.perform(post("/api/nameCnclApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nameCnclApplicationEditLog)))
                .andExpect(status().isCreated());

        // Validate the NameCnclApplicationEditLog in the database
        List<NameCnclApplicationEditLog> nameCnclApplicationEditLogs = nameCnclApplicationEditLogRepository.findAll();
        assertThat(nameCnclApplicationEditLogs).hasSize(databaseSizeBeforeCreate + 1);
        NameCnclApplicationEditLog testNameCnclApplicationEditLog = nameCnclApplicationEditLogs.get(nameCnclApplicationEditLogs.size() - 1);
        assertThat(testNameCnclApplicationEditLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNameCnclApplicationEditLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testNameCnclApplicationEditLog.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNameCnclApplicationEditLogs() throws Exception {
        // Initialize the database
        nameCnclApplicationEditLogRepository.saveAndFlush(nameCnclApplicationEditLog);

        // Get all the nameCnclApplicationEditLogs
        restNameCnclApplicationEditLogMockMvc.perform(get("/api/nameCnclApplicationEditLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(nameCnclApplicationEditLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getNameCnclApplicationEditLog() throws Exception {
        // Initialize the database
        nameCnclApplicationEditLogRepository.saveAndFlush(nameCnclApplicationEditLog);

        // Get the nameCnclApplicationEditLog
        restNameCnclApplicationEditLogMockMvc.perform(get("/api/nameCnclApplicationEditLogs/{id}", nameCnclApplicationEditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(nameCnclApplicationEditLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNameCnclApplicationEditLog() throws Exception {
        // Get the nameCnclApplicationEditLog
        restNameCnclApplicationEditLogMockMvc.perform(get("/api/nameCnclApplicationEditLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNameCnclApplicationEditLog() throws Exception {
        // Initialize the database
        nameCnclApplicationEditLogRepository.saveAndFlush(nameCnclApplicationEditLog);

		int databaseSizeBeforeUpdate = nameCnclApplicationEditLogRepository.findAll().size();

        // Update the nameCnclApplicationEditLog
        nameCnclApplicationEditLog.setStatus(UPDATED_STATUS);
        nameCnclApplicationEditLog.setRemarks(UPDATED_REMARKS);
        nameCnclApplicationEditLog.setCreated_date(UPDATED_CREATED_DATE);

        restNameCnclApplicationEditLogMockMvc.perform(put("/api/nameCnclApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nameCnclApplicationEditLog)))
                .andExpect(status().isOk());

        // Validate the NameCnclApplicationEditLog in the database
        List<NameCnclApplicationEditLog> nameCnclApplicationEditLogs = nameCnclApplicationEditLogRepository.findAll();
        assertThat(nameCnclApplicationEditLogs).hasSize(databaseSizeBeforeUpdate);
        NameCnclApplicationEditLog testNameCnclApplicationEditLog = nameCnclApplicationEditLogs.get(nameCnclApplicationEditLogs.size() - 1);
        assertThat(testNameCnclApplicationEditLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNameCnclApplicationEditLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testNameCnclApplicationEditLog.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void deleteNameCnclApplicationEditLog() throws Exception {
        // Initialize the database
        nameCnclApplicationEditLogRepository.saveAndFlush(nameCnclApplicationEditLog);

		int databaseSizeBeforeDelete = nameCnclApplicationEditLogRepository.findAll().size();

        // Get the nameCnclApplicationEditLog
        restNameCnclApplicationEditLogMockMvc.perform(delete("/api/nameCnclApplicationEditLogs/{id}", nameCnclApplicationEditLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<NameCnclApplicationEditLog> nameCnclApplicationEditLogs = nameCnclApplicationEditLogRepository.findAll();
        assertThat(nameCnclApplicationEditLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
