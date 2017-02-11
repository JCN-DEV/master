package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoTrade;
import gov.step.app.repository.MpoTradeRepository;
import gov.step.app.repository.search.MpoTradeSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MpoTradeResource REST controller.
 *
 * @see MpoTradeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoTradeResourceIntTest {


    private static final LocalDate DEFAULT_CRATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CRATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private MpoTradeRepository mpoTradeRepository;

    @Inject
    private MpoTradeSearchRepository mpoTradeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoTradeMockMvc;

    private MpoTrade mpoTrade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoTradeResource mpoTradeResource = new MpoTradeResource();
        ReflectionTestUtils.setField(mpoTradeResource, "mpoTradeRepository", mpoTradeRepository);
        ReflectionTestUtils.setField(mpoTradeResource, "mpoTradeSearchRepository", mpoTradeSearchRepository);
        this.restMpoTradeMockMvc = MockMvcBuilders.standaloneSetup(mpoTradeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoTrade = new MpoTrade();
        mpoTrade.setCratedDate(DEFAULT_CRATED_DATE);
        mpoTrade.setUpdateDate(DEFAULT_UPDATE_DATE);
        mpoTrade.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createMpoTrade() throws Exception {
        int databaseSizeBeforeCreate = mpoTradeRepository.findAll().size();

        // Create the MpoTrade

        restMpoTradeMockMvc.perform(post("/api/mpoTrades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoTrade)))
                .andExpect(status().isCreated());

        // Validate the MpoTrade in the database
        List<MpoTrade> mpoTrades = mpoTradeRepository.findAll();
        assertThat(mpoTrades).hasSize(databaseSizeBeforeCreate + 1);
        MpoTrade testMpoTrade = mpoTrades.get(mpoTrades.size() - 1);
        assertThat(testMpoTrade.getCratedDate()).isEqualTo(DEFAULT_CRATED_DATE);
        assertThat(testMpoTrade.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testMpoTrade.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMpoTrades() throws Exception {
        // Initialize the database
        mpoTradeRepository.saveAndFlush(mpoTrade);

        // Get all the mpoTrades
        restMpoTradeMockMvc.perform(get("/api/mpoTrades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoTrade.getId().intValue())))
                .andExpect(jsonPath("$.[*].cratedDate").value(hasItem(DEFAULT_CRATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getMpoTrade() throws Exception {
        // Initialize the database
        mpoTradeRepository.saveAndFlush(mpoTrade);

        // Get the mpoTrade
        restMpoTradeMockMvc.perform(get("/api/mpoTrades/{id}", mpoTrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoTrade.getId().intValue()))
            .andExpect(jsonPath("$.cratedDate").value(DEFAULT_CRATED_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingMpoTrade() throws Exception {
        // Get the mpoTrade
        restMpoTradeMockMvc.perform(get("/api/mpoTrades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoTrade() throws Exception {
        // Initialize the database
        mpoTradeRepository.saveAndFlush(mpoTrade);

		int databaseSizeBeforeUpdate = mpoTradeRepository.findAll().size();

        // Update the mpoTrade
        mpoTrade.setCratedDate(UPDATED_CRATED_DATE);
        mpoTrade.setUpdateDate(UPDATED_UPDATE_DATE);
        mpoTrade.setStatus(UPDATED_STATUS);

        restMpoTradeMockMvc.perform(put("/api/mpoTrades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoTrade)))
                .andExpect(status().isOk());

        // Validate the MpoTrade in the database
        List<MpoTrade> mpoTrades = mpoTradeRepository.findAll();
        assertThat(mpoTrades).hasSize(databaseSizeBeforeUpdate);
        MpoTrade testMpoTrade = mpoTrades.get(mpoTrades.size() - 1);
        assertThat(testMpoTrade.getCratedDate()).isEqualTo(UPDATED_CRATED_DATE);
        assertThat(testMpoTrade.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testMpoTrade.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteMpoTrade() throws Exception {
        // Initialize the database
        mpoTradeRepository.saveAndFlush(mpoTrade);

		int databaseSizeBeforeDelete = mpoTradeRepository.findAll().size();

        // Get the mpoTrade
        restMpoTradeMockMvc.perform(delete("/api/mpoTrades/{id}", mpoTrade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoTrade> mpoTrades = mpoTradeRepository.findAll();
        assertThat(mpoTrades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
