package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsRetirmntAttachInfo;
import gov.step.app.repository.PgmsRetirmntAttachInfoRepository;
import gov.step.app.repository.search.PgmsRetirmntAttachInfoSearchRepository;

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
 * Test class for the PgmsRetirmntAttachInfoResource REST controller.
 *
 * @see PgmsRetirmntAttachInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsRetirmntAttachInfoResourceTest {

    private static final String DEFAULT_ATTACH_NAME = "AAAAA";
    private static final String UPDATED_ATTACH_NAME = "BBBBB";

    private static final Long DEFAULT_PRIORITY = 1L;
    private static final Long UPDATED_PRIORITY = 2L;
    private static final String DEFAULT_ATTACH_TYPE = "AAAAA";
    private static final String UPDATED_ATTACH_TYPE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PgmsRetirmntAttachInfoRepository pgmsRetirmntAttachInfoRepository;

    @Inject
    private PgmsRetirmntAttachInfoSearchRepository pgmsRetirmntAttachInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsRetirmntAttachInfoMockMvc;

    private PgmsRetirmntAttachInfo pgmsRetirmntAttachInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsRetirmntAttachInfoResource pgmsRetirmntAttachInfoResource = new PgmsRetirmntAttachInfoResource();
        ReflectionTestUtils.setField(pgmsRetirmntAttachInfoResource, "pgmsRetirmntAttachInfoRepository", pgmsRetirmntAttachInfoRepository);
        ReflectionTestUtils.setField(pgmsRetirmntAttachInfoResource, "pgmsRetirmntAttachInfoSearchRepository", pgmsRetirmntAttachInfoSearchRepository);
        this.restPgmsRetirmntAttachInfoMockMvc = MockMvcBuilders.standaloneSetup(pgmsRetirmntAttachInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsRetirmntAttachInfo = new PgmsRetirmntAttachInfo();
        pgmsRetirmntAttachInfo.setAttachName(DEFAULT_ATTACH_NAME);
        pgmsRetirmntAttachInfo.setPriority(DEFAULT_PRIORITY);
        pgmsRetirmntAttachInfo.setAttachType(DEFAULT_ATTACH_TYPE);
        pgmsRetirmntAttachInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pgmsRetirmntAttachInfo.setCreateDate(DEFAULT_CREATE_DATE);
        pgmsRetirmntAttachInfo.setCreateBy(DEFAULT_CREATE_BY);
        pgmsRetirmntAttachInfo.setUpdateBy(DEFAULT_UPDATE_BY);
        pgmsRetirmntAttachInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createPgmsRetirmntAttachInfo() throws Exception {
        int databaseSizeBeforeCreate = pgmsRetirmntAttachInfoRepository.findAll().size();

        // Create the PgmsRetirmntAttachInfo

        restPgmsRetirmntAttachInfoMockMvc.perform(post("/api/pgmsRetirmntAttachInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsRetirmntAttachInfo)))
                .andExpect(status().isCreated());

        // Validate the PgmsRetirmntAttachInfo in the database
        List<PgmsRetirmntAttachInfo> pgmsRetirmntAttachInfos = pgmsRetirmntAttachInfoRepository.findAll();
        assertThat(pgmsRetirmntAttachInfos).hasSize(databaseSizeBeforeCreate + 1);
        PgmsRetirmntAttachInfo testPgmsRetirmntAttachInfo = pgmsRetirmntAttachInfos.get(pgmsRetirmntAttachInfos.size() - 1);
        assertThat(testPgmsRetirmntAttachInfo.getAttachName()).isEqualTo(DEFAULT_ATTACH_NAME);
        assertThat(testPgmsRetirmntAttachInfo.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPgmsRetirmntAttachInfo.getAttachType()).isEqualTo(DEFAULT_ATTACH_TYPE);
        assertThat(testPgmsRetirmntAttachInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPgmsRetirmntAttachInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPgmsRetirmntAttachInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPgmsRetirmntAttachInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testPgmsRetirmntAttachInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void checkAttachNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsRetirmntAttachInfoRepository.findAll().size();
        // set the field null
        pgmsRetirmntAttachInfo.setAttachName(null);

        // Create the PgmsRetirmntAttachInfo, which fails.

        restPgmsRetirmntAttachInfoMockMvc.perform(post("/api/pgmsRetirmntAttachInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsRetirmntAttachInfo)))
                .andExpect(status().isBadRequest());

        List<PgmsRetirmntAttachInfo> pgmsRetirmntAttachInfos = pgmsRetirmntAttachInfoRepository.findAll();
        assertThat(pgmsRetirmntAttachInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsRetirmntAttachInfoRepository.findAll().size();
        // set the field null
        pgmsRetirmntAttachInfo.setPriority(null);

        // Create the PgmsRetirmntAttachInfo, which fails.

        restPgmsRetirmntAttachInfoMockMvc.perform(post("/api/pgmsRetirmntAttachInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsRetirmntAttachInfo)))
                .andExpect(status().isBadRequest());

        List<PgmsRetirmntAttachInfo> pgmsRetirmntAttachInfos = pgmsRetirmntAttachInfoRepository.findAll();
        assertThat(pgmsRetirmntAttachInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttachTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsRetirmntAttachInfoRepository.findAll().size();
        // set the field null
        pgmsRetirmntAttachInfo.setAttachType(null);

        // Create the PgmsRetirmntAttachInfo, which fails.

        restPgmsRetirmntAttachInfoMockMvc.perform(post("/api/pgmsRetirmntAttachInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsRetirmntAttachInfo)))
                .andExpect(status().isBadRequest());

        List<PgmsRetirmntAttachInfo> pgmsRetirmntAttachInfos = pgmsRetirmntAttachInfoRepository.findAll();
        assertThat(pgmsRetirmntAttachInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsRetirmntAttachInfos() throws Exception {
        // Initialize the database
        pgmsRetirmntAttachInfoRepository.saveAndFlush(pgmsRetirmntAttachInfo);

        // Get all the pgmsRetirmntAttachInfos
        restPgmsRetirmntAttachInfoMockMvc.perform(get("/api/pgmsRetirmntAttachInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsRetirmntAttachInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].attachName").value(hasItem(DEFAULT_ATTACH_NAME.toString())))
                .andExpect(jsonPath("$.[*].Priority").value(hasItem(DEFAULT_PRIORITY.intValue())))
                .andExpect(jsonPath("$.[*].attachType").value(hasItem(DEFAULT_ATTACH_TYPE.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPgmsRetirmntAttachInfo() throws Exception {
        // Initialize the database
        pgmsRetirmntAttachInfoRepository.saveAndFlush(pgmsRetirmntAttachInfo);

        // Get the pgmsRetirmntAttachInfo
        restPgmsRetirmntAttachInfoMockMvc.perform(get("/api/pgmsRetirmntAttachInfos/{id}", pgmsRetirmntAttachInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsRetirmntAttachInfo.getId().intValue()))
            .andExpect(jsonPath("$.attachName").value(DEFAULT_ATTACH_NAME.toString()))
            .andExpect(jsonPath("$.Priority").value(DEFAULT_PRIORITY.intValue()))
            .andExpect(jsonPath("$.attachType").value(DEFAULT_ATTACH_TYPE.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsRetirmntAttachInfo() throws Exception {
        // Get the pgmsRetirmntAttachInfo
        restPgmsRetirmntAttachInfoMockMvc.perform(get("/api/pgmsRetirmntAttachInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsRetirmntAttachInfo() throws Exception {
        // Initialize the database
        pgmsRetirmntAttachInfoRepository.saveAndFlush(pgmsRetirmntAttachInfo);

		int databaseSizeBeforeUpdate = pgmsRetirmntAttachInfoRepository.findAll().size();

        // Update the pgmsRetirmntAttachInfo
        pgmsRetirmntAttachInfo.setAttachName(UPDATED_ATTACH_NAME);
        pgmsRetirmntAttachInfo.setPriority(UPDATED_PRIORITY);
        pgmsRetirmntAttachInfo.setAttachType(UPDATED_ATTACH_TYPE);
        pgmsRetirmntAttachInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pgmsRetirmntAttachInfo.setCreateDate(UPDATED_CREATE_DATE);
        pgmsRetirmntAttachInfo.setCreateBy(UPDATED_CREATE_BY);
        pgmsRetirmntAttachInfo.setUpdateBy(UPDATED_UPDATE_BY);
        pgmsRetirmntAttachInfo.setUpdateDate(UPDATED_UPDATE_DATE);

        restPgmsRetirmntAttachInfoMockMvc.perform(put("/api/pgmsRetirmntAttachInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsRetirmntAttachInfo)))
                .andExpect(status().isOk());

        // Validate the PgmsRetirmntAttachInfo in the database
        List<PgmsRetirmntAttachInfo> pgmsRetirmntAttachInfos = pgmsRetirmntAttachInfoRepository.findAll();
        assertThat(pgmsRetirmntAttachInfos).hasSize(databaseSizeBeforeUpdate);
        PgmsRetirmntAttachInfo testPgmsRetirmntAttachInfo = pgmsRetirmntAttachInfos.get(pgmsRetirmntAttachInfos.size() - 1);
        assertThat(testPgmsRetirmntAttachInfo.getAttachName()).isEqualTo(UPDATED_ATTACH_NAME);
        assertThat(testPgmsRetirmntAttachInfo.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPgmsRetirmntAttachInfo.getAttachType()).isEqualTo(UPDATED_ATTACH_TYPE);
        assertThat(testPgmsRetirmntAttachInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPgmsRetirmntAttachInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPgmsRetirmntAttachInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPgmsRetirmntAttachInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testPgmsRetirmntAttachInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deletePgmsRetirmntAttachInfo() throws Exception {
        // Initialize the database
        pgmsRetirmntAttachInfoRepository.saveAndFlush(pgmsRetirmntAttachInfo);

		int databaseSizeBeforeDelete = pgmsRetirmntAttachInfoRepository.findAll().size();

        // Get the pgmsRetirmntAttachInfo
        restPgmsRetirmntAttachInfoMockMvc.perform(delete("/api/pgmsRetirmntAttachInfos/{id}", pgmsRetirmntAttachInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsRetirmntAttachInfo> pgmsRetirmntAttachInfos = pgmsRetirmntAttachInfoRepository.findAll();
        assertThat(pgmsRetirmntAttachInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
