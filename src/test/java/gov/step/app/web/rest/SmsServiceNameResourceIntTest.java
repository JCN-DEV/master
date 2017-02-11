package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SmsServiceName;
import gov.step.app.repository.SmsServiceNameRepository;
import gov.step.app.repository.search.SmsServiceNameSearchRepository;

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
 * Test class for the SmsServiceNameResource REST controller.
 *
 * @see SmsServiceNameResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SmsServiceNameResourceIntTest {

    private static final String DEFAULT_SERVICE_NAME = "AAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private SmsServiceNameRepository smsServiceNameRepository;

    @Inject
    private SmsServiceNameSearchRepository smsServiceNameSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSmsServiceNameMockMvc;

    private SmsServiceName smsServiceName;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsServiceNameResource smsServiceNameResource = new SmsServiceNameResource();
        ReflectionTestUtils.setField(smsServiceNameResource, "smsServiceNameSearchRepository", smsServiceNameSearchRepository);
        ReflectionTestUtils.setField(smsServiceNameResource, "smsServiceNameRepository", smsServiceNameRepository);
        this.restSmsServiceNameMockMvc = MockMvcBuilders.standaloneSetup(smsServiceNameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        smsServiceName = new SmsServiceName();
        smsServiceName.setServiceName(DEFAULT_SERVICE_NAME);
        smsServiceName.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSmsServiceName() throws Exception {
        int databaseSizeBeforeCreate = smsServiceNameRepository.findAll().size();

        // Create the SmsServiceName

        restSmsServiceNameMockMvc.perform(post("/api/smsServiceNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceName)))
                .andExpect(status().isCreated());

        // Validate the SmsServiceName in the database
        List<SmsServiceName> smsServiceNames = smsServiceNameRepository.findAll();
        assertThat(smsServiceNames).hasSize(databaseSizeBeforeCreate + 1);
        SmsServiceName testSmsServiceName = smsServiceNames.get(smsServiceNames.size() - 1);
        assertThat(testSmsServiceName.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testSmsServiceName.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkServiceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsServiceNameRepository.findAll().size();
        // set the field null
        smsServiceName.setServiceName(null);

        // Create the SmsServiceName, which fails.

        restSmsServiceNameMockMvc.perform(post("/api/smsServiceNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceName)))
                .andExpect(status().isBadRequest());

        List<SmsServiceName> smsServiceNames = smsServiceNameRepository.findAll();
        assertThat(smsServiceNames).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsServiceNameRepository.findAll().size();
        // set the field null
        smsServiceName.setDescription(null);

        // Create the SmsServiceName, which fails.

        restSmsServiceNameMockMvc.perform(post("/api/smsServiceNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceName)))
                .andExpect(status().isBadRequest());

        List<SmsServiceName> smsServiceNames = smsServiceNameRepository.findAll();
        assertThat(smsServiceNames).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSmsServiceNames() throws Exception {
        // Initialize the database
        smsServiceNameRepository.saveAndFlush(smsServiceName);

        // Get all the smsServiceNames
        restSmsServiceNameMockMvc.perform(get("/api/smsServiceNames?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(smsServiceName.getId().intValue())))
                .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSmsServiceName() throws Exception {
        // Initialize the database
        smsServiceNameRepository.saveAndFlush(smsServiceName);

        // Get the smsServiceName
        restSmsServiceNameMockMvc.perform(get("/api/smsServiceNames/{id}", smsServiceName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(smsServiceName.getId().intValue()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsServiceName() throws Exception {
        // Get the smsServiceName
        restSmsServiceNameMockMvc.perform(get("/api/smsServiceNames/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsServiceName() throws Exception {
        // Initialize the database
        smsServiceNameRepository.saveAndFlush(smsServiceName);

		int databaseSizeBeforeUpdate = smsServiceNameRepository.findAll().size();

        // Update the smsServiceName
        smsServiceName.setServiceName(UPDATED_SERVICE_NAME);
        smsServiceName.setDescription(UPDATED_DESCRIPTION);

        restSmsServiceNameMockMvc.perform(put("/api/smsServiceNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceName)))
                .andExpect(status().isOk());

        // Validate the SmsServiceName in the database
        List<SmsServiceName> smsServiceNames = smsServiceNameRepository.findAll();
        assertThat(smsServiceNames).hasSize(databaseSizeBeforeUpdate);
        SmsServiceName testSmsServiceName = smsServiceNames.get(smsServiceNames.size() - 1);
        assertThat(testSmsServiceName.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testSmsServiceName.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteSmsServiceName() throws Exception {
        // Initialize the database
        smsServiceNameRepository.saveAndFlush(smsServiceName);

		int databaseSizeBeforeDelete = smsServiceNameRepository.findAll().size();

        // Get the smsServiceName
        restSmsServiceNameMockMvc.perform(delete("/api/smsServiceNames/{id}", smsServiceName.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsServiceName> smsServiceNames = smsServiceNameRepository.findAll();
        assertThat(smsServiceNames).hasSize(databaseSizeBeforeDelete - 1);
    }
}
