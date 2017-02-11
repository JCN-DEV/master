package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.LecturerSeniority;
import gov.step.app.repository.LecturerSeniorityRepository;
import gov.step.app.repository.search.LecturerSenioritySearchRepository;

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
 * Test class for the LecturerSeniorityResource REST controller.
 *
 * @see LecturerSeniorityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LecturerSeniorityResourceTest {


    private static final Integer DEFAULT_SERIAL = 1;
    private static final Integer UPDATED_SERIAL = 2;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";

    private static final LocalDate DEFAULT_FIRST_MPOENLISTING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_MPOENLISTING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_JOINING_DATE_AS_LECTURER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE_AS_LECTURER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    @Inject
    private LecturerSeniorityRepository lecturerSeniorityRepository;

    @Inject
    private LecturerSenioritySearchRepository lecturerSenioritySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLecturerSeniorityMockMvc;

    private LecturerSeniority lecturerSeniority;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LecturerSeniorityResource lecturerSeniorityResource = new LecturerSeniorityResource();
        ReflectionTestUtils.setField(lecturerSeniorityResource, "lecturerSeniorityRepository", lecturerSeniorityRepository);
        ReflectionTestUtils.setField(lecturerSeniorityResource, "lecturerSenioritySearchRepository", lecturerSenioritySearchRepository);
        this.restLecturerSeniorityMockMvc = MockMvcBuilders.standaloneSetup(lecturerSeniorityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lecturerSeniority = new LecturerSeniority();
        lecturerSeniority.setSerial(DEFAULT_SERIAL);
        lecturerSeniority.setName(DEFAULT_NAME);
        lecturerSeniority.setSubject(DEFAULT_SUBJECT);
        lecturerSeniority.setFirstMPOEnlistingDate(DEFAULT_FIRST_MPOENLISTING_DATE);
        lecturerSeniority.setJoiningDateAsLecturer(DEFAULT_JOINING_DATE_AS_LECTURER);
        lecturerSeniority.setDob(DEFAULT_DOB);
        lecturerSeniority.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createLecturerSeniority() throws Exception {
        int databaseSizeBeforeCreate = lecturerSeniorityRepository.findAll().size();

        // Create the LecturerSeniority

        restLecturerSeniorityMockMvc.perform(post("/api/lecturerSenioritys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lecturerSeniority)))
                .andExpect(status().isCreated());

        // Validate the LecturerSeniority in the database
        List<LecturerSeniority> lecturerSenioritys = lecturerSeniorityRepository.findAll();
        assertThat(lecturerSenioritys).hasSize(databaseSizeBeforeCreate + 1);
        LecturerSeniority testLecturerSeniority = lecturerSenioritys.get(lecturerSenioritys.size() - 1);
        assertThat(testLecturerSeniority.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testLecturerSeniority.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLecturerSeniority.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testLecturerSeniority.getFirstMPOEnlistingDate()).isEqualTo(DEFAULT_FIRST_MPOENLISTING_DATE);
        assertThat(testLecturerSeniority.getJoiningDateAsLecturer()).isEqualTo(DEFAULT_JOINING_DATE_AS_LECTURER);
        assertThat(testLecturerSeniority.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testLecturerSeniority.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturerSeniorityRepository.findAll().size();
        // set the field null
        lecturerSeniority.setName(null);

        // Create the LecturerSeniority, which fails.

        restLecturerSeniorityMockMvc.perform(post("/api/lecturerSenioritys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lecturerSeniority)))
                .andExpect(status().isBadRequest());

        List<LecturerSeniority> lecturerSenioritys = lecturerSeniorityRepository.findAll();
        assertThat(lecturerSenioritys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLecturerSenioritys() throws Exception {
        // Initialize the database
        lecturerSeniorityRepository.saveAndFlush(lecturerSeniority);

        // Get all the lecturerSenioritys
        restLecturerSeniorityMockMvc.perform(get("/api/lecturerSenioritys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lecturerSeniority.getId().intValue())))
                .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].firstMPOEnlistingDate").value(hasItem(DEFAULT_FIRST_MPOENLISTING_DATE.toString())))
                .andExpect(jsonPath("$.[*].joiningDateAsLecturer").value(hasItem(DEFAULT_JOINING_DATE_AS_LECTURER.toString())))
                .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getLecturerSeniority() throws Exception {
        // Initialize the database
        lecturerSeniorityRepository.saveAndFlush(lecturerSeniority);

        // Get the lecturerSeniority
        restLecturerSeniorityMockMvc.perform(get("/api/lecturerSenioritys/{id}", lecturerSeniority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lecturerSeniority.getId().intValue()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.firstMPOEnlistingDate").value(DEFAULT_FIRST_MPOENLISTING_DATE.toString()))
            .andExpect(jsonPath("$.joiningDateAsLecturer").value(DEFAULT_JOINING_DATE_AS_LECTURER.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLecturerSeniority() throws Exception {
        // Get the lecturerSeniority
        restLecturerSeniorityMockMvc.perform(get("/api/lecturerSenioritys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLecturerSeniority() throws Exception {
        // Initialize the database
        lecturerSeniorityRepository.saveAndFlush(lecturerSeniority);

		int databaseSizeBeforeUpdate = lecturerSeniorityRepository.findAll().size();

        // Update the lecturerSeniority
        lecturerSeniority.setSerial(UPDATED_SERIAL);
        lecturerSeniority.setName(UPDATED_NAME);
        lecturerSeniority.setSubject(UPDATED_SUBJECT);
        lecturerSeniority.setFirstMPOEnlistingDate(UPDATED_FIRST_MPOENLISTING_DATE);
        lecturerSeniority.setJoiningDateAsLecturer(UPDATED_JOINING_DATE_AS_LECTURER);
        lecturerSeniority.setDob(UPDATED_DOB);
        lecturerSeniority.setRemarks(UPDATED_REMARKS);

        restLecturerSeniorityMockMvc.perform(put("/api/lecturerSenioritys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lecturerSeniority)))
                .andExpect(status().isOk());

        // Validate the LecturerSeniority in the database
        List<LecturerSeniority> lecturerSenioritys = lecturerSeniorityRepository.findAll();
        assertThat(lecturerSenioritys).hasSize(databaseSizeBeforeUpdate);
        LecturerSeniority testLecturerSeniority = lecturerSenioritys.get(lecturerSenioritys.size() - 1);
        assertThat(testLecturerSeniority.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testLecturerSeniority.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLecturerSeniority.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testLecturerSeniority.getFirstMPOEnlistingDate()).isEqualTo(UPDATED_FIRST_MPOENLISTING_DATE);
        assertThat(testLecturerSeniority.getJoiningDateAsLecturer()).isEqualTo(UPDATED_JOINING_DATE_AS_LECTURER);
        assertThat(testLecturerSeniority.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testLecturerSeniority.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteLecturerSeniority() throws Exception {
        // Initialize the database
        lecturerSeniorityRepository.saveAndFlush(lecturerSeniority);

		int databaseSizeBeforeDelete = lecturerSeniorityRepository.findAll().size();

        // Get the lecturerSeniority
        restLecturerSeniorityMockMvc.perform(delete("/api/lecturerSenioritys/{id}", lecturerSeniority.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LecturerSeniority> lecturerSenioritys = lecturerSeniorityRepository.findAll();
        assertThat(lecturerSenioritys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
