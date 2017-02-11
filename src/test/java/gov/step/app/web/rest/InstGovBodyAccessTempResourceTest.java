package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstGovBodyAccessTemp;
import gov.step.app.repository.InstGovBodyAccessTempRepository;
import gov.step.app.repository.search.InstGovBodyAccessTempSearchRepository;

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
 * Test class for the InstGovBodyAccessTempResource REST controller.
 *
 * @see InstGovBodyAccessTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstGovBodyAccessTempResourceTest {


    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstGovBodyAccessTempRepository instGovBodyAccessTempRepository;

    @Inject
    private InstGovBodyAccessTempSearchRepository instGovBodyAccessTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstGovBodyAccessTempMockMvc;

    private InstGovBodyAccessTemp instGovBodyAccessTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstGovBodyAccessTempResource instGovBodyAccessTempResource = new InstGovBodyAccessTempResource();
        ReflectionTestUtils.setField(instGovBodyAccessTempResource, "instGovBodyAccessTempRepository", instGovBodyAccessTempRepository);
        ReflectionTestUtils.setField(instGovBodyAccessTempResource, "instGovBodyAccessTempSearchRepository", instGovBodyAccessTempSearchRepository);
        this.restInstGovBodyAccessTempMockMvc = MockMvcBuilders.standaloneSetup(instGovBodyAccessTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instGovBodyAccessTemp = new InstGovBodyAccessTemp();
        instGovBodyAccessTemp.setDateCreated(DEFAULT_DATE_CREATED);
        instGovBodyAccessTemp.setDateModified(DEFAULT_DATE_MODIFIED);
        instGovBodyAccessTemp.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstGovBodyAccessTemp() throws Exception {
        int databaseSizeBeforeCreate = instGovBodyAccessTempRepository.findAll().size();

        // Create the InstGovBodyAccessTemp

        restInstGovBodyAccessTempMockMvc.perform(post("/api/instGovBodyAccessTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovBodyAccessTemp)))
                .andExpect(status().isCreated());

        // Validate the InstGovBodyAccessTemp in the database
        List<InstGovBodyAccessTemp> instGovBodyAccessTemps = instGovBodyAccessTempRepository.findAll();
        assertThat(instGovBodyAccessTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstGovBodyAccessTemp testInstGovBodyAccessTemp = instGovBodyAccessTemps.get(instGovBodyAccessTemps.size() - 1);
        assertThat(testInstGovBodyAccessTemp.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testInstGovBodyAccessTemp.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testInstGovBodyAccessTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGovBodyAccessTempRepository.findAll().size();
        // set the field null
        instGovBodyAccessTemp.setDateCreated(null);

        // Create the InstGovBodyAccessTemp, which fails.

        restInstGovBodyAccessTempMockMvc.perform(post("/api/instGovBodyAccessTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovBodyAccessTemp)))
                .andExpect(status().isBadRequest());

        List<InstGovBodyAccessTemp> instGovBodyAccessTemps = instGovBodyAccessTempRepository.findAll();
        assertThat(instGovBodyAccessTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGovBodyAccessTempRepository.findAll().size();
        // set the field null
        instGovBodyAccessTemp.setDateModified(null);

        // Create the InstGovBodyAccessTemp, which fails.

        restInstGovBodyAccessTempMockMvc.perform(post("/api/instGovBodyAccessTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovBodyAccessTemp)))
                .andExpect(status().isBadRequest());

        List<InstGovBodyAccessTemp> instGovBodyAccessTemps = instGovBodyAccessTempRepository.findAll();
        assertThat(instGovBodyAccessTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstGovBodyAccessTemps() throws Exception {
        // Initialize the database
        instGovBodyAccessTempRepository.saveAndFlush(instGovBodyAccessTemp);

        // Get all the instGovBodyAccessTemps
        restInstGovBodyAccessTempMockMvc.perform(get("/api/instGovBodyAccessTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instGovBodyAccessTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstGovBodyAccessTemp() throws Exception {
        // Initialize the database
        instGovBodyAccessTempRepository.saveAndFlush(instGovBodyAccessTemp);

        // Get the instGovBodyAccessTemp
        restInstGovBodyAccessTempMockMvc.perform(get("/api/instGovBodyAccessTemps/{id}", instGovBodyAccessTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instGovBodyAccessTemp.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstGovBodyAccessTemp() throws Exception {
        // Get the instGovBodyAccessTemp
        restInstGovBodyAccessTempMockMvc.perform(get("/api/instGovBodyAccessTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstGovBodyAccessTemp() throws Exception {
        // Initialize the database
        instGovBodyAccessTempRepository.saveAndFlush(instGovBodyAccessTemp);

		int databaseSizeBeforeUpdate = instGovBodyAccessTempRepository.findAll().size();

        // Update the instGovBodyAccessTemp
        instGovBodyAccessTemp.setDateCreated(UPDATED_DATE_CREATED);
        instGovBodyAccessTemp.setDateModified(UPDATED_DATE_MODIFIED);
        instGovBodyAccessTemp.setStatus(UPDATED_STATUS);

        restInstGovBodyAccessTempMockMvc.perform(put("/api/instGovBodyAccessTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovBodyAccessTemp)))
                .andExpect(status().isOk());

        // Validate the InstGovBodyAccessTemp in the database
        List<InstGovBodyAccessTemp> instGovBodyAccessTemps = instGovBodyAccessTempRepository.findAll();
        assertThat(instGovBodyAccessTemps).hasSize(databaseSizeBeforeUpdate);
        InstGovBodyAccessTemp testInstGovBodyAccessTemp = instGovBodyAccessTemps.get(instGovBodyAccessTemps.size() - 1);
        assertThat(testInstGovBodyAccessTemp.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testInstGovBodyAccessTemp.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testInstGovBodyAccessTemp.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstGovBodyAccessTemp() throws Exception {
        // Initialize the database
        instGovBodyAccessTempRepository.saveAndFlush(instGovBodyAccessTemp);

		int databaseSizeBeforeDelete = instGovBodyAccessTempRepository.findAll().size();

        // Get the instGovBodyAccessTemp
        restInstGovBodyAccessTempMockMvc.perform(delete("/api/instGovBodyAccessTemps/{id}", instGovBodyAccessTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstGovBodyAccessTemp> instGovBodyAccessTemps = instGovBodyAccessTempRepository.findAll();
        assertThat(instGovBodyAccessTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
