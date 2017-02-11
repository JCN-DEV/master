package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlBookReturn;
import gov.step.app.repository.DlBookReturnRepository;
import gov.step.app.repository.search.DlBookReturnSearchRepository;

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
 * Test class for the DlBookReturnResource REST controller.
 *
 * @see DlBookReturnResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlBookReturnResourceIntTest {


    private static final Long DEFAULT_ISSUE_ID =0L ;
    private static final Long UPDATED_ISSUE_ID =0L ;

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
    private DlBookReturnRepository dlBookReturnRepository;

    @Inject
    private DlBookReturnSearchRepository dlBookReturnSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlBookReturnMockMvc;

    private DlBookReturn dlBookReturn;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlBookReturnResource dlBookReturnResource = new DlBookReturnResource();
        ReflectionTestUtils.setField(dlBookReturnResource, "dlBookReturnRepository", dlBookReturnRepository);
        ReflectionTestUtils.setField(dlBookReturnResource, "dlBookReturnSearchRepository", dlBookReturnSearchRepository);
        this.restDlBookReturnMockMvc = MockMvcBuilders.standaloneSetup(dlBookReturnResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlBookReturn = new DlBookReturn();
        dlBookReturn.setIssueId(DEFAULT_ISSUE_ID);
        dlBookReturn.setCreatedDate(DEFAULT_CREATED_DATE);
        dlBookReturn.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlBookReturn.setCreatedBy(DEFAULT_CREATED_BY);
        dlBookReturn.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlBookReturn.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlBookReturn() throws Exception {
        int databaseSizeBeforeCreate = dlBookReturnRepository.findAll().size();

        // Create the DlBookReturn

        restDlBookReturnMockMvc.perform(post("/api/dlBookReturns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookReturn)))
                .andExpect(status().isCreated());

        // Validate the DlBookReturn in the database
        List<DlBookReturn> dlBookReturns = dlBookReturnRepository.findAll();
        assertThat(dlBookReturns).hasSize(databaseSizeBeforeCreate + 1);
        DlBookReturn testDlBookReturn = dlBookReturns.get(dlBookReturns.size() - 1);
        assertThat(testDlBookReturn.getIssueId()).isEqualTo(DEFAULT_ISSUE_ID);
        assertThat(testDlBookReturn.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlBookReturn.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlBookReturn.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlBookReturn.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlBookReturn.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlBookReturns() throws Exception {
        // Initialize the database
        dlBookReturnRepository.saveAndFlush(dlBookReturn);

        // Get all the dlBookReturns
        restDlBookReturnMockMvc.perform(get("/api/dlBookReturns"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlBookReturn.getId().intValue())))
                .andExpect(jsonPath("$.[*].issueId").value(hasItem(DEFAULT_ISSUE_ID)))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlBookReturn() throws Exception {
        // Initialize the database
        dlBookReturnRepository.saveAndFlush(dlBookReturn);

        // Get the dlBookReturn
        restDlBookReturnMockMvc.perform(get("/api/dlBookReturns/{id}", dlBookReturn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlBookReturn.getId().intValue()))
            .andExpect(jsonPath("$.issueId").value(DEFAULT_ISSUE_ID))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlBookReturn() throws Exception {
        // Get the dlBookReturn
        restDlBookReturnMockMvc.perform(get("/api/dlBookReturns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlBookReturn() throws Exception {
        // Initialize the database
        dlBookReturnRepository.saveAndFlush(dlBookReturn);

		int databaseSizeBeforeUpdate = dlBookReturnRepository.findAll().size();

        // Update the dlBookReturn
        dlBookReturn.setIssueId(UPDATED_ISSUE_ID);
        dlBookReturn.setCreatedDate(UPDATED_CREATED_DATE);
        dlBookReturn.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlBookReturn.setCreatedBy(UPDATED_CREATED_BY);
        dlBookReturn.setUpdatedBy(UPDATED_UPDATED_BY);
        dlBookReturn.setStatus(UPDATED_STATUS);

        restDlBookReturnMockMvc.perform(put("/api/dlBookReturns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookReturn)))
                .andExpect(status().isOk());

        // Validate the DlBookReturn in the database
        List<DlBookReturn> dlBookReturns = dlBookReturnRepository.findAll();
        assertThat(dlBookReturns).hasSize(databaseSizeBeforeUpdate);
        DlBookReturn testDlBookReturn = dlBookReturns.get(dlBookReturns.size() - 1);
        assertThat(testDlBookReturn.getIssueId()).isEqualTo(UPDATED_ISSUE_ID);
        assertThat(testDlBookReturn.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlBookReturn.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlBookReturn.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlBookReturn.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlBookReturn.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlBookReturn() throws Exception {
        // Initialize the database
        dlBookReturnRepository.saveAndFlush(dlBookReturn);

		int databaseSizeBeforeDelete = dlBookReturnRepository.findAll().size();

        // Get the dlBookReturn
        restDlBookReturnMockMvc.perform(delete("/api/dlBookReturns/{id}", dlBookReturn.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlBookReturn> dlBookReturns = dlBookReturnRepository.findAll();
        assertThat(dlBookReturns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
