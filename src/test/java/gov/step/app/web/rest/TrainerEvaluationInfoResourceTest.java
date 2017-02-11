package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TrainerEvaluationInfo;
import gov.step.app.repository.TrainerEvaluationInfoRepository;
import gov.step.app.repository.search.TrainerEvaluationInfoSearchRepository;

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
 * Test class for the TrainerEvaluationInfoResource REST controller.
 *
 * @see TrainerEvaluationInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TrainerEvaluationInfoResourceTest {

    private static final String DEFAULT_SESSION_YEAR = "AAAAA";
    private static final String UPDATED_SESSION_YEAR = "BBBBB";
    private static final String DEFAULT_PERFORMANCE = "AAAAA";
    private static final String UPDATED_PERFORMANCE = "BBBBB";
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
    private TrainerEvaluationInfoRepository trainerEvaluationInfoRepository;

    @Inject
    private TrainerEvaluationInfoSearchRepository trainerEvaluationInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrainerEvaluationInfoMockMvc;

    private TrainerEvaluationInfo trainerEvaluationInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrainerEvaluationInfoResource trainerEvaluationInfoResource = new TrainerEvaluationInfoResource();
        ReflectionTestUtils.setField(trainerEvaluationInfoResource, "trainerEvaluationInfoRepository", trainerEvaluationInfoRepository);
        ReflectionTestUtils.setField(trainerEvaluationInfoResource, "trainerEvaluationInfoSearchRepository", trainerEvaluationInfoSearchRepository);
        this.restTrainerEvaluationInfoMockMvc = MockMvcBuilders.standaloneSetup(trainerEvaluationInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trainerEvaluationInfo = new TrainerEvaluationInfo();
//        trainerEvaluationInfo.setSessionYear(DEFAULT_SESSION_YEAR);
        trainerEvaluationInfo.setPerformance(DEFAULT_PERFORMANCE);
        trainerEvaluationInfo.setRemarks(DEFAULT_REMARKS);
        trainerEvaluationInfo.setEvaluationDate(DEFAULT_EVALUATION_DATE);
        trainerEvaluationInfo.setStatus(DEFAULT_STATUS);
        trainerEvaluationInfo.setCreateDate(DEFAULT_CREATE_DATE);
        trainerEvaluationInfo.setCreateBy(DEFAULT_CREATE_BY);
        trainerEvaluationInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        trainerEvaluationInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createTrainerEvaluationInfo() throws Exception {
        int databaseSizeBeforeCreate = trainerEvaluationInfoRepository.findAll().size();

        // Create the TrainerEvaluationInfo

        restTrainerEvaluationInfoMockMvc.perform(post("/api/trainerEvaluationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainerEvaluationInfo)))
                .andExpect(status().isCreated());

        // Validate the TrainerEvaluationInfo in the database
        List<TrainerEvaluationInfo> trainerEvaluationInfos = trainerEvaluationInfoRepository.findAll();
        assertThat(trainerEvaluationInfos).hasSize(databaseSizeBeforeCreate + 1);
        TrainerEvaluationInfo testTrainerEvaluationInfo = trainerEvaluationInfos.get(trainerEvaluationInfos.size() - 1);
//        assertThat(testTrainerEvaluationInfo.getSessionYear()).isEqualTo(DEFAULT_SESSION_YEAR);
        assertThat(testTrainerEvaluationInfo.getPerformance()).isEqualTo(DEFAULT_PERFORMANCE);
        assertThat(testTrainerEvaluationInfo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testTrainerEvaluationInfo.getEvaluationDate()).isEqualTo(DEFAULT_EVALUATION_DATE);
        assertThat(testTrainerEvaluationInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrainerEvaluationInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTrainerEvaluationInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTrainerEvaluationInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testTrainerEvaluationInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkPerformanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainerEvaluationInfoRepository.findAll().size();
        // set the field null
        trainerEvaluationInfo.setPerformance(null);

        // Create the TrainerEvaluationInfo, which fails.

        restTrainerEvaluationInfoMockMvc.perform(post("/api/trainerEvaluationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainerEvaluationInfo)))
                .andExpect(status().isBadRequest());

        List<TrainerEvaluationInfo> trainerEvaluationInfos = trainerEvaluationInfoRepository.findAll();
        assertThat(trainerEvaluationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEvaluationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainerEvaluationInfoRepository.findAll().size();
        // set the field null
        trainerEvaluationInfo.setEvaluationDate(null);

        // Create the TrainerEvaluationInfo, which fails.

        restTrainerEvaluationInfoMockMvc.perform(post("/api/trainerEvaluationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainerEvaluationInfo)))
                .andExpect(status().isBadRequest());

        List<TrainerEvaluationInfo> trainerEvaluationInfos = trainerEvaluationInfoRepository.findAll();
        assertThat(trainerEvaluationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrainerEvaluationInfos() throws Exception {
        // Initialize the database
        trainerEvaluationInfoRepository.saveAndFlush(trainerEvaluationInfo);

        // Get all the trainerEvaluationInfos
        restTrainerEvaluationInfoMockMvc.perform(get("/api/trainerEvaluationInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trainerEvaluationInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].sessionYear").value(hasItem(DEFAULT_SESSION_YEAR.toString())))
                .andExpect(jsonPath("$.[*].performance").value(hasItem(DEFAULT_PERFORMANCE.toString())))
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
    public void getTrainerEvaluationInfo() throws Exception {
        // Initialize the database
        trainerEvaluationInfoRepository.saveAndFlush(trainerEvaluationInfo);

        // Get the trainerEvaluationInfo
        restTrainerEvaluationInfoMockMvc.perform(get("/api/trainerEvaluationInfos/{id}", trainerEvaluationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trainerEvaluationInfo.getId().intValue()))
            .andExpect(jsonPath("$.sessionYear").value(DEFAULT_SESSION_YEAR.toString()))
            .andExpect(jsonPath("$.performance").value(DEFAULT_PERFORMANCE.toString()))
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
    public void getNonExistingTrainerEvaluationInfo() throws Exception {
        // Get the trainerEvaluationInfo
        restTrainerEvaluationInfoMockMvc.perform(get("/api/trainerEvaluationInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainerEvaluationInfo() throws Exception {
        // Initialize the database
        trainerEvaluationInfoRepository.saveAndFlush(trainerEvaluationInfo);

		int databaseSizeBeforeUpdate = trainerEvaluationInfoRepository.findAll().size();

        // Update the trainerEvaluationInfo
//        trainerEvaluationInfo.setSessionYear(UPDATED_SESSION_YEAR);
        trainerEvaluationInfo.setPerformance(UPDATED_PERFORMANCE);
        trainerEvaluationInfo.setRemarks(UPDATED_REMARKS);
        trainerEvaluationInfo.setEvaluationDate(UPDATED_EVALUATION_DATE);
        trainerEvaluationInfo.setStatus(UPDATED_STATUS);
        trainerEvaluationInfo.setCreateDate(UPDATED_CREATE_DATE);
        trainerEvaluationInfo.setCreateBy(UPDATED_CREATE_BY);
        trainerEvaluationInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        trainerEvaluationInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restTrainerEvaluationInfoMockMvc.perform(put("/api/trainerEvaluationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainerEvaluationInfo)))
                .andExpect(status().isOk());

        // Validate the TrainerEvaluationInfo in the database
        List<TrainerEvaluationInfo> trainerEvaluationInfos = trainerEvaluationInfoRepository.findAll();
        assertThat(trainerEvaluationInfos).hasSize(databaseSizeBeforeUpdate);
        TrainerEvaluationInfo testTrainerEvaluationInfo = trainerEvaluationInfos.get(trainerEvaluationInfos.size() - 1);
//        assertThat(testTrainerEvaluationInfo.getSessionYear()).isEqualTo(UPDATED_SESSION_YEAR);
        assertThat(testTrainerEvaluationInfo.getPerformance()).isEqualTo(UPDATED_PERFORMANCE);
        assertThat(testTrainerEvaluationInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testTrainerEvaluationInfo.getEvaluationDate()).isEqualTo(UPDATED_EVALUATION_DATE);
        assertThat(testTrainerEvaluationInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainerEvaluationInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTrainerEvaluationInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTrainerEvaluationInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testTrainerEvaluationInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteTrainerEvaluationInfo() throws Exception {
        // Initialize the database
        trainerEvaluationInfoRepository.saveAndFlush(trainerEvaluationInfo);

		int databaseSizeBeforeDelete = trainerEvaluationInfoRepository.findAll().size();

        // Get the trainerEvaluationInfo
        restTrainerEvaluationInfoMockMvc.perform(delete("/api/trainerEvaluationInfos/{id}", trainerEvaluationInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainerEvaluationInfo> trainerEvaluationInfos = trainerEvaluationInfoRepository.findAll();
        assertThat(trainerEvaluationInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
