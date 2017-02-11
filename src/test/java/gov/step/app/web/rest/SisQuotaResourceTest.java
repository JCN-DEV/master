package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SisQuota;
import gov.step.app.repository.SisQuotaRepository;
import gov.step.app.repository.search.SisQuotaSearchRepository;

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
 * Test class for the SisQuotaResource REST controller.
 *
 * @see SisQuotaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SisQuotaResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_IS_FOR = false;
    private static final Boolean UPDATED_IS_FOR = true;

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private SisQuotaRepository sisQuotaRepository;

    @Inject
    private SisQuotaSearchRepository sisQuotaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSisQuotaMockMvc;

    private SisQuota sisQuota;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SisQuotaResource sisQuotaResource = new SisQuotaResource();
        ReflectionTestUtils.setField(sisQuotaResource, "sisQuotaRepository", sisQuotaRepository);
        ReflectionTestUtils.setField(sisQuotaResource, "sisQuotaSearchRepository", sisQuotaSearchRepository);
        this.restSisQuotaMockMvc = MockMvcBuilders.standaloneSetup(sisQuotaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        sisQuota = new SisQuota();
        sisQuota.setName(DEFAULT_NAME);
        sisQuota.setDescription(DEFAULT_DESCRIPTION);
        sisQuota.setIsFor(DEFAULT_IS_FOR);
        sisQuota.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        sisQuota.setCreateDate(DEFAULT_CREATE_DATE);
        sisQuota.setCreateBy(DEFAULT_CREATE_BY);
        sisQuota.setUpdateDate(DEFAULT_UPDATE_DATE);
        sisQuota.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createSisQuota() throws Exception {
        int databaseSizeBeforeCreate = sisQuotaRepository.findAll().size();

        // Create the SisQuota

        restSisQuotaMockMvc.perform(post("/api/sisQuotas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisQuota)))
                .andExpect(status().isCreated());

        // Validate the SisQuota in the database
        List<SisQuota> sisQuotas = sisQuotaRepository.findAll();
        assertThat(sisQuotas).hasSize(databaseSizeBeforeCreate + 1);
        SisQuota testSisQuota = sisQuotas.get(sisQuotas.size() - 1);
        assertThat(testSisQuota.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSisQuota.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSisQuota.getIsFor()).isEqualTo(DEFAULT_IS_FOR);
        assertThat(testSisQuota.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testSisQuota.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testSisQuota.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testSisQuota.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testSisQuota.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sisQuotaRepository.findAll().size();
        // set the field null
        sisQuota.setName(null);

        // Create the SisQuota, which fails.

        restSisQuotaMockMvc.perform(post("/api/sisQuotas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisQuota)))
                .andExpect(status().isBadRequest());

        List<SisQuota> sisQuotas = sisQuotaRepository.findAll();
        assertThat(sisQuotas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSisQuotas() throws Exception {
        // Initialize the database
        sisQuotaRepository.saveAndFlush(sisQuota);

        // Get all the sisQuotas
        restSisQuotaMockMvc.perform(get("/api/sisQuotas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sisQuota.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].isFor").value(hasItem(DEFAULT_IS_FOR.booleanValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getSisQuota() throws Exception {
        // Initialize the database
        sisQuotaRepository.saveAndFlush(sisQuota);

        // Get the sisQuota
        restSisQuotaMockMvc.perform(get("/api/sisQuotas/{id}", sisQuota.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sisQuota.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isFor").value(DEFAULT_IS_FOR.booleanValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSisQuota() throws Exception {
        // Get the sisQuota
        restSisQuotaMockMvc.perform(get("/api/sisQuotas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSisQuota() throws Exception {
        // Initialize the database
        sisQuotaRepository.saveAndFlush(sisQuota);

		int databaseSizeBeforeUpdate = sisQuotaRepository.findAll().size();

        // Update the sisQuota
        sisQuota.setName(UPDATED_NAME);
        sisQuota.setDescription(UPDATED_DESCRIPTION);
        sisQuota.setIsFor(UPDATED_IS_FOR);
        sisQuota.setActiveStatus(UPDATED_ACTIVE_STATUS);
        sisQuota.setCreateDate(UPDATED_CREATE_DATE);
        sisQuota.setCreateBy(UPDATED_CREATE_BY);
        sisQuota.setUpdateDate(UPDATED_UPDATE_DATE);
        sisQuota.setUpdateBy(UPDATED_UPDATE_BY);

        restSisQuotaMockMvc.perform(put("/api/sisQuotas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisQuota)))
                .andExpect(status().isOk());

        // Validate the SisQuota in the database
        List<SisQuota> sisQuotas = sisQuotaRepository.findAll();
        assertThat(sisQuotas).hasSize(databaseSizeBeforeUpdate);
        SisQuota testSisQuota = sisQuotas.get(sisQuotas.size() - 1);
        assertThat(testSisQuota.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSisQuota.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSisQuota.getIsFor()).isEqualTo(UPDATED_IS_FOR);
        assertThat(testSisQuota.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testSisQuota.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testSisQuota.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testSisQuota.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testSisQuota.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteSisQuota() throws Exception {
        // Initialize the database
        sisQuotaRepository.saveAndFlush(sisQuota);

		int databaseSizeBeforeDelete = sisQuotaRepository.findAll().size();

        // Get the sisQuota
        restSisQuotaMockMvc.perform(delete("/api/sisQuotas/{id}", sisQuota.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SisQuota> sisQuotas = sisQuotaRepository.findAll();
        assertThat(sisQuotas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
