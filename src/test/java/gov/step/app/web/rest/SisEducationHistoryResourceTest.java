package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SisEducationHistory;
import gov.step.app.repository.SisEducationHistoryRepository;
import gov.step.app.repository.search.SisEducationHistorySearchRepository;

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

import gov.step.app.domain.enumeration.AchievedCertificate;

/**
 * Test class for the SisEducationHistoryResource REST controller.
 *
 * @see SisEducationHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SisEducationHistoryResourceTest {

    private static final String DEFAULT_YEAR_OR_SEMESTER = "AAAAA";
    private static final String UPDATED_YEAR_OR_SEMESTER = "BBBBB";
    private static final String DEFAULT_ROLL_NO = "AAAAA";
    private static final String UPDATED_ROLL_NO = "BBBBB";
    private static final String DEFAULT_MAJOR_OR_DEPT = "AAAAA";
    private static final String UPDATED_MAJOR_OR_DEPT = "BBBBB";
    private static final String DEFAULT_DIVISION_OR_GPA = "AAAAA";
    private static final String UPDATED_DIVISION_OR_GPA = "BBBBB";
    private static final String DEFAULT_PASSING_YEAR = "AAAAA";
    private static final String UPDATED_PASSING_YEAR = "BBBBB";


//private static final AchievedCertificate DEFAULT_ACHIEVED_CERTIFICATE = AchievedCertificate.Achieved;
    private static final AchievedCertificate UPDATED_ACHIEVED_CERTIFICATE = AchievedCertificate.Continuing;

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
    private SisEducationHistoryRepository sisEducationHistoryRepository;

    @Inject
    private SisEducationHistorySearchRepository sisEducationHistorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSisEducationHistoryMockMvc;

    private SisEducationHistory sisEducationHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SisEducationHistoryResource sisEducationHistoryResource = new SisEducationHistoryResource();
        ReflectionTestUtils.setField(sisEducationHistoryResource, "sisEducationHistoryRepository", sisEducationHistoryRepository);
        ReflectionTestUtils.setField(sisEducationHistoryResource, "sisEducationHistorySearchRepository", sisEducationHistorySearchRepository);
        this.restSisEducationHistoryMockMvc = MockMvcBuilders.standaloneSetup(sisEducationHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        sisEducationHistory = new SisEducationHistory();
        sisEducationHistory.setYearOrSemester(DEFAULT_YEAR_OR_SEMESTER);
        sisEducationHistory.setRollNo(DEFAULT_ROLL_NO);
        sisEducationHistory.setMajorOrDept(DEFAULT_MAJOR_OR_DEPT);
        sisEducationHistory.setDivisionOrGpa(DEFAULT_DIVISION_OR_GPA);
        sisEducationHistory.setPassingYear(DEFAULT_PASSING_YEAR);
        //sisEducationHistory.setAchievedCertificate(DEFAULT_ACHIEVED_CERTIFICATE);
        sisEducationHistory.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        sisEducationHistory.setCreateDate(DEFAULT_CREATE_DATE);
        sisEducationHistory.setCreateBy(DEFAULT_CREATE_BY);
        sisEducationHistory.setUpdateDate(DEFAULT_UPDATE_DATE);
        sisEducationHistory.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createSisEducationHistory() throws Exception {
        int databaseSizeBeforeCreate = sisEducationHistoryRepository.findAll().size();

        // Create the SisEducationHistory

        restSisEducationHistoryMockMvc.perform(post("/api/sisEducationHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisEducationHistory)))
                .andExpect(status().isCreated());

        // Validate the SisEducationHistory in the database
        List<SisEducationHistory> sisEducationHistorys = sisEducationHistoryRepository.findAll();
        assertThat(sisEducationHistorys).hasSize(databaseSizeBeforeCreate + 1);
        SisEducationHistory testSisEducationHistory = sisEducationHistorys.get(sisEducationHistorys.size() - 1);
        assertThat(testSisEducationHistory.getYearOrSemester()).isEqualTo(DEFAULT_YEAR_OR_SEMESTER);
        assertThat(testSisEducationHistory.getRollNo()).isEqualTo(DEFAULT_ROLL_NO);
        assertThat(testSisEducationHistory.getMajorOrDept()).isEqualTo(DEFAULT_MAJOR_OR_DEPT);
        assertThat(testSisEducationHistory.getDivisionOrGpa()).isEqualTo(DEFAULT_DIVISION_OR_GPA);
        assertThat(testSisEducationHistory.getPassingYear()).isEqualTo(DEFAULT_PASSING_YEAR);
        //assertThat(testSisEducationHistory.getAchievedCertificate()).isEqualTo(DEFAULT_ACHIEVED_CERTIFICATE);
        assertThat(testSisEducationHistory.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testSisEducationHistory.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testSisEducationHistory.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testSisEducationHistory.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testSisEducationHistory.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkYearOrSemesterIsRequired() throws Exception {
        int databaseSizeBeforeTest = sisEducationHistoryRepository.findAll().size();
        // set the field null
        sisEducationHistory.setYearOrSemester(null);

        // Create the SisEducationHistory, which fails.

        restSisEducationHistoryMockMvc.perform(post("/api/sisEducationHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisEducationHistory)))
                .andExpect(status().isBadRequest());

        List<SisEducationHistory> sisEducationHistorys = sisEducationHistoryRepository.findAll();
        assertThat(sisEducationHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRollNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = sisEducationHistoryRepository.findAll().size();
        // set the field null
        sisEducationHistory.setRollNo(null);

        // Create the SisEducationHistory, which fails.

        restSisEducationHistoryMockMvc.perform(post("/api/sisEducationHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisEducationHistory)))
                .andExpect(status().isBadRequest());

        List<SisEducationHistory> sisEducationHistorys = sisEducationHistoryRepository.findAll();
        assertThat(sisEducationHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMajorOrDeptIsRequired() throws Exception {
        int databaseSizeBeforeTest = sisEducationHistoryRepository.findAll().size();
        // set the field null
        sisEducationHistory.setMajorOrDept(null);

        // Create the SisEducationHistory, which fails.

        restSisEducationHistoryMockMvc.perform(post("/api/sisEducationHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisEducationHistory)))
                .andExpect(status().isBadRequest());

        List<SisEducationHistory> sisEducationHistorys = sisEducationHistoryRepository.findAll();
        assertThat(sisEducationHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDivisionOrGpaIsRequired() throws Exception {
        int databaseSizeBeforeTest = sisEducationHistoryRepository.findAll().size();
        // set the field null
        sisEducationHistory.setDivisionOrGpa(null);

        // Create the SisEducationHistory, which fails.

        restSisEducationHistoryMockMvc.perform(post("/api/sisEducationHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisEducationHistory)))
                .andExpect(status().isBadRequest());

        List<SisEducationHistory> sisEducationHistorys = sisEducationHistoryRepository.findAll();
        assertThat(sisEducationHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassingYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = sisEducationHistoryRepository.findAll().size();
        // set the field null
        sisEducationHistory.setPassingYear(null);

        // Create the SisEducationHistory, which fails.

        restSisEducationHistoryMockMvc.perform(post("/api/sisEducationHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisEducationHistory)))
                .andExpect(status().isBadRequest());

        List<SisEducationHistory> sisEducationHistorys = sisEducationHistoryRepository.findAll();
        assertThat(sisEducationHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAchievedCertificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sisEducationHistoryRepository.findAll().size();
        // set the field null
        sisEducationHistory.setAchievedCertificate(null);

        // Create the SisEducationHistory, which fails.

        restSisEducationHistoryMockMvc.perform(post("/api/sisEducationHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisEducationHistory)))
                .andExpect(status().isBadRequest());

        List<SisEducationHistory> sisEducationHistorys = sisEducationHistoryRepository.findAll();
        assertThat(sisEducationHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSisEducationHistorys() throws Exception {
        // Initialize the database
        sisEducationHistoryRepository.saveAndFlush(sisEducationHistory);

        // Get all the sisEducationHistorys
        restSisEducationHistoryMockMvc.perform(get("/api/sisEducationHistorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sisEducationHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].yearOrSemester").value(hasItem(DEFAULT_YEAR_OR_SEMESTER.toString())))
                .andExpect(jsonPath("$.[*].rollNo").value(hasItem(DEFAULT_ROLL_NO.toString())))
                .andExpect(jsonPath("$.[*].majorOrDept").value(hasItem(DEFAULT_MAJOR_OR_DEPT.toString())))
                .andExpect(jsonPath("$.[*].divisionOrGpa").value(hasItem(DEFAULT_DIVISION_OR_GPA.toString())))
                .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR.toString())))
                //.andExpect(jsonPath("$.[*].achievedCertificate").value(hasItem(DEFAULT_ACHIEVED_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getSisEducationHistory() throws Exception {
        // Initialize the database
        sisEducationHistoryRepository.saveAndFlush(sisEducationHistory);

        // Get the sisEducationHistory
        restSisEducationHistoryMockMvc.perform(get("/api/sisEducationHistorys/{id}", sisEducationHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sisEducationHistory.getId().intValue()))
            .andExpect(jsonPath("$.yearOrSemester").value(DEFAULT_YEAR_OR_SEMESTER.toString()))
            .andExpect(jsonPath("$.rollNo").value(DEFAULT_ROLL_NO.toString()))
            .andExpect(jsonPath("$.majorOrDept").value(DEFAULT_MAJOR_OR_DEPT.toString()))
            .andExpect(jsonPath("$.divisionOrGpa").value(DEFAULT_DIVISION_OR_GPA.toString()))
            .andExpect(jsonPath("$.passingYear").value(DEFAULT_PASSING_YEAR.toString()))
            //.andExpect(jsonPath("$.achievedCertificate").value(DEFAULT_ACHIEVED_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSisEducationHistory() throws Exception {
        // Get the sisEducationHistory
        restSisEducationHistoryMockMvc.perform(get("/api/sisEducationHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSisEducationHistory() throws Exception {
        // Initialize the database
        sisEducationHistoryRepository.saveAndFlush(sisEducationHistory);

		int databaseSizeBeforeUpdate = sisEducationHistoryRepository.findAll().size();

        // Update the sisEducationHistory
        sisEducationHistory.setYearOrSemester(UPDATED_YEAR_OR_SEMESTER);
        sisEducationHistory.setRollNo(UPDATED_ROLL_NO);
        sisEducationHistory.setMajorOrDept(UPDATED_MAJOR_OR_DEPT);
        sisEducationHistory.setDivisionOrGpa(UPDATED_DIVISION_OR_GPA);
        sisEducationHistory.setPassingYear(UPDATED_PASSING_YEAR);
        //sisEducationHistory.setAchievedCertificate(UPDATED_ACHIEVED_CERTIFICATE);
        sisEducationHistory.setActiveStatus(UPDATED_ACTIVE_STATUS);
        sisEducationHistory.setCreateDate(UPDATED_CREATE_DATE);
        sisEducationHistory.setCreateBy(UPDATED_CREATE_BY);
        sisEducationHistory.setUpdateDate(UPDATED_UPDATE_DATE);
        sisEducationHistory.setUpdateBy(UPDATED_UPDATE_BY);

        restSisEducationHistoryMockMvc.perform(put("/api/sisEducationHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisEducationHistory)))
                .andExpect(status().isOk());

        // Validate the SisEducationHistory in the database
        List<SisEducationHistory> sisEducationHistorys = sisEducationHistoryRepository.findAll();
        assertThat(sisEducationHistorys).hasSize(databaseSizeBeforeUpdate);
        SisEducationHistory testSisEducationHistory = sisEducationHistorys.get(sisEducationHistorys.size() - 1);
        assertThat(testSisEducationHistory.getYearOrSemester()).isEqualTo(UPDATED_YEAR_OR_SEMESTER);
        assertThat(testSisEducationHistory.getRollNo()).isEqualTo(UPDATED_ROLL_NO);
        assertThat(testSisEducationHistory.getMajorOrDept()).isEqualTo(UPDATED_MAJOR_OR_DEPT);
        assertThat(testSisEducationHistory.getDivisionOrGpa()).isEqualTo(UPDATED_DIVISION_OR_GPA);
        assertThat(testSisEducationHistory.getPassingYear()).isEqualTo(UPDATED_PASSING_YEAR);
        assertThat(testSisEducationHistory.getAchievedCertificate()).isEqualTo(UPDATED_ACHIEVED_CERTIFICATE);
        assertThat(testSisEducationHistory.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testSisEducationHistory.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testSisEducationHistory.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testSisEducationHistory.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testSisEducationHistory.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteSisEducationHistory() throws Exception {
        // Initialize the database
        sisEducationHistoryRepository.saveAndFlush(sisEducationHistory);

		int databaseSizeBeforeDelete = sisEducationHistoryRepository.findAll().size();

        // Get the sisEducationHistory
        restSisEducationHistoryMockMvc.perform(delete("/api/sisEducationHistorys/{id}", sisEducationHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SisEducationHistory> sisEducationHistorys = sisEducationHistoryRepository.findAll();
        assertThat(sisEducationHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
