package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmplTraining;
import gov.step.app.repository.InstEmplTrainingRepository;
import gov.step.app.repository.search.InstEmplTrainingSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the InstEmplTrainingResource REST controller.
 *
 * @see InstEmplTrainingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmplTrainingResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_SUBJECTS_COVERD = "AAAAA";
    private static final String UPDATED_SUBJECTS_COVERD = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";

    private static final LocalDate DEFAULT_STARTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RESULT = "1";
    private static final String UPDATED_RESULT = "2";

    @Inject
    private InstEmplTrainingRepository instEmplTrainingRepository;

    @Inject
    private InstEmplTrainingSearchRepository instEmplTrainingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmplTrainingMockMvc;

    private InstEmplTraining instEmplTraining;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmplTrainingResource instEmplTrainingResource = new InstEmplTrainingResource();
        ReflectionTestUtils.setField(instEmplTrainingResource, "instEmplTrainingRepository", instEmplTrainingRepository);
        ReflectionTestUtils.setField(instEmplTrainingResource, "instEmplTrainingSearchRepository", instEmplTrainingSearchRepository);
        this.restInstEmplTrainingMockMvc = MockMvcBuilders.standaloneSetup(instEmplTrainingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmplTraining = new InstEmplTraining();
        instEmplTraining.setName(DEFAULT_NAME);
        instEmplTraining.setSubjectsCoverd(DEFAULT_SUBJECTS_COVERD);
        instEmplTraining.setLocation(DEFAULT_LOCATION);
        instEmplTraining.setStartedDate(DEFAULT_STARTED_DATE);
        instEmplTraining.setEndedDate(DEFAULT_ENDED_DATE);
        instEmplTraining.setResult(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    public void createInstEmplTraining() throws Exception {
        int databaseSizeBeforeCreate = instEmplTrainingRepository.findAll().size();

        // Create the InstEmplTraining

        restInstEmplTrainingMockMvc.perform(post("/api/instEmplTrainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplTraining)))
                .andExpect(status().isCreated());

        // Validate the InstEmplTraining in the database
        List<InstEmplTraining> instEmplTrainings = instEmplTrainingRepository.findAll();
        assertThat(instEmplTrainings).hasSize(databaseSizeBeforeCreate + 1);
        InstEmplTraining testInstEmplTraining = instEmplTrainings.get(instEmplTrainings.size() - 1);
        assertThat(testInstEmplTraining.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstEmplTraining.getSubjectsCoverd()).isEqualTo(DEFAULT_SUBJECTS_COVERD);
        assertThat(testInstEmplTraining.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testInstEmplTraining.getStartedDate()).isEqualTo(DEFAULT_STARTED_DATE);
        assertThat(testInstEmplTraining.getEndedDate()).isEqualTo(DEFAULT_ENDED_DATE);
        assertThat(testInstEmplTraining.getResult()).isEqualTo(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplTrainingRepository.findAll().size();
        // set the field null
        instEmplTraining.setName(null);

        // Create the InstEmplTraining, which fails.

        restInstEmplTrainingMockMvc.perform(post("/api/instEmplTrainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplTraining)))
                .andExpect(status().isBadRequest());

        List<InstEmplTraining> instEmplTrainings = instEmplTrainingRepository.findAll();
        assertThat(instEmplTrainings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubjectsCoverdIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplTrainingRepository.findAll().size();
        // set the field null
        instEmplTraining.setSubjectsCoverd(null);

        // Create the InstEmplTraining, which fails.

        restInstEmplTrainingMockMvc.perform(post("/api/instEmplTrainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplTraining)))
                .andExpect(status().isBadRequest());

        List<InstEmplTraining> instEmplTrainings = instEmplTrainingRepository.findAll();
        assertThat(instEmplTrainings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplTrainingRepository.findAll().size();
        // set the field null
        instEmplTraining.setLocation(null);

        // Create the InstEmplTraining, which fails.

        restInstEmplTrainingMockMvc.perform(post("/api/instEmplTrainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplTraining)))
                .andExpect(status().isBadRequest());

        List<InstEmplTraining> instEmplTrainings = instEmplTrainingRepository.findAll();
        assertThat(instEmplTrainings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstEmplTrainings() throws Exception {
        // Initialize the database
        instEmplTrainingRepository.saveAndFlush(instEmplTraining);

        // Get all the instEmplTrainings
        restInstEmplTrainingMockMvc.perform(get("/api/instEmplTrainings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmplTraining.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].subjectsCoverd").value(hasItem(DEFAULT_SUBJECTS_COVERD.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].startedDate").value(hasItem(DEFAULT_STARTED_DATE.toString())))
                .andExpect(jsonPath("$.[*].endedDate").value(hasItem(DEFAULT_ENDED_DATE.toString())))
                .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)));
    }

    @Test
    @Transactional
    public void getInstEmplTraining() throws Exception {
        // Initialize the database
        instEmplTrainingRepository.saveAndFlush(instEmplTraining);

        // Get the instEmplTraining
        restInstEmplTrainingMockMvc.perform(get("/api/instEmplTrainings/{id}", instEmplTraining.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmplTraining.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.subjectsCoverd").value(DEFAULT_SUBJECTS_COVERD.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.startedDate").value(DEFAULT_STARTED_DATE.toString()))
            .andExpect(jsonPath("$.endedDate").value(DEFAULT_ENDED_DATE.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmplTraining() throws Exception {
        // Get the instEmplTraining
        restInstEmplTrainingMockMvc.perform(get("/api/instEmplTrainings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmplTraining() throws Exception {
        // Initialize the database
        instEmplTrainingRepository.saveAndFlush(instEmplTraining);

		int databaseSizeBeforeUpdate = instEmplTrainingRepository.findAll().size();

        // Update the instEmplTraining
        instEmplTraining.setName(UPDATED_NAME);
        instEmplTraining.setSubjectsCoverd(UPDATED_SUBJECTS_COVERD);
        instEmplTraining.setLocation(UPDATED_LOCATION);
        instEmplTraining.setStartedDate(UPDATED_STARTED_DATE);
        instEmplTraining.setEndedDate(UPDATED_ENDED_DATE);
        instEmplTraining.setResult(UPDATED_RESULT);

        restInstEmplTrainingMockMvc.perform(put("/api/instEmplTrainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplTraining)))
                .andExpect(status().isOk());

        // Validate the InstEmplTraining in the database
        List<InstEmplTraining> instEmplTrainings = instEmplTrainingRepository.findAll();
        assertThat(instEmplTrainings).hasSize(databaseSizeBeforeUpdate);
        InstEmplTraining testInstEmplTraining = instEmplTrainings.get(instEmplTrainings.size() - 1);
        assertThat(testInstEmplTraining.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstEmplTraining.getSubjectsCoverd()).isEqualTo(UPDATED_SUBJECTS_COVERD);
        assertThat(testInstEmplTraining.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testInstEmplTraining.getStartedDate()).isEqualTo(UPDATED_STARTED_DATE);
        assertThat(testInstEmplTraining.getEndedDate()).isEqualTo(UPDATED_ENDED_DATE);
        assertThat(testInstEmplTraining.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void deleteInstEmplTraining() throws Exception {
        // Initialize the database
        instEmplTrainingRepository.saveAndFlush(instEmplTraining);

		int databaseSizeBeforeDelete = instEmplTrainingRepository.findAll().size();

        // Get the instEmplTraining
        restInstEmplTrainingMockMvc.perform(delete("/api/instEmplTrainings/{id}", instEmplTraining.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmplTraining> instEmplTrainings = instEmplTrainingRepository.findAll();
        assertThat(instEmplTrainings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
