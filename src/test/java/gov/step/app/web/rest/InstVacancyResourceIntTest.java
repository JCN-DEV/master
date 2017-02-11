package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstVacancy;
import gov.step.app.repository.InstVacancyRepository;
import gov.step.app.repository.search.InstVacancySearchRepository;

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
 * Test class for the InstVacancyResource REST controller.
 *
 * @see InstVacancyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstVacancyResourceIntTest {


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
    private static final Integer DEFAULT_FILLED_UP_VACANCY = 1;
    private static final Integer UPDATED_FILLED_UP_VACANCY = 2;

    @Inject
    private InstVacancyRepository instVacancyRepository;

    @Inject
    private InstVacancySearchRepository instVacancySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstVacancyMockMvc;

    private InstVacancy instVacancy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstVacancyResource instVacancyResource = new InstVacancyResource();
        ReflectionTestUtils.setField(instVacancyResource, "instVacancyRepository", instVacancyRepository);
        ReflectionTestUtils.setField(instVacancyResource, "instVacancySearchRepository", instVacancySearchRepository);
        this.restInstVacancyMockMvc = MockMvcBuilders.standaloneSetup(instVacancyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instVacancy = new InstVacancy();
        instVacancy.setDateCreated(DEFAULT_DATE_CREATED);
        instVacancy.setDateModified(DEFAULT_DATE_MODIFIED);
        instVacancy.setStatus(DEFAULT_STATUS);
        instVacancy.setEmpType(DEFAULT_EMP_TYPE);
        instVacancy.setTotalVacancy(DEFAULT_TOTAL_VACANCY);
        instVacancy.setFilledUpVacancy(DEFAULT_FILLED_UP_VACANCY);
    }

    @Test
    @Transactional
    public void createInstVacancy() throws Exception {
        int databaseSizeBeforeCreate = instVacancyRepository.findAll().size();

        // Create the InstVacancy

        restInstVacancyMockMvc.perform(post("/api/instVacancys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instVacancy)))
                .andExpect(status().isCreated());

        // Validate the InstVacancy in the database
        List<InstVacancy> instVacancys = instVacancyRepository.findAll();
        assertThat(instVacancys).hasSize(databaseSizeBeforeCreate + 1);
        InstVacancy testInstVacancy = instVacancys.get(instVacancys.size() - 1);
        assertThat(testInstVacancy.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testInstVacancy.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testInstVacancy.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInstVacancy.getEmpType()).isEqualTo(DEFAULT_EMP_TYPE);
        assertThat(testInstVacancy.getTotalVacancy()).isEqualTo(DEFAULT_TOTAL_VACANCY);
        assertThat(testInstVacancy.getFilledUpVacancy()).isEqualTo(DEFAULT_FILLED_UP_VACANCY);
    }

    @Test
    @Transactional
    public void getAllInstVacancys() throws Exception {
        // Initialize the database
        instVacancyRepository.saveAndFlush(instVacancy);

        // Get all the instVacancys
        restInstVacancyMockMvc.perform(get("/api/instVacancys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instVacancy.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].empType").value(hasItem(DEFAULT_EMP_TYPE.toString())))
                .andExpect(jsonPath("$.[*].totalVacancy").value(hasItem(DEFAULT_TOTAL_VACANCY)))
                .andExpect(jsonPath("$.[*].filledUpVacancy").value(hasItem(DEFAULT_FILLED_UP_VACANCY.toString())));
    }

    @Test
    @Transactional
    public void getInstVacancy() throws Exception {
        // Initialize the database
        instVacancyRepository.saveAndFlush(instVacancy);

        // Get the instVacancy
        restInstVacancyMockMvc.perform(get("/api/instVacancys/{id}", instVacancy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instVacancy.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.empType").value(DEFAULT_EMP_TYPE.toString()))
            .andExpect(jsonPath("$.totalVacancy").value(DEFAULT_TOTAL_VACANCY))
            .andExpect(jsonPath("$.filledUpVacancy").value(DEFAULT_FILLED_UP_VACANCY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstVacancy() throws Exception {
        // Get the instVacancy
        restInstVacancyMockMvc.perform(get("/api/instVacancys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstVacancy() throws Exception {
        // Initialize the database
        instVacancyRepository.saveAndFlush(instVacancy);

		int databaseSizeBeforeUpdate = instVacancyRepository.findAll().size();

        // Update the instVacancy
        instVacancy.setDateCreated(UPDATED_DATE_CREATED);
        instVacancy.setDateModified(UPDATED_DATE_MODIFIED);
        instVacancy.setStatus(UPDATED_STATUS);
        instVacancy.setEmpType(UPDATED_EMP_TYPE);
        instVacancy.setTotalVacancy(UPDATED_TOTAL_VACANCY);
        instVacancy.setFilledUpVacancy(UPDATED_FILLED_UP_VACANCY);

        restInstVacancyMockMvc.perform(put("/api/instVacancys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instVacancy)))
                .andExpect(status().isOk());

        // Validate the InstVacancy in the database
        List<InstVacancy> instVacancys = instVacancyRepository.findAll();
        assertThat(instVacancys).hasSize(databaseSizeBeforeUpdate);
        InstVacancy testInstVacancy = instVacancys.get(instVacancys.size() - 1);
        assertThat(testInstVacancy.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testInstVacancy.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testInstVacancy.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInstVacancy.getEmpType()).isEqualTo(UPDATED_EMP_TYPE);
        assertThat(testInstVacancy.getTotalVacancy()).isEqualTo(UPDATED_TOTAL_VACANCY);
        assertThat(testInstVacancy.getFilledUpVacancy()).isEqualTo(UPDATED_FILLED_UP_VACANCY);
    }

    @Test
    @Transactional
    public void deleteInstVacancy() throws Exception {
        // Initialize the database
        instVacancyRepository.saveAndFlush(instVacancy);

		int databaseSizeBeforeDelete = instVacancyRepository.findAll().size();

        // Get the instVacancy
        restInstVacancyMockMvc.perform(delete("/api/instVacancys/{id}", instVacancy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstVacancy> instVacancys = instVacancyRepository.findAll();
        assertThat(instVacancys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
