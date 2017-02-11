/*
package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsTrade;
import gov.step.app.repository.CmsTradeRepository;
import gov.step.app.repository.search.CmsTradeSearchRepository;

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
 * Test class for the CmsTradeResource REST controller.
 *
 * @see CmsTradeResource
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsTradeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsTradeRepository cmsTradeRepository;

    @Inject
    private CmsTradeSearchRepository cmsTradeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsTradeMockMvc;

    private CmsTrade cmsTrade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsTradeResource cmsTradeResource = new CmsTradeResource();
        ReflectionTestUtils.setField(cmsTradeResource, "cmsTradeRepository", cmsTradeRepository);
        ReflectionTestUtils.setField(cmsTradeResource, "cmsTradeSearchRepository", cmsTradeSearchRepository);
        this.restCmsTradeMockMvc = MockMvcBuilders.standaloneSetup(cmsTradeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsTrade = new CmsTrade();
        cmsTrade.setCode(DEFAULT_CODE);
        cmsTrade.setName(DEFAULT_NAME);
        cmsTrade.setDescription(DEFAULT_DESCRIPTION);
        cmsTrade.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsTrade() throws Exception {
        int databaseSizeBeforeCreate = cmsTradeRepository.findAll().size();

        // Create the CmsTrade

        restCmsTradeMockMvc.perform(post("/api/cmsTrades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsTrade)))
                .andExpect(status().isCreated());

        // Validate the CmsTrade in the database
        List<CmsTrade> cmsTrades = cmsTradeRepository.findAll();
        assertThat(cmsTrades).hasSize(databaseSizeBeforeCreate + 1);
        CmsTrade testCmsTrade = cmsTrades.get(cmsTrades.size() - 1);
        assertThat(testCmsTrade.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCmsTrade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCmsTrade.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsTrade.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsTradeRepository.findAll().size();
        // set the field null
        cmsTrade.setCode(null);

        // Create the CmsTrade, which fails.

        restCmsTradeMockMvc.perform(post("/api/cmsTrades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsTrade)))
                .andExpect(status().isBadRequest());

        List<CmsTrade> cmsTrades = cmsTradeRepository.findAll();
        assertThat(cmsTrades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsTradeRepository.findAll().size();
        // set the field null
        cmsTrade.setName(null);

        // Create the CmsTrade, which fails.

        restCmsTradeMockMvc.perform(post("/api/cmsTrades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsTrade)))
                .andExpect(status().isBadRequest());

        List<CmsTrade> cmsTrades = cmsTradeRepository.findAll();
        assertThat(cmsTrades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsTrades() throws Exception {
        // Initialize the database
        cmsTradeRepository.saveAndFlush(cmsTrade);

        // Get all the cmsTrades
        restCmsTradeMockMvc.perform(get("/api/cmsTrades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cmsTrade.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsTrade() throws Exception {
        // Initialize the database
        cmsTradeRepository.saveAndFlush(cmsTrade);

        // Get the cmsTrade
        restCmsTradeMockMvc.perform(get("/api/cmsTrades/{id}", cmsTrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsTrade.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsTrade() throws Exception {
        // Get the cmsTrade
        restCmsTradeMockMvc.perform(get("/api/cmsTrades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsTrade() throws Exception {
        // Initialize the database
        cmsTradeRepository.saveAndFlush(cmsTrade);

		int databaseSizeBeforeUpdate = cmsTradeRepository.findAll().size();

        // Update the cmsTrade
        cmsTrade.setCode(UPDATED_CODE);
        cmsTrade.setName(UPDATED_NAME);
        cmsTrade.setDescription(UPDATED_DESCRIPTION);
        cmsTrade.setStatus(UPDATED_STATUS);

        restCmsTradeMockMvc.perform(put("/api/cmsTrades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cmsTrade)))
                .andExpect(status().isOk());

        // Validate the CmsTrade in the database
        List<CmsTrade> cmsTrades = cmsTradeRepository.findAll();
        assertThat(cmsTrades).hasSize(databaseSizeBeforeUpdate);
        CmsTrade testCmsTrade = cmsTrades.get(cmsTrades.size() - 1);
        assertThat(testCmsTrade.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCmsTrade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCmsTrade.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsTrade.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsTrade() throws Exception {
        // Initialize the database
        cmsTradeRepository.saveAndFlush(cmsTrade);

		int databaseSizeBeforeDelete = cmsTradeRepository.findAll().size();

        // Get the cmsTrade
        restCmsTradeMockMvc.perform(delete("/api/cmsTrades/{id}", cmsTrade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsTrade> cmsTrades = cmsTradeRepository.findAll();
        assertThat(cmsTrades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/

package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CmsTrade;
import gov.step.app.repository.CmsTradeRepository;
import gov.step.app.repository.search.CmsTradeSearchRepository;

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
 * Test class for the CmsTradeResource REST controller.
 *
 * @see CmsTradeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CmsTradeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private CmsTradeRepository cmsTradeRepository;

    @Inject
    private CmsTradeSearchRepository cmsTradeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCmsTradeMockMvc;

    private CmsTrade cmsTrade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CmsTradeResource cmsTradeResource = new CmsTradeResource();
        ReflectionTestUtils.setField(cmsTradeResource, "cmsTradeRepository", cmsTradeRepository);
        ReflectionTestUtils.setField(cmsTradeResource, "cmsTradeSearchRepository", cmsTradeSearchRepository);
        this.restCmsTradeMockMvc = MockMvcBuilders.standaloneSetup(cmsTradeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cmsTrade = new CmsTrade();
        cmsTrade.setCode(DEFAULT_CODE);
        cmsTrade.setName(DEFAULT_NAME);
        cmsTrade.setDescription(DEFAULT_DESCRIPTION);
        cmsTrade.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCmsTrade() throws Exception {
        int databaseSizeBeforeCreate = cmsTradeRepository.findAll().size();

        // Create the CmsTrade

        restCmsTradeMockMvc.perform(post("/api/cmsTrades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsTrade)))
            .andExpect(status().isCreated());

        // Validate the CmsTrade in the database
        List<CmsTrade> cmsTrades = cmsTradeRepository.findAll();
        assertThat(cmsTrades).hasSize(databaseSizeBeforeCreate + 1);
        CmsTrade testCmsTrade = cmsTrades.get(cmsTrades.size() - 1);
        assertThat(testCmsTrade.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCmsTrade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCmsTrade.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCmsTrade.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsTradeRepository.findAll().size();
        // set the field null
        cmsTrade.setCode(null);

        // Create the CmsTrade, which fails.

        restCmsTradeMockMvc.perform(post("/api/cmsTrades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsTrade)))
            .andExpect(status().isBadRequest());

        List<CmsTrade> cmsTrades = cmsTradeRepository.findAll();
        assertThat(cmsTrades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cmsTradeRepository.findAll().size();
        // set the field null
        cmsTrade.setName(null);

        // Create the CmsTrade, which fails.

        restCmsTradeMockMvc.perform(post("/api/cmsTrades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsTrade)))
            .andExpect(status().isBadRequest());

        List<CmsTrade> cmsTrades = cmsTradeRepository.findAll();
        assertThat(cmsTrades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCmsTrades() throws Exception {
        // Initialize the database
        cmsTradeRepository.saveAndFlush(cmsTrade);

        // Get all the cmsTrades
        restCmsTradeMockMvc.perform(get("/api/cmsTrades"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmsTrade.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCmsTrade() throws Exception {
        // Initialize the database
        cmsTradeRepository.saveAndFlush(cmsTrade);

        // Get the cmsTrade
        restCmsTradeMockMvc.perform(get("/api/cmsTrades/{id}", cmsTrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cmsTrade.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCmsTrade() throws Exception {
        // Get the cmsTrade
        restCmsTradeMockMvc.perform(get("/api/cmsTrades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmsTrade() throws Exception {
        // Initialize the database
        cmsTradeRepository.saveAndFlush(cmsTrade);

        int databaseSizeBeforeUpdate = cmsTradeRepository.findAll().size();

        // Update the cmsTrade
        cmsTrade.setCode(UPDATED_CODE);
        cmsTrade.setName(UPDATED_NAME);
        cmsTrade.setDescription(UPDATED_DESCRIPTION);
        cmsTrade.setStatus(UPDATED_STATUS);

        restCmsTradeMockMvc.perform(put("/api/cmsTrades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmsTrade)))
            .andExpect(status().isOk());

        // Validate the CmsTrade in the database
        List<CmsTrade> cmsTrades = cmsTradeRepository.findAll();
        assertThat(cmsTrades).hasSize(databaseSizeBeforeUpdate);
        CmsTrade testCmsTrade = cmsTrades.get(cmsTrades.size() - 1);
        assertThat(testCmsTrade.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCmsTrade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCmsTrade.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCmsTrade.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteCmsTrade() throws Exception {
        // Initialize the database
        cmsTradeRepository.saveAndFlush(cmsTrade);

        int databaseSizeBeforeDelete = cmsTradeRepository.findAll().size();

        // Get the cmsTrade
        restCmsTradeMockMvc.perform(delete("/api/cmsTrades/{id}", cmsTrade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmsTrade> cmsTrades = cmsTradeRepository.findAll();
        assertThat(cmsTrades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
