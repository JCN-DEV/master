package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpPromotionInfo;
import gov.step.app.repository.HrEmpPromotionInfoRepository;
import gov.step.app.repository.search.HrEmpPromotionInfoSearchRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrEmpPromotionInfoResource REST controller.
 *
 * @see HrEmpPromotionInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpPromotionInfoResourceIntTest {

    private static final String DEFAULT_INSTITUTE_FROM = "AAAAA";
    private static final String UPDATED_INSTITUTE_FROM = "BBBBB";
    private static final String DEFAULT_INSTITUTE_TO = "AAAAA";
    private static final String UPDATED_INSTITUTE_TO = "BBBBB";
    private static final String DEFAULT_DEPARTMENT_FROM = "AAAAA";
    private static final String UPDATED_DEPARTMENT_FROM = "BBBBB";
    private static final String DEFAULT_DEPARTMENT_TO = "AAAAA";
    private static final String UPDATED_DEPARTMENT_TO = "BBBBB";
    private static final String DEFAULT_DESIGNATION_FROM = "AAAAA";
    private static final String UPDATED_DESIGNATION_FROM = "BBBBB";
    private static final String DEFAULT_DESIGNATION_TO = "AAAAA";
    private static final String UPDATED_DESIGNATION_TO = "BBBBB";

    private static final BigDecimal DEFAULT_PAYSCALE_FROM = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYSCALE_FROM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYSCALE_TO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYSCALE_TO = new BigDecimal(2);

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_PROMOTION_TYPE = "AAAAA";
    private static final String UPDATED_PROMOTION_TYPE = "BBBBB";
    private static final String DEFAULT_OFFICE_ORDER_NO = "AAAAA";
    private static final String UPDATED_OFFICE_ORDER_NO = "BBBBB";

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private HrEmpPromotionInfoRepository hrEmpPromotionInfoRepository;

    @Inject
    private HrEmpPromotionInfoSearchRepository hrEmpPromotionInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpPromotionInfoMockMvc;

    private HrEmpPromotionInfo hrEmpPromotionInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpPromotionInfoResource hrEmpPromotionInfoResource = new HrEmpPromotionInfoResource();
        ReflectionTestUtils.setField(hrEmpPromotionInfoResource, "hrEmpPromotionInfoSearchRepository", hrEmpPromotionInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpPromotionInfoResource, "hrEmpPromotionInfoRepository", hrEmpPromotionInfoRepository);
        this.restHrEmpPromotionInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpPromotionInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpPromotionInfo = new HrEmpPromotionInfo();
        hrEmpPromotionInfo.setInstituteFrom(DEFAULT_INSTITUTE_FROM);
        hrEmpPromotionInfo.setInstituteTo(DEFAULT_INSTITUTE_TO);
        hrEmpPromotionInfo.setDepartmentFrom(DEFAULT_DEPARTMENT_FROM);
        hrEmpPromotionInfo.setDepartmentTo(DEFAULT_DEPARTMENT_TO);
        hrEmpPromotionInfo.setDesignationFrom(DEFAULT_DESIGNATION_FROM);
        hrEmpPromotionInfo.setDesignationTo(DEFAULT_DESIGNATION_TO);
        hrEmpPromotionInfo.setPayscaleFrom(DEFAULT_PAYSCALE_FROM);
        hrEmpPromotionInfo.setPayscaleTo(DEFAULT_PAYSCALE_TO);
        hrEmpPromotionInfo.setJoiningDate(DEFAULT_JOINING_DATE);
        hrEmpPromotionInfo.setPromotionType(DEFAULT_PROMOTION_TYPE);
        hrEmpPromotionInfo.setOfficeOrderNo(DEFAULT_OFFICE_ORDER_NO);
        hrEmpPromotionInfo.setOrderDate(DEFAULT_ORDER_DATE);
        hrEmpPromotionInfo.setGoDate(DEFAULT_GO_DATE);
        hrEmpPromotionInfo.setGoDoc(DEFAULT_GO_DOC);
        hrEmpPromotionInfo.setGoDocContentType(DEFAULT_GO_DOC_CONTENT_TYPE);
        hrEmpPromotionInfo.setGoDocName(DEFAULT_GO_DOC_NAME);
        hrEmpPromotionInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpPromotionInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpPromotionInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpPromotionInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpPromotionInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpPromotionInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpPromotionInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpPromotionInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpPromotionInfoRepository.findAll().size();

        // Create the HrEmpPromotionInfo

        restHrEmpPromotionInfoMockMvc.perform(post("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpPromotionInfo in the database
        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpPromotionInfo testHrEmpPromotionInfo = hrEmpPromotionInfos.get(hrEmpPromotionInfos.size() - 1);
        assertThat(testHrEmpPromotionInfo.getInstituteFrom()).isEqualTo(DEFAULT_INSTITUTE_FROM);
        assertThat(testHrEmpPromotionInfo.getInstituteTo()).isEqualTo(DEFAULT_INSTITUTE_TO);
        assertThat(testHrEmpPromotionInfo.getDepartmentFrom()).isEqualTo(DEFAULT_DEPARTMENT_FROM);
        assertThat(testHrEmpPromotionInfo.getDepartmentTo()).isEqualTo(DEFAULT_DEPARTMENT_TO);
        assertThat(testHrEmpPromotionInfo.getDesignationFrom()).isEqualTo(DEFAULT_DESIGNATION_FROM);
        assertThat(testHrEmpPromotionInfo.getDesignationTo()).isEqualTo(DEFAULT_DESIGNATION_TO);
        assertThat(testHrEmpPromotionInfo.getPayscaleFrom()).isEqualTo(DEFAULT_PAYSCALE_FROM);
        assertThat(testHrEmpPromotionInfo.getPayscaleTo()).isEqualTo(DEFAULT_PAYSCALE_TO);
        assertThat(testHrEmpPromotionInfo.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testHrEmpPromotionInfo.getPromotionType()).isEqualTo(DEFAULT_PROMOTION_TYPE);
        assertThat(testHrEmpPromotionInfo.getOfficeOrderNo()).isEqualTo(DEFAULT_OFFICE_ORDER_NO);
        assertThat(testHrEmpPromotionInfo.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testHrEmpPromotionInfo.getGoDate()).isEqualTo(DEFAULT_GO_DATE);
        assertThat(testHrEmpPromotionInfo.getGoDoc()).isEqualTo(DEFAULT_GO_DOC);
        assertThat(testHrEmpPromotionInfo.getGoDocContentType()).isEqualTo(DEFAULT_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpPromotionInfo.getGoDocName()).isEqualTo(DEFAULT_GO_DOC_NAME);
        assertThat(testHrEmpPromotionInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpPromotionInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpPromotionInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpPromotionInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpPromotionInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpPromotionInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpPromotionInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkInstituteFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPromotionInfoRepository.findAll().size();
        // set the field null
        hrEmpPromotionInfo.setInstituteFrom(null);

        // Create the HrEmpPromotionInfo, which fails.

        restHrEmpPromotionInfoMockMvc.perform(post("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstituteToIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPromotionInfoRepository.findAll().size();
        // set the field null
        hrEmpPromotionInfo.setInstituteTo(null);

        // Create the HrEmpPromotionInfo, which fails.

        restHrEmpPromotionInfoMockMvc.perform(post("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartmentFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPromotionInfoRepository.findAll().size();
        // set the field null
        hrEmpPromotionInfo.setDepartmentFrom(null);

        // Create the HrEmpPromotionInfo, which fails.

        restHrEmpPromotionInfoMockMvc.perform(post("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartmentToIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPromotionInfoRepository.findAll().size();
        // set the field null
        hrEmpPromotionInfo.setDepartmentTo(null);

        // Create the HrEmpPromotionInfo, which fails.

        restHrEmpPromotionInfoMockMvc.perform(post("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPromotionInfoRepository.findAll().size();
        // set the field null
        hrEmpPromotionInfo.setDesignationFrom(null);

        // Create the HrEmpPromotionInfo, which fails.

        restHrEmpPromotionInfoMockMvc.perform(post("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationToIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPromotionInfoRepository.findAll().size();
        // set the field null
        hrEmpPromotionInfo.setDesignationTo(null);

        // Create the HrEmpPromotionInfo, which fails.

        restHrEmpPromotionInfoMockMvc.perform(post("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPayscaleFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPromotionInfoRepository.findAll().size();
        // set the field null
        hrEmpPromotionInfo.setPayscaleFrom(null);

        // Create the HrEmpPromotionInfo, which fails.

        restHrEmpPromotionInfoMockMvc.perform(post("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPayscaleToIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPromotionInfoRepository.findAll().size();
        // set the field null
        hrEmpPromotionInfo.setPayscaleTo(null);

        // Create the HrEmpPromotionInfo, which fails.

        restHrEmpPromotionInfoMockMvc.perform(post("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPromotionInfoRepository.findAll().size();
        // set the field null
        hrEmpPromotionInfo.setActiveStatus(null);

        // Create the HrEmpPromotionInfo, which fails.

        restHrEmpPromotionInfoMockMvc.perform(post("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpPromotionInfos() throws Exception {
        // Initialize the database
        hrEmpPromotionInfoRepository.saveAndFlush(hrEmpPromotionInfo);

        // Get all the hrEmpPromotionInfos
        restHrEmpPromotionInfoMockMvc.perform(get("/api/hrEmpPromotionInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpPromotionInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].instituteFrom").value(hasItem(DEFAULT_INSTITUTE_FROM.toString())))
                .andExpect(jsonPath("$.[*].instituteTo").value(hasItem(DEFAULT_INSTITUTE_TO.toString())))
                .andExpect(jsonPath("$.[*].departmentFrom").value(hasItem(DEFAULT_DEPARTMENT_FROM.toString())))
                .andExpect(jsonPath("$.[*].departmentTo").value(hasItem(DEFAULT_DEPARTMENT_TO.toString())))
                .andExpect(jsonPath("$.[*].designationFrom").value(hasItem(DEFAULT_DESIGNATION_FROM.toString())))
                .andExpect(jsonPath("$.[*].designationTo").value(hasItem(DEFAULT_DESIGNATION_TO.toString())))
                .andExpect(jsonPath("$.[*].payscaleFrom").value(hasItem(DEFAULT_PAYSCALE_FROM.intValue())))
                .andExpect(jsonPath("$.[*].payscaleTo").value(hasItem(DEFAULT_PAYSCALE_TO.intValue())))
                .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
                .andExpect(jsonPath("$.[*].promotionType").value(hasItem(DEFAULT_PROMOTION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].officeOrderNo").value(hasItem(DEFAULT_OFFICE_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
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
    public void getHrEmpPromotionInfo() throws Exception {
        // Initialize the database
        hrEmpPromotionInfoRepository.saveAndFlush(hrEmpPromotionInfo);

        // Get the hrEmpPromotionInfo
        restHrEmpPromotionInfoMockMvc.perform(get("/api/hrEmpPromotionInfos/{id}", hrEmpPromotionInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpPromotionInfo.getId().intValue()))
            .andExpect(jsonPath("$.instituteFrom").value(DEFAULT_INSTITUTE_FROM.toString()))
            .andExpect(jsonPath("$.instituteTo").value(DEFAULT_INSTITUTE_TO.toString()))
            .andExpect(jsonPath("$.departmentFrom").value(DEFAULT_DEPARTMENT_FROM.toString()))
            .andExpect(jsonPath("$.departmentTo").value(DEFAULT_DEPARTMENT_TO.toString()))
            .andExpect(jsonPath("$.designationFrom").value(DEFAULT_DESIGNATION_FROM.toString()))
            .andExpect(jsonPath("$.designationTo").value(DEFAULT_DESIGNATION_TO.toString()))
            .andExpect(jsonPath("$.payscaleFrom").value(DEFAULT_PAYSCALE_FROM.intValue()))
            .andExpect(jsonPath("$.payscaleTo").value(DEFAULT_PAYSCALE_TO.intValue()))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.promotionType").value(DEFAULT_PROMOTION_TYPE.toString()))
            .andExpect(jsonPath("$.officeOrderNo").value(DEFAULT_OFFICE_ORDER_NO.toString()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
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
    public void getNonExistingHrEmpPromotionInfo() throws Exception {
        // Get the hrEmpPromotionInfo
        restHrEmpPromotionInfoMockMvc.perform(get("/api/hrEmpPromotionInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpPromotionInfo() throws Exception {
        // Initialize the database
        hrEmpPromotionInfoRepository.saveAndFlush(hrEmpPromotionInfo);

		int databaseSizeBeforeUpdate = hrEmpPromotionInfoRepository.findAll().size();

        // Update the hrEmpPromotionInfo
        hrEmpPromotionInfo.setInstituteFrom(UPDATED_INSTITUTE_FROM);
        hrEmpPromotionInfo.setInstituteTo(UPDATED_INSTITUTE_TO);
        hrEmpPromotionInfo.setDepartmentFrom(UPDATED_DEPARTMENT_FROM);
        hrEmpPromotionInfo.setDepartmentTo(UPDATED_DEPARTMENT_TO);
        hrEmpPromotionInfo.setDesignationFrom(UPDATED_DESIGNATION_FROM);
        hrEmpPromotionInfo.setDesignationTo(UPDATED_DESIGNATION_TO);
        hrEmpPromotionInfo.setPayscaleFrom(UPDATED_PAYSCALE_FROM);
        hrEmpPromotionInfo.setPayscaleTo(UPDATED_PAYSCALE_TO);
        hrEmpPromotionInfo.setJoiningDate(UPDATED_JOINING_DATE);
        hrEmpPromotionInfo.setPromotionType(UPDATED_PROMOTION_TYPE);
        hrEmpPromotionInfo.setOfficeOrderNo(UPDATED_OFFICE_ORDER_NO);
        hrEmpPromotionInfo.setOrderDate(UPDATED_ORDER_DATE);
        hrEmpPromotionInfo.setGoDate(UPDATED_GO_DATE);
        hrEmpPromotionInfo.setGoDoc(UPDATED_GO_DOC);
        hrEmpPromotionInfo.setGoDocContentType(UPDATED_GO_DOC_CONTENT_TYPE);
        hrEmpPromotionInfo.setGoDocName(UPDATED_GO_DOC_NAME);
        hrEmpPromotionInfo.setLogId(UPDATED_LOG_ID);
        hrEmpPromotionInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpPromotionInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpPromotionInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpPromotionInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpPromotionInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpPromotionInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpPromotionInfoMockMvc.perform(put("/api/hrEmpPromotionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPromotionInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpPromotionInfo in the database
        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpPromotionInfo testHrEmpPromotionInfo = hrEmpPromotionInfos.get(hrEmpPromotionInfos.size() - 1);
        assertThat(testHrEmpPromotionInfo.getInstituteFrom()).isEqualTo(UPDATED_INSTITUTE_FROM);
        assertThat(testHrEmpPromotionInfo.getInstituteTo()).isEqualTo(UPDATED_INSTITUTE_TO);
        assertThat(testHrEmpPromotionInfo.getDepartmentFrom()).isEqualTo(UPDATED_DEPARTMENT_FROM);
        assertThat(testHrEmpPromotionInfo.getDepartmentTo()).isEqualTo(UPDATED_DEPARTMENT_TO);
        assertThat(testHrEmpPromotionInfo.getDesignationFrom()).isEqualTo(UPDATED_DESIGNATION_FROM);
        assertThat(testHrEmpPromotionInfo.getDesignationTo()).isEqualTo(UPDATED_DESIGNATION_TO);
        assertThat(testHrEmpPromotionInfo.getPayscaleFrom()).isEqualTo(UPDATED_PAYSCALE_FROM);
        assertThat(testHrEmpPromotionInfo.getPayscaleTo()).isEqualTo(UPDATED_PAYSCALE_TO);
        assertThat(testHrEmpPromotionInfo.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testHrEmpPromotionInfo.getPromotionType()).isEqualTo(UPDATED_PROMOTION_TYPE);
        assertThat(testHrEmpPromotionInfo.getOfficeOrderNo()).isEqualTo(UPDATED_OFFICE_ORDER_NO);
        assertThat(testHrEmpPromotionInfo.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testHrEmpPromotionInfo.getGoDate()).isEqualTo(UPDATED_GO_DATE);
        assertThat(testHrEmpPromotionInfo.getGoDoc()).isEqualTo(UPDATED_GO_DOC);
        assertThat(testHrEmpPromotionInfo.getGoDocContentType()).isEqualTo(UPDATED_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpPromotionInfo.getGoDocName()).isEqualTo(UPDATED_GO_DOC_NAME);
        assertThat(testHrEmpPromotionInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpPromotionInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpPromotionInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpPromotionInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpPromotionInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpPromotionInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpPromotionInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpPromotionInfo() throws Exception {
        // Initialize the database
        hrEmpPromotionInfoRepository.saveAndFlush(hrEmpPromotionInfo);

		int databaseSizeBeforeDelete = hrEmpPromotionInfoRepository.findAll().size();

        // Get the hrEmpPromotionInfo
        restHrEmpPromotionInfoMockMvc.perform(delete("/api/hrEmpPromotionInfos/{id}", hrEmpPromotionInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpPromotionInfo> hrEmpPromotionInfos = hrEmpPromotionInfoRepository.findAll();
        assertThat(hrEmpPromotionInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
