package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InformationCorrectionStatusLog;
import gov.step.app.repository.InformationCorrectionStatusLogRepository;
import gov.step.app.repository.search.InformationCorrectionStatusLogSearchRepository;

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
 * Test class for the InformationCorrectionStatusLogResource REST controller.
 *
 * @see InformationCorrectionStatusLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InformationCorrectionStatusLogResourceIntTest {


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
    private InformationCorrectionStatusLogRepository informationCorrectionStatusLogRepository;

    @Inject
    private InformationCorrectionStatusLogSearchRepository informationCorrectionStatusLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInformationCorrectionStatusLogMockMvc;

    private InformationCorrectionStatusLog informationCorrectionStatusLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InformationCorrectionStatusLogResource informationCorrectionStatusLogResource = new InformationCorrectionStatusLogResource();
        ReflectionTestUtils.setField(informationCorrectionStatusLogResource, "informationCorrectionStatusLogRepository", informationCorrectionStatusLogRepository);
        ReflectionTestUtils.setField(informationCorrectionStatusLogResource, "informationCorrectionStatusLogSearchRepository", informationCorrectionStatusLogSearchRepository);
        this.restInformationCorrectionStatusLogMockMvc = MockMvcBuilders.standaloneSetup(informationCorrectionStatusLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        informationCorrectionStatusLog = new InformationCorrectionStatusLog();
        informationCorrectionStatusLog.setStatus(DEFAULT_STATUS);
        informationCorrectionStatusLog.setRemarks(DEFAULT_REMARKS);
        informationCorrectionStatusLog.setFromDate(DEFAULT_FROM_DATE);
        informationCorrectionStatusLog.setToDate(DEFAULT_TO_DATE);
        informationCorrectionStatusLog.setCause(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void createInformationCorrectionStatusLog() throws Exception {
        int databaseSizeBeforeCreate = informationCorrectionStatusLogRepository.findAll().size();

        // Create the InformationCorrectionStatusLog

        restInformationCorrectionStatusLogMockMvc.perform(post("/api/informationCorrectionStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(informationCorrectionStatusLog)))
                .andExpect(status().isCreated());

        // Validate the InformationCorrectionStatusLog in the database
        List<InformationCorrectionStatusLog> informationCorrectionStatusLogs = informationCorrectionStatusLogRepository.findAll();
        assertThat(informationCorrectionStatusLogs).hasSize(databaseSizeBeforeCreate + 1);
        InformationCorrectionStatusLog testInformationCorrectionStatusLog = informationCorrectionStatusLogs.get(informationCorrectionStatusLogs.size() - 1);
        assertThat(testInformationCorrectionStatusLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInformationCorrectionStatusLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testInformationCorrectionStatusLog.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testInformationCorrectionStatusLog.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testInformationCorrectionStatusLog.getCause()).isEqualTo(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void getAllInformationCorrectionStatusLogs() throws Exception {
        // Initialize the database
        informationCorrectionStatusLogRepository.saveAndFlush(informationCorrectionStatusLog);

        // Get all the informationCorrectionStatusLogs
        restInformationCorrectionStatusLogMockMvc.perform(get("/api/informationCorrectionStatusLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(informationCorrectionStatusLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE.toString())));
    }

    @Test
    @Transactional
    public void getInformationCorrectionStatusLog() throws Exception {
        // Initialize the database
        informationCorrectionStatusLogRepository.saveAndFlush(informationCorrectionStatusLog);

        // Get the informationCorrectionStatusLog
        restInformationCorrectionStatusLogMockMvc.perform(get("/api/informationCorrectionStatusLogs/{id}", informationCorrectionStatusLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(informationCorrectionStatusLog.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInformationCorrectionStatusLog() throws Exception {
        // Get the informationCorrectionStatusLog
        restInformationCorrectionStatusLogMockMvc.perform(get("/api/informationCorrectionStatusLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformationCorrectionStatusLog() throws Exception {
        // Initialize the database
        informationCorrectionStatusLogRepository.saveAndFlush(informationCorrectionStatusLog);

		int databaseSizeBeforeUpdate = informationCorrectionStatusLogRepository.findAll().size();

        // Update the informationCorrectionStatusLog
        informationCorrectionStatusLog.setStatus(UPDATED_STATUS);
        informationCorrectionStatusLog.setRemarks(UPDATED_REMARKS);
        informationCorrectionStatusLog.setFromDate(UPDATED_FROM_DATE);
        informationCorrectionStatusLog.setToDate(UPDATED_TO_DATE);
        informationCorrectionStatusLog.setCause(UPDATED_CAUSE);

        restInformationCorrectionStatusLogMockMvc.perform(put("/api/informationCorrectionStatusLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(informationCorrectionStatusLog)))
                .andExpect(status().isOk());

        // Validate the InformationCorrectionStatusLog in the database
        List<InformationCorrectionStatusLog> informationCorrectionStatusLogs = informationCorrectionStatusLogRepository.findAll();
        assertThat(informationCorrectionStatusLogs).hasSize(databaseSizeBeforeUpdate);
        InformationCorrectionStatusLog testInformationCorrectionStatusLog = informationCorrectionStatusLogs.get(informationCorrectionStatusLogs.size() - 1);
        assertThat(testInformationCorrectionStatusLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInformationCorrectionStatusLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testInformationCorrectionStatusLog.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testInformationCorrectionStatusLog.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testInformationCorrectionStatusLog.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    public void deleteInformationCorrectionStatusLog() throws Exception {
        // Initialize the database
        informationCorrectionStatusLogRepository.saveAndFlush(informationCorrectionStatusLog);

		int databaseSizeBeforeDelete = informationCorrectionStatusLogRepository.findAll().size();

        // Get the informationCorrectionStatusLog
        restInformationCorrectionStatusLogMockMvc.perform(delete("/api/informationCorrectionStatusLogs/{id}", informationCorrectionStatusLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InformationCorrectionStatusLog> informationCorrectionStatusLogs = informationCorrectionStatusLogRepository.findAll();
        assertThat(informationCorrectionStatusLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
