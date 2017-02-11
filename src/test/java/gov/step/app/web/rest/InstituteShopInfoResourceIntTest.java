package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstituteShopInfo;
import gov.step.app.repository.InstituteShopInfoRepository;
import gov.step.app.repository.search.InstituteShopInfoSearchRepository;

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
 * Test class for the InstituteShopInfoResource REST controller.
 *
 * @see InstituteShopInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstituteShopInfoResourceIntTest {

    private static final String DEFAULT_NAME_OR_NUMBER = "AAAAA";
    private static final String UPDATED_NAME_OR_NUMBER = "BBBBB";
    private static final String DEFAULT_BUILDING_NAME_OR_NUMBER = "AAAAA";
    private static final String UPDATED_BUILDING_NAME_OR_NUMBER = "BBBBB";
    private static final String DEFAULT_LENGTH = "AAAAA";
    private static final String UPDATED_LENGTH = "BBBBB";
    private static final String DEFAULT_WIDTH = "AAAAA";
    private static final String UPDATED_WIDTH = "BBBBB";

    @Inject
    private InstituteShopInfoRepository instituteShopInfoRepository;

    @Inject
    private InstituteShopInfoSearchRepository instituteShopInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstituteShopInfoMockMvc;

    private InstituteShopInfo instituteShopInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteShopInfoResource instituteShopInfoResource = new InstituteShopInfoResource();
        ReflectionTestUtils.setField(instituteShopInfoResource, "instituteShopInfoRepository", instituteShopInfoRepository);
        ReflectionTestUtils.setField(instituteShopInfoResource, "instituteShopInfoSearchRepository", instituteShopInfoSearchRepository);
        this.restInstituteShopInfoMockMvc = MockMvcBuilders.standaloneSetup(instituteShopInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instituteShopInfo = new InstituteShopInfo();
        instituteShopInfo.setNameOrNumber(DEFAULT_NAME_OR_NUMBER);
        instituteShopInfo.setBuildingNameOrNumber(DEFAULT_BUILDING_NAME_OR_NUMBER);
        instituteShopInfo.setLength(DEFAULT_LENGTH);
        instituteShopInfo.setWidth(DEFAULT_WIDTH);
    }

    @Test
    @Transactional
    public void createInstituteShopInfo() throws Exception {
        int databaseSizeBeforeCreate = instituteShopInfoRepository.findAll().size();

        // Create the InstituteShopInfo

        restInstituteShopInfoMockMvc.perform(post("/api/instituteShopInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteShopInfo)))
                .andExpect(status().isCreated());

        // Validate the InstituteShopInfo in the database
        List<InstituteShopInfo> instituteShopInfos = instituteShopInfoRepository.findAll();
        assertThat(instituteShopInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstituteShopInfo testInstituteShopInfo = instituteShopInfos.get(instituteShopInfos.size() - 1);
        assertThat(testInstituteShopInfo.getNameOrNumber()).isEqualTo(DEFAULT_NAME_OR_NUMBER);
        assertThat(testInstituteShopInfo.getBuildingNameOrNumber()).isEqualTo(DEFAULT_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstituteShopInfo.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testInstituteShopInfo.getWidth()).isEqualTo(DEFAULT_WIDTH);
    }

    @Test
    @Transactional
    public void getAllInstituteShopInfos() throws Exception {
        // Initialize the database
        instituteShopInfoRepository.saveAndFlush(instituteShopInfo);

        // Get all the instituteShopInfos
        restInstituteShopInfoMockMvc.perform(get("/api/instituteShopInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instituteShopInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameOrNumber").value(hasItem(DEFAULT_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].buildingNameOrNumber").value(hasItem(DEFAULT_BUILDING_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.toString())))
                .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.toString())));
    }

    @Test
    @Transactional
    public void getInstituteShopInfo() throws Exception {
        // Initialize the database
        instituteShopInfoRepository.saveAndFlush(instituteShopInfo);

        // Get the instituteShopInfo
        restInstituteShopInfoMockMvc.perform(get("/api/instituteShopInfos/{id}", instituteShopInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instituteShopInfo.getId().intValue()))
            .andExpect(jsonPath("$.nameOrNumber").value(DEFAULT_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.buildingNameOrNumber").value(DEFAULT_BUILDING_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstituteShopInfo() throws Exception {
        // Get the instituteShopInfo
        restInstituteShopInfoMockMvc.perform(get("/api/instituteShopInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituteShopInfo() throws Exception {
        // Initialize the database
        instituteShopInfoRepository.saveAndFlush(instituteShopInfo);

		int databaseSizeBeforeUpdate = instituteShopInfoRepository.findAll().size();

        // Update the instituteShopInfo
        instituteShopInfo.setNameOrNumber(UPDATED_NAME_OR_NUMBER);
        instituteShopInfo.setBuildingNameOrNumber(UPDATED_BUILDING_NAME_OR_NUMBER);
        instituteShopInfo.setLength(UPDATED_LENGTH);
        instituteShopInfo.setWidth(UPDATED_WIDTH);

        restInstituteShopInfoMockMvc.perform(put("/api/instituteShopInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteShopInfo)))
                .andExpect(status().isOk());

        // Validate the InstituteShopInfo in the database
        List<InstituteShopInfo> instituteShopInfos = instituteShopInfoRepository.findAll();
        assertThat(instituteShopInfos).hasSize(databaseSizeBeforeUpdate);
        InstituteShopInfo testInstituteShopInfo = instituteShopInfos.get(instituteShopInfos.size() - 1);
        assertThat(testInstituteShopInfo.getNameOrNumber()).isEqualTo(UPDATED_NAME_OR_NUMBER);
        assertThat(testInstituteShopInfo.getBuildingNameOrNumber()).isEqualTo(UPDATED_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstituteShopInfo.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testInstituteShopInfo.getWidth()).isEqualTo(UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void deleteInstituteShopInfo() throws Exception {
        // Initialize the database
        instituteShopInfoRepository.saveAndFlush(instituteShopInfo);

		int databaseSizeBeforeDelete = instituteShopInfoRepository.findAll().size();

        // Get the instituteShopInfo
        restInstituteShopInfoMockMvc.perform(delete("/api/instituteShopInfos/{id}", instituteShopInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstituteShopInfo> instituteShopInfos = instituteShopInfoRepository.findAll();
        assertThat(instituteShopInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
