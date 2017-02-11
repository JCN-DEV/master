package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JpEmployeeReference;
import gov.step.app.repository.JpEmployeeReferenceRepository;
import gov.step.app.repository.search.JpEmployeeReferenceSearchRepository;

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
 * Test class for the JpEmployeeReferenceResource REST controller.
 *
 * @see JpEmployeeReferenceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JpEmployeeReferenceResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_ORGANISATION = "AAAAA";
    private static final String UPDATED_ORGANISATION = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_RELATION = "AAAAA";
    private static final String UPDATED_RELATION = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    @Inject
    private JpEmployeeReferenceRepository jpEmployeeReferenceRepository;

    @Inject
    private JpEmployeeReferenceSearchRepository jpEmployeeReferenceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJpEmployeeReferenceMockMvc;

    private JpEmployeeReference jpEmployeeReference;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpEmployeeReferenceResource jpEmployeeReferenceResource = new JpEmployeeReferenceResource();
        ReflectionTestUtils.setField(jpEmployeeReferenceResource, "jpEmployeeReferenceRepository", jpEmployeeReferenceRepository);
        ReflectionTestUtils.setField(jpEmployeeReferenceResource, "jpEmployeeReferenceSearchRepository", jpEmployeeReferenceSearchRepository);
        this.restJpEmployeeReferenceMockMvc = MockMvcBuilders.standaloneSetup(jpEmployeeReferenceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jpEmployeeReference = new JpEmployeeReference();
        jpEmployeeReference.setName(DEFAULT_NAME);
        jpEmployeeReference.setEmail(DEFAULT_EMAIL);
        jpEmployeeReference.setOrganisation(DEFAULT_ORGANISATION);
        jpEmployeeReference.setDesignation(DEFAULT_DESIGNATION);
        jpEmployeeReference.setRelation(DEFAULT_RELATION);
        jpEmployeeReference.setPhone(DEFAULT_PHONE);
        jpEmployeeReference.setAddress(DEFAULT_ADDRESS);
        jpEmployeeReference.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createJpEmployeeReference() throws Exception {
        int databaseSizeBeforeCreate = jpEmployeeReferenceRepository.findAll().size();

        // Create the JpEmployeeReference

        restJpEmployeeReferenceMockMvc.perform(post("/api/jpEmployeeReferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeReference)))
                .andExpect(status().isCreated());

        // Validate the JpEmployeeReference in the database
        List<JpEmployeeReference> jpEmployeeReferences = jpEmployeeReferenceRepository.findAll();
        assertThat(jpEmployeeReferences).hasSize(databaseSizeBeforeCreate + 1);
        JpEmployeeReference testJpEmployeeReference = jpEmployeeReferences.get(jpEmployeeReferences.size() - 1);
        assertThat(testJpEmployeeReference.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJpEmployeeReference.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testJpEmployeeReference.getOrganisation()).isEqualTo(DEFAULT_ORGANISATION);
        assertThat(testJpEmployeeReference.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testJpEmployeeReference.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testJpEmployeeReference.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testJpEmployeeReference.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testJpEmployeeReference.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmployeeReferenceRepository.findAll().size();
        // set the field null
        jpEmployeeReference.setName(null);

        // Create the JpEmployeeReference, which fails.

        restJpEmployeeReferenceMockMvc.perform(post("/api/jpEmployeeReferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeReference)))
                .andExpect(status().isBadRequest());

        List<JpEmployeeReference> jpEmployeeReferences = jpEmployeeReferenceRepository.findAll();
        assertThat(jpEmployeeReferences).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmployeeReferenceRepository.findAll().size();
        // set the field null
        jpEmployeeReference.setEmail(null);

        // Create the JpEmployeeReference, which fails.

        restJpEmployeeReferenceMockMvc.perform(post("/api/jpEmployeeReferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeReference)))
                .andExpect(status().isBadRequest());

        List<JpEmployeeReference> jpEmployeeReferences = jpEmployeeReferenceRepository.findAll();
        assertThat(jpEmployeeReferences).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrganisationIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpEmployeeReferenceRepository.findAll().size();
        // set the field null
        jpEmployeeReference.setOrganisation(null);

        // Create the JpEmployeeReference, which fails.

        restJpEmployeeReferenceMockMvc.perform(post("/api/jpEmployeeReferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeReference)))
                .andExpect(status().isBadRequest());

        List<JpEmployeeReference> jpEmployeeReferences = jpEmployeeReferenceRepository.findAll();
        assertThat(jpEmployeeReferences).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJpEmployeeReferences() throws Exception {
        // Initialize the database
        jpEmployeeReferenceRepository.saveAndFlush(jpEmployeeReference);

        // Get all the jpEmployeeReferences
        restJpEmployeeReferenceMockMvc.perform(get("/api/jpEmployeeReferences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jpEmployeeReference.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].organisation").value(hasItem(DEFAULT_ORGANISATION.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getJpEmployeeReference() throws Exception {
        // Initialize the database
        jpEmployeeReferenceRepository.saveAndFlush(jpEmployeeReference);

        // Get the jpEmployeeReference
        restJpEmployeeReferenceMockMvc.perform(get("/api/jpEmployeeReferences/{id}", jpEmployeeReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jpEmployeeReference.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.organisation").value(DEFAULT_ORGANISATION.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJpEmployeeReference() throws Exception {
        // Get the jpEmployeeReference
        restJpEmployeeReferenceMockMvc.perform(get("/api/jpEmployeeReferences/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpEmployeeReference() throws Exception {
        // Initialize the database
        jpEmployeeReferenceRepository.saveAndFlush(jpEmployeeReference);

		int databaseSizeBeforeUpdate = jpEmployeeReferenceRepository.findAll().size();

        // Update the jpEmployeeReference
        jpEmployeeReference.setName(UPDATED_NAME);
        jpEmployeeReference.setEmail(UPDATED_EMAIL);
        jpEmployeeReference.setOrganisation(UPDATED_ORGANISATION);
        jpEmployeeReference.setDesignation(UPDATED_DESIGNATION);
        jpEmployeeReference.setRelation(UPDATED_RELATION);
        jpEmployeeReference.setPhone(UPDATED_PHONE);
        jpEmployeeReference.setAddress(UPDATED_ADDRESS);
        jpEmployeeReference.setRemarks(UPDATED_REMARKS);

        restJpEmployeeReferenceMockMvc.perform(put("/api/jpEmployeeReferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeReference)))
                .andExpect(status().isOk());

        // Validate the JpEmployeeReference in the database
        List<JpEmployeeReference> jpEmployeeReferences = jpEmployeeReferenceRepository.findAll();
        assertThat(jpEmployeeReferences).hasSize(databaseSizeBeforeUpdate);
        JpEmployeeReference testJpEmployeeReference = jpEmployeeReferences.get(jpEmployeeReferences.size() - 1);
        assertThat(testJpEmployeeReference.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJpEmployeeReference.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testJpEmployeeReference.getOrganisation()).isEqualTo(UPDATED_ORGANISATION);
        assertThat(testJpEmployeeReference.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testJpEmployeeReference.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testJpEmployeeReference.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testJpEmployeeReference.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testJpEmployeeReference.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteJpEmployeeReference() throws Exception {
        // Initialize the database
        jpEmployeeReferenceRepository.saveAndFlush(jpEmployeeReference);

		int databaseSizeBeforeDelete = jpEmployeeReferenceRepository.findAll().size();

        // Get the jpEmployeeReference
        restJpEmployeeReferenceMockMvc.perform(delete("/api/jpEmployeeReferences/{id}", jpEmployeeReference.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JpEmployeeReference> jpEmployeeReferences = jpEmployeeReferenceRepository.findAll();
        assertThat(jpEmployeeReferences).hasSize(databaseSizeBeforeDelete - 1);
    }
}
