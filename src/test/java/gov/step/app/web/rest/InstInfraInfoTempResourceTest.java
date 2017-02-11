package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstInfraInfoTemp;
import gov.step.app.repository.InstInfraInfoTempRepository;
import gov.step.app.repository.search.InstInfraInfoTempSearchRepository;

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
 * Test class for the InstInfraInfoTempResource REST controller.
 *
 * @see InstInfraInfoTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstInfraInfoTempResourceTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstInfraInfoTempRepository instInfraInfoTempRepository;

    @Inject
    private InstInfraInfoTempSearchRepository instInfraInfoTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstInfraInfoTempMockMvc;

    private InstInfraInfoTemp instInfraInfoTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstInfraInfoTempResource instInfraInfoTempResource = new InstInfraInfoTempResource();
        ReflectionTestUtils.setField(instInfraInfoTempResource, "instInfraInfoTempRepository", instInfraInfoTempRepository);
        ReflectionTestUtils.setField(instInfraInfoTempResource, "instInfraInfoTempSearchRepository", instInfraInfoTempSearchRepository);
        this.restInstInfraInfoTempMockMvc = MockMvcBuilders.standaloneSetup(instInfraInfoTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instInfraInfoTemp = new InstInfraInfoTemp();
        instInfraInfoTemp.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstInfraInfoTemp() throws Exception {
        int databaseSizeBeforeCreate = instInfraInfoTempRepository.findAll().size();

        // Create the InstInfraInfoTemp

        restInstInfraInfoTempMockMvc.perform(post("/api/instInfraInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instInfraInfoTemp)))
                .andExpect(status().isCreated());

        // Validate the InstInfraInfoTemp in the database
        List<InstInfraInfoTemp> instInfraInfoTemps = instInfraInfoTempRepository.findAll();
        assertThat(instInfraInfoTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstInfraInfoTemp testInstInfraInfoTemp = instInfraInfoTemps.get(instInfraInfoTemps.size() - 1);
        assertThat(testInstInfraInfoTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstInfraInfoTemps() throws Exception {
        // Initialize the database
        instInfraInfoTempRepository.saveAndFlush(instInfraInfoTemp);

        // Get all the instInfraInfoTemps
        restInstInfraInfoTempMockMvc.perform(get("/api/instInfraInfoTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instInfraInfoTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstInfraInfoTemp() throws Exception {
        // Initialize the database
        instInfraInfoTempRepository.saveAndFlush(instInfraInfoTemp);

        // Get the instInfraInfoTemp
        restInstInfraInfoTempMockMvc.perform(get("/api/instInfraInfoTemps/{id}", instInfraInfoTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instInfraInfoTemp.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstInfraInfoTemp() throws Exception {
        // Get the instInfraInfoTemp
        restInstInfraInfoTempMockMvc.perform(get("/api/instInfraInfoTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstInfraInfoTemp() throws Exception {
        // Initialize the database
        instInfraInfoTempRepository.saveAndFlush(instInfraInfoTemp);

		int databaseSizeBeforeUpdate = instInfraInfoTempRepository.findAll().size();

        // Update the instInfraInfoTemp
        instInfraInfoTemp.setStatus(UPDATED_STATUS);

        restInstInfraInfoTempMockMvc.perform(put("/api/instInfraInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instInfraInfoTemp)))
                .andExpect(status().isOk());

        // Validate the InstInfraInfoTemp in the database
        List<InstInfraInfoTemp> instInfraInfoTemps = instInfraInfoTempRepository.findAll();
        assertThat(instInfraInfoTemps).hasSize(databaseSizeBeforeUpdate);
        InstInfraInfoTemp testInstInfraInfoTemp = instInfraInfoTemps.get(instInfraInfoTemps.size() - 1);
        assertThat(testInstInfraInfoTemp.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstInfraInfoTemp() throws Exception {
        // Initialize the database
        instInfraInfoTempRepository.saveAndFlush(instInfraInfoTemp);

		int databaseSizeBeforeDelete = instInfraInfoTempRepository.findAll().size();

        // Get the instInfraInfoTemp
        restInstInfraInfoTempMockMvc.perform(delete("/api/instInfraInfoTemps/{id}", instInfraInfoTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstInfraInfoTemp> instInfraInfoTemps = instInfraInfoTempRepository.findAll();
        assertThat(instInfraInfoTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
