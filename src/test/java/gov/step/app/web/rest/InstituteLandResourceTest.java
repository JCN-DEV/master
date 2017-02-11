package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstituteLand;
import gov.step.app.repository.InstituteLandRepository;
import gov.step.app.repository.search.InstituteLandSearchRepository;

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
 * Test class for the InstituteLandResource REST controller.
 *
 * @see InstituteLandResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstituteLandResourceTest {

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
    private InstituteLandRepository instituteLandRepository;

    @Inject
    private InstituteLandSearchRepository instituteLandSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstituteLandMockMvc;

    private InstituteLand instituteLand;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteLandResource instituteLandResource = new InstituteLandResource();
        ReflectionTestUtils.setField(instituteLandResource, "instituteLandRepository", instituteLandRepository);
        ReflectionTestUtils.setField(instituteLandResource, "instituteLandSearchRepository", instituteLandSearchRepository);
        this.restInstituteLandMockMvc = MockMvcBuilders.standaloneSetup(instituteLandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instituteLand = new InstituteLand();
        instituteLand.setMouja(DEFAULT_MOUJA);
        instituteLand.setJlNo(DEFAULT_JL_NO);
        instituteLand.setLedgerNo(DEFAULT_LEDGER_NO);
        instituteLand.setDagNo(DEFAULT_DAG_NO);
        instituteLand.setAmountOfLand(DEFAULT_AMOUNT_OF_LAND);
        instituteLand.setLandRegistrationLedgerNo(DEFAULT_LAND_REGISTRATION_LEDGER_NO);
        instituteLand.setLandRegistrationDate(DEFAULT_LAND_REGISTRATION_DATE);
        instituteLand.setLastTaxPaymentDate(DEFAULT_LAST_TAX_PAYMENT_DATE);
        instituteLand.setBoundaryNorth(DEFAULT_BOUNDARY_NORTH);
        instituteLand.setBoundarySouth(DEFAULT_BOUNDARY_SOUTH);
        instituteLand.setBoundaryEast(DEFAULT_BOUNDARY_EAST);
        instituteLand.setBoundaryWest(DEFAULT_BOUNDARY_WEST);
    }

    @Test
    @Transactional
    public void createInstituteLand() throws Exception {
        int databaseSizeBeforeCreate = instituteLandRepository.findAll().size();

        // Create the InstituteLand

        restInstituteLandMockMvc.perform(post("/api/instituteLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLand)))
                .andExpect(status().isCreated());

        // Validate the InstituteLand in the database
        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeCreate + 1);
        InstituteLand testInstituteLand = instituteLands.get(instituteLands.size() - 1);
        assertThat(testInstituteLand.getMouja()).isEqualTo(DEFAULT_MOUJA);
        assertThat(testInstituteLand.getJlNo()).isEqualTo(DEFAULT_JL_NO);
        assertThat(testInstituteLand.getLedgerNo()).isEqualTo(DEFAULT_LEDGER_NO);
        assertThat(testInstituteLand.getDagNo()).isEqualTo(DEFAULT_DAG_NO);
        assertThat(testInstituteLand.getAmountOfLand()).isEqualTo(DEFAULT_AMOUNT_OF_LAND);
        assertThat(testInstituteLand.getLandRegistrationLedgerNo()).isEqualTo(DEFAULT_LAND_REGISTRATION_LEDGER_NO);
        assertThat(testInstituteLand.getLandRegistrationDate()).isEqualTo(DEFAULT_LAND_REGISTRATION_DATE);
        assertThat(testInstituteLand.getLastTaxPaymentDate()).isEqualTo(DEFAULT_LAST_TAX_PAYMENT_DATE);
        assertThat(testInstituteLand.getBoundaryNorth()).isEqualTo(DEFAULT_BOUNDARY_NORTH);
        assertThat(testInstituteLand.getBoundarySouth()).isEqualTo(DEFAULT_BOUNDARY_SOUTH);
        assertThat(testInstituteLand.getBoundaryEast()).isEqualTo(DEFAULT_BOUNDARY_EAST);
        assertThat(testInstituteLand.getBoundaryWest()).isEqualTo(DEFAULT_BOUNDARY_WEST);
    }

    @Test
    @Transactional
    public void checkMoujaIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteLandRepository.findAll().size();
        // set the field null
        instituteLand.setMouja(null);

        // Create the InstituteLand, which fails.

        restInstituteLandMockMvc.perform(post("/api/instituteLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLand)))
                .andExpect(status().isBadRequest());

        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJlNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteLandRepository.findAll().size();
        // set the field null
        instituteLand.setJlNo(null);

        // Create the InstituteLand, which fails.

        restInstituteLandMockMvc.perform(post("/api/instituteLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLand)))
                .andExpect(status().isBadRequest());

        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLedgerNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteLandRepository.findAll().size();
        // set the field null
        instituteLand.setLedgerNo(null);

        // Create the InstituteLand, which fails.

        restInstituteLandMockMvc.perform(post("/api/instituteLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLand)))
                .andExpect(status().isBadRequest());

        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDagNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteLandRepository.findAll().size();
        // set the field null
        instituteLand.setDagNo(null);

        // Create the InstituteLand, which fails.

        restInstituteLandMockMvc.perform(post("/api/instituteLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLand)))
                .andExpect(status().isBadRequest());

        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountOfLandIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteLandRepository.findAll().size();
        // set the field null
        instituteLand.setAmountOfLand(null);

        // Create the InstituteLand, which fails.

        restInstituteLandMockMvc.perform(post("/api/instituteLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLand)))
                .andExpect(status().isBadRequest());

        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLandRegistrationLedgerNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteLandRepository.findAll().size();
        // set the field null
        instituteLand.setLandRegistrationLedgerNo(null);

        // Create the InstituteLand, which fails.

        restInstituteLandMockMvc.perform(post("/api/instituteLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLand)))
                .andExpect(status().isBadRequest());

        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLandRegistrationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteLandRepository.findAll().size();
        // set the field null
        instituteLand.setLandRegistrationDate(null);

        // Create the InstituteLand, which fails.

        restInstituteLandMockMvc.perform(post("/api/instituteLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLand)))
                .andExpect(status().isBadRequest());

        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastTaxPaymentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteLandRepository.findAll().size();
        // set the field null
        instituteLand.setLastTaxPaymentDate(null);

        // Create the InstituteLand, which fails.

        restInstituteLandMockMvc.perform(post("/api/instituteLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLand)))
                .andExpect(status().isBadRequest());

        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstituteLands() throws Exception {
        // Initialize the database
        instituteLandRepository.saveAndFlush(instituteLand);

        // Get all the instituteLands
        restInstituteLandMockMvc.perform(get("/api/instituteLands"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instituteLand.getId().intValue())))
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
    public void getInstituteLand() throws Exception {
        // Initialize the database
        instituteLandRepository.saveAndFlush(instituteLand);

        // Get the instituteLand
        restInstituteLandMockMvc.perform(get("/api/instituteLands/{id}", instituteLand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instituteLand.getId().intValue()))
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
    public void getNonExistingInstituteLand() throws Exception {
        // Get the instituteLand
        restInstituteLandMockMvc.perform(get("/api/instituteLands/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituteLand() throws Exception {
        // Initialize the database
        instituteLandRepository.saveAndFlush(instituteLand);

		int databaseSizeBeforeUpdate = instituteLandRepository.findAll().size();

        // Update the instituteLand
        instituteLand.setMouja(UPDATED_MOUJA);
        instituteLand.setJlNo(UPDATED_JL_NO);
        instituteLand.setLedgerNo(UPDATED_LEDGER_NO);
        instituteLand.setDagNo(UPDATED_DAG_NO);
        instituteLand.setAmountOfLand(UPDATED_AMOUNT_OF_LAND);
        instituteLand.setLandRegistrationLedgerNo(UPDATED_LAND_REGISTRATION_LEDGER_NO);
        instituteLand.setLandRegistrationDate(UPDATED_LAND_REGISTRATION_DATE);
        instituteLand.setLastTaxPaymentDate(UPDATED_LAST_TAX_PAYMENT_DATE);
        instituteLand.setBoundaryNorth(UPDATED_BOUNDARY_NORTH);
        instituteLand.setBoundarySouth(UPDATED_BOUNDARY_SOUTH);
        instituteLand.setBoundaryEast(UPDATED_BOUNDARY_EAST);
        instituteLand.setBoundaryWest(UPDATED_BOUNDARY_WEST);

        restInstituteLandMockMvc.perform(put("/api/instituteLands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLand)))
                .andExpect(status().isOk());

        // Validate the InstituteLand in the database
        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeUpdate);
        InstituteLand testInstituteLand = instituteLands.get(instituteLands.size() - 1);
        assertThat(testInstituteLand.getMouja()).isEqualTo(UPDATED_MOUJA);
        assertThat(testInstituteLand.getJlNo()).isEqualTo(UPDATED_JL_NO);
        assertThat(testInstituteLand.getLedgerNo()).isEqualTo(UPDATED_LEDGER_NO);
        assertThat(testInstituteLand.getDagNo()).isEqualTo(UPDATED_DAG_NO);
        assertThat(testInstituteLand.getAmountOfLand()).isEqualTo(UPDATED_AMOUNT_OF_LAND);
        assertThat(testInstituteLand.getLandRegistrationLedgerNo()).isEqualTo(UPDATED_LAND_REGISTRATION_LEDGER_NO);
        assertThat(testInstituteLand.getLandRegistrationDate()).isEqualTo(UPDATED_LAND_REGISTRATION_DATE);
        assertThat(testInstituteLand.getLastTaxPaymentDate()).isEqualTo(UPDATED_LAST_TAX_PAYMENT_DATE);
        assertThat(testInstituteLand.getBoundaryNorth()).isEqualTo(UPDATED_BOUNDARY_NORTH);
        assertThat(testInstituteLand.getBoundarySouth()).isEqualTo(UPDATED_BOUNDARY_SOUTH);
        assertThat(testInstituteLand.getBoundaryEast()).isEqualTo(UPDATED_BOUNDARY_EAST);
        assertThat(testInstituteLand.getBoundaryWest()).isEqualTo(UPDATED_BOUNDARY_WEST);
    }

    @Test
    @Transactional
    public void deleteInstituteLand() throws Exception {
        // Initialize the database
        instituteLandRepository.saveAndFlush(instituteLand);

		int databaseSizeBeforeDelete = instituteLandRepository.findAll().size();

        // Get the instituteLand
        restInstituteLandMockMvc.perform(delete("/api/instituteLands/{id}", instituteLand.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstituteLand> instituteLands = instituteLandRepository.findAll();
        assertThat(instituteLands).hasSize(databaseSizeBeforeDelete - 1);
    }
}
