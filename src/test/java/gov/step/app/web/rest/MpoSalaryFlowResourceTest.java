package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoSalaryFlow;
import gov.step.app.repository.MpoSalaryFlowRepository;
import gov.step.app.repository.search.MpoSalaryFlowSearchRepository;

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
 * Test class for the MpoSalaryFlowResource REST controller.
 *
 * @see MpoSalaryFlowResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoSalaryFlowResourceTest {

    private static final String DEFAULT_REPORT_NAME = "AAAAA";
    private static final String UPDATED_REPORT_NAME = "BBBBB";
    private static final String DEFAULT_URLS = "AAAAA";
    private static final String UPDATED_URLS = "BBBBB";

    private static final Long DEFAULT_FORWARD_TO = 1L;
    private static final Long UPDATED_FORWARD_TO = 2L;

    private static final Long DEFAULT_FORWARD_FROM = 1L;
    private static final Long UPDATED_FORWARD_FROM = 2L;
    private static final String DEFAULT_FORWARD_TO_ROLE = "AAAAA";
    private static final String UPDATED_FORWARD_TO_ROLE = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;
    private static final String DEFAULT_APPROVE_STATUS = "AAAAA";
    private static final String UPDATED_APPROVE_STATUS = "BBBBB";

    private static final Boolean DEFAULT_USER_LOCK = false;
    private static final Boolean UPDATED_USER_LOCK = true;

    private static final Long DEFAULT_LEVELS = 1L;
    private static final Long UPDATED_LEVELS = 2L;
    private static final String DEFAULT_DTE_STATUS = "AAAAA";
    private static final String UPDATED_DTE_STATUS = "BBBBB";

    private static final Long DEFAULT_DTE_ID = 1L;
    private static final Long UPDATED_DTE_ID = 2L;

    private static final LocalDate DEFAULT_DTE_APPROVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DTE_APPROVE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_MINISTRY_STATUS = "AAAAA";
    private static final String UPDATED_MINISTRY_STATUS = "BBBBB";

    private static final Long DEFAULT_MINISTRY_ID = 1L;
    private static final Long UPDATED_MINISTRY_ID = 2L;

    private static final LocalDate DEFAULT_MINISTRY_APPROVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MINISTRY_APPROVE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_AG_STATUS = "AAAAA";
    private static final String UPDATED_AG_STATUS = "BBBBB";

    private static final Long DEFAULT_AG_ID = 1L;
    private static final Long UPDATED_AG_ID = 2L;

    private static final LocalDate DEFAULT_AG_APPROVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AG_APPROVE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DTE_COMMENTS = "AAAAA";
    private static final String UPDATED_DTE_COMMENTS = "BBBBB";
    private static final String DEFAULT_MINISTRY_COMMENTS = "AAAAA";
    private static final String UPDATED_MINISTRY_COMMENTS = "BBBBB";
    private static final String DEFAULT_AG_COMMENTS = "AAAAA";
    private static final String UPDATED_AG_COMMENTS = "BBBBB";
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    @Inject
    private MpoSalaryFlowRepository mpoSalaryFlowRepository;

    @Inject
    private MpoSalaryFlowSearchRepository mpoSalaryFlowSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoSalaryFlowMockMvc;

    private MpoSalaryFlow mpoSalaryFlow;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoSalaryFlowResource mpoSalaryFlowResource = new MpoSalaryFlowResource();
        ReflectionTestUtils.setField(mpoSalaryFlowResource, "mpoSalaryFlowRepository", mpoSalaryFlowRepository);
        ReflectionTestUtils.setField(mpoSalaryFlowResource, "mpoSalaryFlowSearchRepository", mpoSalaryFlowSearchRepository);
        this.restMpoSalaryFlowMockMvc = MockMvcBuilders.standaloneSetup(mpoSalaryFlowResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoSalaryFlow = new MpoSalaryFlow();
        mpoSalaryFlow.setReportName(DEFAULT_REPORT_NAME);
        mpoSalaryFlow.setUrls(DEFAULT_URLS);
        mpoSalaryFlow.setForwardTo(DEFAULT_FORWARD_TO);
        mpoSalaryFlow.setForwardFrom(DEFAULT_FORWARD_FROM);
        mpoSalaryFlow.setForwardToRole(DEFAULT_FORWARD_TO_ROLE);
        mpoSalaryFlow.setStatus(DEFAULT_STATUS);
        mpoSalaryFlow.setApproveStatus(DEFAULT_APPROVE_STATUS);
        mpoSalaryFlow.setUserLock(DEFAULT_USER_LOCK);
        mpoSalaryFlow.setLevels(DEFAULT_LEVELS);
        mpoSalaryFlow.setDteStatus(DEFAULT_DTE_STATUS);
        mpoSalaryFlow.setDteId(DEFAULT_DTE_ID);
        mpoSalaryFlow.setDteApproveDate(DEFAULT_DTE_APPROVE_DATE);
        mpoSalaryFlow.setMinistryStatus(DEFAULT_MINISTRY_STATUS);
        mpoSalaryFlow.setMinistryId(DEFAULT_MINISTRY_ID);
        mpoSalaryFlow.setMinistryApproveDate(DEFAULT_MINISTRY_APPROVE_DATE);
        mpoSalaryFlow.setAgStatus(DEFAULT_AG_STATUS);
        mpoSalaryFlow.setAgId(DEFAULT_AG_ID);
        mpoSalaryFlow.setAgApproveDate(DEFAULT_AG_APPROVE_DATE);
        mpoSalaryFlow.setDteComments(DEFAULT_DTE_COMMENTS);
        mpoSalaryFlow.setMinistryComments(DEFAULT_MINISTRY_COMMENTS);
        mpoSalaryFlow.setAgComments(DEFAULT_AG_COMMENTS);
        mpoSalaryFlow.setComments(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createMpoSalaryFlow() throws Exception {
        int databaseSizeBeforeCreate = mpoSalaryFlowRepository.findAll().size();

        // Create the MpoSalaryFlow

        restMpoSalaryFlowMockMvc.perform(post("/api/mpoSalaryFlows")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoSalaryFlow)))
                .andExpect(status().isCreated());

        // Validate the MpoSalaryFlow in the database
        List<MpoSalaryFlow> mpoSalaryFlows = mpoSalaryFlowRepository.findAll();
        assertThat(mpoSalaryFlows).hasSize(databaseSizeBeforeCreate + 1);
        MpoSalaryFlow testMpoSalaryFlow = mpoSalaryFlows.get(mpoSalaryFlows.size() - 1);
        assertThat(testMpoSalaryFlow.getReportName()).isEqualTo(DEFAULT_REPORT_NAME);
        assertThat(testMpoSalaryFlow.getUrls()).isEqualTo(DEFAULT_URLS);
        assertThat(testMpoSalaryFlow.getForwardTo()).isEqualTo(DEFAULT_FORWARD_TO);
        assertThat(testMpoSalaryFlow.getForwardFrom()).isEqualTo(DEFAULT_FORWARD_FROM);
        assertThat(testMpoSalaryFlow.getForwardToRole()).isEqualTo(DEFAULT_FORWARD_TO_ROLE);
        assertThat(testMpoSalaryFlow.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMpoSalaryFlow.getApproveStatus()).isEqualTo(DEFAULT_APPROVE_STATUS);
        assertThat(testMpoSalaryFlow.getUserLock()).isEqualTo(DEFAULT_USER_LOCK);
        assertThat(testMpoSalaryFlow.getLevels()).isEqualTo(DEFAULT_LEVELS);
        assertThat(testMpoSalaryFlow.getDteStatus()).isEqualTo(DEFAULT_DTE_STATUS);
        assertThat(testMpoSalaryFlow.getDteId()).isEqualTo(DEFAULT_DTE_ID);
        assertThat(testMpoSalaryFlow.getDteApproveDate()).isEqualTo(DEFAULT_DTE_APPROVE_DATE);
        assertThat(testMpoSalaryFlow.getMinistryStatus()).isEqualTo(DEFAULT_MINISTRY_STATUS);
        assertThat(testMpoSalaryFlow.getMinistryId()).isEqualTo(DEFAULT_MINISTRY_ID);
        assertThat(testMpoSalaryFlow.getMinistryApproveDate()).isEqualTo(DEFAULT_MINISTRY_APPROVE_DATE);
        assertThat(testMpoSalaryFlow.getAgStatus()).isEqualTo(DEFAULT_AG_STATUS);
        assertThat(testMpoSalaryFlow.getAgId()).isEqualTo(DEFAULT_AG_ID);
        assertThat(testMpoSalaryFlow.getAgApproveDate()).isEqualTo(DEFAULT_AG_APPROVE_DATE);
        assertThat(testMpoSalaryFlow.getDteComments()).isEqualTo(DEFAULT_DTE_COMMENTS);
        assertThat(testMpoSalaryFlow.getMinistryComments()).isEqualTo(DEFAULT_MINISTRY_COMMENTS);
        assertThat(testMpoSalaryFlow.getAgComments()).isEqualTo(DEFAULT_AG_COMMENTS);
        assertThat(testMpoSalaryFlow.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllMpoSalaryFlows() throws Exception {
        // Initialize the database
        mpoSalaryFlowRepository.saveAndFlush(mpoSalaryFlow);

        // Get all the mpoSalaryFlows
        restMpoSalaryFlowMockMvc.perform(get("/api/mpoSalaryFlows"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoSalaryFlow.getId().intValue())))
                .andExpect(jsonPath("$.[*].reportName").value(hasItem(DEFAULT_REPORT_NAME.toString())))
                .andExpect(jsonPath("$.[*].urls").value(hasItem(DEFAULT_URLS.toString())))
                .andExpect(jsonPath("$.[*].forwardTo").value(hasItem(DEFAULT_FORWARD_TO.intValue())))
                .andExpect(jsonPath("$.[*].forwardFrom").value(hasItem(DEFAULT_FORWARD_FROM.intValue())))
                .andExpect(jsonPath("$.[*].forwardToRole").value(hasItem(DEFAULT_FORWARD_TO_ROLE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].approveStatus").value(hasItem(DEFAULT_APPROVE_STATUS.toString())))
                .andExpect(jsonPath("$.[*].userLock").value(hasItem(DEFAULT_USER_LOCK.booleanValue())))
                .andExpect(jsonPath("$.[*].levels").value(hasItem(DEFAULT_LEVELS.intValue())))
                .andExpect(jsonPath("$.[*].dteStatus").value(hasItem(DEFAULT_DTE_STATUS.toString())))
                .andExpect(jsonPath("$.[*].dteId").value(hasItem(DEFAULT_DTE_ID.intValue())))
                .andExpect(jsonPath("$.[*].dteApproveDate").value(hasItem(DEFAULT_DTE_APPROVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].ministryStatus").value(hasItem(DEFAULT_MINISTRY_STATUS.toString())))
                .andExpect(jsonPath("$.[*].ministryId").value(hasItem(DEFAULT_MINISTRY_ID.intValue())))
                .andExpect(jsonPath("$.[*].ministryApproveDate").value(hasItem(DEFAULT_MINISTRY_APPROVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].agStatus").value(hasItem(DEFAULT_AG_STATUS.toString())))
                .andExpect(jsonPath("$.[*].agId").value(hasItem(DEFAULT_AG_ID.intValue())))
                .andExpect(jsonPath("$.[*].agApproveDate").value(hasItem(DEFAULT_AG_APPROVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].dteComments").value(hasItem(DEFAULT_DTE_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].ministryComments").value(hasItem(DEFAULT_MINISTRY_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].agComments").value(hasItem(DEFAULT_AG_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getMpoSalaryFlow() throws Exception {
        // Initialize the database
        mpoSalaryFlowRepository.saveAndFlush(mpoSalaryFlow);

        // Get the mpoSalaryFlow
        restMpoSalaryFlowMockMvc.perform(get("/api/mpoSalaryFlows/{id}", mpoSalaryFlow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoSalaryFlow.getId().intValue()))
            .andExpect(jsonPath("$.reportName").value(DEFAULT_REPORT_NAME.toString()))
            .andExpect(jsonPath("$.urls").value(DEFAULT_URLS.toString()))
            .andExpect(jsonPath("$.forwardTo").value(DEFAULT_FORWARD_TO.intValue()))
            .andExpect(jsonPath("$.forwardFrom").value(DEFAULT_FORWARD_FROM.intValue()))
            .andExpect(jsonPath("$.forwardToRole").value(DEFAULT_FORWARD_TO_ROLE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.approveStatus").value(DEFAULT_APPROVE_STATUS.toString()))
            .andExpect(jsonPath("$.userLock").value(DEFAULT_USER_LOCK.booleanValue()))
            .andExpect(jsonPath("$.levels").value(DEFAULT_LEVELS.intValue()))
            .andExpect(jsonPath("$.dteStatus").value(DEFAULT_DTE_STATUS.toString()))
            .andExpect(jsonPath("$.dteId").value(DEFAULT_DTE_ID.intValue()))
            .andExpect(jsonPath("$.dteApproveDate").value(DEFAULT_DTE_APPROVE_DATE.toString()))
            .andExpect(jsonPath("$.ministryStatus").value(DEFAULT_MINISTRY_STATUS.toString()))
            .andExpect(jsonPath("$.ministryId").value(DEFAULT_MINISTRY_ID.intValue()))
            .andExpect(jsonPath("$.ministryApproveDate").value(DEFAULT_MINISTRY_APPROVE_DATE.toString()))
            .andExpect(jsonPath("$.agStatus").value(DEFAULT_AG_STATUS.toString()))
            .andExpect(jsonPath("$.agId").value(DEFAULT_AG_ID.intValue()))
            .andExpect(jsonPath("$.agApproveDate").value(DEFAULT_AG_APPROVE_DATE.toString()))
            .andExpect(jsonPath("$.dteComments").value(DEFAULT_DTE_COMMENTS.toString()))
            .andExpect(jsonPath("$.ministryComments").value(DEFAULT_MINISTRY_COMMENTS.toString()))
            .andExpect(jsonPath("$.agComments").value(DEFAULT_AG_COMMENTS.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMpoSalaryFlow() throws Exception {
        // Get the mpoSalaryFlow
        restMpoSalaryFlowMockMvc.perform(get("/api/mpoSalaryFlows/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoSalaryFlow() throws Exception {
        // Initialize the database
        mpoSalaryFlowRepository.saveAndFlush(mpoSalaryFlow);

		int databaseSizeBeforeUpdate = mpoSalaryFlowRepository.findAll().size();

        // Update the mpoSalaryFlow
        mpoSalaryFlow.setReportName(UPDATED_REPORT_NAME);
        mpoSalaryFlow.setUrls(UPDATED_URLS);
        mpoSalaryFlow.setForwardTo(UPDATED_FORWARD_TO);
        mpoSalaryFlow.setForwardFrom(UPDATED_FORWARD_FROM);
        mpoSalaryFlow.setForwardToRole(UPDATED_FORWARD_TO_ROLE);
        mpoSalaryFlow.setStatus(UPDATED_STATUS);
        mpoSalaryFlow.setApproveStatus(UPDATED_APPROVE_STATUS);
        mpoSalaryFlow.setUserLock(UPDATED_USER_LOCK);
        mpoSalaryFlow.setLevels(UPDATED_LEVELS);
        mpoSalaryFlow.setDteStatus(UPDATED_DTE_STATUS);
        mpoSalaryFlow.setDteId(UPDATED_DTE_ID);
        mpoSalaryFlow.setDteApproveDate(UPDATED_DTE_APPROVE_DATE);
        mpoSalaryFlow.setMinistryStatus(UPDATED_MINISTRY_STATUS);
        mpoSalaryFlow.setMinistryId(UPDATED_MINISTRY_ID);
        mpoSalaryFlow.setMinistryApproveDate(UPDATED_MINISTRY_APPROVE_DATE);
        mpoSalaryFlow.setAgStatus(UPDATED_AG_STATUS);
        mpoSalaryFlow.setAgId(UPDATED_AG_ID);
        mpoSalaryFlow.setAgApproveDate(UPDATED_AG_APPROVE_DATE);
        mpoSalaryFlow.setDteComments(UPDATED_DTE_COMMENTS);
        mpoSalaryFlow.setMinistryComments(UPDATED_MINISTRY_COMMENTS);
        mpoSalaryFlow.setAgComments(UPDATED_AG_COMMENTS);
        mpoSalaryFlow.setComments(UPDATED_COMMENTS);

        restMpoSalaryFlowMockMvc.perform(put("/api/mpoSalaryFlows")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoSalaryFlow)))
                .andExpect(status().isOk());

        // Validate the MpoSalaryFlow in the database
        List<MpoSalaryFlow> mpoSalaryFlows = mpoSalaryFlowRepository.findAll();
        assertThat(mpoSalaryFlows).hasSize(databaseSizeBeforeUpdate);
        MpoSalaryFlow testMpoSalaryFlow = mpoSalaryFlows.get(mpoSalaryFlows.size() - 1);
        assertThat(testMpoSalaryFlow.getReportName()).isEqualTo(UPDATED_REPORT_NAME);
        assertThat(testMpoSalaryFlow.getUrls()).isEqualTo(UPDATED_URLS);
        assertThat(testMpoSalaryFlow.getForwardTo()).isEqualTo(UPDATED_FORWARD_TO);
        assertThat(testMpoSalaryFlow.getForwardFrom()).isEqualTo(UPDATED_FORWARD_FROM);
        assertThat(testMpoSalaryFlow.getForwardToRole()).isEqualTo(UPDATED_FORWARD_TO_ROLE);
        assertThat(testMpoSalaryFlow.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMpoSalaryFlow.getApproveStatus()).isEqualTo(UPDATED_APPROVE_STATUS);
        assertThat(testMpoSalaryFlow.getUserLock()).isEqualTo(UPDATED_USER_LOCK);
        assertThat(testMpoSalaryFlow.getLevels()).isEqualTo(UPDATED_LEVELS);
        assertThat(testMpoSalaryFlow.getDteStatus()).isEqualTo(UPDATED_DTE_STATUS);
        assertThat(testMpoSalaryFlow.getDteId()).isEqualTo(UPDATED_DTE_ID);
        assertThat(testMpoSalaryFlow.getDteApproveDate()).isEqualTo(UPDATED_DTE_APPROVE_DATE);
        assertThat(testMpoSalaryFlow.getMinistryStatus()).isEqualTo(UPDATED_MINISTRY_STATUS);
        assertThat(testMpoSalaryFlow.getMinistryId()).isEqualTo(UPDATED_MINISTRY_ID);
        assertThat(testMpoSalaryFlow.getMinistryApproveDate()).isEqualTo(UPDATED_MINISTRY_APPROVE_DATE);
        assertThat(testMpoSalaryFlow.getAgStatus()).isEqualTo(UPDATED_AG_STATUS);
        assertThat(testMpoSalaryFlow.getAgId()).isEqualTo(UPDATED_AG_ID);
        assertThat(testMpoSalaryFlow.getAgApproveDate()).isEqualTo(UPDATED_AG_APPROVE_DATE);
        assertThat(testMpoSalaryFlow.getDteComments()).isEqualTo(UPDATED_DTE_COMMENTS);
        assertThat(testMpoSalaryFlow.getMinistryComments()).isEqualTo(UPDATED_MINISTRY_COMMENTS);
        assertThat(testMpoSalaryFlow.getAgComments()).isEqualTo(UPDATED_AG_COMMENTS);
        assertThat(testMpoSalaryFlow.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void deleteMpoSalaryFlow() throws Exception {
        // Initialize the database
        mpoSalaryFlowRepository.saveAndFlush(mpoSalaryFlow);

		int databaseSizeBeforeDelete = mpoSalaryFlowRepository.findAll().size();

        // Get the mpoSalaryFlow
        restMpoSalaryFlowMockMvc.perform(delete("/api/mpoSalaryFlows/{id}", mpoSalaryFlow.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoSalaryFlow> mpoSalaryFlows = mpoSalaryFlowRepository.findAll();
        assertThat(mpoSalaryFlows).hasSize(databaseSizeBeforeDelete - 1);
    }
}
