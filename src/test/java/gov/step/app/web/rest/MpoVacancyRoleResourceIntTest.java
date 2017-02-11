package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoVacancyRole;
import gov.step.app.repository.MpoVacancyRoleRepository;
import gov.step.app.repository.search.MpoVacancyRoleSearchRepository;

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

import gov.step.app.domain.enumeration.VacancyRoleType;

/**
 * Test class for the MpoVacancyRoleResource REST controller.
 *
 * @see MpoVacancyRoleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoVacancyRoleResourceIntTest {


    private static final LocalDate DEFAULT_CRATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CRATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Integer DEFAULT_TOTAL_TRADE = 1;
    private static final Integer UPDATED_TOTAL_TRADE = 2;


private static final VacancyRoleType DEFAULT_VACANCY_ROLE_TYPE = VacancyRoleType.General;
    private static final VacancyRoleType UPDATED_VACANCY_ROLE_TYPE = VacancyRoleType.Special;

    private static final Integer DEFAULT_TOTAL_VACANCY = 1;
    private static final Integer UPDATED_TOTAL_VACANCY = 2;

    @Inject
    private MpoVacancyRoleRepository mpoVacancyRoleRepository;

    @Inject
    private MpoVacancyRoleSearchRepository mpoVacancyRoleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoVacancyRoleMockMvc;

    private MpoVacancyRole mpoVacancyRole;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoVacancyRoleResource mpoVacancyRoleResource = new MpoVacancyRoleResource();
        ReflectionTestUtils.setField(mpoVacancyRoleResource, "mpoVacancyRoleRepository", mpoVacancyRoleRepository);
        ReflectionTestUtils.setField(mpoVacancyRoleResource, "mpoVacancyRoleSearchRepository", mpoVacancyRoleSearchRepository);
        this.restMpoVacancyRoleMockMvc = MockMvcBuilders.standaloneSetup(mpoVacancyRoleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoVacancyRole = new MpoVacancyRole();
        mpoVacancyRole.setCrateDate(DEFAULT_CRATE_DATE);
        mpoVacancyRole.setUpdateDate(DEFAULT_UPDATE_DATE);
        mpoVacancyRole.setStatus(DEFAULT_STATUS);
        mpoVacancyRole.setTotalTrade(DEFAULT_TOTAL_TRADE);
        mpoVacancyRole.setVacancyRoleType(DEFAULT_VACANCY_ROLE_TYPE);
        mpoVacancyRole.setTotalVacancy(DEFAULT_TOTAL_VACANCY);
    }

    @Test
    @Transactional
    public void createMpoVacancyRole() throws Exception {
        int databaseSizeBeforeCreate = mpoVacancyRoleRepository.findAll().size();

        // Create the MpoVacancyRole

        restMpoVacancyRoleMockMvc.perform(post("/api/mpoVacancyRoles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoVacancyRole)))
                .andExpect(status().isCreated());

        // Validate the MpoVacancyRole in the database
        List<MpoVacancyRole> mpoVacancyRoles = mpoVacancyRoleRepository.findAll();
        assertThat(mpoVacancyRoles).hasSize(databaseSizeBeforeCreate + 1);
        MpoVacancyRole testMpoVacancyRole = mpoVacancyRoles.get(mpoVacancyRoles.size() - 1);
        assertThat(testMpoVacancyRole.getCrateDate()).isEqualTo(DEFAULT_CRATE_DATE);
        assertThat(testMpoVacancyRole.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testMpoVacancyRole.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMpoVacancyRole.getTotalTrade()).isEqualTo(DEFAULT_TOTAL_TRADE);
        assertThat(testMpoVacancyRole.getVacancyRoleType()).isEqualTo(DEFAULT_VACANCY_ROLE_TYPE);
        assertThat(testMpoVacancyRole.getTotalVacancy()).isEqualTo(DEFAULT_TOTAL_VACANCY);
    }

    @Test
    @Transactional
    public void getAllMpoVacancyRoles() throws Exception {
        // Initialize the database
        mpoVacancyRoleRepository.saveAndFlush(mpoVacancyRole);

        // Get all the mpoVacancyRoles
        restMpoVacancyRoleMockMvc.perform(get("/api/mpoVacancyRoles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoVacancyRole.getId().intValue())))
                .andExpect(jsonPath("$.[*].crateDate").value(hasItem(DEFAULT_CRATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].totalTrade").value(hasItem(DEFAULT_TOTAL_TRADE)))
                .andExpect(jsonPath("$.[*].vacancyRoleType").value(hasItem(DEFAULT_VACANCY_ROLE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].totalVacancy").value(hasItem(DEFAULT_TOTAL_VACANCY)));
    }

    @Test
    @Transactional
    public void getMpoVacancyRole() throws Exception {
        // Initialize the database
        mpoVacancyRoleRepository.saveAndFlush(mpoVacancyRole);

        // Get the mpoVacancyRole
        restMpoVacancyRoleMockMvc.perform(get("/api/mpoVacancyRoles/{id}", mpoVacancyRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoVacancyRole.getId().intValue()))
            .andExpect(jsonPath("$.crateDate").value(DEFAULT_CRATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.totalTrade").value(DEFAULT_TOTAL_TRADE))
            .andExpect(jsonPath("$.vacancyRoleType").value(DEFAULT_VACANCY_ROLE_TYPE.toString()))
            .andExpect(jsonPath("$.totalVacancy").value(DEFAULT_TOTAL_VACANCY));
    }

    @Test
    @Transactional
    public void getNonExistingMpoVacancyRole() throws Exception {
        // Get the mpoVacancyRole
        restMpoVacancyRoleMockMvc.perform(get("/api/mpoVacancyRoles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoVacancyRole() throws Exception {
        // Initialize the database
        mpoVacancyRoleRepository.saveAndFlush(mpoVacancyRole);

		int databaseSizeBeforeUpdate = mpoVacancyRoleRepository.findAll().size();

        // Update the mpoVacancyRole
        mpoVacancyRole.setCrateDate(UPDATED_CRATE_DATE);
        mpoVacancyRole.setUpdateDate(UPDATED_UPDATE_DATE);
        mpoVacancyRole.setStatus(UPDATED_STATUS);
        mpoVacancyRole.setTotalTrade(UPDATED_TOTAL_TRADE);
        mpoVacancyRole.setVacancyRoleType(UPDATED_VACANCY_ROLE_TYPE);
        mpoVacancyRole.setTotalVacancy(UPDATED_TOTAL_VACANCY);

        restMpoVacancyRoleMockMvc.perform(put("/api/mpoVacancyRoles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoVacancyRole)))
                .andExpect(status().isOk());

        // Validate the MpoVacancyRole in the database
        List<MpoVacancyRole> mpoVacancyRoles = mpoVacancyRoleRepository.findAll();
        assertThat(mpoVacancyRoles).hasSize(databaseSizeBeforeUpdate);
        MpoVacancyRole testMpoVacancyRole = mpoVacancyRoles.get(mpoVacancyRoles.size() - 1);
        assertThat(testMpoVacancyRole.getCrateDate()).isEqualTo(UPDATED_CRATE_DATE);
        assertThat(testMpoVacancyRole.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testMpoVacancyRole.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMpoVacancyRole.getTotalTrade()).isEqualTo(UPDATED_TOTAL_TRADE);
        assertThat(testMpoVacancyRole.getVacancyRoleType()).isEqualTo(UPDATED_VACANCY_ROLE_TYPE);
        assertThat(testMpoVacancyRole.getTotalVacancy()).isEqualTo(UPDATED_TOTAL_VACANCY);
    }

    @Test
    @Transactional
    public void deleteMpoVacancyRole() throws Exception {
        // Initialize the database
        mpoVacancyRoleRepository.saveAndFlush(mpoVacancyRole);

		int databaseSizeBeforeDelete = mpoVacancyRoleRepository.findAll().size();

        // Get the mpoVacancyRole
        restMpoVacancyRoleMockMvc.perform(delete("/api/mpoVacancyRoles/{id}", mpoVacancyRole.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoVacancyRole> mpoVacancyRoles = mpoVacancyRoleRepository.findAll();
        assertThat(mpoVacancyRoles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
