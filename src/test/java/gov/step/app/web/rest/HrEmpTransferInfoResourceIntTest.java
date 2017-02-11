package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpTransferInfo;
import gov.step.app.repository.HrEmpTransferInfoRepository;
import gov.step.app.repository.search.HrEmpTransferInfoSearchRepository;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the HrEmpTransferInfoResource REST controller.
 *
 * @see HrEmpTransferInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpTransferInfoResourceIntTest {

    private static final String DEFAULT_LOCATION_FROM = "AAAAA";
    private static final String UPDATED_LOCATION_FROM = "BBBBB";
    private static final String DEFAULT_LOCATION_TO = "AAAAA";
    private static final String UPDATED_LOCATION_TO = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_DEPARTMENT_FROM = "AAAAA";
    private static final String UPDATED_DEPARTMENT_FROM = "BBBBB";
    private static final String DEFAULT_DEPARTMENT_TO = "AAAAA";
    private static final String UPDATED_DEPARTMENT_TO = "BBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_OFFICE_ORDER_NO = "AAAAA";
    private static final String UPDATED_OFFICE_ORDER_NO = "BBBBB";

    private static final LocalDate DEFAULT_GO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_GO_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GO_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_GO_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GO_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_GO_DOC_NAME = "AAAAA";
    private static final String UPDATED_GO_DOC_NAME = "BBBBB";

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
    private HrEmpTransferInfoRepository hrEmpTransferInfoRepository;

    @Inject
    private HrEmpTransferInfoSearchRepository hrEmpTransferInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpTransferInfoMockMvc;

    private HrEmpTransferInfo hrEmpTransferInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpTransferInfoResource hrEmpTransferInfoResource = new HrEmpTransferInfoResource();
        ReflectionTestUtils.setField(hrEmpTransferInfoResource, "hrEmpTransferInfoSearchRepository", hrEmpTransferInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpTransferInfoResource, "hrEmpTransferInfoRepository", hrEmpTransferInfoRepository);
        this.restHrEmpTransferInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpTransferInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpTransferInfo = new HrEmpTransferInfo();
        hrEmpTransferInfo.setLocationFrom(DEFAULT_LOCATION_FROM);
        hrEmpTransferInfo.setLocationTo(DEFAULT_LOCATION_TO);
        hrEmpTransferInfo.setDesignation(DEFAULT_DESIGNATION);
        hrEmpTransferInfo.setDepartmentFrom(DEFAULT_DEPARTMENT_FROM);
        hrEmpTransferInfo.setDepartmentTo(DEFAULT_DEPARTMENT_TO);
        hrEmpTransferInfo.setFromDate(DEFAULT_FROM_DATE);
        hrEmpTransferInfo.setToDate(DEFAULT_TO_DATE);
        hrEmpTransferInfo.setOfficeOrderNo(DEFAULT_OFFICE_ORDER_NO);
        hrEmpTransferInfo.setGoDate(DEFAULT_GO_DATE);
        hrEmpTransferInfo.setGoDoc(DEFAULT_GO_DOC);
        hrEmpTransferInfo.setGoDocContentType(DEFAULT_GO_DOC_CONTENT_TYPE);
        hrEmpTransferInfo.setGoDocName(DEFAULT_GO_DOC_NAME);
        hrEmpTransferInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpTransferInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpTransferInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpTransferInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpTransferInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpTransferInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpTransferInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpTransferInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpTransferInfoRepository.findAll().size();

        // Create the HrEmpTransferInfo

        restHrEmpTransferInfoMockMvc.perform(post("/api/hrEmpTransferInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpTransferInfo in the database
        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpTransferInfo testHrEmpTransferInfo = hrEmpTransferInfos.get(hrEmpTransferInfos.size() - 1);
        assertThat(testHrEmpTransferInfo.getLocationFrom()).isEqualTo(DEFAULT_LOCATION_FROM);
        assertThat(testHrEmpTransferInfo.getLocationTo()).isEqualTo(DEFAULT_LOCATION_TO);
        assertThat(testHrEmpTransferInfo.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testHrEmpTransferInfo.getDepartmentFrom()).isEqualTo(DEFAULT_DEPARTMENT_FROM);
        assertThat(testHrEmpTransferInfo.getDepartmentTo()).isEqualTo(DEFAULT_DEPARTMENT_TO);
        assertThat(testHrEmpTransferInfo.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testHrEmpTransferInfo.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testHrEmpTransferInfo.getOfficeOrderNo()).isEqualTo(DEFAULT_OFFICE_ORDER_NO);
        assertThat(testHrEmpTransferInfo.getGoDate()).isEqualTo(DEFAULT_GO_DATE);
        assertThat(testHrEmpTransferInfo.getGoDoc()).isEqualTo(DEFAULT_GO_DOC);
        assertThat(testHrEmpTransferInfo.getGoDocContentType()).isEqualTo(DEFAULT_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpTransferInfo.getGoDocName()).isEqualTo(DEFAULT_GO_DOC_NAME);
        assertThat(testHrEmpTransferInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpTransferInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpTransferInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpTransferInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpTransferInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpTransferInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpTransferInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkLocationFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferInfo.setLocationFrom(null);

        // Create the HrEmpTransferInfo, which fails.

        restHrEmpTransferInfoMockMvc.perform(post("/api/hrEmpTransferInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationToIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferInfo.setLocationTo(null);

        // Create the HrEmpTransferInfo, which fails.

        restHrEmpTransferInfoMockMvc.perform(post("/api/hrEmpTransferInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferInfo.setDesignation(null);

        // Create the HrEmpTransferInfo, which fails.

        restHrEmpTransferInfoMockMvc.perform(post("/api/hrEmpTransferInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartmentFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferInfo.setDepartmentFrom(null);

        // Create the HrEmpTransferInfo, which fails.

        restHrEmpTransferInfoMockMvc.perform(post("/api/hrEmpTransferInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartmentToIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferInfo.setDepartmentTo(null);

        // Create the HrEmpTransferInfo, which fails.

        restHrEmpTransferInfoMockMvc.perform(post("/api/hrEmpTransferInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferInfo.setFromDate(null);

        // Create the HrEmpTransferInfo, which fails.

        restHrEmpTransferInfoMockMvc.perform(post("/api/hrEmpTransferInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferInfo.setToDate(null);

        // Create the HrEmpTransferInfo, which fails.

        restHrEmpTransferInfoMockMvc.perform(post("/api/hrEmpTransferInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferInfo.setActiveStatus(null);

        // Create the HrEmpTransferInfo, which fails.

        restHrEmpTransferInfoMockMvc.perform(post("/api/hrEmpTransferInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpTransferInfos() throws Exception {
        // Initialize the database
        hrEmpTransferInfoRepository.saveAndFlush(hrEmpTransferInfo);

        // Get all the hrEmpTransferInfos
        restHrEmpTransferInfoMockMvc.perform(get("/api/hrEmpTransferInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpTransferInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].locationFrom").value(hasItem(DEFAULT_LOCATION_FROM.toString())))
                .andExpect(jsonPath("$.[*].locationTo").value(hasItem(DEFAULT_LOCATION_TO.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].departmentFrom").value(hasItem(DEFAULT_DEPARTMENT_FROM.toString())))
                .andExpect(jsonPath("$.[*].departmentTo").value(hasItem(DEFAULT_DEPARTMENT_TO.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].officeOrderNo").value(hasItem(DEFAULT_OFFICE_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].goDate").value(hasItem(DEFAULT_GO_DATE.toString())))
                .andExpect(jsonPath("$.[*].goDocContentType").value(hasItem(DEFAULT_GO_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].goDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_GO_DOC))))
                .andExpect(jsonPath("$.[*].goDocName").value(hasItem(DEFAULT_GO_DOC_NAME.toString())))
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
    public void getHrEmpTransferInfo() throws Exception {
        // Initialize the database
        hrEmpTransferInfoRepository.saveAndFlush(hrEmpTransferInfo);

        // Get the hrEmpTransferInfo
        restHrEmpTransferInfoMockMvc.perform(get("/api/hrEmpTransferInfos/{id}", hrEmpTransferInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpTransferInfo.getId().intValue()))
            .andExpect(jsonPath("$.locationFrom").value(DEFAULT_LOCATION_FROM.toString()))
            .andExpect(jsonPath("$.locationTo").value(DEFAULT_LOCATION_TO.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.departmentFrom").value(DEFAULT_DEPARTMENT_FROM.toString()))
            .andExpect(jsonPath("$.departmentTo").value(DEFAULT_DEPARTMENT_TO.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.officeOrderNo").value(DEFAULT_OFFICE_ORDER_NO.toString()))
            .andExpect(jsonPath("$.goDate").value(DEFAULT_GO_DATE.toString()))
            .andExpect(jsonPath("$.goDocContentType").value(DEFAULT_GO_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.goDoc").value(Base64Utils.encodeToString(DEFAULT_GO_DOC)))
            .andExpect(jsonPath("$.goDocName").value(DEFAULT_GO_DOC_NAME.toString()))
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
    public void getNonExistingHrEmpTransferInfo() throws Exception {
        // Get the hrEmpTransferInfo
        restHrEmpTransferInfoMockMvc.perform(get("/api/hrEmpTransferInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpTransferInfo() throws Exception {
        // Initialize the database
        hrEmpTransferInfoRepository.saveAndFlush(hrEmpTransferInfo);

		int databaseSizeBeforeUpdate = hrEmpTransferInfoRepository.findAll().size();

        // Update the hrEmpTransferInfo
        hrEmpTransferInfo.setLocationFrom(UPDATED_LOCATION_FROM);
        hrEmpTransferInfo.setLocationTo(UPDATED_LOCATION_TO);
        hrEmpTransferInfo.setDesignation(UPDATED_DESIGNATION);
        hrEmpTransferInfo.setDepartmentFrom(UPDATED_DEPARTMENT_FROM);
        hrEmpTransferInfo.setDepartmentTo(UPDATED_DEPARTMENT_TO);
        hrEmpTransferInfo.setFromDate(UPDATED_FROM_DATE);
        hrEmpTransferInfo.setToDate(UPDATED_TO_DATE);
        hrEmpTransferInfo.setOfficeOrderNo(UPDATED_OFFICE_ORDER_NO);
        hrEmpTransferInfo.setGoDate(UPDATED_GO_DATE);
        hrEmpTransferInfo.setGoDoc(UPDATED_GO_DOC);
        hrEmpTransferInfo.setGoDocContentType(UPDATED_GO_DOC_CONTENT_TYPE);
        hrEmpTransferInfo.setGoDocName(UPDATED_GO_DOC_NAME);
        hrEmpTransferInfo.setLogId(UPDATED_LOG_ID);
        hrEmpTransferInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpTransferInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpTransferInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpTransferInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpTransferInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpTransferInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpTransferInfoMockMvc.perform(put("/api/hrEmpTransferInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpTransferInfo in the database
        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpTransferInfo testHrEmpTransferInfo = hrEmpTransferInfos.get(hrEmpTransferInfos.size() - 1);
        assertThat(testHrEmpTransferInfo.getLocationFrom()).isEqualTo(UPDATED_LOCATION_FROM);
        assertThat(testHrEmpTransferInfo.getLocationTo()).isEqualTo(UPDATED_LOCATION_TO);
        assertThat(testHrEmpTransferInfo.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testHrEmpTransferInfo.getDepartmentFrom()).isEqualTo(UPDATED_DEPARTMENT_FROM);
        assertThat(testHrEmpTransferInfo.getDepartmentTo()).isEqualTo(UPDATED_DEPARTMENT_TO);
        assertThat(testHrEmpTransferInfo.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testHrEmpTransferInfo.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testHrEmpTransferInfo.getOfficeOrderNo()).isEqualTo(UPDATED_OFFICE_ORDER_NO);
        assertThat(testHrEmpTransferInfo.getGoDate()).isEqualTo(UPDATED_GO_DATE);
        assertThat(testHrEmpTransferInfo.getGoDoc()).isEqualTo(UPDATED_GO_DOC);
        assertThat(testHrEmpTransferInfo.getGoDocContentType()).isEqualTo(UPDATED_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpTransferInfo.getGoDocName()).isEqualTo(UPDATED_GO_DOC_NAME);
        assertThat(testHrEmpTransferInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpTransferInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpTransferInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpTransferInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpTransferInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpTransferInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpTransferInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpTransferInfo() throws Exception {
        // Initialize the database
        hrEmpTransferInfoRepository.saveAndFlush(hrEmpTransferInfo);

		int databaseSizeBeforeDelete = hrEmpTransferInfoRepository.findAll().size();

        // Get the hrEmpTransferInfo
        restHrEmpTransferInfoMockMvc.perform(delete("/api/hrEmpTransferInfos/{id}", hrEmpTransferInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpTransferInfo> hrEmpTransferInfos = hrEmpTransferInfoRepository.findAll();
        assertThat(hrEmpTransferInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
