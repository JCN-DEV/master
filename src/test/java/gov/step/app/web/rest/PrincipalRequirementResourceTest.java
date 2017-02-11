package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PrincipalRequirement;
import gov.step.app.repository.PrincipalRequirementRepository;
import gov.step.app.repository.search.PrincipalRequirementSearchRepository;

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
 * Test class for the PrincipalRequirementResource REST controller.
 *
 * @see PrincipalRequirementResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PrincipalRequirementResourceTest {


    private static final LocalDate DEFAULT_FIRST_JOINING_DATE_AS_LECTURER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_JOINING_DATE_AS_LECTURER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIRST_MPOENLISTING_DATE_AS_LECTURER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_MPOENLISTING_DATE_AS_LECTURER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIRST_JOINING_DATE_AS_ASST_PROF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_JOINING_DATE_AS_ASST_PROF = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIRST_MPOENLISTING_DATE_ASST_PROF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_MPOENLISTING_DATE_ASST_PROF = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIRST_JOINING_DATE_AS_VP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_JOINING_DATE_AS_VP = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIRST_MPOENLISTING_DATE_AS_VP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_MPOENLISTING_DATE_AS_VP = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PrincipalRequirementRepository principalRequirementRepository;

    @Inject
    private PrincipalRequirementSearchRepository principalRequirementSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPrincipalRequirementMockMvc;

    private PrincipalRequirement principalRequirement;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrincipalRequirementResource principalRequirementResource = new PrincipalRequirementResource();
        ReflectionTestUtils.setField(principalRequirementResource, "principalRequirementRepository", principalRequirementRepository);
        ReflectionTestUtils.setField(principalRequirementResource, "principalRequirementSearchRepository", principalRequirementSearchRepository);
        this.restPrincipalRequirementMockMvc = MockMvcBuilders.standaloneSetup(principalRequirementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        principalRequirement = new PrincipalRequirement();
        principalRequirement.setFirstJoiningDateAsLecturer(DEFAULT_FIRST_JOINING_DATE_AS_LECTURER);
        principalRequirement.setFirstMPOEnlistingDateAsLecturer(DEFAULT_FIRST_MPOENLISTING_DATE_AS_LECTURER);
        principalRequirement.setFirstJoiningDateAsAsstProf(DEFAULT_FIRST_JOINING_DATE_AS_ASST_PROF);
        principalRequirement.setFirstMPOEnlistingDateAsstProf(DEFAULT_FIRST_MPOENLISTING_DATE_ASST_PROF);
        principalRequirement.setFirstJoiningDateAsVP(DEFAULT_FIRST_JOINING_DATE_AS_VP);
        principalRequirement.setFirstMPOEnlistingDateAsVP(DEFAULT_FIRST_MPOENLISTING_DATE_AS_VP);
    }

    @Test
    @Transactional
    public void createPrincipalRequirement() throws Exception {
        int databaseSizeBeforeCreate = principalRequirementRepository.findAll().size();

        // Create the PrincipalRequirement

        restPrincipalRequirementMockMvc.perform(post("/api/principalRequirements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(principalRequirement)))
                .andExpect(status().isCreated());

        // Validate the PrincipalRequirement in the database
        List<PrincipalRequirement> principalRequirements = principalRequirementRepository.findAll();
        assertThat(principalRequirements).hasSize(databaseSizeBeforeCreate + 1);
        PrincipalRequirement testPrincipalRequirement = principalRequirements.get(principalRequirements.size() - 1);
        assertThat(testPrincipalRequirement.getFirstJoiningDateAsLecturer()).isEqualTo(DEFAULT_FIRST_JOINING_DATE_AS_LECTURER);
        assertThat(testPrincipalRequirement.getFirstMPOEnlistingDateAsLecturer()).isEqualTo(DEFAULT_FIRST_MPOENLISTING_DATE_AS_LECTURER);
        assertThat(testPrincipalRequirement.getFirstJoiningDateAsAsstProf()).isEqualTo(DEFAULT_FIRST_JOINING_DATE_AS_ASST_PROF);
        assertThat(testPrincipalRequirement.getFirstMPOEnlistingDateAsstProf()).isEqualTo(DEFAULT_FIRST_MPOENLISTING_DATE_ASST_PROF);
        assertThat(testPrincipalRequirement.getFirstJoiningDateAsVP()).isEqualTo(DEFAULT_FIRST_JOINING_DATE_AS_VP);
        assertThat(testPrincipalRequirement.getFirstMPOEnlistingDateAsVP()).isEqualTo(DEFAULT_FIRST_MPOENLISTING_DATE_AS_VP);
    }

    @Test
    @Transactional
    public void getAllPrincipalRequirements() throws Exception {
        // Initialize the database
        principalRequirementRepository.saveAndFlush(principalRequirement);

        // Get all the principalRequirements
        restPrincipalRequirementMockMvc.perform(get("/api/principalRequirements"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(principalRequirement.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstJoiningDateAsLecturer").value(hasItem(DEFAULT_FIRST_JOINING_DATE_AS_LECTURER.toString())))
                .andExpect(jsonPath("$.[*].firstMPOEnlistingDateAsLecturer").value(hasItem(DEFAULT_FIRST_MPOENLISTING_DATE_AS_LECTURER.toString())))
                .andExpect(jsonPath("$.[*].firstJoiningDateAsAsstProf").value(hasItem(DEFAULT_FIRST_JOINING_DATE_AS_ASST_PROF.toString())))
                .andExpect(jsonPath("$.[*].firstMPOEnlistingDateAsstProf").value(hasItem(DEFAULT_FIRST_MPOENLISTING_DATE_ASST_PROF.toString())))
                .andExpect(jsonPath("$.[*].firstJoiningDateAsVP").value(hasItem(DEFAULT_FIRST_JOINING_DATE_AS_VP.toString())))
                .andExpect(jsonPath("$.[*].firstMPOEnlistingDateAsVP").value(hasItem(DEFAULT_FIRST_MPOENLISTING_DATE_AS_VP.toString())));
    }

    @Test
    @Transactional
    public void getPrincipalRequirement() throws Exception {
        // Initialize the database
        principalRequirementRepository.saveAndFlush(principalRequirement);

        // Get the principalRequirement
        restPrincipalRequirementMockMvc.perform(get("/api/principalRequirements/{id}", principalRequirement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(principalRequirement.getId().intValue()))
            .andExpect(jsonPath("$.firstJoiningDateAsLecturer").value(DEFAULT_FIRST_JOINING_DATE_AS_LECTURER.toString()))
            .andExpect(jsonPath("$.firstMPOEnlistingDateAsLecturer").value(DEFAULT_FIRST_MPOENLISTING_DATE_AS_LECTURER.toString()))
            .andExpect(jsonPath("$.firstJoiningDateAsAsstProf").value(DEFAULT_FIRST_JOINING_DATE_AS_ASST_PROF.toString()))
            .andExpect(jsonPath("$.firstMPOEnlistingDateAsstProf").value(DEFAULT_FIRST_MPOENLISTING_DATE_ASST_PROF.toString()))
            .andExpect(jsonPath("$.firstJoiningDateAsVP").value(DEFAULT_FIRST_JOINING_DATE_AS_VP.toString()))
            .andExpect(jsonPath("$.firstMPOEnlistingDateAsVP").value(DEFAULT_FIRST_MPOENLISTING_DATE_AS_VP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrincipalRequirement() throws Exception {
        // Get the principalRequirement
        restPrincipalRequirementMockMvc.perform(get("/api/principalRequirements/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrincipalRequirement() throws Exception {
        // Initialize the database
        principalRequirementRepository.saveAndFlush(principalRequirement);

		int databaseSizeBeforeUpdate = principalRequirementRepository.findAll().size();

        // Update the principalRequirement
        principalRequirement.setFirstJoiningDateAsLecturer(UPDATED_FIRST_JOINING_DATE_AS_LECTURER);
        principalRequirement.setFirstMPOEnlistingDateAsLecturer(UPDATED_FIRST_MPOENLISTING_DATE_AS_LECTURER);
        principalRequirement.setFirstJoiningDateAsAsstProf(UPDATED_FIRST_JOINING_DATE_AS_ASST_PROF);
        principalRequirement.setFirstMPOEnlistingDateAsstProf(UPDATED_FIRST_MPOENLISTING_DATE_ASST_PROF);
        principalRequirement.setFirstJoiningDateAsVP(UPDATED_FIRST_JOINING_DATE_AS_VP);
        principalRequirement.setFirstMPOEnlistingDateAsVP(UPDATED_FIRST_MPOENLISTING_DATE_AS_VP);

        restPrincipalRequirementMockMvc.perform(put("/api/principalRequirements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(principalRequirement)))
                .andExpect(status().isOk());

        // Validate the PrincipalRequirement in the database
        List<PrincipalRequirement> principalRequirements = principalRequirementRepository.findAll();
        assertThat(principalRequirements).hasSize(databaseSizeBeforeUpdate);
        PrincipalRequirement testPrincipalRequirement = principalRequirements.get(principalRequirements.size() - 1);
        assertThat(testPrincipalRequirement.getFirstJoiningDateAsLecturer()).isEqualTo(UPDATED_FIRST_JOINING_DATE_AS_LECTURER);
        assertThat(testPrincipalRequirement.getFirstMPOEnlistingDateAsLecturer()).isEqualTo(UPDATED_FIRST_MPOENLISTING_DATE_AS_LECTURER);
        assertThat(testPrincipalRequirement.getFirstJoiningDateAsAsstProf()).isEqualTo(UPDATED_FIRST_JOINING_DATE_AS_ASST_PROF);
        assertThat(testPrincipalRequirement.getFirstMPOEnlistingDateAsstProf()).isEqualTo(UPDATED_FIRST_MPOENLISTING_DATE_ASST_PROF);
        assertThat(testPrincipalRequirement.getFirstJoiningDateAsVP()).isEqualTo(UPDATED_FIRST_JOINING_DATE_AS_VP);
        assertThat(testPrincipalRequirement.getFirstMPOEnlistingDateAsVP()).isEqualTo(UPDATED_FIRST_MPOENLISTING_DATE_AS_VP);
    }

    @Test
    @Transactional
    public void deletePrincipalRequirement() throws Exception {
        // Initialize the database
        principalRequirementRepository.saveAndFlush(principalRequirement);

		int databaseSizeBeforeDelete = principalRequirementRepository.findAll().size();

        // Get the principalRequirement
        restPrincipalRequirementMockMvc.perform(delete("/api/principalRequirements/{id}", principalRequirement.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PrincipalRequirement> principalRequirements = principalRequirementRepository.findAll();
        assertThat(principalRequirements).hasSize(databaseSizeBeforeDelete - 1);
    }
}
