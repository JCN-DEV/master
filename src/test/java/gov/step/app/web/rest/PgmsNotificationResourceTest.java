package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsNotification;
import gov.step.app.repository.PgmsNotificationRepository;
import gov.step.app.repository.search.PgmsNotificationSearchRepository;

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
 * Test class for the PgmsNotificationResource REST controller.
 *
 * @see PgmsNotificationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsNotificationResourceTest {


    private static final Long DEFAULT_EMP_ID = 1L;
    private static final Long UPDATED_EMP_ID = 2L;
    private static final String DEFAULT_EMP_NAME = "AAAAA";
    private static final String UPDATED_EMP_NAME = "BBBBB";
    private static final String DEFAULT_EMP_DESIGNATION = "AAAAA";
    private static final String UPDATED_EMP_DESIGNATION = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RETIREMNNT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETIREMNNT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_WORK_DURATION = 1L;
    private static final Long UPDATED_WORK_DURATION = 2L;

    private static final Long DEFAULT_CONTACT_NUMBER = 1L;
    private static final Long UPDATED_CONTACT_NUMBER = 2L;
    private static final String DEFAULT_MESSAGE = "AAAAA";
    private static final String UPDATED_MESSAGE = "BBBBB";

    private static final Boolean DEFAULT_NOTIFICATION_STATUS = false;
    private static final Boolean UPDATED_NOTIFICATION_STATUS = true;

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private PgmsNotificationRepository pgmsNotificationRepository;

    @Inject
    private PgmsNotificationSearchRepository pgmsNotificationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsNotificationMockMvc;

    private PgmsNotification pgmsNotification;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsNotificationResource pgmsNotificationResource = new PgmsNotificationResource();
        ReflectionTestUtils.setField(pgmsNotificationResource, "pgmsNotificationRepository", pgmsNotificationRepository);
        ReflectionTestUtils.setField(pgmsNotificationResource, "pgmsNotificationSearchRepository", pgmsNotificationSearchRepository);
        this.restPgmsNotificationMockMvc = MockMvcBuilders.standaloneSetup(pgmsNotificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsNotification = new PgmsNotification();
        pgmsNotification.setEmpId(DEFAULT_EMP_ID);
        pgmsNotification.setEmpName(DEFAULT_EMP_NAME);
        pgmsNotification.setEmpDesignation(DEFAULT_EMP_DESIGNATION);
        pgmsNotification.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
        pgmsNotification.setJoiningDate(DEFAULT_JOINING_DATE);
        pgmsNotification.setRetiremnntDate(DEFAULT_RETIREMNNT_DATE);
        pgmsNotification.setWorkDuration(DEFAULT_WORK_DURATION);
        pgmsNotification.setContactNumber(DEFAULT_CONTACT_NUMBER);
        pgmsNotification.setMessage(DEFAULT_MESSAGE);
        pgmsNotification.setNotificationStatus(DEFAULT_NOTIFICATION_STATUS);
        pgmsNotification.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pgmsNotification.setCreateDate(DEFAULT_CREATE_DATE);
        pgmsNotification.setCreateBy(DEFAULT_CREATE_BY);
        pgmsNotification.setUpdateDate(DEFAULT_UPDATE_DATE);
        pgmsNotification.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPgmsNotification() throws Exception {
        int databaseSizeBeforeCreate = pgmsNotificationRepository.findAll().size();

        // Create the PgmsNotification

        restPgmsNotificationMockMvc.perform(post("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isCreated());

        // Validate the PgmsNotification in the database
        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeCreate + 1);
        PgmsNotification testPgmsNotification = pgmsNotifications.get(pgmsNotifications.size() - 1);
        assertThat(testPgmsNotification.getEmpId()).isEqualTo(DEFAULT_EMP_ID);
        assertThat(testPgmsNotification.getEmpName()).isEqualTo(DEFAULT_EMP_NAME);
        assertThat(testPgmsNotification.getEmpDesignation()).isEqualTo(DEFAULT_EMP_DESIGNATION);
        assertThat(testPgmsNotification.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPgmsNotification.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testPgmsNotification.getRetiremnntDate()).isEqualTo(DEFAULT_RETIREMNNT_DATE);
        assertThat(testPgmsNotification.getWorkDuration()).isEqualTo(DEFAULT_WORK_DURATION);
        assertThat(testPgmsNotification.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testPgmsNotification.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testPgmsNotification.getNotificationStatus()).isEqualTo(DEFAULT_NOTIFICATION_STATUS);
        assertThat(testPgmsNotification.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPgmsNotification.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPgmsNotification.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPgmsNotification.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPgmsNotification.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkEmpIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsNotificationRepository.findAll().size();
        // set the field null
        pgmsNotification.setEmpId(null);

        // Create the PgmsNotification, which fails.

        restPgmsNotificationMockMvc.perform(post("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isBadRequest());

        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsNotificationRepository.findAll().size();
        // set the field null
        pgmsNotification.setEmpName(null);

        // Create the PgmsNotification, which fails.

        restPgmsNotificationMockMvc.perform(post("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isBadRequest());

        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsNotificationRepository.findAll().size();
        // set the field null
        pgmsNotification.setEmpDesignation(null);

        // Create the PgmsNotification, which fails.

        restPgmsNotificationMockMvc.perform(post("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isBadRequest());

        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsNotificationRepository.findAll().size();
        // set the field null
        pgmsNotification.setDateOfBirth(null);

        // Create the PgmsNotification, which fails.

        restPgmsNotificationMockMvc.perform(post("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isBadRequest());

        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJoiningDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsNotificationRepository.findAll().size();
        // set the field null
        pgmsNotification.setJoiningDate(null);

        // Create the PgmsNotification, which fails.

        restPgmsNotificationMockMvc.perform(post("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isBadRequest());

        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetiremnntDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsNotificationRepository.findAll().size();
        // set the field null
        pgmsNotification.setRetiremnntDate(null);

        // Create the PgmsNotification, which fails.

        restPgmsNotificationMockMvc.perform(post("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isBadRequest());

        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWorkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsNotificationRepository.findAll().size();
        // set the field null
        pgmsNotification.setWorkDuration(null);

        // Create the PgmsNotification, which fails.

        restPgmsNotificationMockMvc.perform(post("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isBadRequest());

        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsNotificationRepository.findAll().size();
        // set the field null
        pgmsNotification.setContactNumber(null);

        // Create the PgmsNotification, which fails.

        restPgmsNotificationMockMvc.perform(post("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isBadRequest());

        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsNotificationRepository.findAll().size();
        // set the field null
        pgmsNotification.setMessage(null);

        // Create the PgmsNotification, which fails.

        restPgmsNotificationMockMvc.perform(post("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isBadRequest());

        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsNotifications() throws Exception {
        // Initialize the database
        pgmsNotificationRepository.saveAndFlush(pgmsNotification);

        // Get all the pgmsNotifications
        restPgmsNotificationMockMvc.perform(get("/api/pgmsNotifications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsNotification.getId().intValue())))
                .andExpect(jsonPath("$.[*].empId").value(hasItem(DEFAULT_EMP_ID.intValue())))
                .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME.toString())))
                .andExpect(jsonPath("$.[*].empDesignation").value(hasItem(DEFAULT_EMP_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
                .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
                .andExpect(jsonPath("$.[*].retiremnntDate").value(hasItem(DEFAULT_RETIREMNNT_DATE.toString())))
                .andExpect(jsonPath("$.[*].workDuration").value(hasItem(DEFAULT_WORK_DURATION.intValue())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.intValue())))
                .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
                .andExpect(jsonPath("$.[*].notificationStatus").value(hasItem(DEFAULT_NOTIFICATION_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPgmsNotification() throws Exception {
        // Initialize the database
        pgmsNotificationRepository.saveAndFlush(pgmsNotification);

        // Get the pgmsNotification
        restPgmsNotificationMockMvc.perform(get("/api/pgmsNotifications/{id}", pgmsNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsNotification.getId().intValue()))
            .andExpect(jsonPath("$.empId").value(DEFAULT_EMP_ID.intValue()))
            .andExpect(jsonPath("$.empName").value(DEFAULT_EMP_NAME.toString()))
            .andExpect(jsonPath("$.empDesignation").value(DEFAULT_EMP_DESIGNATION.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.retiremnntDate").value(DEFAULT_RETIREMNNT_DATE.toString()))
            .andExpect(jsonPath("$.workDuration").value(DEFAULT_WORK_DURATION.intValue()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.notificationStatus").value(DEFAULT_NOTIFICATION_STATUS.booleanValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsNotification() throws Exception {
        // Get the pgmsNotification
        restPgmsNotificationMockMvc.perform(get("/api/pgmsNotifications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsNotification() throws Exception {
        // Initialize the database
        pgmsNotificationRepository.saveAndFlush(pgmsNotification);

		int databaseSizeBeforeUpdate = pgmsNotificationRepository.findAll().size();

        // Update the pgmsNotification
        pgmsNotification.setEmpId(UPDATED_EMP_ID);
        pgmsNotification.setEmpName(UPDATED_EMP_NAME);
        pgmsNotification.setEmpDesignation(UPDATED_EMP_DESIGNATION);
        pgmsNotification.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        pgmsNotification.setJoiningDate(UPDATED_JOINING_DATE);
        pgmsNotification.setRetiremnntDate(UPDATED_RETIREMNNT_DATE);
        pgmsNotification.setWorkDuration(UPDATED_WORK_DURATION);
        pgmsNotification.setContactNumber(UPDATED_CONTACT_NUMBER);
        pgmsNotification.setMessage(UPDATED_MESSAGE);
        pgmsNotification.setNotificationStatus(UPDATED_NOTIFICATION_STATUS);
        pgmsNotification.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pgmsNotification.setCreateDate(UPDATED_CREATE_DATE);
        pgmsNotification.setCreateBy(UPDATED_CREATE_BY);
        pgmsNotification.setUpdateDate(UPDATED_UPDATE_DATE);
        pgmsNotification.setUpdateBy(UPDATED_UPDATE_BY);

        restPgmsNotificationMockMvc.perform(put("/api/pgmsNotifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsNotification)))
                .andExpect(status().isOk());

        // Validate the PgmsNotification in the database
        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeUpdate);
        PgmsNotification testPgmsNotification = pgmsNotifications.get(pgmsNotifications.size() - 1);
        assertThat(testPgmsNotification.getEmpId()).isEqualTo(UPDATED_EMP_ID);
        assertThat(testPgmsNotification.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testPgmsNotification.getEmpDesignation()).isEqualTo(UPDATED_EMP_DESIGNATION);
        assertThat(testPgmsNotification.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPgmsNotification.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testPgmsNotification.getRetiremnntDate()).isEqualTo(UPDATED_RETIREMNNT_DATE);
        assertThat(testPgmsNotification.getWorkDuration()).isEqualTo(UPDATED_WORK_DURATION);
        assertThat(testPgmsNotification.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testPgmsNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testPgmsNotification.getNotificationStatus()).isEqualTo(UPDATED_NOTIFICATION_STATUS);
        assertThat(testPgmsNotification.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPgmsNotification.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPgmsNotification.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPgmsNotification.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPgmsNotification.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePgmsNotification() throws Exception {
        // Initialize the database
        pgmsNotificationRepository.saveAndFlush(pgmsNotification);

		int databaseSizeBeforeDelete = pgmsNotificationRepository.findAll().size();

        // Get the pgmsNotification
        restPgmsNotificationMockMvc.perform(delete("/api/pgmsNotifications/{id}", pgmsNotification.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsNotification> pgmsNotifications = pgmsNotificationRepository.findAll();
        assertThat(pgmsNotifications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
