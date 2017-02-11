package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpAuditObjectionInfo;
import gov.step.app.repository.HrEmpAuditObjectionInfoRepository;
import gov.step.app.repository.search.HrEmpAuditObjectionInfoSearchRepository;
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
 * Test class for the HrEmpAuditObjectionInfoResource REST controller.
 *
 * @see HrEmpAuditObjectionInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpAuditObjectionInfoResourceIntTest {

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBB";

    private static final Long DEFAULT_AUDIT_YEAR = 1L;
    private static final Long UPDATED_AUDIT_YEAR = 2L;
    private static final String DEFAULT_PARAGRAPH_NUMBER = "AAAAA";
    private static final String UPDATED_PARAGRAPH_NUMBER = "BBBBB";
    private static final String DEFAULT_OBJECTION_HEADLIINE = "AAAAA";
    private static final String UPDATED_OBJECTION_HEADLIINE = "BBBBB";

    private static final BigDecimal DEFAULT_OBJECTION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_OBJECTION_AMOUNT = new BigDecimal(2);
    private static final String DEFAULT_OFFICE_REPLY_NUMBER = "AAAAA";
    private static final String UPDATED_OFFICE_REPLY_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_REPLY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPLY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_JOINT_MEETING_NUMBER = "AAAAA";
    private static final String UPDATED_JOINT_MEETING_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_JOINT_MEETING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINT_MEETING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_SETTLED = false;
    private static final Boolean UPDATED_IS_SETTLED = true;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

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
    private HrEmpAuditObjectionInfoRepository hrEmpAuditObjectionInfoRepository;

    @Inject
    private HrEmpAuditObjectionInfoSearchRepository hrEmpAuditObjectionInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpAuditObjectionInfoMockMvc;

    private HrEmpAuditObjectionInfo hrEmpAuditObjectionInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpAuditObjectionInfoResource hrEmpAuditObjectionInfoResource = new HrEmpAuditObjectionInfoResource();
        ReflectionTestUtils.setField(hrEmpAuditObjectionInfoResource, "hrEmpAuditObjectionInfoSearchRepository", hrEmpAuditObjectionInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpAuditObjectionInfoResource, "hrEmpAuditObjectionInfoRepository", hrEmpAuditObjectionInfoRepository);
        this.restHrEmpAuditObjectionInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpAuditObjectionInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpAuditObjectionInfo = new HrEmpAuditObjectionInfo();
        hrEmpAuditObjectionInfo.setOrganizationName(DEFAULT_ORGANIZATION_NAME);
        hrEmpAuditObjectionInfo.setAuditYear(DEFAULT_AUDIT_YEAR);
        hrEmpAuditObjectionInfo.setParagraphNumber(DEFAULT_PARAGRAPH_NUMBER);
        hrEmpAuditObjectionInfo.setObjectionHeadliine(DEFAULT_OBJECTION_HEADLIINE);
        hrEmpAuditObjectionInfo.setObjectionAmount(DEFAULT_OBJECTION_AMOUNT);
        hrEmpAuditObjectionInfo.setOfficeReplyNumber(DEFAULT_OFFICE_REPLY_NUMBER);
        hrEmpAuditObjectionInfo.setReplyDate(DEFAULT_REPLY_DATE);
        hrEmpAuditObjectionInfo.setJointMeetingNumber(DEFAULT_JOINT_MEETING_NUMBER);
        hrEmpAuditObjectionInfo.setJointMeetingDate(DEFAULT_JOINT_MEETING_DATE);
        hrEmpAuditObjectionInfo.setIsSettled(DEFAULT_IS_SETTLED);
        hrEmpAuditObjectionInfo.setRemarks(DEFAULT_REMARKS);
        hrEmpAuditObjectionInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpAuditObjectionInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpAuditObjectionInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpAuditObjectionInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpAuditObjectionInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpAuditObjectionInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpAuditObjectionInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpAuditObjectionInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpAuditObjectionInfoRepository.findAll().size();

        // Create the HrEmpAuditObjectionInfo

        restHrEmpAuditObjectionInfoMockMvc.perform(post("/api/hrEmpAuditObjectionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAuditObjectionInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpAuditObjectionInfo in the database
        List<HrEmpAuditObjectionInfo> hrEmpAuditObjectionInfos = hrEmpAuditObjectionInfoRepository.findAll();
        assertThat(hrEmpAuditObjectionInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpAuditObjectionInfo testHrEmpAuditObjectionInfo = hrEmpAuditObjectionInfos.get(hrEmpAuditObjectionInfos.size() - 1);
        assertThat(testHrEmpAuditObjectionInfo.getOrganizationName()).isEqualTo(DEFAULT_ORGANIZATION_NAME);
        assertThat(testHrEmpAuditObjectionInfo.getAuditYear()).isEqualTo(DEFAULT_AUDIT_YEAR);
        assertThat(testHrEmpAuditObjectionInfo.getParagraphNumber()).isEqualTo(DEFAULT_PARAGRAPH_NUMBER);
        assertThat(testHrEmpAuditObjectionInfo.getObjectionHeadliine()).isEqualTo(DEFAULT_OBJECTION_HEADLIINE);
        assertThat(testHrEmpAuditObjectionInfo.getObjectionAmount()).isEqualTo(DEFAULT_OBJECTION_AMOUNT);
        assertThat(testHrEmpAuditObjectionInfo.getOfficeReplyNumber()).isEqualTo(DEFAULT_OFFICE_REPLY_NUMBER);
        assertThat(testHrEmpAuditObjectionInfo.getReplyDate()).isEqualTo(DEFAULT_REPLY_DATE);
        assertThat(testHrEmpAuditObjectionInfo.getJointMeetingNumber()).isEqualTo(DEFAULT_JOINT_MEETING_NUMBER);
        assertThat(testHrEmpAuditObjectionInfo.getJointMeetingDate()).isEqualTo(DEFAULT_JOINT_MEETING_DATE);
        assertThat(testHrEmpAuditObjectionInfo.getIsSettled()).isEqualTo(DEFAULT_IS_SETTLED);
        assertThat(testHrEmpAuditObjectionInfo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testHrEmpAuditObjectionInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpAuditObjectionInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpAuditObjectionInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpAuditObjectionInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpAuditObjectionInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpAuditObjectionInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpAuditObjectionInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkOrganizationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAuditObjectionInfoRepository.findAll().size();
        // set the field null
        hrEmpAuditObjectionInfo.setOrganizationName(null);

        // Create the HrEmpAuditObjectionInfo, which fails.

        restHrEmpAuditObjectionInfoMockMvc.perform(post("/api/hrEmpAuditObjectionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAuditObjectionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAuditObjectionInfo> hrEmpAuditObjectionInfos = hrEmpAuditObjectionInfoRepository.findAll();
        assertThat(hrEmpAuditObjectionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParagraphNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAuditObjectionInfoRepository.findAll().size();
        // set the field null
        hrEmpAuditObjectionInfo.setParagraphNumber(null);

        // Create the HrEmpAuditObjectionInfo, which fails.

        restHrEmpAuditObjectionInfoMockMvc.perform(post("/api/hrEmpAuditObjectionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAuditObjectionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAuditObjectionInfo> hrEmpAuditObjectionInfos = hrEmpAuditObjectionInfoRepository.findAll();
        assertThat(hrEmpAuditObjectionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObjectionHeadliineIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAuditObjectionInfoRepository.findAll().size();
        // set the field null
        hrEmpAuditObjectionInfo.setObjectionHeadliine(null);

        // Create the HrEmpAuditObjectionInfo, which fails.

        restHrEmpAuditObjectionInfoMockMvc.perform(post("/api/hrEmpAuditObjectionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAuditObjectionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAuditObjectionInfo> hrEmpAuditObjectionInfos = hrEmpAuditObjectionInfoRepository.findAll();
        assertThat(hrEmpAuditObjectionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAuditObjectionInfoRepository.findAll().size();
        // set the field null
        hrEmpAuditObjectionInfo.setActiveStatus(null);

        // Create the HrEmpAuditObjectionInfo, which fails.

        restHrEmpAuditObjectionInfoMockMvc.perform(post("/api/hrEmpAuditObjectionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAuditObjectionInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAuditObjectionInfo> hrEmpAuditObjectionInfos = hrEmpAuditObjectionInfoRepository.findAll();
        assertThat(hrEmpAuditObjectionInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpAuditObjectionInfos() throws Exception {
        // Initialize the database
        hrEmpAuditObjectionInfoRepository.saveAndFlush(hrEmpAuditObjectionInfo);

        // Get all the hrEmpAuditObjectionInfos
        restHrEmpAuditObjectionInfoMockMvc.perform(get("/api/hrEmpAuditObjectionInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpAuditObjectionInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME.toString())))
                .andExpect(jsonPath("$.[*].auditYear").value(hasItem(DEFAULT_AUDIT_YEAR.intValue())))
                .andExpect(jsonPath("$.[*].paragraphNumber").value(hasItem(DEFAULT_PARAGRAPH_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].objectionHeadliine").value(hasItem(DEFAULT_OBJECTION_HEADLIINE.toString())))
                .andExpect(jsonPath("$.[*].objectionAmount").value(hasItem(DEFAULT_OBJECTION_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].officeReplyNumber").value(hasItem(DEFAULT_OFFICE_REPLY_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].replyDate").value(hasItem(DEFAULT_REPLY_DATE.toString())))
                .andExpect(jsonPath("$.[*].jointMeetingNumber").value(hasItem(DEFAULT_JOINT_MEETING_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].jointMeetingDate").value(hasItem(DEFAULT_JOINT_MEETING_DATE.toString())))
                .andExpect(jsonPath("$.[*].isSettled").value(hasItem(DEFAULT_IS_SETTLED.booleanValue())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
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
    public void getHrEmpAuditObjectionInfo() throws Exception {
        // Initialize the database
        hrEmpAuditObjectionInfoRepository.saveAndFlush(hrEmpAuditObjectionInfo);

        // Get the hrEmpAuditObjectionInfo
        restHrEmpAuditObjectionInfoMockMvc.perform(get("/api/hrEmpAuditObjectionInfos/{id}", hrEmpAuditObjectionInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpAuditObjectionInfo.getId().intValue()))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME.toString()))
            .andExpect(jsonPath("$.auditYear").value(DEFAULT_AUDIT_YEAR.intValue()))
            .andExpect(jsonPath("$.paragraphNumber").value(DEFAULT_PARAGRAPH_NUMBER.toString()))
            .andExpect(jsonPath("$.objectionHeadliine").value(DEFAULT_OBJECTION_HEADLIINE.toString()))
            .andExpect(jsonPath("$.objectionAmount").value(DEFAULT_OBJECTION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.officeReplyNumber").value(DEFAULT_OFFICE_REPLY_NUMBER.toString()))
            .andExpect(jsonPath("$.replyDate").value(DEFAULT_REPLY_DATE.toString()))
            .andExpect(jsonPath("$.jointMeetingNumber").value(DEFAULT_JOINT_MEETING_NUMBER.toString()))
            .andExpect(jsonPath("$.jointMeetingDate").value(DEFAULT_JOINT_MEETING_DATE.toString()))
            .andExpect(jsonPath("$.isSettled").value(DEFAULT_IS_SETTLED.booleanValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
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
    public void getNonExistingHrEmpAuditObjectionInfo() throws Exception {
        // Get the hrEmpAuditObjectionInfo
        restHrEmpAuditObjectionInfoMockMvc.perform(get("/api/hrEmpAuditObjectionInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpAuditObjectionInfo() throws Exception {
        // Initialize the database
        hrEmpAuditObjectionInfoRepository.saveAndFlush(hrEmpAuditObjectionInfo);

		int databaseSizeBeforeUpdate = hrEmpAuditObjectionInfoRepository.findAll().size();

        // Update the hrEmpAuditObjectionInfo
        hrEmpAuditObjectionInfo.setOrganizationName(UPDATED_ORGANIZATION_NAME);
        hrEmpAuditObjectionInfo.setAuditYear(UPDATED_AUDIT_YEAR);
        hrEmpAuditObjectionInfo.setParagraphNumber(UPDATED_PARAGRAPH_NUMBER);
        hrEmpAuditObjectionInfo.setObjectionHeadliine(UPDATED_OBJECTION_HEADLIINE);
        hrEmpAuditObjectionInfo.setObjectionAmount(UPDATED_OBJECTION_AMOUNT);
        hrEmpAuditObjectionInfo.setOfficeReplyNumber(UPDATED_OFFICE_REPLY_NUMBER);
        hrEmpAuditObjectionInfo.setReplyDate(UPDATED_REPLY_DATE);
        hrEmpAuditObjectionInfo.setJointMeetingNumber(UPDATED_JOINT_MEETING_NUMBER);
        hrEmpAuditObjectionInfo.setJointMeetingDate(UPDATED_JOINT_MEETING_DATE);
        hrEmpAuditObjectionInfo.setIsSettled(UPDATED_IS_SETTLED);
        hrEmpAuditObjectionInfo.setRemarks(UPDATED_REMARKS);
        hrEmpAuditObjectionInfo.setLogId(UPDATED_LOG_ID);
        hrEmpAuditObjectionInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpAuditObjectionInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpAuditObjectionInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpAuditObjectionInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpAuditObjectionInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpAuditObjectionInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpAuditObjectionInfoMockMvc.perform(put("/api/hrEmpAuditObjectionInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAuditObjectionInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpAuditObjectionInfo in the database
        List<HrEmpAuditObjectionInfo> hrEmpAuditObjectionInfos = hrEmpAuditObjectionInfoRepository.findAll();
        assertThat(hrEmpAuditObjectionInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpAuditObjectionInfo testHrEmpAuditObjectionInfo = hrEmpAuditObjectionInfos.get(hrEmpAuditObjectionInfos.size() - 1);
        assertThat(testHrEmpAuditObjectionInfo.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testHrEmpAuditObjectionInfo.getAuditYear()).isEqualTo(UPDATED_AUDIT_YEAR);
        assertThat(testHrEmpAuditObjectionInfo.getParagraphNumber()).isEqualTo(UPDATED_PARAGRAPH_NUMBER);
        assertThat(testHrEmpAuditObjectionInfo.getObjectionHeadliine()).isEqualTo(UPDATED_OBJECTION_HEADLIINE);
        assertThat(testHrEmpAuditObjectionInfo.getObjectionAmount()).isEqualTo(UPDATED_OBJECTION_AMOUNT);
        assertThat(testHrEmpAuditObjectionInfo.getOfficeReplyNumber()).isEqualTo(UPDATED_OFFICE_REPLY_NUMBER);
        assertThat(testHrEmpAuditObjectionInfo.getReplyDate()).isEqualTo(UPDATED_REPLY_DATE);
        assertThat(testHrEmpAuditObjectionInfo.getJointMeetingNumber()).isEqualTo(UPDATED_JOINT_MEETING_NUMBER);
        assertThat(testHrEmpAuditObjectionInfo.getJointMeetingDate()).isEqualTo(UPDATED_JOINT_MEETING_DATE);
        assertThat(testHrEmpAuditObjectionInfo.getIsSettled()).isEqualTo(UPDATED_IS_SETTLED);
        assertThat(testHrEmpAuditObjectionInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testHrEmpAuditObjectionInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpAuditObjectionInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpAuditObjectionInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpAuditObjectionInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpAuditObjectionInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpAuditObjectionInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpAuditObjectionInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpAuditObjectionInfo() throws Exception {
        // Initialize the database
        hrEmpAuditObjectionInfoRepository.saveAndFlush(hrEmpAuditObjectionInfo);

		int databaseSizeBeforeDelete = hrEmpAuditObjectionInfoRepository.findAll().size();

        // Get the hrEmpAuditObjectionInfo
        restHrEmpAuditObjectionInfoMockMvc.perform(delete("/api/hrEmpAuditObjectionInfos/{id}", hrEmpAuditObjectionInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpAuditObjectionInfo> hrEmpAuditObjectionInfos = hrEmpAuditObjectionInfoRepository.findAll();
        assertThat(hrEmpAuditObjectionInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
