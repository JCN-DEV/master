package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TimeScaleApplication;
import gov.step.app.repository.TimeScaleApplicationRepository;
import gov.step.app.repository.search.TimeScaleApplicationSearchRepository;

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
 * Test class for the TimeScaleApplicationResource REST controller.
 *
 * @see TimeScaleApplicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TimeScaleApplicationResourceIntTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_INDEX_NO = "AAAAA";
    private static final String UPDATED_INDEX_NO = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_RESULATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RESULATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_AGENDA_NUMBER = 1;
    private static final Integer UPDATED_AGENDA_NUMBER = 2;

    private static final Boolean DEFAULT_WORKING_BREAK = false;
    private static final Boolean UPDATED_WORKING_BREAK = true;

    private static final LocalDate DEFAULT_WORKING_BREAK_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WORKING_BREAK_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_WORKING_BREAK_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WORKING_BREAK_END = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DISCIPLINARY_ACTION = false;
    private static final Boolean UPDATED_DISCIPLINARY_ACTION = true;
    private static final String DEFAULT_DIS_ACTION_CASE_NO = "AAAAA";
    private static final String UPDATED_DIS_ACTION_CASE_NO = "BBBBB";
    private static final String DEFAULT_DIS_ACTION_FILE_NAME = "AAAAA";
    private static final String UPDATED_DIS_ACTION_FILE_NAME = "BBBBB";

    @Inject
    private TimeScaleApplicationRepository timeScaleApplicationRepository;

    @Inject
    private TimeScaleApplicationSearchRepository timeScaleApplicationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimeScaleApplicationMockMvc;

    private TimeScaleApplication timeScaleApplication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeScaleApplicationResource timeScaleApplicationResource = new TimeScaleApplicationResource();
        ReflectionTestUtils.setField(timeScaleApplicationResource, "timeScaleApplicationRepository", timeScaleApplicationRepository);
        ReflectionTestUtils.setField(timeScaleApplicationResource, "timeScaleApplicationSearchRepository", timeScaleApplicationSearchRepository);
        this.restTimeScaleApplicationMockMvc = MockMvcBuilders.standaloneSetup(timeScaleApplicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timeScaleApplication = new TimeScaleApplication();
        //timeScaleApplication.setDate(DEFAULT_DATE);
        timeScaleApplication.setIndexNo(DEFAULT_INDEX_NO);
        timeScaleApplication.setStatus(DEFAULT_STATUS);
        timeScaleApplication.setResulationDate(DEFAULT_RESULATION_DATE);
        timeScaleApplication.setAgendaNumber(DEFAULT_AGENDA_NUMBER);
        timeScaleApplication.setWorkingBreak(DEFAULT_WORKING_BREAK);
        timeScaleApplication.setWorkingBreakStart(DEFAULT_WORKING_BREAK_START);
        timeScaleApplication.setWorkingBreakEnd(DEFAULT_WORKING_BREAK_END);
        timeScaleApplication.setDisciplinaryAction(DEFAULT_DISCIPLINARY_ACTION);
        timeScaleApplication.setDisActionCaseNo(DEFAULT_DIS_ACTION_CASE_NO);
        timeScaleApplication.setDisActionFileName(DEFAULT_DIS_ACTION_FILE_NAME);
    }

    @Test
    @Transactional
    public void createTimeScaleApplication() throws Exception {
        int databaseSizeBeforeCreate = timeScaleApplicationRepository.findAll().size();

        // Create the TimeScaleApplication

        restTimeScaleApplicationMockMvc.perform(post("/api/timeScaleApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeScaleApplication)))
                .andExpect(status().isCreated());

        // Validate the TimeScaleApplication in the database
        List<TimeScaleApplication> timeScaleApplications = timeScaleApplicationRepository.findAll();
        assertThat(timeScaleApplications).hasSize(databaseSizeBeforeCreate + 1);
        TimeScaleApplication testTimeScaleApplication = timeScaleApplications.get(timeScaleApplications.size() - 1);
        assertThat(testTimeScaleApplication.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTimeScaleApplication.getIndexNo()).isEqualTo(DEFAULT_INDEX_NO);
        assertThat(testTimeScaleApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTimeScaleApplication.getResulationDate()).isEqualTo(DEFAULT_RESULATION_DATE);
        assertThat(testTimeScaleApplication.getAgendaNumber()).isEqualTo(DEFAULT_AGENDA_NUMBER);
        assertThat(testTimeScaleApplication.getWorkingBreak()).isEqualTo(DEFAULT_WORKING_BREAK);
        assertThat(testTimeScaleApplication.getWorkingBreakStart()).isEqualTo(DEFAULT_WORKING_BREAK_START);
        assertThat(testTimeScaleApplication.getWorkingBreakEnd()).isEqualTo(DEFAULT_WORKING_BREAK_END);
        assertThat(testTimeScaleApplication.getDisciplinaryAction()).isEqualTo(DEFAULT_DISCIPLINARY_ACTION);
        assertThat(testTimeScaleApplication.getDisActionCaseNo()).isEqualTo(DEFAULT_DIS_ACTION_CASE_NO);
        assertThat(testTimeScaleApplication.getDisActionFileName()).isEqualTo(DEFAULT_DIS_ACTION_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllTimeScaleApplications() throws Exception {
        // Initialize the database
        timeScaleApplicationRepository.saveAndFlush(timeScaleApplication);

        // Get all the timeScaleApplications
        restTimeScaleApplicationMockMvc.perform(get("/api/timeScaleApplications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timeScaleApplication.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].indexNo").value(hasItem(DEFAULT_INDEX_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].resulationDate").value(hasItem(DEFAULT_RESULATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].agendaNumber").value(hasItem(DEFAULT_AGENDA_NUMBER)))
                .andExpect(jsonPath("$.[*].workingBreak").value(hasItem(DEFAULT_WORKING_BREAK.booleanValue())))
                .andExpect(jsonPath("$.[*].workingBreakStart").value(hasItem(DEFAULT_WORKING_BREAK_START.toString())))
                .andExpect(jsonPath("$.[*].workingBreakEnd").value(hasItem(DEFAULT_WORKING_BREAK_END.toString())))
                .andExpect(jsonPath("$.[*].disciplinaryAction").value(hasItem(DEFAULT_DISCIPLINARY_ACTION.booleanValue())))
                .andExpect(jsonPath("$.[*].disActionCaseNo").value(hasItem(DEFAULT_DIS_ACTION_CASE_NO.toString())))
                .andExpect(jsonPath("$.[*].disActionFileName").value(hasItem(DEFAULT_DIS_ACTION_FILE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTimeScaleApplication() throws Exception {
        // Initialize the database
        timeScaleApplicationRepository.saveAndFlush(timeScaleApplication);

        // Get the timeScaleApplication
        restTimeScaleApplicationMockMvc.perform(get("/api/timeScaleApplications/{id}", timeScaleApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(timeScaleApplication.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.indexNo").value(DEFAULT_INDEX_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.resulationDate").value(DEFAULT_RESULATION_DATE.toString()))
            .andExpect(jsonPath("$.agendaNumber").value(DEFAULT_AGENDA_NUMBER))
            .andExpect(jsonPath("$.workingBreak").value(DEFAULT_WORKING_BREAK.booleanValue()))
            .andExpect(jsonPath("$.workingBreakStart").value(DEFAULT_WORKING_BREAK_START.toString()))
            .andExpect(jsonPath("$.workingBreakEnd").value(DEFAULT_WORKING_BREAK_END.toString()))
            .andExpect(jsonPath("$.disciplinaryAction").value(DEFAULT_DISCIPLINARY_ACTION.booleanValue()))
            .andExpect(jsonPath("$.disActionCaseNo").value(DEFAULT_DIS_ACTION_CASE_NO.toString()))
            .andExpect(jsonPath("$.disActionFileName").value(DEFAULT_DIS_ACTION_FILE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeScaleApplication() throws Exception {
        // Get the timeScaleApplication
        restTimeScaleApplicationMockMvc.perform(get("/api/timeScaleApplications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeScaleApplication() throws Exception {
        // Initialize the database
        timeScaleApplicationRepository.saveAndFlush(timeScaleApplication);

		int databaseSizeBeforeUpdate = timeScaleApplicationRepository.findAll().size();

        // Update the timeScaleApplication
        //timeScaleApplication.setDate(UPDATED_DATE);
        timeScaleApplication.setIndexNo(UPDATED_INDEX_NO);
        timeScaleApplication.setStatus(UPDATED_STATUS);
        timeScaleApplication.setResulationDate(UPDATED_RESULATION_DATE);
        timeScaleApplication.setAgendaNumber(UPDATED_AGENDA_NUMBER);
        timeScaleApplication.setWorkingBreak(UPDATED_WORKING_BREAK);
        timeScaleApplication.setWorkingBreakStart(UPDATED_WORKING_BREAK_START);
        timeScaleApplication.setWorkingBreakEnd(UPDATED_WORKING_BREAK_END);
        timeScaleApplication.setDisciplinaryAction(UPDATED_DISCIPLINARY_ACTION);
        timeScaleApplication.setDisActionCaseNo(UPDATED_DIS_ACTION_CASE_NO);
        timeScaleApplication.setDisActionFileName(UPDATED_DIS_ACTION_FILE_NAME);

        restTimeScaleApplicationMockMvc.perform(put("/api/timeScaleApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeScaleApplication)))
                .andExpect(status().isOk());

        // Validate the TimeScaleApplication in the database
        List<TimeScaleApplication> timeScaleApplications = timeScaleApplicationRepository.findAll();
        assertThat(timeScaleApplications).hasSize(databaseSizeBeforeUpdate);
        TimeScaleApplication testTimeScaleApplication = timeScaleApplications.get(timeScaleApplications.size() - 1);
        assertThat(testTimeScaleApplication.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTimeScaleApplication.getIndexNo()).isEqualTo(UPDATED_INDEX_NO);
        assertThat(testTimeScaleApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTimeScaleApplication.getResulationDate()).isEqualTo(UPDATED_RESULATION_DATE);
        assertThat(testTimeScaleApplication.getAgendaNumber()).isEqualTo(UPDATED_AGENDA_NUMBER);
        assertThat(testTimeScaleApplication.getWorkingBreak()).isEqualTo(UPDATED_WORKING_BREAK);
        assertThat(testTimeScaleApplication.getWorkingBreakStart()).isEqualTo(UPDATED_WORKING_BREAK_START);
        assertThat(testTimeScaleApplication.getWorkingBreakEnd()).isEqualTo(UPDATED_WORKING_BREAK_END);
        assertThat(testTimeScaleApplication.getDisciplinaryAction()).isEqualTo(UPDATED_DISCIPLINARY_ACTION);
        assertThat(testTimeScaleApplication.getDisActionCaseNo()).isEqualTo(UPDATED_DIS_ACTION_CASE_NO);
        assertThat(testTimeScaleApplication.getDisActionFileName()).isEqualTo(UPDATED_DIS_ACTION_FILE_NAME);
    }

    @Test
    @Transactional
    public void deleteTimeScaleApplication() throws Exception {
        // Initialize the database
        timeScaleApplicationRepository.saveAndFlush(timeScaleApplication);

		int databaseSizeBeforeDelete = timeScaleApplicationRepository.findAll().size();

        // Get the timeScaleApplication
        restTimeScaleApplicationMockMvc.perform(delete("/api/timeScaleApplications/{id}", timeScaleApplication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeScaleApplication> timeScaleApplications = timeScaleApplicationRepository.findAll();
        assertThat(timeScaleApplications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
