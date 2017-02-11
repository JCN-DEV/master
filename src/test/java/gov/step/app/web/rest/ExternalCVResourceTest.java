package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.ExternalCV;
import gov.step.app.repository.ExternalCVRepository;
import gov.step.app.repository.search.ExternalCVSearchRepository;

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
 * Test class for the ExternalCVResource REST controller.
 *
 * @see ExternalCVResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ExternalCVResourceTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final byte[] DEFAULT_CV = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CV = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CV_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CV_CONTENT_TYPE = "image/png";

    @Inject
    private ExternalCVRepository externalCVRepository;

    @Inject
    private ExternalCVSearchRepository externalCVSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restExternalCVMockMvc;

    private ExternalCV externalCV;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExternalCVResource externalCVResource = new ExternalCVResource();
        ReflectionTestUtils.setField(externalCVResource, "externalCVRepository", externalCVRepository);
        ReflectionTestUtils.setField(externalCVResource, "externalCVSearchRepository", externalCVSearchRepository);
        this.restExternalCVMockMvc = MockMvcBuilders.standaloneSetup(externalCVResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        externalCV = new ExternalCV();
        externalCV.setTitle(DEFAULT_TITLE);
        externalCV.setCv(DEFAULT_CV);
        externalCV.setCvContentType(DEFAULT_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createExternalCV() throws Exception {
        int databaseSizeBeforeCreate = externalCVRepository.findAll().size();

        // Create the ExternalCV

        restExternalCVMockMvc.perform(post("/api/externalCVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(externalCV)))
                .andExpect(status().isCreated());

        // Validate the ExternalCV in the database
        List<ExternalCV> externalCVs = externalCVRepository.findAll();
        assertThat(externalCVs).hasSize(databaseSizeBeforeCreate + 1);
        ExternalCV testExternalCV = externalCVs.get(externalCVs.size() - 1);
        assertThat(testExternalCV.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExternalCV.getCv()).isEqualTo(DEFAULT_CV);
        assertThat(testExternalCV.getCvContentType()).isEqualTo(DEFAULT_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalCVRepository.findAll().size();
        // set the field null
        externalCV.setTitle(null);

        // Create the ExternalCV, which fails.

        restExternalCVMockMvc.perform(post("/api/externalCVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(externalCV)))
                .andExpect(status().isBadRequest());

        List<ExternalCV> externalCVs = externalCVRepository.findAll();
        assertThat(externalCVs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCvIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalCVRepository.findAll().size();
        // set the field null
        externalCV.setCv(null);

        // Create the ExternalCV, which fails.

        restExternalCVMockMvc.perform(post("/api/externalCVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(externalCV)))
                .andExpect(status().isBadRequest());

        List<ExternalCV> externalCVs = externalCVRepository.findAll();
        assertThat(externalCVs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExternalCVs() throws Exception {
        // Initialize the database
        externalCVRepository.saveAndFlush(externalCV);

        // Get all the externalCVs
        restExternalCVMockMvc.perform(get("/api/externalCVs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(externalCV.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].cvContentType").value(hasItem(DEFAULT_CV_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].cv").value(hasItem(Base64Utils.encodeToString(DEFAULT_CV))));
    }

    @Test
    @Transactional
    public void getExternalCV() throws Exception {
        // Initialize the database
        externalCVRepository.saveAndFlush(externalCV);

        // Get the externalCV
        restExternalCVMockMvc.perform(get("/api/externalCVs/{id}", externalCV.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(externalCV.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.cvContentType").value(DEFAULT_CV_CONTENT_TYPE))
            .andExpect(jsonPath("$.cv").value(Base64Utils.encodeToString(DEFAULT_CV)));
    }

    @Test
    @Transactional
    public void getNonExistingExternalCV() throws Exception {
        // Get the externalCV
        restExternalCVMockMvc.perform(get("/api/externalCVs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExternalCV() throws Exception {
        // Initialize the database
        externalCVRepository.saveAndFlush(externalCV);

		int databaseSizeBeforeUpdate = externalCVRepository.findAll().size();

        // Update the externalCV
        externalCV.setTitle(UPDATED_TITLE);
        externalCV.setCv(UPDATED_CV);
        externalCV.setCvContentType(UPDATED_CV_CONTENT_TYPE);

        restExternalCVMockMvc.perform(put("/api/externalCVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(externalCV)))
                .andExpect(status().isOk());

        // Validate the ExternalCV in the database
        List<ExternalCV> externalCVs = externalCVRepository.findAll();
        assertThat(externalCVs).hasSize(databaseSizeBeforeUpdate);
        ExternalCV testExternalCV = externalCVs.get(externalCVs.size() - 1);
        assertThat(testExternalCV.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExternalCV.getCv()).isEqualTo(UPDATED_CV);
        assertThat(testExternalCV.getCvContentType()).isEqualTo(UPDATED_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteExternalCV() throws Exception {
        // Initialize the database
        externalCVRepository.saveAndFlush(externalCV);

		int databaseSizeBeforeDelete = externalCVRepository.findAll().size();

        // Get the externalCV
        restExternalCVMockMvc.perform(delete("/api/externalCVs/{id}", externalCV.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExternalCV> externalCVs = externalCVRepository.findAll();
        assertThat(externalCVs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
