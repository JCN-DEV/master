package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Lang;
import gov.step.app.repository.LangRepository;
import gov.step.app.repository.search.LangSearchRepository;

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
 * Test class for the LangResource REST controller.
 *
 * @see LangResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LangResourceTest {

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

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private LangRepository langRepository;

    @Inject
    private LangSearchRepository langSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLangMockMvc;

    private Lang lang;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LangResource langResource = new LangResource();
        ReflectionTestUtils.setField(langResource, "langRepository", langRepository);
        ReflectionTestUtils.setField(langResource, "langSearchRepository", langSearchRepository);
        this.restLangMockMvc = MockMvcBuilders.standaloneSetup(langResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lang = new Lang();
        lang.setName(DEFAULT_NAME);
        lang.setReading(DEFAULT_READING);
        lang.setWriting(DEFAULT_WRITING);
        lang.setSpeaking(DEFAULT_SPEAKING);
        lang.setListening(DEFAULT_LISTENING);
        lang.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createLang() throws Exception {
        int databaseSizeBeforeCreate = langRepository.findAll().size();

        // Create the Lang

        restLangMockMvc.perform(post("/api/langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lang)))
                .andExpect(status().isCreated());

        // Validate the Lang in the database
        List<Lang> langs = langRepository.findAll();
        assertThat(langs).hasSize(databaseSizeBeforeCreate + 1);
        Lang testLang = langs.get(langs.size() - 1);
        assertThat(testLang.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLang.getReading()).isEqualTo(DEFAULT_READING);
        assertThat(testLang.getWriting()).isEqualTo(DEFAULT_WRITING);
        assertThat(testLang.getSpeaking()).isEqualTo(DEFAULT_SPEAKING);
        assertThat(testLang.getListening()).isEqualTo(DEFAULT_LISTENING);
        assertThat(testLang.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = langRepository.findAll().size();
        // set the field null
        lang.setName(null);

        // Create the Lang, which fails.

        restLangMockMvc.perform(post("/api/langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lang)))
                .andExpect(status().isBadRequest());

        List<Lang> langs = langRepository.findAll();
        assertThat(langs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLangs() throws Exception {
        // Initialize the database
        langRepository.saveAndFlush(lang);

        // Get all the langs
        restLangMockMvc.perform(get("/api/langs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lang.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].reading").value(hasItem(DEFAULT_READING.toString())))
                .andExpect(jsonPath("$.[*].writing").value(hasItem(DEFAULT_WRITING.toString())))
                .andExpect(jsonPath("$.[*].speaking").value(hasItem(DEFAULT_SPEAKING.toString())))
                .andExpect(jsonPath("$.[*].listening").value(hasItem(DEFAULT_LISTENING.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getLang() throws Exception {
        // Initialize the database
        langRepository.saveAndFlush(lang);

        // Get the lang
        restLangMockMvc.perform(get("/api/langs/{id}", lang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lang.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.reading").value(DEFAULT_READING.toString()))
            .andExpect(jsonPath("$.writing").value(DEFAULT_WRITING.toString()))
            .andExpect(jsonPath("$.speaking").value(DEFAULT_SPEAKING.toString()))
            .andExpect(jsonPath("$.listening").value(DEFAULT_LISTENING.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLang() throws Exception {
        // Get the lang
        restLangMockMvc.perform(get("/api/langs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLang() throws Exception {
        // Initialize the database
        langRepository.saveAndFlush(lang);

		int databaseSizeBeforeUpdate = langRepository.findAll().size();

        // Update the lang
        lang.setName(UPDATED_NAME);
        lang.setReading(UPDATED_READING);
        lang.setWriting(UPDATED_WRITING);
        lang.setSpeaking(UPDATED_SPEAKING);
        lang.setListening(UPDATED_LISTENING);
        lang.setStatus(UPDATED_STATUS);

        restLangMockMvc.perform(put("/api/langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lang)))
                .andExpect(status().isOk());

        // Validate the Lang in the database
        List<Lang> langs = langRepository.findAll();
        assertThat(langs).hasSize(databaseSizeBeforeUpdate);
        Lang testLang = langs.get(langs.size() - 1);
        assertThat(testLang.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLang.getReading()).isEqualTo(UPDATED_READING);
        assertThat(testLang.getWriting()).isEqualTo(UPDATED_WRITING);
        assertThat(testLang.getSpeaking()).isEqualTo(UPDATED_SPEAKING);
        assertThat(testLang.getListening()).isEqualTo(UPDATED_LISTENING);
        assertThat(testLang.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteLang() throws Exception {
        // Initialize the database
        langRepository.saveAndFlush(lang);

		int databaseSizeBeforeDelete = langRepository.findAll().size();

        // Get the lang
        restLangMockMvc.perform(delete("/api/langs/{id}", lang.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lang> langs = langRepository.findAll();
        assertThat(langs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
