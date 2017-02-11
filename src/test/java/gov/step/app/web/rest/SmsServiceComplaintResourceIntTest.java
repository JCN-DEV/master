package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SmsServiceComplaint;
import gov.step.app.repository.SmsServiceComplaintRepository;
import gov.step.app.repository.search.SmsServiceComplaintSearchRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.step.app.domain.enumeration.priority;

/**
 * Test class for the SmsServiceComplaintResource REST controller.
 *
 * @see SmsServiceComplaintResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SmsServiceComplaintResourceIntTest {

    private static final String DEFAULT_PREVIOUS_CODE = "AAAAA";
    private static final String UPDATED_PREVIOUS_CODE = "BBBBB";


    private static final priority DEFAULT_PRIORITY = priority.High;
    private static final priority UPDATED_PRIORITY = priority.Medium;
    private static final String DEFAULT_COMPLAINT_NAME = "AAAAA";
    private static final String UPDATED_COMPLAINT_NAME = "BBBBB";
    private static final String DEFAULT_FULL_NAME = "AAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBB";
    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBB";
    private static final String DEFAULT_CONTACT_NUMBER = "AAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final byte[] DEFAULT_COMPLAINT_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMPLAINT_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COMPLAINT_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMPLAINT_DOC_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    @Inject
    private SmsServiceComplaintRepository smsServiceComplaintRepository;

    @Inject
    private SmsServiceComplaintSearchRepository smsServiceComplaintSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSmsServiceComplaintMockMvc;

    private SmsServiceComplaint smsServiceComplaint;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsServiceComplaintResource smsServiceComplaintResource = new SmsServiceComplaintResource();
        ReflectionTestUtils.setField(smsServiceComplaintResource, "smsServiceComplaintSearchRepository", smsServiceComplaintSearchRepository);
        ReflectionTestUtils.setField(smsServiceComplaintResource, "smsServiceComplaintRepository", smsServiceComplaintRepository);
        this.restSmsServiceComplaintMockMvc = MockMvcBuilders.standaloneSetup(smsServiceComplaintResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        smsServiceComplaint = new SmsServiceComplaint();
        smsServiceComplaint.setPreviousCode(DEFAULT_PREVIOUS_CODE);
        smsServiceComplaint.setPriority(DEFAULT_PRIORITY);
        smsServiceComplaint.setComplaintName(DEFAULT_COMPLAINT_NAME);
        smsServiceComplaint.setFullName(DEFAULT_FULL_NAME);
        smsServiceComplaint.setEmailAddress(DEFAULT_EMAIL_ADDRESS);
        smsServiceComplaint.setContactNumber(DEFAULT_CONTACT_NUMBER);
        smsServiceComplaint.setDescription(DEFAULT_DESCRIPTION);
        smsServiceComplaint.setComplaintDoc(DEFAULT_COMPLAINT_DOC);
        smsServiceComplaint.setComplaintDocContentType(DEFAULT_COMPLAINT_DOC_CONTENT_TYPE);
        smsServiceComplaint.setActiveStatus(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void createSmsServiceComplaint() throws Exception {
        int databaseSizeBeforeCreate = smsServiceComplaintRepository.findAll().size();

        // Create the SmsServiceComplaint

        restSmsServiceComplaintMockMvc.perform(post("/api/smsServiceComplaints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceComplaint)))
                .andExpect(status().isCreated());

        // Validate the SmsServiceComplaint in the database
        List<SmsServiceComplaint> smsServiceComplaints = smsServiceComplaintRepository.findAll();
        assertThat(smsServiceComplaints).hasSize(databaseSizeBeforeCreate + 1);
        SmsServiceComplaint testSmsServiceComplaint = smsServiceComplaints.get(smsServiceComplaints.size() - 1);
        assertThat(testSmsServiceComplaint.getPreviousCode()).isEqualTo(DEFAULT_PREVIOUS_CODE);
        assertThat(testSmsServiceComplaint.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testSmsServiceComplaint.getComplaintName()).isEqualTo(DEFAULT_COMPLAINT_NAME);
        assertThat(testSmsServiceComplaint.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testSmsServiceComplaint.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testSmsServiceComplaint.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testSmsServiceComplaint.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSmsServiceComplaint.getComplaintDoc()).isEqualTo(DEFAULT_COMPLAINT_DOC);
        assertThat(testSmsServiceComplaint.getComplaintDocContentType()).isEqualTo(DEFAULT_COMPLAINT_DOC_CONTENT_TYPE);
        assertThat(testSmsServiceComplaint.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void checkComplaintNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsServiceComplaintRepository.findAll().size();
        // set the field null
        smsServiceComplaint.setComplaintName(null);

        // Create the SmsServiceComplaint, which fails.

        restSmsServiceComplaintMockMvc.perform(post("/api/smsServiceComplaints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceComplaint)))
                .andExpect(status().isBadRequest());

        List<SmsServiceComplaint> smsServiceComplaints = smsServiceComplaintRepository.findAll();
        assertThat(smsServiceComplaints).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsServiceComplaintRepository.findAll().size();
        // set the field null
        smsServiceComplaint.setDescription(null);

        // Create the SmsServiceComplaint, which fails.

        restSmsServiceComplaintMockMvc.perform(post("/api/smsServiceComplaints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceComplaint)))
                .andExpect(status().isBadRequest());

        List<SmsServiceComplaint> smsServiceComplaints = smsServiceComplaintRepository.findAll();
        assertThat(smsServiceComplaints).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSmsServiceComplaints() throws Exception {
        // Initialize the database
        smsServiceComplaintRepository.saveAndFlush(smsServiceComplaint);

        // Get all the smsServiceComplaints
        restSmsServiceComplaintMockMvc.perform(get("/api/smsServiceComplaints?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(smsServiceComplaint.getId().intValue())))
                .andExpect(jsonPath("$.[*].previousCode").value(hasItem(DEFAULT_PREVIOUS_CODE.toString())))
                .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
                .andExpect(jsonPath("$.[*].complaintName").value(hasItem(DEFAULT_COMPLAINT_NAME.toString())))
                .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].complaintDocContentType").value(hasItem(DEFAULT_COMPLAINT_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].complaintDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPLAINT_DOC))))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getSmsServiceComplaint() throws Exception {
        // Initialize the database
        smsServiceComplaintRepository.saveAndFlush(smsServiceComplaint);

        // Get the smsServiceComplaint
        restSmsServiceComplaintMockMvc.perform(get("/api/smsServiceComplaints/{id}", smsServiceComplaint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(smsServiceComplaint.getId().intValue()))
            .andExpect(jsonPath("$.previousCode").value(DEFAULT_PREVIOUS_CODE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.complaintName").value(DEFAULT_COMPLAINT_NAME.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.complaintDocContentType").value(DEFAULT_COMPLAINT_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.complaintDoc").value(Base64Utils.encodeToString(DEFAULT_COMPLAINT_DOC)))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsServiceComplaint() throws Exception {
        // Get the smsServiceComplaint
        restSmsServiceComplaintMockMvc.perform(get("/api/smsServiceComplaints/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsServiceComplaint() throws Exception {
        // Initialize the database
        smsServiceComplaintRepository.saveAndFlush(smsServiceComplaint);

		int databaseSizeBeforeUpdate = smsServiceComplaintRepository.findAll().size();

        // Update the smsServiceComplaint
        smsServiceComplaint.setPreviousCode(UPDATED_PREVIOUS_CODE);
        smsServiceComplaint.setPriority(UPDATED_PRIORITY);
        smsServiceComplaint.setComplaintName(UPDATED_COMPLAINT_NAME);
        smsServiceComplaint.setFullName(UPDATED_FULL_NAME);
        smsServiceComplaint.setEmailAddress(UPDATED_EMAIL_ADDRESS);
        smsServiceComplaint.setContactNumber(UPDATED_CONTACT_NUMBER);
        smsServiceComplaint.setDescription(UPDATED_DESCRIPTION);
        smsServiceComplaint.setComplaintDoc(UPDATED_COMPLAINT_DOC);
        smsServiceComplaint.setComplaintDocContentType(UPDATED_COMPLAINT_DOC_CONTENT_TYPE);
        smsServiceComplaint.setActiveStatus(UPDATED_ACTIVE_STATUS);

        restSmsServiceComplaintMockMvc.perform(put("/api/smsServiceComplaints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceComplaint)))
                .andExpect(status().isOk());

        // Validate the SmsServiceComplaint in the database
        List<SmsServiceComplaint> smsServiceComplaints = smsServiceComplaintRepository.findAll();
        assertThat(smsServiceComplaints).hasSize(databaseSizeBeforeUpdate);
        SmsServiceComplaint testSmsServiceComplaint = smsServiceComplaints.get(smsServiceComplaints.size() - 1);
        assertThat(testSmsServiceComplaint.getPreviousCode()).isEqualTo(UPDATED_PREVIOUS_CODE);
        assertThat(testSmsServiceComplaint.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testSmsServiceComplaint.getComplaintName()).isEqualTo(UPDATED_COMPLAINT_NAME);
        assertThat(testSmsServiceComplaint.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testSmsServiceComplaint.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testSmsServiceComplaint.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testSmsServiceComplaint.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSmsServiceComplaint.getComplaintDoc()).isEqualTo(UPDATED_COMPLAINT_DOC);
        assertThat(testSmsServiceComplaint.getComplaintDocContentType()).isEqualTo(UPDATED_COMPLAINT_DOC_CONTENT_TYPE);
        assertThat(testSmsServiceComplaint.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void deleteSmsServiceComplaint() throws Exception {
        // Initialize the database
        smsServiceComplaintRepository.saveAndFlush(smsServiceComplaint);

		int databaseSizeBeforeDelete = smsServiceComplaintRepository.findAll().size();

        // Get the smsServiceComplaint
        restSmsServiceComplaintMockMvc.perform(delete("/api/smsServiceComplaints/{id}", smsServiceComplaint.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsServiceComplaint> smsServiceComplaints = smsServiceComplaintRepository.findAll();
        assertThat(smsServiceComplaints).hasSize(databaseSizeBeforeDelete - 1);
    }
}
