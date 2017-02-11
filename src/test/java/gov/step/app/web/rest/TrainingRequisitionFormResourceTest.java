package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TrainingRequisitionForm;
import gov.step.app.repository.TrainingRequisitionFormRepository;
import gov.step.app.repository.search.TrainingRequisitionFormSearchRepository;

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
 * Test class for the TrainingRequisitionFormResource REST controller.
 *
 * @see TrainingRequisitionFormResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TrainingRequisitionFormResourceTest {

    private static final String DEFAULT_REQUISITION_CODE = "AAAAA";
    private static final String UPDATED_REQUISITION_CODE = "BBBBB";
    private static final String DEFAULT_TRAINING_TYPE = "AAAAA";
    private static final String UPDATED_TRAINING_TYPE = "BBBBB";
    private static final String DEFAULT_SESSION = "AAAAA";
    private static final String UPDATED_SESSION = "BBBBB";

    private static final LocalDate DEFAULT_APPLY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REASON = "AAAAA";
    private static final String UPDATED_REASON = "BBBBB";
    private static final String DEFAULT_FILE_NAME = "AAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_FILE_CONTENT_NAME = "AAAAA";
    private static final String UPDATED_FILE_CONTENT_NAME = "BBBBB";
    private static final String DEFAULT_APPLY_BY = "AAAAA";
    private static final String UPDATED_APPLY_BY = "BBBBB";

//    private static final Long DEFAULT_INSTITUTE_ID = 1;
//    private static final Long UPDATED_INSTITUTE_ID = 2;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private TrainingRequisitionFormRepository trainingRequisitionFormRepository;

    @Inject
    private TrainingRequisitionFormSearchRepository trainingRequisitionFormSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrainingRequisitionFormMockMvc;

    private TrainingRequisitionForm trainingRequisitionForm;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrainingRequisitionFormResource trainingRequisitionFormResource = new TrainingRequisitionFormResource();
        ReflectionTestUtils.setField(trainingRequisitionFormResource, "trainingRequisitionFormRepository", trainingRequisitionFormRepository);
        ReflectionTestUtils.setField(trainingRequisitionFormResource, "trainingRequisitionFormSearchRepository", trainingRequisitionFormSearchRepository);
        this.restTrainingRequisitionFormMockMvc = MockMvcBuilders.standaloneSetup(trainingRequisitionFormResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trainingRequisitionForm = new TrainingRequisitionForm();
        trainingRequisitionForm.setRequisitionCode(DEFAULT_REQUISITION_CODE);
        trainingRequisitionForm.setTrainingType(DEFAULT_TRAINING_TYPE);
        trainingRequisitionForm.setSession(DEFAULT_SESSION);
        trainingRequisitionForm.setApplyDate(DEFAULT_APPLY_DATE);
        trainingRequisitionForm.setReason(DEFAULT_REASON);
        trainingRequisitionForm.setFileName(DEFAULT_FILE_NAME);
        trainingRequisitionForm.setFile(DEFAULT_FILE);
        trainingRequisitionForm.setFileContentType(DEFAULT_FILE_CONTENT_TYPE);
        trainingRequisitionForm.setFileContentName(DEFAULT_FILE_CONTENT_NAME);
        trainingRequisitionForm.setFileContentType(DEFAULT_FILE_CONTENT_TYPE);
        trainingRequisitionForm.setApplyBy(DEFAULT_APPLY_BY);
//        trainingRequisitionForm.setInstitute(DEFAULT_INSTITUTE_ID);
//        trainingRequisitionForm.setInstitute(DEFAULT_INSTITUTE_ID);
        trainingRequisitionForm.setStatus(DEFAULT_STATUS);
        trainingRequisitionForm.setCreateDate(DEFAULT_CREATE_DATE);
        trainingRequisitionForm.setCreateBy(DEFAULT_CREATE_BY);
        trainingRequisitionForm.setUpdateDate(DEFAULT_UPDATE_DATE);
        trainingRequisitionForm.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createTrainingRequisitionForm() throws Exception {
        int databaseSizeBeforeCreate = trainingRequisitionFormRepository.findAll().size();

        // Create the TrainingRequisitionForm

        restTrainingRequisitionFormMockMvc.perform(post("/api/trainingRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingRequisitionForm)))
                .andExpect(status().isCreated());

        // Validate the TrainingRequisitionForm in the database
        List<TrainingRequisitionForm> trainingRequisitionForms = trainingRequisitionFormRepository.findAll();
        assertThat(trainingRequisitionForms).hasSize(databaseSizeBeforeCreate + 1);
        TrainingRequisitionForm testTrainingRequisitionForm = trainingRequisitionForms.get(trainingRequisitionForms.size() - 1);
        assertThat(testTrainingRequisitionForm.getRequisitionCode()).isEqualTo(DEFAULT_REQUISITION_CODE);
        assertThat(testTrainingRequisitionForm.getTrainingType()).isEqualTo(DEFAULT_TRAINING_TYPE);
        assertThat(testTrainingRequisitionForm.getSession()).isEqualTo(DEFAULT_SESSION);
        assertThat(testTrainingRequisitionForm.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
        assertThat(testTrainingRequisitionForm.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testTrainingRequisitionForm.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testTrainingRequisitionForm.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testTrainingRequisitionForm.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testTrainingRequisitionForm.getFileContentName()).isEqualTo(DEFAULT_FILE_CONTENT_NAME);
        assertThat(testTrainingRequisitionForm.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testTrainingRequisitionForm.getApplyBy()).isEqualTo(DEFAULT_APPLY_BY);
//        assertThat(testTrainingRequisitionForm.getInstituteId()).isEqualTo(DEFAULT_INSTITUTE_ID);
//        assertThat(testTrainingRequisitionForm.getInstituteId()).isEqualTo(DEFAULT_INSTITUTE_ID);
        assertThat(testTrainingRequisitionForm.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrainingRequisitionForm.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTrainingRequisitionForm.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTrainingRequisitionForm.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testTrainingRequisitionForm.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkRequisitionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRequisitionFormRepository.findAll().size();
        // set the field null
        trainingRequisitionForm.setRequisitionCode(null);

        // Create the TrainingRequisitionForm, which fails.

        restTrainingRequisitionFormMockMvc.perform(post("/api/trainingRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingRequisitionForm)))
                .andExpect(status().isBadRequest());

        List<TrainingRequisitionForm> trainingRequisitionForms = trainingRequisitionFormRepository.findAll();
        assertThat(trainingRequisitionForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrainingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRequisitionFormRepository.findAll().size();
        // set the field null
        trainingRequisitionForm.setTrainingType(null);

        // Create the TrainingRequisitionForm, which fails.

        restTrainingRequisitionFormMockMvc.perform(post("/api/trainingRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingRequisitionForm)))
                .andExpect(status().isBadRequest());

        List<TrainingRequisitionForm> trainingRequisitionForms = trainingRequisitionFormRepository.findAll();
        assertThat(trainingRequisitionForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplyDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRequisitionFormRepository.findAll().size();
        // set the field null
        trainingRequisitionForm.setApplyDate(null);

        // Create the TrainingRequisitionForm, which fails.

        restTrainingRequisitionFormMockMvc.perform(post("/api/trainingRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingRequisitionForm)))
                .andExpect(status().isBadRequest());

        List<TrainingRequisitionForm> trainingRequisitionForms = trainingRequisitionFormRepository.findAll();
        assertThat(trainingRequisitionForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrainingRequisitionForms() throws Exception {
        // Initialize the database
        trainingRequisitionFormRepository.saveAndFlush(trainingRequisitionForm);

        // Get all the trainingRequisitionForms
        restTrainingRequisitionFormMockMvc.perform(get("/api/trainingRequisitionForms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trainingRequisitionForm.getId().intValue())))
                .andExpect(jsonPath("$.[*].requisitionCode").value(hasItem(DEFAULT_REQUISITION_CODE.toString())))
                .andExpect(jsonPath("$.[*].trainingType").value(hasItem(DEFAULT_TRAINING_TYPE.toString())))
                .andExpect(jsonPath("$.[*].session").value(hasItem(DEFAULT_SESSION.toString())))
                .andExpect(jsonPath("$.[*].applyDate").value(hasItem(DEFAULT_APPLY_DATE.toString())))
                .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
                .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
                .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
                .andExpect(jsonPath("$.[*].fileContentName").value(hasItem(DEFAULT_FILE_CONTENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].applyBy").value(hasItem(DEFAULT_APPLY_BY.toString())))
//                .andExpect(jsonPath("$.[*].instituteId").value(hasItem(DEFAULT_INSTITUTE_ID.intValue())))
//                .andExpect(jsonPath("$.[*].instituteId").value(hasItem(DEFAULT_INSTITUTE_ID.intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getTrainingRequisitionForm() throws Exception {
        // Initialize the database
        trainingRequisitionFormRepository.saveAndFlush(trainingRequisitionForm);

        // Get the trainingRequisitionForm
        restTrainingRequisitionFormMockMvc.perform(get("/api/trainingRequisitionForms/{id}", trainingRequisitionForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trainingRequisitionForm.getId().intValue()))
            .andExpect(jsonPath("$.requisitionCode").value(DEFAULT_REQUISITION_CODE.toString()))
            .andExpect(jsonPath("$.trainingType").value(DEFAULT_TRAINING_TYPE.toString()))
            .andExpect(jsonPath("$.session").value(DEFAULT_SESSION.toString()))
            .andExpect(jsonPath("$.applyDate").value(DEFAULT_APPLY_DATE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.fileContentName").value(DEFAULT_FILE_CONTENT_NAME.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.applyBy").value(DEFAULT_APPLY_BY.toString()))
//            .andExpect(jsonPath("$.instituteId").value(DEFAULT_INSTITUTE_ID.intValue()))
//            .andExpect(jsonPath("$.instituteId").value(DEFAULT_INSTITUTE_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingRequisitionForm() throws Exception {
        // Get the trainingRequisitionForm
        restTrainingRequisitionFormMockMvc.perform(get("/api/trainingRequisitionForms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingRequisitionForm() throws Exception {
        // Initialize the database
        trainingRequisitionFormRepository.saveAndFlush(trainingRequisitionForm);

		int databaseSizeBeforeUpdate = trainingRequisitionFormRepository.findAll().size();

        // Update the trainingRequisitionForm
        trainingRequisitionForm.setRequisitionCode(UPDATED_REQUISITION_CODE);
        trainingRequisitionForm.setTrainingType(UPDATED_TRAINING_TYPE);
        trainingRequisitionForm.setSession(UPDATED_SESSION);
        trainingRequisitionForm.setApplyDate(UPDATED_APPLY_DATE);
        trainingRequisitionForm.setReason(UPDATED_REASON);
        trainingRequisitionForm.setFileName(UPDATED_FILE_NAME);
        trainingRequisitionForm.setFile(UPDATED_FILE);
        trainingRequisitionForm.setFileContentType(UPDATED_FILE_CONTENT_TYPE);
        trainingRequisitionForm.setFileContentName(UPDATED_FILE_CONTENT_NAME);
        trainingRequisitionForm.setFileContentType(UPDATED_FILE_CONTENT_TYPE);
        trainingRequisitionForm.setApplyBy(UPDATED_APPLY_BY);
//        trainingRequisitionForm.setInstituteId(UPDATED_INSTITUTE_ID);
//        trainingRequisitionForm.setInstituteId(UPDATED_INSTITUTE_ID);
        trainingRequisitionForm.setStatus(UPDATED_STATUS);
        trainingRequisitionForm.setCreateDate(UPDATED_CREATE_DATE);
        trainingRequisitionForm.setCreateBy(UPDATED_CREATE_BY);
        trainingRequisitionForm.setUpdateDate(UPDATED_UPDATE_DATE);
        trainingRequisitionForm.setUpdateBy(UPDATED_UPDATE_BY);

        restTrainingRequisitionFormMockMvc.perform(put("/api/trainingRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trainingRequisitionForm)))
                .andExpect(status().isOk());

        // Validate the TrainingRequisitionForm in the database
        List<TrainingRequisitionForm> trainingRequisitionForms = trainingRequisitionFormRepository.findAll();
        assertThat(trainingRequisitionForms).hasSize(databaseSizeBeforeUpdate);
        TrainingRequisitionForm testTrainingRequisitionForm = trainingRequisitionForms.get(trainingRequisitionForms.size() - 1);
        assertThat(testTrainingRequisitionForm.getRequisitionCode()).isEqualTo(UPDATED_REQUISITION_CODE);
        assertThat(testTrainingRequisitionForm.getTrainingType()).isEqualTo(UPDATED_TRAINING_TYPE);
        assertThat(testTrainingRequisitionForm.getSession()).isEqualTo(UPDATED_SESSION);
        assertThat(testTrainingRequisitionForm.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
        assertThat(testTrainingRequisitionForm.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testTrainingRequisitionForm.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testTrainingRequisitionForm.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testTrainingRequisitionForm.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testTrainingRequisitionForm.getFileContentName()).isEqualTo(UPDATED_FILE_CONTENT_NAME);
        assertThat(testTrainingRequisitionForm.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testTrainingRequisitionForm.getApplyBy()).isEqualTo(UPDATED_APPLY_BY);
//        assertThat(testTrainingRequisitionForm.getInstituteId()).isEqualTo(UPDATED_INSTITUTE_ID);
//        assertThat(testTrainingRequisitionForm.getInstituteId()).isEqualTo(UPDATED_INSTITUTE_ID);
        assertThat(testTrainingRequisitionForm.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainingRequisitionForm.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTrainingRequisitionForm.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTrainingRequisitionForm.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testTrainingRequisitionForm.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteTrainingRequisitionForm() throws Exception {
        // Initialize the database
        trainingRequisitionFormRepository.saveAndFlush(trainingRequisitionForm);

		int databaseSizeBeforeDelete = trainingRequisitionFormRepository.findAll().size();

        // Get the trainingRequisitionForm
        restTrainingRequisitionFormMockMvc.perform(delete("/api/trainingRequisitionForms/{id}", trainingRequisitionForm.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingRequisitionForm> trainingRequisitionForms = trainingRequisitionFormRepository.findAll();
        assertThat(trainingRequisitionForms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
