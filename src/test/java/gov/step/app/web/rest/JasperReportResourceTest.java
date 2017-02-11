package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JasperReport;
import gov.step.app.repository.JasperReportRepository;
import gov.step.app.repository.search.JasperReportSearchRepository;

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
 * Test class for the JasperReportResource REST controller.
 *
 * @see JasperReportResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JasperReportResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_PATH = "AAAAA";
    private static final String UPDATED_PATH = "BBBBB";
    private static final String DEFAULT_MODULE = "AAAAA";
    private static final String UPDATED_MODULE = "BBBBB";
    private static final String DEFAULT_ROLE = "AAAAA";
    private static final String UPDATED_ROLE = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Boolean DEFAULT_STOREPROCEDURESTATUS = false;
    private static final Boolean UPDATED_STOREPROCEDURESTATUS = true;
    private static final String DEFAULT_PROCEDURENAME = "AAAAA";
    private static final String UPDATED_PROCEDURENAME = "BBBBB";

    private static final Boolean DEFAULT_LISTTABLESTATUS = false;
    private static final Boolean UPDATED_LISTTABLESTATUS = true;
    private static final String DEFAULT_PARAMTABLE = "AAAAA";
    private static final String UPDATED_PARAMTABLE = "BBBBB";
    private static final String DEFAULT_FIELDNAME = "AAAAA";
    private static final String UPDATED_FIELDNAME = "BBBBB";
    private static final String DEFAULT_DISPLAYFIELDNAME = "AAAAA";
    private static final String UPDATED_DISPLAYFIELDNAME = "BBBBB";
    private static final String DEFAULT_FIELDTYPE = "AAAAA";
    private static final String UPDATED_FIELDTYPE = "BBBBB";
    private static final String DEFAULT_WHERECOUSE = "AAAAA";
    private static final String UPDATED_WHERECOUSE = "BBBBB";
    private static final String DEFAULT_ORDERBYFIELD = "AAAAA";
    private static final String UPDATED_ORDERBYFIELD = "BBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    @Inject
    private JasperReportRepository jasperReportRepository;

    @Inject
    private JasperReportSearchRepository jasperReportSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJasperReportMockMvc;

    private JasperReport jasperReport;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JasperReportResource jasperReportResource = new JasperReportResource();
        ReflectionTestUtils.setField(jasperReportResource, "jasperReportRepository", jasperReportRepository);
        ReflectionTestUtils.setField(jasperReportResource, "jasperReportSearchRepository", jasperReportSearchRepository);
        this.restJasperReportMockMvc = MockMvcBuilders.standaloneSetup(jasperReportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jasperReport = new JasperReport();
        jasperReport.setName(DEFAULT_NAME);
        jasperReport.setPath(DEFAULT_PATH);
        jasperReport.setModule(DEFAULT_MODULE);
        jasperReport.setRole(DEFAULT_ROLE);
        jasperReport.setStatus(DEFAULT_STATUS);
        jasperReport.setStoreprocedurestatus(DEFAULT_STOREPROCEDURESTATUS);
        jasperReport.setProcedurename(DEFAULT_PROCEDURENAME);
        jasperReport.setListtablestatus(DEFAULT_LISTTABLESTATUS);
        jasperReport.setParamtable(DEFAULT_PARAMTABLE);
        jasperReport.setFieldname(DEFAULT_FIELDNAME);
        jasperReport.setDisplayfieldname(DEFAULT_DISPLAYFIELDNAME);
        jasperReport.setFieldtype(DEFAULT_FIELDTYPE);
        jasperReport.setWherecouse(DEFAULT_WHERECOUSE);
        jasperReport.setOrderbyfield(DEFAULT_ORDERBYFIELD);
        jasperReport.setCreatedDate(DEFAULT_CREATED_DATE);
        jasperReport.setUpdatedDate(DEFAULT_UPDATED_DATE);
        jasperReport.setCreatedBy(DEFAULT_CREATED_BY);
        jasperReport.setUpdatedBy(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createJasperReport() throws Exception {
        int databaseSizeBeforeCreate = jasperReportRepository.findAll().size();

        // Create the JasperReport

        restJasperReportMockMvc.perform(post("/api/jasperReports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jasperReport)))
                .andExpect(status().isCreated());

        // Validate the JasperReport in the database
        List<JasperReport> jasperReports = jasperReportRepository.findAll();
        assertThat(jasperReports).hasSize(databaseSizeBeforeCreate + 1);
        JasperReport testJasperReport = jasperReports.get(jasperReports.size() - 1);
        assertThat(testJasperReport.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJasperReport.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testJasperReport.getModule()).isEqualTo(DEFAULT_MODULE);
        assertThat(testJasperReport.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testJasperReport.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testJasperReport.getStoreprocedurestatus()).isEqualTo(DEFAULT_STOREPROCEDURESTATUS);
        assertThat(testJasperReport.getProcedurename()).isEqualTo(DEFAULT_PROCEDURENAME);
        assertThat(testJasperReport.getListtablestatus()).isEqualTo(DEFAULT_LISTTABLESTATUS);
        assertThat(testJasperReport.getParamtable()).isEqualTo(DEFAULT_PARAMTABLE);
        assertThat(testJasperReport.getFieldname()).isEqualTo(DEFAULT_FIELDNAME);
        assertThat(testJasperReport.getDisplayfieldname()).isEqualTo(DEFAULT_DISPLAYFIELDNAME);
        assertThat(testJasperReport.getFieldtype()).isEqualTo(DEFAULT_FIELDTYPE);
        assertThat(testJasperReport.getWherecouse()).isEqualTo(DEFAULT_WHERECOUSE);
        assertThat(testJasperReport.getOrderbyfield()).isEqualTo(DEFAULT_ORDERBYFIELD);
        assertThat(testJasperReport.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testJasperReport.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testJasperReport.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testJasperReport.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jasperReportRepository.findAll().size();
        // set the field null
        jasperReport.setName(null);

        // Create the JasperReport, which fails.

        restJasperReportMockMvc.perform(post("/api/jasperReports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jasperReport)))
                .andExpect(status().isBadRequest());

        List<JasperReport> jasperReports = jasperReportRepository.findAll();
        assertThat(jasperReports).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = jasperReportRepository.findAll().size();
        // set the field null
        jasperReport.setPath(null);

        // Create the JasperReport, which fails.

        restJasperReportMockMvc.perform(post("/api/jasperReports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jasperReport)))
                .andExpect(status().isBadRequest());

        List<JasperReport> jasperReports = jasperReportRepository.findAll();
        assertThat(jasperReports).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJasperReports() throws Exception {
        // Initialize the database
        jasperReportRepository.saveAndFlush(jasperReport);

        // Get all the jasperReports
        restJasperReportMockMvc.perform(get("/api/jasperReports"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jasperReport.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
                .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())))
                .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].storeprocedurestatus").value(hasItem(DEFAULT_STOREPROCEDURESTATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].procedurename").value(hasItem(DEFAULT_PROCEDURENAME.toString())))
                .andExpect(jsonPath("$.[*].listtablestatus").value(hasItem(DEFAULT_LISTTABLESTATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].paramtable").value(hasItem(DEFAULT_PARAMTABLE.toString())))
                .andExpect(jsonPath("$.[*].fieldname").value(hasItem(DEFAULT_FIELDNAME.toString())))
                .andExpect(jsonPath("$.[*].displayfieldname").value(hasItem(DEFAULT_DISPLAYFIELDNAME.toString())))
                .andExpect(jsonPath("$.[*].fieldtype").value(hasItem(DEFAULT_FIELDTYPE.toString())))
                .andExpect(jsonPath("$.[*].wherecouse").value(hasItem(DEFAULT_WHERECOUSE.toString())))
                .andExpect(jsonPath("$.[*].orderbyfield").value(hasItem(DEFAULT_ORDERBYFIELD.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void getJasperReport() throws Exception {
        // Initialize the database
        jasperReportRepository.saveAndFlush(jasperReport);

        // Get the jasperReport
        restJasperReportMockMvc.perform(get("/api/jasperReports/{id}", jasperReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jasperReport.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.module").value(DEFAULT_MODULE.toString()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.storeprocedurestatus").value(DEFAULT_STOREPROCEDURESTATUS.booleanValue()))
            .andExpect(jsonPath("$.procedurename").value(DEFAULT_PROCEDURENAME.toString()))
            .andExpect(jsonPath("$.listtablestatus").value(DEFAULT_LISTTABLESTATUS.booleanValue()))
            .andExpect(jsonPath("$.paramtable").value(DEFAULT_PARAMTABLE.toString()))
            .andExpect(jsonPath("$.fieldname").value(DEFAULT_FIELDNAME.toString()))
            .andExpect(jsonPath("$.displayfieldname").value(DEFAULT_DISPLAYFIELDNAME.toString()))
            .andExpect(jsonPath("$.fieldtype").value(DEFAULT_FIELDTYPE.toString()))
            .andExpect(jsonPath("$.wherecouse").value(DEFAULT_WHERECOUSE.toString()))
            .andExpect(jsonPath("$.orderbyfield").value(DEFAULT_ORDERBYFIELD.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingJasperReport() throws Exception {
        // Get the jasperReport
        restJasperReportMockMvc.perform(get("/api/jasperReports/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJasperReport() throws Exception {
        // Initialize the database
        jasperReportRepository.saveAndFlush(jasperReport);

		int databaseSizeBeforeUpdate = jasperReportRepository.findAll().size();

        // Update the jasperReport
        jasperReport.setName(UPDATED_NAME);
        jasperReport.setPath(UPDATED_PATH);
        jasperReport.setModule(UPDATED_MODULE);
        jasperReport.setRole(UPDATED_ROLE);
        jasperReport.setStatus(UPDATED_STATUS);
        jasperReport.setStoreprocedurestatus(UPDATED_STOREPROCEDURESTATUS);
        jasperReport.setProcedurename(UPDATED_PROCEDURENAME);
        jasperReport.setListtablestatus(UPDATED_LISTTABLESTATUS);
        jasperReport.setParamtable(UPDATED_PARAMTABLE);
        jasperReport.setFieldname(UPDATED_FIELDNAME);
        jasperReport.setDisplayfieldname(UPDATED_DISPLAYFIELDNAME);
        jasperReport.setFieldtype(UPDATED_FIELDTYPE);
        jasperReport.setWherecouse(UPDATED_WHERECOUSE);
        jasperReport.setOrderbyfield(UPDATED_ORDERBYFIELD);
        jasperReport.setCreatedDate(UPDATED_CREATED_DATE);
        jasperReport.setUpdatedDate(UPDATED_UPDATED_DATE);
        jasperReport.setCreatedBy(UPDATED_CREATED_BY);
        jasperReport.setUpdatedBy(UPDATED_UPDATED_BY);

        restJasperReportMockMvc.perform(put("/api/jasperReports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jasperReport)))
                .andExpect(status().isOk());

        // Validate the JasperReport in the database
        List<JasperReport> jasperReports = jasperReportRepository.findAll();
        assertThat(jasperReports).hasSize(databaseSizeBeforeUpdate);
        JasperReport testJasperReport = jasperReports.get(jasperReports.size() - 1);
        assertThat(testJasperReport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJasperReport.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testJasperReport.getModule()).isEqualTo(UPDATED_MODULE);
        assertThat(testJasperReport.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testJasperReport.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testJasperReport.getStoreprocedurestatus()).isEqualTo(UPDATED_STOREPROCEDURESTATUS);
        assertThat(testJasperReport.getProcedurename()).isEqualTo(UPDATED_PROCEDURENAME);
        assertThat(testJasperReport.getListtablestatus()).isEqualTo(UPDATED_LISTTABLESTATUS);
        assertThat(testJasperReport.getParamtable()).isEqualTo(UPDATED_PARAMTABLE);
        assertThat(testJasperReport.getFieldname()).isEqualTo(UPDATED_FIELDNAME);
        assertThat(testJasperReport.getDisplayfieldname()).isEqualTo(UPDATED_DISPLAYFIELDNAME);
        assertThat(testJasperReport.getFieldtype()).isEqualTo(UPDATED_FIELDTYPE);
        assertThat(testJasperReport.getWherecouse()).isEqualTo(UPDATED_WHERECOUSE);
        assertThat(testJasperReport.getOrderbyfield()).isEqualTo(UPDATED_ORDERBYFIELD);
        assertThat(testJasperReport.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testJasperReport.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testJasperReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJasperReport.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void deleteJasperReport() throws Exception {
        // Initialize the database
        jasperReportRepository.saveAndFlush(jasperReport);

		int databaseSizeBeforeDelete = jasperReportRepository.findAll().size();

        // Get the jasperReport
        restJasperReportMockMvc.perform(delete("/api/jasperReports/{id}", jasperReport.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JasperReport> jasperReports = jasperReportRepository.findAll();
        assertThat(jasperReports).hasSize(databaseSizeBeforeDelete - 1);
    }
}
