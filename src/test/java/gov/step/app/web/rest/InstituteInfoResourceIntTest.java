package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstituteInfo;
import gov.step.app.repository.InstituteInfoRepository;
import gov.step.app.repository.search.InstituteInfoSearchRepository;

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
 * Test class for the InstituteInfoResource REST controller.
 *
 * @see InstituteInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstituteInfoResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private InstituteInfoRepository instituteInfoRepository;

    @Inject
    private InstituteInfoSearchRepository instituteInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstituteInfoMockMvc;

    private InstituteInfo instituteInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteInfoResource instituteInfoResource = new InstituteInfoResource();
        ReflectionTestUtils.setField(instituteInfoResource, "instituteInfoRepository", instituteInfoRepository);
        ReflectionTestUtils.setField(instituteInfoResource, "instituteInfoSearchRepository", instituteInfoSearchRepository);
        this.restInstituteInfoMockMvc = MockMvcBuilders.standaloneSetup(instituteInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instituteInfo = new InstituteInfo();
        instituteInfo.setCode(DEFAULT_CODE);
        instituteInfo.setPublicationDate(DEFAULT_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void createInstituteInfo() throws Exception {
        int databaseSizeBeforeCreate = instituteInfoRepository.findAll().size();

        // Create the InstituteInfo

        restInstituteInfoMockMvc.perform(post("/api/instituteInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteInfo)))
                .andExpect(status().isCreated());

        // Validate the InstituteInfo in the database
        List<InstituteInfo> instituteInfos = instituteInfoRepository.findAll();
        assertThat(instituteInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstituteInfo testInstituteInfo = instituteInfos.get(instituteInfos.size() - 1);
        assertThat(testInstituteInfo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInstituteInfo.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteInfoRepository.findAll().size();
        // set the field null
        instituteInfo.setCode(null);

        // Create the InstituteInfo, which fails.

        restInstituteInfoMockMvc.perform(post("/api/instituteInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteInfo)))
                .andExpect(status().isBadRequest());

        List<InstituteInfo> instituteInfos = instituteInfoRepository.findAll();
        assertThat(instituteInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteInfoRepository.findAll().size();
        // set the field null
        instituteInfo.setPublicationDate(null);

        // Create the InstituteInfo, which fails.

        restInstituteInfoMockMvc.perform(post("/api/instituteInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteInfo)))
                .andExpect(status().isBadRequest());

        List<InstituteInfo> instituteInfos = instituteInfoRepository.findAll();
        assertThat(instituteInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstituteInfos() throws Exception {
        // Initialize the database
        instituteInfoRepository.saveAndFlush(instituteInfo);

        // Get all the instituteInfos
        restInstituteInfoMockMvc.perform(get("/api/instituteInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instituteInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInstituteInfo() throws Exception {
        // Initialize the database
        instituteInfoRepository.saveAndFlush(instituteInfo);

        // Get the instituteInfo
        restInstituteInfoMockMvc.perform(get("/api/instituteInfos/{id}", instituteInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instituteInfo.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstituteInfo() throws Exception {
        // Get the instituteInfo
        restInstituteInfoMockMvc.perform(get("/api/instituteInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituteInfo() throws Exception {
        // Initialize the database
        instituteInfoRepository.saveAndFlush(instituteInfo);

		int databaseSizeBeforeUpdate = instituteInfoRepository.findAll().size();

        // Update the instituteInfo
        instituteInfo.setCode(UPDATED_CODE);
        instituteInfo.setPublicationDate(UPDATED_PUBLICATION_DATE);

        restInstituteInfoMockMvc.perform(put("/api/instituteInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteInfo)))
                .andExpect(status().isOk());

        // Validate the InstituteInfo in the database
        List<InstituteInfo> instituteInfos = instituteInfoRepository.findAll();
        assertThat(instituteInfos).hasSize(databaseSizeBeforeUpdate);
        InstituteInfo testInstituteInfo = instituteInfos.get(instituteInfos.size() - 1);
        assertThat(testInstituteInfo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInstituteInfo.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void deleteInstituteInfo() throws Exception {
        // Initialize the database
        instituteInfoRepository.saveAndFlush(instituteInfo);

		int databaseSizeBeforeDelete = instituteInfoRepository.findAll().size();

        // Get the instituteInfo
        restInstituteInfoMockMvc.perform(delete("/api/instituteInfos/{id}", instituteInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstituteInfo> instituteInfos = instituteInfoRepository.findAll();
        assertThat(instituteInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
