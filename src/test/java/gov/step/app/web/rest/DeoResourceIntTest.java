package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Deo;
import gov.step.app.repository.DeoRepository;
import gov.step.app.repository.search.DeoSearchRepository;

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
 * Test class for the DeoResource REST controller.
 *
 * @see DeoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeoResourceIntTest {

    private static final String DEFAULT_CONTACT_NO = "AAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_ORG_NAME = "AAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DATE_CRATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CRATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    @Inject
    private DeoRepository deoRepository;

    @Inject
    private DeoSearchRepository deoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeoMockMvc;

    private Deo deo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeoResource deoResource = new DeoResource();
        ReflectionTestUtils.setField(deoResource, "deoRepository", deoRepository);
        ReflectionTestUtils.setField(deoResource, "deoSearchRepository", deoSearchRepository);
        this.restDeoMockMvc = MockMvcBuilders.standaloneSetup(deoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        deo = new Deo();
        deo.setContactNo(DEFAULT_CONTACT_NO);
        deo.setName(DEFAULT_NAME);
        deo.setDesignation(DEFAULT_DESIGNATION);
        deo.setOrgName(DEFAULT_ORG_NAME);
        deo.setDateCrated(DEFAULT_DATE_CRATED);
        deo.setDateModified(DEFAULT_DATE_MODIFIED);
        deo.setStatus(DEFAULT_STATUS);
        deo.setEmail(DEFAULT_EMAIL);
        deo.setActivated(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void createDeo() throws Exception {
        int databaseSizeBeforeCreate = deoRepository.findAll().size();

        // Create the Deo

        restDeoMockMvc.perform(post("/api/deos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deo)))
                .andExpect(status().isCreated());

        // Validate the Deo in the database
        List<Deo> deos = deoRepository.findAll();
        assertThat(deos).hasSize(databaseSizeBeforeCreate + 1);
        Deo testDeo = deos.get(deos.size() - 1);
        assertThat(testDeo.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testDeo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeo.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testDeo.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testDeo.getDateCrated()).isEqualTo(DEFAULT_DATE_CRATED);
        assertThat(testDeo.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testDeo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDeo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDeo.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllDeos() throws Exception {
        // Initialize the database
        deoRepository.saveAndFlush(deo);

        // Get all the deos
        restDeoMockMvc.perform(get("/api/deos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(deo.getId().intValue())))
                .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME.toString())))
                .andExpect(jsonPath("$.[*].dateCrated").value(hasItem(DEFAULT_DATE_CRATED.toString())))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
    }

    @Test
    @Transactional
    public void getDeo() throws Exception {
        // Initialize the database
        deoRepository.saveAndFlush(deo);

        // Get the deo
        restDeoMockMvc.perform(get("/api/deos/{id}", deo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(deo.getId().intValue()))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME.toString()))
            .andExpect(jsonPath("$.dateCrated").value(DEFAULT_DATE_CRATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDeo() throws Exception {
        // Get the deo
        restDeoMockMvc.perform(get("/api/deos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeo() throws Exception {
        // Initialize the database
        deoRepository.saveAndFlush(deo);

		int databaseSizeBeforeUpdate = deoRepository.findAll().size();

        // Update the deo
        deo.setContactNo(UPDATED_CONTACT_NO);
        deo.setName(UPDATED_NAME);
        deo.setDesignation(UPDATED_DESIGNATION);
        deo.setOrgName(UPDATED_ORG_NAME);
        deo.setDateCrated(UPDATED_DATE_CRATED);
        deo.setDateModified(UPDATED_DATE_MODIFIED);
        deo.setStatus(UPDATED_STATUS);
        deo.setEmail(UPDATED_EMAIL);
        deo.setActivated(UPDATED_ACTIVATED);

        restDeoMockMvc.perform(put("/api/deos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deo)))
                .andExpect(status().isOk());

        // Validate the Deo in the database
        List<Deo> deos = deoRepository.findAll();
        assertThat(deos).hasSize(databaseSizeBeforeUpdate);
        Deo testDeo = deos.get(deos.size() - 1);
        assertThat(testDeo.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testDeo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeo.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testDeo.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testDeo.getDateCrated()).isEqualTo(UPDATED_DATE_CRATED);
        assertThat(testDeo.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testDeo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDeo.getActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void deleteDeo() throws Exception {
        // Initialize the database
        deoRepository.saveAndFlush(deo);

		int databaseSizeBeforeDelete = deoRepository.findAll().size();

        // Get the deo
        restDeoMockMvc.perform(delete("/api/deos/{id}", deo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Deo> deos = deoRepository.findAll();
        assertThat(deos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
