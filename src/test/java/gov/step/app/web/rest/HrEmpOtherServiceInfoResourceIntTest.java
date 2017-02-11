package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpOtherServiceInfo;
import gov.step.app.repository.HrEmpOtherServiceInfoRepository;
import gov.step.app.repository.search.HrEmpOtherServiceInfoSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrEmpOtherServiceInfoResource REST controller.
 *
 * @see HrEmpOtherServiceInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpOtherServiceInfoResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_SERVICE_TYPE = "AAAAA";
    private static final String UPDATED_SERVICE_TYPE = "BBBBB";
    private static final String DEFAULT_POSITION = "AAAAA";
    private static final String UPDATED_POSITION = "BBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private HrEmpOtherServiceInfoRepository hrEmpOtherServiceInfoRepository;

    @Inject
    private HrEmpOtherServiceInfoSearchRepository hrEmpOtherServiceInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpOtherServiceInfoMockMvc;

    private HrEmpOtherServiceInfo hrEmpOtherServiceInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpOtherServiceInfoResource hrEmpOtherServiceInfoResource = new HrEmpOtherServiceInfoResource();
        ReflectionTestUtils.setField(hrEmpOtherServiceInfoResource, "hrEmpOtherServiceInfoSearchRepository", hrEmpOtherServiceInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpOtherServiceInfoResource, "hrEmpOtherServiceInfoRepository", hrEmpOtherServiceInfoRepository);
        this.restHrEmpOtherServiceInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpOtherServiceInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpOtherServiceInfo = new HrEmpOtherServiceInfo();
        hrEmpOtherServiceInfo.setCompanyName(DEFAULT_COMPANY_NAME);
        hrEmpOtherServiceInfo.setAddress(DEFAULT_ADDRESS);
        hrEmpOtherServiceInfo.setServiceType(DEFAULT_SERVICE_TYPE);
        hrEmpOtherServiceInfo.setPosition(DEFAULT_POSITION);
        hrEmpOtherServiceInfo.setFromDate(DEFAULT_FROM_DATE);
        hrEmpOtherServiceInfo.setToDate(DEFAULT_TO_DATE);
        hrEmpOtherServiceInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpOtherServiceInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpOtherServiceInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpOtherServiceInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpOtherServiceInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpOtherServiceInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpOtherServiceInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpOtherServiceInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpOtherServiceInfoRepository.findAll().size();

        // Create the HrEmpOtherServiceInfo

        restHrEmpOtherServiceInfoMockMvc.perform(post("/api/hrEmpOtherServiceInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpOtherServiceInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpOtherServiceInfo in the database
        List<HrEmpOtherServiceInfo> hrEmpOtherServiceInfos = hrEmpOtherServiceInfoRepository.findAll();
        assertThat(hrEmpOtherServiceInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpOtherServiceInfo testHrEmpOtherServiceInfo = hrEmpOtherServiceInfos.get(hrEmpOtherServiceInfos.size() - 1);
        assertThat(testHrEmpOtherServiceInfo.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testHrEmpOtherServiceInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHrEmpOtherServiceInfo.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
        assertThat(testHrEmpOtherServiceInfo.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testHrEmpOtherServiceInfo.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testHrEmpOtherServiceInfo.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testHrEmpOtherServiceInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpOtherServiceInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpOtherServiceInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpOtherServiceInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpOtherServiceInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpOtherServiceInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpOtherServiceInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpOtherServiceInfoRepository.findAll().size();
        // set the field null
        hrEmpOtherServiceInfo.setCompanyName(null);

        // Create the HrEmpOtherServiceInfo, which fails.

        restHrEmpOtherServiceInfoMockMvc.perform(post("/api/hrEmpOtherServiceInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpOtherServiceInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpOtherServiceInfo> hrEmpOtherServiceInfos = hrEmpOtherServiceInfoRepository.findAll();
        assertThat(hrEmpOtherServiceInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpOtherServiceInfoRepository.findAll().size();
        // set the field null
        hrEmpOtherServiceInfo.setServiceType(null);

        // Create the HrEmpOtherServiceInfo, which fails.

        restHrEmpOtherServiceInfoMockMvc.perform(post("/api/hrEmpOtherServiceInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpOtherServiceInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpOtherServiceInfo> hrEmpOtherServiceInfos = hrEmpOtherServiceInfoRepository.findAll();
        assertThat(hrEmpOtherServiceInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpOtherServiceInfoRepository.findAll().size();
        // set the field null
        hrEmpOtherServiceInfo.setPosition(null);

        // Create the HrEmpOtherServiceInfo, which fails.

        restHrEmpOtherServiceInfoMockMvc.perform(post("/api/hrEmpOtherServiceInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpOtherServiceInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpOtherServiceInfo> hrEmpOtherServiceInfos = hrEmpOtherServiceInfoRepository.findAll();
        assertThat(hrEmpOtherServiceInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpOtherServiceInfoRepository.findAll().size();
        // set the field null
        hrEmpOtherServiceInfo.setFromDate(null);

        // Create the HrEmpOtherServiceInfo, which fails.

        restHrEmpOtherServiceInfoMockMvc.perform(post("/api/hrEmpOtherServiceInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpOtherServiceInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpOtherServiceInfo> hrEmpOtherServiceInfos = hrEmpOtherServiceInfoRepository.findAll();
        assertThat(hrEmpOtherServiceInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpOtherServiceInfoRepository.findAll().size();
        // set the field null
        hrEmpOtherServiceInfo.setActiveStatus(null);

        // Create the HrEmpOtherServiceInfo, which fails.

        restHrEmpOtherServiceInfoMockMvc.perform(post("/api/hrEmpOtherServiceInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpOtherServiceInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpOtherServiceInfo> hrEmpOtherServiceInfos = hrEmpOtherServiceInfoRepository.findAll();
        assertThat(hrEmpOtherServiceInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpOtherServiceInfos() throws Exception {
        // Initialize the database
        hrEmpOtherServiceInfoRepository.saveAndFlush(hrEmpOtherServiceInfo);

        // Get all the hrEmpOtherServiceInfos
        restHrEmpOtherServiceInfoMockMvc.perform(get("/api/hrEmpOtherServiceInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpOtherServiceInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
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
    public void getHrEmpOtherServiceInfo() throws Exception {
        // Initialize the database
        hrEmpOtherServiceInfoRepository.saveAndFlush(hrEmpOtherServiceInfo);

        // Get the hrEmpOtherServiceInfo
        restHrEmpOtherServiceInfoMockMvc.perform(get("/api/hrEmpOtherServiceInfos/{id}", hrEmpOtherServiceInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpOtherServiceInfo.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.serviceType").value(DEFAULT_SERVICE_TYPE.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
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
    public void getNonExistingHrEmpOtherServiceInfo() throws Exception {
        // Get the hrEmpOtherServiceInfo
        restHrEmpOtherServiceInfoMockMvc.perform(get("/api/hrEmpOtherServiceInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpOtherServiceInfo() throws Exception {
        // Initialize the database
        hrEmpOtherServiceInfoRepository.saveAndFlush(hrEmpOtherServiceInfo);

		int databaseSizeBeforeUpdate = hrEmpOtherServiceInfoRepository.findAll().size();

        // Update the hrEmpOtherServiceInfo
        hrEmpOtherServiceInfo.setCompanyName(UPDATED_COMPANY_NAME);
        hrEmpOtherServiceInfo.setAddress(UPDATED_ADDRESS);
        hrEmpOtherServiceInfo.setServiceType(UPDATED_SERVICE_TYPE);
        hrEmpOtherServiceInfo.setPosition(UPDATED_POSITION);
        hrEmpOtherServiceInfo.setFromDate(UPDATED_FROM_DATE);
        hrEmpOtherServiceInfo.setToDate(UPDATED_TO_DATE);
        hrEmpOtherServiceInfo.setLogId(UPDATED_LOG_ID);
        hrEmpOtherServiceInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpOtherServiceInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpOtherServiceInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpOtherServiceInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpOtherServiceInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpOtherServiceInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpOtherServiceInfoMockMvc.perform(put("/api/hrEmpOtherServiceInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpOtherServiceInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpOtherServiceInfo in the database
        List<HrEmpOtherServiceInfo> hrEmpOtherServiceInfos = hrEmpOtherServiceInfoRepository.findAll();
        assertThat(hrEmpOtherServiceInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpOtherServiceInfo testHrEmpOtherServiceInfo = hrEmpOtherServiceInfos.get(hrEmpOtherServiceInfos.size() - 1);
        assertThat(testHrEmpOtherServiceInfo.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testHrEmpOtherServiceInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHrEmpOtherServiceInfo.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
        assertThat(testHrEmpOtherServiceInfo.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testHrEmpOtherServiceInfo.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testHrEmpOtherServiceInfo.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testHrEmpOtherServiceInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpOtherServiceInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpOtherServiceInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpOtherServiceInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpOtherServiceInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpOtherServiceInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpOtherServiceInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpOtherServiceInfo() throws Exception {
        // Initialize the database
        hrEmpOtherServiceInfoRepository.saveAndFlush(hrEmpOtherServiceInfo);

		int databaseSizeBeforeDelete = hrEmpOtherServiceInfoRepository.findAll().size();

        // Get the hrEmpOtherServiceInfo
        restHrEmpOtherServiceInfoMockMvc.perform(delete("/api/hrEmpOtherServiceInfos/{id}", hrEmpOtherServiceInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpOtherServiceInfo> hrEmpOtherServiceInfos = hrEmpOtherServiceInfoRepository.findAll();
        assertThat(hrEmpOtherServiceInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
