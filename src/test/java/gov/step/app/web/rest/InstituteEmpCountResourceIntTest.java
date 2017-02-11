package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstituteEmpCount;
import gov.step.app.repository.InstituteEmpCountRepository;
import gov.step.app.repository.search.InstituteEmpCountSearchRepository;

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


/**
 * Test class for the InstituteEmpCountResource REST controller.
 *
 * @see InstituteEmpCountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstituteEmpCountResourceIntTest {


    private static final BigDecimal DEFAULT_TOTAL_MALE_TEACHER = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_MALE_TEACHER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_FEMALE_TEACHER = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_FEMALE_TEACHER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_MALE_EMPLOYEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_MALE_EMPLOYEE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_FEMALE_EMPLOYEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_FEMALE_EMPLOYEE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_GRANTED = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_GRANTED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_ENGAGED = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_ENGAGED = new BigDecimal(2);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstituteEmpCountRepository instituteEmpCountRepository;

    @Inject
    private InstituteEmpCountSearchRepository instituteEmpCountSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstituteEmpCountMockMvc;

    private InstituteEmpCount instituteEmpCount;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteEmpCountResource instituteEmpCountResource = new InstituteEmpCountResource();
        ReflectionTestUtils.setField(instituteEmpCountResource, "instituteEmpCountRepository", instituteEmpCountRepository);
        ReflectionTestUtils.setField(instituteEmpCountResource, "instituteEmpCountSearchRepository", instituteEmpCountSearchRepository);
        this.restInstituteEmpCountMockMvc = MockMvcBuilders.standaloneSetup(instituteEmpCountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instituteEmpCount = new InstituteEmpCount();
        instituteEmpCount.setTotalMaleTeacher(DEFAULT_TOTAL_MALE_TEACHER);
        instituteEmpCount.setTotalFemaleTeacher(DEFAULT_TOTAL_FEMALE_TEACHER);
        instituteEmpCount.setTotalMaleEmployee(DEFAULT_TOTAL_MALE_EMPLOYEE);
        instituteEmpCount.setTotalFemaleEmployee(DEFAULT_TOTAL_FEMALE_EMPLOYEE);
        instituteEmpCount.setTotalGranted(DEFAULT_TOTAL_GRANTED);
        instituteEmpCount.setTotalEngaged(DEFAULT_TOTAL_ENGAGED);
        instituteEmpCount.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstituteEmpCount() throws Exception {
        int databaseSizeBeforeCreate = instituteEmpCountRepository.findAll().size();

        // Create the InstituteEmpCount

        restInstituteEmpCountMockMvc.perform(post("/api/instituteEmpCounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteEmpCount)))
                .andExpect(status().isCreated());

        // Validate the InstituteEmpCount in the database
        List<InstituteEmpCount> instituteEmpCounts = instituteEmpCountRepository.findAll();
        assertThat(instituteEmpCounts).hasSize(databaseSizeBeforeCreate + 1);
        InstituteEmpCount testInstituteEmpCount = instituteEmpCounts.get(instituteEmpCounts.size() - 1);
        assertThat(testInstituteEmpCount.getTotalMaleTeacher()).isEqualTo(DEFAULT_TOTAL_MALE_TEACHER);
        assertThat(testInstituteEmpCount.getTotalFemaleTeacher()).isEqualTo(DEFAULT_TOTAL_FEMALE_TEACHER);
        assertThat(testInstituteEmpCount.getTotalMaleEmployee()).isEqualTo(DEFAULT_TOTAL_MALE_EMPLOYEE);
        assertThat(testInstituteEmpCount.getTotalFemaleEmployee()).isEqualTo(DEFAULT_TOTAL_FEMALE_EMPLOYEE);
        assertThat(testInstituteEmpCount.getTotalGranted()).isEqualTo(DEFAULT_TOTAL_GRANTED);
        assertThat(testInstituteEmpCount.getTotalEngaged()).isEqualTo(DEFAULT_TOTAL_ENGAGED);
        assertThat(testInstituteEmpCount.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstituteEmpCounts() throws Exception {
        // Initialize the database
        instituteEmpCountRepository.saveAndFlush(instituteEmpCount);

        // Get all the instituteEmpCounts
        restInstituteEmpCountMockMvc.perform(get("/api/instituteEmpCounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instituteEmpCount.getId().intValue())))
                .andExpect(jsonPath("$.[*].totalMaleTeacher").value(hasItem(DEFAULT_TOTAL_MALE_TEACHER.intValue())))
                .andExpect(jsonPath("$.[*].totalFemaleTeacher").value(hasItem(DEFAULT_TOTAL_FEMALE_TEACHER.intValue())))
                .andExpect(jsonPath("$.[*].totalMaleEmployee").value(hasItem(DEFAULT_TOTAL_MALE_EMPLOYEE.intValue())))
                .andExpect(jsonPath("$.[*].totalFemaleEmployee").value(hasItem(DEFAULT_TOTAL_FEMALE_EMPLOYEE.intValue())))
                .andExpect(jsonPath("$.[*].totalGranted").value(hasItem(DEFAULT_TOTAL_GRANTED.intValue())))
                .andExpect(jsonPath("$.[*].totalEngaged").value(hasItem(DEFAULT_TOTAL_ENGAGED.intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstituteEmpCount() throws Exception {
        // Initialize the database
        instituteEmpCountRepository.saveAndFlush(instituteEmpCount);

        // Get the instituteEmpCount
        restInstituteEmpCountMockMvc.perform(get("/api/instituteEmpCounts/{id}", instituteEmpCount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instituteEmpCount.getId().intValue()))
            .andExpect(jsonPath("$.totalMaleTeacher").value(DEFAULT_TOTAL_MALE_TEACHER.intValue()))
            .andExpect(jsonPath("$.totalFemaleTeacher").value(DEFAULT_TOTAL_FEMALE_TEACHER.intValue()))
            .andExpect(jsonPath("$.totalMaleEmployee").value(DEFAULT_TOTAL_MALE_EMPLOYEE.intValue()))
            .andExpect(jsonPath("$.totalFemaleEmployee").value(DEFAULT_TOTAL_FEMALE_EMPLOYEE.intValue()))
            .andExpect(jsonPath("$.totalGranted").value(DEFAULT_TOTAL_GRANTED.intValue()))
            .andExpect(jsonPath("$.totalEngaged").value(DEFAULT_TOTAL_ENGAGED.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstituteEmpCount() throws Exception {
        // Get the instituteEmpCount
        restInstituteEmpCountMockMvc.perform(get("/api/instituteEmpCounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituteEmpCount() throws Exception {
        // Initialize the database
        instituteEmpCountRepository.saveAndFlush(instituteEmpCount);

		int databaseSizeBeforeUpdate = instituteEmpCountRepository.findAll().size();

        // Update the instituteEmpCount
        instituteEmpCount.setTotalMaleTeacher(UPDATED_TOTAL_MALE_TEACHER);
        instituteEmpCount.setTotalFemaleTeacher(UPDATED_TOTAL_FEMALE_TEACHER);
        instituteEmpCount.setTotalMaleEmployee(UPDATED_TOTAL_MALE_EMPLOYEE);
        instituteEmpCount.setTotalFemaleEmployee(UPDATED_TOTAL_FEMALE_EMPLOYEE);
        instituteEmpCount.setTotalGranted(UPDATED_TOTAL_GRANTED);
        instituteEmpCount.setTotalEngaged(UPDATED_TOTAL_ENGAGED);
        instituteEmpCount.setStatus(UPDATED_STATUS);

        restInstituteEmpCountMockMvc.perform(put("/api/instituteEmpCounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteEmpCount)))
                .andExpect(status().isOk());

        // Validate the InstituteEmpCount in the database
        List<InstituteEmpCount> instituteEmpCounts = instituteEmpCountRepository.findAll();
        assertThat(instituteEmpCounts).hasSize(databaseSizeBeforeUpdate);
        InstituteEmpCount testInstituteEmpCount = instituteEmpCounts.get(instituteEmpCounts.size() - 1);
        assertThat(testInstituteEmpCount.getTotalMaleTeacher()).isEqualTo(UPDATED_TOTAL_MALE_TEACHER);
        assertThat(testInstituteEmpCount.getTotalFemaleTeacher()).isEqualTo(UPDATED_TOTAL_FEMALE_TEACHER);
        assertThat(testInstituteEmpCount.getTotalMaleEmployee()).isEqualTo(UPDATED_TOTAL_MALE_EMPLOYEE);
        assertThat(testInstituteEmpCount.getTotalFemaleEmployee()).isEqualTo(UPDATED_TOTAL_FEMALE_EMPLOYEE);
        assertThat(testInstituteEmpCount.getTotalGranted()).isEqualTo(UPDATED_TOTAL_GRANTED);
        assertThat(testInstituteEmpCount.getTotalEngaged()).isEqualTo(UPDATED_TOTAL_ENGAGED);
        assertThat(testInstituteEmpCount.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstituteEmpCount() throws Exception {
        // Initialize the database
        instituteEmpCountRepository.saveAndFlush(instituteEmpCount);

		int databaseSizeBeforeDelete = instituteEmpCountRepository.findAll().size();

        // Get the instituteEmpCount
        restInstituteEmpCountMockMvc.perform(delete("/api/instituteEmpCounts/{id}", instituteEmpCount.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstituteEmpCount> instituteEmpCounts = instituteEmpCountRepository.findAll();
        assertThat(instituteEmpCounts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
