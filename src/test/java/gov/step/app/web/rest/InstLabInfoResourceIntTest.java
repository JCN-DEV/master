package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstLabInfo;
import gov.step.app.repository.InstLabInfoRepository;
import gov.step.app.repository.search.InstLabInfoSearchRepository;

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
 * Test class for the InstLabInfoResource REST controller.
 *
 * @see InstLabInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstLabInfoResourceIntTest {

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
    private InstLabInfoRepository instLabInfoRepository;

    @Inject
    private InstLabInfoSearchRepository instLabInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstLabInfoMockMvc;

    private InstLabInfo instLabInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstLabInfoResource instLabInfoResource = new InstLabInfoResource();
        ReflectionTestUtils.setField(instLabInfoResource, "instLabInfoRepository", instLabInfoRepository);
        ReflectionTestUtils.setField(instLabInfoResource, "instLabInfoSearchRepository", instLabInfoSearchRepository);
        this.restInstLabInfoMockMvc = MockMvcBuilders.standaloneSetup(instLabInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instLabInfo = new InstLabInfo();
        instLabInfo.setNameOrNumber(DEFAULT_NAME_OR_NUMBER);
        instLabInfo.setBuildingNameOrNumber(DEFAULT_BUILDING_NAME_OR_NUMBER);
        instLabInfo.setLength(DEFAULT_LENGTH);
        instLabInfo.setWidth(DEFAULT_WIDTH);
        instLabInfo.setTotalBooks(DEFAULT_TOTAL_BOOKS);
    }

    @Test
    @Transactional
    public void createInstLabInfo() throws Exception {
        int databaseSizeBeforeCreate = instLabInfoRepository.findAll().size();

        // Create the InstLabInfo

        restInstLabInfoMockMvc.perform(post("/api/instLabInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLabInfo)))
                .andExpect(status().isCreated());

        // Validate the InstLabInfo in the database
        List<InstLabInfo> instLabInfos = instLabInfoRepository.findAll();
        assertThat(instLabInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstLabInfo testInstLabInfo = instLabInfos.get(instLabInfos.size() - 1);
        assertThat(testInstLabInfo.getNameOrNumber()).isEqualTo(DEFAULT_NAME_OR_NUMBER);
        assertThat(testInstLabInfo.getBuildingNameOrNumber()).isEqualTo(DEFAULT_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstLabInfo.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testInstLabInfo.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testInstLabInfo.getTotalBooks()).isEqualTo(DEFAULT_TOTAL_BOOKS);
    }

    @Test
    @Transactional
    public void getAllInstLabInfos() throws Exception {
        // Initialize the database
        instLabInfoRepository.saveAndFlush(instLabInfo);

        // Get all the instLabInfos
        restInstLabInfoMockMvc.perform(get("/api/instLabInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instLabInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameOrNumber").value(hasItem(DEFAULT_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].buildingNameOrNumber").value(hasItem(DEFAULT_BUILDING_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.toString())))
                .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.toString())))
                .andExpect(jsonPath("$.[*].totalBooks").value(hasItem(DEFAULT_TOTAL_BOOKS.intValue())));
    }

    @Test
    @Transactional
    public void getInstLabInfo() throws Exception {
        // Initialize the database
        instLabInfoRepository.saveAndFlush(instLabInfo);

        // Get the instLabInfo
        restInstLabInfoMockMvc.perform(get("/api/instLabInfos/{id}", instLabInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instLabInfo.getId().intValue()))
            .andExpect(jsonPath("$.nameOrNumber").value(DEFAULT_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.buildingNameOrNumber").value(DEFAULT_BUILDING_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.toString()))
            .andExpect(jsonPath("$.totalBooks").value(DEFAULT_TOTAL_BOOKS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstLabInfo() throws Exception {
        // Get the instLabInfo
        restInstLabInfoMockMvc.perform(get("/api/instLabInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstLabInfo() throws Exception {
        // Initialize the database
        instLabInfoRepository.saveAndFlush(instLabInfo);

		int databaseSizeBeforeUpdate = instLabInfoRepository.findAll().size();

        // Update the instLabInfo
        instLabInfo.setNameOrNumber(UPDATED_NAME_OR_NUMBER);
        instLabInfo.setBuildingNameOrNumber(UPDATED_BUILDING_NAME_OR_NUMBER);
        instLabInfo.setLength(UPDATED_LENGTH);
        instLabInfo.setWidth(UPDATED_WIDTH);
        instLabInfo.setTotalBooks(UPDATED_TOTAL_BOOKS);

        restInstLabInfoMockMvc.perform(put("/api/instLabInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLabInfo)))
                .andExpect(status().isOk());

        // Validate the InstLabInfo in the database
        List<InstLabInfo> instLabInfos = instLabInfoRepository.findAll();
        assertThat(instLabInfos).hasSize(databaseSizeBeforeUpdate);
        InstLabInfo testInstLabInfo = instLabInfos.get(instLabInfos.size() - 1);
        assertThat(testInstLabInfo.getNameOrNumber()).isEqualTo(UPDATED_NAME_OR_NUMBER);
        assertThat(testInstLabInfo.getBuildingNameOrNumber()).isEqualTo(UPDATED_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstLabInfo.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testInstLabInfo.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testInstLabInfo.getTotalBooks()).isEqualTo(UPDATED_TOTAL_BOOKS);
    }

    @Test
    @Transactional
    public void deleteInstLabInfo() throws Exception {
        // Initialize the database
        instLabInfoRepository.saveAndFlush(instLabInfo);

		int databaseSizeBeforeDelete = instLabInfoRepository.findAll().size();

        // Get the instLabInfo
        restInstLabInfoMockMvc.perform(delete("/api/instLabInfos/{id}", instLabInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstLabInfo> instLabInfos = instLabInfoRepository.findAll();
        assertThat(instLabInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
