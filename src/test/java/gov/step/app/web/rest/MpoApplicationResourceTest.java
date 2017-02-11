package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoApplication;
import gov.step.app.repository.MpoApplicationRepository;
import gov.step.app.repository.search.MpoApplicationSearchRepository;

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
 * Test class for the MpoApplicationResource REST controller.
 *
 * @see MpoApplicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoApplicationResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private MpoApplicationRepository mpoApplicationRepository;

    @Inject
    private MpoApplicationSearchRepository mpoApplicationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoApplicationMockMvc;

    private MpoApplication mpoApplication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoApplicationResource mpoApplicationResource = new MpoApplicationResource();
        ReflectionTestUtils.setField(mpoApplicationResource, "mpoApplicationRepository", mpoApplicationRepository);
        ReflectionTestUtils.setField(mpoApplicationResource, "mpoApplicationSearchRepository", mpoApplicationSearchRepository);
        this.restMpoApplicationMockMvc = MockMvcBuilders.standaloneSetup(mpoApplicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoApplication = new MpoApplication();
        mpoApplication.setMpoApplicationDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createMpoApplication() throws Exception {
        int databaseSizeBeforeCreate = mpoApplicationRepository.findAll().size();

        // Create the MpoApplication

        restMpoApplicationMockMvc.perform(post("/api/mpoApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoApplication)))
                .andExpect(status().isCreated());

        // Validate the MpoApplication in the database
        List<MpoApplication> mpoApplications = mpoApplicationRepository.findAll();
        assertThat(mpoApplications).hasSize(databaseSizeBeforeCreate + 1);
        MpoApplication testMpoApplication = mpoApplications.get(mpoApplications.size() - 1);
        assertThat(testMpoApplication.getMpoApplicationDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mpoApplicationRepository.findAll().size();
        // set the field null

        // Create the MpoApplication, which fails.

        restMpoApplicationMockMvc.perform(post("/api/mpoApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoApplication)))
                .andExpect(status().isBadRequest());

        List<MpoApplication> mpoApplications = mpoApplicationRepository.findAll();
        assertThat(mpoApplications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMpoApplications() throws Exception {
        // Initialize the database
        mpoApplicationRepository.saveAndFlush(mpoApplication);

        // Get all the mpoApplications
        restMpoApplicationMockMvc.perform(get("/api/mpoApplications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoApplication.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMpoApplication() throws Exception {
        // Initialize the database
        mpoApplicationRepository.saveAndFlush(mpoApplication);

        // Get the mpoApplication
        restMpoApplicationMockMvc.perform(get("/api/mpoApplications/{id}", mpoApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoApplication.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMpoApplication() throws Exception {
        // Get the mpoApplication
        restMpoApplicationMockMvc.perform(get("/api/mpoApplications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoApplication() throws Exception {
        // Initialize the database
        mpoApplicationRepository.saveAndFlush(mpoApplication);

		int databaseSizeBeforeUpdate = mpoApplicationRepository.findAll().size();

        // Update the mpoApplication
        mpoApplication.setMpoApplicationDate(UPDATED_DATE);

        restMpoApplicationMockMvc.perform(put("/api/mpoApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoApplication)))
                .andExpect(status().isOk());

        // Validate the MpoApplication in the database
        List<MpoApplication> mpoApplications = mpoApplicationRepository.findAll();
        assertThat(mpoApplications).hasSize(databaseSizeBeforeUpdate);
        MpoApplication testMpoApplication = mpoApplications.get(mpoApplications.size() - 1);
        assertThat(testMpoApplication.getMpoApplicationDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteMpoApplication() throws Exception {
        // Initialize the database
        mpoApplicationRepository.saveAndFlush(mpoApplication);

		int databaseSizeBeforeDelete = mpoApplicationRepository.findAll().size();

        // Get the mpoApplication
        restMpoApplicationMockMvc.perform(delete("/api/mpoApplications/{id}", mpoApplication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoApplication> mpoApplications = mpoApplicationRepository.findAll();
        assertThat(mpoApplications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
