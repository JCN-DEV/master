package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EmployeeLoanTypeSetup;
import gov.step.app.repository.EmployeeLoanTypeSetupRepository;
import gov.step.app.repository.search.EmployeeLoanTypeSetupSearchRepository;

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
 * Test class for the EmployeeLoanTypeSetupResource REST controller.
 *
 * @see EmployeeLoanTypeSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeLoanTypeSetupResourceTest {

    private static final String DEFAULT_LOAN_TYPE_CODE = "AAAAA";
    private static final String UPDATED_LOAN_TYPE_CODE = "BBBBB";
    private static final String DEFAULT_LOAN_TYPE_NAME = "AAAAA";
    private static final String UPDATED_LOAN_TYPE_NAME = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private EmployeeLoanTypeSetupRepository employeeLoanTypeSetupRepository;

    @Inject
    private EmployeeLoanTypeSetupSearchRepository employeeLoanTypeSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeLoanTypeSetupMockMvc;

    private EmployeeLoanTypeSetup employeeLoanTypeSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeLoanTypeSetupResource employeeLoanTypeSetupResource = new EmployeeLoanTypeSetupResource();
        ReflectionTestUtils.setField(employeeLoanTypeSetupResource, "employeeLoanTypeSetupRepository", employeeLoanTypeSetupRepository);
        ReflectionTestUtils.setField(employeeLoanTypeSetupResource, "employeeLoanTypeSetupSearchRepository", employeeLoanTypeSetupSearchRepository);
        this.restEmployeeLoanTypeSetupMockMvc = MockMvcBuilders.standaloneSetup(employeeLoanTypeSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employeeLoanTypeSetup = new EmployeeLoanTypeSetup();
        employeeLoanTypeSetup.setLoanTypeCode(DEFAULT_LOAN_TYPE_CODE);
        employeeLoanTypeSetup.setLoanTypeName(DEFAULT_LOAN_TYPE_NAME);
        employeeLoanTypeSetup.setStatus(DEFAULT_STATUS);
        employeeLoanTypeSetup.setCreateDate(DEFAULT_CREATE_DATE);
        employeeLoanTypeSetup.setCreateBy(DEFAULT_CREATE_BY);
        employeeLoanTypeSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        employeeLoanTypeSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createEmployeeLoanTypeSetup() throws Exception {
        int databaseSizeBeforeCreate = employeeLoanTypeSetupRepository.findAll().size();

        // Create the EmployeeLoanTypeSetup

        restEmployeeLoanTypeSetupMockMvc.perform(post("/api/employeeLoanTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanTypeSetup)))
                .andExpect(status().isCreated());

        // Validate the EmployeeLoanTypeSetup in the database
        List<EmployeeLoanTypeSetup> employeeLoanTypeSetups = employeeLoanTypeSetupRepository.findAll();
        assertThat(employeeLoanTypeSetups).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLoanTypeSetup testEmployeeLoanTypeSetup = employeeLoanTypeSetups.get(employeeLoanTypeSetups.size() - 1);
        assertThat(testEmployeeLoanTypeSetup.getLoanTypeCode()).isEqualTo(DEFAULT_LOAN_TYPE_CODE);
        assertThat(testEmployeeLoanTypeSetup.getLoanTypeName()).isEqualTo(DEFAULT_LOAN_TYPE_NAME);
        assertThat(testEmployeeLoanTypeSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeLoanTypeSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testEmployeeLoanTypeSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testEmployeeLoanTypeSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testEmployeeLoanTypeSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkLoanTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanTypeSetupRepository.findAll().size();
        // set the field null
        employeeLoanTypeSetup.setLoanTypeCode(null);

        // Create the EmployeeLoanTypeSetup, which fails.

        restEmployeeLoanTypeSetupMockMvc.perform(post("/api/employeeLoanTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanTypeSetup)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanTypeSetup> employeeLoanTypeSetups = employeeLoanTypeSetupRepository.findAll();
        assertThat(employeeLoanTypeSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoanTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanTypeSetupRepository.findAll().size();
        // set the field null
        employeeLoanTypeSetup.setLoanTypeName(null);

        // Create the EmployeeLoanTypeSetup, which fails.

        restEmployeeLoanTypeSetupMockMvc.perform(post("/api/employeeLoanTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanTypeSetup)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanTypeSetup> employeeLoanTypeSetups = employeeLoanTypeSetupRepository.findAll();
        assertThat(employeeLoanTypeSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeLoanTypeSetups() throws Exception {
        // Initialize the database
        employeeLoanTypeSetupRepository.saveAndFlush(employeeLoanTypeSetup);

        // Get all the employeeLoanTypeSetups
        restEmployeeLoanTypeSetupMockMvc.perform(get("/api/employeeLoanTypeSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLoanTypeSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].loanTypeCode").value(hasItem(DEFAULT_LOAN_TYPE_CODE.toString())))
                .andExpect(jsonPath("$.[*].loanTypeName").value(hasItem(DEFAULT_LOAN_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getEmployeeLoanTypeSetup() throws Exception {
        // Initialize the database
        employeeLoanTypeSetupRepository.saveAndFlush(employeeLoanTypeSetup);

        // Get the employeeLoanTypeSetup
        restEmployeeLoanTypeSetupMockMvc.perform(get("/api/employeeLoanTypeSetups/{id}", employeeLoanTypeSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employeeLoanTypeSetup.getId().intValue()))
            .andExpect(jsonPath("$.loanTypeCode").value(DEFAULT_LOAN_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.loanTypeName").value(DEFAULT_LOAN_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeLoanTypeSetup() throws Exception {
        // Get the employeeLoanTypeSetup
        restEmployeeLoanTypeSetupMockMvc.perform(get("/api/employeeLoanTypeSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLoanTypeSetup() throws Exception {
        // Initialize the database
        employeeLoanTypeSetupRepository.saveAndFlush(employeeLoanTypeSetup);

		int databaseSizeBeforeUpdate = employeeLoanTypeSetupRepository.findAll().size();

        // Update the employeeLoanTypeSetup
        employeeLoanTypeSetup.setLoanTypeCode(UPDATED_LOAN_TYPE_CODE);
        employeeLoanTypeSetup.setLoanTypeName(UPDATED_LOAN_TYPE_NAME);
        employeeLoanTypeSetup.setStatus(UPDATED_STATUS);
        employeeLoanTypeSetup.setCreateDate(UPDATED_CREATE_DATE);
        employeeLoanTypeSetup.setCreateBy(UPDATED_CREATE_BY);
        employeeLoanTypeSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        employeeLoanTypeSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restEmployeeLoanTypeSetupMockMvc.perform(put("/api/employeeLoanTypeSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanTypeSetup)))
                .andExpect(status().isOk());

        // Validate the EmployeeLoanTypeSetup in the database
        List<EmployeeLoanTypeSetup> employeeLoanTypeSetups = employeeLoanTypeSetupRepository.findAll();
        assertThat(employeeLoanTypeSetups).hasSize(databaseSizeBeforeUpdate);
        EmployeeLoanTypeSetup testEmployeeLoanTypeSetup = employeeLoanTypeSetups.get(employeeLoanTypeSetups.size() - 1);
        assertThat(testEmployeeLoanTypeSetup.getLoanTypeCode()).isEqualTo(UPDATED_LOAN_TYPE_CODE);
        assertThat(testEmployeeLoanTypeSetup.getLoanTypeName()).isEqualTo(UPDATED_LOAN_TYPE_NAME);
        assertThat(testEmployeeLoanTypeSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeLoanTypeSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testEmployeeLoanTypeSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testEmployeeLoanTypeSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testEmployeeLoanTypeSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteEmployeeLoanTypeSetup() throws Exception {
        // Initialize the database
        employeeLoanTypeSetupRepository.saveAndFlush(employeeLoanTypeSetup);

		int databaseSizeBeforeDelete = employeeLoanTypeSetupRepository.findAll().size();

        // Get the employeeLoanTypeSetup
        restEmployeeLoanTypeSetupMockMvc.perform(delete("/api/employeeLoanTypeSetups/{id}", employeeLoanTypeSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeLoanTypeSetup> employeeLoanTypeSetups = employeeLoanTypeSetupRepository.findAll();
        assertThat(employeeLoanTypeSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
