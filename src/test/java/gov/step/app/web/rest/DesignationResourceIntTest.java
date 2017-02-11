package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Designation;
import gov.step.app.repository.DesignationRepository;
import gov.step.app.repository.search.DesignationSearchRepository;

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
 * Test class for the DesignationResource REST controller.
 *
 * @see DesignationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DesignationResourceIntTest {

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
    private DesignationRepository designationRepository;

    @Inject
    private DesignationSearchRepository designationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDesignationMockMvc;

    private Designation designation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DesignationResource designationResource = new DesignationResource();
        ReflectionTestUtils.setField(designationResource, "designationRepository", designationRepository);
        ReflectionTestUtils.setField(designationResource, "designationSearchRepository", designationSearchRepository);
        this.restDesignationMockMvc = MockMvcBuilders.standaloneSetup(designationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        designation = new Designation();
        designation.setCode(DEFAULT_CODE);
        designation.setName(DEFAULT_NAME);
        designation.setDescription(DEFAULT_DESCRIPTION);
        designation.setType(DEFAULT_TYPE);
        designation.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDesignation() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();

        // Create the Designation

        restDesignationMockMvc.perform(post("/api/designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(designation)))
                .andExpect(status().isCreated());

        // Validate the Designation in the database
        List<Designation> designations = designationRepository.findAll();
        assertThat(designations).hasSize(databaseSizeBeforeCreate + 1);
        Designation testDesignation = designations.get(designations.size() - 1);
        assertThat(testDesignation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDesignation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDesignation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDesignation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDesignation.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationRepository.findAll().size();
        // set the field null
        designation.setCode(null);

        // Create the Designation, which fails.

        restDesignationMockMvc.perform(post("/api/designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(designation)))
                .andExpect(status().isBadRequest());

        List<Designation> designations = designationRepository.findAll();
        assertThat(designations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationRepository.findAll().size();
        // set the field null
        designation.setName(null);

        // Create the Designation, which fails.

        restDesignationMockMvc.perform(post("/api/designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(designation)))
                .andExpect(status().isBadRequest());

        List<Designation> designations = designationRepository.findAll();
        assertThat(designations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignations() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designations
        restDesignationMockMvc.perform(get("/api/designations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get the designation
        restDesignationMockMvc.perform(get("/api/designations/{id}", designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(designation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignation() throws Exception {
        // Get the designation
        restDesignationMockMvc.perform(get("/api/designations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

		int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation
        designation.setCode(UPDATED_CODE);
        designation.setName(UPDATED_NAME);
        designation.setDescription(UPDATED_DESCRIPTION);
        designation.setType(UPDATED_TYPE);
        designation.setStatus(UPDATED_STATUS);

        restDesignationMockMvc.perform(put("/api/designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(designation)))
                .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designations = designationRepository.findAll();
        assertThat(designations).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designations.get(designations.size() - 1);
        assertThat(testDesignation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDesignation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDesignation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDesignation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDesignation.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

		int databaseSizeBeforeDelete = designationRepository.findAll().size();

        // Get the designation
        restDesignationMockMvc.perform(delete("/api/designations/{id}", designation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Designation> designations = designationRepository.findAll();
        assertThat(designations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
