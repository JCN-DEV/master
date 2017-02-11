package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrPayScaleSetup;
import gov.step.app.repository.HrPayScaleSetupRepository;
import gov.step.app.repository.search.HrPayScaleSetupSearchRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrPayScaleSetupResource REST controller.
 *
 * @see HrPayScaleSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrPayScaleSetupResourceIntTest {

    private static final String DEFAULT_PAY_SCALE_CODE = "AAAAA";
    private static final String UPDATED_PAY_SCALE_CODE = "BBBBB";

    private static final BigDecimal DEFAULT_BASIC_PAY_SCALE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC_PAY_SCALE = new BigDecimal(2);

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
    private HrPayScaleSetupRepository hrPayScaleSetupRepository;

    @Inject
    private HrPayScaleSetupSearchRepository hrPayScaleSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrPayScaleSetupMockMvc;

    private HrPayScaleSetup hrPayScaleSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrPayScaleSetupResource hrPayScaleSetupResource = new HrPayScaleSetupResource();
        ReflectionTestUtils.setField(hrPayScaleSetupResource, "hrPayScaleSetupSearchRepository", hrPayScaleSetupSearchRepository);
        ReflectionTestUtils.setField(hrPayScaleSetupResource, "hrPayScaleSetupRepository", hrPayScaleSetupRepository);
        this.restHrPayScaleSetupMockMvc = MockMvcBuilders.standaloneSetup(hrPayScaleSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrPayScaleSetup = new HrPayScaleSetup();
        hrPayScaleSetup.setPayScaleCode(DEFAULT_PAY_SCALE_CODE);
        hrPayScaleSetup.setBasicPayScale(DEFAULT_BASIC_PAY_SCALE);
        hrPayScaleSetup.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrPayScaleSetup.setCreateDate(DEFAULT_CREATE_DATE);
        hrPayScaleSetup.setCreateBy(DEFAULT_CREATE_BY);
        hrPayScaleSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrPayScaleSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrPayScaleSetup() throws Exception {
        int databaseSizeBeforeCreate = hrPayScaleSetupRepository.findAll().size();

        // Create the HrPayScaleSetup

        restHrPayScaleSetupMockMvc.perform(post("/api/hrPayScaleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrPayScaleSetup)))
                .andExpect(status().isCreated());

        // Validate the HrPayScaleSetup in the database
        List<HrPayScaleSetup> hrPayScaleSetups = hrPayScaleSetupRepository.findAll();
        assertThat(hrPayScaleSetups).hasSize(databaseSizeBeforeCreate + 1);
        HrPayScaleSetup testHrPayScaleSetup = hrPayScaleSetups.get(hrPayScaleSetups.size() - 1);
        assertThat(testHrPayScaleSetup.getPayScaleCode()).isEqualTo(DEFAULT_PAY_SCALE_CODE);
        assertThat(testHrPayScaleSetup.getBasicPayScale()).isEqualTo(DEFAULT_BASIC_PAY_SCALE);
        assertThat(testHrPayScaleSetup.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrPayScaleSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrPayScaleSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrPayScaleSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrPayScaleSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkPayScaleCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrPayScaleSetupRepository.findAll().size();
        // set the field null
        hrPayScaleSetup.setPayScaleCode(null);

        // Create the HrPayScaleSetup, which fails.

        restHrPayScaleSetupMockMvc.perform(post("/api/hrPayScaleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrPayScaleSetup)))
                .andExpect(status().isBadRequest());

        List<HrPayScaleSetup> hrPayScaleSetups = hrPayScaleSetupRepository.findAll();
        assertThat(hrPayScaleSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrPayScaleSetupRepository.findAll().size();
        // set the field null
        hrPayScaleSetup.setActiveStatus(null);

        // Create the HrPayScaleSetup, which fails.

        restHrPayScaleSetupMockMvc.perform(post("/api/hrPayScaleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrPayScaleSetup)))
                .andExpect(status().isBadRequest());

        List<HrPayScaleSetup> hrPayScaleSetups = hrPayScaleSetupRepository.findAll();
        assertThat(hrPayScaleSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrPayScaleSetups() throws Exception {
        // Initialize the database
        hrPayScaleSetupRepository.saveAndFlush(hrPayScaleSetup);

        // Get all the hrPayScaleSetups
        restHrPayScaleSetupMockMvc.perform(get("/api/hrPayScaleSetups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrPayScaleSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].payScaleCode").value(hasItem(DEFAULT_PAY_SCALE_CODE.toString())))
                .andExpect(jsonPath("$.[*].basicPayScale").value(hasItem(DEFAULT_BASIC_PAY_SCALE.intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrPayScaleSetup() throws Exception {
        // Initialize the database
        hrPayScaleSetupRepository.saveAndFlush(hrPayScaleSetup);

        // Get the hrPayScaleSetup
        restHrPayScaleSetupMockMvc.perform(get("/api/hrPayScaleSetups/{id}", hrPayScaleSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrPayScaleSetup.getId().intValue()))
            .andExpect(jsonPath("$.payScaleCode").value(DEFAULT_PAY_SCALE_CODE.toString()))
            .andExpect(jsonPath("$.basicPayScale").value(DEFAULT_BASIC_PAY_SCALE.intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrPayScaleSetup() throws Exception {
        // Get the hrPayScaleSetup
        restHrPayScaleSetupMockMvc.perform(get("/api/hrPayScaleSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrPayScaleSetup() throws Exception {
        // Initialize the database
        hrPayScaleSetupRepository.saveAndFlush(hrPayScaleSetup);

		int databaseSizeBeforeUpdate = hrPayScaleSetupRepository.findAll().size();

        // Update the hrPayScaleSetup
        hrPayScaleSetup.setPayScaleCode(UPDATED_PAY_SCALE_CODE);
        hrPayScaleSetup.setBasicPayScale(UPDATED_BASIC_PAY_SCALE);
        hrPayScaleSetup.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrPayScaleSetup.setCreateDate(UPDATED_CREATE_DATE);
        hrPayScaleSetup.setCreateBy(UPDATED_CREATE_BY);
        hrPayScaleSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        hrPayScaleSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restHrPayScaleSetupMockMvc.perform(put("/api/hrPayScaleSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrPayScaleSetup)))
                .andExpect(status().isOk());

        // Validate the HrPayScaleSetup in the database
        List<HrPayScaleSetup> hrPayScaleSetups = hrPayScaleSetupRepository.findAll();
        assertThat(hrPayScaleSetups).hasSize(databaseSizeBeforeUpdate);
        HrPayScaleSetup testHrPayScaleSetup = hrPayScaleSetups.get(hrPayScaleSetups.size() - 1);
        assertThat(testHrPayScaleSetup.getPayScaleCode()).isEqualTo(UPDATED_PAY_SCALE_CODE);
        assertThat(testHrPayScaleSetup.getBasicPayScale()).isEqualTo(UPDATED_BASIC_PAY_SCALE);
        assertThat(testHrPayScaleSetup.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrPayScaleSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrPayScaleSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrPayScaleSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrPayScaleSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrPayScaleSetup() throws Exception {
        // Initialize the database
        hrPayScaleSetupRepository.saveAndFlush(hrPayScaleSetup);

		int databaseSizeBeforeDelete = hrPayScaleSetupRepository.findAll().size();

        // Get the hrPayScaleSetup
        restHrPayScaleSetupMockMvc.perform(delete("/api/hrPayScaleSetups/{id}", hrPayScaleSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrPayScaleSetup> hrPayScaleSetups = hrPayScaleSetupRepository.findAll();
        assertThat(hrPayScaleSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
