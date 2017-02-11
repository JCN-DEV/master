package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpChildrenInfo;
import gov.step.app.domain.enumeration.Gender;
import gov.step.app.repository.HrEmpChildrenInfoRepository;
import gov.step.app.repository.search.HrEmpChildrenInfoSearchRepository;
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
 * Test class for the HrEmpChildrenInfoResource REST controller.
 *
 * @see HrEmpChildrenInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpChildrenInfoResourceIntTest {

    private static final String DEFAULT_CHILDREN_NAME = "AAAAA";
    private static final String UPDATED_CHILDREN_NAME = "BBBBB";
    private static final String DEFAULT_CHILDREN_NAME_BN = "AAAAA";
    private static final String UPDATED_CHILDREN_NAME_BN = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());


    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;

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
    private HrEmpChildrenInfoRepository hrEmpChildrenInfoRepository;

    @Inject
    private HrEmpChildrenInfoSearchRepository hrEmpChildrenInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpChildrenInfoMockMvc;

    private HrEmpChildrenInfo hrEmpChildrenInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpChildrenInfoResource hrEmpChildrenInfoResource = new HrEmpChildrenInfoResource();
        ReflectionTestUtils.setField(hrEmpChildrenInfoResource, "hrEmpChildrenInfoSearchRepository", hrEmpChildrenInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpChildrenInfoResource, "hrEmpChildrenInfoRepository", hrEmpChildrenInfoRepository);
        this.restHrEmpChildrenInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpChildrenInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpChildrenInfo = new HrEmpChildrenInfo();
        hrEmpChildrenInfo.setChildrenName(DEFAULT_CHILDREN_NAME);
        hrEmpChildrenInfo.setChildrenNameBn(DEFAULT_CHILDREN_NAME_BN);
        hrEmpChildrenInfo.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
        hrEmpChildrenInfo.setGender(DEFAULT_GENDER);
        hrEmpChildrenInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpChildrenInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpChildrenInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpChildrenInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpChildrenInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpChildrenInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpChildrenInfoRepository.findAll().size();

        // Create the HrEmpChildrenInfo

        restHrEmpChildrenInfoMockMvc.perform(post("/api/hrEmpChildrenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpChildrenInfo in the database
        List<HrEmpChildrenInfo> hrEmpChildrenInfos = hrEmpChildrenInfoRepository.findAll();
        assertThat(hrEmpChildrenInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpChildrenInfo testHrEmpChildrenInfo = hrEmpChildrenInfos.get(hrEmpChildrenInfos.size() - 1);
        assertThat(testHrEmpChildrenInfo.getChildrenName()).isEqualTo(DEFAULT_CHILDREN_NAME);
        assertThat(testHrEmpChildrenInfo.getChildrenNameBn()).isEqualTo(DEFAULT_CHILDREN_NAME_BN);
        assertThat(testHrEmpChildrenInfo.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testHrEmpChildrenInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testHrEmpChildrenInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpChildrenInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpChildrenInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpChildrenInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpChildrenInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkChildrenNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpChildrenInfoRepository.findAll().size();
        // set the field null
        hrEmpChildrenInfo.setChildrenName(null);

        // Create the HrEmpChildrenInfo, which fails.

        restHrEmpChildrenInfoMockMvc.perform(post("/api/hrEmpChildrenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpChildrenInfo> hrEmpChildrenInfos = hrEmpChildrenInfoRepository.findAll();
        assertThat(hrEmpChildrenInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpChildrenInfoRepository.findAll().size();
        // set the field null
        hrEmpChildrenInfo.setDateOfBirth(null);

        // Create the HrEmpChildrenInfo, which fails.

        restHrEmpChildrenInfoMockMvc.perform(post("/api/hrEmpChildrenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpChildrenInfo> hrEmpChildrenInfos = hrEmpChildrenInfoRepository.findAll();
        assertThat(hrEmpChildrenInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpChildrenInfoRepository.findAll().size();
        // set the field null
        hrEmpChildrenInfo.setGender(null);

        // Create the HrEmpChildrenInfo, which fails.

        restHrEmpChildrenInfoMockMvc.perform(post("/api/hrEmpChildrenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpChildrenInfo> hrEmpChildrenInfos = hrEmpChildrenInfoRepository.findAll();
        assertThat(hrEmpChildrenInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpChildrenInfoRepository.findAll().size();
        // set the field null
        hrEmpChildrenInfo.setActiveStatus(null);

        // Create the HrEmpChildrenInfo, which fails.

        restHrEmpChildrenInfoMockMvc.perform(post("/api/hrEmpChildrenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpChildrenInfo> hrEmpChildrenInfos = hrEmpChildrenInfoRepository.findAll();
        assertThat(hrEmpChildrenInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpChildrenInfos() throws Exception {
        // Initialize the database
        hrEmpChildrenInfoRepository.saveAndFlush(hrEmpChildrenInfo);

        // Get all the hrEmpChildrenInfos
        restHrEmpChildrenInfoMockMvc.perform(get("/api/hrEmpChildrenInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpChildrenInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].childrenName").value(hasItem(DEFAULT_CHILDREN_NAME.toString())))
                .andExpect(jsonPath("$.[*].childrenNameBn").value(hasItem(DEFAULT_CHILDREN_NAME_BN.toString())))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmpChildrenInfo() throws Exception {
        // Initialize the database
        hrEmpChildrenInfoRepository.saveAndFlush(hrEmpChildrenInfo);

        // Get the hrEmpChildrenInfo
        restHrEmpChildrenInfoMockMvc.perform(get("/api/hrEmpChildrenInfos/{id}", hrEmpChildrenInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpChildrenInfo.getId().intValue()))
            .andExpect(jsonPath("$.childrenName").value(DEFAULT_CHILDREN_NAME.toString()))
            .andExpect(jsonPath("$.childrenNameBn").value(DEFAULT_CHILDREN_NAME_BN.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmpChildrenInfo() throws Exception {
        // Get the hrEmpChildrenInfo
        restHrEmpChildrenInfoMockMvc.perform(get("/api/hrEmpChildrenInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpChildrenInfo() throws Exception {
        // Initialize the database
        hrEmpChildrenInfoRepository.saveAndFlush(hrEmpChildrenInfo);

		int databaseSizeBeforeUpdate = hrEmpChildrenInfoRepository.findAll().size();

        // Update the hrEmpChildrenInfo
        hrEmpChildrenInfo.setChildrenName(UPDATED_CHILDREN_NAME);
        hrEmpChildrenInfo.setChildrenNameBn(UPDATED_CHILDREN_NAME_BN);
        hrEmpChildrenInfo.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        hrEmpChildrenInfo.setGender(UPDATED_GENDER);
        hrEmpChildrenInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpChildrenInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpChildrenInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpChildrenInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpChildrenInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpChildrenInfoMockMvc.perform(put("/api/hrEmpChildrenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpChildrenInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpChildrenInfo in the database
        List<HrEmpChildrenInfo> hrEmpChildrenInfos = hrEmpChildrenInfoRepository.findAll();
        assertThat(hrEmpChildrenInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpChildrenInfo testHrEmpChildrenInfo = hrEmpChildrenInfos.get(hrEmpChildrenInfos.size() - 1);
        assertThat(testHrEmpChildrenInfo.getChildrenName()).isEqualTo(UPDATED_CHILDREN_NAME);
        assertThat(testHrEmpChildrenInfo.getChildrenNameBn()).isEqualTo(UPDATED_CHILDREN_NAME_BN);
        assertThat(testHrEmpChildrenInfo.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testHrEmpChildrenInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testHrEmpChildrenInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpChildrenInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpChildrenInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpChildrenInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpChildrenInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpChildrenInfo() throws Exception {
        // Initialize the database
        hrEmpChildrenInfoRepository.saveAndFlush(hrEmpChildrenInfo);

		int databaseSizeBeforeDelete = hrEmpChildrenInfoRepository.findAll().size();

        // Get the hrEmpChildrenInfo
        restHrEmpChildrenInfoMockMvc.perform(delete("/api/hrEmpChildrenInfos/{id}", hrEmpChildrenInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpChildrenInfo> hrEmpChildrenInfos = hrEmpChildrenInfoRepository.findAll();
        assertThat(hrEmpChildrenInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
