package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrDesignationHeadSetup;
import gov.step.app.repository.HrDesignationHeadSetupRepository;
import gov.step.app.repository.search.HrDesignationHeadSetupSearchRepository;
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
 * Test class for the HrDesignationHeadSetupResource REST controller.
 *
 * @see HrDesignationHeadSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrDesignationHeadSetupResourceIntTest {

    private static final String DEFAULT_DESIGNATION_CODE = "AAAAAA";
    private static final String UPDATED_DESIGNATION_CODE = "BBBBBB";
    private static final String DEFAULT_DESIGNATION_NAME = "AAAAA";
    private static final String UPDATED_DESIGNATION_NAME = "BBBBB";
    private static final String DEFAULT_DESIGNATION_DETAIL = "AAAAA";
    private static final String UPDATED_DESIGNATION_DETAIL = "BBBBB";

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
    private HrDesignationHeadSetupRepository hrDesignationHeadSetupRepository;

    @Inject
    private HrDesignationHeadSetupSearchRepository hrDesignationHeadSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrDesignationHeadSetupMockMvc;

    private HrDesignationHeadSetup hrDesignationHeadSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrDesignationHeadSetupResource hrDesignationHeadSetupResource = new HrDesignationHeadSetupResource();
        ReflectionTestUtils.setField(hrDesignationHeadSetupResource, "hrDesignationHeadSetupSearchRepository", hrDesignationHeadSetupSearchRepository);
        ReflectionTestUtils.setField(hrDesignationHeadSetupResource, "hrDesignationHeadSetupRepository", hrDesignationHeadSetupRepository);
        this.restHrDesignationHeadSetupMockMvc = MockMvcBuilders.standaloneSetup(hrDesignationHeadSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrDesignationHeadSetup = new HrDesignationHeadSetup();
        hrDesignationHeadSetup.setDesignationCode(DEFAULT_DESIGNATION_CODE);
        hrDesignationHeadSetup.setDesignationName(DEFAULT_DESIGNATION_NAME);
        hrDesignationHeadSetup.setDesignationDetail(DEFAULT_DESIGNATION_DETAIL);
        hrDesignationHeadSetup.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrDesignationHeadSetup.setCreateDate(DEFAULT_CREATE_DATE);
        hrDesignationHeadSetup.setCreateBy(DEFAULT_CREATE_BY);
        hrDesignationHeadSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrDesignationHeadSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrDesignationHeadSetup() throws Exception {
        int databaseSizeBeforeCreate = hrDesignationHeadSetupRepository.findAll().size();

        // Create the HrDesignationHeadSetup

        restHrDesignationHeadSetupMockMvc.perform(post("/api/hrDesignationHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDesignationHeadSetup)))
                .andExpect(status().isCreated());

        // Validate the HrDesignationHeadSetup in the database
        List<HrDesignationHeadSetup> hrDesignationHeadSetups = hrDesignationHeadSetupRepository.findAll();
        assertThat(hrDesignationHeadSetups).hasSize(databaseSizeBeforeCreate + 1);
        HrDesignationHeadSetup testHrDesignationHeadSetup = hrDesignationHeadSetups.get(hrDesignationHeadSetups.size() - 1);
        assertThat(testHrDesignationHeadSetup.getDesignationCode()).isEqualTo(DEFAULT_DESIGNATION_CODE);
        assertThat(testHrDesignationHeadSetup.getDesignationName()).isEqualTo(DEFAULT_DESIGNATION_NAME);
        assertThat(testHrDesignationHeadSetup.getDesignationDetail()).isEqualTo(DEFAULT_DESIGNATION_DETAIL);
        assertThat(testHrDesignationHeadSetup.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrDesignationHeadSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrDesignationHeadSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrDesignationHeadSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrDesignationHeadSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkDesignationCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDesignationHeadSetupRepository.findAll().size();
        // set the field null
        hrDesignationHeadSetup.setDesignationCode(null);

        // Create the HrDesignationHeadSetup, which fails.

        restHrDesignationHeadSetupMockMvc.perform(post("/api/hrDesignationHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDesignationHeadSetup)))
                .andExpect(status().isBadRequest());

        List<HrDesignationHeadSetup> hrDesignationHeadSetups = hrDesignationHeadSetupRepository.findAll();
        assertThat(hrDesignationHeadSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDesignationHeadSetupRepository.findAll().size();
        // set the field null
        hrDesignationHeadSetup.setActiveStatus(null);

        // Create the HrDesignationHeadSetup, which fails.

        restHrDesignationHeadSetupMockMvc.perform(post("/api/hrDesignationHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDesignationHeadSetup)))
                .andExpect(status().isBadRequest());

        List<HrDesignationHeadSetup> hrDesignationHeadSetups = hrDesignationHeadSetupRepository.findAll();
        assertThat(hrDesignationHeadSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrDesignationHeadSetups() throws Exception {
        // Initialize the database
        hrDesignationHeadSetupRepository.saveAndFlush(hrDesignationHeadSetup);

        // Get all the hrDesignationHeadSetups
        restHrDesignationHeadSetupMockMvc.perform(get("/api/hrDesignationHeadSetups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrDesignationHeadSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].designationCode").value(hasItem(DEFAULT_DESIGNATION_CODE.toString())))
                .andExpect(jsonPath("$.[*].designationName").value(hasItem(DEFAULT_DESIGNATION_NAME.toString())))
                .andExpect(jsonPath("$.[*].designationDetail").value(hasItem(DEFAULT_DESIGNATION_DETAIL.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrDesignationHeadSetup() throws Exception {
        // Initialize the database
        hrDesignationHeadSetupRepository.saveAndFlush(hrDesignationHeadSetup);

        // Get the hrDesignationHeadSetup
        restHrDesignationHeadSetupMockMvc.perform(get("/api/hrDesignationHeadSetups/{id}", hrDesignationHeadSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrDesignationHeadSetup.getId().intValue()))
            .andExpect(jsonPath("$.designationCode").value(DEFAULT_DESIGNATION_CODE.toString()))
            .andExpect(jsonPath("$.designationName").value(DEFAULT_DESIGNATION_NAME.toString()))
            .andExpect(jsonPath("$.designationDetail").value(DEFAULT_DESIGNATION_DETAIL.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrDesignationHeadSetup() throws Exception {
        // Get the hrDesignationHeadSetup
        restHrDesignationHeadSetupMockMvc.perform(get("/api/hrDesignationHeadSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrDesignationHeadSetup() throws Exception {
        // Initialize the database
        hrDesignationHeadSetupRepository.saveAndFlush(hrDesignationHeadSetup);

		int databaseSizeBeforeUpdate = hrDesignationHeadSetupRepository.findAll().size();

        // Update the hrDesignationHeadSetup
        hrDesignationHeadSetup.setDesignationCode(UPDATED_DESIGNATION_CODE);
        hrDesignationHeadSetup.setDesignationName(UPDATED_DESIGNATION_NAME);
        hrDesignationHeadSetup.setDesignationDetail(UPDATED_DESIGNATION_DETAIL);
        hrDesignationHeadSetup.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrDesignationHeadSetup.setCreateDate(UPDATED_CREATE_DATE);
        hrDesignationHeadSetup.setCreateBy(UPDATED_CREATE_BY);
        hrDesignationHeadSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        hrDesignationHeadSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restHrDesignationHeadSetupMockMvc.perform(put("/api/hrDesignationHeadSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDesignationHeadSetup)))
                .andExpect(status().isOk());

        // Validate the HrDesignationHeadSetup in the database
        List<HrDesignationHeadSetup> hrDesignationHeadSetups = hrDesignationHeadSetupRepository.findAll();
        assertThat(hrDesignationHeadSetups).hasSize(databaseSizeBeforeUpdate);
        HrDesignationHeadSetup testHrDesignationHeadSetup = hrDesignationHeadSetups.get(hrDesignationHeadSetups.size() - 1);
        assertThat(testHrDesignationHeadSetup.getDesignationCode()).isEqualTo(UPDATED_DESIGNATION_CODE);
        assertThat(testHrDesignationHeadSetup.getDesignationName()).isEqualTo(UPDATED_DESIGNATION_NAME);
        assertThat(testHrDesignationHeadSetup.getDesignationDetail()).isEqualTo(UPDATED_DESIGNATION_DETAIL);
        assertThat(testHrDesignationHeadSetup.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrDesignationHeadSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrDesignationHeadSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrDesignationHeadSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrDesignationHeadSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrDesignationHeadSetup() throws Exception {
        // Initialize the database
        hrDesignationHeadSetupRepository.saveAndFlush(hrDesignationHeadSetup);

		int databaseSizeBeforeDelete = hrDesignationHeadSetupRepository.findAll().size();

        // Get the hrDesignationHeadSetup
        restHrDesignationHeadSetupMockMvc.perform(delete("/api/hrDesignationHeadSetups/{id}", hrDesignationHeadSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrDesignationHeadSetup> hrDesignationHeadSetups = hrDesignationHeadSetupRepository.findAll();
        assertThat(hrDesignationHeadSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
