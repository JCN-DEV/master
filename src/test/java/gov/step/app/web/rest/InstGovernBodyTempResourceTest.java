package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstGovernBodyTemp;
import gov.step.app.repository.InstGovernBodyTempRepository;
import gov.step.app.repository.search.InstGovernBodyTempSearchRepository;

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
 * Test class for the InstGovernBodyTempResource REST controller.
 *
 * @see InstGovernBodyTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstGovernBodyTempResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_POSITION = "AAAAA";
    private static final String UPDATED_POSITION = "BBBBB";
    private static final String DEFAULT_MOBILE_NO = "AAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstGovernBodyTempRepository instGovernBodyTempRepository;

    @Inject
    private InstGovernBodyTempSearchRepository instGovernBodyTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstGovernBodyTempMockMvc;

    private InstGovernBodyTemp instGovernBodyTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstGovernBodyTempResource instGovernBodyTempResource = new InstGovernBodyTempResource();
        ReflectionTestUtils.setField(instGovernBodyTempResource, "instGovernBodyTempRepository", instGovernBodyTempRepository);
        ReflectionTestUtils.setField(instGovernBodyTempResource, "instGovernBodyTempSearchRepository", instGovernBodyTempSearchRepository);
        this.restInstGovernBodyTempMockMvc = MockMvcBuilders.standaloneSetup(instGovernBodyTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instGovernBodyTemp = new InstGovernBodyTemp();
        instGovernBodyTemp.setName(DEFAULT_NAME);
        instGovernBodyTemp.setPosition(DEFAULT_POSITION);
        instGovernBodyTemp.setMobileNo(DEFAULT_MOBILE_NO);
        instGovernBodyTemp.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstGovernBodyTemp() throws Exception {
        int databaseSizeBeforeCreate = instGovernBodyTempRepository.findAll().size();

        // Create the InstGovernBodyTemp

        restInstGovernBodyTempMockMvc.perform(post("/api/instGovernBodyTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovernBodyTemp)))
                .andExpect(status().isCreated());

        // Validate the InstGovernBodyTemp in the database
        List<InstGovernBodyTemp> instGovernBodyTemps = instGovernBodyTempRepository.findAll();
        assertThat(instGovernBodyTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstGovernBodyTemp testInstGovernBodyTemp = instGovernBodyTemps.get(instGovernBodyTemps.size() - 1);
        assertThat(testInstGovernBodyTemp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstGovernBodyTemp.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testInstGovernBodyTemp.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testInstGovernBodyTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGovernBodyTempRepository.findAll().size();
        // set the field null
        instGovernBodyTemp.setName(null);

        // Create the InstGovernBodyTemp, which fails.

        restInstGovernBodyTempMockMvc.perform(post("/api/instGovernBodyTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovernBodyTemp)))
                .andExpect(status().isBadRequest());

        List<InstGovernBodyTemp> instGovernBodyTemps = instGovernBodyTempRepository.findAll();
        assertThat(instGovernBodyTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGovernBodyTempRepository.findAll().size();
        // set the field null
        instGovernBodyTemp.setPosition(null);

        // Create the InstGovernBodyTemp, which fails.

        restInstGovernBodyTempMockMvc.perform(post("/api/instGovernBodyTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovernBodyTemp)))
                .andExpect(status().isBadRequest());

        List<InstGovernBodyTemp> instGovernBodyTemps = instGovernBodyTempRepository.findAll();
        assertThat(instGovernBodyTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstGovernBodyTemps() throws Exception {
        // Initialize the database
        instGovernBodyTempRepository.saveAndFlush(instGovernBodyTemp);

        // Get all the instGovernBodyTemps
        restInstGovernBodyTempMockMvc.perform(get("/api/instGovernBodyTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instGovernBodyTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
                .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstGovernBodyTemp() throws Exception {
        // Initialize the database
        instGovernBodyTempRepository.saveAndFlush(instGovernBodyTemp);

        // Get the instGovernBodyTemp
        restInstGovernBodyTempMockMvc.perform(get("/api/instGovernBodyTemps/{id}", instGovernBodyTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instGovernBodyTemp.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstGovernBodyTemp() throws Exception {
        // Get the instGovernBodyTemp
        restInstGovernBodyTempMockMvc.perform(get("/api/instGovernBodyTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstGovernBodyTemp() throws Exception {
        // Initialize the database
        instGovernBodyTempRepository.saveAndFlush(instGovernBodyTemp);

		int databaseSizeBeforeUpdate = instGovernBodyTempRepository.findAll().size();

        // Update the instGovernBodyTemp
        instGovernBodyTemp.setName(UPDATED_NAME);
        instGovernBodyTemp.setPosition(UPDATED_POSITION);
        instGovernBodyTemp.setMobileNo(UPDATED_MOBILE_NO);
        instGovernBodyTemp.setStatus(UPDATED_STATUS);

        restInstGovernBodyTempMockMvc.perform(put("/api/instGovernBodyTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGovernBodyTemp)))
                .andExpect(status().isOk());

        // Validate the InstGovernBodyTemp in the database
        List<InstGovernBodyTemp> instGovernBodyTemps = instGovernBodyTempRepository.findAll();
        assertThat(instGovernBodyTemps).hasSize(databaseSizeBeforeUpdate);
        InstGovernBodyTemp testInstGovernBodyTemp = instGovernBodyTemps.get(instGovernBodyTemps.size() - 1);
        assertThat(testInstGovernBodyTemp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstGovernBodyTemp.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testInstGovernBodyTemp.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testInstGovernBodyTemp.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstGovernBodyTemp() throws Exception {
        // Initialize the database
        instGovernBodyTempRepository.saveAndFlush(instGovernBodyTemp);

		int databaseSizeBeforeDelete = instGovernBodyTempRepository.findAll().size();

        // Get the instGovernBodyTemp
        restInstGovernBodyTempMockMvc.perform(delete("/api/instGovernBodyTemps/{id}", instGovernBodyTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstGovernBodyTemp> instGovernBodyTemps = instGovernBodyTempRepository.findAll();
        assertThat(instGovernBodyTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
