package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.UmracRoleSetup;
import gov.step.app.repository.UmracRoleSetupRepository;
import gov.step.app.repository.search.UmracRoleSetupSearchRepository;

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
 * Test class for the UmracRoleSetupResource REST controller.
 *
 * @see UmracRoleSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UmracRoleSetupResourceTest {

    private static final String DEFAULT_ROLE_ID = "AAAAA";
    private static final String UPDATED_ROLE_ID = "BBBBB";
    private static final String DEFAULT_ROLE_NAME = "AAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final LocalDate DEFAULT_UPDATED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private UmracRoleSetupRepository umracRoleSetupRepository;

    @Inject
    private UmracRoleSetupSearchRepository umracRoleSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmracRoleSetupMockMvc;

    private UmracRoleSetup umracRoleSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmracRoleSetupResource umracRoleSetupResource = new UmracRoleSetupResource();
        ReflectionTestUtils.setField(umracRoleSetupResource, "umracRoleSetupRepository", umracRoleSetupRepository);
        ReflectionTestUtils.setField(umracRoleSetupResource, "umracRoleSetupSearchRepository", umracRoleSetupSearchRepository);
        this.restUmracRoleSetupMockMvc = MockMvcBuilders.standaloneSetup(umracRoleSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umracRoleSetup = new UmracRoleSetup();
        umracRoleSetup.setRoleId(DEFAULT_ROLE_ID);
        umracRoleSetup.setRoleName(DEFAULT_ROLE_NAME);
        umracRoleSetup.setDescription(DEFAULT_DESCRIPTION);
        umracRoleSetup.setStatus(DEFAULT_STATUS);
        umracRoleSetup.setCreateDate(DEFAULT_CREATE_DATE);
        umracRoleSetup.setCreateBy(DEFAULT_CREATE_BY);
        umracRoleSetup.setUpdatedBy(DEFAULT_UPDATED_BY);
        umracRoleSetup.setUpdatedTime(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createUmracRoleSetup() throws Exception {
        int databaseSizeBeforeCreate = umracRoleSetupRepository.findAll().size();

        // Create the UmracRoleSetup

        restUmracRoleSetupMockMvc.perform(post("/api/umracRoleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracRoleSetup)))
                .andExpect(status().isCreated());

        // Validate the UmracRoleSetup in the database
        List<UmracRoleSetup> umracRoleSetups = umracRoleSetupRepository.findAll();
        assertThat(umracRoleSetups).hasSize(databaseSizeBeforeCreate + 1);
        UmracRoleSetup testUmracRoleSetup = umracRoleSetups.get(umracRoleSetups.size() - 1);
        assertThat(testUmracRoleSetup.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testUmracRoleSetup.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testUmracRoleSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUmracRoleSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUmracRoleSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testUmracRoleSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testUmracRoleSetup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUmracRoleSetup.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void checkRoleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = umracRoleSetupRepository.findAll().size();
        // set the field null
        umracRoleSetup.setRoleName(null);

        // Create the UmracRoleSetup, which fails.

        restUmracRoleSetupMockMvc.perform(post("/api/umracRoleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracRoleSetup)))
                .andExpect(status().isBadRequest());

        List<UmracRoleSetup> umracRoleSetups = umracRoleSetupRepository.findAll();
        assertThat(umracRoleSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUmracRoleSetups() throws Exception {
        // Initialize the database
        umracRoleSetupRepository.saveAndFlush(umracRoleSetup);

        // Get all the umracRoleSetups
        restUmracRoleSetupMockMvc.perform(get("/api/umracRoleSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umracRoleSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID.toString())))
                .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getUmracRoleSetup() throws Exception {
        // Initialize the database
        umracRoleSetupRepository.saveAndFlush(umracRoleSetup);

        // Get the umracRoleSetup
        restUmracRoleSetupMockMvc.perform(get("/api/umracRoleSetups/{id}", umracRoleSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umracRoleSetup.getId().intValue()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID.toString()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUmracRoleSetup() throws Exception {
        // Get the umracRoleSetup
        restUmracRoleSetupMockMvc.perform(get("/api/umracRoleSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmracRoleSetup() throws Exception {
        // Initialize the database
        umracRoleSetupRepository.saveAndFlush(umracRoleSetup);

		int databaseSizeBeforeUpdate = umracRoleSetupRepository.findAll().size();

        // Update the umracRoleSetup
        umracRoleSetup.setRoleId(UPDATED_ROLE_ID);
        umracRoleSetup.setRoleName(UPDATED_ROLE_NAME);
        umracRoleSetup.setDescription(UPDATED_DESCRIPTION);
        umracRoleSetup.setStatus(UPDATED_STATUS);
        umracRoleSetup.setCreateDate(UPDATED_CREATE_DATE);
        umracRoleSetup.setCreateBy(UPDATED_CREATE_BY);
        umracRoleSetup.setUpdatedBy(UPDATED_UPDATED_BY);
        umracRoleSetup.setUpdatedTime(UPDATED_UPDATED_TIME);

        restUmracRoleSetupMockMvc.perform(put("/api/umracRoleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracRoleSetup)))
                .andExpect(status().isOk());

        // Validate the UmracRoleSetup in the database
        List<UmracRoleSetup> umracRoleSetups = umracRoleSetupRepository.findAll();
        assertThat(umracRoleSetups).hasSize(databaseSizeBeforeUpdate);
        UmracRoleSetup testUmracRoleSetup = umracRoleSetups.get(umracRoleSetups.size() - 1);
        assertThat(testUmracRoleSetup.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testUmracRoleSetup.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testUmracRoleSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUmracRoleSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUmracRoleSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testUmracRoleSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testUmracRoleSetup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUmracRoleSetup.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteUmracRoleSetup() throws Exception {
        // Initialize the database
        umracRoleSetupRepository.saveAndFlush(umracRoleSetup);

		int databaseSizeBeforeDelete = umracRoleSetupRepository.findAll().size();

        // Get the umracRoleSetup
        restUmracRoleSetupMockMvc.perform(delete("/api/umracRoleSetups/{id}", umracRoleSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmracRoleSetup> umracRoleSetups = umracRoleSetupRepository.findAll();
        assertThat(umracRoleSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
