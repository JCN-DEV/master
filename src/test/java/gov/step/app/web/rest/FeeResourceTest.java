package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Fee;
import gov.step.app.repository.FeeRepository;
import gov.step.app.repository.search.FeeSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the FeeResource REST controller.
 *
 * @see FeeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FeeResourceTest {

    private static final String DEFAULT_FEE_ID = "AAAAA";
    private static final String UPDATED_FEE_ID = "BBBBB";

    @Inject
    private FeeRepository feeRepository;

    @Inject
    private FeeSearchRepository feeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFeeMockMvc;

    private Fee fee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeeResource feeResource = new FeeResource();
        ReflectionTestUtils.setField(feeResource, "feeRepository", feeRepository);
        ReflectionTestUtils.setField(feeResource, "feeSearchRepository", feeSearchRepository);
        this.restFeeMockMvc = MockMvcBuilders.standaloneSetup(feeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fee = new Fee();
        fee.setFeeId(DEFAULT_FEE_ID);
    }

    @Test
    @Transactional
    public void createFee() throws Exception {
        int databaseSizeBeforeCreate = feeRepository.findAll().size();

        // Create the Fee

        restFeeMockMvc.perform(post("/api/fees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fee)))
                .andExpect(status().isCreated());

        // Validate the Fee in the database
        List<Fee> fees = feeRepository.findAll();
        assertThat(fees).hasSize(databaseSizeBeforeCreate + 1);
        Fee testFee = fees.get(fees.size() - 1);
        assertThat(testFee.getFeeId()).isEqualTo(DEFAULT_FEE_ID);
    }

    @Test
    @Transactional
    public void getAllFees() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        // Get all the fees
        restFeeMockMvc.perform(get("/api/fees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fee.getId().intValue())))
                .andExpect(jsonPath("$.[*].feeId").value(hasItem(DEFAULT_FEE_ID.toString())));
    }

    @Test
    @Transactional
    public void getFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        // Get the fee
        restFeeMockMvc.perform(get("/api/fees/{id}", fee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fee.getId().intValue()))
            .andExpect(jsonPath("$.feeId").value(DEFAULT_FEE_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFee() throws Exception {
        // Get the fee
        restFeeMockMvc.perform(get("/api/fees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

		int databaseSizeBeforeUpdate = feeRepository.findAll().size();

        // Update the fee
        fee.setFeeId(UPDATED_FEE_ID);

        restFeeMockMvc.perform(put("/api/fees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fee)))
                .andExpect(status().isOk());

        // Validate the Fee in the database
        List<Fee> fees = feeRepository.findAll();
        assertThat(fees).hasSize(databaseSizeBeforeUpdate);
        Fee testFee = fees.get(fees.size() - 1);
        assertThat(testFee.getFeeId()).isEqualTo(UPDATED_FEE_ID);
    }

    @Test
    @Transactional
    public void deleteFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

		int databaseSizeBeforeDelete = feeRepository.findAll().size();

        // Get the fee
        restFeeMockMvc.perform(delete("/api/fees/{id}", fee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fee> fees = feeRepository.findAll();
        assertThat(fees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
