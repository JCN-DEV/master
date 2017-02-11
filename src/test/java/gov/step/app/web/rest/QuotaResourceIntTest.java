package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Quota;
import gov.step.app.repository.QuotaRepository;
import gov.step.app.repository.search.QuotaSearchRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the QuotaResource REST controller.
 *
 * @see QuotaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class QuotaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final byte[] DEFAULT_CERTIFICATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERTIFICATE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CERTIFICATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERTIFICATE_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private QuotaRepository quotaRepository;

    @Inject
    private QuotaSearchRepository quotaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restQuotaMockMvc;

    private Quota quota;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuotaResource quotaResource = new QuotaResource();
        ReflectionTestUtils.setField(quotaResource, "quotaRepository", quotaRepository);
        ReflectionTestUtils.setField(quotaResource, "quotaSearchRepository", quotaSearchRepository);
        this.restQuotaMockMvc = MockMvcBuilders.standaloneSetup(quotaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        quota = new Quota();
        quota.setName(DEFAULT_NAME);
        quota.setCertificate(DEFAULT_CERTIFICATE);
        quota.setCertificateContentType(DEFAULT_CERTIFICATE_CONTENT_TYPE);
        quota.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createQuota() throws Exception {
        int databaseSizeBeforeCreate = quotaRepository.findAll().size();

        // Create the Quota

        restQuotaMockMvc.perform(post("/api/quotas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quota)))
                .andExpect(status().isCreated());

        // Validate the Quota in the database
        List<Quota> quotas = quotaRepository.findAll();
        assertThat(quotas).hasSize(databaseSizeBeforeCreate + 1);
        Quota testQuota = quotas.get(quotas.size() - 1);
        assertThat(testQuota.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testQuota.getCertificate()).isEqualTo(DEFAULT_CERTIFICATE);
        assertThat(testQuota.getCertificateContentType()).isEqualTo(DEFAULT_CERTIFICATE_CONTENT_TYPE);
        assertThat(testQuota.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllQuotas() throws Exception {
        // Initialize the database
        quotaRepository.saveAndFlush(quota);

        // Get all the quotas
        restQuotaMockMvc.perform(get("/api/quotas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(quota.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].certificateContentType").value(hasItem(DEFAULT_CERTIFICATE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].certificate").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERTIFICATE))))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getQuota() throws Exception {
        // Initialize the database
        quotaRepository.saveAndFlush(quota);

        // Get the quota
        restQuotaMockMvc.perform(get("/api/quotas/{id}", quota.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(quota.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.certificateContentType").value(DEFAULT_CERTIFICATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.certificate").value(Base64Utils.encodeToString(DEFAULT_CERTIFICATE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuota() throws Exception {
        // Get the quota
        restQuotaMockMvc.perform(get("/api/quotas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuota() throws Exception {
        // Initialize the database
        quotaRepository.saveAndFlush(quota);

		int databaseSizeBeforeUpdate = quotaRepository.findAll().size();

        // Update the quota
        quota.setName(UPDATED_NAME);
        quota.setCertificate(UPDATED_CERTIFICATE);
        quota.setCertificateContentType(UPDATED_CERTIFICATE_CONTENT_TYPE);
        quota.setDescription(UPDATED_DESCRIPTION);

        restQuotaMockMvc.perform(put("/api/quotas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quota)))
                .andExpect(status().isOk());

        // Validate the Quota in the database
        List<Quota> quotas = quotaRepository.findAll();
        assertThat(quotas).hasSize(databaseSizeBeforeUpdate);
        Quota testQuota = quotas.get(quotas.size() - 1);
        assertThat(testQuota.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testQuota.getCertificate()).isEqualTo(UPDATED_CERTIFICATE);
        assertThat(testQuota.getCertificateContentType()).isEqualTo(UPDATED_CERTIFICATE_CONTENT_TYPE);
        assertThat(testQuota.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteQuota() throws Exception {
        // Initialize the database
        quotaRepository.saveAndFlush(quota);

		int databaseSizeBeforeDelete = quotaRepository.findAll().size();

        // Get the quota
        restQuotaMockMvc.perform(delete("/api/quotas/{id}", quota.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Quota> quotas = quotaRepository.findAll();
        assertThat(quotas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
