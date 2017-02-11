package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AclObjectIdentity;
import gov.step.app.repository.AclObjectIdentityRepository;
import gov.step.app.repository.search.AclObjectIdentitySearchRepository;

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
 * Test class for the AclObjectIdentityResource REST controller.
 *
 * @see AclObjectIdentityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AclObjectIdentityResourceTest {


    private static final Boolean DEFAULT_ENTRIES_INHERITING = false;
    private static final Boolean UPDATED_ENTRIES_INHERITING = true;

    @Inject
    private AclObjectIdentityRepository aclObjectIdentityRepository;

    @Inject
    private AclObjectIdentitySearchRepository aclObjectIdentitySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAclObjectIdentityMockMvc;

    private AclObjectIdentity aclObjectIdentity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AclObjectIdentityResource aclObjectIdentityResource = new AclObjectIdentityResource();
        ReflectionTestUtils.setField(aclObjectIdentityResource, "aclObjectIdentityRepository", aclObjectIdentityRepository);
        ReflectionTestUtils.setField(aclObjectIdentityResource, "aclObjectIdentitySearchRepository", aclObjectIdentitySearchRepository);
        this.restAclObjectIdentityMockMvc = MockMvcBuilders.standaloneSetup(aclObjectIdentityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aclObjectIdentity = new AclObjectIdentity();
        aclObjectIdentity.setEntriesInheriting(DEFAULT_ENTRIES_INHERITING);
    }

    @Test
    @Transactional
    public void createAclObjectIdentity() throws Exception {
        int databaseSizeBeforeCreate = aclObjectIdentityRepository.findAll().size();

        // Create the AclObjectIdentity

        restAclObjectIdentityMockMvc.perform(post("/api/aclObjectIdentitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclObjectIdentity)))
                .andExpect(status().isCreated());

        // Validate the AclObjectIdentity in the database
        List<AclObjectIdentity> aclObjectIdentitys = aclObjectIdentityRepository.findAll();
        assertThat(aclObjectIdentitys).hasSize(databaseSizeBeforeCreate + 1);
        AclObjectIdentity testAclObjectIdentity = aclObjectIdentitys.get(aclObjectIdentitys.size() - 1);
        assertThat(testAclObjectIdentity.getEntriesInheriting()).isEqualTo(DEFAULT_ENTRIES_INHERITING);
    }

    @Test
    @Transactional
    public void checkEntriesInheritingIsRequired() throws Exception {
        int databaseSizeBeforeTest = aclObjectIdentityRepository.findAll().size();
        // set the field null
        aclObjectIdentity.setEntriesInheriting(null);

        // Create the AclObjectIdentity, which fails.

        restAclObjectIdentityMockMvc.perform(post("/api/aclObjectIdentitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclObjectIdentity)))
                .andExpect(status().isBadRequest());

        List<AclObjectIdentity> aclObjectIdentitys = aclObjectIdentityRepository.findAll();
        assertThat(aclObjectIdentitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAclObjectIdentitys() throws Exception {
        // Initialize the database
        aclObjectIdentityRepository.saveAndFlush(aclObjectIdentity);

        // Get all the aclObjectIdentitys
        restAclObjectIdentityMockMvc.perform(get("/api/aclObjectIdentitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aclObjectIdentity.getId().intValue())))
                .andExpect(jsonPath("$.[*].entriesInheriting").value(hasItem(DEFAULT_ENTRIES_INHERITING.booleanValue())));
    }

    @Test
    @Transactional
    public void getAclObjectIdentity() throws Exception {
        // Initialize the database
        aclObjectIdentityRepository.saveAndFlush(aclObjectIdentity);

        // Get the aclObjectIdentity
        restAclObjectIdentityMockMvc.perform(get("/api/aclObjectIdentitys/{id}", aclObjectIdentity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aclObjectIdentity.getId().intValue()))
            .andExpect(jsonPath("$.entriesInheriting").value(DEFAULT_ENTRIES_INHERITING.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAclObjectIdentity() throws Exception {
        // Get the aclObjectIdentity
        restAclObjectIdentityMockMvc.perform(get("/api/aclObjectIdentitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAclObjectIdentity() throws Exception {
        // Initialize the database
        aclObjectIdentityRepository.saveAndFlush(aclObjectIdentity);

		int databaseSizeBeforeUpdate = aclObjectIdentityRepository.findAll().size();

        // Update the aclObjectIdentity
        aclObjectIdentity.setEntriesInheriting(UPDATED_ENTRIES_INHERITING);

        restAclObjectIdentityMockMvc.perform(put("/api/aclObjectIdentitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclObjectIdentity)))
                .andExpect(status().isOk());

        // Validate the AclObjectIdentity in the database
        List<AclObjectIdentity> aclObjectIdentitys = aclObjectIdentityRepository.findAll();
        assertThat(aclObjectIdentitys).hasSize(databaseSizeBeforeUpdate);
        AclObjectIdentity testAclObjectIdentity = aclObjectIdentitys.get(aclObjectIdentitys.size() - 1);
        assertThat(testAclObjectIdentity.getEntriesInheriting()).isEqualTo(UPDATED_ENTRIES_INHERITING);
    }

    @Test
    @Transactional
    public void deleteAclObjectIdentity() throws Exception {
        // Initialize the database
        aclObjectIdentityRepository.saveAndFlush(aclObjectIdentity);

		int databaseSizeBeforeDelete = aclObjectIdentityRepository.findAll().size();

        // Get the aclObjectIdentity
        restAclObjectIdentityMockMvc.perform(delete("/api/aclObjectIdentitys/{id}", aclObjectIdentity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AclObjectIdentity> aclObjectIdentitys = aclObjectIdentityRepository.findAll();
        assertThat(aclObjectIdentitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
