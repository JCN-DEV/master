package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstVacancyTemp;
import gov.step.app.repository.InstVacancyTempRepository;
import gov.step.app.repository.search.InstVacancyTempSearchRepository;

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

import gov.step.app.domain.enumeration.EmpTypes;

/**
 * Test class for the InstVacancyTempResource REST controller.
 *
 * @see InstVacancyTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstVacancyTempResourceTest {


    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;


private static final EmpTypes DEFAULT_EMP_TYPE = EmpTypes.Teacher;
    private static final EmpTypes UPDATED_EMP_TYPE = EmpTypes.Staff;

    private static final Integer DEFAULT_TOTAL_VACANCY = 1;
    private static final Integer UPDATED_TOTAL_VACANCY = 2;
    private static final String DEFAULT_FILLED_UP_VACANCY = "AAAAA";
    private static final String UPDATED_FILLED_UP_VACANCY = "BBBBB";

    @Inject
    private InstVacancyTempRepository instVacancyTempRepository;

    @Inject
    private InstVacancyTempSearchRepository instVacancyTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstVacancyTempMockMvc;

    private InstVacancyTemp instVacancyTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstVacancyTempResource instVacancyTempResource = new InstVacancyTempResource();
        ReflectionTestUtils.setField(instVacancyTempResource, "instVacancyTempRepository", instVacancyTempRepository);
        ReflectionTestUtils.setField(instVacancyTempResource, "instVacancyTempSearchRepository", instVacancyTempSearchRepository);
        this.restInstVacancyTempMockMvc = MockMvcBuilders.standaloneSetup(instVacancyTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instVacancyTemp = new InstVacancyTemp();
        instVacancyTemp.setDateCreated(DEFAULT_DATE_CREATED);
        instVacancyTemp.setDateModified(DEFAULT_DATE_MODIFIED);
        instVacancyTemp.setStatus(DEFAULT_STATUS);
        instVacancyTemp.setEmpType(DEFAULT_EMP_TYPE);
        instVacancyTemp.setTotalVacancy(DEFAULT_TOTAL_VACANCY);
        instVacancyTemp.setFilledUpVacancy(DEFAULT_FILLED_UP_VACANCY);
    }

    @Test
    @Transactional
    public void createInstVacancyTemp() throws Exception {
        int databaseSizeBeforeCreate = instVacancyTempRepository.findAll().size();

        // Create the InstVacancyTemp

        restInstVacancyTempMockMvc.perform(post("/api/instVacancyTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instVacancyTemp)))
                .andExpect(status().isCreated());

        // Validate the InstVacancyTemp in the database
        List<InstVacancyTemp> instVacancyTemps = instVacancyTempRepository.findAll();
        assertThat(instVacancyTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstVacancyTemp testInstVacancyTemp = instVacancyTemps.get(instVacancyTemps.size() - 1);
        assertThat(testInstVacancyTemp.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testInstVacancyTemp.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testInstVacancyTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInstVacancyTemp.getEmpType()).isEqualTo(DEFAULT_EMP_TYPE);
        assertThat(testInstVacancyTemp.getTotalVacancy()).isEqualTo(DEFAULT_TOTAL_VACANCY);
        assertThat(testInstVacancyTemp.getFilledUpVacancy()).isEqualTo(DEFAULT_FILLED_UP_VACANCY);
    }

    @Test
    @Transactional
    public void getAllInstVacancyTemps() throws Exception {
        // Initialize the database
        instVacancyTempRepository.saveAndFlush(instVacancyTemp);

        // Get all the instVacancyTemps
        restInstVacancyTempMockMvc.perform(get("/api/instVacancyTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instVacancyTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].empType").value(hasItem(DEFAULT_EMP_TYPE.toString())))
                .andExpect(jsonPath("$.[*].totalVacancy").value(hasItem(DEFAULT_TOTAL_VACANCY)))
                .andExpect(jsonPath("$.[*].filledUpVacancy").value(hasItem(DEFAULT_FILLED_UP_VACANCY.toString())));
    }

    @Test
    @Transactional
    public void getInstVacancyTemp() throws Exception {
        // Initialize the database
        instVacancyTempRepository.saveAndFlush(instVacancyTemp);

        // Get the instVacancyTemp
        restInstVacancyTempMockMvc.perform(get("/api/instVacancyTemps/{id}", instVacancyTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instVacancyTemp.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.empType").value(DEFAULT_EMP_TYPE.toString()))
            .andExpect(jsonPath("$.totalVacancy").value(DEFAULT_TOTAL_VACANCY))
            .andExpect(jsonPath("$.filledUpVacancy").value(DEFAULT_FILLED_UP_VACANCY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstVacancyTemp() throws Exception {
        // Get the instVacancyTemp
        restInstVacancyTempMockMvc.perform(get("/api/instVacancyTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstVacancyTemp() throws Exception {
        // Initialize the database
        instVacancyTempRepository.saveAndFlush(instVacancyTemp);

		int databaseSizeBeforeUpdate = instVacancyTempRepository.findAll().size();

        // Update the instVacancyTemp
        instVacancyTemp.setDateCreated(UPDATED_DATE_CREATED);
        instVacancyTemp.setDateModified(UPDATED_DATE_MODIFIED);
        instVacancyTemp.setStatus(UPDATED_STATUS);
        instVacancyTemp.setEmpType(UPDATED_EMP_TYPE);
        instVacancyTemp.setTotalVacancy(UPDATED_TOTAL_VACANCY);
        instVacancyTemp.setFilledUpVacancy(UPDATED_FILLED_UP_VACANCY);

        restInstVacancyTempMockMvc.perform(put("/api/instVacancyTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instVacancyTemp)))
                .andExpect(status().isOk());

        // Validate the InstVacancyTemp in the database
        List<InstVacancyTemp> instVacancyTemps = instVacancyTempRepository.findAll();
        assertThat(instVacancyTemps).hasSize(databaseSizeBeforeUpdate);
        InstVacancyTemp testInstVacancyTemp = instVacancyTemps.get(instVacancyTemps.size() - 1);
        assertThat(testInstVacancyTemp.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testInstVacancyTemp.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testInstVacancyTemp.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInstVacancyTemp.getEmpType()).isEqualTo(UPDATED_EMP_TYPE);
        assertThat(testInstVacancyTemp.getTotalVacancy()).isEqualTo(UPDATED_TOTAL_VACANCY);
        assertThat(testInstVacancyTemp.getFilledUpVacancy()).isEqualTo(UPDATED_FILLED_UP_VACANCY);
    }

    @Test
    @Transactional
    public void deleteInstVacancyTemp() throws Exception {
        // Initialize the database
        instVacancyTempRepository.saveAndFlush(instVacancyTemp);

		int databaseSizeBeforeDelete = instVacancyTempRepository.findAll().size();

        // Get the instVacancyTemp
        restInstVacancyTempMockMvc.perform(delete("/api/instVacancyTemps/{id}", instVacancyTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstVacancyTemp> instVacancyTemps = instVacancyTempRepository.findAll();
        assertThat(instVacancyTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
