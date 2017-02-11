package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpTransferApplInfo;
import gov.step.app.repository.HrEmpTransferApplInfoRepository;
import gov.step.app.repository.search.HrEmpTransferApplInfoSearchRepository;
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
 * Test class for the HrEmpTransferApplInfoResource REST controller.
 *
 * @see HrEmpTransferApplInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpTransferApplInfoResourceIntTest {

    private static final String DEFAULT_TRANSFER_REASON = "AAAAA";
    private static final String UPDATED_TRANSFER_REASON = "BBBBB";
    private static final String DEFAULT_OFFICE_ORDER_NUMBER = "AAAAA";
    private static final String UPDATED_OFFICE_ORDER_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_OFFICE_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OFFICE_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final Long DEFAULT_LOG_ID = 1L;
    private static final Long UPDATED_LOG_ID = 2L;

    private static final Long DEFAULT_LOG_STATUS = 1L;
    private static final Long UPDATED_LOG_STATUS = 2L;
    private static final String DEFAULT_LOG_COMMENTS = "AAAAA";
    private static final String UPDATED_LOG_COMMENTS = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private HrEmpTransferApplInfoRepository hrEmpTransferApplInfoRepository;

    @Inject
    private HrEmpTransferApplInfoSearchRepository hrEmpTransferApplInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpTransferApplInfoMockMvc;

    private HrEmpTransferApplInfo hrEmpTransferApplInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpTransferApplInfoResource hrEmpTransferApplInfoResource = new HrEmpTransferApplInfoResource();
        ReflectionTestUtils.setField(hrEmpTransferApplInfoResource, "hrEmpTransferApplInfoSearchRepository", hrEmpTransferApplInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpTransferApplInfoResource, "hrEmpTransferApplInfoRepository", hrEmpTransferApplInfoRepository);
        this.restHrEmpTransferApplInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpTransferApplInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpTransferApplInfo = new HrEmpTransferApplInfo();
        hrEmpTransferApplInfo.setTransferReason(DEFAULT_TRANSFER_REASON);
        hrEmpTransferApplInfo.setOfficeOrderNumber(DEFAULT_OFFICE_ORDER_NUMBER);
        hrEmpTransferApplInfo.setOfficeOrderDate(DEFAULT_OFFICE_ORDER_DATE);
        hrEmpTransferApplInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpTransferApplInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpTransferApplInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpTransferApplInfo.setLogComments(DEFAULT_LOG_COMMENTS);
        hrEmpTransferApplInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpTransferApplInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpTransferApplInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpTransferApplInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpTransferApplInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpTransferApplInfoRepository.findAll().size();

        // Create the HrEmpTransferApplInfo

        restHrEmpTransferApplInfoMockMvc.perform(post("/api/hrEmpTransferApplInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferApplInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpTransferApplInfo in the database
        List<HrEmpTransferApplInfo> hrEmpTransferApplInfos = hrEmpTransferApplInfoRepository.findAll();
        assertThat(hrEmpTransferApplInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpTransferApplInfo testHrEmpTransferApplInfo = hrEmpTransferApplInfos.get(hrEmpTransferApplInfos.size() - 1);
        assertThat(testHrEmpTransferApplInfo.getTransferReason()).isEqualTo(DEFAULT_TRANSFER_REASON);
        assertThat(testHrEmpTransferApplInfo.getOfficeOrderNumber()).isEqualTo(DEFAULT_OFFICE_ORDER_NUMBER);
        assertThat(testHrEmpTransferApplInfo.getOfficeOrderDate()).isEqualTo(DEFAULT_OFFICE_ORDER_DATE);
        assertThat(testHrEmpTransferApplInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpTransferApplInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpTransferApplInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpTransferApplInfo.getLogComments()).isEqualTo(DEFAULT_LOG_COMMENTS);
        assertThat(testHrEmpTransferApplInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpTransferApplInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpTransferApplInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpTransferApplInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkTransferReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferApplInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferApplInfo.setTransferReason(null);

        // Create the HrEmpTransferApplInfo, which fails.

        restHrEmpTransferApplInfoMockMvc.perform(post("/api/hrEmpTransferApplInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferApplInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferApplInfo> hrEmpTransferApplInfos = hrEmpTransferApplInfoRepository.findAll();
        assertThat(hrEmpTransferApplInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfficeOrderNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferApplInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferApplInfo.setOfficeOrderNumber(null);

        // Create the HrEmpTransferApplInfo, which fails.

        restHrEmpTransferApplInfoMockMvc.perform(post("/api/hrEmpTransferApplInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferApplInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferApplInfo> hrEmpTransferApplInfos = hrEmpTransferApplInfoRepository.findAll();
        assertThat(hrEmpTransferApplInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpTransferApplInfoRepository.findAll().size();
        // set the field null
        hrEmpTransferApplInfo.setActiveStatus(null);

        // Create the HrEmpTransferApplInfo, which fails.

        restHrEmpTransferApplInfoMockMvc.perform(post("/api/hrEmpTransferApplInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferApplInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpTransferApplInfo> hrEmpTransferApplInfos = hrEmpTransferApplInfoRepository.findAll();
        assertThat(hrEmpTransferApplInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpTransferApplInfos() throws Exception {
        // Initialize the database
        hrEmpTransferApplInfoRepository.saveAndFlush(hrEmpTransferApplInfo);

        // Get all the hrEmpTransferApplInfos
        restHrEmpTransferApplInfoMockMvc.perform(get("/api/hrEmpTransferApplInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpTransferApplInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].transferReason").value(hasItem(DEFAULT_TRANSFER_REASON.toString())))
                .andExpect(jsonPath("$.[*].officeOrderNumber").value(hasItem(DEFAULT_OFFICE_ORDER_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].officeOrderDate").value(hasItem(DEFAULT_OFFICE_ORDER_DATE.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].logId").value(hasItem(DEFAULT_LOG_ID.intValue())))
                .andExpect(jsonPath("$.[*].logStatus").value(hasItem(DEFAULT_LOG_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].logComments").value(hasItem(DEFAULT_LOG_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmpTransferApplInfo() throws Exception {
        // Initialize the database
        hrEmpTransferApplInfoRepository.saveAndFlush(hrEmpTransferApplInfo);

        // Get the hrEmpTransferApplInfo
        restHrEmpTransferApplInfoMockMvc.perform(get("/api/hrEmpTransferApplInfos/{id}", hrEmpTransferApplInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpTransferApplInfo.getId().intValue()))
            .andExpect(jsonPath("$.transferReason").value(DEFAULT_TRANSFER_REASON.toString()))
            .andExpect(jsonPath("$.officeOrderNumber").value(DEFAULT_OFFICE_ORDER_NUMBER.toString()))
            .andExpect(jsonPath("$.officeOrderDate").value(DEFAULT_OFFICE_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.logId").value(DEFAULT_LOG_ID.intValue()))
            .andExpect(jsonPath("$.logStatus").value(DEFAULT_LOG_STATUS.intValue()))
            .andExpect(jsonPath("$.logComments").value(DEFAULT_LOG_COMMENTS.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmpTransferApplInfo() throws Exception {
        // Get the hrEmpTransferApplInfo
        restHrEmpTransferApplInfoMockMvc.perform(get("/api/hrEmpTransferApplInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpTransferApplInfo() throws Exception {
        // Initialize the database
        hrEmpTransferApplInfoRepository.saveAndFlush(hrEmpTransferApplInfo);

		int databaseSizeBeforeUpdate = hrEmpTransferApplInfoRepository.findAll().size();

        // Update the hrEmpTransferApplInfo
        hrEmpTransferApplInfo.setTransferReason(UPDATED_TRANSFER_REASON);
        hrEmpTransferApplInfo.setOfficeOrderNumber(UPDATED_OFFICE_ORDER_NUMBER);
        hrEmpTransferApplInfo.setOfficeOrderDate(UPDATED_OFFICE_ORDER_DATE);
        hrEmpTransferApplInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpTransferApplInfo.setLogId(UPDATED_LOG_ID);
        hrEmpTransferApplInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpTransferApplInfo.setLogComments(UPDATED_LOG_COMMENTS);
        hrEmpTransferApplInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpTransferApplInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpTransferApplInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpTransferApplInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpTransferApplInfoMockMvc.perform(put("/api/hrEmpTransferApplInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpTransferApplInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpTransferApplInfo in the database
        List<HrEmpTransferApplInfo> hrEmpTransferApplInfos = hrEmpTransferApplInfoRepository.findAll();
        assertThat(hrEmpTransferApplInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpTransferApplInfo testHrEmpTransferApplInfo = hrEmpTransferApplInfos.get(hrEmpTransferApplInfos.size() - 1);
        assertThat(testHrEmpTransferApplInfo.getTransferReason()).isEqualTo(UPDATED_TRANSFER_REASON);
        assertThat(testHrEmpTransferApplInfo.getOfficeOrderNumber()).isEqualTo(UPDATED_OFFICE_ORDER_NUMBER);
        assertThat(testHrEmpTransferApplInfo.getOfficeOrderDate()).isEqualTo(UPDATED_OFFICE_ORDER_DATE);
        assertThat(testHrEmpTransferApplInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpTransferApplInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpTransferApplInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpTransferApplInfo.getLogComments()).isEqualTo(UPDATED_LOG_COMMENTS);
        assertThat(testHrEmpTransferApplInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpTransferApplInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpTransferApplInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpTransferApplInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpTransferApplInfo() throws Exception {
        // Initialize the database
        hrEmpTransferApplInfoRepository.saveAndFlush(hrEmpTransferApplInfo);

		int databaseSizeBeforeDelete = hrEmpTransferApplInfoRepository.findAll().size();

        // Get the hrEmpTransferApplInfo
        restHrEmpTransferApplInfoMockMvc.perform(delete("/api/hrEmpTransferApplInfos/{id}", hrEmpTransferApplInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpTransferApplInfo> hrEmpTransferApplInfos = hrEmpTransferApplInfoRepository.findAll();
        assertThat(hrEmpTransferApplInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
