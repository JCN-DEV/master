package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.JpLanguageProficiency;
import gov.step.app.repository.JpLanguageProficiencyRepository;
import gov.step.app.repository.search.JpLanguageProficiencySearchRepository;

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
 * Test class for the JpLanguageProficiencyResource REST controller.
 *
 * @see JpLanguageProficiencyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JpLanguageProficiencyResourceTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_READING = "AAAAA";
    private static final String UPDATED_READING = "BBBBB";
    private static final String DEFAULT_WRITING = "AAAAA";
    private static final String UPDATED_WRITING = "BBBBB";
    private static final String DEFAULT_SPEAKING = "AAAAA";
    private static final String UPDATED_SPEAKING = "BBBBB";
    private static final String DEFAULT_LISTENING = "AAAAA";
    private static final String UPDATED_LISTENING = "BBBBB";

    @Inject
    private JpLanguageProficiencyRepository jpLanguageProficiencyRepository;

    @Inject
    private JpLanguageProficiencySearchRepository jpLanguageProficiencySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJpLanguageProficiencyMockMvc;

    private JpLanguageProficiency jpLanguageProficiency;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpLanguageProficiencyResource jpLanguageProficiencyResource = new JpLanguageProficiencyResource();
        ReflectionTestUtils.setField(jpLanguageProficiencyResource, "jpLanguageProficiencyRepository", jpLanguageProficiencyRepository);
        ReflectionTestUtils.setField(jpLanguageProficiencyResource, "jpLanguageProficiencySearchRepository", jpLanguageProficiencySearchRepository);
        this.restJpLanguageProficiencyMockMvc = MockMvcBuilders.standaloneSetup(jpLanguageProficiencyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jpLanguageProficiency = new JpLanguageProficiency();
        jpLanguageProficiency.setName(DEFAULT_NAME);
        /*jpLanguageProficiency.setReading(DEFAULT_READING);
        jpLanguageProficiency.setWriting(DEFAULT_WRITING);
        jpLanguageProficiency.setSpeaking(DEFAULT_SPEAKING);
        jpLanguageProficiency.setListening(DEFAULT_LISTENING);*/
    }

    @Test
    @Transactional
    public void createJpLanguageProficiency() throws Exception {
        int databaseSizeBeforeCreate = jpLanguageProficiencyRepository.findAll().size();

        // Create the JpLanguageProficiency

        restJpLanguageProficiencyMockMvc.perform(post("/api/jpLanguageProficiencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpLanguageProficiency)))
                .andExpect(status().isCreated());

        // Validate the JpLanguageProficiency in the database
        List<JpLanguageProficiency> jpLanguageProficiencys = jpLanguageProficiencyRepository.findAll();
        assertThat(jpLanguageProficiencys).hasSize(databaseSizeBeforeCreate + 1);
        JpLanguageProficiency testJpLanguageProficiency = jpLanguageProficiencys.get(jpLanguageProficiencys.size() - 1);
        assertThat(testJpLanguageProficiency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJpLanguageProficiency.getReading()).isEqualTo(DEFAULT_READING);
        assertThat(testJpLanguageProficiency.getWriting()).isEqualTo(DEFAULT_WRITING);
        assertThat(testJpLanguageProficiency.getSpeaking()).isEqualTo(DEFAULT_SPEAKING);
        assertThat(testJpLanguageProficiency.getListening()).isEqualTo(DEFAULT_LISTENING);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpLanguageProficiencyRepository.findAll().size();
        // set the field null
        jpLanguageProficiency.setName(null);

        // Create the JpLanguageProficiency, which fails.

        restJpLanguageProficiencyMockMvc.perform(post("/api/jpLanguageProficiencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpLanguageProficiency)))
                .andExpect(status().isBadRequest());

        List<JpLanguageProficiency> jpLanguageProficiencys = jpLanguageProficiencyRepository.findAll();
        assertThat(jpLanguageProficiencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJpLanguageProficiencys() throws Exception {
        // Initialize the database
        jpLanguageProficiencyRepository.saveAndFlush(jpLanguageProficiency);

        // Get all the jpLanguageProficiencys
        restJpLanguageProficiencyMockMvc.perform(get("/api/jpLanguageProficiencys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jpLanguageProficiency.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].reading").value(hasItem(DEFAULT_READING.toString())))
                .andExpect(jsonPath("$.[*].writing").value(hasItem(DEFAULT_WRITING.toString())))
                .andExpect(jsonPath("$.[*].speaking").value(hasItem(DEFAULT_SPEAKING.toString())))
                .andExpect(jsonPath("$.[*].listening").value(hasItem(DEFAULT_LISTENING.toString())));
    }

    @Test
    @Transactional
    public void getJpLanguageProficiency() throws Exception {
        // Initialize the database
        jpLanguageProficiencyRepository.saveAndFlush(jpLanguageProficiency);

        // Get the jpLanguageProficiency
        restJpLanguageProficiencyMockMvc.perform(get("/api/jpLanguageProficiencys/{id}", jpLanguageProficiency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jpLanguageProficiency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.reading").value(DEFAULT_READING.toString()))
            .andExpect(jsonPath("$.writing").value(DEFAULT_WRITING.toString()))
            .andExpect(jsonPath("$.speaking").value(DEFAULT_SPEAKING.toString()))
            .andExpect(jsonPath("$.listening").value(DEFAULT_LISTENING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJpLanguageProficiency() throws Exception {
        // Get the jpLanguageProficiency
        restJpLanguageProficiencyMockMvc.perform(get("/api/jpLanguageProficiencys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpLanguageProficiency() throws Exception {
        // Initialize the database
        jpLanguageProficiencyRepository.saveAndFlush(jpLanguageProficiency);

		int databaseSizeBeforeUpdate = jpLanguageProficiencyRepository.findAll().size();

        // Update the jpLanguageProficiency
       /* jpLanguageProficiency.setName(UPDATED_NAME);
        jpLanguageProficiency.setReading(UPDATED_READING);
        jpLanguageProficiency.setWriting(UPDATED_WRITING);
        jpLanguageProficiency.setSpeaking(UPDATED_SPEAKING);
        jpLanguageProficiency.setListening(UPDATED_LISTENING);*/

        restJpLanguageProficiencyMockMvc.perform(put("/api/jpLanguageProficiencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jpLanguageProficiency)))
                .andExpect(status().isOk());

        // Validate the JpLanguageProficiency in the database
        List<JpLanguageProficiency> jpLanguageProficiencys = jpLanguageProficiencyRepository.findAll();
        assertThat(jpLanguageProficiencys).hasSize(databaseSizeBeforeUpdate);
        JpLanguageProficiency testJpLanguageProficiency = jpLanguageProficiencys.get(jpLanguageProficiencys.size() - 1);
        assertThat(testJpLanguageProficiency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJpLanguageProficiency.getReading()).isEqualTo(UPDATED_READING);
        assertThat(testJpLanguageProficiency.getWriting()).isEqualTo(UPDATED_WRITING);
        assertThat(testJpLanguageProficiency.getSpeaking()).isEqualTo(UPDATED_SPEAKING);
        assertThat(testJpLanguageProficiency.getListening()).isEqualTo(UPDATED_LISTENING);
    }

    @Test
    @Transactional
    public void deleteJpLanguageProficiency() throws Exception {
        // Initialize the database
        jpLanguageProficiencyRepository.saveAndFlush(jpLanguageProficiency);

		int databaseSizeBeforeDelete = jpLanguageProficiencyRepository.findAll().size();

        // Get the jpLanguageProficiency
        restJpLanguageProficiencyMockMvc.perform(delete("/api/jpLanguageProficiencys/{id}", jpLanguageProficiency.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JpLanguageProficiency> jpLanguageProficiencys = jpLanguageProficiencyRepository.findAll();
        assertThat(jpLanguageProficiencys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
