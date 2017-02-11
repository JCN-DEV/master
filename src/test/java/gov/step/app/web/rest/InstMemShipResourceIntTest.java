package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstMemShip;
import gov.step.app.repository.InstMemShipRepository;
import gov.step.app.repository.search.InstMemShipSearchRepository;

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
 * Test class for the InstMemShipResource REST controller.
 *
 * @see InstMemShipResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstMemShipResourceIntTest {

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
    private InstMemShipRepository instMemShipRepository;

    @Inject
    private InstMemShipSearchRepository instMemShipSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstMemShipMockMvc;

    private InstMemShip instMemShip;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstMemShipResource instMemShipResource = new InstMemShipResource();
        ReflectionTestUtils.setField(instMemShipResource, "instMemShipRepository", instMemShipRepository);
        ReflectionTestUtils.setField(instMemShipResource, "instMemShipSearchRepository", instMemShipSearchRepository);
        this.restInstMemShipMockMvc = MockMvcBuilders.standaloneSetup(instMemShipResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instMemShip = new InstMemShip();
        instMemShip.setFullName(DEFAULT_FULL_NAME);
        instMemShip.setDob(DEFAULT_DOB);
        instMemShip.setGender(DEFAULT_GENDER);
        instMemShip.setAddress(DEFAULT_ADDRESS);
        instMemShip.setEmail(DEFAULT_EMAIL);
        instMemShip.setContact(DEFAULT_CONTACT);
        instMemShip.setDesignation(DEFAULT_DESIGNATION);
        instMemShip.setOrgName(DEFAULT_ORG_NAME);
        instMemShip.setOrgAdd(DEFAULT_ORG_ADD);
        instMemShip.setOrgContact(DEFAULT_ORG_CONTACT);
/*
        instMemShip.setDate(DEFAULT_DATE);
*/
    }

    @Test
    @Transactional
    public void createInstMemShip() throws Exception {
        int databaseSizeBeforeCreate = instMemShipRepository.findAll().size();

        // Create the InstMemShip

        restInstMemShipMockMvc.perform(post("/api/instMemShips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instMemShip)))
                .andExpect(status().isCreated());

        // Validate the InstMemShip in the database
        List<InstMemShip> instMemShips = instMemShipRepository.findAll();
        assertThat(instMemShips).hasSize(databaseSizeBeforeCreate + 1);
        InstMemShip testInstMemShip = instMemShips.get(instMemShips.size() - 1);
        assertThat(testInstMemShip.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testInstMemShip.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testInstMemShip.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testInstMemShip.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testInstMemShip.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInstMemShip.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testInstMemShip.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testInstMemShip.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testInstMemShip.getOrgAdd()).isEqualTo(DEFAULT_ORG_ADD);
        assertThat(testInstMemShip.getOrgContact()).isEqualTo(DEFAULT_ORG_CONTACT);
/*
        assertThat(testInstMemShip.getDate()).isEqualTo(DEFAULT_DATE);
*/
    }

    @Test
    @Transactional
    public void getAllInstMemShips() throws Exception {
        // Initialize the database
        instMemShipRepository.saveAndFlush(instMemShip);

        // Get all the instMemShips
        restInstMemShipMockMvc.perform(get("/api/instMemShips"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instMemShip.getId().intValue())))
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
    public void getInstMemShip() throws Exception {
        // Initialize the database
        instMemShipRepository.saveAndFlush(instMemShip);

        // Get the instMemShip
        restInstMemShipMockMvc.perform(get("/api/instMemShips/{id}", instMemShip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instMemShip.getId().intValue()))
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
    public void getNonExistingInstMemShip() throws Exception {
        // Get the instMemShip
        restInstMemShipMockMvc.perform(get("/api/instMemShips/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstMemShip() throws Exception {
        // Initialize the database
        instMemShipRepository.saveAndFlush(instMemShip);

		int databaseSizeBeforeUpdate = instMemShipRepository.findAll().size();

        // Update the instMemShip
        instMemShip.setFullName(UPDATED_FULL_NAME);
        instMemShip.setDob(UPDATED_DOB);
        instMemShip.setGender(UPDATED_GENDER);
        instMemShip.setAddress(UPDATED_ADDRESS);
        instMemShip.setEmail(UPDATED_EMAIL);
        instMemShip.setContact(UPDATED_CONTACT);
        instMemShip.setDesignation(UPDATED_DESIGNATION);
        instMemShip.setOrgName(UPDATED_ORG_NAME);
        instMemShip.setOrgAdd(UPDATED_ORG_ADD);
        instMemShip.setOrgContact(UPDATED_ORG_CONTACT);
/*
        instMemShip.setDate(UPDATED_DATE);
*/

        restInstMemShipMockMvc.perform(put("/api/instMemShips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instMemShip)))
                .andExpect(status().isOk());

        // Validate the InstMemShip in the database
        List<InstMemShip> instMemShips = instMemShipRepository.findAll();
        assertThat(instMemShips).hasSize(databaseSizeBeforeUpdate);
        InstMemShip testInstMemShip = instMemShips.get(instMemShips.size() - 1);
        assertThat(testInstMemShip.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testInstMemShip.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testInstMemShip.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testInstMemShip.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testInstMemShip.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstMemShip.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testInstMemShip.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testInstMemShip.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testInstMemShip.getOrgAdd()).isEqualTo(UPDATED_ORG_ADD);
        assertThat(testInstMemShip.getOrgContact()).isEqualTo(UPDATED_ORG_CONTACT);
/*
        assertThat(testInstMemShip.getDate()).isEqualTo(UPDATED_DATE);
*/
    }

    @Test
    @Transactional
    public void deleteInstMemShip() throws Exception {
        // Initialize the database
        instMemShipRepository.saveAndFlush(instMemShip);

		int databaseSizeBeforeDelete = instMemShipRepository.findAll().size();

        // Get the instMemShip
        restInstMemShipMockMvc.perform(delete("/api/instMemShips/{id}", instMemShip.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstMemShip> instMemShips = instMemShipRepository.findAll();
        assertThat(instMemShips).hasSize(databaseSizeBeforeDelete - 1);
    }
}
