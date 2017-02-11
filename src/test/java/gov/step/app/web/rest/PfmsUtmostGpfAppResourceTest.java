package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PfmsUtmostGpfApp;
import gov.step.app.repository.PfmsUtmostGpfAppRepository;
import gov.step.app.repository.search.PfmsUtmostGpfAppSearchRepository;

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
 * Test class for the PfmsUtmostGpfAppResource REST controller.
 *
 * @see PfmsUtmostGpfAppResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PfmsUtmostGpfAppResourceTest {


    private static final LocalDate DEFAULT_PRL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRL_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_GPF_NO = "AAAAA";
    private static final String UPDATED_GPF_NO = "BBBBB";

    private static final Double DEFAULT_LAST_DEDUCTION = 1D;
    private static final Double UPDATED_LAST_DEDUCTION = 2D;

    private static final LocalDate DEFAULT_DEDUCTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEDUCTION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_BILL_NO = "AAAAA";
    private static final String UPDATED_BILL_NO = "BBBBB";

    private static final LocalDate DEFAULT_BILL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BILL_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_TOKEN_NO = "AAAAA";
    private static final String UPDATED_TOKEN_NO = "BBBBB";

    private static final LocalDate DEFAULT_TOKEN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TOKEN_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_WITHDRAW_FROM = "AAAAA";
    private static final String UPDATED_WITHDRAW_FROM = "BBBBB";

    private static final LocalDate DEFAULT_APPLY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private PfmsUtmostGpfAppRepository pfmsUtmostGpfAppRepository;

    @Inject
    private PfmsUtmostGpfAppSearchRepository pfmsUtmostGpfAppSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPfmsUtmostGpfAppMockMvc;

    private PfmsUtmostGpfApp pfmsUtmostGpfApp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PfmsUtmostGpfAppResource pfmsUtmostGpfAppResource = new PfmsUtmostGpfAppResource();
        ReflectionTestUtils.setField(pfmsUtmostGpfAppResource, "pfmsUtmostGpfAppRepository", pfmsUtmostGpfAppRepository);
        ReflectionTestUtils.setField(pfmsUtmostGpfAppResource, "pfmsUtmostGpfAppSearchRepository", pfmsUtmostGpfAppSearchRepository);
        this.restPfmsUtmostGpfAppMockMvc = MockMvcBuilders.standaloneSetup(pfmsUtmostGpfAppResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pfmsUtmostGpfApp = new PfmsUtmostGpfApp();
        pfmsUtmostGpfApp.setPrlDate(DEFAULT_PRL_DATE);
        pfmsUtmostGpfApp.setGpfNo(DEFAULT_GPF_NO);
        pfmsUtmostGpfApp.setLastDeduction(DEFAULT_LAST_DEDUCTION);
        pfmsUtmostGpfApp.setDeductionDate(DEFAULT_DEDUCTION_DATE);
        pfmsUtmostGpfApp.setBillNo(DEFAULT_BILL_NO);
        pfmsUtmostGpfApp.setBillDate(DEFAULT_BILL_DATE);
        pfmsUtmostGpfApp.setTokenNo(DEFAULT_TOKEN_NO);
        pfmsUtmostGpfApp.setTokenDate(DEFAULT_TOKEN_DATE);
        pfmsUtmostGpfApp.setWithdrawFrom(DEFAULT_WITHDRAW_FROM);
        pfmsUtmostGpfApp.setApplyDate(DEFAULT_APPLY_DATE);
        pfmsUtmostGpfApp.setCreateDate(DEFAULT_CREATE_DATE);
        pfmsUtmostGpfApp.setCreateBy(DEFAULT_CREATE_BY);
        pfmsUtmostGpfApp.setUpdateDate(DEFAULT_UPDATE_DATE);
        pfmsUtmostGpfApp.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPfmsUtmostGpfApp() throws Exception {
        int databaseSizeBeforeCreate = pfmsUtmostGpfAppRepository.findAll().size();

        // Create the PfmsUtmostGpfApp

        restPfmsUtmostGpfAppMockMvc.perform(post("/api/pfmsUtmostGpfApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsUtmostGpfApp)))
                .andExpect(status().isCreated());

        // Validate the PfmsUtmostGpfApp in the database
        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeCreate + 1);
        PfmsUtmostGpfApp testPfmsUtmostGpfApp = pfmsUtmostGpfApps.get(pfmsUtmostGpfApps.size() - 1);
        assertThat(testPfmsUtmostGpfApp.getPrlDate()).isEqualTo(DEFAULT_PRL_DATE);
        assertThat(testPfmsUtmostGpfApp.getGpfNo()).isEqualTo(DEFAULT_GPF_NO);
        assertThat(testPfmsUtmostGpfApp.getLastDeduction()).isEqualTo(DEFAULT_LAST_DEDUCTION);
        assertThat(testPfmsUtmostGpfApp.getDeductionDate()).isEqualTo(DEFAULT_DEDUCTION_DATE);
        assertThat(testPfmsUtmostGpfApp.getBillNo()).isEqualTo(DEFAULT_BILL_NO);
        assertThat(testPfmsUtmostGpfApp.getBillDate()).isEqualTo(DEFAULT_BILL_DATE);
        assertThat(testPfmsUtmostGpfApp.getTokenNo()).isEqualTo(DEFAULT_TOKEN_NO);
        assertThat(testPfmsUtmostGpfApp.getTokenDate()).isEqualTo(DEFAULT_TOKEN_DATE);
        assertThat(testPfmsUtmostGpfApp.getWithdrawFrom()).isEqualTo(DEFAULT_WITHDRAW_FROM);
        assertThat(testPfmsUtmostGpfApp.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
        assertThat(testPfmsUtmostGpfApp.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPfmsUtmostGpfApp.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPfmsUtmostGpfApp.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPfmsUtmostGpfApp.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkPrlDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsUtmostGpfAppRepository.findAll().size();
        // set the field null
        pfmsUtmostGpfApp.setPrlDate(null);

        // Create the PfmsUtmostGpfApp, which fails.

        restPfmsUtmostGpfAppMockMvc.perform(post("/api/pfmsUtmostGpfApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsUtmostGpfApp)))
                .andExpect(status().isBadRequest());

        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeductionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsUtmostGpfAppRepository.findAll().size();
        // set the field null
        pfmsUtmostGpfApp.setDeductionDate(null);

        // Create the PfmsUtmostGpfApp, which fails.

        restPfmsUtmostGpfAppMockMvc.perform(post("/api/pfmsUtmostGpfApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsUtmostGpfApp)))
                .andExpect(status().isBadRequest());

        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBillNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsUtmostGpfAppRepository.findAll().size();
        // set the field null
        pfmsUtmostGpfApp.setBillNo(null);

        // Create the PfmsUtmostGpfApp, which fails.

        restPfmsUtmostGpfAppMockMvc.perform(post("/api/pfmsUtmostGpfApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsUtmostGpfApp)))
                .andExpect(status().isBadRequest());

        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBillDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsUtmostGpfAppRepository.findAll().size();
        // set the field null
        pfmsUtmostGpfApp.setBillDate(null);

        // Create the PfmsUtmostGpfApp, which fails.

        restPfmsUtmostGpfAppMockMvc.perform(post("/api/pfmsUtmostGpfApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsUtmostGpfApp)))
                .andExpect(status().isBadRequest());

        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTokenNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsUtmostGpfAppRepository.findAll().size();
        // set the field null
        pfmsUtmostGpfApp.setTokenNo(null);

        // Create the PfmsUtmostGpfApp, which fails.

        restPfmsUtmostGpfAppMockMvc.perform(post("/api/pfmsUtmostGpfApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsUtmostGpfApp)))
                .andExpect(status().isBadRequest());

        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTokenDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsUtmostGpfAppRepository.findAll().size();
        // set the field null
        pfmsUtmostGpfApp.setTokenDate(null);

        // Create the PfmsUtmostGpfApp, which fails.

        restPfmsUtmostGpfAppMockMvc.perform(post("/api/pfmsUtmostGpfApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsUtmostGpfApp)))
                .andExpect(status().isBadRequest());

        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWithdrawFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsUtmostGpfAppRepository.findAll().size();
        // set the field null
        pfmsUtmostGpfApp.setWithdrawFrom(null);

        // Create the PfmsUtmostGpfApp, which fails.

        restPfmsUtmostGpfAppMockMvc.perform(post("/api/pfmsUtmostGpfApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsUtmostGpfApp)))
                .andExpect(status().isBadRequest());

        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplyDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsUtmostGpfAppRepository.findAll().size();
        // set the field null
        pfmsUtmostGpfApp.setApplyDate(null);

        // Create the PfmsUtmostGpfApp, which fails.

        restPfmsUtmostGpfAppMockMvc.perform(post("/api/pfmsUtmostGpfApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsUtmostGpfApp)))
                .andExpect(status().isBadRequest());

        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPfmsUtmostGpfApps() throws Exception {
        // Initialize the database
        pfmsUtmostGpfAppRepository.saveAndFlush(pfmsUtmostGpfApp);

        // Get all the pfmsUtmostGpfApps
        restPfmsUtmostGpfAppMockMvc.perform(get("/api/pfmsUtmostGpfApps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pfmsUtmostGpfApp.getId().intValue())))
                .andExpect(jsonPath("$.[*].prlDate").value(hasItem(DEFAULT_PRL_DATE.toString())))
                .andExpect(jsonPath("$.[*].gpfNo").value(hasItem(DEFAULT_GPF_NO.toString())))
                .andExpect(jsonPath("$.[*].lastDeduction").value(hasItem(DEFAULT_LAST_DEDUCTION.doubleValue())))
                .andExpect(jsonPath("$.[*].deductionDate").value(hasItem(DEFAULT_DEDUCTION_DATE.toString())))
                .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO.toString())))
                .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
                .andExpect(jsonPath("$.[*].tokenNo").value(hasItem(DEFAULT_TOKEN_NO.toString())))
                .andExpect(jsonPath("$.[*].tokenDate").value(hasItem(DEFAULT_TOKEN_DATE.toString())))
                .andExpect(jsonPath("$.[*].withdrawFrom").value(hasItem(DEFAULT_WITHDRAW_FROM.toString())))
                .andExpect(jsonPath("$.[*].applyDate").value(hasItem(DEFAULT_APPLY_DATE.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPfmsUtmostGpfApp() throws Exception {
        // Initialize the database
        pfmsUtmostGpfAppRepository.saveAndFlush(pfmsUtmostGpfApp);

        // Get the pfmsUtmostGpfApp
        restPfmsUtmostGpfAppMockMvc.perform(get("/api/pfmsUtmostGpfApps/{id}", pfmsUtmostGpfApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pfmsUtmostGpfApp.getId().intValue()))
            .andExpect(jsonPath("$.prlDate").value(DEFAULT_PRL_DATE.toString()))
            .andExpect(jsonPath("$.gpfNo").value(DEFAULT_GPF_NO.toString()))
            .andExpect(jsonPath("$.lastDeduction").value(DEFAULT_LAST_DEDUCTION.doubleValue()))
            .andExpect(jsonPath("$.deductionDate").value(DEFAULT_DEDUCTION_DATE.toString()))
            .andExpect(jsonPath("$.billNo").value(DEFAULT_BILL_NO.toString()))
            .andExpect(jsonPath("$.billDate").value(DEFAULT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.tokenNo").value(DEFAULT_TOKEN_NO.toString()))
            .andExpect(jsonPath("$.tokenDate").value(DEFAULT_TOKEN_DATE.toString()))
            .andExpect(jsonPath("$.withdrawFrom").value(DEFAULT_WITHDRAW_FROM.toString()))
            .andExpect(jsonPath("$.applyDate").value(DEFAULT_APPLY_DATE.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPfmsUtmostGpfApp() throws Exception {
        // Get the pfmsUtmostGpfApp
        restPfmsUtmostGpfAppMockMvc.perform(get("/api/pfmsUtmostGpfApps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePfmsUtmostGpfApp() throws Exception {
        // Initialize the database
        pfmsUtmostGpfAppRepository.saveAndFlush(pfmsUtmostGpfApp);

		int databaseSizeBeforeUpdate = pfmsUtmostGpfAppRepository.findAll().size();

        // Update the pfmsUtmostGpfApp
        pfmsUtmostGpfApp.setPrlDate(UPDATED_PRL_DATE);
        pfmsUtmostGpfApp.setGpfNo(UPDATED_GPF_NO);
        pfmsUtmostGpfApp.setLastDeduction(UPDATED_LAST_DEDUCTION);
        pfmsUtmostGpfApp.setDeductionDate(UPDATED_DEDUCTION_DATE);
        pfmsUtmostGpfApp.setBillNo(UPDATED_BILL_NO);
        pfmsUtmostGpfApp.setBillDate(UPDATED_BILL_DATE);
        pfmsUtmostGpfApp.setTokenNo(UPDATED_TOKEN_NO);
        pfmsUtmostGpfApp.setTokenDate(UPDATED_TOKEN_DATE);
        pfmsUtmostGpfApp.setWithdrawFrom(UPDATED_WITHDRAW_FROM);
        pfmsUtmostGpfApp.setApplyDate(UPDATED_APPLY_DATE);
        pfmsUtmostGpfApp.setCreateDate(UPDATED_CREATE_DATE);
        pfmsUtmostGpfApp.setCreateBy(UPDATED_CREATE_BY);
        pfmsUtmostGpfApp.setUpdateDate(UPDATED_UPDATE_DATE);
        pfmsUtmostGpfApp.setUpdateBy(UPDATED_UPDATE_BY);

        restPfmsUtmostGpfAppMockMvc.perform(put("/api/pfmsUtmostGpfApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsUtmostGpfApp)))
                .andExpect(status().isOk());

        // Validate the PfmsUtmostGpfApp in the database
        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeUpdate);
        PfmsUtmostGpfApp testPfmsUtmostGpfApp = pfmsUtmostGpfApps.get(pfmsUtmostGpfApps.size() - 1);
        assertThat(testPfmsUtmostGpfApp.getPrlDate()).isEqualTo(UPDATED_PRL_DATE);
        assertThat(testPfmsUtmostGpfApp.getGpfNo()).isEqualTo(UPDATED_GPF_NO);
        assertThat(testPfmsUtmostGpfApp.getLastDeduction()).isEqualTo(UPDATED_LAST_DEDUCTION);
        assertThat(testPfmsUtmostGpfApp.getDeductionDate()).isEqualTo(UPDATED_DEDUCTION_DATE);
        assertThat(testPfmsUtmostGpfApp.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testPfmsUtmostGpfApp.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testPfmsUtmostGpfApp.getTokenNo()).isEqualTo(UPDATED_TOKEN_NO);
        assertThat(testPfmsUtmostGpfApp.getTokenDate()).isEqualTo(UPDATED_TOKEN_DATE);
        assertThat(testPfmsUtmostGpfApp.getWithdrawFrom()).isEqualTo(UPDATED_WITHDRAW_FROM);
        assertThat(testPfmsUtmostGpfApp.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
        assertThat(testPfmsUtmostGpfApp.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPfmsUtmostGpfApp.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPfmsUtmostGpfApp.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPfmsUtmostGpfApp.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePfmsUtmostGpfApp() throws Exception {
        // Initialize the database
        pfmsUtmostGpfAppRepository.saveAndFlush(pfmsUtmostGpfApp);

		int databaseSizeBeforeDelete = pfmsUtmostGpfAppRepository.findAll().size();

        // Get the pfmsUtmostGpfApp
        restPfmsUtmostGpfAppMockMvc.perform(delete("/api/pfmsUtmostGpfApps/{id}", pfmsUtmostGpfApp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PfmsUtmostGpfApp> pfmsUtmostGpfApps = pfmsUtmostGpfAppRepository.findAll();
        assertThat(pfmsUtmostGpfApps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
