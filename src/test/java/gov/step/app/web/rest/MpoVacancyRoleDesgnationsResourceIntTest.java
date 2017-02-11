package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoVacancyRoleDesgnations;
import gov.step.app.repository.MpoVacancyRoleDesgnationsRepository;
import gov.step.app.repository.search.MpoVacancyRoleDesgnationsSearchRepository;

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
 * Test class for the MpoVacancyRoleDesgnationsResource REST controller.
 *
 * @see MpoVacancyRoleDesgnationsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoVacancyRoleDesgnationsResourceIntTest {


    private static final LocalDate DEFAULT_CRATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CRATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Integer DEFAULT_TOTAL_POST = 1;
    private static final Integer UPDATED_TOTAL_POST = 2;

    @Inject
    private MpoVacancyRoleDesgnationsRepository mpoVacancyRoleDesgnationsRepository;

    @Inject
    private MpoVacancyRoleDesgnationsSearchRepository mpoVacancyRoleDesgnationsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoVacancyRoleDesgnationsMockMvc;

    private MpoVacancyRoleDesgnations mpoVacancyRoleDesgnations;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoVacancyRoleDesgnationsResource mpoVacancyRoleDesgnationsResource = new MpoVacancyRoleDesgnationsResource();
        ReflectionTestUtils.setField(mpoVacancyRoleDesgnationsResource, "mpoVacancyRoleDesgnationsRepository", mpoVacancyRoleDesgnationsRepository);
        ReflectionTestUtils.setField(mpoVacancyRoleDesgnationsResource, "mpoVacancyRoleDesgnationsSearchRepository", mpoVacancyRoleDesgnationsSearchRepository);
        this.restMpoVacancyRoleDesgnationsMockMvc = MockMvcBuilders.standaloneSetup(mpoVacancyRoleDesgnationsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoVacancyRoleDesgnations = new MpoVacancyRoleDesgnations();
        mpoVacancyRoleDesgnations.setCrateDate(DEFAULT_CRATE_DATE);
        mpoVacancyRoleDesgnations.setUpdateDate(DEFAULT_UPDATE_DATE);
        mpoVacancyRoleDesgnations.setStatus(DEFAULT_STATUS);
        mpoVacancyRoleDesgnations.setTotalPost(DEFAULT_TOTAL_POST);
    }

    @Test
    @Transactional
    public void createMpoVacancyRoleDesgnations() throws Exception {
        int databaseSizeBeforeCreate = mpoVacancyRoleDesgnationsRepository.findAll().size();

        // Create the MpoVacancyRoleDesgnations

        restMpoVacancyRoleDesgnationsMockMvc.perform(post("/api/mpoVacancyRoleDesgnationss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoVacancyRoleDesgnations)))
                .andExpect(status().isCreated());

        // Validate the MpoVacancyRoleDesgnations in the database
        List<MpoVacancyRoleDesgnations> mpoVacancyRoleDesgnationss = mpoVacancyRoleDesgnationsRepository.findAll();
        assertThat(mpoVacancyRoleDesgnationss).hasSize(databaseSizeBeforeCreate + 1);
        MpoVacancyRoleDesgnations testMpoVacancyRoleDesgnations = mpoVacancyRoleDesgnationss.get(mpoVacancyRoleDesgnationss.size() - 1);
        assertThat(testMpoVacancyRoleDesgnations.getCrateDate()).isEqualTo(DEFAULT_CRATE_DATE);
        assertThat(testMpoVacancyRoleDesgnations.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testMpoVacancyRoleDesgnations.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMpoVacancyRoleDesgnations.getTotalPost()).isEqualTo(DEFAULT_TOTAL_POST);
    }

    @Test
    @Transactional
    public void getAllMpoVacancyRoleDesgnationss() throws Exception {
        // Initialize the database
        mpoVacancyRoleDesgnationsRepository.saveAndFlush(mpoVacancyRoleDesgnations);

        // Get all the mpoVacancyRoleDesgnationss
        restMpoVacancyRoleDesgnationsMockMvc.perform(get("/api/mpoVacancyRoleDesgnationss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoVacancyRoleDesgnations.getId().intValue())))
                .andExpect(jsonPath("$.[*].crateDate").value(hasItem(DEFAULT_CRATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].totalPost").value(hasItem(DEFAULT_TOTAL_POST)));
    }

    @Test
    @Transactional
    public void getMpoVacancyRoleDesgnations() throws Exception {
        // Initialize the database
        mpoVacancyRoleDesgnationsRepository.saveAndFlush(mpoVacancyRoleDesgnations);

        // Get the mpoVacancyRoleDesgnations
        restMpoVacancyRoleDesgnationsMockMvc.perform(get("/api/mpoVacancyRoleDesgnationss/{id}", mpoVacancyRoleDesgnations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoVacancyRoleDesgnations.getId().intValue()))
            .andExpect(jsonPath("$.crateDate").value(DEFAULT_CRATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.totalPost").value(DEFAULT_TOTAL_POST));
    }

    @Test
    @Transactional
    public void getNonExistingMpoVacancyRoleDesgnations() throws Exception {
        // Get the mpoVacancyRoleDesgnations
        restMpoVacancyRoleDesgnationsMockMvc.perform(get("/api/mpoVacancyRoleDesgnationss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoVacancyRoleDesgnations() throws Exception {
        // Initialize the database
        mpoVacancyRoleDesgnationsRepository.saveAndFlush(mpoVacancyRoleDesgnations);

		int databaseSizeBeforeUpdate = mpoVacancyRoleDesgnationsRepository.findAll().size();

        // Update the mpoVacancyRoleDesgnations
        mpoVacancyRoleDesgnations.setCrateDate(UPDATED_CRATE_DATE);
        mpoVacancyRoleDesgnations.setUpdateDate(UPDATED_UPDATE_DATE);
        mpoVacancyRoleDesgnations.setStatus(UPDATED_STATUS);
        mpoVacancyRoleDesgnations.setTotalPost(UPDATED_TOTAL_POST);

        restMpoVacancyRoleDesgnationsMockMvc.perform(put("/api/mpoVacancyRoleDesgnationss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoVacancyRoleDesgnations)))
                .andExpect(status().isOk());

        // Validate the MpoVacancyRoleDesgnations in the database
        List<MpoVacancyRoleDesgnations> mpoVacancyRoleDesgnationss = mpoVacancyRoleDesgnationsRepository.findAll();
        assertThat(mpoVacancyRoleDesgnationss).hasSize(databaseSizeBeforeUpdate);
        MpoVacancyRoleDesgnations testMpoVacancyRoleDesgnations = mpoVacancyRoleDesgnationss.get(mpoVacancyRoleDesgnationss.size() - 1);
        assertThat(testMpoVacancyRoleDesgnations.getCrateDate()).isEqualTo(UPDATED_CRATE_DATE);
        assertThat(testMpoVacancyRoleDesgnations.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testMpoVacancyRoleDesgnations.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMpoVacancyRoleDesgnations.getTotalPost()).isEqualTo(UPDATED_TOTAL_POST);
    }

    @Test
    @Transactional
    public void deleteMpoVacancyRoleDesgnations() throws Exception {
        // Initialize the database
        mpoVacancyRoleDesgnationsRepository.saveAndFlush(mpoVacancyRoleDesgnations);

		int databaseSizeBeforeDelete = mpoVacancyRoleDesgnationsRepository.findAll().size();

        // Get the mpoVacancyRoleDesgnations
        restMpoVacancyRoleDesgnationsMockMvc.perform(delete("/api/mpoVacancyRoleDesgnationss/{id}", mpoVacancyRoleDesgnations.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoVacancyRoleDesgnations> mpoVacancyRoleDesgnationss = mpoVacancyRoleDesgnationsRepository.findAll();
        assertThat(mpoVacancyRoleDesgnationss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
