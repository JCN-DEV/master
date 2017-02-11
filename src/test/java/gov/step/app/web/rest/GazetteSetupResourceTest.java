package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.GazetteSetup;
import gov.step.app.repository.GazetteSetupRepository;
import gov.step.app.repository.search.GazetteSetupSearchRepository;

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
 * Test class for the GazetteSetupResource REST controller.
 *
 * @see GazetteSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GazetteSetupResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

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

    @Inject
    private GazetteSetupRepository gazetteSetupRepository;

    @Inject
    private GazetteSetupSearchRepository gazetteSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGazetteSetupMockMvc;

    private GazetteSetup gazetteSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GazetteSetupResource gazetteSetupResource = new GazetteSetupResource();
        ReflectionTestUtils.setField(gazetteSetupResource, "gazetteSetupRepository", gazetteSetupRepository);
        ReflectionTestUtils.setField(gazetteSetupResource, "gazetteSetupSearchRepository", gazetteSetupSearchRepository);
        this.restGazetteSetupMockMvc = MockMvcBuilders.standaloneSetup(gazetteSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        gazetteSetup = new GazetteSetup();
        gazetteSetup.setName(DEFAULT_NAME);
        gazetteSetup.setStatus(DEFAULT_STATUS);
        gazetteSetup.setCreateBy(DEFAULT_CREATE_BY);
        gazetteSetup.setCreateDate(DEFAULT_CREATE_DATE);
        gazetteSetup.setUpdateBy(DEFAULT_UPDATE_BY);
        gazetteSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        gazetteSetup.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createGazetteSetup() throws Exception {
        int databaseSizeBeforeCreate = gazetteSetupRepository.findAll().size();

        // Create the GazetteSetup

        restGazetteSetupMockMvc.perform(post("/api/gazetteSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gazetteSetup)))
                .andExpect(status().isCreated());

        // Validate the GazetteSetup in the database
        List<GazetteSetup> gazetteSetups = gazetteSetupRepository.findAll();
        assertThat(gazetteSetups).hasSize(databaseSizeBeforeCreate + 1);
        GazetteSetup testGazetteSetup = gazetteSetups.get(gazetteSetups.size() - 1);
        assertThat(testGazetteSetup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGazetteSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testGazetteSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testGazetteSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testGazetteSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testGazetteSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testGazetteSetup.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gazetteSetupRepository.findAll().size();
        // set the field null
        gazetteSetup.setName(null);

        // Create the GazetteSetup, which fails.

        restGazetteSetupMockMvc.perform(post("/api/gazetteSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gazetteSetup)))
                .andExpect(status().isBadRequest());

        List<GazetteSetup> gazetteSetups = gazetteSetupRepository.findAll();
        assertThat(gazetteSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGazetteSetups() throws Exception {
        // Initialize the database
        gazetteSetupRepository.saveAndFlush(gazetteSetup);

        // Get all the gazetteSetups
        restGazetteSetupMockMvc.perform(get("/api/gazetteSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(gazetteSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getGazetteSetup() throws Exception {
        // Initialize the database
        gazetteSetupRepository.saveAndFlush(gazetteSetup);

        // Get the gazetteSetup
        restGazetteSetupMockMvc.perform(get("/api/gazetteSetups/{id}", gazetteSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(gazetteSetup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGazetteSetup() throws Exception {
        // Get the gazetteSetup
        restGazetteSetupMockMvc.perform(get("/api/gazetteSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGazetteSetup() throws Exception {
        // Initialize the database
        gazetteSetupRepository.saveAndFlush(gazetteSetup);

		int databaseSizeBeforeUpdate = gazetteSetupRepository.findAll().size();

        // Update the gazetteSetup
        gazetteSetup.setName(UPDATED_NAME);
        gazetteSetup.setStatus(UPDATED_STATUS);
        gazetteSetup.setCreateBy(UPDATED_CREATE_BY);
        gazetteSetup.setCreateDate(UPDATED_CREATE_DATE);
        gazetteSetup.setUpdateBy(UPDATED_UPDATE_BY);
        gazetteSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        gazetteSetup.setRemarks(UPDATED_REMARKS);

        restGazetteSetupMockMvc.perform(put("/api/gazetteSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gazetteSetup)))
                .andExpect(status().isOk());

        // Validate the GazetteSetup in the database
        List<GazetteSetup> gazetteSetups = gazetteSetupRepository.findAll();
        assertThat(gazetteSetups).hasSize(databaseSizeBeforeUpdate);
        GazetteSetup testGazetteSetup = gazetteSetups.get(gazetteSetups.size() - 1);
        assertThat(testGazetteSetup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGazetteSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testGazetteSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testGazetteSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testGazetteSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testGazetteSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testGazetteSetup.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteGazetteSetup() throws Exception {
        // Initialize the database
        gazetteSetupRepository.saveAndFlush(gazetteSetup);

		int databaseSizeBeforeDelete = gazetteSetupRepository.findAll().size();

        // Get the gazetteSetup
        restGazetteSetupMockMvc.perform(delete("/api/gazetteSetups/{id}", gazetteSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GazetteSetup> gazetteSetups = gazetteSetupRepository.findAll();
        assertThat(gazetteSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
