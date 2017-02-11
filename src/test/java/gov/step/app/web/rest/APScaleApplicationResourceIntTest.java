package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.APScaleApplication;
import gov.step.app.repository.APScaleApplicationRepository;
import gov.step.app.repository.search.APScaleApplicationSearchRepository;

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
 * Test class for the APScaleApplicationResource REST controller.
 *
 * @see APScaleApplicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class APScaleApplicationResourceIntTest {


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
    private APScaleApplicationRepository aPScaleApplicationRepository;

    @Inject
    private APScaleApplicationSearchRepository aPScaleApplicationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAPScaleApplicationMockMvc;

    private APScaleApplication aPScaleApplication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        APScaleApplicationResource aPScaleApplicationResource = new APScaleApplicationResource();
        ReflectionTestUtils.setField(aPScaleApplicationResource, "aPScaleApplicationRepository", aPScaleApplicationRepository);
        ReflectionTestUtils.setField(aPScaleApplicationResource, "aPScaleApplicationSearchRepository", aPScaleApplicationSearchRepository);
        this.restAPScaleApplicationMockMvc = MockMvcBuilders.standaloneSetup(aPScaleApplicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aPScaleApplication = new APScaleApplication();
        aPScaleApplication.setDate(DEFAULT_DATE);
        aPScaleApplication.setIndexNo(DEFAULT_INDEX_NO);
        aPScaleApplication.setStatus(DEFAULT_STATUS);
        aPScaleApplication.setResulationDate(DEFAULT_RESULATION_DATE);
        aPScaleApplication.setAgendaNumber(DEFAULT_AGENDA_NUMBER);
        aPScaleApplication.setWorkingBreak(DEFAULT_WORKING_BREAK);
        aPScaleApplication.setWorkingBreakStart(DEFAULT_WORKING_BREAK_START);
        aPScaleApplication.setWorkingBreakEnd(DEFAULT_WORKING_BREAK_END);
        aPScaleApplication.setDisciplinaryAction(DEFAULT_DISCIPLINARY_ACTION);
        aPScaleApplication.setDisActionCaseNo(DEFAULT_DIS_ACTION_CASE_NO);
        aPScaleApplication.setDisActionFileName(DEFAULT_DIS_ACTION_FILE_NAME);
    }

    @Test
    @Transactional
    public void createAPScaleApplication() throws Exception {
        int databaseSizeBeforeCreate = aPScaleApplicationRepository.findAll().size();

        // Create the APScaleApplication

        restAPScaleApplicationMockMvc.perform(post("/api/aPScaleApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aPScaleApplication)))
                .andExpect(status().isCreated());

        // Validate the APScaleApplication in the database
        List<APScaleApplication> aPScaleApplications = aPScaleApplicationRepository.findAll();
        assertThat(aPScaleApplications).hasSize(databaseSizeBeforeCreate + 1);
        APScaleApplication testAPScaleApplication = aPScaleApplications.get(aPScaleApplications.size() - 1);
        assertThat(testAPScaleApplication.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAPScaleApplication.getIndexNo()).isEqualTo(DEFAULT_INDEX_NO);
        assertThat(testAPScaleApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAPScaleApplication.getResulationDate()).isEqualTo(DEFAULT_RESULATION_DATE);
        assertThat(testAPScaleApplication.getAgendaNumber()).isEqualTo(DEFAULT_AGENDA_NUMBER);
        assertThat(testAPScaleApplication.getWorkingBreak()).isEqualTo(DEFAULT_WORKING_BREAK);
        assertThat(testAPScaleApplication.getWorkingBreakStart()).isEqualTo(DEFAULT_WORKING_BREAK_START);
        assertThat(testAPScaleApplication.getWorkingBreakEnd()).isEqualTo(DEFAULT_WORKING_BREAK_END);
        assertThat(testAPScaleApplication.getDisciplinaryAction()).isEqualTo(DEFAULT_DISCIPLINARY_ACTION);
        assertThat(testAPScaleApplication.getDisActionCaseNo()).isEqualTo(DEFAULT_DIS_ACTION_CASE_NO);
        assertThat(testAPScaleApplication.getDisActionFileName()).isEqualTo(DEFAULT_DIS_ACTION_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllAPScaleApplications() throws Exception {
        // Initialize the database
        aPScaleApplicationRepository.saveAndFlush(aPScaleApplication);

        // Get all the aPScaleApplications
        restAPScaleApplicationMockMvc.perform(get("/api/aPScaleApplications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aPScaleApplication.getId().intValue())))
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
    public void getAPScaleApplication() throws Exception {
        // Initialize the database
        aPScaleApplicationRepository.saveAndFlush(aPScaleApplication);

        // Get the aPScaleApplication
        restAPScaleApplicationMockMvc.perform(get("/api/aPScaleApplications/{id}", aPScaleApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aPScaleApplication.getId().intValue()))
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
    public void getNonExistingAPScaleApplication() throws Exception {
        // Get the aPScaleApplication
        restAPScaleApplicationMockMvc.perform(get("/api/aPScaleApplications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAPScaleApplication() throws Exception {
        // Initialize the database
        aPScaleApplicationRepository.saveAndFlush(aPScaleApplication);

		int databaseSizeBeforeUpdate = aPScaleApplicationRepository.findAll().size();

        // Update the aPScaleApplication
        aPScaleApplication.setDate(UPDATED_DATE);
        aPScaleApplication.setIndexNo(UPDATED_INDEX_NO);
        aPScaleApplication.setStatus(UPDATED_STATUS);
        aPScaleApplication.setResulationDate(UPDATED_RESULATION_DATE);
        aPScaleApplication.setAgendaNumber(UPDATED_AGENDA_NUMBER);
        aPScaleApplication.setWorkingBreak(UPDATED_WORKING_BREAK);
        aPScaleApplication.setWorkingBreakStart(UPDATED_WORKING_BREAK_START);
        aPScaleApplication.setWorkingBreakEnd(UPDATED_WORKING_BREAK_END);
        aPScaleApplication.setDisciplinaryAction(UPDATED_DISCIPLINARY_ACTION);
        aPScaleApplication.setDisActionCaseNo(UPDATED_DIS_ACTION_CASE_NO);
        aPScaleApplication.setDisActionFileName(UPDATED_DIS_ACTION_FILE_NAME);

        restAPScaleApplicationMockMvc.perform(put("/api/aPScaleApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aPScaleApplication)))
                .andExpect(status().isOk());

        // Validate the APScaleApplication in the database
        List<APScaleApplication> aPScaleApplications = aPScaleApplicationRepository.findAll();
        assertThat(aPScaleApplications).hasSize(databaseSizeBeforeUpdate);
        APScaleApplication testAPScaleApplication = aPScaleApplications.get(aPScaleApplications.size() - 1);
        assertThat(testAPScaleApplication.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAPScaleApplication.getIndexNo()).isEqualTo(UPDATED_INDEX_NO);
        assertThat(testAPScaleApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAPScaleApplication.getResulationDate()).isEqualTo(UPDATED_RESULATION_DATE);
        assertThat(testAPScaleApplication.getAgendaNumber()).isEqualTo(UPDATED_AGENDA_NUMBER);
        assertThat(testAPScaleApplication.getWorkingBreak()).isEqualTo(UPDATED_WORKING_BREAK);
        assertThat(testAPScaleApplication.getWorkingBreakStart()).isEqualTo(UPDATED_WORKING_BREAK_START);
        assertThat(testAPScaleApplication.getWorkingBreakEnd()).isEqualTo(UPDATED_WORKING_BREAK_END);
        assertThat(testAPScaleApplication.getDisciplinaryAction()).isEqualTo(UPDATED_DISCIPLINARY_ACTION);
        assertThat(testAPScaleApplication.getDisActionCaseNo()).isEqualTo(UPDATED_DIS_ACTION_CASE_NO);
        assertThat(testAPScaleApplication.getDisActionFileName()).isEqualTo(UPDATED_DIS_ACTION_FILE_NAME);
    }

    @Test
    @Transactional
    public void deleteAPScaleApplication() throws Exception {
        // Initialize the database
        aPScaleApplicationRepository.saveAndFlush(aPScaleApplication);

		int databaseSizeBeforeDelete = aPScaleApplicationRepository.findAll().size();

        // Get the aPScaleApplication
        restAPScaleApplicationMockMvc.perform(delete("/api/aPScaleApplications/{id}", aPScaleApplication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<APScaleApplication> aPScaleApplications = aPScaleApplicationRepository.findAll();
        assertThat(aPScaleApplications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
