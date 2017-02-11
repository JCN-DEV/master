package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpForeignTourInfo;
import gov.step.app.repository.HrEmpForeignTourInfoRepository;
import gov.step.app.repository.search.HrEmpForeignTourInfoSearchRepository;
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
 * Test class for the HrEmpForeignTourInfoResource REST controller.
 *
 * @see HrEmpForeignTourInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpForeignTourInfoResourceIntTest {

    private static final String DEFAULT_PURPOSE = "AAAAA";
    private static final String UPDATED_PURPOSE = "BBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_COUNTRY_NAME = "AAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBB";
    private static final String DEFAULT_OFFICE_ORDER_NUMBER = "AAAAA";
    private static final String UPDATED_OFFICE_ORDER_NUMBER = "BBBBB";
    private static final String DEFAULT_FUND_SOURCE = "AAAAA";
    private static final String UPDATED_FUND_SOURCE = "BBBBB";

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
    private HrEmpForeignTourInfoRepository hrEmpForeignTourInfoRepository;

    @Inject
    private HrEmpForeignTourInfoSearchRepository hrEmpForeignTourInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpForeignTourInfoMockMvc;

    private HrEmpForeignTourInfo hrEmpForeignTourInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpForeignTourInfoResource hrEmpForeignTourInfoResource = new HrEmpForeignTourInfoResource();
        ReflectionTestUtils.setField(hrEmpForeignTourInfoResource, "hrEmpForeignTourInfoSearchRepository", hrEmpForeignTourInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpForeignTourInfoResource, "hrEmpForeignTourInfoRepository", hrEmpForeignTourInfoRepository);
        this.restHrEmpForeignTourInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpForeignTourInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpForeignTourInfo = new HrEmpForeignTourInfo();
        hrEmpForeignTourInfo.setPurpose(DEFAULT_PURPOSE);
        hrEmpForeignTourInfo.setFromDate(DEFAULT_FROM_DATE);
        hrEmpForeignTourInfo.setToDate(DEFAULT_TO_DATE);
        hrEmpForeignTourInfo.setCountryName(DEFAULT_COUNTRY_NAME);
        hrEmpForeignTourInfo.setOfficeOrderNumber(DEFAULT_OFFICE_ORDER_NUMBER);
        hrEmpForeignTourInfo.setFundSource(DEFAULT_FUND_SOURCE);
        hrEmpForeignTourInfo.setGoDate(DEFAULT_GO_DATE);
        hrEmpForeignTourInfo.setGoDoc(DEFAULT_GO_DOC);
        hrEmpForeignTourInfo.setGoDocContentType(DEFAULT_GO_DOC_CONTENT_TYPE);
        hrEmpForeignTourInfo.setGoDocName(DEFAULT_GO_DOC_NAME);
        hrEmpForeignTourInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpForeignTourInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpForeignTourInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpForeignTourInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpForeignTourInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpForeignTourInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpForeignTourInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpForeignTourInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpForeignTourInfoRepository.findAll().size();

        // Create the HrEmpForeignTourInfo

        restHrEmpForeignTourInfoMockMvc.perform(post("/api/hrEmpForeignTourInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpForeignTourInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpForeignTourInfo in the database
        List<HrEmpForeignTourInfo> hrEmpForeignTourInfos = hrEmpForeignTourInfoRepository.findAll();
        assertThat(hrEmpForeignTourInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpForeignTourInfo testHrEmpForeignTourInfo = hrEmpForeignTourInfos.get(hrEmpForeignTourInfos.size() - 1);
        assertThat(testHrEmpForeignTourInfo.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testHrEmpForeignTourInfo.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testHrEmpForeignTourInfo.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testHrEmpForeignTourInfo.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testHrEmpForeignTourInfo.getOfficeOrderNumber()).isEqualTo(DEFAULT_OFFICE_ORDER_NUMBER);
        assertThat(testHrEmpForeignTourInfo.getFundSource()).isEqualTo(DEFAULT_FUND_SOURCE);
        assertThat(testHrEmpForeignTourInfo.getGoDate()).isEqualTo(DEFAULT_GO_DATE);
        assertThat(testHrEmpForeignTourInfo.getGoDoc()).isEqualTo(DEFAULT_GO_DOC);
        assertThat(testHrEmpForeignTourInfo.getGoDocContentType()).isEqualTo(DEFAULT_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpForeignTourInfo.getGoDocName()).isEqualTo(DEFAULT_GO_DOC_NAME);
        assertThat(testHrEmpForeignTourInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpForeignTourInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpForeignTourInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpForeignTourInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpForeignTourInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpForeignTourInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpForeignTourInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkPurposeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpForeignTourInfoRepository.findAll().size();
        // set the field null
        hrEmpForeignTourInfo.setPurpose(null);

        // Create the HrEmpForeignTourInfo, which fails.

        restHrEmpForeignTourInfoMockMvc.perform(post("/api/hrEmpForeignTourInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpForeignTourInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpForeignTourInfo> hrEmpForeignTourInfos = hrEmpForeignTourInfoRepository.findAll();
        assertThat(hrEmpForeignTourInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpForeignTourInfoRepository.findAll().size();
        // set the field null
        hrEmpForeignTourInfo.setFromDate(null);

        // Create the HrEmpForeignTourInfo, which fails.

        restHrEmpForeignTourInfoMockMvc.perform(post("/api/hrEmpForeignTourInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpForeignTourInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpForeignTourInfo> hrEmpForeignTourInfos = hrEmpForeignTourInfoRepository.findAll();
        assertThat(hrEmpForeignTourInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpForeignTourInfoRepository.findAll().size();
        // set the field null
        hrEmpForeignTourInfo.setToDate(null);

        // Create the HrEmpForeignTourInfo, which fails.

        restHrEmpForeignTourInfoMockMvc.perform(post("/api/hrEmpForeignTourInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpForeignTourInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpForeignTourInfo> hrEmpForeignTourInfos = hrEmpForeignTourInfoRepository.findAll();
        assertThat(hrEmpForeignTourInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpForeignTourInfoRepository.findAll().size();
        // set the field null
        hrEmpForeignTourInfo.setCountryName(null);

        // Create the HrEmpForeignTourInfo, which fails.

        restHrEmpForeignTourInfoMockMvc.perform(post("/api/hrEmpForeignTourInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpForeignTourInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpForeignTourInfo> hrEmpForeignTourInfos = hrEmpForeignTourInfoRepository.findAll();
        assertThat(hrEmpForeignTourInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpForeignTourInfoRepository.findAll().size();
        // set the field null
        hrEmpForeignTourInfo.setActiveStatus(null);

        // Create the HrEmpForeignTourInfo, which fails.

        restHrEmpForeignTourInfoMockMvc.perform(post("/api/hrEmpForeignTourInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpForeignTourInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpForeignTourInfo> hrEmpForeignTourInfos = hrEmpForeignTourInfoRepository.findAll();
        assertThat(hrEmpForeignTourInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpForeignTourInfos() throws Exception {
        // Initialize the database
        hrEmpForeignTourInfoRepository.saveAndFlush(hrEmpForeignTourInfo);

        // Get all the hrEmpForeignTourInfos
        restHrEmpForeignTourInfoMockMvc.perform(get("/api/hrEmpForeignTourInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpForeignTourInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME.toString())))
                .andExpect(jsonPath("$.[*].officeOrderNumber").value(hasItem(DEFAULT_OFFICE_ORDER_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].fundSource").value(hasItem(DEFAULT_FUND_SOURCE.toString())))
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
    public void getHrEmpForeignTourInfo() throws Exception {
        // Initialize the database
        hrEmpForeignTourInfoRepository.saveAndFlush(hrEmpForeignTourInfo);

        // Get the hrEmpForeignTourInfo
        restHrEmpForeignTourInfoMockMvc.perform(get("/api/hrEmpForeignTourInfos/{id}", hrEmpForeignTourInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpForeignTourInfo.getId().intValue()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME.toString()))
            .andExpect(jsonPath("$.officeOrderNumber").value(DEFAULT_OFFICE_ORDER_NUMBER.toString()))
            .andExpect(jsonPath("$.fundSource").value(DEFAULT_FUND_SOURCE.toString()))
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
    public void getNonExistingHrEmpForeignTourInfo() throws Exception {
        // Get the hrEmpForeignTourInfo
        restHrEmpForeignTourInfoMockMvc.perform(get("/api/hrEmpForeignTourInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpForeignTourInfo() throws Exception {
        // Initialize the database
        hrEmpForeignTourInfoRepository.saveAndFlush(hrEmpForeignTourInfo);

		int databaseSizeBeforeUpdate = hrEmpForeignTourInfoRepository.findAll().size();

        // Update the hrEmpForeignTourInfo
        hrEmpForeignTourInfo.setPurpose(UPDATED_PURPOSE);
        hrEmpForeignTourInfo.setFromDate(UPDATED_FROM_DATE);
        hrEmpForeignTourInfo.setToDate(UPDATED_TO_DATE);
        hrEmpForeignTourInfo.setCountryName(UPDATED_COUNTRY_NAME);
        hrEmpForeignTourInfo.setOfficeOrderNumber(UPDATED_OFFICE_ORDER_NUMBER);
        hrEmpForeignTourInfo.setFundSource(UPDATED_FUND_SOURCE);
        hrEmpForeignTourInfo.setGoDate(UPDATED_GO_DATE);
        hrEmpForeignTourInfo.setGoDoc(UPDATED_GO_DOC);
        hrEmpForeignTourInfo.setGoDocContentType(UPDATED_GO_DOC_CONTENT_TYPE);
        hrEmpForeignTourInfo.setGoDocName(UPDATED_GO_DOC_NAME);
        hrEmpForeignTourInfo.setLogId(UPDATED_LOG_ID);
        hrEmpForeignTourInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpForeignTourInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpForeignTourInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpForeignTourInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpForeignTourInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpForeignTourInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpForeignTourInfoMockMvc.perform(put("/api/hrEmpForeignTourInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpForeignTourInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpForeignTourInfo in the database
        List<HrEmpForeignTourInfo> hrEmpForeignTourInfos = hrEmpForeignTourInfoRepository.findAll();
        assertThat(hrEmpForeignTourInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpForeignTourInfo testHrEmpForeignTourInfo = hrEmpForeignTourInfos.get(hrEmpForeignTourInfos.size() - 1);
        assertThat(testHrEmpForeignTourInfo.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testHrEmpForeignTourInfo.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testHrEmpForeignTourInfo.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testHrEmpForeignTourInfo.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testHrEmpForeignTourInfo.getOfficeOrderNumber()).isEqualTo(UPDATED_OFFICE_ORDER_NUMBER);
        assertThat(testHrEmpForeignTourInfo.getFundSource()).isEqualTo(UPDATED_FUND_SOURCE);
        assertThat(testHrEmpForeignTourInfo.getGoDate()).isEqualTo(UPDATED_GO_DATE);
        assertThat(testHrEmpForeignTourInfo.getGoDoc()).isEqualTo(UPDATED_GO_DOC);
        assertThat(testHrEmpForeignTourInfo.getGoDocContentType()).isEqualTo(UPDATED_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpForeignTourInfo.getGoDocName()).isEqualTo(UPDATED_GO_DOC_NAME);
        assertThat(testHrEmpForeignTourInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpForeignTourInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpForeignTourInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpForeignTourInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpForeignTourInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpForeignTourInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpForeignTourInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpForeignTourInfo() throws Exception {
        // Initialize the database
        hrEmpForeignTourInfoRepository.saveAndFlush(hrEmpForeignTourInfo);

		int databaseSizeBeforeDelete = hrEmpForeignTourInfoRepository.findAll().size();

        // Get the hrEmpForeignTourInfo
        restHrEmpForeignTourInfoMockMvc.perform(delete("/api/hrEmpForeignTourInfos/{id}", hrEmpForeignTourInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpForeignTourInfo> hrEmpForeignTourInfos = hrEmpForeignTourInfoRepository.findAll();
        assertThat(hrEmpForeignTourInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
