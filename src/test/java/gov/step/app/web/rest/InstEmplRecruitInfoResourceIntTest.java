package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmplRecruitInfo;
import gov.step.app.repository.InstEmplRecruitInfoRepository;
import gov.step.app.repository.search.InstEmplRecruitInfoSearchRepository;

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
 * Test class for the InstEmplRecruitInfoResource REST controller.
 *
 * @see InstEmplRecruitInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmplRecruitInfoResourceIntTest {

    private static final String DEFAULT_SALARY_SCALE = "AAAAA";
    private static final String UPDATED_SALARY_SCALE = "BBBBB";
    private static final String DEFAULT_SALARY_CODE = "AAAAA";
    private static final String UPDATED_SALARY_CODE = "BBBBB";

    private static final BigDecimal DEFAULT_MONTHLY_SALARY_GOVT_PROVIDED = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTHLY_SALARY_GOVT_PROVIDED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTHLY_SALARY_INSTITUTE_PROVIDED = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTHLY_SALARY_INSTITUTE_PROVIDED = new BigDecimal(2);

    private static final LocalDate DEFAULT_GB_RESOLUTION_RECEIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GB_RESOLUTION_RECEIVE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_GB_RESOLUTION_AGENDA_NO = "AAAAA";
    private static final String UPDATED_GB_RESOLUTION_AGENDA_NO = "BBBBB";
    private static final String DEFAULT_CIRCULAR_PAPER_NAME = "AAAAA";
    private static final String UPDATED_CIRCULAR_PAPER_NAME = "BBBBB";

    private static final LocalDate DEFAULT_CIRCULAR_PUBLISHED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CIRCULAR_PUBLISHED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RECRUIT_EXAM_RECEIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECRUIT_EXAM_RECEIVE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DG_REPRESENTATIVE_NAME = "AAAAA";
    private static final String UPDATED_DG_REPRESENTATIVE_NAME = "BBBBB";
    private static final String DEFAULT_DG_REPRESENTATIVE_DESIGNATION = "AAAAA";
    private static final String UPDATED_DG_REPRESENTATIVE_DESIGNATION = "BBBBB";
    private static final String DEFAULT_DG_REPRESENTATIVE_ADDRESS = "AAAAA";
    private static final String UPDATED_DG_REPRESENTATIVE_ADDRESS = "BBBBB";
    private static final String DEFAULT_BOARD_REPRESENTATIVE_NAME = "AAAAA";
    private static final String UPDATED_BOARD_REPRESENTATIVE_NAME = "BBBBB";
    private static final String DEFAULT_BOARD_REPRESENTATIVE_DESIGNATION = "AAAAA";
    private static final String UPDATED_BOARD_REPRESENTATIVE_DESIGNATION = "BBBBB";
    private static final String DEFAULT_BOARD_REPRESENTATIVE_ADDRESS = "AAAAA";
    private static final String UPDATED_BOARD_REPRESENTATIVE_ADDRESS = "BBBBB";

    private static final LocalDate DEFAULT_RECRUIT_APPROVE_GBRESOLUTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECRUIT_APPROVE_GBRESOLUTION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_RECRUIT_PERMIT_AGENDA_NO = "AAAAA";
    private static final String UPDATED_RECRUIT_PERMIT_AGENDA_NO = "BBBBB";

    private static final LocalDate DEFAULT_RECRUITMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECRUITMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PRESENT_INSTITUTE_JOIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRESENT_INSTITUTE_JOIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PRESENT_POST_JOIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRESENT_POST_JOIN_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DG_REPRESENTATIVE_RECORD_NO = "AAAAA";
    private static final String UPDATED_DG_REPRESENTATIVE_RECORD_NO = "BBBBB";
    private static final String DEFAULT_BOARD_REPRESENTATIVE_RECORD_NO = "AAAAA";
    private static final String UPDATED_BOARD_REPRESENTATIVE_RECORD_NO = "BBBBB";
    private static final String DEFAULT_DEPARTMENT = "AAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBB";

    @Inject
    private InstEmplRecruitInfoRepository instEmplRecruitInfoRepository;

    @Inject
    private InstEmplRecruitInfoSearchRepository instEmplRecruitInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmplRecruitInfoMockMvc;

    private InstEmplRecruitInfo instEmplRecruitInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmplRecruitInfoResource instEmplRecruitInfoResource = new InstEmplRecruitInfoResource();
        ReflectionTestUtils.setField(instEmplRecruitInfoResource, "instEmplRecruitInfoRepository", instEmplRecruitInfoRepository);
        ReflectionTestUtils.setField(instEmplRecruitInfoResource, "instEmplRecruitInfoSearchRepository", instEmplRecruitInfoSearchRepository);
        this.restInstEmplRecruitInfoMockMvc = MockMvcBuilders.standaloneSetup(instEmplRecruitInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmplRecruitInfo = new InstEmplRecruitInfo();
        instEmplRecruitInfo.setSalaryScale(DEFAULT_SALARY_SCALE);
        instEmplRecruitInfo.setSalaryCode(DEFAULT_SALARY_CODE);
        instEmplRecruitInfo.setMonthlySalaryGovtProvided(DEFAULT_MONTHLY_SALARY_GOVT_PROVIDED);
        instEmplRecruitInfo.setMonthlySalaryInstituteProvided(DEFAULT_MONTHLY_SALARY_INSTITUTE_PROVIDED);
        instEmplRecruitInfo.setGbResolutionReceiveDate(DEFAULT_GB_RESOLUTION_RECEIVE_DATE);
        instEmplRecruitInfo.setGbResolutionAgendaNo(DEFAULT_GB_RESOLUTION_AGENDA_NO);
        instEmplRecruitInfo.setCircularPaperName(DEFAULT_CIRCULAR_PAPER_NAME);
        instEmplRecruitInfo.setCircularPublishedDate(DEFAULT_CIRCULAR_PUBLISHED_DATE);
        instEmplRecruitInfo.setRecruitExamReceiveDate(DEFAULT_RECRUIT_EXAM_RECEIVE_DATE);
        instEmplRecruitInfo.setDgRepresentativeName(DEFAULT_DG_REPRESENTATIVE_NAME);
        instEmplRecruitInfo.setDgRepresentativeDesignation(DEFAULT_DG_REPRESENTATIVE_DESIGNATION);
        instEmplRecruitInfo.setDgRepresentativeAddress(DEFAULT_DG_REPRESENTATIVE_ADDRESS);
        instEmplRecruitInfo.setBoardRepresentativeName(DEFAULT_BOARD_REPRESENTATIVE_NAME);
        instEmplRecruitInfo.setBoardRepresentativeDesignation(DEFAULT_BOARD_REPRESENTATIVE_DESIGNATION);
        instEmplRecruitInfo.setBoardRepresentativeAddress(DEFAULT_BOARD_REPRESENTATIVE_ADDRESS);
        instEmplRecruitInfo.setRecruitApproveGBResolutionDate(DEFAULT_RECRUIT_APPROVE_GBRESOLUTION_DATE);
        instEmplRecruitInfo.setRecruitPermitAgendaNo(DEFAULT_RECRUIT_PERMIT_AGENDA_NO);
        instEmplRecruitInfo.setRecruitmentDate(DEFAULT_RECRUITMENT_DATE);
        instEmplRecruitInfo.setPresentInstituteJoinDate(DEFAULT_PRESENT_INSTITUTE_JOIN_DATE);
        instEmplRecruitInfo.setPresentPostJoinDate(DEFAULT_PRESENT_POST_JOIN_DATE);
        instEmplRecruitInfo.setDgRepresentativeRecordNo(DEFAULT_DG_REPRESENTATIVE_RECORD_NO);
        instEmplRecruitInfo.setBoardRepresentativeRecordNo(DEFAULT_BOARD_REPRESENTATIVE_RECORD_NO);
        instEmplRecruitInfo.setDepartment(DEFAULT_DEPARTMENT);
    }

    @Test
    @Transactional
    public void createInstEmplRecruitInfo() throws Exception {
        int databaseSizeBeforeCreate = instEmplRecruitInfoRepository.findAll().size();

        // Create the InstEmplRecruitInfo

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isCreated());

        // Validate the InstEmplRecruitInfo in the database
        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstEmplRecruitInfo testInstEmplRecruitInfo = instEmplRecruitInfos.get(instEmplRecruitInfos.size() - 1);
        assertThat(testInstEmplRecruitInfo.getSalaryScale()).isEqualTo(DEFAULT_SALARY_SCALE);
        assertThat(testInstEmplRecruitInfo.getSalaryCode()).isEqualTo(DEFAULT_SALARY_CODE);
        assertThat(testInstEmplRecruitInfo.getMonthlySalaryGovtProvided()).isEqualTo(DEFAULT_MONTHLY_SALARY_GOVT_PROVIDED);
        assertThat(testInstEmplRecruitInfo.getMonthlySalaryInstituteProvided()).isEqualTo(DEFAULT_MONTHLY_SALARY_INSTITUTE_PROVIDED);
        assertThat(testInstEmplRecruitInfo.getGbResolutionReceiveDate()).isEqualTo(DEFAULT_GB_RESOLUTION_RECEIVE_DATE);
        assertThat(testInstEmplRecruitInfo.getGbResolutionAgendaNo()).isEqualTo(DEFAULT_GB_RESOLUTION_AGENDA_NO);
        assertThat(testInstEmplRecruitInfo.getCircularPaperName()).isEqualTo(DEFAULT_CIRCULAR_PAPER_NAME);
        assertThat(testInstEmplRecruitInfo.getCircularPublishedDate()).isEqualTo(DEFAULT_CIRCULAR_PUBLISHED_DATE);
        assertThat(testInstEmplRecruitInfo.getRecruitExamReceiveDate()).isEqualTo(DEFAULT_RECRUIT_EXAM_RECEIVE_DATE);
        assertThat(testInstEmplRecruitInfo.getDgRepresentativeName()).isEqualTo(DEFAULT_DG_REPRESENTATIVE_NAME);
        assertThat(testInstEmplRecruitInfo.getDgRepresentativeDesignation()).isEqualTo(DEFAULT_DG_REPRESENTATIVE_DESIGNATION);
        assertThat(testInstEmplRecruitInfo.getDgRepresentativeAddress()).isEqualTo(DEFAULT_DG_REPRESENTATIVE_ADDRESS);
        assertThat(testInstEmplRecruitInfo.getBoardRepresentativeName()).isEqualTo(DEFAULT_BOARD_REPRESENTATIVE_NAME);
        assertThat(testInstEmplRecruitInfo.getBoardRepresentativeDesignation()).isEqualTo(DEFAULT_BOARD_REPRESENTATIVE_DESIGNATION);
        assertThat(testInstEmplRecruitInfo.getBoardRepresentativeAddress()).isEqualTo(DEFAULT_BOARD_REPRESENTATIVE_ADDRESS);
        assertThat(testInstEmplRecruitInfo.getRecruitApproveGBResolutionDate()).isEqualTo(DEFAULT_RECRUIT_APPROVE_GBRESOLUTION_DATE);
        assertThat(testInstEmplRecruitInfo.getRecruitPermitAgendaNo()).isEqualTo(DEFAULT_RECRUIT_PERMIT_AGENDA_NO);
        assertThat(testInstEmplRecruitInfo.getRecruitmentDate()).isEqualTo(DEFAULT_RECRUITMENT_DATE);
        assertThat(testInstEmplRecruitInfo.getPresentInstituteJoinDate()).isEqualTo(DEFAULT_PRESENT_INSTITUTE_JOIN_DATE);
        assertThat(testInstEmplRecruitInfo.getPresentPostJoinDate()).isEqualTo(DEFAULT_PRESENT_POST_JOIN_DATE);
        assertThat(testInstEmplRecruitInfo.getDgRepresentativeRecordNo()).isEqualTo(DEFAULT_DG_REPRESENTATIVE_RECORD_NO);
        assertThat(testInstEmplRecruitInfo.getBoardRepresentativeRecordNo()).isEqualTo(DEFAULT_BOARD_REPRESENTATIVE_RECORD_NO);
        assertThat(testInstEmplRecruitInfo.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
    }

    @Test
    @Transactional
    public void checkSalaryScaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setSalaryScale(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setSalaryCode(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthlySalaryGovtProvidedIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setMonthlySalaryGovtProvided(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthlySalaryInstituteProvidedIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setMonthlySalaryInstituteProvided(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGbResolutionReceiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setGbResolutionReceiveDate(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGbResolutionAgendaNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setGbResolutionAgendaNo(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCircularPaperNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setCircularPaperName(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCircularPublishedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setCircularPublishedDate(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecruitExamReceiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setRecruitExamReceiveDate(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDgRepresentativeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setDgRepresentativeName(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDgRepresentativeDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setDgRepresentativeDesignation(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDgRepresentativeAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setDgRepresentativeAddress(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoardRepresentativeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setBoardRepresentativeName(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoardRepresentativeDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setBoardRepresentativeDesignation(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoardRepresentativeAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setBoardRepresentativeAddress(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecruitApproveGBResolutionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setRecruitApproveGBResolutionDate(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecruitmentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setRecruitmentDate(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresentInstituteJoinDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setPresentInstituteJoinDate(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresentPostJoinDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setPresentPostJoinDate(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDgRepresentativeRecordNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setDgRepresentativeRecordNo(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoardRepresentativeRecordNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplRecruitInfoRepository.findAll().size();
        // set the field null
        instEmplRecruitInfo.setBoardRepresentativeRecordNo(null);

        // Create the InstEmplRecruitInfo, which fails.

        restInstEmplRecruitInfoMockMvc.perform(post("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isBadRequest());

        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstEmplRecruitInfos() throws Exception {
        // Initialize the database
        instEmplRecruitInfoRepository.saveAndFlush(instEmplRecruitInfo);

        // Get all the instEmplRecruitInfos
        restInstEmplRecruitInfoMockMvc.perform(get("/api/instEmplRecruitInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmplRecruitInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].salaryScale").value(hasItem(DEFAULT_SALARY_SCALE.toString())))
                .andExpect(jsonPath("$.[*].salaryCode").value(hasItem(DEFAULT_SALARY_CODE.toString())))
                .andExpect(jsonPath("$.[*].monthlySalaryGovtProvided").value(hasItem(DEFAULT_MONTHLY_SALARY_GOVT_PROVIDED.intValue())))
                .andExpect(jsonPath("$.[*].monthlySalaryInstituteProvided").value(hasItem(DEFAULT_MONTHLY_SALARY_INSTITUTE_PROVIDED.intValue())))
                .andExpect(jsonPath("$.[*].gbResolutionReceiveDate").value(hasItem(DEFAULT_GB_RESOLUTION_RECEIVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].gbResolutionAgendaNo").value(hasItem(DEFAULT_GB_RESOLUTION_AGENDA_NO.toString())))
                .andExpect(jsonPath("$.[*].circularPaperName").value(hasItem(DEFAULT_CIRCULAR_PAPER_NAME.toString())))
                .andExpect(jsonPath("$.[*].circularPublishedDate").value(hasItem(DEFAULT_CIRCULAR_PUBLISHED_DATE.toString())))
                .andExpect(jsonPath("$.[*].recruitExamReceiveDate").value(hasItem(DEFAULT_RECRUIT_EXAM_RECEIVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].dgRepresentativeName").value(hasItem(DEFAULT_DG_REPRESENTATIVE_NAME.toString())))
                .andExpect(jsonPath("$.[*].dgRepresentativeDesignation").value(hasItem(DEFAULT_DG_REPRESENTATIVE_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].dgRepresentativeAddress").value(hasItem(DEFAULT_DG_REPRESENTATIVE_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].boardRepresentativeName").value(hasItem(DEFAULT_BOARD_REPRESENTATIVE_NAME.toString())))
                .andExpect(jsonPath("$.[*].boardRepresentativeDesignation").value(hasItem(DEFAULT_BOARD_REPRESENTATIVE_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].boardRepresentativeAddress").value(hasItem(DEFAULT_BOARD_REPRESENTATIVE_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].recruitApproveGBResolutionDate").value(hasItem(DEFAULT_RECRUIT_APPROVE_GBRESOLUTION_DATE.toString())))
                .andExpect(jsonPath("$.[*].recruitPermitAgendaNo").value(hasItem(DEFAULT_RECRUIT_PERMIT_AGENDA_NO.toString())))
                .andExpect(jsonPath("$.[*].recruitmentDate").value(hasItem(DEFAULT_RECRUITMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].presentInstituteJoinDate").value(hasItem(DEFAULT_PRESENT_INSTITUTE_JOIN_DATE.toString())))
                .andExpect(jsonPath("$.[*].presentPostJoinDate").value(hasItem(DEFAULT_PRESENT_POST_JOIN_DATE.toString())))
                .andExpect(jsonPath("$.[*].dgRepresentativeRecordNo").value(hasItem(DEFAULT_DG_REPRESENTATIVE_RECORD_NO.toString())))
                .andExpect(jsonPath("$.[*].boardRepresentativeRecordNo").value(hasItem(DEFAULT_BOARD_REPRESENTATIVE_RECORD_NO.toString())))
                .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())));
    }

    @Test
    @Transactional
    public void getInstEmplRecruitInfo() throws Exception {
        // Initialize the database
        instEmplRecruitInfoRepository.saveAndFlush(instEmplRecruitInfo);

        // Get the instEmplRecruitInfo
        restInstEmplRecruitInfoMockMvc.perform(get("/api/instEmplRecruitInfos/{id}", instEmplRecruitInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmplRecruitInfo.getId().intValue()))
            .andExpect(jsonPath("$.salaryScale").value(DEFAULT_SALARY_SCALE.toString()))
            .andExpect(jsonPath("$.salaryCode").value(DEFAULT_SALARY_CODE.toString()))
            .andExpect(jsonPath("$.monthlySalaryGovtProvided").value(DEFAULT_MONTHLY_SALARY_GOVT_PROVIDED.intValue()))
            .andExpect(jsonPath("$.monthlySalaryInstituteProvided").value(DEFAULT_MONTHLY_SALARY_INSTITUTE_PROVIDED.intValue()))
            .andExpect(jsonPath("$.gbResolutionReceiveDate").value(DEFAULT_GB_RESOLUTION_RECEIVE_DATE.toString()))
            .andExpect(jsonPath("$.gbResolutionAgendaNo").value(DEFAULT_GB_RESOLUTION_AGENDA_NO.toString()))
            .andExpect(jsonPath("$.circularPaperName").value(DEFAULT_CIRCULAR_PAPER_NAME.toString()))
            .andExpect(jsonPath("$.circularPublishedDate").value(DEFAULT_CIRCULAR_PUBLISHED_DATE.toString()))
            .andExpect(jsonPath("$.recruitExamReceiveDate").value(DEFAULT_RECRUIT_EXAM_RECEIVE_DATE.toString()))
            .andExpect(jsonPath("$.dgRepresentativeName").value(DEFAULT_DG_REPRESENTATIVE_NAME.toString()))
            .andExpect(jsonPath("$.dgRepresentativeDesignation").value(DEFAULT_DG_REPRESENTATIVE_DESIGNATION.toString()))
            .andExpect(jsonPath("$.dgRepresentativeAddress").value(DEFAULT_DG_REPRESENTATIVE_ADDRESS.toString()))
            .andExpect(jsonPath("$.boardRepresentativeName").value(DEFAULT_BOARD_REPRESENTATIVE_NAME.toString()))
            .andExpect(jsonPath("$.boardRepresentativeDesignation").value(DEFAULT_BOARD_REPRESENTATIVE_DESIGNATION.toString()))
            .andExpect(jsonPath("$.boardRepresentativeAddress").value(DEFAULT_BOARD_REPRESENTATIVE_ADDRESS.toString()))
            .andExpect(jsonPath("$.recruitApproveGBResolutionDate").value(DEFAULT_RECRUIT_APPROVE_GBRESOLUTION_DATE.toString()))
            .andExpect(jsonPath("$.recruitPermitAgendaNo").value(DEFAULT_RECRUIT_PERMIT_AGENDA_NO.toString()))
            .andExpect(jsonPath("$.recruitmentDate").value(DEFAULT_RECRUITMENT_DATE.toString()))
            .andExpect(jsonPath("$.presentInstituteJoinDate").value(DEFAULT_PRESENT_INSTITUTE_JOIN_DATE.toString()))
            .andExpect(jsonPath("$.presentPostJoinDate").value(DEFAULT_PRESENT_POST_JOIN_DATE.toString()))
            .andExpect(jsonPath("$.dgRepresentativeRecordNo").value(DEFAULT_DG_REPRESENTATIVE_RECORD_NO.toString()))
            .andExpect(jsonPath("$.boardRepresentativeRecordNo").value(DEFAULT_BOARD_REPRESENTATIVE_RECORD_NO.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmplRecruitInfo() throws Exception {
        // Get the instEmplRecruitInfo
        restInstEmplRecruitInfoMockMvc.perform(get("/api/instEmplRecruitInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmplRecruitInfo() throws Exception {
        // Initialize the database
        instEmplRecruitInfoRepository.saveAndFlush(instEmplRecruitInfo);

		int databaseSizeBeforeUpdate = instEmplRecruitInfoRepository.findAll().size();

        // Update the instEmplRecruitInfo
        instEmplRecruitInfo.setSalaryScale(UPDATED_SALARY_SCALE);
        instEmplRecruitInfo.setSalaryCode(UPDATED_SALARY_CODE);
        instEmplRecruitInfo.setMonthlySalaryGovtProvided(UPDATED_MONTHLY_SALARY_GOVT_PROVIDED);
        instEmplRecruitInfo.setMonthlySalaryInstituteProvided(UPDATED_MONTHLY_SALARY_INSTITUTE_PROVIDED);
        instEmplRecruitInfo.setGbResolutionReceiveDate(UPDATED_GB_RESOLUTION_RECEIVE_DATE);
        instEmplRecruitInfo.setGbResolutionAgendaNo(UPDATED_GB_RESOLUTION_AGENDA_NO);
        instEmplRecruitInfo.setCircularPaperName(UPDATED_CIRCULAR_PAPER_NAME);
        instEmplRecruitInfo.setCircularPublishedDate(UPDATED_CIRCULAR_PUBLISHED_DATE);
        instEmplRecruitInfo.setRecruitExamReceiveDate(UPDATED_RECRUIT_EXAM_RECEIVE_DATE);
        instEmplRecruitInfo.setDgRepresentativeName(UPDATED_DG_REPRESENTATIVE_NAME);
        instEmplRecruitInfo.setDgRepresentativeDesignation(UPDATED_DG_REPRESENTATIVE_DESIGNATION);
        instEmplRecruitInfo.setDgRepresentativeAddress(UPDATED_DG_REPRESENTATIVE_ADDRESS);
        instEmplRecruitInfo.setBoardRepresentativeName(UPDATED_BOARD_REPRESENTATIVE_NAME);
        instEmplRecruitInfo.setBoardRepresentativeDesignation(UPDATED_BOARD_REPRESENTATIVE_DESIGNATION);
        instEmplRecruitInfo.setBoardRepresentativeAddress(UPDATED_BOARD_REPRESENTATIVE_ADDRESS);
        instEmplRecruitInfo.setRecruitApproveGBResolutionDate(UPDATED_RECRUIT_APPROVE_GBRESOLUTION_DATE);
        instEmplRecruitInfo.setRecruitPermitAgendaNo(UPDATED_RECRUIT_PERMIT_AGENDA_NO);
        instEmplRecruitInfo.setRecruitmentDate(UPDATED_RECRUITMENT_DATE);
        instEmplRecruitInfo.setPresentInstituteJoinDate(UPDATED_PRESENT_INSTITUTE_JOIN_DATE);
        instEmplRecruitInfo.setPresentPostJoinDate(UPDATED_PRESENT_POST_JOIN_DATE);
        instEmplRecruitInfo.setDgRepresentativeRecordNo(UPDATED_DG_REPRESENTATIVE_RECORD_NO);
        instEmplRecruitInfo.setBoardRepresentativeRecordNo(UPDATED_BOARD_REPRESENTATIVE_RECORD_NO);
        instEmplRecruitInfo.setDepartment(UPDATED_DEPARTMENT);

        restInstEmplRecruitInfoMockMvc.perform(put("/api/instEmplRecruitInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplRecruitInfo)))
                .andExpect(status().isOk());

        // Validate the InstEmplRecruitInfo in the database
        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeUpdate);
        InstEmplRecruitInfo testInstEmplRecruitInfo = instEmplRecruitInfos.get(instEmplRecruitInfos.size() - 1);
        assertThat(testInstEmplRecruitInfo.getSalaryScale()).isEqualTo(UPDATED_SALARY_SCALE);
        assertThat(testInstEmplRecruitInfo.getSalaryCode()).isEqualTo(UPDATED_SALARY_CODE);
        assertThat(testInstEmplRecruitInfo.getMonthlySalaryGovtProvided()).isEqualTo(UPDATED_MONTHLY_SALARY_GOVT_PROVIDED);
        assertThat(testInstEmplRecruitInfo.getMonthlySalaryInstituteProvided()).isEqualTo(UPDATED_MONTHLY_SALARY_INSTITUTE_PROVIDED);
        assertThat(testInstEmplRecruitInfo.getGbResolutionReceiveDate()).isEqualTo(UPDATED_GB_RESOLUTION_RECEIVE_DATE);
        assertThat(testInstEmplRecruitInfo.getGbResolutionAgendaNo()).isEqualTo(UPDATED_GB_RESOLUTION_AGENDA_NO);
        assertThat(testInstEmplRecruitInfo.getCircularPaperName()).isEqualTo(UPDATED_CIRCULAR_PAPER_NAME);
        assertThat(testInstEmplRecruitInfo.getCircularPublishedDate()).isEqualTo(UPDATED_CIRCULAR_PUBLISHED_DATE);
        assertThat(testInstEmplRecruitInfo.getRecruitExamReceiveDate()).isEqualTo(UPDATED_RECRUIT_EXAM_RECEIVE_DATE);
        assertThat(testInstEmplRecruitInfo.getDgRepresentativeName()).isEqualTo(UPDATED_DG_REPRESENTATIVE_NAME);
        assertThat(testInstEmplRecruitInfo.getDgRepresentativeDesignation()).isEqualTo(UPDATED_DG_REPRESENTATIVE_DESIGNATION);
        assertThat(testInstEmplRecruitInfo.getDgRepresentativeAddress()).isEqualTo(UPDATED_DG_REPRESENTATIVE_ADDRESS);
        assertThat(testInstEmplRecruitInfo.getBoardRepresentativeName()).isEqualTo(UPDATED_BOARD_REPRESENTATIVE_NAME);
        assertThat(testInstEmplRecruitInfo.getBoardRepresentativeDesignation()).isEqualTo(UPDATED_BOARD_REPRESENTATIVE_DESIGNATION);
        assertThat(testInstEmplRecruitInfo.getBoardRepresentativeAddress()).isEqualTo(UPDATED_BOARD_REPRESENTATIVE_ADDRESS);
        assertThat(testInstEmplRecruitInfo.getRecruitApproveGBResolutionDate()).isEqualTo(UPDATED_RECRUIT_APPROVE_GBRESOLUTION_DATE);
        assertThat(testInstEmplRecruitInfo.getRecruitPermitAgendaNo()).isEqualTo(UPDATED_RECRUIT_PERMIT_AGENDA_NO);
        assertThat(testInstEmplRecruitInfo.getRecruitmentDate()).isEqualTo(UPDATED_RECRUITMENT_DATE);
        assertThat(testInstEmplRecruitInfo.getPresentInstituteJoinDate()).isEqualTo(UPDATED_PRESENT_INSTITUTE_JOIN_DATE);
        assertThat(testInstEmplRecruitInfo.getPresentPostJoinDate()).isEqualTo(UPDATED_PRESENT_POST_JOIN_DATE);
        assertThat(testInstEmplRecruitInfo.getDgRepresentativeRecordNo()).isEqualTo(UPDATED_DG_REPRESENTATIVE_RECORD_NO);
        assertThat(testInstEmplRecruitInfo.getBoardRepresentativeRecordNo()).isEqualTo(UPDATED_BOARD_REPRESENTATIVE_RECORD_NO);
        assertThat(testInstEmplRecruitInfo.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    public void deleteInstEmplRecruitInfo() throws Exception {
        // Initialize the database
        instEmplRecruitInfoRepository.saveAndFlush(instEmplRecruitInfo);

		int databaseSizeBeforeDelete = instEmplRecruitInfoRepository.findAll().size();

        // Get the instEmplRecruitInfo
        restInstEmplRecruitInfoMockMvc.perform(delete("/api/instEmplRecruitInfos/{id}", instEmplRecruitInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmplRecruitInfo> instEmplRecruitInfos = instEmplRecruitInfoRepository.findAll();
        assertThat(instEmplRecruitInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
