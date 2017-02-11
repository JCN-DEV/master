package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmplHist;
import gov.step.app.repository.InstEmplHistRepository;
import gov.step.app.repository.search.InstEmplHistSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the InstEmplHistResource REST controller.
 *
 * @see InstEmplHistResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmplHistResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_JOB_AREA = "AAAAA";
    private static final String UPDATED_JOB_AREA = "BBBBB";

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ON_TRACK = false;
    private static final Boolean UPDATED_ON_TRACK = true;
    private static final String DEFAULT_TELEPHONE = "AAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBB";
    private static final String DEFAULT_EXT = "AAAAA";
    private static final String UPDATED_EXT = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_MOBILE = "AAAAA";
    private static final String UPDATED_MOBILE = "BBBBB";
    private static final String DEFAULT_WEBSITE = "AAAAA";
    private static final String UPDATED_WEBSITE = "BBBBB";

    private static final byte[] DEFAULT_CERTIFICATE_COPY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERTIFICATE_COPY = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CERTIFICATE_COPY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERTIFICATE_COPY_CONTENT_TYPE = "image/png";

    @Inject
    private InstEmplHistRepository instEmplHistRepository;

    @Inject
    private InstEmplHistSearchRepository instEmplHistSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmplHistMockMvc;

    private InstEmplHist instEmplHist;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmplHistResource instEmplHistResource = new InstEmplHistResource();
        ReflectionTestUtils.setField(instEmplHistResource, "instEmplHistRepository", instEmplHistRepository);
        ReflectionTestUtils.setField(instEmplHistResource, "instEmplHistSearchRepository", instEmplHistSearchRepository);
        this.restInstEmplHistMockMvc = MockMvcBuilders.standaloneSetup(instEmplHistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmplHist = new InstEmplHist();
        instEmplHist.setName(DEFAULT_NAME);
        instEmplHist.setDesignation(DEFAULT_DESIGNATION);
        instEmplHist.setJobArea(DEFAULT_JOB_AREA);
        instEmplHist.setStart(DEFAULT_START);
        instEmplHist.setEnd(DEFAULT_END);
        instEmplHist.setOnTrack(DEFAULT_ON_TRACK);
        instEmplHist.setTelephone(DEFAULT_TELEPHONE);
        instEmplHist.setExt(DEFAULT_EXT);
        instEmplHist.setEmail(DEFAULT_EMAIL);
        instEmplHist.setMobile(DEFAULT_MOBILE);
        instEmplHist.setWebsite(DEFAULT_WEBSITE);
        instEmplHist.setCertificateCopy(DEFAULT_CERTIFICATE_COPY);
        instEmplHist.setCertificateCopyContentType(DEFAULT_CERTIFICATE_COPY_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createInstEmplHist() throws Exception {
        int databaseSizeBeforeCreate = instEmplHistRepository.findAll().size();

        // Create the InstEmplHist

        restInstEmplHistMockMvc.perform(post("/api/instEmplHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplHist)))
                .andExpect(status().isCreated());

        // Validate the InstEmplHist in the database
        List<InstEmplHist> instEmplHists = instEmplHistRepository.findAll();
        assertThat(instEmplHists).hasSize(databaseSizeBeforeCreate + 1);
        InstEmplHist testInstEmplHist = instEmplHists.get(instEmplHists.size() - 1);
        assertThat(testInstEmplHist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstEmplHist.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testInstEmplHist.getJobArea()).isEqualTo(DEFAULT_JOB_AREA);
        assertThat(testInstEmplHist.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testInstEmplHist.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testInstEmplHist.getOnTrack()).isEqualTo(DEFAULT_ON_TRACK);
        assertThat(testInstEmplHist.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testInstEmplHist.getExt()).isEqualTo(DEFAULT_EXT);
        assertThat(testInstEmplHist.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInstEmplHist.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testInstEmplHist.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testInstEmplHist.getCertificateCopy()).isEqualTo(DEFAULT_CERTIFICATE_COPY);
        assertThat(testInstEmplHist.getCertificateCopyContentType()).isEqualTo(DEFAULT_CERTIFICATE_COPY_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllInstEmplHists() throws Exception {
        // Initialize the database
        instEmplHistRepository.saveAndFlush(instEmplHist);

        // Get all the instEmplHists
        restInstEmplHistMockMvc.perform(get("/api/instEmplHists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmplHist.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].jobArea").value(hasItem(DEFAULT_JOB_AREA.toString())))
                .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
                .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
                .andExpect(jsonPath("$.[*].onTrack").value(hasItem(DEFAULT_ON_TRACK.booleanValue())))
                .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
                .andExpect(jsonPath("$.[*].ext").value(hasItem(DEFAULT_EXT.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
                .andExpect(jsonPath("$.[*].certificateCopyContentType").value(hasItem(DEFAULT_CERTIFICATE_COPY_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].certificateCopy").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERTIFICATE_COPY))));
    }

    @Test
    @Transactional
    public void getInstEmplHist() throws Exception {
        // Initialize the database
        instEmplHistRepository.saveAndFlush(instEmplHist);

        // Get the instEmplHist
        restInstEmplHistMockMvc.perform(get("/api/instEmplHists/{id}", instEmplHist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmplHist.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.jobArea").value(DEFAULT_JOB_AREA.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.onTrack").value(DEFAULT_ON_TRACK.booleanValue()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.ext").value(DEFAULT_EXT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.certificateCopyContentType").value(DEFAULT_CERTIFICATE_COPY_CONTENT_TYPE))
            .andExpect(jsonPath("$.certificateCopy").value(Base64Utils.encodeToString(DEFAULT_CERTIFICATE_COPY)));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmplHist() throws Exception {
        // Get the instEmplHist
        restInstEmplHistMockMvc.perform(get("/api/instEmplHists/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmplHist() throws Exception {
        // Initialize the database
        instEmplHistRepository.saveAndFlush(instEmplHist);

		int databaseSizeBeforeUpdate = instEmplHistRepository.findAll().size();

        // Update the instEmplHist
        instEmplHist.setName(UPDATED_NAME);
        instEmplHist.setDesignation(UPDATED_DESIGNATION);
        instEmplHist.setJobArea(UPDATED_JOB_AREA);
        instEmplHist.setStart(UPDATED_START);
        instEmplHist.setEnd(UPDATED_END);
        instEmplHist.setOnTrack(UPDATED_ON_TRACK);
        instEmplHist.setTelephone(UPDATED_TELEPHONE);
        instEmplHist.setExt(UPDATED_EXT);
        instEmplHist.setEmail(UPDATED_EMAIL);
        instEmplHist.setMobile(UPDATED_MOBILE);
        instEmplHist.setWebsite(UPDATED_WEBSITE);
        instEmplHist.setCertificateCopy(UPDATED_CERTIFICATE_COPY);
        instEmplHist.setCertificateCopyContentType(UPDATED_CERTIFICATE_COPY_CONTENT_TYPE);

        restInstEmplHistMockMvc.perform(put("/api/instEmplHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplHist)))
                .andExpect(status().isOk());

        // Validate the InstEmplHist in the database
        List<InstEmplHist> instEmplHists = instEmplHistRepository.findAll();
        assertThat(instEmplHists).hasSize(databaseSizeBeforeUpdate);
        InstEmplHist testInstEmplHist = instEmplHists.get(instEmplHists.size() - 1);
        assertThat(testInstEmplHist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstEmplHist.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testInstEmplHist.getJobArea()).isEqualTo(UPDATED_JOB_AREA);
        assertThat(testInstEmplHist.getStart()).isEqualTo(UPDATED_START);
        assertThat(testInstEmplHist.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testInstEmplHist.getOnTrack()).isEqualTo(UPDATED_ON_TRACK);
        assertThat(testInstEmplHist.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testInstEmplHist.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testInstEmplHist.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstEmplHist.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testInstEmplHist.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testInstEmplHist.getCertificateCopy()).isEqualTo(UPDATED_CERTIFICATE_COPY);
        assertThat(testInstEmplHist.getCertificateCopyContentType()).isEqualTo(UPDATED_CERTIFICATE_COPY_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteInstEmplHist() throws Exception {
        // Initialize the database
        instEmplHistRepository.saveAndFlush(instEmplHist);

		int databaseSizeBeforeDelete = instEmplHistRepository.findAll().size();

        // Get the instEmplHist
        restInstEmplHistMockMvc.perform(delete("/api/instEmplHists/{id}", instEmplHist.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmplHist> instEmplHists = instEmplHistRepository.findAll();
        assertThat(instEmplHists).hasSize(databaseSizeBeforeDelete - 1);
    }
}
