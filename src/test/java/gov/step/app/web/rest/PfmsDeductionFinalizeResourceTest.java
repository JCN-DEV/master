package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PfmsDeductionFinalize;
import gov.step.app.repository.PfmsDeductionFinalizeRepository;
import gov.step.app.repository.search.PfmsDeductionFinalizeSearchRepository;

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
 * Test class for the PfmsDeductionFinalizeResource REST controller.
 *
 * @see PfmsDeductionFinalizeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PfmsDeductionFinalizeResourceTest {


    private static final Long DEFAULT_FINALIZE_YEAR = 1L;
    private static final Long UPDATED_FINALIZE_YEAR = 2L;
    private static final String DEFAULT_FINALIZE_MONTH = "AAAAA";
    private static final String UPDATED_FINALIZE_MONTH = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private PfmsDeductionFinalizeRepository pfmsDeductionFinalizeRepository;

    @Inject
    private PfmsDeductionFinalizeSearchRepository pfmsDeductionFinalizeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPfmsDeductionFinalizeMockMvc;

    private PfmsDeductionFinalize pfmsDeductionFinalize;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PfmsDeductionFinalizeResource pfmsDeductionFinalizeResource = new PfmsDeductionFinalizeResource();
        ReflectionTestUtils.setField(pfmsDeductionFinalizeResource, "pfmsDeductionFinalizeRepository", pfmsDeductionFinalizeRepository);
        ReflectionTestUtils.setField(pfmsDeductionFinalizeResource, "pfmsDeductionFinalizeSearchRepository", pfmsDeductionFinalizeSearchRepository);
        this.restPfmsDeductionFinalizeMockMvc = MockMvcBuilders.standaloneSetup(pfmsDeductionFinalizeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pfmsDeductionFinalize = new PfmsDeductionFinalize();
        pfmsDeductionFinalize.setFinalizeYear(DEFAULT_FINALIZE_YEAR);
        pfmsDeductionFinalize.setFinalizeMonth(DEFAULT_FINALIZE_MONTH);
        pfmsDeductionFinalize.setCreateDate(DEFAULT_CREATE_DATE);
        pfmsDeductionFinalize.setCreateBy(DEFAULT_CREATE_BY);
        pfmsDeductionFinalize.setUpdateDate(DEFAULT_UPDATE_DATE);
        pfmsDeductionFinalize.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPfmsDeductionFinalize() throws Exception {
        int databaseSizeBeforeCreate = pfmsDeductionFinalizeRepository.findAll().size();

        // Create the PfmsDeductionFinalize

        restPfmsDeductionFinalizeMockMvc.perform(post("/api/pfmsDeductionFinalizes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsDeductionFinalize)))
                .andExpect(status().isCreated());

        // Validate the PfmsDeductionFinalize in the database
        List<PfmsDeductionFinalize> pfmsDeductionFinalizes = pfmsDeductionFinalizeRepository.findAll();
        assertThat(pfmsDeductionFinalizes).hasSize(databaseSizeBeforeCreate + 1);
        PfmsDeductionFinalize testPfmsDeductionFinalize = pfmsDeductionFinalizes.get(pfmsDeductionFinalizes.size() - 1);
        assertThat(testPfmsDeductionFinalize.getFinalizeYear()).isEqualTo(DEFAULT_FINALIZE_YEAR);
        assertThat(testPfmsDeductionFinalize.getFinalizeMonth()).isEqualTo(DEFAULT_FINALIZE_MONTH);
        assertThat(testPfmsDeductionFinalize.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPfmsDeductionFinalize.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPfmsDeductionFinalize.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPfmsDeductionFinalize.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkFinalizeYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsDeductionFinalizeRepository.findAll().size();
        // set the field null
        pfmsDeductionFinalize.setFinalizeYear(null);

        // Create the PfmsDeductionFinalize, which fails.

        restPfmsDeductionFinalizeMockMvc.perform(post("/api/pfmsDeductionFinalizes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsDeductionFinalize)))
                .andExpect(status().isBadRequest());

        List<PfmsDeductionFinalize> pfmsDeductionFinalizes = pfmsDeductionFinalizeRepository.findAll();
        assertThat(pfmsDeductionFinalizes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinalizeMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsDeductionFinalizeRepository.findAll().size();
        // set the field null
        pfmsDeductionFinalize.setFinalizeMonth(null);

        // Create the PfmsDeductionFinalize, which fails.

        restPfmsDeductionFinalizeMockMvc.perform(post("/api/pfmsDeductionFinalizes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsDeductionFinalize)))
                .andExpect(status().isBadRequest());

        List<PfmsDeductionFinalize> pfmsDeductionFinalizes = pfmsDeductionFinalizeRepository.findAll();
        assertThat(pfmsDeductionFinalizes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPfmsDeductionFinalizes() throws Exception {
        // Initialize the database
        pfmsDeductionFinalizeRepository.saveAndFlush(pfmsDeductionFinalize);

        // Get all the pfmsDeductionFinalizes
        restPfmsDeductionFinalizeMockMvc.perform(get("/api/pfmsDeductionFinalizes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pfmsDeductionFinalize.getId().intValue())))
                .andExpect(jsonPath("$.[*].finalizeYear").value(hasItem(DEFAULT_FINALIZE_YEAR.intValue())))
                .andExpect(jsonPath("$.[*].finalizeMonth").value(hasItem(DEFAULT_FINALIZE_MONTH.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPfmsDeductionFinalize() throws Exception {
        // Initialize the database
        pfmsDeductionFinalizeRepository.saveAndFlush(pfmsDeductionFinalize);

        // Get the pfmsDeductionFinalize
        restPfmsDeductionFinalizeMockMvc.perform(get("/api/pfmsDeductionFinalizes/{id}", pfmsDeductionFinalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pfmsDeductionFinalize.getId().intValue()))
            .andExpect(jsonPath("$.finalizeYear").value(DEFAULT_FINALIZE_YEAR.intValue()))
            .andExpect(jsonPath("$.finalizeMonth").value(DEFAULT_FINALIZE_MONTH.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPfmsDeductionFinalize() throws Exception {
        // Get the pfmsDeductionFinalize
        restPfmsDeductionFinalizeMockMvc.perform(get("/api/pfmsDeductionFinalizes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePfmsDeductionFinalize() throws Exception {
        // Initialize the database
        pfmsDeductionFinalizeRepository.saveAndFlush(pfmsDeductionFinalize);

		int databaseSizeBeforeUpdate = pfmsDeductionFinalizeRepository.findAll().size();

        // Update the pfmsDeductionFinalize
        pfmsDeductionFinalize.setFinalizeYear(UPDATED_FINALIZE_YEAR);
        pfmsDeductionFinalize.setFinalizeMonth(UPDATED_FINALIZE_MONTH);
        pfmsDeductionFinalize.setCreateDate(UPDATED_CREATE_DATE);
        pfmsDeductionFinalize.setCreateBy(UPDATED_CREATE_BY);
        pfmsDeductionFinalize.setUpdateDate(UPDATED_UPDATE_DATE);
        pfmsDeductionFinalize.setUpdateBy(UPDATED_UPDATE_BY);

        restPfmsDeductionFinalizeMockMvc.perform(put("/api/pfmsDeductionFinalizes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsDeductionFinalize)))
                .andExpect(status().isOk());

        // Validate the PfmsDeductionFinalize in the database
        List<PfmsDeductionFinalize> pfmsDeductionFinalizes = pfmsDeductionFinalizeRepository.findAll();
        assertThat(pfmsDeductionFinalizes).hasSize(databaseSizeBeforeUpdate);
        PfmsDeductionFinalize testPfmsDeductionFinalize = pfmsDeductionFinalizes.get(pfmsDeductionFinalizes.size() - 1);
        assertThat(testPfmsDeductionFinalize.getFinalizeYear()).isEqualTo(UPDATED_FINALIZE_YEAR);
        assertThat(testPfmsDeductionFinalize.getFinalizeMonth()).isEqualTo(UPDATED_FINALIZE_MONTH);
        assertThat(testPfmsDeductionFinalize.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPfmsDeductionFinalize.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPfmsDeductionFinalize.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPfmsDeductionFinalize.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePfmsDeductionFinalize() throws Exception {
        // Initialize the database
        pfmsDeductionFinalizeRepository.saveAndFlush(pfmsDeductionFinalize);

		int databaseSizeBeforeDelete = pfmsDeductionFinalizeRepository.findAll().size();

        // Get the pfmsDeductionFinalize
        restPfmsDeductionFinalizeMockMvc.perform(delete("/api/pfmsDeductionFinalizes/{id}", pfmsDeductionFinalize.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PfmsDeductionFinalize> pfmsDeductionFinalizes = pfmsDeductionFinalizeRepository.findAll();
        assertThat(pfmsDeductionFinalizes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
