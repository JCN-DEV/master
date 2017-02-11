package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.FeePaymentTypeSetup;
import gov.step.app.repository.FeePaymentTypeSetupRepository;
import gov.step.app.repository.search.FeePaymentTypeSetupSearchRepository;

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
 * Test class for the FeePaymentTypeSetupResource REST controller.
 *
 * @see FeePaymentTypeSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FeePaymentTypeSetupResourceTest {

    private static final String DEFAULT_PAYMENT_TYPE_ID = "AAAAA";
    private static final String UPDATED_PAYMENT_TYPE_ID = "BBBBB";
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
    private FeePaymentTypeSetupRepository feePaymentTypeSetupRepository;

    @Inject
    private FeePaymentTypeSetupSearchRepository feePaymentTypeSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFeePaymentTypeSetupMockMvc;

    private FeePaymentTypeSetup feePaymentTypeSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeePaymentTypeSetupResource feePaymentTypeSetupResource = new FeePaymentTypeSetupResource();
        ReflectionTestUtils.setField(feePaymentTypeSetupResource, "feePaymentTypeSetupRepository", feePaymentTypeSetupRepository);
        ReflectionTestUtils.setField(feePaymentTypeSetupResource, "feePaymentTypeSetupSearchRepository", feePaymentTypeSetupSearchRepository);
        this.restFeePaymentTypeSetupMockMvc = MockMvcBuilders.standaloneSetup(feePaymentTypeSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        feePaymentTypeSetup = new FeePaymentTypeSetup();
        feePaymentTypeSetup.setPaymentTypeId(DEFAULT_PAYMENT_TYPE_ID);
        feePaymentTypeSetup.setName(DEFAULT_NAME);
        feePaymentTypeSetup.setStatus(DEFAULT_STATUS);
        feePaymentTypeSetup.setDescription(DEFAULT_DESCRIPTION);
        feePaymentTypeSetup.setCreateDate(DEFAULT_CREATE_DATE);
        feePaymentTypeSetup.setCreateBy(DEFAULT_CREATE_BY);
        feePaymentTypeSetup.setUpdateBy(DEFAULT_UPDATE_BY);
        feePaymentTypeSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createFeePaymentTypeSetup() throws Exception {
        int databaseSizeBeforeCreate = feePaymentTypeSetupRepository.findAll().size();

        // Create the FeePaymentTypeSetup

        restFeePaymentTypeSetupMockMvc.perform(post("/api/feePaymentTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentTypeSetup)))
                .andExpect(status().isCreated());

        // Validate the FeePaymentTypeSetup in the database
        List<FeePaymentTypeSetup> feePaymentTypeSetups = feePaymentTypeSetupRepository.findAll();
        assertThat(feePaymentTypeSetups).hasSize(databaseSizeBeforeCreate + 1);
        FeePaymentTypeSetup testFeePaymentTypeSetup = feePaymentTypeSetups.get(feePaymentTypeSetups.size() - 1);
        assertThat(testFeePaymentTypeSetup.getPaymentTypeId()).isEqualTo(DEFAULT_PAYMENT_TYPE_ID);
        assertThat(testFeePaymentTypeSetup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFeePaymentTypeSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFeePaymentTypeSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeePaymentTypeSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFeePaymentTypeSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testFeePaymentTypeSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testFeePaymentTypeSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void checkPaymentTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = feePaymentTypeSetupRepository.findAll().size();
        // set the field null
        feePaymentTypeSetup.setPaymentTypeId(null);

        // Create the FeePaymentTypeSetup, which fails.

        restFeePaymentTypeSetupMockMvc.perform(post("/api/feePaymentTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentTypeSetup)))
                .andExpect(status().isBadRequest());

        List<FeePaymentTypeSetup> feePaymentTypeSetups = feePaymentTypeSetupRepository.findAll();
        assertThat(feePaymentTypeSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = feePaymentTypeSetupRepository.findAll().size();
        // set the field null
        feePaymentTypeSetup.setName(null);

        // Create the FeePaymentTypeSetup, which fails.

        restFeePaymentTypeSetupMockMvc.perform(post("/api/feePaymentTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentTypeSetup)))
                .andExpect(status().isBadRequest());

        List<FeePaymentTypeSetup> feePaymentTypeSetups = feePaymentTypeSetupRepository.findAll();
        assertThat(feePaymentTypeSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = feePaymentTypeSetupRepository.findAll().size();
        // set the field null
        feePaymentTypeSetup.setStatus(null);

        // Create the FeePaymentTypeSetup, which fails.

        restFeePaymentTypeSetupMockMvc.perform(post("/api/feePaymentTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentTypeSetup)))
                .andExpect(status().isBadRequest());

        List<FeePaymentTypeSetup> feePaymentTypeSetups = feePaymentTypeSetupRepository.findAll();
        assertThat(feePaymentTypeSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeePaymentTypeSetups() throws Exception {
        // Initialize the database
        feePaymentTypeSetupRepository.saveAndFlush(feePaymentTypeSetup);

        // Get all the feePaymentTypeSetups
        restFeePaymentTypeSetupMockMvc.perform(get("/api/feePaymentTypeSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feePaymentTypeSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].paymentTypeId").value(hasItem(DEFAULT_PAYMENT_TYPE_ID.toString())))
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
    public void getFeePaymentTypeSetup() throws Exception {
        // Initialize the database
        feePaymentTypeSetupRepository.saveAndFlush(feePaymentTypeSetup);

        // Get the feePaymentTypeSetup
        restFeePaymentTypeSetupMockMvc.perform(get("/api/feePaymentTypeSetups/{id}", feePaymentTypeSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(feePaymentTypeSetup.getId().intValue()))
            .andExpect(jsonPath("$.paymentTypeId").value(DEFAULT_PAYMENT_TYPE_ID.toString()))
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
    public void getNonExistingFeePaymentTypeSetup() throws Exception {
        // Get the feePaymentTypeSetup
        restFeePaymentTypeSetupMockMvc.perform(get("/api/feePaymentTypeSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeePaymentTypeSetup() throws Exception {
        // Initialize the database
        feePaymentTypeSetupRepository.saveAndFlush(feePaymentTypeSetup);

		int databaseSizeBeforeUpdate = feePaymentTypeSetupRepository.findAll().size();

        // Update the feePaymentTypeSetup
        feePaymentTypeSetup.setPaymentTypeId(UPDATED_PAYMENT_TYPE_ID);
        feePaymentTypeSetup.setName(UPDATED_NAME);
        feePaymentTypeSetup.setStatus(UPDATED_STATUS);
        feePaymentTypeSetup.setDescription(UPDATED_DESCRIPTION);
        feePaymentTypeSetup.setCreateDate(UPDATED_CREATE_DATE);
        feePaymentTypeSetup.setCreateBy(UPDATED_CREATE_BY);
        feePaymentTypeSetup.setUpdateBy(UPDATED_UPDATE_BY);
        feePaymentTypeSetup.setUpdateDate(UPDATED_UPDATE_DATE);

        restFeePaymentTypeSetupMockMvc.perform(put("/api/feePaymentTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feePaymentTypeSetup)))
                .andExpect(status().isOk());

        // Validate the FeePaymentTypeSetup in the database
        List<FeePaymentTypeSetup> feePaymentTypeSetups = feePaymentTypeSetupRepository.findAll();
        assertThat(feePaymentTypeSetups).hasSize(databaseSizeBeforeUpdate);
        FeePaymentTypeSetup testFeePaymentTypeSetup = feePaymentTypeSetups.get(feePaymentTypeSetups.size() - 1);
        assertThat(testFeePaymentTypeSetup.getPaymentTypeId()).isEqualTo(UPDATED_PAYMENT_TYPE_ID);
        assertThat(testFeePaymentTypeSetup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeePaymentTypeSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFeePaymentTypeSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeePaymentTypeSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFeePaymentTypeSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testFeePaymentTypeSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testFeePaymentTypeSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deleteFeePaymentTypeSetup() throws Exception {
        // Initialize the database
        feePaymentTypeSetupRepository.saveAndFlush(feePaymentTypeSetup);

		int databaseSizeBeforeDelete = feePaymentTypeSetupRepository.findAll().size();

        // Get the feePaymentTypeSetup
        restFeePaymentTypeSetupMockMvc.perform(delete("/api/feePaymentTypeSetups/{id}", feePaymentTypeSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FeePaymentTypeSetup> feePaymentTypeSetups = feePaymentTypeSetupRepository.findAll();
        assertThat(feePaymentTypeSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
