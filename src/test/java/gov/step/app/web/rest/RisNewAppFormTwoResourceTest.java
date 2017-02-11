/*
package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.RisNewAppFormTwo;
import gov.step.app.repository.RisNewAppFormTwoRepository;
import gov.step.app.repository.search.RisNewAppFormTwoSearchRepository;

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


*/
/**
 * Test class for the RisNewAppFormTwoResource REST controller.
 *
 * @see RisNewAppFormTwoResource
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RisNewAppFormTwoResourceTest {

    private static final String DEFAULT_EXAM_NAME = "AAAAA";
    private static final String UPDATED_EXAM_NAME = "BBBBB";
    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_EDUCATIONAL_INSTITUTE = "AAAAA";
    private static final String UPDATED_EDUCATIONAL_INSTITUTE = "BBBBB";

    private static final Integer DEFAULT_PASSING_YEAR = 1;
    private static final Integer UPDATED_PASSING_YEAR = 2;
    private static final String DEFAULT_BOARD_UNIVERSITY = "AAAAA";
    private static final String UPDATED_BOARD_UNIVERSITY = "BBBBB";
    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBB";
    private static final String DEFAULT_EXPERIENCE = "AAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBB";
    private static final String DEFAULT_QOUTA = "AAAAA";
    private static final String UPDATED_QOUTA = "BBBBB";
    private static final String DEFAULT_BANK_DRAFT_NO = "AAAAA";
    private static final String UPDATED_BANK_DRAFT_NO = "BBBBB";
    private static final String DEFAULT_DATE_FIN_DOCUMENT = "AAAAA";
    private static final String UPDATED_DATE_FIN_DOCUMENT = "BBBBB";
    private static final String DEFAULT_BANK_NAME = "AAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBB";
    private static final String DEFAULT_BRANCH_NAME = "AAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBB";
    private static final String DEFAULT_DEPARTMENTAL_CANDIDATE = "AAAAA";
    private static final String UPDATED_DEPARTMENTAL_CANDIDATE = "BBBBB";

    private static final byte[] DEFAULT_BANK_INVOICE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BANK_INVOICE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BANK_INVOICE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BANK_INVOICE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SIGNATURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SIGNATURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_SIGNATURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SIGNATURE_CONTENT_TYPE = "image/png";

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
    private RisNewAppFormTwoRepository risNewAppFormTwoRepository;

    @Inject
    private RisNewAppFormTwoSearchRepository risNewAppFormTwoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRisNewAppFormTwoMockMvc;

    private RisNewAppFormTwo risNewAppFormTwo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RisNewAppFormTwoResource risNewAppFormTwoResource = new RisNewAppFormTwoResource();
        ReflectionTestUtils.setField(risNewAppFormTwoResource, "risNewAppFormTwoRepository", risNewAppFormTwoRepository);
        ReflectionTestUtils.setField(risNewAppFormTwoResource, "risNewAppFormTwoSearchRepository", risNewAppFormTwoSearchRepository);
        this.restRisNewAppFormTwoMockMvc = MockMvcBuilders.standaloneSetup(risNewAppFormTwoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        risNewAppFormTwo = new RisNewAppFormTwo();
        risNewAppFormTwo.setExamName(DEFAULT_EXAM_NAME);
        risNewAppFormTwo.setSubject(DEFAULT_SUBJECT);
        risNewAppFormTwo.setEducationalInstitute(DEFAULT_EDUCATIONAL_INSTITUTE);
        risNewAppFormTwo.setPassingYear(DEFAULT_PASSING_YEAR);
        risNewAppFormTwo.setBoardUniversity(DEFAULT_BOARD_UNIVERSITY);
        risNewAppFormTwo.setAdditionalInformation(DEFAULT_ADDITIONAL_INFORMATION);
        risNewAppFormTwo.setExperience(DEFAULT_EXPERIENCE);
        risNewAppFormTwo.setQouta(DEFAULT_QOUTA);
        risNewAppFormTwo.setBankDraftNo(DEFAULT_BANK_DRAFT_NO);
        risNewAppFormTwo.setDateFinDocument(DEFAULT_DATE_FIN_DOCUMENT);
        risNewAppFormTwo.setBankName(DEFAULT_BANK_NAME);
        risNewAppFormTwo.setBranchName(DEFAULT_BRANCH_NAME);
        risNewAppFormTwo.setDepartmentalCandidate(DEFAULT_DEPARTMENTAL_CANDIDATE);
        risNewAppFormTwo.setBankInvoice(DEFAULT_BANK_INVOICE);
        risNewAppFormTwo.setBankInvoiceContentType(DEFAULT_BANK_INVOICE_CONTENT_TYPE);
        risNewAppFormTwo.setSignature(DEFAULT_SIGNATURE);
        risNewAppFormTwo.setSignatureContentType(DEFAULT_SIGNATURE_CONTENT_TYPE);
        risNewAppFormTwo.setCreatedDate(DEFAULT_CREATED_DATE);
        risNewAppFormTwo.setUpdatedDate(DEFAULT_UPDATED_DATE);
        risNewAppFormTwo.setCreatedBy(DEFAULT_CREATED_BY);
        risNewAppFormTwo.setUpdatedBy(DEFAULT_UPDATED_BY);
        risNewAppFormTwo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRisNewAppFormTwo() throws Exception {
        int databaseSizeBeforeCreate = risNewAppFormTwoRepository.findAll().size();

        // Create the RisNewAppFormTwo

        restRisNewAppFormTwoMockMvc.perform(post("/api/risNewAppFormTwos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppFormTwo)))
                .andExpect(status().isCreated());

        // Validate the RisNewAppFormTwo in the database
        List<RisNewAppFormTwo> risNewAppFormTwos = risNewAppFormTwoRepository.findAll();
        assertThat(risNewAppFormTwos).hasSize(databaseSizeBeforeCreate + 1);
        RisNewAppFormTwo testRisNewAppFormTwo = risNewAppFormTwos.get(risNewAppFormTwos.size() - 1);
        assertThat(testRisNewAppFormTwo.getExamName()).isEqualTo(DEFAULT_EXAM_NAME);
        assertThat(testRisNewAppFormTwo.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testRisNewAppFormTwo.getEducationalInstitute()).isEqualTo(DEFAULT_EDUCATIONAL_INSTITUTE);
        assertThat(testRisNewAppFormTwo.getPassingYear()).isEqualTo(DEFAULT_PASSING_YEAR);
        assertThat(testRisNewAppFormTwo.getBoardUniversity()).isEqualTo(DEFAULT_BOARD_UNIVERSITY);
        assertThat(testRisNewAppFormTwo.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
        assertThat(testRisNewAppFormTwo.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testRisNewAppFormTwo.getQouta()).isEqualTo(DEFAULT_QOUTA);
        assertThat(testRisNewAppFormTwo.getBankDraftNo()).isEqualTo(DEFAULT_BANK_DRAFT_NO);
        assertThat(testRisNewAppFormTwo.getDateFinDocument()).isEqualTo(DEFAULT_DATE_FIN_DOCUMENT);
        assertThat(testRisNewAppFormTwo.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testRisNewAppFormTwo.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testRisNewAppFormTwo.getDepartmentalCandidate()).isEqualTo(DEFAULT_DEPARTMENTAL_CANDIDATE);
        assertThat(testRisNewAppFormTwo.getBankInvoice()).isEqualTo(DEFAULT_BANK_INVOICE);
        assertThat(testRisNewAppFormTwo.getBankInvoiceContentType()).isEqualTo(DEFAULT_BANK_INVOICE_CONTENT_TYPE);
        assertThat(testRisNewAppFormTwo.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testRisNewAppFormTwo.getSignatureContentType()).isEqualTo(DEFAULT_SIGNATURE_CONTENT_TYPE);
        assertThat(testRisNewAppFormTwo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRisNewAppFormTwo.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testRisNewAppFormTwo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRisNewAppFormTwo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRisNewAppFormTwo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkExamNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormTwoRepository.findAll().size();
        // set the field null
        risNewAppFormTwo.setExamName(null);

        // Create the RisNewAppFormTwo, which fails.

        restRisNewAppFormTwoMockMvc.perform(post("/api/risNewAppFormTwos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppFormTwo)))
                .andExpect(status().isBadRequest());

        List<RisNewAppFormTwo> risNewAppFormTwos = risNewAppFormTwoRepository.findAll();
        assertThat(risNewAppFormTwos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRisNewAppFormTwos() throws Exception {
        // Initialize the database
        risNewAppFormTwoRepository.saveAndFlush(risNewAppFormTwo);

        // Get all the risNewAppFormTwos
        restRisNewAppFormTwoMockMvc.perform(get("/api/risNewAppFormTwos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(risNewAppFormTwo.getId().intValue())))
                .andExpect(jsonPath("$.[*].examName").value(hasItem(DEFAULT_EXAM_NAME.toString())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].educationalInstitute").value(hasItem(DEFAULT_EDUCATIONAL_INSTITUTE.toString())))
                .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR)))
                .andExpect(jsonPath("$.[*].boardUniversity").value(hasItem(DEFAULT_BOARD_UNIVERSITY.toString())))
                .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())))
                .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE.toString())))
                .andExpect(jsonPath("$.[*].qouta").value(hasItem(DEFAULT_QOUTA.toString())))
                .andExpect(jsonPath("$.[*].bankDraftNo").value(hasItem(DEFAULT_BANK_DRAFT_NO.toString())))
                .andExpect(jsonPath("$.[*].dateFinDocument").value(hasItem(DEFAULT_DATE_FIN_DOCUMENT.toString())))
                .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
                .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME.toString())))
                .andExpect(jsonPath("$.[*].departmentalCandidate").value(hasItem(DEFAULT_DEPARTMENTAL_CANDIDATE.toString())))
                .andExpect(jsonPath("$.[*].bankInvoiceContentType").value(hasItem(DEFAULT_BANK_INVOICE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].bankInvoice").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANK_INVOICE))))
                .andExpect(jsonPath("$.[*].signatureContentType").value(hasItem(DEFAULT_SIGNATURE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].signature").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE))))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getRisNewAppFormTwo() throws Exception {
        // Initialize the database
        risNewAppFormTwoRepository.saveAndFlush(risNewAppFormTwo);

        // Get the risNewAppFormTwo
        restRisNewAppFormTwoMockMvc.perform(get("/api/risNewAppFormTwos/{id}", risNewAppFormTwo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(risNewAppFormTwo.getId().intValue()))
            .andExpect(jsonPath("$.examName").value(DEFAULT_EXAM_NAME.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.educationalInstitute").value(DEFAULT_EDUCATIONAL_INSTITUTE.toString()))
            .andExpect(jsonPath("$.passingYear").value(DEFAULT_PASSING_YEAR))
            .andExpect(jsonPath("$.boardUniversity").value(DEFAULT_BOARD_UNIVERSITY.toString()))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE.toString()))
            .andExpect(jsonPath("$.qouta").value(DEFAULT_QOUTA.toString()))
            .andExpect(jsonPath("$.bankDraftNo").value(DEFAULT_BANK_DRAFT_NO.toString()))
            .andExpect(jsonPath("$.dateFinDocument").value(DEFAULT_DATE_FIN_DOCUMENT.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME.toString()))
            .andExpect(jsonPath("$.departmentalCandidate").value(DEFAULT_DEPARTMENTAL_CANDIDATE.toString()))
            .andExpect(jsonPath("$.bankInvoiceContentType").value(DEFAULT_BANK_INVOICE_CONTENT_TYPE))
            .andExpect(jsonPath("$.bankInvoice").value(Base64Utils.encodeToString(DEFAULT_BANK_INVOICE)))
            .andExpect(jsonPath("$.signatureContentType").value(DEFAULT_SIGNATURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.signature").value(Base64Utils.encodeToString(DEFAULT_SIGNATURE)))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingRisNewAppFormTwo() throws Exception {
        // Get the risNewAppFormTwo
        restRisNewAppFormTwoMockMvc.perform(get("/api/risNewAppFormTwos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRisNewAppFormTwo() throws Exception {
        // Initialize the database
        risNewAppFormTwoRepository.saveAndFlush(risNewAppFormTwo);

		int databaseSizeBeforeUpdate = risNewAppFormTwoRepository.findAll().size();

        // Update the risNewAppFormTwo
        risNewAppFormTwo.setExamName(UPDATED_EXAM_NAME);
        risNewAppFormTwo.setSubject(UPDATED_SUBJECT);
        risNewAppFormTwo.setEducationalInstitute(UPDATED_EDUCATIONAL_INSTITUTE);
        risNewAppFormTwo.setPassingYear(UPDATED_PASSING_YEAR);
        risNewAppFormTwo.setBoardUniversity(UPDATED_BOARD_UNIVERSITY);
        risNewAppFormTwo.setAdditionalInformation(UPDATED_ADDITIONAL_INFORMATION);
        risNewAppFormTwo.setExperience(UPDATED_EXPERIENCE);
        risNewAppFormTwo.setQouta(UPDATED_QOUTA);
        risNewAppFormTwo.setBankDraftNo(UPDATED_BANK_DRAFT_NO);
        risNewAppFormTwo.setDateFinDocument(UPDATED_DATE_FIN_DOCUMENT);
        risNewAppFormTwo.setBankName(UPDATED_BANK_NAME);
        risNewAppFormTwo.setBranchName(UPDATED_BRANCH_NAME);
        risNewAppFormTwo.setDepartmentalCandidate(UPDATED_DEPARTMENTAL_CANDIDATE);
        risNewAppFormTwo.setBankInvoice(UPDATED_BANK_INVOICE);
        risNewAppFormTwo.setBankInvoiceContentType(UPDATED_BANK_INVOICE_CONTENT_TYPE);
        risNewAppFormTwo.setSignature(UPDATED_SIGNATURE);
        risNewAppFormTwo.setSignatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE);
        risNewAppFormTwo.setCreatedDate(UPDATED_CREATED_DATE);
        risNewAppFormTwo.setUpdatedDate(UPDATED_UPDATED_DATE);
        risNewAppFormTwo.setCreatedBy(UPDATED_CREATED_BY);
        risNewAppFormTwo.setUpdatedBy(UPDATED_UPDATED_BY);
        risNewAppFormTwo.setStatus(UPDATED_STATUS);

        restRisNewAppFormTwoMockMvc.perform(put("/api/risNewAppFormTwos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppFormTwo)))
                .andExpect(status().isOk());

        // Validate the RisNewAppFormTwo in the database
        List<RisNewAppFormTwo> risNewAppFormTwos = risNewAppFormTwoRepository.findAll();
        assertThat(risNewAppFormTwos).hasSize(databaseSizeBeforeUpdate);
        RisNewAppFormTwo testRisNewAppFormTwo = risNewAppFormTwos.get(risNewAppFormTwos.size() - 1);
        assertThat(testRisNewAppFormTwo.getExamName()).isEqualTo(UPDATED_EXAM_NAME);
        assertThat(testRisNewAppFormTwo.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testRisNewAppFormTwo.getEducationalInstitute()).isEqualTo(UPDATED_EDUCATIONAL_INSTITUTE);
        assertThat(testRisNewAppFormTwo.getPassingYear()).isEqualTo(UPDATED_PASSING_YEAR);
        assertThat(testRisNewAppFormTwo.getBoardUniversity()).isEqualTo(UPDATED_BOARD_UNIVERSITY);
        assertThat(testRisNewAppFormTwo.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
        assertThat(testRisNewAppFormTwo.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testRisNewAppFormTwo.getQouta()).isEqualTo(UPDATED_QOUTA);
        assertThat(testRisNewAppFormTwo.getBankDraftNo()).isEqualTo(UPDATED_BANK_DRAFT_NO);
        assertThat(testRisNewAppFormTwo.getDateFinDocument()).isEqualTo(UPDATED_DATE_FIN_DOCUMENT);
        assertThat(testRisNewAppFormTwo.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testRisNewAppFormTwo.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testRisNewAppFormTwo.getDepartmentalCandidate()).isEqualTo(UPDATED_DEPARTMENTAL_CANDIDATE);
        assertThat(testRisNewAppFormTwo.getBankInvoice()).isEqualTo(UPDATED_BANK_INVOICE);
        assertThat(testRisNewAppFormTwo.getBankInvoiceContentType()).isEqualTo(UPDATED_BANK_INVOICE_CONTENT_TYPE);
        assertThat(testRisNewAppFormTwo.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testRisNewAppFormTwo.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
        assertThat(testRisNewAppFormTwo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRisNewAppFormTwo.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testRisNewAppFormTwo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRisNewAppFormTwo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRisNewAppFormTwo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteRisNewAppFormTwo() throws Exception {
        // Initialize the database
        risNewAppFormTwoRepository.saveAndFlush(risNewAppFormTwo);

		int databaseSizeBeforeDelete = risNewAppFormTwoRepository.findAll().size();

        // Get the risNewAppFormTwo
        restRisNewAppFormTwoMockMvc.perform(delete("/api/risNewAppFormTwos/{id}", risNewAppFormTwo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RisNewAppFormTwo> risNewAppFormTwos = risNewAppFormTwoRepository.findAll();
        assertThat(risNewAppFormTwos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/
