package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TempEmployer;
import gov.step.app.repository.TempEmployerRepository;
import gov.step.app.repository.search.TempEmployerSearchRepository;

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

import gov.step.app.domain.enumeration.TempEmployerStatus;

/**
 * Test class for the TempEmployerResource REST controller.
 *
 * @see TempEmployerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TempEmployerResourceTest {

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


private static final TempEmployerStatus DEFAULT_STATUS = TempEmployerStatus.pending;
    private static final TempEmployerStatus UPDATED_STATUS = TempEmployerStatus.rejected;

    @Inject
    private TempEmployerRepository tempEmployerRepository;

    @Inject
    private TempEmployerSearchRepository tempEmployerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTempEmployerMockMvc;

    private TempEmployer tempEmployer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TempEmployerResource tempEmployerResource = new TempEmployerResource();
        ReflectionTestUtils.setField(tempEmployerResource, "tempEmployerRepository", tempEmployerRepository);
        ReflectionTestUtils.setField(tempEmployerResource, "tempEmployerSearchRepository", tempEmployerSearchRepository);
        this.restTempEmployerMockMvc = MockMvcBuilders.standaloneSetup(tempEmployerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tempEmployer = new TempEmployer();
        tempEmployer.setName(DEFAULT_NAME);
        tempEmployer.setAlternativeCompanyName(DEFAULT_ALTERNATIVE_COMPANY_NAME);
        tempEmployer.setContactPersonName(DEFAULT_CONTACT_PERSON_NAME);
        tempEmployer.setPersonDesignation(DEFAULT_PERSON_DESIGNATION);
        tempEmployer.setContactNumber(DEFAULT_CONTACT_NUMBER);
        tempEmployer.setCompanyInformation(DEFAULT_COMPANY_INFORMATION);
        tempEmployer.setAddress(DEFAULT_ADDRESS);
        tempEmployer.setCity(DEFAULT_CITY);
        tempEmployer.setZipCode(DEFAULT_ZIP_CODE);
        tempEmployer.setCompanyWebsite(DEFAULT_COMPANY_WEBSITE);
        tempEmployer.setIndustryType(DEFAULT_INDUSTRY_TYPE);
        tempEmployer.setBusinessDescription(DEFAULT_BUSINESS_DESCRIPTION);
        tempEmployer.setCompanyLogo(DEFAULT_COMPANY_LOGO);
        tempEmployer.setCompanyLogoContentType(DEFAULT_COMPANY_LOGO_CONTENT_TYPE);
        tempEmployer.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createTempEmployer() throws Exception {
        int databaseSizeBeforeCreate = tempEmployerRepository.findAll().size();

        // Create the TempEmployer

        restTempEmployerMockMvc.perform(post("/api/tempEmployers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempEmployer)))
                .andExpect(status().isCreated());

        // Validate the TempEmployer in the database
        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeCreate + 1);
        TempEmployer testTempEmployer = tempEmployers.get(tempEmployers.size() - 1);
        assertThat(testTempEmployer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTempEmployer.getAlternativeCompanyName()).isEqualTo(DEFAULT_ALTERNATIVE_COMPANY_NAME);
        assertThat(testTempEmployer.getContactPersonName()).isEqualTo(DEFAULT_CONTACT_PERSON_NAME);
        assertThat(testTempEmployer.getPersonDesignation()).isEqualTo(DEFAULT_PERSON_DESIGNATION);
        assertThat(testTempEmployer.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testTempEmployer.getCompanyInformation()).isEqualTo(DEFAULT_COMPANY_INFORMATION);
        assertThat(testTempEmployer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTempEmployer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testTempEmployer.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testTempEmployer.getCompanyWebsite()).isEqualTo(DEFAULT_COMPANY_WEBSITE);
        assertThat(testTempEmployer.getIndustryType()).isEqualTo(DEFAULT_INDUSTRY_TYPE);
        assertThat(testTempEmployer.getBusinessDescription()).isEqualTo(DEFAULT_BUSINESS_DESCRIPTION);
        assertThat(testTempEmployer.getCompanyLogo()).isEqualTo(DEFAULT_COMPANY_LOGO);
        assertThat(testTempEmployer.getCompanyLogoContentType()).isEqualTo(DEFAULT_COMPANY_LOGO_CONTENT_TYPE);
        assertThat(testTempEmployer.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempEmployerRepository.findAll().size();
        // set the field null
        tempEmployer.setName(null);

        // Create the TempEmployer, which fails.

        restTempEmployerMockMvc.perform(post("/api/tempEmployers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempEmployer)))
                .andExpect(status().isBadRequest());

        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempEmployerRepository.findAll().size();
        // set the field null
        tempEmployer.setContactNumber(null);

        // Create the TempEmployer, which fails.

        restTempEmployerMockMvc.perform(post("/api/tempEmployers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempEmployer)))
                .andExpect(status().isBadRequest());

        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyInformationIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempEmployerRepository.findAll().size();
        // set the field null
        tempEmployer.setCompanyInformation(null);

        // Create the TempEmployer, which fails.

        restTempEmployerMockMvc.perform(post("/api/tempEmployers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempEmployer)))
                .andExpect(status().isBadRequest());

        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempEmployerRepository.findAll().size();
        // set the field null
        tempEmployer.setAddress(null);

        // Create the TempEmployer, which fails.

        restTempEmployerMockMvc.perform(post("/api/tempEmployers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempEmployer)))
                .andExpect(status().isBadRequest());

        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempEmployerRepository.findAll().size();
        // set the field null
        tempEmployer.setCity(null);

        // Create the TempEmployer, which fails.

        restTempEmployerMockMvc.perform(post("/api/tempEmployers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempEmployer)))
                .andExpect(status().isBadRequest());

        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempEmployerRepository.findAll().size();
        // set the field null
        tempEmployer.setZipCode(null);

        // Create the TempEmployer, which fails.

        restTempEmployerMockMvc.perform(post("/api/tempEmployers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempEmployer)))
                .andExpect(status().isBadRequest());

        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndustryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempEmployerRepository.findAll().size();
        // set the field null
        tempEmployer.setIndustryType(null);

        // Create the TempEmployer, which fails.

        restTempEmployerMockMvc.perform(post("/api/tempEmployers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempEmployer)))
                .andExpect(status().isBadRequest());

        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusinessDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempEmployerRepository.findAll().size();
        // set the field null
        tempEmployer.setBusinessDescription(null);

        // Create the TempEmployer, which fails.

        restTempEmployerMockMvc.perform(post("/api/tempEmployers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempEmployer)))
                .andExpect(status().isBadRequest());

        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTempEmployers() throws Exception {
        // Initialize the database
        tempEmployerRepository.saveAndFlush(tempEmployer);

        // Get all the tempEmployers
        restTempEmployerMockMvc.perform(get("/api/tempEmployers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tempEmployer.getId().intValue())))
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
                .andExpect(jsonPath("$.[*].companyLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPANY_LOGO))))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getTempEmployer() throws Exception {
        // Initialize the database
        tempEmployerRepository.saveAndFlush(tempEmployer);

        // Get the tempEmployer
        restTempEmployerMockMvc.perform(get("/api/tempEmployers/{id}", tempEmployer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tempEmployer.getId().intValue()))
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
            .andExpect(jsonPath("$.companyLogo").value(Base64Utils.encodeToString(DEFAULT_COMPANY_LOGO)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTempEmployer() throws Exception {
        // Get the tempEmployer
        restTempEmployerMockMvc.perform(get("/api/tempEmployers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTempEmployer() throws Exception {
        // Initialize the database
        tempEmployerRepository.saveAndFlush(tempEmployer);

		int databaseSizeBeforeUpdate = tempEmployerRepository.findAll().size();

        // Update the tempEmployer
        tempEmployer.setName(UPDATED_NAME);
        tempEmployer.setAlternativeCompanyName(UPDATED_ALTERNATIVE_COMPANY_NAME);
        tempEmployer.setContactPersonName(UPDATED_CONTACT_PERSON_NAME);
        tempEmployer.setPersonDesignation(UPDATED_PERSON_DESIGNATION);
        tempEmployer.setContactNumber(UPDATED_CONTACT_NUMBER);
        tempEmployer.setCompanyInformation(UPDATED_COMPANY_INFORMATION);
        tempEmployer.setAddress(UPDATED_ADDRESS);
        tempEmployer.setCity(UPDATED_CITY);
        tempEmployer.setZipCode(UPDATED_ZIP_CODE);
        tempEmployer.setCompanyWebsite(UPDATED_COMPANY_WEBSITE);
        tempEmployer.setIndustryType(UPDATED_INDUSTRY_TYPE);
        tempEmployer.setBusinessDescription(UPDATED_BUSINESS_DESCRIPTION);
        tempEmployer.setCompanyLogo(UPDATED_COMPANY_LOGO);
        tempEmployer.setCompanyLogoContentType(UPDATED_COMPANY_LOGO_CONTENT_TYPE);
        tempEmployer.setStatus(UPDATED_STATUS);

        restTempEmployerMockMvc.perform(put("/api/tempEmployers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempEmployer)))
                .andExpect(status().isOk());

        // Validate the TempEmployer in the database
        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeUpdate);
        TempEmployer testTempEmployer = tempEmployers.get(tempEmployers.size() - 1);
        assertThat(testTempEmployer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTempEmployer.getAlternativeCompanyName()).isEqualTo(UPDATED_ALTERNATIVE_COMPANY_NAME);
        assertThat(testTempEmployer.getContactPersonName()).isEqualTo(UPDATED_CONTACT_PERSON_NAME);
        assertThat(testTempEmployer.getPersonDesignation()).isEqualTo(UPDATED_PERSON_DESIGNATION);
        assertThat(testTempEmployer.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testTempEmployer.getCompanyInformation()).isEqualTo(UPDATED_COMPANY_INFORMATION);
        assertThat(testTempEmployer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTempEmployer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testTempEmployer.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testTempEmployer.getCompanyWebsite()).isEqualTo(UPDATED_COMPANY_WEBSITE);
        assertThat(testTempEmployer.getIndustryType()).isEqualTo(UPDATED_INDUSTRY_TYPE);
        assertThat(testTempEmployer.getBusinessDescription()).isEqualTo(UPDATED_BUSINESS_DESCRIPTION);
        assertThat(testTempEmployer.getCompanyLogo()).isEqualTo(UPDATED_COMPANY_LOGO);
        assertThat(testTempEmployer.getCompanyLogoContentType()).isEqualTo(UPDATED_COMPANY_LOGO_CONTENT_TYPE);
        assertThat(testTempEmployer.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteTempEmployer() throws Exception {
        // Initialize the database
        tempEmployerRepository.saveAndFlush(tempEmployer);

		int databaseSizeBeforeDelete = tempEmployerRepository.findAll().size();

        // Get the tempEmployer
        restTempEmployerMockMvc.perform(delete("/api/tempEmployers/{id}", tempEmployer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TempEmployer> tempEmployers = tempEmployerRepository.findAll();
        assertThat(tempEmployers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
