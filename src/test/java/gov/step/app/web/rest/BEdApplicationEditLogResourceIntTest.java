package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.BEdApplicationEditLog;
import gov.step.app.repository.BEdApplicationEditLogRepository;
import gov.step.app.repository.search.BEdApplicationEditLogSearchRepository;

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
 * Test class for the BEdApplicationEditLogResource REST controller.
 *
 * @see BEdApplicationEditLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BEdApplicationEditLogResourceIntTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private BEdApplicationEditLogRepository bEdApplicationEditLogRepository;

    @Inject
    private BEdApplicationEditLogSearchRepository bEdApplicationEditLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBEdApplicationEditLogMockMvc;

    private BEdApplicationEditLog bEdApplicationEditLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BEdApplicationEditLogResource bEdApplicationEditLogResource = new BEdApplicationEditLogResource();
        ReflectionTestUtils.setField(bEdApplicationEditLogResource, "bEdApplicationEditLogRepository", bEdApplicationEditLogRepository);
        ReflectionTestUtils.setField(bEdApplicationEditLogResource, "bEdApplicationEditLogSearchRepository", bEdApplicationEditLogSearchRepository);
        this.restBEdApplicationEditLogMockMvc = MockMvcBuilders.standaloneSetup(bEdApplicationEditLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bEdApplicationEditLog = new BEdApplicationEditLog();
        bEdApplicationEditLog.setStatus(DEFAULT_STATUS);
        bEdApplicationEditLog.setRemarks(DEFAULT_REMARKS);
        bEdApplicationEditLog.setCreated_date(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createBEdApplicationEditLog() throws Exception {
        int databaseSizeBeforeCreate = bEdApplicationEditLogRepository.findAll().size();

        // Create the BEdApplicationEditLog

        restBEdApplicationEditLogMockMvc.perform(post("/api/bEdApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bEdApplicationEditLog)))
                .andExpect(status().isCreated());

        // Validate the BEdApplicationEditLog in the database
        List<BEdApplicationEditLog> bEdApplicationEditLogs = bEdApplicationEditLogRepository.findAll();
        assertThat(bEdApplicationEditLogs).hasSize(databaseSizeBeforeCreate + 1);
        BEdApplicationEditLog testBEdApplicationEditLog = bEdApplicationEditLogs.get(bEdApplicationEditLogs.size() - 1);
        assertThat(testBEdApplicationEditLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBEdApplicationEditLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testBEdApplicationEditLog.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllBEdApplicationEditLogs() throws Exception {
        // Initialize the database
        bEdApplicationEditLogRepository.saveAndFlush(bEdApplicationEditLog);

        // Get all the bEdApplicationEditLogs
        restBEdApplicationEditLogMockMvc.perform(get("/api/bEdApplicationEditLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bEdApplicationEditLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getBEdApplicationEditLog() throws Exception {
        // Initialize the database
        bEdApplicationEditLogRepository.saveAndFlush(bEdApplicationEditLog);

        // Get the bEdApplicationEditLog
        restBEdApplicationEditLogMockMvc.perform(get("/api/bEdApplicationEditLogs/{id}", bEdApplicationEditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bEdApplicationEditLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBEdApplicationEditLog() throws Exception {
        // Get the bEdApplicationEditLog
        restBEdApplicationEditLogMockMvc.perform(get("/api/bEdApplicationEditLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBEdApplicationEditLog() throws Exception {
        // Initialize the database
        bEdApplicationEditLogRepository.saveAndFlush(bEdApplicationEditLog);

		int databaseSizeBeforeUpdate = bEdApplicationEditLogRepository.findAll().size();

        // Update the bEdApplicationEditLog
        bEdApplicationEditLog.setStatus(UPDATED_STATUS);
        bEdApplicationEditLog.setRemarks(UPDATED_REMARKS);
        bEdApplicationEditLog.setCreated_date(UPDATED_CREATED_DATE);

        restBEdApplicationEditLogMockMvc.perform(put("/api/bEdApplicationEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bEdApplicationEditLog)))
                .andExpect(status().isOk());

        // Validate the BEdApplicationEditLog in the database
        List<BEdApplicationEditLog> bEdApplicationEditLogs = bEdApplicationEditLogRepository.findAll();
        assertThat(bEdApplicationEditLogs).hasSize(databaseSizeBeforeUpdate);
        BEdApplicationEditLog testBEdApplicationEditLog = bEdApplicationEditLogs.get(bEdApplicationEditLogs.size() - 1);
        assertThat(testBEdApplicationEditLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBEdApplicationEditLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testBEdApplicationEditLog.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void deleteBEdApplicationEditLog() throws Exception {
        // Initialize the database
        bEdApplicationEditLogRepository.saveAndFlush(bEdApplicationEditLog);

		int databaseSizeBeforeDelete = bEdApplicationEditLogRepository.findAll().size();

        // Get the bEdApplicationEditLog
        restBEdApplicationEditLogMockMvc.perform(delete("/api/bEdApplicationEditLogs/{id}", bEdApplicationEditLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BEdApplicationEditLog> bEdApplicationEditLogs = bEdApplicationEditLogRepository.findAll();
        assertThat(bEdApplicationEditLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
