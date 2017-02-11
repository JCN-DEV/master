package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JobPostingInfo;
import gov.step.app.repository.JobPostingInfoRepository;
import gov.step.app.repository.search.JobPostingInfoSearchRepository;

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
 * Test class for the JobPostingInfoResource REST controller.
 *
 * @see JobPostingInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobPostingInfoResourceIntTest {

    private static final String DEFAULT_JOB_POST_ID = "AAAAA";
    private static final String UPDATED_JOB_POST_ID = "BBBBB";
    private static final String DEFAULT_JOB_TITLE = "AAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBB";
    private static final String DEFAULT_ORGANIZATIN_NAME = "AAAAA";
    private static final String UPDATED_ORGANIZATIN_NAME = "BBBBB";
    private static final String DEFAULT_JOB_VACANCY = "AAAAA";
    private static final String UPDATED_JOB_VACANCY = "BBBBB";
    private static final String DEFAULT_SALARY_RANGE = "AAAAA";
    private static final String UPDATED_SALARY_RANGE = "BBBBB";
    private static final String DEFAULT_JOB_SOURCE = "AAAAA";
    private static final String UPDATED_JOB_SOURCE = "BBBBB";

    private static final LocalDate DEFAULT_PUBLISHED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLISHED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APPLICATION_DATE_LINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLICATION_DATE_LINE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_JOB_DESCRIPTION = "AAAAA";
    private static final String UPDATED_JOB_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_JOB_FILE_NAME = "AAAAA";
    private static final String UPDATED_JOB_FILE_NAME = "BBBBB";

    private static final byte[] DEFAULT_UPLOAD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_UPLOAD = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_UPLOAD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_UPLOAD_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private JobPostingInfoRepository jobPostingInfoRepository;

    @Inject
    private JobPostingInfoSearchRepository jobPostingInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJobPostingInfoMockMvc;

    private JobPostingInfo jobPostingInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobPostingInfoResource jobPostingInfoResource = new JobPostingInfoResource();
        ReflectionTestUtils.setField(jobPostingInfoResource, "jobPostingInfoRepository", jobPostingInfoRepository);
        ReflectionTestUtils.setField(jobPostingInfoResource, "jobPostingInfoSearchRepository", jobPostingInfoSearchRepository);
        this.restJobPostingInfoMockMvc = MockMvcBuilders.standaloneSetup(jobPostingInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jobPostingInfo = new JobPostingInfo();
        jobPostingInfo.setJobPostId(DEFAULT_JOB_POST_ID);
        jobPostingInfo.setJobTitle(DEFAULT_JOB_TITLE);
        jobPostingInfo.setOrganizatinName(DEFAULT_ORGANIZATIN_NAME);
        jobPostingInfo.setJobVacancy(DEFAULT_JOB_VACANCY);
        jobPostingInfo.setSalaryRange(DEFAULT_SALARY_RANGE);
        jobPostingInfo.setJobSource(DEFAULT_JOB_SOURCE);
        jobPostingInfo.setPublishedDate(DEFAULT_PUBLISHED_DATE);
        jobPostingInfo.setApplicationDateLine(DEFAULT_APPLICATION_DATE_LINE);
        jobPostingInfo.setJobDescription(DEFAULT_JOB_DESCRIPTION);
        jobPostingInfo.setJobFileName(DEFAULT_JOB_FILE_NAME);
        jobPostingInfo.setUpload(DEFAULT_UPLOAD);
        jobPostingInfo.setUploadContentType(DEFAULT_UPLOAD_CONTENT_TYPE);
        jobPostingInfo.setCreateDate(DEFAULT_CREATE_DATE);
        jobPostingInfo.setCreateBy(DEFAULT_CREATE_BY);
        jobPostingInfo.setUpdateBy(DEFAULT_UPDATE_BY);
        jobPostingInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createJobPostingInfo() throws Exception {
        int databaseSizeBeforeCreate = jobPostingInfoRepository.findAll().size();

        // Create the JobPostingInfo

        restJobPostingInfoMockMvc.perform(post("/api/jobPostingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobPostingInfo)))
                .andExpect(status().isCreated());

        // Validate the JobPostingInfo in the database
        List<JobPostingInfo> jobPostingInfos = jobPostingInfoRepository.findAll();
        assertThat(jobPostingInfos).hasSize(databaseSizeBeforeCreate + 1);
        JobPostingInfo testJobPostingInfo = jobPostingInfos.get(jobPostingInfos.size() - 1);
        assertThat(testJobPostingInfo.getJobPostId()).isEqualTo(DEFAULT_JOB_POST_ID);
        assertThat(testJobPostingInfo.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testJobPostingInfo.getOrganizatinName()).isEqualTo(DEFAULT_ORGANIZATIN_NAME);
        assertThat(testJobPostingInfo.getJobVacancy()).isEqualTo(DEFAULT_JOB_VACANCY);
        assertThat(testJobPostingInfo.getSalaryRange()).isEqualTo(DEFAULT_SALARY_RANGE);
        assertThat(testJobPostingInfo.getJobSource()).isEqualTo(DEFAULT_JOB_SOURCE);
        assertThat(testJobPostingInfo.getPublishedDate()).isEqualTo(DEFAULT_PUBLISHED_DATE);
        assertThat(testJobPostingInfo.getApplicationDateLine()).isEqualTo(DEFAULT_APPLICATION_DATE_LINE);
        assertThat(testJobPostingInfo.getJobDescription()).isEqualTo(DEFAULT_JOB_DESCRIPTION);
        assertThat(testJobPostingInfo.getJobFileName()).isEqualTo(DEFAULT_JOB_FILE_NAME);
        assertThat(testJobPostingInfo.getUpload()).isEqualTo(DEFAULT_UPLOAD);
        assertThat(testJobPostingInfo.getUploadContentType()).isEqualTo(DEFAULT_UPLOAD_CONTENT_TYPE);
        assertThat(testJobPostingInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testJobPostingInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testJobPostingInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testJobPostingInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllJobPostingInfos() throws Exception {
        // Initialize the database
        jobPostingInfoRepository.saveAndFlush(jobPostingInfo);

        // Get all the jobPostingInfos
        restJobPostingInfoMockMvc.perform(get("/api/jobPostingInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jobPostingInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].jobPostId").value(hasItem(DEFAULT_JOB_POST_ID.toString())))
                .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
                .andExpect(jsonPath("$.[*].organizatinName").value(hasItem(DEFAULT_ORGANIZATIN_NAME.toString())))
                .andExpect(jsonPath("$.[*].jobVacancy").value(hasItem(DEFAULT_JOB_VACANCY.toString())))
                .andExpect(jsonPath("$.[*].salaryRange").value(hasItem(DEFAULT_SALARY_RANGE.toString())))
                .andExpect(jsonPath("$.[*].jobSource").value(hasItem(DEFAULT_JOB_SOURCE.toString())))
                .andExpect(jsonPath("$.[*].publishedDate").value(hasItem(DEFAULT_PUBLISHED_DATE.toString())))
                .andExpect(jsonPath("$.[*].applicationDateLine").value(hasItem(DEFAULT_APPLICATION_DATE_LINE.toString())))
                .andExpect(jsonPath("$.[*].jobDescription").value(hasItem(DEFAULT_JOB_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].jobFileName").value(hasItem(DEFAULT_JOB_FILE_NAME.toString())))
                .andExpect(jsonPath("$.[*].uploadContentType").value(hasItem(DEFAULT_UPLOAD_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].upload").value(hasItem(Base64Utils.encodeToString(DEFAULT_UPLOAD))))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getJobPostingInfo() throws Exception {
        // Initialize the database
        jobPostingInfoRepository.saveAndFlush(jobPostingInfo);

        // Get the jobPostingInfo
        restJobPostingInfoMockMvc.perform(get("/api/jobPostingInfos/{id}", jobPostingInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jobPostingInfo.getId().intValue()))
            .andExpect(jsonPath("$.jobPostId").value(DEFAULT_JOB_POST_ID.toString()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
            .andExpect(jsonPath("$.organizatinName").value(DEFAULT_ORGANIZATIN_NAME.toString()))
            .andExpect(jsonPath("$.jobVacancy").value(DEFAULT_JOB_VACANCY.toString()))
            .andExpect(jsonPath("$.salaryRange").value(DEFAULT_SALARY_RANGE.toString()))
            .andExpect(jsonPath("$.jobSource").value(DEFAULT_JOB_SOURCE.toString()))
            .andExpect(jsonPath("$.publishedDate").value(DEFAULT_PUBLISHED_DATE.toString()))
            .andExpect(jsonPath("$.applicationDateLine").value(DEFAULT_APPLICATION_DATE_LINE.toString()))
            .andExpect(jsonPath("$.jobDescription").value(DEFAULT_JOB_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.jobFileName").value(DEFAULT_JOB_FILE_NAME.toString()))
            .andExpect(jsonPath("$.uploadContentType").value(DEFAULT_UPLOAD_CONTENT_TYPE))
            .andExpect(jsonPath("$.upload").value(Base64Utils.encodeToString(DEFAULT_UPLOAD)))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobPostingInfo() throws Exception {
        // Get the jobPostingInfo
        restJobPostingInfoMockMvc.perform(get("/api/jobPostingInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobPostingInfo() throws Exception {
        // Initialize the database
        jobPostingInfoRepository.saveAndFlush(jobPostingInfo);

		int databaseSizeBeforeUpdate = jobPostingInfoRepository.findAll().size();

        // Update the jobPostingInfo
        jobPostingInfo.setJobPostId(UPDATED_JOB_POST_ID);
        jobPostingInfo.setJobTitle(UPDATED_JOB_TITLE);
        jobPostingInfo.setOrganizatinName(UPDATED_ORGANIZATIN_NAME);
        jobPostingInfo.setJobVacancy(UPDATED_JOB_VACANCY);
        jobPostingInfo.setSalaryRange(UPDATED_SALARY_RANGE);
        jobPostingInfo.setJobSource(UPDATED_JOB_SOURCE);
        jobPostingInfo.setPublishedDate(UPDATED_PUBLISHED_DATE);
        jobPostingInfo.setApplicationDateLine(UPDATED_APPLICATION_DATE_LINE);
        jobPostingInfo.setJobDescription(UPDATED_JOB_DESCRIPTION);
        jobPostingInfo.setJobFileName(UPDATED_JOB_FILE_NAME);
        jobPostingInfo.setUpload(UPDATED_UPLOAD);
        jobPostingInfo.setUploadContentType(UPDATED_UPLOAD_CONTENT_TYPE);
        jobPostingInfo.setCreateDate(UPDATED_CREATE_DATE);
        jobPostingInfo.setCreateBy(UPDATED_CREATE_BY);
        jobPostingInfo.setUpdateBy(UPDATED_UPDATE_BY);
        jobPostingInfo.setUpdateDate(UPDATED_UPDATE_DATE);

        restJobPostingInfoMockMvc.perform(put("/api/jobPostingInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobPostingInfo)))
                .andExpect(status().isOk());

        // Validate the JobPostingInfo in the database
        List<JobPostingInfo> jobPostingInfos = jobPostingInfoRepository.findAll();
        assertThat(jobPostingInfos).hasSize(databaseSizeBeforeUpdate);
        JobPostingInfo testJobPostingInfo = jobPostingInfos.get(jobPostingInfos.size() - 1);
        assertThat(testJobPostingInfo.getJobPostId()).isEqualTo(UPDATED_JOB_POST_ID);
        assertThat(testJobPostingInfo.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testJobPostingInfo.getOrganizatinName()).isEqualTo(UPDATED_ORGANIZATIN_NAME);
        assertThat(testJobPostingInfo.getJobVacancy()).isEqualTo(UPDATED_JOB_VACANCY);
        assertThat(testJobPostingInfo.getSalaryRange()).isEqualTo(UPDATED_SALARY_RANGE);
        assertThat(testJobPostingInfo.getJobSource()).isEqualTo(UPDATED_JOB_SOURCE);
        assertThat(testJobPostingInfo.getPublishedDate()).isEqualTo(UPDATED_PUBLISHED_DATE);
        assertThat(testJobPostingInfo.getApplicationDateLine()).isEqualTo(UPDATED_APPLICATION_DATE_LINE);
        assertThat(testJobPostingInfo.getJobDescription()).isEqualTo(UPDATED_JOB_DESCRIPTION);
        assertThat(testJobPostingInfo.getJobFileName()).isEqualTo(UPDATED_JOB_FILE_NAME);
        assertThat(testJobPostingInfo.getUpload()).isEqualTo(UPDATED_UPLOAD);
        assertThat(testJobPostingInfo.getUploadContentType()).isEqualTo(UPDATED_UPLOAD_CONTENT_TYPE);
        assertThat(testJobPostingInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testJobPostingInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testJobPostingInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testJobPostingInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deleteJobPostingInfo() throws Exception {
        // Initialize the database
        jobPostingInfoRepository.saveAndFlush(jobPostingInfo);

		int databaseSizeBeforeDelete = jobPostingInfoRepository.findAll().size();

        // Get the jobPostingInfo
        restJobPostingInfoMockMvc.perform(delete("/api/jobPostingInfos/{id}", jobPostingInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JobPostingInfo> jobPostingInfos = jobPostingInfoRepository.findAll();
        assertThat(jobPostingInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
