package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlContTypeSet;
import gov.step.app.repository.DlContTypeSetRepository;
import gov.step.app.repository.search.DlContTypeSetSearchRepository;

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
 * Test class for the DlContTypeSetResource REST controller.
 *
 * @see DlContTypeSetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlContTypeSetResourceIntTest {

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
    private DlContTypeSetRepository dlContTypeSetRepository;

    @Inject
    private DlContTypeSetSearchRepository dlContTypeSetSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlContTypeSetMockMvc;

    private DlContTypeSet dlContTypeSet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlContTypeSetResource dlContTypeSetResource = new DlContTypeSetResource();
        ReflectionTestUtils.setField(dlContTypeSetResource, "dlContTypeSetRepository", dlContTypeSetRepository);
        ReflectionTestUtils.setField(dlContTypeSetResource, "dlContTypeSetSearchRepository", dlContTypeSetSearchRepository);
        this.restDlContTypeSetMockMvc = MockMvcBuilders.standaloneSetup(dlContTypeSetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlContTypeSet = new DlContTypeSet();
        dlContTypeSet.setCode(DEFAULT_CODE);
        dlContTypeSet.setName(DEFAULT_NAME);
        dlContTypeSet.setDescription(DEFAULT_DESCRIPTION);
        dlContTypeSet.setpStatus(DEFAULT_P_STATUS);
        dlContTypeSet.setCreatedDate(DEFAULT_CREATED_DATE);
        dlContTypeSet.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlContTypeSet.setCreatedBy(DEFAULT_CREATED_BY);
        dlContTypeSet.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlContTypeSet.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlContTypeSet() throws Exception {
        int databaseSizeBeforeCreate = dlContTypeSetRepository.findAll().size();

        // Create the DlContTypeSet

        restDlContTypeSetMockMvc.perform(post("/api/dlContTypeSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContTypeSet)))
                .andExpect(status().isCreated());

        // Validate the DlContTypeSet in the database
        List<DlContTypeSet> dlContTypeSets = dlContTypeSetRepository.findAll();
        assertThat(dlContTypeSets).hasSize(databaseSizeBeforeCreate + 1);
        DlContTypeSet testDlContTypeSet = dlContTypeSets.get(dlContTypeSets.size() - 1);
        assertThat(testDlContTypeSet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDlContTypeSet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDlContTypeSet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDlContTypeSet.getpStatus()).isEqualTo(DEFAULT_P_STATUS);
        assertThat(testDlContTypeSet.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlContTypeSet.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlContTypeSet.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlContTypeSet.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlContTypeSet.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlContTypeSets() throws Exception {
        // Initialize the database
        dlContTypeSetRepository.saveAndFlush(dlContTypeSet);

        // Get all the dlContTypeSets
        restDlContTypeSetMockMvc.perform(get("/api/dlContTypeSets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlContTypeSet.getId().intValue())))
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
    public void getDlContTypeSet() throws Exception {
        // Initialize the database
        dlContTypeSetRepository.saveAndFlush(dlContTypeSet);

        // Get the dlContTypeSet
        restDlContTypeSetMockMvc.perform(get("/api/dlContTypeSets/{id}", dlContTypeSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlContTypeSet.getId().intValue()))
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
    public void getNonExistingDlContTypeSet() throws Exception {
        // Get the dlContTypeSet
        restDlContTypeSetMockMvc.perform(get("/api/dlContTypeSets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlContTypeSet() throws Exception {
        // Initialize the database
        dlContTypeSetRepository.saveAndFlush(dlContTypeSet);

		int databaseSizeBeforeUpdate = dlContTypeSetRepository.findAll().size();

        // Update the dlContTypeSet
        dlContTypeSet.setCode(UPDATED_CODE);
        dlContTypeSet.setName(UPDATED_NAME);
        dlContTypeSet.setDescription(UPDATED_DESCRIPTION);
        dlContTypeSet.setpStatus(UPDATED_P_STATUS);
        dlContTypeSet.setCreatedDate(UPDATED_CREATED_DATE);
        dlContTypeSet.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlContTypeSet.setCreatedBy(UPDATED_CREATED_BY);
        dlContTypeSet.setUpdatedBy(UPDATED_UPDATED_BY);
        dlContTypeSet.setStatus(UPDATED_STATUS);

        restDlContTypeSetMockMvc.perform(put("/api/dlContTypeSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContTypeSet)))
                .andExpect(status().isOk());

        // Validate the DlContTypeSet in the database
        List<DlContTypeSet> dlContTypeSets = dlContTypeSetRepository.findAll();
        assertThat(dlContTypeSets).hasSize(databaseSizeBeforeUpdate);
        DlContTypeSet testDlContTypeSet = dlContTypeSets.get(dlContTypeSets.size() - 1);
        assertThat(testDlContTypeSet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDlContTypeSet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDlContTypeSet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDlContTypeSet.getpStatus()).isEqualTo(UPDATED_P_STATUS);
        assertThat(testDlContTypeSet.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlContTypeSet.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlContTypeSet.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlContTypeSet.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlContTypeSet.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlContTypeSet() throws Exception {
        // Initialize the database
        dlContTypeSetRepository.saveAndFlush(dlContTypeSet);

		int databaseSizeBeforeDelete = dlContTypeSetRepository.findAll().size();

        // Get the dlContTypeSet
        restDlContTypeSetMockMvc.perform(delete("/api/dlContTypeSets/{id}", dlContTypeSet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlContTypeSet> dlContTypeSets = dlContTypeSetRepository.findAll();
        assertThat(dlContTypeSets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
