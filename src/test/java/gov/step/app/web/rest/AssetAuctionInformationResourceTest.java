package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AssetAuctionInformation;
import gov.step.app.repository.AssetAuctionInformationRepository;
import gov.step.app.repository.search.AssetAuctionInformationSearchRepository;

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
 * Test class for the AssetAuctionInformationResource REST controller.
 *
 * @see AssetAuctionInformationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AssetAuctionInformationResourceTest {


    private static final LocalDate DEFAULT_LAST_REPAIR_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_REPAIR_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_AUCTION_BEFORE = false;
    private static final Boolean UPDATED_IS_AUCTION_BEFORE = true;

    @Inject
    private AssetAuctionInformationRepository assetAuctionInformationRepository;

    @Inject
    private AssetAuctionInformationSearchRepository assetAuctionInformationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAssetAuctionInformationMockMvc;

    private AssetAuctionInformation assetAuctionInformation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetAuctionInformationResource assetAuctionInformationResource = new AssetAuctionInformationResource();
        ReflectionTestUtils.setField(assetAuctionInformationResource, "assetAuctionInformationRepository", assetAuctionInformationRepository);
        ReflectionTestUtils.setField(assetAuctionInformationResource, "assetAuctionInformationSearchRepository", assetAuctionInformationSearchRepository);
        this.restAssetAuctionInformationMockMvc = MockMvcBuilders.standaloneSetup(assetAuctionInformationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        assetAuctionInformation = new AssetAuctionInformation();
        assetAuctionInformation.setLastRepairDate(DEFAULT_LAST_REPAIR_DATE);
        assetAuctionInformation.setIsAuctionBefore(DEFAULT_IS_AUCTION_BEFORE);
    }

    @Test
    @Transactional
    public void createAssetAuctionInformation() throws Exception {
        int databaseSizeBeforeCreate = assetAuctionInformationRepository.findAll().size();

        // Create the AssetAuctionInformation

        restAssetAuctionInformationMockMvc.perform(post("/api/assetAuctionInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetAuctionInformation)))
                .andExpect(status().isCreated());

        // Validate the AssetAuctionInformation in the database
        List<AssetAuctionInformation> assetAuctionInformations = assetAuctionInformationRepository.findAll();
        assertThat(assetAuctionInformations).hasSize(databaseSizeBeforeCreate + 1);
        AssetAuctionInformation testAssetAuctionInformation = assetAuctionInformations.get(assetAuctionInformations.size() - 1);
        assertThat(testAssetAuctionInformation.getLastRepairDate()).isEqualTo(DEFAULT_LAST_REPAIR_DATE);
        assertThat(testAssetAuctionInformation.getIsAuctionBefore()).isEqualTo(DEFAULT_IS_AUCTION_BEFORE);
    }

    @Test
    @Transactional
    public void getAllAssetAuctionInformations() throws Exception {
        // Initialize the database
        assetAuctionInformationRepository.saveAndFlush(assetAuctionInformation);

        // Get all the assetAuctionInformations
        restAssetAuctionInformationMockMvc.perform(get("/api/assetAuctionInformations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assetAuctionInformation.getId().intValue())))
                .andExpect(jsonPath("$.[*].lastRepairDate").value(hasItem(DEFAULT_LAST_REPAIR_DATE.toString())))
                .andExpect(jsonPath("$.[*].isAuctionBefore").value(hasItem(DEFAULT_IS_AUCTION_BEFORE.booleanValue())));
    }

    @Test
    @Transactional
    public void getAssetAuctionInformation() throws Exception {
        // Initialize the database
        assetAuctionInformationRepository.saveAndFlush(assetAuctionInformation);

        // Get the assetAuctionInformation
        restAssetAuctionInformationMockMvc.perform(get("/api/assetAuctionInformations/{id}", assetAuctionInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(assetAuctionInformation.getId().intValue()))
            .andExpect(jsonPath("$.lastRepairDate").value(DEFAULT_LAST_REPAIR_DATE.toString()))
            .andExpect(jsonPath("$.isAuctionBefore").value(DEFAULT_IS_AUCTION_BEFORE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetAuctionInformation() throws Exception {
        // Get the assetAuctionInformation
        restAssetAuctionInformationMockMvc.perform(get("/api/assetAuctionInformations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetAuctionInformation() throws Exception {
        // Initialize the database
        assetAuctionInformationRepository.saveAndFlush(assetAuctionInformation);

		int databaseSizeBeforeUpdate = assetAuctionInformationRepository.findAll().size();

        // Update the assetAuctionInformation
        assetAuctionInformation.setLastRepairDate(UPDATED_LAST_REPAIR_DATE);
        assetAuctionInformation.setIsAuctionBefore(UPDATED_IS_AUCTION_BEFORE);

        restAssetAuctionInformationMockMvc.perform(put("/api/assetAuctionInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetAuctionInformation)))
                .andExpect(status().isOk());

        // Validate the AssetAuctionInformation in the database
        List<AssetAuctionInformation> assetAuctionInformations = assetAuctionInformationRepository.findAll();
        assertThat(assetAuctionInformations).hasSize(databaseSizeBeforeUpdate);
        AssetAuctionInformation testAssetAuctionInformation = assetAuctionInformations.get(assetAuctionInformations.size() - 1);
        assertThat(testAssetAuctionInformation.getLastRepairDate()).isEqualTo(UPDATED_LAST_REPAIR_DATE);
        assertThat(testAssetAuctionInformation.getIsAuctionBefore()).isEqualTo(UPDATED_IS_AUCTION_BEFORE);
    }

    @Test
    @Transactional
    public void deleteAssetAuctionInformation() throws Exception {
        // Initialize the database
        assetAuctionInformationRepository.saveAndFlush(assetAuctionInformation);

		int databaseSizeBeforeDelete = assetAuctionInformationRepository.findAll().size();

        // Get the assetAuctionInformation
        restAssetAuctionInformationMockMvc.perform(delete("/api/assetAuctionInformations/{id}", assetAuctionInformation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetAuctionInformation> assetAuctionInformations = assetAuctionInformationRepository.findAll();
        assertThat(assetAuctionInformations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
