package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsAppRetirmntNmine;
import gov.step.app.repository.PgmsAppRetirmntNmineRepository;
import gov.step.app.repository.search.PgmsAppRetirmntNmineSearchRepository;

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
 * Test class for the PgmsAppRetirmntNmineResource REST controller.
 *
 * @see PgmsAppRetirmntNmineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsAppRetirmntNmineResourceTest {


    private static final Long DEFAULT_APP_RETIRMNT_PEN_ID = 1L;
    private static final Long UPDATED_APP_RETIRMNT_PEN_ID = 2L;

    private static final Boolean DEFAULT_NOMINEE_STATUS = false;
    private static final Boolean UPDATED_NOMINEE_STATUS = true;

    private static final Long DEFAULT_NOMINEE_NAME = 1L;
    private static final Long UPDATED_NOMINEE_NAME = 2L;
    private static final String DEFAULT_GENDER = "AAAAA";
    private static final String UPDATED_GENDER = "BBBBB";
    private static final String DEFAULT_RELATION = "AAAAA";
    private static final String UPDATED_RELATION = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_PRESENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PRESENT_ADDRESS = "BBBBB";
    private static final String DEFAULT_NID = "AAAAA";
    private static final String UPDATED_NID = "BBBBB";
    private static final String DEFAULT_OCCUPATION = "AAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_MARITAL_STATUS = "AAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBB";

    private static final Long DEFAULT_MOBILE_NO = 1L;
    private static final Long UPDATED_MOBILE_NO = 2L;

    private static final Long DEFAULT_GET_PENSION = 1L;
    private static final Long UPDATED_GET_PENSION = 2L;

    private static final Boolean DEFAULT_HR_NOMINEE_INFO = false;
    private static final Boolean UPDATED_HR_NOMINEE_INFO = true;

    @Inject
    private PgmsAppRetirmntNmineRepository pgmsAppRetirmntNmineRepository;

    @Inject
    private PgmsAppRetirmntNmineSearchRepository pgmsAppRetirmntNmineSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsAppRetirmntNmineMockMvc;

    private PgmsAppRetirmntNmine pgmsAppRetirmntNmine;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsAppRetirmntNmineResource pgmsAppRetirmntNmineResource = new PgmsAppRetirmntNmineResource();
        ReflectionTestUtils.setField(pgmsAppRetirmntNmineResource, "pgmsAppRetirmntNmineRepository", pgmsAppRetirmntNmineRepository);
        ReflectionTestUtils.setField(pgmsAppRetirmntNmineResource, "pgmsAppRetirmntNmineSearchRepository", pgmsAppRetirmntNmineSearchRepository);
        this.restPgmsAppRetirmntNmineMockMvc = MockMvcBuilders.standaloneSetup(pgmsAppRetirmntNmineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsAppRetirmntNmine = new PgmsAppRetirmntNmine();
        pgmsAppRetirmntNmine.setAppRetirmntPenId(DEFAULT_APP_RETIRMNT_PEN_ID);
        pgmsAppRetirmntNmine.setNomineeStatus(DEFAULT_NOMINEE_STATUS);
        pgmsAppRetirmntNmine.setGender(DEFAULT_GENDER);
        pgmsAppRetirmntNmine.setRelation(DEFAULT_RELATION);
        pgmsAppRetirmntNmine.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
        pgmsAppRetirmntNmine.setPresentAddress(DEFAULT_PRESENT_ADDRESS);
        pgmsAppRetirmntNmine.setNid(DEFAULT_NID);
        pgmsAppRetirmntNmine.setOccupation(DEFAULT_OCCUPATION);
        pgmsAppRetirmntNmine.setDesignation(DEFAULT_DESIGNATION);
        pgmsAppRetirmntNmine.setMaritalStatus(DEFAULT_MARITAL_STATUS);
        pgmsAppRetirmntNmine.setMobileNo(DEFAULT_MOBILE_NO);
        pgmsAppRetirmntNmine.setGetPension(DEFAULT_GET_PENSION);
        pgmsAppRetirmntNmine.setHrNomineeInfo(DEFAULT_HR_NOMINEE_INFO);
    }

    @Test
    @Transactional
    public void createPgmsAppRetirmntNmine() throws Exception {
        int databaseSizeBeforeCreate = pgmsAppRetirmntNmineRepository.findAll().size();

        // Create the PgmsAppRetirmntNmine

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isCreated());

        // Validate the PgmsAppRetirmntNmine in the database
        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeCreate + 1);
        PgmsAppRetirmntNmine testPgmsAppRetirmntNmine = pgmsAppRetirmntNmines.get(pgmsAppRetirmntNmines.size() - 1);
        assertThat(testPgmsAppRetirmntNmine.getAppRetirmntPenId()).isEqualTo(DEFAULT_APP_RETIRMNT_PEN_ID);
        assertThat(testPgmsAppRetirmntNmine.getNomineeStatus()).isEqualTo(DEFAULT_NOMINEE_STATUS);
        assertThat(testPgmsAppRetirmntNmine.getNomineeName()).isEqualTo(DEFAULT_NOMINEE_NAME);
        assertThat(testPgmsAppRetirmntNmine.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPgmsAppRetirmntNmine.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testPgmsAppRetirmntNmine.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPgmsAppRetirmntNmine.getPresentAddress()).isEqualTo(DEFAULT_PRESENT_ADDRESS);
        assertThat(testPgmsAppRetirmntNmine.getNid()).isEqualTo(DEFAULT_NID);
        assertThat(testPgmsAppRetirmntNmine.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testPgmsAppRetirmntNmine.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testPgmsAppRetirmntNmine.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testPgmsAppRetirmntNmine.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testPgmsAppRetirmntNmine.getGetPension()).isEqualTo(DEFAULT_GET_PENSION);
        assertThat(testPgmsAppRetirmntNmine.getHrNomineeInfo()).isEqualTo(DEFAULT_HR_NOMINEE_INFO);
    }

    @Test
    @Transactional
    public void checkAppRetirmntPenIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setAppRetirmntPenId(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomineeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setNomineeName(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setGender(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRelationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setRelation(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setDateOfBirth(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresentAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setPresentAddress(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNidIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setNid(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOccupationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setOccupation(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setDesignation(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaritalStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setMaritalStatus(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setMobileNo(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGetPensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntNmineRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntNmine.setGetPension(null);

        // Create the PgmsAppRetirmntNmine, which fails.

        restPgmsAppRetirmntNmineMockMvc.perform(post("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsAppRetirmntNmines() throws Exception {
        // Initialize the database
        pgmsAppRetirmntNmineRepository.saveAndFlush(pgmsAppRetirmntNmine);

        // Get all the pgmsAppRetirmntNmines
        restPgmsAppRetirmntNmineMockMvc.perform(get("/api/pgmsAppRetirmntNmines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsAppRetirmntNmine.getId().intValue())))
                .andExpect(jsonPath("$.[*].appRetirmntPenId").value(hasItem(DEFAULT_APP_RETIRMNT_PEN_ID.intValue())))
                .andExpect(jsonPath("$.[*].nomineeStatus").value(hasItem(DEFAULT_NOMINEE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].nomineeName").value(hasItem(DEFAULT_NOMINEE_NAME.intValue())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
                .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].nid").value(hasItem(DEFAULT_NID.toString())))
                .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
                .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.intValue())))
                .andExpect(jsonPath("$.[*].getPension").value(hasItem(DEFAULT_GET_PENSION.intValue())))
                .andExpect(jsonPath("$.[*].hrNomineeInfo").value(hasItem(DEFAULT_HR_NOMINEE_INFO.booleanValue())));
    }

    @Test
    @Transactional
    public void getPgmsAppRetirmntNmine() throws Exception {
        // Initialize the database
        pgmsAppRetirmntNmineRepository.saveAndFlush(pgmsAppRetirmntNmine);

        // Get the pgmsAppRetirmntNmine
        restPgmsAppRetirmntNmineMockMvc.perform(get("/api/pgmsAppRetirmntNmines/{id}", pgmsAppRetirmntNmine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsAppRetirmntNmine.getId().intValue()))
            .andExpect(jsonPath("$.appRetirmntPenId").value(DEFAULT_APP_RETIRMNT_PEN_ID.intValue()))
            .andExpect(jsonPath("$.nomineeStatus").value(DEFAULT_NOMINEE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.nomineeName").value(DEFAULT_NOMINEE_NAME.intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.presentAddress").value(DEFAULT_PRESENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.nid").value(DEFAULT_NID.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.intValue()))
            .andExpect(jsonPath("$.getPension").value(DEFAULT_GET_PENSION.intValue()))
            .andExpect(jsonPath("$.hrNomineeInfo").value(DEFAULT_HR_NOMINEE_INFO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsAppRetirmntNmine() throws Exception {
        // Get the pgmsAppRetirmntNmine
        restPgmsAppRetirmntNmineMockMvc.perform(get("/api/pgmsAppRetirmntNmines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsAppRetirmntNmine() throws Exception {
        // Initialize the database
        pgmsAppRetirmntNmineRepository.saveAndFlush(pgmsAppRetirmntNmine);

		int databaseSizeBeforeUpdate = pgmsAppRetirmntNmineRepository.findAll().size();

        // Update the pgmsAppRetirmntNmine
        pgmsAppRetirmntNmine.setAppRetirmntPenId(UPDATED_APP_RETIRMNT_PEN_ID);
        pgmsAppRetirmntNmine.setNomineeStatus(UPDATED_NOMINEE_STATUS);
        pgmsAppRetirmntNmine.setGender(UPDATED_GENDER);
        pgmsAppRetirmntNmine.setRelation(UPDATED_RELATION);
        pgmsAppRetirmntNmine.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        pgmsAppRetirmntNmine.setPresentAddress(UPDATED_PRESENT_ADDRESS);
        pgmsAppRetirmntNmine.setNid(UPDATED_NID);
        pgmsAppRetirmntNmine.setOccupation(UPDATED_OCCUPATION);
        pgmsAppRetirmntNmine.setDesignation(UPDATED_DESIGNATION);
        pgmsAppRetirmntNmine.setMaritalStatus(UPDATED_MARITAL_STATUS);
        pgmsAppRetirmntNmine.setMobileNo(UPDATED_MOBILE_NO);
        pgmsAppRetirmntNmine.setGetPension(UPDATED_GET_PENSION);
        pgmsAppRetirmntNmine.setHrNomineeInfo(UPDATED_HR_NOMINEE_INFO);

        restPgmsAppRetirmntNmineMockMvc.perform(put("/api/pgmsAppRetirmntNmines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntNmine)))
                .andExpect(status().isOk());

        // Validate the PgmsAppRetirmntNmine in the database
        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeUpdate);
        PgmsAppRetirmntNmine testPgmsAppRetirmntNmine = pgmsAppRetirmntNmines.get(pgmsAppRetirmntNmines.size() - 1);
        assertThat(testPgmsAppRetirmntNmine.getAppRetirmntPenId()).isEqualTo(UPDATED_APP_RETIRMNT_PEN_ID);
        assertThat(testPgmsAppRetirmntNmine.getNomineeStatus()).isEqualTo(UPDATED_NOMINEE_STATUS);
        assertThat(testPgmsAppRetirmntNmine.getNomineeName()).isEqualTo(UPDATED_NOMINEE_NAME);
        assertThat(testPgmsAppRetirmntNmine.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPgmsAppRetirmntNmine.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testPgmsAppRetirmntNmine.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPgmsAppRetirmntNmine.getPresentAddress()).isEqualTo(UPDATED_PRESENT_ADDRESS);
        assertThat(testPgmsAppRetirmntNmine.getNid()).isEqualTo(UPDATED_NID);
        assertThat(testPgmsAppRetirmntNmine.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testPgmsAppRetirmntNmine.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testPgmsAppRetirmntNmine.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testPgmsAppRetirmntNmine.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testPgmsAppRetirmntNmine.getGetPension()).isEqualTo(UPDATED_GET_PENSION);
        assertThat(testPgmsAppRetirmntNmine.getHrNomineeInfo()).isEqualTo(UPDATED_HR_NOMINEE_INFO);
    }

    @Test
    @Transactional
    public void deletePgmsAppRetirmntNmine() throws Exception {
        // Initialize the database
        pgmsAppRetirmntNmineRepository.saveAndFlush(pgmsAppRetirmntNmine);

		int databaseSizeBeforeDelete = pgmsAppRetirmntNmineRepository.findAll().size();

        // Get the pgmsAppRetirmntNmine
        restPgmsAppRetirmntNmineMockMvc.perform(delete("/api/pgmsAppRetirmntNmines/{id}", pgmsAppRetirmntNmine.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsAppRetirmntNmine> pgmsAppRetirmntNmines = pgmsAppRetirmntNmineRepository.findAll();
        assertThat(pgmsAppRetirmntNmines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
