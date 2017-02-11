package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsGrObtainSpecEmp;
import gov.step.app.repository.PgmsGrObtainSpecEmpRepository;
import gov.step.app.repository.search.PgmsGrObtainSpecEmpSearchRepository;

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
 * Test class for the PgmsGrObtainSpecEmpResource REST controller.
 *
 * @see PgmsGrObtainSpecEmpResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsGrObtainSpecEmpResourceTest {

    private static final String DEFAULT_EMP_NAME = "AAAAA";
    private static final String UPDATED_EMP_NAME = "BBBBB";
    private static final String DEFAULT_EMP_DESIGNATION = "AAAAA";
    private static final String UPDATED_EMP_DESIGNATION = "BBBBB";
    private static final String DEFAULT_EMP_DEPARTMENT = "AAAAA";
    private static final String UPDATED_EMP_DEPARTMENT = "BBBBB";

    private static final LocalDate DEFAULT_EMP_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMP_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_EMP_STATUS = false;
    private static final Boolean UPDATED_EMP_STATUS = true;

    private static final Long DEFAULT_EMP_WRKING_YEAR = 1L;
    private static final Long UPDATED_EMP_WRKING_YEAR = 2L;

    private static final Long DEFAULT_AMOUNT_AS_GR = 1L;
    private static final Long UPDATED_AMOUNT_AS_GR = 2L;

    private static final LocalDate DEFAULT_OBTAIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OBTAIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private PgmsGrObtainSpecEmpRepository pgmsGrObtainSpecEmpRepository;

    @Inject
    private PgmsGrObtainSpecEmpSearchRepository pgmsGrObtainSpecEmpSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsGrObtainSpecEmpMockMvc;

    private PgmsGrObtainSpecEmp pgmsGrObtainSpecEmp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsGrObtainSpecEmpResource pgmsGrObtainSpecEmpResource = new PgmsGrObtainSpecEmpResource();
        ReflectionTestUtils.setField(pgmsGrObtainSpecEmpResource, "pgmsGrObtainSpecEmpRepository", pgmsGrObtainSpecEmpRepository);
        ReflectionTestUtils.setField(pgmsGrObtainSpecEmpResource, "pgmsGrObtainSpecEmpSearchRepository", pgmsGrObtainSpecEmpSearchRepository);
        this.restPgmsGrObtainSpecEmpMockMvc = MockMvcBuilders.standaloneSetup(pgmsGrObtainSpecEmpResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsGrObtainSpecEmp = new PgmsGrObtainSpecEmp();
        pgmsGrObtainSpecEmp.setEmpName(DEFAULT_EMP_NAME);
        pgmsGrObtainSpecEmp.setEmpDesignation(DEFAULT_EMP_DESIGNATION);
        pgmsGrObtainSpecEmp.setEmpDepartment(DEFAULT_EMP_DEPARTMENT);
        pgmsGrObtainSpecEmp.setEmpEndDate(DEFAULT_EMP_END_DATE);
        pgmsGrObtainSpecEmp.setEmpStatus(DEFAULT_EMP_STATUS);
        pgmsGrObtainSpecEmp.setEmpWrkingYear(DEFAULT_EMP_WRKING_YEAR);
        pgmsGrObtainSpecEmp.setAmountAsGr(DEFAULT_AMOUNT_AS_GR);
        pgmsGrObtainSpecEmp.setObtainDate(DEFAULT_OBTAIN_DATE);
        pgmsGrObtainSpecEmp.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pgmsGrObtainSpecEmp.setCreateDate(DEFAULT_CREATE_DATE);
        pgmsGrObtainSpecEmp.setCreateBy(DEFAULT_CREATE_BY);
        pgmsGrObtainSpecEmp.setUpdateDate(DEFAULT_UPDATE_DATE);
        pgmsGrObtainSpecEmp.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPgmsGrObtainSpecEmp() throws Exception {
        int databaseSizeBeforeCreate = pgmsGrObtainSpecEmpRepository.findAll().size();

        // Create the PgmsGrObtainSpecEmp

        restPgmsGrObtainSpecEmpMockMvc.perform(post("/api/pgmsGrObtainSpecEmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsGrObtainSpecEmp)))
                .andExpect(status().isCreated());

        // Validate the PgmsGrObtainSpecEmp in the database
        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeCreate + 1);
        PgmsGrObtainSpecEmp testPgmsGrObtainSpecEmp = pgmsGrObtainSpecEmps.get(pgmsGrObtainSpecEmps.size() - 1);
        assertThat(testPgmsGrObtainSpecEmp.getEmpName()).isEqualTo(DEFAULT_EMP_NAME);
        assertThat(testPgmsGrObtainSpecEmp.getEmpDesignation()).isEqualTo(DEFAULT_EMP_DESIGNATION);
        assertThat(testPgmsGrObtainSpecEmp.getEmpDepartment()).isEqualTo(DEFAULT_EMP_DEPARTMENT);
        assertThat(testPgmsGrObtainSpecEmp.getEmpEndDate()).isEqualTo(DEFAULT_EMP_END_DATE);
        assertThat(testPgmsGrObtainSpecEmp.getEmpStatus()).isEqualTo(DEFAULT_EMP_STATUS);
        assertThat(testPgmsGrObtainSpecEmp.getEmpWrkingYear()).isEqualTo(DEFAULT_EMP_WRKING_YEAR);
        assertThat(testPgmsGrObtainSpecEmp.getAmountAsGr()).isEqualTo(DEFAULT_AMOUNT_AS_GR);
        assertThat(testPgmsGrObtainSpecEmp.getObtainDate()).isEqualTo(DEFAULT_OBTAIN_DATE);
        assertThat(testPgmsGrObtainSpecEmp.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPgmsGrObtainSpecEmp.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPgmsGrObtainSpecEmp.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPgmsGrObtainSpecEmp.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPgmsGrObtainSpecEmp.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkEmpNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsGrObtainSpecEmpRepository.findAll().size();
        // set the field null
        pgmsGrObtainSpecEmp.setEmpName(null);

        // Create the PgmsGrObtainSpecEmp, which fails.

        restPgmsGrObtainSpecEmpMockMvc.perform(post("/api/pgmsGrObtainSpecEmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsGrObtainSpecEmp)))
                .andExpect(status().isBadRequest());

        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsGrObtainSpecEmpRepository.findAll().size();
        // set the field null
        pgmsGrObtainSpecEmp.setEmpDesignation(null);

        // Create the PgmsGrObtainSpecEmp, which fails.

        restPgmsGrObtainSpecEmpMockMvc.perform(post("/api/pgmsGrObtainSpecEmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsGrObtainSpecEmp)))
                .andExpect(status().isBadRequest());

        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpDepartmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsGrObtainSpecEmpRepository.findAll().size();
        // set the field null
        pgmsGrObtainSpecEmp.setEmpDepartment(null);

        // Create the PgmsGrObtainSpecEmp, which fails.

        restPgmsGrObtainSpecEmpMockMvc.perform(post("/api/pgmsGrObtainSpecEmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsGrObtainSpecEmp)))
                .andExpect(status().isBadRequest());

        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsGrObtainSpecEmpRepository.findAll().size();
        // set the field null
        pgmsGrObtainSpecEmp.setEmpEndDate(null);

        // Create the PgmsGrObtainSpecEmp, which fails.

        restPgmsGrObtainSpecEmpMockMvc.perform(post("/api/pgmsGrObtainSpecEmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsGrObtainSpecEmp)))
                .andExpect(status().isBadRequest());

        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsGrObtainSpecEmpRepository.findAll().size();
        // set the field null
        pgmsGrObtainSpecEmp.setEmpStatus(null);

        // Create the PgmsGrObtainSpecEmp, which fails.

        restPgmsGrObtainSpecEmpMockMvc.perform(post("/api/pgmsGrObtainSpecEmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsGrObtainSpecEmp)))
                .andExpect(status().isBadRequest());

        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpWrkingYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsGrObtainSpecEmpRepository.findAll().size();
        // set the field null
        pgmsGrObtainSpecEmp.setEmpWrkingYear(null);

        // Create the PgmsGrObtainSpecEmp, which fails.

        restPgmsGrObtainSpecEmpMockMvc.perform(post("/api/pgmsGrObtainSpecEmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsGrObtainSpecEmp)))
                .andExpect(status().isBadRequest());

        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountAsGrIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsGrObtainSpecEmpRepository.findAll().size();
        // set the field null
        pgmsGrObtainSpecEmp.setAmountAsGr(null);

        // Create the PgmsGrObtainSpecEmp, which fails.

        restPgmsGrObtainSpecEmpMockMvc.perform(post("/api/pgmsGrObtainSpecEmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsGrObtainSpecEmp)))
                .andExpect(status().isBadRequest());

        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObtainDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsGrObtainSpecEmpRepository.findAll().size();
        // set the field null
        pgmsGrObtainSpecEmp.setObtainDate(null);

        // Create the PgmsGrObtainSpecEmp, which fails.

        restPgmsGrObtainSpecEmpMockMvc.perform(post("/api/pgmsGrObtainSpecEmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsGrObtainSpecEmp)))
                .andExpect(status().isBadRequest());

        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsGrObtainSpecEmps() throws Exception {
        // Initialize the database
        pgmsGrObtainSpecEmpRepository.saveAndFlush(pgmsGrObtainSpecEmp);

        // Get all the pgmsGrObtainSpecEmps
        restPgmsGrObtainSpecEmpMockMvc.perform(get("/api/pgmsGrObtainSpecEmps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsGrObtainSpecEmp.getId().intValue())))
                .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME.toString())))
                .andExpect(jsonPath("$.[*].empDesignation").value(hasItem(DEFAULT_EMP_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].empDepartment").value(hasItem(DEFAULT_EMP_DEPARTMENT.toString())))
                .andExpect(jsonPath("$.[*].empEndDate").value(hasItem(DEFAULT_EMP_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].empStatus").value(hasItem(DEFAULT_EMP_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].empWrkingYear").value(hasItem(DEFAULT_EMP_WRKING_YEAR.intValue())))
                .andExpect(jsonPath("$.[*].amountAsGr").value(hasItem(DEFAULT_AMOUNT_AS_GR.intValue())))
                .andExpect(jsonPath("$.[*].obtainDate").value(hasItem(DEFAULT_OBTAIN_DATE.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPgmsGrObtainSpecEmp() throws Exception {
        // Initialize the database
        pgmsGrObtainSpecEmpRepository.saveAndFlush(pgmsGrObtainSpecEmp);

        // Get the pgmsGrObtainSpecEmp
        restPgmsGrObtainSpecEmpMockMvc.perform(get("/api/pgmsGrObtainSpecEmps/{id}", pgmsGrObtainSpecEmp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsGrObtainSpecEmp.getId().intValue()))
            .andExpect(jsonPath("$.empName").value(DEFAULT_EMP_NAME.toString()))
            .andExpect(jsonPath("$.empDesignation").value(DEFAULT_EMP_DESIGNATION.toString()))
            .andExpect(jsonPath("$.empDepartment").value(DEFAULT_EMP_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.empEndDate").value(DEFAULT_EMP_END_DATE.toString()))
            .andExpect(jsonPath("$.empStatus").value(DEFAULT_EMP_STATUS.booleanValue()))
            .andExpect(jsonPath("$.empWrkingYear").value(DEFAULT_EMP_WRKING_YEAR.intValue()))
            .andExpect(jsonPath("$.amountAsGr").value(DEFAULT_AMOUNT_AS_GR.intValue()))
            .andExpect(jsonPath("$.obtainDate").value(DEFAULT_OBTAIN_DATE.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsGrObtainSpecEmp() throws Exception {
        // Get the pgmsGrObtainSpecEmp
        restPgmsGrObtainSpecEmpMockMvc.perform(get("/api/pgmsGrObtainSpecEmps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsGrObtainSpecEmp() throws Exception {
        // Initialize the database
        pgmsGrObtainSpecEmpRepository.saveAndFlush(pgmsGrObtainSpecEmp);

		int databaseSizeBeforeUpdate = pgmsGrObtainSpecEmpRepository.findAll().size();

        // Update the pgmsGrObtainSpecEmp
        pgmsGrObtainSpecEmp.setEmpName(UPDATED_EMP_NAME);
        pgmsGrObtainSpecEmp.setEmpDesignation(UPDATED_EMP_DESIGNATION);
        pgmsGrObtainSpecEmp.setEmpDepartment(UPDATED_EMP_DEPARTMENT);
        pgmsGrObtainSpecEmp.setEmpEndDate(UPDATED_EMP_END_DATE);
        pgmsGrObtainSpecEmp.setEmpStatus(UPDATED_EMP_STATUS);
        pgmsGrObtainSpecEmp.setEmpWrkingYear(UPDATED_EMP_WRKING_YEAR);
        pgmsGrObtainSpecEmp.setAmountAsGr(UPDATED_AMOUNT_AS_GR);
        pgmsGrObtainSpecEmp.setObtainDate(UPDATED_OBTAIN_DATE);
        pgmsGrObtainSpecEmp.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pgmsGrObtainSpecEmp.setCreateDate(UPDATED_CREATE_DATE);
        pgmsGrObtainSpecEmp.setCreateBy(UPDATED_CREATE_BY);
        pgmsGrObtainSpecEmp.setUpdateDate(UPDATED_UPDATE_DATE);
        pgmsGrObtainSpecEmp.setUpdateBy(UPDATED_UPDATE_BY);

        restPgmsGrObtainSpecEmpMockMvc.perform(put("/api/pgmsGrObtainSpecEmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsGrObtainSpecEmp)))
                .andExpect(status().isOk());

        // Validate the PgmsGrObtainSpecEmp in the database
        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeUpdate);
        PgmsGrObtainSpecEmp testPgmsGrObtainSpecEmp = pgmsGrObtainSpecEmps.get(pgmsGrObtainSpecEmps.size() - 1);
        assertThat(testPgmsGrObtainSpecEmp.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testPgmsGrObtainSpecEmp.getEmpDesignation()).isEqualTo(UPDATED_EMP_DESIGNATION);
        assertThat(testPgmsGrObtainSpecEmp.getEmpDepartment()).isEqualTo(UPDATED_EMP_DEPARTMENT);
        assertThat(testPgmsGrObtainSpecEmp.getEmpEndDate()).isEqualTo(UPDATED_EMP_END_DATE);
        assertThat(testPgmsGrObtainSpecEmp.getEmpStatus()).isEqualTo(UPDATED_EMP_STATUS);
        assertThat(testPgmsGrObtainSpecEmp.getEmpWrkingYear()).isEqualTo(UPDATED_EMP_WRKING_YEAR);
        assertThat(testPgmsGrObtainSpecEmp.getAmountAsGr()).isEqualTo(UPDATED_AMOUNT_AS_GR);
        assertThat(testPgmsGrObtainSpecEmp.getObtainDate()).isEqualTo(UPDATED_OBTAIN_DATE);
        assertThat(testPgmsGrObtainSpecEmp.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPgmsGrObtainSpecEmp.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPgmsGrObtainSpecEmp.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPgmsGrObtainSpecEmp.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPgmsGrObtainSpecEmp.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePgmsGrObtainSpecEmp() throws Exception {
        // Initialize the database
        pgmsGrObtainSpecEmpRepository.saveAndFlush(pgmsGrObtainSpecEmp);

		int databaseSizeBeforeDelete = pgmsGrObtainSpecEmpRepository.findAll().size();

        // Get the pgmsGrObtainSpecEmp
        restPgmsGrObtainSpecEmpMockMvc.perform(delete("/api/pgmsGrObtainSpecEmps/{id}", pgmsGrObtainSpecEmp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsGrObtainSpecEmp> pgmsGrObtainSpecEmps = pgmsGrObtainSpecEmpRepository.findAll();
        assertThat(pgmsGrObtainSpecEmps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
