package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AssetRecord;
import gov.step.app.repository.AssetRecordRepository;
import gov.step.app.repository.search.AssetRecordSearchRepository;

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
 * Test class for the AssetRecordResource REST controller.
 *
 * @see AssetRecordResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AssetRecordResourceTest {

    private static final String DEFAULT_ASSET_NAME = "AAA";
    private static final String UPDATED_ASSET_NAME = "BBB";
    private static final String DEFAULT_VENDOR_NAME = "AAA";
    private static final String UPDATED_VENDOR_NAME = "BBB";
    private static final String DEFAULT_SUPPLIER_NAME = "AAA";

    private static final LocalDate DEFAULT_PURCHASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_ORDER_NO = "AAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private AssetRecordRepository assetRecordRepository;

    @Inject
    private AssetRecordSearchRepository assetRecordSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAssetRecordMockMvc;

    private AssetRecord assetRecord;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetRecordResource assetRecordResource = new AssetRecordResource();
        ReflectionTestUtils.setField(assetRecordResource, "assetRecordRepository", assetRecordRepository);
        ReflectionTestUtils.setField(assetRecordResource, "assetRecordSearchRepository", assetRecordSearchRepository);
        this.restAssetRecordMockMvc = MockMvcBuilders.standaloneSetup(assetRecordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        assetRecord = new AssetRecord();
        assetRecord.setAssetName(DEFAULT_ASSET_NAME);
        assetRecord.setVendorName(DEFAULT_VENDOR_NAME);
        assetRecord.setPurchaseDate(DEFAULT_PURCHASE_DATE);
        assetRecord.setOrderNo(DEFAULT_ORDER_NO);
        assetRecord.setPrice(DEFAULT_PRICE);
        assetRecord.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAssetRecord() throws Exception {
        int databaseSizeBeforeCreate = assetRecordRepository.findAll().size();

        // Create the AssetRecord

        restAssetRecordMockMvc.perform(post("/api/assetRecords")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRecord)))
                .andExpect(status().isCreated());

        // Validate the AssetRecord in the database
        List<AssetRecord> assetRecords = assetRecordRepository.findAll();
        assertThat(assetRecords).hasSize(databaseSizeBeforeCreate + 1);
        AssetRecord testAssetRecord = assetRecords.get(assetRecords.size() - 1);
        assertThat(testAssetRecord.getAssetName()).isEqualTo(DEFAULT_ASSET_NAME);
        assertThat(testAssetRecord.getVendorName()).isEqualTo(DEFAULT_VENDOR_NAME);
        assertThat(testAssetRecord.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testAssetRecord.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testAssetRecord.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testAssetRecord.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkAssetNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRecordRepository.findAll().size();
        // set the field null
        assetRecord.setAssetName(null);

        // Create the AssetRecord, which fails.

        restAssetRecordMockMvc.perform(post("/api/assetRecords")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRecord)))
                .andExpect(status().isBadRequest());

        List<AssetRecord> assetRecords = assetRecordRepository.findAll();
        assertThat(assetRecords).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVendorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRecordRepository.findAll().size();
        // set the field null
        assetRecord.setVendorName(null);

        // Create the AssetRecord, which fails.

        restAssetRecordMockMvc.perform(post("/api/assetRecords")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRecord)))
                .andExpect(status().isBadRequest());

        List<AssetRecord> assetRecords = assetRecordRepository.findAll();
        assertThat(assetRecords).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSupplierNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRecordRepository.findAll().size();
        // set the field null


        // Create the AssetRecord, which fails.

        restAssetRecordMockMvc.perform(post("/api/assetRecords")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRecord)))
                .andExpect(status().isBadRequest());

        List<AssetRecord> assetRecords = assetRecordRepository.findAll();
        assertThat(assetRecords).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRecordRepository.findAll().size();
        // set the field null
        assetRecord.setOrderNo(null);

        // Create the AssetRecord, which fails.

        restAssetRecordMockMvc.perform(post("/api/assetRecords")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRecord)))
                .andExpect(status().isBadRequest());

        List<AssetRecord> assetRecords = assetRecordRepository.findAll();
        assertThat(assetRecords).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRecordRepository.findAll().size();
        // set the field null
        assetRecord.setPrice(null);

        // Create the AssetRecord, which fails.

        restAssetRecordMockMvc.perform(post("/api/assetRecords")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRecord)))
                .andExpect(status().isBadRequest());

        List<AssetRecord> assetRecords = assetRecordRepository.findAll();
        assertThat(assetRecords).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetRecords() throws Exception {
        // Initialize the database
        assetRecordRepository.saveAndFlush(assetRecord);

        // Get all the assetRecords
        restAssetRecordMockMvc.perform(get("/api/assetRecords"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assetRecord.getId().intValue())))
                .andExpect(jsonPath("$.[*].assetName").value(hasItem(DEFAULT_ASSET_NAME.toString())))
                .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME.toString())))
                .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
                .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getAssetRecord() throws Exception {
        // Initialize the database
        assetRecordRepository.saveAndFlush(assetRecord);

        // Get the assetRecord
        restAssetRecordMockMvc.perform(get("/api/assetRecords/{id}", assetRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(assetRecord.getId().intValue()))
            .andExpect(jsonPath("$.assetName").value(DEFAULT_ASSET_NAME.toString()))
            .andExpect(jsonPath("$.vendorName").value(DEFAULT_VENDOR_NAME.toString()))
            .andExpect(jsonPath("$.supplierName").value(DEFAULT_SUPPLIER_NAME.toString()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetRecord() throws Exception {
        // Get the assetRecord
        restAssetRecordMockMvc.perform(get("/api/assetRecords/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetRecord() throws Exception {
        // Initialize the database
        assetRecordRepository.saveAndFlush(assetRecord);

		int databaseSizeBeforeUpdate = assetRecordRepository.findAll().size();

        // Update the assetRecord
        assetRecord.setAssetName(UPDATED_ASSET_NAME);
        assetRecord.setVendorName(UPDATED_VENDOR_NAME);
        assetRecord.setPurchaseDate(UPDATED_PURCHASE_DATE);
        assetRecord.setOrderNo(UPDATED_ORDER_NO);
        assetRecord.setPrice(UPDATED_PRICE);
        assetRecord.setStatus(UPDATED_STATUS);

        restAssetRecordMockMvc.perform(put("/api/assetRecords")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assetRecord)))
                .andExpect(status().isOk());

        // Validate the AssetRecord in the database
        List<AssetRecord> assetRecords = assetRecordRepository.findAll();
        assertThat(assetRecords).hasSize(databaseSizeBeforeUpdate);
        AssetRecord testAssetRecord = assetRecords.get(assetRecords.size() - 1);
        assertThat(testAssetRecord.getAssetName()).isEqualTo(UPDATED_ASSET_NAME);
        assertThat(testAssetRecord.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testAssetRecord.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testAssetRecord.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testAssetRecord.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testAssetRecord.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteAssetRecord() throws Exception {
        // Initialize the database
        assetRecordRepository.saveAndFlush(assetRecord);

		int databaseSizeBeforeDelete = assetRecordRepository.findAll().size();

        // Get the assetRecord
        restAssetRecordMockMvc.perform(delete("/api/assetRecords/{id}", assetRecord.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetRecord> assetRecords = assetRecordRepository.findAll();
        assertThat(assetRecords).hasSize(databaseSizeBeforeDelete - 1);
    }
}
