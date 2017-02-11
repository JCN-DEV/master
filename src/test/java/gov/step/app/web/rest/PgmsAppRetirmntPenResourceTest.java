package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsAppRetirmntPen;
import gov.step.app.repository.PgmsAppRetirmntPenRepository;
import gov.step.app.repository.search.PgmsAppRetirmntPenSearchRepository;

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
 * Test class for the PgmsAppRetirmntPenResource REST controller.
 *
 * @see PgmsAppRetirmntPenResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsAppRetirmntPenResourceTest {

    private static final String DEFAULT_WITHDRAWN_TYPE = "AAAAA";
    private static final String UPDATED_WITHDRAWN_TYPE = "BBBBB";
    private static final String DEFAULT_APPLICATION_TYPE = "AAAAA";
    private static final String UPDATED_APPLICATION_TYPE = "BBBBB";

    private static final Boolean DEFAULT_RCV_GR_STATUS = false;
    private static final Boolean UPDATED_RCV_GR_STATUS = true;
    private static final String DEFAULT_WORK_DURATION = "AAAAA";
    private static final String UPDATED_WORK_DURATION = "BBBBB";

    private static final Long DEFAULT_EMERGENCY_CONTACT = 1L;
    private static final Long UPDATED_EMERGENCY_CONTACT = 2L;

    private static final Boolean DEFAULT_BANK_ACC_STATUS = false;
    private static final Boolean UPDATED_BANK_ACC_STATUS = true;
    private static final String DEFAULT_BANK_NAME = "AAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBB";
    private static final String DEFAULT_BANK_ACC = "AAAAA";
    private static final String UPDATED_BANK_ACC = "BBBBB";
    private static final String DEFAULT_BANK_BRANCH = "AAAAA";
    private static final String UPDATED_BANK_BRANCH = "BBBBB";

    private static final LocalDate DEFAULT_APP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APP_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_APP_NO = "AAAAA";
    private static final String UPDATED_APP_NO = "BBBBB";

    private static final Long DEFAULT_APRV_STATUS = 1L;
    private static final Long UPDATED_APRV_STATUS = 2L;

    private static final LocalDate DEFAULT_APRV_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APRV_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_APRV_BY = 1L;
    private static final Long UPDATED_APRV_BY = 2L;

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PgmsAppRetirmntPenRepository pgmsAppRetirmntPenRepository;

    @Inject
    private PgmsAppRetirmntPenSearchRepository pgmsAppRetirmntPenSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsAppRetirmntPenMockMvc;

    private PgmsAppRetirmntPen pgmsAppRetirmntPen;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsAppRetirmntPenResource pgmsAppRetirmntPenResource = new PgmsAppRetirmntPenResource();
        ReflectionTestUtils.setField(pgmsAppRetirmntPenResource, "pgmsAppRetirmntPenRepository", pgmsAppRetirmntPenRepository);
        ReflectionTestUtils.setField(pgmsAppRetirmntPenResource, "pgmsAppRetirmntPenSearchRepository", pgmsAppRetirmntPenSearchRepository);
        this.restPgmsAppRetirmntPenMockMvc = MockMvcBuilders.standaloneSetup(pgmsAppRetirmntPenResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsAppRetirmntPen = new PgmsAppRetirmntPen();
        pgmsAppRetirmntPen.setWithdrawnType(DEFAULT_WITHDRAWN_TYPE);
        pgmsAppRetirmntPen.setApplicationType(DEFAULT_APPLICATION_TYPE);
        pgmsAppRetirmntPen.setRcvGrStatus(DEFAULT_RCV_GR_STATUS);
        pgmsAppRetirmntPen.setWorkDuration(DEFAULT_WORK_DURATION);
        pgmsAppRetirmntPen.setEmergencyContact(DEFAULT_EMERGENCY_CONTACT);
        pgmsAppRetirmntPen.setBankAccStatus(DEFAULT_BANK_ACC_STATUS);
        pgmsAppRetirmntPen.setBankName(DEFAULT_BANK_NAME);
        pgmsAppRetirmntPen.setBankAcc(DEFAULT_BANK_ACC);
        pgmsAppRetirmntPen.setBankBranch(DEFAULT_BANK_BRANCH);
        pgmsAppRetirmntPen.setAppDate(DEFAULT_APP_DATE);
        pgmsAppRetirmntPen.setAppNo(DEFAULT_APP_NO);
        pgmsAppRetirmntPen.setAprvDate(DEFAULT_APRV_DATE);
        pgmsAppRetirmntPen.setAprvBy(DEFAULT_APRV_BY);
        pgmsAppRetirmntPen.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pgmsAppRetirmntPen.setCreateDate(DEFAULT_CREATE_DATE);
        pgmsAppRetirmntPen.setCreateBy(DEFAULT_CREATE_BY);
        pgmsAppRetirmntPen.setUpdateBy(DEFAULT_UPDATE_BY);
        pgmsAppRetirmntPen.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createPgmsAppRetirmntPen() throws Exception {
        int databaseSizeBeforeCreate = pgmsAppRetirmntPenRepository.findAll().size();

        // Create the PgmsAppRetirmntPen

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isCreated());

        // Validate the PgmsAppRetirmntPen in the database
        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeCreate + 1);
        PgmsAppRetirmntPen testPgmsAppRetirmntPen = pgmsAppRetirmntPens.get(pgmsAppRetirmntPens.size() - 1);
        assertThat(testPgmsAppRetirmntPen.getWithdrawnType()).isEqualTo(DEFAULT_WITHDRAWN_TYPE);
        assertThat(testPgmsAppRetirmntPen.getApplicationType()).isEqualTo(DEFAULT_APPLICATION_TYPE);
        assertThat(testPgmsAppRetirmntPen.getRcvGrStatus()).isEqualTo(DEFAULT_RCV_GR_STATUS);
        assertThat(testPgmsAppRetirmntPen.getWorkDuration()).isEqualTo(DEFAULT_WORK_DURATION);
        assertThat(testPgmsAppRetirmntPen.getEmergencyContact()).isEqualTo(DEFAULT_EMERGENCY_CONTACT);
        assertThat(testPgmsAppRetirmntPen.getBankAccStatus()).isEqualTo(DEFAULT_BANK_ACC_STATUS);
        assertThat(testPgmsAppRetirmntPen.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testPgmsAppRetirmntPen.getBankAcc()).isEqualTo(DEFAULT_BANK_ACC);
        assertThat(testPgmsAppRetirmntPen.getBankBranch()).isEqualTo(DEFAULT_BANK_BRANCH);
        assertThat(testPgmsAppRetirmntPen.getAppDate()).isEqualTo(DEFAULT_APP_DATE);
        assertThat(testPgmsAppRetirmntPen.getAppNo()).isEqualTo(DEFAULT_APP_NO);
        assertThat(testPgmsAppRetirmntPen.getAprvStatus()).isEqualTo(DEFAULT_APRV_STATUS);
        assertThat(testPgmsAppRetirmntPen.getAprvDate()).isEqualTo(DEFAULT_APRV_DATE);
        assertThat(testPgmsAppRetirmntPen.getAprvBy()).isEqualTo(DEFAULT_APRV_BY);
        assertThat(testPgmsAppRetirmntPen.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPgmsAppRetirmntPen.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPgmsAppRetirmntPen.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPgmsAppRetirmntPen.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testPgmsAppRetirmntPen.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void checkWithdrawnTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntPenRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntPen.setWithdrawnType(null);

        // Create the PgmsAppRetirmntPen, which fails.

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntPenRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntPen.setApplicationType(null);

        // Create the PgmsAppRetirmntPen, which fails.

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRcvGrStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntPenRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntPen.setRcvGrStatus(null);

        // Create the PgmsAppRetirmntPen, which fails.

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWorkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntPenRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntPen.setWorkDuration(null);

        // Create the PgmsAppRetirmntPen, which fails.

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmergencyContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntPenRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntPen.setEmergencyContact(null);

        // Create the PgmsAppRetirmntPen, which fails.

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankAccStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntPenRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntPen.setBankAccStatus(null);

        // Create the PgmsAppRetirmntPen, which fails.

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntPenRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntPen.setBankName(null);

        // Create the PgmsAppRetirmntPen, which fails.

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankAccIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntPenRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntPen.setBankAcc(null);

        // Create the PgmsAppRetirmntPen, which fails.

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankBranchIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntPenRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntPen.setBankBranch(null);

        // Create the PgmsAppRetirmntPen, which fails.

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntPenRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntPen.setAppDate(null);

        // Create the PgmsAppRetirmntPen, which fails.

        restPgmsAppRetirmntPenMockMvc.perform(post("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsAppRetirmntPens() throws Exception {
        // Initialize the database
        pgmsAppRetirmntPenRepository.saveAndFlush(pgmsAppRetirmntPen);

        // Get all the pgmsAppRetirmntPens
        restPgmsAppRetirmntPenMockMvc.perform(get("/api/pgmsAppRetirmntPens"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsAppRetirmntPen.getId().intValue())))
                .andExpect(jsonPath("$.[*].withdrawnType").value(hasItem(DEFAULT_WITHDRAWN_TYPE.toString())))
                .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].rcvGrStatus").value(hasItem(DEFAULT_RCV_GR_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].workDuration").value(hasItem(DEFAULT_WORK_DURATION.toString())))
                .andExpect(jsonPath("$.[*].emergencyContact").value(hasItem(DEFAULT_EMERGENCY_CONTACT.intValue())))
                .andExpect(jsonPath("$.[*].bankAccStatus").value(hasItem(DEFAULT_BANK_ACC_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
                .andExpect(jsonPath("$.[*].bankAcc").value(hasItem(DEFAULT_BANK_ACC.toString())))
                .andExpect(jsonPath("$.[*].bankBranch").value(hasItem(DEFAULT_BANK_BRANCH.toString())))
                .andExpect(jsonPath("$.[*].appDate").value(hasItem(DEFAULT_APP_DATE.toString())))
                .andExpect(jsonPath("$.[*].appNo").value(hasItem(DEFAULT_APP_NO.toString())))
                .andExpect(jsonPath("$.[*].aprvStatus").value(hasItem(DEFAULT_APRV_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].aprvDate").value(hasItem(DEFAULT_APRV_DATE.toString())))
                .andExpect(jsonPath("$.[*].aprvBy").value(hasItem(DEFAULT_APRV_BY.intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPgmsAppRetirmntPen() throws Exception {
        // Initialize the database
        pgmsAppRetirmntPenRepository.saveAndFlush(pgmsAppRetirmntPen);

        // Get the pgmsAppRetirmntPen
        restPgmsAppRetirmntPenMockMvc.perform(get("/api/pgmsAppRetirmntPens/{id}", pgmsAppRetirmntPen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsAppRetirmntPen.getId().intValue()))
            .andExpect(jsonPath("$.withdrawnType").value(DEFAULT_WITHDRAWN_TYPE.toString()))
            .andExpect(jsonPath("$.applicationType").value(DEFAULT_APPLICATION_TYPE.toString()))
            .andExpect(jsonPath("$.rcvGrStatus").value(DEFAULT_RCV_GR_STATUS.booleanValue()))
            .andExpect(jsonPath("$.workDuration").value(DEFAULT_WORK_DURATION.toString()))
            .andExpect(jsonPath("$.emergencyContact").value(DEFAULT_EMERGENCY_CONTACT.intValue()))
            .andExpect(jsonPath("$.bankAccStatus").value(DEFAULT_BANK_ACC_STATUS.booleanValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.bankAcc").value(DEFAULT_BANK_ACC.toString()))
            .andExpect(jsonPath("$.bankBranch").value(DEFAULT_BANK_BRANCH.toString()))
            .andExpect(jsonPath("$.appDate").value(DEFAULT_APP_DATE.toString()))
            .andExpect(jsonPath("$.appNo").value(DEFAULT_APP_NO.toString()))
            .andExpect(jsonPath("$.aprvStatus").value(DEFAULT_APRV_STATUS.intValue()))
            .andExpect(jsonPath("$.aprvDate").value(DEFAULT_APRV_DATE.toString()))
            .andExpect(jsonPath("$.aprvBy").value(DEFAULT_APRV_BY.intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsAppRetirmntPen() throws Exception {
        // Get the pgmsAppRetirmntPen
        restPgmsAppRetirmntPenMockMvc.perform(get("/api/pgmsAppRetirmntPens/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsAppRetirmntPen() throws Exception {
        // Initialize the database
        pgmsAppRetirmntPenRepository.saveAndFlush(pgmsAppRetirmntPen);

		int databaseSizeBeforeUpdate = pgmsAppRetirmntPenRepository.findAll().size();

        // Update the pgmsAppRetirmntPen
        pgmsAppRetirmntPen.setWithdrawnType(UPDATED_WITHDRAWN_TYPE);
        pgmsAppRetirmntPen.setApplicationType(UPDATED_APPLICATION_TYPE);
        pgmsAppRetirmntPen.setRcvGrStatus(UPDATED_RCV_GR_STATUS);
        pgmsAppRetirmntPen.setWorkDuration(UPDATED_WORK_DURATION);
        pgmsAppRetirmntPen.setEmergencyContact(UPDATED_EMERGENCY_CONTACT);
        pgmsAppRetirmntPen.setBankAccStatus(UPDATED_BANK_ACC_STATUS);
        pgmsAppRetirmntPen.setBankName(UPDATED_BANK_NAME);
        pgmsAppRetirmntPen.setBankAcc(UPDATED_BANK_ACC);
        pgmsAppRetirmntPen.setBankBranch(UPDATED_BANK_BRANCH);
        pgmsAppRetirmntPen.setAppDate(UPDATED_APP_DATE);
        pgmsAppRetirmntPen.setAppNo(UPDATED_APP_NO);
        pgmsAppRetirmntPen.setAprvDate(UPDATED_APRV_DATE);
        pgmsAppRetirmntPen.setAprvBy(UPDATED_APRV_BY);
        pgmsAppRetirmntPen.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pgmsAppRetirmntPen.setCreateDate(UPDATED_CREATE_DATE);
        pgmsAppRetirmntPen.setCreateBy(UPDATED_CREATE_BY);
        pgmsAppRetirmntPen.setUpdateBy(UPDATED_UPDATE_BY);
        pgmsAppRetirmntPen.setUpdateDate(UPDATED_UPDATE_DATE);

        restPgmsAppRetirmntPenMockMvc.perform(put("/api/pgmsAppRetirmntPens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntPen)))
                .andExpect(status().isOk());

        // Validate the PgmsAppRetirmntPen in the database
        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeUpdate);
        PgmsAppRetirmntPen testPgmsAppRetirmntPen = pgmsAppRetirmntPens.get(pgmsAppRetirmntPens.size() - 1);
        assertThat(testPgmsAppRetirmntPen.getWithdrawnType()).isEqualTo(UPDATED_WITHDRAWN_TYPE);
        assertThat(testPgmsAppRetirmntPen.getApplicationType()).isEqualTo(UPDATED_APPLICATION_TYPE);
        assertThat(testPgmsAppRetirmntPen.getRcvGrStatus()).isEqualTo(UPDATED_RCV_GR_STATUS);
        assertThat(testPgmsAppRetirmntPen.getWorkDuration()).isEqualTo(UPDATED_WORK_DURATION);
        assertThat(testPgmsAppRetirmntPen.getEmergencyContact()).isEqualTo(UPDATED_EMERGENCY_CONTACT);
        assertThat(testPgmsAppRetirmntPen.getBankAccStatus()).isEqualTo(UPDATED_BANK_ACC_STATUS);
        assertThat(testPgmsAppRetirmntPen.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testPgmsAppRetirmntPen.getBankAcc()).isEqualTo(UPDATED_BANK_ACC);
        assertThat(testPgmsAppRetirmntPen.getBankBranch()).isEqualTo(UPDATED_BANK_BRANCH);
        assertThat(testPgmsAppRetirmntPen.getAppDate()).isEqualTo(UPDATED_APP_DATE);
        assertThat(testPgmsAppRetirmntPen.getAppNo()).isEqualTo(UPDATED_APP_NO);
        assertThat(testPgmsAppRetirmntPen.getAprvStatus()).isEqualTo(UPDATED_APRV_STATUS);
        assertThat(testPgmsAppRetirmntPen.getAprvDate()).isEqualTo(UPDATED_APRV_DATE);
        assertThat(testPgmsAppRetirmntPen.getAprvBy()).isEqualTo(UPDATED_APRV_BY);
        assertThat(testPgmsAppRetirmntPen.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPgmsAppRetirmntPen.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPgmsAppRetirmntPen.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPgmsAppRetirmntPen.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testPgmsAppRetirmntPen.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deletePgmsAppRetirmntPen() throws Exception {
        // Initialize the database
        pgmsAppRetirmntPenRepository.saveAndFlush(pgmsAppRetirmntPen);

		int databaseSizeBeforeDelete = pgmsAppRetirmntPenRepository.findAll().size();

        // Get the pgmsAppRetirmntPen
        restPgmsAppRetirmntPenMockMvc.perform(delete("/api/pgmsAppRetirmntPens/{id}", pgmsAppRetirmntPen.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsAppRetirmntPen> pgmsAppRetirmntPens = pgmsAppRetirmntPenRepository.findAll();
        assertThat(pgmsAppRetirmntPens).hasSize(databaseSizeBeforeDelete - 1);
    }
}
