package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AclEntry;
import gov.step.app.repository.AclEntryRepository;
import gov.step.app.repository.search.AclEntrySearchRepository;

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
 * Test class for the AclEntryResource REST controller.
 *
 * @see AclEntryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AclEntryResourceTest {


    private static final Integer DEFAULT_ACE_ORDER = 1;
    private static final Integer UPDATED_ACE_ORDER = 2;

    private static final Integer DEFAULT_MASK = 1;
    private static final Integer UPDATED_MASK = 2;

    private static final Boolean DEFAULT_GRANTING = false;
    private static final Boolean UPDATED_GRANTING = true;

    private static final Boolean DEFAULT_AUDIT_SUCCESS = false;
    private static final Boolean UPDATED_AUDIT_SUCCESS = true;

    private static final Boolean DEFAULT_AUDIT_FAILURE = false;
    private static final Boolean UPDATED_AUDIT_FAILURE = true;

    @Inject
    private AclEntryRepository aclEntryRepository;

    @Inject
    private AclEntrySearchRepository aclEntrySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAclEntryMockMvc;

    private AclEntry aclEntry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AclEntryResource aclEntryResource = new AclEntryResource();
        ReflectionTestUtils.setField(aclEntryResource, "aclEntryRepository", aclEntryRepository);
        ReflectionTestUtils.setField(aclEntryResource, "aclEntrySearchRepository", aclEntrySearchRepository);
        this.restAclEntryMockMvc = MockMvcBuilders.standaloneSetup(aclEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aclEntry = new AclEntry();
        aclEntry.setAceOrder(DEFAULT_ACE_ORDER);
        aclEntry.setMask(DEFAULT_MASK);
        aclEntry.setGranting(DEFAULT_GRANTING);
        aclEntry.setAuditSuccess(DEFAULT_AUDIT_SUCCESS);
        aclEntry.setAuditFailure(DEFAULT_AUDIT_FAILURE);
    }

    @Test
    @Transactional
    public void createAclEntry() throws Exception {
        int databaseSizeBeforeCreate = aclEntryRepository.findAll().size();

        // Create the AclEntry

        restAclEntryMockMvc.perform(post("/api/aclEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclEntry)))
                .andExpect(status().isCreated());

        // Validate the AclEntry in the database
        List<AclEntry> aclEntrys = aclEntryRepository.findAll();
        assertThat(aclEntrys).hasSize(databaseSizeBeforeCreate + 1);
        AclEntry testAclEntry = aclEntrys.get(aclEntrys.size() - 1);
        assertThat(testAclEntry.getAceOrder()).isEqualTo(DEFAULT_ACE_ORDER);
        assertThat(testAclEntry.getMask()).isEqualTo(DEFAULT_MASK);
        assertThat(testAclEntry.getGranting()).isEqualTo(DEFAULT_GRANTING);
        assertThat(testAclEntry.getAuditSuccess()).isEqualTo(DEFAULT_AUDIT_SUCCESS);
        assertThat(testAclEntry.getAuditFailure()).isEqualTo(DEFAULT_AUDIT_FAILURE);
    }

    @Test
    @Transactional
    public void checkAceOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = aclEntryRepository.findAll().size();
        // set the field null
        aclEntry.setAceOrder(null);

        // Create the AclEntry, which fails.

        restAclEntryMockMvc.perform(post("/api/aclEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclEntry)))
                .andExpect(status().isBadRequest());

        List<AclEntry> aclEntrys = aclEntryRepository.findAll();
        assertThat(aclEntrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaskIsRequired() throws Exception {
        int databaseSizeBeforeTest = aclEntryRepository.findAll().size();
        // set the field null
        aclEntry.setMask(null);

        // Create the AclEntry, which fails.

        restAclEntryMockMvc.perform(post("/api/aclEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclEntry)))
                .andExpect(status().isBadRequest());

        List<AclEntry> aclEntrys = aclEntryRepository.findAll();
        assertThat(aclEntrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrantingIsRequired() throws Exception {
        int databaseSizeBeforeTest = aclEntryRepository.findAll().size();
        // set the field null
        aclEntry.setGranting(null);

        // Create the AclEntry, which fails.

        restAclEntryMockMvc.perform(post("/api/aclEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclEntry)))
                .andExpect(status().isBadRequest());

        List<AclEntry> aclEntrys = aclEntryRepository.findAll();
        assertThat(aclEntrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuditSuccessIsRequired() throws Exception {
        int databaseSizeBeforeTest = aclEntryRepository.findAll().size();
        // set the field null
        aclEntry.setAuditSuccess(null);

        // Create the AclEntry, which fails.

        restAclEntryMockMvc.perform(post("/api/aclEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclEntry)))
                .andExpect(status().isBadRequest());

        List<AclEntry> aclEntrys = aclEntryRepository.findAll();
        assertThat(aclEntrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuditFailureIsRequired() throws Exception {
        int databaseSizeBeforeTest = aclEntryRepository.findAll().size();
        // set the field null
        aclEntry.setAuditFailure(null);

        // Create the AclEntry, which fails.

        restAclEntryMockMvc.perform(post("/api/aclEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclEntry)))
                .andExpect(status().isBadRequest());

        List<AclEntry> aclEntrys = aclEntryRepository.findAll();
        assertThat(aclEntrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAclEntrys() throws Exception {
        // Initialize the database
        aclEntryRepository.saveAndFlush(aclEntry);

        // Get all the aclEntrys
        restAclEntryMockMvc.perform(get("/api/aclEntrys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aclEntry.getId().intValue())))
                .andExpect(jsonPath("$.[*].aceOrder").value(hasItem(DEFAULT_ACE_ORDER)))
                .andExpect(jsonPath("$.[*].mask").value(hasItem(DEFAULT_MASK)))
                .andExpect(jsonPath("$.[*].granting").value(hasItem(DEFAULT_GRANTING.booleanValue())))
                .andExpect(jsonPath("$.[*].auditSuccess").value(hasItem(DEFAULT_AUDIT_SUCCESS.booleanValue())))
                .andExpect(jsonPath("$.[*].auditFailure").value(hasItem(DEFAULT_AUDIT_FAILURE.booleanValue())));
    }

    @Test
    @Transactional
    public void getAclEntry() throws Exception {
        // Initialize the database
        aclEntryRepository.saveAndFlush(aclEntry);

        // Get the aclEntry
        restAclEntryMockMvc.perform(get("/api/aclEntrys/{id}", aclEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aclEntry.getId().intValue()))
            .andExpect(jsonPath("$.aceOrder").value(DEFAULT_ACE_ORDER))
            .andExpect(jsonPath("$.mask").value(DEFAULT_MASK))
            .andExpect(jsonPath("$.granting").value(DEFAULT_GRANTING.booleanValue()))
            .andExpect(jsonPath("$.auditSuccess").value(DEFAULT_AUDIT_SUCCESS.booleanValue()))
            .andExpect(jsonPath("$.auditFailure").value(DEFAULT_AUDIT_FAILURE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAclEntry() throws Exception {
        // Get the aclEntry
        restAclEntryMockMvc.perform(get("/api/aclEntrys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAclEntry() throws Exception {
        // Initialize the database
        aclEntryRepository.saveAndFlush(aclEntry);

		int databaseSizeBeforeUpdate = aclEntryRepository.findAll().size();

        // Update the aclEntry
        aclEntry.setAceOrder(UPDATED_ACE_ORDER);
        aclEntry.setMask(UPDATED_MASK);
        aclEntry.setGranting(UPDATED_GRANTING);
        aclEntry.setAuditSuccess(UPDATED_AUDIT_SUCCESS);
        aclEntry.setAuditFailure(UPDATED_AUDIT_FAILURE);

        restAclEntryMockMvc.perform(put("/api/aclEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclEntry)))
                .andExpect(status().isOk());

        // Validate the AclEntry in the database
        List<AclEntry> aclEntrys = aclEntryRepository.findAll();
        assertThat(aclEntrys).hasSize(databaseSizeBeforeUpdate);
        AclEntry testAclEntry = aclEntrys.get(aclEntrys.size() - 1);
        assertThat(testAclEntry.getAceOrder()).isEqualTo(UPDATED_ACE_ORDER);
        assertThat(testAclEntry.getMask()).isEqualTo(UPDATED_MASK);
        assertThat(testAclEntry.getGranting()).isEqualTo(UPDATED_GRANTING);
        assertThat(testAclEntry.getAuditSuccess()).isEqualTo(UPDATED_AUDIT_SUCCESS);
        assertThat(testAclEntry.getAuditFailure()).isEqualTo(UPDATED_AUDIT_FAILURE);
    }

    @Test
    @Transactional
    public void deleteAclEntry() throws Exception {
        // Initialize the database
        aclEntryRepository.saveAndFlush(aclEntry);

		int databaseSizeBeforeDelete = aclEntryRepository.findAll().size();

        // Get the aclEntry
        restAclEntryMockMvc.perform(delete("/api/aclEntrys/{id}", aclEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AclEntry> aclEntrys = aclEntryRepository.findAll();
        assertThat(aclEntrys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
