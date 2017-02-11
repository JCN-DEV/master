package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Employer;
import gov.step.app.repository.EmployerRepository;
import gov.step.app.repository.search.EmployerSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EmployerResource REST controller.
 *
 * @see EmployerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployerResourceTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";
    private static final String DEFAULT_ALTERNATIVE_COMPANY_NAME = "AAAAA";
    private static final String UPDATED_ALTERNATIVE_COMPANY_NAME = "BBBBB";
    private static final String DEFAULT_CONTACT_PERSON_NAME = "AAAAA";
    private static final String UPDATED_CONTACT_PERSON_NAME = "BBBBB";
    private static final String DEFAULT_PERSON_DESIGNATION = "AAAAA";
    private static final String UPDATED_PERSON_DESIGNATION = "BBBBB";
    private static final String DEFAULT_CONTACT_NUMBER = "AAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBB";
    private static final String DEFAULT_COMPANY_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_INFORMATION = "BBBBBBBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";
    private static final String DEFAULT_ZIP_CODE = "AAAA";
    private static final String UPDATED_ZIP_CODE = "BBBB";
    private static final String DEFAULT_COMPANY_WEBSITE = "AAAAA";
    private static final String UPDATED_COMPANY_WEBSITE = "BBBBB";
    private static final String DEFAULT_INDUSTRY_TYPE = "AAAAA";
    private static final String UPDATED_INDUSTRY_TYPE = "BBBBB";
    private static final String DEFAULT_BUSINESS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMPANY_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMPANY_LOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COMPANY_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMPANY_LOGO_CONTENT_TYPE = "image/png";

    @Inject
    private EmployerRepository employerRepository;

    @Inject
    private EmployerSearchRepository employerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployerMockMvc;

    private Employer employer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployerResource employerResource = new EmployerResource();
        ReflectionTestUtils.setField(employerResource, "employerRepository", employerRepository);
        ReflectionTestUtils.setField(employerResource, "employerSearchRepository", employerSearchRepository);
        this.restEmployerMockMvc = MockMvcBuilders.standaloneSetup(employerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employer = new Employer();
        employer.setName(DEFAULT_NAME);
        employer.setAlternativeCompanyName(DEFAULT_ALTERNATIVE_COMPANY_NAME);
        employer.setContactPersonName(DEFAULT_CONTACT_PERSON_NAME);
        employer.setPersonDesignation(DEFAULT_PERSON_DESIGNATION);
        employer.setContactNumber(DEFAULT_CONTACT_NUMBER);
        employer.setCompanyInformation(DEFAULT_COMPANY_INFORMATION);
        employer.setAddress(DEFAULT_ADDRESS);
        employer.setCity(DEFAULT_CITY);
        employer.setZipCode(DEFAULT_ZIP_CODE);
        employer.setCompanyWebsite(DEFAULT_COMPANY_WEBSITE);
        employer.setIndustryType(DEFAULT_INDUSTRY_TYPE);
        employer.setBusinessDescription(DEFAULT_BUSINESS_DESCRIPTION);
        employer.setCompanyLogo(DEFAULT_COMPANY_LOGO);
        employer.setCompanyLogoContentType(DEFAULT_COMPANY_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createEmployer() throws Exception {
        int databaseSizeBeforeCreate = employerRepository.findAll().size();

        // Create the Employer

        restEmployerMockMvc.perform(post("/api/employers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employer)))
                .andExpect(status().isCreated());

        // Validate the Employer in the database
        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeCreate + 1);
        Employer testEmployer = employers.get(employers.size() - 1);
        assertThat(testEmployer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployer.getAlternativeCompanyName()).isEqualTo(DEFAULT_ALTERNATIVE_COMPANY_NAME);
        assertThat(testEmployer.getContactPersonName()).isEqualTo(DEFAULT_CONTACT_PERSON_NAME);
        assertThat(testEmployer.getPersonDesignation()).isEqualTo(DEFAULT_PERSON_DESIGNATION);
        assertThat(testEmployer.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testEmployer.getCompanyInformation()).isEqualTo(DEFAULT_COMPANY_INFORMATION);
        assertThat(testEmployer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEmployer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEmployer.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testEmployer.getCompanyWebsite()).isEqualTo(DEFAULT_COMPANY_WEBSITE);
        assertThat(testEmployer.getIndustryType()).isEqualTo(DEFAULT_INDUSTRY_TYPE);
        assertThat(testEmployer.getBusinessDescription()).isEqualTo(DEFAULT_BUSINESS_DESCRIPTION);
        assertThat(testEmployer.getCompanyLogo()).isEqualTo(DEFAULT_COMPANY_LOGO);
        assertThat(testEmployer.getCompanyLogoContentType()).isEqualTo(DEFAULT_COMPANY_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setName(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employer)))
                .andExpect(status().isBadRequest());

        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setContactNumber(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employer)))
                .andExpect(status().isBadRequest());

        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyInformationIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setCompanyInformation(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employer)))
                .andExpect(status().isBadRequest());

        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setAddress(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employer)))
                .andExpect(status().isBadRequest());

        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setCity(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employer)))
                .andExpect(status().isBadRequest());

        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setZipCode(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employer)))
                .andExpect(status().isBadRequest());

        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndustryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setIndustryType(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employer)))
                .andExpect(status().isBadRequest());

        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusinessDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = employerRepository.findAll().size();
        // set the field null
        employer.setBusinessDescription(null);

        // Create the Employer, which fails.

        restEmployerMockMvc.perform(post("/api/employers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employer)))
                .andExpect(status().isBadRequest());

        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployers() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);

        // Get all the employers
        restEmployerMockMvc.perform(get("/api/employers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employer.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].alternativeCompanyName").value(hasItem(DEFAULT_ALTERNATIVE_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].contactPersonName").value(hasItem(DEFAULT_CONTACT_PERSON_NAME.toString())))
                .andExpect(jsonPath("$.[*].personDesignation").value(hasItem(DEFAULT_PERSON_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].companyInformation").value(hasItem(DEFAULT_COMPANY_INFORMATION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
                .andExpect(jsonPath("$.[*].companyWebsite").value(hasItem(DEFAULT_COMPANY_WEBSITE.toString())))
                .andExpect(jsonPath("$.[*].industryType").value(hasItem(DEFAULT_INDUSTRY_TYPE.toString())))
                .andExpect(jsonPath("$.[*].businessDescription").value(hasItem(DEFAULT_BUSINESS_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].companyLogoContentType").value(hasItem(DEFAULT_COMPANY_LOGO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].companyLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPANY_LOGO))));
    }

    @Test
    @Transactional
    public void getEmployer() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);

        // Get the employer
        restEmployerMockMvc.perform(get("/api/employers/{id}", employer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.alternativeCompanyName").value(DEFAULT_ALTERNATIVE_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.contactPersonName").value(DEFAULT_CONTACT_PERSON_NAME.toString()))
            .andExpect(jsonPath("$.personDesignation").value(DEFAULT_PERSON_DESIGNATION.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.companyInformation").value(DEFAULT_COMPANY_INFORMATION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.companyWebsite").value(DEFAULT_COMPANY_WEBSITE.toString()))
            .andExpect(jsonPath("$.industryType").value(DEFAULT_INDUSTRY_TYPE.toString()))
            .andExpect(jsonPath("$.businessDescription").value(DEFAULT_BUSINESS_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.companyLogoContentType").value(DEFAULT_COMPANY_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.companyLogo").value(Base64Utils.encodeToString(DEFAULT_COMPANY_LOGO)));
    }

    @Test
    @Transactional
    public void getNonExistingEmployer() throws Exception {
        // Get the employer
        restEmployerMockMvc.perform(get("/api/employers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployer() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);

		int databaseSizeBeforeUpdate = employerRepository.findAll().size();

        // Update the employer
        employer.setName(UPDATED_NAME);
        employer.setAlternativeCompanyName(UPDATED_ALTERNATIVE_COMPANY_NAME);
        employer.setContactPersonName(UPDATED_CONTACT_PERSON_NAME);
        employer.setPersonDesignation(UPDATED_PERSON_DESIGNATION);
        employer.setContactNumber(UPDATED_CONTACT_NUMBER);
        employer.setCompanyInformation(UPDATED_COMPANY_INFORMATION);
        employer.setAddress(UPDATED_ADDRESS);
        employer.setCity(UPDATED_CITY);
        employer.setZipCode(UPDATED_ZIP_CODE);
        employer.setCompanyWebsite(UPDATED_COMPANY_WEBSITE);
        employer.setIndustryType(UPDATED_INDUSTRY_TYPE);
        employer.setBusinessDescription(UPDATED_BUSINESS_DESCRIPTION);
        employer.setCompanyLogo(UPDATED_COMPANY_LOGO);
        employer.setCompanyLogoContentType(UPDATED_COMPANY_LOGO_CONTENT_TYPE);

        restEmployerMockMvc.perform(put("/api/employers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employer)))
                .andExpect(status().isOk());

        // Validate the Employer in the database
        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeUpdate);
        Employer testEmployer = employers.get(employers.size() - 1);
        assertThat(testEmployer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployer.getAlternativeCompanyName()).isEqualTo(UPDATED_ALTERNATIVE_COMPANY_NAME);
        assertThat(testEmployer.getContactPersonName()).isEqualTo(UPDATED_CONTACT_PERSON_NAME);
        assertThat(testEmployer.getPersonDesignation()).isEqualTo(UPDATED_PERSON_DESIGNATION);
        assertThat(testEmployer.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testEmployer.getCompanyInformation()).isEqualTo(UPDATED_COMPANY_INFORMATION);
        assertThat(testEmployer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEmployer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmployer.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testEmployer.getCompanyWebsite()).isEqualTo(UPDATED_COMPANY_WEBSITE);
        assertThat(testEmployer.getIndustryType()).isEqualTo(UPDATED_INDUSTRY_TYPE);
        assertThat(testEmployer.getBusinessDescription()).isEqualTo(UPDATED_BUSINESS_DESCRIPTION);
        assertThat(testEmployer.getCompanyLogo()).isEqualTo(UPDATED_COMPANY_LOGO);
        assertThat(testEmployer.getCompanyLogoContentType()).isEqualTo(UPDATED_COMPANY_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteEmployer() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);

		int databaseSizeBeforeDelete = employerRepository.findAll().size();

        // Get the employer
        restEmployerMockMvc.perform(delete("/api/employers/{id}", employer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Employer> employers = employerRepository.findAll();
        assertThat(employers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
