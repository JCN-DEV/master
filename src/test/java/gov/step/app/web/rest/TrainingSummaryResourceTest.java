package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TrainingSummary;
import gov.step.app.repository.TrainingSummaryRepository;
import gov.step.app.repository.search.TrainingSummarySearchRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TrainingSummaryResource REST controller.
 *
 * @see TrainingSummaryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TrainingSummaryResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_TOPICS_COVERED = "AAAAA";
    private static final String UPDATED_TOPICS_COVERED = "BBBBB";
    private static final String DEFAULT_INSTITUTE = "AAAAA";
    private static final String UPDATED_INSTITUTE = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";

    private static final ZonedDateTime DEFAULT_STARTED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_STARTED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_STARTED_DATE_STR = dateTimeFormatter.format(DEFAULT_STARTED_DATE);

    private static final ZonedDateTime DEFAULT_ENDED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ENDED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ENDED_DATE_STR = dateTimeFormatter.format(DEFAULT_ENDED_DATE);

    @Inject
    private TrainingSummaryRepository trainingSummaryRepository;

    @Inject
    private TrainingSummarySearchRepository trainingSummarySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrainingSummaryMockMvc;

    private TrainingSummary trainingSummary;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrainingSummaryResource trainingSummaryResource = new TrainingSummaryResource();
        ReflectionTestUtils.setField(trainingSummaryResource, "trainingSummaryRepository", trainingSummaryRepository);
        ReflectionTestUtils.setField(trainingSummaryResource, "trainingSummarySearchRepository", trainingSummarySearchRepository);
        this.restTrainingSummaryMockMvc = MockMvcBuilders.standaloneSetup(trainingSummaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trainingSummary = new TrainingSummary();
        trainingSummary.setTitle(DEFAULT_TITLE);
        trainingSummary.setTopicsCovered(DEFAULT_TOPICS_COVERED);
        trainingSummary.setInstitute(DEFAULT_INSTITUTE);
        trainingSummary.setCountry(DEFAULT_COUNTRY);
        trainingSummary.setLocation(DEFAULT_LOCATION);
        trainingSummary.setStartedDate(DEFAULT_STARTED_DATE);
        trainingSummary.setEndedDate(DEFAULT_ENDED_DATE);
    }

    @Test
    @Transactional
    public void createTrainingSummary() throws Exception {
        int databaseSizeBeforeCreate = trainingSummaryRepository.findAll().size();

        // Create the TrainingSummary

        restTrainingSummaryMockMvc.perform(post("/api/trainingSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingSummary)))
                .andExpect(status().isCreated());

        // Validate the TrainingSummary in the database
        List<TrainingSummary> trainingSummarys = trainingSummaryRepository.findAll();
        assertThat(trainingSummarys).hasSize(databaseSizeBeforeCreate + 1);
        TrainingSummary testTrainingSummary = trainingSummarys.get(trainingSummarys.size() - 1);
        assertThat(testTrainingSummary.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTrainingSummary.getTopicsCovered()).isEqualTo(DEFAULT_TOPICS_COVERED);
        assertThat(testTrainingSummary.getInstitute()).isEqualTo(DEFAULT_INSTITUTE);
        assertThat(testTrainingSummary.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testTrainingSummary.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testTrainingSummary.getStartedDate()).isEqualTo(DEFAULT_STARTED_DATE);
        assertThat(testTrainingSummary.getEndedDate()).isEqualTo(DEFAULT_ENDED_DATE);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingSummaryRepository.findAll().size();
        // set the field null
        trainingSummary.setTitle(null);

        // Create the TrainingSummary, which fails.

        restTrainingSummaryMockMvc.perform(post("/api/trainingSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingSummary)))
                .andExpect(status().isBadRequest());

        List<TrainingSummary> trainingSummarys = trainingSummaryRepository.findAll();
        assertThat(trainingSummarys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicsCoveredIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingSummaryRepository.findAll().size();
        // set the field null
        trainingSummary.setTopicsCovered(null);

        // Create the TrainingSummary, which fails.

        restTrainingSummaryMockMvc.perform(post("/api/trainingSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingSummary)))
                .andExpect(status().isBadRequest());

        List<TrainingSummary> trainingSummarys = trainingSummaryRepository.findAll();
        assertThat(trainingSummarys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstituteIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingSummaryRepository.findAll().size();
        // set the field null
        trainingSummary.setInstitute(null);

        // Create the TrainingSummary, which fails.

        restTrainingSummaryMockMvc.perform(post("/api/trainingSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingSummary)))
                .andExpect(status().isBadRequest());

        List<TrainingSummary> trainingSummarys = trainingSummaryRepository.findAll();
        assertThat(trainingSummarys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingSummaryRepository.findAll().size();
        // set the field null
        trainingSummary.setCountry(null);

        // Create the TrainingSummary, which fails.

        restTrainingSummaryMockMvc.perform(post("/api/trainingSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingSummary)))
                .andExpect(status().isBadRequest());

        List<TrainingSummary> trainingSummarys = trainingSummaryRepository.findAll();
        assertThat(trainingSummarys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingSummaryRepository.findAll().size();
        // set the field null
        trainingSummary.setLocation(null);

        // Create the TrainingSummary, which fails.

        restTrainingSummaryMockMvc.perform(post("/api/trainingSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingSummary)))
                .andExpect(status().isBadRequest());

        List<TrainingSummary> trainingSummarys = trainingSummaryRepository.findAll();
        assertThat(trainingSummarys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrainingSummarys() throws Exception {
        // Initialize the database
        trainingSummaryRepository.saveAndFlush(trainingSummary);

        // Get all the trainingSummarys
        restTrainingSummaryMockMvc.perform(get("/api/trainingSummarys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trainingSummary.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].topicsCovered").value(hasItem(DEFAULT_TOPICS_COVERED.toString())))
                .andExpect(jsonPath("$.[*].institute").value(hasItem(DEFAULT_INSTITUTE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].startedDate").value(hasItem(DEFAULT_STARTED_DATE_STR)))
                .andExpect(jsonPath("$.[*].endedDate").value(hasItem(DEFAULT_ENDED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getTrainingSummary() throws Exception {
        // Initialize the database
        trainingSummaryRepository.saveAndFlush(trainingSummary);

        // Get the trainingSummary
        restTrainingSummaryMockMvc.perform(get("/api/trainingSummarys/{id}", trainingSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trainingSummary.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.topicsCovered").value(DEFAULT_TOPICS_COVERED.toString()))
            .andExpect(jsonPath("$.institute").value(DEFAULT_INSTITUTE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.startedDate").value(DEFAULT_STARTED_DATE_STR))
            .andExpect(jsonPath("$.endedDate").value(DEFAULT_ENDED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingSummary() throws Exception {
        // Get the trainingSummary
        restTrainingSummaryMockMvc.perform(get("/api/trainingSummarys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingSummary() throws Exception {
        // Initialize the database
        trainingSummaryRepository.saveAndFlush(trainingSummary);

		int databaseSizeBeforeUpdate = trainingSummaryRepository.findAll().size();

        // Update the trainingSummary
        trainingSummary.setTitle(UPDATED_TITLE);
        trainingSummary.setTopicsCovered(UPDATED_TOPICS_COVERED);
        trainingSummary.setInstitute(UPDATED_INSTITUTE);
        trainingSummary.setCountry(UPDATED_COUNTRY);
        trainingSummary.setLocation(UPDATED_LOCATION);
        trainingSummary.setStartedDate(UPDATED_STARTED_DATE);
        trainingSummary.setEndedDate(UPDATED_ENDED_DATE);

        restTrainingSummaryMockMvc.perform(put("/api/trainingSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingSummary)))
                .andExpect(status().isOk());

        // Validate the TrainingSummary in the database
        List<TrainingSummary> trainingSummarys = trainingSummaryRepository.findAll();
        assertThat(trainingSummarys).hasSize(databaseSizeBeforeUpdate);
        TrainingSummary testTrainingSummary = trainingSummarys.get(trainingSummarys.size() - 1);
        assertThat(testTrainingSummary.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTrainingSummary.getTopicsCovered()).isEqualTo(UPDATED_TOPICS_COVERED);
        assertThat(testTrainingSummary.getInstitute()).isEqualTo(UPDATED_INSTITUTE);
        assertThat(testTrainingSummary.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testTrainingSummary.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testTrainingSummary.getStartedDate()).isEqualTo(UPDATED_STARTED_DATE);
        assertThat(testTrainingSummary.getEndedDate()).isEqualTo(UPDATED_ENDED_DATE);
    }

    @Test
    @Transactional
    public void deleteTrainingSummary() throws Exception {
        // Initialize the database
        trainingSummaryRepository.saveAndFlush(trainingSummary);

		int databaseSizeBeforeDelete = trainingSummaryRepository.findAll().size();

        // Get the trainingSummary
        restTrainingSummaryMockMvc.perform(delete("/api/trainingSummarys/{id}", trainingSummary.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingSummary> trainingSummarys = trainingSummaryRepository.findAll();
        assertThat(trainingSummarys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
