package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AssetDistribution;
import gov.step.app.repository.AssetDistributionRepository;
import gov.step.app.repository.search.AssetDistributionSearchRepository;

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
 * Test class for the AssetDistributionResource REST controller.
 *
 * @see AssetDistributionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AssetDistributionResourceTest {

    private static final String DEFAULT_TRANSFER_REF = "AAAAA";
    private static final String UPDATED_TRANSFER_REF = "BBBBB";

    private static final LocalDate DEFAULT_ASSIGNED_DDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ASSIGNED_DDATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARTKS = "AAAAA";
    private static final String UPDATED_REMARTKS = "BBBBB";

    @Inject
    private AssetDistributionRepository assetDistributionRepository;

    @Inject
    private AssetDistributionSearchRepository assetDistributionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAssetDistributionMockMvc;

    private AssetDistribution assetDistribution;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetDistributionResource assetDistributionResource = new AssetDistributionResource();
        ReflectionTestUtils.setField(assetDistributionResource, "assetDistributionRepository", assetDistributionRepository);
        ReflectionTestUtils.setField(assetDistributionResource, "assetDistributionSearchRepository", assetDistributionSearchRepository);
        this.restAssetDistributionMockMvc = MockMvcBuilders.standaloneSetup(assetDistributionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        assetDistribution = new AssetDistribution();
        assetDistribution.setTransferRef(DEFAULT_TRANSFER_REF);
        assetDistribution.setAssignedDdate(DEFAULT_ASSIGNED_DDATE);
        assetDistribution.setRemartks(DEFAULT_REMARTKS);
    }

    @Test
    @Transactional
    public void createAssetDistribution() throws Exception {
        int databaseSizeBeforeCreate = assetDistributionRepository.findAll().size();

        // Create the AssetDistribution

        restAssetDistributionMockMvc.perform(post("/api/assetDistributions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetDistribution)))
                .andExpect(status().isCreated());

        // Validate the AssetDistribution in the database
        List<AssetDistribution> assetDistributions = assetDistributionRepository.findAll();
        assertThat(assetDistributions).hasSize(databaseSizeBeforeCreate + 1);
        AssetDistribution testAssetDistribution = assetDistributions.get(assetDistributions.size() - 1);
        assertThat(testAssetDistribution.getTransferRef()).isEqualTo(DEFAULT_TRANSFER_REF);
        assertThat(testAssetDistribution.getAssignedDdate()).isEqualTo(DEFAULT_ASSIGNED_DDATE);
        assertThat(testAssetDistribution.getRemartks()).isEqualTo(DEFAULT_REMARTKS);
    }

    @Test
    @Transactional
    public void checkTransferRefIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetDistributionRepository.findAll().size();
        // set the field null
        assetDistribution.setTransferRef(null);

        // Create the AssetDistribution, which fails.

        restAssetDistributionMockMvc.perform(post("/api/assetDistributions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetDistribution)))
                .andExpect(status().isBadRequest());

        List<AssetDistribution> assetDistributions = assetDistributionRepository.findAll();
        assertThat(assetDistributions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRemartksIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetDistributionRepository.findAll().size();
        // set the field null
        assetDistribution.setRemartks(null);

        // Create the AssetDistribution, which fails.

        restAssetDistributionMockMvc.perform(post("/api/assetDistributions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetDistribution)))
                .andExpect(status().isBadRequest());

        List<AssetDistribution> assetDistributions = assetDistributionRepository.findAll();
        assertThat(assetDistributions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetDistributions() throws Exception {
        // Initialize the database
        assetDistributionRepository.saveAndFlush(assetDistribution);

        // Get all the assetDistributions
        restAssetDistributionMockMvc.perform(get("/api/assetDistributions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assetDistribution.getId().intValue())))
                .andExpect(jsonPath("$.[*].transferRef").value(hasItem(DEFAULT_TRANSFER_REF.toString())))
                .andExpect(jsonPath("$.[*].assignedDdate").value(hasItem(DEFAULT_ASSIGNED_DDATE.toString())))
                .andExpect(jsonPath("$.[*].remartks").value(hasItem(DEFAULT_REMARTKS.toString())));
    }

    @Test
    @Transactional
    public void getAssetDistribution() throws Exception {
        // Initialize the database
        assetDistributionRepository.saveAndFlush(assetDistribution);

        // Get the assetDistribution
        restAssetDistributionMockMvc.perform(get("/api/assetDistributions/{id}", assetDistribution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(assetDistribution.getId().intValue()))
            .andExpect(jsonPath("$.transferRef").value(DEFAULT_TRANSFER_REF.toString()))
            .andExpect(jsonPath("$.assignedDdate").value(DEFAULT_ASSIGNED_DDATE.toString()))
            .andExpect(jsonPath("$.remartks").value(DEFAULT_REMARTKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetDistribution() throws Exception {
        // Get the assetDistribution
        restAssetDistributionMockMvc.perform(get("/api/assetDistributions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetDistribution() throws Exception {
        // Initialize the database
        assetDistributionRepository.saveAndFlush(assetDistribution);

		int databaseSizeBeforeUpdate = assetDistributionRepository.findAll().size();

        // Update the assetDistribution
        assetDistribution.setTransferRef(UPDATED_TRANSFER_REF);
        assetDistribution.setAssignedDdate(UPDATED_ASSIGNED_DDATE);
        assetDistribution.setRemartks(UPDATED_REMARTKS);

        restAssetDistributionMockMvc.perform(put("/api/assetDistributions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetDistribution)))
                .andExpect(status().isOk());

        // Validate the AssetDistribution in the database
        List<AssetDistribution> assetDistributions = assetDistributionRepository.findAll();
        assertThat(assetDistributions).hasSize(databaseSizeBeforeUpdate);
        AssetDistribution testAssetDistribution = assetDistributions.get(assetDistributions.size() - 1);
        assertThat(testAssetDistribution.getTransferRef()).isEqualTo(UPDATED_TRANSFER_REF);
        assertThat(testAssetDistribution.getAssignedDdate()).isEqualTo(UPDATED_ASSIGNED_DDATE);
        assertThat(testAssetDistribution.getRemartks()).isEqualTo(UPDATED_REMARTKS);
    }

    @Test
    @Transactional
    public void deleteAssetDistribution() throws Exception {
        // Initialize the database
        assetDistributionRepository.saveAndFlush(assetDistribution);

		int databaseSizeBeforeDelete = assetDistributionRepository.findAll().size();

        // Get the assetDistribution
        restAssetDistributionMockMvc.perform(delete("/api/assetDistributions/{id}", assetDistribution.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetDistribution> assetDistributions = assetDistributionRepository.findAll();
        assertThat(assetDistributions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
