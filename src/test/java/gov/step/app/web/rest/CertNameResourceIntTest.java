package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CertName;
import gov.step.app.repository.CertNameRepository;
import gov.step.app.repository.search.CertNameSearchRepository;

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
 * Test class for the CertNameResource REST controller.
 *
 * @see CertNameResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CertNameResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private CertNameRepository certNameRepository;

    @Inject
    private CertNameSearchRepository certNameSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCertNameMockMvc;

    private CertName certName;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CertNameResource certNameResource = new CertNameResource();
        ReflectionTestUtils.setField(certNameResource, "certNameRepository", certNameRepository);
        ReflectionTestUtils.setField(certNameResource, "certNameSearchRepository", certNameSearchRepository);
        this.restCertNameMockMvc = MockMvcBuilders.standaloneSetup(certNameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        certName = new CertName();
        certName.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCertName() throws Exception {
        int databaseSizeBeforeCreate = certNameRepository.findAll().size();

        // Create the CertName

        restCertNameMockMvc.perform(post("/api/certNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(certName)))
                .andExpect(status().isCreated());

        // Validate the CertName in the database
        List<CertName> certNames = certNameRepository.findAll();
        assertThat(certNames).hasSize(databaseSizeBeforeCreate + 1);
        CertName testCertName = certNames.get(certNames.size() - 1);
        assertThat(testCertName.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllCertNames() throws Exception {
        // Initialize the database
        certNameRepository.saveAndFlush(certName);

        // Get all the certNames
        restCertNameMockMvc.perform(get("/api/certNames"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(certName.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCertName() throws Exception {
        // Initialize the database
        certNameRepository.saveAndFlush(certName);

        // Get the certName
        restCertNameMockMvc.perform(get("/api/certNames/{id}", certName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(certName.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCertName() throws Exception {
        // Get the certName
        restCertNameMockMvc.perform(get("/api/certNames/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCertName() throws Exception {
        // Initialize the database
        certNameRepository.saveAndFlush(certName);

		int databaseSizeBeforeUpdate = certNameRepository.findAll().size();

        // Update the certName
        certName.setName(UPDATED_NAME);

        restCertNameMockMvc.perform(put("/api/certNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(certName)))
                .andExpect(status().isOk());

        // Validate the CertName in the database
        List<CertName> certNames = certNameRepository.findAll();
        assertThat(certNames).hasSize(databaseSizeBeforeUpdate);
        CertName testCertName = certNames.get(certNames.size() - 1);
        assertThat(testCertName.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteCertName() throws Exception {
        // Initialize the database
        certNameRepository.saveAndFlush(certName);

		int databaseSizeBeforeDelete = certNameRepository.findAll().size();

        // Get the certName
        restCertNameMockMvc.perform(delete("/api/certNames/{id}", certName.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CertName> certNames = certNameRepository.findAll();
        assertThat(certNames).hasSize(databaseSizeBeforeDelete - 1);
    }
}
