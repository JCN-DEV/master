package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlFineSetUp;
import gov.step.app.repository.DlFineSetUpRepository;
import gov.step.app.repository.search.DlFineSetUpSearchRepository;

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
 * Test class for the DlFineSetUpResource REST controller.
 *
 * @see DlFineSetUpResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlFineSetUpResourceIntTest {

    private static final String DEFAULT_TIME_LIMIT = "AAAAA";
    private static final String UPDATED_TIME_LIMIT = "BBBBB";

    private static final Integer DEFAULT_FINE = 1;
    private static final Integer UPDATED_FINE = 2;

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
    private DlFineSetUpRepository dlFineSetUpRepository;

    @Inject
    private DlFineSetUpSearchRepository dlFineSetUpSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlFineSetUpMockMvc;

    private DlFineSetUp dlFineSetUp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlFineSetUpResource dlFineSetUpResource = new DlFineSetUpResource();
        ReflectionTestUtils.setField(dlFineSetUpResource, "dlFineSetUpRepository", dlFineSetUpRepository);
        ReflectionTestUtils.setField(dlFineSetUpResource, "dlFineSetUpSearchRepository", dlFineSetUpSearchRepository);
        this.restDlFineSetUpMockMvc = MockMvcBuilders.standaloneSetup(dlFineSetUpResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlFineSetUp = new DlFineSetUp();
        dlFineSetUp.setTimeLimit(DEFAULT_TIME_LIMIT);
        dlFineSetUp.setFine(DEFAULT_FINE);
        dlFineSetUp.setStatus(DEFAULT_STATUS);
        dlFineSetUp.setCreateDate(DEFAULT_CREATE_DATE);
        dlFineSetUp.setCreateBy(DEFAULT_CREATE_BY);
        dlFineSetUp.setUpdateDate(DEFAULT_UPDATE_DATE);
        dlFineSetUp.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createDlFineSetUp() throws Exception {
        int databaseSizeBeforeCreate = dlFineSetUpRepository.findAll().size();

        // Create the DlFineSetUp

        restDlFineSetUpMockMvc.perform(post("/api/dlFineSetUps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlFineSetUp)))
                .andExpect(status().isCreated());

        // Validate the DlFineSetUp in the database
        List<DlFineSetUp> dlFineSetUps = dlFineSetUpRepository.findAll();
        assertThat(dlFineSetUps).hasSize(databaseSizeBeforeCreate + 1);
        DlFineSetUp testDlFineSetUp = dlFineSetUps.get(dlFineSetUps.size() - 1);
        assertThat(testDlFineSetUp.getTimeLimit()).isEqualTo(DEFAULT_TIME_LIMIT);
        assertThat(testDlFineSetUp.getFine()).isEqualTo(DEFAULT_FINE);
        assertThat(testDlFineSetUp.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDlFineSetUp.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDlFineSetUp.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testDlFineSetUp.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testDlFineSetUp.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllDlFineSetUps() throws Exception {
        // Initialize the database
        dlFineSetUpRepository.saveAndFlush(dlFineSetUp);

        // Get all the dlFineSetUps
        restDlFineSetUpMockMvc.perform(get("/api/dlFineSetUps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlFineSetUp.getId().intValue())))
                .andExpect(jsonPath("$.[*].timeLimit").value(hasItem(DEFAULT_TIME_LIMIT.toString())))
                .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getDlFineSetUp() throws Exception {
        // Initialize the database
        dlFineSetUpRepository.saveAndFlush(dlFineSetUp);

        // Get the dlFineSetUp
        restDlFineSetUpMockMvc.perform(get("/api/dlFineSetUps/{id}", dlFineSetUp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlFineSetUp.getId().intValue()))
            .andExpect(jsonPath("$.timeLimit").value(DEFAULT_TIME_LIMIT.toString()))
            .andExpect(jsonPath("$.fine").value(DEFAULT_FINE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDlFineSetUp() throws Exception {
        // Get the dlFineSetUp
        restDlFineSetUpMockMvc.perform(get("/api/dlFineSetUps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlFineSetUp() throws Exception {
        // Initialize the database
        dlFineSetUpRepository.saveAndFlush(dlFineSetUp);

		int databaseSizeBeforeUpdate = dlFineSetUpRepository.findAll().size();

        // Update the dlFineSetUp
        dlFineSetUp.setTimeLimit(UPDATED_TIME_LIMIT);
        dlFineSetUp.setFine(UPDATED_FINE);
        dlFineSetUp.setStatus(UPDATED_STATUS);
        dlFineSetUp.setCreateDate(UPDATED_CREATE_DATE);
        dlFineSetUp.setCreateBy(UPDATED_CREATE_BY);
        dlFineSetUp.setUpdateDate(UPDATED_UPDATE_DATE);
        dlFineSetUp.setUpdateBy(UPDATED_UPDATE_BY);

        restDlFineSetUpMockMvc.perform(put("/api/dlFineSetUps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlFineSetUp)))
                .andExpect(status().isOk());

        // Validate the DlFineSetUp in the database
        List<DlFineSetUp> dlFineSetUps = dlFineSetUpRepository.findAll();
        assertThat(dlFineSetUps).hasSize(databaseSizeBeforeUpdate);
        DlFineSetUp testDlFineSetUp = dlFineSetUps.get(dlFineSetUps.size() - 1);
        assertThat(testDlFineSetUp.getTimeLimit()).isEqualTo(UPDATED_TIME_LIMIT);
        assertThat(testDlFineSetUp.getFine()).isEqualTo(UPDATED_FINE);
        assertThat(testDlFineSetUp.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDlFineSetUp.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDlFineSetUp.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testDlFineSetUp.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testDlFineSetUp.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteDlFineSetUp() throws Exception {
        // Initialize the database
        dlFineSetUpRepository.saveAndFlush(dlFineSetUp);

		int databaseSizeBeforeDelete = dlFineSetUpRepository.findAll().size();

        // Get the dlFineSetUp
        restDlFineSetUpMockMvc.perform(delete("/api/dlFineSetUps/{id}", dlFineSetUp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlFineSetUp> dlFineSetUps = dlFineSetUpRepository.findAll();
        assertThat(dlFineSetUps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
