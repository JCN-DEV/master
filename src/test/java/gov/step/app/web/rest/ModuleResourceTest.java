package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Module;
import gov.step.app.repository.ModuleRepository;
import gov.step.app.repository.search.ModuleSearchRepository;

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
 * Test class for the ModuleResource REST controller.
 *
 * @see ModuleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ModuleResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_MODULE_CODE = "AAAAA";
    private static final String UPDATED_MODULE_CODE = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private ModuleRepository moduleRepository;

    @Inject
    private ModuleSearchRepository moduleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restModuleMockMvc;

    private Module module;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModuleResource moduleResource = new ModuleResource();
        ReflectionTestUtils.setField(moduleResource, "moduleRepository", moduleRepository);
        ReflectionTestUtils.setField(moduleResource, "moduleSearchRepository", moduleSearchRepository);
        this.restModuleMockMvc = MockMvcBuilders.standaloneSetup(moduleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        module = new Module();
        module.setName(DEFAULT_NAME);
        module.setDescription(DEFAULT_DESCRIPTION);
        module.setModuleCode(DEFAULT_MODULE_CODE);
        module.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createModule() throws Exception {
        int databaseSizeBeforeCreate = moduleRepository.findAll().size();

        // Create the Module

        restModuleMockMvc.perform(post("/api/modules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(module)))
                .andExpect(status().isCreated());

        // Validate the Module in the database
        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(databaseSizeBeforeCreate + 1);
        Module testModule = modules.get(modules.size() - 1);
        assertThat(testModule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testModule.getModuleCode()).isEqualTo(DEFAULT_MODULE_CODE);
        assertThat(testModule.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = moduleRepository.findAll().size();
        // set the field null
        module.setName(null);

        // Create the Module, which fails.

        restModuleMockMvc.perform(post("/api/modules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(module)))
                .andExpect(status().isBadRequest());

        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModuleCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = moduleRepository.findAll().size();
        // set the field null
        module.setModuleCode(null);

        // Create the Module, which fails.

        restModuleMockMvc.perform(post("/api/modules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(module)))
                .andExpect(status().isBadRequest());

        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModules() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the modules
        restModuleMockMvc.perform(get("/api/modules"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(module.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].moduleCode").value(hasItem(DEFAULT_MODULE_CODE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get the module
        restModuleMockMvc.perform(get("/api/modules/{id}", module.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(module.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.moduleCode").value(DEFAULT_MODULE_CODE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingModule() throws Exception {
        // Get the module
        restModuleMockMvc.perform(get("/api/modules/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

		int databaseSizeBeforeUpdate = moduleRepository.findAll().size();

        // Update the module
        module.setName(UPDATED_NAME);
        module.setDescription(UPDATED_DESCRIPTION);
        module.setModuleCode(UPDATED_MODULE_CODE);
        module.setStatus(UPDATED_STATUS);

        restModuleMockMvc.perform(put("/api/modules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(module)))
                .andExpect(status().isOk());

        // Validate the Module in the database
        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(databaseSizeBeforeUpdate);
        Module testModule = modules.get(modules.size() - 1);
        assertThat(testModule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testModule.getModuleCode()).isEqualTo(UPDATED_MODULE_CODE);
        assertThat(testModule.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

		int databaseSizeBeforeDelete = moduleRepository.findAll().size();

        // Get the module
        restModuleMockMvc.perform(delete("/api/modules/{id}", module.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(databaseSizeBeforeDelete - 1);
    }
}
