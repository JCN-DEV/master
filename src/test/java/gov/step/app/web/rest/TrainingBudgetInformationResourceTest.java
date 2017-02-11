package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TrainingBudgetInformation;
import gov.step.app.repository.TrainingBudgetInformationRepository;
import gov.step.app.repository.search.TrainingBudgetInformationSearchRepository;

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
 * Test class for the TrainingBudgetInformationResource REST controller.
 *
 * @see TrainingBudgetInformationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TrainingBudgetInformationResourceTest {


    private static final Long DEFAULT_BUDGET_AMOUNT = 1L;
    private static final Long UPDATED_BUDGET_AMOUNT = 2L;

    private static final Long DEFAULT_EXPENSE_AMOUNT = 1L;
    private static final Long UPDATED_EXPENSE_AMOUNT = 2L;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

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
    private TrainingBudgetInformationRepository trainingBudgetInformationRepository;

    @Inject
    private TrainingBudgetInformationSearchRepository trainingBudgetInformationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrainingBudgetInformationMockMvc;

    private TrainingBudgetInformation trainingBudgetInformation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrainingBudgetInformationResource trainingBudgetInformationResource = new TrainingBudgetInformationResource();
        ReflectionTestUtils.setField(trainingBudgetInformationResource, "trainingBudgetInformationRepository", trainingBudgetInformationRepository);
        ReflectionTestUtils.setField(trainingBudgetInformationResource, "trainingBudgetInformationSearchRepository", trainingBudgetInformationSearchRepository);
        this.restTrainingBudgetInformationMockMvc = MockMvcBuilders.standaloneSetup(trainingBudgetInformationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trainingBudgetInformation = new TrainingBudgetInformation();
        trainingBudgetInformation.setBudgetAmount(DEFAULT_BUDGET_AMOUNT);
        trainingBudgetInformation.setExpenseAmount(DEFAULT_EXPENSE_AMOUNT);
        trainingBudgetInformation.setRemarks(DEFAULT_REMARKS);
        trainingBudgetInformation.setStatus(DEFAULT_STATUS);
        trainingBudgetInformation.setCreateDate(DEFAULT_CREATE_DATE);
        trainingBudgetInformation.setCreateBy(DEFAULT_CREATE_BY);
        trainingBudgetInformation.setUpdateDate(DEFAULT_UPDATE_DATE);
        trainingBudgetInformation.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createTrainingBudgetInformation() throws Exception {
        int databaseSizeBeforeCreate = trainingBudgetInformationRepository.findAll().size();

        // Create the TrainingBudgetInformation

        restTrainingBudgetInformationMockMvc.perform(post("/api/trainingBudgetInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingBudgetInformation)))
                .andExpect(status().isCreated());

        // Validate the TrainingBudgetInformation in the database
        List<TrainingBudgetInformation> trainingBudgetInformations = trainingBudgetInformationRepository.findAll();
        assertThat(trainingBudgetInformations).hasSize(databaseSizeBeforeCreate + 1);
        TrainingBudgetInformation testTrainingBudgetInformation = trainingBudgetInformations.get(trainingBudgetInformations.size() - 1);
        assertThat(testTrainingBudgetInformation.getBudgetAmount()).isEqualTo(DEFAULT_BUDGET_AMOUNT);
        assertThat(testTrainingBudgetInformation.getExpenseAmount()).isEqualTo(DEFAULT_EXPENSE_AMOUNT);
        assertThat(testTrainingBudgetInformation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testTrainingBudgetInformation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrainingBudgetInformation.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTrainingBudgetInformation.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTrainingBudgetInformation.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testTrainingBudgetInformation.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllTrainingBudgetInformations() throws Exception {
        // Initialize the database
        trainingBudgetInformationRepository.saveAndFlush(trainingBudgetInformation);

        // Get all the trainingBudgetInformations
        restTrainingBudgetInformationMockMvc.perform(get("/api/trainingBudgetInformations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trainingBudgetInformation.getId().intValue())))
                .andExpect(jsonPath("$.[*].budgetAmount").value(hasItem(DEFAULT_BUDGET_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].expenseAmount").value(hasItem(DEFAULT_EXPENSE_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getTrainingBudgetInformation() throws Exception {
        // Initialize the database
        trainingBudgetInformationRepository.saveAndFlush(trainingBudgetInformation);

        // Get the trainingBudgetInformation
        restTrainingBudgetInformationMockMvc.perform(get("/api/trainingBudgetInformations/{id}", trainingBudgetInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trainingBudgetInformation.getId().intValue()))
            .andExpect(jsonPath("$.budgetAmount").value(DEFAULT_BUDGET_AMOUNT.intValue()))
            .andExpect(jsonPath("$.expenseAmount").value(DEFAULT_EXPENSE_AMOUNT.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingBudgetInformation() throws Exception {
        // Get the trainingBudgetInformation
        restTrainingBudgetInformationMockMvc.perform(get("/api/trainingBudgetInformations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingBudgetInformation() throws Exception {
        // Initialize the database
        trainingBudgetInformationRepository.saveAndFlush(trainingBudgetInformation);

		int databaseSizeBeforeUpdate = trainingBudgetInformationRepository.findAll().size();

        // Update the trainingBudgetInformation
        trainingBudgetInformation.setBudgetAmount(UPDATED_BUDGET_AMOUNT);
        trainingBudgetInformation.setExpenseAmount(UPDATED_EXPENSE_AMOUNT);
        trainingBudgetInformation.setRemarks(UPDATED_REMARKS);
        trainingBudgetInformation.setStatus(UPDATED_STATUS);
        trainingBudgetInformation.setCreateDate(UPDATED_CREATE_DATE);
        trainingBudgetInformation.setCreateBy(UPDATED_CREATE_BY);
        trainingBudgetInformation.setUpdateDate(UPDATED_UPDATE_DATE);
        trainingBudgetInformation.setUpdateBy(UPDATED_UPDATE_BY);

        restTrainingBudgetInformationMockMvc.perform(put("/api/trainingBudgetInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingBudgetInformation)))
                .andExpect(status().isOk());

        // Validate the TrainingBudgetInformation in the database
        List<TrainingBudgetInformation> trainingBudgetInformations = trainingBudgetInformationRepository.findAll();
        assertThat(trainingBudgetInformations).hasSize(databaseSizeBeforeUpdate);
        TrainingBudgetInformation testTrainingBudgetInformation = trainingBudgetInformations.get(trainingBudgetInformations.size() - 1);
        assertThat(testTrainingBudgetInformation.getBudgetAmount()).isEqualTo(UPDATED_BUDGET_AMOUNT);
        assertThat(testTrainingBudgetInformation.getExpenseAmount()).isEqualTo(UPDATED_EXPENSE_AMOUNT);
        assertThat(testTrainingBudgetInformation.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testTrainingBudgetInformation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainingBudgetInformation.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTrainingBudgetInformation.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTrainingBudgetInformation.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testTrainingBudgetInformation.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteTrainingBudgetInformation() throws Exception {
        // Initialize the database
        trainingBudgetInformationRepository.saveAndFlush(trainingBudgetInformation);

		int databaseSizeBeforeDelete = trainingBudgetInformationRepository.findAll().size();

        // Get the trainingBudgetInformation
        restTrainingBudgetInformationMockMvc.perform(delete("/api/trainingBudgetInformations/{id}", trainingBudgetInformation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingBudgetInformation> trainingBudgetInformations = trainingBudgetInformationRepository.findAll();
        assertThat(trainingBudgetInformations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
