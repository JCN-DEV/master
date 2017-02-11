package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlContCatSet;
import gov.step.app.repository.DlContCatSetRepository;
import gov.step.app.repository.search.DlContCatSetSearchRepository;

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
 * Test class for the DlContCatSetResource REST controller.
 *
 * @see DlContCatSetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlContCatSetResourceIntTest {

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
    private DlContCatSetRepository dlContCatSetRepository;

    @Inject
    private DlContCatSetSearchRepository dlContCatSetSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlContCatSetMockMvc;

    private DlContCatSet dlContCatSet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlContCatSetResource dlContCatSetResource = new DlContCatSetResource();
        ReflectionTestUtils.setField(dlContCatSetResource, "dlContCatSetRepository", dlContCatSetRepository);
        ReflectionTestUtils.setField(dlContCatSetResource, "dlContCatSetSearchRepository", dlContCatSetSearchRepository);
        this.restDlContCatSetMockMvc = MockMvcBuilders.standaloneSetup(dlContCatSetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlContCatSet = new DlContCatSet();
        dlContCatSet.setCode(DEFAULT_CODE);
        dlContCatSet.setName(DEFAULT_NAME);
        dlContCatSet.setDescription(DEFAULT_DESCRIPTION);
        dlContCatSet.setpStatus(DEFAULT_P_STATUS);
        dlContCatSet.setCreatedDate(DEFAULT_CREATED_DATE);
        dlContCatSet.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlContCatSet.setCreatedBy(DEFAULT_CREATED_BY);
        dlContCatSet.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlContCatSet.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlContCatSet() throws Exception {
        int databaseSizeBeforeCreate = dlContCatSetRepository.findAll().size();

        // Create the DlContCatSet

        restDlContCatSetMockMvc.perform(post("/api/dlContCatSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContCatSet)))
                .andExpect(status().isCreated());

        // Validate the DlContCatSet in the database
        List<DlContCatSet> dlContCatSets = dlContCatSetRepository.findAll();
        assertThat(dlContCatSets).hasSize(databaseSizeBeforeCreate + 1);
        DlContCatSet testDlContCatSet = dlContCatSets.get(dlContCatSets.size() - 1);
        assertThat(testDlContCatSet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDlContCatSet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDlContCatSet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDlContCatSet.getpStatus()).isEqualTo(DEFAULT_P_STATUS);
        assertThat(testDlContCatSet.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlContCatSet.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlContCatSet.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlContCatSet.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlContCatSet.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlContCatSets() throws Exception {
        // Initialize the database
        dlContCatSetRepository.saveAndFlush(dlContCatSet);

        // Get all the dlContCatSets
        restDlContCatSetMockMvc.perform(get("/api/dlContCatSets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlContCatSet.getId().intValue())))
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
    public void getDlContCatSet() throws Exception {
        // Initialize the database
        dlContCatSetRepository.saveAndFlush(dlContCatSet);

        // Get the dlContCatSet
        restDlContCatSetMockMvc.perform(get("/api/dlContCatSets/{id}", dlContCatSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlContCatSet.getId().intValue()))
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
    public void getNonExistingDlContCatSet() throws Exception {
        // Get the dlContCatSet
        restDlContCatSetMockMvc.perform(get("/api/dlContCatSets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlContCatSet() throws Exception {
        // Initialize the database
        dlContCatSetRepository.saveAndFlush(dlContCatSet);

		int databaseSizeBeforeUpdate = dlContCatSetRepository.findAll().size();

        // Update the dlContCatSet
        dlContCatSet.setCode(UPDATED_CODE);
        dlContCatSet.setName(UPDATED_NAME);
        dlContCatSet.setDescription(UPDATED_DESCRIPTION);
        dlContCatSet.setpStatus(UPDATED_P_STATUS);
        dlContCatSet.setCreatedDate(UPDATED_CREATED_DATE);
        dlContCatSet.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlContCatSet.setCreatedBy(UPDATED_CREATED_BY);
        dlContCatSet.setUpdatedBy(UPDATED_UPDATED_BY);
        dlContCatSet.setStatus(UPDATED_STATUS);

        restDlContCatSetMockMvc.perform(put("/api/dlContCatSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContCatSet)))
                .andExpect(status().isOk());

        // Validate the DlContCatSet in the database
        List<DlContCatSet> dlContCatSets = dlContCatSetRepository.findAll();
        assertThat(dlContCatSets).hasSize(databaseSizeBeforeUpdate);
        DlContCatSet testDlContCatSet = dlContCatSets.get(dlContCatSets.size() - 1);
        assertThat(testDlContCatSet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDlContCatSet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDlContCatSet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDlContCatSet.getpStatus()).isEqualTo(UPDATED_P_STATUS);
        assertThat(testDlContCatSet.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlContCatSet.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlContCatSet.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlContCatSet.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlContCatSet.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlContCatSet() throws Exception {
        // Initialize the database
        dlContCatSetRepository.saveAndFlush(dlContCatSet);

		int databaseSizeBeforeDelete = dlContCatSetRepository.findAll().size();

        // Get the dlContCatSet
        restDlContCatSetMockMvc.perform(delete("/api/dlContCatSets/{id}", dlContCatSet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlContCatSet> dlContCatSets = dlContCatSetRepository.findAll();
        assertThat(dlContCatSets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
