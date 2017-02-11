package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JpEmploymentHistory;
import gov.step.app.repository.JpEmploymentHistoryRepository;
import gov.step.app.repository.search.JpEmploymentHistorySearchRepository;

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
 * Test class for the JpEmploymentHistoryResource REST controller.
 *
 * @see JpEmploymentHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JpEmploymentHistoryResourceTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBB";
    private static final String DEFAULT_COMPANY_BUSINESS = "AAAAA";
    private static final String UPDATED_COMPANY_BUSINESS = "BBBBB";
    private static final String DEFAULT_COMPANY_LOCATION = "AAAAA";
    private static final String UPDATED_COMPANY_LOCATION = "BBBBB";
    private static final String DEFAULT_POSITION_HELD = "AAAAA";
    private static final String UPDATED_POSITION_HELD = "BBBBB";
    private static final String DEFAULT_DEPARTMENT_NAME = "AAAAA";
    private static final String UPDATED_DEPARTMENT_NAME = "BBBBB";
    private static final String DEFAULT_RESPONSIBILITY = "AAAAA";
    private static final String UPDATED_RESPONSIBILITY = "BBBBB";

    private static final LocalDate DEFAULT_START_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_CURRENT_STATUS = false;
    private static final Boolean UPDATED_CURRENT_STATUS = true;

    @Inject
    private JpEmploymentHistoryRepository jpEmploymentHistoryRepository;

    @Inject
    private JpEmploymentHistorySearchRepository jpEmploymentHistorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJpEmploymentHistoryMockMvc;

    private JpEmploymentHistory jpEmploymentHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpEmploymentHistoryResource jpEmploymentHistoryResource = new JpEmploymentHistoryResource();
        ReflectionTestUtils.setField(jpEmploymentHistoryResource, "jpEmploymentHistoryRepository", jpEmploymentHistoryRepository);
        ReflectionTestUtils.setField(jpEmploymentHistoryResource, "jpEmploymentHistorySearchRepository", jpEmploymentHistorySearchRepository);
        this.restJpEmploymentHistoryMockMvc = MockMvcBuilders.standaloneSetup(jpEmploymentHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jpEmploymentHistory = new JpEmploymentHistory();
        jpEmploymentHistory.setCompanyName(DEFAULT_COMPANY_NAME);
        jpEmploymentHistory.setCompanyBusiness(DEFAULT_COMPANY_BUSINESS);
        jpEmploymentHistory.setCompanyLocation(DEFAULT_COMPANY_LOCATION);
        jpEmploymentHistory.setPositionHeld(DEFAULT_POSITION_HELD);
        jpEmploymentHistory.setDepartmentName(DEFAULT_DEPARTMENT_NAME);
        jpEmploymentHistory.setResponsibility(DEFAULT_RESPONSIBILITY);
        jpEmploymentHistory.setStartFrom(DEFAULT_START_FROM);
        jpEmploymentHistory.setEndTo(DEFAULT_END_TO);
        jpEmploymentHistory.setCurrentStatus(DEFAULT_CURRENT_STATUS);
    }

    @Test
    @Transactional
    public void createJpEmploymentHistory() throws Exception {
        int databaseSizeBeforeCreate = jpEmploymentHistoryRepository.findAll().size();

        // Create the JpEmploymentHistory

        restJpEmploymentHistoryMockMvc.perform(post("/api/jpEmploymentHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmploymentHistory)))
                .andExpect(status().isCreated());

        // Validate the JpEmploymentHistory in the database
        List<JpEmploymentHistory> jpEmploymentHistorys = jpEmploymentHistoryRepository.findAll();
        assertThat(jpEmploymentHistorys).hasSize(databaseSizeBeforeCreate + 1);
        JpEmploymentHistory testJpEmploymentHistory = jpEmploymentHistorys.get(jpEmploymentHistorys.size() - 1);
        assertThat(testJpEmploymentHistory.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testJpEmploymentHistory.getCompanyBusiness()).isEqualTo(DEFAULT_COMPANY_BUSINESS);
        assertThat(testJpEmploymentHistory.getCompanyLocation()).isEqualTo(DEFAULT_COMPANY_LOCATION);
        assertThat(testJpEmploymentHistory.getPositionHeld()).isEqualTo(DEFAULT_POSITION_HELD);
        assertThat(testJpEmploymentHistory.getDepartmentName()).isEqualTo(DEFAULT_DEPARTMENT_NAME);
        assertThat(testJpEmploymentHistory.getResponsibility()).isEqualTo(DEFAULT_RESPONSIBILITY);
        assertThat(testJpEmploymentHistory.getStartFrom()).isEqualTo(DEFAULT_START_FROM);
        assertThat(testJpEmploymentHistory.getEndTo()).isEqualTo(DEFAULT_END_TO);
        assertThat(testJpEmploymentHistory.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
    }

    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmploymentHistoryRepository.findAll().size();
        // set the field null
        jpEmploymentHistory.setCompanyName(null);

        // Create the JpEmploymentHistory, which fails.

        restJpEmploymentHistoryMockMvc.perform(post("/api/jpEmploymentHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmploymentHistory)))
                .andExpect(status().isBadRequest());

        List<JpEmploymentHistory> jpEmploymentHistorys = jpEmploymentHistoryRepository.findAll();
        assertThat(jpEmploymentHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionHeldIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmploymentHistoryRepository.findAll().size();
        // set the field null
        jpEmploymentHistory.setPositionHeld(null);

        // Create the JpEmploymentHistory, which fails.

        restJpEmploymentHistoryMockMvc.perform(post("/api/jpEmploymentHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmploymentHistory)))
                .andExpect(status().isBadRequest());

        List<JpEmploymentHistory> jpEmploymentHistorys = jpEmploymentHistoryRepository.findAll();
        assertThat(jpEmploymentHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartmentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmploymentHistoryRepository.findAll().size();
        // set the field null
        jpEmploymentHistory.setDepartmentName(null);

        // Create the JpEmploymentHistory, which fails.

        restJpEmploymentHistoryMockMvc.perform(post("/api/jpEmploymentHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmploymentHistory)))
                .andExpect(status().isBadRequest());

        List<JpEmploymentHistory> jpEmploymentHistorys = jpEmploymentHistoryRepository.findAll();
        assertThat(jpEmploymentHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponsibilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmploymentHistoryRepository.findAll().size();
        // set the field null
        jpEmploymentHistory.setResponsibility(null);

        // Create the JpEmploymentHistory, which fails.

        restJpEmploymentHistoryMockMvc.perform(post("/api/jpEmploymentHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmploymentHistory)))
                .andExpect(status().isBadRequest());

        List<JpEmploymentHistory> jpEmploymentHistorys = jpEmploymentHistoryRepository.findAll();
        assertThat(jpEmploymentHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmploymentHistoryRepository.findAll().size();
        // set the field null
        jpEmploymentHistory.setStartFrom(null);

        // Create the JpEmploymentHistory, which fails.

        restJpEmploymentHistoryMockMvc.perform(post("/api/jpEmploymentHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmploymentHistory)))
                .andExpect(status().isBadRequest());

        List<JpEmploymentHistory> jpEmploymentHistorys = jpEmploymentHistoryRepository.findAll();
        assertThat(jpEmploymentHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndToIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmploymentHistoryRepository.findAll().size();
        // set the field null
        jpEmploymentHistory.setEndTo(null);

        // Create the JpEmploymentHistory, which fails.

        restJpEmploymentHistoryMockMvc.perform(post("/api/jpEmploymentHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmploymentHistory)))
                .andExpect(status().isBadRequest());

        List<JpEmploymentHistory> jpEmploymentHistorys = jpEmploymentHistoryRepository.findAll();
        assertThat(jpEmploymentHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJpEmploymentHistorys() throws Exception {
        // Initialize the database
        jpEmploymentHistoryRepository.saveAndFlush(jpEmploymentHistory);

        // Get all the jpEmploymentHistorys
        restJpEmploymentHistoryMockMvc.perform(get("/api/jpEmploymentHistorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jpEmploymentHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].companyBusiness").value(hasItem(DEFAULT_COMPANY_BUSINESS.toString())))
                .andExpect(jsonPath("$.[*].companyLocation").value(hasItem(DEFAULT_COMPANY_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].positionHeld").value(hasItem(DEFAULT_POSITION_HELD.toString())))
                .andExpect(jsonPath("$.[*].departmentName").value(hasItem(DEFAULT_DEPARTMENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].responsibility").value(hasItem(DEFAULT_RESPONSIBILITY.toString())))
                .andExpect(jsonPath("$.[*].startFrom").value(hasItem(DEFAULT_START_FROM.toString())))
                .andExpect(jsonPath("$.[*].endTo").value(hasItem(DEFAULT_END_TO.toString())))
                .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getJpEmploymentHistory() throws Exception {
        // Initialize the database
        jpEmploymentHistoryRepository.saveAndFlush(jpEmploymentHistory);

        // Get the jpEmploymentHistory
        restJpEmploymentHistoryMockMvc.perform(get("/api/jpEmploymentHistorys/{id}", jpEmploymentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jpEmploymentHistory.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.companyBusiness").value(DEFAULT_COMPANY_BUSINESS.toString()))
            .andExpect(jsonPath("$.companyLocation").value(DEFAULT_COMPANY_LOCATION.toString()))
            .andExpect(jsonPath("$.positionHeld").value(DEFAULT_POSITION_HELD.toString()))
            .andExpect(jsonPath("$.departmentName").value(DEFAULT_DEPARTMENT_NAME.toString()))
            .andExpect(jsonPath("$.responsibility").value(DEFAULT_RESPONSIBILITY.toString()))
            .andExpect(jsonPath("$.startFrom").value(DEFAULT_START_FROM.toString()))
            .andExpect(jsonPath("$.endTo").value(DEFAULT_END_TO.toString()))
            .andExpect(jsonPath("$.currentStatus").value(DEFAULT_CURRENT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJpEmploymentHistory() throws Exception {
        // Get the jpEmploymentHistory
        restJpEmploymentHistoryMockMvc.perform(get("/api/jpEmploymentHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpEmploymentHistory() throws Exception {
        // Initialize the database
        jpEmploymentHistoryRepository.saveAndFlush(jpEmploymentHistory);

		int databaseSizeBeforeUpdate = jpEmploymentHistoryRepository.findAll().size();

        // Update the jpEmploymentHistory
        jpEmploymentHistory.setCompanyName(UPDATED_COMPANY_NAME);
        jpEmploymentHistory.setCompanyBusiness(UPDATED_COMPANY_BUSINESS);
        jpEmploymentHistory.setCompanyLocation(UPDATED_COMPANY_LOCATION);
        jpEmploymentHistory.setPositionHeld(UPDATED_POSITION_HELD);
        jpEmploymentHistory.setDepartmentName(UPDATED_DEPARTMENT_NAME);
        jpEmploymentHistory.setResponsibility(UPDATED_RESPONSIBILITY);
        jpEmploymentHistory.setStartFrom(UPDATED_START_FROM);
        jpEmploymentHistory.setEndTo(UPDATED_END_TO);
        jpEmploymentHistory.setCurrentStatus(UPDATED_CURRENT_STATUS);

        restJpEmploymentHistoryMockMvc.perform(put("/api/jpEmploymentHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmploymentHistory)))
                .andExpect(status().isOk());

        // Validate the JpEmploymentHistory in the database
        List<JpEmploymentHistory> jpEmploymentHistorys = jpEmploymentHistoryRepository.findAll();
        assertThat(jpEmploymentHistorys).hasSize(databaseSizeBeforeUpdate);
        JpEmploymentHistory testJpEmploymentHistory = jpEmploymentHistorys.get(jpEmploymentHistorys.size() - 1);
        assertThat(testJpEmploymentHistory.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testJpEmploymentHistory.getCompanyBusiness()).isEqualTo(UPDATED_COMPANY_BUSINESS);
        assertThat(testJpEmploymentHistory.getCompanyLocation()).isEqualTo(UPDATED_COMPANY_LOCATION);
        assertThat(testJpEmploymentHistory.getPositionHeld()).isEqualTo(UPDATED_POSITION_HELD);
        assertThat(testJpEmploymentHistory.getDepartmentName()).isEqualTo(UPDATED_DEPARTMENT_NAME);
        assertThat(testJpEmploymentHistory.getResponsibility()).isEqualTo(UPDATED_RESPONSIBILITY);
        assertThat(testJpEmploymentHistory.getStartFrom()).isEqualTo(UPDATED_START_FROM);
        assertThat(testJpEmploymentHistory.getEndTo()).isEqualTo(UPDATED_END_TO);
        assertThat(testJpEmploymentHistory.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    public void deleteJpEmploymentHistory() throws Exception {
        // Initialize the database
        jpEmploymentHistoryRepository.saveAndFlush(jpEmploymentHistory);

		int databaseSizeBeforeDelete = jpEmploymentHistoryRepository.findAll().size();

        // Get the jpEmploymentHistory
        restJpEmploymentHistoryMockMvc.perform(delete("/api/jpEmploymentHistorys/{id}", jpEmploymentHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JpEmploymentHistory> jpEmploymentHistorys = jpEmploymentHistoryRepository.findAll();
        assertThat(jpEmploymentHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
