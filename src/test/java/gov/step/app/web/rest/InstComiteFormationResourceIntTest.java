package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstComiteFormation;
import gov.step.app.repository.InstComiteFormationRepository;
import gov.step.app.repository.search.InstComiteFormationSearchRepository;

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
 * Test class for the InstComiteFormationResource REST controller.
 *
 * @see InstComiteFormationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstComiteFormationResourceIntTest {

    private static final String DEFAULT_COMITE_NAME = "AAAAA";
    private static final String UPDATED_COMITE_NAME = "BBBBB";
    private static final String DEFAULT_COMITE_TYPE = "AAAAA";
    private static final String UPDATED_COMITE_TYPE = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final Integer DEFAULT_TIME_FROM = 1;
    private static final Integer UPDATED_TIME_FROM = 2;

    private static final Integer DEFAULT_TIME_TO = 1;
    private static final Integer UPDATED_TIME_TO = 2;

    private static final LocalDate DEFAULT_FORMATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FORMATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private InstComiteFormationRepository instComiteFormationRepository;

    @Inject
    private InstComiteFormationSearchRepository instComiteFormationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstComiteFormationMockMvc;

    private InstComiteFormation instComiteFormation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstComiteFormationResource instComiteFormationResource = new InstComiteFormationResource();
        ReflectionTestUtils.setField(instComiteFormationResource, "instComiteFormationRepository", instComiteFormationRepository);
        ReflectionTestUtils.setField(instComiteFormationResource, "instComiteFormationSearchRepository", instComiteFormationSearchRepository);
        this.restInstComiteFormationMockMvc = MockMvcBuilders.standaloneSetup(instComiteFormationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instComiteFormation = new InstComiteFormation();
        instComiteFormation.setComiteName(DEFAULT_COMITE_NAME);
        instComiteFormation.setComiteType(DEFAULT_COMITE_TYPE);
        instComiteFormation.setAddress(DEFAULT_ADDRESS);
        instComiteFormation.setTimeFrom(DEFAULT_TIME_FROM);
        instComiteFormation.setTimeTo(DEFAULT_TIME_TO);
        instComiteFormation.setFormationDate(DEFAULT_FORMATION_DATE);
    }

    @Test
    @Transactional
    public void createInstComiteFormation() throws Exception {
        int databaseSizeBeforeCreate = instComiteFormationRepository.findAll().size();

        // Create the InstComiteFormation

        restInstComiteFormationMockMvc.perform(post("/api/instComiteFormations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instComiteFormation)))
                .andExpect(status().isCreated());

        // Validate the InstComiteFormation in the database
        List<InstComiteFormation> instComiteFormations = instComiteFormationRepository.findAll();
        assertThat(instComiteFormations).hasSize(databaseSizeBeforeCreate + 1);
        InstComiteFormation testInstComiteFormation = instComiteFormations.get(instComiteFormations.size() - 1);
        assertThat(testInstComiteFormation.getComiteName()).isEqualTo(DEFAULT_COMITE_NAME);
        assertThat(testInstComiteFormation.getComiteType()).isEqualTo(DEFAULT_COMITE_TYPE);
        assertThat(testInstComiteFormation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testInstComiteFormation.getTimeFrom()).isEqualTo(DEFAULT_TIME_FROM);
        assertThat(testInstComiteFormation.getTimeTo()).isEqualTo(DEFAULT_TIME_TO);
        assertThat(testInstComiteFormation.getFormationDate()).isEqualTo(DEFAULT_FORMATION_DATE);
    }

    @Test
    @Transactional
    public void getAllInstComiteFormations() throws Exception {
        // Initialize the database
        instComiteFormationRepository.saveAndFlush(instComiteFormation);

        // Get all the instComiteFormations
        restInstComiteFormationMockMvc.perform(get("/api/instComiteFormations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instComiteFormation.getId().intValue())))
                .andExpect(jsonPath("$.[*].comiteName").value(hasItem(DEFAULT_COMITE_NAME.toString())))
                .andExpect(jsonPath("$.[*].comiteType").value(hasItem(DEFAULT_COMITE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM)))
                .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO)))
                .andExpect(jsonPath("$.[*].formationDate").value(hasItem(DEFAULT_FORMATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInstComiteFormation() throws Exception {
        // Initialize the database
        instComiteFormationRepository.saveAndFlush(instComiteFormation);

        // Get the instComiteFormation
        restInstComiteFormationMockMvc.perform(get("/api/instComiteFormations/{id}", instComiteFormation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instComiteFormation.getId().intValue()))
            .andExpect(jsonPath("$.comiteName").value(DEFAULT_COMITE_NAME.toString()))
            .andExpect(jsonPath("$.comiteType").value(DEFAULT_COMITE_TYPE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.timeFrom").value(DEFAULT_TIME_FROM))
            .andExpect(jsonPath("$.timeTo").value(DEFAULT_TIME_TO))
            .andExpect(jsonPath("$.formationDate").value(DEFAULT_FORMATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstComiteFormation() throws Exception {
        // Get the instComiteFormation
        restInstComiteFormationMockMvc.perform(get("/api/instComiteFormations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstComiteFormation() throws Exception {
        // Initialize the database
        instComiteFormationRepository.saveAndFlush(instComiteFormation);

		int databaseSizeBeforeUpdate = instComiteFormationRepository.findAll().size();

        // Update the instComiteFormation
        instComiteFormation.setComiteName(UPDATED_COMITE_NAME);
        instComiteFormation.setComiteType(UPDATED_COMITE_TYPE);
        instComiteFormation.setAddress(UPDATED_ADDRESS);
        instComiteFormation.setTimeFrom(UPDATED_TIME_FROM);
        instComiteFormation.setTimeTo(UPDATED_TIME_TO);
        instComiteFormation.setFormationDate(UPDATED_FORMATION_DATE);

        restInstComiteFormationMockMvc.perform(put("/api/instComiteFormations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instComiteFormation)))
                .andExpect(status().isOk());

        // Validate the InstComiteFormation in the database
        List<InstComiteFormation> instComiteFormations = instComiteFormationRepository.findAll();
        assertThat(instComiteFormations).hasSize(databaseSizeBeforeUpdate);
        InstComiteFormation testInstComiteFormation = instComiteFormations.get(instComiteFormations.size() - 1);
        assertThat(testInstComiteFormation.getComiteName()).isEqualTo(UPDATED_COMITE_NAME);
        assertThat(testInstComiteFormation.getComiteType()).isEqualTo(UPDATED_COMITE_TYPE);
        assertThat(testInstComiteFormation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testInstComiteFormation.getTimeFrom()).isEqualTo(UPDATED_TIME_FROM);
        assertThat(testInstComiteFormation.getTimeTo()).isEqualTo(UPDATED_TIME_TO);
        assertThat(testInstComiteFormation.getFormationDate()).isEqualTo(UPDATED_FORMATION_DATE);
    }

    @Test
    @Transactional
    public void deleteInstComiteFormation() throws Exception {
        // Initialize the database
        instComiteFormationRepository.saveAndFlush(instComiteFormation);

		int databaseSizeBeforeDelete = instComiteFormationRepository.findAll().size();

        // Get the instComiteFormation
        restInstComiteFormationMockMvc.perform(delete("/api/instComiteFormations/{id}", instComiteFormation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstComiteFormation> instComiteFormations = instComiteFormationRepository.findAll();
        assertThat(instComiteFormations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
