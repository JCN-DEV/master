package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Job;
import gov.step.app.repository.JobRepository;
import gov.step.app.repository.search.JobSearchRepository;

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

import gov.step.app.domain.enumeration.JobStatus;

/**
 * Test class for the JobResource REST controller.
 *
 * @see JobResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobResourceTest {

    private static final String DEFAULT_TITLE = "AAA";
    private static final String UPDATED_TITLE = "BBB";
    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    private static final BigDecimal DEFAULT_MINIMUM_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MINIMUM_SALARY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MAXIMUM_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MAXIMUM_SALARY = new BigDecimal(2);

    private static final Integer DEFAULT_VACANCY = 1;
    private static final Integer UPDATED_VACANCY = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_BENEFITS = "AAAAA";
    private static final String UPDATED_BENEFITS = "BBBBB";
    private static final String DEFAULT_EDUCATION_REQUIREMENTS = "AAAAA";
    private static final String UPDATED_EDUCATION_REQUIREMENTS = "BBBBB";
    private static final String DEFAULT_EXPERIENCE_REQUIREMENTS = "AAAAA";
    private static final String UPDATED_EXPERIENCE_REQUIREMENTS = "BBBBB";
    private static final String DEFAULT_OTHER_REQUIREMENTS = "AAAAA";
    private static final String UPDATED_OTHER_REQUIREMENTS = "BBBBB";

    private static final LocalDate DEFAULT_PUBLISHED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLISHED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APPLICATION_DEADLINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLICATION_DEADLINE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";


private static final JobStatus DEFAULT_STATUS = JobStatus.awaiting;
    private static final JobStatus UPDATED_STATUS = JobStatus.shortlisted;

    @Inject
    private JobRepository jobRepository;

    @Inject
    private JobSearchRepository jobSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJobMockMvc;

    private Job job;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobResource jobResource = new JobResource();
        ReflectionTestUtils.setField(jobResource, "jobRepository", jobRepository);
        ReflectionTestUtils.setField(jobResource, "jobSearchRepository", jobSearchRepository);
        this.restJobMockMvc = MockMvcBuilders.standaloneSetup(jobResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        job = new Job();
        job.setTitle(DEFAULT_TITLE);
        job.setType(DEFAULT_TYPE);
        job.setMinimumSalary(DEFAULT_MINIMUM_SALARY);
        job.setMaximumSalary(DEFAULT_MAXIMUM_SALARY);
        job.setVacancy(DEFAULT_VACANCY);
        job.setDescription(DEFAULT_DESCRIPTION);
        job.setBenefits(DEFAULT_BENEFITS);
        job.setEducationRequirements(DEFAULT_EDUCATION_REQUIREMENTS);
        job.setExperienceRequirements(DEFAULT_EXPERIENCE_REQUIREMENTS);
        job.setOtherRequirements(DEFAULT_OTHER_REQUIREMENTS);
        job.setPublishedAt(DEFAULT_PUBLISHED_AT);
        job.setApplicationDeadline(DEFAULT_APPLICATION_DEADLINE);
        job.setLocation(DEFAULT_LOCATION);
        job.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createJob() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the Job

        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isCreated());

        // Validate the Job in the database
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeCreate + 1);
        Job testJob = jobs.get(jobs.size() - 1);
        assertThat(testJob.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJob.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testJob.getMinimumSalary()).isEqualTo(DEFAULT_MINIMUM_SALARY);
        assertThat(testJob.getMaximumSalary()).isEqualTo(DEFAULT_MAXIMUM_SALARY);
        assertThat(testJob.getVacancy()).isEqualTo(DEFAULT_VACANCY);
        assertThat(testJob.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJob.getBenefits()).isEqualTo(DEFAULT_BENEFITS);
        assertThat(testJob.getEducationRequirements()).isEqualTo(DEFAULT_EDUCATION_REQUIREMENTS);
        assertThat(testJob.getExperienceRequirements()).isEqualTo(DEFAULT_EXPERIENCE_REQUIREMENTS);
        assertThat(testJob.getOtherRequirements()).isEqualTo(DEFAULT_OTHER_REQUIREMENTS);
        assertThat(testJob.getPublishedAt()).isEqualTo(DEFAULT_PUBLISHED_AT);
        assertThat(testJob.getApplicationDeadline()).isEqualTo(DEFAULT_APPLICATION_DEADLINE);
        assertThat(testJob.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testJob.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setTitle(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isBadRequest());

        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVacancyIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setVacancy(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isBadRequest());

        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setDescription(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isBadRequest());

        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEducationRequirementsIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setEducationRequirements(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isBadRequest());

        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobs() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobs
        restJobMockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(job.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].minimumSalary").value(hasItem(DEFAULT_MINIMUM_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].maximumSalary").value(hasItem(DEFAULT_MAXIMUM_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].vacancy").value(hasItem(DEFAULT_VACANCY)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].benefits").value(hasItem(DEFAULT_BENEFITS.toString())))
                .andExpect(jsonPath("$.[*].educationRequirements").value(hasItem(DEFAULT_EDUCATION_REQUIREMENTS.toString())))
                .andExpect(jsonPath("$.[*].experienceRequirements").value(hasItem(DEFAULT_EXPERIENCE_REQUIREMENTS.toString())))
                .andExpect(jsonPath("$.[*].otherRequirements").value(hasItem(DEFAULT_OTHER_REQUIREMENTS.toString())))
                .andExpect(jsonPath("$.[*].publishedAt").value(hasItem(DEFAULT_PUBLISHED_AT.toString())))
                .andExpect(jsonPath("$.[*].applicationDeadline").value(hasItem(DEFAULT_APPLICATION_DEADLINE.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", job.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(job.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.minimumSalary").value(DEFAULT_MINIMUM_SALARY.intValue()))
            .andExpect(jsonPath("$.maximumSalary").value(DEFAULT_MAXIMUM_SALARY.intValue()))
            .andExpect(jsonPath("$.vacancy").value(DEFAULT_VACANCY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.benefits").value(DEFAULT_BENEFITS.toString()))
            .andExpect(jsonPath("$.educationRequirements").value(DEFAULT_EDUCATION_REQUIREMENTS.toString()))
            .andExpect(jsonPath("$.experienceRequirements").value(DEFAULT_EXPERIENCE_REQUIREMENTS.toString()))
            .andExpect(jsonPath("$.otherRequirements").value(DEFAULT_OTHER_REQUIREMENTS.toString()))
            .andExpect(jsonPath("$.publishedAt").value(DEFAULT_PUBLISHED_AT.toString()))
            .andExpect(jsonPath("$.applicationDeadline").value(DEFAULT_APPLICATION_DEADLINE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJob() throws Exception {
        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

		int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the job
        job.setTitle(UPDATED_TITLE);
        job.setType(UPDATED_TYPE);
        job.setMinimumSalary(UPDATED_MINIMUM_SALARY);
        job.setMaximumSalary(UPDATED_MAXIMUM_SALARY);
        job.setVacancy(UPDATED_VACANCY);
        job.setDescription(UPDATED_DESCRIPTION);
        job.setBenefits(UPDATED_BENEFITS);
        job.setEducationRequirements(UPDATED_EDUCATION_REQUIREMENTS);
        job.setExperienceRequirements(UPDATED_EXPERIENCE_REQUIREMENTS);
        job.setOtherRequirements(UPDATED_OTHER_REQUIREMENTS);
        job.setPublishedAt(UPDATED_PUBLISHED_AT);
        job.setApplicationDeadline(UPDATED_APPLICATION_DEADLINE);
        job.setLocation(UPDATED_LOCATION);
        job.setStatus(UPDATED_STATUS);

        restJobMockMvc.perform(put("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isOk());

        // Validate the Job in the database
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeUpdate);
        Job testJob = jobs.get(jobs.size() - 1);
        assertThat(testJob.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJob.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testJob.getMinimumSalary()).isEqualTo(UPDATED_MINIMUM_SALARY);
        assertThat(testJob.getMaximumSalary()).isEqualTo(UPDATED_MAXIMUM_SALARY);
        assertThat(testJob.getVacancy()).isEqualTo(UPDATED_VACANCY);
        assertThat(testJob.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJob.getBenefits()).isEqualTo(UPDATED_BENEFITS);
        assertThat(testJob.getEducationRequirements()).isEqualTo(UPDATED_EDUCATION_REQUIREMENTS);
        assertThat(testJob.getExperienceRequirements()).isEqualTo(UPDATED_EXPERIENCE_REQUIREMENTS);
        assertThat(testJob.getOtherRequirements()).isEqualTo(UPDATED_OTHER_REQUIREMENTS);
        assertThat(testJob.getPublishedAt()).isEqualTo(UPDATED_PUBLISHED_AT);
        assertThat(testJob.getApplicationDeadline()).isEqualTo(UPDATED_APPLICATION_DEADLINE);
        assertThat(testJob.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testJob.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

		int databaseSizeBeforeDelete = jobRepository.findAll().size();

        // Get the job
        restJobMockMvc.perform(delete("/api/jobs/{id}", job.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
