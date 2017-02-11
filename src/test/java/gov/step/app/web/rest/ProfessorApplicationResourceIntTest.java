package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.ProfessorApplication;
import gov.step.app.repository.ProfessorApplicationRepository;
import gov.step.app.repository.search.ProfessorApplicationSearchRepository;

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
 * Test class for the ProfessorApplicationResource REST controller.
 *
 * @see ProfessorApplicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProfessorApplicationResourceIntTest {


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
    private ProfessorApplicationRepository professorApplicationRepository;

    @Inject
    private ProfessorApplicationSearchRepository professorApplicationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProfessorApplicationMockMvc;

    private ProfessorApplication professorApplication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfessorApplicationResource professorApplicationResource = new ProfessorApplicationResource();
        ReflectionTestUtils.setField(professorApplicationResource, "professorApplicationRepository", professorApplicationRepository);
        ReflectionTestUtils.setField(professorApplicationResource, "professorApplicationSearchRepository", professorApplicationSearchRepository);
        this.restProfessorApplicationMockMvc = MockMvcBuilders.standaloneSetup(professorApplicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        professorApplication = new ProfessorApplication();
        professorApplication.setDate(DEFAULT_DATE);
        professorApplication.setIndexNo(DEFAULT_INDEX_NO);
        professorApplication.setStatus(DEFAULT_STATUS);
        professorApplication.setResulationDate(DEFAULT_RESULATION_DATE);
        professorApplication.setAgendaNumber(DEFAULT_AGENDA_NUMBER);
        professorApplication.setWorkingBreak(DEFAULT_WORKING_BREAK);
        professorApplication.setWorkingBreakStart(DEFAULT_WORKING_BREAK_START);
        professorApplication.setWorkingBreakEnd(DEFAULT_WORKING_BREAK_END);
        professorApplication.setDisciplinaryAction(DEFAULT_DISCIPLINARY_ACTION);
        professorApplication.setDisActionCaseNo(DEFAULT_DIS_ACTION_CASE_NO);
        professorApplication.setDisActionFileName(DEFAULT_DIS_ACTION_FILE_NAME);
    }

    @Test
    @Transactional
    public void createProfessorApplication() throws Exception {
        int databaseSizeBeforeCreate = professorApplicationRepository.findAll().size();

        // Create the ProfessorApplication

        restProfessorApplicationMockMvc.perform(post("/api/professorApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorApplication)))
                .andExpect(status().isCreated());

        // Validate the ProfessorApplication in the database
        List<ProfessorApplication> professorApplications = professorApplicationRepository.findAll();
        assertThat(professorApplications).hasSize(databaseSizeBeforeCreate + 1);
        ProfessorApplication testProfessorApplication = professorApplications.get(professorApplications.size() - 1);
        assertThat(testProfessorApplication.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testProfessorApplication.getIndexNo()).isEqualTo(DEFAULT_INDEX_NO);
        assertThat(testProfessorApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProfessorApplication.getResulationDate()).isEqualTo(DEFAULT_RESULATION_DATE);
        assertThat(testProfessorApplication.getAgendaNumber()).isEqualTo(DEFAULT_AGENDA_NUMBER);
        assertThat(testProfessorApplication.getWorkingBreak()).isEqualTo(DEFAULT_WORKING_BREAK);
        assertThat(testProfessorApplication.getWorkingBreakStart()).isEqualTo(DEFAULT_WORKING_BREAK_START);
        assertThat(testProfessorApplication.getWorkingBreakEnd()).isEqualTo(DEFAULT_WORKING_BREAK_END);
        assertThat(testProfessorApplication.getDisciplinaryAction()).isEqualTo(DEFAULT_DISCIPLINARY_ACTION);
        assertThat(testProfessorApplication.getDisActionCaseNo()).isEqualTo(DEFAULT_DIS_ACTION_CASE_NO);
        assertThat(testProfessorApplication.getDisActionFileName()).isEqualTo(DEFAULT_DIS_ACTION_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllProfessorApplications() throws Exception {
        // Initialize the database
        professorApplicationRepository.saveAndFlush(professorApplication);

        // Get all the professorApplications
        restProfessorApplicationMockMvc.perform(get("/api/professorApplications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(professorApplication.getId().intValue())))
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
    public void getProfessorApplication() throws Exception {
        // Initialize the database
        professorApplicationRepository.saveAndFlush(professorApplication);

        // Get the professorApplication
        restProfessorApplicationMockMvc.perform(get("/api/professorApplications/{id}", professorApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(professorApplication.getId().intValue()))
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
    public void getNonExistingProfessorApplication() throws Exception {
        // Get the professorApplication
        restProfessorApplicationMockMvc.perform(get("/api/professorApplications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessorApplication() throws Exception {
        // Initialize the database
        professorApplicationRepository.saveAndFlush(professorApplication);

		int databaseSizeBeforeUpdate = professorApplicationRepository.findAll().size();

        // Update the professorApplication
        professorApplication.setDate(UPDATED_DATE);
        professorApplication.setIndexNo(UPDATED_INDEX_NO);
        professorApplication.setStatus(UPDATED_STATUS);
        professorApplication.setResulationDate(UPDATED_RESULATION_DATE);
        professorApplication.setAgendaNumber(UPDATED_AGENDA_NUMBER);
        professorApplication.setWorkingBreak(UPDATED_WORKING_BREAK);
        professorApplication.setWorkingBreakStart(UPDATED_WORKING_BREAK_START);
        professorApplication.setWorkingBreakEnd(UPDATED_WORKING_BREAK_END);
        professorApplication.setDisciplinaryAction(UPDATED_DISCIPLINARY_ACTION);
        professorApplication.setDisActionCaseNo(UPDATED_DIS_ACTION_CASE_NO);
        professorApplication.setDisActionFileName(UPDATED_DIS_ACTION_FILE_NAME);

        restProfessorApplicationMockMvc.perform(put("/api/professorApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorApplication)))
                .andExpect(status().isOk());

        // Validate the ProfessorApplication in the database
        List<ProfessorApplication> professorApplications = professorApplicationRepository.findAll();
        assertThat(professorApplications).hasSize(databaseSizeBeforeUpdate);
        ProfessorApplication testProfessorApplication = professorApplications.get(professorApplications.size() - 1);
        assertThat(testProfessorApplication.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProfessorApplication.getIndexNo()).isEqualTo(UPDATED_INDEX_NO);
        assertThat(testProfessorApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProfessorApplication.getResulationDate()).isEqualTo(UPDATED_RESULATION_DATE);
        assertThat(testProfessorApplication.getAgendaNumber()).isEqualTo(UPDATED_AGENDA_NUMBER);
        assertThat(testProfessorApplication.getWorkingBreak()).isEqualTo(UPDATED_WORKING_BREAK);
        assertThat(testProfessorApplication.getWorkingBreakStart()).isEqualTo(UPDATED_WORKING_BREAK_START);
        assertThat(testProfessorApplication.getWorkingBreakEnd()).isEqualTo(UPDATED_WORKING_BREAK_END);
        assertThat(testProfessorApplication.getDisciplinaryAction()).isEqualTo(UPDATED_DISCIPLINARY_ACTION);
        assertThat(testProfessorApplication.getDisActionCaseNo()).isEqualTo(UPDATED_DIS_ACTION_CASE_NO);
        assertThat(testProfessorApplication.getDisActionFileName()).isEqualTo(UPDATED_DIS_ACTION_FILE_NAME);
    }

    @Test
    @Transactional
    public void deleteProfessorApplication() throws Exception {
        // Initialize the database
        professorApplicationRepository.saveAndFlush(professorApplication);

		int databaseSizeBeforeDelete = professorApplicationRepository.findAll().size();

        // Get the professorApplication
        restProfessorApplicationMockMvc.perform(delete("/api/professorApplications/{id}", professorApplication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProfessorApplication> professorApplications = professorApplicationRepository.findAll();
        assertThat(professorApplications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
