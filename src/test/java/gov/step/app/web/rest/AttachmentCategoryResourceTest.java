package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AttachmentCategory;
import gov.step.app.repository.AttachmentCategoryRepository;
import gov.step.app.repository.search.AttachmentCategorySearchRepository;

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
 * Test class for the AttachmentCategoryResource REST controller.
 *
 * @see AttachmentCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AttachmentCategoryResourceTest {

    private static final String DEFAULT_APPLICATION_NAME = "AAAAA";
    private static final String UPDATED_APPLICATION_NAME = "BBBBB";
    private static final String DEFAULT_ATTACHMENT_NAME = "AAAAA";
    private static final String UPDATED_ATTACHMENT_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private AttachmentCategoryRepository attachmentCategoryRepository;

    @Inject
    private AttachmentCategorySearchRepository attachmentCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAttachmentCategoryMockMvc;

    private AttachmentCategory attachmentCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AttachmentCategoryResource attachmentCategoryResource = new AttachmentCategoryResource();
        ReflectionTestUtils.setField(attachmentCategoryResource, "attachmentCategoryRepository", attachmentCategoryRepository);
        ReflectionTestUtils.setField(attachmentCategoryResource, "attachmentCategorySearchRepository", attachmentCategorySearchRepository);
        this.restAttachmentCategoryMockMvc = MockMvcBuilders.standaloneSetup(attachmentCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        attachmentCategory = new AttachmentCategory();
        attachmentCategory.setApplicationName(DEFAULT_APPLICATION_NAME);
        attachmentCategory.setAttachmentName(DEFAULT_ATTACHMENT_NAME);
    }

    @Test
    @Transactional
    public void createAttachmentCategory() throws Exception {
        int databaseSizeBeforeCreate = attachmentCategoryRepository.findAll().size();

        // Create the AttachmentCategory

        restAttachmentCategoryMockMvc.perform(post("/api/attachmentCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attachmentCategory)))
                .andExpect(status().isCreated());

        // Validate the AttachmentCategory in the database
        List<AttachmentCategory> attachmentCategorys = attachmentCategoryRepository.findAll();
        assertThat(attachmentCategorys).hasSize(databaseSizeBeforeCreate + 1);
        AttachmentCategory testAttachmentCategory = attachmentCategorys.get(attachmentCategorys.size() - 1);
        assertThat(testAttachmentCategory.getApplicationName()).isEqualTo(DEFAULT_APPLICATION_NAME);
        assertThat(testAttachmentCategory.getAttachmentName()).isEqualTo(DEFAULT_ATTACHMENT_NAME);
    }

    @Test
    @Transactional
    public void checkApplicationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentCategoryRepository.findAll().size();
        // set the field null
        attachmentCategory.setApplicationName(null);

        // Create the AttachmentCategory, which fails.

        restAttachmentCategoryMockMvc.perform(post("/api/attachmentCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attachmentCategory)))
                .andExpect(status().isBadRequest());

        List<AttachmentCategory> attachmentCategorys = attachmentCategoryRepository.findAll();
        assertThat(attachmentCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttachmentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentCategoryRepository.findAll().size();
        // set the field null
        attachmentCategory.setAttachmentName(null);

        // Create the AttachmentCategory, which fails.

        restAttachmentCategoryMockMvc.perform(post("/api/attachmentCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attachmentCategory)))
                .andExpect(status().isBadRequest());

        List<AttachmentCategory> attachmentCategorys = attachmentCategoryRepository.findAll();
        assertThat(attachmentCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentCategoryRepository.findAll().size();
        // set the field null

        // Create the AttachmentCategory, which fails.

        restAttachmentCategoryMockMvc.perform(post("/api/attachmentCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attachmentCategory)))
                .andExpect(status().isBadRequest());

        List<AttachmentCategory> attachmentCategorys = attachmentCategoryRepository.findAll();
        assertThat(attachmentCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttachmentCategorys() throws Exception {
        // Initialize the database
        attachmentCategoryRepository.saveAndFlush(attachmentCategory);

        // Get all the attachmentCategorys
        restAttachmentCategoryMockMvc.perform(get("/api/attachmentCategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(attachmentCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].applicationName").value(hasItem(DEFAULT_APPLICATION_NAME.toString())))
                .andExpect(jsonPath("$.[*].attachmentName").value(hasItem(DEFAULT_ATTACHMENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAttachmentCategory() throws Exception {
        // Initialize the database
        attachmentCategoryRepository.saveAndFlush(attachmentCategory);

        // Get the attachmentCategory
        restAttachmentCategoryMockMvc.perform(get("/api/attachmentCategorys/{id}", attachmentCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(attachmentCategory.getId().intValue()))
            .andExpect(jsonPath("$.applicationName").value(DEFAULT_APPLICATION_NAME.toString()))
            .andExpect(jsonPath("$.attachmentName").value(DEFAULT_ATTACHMENT_NAME.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAttachmentCategory() throws Exception {
        // Get the attachmentCategory
        restAttachmentCategoryMockMvc.perform(get("/api/attachmentCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttachmentCategory() throws Exception {
        // Initialize the database
        attachmentCategoryRepository.saveAndFlush(attachmentCategory);

		int databaseSizeBeforeUpdate = attachmentCategoryRepository.findAll().size();

        // Update the attachmentCategory
        attachmentCategory.setApplicationName(UPDATED_APPLICATION_NAME);
        attachmentCategory.setAttachmentName(UPDATED_ATTACHMENT_NAME);

        restAttachmentCategoryMockMvc.perform(put("/api/attachmentCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attachmentCategory)))
                .andExpect(status().isOk());

        // Validate the AttachmentCategory in the database
        List<AttachmentCategory> attachmentCategorys = attachmentCategoryRepository.findAll();
        assertThat(attachmentCategorys).hasSize(databaseSizeBeforeUpdate);
        AttachmentCategory testAttachmentCategory = attachmentCategorys.get(attachmentCategorys.size() - 1);
        assertThat(testAttachmentCategory.getApplicationName()).isEqualTo(UPDATED_APPLICATION_NAME);
        assertThat(testAttachmentCategory.getAttachmentName()).isEqualTo(UPDATED_ATTACHMENT_NAME);
    }

    @Test
    @Transactional
    public void deleteAttachmentCategory() throws Exception {
        // Initialize the database
        attachmentCategoryRepository.saveAndFlush(attachmentCategory);

		int databaseSizeBeforeDelete = attachmentCategoryRepository.findAll().size();

        // Get the attachmentCategory
        restAttachmentCategoryMockMvc.perform(delete("/api/attachmentCategorys/{id}", attachmentCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AttachmentCategory> attachmentCategorys = attachmentCategoryRepository.findAll();
        assertThat(attachmentCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
