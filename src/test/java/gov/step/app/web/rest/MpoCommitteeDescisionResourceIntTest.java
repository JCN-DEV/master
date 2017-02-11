package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoCommitteeDescision;
import gov.step.app.repository.MpoCommitteeDescisionRepository;
import gov.step.app.repository.search.MpoCommitteeDescisionSearchRepository;

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
 * Test class for the MpoCommitteeDescisionResource REST controller.
 *
 * @see MpoCommitteeDescisionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoCommitteeDescisionResourceIntTest {

    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private MpoCommitteeDescisionRepository mpoCommitteeDescisionRepository;

    @Inject
    private MpoCommitteeDescisionSearchRepository mpoCommitteeDescisionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoCommitteeDescisionMockMvc;

    private MpoCommitteeDescision mpoCommitteeDescision;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoCommitteeDescisionResource mpoCommitteeDescisionResource = new MpoCommitteeDescisionResource();
        ReflectionTestUtils.setField(mpoCommitteeDescisionResource, "mpoCommitteeDescisionRepository", mpoCommitteeDescisionRepository);
        ReflectionTestUtils.setField(mpoCommitteeDescisionResource, "mpoCommitteeDescisionSearchRepository", mpoCommitteeDescisionSearchRepository);
        this.restMpoCommitteeDescisionMockMvc = MockMvcBuilders.standaloneSetup(mpoCommitteeDescisionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoCommitteeDescision = new MpoCommitteeDescision();
        mpoCommitteeDescision.setComments(DEFAULT_COMMENTS);
        mpoCommitteeDescision.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createMpoCommitteeDescision() throws Exception {
        int databaseSizeBeforeCreate = mpoCommitteeDescisionRepository.findAll().size();

        // Create the MpoCommitteeDescision

        restMpoCommitteeDescisionMockMvc.perform(post("/api/mpoCommitteeDescisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoCommitteeDescision)))
                .andExpect(status().isCreated());

        // Validate the MpoCommitteeDescision in the database
        List<MpoCommitteeDescision> mpoCommitteeDescisions = mpoCommitteeDescisionRepository.findAll();
        assertThat(mpoCommitteeDescisions).hasSize(databaseSizeBeforeCreate + 1);
        MpoCommitteeDescision testMpoCommitteeDescision = mpoCommitteeDescisions.get(mpoCommitteeDescisions.size() - 1);
        assertThat(testMpoCommitteeDescision.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testMpoCommitteeDescision.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMpoCommitteeDescisions() throws Exception {
        // Initialize the database
        mpoCommitteeDescisionRepository.saveAndFlush(mpoCommitteeDescision);

        // Get all the mpoCommitteeDescisions
        restMpoCommitteeDescisionMockMvc.perform(get("/api/mpoCommitteeDescisions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoCommitteeDescision.getId().intValue())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getMpoCommitteeDescision() throws Exception {
        // Initialize the database
        mpoCommitteeDescisionRepository.saveAndFlush(mpoCommitteeDescision);

        // Get the mpoCommitteeDescision
        restMpoCommitteeDescisionMockMvc.perform(get("/api/mpoCommitteeDescisions/{id}", mpoCommitteeDescision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoCommitteeDescision.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingMpoCommitteeDescision() throws Exception {
        // Get the mpoCommitteeDescision
        restMpoCommitteeDescisionMockMvc.perform(get("/api/mpoCommitteeDescisions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoCommitteeDescision() throws Exception {
        // Initialize the database
        mpoCommitteeDescisionRepository.saveAndFlush(mpoCommitteeDescision);

		int databaseSizeBeforeUpdate = mpoCommitteeDescisionRepository.findAll().size();

        // Update the mpoCommitteeDescision
        mpoCommitteeDescision.setComments(UPDATED_COMMENTS);
        mpoCommitteeDescision.setStatus(UPDATED_STATUS);

        restMpoCommitteeDescisionMockMvc.perform(put("/api/mpoCommitteeDescisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoCommitteeDescision)))
                .andExpect(status().isOk());

        // Validate the MpoCommitteeDescision in the database
        List<MpoCommitteeDescision> mpoCommitteeDescisions = mpoCommitteeDescisionRepository.findAll();
        assertThat(mpoCommitteeDescisions).hasSize(databaseSizeBeforeUpdate);
        MpoCommitteeDescision testMpoCommitteeDescision = mpoCommitteeDescisions.get(mpoCommitteeDescisions.size() - 1);
        assertThat(testMpoCommitteeDescision.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testMpoCommitteeDescision.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteMpoCommitteeDescision() throws Exception {
        // Initialize the database
        mpoCommitteeDescisionRepository.saveAndFlush(mpoCommitteeDescision);

		int databaseSizeBeforeDelete = mpoCommitteeDescisionRepository.findAll().size();

        // Get the mpoCommitteeDescision
        restMpoCommitteeDescisionMockMvc.perform(delete("/api/mpoCommitteeDescisions/{id}", mpoCommitteeDescision.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoCommitteeDescision> mpoCommitteeDescisions = mpoCommitteeDescisionRepository.findAll();
        assertThat(mpoCommitteeDescisions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
