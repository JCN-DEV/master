package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TrainerInformation;
import gov.step.app.repository.TrainerInformationRepository;
import gov.step.app.repository.search.TrainerInformationSearchRepository;

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
 * Test class for the TrainerInformationResource REST controller.
 *
 * @see TrainerInformationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TrainerInformationResourceTest {

    private static final String DEFAULT_TRAINER_ID = "AAAAA";
    private static final String UPDATED_TRAINER_ID = "BBBBB";
    private static final String DEFAULT_TRAINER_NAME = "AAAAA";
    private static final String UPDATED_TRAINER_NAME = "BBBBB";
    private static final String DEFAULT_TRAINER_TYPE = "AAAAA";
    private static final String UPDATED_TRAINER_TYPE = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_DEPARTMENT = "AAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBB";
    private static final String DEFAULT_ORGANIZATION = "AAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBB";

    private static final Long DEFAULT_MOBILE_NUMBER = 1L;
    private static final Long UPDATED_MOBILE_NUMBER = 2L;
    private static final String DEFAULT_EMAIL_ID = "AAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBB";
    private static final String DEFAULT_SPECIAL_SKILLS = "AAAAA";
    private static final String UPDATED_SPECIAL_SKILLS = "BBBBB";

    private static final Boolean DEFAULT_TRAINING_ASSIGN_STATUS = false;
    private static final Boolean UPDATED_TRAINING_ASSIGN_STATUS = true;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private TrainerInformationRepository trainerInformationRepository;

    @Inject
    private TrainerInformationSearchRepository trainerInformationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrainerInformationMockMvc;

    private TrainerInformation trainerInformation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrainerInformationResource trainerInformationResource = new TrainerInformationResource();
        ReflectionTestUtils.setField(trainerInformationResource, "trainerInformationRepository", trainerInformationRepository);
        ReflectionTestUtils.setField(trainerInformationResource, "trainerInformationSearchRepository", trainerInformationSearchRepository);
        this.restTrainerInformationMockMvc = MockMvcBuilders.standaloneSetup(trainerInformationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trainerInformation = new TrainerInformation();
        trainerInformation.setTrainerId(DEFAULT_TRAINER_ID);
        trainerInformation.setTrainerName(DEFAULT_TRAINER_NAME);
        trainerInformation.setTrainerType(DEFAULT_TRAINER_TYPE);
        trainerInformation.setAddress(DEFAULT_ADDRESS);
        trainerInformation.setDesignation(DEFAULT_DESIGNATION);
        trainerInformation.setDepartment(DEFAULT_DEPARTMENT);
        trainerInformation.setOrganization(DEFAULT_ORGANIZATION);
        trainerInformation.setMobileNumber(DEFAULT_MOBILE_NUMBER);
        trainerInformation.setEmailId(DEFAULT_EMAIL_ID);
        trainerInformation.setSpecialSkills(DEFAULT_SPECIAL_SKILLS);
        trainerInformation.setTrainingAssignStatus(DEFAULT_TRAINING_ASSIGN_STATUS);
        trainerInformation.setStatus(DEFAULT_STATUS);
        trainerInformation.setCreateDate(DEFAULT_CREATE_DATE);
        trainerInformation.setCreateBy(DEFAULT_CREATE_BY);
        trainerInformation.setUpdateDate(DEFAULT_UPDATE_DATE);
        trainerInformation.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createTrainerInformation() throws Exception {
        int databaseSizeBeforeCreate = trainerInformationRepository.findAll().size();

        // Create the TrainerInformation

        restTrainerInformationMockMvc.perform(post("/api/trainerInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainerInformation)))
                .andExpect(status().isCreated());

        // Validate the TrainerInformation in the database
        List<TrainerInformation> trainerInformations = trainerInformationRepository.findAll();
        assertThat(trainerInformations).hasSize(databaseSizeBeforeCreate + 1);
        TrainerInformation testTrainerInformation = trainerInformations.get(trainerInformations.size() - 1);
        assertThat(testTrainerInformation.getTrainerId()).isEqualTo(DEFAULT_TRAINER_ID);
        assertThat(testTrainerInformation.getTrainerName()).isEqualTo(DEFAULT_TRAINER_NAME);
        assertThat(testTrainerInformation.getTrainerType()).isEqualTo(DEFAULT_TRAINER_TYPE);
        assertThat(testTrainerInformation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTrainerInformation.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testTrainerInformation.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testTrainerInformation.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testTrainerInformation.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testTrainerInformation.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testTrainerInformation.getSpecialSkills()).isEqualTo(DEFAULT_SPECIAL_SKILLS);
        assertThat(testTrainerInformation.getTrainingAssignStatus()).isEqualTo(DEFAULT_TRAINING_ASSIGN_STATUS);
        assertThat(testTrainerInformation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrainerInformation.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTrainerInformation.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTrainerInformation.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testTrainerInformation.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkTrainerTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainerInformationRepository.findAll().size();
        // set the field null
        trainerInformation.setTrainerType(null);

        // Create the TrainerInformation, which fails.

        restTrainerInformationMockMvc.perform(post("/api/trainerInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainerInformation)))
                .andExpect(status().isBadRequest());

        List<TrainerInformation> trainerInformations = trainerInformationRepository.findAll();
        assertThat(trainerInformations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrainerInformations() throws Exception {
        // Initialize the database
        trainerInformationRepository.saveAndFlush(trainerInformation);

        // Get all the trainerInformations
        restTrainerInformationMockMvc.perform(get("/api/trainerInformations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trainerInformation.getId().intValue())))
                .andExpect(jsonPath("$.[*].trainerId").value(hasItem(DEFAULT_TRAINER_ID.toString())))
                .andExpect(jsonPath("$.[*].trainerName").value(hasItem(DEFAULT_TRAINER_NAME.toString())))
                .andExpect(jsonPath("$.[*].trainerType").value(hasItem(DEFAULT_TRAINER_TYPE.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
                .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
                .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER.intValue())))
                .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID.toString())))
                .andExpect(jsonPath("$.[*].specialSkills").value(hasItem(DEFAULT_SPECIAL_SKILLS.toString())))
                .andExpect(jsonPath("$.[*].trainingAssignStatus").value(hasItem(DEFAULT_TRAINING_ASSIGN_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getTrainerInformation() throws Exception {
        // Initialize the database
        trainerInformationRepository.saveAndFlush(trainerInformation);

        // Get the trainerInformation
        restTrainerInformationMockMvc.perform(get("/api/trainerInformations/{id}", trainerInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trainerInformation.getId().intValue()))
            .andExpect(jsonPath("$.trainerId").value(DEFAULT_TRAINER_ID.toString()))
            .andExpect(jsonPath("$.trainerName").value(DEFAULT_TRAINER_NAME.toString()))
            .andExpect(jsonPath("$.trainerType").value(DEFAULT_TRAINER_TYPE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER.intValue()))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID.toString()))
            .andExpect(jsonPath("$.specialSkills").value(DEFAULT_SPECIAL_SKILLS.toString()))
            .andExpect(jsonPath("$.trainingAssignStatus").value(DEFAULT_TRAINING_ASSIGN_STATUS.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainerInformation() throws Exception {
        // Get the trainerInformation
        restTrainerInformationMockMvc.perform(get("/api/trainerInformations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainerInformation() throws Exception {
        // Initialize the database
        trainerInformationRepository.saveAndFlush(trainerInformation);

		int databaseSizeBeforeUpdate = trainerInformationRepository.findAll().size();

        // Update the trainerInformation
        trainerInformation.setTrainerId(UPDATED_TRAINER_ID);
        trainerInformation.setTrainerName(UPDATED_TRAINER_NAME);
        trainerInformation.setTrainerType(UPDATED_TRAINER_TYPE);
        trainerInformation.setAddress(UPDATED_ADDRESS);
        trainerInformation.setDesignation(UPDATED_DESIGNATION);
        trainerInformation.setDepartment(UPDATED_DEPARTMENT);
        trainerInformation.setOrganization(UPDATED_ORGANIZATION);
        trainerInformation.setMobileNumber(UPDATED_MOBILE_NUMBER);
        trainerInformation.setEmailId(UPDATED_EMAIL_ID);
        trainerInformation.setSpecialSkills(UPDATED_SPECIAL_SKILLS);
        trainerInformation.setTrainingAssignStatus(UPDATED_TRAINING_ASSIGN_STATUS);
        trainerInformation.setStatus(UPDATED_STATUS);
        trainerInformation.setCreateDate(UPDATED_CREATE_DATE);
        trainerInformation.setCreateBy(UPDATED_CREATE_BY);
        trainerInformation.setUpdateDate(UPDATED_UPDATE_DATE);
        trainerInformation.setUpdateBy(UPDATED_UPDATE_BY);

        restTrainerInformationMockMvc.perform(put("/api/trainerInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainerInformation)))
                .andExpect(status().isOk());

        // Validate the TrainerInformation in the database
        List<TrainerInformation> trainerInformations = trainerInformationRepository.findAll();
        assertThat(trainerInformations).hasSize(databaseSizeBeforeUpdate);
        TrainerInformation testTrainerInformation = trainerInformations.get(trainerInformations.size() - 1);
        assertThat(testTrainerInformation.getTrainerId()).isEqualTo(UPDATED_TRAINER_ID);
        assertThat(testTrainerInformation.getTrainerName()).isEqualTo(UPDATED_TRAINER_NAME);
        assertThat(testTrainerInformation.getTrainerType()).isEqualTo(UPDATED_TRAINER_TYPE);
        assertThat(testTrainerInformation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTrainerInformation.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testTrainerInformation.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testTrainerInformation.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testTrainerInformation.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testTrainerInformation.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testTrainerInformation.getSpecialSkills()).isEqualTo(UPDATED_SPECIAL_SKILLS);
        assertThat(testTrainerInformation.getTrainingAssignStatus()).isEqualTo(UPDATED_TRAINING_ASSIGN_STATUS);
        assertThat(testTrainerInformation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainerInformation.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTrainerInformation.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTrainerInformation.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testTrainerInformation.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteTrainerInformation() throws Exception {
        // Initialize the database
        trainerInformationRepository.saveAndFlush(trainerInformation);

		int databaseSizeBeforeDelete = trainerInformationRepository.findAll().size();

        // Get the trainerInformation
        restTrainerInformationMockMvc.perform(delete("/api/trainerInformations/{id}", trainerInformation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainerInformation> trainerInformations = trainerInformationRepository.findAll();
        assertThat(trainerInformations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
