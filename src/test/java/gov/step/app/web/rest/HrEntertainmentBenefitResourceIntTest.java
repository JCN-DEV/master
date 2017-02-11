package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEntertainmentBenefit;
import gov.step.app.repository.HrEntertainmentBenefitRepository;
import gov.step.app.repository.search.HrEntertainmentBenefitSearchRepository;
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
 * Test class for the HrEntertainmentBenefitResource REST controller.
 *
 * @see HrEntertainmentBenefitResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEntertainmentBenefitResourceIntTest {


    private static final LocalDate DEFAULT_ELIGIBILITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ELIGIBILITY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_TOTAL_DAYS = 1;
    private static final Integer UPDATED_TOTAL_DAYS = 2;
    private static final String DEFAULT_NOT_TAKEN_REASON = "AAAAA";
    private static final String UPDATED_NOT_TAKEN_REASON = "BBBBB";

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
    private HrEntertainmentBenefitRepository hrEntertainmentBenefitRepository;

    @Inject
    private HrEntertainmentBenefitSearchRepository hrEntertainmentBenefitSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEntertainmentBenefitMockMvc;

    private HrEntertainmentBenefit hrEntertainmentBenefit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEntertainmentBenefitResource hrEntertainmentBenefitResource = new HrEntertainmentBenefitResource();
        ReflectionTestUtils.setField(hrEntertainmentBenefitResource, "hrEntertainmentBenefitSearchRepository", hrEntertainmentBenefitSearchRepository);
        ReflectionTestUtils.setField(hrEntertainmentBenefitResource, "hrEntertainmentBenefitRepository", hrEntertainmentBenefitRepository);
        this.restHrEntertainmentBenefitMockMvc = MockMvcBuilders.standaloneSetup(hrEntertainmentBenefitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEntertainmentBenefit = new HrEntertainmentBenefit();
        hrEntertainmentBenefit.setEligibilityDate(DEFAULT_ELIGIBILITY_DATE);
        hrEntertainmentBenefit.setAmount(DEFAULT_AMOUNT);
        hrEntertainmentBenefit.setTotalDays(DEFAULT_TOTAL_DAYS);
        hrEntertainmentBenefit.setNotTakenReason(DEFAULT_NOT_TAKEN_REASON);
        hrEntertainmentBenefit.setLogId(DEFAULT_LOG_ID);
        hrEntertainmentBenefit.setLogStatus(DEFAULT_LOG_STATUS);
        hrEntertainmentBenefit.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEntertainmentBenefit.setCreateDate(DEFAULT_CREATE_DATE);
        hrEntertainmentBenefit.setCreateBy(DEFAULT_CREATE_BY);
        hrEntertainmentBenefit.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEntertainmentBenefit.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEntertainmentBenefit() throws Exception {
        int databaseSizeBeforeCreate = hrEntertainmentBenefitRepository.findAll().size();

        // Create the HrEntertainmentBenefit

        restHrEntertainmentBenefitMockMvc.perform(post("/api/hrEntertainmentBenefits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEntertainmentBenefit)))
                .andExpect(status().isCreated());

        // Validate the HrEntertainmentBenefit in the database
        List<HrEntertainmentBenefit> hrEntertainmentBenefits = hrEntertainmentBenefitRepository.findAll();
        assertThat(hrEntertainmentBenefits).hasSize(databaseSizeBeforeCreate + 1);
        HrEntertainmentBenefit testHrEntertainmentBenefit = hrEntertainmentBenefits.get(hrEntertainmentBenefits.size() - 1);
        assertThat(testHrEntertainmentBenefit.getEligibilityDate()).isEqualTo(DEFAULT_ELIGIBILITY_DATE);
        assertThat(testHrEntertainmentBenefit.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testHrEntertainmentBenefit.getTotalDays()).isEqualTo(DEFAULT_TOTAL_DAYS);
        assertThat(testHrEntertainmentBenefit.getNotTakenReason()).isEqualTo(DEFAULT_NOT_TAKEN_REASON);
        assertThat(testHrEntertainmentBenefit.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEntertainmentBenefit.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEntertainmentBenefit.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEntertainmentBenefit.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEntertainmentBenefit.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEntertainmentBenefit.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEntertainmentBenefit.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkEligibilityDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEntertainmentBenefitRepository.findAll().size();
        // set the field null
        hrEntertainmentBenefit.setEligibilityDate(null);

        // Create the HrEntertainmentBenefit, which fails.

        restHrEntertainmentBenefitMockMvc.perform(post("/api/hrEntertainmentBenefits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEntertainmentBenefit)))
                .andExpect(status().isBadRequest());

        List<HrEntertainmentBenefit> hrEntertainmentBenefits = hrEntertainmentBenefitRepository.findAll();
        assertThat(hrEntertainmentBenefits).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEntertainmentBenefitRepository.findAll().size();
        // set the field null
        hrEntertainmentBenefit.setAmount(null);

        // Create the HrEntertainmentBenefit, which fails.

        restHrEntertainmentBenefitMockMvc.perform(post("/api/hrEntertainmentBenefits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEntertainmentBenefit)))
                .andExpect(status().isBadRequest());

        List<HrEntertainmentBenefit> hrEntertainmentBenefits = hrEntertainmentBenefitRepository.findAll();
        assertThat(hrEntertainmentBenefits).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEntertainmentBenefitRepository.findAll().size();
        // set the field null
        hrEntertainmentBenefit.setTotalDays(null);

        // Create the HrEntertainmentBenefit, which fails.

        restHrEntertainmentBenefitMockMvc.perform(post("/api/hrEntertainmentBenefits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEntertainmentBenefit)))
                .andExpect(status().isBadRequest());

        List<HrEntertainmentBenefit> hrEntertainmentBenefits = hrEntertainmentBenefitRepository.findAll();
        assertThat(hrEntertainmentBenefits).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEntertainmentBenefitRepository.findAll().size();
        // set the field null
        hrEntertainmentBenefit.setActiveStatus(null);

        // Create the HrEntertainmentBenefit, which fails.

        restHrEntertainmentBenefitMockMvc.perform(post("/api/hrEntertainmentBenefits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEntertainmentBenefit)))
                .andExpect(status().isBadRequest());

        List<HrEntertainmentBenefit> hrEntertainmentBenefits = hrEntertainmentBenefitRepository.findAll();
        assertThat(hrEntertainmentBenefits).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEntertainmentBenefits() throws Exception {
        // Initialize the database
        hrEntertainmentBenefitRepository.saveAndFlush(hrEntertainmentBenefit);

        // Get all the hrEntertainmentBenefits
        restHrEntertainmentBenefitMockMvc.perform(get("/api/hrEntertainmentBenefits?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEntertainmentBenefit.getId().intValue())))
                .andExpect(jsonPath("$.[*].eligibilityDate").value(hasItem(DEFAULT_ELIGIBILITY_DATE.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS)))
                .andExpect(jsonPath("$.[*].notTakenReason").value(hasItem(DEFAULT_NOT_TAKEN_REASON.toString())))
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
    public void getHrEntertainmentBenefit() throws Exception {
        // Initialize the database
        hrEntertainmentBenefitRepository.saveAndFlush(hrEntertainmentBenefit);

        // Get the hrEntertainmentBenefit
        restHrEntertainmentBenefitMockMvc.perform(get("/api/hrEntertainmentBenefits/{id}", hrEntertainmentBenefit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEntertainmentBenefit.getId().intValue()))
            .andExpect(jsonPath("$.eligibilityDate").value(DEFAULT_ELIGIBILITY_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.totalDays").value(DEFAULT_TOTAL_DAYS))
            .andExpect(jsonPath("$.notTakenReason").value(DEFAULT_NOT_TAKEN_REASON.toString()))
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
    public void getNonExistingHrEntertainmentBenefit() throws Exception {
        // Get the hrEntertainmentBenefit
        restHrEntertainmentBenefitMockMvc.perform(get("/api/hrEntertainmentBenefits/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEntertainmentBenefit() throws Exception {
        // Initialize the database
        hrEntertainmentBenefitRepository.saveAndFlush(hrEntertainmentBenefit);

		int databaseSizeBeforeUpdate = hrEntertainmentBenefitRepository.findAll().size();

        // Update the hrEntertainmentBenefit
        hrEntertainmentBenefit.setEligibilityDate(UPDATED_ELIGIBILITY_DATE);
        hrEntertainmentBenefit.setAmount(UPDATED_AMOUNT);
        hrEntertainmentBenefit.setTotalDays(UPDATED_TOTAL_DAYS);
        hrEntertainmentBenefit.setNotTakenReason(UPDATED_NOT_TAKEN_REASON);
        hrEntertainmentBenefit.setLogId(UPDATED_LOG_ID);
        hrEntertainmentBenefit.setLogStatus(UPDATED_LOG_STATUS);
        hrEntertainmentBenefit.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEntertainmentBenefit.setCreateDate(UPDATED_CREATE_DATE);
        hrEntertainmentBenefit.setCreateBy(UPDATED_CREATE_BY);
        hrEntertainmentBenefit.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEntertainmentBenefit.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEntertainmentBenefitMockMvc.perform(put("/api/hrEntertainmentBenefits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEntertainmentBenefit)))
                .andExpect(status().isOk());

        // Validate the HrEntertainmentBenefit in the database
        List<HrEntertainmentBenefit> hrEntertainmentBenefits = hrEntertainmentBenefitRepository.findAll();
        assertThat(hrEntertainmentBenefits).hasSize(databaseSizeBeforeUpdate);
        HrEntertainmentBenefit testHrEntertainmentBenefit = hrEntertainmentBenefits.get(hrEntertainmentBenefits.size() - 1);
        assertThat(testHrEntertainmentBenefit.getEligibilityDate()).isEqualTo(UPDATED_ELIGIBILITY_DATE);
        assertThat(testHrEntertainmentBenefit.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testHrEntertainmentBenefit.getTotalDays()).isEqualTo(UPDATED_TOTAL_DAYS);
        assertThat(testHrEntertainmentBenefit.getNotTakenReason()).isEqualTo(UPDATED_NOT_TAKEN_REASON);
        assertThat(testHrEntertainmentBenefit.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEntertainmentBenefit.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEntertainmentBenefit.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEntertainmentBenefit.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEntertainmentBenefit.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEntertainmentBenefit.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEntertainmentBenefit.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEntertainmentBenefit() throws Exception {
        // Initialize the database
        hrEntertainmentBenefitRepository.saveAndFlush(hrEntertainmentBenefit);

		int databaseSizeBeforeDelete = hrEntertainmentBenefitRepository.findAll().size();

        // Get the hrEntertainmentBenefit
        restHrEntertainmentBenefitMockMvc.perform(delete("/api/hrEntertainmentBenefits/{id}", hrEntertainmentBenefit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEntertainmentBenefit> hrEntertainmentBenefits = hrEntertainmentBenefitRepository.findAll();
        assertThat(hrEntertainmentBenefits).hasSize(databaseSizeBeforeDelete - 1);
    }
}
