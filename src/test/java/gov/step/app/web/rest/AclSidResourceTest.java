package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AclSid;
import gov.step.app.repository.AclSidRepository;
import gov.step.app.repository.search.AclSidSearchRepository;

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
 * Test class for the AclSidResource REST controller.
 *
 * @see AclSidResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AclSidResourceTest {


    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;
    private static final String DEFAULT_SID = "AAAAA";
    private static final String UPDATED_SID = "BBBBB";

    @Inject
    private AclSidRepository aclSidRepository;

    @Inject
    private AclSidSearchRepository aclSidSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAclSidMockMvc;

    private AclSid aclSid;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AclSidResource aclSidResource = new AclSidResource();
        ReflectionTestUtils.setField(aclSidResource, "aclSidRepository", aclSidRepository);
        ReflectionTestUtils.setField(aclSidResource, "aclSidSearchRepository", aclSidSearchRepository);
        this.restAclSidMockMvc = MockMvcBuilders.standaloneSetup(aclSidResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aclSid = new AclSid();
        aclSid.setPrincipal(DEFAULT_PRINCIPAL);
        aclSid.setSid(DEFAULT_SID);
    }

    @Test
    @Transactional
    public void createAclSid() throws Exception {
        int databaseSizeBeforeCreate = aclSidRepository.findAll().size();

        // Create the AclSid

        restAclSidMockMvc.perform(post("/api/aclSids")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclSid)))
                .andExpect(status().isCreated());

        // Validate the AclSid in the database
        List<AclSid> aclSids = aclSidRepository.findAll();
        assertThat(aclSids).hasSize(databaseSizeBeforeCreate + 1);
        AclSid testAclSid = aclSids.get(aclSids.size() - 1);
        assertThat(testAclSid.getPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testAclSid.getSid()).isEqualTo(DEFAULT_SID);
    }

    @Test
    @Transactional
    public void checkPrincipalIsRequired() throws Exception {
        int databaseSizeBeforeTest = aclSidRepository.findAll().size();
        // set the field null
        aclSid.setPrincipal(null);

        // Create the AclSid, which fails.

        restAclSidMockMvc.perform(post("/api/aclSids")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclSid)))
                .andExpect(status().isBadRequest());

        List<AclSid> aclSids = aclSidRepository.findAll();
        assertThat(aclSids).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSidIsRequired() throws Exception {
        int databaseSizeBeforeTest = aclSidRepository.findAll().size();
        // set the field null
        aclSid.setSid(null);

        // Create the AclSid, which fails.

        restAclSidMockMvc.perform(post("/api/aclSids")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclSid)))
                .andExpect(status().isBadRequest());

        List<AclSid> aclSids = aclSidRepository.findAll();
        assertThat(aclSids).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAclSids() throws Exception {
        // Initialize the database
        aclSidRepository.saveAndFlush(aclSid);

        // Get all the aclSids
        restAclSidMockMvc.perform(get("/api/aclSids"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aclSid.getId().intValue())))
                .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())))
                .andExpect(jsonPath("$.[*].sid").value(hasItem(DEFAULT_SID.toString())));
    }

    @Test
    @Transactional
    public void getAclSid() throws Exception {
        // Initialize the database
        aclSidRepository.saveAndFlush(aclSid);

        // Get the aclSid
        restAclSidMockMvc.perform(get("/api/aclSids/{id}", aclSid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aclSid.getId().intValue()))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()))
            .andExpect(jsonPath("$.sid").value(DEFAULT_SID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAclSid() throws Exception {
        // Get the aclSid
        restAclSidMockMvc.perform(get("/api/aclSids/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAclSid() throws Exception {
        // Initialize the database
        aclSidRepository.saveAndFlush(aclSid);

		int databaseSizeBeforeUpdate = aclSidRepository.findAll().size();

        // Update the aclSid
        aclSid.setPrincipal(UPDATED_PRINCIPAL);
        aclSid.setSid(UPDATED_SID);

        restAclSidMockMvc.perform(put("/api/aclSids")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aclSid)))
                .andExpect(status().isOk());

        // Validate the AclSid in the database
        List<AclSid> aclSids = aclSidRepository.findAll();
        assertThat(aclSids).hasSize(databaseSizeBeforeUpdate);
        AclSid testAclSid = aclSids.get(aclSids.size() - 1);
        assertThat(testAclSid.getPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testAclSid.getSid()).isEqualTo(UPDATED_SID);
    }

    @Test
    @Transactional
    public void deleteAclSid() throws Exception {
        // Initialize the database
        aclSidRepository.saveAndFlush(aclSid);

		int databaseSizeBeforeDelete = aclSidRepository.findAll().size();

        // Get the aclSid
        restAclSidMockMvc.perform(delete("/api/aclSids/{id}", aclSid.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AclSid> aclSids = aclSidRepository.findAll();
        assertThat(aclSids).hasSize(databaseSizeBeforeDelete - 1);
    }
}
