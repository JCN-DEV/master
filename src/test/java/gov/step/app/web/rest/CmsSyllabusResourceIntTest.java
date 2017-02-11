/*
package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsSyllabus;
import gov.step.app.repository.CmsSyllabusRepository;
import gov.step.app.repository.search.CmsSyllabusSearchRepository;

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


*/
/**
 * Test class for the CmsSyllabusResource REST controller.
 *
 * @see CmsSyllabusResource
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsSyllabusResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsSyllabusRepository cmsSyllabusRepository;

    @Inject
    private CmsSyllabusSearchRepository cmsSyllabusSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsSyllabusMockMvc;

    private CmsSyllabus cmsSyllabus;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsSyllabusResource cmsSyllabusResource = new CmsSyllabusResource();
        ReflectionTestUtils.setField(cmsSyllabusResource, "cmsSyllabusRepository", cmsSyllabusRepository);
        ReflectionTestUtils.setField(cmsSyllabusResource, "cmsSyllabusSearchRepository", cmsSyllabusSearchRepository);
        this.restCmsSyllabusMockMvc = MockMvcBuilders.standaloneSetup(cmsSyllabusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsSyllabus = new CmsSyllabus();
        cmsSyllabus.setVersion(DEFAULT_VERSION);
        cmsSyllabus.setName(DEFAULT_NAME);
        cmsSyllabus.setDescription(DEFAULT_DESCRIPTION);
        cmsSyllabus.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsSyllabus() throws Exception {
        int databaseSizeBeforeCreate = cmsSyllabusRepository.findAll().size();

        // Create the CmsSyllabus

        restCmsSyllabusMockMvc.perform(post("/api/cmsSyllabuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSyllabus)))
                .andExpect(status().isCreated());

        // Validate the CmsSyllabus in the database
        List<CmsSyllabus> cmsSyllabuss = cmsSyllabusRepository.findAll();
        assertThat(cmsSyllabuss).hasSize(databaseSizeBeforeCreate + 1);
        CmsSyllabus testCmsSyllabus = cmsSyllabuss.get(cmsSyllabuss.size() - 1);
        assertThat(testCmsSyllabus.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCmsSyllabus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCmsSyllabus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsSyllabus.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSyllabusRepository.findAll().size();
        // set the field null
        cmsSyllabus.setVersion(null);

        // Create the CmsSyllabus, which fails.

        restCmsSyllabusMockMvc.perform(post("/api/cmsSyllabuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSyllabus)))
                .andExpect(status().isBadRequest());

        List<CmsSyllabus> cmsSyllabuss = cmsSyllabusRepository.findAll();
        assertThat(cmsSyllabuss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSyllabusRepository.findAll().size();
        // set the field null
        cmsSyllabus.setName(null);

        // Create the CmsSyllabus, which fails.

        restCmsSyllabusMockMvc.perform(post("/api/cmsSyllabuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSyllabus)))
                .andExpect(status().isBadRequest());

        List<CmsSyllabus> cmsSyllabuss = cmsSyllabusRepository.findAll();
        assertThat(cmsSyllabuss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsSyllabuss() throws Exception {
        // Initialize the database
        cmsSyllabusRepository.saveAndFlush(cmsSyllabus);

        // Get all the cmsSyllabuss
        restCmsSyllabusMockMvc.perform(get("/api/cmsSyllabuss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cmsSyllabus.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsSyllabus() throws Exception {
        // Initialize the database
        cmsSyllabusRepository.saveAndFlush(cmsSyllabus);

        // Get the cmsSyllabus
        restCmsSyllabusMockMvc.perform(get("/api/cmsSyllabuss/{id}", cmsSyllabus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsSyllabus.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsSyllabus() throws Exception {
        // Get the cmsSyllabus
        restCmsSyllabusMockMvc.perform(get("/api/cmsSyllabuss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsSyllabus() throws Exception {
        // Initialize the database
        cmsSyllabusRepository.saveAndFlush(cmsSyllabus);

		int databaseSizeBeforeUpdate = cmsSyllabusRepository.findAll().size();

        // Update the cmsSyllabus
        cmsSyllabus.setVersion(UPDATED_VERSION);
        cmsSyllabus.setName(UPDATED_NAME);
        cmsSyllabus.setDescription(UPDATED_DESCRIPTION);
        cmsSyllabus.setStatus(UPDATED_STATUS);

        restCmsSyllabusMockMvc.perform(put("/api/cmsSyllabuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsSyllabus)))
                .andExpect(status().isOk());

        // Validate the CmsSyllabus in the database
        List<CmsSyllabus> cmsSyllabuss = cmsSyllabusRepository.findAll();
        assertThat(cmsSyllabuss).hasSize(databaseSizeBeforeUpdate);
        CmsSyllabus testCmsSyllabus = cmsSyllabuss.get(cmsSyllabuss.size() - 1);
        assertThat(testCmsSyllabus.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCmsSyllabus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCmsSyllabus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsSyllabus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsSyllabus() throws Exception {
        // Initialize the database
        cmsSyllabusRepository.saveAndFlush(cmsSyllabus);

		int databaseSizeBeforeDelete = cmsSyllabusRepository.findAll().size();

        // Get the cmsSyllabus
        restCmsSyllabusMockMvc.perform(delete("/api/cmsSyllabuss/{id}", cmsSyllabus.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsSyllabus> cmsSyllabuss = cmsSyllabusRepository.findAll();
        assertThat(cmsSyllabuss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/


package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsSyllabus;
import gov.step.app.repository.CmsSyllabusRepository;
import gov.step.app.repository.search.CmsSyllabusSearchRepository;

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
 * Test class for the CmsSyllabusResource REST controller.
 *
 * @see CmsSyllabusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsSyllabusResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsSyllabusRepository cmsSyllabusRepository;

    @Inject
    private CmsSyllabusSearchRepository cmsSyllabusSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsSyllabusMockMvc;

    private CmsSyllabus cmsSyllabus;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsSyllabusResource cmsSyllabusResource = new CmsSyllabusResource();
        ReflectionTestUtils.setField(cmsSyllabusResource, "cmsSyllabusRepository", cmsSyllabusRepository);
        ReflectionTestUtils.setField(cmsSyllabusResource, "cmsSyllabusSearchRepository", cmsSyllabusSearchRepository);
        this.restCmsSyllabusMockMvc = MockMvcBuilders.standaloneSetup(cmsSyllabusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsSyllabus = new CmsSyllabus();
        cmsSyllabus.setVersion(DEFAULT_VERSION);
        cmsSyllabus.setName(DEFAULT_NAME);
        cmsSyllabus.setDescription(DEFAULT_DESCRIPTION);
        cmsSyllabus.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsSyllabus() throws Exception {
        int databaseSizeBeforeCreate = cmsSyllabusRepository.findAll().size();

        // Create the CmsSyllabus

        restCmsSyllabusMockMvc.perform(post("/api/cmsSyllabuss")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSyllabus)))
            .andExpect(status().isCreated());

        // Validate the CmsSyllabus in the database
        List<CmsSyllabus> cmsSyllabuss = cmsSyllabusRepository.findAll();
        assertThat(cmsSyllabuss).hasSize(databaseSizeBeforeCreate + 1);
        CmsSyllabus testCmsSyllabus = cmsSyllabuss.get(cmsSyllabuss.size() - 1);
        assertThat(testCmsSyllabus.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCmsSyllabus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCmsSyllabus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsSyllabus.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSyllabusRepository.findAll().size();
        // set the field null
        cmsSyllabus.setVersion(null);

        // Create the CmsSyllabus, which fails.

        restCmsSyllabusMockMvc.perform(post("/api/cmsSyllabuss")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSyllabus)))
            .andExpect(status().isBadRequest());

        List<CmsSyllabus> cmsSyllabuss = cmsSyllabusRepository.findAll();
        assertThat(cmsSyllabuss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsSyllabusRepository.findAll().size();
        // set the field null
        cmsSyllabus.setName(null);

        // Create the CmsSyllabus, which fails.

        restCmsSyllabusMockMvc.perform(post("/api/cmsSyllabuss")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSyllabus)))
            .andExpect(status().isBadRequest());

        List<CmsSyllabus> cmsSyllabuss = cmsSyllabusRepository.findAll();
        assertThat(cmsSyllabuss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsSyllabuss() throws Exception {
        // Initialize the database
        cmsSyllabusRepository.saveAndFlush(cmsSyllabus);

        // Get all the cmsSyllabuss
        restCmsSyllabusMockMvc.perform(get("/api/cmsSyllabuss"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmsSyllabus.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsSyllabus() throws Exception {
        // Initialize the database
        cmsSyllabusRepository.saveAndFlush(cmsSyllabus);

        // Get the cmsSyllabus
        restCmsSyllabusMockMvc.perform(get("/api/cmsSyllabuss/{id}", cmsSyllabus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsSyllabus.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsSyllabus() throws Exception {
        // Get the cmsSyllabus
        restCmsSyllabusMockMvc.perform(get("/api/cmsSyllabuss/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsSyllabus() throws Exception {
        // Initialize the database
        cmsSyllabusRepository.saveAndFlush(cmsSyllabus);

        int databaseSizeBeforeUpdate = cmsSyllabusRepository.findAll().size();

        // Update the cmsSyllabus
        cmsSyllabus.setVersion(UPDATED_VERSION);
        cmsSyllabus.setName(UPDATED_NAME);
        cmsSyllabus.setDescription(UPDATED_DESCRIPTION);
        cmsSyllabus.setStatus(UPDATED_STATUS);

        restCmsSyllabusMockMvc.perform(put("/api/cmsSyllabuss")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsSyllabus)))
            .andExpect(status().isOk());

        // Validate the CmsSyllabus in the database
        List<CmsSyllabus> cmsSyllabuss = cmsSyllabusRepository.findAll();
        assertThat(cmsSyllabuss).hasSize(databaseSizeBeforeUpdate);
        CmsSyllabus testCmsSyllabus = cmsSyllabuss.get(cmsSyllabuss.size() - 1);
        assertThat(testCmsSyllabus.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCmsSyllabus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCmsSyllabus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsSyllabus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsSyllabus() throws Exception {
        // Initialize the database
        cmsSyllabusRepository.saveAndFlush(cmsSyllabus);

        int databaseSizeBeforeDelete = cmsSyllabusRepository.findAll().size();

        // Get the cmsSyllabus
        restCmsSyllabusMockMvc.perform(delete("/api/cmsSyllabuss/{id}", cmsSyllabus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsSyllabus> cmsSyllabuss = cmsSyllabusRepository.findAll();
        assertThat(cmsSyllabuss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
