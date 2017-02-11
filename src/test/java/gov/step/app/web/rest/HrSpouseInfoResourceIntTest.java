package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrSpouseInfo;
import gov.step.app.domain.enumeration.Gender;
import gov.step.app.repository.HrSpouseInfoRepository;
import gov.step.app.repository.search.HrSpouseInfoSearchRepository;
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
 * Test class for the HrSpouseInfoResource REST controller.
 *
 * @see HrSpouseInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrSpouseInfoResourceIntTest {

    private static final String DEFAULT_SPOUSE_NAME = "AAAAA";
    private static final String UPDATED_SPOUSE_NAME = "BBBBB";
    private static final String DEFAULT_SPOUSE_NAME_BN = "AAAAA";
    private static final String UPDATED_SPOUSE_NAME_BN = "BBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());


    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;
    private static final String DEFAULT_RELATIONSHIP = "AAAAA";
    private static final String UPDATED_RELATIONSHIP = "BBBBB";

    private static final Boolean DEFAULT_IS_NOMINEE = false;
    private static final Boolean UPDATED_IS_NOMINEE = true;
    private static final String DEFAULT_OCCUPATION = "AAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBB";
    private static final String DEFAULT_ORGANIZATION = "AAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBBBBB";
    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBB";
    private static final String DEFAULT_CONTACT_NUMBER = "AAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final Boolean DEFAULT_OPEN_FOR_EDIT = false;
    private static final Boolean UPDATED_OPEN_FOR_EDIT = true;

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
    private HrSpouseInfoRepository hrSpouseInfoRepository;

    @Inject
    private HrSpouseInfoSearchRepository hrSpouseInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrSpouseInfoMockMvc;

    private HrSpouseInfo hrSpouseInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrSpouseInfoResource hrSpouseInfoResource = new HrSpouseInfoResource();
        ReflectionTestUtils.setField(hrSpouseInfoResource, "hrSpouseInfoSearchRepository", hrSpouseInfoSearchRepository);
        ReflectionTestUtils.setField(hrSpouseInfoResource, "hrSpouseInfoRepository", hrSpouseInfoRepository);
        this.restHrSpouseInfoMockMvc = MockMvcBuilders.standaloneSetup(hrSpouseInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrSpouseInfo = new HrSpouseInfo();
        hrSpouseInfo.setSpouseName(DEFAULT_SPOUSE_NAME);
        hrSpouseInfo.setSpouseNameBn(DEFAULT_SPOUSE_NAME_BN);
        hrSpouseInfo.setBirthDate(DEFAULT_BIRTH_DATE);
        hrSpouseInfo.setGender(DEFAULT_GENDER);
        hrSpouseInfo.setRelationship(DEFAULT_RELATIONSHIP);
        hrSpouseInfo.setIsNominee(DEFAULT_IS_NOMINEE);
        hrSpouseInfo.setOccupation(DEFAULT_OCCUPATION);
        hrSpouseInfo.setOrganization(DEFAULT_ORGANIZATION);
        hrSpouseInfo.setDesignation(DEFAULT_DESIGNATION);
        hrSpouseInfo.setNationalId(DEFAULT_NATIONAL_ID);
        hrSpouseInfo.setEmailAddress(DEFAULT_EMAIL_ADDRESS);
        hrSpouseInfo.setContactNumber(DEFAULT_CONTACT_NUMBER);
        hrSpouseInfo.setAddress(DEFAULT_ADDRESS);
        hrSpouseInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrSpouseInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrSpouseInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrSpouseInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrSpouseInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrSpouseInfo() throws Exception {
        int databaseSizeBeforeCreate = hrSpouseInfoRepository.findAll().size();

        // Create the HrSpouseInfo

        restHrSpouseInfoMockMvc.perform(post("/api/hrSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrSpouseInfo)))
                .andExpect(status().isCreated());

        // Validate the HrSpouseInfo in the database
        List<HrSpouseInfo> hrSpouseInfos = hrSpouseInfoRepository.findAll();
        assertThat(hrSpouseInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrSpouseInfo testHrSpouseInfo = hrSpouseInfos.get(hrSpouseInfos.size() - 1);
        assertThat(testHrSpouseInfo.getSpouseName()).isEqualTo(DEFAULT_SPOUSE_NAME);
        assertThat(testHrSpouseInfo.getSpouseNameBn()).isEqualTo(DEFAULT_SPOUSE_NAME_BN);
        assertThat(testHrSpouseInfo.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testHrSpouseInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testHrSpouseInfo.getRelationship()).isEqualTo(DEFAULT_RELATIONSHIP);
        assertThat(testHrSpouseInfo.getIsNominee()).isEqualTo(DEFAULT_IS_NOMINEE);
        assertThat(testHrSpouseInfo.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testHrSpouseInfo.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testHrSpouseInfo.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testHrSpouseInfo.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testHrSpouseInfo.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testHrSpouseInfo.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testHrSpouseInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHrSpouseInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrSpouseInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrSpouseInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrSpouseInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrSpouseInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkSpouseNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrSpouseInfoRepository.findAll().size();
        // set the field null
        hrSpouseInfo.setSpouseName(null);

        // Create the HrSpouseInfo, which fails.

        restHrSpouseInfoMockMvc.perform(post("/api/hrSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<HrSpouseInfo> hrSpouseInfos = hrSpouseInfoRepository.findAll();
        assertThat(hrSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrSpouseInfoRepository.findAll().size();
        // set the field null
        hrSpouseInfo.setBirthDate(null);

        // Create the HrSpouseInfo, which fails.

        restHrSpouseInfoMockMvc.perform(post("/api/hrSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<HrSpouseInfo> hrSpouseInfos = hrSpouseInfoRepository.findAll();
        assertThat(hrSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrSpouseInfoRepository.findAll().size();
        // set the field null
        hrSpouseInfo.setGender(null);

        // Create the HrSpouseInfo, which fails.

        restHrSpouseInfoMockMvc.perform(post("/api/hrSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<HrSpouseInfo> hrSpouseInfos = hrSpouseInfoRepository.findAll();
        assertThat(hrSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRelationshipIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrSpouseInfoRepository.findAll().size();
        // set the field null
        hrSpouseInfo.setRelationship(null);

        // Create the HrSpouseInfo, which fails.

        restHrSpouseInfoMockMvc.perform(post("/api/hrSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<HrSpouseInfo> hrSpouseInfos = hrSpouseInfoRepository.findAll();
        assertThat(hrSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrSpouseInfoRepository.findAll().size();
        // set the field null
        hrSpouseInfo.setEmailAddress(null);

        // Create the HrSpouseInfo, which fails.

        restHrSpouseInfoMockMvc.perform(post("/api/hrSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<HrSpouseInfo> hrSpouseInfos = hrSpouseInfoRepository.findAll();
        assertThat(hrSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrSpouseInfoRepository.findAll().size();
        // set the field null
        hrSpouseInfo.setContactNumber(null);

        // Create the HrSpouseInfo, which fails.

        restHrSpouseInfoMockMvc.perform(post("/api/hrSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<HrSpouseInfo> hrSpouseInfos = hrSpouseInfoRepository.findAll();
        assertThat(hrSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrSpouseInfoRepository.findAll().size();
        // set the field null
        hrSpouseInfo.setActiveStatus(null);

        // Create the HrSpouseInfo, which fails.

        restHrSpouseInfoMockMvc.perform(post("/api/hrSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<HrSpouseInfo> hrSpouseInfos = hrSpouseInfoRepository.findAll();
        assertThat(hrSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrSpouseInfos() throws Exception {
        // Initialize the database
        hrSpouseInfoRepository.saveAndFlush(hrSpouseInfo);

        // Get all the hrSpouseInfos
        restHrSpouseInfoMockMvc.perform(get("/api/hrSpouseInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrSpouseInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].spouseName").value(hasItem(DEFAULT_SPOUSE_NAME.toString())))
                .andExpect(jsonPath("$.[*].spouseNameBn").value(hasItem(DEFAULT_SPOUSE_NAME_BN.toString())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].relationship").value(hasItem(DEFAULT_RELATIONSHIP.toString())))
                .andExpect(jsonPath("$.[*].isNominee").value(hasItem(DEFAULT_IS_NOMINEE.booleanValue())))
                .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
                .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID.toString())))
                .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrSpouseInfo() throws Exception {
        // Initialize the database
        hrSpouseInfoRepository.saveAndFlush(hrSpouseInfo);

        // Get the hrSpouseInfo
        restHrSpouseInfoMockMvc.perform(get("/api/hrSpouseInfos/{id}", hrSpouseInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrSpouseInfo.getId().intValue()))
            .andExpect(jsonPath("$.spouseName").value(DEFAULT_SPOUSE_NAME.toString()))
            .andExpect(jsonPath("$.spouseNameBn").value(DEFAULT_SPOUSE_NAME_BN.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.relationship").value(DEFAULT_RELATIONSHIP.toString()))
            .andExpect(jsonPath("$.isNominee").value(DEFAULT_IS_NOMINEE.booleanValue()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrSpouseInfo() throws Exception {
        // Get the hrSpouseInfo
        restHrSpouseInfoMockMvc.perform(get("/api/hrSpouseInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrSpouseInfo() throws Exception {
        // Initialize the database
        hrSpouseInfoRepository.saveAndFlush(hrSpouseInfo);

		int databaseSizeBeforeUpdate = hrSpouseInfoRepository.findAll().size();

        // Update the hrSpouseInfo
        hrSpouseInfo.setSpouseName(UPDATED_SPOUSE_NAME);
        hrSpouseInfo.setSpouseNameBn(UPDATED_SPOUSE_NAME_BN);
        hrSpouseInfo.setBirthDate(UPDATED_BIRTH_DATE);
        hrSpouseInfo.setGender(UPDATED_GENDER);
        hrSpouseInfo.setRelationship(UPDATED_RELATIONSHIP);
        hrSpouseInfo.setIsNominee(UPDATED_IS_NOMINEE);
        hrSpouseInfo.setOccupation(UPDATED_OCCUPATION);
        hrSpouseInfo.setOrganization(UPDATED_ORGANIZATION);
        hrSpouseInfo.setDesignation(UPDATED_DESIGNATION);
        hrSpouseInfo.setNationalId(UPDATED_NATIONAL_ID);
        hrSpouseInfo.setEmailAddress(UPDATED_EMAIL_ADDRESS);
        hrSpouseInfo.setContactNumber(UPDATED_CONTACT_NUMBER);
        hrSpouseInfo.setAddress(UPDATED_ADDRESS);
        hrSpouseInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrSpouseInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrSpouseInfo.setCreateBy(UPDATED_CREATE_BY);
        hrSpouseInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrSpouseInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrSpouseInfoMockMvc.perform(put("/api/hrSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrSpouseInfo)))
                .andExpect(status().isOk());

        // Validate the HrSpouseInfo in the database
        List<HrSpouseInfo> hrSpouseInfos = hrSpouseInfoRepository.findAll();
        assertThat(hrSpouseInfos).hasSize(databaseSizeBeforeUpdate);
        HrSpouseInfo testHrSpouseInfo = hrSpouseInfos.get(hrSpouseInfos.size() - 1);
        assertThat(testHrSpouseInfo.getSpouseName()).isEqualTo(UPDATED_SPOUSE_NAME);
        assertThat(testHrSpouseInfo.getSpouseNameBn()).isEqualTo(UPDATED_SPOUSE_NAME_BN);
        assertThat(testHrSpouseInfo.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testHrSpouseInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testHrSpouseInfo.getRelationship()).isEqualTo(UPDATED_RELATIONSHIP);
        assertThat(testHrSpouseInfo.getIsNominee()).isEqualTo(UPDATED_IS_NOMINEE);
        assertThat(testHrSpouseInfo.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testHrSpouseInfo.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testHrSpouseInfo.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testHrSpouseInfo.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testHrSpouseInfo.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testHrSpouseInfo.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testHrSpouseInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHrSpouseInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrSpouseInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrSpouseInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrSpouseInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrSpouseInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrSpouseInfo() throws Exception {
        // Initialize the database
        hrSpouseInfoRepository.saveAndFlush(hrSpouseInfo);

		int databaseSizeBeforeDelete = hrSpouseInfoRepository.findAll().size();

        // Get the hrSpouseInfo
        restHrSpouseInfoMockMvc.perform(delete("/api/hrSpouseInfos/{id}", hrSpouseInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrSpouseInfo> hrSpouseInfos = hrSpouseInfoRepository.findAll();
        assertThat(hrSpouseInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
