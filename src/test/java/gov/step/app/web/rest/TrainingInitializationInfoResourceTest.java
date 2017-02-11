package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TrainingInitializationInfo;
import gov.step.app.repository.TrainingInitializationInfoRepository;
import gov.step.app.repository.search.TrainingInitializationInfoSearchRepository;

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
 * Test class for the TrainingInitializationInfoResource REST controller.
 *
 * @see TrainingInitializationInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TrainingInitializationInfoResourceTest {

    private static final String DEFAULT_TRAINING_CODE = "AAAAA";
    private static final String UPDATED_TRAINING_CODE = "BBBBB";
    private static final String DEFAULT_TRAINING_TYPE = "AAAAA";
    private static final String UPDATED_TRAINING_TYPE = "BBBBB";
    private static final String DEFAULT_SESSION = "AAAAA";
    private static final String UPDATED_SESSION = "BBBBB";
    private static final String DEFAULT_VENUE_NAME = "AAAAA";
    private static final String UPDATED_VENUE_NAME = "BBBBB";

    private static final Long DEFAULT_NUMBER_OF_TRAINEE = 1L;
    private static final Long UPDATED_NUMBER_OF_TRAINEE = 2L;
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";

    private static final Long DEFAULT_DIVISION = 1L;
    private static final Long UPDATED_DIVISION = 2L;

    private static final Long DEFAULT_DISTRICT = 1L;
    private static final Long UPDATED_DISTRICT = 2L;
    private static final String DEFAULT_VENUE_DESCRIPTION = "AAAAA";
    private static final String UPDATED_VENUE_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_INITIALIZATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INITIALIZATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DURATION = "AAAAA";
    private static final String UPDATED_DURATION = "BBBBB";

    private static final Integer DEFAULT_HOURS = 1;
    private static final Integer UPDATED_HOURS = 2;

    private static final Boolean DEFAULT_PUBLISH_STATUS = false;
    private static final Boolean UPDATED_PUBLISH_STATUS = true;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private TrainingInitializationInfoRepository trainingInitializationInfoRepository;

    @Inject
    private TrainingInitializationInfoSearchRepository trainingInitializationInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrainingInitializationInfoMockMvc;

    private TrainingInitializationInfo trainingInitializationInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrainingInitializationInfoResource trainingInitializationInfoResource = new TrainingInitializationInfoResource();
        ReflectionTestUtils.setField(trainingInitializationInfoResource, "trainingInitializationInfoRepository", trainingInitializationInfoRepository);
        ReflectionTestUtils.setField(trainingInitializationInfoResource, "trainingInitializationInfoSearchRepository", trainingInitializationInfoSearchRepository);
        this.restTrainingInitializationInfoMockMvc = MockMvcBuilders.standaloneSetup(trainingInitializationInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trainingInitializationInfo = new TrainingInitializationInfo();
        trainingInitializationInfo.setTrainingCode(DEFAULT_TRAINING_CODE);
        trainingInitializationInfo.setTrainingType(DEFAULT_TRAINING_TYPE);
        trainingInitializationInfo.setSession(DEFAULT_SESSION);
        trainingInitializationInfo.setVenueName(DEFAULT_VENUE_NAME);
        trainingInitializationInfo.setNumberOfTrainee(DEFAULT_NUMBER_OF_TRAINEE);
        trainingInitializationInfo.setLocation(DEFAULT_LOCATION);
//        trainingInitializationInfo.setDivision(DEFAULT_DIVISION);
//        trainingInitializationInfo.setDistrict(DEFAULT_DISTRICT);
        trainingInitializationInfo.setVenueDescription(DEFAULT_VENUE_DESCRIPTION);
        trainingInitializationInfo.setInitializationDate(DEFAULT_INITIALIZATION_DATE);
        trainingInitializationInfo.setStartDate(DEFAULT_START_DATE);
        trainingInitializationInfo.setEndDate(DEFAULT_END_DATE);
        trainingInitializationInfo.setDuration(DEFAULT_DURATION);
        trainingInitializationInfo.setHours(DEFAULT_HOURS);
        trainingInitializationInfo.setPublishStatus(DEFAULT_PUBLISH_STATUS);
        trainingInitializationInfo.setInitializationDate(DEFAULT_INITIALIZATION_DATE);
        trainingInitializationInfo.setStatus(DEFAULT_STATUS);
        trainingInitializationInfo.setCreateDate(DEFAULT_CREATE_DATE);
        trainingInitializationInfo.setCreateBy(DEFAULT_CREATE_BY);
        trainingInitializationInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        trainingInitializationInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createTrainingInitializationInfo() throws Exception {
        int databaseSizeBeforeCreate = trainingInitializationInfoRepository.findAll().size();

        // Create the TrainingInitializationInfo

        restTrainingInitializationInfoMockMvc.perform(post("/api/trainingInitializationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingInitializationInfo)))
                .andExpect(status().isCreated());

        // Validate the TrainingInitializationInfo in the database
        List<TrainingInitializationInfo> trainingInitializationInfos = trainingInitializationInfoRepository.findAll();
        assertThat(trainingInitializationInfos).hasSize(databaseSizeBeforeCreate + 1);
        TrainingInitializationInfo testTrainingInitializationInfo = trainingInitializationInfos.get(trainingInitializationInfos.size() - 1);
        assertThat(testTrainingInitializationInfo.getTrainingCode()).isEqualTo(DEFAULT_TRAINING_CODE);
        assertThat(testTrainingInitializationInfo.getTrainingType()).isEqualTo(DEFAULT_TRAINING_TYPE);
        assertThat(testTrainingInitializationInfo.getSession()).isEqualTo(DEFAULT_SESSION);
        assertThat(testTrainingInitializationInfo.getVenueName()).isEqualTo(DEFAULT_VENUE_NAME);
        assertThat(testTrainingInitializationInfo.getNumberOfTrainee()).isEqualTo(DEFAULT_NUMBER_OF_TRAINEE);
        assertThat(testTrainingInitializationInfo.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testTrainingInitializationInfo.getDivision()).isEqualTo(DEFAULT_DIVISION);
        assertThat(testTrainingInitializationInfo.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testTrainingInitializationInfo.getVenueDescription()).isEqualTo(DEFAULT_VENUE_DESCRIPTION);
        assertThat(testTrainingInitializationInfo.getInitializationDate()).isEqualTo(DEFAULT_INITIALIZATION_DATE);
        assertThat(testTrainingInitializationInfo.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTrainingInitializationInfo.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTrainingInitializationInfo.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTrainingInitializationInfo.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testTrainingInitializationInfo.getPublishStatus()).isEqualTo(DEFAULT_PUBLISH_STATUS);
        assertThat(testTrainingInitializationInfo.getInitializationDate()).isEqualTo(DEFAULT_INITIALIZATION_DATE);
        assertThat(testTrainingInitializationInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrainingInitializationInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTrainingInitializationInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTrainingInitializationInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testTrainingInitializationInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllTrainingInitializationInfos() throws Exception {
        // Initialize the database
        trainingInitializationInfoRepository.saveAndFlush(trainingInitializationInfo);

        // Get all the trainingInitializationInfos
        restTrainingInitializationInfoMockMvc.perform(get("/api/trainingInitializationInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trainingInitializationInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].trainingCode").value(hasItem(DEFAULT_TRAINING_CODE.toString())))
                .andExpect(jsonPath("$.[*].trainingType").value(hasItem(DEFAULT_TRAINING_TYPE.toString())))
                .andExpect(jsonPath("$.[*].session").value(hasItem(DEFAULT_SESSION.toString())))
                .andExpect(jsonPath("$.[*].venueName").value(hasItem(DEFAULT_VENUE_NAME.toString())))
                .andExpect(jsonPath("$.[*].numberOfTrainee").value(hasItem(DEFAULT_NUMBER_OF_TRAINEE.intValue())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].division").value(hasItem(DEFAULT_DIVISION.intValue())))
                .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.intValue())))
                .andExpect(jsonPath("$.[*].venueDescription").value(hasItem(DEFAULT_VENUE_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].initializationDate").value(hasItem(DEFAULT_INITIALIZATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
                .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
                .andExpect(jsonPath("$.[*].publishStatus").value(hasItem(DEFAULT_PUBLISH_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].initializationDate").value(hasItem(DEFAULT_INITIALIZATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getTrainingInitializationInfo() throws Exception {
        // Initialize the database
        trainingInitializationInfoRepository.saveAndFlush(trainingInitializationInfo);

        // Get the trainingInitializationInfo
        restTrainingInitializationInfoMockMvc.perform(get("/api/trainingInitializationInfos/{id}", trainingInitializationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trainingInitializationInfo.getId().intValue()))
            .andExpect(jsonPath("$.trainingCode").value(DEFAULT_TRAINING_CODE.toString()))
            .andExpect(jsonPath("$.trainingType").value(DEFAULT_TRAINING_TYPE.toString()))
            .andExpect(jsonPath("$.session").value(DEFAULT_SESSION.toString()))
            .andExpect(jsonPath("$.venueName").value(DEFAULT_VENUE_NAME.toString()))
            .andExpect(jsonPath("$.numberOfTrainee").value(DEFAULT_NUMBER_OF_TRAINEE.intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.division").value(DEFAULT_DIVISION.intValue()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.intValue()))
            .andExpect(jsonPath("$.venueDescription").value(DEFAULT_VENUE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.initializationDate").value(DEFAULT_INITIALIZATION_DATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS))
            .andExpect(jsonPath("$.publishStatus").value(DEFAULT_PUBLISH_STATUS.booleanValue()))
            .andExpect(jsonPath("$.initializationDate").value(DEFAULT_INITIALIZATION_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingInitializationInfo() throws Exception {
        // Get the trainingInitializationInfo
        restTrainingInitializationInfoMockMvc.perform(get("/api/trainingInitializationInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingInitializationInfo() throws Exception {
        // Initialize the database
        trainingInitializationInfoRepository.saveAndFlush(trainingInitializationInfo);

		int databaseSizeBeforeUpdate = trainingInitializationInfoRepository.findAll().size();

        // Update the trainingInitializationInfo
        trainingInitializationInfo.setTrainingCode(UPDATED_TRAINING_CODE);
        trainingInitializationInfo.setTrainingType(UPDATED_TRAINING_TYPE);
        trainingInitializationInfo.setSession(UPDATED_SESSION);
        trainingInitializationInfo.setVenueName(UPDATED_VENUE_NAME);
        trainingInitializationInfo.setNumberOfTrainee(UPDATED_NUMBER_OF_TRAINEE);
        trainingInitializationInfo.setLocation(UPDATED_LOCATION);
//        trainingInitializationInfo.setDivision(UPDATED_DIVISION);
//        trainingInitializationInfo.setDistrict(UPDATED_DISTRICT);
        trainingInitializationInfo.setVenueDescription(UPDATED_VENUE_DESCRIPTION);
        trainingInitializationInfo.setInitializationDate(UPDATED_INITIALIZATION_DATE);
        trainingInitializationInfo.setStartDate(UPDATED_START_DATE);
        trainingInitializationInfo.setEndDate(UPDATED_END_DATE);
        trainingInitializationInfo.setDuration(UPDATED_DURATION);
        trainingInitializationInfo.setHours(UPDATED_HOURS);
        trainingInitializationInfo.setPublishStatus(UPDATED_PUBLISH_STATUS);
        trainingInitializationInfo.setInitializationDate(UPDATED_INITIALIZATION_DATE);
        trainingInitializationInfo.setStatus(UPDATED_STATUS);
        trainingInitializationInfo.setCreateDate(UPDATED_CREATE_DATE);
        trainingInitializationInfo.setCreateBy(UPDATED_CREATE_BY);
        trainingInitializationInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        trainingInitializationInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restTrainingInitializationInfoMockMvc.perform(put("/api/trainingInitializationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingInitializationInfo)))
                .andExpect(status().isOk());

        // Validate the TrainingInitializationInfo in the database
        List<TrainingInitializationInfo> trainingInitializationInfos = trainingInitializationInfoRepository.findAll();
        assertThat(trainingInitializationInfos).hasSize(databaseSizeBeforeUpdate);
        TrainingInitializationInfo testTrainingInitializationInfo = trainingInitializationInfos.get(trainingInitializationInfos.size() - 1);
        assertThat(testTrainingInitializationInfo.getTrainingCode()).isEqualTo(UPDATED_TRAINING_CODE);
        assertThat(testTrainingInitializationInfo.getTrainingType()).isEqualTo(UPDATED_TRAINING_TYPE);
        assertThat(testTrainingInitializationInfo.getSession()).isEqualTo(UPDATED_SESSION);
        assertThat(testTrainingInitializationInfo.getVenueName()).isEqualTo(UPDATED_VENUE_NAME);
        assertThat(testTrainingInitializationInfo.getNumberOfTrainee()).isEqualTo(UPDATED_NUMBER_OF_TRAINEE);
        assertThat(testTrainingInitializationInfo.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testTrainingInitializationInfo.getDivision()).isEqualTo(UPDATED_DIVISION);
        assertThat(testTrainingInitializationInfo.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testTrainingInitializationInfo.getVenueDescription()).isEqualTo(UPDATED_VENUE_DESCRIPTION);
        assertThat(testTrainingInitializationInfo.getInitializationDate()).isEqualTo(UPDATED_INITIALIZATION_DATE);
        assertThat(testTrainingInitializationInfo.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTrainingInitializationInfo.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTrainingInitializationInfo.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTrainingInitializationInfo.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testTrainingInitializationInfo.getPublishStatus()).isEqualTo(UPDATED_PUBLISH_STATUS);
        assertThat(testTrainingInitializationInfo.getInitializationDate()).isEqualTo(UPDATED_INITIALIZATION_DATE);
        assertThat(testTrainingInitializationInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainingInitializationInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTrainingInitializationInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTrainingInitializationInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testTrainingInitializationInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteTrainingInitializationInfo() throws Exception {
        // Initialize the database
        trainingInitializationInfoRepository.saveAndFlush(trainingInitializationInfo);

		int databaseSizeBeforeDelete = trainingInitializationInfoRepository.findAll().size();

        // Get the trainingInitializationInfo
        restTrainingInitializationInfoMockMvc.perform(delete("/api/trainingInitializationInfos/{id}", trainingInitializationInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingInitializationInfo> trainingInitializationInfos = trainingInitializationInfoRepository.findAll();
        assertThat(trainingInitializationInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
