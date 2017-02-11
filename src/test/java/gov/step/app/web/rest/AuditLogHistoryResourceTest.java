package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AuditLogHistory;
import gov.step.app.repository.AuditLogHistoryRepository;
import gov.step.app.repository.search.AuditLogHistorySearchRepository;

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
 * Test class for the AuditLogHistoryResource REST controller.
 *
 * @see AuditLogHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AuditLogHistoryResourceTest {

    private static final String DEFAULT_COL_NAME = "AAAAA";
    private static final String UPDATED_COL_NAME = "BBBBB";
    private static final String DEFAULT_VALUE_BEFORE = "AAAAA";
    private static final String UPDATED_VALUE_BEFORE = "BBBBB";
    private static final String DEFAULT_VALUE_AFTER = "AAAAA";
    private static final String UPDATED_VALUE_AFTER = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";
    private static final String DEFAULT_USER_ID = "AAAAA";
    private static final String UPDATED_USER_ID = "BBBBB";
    private static final String DEFAULT_ENTITY_NAME = "AAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBB";

    @Inject
    private AuditLogHistoryRepository auditLogHistoryRepository;

    @Inject
    private AuditLogHistorySearchRepository auditLogHistorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAuditLogHistoryMockMvc;

    private AuditLogHistory auditLogHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuditLogHistoryResource auditLogHistoryResource = new AuditLogHistoryResource();
        ReflectionTestUtils.setField(auditLogHistoryResource, "auditLogHistoryRepository", auditLogHistoryRepository);
        ReflectionTestUtils.setField(auditLogHistoryResource, "auditLogHistorySearchRepository", auditLogHistorySearchRepository);
        this.restAuditLogHistoryMockMvc = MockMvcBuilders.standaloneSetup(auditLogHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        auditLogHistory = new AuditLogHistory();
        auditLogHistory.setColName(DEFAULT_COL_NAME);
        auditLogHistory.setValueBefore(DEFAULT_VALUE_BEFORE);
        auditLogHistory.setValueAfter(DEFAULT_VALUE_AFTER);
        auditLogHistory.setStatus(DEFAULT_STATUS);
        auditLogHistory.setCreateBy(DEFAULT_CREATE_BY);
        auditLogHistory.setCreateDate(DEFAULT_CREATE_DATE);
        auditLogHistory.setUpdateBy(DEFAULT_UPDATE_BY);
        auditLogHistory.setUpdateDate(DEFAULT_UPDATE_DATE);
        auditLogHistory.setRemarks(DEFAULT_REMARKS);
        auditLogHistory.setUserId(DEFAULT_USER_ID);
        auditLogHistory.setEntityName(DEFAULT_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void createAuditLogHistory() throws Exception {
        int databaseSizeBeforeCreate = auditLogHistoryRepository.findAll().size();

        // Create the AuditLogHistory

        restAuditLogHistoryMockMvc.perform(post("/api/auditLogHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(auditLogHistory)))
                .andExpect(status().isCreated());

        // Validate the AuditLogHistory in the database
        List<AuditLogHistory> auditLogHistorys = auditLogHistoryRepository.findAll();
        assertThat(auditLogHistorys).hasSize(databaseSizeBeforeCreate + 1);
        AuditLogHistory testAuditLogHistory = auditLogHistorys.get(auditLogHistorys.size() - 1);
        assertThat(testAuditLogHistory.getColName()).isEqualTo(DEFAULT_COL_NAME);
        assertThat(testAuditLogHistory.getValueBefore()).isEqualTo(DEFAULT_VALUE_BEFORE);
        assertThat(testAuditLogHistory.getValueAfter()).isEqualTo(DEFAULT_VALUE_AFTER);
        assertThat(testAuditLogHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAuditLogHistory.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testAuditLogHistory.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testAuditLogHistory.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testAuditLogHistory.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testAuditLogHistory.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAuditLogHistory.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAuditLogHistory.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllAuditLogHistorys() throws Exception {
        // Initialize the database
        auditLogHistoryRepository.saveAndFlush(auditLogHistory);

        // Get all the auditLogHistorys
        restAuditLogHistoryMockMvc.perform(get("/api/auditLogHistorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(auditLogHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].colName").value(hasItem(DEFAULT_COL_NAME.toString())))
                .andExpect(jsonPath("$.[*].valueBefore").value(hasItem(DEFAULT_VALUE_BEFORE.toString())))
                .andExpect(jsonPath("$.[*].valueAfter").value(hasItem(DEFAULT_VALUE_AFTER.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
                .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuditLogHistory() throws Exception {
        // Initialize the database
        auditLogHistoryRepository.saveAndFlush(auditLogHistory);

        // Get the auditLogHistory
        restAuditLogHistoryMockMvc.perform(get("/api/auditLogHistorys/{id}", auditLogHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(auditLogHistory.getId().intValue()))
            .andExpect(jsonPath("$.colName").value(DEFAULT_COL_NAME.toString()))
            .andExpect(jsonPath("$.valueBefore").value(DEFAULT_VALUE_BEFORE.toString()))
            .andExpect(jsonPath("$.valueAfter").value(DEFAULT_VALUE_AFTER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuditLogHistory() throws Exception {
        // Get the auditLogHistory
        restAuditLogHistoryMockMvc.perform(get("/api/auditLogHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditLogHistory() throws Exception {
        // Initialize the database
        auditLogHistoryRepository.saveAndFlush(auditLogHistory);

		int databaseSizeBeforeUpdate = auditLogHistoryRepository.findAll().size();

        // Update the auditLogHistory
        auditLogHistory.setColName(UPDATED_COL_NAME);
        auditLogHistory.setValueBefore(UPDATED_VALUE_BEFORE);
        auditLogHistory.setValueAfter(UPDATED_VALUE_AFTER);
        auditLogHistory.setStatus(UPDATED_STATUS);
        auditLogHistory.setCreateBy(UPDATED_CREATE_BY);
        auditLogHistory.setCreateDate(UPDATED_CREATE_DATE);
        auditLogHistory.setUpdateBy(UPDATED_UPDATE_BY);
        auditLogHistory.setUpdateDate(UPDATED_UPDATE_DATE);
        auditLogHistory.setRemarks(UPDATED_REMARKS);
        auditLogHistory.setUserId(UPDATED_USER_ID);
        auditLogHistory.setEntityName(UPDATED_ENTITY_NAME);

        restAuditLogHistoryMockMvc.perform(put("/api/auditLogHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(auditLogHistory)))
                .andExpect(status().isOk());

        // Validate the AuditLogHistory in the database
        List<AuditLogHistory> auditLogHistorys = auditLogHistoryRepository.findAll();
        assertThat(auditLogHistorys).hasSize(databaseSizeBeforeUpdate);
        AuditLogHistory testAuditLogHistory = auditLogHistorys.get(auditLogHistorys.size() - 1);
        assertThat(testAuditLogHistory.getColName()).isEqualTo(UPDATED_COL_NAME);
        assertThat(testAuditLogHistory.getValueBefore()).isEqualTo(UPDATED_VALUE_BEFORE);
        assertThat(testAuditLogHistory.getValueAfter()).isEqualTo(UPDATED_VALUE_AFTER);
        assertThat(testAuditLogHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuditLogHistory.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testAuditLogHistory.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testAuditLogHistory.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testAuditLogHistory.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testAuditLogHistory.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAuditLogHistory.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAuditLogHistory.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void deleteAuditLogHistory() throws Exception {
        // Initialize the database
        auditLogHistoryRepository.saveAndFlush(auditLogHistory);

		int databaseSizeBeforeDelete = auditLogHistoryRepository.findAll().size();

        // Get the auditLogHistory
        restAuditLogHistoryMockMvc.perform(delete("/api/auditLogHistorys/{id}", auditLogHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AuditLogHistory> auditLogHistorys = auditLogHistoryRepository.findAll();
        assertThat(auditLogHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
