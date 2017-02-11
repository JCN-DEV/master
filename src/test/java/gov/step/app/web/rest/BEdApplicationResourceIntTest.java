package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.BEdApplication;
import gov.step.app.repository.BEdApplicationRepository;
import gov.step.app.repository.search.BEdApplicationSearchRepository;

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
 * Test class for the BEdApplicationResource REST controller.
 *
 * @see BEdApplicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BEdApplicationResourceIntTest {


    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private BEdApplicationRepository bEdApplicationRepository;

    @Inject
    private BEdApplicationSearchRepository bEdApplicationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBEdApplicationMockMvc;

    private BEdApplication bEdApplication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BEdApplicationResource bEdApplicationResource = new BEdApplicationResource();
        ReflectionTestUtils.setField(bEdApplicationResource, "bEdApplicationRepository", bEdApplicationRepository);
        ReflectionTestUtils.setField(bEdApplicationResource, "bEdApplicationSearchRepository", bEdApplicationSearchRepository);
        this.restBEdApplicationMockMvc = MockMvcBuilders.standaloneSetup(bEdApplicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bEdApplication = new BEdApplication();
        bEdApplication.setCreatedDate(DEFAULT_CREATED_DATE);
        bEdApplication.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBEdApplication() throws Exception {
        int databaseSizeBeforeCreate = bEdApplicationRepository.findAll().size();

        // Create the BEdApplication

        restBEdApplicationMockMvc.perform(post("/api/bEdApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bEdApplication)))
                .andExpect(status().isCreated());

        // Validate the BEdApplication in the database
        List<BEdApplication> bEdApplications = bEdApplicationRepository.findAll();
        assertThat(bEdApplications).hasSize(databaseSizeBeforeCreate + 1);
        BEdApplication testBEdApplication = bEdApplications.get(bEdApplications.size() - 1);
        assertThat(testBEdApplication.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBEdApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllBEdApplications() throws Exception {
        // Initialize the database
        bEdApplicationRepository.saveAndFlush(bEdApplication);

        // Get all the bEdApplications
        restBEdApplicationMockMvc.perform(get("/api/bEdApplications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bEdApplication.getId().intValue())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getBEdApplication() throws Exception {
        // Initialize the database
        bEdApplicationRepository.saveAndFlush(bEdApplication);

        // Get the bEdApplication
        restBEdApplicationMockMvc.perform(get("/api/bEdApplications/{id}", bEdApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bEdApplication.getId().intValue()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingBEdApplication() throws Exception {
        // Get the bEdApplication
        restBEdApplicationMockMvc.perform(get("/api/bEdApplications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBEdApplication() throws Exception {
        // Initialize the database
        bEdApplicationRepository.saveAndFlush(bEdApplication);

		int databaseSizeBeforeUpdate = bEdApplicationRepository.findAll().size();

        // Update the bEdApplication
        bEdApplication.setCreatedDate(UPDATED_CREATED_DATE);
        bEdApplication.setStatus(UPDATED_STATUS);

        restBEdApplicationMockMvc.perform(put("/api/bEdApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bEdApplication)))
                .andExpect(status().isOk());

        // Validate the BEdApplication in the database
        List<BEdApplication> bEdApplications = bEdApplicationRepository.findAll();
        assertThat(bEdApplications).hasSize(databaseSizeBeforeUpdate);
        BEdApplication testBEdApplication = bEdApplications.get(bEdApplications.size() - 1);
        assertThat(testBEdApplication.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBEdApplication.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteBEdApplication() throws Exception {
        // Initialize the database
        bEdApplicationRepository.saveAndFlush(bEdApplication);

		int databaseSizeBeforeDelete = bEdApplicationRepository.findAll().size();

        // Get the bEdApplication
        restBEdApplicationMockMvc.perform(delete("/api/bEdApplications/{id}", bEdApplication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BEdApplication> bEdApplications = bEdApplicationRepository.findAll();
        assertThat(bEdApplications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
