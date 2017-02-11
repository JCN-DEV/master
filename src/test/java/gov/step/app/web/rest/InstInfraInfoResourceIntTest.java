package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstInfraInfo;
import gov.step.app.repository.InstInfraInfoRepository;
import gov.step.app.repository.search.InstInfraInfoSearchRepository;

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
 * Test class for the InstInfraInfoResource REST controller.
 *
 * @see InstInfraInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstInfraInfoResourceIntTest {


    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstInfraInfoRepository instInfraInfoRepository;

    @Inject
    private InstInfraInfoSearchRepository instInfraInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstInfraInfoMockMvc;

    private InstInfraInfo instInfraInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstInfraInfoResource instInfraInfoResource = new InstInfraInfoResource();
        ReflectionTestUtils.setField(instInfraInfoResource, "instInfraInfoRepository", instInfraInfoRepository);
        ReflectionTestUtils.setField(instInfraInfoResource, "instInfraInfoSearchRepository", instInfraInfoSearchRepository);
        this.restInstInfraInfoMockMvc = MockMvcBuilders.standaloneSetup(instInfraInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instInfraInfo = new InstInfraInfo();
        instInfraInfo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstInfraInfo() throws Exception {
        int databaseSizeBeforeCreate = instInfraInfoRepository.findAll().size();

        // Create the InstInfraInfo

        restInstInfraInfoMockMvc.perform(post("/api/instInfraInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instInfraInfo)))
                .andExpect(status().isCreated());

        // Validate the InstInfraInfo in the database
        List<InstInfraInfo> instInfraInfos = instInfraInfoRepository.findAll();
        assertThat(instInfraInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstInfraInfo testInstInfraInfo = instInfraInfos.get(instInfraInfos.size() - 1);
        assertThat(testInstInfraInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstInfraInfos() throws Exception {
        // Initialize the database
        instInfraInfoRepository.saveAndFlush(instInfraInfo);

        // Get all the instInfraInfos
        restInstInfraInfoMockMvc.perform(get("/api/instInfraInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instInfraInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstInfraInfo() throws Exception {
        // Initialize the database
        instInfraInfoRepository.saveAndFlush(instInfraInfo);

        // Get the instInfraInfo
        restInstInfraInfoMockMvc.perform(get("/api/instInfraInfos/{id}", instInfraInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instInfraInfo.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstInfraInfo() throws Exception {
        // Get the instInfraInfo
        restInstInfraInfoMockMvc.perform(get("/api/instInfraInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstInfraInfo() throws Exception {
        // Initialize the database
        instInfraInfoRepository.saveAndFlush(instInfraInfo);

		int databaseSizeBeforeUpdate = instInfraInfoRepository.findAll().size();

        // Update the instInfraInfo
        instInfraInfo.setStatus(UPDATED_STATUS);

        restInstInfraInfoMockMvc.perform(put("/api/instInfraInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instInfraInfo)))
                .andExpect(status().isOk());

        // Validate the InstInfraInfo in the database
        List<InstInfraInfo> instInfraInfos = instInfraInfoRepository.findAll();
        assertThat(instInfraInfos).hasSize(databaseSizeBeforeUpdate);
        InstInfraInfo testInstInfraInfo = instInfraInfos.get(instInfraInfos.size() - 1);
        assertThat(testInstInfraInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstInfraInfo() throws Exception {
        // Initialize the database
        instInfraInfoRepository.saveAndFlush(instInfraInfo);

		int databaseSizeBeforeDelete = instInfraInfoRepository.findAll().size();

        // Get the instInfraInfo
        restInstInfraInfoMockMvc.perform(delete("/api/instInfraInfos/{id}", instInfraInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstInfraInfo> instInfraInfos = instInfraInfoRepository.findAll();
        assertThat(instInfraInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
