package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstitutePlayGroundInfo;
import gov.step.app.repository.InstitutePlayGroundInfoRepository;
import gov.step.app.repository.search.InstitutePlayGroundInfoSearchRepository;

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
 * Test class for the InstitutePlayGroundInfoResource REST controller.
 *
 * @see InstitutePlayGroundInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstitutePlayGroundInfoResourceIntTest {

    private static final String DEFAULT_PLAYGROUND_NO = "AAAAA";
    private static final String UPDATED_PLAYGROUND_NO = "BBBBB";
    private static final String DEFAULT_AREA = "AAAAA";
    private static final String UPDATED_AREA = "BBBBB";

    @Inject
    private InstitutePlayGroundInfoRepository institutePlayGroundInfoRepository;

    @Inject
    private InstitutePlayGroundInfoSearchRepository institutePlayGroundInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstitutePlayGroundInfoMockMvc;

    private InstitutePlayGroundInfo institutePlayGroundInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstitutePlayGroundInfoResource institutePlayGroundInfoResource = new InstitutePlayGroundInfoResource();
        ReflectionTestUtils.setField(institutePlayGroundInfoResource, "institutePlayGroundInfoRepository", institutePlayGroundInfoRepository);
        ReflectionTestUtils.setField(institutePlayGroundInfoResource, "institutePlayGroundInfoSearchRepository", institutePlayGroundInfoSearchRepository);
        this.restInstitutePlayGroundInfoMockMvc = MockMvcBuilders.standaloneSetup(institutePlayGroundInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        institutePlayGroundInfo = new InstitutePlayGroundInfo();
        institutePlayGroundInfo.setPlaygroundNo(DEFAULT_PLAYGROUND_NO);
        institutePlayGroundInfo.setArea(DEFAULT_AREA);
    }

    @Test
    @Transactional
    public void createInstitutePlayGroundInfo() throws Exception {
        int databaseSizeBeforeCreate = institutePlayGroundInfoRepository.findAll().size();

        // Create the InstitutePlayGroundInfo

        restInstitutePlayGroundInfoMockMvc.perform(post("/api/institutePlayGroundInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(institutePlayGroundInfo)))
                .andExpect(status().isCreated());

        // Validate the InstitutePlayGroundInfo in the database
        List<InstitutePlayGroundInfo> institutePlayGroundInfos = institutePlayGroundInfoRepository.findAll();
        assertThat(institutePlayGroundInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstitutePlayGroundInfo testInstitutePlayGroundInfo = institutePlayGroundInfos.get(institutePlayGroundInfos.size() - 1);
        assertThat(testInstitutePlayGroundInfo.getPlaygroundNo()).isEqualTo(DEFAULT_PLAYGROUND_NO);
        assertThat(testInstitutePlayGroundInfo.getArea()).isEqualTo(DEFAULT_AREA);
    }

    @Test
    @Transactional
    public void getAllInstitutePlayGroundInfos() throws Exception {
        // Initialize the database
        institutePlayGroundInfoRepository.saveAndFlush(institutePlayGroundInfo);

        // Get all the institutePlayGroundInfos
        restInstitutePlayGroundInfoMockMvc.perform(get("/api/institutePlayGroundInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(institutePlayGroundInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].playgroundNo").value(hasItem(DEFAULT_PLAYGROUND_NO.toString())))
                .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())));
    }

    @Test
    @Transactional
    public void getInstitutePlayGroundInfo() throws Exception {
        // Initialize the database
        institutePlayGroundInfoRepository.saveAndFlush(institutePlayGroundInfo);

        // Get the institutePlayGroundInfo
        restInstitutePlayGroundInfoMockMvc.perform(get("/api/institutePlayGroundInfos/{id}", institutePlayGroundInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(institutePlayGroundInfo.getId().intValue()))
            .andExpect(jsonPath("$.playgroundNo").value(DEFAULT_PLAYGROUND_NO.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstitutePlayGroundInfo() throws Exception {
        // Get the institutePlayGroundInfo
        restInstitutePlayGroundInfoMockMvc.perform(get("/api/institutePlayGroundInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstitutePlayGroundInfo() throws Exception {
        // Initialize the database
        institutePlayGroundInfoRepository.saveAndFlush(institutePlayGroundInfo);

		int databaseSizeBeforeUpdate = institutePlayGroundInfoRepository.findAll().size();

        // Update the institutePlayGroundInfo
        institutePlayGroundInfo.setPlaygroundNo(UPDATED_PLAYGROUND_NO);
        institutePlayGroundInfo.setArea(UPDATED_AREA);

        restInstitutePlayGroundInfoMockMvc.perform(put("/api/institutePlayGroundInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(institutePlayGroundInfo)))
                .andExpect(status().isOk());

        // Validate the InstitutePlayGroundInfo in the database
        List<InstitutePlayGroundInfo> institutePlayGroundInfos = institutePlayGroundInfoRepository.findAll();
        assertThat(institutePlayGroundInfos).hasSize(databaseSizeBeforeUpdate);
        InstitutePlayGroundInfo testInstitutePlayGroundInfo = institutePlayGroundInfos.get(institutePlayGroundInfos.size() - 1);
        assertThat(testInstitutePlayGroundInfo.getPlaygroundNo()).isEqualTo(UPDATED_PLAYGROUND_NO);
        assertThat(testInstitutePlayGroundInfo.getArea()).isEqualTo(UPDATED_AREA);
    }

    @Test
    @Transactional
    public void deleteInstitutePlayGroundInfo() throws Exception {
        // Initialize the database
        institutePlayGroundInfoRepository.saveAndFlush(institutePlayGroundInfo);

		int databaseSizeBeforeDelete = institutePlayGroundInfoRepository.findAll().size();

        // Get the institutePlayGroundInfo
        restInstitutePlayGroundInfoMockMvc.perform(delete("/api/institutePlayGroundInfos/{id}", institutePlayGroundInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstitutePlayGroundInfo> institutePlayGroundInfos = institutePlayGroundInfoRepository.findAll();
        assertThat(institutePlayGroundInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
