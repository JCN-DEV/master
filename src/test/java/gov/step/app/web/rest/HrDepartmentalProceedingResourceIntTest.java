package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrDepartmentalProceeding;
import gov.step.app.repository.HrDepartmentalProceedingRepository;
import gov.step.app.repository.search.HrDepartmentalProceedingSearchRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrDepartmentalProceedingResource REST controller.
 *
 * @see HrDepartmentalProceedingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrDepartmentalProceedingResourceIntTest {

    private static final String DEFAULT_CRIME_NATURE = "AAAAA";
    private static final String UPDATED_CRIME_NATURE = "BBBBB";
    private static final String DEFAULT_NATURE = "AAAAA";
    private static final String UPDATED_NATURE = "BBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_FORM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FORM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_PERIOD = 1L;
    private static final Long UPDATED_PERIOD = 2L;
    private static final String DEFAULT_DUDAK_CASE_DETAIL = "AAAAA";
    private static final String UPDATED_DUDAK_CASE_DETAIL = "BBBBB";
    private static final String DEFAULT_DUDAK_PUNISHMENT = "AAAAA";
    private static final String UPDATED_DUDAK_PUNISHMENT = "BBBBB";

    private static final LocalDate DEFAULT_GO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_GO_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GO_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_GO_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GO_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_GO_DOC_NAME = "AAAAA";
    private static final String UPDATED_GO_DOC_NAME = "BBBBB";
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

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
    private HrDepartmentalProceedingRepository hrDepartmentalProceedingRepository;

    @Inject
    private HrDepartmentalProceedingSearchRepository hrDepartmentalProceedingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrDepartmentalProceedingMockMvc;

    private HrDepartmentalProceeding hrDepartmentalProceeding;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrDepartmentalProceedingResource hrDepartmentalProceedingResource = new HrDepartmentalProceedingResource();
        ReflectionTestUtils.setField(hrDepartmentalProceedingResource, "hrDepartmentalProceedingSearchRepository", hrDepartmentalProceedingSearchRepository);
        ReflectionTestUtils.setField(hrDepartmentalProceedingResource, "hrDepartmentalProceedingRepository", hrDepartmentalProceedingRepository);
        this.restHrDepartmentalProceedingMockMvc = MockMvcBuilders.standaloneSetup(hrDepartmentalProceedingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrDepartmentalProceeding = new HrDepartmentalProceeding();
        hrDepartmentalProceeding.setCrimeNature(DEFAULT_CRIME_NATURE);
        hrDepartmentalProceeding.setNature(DEFAULT_NATURE);
        hrDepartmentalProceeding.setAmount(DEFAULT_AMOUNT);
        hrDepartmentalProceeding.setFormDate(DEFAULT_FORM_DATE);
        hrDepartmentalProceeding.setToDate(DEFAULT_TO_DATE);
        hrDepartmentalProceeding.setPeriod(DEFAULT_PERIOD);
        hrDepartmentalProceeding.setDudakCaseDetail(DEFAULT_DUDAK_CASE_DETAIL);
        hrDepartmentalProceeding.setDudakPunishment(DEFAULT_DUDAK_PUNISHMENT);
        hrDepartmentalProceeding.setGoDate(DEFAULT_GO_DATE);
        hrDepartmentalProceeding.setGoDoc(DEFAULT_GO_DOC);
        hrDepartmentalProceeding.setGoDocContentType(DEFAULT_GO_DOC_CONTENT_TYPE);
        hrDepartmentalProceeding.setGoDocName(DEFAULT_GO_DOC_NAME);
        hrDepartmentalProceeding.setRemarks(DEFAULT_REMARKS);
        hrDepartmentalProceeding.setLogId(DEFAULT_LOG_ID);
        hrDepartmentalProceeding.setLogStatus(DEFAULT_LOG_STATUS);
        hrDepartmentalProceeding.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrDepartmentalProceeding.setCreateDate(DEFAULT_CREATE_DATE);
        hrDepartmentalProceeding.setCreateBy(DEFAULT_CREATE_BY);
        hrDepartmentalProceeding.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrDepartmentalProceeding.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrDepartmentalProceeding() throws Exception {
        int databaseSizeBeforeCreate = hrDepartmentalProceedingRepository.findAll().size();

        // Create the HrDepartmentalProceeding

        restHrDepartmentalProceedingMockMvc.perform(post("/api/hrDepartmentalProceedings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentalProceeding)))
                .andExpect(status().isCreated());

        // Validate the HrDepartmentalProceeding in the database
        List<HrDepartmentalProceeding> hrDepartmentalProceedings = hrDepartmentalProceedingRepository.findAll();
        assertThat(hrDepartmentalProceedings).hasSize(databaseSizeBeforeCreate + 1);
        HrDepartmentalProceeding testHrDepartmentalProceeding = hrDepartmentalProceedings.get(hrDepartmentalProceedings.size() - 1);
        assertThat(testHrDepartmentalProceeding.getCrimeNature()).isEqualTo(DEFAULT_CRIME_NATURE);
        assertThat(testHrDepartmentalProceeding.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testHrDepartmentalProceeding.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testHrDepartmentalProceeding.getFormDate()).isEqualTo(DEFAULT_FORM_DATE);
        assertThat(testHrDepartmentalProceeding.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testHrDepartmentalProceeding.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testHrDepartmentalProceeding.getDudakCaseDetail()).isEqualTo(DEFAULT_DUDAK_CASE_DETAIL);
        assertThat(testHrDepartmentalProceeding.getDudakPunishment()).isEqualTo(DEFAULT_DUDAK_PUNISHMENT);
        assertThat(testHrDepartmentalProceeding.getGoDate()).isEqualTo(DEFAULT_GO_DATE);
        assertThat(testHrDepartmentalProceeding.getGoDoc()).isEqualTo(DEFAULT_GO_DOC);
        assertThat(testHrDepartmentalProceeding.getGoDocContentType()).isEqualTo(DEFAULT_GO_DOC_CONTENT_TYPE);
        assertThat(testHrDepartmentalProceeding.getGoDocName()).isEqualTo(DEFAULT_GO_DOC_NAME);
        assertThat(testHrDepartmentalProceeding.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testHrDepartmentalProceeding.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrDepartmentalProceeding.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrDepartmentalProceeding.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrDepartmentalProceeding.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrDepartmentalProceeding.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrDepartmentalProceeding.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrDepartmentalProceeding.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkCrimeNatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDepartmentalProceedingRepository.findAll().size();
        // set the field null
        hrDepartmentalProceeding.setCrimeNature(null);

        // Create the HrDepartmentalProceeding, which fails.

        restHrDepartmentalProceedingMockMvc.perform(post("/api/hrDepartmentalProceedings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentalProceeding)))
                .andExpect(status().isBadRequest());

        List<HrDepartmentalProceeding> hrDepartmentalProceedings = hrDepartmentalProceedingRepository.findAll();
        assertThat(hrDepartmentalProceedings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDepartmentalProceedingRepository.findAll().size();
        // set the field null
        hrDepartmentalProceeding.setAmount(null);

        // Create the HrDepartmentalProceeding, which fails.

        restHrDepartmentalProceedingMockMvc.perform(post("/api/hrDepartmentalProceedings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentalProceeding)))
                .andExpect(status().isBadRequest());

        List<HrDepartmentalProceeding> hrDepartmentalProceedings = hrDepartmentalProceedingRepository.findAll();
        assertThat(hrDepartmentalProceedings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDepartmentalProceedingRepository.findAll().size();
        // set the field null
        hrDepartmentalProceeding.setFormDate(null);

        // Create the HrDepartmentalProceeding, which fails.

        restHrDepartmentalProceedingMockMvc.perform(post("/api/hrDepartmentalProceedings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentalProceeding)))
                .andExpect(status().isBadRequest());

        List<HrDepartmentalProceeding> hrDepartmentalProceedings = hrDepartmentalProceedingRepository.findAll();
        assertThat(hrDepartmentalProceedings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDepartmentalProceedingRepository.findAll().size();
        // set the field null
        hrDepartmentalProceeding.setActiveStatus(null);

        // Create the HrDepartmentalProceeding, which fails.

        restHrDepartmentalProceedingMockMvc.perform(post("/api/hrDepartmentalProceedings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentalProceeding)))
                .andExpect(status().isBadRequest());

        List<HrDepartmentalProceeding> hrDepartmentalProceedings = hrDepartmentalProceedingRepository.findAll();
        assertThat(hrDepartmentalProceedings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrDepartmentalProceedings() throws Exception {
        // Initialize the database
        hrDepartmentalProceedingRepository.saveAndFlush(hrDepartmentalProceeding);

        // Get all the hrDepartmentalProceedings
        restHrDepartmentalProceedingMockMvc.perform(get("/api/hrDepartmentalProceedings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrDepartmentalProceeding.getId().intValue())))
                .andExpect(jsonPath("$.[*].crimeNature").value(hasItem(DEFAULT_CRIME_NATURE.toString())))
                .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].formDate").value(hasItem(DEFAULT_FORM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.intValue())))
                .andExpect(jsonPath("$.[*].dudakCaseDetail").value(hasItem(DEFAULT_DUDAK_CASE_DETAIL.toString())))
                .andExpect(jsonPath("$.[*].dudakPunishment").value(hasItem(DEFAULT_DUDAK_PUNISHMENT.toString())))
                .andExpect(jsonPath("$.[*].goDate").value(hasItem(DEFAULT_GO_DATE.toString())))
                .andExpect(jsonPath("$.[*].goDocContentType").value(hasItem(DEFAULT_GO_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].goDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_GO_DOC))))
                .andExpect(jsonPath("$.[*].goDocName").value(hasItem(DEFAULT_GO_DOC_NAME.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
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
    public void getHrDepartmentalProceeding() throws Exception {
        // Initialize the database
        hrDepartmentalProceedingRepository.saveAndFlush(hrDepartmentalProceeding);

        // Get the hrDepartmentalProceeding
        restHrDepartmentalProceedingMockMvc.perform(get("/api/hrDepartmentalProceedings/{id}", hrDepartmentalProceeding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrDepartmentalProceeding.getId().intValue()))
            .andExpect(jsonPath("$.crimeNature").value(DEFAULT_CRIME_NATURE.toString()))
            .andExpect(jsonPath("$.nature").value(DEFAULT_NATURE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.formDate").value(DEFAULT_FORM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD.intValue()))
            .andExpect(jsonPath("$.dudakCaseDetail").value(DEFAULT_DUDAK_CASE_DETAIL.toString()))
            .andExpect(jsonPath("$.dudakPunishment").value(DEFAULT_DUDAK_PUNISHMENT.toString()))
            .andExpect(jsonPath("$.goDate").value(DEFAULT_GO_DATE.toString()))
            .andExpect(jsonPath("$.goDocContentType").value(DEFAULT_GO_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.goDoc").value(Base64Utils.encodeToString(DEFAULT_GO_DOC)))
            .andExpect(jsonPath("$.goDocName").value(DEFAULT_GO_DOC_NAME.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
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
    public void getNonExistingHrDepartmentalProceeding() throws Exception {
        // Get the hrDepartmentalProceeding
        restHrDepartmentalProceedingMockMvc.perform(get("/api/hrDepartmentalProceedings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrDepartmentalProceeding() throws Exception {
        // Initialize the database
        hrDepartmentalProceedingRepository.saveAndFlush(hrDepartmentalProceeding);

		int databaseSizeBeforeUpdate = hrDepartmentalProceedingRepository.findAll().size();

        // Update the hrDepartmentalProceeding
        hrDepartmentalProceeding.setCrimeNature(UPDATED_CRIME_NATURE);
        hrDepartmentalProceeding.setNature(UPDATED_NATURE);
        hrDepartmentalProceeding.setAmount(UPDATED_AMOUNT);
        hrDepartmentalProceeding.setFormDate(UPDATED_FORM_DATE);
        hrDepartmentalProceeding.setToDate(UPDATED_TO_DATE);
        hrDepartmentalProceeding.setPeriod(UPDATED_PERIOD);
        hrDepartmentalProceeding.setDudakCaseDetail(UPDATED_DUDAK_CASE_DETAIL);
        hrDepartmentalProceeding.setDudakPunishment(UPDATED_DUDAK_PUNISHMENT);
        hrDepartmentalProceeding.setGoDate(UPDATED_GO_DATE);
        hrDepartmentalProceeding.setGoDoc(UPDATED_GO_DOC);
        hrDepartmentalProceeding.setGoDocContentType(UPDATED_GO_DOC_CONTENT_TYPE);
        hrDepartmentalProceeding.setGoDocName(UPDATED_GO_DOC_NAME);
        hrDepartmentalProceeding.setRemarks(UPDATED_REMARKS);
        hrDepartmentalProceeding.setLogId(UPDATED_LOG_ID);
        hrDepartmentalProceeding.setLogStatus(UPDATED_LOG_STATUS);
        hrDepartmentalProceeding.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrDepartmentalProceeding.setCreateDate(UPDATED_CREATE_DATE);
        hrDepartmentalProceeding.setCreateBy(UPDATED_CREATE_BY);
        hrDepartmentalProceeding.setUpdateDate(UPDATED_UPDATE_DATE);
        hrDepartmentalProceeding.setUpdateBy(UPDATED_UPDATE_BY);

        restHrDepartmentalProceedingMockMvc.perform(put("/api/hrDepartmentalProceedings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentalProceeding)))
                .andExpect(status().isOk());

        // Validate the HrDepartmentalProceeding in the database
        List<HrDepartmentalProceeding> hrDepartmentalProceedings = hrDepartmentalProceedingRepository.findAll();
        assertThat(hrDepartmentalProceedings).hasSize(databaseSizeBeforeUpdate);
        HrDepartmentalProceeding testHrDepartmentalProceeding = hrDepartmentalProceedings.get(hrDepartmentalProceedings.size() - 1);
        assertThat(testHrDepartmentalProceeding.getCrimeNature()).isEqualTo(UPDATED_CRIME_NATURE);
        assertThat(testHrDepartmentalProceeding.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testHrDepartmentalProceeding.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testHrDepartmentalProceeding.getFormDate()).isEqualTo(UPDATED_FORM_DATE);
        assertThat(testHrDepartmentalProceeding.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testHrDepartmentalProceeding.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testHrDepartmentalProceeding.getDudakCaseDetail()).isEqualTo(UPDATED_DUDAK_CASE_DETAIL);
        assertThat(testHrDepartmentalProceeding.getDudakPunishment()).isEqualTo(UPDATED_DUDAK_PUNISHMENT);
        assertThat(testHrDepartmentalProceeding.getGoDate()).isEqualTo(UPDATED_GO_DATE);
        assertThat(testHrDepartmentalProceeding.getGoDoc()).isEqualTo(UPDATED_GO_DOC);
        assertThat(testHrDepartmentalProceeding.getGoDocContentType()).isEqualTo(UPDATED_GO_DOC_CONTENT_TYPE);
        assertThat(testHrDepartmentalProceeding.getGoDocName()).isEqualTo(UPDATED_GO_DOC_NAME);
        assertThat(testHrDepartmentalProceeding.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testHrDepartmentalProceeding.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrDepartmentalProceeding.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrDepartmentalProceeding.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrDepartmentalProceeding.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrDepartmentalProceeding.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrDepartmentalProceeding.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrDepartmentalProceeding.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrDepartmentalProceeding() throws Exception {
        // Initialize the database
        hrDepartmentalProceedingRepository.saveAndFlush(hrDepartmentalProceeding);

		int databaseSizeBeforeDelete = hrDepartmentalProceedingRepository.findAll().size();

        // Get the hrDepartmentalProceeding
        restHrDepartmentalProceedingMockMvc.perform(delete("/api/hrDepartmentalProceedings/{id}", hrDepartmentalProceeding.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrDepartmentalProceeding> hrDepartmentalProceedings = hrDepartmentalProceedingRepository.findAll();
        assertThat(hrDepartmentalProceedings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
