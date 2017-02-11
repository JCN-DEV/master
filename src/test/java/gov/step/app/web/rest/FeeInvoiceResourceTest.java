package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.FeeInvoice;
import gov.step.app.repository.FeeInvoiceRepository;
import gov.step.app.repository.search.FeeInvoiceSearchRepository;

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
 * Test class for the FeeInvoiceResource REST controller.
 *
 * @see FeeInvoiceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FeeInvoiceResourceTest {

    private static final String DEFAULT_INVOICE_ID = "AAAAA";
    private static final String UPDATED_INVOICE_ID = "BBBBB";
    private static final String DEFAULT_BANK_NAME = "AAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBB";
    private static final String DEFAULT_BANK_ACCOUNT_NO = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NO = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    @Inject
    private FeeInvoiceRepository feeInvoiceRepository;

    @Inject
    private FeeInvoiceSearchRepository feeInvoiceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFeeInvoiceMockMvc;

    private FeeInvoice feeInvoice;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeeInvoiceResource feeInvoiceResource = new FeeInvoiceResource();
        ReflectionTestUtils.setField(feeInvoiceResource, "feeInvoiceRepository", feeInvoiceRepository);
        ReflectionTestUtils.setField(feeInvoiceResource, "feeInvoiceSearchRepository", feeInvoiceSearchRepository);
        this.restFeeInvoiceMockMvc = MockMvcBuilders.standaloneSetup(feeInvoiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        feeInvoice = new FeeInvoice();
        feeInvoice.setInvoiceId(DEFAULT_INVOICE_ID);
        feeInvoice.setBankName(DEFAULT_BANK_NAME);
        feeInvoice.setBankAccountNo(DEFAULT_BANK_ACCOUNT_NO);
        feeInvoice.setDescription(DEFAULT_DESCRIPTION);
        feeInvoice.setCreateDate(DEFAULT_CREATE_DATE);
        feeInvoice.setCreateBy(DEFAULT_CREATE_BY);
    }

    @Test
    @Transactional
    public void createFeeInvoice() throws Exception {
        int databaseSizeBeforeCreate = feeInvoiceRepository.findAll().size();

        // Create the FeeInvoice

        restFeeInvoiceMockMvc.perform(post("/api/feeInvoices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feeInvoice)))
                .andExpect(status().isCreated());

        // Validate the FeeInvoice in the database
        List<FeeInvoice> feeInvoices = feeInvoiceRepository.findAll();
        assertThat(feeInvoices).hasSize(databaseSizeBeforeCreate + 1);
        FeeInvoice testFeeInvoice = feeInvoices.get(feeInvoices.size() - 1);
        assertThat(testFeeInvoice.getInvoiceId()).isEqualTo(DEFAULT_INVOICE_ID);
        assertThat(testFeeInvoice.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testFeeInvoice.getBankAccountNo()).isEqualTo(DEFAULT_BANK_ACCOUNT_NO);
        assertThat(testFeeInvoice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeeInvoice.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFeeInvoice.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
    }

    @Test
    @Transactional
    public void checkInvoiceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeInvoiceRepository.findAll().size();
        // set the field null
        feeInvoice.setInvoiceId(null);

        // Create the FeeInvoice, which fails.

        restFeeInvoiceMockMvc.perform(post("/api/feeInvoices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feeInvoice)))
                .andExpect(status().isBadRequest());

        List<FeeInvoice> feeInvoices = feeInvoiceRepository.findAll();
        assertThat(feeInvoices).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeeInvoices() throws Exception {
        // Initialize the database
        feeInvoiceRepository.saveAndFlush(feeInvoice);

        // Get all the feeInvoices
        restFeeInvoiceMockMvc.perform(get("/api/feeInvoices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feeInvoice.getId().intValue())))
                .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID.toString())))
                .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
                .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getFeeInvoice() throws Exception {
        // Initialize the database
        feeInvoiceRepository.saveAndFlush(feeInvoice);

        // Get the feeInvoice
        restFeeInvoiceMockMvc.perform(get("/api/feeInvoices/{id}", feeInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(feeInvoice.getId().intValue()))
            .andExpect(jsonPath("$.invoiceId").value(DEFAULT_INVOICE_ID.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.bankAccountNo").value(DEFAULT_BANK_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFeeInvoice() throws Exception {
        // Get the feeInvoice
        restFeeInvoiceMockMvc.perform(get("/api/feeInvoices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeeInvoice() throws Exception {
        // Initialize the database
        feeInvoiceRepository.saveAndFlush(feeInvoice);

		int databaseSizeBeforeUpdate = feeInvoiceRepository.findAll().size();

        // Update the feeInvoice
        feeInvoice.setInvoiceId(UPDATED_INVOICE_ID);
        feeInvoice.setBankName(UPDATED_BANK_NAME);
        feeInvoice.setBankAccountNo(UPDATED_BANK_ACCOUNT_NO);
        feeInvoice.setDescription(UPDATED_DESCRIPTION);
        feeInvoice.setCreateDate(UPDATED_CREATE_DATE);
        feeInvoice.setCreateBy(UPDATED_CREATE_BY);

        restFeeInvoiceMockMvc.perform(put("/api/feeInvoices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feeInvoice)))
                .andExpect(status().isOk());

        // Validate the FeeInvoice in the database
        List<FeeInvoice> feeInvoices = feeInvoiceRepository.findAll();
        assertThat(feeInvoices).hasSize(databaseSizeBeforeUpdate);
        FeeInvoice testFeeInvoice = feeInvoices.get(feeInvoices.size() - 1);
        assertThat(testFeeInvoice.getInvoiceId()).isEqualTo(UPDATED_INVOICE_ID);
        assertThat(testFeeInvoice.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testFeeInvoice.getBankAccountNo()).isEqualTo(UPDATED_BANK_ACCOUNT_NO);
        assertThat(testFeeInvoice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeeInvoice.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFeeInvoice.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
    }

    @Test
    @Transactional
    public void deleteFeeInvoice() throws Exception {
        // Initialize the database
        feeInvoiceRepository.saveAndFlush(feeInvoice);

		int databaseSizeBeforeDelete = feeInvoiceRepository.findAll().size();

        // Get the feeInvoice
        restFeeInvoiceMockMvc.perform(delete("/api/feeInvoices/{id}", feeInvoice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FeeInvoice> feeInvoices = feeInvoiceRepository.findAll();
        assertThat(feeInvoices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
