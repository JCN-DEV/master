package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstShopInfoTemp;
import gov.step.app.repository.InstShopInfoTempRepository;
import gov.step.app.repository.search.InstShopInfoTempSearchRepository;

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
 * Test class for the InstShopInfoTempResource REST controller.
 *
 * @see InstShopInfoTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstShopInfoTempResourceTest {

    private static final String DEFAULT_NAME_OR_NUMBER = "AAAAA";
    private static final String UPDATED_NAME_OR_NUMBER = "BBBBB";
    private static final String DEFAULT_BUILDING_NAME_OR_NUMBER = "AAAAA";
    private static final String UPDATED_BUILDING_NAME_OR_NUMBER = "BBBBB";
    private static final String DEFAULT_LENGTH = "AAAAA";
    private static final String UPDATED_LENGTH = "BBBBB";
    private static final String DEFAULT_WIDTH = "AAAAA";
    private static final String UPDATED_WIDTH = "BBBBB";

    @Inject
    private InstShopInfoTempRepository instShopInfoTempRepository;

    @Inject
    private InstShopInfoTempSearchRepository instShopInfoTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstShopInfoTempMockMvc;

    private InstShopInfoTemp instShopInfoTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstShopInfoTempResource instShopInfoTempResource = new InstShopInfoTempResource();
        ReflectionTestUtils.setField(instShopInfoTempResource, "instShopInfoTempRepository", instShopInfoTempRepository);
        ReflectionTestUtils.setField(instShopInfoTempResource, "instShopInfoTempSearchRepository", instShopInfoTempSearchRepository);
        this.restInstShopInfoTempMockMvc = MockMvcBuilders.standaloneSetup(instShopInfoTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instShopInfoTemp = new InstShopInfoTemp();
        instShopInfoTemp.setNameOrNumber(DEFAULT_NAME_OR_NUMBER);
        instShopInfoTemp.setBuildingNameOrNumber(DEFAULT_BUILDING_NAME_OR_NUMBER);
        instShopInfoTemp.setLength(DEFAULT_LENGTH);
        instShopInfoTemp.setWidth(DEFAULT_WIDTH);
    }

    @Test
    @Transactional
    public void createInstShopInfoTemp() throws Exception {
        int databaseSizeBeforeCreate = instShopInfoTempRepository.findAll().size();

        // Create the InstShopInfoTemp

        restInstShopInfoTempMockMvc.perform(post("/api/instShopInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instShopInfoTemp)))
                .andExpect(status().isCreated());

        // Validate the InstShopInfoTemp in the database
        List<InstShopInfoTemp> instShopInfoTemps = instShopInfoTempRepository.findAll();
        assertThat(instShopInfoTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstShopInfoTemp testInstShopInfoTemp = instShopInfoTemps.get(instShopInfoTemps.size() - 1);
        assertThat(testInstShopInfoTemp.getNameOrNumber()).isEqualTo(DEFAULT_NAME_OR_NUMBER);
        assertThat(testInstShopInfoTemp.getBuildingNameOrNumber()).isEqualTo(DEFAULT_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstShopInfoTemp.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testInstShopInfoTemp.getWidth()).isEqualTo(DEFAULT_WIDTH);
    }

    @Test
    @Transactional
    public void getAllInstShopInfoTemps() throws Exception {
        // Initialize the database
        instShopInfoTempRepository.saveAndFlush(instShopInfoTemp);

        // Get all the instShopInfoTemps
        restInstShopInfoTempMockMvc.perform(get("/api/instShopInfoTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instShopInfoTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameOrNumber").value(hasItem(DEFAULT_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].buildingNameOrNumber").value(hasItem(DEFAULT_BUILDING_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.toString())))
                .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.toString())));
    }

    @Test
    @Transactional
    public void getInstShopInfoTemp() throws Exception {
        // Initialize the database
        instShopInfoTempRepository.saveAndFlush(instShopInfoTemp);

        // Get the instShopInfoTemp
        restInstShopInfoTempMockMvc.perform(get("/api/instShopInfoTemps/{id}", instShopInfoTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instShopInfoTemp.getId().intValue()))
            .andExpect(jsonPath("$.nameOrNumber").value(DEFAULT_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.buildingNameOrNumber").value(DEFAULT_BUILDING_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstShopInfoTemp() throws Exception {
        // Get the instShopInfoTemp
        restInstShopInfoTempMockMvc.perform(get("/api/instShopInfoTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstShopInfoTemp() throws Exception {
        // Initialize the database
        instShopInfoTempRepository.saveAndFlush(instShopInfoTemp);

		int databaseSizeBeforeUpdate = instShopInfoTempRepository.findAll().size();

        // Update the instShopInfoTemp
        instShopInfoTemp.setNameOrNumber(UPDATED_NAME_OR_NUMBER);
        instShopInfoTemp.setBuildingNameOrNumber(UPDATED_BUILDING_NAME_OR_NUMBER);
        instShopInfoTemp.setLength(UPDATED_LENGTH);
        instShopInfoTemp.setWidth(UPDATED_WIDTH);

        restInstShopInfoTempMockMvc.perform(put("/api/instShopInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instShopInfoTemp)))
                .andExpect(status().isOk());

        // Validate the InstShopInfoTemp in the database
        List<InstShopInfoTemp> instShopInfoTemps = instShopInfoTempRepository.findAll();
        assertThat(instShopInfoTemps).hasSize(databaseSizeBeforeUpdate);
        InstShopInfoTemp testInstShopInfoTemp = instShopInfoTemps.get(instShopInfoTemps.size() - 1);
        assertThat(testInstShopInfoTemp.getNameOrNumber()).isEqualTo(UPDATED_NAME_OR_NUMBER);
        assertThat(testInstShopInfoTemp.getBuildingNameOrNumber()).isEqualTo(UPDATED_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstShopInfoTemp.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testInstShopInfoTemp.getWidth()).isEqualTo(UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void deleteInstShopInfoTemp() throws Exception {
        // Initialize the database
        instShopInfoTempRepository.saveAndFlush(instShopInfoTemp);

		int databaseSizeBeforeDelete = instShopInfoTempRepository.findAll().size();

        // Get the instShopInfoTemp
        restInstShopInfoTempMockMvc.perform(delete("/api/instShopInfoTemps/{id}", instShopInfoTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstShopInfoTemp> instShopInfoTemps = instShopInfoTempRepository.findAll();
        assertThat(instShopInfoTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
