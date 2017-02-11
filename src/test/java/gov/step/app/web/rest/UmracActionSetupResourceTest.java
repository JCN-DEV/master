package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.UmracActionSetup;
import gov.step.app.repository.UmracActionSetupRepository;
import gov.step.app.repository.search.UmracActionSetupSearchRepository;

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
 * Test class for the UmracActionSetupResource REST controller.
 *
 * @see UmracActionSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UmracActionSetupResourceTest {

    private static final String DEFAULT_ACTION_ID = "AAAAA";
    private static final String UPDATED_ACTION_ID = "BBBBB";
    private static final String DEFAULT_ACTION_NAME = "AAAAA";
    private static final String UPDATED_ACTION_NAME = "BBBBB";
    private static final String DEFAULT_ACTION_URL = "AAAAA";
    private static final String UPDATED_ACTION_URL = "BBBBB";
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
    private UmracActionSetupRepository umracActionSetupRepository;

    @Inject
    private UmracActionSetupSearchRepository umracActionSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmracActionSetupMockMvc;

    private UmracActionSetup umracActionSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmracActionSetupResource umracActionSetupResource = new UmracActionSetupResource();
        ReflectionTestUtils.setField(umracActionSetupResource, "umracActionSetupRepository", umracActionSetupRepository);
        ReflectionTestUtils.setField(umracActionSetupResource, "umracActionSetupSearchRepository", umracActionSetupSearchRepository);
        this.restUmracActionSetupMockMvc = MockMvcBuilders.standaloneSetup(umracActionSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umracActionSetup = new UmracActionSetup();
        umracActionSetup.setActionId(DEFAULT_ACTION_ID);
        umracActionSetup.setActionName(DEFAULT_ACTION_NAME);
        umracActionSetup.setActionUrl(DEFAULT_ACTION_URL);
        umracActionSetup.setDescription(DEFAULT_DESCRIPTION);
        umracActionSetup.setStatus(DEFAULT_STATUS);
        umracActionSetup.setCreateDate(DEFAULT_CREATE_DATE);
        umracActionSetup.setCreateBy(DEFAULT_CREATE_BY);
        umracActionSetup.setUpdatedBy(DEFAULT_UPDATED_BY);
        umracActionSetup.setUpdatedTime(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createUmracActionSetup() throws Exception {
        int databaseSizeBeforeCreate = umracActionSetupRepository.findAll().size();

        // Create the UmracActionSetup

        restUmracActionSetupMockMvc.perform(post("/api/umracActionSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracActionSetup)))
                .andExpect(status().isCreated());

        // Validate the UmracActionSetup in the database
        List<UmracActionSetup> umracActionSetups = umracActionSetupRepository.findAll();
        assertThat(umracActionSetups).hasSize(databaseSizeBeforeCreate + 1);
        UmracActionSetup testUmracActionSetup = umracActionSetups.get(umracActionSetups.size() - 1);
        assertThat(testUmracActionSetup.getActionId()).isEqualTo(DEFAULT_ACTION_ID);
        assertThat(testUmracActionSetup.getActionName()).isEqualTo(DEFAULT_ACTION_NAME);
        assertThat(testUmracActionSetup.getActionUrl()).isEqualTo(DEFAULT_ACTION_URL);
        assertThat(testUmracActionSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUmracActionSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUmracActionSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testUmracActionSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testUmracActionSetup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUmracActionSetup.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void checkActionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = umracActionSetupRepository.findAll().size();
        // set the field null
        umracActionSetup.setActionName(null);

        // Create the UmracActionSetup, which fails.

        restUmracActionSetupMockMvc.perform(post("/api/umracActionSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracActionSetup)))
                .andExpect(status().isBadRequest());

        List<UmracActionSetup> umracActionSetups = umracActionSetupRepository.findAll();
        assertThat(umracActionSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = umracActionSetupRepository.findAll().size();
        // set the field null
        umracActionSetup.setActionUrl(null);

        // Create the UmracActionSetup, which fails.

        restUmracActionSetupMockMvc.perform(post("/api/umracActionSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracActionSetup)))
                .andExpect(status().isBadRequest());

        List<UmracActionSetup> umracActionSetups = umracActionSetupRepository.findAll();
        assertThat(umracActionSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUmracActionSetups() throws Exception {
        // Initialize the database
        umracActionSetupRepository.saveAndFlush(umracActionSetup);

        // Get all the umracActionSetups
        restUmracActionSetupMockMvc.perform(get("/api/umracActionSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umracActionSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].actionId").value(hasItem(DEFAULT_ACTION_ID.toString())))
                .andExpect(jsonPath("$.[*].actionName").value(hasItem(DEFAULT_ACTION_NAME.toString())))
                .andExpect(jsonPath("$.[*].actionUrl").value(hasItem(DEFAULT_ACTION_URL.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getUmracActionSetup() throws Exception {
        // Initialize the database
        umracActionSetupRepository.saveAndFlush(umracActionSetup);

        // Get the umracActionSetup
        restUmracActionSetupMockMvc.perform(get("/api/umracActionSetups/{id}", umracActionSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umracActionSetup.getId().intValue()))
            .andExpect(jsonPath("$.actionId").value(DEFAULT_ACTION_ID.toString()))
            .andExpect(jsonPath("$.actionName").value(DEFAULT_ACTION_NAME.toString()))
            .andExpect(jsonPath("$.actionUrl").value(DEFAULT_ACTION_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUmracActionSetup() throws Exception {
        // Get the umracActionSetup
        restUmracActionSetupMockMvc.perform(get("/api/umracActionSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmracActionSetup() throws Exception {
        // Initialize the database
        umracActionSetupRepository.saveAndFlush(umracActionSetup);

		int databaseSizeBeforeUpdate = umracActionSetupRepository.findAll().size();

        // Update the umracActionSetup
        umracActionSetup.setActionId(UPDATED_ACTION_ID);
        umracActionSetup.setActionName(UPDATED_ACTION_NAME);
        umracActionSetup.setActionUrl(UPDATED_ACTION_URL);
        umracActionSetup.setDescription(UPDATED_DESCRIPTION);
        umracActionSetup.setStatus(UPDATED_STATUS);
        umracActionSetup.setCreateDate(UPDATED_CREATE_DATE);
        umracActionSetup.setCreateBy(UPDATED_CREATE_BY);
        umracActionSetup.setUpdatedBy(UPDATED_UPDATED_BY);
        umracActionSetup.setUpdatedTime(UPDATED_UPDATED_TIME);

        restUmracActionSetupMockMvc.perform(put("/api/umracActionSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracActionSetup)))
                .andExpect(status().isOk());

        // Validate the UmracActionSetup in the database
        List<UmracActionSetup> umracActionSetups = umracActionSetupRepository.findAll();
        assertThat(umracActionSetups).hasSize(databaseSizeBeforeUpdate);
        UmracActionSetup testUmracActionSetup = umracActionSetups.get(umracActionSetups.size() - 1);
        assertThat(testUmracActionSetup.getActionId()).isEqualTo(UPDATED_ACTION_ID);
        assertThat(testUmracActionSetup.getActionName()).isEqualTo(UPDATED_ACTION_NAME);
        assertThat(testUmracActionSetup.getActionUrl()).isEqualTo(UPDATED_ACTION_URL);
        assertThat(testUmracActionSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUmracActionSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUmracActionSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testUmracActionSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testUmracActionSetup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUmracActionSetup.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteUmracActionSetup() throws Exception {
        // Initialize the database
        umracActionSetupRepository.saveAndFlush(umracActionSetup);

		int databaseSizeBeforeDelete = umracActionSetupRepository.findAll().size();

        // Get the umracActionSetup
        restUmracActionSetupMockMvc.perform(delete("/api/umracActionSetups/{id}", umracActionSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmracActionSetup> umracActionSetups = umracActionSetupRepository.findAll();
        assertThat(umracActionSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
