package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstGovBodyAccess;
import gov.step.app.repository.InstGovBodyAccessRepository;
import gov.step.app.repository.search.InstGovBodyAccessSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the InstGovBodyAccessResource REST controller.
 *
 * @see InstGovBodyAccessResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstGovBodyAccessResourceIntTest {


    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstGovBodyAccessRepository instGovBodyAccessRepository;

    @Inject
    private InstGovBodyAccessSearchRepository instGovBodyAccessSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstGovBodyAccessMockMvc;

    private InstGovBodyAccess instGovBodyAccess;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstGovBodyAccessResource instGovBodyAccessResource = new InstGovBodyAccessResource();
        ReflectionTestUtils.setField(instGovBodyAccessResource, "instGovBodyAccessRepository", instGovBodyAccessRepository);
        ReflectionTestUtils.setField(instGovBodyAccessResource, "instGovBodyAccessSearchRepository", instGovBodyAccessSearchRepository);
        this.restInstGovBodyAccessMockMvc = MockMvcBuilders.standaloneSetup(instGovBodyAccessResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instGovBodyAccess = new InstGovBodyAccess();
        instGovBodyAccess.setDateCreated(DEFAULT_DATE_CREATED);
        instGovBodyAccess.setDateModified(DEFAULT_DATE_MODIFIED);
        instGovBodyAccess.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstGovBodyAccess() throws Exception {
        int databaseSizeBeforeCreate = instGovBodyAccessRepository.findAll().size();

        // Create the InstGovBodyAccess

        restInstGovBodyAccessMockMvc.perform(post("/api/instGovBodyAccesss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovBodyAccess)))
                .andExpect(status().isCreated());

        // Validate the InstGovBodyAccess in the database
        List<InstGovBodyAccess> instGovBodyAccesss = instGovBodyAccessRepository.findAll();
        assertThat(instGovBodyAccesss).hasSize(databaseSizeBeforeCreate + 1);
        InstGovBodyAccess testInstGovBodyAccess = instGovBodyAccesss.get(instGovBodyAccesss.size() - 1);
        assertThat(testInstGovBodyAccess.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testInstGovBodyAccess.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testInstGovBodyAccess.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGovBodyAccessRepository.findAll().size();
        // set the field null
        instGovBodyAccess.setDateCreated(null);

        // Create the InstGovBodyAccess, which fails.

        restInstGovBodyAccessMockMvc.perform(post("/api/instGovBodyAccesss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovBodyAccess)))
                .andExpect(status().isBadRequest());

        List<InstGovBodyAccess> instGovBodyAccesss = instGovBodyAccessRepository.findAll();
        assertThat(instGovBodyAccesss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGovBodyAccessRepository.findAll().size();
        // set the field null
        instGovBodyAccess.setDateModified(null);

        // Create the InstGovBodyAccess, which fails.

        restInstGovBodyAccessMockMvc.perform(post("/api/instGovBodyAccesss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovBodyAccess)))
                .andExpect(status().isBadRequest());

        List<InstGovBodyAccess> instGovBodyAccesss = instGovBodyAccessRepository.findAll();
        assertThat(instGovBodyAccesss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstGovBodyAccesss() throws Exception {
        // Initialize the database
        instGovBodyAccessRepository.saveAndFlush(instGovBodyAccess);

        // Get all the instGovBodyAccesss
        restInstGovBodyAccessMockMvc.perform(get("/api/instGovBodyAccesss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instGovBodyAccess.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstGovBodyAccess() throws Exception {
        // Initialize the database
        instGovBodyAccessRepository.saveAndFlush(instGovBodyAccess);

        // Get the instGovBodyAccess
        restInstGovBodyAccessMockMvc.perform(get("/api/instGovBodyAccesss/{id}", instGovBodyAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instGovBodyAccess.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstGovBodyAccess() throws Exception {
        // Get the instGovBodyAccess
        restInstGovBodyAccessMockMvc.perform(get("/api/instGovBodyAccesss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstGovBodyAccess() throws Exception {
        // Initialize the database
        instGovBodyAccessRepository.saveAndFlush(instGovBodyAccess);

		int databaseSizeBeforeUpdate = instGovBodyAccessRepository.findAll().size();

        // Update the instGovBodyAccess
        instGovBodyAccess.setDateCreated(UPDATED_DATE_CREATED);
        instGovBodyAccess.setDateModified(UPDATED_DATE_MODIFIED);
        instGovBodyAccess.setStatus(UPDATED_STATUS);

        restInstGovBodyAccessMockMvc.perform(put("/api/instGovBodyAccesss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovBodyAccess)))
                .andExpect(status().isOk());

        // Validate the InstGovBodyAccess in the database
        List<InstGovBodyAccess> instGovBodyAccesss = instGovBodyAccessRepository.findAll();
        assertThat(instGovBodyAccesss).hasSize(databaseSizeBeforeUpdate);
        InstGovBodyAccess testInstGovBodyAccess = instGovBodyAccesss.get(instGovBodyAccesss.size() - 1);
        assertThat(testInstGovBodyAccess.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testInstGovBodyAccess.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testInstGovBodyAccess.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstGovBodyAccess() throws Exception {
        // Initialize the database
        instGovBodyAccessRepository.saveAndFlush(instGovBodyAccess);

		int databaseSizeBeforeDelete = instGovBodyAccessRepository.findAll().size();

        // Get the instGovBodyAccess
        restInstGovBodyAccessMockMvc.perform(delete("/api/instGovBodyAccesss/{id}", instGovBodyAccess.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstGovBodyAccess> instGovBodyAccesss = instGovBodyAccessRepository.findAll();
        assertThat(instGovBodyAccesss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
