/*
package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsSubject;
import gov.step.app.repository.CmsSubjectRepository;
import gov.step.app.repository.search.CmsSubjectSearchRepository;

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
 * Test class for the CmsSubjectResource REST controller.
 *
 * @see CmsSubjectResource
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsSubjectResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_THEORY_CRED_HR = 1;
    private static final Integer UPDATED_THEORY_CRED_HR = 2;

    private static final Integer DEFAULT_PRAC_CRED_HR = 1;
    private static final Integer UPDATED_PRAC_CRED_HR = 2;

    private static final Integer DEFAULT_TOTAL_CRED_HR = 1;
    private static final Integer UPDATED_TOTAL_CRED_HR = 2;

    private static final Integer DEFAULT_THEORY_CON = 1;
    private static final Integer UPDATED_THEORY_CON = 2;

    private static final Integer DEFAULT_THEORY_FINAL = 1;
    private static final Integer UPDATED_THEORY_FINAL = 2;

    private static final Integer DEFAULT_PRAC_CON = 1;
    private static final Integer UPDATED_PRAC_CON = 2;

    private static final Integer DEFAULT_PRAC_FINAL = 1;
    private static final Integer UPDATED_PRAC_FINAL = 2;

    private static final Integer DEFAULT_TOTAL_MARKS = 1;
    private static final Integer UPDATED_TOTAL_MARKS = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsSubjectRepository cmsSubjectRepository;

    @Inject
    private CmsSubjectSearchRepository cmsSubjectSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsSubjectMockMvc;

    private CmsSubject cmsSubject;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsSubjectResource cmsSubjectResource = new CmsSubjectResource();
        ReflectionTestUtils.setField(cmsSubjectResource, "cmsSubjectRepository", cmsSubjectRepository);
        ReflectionTestUtils.setField(cmsSubjectResource, "cmsSubjectSearchRepository", cmsSubjectSearchRepository);
        this.restCmsSubjectMockMvc = MockMvcBuilders.standaloneSetup(cmsSubjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsSubject = new CmsSubject();
        cmsSubject.setCode(DEFAULT_CODE);
        cmsSubject.setName(DEFAULT_NAME);
        cmsSubject.setTheoryCredHr(DEFAULT_THEORY_CRED_HR);
        cmsSubject.setPracCredHr(DEFAULT_PRAC_CRED_HR);
        cmsSubject.setTotalCredHr(DEFAULT_TOTAL_CRED_HR);
        cmsSubject.setTheoryCon(DEFAULT_THEORY_CON);
        cmsSubject.setTheoryFinal(DEFAULT_THEORY_FINAL);
        cmsSubject.setPracCon(DEFAULT_PRAC_CON);
        cmsSubject.setPracFinal(DEFAULT_PRAC_FINAL);
        cmsSubject.setTotalMarks(DEFAULT_TOTAL_MARKS);
        cmsSubject.setDescription(DEFAULT_DESCRIPTION);
        cmsSubject.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsSubject() throws Exception {
        int databaseSizeBeforeCreate = cmsSubjectRepository.findAll().size();

        // Create the CmsSubject

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
                .andExpect(status().isCreated());

        // Validate the CmsSubject in the database
        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeCreate + 1);
        CmsSubject testCmsSubject = cmsSubjects.get(cmsSubjects.size() - 1);
        assertThat(testCmsSubject.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCmsSubject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCmsSubject.getTheoryCredHr()).isEqualTo(DEFAULT_THEORY_CRED_HR);
        assertThat(testCmsSubject.getPracCredHr()).isEqualTo(DEFAULT_PRAC_CRED_HR);
        assertThat(testCmsSubject.getTotalCredHr()).isEqualTo(DEFAULT_TOTAL_CRED_HR);
        assertThat(testCmsSubject.getTheoryCon()).isEqualTo(DEFAULT_THEORY_CON);
        assertThat(testCmsSubject.getTheoryFinal()).isEqualTo(DEFAULT_THEORY_FINAL);
        assertThat(testCmsSubject.getPracCon()).isEqualTo(DEFAULT_PRAC_CON);
        assertThat(testCmsSubject.getPracFinal()).isEqualTo(DEFAULT_PRAC_FINAL);
        assertThat(testCmsSubject.getTotalMarks()).isEqualTo(DEFAULT_TOTAL_MARKS);
        assertThat(testCmsSubject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsSubject.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubjectRepository.findAll().size();
        // set the field null
        cmsSubject.setCode(null);

        // Create the CmsSubject, which fails.

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
                .andExpect(status().isBadRequest());

        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubjectRepository.findAll().size();
        // set the field null
        cmsSubject.setName(null);

        // Create the CmsSubject, which fails.

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
                .andExpect(status().isBadRequest());

        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTheoryCredHrIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubjectRepository.findAll().size();
        // set the field null
        cmsSubject.setTheoryCredHr(null);

        // Create the CmsSubject, which fails.

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
                .andExpect(status().isBadRequest());

        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTheoryConIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubjectRepository.findAll().size();
        // set the field null
        cmsSubject.setTheoryCon(null);

        // Create the CmsSubject, which fails.

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
                .andExpect(status().isBadRequest());

        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTheoryFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubjectRepository.findAll().size();
        // set the field null
        cmsSubject.setTheoryFinal(null);

        // Create the CmsSubject, which fails.

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
                .andExpect(status().isBadRequest());

        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsSubjects() throws Exception {
        // Initialize the database
        cmsSubjectRepository.saveAndFlush(cmsSubject);

        // Get all the cmsSubjects
        restCmsSubjectMockMvc.perform(get("/api/cmsSubjects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cmsSubject.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].theoryCredHr").value(hasItem(DEFAULT_THEORY_CRED_HR)))
                .andExpect(jsonPath("$.[*].pracCredHr").value(hasItem(DEFAULT_PRAC_CRED_HR)))
                .andExpect(jsonPath("$.[*].totalCredHr").value(hasItem(DEFAULT_TOTAL_CRED_HR)))
                .andExpect(jsonPath("$.[*].theoryCon").value(hasItem(DEFAULT_THEORY_CON)))
                .andExpect(jsonPath("$.[*].theoryFinal").value(hasItem(DEFAULT_THEORY_FINAL)))
                .andExpect(jsonPath("$.[*].pracCon").value(hasItem(DEFAULT_PRAC_CON)))
                .andExpect(jsonPath("$.[*].pracFinal").value(hasItem(DEFAULT_PRAC_FINAL)))
                .andExpect(jsonPath("$.[*].totalMarks").value(hasItem(DEFAULT_TOTAL_MARKS)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsSubject() throws Exception {
        // Initialize the database
        cmsSubjectRepository.saveAndFlush(cmsSubject);

        // Get the cmsSubject
        restCmsSubjectMockMvc.perform(get("/api/cmsSubjects/{id}", cmsSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsSubject.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.theoryCredHr").value(DEFAULT_THEORY_CRED_HR))
            .andExpect(jsonPath("$.pracCredHr").value(DEFAULT_PRAC_CRED_HR))
            .andExpect(jsonPath("$.totalCredHr").value(DEFAULT_TOTAL_CRED_HR))
            .andExpect(jsonPath("$.theoryCon").value(DEFAULT_THEORY_CON))
            .andExpect(jsonPath("$.theoryFinal").value(DEFAULT_THEORY_FINAL))
            .andExpect(jsonPath("$.pracCon").value(DEFAULT_PRAC_CON))
            .andExpect(jsonPath("$.pracFinal").value(DEFAULT_PRAC_FINAL))
            .andExpect(jsonPath("$.totalMarks").value(DEFAULT_TOTAL_MARKS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsSubject() throws Exception {
        // Get the cmsSubject
        restCmsSubjectMockMvc.perform(get("/api/cmsSubjects/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsSubject() throws Exception {
        // Initialize the database
        cmsSubjectRepository.saveAndFlush(cmsSubject);

		int databaseSizeBeforeUpdate = cmsSubjectRepository.findAll().size();

        // Update the cmsSubject
        cmsSubject.setCode(UPDATED_CODE);
        cmsSubject.setName(UPDATED_NAME);
        cmsSubject.setTheoryCredHr(UPDATED_THEORY_CRED_HR);
        cmsSubject.setPracCredHr(UPDATED_PRAC_CRED_HR);
        cmsSubject.setTotalCredHr(UPDATED_TOTAL_CRED_HR);
        cmsSubject.setTheoryCon(UPDATED_THEORY_CON);
        cmsSubject.setTheoryFinal(UPDATED_THEORY_FINAL);
        cmsSubject.setPracCon(UPDATED_PRAC_CON);
        cmsSubject.setPracFinal(UPDATED_PRAC_FINAL);
        cmsSubject.setTotalMarks(UPDATED_TOTAL_MARKS);
        cmsSubject.setDescription(UPDATED_DESCRIPTION);
        cmsSubject.setStatus(UPDATED_STATUS);

        restCmsSubjectMockMvc.perform(put("/api/cmsSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
                .andExpect(status().isOk());

        // Validate the CmsSubject in the database
        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeUpdate);
        CmsSubject testCmsSubject = cmsSubjects.get(cmsSubjects.size() - 1);
        assertThat(testCmsSubject.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCmsSubject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCmsSubject.getTheoryCredHr()).isEqualTo(UPDATED_THEORY_CRED_HR);
        assertThat(testCmsSubject.getPracCredHr()).isEqualTo(UPDATED_PRAC_CRED_HR);
        assertThat(testCmsSubject.getTotalCredHr()).isEqualTo(UPDATED_TOTAL_CRED_HR);
        assertThat(testCmsSubject.getTheoryCon()).isEqualTo(UPDATED_THEORY_CON);
        assertThat(testCmsSubject.getTheoryFinal()).isEqualTo(UPDATED_THEORY_FINAL);
        assertThat(testCmsSubject.getPracCon()).isEqualTo(UPDATED_PRAC_CON);
        assertThat(testCmsSubject.getPracFinal()).isEqualTo(UPDATED_PRAC_FINAL);
        assertThat(testCmsSubject.getTotalMarks()).isEqualTo(UPDATED_TOTAL_MARKS);
        assertThat(testCmsSubject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsSubject.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsSubject() throws Exception {
        // Initialize the database
        cmsSubjectRepository.saveAndFlush(cmsSubject);

		int databaseSizeBeforeDelete = cmsSubjectRepository.findAll().size();

        // Get the cmsSubject
        restCmsSubjectMockMvc.perform(delete("/api/cmsSubjects/{id}", cmsSubject.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/



package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsSubject;
import gov.step.app.repository.CmsSubjectRepository;
import gov.step.app.repository.search.CmsSubjectSearchRepository;

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
 * Test class for the CmsSubjectResource REST controller.
 *
 * @see CmsSubjectResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsSubjectResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_THEORY_CRED_HR = 1;
    private static final Integer UPDATED_THEORY_CRED_HR = 2;

    private static final Integer DEFAULT_PRAC_CRED_HR = 1;
    private static final Integer UPDATED_PRAC_CRED_HR = 2;

    private static final Integer DEFAULT_TOTAL_CRED_HR = 1;
    private static final Integer UPDATED_TOTAL_CRED_HR = 2;

    private static final Integer DEFAULT_THEORY_CON = 1;
    private static final Integer UPDATED_THEORY_CON = 2;

    private static final Integer DEFAULT_THEORY_FINAL = 1;
    private static final Integer UPDATED_THEORY_FINAL = 2;

    private static final Integer DEFAULT_PRAC_CON = 1;
    private static final Integer UPDATED_PRAC_CON = 2;

    private static final Integer DEFAULT_PRAC_FINAL = 1;
    private static final Integer UPDATED_PRAC_FINAL = 2;

    private static final Integer DEFAULT_TOTAL_MARKS = 1;
    private static final Integer UPDATED_TOTAL_MARKS = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsSubjectRepository cmsSubjectRepository;

    @Inject
    private CmsSubjectSearchRepository cmsSubjectSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsSubjectMockMvc;

    private CmsSubject cmsSubject;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsSubjectResource cmsSubjectResource = new CmsSubjectResource();
        ReflectionTestUtils.setField(cmsSubjectResource, "cmsSubjectRepository", cmsSubjectRepository);
        ReflectionTestUtils.setField(cmsSubjectResource, "cmsSubjectSearchRepository", cmsSubjectSearchRepository);
        this.restCmsSubjectMockMvc = MockMvcBuilders.standaloneSetup(cmsSubjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsSubject = new CmsSubject();
        cmsSubject.setCode(DEFAULT_CODE);
        cmsSubject.setName(DEFAULT_NAME);
        cmsSubject.setTheoryCredHr(DEFAULT_THEORY_CRED_HR);
        cmsSubject.setPracCredHr(DEFAULT_PRAC_CRED_HR);
        cmsSubject.setTotalCredHr(DEFAULT_TOTAL_CRED_HR);
        cmsSubject.setTheoryCon(DEFAULT_THEORY_CON);
        cmsSubject.setTheoryFinal(DEFAULT_THEORY_FINAL);
        cmsSubject.setPracCon(DEFAULT_PRAC_CON);
        cmsSubject.setPracFinal(DEFAULT_PRAC_FINAL);
        cmsSubject.setTotalMarks(DEFAULT_TOTAL_MARKS);
        cmsSubject.setDescription(DEFAULT_DESCRIPTION);
        cmsSubject.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsSubject() throws Exception {
        int databaseSizeBeforeCreate = cmsSubjectRepository.findAll().size();

        // Create the CmsSubject

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
            .andExpect(status().isCreated());

        // Validate the CmsSubject in the database
        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeCreate + 1);
        CmsSubject testCmsSubject = cmsSubjects.get(cmsSubjects.size() - 1);
        assertThat(testCmsSubject.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCmsSubject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCmsSubject.getTheoryCredHr()).isEqualTo(DEFAULT_THEORY_CRED_HR);
        assertThat(testCmsSubject.getPracCredHr()).isEqualTo(DEFAULT_PRAC_CRED_HR);
        assertThat(testCmsSubject.getTotalCredHr()).isEqualTo(DEFAULT_TOTAL_CRED_HR);
        assertThat(testCmsSubject.getTheoryCon()).isEqualTo(DEFAULT_THEORY_CON);
        assertThat(testCmsSubject.getTheoryFinal()).isEqualTo(DEFAULT_THEORY_FINAL);
        assertThat(testCmsSubject.getPracCon()).isEqualTo(DEFAULT_PRAC_CON);
        assertThat(testCmsSubject.getPracFinal()).isEqualTo(DEFAULT_PRAC_FINAL);
        assertThat(testCmsSubject.getTotalMarks()).isEqualTo(DEFAULT_TOTAL_MARKS);
        assertThat(testCmsSubject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsSubject.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubjectRepository.findAll().size();
        // set the field null
        cmsSubject.setCode(null);

        // Create the CmsSubject, which fails.

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
            .andExpect(status().isBadRequest());

        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubjectRepository.findAll().size();
        // set the field null
        cmsSubject.setName(null);

        // Create the CmsSubject, which fails.

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
            .andExpect(status().isBadRequest());

        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTheoryCredHrIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubjectRepository.findAll().size();
        // set the field null
        cmsSubject.setTheoryCredHr(null);

        // Create the CmsSubject, which fails.

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
            .andExpect(status().isBadRequest());

        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTheoryConIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubjectRepository.findAll().size();
        // set the field null
        cmsSubject.setTheoryCon(null);

        // Create the CmsSubject, which fails.

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
            .andExpect(status().isBadRequest());

        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTheoryFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSubjectRepository.findAll().size();
        // set the field null
        cmsSubject.setTheoryFinal(null);

        // Create the CmsSubject, which fails.

        restCmsSubjectMockMvc.perform(post("/api/cmsSubjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
            .andExpect(status().isBadRequest());

        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsSubjects() throws Exception {
        // Initialize the database
        cmsSubjectRepository.saveAndFlush(cmsSubject);

        // Get all the cmsSubjects
        restCmsSubjectMockMvc.perform(get("/api/cmsSubjects"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmsSubject.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].theoryCredHr").value(hasItem(DEFAULT_THEORY_CRED_HR)))
            .andExpect(jsonPath("$.[*].pracCredHr").value(hasItem(DEFAULT_PRAC_CRED_HR)))
            .andExpect(jsonPath("$.[*].totalCredHr").value(hasItem(DEFAULT_TOTAL_CRED_HR)))
            .andExpect(jsonPath("$.[*].theoryCon").value(hasItem(DEFAULT_THEORY_CON)))
            .andExpect(jsonPath("$.[*].theoryFinal").value(hasItem(DEFAULT_THEORY_FINAL)))
            .andExpect(jsonPath("$.[*].pracCon").value(hasItem(DEFAULT_PRAC_CON)))
            .andExpect(jsonPath("$.[*].pracFinal").value(hasItem(DEFAULT_PRAC_FINAL)))
            .andExpect(jsonPath("$.[*].totalMarks").value(hasItem(DEFAULT_TOTAL_MARKS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsSubject() throws Exception {
        // Initialize the database
        cmsSubjectRepository.saveAndFlush(cmsSubject);

        // Get the cmsSubject
        restCmsSubjectMockMvc.perform(get("/api/cmsSubjects/{id}", cmsSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsSubject.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.theoryCredHr").value(DEFAULT_THEORY_CRED_HR))
            .andExpect(jsonPath("$.pracCredHr").value(DEFAULT_PRAC_CRED_HR))
            .andExpect(jsonPath("$.totalCredHr").value(DEFAULT_TOTAL_CRED_HR))
            .andExpect(jsonPath("$.theoryCon").value(DEFAULT_THEORY_CON))
            .andExpect(jsonPath("$.theoryFinal").value(DEFAULT_THEORY_FINAL))
            .andExpect(jsonPath("$.pracCon").value(DEFAULT_PRAC_CON))
            .andExpect(jsonPath("$.pracFinal").value(DEFAULT_PRAC_FINAL))
            .andExpect(jsonPath("$.totalMarks").value(DEFAULT_TOTAL_MARKS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsSubject() throws Exception {
        // Get the cmsSubject
        restCmsSubjectMockMvc.perform(get("/api/cmsSubjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsSubject() throws Exception {
        // Initialize the database
        cmsSubjectRepository.saveAndFlush(cmsSubject);

        int databaseSizeBeforeUpdate = cmsSubjectRepository.findAll().size();

        // Update the cmsSubject
        cmsSubject.setCode(UPDATED_CODE);
        cmsSubject.setName(UPDATED_NAME);
        cmsSubject.setTheoryCredHr(UPDATED_THEORY_CRED_HR);
        cmsSubject.setPracCredHr(UPDATED_PRAC_CRED_HR);
        cmsSubject.setTotalCredHr(UPDATED_TOTAL_CRED_HR);
        cmsSubject.setTheoryCon(UPDATED_THEORY_CON);
        cmsSubject.setTheoryFinal(UPDATED_THEORY_FINAL);
        cmsSubject.setPracCon(UPDATED_PRAC_CON);
        cmsSubject.setPracFinal(UPDATED_PRAC_FINAL);
        cmsSubject.setTotalMarks(UPDATED_TOTAL_MARKS);
        cmsSubject.setDescription(UPDATED_DESCRIPTION);
        cmsSubject.setStatus(UPDATED_STATUS);

        restCmsSubjectMockMvc.perform(put("/api/cmsSubjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSubject)))
            .andExpect(status().isOk());

        // Validate the CmsSubject in the database
        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeUpdate);
        CmsSubject testCmsSubject = cmsSubjects.get(cmsSubjects.size() - 1);
        assertThat(testCmsSubject.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCmsSubject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCmsSubject.getTheoryCredHr()).isEqualTo(UPDATED_THEORY_CRED_HR);
        assertThat(testCmsSubject.getPracCredHr()).isEqualTo(UPDATED_PRAC_CRED_HR);
        assertThat(testCmsSubject.getTotalCredHr()).isEqualTo(UPDATED_TOTAL_CRED_HR);
        assertThat(testCmsSubject.getTheoryCon()).isEqualTo(UPDATED_THEORY_CON);
        assertThat(testCmsSubject.getTheoryFinal()).isEqualTo(UPDATED_THEORY_FINAL);
        assertThat(testCmsSubject.getPracCon()).isEqualTo(UPDATED_PRAC_CON);
        assertThat(testCmsSubject.getPracFinal()).isEqualTo(UPDATED_PRAC_FINAL);
        assertThat(testCmsSubject.getTotalMarks()).isEqualTo(UPDATED_TOTAL_MARKS);
        assertThat(testCmsSubject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsSubject.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsSubject() throws Exception {
        // Initialize the database
        cmsSubjectRepository.saveAndFlush(cmsSubject);

        int databaseSizeBeforeDelete = cmsSubjectRepository.findAll().size();

        // Get the cmsSubject
        restCmsSubjectMockMvc.perform(delete("/api/cmsSubjects/{id}", cmsSubject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsSubject> cmsSubjects = cmsSubjectRepository.findAll();
        assertThat(cmsSubjects).hasSize(databaseSizeBeforeDelete - 1);
    }
}
