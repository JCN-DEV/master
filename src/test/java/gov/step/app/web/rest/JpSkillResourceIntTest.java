package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JpSkill;
import gov.step.app.repository.JpSkillRepository;
import gov.step.app.repository.search.JpSkillSearchRepository;

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
 * Test class for the JpSkillResource REST controller.
 *
 * @see JpSkillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JpSkillResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private JpSkillRepository jpSkillRepository;

    @Inject
    private JpSkillSearchRepository jpSkillSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJpSkillMockMvc;

    private JpSkill jpSkill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpSkillResource jpSkillResource = new JpSkillResource();
        ReflectionTestUtils.setField(jpSkillResource, "jpSkillRepository", jpSkillRepository);
        ReflectionTestUtils.setField(jpSkillResource, "jpSkillSearchRepository", jpSkillSearchRepository);
        this.restJpSkillMockMvc = MockMvcBuilders.standaloneSetup(jpSkillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jpSkill = new JpSkill();
        jpSkill.setName(DEFAULT_NAME);
        jpSkill.setDescription(DEFAULT_DESCRIPTION);
        jpSkill.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createJpSkill() throws Exception {
        int databaseSizeBeforeCreate = jpSkillRepository.findAll().size();

        // Create the JpSkill

        restJpSkillMockMvc.perform(post("/api/jpSkills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpSkill)))
                .andExpect(status().isCreated());

        // Validate the JpSkill in the database
        List<JpSkill> jpSkills = jpSkillRepository.findAll();
        assertThat(jpSkills).hasSize(databaseSizeBeforeCreate + 1);
        JpSkill testJpSkill = jpSkills.get(jpSkills.size() - 1);
        assertThat(testJpSkill.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJpSkill.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJpSkill.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpSkillRepository.findAll().size();
        // set the field null
        jpSkill.setName(null);

        // Create the JpSkill, which fails.

        restJpSkillMockMvc.perform(post("/api/jpSkills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpSkill)))
                .andExpect(status().isBadRequest());

        List<JpSkill> jpSkills = jpSkillRepository.findAll();
        assertThat(jpSkills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJpSkills() throws Exception {
        // Initialize the database
        jpSkillRepository.saveAndFlush(jpSkill);

        // Get all the jpSkills
        restJpSkillMockMvc.perform(get("/api/jpSkills"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jpSkill.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getJpSkill() throws Exception {
        // Initialize the database
        jpSkillRepository.saveAndFlush(jpSkill);

        // Get the jpSkill
        restJpSkillMockMvc.perform(get("/api/jpSkills/{id}", jpSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jpSkill.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJpSkill() throws Exception {
        // Get the jpSkill
        restJpSkillMockMvc.perform(get("/api/jpSkills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpSkill() throws Exception {
        // Initialize the database
        jpSkillRepository.saveAndFlush(jpSkill);

		int databaseSizeBeforeUpdate = jpSkillRepository.findAll().size();

        // Update the jpSkill
        jpSkill.setName(UPDATED_NAME);
        jpSkill.setDescription(UPDATED_DESCRIPTION);
        jpSkill.setStatus(UPDATED_STATUS);

        restJpSkillMockMvc.perform(put("/api/jpSkills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpSkill)))
                .andExpect(status().isOk());

        // Validate the JpSkill in the database
        List<JpSkill> jpSkills = jpSkillRepository.findAll();
        assertThat(jpSkills).hasSize(databaseSizeBeforeUpdate);
        JpSkill testJpSkill = jpSkills.get(jpSkills.size() - 1);
        assertThat(testJpSkill.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJpSkill.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJpSkill.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteJpSkill() throws Exception {
        // Initialize the database
        jpSkillRepository.saveAndFlush(jpSkill);

		int databaseSizeBeforeDelete = jpSkillRepository.findAll().size();

        // Get the jpSkill
        restJpSkillMockMvc.perform(delete("/api/jpSkills/{id}", jpSkill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JpSkill> jpSkills = jpSkillRepository.findAll();
        assertThat(jpSkills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
