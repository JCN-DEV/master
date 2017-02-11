package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PayScale;
import gov.step.app.repository.PayScaleRepository;
import gov.step.app.repository.search.PayScaleSearchRepository;

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


/**
 * Test class for the PayScaleResource REST controller.
 *
 * @see PayScaleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PayScaleResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_PAY_SCALE_CLASS = "AAAAA";
    private static final String UPDATED_PAY_SCALE_CLASS = "BBBBB";
    private static final String DEFAULT_GRADE = "AAAAA";
    private static final String UPDATED_GRADE = "BBBBB";
    private static final String DEFAULT_GRADE_NAME = "AAAAA";
    private static final String UPDATED_GRADE_NAME = "BBBBB";

    private static final BigDecimal DEFAULT_BASIC_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HOUSE_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOUSE_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MEDICAL_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MEDICAL_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_WELFARE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_WELFARE_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RETIREMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_RETIREMENT_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PayScaleRepository payScaleRepository;

    @Inject
    private PayScaleSearchRepository payScaleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPayScaleMockMvc;

    private PayScale payScale;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PayScaleResource payScaleResource = new PayScaleResource();
        ReflectionTestUtils.setField(payScaleResource, "payScaleRepository", payScaleRepository);
        ReflectionTestUtils.setField(payScaleResource, "payScaleSearchRepository", payScaleSearchRepository);
        this.restPayScaleMockMvc = MockMvcBuilders.standaloneSetup(payScaleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        payScale = new PayScale();
        payScale.setCode(DEFAULT_CODE);
        payScale.setPayScaleClass(DEFAULT_PAY_SCALE_CLASS);
        payScale.setGrade(DEFAULT_GRADE);
        payScale.setGradeName(DEFAULT_GRADE_NAME);
        payScale.setBasicAmount(DEFAULT_BASIC_AMOUNT);
        payScale.setHouseAllowance(DEFAULT_HOUSE_ALLOWANCE);
        payScale.setMedicalAllowance(DEFAULT_MEDICAL_ALLOWANCE);
        payScale.setWelfareAmount(DEFAULT_WELFARE_AMOUNT);
        payScale.setRetirementAmount(DEFAULT_RETIREMENT_AMOUNT);
        payScale.setPayscaleDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createPayScale() throws Exception {
        int databaseSizeBeforeCreate = payScaleRepository.findAll().size();

        // Create the PayScale

        restPayScaleMockMvc.perform(post("/api/payScales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payScale)))
                .andExpect(status().isCreated());

        // Validate the PayScale in the database
        List<PayScale> payScales = payScaleRepository.findAll();
        assertThat(payScales).hasSize(databaseSizeBeforeCreate + 1);
        PayScale testPayScale = payScales.get(payScales.size() - 1);
        assertThat(testPayScale.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPayScale.getPayScaleClass()).isEqualTo(DEFAULT_PAY_SCALE_CLASS);
        assertThat(testPayScale.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testPayScale.getGradeName()).isEqualTo(DEFAULT_GRADE_NAME);
        assertThat(testPayScale.getBasicAmount()).isEqualTo(DEFAULT_BASIC_AMOUNT);
        assertThat(testPayScale.getHouseAllowance()).isEqualTo(DEFAULT_HOUSE_ALLOWANCE);
        assertThat(testPayScale.getMedicalAllowance()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE);
        assertThat(testPayScale.getWelfareAmount()).isEqualTo(DEFAULT_WELFARE_AMOUNT);
        assertThat(testPayScale.getRetirementAmount()).isEqualTo(DEFAULT_RETIREMENT_AMOUNT);
        assertThat(testPayScale.getPayscaleDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = payScaleRepository.findAll().size();
        // set the field null
        payScale.setCode(null);

        // Create the PayScale, which fails.

        restPayScaleMockMvc.perform(post("/api/payScales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payScale)))
                .andExpect(status().isBadRequest());

        List<PayScale> payScales = payScaleRepository.findAll();
        assertThat(payScales).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayScales() throws Exception {
        // Initialize the database
        payScaleRepository.saveAndFlush(payScale);

        // Get all the payScales
        restPayScaleMockMvc.perform(get("/api/payScales"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(payScale.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].payScaleClass").value(hasItem(DEFAULT_PAY_SCALE_CLASS.toString())))
                .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
                .andExpect(jsonPath("$.[*].gradeName").value(hasItem(DEFAULT_GRADE_NAME.toString())))
                .andExpect(jsonPath("$.[*].basicAmount").value(hasItem(DEFAULT_BASIC_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].houseAllowance").value(hasItem(DEFAULT_HOUSE_ALLOWANCE.intValue())))
                .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
                .andExpect(jsonPath("$.[*].welfareAmount").value(hasItem(DEFAULT_WELFARE_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].retirementAmount").value(hasItem(DEFAULT_RETIREMENT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPayScale() throws Exception {
        // Initialize the database
        payScaleRepository.saveAndFlush(payScale);

        // Get the payScale
        restPayScaleMockMvc.perform(get("/api/payScales/{id}", payScale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(payScale.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.payScaleClass").value(DEFAULT_PAY_SCALE_CLASS.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.gradeName").value(DEFAULT_GRADE_NAME.toString()))
            .andExpect(jsonPath("$.basicAmount").value(DEFAULT_BASIC_AMOUNT.intValue()))
            .andExpect(jsonPath("$.houseAllowance").value(DEFAULT_HOUSE_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.medicalAllowance").value(DEFAULT_MEDICAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.welfareAmount").value(DEFAULT_WELFARE_AMOUNT.intValue()))
            .andExpect(jsonPath("$.retirementAmount").value(DEFAULT_RETIREMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayScale() throws Exception {
        // Get the payScale
        restPayScaleMockMvc.perform(get("/api/payScales/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayScale() throws Exception {
        // Initialize the database
        payScaleRepository.saveAndFlush(payScale);

		int databaseSizeBeforeUpdate = payScaleRepository.findAll().size();

        // Update the payScale
        payScale.setCode(UPDATED_CODE);
        payScale.setPayScaleClass(UPDATED_PAY_SCALE_CLASS);
        payScale.setGrade(UPDATED_GRADE);
        payScale.setGradeName(UPDATED_GRADE_NAME);
        payScale.setBasicAmount(UPDATED_BASIC_AMOUNT);
        payScale.setHouseAllowance(UPDATED_HOUSE_ALLOWANCE);
        payScale.setMedicalAllowance(UPDATED_MEDICAL_ALLOWANCE);
        payScale.setWelfareAmount(UPDATED_WELFARE_AMOUNT);
        payScale.setRetirementAmount(UPDATED_RETIREMENT_AMOUNT);
        payScale.setPayscaleDate(UPDATED_DATE);

        restPayScaleMockMvc.perform(put("/api/payScales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payScale)))
                .andExpect(status().isOk());

        // Validate the PayScale in the database
        List<PayScale> payScales = payScaleRepository.findAll();
        assertThat(payScales).hasSize(databaseSizeBeforeUpdate);
        PayScale testPayScale = payScales.get(payScales.size() - 1);
        assertThat(testPayScale.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPayScale.getPayScaleClass()).isEqualTo(UPDATED_PAY_SCALE_CLASS);
        assertThat(testPayScale.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testPayScale.getGradeName()).isEqualTo(UPDATED_GRADE_NAME);
        assertThat(testPayScale.getBasicAmount()).isEqualTo(UPDATED_BASIC_AMOUNT);
        assertThat(testPayScale.getHouseAllowance()).isEqualTo(UPDATED_HOUSE_ALLOWANCE);
        assertThat(testPayScale.getMedicalAllowance()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE);
        assertThat(testPayScale.getWelfareAmount()).isEqualTo(UPDATED_WELFARE_AMOUNT);
        assertThat(testPayScale.getRetirementAmount()).isEqualTo(UPDATED_RETIREMENT_AMOUNT);
        assertThat(testPayScale.getPayscaleDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deletePayScale() throws Exception {
        // Initialize the database
        payScaleRepository.saveAndFlush(payScale);

		int databaseSizeBeforeDelete = payScaleRepository.findAll().size();

        // Get the payScale
        restPayScaleMockMvc.perform(delete("/api/payScales/{id}", payScale.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PayScale> payScales = payScaleRepository.findAll();
        assertThat(payScales).hasSize(databaseSizeBeforeDelete - 1);
    }
}
