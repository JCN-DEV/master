package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpActingDutyInfo;
import gov.step.app.repository.HrEmpActingDutyInfoRepository;
import gov.step.app.repository.search.HrEmpActingDutyInfoSearchRepository;
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
 * Test class for the HrEmpActingDutyInfoResource REST controller.
 *
 * @see HrEmpActingDutyInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpActingDutyInfoResourceIntTest {

    private static final String DEFAULT_TO_INSTITUTION = "AAAAA";
    private static final String UPDATED_TO_INSTITUTION = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_TO_DEPARTMENT = "AAAAA";
    private static final String UPDATED_TO_DEPARTMENT = "BBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_OFFICE_ORDER_NUMBER = "AAAAA";
    private static final String UPDATED_OFFICE_ORDER_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_OFFICE_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OFFICE_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_WORK_ON_ACTING_DUTY = "AAAAA";
    private static final String UPDATED_WORK_ON_ACTING_DUTY = "BBBBB";
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

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
    private HrEmpActingDutyInfoRepository hrEmpActingDutyInfoRepository;

    @Inject
    private HrEmpActingDutyInfoSearchRepository hrEmpActingDutyInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpActingDutyInfoMockMvc;

    private HrEmpActingDutyInfo hrEmpActingDutyInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpActingDutyInfoResource hrEmpActingDutyInfoResource = new HrEmpActingDutyInfoResource();
        ReflectionTestUtils.setField(hrEmpActingDutyInfoResource, "hrEmpActingDutyInfoSearchRepository", hrEmpActingDutyInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpActingDutyInfoResource, "hrEmpActingDutyInfoRepository", hrEmpActingDutyInfoRepository);
        this.restHrEmpActingDutyInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpActingDutyInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpActingDutyInfo = new HrEmpActingDutyInfo();
        hrEmpActingDutyInfo.setToInstitution(DEFAULT_TO_INSTITUTION);
        hrEmpActingDutyInfo.setDesignation(DEFAULT_DESIGNATION);
        hrEmpActingDutyInfo.setToDepartment(DEFAULT_TO_DEPARTMENT);
        hrEmpActingDutyInfo.setFromDate(DEFAULT_FROM_DATE);
        hrEmpActingDutyInfo.setToDate(DEFAULT_TO_DATE);
        hrEmpActingDutyInfo.setOfficeOrderNumber(DEFAULT_OFFICE_ORDER_NUMBER);
        hrEmpActingDutyInfo.setOfficeOrderDate(DEFAULT_OFFICE_ORDER_DATE);
        hrEmpActingDutyInfo.setWorkOnActingDuty(DEFAULT_WORK_ON_ACTING_DUTY);
        hrEmpActingDutyInfo.setComments(DEFAULT_COMMENTS);
        hrEmpActingDutyInfo.setGoDate(DEFAULT_GO_DATE);
        hrEmpActingDutyInfo.setGoDoc(DEFAULT_GO_DOC);
        hrEmpActingDutyInfo.setGoDocContentType(DEFAULT_GO_DOC_CONTENT_TYPE);
        hrEmpActingDutyInfo.setGoDocName(DEFAULT_GO_DOC_NAME);
        hrEmpActingDutyInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpActingDutyInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpActingDutyInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpActingDutyInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpActingDutyInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpActingDutyInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpActingDutyInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpActingDutyInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpActingDutyInfoRepository.findAll().size();

        // Create the HrEmpActingDutyInfo

        restHrEmpActingDutyInfoMockMvc.perform(post("/api/hrEmpActingDutyInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpActingDutyInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpActingDutyInfo in the database
        List<HrEmpActingDutyInfo> hrEmpActingDutyInfos = hrEmpActingDutyInfoRepository.findAll();
        assertThat(hrEmpActingDutyInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpActingDutyInfo testHrEmpActingDutyInfo = hrEmpActingDutyInfos.get(hrEmpActingDutyInfos.size() - 1);
        assertThat(testHrEmpActingDutyInfo.getToInstitution()).isEqualTo(DEFAULT_TO_INSTITUTION);
        assertThat(testHrEmpActingDutyInfo.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testHrEmpActingDutyInfo.getToDepartment()).isEqualTo(DEFAULT_TO_DEPARTMENT);
        assertThat(testHrEmpActingDutyInfo.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testHrEmpActingDutyInfo.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testHrEmpActingDutyInfo.getOfficeOrderNumber()).isEqualTo(DEFAULT_OFFICE_ORDER_NUMBER);
        assertThat(testHrEmpActingDutyInfo.getOfficeOrderDate()).isEqualTo(DEFAULT_OFFICE_ORDER_DATE);
        assertThat(testHrEmpActingDutyInfo.getWorkOnActingDuty()).isEqualTo(DEFAULT_WORK_ON_ACTING_DUTY);
        assertThat(testHrEmpActingDutyInfo.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testHrEmpActingDutyInfo.getGoDate()).isEqualTo(DEFAULT_GO_DATE);
        assertThat(testHrEmpActingDutyInfo.getGoDoc()).isEqualTo(DEFAULT_GO_DOC);
        assertThat(testHrEmpActingDutyInfo.getGoDocContentType()).isEqualTo(DEFAULT_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpActingDutyInfo.getGoDocName()).isEqualTo(DEFAULT_GO_DOC_NAME);
        assertThat(testHrEmpActingDutyInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpActingDutyInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpActingDutyInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpActingDutyInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpActingDutyInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpActingDutyInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpActingDutyInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkToInstitutionIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpActingDutyInfoRepository.findAll().size();
        // set the field null
        hrEmpActingDutyInfo.setToInstitution(null);

        // Create the HrEmpActingDutyInfo, which fails.

        restHrEmpActingDutyInfoMockMvc.perform(post("/api/hrEmpActingDutyInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpActingDutyInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpActingDutyInfo> hrEmpActingDutyInfos = hrEmpActingDutyInfoRepository.findAll();
        assertThat(hrEmpActingDutyInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpActingDutyInfoRepository.findAll().size();
        // set the field null
        hrEmpActingDutyInfo.setDesignation(null);

        // Create the HrEmpActingDutyInfo, which fails.

        restHrEmpActingDutyInfoMockMvc.perform(post("/api/hrEmpActingDutyInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpActingDutyInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpActingDutyInfo> hrEmpActingDutyInfos = hrEmpActingDutyInfoRepository.findAll();
        assertThat(hrEmpActingDutyInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpActingDutyInfoRepository.findAll().size();
        // set the field null
        hrEmpActingDutyInfo.setFromDate(null);

        // Create the HrEmpActingDutyInfo, which fails.

        restHrEmpActingDutyInfoMockMvc.perform(post("/api/hrEmpActingDutyInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpActingDutyInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpActingDutyInfo> hrEmpActingDutyInfos = hrEmpActingDutyInfoRepository.findAll();
        assertThat(hrEmpActingDutyInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpActingDutyInfoRepository.findAll().size();
        // set the field null
        hrEmpActingDutyInfo.setActiveStatus(null);

        // Create the HrEmpActingDutyInfo, which fails.

        restHrEmpActingDutyInfoMockMvc.perform(post("/api/hrEmpActingDutyInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpActingDutyInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpActingDutyInfo> hrEmpActingDutyInfos = hrEmpActingDutyInfoRepository.findAll();
        assertThat(hrEmpActingDutyInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpActingDutyInfos() throws Exception {
        // Initialize the database
        hrEmpActingDutyInfoRepository.saveAndFlush(hrEmpActingDutyInfo);

        // Get all the hrEmpActingDutyInfos
        restHrEmpActingDutyInfoMockMvc.perform(get("/api/hrEmpActingDutyInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpActingDutyInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].toInstitution").value(hasItem(DEFAULT_TO_INSTITUTION.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].toDepartment").value(hasItem(DEFAULT_TO_DEPARTMENT.toString())))
                .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
                .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
                .andExpect(jsonPath("$.[*].officeOrderNumber").value(hasItem(DEFAULT_OFFICE_ORDER_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].officeOrderDate").value(hasItem(DEFAULT_OFFICE_ORDER_DATE.toString())))
                .andExpect(jsonPath("$.[*].workOnActingDuty").value(hasItem(DEFAULT_WORK_ON_ACTING_DUTY.toString())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
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
    public void getHrEmpActingDutyInfo() throws Exception {
        // Initialize the database
        hrEmpActingDutyInfoRepository.saveAndFlush(hrEmpActingDutyInfo);

        // Get the hrEmpActingDutyInfo
        restHrEmpActingDutyInfoMockMvc.perform(get("/api/hrEmpActingDutyInfos/{id}", hrEmpActingDutyInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpActingDutyInfo.getId().intValue()))
            .andExpect(jsonPath("$.toInstitution").value(DEFAULT_TO_INSTITUTION.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.toDepartment").value(DEFAULT_TO_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.officeOrderNumber").value(DEFAULT_OFFICE_ORDER_NUMBER.toString()))
            .andExpect(jsonPath("$.officeOrderDate").value(DEFAULT_OFFICE_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.workOnActingDuty").value(DEFAULT_WORK_ON_ACTING_DUTY.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
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
    public void getNonExistingHrEmpActingDutyInfo() throws Exception {
        // Get the hrEmpActingDutyInfo
        restHrEmpActingDutyInfoMockMvc.perform(get("/api/hrEmpActingDutyInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpActingDutyInfo() throws Exception {
        // Initialize the database
        hrEmpActingDutyInfoRepository.saveAndFlush(hrEmpActingDutyInfo);

		int databaseSizeBeforeUpdate = hrEmpActingDutyInfoRepository.findAll().size();

        // Update the hrEmpActingDutyInfo
        hrEmpActingDutyInfo.setToInstitution(UPDATED_TO_INSTITUTION);
        hrEmpActingDutyInfo.setDesignation(UPDATED_DESIGNATION);
        hrEmpActingDutyInfo.setToDepartment(UPDATED_TO_DEPARTMENT);
        hrEmpActingDutyInfo.setFromDate(UPDATED_FROM_DATE);
        hrEmpActingDutyInfo.setToDate(UPDATED_TO_DATE);
        hrEmpActingDutyInfo.setOfficeOrderNumber(UPDATED_OFFICE_ORDER_NUMBER);
        hrEmpActingDutyInfo.setOfficeOrderDate(UPDATED_OFFICE_ORDER_DATE);
        hrEmpActingDutyInfo.setWorkOnActingDuty(UPDATED_WORK_ON_ACTING_DUTY);
        hrEmpActingDutyInfo.setComments(UPDATED_COMMENTS);
        hrEmpActingDutyInfo.setGoDate(UPDATED_GO_DATE);
        hrEmpActingDutyInfo.setGoDoc(UPDATED_GO_DOC);
        hrEmpActingDutyInfo.setGoDocContentType(UPDATED_GO_DOC_CONTENT_TYPE);
        hrEmpActingDutyInfo.setGoDocName(UPDATED_GO_DOC_NAME);
        hrEmpActingDutyInfo.setLogId(UPDATED_LOG_ID);
        hrEmpActingDutyInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpActingDutyInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpActingDutyInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpActingDutyInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpActingDutyInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpActingDutyInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpActingDutyInfoMockMvc.perform(put("/api/hrEmpActingDutyInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpActingDutyInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpActingDutyInfo in the database
        List<HrEmpActingDutyInfo> hrEmpActingDutyInfos = hrEmpActingDutyInfoRepository.findAll();
        assertThat(hrEmpActingDutyInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpActingDutyInfo testHrEmpActingDutyInfo = hrEmpActingDutyInfos.get(hrEmpActingDutyInfos.size() - 1);
        assertThat(testHrEmpActingDutyInfo.getToInstitution()).isEqualTo(UPDATED_TO_INSTITUTION);
        assertThat(testHrEmpActingDutyInfo.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testHrEmpActingDutyInfo.getToDepartment()).isEqualTo(UPDATED_TO_DEPARTMENT);
        assertThat(testHrEmpActingDutyInfo.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testHrEmpActingDutyInfo.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testHrEmpActingDutyInfo.getOfficeOrderNumber()).isEqualTo(UPDATED_OFFICE_ORDER_NUMBER);
        assertThat(testHrEmpActingDutyInfo.getOfficeOrderDate()).isEqualTo(UPDATED_OFFICE_ORDER_DATE);
        assertThat(testHrEmpActingDutyInfo.getWorkOnActingDuty()).isEqualTo(UPDATED_WORK_ON_ACTING_DUTY);
        assertThat(testHrEmpActingDutyInfo.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testHrEmpActingDutyInfo.getGoDate()).isEqualTo(UPDATED_GO_DATE);
        assertThat(testHrEmpActingDutyInfo.getGoDoc()).isEqualTo(UPDATED_GO_DOC);
        assertThat(testHrEmpActingDutyInfo.getGoDocContentType()).isEqualTo(UPDATED_GO_DOC_CONTENT_TYPE);
        assertThat(testHrEmpActingDutyInfo.getGoDocName()).isEqualTo(UPDATED_GO_DOC_NAME);
        assertThat(testHrEmpActingDutyInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpActingDutyInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpActingDutyInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpActingDutyInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpActingDutyInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpActingDutyInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpActingDutyInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpActingDutyInfo() throws Exception {
        // Initialize the database
        hrEmpActingDutyInfoRepository.saveAndFlush(hrEmpActingDutyInfo);

		int databaseSizeBeforeDelete = hrEmpActingDutyInfoRepository.findAll().size();

        // Get the hrEmpActingDutyInfo
        restHrEmpActingDutyInfoMockMvc.perform(delete("/api/hrEmpActingDutyInfos/{id}", hrEmpActingDutyInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpActingDutyInfo> hrEmpActingDutyInfos = hrEmpActingDutyInfoRepository.findAll();
        assertThat(hrEmpActingDutyInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
