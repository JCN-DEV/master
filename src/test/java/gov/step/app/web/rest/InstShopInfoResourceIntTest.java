package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstShopInfo;
import gov.step.app.repository.InstShopInfoRepository;
import gov.step.app.repository.search.InstShopInfoSearchRepository;

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
 * Test class for the InstShopInfoResource REST controller.
 *
 * @see InstShopInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstShopInfoResourceIntTest {

    private static final String DEFAULT_NAME_OR_NUMBER = "AAAAA";
    private static final String UPDATED_NAME_OR_NUMBER = "BBBBB";
    private static final String DEFAULT_BUILDING_NAME_OR_NUMBER = "AAAAA";
    private static final String UPDATED_BUILDING_NAME_OR_NUMBER = "BBBBB";
    private static final String DEFAULT_LENGTH = "AAAAA";
    private static final String UPDATED_LENGTH = "BBBBB";
    private static final String DEFAULT_WIDTH = "AAAAA";
    private static final String UPDATED_WIDTH = "BBBBB";

    @Inject
    private InstShopInfoRepository instShopInfoRepository;

    @Inject
    private InstShopInfoSearchRepository instShopInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstShopInfoMockMvc;

    private InstShopInfo instShopInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstShopInfoResource instShopInfoResource = new InstShopInfoResource();
        ReflectionTestUtils.setField(instShopInfoResource, "instShopInfoRepository", instShopInfoRepository);
        ReflectionTestUtils.setField(instShopInfoResource, "instShopInfoSearchRepository", instShopInfoSearchRepository);
        this.restInstShopInfoMockMvc = MockMvcBuilders.standaloneSetup(instShopInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instShopInfo = new InstShopInfo();
        instShopInfo.setNameOrNumber(DEFAULT_NAME_OR_NUMBER);
        instShopInfo.setBuildingNameOrNumber(DEFAULT_BUILDING_NAME_OR_NUMBER);
        instShopInfo.setLength(DEFAULT_LENGTH);
        instShopInfo.setWidth(DEFAULT_WIDTH);
    }

    @Test
    @Transactional
    public void createInstShopInfo() throws Exception {
        int databaseSizeBeforeCreate = instShopInfoRepository.findAll().size();

        // Create the InstShopInfo

        restInstShopInfoMockMvc.perform(post("/api/instShopInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instShopInfo)))
                .andExpect(status().isCreated());

        // Validate the InstShopInfo in the database
        List<InstShopInfo> instShopInfos = instShopInfoRepository.findAll();
        assertThat(instShopInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstShopInfo testInstShopInfo = instShopInfos.get(instShopInfos.size() - 1);
        assertThat(testInstShopInfo.getNameOrNumber()).isEqualTo(DEFAULT_NAME_OR_NUMBER);
        assertThat(testInstShopInfo.getBuildingNameOrNumber()).isEqualTo(DEFAULT_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstShopInfo.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testInstShopInfo.getWidth()).isEqualTo(DEFAULT_WIDTH);
    }

    @Test
    @Transactional
    public void getAllInstShopInfos() throws Exception {
        // Initialize the database
        instShopInfoRepository.saveAndFlush(instShopInfo);

        // Get all the instShopInfos
        restInstShopInfoMockMvc.perform(get("/api/instShopInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instShopInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameOrNumber").value(hasItem(DEFAULT_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].buildingNameOrNumber").value(hasItem(DEFAULT_BUILDING_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.toString())))
                .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.toString())));
    }

    @Test
    @Transactional
    public void getInstShopInfo() throws Exception {
        // Initialize the database
        instShopInfoRepository.saveAndFlush(instShopInfo);

        // Get the instShopInfo
        restInstShopInfoMockMvc.perform(get("/api/instShopInfos/{id}", instShopInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instShopInfo.getId().intValue()))
            .andExpect(jsonPath("$.nameOrNumber").value(DEFAULT_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.buildingNameOrNumber").value(DEFAULT_BUILDING_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstShopInfo() throws Exception {
        // Get the instShopInfo
        restInstShopInfoMockMvc.perform(get("/api/instShopInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstShopInfo() throws Exception {
        // Initialize the database
        instShopInfoRepository.saveAndFlush(instShopInfo);

		int databaseSizeBeforeUpdate = instShopInfoRepository.findAll().size();

        // Update the instShopInfo
        instShopInfo.setNameOrNumber(UPDATED_NAME_OR_NUMBER);
        instShopInfo.setBuildingNameOrNumber(UPDATED_BUILDING_NAME_OR_NUMBER);
        instShopInfo.setLength(UPDATED_LENGTH);
        instShopInfo.setWidth(UPDATED_WIDTH);

        restInstShopInfoMockMvc.perform(put("/api/instShopInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instShopInfo)))
                .andExpect(status().isOk());

        // Validate the InstShopInfo in the database
        List<InstShopInfo> instShopInfos = instShopInfoRepository.findAll();
        assertThat(instShopInfos).hasSize(databaseSizeBeforeUpdate);
        InstShopInfo testInstShopInfo = instShopInfos.get(instShopInfos.size() - 1);
        assertThat(testInstShopInfo.getNameOrNumber()).isEqualTo(UPDATED_NAME_OR_NUMBER);
        assertThat(testInstShopInfo.getBuildingNameOrNumber()).isEqualTo(UPDATED_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstShopInfo.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testInstShopInfo.getWidth()).isEqualTo(UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void deleteInstShopInfo() throws Exception {
        // Initialize the database
        instShopInfoRepository.saveAndFlush(instShopInfo);

		int databaseSizeBeforeDelete = instShopInfoRepository.findAll().size();

        // Get the instShopInfo
        restInstShopInfoMockMvc.perform(delete("/api/instShopInfos/{id}", instShopInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstShopInfo> instShopInfos = instShopInfoRepository.findAll();
        assertThat(instShopInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
