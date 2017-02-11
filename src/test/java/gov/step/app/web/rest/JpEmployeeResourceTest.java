package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JpEmployee;
import gov.step.app.repository.JpEmployeeRepository;
import gov.step.app.repository.search.JpEmployeeSearchRepository;

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

import gov.step.app.domain.enumeration.EmployeeGender;
import gov.step.app.domain.enumeration.maritialStatus;
import gov.step.app.domain.enumeration.Nationality;
import gov.step.app.domain.enumeration.EmployeeAvailability;

/**
 * Test class for the JpEmployeeResource REST controller.
 *
 * @see JpEmployeeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JpEmployeeResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_FATHER_NAME = "AAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBB";
    private static final String DEFAULT_MOTHER_NAME = "AAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBB";
    private static final String DEFAULT_PRESENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PRESENT_ADDRESS = "BBBBB";
    private static final String DEFAULT_PERMANENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PERMANENT_ADDRESS = "BBBBB";


private static final EmployeeGender DEFAULT_GENDER = EmployeeGender.Male;
    private static final EmployeeGender UPDATED_GENDER = EmployeeGender.Female;

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());


private static final maritialStatus DEFAULT_MARITIAL_STATUS = maritialStatus.Single;
    private static final maritialStatus UPDATED_MARITIAL_STATUS = maritialStatus.Married;
    private static final String DEFAULT_NID_NO = "AAAAA";
    private static final String UPDATED_NID_NO = "BBBBB";


private static final Nationality DEFAULT_NATIONALITY = Nationality.Bangladeshi;
    private static final Nationality UPDATED_NATIONALITY = Nationality.Other;
    private static final String DEFAULT_CURRENT_LOCATION = "AAAAA";
    private static final String UPDATED_CURRENT_LOCATION = "BBBBB";
    private static final String DEFAULT_MAILING_ADDRESS = "AAAAA";
    private static final String UPDATED_MAILING_ADDRESS = "BBBBB";
    private static final String DEFAULT_HOME_PHONE = "AAAAA";
    private static final String UPDATED_HOME_PHONE = "BBBBB";
    private static final String DEFAULT_MOBILE_NO = "AAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBB";
    private static final String DEFAULT_OFFICE_PHONE = "AAAAA";
    private static final String UPDATED_OFFICE_PHONE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_ALTERNATIVE_MAIL = "AAAAA";
    private static final String UPDATED_ALTERNATIVE_MAIL = "BBBBB";

    private static final byte[] DEFAULT_CV = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CV = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CV_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CV_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_OBJECTIVE = "AAAAA";
    private static final String UPDATED_OBJECTIVE = "BBBBB";

    private static final BigDecimal DEFAULT_PRESENT_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRESENT_SALARY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXPECTED_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_SALARY = new BigDecimal(2);


private static final EmployeeAvailability DEFAULT_AVAILIBILITY_TYPE = EmployeeAvailability.Full_Time;
    private static final EmployeeAvailability UPDATED_AVAILIBILITY_TYPE = EmployeeAvailability.Part_Time;

    @Inject
    private JpEmployeeRepository jpEmployeeRepository;

    @Inject
    private JpEmployeeSearchRepository jpEmployeeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJpEmployeeMockMvc;

    private JpEmployee jpEmployee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpEmployeeResource jpEmployeeResource = new JpEmployeeResource();
        ReflectionTestUtils.setField(jpEmployeeResource, "jpEmployeeRepository", jpEmployeeRepository);
        ReflectionTestUtils.setField(jpEmployeeResource, "jpEmployeeSearchRepository", jpEmployeeSearchRepository);
        this.restJpEmployeeMockMvc = MockMvcBuilders.standaloneSetup(jpEmployeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jpEmployee = new JpEmployee();
        jpEmployee.setName(DEFAULT_NAME);
        jpEmployee.setFatherName(DEFAULT_FATHER_NAME);
        jpEmployee.setMotherName(DEFAULT_MOTHER_NAME);
        jpEmployee.setPresentAddress(DEFAULT_PRESENT_ADDRESS);
        jpEmployee.setPermanentAddress(DEFAULT_PERMANENT_ADDRESS);
        jpEmployee.setGender(DEFAULT_GENDER);
        jpEmployee.setDob(DEFAULT_DOB);
        jpEmployee.setMaritialStatus(DEFAULT_MARITIAL_STATUS);
        jpEmployee.setNidNo(DEFAULT_NID_NO);
        //jpEmployee.setNationality(DEFAULT_NATIONALITY);
        jpEmployee.setCurrentLocation(DEFAULT_CURRENT_LOCATION);
        jpEmployee.setMailingAddress(DEFAULT_MAILING_ADDRESS);
        jpEmployee.setHomePhone(DEFAULT_HOME_PHONE);
        jpEmployee.setMobileNo(DEFAULT_MOBILE_NO);
        jpEmployee.setOfficePhone(DEFAULT_OFFICE_PHONE);
        jpEmployee.setEmail(DEFAULT_EMAIL);
        jpEmployee.setAlternativeMail(DEFAULT_ALTERNATIVE_MAIL);
        jpEmployee.setCv(DEFAULT_CV);
        jpEmployee.setCvContentType(DEFAULT_CV_CONTENT_TYPE);
        jpEmployee.setObjective(DEFAULT_OBJECTIVE);
        jpEmployee.setPresentSalary(DEFAULT_PRESENT_SALARY);
        jpEmployee.setExpectedSalary(DEFAULT_EXPECTED_SALARY);
        jpEmployee.setAvailibilityType(DEFAULT_AVAILIBILITY_TYPE);
    }

    @Test
    @Transactional
    public void createJpEmployee() throws Exception {
        int databaseSizeBeforeCreate = jpEmployeeRepository.findAll().size();

        // Create the JpEmployee

        restJpEmployeeMockMvc.perform(post("/api/jpEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployee)))
                .andExpect(status().isCreated());

        // Validate the JpEmployee in the database
        List<JpEmployee> jpEmployees = jpEmployeeRepository.findAll();
        assertThat(jpEmployees).hasSize(databaseSizeBeforeCreate + 1);
        JpEmployee testJpEmployee = jpEmployees.get(jpEmployees.size() - 1);
        assertThat(testJpEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJpEmployee.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testJpEmployee.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testJpEmployee.getPresentAddress()).isEqualTo(DEFAULT_PRESENT_ADDRESS);
        assertThat(testJpEmployee.getPermanentAddress()).isEqualTo(DEFAULT_PERMANENT_ADDRESS);
        assertThat(testJpEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testJpEmployee.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testJpEmployee.getMaritialStatus()).isEqualTo(DEFAULT_MARITIAL_STATUS);
        assertThat(testJpEmployee.getNidNo()).isEqualTo(DEFAULT_NID_NO);
        assertThat(testJpEmployee.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testJpEmployee.getCurrentLocation()).isEqualTo(DEFAULT_CURRENT_LOCATION);
        assertThat(testJpEmployee.getMailingAddress()).isEqualTo(DEFAULT_MAILING_ADDRESS);
        assertThat(testJpEmployee.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testJpEmployee.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testJpEmployee.getOfficePhone()).isEqualTo(DEFAULT_OFFICE_PHONE);
        assertThat(testJpEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testJpEmployee.getAlternativeMail()).isEqualTo(DEFAULT_ALTERNATIVE_MAIL);
        assertThat(testJpEmployee.getCv()).isEqualTo(DEFAULT_CV);
        assertThat(testJpEmployee.getCvContentType()).isEqualTo(DEFAULT_CV_CONTENT_TYPE);
        assertThat(testJpEmployee.getObjective()).isEqualTo(DEFAULT_OBJECTIVE);
        assertThat(testJpEmployee.getPresentSalary()).isEqualTo(DEFAULT_PRESENT_SALARY);
        assertThat(testJpEmployee.getExpectedSalary()).isEqualTo(DEFAULT_EXPECTED_SALARY);
        assertThat(testJpEmployee.getAvailibilityType()).isEqualTo(DEFAULT_AVAILIBILITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllJpEmployees() throws Exception {
        // Initialize the database
        jpEmployeeRepository.saveAndFlush(jpEmployee);

        // Get all the jpEmployees
        restJpEmployeeMockMvc.perform(get("/api/jpEmployees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jpEmployee.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
                .andExpect(jsonPath("$.[*].maritialStatus").value(hasItem(DEFAULT_MARITIAL_STATUS.toString())))
                .andExpect(jsonPath("$.[*].nidNo").value(hasItem(DEFAULT_NID_NO.toString())))
                .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
                .andExpect(jsonPath("$.[*].currentLocation").value(hasItem(DEFAULT_CURRENT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].mailingAddress").value(hasItem(DEFAULT_MAILING_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE.toString())))
                .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].officePhone").value(hasItem(DEFAULT_OFFICE_PHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].alternativeMail").value(hasItem(DEFAULT_ALTERNATIVE_MAIL.toString())))
                .andExpect(jsonPath("$.[*].cvContentType").value(hasItem(DEFAULT_CV_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].cv").value(hasItem(Base64Utils.encodeToString(DEFAULT_CV))))
                .andExpect(jsonPath("$.[*].objective").value(hasItem(DEFAULT_OBJECTIVE.toString())))
                .andExpect(jsonPath("$.[*].presentSalary").value(hasItem(DEFAULT_PRESENT_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].expectedSalary").value(hasItem(DEFAULT_EXPECTED_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].availibilityType").value(hasItem(DEFAULT_AVAILIBILITY_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getJpEmployee() throws Exception {
        // Initialize the database
        jpEmployeeRepository.saveAndFlush(jpEmployee);

        // Get the jpEmployee
        restJpEmployeeMockMvc.perform(get("/api/jpEmployees/{id}", jpEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jpEmployee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME.toString()))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME.toString()))
            .andExpect(jsonPath("$.presentAddress").value(DEFAULT_PRESENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.permanentAddress").value(DEFAULT_PERMANENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.maritialStatus").value(DEFAULT_MARITIAL_STATUS.toString()))
            .andExpect(jsonPath("$.nidNo").value(DEFAULT_NID_NO.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.currentLocation").value(DEFAULT_CURRENT_LOCATION.toString()))
            .andExpect(jsonPath("$.mailingAddress").value(DEFAULT_MAILING_ADDRESS.toString()))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.officePhone").value(DEFAULT_OFFICE_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.alternativeMail").value(DEFAULT_ALTERNATIVE_MAIL.toString()))
            .andExpect(jsonPath("$.cvContentType").value(DEFAULT_CV_CONTENT_TYPE))
            .andExpect(jsonPath("$.cv").value(Base64Utils.encodeToString(DEFAULT_CV)))
            .andExpect(jsonPath("$.objective").value(DEFAULT_OBJECTIVE.toString()))
            .andExpect(jsonPath("$.presentSalary").value(DEFAULT_PRESENT_SALARY.intValue()))
            .andExpect(jsonPath("$.expectedSalary").value(DEFAULT_EXPECTED_SALARY.intValue()))
            .andExpect(jsonPath("$.availibilityType").value(DEFAULT_AVAILIBILITY_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJpEmployee() throws Exception {
        // Get the jpEmployee
        restJpEmployeeMockMvc.perform(get("/api/jpEmployees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpEmployee() throws Exception {
        // Initialize the database
        jpEmployeeRepository.saveAndFlush(jpEmployee);

		int databaseSizeBeforeUpdate = jpEmployeeRepository.findAll().size();

        // Update the jpEmployee
        jpEmployee.setName(UPDATED_NAME);
        jpEmployee.setFatherName(UPDATED_FATHER_NAME);
        jpEmployee.setMotherName(UPDATED_MOTHER_NAME);
        jpEmployee.setPresentAddress(UPDATED_PRESENT_ADDRESS);
        jpEmployee.setPermanentAddress(UPDATED_PERMANENT_ADDRESS);
        jpEmployee.setGender(UPDATED_GENDER);
        jpEmployee.setDob(UPDATED_DOB);
        jpEmployee.setMaritialStatus(UPDATED_MARITIAL_STATUS);
        jpEmployee.setNidNo(UPDATED_NID_NO);
        //jpEmployee.setNationality(UPDATED_NATIONALITY);
        jpEmployee.setCurrentLocation(UPDATED_CURRENT_LOCATION);
        jpEmployee.setMailingAddress(UPDATED_MAILING_ADDRESS);
        jpEmployee.setHomePhone(UPDATED_HOME_PHONE);
        jpEmployee.setMobileNo(UPDATED_MOBILE_NO);
        jpEmployee.setOfficePhone(UPDATED_OFFICE_PHONE);
        jpEmployee.setEmail(UPDATED_EMAIL);
        jpEmployee.setAlternativeMail(UPDATED_ALTERNATIVE_MAIL);
        jpEmployee.setCv(UPDATED_CV);
        jpEmployee.setCvContentType(UPDATED_CV_CONTENT_TYPE);
        jpEmployee.setObjective(UPDATED_OBJECTIVE);
        jpEmployee.setPresentSalary(UPDATED_PRESENT_SALARY);
        jpEmployee.setExpectedSalary(UPDATED_EXPECTED_SALARY);
        jpEmployee.setAvailibilityType(UPDATED_AVAILIBILITY_TYPE);

        restJpEmployeeMockMvc.perform(put("/api/jpEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployee)))
                .andExpect(status().isOk());

        // Validate the JpEmployee in the database
        List<JpEmployee> jpEmployees = jpEmployeeRepository.findAll();
        assertThat(jpEmployees).hasSize(databaseSizeBeforeUpdate);
        JpEmployee testJpEmployee = jpEmployees.get(jpEmployees.size() - 1);
        assertThat(testJpEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJpEmployee.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testJpEmployee.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testJpEmployee.getPresentAddress()).isEqualTo(UPDATED_PRESENT_ADDRESS);
        assertThat(testJpEmployee.getPermanentAddress()).isEqualTo(UPDATED_PERMANENT_ADDRESS);
        assertThat(testJpEmployee.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testJpEmployee.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testJpEmployee.getMaritialStatus()).isEqualTo(UPDATED_MARITIAL_STATUS);
        assertThat(testJpEmployee.getNidNo()).isEqualTo(UPDATED_NID_NO);
        assertThat(testJpEmployee.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testJpEmployee.getCurrentLocation()).isEqualTo(UPDATED_CURRENT_LOCATION);
        assertThat(testJpEmployee.getMailingAddress()).isEqualTo(UPDATED_MAILING_ADDRESS);
        assertThat(testJpEmployee.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testJpEmployee.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testJpEmployee.getOfficePhone()).isEqualTo(UPDATED_OFFICE_PHONE);
        assertThat(testJpEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testJpEmployee.getAlternativeMail()).isEqualTo(UPDATED_ALTERNATIVE_MAIL);
        assertThat(testJpEmployee.getCv()).isEqualTo(UPDATED_CV);
        assertThat(testJpEmployee.getCvContentType()).isEqualTo(UPDATED_CV_CONTENT_TYPE);
        assertThat(testJpEmployee.getObjective()).isEqualTo(UPDATED_OBJECTIVE);
        assertThat(testJpEmployee.getPresentSalary()).isEqualTo(UPDATED_PRESENT_SALARY);
        assertThat(testJpEmployee.getExpectedSalary()).isEqualTo(UPDATED_EXPECTED_SALARY);
        assertThat(testJpEmployee.getAvailibilityType()).isEqualTo(UPDATED_AVAILIBILITY_TYPE);
    }

    @Test
    @Transactional
    public void deleteJpEmployee() throws Exception {
        // Initialize the database
        jpEmployeeRepository.saveAndFlush(jpEmployee);

		int databaseSizeBeforeDelete = jpEmployeeRepository.findAll().size();

        // Get the jpEmployee
        restJpEmployeeMockMvc.perform(delete("/api/jpEmployees/{id}", jpEmployee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JpEmployee> jpEmployees = jpEmployeeRepository.findAll();
        assertThat(jpEmployees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
