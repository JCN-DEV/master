package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EmployeeLoanAttachment;
import gov.step.app.repository.EmployeeLoanAttachmentRepository;
import gov.step.app.repository.search.EmployeeLoanAttachmentSearchRepository;

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
 * Test class for the EmployeeLoanAttachmentResource REST controller.
 *
 * @see EmployeeLoanAttachmentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeLoanAttachmentResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_FILE_NAME = "AAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_FILE_CONTENT_NAME = "AAAAA";
    private static final String UPDATED_FILE_CONTENT_NAME = "BBBBB";
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private EmployeeLoanAttachmentRepository employeeLoanAttachmentRepository;

    @Inject
    private EmployeeLoanAttachmentSearchRepository employeeLoanAttachmentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeLoanAttachmentMockMvc;

    private EmployeeLoanAttachment employeeLoanAttachment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeLoanAttachmentResource employeeLoanAttachmentResource = new EmployeeLoanAttachmentResource();
        ReflectionTestUtils.setField(employeeLoanAttachmentResource, "employeeLoanAttachmentRepository", employeeLoanAttachmentRepository);
        ReflectionTestUtils.setField(employeeLoanAttachmentResource, "employeeLoanAttachmentSearchRepository", employeeLoanAttachmentSearchRepository);
        this.restEmployeeLoanAttachmentMockMvc = MockMvcBuilders.standaloneSetup(employeeLoanAttachmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employeeLoanAttachment = new EmployeeLoanAttachment();
        employeeLoanAttachment.setName(DEFAULT_NAME);
        employeeLoanAttachment.setFileName(DEFAULT_FILE_NAME);
        employeeLoanAttachment.setFile(DEFAULT_FILE);
        employeeLoanAttachment.setFileContentType(DEFAULT_FILE_CONTENT_TYPE);
        employeeLoanAttachment.setFileContentName(DEFAULT_FILE_CONTENT_NAME);
        employeeLoanAttachment.setFileContentType(DEFAULT_FILE_CONTENT_TYPE);
        employeeLoanAttachment.setRemarks(DEFAULT_REMARKS);
//        employeeLoanAttachment.setStatus(DEFAULT_STATUS);
        employeeLoanAttachment.setCreateBy(DEFAULT_CREATE_BY);
        employeeLoanAttachment.setCreateDate(DEFAULT_CREATE_DATE);
        employeeLoanAttachment.setUpdateBy(DEFAULT_UPDATE_BY);
        employeeLoanAttachment.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createEmployeeLoanAttachment() throws Exception {
        int databaseSizeBeforeCreate = employeeLoanAttachmentRepository.findAll().size();

        // Create the EmployeeLoanAttachment

        restEmployeeLoanAttachmentMockMvc.perform(post("/api/employeeLoanAttachments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanAttachment)))
                .andExpect(status().isCreated());

        // Validate the EmployeeLoanAttachment in the database
        List<EmployeeLoanAttachment> employeeLoanAttachments = employeeLoanAttachmentRepository.findAll();
        assertThat(employeeLoanAttachments).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLoanAttachment testEmployeeLoanAttachment = employeeLoanAttachments.get(employeeLoanAttachments.size() - 1);
        assertThat(testEmployeeLoanAttachment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployeeLoanAttachment.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testEmployeeLoanAttachment.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testEmployeeLoanAttachment.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testEmployeeLoanAttachment.getFileContentName()).isEqualTo(DEFAULT_FILE_CONTENT_NAME);
        assertThat(testEmployeeLoanAttachment.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testEmployeeLoanAttachment.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testEmployeeLoanAttachment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeLoanAttachment.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testEmployeeLoanAttachment.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testEmployeeLoanAttachment.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testEmployeeLoanAttachment.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanAttachmentRepository.findAll().size();
        // set the field null
        employeeLoanAttachment.setName(null);

        // Create the EmployeeLoanAttachment, which fails.

        restEmployeeLoanAttachmentMockMvc.perform(post("/api/employeeLoanAttachments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanAttachment)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanAttachment> employeeLoanAttachments = employeeLoanAttachmentRepository.findAll();
        assertThat(employeeLoanAttachments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeLoanAttachments() throws Exception {
        // Initialize the database
        employeeLoanAttachmentRepository.saveAndFlush(employeeLoanAttachment);

        // Get all the employeeLoanAttachments
        restEmployeeLoanAttachmentMockMvc.perform(get("/api/employeeLoanAttachments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLoanAttachment.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
                .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
                .andExpect(jsonPath("$.[*].fileContentName").value(hasItem(DEFAULT_FILE_CONTENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getEmployeeLoanAttachment() throws Exception {
        // Initialize the database
        employeeLoanAttachmentRepository.saveAndFlush(employeeLoanAttachment);

        // Get the employeeLoanAttachment
        restEmployeeLoanAttachmentMockMvc.perform(get("/api/employeeLoanAttachments/{id}", employeeLoanAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employeeLoanAttachment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.fileContentName").value(DEFAULT_FILE_CONTENT_NAME.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeLoanAttachment() throws Exception {
        // Get the employeeLoanAttachment
        restEmployeeLoanAttachmentMockMvc.perform(get("/api/employeeLoanAttachments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLoanAttachment() throws Exception {
        // Initialize the database
        employeeLoanAttachmentRepository.saveAndFlush(employeeLoanAttachment);

		int databaseSizeBeforeUpdate = employeeLoanAttachmentRepository.findAll().size();

        // Update the employeeLoanAttachment
        employeeLoanAttachment.setName(UPDATED_NAME);
        employeeLoanAttachment.setFileName(UPDATED_FILE_NAME);
        employeeLoanAttachment.setFile(UPDATED_FILE);
        employeeLoanAttachment.setFileContentType(UPDATED_FILE_CONTENT_TYPE);
        employeeLoanAttachment.setFileContentName(UPDATED_FILE_CONTENT_NAME);
        employeeLoanAttachment.setFileContentType(UPDATED_FILE_CONTENT_TYPE);
        employeeLoanAttachment.setRemarks(UPDATED_REMARKS);
//        employeeLoanAttachment.setStatus(UPDATED_STATUS);
        employeeLoanAttachment.setCreateBy(UPDATED_CREATE_BY);
        employeeLoanAttachment.setCreateDate(UPDATED_CREATE_DATE);
        employeeLoanAttachment.setUpdateBy(UPDATED_UPDATE_BY);
        employeeLoanAttachment.setUpdateDate(UPDATED_UPDATE_DATE);

        restEmployeeLoanAttachmentMockMvc.perform(put("/api/employeeLoanAttachments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanAttachment)))
                .andExpect(status().isOk());

        // Validate the EmployeeLoanAttachment in the database
        List<EmployeeLoanAttachment> employeeLoanAttachments = employeeLoanAttachmentRepository.findAll();
        assertThat(employeeLoanAttachments).hasSize(databaseSizeBeforeUpdate);
        EmployeeLoanAttachment testEmployeeLoanAttachment = employeeLoanAttachments.get(employeeLoanAttachments.size() - 1);
        assertThat(testEmployeeLoanAttachment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployeeLoanAttachment.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testEmployeeLoanAttachment.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testEmployeeLoanAttachment.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testEmployeeLoanAttachment.getFileContentName()).isEqualTo(UPDATED_FILE_CONTENT_NAME);
        assertThat(testEmployeeLoanAttachment.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testEmployeeLoanAttachment.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testEmployeeLoanAttachment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeLoanAttachment.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testEmployeeLoanAttachment.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testEmployeeLoanAttachment.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testEmployeeLoanAttachment.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deleteEmployeeLoanAttachment() throws Exception {
        // Initialize the database
        employeeLoanAttachmentRepository.saveAndFlush(employeeLoanAttachment);

		int databaseSizeBeforeDelete = employeeLoanAttachmentRepository.findAll().size();

        // Get the employeeLoanAttachment
        restEmployeeLoanAttachmentMockMvc.perform(delete("/api/employeeLoanAttachments/{id}", employeeLoanAttachment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeLoanAttachment> employeeLoanAttachments = employeeLoanAttachmentRepository.findAll();
        assertThat(employeeLoanAttachments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
