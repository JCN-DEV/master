package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InformationCorrection;
import gov.step.app.repository.InformationCorrectionRepository;
import gov.step.app.repository.search.InformationCorrectionSearchRepository;

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
 * Test class for the InformationCorrectionResource REST controller.
 *
 * @see InformationCorrectionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InformationCorrectionResourceIntTest {


    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_INDEX_NO = "AAAAA";
    private static final String UPDATED_INDEX_NO = "BBBBB";
    private static final String DEFAULT_BANK_ACCOUNT_NO = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NO = "BBBBB";

    private static final Boolean DEFAULT_AD_FORWARDED = false;
    private static final Boolean UPDATED_AD_FORWARDED = true;

    private static final Boolean DEFAULT_DG_FINAL_APPROVAL = false;
    private static final Boolean UPDATED_DG_FINAL_APPROVAL = true;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DIRECTOR_COMMENT = "AAAAA";
    private static final String UPDATED_DIRECTOR_COMMENT = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InformationCorrectionRepository informationCorrectionRepository;

    @Inject
    private InformationCorrectionSearchRepository informationCorrectionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInformationCorrectionMockMvc;

    private InformationCorrection informationCorrection;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InformationCorrectionResource informationCorrectionResource = new InformationCorrectionResource();
        ReflectionTestUtils.setField(informationCorrectionResource, "informationCorrectionRepository", informationCorrectionRepository);
        ReflectionTestUtils.setField(informationCorrectionResource, "informationCorrectionSearchRepository", informationCorrectionSearchRepository);
        this.restInformationCorrectionMockMvc = MockMvcBuilders.standaloneSetup(informationCorrectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        informationCorrection = new InformationCorrection();
        informationCorrection.setDob(DEFAULT_DOB);
        informationCorrection.setName(DEFAULT_NAME);
        informationCorrection.setIndexNo(DEFAULT_INDEX_NO);
        informationCorrection.setBankAccountNo(DEFAULT_BANK_ACCOUNT_NO);
        informationCorrection.setAdForwarded(DEFAULT_AD_FORWARDED);
        informationCorrection.setDgFinalApproval(DEFAULT_DG_FINAL_APPROVAL);
        informationCorrection.setCreatedDate(DEFAULT_CREATED_DATE);
        informationCorrection.setModifiedDate(DEFAULT_MODIFIED_DATE);
        informationCorrection.setDirectorComment(DEFAULT_DIRECTOR_COMMENT);
        informationCorrection.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInformationCorrection() throws Exception {
        int databaseSizeBeforeCreate = informationCorrectionRepository.findAll().size();

        // Create the InformationCorrection

        restInformationCorrectionMockMvc.perform(post("/api/informationCorrections")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(informationCorrection)))
                .andExpect(status().isCreated());

        // Validate the InformationCorrection in the database
        List<InformationCorrection> informationCorrections = informationCorrectionRepository.findAll();
        assertThat(informationCorrections).hasSize(databaseSizeBeforeCreate + 1);
        InformationCorrection testInformationCorrection = informationCorrections.get(informationCorrections.size() - 1);
        assertThat(testInformationCorrection.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testInformationCorrection.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInformationCorrection.getIndexNo()).isEqualTo(DEFAULT_INDEX_NO);
        assertThat(testInformationCorrection.getBankAccountNo()).isEqualTo(DEFAULT_BANK_ACCOUNT_NO);
        assertThat(testInformationCorrection.getAdForwarded()).isEqualTo(DEFAULT_AD_FORWARDED);
        assertThat(testInformationCorrection.getDgFinalApproval()).isEqualTo(DEFAULT_DG_FINAL_APPROVAL);
        assertThat(testInformationCorrection.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInformationCorrection.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testInformationCorrection.getDirectorComment()).isEqualTo(DEFAULT_DIRECTOR_COMMENT);
        assertThat(testInformationCorrection.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInformationCorrections() throws Exception {
        // Initialize the database
        informationCorrectionRepository.saveAndFlush(informationCorrection);

        // Get all the informationCorrections
        restInformationCorrectionMockMvc.perform(get("/api/informationCorrections"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(informationCorrection.getId().intValue())))
                .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].indexNo").value(hasItem(DEFAULT_INDEX_NO.toString())))
                .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO.toString())))
                .andExpect(jsonPath("$.[*].adForwarded").value(hasItem(DEFAULT_AD_FORWARDED.booleanValue())))
                .andExpect(jsonPath("$.[*].dgFinalApproval").value(hasItem(DEFAULT_DG_FINAL_APPROVAL.booleanValue())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
                .andExpect(jsonPath("$.[*].directorComment").value(hasItem(DEFAULT_DIRECTOR_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInformationCorrection() throws Exception {
        // Initialize the database
        informationCorrectionRepository.saveAndFlush(informationCorrection);

        // Get the informationCorrection
        restInformationCorrectionMockMvc.perform(get("/api/informationCorrections/{id}", informationCorrection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(informationCorrection.getId().intValue()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.indexNo").value(DEFAULT_INDEX_NO.toString()))
            .andExpect(jsonPath("$.bankAccountNo").value(DEFAULT_BANK_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.adForwarded").value(DEFAULT_AD_FORWARDED.booleanValue()))
            .andExpect(jsonPath("$.dgFinalApproval").value(DEFAULT_DG_FINAL_APPROVAL.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.directorComment").value(DEFAULT_DIRECTOR_COMMENT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInformationCorrection() throws Exception {
        // Get the informationCorrection
        restInformationCorrectionMockMvc.perform(get("/api/informationCorrections/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformationCorrection() throws Exception {
        // Initialize the database
        informationCorrectionRepository.saveAndFlush(informationCorrection);

		int databaseSizeBeforeUpdate = informationCorrectionRepository.findAll().size();

        // Update the informationCorrection
        informationCorrection.setDob(UPDATED_DOB);
        informationCorrection.setName(UPDATED_NAME);
        informationCorrection.setIndexNo(UPDATED_INDEX_NO);
        informationCorrection.setBankAccountNo(UPDATED_BANK_ACCOUNT_NO);
        informationCorrection.setAdForwarded(UPDATED_AD_FORWARDED);
        informationCorrection.setDgFinalApproval(UPDATED_DG_FINAL_APPROVAL);
        informationCorrection.setCreatedDate(UPDATED_CREATED_DATE);
        informationCorrection.setModifiedDate(UPDATED_MODIFIED_DATE);
        informationCorrection.setDirectorComment(UPDATED_DIRECTOR_COMMENT);
        informationCorrection.setStatus(UPDATED_STATUS);

        restInformationCorrectionMockMvc.perform(put("/api/informationCorrections")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(informationCorrection)))
                .andExpect(status().isOk());

        // Validate the InformationCorrection in the database
        List<InformationCorrection> informationCorrections = informationCorrectionRepository.findAll();
        assertThat(informationCorrections).hasSize(databaseSizeBeforeUpdate);
        InformationCorrection testInformationCorrection = informationCorrections.get(informationCorrections.size() - 1);
        assertThat(testInformationCorrection.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testInformationCorrection.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInformationCorrection.getIndexNo()).isEqualTo(UPDATED_INDEX_NO);
        assertThat(testInformationCorrection.getBankAccountNo()).isEqualTo(UPDATED_BANK_ACCOUNT_NO);
        assertThat(testInformationCorrection.getAdForwarded()).isEqualTo(UPDATED_AD_FORWARDED);
        assertThat(testInformationCorrection.getDgFinalApproval()).isEqualTo(UPDATED_DG_FINAL_APPROVAL);
        assertThat(testInformationCorrection.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInformationCorrection.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testInformationCorrection.getDirectorComment()).isEqualTo(UPDATED_DIRECTOR_COMMENT);
        assertThat(testInformationCorrection.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInformationCorrection() throws Exception {
        // Initialize the database
        informationCorrectionRepository.saveAndFlush(informationCorrection);

		int databaseSizeBeforeDelete = informationCorrectionRepository.findAll().size();

        // Get the informationCorrection
        restInformationCorrectionMockMvc.perform(delete("/api/informationCorrections/{id}", informationCorrection.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InformationCorrection> informationCorrections = informationCorrectionRepository.findAll();
        assertThat(informationCorrections).hasSize(databaseSizeBeforeDelete - 1);
    }
}
