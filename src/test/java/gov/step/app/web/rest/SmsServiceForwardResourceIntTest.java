package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SmsServiceForward;
import gov.step.app.repository.SmsServiceForwardRepository;
import gov.step.app.repository.search.SmsServiceForwardSearchRepository;

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

import gov.step.app.domain.enumeration.serviceStatus;

/**
 * Test class for the SmsServiceForwardResource REST controller.
 *
 * @see SmsServiceForwardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SmsServiceForwardResourceIntTest {

    private static final String DEFAULT_CC = "AAAAA";
    private static final String UPDATED_CC = "BBBBB";


    private static final serviceStatus DEFAULT_SERVICE_STATUS = serviceStatus.Completed;
    private static final serviceStatus UPDATED_SERVICE_STATUS = serviceStatus.Critical;
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    private static final LocalDate DEFAULT_FORWARD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FORWARD_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    @Inject
    private SmsServiceForwardRepository smsServiceForwardRepository;

    @Inject
    private SmsServiceForwardSearchRepository smsServiceForwardSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSmsServiceForwardMockMvc;

    private SmsServiceForward smsServiceForward;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsServiceForwardResource smsServiceForwardResource = new SmsServiceForwardResource();
        ReflectionTestUtils.setField(smsServiceForwardResource, "smsServiceForwardSearchRepository", smsServiceForwardSearchRepository);
        ReflectionTestUtils.setField(smsServiceForwardResource, "smsServiceForwardRepository", smsServiceForwardRepository);
        this.restSmsServiceForwardMockMvc = MockMvcBuilders.standaloneSetup(smsServiceForwardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        smsServiceForward = new SmsServiceForward();
        smsServiceForward.setCc(DEFAULT_CC);
        smsServiceForward.setServiceStatus(DEFAULT_SERVICE_STATUS);
        smsServiceForward.setComments(DEFAULT_COMMENTS);
        smsServiceForward.setForwardDate(DEFAULT_FORWARD_DATE);
        smsServiceForward.setActiveStatus(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void createSmsServiceForward() throws Exception {
        int databaseSizeBeforeCreate = smsServiceForwardRepository.findAll().size();

        // Create the SmsServiceForward

        restSmsServiceForwardMockMvc.perform(post("/api/smsServiceForwards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceForward)))
                .andExpect(status().isCreated());

        // Validate the SmsServiceForward in the database
        List<SmsServiceForward> smsServiceForwards = smsServiceForwardRepository.findAll();
        assertThat(smsServiceForwards).hasSize(databaseSizeBeforeCreate + 1);
        SmsServiceForward testSmsServiceForward = smsServiceForwards.get(smsServiceForwards.size() - 1);
        assertThat(testSmsServiceForward.getCc()).isEqualTo(DEFAULT_CC);
        assertThat(testSmsServiceForward.getServiceStatus()).isEqualTo(DEFAULT_SERVICE_STATUS);
        assertThat(testSmsServiceForward.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testSmsServiceForward.getForwardDate()).isEqualTo(DEFAULT_FORWARD_DATE);
        assertThat(testSmsServiceForward.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void getAllSmsServiceForwards() throws Exception {
        // Initialize the database
        smsServiceForwardRepository.saveAndFlush(smsServiceForward);

        // Get all the smsServiceForwards
        restSmsServiceForwardMockMvc.perform(get("/api/smsServiceForwards?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(smsServiceForward.getId().intValue())))
                .andExpect(jsonPath("$.[*].cc").value(hasItem(DEFAULT_CC.toString())))
                .andExpect(jsonPath("$.[*].serviceStatus").value(hasItem(DEFAULT_SERVICE_STATUS.toString())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].forwardDate").value(hasItem(DEFAULT_FORWARD_DATE.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getSmsServiceForward() throws Exception {
        // Initialize the database
        smsServiceForwardRepository.saveAndFlush(smsServiceForward);

        // Get the smsServiceForward
        restSmsServiceForwardMockMvc.perform(get("/api/smsServiceForwards/{id}", smsServiceForward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(smsServiceForward.getId().intValue()))
            .andExpect(jsonPath("$.cc").value(DEFAULT_CC.toString()))
            .andExpect(jsonPath("$.serviceStatus").value(DEFAULT_SERVICE_STATUS.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.forwardDate").value(DEFAULT_FORWARD_DATE.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsServiceForward() throws Exception {
        // Get the smsServiceForward
        restSmsServiceForwardMockMvc.perform(get("/api/smsServiceForwards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsServiceForward() throws Exception {
        // Initialize the database
        smsServiceForwardRepository.saveAndFlush(smsServiceForward);

		int databaseSizeBeforeUpdate = smsServiceForwardRepository.findAll().size();

        // Update the smsServiceForward
        smsServiceForward.setCc(UPDATED_CC);
        smsServiceForward.setServiceStatus(UPDATED_SERVICE_STATUS);
        smsServiceForward.setComments(UPDATED_COMMENTS);
        smsServiceForward.setForwardDate(UPDATED_FORWARD_DATE);
        smsServiceForward.setActiveStatus(UPDATED_ACTIVE_STATUS);

        restSmsServiceForwardMockMvc.perform(put("/api/smsServiceForwards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceForward)))
                .andExpect(status().isOk());

        // Validate the SmsServiceForward in the database
        List<SmsServiceForward> smsServiceForwards = smsServiceForwardRepository.findAll();
        assertThat(smsServiceForwards).hasSize(databaseSizeBeforeUpdate);
        SmsServiceForward testSmsServiceForward = smsServiceForwards.get(smsServiceForwards.size() - 1);
        assertThat(testSmsServiceForward.getCc()).isEqualTo(UPDATED_CC);
        assertThat(testSmsServiceForward.getServiceStatus()).isEqualTo(UPDATED_SERVICE_STATUS);
        assertThat(testSmsServiceForward.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testSmsServiceForward.getForwardDate()).isEqualTo(UPDATED_FORWARD_DATE);
        assertThat(testSmsServiceForward.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void deleteSmsServiceForward() throws Exception {
        // Initialize the database
        smsServiceForwardRepository.saveAndFlush(smsServiceForward);

		int databaseSizeBeforeDelete = smsServiceForwardRepository.findAll().size();

        // Get the smsServiceForward
        restSmsServiceForwardMockMvc.perform(delete("/api/smsServiceForwards/{id}", smsServiceForward.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsServiceForward> smsServiceForwards = smsServiceForwardRepository.findAll();
        assertThat(smsServiceForwards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
