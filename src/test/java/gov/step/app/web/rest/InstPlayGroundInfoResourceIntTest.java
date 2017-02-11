package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstPlayGroundInfo;
import gov.step.app.repository.InstPlayGroundInfoRepository;
import gov.step.app.repository.search.InstPlayGroundInfoSearchRepository;

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
 * Test class for the InstPlayGroundInfoResource REST controller.
 *
 * @see InstPlayGroundInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstPlayGroundInfoResourceIntTest {

    private static final String DEFAULT_PLAYGROUND_NO = "AAAAA";
    private static final String UPDATED_PLAYGROUND_NO = "BBBBB";
    private static final String DEFAULT_AREA = "AAAAA";
    private static final String UPDATED_AREA = "BBBBB";

    @Inject
    private InstPlayGroundInfoRepository instPlayGroundInfoRepository;

    @Inject
    private InstPlayGroundInfoSearchRepository instPlayGroundInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstPlayGroundInfoMockMvc;

    private InstPlayGroundInfo instPlayGroundInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstPlayGroundInfoResource instPlayGroundInfoResource = new InstPlayGroundInfoResource();
        ReflectionTestUtils.setField(instPlayGroundInfoResource, "instPlayGroundInfoRepository", instPlayGroundInfoRepository);
        ReflectionTestUtils.setField(instPlayGroundInfoResource, "instPlayGroundInfoSearchRepository", instPlayGroundInfoSearchRepository);
        this.restInstPlayGroundInfoMockMvc = MockMvcBuilders.standaloneSetup(instPlayGroundInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instPlayGroundInfo = new InstPlayGroundInfo();
        instPlayGroundInfo.setPlaygroundNo(DEFAULT_PLAYGROUND_NO);
        instPlayGroundInfo.setArea(DEFAULT_AREA);
    }

    @Test
    @Transactional
    public void createInstPlayGroundInfo() throws Exception {
        int databaseSizeBeforeCreate = instPlayGroundInfoRepository.findAll().size();

        // Create the InstPlayGroundInfo

        restInstPlayGroundInfoMockMvc.perform(post("/api/instPlayGroundInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instPlayGroundInfo)))
                .andExpect(status().isCreated());

        // Validate the InstPlayGroundInfo in the database
        List<InstPlayGroundInfo> instPlayGroundInfos = instPlayGroundInfoRepository.findAll();
        assertThat(instPlayGroundInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstPlayGroundInfo testInstPlayGroundInfo = instPlayGroundInfos.get(instPlayGroundInfos.size() - 1);
        assertThat(testInstPlayGroundInfo.getPlaygroundNo()).isEqualTo(DEFAULT_PLAYGROUND_NO);
        assertThat(testInstPlayGroundInfo.getArea()).isEqualTo(DEFAULT_AREA);
    }

    @Test
    @Transactional
    public void getAllInstPlayGroundInfos() throws Exception {
        // Initialize the database
        instPlayGroundInfoRepository.saveAndFlush(instPlayGroundInfo);

        // Get all the instPlayGroundInfos
        restInstPlayGroundInfoMockMvc.perform(get("/api/instPlayGroundInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instPlayGroundInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].playgroundNo").value(hasItem(DEFAULT_PLAYGROUND_NO.toString())))
                .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())));
    }

    @Test
    @Transactional
    public void getInstPlayGroundInfo() throws Exception {
        // Initialize the database
        instPlayGroundInfoRepository.saveAndFlush(instPlayGroundInfo);

        // Get the instPlayGroundInfo
        restInstPlayGroundInfoMockMvc.perform(get("/api/instPlayGroundInfos/{id}", instPlayGroundInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instPlayGroundInfo.getId().intValue()))
            .andExpect(jsonPath("$.playgroundNo").value(DEFAULT_PLAYGROUND_NO.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstPlayGroundInfo() throws Exception {
        // Get the instPlayGroundInfo
        restInstPlayGroundInfoMockMvc.perform(get("/api/instPlayGroundInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstPlayGroundInfo() throws Exception {
        // Initialize the database
        instPlayGroundInfoRepository.saveAndFlush(instPlayGroundInfo);

		int databaseSizeBeforeUpdate = instPlayGroundInfoRepository.findAll().size();

        // Update the instPlayGroundInfo
        instPlayGroundInfo.setPlaygroundNo(UPDATED_PLAYGROUND_NO);
        instPlayGroundInfo.setArea(UPDATED_AREA);

        restInstPlayGroundInfoMockMvc.perform(put("/api/instPlayGroundInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instPlayGroundInfo)))
                .andExpect(status().isOk());

        // Validate the InstPlayGroundInfo in the database
        List<InstPlayGroundInfo> instPlayGroundInfos = instPlayGroundInfoRepository.findAll();
        assertThat(instPlayGroundInfos).hasSize(databaseSizeBeforeUpdate);
        InstPlayGroundInfo testInstPlayGroundInfo = instPlayGroundInfos.get(instPlayGroundInfos.size() - 1);
        assertThat(testInstPlayGroundInfo.getPlaygroundNo()).isEqualTo(UPDATED_PLAYGROUND_NO);
        assertThat(testInstPlayGroundInfo.getArea()).isEqualTo(UPDATED_AREA);
    }

    @Test
    @Transactional
    public void deleteInstPlayGroundInfo() throws Exception {
        // Initialize the database
        instPlayGroundInfoRepository.saveAndFlush(instPlayGroundInfo);

		int databaseSizeBeforeDelete = instPlayGroundInfoRepository.findAll().size();

        // Get the instPlayGroundInfo
        restInstPlayGroundInfoMockMvc.perform(delete("/api/instPlayGroundInfos/{id}", instPlayGroundInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstPlayGroundInfo> instPlayGroundInfos = instPlayGroundInfoRepository.findAll();
        assertThat(instPlayGroundInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
