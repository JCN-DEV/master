package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlUllimConfig;
import gov.step.app.repository.DlUllimConfigRepository;
import gov.step.app.repository.search.DlUllimConfigSearchRepository;

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
 * Test class for the DlUllimConfigResource REST controller.
 *
 * @see DlUllimConfigResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlUllimConfigResourceTest {

    private static final String DEFAULT_FILE_TYPE = "AAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBB";

    private static final Integer DEFAULT_LIMIT_MB = 1;
    private static final Integer UPDATED_LIMIT_MB = 2;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private DlUllimConfigRepository dlUllimConfigRepository;

    @Inject
    private DlUllimConfigSearchRepository dlUllimConfigSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlUllimConfigMockMvc;

    private DlUllimConfig dlUllimConfig;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlUllimConfigResource dlUllimConfigResource = new DlUllimConfigResource();
        ReflectionTestUtils.setField(dlUllimConfigResource, "dlUllimConfigRepository", dlUllimConfigRepository);
        ReflectionTestUtils.setField(dlUllimConfigResource, "dlUllimConfigSearchRepository", dlUllimConfigSearchRepository);
        this.restDlUllimConfigMockMvc = MockMvcBuilders.standaloneSetup(dlUllimConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlUllimConfig = new DlUllimConfig();
        dlUllimConfig.setFileType(DEFAULT_FILE_TYPE);
        dlUllimConfig.setLimitMb(DEFAULT_LIMIT_MB);
        dlUllimConfig.setCreatedDate(DEFAULT_CREATED_DATE);
        dlUllimConfig.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlUllimConfig.setCreatedBy(DEFAULT_CREATED_BY);
        dlUllimConfig.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlUllimConfig.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlUllimConfig() throws Exception {
        int databaseSizeBeforeCreate = dlUllimConfigRepository.findAll().size();

        // Create the DlUllimConfig

        restDlUllimConfigMockMvc.perform(post("/api/dlUllimConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlUllimConfig)))
                .andExpect(status().isCreated());

        // Validate the DlUllimConfig in the database
        List<DlUllimConfig> dlUllimConfigs = dlUllimConfigRepository.findAll();
        assertThat(dlUllimConfigs).hasSize(databaseSizeBeforeCreate + 1);
        DlUllimConfig testDlUllimConfig = dlUllimConfigs.get(dlUllimConfigs.size() - 1);
        assertThat(testDlUllimConfig.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testDlUllimConfig.getLimitMb()).isEqualTo(DEFAULT_LIMIT_MB);
        assertThat(testDlUllimConfig.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlUllimConfig.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlUllimConfig.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlUllimConfig.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlUllimConfig.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlUllimConfigs() throws Exception {
        // Initialize the database
        dlUllimConfigRepository.saveAndFlush(dlUllimConfig);

        // Get all the dlUllimConfigs
        restDlUllimConfigMockMvc.perform(get("/api/dlUllimConfigs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlUllimConfig.getId().intValue())))
                .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].limitMb").value(hasItem(DEFAULT_LIMIT_MB)))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlUllimConfig() throws Exception {
        // Initialize the database
        dlUllimConfigRepository.saveAndFlush(dlUllimConfig);

        // Get the dlUllimConfig
        restDlUllimConfigMockMvc.perform(get("/api/dlUllimConfigs/{id}", dlUllimConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlUllimConfig.getId().intValue()))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()))
            .andExpect(jsonPath("$.limitMb").value(DEFAULT_LIMIT_MB))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlUllimConfig() throws Exception {
        // Get the dlUllimConfig
        restDlUllimConfigMockMvc.perform(get("/api/dlUllimConfigs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlUllimConfig() throws Exception {
        // Initialize the database
        dlUllimConfigRepository.saveAndFlush(dlUllimConfig);

		int databaseSizeBeforeUpdate = dlUllimConfigRepository.findAll().size();

        // Update the dlUllimConfig
        dlUllimConfig.setFileType(UPDATED_FILE_TYPE);
        dlUllimConfig.setLimitMb(UPDATED_LIMIT_MB);
        dlUllimConfig.setCreatedDate(UPDATED_CREATED_DATE);
        dlUllimConfig.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlUllimConfig.setCreatedBy(UPDATED_CREATED_BY);
        dlUllimConfig.setUpdatedBy(UPDATED_UPDATED_BY);
        dlUllimConfig.setStatus(UPDATED_STATUS);

        restDlUllimConfigMockMvc.perform(put("/api/dlUllimConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlUllimConfig)))
                .andExpect(status().isOk());

        // Validate the DlUllimConfig in the database
        List<DlUllimConfig> dlUllimConfigs = dlUllimConfigRepository.findAll();
        assertThat(dlUllimConfigs).hasSize(databaseSizeBeforeUpdate);
        DlUllimConfig testDlUllimConfig = dlUllimConfigs.get(dlUllimConfigs.size() - 1);
        assertThat(testDlUllimConfig.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testDlUllimConfig.getLimitMb()).isEqualTo(UPDATED_LIMIT_MB);
        assertThat(testDlUllimConfig.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlUllimConfig.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlUllimConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlUllimConfig.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlUllimConfig.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlUllimConfig() throws Exception {
        // Initialize the database
        dlUllimConfigRepository.saveAndFlush(dlUllimConfig);

		int databaseSizeBeforeDelete = dlUllimConfigRepository.findAll().size();

        // Get the dlUllimConfig
        restDlUllimConfigMockMvc.perform(delete("/api/dlUllimConfigs/{id}", dlUllimConfig.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlUllimConfig> dlUllimConfigs = dlUllimConfigRepository.findAll();
        assertThat(dlUllimConfigs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
