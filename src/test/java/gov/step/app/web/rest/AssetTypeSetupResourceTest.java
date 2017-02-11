package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AssetTypeSetup;
import gov.step.app.repository.AssetTypeSetupRepository;
import gov.step.app.repository.search.AssetTypeSetupSearchRepository;

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
 * Test class for the AssetTypeSetupResource REST controller.
 *
 * @see AssetTypeSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AssetTypeSetupResourceTest {

    private static final String DEFAULT_TYPE_NAME = "AAAAA";
    private static final String UPDATED_TYPE_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private AssetTypeSetupRepository assetTypeSetupRepository;

    @Inject
    private AssetTypeSetupSearchRepository assetTypeSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAssetTypeSetupMockMvc;

    private AssetTypeSetup assetTypeSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetTypeSetupResource assetTypeSetupResource = new AssetTypeSetupResource();
        ReflectionTestUtils.setField(assetTypeSetupResource, "assetTypeSetupRepository", assetTypeSetupRepository);
        ReflectionTestUtils.setField(assetTypeSetupResource, "assetTypeSetupSearchRepository", assetTypeSetupSearchRepository);
        this.restAssetTypeSetupMockMvc = MockMvcBuilders.standaloneSetup(assetTypeSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        assetTypeSetup = new AssetTypeSetup();
        assetTypeSetup.setTypeName(DEFAULT_TYPE_NAME);
        assetTypeSetup.setDescription(DEFAULT_DESCRIPTION);
        assetTypeSetup.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAssetTypeSetup() throws Exception {
        int databaseSizeBeforeCreate = assetTypeSetupRepository.findAll().size();

        // Create the AssetTypeSetup

        restAssetTypeSetupMockMvc.perform(post("/api/assetTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetTypeSetup)))
                .andExpect(status().isCreated());

        // Validate the AssetTypeSetup in the database
        List<AssetTypeSetup> assetTypeSetups = assetTypeSetupRepository.findAll();
        assertThat(assetTypeSetups).hasSize(databaseSizeBeforeCreate + 1);
        AssetTypeSetup testAssetTypeSetup = assetTypeSetups.get(assetTypeSetups.size() - 1);
        assertThat(testAssetTypeSetup.getTypeName()).isEqualTo(DEFAULT_TYPE_NAME);
        assertThat(testAssetTypeSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssetTypeSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetTypeSetupRepository.findAll().size();
        // set the field null
        assetTypeSetup.setTypeName(null);

        // Create the AssetTypeSetup, which fails.

        restAssetTypeSetupMockMvc.perform(post("/api/assetTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetTypeSetup)))
                .andExpect(status().isBadRequest());

        List<AssetTypeSetup> assetTypeSetups = assetTypeSetupRepository.findAll();
        assertThat(assetTypeSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetTypeSetupRepository.findAll().size();
        // set the field null
        assetTypeSetup.setDescription(null);

        // Create the AssetTypeSetup, which fails.

        restAssetTypeSetupMockMvc.perform(post("/api/assetTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetTypeSetup)))
                .andExpect(status().isBadRequest());

        List<AssetTypeSetup> assetTypeSetups = assetTypeSetupRepository.findAll();
        assertThat(assetTypeSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetTypeSetups() throws Exception {
        // Initialize the database
        assetTypeSetupRepository.saveAndFlush(assetTypeSetup);

        // Get all the assetTypeSetups
        restAssetTypeSetupMockMvc.perform(get("/api/assetTypeSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assetTypeSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getAssetTypeSetup() throws Exception {
        // Initialize the database
        assetTypeSetupRepository.saveAndFlush(assetTypeSetup);

        // Get the assetTypeSetup
        restAssetTypeSetupMockMvc.perform(get("/api/assetTypeSetups/{id}", assetTypeSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(assetTypeSetup.getId().intValue()))
            .andExpect(jsonPath("$.typeName").value(DEFAULT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetTypeSetup() throws Exception {
        // Get the assetTypeSetup
        restAssetTypeSetupMockMvc.perform(get("/api/assetTypeSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetTypeSetup() throws Exception {
        // Initialize the database
        assetTypeSetupRepository.saveAndFlush(assetTypeSetup);

		int databaseSizeBeforeUpdate = assetTypeSetupRepository.findAll().size();

        // Update the assetTypeSetup
        assetTypeSetup.setTypeName(UPDATED_TYPE_NAME);
        assetTypeSetup.setDescription(UPDATED_DESCRIPTION);
        assetTypeSetup.setStatus(UPDATED_STATUS);

        restAssetTypeSetupMockMvc.perform(put("/api/assetTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetTypeSetup)))
                .andExpect(status().isOk());

        // Validate the AssetTypeSetup in the database
        List<AssetTypeSetup> assetTypeSetups = assetTypeSetupRepository.findAll();
        assertThat(assetTypeSetups).hasSize(databaseSizeBeforeUpdate);
        AssetTypeSetup testAssetTypeSetup = assetTypeSetups.get(assetTypeSetups.size() - 1);
        assertThat(testAssetTypeSetup.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testAssetTypeSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetTypeSetup.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteAssetTypeSetup() throws Exception {
        // Initialize the database
        assetTypeSetupRepository.saveAndFlush(assetTypeSetup);

		int databaseSizeBeforeDelete = assetTypeSetupRepository.findAll().size();

        // Get the assetTypeSetup
        restAssetTypeSetupMockMvc.perform(delete("/api/assetTypeSetups/{id}", assetTypeSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetTypeSetup> assetTypeSetups = assetTypeSetupRepository.findAll();
        assertThat(assetTypeSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
