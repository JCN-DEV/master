package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstituteGovernBody;
import gov.step.app.repository.InstituteGovernBodyRepository;
import gov.step.app.repository.search.InstituteGovernBodySearchRepository;

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
 * Test class for the InstituteGovernBodyResource REST controller.
 *
 * @see InstituteGovernBodyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstituteGovernBodyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_POSITION = "AAAAA";
    private static final String UPDATED_POSITION = "BBBBB";
    private static final String DEFAULT_MOBILE_NO = "AAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstituteGovernBodyRepository instituteGovernBodyRepository;

    @Inject
    private InstituteGovernBodySearchRepository instituteGovernBodySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstituteGovernBodyMockMvc;

    private InstituteGovernBody instituteGovernBody;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteGovernBodyResource instituteGovernBodyResource = new InstituteGovernBodyResource();
        ReflectionTestUtils.setField(instituteGovernBodyResource, "instituteGovernBodyRepository", instituteGovernBodyRepository);
        ReflectionTestUtils.setField(instituteGovernBodyResource, "instituteGovernBodySearchRepository", instituteGovernBodySearchRepository);
        this.restInstituteGovernBodyMockMvc = MockMvcBuilders.standaloneSetup(instituteGovernBodyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instituteGovernBody = new InstituteGovernBody();
        instituteGovernBody.setName(DEFAULT_NAME);
        instituteGovernBody.setPosition(DEFAULT_POSITION);
        instituteGovernBody.setMobileNo(DEFAULT_MOBILE_NO);
        instituteGovernBody.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstituteGovernBody() throws Exception {
        int databaseSizeBeforeCreate = instituteGovernBodyRepository.findAll().size();

        // Create the InstituteGovernBody

        restInstituteGovernBodyMockMvc.perform(post("/api/instituteGovernBodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteGovernBody)))
                .andExpect(status().isCreated());

        // Validate the InstituteGovernBody in the database
        List<InstituteGovernBody> instituteGovernBodys = instituteGovernBodyRepository.findAll();
        assertThat(instituteGovernBodys).hasSize(databaseSizeBeforeCreate + 1);
        InstituteGovernBody testInstituteGovernBody = instituteGovernBodys.get(instituteGovernBodys.size() - 1);
        assertThat(testInstituteGovernBody.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstituteGovernBody.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testInstituteGovernBody.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testInstituteGovernBody.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteGovernBodyRepository.findAll().size();
        // set the field null
        instituteGovernBody.setName(null);

        // Create the InstituteGovernBody, which fails.

        restInstituteGovernBodyMockMvc.perform(post("/api/instituteGovernBodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteGovernBody)))
                .andExpect(status().isBadRequest());

        List<InstituteGovernBody> instituteGovernBodys = instituteGovernBodyRepository.findAll();
        assertThat(instituteGovernBodys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteGovernBodyRepository.findAll().size();
        // set the field null
        instituteGovernBody.setPosition(null);

        // Create the InstituteGovernBody, which fails.

        restInstituteGovernBodyMockMvc.perform(post("/api/instituteGovernBodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteGovernBody)))
                .andExpect(status().isBadRequest());

        List<InstituteGovernBody> instituteGovernBodys = instituteGovernBodyRepository.findAll();
        assertThat(instituteGovernBodys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstituteGovernBodys() throws Exception {
        // Initialize the database
        instituteGovernBodyRepository.saveAndFlush(instituteGovernBody);

        // Get all the instituteGovernBodys
        restInstituteGovernBodyMockMvc.perform(get("/api/instituteGovernBodys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instituteGovernBody.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
                .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstituteGovernBody() throws Exception {
        // Initialize the database
        instituteGovernBodyRepository.saveAndFlush(instituteGovernBody);

        // Get the instituteGovernBody
        restInstituteGovernBodyMockMvc.perform(get("/api/instituteGovernBodys/{id}", instituteGovernBody.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instituteGovernBody.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstituteGovernBody() throws Exception {
        // Get the instituteGovernBody
        restInstituteGovernBodyMockMvc.perform(get("/api/instituteGovernBodys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituteGovernBody() throws Exception {
        // Initialize the database
        instituteGovernBodyRepository.saveAndFlush(instituteGovernBody);

		int databaseSizeBeforeUpdate = instituteGovernBodyRepository.findAll().size();

        // Update the instituteGovernBody
        instituteGovernBody.setName(UPDATED_NAME);
        instituteGovernBody.setPosition(UPDATED_POSITION);
        instituteGovernBody.setMobileNo(UPDATED_MOBILE_NO);
        instituteGovernBody.setStatus(UPDATED_STATUS);

        restInstituteGovernBodyMockMvc.perform(put("/api/instituteGovernBodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instituteGovernBody)))
                .andExpect(status().isOk());

        // Validate the InstituteGovernBody in the database
        List<InstituteGovernBody> instituteGovernBodys = instituteGovernBodyRepository.findAll();
        assertThat(instituteGovernBodys).hasSize(databaseSizeBeforeUpdate);
        InstituteGovernBody testInstituteGovernBody = instituteGovernBodys.get(instituteGovernBodys.size() - 1);
        assertThat(testInstituteGovernBody.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstituteGovernBody.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testInstituteGovernBody.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testInstituteGovernBody.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstituteGovernBody() throws Exception {
        // Initialize the database
        instituteGovernBodyRepository.saveAndFlush(instituteGovernBody);

		int databaseSizeBeforeDelete = instituteGovernBodyRepository.findAll().size();

        // Get the instituteGovernBody
        restInstituteGovernBodyMockMvc.perform(delete("/api/instituteGovernBodys/{id}", instituteGovernBody.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstituteGovernBody> instituteGovernBodys = instituteGovernBodyRepository.findAll();
        assertThat(instituteGovernBodys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
