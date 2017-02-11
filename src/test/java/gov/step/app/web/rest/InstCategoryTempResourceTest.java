package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstCategoryTemp;
import gov.step.app.repository.InstCategoryTempRepository;
import gov.step.app.repository.search.InstCategoryTempSearchRepository;

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
 * Test class for the InstCategoryTempResource REST controller.
 *
 * @see InstCategoryTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstCategoryTempResourceTest {

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
    private InstCategoryTempRepository instCategoryTempRepository;

    @Inject
    private InstCategoryTempSearchRepository instCategoryTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstCategoryTempMockMvc;

    private InstCategoryTemp instCategoryTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstCategoryTempResource instCategoryTempResource = new InstCategoryTempResource();
        ReflectionTestUtils.setField(instCategoryTempResource, "instCategoryTempRepository", instCategoryTempRepository);
        ReflectionTestUtils.setField(instCategoryTempResource, "instCategoryTempSearchRepository", instCategoryTempSearchRepository);
        this.restInstCategoryTempMockMvc = MockMvcBuilders.standaloneSetup(instCategoryTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instCategoryTemp = new InstCategoryTemp();
        instCategoryTemp.setCode(DEFAULT_CODE);
        instCategoryTemp.setName(DEFAULT_NAME);
        instCategoryTemp.setDescription(DEFAULT_DESCRIPTION);
        instCategoryTemp.setpStatus(DEFAULT_P_STATUS);
        instCategoryTemp.setCreatedDate(DEFAULT_CREATED_DATE);
        instCategoryTemp.setUpdatedDate(DEFAULT_UPDATED_DATE);
        instCategoryTemp.setCreatedBy(DEFAULT_CREATED_BY);
        instCategoryTemp.setUpdatedBy(DEFAULT_UPDATED_BY);
        instCategoryTemp.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstCategoryTemp() throws Exception {
        int databaseSizeBeforeCreate = instCategoryTempRepository.findAll().size();

        // Create the InstCategoryTemp

        restInstCategoryTempMockMvc.perform(post("/api/instCategoryTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instCategoryTemp)))
                .andExpect(status().isCreated());

        // Validate the InstCategoryTemp in the database
        List<InstCategoryTemp> instCategoryTemps = instCategoryTempRepository.findAll();
        assertThat(instCategoryTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstCategoryTemp testInstCategoryTemp = instCategoryTemps.get(instCategoryTemps.size() - 1);
        assertThat(testInstCategoryTemp.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInstCategoryTemp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstCategoryTemp.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInstCategoryTemp.getpStatus()).isEqualTo(DEFAULT_P_STATUS);
        assertThat(testInstCategoryTemp.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInstCategoryTemp.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testInstCategoryTemp.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInstCategoryTemp.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testInstCategoryTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstCategoryTemps() throws Exception {
        // Initialize the database
        instCategoryTempRepository.saveAndFlush(instCategoryTemp);

        // Get all the instCategoryTemps
        restInstCategoryTempMockMvc.perform(get("/api/instCategoryTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instCategoryTemp.getId().intValue())))
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
    public void getInstCategoryTemp() throws Exception {
        // Initialize the database
        instCategoryTempRepository.saveAndFlush(instCategoryTemp);

        // Get the instCategoryTemp
        restInstCategoryTempMockMvc.perform(get("/api/instCategoryTemps/{id}", instCategoryTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instCategoryTemp.getId().intValue()))
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
    public void getNonExistingInstCategoryTemp() throws Exception {
        // Get the instCategoryTemp
        restInstCategoryTempMockMvc.perform(get("/api/instCategoryTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstCategoryTemp() throws Exception {
        // Initialize the database
        instCategoryTempRepository.saveAndFlush(instCategoryTemp);

		int databaseSizeBeforeUpdate = instCategoryTempRepository.findAll().size();

        // Update the instCategoryTemp
        instCategoryTemp.setCode(UPDATED_CODE);
        instCategoryTemp.setName(UPDATED_NAME);
        instCategoryTemp.setDescription(UPDATED_DESCRIPTION);
        instCategoryTemp.setpStatus(UPDATED_P_STATUS);
        instCategoryTemp.setCreatedDate(UPDATED_CREATED_DATE);
        instCategoryTemp.setUpdatedDate(UPDATED_UPDATED_DATE);
        instCategoryTemp.setCreatedBy(UPDATED_CREATED_BY);
        instCategoryTemp.setUpdatedBy(UPDATED_UPDATED_BY);
        instCategoryTemp.setStatus(UPDATED_STATUS);

        restInstCategoryTempMockMvc.perform(put("/api/instCategoryTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instCategoryTemp)))
                .andExpect(status().isOk());

        // Validate the InstCategoryTemp in the database
        List<InstCategoryTemp> instCategoryTemps = instCategoryTempRepository.findAll();
        assertThat(instCategoryTemps).hasSize(databaseSizeBeforeUpdate);
        InstCategoryTemp testInstCategoryTemp = instCategoryTemps.get(instCategoryTemps.size() - 1);
        assertThat(testInstCategoryTemp.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInstCategoryTemp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstCategoryTemp.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInstCategoryTemp.getpStatus()).isEqualTo(UPDATED_P_STATUS);
        assertThat(testInstCategoryTemp.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInstCategoryTemp.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testInstCategoryTemp.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInstCategoryTemp.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInstCategoryTemp.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstCategoryTemp() throws Exception {
        // Initialize the database
        instCategoryTempRepository.saveAndFlush(instCategoryTemp);

		int databaseSizeBeforeDelete = instCategoryTempRepository.findAll().size();

        // Get the instCategoryTemp
        restInstCategoryTempMockMvc.perform(delete("/api/instCategoryTemps/{id}", instCategoryTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstCategoryTemp> instCategoryTemps = instCategoryTempRepository.findAll();
        assertThat(instCategoryTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
