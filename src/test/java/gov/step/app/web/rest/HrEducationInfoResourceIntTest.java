package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEducationInfo;
import gov.step.app.repository.HrEducationInfoRepository;
import gov.step.app.repository.search.HrEducationInfoSearchRepository;
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
 * Test class for the HrEducationInfoResource REST controller.
 *
 * @see HrEducationInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEducationInfoResourceIntTest {

    private static final String DEFAULT_EXAM_TITLE = "AAAAA";
    private static final String UPDATED_EXAM_TITLE = "BBBBB";
    private static final String DEFAULT_MAJOR_SUBJECT = "AAAAA";
    private static final String UPDATED_MAJOR_SUBJECT = "BBBBB";
    private static final String DEFAULT_CERT_SL_NUMBER = "AAAAA";
    private static final String UPDATED_CERT_SL_NUMBER = "BBBBB";
    private static final String DEFAULT_SESSION_YEAR = "AAAAA";
    private static final String UPDATED_SESSION_YEAR = "BBBBB";
    private static final String DEFAULT_ROLL_NUMBER = "AAAAA";
    private static final String UPDATED_ROLL_NUMBER = "BBBBB";
    private static final String DEFAULT_INSTITUTE_NAME = "AAAAA";
    private static final String UPDATED_INSTITUTE_NAME = "BBBBB";
    private static final String DEFAULT_GPA_OR_CGPA = "AAAAA";
    private static final String UPDATED_GPA_OR_CGPA = "BBBBB";
    private static final String DEFAULT_GPA_SCALE = "AAAAA";
    private static final String UPDATED_GPA_SCALE = "BBBBB";

    private static final Long DEFAULT_PASSING_YEAR = 1L;
    private static final Long UPDATED_PASSING_YEAR = 2L;

    private static final byte[] DEFAULT_CERTIFICATE_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERTIFICATE_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CERTIFICATE_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERTIFICATE_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_CERTIFICATE_DOC_NAME = "AAAAA";
    private static final String UPDATED_CERTIFICATE_DOC_NAME = "BBBBB";

    private static final byte[] DEFAULT_TRANSCRIPT_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TRANSCRIPT_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_TRANSCRIPT_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TRANSCRIPT_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_TRANSCRIPT_DOC_NAME = "AAAAA";
    private static final String UPDATED_TRANSCRIPT_DOC_NAME = "BBBBB";

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
    private HrEducationInfoRepository hrEducationInfoRepository;

    @Inject
    private HrEducationInfoSearchRepository hrEducationInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEducationInfoMockMvc;

    private HrEducationInfo hrEducationInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEducationInfoResource hrEducationInfoResource = new HrEducationInfoResource();
        ReflectionTestUtils.setField(hrEducationInfoResource, "hrEducationInfoSearchRepository", hrEducationInfoSearchRepository);
        ReflectionTestUtils.setField(hrEducationInfoResource, "hrEducationInfoRepository", hrEducationInfoRepository);
        this.restHrEducationInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEducationInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEducationInfo = new HrEducationInfo();
        hrEducationInfo.setExamTitle(DEFAULT_EXAM_TITLE);
        hrEducationInfo.setMajorSubject(DEFAULT_MAJOR_SUBJECT);
        hrEducationInfo.setCertSlNumber(DEFAULT_CERT_SL_NUMBER);
        hrEducationInfo.setSessionYear(DEFAULT_SESSION_YEAR);
        hrEducationInfo.setRollNumber(DEFAULT_ROLL_NUMBER);
        hrEducationInfo.setInstituteName(DEFAULT_INSTITUTE_NAME);
        hrEducationInfo.setGpaOrCgpa(DEFAULT_GPA_OR_CGPA);
        hrEducationInfo.setGpaScale(DEFAULT_GPA_SCALE);
        hrEducationInfo.setPassingYear(DEFAULT_PASSING_YEAR);
        hrEducationInfo.setCertificateDoc(DEFAULT_CERTIFICATE_DOC);
        hrEducationInfo.setCertificateDocContentType(DEFAULT_CERTIFICATE_DOC_CONTENT_TYPE);
        hrEducationInfo.setCertificateDocName(DEFAULT_CERTIFICATE_DOC_NAME);
        hrEducationInfo.setTranscriptDoc(DEFAULT_TRANSCRIPT_DOC);
        hrEducationInfo.setTranscriptDocContentType(DEFAULT_TRANSCRIPT_DOC_CONTENT_TYPE);
        hrEducationInfo.setTranscriptDocName(DEFAULT_TRANSCRIPT_DOC_NAME);
        hrEducationInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEducationInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEducationInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEducationInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEducationInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEducationInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEducationInfoRepository.findAll().size();

        // Create the HrEducationInfo

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEducationInfo in the database
        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEducationInfo testHrEducationInfo = hrEducationInfos.get(hrEducationInfos.size() - 1);
        assertThat(testHrEducationInfo.getExamTitle()).isEqualTo(DEFAULT_EXAM_TITLE);
        assertThat(testHrEducationInfo.getMajorSubject()).isEqualTo(DEFAULT_MAJOR_SUBJECT);
        assertThat(testHrEducationInfo.getCertSlNumber()).isEqualTo(DEFAULT_CERT_SL_NUMBER);
        assertThat(testHrEducationInfo.getSessionYear()).isEqualTo(DEFAULT_SESSION_YEAR);
        assertThat(testHrEducationInfo.getRollNumber()).isEqualTo(DEFAULT_ROLL_NUMBER);
        assertThat(testHrEducationInfo.getInstituteName()).isEqualTo(DEFAULT_INSTITUTE_NAME);
        assertThat(testHrEducationInfo.getGpaOrCgpa()).isEqualTo(DEFAULT_GPA_OR_CGPA);
        assertThat(testHrEducationInfo.getGpaScale()).isEqualTo(DEFAULT_GPA_SCALE);
        assertThat(testHrEducationInfo.getPassingYear()).isEqualTo(DEFAULT_PASSING_YEAR);
        assertThat(testHrEducationInfo.getCertificateDoc()).isEqualTo(DEFAULT_CERTIFICATE_DOC);
        assertThat(testHrEducationInfo.getCertificateDocContentType()).isEqualTo(DEFAULT_CERTIFICATE_DOC_CONTENT_TYPE);
        assertThat(testHrEducationInfo.getCertificateDocName()).isEqualTo(DEFAULT_CERTIFICATE_DOC_NAME);
        assertThat(testHrEducationInfo.getTranscriptDoc()).isEqualTo(DEFAULT_TRANSCRIPT_DOC);
        assertThat(testHrEducationInfo.getTranscriptDocContentType()).isEqualTo(DEFAULT_TRANSCRIPT_DOC_CONTENT_TYPE);
        assertThat(testHrEducationInfo.getTranscriptDocName()).isEqualTo(DEFAULT_TRANSCRIPT_DOC_NAME);
        assertThat(testHrEducationInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEducationInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEducationInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEducationInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEducationInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkExamTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setExamTitle(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMajorSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setMajorSubject(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCertSlNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setCertSlNumber(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSessionYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setSessionYear(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRollNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setRollNumber(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstituteNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setInstituteName(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGpaOrCgpaIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setGpaOrCgpa(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassingYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setPassingYear(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCertificateDocNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setCertificateDocName(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTranscriptDocNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setTranscriptDocName(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEducationInfoRepository.findAll().size();
        // set the field null
        hrEducationInfo.setActiveStatus(null);

        // Create the HrEducationInfo, which fails.

        restHrEducationInfoMockMvc.perform(post("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEducationInfos() throws Exception {
        // Initialize the database
        hrEducationInfoRepository.saveAndFlush(hrEducationInfo);

        // Get all the hrEducationInfos
        restHrEducationInfoMockMvc.perform(get("/api/hrEducationInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEducationInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].examTitle").value(hasItem(DEFAULT_EXAM_TITLE.toString())))
                .andExpect(jsonPath("$.[*].majorSubject").value(hasItem(DEFAULT_MAJOR_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].certSlNumber").value(hasItem(DEFAULT_CERT_SL_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].sessionYear").value(hasItem(DEFAULT_SESSION_YEAR.toString())))
                .andExpect(jsonPath("$.[*].rollNumber").value(hasItem(DEFAULT_ROLL_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].instituteName").value(hasItem(DEFAULT_INSTITUTE_NAME.toString())))
                .andExpect(jsonPath("$.[*].gpaOrCgpa").value(hasItem(DEFAULT_GPA_OR_CGPA.toString())))
                .andExpect(jsonPath("$.[*].gpaScale").value(hasItem(DEFAULT_GPA_SCALE.toString())))
                .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR.intValue())))
                .andExpect(jsonPath("$.[*].certificateDocContentType").value(hasItem(DEFAULT_CERTIFICATE_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].certificateDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERTIFICATE_DOC))))
                .andExpect(jsonPath("$.[*].certificateDocName").value(hasItem(DEFAULT_CERTIFICATE_DOC_NAME.toString())))
                .andExpect(jsonPath("$.[*].transcriptDocContentType").value(hasItem(DEFAULT_TRANSCRIPT_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].transcriptDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_TRANSCRIPT_DOC))))
                .andExpect(jsonPath("$.[*].transcriptDocName").value(hasItem(DEFAULT_TRANSCRIPT_DOC_NAME.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEducationInfo() throws Exception {
        // Initialize the database
        hrEducationInfoRepository.saveAndFlush(hrEducationInfo);

        // Get the hrEducationInfo
        restHrEducationInfoMockMvc.perform(get("/api/hrEducationInfos/{id}", hrEducationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEducationInfo.getId().intValue()))
            .andExpect(jsonPath("$.examTitle").value(DEFAULT_EXAM_TITLE.toString()))
            .andExpect(jsonPath("$.majorSubject").value(DEFAULT_MAJOR_SUBJECT.toString()))
            .andExpect(jsonPath("$.certSlNumber").value(DEFAULT_CERT_SL_NUMBER.toString()))
            .andExpect(jsonPath("$.sessionYear").value(DEFAULT_SESSION_YEAR.toString()))
            .andExpect(jsonPath("$.rollNumber").value(DEFAULT_ROLL_NUMBER.toString()))
            .andExpect(jsonPath("$.instituteName").value(DEFAULT_INSTITUTE_NAME.toString()))
            .andExpect(jsonPath("$.gpaOrCgpa").value(DEFAULT_GPA_OR_CGPA.toString()))
            .andExpect(jsonPath("$.gpaScale").value(DEFAULT_GPA_SCALE.toString()))
            .andExpect(jsonPath("$.passingYear").value(DEFAULT_PASSING_YEAR.intValue()))
            .andExpect(jsonPath("$.certificateDocContentType").value(DEFAULT_CERTIFICATE_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.certificateDoc").value(Base64Utils.encodeToString(DEFAULT_CERTIFICATE_DOC)))
            .andExpect(jsonPath("$.certificateDocName").value(DEFAULT_CERTIFICATE_DOC_NAME.toString()))
            .andExpect(jsonPath("$.transcriptDocContentType").value(DEFAULT_TRANSCRIPT_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.transcriptDoc").value(Base64Utils.encodeToString(DEFAULT_TRANSCRIPT_DOC)))
            .andExpect(jsonPath("$.transcriptDocName").value(DEFAULT_TRANSCRIPT_DOC_NAME.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEducationInfo() throws Exception {
        // Get the hrEducationInfo
        restHrEducationInfoMockMvc.perform(get("/api/hrEducationInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEducationInfo() throws Exception {
        // Initialize the database
        hrEducationInfoRepository.saveAndFlush(hrEducationInfo);

		int databaseSizeBeforeUpdate = hrEducationInfoRepository.findAll().size();

        // Update the hrEducationInfo
        hrEducationInfo.setExamTitle(UPDATED_EXAM_TITLE);
        hrEducationInfo.setMajorSubject(UPDATED_MAJOR_SUBJECT);
        hrEducationInfo.setCertSlNumber(UPDATED_CERT_SL_NUMBER);
        hrEducationInfo.setSessionYear(UPDATED_SESSION_YEAR);
        hrEducationInfo.setRollNumber(UPDATED_ROLL_NUMBER);
        hrEducationInfo.setInstituteName(UPDATED_INSTITUTE_NAME);
        hrEducationInfo.setGpaOrCgpa(UPDATED_GPA_OR_CGPA);
        hrEducationInfo.setGpaScale(UPDATED_GPA_SCALE);
        hrEducationInfo.setPassingYear(UPDATED_PASSING_YEAR);
        hrEducationInfo.setCertificateDoc(UPDATED_CERTIFICATE_DOC);
        hrEducationInfo.setCertificateDocContentType(UPDATED_CERTIFICATE_DOC_CONTENT_TYPE);
        hrEducationInfo.setCertificateDocName(UPDATED_CERTIFICATE_DOC_NAME);
        hrEducationInfo.setTranscriptDoc(UPDATED_TRANSCRIPT_DOC);
        hrEducationInfo.setTranscriptDocContentType(UPDATED_TRANSCRIPT_DOC_CONTENT_TYPE);
        hrEducationInfo.setTranscriptDocName(UPDATED_TRANSCRIPT_DOC_NAME);
        hrEducationInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEducationInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEducationInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEducationInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEducationInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEducationInfoMockMvc.perform(put("/api/hrEducationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEducationInfo)))
                .andExpect(status().isOk());

        // Validate the HrEducationInfo in the database
        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeUpdate);
        HrEducationInfo testHrEducationInfo = hrEducationInfos.get(hrEducationInfos.size() - 1);
        assertThat(testHrEducationInfo.getExamTitle()).isEqualTo(UPDATED_EXAM_TITLE);
        assertThat(testHrEducationInfo.getMajorSubject()).isEqualTo(UPDATED_MAJOR_SUBJECT);
        assertThat(testHrEducationInfo.getCertSlNumber()).isEqualTo(UPDATED_CERT_SL_NUMBER);
        assertThat(testHrEducationInfo.getSessionYear()).isEqualTo(UPDATED_SESSION_YEAR);
        assertThat(testHrEducationInfo.getRollNumber()).isEqualTo(UPDATED_ROLL_NUMBER);
        assertThat(testHrEducationInfo.getInstituteName()).isEqualTo(UPDATED_INSTITUTE_NAME);
        assertThat(testHrEducationInfo.getGpaOrCgpa()).isEqualTo(UPDATED_GPA_OR_CGPA);
        assertThat(testHrEducationInfo.getGpaScale()).isEqualTo(UPDATED_GPA_SCALE);
        assertThat(testHrEducationInfo.getPassingYear()).isEqualTo(UPDATED_PASSING_YEAR);
        assertThat(testHrEducationInfo.getCertificateDoc()).isEqualTo(UPDATED_CERTIFICATE_DOC);
        assertThat(testHrEducationInfo.getCertificateDocContentType()).isEqualTo(UPDATED_CERTIFICATE_DOC_CONTENT_TYPE);
        assertThat(testHrEducationInfo.getCertificateDocName()).isEqualTo(UPDATED_CERTIFICATE_DOC_NAME);
        assertThat(testHrEducationInfo.getTranscriptDoc()).isEqualTo(UPDATED_TRANSCRIPT_DOC);
        assertThat(testHrEducationInfo.getTranscriptDocContentType()).isEqualTo(UPDATED_TRANSCRIPT_DOC_CONTENT_TYPE);
        assertThat(testHrEducationInfo.getTranscriptDocName()).isEqualTo(UPDATED_TRANSCRIPT_DOC_NAME);
        assertThat(testHrEducationInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEducationInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEducationInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEducationInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEducationInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEducationInfo() throws Exception {
        // Initialize the database
        hrEducationInfoRepository.saveAndFlush(hrEducationInfo);

		int databaseSizeBeforeDelete = hrEducationInfoRepository.findAll().size();

        // Get the hrEducationInfo
        restHrEducationInfoMockMvc.perform(delete("/api/hrEducationInfos/{id}", hrEducationInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEducationInfo> hrEducationInfos = hrEducationInfoRepository.findAll();
        assertThat(hrEducationInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
