package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmploymentInfo;
import gov.step.app.repository.HrEmploymentInfoRepository;
import gov.step.app.repository.search.HrEmploymentInfoSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrEmploymentInfoResource REST controller.
 *
 * @see HrEmploymentInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmploymentInfoResourceIntTest {

    private static final String DEFAULT_PRESENT_INSTITUTE = "AAAAA";
    private static final String UPDATED_PRESENT_INSTITUTE = "BBBBB";

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REGULARIZATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGULARIZATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_JOB_CONF_NOTICE_NO = "AAAAA";
    private static final String UPDATED_JOB_CONF_NOTICE_NO = "BBBBB";

    private static final LocalDate DEFAULT_CONFIRMATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONFIRMATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_OFFICE_ORDER_NO = "AAAAA";
    private static final String UPDATED_OFFICE_ORDER_NO = "BBBBB";

    private static final LocalDate DEFAULT_OFFICE_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OFFICE_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private HrEmploymentInfoRepository hrEmploymentInfoRepository;

    @Inject
    private HrEmploymentInfoSearchRepository hrEmploymentInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmploymentInfoMockMvc;

    private HrEmploymentInfo hrEmploymentInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmploymentInfoResource hrEmploymentInfoResource = new HrEmploymentInfoResource();
        ReflectionTestUtils.setField(hrEmploymentInfoResource, "hrEmploymentInfoSearchRepository", hrEmploymentInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmploymentInfoResource, "hrEmploymentInfoRepository", hrEmploymentInfoRepository);
        this.restHrEmploymentInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmploymentInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmploymentInfo = new HrEmploymentInfo();
        hrEmploymentInfo.setPresentInstitute(DEFAULT_PRESENT_INSTITUTE);
        hrEmploymentInfo.setJoiningDate(DEFAULT_JOINING_DATE);
        hrEmploymentInfo.setRegularizationDate(DEFAULT_REGULARIZATION_DATE);
        hrEmploymentInfo.setJobConfNoticeNo(DEFAULT_JOB_CONF_NOTICE_NO);
        hrEmploymentInfo.setConfirmationDate(DEFAULT_CONFIRMATION_DATE);
        hrEmploymentInfo.setOfficeOrderNo(DEFAULT_OFFICE_ORDER_NO);
        hrEmploymentInfo.setOfficeOrderDate(DEFAULT_OFFICE_ORDER_DATE);
        hrEmploymentInfo.setLogId(DEFAULT_LOG_ID);
        hrEmploymentInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmploymentInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmploymentInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmploymentInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmploymentInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmploymentInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmploymentInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmploymentInfoRepository.findAll().size();

        // Create the HrEmploymentInfo

        restHrEmploymentInfoMockMvc.perform(post("/api/hrEmploymentInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmploymentInfo in the database
        List<HrEmploymentInfo> hrEmploymentInfos = hrEmploymentInfoRepository.findAll();
        assertThat(hrEmploymentInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmploymentInfo testHrEmploymentInfo = hrEmploymentInfos.get(hrEmploymentInfos.size() - 1);
        assertThat(testHrEmploymentInfo.getPresentInstitute()).isEqualTo(DEFAULT_PRESENT_INSTITUTE);
        assertThat(testHrEmploymentInfo.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testHrEmploymentInfo.getRegularizationDate()).isEqualTo(DEFAULT_REGULARIZATION_DATE);
        assertThat(testHrEmploymentInfo.getJobConfNoticeNo()).isEqualTo(DEFAULT_JOB_CONF_NOTICE_NO);
        assertThat(testHrEmploymentInfo.getConfirmationDate()).isEqualTo(DEFAULT_CONFIRMATION_DATE);
        assertThat(testHrEmploymentInfo.getOfficeOrderNo()).isEqualTo(DEFAULT_OFFICE_ORDER_NO);
        assertThat(testHrEmploymentInfo.getOfficeOrderDate()).isEqualTo(DEFAULT_OFFICE_ORDER_DATE);
        assertThat(testHrEmploymentInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmploymentInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmploymentInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmploymentInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmploymentInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmploymentInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmploymentInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkPresentInstituteIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmploymentInfoRepository.findAll().size();
        // set the field null
        hrEmploymentInfo.setPresentInstitute(null);

        // Create the HrEmploymentInfo, which fails.

        restHrEmploymentInfoMockMvc.perform(post("/api/hrEmploymentInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmploymentInfo> hrEmploymentInfos = hrEmploymentInfoRepository.findAll();
        assertThat(hrEmploymentInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJoiningDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmploymentInfoRepository.findAll().size();
        // set the field null
        hrEmploymentInfo.setJoiningDate(null);

        // Create the HrEmploymentInfo, which fails.

        restHrEmploymentInfoMockMvc.perform(post("/api/hrEmploymentInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmploymentInfo> hrEmploymentInfos = hrEmploymentInfoRepository.findAll();
        assertThat(hrEmploymentInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegularizationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmploymentInfoRepository.findAll().size();
        // set the field null
        hrEmploymentInfo.setRegularizationDate(null);

        // Create the HrEmploymentInfo, which fails.

        restHrEmploymentInfoMockMvc.perform(post("/api/hrEmploymentInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmploymentInfo> hrEmploymentInfos = hrEmploymentInfoRepository.findAll();
        assertThat(hrEmploymentInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmploymentInfoRepository.findAll().size();
        // set the field null
        hrEmploymentInfo.setActiveStatus(null);

        // Create the HrEmploymentInfo, which fails.

        restHrEmploymentInfoMockMvc.perform(post("/api/hrEmploymentInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmploymentInfo> hrEmploymentInfos = hrEmploymentInfoRepository.findAll();
        assertThat(hrEmploymentInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmploymentInfos() throws Exception {
        // Initialize the database
        hrEmploymentInfoRepository.saveAndFlush(hrEmploymentInfo);

        // Get all the hrEmploymentInfos
        restHrEmploymentInfoMockMvc.perform(get("/api/hrEmploymentInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmploymentInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].presentInstitute").value(hasItem(DEFAULT_PRESENT_INSTITUTE.toString())))
                .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
                .andExpect(jsonPath("$.[*].regularizationDate").value(hasItem(DEFAULT_REGULARIZATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].jobConfNoticeNo").value(hasItem(DEFAULT_JOB_CONF_NOTICE_NO.toString())))
                .andExpect(jsonPath("$.[*].confirmationDate").value(hasItem(DEFAULT_CONFIRMATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].officeOrderNo").value(hasItem(DEFAULT_OFFICE_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].officeOrderDate").value(hasItem(DEFAULT_OFFICE_ORDER_DATE.toString())))
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
    public void getHrEmploymentInfo() throws Exception {
        // Initialize the database
        hrEmploymentInfoRepository.saveAndFlush(hrEmploymentInfo);

        // Get the hrEmploymentInfo
        restHrEmploymentInfoMockMvc.perform(get("/api/hrEmploymentInfos/{id}", hrEmploymentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmploymentInfo.getId().intValue()))
            .andExpect(jsonPath("$.presentInstitute").value(DEFAULT_PRESENT_INSTITUTE.toString()))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.regularizationDate").value(DEFAULT_REGULARIZATION_DATE.toString()))
            .andExpect(jsonPath("$.jobConfNoticeNo").value(DEFAULT_JOB_CONF_NOTICE_NO.toString()))
            .andExpect(jsonPath("$.confirmationDate").value(DEFAULT_CONFIRMATION_DATE.toString()))
            .andExpect(jsonPath("$.officeOrderNo").value(DEFAULT_OFFICE_ORDER_NO.toString()))
            .andExpect(jsonPath("$.officeOrderDate").value(DEFAULT_OFFICE_ORDER_DATE.toString()))
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
    public void getNonExistingHrEmploymentInfo() throws Exception {
        // Get the hrEmploymentInfo
        restHrEmploymentInfoMockMvc.perform(get("/api/hrEmploymentInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmploymentInfo() throws Exception {
        // Initialize the database
        hrEmploymentInfoRepository.saveAndFlush(hrEmploymentInfo);

		int databaseSizeBeforeUpdate = hrEmploymentInfoRepository.findAll().size();

        // Update the hrEmploymentInfo
        hrEmploymentInfo.setPresentInstitute(UPDATED_PRESENT_INSTITUTE);
        hrEmploymentInfo.setJoiningDate(UPDATED_JOINING_DATE);
        hrEmploymentInfo.setRegularizationDate(UPDATED_REGULARIZATION_DATE);
        hrEmploymentInfo.setJobConfNoticeNo(UPDATED_JOB_CONF_NOTICE_NO);
        hrEmploymentInfo.setConfirmationDate(UPDATED_CONFIRMATION_DATE);
        hrEmploymentInfo.setOfficeOrderNo(UPDATED_OFFICE_ORDER_NO);
        hrEmploymentInfo.setOfficeOrderDate(UPDATED_OFFICE_ORDER_DATE);
        hrEmploymentInfo.setLogId(UPDATED_LOG_ID);
        hrEmploymentInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmploymentInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmploymentInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmploymentInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmploymentInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmploymentInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmploymentInfoMockMvc.perform(put("/api/hrEmploymentInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmploymentInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmploymentInfo in the database
        List<HrEmploymentInfo> hrEmploymentInfos = hrEmploymentInfoRepository.findAll();
        assertThat(hrEmploymentInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmploymentInfo testHrEmploymentInfo = hrEmploymentInfos.get(hrEmploymentInfos.size() - 1);
        assertThat(testHrEmploymentInfo.getPresentInstitute()).isEqualTo(UPDATED_PRESENT_INSTITUTE);
        assertThat(testHrEmploymentInfo.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testHrEmploymentInfo.getRegularizationDate()).isEqualTo(UPDATED_REGULARIZATION_DATE);
        assertThat(testHrEmploymentInfo.getJobConfNoticeNo()).isEqualTo(UPDATED_JOB_CONF_NOTICE_NO);
        assertThat(testHrEmploymentInfo.getConfirmationDate()).isEqualTo(UPDATED_CONFIRMATION_DATE);
        assertThat(testHrEmploymentInfo.getOfficeOrderNo()).isEqualTo(UPDATED_OFFICE_ORDER_NO);
        assertThat(testHrEmploymentInfo.getOfficeOrderDate()).isEqualTo(UPDATED_OFFICE_ORDER_DATE);
        assertThat(testHrEmploymentInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmploymentInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmploymentInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmploymentInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmploymentInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmploymentInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmploymentInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmploymentInfo() throws Exception {
        // Initialize the database
        hrEmploymentInfoRepository.saveAndFlush(hrEmploymentInfo);

		int databaseSizeBeforeDelete = hrEmploymentInfoRepository.findAll().size();

        // Get the hrEmploymentInfo
        restHrEmploymentInfoMockMvc.perform(delete("/api/hrEmploymentInfos/{id}", hrEmploymentInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmploymentInfo> hrEmploymentInfos = hrEmploymentInfoRepository.findAll();
        assertThat(hrEmploymentInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
