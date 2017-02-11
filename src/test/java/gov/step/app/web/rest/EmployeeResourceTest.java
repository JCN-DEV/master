package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Employee;
import gov.step.app.repository.EmployeeRepository;
import gov.step.app.repository.search.EmployeeSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.step.app.domain.enumeration.Gender;

/**
 * Test class for the EmployeeResource REST controller.
 *
 * @see EmployeeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_NAME_ENGLISH = "AAAAA";
    private static final String UPDATED_NAME_ENGLISH = "BBBBB";
    private static final String DEFAULT_FATHER = "AAAAA";
    private static final String UPDATED_FATHER = "BBBBB";
    private static final String DEFAULT_MOTHER = "AAAAA";
    private static final String UPDATED_MOTHER = "BBBBB";
    private static final String DEFAULT_PRESENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PRESENT_ADDRESS = "BBBBB";
    private static final String DEFAULT_PERMANENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PERMANENT_ADDRESS = "BBBBB";


private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_ZIP_CODE = "AAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBB";
    private static final String DEFAULT_REGISTRATION_CERTIFICATE_SUBJECT = "AAAAA";
    private static final String UPDATED_REGISTRATION_CERTIFICATE_SUBJECT = "BBBBB";

    private static final Integer DEFAULT_REGISTRATION_EXAM_YEAR = 1;
    private static final Integer UPDATED_REGISTRATION_EXAM_YEAR = 2;
    private static final String DEFAULT_REGISTRATION_CETIFICATE_NO = "AAAAA";
    private static final String UPDATED_REGISTRATION_CETIFICATE_NO = "BBBBB";
    private static final String DEFAULT_INDEX_NO = "AAAAA";
    private static final String UPDATED_INDEX_NO = "BBBBB";
    private static final String DEFAULT_BANK_NAME = "AAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBB";
    private static final String DEFAULT_BANK_BRANCH = "AAAAA";
    private static final String UPDATED_BANK_BRANCH = "BBBBB";
    private static final String DEFAULT_BANK_ACCOUNT_NO = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NO = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_SALARY_SCALE = "AAAAA";
    private static final String UPDATED_SALARY_SCALE = "BBBBB";
    private static final String DEFAULT_SALARY_CODE = "AAAAA";
    private static final String UPDATED_SALARY_CODE = "BBBBB";

    private static final BigDecimal DEFAULT_MONTHLY_SALARY_GOVT_PROVIDED = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTHLY_SALARY_GOVT_PROVIDED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTHLY_SALARY_INSTITUTE_PROVIDED = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTHLY_SALARY_INSTITUTE_PROVIDED = new BigDecimal(2);

    private static final LocalDate DEFAULT_GB_RESOLUTION_RECEIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GB_RESOLUTION_RECEIVE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_GB_RESOLUTION_AGENDA_NO = "AAAAA";
    private static final String UPDATED_GB_RESOLUTION_AGENDA_NO = "BBBBB";
    private static final String DEFAULT_CIRCULAR_PAPER_NAME = "AAAAA";
    private static final String UPDATED_CIRCULAR_PAPER_NAME = "BBBBB";

    private static final LocalDate DEFAULT_CIRCULAR_PUBLISHED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CIRCULAR_PUBLISHED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RECRUIT_EXAM_RECEIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECRUIT_EXAM_RECEIVE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DG_REPRESENTATIVE_NAME = "AAAAA";
    private static final String UPDATED_DG_REPRESENTATIVE_NAME = "BBBBB";
    private static final String DEFAULT_DG_REPRESENTATIVE_DESIGNATION = "AAAAA";
    private static final String UPDATED_DG_REPRESENTATIVE_DESIGNATION = "BBBBB";
    private static final String DEFAULT_DG_REPRESENTATIVE_ADDRESS = "AAAAA";
    private static final String UPDATED_DG_REPRESENTATIVE_ADDRESS = "BBBBB";
    private static final String DEFAULT_BOARD_REPRESENTATIVE_NAME = "AAAAA";
    private static final String UPDATED_BOARD_REPRESENTATIVE_NAME = "BBBBB";
    private static final String DEFAULT_BOARD_REPRESENTATIVE_DESIGNATION = "AAAAA";
    private static final String UPDATED_BOARD_REPRESENTATIVE_DESIGNATION = "BBBBB";
    private static final String DEFAULT_BOARD_REPRESENTATIVE_ADDRESS = "AAAAA";
    private static final String UPDATED_BOARD_REPRESENTATIVE_ADDRESS = "BBBBB";

    private static final LocalDate DEFAULT_RECRUIT_APPROVE_GBRESOLUTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECRUIT_APPROVE_GBRESOLUTION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_RECRUIT_PERMIT_AGENDA_NO = "AAAAA";
    private static final String UPDATED_RECRUIT_PERMIT_AGENDA_NO = "BBBBB";

    private static final LocalDate DEFAULT_RECRUITMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECRUITMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PRESENT_INSTITUTE_JOIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRESENT_INSTITUTE_JOIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PRESENT_POST_JOIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRESENT_POST_JOIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TIME_SCALE_GBRESOLUTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIME_SCALE_GBRESOLUTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TOTAL_JOB_DURATION = 1;
    private static final Integer UPDATED_TOTAL_JOB_DURATION = 2;

    private static final Integer DEFAULT_CURRENT_POSITION_JOB_DURATION = 1;
    private static final Integer UPDATED_CURRENT_POSITION_JOB_DURATION = 2;

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private EmployeeSearchRepository employeeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeResource employeeResource = new EmployeeResource();
        ReflectionTestUtils.setField(employeeResource, "employeeRepository", employeeRepository);
        ReflectionTestUtils.setField(employeeResource, "employeeSearchRepository", employeeSearchRepository);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employee = new Employee();
        employee.setName(DEFAULT_NAME);
        employee.setNameEnglish(DEFAULT_NAME_ENGLISH);
        employee.setFather(DEFAULT_FATHER);
        employee.setMother(DEFAULT_MOTHER);
        employee.setPresentAddress(DEFAULT_PRESENT_ADDRESS);
        employee.setPermanentAddress(DEFAULT_PERMANENT_ADDRESS);
        employee.setGender(DEFAULT_GENDER);
        employee.setDob(DEFAULT_DOB);
        employee.setZipCode(DEFAULT_ZIP_CODE);
        employee.setRegistrationCertificateSubject(DEFAULT_REGISTRATION_CERTIFICATE_SUBJECT);
        employee.setRegistrationExamYear(DEFAULT_REGISTRATION_EXAM_YEAR);
        employee.setRegistrationCetificateNo(DEFAULT_REGISTRATION_CETIFICATE_NO);
        employee.setIndexNo(DEFAULT_INDEX_NO);
        employee.setBankName(DEFAULT_BANK_NAME);
        employee.setBankBranch(DEFAULT_BANK_BRANCH);
        employee.setBankAccountNo(DEFAULT_BANK_ACCOUNT_NO);
        employee.setDesignation(DEFAULT_DESIGNATION);
        employee.setSubject(DEFAULT_SUBJECT);
        employee.setSalaryScale(DEFAULT_SALARY_SCALE);
        employee.setSalaryCode(DEFAULT_SALARY_CODE);
        employee.setMonthlySalaryGovtProvided(DEFAULT_MONTHLY_SALARY_GOVT_PROVIDED);
        employee.setMonthlySalaryInstituteProvided(DEFAULT_MONTHLY_SALARY_INSTITUTE_PROVIDED);
        employee.setGbResolutionReceiveDate(DEFAULT_GB_RESOLUTION_RECEIVE_DATE);
        employee.setGbResolutionAgendaNo(DEFAULT_GB_RESOLUTION_AGENDA_NO);
        employee.setCircularPaperName(DEFAULT_CIRCULAR_PAPER_NAME);
        employee.setCircularPublishedDate(DEFAULT_CIRCULAR_PUBLISHED_DATE);
        employee.setRecruitExamReceiveDate(DEFAULT_RECRUIT_EXAM_RECEIVE_DATE);
        employee.setDgRepresentativeName(DEFAULT_DG_REPRESENTATIVE_NAME);
        employee.setDgRepresentativeDesignation(DEFAULT_DG_REPRESENTATIVE_DESIGNATION);
        employee.setDgRepresentativeAddress(DEFAULT_DG_REPRESENTATIVE_ADDRESS);
        employee.setBoardRepresentativeName(DEFAULT_BOARD_REPRESENTATIVE_NAME);
        employee.setBoardRepresentativeDesignation(DEFAULT_BOARD_REPRESENTATIVE_DESIGNATION);
        employee.setBoardRepresentativeAddress(DEFAULT_BOARD_REPRESENTATIVE_ADDRESS);
        employee.setRecruitApproveGBResolutionDate(DEFAULT_RECRUIT_APPROVE_GBRESOLUTION_DATE);
        employee.setRecruitPermitAgendaNo(DEFAULT_RECRUIT_PERMIT_AGENDA_NO);
        employee.setRecruitmentDate(DEFAULT_RECRUITMENT_DATE);
        employee.setPresentInstituteJoinDate(DEFAULT_PRESENT_INSTITUTE_JOIN_DATE);
        employee.setPresentPostJoinDate(DEFAULT_PRESENT_POST_JOIN_DATE);
        employee.setTimeScaleGBResolutionDate(DEFAULT_TIME_SCALE_GBRESOLUTION_DATE);
        employee.setTotalJobDuration(DEFAULT_TOTAL_JOB_DURATION);
        employee.setCurrentPositionJobDuration(DEFAULT_CURRENT_POSITION_JOB_DURATION);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employees.get(employees.size() - 1);
        assertThat(testEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployee.getNameEnglish()).isEqualTo(DEFAULT_NAME_ENGLISH);
        assertThat(testEmployee.getFather()).isEqualTo(DEFAULT_FATHER);
        assertThat(testEmployee.getMother()).isEqualTo(DEFAULT_MOTHER);
        assertThat(testEmployee.getPresentAddress()).isEqualTo(DEFAULT_PRESENT_ADDRESS);
        assertThat(testEmployee.getPermanentAddress()).isEqualTo(DEFAULT_PERMANENT_ADDRESS);
        assertThat(testEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testEmployee.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testEmployee.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testEmployee.getRegistrationCertificateSubject()).isEqualTo(DEFAULT_REGISTRATION_CERTIFICATE_SUBJECT);
        assertThat(testEmployee.getRegistrationExamYear()).isEqualTo(DEFAULT_REGISTRATION_EXAM_YEAR);
        assertThat(testEmployee.getRegistrationCetificateNo()).isEqualTo(DEFAULT_REGISTRATION_CETIFICATE_NO);
        assertThat(testEmployee.getIndexNo()).isEqualTo(DEFAULT_INDEX_NO);
        assertThat(testEmployee.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testEmployee.getBankBranch()).isEqualTo(DEFAULT_BANK_BRANCH);
        assertThat(testEmployee.getBankAccountNo()).isEqualTo(DEFAULT_BANK_ACCOUNT_NO);
        assertThat(testEmployee.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testEmployee.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEmployee.getSalaryScale()).isEqualTo(DEFAULT_SALARY_SCALE);
        assertThat(testEmployee.getSalaryCode()).isEqualTo(DEFAULT_SALARY_CODE);
        assertThat(testEmployee.getMonthlySalaryGovtProvided()).isEqualTo(DEFAULT_MONTHLY_SALARY_GOVT_PROVIDED);
        assertThat(testEmployee.getMonthlySalaryInstituteProvided()).isEqualTo(DEFAULT_MONTHLY_SALARY_INSTITUTE_PROVIDED);
        assertThat(testEmployee.getGbResolutionReceiveDate()).isEqualTo(DEFAULT_GB_RESOLUTION_RECEIVE_DATE);
        assertThat(testEmployee.getGbResolutionAgendaNo()).isEqualTo(DEFAULT_GB_RESOLUTION_AGENDA_NO);
        assertThat(testEmployee.getCircularPaperName()).isEqualTo(DEFAULT_CIRCULAR_PAPER_NAME);
        assertThat(testEmployee.getCircularPublishedDate()).isEqualTo(DEFAULT_CIRCULAR_PUBLISHED_DATE);
        assertThat(testEmployee.getRecruitExamReceiveDate()).isEqualTo(DEFAULT_RECRUIT_EXAM_RECEIVE_DATE);
        assertThat(testEmployee.getDgRepresentativeName()).isEqualTo(DEFAULT_DG_REPRESENTATIVE_NAME);
        assertThat(testEmployee.getDgRepresentativeDesignation()).isEqualTo(DEFAULT_DG_REPRESENTATIVE_DESIGNATION);
        assertThat(testEmployee.getDgRepresentativeAddress()).isEqualTo(DEFAULT_DG_REPRESENTATIVE_ADDRESS);
        assertThat(testEmployee.getBoardRepresentativeName()).isEqualTo(DEFAULT_BOARD_REPRESENTATIVE_NAME);
        assertThat(testEmployee.getBoardRepresentativeDesignation()).isEqualTo(DEFAULT_BOARD_REPRESENTATIVE_DESIGNATION);
        assertThat(testEmployee.getBoardRepresentativeAddress()).isEqualTo(DEFAULT_BOARD_REPRESENTATIVE_ADDRESS);
        assertThat(testEmployee.getRecruitApproveGBResolutionDate()).isEqualTo(DEFAULT_RECRUIT_APPROVE_GBRESOLUTION_DATE);
        assertThat(testEmployee.getRecruitPermitAgendaNo()).isEqualTo(DEFAULT_RECRUIT_PERMIT_AGENDA_NO);
        assertThat(testEmployee.getRecruitmentDate()).isEqualTo(DEFAULT_RECRUITMENT_DATE);
        assertThat(testEmployee.getPresentInstituteJoinDate()).isEqualTo(DEFAULT_PRESENT_INSTITUTE_JOIN_DATE);
        assertThat(testEmployee.getPresentPostJoinDate()).isEqualTo(DEFAULT_PRESENT_POST_JOIN_DATE);
        assertThat(testEmployee.getTimeScaleGBResolutionDate()).isEqualTo(DEFAULT_TIME_SCALE_GBRESOLUTION_DATE);
        assertThat(testEmployee.getTotalJobDuration()).isEqualTo(DEFAULT_TOTAL_JOB_DURATION);
        assertThat(testEmployee.getCurrentPositionJobDuration()).isEqualTo(DEFAULT_CURRENT_POSITION_JOB_DURATION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameEnglishIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setNameEnglish(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFatherIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFather(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotherIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setMother(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresentAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setPresentAddress(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPermanentAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setPermanentAddress(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setGender(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDob(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setZipCode(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegistrationCertificateSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setRegistrationCertificateSubject(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegistrationExamYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setRegistrationExamYear(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegistrationCetificateNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setRegistrationCetificateNo(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDesignation(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setSubject(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaryScaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setSalaryScale(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setSalaryCode(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthlySalaryGovtProvidedIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setMonthlySalaryGovtProvided(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthlySalaryInstituteProvidedIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setMonthlySalaryInstituteProvided(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGbResolutionReceiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setGbResolutionReceiveDate(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGbResolutionAgendaNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setGbResolutionAgendaNo(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCircularPaperNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setCircularPaperName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCircularPublishedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setCircularPublishedDate(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecruitExamReceiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setRecruitExamReceiveDate(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDgRepresentativeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDgRepresentativeName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDgRepresentativeDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDgRepresentativeDesignation(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDgRepresentativeAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDgRepresentativeAddress(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoardRepresentativeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setBoardRepresentativeName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoardRepresentativeDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setBoardRepresentativeDesignation(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoardRepresentativeAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setBoardRepresentativeAddress(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecruitApproveGBResolutionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setRecruitApproveGBResolutionDate(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecruitmentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setRecruitmentDate(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresentInstituteJoinDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setPresentInstituteJoinDate(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresentPostJoinDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setPresentPostJoinDate(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeScaleGBResolutionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setTimeScaleGBResolutionDate(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalJobDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setTotalJobDuration(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentPositionJobDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setCurrentPositionJobDuration(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employees
        restEmployeeMockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].nameEnglish").value(hasItem(DEFAULT_NAME_ENGLISH.toString())))
                .andExpect(jsonPath("$.[*].father").value(hasItem(DEFAULT_FATHER.toString())))
                .andExpect(jsonPath("$.[*].mother").value(hasItem(DEFAULT_MOTHER.toString())))
                .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
                .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
                .andExpect(jsonPath("$.[*].registrationCertificateSubject").value(hasItem(DEFAULT_REGISTRATION_CERTIFICATE_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].registrationExamYear").value(hasItem(DEFAULT_REGISTRATION_EXAM_YEAR)))
                .andExpect(jsonPath("$.[*].registrationCetificateNo").value(hasItem(DEFAULT_REGISTRATION_CETIFICATE_NO.toString())))
                .andExpect(jsonPath("$.[*].indexNo").value(hasItem(DEFAULT_INDEX_NO.toString())))
                .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
                .andExpect(jsonPath("$.[*].bankBranch").value(hasItem(DEFAULT_BANK_BRANCH.toString())))
                .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].salaryScale").value(hasItem(DEFAULT_SALARY_SCALE.toString())))
                .andExpect(jsonPath("$.[*].salaryCode").value(hasItem(DEFAULT_SALARY_CODE.toString())))
                .andExpect(jsonPath("$.[*].monthlySalaryGovtProvided").value(hasItem(DEFAULT_MONTHLY_SALARY_GOVT_PROVIDED.intValue())))
                .andExpect(jsonPath("$.[*].monthlySalaryInstituteProvided").value(hasItem(DEFAULT_MONTHLY_SALARY_INSTITUTE_PROVIDED.intValue())))
                .andExpect(jsonPath("$.[*].gbResolutionReceiveDate").value(hasItem(DEFAULT_GB_RESOLUTION_RECEIVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].gbResolutionAgendaNo").value(hasItem(DEFAULT_GB_RESOLUTION_AGENDA_NO.toString())))
                .andExpect(jsonPath("$.[*].circularPaperName").value(hasItem(DEFAULT_CIRCULAR_PAPER_NAME.toString())))
                .andExpect(jsonPath("$.[*].circularPublishedDate").value(hasItem(DEFAULT_CIRCULAR_PUBLISHED_DATE.toString())))
                .andExpect(jsonPath("$.[*].recruitExamReceiveDate").value(hasItem(DEFAULT_RECRUIT_EXAM_RECEIVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].dgRepresentativeName").value(hasItem(DEFAULT_DG_REPRESENTATIVE_NAME.toString())))
                .andExpect(jsonPath("$.[*].dgRepresentativeDesignation").value(hasItem(DEFAULT_DG_REPRESENTATIVE_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].dgRepresentativeAddress").value(hasItem(DEFAULT_DG_REPRESENTATIVE_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].boardRepresentativeName").value(hasItem(DEFAULT_BOARD_REPRESENTATIVE_NAME.toString())))
                .andExpect(jsonPath("$.[*].boardRepresentativeDesignation").value(hasItem(DEFAULT_BOARD_REPRESENTATIVE_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].boardRepresentativeAddress").value(hasItem(DEFAULT_BOARD_REPRESENTATIVE_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].recruitApproveGBResolutionDate").value(hasItem(DEFAULT_RECRUIT_APPROVE_GBRESOLUTION_DATE.toString())))
                .andExpect(jsonPath("$.[*].recruitPermitAgendaNo").value(hasItem(DEFAULT_RECRUIT_PERMIT_AGENDA_NO.toString())))
                .andExpect(jsonPath("$.[*].recruitmentDate").value(hasItem(DEFAULT_RECRUITMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].presentInstituteJoinDate").value(hasItem(DEFAULT_PRESENT_INSTITUTE_JOIN_DATE.toString())))
                .andExpect(jsonPath("$.[*].presentPostJoinDate").value(hasItem(DEFAULT_PRESENT_POST_JOIN_DATE.toString())))
                .andExpect(jsonPath("$.[*].timeScaleGBResolutionDate").value(hasItem(DEFAULT_TIME_SCALE_GBRESOLUTION_DATE.toString())))
                .andExpect(jsonPath("$.[*].totalJobDuration").value(hasItem(DEFAULT_TOTAL_JOB_DURATION)))
                .andExpect(jsonPath("$.[*].currentPositionJobDuration").value(hasItem(DEFAULT_CURRENT_POSITION_JOB_DURATION)));
    }

    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nameEnglish").value(DEFAULT_NAME_ENGLISH.toString()))
            .andExpect(jsonPath("$.father").value(DEFAULT_FATHER.toString()))
            .andExpect(jsonPath("$.mother").value(DEFAULT_MOTHER.toString()))
            .andExpect(jsonPath("$.presentAddress").value(DEFAULT_PRESENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.permanentAddress").value(DEFAULT_PERMANENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.registrationCertificateSubject").value(DEFAULT_REGISTRATION_CERTIFICATE_SUBJECT.toString()))
            .andExpect(jsonPath("$.registrationExamYear").value(DEFAULT_REGISTRATION_EXAM_YEAR))
            .andExpect(jsonPath("$.registrationCetificateNo").value(DEFAULT_REGISTRATION_CETIFICATE_NO.toString()))
            .andExpect(jsonPath("$.indexNo").value(DEFAULT_INDEX_NO.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.bankBranch").value(DEFAULT_BANK_BRANCH.toString()))
            .andExpect(jsonPath("$.bankAccountNo").value(DEFAULT_BANK_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.salaryScale").value(DEFAULT_SALARY_SCALE.toString()))
            .andExpect(jsonPath("$.salaryCode").value(DEFAULT_SALARY_CODE.toString()))
            .andExpect(jsonPath("$.monthlySalaryGovtProvided").value(DEFAULT_MONTHLY_SALARY_GOVT_PROVIDED.intValue()))
            .andExpect(jsonPath("$.monthlySalaryInstituteProvided").value(DEFAULT_MONTHLY_SALARY_INSTITUTE_PROVIDED.intValue()))
            .andExpect(jsonPath("$.gbResolutionReceiveDate").value(DEFAULT_GB_RESOLUTION_RECEIVE_DATE.toString()))
            .andExpect(jsonPath("$.gbResolutionAgendaNo").value(DEFAULT_GB_RESOLUTION_AGENDA_NO.toString()))
            .andExpect(jsonPath("$.circularPaperName").value(DEFAULT_CIRCULAR_PAPER_NAME.toString()))
            .andExpect(jsonPath("$.circularPublishedDate").value(DEFAULT_CIRCULAR_PUBLISHED_DATE.toString()))
            .andExpect(jsonPath("$.recruitExamReceiveDate").value(DEFAULT_RECRUIT_EXAM_RECEIVE_DATE.toString()))
            .andExpect(jsonPath("$.dgRepresentativeName").value(DEFAULT_DG_REPRESENTATIVE_NAME.toString()))
            .andExpect(jsonPath("$.dgRepresentativeDesignation").value(DEFAULT_DG_REPRESENTATIVE_DESIGNATION.toString()))
            .andExpect(jsonPath("$.dgRepresentativeAddress").value(DEFAULT_DG_REPRESENTATIVE_ADDRESS.toString()))
            .andExpect(jsonPath("$.boardRepresentativeName").value(DEFAULT_BOARD_REPRESENTATIVE_NAME.toString()))
            .andExpect(jsonPath("$.boardRepresentativeDesignation").value(DEFAULT_BOARD_REPRESENTATIVE_DESIGNATION.toString()))
            .andExpect(jsonPath("$.boardRepresentativeAddress").value(DEFAULT_BOARD_REPRESENTATIVE_ADDRESS.toString()))
            .andExpect(jsonPath("$.recruitApproveGBResolutionDate").value(DEFAULT_RECRUIT_APPROVE_GBRESOLUTION_DATE.toString()))
            .andExpect(jsonPath("$.recruitPermitAgendaNo").value(DEFAULT_RECRUIT_PERMIT_AGENDA_NO.toString()))
            .andExpect(jsonPath("$.recruitmentDate").value(DEFAULT_RECRUITMENT_DATE.toString()))
            .andExpect(jsonPath("$.presentInstituteJoinDate").value(DEFAULT_PRESENT_INSTITUTE_JOIN_DATE.toString()))
            .andExpect(jsonPath("$.presentPostJoinDate").value(DEFAULT_PRESENT_POST_JOIN_DATE.toString()))
            .andExpect(jsonPath("$.timeScaleGBResolutionDate").value(DEFAULT_TIME_SCALE_GBRESOLUTION_DATE.toString()))
            .andExpect(jsonPath("$.totalJobDuration").value(DEFAULT_TOTAL_JOB_DURATION))
            .andExpect(jsonPath("$.currentPositionJobDuration").value(DEFAULT_CURRENT_POSITION_JOB_DURATION));
    }

    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

		int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        employee.setName(UPDATED_NAME);
        employee.setNameEnglish(UPDATED_NAME_ENGLISH);
        employee.setFather(UPDATED_FATHER);
        employee.setMother(UPDATED_MOTHER);
        employee.setPresentAddress(UPDATED_PRESENT_ADDRESS);
        employee.setPermanentAddress(UPDATED_PERMANENT_ADDRESS);
        employee.setGender(UPDATED_GENDER);
        employee.setDob(UPDATED_DOB);
        employee.setZipCode(UPDATED_ZIP_CODE);
        employee.setRegistrationCertificateSubject(UPDATED_REGISTRATION_CERTIFICATE_SUBJECT);
        employee.setRegistrationExamYear(UPDATED_REGISTRATION_EXAM_YEAR);
        employee.setRegistrationCetificateNo(UPDATED_REGISTRATION_CETIFICATE_NO);
        employee.setIndexNo(UPDATED_INDEX_NO);
        employee.setBankName(UPDATED_BANK_NAME);
        employee.setBankBranch(UPDATED_BANK_BRANCH);
        employee.setBankAccountNo(UPDATED_BANK_ACCOUNT_NO);
        employee.setDesignation(UPDATED_DESIGNATION);
        employee.setSubject(UPDATED_SUBJECT);
        employee.setSalaryScale(UPDATED_SALARY_SCALE);
        employee.setSalaryCode(UPDATED_SALARY_CODE);
        employee.setMonthlySalaryGovtProvided(UPDATED_MONTHLY_SALARY_GOVT_PROVIDED);
        employee.setMonthlySalaryInstituteProvided(UPDATED_MONTHLY_SALARY_INSTITUTE_PROVIDED);
        employee.setGbResolutionReceiveDate(UPDATED_GB_RESOLUTION_RECEIVE_DATE);
        employee.setGbResolutionAgendaNo(UPDATED_GB_RESOLUTION_AGENDA_NO);
        employee.setCircularPaperName(UPDATED_CIRCULAR_PAPER_NAME);
        employee.setCircularPublishedDate(UPDATED_CIRCULAR_PUBLISHED_DATE);
        employee.setRecruitExamReceiveDate(UPDATED_RECRUIT_EXAM_RECEIVE_DATE);
        employee.setDgRepresentativeName(UPDATED_DG_REPRESENTATIVE_NAME);
        employee.setDgRepresentativeDesignation(UPDATED_DG_REPRESENTATIVE_DESIGNATION);
        employee.setDgRepresentativeAddress(UPDATED_DG_REPRESENTATIVE_ADDRESS);
        employee.setBoardRepresentativeName(UPDATED_BOARD_REPRESENTATIVE_NAME);
        employee.setBoardRepresentativeDesignation(UPDATED_BOARD_REPRESENTATIVE_DESIGNATION);
        employee.setBoardRepresentativeAddress(UPDATED_BOARD_REPRESENTATIVE_ADDRESS);
        employee.setRecruitApproveGBResolutionDate(UPDATED_RECRUIT_APPROVE_GBRESOLUTION_DATE);
        employee.setRecruitPermitAgendaNo(UPDATED_RECRUIT_PERMIT_AGENDA_NO);
        employee.setRecruitmentDate(UPDATED_RECRUITMENT_DATE);
        employee.setPresentInstituteJoinDate(UPDATED_PRESENT_INSTITUTE_JOIN_DATE);
        employee.setPresentPostJoinDate(UPDATED_PRESENT_POST_JOIN_DATE);
        employee.setTimeScaleGBResolutionDate(UPDATED_TIME_SCALE_GBRESOLUTION_DATE);
        employee.setTotalJobDuration(UPDATED_TOTAL_JOB_DURATION);
        employee.setCurrentPositionJobDuration(UPDATED_CURRENT_POSITION_JOB_DURATION);

        restEmployeeMockMvc.perform(put("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employees.get(employees.size() - 1);
        assertThat(testEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployee.getNameEnglish()).isEqualTo(UPDATED_NAME_ENGLISH);
        assertThat(testEmployee.getFather()).isEqualTo(UPDATED_FATHER);
        assertThat(testEmployee.getMother()).isEqualTo(UPDATED_MOTHER);
        assertThat(testEmployee.getPresentAddress()).isEqualTo(UPDATED_PRESENT_ADDRESS);
        assertThat(testEmployee.getPermanentAddress()).isEqualTo(UPDATED_PERMANENT_ADDRESS);
        assertThat(testEmployee.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testEmployee.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testEmployee.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testEmployee.getRegistrationCertificateSubject()).isEqualTo(UPDATED_REGISTRATION_CERTIFICATE_SUBJECT);
        assertThat(testEmployee.getRegistrationExamYear()).isEqualTo(UPDATED_REGISTRATION_EXAM_YEAR);
        assertThat(testEmployee.getRegistrationCetificateNo()).isEqualTo(UPDATED_REGISTRATION_CETIFICATE_NO);
        assertThat(testEmployee.getIndexNo()).isEqualTo(UPDATED_INDEX_NO);
        assertThat(testEmployee.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testEmployee.getBankBranch()).isEqualTo(UPDATED_BANK_BRANCH);
        assertThat(testEmployee.getBankAccountNo()).isEqualTo(UPDATED_BANK_ACCOUNT_NO);
        assertThat(testEmployee.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployee.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmployee.getSalaryScale()).isEqualTo(UPDATED_SALARY_SCALE);
        assertThat(testEmployee.getSalaryCode()).isEqualTo(UPDATED_SALARY_CODE);
        assertThat(testEmployee.getMonthlySalaryGovtProvided()).isEqualTo(UPDATED_MONTHLY_SALARY_GOVT_PROVIDED);
        assertThat(testEmployee.getMonthlySalaryInstituteProvided()).isEqualTo(UPDATED_MONTHLY_SALARY_INSTITUTE_PROVIDED);
        assertThat(testEmployee.getGbResolutionReceiveDate()).isEqualTo(UPDATED_GB_RESOLUTION_RECEIVE_DATE);
        assertThat(testEmployee.getGbResolutionAgendaNo()).isEqualTo(UPDATED_GB_RESOLUTION_AGENDA_NO);
        assertThat(testEmployee.getCircularPaperName()).isEqualTo(UPDATED_CIRCULAR_PAPER_NAME);
        assertThat(testEmployee.getCircularPublishedDate()).isEqualTo(UPDATED_CIRCULAR_PUBLISHED_DATE);
        assertThat(testEmployee.getRecruitExamReceiveDate()).isEqualTo(UPDATED_RECRUIT_EXAM_RECEIVE_DATE);
        assertThat(testEmployee.getDgRepresentativeName()).isEqualTo(UPDATED_DG_REPRESENTATIVE_NAME);
        assertThat(testEmployee.getDgRepresentativeDesignation()).isEqualTo(UPDATED_DG_REPRESENTATIVE_DESIGNATION);
        assertThat(testEmployee.getDgRepresentativeAddress()).isEqualTo(UPDATED_DG_REPRESENTATIVE_ADDRESS);
        assertThat(testEmployee.getBoardRepresentativeName()).isEqualTo(UPDATED_BOARD_REPRESENTATIVE_NAME);
        assertThat(testEmployee.getBoardRepresentativeDesignation()).isEqualTo(UPDATED_BOARD_REPRESENTATIVE_DESIGNATION);
        assertThat(testEmployee.getBoardRepresentativeAddress()).isEqualTo(UPDATED_BOARD_REPRESENTATIVE_ADDRESS);
        assertThat(testEmployee.getRecruitApproveGBResolutionDate()).isEqualTo(UPDATED_RECRUIT_APPROVE_GBRESOLUTION_DATE);
        assertThat(testEmployee.getRecruitPermitAgendaNo()).isEqualTo(UPDATED_RECRUIT_PERMIT_AGENDA_NO);
        assertThat(testEmployee.getRecruitmentDate()).isEqualTo(UPDATED_RECRUITMENT_DATE);
        assertThat(testEmployee.getPresentInstituteJoinDate()).isEqualTo(UPDATED_PRESENT_INSTITUTE_JOIN_DATE);
        assertThat(testEmployee.getPresentPostJoinDate()).isEqualTo(UPDATED_PRESENT_POST_JOIN_DATE);
        assertThat(testEmployee.getTimeScaleGBResolutionDate()).isEqualTo(UPDATED_TIME_SCALE_GBRESOLUTION_DATE);
        assertThat(testEmployee.getTotalJobDuration()).isEqualTo(UPDATED_TOTAL_JOB_DURATION);
        assertThat(testEmployee.getCurrentPositionJobDuration()).isEqualTo(UPDATED_CURRENT_POSITION_JOB_DURATION);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

		int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Get the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
