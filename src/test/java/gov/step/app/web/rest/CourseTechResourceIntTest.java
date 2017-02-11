package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CourseTech;
import gov.step.app.repository.CourseTechRepository;
import gov.step.app.repository.search.CourseTechSearchRepository;

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
 * Test class for the CourseTechResource REST controller.
 *
 * @see CourseTechResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CourseTechResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CourseTechRepository courseTechRepository;

    @Inject
    private CourseTechSearchRepository courseTechSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCourseTechMockMvc;

    private CourseTech courseTech;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourseTechResource courseTechResource = new CourseTechResource();
        ReflectionTestUtils.setField(courseTechResource, "courseTechRepository", courseTechRepository);
        ReflectionTestUtils.setField(courseTechResource, "courseTechSearchRepository", courseTechSearchRepository);
        this.restCourseTechMockMvc = MockMvcBuilders.standaloneSetup(courseTechResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        courseTech = new CourseTech();
        courseTech.setCode(DEFAULT_CODE);
        courseTech.setName(DEFAULT_NAME);
        courseTech.setDescription(DEFAULT_DESCRIPTION);
        courseTech.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCourseTech() throws Exception {
        int databaseSizeBeforeCreate = courseTechRepository.findAll().size();

        // Create the CourseTech

        restCourseTechMockMvc.perform(post("/api/courseTechs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseTech)))
                .andExpect(status().isCreated());

        // Validate the CourseTech in the database
        List<CourseTech> courseTechs = courseTechRepository.findAll();
        assertThat(courseTechs).hasSize(databaseSizeBeforeCreate + 1);
        CourseTech testCourseTech = courseTechs.get(courseTechs.size() - 1);
        assertThat(testCourseTech.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCourseTech.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCourseTech.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCourseTech.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseTechRepository.findAll().size();
        // set the field null
        courseTech.setCode(null);

        // Create the CourseTech, which fails.

        restCourseTechMockMvc.perform(post("/api/courseTechs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseTech)))
                .andExpect(status().isBadRequest());

        List<CourseTech> courseTechs = courseTechRepository.findAll();
        assertThat(courseTechs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseTechRepository.findAll().size();
        // set the field null
        courseTech.setName(null);

        // Create the CourseTech, which fails.

        restCourseTechMockMvc.perform(post("/api/courseTechs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseTech)))
                .andExpect(status().isBadRequest());

        List<CourseTech> courseTechs = courseTechRepository.findAll();
        assertThat(courseTechs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourseTechs() throws Exception {
        // Initialize the database
        courseTechRepository.saveAndFlush(courseTech);

        // Get all the courseTechs
        restCourseTechMockMvc.perform(get("/api/courseTechs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(courseTech.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCourseTech() throws Exception {
        // Initialize the database
        courseTechRepository.saveAndFlush(courseTech);

        // Get the courseTech
        restCourseTechMockMvc.perform(get("/api/courseTechs/{id}", courseTech.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(courseTech.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseTech() throws Exception {
        // Get the courseTech
        restCourseTechMockMvc.perform(get("/api/courseTechs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseTech() throws Exception {
        // Initialize the database
        courseTechRepository.saveAndFlush(courseTech);

		int databaseSizeBeforeUpdate = courseTechRepository.findAll().size();

        // Update the courseTech
        courseTech.setCode(UPDATED_CODE);
        courseTech.setName(UPDATED_NAME);
        courseTech.setDescription(UPDATED_DESCRIPTION);
        courseTech.setStatus(UPDATED_STATUS);

        restCourseTechMockMvc.perform(put("/api/courseTechs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseTech)))
                .andExpect(status().isOk());

        // Validate the CourseTech in the database
        List<CourseTech> courseTechs = courseTechRepository.findAll();
        assertThat(courseTechs).hasSize(databaseSizeBeforeUpdate);
        CourseTech testCourseTech = courseTechs.get(courseTechs.size() - 1);
        assertThat(testCourseTech.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCourseTech.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCourseTech.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCourseTech.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCourseTech() throws Exception {
        // Initialize the database
        courseTechRepository.saveAndFlush(courseTech);

		int databaseSizeBeforeDelete = courseTechRepository.findAll().size();

        // Get the courseTech
        restCourseTechMockMvc.perform(delete("/api/courseTechs/{id}", courseTech.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CourseTech> courseTechs = courseTechRepository.findAll();
        assertThat(courseTechs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
