package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Training;
import gov.step.app.repository.TrainingRepository;
import gov.step.app.repository.search.TrainingSearchRepository;

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
 * Test class for the TrainingResource REST controller.
 *
 * @see TrainingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TrainingResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";

    private static final LocalDate DEFAULT_STARTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_GPA = new BigDecimal(1);
    private static final BigDecimal UPDATED_GPA = new BigDecimal(2);

    @Inject
    private TrainingRepository trainingRepository;

    @Inject
    private TrainingSearchRepository trainingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrainingMockMvc;

    private Training training;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrainingResource trainingResource = new TrainingResource();
        ReflectionTestUtils.setField(trainingResource, "trainingRepository", trainingRepository);
        ReflectionTestUtils.setField(trainingResource, "trainingSearchRepository", trainingSearchRepository);
        this.restTrainingMockMvc = MockMvcBuilders.standaloneSetup(trainingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        training = new Training();
        training.setName(DEFAULT_NAME);
        training.setSubject(DEFAULT_SUBJECT);
        training.setLocation(DEFAULT_LOCATION);
        training.setStartedDate(DEFAULT_STARTED_DATE);
        training.setEndedDate(DEFAULT_ENDED_DATE);
        training.setGpa(DEFAULT_GPA);
    }

    @Test
    @Transactional
    public void createTraining() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // Create the Training

        restTrainingMockMvc.perform(post("/api/trainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(training)))
                .andExpect(status().isCreated());

        // Validate the Training in the database
        List<Training> trainings = trainingRepository.findAll();
        assertThat(trainings).hasSize(databaseSizeBeforeCreate + 1);
        Training testTraining = trainings.get(trainings.size() - 1);
        assertThat(testTraining.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraining.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testTraining.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testTraining.getStartedDate()).isEqualTo(DEFAULT_STARTED_DATE);
        assertThat(testTraining.getEndedDate()).isEqualTo(DEFAULT_ENDED_DATE);
        assertThat(testTraining.getGpa()).isEqualTo(DEFAULT_GPA);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setName(null);

        // Create the Training, which fails.

        restTrainingMockMvc.perform(post("/api/trainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(training)))
                .andExpect(status().isBadRequest());

        List<Training> trainings = trainingRepository.findAll();
        assertThat(trainings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setSubject(null);

        // Create the Training, which fails.

        restTrainingMockMvc.perform(post("/api/trainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(training)))
                .andExpect(status().isBadRequest());

        List<Training> trainings = trainingRepository.findAll();
        assertThat(trainings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setLocation(null);

        // Create the Training, which fails.

        restTrainingMockMvc.perform(post("/api/trainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(training)))
                .andExpect(status().isBadRequest());

        List<Training> trainings = trainingRepository.findAll();
        assertThat(trainings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrainings() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainings
        restTrainingMockMvc.perform(get("/api/trainings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].startedDate").value(hasItem(DEFAULT_STARTED_DATE.toString())))
                .andExpect(jsonPath("$.[*].endedDate").value(hasItem(DEFAULT_ENDED_DATE.toString())))
                .andExpect(jsonPath("$.[*].gpa").value(hasItem(DEFAULT_GPA.intValue())));
    }

    @Test
    @Transactional
    public void getTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(training.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.startedDate").value(DEFAULT_STARTED_DATE.toString()))
            .andExpect(jsonPath("$.endedDate").value(DEFAULT_ENDED_DATE.toString()))
            .andExpect(jsonPath("$.gpa").value(DEFAULT_GPA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTraining() throws Exception {
        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

		int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training
        training.setName(UPDATED_NAME);
        training.setSubject(UPDATED_SUBJECT);
        training.setLocation(UPDATED_LOCATION);
        training.setStartedDate(UPDATED_STARTED_DATE);
        training.setEndedDate(UPDATED_ENDED_DATE);
        training.setGpa(UPDATED_GPA);

        restTrainingMockMvc.perform(put("/api/trainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(training)))
                .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainings = trainingRepository.findAll();
        assertThat(trainings).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainings.get(trainings.size() - 1);
        assertThat(testTraining.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraining.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testTraining.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testTraining.getStartedDate()).isEqualTo(UPDATED_STARTED_DATE);
        assertThat(testTraining.getEndedDate()).isEqualTo(UPDATED_ENDED_DATE);
        assertThat(testTraining.getGpa()).isEqualTo(UPDATED_GPA);
    }

    @Test
    @Transactional
    public void deleteTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

		int databaseSizeBeforeDelete = trainingRepository.findAll().size();

        // Get the training
        restTrainingMockMvc.perform(delete("/api/trainings/{id}", training.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Training> trainings = trainingRepository.findAll();
        assertThat(trainings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
