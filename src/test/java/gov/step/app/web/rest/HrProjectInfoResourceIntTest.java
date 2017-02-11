package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrProjectInfo;
import gov.step.app.repository.HrProjectInfoRepository;
import gov.step.app.repository.search.HrProjectInfoSearchRepository;
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
 * Test class for the HrProjectInfoResource REST controller.
 *
 * @see HrProjectInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrProjectInfoResourceIntTest {

    private static final String DEFAULT_PROJECT_NAME = "AAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBB";
    private static final String DEFAULT_PROJECT_DETAIL = "AAAAA";
    private static final String UPDATED_PROJECT_DETAIL = "BBBBB";
    private static final String DEFAULT_DIRECTOR_NAME = "AAAAA";
    private static final String UPDATED_DIRECTOR_NAME = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PROJECT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROJECT_VALUE = new BigDecimal(2);
    private static final String DEFAULT_PROJECT_STATUS = "AAAAA";
    private static final String UPDATED_PROJECT_STATUS = "BBBBB";

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
    private HrProjectInfoRepository hrProjectInfoRepository;

    @Inject
    private HrProjectInfoSearchRepository hrProjectInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrProjectInfoMockMvc;

    private HrProjectInfo hrProjectInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrProjectInfoResource hrProjectInfoResource = new HrProjectInfoResource();
        ReflectionTestUtils.setField(hrProjectInfoResource, "hrProjectInfoSearchRepository", hrProjectInfoSearchRepository);
        ReflectionTestUtils.setField(hrProjectInfoResource, "hrProjectInfoRepository", hrProjectInfoRepository);
        this.restHrProjectInfoMockMvc = MockMvcBuilders.standaloneSetup(hrProjectInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrProjectInfo = new HrProjectInfo();
        hrProjectInfo.setProjectName(DEFAULT_PROJECT_NAME);
        hrProjectInfo.setProjectDetail(DEFAULT_PROJECT_DETAIL);
        hrProjectInfo.setDirectorName(DEFAULT_DIRECTOR_NAME);
        hrProjectInfo.setStartDate(DEFAULT_START_DATE);
        hrProjectInfo.setEndDate(DEFAULT_END_DATE);
        hrProjectInfo.setProjectValue(DEFAULT_PROJECT_VALUE);
        hrProjectInfo.setProjectStatus(DEFAULT_PROJECT_STATUS);
        hrProjectInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrProjectInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrProjectInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrProjectInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrProjectInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrProjectInfo() throws Exception {
        int databaseSizeBeforeCreate = hrProjectInfoRepository.findAll().size();

        // Create the HrProjectInfo

        restHrProjectInfoMockMvc.perform(post("/api/hrProjectInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrProjectInfo)))
                .andExpect(status().isCreated());

        // Validate the HrProjectInfo in the database
        List<HrProjectInfo> hrProjectInfos = hrProjectInfoRepository.findAll();
        assertThat(hrProjectInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrProjectInfo testHrProjectInfo = hrProjectInfos.get(hrProjectInfos.size() - 1);
        assertThat(testHrProjectInfo.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testHrProjectInfo.getProjectDetail()).isEqualTo(DEFAULT_PROJECT_DETAIL);
        assertThat(testHrProjectInfo.getDirectorName()).isEqualTo(DEFAULT_DIRECTOR_NAME);
        assertThat(testHrProjectInfo.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testHrProjectInfo.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testHrProjectInfo.getProjectValue()).isEqualTo(DEFAULT_PROJECT_VALUE);
        assertThat(testHrProjectInfo.getProjectStatus()).isEqualTo(DEFAULT_PROJECT_STATUS);
        assertThat(testHrProjectInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrProjectInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrProjectInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrProjectInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrProjectInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkProjectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrProjectInfoRepository.findAll().size();
        // set the field null
        hrProjectInfo.setProjectName(null);

        // Create the HrProjectInfo, which fails.

        restHrProjectInfoMockMvc.perform(post("/api/hrProjectInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrProjectInfo)))
                .andExpect(status().isBadRequest());

        List<HrProjectInfo> hrProjectInfos = hrProjectInfoRepository.findAll();
        assertThat(hrProjectInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDirectorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrProjectInfoRepository.findAll().size();
        // set the field null
        hrProjectInfo.setDirectorName(null);

        // Create the HrProjectInfo, which fails.

        restHrProjectInfoMockMvc.perform(post("/api/hrProjectInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrProjectInfo)))
                .andExpect(status().isBadRequest());

        List<HrProjectInfo> hrProjectInfos = hrProjectInfoRepository.findAll();
        assertThat(hrProjectInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrProjectInfoRepository.findAll().size();
        // set the field null
        hrProjectInfo.setProjectValue(null);

        // Create the HrProjectInfo, which fails.

        restHrProjectInfoMockMvc.perform(post("/api/hrProjectInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrProjectInfo)))
                .andExpect(status().isBadRequest());

        List<HrProjectInfo> hrProjectInfos = hrProjectInfoRepository.findAll();
        assertThat(hrProjectInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrProjectInfoRepository.findAll().size();
        // set the field null
        hrProjectInfo.setProjectStatus(null);

        // Create the HrProjectInfo, which fails.

        restHrProjectInfoMockMvc.perform(post("/api/hrProjectInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrProjectInfo)))
                .andExpect(status().isBadRequest());

        List<HrProjectInfo> hrProjectInfos = hrProjectInfoRepository.findAll();
        assertThat(hrProjectInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrProjectInfos() throws Exception {
        // Initialize the database
        hrProjectInfoRepository.saveAndFlush(hrProjectInfo);

        // Get all the hrProjectInfos
        restHrProjectInfoMockMvc.perform(get("/api/hrProjectInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrProjectInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
                .andExpect(jsonPath("$.[*].projectDetail").value(hasItem(DEFAULT_PROJECT_DETAIL.toString())))
                .andExpect(jsonPath("$.[*].directorName").value(hasItem(DEFAULT_DIRECTOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].projectValue").value(hasItem(DEFAULT_PROJECT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].projectStatus").value(hasItem(DEFAULT_PROJECT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrProjectInfo() throws Exception {
        // Initialize the database
        hrProjectInfoRepository.saveAndFlush(hrProjectInfo);

        // Get the hrProjectInfo
        restHrProjectInfoMockMvc.perform(get("/api/hrProjectInfos/{id}", hrProjectInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrProjectInfo.getId().intValue()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.projectDetail").value(DEFAULT_PROJECT_DETAIL.toString()))
            .andExpect(jsonPath("$.directorName").value(DEFAULT_DIRECTOR_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.projectValue").value(DEFAULT_PROJECT_VALUE.intValue()))
            .andExpect(jsonPath("$.projectStatus").value(DEFAULT_PROJECT_STATUS.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrProjectInfo() throws Exception {
        // Get the hrProjectInfo
        restHrProjectInfoMockMvc.perform(get("/api/hrProjectInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrProjectInfo() throws Exception {
        // Initialize the database
        hrProjectInfoRepository.saveAndFlush(hrProjectInfo);

		int databaseSizeBeforeUpdate = hrProjectInfoRepository.findAll().size();

        // Update the hrProjectInfo
        hrProjectInfo.setProjectName(UPDATED_PROJECT_NAME);
        hrProjectInfo.setProjectDetail(UPDATED_PROJECT_DETAIL);
        hrProjectInfo.setDirectorName(UPDATED_DIRECTOR_NAME);
        hrProjectInfo.setStartDate(UPDATED_START_DATE);
        hrProjectInfo.setEndDate(UPDATED_END_DATE);
        hrProjectInfo.setProjectValue(UPDATED_PROJECT_VALUE);
        hrProjectInfo.setProjectStatus(UPDATED_PROJECT_STATUS);
        hrProjectInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrProjectInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrProjectInfo.setCreateBy(UPDATED_CREATE_BY);
        hrProjectInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrProjectInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrProjectInfoMockMvc.perform(put("/api/hrProjectInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrProjectInfo)))
                .andExpect(status().isOk());

        // Validate the HrProjectInfo in the database
        List<HrProjectInfo> hrProjectInfos = hrProjectInfoRepository.findAll();
        assertThat(hrProjectInfos).hasSize(databaseSizeBeforeUpdate);
        HrProjectInfo testHrProjectInfo = hrProjectInfos.get(hrProjectInfos.size() - 1);
        assertThat(testHrProjectInfo.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testHrProjectInfo.getProjectDetail()).isEqualTo(UPDATED_PROJECT_DETAIL);
        assertThat(testHrProjectInfo.getDirectorName()).isEqualTo(UPDATED_DIRECTOR_NAME);
        assertThat(testHrProjectInfo.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testHrProjectInfo.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testHrProjectInfo.getProjectValue()).isEqualTo(UPDATED_PROJECT_VALUE);
        assertThat(testHrProjectInfo.getProjectStatus()).isEqualTo(UPDATED_PROJECT_STATUS);
        assertThat(testHrProjectInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrProjectInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrProjectInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrProjectInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrProjectInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrProjectInfo() throws Exception {
        // Initialize the database
        hrProjectInfoRepository.saveAndFlush(hrProjectInfo);

		int databaseSizeBeforeDelete = hrProjectInfoRepository.findAll().size();

        // Get the hrProjectInfo
        restHrProjectInfoMockMvc.perform(delete("/api/hrProjectInfos/{id}", hrProjectInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrProjectInfo> hrProjectInfos = hrProjectInfoRepository.findAll();
        assertThat(hrProjectInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
