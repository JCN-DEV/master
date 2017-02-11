package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpIncrementInfo;
import gov.step.app.repository.HrEmpIncrementInfoRepository;
import gov.step.app.repository.search.HrEmpIncrementInfoSearchRepository;
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
 * Test class for the HrEmpIncrementInfoResource REST controller.
 *
 * @see HrEmpIncrementInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpIncrementInfoResourceIntTest {


    private static final BigDecimal DEFAULT_INCREMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INCREMENT_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_INCREMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INCREMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_BASIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC = new BigDecimal(2);

    private static final LocalDate DEFAULT_BASIC_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BASIC_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private HrEmpIncrementInfoRepository hrEmpIncrementInfoRepository;

    @Inject
    private HrEmpIncrementInfoSearchRepository hrEmpIncrementInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpIncrementInfoMockMvc;

    private HrEmpIncrementInfo hrEmpIncrementInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpIncrementInfoResource hrEmpIncrementInfoResource = new HrEmpIncrementInfoResource();
        ReflectionTestUtils.setField(hrEmpIncrementInfoResource, "hrEmpIncrementInfoSearchRepository", hrEmpIncrementInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpIncrementInfoResource, "hrEmpIncrementInfoRepository", hrEmpIncrementInfoRepository);
        this.restHrEmpIncrementInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpIncrementInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpIncrementInfo = new HrEmpIncrementInfo();
        hrEmpIncrementInfo.setIncrementAmount(DEFAULT_INCREMENT_AMOUNT);
        hrEmpIncrementInfo.setIncrementDate(DEFAULT_INCREMENT_DATE);
        hrEmpIncrementInfo.setBasic(DEFAULT_BASIC);
        hrEmpIncrementInfo.setBasicDate(DEFAULT_BASIC_DATE);
        hrEmpIncrementInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpIncrementInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpIncrementInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpIncrementInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpIncrementInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpIncrementInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpIncrementInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpIncrementInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpIncrementInfoRepository.findAll().size();

        // Create the HrEmpIncrementInfo

        restHrEmpIncrementInfoMockMvc.perform(post("/api/hrEmpIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpIncrementInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpIncrementInfo in the database
        List<HrEmpIncrementInfo> hrEmpIncrementInfos = hrEmpIncrementInfoRepository.findAll();
        assertThat(hrEmpIncrementInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpIncrementInfo testHrEmpIncrementInfo = hrEmpIncrementInfos.get(hrEmpIncrementInfos.size() - 1);
        assertThat(testHrEmpIncrementInfo.getIncrementAmount()).isEqualTo(DEFAULT_INCREMENT_AMOUNT);
        assertThat(testHrEmpIncrementInfo.getIncrementDate()).isEqualTo(DEFAULT_INCREMENT_DATE);
        assertThat(testHrEmpIncrementInfo.getBasic()).isEqualTo(DEFAULT_BASIC);
        assertThat(testHrEmpIncrementInfo.getBasicDate()).isEqualTo(DEFAULT_BASIC_DATE);
        assertThat(testHrEmpIncrementInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpIncrementInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpIncrementInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpIncrementInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpIncrementInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpIncrementInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpIncrementInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkIncrementAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpIncrementInfo.setIncrementAmount(null);

        // Create the HrEmpIncrementInfo, which fails.

        restHrEmpIncrementInfoMockMvc.perform(post("/api/hrEmpIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpIncrementInfo> hrEmpIncrementInfos = hrEmpIncrementInfoRepository.findAll();
        assertThat(hrEmpIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncrementDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpIncrementInfo.setIncrementDate(null);

        // Create the HrEmpIncrementInfo, which fails.

        restHrEmpIncrementInfoMockMvc.perform(post("/api/hrEmpIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpIncrementInfo> hrEmpIncrementInfos = hrEmpIncrementInfoRepository.findAll();
        assertThat(hrEmpIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBasicIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpIncrementInfo.setBasic(null);

        // Create the HrEmpIncrementInfo, which fails.

        restHrEmpIncrementInfoMockMvc.perform(post("/api/hrEmpIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpIncrementInfo> hrEmpIncrementInfos = hrEmpIncrementInfoRepository.findAll();
        assertThat(hrEmpIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBasicDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpIncrementInfo.setBasicDate(null);

        // Create the HrEmpIncrementInfo, which fails.

        restHrEmpIncrementInfoMockMvc.perform(post("/api/hrEmpIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpIncrementInfo> hrEmpIncrementInfos = hrEmpIncrementInfoRepository.findAll();
        assertThat(hrEmpIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpIncrementInfo.setActiveStatus(null);

        // Create the HrEmpIncrementInfo, which fails.

        restHrEmpIncrementInfoMockMvc.perform(post("/api/hrEmpIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpIncrementInfo> hrEmpIncrementInfos = hrEmpIncrementInfoRepository.findAll();
        assertThat(hrEmpIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpIncrementInfos() throws Exception {
        // Initialize the database
        hrEmpIncrementInfoRepository.saveAndFlush(hrEmpIncrementInfo);

        // Get all the hrEmpIncrementInfos
        restHrEmpIncrementInfoMockMvc.perform(get("/api/hrEmpIncrementInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpIncrementInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].incrementAmount").value(hasItem(DEFAULT_INCREMENT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].incrementDate").value(hasItem(DEFAULT_INCREMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
                .andExpect(jsonPath("$.[*].basicDate").value(hasItem(DEFAULT_BASIC_DATE.toString())))
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
    public void getHrEmpIncrementInfo() throws Exception {
        // Initialize the database
        hrEmpIncrementInfoRepository.saveAndFlush(hrEmpIncrementInfo);

        // Get the hrEmpIncrementInfo
        restHrEmpIncrementInfoMockMvc.perform(get("/api/hrEmpIncrementInfos/{id}", hrEmpIncrementInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpIncrementInfo.getId().intValue()))
            .andExpect(jsonPath("$.incrementAmount").value(DEFAULT_INCREMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.incrementDate").value(DEFAULT_INCREMENT_DATE.toString()))
            .andExpect(jsonPath("$.basic").value(DEFAULT_BASIC.intValue()))
            .andExpect(jsonPath("$.basicDate").value(DEFAULT_BASIC_DATE.toString()))
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
    public void getNonExistingHrEmpIncrementInfo() throws Exception {
        // Get the hrEmpIncrementInfo
        restHrEmpIncrementInfoMockMvc.perform(get("/api/hrEmpIncrementInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpIncrementInfo() throws Exception {
        // Initialize the database
        hrEmpIncrementInfoRepository.saveAndFlush(hrEmpIncrementInfo);

		int databaseSizeBeforeUpdate = hrEmpIncrementInfoRepository.findAll().size();

        // Update the hrEmpIncrementInfo
        hrEmpIncrementInfo.setIncrementAmount(UPDATED_INCREMENT_AMOUNT);
        hrEmpIncrementInfo.setIncrementDate(UPDATED_INCREMENT_DATE);
        hrEmpIncrementInfo.setBasic(UPDATED_BASIC);
        hrEmpIncrementInfo.setBasicDate(UPDATED_BASIC_DATE);
        hrEmpIncrementInfo.setLogId(UPDATED_LOG_ID);
        hrEmpIncrementInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpIncrementInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpIncrementInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpIncrementInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpIncrementInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpIncrementInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpIncrementInfoMockMvc.perform(put("/api/hrEmpIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpIncrementInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpIncrementInfo in the database
        List<HrEmpIncrementInfo> hrEmpIncrementInfos = hrEmpIncrementInfoRepository.findAll();
        assertThat(hrEmpIncrementInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpIncrementInfo testHrEmpIncrementInfo = hrEmpIncrementInfos.get(hrEmpIncrementInfos.size() - 1);
        assertThat(testHrEmpIncrementInfo.getIncrementAmount()).isEqualTo(UPDATED_INCREMENT_AMOUNT);
        assertThat(testHrEmpIncrementInfo.getIncrementDate()).isEqualTo(UPDATED_INCREMENT_DATE);
        assertThat(testHrEmpIncrementInfo.getBasic()).isEqualTo(UPDATED_BASIC);
        assertThat(testHrEmpIncrementInfo.getBasicDate()).isEqualTo(UPDATED_BASIC_DATE);
        assertThat(testHrEmpIncrementInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpIncrementInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpIncrementInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpIncrementInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpIncrementInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpIncrementInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpIncrementInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpIncrementInfo() throws Exception {
        // Initialize the database
        hrEmpIncrementInfoRepository.saveAndFlush(hrEmpIncrementInfo);

		int databaseSizeBeforeDelete = hrEmpIncrementInfoRepository.findAll().size();

        // Get the hrEmpIncrementInfo
        restHrEmpIncrementInfoMockMvc.perform(delete("/api/hrEmpIncrementInfos/{id}", hrEmpIncrementInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpIncrementInfo> hrEmpIncrementInfos = hrEmpIncrementInfoRepository.findAll();
        assertThat(hrEmpIncrementInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
