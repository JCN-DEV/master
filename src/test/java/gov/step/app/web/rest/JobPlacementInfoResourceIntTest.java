package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JobPlacementInfo;
import gov.step.app.repository.JobPlacementInfoRepository;
import gov.step.app.repository.search.JobPlacementInfoSearchRepository;

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
 * Test class for the JobPlacementInfoResource REST controller.
 *
 * @see JobPlacementInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobPlacementInfoResourceIntTest {

    private static final String DEFAULT_JOB_ID = "AAAAA";
    private static final String UPDATED_JOB_ID = "BBBBB";
    private static final String DEFAULT_ORG_NAME = "AAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_DEPARTMENT = "AAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBB";
    private static final String DEFAULT_RESPONSIBILITY = "AAAAA";
    private static final String UPDATED_RESPONSIBILITY = "BBBBB";

    private static final LocalDate DEFAULT_WORK_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WORK_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_WORK_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WORK_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_CURR_WORK = false;
    private static final Boolean UPDATED_CURR_WORK = true;
    private static final String DEFAULT_EXPERIENCE = "AAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private JobPlacementInfoRepository jobPlacementInfoRepository;

    @Inject
    private JobPlacementInfoSearchRepository jobPlacementInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJobPlacementInfoMockMvc;

    private JobPlacementInfo jobPlacementInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobPlacementInfoResource jobPlacementInfoResource = new JobPlacementInfoResource();
        ReflectionTestUtils.setField(jobPlacementInfoResource, "jobPlacementInfoRepository", jobPlacementInfoRepository);
        ReflectionTestUtils.setField(jobPlacementInfoResource, "jobPlacementInfoSearchRepository", jobPlacementInfoSearchRepository);
        this.restJobPlacementInfoMockMvc = MockMvcBuilders.standaloneSetup(jobPlacementInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jobPlacementInfo = new JobPlacementInfo();
        jobPlacementInfo.setJobId(DEFAULT_JOB_ID);
        jobPlacementInfo.setOrgName(DEFAULT_ORG_NAME);
        jobPlacementInfo.setDescription(DEFAULT_DESCRIPTION);
        jobPlacementInfo.setAddress(DEFAULT_ADDRESS);
        jobPlacementInfo.setDesignation(DEFAULT_DESIGNATION);
        jobPlacementInfo.setDepartment(DEFAULT_DEPARTMENT);
        jobPlacementInfo.setResponsibility(DEFAULT_RESPONSIBILITY);
        jobPlacementInfo.setWorkFrom(DEFAULT_WORK_FROM);
        jobPlacementInfo.setWorkTo(DEFAULT_WORK_TO);
        jobPlacementInfo.setCurrWork(DEFAULT_CURR_WORK);
        jobPlacementInfo.setExperience(DEFAULT_EXPERIENCE);
        jobPlacementInfo.setCreateDate(DEFAULT_CREATE_DATE);
        jobPlacementInfo.setCreateBy(DEFAULT_CREATE_BY);
        jobPlacementInfo.setUpdateBy(DEFAULT_UPDATE_BY);
        jobPlacementInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createJobPlacementInfo() throws Exception {
        int databaseSizeBeforeCreate = jobPlacementInfoRepository.findAll().size();

        // Create the JobPlacementInfo

        restJobPlacementInfoMockMvc.perform(post("/api/jobPlacementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobPlacementInfo)))
                .andExpect(status().isCreated());

        // Validate the JobPlacementInfo in the database
        List<JobPlacementInfo> jobPlacementInfos = jobPlacementInfoRepository.findAll();
        assertThat(jobPlacementInfos).hasSize(databaseSizeBeforeCreate + 1);
        JobPlacementInfo testJobPlacementInfo = jobPlacementInfos.get(jobPlacementInfos.size() - 1);
        assertThat(testJobPlacementInfo.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJobPlacementInfo.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testJobPlacementInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobPlacementInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testJobPlacementInfo.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testJobPlacementInfo.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testJobPlacementInfo.getResponsibility()).isEqualTo(DEFAULT_RESPONSIBILITY);
        assertThat(testJobPlacementInfo.getWorkFrom()).isEqualTo(DEFAULT_WORK_FROM);
        assertThat(testJobPlacementInfo.getWorkTo()).isEqualTo(DEFAULT_WORK_TO);
        assertThat(testJobPlacementInfo.getCurrWork()).isEqualTo(DEFAULT_CURR_WORK);
        assertThat(testJobPlacementInfo.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testJobPlacementInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testJobPlacementInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testJobPlacementInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testJobPlacementInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllJobPlacementInfos() throws Exception {
        // Initialize the database
        jobPlacementInfoRepository.saveAndFlush(jobPlacementInfo);

        // Get all the jobPlacementInfos
        restJobPlacementInfoMockMvc.perform(get("/api/jobPlacementInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jobPlacementInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.toString())))
                .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
                .andExpect(jsonPath("$.[*].responsibility").value(hasItem(DEFAULT_RESPONSIBILITY.toString())))
                .andExpect(jsonPath("$.[*].workFrom").value(hasItem(DEFAULT_WORK_FROM.toString())))
                .andExpect(jsonPath("$.[*].workTo").value(hasItem(DEFAULT_WORK_TO.toString())))
                .andExpect(jsonPath("$.[*].currWork").value(hasItem(DEFAULT_CURR_WORK.booleanValue())))
                .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getJobPlacementInfo() throws Exception {
        // Initialize the database
        jobPlacementInfoRepository.saveAndFlush(jobPlacementInfo);

        // Get the jobPlacementInfo
        restJobPlacementInfoMockMvc.perform(get("/api/jobPlacementInfos/{id}", jobPlacementInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jobPlacementInfo.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.toString()))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.responsibility").value(DEFAULT_RESPONSIBILITY.toString()))
            .andExpect(jsonPath("$.workFrom").value(DEFAULT_WORK_FROM.toString()))
            .andExpect(jsonPath("$.workTo").value(DEFAULT_WORK_TO.toString()))
            .andExpect(jsonPath("$.currWork").value(DEFAULT_CURR_WORK.booleanValue()))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobPlacementInfo() throws Exception {
        // Get the jobPlacementInfo
        restJobPlacementInfoMockMvc.perform(get("/api/jobPlacementInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobPlacementInfo() throws Exception {
        // Initialize the database
        jobPlacementInfoRepository.saveAndFlush(jobPlacementInfo);

		int databaseSizeBeforeUpdate = jobPlacementInfoRepository.findAll().size();

        // Update the jobPlacementInfo
        jobPlacementInfo.setJobId(UPDATED_JOB_ID);
        jobPlacementInfo.setOrgName(UPDATED_ORG_NAME);
        jobPlacementInfo.setDescription(UPDATED_DESCRIPTION);
        jobPlacementInfo.setAddress(UPDATED_ADDRESS);
        jobPlacementInfo.setDesignation(UPDATED_DESIGNATION);
        jobPlacementInfo.setDepartment(UPDATED_DEPARTMENT);
        jobPlacementInfo.setResponsibility(UPDATED_RESPONSIBILITY);
        jobPlacementInfo.setWorkFrom(UPDATED_WORK_FROM);
        jobPlacementInfo.setWorkTo(UPDATED_WORK_TO);
        jobPlacementInfo.setCurrWork(UPDATED_CURR_WORK);
        jobPlacementInfo.setExperience(UPDATED_EXPERIENCE);
        jobPlacementInfo.setCreateDate(UPDATED_CREATE_DATE);
        jobPlacementInfo.setCreateBy(UPDATED_CREATE_BY);
        jobPlacementInfo.setUpdateBy(UPDATED_UPDATE_BY);
        jobPlacementInfo.setUpdateDate(UPDATED_UPDATE_DATE);

        restJobPlacementInfoMockMvc.perform(put("/api/jobPlacementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobPlacementInfo)))
                .andExpect(status().isOk());

        // Validate the JobPlacementInfo in the database
        List<JobPlacementInfo> jobPlacementInfos = jobPlacementInfoRepository.findAll();
        assertThat(jobPlacementInfos).hasSize(databaseSizeBeforeUpdate);
        JobPlacementInfo testJobPlacementInfo = jobPlacementInfos.get(jobPlacementInfos.size() - 1);
        assertThat(testJobPlacementInfo.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobPlacementInfo.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testJobPlacementInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobPlacementInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testJobPlacementInfo.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testJobPlacementInfo.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testJobPlacementInfo.getResponsibility()).isEqualTo(UPDATED_RESPONSIBILITY);
        assertThat(testJobPlacementInfo.getWorkFrom()).isEqualTo(UPDATED_WORK_FROM);
        assertThat(testJobPlacementInfo.getWorkTo()).isEqualTo(UPDATED_WORK_TO);
        assertThat(testJobPlacementInfo.getCurrWork()).isEqualTo(UPDATED_CURR_WORK);
        assertThat(testJobPlacementInfo.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testJobPlacementInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testJobPlacementInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testJobPlacementInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testJobPlacementInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deleteJobPlacementInfo() throws Exception {
        // Initialize the database
        jobPlacementInfoRepository.saveAndFlush(jobPlacementInfo);

		int databaseSizeBeforeDelete = jobPlacementInfoRepository.findAll().size();

        // Get the jobPlacementInfo
        restJobPlacementInfoMockMvc.perform(delete("/api/jobPlacementInfos/{id}", jobPlacementInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JobPlacementInfo> jobPlacementInfos = jobPlacementInfoRepository.findAll();
        assertThat(jobPlacementInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
