package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EducationalQualification;
import gov.step.app.repository.EducationalQualificationRepository;
import gov.step.app.repository.search.EducationalQualificationSearchRepository;

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
 * Test class for the EducationalQualificationResource REST controller.
 *
 * @see EducationalQualificationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EducationalQualificationResourceTest {

    private static final String DEFAULT_LEVEL = "AAA";
    private static final String UPDATED_LEVEL = "BBB";
    private static final String DEFAULT_DEGREE = "AA";
    private static final String UPDATED_DEGREE = "BB";
    private static final String DEFAULT_MAJOR = "AA";
    private static final String UPDATED_MAJOR = "BB";
    private static final String DEFAULT_RESULT = "AAA";
    private static final String UPDATED_RESULT = "BBB";

    private static final LocalDate DEFAULT_PASSING_YEAR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PASSING_YEAR = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    @Inject
    private EducationalQualificationRepository educationalQualificationRepository;

    @Inject
    private EducationalQualificationSearchRepository educationalQualificationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEducationalQualificationMockMvc;

    private EducationalQualification educationalQualification;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EducationalQualificationResource educationalQualificationResource = new EducationalQualificationResource();
        ReflectionTestUtils.setField(educationalQualificationResource, "educationalQualificationRepository", educationalQualificationRepository);
        ReflectionTestUtils.setField(educationalQualificationResource, "educationalQualificationSearchRepository", educationalQualificationSearchRepository);
        this.restEducationalQualificationMockMvc = MockMvcBuilders.standaloneSetup(educationalQualificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        educationalQualification = new EducationalQualification();
        educationalQualification.setLevel(DEFAULT_LEVEL);
        educationalQualification.setDegree(DEFAULT_DEGREE);
        educationalQualification.setMajor(DEFAULT_MAJOR);
        educationalQualification.setResult(DEFAULT_RESULT);
        educationalQualification.setPassingYear(DEFAULT_PASSING_YEAR);
        educationalQualification.setDuration(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createEducationalQualification() throws Exception {
        int databaseSizeBeforeCreate = educationalQualificationRepository.findAll().size();

        // Create the EducationalQualification

        restEducationalQualificationMockMvc.perform(post("/api/educationalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationalQualification)))
                .andExpect(status().isCreated());

        // Validate the EducationalQualification in the database
        List<EducationalQualification> educationalQualifications = educationalQualificationRepository.findAll();
        assertThat(educationalQualifications).hasSize(databaseSizeBeforeCreate + 1);
        EducationalQualification testEducationalQualification = educationalQualifications.get(educationalQualifications.size() - 1);
        assertThat(testEducationalQualification.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testEducationalQualification.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testEducationalQualification.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testEducationalQualification.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testEducationalQualification.getPassingYear()).isEqualTo(DEFAULT_PASSING_YEAR);
        assertThat(testEducationalQualification.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalQualificationRepository.findAll().size();
        // set the field null
        educationalQualification.setLevel(null);

        // Create the EducationalQualification, which fails.

        restEducationalQualificationMockMvc.perform(post("/api/educationalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationalQualification)))
                .andExpect(status().isBadRequest());

        List<EducationalQualification> educationalQualifications = educationalQualificationRepository.findAll();
        assertThat(educationalQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDegreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalQualificationRepository.findAll().size();
        // set the field null
        educationalQualification.setDegree(null);

        // Create the EducationalQualification, which fails.

        restEducationalQualificationMockMvc.perform(post("/api/educationalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationalQualification)))
                .andExpect(status().isBadRequest());

        List<EducationalQualification> educationalQualifications = educationalQualificationRepository.findAll();
        assertThat(educationalQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMajorIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalQualificationRepository.findAll().size();
        // set the field null
        educationalQualification.setMajor(null);

        // Create the EducationalQualification, which fails.

        restEducationalQualificationMockMvc.perform(post("/api/educationalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationalQualification)))
                .andExpect(status().isBadRequest());

        List<EducationalQualification> educationalQualifications = educationalQualificationRepository.findAll();
        assertThat(educationalQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalQualificationRepository.findAll().size();
        // set the field null
        educationalQualification.setResult(null);

        // Create the EducationalQualification, which fails.

        restEducationalQualificationMockMvc.perform(post("/api/educationalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationalQualification)))
                .andExpect(status().isBadRequest());

        List<EducationalQualification> educationalQualifications = educationalQualificationRepository.findAll();
        assertThat(educationalQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassingYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalQualificationRepository.findAll().size();
        // set the field null
        educationalQualification.setPassingYear(null);

        // Create the EducationalQualification, which fails.

        restEducationalQualificationMockMvc.perform(post("/api/educationalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationalQualification)))
                .andExpect(status().isBadRequest());

        List<EducationalQualification> educationalQualifications = educationalQualificationRepository.findAll();
        assertThat(educationalQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalQualificationRepository.findAll().size();
        // set the field null
        educationalQualification.setDuration(null);

        // Create the EducationalQualification, which fails.

        restEducationalQualificationMockMvc.perform(post("/api/educationalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationalQualification)))
                .andExpect(status().isBadRequest());

        List<EducationalQualification> educationalQualifications = educationalQualificationRepository.findAll();
        assertThat(educationalQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEducationalQualifications() throws Exception {
        // Initialize the database
        educationalQualificationRepository.saveAndFlush(educationalQualification);

        // Get all the educationalQualifications
        restEducationalQualificationMockMvc.perform(get("/api/educationalQualifications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(educationalQualification.getId().intValue())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
                .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
                .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
                .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
                .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR.toString())))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)));
    }

    @Test
    @Transactional
    public void getEducationalQualification() throws Exception {
        // Initialize the database
        educationalQualificationRepository.saveAndFlush(educationalQualification);

        // Get the educationalQualification
        restEducationalQualificationMockMvc.perform(get("/api/educationalQualifications/{id}", educationalQualification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(educationalQualification.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.passingYear").value(DEFAULT_PASSING_YEAR.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION));
    }

    @Test
    @Transactional
    public void getNonExistingEducationalQualification() throws Exception {
        // Get the educationalQualification
        restEducationalQualificationMockMvc.perform(get("/api/educationalQualifications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationalQualification() throws Exception {
        // Initialize the database
        educationalQualificationRepository.saveAndFlush(educationalQualification);

		int databaseSizeBeforeUpdate = educationalQualificationRepository.findAll().size();

        // Update the educationalQualification
        educationalQualification.setLevel(UPDATED_LEVEL);
        educationalQualification.setDegree(UPDATED_DEGREE);
        educationalQualification.setMajor(UPDATED_MAJOR);
        educationalQualification.setResult(UPDATED_RESULT);
        educationalQualification.setPassingYear(UPDATED_PASSING_YEAR);
        educationalQualification.setDuration(UPDATED_DURATION);

        restEducationalQualificationMockMvc.perform(put("/api/educationalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationalQualification)))
                .andExpect(status().isOk());

        // Validate the EducationalQualification in the database
        List<EducationalQualification> educationalQualifications = educationalQualificationRepository.findAll();
        assertThat(educationalQualifications).hasSize(databaseSizeBeforeUpdate);
        EducationalQualification testEducationalQualification = educationalQualifications.get(educationalQualifications.size() - 1);
        assertThat(testEducationalQualification.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testEducationalQualification.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEducationalQualification.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testEducationalQualification.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testEducationalQualification.getPassingYear()).isEqualTo(UPDATED_PASSING_YEAR);
        assertThat(testEducationalQualification.getDuration()).isEqualTo(UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void deleteEducationalQualification() throws Exception {
        // Initialize the database
        educationalQualificationRepository.saveAndFlush(educationalQualification);

		int databaseSizeBeforeDelete = educationalQualificationRepository.findAll().size();

        // Get the educationalQualification
        restEducationalQualificationMockMvc.perform(delete("/api/educationalQualifications/{id}", educationalQualification.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EducationalQualification> educationalQualifications = educationalQualificationRepository.findAll();
        assertThat(educationalQualifications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
