package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Reference;
import gov.step.app.repository.ReferenceRepository;
import gov.step.app.repository.search.ReferenceSearchRepository;

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
 * Test class for the ReferenceResource REST controller.
 *
 * @see ReferenceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ReferenceResourceTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_ORGANISATION = "AA";
    private static final String UPDATED_ORGANISATION = "BB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_RELATION = "AAAAA";
    private static final String UPDATED_RELATION = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    @Inject
    private ReferenceRepository referenceRepository;

    @Inject
    private ReferenceSearchRepository referenceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restReferenceMockMvc;

    private Reference reference;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReferenceResource referenceResource = new ReferenceResource();
        ReflectionTestUtils.setField(referenceResource, "referenceRepository", referenceRepository);
        ReflectionTestUtils.setField(referenceResource, "referenceSearchRepository", referenceSearchRepository);
        this.restReferenceMockMvc = MockMvcBuilders.standaloneSetup(referenceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        reference = new Reference();
        reference.setName(DEFAULT_NAME);
        reference.setEmail(DEFAULT_EMAIL);
        reference.setOrganisation(DEFAULT_ORGANISATION);
        reference.setDesignation(DEFAULT_DESIGNATION);
        reference.setRelation(DEFAULT_RELATION);
        reference.setPhone(DEFAULT_PHONE);
        reference.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createReference() throws Exception {
        int databaseSizeBeforeCreate = referenceRepository.findAll().size();

        // Create the Reference

        restReferenceMockMvc.perform(post("/api/references")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reference)))
                .andExpect(status().isCreated());

        // Validate the Reference in the database
        List<Reference> references = referenceRepository.findAll();
        assertThat(references).hasSize(databaseSizeBeforeCreate + 1);
        Reference testReference = references.get(references.size() - 1);
        assertThat(testReference.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReference.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testReference.getOrganisation()).isEqualTo(DEFAULT_ORGANISATION);
        assertThat(testReference.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testReference.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testReference.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testReference.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = referenceRepository.findAll().size();
        // set the field null
        reference.setName(null);

        // Create the Reference, which fails.

        restReferenceMockMvc.perform(post("/api/references")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reference)))
                .andExpect(status().isBadRequest());

        List<Reference> references = referenceRepository.findAll();
        assertThat(references).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = referenceRepository.findAll().size();
        // set the field null
        reference.setEmail(null);

        // Create the Reference, which fails.

        restReferenceMockMvc.perform(post("/api/references")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reference)))
                .andExpect(status().isBadRequest());

        List<Reference> references = referenceRepository.findAll();
        assertThat(references).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrganisationIsRequired() throws Exception {
        int databaseSizeBeforeTest = referenceRepository.findAll().size();
        // set the field null
        reference.setOrganisation(null);

        // Create the Reference, which fails.

        restReferenceMockMvc.perform(post("/api/references")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reference)))
                .andExpect(status().isBadRequest());

        List<Reference> references = referenceRepository.findAll();
        assertThat(references).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReferences() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the references
        restReferenceMockMvc.perform(get("/api/references"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(reference.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].organisation").value(hasItem(DEFAULT_ORGANISATION.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getReference() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get the reference
        restReferenceMockMvc.perform(get("/api/references/{id}", reference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(reference.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.organisation").value(DEFAULT_ORGANISATION.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReference() throws Exception {
        // Get the reference
        restReferenceMockMvc.perform(get("/api/references/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReference() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

		int databaseSizeBeforeUpdate = referenceRepository.findAll().size();

        // Update the reference
        reference.setName(UPDATED_NAME);
        reference.setEmail(UPDATED_EMAIL);
        reference.setOrganisation(UPDATED_ORGANISATION);
        reference.setDesignation(UPDATED_DESIGNATION);
        reference.setRelation(UPDATED_RELATION);
        reference.setPhone(UPDATED_PHONE);
        reference.setRemarks(UPDATED_REMARKS);

        restReferenceMockMvc.perform(put("/api/references")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reference)))
                .andExpect(status().isOk());

        // Validate the Reference in the database
        List<Reference> references = referenceRepository.findAll();
        assertThat(references).hasSize(databaseSizeBeforeUpdate);
        Reference testReference = references.get(references.size() - 1);
        assertThat(testReference.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReference.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testReference.getOrganisation()).isEqualTo(UPDATED_ORGANISATION);
        assertThat(testReference.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testReference.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testReference.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testReference.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteReference() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

		int databaseSizeBeforeDelete = referenceRepository.findAll().size();

        // Get the reference
        restReferenceMockMvc.perform(delete("/api/references/{id}", reference.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Reference> references = referenceRepository.findAll();
        assertThat(references).hasSize(databaseSizeBeforeDelete - 1);
    }
}
