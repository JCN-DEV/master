package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstAcaInfo;
import gov.step.app.repository.InstAcaInfoRepository;
import gov.step.app.repository.search.InstAcaInfoSearchRepository;

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
 * Test class for the InstAcaInfoResource REST controller.
 *
 * @see InstAcaInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstAcaInfoResourceTest {

    private static final String DEFAULT_ACADEMIC_COUNSELOR_NAME = "AAAAA";
    private static final String UPDATED_ACADEMIC_COUNSELOR_NAME = "BBBBB";
    private static final String DEFAULT_MOBILE = "AAAAA";
    private static final String UPDATED_MOBILE = "BBBBB";


private static final Curriculum DEFAULT_CURRICULUM = Curriculum.SSC_VOC;
    private static final Curriculum UPDATED_CURRICULUM = Curriculum.HSC_VOC;

    private static final Integer DEFAULT_TOTAL_TRADE_TECH_NO = 1;
    private static final Integer UPDATED_TOTAL_TRADE_TECH_NO = 2;
    private static final String DEFAULT_TRADE_TECH_DETAILS = "AAAAA";
    private static final String UPDATED_TRADE_TECH_DETAILS = "BBBBB";

    @Inject
    private InstAcaInfoRepository instAcaInfoRepository;

    @Inject
    private InstAcaInfoSearchRepository instAcaInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstAcaInfoMockMvc;

    private InstAcaInfo instAcaInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstAcaInfoResource instAcaInfoResource = new InstAcaInfoResource();
        ReflectionTestUtils.setField(instAcaInfoResource, "instAcaInfoRepository", instAcaInfoRepository);
        ReflectionTestUtils.setField(instAcaInfoResource, "instAcaInfoSearchRepository", instAcaInfoSearchRepository);
        this.restInstAcaInfoMockMvc = MockMvcBuilders.standaloneSetup(instAcaInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instAcaInfo = new InstAcaInfo();
        instAcaInfo.setAcademicCounselorName(DEFAULT_ACADEMIC_COUNSELOR_NAME);
        instAcaInfo.setMobile(DEFAULT_MOBILE);
        instAcaInfo.setCurriculum(DEFAULT_CURRICULUM);
        instAcaInfo.setTotalTradeTechNo(DEFAULT_TOTAL_TRADE_TECH_NO);
        instAcaInfo.setTradeTechDetails(DEFAULT_TRADE_TECH_DETAILS);
    }

    @Test
    @Transactional
    public void createInstAcaInfo() throws Exception {
        int databaseSizeBeforeCreate = instAcaInfoRepository.findAll().size();

        // Create the InstAcaInfo

        restInstAcaInfoMockMvc.perform(post("/api/instAcaInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAcaInfo)))
                .andExpect(status().isCreated());

        // Validate the InstAcaInfo in the database
        List<InstAcaInfo> instAcaInfos = instAcaInfoRepository.findAll();
        assertThat(instAcaInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstAcaInfo testInstAcaInfo = instAcaInfos.get(instAcaInfos.size() - 1);
        assertThat(testInstAcaInfo.getAcademicCounselorName()).isEqualTo(DEFAULT_ACADEMIC_COUNSELOR_NAME);
        assertThat(testInstAcaInfo.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testInstAcaInfo.getCurriculum()).isEqualTo(DEFAULT_CURRICULUM);
        assertThat(testInstAcaInfo.getTotalTradeTechNo()).isEqualTo(DEFAULT_TOTAL_TRADE_TECH_NO);
        assertThat(testInstAcaInfo.getTradeTechDetails()).isEqualTo(DEFAULT_TRADE_TECH_DETAILS);
    }

    @Test
    @Transactional
    public void getAllInstAcaInfos() throws Exception {
        // Initialize the database
        instAcaInfoRepository.saveAndFlush(instAcaInfo);

        // Get all the instAcaInfos
        restInstAcaInfoMockMvc.perform(get("/api/instAcaInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instAcaInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].academicCounselorName").value(hasItem(DEFAULT_ACADEMIC_COUNSELOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].Mobile").value(hasItem(DEFAULT_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].curriculum").value(hasItem(DEFAULT_CURRICULUM.toString())))
                .andExpect(jsonPath("$.[*].totalTradeTechNo").value(hasItem(DEFAULT_TOTAL_TRADE_TECH_NO)))
                .andExpect(jsonPath("$.[*].tradeTechDetails").value(hasItem(DEFAULT_TRADE_TECH_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getInstAcaInfo() throws Exception {
        // Initialize the database
        instAcaInfoRepository.saveAndFlush(instAcaInfo);

        // Get the instAcaInfo
        restInstAcaInfoMockMvc.perform(get("/api/instAcaInfos/{id}", instAcaInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instAcaInfo.getId().intValue()))
            .andExpect(jsonPath("$.academicCounselorName").value(DEFAULT_ACADEMIC_COUNSELOR_NAME.toString()))
            .andExpect(jsonPath("$.Mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.curriculum").value(DEFAULT_CURRICULUM.toString()))
            .andExpect(jsonPath("$.totalTradeTechNo").value(DEFAULT_TOTAL_TRADE_TECH_NO))
            .andExpect(jsonPath("$.tradeTechDetails").value(DEFAULT_TRADE_TECH_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstAcaInfo() throws Exception {
        // Get the instAcaInfo
        restInstAcaInfoMockMvc.perform(get("/api/instAcaInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstAcaInfo() throws Exception {
        // Initialize the database
        instAcaInfoRepository.saveAndFlush(instAcaInfo);

		int databaseSizeBeforeUpdate = instAcaInfoRepository.findAll().size();

        // Update the instAcaInfo
        instAcaInfo.setAcademicCounselorName(UPDATED_ACADEMIC_COUNSELOR_NAME);
        instAcaInfo.setMobile(UPDATED_MOBILE);
        instAcaInfo.setCurriculum(UPDATED_CURRICULUM);
        instAcaInfo.setTotalTradeTechNo(UPDATED_TOTAL_TRADE_TECH_NO);
        instAcaInfo.setTradeTechDetails(UPDATED_TRADE_TECH_DETAILS);

        restInstAcaInfoMockMvc.perform(put("/api/instAcaInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAcaInfo)))
                .andExpect(status().isOk());

        // Validate the InstAcaInfo in the database
        List<InstAcaInfo> instAcaInfos = instAcaInfoRepository.findAll();
        assertThat(instAcaInfos).hasSize(databaseSizeBeforeUpdate);
        InstAcaInfo testInstAcaInfo = instAcaInfos.get(instAcaInfos.size() - 1);
        assertThat(testInstAcaInfo.getAcademicCounselorName()).isEqualTo(UPDATED_ACADEMIC_COUNSELOR_NAME);
        assertThat(testInstAcaInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testInstAcaInfo.getCurriculum()).isEqualTo(UPDATED_CURRICULUM);
        assertThat(testInstAcaInfo.getTotalTradeTechNo()).isEqualTo(UPDATED_TOTAL_TRADE_TECH_NO);
        assertThat(testInstAcaInfo.getTradeTechDetails()).isEqualTo(UPDATED_TRADE_TECH_DETAILS);
    }

    @Test
    @Transactional
    public void deleteInstAcaInfo() throws Exception {
        // Initialize the database
        instAcaInfoRepository.saveAndFlush(instAcaInfo);

		int databaseSizeBeforeDelete = instAcaInfoRepository.findAll().size();

        // Get the instAcaInfo
        restInstAcaInfoMockMvc.perform(delete("/api/instAcaInfos/{id}", instAcaInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstAcaInfo> instAcaInfos = instAcaInfoRepository.findAll();
        assertThat(instAcaInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
