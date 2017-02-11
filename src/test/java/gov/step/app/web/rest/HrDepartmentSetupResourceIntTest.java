package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrDepartmentSetup;
import gov.step.app.repository.HrDepartmentSetupRepository;
import gov.step.app.repository.search.HrDepartmentSetupSearchRepository;
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
 * Test class for the HrDepartmentSetupResource REST controller.
 *
 * @see HrDepartmentSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrDepartmentSetupResourceIntTest {

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
    private HrDepartmentSetupRepository hrDepartmentSetupRepository;

    @Inject
    private HrDepartmentSetupSearchRepository hrDepartmentSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrDepartmentSetupMockMvc;

    private HrDepartmentSetup hrDepartmentSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrDepartmentSetupResource hrDepartmentSetupResource = new HrDepartmentSetupResource();
        ReflectionTestUtils.setField(hrDepartmentSetupResource, "hrDepartmentSetupSearchRepository", hrDepartmentSetupSearchRepository);
        ReflectionTestUtils.setField(hrDepartmentSetupResource, "hrDepartmentSetupRepository", hrDepartmentSetupRepository);
        this.restHrDepartmentSetupMockMvc = MockMvcBuilders.standaloneSetup(hrDepartmentSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrDepartmentSetup = new HrDepartmentSetup();
        hrDepartmentSetup.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrDepartmentSetup.setCreateDate(DEFAULT_CREATE_DATE);
        hrDepartmentSetup.setCreateBy(DEFAULT_CREATE_BY);
        hrDepartmentSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrDepartmentSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrDepartmentSetup() throws Exception {
        int databaseSizeBeforeCreate = hrDepartmentSetupRepository.findAll().size();

        // Create the HrDepartmentSetup

        restHrDepartmentSetupMockMvc.perform(post("/api/hrDepartmentSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentSetup)))
                .andExpect(status().isCreated());

        // Validate the HrDepartmentSetup in the database
        List<HrDepartmentSetup> hrDepartmentSetups = hrDepartmentSetupRepository.findAll();
        assertThat(hrDepartmentSetups).hasSize(databaseSizeBeforeCreate + 1);
        HrDepartmentSetup testHrDepartmentSetup = hrDepartmentSetups.get(hrDepartmentSetups.size() - 1);
        assertThat(testHrDepartmentSetup.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrDepartmentSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrDepartmentSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrDepartmentSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrDepartmentSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkDepartmentCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDepartmentSetupRepository.findAll().size();
        // set the field null

        // Create the HrDepartmentSetup, which fails.

        restHrDepartmentSetupMockMvc.perform(post("/api/hrDepartmentSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentSetup)))
                .andExpect(status().isBadRequest());

        List<HrDepartmentSetup> hrDepartmentSetups = hrDepartmentSetupRepository.findAll();
        assertThat(hrDepartmentSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDepartmentSetupRepository.findAll().size();
        // set the field null
        hrDepartmentSetup.setActiveStatus(null);

        // Create the HrDepartmentSetup, which fails.

        restHrDepartmentSetupMockMvc.perform(post("/api/hrDepartmentSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentSetup)))
                .andExpect(status().isBadRequest());

        List<HrDepartmentSetup> hrDepartmentSetups = hrDepartmentSetupRepository.findAll();
        assertThat(hrDepartmentSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrDepartmentSetups() throws Exception {
        // Initialize the database
        hrDepartmentSetupRepository.saveAndFlush(hrDepartmentSetup);

        // Get all the hrDepartmentSetups
        restHrDepartmentSetupMockMvc.perform(get("/api/hrDepartmentSetups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrDepartmentSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrDepartmentSetup() throws Exception {
        // Initialize the database
        hrDepartmentSetupRepository.saveAndFlush(hrDepartmentSetup);

        // Get the hrDepartmentSetup
        restHrDepartmentSetupMockMvc.perform(get("/api/hrDepartmentSetups/{id}", hrDepartmentSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrDepartmentSetup.getId().intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrDepartmentSetup() throws Exception {
        // Get the hrDepartmentSetup
        restHrDepartmentSetupMockMvc.perform(get("/api/hrDepartmentSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrDepartmentSetup() throws Exception {
        // Initialize the database
        hrDepartmentSetupRepository.saveAndFlush(hrDepartmentSetup);

		int databaseSizeBeforeUpdate = hrDepartmentSetupRepository.findAll().size();

        // Update the hrDepartmentSetup
        hrDepartmentSetup.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrDepartmentSetup.setCreateDate(UPDATED_CREATE_DATE);
        hrDepartmentSetup.setCreateBy(UPDATED_CREATE_BY);
        hrDepartmentSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        hrDepartmentSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restHrDepartmentSetupMockMvc.perform(put("/api/hrDepartmentSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDepartmentSetup)))
                .andExpect(status().isOk());

        // Validate the HrDepartmentSetup in the database
        List<HrDepartmentSetup> hrDepartmentSetups = hrDepartmentSetupRepository.findAll();
        assertThat(hrDepartmentSetups).hasSize(databaseSizeBeforeUpdate);
        HrDepartmentSetup testHrDepartmentSetup = hrDepartmentSetups.get(hrDepartmentSetups.size() - 1);
        assertThat(testHrDepartmentSetup.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrDepartmentSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrDepartmentSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrDepartmentSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrDepartmentSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrDepartmentSetup() throws Exception {
        // Initialize the database
        hrDepartmentSetupRepository.saveAndFlush(hrDepartmentSetup);

		int databaseSizeBeforeDelete = hrDepartmentSetupRepository.findAll().size();

        // Get the hrDepartmentSetup
        restHrDepartmentSetupMockMvc.perform(delete("/api/hrDepartmentSetups/{id}", hrDepartmentSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrDepartmentSetup> hrDepartmentSetups = hrDepartmentSetupRepository.findAll();
        assertThat(hrDepartmentSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
