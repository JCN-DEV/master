/*
package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsCurriculum;
import gov.step.app.repository.CmsCurriculumRepository;
import gov.step.app.repository.search.CmsCurriculumSearchRepository;

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


*/
/**
 * Test class for the CmsCurriculumResource REST controller.
 *
 * @see CmsCurriculumResource
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsCurriculumResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsCurriculumRepository cmsCurriculumRepository;

    @Inject
    private CmsCurriculumSearchRepository cmsCurriculumSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsCurriculumMockMvc;

    private CmsCurriculum cmsCurriculum;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsCurriculumResource cmsCurriculumResource = new CmsCurriculumResource();
        ReflectionTestUtils.setField(cmsCurriculumResource, "cmsCurriculumRepository", cmsCurriculumRepository);
        ReflectionTestUtils.setField(cmsCurriculumResource, "cmsCurriculumSearchRepository", cmsCurriculumSearchRepository);
        this.restCmsCurriculumMockMvc = MockMvcBuilders.standaloneSetup(cmsCurriculumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsCurriculum = new CmsCurriculum();
        cmsCurriculum.setCode(DEFAULT_CODE);
        cmsCurriculum.setName(DEFAULT_NAME);
        cmsCurriculum.setDuration(DEFAULT_DURATION);
        cmsCurriculum.setDescription(DEFAULT_DESCRIPTION);
        cmsCurriculum.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsCurriculum() throws Exception {
        int databaseSizeBeforeCreate = cmsCurriculumRepository.findAll().size();

        // Create the CmsCurriculum

        restCmsCurriculumMockMvc.perform(post("/api/cmsCurriculums")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsCurriculum)))
                .andExpect(status().isCreated());

        // Validate the CmsCurriculum in the database
        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeCreate + 1);
        CmsCurriculum testCmsCurriculum = cmsCurriculums.get(cmsCurriculums.size() - 1);
        assertThat(testCmsCurriculum.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCmsCurriculum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCmsCurriculum.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testCmsCurriculum.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsCurriculum.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsCurriculumRepository.findAll().size();
        // set the field null
        cmsCurriculum.setCode(null);

        // Create the CmsCurriculum, which fails.

        restCmsCurriculumMockMvc.perform(post("/api/cmsCurriculums")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsCurriculum)))
                .andExpect(status().isBadRequest());

        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsCurriculumRepository.findAll().size();
        // set the field null
        cmsCurriculum.setName(null);

        // Create the CmsCurriculum, which fails.

        restCmsCurriculumMockMvc.perform(post("/api/cmsCurriculums")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsCurriculum)))
                .andExpect(status().isBadRequest());

        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsCurriculumRepository.findAll().size();
        // set the field null
        cmsCurriculum.setDuration(null);

        // Create the CmsCurriculum, which fails.

        restCmsCurriculumMockMvc.perform(post("/api/cmsCurriculums")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsCurriculum)))
                .andExpect(status().isBadRequest());

        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsCurriculums() throws Exception {
        // Initialize the database
        cmsCurriculumRepository.saveAndFlush(cmsCurriculum);

        // Get all the cmsCurriculums
        restCmsCurriculumMockMvc.perform(get("/api/cmsCurriculums"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cmsCurriculum.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsCurriculum() throws Exception {
        // Initialize the database
        cmsCurriculumRepository.saveAndFlush(cmsCurriculum);

        // Get the cmsCurriculum
        restCmsCurriculumMockMvc.perform(get("/api/cmsCurriculums/{id}", cmsCurriculum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsCurriculum.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsCurriculum() throws Exception {
        // Get the cmsCurriculum
        restCmsCurriculumMockMvc.perform(get("/api/cmsCurriculums/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsCurriculum() throws Exception {
        // Initialize the database
        cmsCurriculumRepository.saveAndFlush(cmsCurriculum);

		int databaseSizeBeforeUpdate = cmsCurriculumRepository.findAll().size();

        // Update the cmsCurriculum
        cmsCurriculum.setCode(UPDATED_CODE);
        cmsCurriculum.setName(UPDATED_NAME);
        cmsCurriculum.setDuration(UPDATED_DURATION);
        cmsCurriculum.setDescription(UPDATED_DESCRIPTION);
        cmsCurriculum.setStatus(UPDATED_STATUS);

        restCmsCurriculumMockMvc.perform(put("/api/cmsCurriculums")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsCurriculum)))
                .andExpect(status().isOk());

        // Validate the CmsCurriculum in the database
        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeUpdate);
        CmsCurriculum testCmsCurriculum = cmsCurriculums.get(cmsCurriculums.size() - 1);
        assertThat(testCmsCurriculum.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCmsCurriculum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCmsCurriculum.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testCmsCurriculum.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsCurriculum.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsCurriculum() throws Exception {
        // Initialize the database
        cmsCurriculumRepository.saveAndFlush(cmsCurriculum);

		int databaseSizeBeforeDelete = cmsCurriculumRepository.findAll().size();

        // Get the cmsCurriculum
        restCmsCurriculumMockMvc.perform(delete("/api/cmsCurriculums/{id}", cmsCurriculum.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/


package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsCurriculum;
import gov.step.app.repository.CmsCurriculumRepository;
import gov.step.app.repository.search.CmsCurriculumSearchRepository;

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
 * Test class for the CmsCurriculumResource REST controller.
 *
 * @see CmsCurriculumResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsCurriculumResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsCurriculumRepository cmsCurriculumRepository;

    @Inject
    private CmsCurriculumSearchRepository cmsCurriculumSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsCurriculumMockMvc;

    private CmsCurriculum cmsCurriculum;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsCurriculumResource cmsCurriculumResource = new CmsCurriculumResource();
        ReflectionTestUtils.setField(cmsCurriculumResource, "cmsCurriculumRepository", cmsCurriculumRepository);
        ReflectionTestUtils.setField(cmsCurriculumResource, "cmsCurriculumSearchRepository", cmsCurriculumSearchRepository);
        this.restCmsCurriculumMockMvc = MockMvcBuilders.standaloneSetup(cmsCurriculumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsCurriculum = new CmsCurriculum();
        cmsCurriculum.setCode(DEFAULT_CODE);
        cmsCurriculum.setName(DEFAULT_NAME);
        cmsCurriculum.setDuration(DEFAULT_DURATION);
        cmsCurriculum.setDescription(DEFAULT_DESCRIPTION);
        cmsCurriculum.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsCurriculum() throws Exception {
        int databaseSizeBeforeCreate = cmsCurriculumRepository.findAll().size();

        // Create the CmsCurriculum

        restCmsCurriculumMockMvc.perform(post("/api/cmsCurriculums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsCurriculum)))
            .andExpect(status().isCreated());

        // Validate the CmsCurriculum in the database
        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeCreate + 1);
        CmsCurriculum testCmsCurriculum = cmsCurriculums.get(cmsCurriculums.size() - 1);
        assertThat(testCmsCurriculum.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCmsCurriculum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCmsCurriculum.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testCmsCurriculum.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsCurriculum.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsCurriculumRepository.findAll().size();
        // set the field null
        cmsCurriculum.setCode(null);

        // Create the CmsCurriculum, which fails.

        restCmsCurriculumMockMvc.perform(post("/api/cmsCurriculums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsCurriculum)))
            .andExpect(status().isBadRequest());

        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsCurriculumRepository.findAll().size();
        // set the field null
        cmsCurriculum.setName(null);

        // Create the CmsCurriculum, which fails.

        restCmsCurriculumMockMvc.perform(post("/api/cmsCurriculums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsCurriculum)))
            .andExpect(status().isBadRequest());

        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsCurriculumRepository.findAll().size();
        // set the field null
        cmsCurriculum.setDuration(null);

        // Create the CmsCurriculum, which fails.

        restCmsCurriculumMockMvc.perform(post("/api/cmsCurriculums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsCurriculum)))
            .andExpect(status().isBadRequest());

        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsCurriculums() throws Exception {
        // Initialize the database
        cmsCurriculumRepository.saveAndFlush(cmsCurriculum);

        // Get all the cmsCurriculums
        restCmsCurriculumMockMvc.perform(get("/api/cmsCurriculums"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmsCurriculum.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsCurriculum() throws Exception {
        // Initialize the database
        cmsCurriculumRepository.saveAndFlush(cmsCurriculum);

        // Get the cmsCurriculum
        restCmsCurriculumMockMvc.perform(get("/api/cmsCurriculums/{id}", cmsCurriculum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsCurriculum.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsCurriculum() throws Exception {
        // Get the cmsCurriculum
        restCmsCurriculumMockMvc.perform(get("/api/cmsCurriculums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsCurriculum() throws Exception {
        // Initialize the database
        cmsCurriculumRepository.saveAndFlush(cmsCurriculum);

        int databaseSizeBeforeUpdate = cmsCurriculumRepository.findAll().size();

        // Update the cmsCurriculum
        cmsCurriculum.setCode(UPDATED_CODE);
        cmsCurriculum.setName(UPDATED_NAME);
        cmsCurriculum.setDuration(UPDATED_DURATION);
        cmsCurriculum.setDescription(UPDATED_DESCRIPTION);
        cmsCurriculum.setStatus(UPDATED_STATUS);

        restCmsCurriculumMockMvc.perform(put("/api/cmsCurriculums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsCurriculum)))
            .andExpect(status().isOk());

        // Validate the CmsCurriculum in the database
        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeUpdate);
        CmsCurriculum testCmsCurriculum = cmsCurriculums.get(cmsCurriculums.size() - 1);
        assertThat(testCmsCurriculum.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCmsCurriculum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCmsCurriculum.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testCmsCurriculum.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsCurriculum.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsCurriculum() throws Exception {
        // Initialize the database
        cmsCurriculumRepository.saveAndFlush(cmsCurriculum);

        int databaseSizeBeforeDelete = cmsCurriculumRepository.findAll().size();

        // Get the cmsCurriculum
        restCmsCurriculumMockMvc.perform(delete("/api/cmsCurriculums/{id}", cmsCurriculum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsCurriculum> cmsCurriculums = cmsCurriculumRepository.findAll();
        assertThat(cmsCurriculums).hasSize(databaseSizeBeforeDelete - 1);
    }
}
