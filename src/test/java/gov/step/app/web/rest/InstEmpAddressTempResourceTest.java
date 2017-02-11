package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmpAddressTemp;
import gov.step.app.repository.InstEmpAddressTempRepository;
import gov.step.app.repository.search.InstEmpAddressTempSearchRepository;

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
 * Test class for the InstEmpAddressTempResource REST controller.
 *
 * @see InstEmpAddressTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmpAddressTempResourceTest {

    private static final String DEFAULT_VILLAGE_OR_HOUSE = "AAAAA";
    private static final String UPDATED_VILLAGE_OR_HOUSE = "BBBBB";
    private static final String DEFAULT_ROAD_BLOCK_SECTOR = "AAAAA";
    private static final String UPDATED_ROAD_BLOCK_SECTOR = "BBBBB";
    private static final String DEFAULT_POST = "AAAAA";
    private static final String UPDATED_POST = "BBBBB";
    private static final String DEFAULT_PREV_VILLAGE_OR_HOUSE = "AAAAA";
    private static final String UPDATED_PREV_VILLAGE_OR_HOUSE = "BBBBB";
    private static final String DEFAULT_PREV_ROAD_BLOCK_SECTOR = "AAAAA";
    private static final String UPDATED_PREV_ROAD_BLOCK_SECTOR = "BBBBB";
    private static final String DEFAULT_PREV_POST = "AAAAA";
    private static final String UPDATED_PREV_POST = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private InstEmpAddressTempRepository instEmpAddressTempRepository;

    @Inject
    private InstEmpAddressTempSearchRepository instEmpAddressTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmpAddressTempMockMvc;

    private InstEmpAddressTemp instEmpAddressTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmpAddressTempResource instEmpAddressTempResource = new InstEmpAddressTempResource();
        ReflectionTestUtils.setField(instEmpAddressTempResource, "instEmpAddressTempRepository", instEmpAddressTempRepository);
        ReflectionTestUtils.setField(instEmpAddressTempResource, "instEmpAddressTempSearchRepository", instEmpAddressTempSearchRepository);
        this.restInstEmpAddressTempMockMvc = MockMvcBuilders.standaloneSetup(instEmpAddressTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmpAddressTemp = new InstEmpAddressTemp();
        instEmpAddressTemp.setVillageOrHouse(DEFAULT_VILLAGE_OR_HOUSE);
        instEmpAddressTemp.setRoadBlockSector(DEFAULT_ROAD_BLOCK_SECTOR);
        instEmpAddressTemp.setPost(DEFAULT_POST);
        instEmpAddressTemp.setPrevVillageOrHouse(DEFAULT_PREV_VILLAGE_OR_HOUSE);
        instEmpAddressTemp.setPrevRoadBlockSector(DEFAULT_PREV_ROAD_BLOCK_SECTOR);
        instEmpAddressTemp.setPrevPost(DEFAULT_PREV_POST);
        instEmpAddressTemp.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstEmpAddressTemp() throws Exception {
        int databaseSizeBeforeCreate = instEmpAddressTempRepository.findAll().size();

        // Create the InstEmpAddressTemp

        restInstEmpAddressTempMockMvc.perform(post("/api/instEmpAddressTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpAddressTemp)))
                .andExpect(status().isCreated());

        // Validate the InstEmpAddressTemp in the database
        List<InstEmpAddressTemp> instEmpAddressTemps = instEmpAddressTempRepository.findAll();
        assertThat(instEmpAddressTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstEmpAddressTemp testInstEmpAddressTemp = instEmpAddressTemps.get(instEmpAddressTemps.size() - 1);
        assertThat(testInstEmpAddressTemp.getVillageOrHouse()).isEqualTo(DEFAULT_VILLAGE_OR_HOUSE);
        assertThat(testInstEmpAddressTemp.getRoadBlockSector()).isEqualTo(DEFAULT_ROAD_BLOCK_SECTOR);
        assertThat(testInstEmpAddressTemp.getPost()).isEqualTo(DEFAULT_POST);
        assertThat(testInstEmpAddressTemp.getPrevVillageOrHouse()).isEqualTo(DEFAULT_PREV_VILLAGE_OR_HOUSE);
        assertThat(testInstEmpAddressTemp.getPrevRoadBlockSector()).isEqualTo(DEFAULT_PREV_ROAD_BLOCK_SECTOR);
        assertThat(testInstEmpAddressTemp.getPrevPost()).isEqualTo(DEFAULT_PREV_POST);
        assertThat(testInstEmpAddressTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstEmpAddressTemps() throws Exception {
        // Initialize the database
        instEmpAddressTempRepository.saveAndFlush(instEmpAddressTemp);

        // Get all the instEmpAddressTemps
        restInstEmpAddressTempMockMvc.perform(get("/api/instEmpAddressTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmpAddressTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].villageOrHouse").value(hasItem(DEFAULT_VILLAGE_OR_HOUSE.toString())))
                .andExpect(jsonPath("$.[*].roadBlockSector").value(hasItem(DEFAULT_ROAD_BLOCK_SECTOR.toString())))
                .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST.toString())))
                .andExpect(jsonPath("$.[*].prevVillageOrHouse").value(hasItem(DEFAULT_PREV_VILLAGE_OR_HOUSE.toString())))
                .andExpect(jsonPath("$.[*].prevRoadBlockSector").value(hasItem(DEFAULT_PREV_ROAD_BLOCK_SECTOR.toString())))
                .andExpect(jsonPath("$.[*].prevPost").value(hasItem(DEFAULT_PREV_POST.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getInstEmpAddressTemp() throws Exception {
        // Initialize the database
        instEmpAddressTempRepository.saveAndFlush(instEmpAddressTemp);

        // Get the instEmpAddressTemp
        restInstEmpAddressTempMockMvc.perform(get("/api/instEmpAddressTemps/{id}", instEmpAddressTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmpAddressTemp.getId().intValue()))
            .andExpect(jsonPath("$.villageOrHouse").value(DEFAULT_VILLAGE_OR_HOUSE.toString()))
            .andExpect(jsonPath("$.roadBlockSector").value(DEFAULT_ROAD_BLOCK_SECTOR.toString()))
            .andExpect(jsonPath("$.post").value(DEFAULT_POST.toString()))
            .andExpect(jsonPath("$.prevVillageOrHouse").value(DEFAULT_PREV_VILLAGE_OR_HOUSE.toString()))
            .andExpect(jsonPath("$.prevRoadBlockSector").value(DEFAULT_PREV_ROAD_BLOCK_SECTOR.toString()))
            .andExpect(jsonPath("$.prevPost").value(DEFAULT_PREV_POST.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmpAddressTemp() throws Exception {
        // Get the instEmpAddressTemp
        restInstEmpAddressTempMockMvc.perform(get("/api/instEmpAddressTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmpAddressTemp() throws Exception {
        // Initialize the database
        instEmpAddressTempRepository.saveAndFlush(instEmpAddressTemp);

		int databaseSizeBeforeUpdate = instEmpAddressTempRepository.findAll().size();

        // Update the instEmpAddressTemp
        instEmpAddressTemp.setVillageOrHouse(UPDATED_VILLAGE_OR_HOUSE);
        instEmpAddressTemp.setRoadBlockSector(UPDATED_ROAD_BLOCK_SECTOR);
        instEmpAddressTemp.setPost(UPDATED_POST);
        instEmpAddressTemp.setPrevVillageOrHouse(UPDATED_PREV_VILLAGE_OR_HOUSE);
        instEmpAddressTemp.setPrevRoadBlockSector(UPDATED_PREV_ROAD_BLOCK_SECTOR);
        instEmpAddressTemp.setPrevPost(UPDATED_PREV_POST);
        instEmpAddressTemp.setStatus(UPDATED_STATUS);

        restInstEmpAddressTempMockMvc.perform(put("/api/instEmpAddressTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpAddressTemp)))
                .andExpect(status().isOk());

        // Validate the InstEmpAddressTemp in the database
        List<InstEmpAddressTemp> instEmpAddressTemps = instEmpAddressTempRepository.findAll();
        assertThat(instEmpAddressTemps).hasSize(databaseSizeBeforeUpdate);
        InstEmpAddressTemp testInstEmpAddressTemp = instEmpAddressTemps.get(instEmpAddressTemps.size() - 1);
        assertThat(testInstEmpAddressTemp.getVillageOrHouse()).isEqualTo(UPDATED_VILLAGE_OR_HOUSE);
        assertThat(testInstEmpAddressTemp.getRoadBlockSector()).isEqualTo(UPDATED_ROAD_BLOCK_SECTOR);
        assertThat(testInstEmpAddressTemp.getPost()).isEqualTo(UPDATED_POST);
        assertThat(testInstEmpAddressTemp.getPrevVillageOrHouse()).isEqualTo(UPDATED_PREV_VILLAGE_OR_HOUSE);
        assertThat(testInstEmpAddressTemp.getPrevRoadBlockSector()).isEqualTo(UPDATED_PREV_ROAD_BLOCK_SECTOR);
        assertThat(testInstEmpAddressTemp.getPrevPost()).isEqualTo(UPDATED_PREV_POST);
        assertThat(testInstEmpAddressTemp.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstEmpAddressTemp() throws Exception {
        // Initialize the database
        instEmpAddressTempRepository.saveAndFlush(instEmpAddressTemp);

		int databaseSizeBeforeDelete = instEmpAddressTempRepository.findAll().size();

        // Get the instEmpAddressTemp
        restInstEmpAddressTempMockMvc.perform(delete("/api/instEmpAddressTemps/{id}", instEmpAddressTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmpAddressTemp> instEmpAddressTemps = instEmpAddressTempRepository.findAll();
        assertThat(instEmpAddressTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
