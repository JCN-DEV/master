package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpAcrInfo;
import gov.step.app.repository.HrEmpAcrInfoRepository;
import gov.step.app.repository.search.HrEmpAcrInfoSearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrEmpAcrInfoResource REST controller.
 *
 * @see HrEmpAcrInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpAcrInfoResourceIntTest {


    private static final Long DEFAULT_ACR_YEAR = 1l;
    private static final Long UPDATED_ACR_YEAR = 2l;

    private static final BigDecimal DEFAULT_TOTAL_MARKS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_MARKS = new BigDecimal(2);
    private static final String DEFAULT_OVERALL_EVALUATION = "AAAAA";
    private static final String UPDATED_OVERALL_EVALUATION = "BBBBB";
    private static final String DEFAULT_PROMOTION_STATUS = "AAAAA";
    private static final String UPDATED_PROMOTION_STATUS = "BBBBB";

    private static final LocalDate DEFAULT_ACR_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACR_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_LOG_ID = 1L;
    private static final Long UPDATED_LOG_ID = 2L;

    private static final Long DEFAULT_LOG_STATUS = 1L;
    private static final Long UPDATED_LOG_STATUS = 2L;

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private HrEmpAcrInfoRepository hrEmpAcrInfoRepository;

    @Inject
    private HrEmpAcrInfoSearchRepository hrEmpAcrInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpAcrInfoMockMvc;

    private HrEmpAcrInfo hrEmpAcrInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpAcrInfoResource hrEmpAcrInfoResource = new HrEmpAcrInfoResource();
        ReflectionTestUtils.setField(hrEmpAcrInfoResource, "hrEmpAcrInfoSearchRepository", hrEmpAcrInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpAcrInfoResource, "hrEmpAcrInfoRepository", hrEmpAcrInfoRepository);
        this.restHrEmpAcrInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpAcrInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpAcrInfo = new HrEmpAcrInfo();
        hrEmpAcrInfo.setAcrYear(DEFAULT_ACR_YEAR);
        hrEmpAcrInfo.setTotalMarks(DEFAULT_TOTAL_MARKS);
        hrEmpAcrInfo.setOverallEvaluation(DEFAULT_OVERALL_EVALUATION);
        hrEmpAcrInfo.setPromotionStatus(DEFAULT_PROMOTION_STATUS);
        hrEmpAcrInfo.setAcrDate(DEFAULT_ACR_DATE);
        hrEmpAcrInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpAcrInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpAcrInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpAcrInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpAcrInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpAcrInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpAcrInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpAcrInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpAcrInfoRepository.findAll().size();

        // Create the HrEmpAcrInfo

        restHrEmpAcrInfoMockMvc.perform(post("/api/hrEmpAcrInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAcrInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpAcrInfo in the database
        List<HrEmpAcrInfo> hrEmpAcrInfos = hrEmpAcrInfoRepository.findAll();
        assertThat(hrEmpAcrInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpAcrInfo testHrEmpAcrInfo = hrEmpAcrInfos.get(hrEmpAcrInfos.size() - 1);
        assertThat(testHrEmpAcrInfo.getAcrYear()).isEqualTo(DEFAULT_ACR_YEAR);
        assertThat(testHrEmpAcrInfo.getTotalMarks()).isEqualTo(DEFAULT_TOTAL_MARKS);
        assertThat(testHrEmpAcrInfo.getOverallEvaluation()).isEqualTo(DEFAULT_OVERALL_EVALUATION);
        assertThat(testHrEmpAcrInfo.getPromotionStatus()).isEqualTo(DEFAULT_PROMOTION_STATUS);
        assertThat(testHrEmpAcrInfo.getAcrDate()).isEqualTo(DEFAULT_ACR_DATE);
        assertThat(testHrEmpAcrInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpAcrInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpAcrInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpAcrInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpAcrInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpAcrInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpAcrInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkAcrYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAcrInfoRepository.findAll().size();
        // set the field null
        hrEmpAcrInfo.setAcrYear(null);

        // Create the HrEmpAcrInfo, which fails.

        restHrEmpAcrInfoMockMvc.perform(post("/api/hrEmpAcrInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAcrInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAcrInfo> hrEmpAcrInfos = hrEmpAcrInfoRepository.findAll();
        assertThat(hrEmpAcrInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalMarksIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAcrInfoRepository.findAll().size();
        // set the field null
        hrEmpAcrInfo.setTotalMarks(null);

        // Create the HrEmpAcrInfo, which fails.

        restHrEmpAcrInfoMockMvc.perform(post("/api/hrEmpAcrInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAcrInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAcrInfo> hrEmpAcrInfos = hrEmpAcrInfoRepository.findAll();
        assertThat(hrEmpAcrInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOverallEvaluationIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAcrInfoRepository.findAll().size();
        // set the field null
        hrEmpAcrInfo.setOverallEvaluation(null);

        // Create the HrEmpAcrInfo, which fails.

        restHrEmpAcrInfoMockMvc.perform(post("/api/hrEmpAcrInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAcrInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAcrInfo> hrEmpAcrInfos = hrEmpAcrInfoRepository.findAll();
        assertThat(hrEmpAcrInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAcrInfoRepository.findAll().size();
        // set the field null
        hrEmpAcrInfo.setActiveStatus(null);

        // Create the HrEmpAcrInfo, which fails.

        restHrEmpAcrInfoMockMvc.perform(post("/api/hrEmpAcrInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAcrInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAcrInfo> hrEmpAcrInfos = hrEmpAcrInfoRepository.findAll();
        assertThat(hrEmpAcrInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpAcrInfos() throws Exception {
        // Initialize the database
        hrEmpAcrInfoRepository.saveAndFlush(hrEmpAcrInfo);

        // Get all the hrEmpAcrInfos
        restHrEmpAcrInfoMockMvc.perform(get("/api/hrEmpAcrInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpAcrInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].acrYear").value(hasItem(DEFAULT_ACR_YEAR)))
                .andExpect(jsonPath("$.[*].totalMarks").value(hasItem(DEFAULT_TOTAL_MARKS.intValue())))
                .andExpect(jsonPath("$.[*].overallEvaluation").value(hasItem(DEFAULT_OVERALL_EVALUATION.toString())))
                .andExpect(jsonPath("$.[*].promotionStatus").value(hasItem(DEFAULT_PROMOTION_STATUS.toString())))
                .andExpect(jsonPath("$.[*].acrDate").value(hasItem(DEFAULT_ACR_DATE.toString())))
                .andExpect(jsonPath("$.[*].logId").value(hasItem(DEFAULT_LOG_ID.intValue())))
                .andExpect(jsonPath("$.[*].logStatus").value(hasItem(DEFAULT_LOG_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmpAcrInfo() throws Exception {
        // Initialize the database
        hrEmpAcrInfoRepository.saveAndFlush(hrEmpAcrInfo);

        // Get the hrEmpAcrInfo
        restHrEmpAcrInfoMockMvc.perform(get("/api/hrEmpAcrInfos/{id}", hrEmpAcrInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpAcrInfo.getId().intValue()))
            .andExpect(jsonPath("$.acrYear").value(DEFAULT_ACR_YEAR))
            .andExpect(jsonPath("$.totalMarks").value(DEFAULT_TOTAL_MARKS.intValue()))
            .andExpect(jsonPath("$.overallEvaluation").value(DEFAULT_OVERALL_EVALUATION.toString()))
            .andExpect(jsonPath("$.promotionStatus").value(DEFAULT_PROMOTION_STATUS.toString()))
            .andExpect(jsonPath("$.acrDate").value(DEFAULT_ACR_DATE.toString()))
            .andExpect(jsonPath("$.logId").value(DEFAULT_LOG_ID.intValue()))
            .andExpect(jsonPath("$.logStatus").value(DEFAULT_LOG_STATUS.intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmpAcrInfo() throws Exception {
        // Get the hrEmpAcrInfo
        restHrEmpAcrInfoMockMvc.perform(get("/api/hrEmpAcrInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpAcrInfo() throws Exception {
        // Initialize the database
        hrEmpAcrInfoRepository.saveAndFlush(hrEmpAcrInfo);

		int databaseSizeBeforeUpdate = hrEmpAcrInfoRepository.findAll().size();

        // Update the hrEmpAcrInfo
        hrEmpAcrInfo.setAcrYear(UPDATED_ACR_YEAR);
        hrEmpAcrInfo.setTotalMarks(UPDATED_TOTAL_MARKS);
        hrEmpAcrInfo.setOverallEvaluation(UPDATED_OVERALL_EVALUATION);
        hrEmpAcrInfo.setPromotionStatus(UPDATED_PROMOTION_STATUS);
        hrEmpAcrInfo.setAcrDate(UPDATED_ACR_DATE);
        hrEmpAcrInfo.setLogId(UPDATED_LOG_ID);
        hrEmpAcrInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpAcrInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpAcrInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpAcrInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpAcrInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpAcrInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpAcrInfoMockMvc.perform(put("/api/hrEmpAcrInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAcrInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpAcrInfo in the database
        List<HrEmpAcrInfo> hrEmpAcrInfos = hrEmpAcrInfoRepository.findAll();
        assertThat(hrEmpAcrInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpAcrInfo testHrEmpAcrInfo = hrEmpAcrInfos.get(hrEmpAcrInfos.size() - 1);
        assertThat(testHrEmpAcrInfo.getAcrYear()).isEqualTo(UPDATED_ACR_YEAR);
        assertThat(testHrEmpAcrInfo.getTotalMarks()).isEqualTo(UPDATED_TOTAL_MARKS);
        assertThat(testHrEmpAcrInfo.getOverallEvaluation()).isEqualTo(UPDATED_OVERALL_EVALUATION);
        assertThat(testHrEmpAcrInfo.getPromotionStatus()).isEqualTo(UPDATED_PROMOTION_STATUS);
        assertThat(testHrEmpAcrInfo.getAcrDate()).isEqualTo(UPDATED_ACR_DATE);
        assertThat(testHrEmpAcrInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpAcrInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpAcrInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpAcrInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpAcrInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpAcrInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpAcrInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpAcrInfo() throws Exception {
        // Initialize the database
        hrEmpAcrInfoRepository.saveAndFlush(hrEmpAcrInfo);

		int databaseSizeBeforeDelete = hrEmpAcrInfoRepository.findAll().size();

        // Get the hrEmpAcrInfo
        restHrEmpAcrInfoMockMvc.perform(delete("/api/hrEmpAcrInfos/{id}", hrEmpAcrInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpAcrInfo> hrEmpAcrInfos = hrEmpAcrInfoRepository.findAll();
        assertThat(hrEmpAcrInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
