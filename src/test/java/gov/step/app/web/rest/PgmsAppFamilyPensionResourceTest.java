package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsAppFamilyPension;
import gov.step.app.repository.PgmsAppFamilyPensionRepository;
import gov.step.app.repository.search.PgmsAppFamilyPensionSearchRepository;

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
 * Test class for the PgmsAppFamilyPensionResource REST controller.
 *
 * @see PgmsAppFamilyPensionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsAppFamilyPensionResourceTest {

    private static final String DEFAULT_EMP_NAME = "AAAAA";
    private static final String UPDATED_EMP_NAME = "BBBBB";
    private static final String DEFAULT_EMP_DEPARTMENT = "AAAAA";
    private static final String UPDATED_EMP_DEPARTMENT = "BBBBB";
    private static final String DEFAULT_EMP_DESIGNATION = "AAAAA";
    private static final String UPDATED_EMP_DESIGNATION = "BBBBB";
    private static final String DEFAULT_EMP_NID = "AAAAA";
    private static final String UPDATED_EMP_NID = "BBBBB";

    private static final Boolean DEFAULT_NOMINEE_STATUS = false;
    private static final Boolean UPDATED_NOMINEE_STATUS = true;
    private static final String DEFAULT_NOMINE_NAME = "AAAAA";
    private static final String UPDATED_NOMINE_NAME = "BBBBB";

    private static final LocalDate DEFAULT_NOMINE_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NOMINE_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_NOMINE_GENDER = "AAAAA";
    private static final String UPDATED_NOMINE_GENDER = "BBBBB";
    private static final String DEFAULT_NOMINE_RELATION = "AAAAA";
    private static final String UPDATED_NOMINE_RELATION = "BBBBB";
    private static final String DEFAULT_NOMINE_OCCUPATION = "AAAAA";
    private static final String UPDATED_NOMINE_OCCUPATION = "BBBBB";
    private static final String DEFAULT_NOMINE_DESIGNATION = "AAAAA";
    private static final String UPDATED_NOMINE_DESIGNATION = "BBBBB";
    private static final String DEFAULT_NOMINE_PRE_ADDRESS = "AAAAA";
    private static final String UPDATED_NOMINE_PRE_ADDRESS = "BBBBB";
    private static final String DEFAULT_NOMINE_PAR_ADDRESS = "AAAAA";
    private static final String UPDATED_NOMINE_PAR_ADDRESS = "BBBBB";
    private static final String DEFAULT_NOMINE_NID = "AAAAA";
    private static final String UPDATED_NOMINE_NID = "BBBBB";

    private static final Long DEFAULT_NOMINE_CONT_NO = 1L;
    private static final Long UPDATED_NOMINE_CONT_NO = 2L;
    private static final String DEFAULT_NOMINE_BANK_NAME = "AAAAA";
    private static final String UPDATED_NOMINE_BANK_NAME = "BBBBB";
    private static final String DEFAULT_NOMINE_BRANCH_NAME = "AAAAA";
    private static final String UPDATED_NOMINE_BRANCH_NAME = "BBBBB";
    private static final String DEFAULT_NOMINE_ACC_NO = "AAAAA";
    private static final String UPDATED_NOMINE_ACC_NO = "BBBBB";

    private static final LocalDate DEFAULT_APPLY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLY_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private PgmsAppFamilyPensionRepository pgmsAppFamilyPensionRepository;

    @Inject
    private PgmsAppFamilyPensionSearchRepository pgmsAppFamilyPensionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsAppFamilyPensionMockMvc;

    private PgmsAppFamilyPension pgmsAppFamilyPension;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsAppFamilyPensionResource pgmsAppFamilyPensionResource = new PgmsAppFamilyPensionResource();
        ReflectionTestUtils.setField(pgmsAppFamilyPensionResource, "pgmsAppFamilyPensionRepository", pgmsAppFamilyPensionRepository);
        ReflectionTestUtils.setField(pgmsAppFamilyPensionResource, "pgmsAppFamilyPensionSearchRepository", pgmsAppFamilyPensionSearchRepository);
        this.restPgmsAppFamilyPensionMockMvc = MockMvcBuilders.standaloneSetup(pgmsAppFamilyPensionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsAppFamilyPension = new PgmsAppFamilyPension();
        pgmsAppFamilyPension.setEmpName(DEFAULT_EMP_NAME);
        pgmsAppFamilyPension.setEmpDepartment(DEFAULT_EMP_DEPARTMENT);
        pgmsAppFamilyPension.setEmpDesignation(DEFAULT_EMP_DESIGNATION);
        pgmsAppFamilyPension.setEmpNid(DEFAULT_EMP_NID);
        pgmsAppFamilyPension.setNomineeStatus(DEFAULT_NOMINEE_STATUS);
        pgmsAppFamilyPension.setNomineName(DEFAULT_NOMINE_NAME);
        pgmsAppFamilyPension.setNomineDob(DEFAULT_NOMINE_DOB);
        pgmsAppFamilyPension.setNomineGender(DEFAULT_NOMINE_GENDER);
        pgmsAppFamilyPension.setNomineRelation(DEFAULT_NOMINE_RELATION);
        pgmsAppFamilyPension.setNomineOccupation(DEFAULT_NOMINE_OCCUPATION);
        pgmsAppFamilyPension.setNomineDesignation(DEFAULT_NOMINE_DESIGNATION);
        pgmsAppFamilyPension.setNominePreAddress(DEFAULT_NOMINE_PRE_ADDRESS);
        pgmsAppFamilyPension.setNomineParAddress(DEFAULT_NOMINE_PAR_ADDRESS);
        pgmsAppFamilyPension.setNomineNid(DEFAULT_NOMINE_NID);
        pgmsAppFamilyPension.setNomineContNo(DEFAULT_NOMINE_CONT_NO);
        pgmsAppFamilyPension.setNomineBankName(DEFAULT_NOMINE_BANK_NAME);
        pgmsAppFamilyPension.setNomineBranchName(DEFAULT_NOMINE_BRANCH_NAME);
        pgmsAppFamilyPension.setNomineAccNo(DEFAULT_NOMINE_ACC_NO);
        pgmsAppFamilyPension.setApplyDate(DEFAULT_APPLY_DATE);
        pgmsAppFamilyPension.setAprvDate(DEFAULT_APRV_DATE);
        pgmsAppFamilyPension.setAprvBy(DEFAULT_APRV_BY);
        pgmsAppFamilyPension.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pgmsAppFamilyPension.setCreateDate(DEFAULT_CREATE_DATE);
        pgmsAppFamilyPension.setCreateBy(DEFAULT_CREATE_BY);
        pgmsAppFamilyPension.setUpdateBy(DEFAULT_UPDATE_BY);
        pgmsAppFamilyPension.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createPgmsAppFamilyPension() throws Exception {
        int databaseSizeBeforeCreate = pgmsAppFamilyPensionRepository.findAll().size();

        // Create the PgmsAppFamilyPension

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isCreated());

        // Validate the PgmsAppFamilyPension in the database
        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeCreate + 1);
        PgmsAppFamilyPension testPgmsAppFamilyPension = pgmsAppFamilyPensions.get(pgmsAppFamilyPensions.size() - 1);
        assertThat(testPgmsAppFamilyPension.getEmpName()).isEqualTo(DEFAULT_EMP_NAME);
        assertThat(testPgmsAppFamilyPension.getEmpDepartment()).isEqualTo(DEFAULT_EMP_DEPARTMENT);
        assertThat(testPgmsAppFamilyPension.getEmpDesignation()).isEqualTo(DEFAULT_EMP_DESIGNATION);
        assertThat(testPgmsAppFamilyPension.getEmpNid()).isEqualTo(DEFAULT_EMP_NID);
        assertThat(testPgmsAppFamilyPension.getNomineeStatus()).isEqualTo(DEFAULT_NOMINEE_STATUS);
        assertThat(testPgmsAppFamilyPension.getNomineName()).isEqualTo(DEFAULT_NOMINE_NAME);
        assertThat(testPgmsAppFamilyPension.getNomineDob()).isEqualTo(DEFAULT_NOMINE_DOB);
        assertThat(testPgmsAppFamilyPension.getNomineGender()).isEqualTo(DEFAULT_NOMINE_GENDER);
        assertThat(testPgmsAppFamilyPension.getNomineRelation()).isEqualTo(DEFAULT_NOMINE_RELATION);
        assertThat(testPgmsAppFamilyPension.getNomineOccupation()).isEqualTo(DEFAULT_NOMINE_OCCUPATION);
        assertThat(testPgmsAppFamilyPension.getNomineDesignation()).isEqualTo(DEFAULT_NOMINE_DESIGNATION);
        assertThat(testPgmsAppFamilyPension.getNominePreAddress()).isEqualTo(DEFAULT_NOMINE_PRE_ADDRESS);
        assertThat(testPgmsAppFamilyPension.getNomineParAddress()).isEqualTo(DEFAULT_NOMINE_PAR_ADDRESS);
        assertThat(testPgmsAppFamilyPension.getNomineNid()).isEqualTo(DEFAULT_NOMINE_NID);
        assertThat(testPgmsAppFamilyPension.getNomineContNo()).isEqualTo(DEFAULT_NOMINE_CONT_NO);
        assertThat(testPgmsAppFamilyPension.getNomineBankName()).isEqualTo(DEFAULT_NOMINE_BANK_NAME);
        assertThat(testPgmsAppFamilyPension.getNomineBranchName()).isEqualTo(DEFAULT_NOMINE_BRANCH_NAME);
        assertThat(testPgmsAppFamilyPension.getNomineAccNo()).isEqualTo(DEFAULT_NOMINE_ACC_NO);
        assertThat(testPgmsAppFamilyPension.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
        assertThat(testPgmsAppFamilyPension.getAprvStatus()).isEqualTo(DEFAULT_APRV_STATUS);
        assertThat(testPgmsAppFamilyPension.getAprvDate()).isEqualTo(DEFAULT_APRV_DATE);
        assertThat(testPgmsAppFamilyPension.getAprvBy()).isEqualTo(DEFAULT_APRV_BY);
        assertThat(testPgmsAppFamilyPension.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPgmsAppFamilyPension.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPgmsAppFamilyPension.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPgmsAppFamilyPension.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testPgmsAppFamilyPension.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void checkEmpNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setEmpName(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpDepartmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setEmpDepartment(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setEmpDesignation(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpNidIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setEmpNid(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineName(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineDob(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineGender(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineRelationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineRelation(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineOccupationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineOccupation(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineDesignation(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNominePreAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNominePreAddress(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineParAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineParAddress(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineNidIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineNid(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineContNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineContNo(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineBankName(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineBranchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineBranchName(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineAccNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setNomineAccNo(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplyDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyPensionRepository.findAll().size();
        // set the field null
        pgmsAppFamilyPension.setApplyDate(null);

        // Create the PgmsAppFamilyPension, which fails.

        restPgmsAppFamilyPensionMockMvc.perform(post("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsAppFamilyPensions() throws Exception {
        // Initialize the database
        pgmsAppFamilyPensionRepository.saveAndFlush(pgmsAppFamilyPension);

        // Get all the pgmsAppFamilyPensions
        restPgmsAppFamilyPensionMockMvc.perform(get("/api/pgmsAppFamilyPensions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsAppFamilyPension.getId().intValue())))
                .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME.toString())))
                .andExpect(jsonPath("$.[*].empDepartment").value(hasItem(DEFAULT_EMP_DEPARTMENT.toString())))
                .andExpect(jsonPath("$.[*].empDesignation").value(hasItem(DEFAULT_EMP_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].empNid").value(hasItem(DEFAULT_EMP_NID.toString())))
                .andExpect(jsonPath("$.[*].nomineeStatus").value(hasItem(DEFAULT_NOMINEE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].nomineName").value(hasItem(DEFAULT_NOMINE_NAME.toString())))
                .andExpect(jsonPath("$.[*].nomineDob").value(hasItem(DEFAULT_NOMINE_DOB.toString())))
                .andExpect(jsonPath("$.[*].nomineGender").value(hasItem(DEFAULT_NOMINE_GENDER.toString())))
                .andExpect(jsonPath("$.[*].nomineRelation").value(hasItem(DEFAULT_NOMINE_RELATION.toString())))
                .andExpect(jsonPath("$.[*].nomineOccupation").value(hasItem(DEFAULT_NOMINE_OCCUPATION.toString())))
                .andExpect(jsonPath("$.[*].nomineDesignation").value(hasItem(DEFAULT_NOMINE_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].nominePreAddress").value(hasItem(DEFAULT_NOMINE_PRE_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].nomineParAddress").value(hasItem(DEFAULT_NOMINE_PAR_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].nomineNid").value(hasItem(DEFAULT_NOMINE_NID.toString())))
                .andExpect(jsonPath("$.[*].nomineContNo").value(hasItem(DEFAULT_NOMINE_CONT_NO.intValue())))
                .andExpect(jsonPath("$.[*].nomineBankName").value(hasItem(DEFAULT_NOMINE_BANK_NAME.toString())))
                .andExpect(jsonPath("$.[*].nomineBranchName").value(hasItem(DEFAULT_NOMINE_BRANCH_NAME.toString())))
                .andExpect(jsonPath("$.[*].nomineAccNo").value(hasItem(DEFAULT_NOMINE_ACC_NO.toString())))
                .andExpect(jsonPath("$.[*].ApplyDate").value(hasItem(DEFAULT_APPLY_DATE.toString())))
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
    public void getPgmsAppFamilyPension() throws Exception {
        // Initialize the database
        pgmsAppFamilyPensionRepository.saveAndFlush(pgmsAppFamilyPension);

        // Get the pgmsAppFamilyPension
        restPgmsAppFamilyPensionMockMvc.perform(get("/api/pgmsAppFamilyPensions/{id}", pgmsAppFamilyPension.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsAppFamilyPension.getId().intValue()))
            .andExpect(jsonPath("$.empName").value(DEFAULT_EMP_NAME.toString()))
            .andExpect(jsonPath("$.empDepartment").value(DEFAULT_EMP_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.empDesignation").value(DEFAULT_EMP_DESIGNATION.toString()))
            .andExpect(jsonPath("$.empNid").value(DEFAULT_EMP_NID.toString()))
            .andExpect(jsonPath("$.nomineeStatus").value(DEFAULT_NOMINEE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.nomineName").value(DEFAULT_NOMINE_NAME.toString()))
            .andExpect(jsonPath("$.nomineDob").value(DEFAULT_NOMINE_DOB.toString()))
            .andExpect(jsonPath("$.nomineGender").value(DEFAULT_NOMINE_GENDER.toString()))
            .andExpect(jsonPath("$.nomineRelation").value(DEFAULT_NOMINE_RELATION.toString()))
            .andExpect(jsonPath("$.nomineOccupation").value(DEFAULT_NOMINE_OCCUPATION.toString()))
            .andExpect(jsonPath("$.nomineDesignation").value(DEFAULT_NOMINE_DESIGNATION.toString()))
            .andExpect(jsonPath("$.nominePreAddress").value(DEFAULT_NOMINE_PRE_ADDRESS.toString()))
            .andExpect(jsonPath("$.nomineParAddress").value(DEFAULT_NOMINE_PAR_ADDRESS.toString()))
            .andExpect(jsonPath("$.nomineNid").value(DEFAULT_NOMINE_NID.toString()))
            .andExpect(jsonPath("$.nomineContNo").value(DEFAULT_NOMINE_CONT_NO.intValue()))
            .andExpect(jsonPath("$.nomineBankName").value(DEFAULT_NOMINE_BANK_NAME.toString()))
            .andExpect(jsonPath("$.nomineBranchName").value(DEFAULT_NOMINE_BRANCH_NAME.toString()))
            .andExpect(jsonPath("$.nomineAccNo").value(DEFAULT_NOMINE_ACC_NO.toString()))
            .andExpect(jsonPath("$.ApplyDate").value(DEFAULT_APPLY_DATE.toString()))
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
    public void getNonExistingPgmsAppFamilyPension() throws Exception {
        // Get the pgmsAppFamilyPension
        restPgmsAppFamilyPensionMockMvc.perform(get("/api/pgmsAppFamilyPensions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsAppFamilyPension() throws Exception {
        // Initialize the database
        pgmsAppFamilyPensionRepository.saveAndFlush(pgmsAppFamilyPension);

		int databaseSizeBeforeUpdate = pgmsAppFamilyPensionRepository.findAll().size();

        // Update the pgmsAppFamilyPension
        pgmsAppFamilyPension.setEmpName(UPDATED_EMP_NAME);
        pgmsAppFamilyPension.setEmpDepartment(UPDATED_EMP_DEPARTMENT);
        pgmsAppFamilyPension.setEmpDesignation(UPDATED_EMP_DESIGNATION);
        pgmsAppFamilyPension.setEmpNid(UPDATED_EMP_NID);
        pgmsAppFamilyPension.setNomineeStatus(UPDATED_NOMINEE_STATUS);
        pgmsAppFamilyPension.setNomineName(UPDATED_NOMINE_NAME);
        pgmsAppFamilyPension.setNomineDob(UPDATED_NOMINE_DOB);
        pgmsAppFamilyPension.setNomineGender(UPDATED_NOMINE_GENDER);
        pgmsAppFamilyPension.setNomineRelation(UPDATED_NOMINE_RELATION);
        pgmsAppFamilyPension.setNomineOccupation(UPDATED_NOMINE_OCCUPATION);
        pgmsAppFamilyPension.setNomineDesignation(UPDATED_NOMINE_DESIGNATION);
        pgmsAppFamilyPension.setNominePreAddress(UPDATED_NOMINE_PRE_ADDRESS);
        pgmsAppFamilyPension.setNomineParAddress(UPDATED_NOMINE_PAR_ADDRESS);
        pgmsAppFamilyPension.setNomineNid(UPDATED_NOMINE_NID);
        pgmsAppFamilyPension.setNomineContNo(UPDATED_NOMINE_CONT_NO);
        pgmsAppFamilyPension.setNomineBankName(UPDATED_NOMINE_BANK_NAME);
        pgmsAppFamilyPension.setNomineBranchName(UPDATED_NOMINE_BRANCH_NAME);
        pgmsAppFamilyPension.setNomineAccNo(UPDATED_NOMINE_ACC_NO);
        pgmsAppFamilyPension.setApplyDate(UPDATED_APPLY_DATE);
        pgmsAppFamilyPension.setAprvDate(UPDATED_APRV_DATE);
        pgmsAppFamilyPension.setAprvBy(UPDATED_APRV_BY);
        pgmsAppFamilyPension.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pgmsAppFamilyPension.setCreateDate(UPDATED_CREATE_DATE);
        pgmsAppFamilyPension.setCreateBy(UPDATED_CREATE_BY);
        pgmsAppFamilyPension.setUpdateBy(UPDATED_UPDATE_BY);
        pgmsAppFamilyPension.setUpdateDate(UPDATED_UPDATE_DATE);

        restPgmsAppFamilyPensionMockMvc.perform(put("/api/pgmsAppFamilyPensions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyPension)))
                .andExpect(status().isOk());

        // Validate the PgmsAppFamilyPension in the database
        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeUpdate);
        PgmsAppFamilyPension testPgmsAppFamilyPension = pgmsAppFamilyPensions.get(pgmsAppFamilyPensions.size() - 1);
        assertThat(testPgmsAppFamilyPension.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testPgmsAppFamilyPension.getEmpDepartment()).isEqualTo(UPDATED_EMP_DEPARTMENT);
        assertThat(testPgmsAppFamilyPension.getEmpDesignation()).isEqualTo(UPDATED_EMP_DESIGNATION);
        assertThat(testPgmsAppFamilyPension.getEmpNid()).isEqualTo(UPDATED_EMP_NID);
        assertThat(testPgmsAppFamilyPension.getNomineeStatus()).isEqualTo(UPDATED_NOMINEE_STATUS);
        assertThat(testPgmsAppFamilyPension.getNomineName()).isEqualTo(UPDATED_NOMINE_NAME);
        assertThat(testPgmsAppFamilyPension.getNomineDob()).isEqualTo(UPDATED_NOMINE_DOB);
        assertThat(testPgmsAppFamilyPension.getNomineGender()).isEqualTo(UPDATED_NOMINE_GENDER);
        assertThat(testPgmsAppFamilyPension.getNomineRelation()).isEqualTo(UPDATED_NOMINE_RELATION);
        assertThat(testPgmsAppFamilyPension.getNomineOccupation()).isEqualTo(UPDATED_NOMINE_OCCUPATION);
        assertThat(testPgmsAppFamilyPension.getNomineDesignation()).isEqualTo(UPDATED_NOMINE_DESIGNATION);
        assertThat(testPgmsAppFamilyPension.getNominePreAddress()).isEqualTo(UPDATED_NOMINE_PRE_ADDRESS);
        assertThat(testPgmsAppFamilyPension.getNomineParAddress()).isEqualTo(UPDATED_NOMINE_PAR_ADDRESS);
        assertThat(testPgmsAppFamilyPension.getNomineNid()).isEqualTo(UPDATED_NOMINE_NID);
        assertThat(testPgmsAppFamilyPension.getNomineContNo()).isEqualTo(UPDATED_NOMINE_CONT_NO);
        assertThat(testPgmsAppFamilyPension.getNomineBankName()).isEqualTo(UPDATED_NOMINE_BANK_NAME);
        assertThat(testPgmsAppFamilyPension.getNomineBranchName()).isEqualTo(UPDATED_NOMINE_BRANCH_NAME);
        assertThat(testPgmsAppFamilyPension.getNomineAccNo()).isEqualTo(UPDATED_NOMINE_ACC_NO);
        assertThat(testPgmsAppFamilyPension.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
        assertThat(testPgmsAppFamilyPension.getAprvStatus()).isEqualTo(UPDATED_APRV_STATUS);
        assertThat(testPgmsAppFamilyPension.getAprvDate()).isEqualTo(UPDATED_APRV_DATE);
        assertThat(testPgmsAppFamilyPension.getAprvBy()).isEqualTo(UPDATED_APRV_BY);
        assertThat(testPgmsAppFamilyPension.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPgmsAppFamilyPension.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPgmsAppFamilyPension.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPgmsAppFamilyPension.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testPgmsAppFamilyPension.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deletePgmsAppFamilyPension() throws Exception {
        // Initialize the database
        pgmsAppFamilyPensionRepository.saveAndFlush(pgmsAppFamilyPension);

		int databaseSizeBeforeDelete = pgmsAppFamilyPensionRepository.findAll().size();

        // Get the pgmsAppFamilyPension
        restPgmsAppFamilyPensionMockMvc.perform(delete("/api/pgmsAppFamilyPensions/{id}", pgmsAppFamilyPension.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsAppFamilyPension> pgmsAppFamilyPensions = pgmsAppFamilyPensionRepository.findAll();
        assertThat(pgmsAppFamilyPensions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
