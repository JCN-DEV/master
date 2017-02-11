package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.ApplicantEducation;
import gov.step.app.repository.ApplicantEducationRepository;
import gov.step.app.repository.search.ApplicantEducationSearchRepository;

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
 * Test class for the ApplicantEducationResource REST controller.
 *
 * @see ApplicantEducationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ApplicantEducationResourceTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";
    private static final String DEFAULT_APPLICANT_GROUP = "AAAAA";
    private static final String UPDATED_APPLICANT_GROUP = "BBBBB";
    private static final String DEFAULT_GPA = "AAAAA";
    private static final String UPDATED_GPA = "BBBBB";

    private static final LocalDate DEFAULT_EXAM_YEAR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXAM_YEAR = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXAM_RESULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXAM_RESULT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    @Inject
    private ApplicantEducationRepository applicantEducationRepository;

    @Inject
    private ApplicantEducationSearchRepository applicantEducationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restApplicantEducationMockMvc;

    private ApplicantEducation applicantEducation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApplicantEducationResource applicantEducationResource = new ApplicantEducationResource();
        ReflectionTestUtils.setField(applicantEducationResource, "applicantEducationRepository", applicantEducationRepository);
        ReflectionTestUtils.setField(applicantEducationResource, "applicantEducationSearchRepository", applicantEducationSearchRepository);
        this.restApplicantEducationMockMvc = MockMvcBuilders.standaloneSetup(applicantEducationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        applicantEducation = new ApplicantEducation();
        applicantEducation.setName(DEFAULT_NAME);
        applicantEducation.setApplicantGroup(DEFAULT_APPLICANT_GROUP);
        applicantEducation.setGpa(DEFAULT_GPA);
        applicantEducation.setExamYear(DEFAULT_EXAM_YEAR);
        applicantEducation.setExamResultDate(DEFAULT_EXAM_RESULT_DATE);
        applicantEducation.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createApplicantEducation() throws Exception {
        int databaseSizeBeforeCreate = applicantEducationRepository.findAll().size();

        // Create the ApplicantEducation

        restApplicantEducationMockMvc.perform(post("/api/applicantEducations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicantEducation)))
                .andExpect(status().isCreated());

        // Validate the ApplicantEducation in the database
        List<ApplicantEducation> applicantEducations = applicantEducationRepository.findAll();
        assertThat(applicantEducations).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantEducation testApplicantEducation = applicantEducations.get(applicantEducations.size() - 1);
        assertThat(testApplicantEducation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicantEducation.getApplicantGroup()).isEqualTo(DEFAULT_APPLICANT_GROUP);
        assertThat(testApplicantEducation.getGpa()).isEqualTo(DEFAULT_GPA);
        assertThat(testApplicantEducation.getExamYear()).isEqualTo(DEFAULT_EXAM_YEAR);
        assertThat(testApplicantEducation.getExamResultDate()).isEqualTo(DEFAULT_EXAM_RESULT_DATE);
        assertThat(testApplicantEducation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantEducationRepository.findAll().size();
        // set the field null
        applicantEducation.setName(null);

        // Create the ApplicantEducation, which fails.

        restApplicantEducationMockMvc.perform(post("/api/applicantEducations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicantEducation)))
                .andExpect(status().isBadRequest());

        List<ApplicantEducation> applicantEducations = applicantEducationRepository.findAll();
        assertThat(applicantEducations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicantEducations() throws Exception {
        // Initialize the database
        applicantEducationRepository.saveAndFlush(applicantEducation);

        // Get all the applicantEducations
        restApplicantEducationMockMvc.perform(get("/api/applicantEducations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(applicantEducation.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].applicantGroup").value(hasItem(DEFAULT_APPLICANT_GROUP.toString())))
                .andExpect(jsonPath("$.[*].gpa").value(hasItem(DEFAULT_GPA.toString())))
                .andExpect(jsonPath("$.[*].examYear").value(hasItem(DEFAULT_EXAM_YEAR.toString())))
                .andExpect(jsonPath("$.[*].examResultDate").value(hasItem(DEFAULT_EXAM_RESULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getApplicantEducation() throws Exception {
        // Initialize the database
        applicantEducationRepository.saveAndFlush(applicantEducation);

        // Get the applicantEducation
        restApplicantEducationMockMvc.perform(get("/api/applicantEducations/{id}", applicantEducation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(applicantEducation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.applicantGroup").value(DEFAULT_APPLICANT_GROUP.toString()))
            .andExpect(jsonPath("$.gpa").value(DEFAULT_GPA.toString()))
            .andExpect(jsonPath("$.examYear").value(DEFAULT_EXAM_YEAR.toString()))
            .andExpect(jsonPath("$.examResultDate").value(DEFAULT_EXAM_RESULT_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicantEducation() throws Exception {
        // Get the applicantEducation
        restApplicantEducationMockMvc.perform(get("/api/applicantEducations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicantEducation() throws Exception {
        // Initialize the database
        applicantEducationRepository.saveAndFlush(applicantEducation);

		int databaseSizeBeforeUpdate = applicantEducationRepository.findAll().size();

        // Update the applicantEducation
        applicantEducation.setName(UPDATED_NAME);
        applicantEducation.setApplicantGroup(UPDATED_APPLICANT_GROUP);
        applicantEducation.setGpa(UPDATED_GPA);
        applicantEducation.setExamYear(UPDATED_EXAM_YEAR);
        applicantEducation.setExamResultDate(UPDATED_EXAM_RESULT_DATE);
        applicantEducation.setRemarks(UPDATED_REMARKS);

        restApplicantEducationMockMvc.perform(put("/api/applicantEducations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicantEducation)))
                .andExpect(status().isOk());

        // Validate the ApplicantEducation in the database
        List<ApplicantEducation> applicantEducations = applicantEducationRepository.findAll();
        assertThat(applicantEducations).hasSize(databaseSizeBeforeUpdate);
        ApplicantEducation testApplicantEducation = applicantEducations.get(applicantEducations.size() - 1);
        assertThat(testApplicantEducation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicantEducation.getApplicantGroup()).isEqualTo(UPDATED_APPLICANT_GROUP);
        assertThat(testApplicantEducation.getGpa()).isEqualTo(UPDATED_GPA);
        assertThat(testApplicantEducation.getExamYear()).isEqualTo(UPDATED_EXAM_YEAR);
        assertThat(testApplicantEducation.getExamResultDate()).isEqualTo(UPDATED_EXAM_RESULT_DATE);
        assertThat(testApplicantEducation.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteApplicantEducation() throws Exception {
        // Initialize the database
        applicantEducationRepository.saveAndFlush(applicantEducation);

		int databaseSizeBeforeDelete = applicantEducationRepository.findAll().size();

        // Get the applicantEducation
        restApplicantEducationMockMvc.perform(delete("/api/applicantEducations/{id}", applicantEducation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicantEducation> applicantEducations = applicantEducationRepository.findAll();
        assertThat(applicantEducations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
