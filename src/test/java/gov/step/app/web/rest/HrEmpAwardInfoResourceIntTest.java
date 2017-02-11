package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpAwardInfo;
import gov.step.app.repository.HrEmpAwardInfoRepository;
import gov.step.app.repository.search.HrEmpAwardInfoSearchRepository;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the HrEmpAwardInfoResource REST controller.
 *
 * @see HrEmpAwardInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpAwardInfoResourceIntTest {

    private static final String DEFAULT_AWARD_NAME = "AAAAA";
    private static final String UPDATED_AWARD_NAME = "BBBBB";
    private static final String DEFAULT_AWARD_AREA = "AAAAA";
    private static final String UPDATED_AWARD_AREA = "BBBBB";

    private static final LocalDate DEFAULT_AWARD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AWARD_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final byte[] DEFAULT_GO_ORDER_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GO_ORDER_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_GO_ORDER_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GO_ORDER_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_GO_ORDER_DOC_NAME = "AAAAA";
    private static final String UPDATED_GO_ORDER_DOC_NAME = "BBBBB";

    private static final byte[] DEFAULT_CERT_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERT_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CERT_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERT_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_CERT_DOC_NAME = "AAAAA";
    private static final String UPDATED_CERT_DOC_NAME = "BBBBB";

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
    private HrEmpAwardInfoRepository hrEmpAwardInfoRepository;

    @Inject
    private HrEmpAwardInfoSearchRepository hrEmpAwardInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpAwardInfoMockMvc;

    private HrEmpAwardInfo hrEmpAwardInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpAwardInfoResource hrEmpAwardInfoResource = new HrEmpAwardInfoResource();
        ReflectionTestUtils.setField(hrEmpAwardInfoResource, "hrEmpAwardInfoSearchRepository", hrEmpAwardInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpAwardInfoResource, "hrEmpAwardInfoRepository", hrEmpAwardInfoRepository);
        this.restHrEmpAwardInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpAwardInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpAwardInfo = new HrEmpAwardInfo();
        hrEmpAwardInfo.setAwardName(DEFAULT_AWARD_NAME);
        hrEmpAwardInfo.setAwardArea(DEFAULT_AWARD_AREA);
        hrEmpAwardInfo.setAwardDate(DEFAULT_AWARD_DATE);
        hrEmpAwardInfo.setRemarks(DEFAULT_REMARKS);
        hrEmpAwardInfo.setGoOrderDoc(DEFAULT_GO_ORDER_DOC);
        hrEmpAwardInfo.setGoOrderDocContentType(DEFAULT_GO_ORDER_DOC_CONTENT_TYPE);
        hrEmpAwardInfo.setGoOrderDocName(DEFAULT_GO_ORDER_DOC_NAME);
        hrEmpAwardInfo.setCertDoc(DEFAULT_CERT_DOC);
        hrEmpAwardInfo.setCertDocContentType(DEFAULT_CERT_DOC_CONTENT_TYPE);
        hrEmpAwardInfo.setCertDocName(DEFAULT_CERT_DOC_NAME);
        hrEmpAwardInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpAwardInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpAwardInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpAwardInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpAwardInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpAwardInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpAwardInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpAwardInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpAwardInfoRepository.findAll().size();

        // Create the HrEmpAwardInfo

        restHrEmpAwardInfoMockMvc.perform(post("/api/hrEmpAwardInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAwardInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpAwardInfo in the database
        List<HrEmpAwardInfo> hrEmpAwardInfos = hrEmpAwardInfoRepository.findAll();
        assertThat(hrEmpAwardInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpAwardInfo testHrEmpAwardInfo = hrEmpAwardInfos.get(hrEmpAwardInfos.size() - 1);
        assertThat(testHrEmpAwardInfo.getAwardName()).isEqualTo(DEFAULT_AWARD_NAME);
        assertThat(testHrEmpAwardInfo.getAwardArea()).isEqualTo(DEFAULT_AWARD_AREA);
        assertThat(testHrEmpAwardInfo.getAwardDate()).isEqualTo(DEFAULT_AWARD_DATE);
        assertThat(testHrEmpAwardInfo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testHrEmpAwardInfo.getGoOrderDoc()).isEqualTo(DEFAULT_GO_ORDER_DOC);
        assertThat(testHrEmpAwardInfo.getGoOrderDocContentType()).isEqualTo(DEFAULT_GO_ORDER_DOC_CONTENT_TYPE);
        assertThat(testHrEmpAwardInfo.getGoOrderDocName()).isEqualTo(DEFAULT_GO_ORDER_DOC_NAME);
        assertThat(testHrEmpAwardInfo.getCertDoc()).isEqualTo(DEFAULT_CERT_DOC);
        assertThat(testHrEmpAwardInfo.getCertDocContentType()).isEqualTo(DEFAULT_CERT_DOC_CONTENT_TYPE);
        assertThat(testHrEmpAwardInfo.getCertDocName()).isEqualTo(DEFAULT_CERT_DOC_NAME);
        assertThat(testHrEmpAwardInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpAwardInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpAwardInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpAwardInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpAwardInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpAwardInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpAwardInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkAwardNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAwardInfoRepository.findAll().size();
        // set the field null
        hrEmpAwardInfo.setAwardName(null);

        // Create the HrEmpAwardInfo, which fails.

        restHrEmpAwardInfoMockMvc.perform(post("/api/hrEmpAwardInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAwardInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAwardInfo> hrEmpAwardInfos = hrEmpAwardInfoRepository.findAll();
        assertThat(hrEmpAwardInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAwardAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAwardInfoRepository.findAll().size();
        // set the field null
        hrEmpAwardInfo.setAwardArea(null);

        // Create the HrEmpAwardInfo, which fails.

        restHrEmpAwardInfoMockMvc.perform(post("/api/hrEmpAwardInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAwardInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAwardInfo> hrEmpAwardInfos = hrEmpAwardInfoRepository.findAll();
        assertThat(hrEmpAwardInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAwardDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAwardInfoRepository.findAll().size();
        // set the field null
        hrEmpAwardInfo.setAwardDate(null);

        // Create the HrEmpAwardInfo, which fails.

        restHrEmpAwardInfoMockMvc.perform(post("/api/hrEmpAwardInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAwardInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAwardInfo> hrEmpAwardInfos = hrEmpAwardInfoRepository.findAll();
        assertThat(hrEmpAwardInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAwardInfoRepository.findAll().size();
        // set the field null
        hrEmpAwardInfo.setActiveStatus(null);

        // Create the HrEmpAwardInfo, which fails.

        restHrEmpAwardInfoMockMvc.perform(post("/api/hrEmpAwardInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAwardInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAwardInfo> hrEmpAwardInfos = hrEmpAwardInfoRepository.findAll();
        assertThat(hrEmpAwardInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpAwardInfos() throws Exception {
        // Initialize the database
        hrEmpAwardInfoRepository.saveAndFlush(hrEmpAwardInfo);

        // Get all the hrEmpAwardInfos
        restHrEmpAwardInfoMockMvc.perform(get("/api/hrEmpAwardInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpAwardInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].awardName").value(hasItem(DEFAULT_AWARD_NAME.toString())))
                .andExpect(jsonPath("$.[*].awardArea").value(hasItem(DEFAULT_AWARD_AREA.toString())))
                .andExpect(jsonPath("$.[*].awardDate").value(hasItem(DEFAULT_AWARD_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].goOrderDocContentType").value(hasItem(DEFAULT_GO_ORDER_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].goOrderDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_GO_ORDER_DOC))))
                .andExpect(jsonPath("$.[*].goOrderDocName").value(hasItem(DEFAULT_GO_ORDER_DOC_NAME.toString())))
                .andExpect(jsonPath("$.[*].certDocContentType").value(hasItem(DEFAULT_CERT_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].certDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERT_DOC))))
                .andExpect(jsonPath("$.[*].certDocName").value(hasItem(DEFAULT_CERT_DOC_NAME.toString())))
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
    public void getHrEmpAwardInfo() throws Exception {
        // Initialize the database
        hrEmpAwardInfoRepository.saveAndFlush(hrEmpAwardInfo);

        // Get the hrEmpAwardInfo
        restHrEmpAwardInfoMockMvc.perform(get("/api/hrEmpAwardInfos/{id}", hrEmpAwardInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpAwardInfo.getId().intValue()))
            .andExpect(jsonPath("$.awardName").value(DEFAULT_AWARD_NAME.toString()))
            .andExpect(jsonPath("$.awardArea").value(DEFAULT_AWARD_AREA.toString()))
            .andExpect(jsonPath("$.awardDate").value(DEFAULT_AWARD_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.goOrderDocContentType").value(DEFAULT_GO_ORDER_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.goOrderDoc").value(Base64Utils.encodeToString(DEFAULT_GO_ORDER_DOC)))
            .andExpect(jsonPath("$.goOrderDocName").value(DEFAULT_GO_ORDER_DOC_NAME.toString()))
            .andExpect(jsonPath("$.certDocContentType").value(DEFAULT_CERT_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.certDoc").value(Base64Utils.encodeToString(DEFAULT_CERT_DOC)))
            .andExpect(jsonPath("$.certDocName").value(DEFAULT_CERT_DOC_NAME.toString()))
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
    public void getNonExistingHrEmpAwardInfo() throws Exception {
        // Get the hrEmpAwardInfo
        restHrEmpAwardInfoMockMvc.perform(get("/api/hrEmpAwardInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpAwardInfo() throws Exception {
        // Initialize the database
        hrEmpAwardInfoRepository.saveAndFlush(hrEmpAwardInfo);

		int databaseSizeBeforeUpdate = hrEmpAwardInfoRepository.findAll().size();

        // Update the hrEmpAwardInfo
        hrEmpAwardInfo.setAwardName(UPDATED_AWARD_NAME);
        hrEmpAwardInfo.setAwardArea(UPDATED_AWARD_AREA);
        hrEmpAwardInfo.setAwardDate(UPDATED_AWARD_DATE);
        hrEmpAwardInfo.setRemarks(UPDATED_REMARKS);
        hrEmpAwardInfo.setGoOrderDoc(UPDATED_GO_ORDER_DOC);
        hrEmpAwardInfo.setGoOrderDocContentType(UPDATED_GO_ORDER_DOC_CONTENT_TYPE);
        hrEmpAwardInfo.setGoOrderDocName(UPDATED_GO_ORDER_DOC_NAME);
        hrEmpAwardInfo.setCertDoc(UPDATED_CERT_DOC);
        hrEmpAwardInfo.setCertDocContentType(UPDATED_CERT_DOC_CONTENT_TYPE);
        hrEmpAwardInfo.setCertDocName(UPDATED_CERT_DOC_NAME);
        hrEmpAwardInfo.setLogId(UPDATED_LOG_ID);
        hrEmpAwardInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpAwardInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpAwardInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpAwardInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpAwardInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpAwardInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpAwardInfoMockMvc.perform(put("/api/hrEmpAwardInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAwardInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpAwardInfo in the database
        List<HrEmpAwardInfo> hrEmpAwardInfos = hrEmpAwardInfoRepository.findAll();
        assertThat(hrEmpAwardInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpAwardInfo testHrEmpAwardInfo = hrEmpAwardInfos.get(hrEmpAwardInfos.size() - 1);
        assertThat(testHrEmpAwardInfo.getAwardName()).isEqualTo(UPDATED_AWARD_NAME);
        assertThat(testHrEmpAwardInfo.getAwardArea()).isEqualTo(UPDATED_AWARD_AREA);
        assertThat(testHrEmpAwardInfo.getAwardDate()).isEqualTo(UPDATED_AWARD_DATE);
        assertThat(testHrEmpAwardInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testHrEmpAwardInfo.getGoOrderDoc()).isEqualTo(UPDATED_GO_ORDER_DOC);
        assertThat(testHrEmpAwardInfo.getGoOrderDocContentType()).isEqualTo(UPDATED_GO_ORDER_DOC_CONTENT_TYPE);
        assertThat(testHrEmpAwardInfo.getGoOrderDocName()).isEqualTo(UPDATED_GO_ORDER_DOC_NAME);
        assertThat(testHrEmpAwardInfo.getCertDoc()).isEqualTo(UPDATED_CERT_DOC);
        assertThat(testHrEmpAwardInfo.getCertDocContentType()).isEqualTo(UPDATED_CERT_DOC_CONTENT_TYPE);
        assertThat(testHrEmpAwardInfo.getCertDocName()).isEqualTo(UPDATED_CERT_DOC_NAME);
        assertThat(testHrEmpAwardInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpAwardInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpAwardInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpAwardInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpAwardInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpAwardInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpAwardInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpAwardInfo() throws Exception {
        // Initialize the database
        hrEmpAwardInfoRepository.saveAndFlush(hrEmpAwardInfo);

		int databaseSizeBeforeDelete = hrEmpAwardInfoRepository.findAll().size();

        // Get the hrEmpAwardInfo
        restHrEmpAwardInfoMockMvc.perform(delete("/api/hrEmpAwardInfos/{id}", hrEmpAwardInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpAwardInfo> hrEmpAwardInfos = hrEmpAwardInfoRepository.findAll();
        assertThat(hrEmpAwardInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
