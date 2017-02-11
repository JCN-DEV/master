package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstLandTemp;
import gov.step.app.repository.InstLandTempRepository;
import gov.step.app.repository.search.InstLandTempSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the InstLandTempResource REST controller.
 *
 * @see InstLandTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstLandTempResourceTest {

    private static final String DEFAULT_MOUJA = "AAAAA";
    private static final String UPDATED_MOUJA = "BBBBB";
    private static final String DEFAULT_JL_NO = "AAAAA";
    private static final String UPDATED_JL_NO = "BBBBB";

    private static final BigDecimal DEFAULT_LEDGER_NO = new BigDecimal(1);
    private static final BigDecimal UPDATED_LEDGER_NO = new BigDecimal(2);
    private static final String DEFAULT_DAG_NO = "AAAAA";
    private static final String UPDATED_DAG_NO = "BBBBB";

    private static final BigDecimal DEFAULT_AMOUNT_OF_LAND = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_OF_LAND = new BigDecimal(2);
    private static final String DEFAULT_LAND_REGISTRATION_LEDGER_NO = "AAAAA";
    private static final String UPDATED_LAND_REGISTRATION_LEDGER_NO = "BBBBB";

    private static final LocalDate DEFAULT_LAND_REGISTRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAND_REGISTRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_TAX_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_TAX_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_BOUNDARY_NORTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_BOUNDARY_NORTH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BOUNDARY_SOUTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_BOUNDARY_SOUTH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BOUNDARY_EAST = new BigDecimal(1);
    private static final BigDecimal UPDATED_BOUNDARY_EAST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BOUNDARY_WEST = new BigDecimal(1);
    private static final BigDecimal UPDATED_BOUNDARY_WEST = new BigDecimal(2);

    @Inject
    private InstLandTempRepository instLandTempRepository;

    @Inject
    private InstLandTempSearchRepository instLandTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstLandTempMockMvc;

    private InstLandTemp instLandTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstLandTempResource instLandTempResource = new InstLandTempResource();
        ReflectionTestUtils.setField(instLandTempResource, "instLandTempRepository", instLandTempRepository);
        ReflectionTestUtils.setField(instLandTempResource, "instLandTempSearchRepository", instLandTempSearchRepository);
        this.restInstLandTempMockMvc = MockMvcBuilders.standaloneSetup(instLandTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instLandTemp = new InstLandTemp();
        instLandTemp.setMouja(DEFAULT_MOUJA);
        instLandTemp.setJlNo(DEFAULT_JL_NO);
        instLandTemp.setLedgerNo(DEFAULT_LEDGER_NO);
        instLandTemp.setDagNo(DEFAULT_DAG_NO);
        instLandTemp.setAmountOfLand(DEFAULT_AMOUNT_OF_LAND);
        instLandTemp.setLandRegistrationLedgerNo(DEFAULT_LAND_REGISTRATION_LEDGER_NO);
        instLandTemp.setLandRegistrationDate(DEFAULT_LAND_REGISTRATION_DATE);
        instLandTemp.setLastTaxPaymentDate(DEFAULT_LAST_TAX_PAYMENT_DATE);
        instLandTemp.setBoundaryNorth(DEFAULT_BOUNDARY_NORTH);
        instLandTemp.setBoundarySouth(DEFAULT_BOUNDARY_SOUTH);
        instLandTemp.setBoundaryEast(DEFAULT_BOUNDARY_EAST);
        instLandTemp.setBoundaryWest(DEFAULT_BOUNDARY_WEST);
    }

    @Test
    @Transactional
    public void createInstLandTemp() throws Exception {
        int databaseSizeBeforeCreate = instLandTempRepository.findAll().size();

        // Create the InstLandTemp

        restInstLandTempMockMvc.perform(post("/api/instLandTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLandTemp)))
                .andExpect(status().isCreated());

        // Validate the InstLandTemp in the database
        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstLandTemp testInstLandTemp = instLandTemps.get(instLandTemps.size() - 1);
        assertThat(testInstLandTemp.getMouja()).isEqualTo(DEFAULT_MOUJA);
        assertThat(testInstLandTemp.getJlNo()).isEqualTo(DEFAULT_JL_NO);
        assertThat(testInstLandTemp.getLedgerNo()).isEqualTo(DEFAULT_LEDGER_NO);
        assertThat(testInstLandTemp.getDagNo()).isEqualTo(DEFAULT_DAG_NO);
        assertThat(testInstLandTemp.getAmountOfLand()).isEqualTo(DEFAULT_AMOUNT_OF_LAND);
        assertThat(testInstLandTemp.getLandRegistrationLedgerNo()).isEqualTo(DEFAULT_LAND_REGISTRATION_LEDGER_NO);
        assertThat(testInstLandTemp.getLandRegistrationDate()).isEqualTo(DEFAULT_LAND_REGISTRATION_DATE);
        assertThat(testInstLandTemp.getLastTaxPaymentDate()).isEqualTo(DEFAULT_LAST_TAX_PAYMENT_DATE);
        assertThat(testInstLandTemp.getBoundaryNorth()).isEqualTo(DEFAULT_BOUNDARY_NORTH);
        assertThat(testInstLandTemp.getBoundarySouth()).isEqualTo(DEFAULT_BOUNDARY_SOUTH);
        assertThat(testInstLandTemp.getBoundaryEast()).isEqualTo(DEFAULT_BOUNDARY_EAST);
        assertThat(testInstLandTemp.getBoundaryWest()).isEqualTo(DEFAULT_BOUNDARY_WEST);
    }

    @Test
    @Transactional
    public void checkMoujaIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandTempRepository.findAll().size();
        // set the field null
        instLandTemp.setMouja(null);

        // Create the InstLandTemp, which fails.

        restInstLandTempMockMvc.perform(post("/api/instLandTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLandTemp)))
                .andExpect(status().isBadRequest());

        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJlNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandTempRepository.findAll().size();
        // set the field null
        instLandTemp.setJlNo(null);

        // Create the InstLandTemp, which fails.

        restInstLandTempMockMvc.perform(post("/api/instLandTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLandTemp)))
                .andExpect(status().isBadRequest());

        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLedgerNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandTempRepository.findAll().size();
        // set the field null
        instLandTemp.setLedgerNo(null);

        // Create the InstLandTemp, which fails.

        restInstLandTempMockMvc.perform(post("/api/instLandTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLandTemp)))
                .andExpect(status().isBadRequest());

        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDagNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandTempRepository.findAll().size();
        // set the field null
        instLandTemp.setDagNo(null);

        // Create the InstLandTemp, which fails.

        restInstLandTempMockMvc.perform(post("/api/instLandTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLandTemp)))
                .andExpect(status().isBadRequest());

        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountOfLandIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandTempRepository.findAll().size();
        // set the field null
        instLandTemp.setAmountOfLand(null);

        // Create the InstLandTemp, which fails.

        restInstLandTempMockMvc.perform(post("/api/instLandTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLandTemp)))
                .andExpect(status().isBadRequest());

        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLandRegistrationLedgerNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandTempRepository.findAll().size();
        // set the field null
        instLandTemp.setLandRegistrationLedgerNo(null);

        // Create the InstLandTemp, which fails.

        restInstLandTempMockMvc.perform(post("/api/instLandTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLandTemp)))
                .andExpect(status().isBadRequest());

        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLandRegistrationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandTempRepository.findAll().size();
        // set the field null
        instLandTemp.setLandRegistrationDate(null);

        // Create the InstLandTemp, which fails.

        restInstLandTempMockMvc.perform(post("/api/instLandTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLandTemp)))
                .andExpect(status().isBadRequest());

        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastTaxPaymentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandTempRepository.findAll().size();
        // set the field null
        instLandTemp.setLastTaxPaymentDate(null);

        // Create the InstLandTemp, which fails.

        restInstLandTempMockMvc.perform(post("/api/instLandTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLandTemp)))
                .andExpect(status().isBadRequest());

        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstLandTemps() throws Exception {
        // Initialize the database
        instLandTempRepository.saveAndFlush(instLandTemp);

        // Get all the instLandTemps
        restInstLandTempMockMvc.perform(get("/api/instLandTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instLandTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].mouja").value(hasItem(DEFAULT_MOUJA.toString())))
                .andExpect(jsonPath("$.[*].jlNo").value(hasItem(DEFAULT_JL_NO.toString())))
                .andExpect(jsonPath("$.[*].ledgerNo").value(hasItem(DEFAULT_LEDGER_NO.intValue())))
                .andExpect(jsonPath("$.[*].dagNo").value(hasItem(DEFAULT_DAG_NO.toString())))
                .andExpect(jsonPath("$.[*].amountOfLand").value(hasItem(DEFAULT_AMOUNT_OF_LAND.intValue())))
                .andExpect(jsonPath("$.[*].landRegistrationLedgerNo").value(hasItem(DEFAULT_LAND_REGISTRATION_LEDGER_NO.toString())))
                .andExpect(jsonPath("$.[*].landRegistrationDate").value(hasItem(DEFAULT_LAND_REGISTRATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].lastTaxPaymentDate").value(hasItem(DEFAULT_LAST_TAX_PAYMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].boundaryNorth").value(hasItem(DEFAULT_BOUNDARY_NORTH.intValue())))
                .andExpect(jsonPath("$.[*].boundarySouth").value(hasItem(DEFAULT_BOUNDARY_SOUTH.intValue())))
                .andExpect(jsonPath("$.[*].boundaryEast").value(hasItem(DEFAULT_BOUNDARY_EAST.intValue())))
                .andExpect(jsonPath("$.[*].boundaryWest").value(hasItem(DEFAULT_BOUNDARY_WEST.intValue())));
    }

    @Test
    @Transactional
    public void getInstLandTemp() throws Exception {
        // Initialize the database
        instLandTempRepository.saveAndFlush(instLandTemp);

        // Get the instLandTemp
        restInstLandTempMockMvc.perform(get("/api/instLandTemps/{id}", instLandTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instLandTemp.getId().intValue()))
            .andExpect(jsonPath("$.mouja").value(DEFAULT_MOUJA.toString()))
            .andExpect(jsonPath("$.jlNo").value(DEFAULT_JL_NO.toString()))
            .andExpect(jsonPath("$.ledgerNo").value(DEFAULT_LEDGER_NO.intValue()))
            .andExpect(jsonPath("$.dagNo").value(DEFAULT_DAG_NO.toString()))
            .andExpect(jsonPath("$.amountOfLand").value(DEFAULT_AMOUNT_OF_LAND.intValue()))
            .andExpect(jsonPath("$.landRegistrationLedgerNo").value(DEFAULT_LAND_REGISTRATION_LEDGER_NO.toString()))
            .andExpect(jsonPath("$.landRegistrationDate").value(DEFAULT_LAND_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.lastTaxPaymentDate").value(DEFAULT_LAST_TAX_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.boundaryNorth").value(DEFAULT_BOUNDARY_NORTH.intValue()))
            .andExpect(jsonPath("$.boundarySouth").value(DEFAULT_BOUNDARY_SOUTH.intValue()))
            .andExpect(jsonPath("$.boundaryEast").value(DEFAULT_BOUNDARY_EAST.intValue()))
            .andExpect(jsonPath("$.boundaryWest").value(DEFAULT_BOUNDARY_WEST.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstLandTemp() throws Exception {
        // Get the instLandTemp
        restInstLandTempMockMvc.perform(get("/api/instLandTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstLandTemp() throws Exception {
        // Initialize the database
        instLandTempRepository.saveAndFlush(instLandTemp);

		int databaseSizeBeforeUpdate = instLandTempRepository.findAll().size();

        // Update the instLandTemp
        instLandTemp.setMouja(UPDATED_MOUJA);
        instLandTemp.setJlNo(UPDATED_JL_NO);
        instLandTemp.setLedgerNo(UPDATED_LEDGER_NO);
        instLandTemp.setDagNo(UPDATED_DAG_NO);
        instLandTemp.setAmountOfLand(UPDATED_AMOUNT_OF_LAND);
        instLandTemp.setLandRegistrationLedgerNo(UPDATED_LAND_REGISTRATION_LEDGER_NO);
        instLandTemp.setLandRegistrationDate(UPDATED_LAND_REGISTRATION_DATE);
        instLandTemp.setLastTaxPaymentDate(UPDATED_LAST_TAX_PAYMENT_DATE);
        instLandTemp.setBoundaryNorth(UPDATED_BOUNDARY_NORTH);
        instLandTemp.setBoundarySouth(UPDATED_BOUNDARY_SOUTH);
        instLandTemp.setBoundaryEast(UPDATED_BOUNDARY_EAST);
        instLandTemp.setBoundaryWest(UPDATED_BOUNDARY_WEST);

        restInstLandTempMockMvc.perform(put("/api/instLandTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLandTemp)))
                .andExpect(status().isOk());

        // Validate the InstLandTemp in the database
        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeUpdate);
        InstLandTemp testInstLandTemp = instLandTemps.get(instLandTemps.size() - 1);
        assertThat(testInstLandTemp.getMouja()).isEqualTo(UPDATED_MOUJA);
        assertThat(testInstLandTemp.getJlNo()).isEqualTo(UPDATED_JL_NO);
        assertThat(testInstLandTemp.getLedgerNo()).isEqualTo(UPDATED_LEDGER_NO);
        assertThat(testInstLandTemp.getDagNo()).isEqualTo(UPDATED_DAG_NO);
        assertThat(testInstLandTemp.getAmountOfLand()).isEqualTo(UPDATED_AMOUNT_OF_LAND);
        assertThat(testInstLandTemp.getLandRegistrationLedgerNo()).isEqualTo(UPDATED_LAND_REGISTRATION_LEDGER_NO);
        assertThat(testInstLandTemp.getLandRegistrationDate()).isEqualTo(UPDATED_LAND_REGISTRATION_DATE);
        assertThat(testInstLandTemp.getLastTaxPaymentDate()).isEqualTo(UPDATED_LAST_TAX_PAYMENT_DATE);
        assertThat(testInstLandTemp.getBoundaryNorth()).isEqualTo(UPDATED_BOUNDARY_NORTH);
        assertThat(testInstLandTemp.getBoundarySouth()).isEqualTo(UPDATED_BOUNDARY_SOUTH);
        assertThat(testInstLandTemp.getBoundaryEast()).isEqualTo(UPDATED_BOUNDARY_EAST);
        assertThat(testInstLandTemp.getBoundaryWest()).isEqualTo(UPDATED_BOUNDARY_WEST);
    }

    @Test
    @Transactional
    public void deleteInstLandTemp() throws Exception {
        // Initialize the database
        instLandTempRepository.saveAndFlush(instLandTemp);

		int databaseSizeBeforeDelete = instLandTempRepository.findAll().size();

        // Get the instLandTemp
        restInstLandTempMockMvc.perform(delete("/api/instLandTemps/{id}", instLandTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstLandTemp> instLandTemps = instLandTempRepository.findAll();
        assertThat(instLandTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
