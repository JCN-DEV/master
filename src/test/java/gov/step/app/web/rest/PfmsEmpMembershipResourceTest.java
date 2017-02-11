package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PfmsEmpMembership;
import gov.step.app.repository.PfmsEmpMembershipRepository;
import gov.step.app.repository.search.PfmsEmpMembershipSearchRepository;

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
 * Test class for the PfmsEmpMembershipResource REST controller.
 *
 * @see PfmsEmpMembershipResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PfmsEmpMembershipResourceTest {


    private static final Double DEFAULT_INIT_OWN_CONTRIBUTE = 1D;
    private static final Double UPDATED_INIT_OWN_CONTRIBUTE = 2D;

    private static final Double DEFAULT_INIT_OWN_CONTRIBUTE_INT = 1D;
    private static final Double UPDATED_INIT_OWN_CONTRIBUTE_INT = 2D;

    private static final Double DEFAULT_CUR_OWN_CONTRIBUTE = 1D;
    private static final Double UPDATED_CUR_OWN_CONTRIBUTE = 2D;

    private static final Double DEFAULT_CUR_OWN_CONTRIBUTE_INT = 1D;
    private static final Double UPDATED_CUR_OWN_CONTRIBUTE_INT = 2D;

    private static final Double DEFAULT_CUR_OWN_CONTRIBUTE_TOT = 1D;
    private static final Double UPDATED_CUR_OWN_CONTRIBUTE_TOT = 2D;

    private static final Double DEFAULT_PERCENT_OF_DEDUCT = 1D;
    private static final Double UPDATED_PERCENT_OF_DEDUCT = 2D;

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private PfmsEmpMembershipRepository pfmsEmpMembershipRepository;

    @Inject
    private PfmsEmpMembershipSearchRepository pfmsEmpMembershipSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPfmsEmpMembershipMockMvc;

    private PfmsEmpMembership pfmsEmpMembership;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PfmsEmpMembershipResource pfmsEmpMembershipResource = new PfmsEmpMembershipResource();
        ReflectionTestUtils.setField(pfmsEmpMembershipResource, "pfmsEmpMembershipRepository", pfmsEmpMembershipRepository);
        ReflectionTestUtils.setField(pfmsEmpMembershipResource, "pfmsEmpMembershipSearchRepository", pfmsEmpMembershipSearchRepository);
        this.restPfmsEmpMembershipMockMvc = MockMvcBuilders.standaloneSetup(pfmsEmpMembershipResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pfmsEmpMembership = new PfmsEmpMembership();
        pfmsEmpMembership.setInitOwnContribute(DEFAULT_INIT_OWN_CONTRIBUTE);
        pfmsEmpMembership.setInitOwnContributeInt(DEFAULT_INIT_OWN_CONTRIBUTE_INT);
        pfmsEmpMembership.setCurOwnContribute(DEFAULT_CUR_OWN_CONTRIBUTE);
        pfmsEmpMembership.setCurOwnContributeInt(DEFAULT_CUR_OWN_CONTRIBUTE_INT);
        pfmsEmpMembership.setCurOwnContributeTot(DEFAULT_CUR_OWN_CONTRIBUTE_TOT);
        pfmsEmpMembership.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pfmsEmpMembership.setCreateDate(DEFAULT_CREATE_DATE);
        pfmsEmpMembership.setCreateBy(DEFAULT_CREATE_BY);
        pfmsEmpMembership.setUpdateDate(DEFAULT_UPDATE_DATE);
        pfmsEmpMembership.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPfmsEmpMembership() throws Exception {
        int databaseSizeBeforeCreate = pfmsEmpMembershipRepository.findAll().size();

        // Create the PfmsEmpMembership

        restPfmsEmpMembershipMockMvc.perform(post("/api/pfmsEmpMemberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMembership)))
                .andExpect(status().isCreated());

        // Validate the PfmsEmpMembership in the database
        List<PfmsEmpMembership> pfmsEmpMemberships = pfmsEmpMembershipRepository.findAll();
        assertThat(pfmsEmpMemberships).hasSize(databaseSizeBeforeCreate + 1);
        PfmsEmpMembership testPfmsEmpMembership = pfmsEmpMemberships.get(pfmsEmpMemberships.size() - 1);
        assertThat(testPfmsEmpMembership.getInitOwnContribute()).isEqualTo(DEFAULT_INIT_OWN_CONTRIBUTE);
        assertThat(testPfmsEmpMembership.getInitOwnContributeInt()).isEqualTo(DEFAULT_INIT_OWN_CONTRIBUTE_INT);
        assertThat(testPfmsEmpMembership.getCurOwnContribute()).isEqualTo(DEFAULT_CUR_OWN_CONTRIBUTE);
        assertThat(testPfmsEmpMembership.getCurOwnContributeInt()).isEqualTo(DEFAULT_CUR_OWN_CONTRIBUTE_INT);
        assertThat(testPfmsEmpMembership.getCurOwnContributeTot()).isEqualTo(DEFAULT_CUR_OWN_CONTRIBUTE_TOT);
        assertThat(testPfmsEmpMembership.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPfmsEmpMembership.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPfmsEmpMembership.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPfmsEmpMembership.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPfmsEmpMembership.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkInitOwnContributeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMembershipRepository.findAll().size();
        // set the field null
        pfmsEmpMembership.setInitOwnContribute(null);

        // Create the PfmsEmpMembership, which fails.

        restPfmsEmpMembershipMockMvc.perform(post("/api/pfmsEmpMemberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMembership)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMembership> pfmsEmpMemberships = pfmsEmpMembershipRepository.findAll();
        assertThat(pfmsEmpMemberships).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInitOwnContributeIntIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMembershipRepository.findAll().size();
        // set the field null
        pfmsEmpMembership.setInitOwnContributeInt(null);

        // Create the PfmsEmpMembership, which fails.

        restPfmsEmpMembershipMockMvc.perform(post("/api/pfmsEmpMemberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMembership)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMembership> pfmsEmpMemberships = pfmsEmpMembershipRepository.findAll();
        assertThat(pfmsEmpMemberships).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurOwnContributeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMembershipRepository.findAll().size();
        // set the field null
        pfmsEmpMembership.setCurOwnContribute(null);

        // Create the PfmsEmpMembership, which fails.

        restPfmsEmpMembershipMockMvc.perform(post("/api/pfmsEmpMemberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMembership)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMembership> pfmsEmpMemberships = pfmsEmpMembershipRepository.findAll();
        assertThat(pfmsEmpMemberships).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurOwnContributeIntIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMembershipRepository.findAll().size();
        // set the field null
        pfmsEmpMembership.setCurOwnContributeInt(null);

        // Create the PfmsEmpMembership, which fails.

        restPfmsEmpMembershipMockMvc.perform(post("/api/pfmsEmpMemberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMembership)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMembership> pfmsEmpMemberships = pfmsEmpMembershipRepository.findAll();
        assertThat(pfmsEmpMemberships).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurOwnContributeTotIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMembershipRepository.findAll().size();
        // set the field null
        pfmsEmpMembership.setCurOwnContributeTot(null);

        // Create the PfmsEmpMembership, which fails.

        restPfmsEmpMembershipMockMvc.perform(post("/api/pfmsEmpMemberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMembership)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMembership> pfmsEmpMemberships = pfmsEmpMembershipRepository.findAll();
        assertThat(pfmsEmpMemberships).hasSize(databaseSizeBeforeTest);
    }



    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpMembershipRepository.findAll().size();
        // set the field null
        pfmsEmpMembership.setActiveStatus(null);

        // Create the PfmsEmpMembership, which fails.

        restPfmsEmpMembershipMockMvc.perform(post("/api/pfmsEmpMemberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMembership)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpMembership> pfmsEmpMemberships = pfmsEmpMembershipRepository.findAll();
        assertThat(pfmsEmpMemberships).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPfmsEmpMemberships() throws Exception {
        // Initialize the database
        pfmsEmpMembershipRepository.saveAndFlush(pfmsEmpMembership);

        // Get all the pfmsEmpMemberships
        restPfmsEmpMembershipMockMvc.perform(get("/api/pfmsEmpMemberships"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pfmsEmpMembership.getId().intValue())))
                .andExpect(jsonPath("$.[*].initOwnContribute").value(hasItem(DEFAULT_INIT_OWN_CONTRIBUTE.doubleValue())))
                .andExpect(jsonPath("$.[*].initOwnContributeInt").value(hasItem(DEFAULT_INIT_OWN_CONTRIBUTE_INT.doubleValue())))
                .andExpect(jsonPath("$.[*].curOwnContribute").value(hasItem(DEFAULT_CUR_OWN_CONTRIBUTE.doubleValue())))
                .andExpect(jsonPath("$.[*].curOwnContributeInt").value(hasItem(DEFAULT_CUR_OWN_CONTRIBUTE_INT.doubleValue())))
                .andExpect(jsonPath("$.[*].curOwnContributeTot").value(hasItem(DEFAULT_CUR_OWN_CONTRIBUTE_TOT.doubleValue())))
                .andExpect(jsonPath("$.[*].percentOfDeduct").value(hasItem(DEFAULT_PERCENT_OF_DEDUCT.doubleValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPfmsEmpMembership() throws Exception {
        // Initialize the database
        pfmsEmpMembershipRepository.saveAndFlush(pfmsEmpMembership);

        // Get the pfmsEmpMembership
        restPfmsEmpMembershipMockMvc.perform(get("/api/pfmsEmpMemberships/{id}", pfmsEmpMembership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pfmsEmpMembership.getId().intValue()))
            .andExpect(jsonPath("$.initOwnContribute").value(DEFAULT_INIT_OWN_CONTRIBUTE.doubleValue()))
            .andExpect(jsonPath("$.initOwnContributeInt").value(DEFAULT_INIT_OWN_CONTRIBUTE_INT.doubleValue()))
            .andExpect(jsonPath("$.curOwnContribute").value(DEFAULT_CUR_OWN_CONTRIBUTE.doubleValue()))
            .andExpect(jsonPath("$.curOwnContributeInt").value(DEFAULT_CUR_OWN_CONTRIBUTE_INT.doubleValue()))
            .andExpect(jsonPath("$.curOwnContributeTot").value(DEFAULT_CUR_OWN_CONTRIBUTE_TOT.doubleValue()))
            .andExpect(jsonPath("$.percentOfDeduct").value(DEFAULT_PERCENT_OF_DEDUCT.doubleValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPfmsEmpMembership() throws Exception {
        // Get the pfmsEmpMembership
        restPfmsEmpMembershipMockMvc.perform(get("/api/pfmsEmpMemberships/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePfmsEmpMembership() throws Exception {
        // Initialize the database
        pfmsEmpMembershipRepository.saveAndFlush(pfmsEmpMembership);

		int databaseSizeBeforeUpdate = pfmsEmpMembershipRepository.findAll().size();

        // Update the pfmsEmpMembership
        pfmsEmpMembership.setInitOwnContribute(UPDATED_INIT_OWN_CONTRIBUTE);
        pfmsEmpMembership.setInitOwnContributeInt(UPDATED_INIT_OWN_CONTRIBUTE_INT);
        pfmsEmpMembership.setCurOwnContribute(UPDATED_CUR_OWN_CONTRIBUTE);
        pfmsEmpMembership.setCurOwnContributeInt(UPDATED_CUR_OWN_CONTRIBUTE_INT);
        pfmsEmpMembership.setCurOwnContributeTot(UPDATED_CUR_OWN_CONTRIBUTE_TOT);
        pfmsEmpMembership.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pfmsEmpMembership.setCreateDate(UPDATED_CREATE_DATE);
        pfmsEmpMembership.setCreateBy(UPDATED_CREATE_BY);
        pfmsEmpMembership.setUpdateDate(UPDATED_UPDATE_DATE);
        pfmsEmpMembership.setUpdateBy(UPDATED_UPDATE_BY);

        restPfmsEmpMembershipMockMvc.perform(put("/api/pfmsEmpMemberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpMembership)))
                .andExpect(status().isOk());

        // Validate the PfmsEmpMembership in the database
        List<PfmsEmpMembership> pfmsEmpMemberships = pfmsEmpMembershipRepository.findAll();
        assertThat(pfmsEmpMemberships).hasSize(databaseSizeBeforeUpdate);
        PfmsEmpMembership testPfmsEmpMembership = pfmsEmpMemberships.get(pfmsEmpMemberships.size() - 1);
        assertThat(testPfmsEmpMembership.getInitOwnContribute()).isEqualTo(UPDATED_INIT_OWN_CONTRIBUTE);
        assertThat(testPfmsEmpMembership.getInitOwnContributeInt()).isEqualTo(UPDATED_INIT_OWN_CONTRIBUTE_INT);
        assertThat(testPfmsEmpMembership.getCurOwnContribute()).isEqualTo(UPDATED_CUR_OWN_CONTRIBUTE);
        assertThat(testPfmsEmpMembership.getCurOwnContributeInt()).isEqualTo(UPDATED_CUR_OWN_CONTRIBUTE_INT);
        assertThat(testPfmsEmpMembership.getCurOwnContributeTot()).isEqualTo(UPDATED_CUR_OWN_CONTRIBUTE_TOT);
        assertThat(testPfmsEmpMembership.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPfmsEmpMembership.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPfmsEmpMembership.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPfmsEmpMembership.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPfmsEmpMembership.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePfmsEmpMembership() throws Exception {
        // Initialize the database
        pfmsEmpMembershipRepository.saveAndFlush(pfmsEmpMembership);

		int databaseSizeBeforeDelete = pfmsEmpMembershipRepository.findAll().size();

        // Get the pfmsEmpMembership
        restPfmsEmpMembershipMockMvc.perform(delete("/api/pfmsEmpMemberships/{id}", pfmsEmpMembership.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PfmsEmpMembership> pfmsEmpMemberships = pfmsEmpMembershipRepository.findAll();
        assertThat(pfmsEmpMemberships).hasSize(databaseSizeBeforeDelete - 1);
    }
}
