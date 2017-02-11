package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DteJasperConfiguration;
import gov.step.app.repository.DteJasperConfigurationRepository;
import gov.step.app.repository.search.DteJasperConfigurationSearchRepository;

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
 * Test class for the DteJasperConfigurationResource REST controller.
 *
 * @see DteJasperConfigurationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DteJasperConfigurationResourceTest {

    private static final String DEFAULT_DOMAIN = "AAAAA";
    private static final String UPDATED_DOMAIN = "BBBBB";
    private static final String DEFAULT_PORT = "AAAAA";
    private static final String UPDATED_PORT = "BBBBB";
    private static final String DEFAULT_USERNAME = "AAAAA";
    private static final String UPDATED_USERNAME = "BBBBB";
    private static final String DEFAULT_PASSWORD = "AAAAA";
    private static final String UPDATED_PASSWORD = "BBBBB";
    private static final String DEFAULT_CREATE_URL = "AAAAA";
    private static final String UPDATED_CREATE_URL = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CREATE_BY = "AAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBB";
    private static final String DEFAULT_MODIFIED_BY = "AAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private DteJasperConfigurationRepository dteJasperConfigurationRepository;

    @Inject
    private DteJasperConfigurationSearchRepository dteJasperConfigurationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDteJasperConfigurationMockMvc;

    private DteJasperConfiguration dteJasperConfiguration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DteJasperConfigurationResource dteJasperConfigurationResource = new DteJasperConfigurationResource();
        ReflectionTestUtils.setField(dteJasperConfigurationResource, "dteJasperConfigurationRepository", dteJasperConfigurationRepository);
        ReflectionTestUtils.setField(dteJasperConfigurationResource, "dteJasperConfigurationSearchRepository", dteJasperConfigurationSearchRepository);
        this.restDteJasperConfigurationMockMvc = MockMvcBuilders.standaloneSetup(dteJasperConfigurationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dteJasperConfiguration = new DteJasperConfiguration();
        dteJasperConfiguration.setDomain(DEFAULT_DOMAIN);
        dteJasperConfiguration.setPort(DEFAULT_PORT);
        dteJasperConfiguration.setUsername(DEFAULT_USERNAME);
        dteJasperConfiguration.setPassword(DEFAULT_PASSWORD);
        dteJasperConfiguration.setCreateUrl(DEFAULT_CREATE_URL);
        dteJasperConfiguration.setCreateDate(DEFAULT_CREATE_DATE);
        dteJasperConfiguration.setModifiedDate(DEFAULT_MODIFIED_DATE);
        dteJasperConfiguration.setCreateBy(DEFAULT_CREATE_BY);
        dteJasperConfiguration.setModifiedBy(DEFAULT_MODIFIED_BY);
        dteJasperConfiguration.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDteJasperConfiguration() throws Exception {
        int databaseSizeBeforeCreate = dteJasperConfigurationRepository.findAll().size();

        // Create the DteJasperConfiguration

        restDteJasperConfigurationMockMvc.perform(post("/api/dteJasperConfigurations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dteJasperConfiguration)))
                .andExpect(status().isCreated());

        // Validate the DteJasperConfiguration in the database
        List<DteJasperConfiguration> dteJasperConfigurations = dteJasperConfigurationRepository.findAll();
        assertThat(dteJasperConfigurations).hasSize(databaseSizeBeforeCreate + 1);
        DteJasperConfiguration testDteJasperConfiguration = dteJasperConfigurations.get(dteJasperConfigurations.size() - 1);
        assertThat(testDteJasperConfiguration.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testDteJasperConfiguration.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testDteJasperConfiguration.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testDteJasperConfiguration.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testDteJasperConfiguration.getCreateUrl()).isEqualTo(DEFAULT_CREATE_URL);
        assertThat(testDteJasperConfiguration.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDteJasperConfiguration.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testDteJasperConfiguration.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testDteJasperConfiguration.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDteJasperConfiguration.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkDomainIsRequired() throws Exception {
        int databaseSizeBeforeTest = dteJasperConfigurationRepository.findAll().size();
        // set the field null
        dteJasperConfiguration.setDomain(null);

        // Create the DteJasperConfiguration, which fails.

        restDteJasperConfigurationMockMvc.perform(post("/api/dteJasperConfigurations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dteJasperConfiguration)))
                .andExpect(status().isBadRequest());

        List<DteJasperConfiguration> dteJasperConfigurations = dteJasperConfigurationRepository.findAll();
        assertThat(dteJasperConfigurations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = dteJasperConfigurationRepository.findAll().size();
        // set the field null
        dteJasperConfiguration.setPort(null);

        // Create the DteJasperConfiguration, which fails.

        restDteJasperConfigurationMockMvc.perform(post("/api/dteJasperConfigurations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dteJasperConfiguration)))
                .andExpect(status().isBadRequest());

        List<DteJasperConfiguration> dteJasperConfigurations = dteJasperConfigurationRepository.findAll();
        assertThat(dteJasperConfigurations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dteJasperConfigurationRepository.findAll().size();
        // set the field null
        dteJasperConfiguration.setUsername(null);

        // Create the DteJasperConfiguration, which fails.

        restDteJasperConfigurationMockMvc.perform(post("/api/dteJasperConfigurations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dteJasperConfiguration)))
                .andExpect(status().isBadRequest());

        List<DteJasperConfiguration> dteJasperConfigurations = dteJasperConfigurationRepository.findAll();
        assertThat(dteJasperConfigurations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = dteJasperConfigurationRepository.findAll().size();
        // set the field null
        dteJasperConfiguration.setPassword(null);

        // Create the DteJasperConfiguration, which fails.

        restDteJasperConfigurationMockMvc.perform(post("/api/dteJasperConfigurations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dteJasperConfiguration)))
                .andExpect(status().isBadRequest());

        List<DteJasperConfiguration> dteJasperConfigurations = dteJasperConfigurationRepository.findAll();
        assertThat(dteJasperConfigurations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDteJasperConfigurations() throws Exception {
        // Initialize the database
        dteJasperConfigurationRepository.saveAndFlush(dteJasperConfiguration);

        // Get all the dteJasperConfigurations
        restDteJasperConfigurationMockMvc.perform(get("/api/dteJasperConfigurations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dteJasperConfiguration.getId().intValue())))
                .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())))
                .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT.toString())))
                .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
                .andExpect(jsonPath("$.[*].createUrl").value(hasItem(DEFAULT_CREATE_URL.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDteJasperConfiguration() throws Exception {
        // Initialize the database
        dteJasperConfigurationRepository.saveAndFlush(dteJasperConfiguration);

        // Get the dteJasperConfiguration
        restDteJasperConfigurationMockMvc.perform(get("/api/dteJasperConfigurations/{id}", dteJasperConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dteJasperConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.createUrl").value(DEFAULT_CREATE_URL.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDteJasperConfiguration() throws Exception {
        // Get the dteJasperConfiguration
        restDteJasperConfigurationMockMvc.perform(get("/api/dteJasperConfigurations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDteJasperConfiguration() throws Exception {
        // Initialize the database
        dteJasperConfigurationRepository.saveAndFlush(dteJasperConfiguration);

		int databaseSizeBeforeUpdate = dteJasperConfigurationRepository.findAll().size();

        // Update the dteJasperConfiguration
        dteJasperConfiguration.setDomain(UPDATED_DOMAIN);
        dteJasperConfiguration.setPort(UPDATED_PORT);
        dteJasperConfiguration.setUsername(UPDATED_USERNAME);
        dteJasperConfiguration.setPassword(UPDATED_PASSWORD);
        dteJasperConfiguration.setCreateUrl(UPDATED_CREATE_URL);
        dteJasperConfiguration.setCreateDate(UPDATED_CREATE_DATE);
        dteJasperConfiguration.setModifiedDate(UPDATED_MODIFIED_DATE);
        dteJasperConfiguration.setCreateBy(UPDATED_CREATE_BY);
        dteJasperConfiguration.setModifiedBy(UPDATED_MODIFIED_BY);
        dteJasperConfiguration.setStatus(UPDATED_STATUS);

        restDteJasperConfigurationMockMvc.perform(put("/api/dteJasperConfigurations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dteJasperConfiguration)))
                .andExpect(status().isOk());

        // Validate the DteJasperConfiguration in the database
        List<DteJasperConfiguration> dteJasperConfigurations = dteJasperConfigurationRepository.findAll();
        assertThat(dteJasperConfigurations).hasSize(databaseSizeBeforeUpdate);
        DteJasperConfiguration testDteJasperConfiguration = dteJasperConfigurations.get(dteJasperConfigurations.size() - 1);
        assertThat(testDteJasperConfiguration.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testDteJasperConfiguration.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testDteJasperConfiguration.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDteJasperConfiguration.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDteJasperConfiguration.getCreateUrl()).isEqualTo(UPDATED_CREATE_URL);
        assertThat(testDteJasperConfiguration.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDteJasperConfiguration.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testDteJasperConfiguration.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testDteJasperConfiguration.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDteJasperConfiguration.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDteJasperConfiguration() throws Exception {
        // Initialize the database
        dteJasperConfigurationRepository.saveAndFlush(dteJasperConfiguration);

		int databaseSizeBeforeDelete = dteJasperConfigurationRepository.findAll().size();

        // Get the dteJasperConfiguration
        restDteJasperConfigurationMockMvc.perform(delete("/api/dteJasperConfigurations/{id}", dteJasperConfiguration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DteJasperConfiguration> dteJasperConfigurations = dteJasperConfigurationRepository.findAll();
        assertThat(dteJasperConfigurations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
