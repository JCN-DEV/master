package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JpAcademicQualification;
import gov.step.app.repository.JpAcademicQualificationRepository;
import gov.step.app.repository.search.JpAcademicQualificationSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.step.app.domain.enumeration.ResultType;

/**
 * Test class for the JpAcademicQualificationResource REST controller.
 *
 * @see JpAcademicQualificationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JpAcademicQualificationResourceTest {

    private static final String DEFAULT_EDUCATION_LEVEL = "AAAAA";
    private static final String UPDATED_EDUCATION_LEVEL = "BBBBB";
    private static final String DEFAULT_DEGREE_TITLE = "AAAAA";
    private static final String UPDATED_DEGREE_TITLE = "BBBBB";
    private static final String DEFAULT_MAJOR = "AAAAA";
    private static final String UPDATED_MAJOR = "BBBBB";
    private static final String DEFAULT_INSTITUTE = "AAAAA";
    private static final String UPDATED_INSTITUTE = "BBBBB";


private static final ResultType DEFAULT_RESULTTYPE = ResultType.gpa;
    private static final ResultType UPDATED_RESULTTYPE = ResultType.division;
    private static final String DEFAULT_RESULT = "AAAAA";
    private static final String UPDATED_RESULT = "BBBBB";

    private static final Integer DEFAULT_PASSINGYEAR = 1;
    private static final Integer UPDATED_PASSINGYEAR = 2;
    private static final String DEFAULT_DURATION = "AAAAA";
    private static final String UPDATED_DURATION = "BBBBB";
    private static final String DEFAULT_ACHIVEMENT = "AAAAA";
    private static final String UPDATED_ACHIVEMENT = "BBBBB";

    @Inject
    private JpAcademicQualificationRepository jpAcademicQualificationRepository;

    @Inject
    private JpAcademicQualificationSearchRepository jpAcademicQualificationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJpAcademicQualificationMockMvc;

    private JpAcademicQualification jpAcademicQualification;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpAcademicQualificationResource jpAcademicQualificationResource = new JpAcademicQualificationResource();
        ReflectionTestUtils.setField(jpAcademicQualificationResource, "jpAcademicQualificationRepository", jpAcademicQualificationRepository);
        ReflectionTestUtils.setField(jpAcademicQualificationResource, "jpAcademicQualificationSearchRepository", jpAcademicQualificationSearchRepository);
        this.restJpAcademicQualificationMockMvc = MockMvcBuilders.standaloneSetup(jpAcademicQualificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jpAcademicQualification = new JpAcademicQualification();
        jpAcademicQualification.setEducationLevel(DEFAULT_EDUCATION_LEVEL);
        jpAcademicQualification.setDegreeTitle(DEFAULT_DEGREE_TITLE);
        jpAcademicQualification.setMajor(DEFAULT_MAJOR);
        jpAcademicQualification.setInstitute(DEFAULT_INSTITUTE);
        jpAcademicQualification.setResulttype(DEFAULT_RESULTTYPE);
        jpAcademicQualification.setResult(DEFAULT_RESULT);
        jpAcademicQualification.setPassingyear(DEFAULT_PASSINGYEAR);
        jpAcademicQualification.setDuration(DEFAULT_DURATION);
        jpAcademicQualification.setAchivement(DEFAULT_ACHIVEMENT);
    }

    @Test
    @Transactional
    public void createJpAcademicQualification() throws Exception {
        int databaseSizeBeforeCreate = jpAcademicQualificationRepository.findAll().size();

        // Create the JpAcademicQualification

        restJpAcademicQualificationMockMvc.perform(post("/api/jpAcademicQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpAcademicQualification)))
                .andExpect(status().isCreated());

        // Validate the JpAcademicQualification in the database
        List<JpAcademicQualification> jpAcademicQualifications = jpAcademicQualificationRepository.findAll();
        assertThat(jpAcademicQualifications).hasSize(databaseSizeBeforeCreate + 1);
        JpAcademicQualification testJpAcademicQualification = jpAcademicQualifications.get(jpAcademicQualifications.size() - 1);
        assertThat(testJpAcademicQualification.getEducationLevel()).isEqualTo(DEFAULT_EDUCATION_LEVEL);
        assertThat(testJpAcademicQualification.getDegreeTitle()).isEqualTo(DEFAULT_DEGREE_TITLE);
        assertThat(testJpAcademicQualification.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testJpAcademicQualification.getInstitute()).isEqualTo(DEFAULT_INSTITUTE);
        assertThat(testJpAcademicQualification.getResulttype()).isEqualTo(DEFAULT_RESULTTYPE);
        assertThat(testJpAcademicQualification.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testJpAcademicQualification.getPassingyear()).isEqualTo(DEFAULT_PASSINGYEAR);
        assertThat(testJpAcademicQualification.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testJpAcademicQualification.getAchivement()).isEqualTo(DEFAULT_ACHIVEMENT);
    }

    @Test
    @Transactional
    public void checkEducationLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpAcademicQualificationRepository.findAll().size();
        // set the field null
        jpAcademicQualification.setEducationLevel(null);

        // Create the JpAcademicQualification, which fails.

        restJpAcademicQualificationMockMvc.perform(post("/api/jpAcademicQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpAcademicQualification)))
                .andExpect(status().isBadRequest());

        List<JpAcademicQualification> jpAcademicQualifications = jpAcademicQualificationRepository.findAll();
        assertThat(jpAcademicQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDegreeTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpAcademicQualificationRepository.findAll().size();
        // set the field null
        jpAcademicQualification.setDegreeTitle(null);

        // Create the JpAcademicQualification, which fails.

        restJpAcademicQualificationMockMvc.perform(post("/api/jpAcademicQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpAcademicQualification)))
                .andExpect(status().isBadRequest());

        List<JpAcademicQualification> jpAcademicQualifications = jpAcademicQualificationRepository.findAll();
        assertThat(jpAcademicQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMajorIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpAcademicQualificationRepository.findAll().size();
        // set the field null
        jpAcademicQualification.setMajor(null);

        // Create the JpAcademicQualification, which fails.

        restJpAcademicQualificationMockMvc.perform(post("/api/jpAcademicQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpAcademicQualification)))
                .andExpect(status().isBadRequest());

        List<JpAcademicQualification> jpAcademicQualifications = jpAcademicQualificationRepository.findAll();
        assertThat(jpAcademicQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstituteIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpAcademicQualificationRepository.findAll().size();
        // set the field null
        jpAcademicQualification.setInstitute(null);

        // Create the JpAcademicQualification, which fails.

        restJpAcademicQualificationMockMvc.perform(post("/api/jpAcademicQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpAcademicQualification)))
                .andExpect(status().isBadRequest());

        List<JpAcademicQualification> jpAcademicQualifications = jpAcademicQualificationRepository.findAll();
        assertThat(jpAcademicQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpAcademicQualificationRepository.findAll().size();
        // set the field null
        jpAcademicQualification.setResult(null);

        // Create the JpAcademicQualification, which fails.

        restJpAcademicQualificationMockMvc.perform(post("/api/jpAcademicQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpAcademicQualification)))
                .andExpect(status().isBadRequest());

        List<JpAcademicQualification> jpAcademicQualifications = jpAcademicQualificationRepository.findAll();
        assertThat(jpAcademicQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassingyearIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpAcademicQualificationRepository.findAll().size();
        // set the field null
        jpAcademicQualification.setPassingyear(null);

        // Create the JpAcademicQualification, which fails.

        restJpAcademicQualificationMockMvc.perform(post("/api/jpAcademicQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpAcademicQualification)))
                .andExpect(status().isBadRequest());

        List<JpAcademicQualification> jpAcademicQualifications = jpAcademicQualificationRepository.findAll();
        assertThat(jpAcademicQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpAcademicQualificationRepository.findAll().size();
        // set the field null
        jpAcademicQualification.setDuration(null);

        // Create the JpAcademicQualification, which fails.

        restJpAcademicQualificationMockMvc.perform(post("/api/jpAcademicQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpAcademicQualification)))
                .andExpect(status().isBadRequest());

        List<JpAcademicQualification> jpAcademicQualifications = jpAcademicQualificationRepository.findAll();
        assertThat(jpAcademicQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJpAcademicQualifications() throws Exception {
        // Initialize the database
        jpAcademicQualificationRepository.saveAndFlush(jpAcademicQualification);

        // Get all the jpAcademicQualifications
        restJpAcademicQualificationMockMvc.perform(get("/api/jpAcademicQualifications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jpAcademicQualification.getId().intValue())))
                .andExpect(jsonPath("$.[*].educationLevel").value(hasItem(DEFAULT_EDUCATION_LEVEL.toString())))
                .andExpect(jsonPath("$.[*].degreeTitle").value(hasItem(DEFAULT_DEGREE_TITLE.toString())))
                .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
                .andExpect(jsonPath("$.[*].institute").value(hasItem(DEFAULT_INSTITUTE.toString())))
                .andExpect(jsonPath("$.[*].resulttype").value(hasItem(DEFAULT_RESULTTYPE.toString())))
                .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
                .andExpect(jsonPath("$.[*].passingyear").value(hasItem(DEFAULT_PASSINGYEAR)))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
                .andExpect(jsonPath("$.[*].achivement").value(hasItem(DEFAULT_ACHIVEMENT.toString())));
    }

    @Test
    @Transactional
    public void getJpAcademicQualification() throws Exception {
        // Initialize the database
        jpAcademicQualificationRepository.saveAndFlush(jpAcademicQualification);

        // Get the jpAcademicQualification
        restJpAcademicQualificationMockMvc.perform(get("/api/jpAcademicQualifications/{id}", jpAcademicQualification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jpAcademicQualification.getId().intValue()))
            .andExpect(jsonPath("$.educationLevel").value(DEFAULT_EDUCATION_LEVEL.toString()))
            .andExpect(jsonPath("$.degreeTitle").value(DEFAULT_DEGREE_TITLE.toString()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.toString()))
            .andExpect(jsonPath("$.institute").value(DEFAULT_INSTITUTE.toString()))
            .andExpect(jsonPath("$.resulttype").value(DEFAULT_RESULTTYPE.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.passingyear").value(DEFAULT_PASSINGYEAR))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.achivement").value(DEFAULT_ACHIVEMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJpAcademicQualification() throws Exception {
        // Get the jpAcademicQualification
        restJpAcademicQualificationMockMvc.perform(get("/api/jpAcademicQualifications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpAcademicQualification() throws Exception {
        // Initialize the database
        jpAcademicQualificationRepository.saveAndFlush(jpAcademicQualification);

		int databaseSizeBeforeUpdate = jpAcademicQualificationRepository.findAll().size();

        // Update the jpAcademicQualification
        jpAcademicQualification.setEducationLevel(UPDATED_EDUCATION_LEVEL);
        jpAcademicQualification.setDegreeTitle(UPDATED_DEGREE_TITLE);
        jpAcademicQualification.setMajor(UPDATED_MAJOR);
        jpAcademicQualification.setInstitute(UPDATED_INSTITUTE);
        jpAcademicQualification.setResulttype(UPDATED_RESULTTYPE);
        jpAcademicQualification.setResult(UPDATED_RESULT);
        jpAcademicQualification.setPassingyear(UPDATED_PASSINGYEAR);
        jpAcademicQualification.setDuration(UPDATED_DURATION);
        jpAcademicQualification.setAchivement(UPDATED_ACHIVEMENT);

        restJpAcademicQualificationMockMvc.perform(put("/api/jpAcademicQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpAcademicQualification)))
                .andExpect(status().isOk());

        // Validate the JpAcademicQualification in the database
        List<JpAcademicQualification> jpAcademicQualifications = jpAcademicQualificationRepository.findAll();
        assertThat(jpAcademicQualifications).hasSize(databaseSizeBeforeUpdate);
        JpAcademicQualification testJpAcademicQualification = jpAcademicQualifications.get(jpAcademicQualifications.size() - 1);
        assertThat(testJpAcademicQualification.getEducationLevel()).isEqualTo(UPDATED_EDUCATION_LEVEL);
        assertThat(testJpAcademicQualification.getDegreeTitle()).isEqualTo(UPDATED_DEGREE_TITLE);
        assertThat(testJpAcademicQualification.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testJpAcademicQualification.getInstitute()).isEqualTo(UPDATED_INSTITUTE);
        assertThat(testJpAcademicQualification.getResulttype()).isEqualTo(UPDATED_RESULTTYPE);
        assertThat(testJpAcademicQualification.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testJpAcademicQualification.getPassingyear()).isEqualTo(UPDATED_PASSINGYEAR);
        assertThat(testJpAcademicQualification.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testJpAcademicQualification.getAchivement()).isEqualTo(UPDATED_ACHIVEMENT);
    }

    @Test
    @Transactional
    public void deleteJpAcademicQualification() throws Exception {
        // Initialize the database
        jpAcademicQualificationRepository.saveAndFlush(jpAcademicQualification);

		int databaseSizeBeforeDelete = jpAcademicQualificationRepository.findAll().size();

        // Get the jpAcademicQualification
        restJpAcademicQualificationMockMvc.perform(delete("/api/jpAcademicQualifications/{id}", jpAcademicQualification.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JpAcademicQualification> jpAcademicQualifications = jpAcademicQualificationRepository.findAll();
        assertThat(jpAcademicQualifications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
