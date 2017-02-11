package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.domain.enumeration.*;
import gov.step.app.repository.HrEmployeeInfoRepository;
import gov.step.app.repository.search.HrEmployeeInfoSearchRepository;
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
 * Test class for the HrEmployeeInfoResource REST controller.
 *
 * @see HrEmployeeInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmployeeInfoResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBB";
    private static final String DEFAULT_FULL_NAME_BN = "AAAAA";
    private static final String UPDATED_FULL_NAME_BN = "BBBBB";
    private static final String DEFAULT_FATHER_NAME = "AAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBB";
    private static final String DEFAULT_FATHER_NAME_BN = "AAAAA";
    private static final String UPDATED_FATHER_NAME_BN = "BBBBB";
    private static final String DEFAULT_MOTHER_NAME = "AAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBB";
    private static final String DEFAULT_MOTHER_NAME_BN = "AAAAA";
    private static final String UPDATED_MOTHER_NAME_BN = "BBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APOINTMENT_GO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APOINTMENT_GO_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_PRESENT_ID = "AAAAA";
    private static final String UPDATED_PRESENT_ID = "BBBBB";
    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBBBBB";
    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBB";
    private static final String DEFAULT_MOBILE_NUMBER = "AAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBB";


    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;
    private static final String DEFAULT_BIRTH_PLACE = "AAAAA";
    private static final String UPDATED_BIRTH_PLACE = "BBBBB";
    private static final String DEFAULT_ANY_DISEASE = "AAAAA";
    private static final String UPDATED_ANY_DISEASE = "BBBBB";
    private static final String DEFAULT_OFFICER_STUFF = "AAAAA";
    private static final String UPDATED_OFFICER_STUFF = "BBBBB";
    private static final String DEFAULT_TIN_NUMBER = "AAAAA";
    private static final String UPDATED_TIN_NUMBER = "BBBBB";


    private static final maritalStatus DEFAULT_MARITAL_STATUS = maritalStatus.Single;
    private static final maritalStatus UPDATED_MARITAL_STATUS = maritalStatus.Married;


    private static final bloodGroup DEFAULT_BLOOD_GROUP = bloodGroup.A_POSITIVE;
    private static final bloodGroup UPDATED_BLOOD_GROUP = bloodGroup.A_NEGATIVE;
    private static final String DEFAULT_NATIONALITY = "AAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBB";


    private static final jobQuota DEFAULT_QUOTA = jobQuota.FreedomFighter;
    private static final jobQuota UPDATED_QUOTA = jobQuota.ChileOfFreedomFighter;
    private static final String DEFAULT_BIRTH_CERTIFICATE_NO = "AAAAA";
    private static final String UPDATED_BIRTH_CERTIFICATE_NO = "BBBBB";


    private static final Religions DEFAULT_RELIGION = Religions.Islam;
    private static final Religions UPDATED_RELIGION = Religions.Hinduism;

    private static final Boolean DEFAULT_OPEN_FOR_EDIT = false;
    private static final Boolean UPDATED_OPEN_FOR_EDIT = true;

    private static final Boolean DEFAULT_OPEN_FOR_APPROVAL = false;
    private static final Boolean UPDATED_OPEN_FOR_APPROVAL = true;

    private static final Long DEFAULT_EMP_LOG_ID = 1L;
    private static final Long UPDATED_EMP_LOG_ID = 2L;
    private static final String DEFAULT_ACTION_COMMENTS = "AAAAA";
    private static final String UPDATED_ACTION_COMMENTS = "BBBBB";

    private static final LocalDate DEFAULT_ACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ACTION_BY = 1L;
    private static final Long UPDATED_ACTION_BY = 2L;

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
    private HrEmployeeInfoRepository hrEmployeeInfoRepository;

    @Inject
    private HrEmployeeInfoSearchRepository hrEmployeeInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmployeeInfoMockMvc;

    private HrEmployeeInfo hrEmployeeInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmployeeInfoResource hrEmployeeInfoResource = new HrEmployeeInfoResource();
        ReflectionTestUtils.setField(hrEmployeeInfoResource, "hrEmployeeInfoSearchRepository", hrEmployeeInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmployeeInfoResource, "hrEmployeeInfoRepository", hrEmployeeInfoRepository);
        this.restHrEmployeeInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmployeeInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmployeeInfo = new HrEmployeeInfo();
        hrEmployeeInfo.setFullName(DEFAULT_FULL_NAME);
        hrEmployeeInfo.setFullNameBn(DEFAULT_FULL_NAME_BN);
        hrEmployeeInfo.setFatherName(DEFAULT_FATHER_NAME);
        hrEmployeeInfo.setFatherNameBn(DEFAULT_FATHER_NAME_BN);
        hrEmployeeInfo.setMotherName(DEFAULT_MOTHER_NAME);
        hrEmployeeInfo.setMotherNameBn(DEFAULT_MOTHER_NAME_BN);
        hrEmployeeInfo.setBirthDate(DEFAULT_BIRTH_DATE);
        hrEmployeeInfo.setApointmentGoDate(DEFAULT_APOINTMENT_GO_DATE);
        hrEmployeeInfo.setPresentId(DEFAULT_PRESENT_ID);
        hrEmployeeInfo.setNationalId(DEFAULT_NATIONAL_ID);
        hrEmployeeInfo.setEmailAddress(DEFAULT_EMAIL_ADDRESS);
        hrEmployeeInfo.setMobileNumber(DEFAULT_MOBILE_NUMBER);
        hrEmployeeInfo.setGender(DEFAULT_GENDER);
        hrEmployeeInfo.setBirthPlace(DEFAULT_BIRTH_PLACE);
        hrEmployeeInfo.setAnyDisease(DEFAULT_ANY_DISEASE);
        hrEmployeeInfo.setOfficerStuff(DEFAULT_OFFICER_STUFF);
        hrEmployeeInfo.setTinNumber(DEFAULT_TIN_NUMBER);
        hrEmployeeInfo.setMaritalStatus(DEFAULT_MARITAL_STATUS);
        hrEmployeeInfo.setBloodGroup(DEFAULT_BLOOD_GROUP);
        hrEmployeeInfo.setNationality(DEFAULT_NATIONALITY);
        hrEmployeeInfo.setQuota(DEFAULT_QUOTA);
        hrEmployeeInfo.setBirthCertificateNo(DEFAULT_BIRTH_CERTIFICATE_NO);
        hrEmployeeInfo.setReligion(DEFAULT_RELIGION);
        hrEmployeeInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmployeeInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmployeeInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmployeeInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmployeeInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmployeeInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmployeeInfoRepository.findAll().size();

        // Create the HrEmployeeInfo

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmployeeInfo in the database
        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmployeeInfo testHrEmployeeInfo = hrEmployeeInfos.get(hrEmployeeInfos.size() - 1);
        assertThat(testHrEmployeeInfo.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testHrEmployeeInfo.getFullNameBn()).isEqualTo(DEFAULT_FULL_NAME_BN);
        assertThat(testHrEmployeeInfo.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testHrEmployeeInfo.getFatherNameBn()).isEqualTo(DEFAULT_FATHER_NAME_BN);
        assertThat(testHrEmployeeInfo.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testHrEmployeeInfo.getMotherNameBn()).isEqualTo(DEFAULT_MOTHER_NAME_BN);
        assertThat(testHrEmployeeInfo.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testHrEmployeeInfo.getApointmentGoDate()).isEqualTo(DEFAULT_APOINTMENT_GO_DATE);
        assertThat(testHrEmployeeInfo.getPresentId()).isEqualTo(DEFAULT_PRESENT_ID);
        assertThat(testHrEmployeeInfo.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testHrEmployeeInfo.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testHrEmployeeInfo.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testHrEmployeeInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testHrEmployeeInfo.getBirthPlace()).isEqualTo(DEFAULT_BIRTH_PLACE);
        assertThat(testHrEmployeeInfo.getAnyDisease()).isEqualTo(DEFAULT_ANY_DISEASE);
        assertThat(testHrEmployeeInfo.getOfficerStuff()).isEqualTo(DEFAULT_OFFICER_STUFF);
        assertThat(testHrEmployeeInfo.getTinNumber()).isEqualTo(DEFAULT_TIN_NUMBER);
        assertThat(testHrEmployeeInfo.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testHrEmployeeInfo.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testHrEmployeeInfo.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testHrEmployeeInfo.getQuota()).isEqualTo(DEFAULT_QUOTA);
        assertThat(testHrEmployeeInfo.getBirthCertificateNo()).isEqualTo(DEFAULT_BIRTH_CERTIFICATE_NO);
        assertThat(testHrEmployeeInfo.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testHrEmployeeInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmployeeInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmployeeInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmployeeInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmployeeInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmployeeInfoRepository.findAll().size();
        // set the field null
        hrEmployeeInfo.setFullName(null);

        // Create the HrEmployeeInfo, which fails.

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFatherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmployeeInfoRepository.findAll().size();
        // set the field null
        hrEmployeeInfo.setFatherName(null);

        // Create the HrEmployeeInfo, which fails.

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmployeeInfoRepository.findAll().size();
        // set the field null
        hrEmployeeInfo.setMotherName(null);

        // Create the HrEmployeeInfo, which fails.

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmployeeInfoRepository.findAll().size();
        // set the field null
        hrEmployeeInfo.setBirthDate(null);

        // Create the HrEmployeeInfo, which fails.

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApointmentGoDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmployeeInfoRepository.findAll().size();
        // set the field null
        hrEmployeeInfo.setApointmentGoDate(null);

        // Create the HrEmployeeInfo, which fails.

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmployeeInfoRepository.findAll().size();
        // set the field null
        hrEmployeeInfo.setEmailAddress(null);

        // Create the HrEmployeeInfo, which fails.

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmployeeInfoRepository.findAll().size();
        // set the field null
        hrEmployeeInfo.setMobileNumber(null);

        // Create the HrEmployeeInfo, which fails.

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmployeeInfoRepository.findAll().size();
        // set the field null
        hrEmployeeInfo.setGender(null);

        // Create the HrEmployeeInfo, which fails.

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmployeeInfoRepository.findAll().size();
        // set the field null
        hrEmployeeInfo.setNationality(null);

        // Create the HrEmployeeInfo, which fails.

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmployeeInfoRepository.findAll().size();
        // set the field null
        hrEmployeeInfo.setActiveStatus(null);

        // Create the HrEmployeeInfo, which fails.

        restHrEmployeeInfoMockMvc.perform(post("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmployeeInfos() throws Exception {
        // Initialize the database
        hrEmployeeInfoRepository.saveAndFlush(hrEmployeeInfo);

        // Get all the hrEmployeeInfos
        restHrEmployeeInfoMockMvc.perform(get("/api/hrEmployeeInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmployeeInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].fullNameBn").value(hasItem(DEFAULT_FULL_NAME_BN.toString())))
                .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].fatherNameBn").value(hasItem(DEFAULT_FATHER_NAME_BN.toString())))
                .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].motherNameBn").value(hasItem(DEFAULT_MOTHER_NAME_BN.toString())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
                .andExpect(jsonPath("$.[*].apointmentGoDate").value(hasItem(DEFAULT_APOINTMENT_GO_DATE.toString())))
                .andExpect(jsonPath("$.[*].presentId").value(hasItem(DEFAULT_PRESENT_ID.toString())))
                .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID.toString())))
                .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].birthPlace").value(hasItem(DEFAULT_BIRTH_PLACE.toString())))
                .andExpect(jsonPath("$.[*].anyDisease").value(hasItem(DEFAULT_ANY_DISEASE.toString())))
                .andExpect(jsonPath("$.[*].officerStuff").value(hasItem(DEFAULT_OFFICER_STUFF.toString())))
                .andExpect(jsonPath("$.[*].tinNumber").value(hasItem(DEFAULT_TIN_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
                .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
                .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
                .andExpect(jsonPath("$.[*].quota").value(hasItem(DEFAULT_QUOTA.toString())))
                .andExpect(jsonPath("$.[*].birthCertificateNo").value(hasItem(DEFAULT_BIRTH_CERTIFICATE_NO.toString())))
                .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmployeeInfo() throws Exception {
        // Initialize the database
        hrEmployeeInfoRepository.saveAndFlush(hrEmployeeInfo);

        // Get the hrEmployeeInfo
        restHrEmployeeInfoMockMvc.perform(get("/api/hrEmployeeInfos/{id}", hrEmployeeInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmployeeInfo.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.fullNameBn").value(DEFAULT_FULL_NAME_BN.toString()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME.toString()))
            .andExpect(jsonPath("$.fatherNameBn").value(DEFAULT_FATHER_NAME_BN.toString()))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME.toString()))
            .andExpect(jsonPath("$.motherNameBn").value(DEFAULT_MOTHER_NAME_BN.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.apointmentGoDate").value(DEFAULT_APOINTMENT_GO_DATE.toString()))
            .andExpect(jsonPath("$.presentId").value(DEFAULT_PRESENT_ID.toString()))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.birthPlace").value(DEFAULT_BIRTH_PLACE.toString()))
            .andExpect(jsonPath("$.anyDisease").value(DEFAULT_ANY_DISEASE.toString()))
            .andExpect(jsonPath("$.officerStuff").value(DEFAULT_OFFICER_STUFF.toString()))
            .andExpect(jsonPath("$.tinNumber").value(DEFAULT_TIN_NUMBER.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.quota").value(DEFAULT_QUOTA.toString()))
            .andExpect(jsonPath("$.birthCertificateNo").value(DEFAULT_BIRTH_CERTIFICATE_NO.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmployeeInfo() throws Exception {
        // Get the hrEmployeeInfo
        restHrEmployeeInfoMockMvc.perform(get("/api/hrEmployeeInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmployeeInfo() throws Exception {
        // Initialize the database
        hrEmployeeInfoRepository.saveAndFlush(hrEmployeeInfo);

		int databaseSizeBeforeUpdate = hrEmployeeInfoRepository.findAll().size();

        // Update the hrEmployeeInfo
        hrEmployeeInfo.setFullName(UPDATED_FULL_NAME);
        hrEmployeeInfo.setFullNameBn(UPDATED_FULL_NAME_BN);
        hrEmployeeInfo.setFatherName(UPDATED_FATHER_NAME);
        hrEmployeeInfo.setFatherNameBn(UPDATED_FATHER_NAME_BN);
        hrEmployeeInfo.setMotherName(UPDATED_MOTHER_NAME);
        hrEmployeeInfo.setMotherNameBn(UPDATED_MOTHER_NAME_BN);
        hrEmployeeInfo.setBirthDate(UPDATED_BIRTH_DATE);
        hrEmployeeInfo.setApointmentGoDate(UPDATED_APOINTMENT_GO_DATE);
        hrEmployeeInfo.setPresentId(UPDATED_PRESENT_ID);
        hrEmployeeInfo.setNationalId(UPDATED_NATIONAL_ID);
        hrEmployeeInfo.setEmailAddress(UPDATED_EMAIL_ADDRESS);
        hrEmployeeInfo.setMobileNumber(UPDATED_MOBILE_NUMBER);
        hrEmployeeInfo.setGender(UPDATED_GENDER);
        hrEmployeeInfo.setBirthPlace(UPDATED_BIRTH_PLACE);
        hrEmployeeInfo.setAnyDisease(UPDATED_ANY_DISEASE);
        hrEmployeeInfo.setOfficerStuff(UPDATED_OFFICER_STUFF);
        hrEmployeeInfo.setTinNumber(UPDATED_TIN_NUMBER);
        hrEmployeeInfo.setMaritalStatus(UPDATED_MARITAL_STATUS);
        hrEmployeeInfo.setBloodGroup(UPDATED_BLOOD_GROUP);
        hrEmployeeInfo.setNationality(UPDATED_NATIONALITY);
        hrEmployeeInfo.setQuota(UPDATED_QUOTA);
        hrEmployeeInfo.setBirthCertificateNo(UPDATED_BIRTH_CERTIFICATE_NO);
        hrEmployeeInfo.setReligion(UPDATED_RELIGION);
        hrEmployeeInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmployeeInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmployeeInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmployeeInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmployeeInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmployeeInfoMockMvc.perform(put("/api/hrEmployeeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmployeeInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmployeeInfo in the database
        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmployeeInfo testHrEmployeeInfo = hrEmployeeInfos.get(hrEmployeeInfos.size() - 1);
        assertThat(testHrEmployeeInfo.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testHrEmployeeInfo.getFullNameBn()).isEqualTo(UPDATED_FULL_NAME_BN);
        assertThat(testHrEmployeeInfo.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testHrEmployeeInfo.getFatherNameBn()).isEqualTo(UPDATED_FATHER_NAME_BN);
        assertThat(testHrEmployeeInfo.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testHrEmployeeInfo.getMotherNameBn()).isEqualTo(UPDATED_MOTHER_NAME_BN);
        assertThat(testHrEmployeeInfo.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testHrEmployeeInfo.getApointmentGoDate()).isEqualTo(UPDATED_APOINTMENT_GO_DATE);
        assertThat(testHrEmployeeInfo.getPresentId()).isEqualTo(UPDATED_PRESENT_ID);
        assertThat(testHrEmployeeInfo.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testHrEmployeeInfo.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testHrEmployeeInfo.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testHrEmployeeInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testHrEmployeeInfo.getBirthPlace()).isEqualTo(UPDATED_BIRTH_PLACE);
        assertThat(testHrEmployeeInfo.getAnyDisease()).isEqualTo(UPDATED_ANY_DISEASE);
        assertThat(testHrEmployeeInfo.getOfficerStuff()).isEqualTo(UPDATED_OFFICER_STUFF);
        assertThat(testHrEmployeeInfo.getTinNumber()).isEqualTo(UPDATED_TIN_NUMBER);
        assertThat(testHrEmployeeInfo.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testHrEmployeeInfo.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testHrEmployeeInfo.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testHrEmployeeInfo.getQuota()).isEqualTo(UPDATED_QUOTA);
        assertThat(testHrEmployeeInfo.getBirthCertificateNo()).isEqualTo(UPDATED_BIRTH_CERTIFICATE_NO);
        assertThat(testHrEmployeeInfo.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testHrEmployeeInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmployeeInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmployeeInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmployeeInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmployeeInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmployeeInfo() throws Exception {
        // Initialize the database
        hrEmployeeInfoRepository.saveAndFlush(hrEmployeeInfo);

		int databaseSizeBeforeDelete = hrEmployeeInfoRepository.findAll().size();

        // Get the hrEmployeeInfo
        restHrEmployeeInfoMockMvc.perform(delete("/api/hrEmployeeInfos/{id}", hrEmployeeInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmployeeInfo> hrEmployeeInfos = hrEmployeeInfoRepository.findAll();
        assertThat(hrEmployeeInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
