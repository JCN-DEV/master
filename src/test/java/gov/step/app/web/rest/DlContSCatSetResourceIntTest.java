package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlContSCatSet;
import gov.step.app.repository.DlContSCatSetRepository;
import gov.step.app.repository.search.DlContSCatSetSearchRepository;

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
 * Test class for the DlContSCatSetResource REST controller.
 *
 * @see DlContSCatSetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlContSCatSetResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_P_STATUS = false;
    private static final Boolean UPDATED_P_STATUS = true;

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
    private DlContSCatSetRepository dlContSCatSetRepository;

    @Inject
    private DlContSCatSetSearchRepository dlContSCatSetSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlContSCatSetMockMvc;

    private DlContSCatSet dlContSCatSet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlContSCatSetResource dlContSCatSetResource = new DlContSCatSetResource();
        ReflectionTestUtils.setField(dlContSCatSetResource, "dlContSCatSetRepository", dlContSCatSetRepository);
        ReflectionTestUtils.setField(dlContSCatSetResource, "dlContSCatSetSearchRepository", dlContSCatSetSearchRepository);
        this.restDlContSCatSetMockMvc = MockMvcBuilders.standaloneSetup(dlContSCatSetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlContSCatSet = new DlContSCatSet();
        dlContSCatSet.setCode(DEFAULT_CODE);
        dlContSCatSet.setName(DEFAULT_NAME);
        dlContSCatSet.setDescription(DEFAULT_DESCRIPTION);
        dlContSCatSet.setpStatus(DEFAULT_P_STATUS);
        dlContSCatSet.setCreatedDate(DEFAULT_CREATED_DATE);
        dlContSCatSet.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlContSCatSet.setCreatedBy(DEFAULT_CREATED_BY);
        dlContSCatSet.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlContSCatSet.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlContSCatSet() throws Exception {
        int databaseSizeBeforeCreate = dlContSCatSetRepository.findAll().size();

        // Create the DlContSCatSet

        restDlContSCatSetMockMvc.perform(post("/api/dlContSCatSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContSCatSet)))
                .andExpect(status().isCreated());

        // Validate the DlContSCatSet in the database
        List<DlContSCatSet> dlContSCatSets = dlContSCatSetRepository.findAll();
        assertThat(dlContSCatSets).hasSize(databaseSizeBeforeCreate + 1);
        DlContSCatSet testDlContSCatSet = dlContSCatSets.get(dlContSCatSets.size() - 1);
        assertThat(testDlContSCatSet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDlContSCatSet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDlContSCatSet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDlContSCatSet.getpStatus()).isEqualTo(DEFAULT_P_STATUS);
        assertThat(testDlContSCatSet.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlContSCatSet.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlContSCatSet.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlContSCatSet.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlContSCatSet.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlContSCatSets() throws Exception {
        // Initialize the database
        dlContSCatSetRepository.saveAndFlush(dlContSCatSet);

        // Get all the dlContSCatSets
        restDlContSCatSetMockMvc.perform(get("/api/dlContSCatSets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlContSCatSet.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].pStatus").value(hasItem(DEFAULT_P_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlContSCatSet() throws Exception {
        // Initialize the database
        dlContSCatSetRepository.saveAndFlush(dlContSCatSet);

        // Get the dlContSCatSet
        restDlContSCatSetMockMvc.perform(get("/api/dlContSCatSets/{id}", dlContSCatSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlContSCatSet.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.pStatus").value(DEFAULT_P_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlContSCatSet() throws Exception {
        // Get the dlContSCatSet
        restDlContSCatSetMockMvc.perform(get("/api/dlContSCatSets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlContSCatSet() throws Exception {
        // Initialize the database
        dlContSCatSetRepository.saveAndFlush(dlContSCatSet);

		int databaseSizeBeforeUpdate = dlContSCatSetRepository.findAll().size();

        // Update the dlContSCatSet
        dlContSCatSet.setCode(UPDATED_CODE);
        dlContSCatSet.setName(UPDATED_NAME);
        dlContSCatSet.setDescription(UPDATED_DESCRIPTION);
        dlContSCatSet.setpStatus(UPDATED_P_STATUS);
        dlContSCatSet.setCreatedDate(UPDATED_CREATED_DATE);
        dlContSCatSet.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlContSCatSet.setCreatedBy(UPDATED_CREATED_BY);
        dlContSCatSet.setUpdatedBy(UPDATED_UPDATED_BY);
        dlContSCatSet.setStatus(UPDATED_STATUS);

        restDlContSCatSetMockMvc.perform(put("/api/dlContSCatSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContSCatSet)))
                .andExpect(status().isOk());

        // Validate the DlContSCatSet in the database
        List<DlContSCatSet> dlContSCatSets = dlContSCatSetRepository.findAll();
        assertThat(dlContSCatSets).hasSize(databaseSizeBeforeUpdate);
        DlContSCatSet testDlContSCatSet = dlContSCatSets.get(dlContSCatSets.size() - 1);
        assertThat(testDlContSCatSet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDlContSCatSet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDlContSCatSet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDlContSCatSet.getpStatus()).isEqualTo(UPDATED_P_STATUS);
        assertThat(testDlContSCatSet.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlContSCatSet.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlContSCatSet.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlContSCatSet.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlContSCatSet.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlContSCatSet() throws Exception {
        // Initialize the database
        dlContSCatSetRepository.saveAndFlush(dlContSCatSet);

		int databaseSizeBeforeDelete = dlContSCatSetRepository.findAll().size();

        // Get the dlContSCatSet
        restDlContSCatSetMockMvc.perform(delete("/api/dlContSCatSets/{id}", dlContSCatSet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlContSCatSet> dlContSCatSets = dlContSCatSetRepository.findAll();
        assertThat(dlContSCatSets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
