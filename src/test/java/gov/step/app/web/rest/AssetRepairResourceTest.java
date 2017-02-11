package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AssetRepair;
import gov.step.app.repository.AssetRepairRepository;
import gov.step.app.repository.search.AssetRepairSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AssetRepairResource REST controller.
 *
 * @see AssetRepairResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AssetRepairResourceTest {

    private static final String DEFAULT_REF_NO = "AAAAA";
    private static final String UPDATED_REF_NO = "BBBBB";
    private static final String DEFAULT_REPAIRED_BY = "AAAAA";
    private static final String UPDATED_REPAIRED_BY = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_PROBLEM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_PROBLEM = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REPAIR_DATE = "AAAAA";
    private static final String UPDATED_REPAIR_DATE = "BBBBB";

    private static final BigDecimal DEFAULT_REPAIR_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_REPAIR_COST = new BigDecimal(2);

    @Inject
    private AssetRepairRepository assetRepairRepository;

    @Inject
    private AssetRepairSearchRepository assetRepairSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAssetRepairMockMvc;

    private AssetRepair assetRepair;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetRepairResource assetRepairResource = new AssetRepairResource();
        ReflectionTestUtils.setField(assetRepairResource, "assetRepairRepository", assetRepairRepository);
        ReflectionTestUtils.setField(assetRepairResource, "assetRepairSearchRepository", assetRepairSearchRepository);
        this.restAssetRepairMockMvc = MockMvcBuilders.standaloneSetup(assetRepairResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        assetRepair = new AssetRepair();
        assetRepair.setRefNo(DEFAULT_REF_NO);
        assetRepair.setRepairedBy(DEFAULT_REPAIRED_BY);
        assetRepair.setDateOfProblem(DEFAULT_DATE_OF_PROBLEM);
        assetRepair.setRepairDate(DEFAULT_REPAIR_DATE);
        assetRepair.setRepairCost(DEFAULT_REPAIR_COST);
    }

    @Test
    @Transactional
    public void createAssetRepair() throws Exception {
        int databaseSizeBeforeCreate = assetRepairRepository.findAll().size();

        // Create the AssetRepair

        restAssetRepairMockMvc.perform(post("/api/assetRepairs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRepair)))
                .andExpect(status().isCreated());

        // Validate the AssetRepair in the database
        List<AssetRepair> assetRepairs = assetRepairRepository.findAll();
        assertThat(assetRepairs).hasSize(databaseSizeBeforeCreate + 1);
        AssetRepair testAssetRepair = assetRepairs.get(assetRepairs.size() - 1);
        assertThat(testAssetRepair.getRefNo()).isEqualTo(DEFAULT_REF_NO);
        assertThat(testAssetRepair.getRepairedBy()).isEqualTo(DEFAULT_REPAIRED_BY);
        assertThat(testAssetRepair.getDateOfProblem()).isEqualTo(DEFAULT_DATE_OF_PROBLEM);
        assertThat(testAssetRepair.getRepairDate()).isEqualTo(DEFAULT_REPAIR_DATE);
        assertThat(testAssetRepair.getRepairCost()).isEqualTo(DEFAULT_REPAIR_COST);
    }

    @Test
    @Transactional
    public void checkRefNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRepairRepository.findAll().size();
        // set the field null
        assetRepair.setRefNo(null);

        // Create the AssetRepair, which fails.

        restAssetRepairMockMvc.perform(post("/api/assetRepairs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRepair)))
                .andExpect(status().isBadRequest());

        List<AssetRepair> assetRepairs = assetRepairRepository.findAll();
        assertThat(assetRepairs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepairedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRepairRepository.findAll().size();
        // set the field null
        assetRepair.setRepairedBy(null);

        // Create the AssetRepair, which fails.

        restAssetRepairMockMvc.perform(post("/api/assetRepairs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRepair)))
                .andExpect(status().isBadRequest());

        List<AssetRepair> assetRepairs = assetRepairRepository.findAll();
        assertThat(assetRepairs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetRepairs() throws Exception {
        // Initialize the database
        assetRepairRepository.saveAndFlush(assetRepair);

        // Get all the assetRepairs
        restAssetRepairMockMvc.perform(get("/api/assetRepairs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assetRepair.getId().intValue())))
                .andExpect(jsonPath("$.[*].refNo").value(hasItem(DEFAULT_REF_NO.toString())))
                .andExpect(jsonPath("$.[*].repairedBy").value(hasItem(DEFAULT_REPAIRED_BY.toString())))
                .andExpect(jsonPath("$.[*].dateOfProblem").value(hasItem(DEFAULT_DATE_OF_PROBLEM.toString())))
                .andExpect(jsonPath("$.[*].repairDate").value(hasItem(DEFAULT_REPAIR_DATE.toString())))
                .andExpect(jsonPath("$.[*].repairCost").value(hasItem(DEFAULT_REPAIR_COST.intValue())));
    }

    @Test
    @Transactional
    public void getAssetRepair() throws Exception {
        // Initialize the database
        assetRepairRepository.saveAndFlush(assetRepair);

        // Get the assetRepair
        restAssetRepairMockMvc.perform(get("/api/assetRepairs/{id}", assetRepair.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(assetRepair.getId().intValue()))
            .andExpect(jsonPath("$.refNo").value(DEFAULT_REF_NO.toString()))
            .andExpect(jsonPath("$.repairedBy").value(DEFAULT_REPAIRED_BY.toString()))
            .andExpect(jsonPath("$.dateOfProblem").value(DEFAULT_DATE_OF_PROBLEM.toString()))
            .andExpect(jsonPath("$.repairDate").value(DEFAULT_REPAIR_DATE.toString()))
            .andExpect(jsonPath("$.repairCost").value(DEFAULT_REPAIR_COST.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetRepair() throws Exception {
        // Get the assetRepair
        restAssetRepairMockMvc.perform(get("/api/assetRepairs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetRepair() throws Exception {
        // Initialize the database
        assetRepairRepository.saveAndFlush(assetRepair);

		int databaseSizeBeforeUpdate = assetRepairRepository.findAll().size();

        // Update the assetRepair
        assetRepair.setRefNo(UPDATED_REF_NO);
        assetRepair.setRepairedBy(UPDATED_REPAIRED_BY);
        assetRepair.setDateOfProblem(UPDATED_DATE_OF_PROBLEM);
        assetRepair.setRepairDate(UPDATED_REPAIR_DATE);
        assetRepair.setRepairCost(UPDATED_REPAIR_COST);

        restAssetRepairMockMvc.perform(put("/api/assetRepairs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRepair)))
                .andExpect(status().isOk());

        // Validate the AssetRepair in the database
        List<AssetRepair> assetRepairs = assetRepairRepository.findAll();
        assertThat(assetRepairs).hasSize(databaseSizeBeforeUpdate);
        AssetRepair testAssetRepair = assetRepairs.get(assetRepairs.size() - 1);
        assertThat(testAssetRepair.getRefNo()).isEqualTo(UPDATED_REF_NO);
        assertThat(testAssetRepair.getRepairedBy()).isEqualTo(UPDATED_REPAIRED_BY);
        assertThat(testAssetRepair.getDateOfProblem()).isEqualTo(UPDATED_DATE_OF_PROBLEM);
        assertThat(testAssetRepair.getRepairDate()).isEqualTo(UPDATED_REPAIR_DATE);
        assertThat(testAssetRepair.getRepairCost()).isEqualTo(UPDATED_REPAIR_COST);
    }

    @Test
    @Transactional
    public void deleteAssetRepair() throws Exception {
        // Initialize the database
        assetRepairRepository.saveAndFlush(assetRepair);

		int databaseSizeBeforeDelete = assetRepairRepository.findAll().size();

        // Get the assetRepair
        restAssetRepairMockMvc.perform(delete("/api/assetRepairs/{id}", assetRepair.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetRepair> assetRepairs = assetRepairRepository.findAll();
        assertThat(assetRepairs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
