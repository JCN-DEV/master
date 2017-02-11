package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SmsServiceDepartment;
import gov.step.app.repository.SmsServiceDepartmentRepository;
import gov.step.app.repository.search.SmsServiceDepartmentSearchRepository;

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
 * Test class for the SmsServiceDepartmentResource REST controller.
 *
 * @see SmsServiceDepartmentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SmsServiceDepartmentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    @Inject
    private SmsServiceDepartmentRepository smsServiceDepartmentRepository;

    @Inject
    private SmsServiceDepartmentSearchRepository smsServiceDepartmentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSmsServiceDepartmentMockMvc;

    private SmsServiceDepartment smsServiceDepartment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsServiceDepartmentResource smsServiceDepartmentResource = new SmsServiceDepartmentResource();
        ReflectionTestUtils.setField(smsServiceDepartmentResource, "smsServiceDepartmentSearchRepository", smsServiceDepartmentSearchRepository);
        ReflectionTestUtils.setField(smsServiceDepartmentResource, "smsServiceDepartmentRepository", smsServiceDepartmentRepository);
        this.restSmsServiceDepartmentMockMvc = MockMvcBuilders.standaloneSetup(smsServiceDepartmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        smsServiceDepartment = new SmsServiceDepartment();
        smsServiceDepartment.setName(DEFAULT_NAME);
        smsServiceDepartment.setDescription(DEFAULT_DESCRIPTION);
        smsServiceDepartment.setActiveStatus(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void createSmsServiceDepartment() throws Exception {
        int databaseSizeBeforeCreate = smsServiceDepartmentRepository.findAll().size();

        // Create the SmsServiceDepartment

        restSmsServiceDepartmentMockMvc.perform(post("/api/smsServiceDepartments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceDepartment)))
                .andExpect(status().isCreated());

        // Validate the SmsServiceDepartment in the database
        List<SmsServiceDepartment> smsServiceDepartments = smsServiceDepartmentRepository.findAll();
        assertThat(smsServiceDepartments).hasSize(databaseSizeBeforeCreate + 1);
        SmsServiceDepartment testSmsServiceDepartment = smsServiceDepartments.get(smsServiceDepartments.size() - 1);
        assertThat(testSmsServiceDepartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSmsServiceDepartment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSmsServiceDepartment.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsServiceDepartmentRepository.findAll().size();
        // set the field null
        smsServiceDepartment.setName(null);

        // Create the SmsServiceDepartment, which fails.

        restSmsServiceDepartmentMockMvc.perform(post("/api/smsServiceDepartments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceDepartment)))
                .andExpect(status().isBadRequest());

        List<SmsServiceDepartment> smsServiceDepartments = smsServiceDepartmentRepository.findAll();
        assertThat(smsServiceDepartments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsServiceDepartmentRepository.findAll().size();
        // set the field null
        smsServiceDepartment.setDescription(null);

        // Create the SmsServiceDepartment, which fails.

        restSmsServiceDepartmentMockMvc.perform(post("/api/smsServiceDepartments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceDepartment)))
                .andExpect(status().isBadRequest());

        List<SmsServiceDepartment> smsServiceDepartments = smsServiceDepartmentRepository.findAll();
        assertThat(smsServiceDepartments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSmsServiceDepartments() throws Exception {
        // Initialize the database
        smsServiceDepartmentRepository.saveAndFlush(smsServiceDepartment);

        // Get all the smsServiceDepartments
        restSmsServiceDepartmentMockMvc.perform(get("/api/smsServiceDepartments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(smsServiceDepartment.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getSmsServiceDepartment() throws Exception {
        // Initialize the database
        smsServiceDepartmentRepository.saveAndFlush(smsServiceDepartment);

        // Get the smsServiceDepartment
        restSmsServiceDepartmentMockMvc.perform(get("/api/smsServiceDepartments/{id}", smsServiceDepartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(smsServiceDepartment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsServiceDepartment() throws Exception {
        // Get the smsServiceDepartment
        restSmsServiceDepartmentMockMvc.perform(get("/api/smsServiceDepartments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsServiceDepartment() throws Exception {
        // Initialize the database
        smsServiceDepartmentRepository.saveAndFlush(smsServiceDepartment);

		int databaseSizeBeforeUpdate = smsServiceDepartmentRepository.findAll().size();

        // Update the smsServiceDepartment
        smsServiceDepartment.setName(UPDATED_NAME);
        smsServiceDepartment.setDescription(UPDATED_DESCRIPTION);
        smsServiceDepartment.setActiveStatus(UPDATED_ACTIVE_STATUS);

        restSmsServiceDepartmentMockMvc.perform(put("/api/smsServiceDepartments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceDepartment)))
                .andExpect(status().isOk());

        // Validate the SmsServiceDepartment in the database
        List<SmsServiceDepartment> smsServiceDepartments = smsServiceDepartmentRepository.findAll();
        assertThat(smsServiceDepartments).hasSize(databaseSizeBeforeUpdate);
        SmsServiceDepartment testSmsServiceDepartment = smsServiceDepartments.get(smsServiceDepartments.size() - 1);
        assertThat(testSmsServiceDepartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSmsServiceDepartment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSmsServiceDepartment.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void deleteSmsServiceDepartment() throws Exception {
        // Initialize the database
        smsServiceDepartmentRepository.saveAndFlush(smsServiceDepartment);

		int databaseSizeBeforeDelete = smsServiceDepartmentRepository.findAll().size();

        // Get the smsServiceDepartment
        restSmsServiceDepartmentMockMvc.perform(delete("/api/smsServiceDepartments/{id}", smsServiceDepartment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsServiceDepartment> smsServiceDepartments = smsServiceDepartmentRepository.findAll();
        assertThat(smsServiceDepartments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
