package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SmsGateway;
import gov.step.app.repository.SmsGatewayRepository;
import gov.step.app.repository.search.SmsGatewaySearchRepository;

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
 * Test class for the SmsGatewayResource REST controller.
 *
 * @see SmsGatewayResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SmsGatewayResourceTest {

    private static final String DEFAULT_USER_ID = "AAAAA";
    private static final String UPDATED_USER_ID = "BBBBB";
    private static final String DEFAULT_PHONE_NO = "AAAAA";
    private static final String UPDATED_PHONE_NO = "BBBBB";
    private static final String DEFAULT_MSG = "AAAAA";
    private static final String UPDATED_MSG = "BBBBB";

    @Inject
    private SmsGatewayRepository smsGatewayRepository;

    @Inject
    private SmsGatewaySearchRepository smsGatewaySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSmsGatewayMockMvc;

    private SmsGateway smsGateway;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsGatewayResource smsGatewayResource = new SmsGatewayResource();
        ReflectionTestUtils.setField(smsGatewayResource, "smsGatewayRepository", smsGatewayRepository);
        ReflectionTestUtils.setField(smsGatewayResource, "smsGatewaySearchRepository", smsGatewaySearchRepository);
        this.restSmsGatewayMockMvc = MockMvcBuilders.standaloneSetup(smsGatewayResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        smsGateway = new SmsGateway();
        smsGateway.setUserId(DEFAULT_USER_ID);
        smsGateway.setPhoneNo(DEFAULT_PHONE_NO);
        smsGateway.setMsg(DEFAULT_MSG);
    }

    @Test
    @Transactional
    public void createSmsGateway() throws Exception {
        int databaseSizeBeforeCreate = smsGatewayRepository.findAll().size();

        // Create the SmsGateway

        restSmsGatewayMockMvc.perform(post("/api/smsGateways")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsGateway)))
                .andExpect(status().isCreated());

        // Validate the SmsGateway in the database
        List<SmsGateway> smsGateways = smsGatewayRepository.findAll();
        assertThat(smsGateways).hasSize(databaseSizeBeforeCreate + 1);
        SmsGateway testSmsGateway = smsGateways.get(smsGateways.size() - 1);
        assertThat(testSmsGateway.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSmsGateway.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testSmsGateway.getMsg()).isEqualTo(DEFAULT_MSG);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsGatewayRepository.findAll().size();
        // set the field null
        smsGateway.setUserId(null);

        // Create the SmsGateway, which fails.

        restSmsGatewayMockMvc.perform(post("/api/smsGateways")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsGateway)))
                .andExpect(status().isBadRequest());

        List<SmsGateway> smsGateways = smsGatewayRepository.findAll();
        assertThat(smsGateways).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsGatewayRepository.findAll().size();
        // set the field null
        smsGateway.setPhoneNo(null);

        // Create the SmsGateway, which fails.

        restSmsGatewayMockMvc.perform(post("/api/smsGateways")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsGateway)))
                .andExpect(status().isBadRequest());

        List<SmsGateway> smsGateways = smsGatewayRepository.findAll();
        assertThat(smsGateways).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMsgIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsGatewayRepository.findAll().size();
        // set the field null
        smsGateway.setMsg(null);

        // Create the SmsGateway, which fails.

        restSmsGatewayMockMvc.perform(post("/api/smsGateways")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsGateway)))
                .andExpect(status().isBadRequest());

        List<SmsGateway> smsGateways = smsGatewayRepository.findAll();
        assertThat(smsGateways).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSmsGateways() throws Exception {
        // Initialize the database
        smsGatewayRepository.saveAndFlush(smsGateway);

        // Get all the smsGateways
        restSmsGatewayMockMvc.perform(get("/api/smsGateways"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(smsGateway.getId().intValue())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
                .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO.toString())))
                .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG.toString())));
    }

    @Test
    @Transactional
    public void getSmsGateway() throws Exception {
        // Initialize the database
        smsGatewayRepository.saveAndFlush(smsGateway);

        // Get the smsGateway
        restSmsGatewayMockMvc.perform(get("/api/smsGateways/{id}", smsGateway.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(smsGateway.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.phoneNo").value(DEFAULT_PHONE_NO.toString()))
            .andExpect(jsonPath("$.msg").value(DEFAULT_MSG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsGateway() throws Exception {
        // Get the smsGateway
        restSmsGatewayMockMvc.perform(get("/api/smsGateways/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsGateway() throws Exception {
        // Initialize the database
        smsGatewayRepository.saveAndFlush(smsGateway);

		int databaseSizeBeforeUpdate = smsGatewayRepository.findAll().size();

        // Update the smsGateway
        smsGateway.setUserId(UPDATED_USER_ID);
        smsGateway.setPhoneNo(UPDATED_PHONE_NO);
        smsGateway.setMsg(UPDATED_MSG);

        restSmsGatewayMockMvc.perform(put("/api/smsGateways")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsGateway)))
                .andExpect(status().isOk());

        // Validate the SmsGateway in the database
        List<SmsGateway> smsGateways = smsGatewayRepository.findAll();
        assertThat(smsGateways).hasSize(databaseSizeBeforeUpdate);
        SmsGateway testSmsGateway = smsGateways.get(smsGateways.size() - 1);
        assertThat(testSmsGateway.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSmsGateway.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testSmsGateway.getMsg()).isEqualTo(UPDATED_MSG);
    }

    @Test
    @Transactional
    public void deleteSmsGateway() throws Exception {
        // Initialize the database
        smsGatewayRepository.saveAndFlush(smsGateway);

		int databaseSizeBeforeDelete = smsGatewayRepository.findAll().size();

        // Get the smsGateway
        restSmsGatewayMockMvc.perform(delete("/api/smsGateways/{id}", smsGateway.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsGateway> smsGateways = smsGatewayRepository.findAll();
        assertThat(smsGateways).hasSize(databaseSizeBeforeDelete - 1);
    }
}
