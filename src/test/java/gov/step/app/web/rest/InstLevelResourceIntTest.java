package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstLevel;
import gov.step.app.repository.InstLevelRepository;
import gov.step.app.repository.search.InstLevelSearchRepository;

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
 * Test class for the InstLevelResource REST controller.
 *
 * @see InstLevelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstLevelResourceIntTest {

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
    private InstLevelRepository instLevelRepository;

    @Inject
    private InstLevelSearchRepository instLevelSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstLevelMockMvc;

    private InstLevel instLevel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstLevelResource instLevelResource = new InstLevelResource();
        ReflectionTestUtils.setField(instLevelResource, "instLevelRepository", instLevelRepository);
        ReflectionTestUtils.setField(instLevelResource, "instLevelSearchRepository", instLevelSearchRepository);
        this.restInstLevelMockMvc = MockMvcBuilders.standaloneSetup(instLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instLevel = new InstLevel();
        instLevel.setCode(DEFAULT_CODE);
        instLevel.setName(DEFAULT_NAME);
        instLevel.setDescription(DEFAULT_DESCRIPTION);
        instLevel.setpStatus(DEFAULT_P_STATUS);
        instLevel.setCreatedDate(DEFAULT_CREATED_DATE);
        instLevel.setUpdatedDate(DEFAULT_UPDATED_DATE);
        instLevel.setCreatedBy(DEFAULT_CREATED_BY);
        instLevel.setUpdatedBy(DEFAULT_UPDATED_BY);
        instLevel.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstLevel() throws Exception {
        int databaseSizeBeforeCreate = instLevelRepository.findAll().size();

        // Create the InstLevel

        restInstLevelMockMvc.perform(post("/api/instLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLevel)))
                .andExpect(status().isCreated());

        // Validate the InstLevel in the database
        List<InstLevel> instLevels = instLevelRepository.findAll();
        assertThat(instLevels).hasSize(databaseSizeBeforeCreate + 1);
        InstLevel testInstLevel = instLevels.get(instLevels.size() - 1);
        assertThat(testInstLevel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInstLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInstLevel.getpStatus()).isEqualTo(DEFAULT_P_STATUS);
        assertThat(testInstLevel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInstLevel.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testInstLevel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInstLevel.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testInstLevel.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstLevels() throws Exception {
        // Initialize the database
        instLevelRepository.saveAndFlush(instLevel);

        // Get all the instLevels
        restInstLevelMockMvc.perform(get("/api/instLevels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instLevel.getId().intValue())))
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
    public void getInstLevel() throws Exception {
        // Initialize the database
        instLevelRepository.saveAndFlush(instLevel);

        // Get the instLevel
        restInstLevelMockMvc.perform(get("/api/instLevels/{id}", instLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instLevel.getId().intValue()))
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
    public void getNonExistingInstLevel() throws Exception {
        // Get the instLevel
        restInstLevelMockMvc.perform(get("/api/instLevels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstLevel() throws Exception {
        // Initialize the database
        instLevelRepository.saveAndFlush(instLevel);

		int databaseSizeBeforeUpdate = instLevelRepository.findAll().size();

        // Update the instLevel
        instLevel.setCode(UPDATED_CODE);
        instLevel.setName(UPDATED_NAME);
        instLevel.setDescription(UPDATED_DESCRIPTION);
        instLevel.setpStatus(UPDATED_P_STATUS);
        instLevel.setCreatedDate(UPDATED_CREATED_DATE);
        instLevel.setUpdatedDate(UPDATED_UPDATED_DATE);
        instLevel.setCreatedBy(UPDATED_CREATED_BY);
        instLevel.setUpdatedBy(UPDATED_UPDATED_BY);
        instLevel.setStatus(UPDATED_STATUS);

        restInstLevelMockMvc.perform(put("/api/instLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instLevel)))
                .andExpect(status().isOk());

        // Validate the InstLevel in the database
        List<InstLevel> instLevels = instLevelRepository.findAll();
        assertThat(instLevels).hasSize(databaseSizeBeforeUpdate);
        InstLevel testInstLevel = instLevels.get(instLevels.size() - 1);
        assertThat(testInstLevel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInstLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInstLevel.getpStatus()).isEqualTo(UPDATED_P_STATUS);
        assertThat(testInstLevel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInstLevel.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testInstLevel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInstLevel.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInstLevel.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstLevel() throws Exception {
        // Initialize the database
        instLevelRepository.saveAndFlush(instLevel);

		int databaseSizeBeforeDelete = instLevelRepository.findAll().size();

        // Get the instLevel
        restInstLevelMockMvc.perform(delete("/api/instLevels/{id}", instLevel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstLevel> instLevels = instLevelRepository.findAll();
        assertThat(instLevels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
