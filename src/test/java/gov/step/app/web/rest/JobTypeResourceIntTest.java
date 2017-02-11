package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JobType;
import gov.step.app.repository.JobTypeRepository;
import gov.step.app.repository.search.JobTypeSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the JobTypeResource REST controller.
 *
 * @see JobTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private JobTypeRepository jobTypeRepository;

    @Inject
    private JobTypeSearchRepository jobTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJobTypeMockMvc;

    private JobType jobType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobTypeResource jobTypeResource = new JobTypeResource();
        ReflectionTestUtils.setField(jobTypeResource, "jobTypeRepository", jobTypeRepository);
        ReflectionTestUtils.setField(jobTypeResource, "jobTypeSearchRepository", jobTypeSearchRepository);
        this.restJobTypeMockMvc = MockMvcBuilders.standaloneSetup(jobTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jobType = new JobType();
        jobType.setName(DEFAULT_NAME);
        jobType.setDescription(DEFAULT_DESCRIPTION);
        jobType.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createJobType() throws Exception {
        int databaseSizeBeforeCreate = jobTypeRepository.findAll().size();

        // Create the JobType

        restJobTypeMockMvc.perform(post("/api/jobTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobType)))
                .andExpect(status().isCreated());

        // Validate the JobType in the database
        List<JobType> jobTypes = jobTypeRepository.findAll();
        assertThat(jobTypes).hasSize(databaseSizeBeforeCreate + 1);
        JobType testJobType = jobTypes.get(jobTypes.size() - 1);
        assertThat(testJobType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobTypeRepository.findAll().size();
        // set the field null
        jobType.setName(null);

        // Create the JobType, which fails.

        restJobTypeMockMvc.perform(post("/api/jobTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobType)))
                .andExpect(status().isBadRequest());

        List<JobType> jobTypes = jobTypeRepository.findAll();
        assertThat(jobTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobTypeRepository.findAll().size();
        // set the field null
        jobType.setDescription(null);

        // Create the JobType, which fails.

        restJobTypeMockMvc.perform(post("/api/jobTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobType)))
                .andExpect(status().isBadRequest());

        List<JobType> jobTypes = jobTypeRepository.findAll();
        assertThat(jobTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobTypes() throws Exception {
        // Initialize the database
        jobTypeRepository.saveAndFlush(jobType);

        // Get all the jobTypes
        restJobTypeMockMvc.perform(get("/api/jobTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jobType.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getJobType() throws Exception {
        // Initialize the database
        jobTypeRepository.saveAndFlush(jobType);

        // Get the jobType
        restJobTypeMockMvc.perform(get("/api/jobTypes/{id}", jobType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jobType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJobType() throws Exception {
        // Get the jobType
        restJobTypeMockMvc.perform(get("/api/jobTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobType() throws Exception {
        // Initialize the database
        jobTypeRepository.saveAndFlush(jobType);

		int databaseSizeBeforeUpdate = jobTypeRepository.findAll().size();

        // Update the jobType
        jobType.setName(UPDATED_NAME);
        jobType.setDescription(UPDATED_DESCRIPTION);
        jobType.setStatus(UPDATED_STATUS);

        restJobTypeMockMvc.perform(put("/api/jobTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobType)))
                .andExpect(status().isOk());

        // Validate the JobType in the database
        List<JobType> jobTypes = jobTypeRepository.findAll();
        assertThat(jobTypes).hasSize(databaseSizeBeforeUpdate);
        JobType testJobType = jobTypes.get(jobTypes.size() - 1);
        assertThat(testJobType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteJobType() throws Exception {
        // Initialize the database
        jobTypeRepository.saveAndFlush(jobType);

		int databaseSizeBeforeDelete = jobTypeRepository.findAll().size();

        // Get the jobType
        restJobTypeMockMvc.perform(delete("/api/jobTypes/{id}", jobType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JobType> jobTypes = jobTypeRepository.findAll();
        assertThat(jobTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
