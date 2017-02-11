package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SmsServiceAssign;
import gov.step.app.repository.SmsServiceAssignRepository;
import gov.step.app.repository.search.SmsServiceAssignSearchRepository;

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
 * Test class for the SmsServiceAssignResource REST controller.
 *
 * @see SmsServiceAssignResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SmsServiceAssignResourceIntTest {


    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    @Inject
    private SmsServiceAssignRepository smsServiceAssignRepository;

    @Inject
    private SmsServiceAssignSearchRepository smsServiceAssignSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSmsServiceAssignMockMvc;

    private SmsServiceAssign smsServiceAssign;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsServiceAssignResource smsServiceAssignResource = new SmsServiceAssignResource();
        ReflectionTestUtils.setField(smsServiceAssignResource, "smsServiceAssignSearchRepository", smsServiceAssignSearchRepository);
        ReflectionTestUtils.setField(smsServiceAssignResource, "smsServiceAssignRepository", smsServiceAssignRepository);
        this.restSmsServiceAssignMockMvc = MockMvcBuilders.standaloneSetup(smsServiceAssignResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        smsServiceAssign = new SmsServiceAssign();
        smsServiceAssign.setActiveStatus(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void createSmsServiceAssign() throws Exception {
        int databaseSizeBeforeCreate = smsServiceAssignRepository.findAll().size();

        // Create the SmsServiceAssign

        restSmsServiceAssignMockMvc.perform(post("/api/smsServiceAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceAssign)))
                .andExpect(status().isCreated());

        // Validate the SmsServiceAssign in the database
        List<SmsServiceAssign> smsServiceAssigns = smsServiceAssignRepository.findAll();
        assertThat(smsServiceAssigns).hasSize(databaseSizeBeforeCreate + 1);
        SmsServiceAssign testSmsServiceAssign = smsServiceAssigns.get(smsServiceAssigns.size() - 1);
        assertThat(testSmsServiceAssign.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void getAllSmsServiceAssigns() throws Exception {
        // Initialize the database
        smsServiceAssignRepository.saveAndFlush(smsServiceAssign);

        // Get all the smsServiceAssigns
        restSmsServiceAssignMockMvc.perform(get("/api/smsServiceAssigns?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(smsServiceAssign.getId().intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getSmsServiceAssign() throws Exception {
        // Initialize the database
        smsServiceAssignRepository.saveAndFlush(smsServiceAssign);

        // Get the smsServiceAssign
        restSmsServiceAssignMockMvc.perform(get("/api/smsServiceAssigns/{id}", smsServiceAssign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(smsServiceAssign.getId().intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsServiceAssign() throws Exception {
        // Get the smsServiceAssign
        restSmsServiceAssignMockMvc.perform(get("/api/smsServiceAssigns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsServiceAssign() throws Exception {
        // Initialize the database
        smsServiceAssignRepository.saveAndFlush(smsServiceAssign);

		int databaseSizeBeforeUpdate = smsServiceAssignRepository.findAll().size();

        // Update the smsServiceAssign
        smsServiceAssign.setActiveStatus(UPDATED_ACTIVE_STATUS);

        restSmsServiceAssignMockMvc.perform(put("/api/smsServiceAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceAssign)))
                .andExpect(status().isOk());

        // Validate the SmsServiceAssign in the database
        List<SmsServiceAssign> smsServiceAssigns = smsServiceAssignRepository.findAll();
        assertThat(smsServiceAssigns).hasSize(databaseSizeBeforeUpdate);
        SmsServiceAssign testSmsServiceAssign = smsServiceAssigns.get(smsServiceAssigns.size() - 1);
        assertThat(testSmsServiceAssign.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void deleteSmsServiceAssign() throws Exception {
        // Initialize the database
        smsServiceAssignRepository.saveAndFlush(smsServiceAssign);

		int databaseSizeBeforeDelete = smsServiceAssignRepository.findAll().size();

        // Get the smsServiceAssign
        restSmsServiceAssignMockMvc.perform(delete("/api/smsServiceAssigns/{id}", smsServiceAssign.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsServiceAssign> smsServiceAssigns = smsServiceAssignRepository.findAll();
        assertThat(smsServiceAssigns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
