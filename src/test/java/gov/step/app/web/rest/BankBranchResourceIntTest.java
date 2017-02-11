package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.BankBranch;
import gov.step.app.repository.BankBranchRepository;
import gov.step.app.repository.search.BankBranchSearchRepository;

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
 * Test class for the BankBranchResource REST controller.
 *
 * @see BankBranchResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BankBranchResourceIntTest {

    private static final String DEFAULT_BR_NAME = "AAAAA";
    private static final String UPDATED_BR_NAME = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private BankBranchRepository bankBranchRepository;

    @Inject
    private BankBranchSearchRepository bankBranchSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBankBranchMockMvc;

    private BankBranch bankBranch;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BankBranchResource bankBranchResource = new BankBranchResource();
        ReflectionTestUtils.setField(bankBranchResource, "bankBranchRepository", bankBranchRepository);
        ReflectionTestUtils.setField(bankBranchResource, "bankBranchSearchRepository", bankBranchSearchRepository);
        this.restBankBranchMockMvc = MockMvcBuilders.standaloneSetup(bankBranchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bankBranch = new BankBranch();
        bankBranch.setBrName(DEFAULT_BR_NAME);
        bankBranch.setAddress(DEFAULT_ADDRESS);
        bankBranch.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBankBranch() throws Exception {
        int databaseSizeBeforeCreate = bankBranchRepository.findAll().size();

        // Create the BankBranch

        restBankBranchMockMvc.perform(post("/api/bankBranchs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankBranch)))
                .andExpect(status().isCreated());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchs = bankBranchRepository.findAll();
        assertThat(bankBranchs).hasSize(databaseSizeBeforeCreate + 1);
        BankBranch testBankBranch = bankBranchs.get(bankBranchs.size() - 1);
        assertThat(testBankBranch.getBrName()).isEqualTo(DEFAULT_BR_NAME);
        assertThat(testBankBranch.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBankBranch.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkBrNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankBranchRepository.findAll().size();
        // set the field null
        bankBranch.setBrName(null);

        // Create the BankBranch, which fails.

        restBankBranchMockMvc.perform(post("/api/bankBranchs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankBranch)))
                .andExpect(status().isBadRequest());

        List<BankBranch> bankBranchs = bankBranchRepository.findAll();
        assertThat(bankBranchs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBankBranchs() throws Exception {
        // Initialize the database
        bankBranchRepository.saveAndFlush(bankBranch);

        // Get all the bankBranchs
        restBankBranchMockMvc.perform(get("/api/bankBranchs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bankBranch.getId().intValue())))
                .andExpect(jsonPath("$.[*].brName").value(hasItem(DEFAULT_BR_NAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getBankBranch() throws Exception {
        // Initialize the database
        bankBranchRepository.saveAndFlush(bankBranch);

        // Get the bankBranch
        restBankBranchMockMvc.perform(get("/api/bankBranchs/{id}", bankBranch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bankBranch.getId().intValue()))
            .andExpect(jsonPath("$.brName").value(DEFAULT_BR_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBankBranch() throws Exception {
        // Get the bankBranch
        restBankBranchMockMvc.perform(get("/api/bankBranchs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankBranch() throws Exception {
        // Initialize the database
        bankBranchRepository.saveAndFlush(bankBranch);

		int databaseSizeBeforeUpdate = bankBranchRepository.findAll().size();

        // Update the bankBranch
        bankBranch.setBrName(UPDATED_BR_NAME);
        bankBranch.setAddress(UPDATED_ADDRESS);
        bankBranch.setStatus(UPDATED_STATUS);

        restBankBranchMockMvc.perform(put("/api/bankBranchs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankBranch)))
                .andExpect(status().isOk());

        // Validate the BankBranch in the database
        List<BankBranch> bankBranchs = bankBranchRepository.findAll();
        assertThat(bankBranchs).hasSize(databaseSizeBeforeUpdate);
        BankBranch testBankBranch = bankBranchs.get(bankBranchs.size() - 1);
        assertThat(testBankBranch.getBrName()).isEqualTo(UPDATED_BR_NAME);
        assertThat(testBankBranch.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBankBranch.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteBankBranch() throws Exception {
        // Initialize the database
        bankBranchRepository.saveAndFlush(bankBranch);

		int databaseSizeBeforeDelete = bankBranchRepository.findAll().size();

        // Get the bankBranch
        restBankBranchMockMvc.perform(delete("/api/bankBranchs/{id}", bankBranch.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BankBranch> bankBranchs = bankBranchRepository.findAll();
        assertThat(bankBranchs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
