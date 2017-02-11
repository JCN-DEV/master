package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoApplicationEditLog;
import gov.step.app.repository.MpoApplicationEditLogRepository;
import gov.step.app.repository.search.MpoApplicationEditLogSearchRepository;

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
 * Test class for the MpoApplicationEditLogResource REST controller.
 *
 * @see MpoApplicationEditLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoApplicationEditLogResourceIntTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private MpoApplicationEditLogRepository mpoApplicationEditLogRepository;

    @Inject
    private MpoApplicationEditLogSearchRepository mpoApplicationEditLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoApplicationEditLogMockMvc;

    private MpoApplicationEditLog mpoApplicationEditLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoApplicationEditLogResource mpoApplicationEditLogResource = new MpoApplicationEditLogResource();
        ReflectionTestUtils.setField(mpoApplicationEditLogResource, "mpoApplicationEditLogRepository", mpoApplicationEditLogRepository);
        ReflectionTestUtils.setField(mpoApplicationEditLogResource, "mpoApplicationEditLogSearchRepository", mpoApplicationEditLogSearchRepository);
        this.restMpoApplicationEditLogMockMvc = MockMvcBuilders.standaloneSetup(mpoApplicationEditLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoApplicationEditLog = new MpoApplicationEditLog();
        mpoApplicationEditLog.setStatus(DEFAULT_STATUS);
        mpoApplicationEditLog.setRemarks(DEFAULT_REMARKS);
        mpoApplicationEditLog.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createMpoApplicationEditLog() throws Exception {
        int databaseSizeBeforeCreate = mpoApplicationEditLogRepository.findAll().size();

        // Create the MpoApplicationEditLog

        restMpoApplicationEditLogMockMvc.perform(post("/api/mpoApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoApplicationEditLog)))
                .andExpect(status().isCreated());

        // Validate the MpoApplicationEditLog in the database
        List<MpoApplicationEditLog> mpoApplicationEditLogs = mpoApplicationEditLogRepository.findAll();
        assertThat(mpoApplicationEditLogs).hasSize(databaseSizeBeforeCreate + 1);
        MpoApplicationEditLog testMpoApplicationEditLog = mpoApplicationEditLogs.get(mpoApplicationEditLogs.size() - 1);
        assertThat(testMpoApplicationEditLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMpoApplicationEditLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testMpoApplicationEditLog.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllMpoApplicationEditLogs() throws Exception {
        // Initialize the database
        mpoApplicationEditLogRepository.saveAndFlush(mpoApplicationEditLog);

        // Get all the mpoApplicationEditLogs
        restMpoApplicationEditLogMockMvc.perform(get("/api/mpoApplicationEditLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoApplicationEditLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMpoApplicationEditLog() throws Exception {
        // Initialize the database
        mpoApplicationEditLogRepository.saveAndFlush(mpoApplicationEditLog);

        // Get the mpoApplicationEditLog
        restMpoApplicationEditLogMockMvc.perform(get("/api/mpoApplicationEditLogs/{id}", mpoApplicationEditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoApplicationEditLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMpoApplicationEditLog() throws Exception {
        // Get the mpoApplicationEditLog
        restMpoApplicationEditLogMockMvc.perform(get("/api/mpoApplicationEditLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoApplicationEditLog() throws Exception {
        // Initialize the database
        mpoApplicationEditLogRepository.saveAndFlush(mpoApplicationEditLog);

		int databaseSizeBeforeUpdate = mpoApplicationEditLogRepository.findAll().size();

        // Update the mpoApplicationEditLog
        mpoApplicationEditLog.setStatus(UPDATED_STATUS);
        mpoApplicationEditLog.setRemarks(UPDATED_REMARKS);
        mpoApplicationEditLog.setDate(UPDATED_DATE);

        restMpoApplicationEditLogMockMvc.perform(put("/api/mpoApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoApplicationEditLog)))
                .andExpect(status().isOk());

        // Validate the MpoApplicationEditLog in the database
        List<MpoApplicationEditLog> mpoApplicationEditLogs = mpoApplicationEditLogRepository.findAll();
        assertThat(mpoApplicationEditLogs).hasSize(databaseSizeBeforeUpdate);
        MpoApplicationEditLog testMpoApplicationEditLog = mpoApplicationEditLogs.get(mpoApplicationEditLogs.size() - 1);
        assertThat(testMpoApplicationEditLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMpoApplicationEditLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testMpoApplicationEditLog.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteMpoApplicationEditLog() throws Exception {
        // Initialize the database
        mpoApplicationEditLogRepository.saveAndFlush(mpoApplicationEditLog);

		int databaseSizeBeforeDelete = mpoApplicationEditLogRepository.findAll().size();

        // Get the mpoApplicationEditLog
        restMpoApplicationEditLogMockMvc.perform(delete("/api/mpoApplicationEditLogs/{id}", mpoApplicationEditLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoApplicationEditLog> mpoApplicationEditLogs = mpoApplicationEditLogRepository.findAll();
        assertThat(mpoApplicationEditLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
