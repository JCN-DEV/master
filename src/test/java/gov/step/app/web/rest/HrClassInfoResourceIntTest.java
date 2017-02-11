package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrClassInfo;
import gov.step.app.repository.HrClassInfoRepository;
import gov.step.app.repository.search.HrClassInfoSearchRepository;
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
 * Test class for the HrClassInfoResource REST controller.
 *
 * @see HrClassInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrClassInfoResourceIntTest {

    private static final String DEFAULT_CLASS_CODE = "AAAAA";
    private static final String UPDATED_CLASS_CODE = "BBBBB";
    private static final String DEFAULT_CLASS_NAME = "AAAAA";
    private static final String UPDATED_CLASS_NAME = "BBBBB";
    private static final String DEFAULT_CLASS_DETAIL = "AAAAA";
    private static final String UPDATED_CLASS_DETAIL = "BBBBB";

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
    private HrClassInfoRepository hrClassInfoRepository;

    @Inject
    private HrClassInfoSearchRepository hrClassInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrClassInfoMockMvc;

    private HrClassInfo hrClassInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrClassInfoResource hrClassInfoResource = new HrClassInfoResource();
        ReflectionTestUtils.setField(hrClassInfoResource, "hrClassInfoSearchRepository", hrClassInfoSearchRepository);
        ReflectionTestUtils.setField(hrClassInfoResource, "hrClassInfoRepository", hrClassInfoRepository);
        this.restHrClassInfoMockMvc = MockMvcBuilders.standaloneSetup(hrClassInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrClassInfo = new HrClassInfo();
        hrClassInfo.setClassCode(DEFAULT_CLASS_CODE);
        hrClassInfo.setClassName(DEFAULT_CLASS_NAME);
        hrClassInfo.setClassDetail(DEFAULT_CLASS_DETAIL);
        hrClassInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrClassInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrClassInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrClassInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrClassInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrClassInfo() throws Exception {
        int databaseSizeBeforeCreate = hrClassInfoRepository.findAll().size();

        // Create the HrClassInfo

        restHrClassInfoMockMvc.perform(post("/api/hrClassInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrClassInfo)))
                .andExpect(status().isCreated());

        // Validate the HrClassInfo in the database
        List<HrClassInfo> hrClassInfos = hrClassInfoRepository.findAll();
        assertThat(hrClassInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrClassInfo testHrClassInfo = hrClassInfos.get(hrClassInfos.size() - 1);
        assertThat(testHrClassInfo.getClassCode()).isEqualTo(DEFAULT_CLASS_CODE);
        assertThat(testHrClassInfo.getClassName()).isEqualTo(DEFAULT_CLASS_NAME);
        assertThat(testHrClassInfo.getClassDetail()).isEqualTo(DEFAULT_CLASS_DETAIL);
        assertThat(testHrClassInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrClassInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrClassInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrClassInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrClassInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkClassCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrClassInfoRepository.findAll().size();
        // set the field null
        hrClassInfo.setClassCode(null);

        // Create the HrClassInfo, which fails.

        restHrClassInfoMockMvc.perform(post("/api/hrClassInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrClassInfo)))
                .andExpect(status().isBadRequest());

        List<HrClassInfo> hrClassInfos = hrClassInfoRepository.findAll();
        assertThat(hrClassInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClassNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrClassInfoRepository.findAll().size();
        // set the field null
        hrClassInfo.setClassName(null);

        // Create the HrClassInfo, which fails.

        restHrClassInfoMockMvc.perform(post("/api/hrClassInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrClassInfo)))
                .andExpect(status().isBadRequest());

        List<HrClassInfo> hrClassInfos = hrClassInfoRepository.findAll();
        assertThat(hrClassInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrClassInfoRepository.findAll().size();
        // set the field null
        hrClassInfo.setActiveStatus(null);

        // Create the HrClassInfo, which fails.

        restHrClassInfoMockMvc.perform(post("/api/hrClassInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrClassInfo)))
                .andExpect(status().isBadRequest());

        List<HrClassInfo> hrClassInfos = hrClassInfoRepository.findAll();
        assertThat(hrClassInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrClassInfos() throws Exception {
        // Initialize the database
        hrClassInfoRepository.saveAndFlush(hrClassInfo);

        // Get all the hrClassInfos
        restHrClassInfoMockMvc.perform(get("/api/hrClassInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrClassInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].classCode").value(hasItem(DEFAULT_CLASS_CODE.toString())))
                .andExpect(jsonPath("$.[*].className").value(hasItem(DEFAULT_CLASS_NAME.toString())))
                .andExpect(jsonPath("$.[*].classDetail").value(hasItem(DEFAULT_CLASS_DETAIL.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrClassInfo() throws Exception {
        // Initialize the database
        hrClassInfoRepository.saveAndFlush(hrClassInfo);

        // Get the hrClassInfo
        restHrClassInfoMockMvc.perform(get("/api/hrClassInfos/{id}", hrClassInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrClassInfo.getId().intValue()))
            .andExpect(jsonPath("$.classCode").value(DEFAULT_CLASS_CODE.toString()))
            .andExpect(jsonPath("$.className").value(DEFAULT_CLASS_NAME.toString()))
            .andExpect(jsonPath("$.classDetail").value(DEFAULT_CLASS_DETAIL.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrClassInfo() throws Exception {
        // Get the hrClassInfo
        restHrClassInfoMockMvc.perform(get("/api/hrClassInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrClassInfo() throws Exception {
        // Initialize the database
        hrClassInfoRepository.saveAndFlush(hrClassInfo);

		int databaseSizeBeforeUpdate = hrClassInfoRepository.findAll().size();

        // Update the hrClassInfo
        hrClassInfo.setClassCode(UPDATED_CLASS_CODE);
        hrClassInfo.setClassName(UPDATED_CLASS_NAME);
        hrClassInfo.setClassDetail(UPDATED_CLASS_DETAIL);
        hrClassInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrClassInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrClassInfo.setCreateBy(UPDATED_CREATE_BY);
        hrClassInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrClassInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrClassInfoMockMvc.perform(put("/api/hrClassInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrClassInfo)))
                .andExpect(status().isOk());

        // Validate the HrClassInfo in the database
        List<HrClassInfo> hrClassInfos = hrClassInfoRepository.findAll();
        assertThat(hrClassInfos).hasSize(databaseSizeBeforeUpdate);
        HrClassInfo testHrClassInfo = hrClassInfos.get(hrClassInfos.size() - 1);
        assertThat(testHrClassInfo.getClassCode()).isEqualTo(UPDATED_CLASS_CODE);
        assertThat(testHrClassInfo.getClassName()).isEqualTo(UPDATED_CLASS_NAME);
        assertThat(testHrClassInfo.getClassDetail()).isEqualTo(UPDATED_CLASS_DETAIL);
        assertThat(testHrClassInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrClassInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrClassInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrClassInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrClassInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrClassInfo() throws Exception {
        // Initialize the database
        hrClassInfoRepository.saveAndFlush(hrClassInfo);

		int databaseSizeBeforeDelete = hrClassInfoRepository.findAll().size();

        // Get the hrClassInfo
        restHrClassInfoMockMvc.perform(delete("/api/hrClassInfos/{id}", hrClassInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrClassInfo> hrClassInfos = hrClassInfoRepository.findAll();
        assertThat(hrClassInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
