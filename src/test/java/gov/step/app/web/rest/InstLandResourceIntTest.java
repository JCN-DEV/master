package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstLand;
import gov.step.app.repository.InstLandRepository;
import gov.step.app.repository.search.InstLandSearchRepository;

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
 * Test class for the InstLandResource REST controller.
 *
 * @see InstLandResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstLandResourceIntTest {

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
    private InstLandRepository instLandRepository;

    @Inject
    private InstLandSearchRepository instLandSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstLandMockMvc;

    private InstLand instLand;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstLandResource instLandResource = new InstLandResource();
        ReflectionTestUtils.setField(instLandResource, "instLandRepository", instLandRepository);
        ReflectionTestUtils.setField(instLandResource, "instLandSearchRepository", instLandSearchRepository);
        this.restInstLandMockMvc = MockMvcBuilders.standaloneSetup(instLandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instLand = new InstLand();
        instLand.setMouja(DEFAULT_MOUJA);
        instLand.setJlNo(DEFAULT_JL_NO);
        instLand.setLedgerNo(DEFAULT_LEDGER_NO);
        instLand.setDagNo(DEFAULT_DAG_NO);
        instLand.setAmountOfLand(DEFAULT_AMOUNT_OF_LAND);
        instLand.setLandRegistrationLedgerNo(DEFAULT_LAND_REGISTRATION_LEDGER_NO);
        instLand.setLandRegistrationDate(DEFAULT_LAND_REGISTRATION_DATE);
        instLand.setLastTaxPaymentDate(DEFAULT_LAST_TAX_PAYMENT_DATE);
        instLand.setBoundaryNorth(DEFAULT_BOUNDARY_NORTH);
        instLand.setBoundarySouth(DEFAULT_BOUNDARY_SOUTH);
        instLand.setBoundaryEast(DEFAULT_BOUNDARY_EAST);
        instLand.setBoundaryWest(DEFAULT_BOUNDARY_WEST);
    }

    @Test
    @Transactional
    public void createInstLand() throws Exception {
        int databaseSizeBeforeCreate = instLandRepository.findAll().size();

        // Create the InstLand

        restInstLandMockMvc.perform(post("/api/instLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLand)))
                .andExpect(status().isCreated());

        // Validate the InstLand in the database
        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeCreate + 1);
        InstLand testInstLand = instLands.get(instLands.size() - 1);
        assertThat(testInstLand.getMouja()).isEqualTo(DEFAULT_MOUJA);
        assertThat(testInstLand.getJlNo()).isEqualTo(DEFAULT_JL_NO);
        assertThat(testInstLand.getLedgerNo()).isEqualTo(DEFAULT_LEDGER_NO);
        assertThat(testInstLand.getDagNo()).isEqualTo(DEFAULT_DAG_NO);
        assertThat(testInstLand.getAmountOfLand()).isEqualTo(DEFAULT_AMOUNT_OF_LAND);
        assertThat(testInstLand.getLandRegistrationLedgerNo()).isEqualTo(DEFAULT_LAND_REGISTRATION_LEDGER_NO);
        assertThat(testInstLand.getLandRegistrationDate()).isEqualTo(DEFAULT_LAND_REGISTRATION_DATE);
        assertThat(testInstLand.getLastTaxPaymentDate()).isEqualTo(DEFAULT_LAST_TAX_PAYMENT_DATE);
        assertThat(testInstLand.getBoundaryNorth()).isEqualTo(DEFAULT_BOUNDARY_NORTH);
        assertThat(testInstLand.getBoundarySouth()).isEqualTo(DEFAULT_BOUNDARY_SOUTH);
        assertThat(testInstLand.getBoundaryEast()).isEqualTo(DEFAULT_BOUNDARY_EAST);
        assertThat(testInstLand.getBoundaryWest()).isEqualTo(DEFAULT_BOUNDARY_WEST);
    }

    @Test
    @Transactional
    public void checkMoujaIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandRepository.findAll().size();
        // set the field null
        instLand.setMouja(null);

        // Create the InstLand, which fails.

        restInstLandMockMvc.perform(post("/api/instLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLand)))
                .andExpect(status().isBadRequest());

        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJlNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandRepository.findAll().size();
        // set the field null
        instLand.setJlNo(null);

        // Create the InstLand, which fails.

        restInstLandMockMvc.perform(post("/api/instLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLand)))
                .andExpect(status().isBadRequest());

        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLedgerNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandRepository.findAll().size();
        // set the field null
        instLand.setLedgerNo(null);

        // Create the InstLand, which fails.

        restInstLandMockMvc.perform(post("/api/instLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLand)))
                .andExpect(status().isBadRequest());

        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDagNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandRepository.findAll().size();
        // set the field null
        instLand.setDagNo(null);

        // Create the InstLand, which fails.

        restInstLandMockMvc.perform(post("/api/instLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLand)))
                .andExpect(status().isBadRequest());

        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountOfLandIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandRepository.findAll().size();
        // set the field null
        instLand.setAmountOfLand(null);

        // Create the InstLand, which fails.

        restInstLandMockMvc.perform(post("/api/instLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLand)))
                .andExpect(status().isBadRequest());

        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLandRegistrationLedgerNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandRepository.findAll().size();
        // set the field null
        instLand.setLandRegistrationLedgerNo(null);

        // Create the InstLand, which fails.

        restInstLandMockMvc.perform(post("/api/instLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLand)))
                .andExpect(status().isBadRequest());

        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLandRegistrationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandRepository.findAll().size();
        // set the field null
        instLand.setLandRegistrationDate(null);

        // Create the InstLand, which fails.

        restInstLandMockMvc.perform(post("/api/instLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLand)))
                .andExpect(status().isBadRequest());

        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastTaxPaymentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instLandRepository.findAll().size();
        // set the field null
        instLand.setLastTaxPaymentDate(null);

        // Create the InstLand, which fails.

        restInstLandMockMvc.perform(post("/api/instLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLand)))
                .andExpect(status().isBadRequest());

        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstLands() throws Exception {
        // Initialize the database
        instLandRepository.saveAndFlush(instLand);

        // Get all the instLands
        restInstLandMockMvc.perform(get("/api/instLands"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instLand.getId().intValue())))
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
    public void getInstLand() throws Exception {
        // Initialize the database
        instLandRepository.saveAndFlush(instLand);

        // Get the instLand
        restInstLandMockMvc.perform(get("/api/instLands/{id}", instLand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instLand.getId().intValue()))
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
    public void getNonExistingInstLand() throws Exception {
        // Get the instLand
        restInstLandMockMvc.perform(get("/api/instLands/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstLand() throws Exception {
        // Initialize the database
        instLandRepository.saveAndFlush(instLand);

		int databaseSizeBeforeUpdate = instLandRepository.findAll().size();

        // Update the instLand
        instLand.setMouja(UPDATED_MOUJA);
        instLand.setJlNo(UPDATED_JL_NO);
        instLand.setLedgerNo(UPDATED_LEDGER_NO);
        instLand.setDagNo(UPDATED_DAG_NO);
        instLand.setAmountOfLand(UPDATED_AMOUNT_OF_LAND);
        instLand.setLandRegistrationLedgerNo(UPDATED_LAND_REGISTRATION_LEDGER_NO);
        instLand.setLandRegistrationDate(UPDATED_LAND_REGISTRATION_DATE);
        instLand.setLastTaxPaymentDate(UPDATED_LAST_TAX_PAYMENT_DATE);
        instLand.setBoundaryNorth(UPDATED_BOUNDARY_NORTH);
        instLand.setBoundarySouth(UPDATED_BOUNDARY_SOUTH);
        instLand.setBoundaryEast(UPDATED_BOUNDARY_EAST);
        instLand.setBoundaryWest(UPDATED_BOUNDARY_WEST);

        restInstLandMockMvc.perform(put("/api/instLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLand)))
                .andExpect(status().isOk());

        // Validate the InstLand in the database
        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeUpdate);
        InstLand testInstLand = instLands.get(instLands.size() - 1);
        assertThat(testInstLand.getMouja()).isEqualTo(UPDATED_MOUJA);
        assertThat(testInstLand.getJlNo()).isEqualTo(UPDATED_JL_NO);
        assertThat(testInstLand.getLedgerNo()).isEqualTo(UPDATED_LEDGER_NO);
        assertThat(testInstLand.getDagNo()).isEqualTo(UPDATED_DAG_NO);
        assertThat(testInstLand.getAmountOfLand()).isEqualTo(UPDATED_AMOUNT_OF_LAND);
        assertThat(testInstLand.getLandRegistrationLedgerNo()).isEqualTo(UPDATED_LAND_REGISTRATION_LEDGER_NO);
        assertThat(testInstLand.getLandRegistrationDate()).isEqualTo(UPDATED_LAND_REGISTRATION_DATE);
        assertThat(testInstLand.getLastTaxPaymentDate()).isEqualTo(UPDATED_LAST_TAX_PAYMENT_DATE);
        assertThat(testInstLand.getBoundaryNorth()).isEqualTo(UPDATED_BOUNDARY_NORTH);
        assertThat(testInstLand.getBoundarySouth()).isEqualTo(UPDATED_BOUNDARY_SOUTH);
        assertThat(testInstLand.getBoundaryEast()).isEqualTo(UPDATED_BOUNDARY_EAST);
        assertThat(testInstLand.getBoundaryWest()).isEqualTo(UPDATED_BOUNDARY_WEST);
    }

    @Test
    @Transactional
    public void deleteInstLand() throws Exception {
        // Initialize the database
        instLandRepository.saveAndFlush(instLand);

		int databaseSizeBeforeDelete = instLandRepository.findAll().size();

        // Get the instLand
        restInstLandMockMvc.perform(delete("/api/instLands/{id}", instLand.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstLand> instLands = instLandRepository.findAll();
        assertThat(instLands).hasSize(databaseSizeBeforeDelete - 1);
    }
}
