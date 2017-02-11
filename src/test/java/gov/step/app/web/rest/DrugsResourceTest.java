package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Drugs;
import gov.step.app.repository.DrugsRepository;
import gov.step.app.repository.search.DrugsSearchRepository;

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
 * Test class for the DrugsResource REST controller.
 *
 * @see DrugsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DrugsResourceTest {

    private static final String DEFAULT_DRUGS_FOR = "AAAAA";
    private static final String UPDATED_DRUGS_FOR = "BBBBB";
    private static final String DEFAULT_DRUG_CLASS = "AAAAA";
    private static final String UPDATED_DRUG_CLASS = "BBBBB";
    private static final String DEFAULT_BRAND_NAME = "AAAAA";
    private static final String UPDATED_BRAND_NAME = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";
    private static final String DEFAULT_CONTAINS = "AAAAA";
    private static final String UPDATED_CONTAINS = "BBBBB";
    private static final String DEFAULT_DOSAGE_FORM = "AAAAA";
    private static final String UPDATED_DOSAGE_FORM = "BBBBB";
    private static final String DEFAULT_MANUFACTURER = "AAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBB";
    private static final String DEFAULT_PRICE = "AAAAA";
    private static final String UPDATED_PRICE = "BBBBB";
    private static final String DEFAULT_TYPES = "AAAAA";
    private static final String UPDATED_TYPES = "BBBBB";

    @Inject
    private DrugsRepository drugsRepository;

    @Inject
    private DrugsSearchRepository drugsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDrugsMockMvc;

    private Drugs drugs;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DrugsResource drugsResource = new DrugsResource();
        ReflectionTestUtils.setField(drugsResource, "drugsRepository", drugsRepository);
        ReflectionTestUtils.setField(drugsResource, "drugsSearchRepository", drugsSearchRepository);
        this.restDrugsMockMvc = MockMvcBuilders.standaloneSetup(drugsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        drugs = new Drugs();
        drugs.setDrugsFor(DEFAULT_DRUGS_FOR);
        drugs.setDrugClass(DEFAULT_DRUG_CLASS);
        drugs.setBrandName(DEFAULT_BRAND_NAME);
        drugs.setStatus(DEFAULT_STATUS);
        drugs.setCreateBy(DEFAULT_CREATE_BY);
        drugs.setCreateDate(DEFAULT_CREATE_DATE);
        drugs.setUpdateBy(DEFAULT_UPDATE_BY);
        drugs.setUpdateDate(DEFAULT_UPDATE_DATE);
        drugs.setRemarks(DEFAULT_REMARKS);
        drugs.setContains(DEFAULT_CONTAINS);
        drugs.setDosageForm(DEFAULT_DOSAGE_FORM);
        drugs.setManufacturer(DEFAULT_MANUFACTURER);
        drugs.setPrice(DEFAULT_PRICE);
        drugs.setTypes(DEFAULT_TYPES);
    }

    @Test
    @Transactional
    public void createDrugs() throws Exception {
        int databaseSizeBeforeCreate = drugsRepository.findAll().size();

        // Create the Drugs

        restDrugsMockMvc.perform(post("/api/drugss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(drugs)))
                .andExpect(status().isCreated());

        // Validate the Drugs in the database
        List<Drugs> drugss = drugsRepository.findAll();
        assertThat(drugss).hasSize(databaseSizeBeforeCreate + 1);
        Drugs testDrugs = drugss.get(drugss.size() - 1);
        assertThat(testDrugs.getDrugsFor()).isEqualTo(DEFAULT_DRUGS_FOR);
        assertThat(testDrugs.getDrugClass()).isEqualTo(DEFAULT_DRUG_CLASS);
        assertThat(testDrugs.getBrandName()).isEqualTo(DEFAULT_BRAND_NAME);
        assertThat(testDrugs.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDrugs.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testDrugs.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDrugs.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testDrugs.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testDrugs.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testDrugs.getContains()).isEqualTo(DEFAULT_CONTAINS);
        assertThat(testDrugs.getDosageForm()).isEqualTo(DEFAULT_DOSAGE_FORM);
        assertThat(testDrugs.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testDrugs.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testDrugs.getTypes()).isEqualTo(DEFAULT_TYPES);
    }

    @Test
    @Transactional
    public void getAllDrugss() throws Exception {
        // Initialize the database
        drugsRepository.saveAndFlush(drugs);

        // Get all the drugss
        restDrugsMockMvc.perform(get("/api/drugss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(drugs.getId().intValue())))
                .andExpect(jsonPath("$.[*].drugsFor").value(hasItem(DEFAULT_DRUGS_FOR.toString())))
                .andExpect(jsonPath("$.[*].drugClass").value(hasItem(DEFAULT_DRUG_CLASS.toString())))
                .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].contains").value(hasItem(DEFAULT_CONTAINS.toString())))
                .andExpect(jsonPath("$.[*].dosageForm").value(hasItem(DEFAULT_DOSAGE_FORM.toString())))
                .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.toString())))
                .andExpect(jsonPath("$.[*].types").value(hasItem(DEFAULT_TYPES.toString())));
    }

    @Test
    @Transactional
    public void getDrugs() throws Exception {
        // Initialize the database
        drugsRepository.saveAndFlush(drugs);

        // Get the drugs
        restDrugsMockMvc.perform(get("/api/drugss/{id}", drugs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(drugs.getId().intValue()))
            .andExpect(jsonPath("$.drugsFor").value(DEFAULT_DRUGS_FOR.toString()))
            .andExpect(jsonPath("$.drugClass").value(DEFAULT_DRUG_CLASS.toString()))
            .andExpect(jsonPath("$.brandName").value(DEFAULT_BRAND_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.contains").value(DEFAULT_CONTAINS.toString()))
            .andExpect(jsonPath("$.dosageForm").value(DEFAULT_DOSAGE_FORM.toString()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.toString()))
            .andExpect(jsonPath("$.types").value(DEFAULT_TYPES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDrugs() throws Exception {
        // Get the drugs
        restDrugsMockMvc.perform(get("/api/drugss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrugs() throws Exception {
        // Initialize the database
        drugsRepository.saveAndFlush(drugs);

		int databaseSizeBeforeUpdate = drugsRepository.findAll().size();

        // Update the drugs
        drugs.setDrugsFor(UPDATED_DRUGS_FOR);
        drugs.setDrugClass(UPDATED_DRUG_CLASS);
        drugs.setBrandName(UPDATED_BRAND_NAME);
        drugs.setStatus(UPDATED_STATUS);
        drugs.setCreateBy(UPDATED_CREATE_BY);
        drugs.setCreateDate(UPDATED_CREATE_DATE);
        drugs.setUpdateBy(UPDATED_UPDATE_BY);
        drugs.setUpdateDate(UPDATED_UPDATE_DATE);
        drugs.setRemarks(UPDATED_REMARKS);
        drugs.setContains(UPDATED_CONTAINS);
        drugs.setDosageForm(UPDATED_DOSAGE_FORM);
        drugs.setManufacturer(UPDATED_MANUFACTURER);
        drugs.setPrice(UPDATED_PRICE);
        drugs.setTypes(UPDATED_TYPES);

        restDrugsMockMvc.perform(put("/api/drugss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(drugs)))
                .andExpect(status().isOk());

        // Validate the Drugs in the database
        List<Drugs> drugss = drugsRepository.findAll();
        assertThat(drugss).hasSize(databaseSizeBeforeUpdate);
        Drugs testDrugs = drugss.get(drugss.size() - 1);
        assertThat(testDrugs.getDrugsFor()).isEqualTo(UPDATED_DRUGS_FOR);
        assertThat(testDrugs.getDrugClass()).isEqualTo(UPDATED_DRUG_CLASS);
        assertThat(testDrugs.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testDrugs.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDrugs.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testDrugs.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDrugs.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testDrugs.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testDrugs.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testDrugs.getContains()).isEqualTo(UPDATED_CONTAINS);
        assertThat(testDrugs.getDosageForm()).isEqualTo(UPDATED_DOSAGE_FORM);
        assertThat(testDrugs.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testDrugs.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDrugs.getTypes()).isEqualTo(UPDATED_TYPES);
    }

    @Test
    @Transactional
    public void deleteDrugs() throws Exception {
        // Initialize the database
        drugsRepository.saveAndFlush(drugs);

		int databaseSizeBeforeDelete = drugsRepository.findAll().size();

        // Get the drugs
        restDrugsMockMvc.perform(delete("/api/drugss/{id}", drugs.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Drugs> drugss = drugsRepository.findAll();
        assertThat(drugss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
