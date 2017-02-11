package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpTrainingInfo;
import gov.step.app.repository.HrEmpTrainingInfoRepository;
import gov.step.app.repository.search.HrEmpTrainingInfoSearchRepository;
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
 * Test class for the HrEmpTrainingInfoResource REST controller.
 *
 * @see HrEmpTrainingInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpTrainingInfoResourceIntTest {

    private static final String DEFAULT_INSTITUTE_NAME = "AAAAA";
    private static final String UPDATED_INSTITUTE_NAME = "BBBBB";
    private static final String DEFAULT_COURSE_TITLE = "AAAAA";
    private static final String UPDATED_COURSE_TITLE = "BBBBB";

    private static final LocalDate DEFAULT_DURATION_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DURATION_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DURATION_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DURATION_TO = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_RESULT = "AAAAA";
    private static final String UPDATED_RESULT = "BBBBB";
    private static final String DEFAULT_OFFICE_ORDER_NO = "AAAAA";
    private static final String UPDATED_OFFICE_ORDER_NO = "BBBBB";
    private static final String DEFAULT_JOB_CATEGORY = "AAAAA";
    private static final String UPDATED_JOB_CATEGORY = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";

    private static final byte[] DEFAULT_GO_ORDER_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GO_ORDER_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_GO_ORDER_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GO_ORDER_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_GO_ORDER_DOC_NAME = "AAAAA";
    private static final String UPDATED_GO_ORDER_DOC_NAME = "BBBBB";

    private static final byte[] DEFAULT_CERT_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERT_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CERT_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERT_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_CERT_DOC_NAME = "AAAAA";
    private static final String UPDATED_CERT_DOC_NAME = "BBBBB";

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
    private HrEmpTrainingInfoRepository hrEmpTrainingInfoRepository;

    @Inject
    private HrEmpTrainingInfoSearchRepository hrEmpTrainingInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpTrainingInfoMockMvc;

    private HrEmpTrainingInfo hrEmpTrainingInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpTrainingInfoResource hrEmpTrainingInfoResource = new HrEmpTrainingInfoResource();
        ReflectionTestUtils.setField(hrEmpTrainingInfoResource, "hrEmpTrainingInfoSearchRepository", hrEmpTrainingInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpTrainingInfoResource, "hrEmpTrainingInfoRepository", hrEmpTrainingInfoRepository);
        this.restHrEmpTrainingInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpTrainingInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpTrainingInfo = new HrEmpTrainingInfo();
        hrEmpTrainingInfo.setInstituteName(DEFAULT_INSTITUTE_NAME);
        hrEmpTrainingInfo.setCourseTitle(DEFAULT_COURSE_TITLE);
        hrEmpTrainingInfo.setDurationFrom(DEFAULT_DURATION_FROM);
        hrEmpTrainingInfo.setDurationTo(DEFAULT_DURATION_TO);
        hrEmpTrainingInfo.setResult(DEFAULT_RESULT);
        hrEmpTrainingInfo.setOfficeOrderNo(DEFAULT_OFFICE_ORDER_NO);
        hrEmpTrainingInfo.setJobCategory(DEFAULT_JOB_CATEGORY);
        hrEmpTrainingInfo.setCountry(DEFAULT_COUNTRY);
        hrEmpTrainingInfo.setGoOrderDoc(DEFAULT_GO_ORDER_DOC);
        hrEmpTrainingInfo.setGoOrderDocContentType(DEFAULT_GO_ORDER_DOC_CONTENT_TYPE);
        hrEmpTrainingInfo.setGoOrderDocName(DEFAULT_GO_ORDER_DOC_NAME);
        hrEmpTrainingInfo.setCertDoc(DEFAULT_CERT_DOC);
        hrEmpTrainingInfo.setCertDocContentType(DEFAULT_CERT_DOC_CONTENT_TYPE);
        hrEmpTrainingInfo.setCertDocName(DEFAULT_CERT_DOC_NAME);
        hrEmpTrainingInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpTrainingInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpTrainingInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpTrainingInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpTrainingInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpTrainingInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpTrainingInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpTrainingInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpTrainingInfoRepository.findAll().size();

        // Create the HrEmpTrainingInfo

        restHrEmpTrainingInfoMockMvc.perform(post("/api/hrEmpTrainingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTrainingInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpTrainingInfo in the database
        List<HrEmpTrainingInfo> hrEmpTrainingInfos = hrEmpTrainingInfoRepository.findAll();
        assertThat(hrEmpTrainingInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpTrainingInfo testHrEmpTrainingInfo = hrEmpTrainingInfos.get(hrEmpTrainingInfos.size() - 1);
        assertThat(testHrEmpTrainingInfo.getInstituteName()).isEqualTo(DEFAULT_INSTITUTE_NAME);
        assertThat(testHrEmpTrainingInfo.getCourseTitle()).isEqualTo(DEFAULT_COURSE_TITLE);
        assertThat(testHrEmpTrainingInfo.getDurationFrom()).isEqualTo(DEFAULT_DURATION_FROM);
        assertThat(testHrEmpTrainingInfo.getDurationTo()).isEqualTo(DEFAULT_DURATION_TO);
        assertThat(testHrEmpTrainingInfo.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testHrEmpTrainingInfo.getOfficeOrderNo()).isEqualTo(DEFAULT_OFFICE_ORDER_NO);
        assertThat(testHrEmpTrainingInfo.getJobCategory()).isEqualTo(DEFAULT_JOB_CATEGORY);
        assertThat(testHrEmpTrainingInfo.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testHrEmpTrainingInfo.getGoOrderDoc()).isEqualTo(DEFAULT_GO_ORDER_DOC);
        assertThat(testHrEmpTrainingInfo.getGoOrderDocContentType()).isEqualTo(DEFAULT_GO_ORDER_DOC_CONTENT_TYPE);
        assertThat(testHrEmpTrainingInfo.getGoOrderDocName()).isEqualTo(DEFAULT_GO_ORDER_DOC_NAME);
        assertThat(testHrEmpTrainingInfo.getCertDoc()).isEqualTo(DEFAULT_CERT_DOC);
        assertThat(testHrEmpTrainingInfo.getCertDocContentType()).isEqualTo(DEFAULT_CERT_DOC_CONTENT_TYPE);
        assertThat(testHrEmpTrainingInfo.getCertDocName()).isEqualTo(DEFAULT_CERT_DOC_NAME);
        assertThat(testHrEmpTrainingInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpTrainingInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpTrainingInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpTrainingInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpTrainingInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpTrainingInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpTrainingInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkInstituteNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTrainingInfoRepository.findAll().size();
        // set the field null
        hrEmpTrainingInfo.setInstituteName(null);

        // Create the HrEmpTrainingInfo, which fails.

        restHrEmpTrainingInfoMockMvc.perform(post("/api/hrEmpTrainingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTrainingInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTrainingInfo> hrEmpTrainingInfos = hrEmpTrainingInfoRepository.findAll();
        assertThat(hrEmpTrainingInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCourseTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTrainingInfoRepository.findAll().size();
        // set the field null
        hrEmpTrainingInfo.setCourseTitle(null);

        // Create the HrEmpTrainingInfo, which fails.

        restHrEmpTrainingInfoMockMvc.perform(post("/api/hrEmpTrainingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTrainingInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTrainingInfo> hrEmpTrainingInfos = hrEmpTrainingInfoRepository.findAll();
        assertThat(hrEmpTrainingInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTrainingInfoRepository.findAll().size();
        // set the field null
        hrEmpTrainingInfo.setDurationFrom(null);

        // Create the HrEmpTrainingInfo, which fails.

        restHrEmpTrainingInfoMockMvc.perform(post("/api/hrEmpTrainingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTrainingInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTrainingInfo> hrEmpTrainingInfos = hrEmpTrainingInfoRepository.findAll();
        assertThat(hrEmpTrainingInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationToIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTrainingInfoRepository.findAll().size();
        // set the field null
        hrEmpTrainingInfo.setDurationTo(null);

        // Create the HrEmpTrainingInfo, which fails.

        restHrEmpTrainingInfoMockMvc.perform(post("/api/hrEmpTrainingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTrainingInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTrainingInfo> hrEmpTrainingInfos = hrEmpTrainingInfoRepository.findAll();
        assertThat(hrEmpTrainingInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTrainingInfoRepository.findAll().size();
        // set the field null
        hrEmpTrainingInfo.setResult(null);

        // Create the HrEmpTrainingInfo, which fails.

        restHrEmpTrainingInfoMockMvc.perform(post("/api/hrEmpTrainingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTrainingInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTrainingInfo> hrEmpTrainingInfos = hrEmpTrainingInfoRepository.findAll();
        assertThat(hrEmpTrainingInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfficeOrderNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTrainingInfoRepository.findAll().size();
        // set the field null
        hrEmpTrainingInfo.setOfficeOrderNo(null);

        // Create the HrEmpTrainingInfo, which fails.

        restHrEmpTrainingInfoMockMvc.perform(post("/api/hrEmpTrainingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTrainingInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTrainingInfo> hrEmpTrainingInfos = hrEmpTrainingInfoRepository.findAll();
        assertThat(hrEmpTrainingInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTrainingInfoRepository.findAll().size();
        // set the field null
        hrEmpTrainingInfo.setActiveStatus(null);

        // Create the HrEmpTrainingInfo, which fails.

        restHrEmpTrainingInfoMockMvc.perform(post("/api/hrEmpTrainingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTrainingInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTrainingInfo> hrEmpTrainingInfos = hrEmpTrainingInfoRepository.findAll();
        assertThat(hrEmpTrainingInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpTrainingInfos() throws Exception {
        // Initialize the database
        hrEmpTrainingInfoRepository.saveAndFlush(hrEmpTrainingInfo);

        // Get all the hrEmpTrainingInfos
        restHrEmpTrainingInfoMockMvc.perform(get("/api/hrEmpTrainingInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpTrainingInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].instituteName").value(hasItem(DEFAULT_INSTITUTE_NAME.toString())))
                .andExpect(jsonPath("$.[*].courseTitle").value(hasItem(DEFAULT_COURSE_TITLE.toString())))
                .andExpect(jsonPath("$.[*].durationFrom").value(hasItem(DEFAULT_DURATION_FROM.toString())))
                .andExpect(jsonPath("$.[*].durationTo").value(hasItem(DEFAULT_DURATION_TO.toString())))
                .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
                .andExpect(jsonPath("$.[*].officeOrderNo").value(hasItem(DEFAULT_OFFICE_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].jobCategory").value(hasItem(DEFAULT_JOB_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].goOrderDocContentType").value(hasItem(DEFAULT_GO_ORDER_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].goOrderDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_GO_ORDER_DOC))))
                .andExpect(jsonPath("$.[*].goOrderDocName").value(hasItem(DEFAULT_GO_ORDER_DOC_NAME.toString())))
                .andExpect(jsonPath("$.[*].certDocContentType").value(hasItem(DEFAULT_CERT_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].certDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERT_DOC))))
                .andExpect(jsonPath("$.[*].certDocName").value(hasItem(DEFAULT_CERT_DOC_NAME.toString())))
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
    public void getHrEmpTrainingInfo() throws Exception {
        // Initialize the database
        hrEmpTrainingInfoRepository.saveAndFlush(hrEmpTrainingInfo);

        // Get the hrEmpTrainingInfo
        restHrEmpTrainingInfoMockMvc.perform(get("/api/hrEmpTrainingInfos/{id}", hrEmpTrainingInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpTrainingInfo.getId().intValue()))
            .andExpect(jsonPath("$.instituteName").value(DEFAULT_INSTITUTE_NAME.toString()))
            .andExpect(jsonPath("$.courseTitle").value(DEFAULT_COURSE_TITLE.toString()))
            .andExpect(jsonPath("$.durationFrom").value(DEFAULT_DURATION_FROM.toString()))
            .andExpect(jsonPath("$.durationTo").value(DEFAULT_DURATION_TO.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.officeOrderNo").value(DEFAULT_OFFICE_ORDER_NO.toString()))
            .andExpect(jsonPath("$.jobCategory").value(DEFAULT_JOB_CATEGORY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.goOrderDocContentType").value(DEFAULT_GO_ORDER_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.goOrderDoc").value(Base64Utils.encodeToString(DEFAULT_GO_ORDER_DOC)))
            .andExpect(jsonPath("$.goOrderDocName").value(DEFAULT_GO_ORDER_DOC_NAME.toString()))
            .andExpect(jsonPath("$.certDocContentType").value(DEFAULT_CERT_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.certDoc").value(Base64Utils.encodeToString(DEFAULT_CERT_DOC)))
            .andExpect(jsonPath("$.certDocName").value(DEFAULT_CERT_DOC_NAME.toString()))
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
    public void getNonExistingHrEmpTrainingInfo() throws Exception {
        // Get the hrEmpTrainingInfo
        restHrEmpTrainingInfoMockMvc.perform(get("/api/hrEmpTrainingInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpTrainingInfo() throws Exception {
        // Initialize the database
        hrEmpTrainingInfoRepository.saveAndFlush(hrEmpTrainingInfo);

		int databaseSizeBeforeUpdate = hrEmpTrainingInfoRepository.findAll().size();

        // Update the hrEmpTrainingInfo
        hrEmpTrainingInfo.setInstituteName(UPDATED_INSTITUTE_NAME);
        hrEmpTrainingInfo.setCourseTitle(UPDATED_COURSE_TITLE);
        hrEmpTrainingInfo.setDurationFrom(UPDATED_DURATION_FROM);
        hrEmpTrainingInfo.setDurationTo(UPDATED_DURATION_TO);
        hrEmpTrainingInfo.setResult(UPDATED_RESULT);
        hrEmpTrainingInfo.setOfficeOrderNo(UPDATED_OFFICE_ORDER_NO);
        hrEmpTrainingInfo.setJobCategory(UPDATED_JOB_CATEGORY);
        hrEmpTrainingInfo.setCountry(UPDATED_COUNTRY);
        hrEmpTrainingInfo.setGoOrderDoc(UPDATED_GO_ORDER_DOC);
        hrEmpTrainingInfo.setGoOrderDocContentType(UPDATED_GO_ORDER_DOC_CONTENT_TYPE);
        hrEmpTrainingInfo.setGoOrderDocName(UPDATED_GO_ORDER_DOC_NAME);
        hrEmpTrainingInfo.setCertDoc(UPDATED_CERT_DOC);
        hrEmpTrainingInfo.setCertDocContentType(UPDATED_CERT_DOC_CONTENT_TYPE);
        hrEmpTrainingInfo.setCertDocName(UPDATED_CERT_DOC_NAME);
        hrEmpTrainingInfo.setLogId(UPDATED_LOG_ID);
        hrEmpTrainingInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpTrainingInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpTrainingInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpTrainingInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpTrainingInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpTrainingInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpTrainingInfoMockMvc.perform(put("/api/hrEmpTrainingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTrainingInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpTrainingInfo in the database
        List<HrEmpTrainingInfo> hrEmpTrainingInfos = hrEmpTrainingInfoRepository.findAll();
        assertThat(hrEmpTrainingInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpTrainingInfo testHrEmpTrainingInfo = hrEmpTrainingInfos.get(hrEmpTrainingInfos.size() - 1);
        assertThat(testHrEmpTrainingInfo.getInstituteName()).isEqualTo(UPDATED_INSTITUTE_NAME);
        assertThat(testHrEmpTrainingInfo.getCourseTitle()).isEqualTo(UPDATED_COURSE_TITLE);
        assertThat(testHrEmpTrainingInfo.getDurationFrom()).isEqualTo(UPDATED_DURATION_FROM);
        assertThat(testHrEmpTrainingInfo.getDurationTo()).isEqualTo(UPDATED_DURATION_TO);
        assertThat(testHrEmpTrainingInfo.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testHrEmpTrainingInfo.getOfficeOrderNo()).isEqualTo(UPDATED_OFFICE_ORDER_NO);
        assertThat(testHrEmpTrainingInfo.getJobCategory()).isEqualTo(UPDATED_JOB_CATEGORY);
        assertThat(testHrEmpTrainingInfo.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testHrEmpTrainingInfo.getGoOrderDoc()).isEqualTo(UPDATED_GO_ORDER_DOC);
        assertThat(testHrEmpTrainingInfo.getGoOrderDocContentType()).isEqualTo(UPDATED_GO_ORDER_DOC_CONTENT_TYPE);
        assertThat(testHrEmpTrainingInfo.getGoOrderDocName()).isEqualTo(UPDATED_GO_ORDER_DOC_NAME);
        assertThat(testHrEmpTrainingInfo.getCertDoc()).isEqualTo(UPDATED_CERT_DOC);
        assertThat(testHrEmpTrainingInfo.getCertDocContentType()).isEqualTo(UPDATED_CERT_DOC_CONTENT_TYPE);
        assertThat(testHrEmpTrainingInfo.getCertDocName()).isEqualTo(UPDATED_CERT_DOC_NAME);
        assertThat(testHrEmpTrainingInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpTrainingInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpTrainingInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpTrainingInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpTrainingInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpTrainingInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpTrainingInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpTrainingInfo() throws Exception {
        // Initialize the database
        hrEmpTrainingInfoRepository.saveAndFlush(hrEmpTrainingInfo);

		int databaseSizeBeforeDelete = hrEmpTrainingInfoRepository.findAll().size();

        // Get the hrEmpTrainingInfo
        restHrEmpTrainingInfoMockMvc.perform(delete("/api/hrEmpTrainingInfos/{id}", hrEmpTrainingInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpTrainingInfo> hrEmpTrainingInfos = hrEmpTrainingInfoRepository.findAll();
        assertThat(hrEmpTrainingInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
