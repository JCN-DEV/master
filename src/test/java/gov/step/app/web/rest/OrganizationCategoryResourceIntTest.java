package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.OrganizationCategory;
import gov.step.app.repository.OrganizationCategoryRepository;
import gov.step.app.repository.search.OrganizationCategorySearchRepository;

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
 * Test class for the OrganizationCategoryResource REST controller.
 *
 * @see OrganizationCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrganizationCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private OrganizationCategoryRepository organizationCategoryRepository;

    @Inject
    private OrganizationCategorySearchRepository organizationCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOrganizationCategoryMockMvc;

    private OrganizationCategory organizationCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrganizationCategoryResource organizationCategoryResource = new OrganizationCategoryResource();
        ReflectionTestUtils.setField(organizationCategoryResource, "organizationCategoryRepository", organizationCategoryRepository);
        ReflectionTestUtils.setField(organizationCategoryResource, "organizationCategorySearchRepository", organizationCategorySearchRepository);
        this.restOrganizationCategoryMockMvc = MockMvcBuilders.standaloneSetup(organizationCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        organizationCategory = new OrganizationCategory();
        organizationCategory.setName(DEFAULT_NAME);
        organizationCategory.setDescription(DEFAULT_DESCRIPTION);
        organizationCategory.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createOrganizationCategory() throws Exception {
        int databaseSizeBeforeCreate = organizationCategoryRepository.findAll().size();

        // Create the OrganizationCategory

        restOrganizationCategoryMockMvc.perform(post("/api/organizationCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organizationCategory)))
                .andExpect(status().isCreated());

        // Validate the OrganizationCategory in the database
        List<OrganizationCategory> organizationCategorys = organizationCategoryRepository.findAll();
        assertThat(organizationCategorys).hasSize(databaseSizeBeforeCreate + 1);
        OrganizationCategory testOrganizationCategory = organizationCategorys.get(organizationCategorys.size() - 1);
        assertThat(testOrganizationCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganizationCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganizationCategory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationCategoryRepository.findAll().size();
        // set the field null
        organizationCategory.setName(null);

        // Create the OrganizationCategory, which fails.

        restOrganizationCategoryMockMvc.perform(post("/api/organizationCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organizationCategory)))
                .andExpect(status().isBadRequest());

        List<OrganizationCategory> organizationCategorys = organizationCategoryRepository.findAll();
        assertThat(organizationCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationCategoryRepository.findAll().size();
        // set the field null
        organizationCategory.setDescription(null);

        // Create the OrganizationCategory, which fails.

        restOrganizationCategoryMockMvc.perform(post("/api/organizationCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organizationCategory)))
                .andExpect(status().isBadRequest());

        List<OrganizationCategory> organizationCategorys = organizationCategoryRepository.findAll();
        assertThat(organizationCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrganizationCategorys() throws Exception {
        // Initialize the database
        organizationCategoryRepository.saveAndFlush(organizationCategory);

        // Get all the organizationCategorys
        restOrganizationCategoryMockMvc.perform(get("/api/organizationCategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(organizationCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getOrganizationCategory() throws Exception {
        // Initialize the database
        organizationCategoryRepository.saveAndFlush(organizationCategory);

        // Get the organizationCategory
        restOrganizationCategoryMockMvc.perform(get("/api/organizationCategorys/{id}", organizationCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(organizationCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganizationCategory() throws Exception {
        // Get the organizationCategory
        restOrganizationCategoryMockMvc.perform(get("/api/organizationCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganizationCategory() throws Exception {
        // Initialize the database
        organizationCategoryRepository.saveAndFlush(organizationCategory);

		int databaseSizeBeforeUpdate = organizationCategoryRepository.findAll().size();

        // Update the organizationCategory
        organizationCategory.setName(UPDATED_NAME);
        organizationCategory.setDescription(UPDATED_DESCRIPTION);
        organizationCategory.setStatus(UPDATED_STATUS);

        restOrganizationCategoryMockMvc.perform(put("/api/organizationCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organizationCategory)))
                .andExpect(status().isOk());

        // Validate the OrganizationCategory in the database
        List<OrganizationCategory> organizationCategorys = organizationCategoryRepository.findAll();
        assertThat(organizationCategorys).hasSize(databaseSizeBeforeUpdate);
        OrganizationCategory testOrganizationCategory = organizationCategorys.get(organizationCategorys.size() - 1);
        assertThat(testOrganizationCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganizationCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganizationCategory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteOrganizationCategory() throws Exception {
        // Initialize the database
        organizationCategoryRepository.saveAndFlush(organizationCategory);

		int databaseSizeBeforeDelete = organizationCategoryRepository.findAll().size();

        // Get the organizationCategory
        restOrganizationCategoryMockMvc.perform(delete("/api/organizationCategorys/{id}", organizationCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrganizationCategory> organizationCategorys = organizationCategoryRepository.findAll();
        assertThat(organizationCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
