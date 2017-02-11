package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.NameCnclApplication;
import gov.step.app.repository.NameCnclApplicationRepository;
import gov.step.app.repository.search.NameCnclApplicationSearchRepository;

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
 * Test class for the NameCnclApplicationResource REST controller.
 *
 * @see NameCnclApplicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NameCnclApplicationResourceIntTest {


    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private NameCnclApplicationRepository nameCnclApplicationRepository;

    @Inject
    private NameCnclApplicationSearchRepository nameCnclApplicationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNameCnclApplicationMockMvc;

    private NameCnclApplication nameCnclApplication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NameCnclApplicationResource nameCnclApplicationResource = new NameCnclApplicationResource();
        ReflectionTestUtils.setField(nameCnclApplicationResource, "nameCnclApplicationRepository", nameCnclApplicationRepository);
        ReflectionTestUtils.setField(nameCnclApplicationResource, "nameCnclApplicationSearchRepository", nameCnclApplicationSearchRepository);
        this.restNameCnclApplicationMockMvc = MockMvcBuilders.standaloneSetup(nameCnclApplicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        nameCnclApplication = new NameCnclApplication();
        nameCnclApplication.setCreated_date(DEFAULT_CREATED_DATE);
        nameCnclApplication.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createNameCnclApplication() throws Exception {
        int databaseSizeBeforeCreate = nameCnclApplicationRepository.findAll().size();

        // Create the NameCnclApplication

        restNameCnclApplicationMockMvc.perform(post("/api/nameCnclApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nameCnclApplication)))
                .andExpect(status().isCreated());

        // Validate the NameCnclApplication in the database
        List<NameCnclApplication> nameCnclApplications = nameCnclApplicationRepository.findAll();
        assertThat(nameCnclApplications).hasSize(databaseSizeBeforeCreate + 1);
        NameCnclApplication testNameCnclApplication = nameCnclApplications.get(nameCnclApplications.size() - 1);
        assertThat(testNameCnclApplication.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNameCnclApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllNameCnclApplications() throws Exception {
        // Initialize the database
        nameCnclApplicationRepository.saveAndFlush(nameCnclApplication);

        // Get all the nameCnclApplications
        restNameCnclApplicationMockMvc.perform(get("/api/nameCnclApplications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(nameCnclApplication.getId().intValue())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getNameCnclApplication() throws Exception {
        // Initialize the database
        nameCnclApplicationRepository.saveAndFlush(nameCnclApplication);

        // Get the nameCnclApplication
        restNameCnclApplicationMockMvc.perform(get("/api/nameCnclApplications/{id}", nameCnclApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(nameCnclApplication.getId().intValue()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingNameCnclApplication() throws Exception {
        // Get the nameCnclApplication
        restNameCnclApplicationMockMvc.perform(get("/api/nameCnclApplications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNameCnclApplication() throws Exception {
        // Initialize the database
        nameCnclApplicationRepository.saveAndFlush(nameCnclApplication);

		int databaseSizeBeforeUpdate = nameCnclApplicationRepository.findAll().size();

        // Update the nameCnclApplication
        nameCnclApplication.setCreated_date(UPDATED_CREATED_DATE);
        nameCnclApplication.setStatus(UPDATED_STATUS);

        restNameCnclApplicationMockMvc.perform(put("/api/nameCnclApplications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nameCnclApplication)))
                .andExpect(status().isOk());

        // Validate the NameCnclApplication in the database
        List<NameCnclApplication> nameCnclApplications = nameCnclApplicationRepository.findAll();
        assertThat(nameCnclApplications).hasSize(databaseSizeBeforeUpdate);
        NameCnclApplication testNameCnclApplication = nameCnclApplications.get(nameCnclApplications.size() - 1);
        assertThat(testNameCnclApplication.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNameCnclApplication.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteNameCnclApplication() throws Exception {
        // Initialize the database
        nameCnclApplicationRepository.saveAndFlush(nameCnclApplication);

		int databaseSizeBeforeDelete = nameCnclApplicationRepository.findAll().size();

        // Get the nameCnclApplication
        restNameCnclApplicationMockMvc.perform(delete("/api/nameCnclApplications/{id}", nameCnclApplication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<NameCnclApplication> nameCnclApplications = nameCnclApplicationRepository.findAll();
        assertThat(nameCnclApplications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
