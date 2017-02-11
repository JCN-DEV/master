package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.UmracRightsSetup;
import gov.step.app.repository.UmracRightsSetupRepository;
import gov.step.app.repository.search.UmracRightsSetupSearchRepository;

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
 * Test class for the UmracRightsSetupResource REST controller.
 *
 * @see UmracRightsSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UmracRightsSetupResourceTest {

    private static final String DEFAULT_RIGHT_ID = "AAAAA";
    private static final String UPDATED_RIGHT_ID = "BBBBB";

    private static final Long DEFAULT_ROLE_ID = 1L;
    private static final Long UPDATED_ROLE_ID = 2L;

    private static final Long DEFAULT_MODULE_ID = 1L;
    private static final Long UPDATED_MODULE_ID = 2L;

    private static final Long DEFAULT_SUB_MODULE_ID = 1L;
    private static final Long UPDATED_SUB_MODULE_ID = 2L;
    private static final String DEFAULT_RIGHTS = "AAAAA";
    private static final String UPDATED_RIGHTS = "BBBBB";
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
    private UmracRightsSetupRepository umracRightsSetupRepository;

    @Inject
    private UmracRightsSetupSearchRepository umracRightsSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmracRightsSetupMockMvc;

    private UmracRightsSetup umracRightsSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmracRightsSetupResource umracRightsSetupResource = new UmracRightsSetupResource();
        ReflectionTestUtils.setField(umracRightsSetupResource, "umracRightsSetupRepository", umracRightsSetupRepository);
        ReflectionTestUtils.setField(umracRightsSetupResource, "umracRightsSetupSearchRepository", umracRightsSetupSearchRepository);
        this.restUmracRightsSetupMockMvc = MockMvcBuilders.standaloneSetup(umracRightsSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umracRightsSetup = new UmracRightsSetup();
        umracRightsSetup.setRightId(DEFAULT_RIGHT_ID);
        umracRightsSetup.setRoleId(DEFAULT_ROLE_ID);
        umracRightsSetup.setModule_id(DEFAULT_MODULE_ID);
        umracRightsSetup.setSubModule_id(DEFAULT_SUB_MODULE_ID);
        umracRightsSetup.setRights(DEFAULT_RIGHTS);
        umracRightsSetup.setDescription(DEFAULT_DESCRIPTION);
        umracRightsSetup.setStatus(DEFAULT_STATUS);
        umracRightsSetup.setCreateDate(DEFAULT_CREATE_DATE);
        umracRightsSetup.setCreateBy(DEFAULT_CREATE_BY);
        umracRightsSetup.setUpdatedBy(DEFAULT_UPDATED_BY);
        umracRightsSetup.setUpdatedTime(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createUmracRightsSetup() throws Exception {
        int databaseSizeBeforeCreate = umracRightsSetupRepository.findAll().size();

        // Create the UmracRightsSetup

        restUmracRightsSetupMockMvc.perform(post("/api/umracRightsSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracRightsSetup)))
                .andExpect(status().isCreated());

        // Validate the UmracRightsSetup in the database
        List<UmracRightsSetup> umracRightsSetups = umracRightsSetupRepository.findAll();
        assertThat(umracRightsSetups).hasSize(databaseSizeBeforeCreate + 1);
        UmracRightsSetup testUmracRightsSetup = umracRightsSetups.get(umracRightsSetups.size() - 1);
        assertThat(testUmracRightsSetup.getRightId()).isEqualTo(DEFAULT_RIGHT_ID);
        assertThat(testUmracRightsSetup.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testUmracRightsSetup.getModule_id()).isEqualTo(DEFAULT_MODULE_ID);
        assertThat(testUmracRightsSetup.getSubModule_id()).isEqualTo(DEFAULT_SUB_MODULE_ID);
        assertThat(testUmracRightsSetup.getRights()).isEqualTo(DEFAULT_RIGHTS);
        assertThat(testUmracRightsSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUmracRightsSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUmracRightsSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testUmracRightsSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testUmracRightsSetup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUmracRightsSetup.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllUmracRightsSetups() throws Exception {
        // Initialize the database
        umracRightsSetupRepository.saveAndFlush(umracRightsSetup);

        // Get all the umracRightsSetups
        restUmracRightsSetupMockMvc.perform(get("/api/umracRightsSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umracRightsSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].rightId").value(hasItem(DEFAULT_RIGHT_ID.toString())))
                .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID.intValue())))
                .andExpect(jsonPath("$.[*].module_id").value(hasItem(DEFAULT_MODULE_ID.intValue())))
                .andExpect(jsonPath("$.[*].subModule_id").value(hasItem(DEFAULT_SUB_MODULE_ID.intValue())))
                .andExpect(jsonPath("$.[*].rights").value(hasItem(DEFAULT_RIGHTS.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getUmracRightsSetup() throws Exception {
        // Initialize the database
        umracRightsSetupRepository.saveAndFlush(umracRightsSetup);

        // Get the umracRightsSetup
        restUmracRightsSetupMockMvc.perform(get("/api/umracRightsSetups/{id}", umracRightsSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umracRightsSetup.getId().intValue()))
            .andExpect(jsonPath("$.rightId").value(DEFAULT_RIGHT_ID.toString()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID.intValue()))
            .andExpect(jsonPath("$.module_id").value(DEFAULT_MODULE_ID.intValue()))
            .andExpect(jsonPath("$.subModule_id").value(DEFAULT_SUB_MODULE_ID.intValue()))
            .andExpect(jsonPath("$.rights").value(DEFAULT_RIGHTS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUmracRightsSetup() throws Exception {
        // Get the umracRightsSetup
        restUmracRightsSetupMockMvc.perform(get("/api/umracRightsSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmracRightsSetup() throws Exception {
        // Initialize the database
        umracRightsSetupRepository.saveAndFlush(umracRightsSetup);

		int databaseSizeBeforeUpdate = umracRightsSetupRepository.findAll().size();

        // Update the umracRightsSetup
        umracRightsSetup.setRightId(UPDATED_RIGHT_ID);
        umracRightsSetup.setRoleId(UPDATED_ROLE_ID);
        umracRightsSetup.setModule_id(UPDATED_MODULE_ID);
        umracRightsSetup.setSubModule_id(UPDATED_SUB_MODULE_ID);
        umracRightsSetup.setRights(UPDATED_RIGHTS);
        umracRightsSetup.setDescription(UPDATED_DESCRIPTION);
        umracRightsSetup.setStatus(UPDATED_STATUS);
        umracRightsSetup.setCreateDate(UPDATED_CREATE_DATE);
        umracRightsSetup.setCreateBy(UPDATED_CREATE_BY);
        umracRightsSetup.setUpdatedBy(UPDATED_UPDATED_BY);
        umracRightsSetup.setUpdatedTime(UPDATED_UPDATED_TIME);

        restUmracRightsSetupMockMvc.perform(put("/api/umracRightsSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracRightsSetup)))
                .andExpect(status().isOk());

        // Validate the UmracRightsSetup in the database
        List<UmracRightsSetup> umracRightsSetups = umracRightsSetupRepository.findAll();
        assertThat(umracRightsSetups).hasSize(databaseSizeBeforeUpdate);
        UmracRightsSetup testUmracRightsSetup = umracRightsSetups.get(umracRightsSetups.size() - 1);
        assertThat(testUmracRightsSetup.getRightId()).isEqualTo(UPDATED_RIGHT_ID);
        assertThat(testUmracRightsSetup.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testUmracRightsSetup.getModule_id()).isEqualTo(UPDATED_MODULE_ID);
        assertThat(testUmracRightsSetup.getSubModule_id()).isEqualTo(UPDATED_SUB_MODULE_ID);
        assertThat(testUmracRightsSetup.getRights()).isEqualTo(UPDATED_RIGHTS);
        assertThat(testUmracRightsSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUmracRightsSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUmracRightsSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testUmracRightsSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testUmracRightsSetup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUmracRightsSetup.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteUmracRightsSetup() throws Exception {
        // Initialize the database
        umracRightsSetupRepository.saveAndFlush(umracRightsSetup);

		int databaseSizeBeforeDelete = umracRightsSetupRepository.findAll().size();

        // Get the umracRightsSetup
        restUmracRightsSetupMockMvc.perform(delete("/api/umracRightsSetups/{id}", umracRightsSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmracRightsSetup> umracRightsSetups = umracRightsSetupRepository.findAll();
        assertThat(umracRightsSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
