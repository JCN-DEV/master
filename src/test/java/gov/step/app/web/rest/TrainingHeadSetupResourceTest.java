package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TrainingHeadSetup;
import gov.step.app.repository.TrainingHeadSetupRepository;
import gov.step.app.repository.search.TrainingHeadSetupSearchRepository;

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
 * Test class for the TrainingHeadSetupResource REST controller.
 *
 * @see TrainingHeadSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TrainingHeadSetupResourceTest {

    private static final String DEFAULT_HEAD_NAME = "AAAAA";
    private static final String UPDATED_HEAD_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

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
    private TrainingHeadSetupRepository trainingHeadSetupRepository;

    @Inject
    private TrainingHeadSetupSearchRepository trainingHeadSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrainingHeadSetupMockMvc;

    private TrainingHeadSetup trainingHeadSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrainingHeadSetupResource trainingHeadSetupResource = new TrainingHeadSetupResource();
        ReflectionTestUtils.setField(trainingHeadSetupResource, "trainingHeadSetupRepository", trainingHeadSetupRepository);
        ReflectionTestUtils.setField(trainingHeadSetupResource, "trainingHeadSetupSearchRepository", trainingHeadSetupSearchRepository);
        this.restTrainingHeadSetupMockMvc = MockMvcBuilders.standaloneSetup(trainingHeadSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trainingHeadSetup = new TrainingHeadSetup();
        trainingHeadSetup.setHeadName(DEFAULT_HEAD_NAME);
        trainingHeadSetup.setDescription(DEFAULT_DESCRIPTION);
        trainingHeadSetup.setStatus(DEFAULT_STATUS);
        trainingHeadSetup.setCreateDate(DEFAULT_CREATE_DATE);
        trainingHeadSetup.setCreateBy(DEFAULT_CREATE_BY);
        trainingHeadSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        trainingHeadSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createTrainingHeadSetup() throws Exception {
        int databaseSizeBeforeCreate = trainingHeadSetupRepository.findAll().size();

        // Create the TrainingHeadSetup

        restTrainingHeadSetupMockMvc.perform(post("/api/trainingHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingHeadSetup)))
                .andExpect(status().isCreated());

        // Validate the TrainingHeadSetup in the database
        List<TrainingHeadSetup> trainingHeadSetups = trainingHeadSetupRepository.findAll();
        assertThat(trainingHeadSetups).hasSize(databaseSizeBeforeCreate + 1);
        TrainingHeadSetup testTrainingHeadSetup = trainingHeadSetups.get(trainingHeadSetups.size() - 1);
        assertThat(testTrainingHeadSetup.getHeadName()).isEqualTo(DEFAULT_HEAD_NAME);
        assertThat(testTrainingHeadSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTrainingHeadSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrainingHeadSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTrainingHeadSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTrainingHeadSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testTrainingHeadSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkHeadNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingHeadSetupRepository.findAll().size();
        // set the field null
        trainingHeadSetup.setHeadName(null);

        // Create the TrainingHeadSetup, which fails.

        restTrainingHeadSetupMockMvc.perform(post("/api/trainingHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingHeadSetup)))
                .andExpect(status().isBadRequest());

        List<TrainingHeadSetup> trainingHeadSetups = trainingHeadSetupRepository.findAll();
        assertThat(trainingHeadSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrainingHeadSetups() throws Exception {
        // Initialize the database
        trainingHeadSetupRepository.saveAndFlush(trainingHeadSetup);

        // Get all the trainingHeadSetups
        restTrainingHeadSetupMockMvc.perform(get("/api/trainingHeadSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trainingHeadSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].headName").value(hasItem(DEFAULT_HEAD_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getTrainingHeadSetup() throws Exception {
        // Initialize the database
        trainingHeadSetupRepository.saveAndFlush(trainingHeadSetup);

        // Get the trainingHeadSetup
        restTrainingHeadSetupMockMvc.perform(get("/api/trainingHeadSetups/{id}", trainingHeadSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trainingHeadSetup.getId().intValue()))
            .andExpect(jsonPath("$.headName").value(DEFAULT_HEAD_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingHeadSetup() throws Exception {
        // Get the trainingHeadSetup
        restTrainingHeadSetupMockMvc.perform(get("/api/trainingHeadSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingHeadSetup() throws Exception {
        // Initialize the database
        trainingHeadSetupRepository.saveAndFlush(trainingHeadSetup);

		int databaseSizeBeforeUpdate = trainingHeadSetupRepository.findAll().size();

        // Update the trainingHeadSetup
        trainingHeadSetup.setHeadName(UPDATED_HEAD_NAME);
        trainingHeadSetup.setDescription(UPDATED_DESCRIPTION);
        trainingHeadSetup.setStatus(UPDATED_STATUS);
        trainingHeadSetup.setCreateDate(UPDATED_CREATE_DATE);
        trainingHeadSetup.setCreateBy(UPDATED_CREATE_BY);
        trainingHeadSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        trainingHeadSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restTrainingHeadSetupMockMvc.perform(put("/api/trainingHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingHeadSetup)))
                .andExpect(status().isOk());

        // Validate the TrainingHeadSetup in the database
        List<TrainingHeadSetup> trainingHeadSetups = trainingHeadSetupRepository.findAll();
        assertThat(trainingHeadSetups).hasSize(databaseSizeBeforeUpdate);
        TrainingHeadSetup testTrainingHeadSetup = trainingHeadSetups.get(trainingHeadSetups.size() - 1);
        assertThat(testTrainingHeadSetup.getHeadName()).isEqualTo(UPDATED_HEAD_NAME);
        assertThat(testTrainingHeadSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrainingHeadSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainingHeadSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTrainingHeadSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTrainingHeadSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testTrainingHeadSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteTrainingHeadSetup() throws Exception {
        // Initialize the database
        trainingHeadSetupRepository.saveAndFlush(trainingHeadSetup);

		int databaseSizeBeforeDelete = trainingHeadSetupRepository.findAll().size();

        // Get the trainingHeadSetup
        restTrainingHeadSetupMockMvc.perform(delete("/api/trainingHeadSetups/{id}", trainingHeadSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingHeadSetup> trainingHeadSetups = trainingHeadSetupRepository.findAll();
        assertThat(trainingHeadSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
