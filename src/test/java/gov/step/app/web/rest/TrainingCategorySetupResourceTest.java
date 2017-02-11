/*
package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TrainingCategorySetup;
import gov.step.app.repository.TrainingCategorySetupRepository;
import gov.step.app.repository.search.TrainingCategorySetupSearchRepository;

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


*/
/**
 * Test class for the TrainingCategorySetupResource REST controller.
 *
 * @see TrainingCategorySetupResource
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TrainingCategorySetupResourceTest {

    private static final String DEFAULT_CATEGORY_NAME = "AAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBB";
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
    private TrainingCategorySetupRepository trainingCategorySetupRepository;

    @Inject
    private TrainingCategorySetupSearchRepository trainingCategorySetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrainingCategorySetupMockMvc;

    private TrainingCategorySetup trainingCategorySetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrainingCategorySetupResource trainingCategorySetupResource = new TrainingCategorySetupResource();
        ReflectionTestUtils.setField(trainingCategorySetupResource, "trainingCategorySetupRepository", trainingCategorySetupRepository);
        ReflectionTestUtils.setField(trainingCategorySetupResource, "trainingCategorySetupSearchRepository", trainingCategorySetupSearchRepository);
        this.restTrainingCategorySetupMockMvc = MockMvcBuilders.standaloneSetup(trainingCategorySetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trainingCategorySetup = new TrainingCategorySetup();
        trainingCategorySetup.setCategoryName(DEFAULT_CATEGORY_NAME);
        trainingCategorySetup.setDescription(DEFAULT_DESCRIPTION);
        trainingCategorySetup.setStatus(DEFAULT_STATUS);
        trainingCategorySetup.setCreateDate(DEFAULT_CREATE_DATE);
        trainingCategorySetup.setCreateBy(DEFAULT_CREATE_BY);
        trainingCategorySetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        trainingCategorySetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createTrainingCategorySetup() throws Exception {
        int databaseSizeBeforeCreate = trainingCategorySetupRepository.findAll().size();

        // Create the TrainingCategorySetup

        restTrainingCategorySetupMockMvc.perform(post("/api/trainingCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingCategorySetup)))
                .andExpect(status().isCreated());

        // Validate the TrainingCategorySetup in the database
        List<TrainingCategorySetup> trainingCategorySetups = trainingCategorySetupRepository.findAll();
        assertThat(trainingCategorySetups).hasSize(databaseSizeBeforeCreate + 1);
        TrainingCategorySetup testTrainingCategorySetup = trainingCategorySetups.get(trainingCategorySetups.size() - 1);
        assertThat(testTrainingCategorySetup.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testTrainingCategorySetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTrainingCategorySetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrainingCategorySetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTrainingCategorySetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTrainingCategorySetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testTrainingCategorySetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingCategorySetupRepository.findAll().size();
        // set the field null
        trainingCategorySetup.setCategoryName(null);

        // Create the TrainingCategorySetup, which fails.

        restTrainingCategorySetupMockMvc.perform(post("/api/trainingCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingCategorySetup)))
                .andExpect(status().isBadRequest());

        List<TrainingCategorySetup> trainingCategorySetups = trainingCategorySetupRepository.findAll();
        assertThat(trainingCategorySetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrainingCategorySetups() throws Exception {
        // Initialize the database
        trainingCategorySetupRepository.saveAndFlush(trainingCategorySetup);

        // Get all the trainingCategorySetups
        restTrainingCategorySetupMockMvc.perform(get("/api/trainingCategorySetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trainingCategorySetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getTrainingCategorySetup() throws Exception {
        // Initialize the database
        trainingCategorySetupRepository.saveAndFlush(trainingCategorySetup);

        // Get the trainingCategorySetup
        restTrainingCategorySetupMockMvc.perform(get("/api/trainingCategorySetups/{id}", trainingCategorySetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trainingCategorySetup.getId().intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingCategorySetup() throws Exception {
        // Get the trainingCategorySetup
        restTrainingCategorySetupMockMvc.perform(get("/api/trainingCategorySetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingCategorySetup() throws Exception {
        // Initialize the database
        trainingCategorySetupRepository.saveAndFlush(trainingCategorySetup);

		int databaseSizeBeforeUpdate = trainingCategorySetupRepository.findAll().size();

        // Update the trainingCategorySetup
        trainingCategorySetup.setCategoryName(UPDATED_CATEGORY_NAME);
        trainingCategorySetup.setDescription(UPDATED_DESCRIPTION);
        trainingCategorySetup.setStatus(UPDATED_STATUS);
        trainingCategorySetup.setCreateDate(UPDATED_CREATE_DATE);
        trainingCategorySetup.setCreateBy(UPDATED_CREATE_BY);
        trainingCategorySetup.setUpdateDate(UPDATED_UPDATE_DATE);
        trainingCategorySetup.setUpdateBy(UPDATED_UPDATE_BY);

        restTrainingCategorySetupMockMvc.perform(put("/api/trainingCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingCategorySetup)))
                .andExpect(status().isOk());

        // Validate the TrainingCategorySetup in the database
        List<TrainingCategorySetup> trainingCategorySetups = trainingCategorySetupRepository.findAll();
        assertThat(trainingCategorySetups).hasSize(databaseSizeBeforeUpdate);
        TrainingCategorySetup testTrainingCategorySetup = trainingCategorySetups.get(trainingCategorySetups.size() - 1);
        assertThat(testTrainingCategorySetup.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testTrainingCategorySetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrainingCategorySetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainingCategorySetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTrainingCategorySetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTrainingCategorySetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testTrainingCategorySetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteTrainingCategorySetup() throws Exception {
        // Initialize the database
        trainingCategorySetupRepository.saveAndFlush(trainingCategorySetup);

		int databaseSizeBeforeDelete = trainingCategorySetupRepository.findAll().size();

        // Get the trainingCategorySetup
        restTrainingCategorySetupMockMvc.perform(delete("/api/trainingCategorySetups/{id}", trainingCategorySetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingCategorySetup> trainingCategorySetups = trainingCategorySetupRepository.findAll();
        assertThat(trainingCategorySetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/
