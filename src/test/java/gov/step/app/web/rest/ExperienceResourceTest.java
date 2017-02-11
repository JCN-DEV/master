package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Experience;
import gov.step.app.repository.ExperienceRepository;
import gov.step.app.repository.search.ExperienceSearchRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ExperienceResource REST controller.
 *
 * @see ExperienceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ExperienceResourceTest {

    private static final String DEFAULT_INDEX_NO = "AAAAA";
    private static final String UPDATED_INDEX_NO = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_SALARY_CODE = "AAAAA";
    private static final String UPDATED_SALARY_CODE = "BBBBB";

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MPO_ENLISTING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MPO_ENLISTING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RESIGN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RESIGN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_OF_PAYMENT_RECEIVED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_PAYMENT_RECEIVED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VACATION_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VACATION_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VACATION_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VACATION_TO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TOTAL_EXP_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TOTAL_EXP_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TOTAL_EXP_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TOTAL_EXP_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_CURRENT = false;
    private static final Boolean UPDATED_CURRENT = true;

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    @Inject
    private ExperienceRepository experienceRepository;

    @Inject
    private ExperienceSearchRepository experienceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restExperienceMockMvc;

    private Experience experience;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExperienceResource experienceResource = new ExperienceResource();
        ReflectionTestUtils.setField(experienceResource, "experienceRepository", experienceRepository);
        ReflectionTestUtils.setField(experienceResource, "experienceSearchRepository", experienceSearchRepository);
        this.restExperienceMockMvc = MockMvcBuilders.standaloneSetup(experienceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        experience = new Experience();
        experience.setIndexNo(DEFAULT_INDEX_NO);
        experience.setAddress(DEFAULT_ADDRESS);
        experience.setDesignation(DEFAULT_DESIGNATION);
        experience.setSubject(DEFAULT_SUBJECT);
        experience.setSalaryCode(DEFAULT_SALARY_CODE);
        experience.setJoiningDate(DEFAULT_JOINING_DATE);
        experience.setMpoEnlistingDate(DEFAULT_MPO_ENLISTING_DATE);
        experience.setResignDate(DEFAULT_RESIGN_DATE);
        experience.setDateOfPaymentReceived(DEFAULT_DATE_OF_PAYMENT_RECEIVED);
        experience.setStartDate(DEFAULT_START_DATE);
        experience.setEndDate(DEFAULT_END_DATE);
        experience.setVacationFrom(DEFAULT_VACATION_FROM);
        experience.setVacationTo(DEFAULT_VACATION_TO);
        experience.setTotalExpFrom(DEFAULT_TOTAL_EXP_FROM);
        experience.setTotalExpTo(DEFAULT_TOTAL_EXP_TO);
        experience.setCurrent(DEFAULT_CURRENT);
        experience.setAttachment(DEFAULT_ATTACHMENT);
        experience.setAttachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createExperience() throws Exception {
        int databaseSizeBeforeCreate = experienceRepository.findAll().size();

        // Create the Experience

        restExperienceMockMvc.perform(post("/api/experiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(experience)))
                .andExpect(status().isCreated());

        // Validate the Experience in the database
        List<Experience> experiences = experienceRepository.findAll();
        assertThat(experiences).hasSize(databaseSizeBeforeCreate + 1);
        Experience testExperience = experiences.get(experiences.size() - 1);
        assertThat(testExperience.getIndexNo()).isEqualTo(DEFAULT_INDEX_NO);
        assertThat(testExperience.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testExperience.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testExperience.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testExperience.getSalaryCode()).isEqualTo(DEFAULT_SALARY_CODE);
        assertThat(testExperience.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testExperience.getMpoEnlistingDate()).isEqualTo(DEFAULT_MPO_ENLISTING_DATE);
        assertThat(testExperience.getResignDate()).isEqualTo(DEFAULT_RESIGN_DATE);
        assertThat(testExperience.getDateOfPaymentReceived()).isEqualTo(DEFAULT_DATE_OF_PAYMENT_RECEIVED);
        assertThat(testExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExperience.getVacationFrom()).isEqualTo(DEFAULT_VACATION_FROM);
        assertThat(testExperience.getVacationTo()).isEqualTo(DEFAULT_VACATION_TO);
        assertThat(testExperience.getTotalExpFrom()).isEqualTo(DEFAULT_TOTAL_EXP_FROM);
        assertThat(testExperience.getTotalExpTo()).isEqualTo(DEFAULT_TOTAL_EXP_TO);
        assertThat(testExperience.getCurrent()).isEqualTo(DEFAULT_CURRENT);
        assertThat(testExperience.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testExperience.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllExperiences() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experiences
        restExperienceMockMvc.perform(get("/api/experiences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(experience.getId().intValue())))
                .andExpect(jsonPath("$.[*].indexNo").value(hasItem(DEFAULT_INDEX_NO.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].salaryCode").value(hasItem(DEFAULT_SALARY_CODE.toString())))
                .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
                .andExpect(jsonPath("$.[*].mpoEnlistingDate").value(hasItem(DEFAULT_MPO_ENLISTING_DATE.toString())))
                .andExpect(jsonPath("$.[*].resignDate").value(hasItem(DEFAULT_RESIGN_DATE.toString())))
                .andExpect(jsonPath("$.[*].dateOfPaymentReceived").value(hasItem(DEFAULT_DATE_OF_PAYMENT_RECEIVED.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].vacationFrom").value(hasItem(DEFAULT_VACATION_FROM.toString())))
                .andExpect(jsonPath("$.[*].vacationTo").value(hasItem(DEFAULT_VACATION_TO.toString())))
                .andExpect(jsonPath("$.[*].totalExpFrom").value(hasItem(DEFAULT_TOTAL_EXP_FROM.toString())))
                .andExpect(jsonPath("$.[*].totalExpTo").value(hasItem(DEFAULT_TOTAL_EXP_TO.toString())))
                .andExpect(jsonPath("$.[*].current").value(hasItem(DEFAULT_CURRENT.booleanValue())))
                .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))));
    }

    @Test
    @Transactional
    public void getExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get the experience
        restExperienceMockMvc.perform(get("/api/experiences/{id}", experience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(experience.getId().intValue()))
            .andExpect(jsonPath("$.indexNo").value(DEFAULT_INDEX_NO.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.salaryCode").value(DEFAULT_SALARY_CODE.toString()))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.mpoEnlistingDate").value(DEFAULT_MPO_ENLISTING_DATE.toString()))
            .andExpect(jsonPath("$.resignDate").value(DEFAULT_RESIGN_DATE.toString()))
            .andExpect(jsonPath("$.dateOfPaymentReceived").value(DEFAULT_DATE_OF_PAYMENT_RECEIVED.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.vacationFrom").value(DEFAULT_VACATION_FROM.toString()))
            .andExpect(jsonPath("$.vacationTo").value(DEFAULT_VACATION_TO.toString()))
            .andExpect(jsonPath("$.totalExpFrom").value(DEFAULT_TOTAL_EXP_FROM.toString()))
            .andExpect(jsonPath("$.totalExpTo").value(DEFAULT_TOTAL_EXP_TO.toString()))
            .andExpect(jsonPath("$.current").value(DEFAULT_CURRENT.booleanValue()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)));
    }

    @Test
    @Transactional
    public void getNonExistingExperience() throws Exception {
        // Get the experience
        restExperienceMockMvc.perform(get("/api/experiences/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

		int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Update the experience
        experience.setIndexNo(UPDATED_INDEX_NO);
        experience.setAddress(UPDATED_ADDRESS);
        experience.setDesignation(UPDATED_DESIGNATION);
        experience.setSubject(UPDATED_SUBJECT);
        experience.setSalaryCode(UPDATED_SALARY_CODE);
        experience.setJoiningDate(UPDATED_JOINING_DATE);
        experience.setMpoEnlistingDate(UPDATED_MPO_ENLISTING_DATE);
        experience.setResignDate(UPDATED_RESIGN_DATE);
        experience.setDateOfPaymentReceived(UPDATED_DATE_OF_PAYMENT_RECEIVED);
        experience.setStartDate(UPDATED_START_DATE);
        experience.setEndDate(UPDATED_END_DATE);
        experience.setVacationFrom(UPDATED_VACATION_FROM);
        experience.setVacationTo(UPDATED_VACATION_TO);
        experience.setTotalExpFrom(UPDATED_TOTAL_EXP_FROM);
        experience.setTotalExpTo(UPDATED_TOTAL_EXP_TO);
        experience.setCurrent(UPDATED_CURRENT);
        experience.setAttachment(UPDATED_ATTACHMENT);
        experience.setAttachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);

        restExperienceMockMvc.perform(put("/api/experiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(experience)))
                .andExpect(status().isOk());

        // Validate the Experience in the database
        List<Experience> experiences = experienceRepository.findAll();
        assertThat(experiences).hasSize(databaseSizeBeforeUpdate);
        Experience testExperience = experiences.get(experiences.size() - 1);
        assertThat(testExperience.getIndexNo()).isEqualTo(UPDATED_INDEX_NO);
        assertThat(testExperience.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testExperience.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testExperience.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testExperience.getSalaryCode()).isEqualTo(UPDATED_SALARY_CODE);
        assertThat(testExperience.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testExperience.getMpoEnlistingDate()).isEqualTo(UPDATED_MPO_ENLISTING_DATE);
        assertThat(testExperience.getResignDate()).isEqualTo(UPDATED_RESIGN_DATE);
        assertThat(testExperience.getDateOfPaymentReceived()).isEqualTo(UPDATED_DATE_OF_PAYMENT_RECEIVED);
        assertThat(testExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExperience.getVacationFrom()).isEqualTo(UPDATED_VACATION_FROM);
        assertThat(testExperience.getVacationTo()).isEqualTo(UPDATED_VACATION_TO);
        assertThat(testExperience.getTotalExpFrom()).isEqualTo(UPDATED_TOTAL_EXP_FROM);
        assertThat(testExperience.getTotalExpTo()).isEqualTo(UPDATED_TOTAL_EXP_TO);
        assertThat(testExperience.getCurrent()).isEqualTo(UPDATED_CURRENT);
        assertThat(testExperience.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testExperience.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

		int databaseSizeBeforeDelete = experienceRepository.findAll().size();

        // Get the experience
        restExperienceMockMvc.perform(delete("/api/experiences/{id}", experience.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Experience> experiences = experienceRepository.findAll();
        assertThat(experiences).hasSize(databaseSizeBeforeDelete - 1);
    }
}
