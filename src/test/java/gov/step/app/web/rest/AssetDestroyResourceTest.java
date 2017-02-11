package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AssetDestroy;
import gov.step.app.repository.AssetDestroyRepository;
import gov.step.app.repository.search.AssetDestroySearchRepository;

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
 * Test class for the AssetDestroyResource REST controller.
 *
 * @see AssetDestroyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AssetDestroyResourceTest {

    private static final String DEFAULT_TRANSFER_REFERENCE = "AAAAA";
    private static final String UPDATED_TRANSFER_REFERENCE = "BBBBB";

    private static final LocalDate DEFAULT_DESTROY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DESTROY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_USED_PERIOD = 1;
    private static final Integer UPDATED_USED_PERIOD = 2;

    private static final LocalDate DEFAULT_CAUSE_OF_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CAUSE_OF_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private AssetDestroyRepository assetDestroyRepository;

    @Inject
    private AssetDestroySearchRepository assetDestroySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAssetDestroyMockMvc;

    private AssetDestroy assetDestroy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetDestroyResource assetDestroyResource = new AssetDestroyResource();
        ReflectionTestUtils.setField(assetDestroyResource, "assetDestroyRepository", assetDestroyRepository);
        ReflectionTestUtils.setField(assetDestroyResource, "assetDestroySearchRepository", assetDestroySearchRepository);
        this.restAssetDestroyMockMvc = MockMvcBuilders.standaloneSetup(assetDestroyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        assetDestroy = new AssetDestroy();
        assetDestroy.setTransferReference(DEFAULT_TRANSFER_REFERENCE);
        assetDestroy.setDestroyDate(DEFAULT_DESTROY_DATE);
        assetDestroy.setUsedPeriod(DEFAULT_USED_PERIOD);
        assetDestroy.setCauseOfDate(DEFAULT_CAUSE_OF_DATE);
    }

    @Test
    @Transactional
    public void createAssetDestroy() throws Exception {
        int databaseSizeBeforeCreate = assetDestroyRepository.findAll().size();

        // Create the AssetDestroy

        restAssetDestroyMockMvc.perform(post("/api/assetDestroys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetDestroy)))
                .andExpect(status().isCreated());

        // Validate the AssetDestroy in the database
        List<AssetDestroy> assetDestroys = assetDestroyRepository.findAll();
        assertThat(assetDestroys).hasSize(databaseSizeBeforeCreate + 1);
        AssetDestroy testAssetDestroy = assetDestroys.get(assetDestroys.size() - 1);
        assertThat(testAssetDestroy.getTransferReference()).isEqualTo(DEFAULT_TRANSFER_REFERENCE);
        assertThat(testAssetDestroy.getDestroyDate()).isEqualTo(DEFAULT_DESTROY_DATE);
        assertThat(testAssetDestroy.getUsedPeriod()).isEqualTo(DEFAULT_USED_PERIOD);
        assertThat(testAssetDestroy.getCauseOfDate()).isEqualTo(DEFAULT_CAUSE_OF_DATE);
    }

    @Test
    @Transactional
    public void checkTransferReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetDestroyRepository.findAll().size();
        // set the field null
        assetDestroy.setTransferReference(null);

        // Create the AssetDestroy, which fails.

        restAssetDestroyMockMvc.perform(post("/api/assetDestroys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetDestroy)))
                .andExpect(status().isBadRequest());

        List<AssetDestroy> assetDestroys = assetDestroyRepository.findAll();
        assertThat(assetDestroys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetDestroys() throws Exception {
        // Initialize the database
        assetDestroyRepository.saveAndFlush(assetDestroy);

        // Get all the assetDestroys
        restAssetDestroyMockMvc.perform(get("/api/assetDestroys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assetDestroy.getId().intValue())))
                .andExpect(jsonPath("$.[*].transferReference").value(hasItem(DEFAULT_TRANSFER_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].destroyDate").value(hasItem(DEFAULT_DESTROY_DATE.toString())))
                .andExpect(jsonPath("$.[*].usedPeriod").value(hasItem(DEFAULT_USED_PERIOD)))
                .andExpect(jsonPath("$.[*].causeOfDate").value(hasItem(DEFAULT_CAUSE_OF_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAssetDestroy() throws Exception {
        // Initialize the database
        assetDestroyRepository.saveAndFlush(assetDestroy);

        // Get the assetDestroy
        restAssetDestroyMockMvc.perform(get("/api/assetDestroys/{id}", assetDestroy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(assetDestroy.getId().intValue()))
            .andExpect(jsonPath("$.transferReference").value(DEFAULT_TRANSFER_REFERENCE.toString()))
            .andExpect(jsonPath("$.destroyDate").value(DEFAULT_DESTROY_DATE.toString()))
            .andExpect(jsonPath("$.usedPeriod").value(DEFAULT_USED_PERIOD))
            .andExpect(jsonPath("$.causeOfDate").value(DEFAULT_CAUSE_OF_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetDestroy() throws Exception {
        // Get the assetDestroy
        restAssetDestroyMockMvc.perform(get("/api/assetDestroys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetDestroy() throws Exception {
        // Initialize the database
        assetDestroyRepository.saveAndFlush(assetDestroy);

		int databaseSizeBeforeUpdate = assetDestroyRepository.findAll().size();

        // Update the assetDestroy
        assetDestroy.setTransferReference(UPDATED_TRANSFER_REFERENCE);
        assetDestroy.setDestroyDate(UPDATED_DESTROY_DATE);
        assetDestroy.setUsedPeriod(UPDATED_USED_PERIOD);
        assetDestroy.setCauseOfDate(UPDATED_CAUSE_OF_DATE);

        restAssetDestroyMockMvc.perform(put("/api/assetDestroys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetDestroy)))
                .andExpect(status().isOk());

        // Validate the AssetDestroy in the database
        List<AssetDestroy> assetDestroys = assetDestroyRepository.findAll();
        assertThat(assetDestroys).hasSize(databaseSizeBeforeUpdate);
        AssetDestroy testAssetDestroy = assetDestroys.get(assetDestroys.size() - 1);
        assertThat(testAssetDestroy.getTransferReference()).isEqualTo(UPDATED_TRANSFER_REFERENCE);
        assertThat(testAssetDestroy.getDestroyDate()).isEqualTo(UPDATED_DESTROY_DATE);
        assertThat(testAssetDestroy.getUsedPeriod()).isEqualTo(UPDATED_USED_PERIOD);
        assertThat(testAssetDestroy.getCauseOfDate()).isEqualTo(UPDATED_CAUSE_OF_DATE);
    }

    @Test
    @Transactional
    public void deleteAssetDestroy() throws Exception {
        // Initialize the database
        assetDestroyRepository.saveAndFlush(assetDestroy);

		int databaseSizeBeforeDelete = assetDestroyRepository.findAll().size();

        // Get the assetDestroy
        restAssetDestroyMockMvc.perform(delete("/api/assetDestroys/{id}", assetDestroy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetDestroy> assetDestroys = assetDestroyRepository.findAll();
        assertThat(assetDestroys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
