package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.IncrementSetup;
import gov.step.app.repository.IncrementSetupRepository;
import gov.step.app.repository.search.IncrementSetupSearchRepository;

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
 * Test class for the IncrementSetupResource REST controller.
 *
 * @see IncrementSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class IncrementSetupResourceTest {


    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";
    private static final String DEFAULT_PAY_CODE_SERIAL = "AAAAA";
    private static final String UPDATED_PAY_CODE_SERIAL = "BBBBB";

    @Inject
    private IncrementSetupRepository incrementSetupRepository;

    @Inject
    private IncrementSetupSearchRepository incrementSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIncrementSetupMockMvc;

    private IncrementSetup incrementSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IncrementSetupResource incrementSetupResource = new IncrementSetupResource();
        ReflectionTestUtils.setField(incrementSetupResource, "incrementSetupRepository", incrementSetupRepository);
        ReflectionTestUtils.setField(incrementSetupResource, "incrementSetupSearchRepository", incrementSetupSearchRepository);
        this.restIncrementSetupMockMvc = MockMvcBuilders.standaloneSetup(incrementSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        incrementSetup = new IncrementSetup();
        incrementSetup.setAmount(DEFAULT_AMOUNT);
        incrementSetup.setStatus(DEFAULT_STATUS);
        incrementSetup.setCreateBy(DEFAULT_CREATE_BY);
        incrementSetup.setCreateDate(DEFAULT_CREATE_DATE);
        incrementSetup.setUpdateBy(DEFAULT_UPDATE_BY);
        incrementSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        incrementSetup.setRemarks(DEFAULT_REMARKS);
        incrementSetup.setPayCodeSerial(DEFAULT_PAY_CODE_SERIAL);
    }

    @Test
    @Transactional
    public void createIncrementSetup() throws Exception {
        int databaseSizeBeforeCreate = incrementSetupRepository.findAll().size();

        // Create the IncrementSetup

        restIncrementSetupMockMvc.perform(post("/api/incrementSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(incrementSetup)))
                .andExpect(status().isCreated());

        // Validate the IncrementSetup in the database
        List<IncrementSetup> incrementSetups = incrementSetupRepository.findAll();
        assertThat(incrementSetups).hasSize(databaseSizeBeforeCreate + 1);
        IncrementSetup testIncrementSetup = incrementSetups.get(incrementSetups.size() - 1);
        assertThat(testIncrementSetup.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testIncrementSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testIncrementSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testIncrementSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testIncrementSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testIncrementSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testIncrementSetup.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testIncrementSetup.getPayCodeSerial()).isEqualTo(DEFAULT_PAY_CODE_SERIAL);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = incrementSetupRepository.findAll().size();
        // set the field null
        incrementSetup.setAmount(null);

        // Create the IncrementSetup, which fails.

        restIncrementSetupMockMvc.perform(post("/api/incrementSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(incrementSetup)))
                .andExpect(status().isBadRequest());

        List<IncrementSetup> incrementSetups = incrementSetupRepository.findAll();
        assertThat(incrementSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncrementSetups() throws Exception {
        // Initialize the database
        incrementSetupRepository.saveAndFlush(incrementSetup);

        // Get all the incrementSetups
        restIncrementSetupMockMvc.perform(get("/api/incrementSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(incrementSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].PayCodeSerial").value(hasItem(DEFAULT_PAY_CODE_SERIAL.toString())));
    }

    @Test
    @Transactional
    public void getIncrementSetup() throws Exception {
        // Initialize the database
        incrementSetupRepository.saveAndFlush(incrementSetup);

        // Get the incrementSetup
        restIncrementSetupMockMvc.perform(get("/api/incrementSetups/{id}", incrementSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(incrementSetup.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.PayCodeSerial").value(DEFAULT_PAY_CODE_SERIAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIncrementSetup() throws Exception {
        // Get the incrementSetup
        restIncrementSetupMockMvc.perform(get("/api/incrementSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncrementSetup() throws Exception {
        // Initialize the database
        incrementSetupRepository.saveAndFlush(incrementSetup);

		int databaseSizeBeforeUpdate = incrementSetupRepository.findAll().size();

        // Update the incrementSetup
        incrementSetup.setAmount(UPDATED_AMOUNT);
        incrementSetup.setStatus(UPDATED_STATUS);
        incrementSetup.setCreateBy(UPDATED_CREATE_BY);
        incrementSetup.setCreateDate(UPDATED_CREATE_DATE);
        incrementSetup.setUpdateBy(UPDATED_UPDATE_BY);
        incrementSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        incrementSetup.setRemarks(UPDATED_REMARKS);
        incrementSetup.setPayCodeSerial(UPDATED_PAY_CODE_SERIAL);

        restIncrementSetupMockMvc.perform(put("/api/incrementSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(incrementSetup)))
                .andExpect(status().isOk());

        // Validate the IncrementSetup in the database
        List<IncrementSetup> incrementSetups = incrementSetupRepository.findAll();
        assertThat(incrementSetups).hasSize(databaseSizeBeforeUpdate);
        IncrementSetup testIncrementSetup = incrementSetups.get(incrementSetups.size() - 1);
        assertThat(testIncrementSetup.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testIncrementSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testIncrementSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testIncrementSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testIncrementSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testIncrementSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testIncrementSetup.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testIncrementSetup.getPayCodeSerial()).isEqualTo(UPDATED_PAY_CODE_SERIAL);
    }

    @Test
    @Transactional
    public void deleteIncrementSetup() throws Exception {
        // Initialize the database
        incrementSetupRepository.saveAndFlush(incrementSetup);

		int databaseSizeBeforeDelete = incrementSetupRepository.findAll().size();

        // Get the incrementSetup
        restIncrementSetupMockMvc.perform(delete("/api/incrementSetups/{id}", incrementSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<IncrementSetup> incrementSetups = incrementSetupRepository.findAll();
        assertThat(incrementSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
