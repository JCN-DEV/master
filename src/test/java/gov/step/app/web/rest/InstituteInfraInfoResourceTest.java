package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstituteInfraInfo;
import gov.step.app.repository.InstituteInfraInfoRepository;
import gov.step.app.repository.search.InstituteInfraInfoSearchRepository;

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
 * Test class for the InstituteInfraInfoResource REST controller.
 *
 * @see InstituteInfraInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstituteInfraInfoResourceTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstituteInfraInfoRepository instituteInfraInfoRepository;

    @Inject
    private InstituteInfraInfoSearchRepository instituteInfraInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstituteInfraInfoMockMvc;

    private InstituteInfraInfo instituteInfraInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteInfraInfoResource instituteInfraInfoResource = new InstituteInfraInfoResource();
        ReflectionTestUtils.setField(instituteInfraInfoResource, "instituteInfraInfoRepository", instituteInfraInfoRepository);
        ReflectionTestUtils.setField(instituteInfraInfoResource, "instituteInfraInfoSearchRepository", instituteInfraInfoSearchRepository);
        this.restInstituteInfraInfoMockMvc = MockMvcBuilders.standaloneSetup(instituteInfraInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instituteInfraInfo = new InstituteInfraInfo();
        instituteInfraInfo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstituteInfraInfo() throws Exception {
        int databaseSizeBeforeCreate = instituteInfraInfoRepository.findAll().size();

        // Create the InstituteInfraInfo

        restInstituteInfraInfoMockMvc.perform(post("/api/instituteInfraInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteInfraInfo)))
                .andExpect(status().isCreated());

        // Validate the InstituteInfraInfo in the database
        List<InstituteInfraInfo> instituteInfraInfos = instituteInfraInfoRepository.findAll();
        assertThat(instituteInfraInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstituteInfraInfo testInstituteInfraInfo = instituteInfraInfos.get(instituteInfraInfos.size() - 1);
        assertThat(testInstituteInfraInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstituteInfraInfos() throws Exception {
        // Initialize the database
        instituteInfraInfoRepository.saveAndFlush(instituteInfraInfo);

        // Get all the instituteInfraInfos
        restInstituteInfraInfoMockMvc.perform(get("/api/instituteInfraInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instituteInfraInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstituteInfraInfo() throws Exception {
        // Initialize the database
        instituteInfraInfoRepository.saveAndFlush(instituteInfraInfo);

        // Get the instituteInfraInfo
        restInstituteInfraInfoMockMvc.perform(get("/api/instituteInfraInfos/{id}", instituteInfraInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instituteInfraInfo.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstituteInfraInfo() throws Exception {
        // Get the instituteInfraInfo
        restInstituteInfraInfoMockMvc.perform(get("/api/instituteInfraInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituteInfraInfo() throws Exception {
        // Initialize the database
        instituteInfraInfoRepository.saveAndFlush(instituteInfraInfo);

		int databaseSizeBeforeUpdate = instituteInfraInfoRepository.findAll().size();

        // Update the instituteInfraInfo
        instituteInfraInfo.setStatus(UPDATED_STATUS);

        restInstituteInfraInfoMockMvc.perform(put("/api/instituteInfraInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteInfraInfo)))
                .andExpect(status().isOk());

        // Validate the InstituteInfraInfo in the database
        List<InstituteInfraInfo> instituteInfraInfos = instituteInfraInfoRepository.findAll();
        assertThat(instituteInfraInfos).hasSize(databaseSizeBeforeUpdate);
        InstituteInfraInfo testInstituteInfraInfo = instituteInfraInfos.get(instituteInfraInfos.size() - 1);
        assertThat(testInstituteInfraInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstituteInfraInfo() throws Exception {
        // Initialize the database
        instituteInfraInfoRepository.saveAndFlush(instituteInfraInfo);

		int databaseSizeBeforeDelete = instituteInfraInfoRepository.findAll().size();

        // Get the instituteInfraInfo
        restInstituteInfraInfoMockMvc.perform(delete("/api/instituteInfraInfos/{id}", instituteInfraInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstituteInfraInfo> instituteInfraInfos = instituteInfraInfoRepository.findAll();
        assertThat(instituteInfraInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
