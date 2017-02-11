package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JpExperienceCategory;
import gov.step.app.repository.JpExperienceCategoryRepository;
import gov.step.app.repository.search.JpExperienceCategorySearchRepository;

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
 * Test class for the JpExperienceCategoryResource REST controller.
 *
 * @see JpExperienceCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JpExperienceCategoryResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private JpExperienceCategoryRepository jpExperienceCategoryRepository;

    @Inject
    private JpExperienceCategorySearchRepository jpExperienceCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJpExperienceCategoryMockMvc;

    private JpExperienceCategory jpExperienceCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpExperienceCategoryResource jpExperienceCategoryResource = new JpExperienceCategoryResource();
        ReflectionTestUtils.setField(jpExperienceCategoryResource, "jpExperienceCategoryRepository", jpExperienceCategoryRepository);
        ReflectionTestUtils.setField(jpExperienceCategoryResource, "jpExperienceCategorySearchRepository", jpExperienceCategorySearchRepository);
        this.restJpExperienceCategoryMockMvc = MockMvcBuilders.standaloneSetup(jpExperienceCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jpExperienceCategory = new JpExperienceCategory();
        jpExperienceCategory.setName(DEFAULT_NAME);
        jpExperienceCategory.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createJpExperienceCategory() throws Exception {
        int databaseSizeBeforeCreate = jpExperienceCategoryRepository.findAll().size();

        // Create the JpExperienceCategory

        restJpExperienceCategoryMockMvc.perform(post("/api/jpExperienceCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpExperienceCategory)))
                .andExpect(status().isCreated());

        // Validate the JpExperienceCategory in the database
        List<JpExperienceCategory> jpExperienceCategorys = jpExperienceCategoryRepository.findAll();
        assertThat(jpExperienceCategorys).hasSize(databaseSizeBeforeCreate + 1);
        JpExperienceCategory testJpExperienceCategory = jpExperienceCategorys.get(jpExperienceCategorys.size() - 1);
        assertThat(testJpExperienceCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJpExperienceCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpExperienceCategoryRepository.findAll().size();
        // set the field null
        jpExperienceCategory.setName(null);

        // Create the JpExperienceCategory, which fails.

        restJpExperienceCategoryMockMvc.perform(post("/api/jpExperienceCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpExperienceCategory)))
                .andExpect(status().isBadRequest());

        List<JpExperienceCategory> jpExperienceCategorys = jpExperienceCategoryRepository.findAll();
        assertThat(jpExperienceCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJpExperienceCategorys() throws Exception {
        // Initialize the database
        jpExperienceCategoryRepository.saveAndFlush(jpExperienceCategory);

        // Get all the jpExperienceCategorys
        restJpExperienceCategoryMockMvc.perform(get("/api/jpExperienceCategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jpExperienceCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getJpExperienceCategory() throws Exception {
        // Initialize the database
        jpExperienceCategoryRepository.saveAndFlush(jpExperienceCategory);

        // Get the jpExperienceCategory
        restJpExperienceCategoryMockMvc.perform(get("/api/jpExperienceCategorys/{id}", jpExperienceCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jpExperienceCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJpExperienceCategory() throws Exception {
        // Get the jpExperienceCategory
        restJpExperienceCategoryMockMvc.perform(get("/api/jpExperienceCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpExperienceCategory() throws Exception {
        // Initialize the database
        jpExperienceCategoryRepository.saveAndFlush(jpExperienceCategory);

		int databaseSizeBeforeUpdate = jpExperienceCategoryRepository.findAll().size();

        // Update the jpExperienceCategory
        jpExperienceCategory.setName(UPDATED_NAME);
        jpExperienceCategory.setDescription(UPDATED_DESCRIPTION);

        restJpExperienceCategoryMockMvc.perform(put("/api/jpExperienceCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpExperienceCategory)))
                .andExpect(status().isOk());

        // Validate the JpExperienceCategory in the database
        List<JpExperienceCategory> jpExperienceCategorys = jpExperienceCategoryRepository.findAll();
        assertThat(jpExperienceCategorys).hasSize(databaseSizeBeforeUpdate);
        JpExperienceCategory testJpExperienceCategory = jpExperienceCategorys.get(jpExperienceCategorys.size() - 1);
        assertThat(testJpExperienceCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJpExperienceCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteJpExperienceCategory() throws Exception {
        // Initialize the database
        jpExperienceCategoryRepository.saveAndFlush(jpExperienceCategory);

		int databaseSizeBeforeDelete = jpExperienceCategoryRepository.findAll().size();

        // Get the jpExperienceCategory
        restJpExperienceCategoryMockMvc.perform(delete("/api/jpExperienceCategorys/{id}", jpExperienceCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JpExperienceCategory> jpExperienceCategorys = jpExperienceCategoryRepository.findAll();
        assertThat(jpExperienceCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
