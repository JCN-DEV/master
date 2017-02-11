package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.FeePaymentCategorySetup;
import gov.step.app.repository.FeePaymentCategorySetupRepository;
import gov.step.app.repository.search.FeePaymentCategorySetupSearchRepository;

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
 * Test class for the FeePaymentCategorySetupResource REST controller.
 *
 * @see FeePaymentCategorySetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FeePaymentCategorySetupResourceTest {

    private static final String DEFAULT_PAYMENT_CATEGORY_ID = "AAAAA";
    private static final String UPDATED_PAYMENT_CATEGORY_ID = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_STATUS = "AAAAA";
    private static final String UPDATED_STATUS = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private FeePaymentCategorySetupRepository feePaymentCategorySetupRepository;

    @Inject
    private FeePaymentCategorySetupSearchRepository feePaymentCategorySetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFeePaymentCategorySetupMockMvc;

    private FeePaymentCategorySetup feePaymentCategorySetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeePaymentCategorySetupResource feePaymentCategorySetupResource = new FeePaymentCategorySetupResource();
        ReflectionTestUtils.setField(feePaymentCategorySetupResource, "feePaymentCategorySetupRepository", feePaymentCategorySetupRepository);
        ReflectionTestUtils.setField(feePaymentCategorySetupResource, "feePaymentCategorySetupSearchRepository", feePaymentCategorySetupSearchRepository);
        this.restFeePaymentCategorySetupMockMvc = MockMvcBuilders.standaloneSetup(feePaymentCategorySetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        feePaymentCategorySetup = new FeePaymentCategorySetup();
        feePaymentCategorySetup.setPaymentCategoryId(DEFAULT_PAYMENT_CATEGORY_ID);
        feePaymentCategorySetup.setName(DEFAULT_NAME);
        feePaymentCategorySetup.setStatus(DEFAULT_STATUS);
        feePaymentCategorySetup.setDescription(DEFAULT_DESCRIPTION);
        feePaymentCategorySetup.setCreateDate(DEFAULT_CREATE_DATE);
        feePaymentCategorySetup.setCreateBy(DEFAULT_CREATE_BY);
        feePaymentCategorySetup.setUpdateBy(DEFAULT_UPDATE_BY);
        feePaymentCategorySetup.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createFeePaymentCategorySetup() throws Exception {
        int databaseSizeBeforeCreate = feePaymentCategorySetupRepository.findAll().size();

        // Create the FeePaymentCategorySetup

        restFeePaymentCategorySetupMockMvc.perform(post("/api/feePaymentCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentCategorySetup)))
                .andExpect(status().isCreated());

        // Validate the FeePaymentCategorySetup in the database
        List<FeePaymentCategorySetup> feePaymentCategorySetups = feePaymentCategorySetupRepository.findAll();
        assertThat(feePaymentCategorySetups).hasSize(databaseSizeBeforeCreate + 1);
        FeePaymentCategorySetup testFeePaymentCategorySetup = feePaymentCategorySetups.get(feePaymentCategorySetups.size() - 1);
        assertThat(testFeePaymentCategorySetup.getPaymentCategoryId()).isEqualTo(DEFAULT_PAYMENT_CATEGORY_ID);
        assertThat(testFeePaymentCategorySetup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFeePaymentCategorySetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFeePaymentCategorySetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeePaymentCategorySetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFeePaymentCategorySetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testFeePaymentCategorySetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testFeePaymentCategorySetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void checkPaymentCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = feePaymentCategorySetupRepository.findAll().size();
        // set the field null
        feePaymentCategorySetup.setPaymentCategoryId(null);

        // Create the FeePaymentCategorySetup, which fails.

        restFeePaymentCategorySetupMockMvc.perform(post("/api/feePaymentCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentCategorySetup)))
                .andExpect(status().isBadRequest());

        List<FeePaymentCategorySetup> feePaymentCategorySetups = feePaymentCategorySetupRepository.findAll();
        assertThat(feePaymentCategorySetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = feePaymentCategorySetupRepository.findAll().size();
        // set the field null
        feePaymentCategorySetup.setName(null);

        // Create the FeePaymentCategorySetup, which fails.

        restFeePaymentCategorySetupMockMvc.perform(post("/api/feePaymentCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentCategorySetup)))
                .andExpect(status().isBadRequest());

        List<FeePaymentCategorySetup> feePaymentCategorySetups = feePaymentCategorySetupRepository.findAll();
        assertThat(feePaymentCategorySetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = feePaymentCategorySetupRepository.findAll().size();
        // set the field null
        feePaymentCategorySetup.setStatus(null);

        // Create the FeePaymentCategorySetup, which fails.

        restFeePaymentCategorySetupMockMvc.perform(post("/api/feePaymentCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentCategorySetup)))
                .andExpect(status().isBadRequest());

        List<FeePaymentCategorySetup> feePaymentCategorySetups = feePaymentCategorySetupRepository.findAll();
        assertThat(feePaymentCategorySetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeePaymentCategorySetups() throws Exception {
        // Initialize the database
        feePaymentCategorySetupRepository.saveAndFlush(feePaymentCategorySetup);

        // Get all the feePaymentCategorySetups
        restFeePaymentCategorySetupMockMvc.perform(get("/api/feePaymentCategorySetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feePaymentCategorySetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].paymentCategoryId").value(hasItem(DEFAULT_PAYMENT_CATEGORY_ID.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getFeePaymentCategorySetup() throws Exception {
        // Initialize the database
        feePaymentCategorySetupRepository.saveAndFlush(feePaymentCategorySetup);

        // Get the feePaymentCategorySetup
        restFeePaymentCategorySetupMockMvc.perform(get("/api/feePaymentCategorySetups/{id}", feePaymentCategorySetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(feePaymentCategorySetup.getId().intValue()))
            .andExpect(jsonPath("$.paymentCategoryId").value(DEFAULT_PAYMENT_CATEGORY_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeePaymentCategorySetup() throws Exception {
        // Get the feePaymentCategorySetup
        restFeePaymentCategorySetupMockMvc.perform(get("/api/feePaymentCategorySetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeePaymentCategorySetup() throws Exception {
        // Initialize the database
        feePaymentCategorySetupRepository.saveAndFlush(feePaymentCategorySetup);

		int databaseSizeBeforeUpdate = feePaymentCategorySetupRepository.findAll().size();

        // Update the feePaymentCategorySetup
        feePaymentCategorySetup.setPaymentCategoryId(UPDATED_PAYMENT_CATEGORY_ID);
        feePaymentCategorySetup.setName(UPDATED_NAME);
        feePaymentCategorySetup.setStatus(UPDATED_STATUS);
        feePaymentCategorySetup.setDescription(UPDATED_DESCRIPTION);
        feePaymentCategorySetup.setCreateDate(UPDATED_CREATE_DATE);
        feePaymentCategorySetup.setCreateBy(UPDATED_CREATE_BY);
        feePaymentCategorySetup.setUpdateBy(UPDATED_UPDATE_BY);
        feePaymentCategorySetup.setUpdateDate(UPDATED_UPDATE_DATE);

        restFeePaymentCategorySetupMockMvc.perform(put("/api/feePaymentCategorySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentCategorySetup)))
                .andExpect(status().isOk());

        // Validate the FeePaymentCategorySetup in the database
        List<FeePaymentCategorySetup> feePaymentCategorySetups = feePaymentCategorySetupRepository.findAll();
        assertThat(feePaymentCategorySetups).hasSize(databaseSizeBeforeUpdate);
        FeePaymentCategorySetup testFeePaymentCategorySetup = feePaymentCategorySetups.get(feePaymentCategorySetups.size() - 1);
        assertThat(testFeePaymentCategorySetup.getPaymentCategoryId()).isEqualTo(UPDATED_PAYMENT_CATEGORY_ID);
        assertThat(testFeePaymentCategorySetup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeePaymentCategorySetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFeePaymentCategorySetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeePaymentCategorySetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFeePaymentCategorySetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testFeePaymentCategorySetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testFeePaymentCategorySetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deleteFeePaymentCategorySetup() throws Exception {
        // Initialize the database
        feePaymentCategorySetupRepository.saveAndFlush(feePaymentCategorySetup);

		int databaseSizeBeforeDelete = feePaymentCategorySetupRepository.findAll().size();

        // Get the feePaymentCategorySetup
        restFeePaymentCategorySetupMockMvc.perform(delete("/api/feePaymentCategorySetups/{id}", feePaymentCategorySetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FeePaymentCategorySetup> feePaymentCategorySetups = feePaymentCategorySetupRepository.findAll();
        assertThat(feePaymentCategorySetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
