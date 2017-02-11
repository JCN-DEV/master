package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstMemShipTemp;
import gov.step.app.repository.InstMemShipTempRepository;
import gov.step.app.repository.search.InstMemShipTempSearchRepository;

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
 * Test class for the InstMemShipTempResource REST controller.
 *
 * @see InstMemShipTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstMemShipTempResourceTest {

    private static final String DEFAULT_FULL_NAME = "AAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_GENDER = "AAAAA";
    private static final String UPDATED_GENDER = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_CONTACT = "AAAAA";
    private static final String UPDATED_CONTACT = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_ORG_NAME = "AAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBB";
    private static final String DEFAULT_ORG_ADD = "AAAAA";
    private static final String UPDATED_ORG_ADD = "BBBBB";
    private static final String DEFAULT_ORG_CONTACT = "AAAAA";
    private static final String UPDATED_ORG_CONTACT = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private InstMemShipTempRepository instMemShipTempRepository;

    @Inject
    private InstMemShipTempSearchRepository instMemShipTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstMemShipTempMockMvc;

    private InstMemShipTemp instMemShipTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstMemShipTempResource instMemShipTempResource = new InstMemShipTempResource();
        ReflectionTestUtils.setField(instMemShipTempResource, "instMemShipTempRepository", instMemShipTempRepository);
        ReflectionTestUtils.setField(instMemShipTempResource, "instMemShipTempSearchRepository", instMemShipTempSearchRepository);
        this.restInstMemShipTempMockMvc = MockMvcBuilders.standaloneSetup(instMemShipTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instMemShipTemp = new InstMemShipTemp();
        instMemShipTemp.setFullName(DEFAULT_FULL_NAME);
        instMemShipTemp.setDob(DEFAULT_DOB);
        instMemShipTemp.setGender(DEFAULT_GENDER);
        instMemShipTemp.setAddress(DEFAULT_ADDRESS);
        instMemShipTemp.setEmail(DEFAULT_EMAIL);
        instMemShipTemp.setContact(DEFAULT_CONTACT);
        instMemShipTemp.setDesignation(DEFAULT_DESIGNATION);
        instMemShipTemp.setOrgName(DEFAULT_ORG_NAME);
        instMemShipTemp.setOrgAdd(DEFAULT_ORG_ADD);
        instMemShipTemp.setOrgContact(DEFAULT_ORG_CONTACT);
        instMemShipTemp.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createInstMemShipTemp() throws Exception {
        int databaseSizeBeforeCreate = instMemShipTempRepository.findAll().size();

        // Create the InstMemShipTemp

        restInstMemShipTempMockMvc.perform(post("/api/instMemShipTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instMemShipTemp)))
                .andExpect(status().isCreated());

        // Validate the InstMemShipTemp in the database
        List<InstMemShipTemp> instMemShipTemps = instMemShipTempRepository.findAll();
        assertThat(instMemShipTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstMemShipTemp testInstMemShipTemp = instMemShipTemps.get(instMemShipTemps.size() - 1);
        assertThat(testInstMemShipTemp.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testInstMemShipTemp.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testInstMemShipTemp.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testInstMemShipTemp.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testInstMemShipTemp.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInstMemShipTemp.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testInstMemShipTemp.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testInstMemShipTemp.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testInstMemShipTemp.getOrgAdd()).isEqualTo(DEFAULT_ORG_ADD);
        assertThat(testInstMemShipTemp.getOrgContact()).isEqualTo(DEFAULT_ORG_CONTACT);
        assertThat(testInstMemShipTemp.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllInstMemShipTemps() throws Exception {
        // Initialize the database
        instMemShipTempRepository.saveAndFlush(instMemShipTemp);

        // Get all the instMemShipTemps
        restInstMemShipTempMockMvc.perform(get("/api/instMemShipTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instMemShipTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME.toString())))
                .andExpect(jsonPath("$.[*].orgAdd").value(hasItem(DEFAULT_ORG_ADD.toString())))
                .andExpect(jsonPath("$.[*].orgContact").value(hasItem(DEFAULT_ORG_CONTACT.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInstMemShipTemp() throws Exception {
        // Initialize the database
        instMemShipTempRepository.saveAndFlush(instMemShipTemp);

        // Get the instMemShipTemp
        restInstMemShipTempMockMvc.perform(get("/api/instMemShipTemps/{id}", instMemShipTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instMemShipTemp.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME.toString()))
            .andExpect(jsonPath("$.orgAdd").value(DEFAULT_ORG_ADD.toString()))
            .andExpect(jsonPath("$.orgContact").value(DEFAULT_ORG_CONTACT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstMemShipTemp() throws Exception {
        // Get the instMemShipTemp
        restInstMemShipTempMockMvc.perform(get("/api/instMemShipTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstMemShipTemp() throws Exception {
        // Initialize the database
        instMemShipTempRepository.saveAndFlush(instMemShipTemp);

		int databaseSizeBeforeUpdate = instMemShipTempRepository.findAll().size();

        // Update the instMemShipTemp
        instMemShipTemp.setFullName(UPDATED_FULL_NAME);
        instMemShipTemp.setDob(UPDATED_DOB);
        instMemShipTemp.setGender(UPDATED_GENDER);
        instMemShipTemp.setAddress(UPDATED_ADDRESS);
        instMemShipTemp.setEmail(UPDATED_EMAIL);
        instMemShipTemp.setContact(UPDATED_CONTACT);
        instMemShipTemp.setDesignation(UPDATED_DESIGNATION);
        instMemShipTemp.setOrgName(UPDATED_ORG_NAME);
        instMemShipTemp.setOrgAdd(UPDATED_ORG_ADD);
        instMemShipTemp.setOrgContact(UPDATED_ORG_CONTACT);
        instMemShipTemp.setDate(UPDATED_DATE);

        restInstMemShipTempMockMvc.perform(put("/api/instMemShipTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instMemShipTemp)))
                .andExpect(status().isOk());

        // Validate the InstMemShipTemp in the database
        List<InstMemShipTemp> instMemShipTemps = instMemShipTempRepository.findAll();
        assertThat(instMemShipTemps).hasSize(databaseSizeBeforeUpdate);
        InstMemShipTemp testInstMemShipTemp = instMemShipTemps.get(instMemShipTemps.size() - 1);
        assertThat(testInstMemShipTemp.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testInstMemShipTemp.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testInstMemShipTemp.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testInstMemShipTemp.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testInstMemShipTemp.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstMemShipTemp.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testInstMemShipTemp.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testInstMemShipTemp.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testInstMemShipTemp.getOrgAdd()).isEqualTo(UPDATED_ORG_ADD);
        assertThat(testInstMemShipTemp.getOrgContact()).isEqualTo(UPDATED_ORG_CONTACT);
        assertThat(testInstMemShipTemp.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteInstMemShipTemp() throws Exception {
        // Initialize the database
        instMemShipTempRepository.saveAndFlush(instMemShipTemp);

		int databaseSizeBeforeDelete = instMemShipTempRepository.findAll().size();

        // Get the instMemShipTemp
        restInstMemShipTempMockMvc.perform(delete("/api/instMemShipTemps/{id}", instMemShipTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstMemShipTemp> instMemShipTemps = instMemShipTempRepository.findAll();
        assertThat(instMemShipTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
