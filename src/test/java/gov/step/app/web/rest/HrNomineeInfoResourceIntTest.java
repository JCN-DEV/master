package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrNomineeInfo;
import gov.step.app.domain.enumeration.Gender;
import gov.step.app.repository.HrNomineeInfoRepository;
import gov.step.app.repository.search.HrNomineeInfoSearchRepository;
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
 * Test class for the HrNomineeInfoResource REST controller.
 *
 * @see HrNomineeInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrNomineeInfoResourceIntTest {

    private static final String DEFAULT_NOMINEE_NAME = "AAAAA";
    private static final String UPDATED_NOMINEE_NAME = "BBBBB";
    private static final String DEFAULT_NOMINEE_NAME_BN = "AAAAA";
    private static final String UPDATED_NOMINEE_NAME_BN = "BBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());


    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;
    private static final String DEFAULT_RELATIONSHIP = "AAAAA";
    private static final String UPDATED_RELATIONSHIP = "BBBBB";
    private static final String DEFAULT_OCCUPATION = "AAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBBBBB";
    private static final String DEFAULT_MOBILE_NUMBER = "AAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final Boolean DEFAULT_ROW_LOCKED = false;
    private static final Boolean UPDATED_ROW_LOCKED = true;

    private static final Boolean DEFAULT_OPEN_FOR_APPROVAL = false;
    private static final Boolean UPDATED_OPEN_FOR_APPROVAL = true;
    private static final String DEFAULT_ACTION_COMMENTS = "AAAAA";
    private static final String UPDATED_ACTION_COMMENTS = "BBBBB";

    private static final LocalDate DEFAULT_ACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ACTION_BY = 1L;
    private static final Long UPDATED_ACTION_BY = 2L;

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
    private HrNomineeInfoRepository hrNomineeInfoRepository;

    @Inject
    private HrNomineeInfoSearchRepository hrNomineeInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrNomineeInfoMockMvc;

    private HrNomineeInfo hrNomineeInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrNomineeInfoResource hrNomineeInfoResource = new HrNomineeInfoResource();
        ReflectionTestUtils.setField(hrNomineeInfoResource, "hrNomineeInfoSearchRepository", hrNomineeInfoSearchRepository);
        ReflectionTestUtils.setField(hrNomineeInfoResource, "hrNomineeInfoRepository", hrNomineeInfoRepository);
        this.restHrNomineeInfoMockMvc = MockMvcBuilders.standaloneSetup(hrNomineeInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrNomineeInfo = new HrNomineeInfo();
        hrNomineeInfo.setNomineeName(DEFAULT_NOMINEE_NAME);
        hrNomineeInfo.setNomineeNameBn(DEFAULT_NOMINEE_NAME_BN);
        hrNomineeInfo.setBirthDate(DEFAULT_BIRTH_DATE);
        hrNomineeInfo.setGender(DEFAULT_GENDER);
        hrNomineeInfo.setOccupation(DEFAULT_OCCUPATION);
        hrNomineeInfo.setDesignation(DEFAULT_DESIGNATION);
        hrNomineeInfo.setNationalId(DEFAULT_NATIONAL_ID);
        hrNomineeInfo.setMobileNumber(DEFAULT_MOBILE_NUMBER);
        hrNomineeInfo.setAddress(DEFAULT_ADDRESS);
        hrNomineeInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrNomineeInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrNomineeInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrNomineeInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrNomineeInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrNomineeInfo() throws Exception {
        int databaseSizeBeforeCreate = hrNomineeInfoRepository.findAll().size();

        // Create the HrNomineeInfo

        restHrNomineeInfoMockMvc.perform(post("/api/hrNomineeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrNomineeInfo)))
                .andExpect(status().isCreated());

        // Validate the HrNomineeInfo in the database
        List<HrNomineeInfo> hrNomineeInfos = hrNomineeInfoRepository.findAll();
        assertThat(hrNomineeInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrNomineeInfo testHrNomineeInfo = hrNomineeInfos.get(hrNomineeInfos.size() - 1);
        assertThat(testHrNomineeInfo.getNomineeName()).isEqualTo(DEFAULT_NOMINEE_NAME);
        assertThat(testHrNomineeInfo.getNomineeNameBn()).isEqualTo(DEFAULT_NOMINEE_NAME_BN);
        assertThat(testHrNomineeInfo.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testHrNomineeInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testHrNomineeInfo.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testHrNomineeInfo.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testHrNomineeInfo.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testHrNomineeInfo.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testHrNomineeInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHrNomineeInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrNomineeInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrNomineeInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrNomineeInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrNomineeInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkNomineeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrNomineeInfoRepository.findAll().size();
        // set the field null
        hrNomineeInfo.setNomineeName(null);

        // Create the HrNomineeInfo, which fails.

        restHrNomineeInfoMockMvc.perform(post("/api/hrNomineeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrNomineeInfo)))
                .andExpect(status().isBadRequest());

        List<HrNomineeInfo> hrNomineeInfos = hrNomineeInfoRepository.findAll();
        assertThat(hrNomineeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrNomineeInfoRepository.findAll().size();
        // set the field null
        hrNomineeInfo.setBirthDate(null);

        // Create the HrNomineeInfo, which fails.

        restHrNomineeInfoMockMvc.perform(post("/api/hrNomineeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrNomineeInfo)))
                .andExpect(status().isBadRequest());

        List<HrNomineeInfo> hrNomineeInfos = hrNomineeInfoRepository.findAll();
        assertThat(hrNomineeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrNomineeInfoRepository.findAll().size();
        // set the field null
        hrNomineeInfo.setGender(null);

        // Create the HrNomineeInfo, which fails.

        restHrNomineeInfoMockMvc.perform(post("/api/hrNomineeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrNomineeInfo)))
                .andExpect(status().isBadRequest());

        List<HrNomineeInfo> hrNomineeInfos = hrNomineeInfoRepository.findAll();
        assertThat(hrNomineeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRelationshipIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrNomineeInfoRepository.findAll().size();
        // set the field null

        // Create the HrNomineeInfo, which fails.

        restHrNomineeInfoMockMvc.perform(post("/api/hrNomineeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrNomineeInfo)))
                .andExpect(status().isBadRequest());

        List<HrNomineeInfo> hrNomineeInfos = hrNomineeInfoRepository.findAll();
        assertThat(hrNomineeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrNomineeInfoRepository.findAll().size();
        // set the field null
        hrNomineeInfo.setMobileNumber(null);

        // Create the HrNomineeInfo, which fails.

        restHrNomineeInfoMockMvc.perform(post("/api/hrNomineeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrNomineeInfo)))
                .andExpect(status().isBadRequest());

        List<HrNomineeInfo> hrNomineeInfos = hrNomineeInfoRepository.findAll();
        assertThat(hrNomineeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrNomineeInfoRepository.findAll().size();
        // set the field null
        hrNomineeInfo.setActiveStatus(null);

        // Create the HrNomineeInfo, which fails.

        restHrNomineeInfoMockMvc.perform(post("/api/hrNomineeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrNomineeInfo)))
                .andExpect(status().isBadRequest());

        List<HrNomineeInfo> hrNomineeInfos = hrNomineeInfoRepository.findAll();
        assertThat(hrNomineeInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrNomineeInfos() throws Exception {
        // Initialize the database
        hrNomineeInfoRepository.saveAndFlush(hrNomineeInfo);

        // Get all the hrNomineeInfos
        restHrNomineeInfoMockMvc.perform(get("/api/hrNomineeInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrNomineeInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].nomineeName").value(hasItem(DEFAULT_NOMINEE_NAME.toString())))
                .andExpect(jsonPath("$.[*].nomineeNameBn").value(hasItem(DEFAULT_NOMINEE_NAME_BN.toString())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID.toString())))
                .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrNomineeInfo() throws Exception {
        // Initialize the database
        hrNomineeInfoRepository.saveAndFlush(hrNomineeInfo);

        // Get the hrNomineeInfo
        restHrNomineeInfoMockMvc.perform(get("/api/hrNomineeInfos/{id}", hrNomineeInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrNomineeInfo.getId().intValue()))
            .andExpect(jsonPath("$.nomineeName").value(DEFAULT_NOMINEE_NAME.toString()))
            .andExpect(jsonPath("$.nomineeNameBn").value(DEFAULT_NOMINEE_NAME_BN.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID.toString()))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrNomineeInfo() throws Exception {
        // Get the hrNomineeInfo
        restHrNomineeInfoMockMvc.perform(get("/api/hrNomineeInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrNomineeInfo() throws Exception {
        // Initialize the database
        hrNomineeInfoRepository.saveAndFlush(hrNomineeInfo);

		int databaseSizeBeforeUpdate = hrNomineeInfoRepository.findAll().size();

        // Update the hrNomineeInfo
        hrNomineeInfo.setNomineeName(UPDATED_NOMINEE_NAME);
        hrNomineeInfo.setNomineeNameBn(UPDATED_NOMINEE_NAME_BN);
        hrNomineeInfo.setBirthDate(UPDATED_BIRTH_DATE);
        hrNomineeInfo.setGender(UPDATED_GENDER);
        hrNomineeInfo.setOccupation(UPDATED_OCCUPATION);
        hrNomineeInfo.setDesignation(UPDATED_DESIGNATION);
        hrNomineeInfo.setNationalId(UPDATED_NATIONAL_ID);
        hrNomineeInfo.setMobileNumber(UPDATED_MOBILE_NUMBER);
        hrNomineeInfo.setAddress(UPDATED_ADDRESS);
        hrNomineeInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrNomineeInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrNomineeInfo.setCreateBy(UPDATED_CREATE_BY);
        hrNomineeInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrNomineeInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrNomineeInfoMockMvc.perform(put("/api/hrNomineeInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrNomineeInfo)))
                .andExpect(status().isOk());

        // Validate the HrNomineeInfo in the database
        List<HrNomineeInfo> hrNomineeInfos = hrNomineeInfoRepository.findAll();
        assertThat(hrNomineeInfos).hasSize(databaseSizeBeforeUpdate);
        HrNomineeInfo testHrNomineeInfo = hrNomineeInfos.get(hrNomineeInfos.size() - 1);
        assertThat(testHrNomineeInfo.getNomineeName()).isEqualTo(UPDATED_NOMINEE_NAME);
        assertThat(testHrNomineeInfo.getNomineeNameBn()).isEqualTo(UPDATED_NOMINEE_NAME_BN);
        assertThat(testHrNomineeInfo.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testHrNomineeInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testHrNomineeInfo.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testHrNomineeInfo.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testHrNomineeInfo.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testHrNomineeInfo.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testHrNomineeInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHrNomineeInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrNomineeInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrNomineeInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrNomineeInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrNomineeInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrNomineeInfo() throws Exception {
        // Initialize the database
        hrNomineeInfoRepository.saveAndFlush(hrNomineeInfo);

		int databaseSizeBeforeDelete = hrNomineeInfoRepository.findAll().size();

        // Get the hrNomineeInfo
        restHrNomineeInfoMockMvc.perform(delete("/api/hrNomineeInfos/{id}", hrNomineeInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrNomineeInfo> hrNomineeInfos = hrNomineeInfoRepository.findAll();
        assertThat(hrNomineeInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
