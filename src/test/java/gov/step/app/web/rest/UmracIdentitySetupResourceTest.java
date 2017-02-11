package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.UmracIdentitySetup;
import gov.step.app.repository.UmracIdentitySetupRepository;
import gov.step.app.repository.search.UmracIdentitySetupSearchRepository;

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
 * Test class for the UmracIdentitySetupResource REST controller.
 *
 * @see UmracIdentitySetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UmracIdentitySetupResourceTest {

    private static final String DEFAULT_EMP_ID = "AAAAA";
    private static final String UPDATED_EMP_ID = "BBBBB";
    private static final String DEFAULT_USER_NAME = "AAAAA";
    private static final String UPDATED_USER_NAME = "BBBBB";
    private static final String DEFAULT_U_PW = "AAAAA";
    private static final String UPDATED_U_PW = "BBBBB";
    private static final String DEFAULT_CONFM_PW = "AAAAA";
    private static final String UPDATED_CONFM_PW = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final LocalDate DEFAULT_UPDATED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private UmracIdentitySetupRepository umracIdentitySetupRepository;

    @Inject
    private UmracIdentitySetupSearchRepository umracIdentitySetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmracIdentitySetupMockMvc;

    private UmracIdentitySetup umracIdentitySetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmracIdentitySetupResource umracIdentitySetupResource = new UmracIdentitySetupResource();
        ReflectionTestUtils.setField(umracIdentitySetupResource, "umracIdentitySetupRepository", umracIdentitySetupRepository);
        ReflectionTestUtils.setField(umracIdentitySetupResource, "umracIdentitySetupSearchRepository", umracIdentitySetupSearchRepository);
        this.restUmracIdentitySetupMockMvc = MockMvcBuilders.standaloneSetup(umracIdentitySetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umracIdentitySetup = new UmracIdentitySetup();
        umracIdentitySetup.setEmpId(DEFAULT_EMP_ID);
        umracIdentitySetup.setUserName(DEFAULT_USER_NAME);
        umracIdentitySetup.setuPw(DEFAULT_U_PW);
        umracIdentitySetup.setConfm_Pw(DEFAULT_CONFM_PW);
        umracIdentitySetup.setStatus(DEFAULT_STATUS);
        umracIdentitySetup.setCreateDate(DEFAULT_CREATE_DATE);
        umracIdentitySetup.setCreateBy(DEFAULT_CREATE_BY);
        umracIdentitySetup.setUpdatedBy(DEFAULT_UPDATED_BY);
        umracIdentitySetup.setUpdatedTime(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createUmracIdentitySetup() throws Exception {
        int databaseSizeBeforeCreate = umracIdentitySetupRepository.findAll().size();

        // Create the UmracIdentitySetup

        restUmracIdentitySetupMockMvc.perform(post("/api/umracIdentitySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracIdentitySetup)))
                .andExpect(status().isCreated());

        // Validate the UmracIdentitySetup in the database
        List<UmracIdentitySetup> umracIdentitySetups = umracIdentitySetupRepository.findAll();
        assertThat(umracIdentitySetups).hasSize(databaseSizeBeforeCreate + 1);
        UmracIdentitySetup testUmracIdentitySetup = umracIdentitySetups.get(umracIdentitySetups.size() - 1);
        assertThat(testUmracIdentitySetup.getEmpId()).isEqualTo(DEFAULT_EMP_ID);
        assertThat(testUmracIdentitySetup.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUmracIdentitySetup.getuPw()).isEqualTo(DEFAULT_U_PW);
        assertThat(testUmracIdentitySetup.getConfm_Pw()).isEqualTo(DEFAULT_CONFM_PW);
        assertThat(testUmracIdentitySetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUmracIdentitySetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testUmracIdentitySetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testUmracIdentitySetup.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUmracIdentitySetup.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = umracIdentitySetupRepository.findAll().size();
        // set the field null
        umracIdentitySetup.setUserName(null);

        // Create the UmracIdentitySetup, which fails.

        restUmracIdentitySetupMockMvc.perform(post("/api/umracIdentitySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracIdentitySetup)))
                .andExpect(status().isBadRequest());

        List<UmracIdentitySetup> umracIdentitySetups = umracIdentitySetupRepository.findAll();
        assertThat(umracIdentitySetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkuPwIsRequired() throws Exception {
        int databaseSizeBeforeTest = umracIdentitySetupRepository.findAll().size();
        // set the field null
        umracIdentitySetup.setuPw(null);

        // Create the UmracIdentitySetup, which fails.

        restUmracIdentitySetupMockMvc.perform(post("/api/umracIdentitySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracIdentitySetup)))
                .andExpect(status().isBadRequest());

        List<UmracIdentitySetup> umracIdentitySetups = umracIdentitySetupRepository.findAll();
        assertThat(umracIdentitySetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUmracIdentitySetups() throws Exception {
        // Initialize the database
        umracIdentitySetupRepository.saveAndFlush(umracIdentitySetup);

        // Get all the umracIdentitySetups
        restUmracIdentitySetupMockMvc.perform(get("/api/umracIdentitySetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umracIdentitySetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].empId").value(hasItem(DEFAULT_EMP_ID.toString())))
                .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
                .andExpect(jsonPath("$.[*].uPw").value(hasItem(DEFAULT_U_PW.toString())))
                .andExpect(jsonPath("$.[*].confm_Pw").value(hasItem(DEFAULT_CONFM_PW.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getUmracIdentitySetup() throws Exception {
        // Initialize the database
        umracIdentitySetupRepository.saveAndFlush(umracIdentitySetup);

        // Get the umracIdentitySetup
        restUmracIdentitySetupMockMvc.perform(get("/api/umracIdentitySetups/{id}", umracIdentitySetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umracIdentitySetup.getId().intValue()))
            .andExpect(jsonPath("$.empId").value(DEFAULT_EMP_ID.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.uPw").value(DEFAULT_U_PW.toString()))
            .andExpect(jsonPath("$.confm_Pw").value(DEFAULT_CONFM_PW.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUmracIdentitySetup() throws Exception {
        // Get the umracIdentitySetup
        restUmracIdentitySetupMockMvc.perform(get("/api/umracIdentitySetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmracIdentitySetup() throws Exception {
        // Initialize the database
        umracIdentitySetupRepository.saveAndFlush(umracIdentitySetup);

		int databaseSizeBeforeUpdate = umracIdentitySetupRepository.findAll().size();

        // Update the umracIdentitySetup
        umracIdentitySetup.setEmpId(UPDATED_EMP_ID);
        umracIdentitySetup.setUserName(UPDATED_USER_NAME);
        umracIdentitySetup.setuPw(UPDATED_U_PW);
        umracIdentitySetup.setConfm_Pw(UPDATED_CONFM_PW);
        umracIdentitySetup.setStatus(UPDATED_STATUS);
        umracIdentitySetup.setCreateDate(UPDATED_CREATE_DATE);
        umracIdentitySetup.setCreateBy(UPDATED_CREATE_BY);
        umracIdentitySetup.setUpdatedBy(UPDATED_UPDATED_BY);
        umracIdentitySetup.setUpdatedTime(UPDATED_UPDATED_TIME);

        restUmracIdentitySetupMockMvc.perform(put("/api/umracIdentitySetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracIdentitySetup)))
                .andExpect(status().isOk());

        // Validate the UmracIdentitySetup in the database
        List<UmracIdentitySetup> umracIdentitySetups = umracIdentitySetupRepository.findAll();
        assertThat(umracIdentitySetups).hasSize(databaseSizeBeforeUpdate);
        UmracIdentitySetup testUmracIdentitySetup = umracIdentitySetups.get(umracIdentitySetups.size() - 1);
        assertThat(testUmracIdentitySetup.getEmpId()).isEqualTo(UPDATED_EMP_ID);
        assertThat(testUmracIdentitySetup.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUmracIdentitySetup.getuPw()).isEqualTo(UPDATED_U_PW);
        assertThat(testUmracIdentitySetup.getConfm_Pw()).isEqualTo(UPDATED_CONFM_PW);
        assertThat(testUmracIdentitySetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUmracIdentitySetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testUmracIdentitySetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testUmracIdentitySetup.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUmracIdentitySetup.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteUmracIdentitySetup() throws Exception {
        // Initialize the database
        umracIdentitySetupRepository.saveAndFlush(umracIdentitySetup);

		int databaseSizeBeforeDelete = umracIdentitySetupRepository.findAll().size();

        // Get the umracIdentitySetup
        restUmracIdentitySetupMockMvc.perform(delete("/api/umracIdentitySetups/{id}", umracIdentitySetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmracIdentitySetup> umracIdentitySetups = umracIdentitySetupRepository.findAll();
        assertThat(umracIdentitySetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
