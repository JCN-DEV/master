package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InformationCorrectionEditLog;
import gov.step.app.repository.InformationCorrectionEditLogRepository;
import gov.step.app.repository.search.InformationCorrectionEditLogSearchRepository;

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
 * Test class for the InformationCorrectionEditLogResource REST controller.
 *
 * @see InformationCorrectionEditLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InformationCorrectionEditLogResourceIntTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private InformationCorrectionEditLogRepository informationCorrectionEditLogRepository;

    @Inject
    private InformationCorrectionEditLogSearchRepository informationCorrectionEditLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInformationCorrectionEditLogMockMvc;

    private InformationCorrectionEditLog informationCorrectionEditLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InformationCorrectionEditLogResource informationCorrectionEditLogResource = new InformationCorrectionEditLogResource();
        ReflectionTestUtils.setField(informationCorrectionEditLogResource, "informationCorrectionEditLogRepository", informationCorrectionEditLogRepository);
        ReflectionTestUtils.setField(informationCorrectionEditLogResource, "informationCorrectionEditLogSearchRepository", informationCorrectionEditLogSearchRepository);
        this.restInformationCorrectionEditLogMockMvc = MockMvcBuilders.standaloneSetup(informationCorrectionEditLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        informationCorrectionEditLog = new InformationCorrectionEditLog();
        informationCorrectionEditLog.setStatus(DEFAULT_STATUS);
        informationCorrectionEditLog.setRemarks(DEFAULT_REMARKS);
        informationCorrectionEditLog.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createInformationCorrectionEditLog() throws Exception {
        int databaseSizeBeforeCreate = informationCorrectionEditLogRepository.findAll().size();

        // Create the InformationCorrectionEditLog

        restInformationCorrectionEditLogMockMvc.perform(post("/api/informationCorrectionEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(informationCorrectionEditLog)))
                .andExpect(status().isCreated());

        // Validate the InformationCorrectionEditLog in the database
        List<InformationCorrectionEditLog> informationCorrectionEditLogs = informationCorrectionEditLogRepository.findAll();
        assertThat(informationCorrectionEditLogs).hasSize(databaseSizeBeforeCreate + 1);
        InformationCorrectionEditLog testInformationCorrectionEditLog = informationCorrectionEditLogs.get(informationCorrectionEditLogs.size() - 1);
        assertThat(testInformationCorrectionEditLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInformationCorrectionEditLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testInformationCorrectionEditLog.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllInformationCorrectionEditLogs() throws Exception {
        // Initialize the database
        informationCorrectionEditLogRepository.saveAndFlush(informationCorrectionEditLog);

        // Get all the informationCorrectionEditLogs
        restInformationCorrectionEditLogMockMvc.perform(get("/api/informationCorrectionEditLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(informationCorrectionEditLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInformationCorrectionEditLog() throws Exception {
        // Initialize the database
        informationCorrectionEditLogRepository.saveAndFlush(informationCorrectionEditLog);

        // Get the informationCorrectionEditLog
        restInformationCorrectionEditLogMockMvc.perform(get("/api/informationCorrectionEditLogs/{id}", informationCorrectionEditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(informationCorrectionEditLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInformationCorrectionEditLog() throws Exception {
        // Get the informationCorrectionEditLog
        restInformationCorrectionEditLogMockMvc.perform(get("/api/informationCorrectionEditLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformationCorrectionEditLog() throws Exception {
        // Initialize the database
        informationCorrectionEditLogRepository.saveAndFlush(informationCorrectionEditLog);

		int databaseSizeBeforeUpdate = informationCorrectionEditLogRepository.findAll().size();

        // Update the informationCorrectionEditLog
        informationCorrectionEditLog.setStatus(UPDATED_STATUS);
        informationCorrectionEditLog.setRemarks(UPDATED_REMARKS);
        informationCorrectionEditLog.setDate(UPDATED_DATE);

        restInformationCorrectionEditLogMockMvc.perform(put("/api/informationCorrectionEditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(informationCorrectionEditLog)))
                .andExpect(status().isOk());

        // Validate the InformationCorrectionEditLog in the database
        List<InformationCorrectionEditLog> informationCorrectionEditLogs = informationCorrectionEditLogRepository.findAll();
        assertThat(informationCorrectionEditLogs).hasSize(databaseSizeBeforeUpdate);
        InformationCorrectionEditLog testInformationCorrectionEditLog = informationCorrectionEditLogs.get(informationCorrectionEditLogs.size() - 1);
        assertThat(testInformationCorrectionEditLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInformationCorrectionEditLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testInformationCorrectionEditLog.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteInformationCorrectionEditLog() throws Exception {
        // Initialize the database
        informationCorrectionEditLogRepository.saveAndFlush(informationCorrectionEditLog);

		int databaseSizeBeforeDelete = informationCorrectionEditLogRepository.findAll().size();

        // Get the informationCorrectionEditLog
        restInformationCorrectionEditLogMockMvc.perform(delete("/api/informationCorrectionEditLogs/{id}", informationCorrectionEditLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InformationCorrectionEditLog> informationCorrectionEditLogs = informationCorrectionEditLogRepository.findAll();
        assertThat(informationCorrectionEditLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
