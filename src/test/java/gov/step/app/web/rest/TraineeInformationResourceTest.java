package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TraineeInformation;
import gov.step.app.repository.TraineeInformationRepository;
import gov.step.app.repository.search.TraineeInformationSearchRepository;

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
 * Test class for the TraineeInformationResource REST controller.
 *
 * @see TraineeInformationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TraineeInformationResourceTest {

    private static final String DEFAULT_TRAINEE_ID = "AAAAA";
    private static final String UPDATED_TRAINEE_ID = "BBBBB";
    private static final String DEFAULT_TRAINEE_NAME = "AAAAA";
    private static final String UPDATED_TRAINEE_NAME = "BBBBB";
    private static final String DEFAULT_SESSION = "AAAAA";
    private static final String UPDATED_SESSION = "BBBBB";
    private static final String DEFAULT_GENDER = "AAAAA";
    private static final String UPDATED_GENDER = "BBBBB";
    private static final String DEFAULT_ORGANIZATION = "AAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_DIVISION_ID = "AAAAA";
    private static final String UPDATED_DIVISION_ID= "BBBBB";
    private static final String DEFAULT_DISTRICT_ID = "AAAAA";
    private static final String UPDATED_DISTRICT_ID = "BBBBB";

    private static final Long DEFAULT_CONTACT_NUMBER = 1L;
    private static final Long UPDATED_CONTACT_NUMBER = 2L;

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
    private TraineeInformationRepository traineeInformationRepository;

    @Inject
    private TraineeInformationSearchRepository traineeInformationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTraineeInformationMockMvc;

    private TraineeInformation traineeInformation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraineeInformationResource traineeInformationResource = new TraineeInformationResource();
        ReflectionTestUtils.setField(traineeInformationResource, "traineeInformationRepository", traineeInformationRepository);
        ReflectionTestUtils.setField(traineeInformationResource, "traineeInformationSearchRepository", traineeInformationSearchRepository);
        this.restTraineeInformationMockMvc = MockMvcBuilders.standaloneSetup(traineeInformationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traineeInformation = new TraineeInformation();
        traineeInformation.setTraineeId(DEFAULT_TRAINEE_ID);
        traineeInformation.setTraineeName(DEFAULT_TRAINEE_NAME);
//        traineeInformation.setSession(DEFAULT_SESSION);
        traineeInformation.setGender(DEFAULT_GENDER);
        traineeInformation.setOrganization(DEFAULT_ORGANIZATION);
        traineeInformation.setAddress(DEFAULT_ADDRESS);
        // traineeInformation.setDivision(DEFAULT_DIVISION_ID);
        // traineeInformation.setDistrict(DEFAULT_DISTRICT_ID);
        traineeInformation.setContactNumber(DEFAULT_CONTACT_NUMBER);
        traineeInformation.setStatus(DEFAULT_STATUS);
        traineeInformation.setCreateDate(DEFAULT_CREATE_DATE);
        traineeInformation.setCreateBy(DEFAULT_CREATE_BY);
        traineeInformation.setUpdateDate(DEFAULT_UPDATE_DATE);
        traineeInformation.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createTraineeInformation() throws Exception {
        int databaseSizeBeforeCreate = traineeInformationRepository.findAll().size();

        // Create the TraineeInformation

        restTraineeInformationMockMvc.perform(post("/api/traineeInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traineeInformation)))
                .andExpect(status().isCreated());

        // Validate the TraineeInformation in the database
        List<TraineeInformation> traineeInformations = traineeInformationRepository.findAll();
        assertThat(traineeInformations).hasSize(databaseSizeBeforeCreate + 1);
        TraineeInformation testTraineeInformation = traineeInformations.get(traineeInformations.size() - 1);
        assertThat(testTraineeInformation.getTraineeId()).isEqualTo(DEFAULT_TRAINEE_ID);
        assertThat(testTraineeInformation.getTraineeName()).isEqualTo(DEFAULT_TRAINEE_NAME);
//        assertThat(testTraineeInformation.getSession()).isEqualTo(DEFAULT_SESSION);
        assertThat(testTraineeInformation.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testTraineeInformation.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testTraineeInformation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTraineeInformation.getDivision()).isEqualTo(DEFAULT_DIVISION_ID);
        assertThat(testTraineeInformation.getDistrict()).isEqualTo(DEFAULT_DISTRICT_ID);
        assertThat(testTraineeInformation.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testTraineeInformation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTraineeInformation.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTraineeInformation.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTraineeInformation.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testTraineeInformation.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkTraineeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeInformationRepository.findAll().size();
        // set the field null
        traineeInformation.setTraineeName(null);

        // Create the TraineeInformation, which fails.

        restTraineeInformationMockMvc.perform(post("/api/traineeInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traineeInformation)))
                .andExpect(status().isBadRequest());

        List<TraineeInformation> traineeInformations = traineeInformationRepository.findAll();
        assertThat(traineeInformations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeInformationRepository.findAll().size();
        // set the field null
        traineeInformation.setGender(null);

        // Create the TraineeInformation, which fails.

        restTraineeInformationMockMvc.perform(post("/api/traineeInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traineeInformation)))
                .andExpect(status().isBadRequest());

        List<TraineeInformation> traineeInformations = traineeInformationRepository.findAll();
        assertThat(traineeInformations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeInformationRepository.findAll().size();
        // set the field null
        traineeInformation.setAddress(null);

        // Create the TraineeInformation, which fails.

        restTraineeInformationMockMvc.perform(post("/api/traineeInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traineeInformation)))
                .andExpect(status().isBadRequest());

        List<TraineeInformation> traineeInformations = traineeInformationRepository.findAll();
        assertThat(traineeInformations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTraineeInformations() throws Exception {
        // Initialize the database
        traineeInformationRepository.saveAndFlush(traineeInformation);

        // Get all the traineeInformations
        restTraineeInformationMockMvc.perform(get("/api/traineeInformations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traineeInformation.getId().intValue())))
                .andExpect(jsonPath("$.[*].traineeId").value(hasItem(DEFAULT_TRAINEE_ID.toString())))
                .andExpect(jsonPath("$.[*].traineeName").value(hasItem(DEFAULT_TRAINEE_NAME.toString())))
                .andExpect(jsonPath("$.[*].session").value(hasItem(DEFAULT_SESSION.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].division").value(hasItem(DEFAULT_DIVISION_ID.toString())))
                .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT_ID.toString())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getTraineeInformation() throws Exception {
        // Initialize the database
        traineeInformationRepository.saveAndFlush(traineeInformation);

        // Get the traineeInformation
        restTraineeInformationMockMvc.perform(get("/api/traineeInformations/{id}", traineeInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(traineeInformation.getId().intValue()))
            .andExpect(jsonPath("$.traineeId").value(DEFAULT_TRAINEE_ID.toString()))
            .andExpect(jsonPath("$.traineeName").value(DEFAULT_TRAINEE_NAME.toString()))
            .andExpect(jsonPath("$.session").value(DEFAULT_SESSION.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.division").value(DEFAULT_DIVISION_ID.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT_ID.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTraineeInformation() throws Exception {
        // Get the traineeInformation
        restTraineeInformationMockMvc.perform(get("/api/traineeInformations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraineeInformation() throws Exception {
        // Initialize the database
        traineeInformationRepository.saveAndFlush(traineeInformation);

		int databaseSizeBeforeUpdate = traineeInformationRepository.findAll().size();

        // Update the traineeInformation
        traineeInformation.setTraineeId(UPDATED_TRAINEE_ID);
        traineeInformation.setTraineeName(UPDATED_TRAINEE_NAME);
//        traineeInformation.setSession(UPDATED_SESSION);
        traineeInformation.setGender(UPDATED_GENDER);
        traineeInformation.setOrganization(UPDATED_ORGANIZATION);
        traineeInformation.setAddress(UPDATED_ADDRESS);
        // traineeInformation.setDivision(UPDATED_DIVISION_ID);
        // traineeInformation.setDistrict(UPDATED_DISTRICT_ID);
        traineeInformation.setContactNumber(UPDATED_CONTACT_NUMBER);
        traineeInformation.setStatus(UPDATED_STATUS);
        traineeInformation.setCreateDate(UPDATED_CREATE_DATE);
        traineeInformation.setCreateBy(UPDATED_CREATE_BY);
        traineeInformation.setUpdateDate(UPDATED_UPDATE_DATE);
        traineeInformation.setUpdateBy(UPDATED_UPDATE_BY);

        restTraineeInformationMockMvc.perform(put("/api/traineeInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traineeInformation)))
                .andExpect(status().isOk());

        // Validate the TraineeInformation in the database
        List<TraineeInformation> traineeInformations = traineeInformationRepository.findAll();
        assertThat(traineeInformations).hasSize(databaseSizeBeforeUpdate);
        TraineeInformation testTraineeInformation = traineeInformations.get(traineeInformations.size() - 1);
        assertThat(testTraineeInformation.getTraineeId()).isEqualTo(UPDATED_TRAINEE_ID);
        assertThat(testTraineeInformation.getTraineeName()).isEqualTo(UPDATED_TRAINEE_NAME);
//        assertThat(testTraineeInformation.getSession()).isEqualTo(UPDATED_SESSION);
        assertThat(testTraineeInformation.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testTraineeInformation.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testTraineeInformation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTraineeInformation.getDivision()).isEqualTo(UPDATED_DIVISION_ID);
        assertThat(testTraineeInformation.getDistrict()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testTraineeInformation.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testTraineeInformation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTraineeInformation.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTraineeInformation.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTraineeInformation.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testTraineeInformation.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteTraineeInformation() throws Exception {
        // Initialize the database
        traineeInformationRepository.saveAndFlush(traineeInformation);

		int databaseSizeBeforeDelete = traineeInformationRepository.findAll().size();

        // Get the traineeInformation
        restTraineeInformationMockMvc.perform(delete("/api/traineeInformations/{id}", traineeInformation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TraineeInformation> traineeInformations = traineeInformationRepository.findAll();
        assertThat(traineeInformations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
