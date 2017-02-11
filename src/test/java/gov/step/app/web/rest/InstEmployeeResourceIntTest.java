package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmployee;
import gov.step.app.repository.InstEmployeeRepository;
import gov.step.app.repository.search.InstEmployeeSearchRepository;

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

import gov.step.app.domain.enumeration.EmpType;
import gov.step.app.domain.enumeration.maritalStatus;
import gov.step.app.domain.enumeration.bloodGroup;

/**
 * Test class for the InstEmployeeResource REST controller.
 *
 * @see InstEmployeeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmployeeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_CONTACT_NO = "AAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBB";
    private static final String DEFAULT_FATHER_NAME = "AAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBB";
    private static final String DEFAULT_MOTHER_NAME = "AAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());


private static final EmpType DEFAULT_CATEGORY = EmpType.Teacher;
    private static final EmpType UPDATED_CATEGORY = EmpType.Employee;
    private static final String DEFAULT_GENDER = "AAAAA";
    private static final String UPDATED_GENDER = "BBBBB";


private static final maritalStatus DEFAULT_MARITAL_STATUS = maritalStatus.Single;
    private static final maritalStatus UPDATED_MARITAL_STATUS = maritalStatus.Married;


private static final bloodGroup DEFAULT_BLOOD_GROUP = bloodGroup.A_POSITIVE;
    private static final bloodGroup UPDATED_BLOOD_GROUP = bloodGroup.A_NEGATIVE;
    private static final String DEFAULT_TIN = "AAAAA";
    private static final String UPDATED_TIN = "BBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_NATIONALITY = "AAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBB";
    private static final String DEFAULT_NID = "AAAAA";
    private static final String UPDATED_NID = "BBBBB";

    private static final byte[] DEFAULT_NID_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NID_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_NID_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NID_IMAGE_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_BIRTH_CERT_NO = "AAAAA";
    private static final String UPDATED_BIRTH_CERT_NO = "BBBBB";

    private static final byte[] DEFAULT_BIRTH_CERT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BIRTH_CERT_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BIRTH_CERT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BIRTH_CERT_IMAGE_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_CON_PER_NAME = "AAAAA";
    private static final String UPDATED_CON_PER_NAME = "BBBBB";

    private static final Integer DEFAULT_CON_PER_MOBILE = 1;
    private static final Integer UPDATED_CON_PER_MOBILE = 2;
    private static final String DEFAULT_CON_PER_ADDRESS = "AAAAA";
    private static final String UPDATED_CON_PER_ADDRESS = "BBBBB";

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private InstEmployeeSearchRepository instEmployeeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmployeeMockMvc;

    private InstEmployee instEmployee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmployeeResource instEmployeeResource = new InstEmployeeResource();
        ReflectionTestUtils.setField(instEmployeeResource, "instEmployeeRepository", instEmployeeRepository);
        ReflectionTestUtils.setField(instEmployeeResource, "instEmployeeSearchRepository", instEmployeeSearchRepository);
        this.restInstEmployeeMockMvc = MockMvcBuilders.standaloneSetup(instEmployeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmployee = new InstEmployee();
        instEmployee.setName(DEFAULT_NAME);
        instEmployee.setEmail(DEFAULT_EMAIL);
        instEmployee.setContactNo(DEFAULT_CONTACT_NO);
        instEmployee.setFatherName(DEFAULT_FATHER_NAME);
        instEmployee.setMotherName(DEFAULT_MOTHER_NAME);
        instEmployee.setDob(DEFAULT_DOB);
        instEmployee.setCategory(DEFAULT_CATEGORY);
        instEmployee.setGender(DEFAULT_GENDER);
        instEmployee.setMaritalStatus(DEFAULT_MARITAL_STATUS);
        instEmployee.setBloodGroup(DEFAULT_BLOOD_GROUP);
        instEmployee.setTin(DEFAULT_TIN);
        instEmployee.setImage(DEFAULT_IMAGE);
        instEmployee.setImageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        instEmployee.setNationality(DEFAULT_NATIONALITY);
        instEmployee.setNid(DEFAULT_NID);
        instEmployee.setNidImage(DEFAULT_NID_IMAGE);
        instEmployee.setNidImageContentType(DEFAULT_NID_IMAGE_CONTENT_TYPE);
        instEmployee.setBirthCertNo(DEFAULT_BIRTH_CERT_NO);
        instEmployee.setBirthCertImage(DEFAULT_BIRTH_CERT_IMAGE);
        instEmployee.setBirthCertImageContentType(DEFAULT_BIRTH_CERT_IMAGE_CONTENT_TYPE);
        instEmployee.setConPerName(DEFAULT_CON_PER_NAME);
        instEmployee.setConPerAddress(DEFAULT_CON_PER_ADDRESS);
    }

    @Test
    @Transactional
    public void createInstEmployee() throws Exception {
        int databaseSizeBeforeCreate = instEmployeeRepository.findAll().size();

        // Create the InstEmployee

        restInstEmployeeMockMvc.perform(post("/api/instEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmployee)))
                .andExpect(status().isCreated());

        // Validate the InstEmployee in the database
        List<InstEmployee> instEmployees = instEmployeeRepository.findAll();
        assertThat(instEmployees).hasSize(databaseSizeBeforeCreate + 1);
        InstEmployee testInstEmployee = instEmployees.get(instEmployees.size() - 1);
        assertThat(testInstEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInstEmployee.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testInstEmployee.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testInstEmployee.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testInstEmployee.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testInstEmployee.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testInstEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testInstEmployee.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testInstEmployee.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testInstEmployee.getTin()).isEqualTo(DEFAULT_TIN);
        assertThat(testInstEmployee.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testInstEmployee.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testInstEmployee.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testInstEmployee.getNid()).isEqualTo(DEFAULT_NID);
        assertThat(testInstEmployee.getNidImage()).isEqualTo(DEFAULT_NID_IMAGE);
        assertThat(testInstEmployee.getNidImageContentType()).isEqualTo(DEFAULT_NID_IMAGE_CONTENT_TYPE);
        assertThat(testInstEmployee.getBirthCertNo()).isEqualTo(DEFAULT_BIRTH_CERT_NO);
        assertThat(testInstEmployee.getBirthCertImage()).isEqualTo(DEFAULT_BIRTH_CERT_IMAGE);
        assertThat(testInstEmployee.getBirthCertImageContentType()).isEqualTo(DEFAULT_BIRTH_CERT_IMAGE_CONTENT_TYPE);
        assertThat(testInstEmployee.getConPerName()).isEqualTo(DEFAULT_CON_PER_NAME);
        assertThat(testInstEmployee.getConPerMobile()).isEqualTo(DEFAULT_CON_PER_MOBILE);
        assertThat(testInstEmployee.getConPerAddress()).isEqualTo(DEFAULT_CON_PER_ADDRESS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmployeeRepository.findAll().size();
        // set the field null
        instEmployee.setName(null);

        // Create the InstEmployee, which fails.

        restInstEmployeeMockMvc.perform(post("/api/instEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmployee)))
                .andExpect(status().isBadRequest());

        List<InstEmployee> instEmployees = instEmployeeRepository.findAll();
        assertThat(instEmployees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmployeeRepository.findAll().size();
        // set the field null
        instEmployee.setEmail(null);

        // Create the InstEmployee, which fails.

        restInstEmployeeMockMvc.perform(post("/api/instEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmployee)))
                .andExpect(status().isBadRequest());

        List<InstEmployee> instEmployees = instEmployeeRepository.findAll();
        assertThat(instEmployees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFatherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmployeeRepository.findAll().size();
        // set the field null
        instEmployee.setFatherName(null);

        // Create the InstEmployee, which fails.

        restInstEmployeeMockMvc.perform(post("/api/instEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmployee)))
                .andExpect(status().isBadRequest());

        List<InstEmployee> instEmployees = instEmployeeRepository.findAll();
        assertThat(instEmployees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmployeeRepository.findAll().size();
        // set the field null
        instEmployee.setMotherName(null);

        // Create the InstEmployee, which fails.

        restInstEmployeeMockMvc.perform(post("/api/instEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmployee)))
                .andExpect(status().isBadRequest());

        List<InstEmployee> instEmployees = instEmployeeRepository.findAll();
        assertThat(instEmployees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmployeeRepository.findAll().size();
        // set the field null
        instEmployee.setDob(null);

        // Create the InstEmployee, which fails.

        restInstEmployeeMockMvc.perform(post("/api/instEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmployee)))
                .andExpect(status().isBadRequest());

        List<InstEmployee> instEmployees = instEmployeeRepository.findAll();
        assertThat(instEmployees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmployeeRepository.findAll().size();
        // set the field null
        instEmployee.setImage(null);

        // Create the InstEmployee, which fails.

        restInstEmployeeMockMvc.perform(post("/api/instEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmployee)))
                .andExpect(status().isBadRequest());

        List<InstEmployee> instEmployees = instEmployeeRepository.findAll();
        assertThat(instEmployees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstEmployees() throws Exception {
        // Initialize the database
        instEmployeeRepository.saveAndFlush(instEmployee);

        // Get all the instEmployees
        restInstEmployeeMockMvc.perform(get("/api/instEmployees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmployee.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.toString())))
                .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
                .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
                .andExpect(jsonPath("$.[*].tin").value(hasItem(DEFAULT_TIN.toString())))
                .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
                .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
                .andExpect(jsonPath("$.[*].nid").value(hasItem(DEFAULT_NID.toString())))
                .andExpect(jsonPath("$.[*].nidImageContentType").value(hasItem(DEFAULT_NID_IMAGE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].nidImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_NID_IMAGE))))
                .andExpect(jsonPath("$.[*].birthCertNo").value(hasItem(DEFAULT_BIRTH_CERT_NO.toString())))
                .andExpect(jsonPath("$.[*].birthCertImageContentType").value(hasItem(DEFAULT_BIRTH_CERT_IMAGE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].birthCertImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_BIRTH_CERT_IMAGE))))
                .andExpect(jsonPath("$.[*].conPerName").value(hasItem(DEFAULT_CON_PER_NAME.toString())))
                .andExpect(jsonPath("$.[*].conPerMobile").value(hasItem(DEFAULT_CON_PER_MOBILE)))
                .andExpect(jsonPath("$.[*].conPerAddress").value(hasItem(DEFAULT_CON_PER_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getInstEmployee() throws Exception {
        // Initialize the database
        instEmployeeRepository.saveAndFlush(instEmployee);

        // Get the instEmployee
        restInstEmployeeMockMvc.perform(get("/api/instEmployees/{id}", instEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmployee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO.toString()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME.toString()))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.tin").value(DEFAULT_TIN.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.nid").value(DEFAULT_NID.toString()))
            .andExpect(jsonPath("$.nidImageContentType").value(DEFAULT_NID_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.nidImage").value(Base64Utils.encodeToString(DEFAULT_NID_IMAGE)))
            .andExpect(jsonPath("$.birthCertNo").value(DEFAULT_BIRTH_CERT_NO.toString()))
            .andExpect(jsonPath("$.birthCertImageContentType").value(DEFAULT_BIRTH_CERT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.birthCertImage").value(Base64Utils.encodeToString(DEFAULT_BIRTH_CERT_IMAGE)))
            .andExpect(jsonPath("$.conPerName").value(DEFAULT_CON_PER_NAME.toString()))
            .andExpect(jsonPath("$.conPerMobile").value(DEFAULT_CON_PER_MOBILE))
            .andExpect(jsonPath("$.conPerAddress").value(DEFAULT_CON_PER_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmployee() throws Exception {
        // Get the instEmployee
        restInstEmployeeMockMvc.perform(get("/api/instEmployees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmployee() throws Exception {
        // Initialize the database
        instEmployeeRepository.saveAndFlush(instEmployee);

		int databaseSizeBeforeUpdate = instEmployeeRepository.findAll().size();

        // Update the instEmployee
        instEmployee.setName(UPDATED_NAME);
        instEmployee.setEmail(UPDATED_EMAIL);
        instEmployee.setContactNo(UPDATED_CONTACT_NO);
        instEmployee.setFatherName(UPDATED_FATHER_NAME);
        instEmployee.setMotherName(UPDATED_MOTHER_NAME);
        instEmployee.setDob(UPDATED_DOB);
        instEmployee.setCategory(UPDATED_CATEGORY);
        instEmployee.setGender(UPDATED_GENDER);
        instEmployee.setMaritalStatus(UPDATED_MARITAL_STATUS);
        instEmployee.setBloodGroup(UPDATED_BLOOD_GROUP);
        instEmployee.setTin(UPDATED_TIN);
        instEmployee.setImage(UPDATED_IMAGE);
        instEmployee.setImageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        instEmployee.setNationality(UPDATED_NATIONALITY);
        instEmployee.setNid(UPDATED_NID);
        instEmployee.setNidImage(UPDATED_NID_IMAGE);
        instEmployee.setNidImageContentType(UPDATED_NID_IMAGE_CONTENT_TYPE);
        instEmployee.setBirthCertNo(UPDATED_BIRTH_CERT_NO);
        instEmployee.setBirthCertImage(UPDATED_BIRTH_CERT_IMAGE);
        instEmployee.setBirthCertImageContentType(UPDATED_BIRTH_CERT_IMAGE_CONTENT_TYPE);
        instEmployee.setConPerName(UPDATED_CON_PER_NAME);
        instEmployee.setConPerAddress(UPDATED_CON_PER_ADDRESS);

        restInstEmployeeMockMvc.perform(put("/api/instEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmployee)))
                .andExpect(status().isOk());

        // Validate the InstEmployee in the database
        List<InstEmployee> instEmployees = instEmployeeRepository.findAll();
        assertThat(instEmployees).hasSize(databaseSizeBeforeUpdate);
        InstEmployee testInstEmployee = instEmployees.get(instEmployees.size() - 1);
        assertThat(testInstEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstEmployee.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testInstEmployee.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testInstEmployee.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testInstEmployee.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testInstEmployee.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testInstEmployee.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testInstEmployee.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testInstEmployee.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testInstEmployee.getTin()).isEqualTo(UPDATED_TIN);
        assertThat(testInstEmployee.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testInstEmployee.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testInstEmployee.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testInstEmployee.getNid()).isEqualTo(UPDATED_NID);
        assertThat(testInstEmployee.getNidImage()).isEqualTo(UPDATED_NID_IMAGE);
        assertThat(testInstEmployee.getNidImageContentType()).isEqualTo(UPDATED_NID_IMAGE_CONTENT_TYPE);
        assertThat(testInstEmployee.getBirthCertNo()).isEqualTo(UPDATED_BIRTH_CERT_NO);
        assertThat(testInstEmployee.getBirthCertImage()).isEqualTo(UPDATED_BIRTH_CERT_IMAGE);
        assertThat(testInstEmployee.getBirthCertImageContentType()).isEqualTo(UPDATED_BIRTH_CERT_IMAGE_CONTENT_TYPE);
        assertThat(testInstEmployee.getConPerName()).isEqualTo(UPDATED_CON_PER_NAME);
        assertThat(testInstEmployee.getConPerMobile()).isEqualTo(UPDATED_CON_PER_MOBILE);
        assertThat(testInstEmployee.getConPerAddress()).isEqualTo(UPDATED_CON_PER_ADDRESS);
    }

    @Test
    @Transactional
    public void deleteInstEmployee() throws Exception {
        // Initialize the database
        instEmployeeRepository.saveAndFlush(instEmployee);

		int databaseSizeBeforeDelete = instEmployeeRepository.findAll().size();

        // Get the instEmployee
        restInstEmployeeMockMvc.perform(delete("/api/instEmployees/{id}", instEmployee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmployee> instEmployees = instEmployeeRepository.findAll();
        assertThat(instEmployees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
