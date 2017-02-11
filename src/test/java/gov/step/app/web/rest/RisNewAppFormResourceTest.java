/*
package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.RisNewAppForm;
import gov.step.app.repository.RisNewAppFormRepository;
import gov.step.app.repository.search.RisNewAppFormSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.step.app.domain.enumeration.religion;

*/
/**
 * Test class for the RisNewAppFormResource REST controller.
 *
 * @see RisNewAppFormResource
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RisNewAppFormResourceTest {

    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_CIRCULAR_NO = "AAAAA";
    private static final String UPDATED_CIRCULAR_NO = "BBBBB";

    private static final LocalDate DEFAULT_APPLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLICATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_APPLICANTS_NAME_BN = "AAAAA";
    private static final String UPDATED_APPLICANTS_NAME_BN = "BBBBB";
    private static final String DEFAULT_APPLICANTS_NAME_EN = "AAAAA";
    private static final String UPDATED_APPLICANTS_NAME_EN = "BBBBB";

    private static final Long DEFAULT_NATIONAL_ID = 1L;
    private static final Long UPDATED_NATIONAL_ID = 2L;

    private static final Long DEFAULT_BIRTH_CERTIFICATE_NO = 1L;
    private static final Long UPDATED_BIRTH_CERTIFICATE_NO = 2L;

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_AGE = "AAAAA";
    private static final String UPDATED_AGE = "BBBBB";
    private static final String DEFAULT_FATHERS_NAME = "AAAAA";
    private static final String UPDATED_FATHERS_NAME = "BBBBB";
    private static final String DEFAULT_MOTHERS_NAME = "AAAAA";
    private static final String UPDATED_MOTHERS_NAME = "BBBBB";
    private static final String DEFAULT_HOLDING_NAME_BN_PRESENT = "AAAAA";
    private static final String UPDATED_HOLDING_NAME_BN_PRESENT = "BBBBB";
    private static final String DEFAULT_VILLAGE_BN_PRESENT = "AAAAA";
    private static final String UPDATED_VILLAGE_BN_PRESENT = "BBBBB";
    private static final String DEFAULT_UNION_BN_PRESENT = "AAAAA";
    private static final String UPDATED_UNION_BN_PRESENT = "BBBBB";
    private static final String DEFAULT_PO_BN_PRESENT = "AAAAA";
    private static final String UPDATED_PO_BN_PRESENT = "BBBBB";
    private static final String DEFAULT_PO_CODE_BN_PRESENT = "AAAAA";
    private static final String UPDATED_PO_CODE_BN_PRESENT = "BBBBB";
    private static final String DEFAULT_HOLDING_NAME_BN_PERMANENT = "AAAAA";
    private static final String UPDATED_HOLDING_NAME_BN_PERMANENT = "BBBBB";
    private static final String DEFAULT_VILLAGE_BN_PERMANENT = "AAAAA";
    private static final String UPDATED_VILLAGE_BN_PERMANENT = "BBBBB";
    private static final String DEFAULT_UNION_BN_PERMANENT = "AAAAA";
    private static final String UPDATED_UNION_BN_PERMANENT = "BBBBB";
    private static final String DEFAULT_PO_BN_PERMANENT = "AAAAA";
    private static final String UPDATED_PO_BN_PERMANENT = "BBBBB";
    private static final String DEFAULT_PO_CODE_BN_PERMANENT = "AAAAA";
    private static final String UPDATED_PO_CODE_BN_PERMANENT = "BBBBB";

    private static final Integer DEFAULT_CONTACT_PHONE = 1;
    private static final Integer UPDATED_CONTACT_PHONE = 2;
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_NATIONALITY = "AAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBB";
    private static final String DEFAULT_PROFESSION = "AAAAA";
    private static final String UPDATED_PROFESSION = "BBBBB";


private static final religion DEFAULT_RELIGION = religion.Islam;
    private static final religion UPDATED_RELIGION = religion.Hinduism;
    private static final String DEFAULT_HOLDING_NAME_EN_PRESENT = "AAAAA";
    private static final String UPDATED_HOLDING_NAME_EN_PRESENT = "BBBBB";
    private static final String DEFAULT_VILLAGE_EN_PRESENT = "AAAAA";
    private static final String UPDATED_VILLAGE_EN_PRESENT = "BBBBB";
    private static final String DEFAULT_UNION_EN_PRESENT = "AAAAA";
    private static final String UPDATED_UNION_EN_PRESENT = "BBBBB";
    private static final String DEFAULT_PO_EN_PRESENT = "AAAAA";
    private static final String UPDATED_PO_EN_PRESENT = "BBBBB";
    private static final String DEFAULT_PO_CODE_EN_PRESENT = "AAAAA";
    private static final String UPDATED_PO_CODE_EN_PRESENT = "BBBBB";
    private static final String DEFAULT_HOLDING_NAME_EN_PERMANENT = "AAAAA";
    private static final String UPDATED_HOLDING_NAME_EN_PERMANENT = "BBBBB";
    private static final String DEFAULT_VILLAGE_EN_PERMANENT = "AAAAA";
    private static final String UPDATED_VILLAGE_EN_PERMANENT = "BBBBB";
    private static final String DEFAULT_UNION_EN_PERMANENT = "AAAAA";
    private static final String UPDATED_UNION_EN_PERMANENT = "BBBBB";
    private static final String DEFAULT_PO_EN_PERMANENT = "AAAAA";
    private static final String UPDATED_PO_EN_PERMANENT = "BBBBB";
    private static final String DEFAULT_PO_CODE_EN_PERMANENT = "AAAAA";
    private static final String UPDATED_PO_CODE_EN_PERMANENT = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private RisNewAppFormRepository risNewAppFormRepository;

    @Inject
    private RisNewAppFormSearchRepository risNewAppFormSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRisNewAppFormMockMvc;

    private RisNewAppForm risNewAppForm;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RisNewAppFormResource risNewAppFormResource = new RisNewAppFormResource();
        ReflectionTestUtils.setField(risNewAppFormResource, "risNewAppFormRepository", risNewAppFormRepository);
        ReflectionTestUtils.setField(risNewAppFormResource, "risNewAppFormSearchRepository", risNewAppFormSearchRepository);
        this.restRisNewAppFormMockMvc = MockMvcBuilders.standaloneSetup(risNewAppFormResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        risNewAppForm = new RisNewAppForm();
        risNewAppForm.setDesignation(DEFAULT_DESIGNATION);
        risNewAppForm.setCircularNo(DEFAULT_CIRCULAR_NO);
        risNewAppForm.setApplicationDate(DEFAULT_APPLICATION_DATE);
        risNewAppForm.setApplicantsNameBn(DEFAULT_APPLICANTS_NAME_BN);
        risNewAppForm.setApplicantsNameEn(DEFAULT_APPLICANTS_NAME_EN);
        risNewAppForm.setNationalId(DEFAULT_NATIONAL_ID);
        risNewAppForm.setBirthCertificateNo(DEFAULT_BIRTH_CERTIFICATE_NO);
        risNewAppForm.setBirthDate(DEFAULT_BIRTH_DATE);
        risNewAppForm.setAge(DEFAULT_AGE);
        risNewAppForm.setFathersName(DEFAULT_FATHERS_NAME);
        risNewAppForm.setMothersName(DEFAULT_MOTHERS_NAME);
        risNewAppForm.setHoldingNameBnPresent(DEFAULT_HOLDING_NAME_BN_PRESENT);
        risNewAppForm.setVillageBnPresent(DEFAULT_VILLAGE_BN_PRESENT);
        risNewAppForm.setUnionBnPresent(DEFAULT_UNION_BN_PRESENT);
        risNewAppForm.setPoBnPresent(DEFAULT_PO_BN_PRESENT);
        risNewAppForm.setPoCodeBnPresent(DEFAULT_PO_CODE_BN_PRESENT);
        risNewAppForm.setHoldingNameBnPermanent(DEFAULT_HOLDING_NAME_BN_PERMANENT);
        risNewAppForm.setVillageBnPermanent(DEFAULT_VILLAGE_BN_PERMANENT);
        risNewAppForm.setUnionBnPermanent(DEFAULT_UNION_BN_PERMANENT);
        risNewAppForm.setPoBnPermanent(DEFAULT_PO_BN_PERMANENT);
        risNewAppForm.setPoCodeBnPermanent(DEFAULT_PO_CODE_BN_PERMANENT);
        risNewAppForm.setContactPhone(DEFAULT_CONTACT_PHONE);
        risNewAppForm.setEmail(DEFAULT_EMAIL);
        risNewAppForm.setNationality(DEFAULT_NATIONALITY);
        risNewAppForm.setProfession(DEFAULT_PROFESSION);
        risNewAppForm.setReligion(DEFAULT_RELIGION);
        risNewAppForm.setHoldingNameEnPresent(DEFAULT_HOLDING_NAME_EN_PRESENT);
        risNewAppForm.setVillageEnPresent(DEFAULT_VILLAGE_EN_PRESENT);
        risNewAppForm.setUnionEnPresent(DEFAULT_UNION_EN_PRESENT);
        risNewAppForm.setPoEnPresent(DEFAULT_PO_EN_PRESENT);
        risNewAppForm.setPoCodeEnPresent(DEFAULT_PO_CODE_EN_PRESENT);
        risNewAppForm.setHoldingNameEnPermanent(DEFAULT_HOLDING_NAME_EN_PERMANENT);
        risNewAppForm.setVillageEnPermanent(DEFAULT_VILLAGE_EN_PERMANENT);
        risNewAppForm.setUnionEnPermanent(DEFAULT_UNION_EN_PERMANENT);
        risNewAppForm.setPoEnPermanent(DEFAULT_PO_EN_PERMANENT);
        risNewAppForm.setPoCodeEnPermanent(DEFAULT_PO_CODE_EN_PERMANENT);
        risNewAppForm.setCreateDate(DEFAULT_CREATE_DATE);
        risNewAppForm.setCreateBy(DEFAULT_CREATE_BY);
        risNewAppForm.setUpdateDate(DEFAULT_UPDATE_DATE);
        risNewAppForm.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createRisNewAppForm() throws Exception {
        int databaseSizeBeforeCreate = risNewAppFormRepository.findAll().size();

        // Create the RisNewAppForm

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isCreated());

        // Validate the RisNewAppForm in the database
        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeCreate + 1);
        RisNewAppForm testRisNewAppForm = risNewAppForms.get(risNewAppForms.size() - 1);
        assertThat(testRisNewAppForm.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testRisNewAppForm.getCircularNo()).isEqualTo(DEFAULT_CIRCULAR_NO);
        assertThat(testRisNewAppForm.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testRisNewAppForm.getApplicantsNameBn()).isEqualTo(DEFAULT_APPLICANTS_NAME_BN);
        assertThat(testRisNewAppForm.getApplicantsNameEn()).isEqualTo(DEFAULT_APPLICANTS_NAME_EN);
        assertThat(testRisNewAppForm.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testRisNewAppForm.getBirthCertificateNo()).isEqualTo(DEFAULT_BIRTH_CERTIFICATE_NO);
        assertThat(testRisNewAppForm.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testRisNewAppForm.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testRisNewAppForm.getFathersName()).isEqualTo(DEFAULT_FATHERS_NAME);
        assertThat(testRisNewAppForm.getMothersName()).isEqualTo(DEFAULT_MOTHERS_NAME);
        assertThat(testRisNewAppForm.getHoldingNameBnPresent()).isEqualTo(DEFAULT_HOLDING_NAME_BN_PRESENT);
        assertThat(testRisNewAppForm.getVillageBnPresent()).isEqualTo(DEFAULT_VILLAGE_BN_PRESENT);
        assertThat(testRisNewAppForm.getUnionBnPresent()).isEqualTo(DEFAULT_UNION_BN_PRESENT);
        assertThat(testRisNewAppForm.getPoBnPresent()).isEqualTo(DEFAULT_PO_BN_PRESENT);
        assertThat(testRisNewAppForm.getPoCodeBnPresent()).isEqualTo(DEFAULT_PO_CODE_BN_PRESENT);
        assertThat(testRisNewAppForm.getHoldingNameBnPermanent()).isEqualTo(DEFAULT_HOLDING_NAME_BN_PERMANENT);
        assertThat(testRisNewAppForm.getVillageBnPermanent()).isEqualTo(DEFAULT_VILLAGE_BN_PERMANENT);
        assertThat(testRisNewAppForm.getUnionBnPermanent()).isEqualTo(DEFAULT_UNION_BN_PERMANENT);
        assertThat(testRisNewAppForm.getPoBnPermanent()).isEqualTo(DEFAULT_PO_BN_PERMANENT);
        assertThat(testRisNewAppForm.getPoCodeBnPermanent()).isEqualTo(DEFAULT_PO_CODE_BN_PERMANENT);
        assertThat(testRisNewAppForm.getContactPhone()).isEqualTo(DEFAULT_CONTACT_PHONE);
        assertThat(testRisNewAppForm.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRisNewAppForm.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testRisNewAppForm.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testRisNewAppForm.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testRisNewAppForm.getHoldingNameEnPresent()).isEqualTo(DEFAULT_HOLDING_NAME_EN_PRESENT);
        assertThat(testRisNewAppForm.getVillageEnPresent()).isEqualTo(DEFAULT_VILLAGE_EN_PRESENT);
        assertThat(testRisNewAppForm.getUnionEnPresent()).isEqualTo(DEFAULT_UNION_EN_PRESENT);
        assertThat(testRisNewAppForm.getPoEnPresent()).isEqualTo(DEFAULT_PO_EN_PRESENT);
        assertThat(testRisNewAppForm.getPoCodeEnPresent()).isEqualTo(DEFAULT_PO_CODE_EN_PRESENT);
        assertThat(testRisNewAppForm.getHoldingNameEnPermanent()).isEqualTo(DEFAULT_HOLDING_NAME_EN_PERMANENT);
        assertThat(testRisNewAppForm.getVillageEnPermanent()).isEqualTo(DEFAULT_VILLAGE_EN_PERMANENT);
        assertThat(testRisNewAppForm.getUnionEnPermanent()).isEqualTo(DEFAULT_UNION_EN_PERMANENT);
        assertThat(testRisNewAppForm.getPoEnPermanent()).isEqualTo(DEFAULT_PO_EN_PERMANENT);
        assertThat(testRisNewAppForm.getPoCodeEnPermanent()).isEqualTo(DEFAULT_PO_CODE_EN_PERMANENT);
        assertThat(testRisNewAppForm.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testRisNewAppForm.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testRisNewAppForm.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testRisNewAppForm.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setDesignation(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCircularNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setCircularNo(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setApplicationDate(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicantsNameBnIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setApplicantsNameBn(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicantsNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setApplicantsNameEn(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setNationalId(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthCertificateNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setBirthCertificateNo(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setBirthDate(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setAge(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFathersNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setFathersName(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMothersNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setMothersName(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoldingNameBnPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setHoldingNameBnPresent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVillageBnPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setVillageBnPresent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnionBnPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setUnionBnPresent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoBnPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setPoBnPresent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoCodeBnPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setPoCodeBnPresent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoldingNameBnPermanentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setHoldingNameBnPermanent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVillageBnPermanentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setVillageBnPermanent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnionBnPermanentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setUnionBnPermanent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoBnPermanentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setPoBnPermanent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoCodeBnPermanentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setPoCodeBnPermanent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setContactPhone(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setEmail(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setNationality(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProfessionIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setProfession(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReligionIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setReligion(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoldingNameEnPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setHoldingNameEnPresent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVillageEnPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setVillageEnPresent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnionEnPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setUnionEnPresent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoEnPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setPoEnPresent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoCodeEnPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setPoCodeEnPresent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoldingNameEnPermanentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setHoldingNameEnPermanent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVillageEnPermanentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setVillageEnPermanent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnionEnPermanentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setUnionEnPermanent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoEnPermanentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setPoEnPermanent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoCodeEnPermanentIsRequired() throws Exception {
        int databaseSizeBeforeTest = risNewAppFormRepository.findAll().size();
        // set the field null
        risNewAppForm.setPoCodeEnPermanent(null);

        // Create the RisNewAppForm, which fails.

        restRisNewAppFormMockMvc.perform(post("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isBadRequest());

        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRisNewAppForms() throws Exception {
        // Initialize the database
        risNewAppFormRepository.saveAndFlush(risNewAppForm);

        // Get all the risNewAppForms
        restRisNewAppFormMockMvc.perform(get("/api/risNewAppForms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(risNewAppForm.getId().intValue())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].circularNo").value(hasItem(DEFAULT_CIRCULAR_NO.toString())))
                .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].applicantsNameBn").value(hasItem(DEFAULT_APPLICANTS_NAME_BN.toString())))
                .andExpect(jsonPath("$.[*].applicantsNameEn").value(hasItem(DEFAULT_APPLICANTS_NAME_EN.toString())))
                .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID.intValue())))
                .andExpect(jsonPath("$.[*].birthCertificateNo").value(hasItem(DEFAULT_BIRTH_CERTIFICATE_NO.intValue())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.toString())))
                .andExpect(jsonPath("$.[*].fathersName").value(hasItem(DEFAULT_FATHERS_NAME.toString())))
                .andExpect(jsonPath("$.[*].mothersName").value(hasItem(DEFAULT_MOTHERS_NAME.toString())))
                .andExpect(jsonPath("$.[*].holdingNameBnPresent").value(hasItem(DEFAULT_HOLDING_NAME_BN_PRESENT.toString())))
                .andExpect(jsonPath("$.[*].villageBnPresent").value(hasItem(DEFAULT_VILLAGE_BN_PRESENT.toString())))
                .andExpect(jsonPath("$.[*].unionBnPresent").value(hasItem(DEFAULT_UNION_BN_PRESENT.toString())))
                .andExpect(jsonPath("$.[*].poBnPresent").value(hasItem(DEFAULT_PO_BN_PRESENT.toString())))
                .andExpect(jsonPath("$.[*].poCodeBnPresent").value(hasItem(DEFAULT_PO_CODE_BN_PRESENT.toString())))
                .andExpect(jsonPath("$.[*].holdingNameBnPermanent").value(hasItem(DEFAULT_HOLDING_NAME_BN_PERMANENT.toString())))
                .andExpect(jsonPath("$.[*].villageBnPermanent").value(hasItem(DEFAULT_VILLAGE_BN_PERMANENT.toString())))
                .andExpect(jsonPath("$.[*].unionBnPermanent").value(hasItem(DEFAULT_UNION_BN_PERMANENT.toString())))
                .andExpect(jsonPath("$.[*].poBnPermanent").value(hasItem(DEFAULT_PO_BN_PERMANENT.toString())))
                .andExpect(jsonPath("$.[*].poCodeBnPermanent").value(hasItem(DEFAULT_PO_CODE_BN_PERMANENT.toString())))
                .andExpect(jsonPath("$.[*].contactPhone").value(hasItem(DEFAULT_CONTACT_PHONE)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
                .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())))
                .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
                .andExpect(jsonPath("$.[*].holdingNameEnPresent").value(hasItem(DEFAULT_HOLDING_NAME_EN_PRESENT.toString())))
                .andExpect(jsonPath("$.[*].villageEnPresent").value(hasItem(DEFAULT_VILLAGE_EN_PRESENT.toString())))
                .andExpect(jsonPath("$.[*].unionEnPresent").value(hasItem(DEFAULT_UNION_EN_PRESENT.toString())))
                .andExpect(jsonPath("$.[*].poEnPresent").value(hasItem(DEFAULT_PO_EN_PRESENT.toString())))
                .andExpect(jsonPath("$.[*].poCodeEnPresent").value(hasItem(DEFAULT_PO_CODE_EN_PRESENT.toString())))
                .andExpect(jsonPath("$.[*].holdingNameEnPermanent").value(hasItem(DEFAULT_HOLDING_NAME_EN_PERMANENT.toString())))
                .andExpect(jsonPath("$.[*].villageEnPermanent").value(hasItem(DEFAULT_VILLAGE_EN_PERMANENT.toString())))
                .andExpect(jsonPath("$.[*].unionEnPermanent").value(hasItem(DEFAULT_UNION_EN_PERMANENT.toString())))
                .andExpect(jsonPath("$.[*].poEnPermanent").value(hasItem(DEFAULT_PO_EN_PERMANENT.toString())))
                .andExpect(jsonPath("$.[*].poCodeEnPermanent").value(hasItem(DEFAULT_PO_CODE_EN_PERMANENT.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getRisNewAppForm() throws Exception {
        // Initialize the database
        risNewAppFormRepository.saveAndFlush(risNewAppForm);

        // Get the risNewAppForm
        restRisNewAppFormMockMvc.perform(get("/api/risNewAppForms/{id}", risNewAppForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(risNewAppForm.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.circularNo").value(DEFAULT_CIRCULAR_NO.toString()))
            .andExpect(jsonPath("$.applicationDate").value(DEFAULT_APPLICATION_DATE.toString()))
            .andExpect(jsonPath("$.applicantsNameBn").value(DEFAULT_APPLICANTS_NAME_BN.toString()))
            .andExpect(jsonPath("$.applicantsNameEn").value(DEFAULT_APPLICANTS_NAME_EN.toString()))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID.intValue()))
            .andExpect(jsonPath("$.birthCertificateNo").value(DEFAULT_BIRTH_CERTIFICATE_NO.intValue()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.toString()))
            .andExpect(jsonPath("$.fathersName").value(DEFAULT_FATHERS_NAME.toString()))
            .andExpect(jsonPath("$.mothersName").value(DEFAULT_MOTHERS_NAME.toString()))
            .andExpect(jsonPath("$.holdingNameBnPresent").value(DEFAULT_HOLDING_NAME_BN_PRESENT.toString()))
            .andExpect(jsonPath("$.villageBnPresent").value(DEFAULT_VILLAGE_BN_PRESENT.toString()))
            .andExpect(jsonPath("$.unionBnPresent").value(DEFAULT_UNION_BN_PRESENT.toString()))
            .andExpect(jsonPath("$.poBnPresent").value(DEFAULT_PO_BN_PRESENT.toString()))
            .andExpect(jsonPath("$.poCodeBnPresent").value(DEFAULT_PO_CODE_BN_PRESENT.toString()))
            .andExpect(jsonPath("$.holdingNameBnPermanent").value(DEFAULT_HOLDING_NAME_BN_PERMANENT.toString()))
            .andExpect(jsonPath("$.villageBnPermanent").value(DEFAULT_VILLAGE_BN_PERMANENT.toString()))
            .andExpect(jsonPath("$.unionBnPermanent").value(DEFAULT_UNION_BN_PERMANENT.toString()))
            .andExpect(jsonPath("$.poBnPermanent").value(DEFAULT_PO_BN_PERMANENT.toString()))
            .andExpect(jsonPath("$.poCodeBnPermanent").value(DEFAULT_PO_CODE_BN_PERMANENT.toString()))
            .andExpect(jsonPath("$.contactPhone").value(DEFAULT_CONTACT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.holdingNameEnPresent").value(DEFAULT_HOLDING_NAME_EN_PRESENT.toString()))
            .andExpect(jsonPath("$.villageEnPresent").value(DEFAULT_VILLAGE_EN_PRESENT.toString()))
            .andExpect(jsonPath("$.unionEnPresent").value(DEFAULT_UNION_EN_PRESENT.toString()))
            .andExpect(jsonPath("$.poEnPresent").value(DEFAULT_PO_EN_PRESENT.toString()))
            .andExpect(jsonPath("$.poCodeEnPresent").value(DEFAULT_PO_CODE_EN_PRESENT.toString()))
            .andExpect(jsonPath("$.holdingNameEnPermanent").value(DEFAULT_HOLDING_NAME_EN_PERMANENT.toString()))
            .andExpect(jsonPath("$.villageEnPermanent").value(DEFAULT_VILLAGE_EN_PERMANENT.toString()))
            .andExpect(jsonPath("$.unionEnPermanent").value(DEFAULT_UNION_EN_PERMANENT.toString()))
            .andExpect(jsonPath("$.poEnPermanent").value(DEFAULT_PO_EN_PERMANENT.toString()))
            .andExpect(jsonPath("$.poCodeEnPermanent").value(DEFAULT_PO_CODE_EN_PERMANENT.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRisNewAppForm() throws Exception {
        // Get the risNewAppForm
        restRisNewAppFormMockMvc.perform(get("/api/risNewAppForms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRisNewAppForm() throws Exception {
        // Initialize the database
        risNewAppFormRepository.saveAndFlush(risNewAppForm);

		int databaseSizeBeforeUpdate = risNewAppFormRepository.findAll().size();

        // Update the risNewAppForm
        risNewAppForm.setDesignation(UPDATED_DESIGNATION);
        risNewAppForm.setCircularNo(UPDATED_CIRCULAR_NO);
        risNewAppForm.setApplicationDate(UPDATED_APPLICATION_DATE);
        risNewAppForm.setApplicantsNameBn(UPDATED_APPLICANTS_NAME_BN);
        risNewAppForm.setApplicantsNameEn(UPDATED_APPLICANTS_NAME_EN);
        risNewAppForm.setNationalId(UPDATED_NATIONAL_ID);
        risNewAppForm.setBirthCertificateNo(UPDATED_BIRTH_CERTIFICATE_NO);
        risNewAppForm.setBirthDate(UPDATED_BIRTH_DATE);
        risNewAppForm.setAge(UPDATED_AGE);
        risNewAppForm.setFathersName(UPDATED_FATHERS_NAME);
        risNewAppForm.setMothersName(UPDATED_MOTHERS_NAME);
        risNewAppForm.setHoldingNameBnPresent(UPDATED_HOLDING_NAME_BN_PRESENT);
        risNewAppForm.setVillageBnPresent(UPDATED_VILLAGE_BN_PRESENT);
        risNewAppForm.setUnionBnPresent(UPDATED_UNION_BN_PRESENT);
        risNewAppForm.setPoBnPresent(UPDATED_PO_BN_PRESENT);
        risNewAppForm.setPoCodeBnPresent(UPDATED_PO_CODE_BN_PRESENT);
        risNewAppForm.setHoldingNameBnPermanent(UPDATED_HOLDING_NAME_BN_PERMANENT);
        risNewAppForm.setVillageBnPermanent(UPDATED_VILLAGE_BN_PERMANENT);
        risNewAppForm.setUnionBnPermanent(UPDATED_UNION_BN_PERMANENT);
        risNewAppForm.setPoBnPermanent(UPDATED_PO_BN_PERMANENT);
        risNewAppForm.setPoCodeBnPermanent(UPDATED_PO_CODE_BN_PERMANENT);
        risNewAppForm.setContactPhone(UPDATED_CONTACT_PHONE);
        risNewAppForm.setEmail(UPDATED_EMAIL);
        risNewAppForm.setNationality(UPDATED_NATIONALITY);
        risNewAppForm.setProfession(UPDATED_PROFESSION);
        risNewAppForm.setReligion(UPDATED_RELIGION);
        risNewAppForm.setHoldingNameEnPresent(UPDATED_HOLDING_NAME_EN_PRESENT);
        risNewAppForm.setVillageEnPresent(UPDATED_VILLAGE_EN_PRESENT);
        risNewAppForm.setUnionEnPresent(UPDATED_UNION_EN_PRESENT);
        risNewAppForm.setPoEnPresent(UPDATED_PO_EN_PRESENT);
        risNewAppForm.setPoCodeEnPresent(UPDATED_PO_CODE_EN_PRESENT);
        risNewAppForm.setHoldingNameEnPermanent(UPDATED_HOLDING_NAME_EN_PERMANENT);
        risNewAppForm.setVillageEnPermanent(UPDATED_VILLAGE_EN_PERMANENT);
        risNewAppForm.setUnionEnPermanent(UPDATED_UNION_EN_PERMANENT);
        risNewAppForm.setPoEnPermanent(UPDATED_PO_EN_PERMANENT);
        risNewAppForm.setPoCodeEnPermanent(UPDATED_PO_CODE_EN_PERMANENT);
        risNewAppForm.setCreateDate(UPDATED_CREATE_DATE);
        risNewAppForm.setCreateBy(UPDATED_CREATE_BY);
        risNewAppForm.setUpdateDate(UPDATED_UPDATE_DATE);
        risNewAppForm.setUpdateBy(UPDATED_UPDATE_BY);

        restRisNewAppFormMockMvc.perform(put("/api/risNewAppForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risNewAppForm)))
                .andExpect(status().isOk());

        // Validate the RisNewAppForm in the database
        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeUpdate);
        RisNewAppForm testRisNewAppForm = risNewAppForms.get(risNewAppForms.size() - 1);
        assertThat(testRisNewAppForm.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testRisNewAppForm.getCircularNo()).isEqualTo(UPDATED_CIRCULAR_NO);
        assertThat(testRisNewAppForm.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testRisNewAppForm.getApplicantsNameBn()).isEqualTo(UPDATED_APPLICANTS_NAME_BN);
        assertThat(testRisNewAppForm.getApplicantsNameEn()).isEqualTo(UPDATED_APPLICANTS_NAME_EN);
        assertThat(testRisNewAppForm.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testRisNewAppForm.getBirthCertificateNo()).isEqualTo(UPDATED_BIRTH_CERTIFICATE_NO);
        assertThat(testRisNewAppForm.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testRisNewAppForm.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testRisNewAppForm.getFathersName()).isEqualTo(UPDATED_FATHERS_NAME);
        assertThat(testRisNewAppForm.getMothersName()).isEqualTo(UPDATED_MOTHERS_NAME);
        assertThat(testRisNewAppForm.getHoldingNameBnPresent()).isEqualTo(UPDATED_HOLDING_NAME_BN_PRESENT);
        assertThat(testRisNewAppForm.getVillageBnPresent()).isEqualTo(UPDATED_VILLAGE_BN_PRESENT);
        assertThat(testRisNewAppForm.getUnionBnPresent()).isEqualTo(UPDATED_UNION_BN_PRESENT);
        assertThat(testRisNewAppForm.getPoBnPresent()).isEqualTo(UPDATED_PO_BN_PRESENT);
        assertThat(testRisNewAppForm.getPoCodeBnPresent()).isEqualTo(UPDATED_PO_CODE_BN_PRESENT);
        assertThat(testRisNewAppForm.getHoldingNameBnPermanent()).isEqualTo(UPDATED_HOLDING_NAME_BN_PERMANENT);
        assertThat(testRisNewAppForm.getVillageBnPermanent()).isEqualTo(UPDATED_VILLAGE_BN_PERMANENT);
        assertThat(testRisNewAppForm.getUnionBnPermanent()).isEqualTo(UPDATED_UNION_BN_PERMANENT);
        assertThat(testRisNewAppForm.getPoBnPermanent()).isEqualTo(UPDATED_PO_BN_PERMANENT);
        assertThat(testRisNewAppForm.getPoCodeBnPermanent()).isEqualTo(UPDATED_PO_CODE_BN_PERMANENT);
        assertThat(testRisNewAppForm.getContactPhone()).isEqualTo(UPDATED_CONTACT_PHONE);
        assertThat(testRisNewAppForm.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRisNewAppForm.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testRisNewAppForm.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testRisNewAppForm.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testRisNewAppForm.getHoldingNameEnPresent()).isEqualTo(UPDATED_HOLDING_NAME_EN_PRESENT);
        assertThat(testRisNewAppForm.getVillageEnPresent()).isEqualTo(UPDATED_VILLAGE_EN_PRESENT);
        assertThat(testRisNewAppForm.getUnionEnPresent()).isEqualTo(UPDATED_UNION_EN_PRESENT);
        assertThat(testRisNewAppForm.getPoEnPresent()).isEqualTo(UPDATED_PO_EN_PRESENT);
        assertThat(testRisNewAppForm.getPoCodeEnPresent()).isEqualTo(UPDATED_PO_CODE_EN_PRESENT);
        assertThat(testRisNewAppForm.getHoldingNameEnPermanent()).isEqualTo(UPDATED_HOLDING_NAME_EN_PERMANENT);
        assertThat(testRisNewAppForm.getVillageEnPermanent()).isEqualTo(UPDATED_VILLAGE_EN_PERMANENT);
        assertThat(testRisNewAppForm.getUnionEnPermanent()).isEqualTo(UPDATED_UNION_EN_PERMANENT);
        assertThat(testRisNewAppForm.getPoEnPermanent()).isEqualTo(UPDATED_PO_EN_PERMANENT);
        assertThat(testRisNewAppForm.getPoCodeEnPermanent()).isEqualTo(UPDATED_PO_CODE_EN_PERMANENT);
        assertThat(testRisNewAppForm.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testRisNewAppForm.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testRisNewAppForm.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testRisNewAppForm.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteRisNewAppForm() throws Exception {
        // Initialize the database
        risNewAppFormRepository.saveAndFlush(risNewAppForm);

		int databaseSizeBeforeDelete = risNewAppFormRepository.findAll().size();

        // Get the risNewAppForm
        restRisNewAppFormMockMvc.perform(delete("/api/risNewAppForms/{id}", risNewAppForm.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RisNewAppForm> risNewAppForms = risNewAppFormRepository.findAll();
        assertThat(risNewAppForms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/
