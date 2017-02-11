package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmplBankInfo;
import gov.step.app.repository.InstEmplBankInfoRepository;
import gov.step.app.repository.search.InstEmplBankInfoSearchRepository;

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
 * Test class for the InstEmplBankInfoResource REST controller.
 *
 * @see InstEmplBankInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmplBankInfoResourceIntTest {

    private static final String DEFAULT_BANK_NAME = "AAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBB";
    private static final String DEFAULT_BANK_BRANCH = "AAAAA";
    private static final String UPDATED_BANK_BRANCH = "BBBBB";
    private static final String DEFAULT_BANK_ACCOUNT_NO = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NO = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstEmplBankInfoRepository instEmplBankInfoRepository;

    @Inject
    private InstEmplBankInfoSearchRepository instEmplBankInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmplBankInfoMockMvc;

    private InstEmplBankInfo instEmplBankInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmplBankInfoResource instEmplBankInfoResource = new InstEmplBankInfoResource();
        ReflectionTestUtils.setField(instEmplBankInfoResource, "instEmplBankInfoRepository", instEmplBankInfoRepository);
        ReflectionTestUtils.setField(instEmplBankInfoResource, "instEmplBankInfoSearchRepository", instEmplBankInfoSearchRepository);
        this.restInstEmplBankInfoMockMvc = MockMvcBuilders.standaloneSetup(instEmplBankInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmplBankInfo = new InstEmplBankInfo();
        instEmplBankInfo.setBankName(DEFAULT_BANK_NAME);
        instEmplBankInfo.setBankBranch(DEFAULT_BANK_BRANCH);
        instEmplBankInfo.setBankAccountNo(DEFAULT_BANK_ACCOUNT_NO);
        instEmplBankInfo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstEmplBankInfo() throws Exception {
        int databaseSizeBeforeCreate = instEmplBankInfoRepository.findAll().size();

        // Create the InstEmplBankInfo

        restInstEmplBankInfoMockMvc.perform(post("/api/instEmplBankInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplBankInfo)))
                .andExpect(status().isCreated());

        // Validate the InstEmplBankInfo in the database
        List<InstEmplBankInfo> instEmplBankInfos = instEmplBankInfoRepository.findAll();
        assertThat(instEmplBankInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstEmplBankInfo testInstEmplBankInfo = instEmplBankInfos.get(instEmplBankInfos.size() - 1);
        assertThat(testInstEmplBankInfo.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testInstEmplBankInfo.getBankBranch()).isEqualTo(DEFAULT_BANK_BRANCH);
        assertThat(testInstEmplBankInfo.getBankAccountNo()).isEqualTo(DEFAULT_BANK_ACCOUNT_NO);
        assertThat(testInstEmplBankInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplBankInfoRepository.findAll().size();
        // set the field null
        instEmplBankInfo.setBankName(null);

        // Create the InstEmplBankInfo, which fails.

        restInstEmplBankInfoMockMvc.perform(post("/api/instEmplBankInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplBankInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplBankInfo> instEmplBankInfos = instEmplBankInfoRepository.findAll();
        assertThat(instEmplBankInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankBranchIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplBankInfoRepository.findAll().size();
        // set the field null
        instEmplBankInfo.setBankBranch(null);

        // Create the InstEmplBankInfo, which fails.

        restInstEmplBankInfoMockMvc.perform(post("/api/instEmplBankInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplBankInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplBankInfo> instEmplBankInfos = instEmplBankInfoRepository.findAll();
        assertThat(instEmplBankInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankAccountNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplBankInfoRepository.findAll().size();
        // set the field null
        instEmplBankInfo.setBankAccountNo(null);

        // Create the InstEmplBankInfo, which fails.

        restInstEmplBankInfoMockMvc.perform(post("/api/instEmplBankInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplBankInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplBankInfo> instEmplBankInfos = instEmplBankInfoRepository.findAll();
        assertThat(instEmplBankInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstEmplBankInfos() throws Exception {
        // Initialize the database
        instEmplBankInfoRepository.saveAndFlush(instEmplBankInfo);

        // Get all the instEmplBankInfos
        restInstEmplBankInfoMockMvc.perform(get("/api/instEmplBankInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmplBankInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
                .andExpect(jsonPath("$.[*].bankBranch").value(hasItem(DEFAULT_BANK_BRANCH.toString())))
                .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstEmplBankInfo() throws Exception {
        // Initialize the database
        instEmplBankInfoRepository.saveAndFlush(instEmplBankInfo);

        // Get the instEmplBankInfo
        restInstEmplBankInfoMockMvc.perform(get("/api/instEmplBankInfos/{id}", instEmplBankInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmplBankInfo.getId().intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.bankBranch").value(DEFAULT_BANK_BRANCH.toString()))
            .andExpect(jsonPath("$.bankAccountNo").value(DEFAULT_BANK_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmplBankInfo() throws Exception {
        // Get the instEmplBankInfo
        restInstEmplBankInfoMockMvc.perform(get("/api/instEmplBankInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmplBankInfo() throws Exception {
        // Initialize the database
        instEmplBankInfoRepository.saveAndFlush(instEmplBankInfo);

		int databaseSizeBeforeUpdate = instEmplBankInfoRepository.findAll().size();

        // Update the instEmplBankInfo
        instEmplBankInfo.setBankName(UPDATED_BANK_NAME);
        instEmplBankInfo.setBankBranch(UPDATED_BANK_BRANCH);
        instEmplBankInfo.setBankAccountNo(UPDATED_BANK_ACCOUNT_NO);
        instEmplBankInfo.setStatus(UPDATED_STATUS);

        restInstEmplBankInfoMockMvc.perform(put("/api/instEmplBankInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplBankInfo)))
                .andExpect(status().isOk());

        // Validate the InstEmplBankInfo in the database
        List<InstEmplBankInfo> instEmplBankInfos = instEmplBankInfoRepository.findAll();
        assertThat(instEmplBankInfos).hasSize(databaseSizeBeforeUpdate);
        InstEmplBankInfo testInstEmplBankInfo = instEmplBankInfos.get(instEmplBankInfos.size() - 1);
        assertThat(testInstEmplBankInfo.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testInstEmplBankInfo.getBankBranch()).isEqualTo(UPDATED_BANK_BRANCH);
        assertThat(testInstEmplBankInfo.getBankAccountNo()).isEqualTo(UPDATED_BANK_ACCOUNT_NO);
        assertThat(testInstEmplBankInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstEmplBankInfo() throws Exception {
        // Initialize the database
        instEmplBankInfoRepository.saveAndFlush(instEmplBankInfo);

		int databaseSizeBeforeDelete = instEmplBankInfoRepository.findAll().size();

        // Get the instEmplBankInfo
        restInstEmplBankInfoMockMvc.perform(delete("/api/instEmplBankInfos/{id}", instEmplBankInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmplBankInfo> instEmplBankInfos = instEmplBankInfoRepository.findAll();
        assertThat(instEmplBankInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
