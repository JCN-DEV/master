package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.UmracModuleSetup;
import gov.step.app.repository.UmracModuleSetupRepository;
import gov.step.app.repository.search.UmracModuleSetupSearchRepository;

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
 * Test class for the UmracModuleSetupResource REST controller.
 *
 * @see UmracModuleSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UmracModuleSetupResourceTest {

    private static final String DEFAULT_MODULE_ID = "AAAAA";
    private static final String UPDATED_MODULE_ID = "BBBBB";
    private static final String DEFAULT_MODULE_NAME = "AAAAA";
    private static final String UPDATED_MODULE_NAME = "BBBBB";
    private static final String DEFAULT_MODULE_URL = "AAAAA";
    private static final String UPDATED_MODULE_URL = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final LocalDate DEFAULT_UPDATED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private UmracModuleSetupRepository umracModuleSetupRepository;

    @Inject
    private UmracModuleSetupSearchRepository umracModuleSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmracModuleSetupMockMvc;

    private UmracModuleSetup umracModuleSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmracModuleSetupResource umracModuleSetupResource = new UmracModuleSetupResource();
        ReflectionTestUtils.setField(umracModuleSetupResource, "umracModuleSetupRepository", umracModuleSetupRepository);
        ReflectionTestUtils.setField(umracModuleSetupResource, "umracModuleSetupSearchRepository", umracModuleSetupSearchRepository);
        this.restUmracModuleSetupMockMvc = MockMvcBuilders.standaloneSetup(umracModuleSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umracModuleSetup = new UmracModuleSetup();
        umracModuleSetup.setModuleId(DEFAULT_MODULE_ID);
        umracModuleSetup.setModuleName(DEFAULT_MODULE_NAME);
        umracModuleSetup.setModuleUrl(DEFAULT_MODULE_URL);
        umracModuleSetup.setDescription(DEFAULT_DESCRIPTION);
        umracModuleSetup.setStatus(DEFAULT_STATUS);
        umracModuleSetup.setCreateDate(DEFAULT_CREATE_DATE);
        umracModuleSetup.setCreateBy(DEFAULT_CREATE_BY);
        umracModuleSetup.setUpdatedBy(DEFAULT_UPDATED_BY);
        umracModuleSetup.setUpdatedTime(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createUmracModuleSetup() throws Exception {
        int databaseSizeBeforeCreate = umracModuleSetupRepository.findAll().size();

        // Create the UmracModuleSetup

        restUmracModuleSetupMockMvc.perform(post("/api/umracModuleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracModuleSetup)))
                .andExpect(status().isCreated());

        // Validate the UmracModuleSetup in the database
        List<UmracModuleSetup> umracModuleSetups = umracModuleSetupRepository.findAll();
        assertThat(umracModuleSetups).hasSize(databaseSizeBeforeCreate + 1);
        UmracModuleSetup testUmracModuleSetup = umracModuleSetups.get(umracModuleSetups.size() - 1);
        assertThat(testUmracModuleSetup.getModuleId()).isEqualTo(DEFAULT_MODULE_ID);
        assertThat(testUmracModuleSetup.getModuleName()).isEqualTo(DEFAULT_MODULE_NAME);
        assertThat(testUmracModuleSetup.getModuleUrl()).isEqualTo(DEFAULT_MODULE_URL);
        assertThat(testUmracModuleSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUmracModuleSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUmracModuleSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testUmracModuleSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testUmracModuleSetup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUmracModuleSetup.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void checkModuleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = umracModuleSetupRepository.findAll().size();
        // set the field null
        umracModuleSetup.setModuleName(null);

        // Create the UmracModuleSetup, which fails.

        restUmracModuleSetupMockMvc.perform(post("/api/umracModuleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracModuleSetup)))
                .andExpect(status().isBadRequest());

        List<UmracModuleSetup> umracModuleSetups = umracModuleSetupRepository.findAll();
        assertThat(umracModuleSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModuleUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = umracModuleSetupRepository.findAll().size();
        // set the field null
        umracModuleSetup.setModuleUrl(null);

        // Create the UmracModuleSetup, which fails.

        restUmracModuleSetupMockMvc.perform(post("/api/umracModuleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracModuleSetup)))
                .andExpect(status().isBadRequest());

        List<UmracModuleSetup> umracModuleSetups = umracModuleSetupRepository.findAll();
        assertThat(umracModuleSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUmracModuleSetups() throws Exception {
        // Initialize the database
        umracModuleSetupRepository.saveAndFlush(umracModuleSetup);

        // Get all the umracModuleSetups
        restUmracModuleSetupMockMvc.perform(get("/api/umracModuleSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umracModuleSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].moduleId").value(hasItem(DEFAULT_MODULE_ID.toString())))
                .andExpect(jsonPath("$.[*].moduleName").value(hasItem(DEFAULT_MODULE_NAME.toString())))
                .andExpect(jsonPath("$.[*].moduleUrl").value(hasItem(DEFAULT_MODULE_URL.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getUmracModuleSetup() throws Exception {
        // Initialize the database
        umracModuleSetupRepository.saveAndFlush(umracModuleSetup);

        // Get the umracModuleSetup
        restUmracModuleSetupMockMvc.perform(get("/api/umracModuleSetups/{id}", umracModuleSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umracModuleSetup.getId().intValue()))
            .andExpect(jsonPath("$.moduleId").value(DEFAULT_MODULE_ID.toString()))
            .andExpect(jsonPath("$.moduleName").value(DEFAULT_MODULE_NAME.toString()))
            .andExpect(jsonPath("$.moduleUrl").value(DEFAULT_MODULE_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUmracModuleSetup() throws Exception {
        // Get the umracModuleSetup
        restUmracModuleSetupMockMvc.perform(get("/api/umracModuleSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmracModuleSetup() throws Exception {
        // Initialize the database
        umracModuleSetupRepository.saveAndFlush(umracModuleSetup);

		int databaseSizeBeforeUpdate = umracModuleSetupRepository.findAll().size();

        // Update the umracModuleSetup
        umracModuleSetup.setModuleId(UPDATED_MODULE_ID);
        umracModuleSetup.setModuleName(UPDATED_MODULE_NAME);
        umracModuleSetup.setModuleUrl(UPDATED_MODULE_URL);
        umracModuleSetup.setDescription(UPDATED_DESCRIPTION);
        umracModuleSetup.setStatus(UPDATED_STATUS);
        umracModuleSetup.setCreateDate(UPDATED_CREATE_DATE);
        umracModuleSetup.setCreateBy(UPDATED_CREATE_BY);
        umracModuleSetup.setUpdatedBy(UPDATED_UPDATED_BY);
        umracModuleSetup.setUpdatedTime(UPDATED_UPDATED_TIME);

        restUmracModuleSetupMockMvc.perform(put("/api/umracModuleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracModuleSetup)))
                .andExpect(status().isOk());

        // Validate the UmracModuleSetup in the database
        List<UmracModuleSetup> umracModuleSetups = umracModuleSetupRepository.findAll();
        assertThat(umracModuleSetups).hasSize(databaseSizeBeforeUpdate);
        UmracModuleSetup testUmracModuleSetup = umracModuleSetups.get(umracModuleSetups.size() - 1);
        assertThat(testUmracModuleSetup.getModuleId()).isEqualTo(UPDATED_MODULE_ID);
        assertThat(testUmracModuleSetup.getModuleName()).isEqualTo(UPDATED_MODULE_NAME);
        assertThat(testUmracModuleSetup.getModuleUrl()).isEqualTo(UPDATED_MODULE_URL);
        assertThat(testUmracModuleSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUmracModuleSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUmracModuleSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testUmracModuleSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testUmracModuleSetup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUmracModuleSetup.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteUmracModuleSetup() throws Exception {
        // Initialize the database
        umracModuleSetupRepository.saveAndFlush(umracModuleSetup);

		int databaseSizeBeforeDelete = umracModuleSetupRepository.findAll().size();

        // Get the umracModuleSetup
        restUmracModuleSetupMockMvc.perform(delete("/api/umracModuleSetups/{id}", umracModuleSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmracModuleSetup> umracModuleSetups = umracModuleSetupRepository.findAll();
        assertThat(umracModuleSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
