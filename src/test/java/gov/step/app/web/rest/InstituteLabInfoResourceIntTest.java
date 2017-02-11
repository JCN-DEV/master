package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstituteLabInfo;
import gov.step.app.repository.InstituteLabInfoRepository;
import gov.step.app.repository.search.InstituteLabInfoSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the InstituteLabInfoResource REST controller.
 *
 * @see InstituteLabInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstituteLabInfoResourceIntTest {

    private static final String DEFAULT_NAME_OR_NUMBER = "AAAAA";
    private static final String UPDATED_NAME_OR_NUMBER = "BBBBB";
    private static final String DEFAULT_BUILDING_NAME_OR_NUMBER = "AAAAA";
    private static final String UPDATED_BUILDING_NAME_OR_NUMBER = "BBBBB";
    private static final String DEFAULT_LENGTH = "AAAAA";
    private static final String UPDATED_LENGTH = "BBBBB";
    private static final String DEFAULT_WIDTH = "AAAAA";
    private static final String UPDATED_WIDTH = "BBBBB";

    private static final BigDecimal DEFAULT_TOTAL_BOOKS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_BOOKS = new BigDecimal(2);

    @Inject
    private InstituteLabInfoRepository instituteLabInfoRepository;

    @Inject
    private InstituteLabInfoSearchRepository instituteLabInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstituteLabInfoMockMvc;

    private InstituteLabInfo instituteLabInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteLabInfoResource instituteLabInfoResource = new InstituteLabInfoResource();
        ReflectionTestUtils.setField(instituteLabInfoResource, "instituteLabInfoRepository", instituteLabInfoRepository);
        ReflectionTestUtils.setField(instituteLabInfoResource, "instituteLabInfoSearchRepository", instituteLabInfoSearchRepository);
        this.restInstituteLabInfoMockMvc = MockMvcBuilders.standaloneSetup(instituteLabInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instituteLabInfo = new InstituteLabInfo();
        instituteLabInfo.setNameOrNumber(DEFAULT_NAME_OR_NUMBER);
        instituteLabInfo.setBuildingNameOrNumber(DEFAULT_BUILDING_NAME_OR_NUMBER);
        instituteLabInfo.setLength(DEFAULT_LENGTH);
        instituteLabInfo.setWidth(DEFAULT_WIDTH);
        instituteLabInfo.setTotalBooks(DEFAULT_TOTAL_BOOKS);
    }

    @Test
    @Transactional
    public void createInstituteLabInfo() throws Exception {
        int databaseSizeBeforeCreate = instituteLabInfoRepository.findAll().size();

        // Create the InstituteLabInfo

        restInstituteLabInfoMockMvc.perform(post("/api/instituteLabInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLabInfo)))
                .andExpect(status().isCreated());

        // Validate the InstituteLabInfo in the database
        List<InstituteLabInfo> instituteLabInfos = instituteLabInfoRepository.findAll();
        assertThat(instituteLabInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstituteLabInfo testInstituteLabInfo = instituteLabInfos.get(instituteLabInfos.size() - 1);
        assertThat(testInstituteLabInfo.getNameOrNumber()).isEqualTo(DEFAULT_NAME_OR_NUMBER);
        assertThat(testInstituteLabInfo.getBuildingNameOrNumber()).isEqualTo(DEFAULT_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstituteLabInfo.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testInstituteLabInfo.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testInstituteLabInfo.getTotalBooks()).isEqualTo(DEFAULT_TOTAL_BOOKS);
    }

    @Test
    @Transactional
    public void getAllInstituteLabInfos() throws Exception {
        // Initialize the database
        instituteLabInfoRepository.saveAndFlush(instituteLabInfo);

        // Get all the instituteLabInfos
        restInstituteLabInfoMockMvc.perform(get("/api/instituteLabInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instituteLabInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameOrNumber").value(hasItem(DEFAULT_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].buildingNameOrNumber").value(hasItem(DEFAULT_BUILDING_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.toString())))
                .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.toString())))
                .andExpect(jsonPath("$.[*].totalBooks").value(hasItem(DEFAULT_TOTAL_BOOKS.intValue())));
    }

    @Test
    @Transactional
    public void getInstituteLabInfo() throws Exception {
        // Initialize the database
        instituteLabInfoRepository.saveAndFlush(instituteLabInfo);

        // Get the instituteLabInfo
        restInstituteLabInfoMockMvc.perform(get("/api/instituteLabInfos/{id}", instituteLabInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instituteLabInfo.getId().intValue()))
            .andExpect(jsonPath("$.nameOrNumber").value(DEFAULT_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.buildingNameOrNumber").value(DEFAULT_BUILDING_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.toString()))
            .andExpect(jsonPath("$.totalBooks").value(DEFAULT_TOTAL_BOOKS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstituteLabInfo() throws Exception {
        // Get the instituteLabInfo
        restInstituteLabInfoMockMvc.perform(get("/api/instituteLabInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituteLabInfo() throws Exception {
        // Initialize the database
        instituteLabInfoRepository.saveAndFlush(instituteLabInfo);

		int databaseSizeBeforeUpdate = instituteLabInfoRepository.findAll().size();

        // Update the instituteLabInfo
        instituteLabInfo.setNameOrNumber(UPDATED_NAME_OR_NUMBER);
        instituteLabInfo.setBuildingNameOrNumber(UPDATED_BUILDING_NAME_OR_NUMBER);
        instituteLabInfo.setLength(UPDATED_LENGTH);
        instituteLabInfo.setWidth(UPDATED_WIDTH);
        instituteLabInfo.setTotalBooks(UPDATED_TOTAL_BOOKS);

        restInstituteLabInfoMockMvc.perform(put("/api/instituteLabInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteLabInfo)))
                .andExpect(status().isOk());

        // Validate the InstituteLabInfo in the database
        List<InstituteLabInfo> instituteLabInfos = instituteLabInfoRepository.findAll();
        assertThat(instituteLabInfos).hasSize(databaseSizeBeforeUpdate);
        InstituteLabInfo testInstituteLabInfo = instituteLabInfos.get(instituteLabInfos.size() - 1);
        assertThat(testInstituteLabInfo.getNameOrNumber()).isEqualTo(UPDATED_NAME_OR_NUMBER);
        assertThat(testInstituteLabInfo.getBuildingNameOrNumber()).isEqualTo(UPDATED_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstituteLabInfo.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testInstituteLabInfo.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testInstituteLabInfo.getTotalBooks()).isEqualTo(UPDATED_TOTAL_BOOKS);
    }

    @Test
    @Transactional
    public void deleteInstituteLabInfo() throws Exception {
        // Initialize the database
        instituteLabInfoRepository.saveAndFlush(instituteLabInfo);

		int databaseSizeBeforeDelete = instituteLabInfoRepository.findAll().size();

        // Get the instituteLabInfo
        restInstituteLabInfoMockMvc.perform(delete("/api/instituteLabInfos/{id}", instituteLabInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstituteLabInfo> instituteLabInfos = instituteLabInfoRepository.findAll();
        assertThat(instituteLabInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
