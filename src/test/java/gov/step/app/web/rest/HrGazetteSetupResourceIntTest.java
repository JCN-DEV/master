package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrGazetteSetup;
import gov.step.app.repository.HrGazetteSetupRepository;
import gov.step.app.repository.search.HrGazetteSetupSearchRepository;
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
 * Test class for the HrGazetteSetupResource REST controller.
 *
 * @see HrGazetteSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrGazetteSetupResourceIntTest {

    private static final String DEFAULT_GAZETTE_CODE = "AAAAA";
    private static final String UPDATED_GAZETTE_CODE = "BBBBB";
    private static final String DEFAULT_GAZETTE_NAME = "AAAAA";
    private static final String UPDATED_GAZETTE_NAME = "BBBBB";
    private static final String DEFAULT_GAZETTE_YEAR = "AAAAA";
    private static final String UPDATED_GAZETTE_YEAR = "BBBBB";
    private static final String DEFAULT_GAZETTE_DETAIL = "AAAAA";
    private static final String UPDATED_GAZETTE_DETAIL = "BBBBB";

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
    private HrGazetteSetupRepository hrGazetteSetupRepository;

    @Inject
    private HrGazetteSetupSearchRepository hrGazetteSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrGazetteSetupMockMvc;

    private HrGazetteSetup hrGazetteSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrGazetteSetupResource hrGazetteSetupResource = new HrGazetteSetupResource();
        ReflectionTestUtils.setField(hrGazetteSetupResource, "hrGazetteSetupSearchRepository", hrGazetteSetupSearchRepository);
        ReflectionTestUtils.setField(hrGazetteSetupResource, "hrGazetteSetupRepository", hrGazetteSetupRepository);
        this.restHrGazetteSetupMockMvc = MockMvcBuilders.standaloneSetup(hrGazetteSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrGazetteSetup = new HrGazetteSetup();
        hrGazetteSetup.setGazetteCode(DEFAULT_GAZETTE_CODE);
        hrGazetteSetup.setGazetteName(DEFAULT_GAZETTE_NAME);
        hrGazetteSetup.setGazetteYear(DEFAULT_GAZETTE_YEAR);
        hrGazetteSetup.setGazetteDetail(DEFAULT_GAZETTE_DETAIL);
        hrGazetteSetup.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrGazetteSetup.setCreateDate(DEFAULT_CREATE_DATE);
        hrGazetteSetup.setCreateBy(DEFAULT_CREATE_BY);
        hrGazetteSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrGazetteSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrGazetteSetup() throws Exception {
        int databaseSizeBeforeCreate = hrGazetteSetupRepository.findAll().size();

        // Create the HrGazetteSetup

        restHrGazetteSetupMockMvc.perform(post("/api/hrGazetteSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrGazetteSetup)))
                .andExpect(status().isCreated());

        // Validate the HrGazetteSetup in the database
        List<HrGazetteSetup> hrGazetteSetups = hrGazetteSetupRepository.findAll();
        assertThat(hrGazetteSetups).hasSize(databaseSizeBeforeCreate + 1);
        HrGazetteSetup testHrGazetteSetup = hrGazetteSetups.get(hrGazetteSetups.size() - 1);
        assertThat(testHrGazetteSetup.getGazetteCode()).isEqualTo(DEFAULT_GAZETTE_CODE);
        assertThat(testHrGazetteSetup.getGazetteName()).isEqualTo(DEFAULT_GAZETTE_NAME);
        assertThat(testHrGazetteSetup.getGazetteYear()).isEqualTo(DEFAULT_GAZETTE_YEAR);
        assertThat(testHrGazetteSetup.getGazetteDetail()).isEqualTo(DEFAULT_GAZETTE_DETAIL);
        assertThat(testHrGazetteSetup.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrGazetteSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrGazetteSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrGazetteSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrGazetteSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkGazetteCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrGazetteSetupRepository.findAll().size();
        // set the field null
        hrGazetteSetup.setGazetteCode(null);

        // Create the HrGazetteSetup, which fails.

        restHrGazetteSetupMockMvc.perform(post("/api/hrGazetteSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrGazetteSetup)))
                .andExpect(status().isBadRequest());

        List<HrGazetteSetup> hrGazetteSetups = hrGazetteSetupRepository.findAll();
        assertThat(hrGazetteSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGazetteNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrGazetteSetupRepository.findAll().size();
        // set the field null
        hrGazetteSetup.setGazetteName(null);

        // Create the HrGazetteSetup, which fails.

        restHrGazetteSetupMockMvc.perform(post("/api/hrGazetteSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrGazetteSetup)))
                .andExpect(status().isBadRequest());

        List<HrGazetteSetup> hrGazetteSetups = hrGazetteSetupRepository.findAll();
        assertThat(hrGazetteSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrGazetteSetupRepository.findAll().size();
        // set the field null
        hrGazetteSetup.setActiveStatus(null);

        // Create the HrGazetteSetup, which fails.

        restHrGazetteSetupMockMvc.perform(post("/api/hrGazetteSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrGazetteSetup)))
                .andExpect(status().isBadRequest());

        List<HrGazetteSetup> hrGazetteSetups = hrGazetteSetupRepository.findAll();
        assertThat(hrGazetteSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrGazetteSetups() throws Exception {
        // Initialize the database
        hrGazetteSetupRepository.saveAndFlush(hrGazetteSetup);

        // Get all the hrGazetteSetups
        restHrGazetteSetupMockMvc.perform(get("/api/hrGazetteSetups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrGazetteSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].gazetteCode").value(hasItem(DEFAULT_GAZETTE_CODE.toString())))
                .andExpect(jsonPath("$.[*].gazetteName").value(hasItem(DEFAULT_GAZETTE_NAME.toString())))
                .andExpect(jsonPath("$.[*].gazetteYear").value(hasItem(DEFAULT_GAZETTE_YEAR.toString())))
                .andExpect(jsonPath("$.[*].gazetteDetail").value(hasItem(DEFAULT_GAZETTE_DETAIL.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrGazetteSetup() throws Exception {
        // Initialize the database
        hrGazetteSetupRepository.saveAndFlush(hrGazetteSetup);

        // Get the hrGazetteSetup
        restHrGazetteSetupMockMvc.perform(get("/api/hrGazetteSetups/{id}", hrGazetteSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrGazetteSetup.getId().intValue()))
            .andExpect(jsonPath("$.gazetteCode").value(DEFAULT_GAZETTE_CODE.toString()))
            .andExpect(jsonPath("$.gazetteName").value(DEFAULT_GAZETTE_NAME.toString()))
            .andExpect(jsonPath("$.gazetteYear").value(DEFAULT_GAZETTE_YEAR.toString()))
            .andExpect(jsonPath("$.gazetteDetail").value(DEFAULT_GAZETTE_DETAIL.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrGazetteSetup() throws Exception {
        // Get the hrGazetteSetup
        restHrGazetteSetupMockMvc.perform(get("/api/hrGazetteSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrGazetteSetup() throws Exception {
        // Initialize the database
        hrGazetteSetupRepository.saveAndFlush(hrGazetteSetup);

		int databaseSizeBeforeUpdate = hrGazetteSetupRepository.findAll().size();

        // Update the hrGazetteSetup
        hrGazetteSetup.setGazetteCode(UPDATED_GAZETTE_CODE);
        hrGazetteSetup.setGazetteName(UPDATED_GAZETTE_NAME);
        hrGazetteSetup.setGazetteYear(UPDATED_GAZETTE_YEAR);
        hrGazetteSetup.setGazetteDetail(UPDATED_GAZETTE_DETAIL);
        hrGazetteSetup.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrGazetteSetup.setCreateDate(UPDATED_CREATE_DATE);
        hrGazetteSetup.setCreateBy(UPDATED_CREATE_BY);
        hrGazetteSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        hrGazetteSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restHrGazetteSetupMockMvc.perform(put("/api/hrGazetteSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrGazetteSetup)))
                .andExpect(status().isOk());

        // Validate the HrGazetteSetup in the database
        List<HrGazetteSetup> hrGazetteSetups = hrGazetteSetupRepository.findAll();
        assertThat(hrGazetteSetups).hasSize(databaseSizeBeforeUpdate);
        HrGazetteSetup testHrGazetteSetup = hrGazetteSetups.get(hrGazetteSetups.size() - 1);
        assertThat(testHrGazetteSetup.getGazetteCode()).isEqualTo(UPDATED_GAZETTE_CODE);
        assertThat(testHrGazetteSetup.getGazetteName()).isEqualTo(UPDATED_GAZETTE_NAME);
        assertThat(testHrGazetteSetup.getGazetteYear()).isEqualTo(UPDATED_GAZETTE_YEAR);
        assertThat(testHrGazetteSetup.getGazetteDetail()).isEqualTo(UPDATED_GAZETTE_DETAIL);
        assertThat(testHrGazetteSetup.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrGazetteSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrGazetteSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrGazetteSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrGazetteSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrGazetteSetup() throws Exception {
        // Initialize the database
        hrGazetteSetupRepository.saveAndFlush(hrGazetteSetup);

		int databaseSizeBeforeDelete = hrGazetteSetupRepository.findAll().size();

        // Get the hrGazetteSetup
        restHrGazetteSetupMockMvc.perform(delete("/api/hrGazetteSetups/{id}", hrGazetteSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrGazetteSetup> hrGazetteSetups = hrGazetteSetupRepository.findAll();
        assertThat(hrGazetteSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
