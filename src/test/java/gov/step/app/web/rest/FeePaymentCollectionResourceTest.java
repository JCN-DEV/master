package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.FeePaymentCollection;
import gov.step.app.repository.FeePaymentCollectionRepository;
import gov.step.app.repository.search.FeePaymentCollectionSearchRepository;

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
 * Test class for the FeePaymentCollectionResource REST controller.
 *
 * @see FeePaymentCollectionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FeePaymentCollectionResourceTest {

    private static final String DEFAULT_TRANSACTION_ID = "AAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBB";
    private static final String DEFAULT_VOUCHER_NO = "AAAAA";
    private static final String UPDATED_VOUCHER_NO = "BBBBB";
    private static final String DEFAULT_STATUS = "AAAAA";
    private static final String UPDATED_STATUS = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_BANK_NAME = "AAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBB";
    private static final String DEFAULT_BANK_ACCOUNT_NO = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NO = "BBBBB";
    private static final String DEFAULT_PAYMENT_METHOD = "AAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBB";
    private static final String DEFAULT_PAYMENT_API_ID = "AAAAA";
    private static final String UPDATED_PAYMENT_API_ID = "BBBBB";
    private static final String DEFAULT_PAYMENT_INSTRUMENT_TYPE = "AAAAA";
    private static final String UPDATED_PAYMENT_INSTRUMENT_TYPE = "BBBBB";

    private static final Long DEFAULT_CURRENCY = 1L;
    private static final Long UPDATED_CURRENCY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final LocalDate DEFAULT_UPDATED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private FeePaymentCollectionRepository feePaymentCollectionRepository;

    @Inject
    private FeePaymentCollectionSearchRepository feePaymentCollectionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFeePaymentCollectionMockMvc;

    private FeePaymentCollection feePaymentCollection;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeePaymentCollectionResource feePaymentCollectionResource = new FeePaymentCollectionResource();
        ReflectionTestUtils.setField(feePaymentCollectionResource, "feePaymentCollectionRepository", feePaymentCollectionRepository);
        ReflectionTestUtils.setField(feePaymentCollectionResource, "feePaymentCollectionSearchRepository", feePaymentCollectionSearchRepository);
        this.restFeePaymentCollectionMockMvc = MockMvcBuilders.standaloneSetup(feePaymentCollectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        feePaymentCollection = new FeePaymentCollection();
        feePaymentCollection.setTransactionId(DEFAULT_TRANSACTION_ID);
        feePaymentCollection.setVoucherNo(DEFAULT_VOUCHER_NO);
        feePaymentCollection.setStatus(DEFAULT_STATUS);
        feePaymentCollection.setDescription(DEFAULT_DESCRIPTION);
        feePaymentCollection.setAmount(DEFAULT_AMOUNT);
        feePaymentCollection.setCreateDate(DEFAULT_CREATE_DATE);
        feePaymentCollection.setCreateBy(DEFAULT_CREATE_BY);
        feePaymentCollection.setPaymentDate(DEFAULT_PAYMENT_DATE);
        feePaymentCollection.setBankName(DEFAULT_BANK_NAME);
        feePaymentCollection.setBankAccountNo(DEFAULT_BANK_ACCOUNT_NO);
        feePaymentCollection.setPaymentMethod(DEFAULT_PAYMENT_METHOD);
        feePaymentCollection.setPaymentApiID(DEFAULT_PAYMENT_API_ID);
        feePaymentCollection.setPaymentInstrumentType(DEFAULT_PAYMENT_INSTRUMENT_TYPE);
        feePaymentCollection.setCurrency(DEFAULT_CURRENCY);
        feePaymentCollection.setUpdatedBy(DEFAULT_UPDATED_BY);
        feePaymentCollection.setUpdatedTime(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createFeePaymentCollection() throws Exception {
        int databaseSizeBeforeCreate = feePaymentCollectionRepository.findAll().size();

        // Create the FeePaymentCollection

        restFeePaymentCollectionMockMvc.perform(post("/api/feePaymentCollections")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentCollection)))
                .andExpect(status().isCreated());

        // Validate the FeePaymentCollection in the database
        List<FeePaymentCollection> feePaymentCollections = feePaymentCollectionRepository.findAll();
        assertThat(feePaymentCollections).hasSize(databaseSizeBeforeCreate + 1);
        FeePaymentCollection testFeePaymentCollection = feePaymentCollections.get(feePaymentCollections.size() - 1);
        assertThat(testFeePaymentCollection.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testFeePaymentCollection.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testFeePaymentCollection.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFeePaymentCollection.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeePaymentCollection.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testFeePaymentCollection.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFeePaymentCollection.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testFeePaymentCollection.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testFeePaymentCollection.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testFeePaymentCollection.getBankAccountNo()).isEqualTo(DEFAULT_BANK_ACCOUNT_NO);
        assertThat(testFeePaymentCollection.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testFeePaymentCollection.getPaymentApiID()).isEqualTo(DEFAULT_PAYMENT_API_ID);
        assertThat(testFeePaymentCollection.getPaymentInstrumentType()).isEqualTo(DEFAULT_PAYMENT_INSTRUMENT_TYPE);
        assertThat(testFeePaymentCollection.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testFeePaymentCollection.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFeePaymentCollection.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void checkTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = feePaymentCollectionRepository.findAll().size();
        // set the field null
        feePaymentCollection.setTransactionId(null);

        // Create the FeePaymentCollection, which fails.

        restFeePaymentCollectionMockMvc.perform(post("/api/feePaymentCollections")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentCollection)))
                .andExpect(status().isBadRequest());

        List<FeePaymentCollection> feePaymentCollections = feePaymentCollectionRepository.findAll();
        assertThat(feePaymentCollections).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVoucherNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = feePaymentCollectionRepository.findAll().size();
        // set the field null
        feePaymentCollection.setVoucherNo(null);

        // Create the FeePaymentCollection, which fails.

        restFeePaymentCollectionMockMvc.perform(post("/api/feePaymentCollections")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentCollection)))
                .andExpect(status().isBadRequest());

        List<FeePaymentCollection> feePaymentCollections = feePaymentCollectionRepository.findAll();
        assertThat(feePaymentCollections).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = feePaymentCollectionRepository.findAll().size();
        // set the field null
        feePaymentCollection.setStatus(null);

        // Create the FeePaymentCollection, which fails.

        restFeePaymentCollectionMockMvc.perform(post("/api/feePaymentCollections")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentCollection)))
                .andExpect(status().isBadRequest());

        List<FeePaymentCollection> feePaymentCollections = feePaymentCollectionRepository.findAll();
        assertThat(feePaymentCollections).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeePaymentCollections() throws Exception {
        // Initialize the database
        feePaymentCollectionRepository.saveAndFlush(feePaymentCollection);

        // Get all the feePaymentCollections
        restFeePaymentCollectionMockMvc.perform(get("/api/feePaymentCollections"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feePaymentCollection.getId().intValue())))
                .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.toString())))
                .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
                .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO.toString())))
                .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
                .andExpect(jsonPath("$.[*].paymentApiID").value(hasItem(DEFAULT_PAYMENT_API_ID.toString())))
                .andExpect(jsonPath("$.[*].paymentInstrumentType").value(hasItem(DEFAULT_PAYMENT_INSTRUMENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getFeePaymentCollection() throws Exception {
        // Initialize the database
        feePaymentCollectionRepository.saveAndFlush(feePaymentCollection);

        // Get the feePaymentCollection
        restFeePaymentCollectionMockMvc.perform(get("/api/feePaymentCollections/{id}", feePaymentCollection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(feePaymentCollection.getId().intValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID.toString()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.bankAccountNo").value(DEFAULT_BANK_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.paymentApiID").value(DEFAULT_PAYMENT_API_ID.toString()))
            .andExpect(jsonPath("$.paymentInstrumentType").value(DEFAULT_PAYMENT_INSTRUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeePaymentCollection() throws Exception {
        // Get the feePaymentCollection
        restFeePaymentCollectionMockMvc.perform(get("/api/feePaymentCollections/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeePaymentCollection() throws Exception {
        // Initialize the database
        feePaymentCollectionRepository.saveAndFlush(feePaymentCollection);

		int databaseSizeBeforeUpdate = feePaymentCollectionRepository.findAll().size();

        // Update the feePaymentCollection
        feePaymentCollection.setTransactionId(UPDATED_TRANSACTION_ID);
        feePaymentCollection.setVoucherNo(UPDATED_VOUCHER_NO);
        feePaymentCollection.setStatus(UPDATED_STATUS);
        feePaymentCollection.setDescription(UPDATED_DESCRIPTION);
        feePaymentCollection.setAmount(UPDATED_AMOUNT);
        feePaymentCollection.setCreateDate(UPDATED_CREATE_DATE);
        feePaymentCollection.setCreateBy(UPDATED_CREATE_BY);
        feePaymentCollection.setPaymentDate(UPDATED_PAYMENT_DATE);
        feePaymentCollection.setBankName(UPDATED_BANK_NAME);
        feePaymentCollection.setBankAccountNo(UPDATED_BANK_ACCOUNT_NO);
        feePaymentCollection.setPaymentMethod(UPDATED_PAYMENT_METHOD);
        feePaymentCollection.setPaymentApiID(UPDATED_PAYMENT_API_ID);
        feePaymentCollection.setPaymentInstrumentType(UPDATED_PAYMENT_INSTRUMENT_TYPE);
        feePaymentCollection.setCurrency(UPDATED_CURRENCY);
        feePaymentCollection.setUpdatedBy(UPDATED_UPDATED_BY);
        feePaymentCollection.setUpdatedTime(UPDATED_UPDATED_TIME);

        restFeePaymentCollectionMockMvc.perform(put("/api/feePaymentCollections")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentCollection)))
                .andExpect(status().isOk());

        // Validate the FeePaymentCollection in the database
        List<FeePaymentCollection> feePaymentCollections = feePaymentCollectionRepository.findAll();
        assertThat(feePaymentCollections).hasSize(databaseSizeBeforeUpdate);
        FeePaymentCollection testFeePaymentCollection = feePaymentCollections.get(feePaymentCollections.size() - 1);
        assertThat(testFeePaymentCollection.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testFeePaymentCollection.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testFeePaymentCollection.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFeePaymentCollection.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeePaymentCollection.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFeePaymentCollection.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFeePaymentCollection.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testFeePaymentCollection.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testFeePaymentCollection.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testFeePaymentCollection.getBankAccountNo()).isEqualTo(UPDATED_BANK_ACCOUNT_NO);
        assertThat(testFeePaymentCollection.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testFeePaymentCollection.getPaymentApiID()).isEqualTo(UPDATED_PAYMENT_API_ID);
        assertThat(testFeePaymentCollection.getPaymentInstrumentType()).isEqualTo(UPDATED_PAYMENT_INSTRUMENT_TYPE);
        assertThat(testFeePaymentCollection.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testFeePaymentCollection.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFeePaymentCollection.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteFeePaymentCollection() throws Exception {
        // Initialize the database
        feePaymentCollectionRepository.saveAndFlush(feePaymentCollection);

		int databaseSizeBeforeDelete = feePaymentCollectionRepository.findAll().size();

        // Get the feePaymentCollection
        restFeePaymentCollectionMockMvc.perform(delete("/api/feePaymentCollections/{id}", feePaymentCollection.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FeePaymentCollection> feePaymentCollections = feePaymentCollectionRepository.findAll();
        assertThat(feePaymentCollections).hasSize(databaseSizeBeforeDelete - 1);
    }
}
