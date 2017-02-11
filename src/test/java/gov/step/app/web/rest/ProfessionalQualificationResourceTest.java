package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.ProfessionalQualification;
import gov.step.app.repository.ProfessionalQualificationRepository;
import gov.step.app.repository.search.ProfessionalQualificationSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProfessionalQualificationResource REST controller.
 *
 * @see ProfessionalQualificationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProfessionalQualificationResourceTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";
    private static final String DEFAULT_LOCATION = "AAAA";
    private static final String UPDATED_LOCATION = "BBBB";

    private static final LocalDate DEFAULT_DATE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    @Inject
    private ProfessionalQualificationRepository professionalQualificationRepository;

    @Inject
    private ProfessionalQualificationSearchRepository professionalQualificationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProfessionalQualificationMockMvc;

    private ProfessionalQualification professionalQualification;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfessionalQualificationResource professionalQualificationResource = new ProfessionalQualificationResource();
        ReflectionTestUtils.setField(professionalQualificationResource, "professionalQualificationRepository", professionalQualificationRepository);
        ReflectionTestUtils.setField(professionalQualificationResource, "professionalQualificationSearchRepository", professionalQualificationSearchRepository);
        this.restProfessionalQualificationMockMvc = MockMvcBuilders.standaloneSetup(professionalQualificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        professionalQualification = new ProfessionalQualification();
        professionalQualification.setName(DEFAULT_NAME);
        professionalQualification.setLocation(DEFAULT_LOCATION);
        professionalQualification.setDateFrom(DEFAULT_DATE_FROM);
        professionalQualification.setDateTo(DEFAULT_DATE_TO);
        professionalQualification.setDuration(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createProfessionalQualification() throws Exception {
        int databaseSizeBeforeCreate = professionalQualificationRepository.findAll().size();

        // Create the ProfessionalQualification

        restProfessionalQualificationMockMvc.perform(post("/api/professionalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professionalQualification)))
                .andExpect(status().isCreated());

        // Validate the ProfessionalQualification in the database
        List<ProfessionalQualification> professionalQualifications = professionalQualificationRepository.findAll();
        assertThat(professionalQualifications).hasSize(databaseSizeBeforeCreate + 1);
        ProfessionalQualification testProfessionalQualification = professionalQualifications.get(professionalQualifications.size() - 1);
        assertThat(testProfessionalQualification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProfessionalQualification.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProfessionalQualification.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testProfessionalQualification.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testProfessionalQualification.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = professionalQualificationRepository.findAll().size();
        // set the field null
        professionalQualification.setName(null);

        // Create the ProfessionalQualification, which fails.

        restProfessionalQualificationMockMvc.perform(post("/api/professionalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professionalQualification)))
                .andExpect(status().isBadRequest());

        List<ProfessionalQualification> professionalQualifications = professionalQualificationRepository.findAll();
        assertThat(professionalQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = professionalQualificationRepository.findAll().size();
        // set the field null
        professionalQualification.setLocation(null);

        // Create the ProfessionalQualification, which fails.

        restProfessionalQualificationMockMvc.perform(post("/api/professionalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professionalQualification)))
                .andExpect(status().isBadRequest());

        List<ProfessionalQualification> professionalQualifications = professionalQualificationRepository.findAll();
        assertThat(professionalQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = professionalQualificationRepository.findAll().size();
        // set the field null
        professionalQualification.setDuration(null);

        // Create the ProfessionalQualification, which fails.

        restProfessionalQualificationMockMvc.perform(post("/api/professionalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professionalQualification)))
                .andExpect(status().isBadRequest());

        List<ProfessionalQualification> professionalQualifications = professionalQualificationRepository.findAll();
        assertThat(professionalQualifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfessionalQualifications() throws Exception {
        // Initialize the database
        professionalQualificationRepository.saveAndFlush(professionalQualification);

        // Get all the professionalQualifications
        restProfessionalQualificationMockMvc.perform(get("/api/professionalQualifications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(professionalQualification.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
                .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)));
    }

    @Test
    @Transactional
    public void getProfessionalQualification() throws Exception {
        // Initialize the database
        professionalQualificationRepository.saveAndFlush(professionalQualification);

        // Get the professionalQualification
        restProfessionalQualificationMockMvc.perform(get("/api/professionalQualifications/{id}", professionalQualification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(professionalQualification.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION));
    }

    @Test
    @Transactional
    public void getNonExistingProfessionalQualification() throws Exception {
        // Get the professionalQualification
        restProfessionalQualificationMockMvc.perform(get("/api/professionalQualifications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessionalQualification() throws Exception {
        // Initialize the database
        professionalQualificationRepository.saveAndFlush(professionalQualification);

		int databaseSizeBeforeUpdate = professionalQualificationRepository.findAll().size();

        // Update the professionalQualification
        professionalQualification.setName(UPDATED_NAME);
        professionalQualification.setLocation(UPDATED_LOCATION);
        professionalQualification.setDateFrom(UPDATED_DATE_FROM);
        professionalQualification.setDateTo(UPDATED_DATE_TO);
        professionalQualification.setDuration(UPDATED_DURATION);

        restProfessionalQualificationMockMvc.perform(put("/api/professionalQualifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professionalQualification)))
                .andExpect(status().isOk());

        // Validate the ProfessionalQualification in the database
        List<ProfessionalQualification> professionalQualifications = professionalQualificationRepository.findAll();
        assertThat(professionalQualifications).hasSize(databaseSizeBeforeUpdate);
        ProfessionalQualification testProfessionalQualification = professionalQualifications.get(professionalQualifications.size() - 1);
        assertThat(testProfessionalQualification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfessionalQualification.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProfessionalQualification.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testProfessionalQualification.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testProfessionalQualification.getDuration()).isEqualTo(UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void deleteProfessionalQualification() throws Exception {
        // Initialize the database
        professionalQualificationRepository.saveAndFlush(professionalQualification);

		int databaseSizeBeforeDelete = professionalQualificationRepository.findAll().size();

        // Get the professionalQualification
        restProfessionalQualificationMockMvc.perform(delete("/api/professionalQualifications/{id}", professionalQualification.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProfessionalQualification> professionalQualifications = professionalQualificationRepository.findAll();
        assertThat(professionalQualifications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
