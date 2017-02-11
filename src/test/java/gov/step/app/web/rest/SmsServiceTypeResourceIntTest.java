package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SmsServiceType;
import gov.step.app.repository.SmsServiceTypeRepository;
import gov.step.app.repository.search.SmsServiceTypeSearchRepository;

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
 * Test class for the SmsServiceTypeResource REST controller.
 *
 * @see SmsServiceTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SmsServiceTypeResourceIntTest {

    private static final String DEFAULT_SERVICE_TYPE_NAME = "AAAAA";
    private static final String UPDATED_SERVICE_TYPE_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private SmsServiceTypeRepository smsServiceTypeRepository;

    @Inject
    private SmsServiceTypeSearchRepository smsServiceTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSmsServiceTypeMockMvc;

    private SmsServiceType smsServiceType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsServiceTypeResource smsServiceTypeResource = new SmsServiceTypeResource();
        ReflectionTestUtils.setField(smsServiceTypeResource, "smsServiceTypeSearchRepository", smsServiceTypeSearchRepository);
        ReflectionTestUtils.setField(smsServiceTypeResource, "smsServiceTypeRepository", smsServiceTypeRepository);
        this.restSmsServiceTypeMockMvc = MockMvcBuilders.standaloneSetup(smsServiceTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        smsServiceType = new SmsServiceType();
        smsServiceType.setServiceTypeName(DEFAULT_SERVICE_TYPE_NAME);
        smsServiceType.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSmsServiceType() throws Exception {
        int databaseSizeBeforeCreate = smsServiceTypeRepository.findAll().size();

        // Create the SmsServiceType

        restSmsServiceTypeMockMvc.perform(post("/api/smsServiceTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceType)))
                .andExpect(status().isCreated());

        // Validate the SmsServiceType in the database
        List<SmsServiceType> smsServiceTypes = smsServiceTypeRepository.findAll();
        assertThat(smsServiceTypes).hasSize(databaseSizeBeforeCreate + 1);
        SmsServiceType testSmsServiceType = smsServiceTypes.get(smsServiceTypes.size() - 1);
        assertThat(testSmsServiceType.getServiceTypeName()).isEqualTo(DEFAULT_SERVICE_TYPE_NAME);
        assertThat(testSmsServiceType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkServiceTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsServiceTypeRepository.findAll().size();
        // set the field null
        smsServiceType.setServiceTypeName(null);

        // Create the SmsServiceType, which fails.

        restSmsServiceTypeMockMvc.perform(post("/api/smsServiceTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceType)))
                .andExpect(status().isBadRequest());

        List<SmsServiceType> smsServiceTypes = smsServiceTypeRepository.findAll();
        assertThat(smsServiceTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsServiceTypeRepository.findAll().size();
        // set the field null
        smsServiceType.setDescription(null);

        // Create the SmsServiceType, which fails.

        restSmsServiceTypeMockMvc.perform(post("/api/smsServiceTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceType)))
                .andExpect(status().isBadRequest());

        List<SmsServiceType> smsServiceTypes = smsServiceTypeRepository.findAll();
        assertThat(smsServiceTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSmsServiceTypes() throws Exception {
        // Initialize the database
        smsServiceTypeRepository.saveAndFlush(smsServiceType);

        // Get all the smsServiceTypes
        restSmsServiceTypeMockMvc.perform(get("/api/smsServiceTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(smsServiceType.getId().intValue())))
                .andExpect(jsonPath("$.[*].serviceTypeName").value(hasItem(DEFAULT_SERVICE_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSmsServiceType() throws Exception {
        // Initialize the database
        smsServiceTypeRepository.saveAndFlush(smsServiceType);

        // Get the smsServiceType
        restSmsServiceTypeMockMvc.perform(get("/api/smsServiceTypes/{id}", smsServiceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(smsServiceType.getId().intValue()))
            .andExpect(jsonPath("$.serviceTypeName").value(DEFAULT_SERVICE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsServiceType() throws Exception {
        // Get the smsServiceType
        restSmsServiceTypeMockMvc.perform(get("/api/smsServiceTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsServiceType() throws Exception {
        // Initialize the database
        smsServiceTypeRepository.saveAndFlush(smsServiceType);

		int databaseSizeBeforeUpdate = smsServiceTypeRepository.findAll().size();

        // Update the smsServiceType
        smsServiceType.setServiceTypeName(UPDATED_SERVICE_TYPE_NAME);
        smsServiceType.setDescription(UPDATED_DESCRIPTION);

        restSmsServiceTypeMockMvc.perform(put("/api/smsServiceTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceType)))
                .andExpect(status().isOk());

        // Validate the SmsServiceType in the database
        List<SmsServiceType> smsServiceTypes = smsServiceTypeRepository.findAll();
        assertThat(smsServiceTypes).hasSize(databaseSizeBeforeUpdate);
        SmsServiceType testSmsServiceType = smsServiceTypes.get(smsServiceTypes.size() - 1);
        assertThat(testSmsServiceType.getServiceTypeName()).isEqualTo(UPDATED_SERVICE_TYPE_NAME);
        assertThat(testSmsServiceType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteSmsServiceType() throws Exception {
        // Initialize the database
        smsServiceTypeRepository.saveAndFlush(smsServiceType);

		int databaseSizeBeforeDelete = smsServiceTypeRepository.findAll().size();

        // Get the smsServiceType
        restSmsServiceTypeMockMvc.perform(delete("/api/smsServiceTypes/{id}", smsServiceType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsServiceType> smsServiceTypes = smsServiceTypeRepository.findAll();
        assertThat(smsServiceTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
