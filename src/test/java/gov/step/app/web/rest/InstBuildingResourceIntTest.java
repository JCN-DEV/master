package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstBuilding;
import gov.step.app.repository.InstBuildingRepository;
import gov.step.app.repository.search.InstBuildingSearchRepository;

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
 * Test class for the InstBuildingResource REST controller.
 *
 * @see InstBuildingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstBuildingResourceIntTest {

    private static final String DEFAULT_TOTAL_AREA = "AAAAA";
    private static final String UPDATED_TOTAL_AREA = "BBBBB";

    private static final BigDecimal DEFAULT_TOTAL_ROOM = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_ROOM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CLASS_ROOM = new BigDecimal(1);
    private static final BigDecimal UPDATED_CLASS_ROOM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OFFICE_ROOM = new BigDecimal(1);
    private static final BigDecimal UPDATED_OFFICE_ROOM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OTHER_ROOM = new BigDecimal(1);
    private static final BigDecimal UPDATED_OTHER_ROOM = new BigDecimal(2);

    @Inject
    private InstBuildingRepository instBuildingRepository;

    @Inject
    private InstBuildingSearchRepository instBuildingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstBuildingMockMvc;

    private InstBuilding instBuilding;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstBuildingResource instBuildingResource = new InstBuildingResource();
        ReflectionTestUtils.setField(instBuildingResource, "instBuildingRepository", instBuildingRepository);
        ReflectionTestUtils.setField(instBuildingResource, "instBuildingSearchRepository", instBuildingSearchRepository);
        this.restInstBuildingMockMvc = MockMvcBuilders.standaloneSetup(instBuildingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instBuilding = new InstBuilding();
        instBuilding.setTotalArea(DEFAULT_TOTAL_AREA);
        instBuilding.setTotalRoom(DEFAULT_TOTAL_ROOM);
        instBuilding.setClassRoom(DEFAULT_CLASS_ROOM);
        instBuilding.setOfficeRoom(DEFAULT_OFFICE_ROOM);
        instBuilding.setOtherRoom(DEFAULT_OTHER_ROOM);
    }

    @Test
    @Transactional
    public void createInstBuilding() throws Exception {
        int databaseSizeBeforeCreate = instBuildingRepository.findAll().size();

        // Create the InstBuilding

        restInstBuildingMockMvc.perform(post("/api/instBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuilding)))
                .andExpect(status().isCreated());

        // Validate the InstBuilding in the database
        List<InstBuilding> instBuildings = instBuildingRepository.findAll();
        assertThat(instBuildings).hasSize(databaseSizeBeforeCreate + 1);
        InstBuilding testInstBuilding = instBuildings.get(instBuildings.size() - 1);
        assertThat(testInstBuilding.getTotalArea()).isEqualTo(DEFAULT_TOTAL_AREA);
        assertThat(testInstBuilding.getTotalRoom()).isEqualTo(DEFAULT_TOTAL_ROOM);
        assertThat(testInstBuilding.getClassRoom()).isEqualTo(DEFAULT_CLASS_ROOM);
        assertThat(testInstBuilding.getOfficeRoom()).isEqualTo(DEFAULT_OFFICE_ROOM);
        assertThat(testInstBuilding.getOtherRoom()).isEqualTo(DEFAULT_OTHER_ROOM);
    }

    @Test
    @Transactional
    public void checkTotalAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = instBuildingRepository.findAll().size();
        // set the field null
        instBuilding.setTotalArea(null);

        // Create the InstBuilding, which fails.

        restInstBuildingMockMvc.perform(post("/api/instBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuilding)))
                .andExpect(status().isBadRequest());

        List<InstBuilding> instBuildings = instBuildingRepository.findAll();
        assertThat(instBuildings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = instBuildingRepository.findAll().size();
        // set the field null
        instBuilding.setTotalRoom(null);

        // Create the InstBuilding, which fails.

        restInstBuildingMockMvc.perform(post("/api/instBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuilding)))
                .andExpect(status().isBadRequest());

        List<InstBuilding> instBuildings = instBuildingRepository.findAll();
        assertThat(instBuildings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClassRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = instBuildingRepository.findAll().size();
        // set the field null
        instBuilding.setClassRoom(null);

        // Create the InstBuilding, which fails.

        restInstBuildingMockMvc.perform(post("/api/instBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuilding)))
                .andExpect(status().isBadRequest());

        List<InstBuilding> instBuildings = instBuildingRepository.findAll();
        assertThat(instBuildings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfficeRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = instBuildingRepository.findAll().size();
        // set the field null
        instBuilding.setOfficeRoom(null);

        // Create the InstBuilding, which fails.

        restInstBuildingMockMvc.perform(post("/api/instBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuilding)))
                .andExpect(status().isBadRequest());

        List<InstBuilding> instBuildings = instBuildingRepository.findAll();
        assertThat(instBuildings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstBuildings() throws Exception {
        // Initialize the database
        instBuildingRepository.saveAndFlush(instBuilding);

        // Get all the instBuildings
        restInstBuildingMockMvc.perform(get("/api/instBuildings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instBuilding.getId().intValue())))
                .andExpect(jsonPath("$.[*].totalArea").value(hasItem(DEFAULT_TOTAL_AREA.toString())))
                .andExpect(jsonPath("$.[*].totalRoom").value(hasItem(DEFAULT_TOTAL_ROOM.intValue())))
                .andExpect(jsonPath("$.[*].classRoom").value(hasItem(DEFAULT_CLASS_ROOM.intValue())))
                .andExpect(jsonPath("$.[*].officeRoom").value(hasItem(DEFAULT_OFFICE_ROOM.intValue())))
                .andExpect(jsonPath("$.[*].otherRoom").value(hasItem(DEFAULT_OTHER_ROOM.intValue())));
    }

    @Test
    @Transactional
    public void getInstBuilding() throws Exception {
        // Initialize the database
        instBuildingRepository.saveAndFlush(instBuilding);

        // Get the instBuilding
        restInstBuildingMockMvc.perform(get("/api/instBuildings/{id}", instBuilding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instBuilding.getId().intValue()))
            .andExpect(jsonPath("$.totalArea").value(DEFAULT_TOTAL_AREA.toString()))
            .andExpect(jsonPath("$.totalRoom").value(DEFAULT_TOTAL_ROOM.intValue()))
            .andExpect(jsonPath("$.classRoom").value(DEFAULT_CLASS_ROOM.intValue()))
            .andExpect(jsonPath("$.officeRoom").value(DEFAULT_OFFICE_ROOM.intValue()))
            .andExpect(jsonPath("$.otherRoom").value(DEFAULT_OTHER_ROOM.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstBuilding() throws Exception {
        // Get the instBuilding
        restInstBuildingMockMvc.perform(get("/api/instBuildings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstBuilding() throws Exception {
        // Initialize the database
        instBuildingRepository.saveAndFlush(instBuilding);

		int databaseSizeBeforeUpdate = instBuildingRepository.findAll().size();

        // Update the instBuilding
        instBuilding.setTotalArea(UPDATED_TOTAL_AREA);
        instBuilding.setTotalRoom(UPDATED_TOTAL_ROOM);
        instBuilding.setClassRoom(UPDATED_CLASS_ROOM);
        instBuilding.setOfficeRoom(UPDATED_OFFICE_ROOM);
        instBuilding.setOtherRoom(UPDATED_OTHER_ROOM);

        restInstBuildingMockMvc.perform(put("/api/instBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuilding)))
                .andExpect(status().isOk());

        // Validate the InstBuilding in the database
        List<InstBuilding> instBuildings = instBuildingRepository.findAll();
        assertThat(instBuildings).hasSize(databaseSizeBeforeUpdate);
        InstBuilding testInstBuilding = instBuildings.get(instBuildings.size() - 1);
        assertThat(testInstBuilding.getTotalArea()).isEqualTo(UPDATED_TOTAL_AREA);
        assertThat(testInstBuilding.getTotalRoom()).isEqualTo(UPDATED_TOTAL_ROOM);
        assertThat(testInstBuilding.getClassRoom()).isEqualTo(UPDATED_CLASS_ROOM);
        assertThat(testInstBuilding.getOfficeRoom()).isEqualTo(UPDATED_OFFICE_ROOM);
        assertThat(testInstBuilding.getOtherRoom()).isEqualTo(UPDATED_OTHER_ROOM);
    }

    @Test
    @Transactional
    public void deleteInstBuilding() throws Exception {
        // Initialize the database
        instBuildingRepository.saveAndFlush(instBuilding);

		int databaseSizeBeforeDelete = instBuildingRepository.findAll().size();

        // Get the instBuilding
        restInstBuildingMockMvc.perform(delete("/api/instBuildings/{id}", instBuilding.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstBuilding> instBuildings = instBuildingRepository.findAll();
        assertThat(instBuildings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
