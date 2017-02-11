package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmplPayscaleHist;
import gov.step.app.repository.InstEmplPayscaleHistRepository;
import gov.step.app.repository.search.InstEmplPayscaleHistSearchRepository;

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
 * Test class for the InstEmplPayscaleHistResource REST controller.
 *
 * @see InstEmplPayscaleHistResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmplPayscaleHistResourceIntTest {


    private static final LocalDate DEFAULT_ACTIVATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private InstEmplPayscaleHistRepository instEmplPayscaleHistRepository;

    @Inject
    private InstEmplPayscaleHistSearchRepository instEmplPayscaleHistSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmplPayscaleHistMockMvc;

    private InstEmplPayscaleHist instEmplPayscaleHist;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmplPayscaleHistResource instEmplPayscaleHistResource = new InstEmplPayscaleHistResource();
        ReflectionTestUtils.setField(instEmplPayscaleHistResource, "instEmplPayscaleHistRepository", instEmplPayscaleHistRepository);
        ReflectionTestUtils.setField(instEmplPayscaleHistResource, "instEmplPayscaleHistSearchRepository", instEmplPayscaleHistSearchRepository);
        this.restInstEmplPayscaleHistMockMvc = MockMvcBuilders.standaloneSetup(instEmplPayscaleHistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmplPayscaleHist = new InstEmplPayscaleHist();
        instEmplPayscaleHist.setActivationDate(DEFAULT_ACTIVATION_DATE);
        instEmplPayscaleHist.setEndDate(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createInstEmplPayscaleHist() throws Exception {
        int databaseSizeBeforeCreate = instEmplPayscaleHistRepository.findAll().size();

        // Create the InstEmplPayscaleHist

        restInstEmplPayscaleHistMockMvc.perform(post("/api/instEmplPayscaleHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplPayscaleHist)))
                .andExpect(status().isCreated());

        // Validate the InstEmplPayscaleHist in the database
        List<InstEmplPayscaleHist> instEmplPayscaleHists = instEmplPayscaleHistRepository.findAll();
        assertThat(instEmplPayscaleHists).hasSize(databaseSizeBeforeCreate + 1);
        InstEmplPayscaleHist testInstEmplPayscaleHist = instEmplPayscaleHists.get(instEmplPayscaleHists.size() - 1);
        assertThat(testInstEmplPayscaleHist.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);
        assertThat(testInstEmplPayscaleHist.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllInstEmplPayscaleHists() throws Exception {
        // Initialize the database
        instEmplPayscaleHistRepository.saveAndFlush(instEmplPayscaleHist);

        // Get all the instEmplPayscaleHists
        restInstEmplPayscaleHistMockMvc.perform(get("/api/instEmplPayscaleHists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmplPayscaleHist.getId().intValue())))
                .andExpect(jsonPath("$.[*].activationDate").value(hasItem(DEFAULT_ACTIVATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInstEmplPayscaleHist() throws Exception {
        // Initialize the database
        instEmplPayscaleHistRepository.saveAndFlush(instEmplPayscaleHist);

        // Get the instEmplPayscaleHist
        restInstEmplPayscaleHistMockMvc.perform(get("/api/instEmplPayscaleHists/{id}", instEmplPayscaleHist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmplPayscaleHist.getId().intValue()))
            .andExpect(jsonPath("$.activationDate").value(DEFAULT_ACTIVATION_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmplPayscaleHist() throws Exception {
        // Get the instEmplPayscaleHist
        restInstEmplPayscaleHistMockMvc.perform(get("/api/instEmplPayscaleHists/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmplPayscaleHist() throws Exception {
        // Initialize the database
        instEmplPayscaleHistRepository.saveAndFlush(instEmplPayscaleHist);

		int databaseSizeBeforeUpdate = instEmplPayscaleHistRepository.findAll().size();

        // Update the instEmplPayscaleHist
        instEmplPayscaleHist.setActivationDate(UPDATED_ACTIVATION_DATE);
        instEmplPayscaleHist.setEndDate(UPDATED_END_DATE);

        restInstEmplPayscaleHistMockMvc.perform(put("/api/instEmplPayscaleHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplPayscaleHist)))
                .andExpect(status().isOk());

        // Validate the InstEmplPayscaleHist in the database
        List<InstEmplPayscaleHist> instEmplPayscaleHists = instEmplPayscaleHistRepository.findAll();
        assertThat(instEmplPayscaleHists).hasSize(databaseSizeBeforeUpdate);
        InstEmplPayscaleHist testInstEmplPayscaleHist = instEmplPayscaleHists.get(instEmplPayscaleHists.size() - 1);
        assertThat(testInstEmplPayscaleHist.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);
        assertThat(testInstEmplPayscaleHist.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void deleteInstEmplPayscaleHist() throws Exception {
        // Initialize the database
        instEmplPayscaleHistRepository.saveAndFlush(instEmplPayscaleHist);

		int databaseSizeBeforeDelete = instEmplPayscaleHistRepository.findAll().size();

        // Get the instEmplPayscaleHist
        restInstEmplPayscaleHistMockMvc.perform(delete("/api/instEmplPayscaleHists/{id}", instEmplPayscaleHist.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmplPayscaleHist> instEmplPayscaleHists = instEmplPayscaleHistRepository.findAll();
        assertThat(instEmplPayscaleHists).hasSize(databaseSizeBeforeDelete - 1);
    }
}
