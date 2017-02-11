package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SisStudentReg;
import gov.step.app.repository.SisStudentRegRepository;
import gov.step.app.repository.search.SisStudentRegSearchRepository;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SisStudentRegResource REST controller.
 *
 * @see SisStudentRegResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SisStudentRegResourceTest {


    private static final Long DEFAULT_APPLICATION_ID = 1L;
    private static final Long UPDATED_APPLICATION_ID = 2L;
    private static final String DEFAULT_INST_CATEGORY = "AAAAA";
    private static final String UPDATED_INST_CATEGORY = "BBBBB";
    private static final String DEFAULT_INSTITUTE_NAME = "AAAAA";
    private static final String UPDATED_INSTITUTE_NAME = "BBBBB";
    private static final String DEFAULT_CURRICULUM = "AAAAA";
    private static final String UPDATED_CURRICULUM = "BBBBB";
    private static final String DEFAULT_TRADE_TECHNOLOGY = "AAAAA";
    private static final String UPDATED_TRADE_TECHNOLOGY = "BBBBB";
    private static final String DEFAULT_SUBJECT1 = "AAAAA";
    private static final String UPDATED_SUBJECT1 = "BBBBB";
    private static final String DEFAULT_SUBJECT2 = "AAAAA";
    private static final String UPDATED_SUBJECT2 = "BBBBB";
    private static final String DEFAULT_SUBJECT3 = "AAAAA";
    private static final String UPDATED_SUBJECT3 = "BBBBB";
    private static final String DEFAULT_SUBJECT4 = "AAAAA";
    private static final String UPDATED_SUBJECT4 = "BBBBB";
    private static final String DEFAULT_SUBJECT5 = "AAAAA";
    private static final String UPDATED_SUBJECT5 = "BBBBB";
    private static final String DEFAULT_OPTIONAL = "AAAAA";
    private static final String UPDATED_OPTIONAL = "BBBBB";
    private static final String DEFAULT_SHIFT = "AAAAA";
    private static final String UPDATED_SHIFT = "BBBBB";
    private static final String DEFAULT_SEMESTER = "AAAAA";
    private static final String UPDATED_SEMESTER = "BBBBB";
    private static final String DEFAULT_STUDENT_NAME = "AAAAA";
    private static final String UPDATED_STUDENT_NAME = "BBBBB";
    private static final String DEFAULT_FATHER_NAME = "AAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBB";
    private static final String DEFAULT_MOTHER_NAME = "AAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBB";
    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBB";
    private static final String DEFAULT_GENDER = "AAAAA";
    private static final String UPDATED_GENDER = "BBBBB";
    private static final String DEFAULT_RELIGION = "AAAAA";
    private static final String UPDATED_RELIGION = "BBBBB";
    private static final String DEFAULT_BLOOD_GROUP = "AAAAA";
    private static final String UPDATED_BLOOD_GROUP = "BBBBB";
    private static final String DEFAULT_QUOTA = "AAAAA";
    private static final String UPDATED_QUOTA = "BBBBB";
    private static final String DEFAULT_NATIONALITY = "AAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBB";
    private static final String DEFAULT_MOBILE_NO = "AAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBB";
    private static final String DEFAULT_CONTACT_NO_HOME = "AAAAA";
    private static final String UPDATED_CONTACT_NO_HOME = "BBBBB";
    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBB";
    private static final String DEFAULT_PRESENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PRESENT_ADDRESS = "BBBBB";
    private static final String DEFAULT_PERMANENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PERMANENT_ADDRESS = "BBBBB";

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
    private static final String DEFAULT_MARITAL_STATUS = "AAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBB";

    @Inject
    private SisStudentRegRepository sisStudentRegRepository;

    @Inject
    private SisStudentRegSearchRepository sisStudentRegSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSisStudentRegMockMvc;

    private SisStudentReg sisStudentReg;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SisStudentRegResource sisStudentRegResource = new SisStudentRegResource();
        ReflectionTestUtils.setField(sisStudentRegResource, "sisStudentRegRepository", sisStudentRegRepository);
        ReflectionTestUtils.setField(sisStudentRegResource, "sisStudentRegSearchRepository", sisStudentRegSearchRepository);
        this.restSisStudentRegMockMvc = MockMvcBuilders.standaloneSetup(sisStudentRegResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        sisStudentReg = new SisStudentReg();
        sisStudentReg.setApplicationId(DEFAULT_APPLICATION_ID);
        sisStudentReg.setInstCategory(DEFAULT_INST_CATEGORY);
        sisStudentReg.setInstituteName(DEFAULT_INSTITUTE_NAME);
        sisStudentReg.setCurriculum(DEFAULT_CURRICULUM);
        sisStudentReg.setTradeTechnology(DEFAULT_TRADE_TECHNOLOGY);
        sisStudentReg.setSubject1(DEFAULT_SUBJECT1);
        sisStudentReg.setSubject2(DEFAULT_SUBJECT2);
        sisStudentReg.setSubject3(DEFAULT_SUBJECT3);
        sisStudentReg.setSubject4(DEFAULT_SUBJECT4);
        sisStudentReg.setSubject5(DEFAULT_SUBJECT5);
        sisStudentReg.setOptional(DEFAULT_OPTIONAL);
        sisStudentReg.setShift(DEFAULT_SHIFT);
        sisStudentReg.setSemester(DEFAULT_SEMESTER);
        sisStudentReg.setStudentName(DEFAULT_STUDENT_NAME);
        sisStudentReg.setFatherName(DEFAULT_FATHER_NAME);
        sisStudentReg.setMotherName(DEFAULT_MOTHER_NAME);
        sisStudentReg.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
        sisStudentReg.setGender(DEFAULT_GENDER);
        sisStudentReg.setReligion(DEFAULT_RELIGION);
        sisStudentReg.setBloodGroup(DEFAULT_BLOOD_GROUP);
        sisStudentReg.setQuota(DEFAULT_QUOTA);
        sisStudentReg.setNationality(DEFAULT_NATIONALITY);
        sisStudentReg.setMobileNo(DEFAULT_MOBILE_NO);
        sisStudentReg.setContactNoHome(DEFAULT_CONTACT_NO_HOME);
        sisStudentReg.setEmailAddress(DEFAULT_EMAIL_ADDRESS);
        sisStudentReg.setPresentAddress(DEFAULT_PRESENT_ADDRESS);
        sisStudentReg.setPermanentAddress(DEFAULT_PERMANENT_ADDRESS);
        sisStudentReg.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        sisStudentReg.setCreateDate(DEFAULT_CREATE_DATE);
        sisStudentReg.setCreateBy(DEFAULT_CREATE_BY);
        sisStudentReg.setUpdateDate(DEFAULT_UPDATE_DATE);
        sisStudentReg.setUpdateBy(DEFAULT_UPDATE_BY);
        sisStudentReg.setMaritalStatus(DEFAULT_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void createSisStudentReg() throws Exception {
        int databaseSizeBeforeCreate = sisStudentRegRepository.findAll().size();

        // Create the SisStudentReg

        restSisStudentRegMockMvc.perform(post("/api/sisStudentRegs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisStudentReg)))
                .andExpect(status().isCreated());

        // Validate the SisStudentReg in the database
        List<SisStudentReg> sisStudentRegs = sisStudentRegRepository.findAll();
        assertThat(sisStudentRegs).hasSize(databaseSizeBeforeCreate + 1);
        SisStudentReg testSisStudentReg = sisStudentRegs.get(sisStudentRegs.size() - 1);
        assertThat(testSisStudentReg.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testSisStudentReg.getInstCategory()).isEqualTo(DEFAULT_INST_CATEGORY);
        assertThat(testSisStudentReg.getInstituteName()).isEqualTo(DEFAULT_INSTITUTE_NAME);
        assertThat(testSisStudentReg.getCurriculum()).isEqualTo(DEFAULT_CURRICULUM);
        assertThat(testSisStudentReg.getTradeTechnology()).isEqualTo(DEFAULT_TRADE_TECHNOLOGY);
        assertThat(testSisStudentReg.getSubject1()).isEqualTo(DEFAULT_SUBJECT1);
        assertThat(testSisStudentReg.getSubject2()).isEqualTo(DEFAULT_SUBJECT2);
        assertThat(testSisStudentReg.getSubject3()).isEqualTo(DEFAULT_SUBJECT3);
        assertThat(testSisStudentReg.getSubject4()).isEqualTo(DEFAULT_SUBJECT4);
        assertThat(testSisStudentReg.getSubject5()).isEqualTo(DEFAULT_SUBJECT5);
        assertThat(testSisStudentReg.getOptional()).isEqualTo(DEFAULT_OPTIONAL);
        assertThat(testSisStudentReg.getShift()).isEqualTo(DEFAULT_SHIFT);
        assertThat(testSisStudentReg.getSemester()).isEqualTo(DEFAULT_SEMESTER);
        assertThat(testSisStudentReg.getStudentName()).isEqualTo(DEFAULT_STUDENT_NAME);
        assertThat(testSisStudentReg.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testSisStudentReg.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testSisStudentReg.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testSisStudentReg.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testSisStudentReg.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testSisStudentReg.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testSisStudentReg.getQuota()).isEqualTo(DEFAULT_QUOTA);
        assertThat(testSisStudentReg.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testSisStudentReg.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testSisStudentReg.getContactNoHome()).isEqualTo(DEFAULT_CONTACT_NO_HOME);
        assertThat(testSisStudentReg.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testSisStudentReg.getPresentAddress()).isEqualTo(DEFAULT_PRESENT_ADDRESS);
        assertThat(testSisStudentReg.getPermanentAddress()).isEqualTo(DEFAULT_PERMANENT_ADDRESS);
        assertThat(testSisStudentReg.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testSisStudentReg.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testSisStudentReg.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testSisStudentReg.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testSisStudentReg.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testSisStudentReg.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllSisStudentRegs() throws Exception {
        // Initialize the database
        sisStudentRegRepository.saveAndFlush(sisStudentReg);

        // Get all the sisStudentRegs
        restSisStudentRegMockMvc.perform(get("/api/sisStudentRegs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sisStudentReg.getId().intValue())))
                .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
                .andExpect(jsonPath("$.[*].instCategory").value(hasItem(DEFAULT_INST_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].instituteName").value(hasItem(DEFAULT_INSTITUTE_NAME.toString())))
                .andExpect(jsonPath("$.[*].curriculum").value(hasItem(DEFAULT_CURRICULUM.toString())))
                .andExpect(jsonPath("$.[*].TradeTechnology").value(hasItem(DEFAULT_TRADE_TECHNOLOGY.toString())))
                .andExpect(jsonPath("$.[*].subject1").value(hasItem(DEFAULT_SUBJECT1.toString())))
                .andExpect(jsonPath("$.[*].subject2").value(hasItem(DEFAULT_SUBJECT2.toString())))
                .andExpect(jsonPath("$.[*].subject3").value(hasItem(DEFAULT_SUBJECT3.toString())))
                .andExpect(jsonPath("$.[*].subject4").value(hasItem(DEFAULT_SUBJECT4.toString())))
                .andExpect(jsonPath("$.[*].subject5").value(hasItem(DEFAULT_SUBJECT5.toString())))
                .andExpect(jsonPath("$.[*].optional").value(hasItem(DEFAULT_OPTIONAL.toString())))
                .andExpect(jsonPath("$.[*].shift").value(hasItem(DEFAULT_SHIFT.toString())))
                .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())))
                .andExpect(jsonPath("$.[*].studentName").value(hasItem(DEFAULT_STUDENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
                .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
                .andExpect(jsonPath("$.[*].quota").value(hasItem(DEFAULT_QUOTA.toString())))
                .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
                .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].contactNoHome").value(hasItem(DEFAULT_CONTACT_NO_HOME.toString())))
                .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getSisStudentReg() throws Exception {
        // Initialize the database
        sisStudentRegRepository.saveAndFlush(sisStudentReg);

        // Get the sisStudentReg
        restSisStudentRegMockMvc.perform(get("/api/sisStudentRegs/{id}", sisStudentReg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sisStudentReg.getId().intValue()))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.instCategory").value(DEFAULT_INST_CATEGORY.toString()))
            .andExpect(jsonPath("$.instituteName").value(DEFAULT_INSTITUTE_NAME.toString()))
            .andExpect(jsonPath("$.curriculum").value(DEFAULT_CURRICULUM.toString()))
            .andExpect(jsonPath("$.TradeTechnology").value(DEFAULT_TRADE_TECHNOLOGY.toString()))
            .andExpect(jsonPath("$.subject1").value(DEFAULT_SUBJECT1.toString()))
            .andExpect(jsonPath("$.subject2").value(DEFAULT_SUBJECT2.toString()))
            .andExpect(jsonPath("$.subject3").value(DEFAULT_SUBJECT3.toString()))
            .andExpect(jsonPath("$.subject4").value(DEFAULT_SUBJECT4.toString()))
            .andExpect(jsonPath("$.subject5").value(DEFAULT_SUBJECT5.toString()))
            .andExpect(jsonPath("$.optional").value(DEFAULT_OPTIONAL.toString()))
            .andExpect(jsonPath("$.shift").value(DEFAULT_SHIFT.toString()))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()))
            .andExpect(jsonPath("$.studentName").value(DEFAULT_STUDENT_NAME.toString()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME.toString()))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.quota").value(DEFAULT_QUOTA.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.contactNoHome").value(DEFAULT_CONTACT_NO_HOME.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.presentAddress").value(DEFAULT_PRESENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.permanentAddress").value(DEFAULT_PERMANENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSisStudentReg() throws Exception {
        // Get the sisStudentReg
        restSisStudentRegMockMvc.perform(get("/api/sisStudentRegs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSisStudentReg() throws Exception {
        // Initialize the database
        sisStudentRegRepository.saveAndFlush(sisStudentReg);

		int databaseSizeBeforeUpdate = sisStudentRegRepository.findAll().size();

        // Update the sisStudentReg
        sisStudentReg.setApplicationId(UPDATED_APPLICATION_ID);
        sisStudentReg.setInstCategory(UPDATED_INST_CATEGORY);
        sisStudentReg.setInstituteName(UPDATED_INSTITUTE_NAME);
        sisStudentReg.setCurriculum(UPDATED_CURRICULUM);
        sisStudentReg.setTradeTechnology(UPDATED_TRADE_TECHNOLOGY);
        sisStudentReg.setSubject1(UPDATED_SUBJECT1);
        sisStudentReg.setSubject2(UPDATED_SUBJECT2);
        sisStudentReg.setSubject3(UPDATED_SUBJECT3);
        sisStudentReg.setSubject4(UPDATED_SUBJECT4);
        sisStudentReg.setSubject5(UPDATED_SUBJECT5);
        sisStudentReg.setOptional(UPDATED_OPTIONAL);
        sisStudentReg.setShift(UPDATED_SHIFT);
        sisStudentReg.setSemester(UPDATED_SEMESTER);
        sisStudentReg.setStudentName(UPDATED_STUDENT_NAME);
        sisStudentReg.setFatherName(UPDATED_FATHER_NAME);
        sisStudentReg.setMotherName(UPDATED_MOTHER_NAME);
        sisStudentReg.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        sisStudentReg.setGender(UPDATED_GENDER);
        sisStudentReg.setReligion(UPDATED_RELIGION);
        sisStudentReg.setBloodGroup(UPDATED_BLOOD_GROUP);
        sisStudentReg.setQuota(UPDATED_QUOTA);
        sisStudentReg.setNationality(UPDATED_NATIONALITY);
        sisStudentReg.setMobileNo(UPDATED_MOBILE_NO);
        sisStudentReg.setContactNoHome(UPDATED_CONTACT_NO_HOME);
        sisStudentReg.setEmailAddress(UPDATED_EMAIL_ADDRESS);
        sisStudentReg.setPresentAddress(UPDATED_PRESENT_ADDRESS);
        sisStudentReg.setPermanentAddress(UPDATED_PERMANENT_ADDRESS);
        sisStudentReg.setActiveStatus(UPDATED_ACTIVE_STATUS);
        sisStudentReg.setCreateDate(UPDATED_CREATE_DATE);
        sisStudentReg.setCreateBy(UPDATED_CREATE_BY);
        sisStudentReg.setUpdateDate(UPDATED_UPDATE_DATE);
        sisStudentReg.setUpdateBy(UPDATED_UPDATE_BY);
        sisStudentReg.setMaritalStatus(UPDATED_MARITAL_STATUS);

        restSisStudentRegMockMvc.perform(put("/api/sisStudentRegs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisStudentReg)))
                .andExpect(status().isOk());

        // Validate the SisStudentReg in the database
        List<SisStudentReg> sisStudentRegs = sisStudentRegRepository.findAll();
        assertThat(sisStudentRegs).hasSize(databaseSizeBeforeUpdate);
        SisStudentReg testSisStudentReg = sisStudentRegs.get(sisStudentRegs.size() - 1);
        assertThat(testSisStudentReg.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testSisStudentReg.getInstCategory()).isEqualTo(UPDATED_INST_CATEGORY);
        assertThat(testSisStudentReg.getInstituteName()).isEqualTo(UPDATED_INSTITUTE_NAME);
        assertThat(testSisStudentReg.getCurriculum()).isEqualTo(UPDATED_CURRICULUM);
        assertThat(testSisStudentReg.getTradeTechnology()).isEqualTo(UPDATED_TRADE_TECHNOLOGY);
        assertThat(testSisStudentReg.getSubject1()).isEqualTo(UPDATED_SUBJECT1);
        assertThat(testSisStudentReg.getSubject2()).isEqualTo(UPDATED_SUBJECT2);
        assertThat(testSisStudentReg.getSubject3()).isEqualTo(UPDATED_SUBJECT3);
        assertThat(testSisStudentReg.getSubject4()).isEqualTo(UPDATED_SUBJECT4);
        assertThat(testSisStudentReg.getSubject5()).isEqualTo(UPDATED_SUBJECT5);
        assertThat(testSisStudentReg.getOptional()).isEqualTo(UPDATED_OPTIONAL);
        assertThat(testSisStudentReg.getShift()).isEqualTo(UPDATED_SHIFT);
        assertThat(testSisStudentReg.getSemester()).isEqualTo(UPDATED_SEMESTER);
        assertThat(testSisStudentReg.getStudentName()).isEqualTo(UPDATED_STUDENT_NAME);
        assertThat(testSisStudentReg.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testSisStudentReg.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testSisStudentReg.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testSisStudentReg.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testSisStudentReg.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testSisStudentReg.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testSisStudentReg.getQuota()).isEqualTo(UPDATED_QUOTA);
        assertThat(testSisStudentReg.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testSisStudentReg.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testSisStudentReg.getContactNoHome()).isEqualTo(UPDATED_CONTACT_NO_HOME);
        assertThat(testSisStudentReg.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testSisStudentReg.getPresentAddress()).isEqualTo(UPDATED_PRESENT_ADDRESS);
        assertThat(testSisStudentReg.getPermanentAddress()).isEqualTo(UPDATED_PERMANENT_ADDRESS);
        assertThat(testSisStudentReg.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testSisStudentReg.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testSisStudentReg.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testSisStudentReg.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testSisStudentReg.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testSisStudentReg.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void deleteSisStudentReg() throws Exception {
        // Initialize the database
        sisStudentRegRepository.saveAndFlush(sisStudentReg);

		int databaseSizeBeforeDelete = sisStudentRegRepository.findAll().size();

        // Get the sisStudentReg
        restSisStudentRegMockMvc.perform(delete("/api/sisStudentRegs/{id}", sisStudentReg.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SisStudentReg> sisStudentRegs = sisStudentRegRepository.findAll();
        assertThat(sisStudentRegs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
