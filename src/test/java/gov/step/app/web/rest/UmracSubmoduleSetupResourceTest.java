package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.UmracSubmoduleSetup;
import gov.step.app.repository.UmracSubmoduleSetupRepository;
import gov.step.app.repository.search.UmracSubmoduleSetupSearchRepository;

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
 * Test class for the UmracSubmoduleSetupResource REST controller.
 *
 * @see UmracSubmoduleSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UmracSubmoduleSetupResourceTest {

    private static final String DEFAULT_SUB_MODULE_ID = "AAAAA";
    private static final String UPDATED_SUB_MODULE_ID = "BBBBB";
    private static final String DEFAULT_SUB_MODULE_NAME = "AAAAA";
    private static final String UPDATED_SUB_MODULE_NAME = "BBBBB";
    private static final String DEFAULT_SUB_MODULE_URL = "AAAAA";
    private static final String UPDATED_SUB_MODULE_URL = "BBBBB";
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
    private UmracSubmoduleSetupRepository umracSubmoduleSetupRepository;

    @Inject
    private UmracSubmoduleSetupSearchRepository umracSubmoduleSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmracSubmoduleSetupMockMvc;

    private UmracSubmoduleSetup umracSubmoduleSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmracSubmoduleSetupResource umracSubmoduleSetupResource = new UmracSubmoduleSetupResource();
        ReflectionTestUtils.setField(umracSubmoduleSetupResource, "umracSubmoduleSetupRepository", umracSubmoduleSetupRepository);
        ReflectionTestUtils.setField(umracSubmoduleSetupResource, "umracSubmoduleSetupSearchRepository", umracSubmoduleSetupSearchRepository);
        this.restUmracSubmoduleSetupMockMvc = MockMvcBuilders.standaloneSetup(umracSubmoduleSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umracSubmoduleSetup = new UmracSubmoduleSetup();
        umracSubmoduleSetup.setSubModuleId(DEFAULT_SUB_MODULE_ID);
        umracSubmoduleSetup.setSubModuleName(DEFAULT_SUB_MODULE_NAME);
        umracSubmoduleSetup.setSubModuleUrl(DEFAULT_SUB_MODULE_URL);
        umracSubmoduleSetup.setDescription(DEFAULT_DESCRIPTION);
        umracSubmoduleSetup.setStatus(DEFAULT_STATUS);
        umracSubmoduleSetup.setCreateDate(DEFAULT_CREATE_DATE);
        umracSubmoduleSetup.setCreateBy(DEFAULT_CREATE_BY);
        umracSubmoduleSetup.setUpdatedBy(DEFAULT_UPDATED_BY);
        umracSubmoduleSetup.setUpdatedTime(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createUmracSubmoduleSetup() throws Exception {
        int databaseSizeBeforeCreate = umracSubmoduleSetupRepository.findAll().size();

        // Create the UmracSubmoduleSetup

        restUmracSubmoduleSetupMockMvc.perform(post("/api/umracSubmoduleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracSubmoduleSetup)))
                .andExpect(status().isCreated());

        // Validate the UmracSubmoduleSetup in the database
        List<UmracSubmoduleSetup> umracSubmoduleSetups = umracSubmoduleSetupRepository.findAll();
        assertThat(umracSubmoduleSetups).hasSize(databaseSizeBeforeCreate + 1);
        UmracSubmoduleSetup testUmracSubmoduleSetup = umracSubmoduleSetups.get(umracSubmoduleSetups.size() - 1);
        assertThat(testUmracSubmoduleSetup.getSubModuleId()).isEqualTo(DEFAULT_SUB_MODULE_ID);
        assertThat(testUmracSubmoduleSetup.getSubModuleName()).isEqualTo(DEFAULT_SUB_MODULE_NAME);
        assertThat(testUmracSubmoduleSetup.getSubModuleUrl()).isEqualTo(DEFAULT_SUB_MODULE_URL);
        assertThat(testUmracSubmoduleSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUmracSubmoduleSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUmracSubmoduleSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testUmracSubmoduleSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testUmracSubmoduleSetup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUmracSubmoduleSetup.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void checkSubModuleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = umracSubmoduleSetupRepository.findAll().size();
        // set the field null
        umracSubmoduleSetup.setSubModuleName(null);

        // Create the UmracSubmoduleSetup, which fails.

        restUmracSubmoduleSetupMockMvc.perform(post("/api/umracSubmoduleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracSubmoduleSetup)))
                .andExpect(status().isBadRequest());

        List<UmracSubmoduleSetup> umracSubmoduleSetups = umracSubmoduleSetupRepository.findAll();
        assertThat(umracSubmoduleSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubModuleUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = umracSubmoduleSetupRepository.findAll().size();
        // set the field null
        umracSubmoduleSetup.setSubModuleUrl(null);

        // Create the UmracSubmoduleSetup, which fails.

        restUmracSubmoduleSetupMockMvc.perform(post("/api/umracSubmoduleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracSubmoduleSetup)))
                .andExpect(status().isBadRequest());

        List<UmracSubmoduleSetup> umracSubmoduleSetups = umracSubmoduleSetupRepository.findAll();
        assertThat(umracSubmoduleSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUmracSubmoduleSetups() throws Exception {
        // Initialize the database
        umracSubmoduleSetupRepository.saveAndFlush(umracSubmoduleSetup);

        // Get all the umracSubmoduleSetups
        restUmracSubmoduleSetupMockMvc.perform(get("/api/umracSubmoduleSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umracSubmoduleSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].subModuleId").value(hasItem(DEFAULT_SUB_MODULE_ID.toString())))
                .andExpect(jsonPath("$.[*].subModuleName").value(hasItem(DEFAULT_SUB_MODULE_NAME.toString())))
                .andExpect(jsonPath("$.[*].subModuleUrl").value(hasItem(DEFAULT_SUB_MODULE_URL.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getUmracSubmoduleSetup() throws Exception {
        // Initialize the database
        umracSubmoduleSetupRepository.saveAndFlush(umracSubmoduleSetup);

        // Get the umracSubmoduleSetup
        restUmracSubmoduleSetupMockMvc.perform(get("/api/umracSubmoduleSetups/{id}", umracSubmoduleSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umracSubmoduleSetup.getId().intValue()))
            .andExpect(jsonPath("$.subModuleId").value(DEFAULT_SUB_MODULE_ID.toString()))
            .andExpect(jsonPath("$.subModuleName").value(DEFAULT_SUB_MODULE_NAME.toString()))
            .andExpect(jsonPath("$.subModuleUrl").value(DEFAULT_SUB_MODULE_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUmracSubmoduleSetup() throws Exception {
        // Get the umracSubmoduleSetup
        restUmracSubmoduleSetupMockMvc.perform(get("/api/umracSubmoduleSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmracSubmoduleSetup() throws Exception {
        // Initialize the database
        umracSubmoduleSetupRepository.saveAndFlush(umracSubmoduleSetup);

		int databaseSizeBeforeUpdate = umracSubmoduleSetupRepository.findAll().size();

        // Update the umracSubmoduleSetup
        umracSubmoduleSetup.setSubModuleId(UPDATED_SUB_MODULE_ID);
        umracSubmoduleSetup.setSubModuleName(UPDATED_SUB_MODULE_NAME);
        umracSubmoduleSetup.setSubModuleUrl(UPDATED_SUB_MODULE_URL);
        umracSubmoduleSetup.setDescription(UPDATED_DESCRIPTION);
        umracSubmoduleSetup.setStatus(UPDATED_STATUS);
        umracSubmoduleSetup.setCreateDate(UPDATED_CREATE_DATE);
        umracSubmoduleSetup.setCreateBy(UPDATED_CREATE_BY);
        umracSubmoduleSetup.setUpdatedBy(UPDATED_UPDATED_BY);
        umracSubmoduleSetup.setUpdatedTime(UPDATED_UPDATED_TIME);

        restUmracSubmoduleSetupMockMvc.perform(put("/api/umracSubmoduleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracSubmoduleSetup)))
                .andExpect(status().isOk());

        // Validate the UmracSubmoduleSetup in the database
        List<UmracSubmoduleSetup> umracSubmoduleSetups = umracSubmoduleSetupRepository.findAll();
        assertThat(umracSubmoduleSetups).hasSize(databaseSizeBeforeUpdate);
        UmracSubmoduleSetup testUmracSubmoduleSetup = umracSubmoduleSetups.get(umracSubmoduleSetups.size() - 1);
        assertThat(testUmracSubmoduleSetup.getSubModuleId()).isEqualTo(UPDATED_SUB_MODULE_ID);
        assertThat(testUmracSubmoduleSetup.getSubModuleName()).isEqualTo(UPDATED_SUB_MODULE_NAME);
        assertThat(testUmracSubmoduleSetup.getSubModuleUrl()).isEqualTo(UPDATED_SUB_MODULE_URL);
        assertThat(testUmracSubmoduleSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUmracSubmoduleSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUmracSubmoduleSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testUmracSubmoduleSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testUmracSubmoduleSetup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUmracSubmoduleSetup.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteUmracSubmoduleSetup() throws Exception {
        // Initialize the database
        umracSubmoduleSetupRepository.saveAndFlush(umracSubmoduleSetup);

		int databaseSizeBeforeDelete = umracSubmoduleSetupRepository.findAll().size();

        // Get the umracSubmoduleSetup
        restUmracSubmoduleSetupMockMvc.perform(delete("/api/umracSubmoduleSetups/{id}", umracSubmoduleSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmracSubmoduleSetup> umracSubmoduleSetups = umracSubmoduleSetupRepository.findAll();
        assertThat(umracSubmoduleSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
