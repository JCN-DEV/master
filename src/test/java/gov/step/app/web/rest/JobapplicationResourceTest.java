package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Jobapplication;
import gov.step.app.repository.JobapplicationRepository;
import gov.step.app.repository.search.JobapplicationSearchRepository;

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

import gov.step.app.domain.enumeration.JobApplicationStatus;
import gov.step.app.domain.enumeration.CvType;

/**
 * Test class for the JobapplicationResource REST controller.
 *
 * @see JobapplicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobapplicationResourceTest {

    private static final String DEFAULT_NOTE = "AAAAA";
    private static final String UPDATED_NOTE = "BBBBB";

    private static final byte[] DEFAULT_CV = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CV = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CV_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CV_CONTENT_TYPE = "image/png";

    private static final BigDecimal DEFAULT_EXPECTED_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_SALARY = new BigDecimal(2);


private static final JobApplicationStatus DEFAULT_APPLICANT_STATUS = JobApplicationStatus.awaiting;
    private static final JobApplicationStatus UPDATED_APPLICANT_STATUS = JobApplicationStatus.shortlisted;


private static final CvType DEFAULT_CV_TYPE = CvType.internal;
    private static final CvType UPDATED_CV_TYPE = CvType.external;

    private static final LocalDate DEFAULT_APPLIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private JobapplicationRepository jobapplicationRepository;

    @Inject
    private JobapplicationSearchRepository jobapplicationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJobapplicationMockMvc;

    private Jobapplication jobapplication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobapplicationResource jobapplicationResource = new JobapplicationResource();
        ReflectionTestUtils.setField(jobapplicationResource, "jobapplicationRepository", jobapplicationRepository);
        ReflectionTestUtils.setField(jobapplicationResource, "jobapplicationSearchRepository", jobapplicationSearchRepository);
        this.restJobapplicationMockMvc = MockMvcBuilders.standaloneSetup(jobapplicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jobapplication = new Jobapplication();
        jobapplication.setNote(DEFAULT_NOTE);
        jobapplication.setCv(DEFAULT_CV);
        jobapplication.setCvContentType(DEFAULT_CV_CONTENT_TYPE);
        jobapplication.setExpectedSalary(DEFAULT_EXPECTED_SALARY);
        jobapplication.setApplicantStatus(DEFAULT_APPLICANT_STATUS);
        jobapplication.setCvType(DEFAULT_CV_TYPE);
        jobapplication.setAppliedDate(DEFAULT_APPLIED_DATE);
    }

    @Test
    @Transactional
    public void createJobapplication() throws Exception {
        int databaseSizeBeforeCreate = jobapplicationRepository.findAll().size();

        // Create the Jobapplication

        restJobapplicationMockMvc.perform(post("/api/jobapplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobapplication)))
                .andExpect(status().isCreated());

        // Validate the Jobapplication in the database
        List<Jobapplication> jobapplications = jobapplicationRepository.findAll();
        assertThat(jobapplications).hasSize(databaseSizeBeforeCreate + 1);
        Jobapplication testJobapplication = jobapplications.get(jobapplications.size() - 1);
        assertThat(testJobapplication.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testJobapplication.getCv()).isEqualTo(DEFAULT_CV);
        assertThat(testJobapplication.getCvContentType()).isEqualTo(DEFAULT_CV_CONTENT_TYPE);
        assertThat(testJobapplication.getExpectedSalary()).isEqualTo(DEFAULT_EXPECTED_SALARY);
        assertThat(testJobapplication.getApplicantStatus()).isEqualTo(DEFAULT_APPLICANT_STATUS);
        assertThat(testJobapplication.getCvType()).isEqualTo(DEFAULT_CV_TYPE);
        assertThat(testJobapplication.getAppliedDate()).isEqualTo(DEFAULT_APPLIED_DATE);
    }

    @Test
    @Transactional
    public void getAllJobapplications() throws Exception {
        // Initialize the database
        jobapplicationRepository.saveAndFlush(jobapplication);

        // Get all the jobapplications
        restJobapplicationMockMvc.perform(get("/api/jobapplications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jobapplication.getId().intValue())))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
                .andExpect(jsonPath("$.[*].cvContentType").value(hasItem(DEFAULT_CV_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].cv").value(hasItem(Base64Utils.encodeToString(DEFAULT_CV))))
                .andExpect(jsonPath("$.[*].expectedSalary").value(hasItem(DEFAULT_EXPECTED_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].applicantStatus").value(hasItem(DEFAULT_APPLICANT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].cvType").value(hasItem(DEFAULT_CV_TYPE.toString())))
                .andExpect(jsonPath("$.[*].appliedDate").value(hasItem(DEFAULT_APPLIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getJobapplication() throws Exception {
        // Initialize the database
        jobapplicationRepository.saveAndFlush(jobapplication);

        // Get the jobapplication
        restJobapplicationMockMvc.perform(get("/api/jobapplications/{id}", jobapplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jobapplication.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.cvContentType").value(DEFAULT_CV_CONTENT_TYPE))
            .andExpect(jsonPath("$.cv").value(Base64Utils.encodeToString(DEFAULT_CV)))
            .andExpect(jsonPath("$.expectedSalary").value(DEFAULT_EXPECTED_SALARY.intValue()))
            .andExpect(jsonPath("$.applicantStatus").value(DEFAULT_APPLICANT_STATUS.toString()))
            .andExpect(jsonPath("$.cvType").value(DEFAULT_CV_TYPE.toString()))
            .andExpect(jsonPath("$.appliedDate").value(DEFAULT_APPLIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobapplication() throws Exception {
        // Get the jobapplication
        restJobapplicationMockMvc.perform(get("/api/jobapplications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobapplication() throws Exception {
        // Initialize the database
        jobapplicationRepository.saveAndFlush(jobapplication);

		int databaseSizeBeforeUpdate = jobapplicationRepository.findAll().size();

        // Update the jobapplication
        jobapplication.setNote(UPDATED_NOTE);
        jobapplication.setCv(UPDATED_CV);
        jobapplication.setCvContentType(UPDATED_CV_CONTENT_TYPE);
        jobapplication.setExpectedSalary(UPDATED_EXPECTED_SALARY);
        jobapplication.setApplicantStatus(UPDATED_APPLICANT_STATUS);
        jobapplication.setCvType(UPDATED_CV_TYPE);
        jobapplication.setAppliedDate(UPDATED_APPLIED_DATE);

        restJobapplicationMockMvc.perform(put("/api/jobapplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobapplication)))
                .andExpect(status().isOk());

        // Validate the Jobapplication in the database
        List<Jobapplication> jobapplications = jobapplicationRepository.findAll();
        assertThat(jobapplications).hasSize(databaseSizeBeforeUpdate);
        Jobapplication testJobapplication = jobapplications.get(jobapplications.size() - 1);
        assertThat(testJobapplication.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testJobapplication.getCv()).isEqualTo(UPDATED_CV);
        assertThat(testJobapplication.getCvContentType()).isEqualTo(UPDATED_CV_CONTENT_TYPE);
        assertThat(testJobapplication.getExpectedSalary()).isEqualTo(UPDATED_EXPECTED_SALARY);
        assertThat(testJobapplication.getApplicantStatus()).isEqualTo(UPDATED_APPLICANT_STATUS);
        assertThat(testJobapplication.getCvType()).isEqualTo(UPDATED_CV_TYPE);
        assertThat(testJobapplication.getAppliedDate()).isEqualTo(UPDATED_APPLIED_DATE);
    }

    @Test
    @Transactional
    public void deleteJobapplication() throws Exception {
        // Initialize the database
        jobapplicationRepository.saveAndFlush(jobapplication);

		int databaseSizeBeforeDelete = jobapplicationRepository.findAll().size();

        // Get the jobapplication
        restJobapplicationMockMvc.perform(delete("/api/jobapplications/{id}", jobapplication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Jobapplication> jobapplications = jobapplicationRepository.findAll();
        assertThat(jobapplications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
