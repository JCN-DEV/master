package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AssetSupplier;
import gov.step.app.repository.AssetSupplierRepository;
import gov.step.app.repository.search.AssetSupplierSearchRepository;

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
 * Test class for the AssetSupplierResource REST controller.
 *
 * @see AssetSupplierResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AssetSupplierResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private AssetSupplierRepository assetSupplierRepository;

    @Inject
    private AssetSupplierSearchRepository assetSupplierSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAssetSupplierMockMvc;

    private AssetSupplier assetSupplier;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetSupplierResource assetSupplierResource = new AssetSupplierResource();
        ReflectionTestUtils.setField(assetSupplierResource, "assetSupplierRepository", assetSupplierRepository);
        ReflectionTestUtils.setField(assetSupplierResource, "assetSupplierSearchRepository", assetSupplierSearchRepository);
        this.restAssetSupplierMockMvc = MockMvcBuilders.standaloneSetup(assetSupplierResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        assetSupplier = new AssetSupplier();
        assetSupplier.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAssetSupplier() throws Exception {
        int databaseSizeBeforeCreate = assetSupplierRepository.findAll().size();

        // Create the AssetSupplier

        restAssetSupplierMockMvc.perform(post("/api/assetSuppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetSupplier)))
                .andExpect(status().isCreated());

        // Validate the AssetSupplier in the database
        List<AssetSupplier> assetSuppliers = assetSupplierRepository.findAll();
        assertThat(assetSuppliers).hasSize(databaseSizeBeforeCreate + 1);
        AssetSupplier testAssetSupplier = assetSuppliers.get(assetSuppliers.size() - 1);
        assertThat(testAssetSupplier.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllAssetSuppliers() throws Exception {
        // Initialize the database
        assetSupplierRepository.saveAndFlush(assetSupplier);

        // Get all the assetSuppliers
        restAssetSupplierMockMvc.perform(get("/api/assetSuppliers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assetSupplier.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAssetSupplier() throws Exception {
        // Initialize the database
        assetSupplierRepository.saveAndFlush(assetSupplier);

        // Get the assetSupplier
        restAssetSupplierMockMvc.perform(get("/api/assetSuppliers/{id}", assetSupplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(assetSupplier.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetSupplier() throws Exception {
        // Get the assetSupplier
        restAssetSupplierMockMvc.perform(get("/api/assetSuppliers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetSupplier() throws Exception {
        // Initialize the database
        assetSupplierRepository.saveAndFlush(assetSupplier);

		int databaseSizeBeforeUpdate = assetSupplierRepository.findAll().size();

        // Update the assetSupplier
        assetSupplier.setName(UPDATED_NAME);

        restAssetSupplierMockMvc.perform(put("/api/assetSuppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetSupplier)))
                .andExpect(status().isOk());

        // Validate the AssetSupplier in the database
        List<AssetSupplier> assetSuppliers = assetSupplierRepository.findAll();
        assertThat(assetSuppliers).hasSize(databaseSizeBeforeUpdate);
        AssetSupplier testAssetSupplier = assetSuppliers.get(assetSuppliers.size() - 1);
        assertThat(testAssetSupplier.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteAssetSupplier() throws Exception {
        // Initialize the database
        assetSupplierRepository.saveAndFlush(assetSupplier);

		int databaseSizeBeforeDelete = assetSupplierRepository.findAll().size();

        // Get the assetSupplier
        restAssetSupplierMockMvc.perform(delete("/api/assetSuppliers/{id}", assetSupplier.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetSupplier> assetSuppliers = assetSupplierRepository.findAll();
        assertThat(assetSuppliers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
