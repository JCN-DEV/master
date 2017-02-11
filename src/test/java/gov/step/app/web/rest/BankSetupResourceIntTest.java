package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.BankSetup;
import gov.step.app.repository.BankSetupRepository;
import gov.step.app.repository.search.BankSetupSearchRepository;

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
 * Test class for the BankSetupResource REST controller.
 *
 * @see BankSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BankSetupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_BRANCH_NAME = "AAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBB";

    @Inject
    private BankSetupRepository bankSetupRepository;

    @Inject
    private BankSetupSearchRepository bankSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBankSetupMockMvc;

    private BankSetup bankSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BankSetupResource bankSetupResource = new BankSetupResource();
        ReflectionTestUtils.setField(bankSetupResource, "bankSetupRepository", bankSetupRepository);
        ReflectionTestUtils.setField(bankSetupResource, "bankSetupSearchRepository", bankSetupSearchRepository);
        this.restBankSetupMockMvc = MockMvcBuilders.standaloneSetup(bankSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bankSetup = new BankSetup();
        bankSetup.setName(DEFAULT_NAME);
        bankSetup.setCode(DEFAULT_CODE);
        bankSetup.setBranchName(DEFAULT_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void createBankSetup() throws Exception {
        int databaseSizeBeforeCreate = bankSetupRepository.findAll().size();

        // Create the BankSetup

        restBankSetupMockMvc.perform(post("/api/bankSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankSetup)))
                .andExpect(status().isCreated());

        // Validate the BankSetup in the database
        List<BankSetup> bankSetups = bankSetupRepository.findAll();
        assertThat(bankSetups).hasSize(databaseSizeBeforeCreate + 1);
        BankSetup testBankSetup = bankSetups.get(bankSetups.size() - 1);
        assertThat(testBankSetup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBankSetup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBankSetup.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllBankSetups() throws Exception {
        // Initialize the database
        bankSetupRepository.saveAndFlush(bankSetup);

        // Get all the bankSetups
        restBankSetupMockMvc.perform(get("/api/bankSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bankSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBankSetup() throws Exception {
        // Initialize the database
        bankSetupRepository.saveAndFlush(bankSetup);

        // Get the bankSetup
        restBankSetupMockMvc.perform(get("/api/bankSetups/{id}", bankSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bankSetup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBankSetup() throws Exception {
        // Get the bankSetup
        restBankSetupMockMvc.perform(get("/api/bankSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankSetup() throws Exception {
        // Initialize the database
        bankSetupRepository.saveAndFlush(bankSetup);

		int databaseSizeBeforeUpdate = bankSetupRepository.findAll().size();

        // Update the bankSetup
        bankSetup.setName(UPDATED_NAME);
        bankSetup.setCode(UPDATED_CODE);
        bankSetup.setBranchName(UPDATED_BRANCH_NAME);

        restBankSetupMockMvc.perform(put("/api/bankSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankSetup)))
                .andExpect(status().isOk());

        // Validate the BankSetup in the database
        List<BankSetup> bankSetups = bankSetupRepository.findAll();
        assertThat(bankSetups).hasSize(databaseSizeBeforeUpdate);
        BankSetup testBankSetup = bankSetups.get(bankSetups.size() - 1);
        assertThat(testBankSetup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankSetup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBankSetup.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void deleteBankSetup() throws Exception {
        // Initialize the database
        bankSetupRepository.saveAndFlush(bankSetup);

		int databaseSizeBeforeDelete = bankSetupRepository.findAll().size();

        // Get the bankSetup
        restBankSetupMockMvc.perform(delete("/api/bankSetups/{id}", bankSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BankSetup> bankSetups = bankSetupRepository.findAll();
        assertThat(bankSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
