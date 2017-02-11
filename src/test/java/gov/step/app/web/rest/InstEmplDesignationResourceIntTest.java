package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmplDesignation;
import gov.step.app.repository.InstEmplDesignationRepository;
import gov.step.app.repository.search.InstEmplDesignationSearchRepository;

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

import gov.step.app.domain.enumeration.designationType;

/**
 * Test class for the InstEmplDesignationResource REST controller.
 *
 * @see InstEmplDesignationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmplDesignationResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";


private static final designationType DEFAULT_TYPE = designationType.Teacher;
    private static final designationType UPDATED_TYPE = designationType.Employee;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private InstEmplDesignationRepository instEmplDesignationRepository;

    @Inject
    private InstEmplDesignationSearchRepository instEmplDesignationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmplDesignationMockMvc;

    private InstEmplDesignation instEmplDesignation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmplDesignationResource instEmplDesignationResource = new InstEmplDesignationResource();
        ReflectionTestUtils.setField(instEmplDesignationResource, "instEmplDesignationRepository", instEmplDesignationRepository);
        ReflectionTestUtils.setField(instEmplDesignationResource, "instEmplDesignationSearchRepository", instEmplDesignationSearchRepository);
        this.restInstEmplDesignationMockMvc = MockMvcBuilders.standaloneSetup(instEmplDesignationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmplDesignation = new InstEmplDesignation();
        instEmplDesignation.setCode(DEFAULT_CODE);
        instEmplDesignation.setName(DEFAULT_NAME);
        instEmplDesignation.setDescription(DEFAULT_DESCRIPTION);
        instEmplDesignation.setType(DEFAULT_TYPE);
        instEmplDesignation.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstEmplDesignation() throws Exception {
        int databaseSizeBeforeCreate = instEmplDesignationRepository.findAll().size();

        // Create the InstEmplDesignation

        restInstEmplDesignationMockMvc.perform(post("/api/instEmplDesignations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplDesignation)))
                .andExpect(status().isCreated());

        // Validate the InstEmplDesignation in the database
        List<InstEmplDesignation> instEmplDesignations = instEmplDesignationRepository.findAll();
        assertThat(instEmplDesignations).hasSize(databaseSizeBeforeCreate + 1);
        InstEmplDesignation testInstEmplDesignation = instEmplDesignations.get(instEmplDesignations.size() - 1);
        assertThat(testInstEmplDesignation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInstEmplDesignation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstEmplDesignation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInstEmplDesignation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInstEmplDesignation.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplDesignationRepository.findAll().size();
        // set the field null
        instEmplDesignation.setCode(null);

        // Create the InstEmplDesignation, which fails.

        restInstEmplDesignationMockMvc.perform(post("/api/instEmplDesignations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplDesignation)))
                .andExpect(status().isBadRequest());

        List<InstEmplDesignation> instEmplDesignations = instEmplDesignationRepository.findAll();
        assertThat(instEmplDesignations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplDesignationRepository.findAll().size();
        // set the field null
        instEmplDesignation.setName(null);

        // Create the InstEmplDesignation, which fails.

        restInstEmplDesignationMockMvc.perform(post("/api/instEmplDesignations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplDesignation)))
                .andExpect(status().isBadRequest());

        List<InstEmplDesignation> instEmplDesignations = instEmplDesignationRepository.findAll();
        assertThat(instEmplDesignations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstEmplDesignations() throws Exception {
        // Initialize the database
        instEmplDesignationRepository.saveAndFlush(instEmplDesignation);

        // Get all the instEmplDesignations
        restInstEmplDesignationMockMvc.perform(get("/api/instEmplDesignations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmplDesignation.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getInstEmplDesignation() throws Exception {
        // Initialize the database
        instEmplDesignationRepository.saveAndFlush(instEmplDesignation);

        // Get the instEmplDesignation
        restInstEmplDesignationMockMvc.perform(get("/api/instEmplDesignations/{id}", instEmplDesignation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmplDesignation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmplDesignation() throws Exception {
        // Get the instEmplDesignation
        restInstEmplDesignationMockMvc.perform(get("/api/instEmplDesignations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmplDesignation() throws Exception {
        // Initialize the database
        instEmplDesignationRepository.saveAndFlush(instEmplDesignation);

		int databaseSizeBeforeUpdate = instEmplDesignationRepository.findAll().size();

        // Update the instEmplDesignation
        instEmplDesignation.setCode(UPDATED_CODE);
        instEmplDesignation.setName(UPDATED_NAME);
        instEmplDesignation.setDescription(UPDATED_DESCRIPTION);
        instEmplDesignation.setType(UPDATED_TYPE);
        instEmplDesignation.setStatus(UPDATED_STATUS);

        restInstEmplDesignationMockMvc.perform(put("/api/instEmplDesignations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplDesignation)))
                .andExpect(status().isOk());

        // Validate the InstEmplDesignation in the database
        List<InstEmplDesignation> instEmplDesignations = instEmplDesignationRepository.findAll();
        assertThat(instEmplDesignations).hasSize(databaseSizeBeforeUpdate);
        InstEmplDesignation testInstEmplDesignation = instEmplDesignations.get(instEmplDesignations.size() - 1);
        assertThat(testInstEmplDesignation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInstEmplDesignation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstEmplDesignation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInstEmplDesignation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInstEmplDesignation.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstEmplDesignation() throws Exception {
        // Initialize the database
        instEmplDesignationRepository.saveAndFlush(instEmplDesignation);

		int databaseSizeBeforeDelete = instEmplDesignationRepository.findAll().size();

        // Get the instEmplDesignation
        restInstEmplDesignationMockMvc.perform(delete("/api/instEmplDesignations/{id}", instEmplDesignation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmplDesignation> instEmplDesignations = instEmplDesignationRepository.findAll();
        assertThat(instEmplDesignations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
