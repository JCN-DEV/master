package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpPreGovtJobInfo;
import gov.step.app.repository.HrEmpPreGovtJobInfoRepository;
import gov.step.app.repository.search.HrEmpPreGovtJobInfoSearchRepository;
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
 * Test class for the HrEmpPreGovtJobInfoResource REST controller.
 *
 * @see HrEmpPreGovtJobInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpPreGovtJobInfoResourceIntTest {

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBB";
    private static final String DEFAULT_POST_NAME = "AAAAA";
    private static final String UPDATED_POST_NAME = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALARY = new BigDecimal(2);
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

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
    private HrEmpPreGovtJobInfoRepository hrEmpPreGovtJobInfoRepository;

    @Inject
    private HrEmpPreGovtJobInfoSearchRepository hrEmpPreGovtJobInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpPreGovtJobInfoMockMvc;

    private HrEmpPreGovtJobInfo hrEmpPreGovtJobInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpPreGovtJobInfoResource hrEmpPreGovtJobInfoResource = new HrEmpPreGovtJobInfoResource();
        ReflectionTestUtils.setField(hrEmpPreGovtJobInfoResource, "hrEmpPreGovtJobInfoSearchRepository", hrEmpPreGovtJobInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpPreGovtJobInfoResource, "hrEmpPreGovtJobInfoRepository", hrEmpPreGovtJobInfoRepository);
        this.restHrEmpPreGovtJobInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpPreGovtJobInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpPreGovtJobInfo = new HrEmpPreGovtJobInfo();
        hrEmpPreGovtJobInfo.setOrganizationName(DEFAULT_ORGANIZATION_NAME);
        hrEmpPreGovtJobInfo.setPostName(DEFAULT_POST_NAME);
        hrEmpPreGovtJobInfo.setAddress(DEFAULT_ADDRESS);
        hrEmpPreGovtJobInfo.setFromDate(DEFAULT_FROM_DATE);
        hrEmpPreGovtJobInfo.setToDate(DEFAULT_TO_DATE);
        hrEmpPreGovtJobInfo.setSalary(DEFAULT_SALARY);
        hrEmpPreGovtJobInfo.setComments(DEFAULT_COMMENTS);
        hrEmpPreGovtJobInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpPreGovtJobInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpPreGovtJobInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpPreGovtJobInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpPreGovtJobInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpPreGovtJobInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpPreGovtJobInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpPreGovtJobInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpPreGovtJobInfoRepository.findAll().size();

        // Create the HrEmpPreGovtJobInfo

        restHrEmpPreGovtJobInfoMockMvc.perform(post("/api/hrEmpPreGovtJobInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPreGovtJobInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpPreGovtJobInfo in the database
        List<HrEmpPreGovtJobInfo> hrEmpPreGovtJobInfos = hrEmpPreGovtJobInfoRepository.findAll();
        assertThat(hrEmpPreGovtJobInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpPreGovtJobInfo testHrEmpPreGovtJobInfo = hrEmpPreGovtJobInfos.get(hrEmpPreGovtJobInfos.size() - 1);
        assertThat(testHrEmpPreGovtJobInfo.getOrganizationName()).isEqualTo(DEFAULT_ORGANIZATION_NAME);
        assertThat(testHrEmpPreGovtJobInfo.getPostName()).isEqualTo(DEFAULT_POST_NAME);
        assertThat(testHrEmpPreGovtJobInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHrEmpPreGovtJobInfo.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testHrEmpPreGovtJobInfo.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testHrEmpPreGovtJobInfo.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testHrEmpPreGovtJobInfo.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testHrEmpPreGovtJobInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpPreGovtJobInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpPreGovtJobInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpPreGovtJobInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpPreGovtJobInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpPreGovtJobInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpPreGovtJobInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkOrganizationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPreGovtJobInfoRepository.findAll().size();
        // set the field null
        hrEmpPreGovtJobInfo.setOrganizationName(null);

        // Create the HrEmpPreGovtJobInfo, which fails.

        restHrEmpPreGovtJobInfoMockMvc.perform(post("/api/hrEmpPreGovtJobInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPreGovtJobInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPreGovtJobInfo> hrEmpPreGovtJobInfos = hrEmpPreGovtJobInfoRepository.findAll();
        assertThat(hrEmpPreGovtJobInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPreGovtJobInfoRepository.findAll().size();
        // set the field null
        hrEmpPreGovtJobInfo.setPostName(null);

        // Create the HrEmpPreGovtJobInfo, which fails.

        restHrEmpPreGovtJobInfoMockMvc.perform(post("/api/hrEmpPreGovtJobInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPreGovtJobInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPreGovtJobInfo> hrEmpPreGovtJobInfos = hrEmpPreGovtJobInfoRepository.findAll();
        assertThat(hrEmpPreGovtJobInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPreGovtJobInfoRepository.findAll().size();
        // set the field null
        hrEmpPreGovtJobInfo.setAddress(null);

        // Create the HrEmpPreGovtJobInfo, which fails.

        restHrEmpPreGovtJobInfoMockMvc.perform(post("/api/hrEmpPreGovtJobInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPreGovtJobInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPreGovtJobInfo> hrEmpPreGovtJobInfos = hrEmpPreGovtJobInfoRepository.findAll();
        assertThat(hrEmpPreGovtJobInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPreGovtJobInfoRepository.findAll().size();
        // set the field null
        hrEmpPreGovtJobInfo.setFromDate(null);

        // Create the HrEmpPreGovtJobInfo, which fails.

        restHrEmpPreGovtJobInfoMockMvc.perform(post("/api/hrEmpPreGovtJobInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPreGovtJobInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPreGovtJobInfo> hrEmpPreGovtJobInfos = hrEmpPreGovtJobInfoRepository.findAll();
        assertThat(hrEmpPreGovtJobInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPreGovtJobInfoRepository.findAll().size();
        // set the field null
        hrEmpPreGovtJobInfo.setToDate(null);

        // Create the HrEmpPreGovtJobInfo, which fails.

        restHrEmpPreGovtJobInfoMockMvc.perform(post("/api/hrEmpPreGovtJobInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPreGovtJobInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPreGovtJobInfo> hrEmpPreGovtJobInfos = hrEmpPreGovtJobInfoRepository.findAll();
        assertThat(hrEmpPreGovtJobInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPreGovtJobInfoRepository.findAll().size();
        // set the field null
        hrEmpPreGovtJobInfo.setSalary(null);

        // Create the HrEmpPreGovtJobInfo, which fails.

        restHrEmpPreGovtJobInfoMockMvc.perform(post("/api/hrEmpPreGovtJobInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPreGovtJobInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPreGovtJobInfo> hrEmpPreGovtJobInfos = hrEmpPreGovtJobInfoRepository.findAll();
        assertThat(hrEmpPreGovtJobInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPreGovtJobInfoRepository.findAll().size();
        // set the field null
        hrEmpPreGovtJobInfo.setActiveStatus(null);

        // Create the HrEmpPreGovtJobInfo, which fails.

        restHrEmpPreGovtJobInfoMockMvc.perform(post("/api/hrEmpPreGovtJobInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPreGovtJobInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPreGovtJobInfo> hrEmpPreGovtJobInfos = hrEmpPreGovtJobInfoRepository.findAll();
        assertThat(hrEmpPreGovtJobInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpPreGovtJobInfos() throws Exception {
        // Initialize the database
        hrEmpPreGovtJobInfoRepository.saveAndFlush(hrEmpPreGovtJobInfo);

        // Get all the hrEmpPreGovtJobInfos
        restHrEmpPreGovtJobInfoMockMvc.perform(get("/api/hrEmpPreGovtJobInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpPreGovtJobInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME.toString())))
                .andExpect(jsonPath("$.[*].postName").value(hasItem(DEFAULT_POST_NAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
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
    public void getHrEmpPreGovtJobInfo() throws Exception {
        // Initialize the database
        hrEmpPreGovtJobInfoRepository.saveAndFlush(hrEmpPreGovtJobInfo);

        // Get the hrEmpPreGovtJobInfo
        restHrEmpPreGovtJobInfoMockMvc.perform(get("/api/hrEmpPreGovtJobInfos/{id}", hrEmpPreGovtJobInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpPreGovtJobInfo.getId().intValue()))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME.toString()))
            .andExpect(jsonPath("$.postName").value(DEFAULT_POST_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
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
    public void getNonExistingHrEmpPreGovtJobInfo() throws Exception {
        // Get the hrEmpPreGovtJobInfo
        restHrEmpPreGovtJobInfoMockMvc.perform(get("/api/hrEmpPreGovtJobInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpPreGovtJobInfo() throws Exception {
        // Initialize the database
        hrEmpPreGovtJobInfoRepository.saveAndFlush(hrEmpPreGovtJobInfo);

		int databaseSizeBeforeUpdate = hrEmpPreGovtJobInfoRepository.findAll().size();

        // Update the hrEmpPreGovtJobInfo
        hrEmpPreGovtJobInfo.setOrganizationName(UPDATED_ORGANIZATION_NAME);
        hrEmpPreGovtJobInfo.setPostName(UPDATED_POST_NAME);
        hrEmpPreGovtJobInfo.setAddress(UPDATED_ADDRESS);
        hrEmpPreGovtJobInfo.setFromDate(UPDATED_FROM_DATE);
        hrEmpPreGovtJobInfo.setToDate(UPDATED_TO_DATE);
        hrEmpPreGovtJobInfo.setSalary(UPDATED_SALARY);
        hrEmpPreGovtJobInfo.setComments(UPDATED_COMMENTS);
        hrEmpPreGovtJobInfo.setLogId(UPDATED_LOG_ID);
        hrEmpPreGovtJobInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpPreGovtJobInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpPreGovtJobInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpPreGovtJobInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpPreGovtJobInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpPreGovtJobInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpPreGovtJobInfoMockMvc.perform(put("/api/hrEmpPreGovtJobInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPreGovtJobInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpPreGovtJobInfo in the database
        List<HrEmpPreGovtJobInfo> hrEmpPreGovtJobInfos = hrEmpPreGovtJobInfoRepository.findAll();
        assertThat(hrEmpPreGovtJobInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpPreGovtJobInfo testHrEmpPreGovtJobInfo = hrEmpPreGovtJobInfos.get(hrEmpPreGovtJobInfos.size() - 1);
        assertThat(testHrEmpPreGovtJobInfo.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testHrEmpPreGovtJobInfo.getPostName()).isEqualTo(UPDATED_POST_NAME);
        assertThat(testHrEmpPreGovtJobInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHrEmpPreGovtJobInfo.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testHrEmpPreGovtJobInfo.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testHrEmpPreGovtJobInfo.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testHrEmpPreGovtJobInfo.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testHrEmpPreGovtJobInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpPreGovtJobInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpPreGovtJobInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpPreGovtJobInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpPreGovtJobInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpPreGovtJobInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpPreGovtJobInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpPreGovtJobInfo() throws Exception {
        // Initialize the database
        hrEmpPreGovtJobInfoRepository.saveAndFlush(hrEmpPreGovtJobInfo);

		int databaseSizeBeforeDelete = hrEmpPreGovtJobInfoRepository.findAll().size();

        // Get the hrEmpPreGovtJobInfo
        restHrEmpPreGovtJobInfoMockMvc.perform(delete("/api/hrEmpPreGovtJobInfos/{id}", hrEmpPreGovtJobInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpPreGovtJobInfo> hrEmpPreGovtJobInfos = hrEmpPreGovtJobInfoRepository.findAll();
        assertThat(hrEmpPreGovtJobInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
