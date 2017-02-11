package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsPenRules;
import gov.step.app.repository.PgmsPenRulesRepository;
import gov.step.app.repository.search.PgmsPenRulesSearchRepository;

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

import gov.step.app.domain.enumeration.penQuota;

/**
 * Test class for the PgmsPenRulesResource REST controller.
 *
 * @see PgmsPenRulesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsPenRulesResourceTest {



private static final penQuota DEFAULT_QUOTA_TYPE = penQuota.NA;
    private static final penQuota UPDATED_QUOTA_TYPE = penQuota.FreedomFighter;

    private static final Long DEFAULT_MIN_AGE_LIMIT = 1L;
    private static final Long UPDATED_MIN_AGE_LIMIT = 2L;

    private static final Long DEFAULT_MAX_AGE_LIMIT = 1L;
    private static final Long UPDATED_MAX_AGE_LIMIT = 2L;

    private static final Long DEFAULT_MIN_WORK_DURATION = 1L;
    private static final Long UPDATED_MIN_WORK_DURATION = 2L;

    private static final Boolean DEFAULT_DISABLE = false;
    private static final Boolean UPDATED_DISABLE = true;

    private static final Boolean DEFAULT_SENILE = false;
    private static final Boolean UPDATED_SENILE = true;

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private PgmsPenRulesRepository pgmsPenRulesRepository;

    @Inject
    private PgmsPenRulesSearchRepository pgmsPenRulesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsPenRulesMockMvc;

    private PgmsPenRules pgmsPenRules;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsPenRulesResource pgmsPenRulesResource = new PgmsPenRulesResource();
        ReflectionTestUtils.setField(pgmsPenRulesResource, "pgmsPenRulesRepository", pgmsPenRulesRepository);
        ReflectionTestUtils.setField(pgmsPenRulesResource, "pgmsPenRulesSearchRepository", pgmsPenRulesSearchRepository);
        this.restPgmsPenRulesMockMvc = MockMvcBuilders.standaloneSetup(pgmsPenRulesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsPenRules = new PgmsPenRules();
        pgmsPenRules.setQuotaType(DEFAULT_QUOTA_TYPE);
        pgmsPenRules.setMinAgeLimit(DEFAULT_MIN_AGE_LIMIT);
        pgmsPenRules.setMaxAgeLimit(DEFAULT_MAX_AGE_LIMIT);
        pgmsPenRules.setMinWorkDuration(DEFAULT_MIN_WORK_DURATION);
        pgmsPenRules.setDisable(DEFAULT_DISABLE);
        pgmsPenRules.setSenile(DEFAULT_SENILE);
        pgmsPenRules.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pgmsPenRules.setCreateDate(DEFAULT_CREATE_DATE);
        pgmsPenRules.setCreateBy(DEFAULT_CREATE_BY);
        pgmsPenRules.setUpdateDate(DEFAULT_UPDATE_DATE);
        pgmsPenRules.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPgmsPenRules() throws Exception {
        int databaseSizeBeforeCreate = pgmsPenRulesRepository.findAll().size();

        // Create the PgmsPenRules

        restPgmsPenRulesMockMvc.perform(post("/api/pgmsPenRuless")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenRules)))
                .andExpect(status().isCreated());

        // Validate the PgmsPenRules in the database
        List<PgmsPenRules> pgmsPenRuless = pgmsPenRulesRepository.findAll();
        assertThat(pgmsPenRuless).hasSize(databaseSizeBeforeCreate + 1);
        PgmsPenRules testPgmsPenRules = pgmsPenRuless.get(pgmsPenRuless.size() - 1);
        assertThat(testPgmsPenRules.getQuotaType()).isEqualTo(DEFAULT_QUOTA_TYPE);
        assertThat(testPgmsPenRules.getMinAgeLimit()).isEqualTo(DEFAULT_MIN_AGE_LIMIT);
        assertThat(testPgmsPenRules.getMaxAgeLimit()).isEqualTo(DEFAULT_MAX_AGE_LIMIT);
        assertThat(testPgmsPenRules.getMinWorkDuration()).isEqualTo(DEFAULT_MIN_WORK_DURATION);
        assertThat(testPgmsPenRules.getDisable()).isEqualTo(DEFAULT_DISABLE);
        assertThat(testPgmsPenRules.getSenile()).isEqualTo(DEFAULT_SENILE);
        assertThat(testPgmsPenRules.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPgmsPenRules.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPgmsPenRules.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPgmsPenRules.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPgmsPenRules.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkMinAgeLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsPenRulesRepository.findAll().size();
        // set the field null
        pgmsPenRules.setMinAgeLimit(null);

        // Create the PgmsPenRules, which fails.

        restPgmsPenRulesMockMvc.perform(post("/api/pgmsPenRuless")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenRules)))
                .andExpect(status().isBadRequest());

        List<PgmsPenRules> pgmsPenRuless = pgmsPenRulesRepository.findAll();
        assertThat(pgmsPenRuless).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxAgeLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsPenRulesRepository.findAll().size();
        // set the field null
        pgmsPenRules.setMaxAgeLimit(null);

        // Create the PgmsPenRules, which fails.

        restPgmsPenRulesMockMvc.perform(post("/api/pgmsPenRuless")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenRules)))
                .andExpect(status().isBadRequest());

        List<PgmsPenRules> pgmsPenRuless = pgmsPenRulesRepository.findAll();
        assertThat(pgmsPenRuless).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinWorkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsPenRulesRepository.findAll().size();
        // set the field null
        pgmsPenRules.setMinWorkDuration(null);

        // Create the PgmsPenRules, which fails.

        restPgmsPenRulesMockMvc.perform(post("/api/pgmsPenRuless")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenRules)))
                .andExpect(status().isBadRequest());

        List<PgmsPenRules> pgmsPenRuless = pgmsPenRulesRepository.findAll();
        assertThat(pgmsPenRuless).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsPenRuless() throws Exception {
        // Initialize the database
        pgmsPenRulesRepository.saveAndFlush(pgmsPenRules);

        // Get all the pgmsPenRuless
        restPgmsPenRulesMockMvc.perform(get("/api/pgmsPenRuless"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsPenRules.getId().intValue())))
                .andExpect(jsonPath("$.[*].quotaType").value(hasItem(DEFAULT_QUOTA_TYPE.toString())))
                .andExpect(jsonPath("$.[*].minAgeLimit").value(hasItem(DEFAULT_MIN_AGE_LIMIT.intValue())))
                .andExpect(jsonPath("$.[*].maxAgeLimit").value(hasItem(DEFAULT_MAX_AGE_LIMIT.intValue())))
                .andExpect(jsonPath("$.[*].minWorkDuration").value(hasItem(DEFAULT_MIN_WORK_DURATION.intValue())))
                .andExpect(jsonPath("$.[*].disable").value(hasItem(DEFAULT_DISABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].senile").value(hasItem(DEFAULT_SENILE.booleanValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPgmsPenRules() throws Exception {
        // Initialize the database
        pgmsPenRulesRepository.saveAndFlush(pgmsPenRules);

        // Get the pgmsPenRules
        restPgmsPenRulesMockMvc.perform(get("/api/pgmsPenRuless/{id}", pgmsPenRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsPenRules.getId().intValue()))
            .andExpect(jsonPath("$.quotaType").value(DEFAULT_QUOTA_TYPE.toString()))
            .andExpect(jsonPath("$.minAgeLimit").value(DEFAULT_MIN_AGE_LIMIT.intValue()))
            .andExpect(jsonPath("$.maxAgeLimit").value(DEFAULT_MAX_AGE_LIMIT.intValue()))
            .andExpect(jsonPath("$.minWorkDuration").value(DEFAULT_MIN_WORK_DURATION.intValue()))
            .andExpect(jsonPath("$.disable").value(DEFAULT_DISABLE.booleanValue()))
            .andExpect(jsonPath("$.senile").value(DEFAULT_SENILE.booleanValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsPenRules() throws Exception {
        // Get the pgmsPenRules
        restPgmsPenRulesMockMvc.perform(get("/api/pgmsPenRuless/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsPenRules() throws Exception {
        // Initialize the database
        pgmsPenRulesRepository.saveAndFlush(pgmsPenRules);

		int databaseSizeBeforeUpdate = pgmsPenRulesRepository.findAll().size();

        // Update the pgmsPenRules
        pgmsPenRules.setQuotaType(UPDATED_QUOTA_TYPE);
        pgmsPenRules.setMinAgeLimit(UPDATED_MIN_AGE_LIMIT);
        pgmsPenRules.setMaxAgeLimit(UPDATED_MAX_AGE_LIMIT);
        pgmsPenRules.setMinWorkDuration(UPDATED_MIN_WORK_DURATION);
        pgmsPenRules.setDisable(UPDATED_DISABLE);
        pgmsPenRules.setSenile(UPDATED_SENILE);
        pgmsPenRules.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pgmsPenRules.setCreateDate(UPDATED_CREATE_DATE);
        pgmsPenRules.setCreateBy(UPDATED_CREATE_BY);
        pgmsPenRules.setUpdateDate(UPDATED_UPDATE_DATE);
        pgmsPenRules.setUpdateBy(UPDATED_UPDATE_BY);

        restPgmsPenRulesMockMvc.perform(put("/api/pgmsPenRuless")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsPenRules)))
                .andExpect(status().isOk());

        // Validate the PgmsPenRules in the database
        List<PgmsPenRules> pgmsPenRuless = pgmsPenRulesRepository.findAll();
        assertThat(pgmsPenRuless).hasSize(databaseSizeBeforeUpdate);
        PgmsPenRules testPgmsPenRules = pgmsPenRuless.get(pgmsPenRuless.size() - 1);
        assertThat(testPgmsPenRules.getQuotaType()).isEqualTo(UPDATED_QUOTA_TYPE);
        assertThat(testPgmsPenRules.getMinAgeLimit()).isEqualTo(UPDATED_MIN_AGE_LIMIT);
        assertThat(testPgmsPenRules.getMaxAgeLimit()).isEqualTo(UPDATED_MAX_AGE_LIMIT);
        assertThat(testPgmsPenRules.getMinWorkDuration()).isEqualTo(UPDATED_MIN_WORK_DURATION);
        assertThat(testPgmsPenRules.getDisable()).isEqualTo(UPDATED_DISABLE);
        assertThat(testPgmsPenRules.getSenile()).isEqualTo(UPDATED_SENILE);
        assertThat(testPgmsPenRules.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPgmsPenRules.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPgmsPenRules.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPgmsPenRules.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPgmsPenRules.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePgmsPenRules() throws Exception {
        // Initialize the database
        pgmsPenRulesRepository.saveAndFlush(pgmsPenRules);

		int databaseSizeBeforeDelete = pgmsPenRulesRepository.findAll().size();

        // Get the pgmsPenRules
        restPgmsPenRulesMockMvc.perform(delete("/api/pgmsPenRuless/{id}", pgmsPenRules.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsPenRules> pgmsPenRuless = pgmsPenRulesRepository.findAll();
        assertThat(pgmsPenRuless).hasSize(databaseSizeBeforeDelete - 1);
    }
}
