package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SisStudentInfo;
import gov.step.app.repository.SisStudentInfoRepository;
import gov.step.app.repository.search.SisStudentInfoSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SisStudentInfoResource REST controller.
 *
 * @see SisStudentInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SisStudentInfoResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final byte[] DEFAULT_STU_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_STU_PICTURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_STU_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_STU_PICTURE_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_INSTITUTE_NAME = "AAAAA";
    private static final String UPDATED_INSTITUTE_NAME = "BBBBB";
    private static final String DEFAULT_TRADE_TECHNOLOGY = "AAAAA";
    private static final String UPDATED_TRADE_TECHNOLOGY = "BBBBB";
    private static final String DEFAULT_STUDENT_NAME = "AAAAA";
    private static final String UPDATED_STUDENT_NAME = "BBBBB";
    private static final String DEFAULT_FATHER_NAME = "AAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBB";
    private static final String DEFAULT_MOTHER_NAME = "AAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBB";
    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBB";
    private static final String DEFAULT_PRESENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PRESENT_ADDRESS = "BBBBB";
    private static final String DEFAULT_PERMANENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PERMANENT_ADDRESS = "BBBBB";
    private static final String DEFAULT_NATIONALITY = "AAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBB";
    private static final String DEFAULT_NATIONAL_ID_NO = "AAAAA";
    private static final String UPDATED_NATIONAL_ID_NO = "BBBBB";
    private static final String DEFAULT_BIRTH_CERTIFICATE_NO = "AAAAA";
    private static final String UPDATED_BIRTH_CERTIFICATE_NO = "BBBBB";
    private static final String DEFAULT_MOBILE_NO = "AAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBB";
    private static final String DEFAULT_CONTACT_NO_HOME = "AAAAA";
    private static final String UPDATED_CONTACT_NO_HOME = "BBBBB";
    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBB";
    private static final String DEFAULT_GENDER = "AAAAA";
    private static final String UPDATED_GENDER = "BBBBB";
    private static final String DEFAULT_MARITAL_STATUS = "AAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBB";
    private static final String DEFAULT_BLOOD_GROUP = "AAAAA";
    private static final String UPDATED_BLOOD_GROUP = "BBBBB";
    private static final String DEFAULT_RELIGION = "AAAAA";
    private static final String UPDATED_RELIGION = "BBBBB";

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
    private static final String DEFAULT_CURRICULUM = "AAAAA";
    private static final String UPDATED_CURRICULUM = "BBBBB";

    private static final Long DEFAULT_APPLICATION_ID = 1L;
    private static final Long UPDATED_APPLICATION_ID = 2L;

    @Inject
    private SisStudentInfoRepository sisStudentInfoRepository;

    @Inject
    private SisStudentInfoSearchRepository sisStudentInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSisStudentInfoMockMvc;

    private SisStudentInfo sisStudentInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SisStudentInfoResource sisStudentInfoResource = new SisStudentInfoResource();
        ReflectionTestUtils.setField(sisStudentInfoResource, "sisStudentInfoRepository", sisStudentInfoRepository);
        ReflectionTestUtils.setField(sisStudentInfoResource, "sisStudentInfoSearchRepository", sisStudentInfoSearchRepository);
        this.restSisStudentInfoMockMvc = MockMvcBuilders.standaloneSetup(sisStudentInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        sisStudentInfo = new SisStudentInfo();
        sisStudentInfo.setName(DEFAULT_NAME);
        sisStudentInfo.setStuPicture(DEFAULT_STU_PICTURE);
        sisStudentInfo.setStuPictureContentType(DEFAULT_STU_PICTURE_CONTENT_TYPE);
        sisStudentInfo.setInstituteName(DEFAULT_INSTITUTE_NAME);
        sisStudentInfo.setTradeTechnology(DEFAULT_TRADE_TECHNOLOGY);
        sisStudentInfo.setStudentName(DEFAULT_STUDENT_NAME);
        sisStudentInfo.setFatherName(DEFAULT_FATHER_NAME);
        sisStudentInfo.setMotherName(DEFAULT_MOTHER_NAME);
        sisStudentInfo.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
        sisStudentInfo.setPresentAddress(DEFAULT_PRESENT_ADDRESS);
        sisStudentInfo.setPermanentAddress(DEFAULT_PERMANENT_ADDRESS);
        sisStudentInfo.setNationality(DEFAULT_NATIONALITY);
        sisStudentInfo.setNationalIdNo(DEFAULT_NATIONAL_ID_NO);
        sisStudentInfo.setBirthCertificateNo(DEFAULT_BIRTH_CERTIFICATE_NO);
        sisStudentInfo.setMobileNo(DEFAULT_MOBILE_NO);
        sisStudentInfo.setContactNoHome(DEFAULT_CONTACT_NO_HOME);
        sisStudentInfo.setEmailAddress(DEFAULT_EMAIL_ADDRESS);
        sisStudentInfo.setGender(DEFAULT_GENDER);
        sisStudentInfo.setMaritalStatus(DEFAULT_MARITAL_STATUS);
        sisStudentInfo.setBloodGroup(DEFAULT_BLOOD_GROUP);
        sisStudentInfo.setReligion(DEFAULT_RELIGION);
        sisStudentInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        sisStudentInfo.setCreateDate(DEFAULT_CREATE_DATE);
        sisStudentInfo.setCreateBy(DEFAULT_CREATE_BY);
        sisStudentInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        sisStudentInfo.setUpdateBy(DEFAULT_UPDATE_BY);
        sisStudentInfo.setCurriculum(DEFAULT_CURRICULUM);
        sisStudentInfo.setApplicationId(DEFAULT_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void createSisStudentInfo() throws Exception {
        int databaseSizeBeforeCreate = sisStudentInfoRepository.findAll().size();

        // Create the SisStudentInfo

        restSisStudentInfoMockMvc.perform(post("/api/sisStudentInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisStudentInfo)))
                .andExpect(status().isCreated());

        // Validate the SisStudentInfo in the database
        List<SisStudentInfo> sisStudentInfos = sisStudentInfoRepository.findAll();
        assertThat(sisStudentInfos).hasSize(databaseSizeBeforeCreate + 1);
        SisStudentInfo testSisStudentInfo = sisStudentInfos.get(sisStudentInfos.size() - 1);
        assertThat(testSisStudentInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSisStudentInfo.getStuPicture()).isEqualTo(DEFAULT_STU_PICTURE);
        assertThat(testSisStudentInfo.getStuPictureContentType()).isEqualTo(DEFAULT_STU_PICTURE_CONTENT_TYPE);
        assertThat(testSisStudentInfo.getInstituteName()).isEqualTo(DEFAULT_INSTITUTE_NAME);
        assertThat(testSisStudentInfo.getTradeTechnology()).isEqualTo(DEFAULT_TRADE_TECHNOLOGY);
        assertThat(testSisStudentInfo.getStudentName()).isEqualTo(DEFAULT_STUDENT_NAME);
        assertThat(testSisStudentInfo.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testSisStudentInfo.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testSisStudentInfo.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testSisStudentInfo.getPresentAddress()).isEqualTo(DEFAULT_PRESENT_ADDRESS);
        assertThat(testSisStudentInfo.getPermanentAddress()).isEqualTo(DEFAULT_PERMANENT_ADDRESS);
        assertThat(testSisStudentInfo.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testSisStudentInfo.getNationalIdNo()).isEqualTo(DEFAULT_NATIONAL_ID_NO);
        assertThat(testSisStudentInfo.getBirthCertificateNo()).isEqualTo(DEFAULT_BIRTH_CERTIFICATE_NO);
        assertThat(testSisStudentInfo.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testSisStudentInfo.getContactNoHome()).isEqualTo(DEFAULT_CONTACT_NO_HOME);
        assertThat(testSisStudentInfo.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testSisStudentInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testSisStudentInfo.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testSisStudentInfo.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testSisStudentInfo.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testSisStudentInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testSisStudentInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testSisStudentInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testSisStudentInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testSisStudentInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testSisStudentInfo.getCurriculum()).isEqualTo(DEFAULT_CURRICULUM);
        assertThat(testSisStudentInfo.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sisStudentInfoRepository.findAll().size();
        // set the field null
        sisStudentInfo.setName(null);

        // Create the SisStudentInfo, which fails.

        restSisStudentInfoMockMvc.perform(post("/api/sisStudentInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisStudentInfo)))
                .andExpect(status().isBadRequest());

        List<SisStudentInfo> sisStudentInfos = sisStudentInfoRepository.findAll();
        assertThat(sisStudentInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSisStudentInfos() throws Exception {
        // Initialize the database
        sisStudentInfoRepository.saveAndFlush(sisStudentInfo);

        // Get all the sisStudentInfos
        restSisStudentInfoMockMvc.perform(get("/api/sisStudentInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sisStudentInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].stuPictureContentType").value(hasItem(DEFAULT_STU_PICTURE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].stuPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_STU_PICTURE))))
                .andExpect(jsonPath("$.[*].instituteName").value(hasItem(DEFAULT_INSTITUTE_NAME.toString())))
                .andExpect(jsonPath("$.[*].TradeTechnology").value(hasItem(DEFAULT_TRADE_TECHNOLOGY.toString())))
                .andExpect(jsonPath("$.[*].studentName").value(hasItem(DEFAULT_STUDENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
                .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
                .andExpect(jsonPath("$.[*].nationalIdNo").value(hasItem(DEFAULT_NATIONAL_ID_NO.toString())))
                .andExpect(jsonPath("$.[*].birthCertificateNo").value(hasItem(DEFAULT_BIRTH_CERTIFICATE_NO.toString())))
                .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].contactNoHome").value(hasItem(DEFAULT_CONTACT_NO_HOME.toString())))
                .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
                .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
                .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].curriculum").value(hasItem(DEFAULT_CURRICULUM.toString())))
                .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())));
    }

    @Test
    @Transactional
    public void getSisStudentInfo() throws Exception {
        // Initialize the database
        sisStudentInfoRepository.saveAndFlush(sisStudentInfo);

        // Get the sisStudentInfo
        restSisStudentInfoMockMvc.perform(get("/api/sisStudentInfos/{id}", sisStudentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sisStudentInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.stuPictureContentType").value(DEFAULT_STU_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.stuPicture").value(Base64Utils.encodeToString(DEFAULT_STU_PICTURE)))
            .andExpect(jsonPath("$.instituteName").value(DEFAULT_INSTITUTE_NAME.toString()))
            .andExpect(jsonPath("$.TradeTechnology").value(DEFAULT_TRADE_TECHNOLOGY.toString()))
            .andExpect(jsonPath("$.studentName").value(DEFAULT_STUDENT_NAME.toString()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME.toString()))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.presentAddress").value(DEFAULT_PRESENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.permanentAddress").value(DEFAULT_PERMANENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.nationalIdNo").value(DEFAULT_NATIONAL_ID_NO.toString()))
            .andExpect(jsonPath("$.birthCertificateNo").value(DEFAULT_BIRTH_CERTIFICATE_NO.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.contactNoHome").value(DEFAULT_CONTACT_NO_HOME.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.curriculum").value(DEFAULT_CURRICULUM.toString()))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSisStudentInfo() throws Exception {
        // Get the sisStudentInfo
        restSisStudentInfoMockMvc.perform(get("/api/sisStudentInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSisStudentInfo() throws Exception {
        // Initialize the database
        sisStudentInfoRepository.saveAndFlush(sisStudentInfo);

		int databaseSizeBeforeUpdate = sisStudentInfoRepository.findAll().size();

        // Update the sisStudentInfo
        sisStudentInfo.setName(UPDATED_NAME);
        sisStudentInfo.setStuPicture(UPDATED_STU_PICTURE);
        sisStudentInfo.setStuPictureContentType(UPDATED_STU_PICTURE_CONTENT_TYPE);
        sisStudentInfo.setInstituteName(UPDATED_INSTITUTE_NAME);
        sisStudentInfo.setTradeTechnology(UPDATED_TRADE_TECHNOLOGY);
        sisStudentInfo.setStudentName(UPDATED_STUDENT_NAME);
        sisStudentInfo.setFatherName(UPDATED_FATHER_NAME);
        sisStudentInfo.setMotherName(UPDATED_MOTHER_NAME);
        sisStudentInfo.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        sisStudentInfo.setPresentAddress(UPDATED_PRESENT_ADDRESS);
        sisStudentInfo.setPermanentAddress(UPDATED_PERMANENT_ADDRESS);
        sisStudentInfo.setNationality(UPDATED_NATIONALITY);
        sisStudentInfo.setNationalIdNo(UPDATED_NATIONAL_ID_NO);
        sisStudentInfo.setBirthCertificateNo(UPDATED_BIRTH_CERTIFICATE_NO);
        sisStudentInfo.setMobileNo(UPDATED_MOBILE_NO);
        sisStudentInfo.setContactNoHome(UPDATED_CONTACT_NO_HOME);
        sisStudentInfo.setEmailAddress(UPDATED_EMAIL_ADDRESS);
        sisStudentInfo.setGender(UPDATED_GENDER);
        sisStudentInfo.setMaritalStatus(UPDATED_MARITAL_STATUS);
        sisStudentInfo.setBloodGroup(UPDATED_BLOOD_GROUP);
        sisStudentInfo.setReligion(UPDATED_RELIGION);
        sisStudentInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        sisStudentInfo.setCreateDate(UPDATED_CREATE_DATE);
        sisStudentInfo.setCreateBy(UPDATED_CREATE_BY);
        sisStudentInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        sisStudentInfo.setUpdateBy(UPDATED_UPDATE_BY);
        sisStudentInfo.setCurriculum(UPDATED_CURRICULUM);
        sisStudentInfo.setApplicationId(UPDATED_APPLICATION_ID);

        restSisStudentInfoMockMvc.perform(put("/api/sisStudentInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisStudentInfo)))
                .andExpect(status().isOk());

        // Validate the SisStudentInfo in the database
        List<SisStudentInfo> sisStudentInfos = sisStudentInfoRepository.findAll();
        assertThat(sisStudentInfos).hasSize(databaseSizeBeforeUpdate);
        SisStudentInfo testSisStudentInfo = sisStudentInfos.get(sisStudentInfos.size() - 1);
        assertThat(testSisStudentInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSisStudentInfo.getStuPicture()).isEqualTo(UPDATED_STU_PICTURE);
        assertThat(testSisStudentInfo.getStuPictureContentType()).isEqualTo(UPDATED_STU_PICTURE_CONTENT_TYPE);
        assertThat(testSisStudentInfo.getInstituteName()).isEqualTo(UPDATED_INSTITUTE_NAME);
        assertThat(testSisStudentInfo.getTradeTechnology()).isEqualTo(UPDATED_TRADE_TECHNOLOGY);
        assertThat(testSisStudentInfo.getStudentName()).isEqualTo(UPDATED_STUDENT_NAME);
        assertThat(testSisStudentInfo.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testSisStudentInfo.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testSisStudentInfo.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testSisStudentInfo.getPresentAddress()).isEqualTo(UPDATED_PRESENT_ADDRESS);
        assertThat(testSisStudentInfo.getPermanentAddress()).isEqualTo(UPDATED_PERMANENT_ADDRESS);
        assertThat(testSisStudentInfo.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testSisStudentInfo.getNationalIdNo()).isEqualTo(UPDATED_NATIONAL_ID_NO);
        assertThat(testSisStudentInfo.getBirthCertificateNo()).isEqualTo(UPDATED_BIRTH_CERTIFICATE_NO);
        assertThat(testSisStudentInfo.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testSisStudentInfo.getContactNoHome()).isEqualTo(UPDATED_CONTACT_NO_HOME);
        assertThat(testSisStudentInfo.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testSisStudentInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testSisStudentInfo.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testSisStudentInfo.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testSisStudentInfo.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testSisStudentInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testSisStudentInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testSisStudentInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testSisStudentInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testSisStudentInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testSisStudentInfo.getCurriculum()).isEqualTo(UPDATED_CURRICULUM);
        assertThat(testSisStudentInfo.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void deleteSisStudentInfo() throws Exception {
        // Initialize the database
        sisStudentInfoRepository.saveAndFlush(sisStudentInfo);

		int databaseSizeBeforeDelete = sisStudentInfoRepository.findAll().size();

        // Get the sisStudentInfo
        restSisStudentInfoMockMvc.perform(delete("/api/sisStudentInfos/{id}", sisStudentInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SisStudentInfo> sisStudentInfos = sisStudentInfoRepository.findAll();
        assertThat(sisStudentInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
