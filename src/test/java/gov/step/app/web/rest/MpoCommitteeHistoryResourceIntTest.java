package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoCommitteeHistory;
import gov.step.app.repository.MpoCommitteeHistoryRepository;
import gov.step.app.repository.search.MpoCommitteeHistorySearchRepository;

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
 * Test class for the MpoCommitteeHistoryResource REST controller.
 *
 * @see MpoCommitteeHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoCommitteeHistoryResourceIntTest {


    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final LocalDate DEFAULT_DATE_CRATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CRATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private MpoCommitteeHistoryRepository mpoCommitteeHistoryRepository;

    @Inject
    private MpoCommitteeHistorySearchRepository mpoCommitteeHistorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoCommitteeHistoryMockMvc;

    private MpoCommitteeHistory mpoCommitteeHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoCommitteeHistoryResource mpoCommitteeHistoryResource = new MpoCommitteeHistoryResource();
        ReflectionTestUtils.setField(mpoCommitteeHistoryResource, "mpoCommitteeHistoryRepository", mpoCommitteeHistoryRepository);
        ReflectionTestUtils.setField(mpoCommitteeHistoryResource, "mpoCommitteeHistorySearchRepository", mpoCommitteeHistorySearchRepository);
        this.restMpoCommitteeHistoryMockMvc = MockMvcBuilders.standaloneSetup(mpoCommitteeHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoCommitteeHistory = new MpoCommitteeHistory();
        mpoCommitteeHistory.setMonth(DEFAULT_MONTH);
        mpoCommitteeHistory.setYear(DEFAULT_YEAR);
        mpoCommitteeHistory.setDateCrated(DEFAULT_DATE_CRATED);
        mpoCommitteeHistory.setDateModified(DEFAULT_DATE_MODIFIED);
        mpoCommitteeHistory.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createMpoCommitteeHistory() throws Exception {
        int databaseSizeBeforeCreate = mpoCommitteeHistoryRepository.findAll().size();

        // Create the MpoCommitteeHistory

        restMpoCommitteeHistoryMockMvc.perform(post("/api/mpoCommitteeHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoCommitteeHistory)))
                .andExpect(status().isCreated());

        // Validate the MpoCommitteeHistory in the database
        List<MpoCommitteeHistory> mpoCommitteeHistorys = mpoCommitteeHistoryRepository.findAll();
        assertThat(mpoCommitteeHistorys).hasSize(databaseSizeBeforeCreate + 1);
        MpoCommitteeHistory testMpoCommitteeHistory = mpoCommitteeHistorys.get(mpoCommitteeHistorys.size() - 1);
        assertThat(testMpoCommitteeHistory.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testMpoCommitteeHistory.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testMpoCommitteeHistory.getDateCrated()).isEqualTo(DEFAULT_DATE_CRATED);
        assertThat(testMpoCommitteeHistory.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testMpoCommitteeHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = mpoCommitteeHistoryRepository.findAll().size();
        // set the field null
        mpoCommitteeHistory.setMonth(null);

        // Create the MpoCommitteeHistory, which fails.

        restMpoCommitteeHistoryMockMvc.perform(post("/api/mpoCommitteeHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoCommitteeHistory)))
                .andExpect(status().isBadRequest());

        List<MpoCommitteeHistory> mpoCommitteeHistorys = mpoCommitteeHistoryRepository.findAll();
        assertThat(mpoCommitteeHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = mpoCommitteeHistoryRepository.findAll().size();
        // set the field null
        mpoCommitteeHistory.setYear(null);

        // Create the MpoCommitteeHistory, which fails.

        restMpoCommitteeHistoryMockMvc.perform(post("/api/mpoCommitteeHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoCommitteeHistory)))
                .andExpect(status().isBadRequest());

        List<MpoCommitteeHistory> mpoCommitteeHistorys = mpoCommitteeHistoryRepository.findAll();
        assertThat(mpoCommitteeHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMpoCommitteeHistorys() throws Exception {
        // Initialize the database
        mpoCommitteeHistoryRepository.saveAndFlush(mpoCommitteeHistory);

        // Get all the mpoCommitteeHistorys
        restMpoCommitteeHistoryMockMvc.perform(get("/api/mpoCommitteeHistorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoCommitteeHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
                .andExpect(jsonPath("$.[*].dateCrated").value(hasItem(DEFAULT_DATE_CRATED.toString())))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getMpoCommitteeHistory() throws Exception {
        // Initialize the database
        mpoCommitteeHistoryRepository.saveAndFlush(mpoCommitteeHistory);

        // Get the mpoCommitteeHistory
        restMpoCommitteeHistoryMockMvc.perform(get("/api/mpoCommitteeHistorys/{id}", mpoCommitteeHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoCommitteeHistory.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.dateCrated").value(DEFAULT_DATE_CRATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingMpoCommitteeHistory() throws Exception {
        // Get the mpoCommitteeHistory
        restMpoCommitteeHistoryMockMvc.perform(get("/api/mpoCommitteeHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoCommitteeHistory() throws Exception {
        // Initialize the database
        mpoCommitteeHistoryRepository.saveAndFlush(mpoCommitteeHistory);

		int databaseSizeBeforeUpdate = mpoCommitteeHistoryRepository.findAll().size();

        // Update the mpoCommitteeHistory
        mpoCommitteeHistory.setMonth(UPDATED_MONTH);
        mpoCommitteeHistory.setYear(UPDATED_YEAR);
        mpoCommitteeHistory.setDateCrated(UPDATED_DATE_CRATED);
        mpoCommitteeHistory.setDateModified(UPDATED_DATE_MODIFIED);
        mpoCommitteeHistory.setStatus(UPDATED_STATUS);

        restMpoCommitteeHistoryMockMvc.perform(put("/api/mpoCommitteeHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoCommitteeHistory)))
                .andExpect(status().isOk());

        // Validate the MpoCommitteeHistory in the database
        List<MpoCommitteeHistory> mpoCommitteeHistorys = mpoCommitteeHistoryRepository.findAll();
        assertThat(mpoCommitteeHistorys).hasSize(databaseSizeBeforeUpdate);
        MpoCommitteeHistory testMpoCommitteeHistory = mpoCommitteeHistorys.get(mpoCommitteeHistorys.size() - 1);
        assertThat(testMpoCommitteeHistory.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testMpoCommitteeHistory.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMpoCommitteeHistory.getDateCrated()).isEqualTo(UPDATED_DATE_CRATED);
        assertThat(testMpoCommitteeHistory.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testMpoCommitteeHistory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteMpoCommitteeHistory() throws Exception {
        // Initialize the database
        mpoCommitteeHistoryRepository.saveAndFlush(mpoCommitteeHistory);

		int databaseSizeBeforeDelete = mpoCommitteeHistoryRepository.findAll().size();

        // Get the mpoCommitteeHistory
        restMpoCommitteeHistoryMockMvc.perform(delete("/api/mpoCommitteeHistorys/{id}", mpoCommitteeHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoCommitteeHistory> mpoCommitteeHistorys = mpoCommitteeHistoryRepository.findAll();
        assertThat(mpoCommitteeHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
