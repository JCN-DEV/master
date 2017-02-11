package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmpCount;
import gov.step.app.repository.InstEmpCountRepository;
import gov.step.app.repository.search.InstEmpCountSearchRepository;

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
 * Test class for the InstEmpCountResource REST controller.
 *
 * @see InstEmpCountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmpCountResourceIntTest {


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
    private InstEmpCountRepository instEmpCountRepository;

    @Inject
    private InstEmpCountSearchRepository instEmpCountSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmpCountMockMvc;

    private InstEmpCount instEmpCount;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmpCountResource instEmpCountResource = new InstEmpCountResource();
        ReflectionTestUtils.setField(instEmpCountResource, "instEmpCountRepository", instEmpCountRepository);
        ReflectionTestUtils.setField(instEmpCountResource, "instEmpCountSearchRepository", instEmpCountSearchRepository);
        this.restInstEmpCountMockMvc = MockMvcBuilders.standaloneSetup(instEmpCountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmpCount = new InstEmpCount();
        instEmpCount.setTotalMaleTeacher(DEFAULT_TOTAL_MALE_TEACHER);
        instEmpCount.setTotalFemaleTeacher(DEFAULT_TOTAL_FEMALE_TEACHER);
        instEmpCount.setTotalMaleEmployee(DEFAULT_TOTAL_MALE_EMPLOYEE);
        instEmpCount.setTotalFemaleEmployee(DEFAULT_TOTAL_FEMALE_EMPLOYEE);
        instEmpCount.setTotalGranted(DEFAULT_TOTAL_GRANTED);
        instEmpCount.setTotalEngaged(DEFAULT_TOTAL_ENGAGED);
        instEmpCount.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstEmpCount() throws Exception {
        int databaseSizeBeforeCreate = instEmpCountRepository.findAll().size();

        // Create the InstEmpCount

        restInstEmpCountMockMvc.perform(post("/api/instEmpCounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpCount)))
                .andExpect(status().isCreated());

        // Validate the InstEmpCount in the database
        List<InstEmpCount> instEmpCounts = instEmpCountRepository.findAll();
        assertThat(instEmpCounts).hasSize(databaseSizeBeforeCreate + 1);
        InstEmpCount testInstEmpCount = instEmpCounts.get(instEmpCounts.size() - 1);
        assertThat(testInstEmpCount.getTotalMaleTeacher()).isEqualTo(DEFAULT_TOTAL_MALE_TEACHER);
        assertThat(testInstEmpCount.getTotalFemaleTeacher()).isEqualTo(DEFAULT_TOTAL_FEMALE_TEACHER);
        assertThat(testInstEmpCount.getTotalMaleEmployee()).isEqualTo(DEFAULT_TOTAL_MALE_EMPLOYEE);
        assertThat(testInstEmpCount.getTotalFemaleEmployee()).isEqualTo(DEFAULT_TOTAL_FEMALE_EMPLOYEE);
        assertThat(testInstEmpCount.getTotalGranted()).isEqualTo(DEFAULT_TOTAL_GRANTED);
        assertThat(testInstEmpCount.getTotalEngaged()).isEqualTo(DEFAULT_TOTAL_ENGAGED);
        assertThat(testInstEmpCount.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstEmpCounts() throws Exception {
        // Initialize the database
        instEmpCountRepository.saveAndFlush(instEmpCount);

        // Get all the instEmpCounts
        restInstEmpCountMockMvc.perform(get("/api/instEmpCounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmpCount.getId().intValue())))
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
    public void getInstEmpCount() throws Exception {
        // Initialize the database
        instEmpCountRepository.saveAndFlush(instEmpCount);

        // Get the instEmpCount
        restInstEmpCountMockMvc.perform(get("/api/instEmpCounts/{id}", instEmpCount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmpCount.getId().intValue()))
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
    public void getNonExistingInstEmpCount() throws Exception {
        // Get the instEmpCount
        restInstEmpCountMockMvc.perform(get("/api/instEmpCounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmpCount() throws Exception {
        // Initialize the database
        instEmpCountRepository.saveAndFlush(instEmpCount);

		int databaseSizeBeforeUpdate = instEmpCountRepository.findAll().size();

        // Update the instEmpCount
        instEmpCount.setTotalMaleTeacher(UPDATED_TOTAL_MALE_TEACHER);
        instEmpCount.setTotalFemaleTeacher(UPDATED_TOTAL_FEMALE_TEACHER);
        instEmpCount.setTotalMaleEmployee(UPDATED_TOTAL_MALE_EMPLOYEE);
        instEmpCount.setTotalFemaleEmployee(UPDATED_TOTAL_FEMALE_EMPLOYEE);
        instEmpCount.setTotalGranted(UPDATED_TOTAL_GRANTED);
        instEmpCount.setTotalEngaged(UPDATED_TOTAL_ENGAGED);
        instEmpCount.setStatus(UPDATED_STATUS);

        restInstEmpCountMockMvc.perform(put("/api/instEmpCounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpCount)))
                .andExpect(status().isOk());

        // Validate the InstEmpCount in the database
        List<InstEmpCount> instEmpCounts = instEmpCountRepository.findAll();
        assertThat(instEmpCounts).hasSize(databaseSizeBeforeUpdate);
        InstEmpCount testInstEmpCount = instEmpCounts.get(instEmpCounts.size() - 1);
        assertThat(testInstEmpCount.getTotalMaleTeacher()).isEqualTo(UPDATED_TOTAL_MALE_TEACHER);
        assertThat(testInstEmpCount.getTotalFemaleTeacher()).isEqualTo(UPDATED_TOTAL_FEMALE_TEACHER);
        assertThat(testInstEmpCount.getTotalMaleEmployee()).isEqualTo(UPDATED_TOTAL_MALE_EMPLOYEE);
        assertThat(testInstEmpCount.getTotalFemaleEmployee()).isEqualTo(UPDATED_TOTAL_FEMALE_EMPLOYEE);
        assertThat(testInstEmpCount.getTotalGranted()).isEqualTo(UPDATED_TOTAL_GRANTED);
        assertThat(testInstEmpCount.getTotalEngaged()).isEqualTo(UPDATED_TOTAL_ENGAGED);
        assertThat(testInstEmpCount.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstEmpCount() throws Exception {
        // Initialize the database
        instEmpCountRepository.saveAndFlush(instEmpCount);

		int databaseSizeBeforeDelete = instEmpCountRepository.findAll().size();

        // Get the instEmpCount
        restInstEmpCountMockMvc.perform(delete("/api/instEmpCounts/{id}", instEmpCount.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmpCount> instEmpCounts = instEmpCountRepository.findAll();
        assertThat(instEmpCounts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
