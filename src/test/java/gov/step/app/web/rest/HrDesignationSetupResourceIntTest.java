package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrDesignationSetup;
import gov.step.app.repository.HrDesignationSetupRepository;
import gov.step.app.repository.search.HrDesignationSetupSearchRepository;
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
 * Test class for the HrDesignationSetupResource REST controller.
 *
 * @see HrDesignationSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrDesignationSetupResourceIntTest
{
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
    private HrDesignationSetupRepository hrDesignationSetupRepository;

    @Inject
    private HrDesignationSetupSearchRepository hrDesignationSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrDesignationSetupMockMvc;

    private HrDesignationSetup hrDesignationSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrDesignationSetupResource hrDesignationSetupResource = new HrDesignationSetupResource();
        ReflectionTestUtils.setField(hrDesignationSetupResource, "hrDesignationSetupSearchRepository", hrDesignationSetupSearchRepository);
        ReflectionTestUtils.setField(hrDesignationSetupResource, "hrDesignationSetupRepository", hrDesignationSetupRepository);
        this.restHrDesignationSetupMockMvc = MockMvcBuilders.standaloneSetup(hrDesignationSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrDesignationSetup = new HrDesignationSetup();
        hrDesignationSetup.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrDesignationSetup.setCreateDate(DEFAULT_CREATE_DATE);
        hrDesignationSetup.setCreateBy(DEFAULT_CREATE_BY);
        hrDesignationSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrDesignationSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrDesignationSetup() throws Exception {
        int databaseSizeBeforeCreate = hrDesignationSetupRepository.findAll().size();

        // Create the HrDesignationSetup

        restHrDesignationSetupMockMvc.perform(post("/api/hrDesignationSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDesignationSetup)))
                .andExpect(status().isCreated());

        // Validate the HrDesignationSetup in the database
        List<HrDesignationSetup> hrDesignationSetups = hrDesignationSetupRepository.findAll();
        assertThat(hrDesignationSetups).hasSize(databaseSizeBeforeCreate + 1);
        HrDesignationSetup testHrDesignationSetup = hrDesignationSetups.get(hrDesignationSetups.size() - 1);
        assertThat(testHrDesignationSetup.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrDesignationSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrDesignationSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrDesignationSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrDesignationSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }


    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrDesignationSetupRepository.findAll().size();
        // set the field null
        hrDesignationSetup.setActiveStatus(null);

        // Create the HrDesignationSetup, which fails.

        restHrDesignationSetupMockMvc.perform(post("/api/hrDesignationSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDesignationSetup)))
                .andExpect(status().isBadRequest());

        List<HrDesignationSetup> hrDesignationSetups = hrDesignationSetupRepository.findAll();
        assertThat(hrDesignationSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrDesignationSetups() throws Exception {
        // Initialize the database
        hrDesignationSetupRepository.saveAndFlush(hrDesignationSetup);

        // Get all the hrDesignationSetups
        restHrDesignationSetupMockMvc.perform(get("/api/hrDesignationSetups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrDesignationSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrDesignationSetup() throws Exception {
        // Initialize the database
        hrDesignationSetupRepository.saveAndFlush(hrDesignationSetup);

        // Get the hrDesignationSetup
        restHrDesignationSetupMockMvc.perform(get("/api/hrDesignationSetups/{id}", hrDesignationSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrDesignationSetup.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrDesignationSetup() throws Exception {
        // Get the hrDesignationSetup
        restHrDesignationSetupMockMvc.perform(get("/api/hrDesignationSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrDesignationSetup() throws Exception {
        // Initialize the database
        hrDesignationSetupRepository.saveAndFlush(hrDesignationSetup);

		int databaseSizeBeforeUpdate = hrDesignationSetupRepository.findAll().size();

        // Update the hrDesignationSetup
        hrDesignationSetup.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrDesignationSetup.setCreateDate(UPDATED_CREATE_DATE);
        hrDesignationSetup.setCreateBy(UPDATED_CREATE_BY);
        hrDesignationSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        hrDesignationSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restHrDesignationSetupMockMvc.perform(put("/api/hrDesignationSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrDesignationSetup)))
                .andExpect(status().isOk());

        // Validate the HrDesignationSetup in the database
        List<HrDesignationSetup> hrDesignationSetups = hrDesignationSetupRepository.findAll();
        assertThat(hrDesignationSetups).hasSize(databaseSizeBeforeUpdate);
        HrDesignationSetup testHrDesignationSetup = hrDesignationSetups.get(hrDesignationSetups.size() - 1);
        assertThat(testHrDesignationSetup.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrDesignationSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrDesignationSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrDesignationSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrDesignationSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrDesignationSetup() throws Exception {
        // Initialize the database
        hrDesignationSetupRepository.saveAndFlush(hrDesignationSetup);

		int databaseSizeBeforeDelete = hrDesignationSetupRepository.findAll().size();

        // Get the hrDesignationSetup
        restHrDesignationSetupMockMvc.perform(delete("/api/hrDesignationSetups/{id}", hrDesignationSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrDesignationSetup> hrDesignationSetups = hrDesignationSetupRepository.findAll();
        assertThat(hrDesignationSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
