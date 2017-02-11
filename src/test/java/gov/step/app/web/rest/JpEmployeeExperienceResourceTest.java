package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JpEmployeeExperience;
import gov.step.app.repository.JpEmployeeExperienceRepository;
import gov.step.app.repository.search.JpEmployeeExperienceSearchRepository;

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
 * Test class for the JpEmployeeExperienceResource REST controller.
 *
 * @see JpEmployeeExperienceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JpEmployeeExperienceResourceTest {

    private static final String DEFAULT_SKILL = "AAAAA";
    private static final String UPDATED_SKILL = "BBBBB";
    private static final String DEFAULT_SKILL_DESCRIPTION = "AAAAA";
    private static final String UPDATED_SKILL_DESCRIPTION = "BBBBB";

    @Inject
    private JpEmployeeExperienceRepository jpEmployeeExperienceRepository;

    @Inject
    private JpEmployeeExperienceSearchRepository jpEmployeeExperienceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJpEmployeeExperienceMockMvc;

    private JpEmployeeExperience jpEmployeeExperience;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpEmployeeExperienceResource jpEmployeeExperienceResource = new JpEmployeeExperienceResource();
        ReflectionTestUtils.setField(jpEmployeeExperienceResource, "jpEmployeeExperienceRepository", jpEmployeeExperienceRepository);
        ReflectionTestUtils.setField(jpEmployeeExperienceResource, "jpEmployeeExperienceSearchRepository", jpEmployeeExperienceSearchRepository);
        this.restJpEmployeeExperienceMockMvc = MockMvcBuilders.standaloneSetup(jpEmployeeExperienceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jpEmployeeExperience = new JpEmployeeExperience();
        jpEmployeeExperience.setSkill(DEFAULT_SKILL);
        jpEmployeeExperience.setSkillDescription(DEFAULT_SKILL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createJpEmployeeExperience() throws Exception {
        int databaseSizeBeforeCreate = jpEmployeeExperienceRepository.findAll().size();

        // Create the JpEmployeeExperience

        restJpEmployeeExperienceMockMvc.perform(post("/api/jpEmployeeExperiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeExperience)))
                .andExpect(status().isCreated());

        // Validate the JpEmployeeExperience in the database
        List<JpEmployeeExperience> jpEmployeeExperiences = jpEmployeeExperienceRepository.findAll();
        assertThat(jpEmployeeExperiences).hasSize(databaseSizeBeforeCreate + 1);
        JpEmployeeExperience testJpEmployeeExperience = jpEmployeeExperiences.get(jpEmployeeExperiences.size() - 1);
        assertThat(testJpEmployeeExperience.getSkill()).isEqualTo(DEFAULT_SKILL);
        assertThat(testJpEmployeeExperience.getSkillDescription()).isEqualTo(DEFAULT_SKILL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllJpEmployeeExperiences() throws Exception {
        // Initialize the database
        jpEmployeeExperienceRepository.saveAndFlush(jpEmployeeExperience);

        // Get all the jpEmployeeExperiences
        restJpEmployeeExperienceMockMvc.perform(get("/api/jpEmployeeExperiences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jpEmployeeExperience.getId().intValue())))
                .andExpect(jsonPath("$.[*].skill").value(hasItem(DEFAULT_SKILL.toString())))
                .andExpect(jsonPath("$.[*].skillDescription").value(hasItem(DEFAULT_SKILL_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getJpEmployeeExperience() throws Exception {
        // Initialize the database
        jpEmployeeExperienceRepository.saveAndFlush(jpEmployeeExperience);

        // Get the jpEmployeeExperience
        restJpEmployeeExperienceMockMvc.perform(get("/api/jpEmployeeExperiences/{id}", jpEmployeeExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jpEmployeeExperience.getId().intValue()))
            .andExpect(jsonPath("$.skill").value(DEFAULT_SKILL.toString()))
            .andExpect(jsonPath("$.skillDescription").value(DEFAULT_SKILL_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJpEmployeeExperience() throws Exception {
        // Get the jpEmployeeExperience
        restJpEmployeeExperienceMockMvc.perform(get("/api/jpEmployeeExperiences/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpEmployeeExperience() throws Exception {
        // Initialize the database
        jpEmployeeExperienceRepository.saveAndFlush(jpEmployeeExperience);

		int databaseSizeBeforeUpdate = jpEmployeeExperienceRepository.findAll().size();

        // Update the jpEmployeeExperience
        jpEmployeeExperience.setSkill(UPDATED_SKILL);
        jpEmployeeExperience.setSkillDescription(UPDATED_SKILL_DESCRIPTION);

        restJpEmployeeExperienceMockMvc.perform(put("/api/jpEmployeeExperiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpEmployeeExperience)))
                .andExpect(status().isOk());

        // Validate the JpEmployeeExperience in the database
        List<JpEmployeeExperience> jpEmployeeExperiences = jpEmployeeExperienceRepository.findAll();
        assertThat(jpEmployeeExperiences).hasSize(databaseSizeBeforeUpdate);
        JpEmployeeExperience testJpEmployeeExperience = jpEmployeeExperiences.get(jpEmployeeExperiences.size() - 1);
        assertThat(testJpEmployeeExperience.getSkill()).isEqualTo(UPDATED_SKILL);
        assertThat(testJpEmployeeExperience.getSkillDescription()).isEqualTo(UPDATED_SKILL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteJpEmployeeExperience() throws Exception {
        // Initialize the database
        jpEmployeeExperienceRepository.saveAndFlush(jpEmployeeExperience);

		int databaseSizeBeforeDelete = jpEmployeeExperienceRepository.findAll().size();

        // Get the jpEmployeeExperience
        restJpEmployeeExperienceMockMvc.perform(delete("/api/jpEmployeeExperiences/{id}", jpEmployeeExperience.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JpEmployeeExperience> jpEmployeeExperiences = jpEmployeeExperienceRepository.findAll();
        assertThat(jpEmployeeExperiences).hasSize(databaseSizeBeforeDelete - 1);
    }
}
