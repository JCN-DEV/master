package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstPlayGroundInfoTemp;
import gov.step.app.repository.InstPlayGroundInfoTempRepository;
import gov.step.app.repository.search.InstPlayGroundInfoTempSearchRepository;

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
 * Test class for the InstPlayGroundInfoTempResource REST controller.
 *
 * @see InstPlayGroundInfoTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstPlayGroundInfoTempResourceTest {

    private static final String DEFAULT_PLAYGROUND_NO = "AAAAA";
    private static final String UPDATED_PLAYGROUND_NO = "BBBBB";
    private static final String DEFAULT_AREA = "AAAAA";
    private static final String UPDATED_AREA = "BBBBB";

    @Inject
    private InstPlayGroundInfoTempRepository instPlayGroundInfoTempRepository;

    @Inject
    private InstPlayGroundInfoTempSearchRepository instPlayGroundInfoTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstPlayGroundInfoTempMockMvc;

    private InstPlayGroundInfoTemp instPlayGroundInfoTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstPlayGroundInfoTempResource instPlayGroundInfoTempResource = new InstPlayGroundInfoTempResource();
        ReflectionTestUtils.setField(instPlayGroundInfoTempResource, "instPlayGroundInfoTempRepository", instPlayGroundInfoTempRepository);
        ReflectionTestUtils.setField(instPlayGroundInfoTempResource, "instPlayGroundInfoTempSearchRepository", instPlayGroundInfoTempSearchRepository);
        this.restInstPlayGroundInfoTempMockMvc = MockMvcBuilders.standaloneSetup(instPlayGroundInfoTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instPlayGroundInfoTemp = new InstPlayGroundInfoTemp();
        instPlayGroundInfoTemp.setPlaygroundNo(DEFAULT_PLAYGROUND_NO);
        instPlayGroundInfoTemp.setArea(DEFAULT_AREA);
    }

    @Test
    @Transactional
    public void createInstPlayGroundInfoTemp() throws Exception {
        int databaseSizeBeforeCreate = instPlayGroundInfoTempRepository.findAll().size();

        // Create the InstPlayGroundInfoTemp

        restInstPlayGroundInfoTempMockMvc.perform(post("/api/instPlayGroundInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instPlayGroundInfoTemp)))
                .andExpect(status().isCreated());

        // Validate the InstPlayGroundInfoTemp in the database
        List<InstPlayGroundInfoTemp> instPlayGroundInfoTemps = instPlayGroundInfoTempRepository.findAll();
        assertThat(instPlayGroundInfoTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstPlayGroundInfoTemp testInstPlayGroundInfoTemp = instPlayGroundInfoTemps.get(instPlayGroundInfoTemps.size() - 1);
        assertThat(testInstPlayGroundInfoTemp.getPlaygroundNo()).isEqualTo(DEFAULT_PLAYGROUND_NO);
        assertThat(testInstPlayGroundInfoTemp.getArea()).isEqualTo(DEFAULT_AREA);
    }

    @Test
    @Transactional
    public void getAllInstPlayGroundInfoTemps() throws Exception {
        // Initialize the database
        instPlayGroundInfoTempRepository.saveAndFlush(instPlayGroundInfoTemp);

        // Get all the instPlayGroundInfoTemps
        restInstPlayGroundInfoTempMockMvc.perform(get("/api/instPlayGroundInfoTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instPlayGroundInfoTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].playgroundNo").value(hasItem(DEFAULT_PLAYGROUND_NO.toString())))
                .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())));
    }

    @Test
    @Transactional
    public void getInstPlayGroundInfoTemp() throws Exception {
        // Initialize the database
        instPlayGroundInfoTempRepository.saveAndFlush(instPlayGroundInfoTemp);

        // Get the instPlayGroundInfoTemp
        restInstPlayGroundInfoTempMockMvc.perform(get("/api/instPlayGroundInfoTemps/{id}", instPlayGroundInfoTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instPlayGroundInfoTemp.getId().intValue()))
            .andExpect(jsonPath("$.playgroundNo").value(DEFAULT_PLAYGROUND_NO.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstPlayGroundInfoTemp() throws Exception {
        // Get the instPlayGroundInfoTemp
        restInstPlayGroundInfoTempMockMvc.perform(get("/api/instPlayGroundInfoTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstPlayGroundInfoTemp() throws Exception {
        // Initialize the database
        instPlayGroundInfoTempRepository.saveAndFlush(instPlayGroundInfoTemp);

		int databaseSizeBeforeUpdate = instPlayGroundInfoTempRepository.findAll().size();

        // Update the instPlayGroundInfoTemp
        instPlayGroundInfoTemp.setPlaygroundNo(UPDATED_PLAYGROUND_NO);
        instPlayGroundInfoTemp.setArea(UPDATED_AREA);

        restInstPlayGroundInfoTempMockMvc.perform(put("/api/instPlayGroundInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instPlayGroundInfoTemp)))
                .andExpect(status().isOk());

        // Validate the InstPlayGroundInfoTemp in the database
        List<InstPlayGroundInfoTemp> instPlayGroundInfoTemps = instPlayGroundInfoTempRepository.findAll();
        assertThat(instPlayGroundInfoTemps).hasSize(databaseSizeBeforeUpdate);
        InstPlayGroundInfoTemp testInstPlayGroundInfoTemp = instPlayGroundInfoTemps.get(instPlayGroundInfoTemps.size() - 1);
        assertThat(testInstPlayGroundInfoTemp.getPlaygroundNo()).isEqualTo(UPDATED_PLAYGROUND_NO);
        assertThat(testInstPlayGroundInfoTemp.getArea()).isEqualTo(UPDATED_AREA);
    }

    @Test
    @Transactional
    public void deleteInstPlayGroundInfoTemp() throws Exception {
        // Initialize the database
        instPlayGroundInfoTempRepository.saveAndFlush(instPlayGroundInfoTemp);

		int databaseSizeBeforeDelete = instPlayGroundInfoTempRepository.findAll().size();

        // Get the instPlayGroundInfoTemp
        restInstPlayGroundInfoTempMockMvc.perform(delete("/api/instPlayGroundInfoTemps/{id}", instPlayGroundInfoTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstPlayGroundInfoTemp> instPlayGroundInfoTemps = instPlayGroundInfoTempRepository.findAll();
        assertThat(instPlayGroundInfoTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
