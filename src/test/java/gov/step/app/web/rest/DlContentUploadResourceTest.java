package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlContentUpload;
import gov.step.app.repository.DlContentUploadRepository;
import gov.step.app.repository.search.DlContentUploadSearchRepository;

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

import gov.step.app.domain.enumeration.UserType;

/**
 * Test class for the DlContentUploadResource REST controller.
 *
 * @see DlContentUploadResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlContentUploadResourceTest {



private static final UserType DEFAULT_USER_TYPE = UserType.Student;
    private static final UserType UPDATED_USER_TYPE = UserType.Teacher;
    private static final String DEFAULT_CONTENT_NAME = "AAAAA";
    private static final String UPDATED_CONTENT_NAME = "BBBBB";
    private static final String DEFAULT_EDITION = "AAAAA";
    private static final String UPDATED_EDITION = "BBBBB";
    private static final String DEFAULT_AUTHOR_NAME = "AAAAA";
    private static final String UPDATED_AUTHOR_NAME = "BBBBB";
    private static final String DEFAULT_ISBN_NO = "AAAAA";
    private static final String UPDATED_ISBN_NO = "BBBBB";
    private static final String DEFAULT_PUBLISHER_NAME = "AAAAA";
    private static final String UPDATED_PUBLISHER_NAME = "BBBBB";
    private static final String DEFAULT_COPYRIGHT = "AAAAA";
    private static final String UPDATED_COPYRIGHT = "BBBBB";

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
    private DlContentUploadRepository dlContentUploadRepository;

    @Inject
    private DlContentUploadSearchRepository dlContentUploadSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlContentUploadMockMvc;

    private DlContentUpload dlContentUpload;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlContentUploadResource dlContentUploadResource = new DlContentUploadResource();
        ReflectionTestUtils.setField(dlContentUploadResource, "dlContentUploadRepository", dlContentUploadRepository);
        ReflectionTestUtils.setField(dlContentUploadResource, "dlContentUploadSearchRepository", dlContentUploadSearchRepository);
        this.restDlContentUploadMockMvc = MockMvcBuilders.standaloneSetup(dlContentUploadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlContentUpload = new DlContentUpload();
        dlContentUpload.setUserType(DEFAULT_USER_TYPE);
        dlContentUpload.setContentName(DEFAULT_CONTENT_NAME);
        dlContentUpload.setEdition(DEFAULT_EDITION);
        dlContentUpload.setAuthorName(DEFAULT_AUTHOR_NAME);
        dlContentUpload.setIsbnNo(DEFAULT_ISBN_NO);
        dlContentUpload.setPublisherName(DEFAULT_PUBLISHER_NAME);
        dlContentUpload.setCopyright(DEFAULT_COPYRIGHT);
        dlContentUpload.setCreatedDate(DEFAULT_CREATED_DATE);
        dlContentUpload.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlContentUpload.setCreatedBy(DEFAULT_CREATED_BY);
        dlContentUpload.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlContentUpload.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlContentUpload() throws Exception {
        int databaseSizeBeforeCreate = dlContentUploadRepository.findAll().size();

        // Create the DlContentUpload

        restDlContentUploadMockMvc.perform(post("/api/dlContentUploads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentUpload)))
                .andExpect(status().isCreated());

        // Validate the DlContentUpload in the database
        List<DlContentUpload> dlContentUploads = dlContentUploadRepository.findAll();
        assertThat(dlContentUploads).hasSize(databaseSizeBeforeCreate + 1);
        DlContentUpload testDlContentUpload = dlContentUploads.get(dlContentUploads.size() - 1);
        assertThat(testDlContentUpload.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testDlContentUpload.getContentName()).isEqualTo(DEFAULT_CONTENT_NAME);
        assertThat(testDlContentUpload.getEdition()).isEqualTo(DEFAULT_EDITION);
        assertThat(testDlContentUpload.getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME);
        assertThat(testDlContentUpload.getIsbnNo()).isEqualTo(DEFAULT_ISBN_NO);
        assertThat(testDlContentUpload.getPublisherName()).isEqualTo(DEFAULT_PUBLISHER_NAME);
        assertThat(testDlContentUpload.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testDlContentUpload.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlContentUpload.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlContentUpload.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlContentUpload.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlContentUpload.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkContentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlContentUploadRepository.findAll().size();
        // set the field null
        dlContentUpload.setContentName(null);

        // Create the DlContentUpload, which fails.

        restDlContentUploadMockMvc.perform(post("/api/dlContentUploads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentUpload)))
                .andExpect(status().isBadRequest());

        List<DlContentUpload> dlContentUploads = dlContentUploadRepository.findAll();
        assertThat(dlContentUploads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlContentUploadRepository.findAll().size();
        // set the field null
        dlContentUpload.setEdition(null);

        // Create the DlContentUpload, which fails.

        restDlContentUploadMockMvc.perform(post("/api/dlContentUploads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentUpload)))
                .andExpect(status().isBadRequest());

        List<DlContentUpload> dlContentUploads = dlContentUploadRepository.findAll();
        assertThat(dlContentUploads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuthorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlContentUploadRepository.findAll().size();
        // set the field null
        dlContentUpload.setAuthorName(null);

        // Create the DlContentUpload, which fails.

        restDlContentUploadMockMvc.perform(post("/api/dlContentUploads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentUpload)))
                .andExpect(status().isBadRequest());

        List<DlContentUpload> dlContentUploads = dlContentUploadRepository.findAll();
        assertThat(dlContentUploads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsbnNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlContentUploadRepository.findAll().size();
        // set the field null
        dlContentUpload.setIsbnNo(null);

        // Create the DlContentUpload, which fails.

        restDlContentUploadMockMvc.perform(post("/api/dlContentUploads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentUpload)))
                .andExpect(status().isBadRequest());

        List<DlContentUpload> dlContentUploads = dlContentUploadRepository.findAll();
        assertThat(dlContentUploads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublisherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlContentUploadRepository.findAll().size();
        // set the field null
        dlContentUpload.setPublisherName(null);

        // Create the DlContentUpload, which fails.

        restDlContentUploadMockMvc.perform(post("/api/dlContentUploads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentUpload)))
                .andExpect(status().isBadRequest());

        List<DlContentUpload> dlContentUploads = dlContentUploadRepository.findAll();
        assertThat(dlContentUploads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCopyrightIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlContentUploadRepository.findAll().size();
        // set the field null
        dlContentUpload.setCopyright(null);

        // Create the DlContentUpload, which fails.

        restDlContentUploadMockMvc.perform(post("/api/dlContentUploads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentUpload)))
                .andExpect(status().isBadRequest());

        List<DlContentUpload> dlContentUploads = dlContentUploadRepository.findAll();
        assertThat(dlContentUploads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDlContentUploads() throws Exception {
        // Initialize the database
        dlContentUploadRepository.saveAndFlush(dlContentUpload);

        // Get all the dlContentUploads
        restDlContentUploadMockMvc.perform(get("/api/dlContentUploads"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlContentUpload.getId().intValue())))
                .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
                .andExpect(jsonPath("$.[*].contentName").value(hasItem(DEFAULT_CONTENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION.toString())))
                .andExpect(jsonPath("$.[*].authorName").value(hasItem(DEFAULT_AUTHOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].isbnNo").value(hasItem(DEFAULT_ISBN_NO.toString())))
                .andExpect(jsonPath("$.[*].publisherName").value(hasItem(DEFAULT_PUBLISHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlContentUpload() throws Exception {
        // Initialize the database
        dlContentUploadRepository.saveAndFlush(dlContentUpload);

        // Get the dlContentUpload
        restDlContentUploadMockMvc.perform(get("/api/dlContentUploads/{id}", dlContentUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlContentUpload.getId().intValue()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.contentName").value(DEFAULT_CONTENT_NAME.toString()))
            .andExpect(jsonPath("$.edition").value(DEFAULT_EDITION.toString()))
            .andExpect(jsonPath("$.authorName").value(DEFAULT_AUTHOR_NAME.toString()))
            .andExpect(jsonPath("$.isbnNo").value(DEFAULT_ISBN_NO.toString()))
            .andExpect(jsonPath("$.publisherName").value(DEFAULT_PUBLISHER_NAME.toString()))
            .andExpect(jsonPath("$.copyright").value(DEFAULT_COPYRIGHT.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlContentUpload() throws Exception {
        // Get the dlContentUpload
        restDlContentUploadMockMvc.perform(get("/api/dlContentUploads/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlContentUpload() throws Exception {
        // Initialize the database
        dlContentUploadRepository.saveAndFlush(dlContentUpload);

		int databaseSizeBeforeUpdate = dlContentUploadRepository.findAll().size();

        // Update the dlContentUpload
        dlContentUpload.setUserType(UPDATED_USER_TYPE);
        dlContentUpload.setContentName(UPDATED_CONTENT_NAME);
        dlContentUpload.setEdition(UPDATED_EDITION);
        dlContentUpload.setAuthorName(UPDATED_AUTHOR_NAME);
        dlContentUpload.setIsbnNo(UPDATED_ISBN_NO);
        dlContentUpload.setPublisherName(UPDATED_PUBLISHER_NAME);
        dlContentUpload.setCopyright(UPDATED_COPYRIGHT);
        dlContentUpload.setCreatedDate(UPDATED_CREATED_DATE);
        dlContentUpload.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlContentUpload.setCreatedBy(UPDATED_CREATED_BY);
        dlContentUpload.setUpdatedBy(UPDATED_UPDATED_BY);
        dlContentUpload.setStatus(UPDATED_STATUS);

        restDlContentUploadMockMvc.perform(put("/api/dlContentUploads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentUpload)))
                .andExpect(status().isOk());

        // Validate the DlContentUpload in the database
        List<DlContentUpload> dlContentUploads = dlContentUploadRepository.findAll();
        assertThat(dlContentUploads).hasSize(databaseSizeBeforeUpdate);
        DlContentUpload testDlContentUpload = dlContentUploads.get(dlContentUploads.size() - 1);
        assertThat(testDlContentUpload.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testDlContentUpload.getContentName()).isEqualTo(UPDATED_CONTENT_NAME);
        assertThat(testDlContentUpload.getEdition()).isEqualTo(UPDATED_EDITION);
        assertThat(testDlContentUpload.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testDlContentUpload.getIsbnNo()).isEqualTo(UPDATED_ISBN_NO);
        assertThat(testDlContentUpload.getPublisherName()).isEqualTo(UPDATED_PUBLISHER_NAME);
        assertThat(testDlContentUpload.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testDlContentUpload.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlContentUpload.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlContentUpload.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlContentUpload.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlContentUpload.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlContentUpload() throws Exception {
        // Initialize the database
        dlContentUploadRepository.saveAndFlush(dlContentUpload);

		int databaseSizeBeforeDelete = dlContentUploadRepository.findAll().size();

        // Get the dlContentUpload
        restDlContentUploadMockMvc.perform(delete("/api/dlContentUploads/{id}", dlContentUpload.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlContentUpload> dlContentUploads = dlContentUploadRepository.findAll();
        assertThat(dlContentUploads).hasSize(databaseSizeBeforeDelete - 1);
    }
}
