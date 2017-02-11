package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JasperReportParameter;
import gov.step.app.repository.JasperReportParameterRepository;
import gov.step.app.repository.search.JasperReportParameterSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the JasperReportParameterResource REST controller.
 *
 * @see JasperReportParameterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JasperReportParameterResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";
    private static final String DEFAULT_FLEVEL = "AAAAA";
    private static final String UPDATED_FLEVEL = "BBBBB";

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;
    private static final String DEFAULT_DATATYPE = "AAAAA";
    private static final String UPDATED_DATATYPE = "BBBBB";
    private static final String DEFAULT_SERVICENAME = "AAAAA";
    private static final String UPDATED_SERVICENAME = "BBBBB";
    private static final String DEFAULT_COMBODISPLAYFIELD = "AAAAA";
    private static final String UPDATED_COMBODISPLAYFIELD = "BBBBB";
    private static final String DEFAULT_VALIDATIONEXP = "AAAAA";
    private static final String UPDATED_VALIDATIONEXP = "BBBBB";

    private static final Integer DEFAULT_MAXLENGTH = 1;
    private static final Integer UPDATED_MAXLENGTH = 2;

    private static final Integer DEFAULT_MINLENGTH = 1;
    private static final Integer UPDATED_MINLENGTH = 2;
    private static final String DEFAULT_ACTIONNAME = "AAAAA";
    private static final String UPDATED_ACTIONNAME = "BBBBB";
    private static final String DEFAULT_ACTIONTYPE = "AAAAA";
    private static final String UPDATED_ACTIONTYPE = "BBBBB";

    @Inject
    private JasperReportParameterRepository jasperReportParameterRepository;

    @Inject
    private JasperReportParameterSearchRepository jasperReportParameterSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJasperReportParameterMockMvc;

    private JasperReportParameter jasperReportParameter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JasperReportParameterResource jasperReportParameterResource = new JasperReportParameterResource();
        ReflectionTestUtils.setField(jasperReportParameterResource, "jasperReportParameterRepository", jasperReportParameterRepository);
        ReflectionTestUtils.setField(jasperReportParameterResource, "jasperReportParameterSearchRepository", jasperReportParameterSearchRepository);
        this.restJasperReportParameterMockMvc = MockMvcBuilders.standaloneSetup(jasperReportParameterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jasperReportParameter = new JasperReportParameter();
        jasperReportParameter.setName(DEFAULT_NAME);
        jasperReportParameter.setType(DEFAULT_TYPE);
        jasperReportParameter.setFlevel(DEFAULT_FLEVEL);
        jasperReportParameter.setPosition(DEFAULT_POSITION);
        jasperReportParameter.setDatatype(DEFAULT_DATATYPE);
        jasperReportParameter.setServicename(DEFAULT_SERVICENAME);
        jasperReportParameter.setCombodisplayfield(DEFAULT_COMBODISPLAYFIELD);
        jasperReportParameter.setValidationexp(DEFAULT_VALIDATIONEXP);
        jasperReportParameter.setMaxlength(DEFAULT_MAXLENGTH);
        jasperReportParameter.setMinlength(DEFAULT_MINLENGTH);
        jasperReportParameter.setActionname(DEFAULT_ACTIONNAME);
        jasperReportParameter.setActiontype(DEFAULT_ACTIONTYPE);
    }

    @Test
    @Transactional
    public void createJasperReportParameter() throws Exception {
        int databaseSizeBeforeCreate = jasperReportParameterRepository.findAll().size();

        // Create the JasperReportParameter

        restJasperReportParameterMockMvc.perform(post("/api/jasperReportParameters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jasperReportParameter)))
                .andExpect(status().isCreated());

        // Validate the JasperReportParameter in the database
        List<JasperReportParameter> jasperReportParameters = jasperReportParameterRepository.findAll();
        assertThat(jasperReportParameters).hasSize(databaseSizeBeforeCreate + 1);
        JasperReportParameter testJasperReportParameter = jasperReportParameters.get(jasperReportParameters.size() - 1);
        assertThat(testJasperReportParameter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJasperReportParameter.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testJasperReportParameter.getFlevel()).isEqualTo(DEFAULT_FLEVEL);
        assertThat(testJasperReportParameter.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testJasperReportParameter.getDatatype()).isEqualTo(DEFAULT_DATATYPE);
        assertThat(testJasperReportParameter.getServicename()).isEqualTo(DEFAULT_SERVICENAME);
        assertThat(testJasperReportParameter.getCombodisplayfield()).isEqualTo(DEFAULT_COMBODISPLAYFIELD);
        assertThat(testJasperReportParameter.getValidationexp()).isEqualTo(DEFAULT_VALIDATIONEXP);
        assertThat(testJasperReportParameter.getMaxlength()).isEqualTo(DEFAULT_MAXLENGTH);
        assertThat(testJasperReportParameter.getMinlength()).isEqualTo(DEFAULT_MINLENGTH);
        assertThat(testJasperReportParameter.getActionname()).isEqualTo(DEFAULT_ACTIONNAME);
        assertThat(testJasperReportParameter.getActiontype()).isEqualTo(DEFAULT_ACTIONTYPE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jasperReportParameterRepository.findAll().size();
        // set the field null
        jasperReportParameter.setName(null);

        // Create the JasperReportParameter, which fails.

        restJasperReportParameterMockMvc.perform(post("/api/jasperReportParameters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jasperReportParameter)))
                .andExpect(status().isBadRequest());

        List<JasperReportParameter> jasperReportParameters = jasperReportParameterRepository.findAll();
        assertThat(jasperReportParameters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = jasperReportParameterRepository.findAll().size();
        // set the field null
        jasperReportParameter.setType(null);

        // Create the JasperReportParameter, which fails.

        restJasperReportParameterMockMvc.perform(post("/api/jasperReportParameters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jasperReportParameter)))
                .andExpect(status().isBadRequest());

        List<JasperReportParameter> jasperReportParameters = jasperReportParameterRepository.findAll();
        assertThat(jasperReportParameters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = jasperReportParameterRepository.findAll().size();
        // set the field null
        jasperReportParameter.setFlevel(null);

        // Create the JasperReportParameter, which fails.

        restJasperReportParameterMockMvc.perform(post("/api/jasperReportParameters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jasperReportParameter)))
                .andExpect(status().isBadRequest());

        List<JasperReportParameter> jasperReportParameters = jasperReportParameterRepository.findAll();
        assertThat(jasperReportParameters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJasperReportParameters() throws Exception {
        // Initialize the database
        jasperReportParameterRepository.saveAndFlush(jasperReportParameter);

        // Get all the jasperReportParameters
        restJasperReportParameterMockMvc.perform(get("/api/jasperReportParameters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jasperReportParameter.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].flevel").value(hasItem(DEFAULT_FLEVEL.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
                .andExpect(jsonPath("$.[*].datatype").value(hasItem(DEFAULT_DATATYPE.toString())))
                .andExpect(jsonPath("$.[*].servicename").value(hasItem(DEFAULT_SERVICENAME.toString())))
                .andExpect(jsonPath("$.[*].combodisplayfield").value(hasItem(DEFAULT_COMBODISPLAYFIELD.toString())))
                .andExpect(jsonPath("$.[*].validationexp").value(hasItem(DEFAULT_VALIDATIONEXP.toString())))
                .andExpect(jsonPath("$.[*].maxlength").value(hasItem(DEFAULT_MAXLENGTH)))
                .andExpect(jsonPath("$.[*].minlength").value(hasItem(DEFAULT_MINLENGTH)))
                .andExpect(jsonPath("$.[*].actionname").value(hasItem(DEFAULT_ACTIONNAME.toString())))
                .andExpect(jsonPath("$.[*].actiontype").value(hasItem(DEFAULT_ACTIONTYPE.toString())));
    }

    @Test
    @Transactional
    public void getJasperReportParameter() throws Exception {
        // Initialize the database
        jasperReportParameterRepository.saveAndFlush(jasperReportParameter);

        // Get the jasperReportParameter
        restJasperReportParameterMockMvc.perform(get("/api/jasperReportParameters/{id}", jasperReportParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jasperReportParameter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.flevel").value(DEFAULT_FLEVEL.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.datatype").value(DEFAULT_DATATYPE.toString()))
            .andExpect(jsonPath("$.servicename").value(DEFAULT_SERVICENAME.toString()))
            .andExpect(jsonPath("$.combodisplayfield").value(DEFAULT_COMBODISPLAYFIELD.toString()))
            .andExpect(jsonPath("$.validationexp").value(DEFAULT_VALIDATIONEXP.toString()))
            .andExpect(jsonPath("$.maxlength").value(DEFAULT_MAXLENGTH))
            .andExpect(jsonPath("$.minlength").value(DEFAULT_MINLENGTH))
            .andExpect(jsonPath("$.actionname").value(DEFAULT_ACTIONNAME.toString()))
            .andExpect(jsonPath("$.actiontype").value(DEFAULT_ACTIONTYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJasperReportParameter() throws Exception {
        // Get the jasperReportParameter
        restJasperReportParameterMockMvc.perform(get("/api/jasperReportParameters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJasperReportParameter() throws Exception {
        // Initialize the database
        jasperReportParameterRepository.saveAndFlush(jasperReportParameter);

		int databaseSizeBeforeUpdate = jasperReportParameterRepository.findAll().size();

        // Update the jasperReportParameter
        jasperReportParameter.setName(UPDATED_NAME);
        jasperReportParameter.setType(UPDATED_TYPE);
        jasperReportParameter.setFlevel(UPDATED_FLEVEL);
        jasperReportParameter.setPosition(UPDATED_POSITION);
        jasperReportParameter.setDatatype(UPDATED_DATATYPE);
        jasperReportParameter.setServicename(UPDATED_SERVICENAME);
        jasperReportParameter.setCombodisplayfield(UPDATED_COMBODISPLAYFIELD);
        jasperReportParameter.setValidationexp(UPDATED_VALIDATIONEXP);
        jasperReportParameter.setMaxlength(UPDATED_MAXLENGTH);
        jasperReportParameter.setMinlength(UPDATED_MINLENGTH);
        jasperReportParameter.setActionname(UPDATED_ACTIONNAME);
        jasperReportParameter.setActiontype(UPDATED_ACTIONTYPE);

        restJasperReportParameterMockMvc.perform(put("/api/jasperReportParameters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jasperReportParameter)))
                .andExpect(status().isOk());

        // Validate the JasperReportParameter in the database
        List<JasperReportParameter> jasperReportParameters = jasperReportParameterRepository.findAll();
        assertThat(jasperReportParameters).hasSize(databaseSizeBeforeUpdate);
        JasperReportParameter testJasperReportParameter = jasperReportParameters.get(jasperReportParameters.size() - 1);
        assertThat(testJasperReportParameter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJasperReportParameter.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testJasperReportParameter.getFlevel()).isEqualTo(UPDATED_FLEVEL);
        assertThat(testJasperReportParameter.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testJasperReportParameter.getDatatype()).isEqualTo(UPDATED_DATATYPE);
        assertThat(testJasperReportParameter.getServicename()).isEqualTo(UPDATED_SERVICENAME);
        assertThat(testJasperReportParameter.getCombodisplayfield()).isEqualTo(UPDATED_COMBODISPLAYFIELD);
        assertThat(testJasperReportParameter.getValidationexp()).isEqualTo(UPDATED_VALIDATIONEXP);
        assertThat(testJasperReportParameter.getMaxlength()).isEqualTo(UPDATED_MAXLENGTH);
        assertThat(testJasperReportParameter.getMinlength()).isEqualTo(UPDATED_MINLENGTH);
        assertThat(testJasperReportParameter.getActionname()).isEqualTo(UPDATED_ACTIONNAME);
        assertThat(testJasperReportParameter.getActiontype()).isEqualTo(UPDATED_ACTIONTYPE);
    }

    @Test
    @Transactional
    public void deleteJasperReportParameter() throws Exception {
        // Initialize the database
        jasperReportParameterRepository.saveAndFlush(jasperReportParameter);

		int databaseSizeBeforeDelete = jasperReportParameterRepository.findAll().size();

        // Get the jasperReportParameter
        restJasperReportParameterMockMvc.perform(delete("/api/jasperReportParameters/{id}", jasperReportParameter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JasperReportParameter> jasperReportParameters = jasperReportParameterRepository.findAll();
        assertThat(jasperReportParameters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
