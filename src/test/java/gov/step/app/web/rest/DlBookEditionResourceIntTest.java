package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlBookEdition;
import gov.step.app.repository.DlBookEditionRepository;
import gov.step.app.repository.search.DlBookEditionSearchRepository;

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
 * Test class for the DlBookEditionResource REST controller.
 *
 * @see DlBookEditionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlBookEditionResourceIntTest {

    private static final String DEFAULT_EDITION = "AAAAA";
    private static final String UPDATED_EDITION = "BBBBB";
    private static final String DEFAULT_TOTAL_COPIES = "AAAAA";
    private static final String UPDATED_TOTAL_COPIES = "BBBBB";
    private static final String DEFAULT_COMPENSATION = "AAAAA";
    private static final String UPDATED_COMPENSATION = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATE_BY = 1;
    private static final Integer UPDATED_CREATE_BY = 2;

    private static final Integer DEFAULT_UPDATE_BY = 1;
    private static final Integer UPDATED_UPDATE_BY = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private DlBookEditionRepository dlBookEditionRepository;

    @Inject
    private DlBookEditionSearchRepository dlBookEditionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlBookEditionMockMvc;

    private DlBookEdition dlBookEdition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlBookEditionResource dlBookEditionResource = new DlBookEditionResource();
        ReflectionTestUtils.setField(dlBookEditionResource, "dlBookEditionRepository", dlBookEditionRepository);
        ReflectionTestUtils.setField(dlBookEditionResource, "dlBookEditionSearchRepository", dlBookEditionSearchRepository);
        this.restDlBookEditionMockMvc = MockMvcBuilders.standaloneSetup(dlBookEditionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlBookEdition = new DlBookEdition();
        dlBookEdition.setEdition(DEFAULT_EDITION);
        dlBookEdition.setTotalCopies(DEFAULT_TOTAL_COPIES);
        dlBookEdition.setCompensation(DEFAULT_COMPENSATION);
        dlBookEdition.setCreateDate(DEFAULT_CREATE_DATE);
        dlBookEdition.setUpdateDate(DEFAULT_UPDATE_DATE);
        dlBookEdition.setCreateBy(DEFAULT_CREATE_BY);
        dlBookEdition.setUpdateBy(DEFAULT_UPDATE_BY);
        dlBookEdition.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlBookEdition() throws Exception {
        int databaseSizeBeforeCreate = dlBookEditionRepository.findAll().size();

        // Create the DlBookEdition

        restDlBookEditionMockMvc.perform(post("/api/dlBookEditions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookEdition)))
                .andExpect(status().isCreated());

        // Validate the DlBookEdition in the database
        List<DlBookEdition> dlBookEditions = dlBookEditionRepository.findAll();
        assertThat(dlBookEditions).hasSize(databaseSizeBeforeCreate + 1);
        DlBookEdition testDlBookEdition = dlBookEditions.get(dlBookEditions.size() - 1);
        assertThat(testDlBookEdition.getEdition()).isEqualTo(DEFAULT_EDITION);
        assertThat(testDlBookEdition.getTotalCopies()).isEqualTo(DEFAULT_TOTAL_COPIES);
        assertThat(testDlBookEdition.getCompensation()).isEqualTo(DEFAULT_COMPENSATION);
        assertThat(testDlBookEdition.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDlBookEdition.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testDlBookEdition.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testDlBookEdition.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testDlBookEdition.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlBookEditions() throws Exception {
        // Initialize the database
        dlBookEditionRepository.saveAndFlush(dlBookEdition);

        // Get all the dlBookEditions
        restDlBookEditionMockMvc.perform(get("/api/dlBookEditions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlBookEdition.getId().intValue())))
                .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION.toString())))
                .andExpect(jsonPath("$.[*].totalCopies").value(hasItem(DEFAULT_TOTAL_COPIES.toString())))
                .andExpect(jsonPath("$.[*].compensation").value(hasItem(DEFAULT_COMPENSATION.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlBookEdition() throws Exception {
        // Initialize the database
        dlBookEditionRepository.saveAndFlush(dlBookEdition);

        // Get the dlBookEdition
        restDlBookEditionMockMvc.perform(get("/api/dlBookEditions/{id}", dlBookEdition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlBookEdition.getId().intValue()))
            .andExpect(jsonPath("$.edition").value(DEFAULT_EDITION.toString()))
            .andExpect(jsonPath("$.totalCopies").value(DEFAULT_TOTAL_COPIES.toString()))
            .andExpect(jsonPath("$.compensation").value(DEFAULT_COMPENSATION.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlBookEdition() throws Exception {
        // Get the dlBookEdition
        restDlBookEditionMockMvc.perform(get("/api/dlBookEditions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlBookEdition() throws Exception {
        // Initialize the database
        dlBookEditionRepository.saveAndFlush(dlBookEdition);

		int databaseSizeBeforeUpdate = dlBookEditionRepository.findAll().size();

        // Update the dlBookEdition
        dlBookEdition.setEdition(UPDATED_EDITION);
        dlBookEdition.setTotalCopies(UPDATED_TOTAL_COPIES);
        dlBookEdition.setCompensation(UPDATED_COMPENSATION);
        dlBookEdition.setCreateDate(UPDATED_CREATE_DATE);
        dlBookEdition.setUpdateDate(UPDATED_UPDATE_DATE);
        dlBookEdition.setCreateBy(UPDATED_CREATE_BY);
        dlBookEdition.setUpdateBy(UPDATED_UPDATE_BY);
        dlBookEdition.setStatus(UPDATED_STATUS);

        restDlBookEditionMockMvc.perform(put("/api/dlBookEditions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookEdition)))
                .andExpect(status().isOk());

        // Validate the DlBookEdition in the database
        List<DlBookEdition> dlBookEditions = dlBookEditionRepository.findAll();
        assertThat(dlBookEditions).hasSize(databaseSizeBeforeUpdate);
        DlBookEdition testDlBookEdition = dlBookEditions.get(dlBookEditions.size() - 1);
        assertThat(testDlBookEdition.getEdition()).isEqualTo(UPDATED_EDITION);
        assertThat(testDlBookEdition.getTotalCopies()).isEqualTo(UPDATED_TOTAL_COPIES);
        assertThat(testDlBookEdition.getCompensation()).isEqualTo(UPDATED_COMPENSATION);
        assertThat(testDlBookEdition.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDlBookEdition.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testDlBookEdition.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testDlBookEdition.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testDlBookEdition.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlBookEdition() throws Exception {
        // Initialize the database
        dlBookEditionRepository.saveAndFlush(dlBookEdition);

		int databaseSizeBeforeDelete = dlBookEditionRepository.findAll().size();

        // Get the dlBookEdition
        restDlBookEditionMockMvc.perform(delete("/api/dlBookEditions/{id}", dlBookEdition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlBookEdition> dlBookEditions = dlBookEditionRepository.findAll();
        assertThat(dlBookEditions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
