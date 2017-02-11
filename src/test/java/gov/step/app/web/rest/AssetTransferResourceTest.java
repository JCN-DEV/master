package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AssetTransfer;
import gov.step.app.repository.AssetTransferRepository;
import gov.step.app.repository.search.AssetTransferSearchRepository;

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
 * Test class for the AssetTransferResource REST controller.
 *
 * @see AssetTransferResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AssetTransferResourceTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private AssetTransferRepository assetTransferRepository;

    @Inject
    private AssetTransferSearchRepository assetTransferSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAssetTransferMockMvc;

    private AssetTransfer assetTransfer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetTransferResource assetTransferResource = new AssetTransferResource();
        ReflectionTestUtils.setField(assetTransferResource, "assetTransferRepository", assetTransferRepository);
        ReflectionTestUtils.setField(assetTransferResource, "assetTransferSearchRepository", assetTransferSearchRepository);
        this.restAssetTransferMockMvc = MockMvcBuilders.standaloneSetup(assetTransferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        assetTransfer = new AssetTransfer();
        assetTransfer.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createAssetTransfer() throws Exception {
        int databaseSizeBeforeCreate = assetTransferRepository.findAll().size();

        // Create the AssetTransfer

        restAssetTransferMockMvc.perform(post("/api/assetTransfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetTransfer)))
                .andExpect(status().isCreated());

        // Validate the AssetTransfer in the database
        List<AssetTransfer> assetTransfers = assetTransferRepository.findAll();
        assertThat(assetTransfers).hasSize(databaseSizeBeforeCreate + 1);
        AssetTransfer testAssetTransfer = assetTransfers.get(assetTransfers.size() - 1);
        assertThat(testAssetTransfer.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllAssetTransfers() throws Exception {
        // Initialize the database
        assetTransferRepository.saveAndFlush(assetTransfer);

        // Get all the assetTransfers
        restAssetTransferMockMvc.perform(get("/api/assetTransfers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assetTransfer.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAssetTransfer() throws Exception {
        // Initialize the database
        assetTransferRepository.saveAndFlush(assetTransfer);

        // Get the assetTransfer
        restAssetTransferMockMvc.perform(get("/api/assetTransfers/{id}", assetTransfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(assetTransfer.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetTransfer() throws Exception {
        // Get the assetTransfer
        restAssetTransferMockMvc.perform(get("/api/assetTransfers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetTransfer() throws Exception {
        // Initialize the database
        assetTransferRepository.saveAndFlush(assetTransfer);

		int databaseSizeBeforeUpdate = assetTransferRepository.findAll().size();

        // Update the assetTransfer
        assetTransfer.setDate(UPDATED_DATE);

        restAssetTransferMockMvc.perform(put("/api/assetTransfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetTransfer)))
                .andExpect(status().isOk());

        // Validate the AssetTransfer in the database
        List<AssetTransfer> assetTransfers = assetTransferRepository.findAll();
        assertThat(assetTransfers).hasSize(databaseSizeBeforeUpdate);
        AssetTransfer testAssetTransfer = assetTransfers.get(assetTransfers.size() - 1);
        assertThat(testAssetTransfer.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteAssetTransfer() throws Exception {
        // Initialize the database
        assetTransferRepository.saveAndFlush(assetTransfer);

		int databaseSizeBeforeDelete = assetTransferRepository.findAll().size();

        // Get the assetTransfer
        restAssetTransferMockMvc.perform(delete("/api/assetTransfers/{id}", assetTransfer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetTransfer> assetTransfers = assetTransferRepository.findAll();
        assertThat(assetTransfers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
