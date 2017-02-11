package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlExistingBookIssue;
import gov.step.app.repository.DlExistingBookIssueRepository;
import gov.step.app.repository.search.DlExistingBookIssueSearchRepository;

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
 * Test class for the DlExistingBookIssueResource REST controller.
 *
 * @see DlExistingBookIssueResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlExistingBookIssueResourceTest {

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RETURN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETURN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACT_RETURN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACT_RETURN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_RETURN_STATUS = 1;
    private static final Integer UPDATED_RETURN_STATUS = 2;

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
    private DlExistingBookIssueRepository dlExistingBookIssueRepository;

    @Inject
    private DlExistingBookIssueSearchRepository dlExistingBookIssueSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlExistingBookIssueMockMvc;

    private DlExistingBookIssue dlExistingBookIssue;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlExistingBookIssueResource dlExistingBookIssueResource = new DlExistingBookIssueResource();
        ReflectionTestUtils.setField(dlExistingBookIssueResource, "dlExistingBookIssueRepository", dlExistingBookIssueRepository);
        ReflectionTestUtils.setField(dlExistingBookIssueResource, "dlExistingBookIssueSearchRepository", dlExistingBookIssueSearchRepository);
        this.restDlExistingBookIssueMockMvc = MockMvcBuilders.standaloneSetup(dlExistingBookIssueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlExistingBookIssue = new DlExistingBookIssue();
        dlExistingBookIssue.setEmail(DEFAULT_EMAIL);
        dlExistingBookIssue.setIssueDate(DEFAULT_ISSUE_DATE);
        dlExistingBookIssue.setReturnDate(DEFAULT_RETURN_DATE);
        dlExistingBookIssue.setActReturnDate(DEFAULT_ACT_RETURN_DATE);
        dlExistingBookIssue.setReturnStatus(DEFAULT_RETURN_STATUS);
        dlExistingBookIssue.setCreatedDate(DEFAULT_CREATED_DATE);
        dlExistingBookIssue.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlExistingBookIssue.setCreatedBy(DEFAULT_CREATED_BY);
        dlExistingBookIssue.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlExistingBookIssue.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlExistingBookIssue() throws Exception {
        int databaseSizeBeforeCreate = dlExistingBookIssueRepository.findAll().size();

        // Create the DlExistingBookIssue

        restDlExistingBookIssueMockMvc.perform(post("/api/dlExistingBookIssues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistingBookIssue)))
                .andExpect(status().isCreated());

        // Validate the DlExistingBookIssue in the database
        List<DlExistingBookIssue> dlExistingBookIssues = dlExistingBookIssueRepository.findAll();
        assertThat(dlExistingBookIssues).hasSize(databaseSizeBeforeCreate + 1);
        DlExistingBookIssue testDlExistingBookIssue = dlExistingBookIssues.get(dlExistingBookIssues.size() - 1);
        assertThat(testDlExistingBookIssue.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDlExistingBookIssue.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testDlExistingBookIssue.getReturnDate()).isEqualTo(DEFAULT_RETURN_DATE);
        assertThat(testDlExistingBookIssue.getActReturnDate()).isEqualTo(DEFAULT_ACT_RETURN_DATE);
        assertThat(testDlExistingBookIssue.getReturnStatus()).isEqualTo(DEFAULT_RETURN_STATUS);
        assertThat(testDlExistingBookIssue.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlExistingBookIssue.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlExistingBookIssue.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlExistingBookIssue.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlExistingBookIssue.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistingBookIssueRepository.findAll().size();
        // set the field null
        dlExistingBookIssue.setEmail(null);

        // Create the DlExistingBookIssue, which fails.

        restDlExistingBookIssueMockMvc.perform(post("/api/dlExistingBookIssues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistingBookIssue)))
                .andExpect(status().isBadRequest());

        List<DlExistingBookIssue> dlExistingBookIssues = dlExistingBookIssueRepository.findAll();
        assertThat(dlExistingBookIssues).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistingBookIssueRepository.findAll().size();
        // set the field null
        dlExistingBookIssue.setIssueDate(null);

        // Create the DlExistingBookIssue, which fails.

        restDlExistingBookIssueMockMvc.perform(post("/api/dlExistingBookIssues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistingBookIssue)))
                .andExpect(status().isBadRequest());

        List<DlExistingBookIssue> dlExistingBookIssues = dlExistingBookIssueRepository.findAll();
        assertThat(dlExistingBookIssues).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReturnDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistingBookIssueRepository.findAll().size();
        // set the field null
        dlExistingBookIssue.setReturnDate(null);

        // Create the DlExistingBookIssue, which fails.

        restDlExistingBookIssueMockMvc.perform(post("/api/dlExistingBookIssues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistingBookIssue)))
                .andExpect(status().isBadRequest());

        List<DlExistingBookIssue> dlExistingBookIssues = dlExistingBookIssueRepository.findAll();
        assertThat(dlExistingBookIssues).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReturnStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistingBookIssueRepository.findAll().size();
        // set the field null
        dlExistingBookIssue.setReturnStatus(null);

        // Create the DlExistingBookIssue, which fails.

        restDlExistingBookIssueMockMvc.perform(post("/api/dlExistingBookIssues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistingBookIssue)))
                .andExpect(status().isBadRequest());

        List<DlExistingBookIssue> dlExistingBookIssues = dlExistingBookIssueRepository.findAll();
        assertThat(dlExistingBookIssues).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDlExistingBookIssues() throws Exception {
        // Initialize the database
        dlExistingBookIssueRepository.saveAndFlush(dlExistingBookIssue);

        // Get all the dlExistingBookIssues
        restDlExistingBookIssueMockMvc.perform(get("/api/dlExistingBookIssues"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlExistingBookIssue.getId().intValue())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
                .andExpect(jsonPath("$.[*].returnDate").value(hasItem(DEFAULT_RETURN_DATE.toString())))
                .andExpect(jsonPath("$.[*].actReturnDate").value(hasItem(DEFAULT_ACT_RETURN_DATE.toString())))
                .andExpect(jsonPath("$.[*].returnStatus").value(hasItem(DEFAULT_RETURN_STATUS)))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlExistingBookIssue() throws Exception {
        // Initialize the database
        dlExistingBookIssueRepository.saveAndFlush(dlExistingBookIssue);

        // Get the dlExistingBookIssue
        restDlExistingBookIssueMockMvc.perform(get("/api/dlExistingBookIssues/{id}", dlExistingBookIssue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlExistingBookIssue.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.returnDate").value(DEFAULT_RETURN_DATE.toString()))
            .andExpect(jsonPath("$.actReturnDate").value(DEFAULT_ACT_RETURN_DATE.toString()))
            .andExpect(jsonPath("$.returnStatus").value(DEFAULT_RETURN_STATUS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlExistingBookIssue() throws Exception {
        // Get the dlExistingBookIssue
        restDlExistingBookIssueMockMvc.perform(get("/api/dlExistingBookIssues/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlExistingBookIssue() throws Exception {
        // Initialize the database
        dlExistingBookIssueRepository.saveAndFlush(dlExistingBookIssue);

		int databaseSizeBeforeUpdate = dlExistingBookIssueRepository.findAll().size();

        // Update the dlExistingBookIssue
        dlExistingBookIssue.setEmail(UPDATED_EMAIL);
        dlExistingBookIssue.setIssueDate(UPDATED_ISSUE_DATE);
        dlExistingBookIssue.setReturnDate(UPDATED_RETURN_DATE);
        dlExistingBookIssue.setActReturnDate(UPDATED_ACT_RETURN_DATE);
        dlExistingBookIssue.setReturnStatus(UPDATED_RETURN_STATUS);
        dlExistingBookIssue.setCreatedDate(UPDATED_CREATED_DATE);
        dlExistingBookIssue.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlExistingBookIssue.setCreatedBy(UPDATED_CREATED_BY);
        dlExistingBookIssue.setUpdatedBy(UPDATED_UPDATED_BY);
        dlExistingBookIssue.setStatus(UPDATED_STATUS);

        restDlExistingBookIssueMockMvc.perform(put("/api/dlExistingBookIssues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistingBookIssue)))
                .andExpect(status().isOk());

        // Validate the DlExistingBookIssue in the database
        List<DlExistingBookIssue> dlExistingBookIssues = dlExistingBookIssueRepository.findAll();
        assertThat(dlExistingBookIssues).hasSize(databaseSizeBeforeUpdate);
        DlExistingBookIssue testDlExistingBookIssue = dlExistingBookIssues.get(dlExistingBookIssues.size() - 1);
        assertThat(testDlExistingBookIssue.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDlExistingBookIssue.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testDlExistingBookIssue.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testDlExistingBookIssue.getActReturnDate()).isEqualTo(UPDATED_ACT_RETURN_DATE);
        assertThat(testDlExistingBookIssue.getReturnStatus()).isEqualTo(UPDATED_RETURN_STATUS);
        assertThat(testDlExistingBookIssue.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlExistingBookIssue.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlExistingBookIssue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlExistingBookIssue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlExistingBookIssue.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlExistingBookIssue() throws Exception {
        // Initialize the database
        dlExistingBookIssueRepository.saveAndFlush(dlExistingBookIssue);

		int databaseSizeBeforeDelete = dlExistingBookIssueRepository.findAll().size();

        // Get the dlExistingBookIssue
        restDlExistingBookIssueMockMvc.perform(delete("/api/dlExistingBookIssues/{id}", dlExistingBookIssue.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlExistingBookIssue> dlExistingBookIssues = dlExistingBookIssueRepository.findAll();
        assertThat(dlExistingBookIssues).hasSize(databaseSizeBeforeDelete - 1);
    }
}
