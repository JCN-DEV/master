package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.APScaleApplicationEditLog;
import gov.step.app.repository.APScaleApplicationEditLogRepository;
import gov.step.app.repository.search.APScaleApplicationEditLogSearchRepository;

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
 * Test class for the APScaleApplicationEditLogResource REST controller.
 *
 * @see APScaleApplicationEditLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class APScaleApplicationEditLogResourceIntTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private APScaleApplicationEditLogRepository aPScaleApplicationEditLogRepository;

    @Inject
    private APScaleApplicationEditLogSearchRepository aPScaleApplicationEditLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAPScaleApplicationEditLogMockMvc;

    private APScaleApplicationEditLog aPScaleApplicationEditLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        APScaleApplicationEditLogResource aPScaleApplicationEditLogResource = new APScaleApplicationEditLogResource();
        ReflectionTestUtils.setField(aPScaleApplicationEditLogResource, "aPScaleApplicationEditLogRepository", aPScaleApplicationEditLogRepository);
        ReflectionTestUtils.setField(aPScaleApplicationEditLogResource, "aPScaleApplicationEditLogSearchRepository", aPScaleApplicationEditLogSearchRepository);
        this.restAPScaleApplicationEditLogMockMvc = MockMvcBuilders.standaloneSetup(aPScaleApplicationEditLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aPScaleApplicationEditLog = new APScaleApplicationEditLog();
        aPScaleApplicationEditLog.setStatus(DEFAULT_STATUS);
        aPScaleApplicationEditLog.setRemarks(DEFAULT_REMARKS);
        aPScaleApplicationEditLog.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createAPScaleApplicationEditLog() throws Exception {
        int databaseSizeBeforeCreate = aPScaleApplicationEditLogRepository.findAll().size();

        // Create the APScaleApplicationEditLog

        restAPScaleApplicationEditLogMockMvc.perform(post("/api/aPScaleApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aPScaleApplicationEditLog)))
                .andExpect(status().isCreated());

        // Validate the APScaleApplicationEditLog in the database
        List<APScaleApplicationEditLog> aPScaleApplicationEditLogs = aPScaleApplicationEditLogRepository.findAll();
        assertThat(aPScaleApplicationEditLogs).hasSize(databaseSizeBeforeCreate + 1);
        APScaleApplicationEditLog testAPScaleApplicationEditLog = aPScaleApplicationEditLogs.get(aPScaleApplicationEditLogs.size() - 1);
        assertThat(testAPScaleApplicationEditLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAPScaleApplicationEditLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAPScaleApplicationEditLog.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllAPScaleApplicationEditLogs() throws Exception {
        // Initialize the database
        aPScaleApplicationEditLogRepository.saveAndFlush(aPScaleApplicationEditLog);

        // Get all the aPScaleApplicationEditLogs
        restAPScaleApplicationEditLogMockMvc.perform(get("/api/aPScaleApplicationEditLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aPScaleApplicationEditLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAPScaleApplicationEditLog() throws Exception {
        // Initialize the database
        aPScaleApplicationEditLogRepository.saveAndFlush(aPScaleApplicationEditLog);

        // Get the aPScaleApplicationEditLog
        restAPScaleApplicationEditLogMockMvc.perform(get("/api/aPScaleApplicationEditLogs/{id}", aPScaleApplicationEditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aPScaleApplicationEditLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAPScaleApplicationEditLog() throws Exception {
        // Get the aPScaleApplicationEditLog
        restAPScaleApplicationEditLogMockMvc.perform(get("/api/aPScaleApplicationEditLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAPScaleApplicationEditLog() throws Exception {
        // Initialize the database
        aPScaleApplicationEditLogRepository.saveAndFlush(aPScaleApplicationEditLog);

		int databaseSizeBeforeUpdate = aPScaleApplicationEditLogRepository.findAll().size();

        // Update the aPScaleApplicationEditLog
        aPScaleApplicationEditLog.setStatus(UPDATED_STATUS);
        aPScaleApplicationEditLog.setRemarks(UPDATED_REMARKS);
        aPScaleApplicationEditLog.setDate(UPDATED_DATE);

        restAPScaleApplicationEditLogMockMvc.perform(put("/api/aPScaleApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aPScaleApplicationEditLog)))
                .andExpect(status().isOk());

        // Validate the APScaleApplicationEditLog in the database
        List<APScaleApplicationEditLog> aPScaleApplicationEditLogs = aPScaleApplicationEditLogRepository.findAll();
        assertThat(aPScaleApplicationEditLogs).hasSize(databaseSizeBeforeUpdate);
        APScaleApplicationEditLog testAPScaleApplicationEditLog = aPScaleApplicationEditLogs.get(aPScaleApplicationEditLogs.size() - 1);
        assertThat(testAPScaleApplicationEditLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAPScaleApplicationEditLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAPScaleApplicationEditLog.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteAPScaleApplicationEditLog() throws Exception {
        // Initialize the database
        aPScaleApplicationEditLogRepository.saveAndFlush(aPScaleApplicationEditLog);

		int databaseSizeBeforeDelete = aPScaleApplicationEditLogRepository.findAll().size();

        // Get the aPScaleApplicationEditLog
        restAPScaleApplicationEditLogMockMvc.perform(delete("/api/aPScaleApplicationEditLogs/{id}", aPScaleApplicationEditLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<APScaleApplicationEditLog> aPScaleApplicationEditLogs = aPScaleApplicationEditLogRepository.findAll();
        assertThat(aPScaleApplicationEditLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
