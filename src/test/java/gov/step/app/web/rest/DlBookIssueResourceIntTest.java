package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlBookIssue;
import gov.step.app.repository.DlBookIssueRepository;
import gov.step.app.repository.search.DlBookIssueSearchRepository;

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
 * Test class for the DlBookIssueResource REST controller.
 *
 * @see DlBookIssueResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlBookIssueResourceIntTest {

    private static final String DEFAULT_ISBN_NO = "AAAAA";
    private static final String UPDATED_ISBN_NO = "BBBBB";

    private static final Integer DEFAULT_NO_OF_COPIES = 1;
    private static final Integer UPDATED_NO_OF_COPIES = 2;

    private static final LocalDate DEFAULT_RETURN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETURN_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private DlBookIssueRepository dlBookIssueRepository;

    @Inject
    private DlBookIssueSearchRepository dlBookIssueSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlBookIssueMockMvc;

    private DlBookIssue dlBookIssue;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlBookIssueResource dlBookIssueResource = new DlBookIssueResource();
        ReflectionTestUtils.setField(dlBookIssueResource, "dlBookIssueRepository", dlBookIssueRepository);
        ReflectionTestUtils.setField(dlBookIssueResource, "dlBookIssueSearchRepository", dlBookIssueSearchRepository);
        this.restDlBookIssueMockMvc = MockMvcBuilders.standaloneSetup(dlBookIssueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlBookIssue = new DlBookIssue();
        dlBookIssue.setIsbnNo(DEFAULT_ISBN_NO);
        dlBookIssue.setNoOfCopies(DEFAULT_NO_OF_COPIES);
        dlBookIssue.setReturnDate(DEFAULT_RETURN_DATE);
        dlBookIssue.setCreatedDate(DEFAULT_CREATED_DATE);
        dlBookIssue.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlBookIssue.setCreatedBy(DEFAULT_CREATED_BY);
        dlBookIssue.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlBookIssue.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlBookIssue() throws Exception {
        int databaseSizeBeforeCreate = dlBookIssueRepository.findAll().size();

        // Create the DlBookIssue

        restDlBookIssueMockMvc.perform(post("/api/dlBookIssues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookIssue)))
                .andExpect(status().isCreated());

        // Validate the DlBookIssue in the database
        List<DlBookIssue> dlBookIssues = dlBookIssueRepository.findAll();
        assertThat(dlBookIssues).hasSize(databaseSizeBeforeCreate + 1);
        DlBookIssue testDlBookIssue = dlBookIssues.get(dlBookIssues.size() - 1);
        assertThat(testDlBookIssue.getIsbnNo()).isEqualTo(DEFAULT_ISBN_NO);
        assertThat(testDlBookIssue.getNoOfCopies()).isEqualTo(DEFAULT_NO_OF_COPIES);
        assertThat(testDlBookIssue.getReturnDate()).isEqualTo(DEFAULT_RETURN_DATE);
        assertThat(testDlBookIssue.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlBookIssue.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlBookIssue.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlBookIssue.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlBookIssue.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlBookIssues() throws Exception {
        // Initialize the database
        dlBookIssueRepository.saveAndFlush(dlBookIssue);

        // Get all the dlBookIssues
        restDlBookIssueMockMvc.perform(get("/api/dlBookIssues"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlBookIssue.getId().intValue())))
                .andExpect(jsonPath("$.[*].isbnNo").value(hasItem(DEFAULT_ISBN_NO.toString())))
                .andExpect(jsonPath("$.[*].noOfCopies").value(hasItem(DEFAULT_NO_OF_COPIES)))
                .andExpect(jsonPath("$.[*].returnDate").value(hasItem(DEFAULT_RETURN_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlBookIssue() throws Exception {
        // Initialize the database
        dlBookIssueRepository.saveAndFlush(dlBookIssue);

        // Get the dlBookIssue
        restDlBookIssueMockMvc.perform(get("/api/dlBookIssues/{id}", dlBookIssue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlBookIssue.getId().intValue()))
            .andExpect(jsonPath("$.isbnNo").value(DEFAULT_ISBN_NO.toString()))
            .andExpect(jsonPath("$.noOfCopies").value(DEFAULT_NO_OF_COPIES))
            .andExpect(jsonPath("$.returnDate").value(DEFAULT_RETURN_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlBookIssue() throws Exception {
        // Get the dlBookIssue
        restDlBookIssueMockMvc.perform(get("/api/dlBookIssues/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlBookIssue() throws Exception {
        // Initialize the database
        dlBookIssueRepository.saveAndFlush(dlBookIssue);

		int databaseSizeBeforeUpdate = dlBookIssueRepository.findAll().size();

        // Update the dlBookIssue
        dlBookIssue.setIsbnNo(UPDATED_ISBN_NO);
        dlBookIssue.setNoOfCopies(UPDATED_NO_OF_COPIES);
        dlBookIssue.setReturnDate(UPDATED_RETURN_DATE);
        dlBookIssue.setCreatedDate(UPDATED_CREATED_DATE);
        dlBookIssue.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlBookIssue.setCreatedBy(UPDATED_CREATED_BY);
        dlBookIssue.setUpdatedBy(UPDATED_UPDATED_BY);
        dlBookIssue.setStatus(UPDATED_STATUS);

        restDlBookIssueMockMvc.perform(put("/api/dlBookIssues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookIssue)))
                .andExpect(status().isOk());

        // Validate the DlBookIssue in the database
        List<DlBookIssue> dlBookIssues = dlBookIssueRepository.findAll();
        assertThat(dlBookIssues).hasSize(databaseSizeBeforeUpdate);
        DlBookIssue testDlBookIssue = dlBookIssues.get(dlBookIssues.size() - 1);
        assertThat(testDlBookIssue.getIsbnNo()).isEqualTo(UPDATED_ISBN_NO);
        assertThat(testDlBookIssue.getNoOfCopies()).isEqualTo(UPDATED_NO_OF_COPIES);
        assertThat(testDlBookIssue.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testDlBookIssue.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlBookIssue.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlBookIssue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlBookIssue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlBookIssue.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlBookIssue() throws Exception {
        // Initialize the database
        dlBookIssueRepository.saveAndFlush(dlBookIssue);

		int databaseSizeBeforeDelete = dlBookIssueRepository.findAll().size();

        // Get the dlBookIssue
        restDlBookIssueMockMvc.perform(delete("/api/dlBookIssues/{id}", dlBookIssue.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlBookIssue> dlBookIssues = dlBookIssueRepository.findAll();
        assertThat(dlBookIssues).hasSize(databaseSizeBeforeDelete - 1);
    }
}
