package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EmployeeLoanBillRegister;
import gov.step.app.repository.EmployeeLoanBillRegisterRepository;
import gov.step.app.repository.search.EmployeeLoanBillRegisterSearchRepository;

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
 * Test class for the EmployeeLoanBillRegisterResource REST controller.
 *
 * @see EmployeeLoanBillRegisterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeLoanBillRegisterResourceTest {

    private static final String DEFAULT_APPLICATION_TYPE = "AAAAA";
    private static final String UPDATED_APPLICATION_TYPE = "BBBBB";
    private static final String DEFAULT_BILL_NO = "AAAAA";
    private static final String UPDATED_BILL_NO = "BBBBB";

    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_RECEIVER_NAME = "AAAAA";
    private static final String UPDATED_RECEIVER_NAME = "BBBBB";
    private static final String DEFAULT_PLACE = "AAAAA";
    private static final String UPDATED_PLACE = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private EmployeeLoanBillRegisterRepository employeeLoanBillRegisterRepository;

    @Inject
    private EmployeeLoanBillRegisterSearchRepository employeeLoanBillRegisterSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeLoanBillRegisterMockMvc;

    private EmployeeLoanBillRegister employeeLoanBillRegister;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeLoanBillRegisterResource employeeLoanBillRegisterResource = new EmployeeLoanBillRegisterResource();
        ReflectionTestUtils.setField(employeeLoanBillRegisterResource, "employeeLoanBillRegisterRepository", employeeLoanBillRegisterRepository);
        ReflectionTestUtils.setField(employeeLoanBillRegisterResource, "employeeLoanBillRegisterSearchRepository", employeeLoanBillRegisterSearchRepository);
        this.restEmployeeLoanBillRegisterMockMvc = MockMvcBuilders.standaloneSetup(employeeLoanBillRegisterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employeeLoanBillRegister = new EmployeeLoanBillRegister();
        employeeLoanBillRegister.setApplicationType(DEFAULT_APPLICATION_TYPE);
        employeeLoanBillRegister.setBillNo(DEFAULT_BILL_NO);
        employeeLoanBillRegister.setIssueDate(DEFAULT_ISSUE_DATE);
        employeeLoanBillRegister.setReceiverName(DEFAULT_RECEIVER_NAME);
        employeeLoanBillRegister.setPlace(DEFAULT_PLACE);
        employeeLoanBillRegister.setStatus(DEFAULT_STATUS);
        employeeLoanBillRegister.setCreateDate(DEFAULT_CREATE_DATE);
        employeeLoanBillRegister.setCreateBy(DEFAULT_CREATE_BY);
        employeeLoanBillRegister.setUpdateDate(DEFAULT_UPDATE_DATE);
        employeeLoanBillRegister.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createEmployeeLoanBillRegister() throws Exception {
        int databaseSizeBeforeCreate = employeeLoanBillRegisterRepository.findAll().size();

        // Create the EmployeeLoanBillRegister

        restEmployeeLoanBillRegisterMockMvc.perform(post("/api/employeeLoanBillRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanBillRegister)))
                .andExpect(status().isCreated());

        // Validate the EmployeeLoanBillRegister in the database
        List<EmployeeLoanBillRegister> employeeLoanBillRegisters = employeeLoanBillRegisterRepository.findAll();
        assertThat(employeeLoanBillRegisters).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLoanBillRegister testEmployeeLoanBillRegister = employeeLoanBillRegisters.get(employeeLoanBillRegisters.size() - 1);
        assertThat(testEmployeeLoanBillRegister.getApplicationType()).isEqualTo(DEFAULT_APPLICATION_TYPE);
        assertThat(testEmployeeLoanBillRegister.getBillNo()).isEqualTo(DEFAULT_BILL_NO);
        assertThat(testEmployeeLoanBillRegister.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testEmployeeLoanBillRegister.getReceiverName()).isEqualTo(DEFAULT_RECEIVER_NAME);
        assertThat(testEmployeeLoanBillRegister.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testEmployeeLoanBillRegister.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeLoanBillRegister.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testEmployeeLoanBillRegister.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testEmployeeLoanBillRegister.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testEmployeeLoanBillRegister.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkApplicationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanBillRegisterRepository.findAll().size();
        // set the field null
        employeeLoanBillRegister.setApplicationType(null);

        // Create the EmployeeLoanBillRegister, which fails.

        restEmployeeLoanBillRegisterMockMvc.perform(post("/api/employeeLoanBillRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanBillRegister)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanBillRegister> employeeLoanBillRegisters = employeeLoanBillRegisterRepository.findAll();
        assertThat(employeeLoanBillRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBillNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanBillRegisterRepository.findAll().size();
        // set the field null
        employeeLoanBillRegister.setBillNo(null);

        // Create the EmployeeLoanBillRegister, which fails.

        restEmployeeLoanBillRegisterMockMvc.perform(post("/api/employeeLoanBillRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanBillRegister)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanBillRegister> employeeLoanBillRegisters = employeeLoanBillRegisterRepository.findAll();
        assertThat(employeeLoanBillRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanBillRegisterRepository.findAll().size();
        // set the field null
        employeeLoanBillRegister.setIssueDate(null);

        // Create the EmployeeLoanBillRegister, which fails.

        restEmployeeLoanBillRegisterMockMvc.perform(post("/api/employeeLoanBillRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanBillRegister)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanBillRegister> employeeLoanBillRegisters = employeeLoanBillRegisterRepository.findAll();
        assertThat(employeeLoanBillRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceiverNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanBillRegisterRepository.findAll().size();
        // set the field null
        employeeLoanBillRegister.setReceiverName(null);

        // Create the EmployeeLoanBillRegister, which fails.

        restEmployeeLoanBillRegisterMockMvc.perform(post("/api/employeeLoanBillRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanBillRegister)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanBillRegister> employeeLoanBillRegisters = employeeLoanBillRegisterRepository.findAll();
        assertThat(employeeLoanBillRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanBillRegisterRepository.findAll().size();
        // set the field null
        employeeLoanBillRegister.setPlace(null);

        // Create the EmployeeLoanBillRegister, which fails.

        restEmployeeLoanBillRegisterMockMvc.perform(post("/api/employeeLoanBillRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanBillRegister)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanBillRegister> employeeLoanBillRegisters = employeeLoanBillRegisterRepository.findAll();
        assertThat(employeeLoanBillRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeLoanBillRegisters() throws Exception {
        // Initialize the database
        employeeLoanBillRegisterRepository.saveAndFlush(employeeLoanBillRegister);

        // Get all the employeeLoanBillRegisters
        restEmployeeLoanBillRegisterMockMvc.perform(get("/api/employeeLoanBillRegisters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLoanBillRegister.getId().intValue())))
                .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO.toString())))
                .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
                .andExpect(jsonPath("$.[*].receiverName").value(hasItem(DEFAULT_RECEIVER_NAME.toString())))
                .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getEmployeeLoanBillRegister() throws Exception {
        // Initialize the database
        employeeLoanBillRegisterRepository.saveAndFlush(employeeLoanBillRegister);

        // Get the employeeLoanBillRegister
        restEmployeeLoanBillRegisterMockMvc.perform(get("/api/employeeLoanBillRegisters/{id}", employeeLoanBillRegister.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employeeLoanBillRegister.getId().intValue()))
            .andExpect(jsonPath("$.applicationType").value(DEFAULT_APPLICATION_TYPE.toString()))
            .andExpect(jsonPath("$.billNo").value(DEFAULT_BILL_NO.toString()))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.receiverName").value(DEFAULT_RECEIVER_NAME.toString()))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeLoanBillRegister() throws Exception {
        // Get the employeeLoanBillRegister
        restEmployeeLoanBillRegisterMockMvc.perform(get("/api/employeeLoanBillRegisters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLoanBillRegister() throws Exception {
        // Initialize the database
        employeeLoanBillRegisterRepository.saveAndFlush(employeeLoanBillRegister);

		int databaseSizeBeforeUpdate = employeeLoanBillRegisterRepository.findAll().size();

        // Update the employeeLoanBillRegister
        employeeLoanBillRegister.setApplicationType(UPDATED_APPLICATION_TYPE);
        employeeLoanBillRegister.setBillNo(UPDATED_BILL_NO);
        employeeLoanBillRegister.setIssueDate(UPDATED_ISSUE_DATE);
        employeeLoanBillRegister.setReceiverName(UPDATED_RECEIVER_NAME);
        employeeLoanBillRegister.setPlace(UPDATED_PLACE);
        employeeLoanBillRegister.setStatus(UPDATED_STATUS);
        employeeLoanBillRegister.setCreateDate(UPDATED_CREATE_DATE);
        employeeLoanBillRegister.setCreateBy(UPDATED_CREATE_BY);
        employeeLoanBillRegister.setUpdateDate(UPDATED_UPDATE_DATE);
        employeeLoanBillRegister.setUpdateBy(UPDATED_UPDATE_BY);

        restEmployeeLoanBillRegisterMockMvc.perform(put("/api/employeeLoanBillRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanBillRegister)))
                .andExpect(status().isOk());

        // Validate the EmployeeLoanBillRegister in the database
        List<EmployeeLoanBillRegister> employeeLoanBillRegisters = employeeLoanBillRegisterRepository.findAll();
        assertThat(employeeLoanBillRegisters).hasSize(databaseSizeBeforeUpdate);
        EmployeeLoanBillRegister testEmployeeLoanBillRegister = employeeLoanBillRegisters.get(employeeLoanBillRegisters.size() - 1);
        assertThat(testEmployeeLoanBillRegister.getApplicationType()).isEqualTo(UPDATED_APPLICATION_TYPE);
        assertThat(testEmployeeLoanBillRegister.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testEmployeeLoanBillRegister.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testEmployeeLoanBillRegister.getReceiverName()).isEqualTo(UPDATED_RECEIVER_NAME);
        assertThat(testEmployeeLoanBillRegister.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testEmployeeLoanBillRegister.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeLoanBillRegister.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testEmployeeLoanBillRegister.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testEmployeeLoanBillRegister.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testEmployeeLoanBillRegister.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteEmployeeLoanBillRegister() throws Exception {
        // Initialize the database
        employeeLoanBillRegisterRepository.saveAndFlush(employeeLoanBillRegister);

		int databaseSizeBeforeDelete = employeeLoanBillRegisterRepository.findAll().size();

        // Get the employeeLoanBillRegister
        restEmployeeLoanBillRegisterMockMvc.perform(delete("/api/employeeLoanBillRegisters/{id}", employeeLoanBillRegister.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeLoanBillRegister> employeeLoanBillRegisters = employeeLoanBillRegisterRepository.findAll();
        assertThat(employeeLoanBillRegisters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
