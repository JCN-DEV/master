package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsElpc;
import gov.step.app.repository.PgmsElpcRepository;
import gov.step.app.repository.search.PgmsElpcSearchRepository;

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
 * Test class for the PgmsElpcResource REST controller.
 *
 * @see PgmsElpcResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsElpcResourceTest {

    private static final String DEFAULT_EMP_CODE = "AAAAA";
    private static final String UPDATED_EMP_CODE = "BBBBB";
    private static final String DEFAULT_INST_CODE = "AAAAA";
    private static final String UPDATED_INST_CODE = "BBBBB";
    private static final String DEFAULT_EMP_NAME = "AAAAA";
    private static final String UPDATED_EMP_NAME = "BBBBB";
    private static final String DEFAULT_INST_NAME = "AAAAA";
    private static final String UPDATED_INST_NAME = "BBBBB";

    private static final Long DEFAULT_DESIG_ID = 1L;
    private static final Long UPDATED_DESIG_ID = 2L;
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_JOIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BEGIN_DATE_OF_RETIREMNT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGIN_DATE_OF_RETIREMNT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RETIREMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETIREMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_LAST_RCV_PAYSCALE = 1L;
    private static final Long UPDATED_LAST_RCV_PAYSCALE = 2L;

    private static final Long DEFAULT_INCRS_DT_OF_YRLY_PAYMENT = 1L;
    private static final Long UPDATED_INCRS_DT_OF_YRLY_PAYMENT = 2L;

    private static final Long DEFAULT_GAINING_LEAVE = 1L;
    private static final Long UPDATED_GAINING_LEAVE = 2L;
    private static final String DEFAULT_LEAVE_TYPE = "AAAAA";
    private static final String UPDATED_LEAVE_TYPE = "BBBBB";

    private static final Long DEFAULT_LEAVE_TOTAL = 1L;
    private static final Long UPDATED_LEAVE_TOTAL = 2L;

    private static final Long DEFAULT_MON_PAY_DESC_VSOURCE = 1L;
    private static final Long UPDATED_MON_PAY_DESC_VSOURCE = 2L;

    private static final Long DEFAULT_MAIN_PAYMENT = 1L;
    private static final Long UPDATED_MAIN_PAYMENT = 2L;

    private static final Long DEFAULT_INCR_MON_RATE_LEAVING = 1L;
    private static final Long UPDATED_INCR_MON_RATE_LEAVING = 2L;

    private static final Long DEFAULT_SPECIAL_PAYMENT = 1L;
    private static final Long UPDATED_SPECIAL_PAYMENT = 2L;

    private static final Long DEFAULT_SPECIAL_ALLOWANCE = 1L;
    private static final Long UPDATED_SPECIAL_ALLOWANCE = 2L;

    private static final Long DEFAULT_HOUSERENT_AL = 1L;
    private static final Long UPDATED_HOUSERENT_AL = 2L;

    private static final Long DEFAULT_TREATMENT_AL = 1L;
    private static final Long UPDATED_TREATMENT_AL = 2L;

    private static final Long DEFAULT_DEARNESS_AL = 1L;
    private static final Long UPDATED_DEARNESS_AL = 2L;

    private static final Long DEFAULT_TRAVELLING_AL = 1L;
    private static final Long UPDATED_TRAVELLING_AL = 2L;

    private static final Long DEFAULT_LAUNDRY_AL = 1L;
    private static final Long UPDATED_LAUNDRY_AL = 2L;

    private static final Long DEFAULT_PERSONAL_AL = 1L;
    private static final Long UPDATED_PERSONAL_AL = 2L;

    private static final Long DEFAULT_TECHNICAL_AL = 1L;
    private static final Long UPDATED_TECHNICAL_AL = 2L;

    private static final Long DEFAULT_HOSPITALITY_AL = 1L;
    private static final Long UPDATED_HOSPITALITY_AL = 2L;

    private static final Long DEFAULT_TIFFIN_AL = 1L;
    private static final Long UPDATED_TIFFIN_AL = 2L;

    private static final Long DEFAULT_ADV_OF_MAKING_HOUSE = 1L;
    private static final Long UPDATED_ADV_OF_MAKING_HOUSE = 2L;

    private static final Long DEFAULT_VECHILE_STATUS = 1L;
    private static final Long UPDATED_VECHILE_STATUS = 2L;

    private static final Long DEFAULT_ADV_TRAV_AL = 1L;
    private static final Long UPDATED_ADV_TRAV_AL = 2L;

    private static final Long DEFAULT_ADV_SALARY = 1L;
    private static final Long UPDATED_ADV_SALARY = 2L;

    private static final Long DEFAULT_HOUSE_RENT = 1L;
    private static final Long UPDATED_HOUSE_RENT = 2L;

    private static final Long DEFAULT_CAR_RENT = 1L;
    private static final Long UPDATED_CAR_RENT = 2L;

    private static final Long DEFAULT_GAS_BILL = 1L;
    private static final Long UPDATED_GAS_BILL = 2L;
    private static final String DEFAULT_SANTRY_WATER_TAX = "AAAAA";
    private static final String UPDATED_SANTRY_WATER_TAX = "BBBBB";
    private static final String DEFAULT_BANK_ACC = "AAAAA";
    private static final String UPDATED_BANK_ACC = "BBBBB";
    private static final String DEFAULT_ACC_BOOK_NO = "AAAAA";
    private static final String UPDATED_ACC_BOOK_NO = "BBBBB";
    private static final String DEFAULT_BOOK_PAGE_NO = "AAAAA";
    private static final String UPDATED_BOOK_PAGE_NO = "BBBBB";
    private static final String DEFAULT_BANK_INTEREST = "AAAAA";
    private static final String UPDATED_BANK_INTEREST = "BBBBB";
    private static final String DEFAULT_MONLY_DEP_RATE_FR_SALARY = "AAAAA";
    private static final String UPDATED_MONLY_DEP_RATE_FR_SALARY = "BBBBB";
    private static final String DEFAULT_EXPECTED_DEPOSITION = "AAAAA";
    private static final String UPDATED_EXPECTED_DEPOSITION = "BBBBB";

    private static final LocalDate DEFAULT_ACC_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACC_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_APP_NO = "AAAAA";
    private static final String UPDATED_APP_NO = "BBBBB";

    private static final LocalDate DEFAULT_APP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APP_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_APP_TYPE = "AAAAA";
    private static final String UPDATED_APP_TYPE = "BBBBB";
    private static final String DEFAULT_APP_COMMENTS = "AAAAA";
    private static final String UPDATED_APP_COMMENTS = "BBBBB";

    private static final String DEFAULT_APRV_STATUS = "AAAAA";
    private static final String UPDATED_APRV_STATUS = "BBBBB";

    private static final LocalDate DEFAULT_APRV_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APRV_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_APRV_BY = 1L;
    private static final Long UPDATED_APRV_BY = 2L;

    private static final Long DEFAULT_NOTIFICATION_STATUS = 1L;
    private static final Long UPDATED_NOTIFICATION_STATUS = 2L;

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PgmsElpcRepository pgmsElpcRepository;

    @Inject
    private PgmsElpcSearchRepository pgmsElpcSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsElpcMockMvc;

    private PgmsElpc pgmsElpc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsElpcResource pgmsElpcResource = new PgmsElpcResource();
        ReflectionTestUtils.setField(pgmsElpcResource, "pgmsElpcRepository", pgmsElpcRepository);
        ReflectionTestUtils.setField(pgmsElpcResource, "pgmsElpcSearchRepository", pgmsElpcSearchRepository);
        this.restPgmsElpcMockMvc = MockMvcBuilders.standaloneSetup(pgmsElpcResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsElpc = new PgmsElpc();
        pgmsElpc.setEmpCode(DEFAULT_EMP_CODE);
        pgmsElpc.setInstCode(DEFAULT_INST_CODE);
        pgmsElpc.setEmpName(DEFAULT_EMP_NAME);
        pgmsElpc.setInstName(DEFAULT_INST_NAME);
        pgmsElpc.setDesigId(DEFAULT_DESIG_ID);
        pgmsElpc.setDesignation(DEFAULT_DESIGNATION);
        pgmsElpc.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
        pgmsElpc.setJoinDate(DEFAULT_JOIN_DATE);
        pgmsElpc.setBeginDateOfRetiremnt(DEFAULT_BEGIN_DATE_OF_RETIREMNT);
        pgmsElpc.setRetirementDate(DEFAULT_RETIREMENT_DATE);
        pgmsElpc.setLastRcvPayscale(DEFAULT_LAST_RCV_PAYSCALE);
        pgmsElpc.setIncrsDtOfYrlyPayment(DEFAULT_INCRS_DT_OF_YRLY_PAYMENT);
        pgmsElpc.setGainingLeave(DEFAULT_GAINING_LEAVE);
        pgmsElpc.setLeaveType(DEFAULT_LEAVE_TYPE);
        pgmsElpc.setLeaveTotal(DEFAULT_LEAVE_TOTAL);
//        pgmsElpc.setMonPayDescVsource(DEFAULT_MON_PAY_DESC_VSOURCE);
        pgmsElpc.setMainPayment(DEFAULT_MAIN_PAYMENT);
        pgmsElpc.setIncrMonRateLeaving(DEFAULT_INCR_MON_RATE_LEAVING);
        pgmsElpc.setSpecialPayment(DEFAULT_SPECIAL_PAYMENT);
        pgmsElpc.setSpecialAllowance(DEFAULT_SPECIAL_ALLOWANCE);
        pgmsElpc.setHouserentAl(DEFAULT_HOUSERENT_AL);
        pgmsElpc.setTreatmentAl(DEFAULT_TREATMENT_AL);
        pgmsElpc.setDearnessAl(DEFAULT_DEARNESS_AL);
        pgmsElpc.setTravellingAl(DEFAULT_TRAVELLING_AL);
        pgmsElpc.setLaundryAl(DEFAULT_LAUNDRY_AL);
        pgmsElpc.setPersonalAl(DEFAULT_PERSONAL_AL);
        pgmsElpc.setTechnicalAl(DEFAULT_TECHNICAL_AL);
        pgmsElpc.setHospitalityAl(DEFAULT_HOSPITALITY_AL);
        pgmsElpc.setTiffinAl(DEFAULT_TIFFIN_AL);
        pgmsElpc.setAdvOfMakingHouse(DEFAULT_ADV_OF_MAKING_HOUSE);
        pgmsElpc.setVechileStatus(DEFAULT_VECHILE_STATUS);
        pgmsElpc.setAdvTravAl(DEFAULT_ADV_TRAV_AL);
        pgmsElpc.setAdvSalary(DEFAULT_ADV_SALARY);
        pgmsElpc.setHouseRent(DEFAULT_HOUSE_RENT);
        pgmsElpc.setCarRent(DEFAULT_CAR_RENT);
        pgmsElpc.setGasBill(DEFAULT_GAS_BILL);
        pgmsElpc.setSantryWaterTax(DEFAULT_SANTRY_WATER_TAX);
        pgmsElpc.setBankAcc(DEFAULT_BANK_ACC);
        pgmsElpc.setAccBookNo(DEFAULT_ACC_BOOK_NO);
        pgmsElpc.setBookPageNo(DEFAULT_BOOK_PAGE_NO);
        pgmsElpc.setBankInterest(DEFAULT_BANK_INTEREST);
        pgmsElpc.setMonlyDepRateFrSalary(DEFAULT_MONLY_DEP_RATE_FR_SALARY);
        pgmsElpc.setExpectedDeposition(DEFAULT_EXPECTED_DEPOSITION);
        pgmsElpc.setAccDate(DEFAULT_ACC_DATE);
        pgmsElpc.setAppNo(DEFAULT_APP_NO);
        pgmsElpc.setAppDate(DEFAULT_APP_DATE);
        pgmsElpc.setAppType(DEFAULT_APP_TYPE);
        pgmsElpc.setAppComments(DEFAULT_APP_COMMENTS);
        pgmsElpc.setAprvStatus(DEFAULT_APRV_STATUS);
        pgmsElpc.setAprvDate(DEFAULT_APRV_DATE);
        pgmsElpc.setAprvBy(DEFAULT_APRV_BY);
        pgmsElpc.setNotificationStatus(DEFAULT_NOTIFICATION_STATUS);
        pgmsElpc.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pgmsElpc.setCreateDate(DEFAULT_CREATE_DATE);
        pgmsElpc.setCreateBy(DEFAULT_CREATE_BY);
        pgmsElpc.setUpdateBy(DEFAULT_UPDATE_BY);
        pgmsElpc.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createPgmsElpc() throws Exception {
        int databaseSizeBeforeCreate = pgmsElpcRepository.findAll().size();

        // Create the PgmsElpc

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isCreated());

        // Validate the PgmsElpc in the database
        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeCreate + 1);
        PgmsElpc testPgmsElpc = pgmsElpcs.get(pgmsElpcs.size() - 1);
        assertThat(testPgmsElpc.getEmpCode()).isEqualTo(DEFAULT_EMP_CODE);
        assertThat(testPgmsElpc.getInstCode()).isEqualTo(DEFAULT_INST_CODE);
        assertThat(testPgmsElpc.getEmpName()).isEqualTo(DEFAULT_EMP_NAME);
        assertThat(testPgmsElpc.getInstName()).isEqualTo(DEFAULT_INST_NAME);
        assertThat(testPgmsElpc.getDesigId()).isEqualTo(DEFAULT_DESIG_ID);
        assertThat(testPgmsElpc.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testPgmsElpc.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPgmsElpc.getJoinDate()).isEqualTo(DEFAULT_JOIN_DATE);
        assertThat(testPgmsElpc.getBeginDateOfRetiremnt()).isEqualTo(DEFAULT_BEGIN_DATE_OF_RETIREMNT);
        assertThat(testPgmsElpc.getRetirementDate()).isEqualTo(DEFAULT_RETIREMENT_DATE);
        assertThat(testPgmsElpc.getLastRcvPayscale()).isEqualTo(DEFAULT_LAST_RCV_PAYSCALE);
        assertThat(testPgmsElpc.getIncrsDtOfYrlyPayment()).isEqualTo(DEFAULT_INCRS_DT_OF_YRLY_PAYMENT);
        assertThat(testPgmsElpc.getGainingLeave()).isEqualTo(DEFAULT_GAINING_LEAVE);
        assertThat(testPgmsElpc.getLeaveType()).isEqualTo(DEFAULT_LEAVE_TYPE);
        assertThat(testPgmsElpc.getLeaveTotal()).isEqualTo(DEFAULT_LEAVE_TOTAL);
//        assertThat(testPgmsElpc.getMonPayDescVsource()).isEqualTo(DEFAULT_MON_PAY_DESC_VSOURCE);
        assertThat(testPgmsElpc.getMainPayment()).isEqualTo(DEFAULT_MAIN_PAYMENT);
        assertThat(testPgmsElpc.getIncrMonRateLeaving()).isEqualTo(DEFAULT_INCR_MON_RATE_LEAVING);
        assertThat(testPgmsElpc.getSpecialPayment()).isEqualTo(DEFAULT_SPECIAL_PAYMENT);
        assertThat(testPgmsElpc.getSpecialAllowance()).isEqualTo(DEFAULT_SPECIAL_ALLOWANCE);
        assertThat(testPgmsElpc.getHouserentAl()).isEqualTo(DEFAULT_HOUSERENT_AL);
        assertThat(testPgmsElpc.getTreatmentAl()).isEqualTo(DEFAULT_TREATMENT_AL);
        assertThat(testPgmsElpc.getDearnessAl()).isEqualTo(DEFAULT_DEARNESS_AL);
        assertThat(testPgmsElpc.getTravellingAl()).isEqualTo(DEFAULT_TRAVELLING_AL);
        assertThat(testPgmsElpc.getLaundryAl()).isEqualTo(DEFAULT_LAUNDRY_AL);
        assertThat(testPgmsElpc.getPersonalAl()).isEqualTo(DEFAULT_PERSONAL_AL);
        assertThat(testPgmsElpc.getTechnicalAl()).isEqualTo(DEFAULT_TECHNICAL_AL);
        assertThat(testPgmsElpc.getHospitalityAl()).isEqualTo(DEFAULT_HOSPITALITY_AL);
        assertThat(testPgmsElpc.getTiffinAl()).isEqualTo(DEFAULT_TIFFIN_AL);
        assertThat(testPgmsElpc.getAdvOfMakingHouse()).isEqualTo(DEFAULT_ADV_OF_MAKING_HOUSE);
        assertThat(testPgmsElpc.getVechileStatus()).isEqualTo(DEFAULT_VECHILE_STATUS);
        assertThat(testPgmsElpc.getAdvTravAl()).isEqualTo(DEFAULT_ADV_TRAV_AL);
        assertThat(testPgmsElpc.getAdvSalary()).isEqualTo(DEFAULT_ADV_SALARY);
        assertThat(testPgmsElpc.getHouseRent()).isEqualTo(DEFAULT_HOUSE_RENT);
        assertThat(testPgmsElpc.getCarRent()).isEqualTo(DEFAULT_CAR_RENT);
        assertThat(testPgmsElpc.getGasBill()).isEqualTo(DEFAULT_GAS_BILL);
        assertThat(testPgmsElpc.getSantryWaterTax()).isEqualTo(DEFAULT_SANTRY_WATER_TAX);
        assertThat(testPgmsElpc.getBankAcc()).isEqualTo(DEFAULT_BANK_ACC);
        assertThat(testPgmsElpc.getAccBookNo()).isEqualTo(DEFAULT_ACC_BOOK_NO);
        assertThat(testPgmsElpc.getBookPageNo()).isEqualTo(DEFAULT_BOOK_PAGE_NO);
        assertThat(testPgmsElpc.getBankInterest()).isEqualTo(DEFAULT_BANK_INTEREST);
        assertThat(testPgmsElpc.getMonlyDepRateFrSalary()).isEqualTo(DEFAULT_MONLY_DEP_RATE_FR_SALARY);
        assertThat(testPgmsElpc.getExpectedDeposition()).isEqualTo(DEFAULT_EXPECTED_DEPOSITION);
        assertThat(testPgmsElpc.getAccDate()).isEqualTo(DEFAULT_ACC_DATE);
        assertThat(testPgmsElpc.getAppNo()).isEqualTo(DEFAULT_APP_NO);
        assertThat(testPgmsElpc.getAppDate()).isEqualTo(DEFAULT_APP_DATE);
        assertThat(testPgmsElpc.getAppType()).isEqualTo(DEFAULT_APP_TYPE);
        assertThat(testPgmsElpc.getAppComments()).isEqualTo(DEFAULT_APP_COMMENTS);
        assertThat(testPgmsElpc.getAprvStatus()).isEqualTo(DEFAULT_APRV_STATUS);
        assertThat(testPgmsElpc.getAprvDate()).isEqualTo(DEFAULT_APRV_DATE);
        assertThat(testPgmsElpc.getAprvBy()).isEqualTo(DEFAULT_APRV_BY);
        assertThat(testPgmsElpc.getNotificationStatus()).isEqualTo(DEFAULT_NOTIFICATION_STATUS);
        assertThat(testPgmsElpc.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPgmsElpc.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPgmsElpc.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPgmsElpc.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testPgmsElpc.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void checkEmpCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setEmpCode(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setInstCode(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setEmpName(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setInstName(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesigIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setDesigId(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setDesignation(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setDateOfBirth(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJoinDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setJoinDate(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeginDateOfRetirementIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setBeginDateOfRetiremnt(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetirementDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setRetirementDate(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastRcvPayscaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setLastRcvPayscale(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncrsDtOfYrlyPaymentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setIncrsDtOfYrlyPayment(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGainingLeaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setGainingLeave(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLeaveTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setLeaveType(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLeaveTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setLeaveTotal(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMainPaymentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setMainPayment(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncrMonRateLeavingIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setIncrMonRateLeaving(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpecialPaymentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setSpecialPayment(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpecialAllowanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setSpecialAllowance(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHouserentAlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setHouserentAl(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTreatmentAlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setTreatmentAl(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDearnessAlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setDearnessAl(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTravellingAlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setTravellingAl(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLaundryAlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setLaundryAl(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPersonalAlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setPersonalAl(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTechnicalAlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setTechnicalAl(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHospitalityAlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setHospitalityAl(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTiffinAlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setTiffinAl(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdvOfMakingHouseIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAdvOfMakingHouse(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVechileStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setVechileStatus(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdvTravAlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAdvTravAl(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdvSalaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAdvSalary(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHouseRentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setHouseRent(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCarRentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setCarRent(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGasBillIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setGasBill(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSantryWaterTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setSantryWaterTax(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankAccIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setBankAcc(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccBookNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAccBookNo(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBookPageNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setBookPageNo(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankInterestIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setBankInterest(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonlyDepRateFrSalaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setMonlyDepRateFrSalary(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpectedDepositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setExpectedDeposition(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAccDate(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAppNo(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAppDate(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAppType(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppComentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAppComments(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAprvStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAprvStatus(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAprvDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAprvDate(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAprvByIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setAprvBy(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsElpcRepository.findAll().size();
        // set the field null
        pgmsElpc.setNotificationStatus(null);

        // Create the PgmsElpc, which fails.

        restPgmsElpcMockMvc.perform(post("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isBadRequest());

        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsElpcs() throws Exception {
        // Initialize the database
        pgmsElpcRepository.saveAndFlush(pgmsElpc);

        // Get all the pgmsElpcs
        restPgmsElpcMockMvc.perform(get("/api/pgmsElpcs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsElpc.getId().intValue())))
                .andExpect(jsonPath("$.[*].empCode").value(hasItem(DEFAULT_EMP_CODE.toString())))
                .andExpect(jsonPath("$.[*].instCode").value(hasItem(DEFAULT_INST_CODE.toString())))
                .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME.toString())))
                .andExpect(jsonPath("$.[*].instName").value(hasItem(DEFAULT_INST_NAME.toString())))
                .andExpect(jsonPath("$.[*].desigId").value(hasItem(DEFAULT_DESIG_ID.intValue())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
                .andExpect(jsonPath("$.[*].joinDate").value(hasItem(DEFAULT_JOIN_DATE.toString())))
                .andExpect(jsonPath("$.[*].beginDateOfRetiremnt").value(hasItem(DEFAULT_BEGIN_DATE_OF_RETIREMNT.toString())))
                .andExpect(jsonPath("$.[*].retirementDate").value(hasItem(DEFAULT_RETIREMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].lastRcvPayscale").value(hasItem(DEFAULT_LAST_RCV_PAYSCALE.intValue())))
                .andExpect(jsonPath("$.[*].incrsDtOfYrlyPayment").value(hasItem(DEFAULT_INCRS_DT_OF_YRLY_PAYMENT.intValue())))
                .andExpect(jsonPath("$.[*].gainingLeave").value(hasItem(DEFAULT_GAINING_LEAVE.intValue())))
                .andExpect(jsonPath("$.[*].leaveType").value(hasItem(DEFAULT_LEAVE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].leaveTotal").value(hasItem(DEFAULT_LEAVE_TOTAL.intValue())))
                .andExpect(jsonPath("$.[*].monPayDescVsource").value(hasItem(DEFAULT_MON_PAY_DESC_VSOURCE.intValue())))
                .andExpect(jsonPath("$.[*].mainPayment").value(hasItem(DEFAULT_MAIN_PAYMENT.intValue())))
                .andExpect(jsonPath("$.[*].incrMonRateLeaving").value(hasItem(DEFAULT_INCR_MON_RATE_LEAVING.intValue())))
                .andExpect(jsonPath("$.[*].specialPayment").value(hasItem(DEFAULT_SPECIAL_PAYMENT.intValue())))
                .andExpect(jsonPath("$.[*].specialAllowance").value(hasItem(DEFAULT_SPECIAL_ALLOWANCE.intValue())))
                .andExpect(jsonPath("$.[*].houserentAl").value(hasItem(DEFAULT_HOUSERENT_AL.intValue())))
                .andExpect(jsonPath("$.[*].treatmentAl").value(hasItem(DEFAULT_TREATMENT_AL.intValue())))
                .andExpect(jsonPath("$.[*].dearnessAl").value(hasItem(DEFAULT_DEARNESS_AL.intValue())))
                .andExpect(jsonPath("$.[*].travellingAl").value(hasItem(DEFAULT_TRAVELLING_AL.intValue())))
                .andExpect(jsonPath("$.[*].laundryAl").value(hasItem(DEFAULT_LAUNDRY_AL.intValue())))
                .andExpect(jsonPath("$.[*].personalAl").value(hasItem(DEFAULT_PERSONAL_AL.intValue())))
                .andExpect(jsonPath("$.[*].technicalAl").value(hasItem(DEFAULT_TECHNICAL_AL.intValue())))
                .andExpect(jsonPath("$.[*].hospitalityAl").value(hasItem(DEFAULT_HOSPITALITY_AL.intValue())))
                .andExpect(jsonPath("$.[*].tiffinAl").value(hasItem(DEFAULT_TIFFIN_AL.intValue())))
                .andExpect(jsonPath("$.[*].advOfMakingHouse").value(hasItem(DEFAULT_ADV_OF_MAKING_HOUSE.intValue())))
                .andExpect(jsonPath("$.[*].vechileStatus").value(hasItem(DEFAULT_VECHILE_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].advTravAl").value(hasItem(DEFAULT_ADV_TRAV_AL.intValue())))
                .andExpect(jsonPath("$.[*].advSalary").value(hasItem(DEFAULT_ADV_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.intValue())))
                .andExpect(jsonPath("$.[*].carRent").value(hasItem(DEFAULT_CAR_RENT.intValue())))
                .andExpect(jsonPath("$.[*].gasBill").value(hasItem(DEFAULT_GAS_BILL.intValue())))
                .andExpect(jsonPath("$.[*].santryWaterTax").value(hasItem(DEFAULT_SANTRY_WATER_TAX.toString())))
                .andExpect(jsonPath("$.[*].bankAcc").value(hasItem(DEFAULT_BANK_ACC.toString())))
                .andExpect(jsonPath("$.[*].accBookNo").value(hasItem(DEFAULT_ACC_BOOK_NO.toString())))
                .andExpect(jsonPath("$.[*].bookPageNo").value(hasItem(DEFAULT_BOOK_PAGE_NO.toString())))
                .andExpect(jsonPath("$.[*].bankInterest").value(hasItem(DEFAULT_BANK_INTEREST.toString())))
                .andExpect(jsonPath("$.[*].monlyDepRateFrSalary").value(hasItem(DEFAULT_MONLY_DEP_RATE_FR_SALARY.toString())))
                .andExpect(jsonPath("$.[*].expectedDeposition").value(hasItem(DEFAULT_EXPECTED_DEPOSITION.toString())))
                .andExpect(jsonPath("$.[*].accDate").value(hasItem(DEFAULT_ACC_DATE.toString())))
                .andExpect(jsonPath("$.[*].appNo").value(hasItem(DEFAULT_APP_NO.toString())))
                .andExpect(jsonPath("$.[*].appDate").value(hasItem(DEFAULT_APP_DATE.toString())))
                .andExpect(jsonPath("$.[*].appType").value(hasItem(DEFAULT_APP_TYPE.toString())))
                .andExpect(jsonPath("$.[*].appComments").value(hasItem(DEFAULT_APP_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].aprvStatus").value(hasItem(DEFAULT_APRV_STATUS.toString())))
                .andExpect(jsonPath("$.[*].aprvDate").value(hasItem(DEFAULT_APRV_DATE.toString())))
                .andExpect(jsonPath("$.[*].aprvBy").value(hasItem(DEFAULT_APRV_BY.intValue())))
                .andExpect(jsonPath("$.[*].notificationStatus").value(hasItem(DEFAULT_NOTIFICATION_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPgmsElpc() throws Exception {
        // Initialize the database
        pgmsElpcRepository.saveAndFlush(pgmsElpc);

        // Get the pgmsElpc
        restPgmsElpcMockMvc.perform(get("/api/pgmsElpcs/{id}", pgmsElpc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsElpc.getId().intValue()))
            .andExpect(jsonPath("$.empCode").value(DEFAULT_EMP_CODE.toString()))
            .andExpect(jsonPath("$.instCode").value(DEFAULT_INST_CODE.toString()))
            .andExpect(jsonPath("$.empName").value(DEFAULT_EMP_NAME.toString()))
            .andExpect(jsonPath("$.instName").value(DEFAULT_INST_NAME.toString()))
            .andExpect(jsonPath("$.desigId").value(DEFAULT_DESIG_ID.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.joinDate").value(DEFAULT_JOIN_DATE.toString()))
            .andExpect(jsonPath("$.beginDateOfRetiremnt").value(DEFAULT_BEGIN_DATE_OF_RETIREMNT.toString()))
            .andExpect(jsonPath("$.retirementDate").value(DEFAULT_RETIREMENT_DATE.toString()))
            .andExpect(jsonPath("$.lastRcvPayscale").value(DEFAULT_LAST_RCV_PAYSCALE.intValue()))
            .andExpect(jsonPath("$.incrsDtOfYrlyPayment").value(DEFAULT_INCRS_DT_OF_YRLY_PAYMENT.intValue()))
            .andExpect(jsonPath("$.gainingLeave").value(DEFAULT_GAINING_LEAVE.intValue()))
            .andExpect(jsonPath("$.leaveType").value(DEFAULT_LEAVE_TYPE.toString()))
            .andExpect(jsonPath("$.leaveTotal").value(DEFAULT_LEAVE_TOTAL.intValue()))
            .andExpect(jsonPath("$.monPayDescVsource").value(DEFAULT_MON_PAY_DESC_VSOURCE.intValue()))
            .andExpect(jsonPath("$.mainPayment").value(DEFAULT_MAIN_PAYMENT.intValue()))
            .andExpect(jsonPath("$.incrMonRateLeaving").value(DEFAULT_INCR_MON_RATE_LEAVING.intValue()))
            .andExpect(jsonPath("$.specialPayment").value(DEFAULT_SPECIAL_PAYMENT.intValue()))
            .andExpect(jsonPath("$.specialAllowance").value(DEFAULT_SPECIAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.houserentAl").value(DEFAULT_HOUSERENT_AL.intValue()))
            .andExpect(jsonPath("$.treatmentAl").value(DEFAULT_TREATMENT_AL.intValue()))
            .andExpect(jsonPath("$.dearnessAl").value(DEFAULT_DEARNESS_AL.intValue()))
            .andExpect(jsonPath("$.travellingAl").value(DEFAULT_TRAVELLING_AL.intValue()))
            .andExpect(jsonPath("$.laundryAl").value(DEFAULT_LAUNDRY_AL.intValue()))
            .andExpect(jsonPath("$.personalAl").value(DEFAULT_PERSONAL_AL.intValue()))
            .andExpect(jsonPath("$.technicalAl").value(DEFAULT_TECHNICAL_AL.intValue()))
            .andExpect(jsonPath("$.hospitalityAl").value(DEFAULT_HOSPITALITY_AL.intValue()))
            .andExpect(jsonPath("$.tiffinAl").value(DEFAULT_TIFFIN_AL.intValue()))
            .andExpect(jsonPath("$.advOfMakingHouse").value(DEFAULT_ADV_OF_MAKING_HOUSE.intValue()))
            .andExpect(jsonPath("$.vechileStatus").value(DEFAULT_VECHILE_STATUS.intValue()))
            .andExpect(jsonPath("$.advTravAl").value(DEFAULT_ADV_TRAV_AL.intValue()))
            .andExpect(jsonPath("$.advSalary").value(DEFAULT_ADV_SALARY.intValue()))
            .andExpect(jsonPath("$.houseRent").value(DEFAULT_HOUSE_RENT.intValue()))
            .andExpect(jsonPath("$.carRent").value(DEFAULT_CAR_RENT.intValue()))
            .andExpect(jsonPath("$.gasBill").value(DEFAULT_GAS_BILL.intValue()))
            .andExpect(jsonPath("$.santryWaterTax").value(DEFAULT_SANTRY_WATER_TAX.toString()))
            .andExpect(jsonPath("$.bankAcc").value(DEFAULT_BANK_ACC.toString()))
            .andExpect(jsonPath("$.accBookNo").value(DEFAULT_ACC_BOOK_NO.toString()))
            .andExpect(jsonPath("$.bookPageNo").value(DEFAULT_BOOK_PAGE_NO.toString()))
            .andExpect(jsonPath("$.bankInterest").value(DEFAULT_BANK_INTEREST.toString()))
            .andExpect(jsonPath("$.monlyDepRateFrSalary").value(DEFAULT_MONLY_DEP_RATE_FR_SALARY.toString()))
            .andExpect(jsonPath("$.expectedDeposition").value(DEFAULT_EXPECTED_DEPOSITION.toString()))
            .andExpect(jsonPath("$.accDate").value(DEFAULT_ACC_DATE.toString()))
            .andExpect(jsonPath("$.appNo").value(DEFAULT_APP_NO.toString()))
            .andExpect(jsonPath("$.appDate").value(DEFAULT_APP_DATE.toString()))
            .andExpect(jsonPath("$.appType").value(DEFAULT_APP_TYPE.toString()))
            .andExpect(jsonPath("$.appComments").value(DEFAULT_APP_COMMENTS.toString()))
            .andExpect(jsonPath("$.aprvStatus").value(DEFAULT_APRV_STATUS.toString()))
            .andExpect(jsonPath("$.aprvDate").value(DEFAULT_APRV_DATE.toString()))
            .andExpect(jsonPath("$.aprvBy").value(DEFAULT_APRV_BY.intValue()))
            .andExpect(jsonPath("$.notificationStatus").value(DEFAULT_NOTIFICATION_STATUS.intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsElpc() throws Exception {
        // Get the pgmsElpc
        restPgmsElpcMockMvc.perform(get("/api/pgmsElpcs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsElpc() throws Exception {
        // Initialize the database
        pgmsElpcRepository.saveAndFlush(pgmsElpc);

		int databaseSizeBeforeUpdate = pgmsElpcRepository.findAll().size();

        // Update the pgmsElpc
        pgmsElpc.setEmpCode(UPDATED_EMP_CODE);
        pgmsElpc.setInstCode(UPDATED_INST_CODE);
        pgmsElpc.setEmpName(UPDATED_EMP_NAME);
        pgmsElpc.setInstName(UPDATED_INST_NAME);
        pgmsElpc.setDesigId(UPDATED_DESIG_ID);
        pgmsElpc.setDesignation(UPDATED_DESIGNATION);
        pgmsElpc.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        pgmsElpc.setJoinDate(UPDATED_JOIN_DATE);
        pgmsElpc.setBeginDateOfRetiremnt(UPDATED_BEGIN_DATE_OF_RETIREMNT);
        pgmsElpc.setRetirementDate(UPDATED_RETIREMENT_DATE);
        pgmsElpc.setLastRcvPayscale(UPDATED_LAST_RCV_PAYSCALE);
        pgmsElpc.setIncrsDtOfYrlyPayment(UPDATED_INCRS_DT_OF_YRLY_PAYMENT);
        pgmsElpc.setGainingLeave(UPDATED_GAINING_LEAVE);
        pgmsElpc.setLeaveType(UPDATED_LEAVE_TYPE);
        pgmsElpc.setLeaveTotal(UPDATED_LEAVE_TOTAL);
//        pgmsElpc.setMonPayDescVsource(UPDATED_MON_PAY_DESC_VSOURCE);
        pgmsElpc.setMainPayment(UPDATED_MAIN_PAYMENT);
        pgmsElpc.setIncrMonRateLeaving(UPDATED_INCR_MON_RATE_LEAVING);
        pgmsElpc.setSpecialPayment(UPDATED_SPECIAL_PAYMENT);
        pgmsElpc.setSpecialAllowance(UPDATED_SPECIAL_ALLOWANCE);
        pgmsElpc.setHouserentAl(UPDATED_HOUSERENT_AL);
        pgmsElpc.setTreatmentAl(UPDATED_TREATMENT_AL);
        pgmsElpc.setDearnessAl(UPDATED_DEARNESS_AL);
        pgmsElpc.setTravellingAl(UPDATED_TRAVELLING_AL);
        pgmsElpc.setLaundryAl(UPDATED_LAUNDRY_AL);
        pgmsElpc.setPersonalAl(UPDATED_PERSONAL_AL);
        pgmsElpc.setTechnicalAl(UPDATED_TECHNICAL_AL);
        pgmsElpc.setHospitalityAl(UPDATED_HOSPITALITY_AL);
        pgmsElpc.setTiffinAl(UPDATED_TIFFIN_AL);
        pgmsElpc.setAdvOfMakingHouse(UPDATED_ADV_OF_MAKING_HOUSE);
        pgmsElpc.setVechileStatus(UPDATED_VECHILE_STATUS);
        pgmsElpc.setAdvTravAl(UPDATED_ADV_TRAV_AL);
        pgmsElpc.setAdvSalary(UPDATED_ADV_SALARY);
        pgmsElpc.setHouseRent(UPDATED_HOUSE_RENT);
        pgmsElpc.setCarRent(UPDATED_CAR_RENT);
        pgmsElpc.setGasBill(UPDATED_GAS_BILL);
        pgmsElpc.setSantryWaterTax(UPDATED_SANTRY_WATER_TAX);
        pgmsElpc.setBankAcc(UPDATED_BANK_ACC);
        pgmsElpc.setAccBookNo(UPDATED_ACC_BOOK_NO);
        pgmsElpc.setBookPageNo(UPDATED_BOOK_PAGE_NO);
        pgmsElpc.setBankInterest(UPDATED_BANK_INTEREST);
        pgmsElpc.setMonlyDepRateFrSalary(UPDATED_MONLY_DEP_RATE_FR_SALARY);
        pgmsElpc.setExpectedDeposition(UPDATED_EXPECTED_DEPOSITION);
        pgmsElpc.setAccDate(UPDATED_ACC_DATE);
        pgmsElpc.setAppNo(UPDATED_APP_NO);
        pgmsElpc.setAppDate(UPDATED_APP_DATE);
        pgmsElpc.setAppType(UPDATED_APP_TYPE);
        pgmsElpc.setAppComments(UPDATED_APP_COMMENTS);
        pgmsElpc.setAprvStatus(UPDATED_APRV_STATUS);
        pgmsElpc.setAprvDate(UPDATED_APRV_DATE);
        pgmsElpc.setAprvBy(UPDATED_APRV_BY);
        pgmsElpc.setNotificationStatus(UPDATED_NOTIFICATION_STATUS);
        pgmsElpc.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pgmsElpc.setCreateDate(UPDATED_CREATE_DATE);
        pgmsElpc.setCreateBy(UPDATED_CREATE_BY);
        pgmsElpc.setUpdateBy(UPDATED_UPDATE_BY);
        pgmsElpc.setUpdateDate(UPDATED_UPDATE_DATE);

        restPgmsElpcMockMvc.perform(put("/api/pgmsElpcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsElpc)))
                .andExpect(status().isOk());

        // Validate the PgmsElpc in the database
        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeUpdate);
        PgmsElpc testPgmsElpc = pgmsElpcs.get(pgmsElpcs.size() - 1);
        assertThat(testPgmsElpc.getEmpCode()).isEqualTo(UPDATED_EMP_CODE);
        assertThat(testPgmsElpc.getInstCode()).isEqualTo(UPDATED_INST_CODE);
        assertThat(testPgmsElpc.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testPgmsElpc.getInstName()).isEqualTo(UPDATED_INST_NAME);
        assertThat(testPgmsElpc.getDesigId()).isEqualTo(UPDATED_DESIG_ID);
        assertThat(testPgmsElpc.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testPgmsElpc.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPgmsElpc.getJoinDate()).isEqualTo(UPDATED_JOIN_DATE);
        assertThat(testPgmsElpc.getBeginDateOfRetiremnt()).isEqualTo(UPDATED_BEGIN_DATE_OF_RETIREMNT);
        assertThat(testPgmsElpc.getRetirementDate()).isEqualTo(UPDATED_RETIREMENT_DATE);
        assertThat(testPgmsElpc.getLastRcvPayscale()).isEqualTo(UPDATED_LAST_RCV_PAYSCALE);
        assertThat(testPgmsElpc.getIncrsDtOfYrlyPayment()).isEqualTo(UPDATED_INCRS_DT_OF_YRLY_PAYMENT);
        assertThat(testPgmsElpc.getGainingLeave()).isEqualTo(UPDATED_GAINING_LEAVE);
        assertThat(testPgmsElpc.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testPgmsElpc.getLeaveTotal()).isEqualTo(UPDATED_LEAVE_TOTAL);
//        assertThat(testPgmsElpc.getMonPayDescVsource()).isEqualTo(UPDATED_MON_PAY_DESC_VSOURCE);
        assertThat(testPgmsElpc.getMainPayment()).isEqualTo(UPDATED_MAIN_PAYMENT);
        assertThat(testPgmsElpc.getIncrMonRateLeaving()).isEqualTo(UPDATED_INCR_MON_RATE_LEAVING);
        assertThat(testPgmsElpc.getSpecialPayment()).isEqualTo(UPDATED_SPECIAL_PAYMENT);
        assertThat(testPgmsElpc.getSpecialAllowance()).isEqualTo(UPDATED_SPECIAL_ALLOWANCE);
        assertThat(testPgmsElpc.getHouserentAl()).isEqualTo(UPDATED_HOUSERENT_AL);
        assertThat(testPgmsElpc.getTreatmentAl()).isEqualTo(UPDATED_TREATMENT_AL);
        assertThat(testPgmsElpc.getDearnessAl()).isEqualTo(UPDATED_DEARNESS_AL);
        assertThat(testPgmsElpc.getTravellingAl()).isEqualTo(UPDATED_TRAVELLING_AL);
        assertThat(testPgmsElpc.getLaundryAl()).isEqualTo(UPDATED_LAUNDRY_AL);
        assertThat(testPgmsElpc.getPersonalAl()).isEqualTo(UPDATED_PERSONAL_AL);
        assertThat(testPgmsElpc.getTechnicalAl()).isEqualTo(UPDATED_TECHNICAL_AL);
        assertThat(testPgmsElpc.getHospitalityAl()).isEqualTo(UPDATED_HOSPITALITY_AL);
        assertThat(testPgmsElpc.getTiffinAl()).isEqualTo(UPDATED_TIFFIN_AL);
        assertThat(testPgmsElpc.getAdvOfMakingHouse()).isEqualTo(UPDATED_ADV_OF_MAKING_HOUSE);
        assertThat(testPgmsElpc.getVechileStatus()).isEqualTo(UPDATED_VECHILE_STATUS);
        assertThat(testPgmsElpc.getAdvTravAl()).isEqualTo(UPDATED_ADV_TRAV_AL);
        assertThat(testPgmsElpc.getAdvSalary()).isEqualTo(UPDATED_ADV_SALARY);
        assertThat(testPgmsElpc.getHouseRent()).isEqualTo(UPDATED_HOUSE_RENT);
        assertThat(testPgmsElpc.getCarRent()).isEqualTo(UPDATED_CAR_RENT);
        assertThat(testPgmsElpc.getGasBill()).isEqualTo(UPDATED_GAS_BILL);
        assertThat(testPgmsElpc.getSantryWaterTax()).isEqualTo(UPDATED_SANTRY_WATER_TAX);
        assertThat(testPgmsElpc.getBankAcc()).isEqualTo(UPDATED_BANK_ACC);
        assertThat(testPgmsElpc.getAccBookNo()).isEqualTo(UPDATED_ACC_BOOK_NO);
        assertThat(testPgmsElpc.getBookPageNo()).isEqualTo(UPDATED_BOOK_PAGE_NO);
        assertThat(testPgmsElpc.getBankInterest()).isEqualTo(UPDATED_BANK_INTEREST);
        assertThat(testPgmsElpc.getMonlyDepRateFrSalary()).isEqualTo(UPDATED_MONLY_DEP_RATE_FR_SALARY);
        assertThat(testPgmsElpc.getExpectedDeposition()).isEqualTo(UPDATED_EXPECTED_DEPOSITION);
        assertThat(testPgmsElpc.getAccDate()).isEqualTo(UPDATED_ACC_DATE);
        assertThat(testPgmsElpc.getAppNo()).isEqualTo(UPDATED_APP_NO);
        assertThat(testPgmsElpc.getAppDate()).isEqualTo(UPDATED_APP_DATE);
        assertThat(testPgmsElpc.getAppType()).isEqualTo(UPDATED_APP_TYPE);
        assertThat(testPgmsElpc.getAppComments()).isEqualTo(UPDATED_APP_COMMENTS);
        assertThat(testPgmsElpc.getAprvStatus()).isEqualTo(UPDATED_APRV_STATUS);
        assertThat(testPgmsElpc.getAprvDate()).isEqualTo(UPDATED_APRV_DATE);
        assertThat(testPgmsElpc.getAprvBy()).isEqualTo(UPDATED_APRV_BY);
        assertThat(testPgmsElpc.getNotificationStatus()).isEqualTo(UPDATED_NOTIFICATION_STATUS);
        assertThat(testPgmsElpc.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPgmsElpc.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPgmsElpc.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPgmsElpc.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testPgmsElpc.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deletePgmsElpc() throws Exception {
        // Initialize the database
        pgmsElpcRepository.saveAndFlush(pgmsElpc);

		int databaseSizeBeforeDelete = pgmsElpcRepository.findAll().size();

        // Get the pgmsElpc
        restPgmsElpcMockMvc.perform(delete("/api/pgmsElpcs/{id}", pgmsElpc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsElpc> pgmsElpcs = pgmsElpcRepository.findAll();
        assertThat(pgmsElpcs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
