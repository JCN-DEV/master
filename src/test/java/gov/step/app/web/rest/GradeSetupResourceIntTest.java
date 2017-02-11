package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.GradeSetup;
import gov.step.app.repository.GradeSetupRepository;
import gov.step.app.repository.search.GradeSetupSearchRepository;

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

import gov.step.app.domain.enumeration.gradeClass;
import gov.step.app.domain.enumeration.gradeType;

/**
 * Test class for the GradeSetupResource REST controller.
 *
 * @see GradeSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GradeSetupResourceIntTest {


    private static final Integer DEFAULT_NO = 1;
    private static final Integer UPDATED_NO = 2;
    private static final String DEFAULT_DETAILS = "AAAAA";
    private static final String UPDATED_DETAILS = "BBBBB";


private static final gradeClass DEFAULT_GRADE_CLASS = gradeClass.First_Class;
    private static final gradeClass UPDATED_GRADE_CLASS = gradeClass.Second_Class;


private static final gradeType DEFAULT_TYPE = gradeType.Selection;
    private static final gradeType UPDATED_TYPE = gradeType.Promotion;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private GradeSetupRepository gradeSetupRepository;

    @Inject
    private GradeSetupSearchRepository gradeSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGradeSetupMockMvc;

    private GradeSetup gradeSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GradeSetupResource gradeSetupResource = new GradeSetupResource();
        ReflectionTestUtils.setField(gradeSetupResource, "gradeSetupRepository", gradeSetupRepository);
        ReflectionTestUtils.setField(gradeSetupResource, "gradeSetupSearchRepository", gradeSetupSearchRepository);
        this.restGradeSetupMockMvc = MockMvcBuilders.standaloneSetup(gradeSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        gradeSetup = new GradeSetup();
        gradeSetup.setNo(DEFAULT_NO);
        gradeSetup.setDetails(DEFAULT_DETAILS);
        gradeSetup.setGradeClass(DEFAULT_GRADE_CLASS);
        gradeSetup.setType(DEFAULT_TYPE);
        gradeSetup.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createGradeSetup() throws Exception {
        int databaseSizeBeforeCreate = gradeSetupRepository.findAll().size();

        // Create the GradeSetup

        restGradeSetupMockMvc.perform(post("/api/gradeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gradeSetup)))
                .andExpect(status().isCreated());

        // Validate the GradeSetup in the database
        List<GradeSetup> gradeSetups = gradeSetupRepository.findAll();
        assertThat(gradeSetups).hasSize(databaseSizeBeforeCreate + 1);
        GradeSetup testGradeSetup = gradeSetups.get(gradeSetups.size() - 1);
        assertThat(testGradeSetup.getNo()).isEqualTo(DEFAULT_NO);
        assertThat(testGradeSetup.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testGradeSetup.getGradeClass()).isEqualTo(DEFAULT_GRADE_CLASS);
        assertThat(testGradeSetup.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGradeSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeSetupRepository.findAll().size();
        // set the field null
        gradeSetup.setNo(null);

        // Create the GradeSetup, which fails.

        restGradeSetupMockMvc.perform(post("/api/gradeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gradeSetup)))
                .andExpect(status().isBadRequest());

        List<GradeSetup> gradeSetups = gradeSetupRepository.findAll();
        assertThat(gradeSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGradeSetups() throws Exception {
        // Initialize the database
        gradeSetupRepository.saveAndFlush(gradeSetup);

        // Get all the gradeSetups
        restGradeSetupMockMvc.perform(get("/api/gradeSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(gradeSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].no").value(hasItem(DEFAULT_NO)))
                .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
                .andExpect(jsonPath("$.[*].gradeClass").value(hasItem(DEFAULT_GRADE_CLASS.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getGradeSetup() throws Exception {
        // Initialize the database
        gradeSetupRepository.saveAndFlush(gradeSetup);

        // Get the gradeSetup
        restGradeSetupMockMvc.perform(get("/api/gradeSetups/{id}", gradeSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(gradeSetup.getId().intValue()))
            .andExpect(jsonPath("$.no").value(DEFAULT_NO))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.gradeClass").value(DEFAULT_GRADE_CLASS.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGradeSetup() throws Exception {
        // Get the gradeSetup
        restGradeSetupMockMvc.perform(get("/api/gradeSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGradeSetup() throws Exception {
        // Initialize the database
        gradeSetupRepository.saveAndFlush(gradeSetup);

		int databaseSizeBeforeUpdate = gradeSetupRepository.findAll().size();

        // Update the gradeSetup
        gradeSetup.setNo(UPDATED_NO);
        gradeSetup.setDetails(UPDATED_DETAILS);
        gradeSetup.setGradeClass(UPDATED_GRADE_CLASS);
        gradeSetup.setType(UPDATED_TYPE);
        gradeSetup.setStatus(UPDATED_STATUS);

        restGradeSetupMockMvc.perform(put("/api/gradeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gradeSetup)))
                .andExpect(status().isOk());

        // Validate the GradeSetup in the database
        List<GradeSetup> gradeSetups = gradeSetupRepository.findAll();
        assertThat(gradeSetups).hasSize(databaseSizeBeforeUpdate);
        GradeSetup testGradeSetup = gradeSetups.get(gradeSetups.size() - 1);
        assertThat(testGradeSetup.getNo()).isEqualTo(UPDATED_NO);
        assertThat(testGradeSetup.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testGradeSetup.getGradeClass()).isEqualTo(UPDATED_GRADE_CLASS);
        assertThat(testGradeSetup.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGradeSetup.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteGradeSetup() throws Exception {
        // Initialize the database
        gradeSetupRepository.saveAndFlush(gradeSetup);

		int databaseSizeBeforeDelete = gradeSetupRepository.findAll().size();

        // Get the gradeSetup
        restGradeSetupMockMvc.perform(delete("/api/gradeSetups/{id}", gradeSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GradeSetup> gradeSetups = gradeSetupRepository.findAll();
        assertThat(gradeSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
