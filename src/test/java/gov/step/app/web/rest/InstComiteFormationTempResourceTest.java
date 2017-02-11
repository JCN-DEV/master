package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstComiteFormationTemp;
import gov.step.app.repository.InstComiteFormationTempRepository;
import gov.step.app.repository.search.InstComiteFormationTempSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the InstComiteFormationTempResource REST controller.
 *
 * @see InstComiteFormationTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstComiteFormationTempResourceTest {

    private static final String DEFAULT_COMITE_NAME = "AAAAA";
    private static final String UPDATED_COMITE_NAME = "BBBBB";
    private static final String DEFAULT_COMITE_TYPE = "AAAAA";
    private static final String UPDATED_COMITE_TYPE = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final Integer DEFAULT_TIME_FROM = 1;
    private static final Integer UPDATED_TIME_FROM = 2;

    private static final Integer DEFAULT_TIME_TO = 1;
    private static final Integer UPDATED_TIME_TO = 2;

    private static final LocalDate DEFAULT_FORMATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FORMATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private InstComiteFormationTempRepository instComiteFormationTempRepository;

    @Inject
    private InstComiteFormationTempSearchRepository instComiteFormationTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstComiteFormationTempMockMvc;

    private InstComiteFormationTemp instComiteFormationTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstComiteFormationTempResource instComiteFormationTempResource = new InstComiteFormationTempResource();
        ReflectionTestUtils.setField(instComiteFormationTempResource, "instComiteFormationTempRepository", instComiteFormationTempRepository);
        ReflectionTestUtils.setField(instComiteFormationTempResource, "instComiteFormationTempSearchRepository", instComiteFormationTempSearchRepository);
        this.restInstComiteFormationTempMockMvc = MockMvcBuilders.standaloneSetup(instComiteFormationTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instComiteFormationTemp = new InstComiteFormationTemp();
        instComiteFormationTemp.setComiteName(DEFAULT_COMITE_NAME);
        instComiteFormationTemp.setComiteType(DEFAULT_COMITE_TYPE);
        instComiteFormationTemp.setAddress(DEFAULT_ADDRESS);
        instComiteFormationTemp.setTimeFrom(DEFAULT_TIME_FROM);
        instComiteFormationTemp.setTimeTo(DEFAULT_TIME_TO);
        instComiteFormationTemp.setFormationDate(DEFAULT_FORMATION_DATE);
    }

    @Test
    @Transactional
    public void createInstComiteFormationTemp() throws Exception {
        int databaseSizeBeforeCreate = instComiteFormationTempRepository.findAll().size();

        // Create the InstComiteFormationTemp

        restInstComiteFormationTempMockMvc.perform(post("/api/instComiteFormationTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instComiteFormationTemp)))
                .andExpect(status().isCreated());

        // Validate the InstComiteFormationTemp in the database
        List<InstComiteFormationTemp> instComiteFormationTemps = instComiteFormationTempRepository.findAll();
        assertThat(instComiteFormationTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstComiteFormationTemp testInstComiteFormationTemp = instComiteFormationTemps.get(instComiteFormationTemps.size() - 1);
        assertThat(testInstComiteFormationTemp.getComiteName()).isEqualTo(DEFAULT_COMITE_NAME);
        assertThat(testInstComiteFormationTemp.getComiteType()).isEqualTo(DEFAULT_COMITE_TYPE);
        assertThat(testInstComiteFormationTemp.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testInstComiteFormationTemp.getTimeFrom()).isEqualTo(DEFAULT_TIME_FROM);
        assertThat(testInstComiteFormationTemp.getTimeTo()).isEqualTo(DEFAULT_TIME_TO);
        assertThat(testInstComiteFormationTemp.getFormationDate()).isEqualTo(DEFAULT_FORMATION_DATE);
    }

    @Test
    @Transactional
    public void getAllInstComiteFormationTemps() throws Exception {
        // Initialize the database
        instComiteFormationTempRepository.saveAndFlush(instComiteFormationTemp);

        // Get all the instComiteFormationTemps
        restInstComiteFormationTempMockMvc.perform(get("/api/instComiteFormationTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instComiteFormationTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].comiteName").value(hasItem(DEFAULT_COMITE_NAME.toString())))
                .andExpect(jsonPath("$.[*].comiteType").value(hasItem(DEFAULT_COMITE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM)))
                .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO)))
                .andExpect(jsonPath("$.[*].formationDate").value(hasItem(DEFAULT_FORMATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInstComiteFormationTemp() throws Exception {
        // Initialize the database
        instComiteFormationTempRepository.saveAndFlush(instComiteFormationTemp);

        // Get the instComiteFormationTemp
        restInstComiteFormationTempMockMvc.perform(get("/api/instComiteFormationTemps/{id}", instComiteFormationTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instComiteFormationTemp.getId().intValue()))
            .andExpect(jsonPath("$.comiteName").value(DEFAULT_COMITE_NAME.toString()))
            .andExpect(jsonPath("$.comiteType").value(DEFAULT_COMITE_TYPE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.timeFrom").value(DEFAULT_TIME_FROM))
            .andExpect(jsonPath("$.timeTo").value(DEFAULT_TIME_TO))
            .andExpect(jsonPath("$.formationDate").value(DEFAULT_FORMATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstComiteFormationTemp() throws Exception {
        // Get the instComiteFormationTemp
        restInstComiteFormationTempMockMvc.perform(get("/api/instComiteFormationTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstComiteFormationTemp() throws Exception {
        // Initialize the database
        instComiteFormationTempRepository.saveAndFlush(instComiteFormationTemp);

		int databaseSizeBeforeUpdate = instComiteFormationTempRepository.findAll().size();

        // Update the instComiteFormationTemp
        instComiteFormationTemp.setComiteName(UPDATED_COMITE_NAME);
        instComiteFormationTemp.setComiteType(UPDATED_COMITE_TYPE);
        instComiteFormationTemp.setAddress(UPDATED_ADDRESS);
        instComiteFormationTemp.setTimeFrom(UPDATED_TIME_FROM);
        instComiteFormationTemp.setTimeTo(UPDATED_TIME_TO);
        instComiteFormationTemp.setFormationDate(UPDATED_FORMATION_DATE);

        restInstComiteFormationTempMockMvc.perform(put("/api/instComiteFormationTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instComiteFormationTemp)))
                .andExpect(status().isOk());

        // Validate the InstComiteFormationTemp in the database
        List<InstComiteFormationTemp> instComiteFormationTemps = instComiteFormationTempRepository.findAll();
        assertThat(instComiteFormationTemps).hasSize(databaseSizeBeforeUpdate);
        InstComiteFormationTemp testInstComiteFormationTemp = instComiteFormationTemps.get(instComiteFormationTemps.size() - 1);
        assertThat(testInstComiteFormationTemp.getComiteName()).isEqualTo(UPDATED_COMITE_NAME);
        assertThat(testInstComiteFormationTemp.getComiteType()).isEqualTo(UPDATED_COMITE_TYPE);
        assertThat(testInstComiteFormationTemp.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testInstComiteFormationTemp.getTimeFrom()).isEqualTo(UPDATED_TIME_FROM);
        assertThat(testInstComiteFormationTemp.getTimeTo()).isEqualTo(UPDATED_TIME_TO);
        assertThat(testInstComiteFormationTemp.getFormationDate()).isEqualTo(UPDATED_FORMATION_DATE);
    }

    @Test
    @Transactional
    public void deleteInstComiteFormationTemp() throws Exception {
        // Initialize the database
        instComiteFormationTempRepository.saveAndFlush(instComiteFormationTemp);

		int databaseSizeBeforeDelete = instComiteFormationTempRepository.findAll().size();

        // Get the instComiteFormationTemp
        restInstComiteFormationTempMockMvc.perform(delete("/api/instComiteFormationTemps/{id}", instComiteFormationTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstComiteFormationTemp> instComiteFormationTemps = instComiteFormationTempRepository.findAll();
        assertThat(instComiteFormationTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
