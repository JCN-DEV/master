package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PfmsRegister;
import gov.step.app.repository.PfmsRegisterRepository;
import gov.step.app.repository.search.PfmsRegisterSearchRepository;

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
 * Test class for the PfmsRegisterResource REST controller.
 *
 * @see PfmsRegisterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PfmsRegisterResourceTest {

    private static final String DEFAULT_APPLICATION_TYPE = "AAAAA";
    private static final String UPDATED_APPLICATION_TYPE = "BBBBB";

    private static final Boolean DEFAULT_IS_BILL_REGISTER = false;
    private static final Boolean UPDATED_IS_BILL_REGISTER = true;
    private static final String DEFAULT_BILL_NO = "AAAAA";
    private static final String UPDATED_BILL_NO = "BBBBB";

    private static final LocalDate DEFAULT_BILL_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BILL_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_BILL_RECEIVER_NAME = "AAAAA";
    private static final String UPDATED_BILL_RECEIVER_NAME = "BBBBB";
    private static final String DEFAULT_BILL_PLACE = "AAAAA";
    private static final String UPDATED_BILL_PLACE = "BBBBB";

    private static final LocalDate DEFAULT_BILL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BILL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_NO_OF_WITHDRAWAL = 1L;
    private static final Long UPDATED_NO_OF_WITHDRAWAL = 2L;
    private static final String DEFAULT_CHECK_NO = "AAAAA";
    private static final String UPDATED_CHECK_NO = "BBBBB";

    private static final LocalDate DEFAULT_CHECK_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECK_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CHECK_TOKEN_NO = "AAAAA";
    private static final String UPDATED_CHECK_TOKEN_NO = "BBBBB";

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
    private PfmsRegisterRepository pfmsRegisterRepository;

    @Inject
    private PfmsRegisterSearchRepository pfmsRegisterSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPfmsRegisterMockMvc;

    private PfmsRegister pfmsRegister;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PfmsRegisterResource pfmsRegisterResource = new PfmsRegisterResource();
        ReflectionTestUtils.setField(pfmsRegisterResource, "pfmsRegisterRepository", pfmsRegisterRepository);
        ReflectionTestUtils.setField(pfmsRegisterResource, "pfmsRegisterSearchRepository", pfmsRegisterSearchRepository);
        this.restPfmsRegisterMockMvc = MockMvcBuilders.standaloneSetup(pfmsRegisterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pfmsRegister = new PfmsRegister();
        pfmsRegister.setApplicationType(DEFAULT_APPLICATION_TYPE);
        pfmsRegister.setIsBillRegister(DEFAULT_IS_BILL_REGISTER);
        pfmsRegister.setBillNo(DEFAULT_BILL_NO);
        pfmsRegister.setBillIssueDate(DEFAULT_BILL_ISSUE_DATE);
        pfmsRegister.setBillReceiverName(DEFAULT_BILL_RECEIVER_NAME);
        pfmsRegister.setBillPlace(DEFAULT_BILL_PLACE);
        pfmsRegister.setBillDate(DEFAULT_BILL_DATE);
        pfmsRegister.setNoOfWithdrawal(DEFAULT_NO_OF_WITHDRAWAL);
        pfmsRegister.setCheckNo(DEFAULT_CHECK_NO);
        pfmsRegister.setCheckDate(DEFAULT_CHECK_DATE);
        pfmsRegister.setCheckTokenNo(DEFAULT_CHECK_TOKEN_NO);
        pfmsRegister.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        pfmsRegister.setCreateDate(DEFAULT_CREATE_DATE);
        pfmsRegister.setCreateBy(DEFAULT_CREATE_BY);
        pfmsRegister.setUpdateDate(DEFAULT_UPDATE_DATE);
        pfmsRegister.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPfmsRegister() throws Exception {
        int databaseSizeBeforeCreate = pfmsRegisterRepository.findAll().size();

        // Create the PfmsRegister

        restPfmsRegisterMockMvc.perform(post("/api/pfmsRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsRegister)))
                .andExpect(status().isCreated());

        // Validate the PfmsRegister in the database
        List<PfmsRegister> pfmsRegisters = pfmsRegisterRepository.findAll();
        assertThat(pfmsRegisters).hasSize(databaseSizeBeforeCreate + 1);
        PfmsRegister testPfmsRegister = pfmsRegisters.get(pfmsRegisters.size() - 1);
        assertThat(testPfmsRegister.getApplicationType()).isEqualTo(DEFAULT_APPLICATION_TYPE);
        assertThat(testPfmsRegister.getIsBillRegister()).isEqualTo(DEFAULT_IS_BILL_REGISTER);
        assertThat(testPfmsRegister.getBillNo()).isEqualTo(DEFAULT_BILL_NO);
        assertThat(testPfmsRegister.getBillIssueDate()).isEqualTo(DEFAULT_BILL_ISSUE_DATE);
        assertThat(testPfmsRegister.getBillReceiverName()).isEqualTo(DEFAULT_BILL_RECEIVER_NAME);
        assertThat(testPfmsRegister.getBillPlace()).isEqualTo(DEFAULT_BILL_PLACE);
        assertThat(testPfmsRegister.getBillDate()).isEqualTo(DEFAULT_BILL_DATE);
        assertThat(testPfmsRegister.getNoOfWithdrawal()).isEqualTo(DEFAULT_NO_OF_WITHDRAWAL);
        assertThat(testPfmsRegister.getCheckNo()).isEqualTo(DEFAULT_CHECK_NO);
        assertThat(testPfmsRegister.getCheckDate()).isEqualTo(DEFAULT_CHECK_DATE);
        assertThat(testPfmsRegister.getCheckTokenNo()).isEqualTo(DEFAULT_CHECK_TOKEN_NO);
        assertThat(testPfmsRegister.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testPfmsRegister.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPfmsRegister.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPfmsRegister.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPfmsRegister.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkApplicationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsRegisterRepository.findAll().size();
        // set the field null
        pfmsRegister.setApplicationType(null);

        // Create the PfmsRegister, which fails.

        restPfmsRegisterMockMvc.perform(post("/api/pfmsRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsRegister)))
                .andExpect(status().isBadRequest());

        List<PfmsRegister> pfmsRegisters = pfmsRegisterRepository.findAll();
        assertThat(pfmsRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsBillRegisterIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfmsRegisterRepository.findAll().size();
        // set the field null
        pfmsRegister.setIsBillRegister(null);

        // Create the PfmsRegister, which fails.

        restPfmsRegisterMockMvc.perform(post("/api/pfmsRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsRegister)))
                .andExpect(status().isBadRequest());

        List<PfmsRegister> pfmsRegisters = pfmsRegisterRepository.findAll();
        assertThat(pfmsRegisters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPfmsRegisters() throws Exception {
        // Initialize the database
        pfmsRegisterRepository.saveAndFlush(pfmsRegister);

        // Get all the pfmsRegisters
        restPfmsRegisterMockMvc.perform(get("/api/pfmsRegisters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pfmsRegister.getId().intValue())))
                .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].isBillRegister").value(hasItem(DEFAULT_IS_BILL_REGISTER.booleanValue())))
                .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO.toString())))
                .andExpect(jsonPath("$.[*].billIssueDate").value(hasItem(DEFAULT_BILL_ISSUE_DATE.toString())))
                .andExpect(jsonPath("$.[*].billReceiverName").value(hasItem(DEFAULT_BILL_RECEIVER_NAME.toString())))
                .andExpect(jsonPath("$.[*].billPlace").value(hasItem(DEFAULT_BILL_PLACE.toString())))
                .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
                .andExpect(jsonPath("$.[*].noOfWithdrawal").value(hasItem(DEFAULT_NO_OF_WITHDRAWAL.intValue())))
                .andExpect(jsonPath("$.[*].checkNo").value(hasItem(DEFAULT_CHECK_NO.toString())))
                .andExpect(jsonPath("$.[*].checkDate").value(hasItem(DEFAULT_CHECK_DATE.toString())))
                .andExpect(jsonPath("$.[*].checkTokenNo").value(hasItem(DEFAULT_CHECK_TOKEN_NO.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPfmsRegister() throws Exception {
        // Initialize the database
        pfmsRegisterRepository.saveAndFlush(pfmsRegister);

        // Get the pfmsRegister
        restPfmsRegisterMockMvc.perform(get("/api/pfmsRegisters/{id}", pfmsRegister.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pfmsRegister.getId().intValue()))
            .andExpect(jsonPath("$.applicationType").value(DEFAULT_APPLICATION_TYPE.toString()))
            .andExpect(jsonPath("$.isBillRegister").value(DEFAULT_IS_BILL_REGISTER.booleanValue()))
            .andExpect(jsonPath("$.billNo").value(DEFAULT_BILL_NO.toString()))
            .andExpect(jsonPath("$.billIssueDate").value(DEFAULT_BILL_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.billReceiverName").value(DEFAULT_BILL_RECEIVER_NAME.toString()))
            .andExpect(jsonPath("$.billPlace").value(DEFAULT_BILL_PLACE.toString()))
            .andExpect(jsonPath("$.billDate").value(DEFAULT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.noOfWithdrawal").value(DEFAULT_NO_OF_WITHDRAWAL.intValue()))
            .andExpect(jsonPath("$.checkNo").value(DEFAULT_CHECK_NO.toString()))
            .andExpect(jsonPath("$.checkDate").value(DEFAULT_CHECK_DATE.toString()))
            .andExpect(jsonPath("$.checkTokenNo").value(DEFAULT_CHECK_TOKEN_NO.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPfmsRegister() throws Exception {
        // Get the pfmsRegister
        restPfmsRegisterMockMvc.perform(get("/api/pfmsRegisters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePfmsRegister() throws Exception {
        // Initialize the database
        pfmsRegisterRepository.saveAndFlush(pfmsRegister);

		int databaseSizeBeforeUpdate = pfmsRegisterRepository.findAll().size();

        // Update the pfmsRegister
        pfmsRegister.setApplicationType(UPDATED_APPLICATION_TYPE);
        pfmsRegister.setIsBillRegister(UPDATED_IS_BILL_REGISTER);
        pfmsRegister.setBillNo(UPDATED_BILL_NO);
        pfmsRegister.setBillIssueDate(UPDATED_BILL_ISSUE_DATE);
        pfmsRegister.setBillReceiverName(UPDATED_BILL_RECEIVER_NAME);
        pfmsRegister.setBillPlace(UPDATED_BILL_PLACE);
        pfmsRegister.setBillDate(UPDATED_BILL_DATE);
        pfmsRegister.setNoOfWithdrawal(UPDATED_NO_OF_WITHDRAWAL);
        pfmsRegister.setCheckNo(UPDATED_CHECK_NO);
        pfmsRegister.setCheckDate(UPDATED_CHECK_DATE);
        pfmsRegister.setCheckTokenNo(UPDATED_CHECK_TOKEN_NO);
        pfmsRegister.setActiveStatus(UPDATED_ACTIVE_STATUS);
        pfmsRegister.setCreateDate(UPDATED_CREATE_DATE);
        pfmsRegister.setCreateBy(UPDATED_CREATE_BY);
        pfmsRegister.setUpdateDate(UPDATED_UPDATE_DATE);
        pfmsRegister.setUpdateBy(UPDATED_UPDATE_BY);

        restPfmsRegisterMockMvc.perform(put("/api/pfmsRegisters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pfmsRegister)))
                .andExpect(status().isOk());

        // Validate the PfmsRegister in the database
        List<PfmsRegister> pfmsRegisters = pfmsRegisterRepository.findAll();
        assertThat(pfmsRegisters).hasSize(databaseSizeBeforeUpdate);
        PfmsRegister testPfmsRegister = pfmsRegisters.get(pfmsRegisters.size() - 1);
        assertThat(testPfmsRegister.getApplicationType()).isEqualTo(UPDATED_APPLICATION_TYPE);
        assertThat(testPfmsRegister.getIsBillRegister()).isEqualTo(UPDATED_IS_BILL_REGISTER);
        assertThat(testPfmsRegister.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testPfmsRegister.getBillIssueDate()).isEqualTo(UPDATED_BILL_ISSUE_DATE);
        assertThat(testPfmsRegister.getBillReceiverName()).isEqualTo(UPDATED_BILL_RECEIVER_NAME);
        assertThat(testPfmsRegister.getBillPlace()).isEqualTo(UPDATED_BILL_PLACE);
        assertThat(testPfmsRegister.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testPfmsRegister.getNoOfWithdrawal()).isEqualTo(UPDATED_NO_OF_WITHDRAWAL);
        assertThat(testPfmsRegister.getCheckNo()).isEqualTo(UPDATED_CHECK_NO);
        assertThat(testPfmsRegister.getCheckDate()).isEqualTo(UPDATED_CHECK_DATE);
        assertThat(testPfmsRegister.getCheckTokenNo()).isEqualTo(UPDATED_CHECK_TOKEN_NO);
        assertThat(testPfmsRegister.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testPfmsRegister.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPfmsRegister.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPfmsRegister.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPfmsRegister.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deletePfmsRegister() throws Exception {
        // Initialize the database
        pfmsRegisterRepository.saveAndFlush(pfmsRegister);

		int databaseSizeBeforeDelete = pfmsRegisterRepository.findAll().size();

        // Get the pfmsRegister
        restPfmsRegisterMockMvc.perform(delete("/api/pfmsRegisters/{id}", pfmsRegister.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PfmsRegister> pfmsRegisters = pfmsRegisterRepository.findAll();
        assertThat(pfmsRegisters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
