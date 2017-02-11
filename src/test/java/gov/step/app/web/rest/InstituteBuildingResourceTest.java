package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstituteBuilding;
import gov.step.app.repository.InstituteBuildingRepository;
import gov.step.app.repository.search.InstituteBuildingSearchRepository;

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
 * Test class for the InstituteBuildingResource REST controller.
 *
 * @see InstituteBuildingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstituteBuildingResourceTest {

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
    private InstituteBuildingRepository instituteBuildingRepository;

    @Inject
    private InstituteBuildingSearchRepository instituteBuildingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstituteBuildingMockMvc;

    private InstituteBuilding instituteBuilding;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteBuildingResource instituteBuildingResource = new InstituteBuildingResource();
        ReflectionTestUtils.setField(instituteBuildingResource, "instituteBuildingRepository", instituteBuildingRepository);
        ReflectionTestUtils.setField(instituteBuildingResource, "instituteBuildingSearchRepository", instituteBuildingSearchRepository);
        this.restInstituteBuildingMockMvc = MockMvcBuilders.standaloneSetup(instituteBuildingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instituteBuilding = new InstituteBuilding();
        instituteBuilding.setTotalArea(DEFAULT_TOTAL_AREA);
        instituteBuilding.setTotalRoom(DEFAULT_TOTAL_ROOM);
        instituteBuilding.setClassRoom(DEFAULT_CLASS_ROOM);
        instituteBuilding.setOfficeRoom(DEFAULT_OFFICE_ROOM);
        instituteBuilding.setOtherRoom(DEFAULT_OTHER_ROOM);
    }

    @Test
    @Transactional
    public void createInstituteBuilding() throws Exception {
        int databaseSizeBeforeCreate = instituteBuildingRepository.findAll().size();

        // Create the InstituteBuilding

        restInstituteBuildingMockMvc.perform(post("/api/instituteBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteBuilding)))
                .andExpect(status().isCreated());

        // Validate the InstituteBuilding in the database
        List<InstituteBuilding> instituteBuildings = instituteBuildingRepository.findAll();
        assertThat(instituteBuildings).hasSize(databaseSizeBeforeCreate + 1);
        InstituteBuilding testInstituteBuilding = instituteBuildings.get(instituteBuildings.size() - 1);
        assertThat(testInstituteBuilding.getTotalArea()).isEqualTo(DEFAULT_TOTAL_AREA);
        assertThat(testInstituteBuilding.getTotalRoom()).isEqualTo(DEFAULT_TOTAL_ROOM);
        assertThat(testInstituteBuilding.getClassRoom()).isEqualTo(DEFAULT_CLASS_ROOM);
        assertThat(testInstituteBuilding.getOfficeRoom()).isEqualTo(DEFAULT_OFFICE_ROOM);
        assertThat(testInstituteBuilding.getOtherRoom()).isEqualTo(DEFAULT_OTHER_ROOM);
    }

    @Test
    @Transactional
    public void checkTotalAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteBuildingRepository.findAll().size();
        // set the field null
        instituteBuilding.setTotalArea(null);

        // Create the InstituteBuilding, which fails.

        restInstituteBuildingMockMvc.perform(post("/api/instituteBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteBuilding)))
                .andExpect(status().isBadRequest());

        List<InstituteBuilding> instituteBuildings = instituteBuildingRepository.findAll();
        assertThat(instituteBuildings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteBuildingRepository.findAll().size();
        // set the field null
        instituteBuilding.setTotalRoom(null);

        // Create the InstituteBuilding, which fails.

        restInstituteBuildingMockMvc.perform(post("/api/instituteBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteBuilding)))
                .andExpect(status().isBadRequest());

        List<InstituteBuilding> instituteBuildings = instituteBuildingRepository.findAll();
        assertThat(instituteBuildings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClassRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteBuildingRepository.findAll().size();
        // set the field null
        instituteBuilding.setClassRoom(null);

        // Create the InstituteBuilding, which fails.

        restInstituteBuildingMockMvc.perform(post("/api/instituteBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteBuilding)))
                .andExpect(status().isBadRequest());

        List<InstituteBuilding> instituteBuildings = instituteBuildingRepository.findAll();
        assertThat(instituteBuildings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfficeRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteBuildingRepository.findAll().size();
        // set the field null
        instituteBuilding.setOfficeRoom(null);

        // Create the InstituteBuilding, which fails.

        restInstituteBuildingMockMvc.perform(post("/api/instituteBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteBuilding)))
                .andExpect(status().isBadRequest());

        List<InstituteBuilding> instituteBuildings = instituteBuildingRepository.findAll();
        assertThat(instituteBuildings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstituteBuildings() throws Exception {
        // Initialize the database
        instituteBuildingRepository.saveAndFlush(instituteBuilding);

        // Get all the instituteBuildings
        restInstituteBuildingMockMvc.perform(get("/api/instituteBuildings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instituteBuilding.getId().intValue())))
                .andExpect(jsonPath("$.[*].totalArea").value(hasItem(DEFAULT_TOTAL_AREA.toString())))
                .andExpect(jsonPath("$.[*].totalRoom").value(hasItem(DEFAULT_TOTAL_ROOM.intValue())))
                .andExpect(jsonPath("$.[*].classRoom").value(hasItem(DEFAULT_CLASS_ROOM.intValue())))
                .andExpect(jsonPath("$.[*].officeRoom").value(hasItem(DEFAULT_OFFICE_ROOM.intValue())))
                .andExpect(jsonPath("$.[*].otherRoom").value(hasItem(DEFAULT_OTHER_ROOM.intValue())));
    }

    @Test
    @Transactional
    public void getInstituteBuilding() throws Exception {
        // Initialize the database
        instituteBuildingRepository.saveAndFlush(instituteBuilding);

        // Get the instituteBuilding
        restInstituteBuildingMockMvc.perform(get("/api/instituteBuildings/{id}", instituteBuilding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instituteBuilding.getId().intValue()))
            .andExpect(jsonPath("$.totalArea").value(DEFAULT_TOTAL_AREA.toString()))
            .andExpect(jsonPath("$.totalRoom").value(DEFAULT_TOTAL_ROOM.intValue()))
            .andExpect(jsonPath("$.classRoom").value(DEFAULT_CLASS_ROOM.intValue()))
            .andExpect(jsonPath("$.officeRoom").value(DEFAULT_OFFICE_ROOM.intValue()))
            .andExpect(jsonPath("$.otherRoom").value(DEFAULT_OTHER_ROOM.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstituteBuilding() throws Exception {
        // Get the instituteBuilding
        restInstituteBuildingMockMvc.perform(get("/api/instituteBuildings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituteBuilding() throws Exception {
        // Initialize the database
        instituteBuildingRepository.saveAndFlush(instituteBuilding);

		int databaseSizeBeforeUpdate = instituteBuildingRepository.findAll().size();

        // Update the instituteBuilding
        instituteBuilding.setTotalArea(UPDATED_TOTAL_AREA);
        instituteBuilding.setTotalRoom(UPDATED_TOTAL_ROOM);
        instituteBuilding.setClassRoom(UPDATED_CLASS_ROOM);
        instituteBuilding.setOfficeRoom(UPDATED_OFFICE_ROOM);
        instituteBuilding.setOtherRoom(UPDATED_OTHER_ROOM);

        restInstituteBuildingMockMvc.perform(put("/api/instituteBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteBuilding)))
                .andExpect(status().isOk());

        // Validate the InstituteBuilding in the database
        List<InstituteBuilding> instituteBuildings = instituteBuildingRepository.findAll();
        assertThat(instituteBuildings).hasSize(databaseSizeBeforeUpdate);
        InstituteBuilding testInstituteBuilding = instituteBuildings.get(instituteBuildings.size() - 1);
        assertThat(testInstituteBuilding.getTotalArea()).isEqualTo(UPDATED_TOTAL_AREA);
        assertThat(testInstituteBuilding.getTotalRoom()).isEqualTo(UPDATED_TOTAL_ROOM);
        assertThat(testInstituteBuilding.getClassRoom()).isEqualTo(UPDATED_CLASS_ROOM);
        assertThat(testInstituteBuilding.getOfficeRoom()).isEqualTo(UPDATED_OFFICE_ROOM);
        assertThat(testInstituteBuilding.getOtherRoom()).isEqualTo(UPDATED_OTHER_ROOM);
    }

    @Test
    @Transactional
    public void deleteInstituteBuilding() throws Exception {
        // Initialize the database
        instituteBuildingRepository.saveAndFlush(instituteBuilding);

		int databaseSizeBeforeDelete = instituteBuildingRepository.findAll().size();

        // Get the instituteBuilding
        restInstituteBuildingMockMvc.perform(delete("/api/instituteBuildings/{id}", instituteBuilding.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstituteBuilding> instituteBuildings = instituteBuildingRepository.findAll();
        assertThat(instituteBuildings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
