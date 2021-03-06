package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Religion;
import gov.step.app.repository.ReligionRepository;
import gov.step.app.repository.search.ReligionSearchRepository;

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
 * Test class for the ReligionResource REST controller.
 *
 * @see ReligionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ReligionResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ReligionRepository religionRepository;

    @Inject
    private ReligionSearchRepository religionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restReligionMockMvc;

    private Religion religion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReligionResource religionResource = new ReligionResource();
        ReflectionTestUtils.setField(religionResource, "religionRepository", religionRepository);
        ReflectionTestUtils.setField(religionResource, "religionSearchRepository", religionSearchRepository);
        this.restReligionMockMvc = MockMvcBuilders.standaloneSetup(religionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        religion = new Religion();
        religion.setName(DEFAULT_NAME);
        religion.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReligion() throws Exception {
        int databaseSizeBeforeCreate = religionRepository.findAll().size();

        // Create the Religion

        restReligionMockMvc.perform(post("/api/religions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(religion)))
                .andExpect(status().isCreated());

        // Validate the Religion in the database
        List<Religion> religions = religionRepository.findAll();
        assertThat(religions).hasSize(databaseSizeBeforeCreate + 1);
        Religion testReligion = religions.get(religions.size() - 1);
        assertThat(testReligion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReligion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = religionRepository.findAll().size();
        // set the field null
        religion.setName(null);

        // Create the Religion, which fails.

        restReligionMockMvc.perform(post("/api/religions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(religion)))
                .andExpect(status().isBadRequest());

        List<Religion> religions = religionRepository.findAll();
        assertThat(religions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReligions() throws Exception {
        // Initialize the database
        religionRepository.saveAndFlush(religion);

        // Get all the religions
        restReligionMockMvc.perform(get("/api/religions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(religion.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getReligion() throws Exception {
        // Initialize the database
        religionRepository.saveAndFlush(religion);

        // Get the religion
        restReligionMockMvc.perform(get("/api/religions/{id}", religion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(religion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReligion() throws Exception {
        // Get the religion
        restReligionMockMvc.perform(get("/api/religions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReligion() throws Exception {
        // Initialize the database
        religionRepository.saveAndFlush(religion);

		int databaseSizeBeforeUpdate = religionRepository.findAll().size();

        // Update the religion
        religion.setName(UPDATED_NAME);
        religion.setDescription(UPDATED_DESCRIPTION);

        restReligionMockMvc.perform(put("/api/religions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(religion)))
                .andExpect(status().isOk());

        // Validate the Religion in the database
        List<Religion> religions = religionRepository.findAll();
        assertThat(religions).hasSize(databaseSizeBeforeUpdate);
        Religion testReligion = religions.get(religions.size() - 1);
        assertThat(testReligion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReligion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteReligion() throws Exception {
        // Initialize the database
        religionRepository.saveAndFlush(religion);

		int databaseSizeBeforeDelete = religionRepository.findAll().size();

        // Get the religion
        restReligionMockMvc.perform(delete("/api/religions/{id}", religion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Religion> religions = religionRepository.findAll();
        assertThat(religions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
