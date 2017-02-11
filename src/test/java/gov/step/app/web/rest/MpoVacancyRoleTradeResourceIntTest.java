package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoVacancyRoleTrade;
import gov.step.app.repository.MpoVacancyRoleTradeRepository;
import gov.step.app.repository.search.MpoVacancyRoleTradeSearchRepository;

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
 * Test class for the MpoVacancyRoleTradeResource REST controller.
 *
 * @see MpoVacancyRoleTradeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoVacancyRoleTradeResourceIntTest {


    private static final LocalDate DEFAULT_CRATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CRATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private MpoVacancyRoleTradeRepository mpoVacancyRoleTradeRepository;

    @Inject
    private MpoVacancyRoleTradeSearchRepository mpoVacancyRoleTradeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoVacancyRoleTradeMockMvc;

    private MpoVacancyRoleTrade mpoVacancyRoleTrade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoVacancyRoleTradeResource mpoVacancyRoleTradeResource = new MpoVacancyRoleTradeResource();
        ReflectionTestUtils.setField(mpoVacancyRoleTradeResource, "mpoVacancyRoleTradeRepository", mpoVacancyRoleTradeRepository);
        ReflectionTestUtils.setField(mpoVacancyRoleTradeResource, "mpoVacancyRoleTradeSearchRepository", mpoVacancyRoleTradeSearchRepository);
        this.restMpoVacancyRoleTradeMockMvc = MockMvcBuilders.standaloneSetup(mpoVacancyRoleTradeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoVacancyRoleTrade = new MpoVacancyRoleTrade();
        mpoVacancyRoleTrade.setCrateDate(DEFAULT_CRATE_DATE);
        mpoVacancyRoleTrade.setUpdateDate(DEFAULT_UPDATE_DATE);
        mpoVacancyRoleTrade.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createMpoVacancyRoleTrade() throws Exception {
        int databaseSizeBeforeCreate = mpoVacancyRoleTradeRepository.findAll().size();

        // Create the MpoVacancyRoleTrade

        restMpoVacancyRoleTradeMockMvc.perform(post("/api/mpoVacancyRoleTrades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoVacancyRoleTrade)))
                .andExpect(status().isCreated());

        // Validate the MpoVacancyRoleTrade in the database
        List<MpoVacancyRoleTrade> mpoVacancyRoleTrades = mpoVacancyRoleTradeRepository.findAll();
        assertThat(mpoVacancyRoleTrades).hasSize(databaseSizeBeforeCreate + 1);
        MpoVacancyRoleTrade testMpoVacancyRoleTrade = mpoVacancyRoleTrades.get(mpoVacancyRoleTrades.size() - 1);
        assertThat(testMpoVacancyRoleTrade.getCrateDate()).isEqualTo(DEFAULT_CRATE_DATE);
        assertThat(testMpoVacancyRoleTrade.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testMpoVacancyRoleTrade.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMpoVacancyRoleTrades() throws Exception {
        // Initialize the database
        mpoVacancyRoleTradeRepository.saveAndFlush(mpoVacancyRoleTrade);

        // Get all the mpoVacancyRoleTrades
        restMpoVacancyRoleTradeMockMvc.perform(get("/api/mpoVacancyRoleTrades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoVacancyRoleTrade.getId().intValue())))
                .andExpect(jsonPath("$.[*].crateDate").value(hasItem(DEFAULT_CRATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getMpoVacancyRoleTrade() throws Exception {
        // Initialize the database
        mpoVacancyRoleTradeRepository.saveAndFlush(mpoVacancyRoleTrade);

        // Get the mpoVacancyRoleTrade
        restMpoVacancyRoleTradeMockMvc.perform(get("/api/mpoVacancyRoleTrades/{id}", mpoVacancyRoleTrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoVacancyRoleTrade.getId().intValue()))
            .andExpect(jsonPath("$.crateDate").value(DEFAULT_CRATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMpoVacancyRoleTrade() throws Exception {
        // Get the mpoVacancyRoleTrade
        restMpoVacancyRoleTradeMockMvc.perform(get("/api/mpoVacancyRoleTrades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoVacancyRoleTrade() throws Exception {
        // Initialize the database
        mpoVacancyRoleTradeRepository.saveAndFlush(mpoVacancyRoleTrade);

		int databaseSizeBeforeUpdate = mpoVacancyRoleTradeRepository.findAll().size();

        // Update the mpoVacancyRoleTrade
        mpoVacancyRoleTrade.setCrateDate(UPDATED_CRATE_DATE);
        mpoVacancyRoleTrade.setUpdateDate(UPDATED_UPDATE_DATE);
        mpoVacancyRoleTrade.setStatus(UPDATED_STATUS);

        restMpoVacancyRoleTradeMockMvc.perform(put("/api/mpoVacancyRoleTrades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoVacancyRoleTrade)))
                .andExpect(status().isOk());

        // Validate the MpoVacancyRoleTrade in the database
        List<MpoVacancyRoleTrade> mpoVacancyRoleTrades = mpoVacancyRoleTradeRepository.findAll();
        assertThat(mpoVacancyRoleTrades).hasSize(databaseSizeBeforeUpdate);
        MpoVacancyRoleTrade testMpoVacancyRoleTrade = mpoVacancyRoleTrades.get(mpoVacancyRoleTrades.size() - 1);
        assertThat(testMpoVacancyRoleTrade.getCrateDate()).isEqualTo(UPDATED_CRATE_DATE);
        assertThat(testMpoVacancyRoleTrade.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testMpoVacancyRoleTrade.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteMpoVacancyRoleTrade() throws Exception {
        // Initialize the database
        mpoVacancyRoleTradeRepository.saveAndFlush(mpoVacancyRoleTrade);

		int databaseSizeBeforeDelete = mpoVacancyRoleTradeRepository.findAll().size();

        // Get the mpoVacancyRoleTrade
        restMpoVacancyRoleTradeMockMvc.perform(delete("/api/mpoVacancyRoleTrades/{id}", mpoVacancyRoleTrade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoVacancyRoleTrade> mpoVacancyRoleTrades = mpoVacancyRoleTradeRepository.findAll();
        assertThat(mpoVacancyRoleTrades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
