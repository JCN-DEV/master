package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AclClass;
import gov.step.app.repository.AclClassRepository;
import gov.step.app.repository.search.AclClassSearchRepository;

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
 * Test class for the AclClassResource REST controller.
 *
 * @see AclClassResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AclClassResourceTest {

    private static final String DEFAULT_ACL_CLASS = "AAAAA";
    private static final String UPDATED_ACL_CLASS = "BBBBB";

    @Inject
    private AclClassRepository aclClassRepository;

    @Inject
    private AclClassSearchRepository aclClassSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAclClassMockMvc;

    private AclClass aclClass;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AclClassResource aclClassResource = new AclClassResource();
        ReflectionTestUtils.setField(aclClassResource, "aclClassRepository", aclClassRepository);
        ReflectionTestUtils.setField(aclClassResource, "aclClassSearchRepository", aclClassSearchRepository);
        this.restAclClassMockMvc = MockMvcBuilders.standaloneSetup(aclClassResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aclClass = new AclClass();
        aclClass.setAclClass(DEFAULT_ACL_CLASS);
    }

    @Test
    @Transactional
    public void createAclClass() throws Exception {
        int databaseSizeBeforeCreate = aclClassRepository.findAll().size();

        // Create the AclClass

        restAclClassMockMvc.perform(post("/api/aclClasss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclClass)))
                .andExpect(status().isCreated());

        // Validate the AclClass in the database
        List<AclClass> aclClasss = aclClassRepository.findAll();
        assertThat(aclClasss).hasSize(databaseSizeBeforeCreate + 1);
        AclClass testAclClass = aclClasss.get(aclClasss.size() - 1);
        assertThat(testAclClass.getAclClass()).isEqualTo(DEFAULT_ACL_CLASS);
    }

    @Test
    @Transactional
    public void checkAclClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = aclClassRepository.findAll().size();
        // set the field null
        aclClass.setAclClass(null);

        // Create the AclClass, which fails.

        restAclClassMockMvc.perform(post("/api/aclClasss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclClass)))
                .andExpect(status().isBadRequest());

        List<AclClass> aclClasss = aclClassRepository.findAll();
        assertThat(aclClasss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAclClasss() throws Exception {
        // Initialize the database
        aclClassRepository.saveAndFlush(aclClass);

        // Get all the aclClasss
        restAclClassMockMvc.perform(get("/api/aclClasss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aclClass.getId().intValue())))
                .andExpect(jsonPath("$.[*].aclClass").value(hasItem(DEFAULT_ACL_CLASS.toString())));
    }

    @Test
    @Transactional
    public void getAclClass() throws Exception {
        // Initialize the database
        aclClassRepository.saveAndFlush(aclClass);

        // Get the aclClass
        restAclClassMockMvc.perform(get("/api/aclClasss/{id}", aclClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aclClass.getId().intValue()))
            .andExpect(jsonPath("$.aclClass").value(DEFAULT_ACL_CLASS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAclClass() throws Exception {
        // Get the aclClass
        restAclClassMockMvc.perform(get("/api/aclClasss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAclClass() throws Exception {
        // Initialize the database
        aclClassRepository.saveAndFlush(aclClass);

		int databaseSizeBeforeUpdate = aclClassRepository.findAll().size();

        // Update the aclClass
        aclClass.setAclClass(UPDATED_ACL_CLASS);

        restAclClassMockMvc.perform(put("/api/aclClasss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclClass)))
                .andExpect(status().isOk());

        // Validate the AclClass in the database
        List<AclClass> aclClasss = aclClassRepository.findAll();
        assertThat(aclClasss).hasSize(databaseSizeBeforeUpdate);
        AclClass testAclClass = aclClasss.get(aclClasss.size() - 1);
        assertThat(testAclClass.getAclClass()).isEqualTo(UPDATED_ACL_CLASS);
    }

    @Test
    @Transactional
    public void deleteAclClass() throws Exception {
        // Initialize the database
        aclClassRepository.saveAndFlush(aclClass);

		int databaseSizeBeforeDelete = aclClassRepository.findAll().size();

        // Get the aclClass
        restAclClassMockMvc.perform(delete("/api/aclClasss/{id}", aclClass.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AclClass> aclClasss = aclClassRepository.findAll();
        assertThat(aclClasss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
