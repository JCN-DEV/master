package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EmployeeLoanRequisitionForm;
import gov.step.app.repository.EmployeeLoanRequisitionFormRepository;
import gov.step.app.repository.search.EmployeeLoanRequisitionFormSearchRepository;

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


/**
 * Test class for the EmployeeLoanRequisitionFormResource REST controller.
 *
 * @see EmployeeLoanRequisitionFormResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeLoanRequisitionFormResourceTest {

    private static final String DEFAULT_LOAN_REQUISITION_CODE = "AAAAA";
    private static final String UPDATED_LOAN_REQUISITION_CODE = "BBBBB";

    private static final Long DEFAULT_INSTITUTE_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_INSTITUTE_EMPLOYEE_ID = 2L;

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final Long DEFAULT_INSTALLMENT = 1L;
    private static final Long UPDATED_INSTALLMENT = 2L;

    private static final Long DEFAULT_APPROVE_STATUS = 1L;
    private static final Long UPDATED_APPROVE_STATUS = 2L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private EmployeeLoanRequisitionFormRepository employeeLoanRequisitionFormRepository;

    @Inject
    private EmployeeLoanRequisitionFormSearchRepository employeeLoanRequisitionFormSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeLoanRequisitionFormMockMvc;

    private EmployeeLoanRequisitionForm employeeLoanRequisitionForm;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeLoanRequisitionFormResource employeeLoanRequisitionFormResource = new EmployeeLoanRequisitionFormResource();
        ReflectionTestUtils.setField(employeeLoanRequisitionFormResource, "employeeLoanRequisitionFormRepository", employeeLoanRequisitionFormRepository);
        ReflectionTestUtils.setField(employeeLoanRequisitionFormResource, "employeeLoanRequisitionFormSearchRepository", employeeLoanRequisitionFormSearchRepository);
        this.restEmployeeLoanRequisitionFormMockMvc = MockMvcBuilders.standaloneSetup(employeeLoanRequisitionFormResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employeeLoanRequisitionForm = new EmployeeLoanRequisitionForm();
        employeeLoanRequisitionForm.setLoanRequisitionCode(DEFAULT_LOAN_REQUISITION_CODE);
        // employeeLoanRequisitionForm.setInstituteEmployeeId(DEFAULT_INSTITUTE_EMPLOYEE_ID);
        employeeLoanRequisitionForm.setAmount(DEFAULT_AMOUNT);
        employeeLoanRequisitionForm.setInstallment(DEFAULT_INSTALLMENT);
        employeeLoanRequisitionForm.setApproveStatus(DEFAULT_APPROVE_STATUS.intValue());
        employeeLoanRequisitionForm.setCreateDate(DEFAULT_CREATE_DATE);
        employeeLoanRequisitionForm.setCreateBy(DEFAULT_CREATE_BY);
        employeeLoanRequisitionForm.setUpdateDate(DEFAULT_UPDATE_DATE);
        employeeLoanRequisitionForm.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createEmployeeLoanRequisitionForm() throws Exception {
        int databaseSizeBeforeCreate = employeeLoanRequisitionFormRepository.findAll().size();

        // Create the EmployeeLoanRequisitionForm

        restEmployeeLoanRequisitionFormMockMvc.perform(post("/api/employeeLoanRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRequisitionForm)))
                .andExpect(status().isCreated());

        // Validate the EmployeeLoanRequisitionForm in the database
        List<EmployeeLoanRequisitionForm> employeeLoanRequisitionForms = employeeLoanRequisitionFormRepository.findAll();
        assertThat(employeeLoanRequisitionForms).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLoanRequisitionForm testEmployeeLoanRequisitionForm = employeeLoanRequisitionForms.get(employeeLoanRequisitionForms.size() - 1);
        assertThat(testEmployeeLoanRequisitionForm.getLoanRequisitionCode()).isEqualTo(DEFAULT_LOAN_REQUISITION_CODE);
        // assertThat(testEmployeeLoanRequisitionForm.getInstituteEmployeeId()).isEqualTo(DEFAULT_INSTITUTE_EMPLOYEE_ID);
        assertThat(testEmployeeLoanRequisitionForm.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEmployeeLoanRequisitionForm.getInstallment()).isEqualTo(DEFAULT_INSTALLMENT);
        assertThat(testEmployeeLoanRequisitionForm.getApproveStatus()).isEqualTo(DEFAULT_APPROVE_STATUS);
        assertThat(testEmployeeLoanRequisitionForm.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testEmployeeLoanRequisitionForm.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testEmployeeLoanRequisitionForm.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testEmployeeLoanRequisitionForm.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkLoanRequisitionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanRequisitionFormRepository.findAll().size();
        // set the field null
        employeeLoanRequisitionForm.setLoanRequisitionCode(null);

        // Create the EmployeeLoanRequisitionForm, which fails.

        restEmployeeLoanRequisitionFormMockMvc.perform(post("/api/employeeLoanRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRequisitionForm)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanRequisitionForm> employeeLoanRequisitionForms = employeeLoanRequisitionFormRepository.findAll();
        assertThat(employeeLoanRequisitionForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstituteEmployeeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanRequisitionFormRepository.findAll().size();
        // set the field null
        // employeeLoanRequisitionForm.setInstituteEmployeeId(null);

        // Create the EmployeeLoanRequisitionForm, which fails.

        restEmployeeLoanRequisitionFormMockMvc.perform(post("/api/employeeLoanRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRequisitionForm)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanRequisitionForm> employeeLoanRequisitionForms = employeeLoanRequisitionFormRepository.findAll();
        assertThat(employeeLoanRequisitionForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanRequisitionFormRepository.findAll().size();
        // set the field null
        employeeLoanRequisitionForm.setAmount(null);

        // Create the EmployeeLoanRequisitionForm, which fails.

        restEmployeeLoanRequisitionFormMockMvc.perform(post("/api/employeeLoanRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRequisitionForm)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanRequisitionForm> employeeLoanRequisitionForms = employeeLoanRequisitionFormRepository.findAll();
        assertThat(employeeLoanRequisitionForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstallmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanRequisitionFormRepository.findAll().size();
        // set the field null
        employeeLoanRequisitionForm.setInstallment(null);

        // Create the EmployeeLoanRequisitionForm, which fails.

        restEmployeeLoanRequisitionFormMockMvc.perform(post("/api/employeeLoanRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRequisitionForm)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanRequisitionForm> employeeLoanRequisitionForms = employeeLoanRequisitionFormRepository.findAll();
        assertThat(employeeLoanRequisitionForms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeLoanRequisitionForms() throws Exception {
        // Initialize the database
        employeeLoanRequisitionFormRepository.saveAndFlush(employeeLoanRequisitionForm);

        // Get all the employeeLoanRequisitionForms
        restEmployeeLoanRequisitionFormMockMvc.perform(get("/api/employeeLoanRequisitionForms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLoanRequisitionForm.getId().intValue())))
                .andExpect(jsonPath("$.[*].loanRequisitionCode").value(hasItem(DEFAULT_LOAN_REQUISITION_CODE.toString())))
                .andExpect(jsonPath("$.[*].instituteEmployeeId").value(hasItem(DEFAULT_INSTITUTE_EMPLOYEE_ID.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].installment").value(hasItem(DEFAULT_INSTALLMENT.intValue())))
                .andExpect(jsonPath("$.[*].approveStatus").value(hasItem(DEFAULT_APPROVE_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getEmployeeLoanRequisitionForm() throws Exception {
        // Initialize the database
        employeeLoanRequisitionFormRepository.saveAndFlush(employeeLoanRequisitionForm);

        // Get the employeeLoanRequisitionForm
        restEmployeeLoanRequisitionFormMockMvc.perform(get("/api/employeeLoanRequisitionForms/{id}", employeeLoanRequisitionForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employeeLoanRequisitionForm.getId().intValue()))
            .andExpect(jsonPath("$.loanRequisitionCode").value(DEFAULT_LOAN_REQUISITION_CODE.toString()))
            .andExpect(jsonPath("$.instituteEmployeeId").value(DEFAULT_INSTITUTE_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.installment").value(DEFAULT_INSTALLMENT.intValue()))
            .andExpect(jsonPath("$.approveStatus").value(DEFAULT_APPROVE_STATUS.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeLoanRequisitionForm() throws Exception {
        // Get the employeeLoanRequisitionForm
        restEmployeeLoanRequisitionFormMockMvc.perform(get("/api/employeeLoanRequisitionForms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLoanRequisitionForm() throws Exception {
        // Initialize the database
        employeeLoanRequisitionFormRepository.saveAndFlush(employeeLoanRequisitionForm);

		int databaseSizeBeforeUpdate = employeeLoanRequisitionFormRepository.findAll().size();

        // Update the employeeLoanRequisitionForm
        employeeLoanRequisitionForm.setLoanRequisitionCode(UPDATED_LOAN_REQUISITION_CODE);
        // employeeLoanRequisitionForm.setInstituteEmployeeId(UPDATED_INSTITUTE_EMPLOYEE_ID);
        employeeLoanRequisitionForm.setAmount(UPDATED_AMOUNT);
        employeeLoanRequisitionForm.setInstallment(UPDATED_INSTALLMENT);
        employeeLoanRequisitionForm.setApproveStatus(UPDATED_APPROVE_STATUS.intValue());
        employeeLoanRequisitionForm.setCreateDate(UPDATED_CREATE_DATE);
        employeeLoanRequisitionForm.setCreateBy(UPDATED_CREATE_BY);
        employeeLoanRequisitionForm.setUpdateDate(UPDATED_UPDATE_DATE);
        employeeLoanRequisitionForm.setUpdateBy(UPDATED_UPDATE_BY);

        restEmployeeLoanRequisitionFormMockMvc.perform(put("/api/employeeLoanRequisitionForms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanRequisitionForm)))
                .andExpect(status().isOk());

        // Validate the EmployeeLoanRequisitionForm in the database
        List<EmployeeLoanRequisitionForm> employeeLoanRequisitionForms = employeeLoanRequisitionFormRepository.findAll();
        assertThat(employeeLoanRequisitionForms).hasSize(databaseSizeBeforeUpdate);
        EmployeeLoanRequisitionForm testEmployeeLoanRequisitionForm = employeeLoanRequisitionForms.get(employeeLoanRequisitionForms.size() - 1);
        assertThat(testEmployeeLoanRequisitionForm.getLoanRequisitionCode()).isEqualTo(UPDATED_LOAN_REQUISITION_CODE);
        // assertThat(testEmployeeLoanRequisitionForm.getInstituteEmployeeId()).isEqualTo(UPDATED_INSTITUTE_EMPLOYEE_ID);
        assertThat(testEmployeeLoanRequisitionForm.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEmployeeLoanRequisitionForm.getInstallment()).isEqualTo(UPDATED_INSTALLMENT);
        assertThat(testEmployeeLoanRequisitionForm.getApproveStatus()).isEqualTo(UPDATED_APPROVE_STATUS);
        assertThat(testEmployeeLoanRequisitionForm.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testEmployeeLoanRequisitionForm.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testEmployeeLoanRequisitionForm.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testEmployeeLoanRequisitionForm.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteEmployeeLoanRequisitionForm() throws Exception {
        // Initialize the database
        employeeLoanRequisitionFormRepository.saveAndFlush(employeeLoanRequisitionForm);

		int databaseSizeBeforeDelete = employeeLoanRequisitionFormRepository.findAll().size();

        // Get the employeeLoanRequisitionForm
        restEmployeeLoanRequisitionFormMockMvc.perform(delete("/api/employeeLoanRequisitionForms/{id}", employeeLoanRequisitionForm.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeLoanRequisitionForm> employeeLoanRequisitionForms = employeeLoanRequisitionFormRepository.findAll();
        assertThat(employeeLoanRequisitionForms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
