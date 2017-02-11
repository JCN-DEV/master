package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Division;
import gov.step.app.repository.DivisionRepository;
import gov.step.app.repository.search.DivisionSearchRepository;

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
 * Test class for the DivisionResource REST controller.
 *
 * @see DivisionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DivisionResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_BN_NAME = "AAAAA";
    private static final String UPDATED_BN_NAME = "BBBBB";

    @Inject
    private DivisionRepository divisionRepository;

    @Inject
    private DivisionSearchRepository divisionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDivisionMockMvc;

    private Division division;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DivisionResource divisionResource = new DivisionResource();
        ReflectionTestUtils.setField(divisionResource, "divisionRepository", divisionRepository);
        ReflectionTestUtils.setField(divisionResource, "divisionSearchRepository", divisionSearchRepository);
        this.restDivisionMockMvc = MockMvcBuilders.standaloneSetup(divisionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        division = new Division();
        division.setName(DEFAULT_NAME);
        division.setBnName(DEFAULT_BN_NAME);
    }

    @Test
    @Transactional
    public void createDivision() throws Exception {
        int databaseSizeBeforeCreate = divisionRepository.findAll().size();

        // Create the Division

        restDivisionMockMvc.perform(post("/api/divisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(division)))
                .andExpect(status().isCreated());

        // Validate the Division in the database
        List<Division> divisions = divisionRepository.findAll();
        assertThat(divisions).hasSize(databaseSizeBeforeCreate + 1);
        Division testDivision = divisions.get(divisions.size() - 1);
        assertThat(testDivision.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDivision.getBnName()).isEqualTo(DEFAULT_BN_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionRepository.findAll().size();
        // set the field null
        division.setName(null);

        // Create the Division, which fails.

        restDivisionMockMvc.perform(post("/api/divisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(division)))
                .andExpect(status().isBadRequest());

        List<Division> divisions = divisionRepository.findAll();
        assertThat(divisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDivisions() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisions
        restDivisionMockMvc.perform(get("/api/divisions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(division.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].bnName").value(hasItem(DEFAULT_BN_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", division.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(division.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.bnName").value(DEFAULT_BN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDivision() throws Exception {
        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

		int databaseSizeBeforeUpdate = divisionRepository.findAll().size();

        // Update the division
        division.setName(UPDATED_NAME);
        division.setBnName(UPDATED_BN_NAME);

        restDivisionMockMvc.perform(put("/api/divisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(division)))
                .andExpect(status().isOk());

        // Validate the Division in the database
        List<Division> divisions = divisionRepository.findAll();
        assertThat(divisions).hasSize(databaseSizeBeforeUpdate);
        Division testDivision = divisions.get(divisions.size() - 1);
        assertThat(testDivision.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDivision.getBnName()).isEqualTo(UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void deleteDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

		int databaseSizeBeforeDelete = divisionRepository.findAll().size();

        // Get the division
        restDivisionMockMvc.perform(delete("/api/divisions/{id}", division.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Division> divisions = divisionRepository.findAll();
        assertThat(divisions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
