package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TimeScale;
import gov.step.app.repository.TimeScaleRepository;
import gov.step.app.repository.search.TimeScaleSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.step.app.domain.enumeration.TimeScaleLevel;

/**
 * Test class for the TimeScaleResource REST controller.
 *
 * @see TimeScaleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TimeScaleResourceTest {



private static final TimeScaleLevel DEFAULT_LEVEL = TimeScaleLevel.School;
    private static final TimeScaleLevel UPDATED_LEVEL = TimeScaleLevel.College;

    private static final BigDecimal DEFAULT_CLASS_GRADE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CLASS_GRADE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SALARY_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALARY_AMOUNT = new BigDecimal(2);

    @Inject
    private TimeScaleRepository timeScaleRepository;

    @Inject
    private TimeScaleSearchRepository timeScaleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimeScaleMockMvc;

    private TimeScale timeScale;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeScaleResource timeScaleResource = new TimeScaleResource();
        ReflectionTestUtils.setField(timeScaleResource, "timeScaleRepository", timeScaleRepository);
        ReflectionTestUtils.setField(timeScaleResource, "timeScaleSearchRepository", timeScaleSearchRepository);
        this.restTimeScaleMockMvc = MockMvcBuilders.standaloneSetup(timeScaleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timeScale = new TimeScale();
        timeScale.setLevel(DEFAULT_LEVEL);
        timeScale.setClassGrade(DEFAULT_CLASS_GRADE);
        timeScale.setSalaryAmount(DEFAULT_SALARY_AMOUNT);
    }

    @Test
    @Transactional
    public void createTimeScale() throws Exception {
        int databaseSizeBeforeCreate = timeScaleRepository.findAll().size();

        // Create the TimeScale

        restTimeScaleMockMvc.perform(post("/api/timeScales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeScale)))
                .andExpect(status().isCreated());

        // Validate the TimeScale in the database
        List<TimeScale> timeScales = timeScaleRepository.findAll();
        assertThat(timeScales).hasSize(databaseSizeBeforeCreate + 1);
        TimeScale testTimeScale = timeScales.get(timeScales.size() - 1);
        assertThat(testTimeScale.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testTimeScale.getClassGrade()).isEqualTo(DEFAULT_CLASS_GRADE);
        assertThat(testTimeScale.getSalaryAmount()).isEqualTo(DEFAULT_SALARY_AMOUNT);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeScaleRepository.findAll().size();
        // set the field null
        timeScale.setLevel(null);

        // Create the TimeScale, which fails.

        restTimeScaleMockMvc.perform(post("/api/timeScales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeScale)))
                .andExpect(status().isBadRequest());

        List<TimeScale> timeScales = timeScaleRepository.findAll();
        assertThat(timeScales).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClassGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeScaleRepository.findAll().size();
        // set the field null
        timeScale.setClassGrade(null);

        // Create the TimeScale, which fails.

        restTimeScaleMockMvc.perform(post("/api/timeScales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeScale)))
                .andExpect(status().isBadRequest());

        List<TimeScale> timeScales = timeScaleRepository.findAll();
        assertThat(timeScales).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimeScales() throws Exception {
        // Initialize the database
        timeScaleRepository.saveAndFlush(timeScale);

        // Get all the timeScales
        restTimeScaleMockMvc.perform(get("/api/timeScales"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timeScale.getId().intValue())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
                .andExpect(jsonPath("$.[*].classGrade").value(hasItem(DEFAULT_CLASS_GRADE.intValue())))
                .andExpect(jsonPath("$.[*].salaryAmount").value(hasItem(DEFAULT_SALARY_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getTimeScale() throws Exception {
        // Initialize the database
        timeScaleRepository.saveAndFlush(timeScale);

        // Get the timeScale
        restTimeScaleMockMvc.perform(get("/api/timeScales/{id}", timeScale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(timeScale.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.classGrade").value(DEFAULT_CLASS_GRADE.intValue()))
            .andExpect(jsonPath("$.salaryAmount").value(DEFAULT_SALARY_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeScale() throws Exception {
        // Get the timeScale
        restTimeScaleMockMvc.perform(get("/api/timeScales/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeScale() throws Exception {
        // Initialize the database
        timeScaleRepository.saveAndFlush(timeScale);

		int databaseSizeBeforeUpdate = timeScaleRepository.findAll().size();

        // Update the timeScale
        timeScale.setLevel(UPDATED_LEVEL);
        timeScale.setClassGrade(UPDATED_CLASS_GRADE);
        timeScale.setSalaryAmount(UPDATED_SALARY_AMOUNT);

        restTimeScaleMockMvc.perform(put("/api/timeScales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeScale)))
                .andExpect(status().isOk());

        // Validate the TimeScale in the database
        List<TimeScale> timeScales = timeScaleRepository.findAll();
        assertThat(timeScales).hasSize(databaseSizeBeforeUpdate);
        TimeScale testTimeScale = timeScales.get(timeScales.size() - 1);
        assertThat(testTimeScale.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testTimeScale.getClassGrade()).isEqualTo(UPDATED_CLASS_GRADE);
        assertThat(testTimeScale.getSalaryAmount()).isEqualTo(UPDATED_SALARY_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteTimeScale() throws Exception {
        // Initialize the database
        timeScaleRepository.saveAndFlush(timeScale);

		int databaseSizeBeforeDelete = timeScaleRepository.findAll().size();

        // Get the timeScale
        restTimeScaleMockMvc.perform(delete("/api/timeScales/{id}", timeScale.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeScale> timeScales = timeScaleRepository.findAll();
        assertThat(timeScales).hasSize(databaseSizeBeforeDelete - 1);
    }
}
