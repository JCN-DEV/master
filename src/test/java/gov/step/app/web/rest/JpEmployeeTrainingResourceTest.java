package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JpEmployeeTraining;
import gov.step.app.repository.JpEmployeeTrainingRepository;
import gov.step.app.repository.search.JpEmployeeTrainingSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the JpEmployeeTrainingResource REST controller.
 *
 * @see JpEmployeeTrainingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JpEmployeeTrainingResourceTest {

    private static final String DEFAULT_TRAINING_TITLE = "AAAAA";
    private static final String UPDATED_TRAINING_TITLE = "BBBBB";
    private static final String DEFAULT_TOPIC_COVERED = "AAAAA";
    private static final String UPDATED_TOPIC_COVERED = "BBBBB";
    private static final String DEFAULT_INSTITUTE = "AAAAA";
    private static final String UPDATED_INSTITUTE = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    private static final String DEFAULT_DURATION = "AAAAA";
    private static final String UPDATED_DURATION = "BBBBB";
    private static final String DEFAULT_RESULT = "AAAAA";
    private static final String UPDATED_RESULT = "BBBBB";

    @Inject
    private JpEmployeeTrainingRepository jpEmployeeTrainingRepository;

    @Inject
    private JpEmployeeTrainingSearchRepository jpEmployeeTrainingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJpEmployeeTrainingMockMvc;

    private JpEmployeeTraining jpEmployeeTraining;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpEmployeeTrainingResource jpEmployeeTrainingResource = new JpEmployeeTrainingResource();
        ReflectionTestUtils.setField(jpEmployeeTrainingResource, "jpEmployeeTrainingRepository", jpEmployeeTrainingRepository);
        ReflectionTestUtils.setField(jpEmployeeTrainingResource, "jpEmployeeTrainingSearchRepository", jpEmployeeTrainingSearchRepository);
        this.restJpEmployeeTrainingMockMvc = MockMvcBuilders.standaloneSetup(jpEmployeeTrainingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jpEmployeeTraining = new JpEmployeeTraining();
        jpEmployeeTraining.setTrainingTitle(DEFAULT_TRAINING_TITLE);
        jpEmployeeTraining.setTopicCovered(DEFAULT_TOPIC_COVERED);
        jpEmployeeTraining.setInstitute(DEFAULT_INSTITUTE);
        jpEmployeeTraining.setLocation(DEFAULT_LOCATION);
        jpEmployeeTraining.setDuration(DEFAULT_DURATION);
        jpEmployeeTraining.setResult(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    public void createJpEmployeeTraining() throws Exception {
        int databaseSizeBeforeCreate = jpEmployeeTrainingRepository.findAll().size();

        // Create the JpEmployeeTraining

        restJpEmployeeTrainingMockMvc.perform(post("/api/jpEmployeeTrainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeTraining)))
                .andExpect(status().isCreated());

        // Validate the JpEmployeeTraining in the database
        List<JpEmployeeTraining> jpEmployeeTrainings = jpEmployeeTrainingRepository.findAll();
        assertThat(jpEmployeeTrainings).hasSize(databaseSizeBeforeCreate + 1);
        JpEmployeeTraining testJpEmployeeTraining = jpEmployeeTrainings.get(jpEmployeeTrainings.size() - 1);
        assertThat(testJpEmployeeTraining.getTrainingTitle()).isEqualTo(DEFAULT_TRAINING_TITLE);
        assertThat(testJpEmployeeTraining.getTopicCovered()).isEqualTo(DEFAULT_TOPIC_COVERED);
        assertThat(testJpEmployeeTraining.getInstitute()).isEqualTo(DEFAULT_INSTITUTE);
        assertThat(testJpEmployeeTraining.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testJpEmployeeTraining.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testJpEmployeeTraining.getResult()).isEqualTo(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    public void checkTrainingTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmployeeTrainingRepository.findAll().size();
        // set the field null
        jpEmployeeTraining.setTrainingTitle(null);

        // Create the JpEmployeeTraining, which fails.

        restJpEmployeeTrainingMockMvc.perform(post("/api/jpEmployeeTrainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeTraining)))
                .andExpect(status().isBadRequest());

        List<JpEmployeeTraining> jpEmployeeTrainings = jpEmployeeTrainingRepository.findAll();
        assertThat(jpEmployeeTrainings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicCoveredIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmployeeTrainingRepository.findAll().size();
        // set the field null
        jpEmployeeTraining.setTopicCovered(null);

        // Create the JpEmployeeTraining, which fails.

        restJpEmployeeTrainingMockMvc.perform(post("/api/jpEmployeeTrainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeTraining)))
                .andExpect(status().isBadRequest());

        List<JpEmployeeTraining> jpEmployeeTrainings = jpEmployeeTrainingRepository.findAll();
        assertThat(jpEmployeeTrainings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstituteIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmployeeTrainingRepository.findAll().size();
        // set the field null
        jpEmployeeTraining.setInstitute(null);

        // Create the JpEmployeeTraining, which fails.

        restJpEmployeeTrainingMockMvc.perform(post("/api/jpEmployeeTrainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeTraining)))
                .andExpect(status().isBadRequest());

        List<JpEmployeeTraining> jpEmployeeTrainings = jpEmployeeTrainingRepository.findAll();
        assertThat(jpEmployeeTrainings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJpEmployeeTrainings() throws Exception {
        // Initialize the database
        jpEmployeeTrainingRepository.saveAndFlush(jpEmployeeTraining);

        // Get all the jpEmployeeTrainings
        restJpEmployeeTrainingMockMvc.perform(get("/api/jpEmployeeTrainings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jpEmployeeTraining.getId().intValue())))
                .andExpect(jsonPath("$.[*].trainingTitle").value(hasItem(DEFAULT_TRAINING_TITLE.toString())))
                .andExpect(jsonPath("$.[*].topicCovered").value(hasItem(DEFAULT_TOPIC_COVERED.toString())))
                .andExpect(jsonPath("$.[*].institute").value(hasItem(DEFAULT_INSTITUTE.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
                .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())));
    }

    @Test
    @Transactional
    public void getJpEmployeeTraining() throws Exception {
        // Initialize the database
        jpEmployeeTrainingRepository.saveAndFlush(jpEmployeeTraining);

        // Get the jpEmployeeTraining
        restJpEmployeeTrainingMockMvc.perform(get("/api/jpEmployeeTrainings/{id}", jpEmployeeTraining.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jpEmployeeTraining.getId().intValue()))
            .andExpect(jsonPath("$.trainingTitle").value(DEFAULT_TRAINING_TITLE.toString()))
            .andExpect(jsonPath("$.topicCovered").value(DEFAULT_TOPIC_COVERED.toString()))
            .andExpect(jsonPath("$.institute").value(DEFAULT_INSTITUTE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJpEmployeeTraining() throws Exception {
        // Get the jpEmployeeTraining
        restJpEmployeeTrainingMockMvc.perform(get("/api/jpEmployeeTrainings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpEmployeeTraining() throws Exception {
        // Initialize the database
        jpEmployeeTrainingRepository.saveAndFlush(jpEmployeeTraining);

		int databaseSizeBeforeUpdate = jpEmployeeTrainingRepository.findAll().size();

        // Update the jpEmployeeTraining
        jpEmployeeTraining.setTrainingTitle(UPDATED_TRAINING_TITLE);
        jpEmployeeTraining.setTopicCovered(UPDATED_TOPIC_COVERED);
        jpEmployeeTraining.setInstitute(UPDATED_INSTITUTE);
        jpEmployeeTraining.setLocation(UPDATED_LOCATION);
        jpEmployeeTraining.setDuration(UPDATED_DURATION);
        jpEmployeeTraining.setResult(UPDATED_RESULT);

        restJpEmployeeTrainingMockMvc.perform(put("/api/jpEmployeeTrainings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeTraining)))
                .andExpect(status().isOk());

        // Validate the JpEmployeeTraining in the database
        List<JpEmployeeTraining> jpEmployeeTrainings = jpEmployeeTrainingRepository.findAll();
        assertThat(jpEmployeeTrainings).hasSize(databaseSizeBeforeUpdate);
        JpEmployeeTraining testJpEmployeeTraining = jpEmployeeTrainings.get(jpEmployeeTrainings.size() - 1);
        assertThat(testJpEmployeeTraining.getTrainingTitle()).isEqualTo(UPDATED_TRAINING_TITLE);
        assertThat(testJpEmployeeTraining.getTopicCovered()).isEqualTo(UPDATED_TOPIC_COVERED);
        assertThat(testJpEmployeeTraining.getInstitute()).isEqualTo(UPDATED_INSTITUTE);
        assertThat(testJpEmployeeTraining.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testJpEmployeeTraining.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testJpEmployeeTraining.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void deleteJpEmployeeTraining() throws Exception {
        // Initialize the database
        jpEmployeeTrainingRepository.saveAndFlush(jpEmployeeTraining);

		int databaseSizeBeforeDelete = jpEmployeeTrainingRepository.findAll().size();

        // Get the jpEmployeeTraining
        restJpEmployeeTrainingMockMvc.perform(delete("/api/jpEmployeeTrainings/{id}", jpEmployeeTraining.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JpEmployeeTraining> jpEmployeeTrainings = jpEmployeeTrainingRepository.findAll();
        assertThat(jpEmployeeTrainings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
