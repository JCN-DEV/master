package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlContentSetup;
import gov.step.app.repository.DlContentSetupRepository;
import gov.step.app.repository.search.DlContentSetupSearchRepository;

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
 * Test class for the DlContentSetupResource REST controller.
 *
 * @see DlContentSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlContentSetupResourceTest {

    private static final String DEFAULT_CONTENT_NAME = "AAAAA";
    private static final String UPDATED_CONTENT_NAME = "BBBBB";
    private static final String DEFAULT_CONTENT_TYPE = "AAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

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
    private DlContentSetupRepository dlContentSetupRepository;

    @Inject
    private DlContentSetupSearchRepository dlContentSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlContentSetupMockMvc;

    private DlContentSetup dlContentSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlContentSetupResource dlContentSetupResource = new DlContentSetupResource();
        ReflectionTestUtils.setField(dlContentSetupResource, "dlContentSetupRepository", dlContentSetupRepository);
        ReflectionTestUtils.setField(dlContentSetupResource, "dlContentSetupSearchRepository", dlContentSetupSearchRepository);
        this.restDlContentSetupMockMvc = MockMvcBuilders.standaloneSetup(dlContentSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlContentSetup = new DlContentSetup();
        dlContentSetup.setContentName(DEFAULT_CONTENT_NAME);
        dlContentSetup.setContentType(DEFAULT_CONTENT_TYPE);
        dlContentSetup.setDescription(DEFAULT_DESCRIPTION);
        dlContentSetup.setCreatedDate(DEFAULT_CREATED_DATE);
        dlContentSetup.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlContentSetup.setCreatedBy(DEFAULT_CREATED_BY);
        dlContentSetup.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlContentSetup.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlContentSetup() throws Exception {
        int databaseSizeBeforeCreate = dlContentSetupRepository.findAll().size();

        // Create the DlContentSetup

        restDlContentSetupMockMvc.perform(post("/api/dlContentSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentSetup)))
                .andExpect(status().isCreated());

        // Validate the DlContentSetup in the database
        List<DlContentSetup> dlContentSetups = dlContentSetupRepository.findAll();
        assertThat(dlContentSetups).hasSize(databaseSizeBeforeCreate + 1);
        DlContentSetup testDlContentSetup = dlContentSetups.get(dlContentSetups.size() - 1);
        assertThat(testDlContentSetup.getContentName()).isEqualTo(DEFAULT_CONTENT_NAME);
        assertThat(testDlContentSetup.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testDlContentSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDlContentSetup.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlContentSetup.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlContentSetup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlContentSetup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlContentSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkContentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlContentSetupRepository.findAll().size();
        // set the field null
        dlContentSetup.setContentName(null);

        // Create the DlContentSetup, which fails.

        restDlContentSetupMockMvc.perform(post("/api/dlContentSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentSetup)))
                .andExpect(status().isBadRequest());

        List<DlContentSetup> dlContentSetups = dlContentSetupRepository.findAll();
        assertThat(dlContentSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlContentSetupRepository.findAll().size();
        // set the field null
        dlContentSetup.setContentType(null);

        // Create the DlContentSetup, which fails.

        restDlContentSetupMockMvc.perform(post("/api/dlContentSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentSetup)))
                .andExpect(status().isBadRequest());

        List<DlContentSetup> dlContentSetups = dlContentSetupRepository.findAll();
        assertThat(dlContentSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlContentSetupRepository.findAll().size();
        // set the field null
        dlContentSetup.setDescription(null);

        // Create the DlContentSetup, which fails.

        restDlContentSetupMockMvc.perform(post("/api/dlContentSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentSetup)))
                .andExpect(status().isBadRequest());

        List<DlContentSetup> dlContentSetups = dlContentSetupRepository.findAll();
        assertThat(dlContentSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDlContentSetups() throws Exception {
        // Initialize the database
        dlContentSetupRepository.saveAndFlush(dlContentSetup);

        // Get all the dlContentSetups
        restDlContentSetupMockMvc.perform(get("/api/dlContentSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlContentSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].contentName").value(hasItem(DEFAULT_CONTENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlContentSetup() throws Exception {
        // Initialize the database
        dlContentSetupRepository.saveAndFlush(dlContentSetup);

        // Get the dlContentSetup
        restDlContentSetupMockMvc.perform(get("/api/dlContentSetups/{id}", dlContentSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlContentSetup.getId().intValue()))
            .andExpect(jsonPath("$.contentName").value(DEFAULT_CONTENT_NAME.toString()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlContentSetup() throws Exception {
        // Get the dlContentSetup
        restDlContentSetupMockMvc.perform(get("/api/dlContentSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlContentSetup() throws Exception {
        // Initialize the database
        dlContentSetupRepository.saveAndFlush(dlContentSetup);

		int databaseSizeBeforeUpdate = dlContentSetupRepository.findAll().size();

        // Update the dlContentSetup
        dlContentSetup.setContentName(UPDATED_CONTENT_NAME);
        dlContentSetup.setContentType(UPDATED_CONTENT_TYPE);
        dlContentSetup.setDescription(UPDATED_DESCRIPTION);
        dlContentSetup.setCreatedDate(UPDATED_CREATED_DATE);
        dlContentSetup.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlContentSetup.setCreatedBy(UPDATED_CREATED_BY);
        dlContentSetup.setUpdatedBy(UPDATED_UPDATED_BY);
        dlContentSetup.setStatus(UPDATED_STATUS);

        restDlContentSetupMockMvc.perform(put("/api/dlContentSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContentSetup)))
                .andExpect(status().isOk());

        // Validate the DlContentSetup in the database
        List<DlContentSetup> dlContentSetups = dlContentSetupRepository.findAll();
        assertThat(dlContentSetups).hasSize(databaseSizeBeforeUpdate);
        DlContentSetup testDlContentSetup = dlContentSetups.get(dlContentSetups.size() - 1);
        assertThat(testDlContentSetup.getContentName()).isEqualTo(UPDATED_CONTENT_NAME);
        assertThat(testDlContentSetup.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testDlContentSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDlContentSetup.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlContentSetup.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlContentSetup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlContentSetup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlContentSetup.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlContentSetup() throws Exception {
        // Initialize the database
        dlContentSetupRepository.saveAndFlush(dlContentSetup);

		int databaseSizeBeforeDelete = dlContentSetupRepository.findAll().size();

        // Get the dlContentSetup
        restDlContentSetupMockMvc.perform(delete("/api/dlContentSetups/{id}", dlContentSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlContentSetup> dlContentSetups = dlContentSetupRepository.findAll();
        assertThat(dlContentSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
