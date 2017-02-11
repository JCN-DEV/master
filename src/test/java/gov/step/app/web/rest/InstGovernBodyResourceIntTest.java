package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstGovernBody;
import gov.step.app.repository.InstGovernBodyRepository;
import gov.step.app.repository.search.InstGovernBodySearchRepository;

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
 * Test class for the InstGovernBodyResource REST controller.
 *
 * @see InstGovernBodyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstGovernBodyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_POSITION = "AAAAA";
    private static final String UPDATED_POSITION = "BBBBB";
    private static final String DEFAULT_MOBILE_NO = "AAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstGovernBodyRepository instGovernBodyRepository;

    @Inject
    private InstGovernBodySearchRepository instGovernBodySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstGovernBodyMockMvc;

    private InstGovernBody instGovernBody;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstGovernBodyResource instGovernBodyResource = new InstGovernBodyResource();
        ReflectionTestUtils.setField(instGovernBodyResource, "instGovernBodyRepository", instGovernBodyRepository);
        ReflectionTestUtils.setField(instGovernBodyResource, "instGovernBodySearchRepository", instGovernBodySearchRepository);
        this.restInstGovernBodyMockMvc = MockMvcBuilders.standaloneSetup(instGovernBodyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instGovernBody = new InstGovernBody();
        instGovernBody.setName(DEFAULT_NAME);
        instGovernBody.setPosition(DEFAULT_POSITION);
        instGovernBody.setMobileNo(DEFAULT_MOBILE_NO);
        instGovernBody.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstGovernBody() throws Exception {
        int databaseSizeBeforeCreate = instGovernBodyRepository.findAll().size();

        // Create the InstGovernBody

        restInstGovernBodyMockMvc.perform(post("/api/instGovernBodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovernBody)))
                .andExpect(status().isCreated());

        // Validate the InstGovernBody in the database
        List<InstGovernBody> instGovernBodys = instGovernBodyRepository.findAll();
        assertThat(instGovernBodys).hasSize(databaseSizeBeforeCreate + 1);
        InstGovernBody testInstGovernBody = instGovernBodys.get(instGovernBodys.size() - 1);
        assertThat(testInstGovernBody.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstGovernBody.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testInstGovernBody.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testInstGovernBody.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGovernBodyRepository.findAll().size();
        // set the field null
        instGovernBody.setName(null);

        // Create the InstGovernBody, which fails.

        restInstGovernBodyMockMvc.perform(post("/api/instGovernBodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovernBody)))
                .andExpect(status().isBadRequest());

        List<InstGovernBody> instGovernBodys = instGovernBodyRepository.findAll();
        assertThat(instGovernBodys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGovernBodyRepository.findAll().size();
        // set the field null
        instGovernBody.setPosition(null);

        // Create the InstGovernBody, which fails.

        restInstGovernBodyMockMvc.perform(post("/api/instGovernBodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovernBody)))
                .andExpect(status().isBadRequest());

        List<InstGovernBody> instGovernBodys = instGovernBodyRepository.findAll();
        assertThat(instGovernBodys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstGovernBodys() throws Exception {
        // Initialize the database
        instGovernBodyRepository.saveAndFlush(instGovernBody);

        // Get all the instGovernBodys
        restInstGovernBodyMockMvc.perform(get("/api/instGovernBodys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instGovernBody.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
                .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstGovernBody() throws Exception {
        // Initialize the database
        instGovernBodyRepository.saveAndFlush(instGovernBody);

        // Get the instGovernBody
        restInstGovernBodyMockMvc.perform(get("/api/instGovernBodys/{id}", instGovernBody.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instGovernBody.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstGovernBody() throws Exception {
        // Get the instGovernBody
        restInstGovernBodyMockMvc.perform(get("/api/instGovernBodys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstGovernBody() throws Exception {
        // Initialize the database
        instGovernBodyRepository.saveAndFlush(instGovernBody);

		int databaseSizeBeforeUpdate = instGovernBodyRepository.findAll().size();

        // Update the instGovernBody
        instGovernBody.setName(UPDATED_NAME);
        instGovernBody.setPosition(UPDATED_POSITION);
        instGovernBody.setMobileNo(UPDATED_MOBILE_NO);
        instGovernBody.setStatus(UPDATED_STATUS);

        restInstGovernBodyMockMvc.perform(put("/api/instGovernBodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovernBody)))
                .andExpect(status().isOk());

        // Validate the InstGovernBody in the database
        List<InstGovernBody> instGovernBodys = instGovernBodyRepository.findAll();
        assertThat(instGovernBodys).hasSize(databaseSizeBeforeUpdate);
        InstGovernBody testInstGovernBody = instGovernBodys.get(instGovernBodys.size() - 1);
        assertThat(testInstGovernBody.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstGovernBody.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testInstGovernBody.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testInstGovernBody.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstGovernBody() throws Exception {
        // Initialize the database
        instGovernBodyRepository.saveAndFlush(instGovernBody);

		int databaseSizeBeforeDelete = instGovernBodyRepository.findAll().size();

        // Get the instGovernBody
        restInstGovernBodyMockMvc.perform(delete("/api/instGovernBodys/{id}", instGovernBody.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstGovernBody> instGovernBodys = instGovernBodyRepository.findAll();
        assertThat(instGovernBodys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
