package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpGovtDuesInfo;
import gov.step.app.repository.HrEmpGovtDuesInfoRepository;
import gov.step.app.repository.search.HrEmpGovtDuesInfoSearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrEmpGovtDuesInfoResource REST controller.
 *
 * @see HrEmpGovtDuesInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpGovtDuesInfoResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final BigDecimal DEFAULT_DUE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DUE_AMOUNT = new BigDecimal(2);
    private static final String DEFAULT_CLAIMER_AUTHORITY = "AAAAA";
    private static final String UPDATED_CLAIMER_AUTHORITY = "BBBBB";
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    private static final Long DEFAULT_LOG_ID = 1L;
    private static final Long UPDATED_LOG_ID = 2L;

    private static final Long DEFAULT_LOG_STATUS = 1L;
    private static final Long UPDATED_LOG_STATUS = 2L;

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
    private HrEmpGovtDuesInfoRepository hrEmpGovtDuesInfoRepository;

    @Inject
    private HrEmpGovtDuesInfoSearchRepository hrEmpGovtDuesInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpGovtDuesInfoMockMvc;

    private HrEmpGovtDuesInfo hrEmpGovtDuesInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpGovtDuesInfoResource hrEmpGovtDuesInfoResource = new HrEmpGovtDuesInfoResource();
        ReflectionTestUtils.setField(hrEmpGovtDuesInfoResource, "hrEmpGovtDuesInfoSearchRepository", hrEmpGovtDuesInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpGovtDuesInfoResource, "hrEmpGovtDuesInfoRepository", hrEmpGovtDuesInfoRepository);
        this.restHrEmpGovtDuesInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpGovtDuesInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpGovtDuesInfo = new HrEmpGovtDuesInfo();
        hrEmpGovtDuesInfo.setDescription(DEFAULT_DESCRIPTION);
        hrEmpGovtDuesInfo.setDueAmount(DEFAULT_DUE_AMOUNT);
        hrEmpGovtDuesInfo.setClaimerAuthority(DEFAULT_CLAIMER_AUTHORITY);
        hrEmpGovtDuesInfo.setComments(DEFAULT_COMMENTS);
        hrEmpGovtDuesInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpGovtDuesInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpGovtDuesInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpGovtDuesInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpGovtDuesInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpGovtDuesInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpGovtDuesInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpGovtDuesInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpGovtDuesInfoRepository.findAll().size();

        // Create the HrEmpGovtDuesInfo

        restHrEmpGovtDuesInfoMockMvc.perform(post("/api/hrEmpGovtDuesInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpGovtDuesInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpGovtDuesInfo in the database
        List<HrEmpGovtDuesInfo> hrEmpGovtDuesInfos = hrEmpGovtDuesInfoRepository.findAll();
        assertThat(hrEmpGovtDuesInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpGovtDuesInfo testHrEmpGovtDuesInfo = hrEmpGovtDuesInfos.get(hrEmpGovtDuesInfos.size() - 1);
        assertThat(testHrEmpGovtDuesInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHrEmpGovtDuesInfo.getDueAmount()).isEqualTo(DEFAULT_DUE_AMOUNT);
        assertThat(testHrEmpGovtDuesInfo.getClaimerAuthority()).isEqualTo(DEFAULT_CLAIMER_AUTHORITY);
        assertThat(testHrEmpGovtDuesInfo.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testHrEmpGovtDuesInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpGovtDuesInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpGovtDuesInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpGovtDuesInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpGovtDuesInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpGovtDuesInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpGovtDuesInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpGovtDuesInfoRepository.findAll().size();
        // set the field null
        hrEmpGovtDuesInfo.setDescription(null);

        // Create the HrEmpGovtDuesInfo, which fails.

        restHrEmpGovtDuesInfoMockMvc.perform(post("/api/hrEmpGovtDuesInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpGovtDuesInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpGovtDuesInfo> hrEmpGovtDuesInfos = hrEmpGovtDuesInfoRepository.findAll();
        assertThat(hrEmpGovtDuesInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDueAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpGovtDuesInfoRepository.findAll().size();
        // set the field null
        hrEmpGovtDuesInfo.setDueAmount(null);

        // Create the HrEmpGovtDuesInfo, which fails.

        restHrEmpGovtDuesInfoMockMvc.perform(post("/api/hrEmpGovtDuesInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpGovtDuesInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpGovtDuesInfo> hrEmpGovtDuesInfos = hrEmpGovtDuesInfoRepository.findAll();
        assertThat(hrEmpGovtDuesInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClaimerAuthorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpGovtDuesInfoRepository.findAll().size();
        // set the field null
        hrEmpGovtDuesInfo.setClaimerAuthority(null);

        // Create the HrEmpGovtDuesInfo, which fails.

        restHrEmpGovtDuesInfoMockMvc.perform(post("/api/hrEmpGovtDuesInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpGovtDuesInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpGovtDuesInfo> hrEmpGovtDuesInfos = hrEmpGovtDuesInfoRepository.findAll();
        assertThat(hrEmpGovtDuesInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpGovtDuesInfoRepository.findAll().size();
        // set the field null
        hrEmpGovtDuesInfo.setActiveStatus(null);

        // Create the HrEmpGovtDuesInfo, which fails.

        restHrEmpGovtDuesInfoMockMvc.perform(post("/api/hrEmpGovtDuesInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpGovtDuesInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpGovtDuesInfo> hrEmpGovtDuesInfos = hrEmpGovtDuesInfoRepository.findAll();
        assertThat(hrEmpGovtDuesInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpGovtDuesInfos() throws Exception {
        // Initialize the database
        hrEmpGovtDuesInfoRepository.saveAndFlush(hrEmpGovtDuesInfo);

        // Get all the hrEmpGovtDuesInfos
        restHrEmpGovtDuesInfoMockMvc.perform(get("/api/hrEmpGovtDuesInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpGovtDuesInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].dueAmount").value(hasItem(DEFAULT_DUE_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].claimerAuthority").value(hasItem(DEFAULT_CLAIMER_AUTHORITY.toString())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].logId").value(hasItem(DEFAULT_LOG_ID.intValue())))
                .andExpect(jsonPath("$.[*].logStatus").value(hasItem(DEFAULT_LOG_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmpGovtDuesInfo() throws Exception {
        // Initialize the database
        hrEmpGovtDuesInfoRepository.saveAndFlush(hrEmpGovtDuesInfo);

        // Get the hrEmpGovtDuesInfo
        restHrEmpGovtDuesInfoMockMvc.perform(get("/api/hrEmpGovtDuesInfos/{id}", hrEmpGovtDuesInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpGovtDuesInfo.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dueAmount").value(DEFAULT_DUE_AMOUNT.intValue()))
            .andExpect(jsonPath("$.claimerAuthority").value(DEFAULT_CLAIMER_AUTHORITY.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.logId").value(DEFAULT_LOG_ID.intValue()))
            .andExpect(jsonPath("$.logStatus").value(DEFAULT_LOG_STATUS.intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmpGovtDuesInfo() throws Exception {
        // Get the hrEmpGovtDuesInfo
        restHrEmpGovtDuesInfoMockMvc.perform(get("/api/hrEmpGovtDuesInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpGovtDuesInfo() throws Exception {
        // Initialize the database
        hrEmpGovtDuesInfoRepository.saveAndFlush(hrEmpGovtDuesInfo);

		int databaseSizeBeforeUpdate = hrEmpGovtDuesInfoRepository.findAll().size();

        // Update the hrEmpGovtDuesInfo
        hrEmpGovtDuesInfo.setDescription(UPDATED_DESCRIPTION);
        hrEmpGovtDuesInfo.setDueAmount(UPDATED_DUE_AMOUNT);
        hrEmpGovtDuesInfo.setClaimerAuthority(UPDATED_CLAIMER_AUTHORITY);
        hrEmpGovtDuesInfo.setComments(UPDATED_COMMENTS);
        hrEmpGovtDuesInfo.setLogId(UPDATED_LOG_ID);
        hrEmpGovtDuesInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpGovtDuesInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpGovtDuesInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpGovtDuesInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpGovtDuesInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpGovtDuesInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpGovtDuesInfoMockMvc.perform(put("/api/hrEmpGovtDuesInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpGovtDuesInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpGovtDuesInfo in the database
        List<HrEmpGovtDuesInfo> hrEmpGovtDuesInfos = hrEmpGovtDuesInfoRepository.findAll();
        assertThat(hrEmpGovtDuesInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpGovtDuesInfo testHrEmpGovtDuesInfo = hrEmpGovtDuesInfos.get(hrEmpGovtDuesInfos.size() - 1);
        assertThat(testHrEmpGovtDuesInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHrEmpGovtDuesInfo.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
        assertThat(testHrEmpGovtDuesInfo.getClaimerAuthority()).isEqualTo(UPDATED_CLAIMER_AUTHORITY);
        assertThat(testHrEmpGovtDuesInfo.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testHrEmpGovtDuesInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpGovtDuesInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpGovtDuesInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpGovtDuesInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpGovtDuesInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpGovtDuesInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpGovtDuesInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpGovtDuesInfo() throws Exception {
        // Initialize the database
        hrEmpGovtDuesInfoRepository.saveAndFlush(hrEmpGovtDuesInfo);

		int databaseSizeBeforeDelete = hrEmpGovtDuesInfoRepository.findAll().size();

        // Get the hrEmpGovtDuesInfo
        restHrEmpGovtDuesInfoMockMvc.perform(delete("/api/hrEmpGovtDuesInfos/{id}", hrEmpGovtDuesInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpGovtDuesInfo> hrEmpGovtDuesInfos = hrEmpGovtDuesInfoRepository.findAll();
        assertThat(hrEmpGovtDuesInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
