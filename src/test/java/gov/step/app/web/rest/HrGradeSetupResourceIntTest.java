package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrGradeSetup;
import gov.step.app.repository.HrGradeSetupRepository;
import gov.step.app.repository.search.HrGradeSetupSearchRepository;
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
 * Test class for the HrGradeSetupResource REST controller.
 *
 * @see HrGradeSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrGradeSetupResourceIntTest {

    private static final String DEFAULT_GRADE_CODE = "AAAAA";
    private static final String UPDATED_GRADE_CODE = "BBBBB";
    private static final String DEFAULT_GRADE_DETAIL = "AAAAA";
    private static final String UPDATED_GRADE_DETAIL = "BBBBB";

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
    private HrGradeSetupRepository hrGradeSetupRepository;

    @Inject
    private HrGradeSetupSearchRepository hrGradeSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrGradeSetupMockMvc;

    private HrGradeSetup hrGradeSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrGradeSetupResource hrGradeSetupResource = new HrGradeSetupResource();
        ReflectionTestUtils.setField(hrGradeSetupResource, "hrGradeSetupSearchRepository", hrGradeSetupSearchRepository);
        ReflectionTestUtils.setField(hrGradeSetupResource, "hrGradeSetupRepository", hrGradeSetupRepository);
        this.restHrGradeSetupMockMvc = MockMvcBuilders.standaloneSetup(hrGradeSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrGradeSetup = new HrGradeSetup();
        hrGradeSetup.setGradeCode(DEFAULT_GRADE_CODE);
        hrGradeSetup.setGradeDetail(DEFAULT_GRADE_DETAIL);
        hrGradeSetup.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrGradeSetup.setCreateDate(DEFAULT_CREATE_DATE);
        hrGradeSetup.setCreateBy(DEFAULT_CREATE_BY);
        hrGradeSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrGradeSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrGradeSetup() throws Exception {
        int databaseSizeBeforeCreate = hrGradeSetupRepository.findAll().size();

        // Create the HrGradeSetup

        restHrGradeSetupMockMvc.perform(post("/api/hrGradeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrGradeSetup)))
                .andExpect(status().isCreated());

        // Validate the HrGradeSetup in the database
        List<HrGradeSetup> hrGradeSetups = hrGradeSetupRepository.findAll();
        assertThat(hrGradeSetups).hasSize(databaseSizeBeforeCreate + 1);
        HrGradeSetup testHrGradeSetup = hrGradeSetups.get(hrGradeSetups.size() - 1);
        assertThat(testHrGradeSetup.getGradeCode()).isEqualTo(DEFAULT_GRADE_CODE);
        assertThat(testHrGradeSetup.getGradeDetail()).isEqualTo(DEFAULT_GRADE_DETAIL);
        assertThat(testHrGradeSetup.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrGradeSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrGradeSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrGradeSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrGradeSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkGradeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrGradeSetupRepository.findAll().size();
        // set the field null
        hrGradeSetup.setGradeCode(null);

        // Create the HrGradeSetup, which fails.

        restHrGradeSetupMockMvc.perform(post("/api/hrGradeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrGradeSetup)))
                .andExpect(status().isBadRequest());

        List<HrGradeSetup> hrGradeSetups = hrGradeSetupRepository.findAll();
        assertThat(hrGradeSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrGradeSetupRepository.findAll().size();
        // set the field null
        hrGradeSetup.setActiveStatus(null);

        // Create the HrGradeSetup, which fails.

        restHrGradeSetupMockMvc.perform(post("/api/hrGradeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrGradeSetup)))
                .andExpect(status().isBadRequest());

        List<HrGradeSetup> hrGradeSetups = hrGradeSetupRepository.findAll();
        assertThat(hrGradeSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrGradeSetups() throws Exception {
        // Initialize the database
        hrGradeSetupRepository.saveAndFlush(hrGradeSetup);

        // Get all the hrGradeSetups
        restHrGradeSetupMockMvc.perform(get("/api/hrGradeSetups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrGradeSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].gradeCode").value(hasItem(DEFAULT_GRADE_CODE.toString())))
                .andExpect(jsonPath("$.[*].gradeDetail").value(hasItem(DEFAULT_GRADE_DETAIL.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrGradeSetup() throws Exception {
        // Initialize the database
        hrGradeSetupRepository.saveAndFlush(hrGradeSetup);

        // Get the hrGradeSetup
        restHrGradeSetupMockMvc.perform(get("/api/hrGradeSetups/{id}", hrGradeSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrGradeSetup.getId().intValue()))
            .andExpect(jsonPath("$.gradeCode").value(DEFAULT_GRADE_CODE.toString()))
            .andExpect(jsonPath("$.gradeDetail").value(DEFAULT_GRADE_DETAIL.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrGradeSetup() throws Exception {
        // Get the hrGradeSetup
        restHrGradeSetupMockMvc.perform(get("/api/hrGradeSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrGradeSetup() throws Exception {
        // Initialize the database
        hrGradeSetupRepository.saveAndFlush(hrGradeSetup);

		int databaseSizeBeforeUpdate = hrGradeSetupRepository.findAll().size();

        // Update the hrGradeSetup
        hrGradeSetup.setGradeCode(UPDATED_GRADE_CODE);
        hrGradeSetup.setGradeDetail(UPDATED_GRADE_DETAIL);
        hrGradeSetup.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrGradeSetup.setCreateDate(UPDATED_CREATE_DATE);
        hrGradeSetup.setCreateBy(UPDATED_CREATE_BY);
        hrGradeSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        hrGradeSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restHrGradeSetupMockMvc.perform(put("/api/hrGradeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrGradeSetup)))
                .andExpect(status().isOk());

        // Validate the HrGradeSetup in the database
        List<HrGradeSetup> hrGradeSetups = hrGradeSetupRepository.findAll();
        assertThat(hrGradeSetups).hasSize(databaseSizeBeforeUpdate);
        HrGradeSetup testHrGradeSetup = hrGradeSetups.get(hrGradeSetups.size() - 1);
        assertThat(testHrGradeSetup.getGradeCode()).isEqualTo(UPDATED_GRADE_CODE);
        assertThat(testHrGradeSetup.getGradeDetail()).isEqualTo(UPDATED_GRADE_DETAIL);
        assertThat(testHrGradeSetup.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrGradeSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrGradeSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrGradeSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrGradeSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrGradeSetup() throws Exception {
        // Initialize the database
        hrGradeSetupRepository.saveAndFlush(hrGradeSetup);

		int databaseSizeBeforeDelete = hrGradeSetupRepository.findAll().size();

        // Get the hrGradeSetup
        restHrGradeSetupMockMvc.perform(delete("/api/hrGradeSetups/{id}", hrGradeSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrGradeSetup> hrGradeSetups = hrGradeSetupRepository.findAll();
        assertThat(hrGradeSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
