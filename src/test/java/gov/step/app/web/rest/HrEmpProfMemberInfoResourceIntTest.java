package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpProfMemberInfo;
import gov.step.app.repository.HrEmpProfMemberInfoRepository;
import gov.step.app.repository.search.HrEmpProfMemberInfoSearchRepository;
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
 * Test class for the HrEmpProfMemberInfoResource REST controller.
 *
 * @see HrEmpProfMemberInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpProfMemberInfoResourceIntTest {

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBB";
    private static final String DEFAULT_MEMBERSHIP_NUMBER = "AAAAA";
    private static final String UPDATED_MEMBERSHIP_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_MEMBERSHIP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MEMBERSHIP_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private HrEmpProfMemberInfoRepository hrEmpProfMemberInfoRepository;

    @Inject
    private HrEmpProfMemberInfoSearchRepository hrEmpProfMemberInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpProfMemberInfoMockMvc;

    private HrEmpProfMemberInfo hrEmpProfMemberInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpProfMemberInfoResource hrEmpProfMemberInfoResource = new HrEmpProfMemberInfoResource();
        ReflectionTestUtils.setField(hrEmpProfMemberInfoResource, "hrEmpProfMemberInfoSearchRepository", hrEmpProfMemberInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpProfMemberInfoResource, "hrEmpProfMemberInfoRepository", hrEmpProfMemberInfoRepository);
        this.restHrEmpProfMemberInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpProfMemberInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpProfMemberInfo = new HrEmpProfMemberInfo();
        hrEmpProfMemberInfo.setOrganizationName(DEFAULT_ORGANIZATION_NAME);
        hrEmpProfMemberInfo.setMembershipNumber(DEFAULT_MEMBERSHIP_NUMBER);
        hrEmpProfMemberInfo.setMembershipDate(DEFAULT_MEMBERSHIP_DATE);
        hrEmpProfMemberInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpProfMemberInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpProfMemberInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpProfMemberInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpProfMemberInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpProfMemberInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpProfMemberInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpProfMemberInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpProfMemberInfoRepository.findAll().size();

        // Create the HrEmpProfMemberInfo

        restHrEmpProfMemberInfoMockMvc.perform(post("/api/hrEmpProfMemberInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpProfMemberInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpProfMemberInfo in the database
        List<HrEmpProfMemberInfo> hrEmpProfMemberInfos = hrEmpProfMemberInfoRepository.findAll();
        assertThat(hrEmpProfMemberInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpProfMemberInfo testHrEmpProfMemberInfo = hrEmpProfMemberInfos.get(hrEmpProfMemberInfos.size() - 1);
        assertThat(testHrEmpProfMemberInfo.getOrganizationName()).isEqualTo(DEFAULT_ORGANIZATION_NAME);
        assertThat(testHrEmpProfMemberInfo.getMembershipNumber()).isEqualTo(DEFAULT_MEMBERSHIP_NUMBER);
        assertThat(testHrEmpProfMemberInfo.getMembershipDate()).isEqualTo(DEFAULT_MEMBERSHIP_DATE);
        assertThat(testHrEmpProfMemberInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpProfMemberInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpProfMemberInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpProfMemberInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpProfMemberInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpProfMemberInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpProfMemberInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkOrganizationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpProfMemberInfoRepository.findAll().size();
        // set the field null
        hrEmpProfMemberInfo.setOrganizationName(null);

        // Create the HrEmpProfMemberInfo, which fails.

        restHrEmpProfMemberInfoMockMvc.perform(post("/api/hrEmpProfMemberInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpProfMemberInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpProfMemberInfo> hrEmpProfMemberInfos = hrEmpProfMemberInfoRepository.findAll();
        assertThat(hrEmpProfMemberInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMembershipNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpProfMemberInfoRepository.findAll().size();
        // set the field null
        hrEmpProfMemberInfo.setMembershipNumber(null);

        // Create the HrEmpProfMemberInfo, which fails.

        restHrEmpProfMemberInfoMockMvc.perform(post("/api/hrEmpProfMemberInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpProfMemberInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpProfMemberInfo> hrEmpProfMemberInfos = hrEmpProfMemberInfoRepository.findAll();
        assertThat(hrEmpProfMemberInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpProfMemberInfoRepository.findAll().size();
        // set the field null
        hrEmpProfMemberInfo.setActiveStatus(null);

        // Create the HrEmpProfMemberInfo, which fails.

        restHrEmpProfMemberInfoMockMvc.perform(post("/api/hrEmpProfMemberInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpProfMemberInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpProfMemberInfo> hrEmpProfMemberInfos = hrEmpProfMemberInfoRepository.findAll();
        assertThat(hrEmpProfMemberInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpProfMemberInfos() throws Exception {
        // Initialize the database
        hrEmpProfMemberInfoRepository.saveAndFlush(hrEmpProfMemberInfo);

        // Get all the hrEmpProfMemberInfos
        restHrEmpProfMemberInfoMockMvc.perform(get("/api/hrEmpProfMemberInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpProfMemberInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME.toString())))
                .andExpect(jsonPath("$.[*].membershipNumber").value(hasItem(DEFAULT_MEMBERSHIP_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].membershipDate").value(hasItem(DEFAULT_MEMBERSHIP_DATE.toString())))
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
    public void getHrEmpProfMemberInfo() throws Exception {
        // Initialize the database
        hrEmpProfMemberInfoRepository.saveAndFlush(hrEmpProfMemberInfo);

        // Get the hrEmpProfMemberInfo
        restHrEmpProfMemberInfoMockMvc.perform(get("/api/hrEmpProfMemberInfos/{id}", hrEmpProfMemberInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpProfMemberInfo.getId().intValue()))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME.toString()))
            .andExpect(jsonPath("$.membershipNumber").value(DEFAULT_MEMBERSHIP_NUMBER.toString()))
            .andExpect(jsonPath("$.membershipDate").value(DEFAULT_MEMBERSHIP_DATE.toString()))
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
    public void getNonExistingHrEmpProfMemberInfo() throws Exception {
        // Get the hrEmpProfMemberInfo
        restHrEmpProfMemberInfoMockMvc.perform(get("/api/hrEmpProfMemberInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpProfMemberInfo() throws Exception {
        // Initialize the database
        hrEmpProfMemberInfoRepository.saveAndFlush(hrEmpProfMemberInfo);

		int databaseSizeBeforeUpdate = hrEmpProfMemberInfoRepository.findAll().size();

        // Update the hrEmpProfMemberInfo
        hrEmpProfMemberInfo.setOrganizationName(UPDATED_ORGANIZATION_NAME);
        hrEmpProfMemberInfo.setMembershipNumber(UPDATED_MEMBERSHIP_NUMBER);
        hrEmpProfMemberInfo.setMembershipDate(UPDATED_MEMBERSHIP_DATE);
        hrEmpProfMemberInfo.setLogId(UPDATED_LOG_ID);
        hrEmpProfMemberInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpProfMemberInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpProfMemberInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpProfMemberInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpProfMemberInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpProfMemberInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpProfMemberInfoMockMvc.perform(put("/api/hrEmpProfMemberInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpProfMemberInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpProfMemberInfo in the database
        List<HrEmpProfMemberInfo> hrEmpProfMemberInfos = hrEmpProfMemberInfoRepository.findAll();
        assertThat(hrEmpProfMemberInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpProfMemberInfo testHrEmpProfMemberInfo = hrEmpProfMemberInfos.get(hrEmpProfMemberInfos.size() - 1);
        assertThat(testHrEmpProfMemberInfo.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testHrEmpProfMemberInfo.getMembershipNumber()).isEqualTo(UPDATED_MEMBERSHIP_NUMBER);
        assertThat(testHrEmpProfMemberInfo.getMembershipDate()).isEqualTo(UPDATED_MEMBERSHIP_DATE);
        assertThat(testHrEmpProfMemberInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpProfMemberInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpProfMemberInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpProfMemberInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpProfMemberInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpProfMemberInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpProfMemberInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpProfMemberInfo() throws Exception {
        // Initialize the database
        hrEmpProfMemberInfoRepository.saveAndFlush(hrEmpProfMemberInfo);

		int databaseSizeBeforeDelete = hrEmpProfMemberInfoRepository.findAll().size();

        // Get the hrEmpProfMemberInfo
        restHrEmpProfMemberInfoMockMvc.perform(delete("/api/hrEmpProfMemberInfos/{id}", hrEmpProfMemberInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpProfMemberInfo> hrEmpProfMemberInfos = hrEmpProfMemberInfoRepository.findAll();
        assertThat(hrEmpProfMemberInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
