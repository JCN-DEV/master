package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstAdmInfo;
import gov.step.app.repository.InstAdmInfoRepository;
import gov.step.app.repository.search.InstAdmInfoSearchRepository;

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
 * Test class for the InstAdmInfoResource REST controller.
 *
 * @see InstAdmInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstAdmInfoResourceIntTest {

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
    private InstAdmInfoRepository instAdmInfoRepository;

    @Inject
    private InstAdmInfoSearchRepository instAdmInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstAdmInfoMockMvc;

    private InstAdmInfo instAdmInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstAdmInfoResource instAdmInfoResource = new InstAdmInfoResource();
        ReflectionTestUtils.setField(instAdmInfoResource, "instAdmInfoRepository", instAdmInfoRepository);
        ReflectionTestUtils.setField(instAdmInfoResource, "instAdmInfoSearchRepository", instAdmInfoSearchRepository);
        this.restInstAdmInfoMockMvc = MockMvcBuilders.standaloneSetup(instAdmInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instAdmInfo = new InstAdmInfo();
        instAdmInfo.setAdminCounselorName(DEFAULT_ADMIN_COUNSELOR_NAME);
        instAdmInfo.setCounselorMobileNo(DEFAULT_COUNSELOR_MOBILE_NO);
        instAdmInfo.setInsHeadName(DEFAULT_INS_HEAD_NAME);
        instAdmInfo.setInsHeadMobileNo(DEFAULT_INS_HEAD_MOBILE_NO);
        instAdmInfo.setDeoName(DEFAULT_DEO_NAME);
        instAdmInfo.setDeoMobileNo(DEFAULT_DEO_MOBILE_NO);
        instAdmInfo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstAdmInfo() throws Exception {
        int databaseSizeBeforeCreate = instAdmInfoRepository.findAll().size();

        // Create the InstAdmInfo

        restInstAdmInfoMockMvc.perform(post("/api/instAdmInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfo)))
                .andExpect(status().isCreated());

        // Validate the InstAdmInfo in the database
        List<InstAdmInfo> instAdmInfos = instAdmInfoRepository.findAll();
        assertThat(instAdmInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstAdmInfo testInstAdmInfo = instAdmInfos.get(instAdmInfos.size() - 1);
        assertThat(testInstAdmInfo.getAdminCounselorName()).isEqualTo(DEFAULT_ADMIN_COUNSELOR_NAME);
        assertThat(testInstAdmInfo.getCounselorMobileNo()).isEqualTo(DEFAULT_COUNSELOR_MOBILE_NO);
        assertThat(testInstAdmInfo.getInsHeadName()).isEqualTo(DEFAULT_INS_HEAD_NAME);
        assertThat(testInstAdmInfo.getInsHeadMobileNo()).isEqualTo(DEFAULT_INS_HEAD_MOBILE_NO);
        assertThat(testInstAdmInfo.getDeoName()).isEqualTo(DEFAULT_DEO_NAME);
        assertThat(testInstAdmInfo.getDeoMobileNo()).isEqualTo(DEFAULT_DEO_MOBILE_NO);
        assertThat(testInstAdmInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkAdminCounselorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instAdmInfoRepository.findAll().size();
        // set the field null
        instAdmInfo.setAdminCounselorName(null);

        // Create the InstAdmInfo, which fails.

        restInstAdmInfoMockMvc.perform(post("/api/instAdmInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfo)))
                .andExpect(status().isBadRequest());

        List<InstAdmInfo> instAdmInfos = instAdmInfoRepository.findAll();
        assertThat(instAdmInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInsHeadNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instAdmInfoRepository.findAll().size();
        // set the field null
        instAdmInfo.setInsHeadName(null);

        // Create the InstAdmInfo, which fails.

        restInstAdmInfoMockMvc.perform(post("/api/instAdmInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfo)))
                .andExpect(status().isBadRequest());

        List<InstAdmInfo> instAdmInfos = instAdmInfoRepository.findAll();
        assertThat(instAdmInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInsHeadMobileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instAdmInfoRepository.findAll().size();
        // set the field null
        instAdmInfo.setInsHeadMobileNo(null);

        // Create the InstAdmInfo, which fails.

        restInstAdmInfoMockMvc.perform(post("/api/instAdmInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfo)))
                .andExpect(status().isBadRequest());

        List<InstAdmInfo> instAdmInfos = instAdmInfoRepository.findAll();
        assertThat(instAdmInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeoNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instAdmInfoRepository.findAll().size();
        // set the field null
        instAdmInfo.setDeoName(null);

        // Create the InstAdmInfo, which fails.

        restInstAdmInfoMockMvc.perform(post("/api/instAdmInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfo)))
                .andExpect(status().isBadRequest());

        List<InstAdmInfo> instAdmInfos = instAdmInfoRepository.findAll();
        assertThat(instAdmInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstAdmInfos() throws Exception {
        // Initialize the database
        instAdmInfoRepository.saveAndFlush(instAdmInfo);

        // Get all the instAdmInfos
        restInstAdmInfoMockMvc.perform(get("/api/instAdmInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instAdmInfo.getId().intValue())))
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
    public void getInstAdmInfo() throws Exception {
        // Initialize the database
        instAdmInfoRepository.saveAndFlush(instAdmInfo);

        // Get the instAdmInfo
        restInstAdmInfoMockMvc.perform(get("/api/instAdmInfos/{id}", instAdmInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instAdmInfo.getId().intValue()))
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
    public void getNonExistingInstAdmInfo() throws Exception {
        // Get the instAdmInfo
        restInstAdmInfoMockMvc.perform(get("/api/instAdmInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstAdmInfo() throws Exception {
        // Initialize the database
        instAdmInfoRepository.saveAndFlush(instAdmInfo);

		int databaseSizeBeforeUpdate = instAdmInfoRepository.findAll().size();

        // Update the instAdmInfo
        instAdmInfo.setAdminCounselorName(UPDATED_ADMIN_COUNSELOR_NAME);
        instAdmInfo.setCounselorMobileNo(UPDATED_COUNSELOR_MOBILE_NO);
        instAdmInfo.setInsHeadName(UPDATED_INS_HEAD_NAME);
        instAdmInfo.setInsHeadMobileNo(UPDATED_INS_HEAD_MOBILE_NO);
        instAdmInfo.setDeoName(UPDATED_DEO_NAME);
        instAdmInfo.setDeoMobileNo(UPDATED_DEO_MOBILE_NO);
        instAdmInfo.setStatus(UPDATED_STATUS);

        restInstAdmInfoMockMvc.perform(put("/api/instAdmInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAdmInfo)))
                .andExpect(status().isOk());

        // Validate the InstAdmInfo in the database
        List<InstAdmInfo> instAdmInfos = instAdmInfoRepository.findAll();
        assertThat(instAdmInfos).hasSize(databaseSizeBeforeUpdate);
        InstAdmInfo testInstAdmInfo = instAdmInfos.get(instAdmInfos.size() - 1);
        assertThat(testInstAdmInfo.getAdminCounselorName()).isEqualTo(UPDATED_ADMIN_COUNSELOR_NAME);
        assertThat(testInstAdmInfo.getCounselorMobileNo()).isEqualTo(UPDATED_COUNSELOR_MOBILE_NO);
        assertThat(testInstAdmInfo.getInsHeadName()).isEqualTo(UPDATED_INS_HEAD_NAME);
        assertThat(testInstAdmInfo.getInsHeadMobileNo()).isEqualTo(UPDATED_INS_HEAD_MOBILE_NO);
        assertThat(testInstAdmInfo.getDeoName()).isEqualTo(UPDATED_DEO_NAME);
        assertThat(testInstAdmInfo.getDeoMobileNo()).isEqualTo(UPDATED_DEO_MOBILE_NO);
        assertThat(testInstAdmInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstAdmInfo() throws Exception {
        // Initialize the database
        instAdmInfoRepository.saveAndFlush(instAdmInfo);

		int databaseSizeBeforeDelete = instAdmInfoRepository.findAll().size();

        // Get the instAdmInfo
        restInstAdmInfoMockMvc.perform(delete("/api/instAdmInfos/{id}", instAdmInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstAdmInfo> instAdmInfos = instAdmInfoRepository.findAll();
        assertThat(instAdmInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
