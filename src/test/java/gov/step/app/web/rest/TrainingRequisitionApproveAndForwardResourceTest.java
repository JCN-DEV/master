package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TrainingRequisitionApproveAndForward;
import gov.step.app.repository.TrainingRequisitionApproveAndForwardRepository;
import gov.step.app.repository.search.TrainingRequisitionApproveAndForwardSearchRepository;

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
 * Test class for the TrainingRequisitionApproveAndForwardResource REST controller.
 *
 * @see TrainingRequisitionApproveAndForwardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TrainingRequisitionApproveAndForwardResourceTest {


    private static final String DEFAULT_APPROVE_BY_AUTHORITY = "AAAAA";
    private static final String UPDATED_APPROVE_BY_AUTHORITY = "BBBBB";
    private static final String DEFAULT_FORWARD_TO_AUTHORITY = "AAAAA";
    private static final String UPDATED_FORWARD_TO_AUTHORITY = "BBBBB";

    private static final Integer DEFAULT_APPROVE_STATUS = 1;
    private static final Integer UPDATED_APPROVE_STATUS = 2;
    private static final String DEFAULT_LOG_COMMENTS = "AAAAA";
    private static final String UPDATED_LOG_COMMENTS = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private TrainingRequisitionApproveAndForwardRepository trainingRequisitionApproveAndForwardRepository;

    @Inject
    private TrainingRequisitionApproveAndForwardSearchRepository trainingRequisitionApproveAndForwardSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrainingRequisitionApproveAndForwardMockMvc;

    private TrainingRequisitionApproveAndForward trainingRequisitionApproveAndForward;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrainingRequisitionApproveAndForwardResource trainingRequisitionApproveAndForwardResource = new TrainingRequisitionApproveAndForwardResource();
        ReflectionTestUtils.setField(trainingRequisitionApproveAndForwardResource, "trainingRequisitionApproveAndForwardRepository", trainingRequisitionApproveAndForwardRepository);
        ReflectionTestUtils.setField(trainingRequisitionApproveAndForwardResource, "trainingRequisitionApproveAndForwardSearchRepository", trainingRequisitionApproveAndForwardSearchRepository);
        this.restTrainingRequisitionApproveAndForwardMockMvc = MockMvcBuilders.standaloneSetup(trainingRequisitionApproveAndForwardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trainingRequisitionApproveAndForward = new TrainingRequisitionApproveAndForward();
        trainingRequisitionApproveAndForward.setApproveStatus(DEFAULT_APPROVE_STATUS);
        trainingRequisitionApproveAndForward.setApproveByAuthority(DEFAULT_APPROVE_BY_AUTHORITY);
        trainingRequisitionApproveAndForward.setForwardToAuthority(DEFAULT_FORWARD_TO_AUTHORITY);
        trainingRequisitionApproveAndForward.setApproveStatus(DEFAULT_APPROVE_STATUS);
        trainingRequisitionApproveAndForward.setLogComments(DEFAULT_LOG_COMMENTS);
//        trainingRequisitionApproveAndForward.setStatus(DEFAULT_STATUS);
        trainingRequisitionApproveAndForward.setCreateDate(DEFAULT_CREATE_DATE);
        trainingRequisitionApproveAndForward.setCreateBy(DEFAULT_CREATE_BY);
        trainingRequisitionApproveAndForward.setUpdateDate(DEFAULT_UPDATE_DATE);
        trainingRequisitionApproveAndForward.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createTrainingRequisitionApproveAndForward() throws Exception {
        int databaseSizeBeforeCreate = trainingRequisitionApproveAndForwardRepository.findAll().size();

        // Create the TrainingRequisitionApproveAndForward

        restTrainingRequisitionApproveAndForwardMockMvc.perform(post("/api/trainingRequisitionApproveAndForwards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingRequisitionApproveAndForward)))
                .andExpect(status().isCreated());

        // Validate the TrainingRequisitionApproveAndForward in the database
        List<TrainingRequisitionApproveAndForward> trainingRequisitionApproveAndForwards = trainingRequisitionApproveAndForwardRepository.findAll();
        assertThat(trainingRequisitionApproveAndForwards).hasSize(databaseSizeBeforeCreate + 1);
        TrainingRequisitionApproveAndForward testTrainingRequisitionApproveAndForward = trainingRequisitionApproveAndForwards.get(trainingRequisitionApproveAndForwards.size() - 1);
        assertThat(testTrainingRequisitionApproveAndForward.getApproveStatus()).isEqualTo(DEFAULT_APPROVE_STATUS);
        assertThat(testTrainingRequisitionApproveAndForward.getApproveByAuthority()).isEqualTo(DEFAULT_APPROVE_BY_AUTHORITY);
        assertThat(testTrainingRequisitionApproveAndForward.getForwardToAuthority()).isEqualTo(DEFAULT_FORWARD_TO_AUTHORITY);
        assertThat(testTrainingRequisitionApproveAndForward.getApproveStatus()).isEqualTo(DEFAULT_APPROVE_STATUS);
        assertThat(testTrainingRequisitionApproveAndForward.getLogComments()).isEqualTo(DEFAULT_LOG_COMMENTS);
        assertThat(testTrainingRequisitionApproveAndForward.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrainingRequisitionApproveAndForward.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTrainingRequisitionApproveAndForward.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTrainingRequisitionApproveAndForward.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testTrainingRequisitionApproveAndForward.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllTrainingRequisitionApproveAndForwards() throws Exception {
        // Initialize the database
        trainingRequisitionApproveAndForwardRepository.saveAndFlush(trainingRequisitionApproveAndForward);

        // Get all the trainingRequisitionApproveAndForwards
        restTrainingRequisitionApproveAndForwardMockMvc.perform(get("/api/trainingRequisitionApproveAndForwards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trainingRequisitionApproveAndForward.getId().intValue())))
                .andExpect(jsonPath("$.[*].approveStatus").value(hasItem(DEFAULT_APPROVE_STATUS)))
                .andExpect(jsonPath("$.[*].approveByAuthority").value(hasItem(DEFAULT_APPROVE_BY_AUTHORITY.toString())))
                .andExpect(jsonPath("$.[*].forwardToAuthority").value(hasItem(DEFAULT_FORWARD_TO_AUTHORITY.toString())))
                .andExpect(jsonPath("$.[*].approveStatus").value(hasItem(DEFAULT_APPROVE_STATUS)))
                .andExpect(jsonPath("$.[*].logComments").value(hasItem(DEFAULT_LOG_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getTrainingRequisitionApproveAndForward() throws Exception {
        // Initialize the database
        trainingRequisitionApproveAndForwardRepository.saveAndFlush(trainingRequisitionApproveAndForward);

        // Get the trainingRequisitionApproveAndForward
        restTrainingRequisitionApproveAndForwardMockMvc.perform(get("/api/trainingRequisitionApproveAndForwards/{id}", trainingRequisitionApproveAndForward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trainingRequisitionApproveAndForward.getId().intValue()))
            .andExpect(jsonPath("$.approveStatus").value(DEFAULT_APPROVE_STATUS))
            .andExpect(jsonPath("$.approveByAuthority").value(DEFAULT_APPROVE_BY_AUTHORITY.toString()))
            .andExpect(jsonPath("$.forwardToAuthority").value(DEFAULT_FORWARD_TO_AUTHORITY.toString()))
            .andExpect(jsonPath("$.approveStatus").value(DEFAULT_APPROVE_STATUS))
            .andExpect(jsonPath("$.logComments").value(DEFAULT_LOG_COMMENTS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingRequisitionApproveAndForward() throws Exception {
        // Get the trainingRequisitionApproveAndForward
        restTrainingRequisitionApproveAndForwardMockMvc.perform(get("/api/trainingRequisitionApproveAndForwards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingRequisitionApproveAndForward() throws Exception {
        // Initialize the database
        trainingRequisitionApproveAndForwardRepository.saveAndFlush(trainingRequisitionApproveAndForward);

		int databaseSizeBeforeUpdate = trainingRequisitionApproveAndForwardRepository.findAll().size();

        // Update the trainingRequisitionApproveAndForward
        trainingRequisitionApproveAndForward.setApproveStatus(UPDATED_APPROVE_STATUS);
        trainingRequisitionApproveAndForward.setApproveByAuthority(UPDATED_APPROVE_BY_AUTHORITY);
        trainingRequisitionApproveAndForward.setForwardToAuthority(UPDATED_FORWARD_TO_AUTHORITY);
        trainingRequisitionApproveAndForward.setApproveStatus(UPDATED_APPROVE_STATUS);
        trainingRequisitionApproveAndForward.setLogComments(UPDATED_LOG_COMMENTS);
//        trainingRequisitionApproveAndForward.setStatus(UPDATED_STATUS);
        trainingRequisitionApproveAndForward.setCreateDate(UPDATED_CREATE_DATE);
        trainingRequisitionApproveAndForward.setCreateBy(UPDATED_CREATE_BY);
        trainingRequisitionApproveAndForward.setUpdateDate(UPDATED_UPDATE_DATE);
        trainingRequisitionApproveAndForward.setUpdateBy(UPDATED_UPDATE_BY);

        restTrainingRequisitionApproveAndForwardMockMvc.perform(put("/api/trainingRequisitionApproveAndForwards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingRequisitionApproveAndForward)))
                .andExpect(status().isOk());

        // Validate the TrainingRequisitionApproveAndForward in the database
        List<TrainingRequisitionApproveAndForward> trainingRequisitionApproveAndForwards = trainingRequisitionApproveAndForwardRepository.findAll();
        assertThat(trainingRequisitionApproveAndForwards).hasSize(databaseSizeBeforeUpdate);
        TrainingRequisitionApproveAndForward testTrainingRequisitionApproveAndForward = trainingRequisitionApproveAndForwards.get(trainingRequisitionApproveAndForwards.size() - 1);
        assertThat(testTrainingRequisitionApproveAndForward.getApproveStatus()).isEqualTo(UPDATED_APPROVE_STATUS);
        assertThat(testTrainingRequisitionApproveAndForward.getApproveByAuthority()).isEqualTo(UPDATED_APPROVE_BY_AUTHORITY);
        assertThat(testTrainingRequisitionApproveAndForward.getForwardToAuthority()).isEqualTo(UPDATED_FORWARD_TO_AUTHORITY);
        assertThat(testTrainingRequisitionApproveAndForward.getApproveStatus()).isEqualTo(UPDATED_APPROVE_STATUS);
        assertThat(testTrainingRequisitionApproveAndForward.getLogComments()).isEqualTo(UPDATED_LOG_COMMENTS);
        assertThat(testTrainingRequisitionApproveAndForward.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainingRequisitionApproveAndForward.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTrainingRequisitionApproveAndForward.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTrainingRequisitionApproveAndForward.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testTrainingRequisitionApproveAndForward.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteTrainingRequisitionApproveAndForward() throws Exception {
        // Initialize the database
        trainingRequisitionApproveAndForwardRepository.saveAndFlush(trainingRequisitionApproveAndForward);

		int databaseSizeBeforeDelete = trainingRequisitionApproveAndForwardRepository.findAll().size();

        // Get the trainingRequisitionApproveAndForward
        restTrainingRequisitionApproveAndForwardMockMvc.perform(delete("/api/trainingRequisitionApproveAndForwards/{id}", trainingRequisitionApproveAndForward.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingRequisitionApproveAndForward> trainingRequisitionApproveAndForwards = trainingRequisitionApproveAndForwardRepository.findAll();
        assertThat(trainingRequisitionApproveAndForwards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
