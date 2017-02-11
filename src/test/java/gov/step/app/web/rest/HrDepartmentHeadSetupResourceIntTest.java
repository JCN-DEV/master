package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrDepartmentHeadSetup;
import gov.step.app.repository.HrDepartmentHeadSetupRepository;
import gov.step.app.repository.search.HrDepartmentHeadSetupSearchRepository;
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
 * Test class for the HrDepartmentHeadSetupResource REST controller.
 *
 * @see HrDepartmentHeadSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrDepartmentHeadSetupResourceIntTest {

    private static final String DEFAULT_DEPARTMENT_CODE = "AAAAAA";
    private static final String UPDATED_DEPARTMENT_CODE = "BBBBBB";
    private static final String DEFAULT_DEPARTMENT_NAME = "AAAAA";
    private static final String UPDATED_DEPARTMENT_NAME = "BBBBB";
    private static final String DEFAULT_DEPARTMENT_DETAIL = "AAAAA";
    private static final String UPDATED_DEPARTMENT_DETAIL = "BBBBB";

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
    private HrDepartmentHeadSetupRepository hrDepartmentHeadSetupRepository;

    @Inject
    private HrDepartmentHeadSetupSearchRepository hrDepartmentHeadSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrDepartmentHeadSetupMockMvc;

    private HrDepartmentHeadSetup hrDepartmentHeadSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrDepartmentHeadSetupResource hrDepartmentHeadSetupResource = new HrDepartmentHeadSetupResource();
        ReflectionTestUtils.setField(hrDepartmentHeadSetupResource, "hrDepartmentHeadSetupSearchRepository", hrDepartmentHeadSetupSearchRepository);
        ReflectionTestUtils.setField(hrDepartmentHeadSetupResource, "hrDepartmentHeadSetupRepository", hrDepartmentHeadSetupRepository);
        this.restHrDepartmentHeadSetupMockMvc = MockMvcBuilders.standaloneSetup(hrDepartmentHeadSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrDepartmentHeadSetup = new HrDepartmentHeadSetup();
        hrDepartmentHeadSetup.setDepartmentCode(DEFAULT_DEPARTMENT_CODE);
        hrDepartmentHeadSetup.setDepartmentName(DEFAULT_DEPARTMENT_NAME);
        hrDepartmentHeadSetup.setDepartmentDetail(DEFAULT_DEPARTMENT_DETAIL);
        hrDepartmentHeadSetup.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrDepartmentHeadSetup.setCreateDate(DEFAULT_CREATE_DATE);
        hrDepartmentHeadSetup.setCreateBy(DEFAULT_CREATE_BY);
        hrDepartmentHeadSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrDepartmentHeadSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrDepartmentHeadSetup() throws Exception {
        int databaseSizeBeforeCreate = hrDepartmentHeadSetupRepository.findAll().size();

        // Create the HrDepartmentHeadSetup

        restHrDepartmentHeadSetupMockMvc.perform(post("/api/hrDepartmentHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentHeadSetup)))
                .andExpect(status().isCreated());

        // Validate the HrDepartmentHeadSetup in the database
        List<HrDepartmentHeadSetup> hrDepartmentHeadSetups = hrDepartmentHeadSetupRepository.findAll();
        assertThat(hrDepartmentHeadSetups).hasSize(databaseSizeBeforeCreate + 1);
        HrDepartmentHeadSetup testHrDepartmentHeadSetup = hrDepartmentHeadSetups.get(hrDepartmentHeadSetups.size() - 1);
        assertThat(testHrDepartmentHeadSetup.getDepartmentCode()).isEqualTo(DEFAULT_DEPARTMENT_CODE);
        assertThat(testHrDepartmentHeadSetup.getDepartmentName()).isEqualTo(DEFAULT_DEPARTMENT_NAME);
        assertThat(testHrDepartmentHeadSetup.getDepartmentDetail()).isEqualTo(DEFAULT_DEPARTMENT_DETAIL);
        assertThat(testHrDepartmentHeadSetup.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrDepartmentHeadSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrDepartmentHeadSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrDepartmentHeadSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrDepartmentHeadSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkDepartmentCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDepartmentHeadSetupRepository.findAll().size();
        // set the field null
        hrDepartmentHeadSetup.setDepartmentCode(null);

        // Create the HrDepartmentHeadSetup, which fails.

        restHrDepartmentHeadSetupMockMvc.perform(post("/api/hrDepartmentHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentHeadSetup)))
                .andExpect(status().isBadRequest());

        List<HrDepartmentHeadSetup> hrDepartmentHeadSetups = hrDepartmentHeadSetupRepository.findAll();
        assertThat(hrDepartmentHeadSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDepartmentHeadSetupRepository.findAll().size();
        // set the field null
        hrDepartmentHeadSetup.setActiveStatus(null);

        // Create the HrDepartmentHeadSetup, which fails.

        restHrDepartmentHeadSetupMockMvc.perform(post("/api/hrDepartmentHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentHeadSetup)))
                .andExpect(status().isBadRequest());

        List<HrDepartmentHeadSetup> hrDepartmentHeadSetups = hrDepartmentHeadSetupRepository.findAll();
        assertThat(hrDepartmentHeadSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrDepartmentHeadSetups() throws Exception {
        // Initialize the database
        hrDepartmentHeadSetupRepository.saveAndFlush(hrDepartmentHeadSetup);

        // Get all the hrDepartmentHeadSetups
        restHrDepartmentHeadSetupMockMvc.perform(get("/api/hrDepartmentHeadSetups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrDepartmentHeadSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].departmentCode").value(hasItem(DEFAULT_DEPARTMENT_CODE.toString())))
                .andExpect(jsonPath("$.[*].departmentName").value(hasItem(DEFAULT_DEPARTMENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].departmentDetail").value(hasItem(DEFAULT_DEPARTMENT_DETAIL.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrDepartmentHeadSetup() throws Exception {
        // Initialize the database
        hrDepartmentHeadSetupRepository.saveAndFlush(hrDepartmentHeadSetup);

        // Get the hrDepartmentHeadSetup
        restHrDepartmentHeadSetupMockMvc.perform(get("/api/hrDepartmentHeadSetups/{id}", hrDepartmentHeadSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrDepartmentHeadSetup.getId().intValue()))
            .andExpect(jsonPath("$.departmentCode").value(DEFAULT_DEPARTMENT_CODE.toString()))
            .andExpect(jsonPath("$.departmentName").value(DEFAULT_DEPARTMENT_NAME.toString()))
            .andExpect(jsonPath("$.departmentDetail").value(DEFAULT_DEPARTMENT_DETAIL.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrDepartmentHeadSetup() throws Exception {
        // Get the hrDepartmentHeadSetup
        restHrDepartmentHeadSetupMockMvc.perform(get("/api/hrDepartmentHeadSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrDepartmentHeadSetup() throws Exception {
        // Initialize the database
        hrDepartmentHeadSetupRepository.saveAndFlush(hrDepartmentHeadSetup);

		int databaseSizeBeforeUpdate = hrDepartmentHeadSetupRepository.findAll().size();

        // Update the hrDepartmentHeadSetup
        hrDepartmentHeadSetup.setDepartmentCode(UPDATED_DEPARTMENT_CODE);
        hrDepartmentHeadSetup.setDepartmentName(UPDATED_DEPARTMENT_NAME);
        hrDepartmentHeadSetup.setDepartmentDetail(UPDATED_DEPARTMENT_DETAIL);
        hrDepartmentHeadSetup.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrDepartmentHeadSetup.setCreateDate(UPDATED_CREATE_DATE);
        hrDepartmentHeadSetup.setCreateBy(UPDATED_CREATE_BY);
        hrDepartmentHeadSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        hrDepartmentHeadSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restHrDepartmentHeadSetupMockMvc.perform(put("/api/hrDepartmentHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentHeadSetup)))
                .andExpect(status().isOk());

        // Validate the HrDepartmentHeadSetup in the database
        List<HrDepartmentHeadSetup> hrDepartmentHeadSetups = hrDepartmentHeadSetupRepository.findAll();
        assertThat(hrDepartmentHeadSetups).hasSize(databaseSizeBeforeUpdate);
        HrDepartmentHeadSetup testHrDepartmentHeadSetup = hrDepartmentHeadSetups.get(hrDepartmentHeadSetups.size() - 1);
        assertThat(testHrDepartmentHeadSetup.getDepartmentCode()).isEqualTo(UPDATED_DEPARTMENT_CODE);
        assertThat(testHrDepartmentHeadSetup.getDepartmentName()).isEqualTo(UPDATED_DEPARTMENT_NAME);
        assertThat(testHrDepartmentHeadSetup.getDepartmentDetail()).isEqualTo(UPDATED_DEPARTMENT_DETAIL);
        assertThat(testHrDepartmentHeadSetup.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrDepartmentHeadSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrDepartmentHeadSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrDepartmentHeadSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrDepartmentHeadSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrDepartmentHeadSetup() throws Exception {
        // Initialize the database
        hrDepartmentHeadSetupRepository.saveAndFlush(hrDepartmentHeadSetup);

		int databaseSizeBeforeDelete = hrDepartmentHeadSetupRepository.findAll().size();

        // Get the hrDepartmentHeadSetup
        restHrDepartmentHeadSetupMockMvc.perform(delete("/api/hrDepartmentHeadSetups/{id}", hrDepartmentHeadSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrDepartmentHeadSetup> hrDepartmentHeadSetups = hrDepartmentHeadSetupRepository.findAll();
        assertThat(hrDepartmentHeadSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
