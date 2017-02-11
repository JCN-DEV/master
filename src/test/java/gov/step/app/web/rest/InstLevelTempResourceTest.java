package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstLevelTemp;
import gov.step.app.repository.InstLevelTempRepository;
import gov.step.app.repository.search.InstLevelTempSearchRepository;

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
 * Test class for the InstLevelTempResource REST controller.
 *
 * @see InstLevelTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstLevelTempResourceTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_P_STATUS = false;
    private static final Boolean UPDATED_P_STATUS = true;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstLevelTempRepository instLevelTempRepository;

    @Inject
    private InstLevelTempSearchRepository instLevelTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstLevelTempMockMvc;

    private InstLevelTemp instLevelTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstLevelTempResource instLevelTempResource = new InstLevelTempResource();
        ReflectionTestUtils.setField(instLevelTempResource, "instLevelTempRepository", instLevelTempRepository);
        ReflectionTestUtils.setField(instLevelTempResource, "instLevelTempSearchRepository", instLevelTempSearchRepository);
        this.restInstLevelTempMockMvc = MockMvcBuilders.standaloneSetup(instLevelTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instLevelTemp = new InstLevelTemp();
        instLevelTemp.setCode(DEFAULT_CODE);
        instLevelTemp.setName(DEFAULT_NAME);
        instLevelTemp.setDescription(DEFAULT_DESCRIPTION);
        instLevelTemp.setpStatus(DEFAULT_P_STATUS);
        instLevelTemp.setCreatedDate(DEFAULT_CREATED_DATE);
        instLevelTemp.setUpdatedDate(DEFAULT_UPDATED_DATE);
        instLevelTemp.setCreatedBy(DEFAULT_CREATED_BY);
        instLevelTemp.setUpdatedBy(DEFAULT_UPDATED_BY);
        instLevelTemp.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstLevelTemp() throws Exception {
        int databaseSizeBeforeCreate = instLevelTempRepository.findAll().size();

        // Create the InstLevelTemp

        restInstLevelTempMockMvc.perform(post("/api/instLevelTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLevelTemp)))
                .andExpect(status().isCreated());

        // Validate the InstLevelTemp in the database
        List<InstLevelTemp> instLevelTemps = instLevelTempRepository.findAll();
        assertThat(instLevelTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstLevelTemp testInstLevelTemp = instLevelTemps.get(instLevelTemps.size() - 1);
        assertThat(testInstLevelTemp.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInstLevelTemp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstLevelTemp.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInstLevelTemp.getpStatus()).isEqualTo(DEFAULT_P_STATUS);
        assertThat(testInstLevelTemp.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInstLevelTemp.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testInstLevelTemp.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInstLevelTemp.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testInstLevelTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstLevelTemps() throws Exception {
        // Initialize the database
        instLevelTempRepository.saveAndFlush(instLevelTemp);

        // Get all the instLevelTemps
        restInstLevelTempMockMvc.perform(get("/api/instLevelTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instLevelTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].pStatus").value(hasItem(DEFAULT_P_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstLevelTemp() throws Exception {
        // Initialize the database
        instLevelTempRepository.saveAndFlush(instLevelTemp);

        // Get the instLevelTemp
        restInstLevelTempMockMvc.perform(get("/api/instLevelTemps/{id}", instLevelTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instLevelTemp.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.pStatus").value(DEFAULT_P_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstLevelTemp() throws Exception {
        // Get the instLevelTemp
        restInstLevelTempMockMvc.perform(get("/api/instLevelTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstLevelTemp() throws Exception {
        // Initialize the database
        instLevelTempRepository.saveAndFlush(instLevelTemp);

		int databaseSizeBeforeUpdate = instLevelTempRepository.findAll().size();

        // Update the instLevelTemp
        instLevelTemp.setCode(UPDATED_CODE);
        instLevelTemp.setName(UPDATED_NAME);
        instLevelTemp.setDescription(UPDATED_DESCRIPTION);
        instLevelTemp.setpStatus(UPDATED_P_STATUS);
        instLevelTemp.setCreatedDate(UPDATED_CREATED_DATE);
        instLevelTemp.setUpdatedDate(UPDATED_UPDATED_DATE);
        instLevelTemp.setCreatedBy(UPDATED_CREATED_BY);
        instLevelTemp.setUpdatedBy(UPDATED_UPDATED_BY);
        instLevelTemp.setStatus(UPDATED_STATUS);

        restInstLevelTempMockMvc.perform(put("/api/instLevelTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLevelTemp)))
                .andExpect(status().isOk());

        // Validate the InstLevelTemp in the database
        List<InstLevelTemp> instLevelTemps = instLevelTempRepository.findAll();
        assertThat(instLevelTemps).hasSize(databaseSizeBeforeUpdate);
        InstLevelTemp testInstLevelTemp = instLevelTemps.get(instLevelTemps.size() - 1);
        assertThat(testInstLevelTemp.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInstLevelTemp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstLevelTemp.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInstLevelTemp.getpStatus()).isEqualTo(UPDATED_P_STATUS);
        assertThat(testInstLevelTemp.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInstLevelTemp.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testInstLevelTemp.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInstLevelTemp.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInstLevelTemp.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstLevelTemp() throws Exception {
        // Initialize the database
        instLevelTempRepository.saveAndFlush(instLevelTemp);

		int databaseSizeBeforeDelete = instLevelTempRepository.findAll().size();

        // Get the instLevelTemp
        restInstLevelTempMockMvc.perform(delete("/api/instLevelTemps/{id}", instLevelTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstLevelTemp> instLevelTemps = instLevelTempRepository.findAll();
        assertThat(instLevelTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
