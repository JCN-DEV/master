package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstAdmInfoTemp;
import gov.step.app.repository.InstAdmInfoTempRepository;
import gov.step.app.repository.search.InstAdmInfoTempSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the InstAdmInfoTempResource REST controller.
 *
 * @see InstAdmInfoTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstAdmInfoTempResourceTest {

    private static final String DEFAULT_ADMIN_COUNSELOR_NAME = "AAAAA";
    private static final String UPDATED_ADMIN_COUNSELOR_NAME = "BBBBB";
    private static final String DEFAULT_COUNSELOR_MOBILE_NO = "AAAAA";
    private static final String UPDATED_COUNSELOR_MOBILE_NO = "BBBBB";
    private static final String DEFAULT_INS_HEAD_NAME = "AAAAA";
    private static final String UPDATED_INS_HEAD_NAME = "BBBBB";
    private static final String DEFAULT_INS_HEAD_MOBILE_NO = "AAAAA";
    private static final String UPDATED_INS_HEAD_MOBILE_NO = "BBBBB";
    private static final String DEFAULT_DEO_NAME = "AAAAA";
    private static final String UPDATED_DEO_NAME = "BBBBB";
    private static final String DEFAULT_DEO_MOBILE_NO = "AAAAA";
    private static final String UPDATED_DEO_MOBILE_NO = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstAdmInfoTempRepository instAdmInfoTempRepository;

    @Inject
    private InstAdmInfoTempSearchRepository instAdmInfoTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstAdmInfoTempMockMvc;

    private InstAdmInfoTemp instAdmInfoTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstAdmInfoTempResource instAdmInfoTempResource = new InstAdmInfoTempResource();
        ReflectionTestUtils.setField(instAdmInfoTempResource, "instAdmInfoTempRepository", instAdmInfoTempRepository);
        ReflectionTestUtils.setField(instAdmInfoTempResource, "instAdmInfoTempSearchRepository", instAdmInfoTempSearchRepository);
        this.restInstAdmInfoTempMockMvc = MockMvcBuilders.standaloneSetup(instAdmInfoTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instAdmInfoTemp = new InstAdmInfoTemp();
        instAdmInfoTemp.setAdminCounselorName(DEFAULT_ADMIN_COUNSELOR_NAME);
        instAdmInfoTemp.setCounselorMobileNo(DEFAULT_COUNSELOR_MOBILE_NO);
        instAdmInfoTemp.setInsHeadName(DEFAULT_INS_HEAD_NAME);
        instAdmInfoTemp.setInsHeadMobileNo(DEFAULT_INS_HEAD_MOBILE_NO);
        instAdmInfoTemp.setDeoName(DEFAULT_DEO_NAME);
        instAdmInfoTemp.setDeoMobileNo(DEFAULT_DEO_MOBILE_NO);
        instAdmInfoTemp.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstAdmInfoTemp() throws Exception {
        int databaseSizeBeforeCreate = instAdmInfoTempRepository.findAll().size();

        // Create the InstAdmInfoTemp

        restInstAdmInfoTempMockMvc.perform(post("/api/instAdmInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfoTemp)))
                .andExpect(status().isCreated());

        // Validate the InstAdmInfoTemp in the database
        List<InstAdmInfoTemp> instAdmInfoTemps = instAdmInfoTempRepository.findAll();
        assertThat(instAdmInfoTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstAdmInfoTemp testInstAdmInfoTemp = instAdmInfoTemps.get(instAdmInfoTemps.size() - 1);
        assertThat(testInstAdmInfoTemp.getAdminCounselorName()).isEqualTo(DEFAULT_ADMIN_COUNSELOR_NAME);
        assertThat(testInstAdmInfoTemp.getCounselorMobileNo()).isEqualTo(DEFAULT_COUNSELOR_MOBILE_NO);
        assertThat(testInstAdmInfoTemp.getInsHeadName()).isEqualTo(DEFAULT_INS_HEAD_NAME);
        assertThat(testInstAdmInfoTemp.getInsHeadMobileNo()).isEqualTo(DEFAULT_INS_HEAD_MOBILE_NO);
        assertThat(testInstAdmInfoTemp.getDeoName()).isEqualTo(DEFAULT_DEO_NAME);
        assertThat(testInstAdmInfoTemp.getDeoMobileNo()).isEqualTo(DEFAULT_DEO_MOBILE_NO);
        assertThat(testInstAdmInfoTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkAdminCounselorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instAdmInfoTempRepository.findAll().size();
        // set the field null
        instAdmInfoTemp.setAdminCounselorName(null);

        // Create the InstAdmInfoTemp, which fails.

        restInstAdmInfoTempMockMvc.perform(post("/api/instAdmInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfoTemp)))
                .andExpect(status().isBadRequest());

        List<InstAdmInfoTemp> instAdmInfoTemps = instAdmInfoTempRepository.findAll();
        assertThat(instAdmInfoTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInsHeadNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instAdmInfoTempRepository.findAll().size();
        // set the field null
        instAdmInfoTemp.setInsHeadName(null);

        // Create the InstAdmInfoTemp, which fails.

        restInstAdmInfoTempMockMvc.perform(post("/api/instAdmInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfoTemp)))
                .andExpect(status().isBadRequest());

        List<InstAdmInfoTemp> instAdmInfoTemps = instAdmInfoTempRepository.findAll();
        assertThat(instAdmInfoTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInsHeadMobileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instAdmInfoTempRepository.findAll().size();
        // set the field null
        instAdmInfoTemp.setInsHeadMobileNo(null);

        // Create the InstAdmInfoTemp, which fails.

        restInstAdmInfoTempMockMvc.perform(post("/api/instAdmInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfoTemp)))
                .andExpect(status().isBadRequest());

        List<InstAdmInfoTemp> instAdmInfoTemps = instAdmInfoTempRepository.findAll();
        assertThat(instAdmInfoTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeoNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instAdmInfoTempRepository.findAll().size();
        // set the field null
        instAdmInfoTemp.setDeoName(null);

        // Create the InstAdmInfoTemp, which fails.

        restInstAdmInfoTempMockMvc.perform(post("/api/instAdmInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfoTemp)))
                .andExpect(status().isBadRequest());

        List<InstAdmInfoTemp> instAdmInfoTemps = instAdmInfoTempRepository.findAll();
        assertThat(instAdmInfoTemps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstAdmInfoTemps() throws Exception {
        // Initialize the database
        instAdmInfoTempRepository.saveAndFlush(instAdmInfoTemp);

        // Get all the instAdmInfoTemps
        restInstAdmInfoTempMockMvc.perform(get("/api/instAdmInfoTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instAdmInfoTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].adminCounselorName").value(hasItem(DEFAULT_ADMIN_COUNSELOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].counselorMobileNo").value(hasItem(DEFAULT_COUNSELOR_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].insHeadName").value(hasItem(DEFAULT_INS_HEAD_NAME.toString())))
                .andExpect(jsonPath("$.[*].insHeadMobileNo").value(hasItem(DEFAULT_INS_HEAD_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].deoName").value(hasItem(DEFAULT_DEO_NAME.toString())))
                .andExpect(jsonPath("$.[*].deoMobileNo").value(hasItem(DEFAULT_DEO_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstAdmInfoTemp() throws Exception {
        // Initialize the database
        instAdmInfoTempRepository.saveAndFlush(instAdmInfoTemp);

        // Get the instAdmInfoTemp
        restInstAdmInfoTempMockMvc.perform(get("/api/instAdmInfoTemps/{id}", instAdmInfoTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instAdmInfoTemp.getId().intValue()))
            .andExpect(jsonPath("$.adminCounselorName").value(DEFAULT_ADMIN_COUNSELOR_NAME.toString()))
            .andExpect(jsonPath("$.counselorMobileNo").value(DEFAULT_COUNSELOR_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.insHeadName").value(DEFAULT_INS_HEAD_NAME.toString()))
            .andExpect(jsonPath("$.insHeadMobileNo").value(DEFAULT_INS_HEAD_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.deoName").value(DEFAULT_DEO_NAME.toString()))
            .andExpect(jsonPath("$.deoMobileNo").value(DEFAULT_DEO_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstAdmInfoTemp() throws Exception {
        // Get the instAdmInfoTemp
        restInstAdmInfoTempMockMvc.perform(get("/api/instAdmInfoTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstAdmInfoTemp() throws Exception {
        // Initialize the database
        instAdmInfoTempRepository.saveAndFlush(instAdmInfoTemp);

		int databaseSizeBeforeUpdate = instAdmInfoTempRepository.findAll().size();

        // Update the instAdmInfoTemp
        instAdmInfoTemp.setAdminCounselorName(UPDATED_ADMIN_COUNSELOR_NAME);
        instAdmInfoTemp.setCounselorMobileNo(UPDATED_COUNSELOR_MOBILE_NO);
        instAdmInfoTemp.setInsHeadName(UPDATED_INS_HEAD_NAME);
        instAdmInfoTemp.setInsHeadMobileNo(UPDATED_INS_HEAD_MOBILE_NO);
        instAdmInfoTemp.setDeoName(UPDATED_DEO_NAME);
        instAdmInfoTemp.setDeoMobileNo(UPDATED_DEO_MOBILE_NO);
        instAdmInfoTemp.setStatus(UPDATED_STATUS);

        restInstAdmInfoTempMockMvc.perform(put("/api/instAdmInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfoTemp)))
                .andExpect(status().isOk());

        // Validate the InstAdmInfoTemp in the database
        List<InstAdmInfoTemp> instAdmInfoTemps = instAdmInfoTempRepository.findAll();
        assertThat(instAdmInfoTemps).hasSize(databaseSizeBeforeUpdate);
        InstAdmInfoTemp testInstAdmInfoTemp = instAdmInfoTemps.get(instAdmInfoTemps.size() - 1);
        assertThat(testInstAdmInfoTemp.getAdminCounselorName()).isEqualTo(UPDATED_ADMIN_COUNSELOR_NAME);
        assertThat(testInstAdmInfoTemp.getCounselorMobileNo()).isEqualTo(UPDATED_COUNSELOR_MOBILE_NO);
        assertThat(testInstAdmInfoTemp.getInsHeadName()).isEqualTo(UPDATED_INS_HEAD_NAME);
        assertThat(testInstAdmInfoTemp.getInsHeadMobileNo()).isEqualTo(UPDATED_INS_HEAD_MOBILE_NO);
        assertThat(testInstAdmInfoTemp.getDeoName()).isEqualTo(UPDATED_DEO_NAME);
        assertThat(testInstAdmInfoTemp.getDeoMobileNo()).isEqualTo(UPDATED_DEO_MOBILE_NO);
        assertThat(testInstAdmInfoTemp.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstAdmInfoTemp() throws Exception {
        // Initialize the database
        instAdmInfoTempRepository.saveAndFlush(instAdmInfoTemp);

		int databaseSizeBeforeDelete = instAdmInfoTempRepository.findAll().size();

        // Get the instAdmInfoTemp
        restInstAdmInfoTempMockMvc.perform(delete("/api/instAdmInfoTemps/{id}", instAdmInfoTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstAdmInfoTemp> instAdmInfoTemps = instAdmInfoTempRepository.findAll();
        assertThat(instAdmInfoTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
