package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PfmsEmpAdjustment;
import gov.step.app.repository.PfmsEmpAdjustmentRepository;
import gov.step.app.repository.search.PfmsEmpAdjustmentSearchRepository;

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
 * Test class for the PfmsEmpAdjustmentResource REST controller.
 *
 * @see PfmsEmpAdjustmentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PfmsEmpAdjustmentResourceTest {


    private static final Boolean DEFAULT_IS_CURRENT_BALANCE = false;
    private static final Boolean UPDATED_IS_CURRENT_BALANCE = true;

    private static final Double DEFAULT_OWN_CONTRIBUTE = 1D;
    private static final Double UPDATED_OWN_CONTRIBUTE = 2D;

    private static final Double DEFAULT_OWN_CONTRIBUTE_INT = 1D;
    private static final Double UPDATED_OWN_CONTRIBUTE_INT = 2D;

    private static final Double DEFAULT_PRE_OWN_CONTRIBUTE = 1D;
    private static final Double UPDATED_PRE_OWN_CONTRIBUTE = 2D;

    private static final Double DEFAULT_PRE_OWN_CONTRIBUTE_INT = 1D;
    private static final Double UPDATED_PRE_OWN_CONTRIBUTE_INT = 2D;
    private static final String DEFAULT_REASON = "AAAAA";
    private static final String UPDATED_REASON = "BBBBB";

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
    private PfmsEmpAdjustmentRepository pfmsEmpAdjustmentRepository;

    @Inject
    private PfmsEmpAdjustmentSearchRepository pfmsEmpAdjustmentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPfmsEmpAdjustmentMockMvc;

    private PfmsEmpAdjustment pfmsEmpAdjustment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PfmsEmpAdjustmentResource pfmsEmpAdjustmentResource = new PfmsEmpAdjustmentResource();
        ReflectionTestUtils.setField(pfmsEmpAdjustmentResource, "pfmsEmpAdjustmentRepository", pfmsEmpAdjustmentRepository);
        ReflectionTestUtils.setField(pfmsEmpAdjustmentResource, "pfmsEmpAdjustmentSearchRepository", pfmsEmpAdjustmentSearchRepository);
        this.restPfmsEmpAdjustmentMockMvc = MockMvcBuilders.standaloneSetup(pfmsEmpAdjustmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pfmsEmpAdjustment = new PfmsEmpAdjustment();
        pfmsEmpAdjustment.setIsCurrentBalance(DEFAULT_IS_CURRENT_BALANCE);
        pfmsEmpAdjustment.setOwnContribute(DEFAULT_OWN_CONTRIBUTE);
        pfmsEmpAdjustment.setOwnContributeInt(DEFAULT_OWN_CONTRIBUTE_INT);
        pfmsEmpAdjustment.setPreOwnContribute(DEFAULT_PRE_OWN_CONTRIBUTE);
        pfmsEmpAdjustment.setPreOwnContributeInt(DEFAULT_PRE_OWN_CONTRIBUTE_INT);
        pfmsEmpAdjustment.setReason(DEFAULT_REASON);
        pfmsEmpAdjustment.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pfmsEmpAdjustment.setCreateDate(DEFAULT_CREATE_DATE);
        pfmsEmpAdjustment.setCreateBy(DEFAULT_CREATE_BY);
        pfmsEmpAdjustment.setUpdateDate(DEFAULT_UPDATE_DATE);
        pfmsEmpAdjustment.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPfmsEmpAdjustment() throws Exception {
        int databaseSizeBeforeCreate = pfmsEmpAdjustmentRepository.findAll().size();

        // Create the PfmsEmpAdjustment

        restPfmsEmpAdjustmentMockMvc.perform(post("/api/pfmsEmpAdjustments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpAdjustment)))
                .andExpect(status().isCreated());

        // Validate the PfmsEmpAdjustment in the database
        List<PfmsEmpAdjustment> pfmsEmpAdjustments = pfmsEmpAdjustmentRepository.findAll();
        assertThat(pfmsEmpAdjustments).hasSize(databaseSizeBeforeCreate + 1);
        PfmsEmpAdjustment testPfmsEmpAdjustment = pfmsEmpAdjustments.get(pfmsEmpAdjustments.size() - 1);
        assertThat(testPfmsEmpAdjustment.getIsCurrentBalance()).isEqualTo(DEFAULT_IS_CURRENT_BALANCE);
        assertThat(testPfmsEmpAdjustment.getOwnContribute()).isEqualTo(DEFAULT_OWN_CONTRIBUTE);
        assertThat(testPfmsEmpAdjustment.getOwnContributeInt()).isEqualTo(DEFAULT_OWN_CONTRIBUTE_INT);
        assertThat(testPfmsEmpAdjustment.getPreOwnContribute()).isEqualTo(DEFAULT_PRE_OWN_CONTRIBUTE);
        assertThat(testPfmsEmpAdjustment.getPreOwnContributeInt()).isEqualTo(DEFAULT_PRE_OWN_CONTRIBUTE_INT);
        assertThat(testPfmsEmpAdjustment.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testPfmsEmpAdjustment.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPfmsEmpAdjustment.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPfmsEmpAdjustment.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPfmsEmpAdjustment.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPfmsEmpAdjustment.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkIsCurrentBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpAdjustmentRepository.findAll().size();
        // set the field null
        pfmsEmpAdjustment.setIsCurrentBalance(null);

        // Create the PfmsEmpAdjustment, which fails.

        restPfmsEmpAdjustmentMockMvc.perform(post("/api/pfmsEmpAdjustments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpAdjustment)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpAdjustment> pfmsEmpAdjustments = pfmsEmpAdjustmentRepository.findAll();
        assertThat(pfmsEmpAdjustments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOwnContributeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpAdjustmentRepository.findAll().size();
        // set the field null
        pfmsEmpAdjustment.setOwnContribute(null);

        // Create the PfmsEmpAdjustment, which fails.

        restPfmsEmpAdjustmentMockMvc.perform(post("/api/pfmsEmpAdjustments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpAdjustment)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpAdjustment> pfmsEmpAdjustments = pfmsEmpAdjustmentRepository.findAll();
        assertThat(pfmsEmpAdjustments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOwnContributeIntIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpAdjustmentRepository.findAll().size();
        // set the field null
        pfmsEmpAdjustment.setOwnContributeInt(null);

        // Create the PfmsEmpAdjustment, which fails.

        restPfmsEmpAdjustmentMockMvc.perform(post("/api/pfmsEmpAdjustments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpAdjustment)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpAdjustment> pfmsEmpAdjustments = pfmsEmpAdjustmentRepository.findAll();
        assertThat(pfmsEmpAdjustments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreOwnContributeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpAdjustmentRepository.findAll().size();
        // set the field null
        pfmsEmpAdjustment.setPreOwnContribute(null);

        // Create the PfmsEmpAdjustment, which fails.

        restPfmsEmpAdjustmentMockMvc.perform(post("/api/pfmsEmpAdjustments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpAdjustment)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpAdjustment> pfmsEmpAdjustments = pfmsEmpAdjustmentRepository.findAll();
        assertThat(pfmsEmpAdjustments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreOwnContributeIntIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpAdjustmentRepository.findAll().size();
        // set the field null
        pfmsEmpAdjustment.setPreOwnContributeInt(null);

        // Create the PfmsEmpAdjustment, which fails.

        restPfmsEmpAdjustmentMockMvc.perform(post("/api/pfmsEmpAdjustments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpAdjustment)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpAdjustment> pfmsEmpAdjustments = pfmsEmpAdjustmentRepository.findAll();
        assertThat(pfmsEmpAdjustments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpAdjustmentRepository.findAll().size();
        // set the field null
        pfmsEmpAdjustment.setReason(null);

        // Create the PfmsEmpAdjustment, which fails.

        restPfmsEmpAdjustmentMockMvc.perform(post("/api/pfmsEmpAdjustments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpAdjustment)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpAdjustment> pfmsEmpAdjustments = pfmsEmpAdjustmentRepository.findAll();
        assertThat(pfmsEmpAdjustments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsEmpAdjustmentRepository.findAll().size();
        // set the field null
        pfmsEmpAdjustment.setActiveStatus(null);

        // Create the PfmsEmpAdjustment, which fails.

        restPfmsEmpAdjustmentMockMvc.perform(post("/api/pfmsEmpAdjustments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpAdjustment)))
                .andExpect(status().isBadRequest());

        List<PfmsEmpAdjustment> pfmsEmpAdjustments = pfmsEmpAdjustmentRepository.findAll();
        assertThat(pfmsEmpAdjustments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPfmsEmpAdjustments() throws Exception {
        // Initialize the database
        pfmsEmpAdjustmentRepository.saveAndFlush(pfmsEmpAdjustment);

        // Get all the pfmsEmpAdjustments
        restPfmsEmpAdjustmentMockMvc.perform(get("/api/pfmsEmpAdjustments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pfmsEmpAdjustment.getId().intValue())))
                .andExpect(jsonPath("$.[*].isCurrentBalance").value(hasItem(DEFAULT_IS_CURRENT_BALANCE.booleanValue())))
                .andExpect(jsonPath("$.[*].ownContribute").value(hasItem(DEFAULT_OWN_CONTRIBUTE.doubleValue())))
                .andExpect(jsonPath("$.[*].ownContributeInt").value(hasItem(DEFAULT_OWN_CONTRIBUTE_INT.doubleValue())))
                .andExpect(jsonPath("$.[*].preOwnContribute").value(hasItem(DEFAULT_PRE_OWN_CONTRIBUTE.doubleValue())))
                .andExpect(jsonPath("$.[*].preOwnContributeInt").value(hasItem(DEFAULT_PRE_OWN_CONTRIBUTE_INT.doubleValue())))
                .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPfmsEmpAdjustment() throws Exception {
        // Initialize the database
        pfmsEmpAdjustmentRepository.saveAndFlush(pfmsEmpAdjustment);

        // Get the pfmsEmpAdjustment
        restPfmsEmpAdjustmentMockMvc.perform(get("/api/pfmsEmpAdjustments/{id}", pfmsEmpAdjustment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pfmsEmpAdjustment.getId().intValue()))
            .andExpect(jsonPath("$.isCurrentBalance").value(DEFAULT_IS_CURRENT_BALANCE.booleanValue()))
            .andExpect(jsonPath("$.ownContribute").value(DEFAULT_OWN_CONTRIBUTE.doubleValue()))
            .andExpect(jsonPath("$.ownContributeInt").value(DEFAULT_OWN_CONTRIBUTE_INT.doubleValue()))
            .andExpect(jsonPath("$.preOwnContribute").value(DEFAULT_PRE_OWN_CONTRIBUTE.doubleValue()))
            .andExpect(jsonPath("$.preOwnContributeInt").value(DEFAULT_PRE_OWN_CONTRIBUTE_INT.doubleValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPfmsEmpAdjustment() throws Exception {
        // Get the pfmsEmpAdjustment
        restPfmsEmpAdjustmentMockMvc.perform(get("/api/pfmsEmpAdjustments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePfmsEmpAdjustment() throws Exception {
        // Initialize the database
        pfmsEmpAdjustmentRepository.saveAndFlush(pfmsEmpAdjustment);

		int databaseSizeBeforeUpdate = pfmsEmpAdjustmentRepository.findAll().size();

        // Update the pfmsEmpAdjustment
        pfmsEmpAdjustment.setIsCurrentBalance(UPDATED_IS_CURRENT_BALANCE);
        pfmsEmpAdjustment.setOwnContribute(UPDATED_OWN_CONTRIBUTE);
        pfmsEmpAdjustment.setOwnContributeInt(UPDATED_OWN_CONTRIBUTE_INT);
        pfmsEmpAdjustment.setPreOwnContribute(UPDATED_PRE_OWN_CONTRIBUTE);
        pfmsEmpAdjustment.setPreOwnContributeInt(UPDATED_PRE_OWN_CONTRIBUTE_INT);
        pfmsEmpAdjustment.setReason(UPDATED_REASON);
        pfmsEmpAdjustment.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pfmsEmpAdjustment.setCreateDate(UPDATED_CREATE_DATE);
        pfmsEmpAdjustment.setCreateBy(UPDATED_CREATE_BY);
        pfmsEmpAdjustment.setUpdateDate(UPDATED_UPDATE_DATE);
        pfmsEmpAdjustment.setUpdateBy(UPDATED_UPDATE_BY);

        restPfmsEmpAdjustmentMockMvc.perform(put("/api/pfmsEmpAdjustments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsEmpAdjustment)))
                .andExpect(status().isOk());

        // Validate the PfmsEmpAdjustment in the database
        List<PfmsEmpAdjustment> pfmsEmpAdjustments = pfmsEmpAdjustmentRepository.findAll();
        assertThat(pfmsEmpAdjustments).hasSize(databaseSizeBeforeUpdate);
        PfmsEmpAdjustment testPfmsEmpAdjustment = pfmsEmpAdjustments.get(pfmsEmpAdjustments.size() - 1);
        assertThat(testPfmsEmpAdjustment.getIsCurrentBalance()).isEqualTo(UPDATED_IS_CURRENT_BALANCE);
        assertThat(testPfmsEmpAdjustment.getOwnContribute()).isEqualTo(UPDATED_OWN_CONTRIBUTE);
        assertThat(testPfmsEmpAdjustment.getOwnContributeInt()).isEqualTo(UPDATED_OWN_CONTRIBUTE_INT);
        assertThat(testPfmsEmpAdjustment.getPreOwnContribute()).isEqualTo(UPDATED_PRE_OWN_CONTRIBUTE);
        assertThat(testPfmsEmpAdjustment.getPreOwnContributeInt()).isEqualTo(UPDATED_PRE_OWN_CONTRIBUTE_INT);
        assertThat(testPfmsEmpAdjustment.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPfmsEmpAdjustment.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPfmsEmpAdjustment.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPfmsEmpAdjustment.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPfmsEmpAdjustment.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPfmsEmpAdjustment.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePfmsEmpAdjustment() throws Exception {
        // Initialize the database
        pfmsEmpAdjustmentRepository.saveAndFlush(pfmsEmpAdjustment);

		int databaseSizeBeforeDelete = pfmsEmpAdjustmentRepository.findAll().size();

        // Get the pfmsEmpAdjustment
        restPfmsEmpAdjustmentMockMvc.perform(delete("/api/pfmsEmpAdjustments/{id}", pfmsEmpAdjustment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PfmsEmpAdjustment> pfmsEmpAdjustments = pfmsEmpAdjustmentRepository.findAll();
        assertThat(pfmsEmpAdjustments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
