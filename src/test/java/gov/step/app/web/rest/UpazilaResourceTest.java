package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Upazila;
import gov.step.app.repository.UpazilaRepository;
import gov.step.app.repository.search.UpazilaSearchRepository;

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
 * Test class for the UpazilaResource REST controller.
 *
 * @see UpazilaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UpazilaResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_BN_NAME = "AAAAA";
    private static final String UPDATED_BN_NAME = "BBBBB";

    @Inject
    private UpazilaRepository upazilaRepository;

    @Inject
    private UpazilaSearchRepository upazilaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUpazilaMockMvc;

    private Upazila upazila;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UpazilaResource upazilaResource = new UpazilaResource();
        ReflectionTestUtils.setField(upazilaResource, "upazilaRepository", upazilaRepository);
        ReflectionTestUtils.setField(upazilaResource, "upazilaSearchRepository", upazilaSearchRepository);
        this.restUpazilaMockMvc = MockMvcBuilders.standaloneSetup(upazilaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        upazila = new Upazila();
        upazila.setName(DEFAULT_NAME);
        upazila.setBnName(DEFAULT_BN_NAME);
    }

    @Test
    @Transactional
    public void createUpazila() throws Exception {
        int databaseSizeBeforeCreate = upazilaRepository.findAll().size();

        // Create the Upazila

        restUpazilaMockMvc.perform(post("/api/upazilas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(upazila)))
                .andExpect(status().isCreated());

        // Validate the Upazila in the database
        List<Upazila> upazilas = upazilaRepository.findAll();
        assertThat(upazilas).hasSize(databaseSizeBeforeCreate + 1);
        Upazila testUpazila = upazilas.get(upazilas.size() - 1);
        assertThat(testUpazila.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUpazila.getBnName()).isEqualTo(DEFAULT_BN_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = upazilaRepository.findAll().size();
        // set the field null
        upazila.setName(null);

        // Create the Upazila, which fails.

        restUpazilaMockMvc.perform(post("/api/upazilas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(upazila)))
                .andExpect(status().isBadRequest());

        List<Upazila> upazilas = upazilaRepository.findAll();
        assertThat(upazilas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUpazilas() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);

        // Get all the upazilas
        restUpazilaMockMvc.perform(get("/api/upazilas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(upazila.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].bnName").value(hasItem(DEFAULT_BN_NAME.toString())));
    }

    @Test
    @Transactional
    public void getUpazila() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);

        // Get the upazila
        restUpazilaMockMvc.perform(get("/api/upazilas/{id}", upazila.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(upazila.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.bnName").value(DEFAULT_BN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUpazila() throws Exception {
        // Get the upazila
        restUpazilaMockMvc.perform(get("/api/upazilas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpazila() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);

		int databaseSizeBeforeUpdate = upazilaRepository.findAll().size();

        // Update the upazila
        upazila.setName(UPDATED_NAME);
        upazila.setBnName(UPDATED_BN_NAME);

        restUpazilaMockMvc.perform(put("/api/upazilas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(upazila)))
                .andExpect(status().isOk());

        // Validate the Upazila in the database
        List<Upazila> upazilas = upazilaRepository.findAll();
        assertThat(upazilas).hasSize(databaseSizeBeforeUpdate);
        Upazila testUpazila = upazilas.get(upazilas.size() - 1);
        assertThat(testUpazila.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUpazila.getBnName()).isEqualTo(UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void deleteUpazila() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);

		int databaseSizeBeforeDelete = upazilaRepository.findAll().size();

        // Get the upazila
        restUpazilaMockMvc.perform(delete("/api/upazilas/{id}", upazila.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Upazila> upazilas = upazilaRepository.findAll();
        assertThat(upazilas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
