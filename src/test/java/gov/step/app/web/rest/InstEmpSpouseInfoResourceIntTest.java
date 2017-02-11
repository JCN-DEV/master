package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmpSpouseInfo;
import gov.step.app.repository.InstEmpSpouseInfoRepository;
import gov.step.app.repository.search.InstEmpSpouseInfoSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.step.app.domain.enumeration.relationType;

/**
 * Test class for the InstEmpSpouseInfoResource REST controller.
 *
 * @see InstEmpSpouseInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmpSpouseInfoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_NOMINEE = false;
    private static final Boolean UPDATED_IS_NOMINEE = true;
    private static final String DEFAULT_GENDER = "AAAAA";
    private static final String UPDATED_GENDER = "BBBBB";


private static final relationType DEFAULT_RELATION = relationType.Husband;
    private static final relationType UPDATED_RELATION = relationType.Wife;

    private static final BigDecimal DEFAULT_NOMINEE_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_NOMINEE_PERCENTAGE = new BigDecimal(2);
    private static final String DEFAULT_OCCUPATION = "AAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBB";
    private static final String DEFAULT_TIN = "AAAAA";
    private static final String UPDATED_TIN = "BBBBB";
    private static final String DEFAULT_NID = "AAAAA";
    private static final String UPDATED_NID = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_GOV_JOB_ID = "AAAAA";
    private static final String UPDATED_GOV_JOB_ID = "BBBBB";

    private static final Integer DEFAULT_MOBILE = 1;
    private static final Integer UPDATED_MOBILE = 2;
    private static final String DEFAULT_OFFICE_CONTACT = "AAAAA";
    private static final String UPDATED_OFFICE_CONTACT = "BBBBB";

    @Inject
    private InstEmpSpouseInfoRepository instEmpSpouseInfoRepository;

    @Inject
    private InstEmpSpouseInfoSearchRepository instEmpSpouseInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmpSpouseInfoMockMvc;

    private InstEmpSpouseInfo instEmpSpouseInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmpSpouseInfoResource instEmpSpouseInfoResource = new InstEmpSpouseInfoResource();
        ReflectionTestUtils.setField(instEmpSpouseInfoResource, "instEmpSpouseInfoRepository", instEmpSpouseInfoRepository);
        ReflectionTestUtils.setField(instEmpSpouseInfoResource, "instEmpSpouseInfoSearchRepository", instEmpSpouseInfoSearchRepository);
        this.restInstEmpSpouseInfoMockMvc = MockMvcBuilders.standaloneSetup(instEmpSpouseInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmpSpouseInfo = new InstEmpSpouseInfo();
        instEmpSpouseInfo.setName(DEFAULT_NAME);
        instEmpSpouseInfo.setDob(DEFAULT_DOB);
        instEmpSpouseInfo.setIsNominee(DEFAULT_IS_NOMINEE);
        instEmpSpouseInfo.setGender(DEFAULT_GENDER);
        instEmpSpouseInfo.setRelation(DEFAULT_RELATION);
        instEmpSpouseInfo.setNomineePercentage(DEFAULT_NOMINEE_PERCENTAGE);
        instEmpSpouseInfo.setOccupation(DEFAULT_OCCUPATION);
        instEmpSpouseInfo.setTin(DEFAULT_TIN);
        instEmpSpouseInfo.setNid(DEFAULT_NID);
        instEmpSpouseInfo.setDesignation(DEFAULT_DESIGNATION);
        instEmpSpouseInfo.setGovJobId(DEFAULT_GOV_JOB_ID);
        instEmpSpouseInfo.setMobile(DEFAULT_MOBILE);
        instEmpSpouseInfo.setOfficeContact(DEFAULT_OFFICE_CONTACT);
    }

    @Test
    @Transactional
    public void createInstEmpSpouseInfo() throws Exception {
        int databaseSizeBeforeCreate = instEmpSpouseInfoRepository.findAll().size();

        // Create the InstEmpSpouseInfo

        restInstEmpSpouseInfoMockMvc.perform(post("/api/instEmpSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpSpouseInfo)))
                .andExpect(status().isCreated());

        // Validate the InstEmpSpouseInfo in the database
        List<InstEmpSpouseInfo> instEmpSpouseInfos = instEmpSpouseInfoRepository.findAll();
        assertThat(instEmpSpouseInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstEmpSpouseInfo testInstEmpSpouseInfo = instEmpSpouseInfos.get(instEmpSpouseInfos.size() - 1);
        assertThat(testInstEmpSpouseInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstEmpSpouseInfo.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testInstEmpSpouseInfo.getIsNominee()).isEqualTo(DEFAULT_IS_NOMINEE);
        assertThat(testInstEmpSpouseInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testInstEmpSpouseInfo.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testInstEmpSpouseInfo.getNomineePercentage()).isEqualTo(DEFAULT_NOMINEE_PERCENTAGE);
        assertThat(testInstEmpSpouseInfo.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testInstEmpSpouseInfo.getTin()).isEqualTo(DEFAULT_TIN);
        assertThat(testInstEmpSpouseInfo.getNid()).isEqualTo(DEFAULT_NID);
        assertThat(testInstEmpSpouseInfo.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testInstEmpSpouseInfo.getGovJobId()).isEqualTo(DEFAULT_GOV_JOB_ID);
        assertThat(testInstEmpSpouseInfo.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testInstEmpSpouseInfo.getOfficeContact()).isEqualTo(DEFAULT_OFFICE_CONTACT);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmpSpouseInfoRepository.findAll().size();
        // set the field null
        instEmpSpouseInfo.setName(null);

        // Create the InstEmpSpouseInfo, which fails.

        restInstEmpSpouseInfoMockMvc.perform(post("/api/instEmpSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmpSpouseInfo> instEmpSpouseInfos = instEmpSpouseInfoRepository.findAll();
        assertThat(instEmpSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmpSpouseInfoRepository.findAll().size();
        // set the field null
        instEmpSpouseInfo.setDob(null);

        // Create the InstEmpSpouseInfo, which fails.

        restInstEmpSpouseInfoMockMvc.perform(post("/api/instEmpSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmpSpouseInfo> instEmpSpouseInfos = instEmpSpouseInfoRepository.findAll();
        assertThat(instEmpSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsNomineeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmpSpouseInfoRepository.findAll().size();
        // set the field null
        instEmpSpouseInfo.setIsNominee(null);

        // Create the InstEmpSpouseInfo, which fails.

        restInstEmpSpouseInfoMockMvc.perform(post("/api/instEmpSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmpSpouseInfo> instEmpSpouseInfos = instEmpSpouseInfoRepository.findAll();
        assertThat(instEmpSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmpSpouseInfoRepository.findAll().size();
        // set the field null
        instEmpSpouseInfo.setGender(null);

        // Create the InstEmpSpouseInfo, which fails.

        restInstEmpSpouseInfoMockMvc.perform(post("/api/instEmpSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmpSpouseInfo> instEmpSpouseInfos = instEmpSpouseInfoRepository.findAll();
        assertThat(instEmpSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRelationIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmpSpouseInfoRepository.findAll().size();
        // set the field null
        instEmpSpouseInfo.setRelation(null);

        // Create the InstEmpSpouseInfo, which fails.

        restInstEmpSpouseInfoMockMvc.perform(post("/api/instEmpSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmpSpouseInfo> instEmpSpouseInfos = instEmpSpouseInfoRepository.findAll();
        assertThat(instEmpSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNidIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmpSpouseInfoRepository.findAll().size();
        // set the field null
        instEmpSpouseInfo.setNid(null);

        // Create the InstEmpSpouseInfo, which fails.

        restInstEmpSpouseInfoMockMvc.perform(post("/api/instEmpSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpSpouseInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmpSpouseInfo> instEmpSpouseInfos = instEmpSpouseInfoRepository.findAll();
        assertThat(instEmpSpouseInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstEmpSpouseInfos() throws Exception {
        // Initialize the database
        instEmpSpouseInfoRepository.saveAndFlush(instEmpSpouseInfo);

        // Get all the instEmpSpouseInfos
        restInstEmpSpouseInfoMockMvc.perform(get("/api/instEmpSpouseInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmpSpouseInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
                .andExpect(jsonPath("$.[*].isNominee").value(hasItem(DEFAULT_IS_NOMINEE.booleanValue())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())))
                .andExpect(jsonPath("$.[*].nomineePercentage").value(hasItem(DEFAULT_NOMINEE_PERCENTAGE.intValue())))
                .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
                .andExpect(jsonPath("$.[*].tin").value(hasItem(DEFAULT_TIN.toString())))
                .andExpect(jsonPath("$.[*].nid").value(hasItem(DEFAULT_NID.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].govJobId").value(hasItem(DEFAULT_GOV_JOB_ID.toString())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
                .andExpect(jsonPath("$.[*].officeContact").value(hasItem(DEFAULT_OFFICE_CONTACT.toString())));
    }

    @Test
    @Transactional
    public void getInstEmpSpouseInfo() throws Exception {
        // Initialize the database
        instEmpSpouseInfoRepository.saveAndFlush(instEmpSpouseInfo);

        // Get the instEmpSpouseInfo
        restInstEmpSpouseInfoMockMvc.perform(get("/api/instEmpSpouseInfos/{id}", instEmpSpouseInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmpSpouseInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.isNominee").value(DEFAULT_IS_NOMINEE.booleanValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()))
            .andExpect(jsonPath("$.nomineePercentage").value(DEFAULT_NOMINEE_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.tin").value(DEFAULT_TIN.toString()))
            .andExpect(jsonPath("$.nid").value(DEFAULT_NID.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.govJobId").value(DEFAULT_GOV_JOB_ID.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.officeContact").value(DEFAULT_OFFICE_CONTACT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmpSpouseInfo() throws Exception {
        // Get the instEmpSpouseInfo
        restInstEmpSpouseInfoMockMvc.perform(get("/api/instEmpSpouseInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmpSpouseInfo() throws Exception {
        // Initialize the database
        instEmpSpouseInfoRepository.saveAndFlush(instEmpSpouseInfo);

		int databaseSizeBeforeUpdate = instEmpSpouseInfoRepository.findAll().size();

        // Update the instEmpSpouseInfo
        instEmpSpouseInfo.setName(UPDATED_NAME);
        instEmpSpouseInfo.setDob(UPDATED_DOB);
        instEmpSpouseInfo.setIsNominee(UPDATED_IS_NOMINEE);
        instEmpSpouseInfo.setGender(UPDATED_GENDER);
        instEmpSpouseInfo.setRelation(UPDATED_RELATION);
        instEmpSpouseInfo.setNomineePercentage(UPDATED_NOMINEE_PERCENTAGE);
        instEmpSpouseInfo.setOccupation(UPDATED_OCCUPATION);
        instEmpSpouseInfo.setTin(UPDATED_TIN);
        instEmpSpouseInfo.setNid(UPDATED_NID);
        instEmpSpouseInfo.setDesignation(UPDATED_DESIGNATION);
        instEmpSpouseInfo.setGovJobId(UPDATED_GOV_JOB_ID);
        instEmpSpouseInfo.setMobile(UPDATED_MOBILE);
        instEmpSpouseInfo.setOfficeContact(UPDATED_OFFICE_CONTACT);

        restInstEmpSpouseInfoMockMvc.perform(put("/api/instEmpSpouseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpSpouseInfo)))
                .andExpect(status().isOk());

        // Validate the InstEmpSpouseInfo in the database
        List<InstEmpSpouseInfo> instEmpSpouseInfos = instEmpSpouseInfoRepository.findAll();
        assertThat(instEmpSpouseInfos).hasSize(databaseSizeBeforeUpdate);
        InstEmpSpouseInfo testInstEmpSpouseInfo = instEmpSpouseInfos.get(instEmpSpouseInfos.size() - 1);
        assertThat(testInstEmpSpouseInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstEmpSpouseInfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testInstEmpSpouseInfo.getIsNominee()).isEqualTo(UPDATED_IS_NOMINEE);
        assertThat(testInstEmpSpouseInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testInstEmpSpouseInfo.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testInstEmpSpouseInfo.getNomineePercentage()).isEqualTo(UPDATED_NOMINEE_PERCENTAGE);
        assertThat(testInstEmpSpouseInfo.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testInstEmpSpouseInfo.getTin()).isEqualTo(UPDATED_TIN);
        assertThat(testInstEmpSpouseInfo.getNid()).isEqualTo(UPDATED_NID);
        assertThat(testInstEmpSpouseInfo.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testInstEmpSpouseInfo.getGovJobId()).isEqualTo(UPDATED_GOV_JOB_ID);
        assertThat(testInstEmpSpouseInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testInstEmpSpouseInfo.getOfficeContact()).isEqualTo(UPDATED_OFFICE_CONTACT);
    }

    @Test
    @Transactional
    public void deleteInstEmpSpouseInfo() throws Exception {
        // Initialize the database
        instEmpSpouseInfoRepository.saveAndFlush(instEmpSpouseInfo);

		int databaseSizeBeforeDelete = instEmpSpouseInfoRepository.findAll().size();

        // Get the instEmpSpouseInfo
        restInstEmpSpouseInfoMockMvc.perform(delete("/api/instEmpSpouseInfos/{id}", instEmpSpouseInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmpSpouseInfo> instEmpSpouseInfos = instEmpSpouseInfoRepository.findAll();
        assertThat(instEmpSpouseInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
