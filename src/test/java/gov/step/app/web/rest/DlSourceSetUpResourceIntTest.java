package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlSourceSetUp;
import gov.step.app.repository.DlSourceSetUpRepository;
import gov.step.app.repository.search.DlSourceSetUpSearchRepository;

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
 * Test class for the DlSourceSetUpResource REST controller.
 *
 * @see DlSourceSetUpResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlSourceSetUpResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

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
    private DlSourceSetUpRepository dlSourceSetUpRepository;

    @Inject
    private DlSourceSetUpSearchRepository dlSourceSetUpSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlSourceSetUpMockMvc;

    private DlSourceSetUp dlSourceSetUp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlSourceSetUpResource dlSourceSetUpResource = new DlSourceSetUpResource();
        ReflectionTestUtils.setField(dlSourceSetUpResource, "dlSourceSetUpRepository", dlSourceSetUpRepository);
        ReflectionTestUtils.setField(dlSourceSetUpResource, "dlSourceSetUpSearchRepository", dlSourceSetUpSearchRepository);
        this.restDlSourceSetUpMockMvc = MockMvcBuilders.standaloneSetup(dlSourceSetUpResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlSourceSetUp = new DlSourceSetUp();
        dlSourceSetUp.setName(DEFAULT_NAME);
        dlSourceSetUp.setStatus(DEFAULT_STATUS);
        dlSourceSetUp.setCreateDate(DEFAULT_CREATE_DATE);
        dlSourceSetUp.setCreateBy(DEFAULT_CREATE_BY);
        dlSourceSetUp.setUpdateDate(DEFAULT_UPDATE_DATE);
        dlSourceSetUp.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createDlSourceSetUp() throws Exception {
        int databaseSizeBeforeCreate = dlSourceSetUpRepository.findAll().size();

        // Create the DlSourceSetUp

        restDlSourceSetUpMockMvc.perform(post("/api/dlSourceSetUps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlSourceSetUp)))
                .andExpect(status().isCreated());

        // Validate the DlSourceSetUp in the database
        List<DlSourceSetUp> dlSourceSetUps = dlSourceSetUpRepository.findAll();
        assertThat(dlSourceSetUps).hasSize(databaseSizeBeforeCreate + 1);
        DlSourceSetUp testDlSourceSetUp = dlSourceSetUps.get(dlSourceSetUps.size() - 1);
        assertThat(testDlSourceSetUp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDlSourceSetUp.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDlSourceSetUp.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDlSourceSetUp.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testDlSourceSetUp.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testDlSourceSetUp.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllDlSourceSetUps() throws Exception {
        // Initialize the database
        dlSourceSetUpRepository.saveAndFlush(dlSourceSetUp);

        // Get all the dlSourceSetUps
        restDlSourceSetUpMockMvc.perform(get("/api/dlSourceSetUps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlSourceSetUp.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getDlSourceSetUp() throws Exception {
        // Initialize the database
        dlSourceSetUpRepository.saveAndFlush(dlSourceSetUp);

        // Get the dlSourceSetUp
        restDlSourceSetUpMockMvc.perform(get("/api/dlSourceSetUps/{id}", dlSourceSetUp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlSourceSetUp.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fine").value(DEFAULT_FINE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDlSourceSetUp() throws Exception {
        // Get the dlSourceSetUp
        restDlSourceSetUpMockMvc.perform(get("/api/dlSourceSetUps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlSourceSetUp() throws Exception {
        // Initialize the database
        dlSourceSetUpRepository.saveAndFlush(dlSourceSetUp);

		int databaseSizeBeforeUpdate = dlSourceSetUpRepository.findAll().size();

        // Update the dlSourceSetUp
        dlSourceSetUp.setName(UPDATED_NAME);
        dlSourceSetUp.setStatus(UPDATED_STATUS);
        dlSourceSetUp.setCreateDate(UPDATED_CREATE_DATE);
        dlSourceSetUp.setCreateBy(UPDATED_CREATE_BY);
        dlSourceSetUp.setUpdateDate(UPDATED_UPDATE_DATE);
        dlSourceSetUp.setUpdateBy(UPDATED_UPDATE_BY);

        restDlSourceSetUpMockMvc.perform(put("/api/dlSourceSetUps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlSourceSetUp)))
                .andExpect(status().isOk());

        // Validate the DlSourceSetUp in the database
        List<DlSourceSetUp> dlSourceSetUps = dlSourceSetUpRepository.findAll();
        assertThat(dlSourceSetUps).hasSize(databaseSizeBeforeUpdate);
        DlSourceSetUp testDlSourceSetUp = dlSourceSetUps.get(dlSourceSetUps.size() - 1);
        assertThat(testDlSourceSetUp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDlSourceSetUp.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDlSourceSetUp.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDlSourceSetUp.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testDlSourceSetUp.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testDlSourceSetUp.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteDlSourceSetUp() throws Exception {
        // Initialize the database
        dlSourceSetUpRepository.saveAndFlush(dlSourceSetUp);

		int databaseSizeBeforeDelete = dlSourceSetUpRepository.findAll().size();

        // Get the dlSourceSetUp
        restDlSourceSetUpMockMvc.perform(delete("/api/dlSourceSetUps/{id}", dlSourceSetUp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlSourceSetUp> dlSourceSetUps = dlSourceSetUpRepository.findAll();
        assertThat(dlSourceSetUps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
