/*
package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsSemester;
import gov.step.app.repository.CmsSemesterRepository;
import gov.step.app.repository.search.CmsSemesterSearchRepository;

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
 * Test class for the CmsSemesterResource REST controller.
 *
 * @see CmsSemesterResource
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsSemesterResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsSemesterRepository cmsSemesterRepository;

    @Inject
    private CmsSemesterSearchRepository cmsSemesterSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsSemesterMockMvc;

    private CmsSemester cmsSemester;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsSemesterResource cmsSemesterResource = new CmsSemesterResource();
        ReflectionTestUtils.setField(cmsSemesterResource, "cmsSemesterRepository", cmsSemesterRepository);
        ReflectionTestUtils.setField(cmsSemesterResource, "cmsSemesterSearchRepository", cmsSemesterSearchRepository);
        this.restCmsSemesterMockMvc = MockMvcBuilders.standaloneSetup(cmsSemesterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsSemester = new CmsSemester();
        cmsSemester.setCode(DEFAULT_CODE);
        cmsSemester.setName(DEFAULT_NAME);
        cmsSemester.setYear(DEFAULT_YEAR);
        cmsSemester.setDuration(DEFAULT_DURATION);
        cmsSemester.setDescription(DEFAULT_DESCRIPTION);
        cmsSemester.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsSemester() throws Exception {
        int databaseSizeBeforeCreate = cmsSemesterRepository.findAll().size();

        // Create the CmsSemester

        restCmsSemesterMockMvc.perform(post("/api/cmsSemesters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
                .andExpect(status().isCreated());

        // Validate the CmsSemester in the database
        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeCreate + 1);
        CmsSemester testCmsSemester = cmsSemesters.get(cmsSemesters.size() - 1);
        assertThat(testCmsSemester.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCmsSemester.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCmsSemester.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCmsSemester.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testCmsSemester.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsSemester.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSemesterRepository.findAll().size();
        // set the field null
        cmsSemester.setCode(null);

        // Create the CmsSemester, which fails.

        restCmsSemesterMockMvc.perform(post("/api/cmsSemesters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
                .andExpect(status().isBadRequest());

        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSemesterRepository.findAll().size();
        // set the field null
        cmsSemester.setName(null);

        // Create the CmsSemester, which fails.

        restCmsSemesterMockMvc.perform(post("/api/cmsSemesters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
                .andExpect(status().isBadRequest());

        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSemesterRepository.findAll().size();
        // set the field null
        cmsSemester.setYear(null);

        // Create the CmsSemester, which fails.

        restCmsSemesterMockMvc.perform(post("/api/cmsSemesters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
                .andExpect(status().isBadRequest());

        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSemesterRepository.findAll().size();
        // set the field null
        cmsSemester.setDuration(null);

        // Create the CmsSemester, which fails.

        restCmsSemesterMockMvc.perform(post("/api/cmsSemesters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
                .andExpect(status().isBadRequest());

        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsSemesters() throws Exception {
        // Initialize the database
        cmsSemesterRepository.saveAndFlush(cmsSemester);

        // Get all the cmsSemesters
        restCmsSemesterMockMvc.perform(get("/api/cmsSemesters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cmsSemester.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsSemester() throws Exception {
        // Initialize the database
        cmsSemesterRepository.saveAndFlush(cmsSemester);

        // Get the cmsSemester
        restCmsSemesterMockMvc.perform(get("/api/cmsSemesters/{id}", cmsSemester.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsSemester.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsSemester() throws Exception {
        // Get the cmsSemester
        restCmsSemesterMockMvc.perform(get("/api/cmsSemesters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsSemester() throws Exception {
        // Initialize the database
        cmsSemesterRepository.saveAndFlush(cmsSemester);

		int databaseSizeBeforeUpdate = cmsSemesterRepository.findAll().size();

        // Update the cmsSemester
        cmsSemester.setCode(UPDATED_CODE);
        cmsSemester.setName(UPDATED_NAME);
        cmsSemester.setYear(UPDATED_YEAR);
        cmsSemester.setDuration(UPDATED_DURATION);
        cmsSemester.setDescription(UPDATED_DESCRIPTION);
        cmsSemester.setStatus(UPDATED_STATUS);

        restCmsSemesterMockMvc.perform(put("/api/cmsSemesters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
                .andExpect(status().isOk());

        // Validate the CmsSemester in the database
        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeUpdate);
        CmsSemester testCmsSemester = cmsSemesters.get(cmsSemesters.size() - 1);
        assertThat(testCmsSemester.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCmsSemester.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCmsSemester.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCmsSemester.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testCmsSemester.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsSemester.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsSemester() throws Exception {
        // Initialize the database
        cmsSemesterRepository.saveAndFlush(cmsSemester);

		int databaseSizeBeforeDelete = cmsSemesterRepository.findAll().size();

        // Get the cmsSemester
        restCmsSemesterMockMvc.perform(delete("/api/cmsSemesters/{id}", cmsSemester.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/



package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsSemester;
import gov.step.app.repository.CmsSemesterRepository;
import gov.step.app.repository.search.CmsSemesterSearchRepository;

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
 * Test class for the CmsSemesterResource REST controller.
 *
 * @see CmsSemesterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsSemesterResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_YEAR = "AAAAA";
    private static final String UPDATED_YEAR = "BBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsSemesterRepository cmsSemesterRepository;

    @Inject
    private CmsSemesterSearchRepository cmsSemesterSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsSemesterMockMvc;

    private CmsSemester cmsSemester;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsSemesterResource cmsSemesterResource = new CmsSemesterResource();
        ReflectionTestUtils.setField(cmsSemesterResource, "cmsSemesterRepository", cmsSemesterRepository);
        ReflectionTestUtils.setField(cmsSemesterResource, "cmsSemesterSearchRepository", cmsSemesterSearchRepository);
        this.restCmsSemesterMockMvc = MockMvcBuilders.standaloneSetup(cmsSemesterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsSemester = new CmsSemester();
        cmsSemester.setCode(DEFAULT_CODE);
        cmsSemester.setName(DEFAULT_NAME);
        cmsSemester.setYear(DEFAULT_YEAR);
        cmsSemester.setDuration(DEFAULT_DURATION);
        cmsSemester.setDescription(DEFAULT_DESCRIPTION);
        cmsSemester.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsSemester() throws Exception {
        int databaseSizeBeforeCreate = cmsSemesterRepository.findAll().size();

        // Create the CmsSemester

        restCmsSemesterMockMvc.perform(post("/api/cmsSemesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
            .andExpect(status().isCreated());

        // Validate the CmsSemester in the database
        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeCreate + 1);
        CmsSemester testCmsSemester = cmsSemesters.get(cmsSemesters.size() - 1);
        assertThat(testCmsSemester.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCmsSemester.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCmsSemester.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCmsSemester.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testCmsSemester.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsSemester.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSemesterRepository.findAll().size();
        // set the field null
        cmsSemester.setCode(null);

        // Create the CmsSemester, which fails.

        restCmsSemesterMockMvc.perform(post("/api/cmsSemesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
            .andExpect(status().isBadRequest());

        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSemesterRepository.findAll().size();
        // set the field null
        cmsSemester.setName(null);

        // Create the CmsSemester, which fails.

        restCmsSemesterMockMvc.perform(post("/api/cmsSemesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
            .andExpect(status().isBadRequest());

        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSemesterRepository.findAll().size();
        // set the field null
        cmsSemester.setYear(null);

        // Create the CmsSemester, which fails.

        restCmsSemesterMockMvc.perform(post("/api/cmsSemesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
            .andExpect(status().isBadRequest());

        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSemesterRepository.findAll().size();
        // set the field null
        cmsSemester.setDuration(null);

        // Create the CmsSemester, which fails.

        restCmsSemesterMockMvc.perform(post("/api/cmsSemesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
            .andExpect(status().isBadRequest());

        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsSemesters() throws Exception {
        // Initialize the database
        cmsSemesterRepository.saveAndFlush(cmsSemester);

        // Get all the cmsSemesters
        restCmsSemesterMockMvc.perform(get("/api/cmsSemesters"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmsSemester.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsSemester() throws Exception {
        // Initialize the database
        cmsSemesterRepository.saveAndFlush(cmsSemester);

        // Get the cmsSemester
        restCmsSemesterMockMvc.perform(get("/api/cmsSemesters/{id}", cmsSemester.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsSemester.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsSemester() throws Exception {
        // Get the cmsSemester
        restCmsSemesterMockMvc.perform(get("/api/cmsSemesters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsSemester() throws Exception {
        // Initialize the database
        cmsSemesterRepository.saveAndFlush(cmsSemester);

        int databaseSizeBeforeUpdate = cmsSemesterRepository.findAll().size();

        // Update the cmsSemester
        cmsSemester.setCode(UPDATED_CODE);
        cmsSemester.setName(UPDATED_NAME);
        cmsSemester.setYear(UPDATED_YEAR);
        cmsSemester.setDuration(UPDATED_DURATION);
        cmsSemester.setDescription(UPDATED_DESCRIPTION);
        cmsSemester.setStatus(UPDATED_STATUS);

        restCmsSemesterMockMvc.perform(put("/api/cmsSemesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSemester)))
            .andExpect(status().isOk());

        // Validate the CmsSemester in the database
        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeUpdate);
        CmsSemester testCmsSemester = cmsSemesters.get(cmsSemesters.size() - 1);
        assertThat(testCmsSemester.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCmsSemester.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCmsSemester.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCmsSemester.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testCmsSemester.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsSemester.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsSemester() throws Exception {
        // Initialize the database
        cmsSemesterRepository.saveAndFlush(cmsSemester);

        int databaseSizeBeforeDelete = cmsSemesterRepository.findAll().size();

        // Get the cmsSemester
        restCmsSemesterMockMvc.perform(delete("/api/cmsSemesters/{id}", cmsSemester.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsSemester> cmsSemesters = cmsSemesterRepository.findAll();
        assertThat(cmsSemesters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
