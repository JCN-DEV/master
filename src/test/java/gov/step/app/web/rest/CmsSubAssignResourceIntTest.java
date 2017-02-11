/*
package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsSubAssign;
import gov.step.app.repository.CmsSubAssignRepository;
import gov.step.app.repository.search.CmsSubAssignSearchRepository;

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
 * Test class for the CmsSubAssignResource REST controller.
 *
 * @see CmsSubAssignResource
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsSubAssignResourceIntTest {

    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_EXAM_FEE = 1;
    private static final Integer UPDATED_EXAM_FEE = 2;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsSubAssignRepository cmsSubAssignRepository;

    @Inject
    private CmsSubAssignSearchRepository cmsSubAssignSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsSubAssignMockMvc;

    private CmsSubAssign cmsSubAssign;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsSubAssignResource cmsSubAssignResource = new CmsSubAssignResource();
        ReflectionTestUtils.setField(cmsSubAssignResource, "cmsSubAssignRepository", cmsSubAssignRepository);
        ReflectionTestUtils.setField(cmsSubAssignResource, "cmsSubAssignSearchRepository", cmsSubAssignSearchRepository);
        this.restCmsSubAssignMockMvc = MockMvcBuilders.standaloneSetup(cmsSubAssignResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsSubAssign = new CmsSubAssign();
        cmsSubAssign.setDescription(DEFAULT_DESCRIPTION);
        cmsSubAssign.setExamFee(DEFAULT_EXAM_FEE);
        cmsSubAssign.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsSubAssign() throws Exception {
        int databaseSizeBeforeCreate = cmsSubAssignRepository.findAll().size();

        // Create the CmsSubAssign

        restCmsSubAssignMockMvc.perform(post("/api/cmsSubAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubAssign)))
                .andExpect(status().isCreated());

        // Validate the CmsSubAssign in the database
        List<CmsSubAssign> cmsSubAssigns = cmsSubAssignRepository.findAll();
        assertThat(cmsSubAssigns).hasSize(databaseSizeBeforeCreate + 1);
        CmsSubAssign testCmsSubAssign = cmsSubAssigns.get(cmsSubAssigns.size() - 1);
        assertThat(testCmsSubAssign.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsSubAssign.getExamFee()).isEqualTo(DEFAULT_EXAM_FEE);
        assertThat(testCmsSubAssign.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubAssignRepository.findAll().size();
        // set the field null

        // Create the CmsSubAssign, which fails.

        restCmsSubAssignMockMvc.perform(post("/api/cmsSubAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubAssign)))
                .andExpect(status().isBadRequest());

        List<CmsSubAssign> cmsSubAssigns = cmsSubAssignRepository.findAll();
        assertThat(cmsSubAssigns).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExamFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubAssignRepository.findAll().size();
        // set the field null
        cmsSubAssign.setExamFee(null);

        // Create the CmsSubAssign, which fails.

        restCmsSubAssignMockMvc.perform(post("/api/cmsSubAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubAssign)))
                .andExpect(status().isBadRequest());

        List<CmsSubAssign> cmsSubAssigns = cmsSubAssignRepository.findAll();
        assertThat(cmsSubAssigns).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsSubAssigns() throws Exception {
        // Initialize the database
        cmsSubAssignRepository.saveAndFlush(cmsSubAssign);

        // Get all the cmsSubAssigns
        restCmsSubAssignMockMvc.perform(get("/api/cmsSubAssigns"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cmsSubAssign.getId().intValue())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].examFee").value(hasItem(DEFAULT_EXAM_FEE)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsSubAssign() throws Exception {
        // Initialize the database
        cmsSubAssignRepository.saveAndFlush(cmsSubAssign);

        // Get the cmsSubAssign
        restCmsSubAssignMockMvc.perform(get("/api/cmsSubAssigns/{id}", cmsSubAssign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsSubAssign.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.examFee").value(DEFAULT_EXAM_FEE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsSubAssign() throws Exception {
        // Get the cmsSubAssign
        restCmsSubAssignMockMvc.perform(get("/api/cmsSubAssigns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsSubAssign() throws Exception {
        // Initialize the database
        cmsSubAssignRepository.saveAndFlush(cmsSubAssign);

		int databaseSizeBeforeUpdate = cmsSubAssignRepository.findAll().size();

        // Update the cmsSubAssign
        cmsSubAssign.setDescription(UPDATED_DESCRIPTION);
        cmsSubAssign.setExamFee(UPDATED_EXAM_FEE);
        cmsSubAssign.setStatus(UPDATED_STATUS);

        restCmsSubAssignMockMvc.perform(put("/api/cmsSubAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubAssign)))
                .andExpect(status().isOk());

        // Validate the CmsSubAssign in the database
        List<CmsSubAssign> cmsSubAssigns = cmsSubAssignRepository.findAll();
        assertThat(cmsSubAssigns).hasSize(databaseSizeBeforeUpdate);
        CmsSubAssign testCmsSubAssign = cmsSubAssigns.get(cmsSubAssigns.size() - 1);
        assertThat(testCmsSubAssign.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsSubAssign.getExamFee()).isEqualTo(UPDATED_EXAM_FEE);
        assertThat(testCmsSubAssign.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsSubAssign() throws Exception {
        // Initialize the database
        cmsSubAssignRepository.saveAndFlush(cmsSubAssign);

		int databaseSizeBeforeDelete = cmsSubAssignRepository.findAll().size();

        // Get the cmsSubAssign
        restCmsSubAssignMockMvc.perform(delete("/api/cmsSubAssigns/{id}", cmsSubAssign.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsSubAssign> cmsSubAssigns = cmsSubAssignRepository.findAll();
        assertThat(cmsSubAssigns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/


package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsSubAssign;
import gov.step.app.repository.CmsSubAssignRepository;
import gov.step.app.repository.search.CmsSubAssignSearchRepository;

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
 * Test class for the CmsSubAssignResource REST controller.
 *
 * @see CmsSubAssignResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsSubAssignResourceIntTest {

    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_EXAM_FEE = 1;
    private static final Integer UPDATED_EXAM_FEE = 2;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsSubAssignRepository cmsSubAssignRepository;

    @Inject
    private CmsSubAssignSearchRepository cmsSubAssignSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsSubAssignMockMvc;

    private CmsSubAssign cmsSubAssign;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsSubAssignResource cmsSubAssignResource = new CmsSubAssignResource();
        ReflectionTestUtils.setField(cmsSubAssignResource, "cmsSubAssignRepository", cmsSubAssignRepository);
        ReflectionTestUtils.setField(cmsSubAssignResource, "cmsSubAssignSearchRepository", cmsSubAssignSearchRepository);
        this.restCmsSubAssignMockMvc = MockMvcBuilders.standaloneSetup(cmsSubAssignResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsSubAssign = new CmsSubAssign();
        cmsSubAssign.setDescription(DEFAULT_DESCRIPTION);
        cmsSubAssign.setExamFee(DEFAULT_EXAM_FEE);
        cmsSubAssign.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsSubAssign() throws Exception {
        int databaseSizeBeforeCreate = cmsSubAssignRepository.findAll().size();

        // Create the CmsSubAssign

        restCmsSubAssignMockMvc.perform(post("/api/cmsSubAssigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubAssign)))
            .andExpect(status().isCreated());

        // Validate the CmsSubAssign in the database
        List<CmsSubAssign> cmsSubAssigns = cmsSubAssignRepository.findAll();
        assertThat(cmsSubAssigns).hasSize(databaseSizeBeforeCreate + 1);
        CmsSubAssign testCmsSubAssign = cmsSubAssigns.get(cmsSubAssigns.size() - 1);
        assertThat(testCmsSubAssign.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsSubAssign.getExamFee()).isEqualTo(DEFAULT_EXAM_FEE);
        assertThat(testCmsSubAssign.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubAssignRepository.findAll().size();
        // set the field null

        // Create the CmsSubAssign, which fails.

        restCmsSubAssignMockMvc.perform(post("/api/cmsSubAssigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubAssign)))
            .andExpect(status().isBadRequest());

        List<CmsSubAssign> cmsSubAssigns = cmsSubAssignRepository.findAll();
        assertThat(cmsSubAssigns).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExamFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubAssignRepository.findAll().size();
        // set the field null
        cmsSubAssign.setExamFee(null);

        // Create the CmsSubAssign, which fails.

        restCmsSubAssignMockMvc.perform(post("/api/cmsSubAssigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubAssign)))
            .andExpect(status().isBadRequest());

        List<CmsSubAssign> cmsSubAssigns = cmsSubAssignRepository.findAll();
        assertThat(cmsSubAssigns).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsSubAssigns() throws Exception {
        // Initialize the database
        cmsSubAssignRepository.saveAndFlush(cmsSubAssign);

        // Get all the cmsSubAssigns
        restCmsSubAssignMockMvc.perform(get("/api/cmsSubAssigns"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmsSubAssign.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].examFee").value(hasItem(DEFAULT_EXAM_FEE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsSubAssign() throws Exception {
        // Initialize the database
        cmsSubAssignRepository.saveAndFlush(cmsSubAssign);

        // Get the cmsSubAssign
        restCmsSubAssignMockMvc.perform(get("/api/cmsSubAssigns/{id}", cmsSubAssign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsSubAssign.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.examFee").value(DEFAULT_EXAM_FEE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsSubAssign() throws Exception {
        // Get the cmsSubAssign
        restCmsSubAssignMockMvc.perform(get("/api/cmsSubAssigns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsSubAssign() throws Exception {
        // Initialize the database
        cmsSubAssignRepository.saveAndFlush(cmsSubAssign);

        int databaseSizeBeforeUpdate = cmsSubAssignRepository.findAll().size();

        // Update the cmsSubAssign
        cmsSubAssign.setDescription(UPDATED_DESCRIPTION);
        cmsSubAssign.setExamFee(UPDATED_EXAM_FEE);
        cmsSubAssign.setStatus(UPDATED_STATUS);

        restCmsSubAssignMockMvc.perform(put("/api/cmsSubAssigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubAssign)))
            .andExpect(status().isOk());

        // Validate the CmsSubAssign in the database
        List<CmsSubAssign> cmsSubAssigns = cmsSubAssignRepository.findAll();
        assertThat(cmsSubAssigns).hasSize(databaseSizeBeforeUpdate);
        CmsSubAssign testCmsSubAssign = cmsSubAssigns.get(cmsSubAssigns.size() - 1);
        assertThat(testCmsSubAssign.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsSubAssign.getExamFee()).isEqualTo(UPDATED_EXAM_FEE);
        assertThat(testCmsSubAssign.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsSubAssign() throws Exception {
        // Initialize the database
        cmsSubAssignRepository.saveAndFlush(cmsSubAssign);

        int databaseSizeBeforeDelete = cmsSubAssignRepository.findAll().size();

        // Get the cmsSubAssign
        restCmsSubAssignMockMvc.perform(delete("/api/cmsSubAssigns/{id}", cmsSubAssign.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsSubAssign> cmsSubAssigns = cmsSubAssignRepository.findAll();
        assertThat(cmsSubAssigns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
