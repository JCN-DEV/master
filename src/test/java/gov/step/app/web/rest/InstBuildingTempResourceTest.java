package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstBuildingTemp;
import gov.step.app.repository.InstBuildingTempRepository;
import gov.step.app.repository.search.InstBuildingTempSearchRepository;

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
 * Test class for the InstBuildingTempResource REST controller.
 *
 * @see InstBuildingTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstBuildingTempResourceTest {

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
    private InstBuildingTempRepository instBuildingTempRepository;

    @Inject
    private InstBuildingTempSearchRepository instBuildingTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstBuildingTempMockMvc;

    private InstBuildingTemp instBuildingTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstBuildingTempResource instBuildingTempResource = new InstBuildingTempResource();
        ReflectionTestUtils.setField(instBuildingTempResource, "instBuildingTempRepository", instBuildingTempRepository);
        ReflectionTestUtils.setField(instBuildingTempResource, "instBuildingTempSearchRepository", instBuildingTempSearchRepository);
        this.restInstBuildingTempMockMvc = MockMvcBuilders.standaloneSetup(instBuildingTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instBuildingTemp = new InstBuildingTemp();
        instBuildingTemp.setTotalArea(DEFAULT_TOTAL_AREA);
        instBuildingTemp.setTotalRoom(DEFAULT_TOTAL_ROOM);
        instBuildingTemp.setClassRoom(DEFAULT_CLASS_ROOM);
        instBuildingTemp.setOfficeRoom(DEFAULT_OFFICE_ROOM);
        instBuildingTemp.setOtherRoom(DEFAULT_OTHER_ROOM);
    }

    @Test
    @Transactional
    public void createInstBuildingTemp() throws Exception {
        int databaseSizeBeforeCreate = instBuildingTempRepository.findAll().size();

        // Create the InstBuildingTemp

        restInstBuildingTempMockMvc.perform(post("/api/instBuildingTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuildingTemp)))
                .andExpect(status().isCreated());

        // Validate the InstBuildingTemp in the database
        List<InstBuildingTemp> instBuildingTemps = instBuildingTempRepository.findAll();
        assertThat(instBuildingTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstBuildingTemp testInstBuildingTemp = instBuildingTemps.get(instBuildingTemps.size() - 1);
        assertThat(testInstBuildingTemp.getTotalArea()).isEqualTo(DEFAULT_TOTAL_AREA);
        assertThat(testInstBuildingTemp.getTotalRoom()).isEqualTo(DEFAULT_TOTAL_ROOM);
        assertThat(testInstBuildingTemp.getClassRoom()).isEqualTo(DEFAULT_CLASS_ROOM);
        assertThat(testInstBuildingTemp.getOfficeRoom()).isEqualTo(DEFAULT_OFFICE_ROOM);
        assertThat(testInstBuildingTemp.getOtherRoom()).isEqualTo(DEFAULT_OTHER_ROOM);
    }

    @Test
    @Transactional
    public void checkTotalAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = instBuildingTempRepository.findAll().size();
        // set the field null
        instBuildingTemp.setTotalArea(null);

        // Create the InstBuildingTemp, which fails.

        restInstBuildingTempMockMvc.perform(post("/api/instBuildingTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuildingTemp)))
                .andExpect(status().isBadRequest());

        List<InstBuildingTemp> instBuildingTemps = instBuildingTempRepository.findAll();
        assertThat(instBuildingTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = instBuildingTempRepository.findAll().size();
        // set the field null
        instBuildingTemp.setTotalRoom(null);

        // Create the InstBuildingTemp, which fails.

        restInstBuildingTempMockMvc.perform(post("/api/instBuildingTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuildingTemp)))
                .andExpect(status().isBadRequest());

        List<InstBuildingTemp> instBuildingTemps = instBuildingTempRepository.findAll();
        assertThat(instBuildingTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClassRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = instBuildingTempRepository.findAll().size();
        // set the field null
        instBuildingTemp.setClassRoom(null);

        // Create the InstBuildingTemp, which fails.

        restInstBuildingTempMockMvc.perform(post("/api/instBuildingTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuildingTemp)))
                .andExpect(status().isBadRequest());

        List<InstBuildingTemp> instBuildingTemps = instBuildingTempRepository.findAll();
        assertThat(instBuildingTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfficeRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = instBuildingTempRepository.findAll().size();
        // set the field null
        instBuildingTemp.setOfficeRoom(null);

        // Create the InstBuildingTemp, which fails.

        restInstBuildingTempMockMvc.perform(post("/api/instBuildingTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuildingTemp)))
                .andExpect(status().isBadRequest());

        List<InstBuildingTemp> instBuildingTemps = instBuildingTempRepository.findAll();
        assertThat(instBuildingTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstBuildingTemps() throws Exception {
        // Initialize the database
        instBuildingTempRepository.saveAndFlush(instBuildingTemp);

        // Get all the instBuildingTemps
        restInstBuildingTempMockMvc.perform(get("/api/instBuildingTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instBuildingTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].totalArea").value(hasItem(DEFAULT_TOTAL_AREA.toString())))
                .andExpect(jsonPath("$.[*].totalRoom").value(hasItem(DEFAULT_TOTAL_ROOM.intValue())))
                .andExpect(jsonPath("$.[*].classRoom").value(hasItem(DEFAULT_CLASS_ROOM.intValue())))
                .andExpect(jsonPath("$.[*].officeRoom").value(hasItem(DEFAULT_OFFICE_ROOM.intValue())))
                .andExpect(jsonPath("$.[*].otherRoom").value(hasItem(DEFAULT_OTHER_ROOM.intValue())));
    }

    @Test
    @Transactional
    public void getInstBuildingTemp() throws Exception {
        // Initialize the database
        instBuildingTempRepository.saveAndFlush(instBuildingTemp);

        // Get the instBuildingTemp
        restInstBuildingTempMockMvc.perform(get("/api/instBuildingTemps/{id}", instBuildingTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instBuildingTemp.getId().intValue()))
            .andExpect(jsonPath("$.totalArea").value(DEFAULT_TOTAL_AREA.toString()))
            .andExpect(jsonPath("$.totalRoom").value(DEFAULT_TOTAL_ROOM.intValue()))
            .andExpect(jsonPath("$.classRoom").value(DEFAULT_CLASS_ROOM.intValue()))
            .andExpect(jsonPath("$.officeRoom").value(DEFAULT_OFFICE_ROOM.intValue()))
            .andExpect(jsonPath("$.otherRoom").value(DEFAULT_OTHER_ROOM.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstBuildingTemp() throws Exception {
        // Get the instBuildingTemp
        restInstBuildingTempMockMvc.perform(get("/api/instBuildingTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstBuildingTemp() throws Exception {
        // Initialize the database
        instBuildingTempRepository.saveAndFlush(instBuildingTemp);

		int databaseSizeBeforeUpdate = instBuildingTempRepository.findAll().size();

        // Update the instBuildingTemp
        instBuildingTemp.setTotalArea(UPDATED_TOTAL_AREA);
        instBuildingTemp.setTotalRoom(UPDATED_TOTAL_ROOM);
        instBuildingTemp.setClassRoom(UPDATED_CLASS_ROOM);
        instBuildingTemp.setOfficeRoom(UPDATED_OFFICE_ROOM);
        instBuildingTemp.setOtherRoom(UPDATED_OTHER_ROOM);

        restInstBuildingTempMockMvc.perform(put("/api/instBuildingTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instBuildingTemp)))
                .andExpect(status().isOk());

        // Validate the InstBuildingTemp in the database
        List<InstBuildingTemp> instBuildingTemps = instBuildingTempRepository.findAll();
        assertThat(instBuildingTemps).hasSize(databaseSizeBeforeUpdate);
        InstBuildingTemp testInstBuildingTemp = instBuildingTemps.get(instBuildingTemps.size() - 1);
        assertThat(testInstBuildingTemp.getTotalArea()).isEqualTo(UPDATED_TOTAL_AREA);
        assertThat(testInstBuildingTemp.getTotalRoom()).isEqualTo(UPDATED_TOTAL_ROOM);
        assertThat(testInstBuildingTemp.getClassRoom()).isEqualTo(UPDATED_CLASS_ROOM);
        assertThat(testInstBuildingTemp.getOfficeRoom()).isEqualTo(UPDATED_OFFICE_ROOM);
        assertThat(testInstBuildingTemp.getOtherRoom()).isEqualTo(UPDATED_OTHER_ROOM);
    }

    @Test
    @Transactional
    public void deleteInstBuildingTemp() throws Exception {
        // Initialize the database
        instBuildingTempRepository.saveAndFlush(instBuildingTemp);

		int databaseSizeBeforeDelete = instBuildingTempRepository.findAll().size();

        // Get the instBuildingTemp
        restInstBuildingTempMockMvc.perform(delete("/api/instBuildingTemps/{id}", instBuildingTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstBuildingTemp> instBuildingTemps = instBuildingTempRepository.findAll();
        assertThat(instBuildingTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
