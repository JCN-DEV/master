package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmplExperience;
import gov.step.app.repository.InstEmplExperienceRepository;
import gov.step.app.repository.search.InstEmplExperienceSearchRepository;

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
 * Test class for the InstEmplExperienceResource REST controller.
 *
 * @see InstEmplExperienceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmplExperienceResourceIntTest {

    private static final String DEFAULT_INSTITUTE_NAME = "AAAAA";
    private static final String UPDATED_INSTITUTE_NAME = "BBBBB";
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
    private InstEmplExperienceRepository instEmplExperienceRepository;

    @Inject
    private InstEmplExperienceSearchRepository instEmplExperienceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmplExperienceMockMvc;

    private InstEmplExperience instEmplExperience;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmplExperienceResource instEmplExperienceResource = new InstEmplExperienceResource();
        ReflectionTestUtils.setField(instEmplExperienceResource, "instEmplExperienceRepository", instEmplExperienceRepository);
        ReflectionTestUtils.setField(instEmplExperienceResource, "instEmplExperienceSearchRepository", instEmplExperienceSearchRepository);
        this.restInstEmplExperienceMockMvc = MockMvcBuilders.standaloneSetup(instEmplExperienceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmplExperience = new InstEmplExperience();
        instEmplExperience.setInstituteName(DEFAULT_INSTITUTE_NAME);
        instEmplExperience.setIndexNo(DEFAULT_INDEX_NO);
        instEmplExperience.setAddress(DEFAULT_ADDRESS);
        instEmplExperience.setDesignation(DEFAULT_DESIGNATION);
        instEmplExperience.setSubject(DEFAULT_SUBJECT);
        instEmplExperience.setSalaryCode(DEFAULT_SALARY_CODE);
        instEmplExperience.setJoiningDate(DEFAULT_JOINING_DATE);
        instEmplExperience.setMpoEnlistingDate(DEFAULT_MPO_ENLISTING_DATE);
        instEmplExperience.setResignDate(DEFAULT_RESIGN_DATE);
        instEmplExperience.setDateOfPaymentReceived(DEFAULT_DATE_OF_PAYMENT_RECEIVED);
        instEmplExperience.setStartDate(DEFAULT_START_DATE);
        instEmplExperience.setEndDate(DEFAULT_END_DATE);
        instEmplExperience.setVacationFrom(DEFAULT_VACATION_FROM);
        instEmplExperience.setVacationTo(DEFAULT_VACATION_TO);
        instEmplExperience.setTotalExpFrom(DEFAULT_TOTAL_EXP_FROM);
        instEmplExperience.setTotalExpTo(DEFAULT_TOTAL_EXP_TO);
        instEmplExperience.setCurrent(DEFAULT_CURRENT);
        instEmplExperience.setAttachment(DEFAULT_ATTACHMENT);
        instEmplExperience.setAttachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createInstEmplExperience() throws Exception {
        int databaseSizeBeforeCreate = instEmplExperienceRepository.findAll().size();

        // Create the InstEmplExperience

        restInstEmplExperienceMockMvc.perform(post("/api/instEmplExperiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplExperience)))
                .andExpect(status().isCreated());

        // Validate the InstEmplExperience in the database
        List<InstEmplExperience> instEmplExperiences = instEmplExperienceRepository.findAll();
        assertThat(instEmplExperiences).hasSize(databaseSizeBeforeCreate + 1);
        InstEmplExperience testInstEmplExperience = instEmplExperiences.get(instEmplExperiences.size() - 1);
        assertThat(testInstEmplExperience.getInstituteName()).isEqualTo(DEFAULT_INSTITUTE_NAME);
        assertThat(testInstEmplExperience.getIndexNo()).isEqualTo(DEFAULT_INDEX_NO);
        assertThat(testInstEmplExperience.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testInstEmplExperience.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testInstEmplExperience.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testInstEmplExperience.getSalaryCode()).isEqualTo(DEFAULT_SALARY_CODE);
        assertThat(testInstEmplExperience.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testInstEmplExperience.getMpoEnlistingDate()).isEqualTo(DEFAULT_MPO_ENLISTING_DATE);
        assertThat(testInstEmplExperience.getResignDate()).isEqualTo(DEFAULT_RESIGN_DATE);
        assertThat(testInstEmplExperience.getDateOfPaymentReceived()).isEqualTo(DEFAULT_DATE_OF_PAYMENT_RECEIVED);
        assertThat(testInstEmplExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testInstEmplExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testInstEmplExperience.getVacationFrom()).isEqualTo(DEFAULT_VACATION_FROM);
        assertThat(testInstEmplExperience.getVacationTo()).isEqualTo(DEFAULT_VACATION_TO);
        assertThat(testInstEmplExperience.getTotalExpFrom()).isEqualTo(DEFAULT_TOTAL_EXP_FROM);
        assertThat(testInstEmplExperience.getTotalExpTo()).isEqualTo(DEFAULT_TOTAL_EXP_TO);
        assertThat(testInstEmplExperience.getCurrent()).isEqualTo(DEFAULT_CURRENT);
        assertThat(testInstEmplExperience.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testInstEmplExperience.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllInstEmplExperiences() throws Exception {
        // Initialize the database
        instEmplExperienceRepository.saveAndFlush(instEmplExperience);

        // Get all the instEmplExperiences
        restInstEmplExperienceMockMvc.perform(get("/api/instEmplExperiences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmplExperience.getId().intValue())))
                .andExpect(jsonPath("$.[*].instituteName").value(hasItem(DEFAULT_INSTITUTE_NAME.toString())))
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
    public void getInstEmplExperience() throws Exception {
        // Initialize the database
        instEmplExperienceRepository.saveAndFlush(instEmplExperience);

        // Get the instEmplExperience
        restInstEmplExperienceMockMvc.perform(get("/api/instEmplExperiences/{id}", instEmplExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmplExperience.getId().intValue()))
            .andExpect(jsonPath("$.instituteName").value(DEFAULT_INSTITUTE_NAME.toString()))
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
    public void getNonExistingInstEmplExperience() throws Exception {
        // Get the instEmplExperience
        restInstEmplExperienceMockMvc.perform(get("/api/instEmplExperiences/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmplExperience() throws Exception {
        // Initialize the database
        instEmplExperienceRepository.saveAndFlush(instEmplExperience);

		int databaseSizeBeforeUpdate = instEmplExperienceRepository.findAll().size();

        // Update the instEmplExperience
        instEmplExperience.setInstituteName(UPDATED_INSTITUTE_NAME);
        instEmplExperience.setIndexNo(UPDATED_INDEX_NO);
        instEmplExperience.setAddress(UPDATED_ADDRESS);
        instEmplExperience.setDesignation(UPDATED_DESIGNATION);
        instEmplExperience.setSubject(UPDATED_SUBJECT);
        instEmplExperience.setSalaryCode(UPDATED_SALARY_CODE);
        instEmplExperience.setJoiningDate(UPDATED_JOINING_DATE);
        instEmplExperience.setMpoEnlistingDate(UPDATED_MPO_ENLISTING_DATE);
        instEmplExperience.setResignDate(UPDATED_RESIGN_DATE);
        instEmplExperience.setDateOfPaymentReceived(UPDATED_DATE_OF_PAYMENT_RECEIVED);
        instEmplExperience.setStartDate(UPDATED_START_DATE);
        instEmplExperience.setEndDate(UPDATED_END_DATE);
        instEmplExperience.setVacationFrom(UPDATED_VACATION_FROM);
        instEmplExperience.setVacationTo(UPDATED_VACATION_TO);
        instEmplExperience.setTotalExpFrom(UPDATED_TOTAL_EXP_FROM);
        instEmplExperience.setTotalExpTo(UPDATED_TOTAL_EXP_TO);
        instEmplExperience.setCurrent(UPDATED_CURRENT);
        instEmplExperience.setAttachment(UPDATED_ATTACHMENT);
        instEmplExperience.setAttachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);

        restInstEmplExperienceMockMvc.perform(put("/api/instEmplExperiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplExperience)))
                .andExpect(status().isOk());

        // Validate the InstEmplExperience in the database
        List<InstEmplExperience> instEmplExperiences = instEmplExperienceRepository.findAll();
        assertThat(instEmplExperiences).hasSize(databaseSizeBeforeUpdate);
        InstEmplExperience testInstEmplExperience = instEmplExperiences.get(instEmplExperiences.size() - 1);
        assertThat(testInstEmplExperience.getInstituteName()).isEqualTo(UPDATED_INSTITUTE_NAME);
        assertThat(testInstEmplExperience.getIndexNo()).isEqualTo(UPDATED_INDEX_NO);
        assertThat(testInstEmplExperience.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testInstEmplExperience.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testInstEmplExperience.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testInstEmplExperience.getSalaryCode()).isEqualTo(UPDATED_SALARY_CODE);
        assertThat(testInstEmplExperience.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testInstEmplExperience.getMpoEnlistingDate()).isEqualTo(UPDATED_MPO_ENLISTING_DATE);
        assertThat(testInstEmplExperience.getResignDate()).isEqualTo(UPDATED_RESIGN_DATE);
        assertThat(testInstEmplExperience.getDateOfPaymentReceived()).isEqualTo(UPDATED_DATE_OF_PAYMENT_RECEIVED);
        assertThat(testInstEmplExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testInstEmplExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testInstEmplExperience.getVacationFrom()).isEqualTo(UPDATED_VACATION_FROM);
        assertThat(testInstEmplExperience.getVacationTo()).isEqualTo(UPDATED_VACATION_TO);
        assertThat(testInstEmplExperience.getTotalExpFrom()).isEqualTo(UPDATED_TOTAL_EXP_FROM);
        assertThat(testInstEmplExperience.getTotalExpTo()).isEqualTo(UPDATED_TOTAL_EXP_TO);
        assertThat(testInstEmplExperience.getCurrent()).isEqualTo(UPDATED_CURRENT);
        assertThat(testInstEmplExperience.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testInstEmplExperience.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteInstEmplExperience() throws Exception {
        // Initialize the database
        instEmplExperienceRepository.saveAndFlush(instEmplExperience);

		int databaseSizeBeforeDelete = instEmplExperienceRepository.findAll().size();

        // Get the instEmplExperience
        restInstEmplExperienceMockMvc.perform(delete("/api/instEmplExperiences/{id}", instEmplExperience.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmplExperience> instEmplExperiences = instEmplExperienceRepository.findAll();
        assertThat(instEmplExperiences).hasSize(databaseSizeBeforeDelete - 1);
    }
}
