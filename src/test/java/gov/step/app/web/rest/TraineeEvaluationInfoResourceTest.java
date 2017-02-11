package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TraineeEvaluationInfo;
import gov.step.app.repository.TraineeEvaluationInfoRepository;
import gov.step.app.repository.search.TraineeEvaluationInfoSearchRepository;

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
 * Test class for the TraineeEvaluationInfoResource REST controller.
 *
 * @see TraineeEvaluationInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TraineeEvaluationInfoResourceTest {

    private static final String DEFAULT_SESSION_YEAR = "AAAAA";
    private static final String UPDATED_SESSION_YEAR = "BBBBB";
    private static final String DEFAULT_PERFORMANCE = "AAAAA";
    private static final String UPDATED_PERFORMANCE = "BBBBB";
    private static final String DEFAULT_MARK = "AAAAA";
    private static final String UPDATED_MARK = "BBBBB";
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_EVALUATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVALUATION_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private TraineeEvaluationInfoRepository traineeEvaluationInfoRepository;

    @Inject
    private TraineeEvaluationInfoSearchRepository traineeEvaluationInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTraineeEvaluationInfoMockMvc;

    private TraineeEvaluationInfo traineeEvaluationInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraineeEvaluationInfoResource traineeEvaluationInfoResource = new TraineeEvaluationInfoResource();
        ReflectionTestUtils.setField(traineeEvaluationInfoResource, "traineeEvaluationInfoRepository", traineeEvaluationInfoRepository);
        ReflectionTestUtils.setField(traineeEvaluationInfoResource, "traineeEvaluationInfoSearchRepository", traineeEvaluationInfoSearchRepository);
        this.restTraineeEvaluationInfoMockMvc = MockMvcBuilders.standaloneSetup(traineeEvaluationInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traineeEvaluationInfo = new TraineeEvaluationInfo();

        traineeEvaluationInfo.setPerformance(DEFAULT_PERFORMANCE);
        traineeEvaluationInfo.setMark(DEFAULT_MARK);
        traineeEvaluationInfo.setRemarks(DEFAULT_REMARKS);
        traineeEvaluationInfo.setEvaluationDate(DEFAULT_EVALUATION_DATE);
        traineeEvaluationInfo.setStatus(DEFAULT_STATUS);
        traineeEvaluationInfo.setCreateDate(DEFAULT_CREATE_DATE);
        traineeEvaluationInfo.setCreateBy(DEFAULT_CREATE_BY);
        traineeEvaluationInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        traineeEvaluationInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createTraineeEvaluationInfo() throws Exception {
        int databaseSizeBeforeCreate = traineeEvaluationInfoRepository.findAll().size();

        // Create the TraineeEvaluationInfo

        restTraineeEvaluationInfoMockMvc.perform(post("/api/traineeEvaluationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traineeEvaluationInfo)))
                .andExpect(status().isCreated());

        // Validate the TraineeEvaluationInfo in the database
        List<TraineeEvaluationInfo> traineeEvaluationInfos = traineeEvaluationInfoRepository.findAll();
        assertThat(traineeEvaluationInfos).hasSize(databaseSizeBeforeCreate + 1);
        TraineeEvaluationInfo testTraineeEvaluationInfo = traineeEvaluationInfos.get(traineeEvaluationInfos.size() - 1);
//        assertThat(testTraineeEvaluationInfo.getSessionYear()).isEqualTo(DEFAULT_SESSION_YEAR);
        assertThat(testTraineeEvaluationInfo.getPerformance()).isEqualTo(DEFAULT_PERFORMANCE);
        assertThat(testTraineeEvaluationInfo.getMark()).isEqualTo(DEFAULT_MARK);
        assertThat(testTraineeEvaluationInfo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testTraineeEvaluationInfo.getEvaluationDate()).isEqualTo(DEFAULT_EVALUATION_DATE);
        assertThat(testTraineeEvaluationInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTraineeEvaluationInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTraineeEvaluationInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTraineeEvaluationInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testTraineeEvaluationInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkPerformanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeEvaluationInfoRepository.findAll().size();
        // set the field null
        traineeEvaluationInfo.setPerformance(null);

        // Create the TraineeEvaluationInfo, which fails.

        restTraineeEvaluationInfoMockMvc.perform(post("/api/traineeEvaluationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traineeEvaluationInfo)))
                .andExpect(status().isBadRequest());

        List<TraineeEvaluationInfo> traineeEvaluationInfos = traineeEvaluationInfoRepository.findAll();
        assertThat(traineeEvaluationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarkIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeEvaluationInfoRepository.findAll().size();
        // set the field null
        traineeEvaluationInfo.setMark(null);

        // Create the TraineeEvaluationInfo, which fails.

        restTraineeEvaluationInfoMockMvc.perform(post("/api/traineeEvaluationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traineeEvaluationInfo)))
                .andExpect(status().isBadRequest());

        List<TraineeEvaluationInfo> traineeEvaluationInfos = traineeEvaluationInfoRepository.findAll();
        assertThat(traineeEvaluationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEvaluationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeEvaluationInfoRepository.findAll().size();
        // set the field null
        traineeEvaluationInfo.setEvaluationDate(null);

        // Create the TraineeEvaluationInfo, which fails.

        restTraineeEvaluationInfoMockMvc.perform(post("/api/traineeEvaluationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traineeEvaluationInfo)))
                .andExpect(status().isBadRequest());

        List<TraineeEvaluationInfo> traineeEvaluationInfos = traineeEvaluationInfoRepository.findAll();
        assertThat(traineeEvaluationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTraineeEvaluationInfos() throws Exception {
        // Initialize the database
        traineeEvaluationInfoRepository.saveAndFlush(traineeEvaluationInfo);

        // Get all the traineeEvaluationInfos
        restTraineeEvaluationInfoMockMvc.perform(get("/api/traineeEvaluationInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traineeEvaluationInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].sessionYear").value(hasItem(DEFAULT_SESSION_YEAR.toString())))
                .andExpect(jsonPath("$.[*].performance").value(hasItem(DEFAULT_PERFORMANCE.toString())))
                .andExpect(jsonPath("$.[*].mark").value(hasItem(DEFAULT_MARK.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].evaluationDate").value(hasItem(DEFAULT_EVALUATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getTraineeEvaluationInfo() throws Exception {
        // Initialize the database
        traineeEvaluationInfoRepository.saveAndFlush(traineeEvaluationInfo);

        // Get the traineeEvaluationInfo
        restTraineeEvaluationInfoMockMvc.perform(get("/api/traineeEvaluationInfos/{id}", traineeEvaluationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(traineeEvaluationInfo.getId().intValue()))
            .andExpect(jsonPath("$.sessionYear").value(DEFAULT_SESSION_YEAR.toString()))
            .andExpect(jsonPath("$.performance").value(DEFAULT_PERFORMANCE.toString()))
            .andExpect(jsonPath("$.mark").value(DEFAULT_MARK.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.evaluationDate").value(DEFAULT_EVALUATION_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTraineeEvaluationInfo() throws Exception {
        // Get the traineeEvaluationInfo
        restTraineeEvaluationInfoMockMvc.perform(get("/api/traineeEvaluationInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraineeEvaluationInfo() throws Exception {
        // Initialize the database
        traineeEvaluationInfoRepository.saveAndFlush(traineeEvaluationInfo);

		int databaseSizeBeforeUpdate = traineeEvaluationInfoRepository.findAll().size();

        // Update the traineeEvaluationInfo
//        traineeEvaluationInfo.setSessionYear(UPDATED_SESSION_YEAR);
        traineeEvaluationInfo.setPerformance(UPDATED_PERFORMANCE);
        traineeEvaluationInfo.setMark(UPDATED_MARK);
        traineeEvaluationInfo.setRemarks(UPDATED_REMARKS);
        traineeEvaluationInfo.setEvaluationDate(UPDATED_EVALUATION_DATE);
        traineeEvaluationInfo.setStatus(UPDATED_STATUS);
        traineeEvaluationInfo.setCreateDate(UPDATED_CREATE_DATE);
        traineeEvaluationInfo.setCreateBy(UPDATED_CREATE_BY);
        traineeEvaluationInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        traineeEvaluationInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restTraineeEvaluationInfoMockMvc.perform(put("/api/traineeEvaluationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traineeEvaluationInfo)))
                .andExpect(status().isOk());

        // Validate the TraineeEvaluationInfo in the database
        List<TraineeEvaluationInfo> traineeEvaluationInfos = traineeEvaluationInfoRepository.findAll();
        assertThat(traineeEvaluationInfos).hasSize(databaseSizeBeforeUpdate);
        TraineeEvaluationInfo testTraineeEvaluationInfo = traineeEvaluationInfos.get(traineeEvaluationInfos.size() - 1);
//        assertThat(testTraineeEvaluationInfo.getSessionYear()).isEqualTo(UPDATED_SESSION_YEAR);
        assertThat(testTraineeEvaluationInfo.getPerformance()).isEqualTo(UPDATED_PERFORMANCE);
        assertThat(testTraineeEvaluationInfo.getMark()).isEqualTo(UPDATED_MARK);
        assertThat(testTraineeEvaluationInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testTraineeEvaluationInfo.getEvaluationDate()).isEqualTo(UPDATED_EVALUATION_DATE);
        assertThat(testTraineeEvaluationInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTraineeEvaluationInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTraineeEvaluationInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTraineeEvaluationInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testTraineeEvaluationInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteTraineeEvaluationInfo() throws Exception {
        // Initialize the database
        traineeEvaluationInfoRepository.saveAndFlush(traineeEvaluationInfo);

		int databaseSizeBeforeDelete = traineeEvaluationInfoRepository.findAll().size();

        // Get the traineeEvaluationInfo
        restTraineeEvaluationInfoMockMvc.perform(delete("/api/traineeEvaluationInfos/{id}", traineeEvaluationInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TraineeEvaluationInfo> traineeEvaluationInfos = traineeEvaluationInfoRepository.findAll();
        assertThat(traineeEvaluationInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
