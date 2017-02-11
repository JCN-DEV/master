package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.RisNewVacancy;
import gov.step.app.repository.RisNewVacancyRepository;
import gov.step.app.repository.search.RisNewVacancySearchRepository;

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
 * Test class for the RisNewVacancyResource REST controller.
 *
 * @see RisNewVacancyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RisNewVacancyResourceTest {


    private static final Integer DEFAULT_VACANCY_NO = 1;
    private static final Integer UPDATED_VACANCY_NO = 2;
    private static final String DEFAULT_EDUCATIONAL_QUALIFICATION = "AAAAA";
    private static final String UPDATED_EDUCATIONAL_QUALIFICATION = "BBBBB";
    private static final String DEFAULT_OTHER_QUALIFICATION = "AAAAA";
    private static final String UPDATED_OTHER_QUALIFICATION = "BBBBB";
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_PUBLISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLISH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APPLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private RisNewVacancyRepository risNewVacancyRepository;

    @Inject
    private RisNewVacancySearchRepository risNewVacancySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRisNewVacancyMockMvc;

    private RisNewVacancy risNewVacancy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RisNewVacancyResource risNewVacancyResource = new RisNewVacancyResource();
        ReflectionTestUtils.setField(risNewVacancyResource, "risNewVacancyRepository", risNewVacancyRepository);
        ReflectionTestUtils.setField(risNewVacancyResource, "risNewVacancySearchRepository", risNewVacancySearchRepository);
        this.restRisNewVacancyMockMvc = MockMvcBuilders.standaloneSetup(risNewVacancyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        risNewVacancy = new RisNewVacancy();
        risNewVacancy.setVacancyNo(DEFAULT_VACANCY_NO);
        risNewVacancy.setEducationalQualification(DEFAULT_EDUCATIONAL_QUALIFICATION);
        risNewVacancy.setOtherQualification(DEFAULT_OTHER_QUALIFICATION);
        risNewVacancy.setRemarks(DEFAULT_REMARKS);
        risNewVacancy.setPublishDate(DEFAULT_PUBLISH_DATE);
        risNewVacancy.setApplicationDate(DEFAULT_APPLICATION_DATE);
        risNewVacancy.setAttachment(DEFAULT_ATTACHMENT);
        risNewVacancy.setAttachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        risNewVacancy.setCreatedDate(DEFAULT_CREATED_DATE);
        risNewVacancy.setUpdatedDate(DEFAULT_UPDATED_DATE);
        risNewVacancy.setCreatedBy(DEFAULT_CREATED_BY);
        risNewVacancy.setUpdatedBy(DEFAULT_UPDATED_BY);
        risNewVacancy.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRisNewVacancy() throws Exception {
        int databaseSizeBeforeCreate = risNewVacancyRepository.findAll().size();

        // Create the RisNewVacancy

        restRisNewVacancyMockMvc.perform(post("/api/risNewVacancys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewVacancy)))
                .andExpect(status().isCreated());

        // Validate the RisNewVacancy in the database
        List<RisNewVacancy> risNewVacancys = risNewVacancyRepository.findAll();
        assertThat(risNewVacancys).hasSize(databaseSizeBeforeCreate + 1);
        RisNewVacancy testRisNewVacancy = risNewVacancys.get(risNewVacancys.size() - 1);
        assertThat(testRisNewVacancy.getVacancyNo()).isEqualTo(DEFAULT_VACANCY_NO);
        assertThat(testRisNewVacancy.getEducationalQualification()).isEqualTo(DEFAULT_EDUCATIONAL_QUALIFICATION);
        assertThat(testRisNewVacancy.getOtherQualification()).isEqualTo(DEFAULT_OTHER_QUALIFICATION);
        assertThat(testRisNewVacancy.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testRisNewVacancy.getPublishDate()).isEqualTo(DEFAULT_PUBLISH_DATE);
        assertThat(testRisNewVacancy.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testRisNewVacancy.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testRisNewVacancy.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testRisNewVacancy.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRisNewVacancy.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testRisNewVacancy.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRisNewVacancy.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRisNewVacancy.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllRisNewVacancys() throws Exception {
        // Initialize the database
        risNewVacancyRepository.saveAndFlush(risNewVacancy);

        // Get all the risNewVacancys
        restRisNewVacancyMockMvc.perform(get("/api/risNewVacancys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(risNewVacancy.getId().intValue())))
                .andExpect(jsonPath("$.[*].vacancyNo").value(hasItem(DEFAULT_VACANCY_NO)))
                .andExpect(jsonPath("$.[*].educationalQualification").value(hasItem(DEFAULT_EDUCATIONAL_QUALIFICATION.toString())))
                .andExpect(jsonPath("$.[*].otherQualification").value(hasItem(DEFAULT_OTHER_QUALIFICATION.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].publishDate").value(hasItem(DEFAULT_PUBLISH_DATE.toString())))
                .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getRisNewVacancy() throws Exception {
        // Initialize the database
        risNewVacancyRepository.saveAndFlush(risNewVacancy);

        // Get the risNewVacancy
        restRisNewVacancyMockMvc.perform(get("/api/risNewVacancys/{id}", risNewVacancy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(risNewVacancy.getId().intValue()))
            .andExpect(jsonPath("$.vacancyNo").value(DEFAULT_VACANCY_NO))
            .andExpect(jsonPath("$.educationalQualification").value(DEFAULT_EDUCATIONAL_QUALIFICATION.toString()))
            .andExpect(jsonPath("$.otherQualification").value(DEFAULT_OTHER_QUALIFICATION.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.publishDate").value(DEFAULT_PUBLISH_DATE.toString()))
            .andExpect(jsonPath("$.applicationDate").value(DEFAULT_APPLICATION_DATE.toString()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingRisNewVacancy() throws Exception {
        // Get the risNewVacancy
        restRisNewVacancyMockMvc.perform(get("/api/risNewVacancys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRisNewVacancy() throws Exception {
        // Initialize the database
        risNewVacancyRepository.saveAndFlush(risNewVacancy);

		int databaseSizeBeforeUpdate = risNewVacancyRepository.findAll().size();

        // Update the risNewVacancy
        risNewVacancy.setVacancyNo(UPDATED_VACANCY_NO);
        risNewVacancy.setEducationalQualification(UPDATED_EDUCATIONAL_QUALIFICATION);
        risNewVacancy.setOtherQualification(UPDATED_OTHER_QUALIFICATION);
        risNewVacancy.setRemarks(UPDATED_REMARKS);
        risNewVacancy.setPublishDate(UPDATED_PUBLISH_DATE);
        risNewVacancy.setApplicationDate(UPDATED_APPLICATION_DATE);
        risNewVacancy.setAttachment(UPDATED_ATTACHMENT);
        risNewVacancy.setAttachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);
        risNewVacancy.setCreatedDate(UPDATED_CREATED_DATE);
        risNewVacancy.setUpdatedDate(UPDATED_UPDATED_DATE);
        risNewVacancy.setCreatedBy(UPDATED_CREATED_BY);
        risNewVacancy.setUpdatedBy(UPDATED_UPDATED_BY);
        risNewVacancy.setStatus(UPDATED_STATUS);

        restRisNewVacancyMockMvc.perform(put("/api/risNewVacancys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewVacancy)))
                .andExpect(status().isOk());

        // Validate the RisNewVacancy in the database
        List<RisNewVacancy> risNewVacancys = risNewVacancyRepository.findAll();
        assertThat(risNewVacancys).hasSize(databaseSizeBeforeUpdate);
        RisNewVacancy testRisNewVacancy = risNewVacancys.get(risNewVacancys.size() - 1);
        assertThat(testRisNewVacancy.getVacancyNo()).isEqualTo(UPDATED_VACANCY_NO);
        assertThat(testRisNewVacancy.getEducationalQualification()).isEqualTo(UPDATED_EDUCATIONAL_QUALIFICATION);
        assertThat(testRisNewVacancy.getOtherQualification()).isEqualTo(UPDATED_OTHER_QUALIFICATION);
        assertThat(testRisNewVacancy.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testRisNewVacancy.getPublishDate()).isEqualTo(UPDATED_PUBLISH_DATE);
        assertThat(testRisNewVacancy.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testRisNewVacancy.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testRisNewVacancy.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testRisNewVacancy.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRisNewVacancy.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testRisNewVacancy.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRisNewVacancy.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRisNewVacancy.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteRisNewVacancy() throws Exception {
        // Initialize the database
        risNewVacancyRepository.saveAndFlush(risNewVacancy);

		int databaseSizeBeforeDelete = risNewVacancyRepository.findAll().size();

        // Get the risNewVacancy
        restRisNewVacancyMockMvc.perform(delete("/api/risNewVacancys/{id}", risNewVacancy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RisNewVacancy> risNewVacancys = risNewVacancyRepository.findAll();
        assertThat(risNewVacancys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
