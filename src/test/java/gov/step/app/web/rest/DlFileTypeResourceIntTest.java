package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlFileType;
import gov.step.app.repository.DlFileTypeRepository;
import gov.step.app.repository.search.DlFileTypeSearchRepository;

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
 * Test class for the DlFileTypeResource REST controller.
 *
 * @see DlFileTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlFileTypeResourceIntTest {

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
    private DlFileTypeRepository dlFileTypeRepository;

    @Inject
    private DlFileTypeSearchRepository dlFileTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlFileTypeMockMvc;

    private DlFileType dlFileType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlFileTypeResource dlFileTypeResource = new DlFileTypeResource();
        ReflectionTestUtils.setField(dlFileTypeResource, "dlFileTypeRepository", dlFileTypeRepository);
        ReflectionTestUtils.setField(dlFileTypeResource, "dlFileTypeSearchRepository", dlFileTypeSearchRepository);
        this.restDlFileTypeMockMvc = MockMvcBuilders.standaloneSetup(dlFileTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlFileType = new DlFileType();
        dlFileType.setFileType(DEFAULT_FILE_TYPE);
        dlFileType.setLimitMb(DEFAULT_LIMIT_MB);
        dlFileType.setCreatedDate(DEFAULT_CREATED_DATE);
        dlFileType.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlFileType.setCreatedBy(DEFAULT_CREATED_BY);
        dlFileType.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlFileType.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlFileType() throws Exception {
        int databaseSizeBeforeCreate = dlFileTypeRepository.findAll().size();

        // Create the DlFileType

        restDlFileTypeMockMvc.perform(post("/api/dlFileTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlFileType)))
                .andExpect(status().isCreated());

        // Validate the DlFileType in the database
        List<DlFileType> dlFileTypes = dlFileTypeRepository.findAll();
        assertThat(dlFileTypes).hasSize(databaseSizeBeforeCreate + 1);
        DlFileType testDlFileType = dlFileTypes.get(dlFileTypes.size() - 1);
        assertThat(testDlFileType.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testDlFileType.getLimitMb()).isEqualTo(DEFAULT_LIMIT_MB);
        assertThat(testDlFileType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlFileType.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlFileType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlFileType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlFileType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlFileTypes() throws Exception {
        // Initialize the database
        dlFileTypeRepository.saveAndFlush(dlFileType);

        // Get all the dlFileTypes
        restDlFileTypeMockMvc.perform(get("/api/dlFileTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlFileType.getId().intValue())))
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
    public void getDlFileType() throws Exception {
        // Initialize the database
        dlFileTypeRepository.saveAndFlush(dlFileType);

        // Get the dlFileType
        restDlFileTypeMockMvc.perform(get("/api/dlFileTypes/{id}", dlFileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlFileType.getId().intValue()))
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
    public void getNonExistingDlFileType() throws Exception {
        // Get the dlFileType
        restDlFileTypeMockMvc.perform(get("/api/dlFileTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlFileType() throws Exception {
        // Initialize the database
        dlFileTypeRepository.saveAndFlush(dlFileType);

		int databaseSizeBeforeUpdate = dlFileTypeRepository.findAll().size();

        // Update the dlFileType
        dlFileType.setFileType(UPDATED_FILE_TYPE);
        dlFileType.setLimitMb(UPDATED_LIMIT_MB);
        dlFileType.setCreatedDate(UPDATED_CREATED_DATE);
        dlFileType.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlFileType.setCreatedBy(UPDATED_CREATED_BY);
        dlFileType.setUpdatedBy(UPDATED_UPDATED_BY);
        dlFileType.setStatus(UPDATED_STATUS);

        restDlFileTypeMockMvc.perform(put("/api/dlFileTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlFileType)))
                .andExpect(status().isOk());

        // Validate the DlFileType in the database
        List<DlFileType> dlFileTypes = dlFileTypeRepository.findAll();
        assertThat(dlFileTypes).hasSize(databaseSizeBeforeUpdate);
        DlFileType testDlFileType = dlFileTypes.get(dlFileTypes.size() - 1);
        assertThat(testDlFileType.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testDlFileType.getLimitMb()).isEqualTo(UPDATED_LIMIT_MB);
        assertThat(testDlFileType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlFileType.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlFileType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlFileType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlFileType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlFileType() throws Exception {
        // Initialize the database
        dlFileTypeRepository.saveAndFlush(dlFileType);

		int databaseSizeBeforeDelete = dlFileTypeRepository.findAll().size();

        // Get the dlFileType
        restDlFileTypeMockMvc.perform(delete("/api/dlFileTypes/{id}", dlFileType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlFileType> dlFileTypes = dlFileTypeRepository.findAll();
        assertThat(dlFileTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
