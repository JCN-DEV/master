package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Vacancy;
import gov.step.app.repository.VacancyRepository;
import gov.step.app.repository.search.VacancySearchRepository;

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

import gov.step.app.domain.enumeration.EmployeeType;

/**
 * Test class for the VacancyResource REST controller.
 *
 * @see VacancyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VacancyResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_PROMOTION_GBRESOLUTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROMOTION_GBRESOLUTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TOTAL_SERVICE_TENURE = 1;
    private static final Integer UPDATED_TOTAL_SERVICE_TENURE = 2;

    private static final Integer DEFAULT_CURRENT_JOB_DURATION = 1;
    private static final Integer UPDATED_CURRENT_JOB_DURATION = 2;


private static final EmployeeType DEFAULT_STATUS = EmployeeType.Approved;
    private static final EmployeeType UPDATED_STATUS = EmployeeType.CurrentlyWorking;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    @Inject
    private VacancyRepository vacancyRepository;

    @Inject
    private VacancySearchRepository vacancySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVacancyMockMvc;

    private Vacancy vacancy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VacancyResource vacancyResource = new VacancyResource();
        ReflectionTestUtils.setField(vacancyResource, "vacancyRepository", vacancyRepository);
        ReflectionTestUtils.setField(vacancyResource, "vacancySearchRepository", vacancySearchRepository);
        this.restVacancyMockMvc = MockMvcBuilders.standaloneSetup(vacancyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vacancy = new Vacancy();
        vacancy.setName(DEFAULT_NAME);
        vacancy.setPromotionGBResolutionDate(DEFAULT_PROMOTION_GBRESOLUTION_DATE);
        vacancy.setTotalServiceTenure(DEFAULT_TOTAL_SERVICE_TENURE);
        vacancy.setCurrentJobDuration(DEFAULT_CURRENT_JOB_DURATION);
        vacancy.setStatus(DEFAULT_STATUS);
        vacancy.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createVacancy() throws Exception {
        int databaseSizeBeforeCreate = vacancyRepository.findAll().size();

        // Create the Vacancy

        restVacancyMockMvc.perform(post("/api/vacancys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vacancy)))
                .andExpect(status().isCreated());

        // Validate the Vacancy in the database
        List<Vacancy> vacancys = vacancyRepository.findAll();
        assertThat(vacancys).hasSize(databaseSizeBeforeCreate + 1);
        Vacancy testVacancy = vacancys.get(vacancys.size() - 1);
        assertThat(testVacancy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVacancy.getPromotionGBResolutionDate()).isEqualTo(DEFAULT_PROMOTION_GBRESOLUTION_DATE);
        assertThat(testVacancy.getTotalServiceTenure()).isEqualTo(DEFAULT_TOTAL_SERVICE_TENURE);
        assertThat(testVacancy.getCurrentJobDuration()).isEqualTo(DEFAULT_CURRENT_JOB_DURATION);
        assertThat(testVacancy.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testVacancy.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vacancyRepository.findAll().size();
        // set the field null
        vacancy.setName(null);

        // Create the Vacancy, which fails.

        restVacancyMockMvc.perform(post("/api/vacancys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vacancy)))
                .andExpect(status().isBadRequest());

        List<Vacancy> vacancys = vacancyRepository.findAll();
        assertThat(vacancys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVacancys() throws Exception {
        // Initialize the database
        vacancyRepository.saveAndFlush(vacancy);

        // Get all the vacancys
        restVacancyMockMvc.perform(get("/api/vacancys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vacancy.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].promotionGBResolutionDate").value(hasItem(DEFAULT_PROMOTION_GBRESOLUTION_DATE.toString())))
                .andExpect(jsonPath("$.[*].totalServiceTenure").value(hasItem(DEFAULT_TOTAL_SERVICE_TENURE)))
                .andExpect(jsonPath("$.[*].currentJobDuration").value(hasItem(DEFAULT_CURRENT_JOB_DURATION)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getVacancy() throws Exception {
        // Initialize the database
        vacancyRepository.saveAndFlush(vacancy);

        // Get the vacancy
        restVacancyMockMvc.perform(get("/api/vacancys/{id}", vacancy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vacancy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.promotionGBResolutionDate").value(DEFAULT_PROMOTION_GBRESOLUTION_DATE.toString()))
            .andExpect(jsonPath("$.totalServiceTenure").value(DEFAULT_TOTAL_SERVICE_TENURE))
            .andExpect(jsonPath("$.currentJobDuration").value(DEFAULT_CURRENT_JOB_DURATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVacancy() throws Exception {
        // Get the vacancy
        restVacancyMockMvc.perform(get("/api/vacancys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVacancy() throws Exception {
        // Initialize the database
        vacancyRepository.saveAndFlush(vacancy);

		int databaseSizeBeforeUpdate = vacancyRepository.findAll().size();

        // Update the vacancy
        vacancy.setName(UPDATED_NAME);
        vacancy.setPromotionGBResolutionDate(UPDATED_PROMOTION_GBRESOLUTION_DATE);
        vacancy.setTotalServiceTenure(UPDATED_TOTAL_SERVICE_TENURE);
        vacancy.setCurrentJobDuration(UPDATED_CURRENT_JOB_DURATION);
        vacancy.setStatus(UPDATED_STATUS);
        vacancy.setRemarks(UPDATED_REMARKS);

        restVacancyMockMvc.perform(put("/api/vacancys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vacancy)))
                .andExpect(status().isOk());

        // Validate the Vacancy in the database
        List<Vacancy> vacancys = vacancyRepository.findAll();
        assertThat(vacancys).hasSize(databaseSizeBeforeUpdate);
        Vacancy testVacancy = vacancys.get(vacancys.size() - 1);
        assertThat(testVacancy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVacancy.getPromotionGBResolutionDate()).isEqualTo(UPDATED_PROMOTION_GBRESOLUTION_DATE);
        assertThat(testVacancy.getTotalServiceTenure()).isEqualTo(UPDATED_TOTAL_SERVICE_TENURE);
        assertThat(testVacancy.getCurrentJobDuration()).isEqualTo(UPDATED_CURRENT_JOB_DURATION);
        assertThat(testVacancy.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testVacancy.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteVacancy() throws Exception {
        // Initialize the database
        vacancyRepository.saveAndFlush(vacancy);

		int databaseSizeBeforeDelete = vacancyRepository.findAll().size();

        // Get the vacancy
        restVacancyMockMvc.perform(delete("/api/vacancys/{id}", vacancy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Vacancy> vacancys = vacancyRepository.findAll();
        assertThat(vacancys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
