package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CourseSub;
import gov.step.app.repository.CourseSubRepository;
import gov.step.app.repository.search.CourseSubSearchRepository;

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
 * Test class for the CourseSubResource REST controller.
 *
 * @see CourseSubResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CourseSubResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CourseSubRepository courseSubRepository;

    @Inject
    private CourseSubSearchRepository courseSubSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCourseSubMockMvc;

    private CourseSub courseSub;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourseSubResource courseSubResource = new CourseSubResource();
        ReflectionTestUtils.setField(courseSubResource, "courseSubRepository", courseSubRepository);
        ReflectionTestUtils.setField(courseSubResource, "courseSubSearchRepository", courseSubSearchRepository);
        this.restCourseSubMockMvc = MockMvcBuilders.standaloneSetup(courseSubResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        courseSub = new CourseSub();
        courseSub.setCode(DEFAULT_CODE);
        courseSub.setName(DEFAULT_NAME);
        courseSub.setDescription(DEFAULT_DESCRIPTION);
        courseSub.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCourseSub() throws Exception {
        int databaseSizeBeforeCreate = courseSubRepository.findAll().size();

        // Create the CourseSub

        restCourseSubMockMvc.perform(post("/api/courseSubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseSub)))
                .andExpect(status().isCreated());

        // Validate the CourseSub in the database
        List<CourseSub> courseSubs = courseSubRepository.findAll();
        assertThat(courseSubs).hasSize(databaseSizeBeforeCreate + 1);
        CourseSub testCourseSub = courseSubs.get(courseSubs.size() - 1);
        assertThat(testCourseSub.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCourseSub.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCourseSub.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCourseSub.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSubRepository.findAll().size();
        // set the field null
        courseSub.setCode(null);

        // Create the CourseSub, which fails.

        restCourseSubMockMvc.perform(post("/api/courseSubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseSub)))
                .andExpect(status().isBadRequest());

        List<CourseSub> courseSubs = courseSubRepository.findAll();
        assertThat(courseSubs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSubRepository.findAll().size();
        // set the field null
        courseSub.setName(null);

        // Create the CourseSub, which fails.

        restCourseSubMockMvc.perform(post("/api/courseSubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseSub)))
                .andExpect(status().isBadRequest());

        List<CourseSub> courseSubs = courseSubRepository.findAll();
        assertThat(courseSubs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourseSubs() throws Exception {
        // Initialize the database
        courseSubRepository.saveAndFlush(courseSub);

        // Get all the courseSubs
        restCourseSubMockMvc.perform(get("/api/courseSubs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(courseSub.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCourseSub() throws Exception {
        // Initialize the database
        courseSubRepository.saveAndFlush(courseSub);

        // Get the courseSub
        restCourseSubMockMvc.perform(get("/api/courseSubs/{id}", courseSub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(courseSub.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseSub() throws Exception {
        // Get the courseSub
        restCourseSubMockMvc.perform(get("/api/courseSubs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseSub() throws Exception {
        // Initialize the database
        courseSubRepository.saveAndFlush(courseSub);

		int databaseSizeBeforeUpdate = courseSubRepository.findAll().size();

        // Update the courseSub
        courseSub.setCode(UPDATED_CODE);
        courseSub.setName(UPDATED_NAME);
        courseSub.setDescription(UPDATED_DESCRIPTION);
        courseSub.setStatus(UPDATED_STATUS);

        restCourseSubMockMvc.perform(put("/api/courseSubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseSub)))
                .andExpect(status().isOk());

        // Validate the CourseSub in the database
        List<CourseSub> courseSubs = courseSubRepository.findAll();
        assertThat(courseSubs).hasSize(databaseSizeBeforeUpdate);
        CourseSub testCourseSub = courseSubs.get(courseSubs.size() - 1);
        assertThat(testCourseSub.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCourseSub.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCourseSub.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCourseSub.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCourseSub() throws Exception {
        // Initialize the database
        courseSubRepository.saveAndFlush(courseSub);

		int databaseSizeBeforeDelete = courseSubRepository.findAll().size();

        // Get the courseSub
        restCourseSubMockMvc.perform(delete("/api/courseSubs/{id}", courseSub.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CourseSub> courseSubs = courseSubRepository.findAll();
        assertThat(courseSubs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
