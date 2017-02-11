package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmplTypeInfo;
import gov.step.app.repository.HrEmplTypeInfoRepository;
import gov.step.app.repository.search.HrEmplTypeInfoSearchRepository;
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
 * Test class for the HrEmplTypeInfoResource REST controller.
 *
 * @see HrEmplTypeInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmplTypeInfoResourceIntTest {

    private static final String DEFAULT_TYPE_CODE = "AAAAA";
    private static final String UPDATED_TYPE_CODE = "BBBBB";
    private static final String DEFAULT_TYPE_NAME = "AAAAA";
    private static final String UPDATED_TYPE_NAME = "BBBBB";
    private static final String DEFAULT_TYPE_DETAIL = "AAAAA";
    private static final String UPDATED_TYPE_DETAIL = "BBBBB";

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
    private HrEmplTypeInfoRepository hrEmplTypeInfoRepository;

    @Inject
    private HrEmplTypeInfoSearchRepository hrEmplTypeInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmplTypeInfoMockMvc;

    private HrEmplTypeInfo hrEmplTypeInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmplTypeInfoResource hrEmplTypeInfoResource = new HrEmplTypeInfoResource();
        ReflectionTestUtils.setField(hrEmplTypeInfoResource, "hrEmplTypeInfoSearchRepository", hrEmplTypeInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmplTypeInfoResource, "hrEmplTypeInfoRepository", hrEmplTypeInfoRepository);
        this.restHrEmplTypeInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmplTypeInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmplTypeInfo = new HrEmplTypeInfo();
        hrEmplTypeInfo.setTypeCode(DEFAULT_TYPE_CODE);
        hrEmplTypeInfo.setTypeName(DEFAULT_TYPE_NAME);
        hrEmplTypeInfo.setTypeDetail(DEFAULT_TYPE_DETAIL);
        hrEmplTypeInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmplTypeInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmplTypeInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmplTypeInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmplTypeInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmplTypeInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmplTypeInfoRepository.findAll().size();

        // Create the HrEmplTypeInfo

        restHrEmplTypeInfoMockMvc.perform(post("/api/hrEmplTypeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmplTypeInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmplTypeInfo in the database
        List<HrEmplTypeInfo> hrEmplTypeInfos = hrEmplTypeInfoRepository.findAll();
        assertThat(hrEmplTypeInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmplTypeInfo testHrEmplTypeInfo = hrEmplTypeInfos.get(hrEmplTypeInfos.size() - 1);
        assertThat(testHrEmplTypeInfo.getTypeCode()).isEqualTo(DEFAULT_TYPE_CODE);
        assertThat(testHrEmplTypeInfo.getTypeName()).isEqualTo(DEFAULT_TYPE_NAME);
        assertThat(testHrEmplTypeInfo.getTypeDetail()).isEqualTo(DEFAULT_TYPE_DETAIL);
        assertThat(testHrEmplTypeInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmplTypeInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmplTypeInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmplTypeInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmplTypeInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmplTypeInfoRepository.findAll().size();
        // set the field null
        hrEmplTypeInfo.setTypeCode(null);

        // Create the HrEmplTypeInfo, which fails.

        restHrEmplTypeInfoMockMvc.perform(post("/api/hrEmplTypeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmplTypeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmplTypeInfo> hrEmplTypeInfos = hrEmplTypeInfoRepository.findAll();
        assertThat(hrEmplTypeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmplTypeInfoRepository.findAll().size();
        // set the field null
        hrEmplTypeInfo.setTypeName(null);

        // Create the HrEmplTypeInfo, which fails.

        restHrEmplTypeInfoMockMvc.perform(post("/api/hrEmplTypeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmplTypeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmplTypeInfo> hrEmplTypeInfos = hrEmplTypeInfoRepository.findAll();
        assertThat(hrEmplTypeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmplTypeInfoRepository.findAll().size();
        // set the field null
        hrEmplTypeInfo.setActiveStatus(null);

        // Create the HrEmplTypeInfo, which fails.

        restHrEmplTypeInfoMockMvc.perform(post("/api/hrEmplTypeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmplTypeInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmplTypeInfo> hrEmplTypeInfos = hrEmplTypeInfoRepository.findAll();
        assertThat(hrEmplTypeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmplTypeInfos() throws Exception {
        // Initialize the database
        hrEmplTypeInfoRepository.saveAndFlush(hrEmplTypeInfo);

        // Get all the hrEmplTypeInfos
        restHrEmplTypeInfoMockMvc.perform(get("/api/hrEmplTypeInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmplTypeInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].typeCode").value(hasItem(DEFAULT_TYPE_CODE.toString())))
                .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].typeDetail").value(hasItem(DEFAULT_TYPE_DETAIL.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmplTypeInfo() throws Exception {
        // Initialize the database
        hrEmplTypeInfoRepository.saveAndFlush(hrEmplTypeInfo);

        // Get the hrEmplTypeInfo
        restHrEmplTypeInfoMockMvc.perform(get("/api/hrEmplTypeInfos/{id}", hrEmplTypeInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmplTypeInfo.getId().intValue()))
            .andExpect(jsonPath("$.typeCode").value(DEFAULT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.typeName").value(DEFAULT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.typeDetail").value(DEFAULT_TYPE_DETAIL.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmplTypeInfo() throws Exception {
        // Get the hrEmplTypeInfo
        restHrEmplTypeInfoMockMvc.perform(get("/api/hrEmplTypeInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmplTypeInfo() throws Exception {
        // Initialize the database
        hrEmplTypeInfoRepository.saveAndFlush(hrEmplTypeInfo);

		int databaseSizeBeforeUpdate = hrEmplTypeInfoRepository.findAll().size();

        // Update the hrEmplTypeInfo
        hrEmplTypeInfo.setTypeCode(UPDATED_TYPE_CODE);
        hrEmplTypeInfo.setTypeName(UPDATED_TYPE_NAME);
        hrEmplTypeInfo.setTypeDetail(UPDATED_TYPE_DETAIL);
        hrEmplTypeInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmplTypeInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmplTypeInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmplTypeInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmplTypeInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmplTypeInfoMockMvc.perform(put("/api/hrEmplTypeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmplTypeInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmplTypeInfo in the database
        List<HrEmplTypeInfo> hrEmplTypeInfos = hrEmplTypeInfoRepository.findAll();
        assertThat(hrEmplTypeInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmplTypeInfo testHrEmplTypeInfo = hrEmplTypeInfos.get(hrEmplTypeInfos.size() - 1);
        assertThat(testHrEmplTypeInfo.getTypeCode()).isEqualTo(UPDATED_TYPE_CODE);
        assertThat(testHrEmplTypeInfo.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testHrEmplTypeInfo.getTypeDetail()).isEqualTo(UPDATED_TYPE_DETAIL);
        assertThat(testHrEmplTypeInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmplTypeInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmplTypeInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmplTypeInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmplTypeInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmplTypeInfo() throws Exception {
        // Initialize the database
        hrEmplTypeInfoRepository.saveAndFlush(hrEmplTypeInfo);

		int databaseSizeBeforeDelete = hrEmplTypeInfoRepository.findAll().size();

        // Get the hrEmplTypeInfo
        restHrEmplTypeInfoMockMvc.perform(delete("/api/hrEmplTypeInfos/{id}", hrEmplTypeInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmplTypeInfo> hrEmplTypeInfos = hrEmplTypeInfoRepository.findAll();
        assertThat(hrEmplTypeInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
