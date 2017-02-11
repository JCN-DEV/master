package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpAdvIncrementInfo;
import gov.step.app.repository.HrEmpAdvIncrementInfoRepository;
import gov.step.app.repository.search.HrEmpAdvIncrementInfoSearchRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrEmpAdvIncrementInfoResource REST controller.
 *
 * @see HrEmpAdvIncrementInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpAdvIncrementInfoResourceIntTest {

    private static final String DEFAULT_POST_NAME = "AAAAA";
    private static final String UPDATED_POST_NAME = "BBBBB";
    private static final String DEFAULT_PURPOSE = "AAAAA";
    private static final String UPDATED_PURPOSE = "BBBBB";

    private static final BigDecimal DEFAULT_INCREMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INCREMENT_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_ORDER_NUMBER = "AAAAA";
    private static final String UPDATED_ORDER_NUMBER = "BBBBB";
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final LocalDate DEFAULT_GO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_GO_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GO_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_GO_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GO_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_GO_DOC_NAME = "AAAAA";
    private static final String UPDATED_GO_DOC_NAME = "BBBBB";

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
    private HrEmpAdvIncrementInfoRepository hrEmpAdvIncrementInfoRepository;

    @Inject
    private HrEmpAdvIncrementInfoSearchRepository hrEmpAdvIncrementInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpAdvIncrementInfoMockMvc;

    private HrEmpAdvIncrementInfo hrEmpAdvIncrementInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpAdvIncrementInfoResource hrEmpAdvIncrementInfoResource = new HrEmpAdvIncrementInfoResource();
        ReflectionTestUtils.setField(hrEmpAdvIncrementInfoResource, "hrEmpAdvIncrementInfoSearchRepository", hrEmpAdvIncrementInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpAdvIncrementInfoResource, "hrEmpAdvIncrementInfoRepository", hrEmpAdvIncrementInfoRepository);
        this.restHrEmpAdvIncrementInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpAdvIncrementInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpAdvIncrementInfo = new HrEmpAdvIncrementInfo();
        hrEmpAdvIncrementInfo.setPostName(DEFAULT_POST_NAME);
        hrEmpAdvIncrementInfo.setPurpose(DEFAULT_PURPOSE);
        hrEmpAdvIncrementInfo.setIncrementAmount(DEFAULT_INCREMENT_AMOUNT);
        hrEmpAdvIncrementInfo.setOrderDate(DEFAULT_ORDER_DATE);
        hrEmpAdvIncrementInfo.setOrderNumber(DEFAULT_ORDER_NUMBER);
        hrEmpAdvIncrementInfo.setRemarks(DEFAULT_REMARKS);
        hrEmpAdvIncrementInfo.setGoDate(DEFAULT_GO_DATE);
        hrEmpAdvIncrementInfo.setGoDoc(DEFAULT_GO_DOC);
        hrEmpAdvIncrementInfo.setGoDocContentType(DEFAULT_GO_DOC_CONTENT_TYPE);
        hrEmpAdvIncrementInfo.setGoDocName(DEFAULT_GO_DOC_NAME);
        hrEmpAdvIncrementInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpAdvIncrementInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpAdvIncrementInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpAdvIncrementInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpAdvIncrementInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpAdvIncrementInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpAdvIncrementInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpAdvIncrementInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpAdvIncrementInfoRepository.findAll().size();

        // Create the HrEmpAdvIncrementInfo

        restHrEmpAdvIncrementInfoMockMvc.perform(post("/api/hrEmpAdvIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAdvIncrementInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpAdvIncrementInfo in the database
        List<HrEmpAdvIncrementInfo> hrEmpAdvIncrementInfos = hrEmpAdvIncrementInfoRepository.findAll();
        assertThat(hrEmpAdvIncrementInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpAdvIncrementInfo testHrEmpAdvIncrementInfo = hrEmpAdvIncrementInfos.get(hrEmpAdvIncrementInfos.size() - 1);
        assertThat(testHrEmpAdvIncrementInfo.getPostName()).isEqualTo(DEFAULT_POST_NAME);
        assertThat(testHrEmpAdvIncrementInfo.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testHrEmpAdvIncrementInfo.getIncrementAmount()).isEqualTo(DEFAULT_INCREMENT_AMOUNT);
        assertThat(testHrEmpAdvIncrementInfo.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testHrEmpAdvIncrementInfo.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testHrEmpAdvIncrementInfo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testHrEmpAdvIncrementInfo.getGoDate()).isEqualTo(DEFAULT_GO_DATE);
        assertThat(testHrEmpAdvIncrementInfo.getGoDoc()).isEqualTo(DEFAULT_GO_DOC);
        assertThat(testHrEmpAdvIncrementInfo.getGoDocContentType()).isEqualTo(DEFAULT_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpAdvIncrementInfo.getGoDocName()).isEqualTo(DEFAULT_GO_DOC_NAME);
        assertThat(testHrEmpAdvIncrementInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpAdvIncrementInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpAdvIncrementInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpAdvIncrementInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpAdvIncrementInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpAdvIncrementInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpAdvIncrementInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkPostNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAdvIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpAdvIncrementInfo.setPostName(null);

        // Create the HrEmpAdvIncrementInfo, which fails.

        restHrEmpAdvIncrementInfoMockMvc.perform(post("/api/hrEmpAdvIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAdvIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAdvIncrementInfo> hrEmpAdvIncrementInfos = hrEmpAdvIncrementInfoRepository.findAll();
        assertThat(hrEmpAdvIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPurposeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAdvIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpAdvIncrementInfo.setPurpose(null);

        // Create the HrEmpAdvIncrementInfo, which fails.

        restHrEmpAdvIncrementInfoMockMvc.perform(post("/api/hrEmpAdvIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAdvIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAdvIncrementInfo> hrEmpAdvIncrementInfos = hrEmpAdvIncrementInfoRepository.findAll();
        assertThat(hrEmpAdvIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncrementAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAdvIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpAdvIncrementInfo.setIncrementAmount(null);

        // Create the HrEmpAdvIncrementInfo, which fails.

        restHrEmpAdvIncrementInfoMockMvc.perform(post("/api/hrEmpAdvIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAdvIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAdvIncrementInfo> hrEmpAdvIncrementInfos = hrEmpAdvIncrementInfoRepository.findAll();
        assertThat(hrEmpAdvIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAdvIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpAdvIncrementInfo.setOrderDate(null);

        // Create the HrEmpAdvIncrementInfo, which fails.

        restHrEmpAdvIncrementInfoMockMvc.perform(post("/api/hrEmpAdvIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAdvIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAdvIncrementInfo> hrEmpAdvIncrementInfos = hrEmpAdvIncrementInfoRepository.findAll();
        assertThat(hrEmpAdvIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAdvIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpAdvIncrementInfo.setOrderNumber(null);

        // Create the HrEmpAdvIncrementInfo, which fails.

        restHrEmpAdvIncrementInfoMockMvc.perform(post("/api/hrEmpAdvIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAdvIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAdvIncrementInfo> hrEmpAdvIncrementInfos = hrEmpAdvIncrementInfoRepository.findAll();
        assertThat(hrEmpAdvIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAdvIncrementInfoRepository.findAll().size();
        // set the field null
        hrEmpAdvIncrementInfo.setActiveStatus(null);

        // Create the HrEmpAdvIncrementInfo, which fails.

        restHrEmpAdvIncrementInfoMockMvc.perform(post("/api/hrEmpAdvIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAdvIncrementInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAdvIncrementInfo> hrEmpAdvIncrementInfos = hrEmpAdvIncrementInfoRepository.findAll();
        assertThat(hrEmpAdvIncrementInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpAdvIncrementInfos() throws Exception {
        // Initialize the database
        hrEmpAdvIncrementInfoRepository.saveAndFlush(hrEmpAdvIncrementInfo);

        // Get all the hrEmpAdvIncrementInfos
        restHrEmpAdvIncrementInfoMockMvc.perform(get("/api/hrEmpAdvIncrementInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpAdvIncrementInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].postName").value(hasItem(DEFAULT_POST_NAME.toString())))
                .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
                .andExpect(jsonPath("$.[*].incrementAmount").value(hasItem(DEFAULT_INCREMENT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
                .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].goDate").value(hasItem(DEFAULT_GO_DATE.toString())))
                .andExpect(jsonPath("$.[*].goDocContentType").value(hasItem(DEFAULT_GO_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].goDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_GO_DOC))))
                .andExpect(jsonPath("$.[*].goDocName").value(hasItem(DEFAULT_GO_DOC_NAME.toString())))
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
    public void getHrEmpAdvIncrementInfo() throws Exception {
        // Initialize the database
        hrEmpAdvIncrementInfoRepository.saveAndFlush(hrEmpAdvIncrementInfo);

        // Get the hrEmpAdvIncrementInfo
        restHrEmpAdvIncrementInfoMockMvc.perform(get("/api/hrEmpAdvIncrementInfos/{id}", hrEmpAdvIncrementInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpAdvIncrementInfo.getId().intValue()))
            .andExpect(jsonPath("$.postName").value(DEFAULT_POST_NAME.toString()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE.toString()))
            .andExpect(jsonPath("$.incrementAmount").value(DEFAULT_INCREMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.goDate").value(DEFAULT_GO_DATE.toString()))
            .andExpect(jsonPath("$.goDocContentType").value(DEFAULT_GO_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.goDoc").value(Base64Utils.encodeToString(DEFAULT_GO_DOC)))
            .andExpect(jsonPath("$.goDocName").value(DEFAULT_GO_DOC_NAME.toString()))
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
    public void getNonExistingHrEmpAdvIncrementInfo() throws Exception {
        // Get the hrEmpAdvIncrementInfo
        restHrEmpAdvIncrementInfoMockMvc.perform(get("/api/hrEmpAdvIncrementInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpAdvIncrementInfo() throws Exception {
        // Initialize the database
        hrEmpAdvIncrementInfoRepository.saveAndFlush(hrEmpAdvIncrementInfo);

		int databaseSizeBeforeUpdate = hrEmpAdvIncrementInfoRepository.findAll().size();

        // Update the hrEmpAdvIncrementInfo
        hrEmpAdvIncrementInfo.setPostName(UPDATED_POST_NAME);
        hrEmpAdvIncrementInfo.setPurpose(UPDATED_PURPOSE);
        hrEmpAdvIncrementInfo.setIncrementAmount(UPDATED_INCREMENT_AMOUNT);
        hrEmpAdvIncrementInfo.setOrderDate(UPDATED_ORDER_DATE);
        hrEmpAdvIncrementInfo.setOrderNumber(UPDATED_ORDER_NUMBER);
        hrEmpAdvIncrementInfo.setRemarks(UPDATED_REMARKS);
        hrEmpAdvIncrementInfo.setGoDate(UPDATED_GO_DATE);
        hrEmpAdvIncrementInfo.setGoDoc(UPDATED_GO_DOC);
        hrEmpAdvIncrementInfo.setGoDocContentType(UPDATED_GO_DOC_CONTENT_TYPE);
        hrEmpAdvIncrementInfo.setGoDocName(UPDATED_GO_DOC_NAME);
        hrEmpAdvIncrementInfo.setLogId(UPDATED_LOG_ID);
        hrEmpAdvIncrementInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpAdvIncrementInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpAdvIncrementInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpAdvIncrementInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpAdvIncrementInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpAdvIncrementInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpAdvIncrementInfoMockMvc.perform(put("/api/hrEmpAdvIncrementInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAdvIncrementInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpAdvIncrementInfo in the database
        List<HrEmpAdvIncrementInfo> hrEmpAdvIncrementInfos = hrEmpAdvIncrementInfoRepository.findAll();
        assertThat(hrEmpAdvIncrementInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpAdvIncrementInfo testHrEmpAdvIncrementInfo = hrEmpAdvIncrementInfos.get(hrEmpAdvIncrementInfos.size() - 1);
        assertThat(testHrEmpAdvIncrementInfo.getPostName()).isEqualTo(UPDATED_POST_NAME);
        assertThat(testHrEmpAdvIncrementInfo.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testHrEmpAdvIncrementInfo.getIncrementAmount()).isEqualTo(UPDATED_INCREMENT_AMOUNT);
        assertThat(testHrEmpAdvIncrementInfo.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testHrEmpAdvIncrementInfo.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testHrEmpAdvIncrementInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testHrEmpAdvIncrementInfo.getGoDate()).isEqualTo(UPDATED_GO_DATE);
        assertThat(testHrEmpAdvIncrementInfo.getGoDoc()).isEqualTo(UPDATED_GO_DOC);
        assertThat(testHrEmpAdvIncrementInfo.getGoDocContentType()).isEqualTo(UPDATED_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpAdvIncrementInfo.getGoDocName()).isEqualTo(UPDATED_GO_DOC_NAME);
        assertThat(testHrEmpAdvIncrementInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpAdvIncrementInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpAdvIncrementInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpAdvIncrementInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpAdvIncrementInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpAdvIncrementInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpAdvIncrementInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpAdvIncrementInfo() throws Exception {
        // Initialize the database
        hrEmpAdvIncrementInfoRepository.saveAndFlush(hrEmpAdvIncrementInfo);

		int databaseSizeBeforeDelete = hrEmpAdvIncrementInfoRepository.findAll().size();

        // Get the hrEmpAdvIncrementInfo
        restHrEmpAdvIncrementInfoMockMvc.perform(delete("/api/hrEmpAdvIncrementInfos/{id}", hrEmpAdvIncrementInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpAdvIncrementInfo> hrEmpAdvIncrementInfos = hrEmpAdvIncrementInfoRepository.findAll();
        assertThat(hrEmpAdvIncrementInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
