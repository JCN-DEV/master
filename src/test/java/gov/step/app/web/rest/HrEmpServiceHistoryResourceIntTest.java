package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpServiceHistory;
import gov.step.app.repository.HrEmpServiceHistoryRepository;
import gov.step.app.repository.search.HrEmpServiceHistorySearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrEmpServiceHistoryResource REST controller.
 *
 * @see HrEmpServiceHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpServiceHistoryResourceIntTest {


    private static final LocalDate DEFAULT_SERVICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SERVICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_GAZETTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GAZETTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENCADREMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENCADREMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_NATIONAL_SENIORITY = "AAAAA";
    private static final String UPDATED_NATIONAL_SENIORITY = "BBBBB";
    private static final String DEFAULT_CADRE_NUMBER = "AAAAA";
    private static final String UPDATED_CADRE_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_GO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_GO_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GO_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_GO_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GO_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_GO_DOC_NAME = "AAAAA";
    private static final String UPDATED_GO_DOC_NAME = "BBBBB";

    private static final Long DEFAULT_LOG_ID = 1L;
    private static final Long UPDATED_LOG_ID = 2L;

    private static final Long DEFAULT_LOG_STATUS = 1L;
    private static final Long UPDATED_LOG_STATUS = 2L;

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
    private HrEmpServiceHistoryRepository hrEmpServiceHistoryRepository;

    @Inject
    private HrEmpServiceHistorySearchRepository hrEmpServiceHistorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpServiceHistoryMockMvc;

    private HrEmpServiceHistory hrEmpServiceHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpServiceHistoryResource hrEmpServiceHistoryResource = new HrEmpServiceHistoryResource();
        ReflectionTestUtils.setField(hrEmpServiceHistoryResource, "hrEmpServiceHistorySearchRepository", hrEmpServiceHistorySearchRepository);
        ReflectionTestUtils.setField(hrEmpServiceHistoryResource, "hrEmpServiceHistoryRepository", hrEmpServiceHistoryRepository);
        this.restHrEmpServiceHistoryMockMvc = MockMvcBuilders.standaloneSetup(hrEmpServiceHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpServiceHistory = new HrEmpServiceHistory();
        hrEmpServiceHistory.setServiceDate(DEFAULT_SERVICE_DATE);
        hrEmpServiceHistory.setGazettedDate(DEFAULT_GAZETTED_DATE);
        hrEmpServiceHistory.setEncadrementDate(DEFAULT_ENCADREMENT_DATE);
        hrEmpServiceHistory.setNationalSeniority(DEFAULT_NATIONAL_SENIORITY);
        hrEmpServiceHistory.setCadreNumber(DEFAULT_CADRE_NUMBER);
        hrEmpServiceHistory.setGoDate(DEFAULT_GO_DATE);
        hrEmpServiceHistory.setGoDoc(DEFAULT_GO_DOC);
        hrEmpServiceHistory.setGoDocContentType(DEFAULT_GO_DOC_CONTENT_TYPE);
        hrEmpServiceHistory.setGoDocName(DEFAULT_GO_DOC_NAME);
        hrEmpServiceHistory.setLogId(DEFAULT_LOG_ID);
        hrEmpServiceHistory.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpServiceHistory.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpServiceHistory.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpServiceHistory.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpServiceHistory.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpServiceHistory.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpServiceHistory() throws Exception {
        int databaseSizeBeforeCreate = hrEmpServiceHistoryRepository.findAll().size();

        // Create the HrEmpServiceHistory

        restHrEmpServiceHistoryMockMvc.perform(post("/api/hrEmpServiceHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpServiceHistory)))
                .andExpect(status().isCreated());

        // Validate the HrEmpServiceHistory in the database
        List<HrEmpServiceHistory> hrEmpServiceHistorys = hrEmpServiceHistoryRepository.findAll();
        assertThat(hrEmpServiceHistorys).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpServiceHistory testHrEmpServiceHistory = hrEmpServiceHistorys.get(hrEmpServiceHistorys.size() - 1);
        assertThat(testHrEmpServiceHistory.getServiceDate()).isEqualTo(DEFAULT_SERVICE_DATE);
        assertThat(testHrEmpServiceHistory.getGazettedDate()).isEqualTo(DEFAULT_GAZETTED_DATE);
        assertThat(testHrEmpServiceHistory.getEncadrementDate()).isEqualTo(DEFAULT_ENCADREMENT_DATE);
        assertThat(testHrEmpServiceHistory.getNationalSeniority()).isEqualTo(DEFAULT_NATIONAL_SENIORITY);
        assertThat(testHrEmpServiceHistory.getCadreNumber()).isEqualTo(DEFAULT_CADRE_NUMBER);
        assertThat(testHrEmpServiceHistory.getGoDate()).isEqualTo(DEFAULT_GO_DATE);
        assertThat(testHrEmpServiceHistory.getGoDoc()).isEqualTo(DEFAULT_GO_DOC);
        assertThat(testHrEmpServiceHistory.getGoDocContentType()).isEqualTo(DEFAULT_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpServiceHistory.getGoDocName()).isEqualTo(DEFAULT_GO_DOC_NAME);
        assertThat(testHrEmpServiceHistory.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpServiceHistory.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpServiceHistory.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpServiceHistory.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpServiceHistory.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpServiceHistory.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpServiceHistory.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkServiceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpServiceHistoryRepository.findAll().size();
        // set the field null
        hrEmpServiceHistory.setServiceDate(null);

        // Create the HrEmpServiceHistory, which fails.

        restHrEmpServiceHistoryMockMvc.perform(post("/api/hrEmpServiceHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpServiceHistory)))
                .andExpect(status().isBadRequest());

        List<HrEmpServiceHistory> hrEmpServiceHistorys = hrEmpServiceHistoryRepository.findAll();
        assertThat(hrEmpServiceHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGazettedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpServiceHistoryRepository.findAll().size();
        // set the field null
        hrEmpServiceHistory.setGazettedDate(null);

        // Create the HrEmpServiceHistory, which fails.

        restHrEmpServiceHistoryMockMvc.perform(post("/api/hrEmpServiceHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpServiceHistory)))
                .andExpect(status().isBadRequest());

        List<HrEmpServiceHistory> hrEmpServiceHistorys = hrEmpServiceHistoryRepository.findAll();
        assertThat(hrEmpServiceHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEncadrementDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpServiceHistoryRepository.findAll().size();
        // set the field null
        hrEmpServiceHistory.setEncadrementDate(null);

        // Create the HrEmpServiceHistory, which fails.

        restHrEmpServiceHistoryMockMvc.perform(post("/api/hrEmpServiceHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpServiceHistory)))
                .andExpect(status().isBadRequest());

        List<HrEmpServiceHistory> hrEmpServiceHistorys = hrEmpServiceHistoryRepository.findAll();
        assertThat(hrEmpServiceHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpServiceHistoryRepository.findAll().size();
        // set the field null
        hrEmpServiceHistory.setActiveStatus(null);

        // Create the HrEmpServiceHistory, which fails.

        restHrEmpServiceHistoryMockMvc.perform(post("/api/hrEmpServiceHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpServiceHistory)))
                .andExpect(status().isBadRequest());

        List<HrEmpServiceHistory> hrEmpServiceHistorys = hrEmpServiceHistoryRepository.findAll();
        assertThat(hrEmpServiceHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpServiceHistorys() throws Exception {
        // Initialize the database
        hrEmpServiceHistoryRepository.saveAndFlush(hrEmpServiceHistory);

        // Get all the hrEmpServiceHistorys
        restHrEmpServiceHistoryMockMvc.perform(get("/api/hrEmpServiceHistorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpServiceHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].serviceDate").value(hasItem(DEFAULT_SERVICE_DATE.toString())))
                .andExpect(jsonPath("$.[*].gazettedDate").value(hasItem(DEFAULT_GAZETTED_DATE.toString())))
                .andExpect(jsonPath("$.[*].encadrementDate").value(hasItem(DEFAULT_ENCADREMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].nationalSeniority").value(hasItem(DEFAULT_NATIONAL_SENIORITY.toString())))
                .andExpect(jsonPath("$.[*].cadreNumber").value(hasItem(DEFAULT_CADRE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].goDate").value(hasItem(DEFAULT_GO_DATE.toString())))
                .andExpect(jsonPath("$.[*].goDocContentType").value(hasItem(DEFAULT_GO_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].goDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_GO_DOC))))
                .andExpect(jsonPath("$.[*].goDocName").value(hasItem(DEFAULT_GO_DOC_NAME.toString())))
                .andExpect(jsonPath("$.[*].logId").value(hasItem(DEFAULT_LOG_ID.intValue())))
                .andExpect(jsonPath("$.[*].logStatus").value(hasItem(DEFAULT_LOG_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmpServiceHistory() throws Exception {
        // Initialize the database
        hrEmpServiceHistoryRepository.saveAndFlush(hrEmpServiceHistory);

        // Get the hrEmpServiceHistory
        restHrEmpServiceHistoryMockMvc.perform(get("/api/hrEmpServiceHistorys/{id}", hrEmpServiceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpServiceHistory.getId().intValue()))
            .andExpect(jsonPath("$.serviceDate").value(DEFAULT_SERVICE_DATE.toString()))
            .andExpect(jsonPath("$.gazettedDate").value(DEFAULT_GAZETTED_DATE.toString()))
            .andExpect(jsonPath("$.encadrementDate").value(DEFAULT_ENCADREMENT_DATE.toString()))
            .andExpect(jsonPath("$.nationalSeniority").value(DEFAULT_NATIONAL_SENIORITY.toString()))
            .andExpect(jsonPath("$.cadreNumber").value(DEFAULT_CADRE_NUMBER.toString()))
            .andExpect(jsonPath("$.goDate").value(DEFAULT_GO_DATE.toString()))
            .andExpect(jsonPath("$.goDocContentType").value(DEFAULT_GO_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.goDoc").value(Base64Utils.encodeToString(DEFAULT_GO_DOC)))
            .andExpect(jsonPath("$.goDocName").value(DEFAULT_GO_DOC_NAME.toString()))
            .andExpect(jsonPath("$.logId").value(DEFAULT_LOG_ID.intValue()))
            .andExpect(jsonPath("$.logStatus").value(DEFAULT_LOG_STATUS.intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmpServiceHistory() throws Exception {
        // Get the hrEmpServiceHistory
        restHrEmpServiceHistoryMockMvc.perform(get("/api/hrEmpServiceHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpServiceHistory() throws Exception {
        // Initialize the database
        hrEmpServiceHistoryRepository.saveAndFlush(hrEmpServiceHistory);

		int databaseSizeBeforeUpdate = hrEmpServiceHistoryRepository.findAll().size();

        // Update the hrEmpServiceHistory
        hrEmpServiceHistory.setServiceDate(UPDATED_SERVICE_DATE);
        hrEmpServiceHistory.setGazettedDate(UPDATED_GAZETTED_DATE);
        hrEmpServiceHistory.setEncadrementDate(UPDATED_ENCADREMENT_DATE);
        hrEmpServiceHistory.setNationalSeniority(UPDATED_NATIONAL_SENIORITY);
        hrEmpServiceHistory.setCadreNumber(UPDATED_CADRE_NUMBER);
        hrEmpServiceHistory.setGoDate(UPDATED_GO_DATE);
        hrEmpServiceHistory.setGoDoc(UPDATED_GO_DOC);
        hrEmpServiceHistory.setGoDocContentType(UPDATED_GO_DOC_CONTENT_TYPE);
        hrEmpServiceHistory.setGoDocName(UPDATED_GO_DOC_NAME);
        hrEmpServiceHistory.setLogId(UPDATED_LOG_ID);
        hrEmpServiceHistory.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpServiceHistory.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpServiceHistory.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpServiceHistory.setCreateBy(UPDATED_CREATE_BY);
        hrEmpServiceHistory.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpServiceHistory.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpServiceHistoryMockMvc.perform(put("/api/hrEmpServiceHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpServiceHistory)))
                .andExpect(status().isOk());

        // Validate the HrEmpServiceHistory in the database
        List<HrEmpServiceHistory> hrEmpServiceHistorys = hrEmpServiceHistoryRepository.findAll();
        assertThat(hrEmpServiceHistorys).hasSize(databaseSizeBeforeUpdate);
        HrEmpServiceHistory testHrEmpServiceHistory = hrEmpServiceHistorys.get(hrEmpServiceHistorys.size() - 1);
        assertThat(testHrEmpServiceHistory.getServiceDate()).isEqualTo(UPDATED_SERVICE_DATE);
        assertThat(testHrEmpServiceHistory.getGazettedDate()).isEqualTo(UPDATED_GAZETTED_DATE);
        assertThat(testHrEmpServiceHistory.getEncadrementDate()).isEqualTo(UPDATED_ENCADREMENT_DATE);
        assertThat(testHrEmpServiceHistory.getNationalSeniority()).isEqualTo(UPDATED_NATIONAL_SENIORITY);
        assertThat(testHrEmpServiceHistory.getCadreNumber()).isEqualTo(UPDATED_CADRE_NUMBER);
        assertThat(testHrEmpServiceHistory.getGoDate()).isEqualTo(UPDATED_GO_DATE);
        assertThat(testHrEmpServiceHistory.getGoDoc()).isEqualTo(UPDATED_GO_DOC);
        assertThat(testHrEmpServiceHistory.getGoDocContentType()).isEqualTo(UPDATED_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpServiceHistory.getGoDocName()).isEqualTo(UPDATED_GO_DOC_NAME);
        assertThat(testHrEmpServiceHistory.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpServiceHistory.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpServiceHistory.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpServiceHistory.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpServiceHistory.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpServiceHistory.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpServiceHistory.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpServiceHistory() throws Exception {
        // Initialize the database
        hrEmpServiceHistoryRepository.saveAndFlush(hrEmpServiceHistory);

		int databaseSizeBeforeDelete = hrEmpServiceHistoryRepository.findAll().size();

        // Get the hrEmpServiceHistory
        restHrEmpServiceHistoryMockMvc.perform(delete("/api/hrEmpServiceHistorys/{id}", hrEmpServiceHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpServiceHistory> hrEmpServiceHistorys = hrEmpServiceHistoryRepository.findAll();
        assertThat(hrEmpServiceHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
