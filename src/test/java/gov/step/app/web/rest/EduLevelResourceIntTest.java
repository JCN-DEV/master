package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EduLevel;
import gov.step.app.repository.EduLevelRepository;
import gov.step.app.repository.search.EduLevelSearchRepository;

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
 * Test class for the EduLevelResource REST controller.
 *
 * @see EduLevelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EduLevelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private EduLevelRepository eduLevelRepository;

    @Inject
    private EduLevelSearchRepository eduLevelSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEduLevelMockMvc;

    private EduLevel eduLevel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EduLevelResource eduLevelResource = new EduLevelResource();
        ReflectionTestUtils.setField(eduLevelResource, "eduLevelRepository", eduLevelRepository);
        ReflectionTestUtils.setField(eduLevelResource, "eduLevelSearchRepository", eduLevelSearchRepository);
        this.restEduLevelMockMvc = MockMvcBuilders.standaloneSetup(eduLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        eduLevel = new EduLevel();
        eduLevel.setName(DEFAULT_NAME);
        eduLevel.setDescription(DEFAULT_DESCRIPTION);
        eduLevel.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEduLevel() throws Exception {
        int databaseSizeBeforeCreate = eduLevelRepository.findAll().size();

        // Create the EduLevel

        restEduLevelMockMvc.perform(post("/api/eduLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eduLevel)))
                .andExpect(status().isCreated());

        // Validate the EduLevel in the database
        List<EduLevel> eduLevels = eduLevelRepository.findAll();
        assertThat(eduLevels).hasSize(databaseSizeBeforeCreate + 1);
        EduLevel testEduLevel = eduLevels.get(eduLevels.size() - 1);
        assertThat(testEduLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEduLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEduLevel.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eduLevelRepository.findAll().size();
        // set the field null
        eduLevel.setName(null);

        // Create the EduLevel, which fails.

        restEduLevelMockMvc.perform(post("/api/eduLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eduLevel)))
                .andExpect(status().isBadRequest());

        List<EduLevel> eduLevels = eduLevelRepository.findAll();
        assertThat(eduLevels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEduLevels() throws Exception {
        // Initialize the database
        eduLevelRepository.saveAndFlush(eduLevel);

        // Get all the eduLevels
        restEduLevelMockMvc.perform(get("/api/eduLevels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eduLevel.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getEduLevel() throws Exception {
        // Initialize the database
        eduLevelRepository.saveAndFlush(eduLevel);

        // Get the eduLevel
        restEduLevelMockMvc.perform(get("/api/eduLevels/{id}", eduLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(eduLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEduLevel() throws Exception {
        // Get the eduLevel
        restEduLevelMockMvc.perform(get("/api/eduLevels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEduLevel() throws Exception {
        // Initialize the database
        eduLevelRepository.saveAndFlush(eduLevel);

		int databaseSizeBeforeUpdate = eduLevelRepository.findAll().size();

        // Update the eduLevel
        eduLevel.setName(UPDATED_NAME);
        eduLevel.setDescription(UPDATED_DESCRIPTION);
        eduLevel.setStatus(UPDATED_STATUS);

        restEduLevelMockMvc.perform(put("/api/eduLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eduLevel)))
                .andExpect(status().isOk());

        // Validate the EduLevel in the database
        List<EduLevel> eduLevels = eduLevelRepository.findAll();
        assertThat(eduLevels).hasSize(databaseSizeBeforeUpdate);
        EduLevel testEduLevel = eduLevels.get(eduLevels.size() - 1);
        assertThat(testEduLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEduLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEduLevel.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteEduLevel() throws Exception {
        // Initialize the database
        eduLevelRepository.saveAndFlush(eduLevel);

		int databaseSizeBeforeDelete = eduLevelRepository.findAll().size();

        // Get the eduLevel
        restEduLevelMockMvc.perform(delete("/api/eduLevels/{id}", eduLevel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EduLevel> eduLevels = eduLevelRepository.findAll();
        assertThat(eduLevels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
