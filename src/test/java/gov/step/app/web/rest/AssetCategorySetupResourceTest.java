package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AssetCategorySetup;
import gov.step.app.repository.AssetCategorySetupRepository;
import gov.step.app.repository.search.AssetCategorySetupSearchRepository;

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
 * Test class for the AssetCategorySetupResource REST controller.
 *
 * @see AssetCategorySetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AssetCategorySetupResourceTest {

    private static final String DEFAULT_CATEGORY_NAME = "AAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private AssetCategorySetupRepository assetCategorySetupRepository;

    @Inject
    private AssetCategorySetupSearchRepository assetCategorySetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAssetCategorySetupMockMvc;

    private AssetCategorySetup assetCategorySetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetCategorySetupResource assetCategorySetupResource = new AssetCategorySetupResource();
        ReflectionTestUtils.setField(assetCategorySetupResource, "assetCategorySetupRepository", assetCategorySetupRepository);
        ReflectionTestUtils.setField(assetCategorySetupResource, "assetCategorySetupSearchRepository", assetCategorySetupSearchRepository);
        this.restAssetCategorySetupMockMvc = MockMvcBuilders.standaloneSetup(assetCategorySetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        assetCategorySetup = new AssetCategorySetup();
        assetCategorySetup.setCategoryName(DEFAULT_CATEGORY_NAME);
        assetCategorySetup.setDescription(DEFAULT_DESCRIPTION);
        assetCategorySetup.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAssetCategorySetup() throws Exception {
        int databaseSizeBeforeCreate = assetCategorySetupRepository.findAll().size();

        // Create the AssetCategorySetup

        restAssetCategorySetupMockMvc.perform(post("/api/assetCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetCategorySetup)))
                .andExpect(status().isCreated());

        // Validate the AssetCategorySetup in the database
        List<AssetCategorySetup> assetCategorySetups = assetCategorySetupRepository.findAll();
        assertThat(assetCategorySetups).hasSize(databaseSizeBeforeCreate + 1);
        AssetCategorySetup testAssetCategorySetup = assetCategorySetups.get(assetCategorySetups.size() - 1);
        assertThat(testAssetCategorySetup.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testAssetCategorySetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssetCategorySetup.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetCategorySetupRepository.findAll().size();
        // set the field null
        assetCategorySetup.setCategoryName(null);

        // Create the AssetCategorySetup, which fails.

        restAssetCategorySetupMockMvc.perform(post("/api/assetCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetCategorySetup)))
                .andExpect(status().isBadRequest());

        List<AssetCategorySetup> assetCategorySetups = assetCategorySetupRepository.findAll();
        assertThat(assetCategorySetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetCategorySetupRepository.findAll().size();
        // set the field null
        assetCategorySetup.setDescription(null);

        // Create the AssetCategorySetup, which fails.

        restAssetCategorySetupMockMvc.perform(post("/api/assetCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetCategorySetup)))
                .andExpect(status().isBadRequest());

        List<AssetCategorySetup> assetCategorySetups = assetCategorySetupRepository.findAll();
        assertThat(assetCategorySetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetCategorySetups() throws Exception {
        // Initialize the database
        assetCategorySetupRepository.saveAndFlush(assetCategorySetup);

        // Get all the assetCategorySetups
        restAssetCategorySetupMockMvc.perform(get("/api/assetCategorySetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assetCategorySetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getAssetCategorySetup() throws Exception {
        // Initialize the database
        assetCategorySetupRepository.saveAndFlush(assetCategorySetup);

        // Get the assetCategorySetup
        restAssetCategorySetupMockMvc.perform(get("/api/assetCategorySetups/{id}", assetCategorySetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(assetCategorySetup.getId().intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetCategorySetup() throws Exception {
        // Get the assetCategorySetup
        restAssetCategorySetupMockMvc.perform(get("/api/assetCategorySetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetCategorySetup() throws Exception {
        // Initialize the database
        assetCategorySetupRepository.saveAndFlush(assetCategorySetup);

		int databaseSizeBeforeUpdate = assetCategorySetupRepository.findAll().size();

        // Update the assetCategorySetup
        assetCategorySetup.setCategoryName(UPDATED_CATEGORY_NAME);
        assetCategorySetup.setDescription(UPDATED_DESCRIPTION);
        assetCategorySetup.setStatus(UPDATED_STATUS);

        restAssetCategorySetupMockMvc.perform(put("/api/assetCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetCategorySetup)))
                .andExpect(status().isOk());

        // Validate the AssetCategorySetup in the database
        List<AssetCategorySetup> assetCategorySetups = assetCategorySetupRepository.findAll();
        assertThat(assetCategorySetups).hasSize(databaseSizeBeforeUpdate);
        AssetCategorySetup testAssetCategorySetup = assetCategorySetups.get(assetCategorySetups.size() - 1);
        assertThat(testAssetCategorySetup.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testAssetCategorySetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetCategorySetup.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteAssetCategorySetup() throws Exception {
        // Initialize the database
        assetCategorySetupRepository.saveAndFlush(assetCategorySetup);

		int databaseSizeBeforeDelete = assetCategorySetupRepository.findAll().size();

        // Get the assetCategorySetup
        restAssetCategorySetupMockMvc.perform(delete("/api/assetCategorySetups/{id}", assetCategorySetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetCategorySetup> assetCategorySetups = assetCategorySetupRepository.findAll();
        assertThat(assetCategorySetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
