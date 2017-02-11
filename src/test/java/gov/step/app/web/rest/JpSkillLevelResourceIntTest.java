package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JpSkillLevel;
import gov.step.app.repository.JpSkillLevelRepository;
import gov.step.app.repository.search.JpSkillLevelSearchRepository;

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
 * Test class for the JpSkillLevelResource REST controller.
 *
 * @see JpSkillLevelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JpSkillLevelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private JpSkillLevelRepository jpSkillLevelRepository;

    @Inject
    private JpSkillLevelSearchRepository jpSkillLevelSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJpSkillLevelMockMvc;

    private JpSkillLevel jpSkillLevel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpSkillLevelResource jpSkillLevelResource = new JpSkillLevelResource();
        ReflectionTestUtils.setField(jpSkillLevelResource, "jpSkillLevelRepository", jpSkillLevelRepository);
        ReflectionTestUtils.setField(jpSkillLevelResource, "jpSkillLevelSearchRepository", jpSkillLevelSearchRepository);
        this.restJpSkillLevelMockMvc = MockMvcBuilders.standaloneSetup(jpSkillLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jpSkillLevel = new JpSkillLevel();
        jpSkillLevel.setName(DEFAULT_NAME);
        jpSkillLevel.setDescription(DEFAULT_DESCRIPTION);
        jpSkillLevel.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createJpSkillLevel() throws Exception {
        int databaseSizeBeforeCreate = jpSkillLevelRepository.findAll().size();

        // Create the JpSkillLevel

        restJpSkillLevelMockMvc.perform(post("/api/jpSkillLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpSkillLevel)))
                .andExpect(status().isCreated());

        // Validate the JpSkillLevel in the database
        List<JpSkillLevel> jpSkillLevels = jpSkillLevelRepository.findAll();
        assertThat(jpSkillLevels).hasSize(databaseSizeBeforeCreate + 1);
        JpSkillLevel testJpSkillLevel = jpSkillLevels.get(jpSkillLevels.size() - 1);
        assertThat(testJpSkillLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJpSkillLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJpSkillLevel.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpSkillLevelRepository.findAll().size();
        // set the field null
        jpSkillLevel.setName(null);

        // Create the JpSkillLevel, which fails.

        restJpSkillLevelMockMvc.perform(post("/api/jpSkillLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpSkillLevel)))
                .andExpect(status().isBadRequest());

        List<JpSkillLevel> jpSkillLevels = jpSkillLevelRepository.findAll();
        assertThat(jpSkillLevels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJpSkillLevels() throws Exception {
        // Initialize the database
        jpSkillLevelRepository.saveAndFlush(jpSkillLevel);

        // Get all the jpSkillLevels
        restJpSkillLevelMockMvc.perform(get("/api/jpSkillLevels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jpSkillLevel.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getJpSkillLevel() throws Exception {
        // Initialize the database
        jpSkillLevelRepository.saveAndFlush(jpSkillLevel);

        // Get the jpSkillLevel
        restJpSkillLevelMockMvc.perform(get("/api/jpSkillLevels/{id}", jpSkillLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jpSkillLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJpSkillLevel() throws Exception {
        // Get the jpSkillLevel
        restJpSkillLevelMockMvc.perform(get("/api/jpSkillLevels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpSkillLevel() throws Exception {
        // Initialize the database
        jpSkillLevelRepository.saveAndFlush(jpSkillLevel);

		int databaseSizeBeforeUpdate = jpSkillLevelRepository.findAll().size();

        // Update the jpSkillLevel
        jpSkillLevel.setName(UPDATED_NAME);
        jpSkillLevel.setDescription(UPDATED_DESCRIPTION);
        jpSkillLevel.setStatus(UPDATED_STATUS);

        restJpSkillLevelMockMvc.perform(put("/api/jpSkillLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpSkillLevel)))
                .andExpect(status().isOk());

        // Validate the JpSkillLevel in the database
        List<JpSkillLevel> jpSkillLevels = jpSkillLevelRepository.findAll();
        assertThat(jpSkillLevels).hasSize(databaseSizeBeforeUpdate);
        JpSkillLevel testJpSkillLevel = jpSkillLevels.get(jpSkillLevels.size() - 1);
        assertThat(testJpSkillLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJpSkillLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJpSkillLevel.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteJpSkillLevel() throws Exception {
        // Initialize the database
        jpSkillLevelRepository.saveAndFlush(jpSkillLevel);

		int databaseSizeBeforeDelete = jpSkillLevelRepository.findAll().size();

        // Get the jpSkillLevel
        restJpSkillLevelMockMvc.perform(delete("/api/jpSkillLevels/{id}", jpSkillLevel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JpSkillLevel> jpSkillLevels = jpSkillLevelRepository.findAll();
        assertThat(jpSkillLevels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
