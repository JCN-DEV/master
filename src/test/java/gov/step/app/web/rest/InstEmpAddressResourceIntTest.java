package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmpAddress;
import gov.step.app.repository.InstEmpAddressRepository;
import gov.step.app.repository.search.InstEmpAddressSearchRepository;

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
 * Test class for the InstEmpAddressResource REST controller.
 *
 * @see InstEmpAddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmpAddressResourceIntTest {

    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private InstEmpAddressRepository instEmpAddressRepository;

    @Inject
    private InstEmpAddressSearchRepository instEmpAddressSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmpAddressMockMvc;

    private InstEmpAddress instEmpAddress;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmpAddressResource instEmpAddressResource = new InstEmpAddressResource();
        ReflectionTestUtils.setField(instEmpAddressResource, "instEmpAddressRepository", instEmpAddressRepository);
        ReflectionTestUtils.setField(instEmpAddressResource, "instEmpAddressSearchRepository", instEmpAddressSearchRepository);
        this.restInstEmpAddressMockMvc = MockMvcBuilders.standaloneSetup(instEmpAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmpAddress = new InstEmpAddress();
       // instEmpAddress.setAddress(DEFAULT_ADDRESS);
        instEmpAddress.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstEmpAddress() throws Exception {
        int databaseSizeBeforeCreate = instEmpAddressRepository.findAll().size();

        // Create the InstEmpAddress

        restInstEmpAddressMockMvc.perform(post("/api/instEmpAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpAddress)))
                .andExpect(status().isCreated());

        // Validate the InstEmpAddress in the database
        List<InstEmpAddress> instEmpAddresss = instEmpAddressRepository.findAll();
        assertThat(instEmpAddresss).hasSize(databaseSizeBeforeCreate + 1);
        InstEmpAddress testInstEmpAddress = instEmpAddresss.get(instEmpAddresss.size() - 1);
        //assertThat(testInstEmpAddress.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testInstEmpAddress.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstEmpAddresss() throws Exception {
        // Initialize the database
        instEmpAddressRepository.saveAndFlush(instEmpAddress);

        // Get all the instEmpAddresss
        restInstEmpAddressMockMvc.perform(get("/api/instEmpAddresss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmpAddress.getId().intValue())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getInstEmpAddress() throws Exception {
        // Initialize the database
        instEmpAddressRepository.saveAndFlush(instEmpAddress);

        // Get the instEmpAddress
        restInstEmpAddressMockMvc.perform(get("/api/instEmpAddresss/{id}", instEmpAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmpAddress.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmpAddress() throws Exception {
        // Get the instEmpAddress
        restInstEmpAddressMockMvc.perform(get("/api/instEmpAddresss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmpAddress() throws Exception {
        // Initialize the database
        instEmpAddressRepository.saveAndFlush(instEmpAddress);

		int databaseSizeBeforeUpdate = instEmpAddressRepository.findAll().size();

        // Update the instEmpAddress
       // instEmpAddress.setAddress(UPDATED_ADDRESS);
        instEmpAddress.setStatus(UPDATED_STATUS);

        restInstEmpAddressMockMvc.perform(put("/api/instEmpAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpAddress)))
                .andExpect(status().isOk());

        // Validate the InstEmpAddress in the database
        List<InstEmpAddress> instEmpAddresss = instEmpAddressRepository.findAll();
        assertThat(instEmpAddresss).hasSize(databaseSizeBeforeUpdate);
        InstEmpAddress testInstEmpAddress = instEmpAddresss.get(instEmpAddresss.size() - 1);
        //assertThat(testInstEmpAddress.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testInstEmpAddress.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstEmpAddress() throws Exception {
        // Initialize the database
        instEmpAddressRepository.saveAndFlush(instEmpAddress);

		int databaseSizeBeforeDelete = instEmpAddressRepository.findAll().size();

        // Get the instEmpAddress
        restInstEmpAddressMockMvc.perform(delete("/api/instEmpAddresss/{id}", instEmpAddress.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmpAddress> instEmpAddresss = instEmpAddressRepository.findAll();
        assertThat(instEmpAddresss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
