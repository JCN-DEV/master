package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.BankAssign;
import gov.step.app.repository.BankAssignRepository;
import gov.step.app.repository.search.BankAssignSearchRepository;

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
 * Test class for the BankAssignResource REST controller.
 *
 * @see BankAssignResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BankAssignResourceIntTest {


    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = true;
    private static final Boolean UPDATED_STATUS = false;

    @Inject
    private BankAssignRepository bankAssignRepository;

    @Inject
    private BankAssignSearchRepository bankAssignSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBankAssignMockMvc;

    private BankAssign bankAssign;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BankAssignResource bankAssignResource = new BankAssignResource();
        ReflectionTestUtils.setField(bankAssignResource, "bankAssignRepository", bankAssignRepository);
        ReflectionTestUtils.setField(bankAssignResource, "bankAssignSearchRepository", bankAssignSearchRepository);
        this.restBankAssignMockMvc = MockMvcBuilders.standaloneSetup(bankAssignResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bankAssign = new BankAssign();
        bankAssign.setCreatedDate(DEFAULT_CREATED_DATE);
        bankAssign.setModifiedDate(DEFAULT_MODIFIED_DATE);
        bankAssign.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBankAssign() throws Exception {
        int databaseSizeBeforeCreate = bankAssignRepository.findAll().size();

        // Create the BankAssign

        restBankAssignMockMvc.perform(post("/api/bankAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankAssign)))
                .andExpect(status().isCreated());

        // Validate the BankAssign in the database
        List<BankAssign> bankAssigns = bankAssignRepository.findAll();
        assertThat(bankAssigns).hasSize(databaseSizeBeforeCreate + 1);
        BankAssign testBankAssign = bankAssigns.get(bankAssigns.size() - 1);
        assertThat(testBankAssign.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBankAssign.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testBankAssign.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllBankAssigns() throws Exception {
        // Initialize the database
        bankAssignRepository.saveAndFlush(bankAssign);

        // Get all the bankAssigns
        restBankAssignMockMvc.perform(get("/api/bankAssigns"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bankAssign.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getBankAssign() throws Exception {
        // Initialize the database
        bankAssignRepository.saveAndFlush(bankAssign);

        // Get the bankAssign
        restBankAssignMockMvc.perform(get("/api/bankAssigns/{id}", bankAssign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bankAssign.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingBankAssign() throws Exception {
        // Get the bankAssign
        restBankAssignMockMvc.perform(get("/api/bankAssigns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankAssign() throws Exception {
        // Initialize the database
        bankAssignRepository.saveAndFlush(bankAssign);

		int databaseSizeBeforeUpdate = bankAssignRepository.findAll().size();

        // Update the bankAssign
        bankAssign.setCreatedDate(UPDATED_CREATED_DATE);
        bankAssign.setModifiedDate(UPDATED_MODIFIED_DATE);
        bankAssign.setStatus(UPDATED_STATUS);

        restBankAssignMockMvc.perform(put("/api/bankAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankAssign)))
                .andExpect(status().isOk());

        // Validate the BankAssign in the database
        List<BankAssign> bankAssigns = bankAssignRepository.findAll();
        assertThat(bankAssigns).hasSize(databaseSizeBeforeUpdate);
        BankAssign testBankAssign = bankAssigns.get(bankAssigns.size() - 1);
        assertThat(testBankAssign.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBankAssign.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testBankAssign.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteBankAssign() throws Exception {
        // Initialize the database
        bankAssignRepository.saveAndFlush(bankAssign);

		int databaseSizeBeforeDelete = bankAssignRepository.findAll().size();

        // Get the bankAssign
        restBankAssignMockMvc.perform(delete("/api/bankAssigns/{id}", bankAssign.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BankAssign> bankAssigns = bankAssignRepository.findAll();
        assertThat(bankAssigns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
