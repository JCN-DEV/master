package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstLabInfoTemp;
import gov.step.app.repository.InstLabInfoTempRepository;
import gov.step.app.repository.search.InstLabInfoTempSearchRepository;

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
 * Test class for the InstLabInfoTempResource REST controller.
 *
 * @see InstLabInfoTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstLabInfoTempResourceTest {

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
    private InstLabInfoTempRepository instLabInfoTempRepository;

    @Inject
    private InstLabInfoTempSearchRepository instLabInfoTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstLabInfoTempMockMvc;

    private InstLabInfoTemp instLabInfoTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstLabInfoTempResource instLabInfoTempResource = new InstLabInfoTempResource();
        ReflectionTestUtils.setField(instLabInfoTempResource, "instLabInfoTempRepository", instLabInfoTempRepository);
        ReflectionTestUtils.setField(instLabInfoTempResource, "instLabInfoTempSearchRepository", instLabInfoTempSearchRepository);
        this.restInstLabInfoTempMockMvc = MockMvcBuilders.standaloneSetup(instLabInfoTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instLabInfoTemp = new InstLabInfoTemp();
        instLabInfoTemp.setNameOrNumber(DEFAULT_NAME_OR_NUMBER);
        instLabInfoTemp.setBuildingNameOrNumber(DEFAULT_BUILDING_NAME_OR_NUMBER);
        instLabInfoTemp.setLength(DEFAULT_LENGTH);
        instLabInfoTemp.setWidth(DEFAULT_WIDTH);
        instLabInfoTemp.setTotalBooks(DEFAULT_TOTAL_BOOKS);
    }

    @Test
    @Transactional
    public void createInstLabInfoTemp() throws Exception {
        int databaseSizeBeforeCreate = instLabInfoTempRepository.findAll().size();

        // Create the InstLabInfoTemp

        restInstLabInfoTempMockMvc.perform(post("/api/instLabInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLabInfoTemp)))
                .andExpect(status().isCreated());

        // Validate the InstLabInfoTemp in the database
        List<InstLabInfoTemp> instLabInfoTemps = instLabInfoTempRepository.findAll();
        assertThat(instLabInfoTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstLabInfoTemp testInstLabInfoTemp = instLabInfoTemps.get(instLabInfoTemps.size() - 1);
        assertThat(testInstLabInfoTemp.getNameOrNumber()).isEqualTo(DEFAULT_NAME_OR_NUMBER);
        assertThat(testInstLabInfoTemp.getBuildingNameOrNumber()).isEqualTo(DEFAULT_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstLabInfoTemp.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testInstLabInfoTemp.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testInstLabInfoTemp.getTotalBooks()).isEqualTo(DEFAULT_TOTAL_BOOKS);
    }

    @Test
    @Transactional
    public void getAllInstLabInfoTemps() throws Exception {
        // Initialize the database
        instLabInfoTempRepository.saveAndFlush(instLabInfoTemp);

        // Get all the instLabInfoTemps
        restInstLabInfoTempMockMvc.perform(get("/api/instLabInfoTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instLabInfoTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameOrNumber").value(hasItem(DEFAULT_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].buildingNameOrNumber").value(hasItem(DEFAULT_BUILDING_NAME_OR_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.toString())))
                .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.toString())))
                .andExpect(jsonPath("$.[*].totalBooks").value(hasItem(DEFAULT_TOTAL_BOOKS.intValue())));
    }

    @Test
    @Transactional
    public void getInstLabInfoTemp() throws Exception {
        // Initialize the database
        instLabInfoTempRepository.saveAndFlush(instLabInfoTemp);

        // Get the instLabInfoTemp
        restInstLabInfoTempMockMvc.perform(get("/api/instLabInfoTemps/{id}", instLabInfoTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instLabInfoTemp.getId().intValue()))
            .andExpect(jsonPath("$.nameOrNumber").value(DEFAULT_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.buildingNameOrNumber").value(DEFAULT_BUILDING_NAME_OR_NUMBER.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.toString()))
            .andExpect(jsonPath("$.totalBooks").value(DEFAULT_TOTAL_BOOKS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstLabInfoTemp() throws Exception {
        // Get the instLabInfoTemp
        restInstLabInfoTempMockMvc.perform(get("/api/instLabInfoTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstLabInfoTemp() throws Exception {
        // Initialize the database
        instLabInfoTempRepository.saveAndFlush(instLabInfoTemp);

		int databaseSizeBeforeUpdate = instLabInfoTempRepository.findAll().size();

        // Update the instLabInfoTemp
        instLabInfoTemp.setNameOrNumber(UPDATED_NAME_OR_NUMBER);
        instLabInfoTemp.setBuildingNameOrNumber(UPDATED_BUILDING_NAME_OR_NUMBER);
        instLabInfoTemp.setLength(UPDATED_LENGTH);
        instLabInfoTemp.setWidth(UPDATED_WIDTH);
        instLabInfoTemp.setTotalBooks(UPDATED_TOTAL_BOOKS);

        restInstLabInfoTempMockMvc.perform(put("/api/instLabInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLabInfoTemp)))
                .andExpect(status().isOk());

        // Validate the InstLabInfoTemp in the database
        List<InstLabInfoTemp> instLabInfoTemps = instLabInfoTempRepository.findAll();
        assertThat(instLabInfoTemps).hasSize(databaseSizeBeforeUpdate);
        InstLabInfoTemp testInstLabInfoTemp = instLabInfoTemps.get(instLabInfoTemps.size() - 1);
        assertThat(testInstLabInfoTemp.getNameOrNumber()).isEqualTo(UPDATED_NAME_OR_NUMBER);
        assertThat(testInstLabInfoTemp.getBuildingNameOrNumber()).isEqualTo(UPDATED_BUILDING_NAME_OR_NUMBER);
        assertThat(testInstLabInfoTemp.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testInstLabInfoTemp.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testInstLabInfoTemp.getTotalBooks()).isEqualTo(UPDATED_TOTAL_BOOKS);
    }

    @Test
    @Transactional
    public void deleteInstLabInfoTemp() throws Exception {
        // Initialize the database
        instLabInfoTempRepository.saveAndFlush(instLabInfoTemp);

		int databaseSizeBeforeDelete = instLabInfoTempRepository.findAll().size();

        // Get the instLabInfoTemp
        restInstLabInfoTempMockMvc.perform(delete("/api/instLabInfoTemps/{id}", instLabInfoTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstLabInfoTemp> instLabInfoTemps = instLabInfoTempRepository.findAll();
        assertThat(instLabInfoTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
