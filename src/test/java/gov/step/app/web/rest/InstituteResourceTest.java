package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Institute;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.search.InstituteSearchRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.step.app.domain.enumeration.Location;
import gov.step.app.domain.enumeration.InstituteType;

/**
 * Test class for the InstituteResource REST controller.
 *
 * @see InstituteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstituteResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_ESTABLISHMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_ESTABLISHMENT = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_MPO_CODE = "AAAAA";
    private static final String UPDATED_MPO_CODE = "BBBBB";
    private static final String DEFAULT_POST_OFFICE = "AAAAA";
    private static final String UPDATED_POST_OFFICE = "BBBBB";


private static final Location DEFAULT_LOCATION = Location.CityCorporation;
    private static final Location UPDATED_LOCATION = Location.Municipality;

    private static final Boolean DEFAULT_IS_JOINT = false;
    private static final Boolean UPDATED_IS_JOINT = true;
    private static final String DEFAULT_MULTI_EDUCATIONAL = "AAAAA";
    private static final String UPDATED_MULTI_EDUCATIONAL = "BBBBB";

    private static final LocalDate DEFAULT_FIRST_APPROVED_EDUCATIONAL_YEAR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_APPROVED_EDUCATIONAL_YEAR = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_APPROVED_EDUCATIONAL_YEAR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_APPROVED_EDUCATIONAL_YEAR = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIRST_MPO_INCLUDE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_MPO_INCLUDE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_MPO_EXPIRE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MPO_EXPIRE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_NAME_OF_TRADE_SUBJECT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NAME_OF_TRADE_SUBJECT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_APPROVED_SIGNATURE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_APPROVED_SIGNATURE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_COMMITTEE_APPROVED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_COMMITTEE_APPROVED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_LAST_COMMITTEE_APPROVED_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LAST_COMMITTEE_APPROVED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LAST_COMMITTEE_APPROVED_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LAST_COMMITTEE_APPROVED_FILE_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_LAST_COMMITTEE_EXP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_COMMITTEE_EXP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_LAST_COMMITTEE1ST_MEETING_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LAST_COMMITTEE1ST_MEETING_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LAST_COMMITTEE1ST_MEETING_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LAST_COMMITTEE1ST_MEETING_FILE_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_LAST_COMMITTEE_EXPIRE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_COMMITTEE_EXPIRE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_MPO_MEMORIAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MPO_MEMORIAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TOTAL_STUDENT = 1;
    private static final Integer UPDATED_TOTAL_STUDENT = 2;

    private static final BigDecimal DEFAULT_LENGTH_OF_LIBRARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_LENGTH_OF_LIBRARY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_WIDTH_OF_LIBRARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_WIDTH_OF_LIBRARY = new BigDecimal(2);

    private static final Integer DEFAULT_NUMBER_OF_BOOK = 1;
    private static final Integer UPDATED_NUMBER_OF_BOOK = 2;

    private static final LocalDate DEFAULT_LAST_MPO_INCLUDE_EXPIRE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MPO_INCLUDE_EXPIRE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMBER_OF_LAB = 1;
    private static final Integer UPDATED_NUMBER_OF_LAB = 2;
    private static final String DEFAULT_LAB_AREA = "AAAAA";
    private static final String UPDATED_LAB_AREA = "BBBBB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_LAND_PHONE = "AAAAA";
    private static final String UPDATED_LAND_PHONE = "BBBBB";
    private static final String DEFAULT_MOBILE = "AAAAA";
    private static final String UPDATED_MOBILE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_CONSTITUENCY_AREA = "AAAAA";
    private static final String UPDATED_CONSTITUENCY_AREA = "BBBBB";
    private static final String DEFAULT_ADMIN_COUNSELOR_NAME = "AAAAA";
    private static final String UPDATED_ADMIN_COUNSELOR_NAME = "BBBBB";
    private static final String DEFAULT_COUNSELOR_MOBILE_NO = "AAAAA";
    private static final String UPDATED_COUNSELOR_MOBILE_NO = "BBBBB";
    private static final String DEFAULT_INS_HEAD_NAME = "AAAAA";
    private static final String UPDATED_INS_HEAD_NAME = "BBBBB";
    private static final String DEFAULT_INS_HEAD_MOBILE_NO = "AAAAA";
    private static final String UPDATED_INS_HEAD_MOBILE_NO = "BBBBB";
    private static final String DEFAULT_DEO_NAME = "AAAAA";
    private static final String UPDATED_DEO_NAME = "BBBBB";
    private static final String DEFAULT_DEO_MOBILE_NO = "AAAAA";
    private static final String UPDATED_DEO_MOBILE_NO = "BBBBB";


private static final InstituteType DEFAULT_TYPE = InstituteType.Government;
    private static final InstituteType UPDATED_TYPE = InstituteType.NonGovernment;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstituteSearchRepository instituteSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstituteMockMvc;

    private Institute institute;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteResource instituteResource = new InstituteResource();
        ReflectionTestUtils.setField(instituteResource, "instituteRepository", instituteRepository);
        ReflectionTestUtils.setField(instituteResource, "instituteSearchRepository", instituteSearchRepository);
        this.restInstituteMockMvc = MockMvcBuilders.standaloneSetup(instituteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        institute = new Institute();
        institute.setName(DEFAULT_NAME);
        institute.setDateOfEstablishment(DEFAULT_DATE_OF_ESTABLISHMENT);
        institute.setMpoCode(DEFAULT_MPO_CODE);
        institute.setPostOffice(DEFAULT_POST_OFFICE);
        institute.setLocation(DEFAULT_LOCATION);
        institute.setIsJoint(DEFAULT_IS_JOINT);
        institute.setMultiEducational(DEFAULT_MULTI_EDUCATIONAL);
        institute.setFirstApprovedEducationalYear(DEFAULT_FIRST_APPROVED_EDUCATIONAL_YEAR);
        institute.setLastApprovedEducationalYear(DEFAULT_LAST_APPROVED_EDUCATIONAL_YEAR);
        institute.setFirstMpoIncludeDate(DEFAULT_FIRST_MPO_INCLUDE_DATE);
        institute.setLastMpoExpireDate(DEFAULT_LAST_MPO_EXPIRE_DATE);
        institute.setNameOfTradeSubject(DEFAULT_NAME_OF_TRADE_SUBJECT);
        institute.setLastApprovedSignatureDate(DEFAULT_LAST_APPROVED_SIGNATURE_DATE);
        institute.setLastCommitteeApprovedDate(DEFAULT_LAST_COMMITTEE_APPROVED_DATE);
        institute.setLastCommitteeApprovedFile(DEFAULT_LAST_COMMITTEE_APPROVED_FILE);
        institute.setLastCommitteeApprovedFileContentType(DEFAULT_LAST_COMMITTEE_APPROVED_FILE_CONTENT_TYPE);
        institute.setLastCommitteeExpDate(DEFAULT_LAST_COMMITTEE_EXP_DATE);
        institute.setLastCommittee1stMeetingFile(DEFAULT_LAST_COMMITTEE1ST_MEETING_FILE);
        institute.setLastCommittee1stMeetingFileContentType(DEFAULT_LAST_COMMITTEE1ST_MEETING_FILE_CONTENT_TYPE);
        institute.setLastCommitteeExpireDate(DEFAULT_LAST_COMMITTEE_EXPIRE_DATE);
        institute.setLastMpoMemorialDate(DEFAULT_LAST_MPO_MEMORIAL_DATE);
        institute.setTotalStudent(DEFAULT_TOTAL_STUDENT);
        institute.setLengthOfLibrary(DEFAULT_LENGTH_OF_LIBRARY);
        institute.setWidthOfLibrary(DEFAULT_WIDTH_OF_LIBRARY);
        institute.setNumberOfBook(DEFAULT_NUMBER_OF_BOOK);
        institute.setLastMpoIncludeExpireDate(DEFAULT_LAST_MPO_INCLUDE_EXPIRE_DATE);
        institute.setNumberOfLab(DEFAULT_NUMBER_OF_LAB);
        institute.setLabArea(DEFAULT_LAB_AREA);
        institute.setCode(DEFAULT_CODE);
        institute.setLandPhone(DEFAULT_LAND_PHONE);
        institute.setMobile(DEFAULT_MOBILE);
        institute.setEmail(DEFAULT_EMAIL);
        institute.setConstituencyArea(DEFAULT_CONSTITUENCY_AREA);
        institute.setAdminCounselorName(DEFAULT_ADMIN_COUNSELOR_NAME);
        institute.setCounselorMobileNo(DEFAULT_COUNSELOR_MOBILE_NO);
        institute.setInsHeadName(DEFAULT_INS_HEAD_NAME);
        institute.setInsHeadMobileNo(DEFAULT_INS_HEAD_MOBILE_NO);
        institute.setDeoName(DEFAULT_DEO_NAME);
        institute.setDeoMobileNo(DEFAULT_DEO_MOBILE_NO);
        institute.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createInstitute() throws Exception {
        int databaseSizeBeforeCreate = instituteRepository.findAll().size();

        // Create the Institute

        restInstituteMockMvc.perform(post("/api/institutes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(institute)))
                .andExpect(status().isCreated());

        // Validate the Institute in the database
        List<Institute> institutes = instituteRepository.findAll();
        assertThat(institutes).hasSize(databaseSizeBeforeCreate + 1);
        Institute testInstitute = institutes.get(institutes.size() - 1);
        assertThat(testInstitute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstitute.getDateOfEstablishment()).isEqualTo(DEFAULT_DATE_OF_ESTABLISHMENT);
        assertThat(testInstitute.getMpoCode()).isEqualTo(DEFAULT_MPO_CODE);
        assertThat(testInstitute.getPostOffice()).isEqualTo(DEFAULT_POST_OFFICE);
        assertThat(testInstitute.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testInstitute.getIsJoint()).isEqualTo(DEFAULT_IS_JOINT);
        assertThat(testInstitute.getMultiEducational()).isEqualTo(DEFAULT_MULTI_EDUCATIONAL);
        assertThat(testInstitute.getFirstApprovedEducationalYear()).isEqualTo(DEFAULT_FIRST_APPROVED_EDUCATIONAL_YEAR);
        assertThat(testInstitute.getLastApprovedEducationalYear()).isEqualTo(DEFAULT_LAST_APPROVED_EDUCATIONAL_YEAR);
        assertThat(testInstitute.getFirstMpoIncludeDate()).isEqualTo(DEFAULT_FIRST_MPO_INCLUDE_DATE);
        assertThat(testInstitute.getLastMpoExpireDate()).isEqualTo(DEFAULT_LAST_MPO_EXPIRE_DATE);
        assertThat(testInstitute.getNameOfTradeSubject()).isEqualTo(DEFAULT_NAME_OF_TRADE_SUBJECT);
        assertThat(testInstitute.getLastApprovedSignatureDate()).isEqualTo(DEFAULT_LAST_APPROVED_SIGNATURE_DATE);
        assertThat(testInstitute.getLastCommitteeApprovedDate()).isEqualTo(DEFAULT_LAST_COMMITTEE_APPROVED_DATE);
        assertThat(testInstitute.getLastCommitteeApprovedFile()).isEqualTo(DEFAULT_LAST_COMMITTEE_APPROVED_FILE);
        assertThat(testInstitute.getLastCommitteeApprovedFileContentType()).isEqualTo(DEFAULT_LAST_COMMITTEE_APPROVED_FILE_CONTENT_TYPE);
        assertThat(testInstitute.getLastCommitteeExpDate()).isEqualTo(DEFAULT_LAST_COMMITTEE_EXP_DATE);
        assertThat(testInstitute.getLastCommittee1stMeetingFile()).isEqualTo(DEFAULT_LAST_COMMITTEE1ST_MEETING_FILE);
        assertThat(testInstitute.getLastCommittee1stMeetingFileContentType()).isEqualTo(DEFAULT_LAST_COMMITTEE1ST_MEETING_FILE_CONTENT_TYPE);
        assertThat(testInstitute.getLastCommitteeExpireDate()).isEqualTo(DEFAULT_LAST_COMMITTEE_EXPIRE_DATE);
        assertThat(testInstitute.getLastMpoMemorialDate()).isEqualTo(DEFAULT_LAST_MPO_MEMORIAL_DATE);
        assertThat(testInstitute.getTotalStudent()).isEqualTo(DEFAULT_TOTAL_STUDENT);
        assertThat(testInstitute.getLengthOfLibrary()).isEqualTo(DEFAULT_LENGTH_OF_LIBRARY);
        assertThat(testInstitute.getWidthOfLibrary()).isEqualTo(DEFAULT_WIDTH_OF_LIBRARY);
        assertThat(testInstitute.getNumberOfBook()).isEqualTo(DEFAULT_NUMBER_OF_BOOK);
        assertThat(testInstitute.getLastMpoIncludeExpireDate()).isEqualTo(DEFAULT_LAST_MPO_INCLUDE_EXPIRE_DATE);
        assertThat(testInstitute.getNumberOfLab()).isEqualTo(DEFAULT_NUMBER_OF_LAB);
        assertThat(testInstitute.getLabArea()).isEqualTo(DEFAULT_LAB_AREA);
        assertThat(testInstitute.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInstitute.getLandPhone()).isEqualTo(DEFAULT_LAND_PHONE);
        assertThat(testInstitute.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testInstitute.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInstitute.getConstituencyArea()).isEqualTo(DEFAULT_CONSTITUENCY_AREA);
        assertThat(testInstitute.getAdminCounselorName()).isEqualTo(DEFAULT_ADMIN_COUNSELOR_NAME);
        assertThat(testInstitute.getCounselorMobileNo()).isEqualTo(DEFAULT_COUNSELOR_MOBILE_NO);
        assertThat(testInstitute.getInsHeadName()).isEqualTo(DEFAULT_INS_HEAD_NAME);
        assertThat(testInstitute.getInsHeadMobileNo()).isEqualTo(DEFAULT_INS_HEAD_MOBILE_NO);
        assertThat(testInstitute.getDeoName()).isEqualTo(DEFAULT_DEO_NAME);
        assertThat(testInstitute.getDeoMobileNo()).isEqualTo(DEFAULT_DEO_MOBILE_NO);
        assertThat(testInstitute.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setName(null);

        // Create the Institute, which fails.

        restInstituteMockMvc.perform(post("/api/institutes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(institute)))
                .andExpect(status().isBadRequest());

        List<Institute> institutes = instituteRepository.findAll();
        assertThat(institutes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setCode(null);

        // Create the Institute, which fails.

        restInstituteMockMvc.perform(post("/api/institutes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(institute)))
                .andExpect(status().isBadRequest());

        List<Institute> institutes = instituteRepository.findAll();
        assertThat(institutes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setType(null);

        // Create the Institute, which fails.

        restInstituteMockMvc.perform(post("/api/institutes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(institute)))
                .andExpect(status().isBadRequest());

        List<Institute> institutes = instituteRepository.findAll();
        assertThat(institutes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstitutes() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the institutes
        restInstituteMockMvc.perform(get("/api/institutes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(institute.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].dateOfEstablishment").value(hasItem(DEFAULT_DATE_OF_ESTABLISHMENT.toString())))
                .andExpect(jsonPath("$.[*].mpoCode").value(hasItem(DEFAULT_MPO_CODE.toString())))
                .andExpect(jsonPath("$.[*].postOffice").value(hasItem(DEFAULT_POST_OFFICE.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].isJoint").value(hasItem(DEFAULT_IS_JOINT.booleanValue())))
                .andExpect(jsonPath("$.[*].multiEducational").value(hasItem(DEFAULT_MULTI_EDUCATIONAL.toString())))
                .andExpect(jsonPath("$.[*].firstApprovedEducationalYear").value(hasItem(DEFAULT_FIRST_APPROVED_EDUCATIONAL_YEAR.toString())))
                .andExpect(jsonPath("$.[*].lastApprovedEducationalYear").value(hasItem(DEFAULT_LAST_APPROVED_EDUCATIONAL_YEAR.toString())))
                .andExpect(jsonPath("$.[*].firstMpoIncludeDate").value(hasItem(DEFAULT_FIRST_MPO_INCLUDE_DATE.toString())))
                .andExpect(jsonPath("$.[*].lastMpoExpireDate").value(hasItem(DEFAULT_LAST_MPO_EXPIRE_DATE.toString())))
                .andExpect(jsonPath("$.[*].nameOfTradeSubject").value(hasItem(DEFAULT_NAME_OF_TRADE_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].lastApprovedSignatureDate").value(hasItem(DEFAULT_LAST_APPROVED_SIGNATURE_DATE.toString())))
                .andExpect(jsonPath("$.[*].lastCommitteeApprovedDate").value(hasItem(DEFAULT_LAST_COMMITTEE_APPROVED_DATE.toString())))
                .andExpect(jsonPath("$.[*].lastCommitteeApprovedFileContentType").value(hasItem(DEFAULT_LAST_COMMITTEE_APPROVED_FILE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].lastCommitteeApprovedFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_LAST_COMMITTEE_APPROVED_FILE))))
                .andExpect(jsonPath("$.[*].lastCommitteeExpDate").value(hasItem(DEFAULT_LAST_COMMITTEE_EXP_DATE.toString())))
                .andExpect(jsonPath("$.[*].lastCommittee1stMeetingFileContentType").value(hasItem(DEFAULT_LAST_COMMITTEE1ST_MEETING_FILE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].lastCommittee1stMeetingFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_LAST_COMMITTEE1ST_MEETING_FILE))))
                .andExpect(jsonPath("$.[*].lastCommitteeExpireDate").value(hasItem(DEFAULT_LAST_COMMITTEE_EXPIRE_DATE.toString())))
                .andExpect(jsonPath("$.[*].lastMpoMemorialDate").value(hasItem(DEFAULT_LAST_MPO_MEMORIAL_DATE.toString())))
                .andExpect(jsonPath("$.[*].totalStudent").value(hasItem(DEFAULT_TOTAL_STUDENT)))
                .andExpect(jsonPath("$.[*].lengthOfLibrary").value(hasItem(DEFAULT_LENGTH_OF_LIBRARY.intValue())))
                .andExpect(jsonPath("$.[*].widthOfLibrary").value(hasItem(DEFAULT_WIDTH_OF_LIBRARY.intValue())))
                .andExpect(jsonPath("$.[*].numberOfBook").value(hasItem(DEFAULT_NUMBER_OF_BOOK)))
                .andExpect(jsonPath("$.[*].lastMpoIncludeExpireDate").value(hasItem(DEFAULT_LAST_MPO_INCLUDE_EXPIRE_DATE.toString())))
                .andExpect(jsonPath("$.[*].numberOfLab").value(hasItem(DEFAULT_NUMBER_OF_LAB)))
                .andExpect(jsonPath("$.[*].labArea").value(hasItem(DEFAULT_LAB_AREA.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].landPhone").value(hasItem(DEFAULT_LAND_PHONE.toString())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].constituencyArea").value(hasItem(DEFAULT_CONSTITUENCY_AREA.toString())))
                .andExpect(jsonPath("$.[*].adminCounselorName").value(hasItem(DEFAULT_ADMIN_COUNSELOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].counselorMobileNo").value(hasItem(DEFAULT_COUNSELOR_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].insHeadName").value(hasItem(DEFAULT_INS_HEAD_NAME.toString())))
                .andExpect(jsonPath("$.[*].insHeadMobileNo").value(hasItem(DEFAULT_INS_HEAD_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].deoName").value(hasItem(DEFAULT_DEO_NAME.toString())))
                .andExpect(jsonPath("$.[*].deoMobileNo").value(hasItem(DEFAULT_DEO_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get the institute
        restInstituteMockMvc.perform(get("/api/institutes/{id}", institute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(institute.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateOfEstablishment").value(DEFAULT_DATE_OF_ESTABLISHMENT.toString()))
            .andExpect(jsonPath("$.mpoCode").value(DEFAULT_MPO_CODE.toString()))
            .andExpect(jsonPath("$.postOffice").value(DEFAULT_POST_OFFICE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.isJoint").value(DEFAULT_IS_JOINT.booleanValue()))
            .andExpect(jsonPath("$.multiEducational").value(DEFAULT_MULTI_EDUCATIONAL.toString()))
            .andExpect(jsonPath("$.firstApprovedEducationalYear").value(DEFAULT_FIRST_APPROVED_EDUCATIONAL_YEAR.toString()))
            .andExpect(jsonPath("$.lastApprovedEducationalYear").value(DEFAULT_LAST_APPROVED_EDUCATIONAL_YEAR.toString()))
            .andExpect(jsonPath("$.firstMpoIncludeDate").value(DEFAULT_FIRST_MPO_INCLUDE_DATE.toString()))
            .andExpect(jsonPath("$.lastMpoExpireDate").value(DEFAULT_LAST_MPO_EXPIRE_DATE.toString()))
            .andExpect(jsonPath("$.nameOfTradeSubject").value(DEFAULT_NAME_OF_TRADE_SUBJECT.toString()))
            .andExpect(jsonPath("$.lastApprovedSignatureDate").value(DEFAULT_LAST_APPROVED_SIGNATURE_DATE.toString()))
            .andExpect(jsonPath("$.lastCommitteeApprovedDate").value(DEFAULT_LAST_COMMITTEE_APPROVED_DATE.toString()))
            .andExpect(jsonPath("$.lastCommitteeApprovedFileContentType").value(DEFAULT_LAST_COMMITTEE_APPROVED_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.lastCommitteeApprovedFile").value(Base64Utils.encodeToString(DEFAULT_LAST_COMMITTEE_APPROVED_FILE)))
            .andExpect(jsonPath("$.lastCommitteeExpDate").value(DEFAULT_LAST_COMMITTEE_EXP_DATE.toString()))
            .andExpect(jsonPath("$.lastCommittee1stMeetingFileContentType").value(DEFAULT_LAST_COMMITTEE1ST_MEETING_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.lastCommittee1stMeetingFile").value(Base64Utils.encodeToString(DEFAULT_LAST_COMMITTEE1ST_MEETING_FILE)))
            .andExpect(jsonPath("$.lastCommitteeExpireDate").value(DEFAULT_LAST_COMMITTEE_EXPIRE_DATE.toString()))
            .andExpect(jsonPath("$.lastMpoMemorialDate").value(DEFAULT_LAST_MPO_MEMORIAL_DATE.toString()))
            .andExpect(jsonPath("$.totalStudent").value(DEFAULT_TOTAL_STUDENT))
            .andExpect(jsonPath("$.lengthOfLibrary").value(DEFAULT_LENGTH_OF_LIBRARY.intValue()))
            .andExpect(jsonPath("$.widthOfLibrary").value(DEFAULT_WIDTH_OF_LIBRARY.intValue()))
            .andExpect(jsonPath("$.numberOfBook").value(DEFAULT_NUMBER_OF_BOOK))
            .andExpect(jsonPath("$.lastMpoIncludeExpireDate").value(DEFAULT_LAST_MPO_INCLUDE_EXPIRE_DATE.toString()))
            .andExpect(jsonPath("$.numberOfLab").value(DEFAULT_NUMBER_OF_LAB))
            .andExpect(jsonPath("$.labArea").value(DEFAULT_LAB_AREA.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.landPhone").value(DEFAULT_LAND_PHONE.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.constituencyArea").value(DEFAULT_CONSTITUENCY_AREA.toString()))
            .andExpect(jsonPath("$.adminCounselorName").value(DEFAULT_ADMIN_COUNSELOR_NAME.toString()))
            .andExpect(jsonPath("$.counselorMobileNo").value(DEFAULT_COUNSELOR_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.insHeadName").value(DEFAULT_INS_HEAD_NAME.toString()))
            .andExpect(jsonPath("$.insHeadMobileNo").value(DEFAULT_INS_HEAD_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.deoName").value(DEFAULT_DEO_NAME.toString()))
            .andExpect(jsonPath("$.deoMobileNo").value(DEFAULT_DEO_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstitute() throws Exception {
        // Get the institute
        restInstituteMockMvc.perform(get("/api/institutes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

		int databaseSizeBeforeUpdate = instituteRepository.findAll().size();

        // Update the institute
        institute.setName(UPDATED_NAME);
        institute.setDateOfEstablishment(UPDATED_DATE_OF_ESTABLISHMENT);
        institute.setMpoCode(UPDATED_MPO_CODE);
        institute.setPostOffice(UPDATED_POST_OFFICE);
        institute.setLocation(UPDATED_LOCATION);
        institute.setIsJoint(UPDATED_IS_JOINT);
        institute.setMultiEducational(UPDATED_MULTI_EDUCATIONAL);
        institute.setFirstApprovedEducationalYear(UPDATED_FIRST_APPROVED_EDUCATIONAL_YEAR);
        institute.setLastApprovedEducationalYear(UPDATED_LAST_APPROVED_EDUCATIONAL_YEAR);
        institute.setFirstMpoIncludeDate(UPDATED_FIRST_MPO_INCLUDE_DATE);
        institute.setLastMpoExpireDate(UPDATED_LAST_MPO_EXPIRE_DATE);
        institute.setNameOfTradeSubject(UPDATED_NAME_OF_TRADE_SUBJECT);
        institute.setLastApprovedSignatureDate(UPDATED_LAST_APPROVED_SIGNATURE_DATE);
        institute.setLastCommitteeApprovedDate(UPDATED_LAST_COMMITTEE_APPROVED_DATE);
        institute.setLastCommitteeApprovedFile(UPDATED_LAST_COMMITTEE_APPROVED_FILE);
        institute.setLastCommitteeApprovedFileContentType(UPDATED_LAST_COMMITTEE_APPROVED_FILE_CONTENT_TYPE);
        institute.setLastCommitteeExpDate(UPDATED_LAST_COMMITTEE_EXP_DATE);
        institute.setLastCommittee1stMeetingFile(UPDATED_LAST_COMMITTEE1ST_MEETING_FILE);
        institute.setLastCommittee1stMeetingFileContentType(UPDATED_LAST_COMMITTEE1ST_MEETING_FILE_CONTENT_TYPE);
        institute.setLastCommitteeExpireDate(UPDATED_LAST_COMMITTEE_EXPIRE_DATE);
        institute.setLastMpoMemorialDate(UPDATED_LAST_MPO_MEMORIAL_DATE);
        institute.setTotalStudent(UPDATED_TOTAL_STUDENT);
        institute.setLengthOfLibrary(UPDATED_LENGTH_OF_LIBRARY);
        institute.setWidthOfLibrary(UPDATED_WIDTH_OF_LIBRARY);
        institute.setNumberOfBook(UPDATED_NUMBER_OF_BOOK);
        institute.setLastMpoIncludeExpireDate(UPDATED_LAST_MPO_INCLUDE_EXPIRE_DATE);
        institute.setNumberOfLab(UPDATED_NUMBER_OF_LAB);
        institute.setLabArea(UPDATED_LAB_AREA);
        institute.setCode(UPDATED_CODE);
        institute.setLandPhone(UPDATED_LAND_PHONE);
        institute.setMobile(UPDATED_MOBILE);
        institute.setEmail(UPDATED_EMAIL);
        institute.setConstituencyArea(UPDATED_CONSTITUENCY_AREA);
        institute.setAdminCounselorName(UPDATED_ADMIN_COUNSELOR_NAME);
        institute.setCounselorMobileNo(UPDATED_COUNSELOR_MOBILE_NO);
        institute.setInsHeadName(UPDATED_INS_HEAD_NAME);
        institute.setInsHeadMobileNo(UPDATED_INS_HEAD_MOBILE_NO);
        institute.setDeoName(UPDATED_DEO_NAME);
        institute.setDeoMobileNo(UPDATED_DEO_MOBILE_NO);
        institute.setType(UPDATED_TYPE);

        restInstituteMockMvc.perform(put("/api/institutes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(institute)))
                .andExpect(status().isOk());

        // Validate the Institute in the database
        List<Institute> institutes = instituteRepository.findAll();
        assertThat(institutes).hasSize(databaseSizeBeforeUpdate);
        Institute testInstitute = institutes.get(institutes.size() - 1);
        assertThat(testInstitute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstitute.getDateOfEstablishment()).isEqualTo(UPDATED_DATE_OF_ESTABLISHMENT);
        assertThat(testInstitute.getMpoCode()).isEqualTo(UPDATED_MPO_CODE);
        assertThat(testInstitute.getPostOffice()).isEqualTo(UPDATED_POST_OFFICE);
        assertThat(testInstitute.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testInstitute.getIsJoint()).isEqualTo(UPDATED_IS_JOINT);
        assertThat(testInstitute.getMultiEducational()).isEqualTo(UPDATED_MULTI_EDUCATIONAL);
        assertThat(testInstitute.getFirstApprovedEducationalYear()).isEqualTo(UPDATED_FIRST_APPROVED_EDUCATIONAL_YEAR);
        assertThat(testInstitute.getLastApprovedEducationalYear()).isEqualTo(UPDATED_LAST_APPROVED_EDUCATIONAL_YEAR);
        assertThat(testInstitute.getFirstMpoIncludeDate()).isEqualTo(UPDATED_FIRST_MPO_INCLUDE_DATE);
        assertThat(testInstitute.getLastMpoExpireDate()).isEqualTo(UPDATED_LAST_MPO_EXPIRE_DATE);
        assertThat(testInstitute.getNameOfTradeSubject()).isEqualTo(UPDATED_NAME_OF_TRADE_SUBJECT);
        assertThat(testInstitute.getLastApprovedSignatureDate()).isEqualTo(UPDATED_LAST_APPROVED_SIGNATURE_DATE);
        assertThat(testInstitute.getLastCommitteeApprovedDate()).isEqualTo(UPDATED_LAST_COMMITTEE_APPROVED_DATE);
        assertThat(testInstitute.getLastCommitteeApprovedFile()).isEqualTo(UPDATED_LAST_COMMITTEE_APPROVED_FILE);
        assertThat(testInstitute.getLastCommitteeApprovedFileContentType()).isEqualTo(UPDATED_LAST_COMMITTEE_APPROVED_FILE_CONTENT_TYPE);
        assertThat(testInstitute.getLastCommitteeExpDate()).isEqualTo(UPDATED_LAST_COMMITTEE_EXP_DATE);
        assertThat(testInstitute.getLastCommittee1stMeetingFile()).isEqualTo(UPDATED_LAST_COMMITTEE1ST_MEETING_FILE);
        assertThat(testInstitute.getLastCommittee1stMeetingFileContentType()).isEqualTo(UPDATED_LAST_COMMITTEE1ST_MEETING_FILE_CONTENT_TYPE);
        assertThat(testInstitute.getLastCommitteeExpireDate()).isEqualTo(UPDATED_LAST_COMMITTEE_EXPIRE_DATE);
        assertThat(testInstitute.getLastMpoMemorialDate()).isEqualTo(UPDATED_LAST_MPO_MEMORIAL_DATE);
        assertThat(testInstitute.getTotalStudent()).isEqualTo(UPDATED_TOTAL_STUDENT);
        assertThat(testInstitute.getLengthOfLibrary()).isEqualTo(UPDATED_LENGTH_OF_LIBRARY);
        assertThat(testInstitute.getWidthOfLibrary()).isEqualTo(UPDATED_WIDTH_OF_LIBRARY);
        assertThat(testInstitute.getNumberOfBook()).isEqualTo(UPDATED_NUMBER_OF_BOOK);
        assertThat(testInstitute.getLastMpoIncludeExpireDate()).isEqualTo(UPDATED_LAST_MPO_INCLUDE_EXPIRE_DATE);
        assertThat(testInstitute.getNumberOfLab()).isEqualTo(UPDATED_NUMBER_OF_LAB);
        assertThat(testInstitute.getLabArea()).isEqualTo(UPDATED_LAB_AREA);
        assertThat(testInstitute.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInstitute.getLandPhone()).isEqualTo(UPDATED_LAND_PHONE);
        assertThat(testInstitute.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testInstitute.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstitute.getConstituencyArea()).isEqualTo(UPDATED_CONSTITUENCY_AREA);
        assertThat(testInstitute.getAdminCounselorName()).isEqualTo(UPDATED_ADMIN_COUNSELOR_NAME);
        assertThat(testInstitute.getCounselorMobileNo()).isEqualTo(UPDATED_COUNSELOR_MOBILE_NO);
        assertThat(testInstitute.getInsHeadName()).isEqualTo(UPDATED_INS_HEAD_NAME);
        assertThat(testInstitute.getInsHeadMobileNo()).isEqualTo(UPDATED_INS_HEAD_MOBILE_NO);
        assertThat(testInstitute.getDeoName()).isEqualTo(UPDATED_DEO_NAME);
        assertThat(testInstitute.getDeoMobileNo()).isEqualTo(UPDATED_DEO_MOBILE_NO);
        assertThat(testInstitute.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

		int databaseSizeBeforeDelete = instituteRepository.findAll().size();

        // Get the institute
        restInstituteMockMvc.perform(delete("/api/institutes/{id}", institute.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Institute> institutes = instituteRepository.findAll();
        assertThat(institutes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
