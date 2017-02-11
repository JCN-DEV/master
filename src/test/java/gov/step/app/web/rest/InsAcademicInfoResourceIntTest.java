package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InsAcademicInfo;
import gov.step.app.repository.InsAcademicInfoRepository;
import gov.step.app.repository.search.InsAcademicInfoSearchRepository;

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

import gov.step.app.domain.enumeration.Curriculum;

/**
 * Test class for the InsAcademicInfoResource REST controller.
 *
 * @see InsAcademicInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InsAcademicInfoResourceIntTest {

    private static final String DEFAULT_COUNSELOR_NAME = "AAAAA";
    private static final String UPDATED_COUNSELOR_NAME = "BBBBB";


private static final Curriculum DEFAULT_CURRICULUM = Curriculum.SSC_VOC;
    private static final Curriculum UPDATED_CURRICULUM = Curriculum.HSC_VOC;

    private static final Integer DEFAULT_TOTAL_TECH_TRADE_NO = 1;
    private static final Integer UPDATED_TOTAL_TECH_TRADE_NO = 2;
    private static final String DEFAULT_TRADE_TECH_DETAILS = "AAAAA";
    private static final String UPDATED_TRADE_TECH_DETAILS = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InsAcademicInfoRepository insAcademicInfoRepository;

    @Inject
    private InsAcademicInfoSearchRepository insAcademicInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInsAcademicInfoMockMvc;

    private InsAcademicInfo insAcademicInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InsAcademicInfoResource insAcademicInfoResource = new InsAcademicInfoResource();
        ReflectionTestUtils.setField(insAcademicInfoResource, "insAcademicInfoRepository", insAcademicInfoRepository);
        ReflectionTestUtils.setField(insAcademicInfoResource, "insAcademicInfoSearchRepository", insAcademicInfoSearchRepository);
        this.restInsAcademicInfoMockMvc = MockMvcBuilders.standaloneSetup(insAcademicInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        insAcademicInfo = new InsAcademicInfo();
        insAcademicInfo.setCounselorName(DEFAULT_COUNSELOR_NAME);
        insAcademicInfo.setCurriculum(DEFAULT_CURRICULUM);
        insAcademicInfo.setTotalTechTradeNo(DEFAULT_TOTAL_TECH_TRADE_NO);
        insAcademicInfo.setTradeTechDetails(DEFAULT_TRADE_TECH_DETAILS);
        insAcademicInfo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInsAcademicInfo() throws Exception {
        int databaseSizeBeforeCreate = insAcademicInfoRepository.findAll().size();

        // Create the InsAcademicInfo

        restInsAcademicInfoMockMvc.perform(post("/api/insAcademicInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(insAcademicInfo)))
                .andExpect(status().isCreated());

        // Validate the InsAcademicInfo in the database
        List<InsAcademicInfo> insAcademicInfos = insAcademicInfoRepository.findAll();
        assertThat(insAcademicInfos).hasSize(databaseSizeBeforeCreate + 1);
        InsAcademicInfo testInsAcademicInfo = insAcademicInfos.get(insAcademicInfos.size() - 1);
        assertThat(testInsAcademicInfo.getCounselorName()).isEqualTo(DEFAULT_COUNSELOR_NAME);
        assertThat(testInsAcademicInfo.getCurriculum()).isEqualTo(DEFAULT_CURRICULUM);
        assertThat(testInsAcademicInfo.getTotalTechTradeNo()).isEqualTo(DEFAULT_TOTAL_TECH_TRADE_NO);
        assertThat(testInsAcademicInfo.getTradeTechDetails()).isEqualTo(DEFAULT_TRADE_TECH_DETAILS);
        assertThat(testInsAcademicInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInsAcademicInfos() throws Exception {
        // Initialize the database
        insAcademicInfoRepository.saveAndFlush(insAcademicInfo);

        // Get all the insAcademicInfos
        restInsAcademicInfoMockMvc.perform(get("/api/insAcademicInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(insAcademicInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].counselorName").value(hasItem(DEFAULT_COUNSELOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].curriculum").value(hasItem(DEFAULT_CURRICULUM.toString())))
                .andExpect(jsonPath("$.[*].totalTechTradeNo").value(hasItem(DEFAULT_TOTAL_TECH_TRADE_NO)))
                .andExpect(jsonPath("$.[*].tradeTechDetails").value(hasItem(DEFAULT_TRADE_TECH_DETAILS.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInsAcademicInfo() throws Exception {
        // Initialize the database
        insAcademicInfoRepository.saveAndFlush(insAcademicInfo);

        // Get the insAcademicInfo
        restInsAcademicInfoMockMvc.perform(get("/api/insAcademicInfos/{id}", insAcademicInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(insAcademicInfo.getId().intValue()))
            .andExpect(jsonPath("$.counselorName").value(DEFAULT_COUNSELOR_NAME.toString()))
            .andExpect(jsonPath("$.curriculum").value(DEFAULT_CURRICULUM.toString()))
            .andExpect(jsonPath("$.totalTechTradeNo").value(DEFAULT_TOTAL_TECH_TRADE_NO))
            .andExpect(jsonPath("$.tradeTechDetails").value(DEFAULT_TRADE_TECH_DETAILS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInsAcademicInfo() throws Exception {
        // Get the insAcademicInfo
        restInsAcademicInfoMockMvc.perform(get("/api/insAcademicInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsAcademicInfo() throws Exception {
        // Initialize the database
        insAcademicInfoRepository.saveAndFlush(insAcademicInfo);

		int databaseSizeBeforeUpdate = insAcademicInfoRepository.findAll().size();

        // Update the insAcademicInfo
        insAcademicInfo.setCounselorName(UPDATED_COUNSELOR_NAME);
        insAcademicInfo.setCurriculum(UPDATED_CURRICULUM);
        insAcademicInfo.setTotalTechTradeNo(UPDATED_TOTAL_TECH_TRADE_NO);
        insAcademicInfo.setTradeTechDetails(UPDATED_TRADE_TECH_DETAILS);
        insAcademicInfo.setStatus(UPDATED_STATUS);

        restInsAcademicInfoMockMvc.perform(put("/api/insAcademicInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(insAcademicInfo)))
                .andExpect(status().isOk());

        // Validate the InsAcademicInfo in the database
        List<InsAcademicInfo> insAcademicInfos = insAcademicInfoRepository.findAll();
        assertThat(insAcademicInfos).hasSize(databaseSizeBeforeUpdate);
        InsAcademicInfo testInsAcademicInfo = insAcademicInfos.get(insAcademicInfos.size() - 1);
        assertThat(testInsAcademicInfo.getCounselorName()).isEqualTo(UPDATED_COUNSELOR_NAME);
        assertThat(testInsAcademicInfo.getCurriculum()).isEqualTo(UPDATED_CURRICULUM);
        assertThat(testInsAcademicInfo.getTotalTechTradeNo()).isEqualTo(UPDATED_TOTAL_TECH_TRADE_NO);
        assertThat(testInsAcademicInfo.getTradeTechDetails()).isEqualTo(UPDATED_TRADE_TECH_DETAILS);
        assertThat(testInsAcademicInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInsAcademicInfo() throws Exception {
        // Initialize the database
        insAcademicInfoRepository.saveAndFlush(insAcademicInfo);

		int databaseSizeBeforeDelete = insAcademicInfoRepository.findAll().size();

        // Get the insAcademicInfo
        restInsAcademicInfoMockMvc.perform(delete("/api/insAcademicInfos/{id}", insAcademicInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InsAcademicInfo> insAcademicInfos = insAcademicInfoRepository.findAll();
        assertThat(insAcademicInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
