package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.NotificationStep;
import gov.step.app.repository.NotificationStepRepository;
import gov.step.app.repository.search.NotificationStepSearchRepository;

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
 * Test class for the NotificationStepResource REST controller.
 *
 * @see NotificationStepResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NotificationStepResourceTest {


    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final LocalDate DEFAULT_NOTIFY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NOTIFY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_URLS = "AAAAA";
    private static final String UPDATED_URLS = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";
    private static final String DEFAULT_NOTIFICATION = "AAAAA";
    private static final String UPDATED_NOTIFICATION = "BBBBB";

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private NotificationStepSearchRepository notificationStepSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNotificationStepMockMvc;

    private NotificationStep notificationStep;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NotificationStepResource notificationStepResource = new NotificationStepResource();
        ReflectionTestUtils.setField(notificationStepResource, "notificationStepRepository", notificationStepRepository);
        ReflectionTestUtils.setField(notificationStepResource, "notificationStepSearchRepository", notificationStepSearchRepository);
        this.restNotificationStepMockMvc = MockMvcBuilders.standaloneSetup(notificationStepResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        notificationStep = new NotificationStep();
        notificationStep.setUserId(DEFAULT_USER_ID);
        notificationStep.setNotifyDate(DEFAULT_NOTIFY_DATE);
        notificationStep.setUrls(DEFAULT_URLS);
        notificationStep.setStatus(DEFAULT_STATUS);
        notificationStep.setCreateBy(DEFAULT_CREATE_BY);
        notificationStep.setCreateDate(DEFAULT_CREATE_DATE);
        notificationStep.setUpdateBy(DEFAULT_UPDATE_BY);
        notificationStep.setUpdateDate(DEFAULT_UPDATE_DATE);
        notificationStep.setRemarks(DEFAULT_REMARKS);
        notificationStep.setNotification(DEFAULT_NOTIFICATION);
    }

    @Test
    @Transactional
    public void createNotificationStep() throws Exception {
        int databaseSizeBeforeCreate = notificationStepRepository.findAll().size();

        // Create the NotificationStep

        restNotificationStepMockMvc.perform(post("/api/notificationSteps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(notificationStep)))
                .andExpect(status().isCreated());

        // Validate the NotificationStep in the database
        List<NotificationStep> notificationSteps = notificationStepRepository.findAll();
        assertThat(notificationSteps).hasSize(databaseSizeBeforeCreate + 1);
        NotificationStep testNotificationStep = notificationSteps.get(notificationSteps.size() - 1);
        assertThat(testNotificationStep.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testNotificationStep.getNotifyDate()).isEqualTo(DEFAULT_NOTIFY_DATE);
        assertThat(testNotificationStep.getUrls()).isEqualTo(DEFAULT_URLS);
        assertThat(testNotificationStep.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNotificationStep.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testNotificationStep.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testNotificationStep.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testNotificationStep.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testNotificationStep.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testNotificationStep.getNotification()).isEqualTo(DEFAULT_NOTIFICATION);
    }

    @Test
    @Transactional
    public void getAllNotificationSteps() throws Exception {
        // Initialize the database
        notificationStepRepository.saveAndFlush(notificationStep);

        // Get all the notificationSteps
        restNotificationStepMockMvc.perform(get("/api/notificationSteps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(notificationStep.getId().intValue())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
                .andExpect(jsonPath("$.[*].notifyDate").value(hasItem(DEFAULT_NOTIFY_DATE.toString())))
                .andExpect(jsonPath("$.[*].urls").value(hasItem(DEFAULT_URLS.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].notification").value(hasItem(DEFAULT_NOTIFICATION.toString())));
    }

    @Test
    @Transactional
    public void getNotificationStep() throws Exception {
        // Initialize the database
        notificationStepRepository.saveAndFlush(notificationStep);

        // Get the notificationStep
        restNotificationStepMockMvc.perform(get("/api/notificationSteps/{id}", notificationStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(notificationStep.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.notifyDate").value(DEFAULT_NOTIFY_DATE.toString()))
            .andExpect(jsonPath("$.urls").value(DEFAULT_URLS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.notification").value(DEFAULT_NOTIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNotificationStep() throws Exception {
        // Get the notificationStep
        restNotificationStepMockMvc.perform(get("/api/notificationSteps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificationStep() throws Exception {
        // Initialize the database
        notificationStepRepository.saveAndFlush(notificationStep);

		int databaseSizeBeforeUpdate = notificationStepRepository.findAll().size();

        // Update the notificationStep
        notificationStep.setUserId(UPDATED_USER_ID);
        notificationStep.setNotifyDate(UPDATED_NOTIFY_DATE);
        notificationStep.setUrls(UPDATED_URLS);
        notificationStep.setStatus(UPDATED_STATUS);
        notificationStep.setCreateBy(UPDATED_CREATE_BY);
        notificationStep.setCreateDate(UPDATED_CREATE_DATE);
        notificationStep.setUpdateBy(UPDATED_UPDATE_BY);
        notificationStep.setUpdateDate(UPDATED_UPDATE_DATE);
        notificationStep.setRemarks(UPDATED_REMARKS);
        notificationStep.setNotification(UPDATED_NOTIFICATION);

        restNotificationStepMockMvc.perform(put("/api/notificationSteps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(notificationStep)))
                .andExpect(status().isOk());

        // Validate the NotificationStep in the database
        List<NotificationStep> notificationSteps = notificationStepRepository.findAll();
        assertThat(notificationSteps).hasSize(databaseSizeBeforeUpdate);
        NotificationStep testNotificationStep = notificationSteps.get(notificationSteps.size() - 1);
        assertThat(testNotificationStep.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testNotificationStep.getNotifyDate()).isEqualTo(UPDATED_NOTIFY_DATE);
        assertThat(testNotificationStep.getUrls()).isEqualTo(UPDATED_URLS);
        assertThat(testNotificationStep.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotificationStep.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testNotificationStep.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testNotificationStep.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testNotificationStep.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testNotificationStep.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testNotificationStep.getNotification()).isEqualTo(UPDATED_NOTIFICATION);
    }

    @Test
    @Transactional
    public void deleteNotificationStep() throws Exception {
        // Initialize the database
        notificationStepRepository.saveAndFlush(notificationStep);

		int databaseSizeBeforeDelete = notificationStepRepository.findAll().size();

        // Get the notificationStep
        restNotificationStepMockMvc.perform(delete("/api/notificationSteps/{id}", notificationStep.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<NotificationStep> notificationSteps = notificationStepRepository.findAll();
        assertThat(notificationSteps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
