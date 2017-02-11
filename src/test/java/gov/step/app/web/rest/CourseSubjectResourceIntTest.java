package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CourseSubject;
import gov.step.app.repository.CourseSubjectRepository;
import gov.step.app.repository.search.CourseSubjectSearchRepository;

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


/**
 * Test class for the CourseSubjectResource REST controller.
 *
 * @see CourseSubjectResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CourseSubjectResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CourseSubjectRepository courseSubjectRepository;

    @Inject
    private CourseSubjectSearchRepository courseSubjectSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCourseSubjectMockMvc;

    private CourseSubject courseSubject;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourseSubjectResource courseSubjectResource = new CourseSubjectResource();
        ReflectionTestUtils.setField(courseSubjectResource, "courseSubjectRepository", courseSubjectRepository);
        ReflectionTestUtils.setField(courseSubjectResource, "courseSubjectSearchRepository", courseSubjectSearchRepository);
        this.restCourseSubjectMockMvc = MockMvcBuilders.standaloneSetup(courseSubjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        courseSubject = new CourseSubject();
        courseSubject.setCode(DEFAULT_CODE);
        courseSubject.setName(DEFAULT_NAME);
        courseSubject.setDescription(DEFAULT_DESCRIPTION);
        courseSubject.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCourseSubject() throws Exception {
        int databaseSizeBeforeCreate = courseSubjectRepository.findAll().size();

        // Create the CourseSubject

        restCourseSubjectMockMvc.perform(post("/api/courseSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseSubject)))
                .andExpect(status().isCreated());

        // Validate the CourseSubject in the database
        List<CourseSubject> courseSubjects = courseSubjectRepository.findAll();
        assertThat(courseSubjects).hasSize(databaseSizeBeforeCreate + 1);
        CourseSubject testCourseSubject = courseSubjects.get(courseSubjects.size() - 1);
        assertThat(testCourseSubject.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCourseSubject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCourseSubject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCourseSubject.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSubjectRepository.findAll().size();
        // set the field null
        courseSubject.setCode(null);

        // Create the CourseSubject, which fails.

        restCourseSubjectMockMvc.perform(post("/api/courseSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseSubject)))
                .andExpect(status().isBadRequest());

        List<CourseSubject> courseSubjects = courseSubjectRepository.findAll();
        assertThat(courseSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSubjectRepository.findAll().size();
        // set the field null
        courseSubject.setName(null);

        // Create the CourseSubject, which fails.

        restCourseSubjectMockMvc.perform(post("/api/courseSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseSubject)))
                .andExpect(status().isBadRequest());

        List<CourseSubject> courseSubjects = courseSubjectRepository.findAll();
        assertThat(courseSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourseSubjects() throws Exception {
        // Initialize the database
        courseSubjectRepository.saveAndFlush(courseSubject);

        // Get all the courseSubjects
        restCourseSubjectMockMvc.perform(get("/api/courseSubjects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(courseSubject.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCourseSubject() throws Exception {
        // Initialize the database
        courseSubjectRepository.saveAndFlush(courseSubject);

        // Get the courseSubject
        restCourseSubjectMockMvc.perform(get("/api/courseSubjects/{id}", courseSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(courseSubject.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseSubject() throws Exception {
        // Get the courseSubject
        restCourseSubjectMockMvc.perform(get("/api/courseSubjects/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseSubject() throws Exception {
        // Initialize the database
        courseSubjectRepository.saveAndFlush(courseSubject);

		int databaseSizeBeforeUpdate = courseSubjectRepository.findAll().size();

        // Update the courseSubject
        courseSubject.setCode(UPDATED_CODE);
        courseSubject.setName(UPDATED_NAME);
        courseSubject.setDescription(UPDATED_DESCRIPTION);
        courseSubject.setStatus(UPDATED_STATUS);

        restCourseSubjectMockMvc.perform(put("/api/courseSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseSubject)))
                .andExpect(status().isOk());

        // Validate the CourseSubject in the database
        List<CourseSubject> courseSubjects = courseSubjectRepository.findAll();
        assertThat(courseSubjects).hasSize(databaseSizeBeforeUpdate);
        CourseSubject testCourseSubject = courseSubjects.get(courseSubjects.size() - 1);
        assertThat(testCourseSubject.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCourseSubject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCourseSubject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCourseSubject.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCourseSubject() throws Exception {
        // Initialize the database
        courseSubjectRepository.saveAndFlush(courseSubject);

		int databaseSizeBeforeDelete = courseSubjectRepository.findAll().size();

        // Get the courseSubject
        restCourseSubjectMockMvc.perform(delete("/api/courseSubjects/{id}", courseSubject.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CourseSubject> courseSubjects = courseSubjectRepository.findAll();
        assertThat(courseSubjects).hasSize(databaseSizeBeforeDelete - 1);
    }
}
