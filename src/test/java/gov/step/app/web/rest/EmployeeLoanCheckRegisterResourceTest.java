package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EmployeeLoanCheckRegister;
import gov.step.app.repository.EmployeeLoanCheckRegisterRepository;
import gov.step.app.repository.search.EmployeeLoanCheckRegisterSearchRepository;

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
 * Test class for the EmployeeLoanCheckRegisterResource REST controller.
 *
 * @see EmployeeLoanCheckRegisterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeLoanCheckRegisterResourceTest {

    private static final String DEFAULT_APPLICATION_TYPE = "AAAAA";
    private static final String UPDATED_APPLICATION_TYPE = "BBBBB";

    private static final Long DEFAULT_NUMBER_OF_WITHDRAWAL = 1L;
    private static final Long UPDATED_NUMBER_OF_WITHDRAWAL = 2L;
    private static final String DEFAULT_CHECK_NUMBER = "AAAAA";
    private static final String UPDATED_CHECK_NUMBER = "BBBBB";
    private static final String DEFAULT_TOKEN_NUMBER = "AAAAA";
    private static final String UPDATED_TOKEN_NUMBER = "BBBBB";

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
    private EmployeeLoanCheckRegisterRepository employeeLoanCheckRegisterRepository;

    @Inject
    private EmployeeLoanCheckRegisterSearchRepository employeeLoanCheckRegisterSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeLoanCheckRegisterMockMvc;

    private EmployeeLoanCheckRegister employeeLoanCheckRegister;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeLoanCheckRegisterResource employeeLoanCheckRegisterResource = new EmployeeLoanCheckRegisterResource();
        ReflectionTestUtils.setField(employeeLoanCheckRegisterResource, "employeeLoanCheckRegisterRepository", employeeLoanCheckRegisterRepository);
        ReflectionTestUtils.setField(employeeLoanCheckRegisterResource, "employeeLoanCheckRegisterSearchRepository", employeeLoanCheckRegisterSearchRepository);
        this.restEmployeeLoanCheckRegisterMockMvc = MockMvcBuilders.standaloneSetup(employeeLoanCheckRegisterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employeeLoanCheckRegister = new EmployeeLoanCheckRegister();
        employeeLoanCheckRegister.setApplicationType(DEFAULT_APPLICATION_TYPE);
        employeeLoanCheckRegister.setNumberOfWithdrawal(DEFAULT_NUMBER_OF_WITHDRAWAL);
        employeeLoanCheckRegister.setCheckNumber(DEFAULT_CHECK_NUMBER);
        employeeLoanCheckRegister.setTokenNumber(DEFAULT_TOKEN_NUMBER);
        employeeLoanCheckRegister.setStatus(DEFAULT_STATUS);
        employeeLoanCheckRegister.setCreateDate(DEFAULT_CREATE_DATE);
        employeeLoanCheckRegister.setCreateBy(DEFAULT_CREATE_BY);
        employeeLoanCheckRegister.setUpdateDate(DEFAULT_UPDATE_DATE);
        employeeLoanCheckRegister.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createEmployeeLoanCheckRegister() throws Exception {
        int databaseSizeBeforeCreate = employeeLoanCheckRegisterRepository.findAll().size();

        // Create the EmployeeLoanCheckRegister

        restEmployeeLoanCheckRegisterMockMvc.perform(post("/api/employeeLoanCheckRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanCheckRegister)))
                .andExpect(status().isCreated());

        // Validate the EmployeeLoanCheckRegister in the database
        List<EmployeeLoanCheckRegister> employeeLoanCheckRegisters = employeeLoanCheckRegisterRepository.findAll();
        assertThat(employeeLoanCheckRegisters).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLoanCheckRegister testEmployeeLoanCheckRegister = employeeLoanCheckRegisters.get(employeeLoanCheckRegisters.size() - 1);
        assertThat(testEmployeeLoanCheckRegister.getApplicationType()).isEqualTo(DEFAULT_APPLICATION_TYPE);
        assertThat(testEmployeeLoanCheckRegister.getNumberOfWithdrawal()).isEqualTo(DEFAULT_NUMBER_OF_WITHDRAWAL);
        assertThat(testEmployeeLoanCheckRegister.getCheckNumber()).isEqualTo(DEFAULT_CHECK_NUMBER);
        assertThat(testEmployeeLoanCheckRegister.getTokenNumber()).isEqualTo(DEFAULT_TOKEN_NUMBER);
        assertThat(testEmployeeLoanCheckRegister.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeLoanCheckRegister.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testEmployeeLoanCheckRegister.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testEmployeeLoanCheckRegister.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testEmployeeLoanCheckRegister.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkApplicationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanCheckRegisterRepository.findAll().size();
        // set the field null
        employeeLoanCheckRegister.setApplicationType(null);

        // Create the EmployeeLoanCheckRegister, which fails.

        restEmployeeLoanCheckRegisterMockMvc.perform(post("/api/employeeLoanCheckRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanCheckRegister)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanCheckRegister> employeeLoanCheckRegisters = employeeLoanCheckRegisterRepository.findAll();
        assertThat(employeeLoanCheckRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfWithdrawalIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanCheckRegisterRepository.findAll().size();
        // set the field null
        employeeLoanCheckRegister.setNumberOfWithdrawal(null);

        // Create the EmployeeLoanCheckRegister, which fails.

        restEmployeeLoanCheckRegisterMockMvc.perform(post("/api/employeeLoanCheckRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanCheckRegister)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanCheckRegister> employeeLoanCheckRegisters = employeeLoanCheckRegisterRepository.findAll();
        assertThat(employeeLoanCheckRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheckNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanCheckRegisterRepository.findAll().size();
        // set the field null
        employeeLoanCheckRegister.setCheckNumber(null);

        // Create the EmployeeLoanCheckRegister, which fails.

        restEmployeeLoanCheckRegisterMockMvc.perform(post("/api/employeeLoanCheckRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanCheckRegister)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanCheckRegister> employeeLoanCheckRegisters = employeeLoanCheckRegisterRepository.findAll();
        assertThat(employeeLoanCheckRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTokenNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanCheckRegisterRepository.findAll().size();
        // set the field null
        employeeLoanCheckRegister.setTokenNumber(null);

        // Create the EmployeeLoanCheckRegister, which fails.

        restEmployeeLoanCheckRegisterMockMvc.perform(post("/api/employeeLoanCheckRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanCheckRegister)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanCheckRegister> employeeLoanCheckRegisters = employeeLoanCheckRegisterRepository.findAll();
        assertThat(employeeLoanCheckRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeLoanCheckRegisters() throws Exception {
        // Initialize the database
        employeeLoanCheckRegisterRepository.saveAndFlush(employeeLoanCheckRegister);

        // Get all the employeeLoanCheckRegisters
        restEmployeeLoanCheckRegisterMockMvc.perform(get("/api/employeeLoanCheckRegisters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLoanCheckRegister.getId().intValue())))
                .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].numberOfWithdrawal").value(hasItem(DEFAULT_NUMBER_OF_WITHDRAWAL.intValue())))
                .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].tokenNumber").value(hasItem(DEFAULT_TOKEN_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getEmployeeLoanCheckRegister() throws Exception {
        // Initialize the database
        employeeLoanCheckRegisterRepository.saveAndFlush(employeeLoanCheckRegister);

        // Get the employeeLoanCheckRegister
        restEmployeeLoanCheckRegisterMockMvc.perform(get("/api/employeeLoanCheckRegisters/{id}", employeeLoanCheckRegister.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employeeLoanCheckRegister.getId().intValue()))
            .andExpect(jsonPath("$.applicationType").value(DEFAULT_APPLICATION_TYPE.toString()))
            .andExpect(jsonPath("$.numberOfWithdrawal").value(DEFAULT_NUMBER_OF_WITHDRAWAL.intValue()))
            .andExpect(jsonPath("$.checkNumber").value(DEFAULT_CHECK_NUMBER.toString()))
            .andExpect(jsonPath("$.tokenNumber").value(DEFAULT_TOKEN_NUMBER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeLoanCheckRegister() throws Exception {
        // Get the employeeLoanCheckRegister
        restEmployeeLoanCheckRegisterMockMvc.perform(get("/api/employeeLoanCheckRegisters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLoanCheckRegister() throws Exception {
        // Initialize the database
        employeeLoanCheckRegisterRepository.saveAndFlush(employeeLoanCheckRegister);

		int databaseSizeBeforeUpdate = employeeLoanCheckRegisterRepository.findAll().size();

        // Update the employeeLoanCheckRegister
        employeeLoanCheckRegister.setApplicationType(UPDATED_APPLICATION_TYPE);
        employeeLoanCheckRegister.setNumberOfWithdrawal(UPDATED_NUMBER_OF_WITHDRAWAL);
        employeeLoanCheckRegister.setCheckNumber(UPDATED_CHECK_NUMBER);
        employeeLoanCheckRegister.setTokenNumber(UPDATED_TOKEN_NUMBER);
        employeeLoanCheckRegister.setStatus(UPDATED_STATUS);
        employeeLoanCheckRegister.setCreateDate(UPDATED_CREATE_DATE);
        employeeLoanCheckRegister.setCreateBy(UPDATED_CREATE_BY);
        employeeLoanCheckRegister.setUpdateDate(UPDATED_UPDATE_DATE);
        employeeLoanCheckRegister.setUpdateBy(UPDATED_UPDATE_BY);

        restEmployeeLoanCheckRegisterMockMvc.perform(put("/api/employeeLoanCheckRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanCheckRegister)))
                .andExpect(status().isOk());

        // Validate the EmployeeLoanCheckRegister in the database
        List<EmployeeLoanCheckRegister> employeeLoanCheckRegisters = employeeLoanCheckRegisterRepository.findAll();
        assertThat(employeeLoanCheckRegisters).hasSize(databaseSizeBeforeUpdate);
        EmployeeLoanCheckRegister testEmployeeLoanCheckRegister = employeeLoanCheckRegisters.get(employeeLoanCheckRegisters.size() - 1);
        assertThat(testEmployeeLoanCheckRegister.getApplicationType()).isEqualTo(UPDATED_APPLICATION_TYPE);
        assertThat(testEmployeeLoanCheckRegister.getNumberOfWithdrawal()).isEqualTo(UPDATED_NUMBER_OF_WITHDRAWAL);
        assertThat(testEmployeeLoanCheckRegister.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testEmployeeLoanCheckRegister.getTokenNumber()).isEqualTo(UPDATED_TOKEN_NUMBER);
        assertThat(testEmployeeLoanCheckRegister.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeLoanCheckRegister.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testEmployeeLoanCheckRegister.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testEmployeeLoanCheckRegister.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testEmployeeLoanCheckRegister.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteEmployeeLoanCheckRegister() throws Exception {
        // Initialize the database
        employeeLoanCheckRegisterRepository.saveAndFlush(employeeLoanCheckRegister);

		int databaseSizeBeforeDelete = employeeLoanCheckRegisterRepository.findAll().size();

        // Get the employeeLoanCheckRegister
        restEmployeeLoanCheckRegisterMockMvc.perform(delete("/api/employeeLoanCheckRegisters/{id}", employeeLoanCheckRegister.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeLoanCheckRegister> employeeLoanCheckRegisters = employeeLoanCheckRegisterRepository.findAll();
        assertThat(employeeLoanCheckRegisters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
