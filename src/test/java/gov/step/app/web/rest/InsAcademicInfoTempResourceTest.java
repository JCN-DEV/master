package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InsAcademicInfoTemp;
import gov.step.app.repository.InsAcademicInfoTempRepository;
import gov.step.app.repository.search.InsAcademicInfoTempSearchRepository;

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
 * Test class for the InsAcademicInfoTempResource REST controller.
 *
 * @see InsAcademicInfoTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InsAcademicInfoTempResourceTest {

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
    private InsAcademicInfoTempRepository insAcademicInfoTempRepository;

    @Inject
    private InsAcademicInfoTempSearchRepository insAcademicInfoTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInsAcademicInfoTempMockMvc;

    private InsAcademicInfoTemp insAcademicInfoTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InsAcademicInfoTempResource insAcademicInfoTempResource = new InsAcademicInfoTempResource();
        ReflectionTestUtils.setField(insAcademicInfoTempResource, "insAcademicInfoTempRepository", insAcademicInfoTempRepository);
        ReflectionTestUtils.setField(insAcademicInfoTempResource, "insAcademicInfoTempSearchRepository", insAcademicInfoTempSearchRepository);
        this.restInsAcademicInfoTempMockMvc = MockMvcBuilders.standaloneSetup(insAcademicInfoTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        insAcademicInfoTemp = new InsAcademicInfoTemp();
        insAcademicInfoTemp.setCounselorName(DEFAULT_COUNSELOR_NAME);
        insAcademicInfoTemp.setCurriculum(DEFAULT_CURRICULUM);
        insAcademicInfoTemp.setTotalTechTradeNo(DEFAULT_TOTAL_TECH_TRADE_NO);
        insAcademicInfoTemp.setTradeTechDetails(DEFAULT_TRADE_TECH_DETAILS);
        insAcademicInfoTemp.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInsAcademicInfoTemp() throws Exception {
        int databaseSizeBeforeCreate = insAcademicInfoTempRepository.findAll().size();

        // Create the InsAcademicInfoTemp

        restInsAcademicInfoTempMockMvc.perform(post("/api/insAcademicInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(insAcademicInfoTemp)))
                .andExpect(status().isCreated());

        // Validate the InsAcademicInfoTemp in the database
        List<InsAcademicInfoTemp> insAcademicInfoTemps = insAcademicInfoTempRepository.findAll();
        assertThat(insAcademicInfoTemps).hasSize(databaseSizeBeforeCreate + 1);
        InsAcademicInfoTemp testInsAcademicInfoTemp = insAcademicInfoTemps.get(insAcademicInfoTemps.size() - 1);
        assertThat(testInsAcademicInfoTemp.getCounselorName()).isEqualTo(DEFAULT_COUNSELOR_NAME);
        assertThat(testInsAcademicInfoTemp.getCurriculum()).isEqualTo(DEFAULT_CURRICULUM);
        assertThat(testInsAcademicInfoTemp.getTotalTechTradeNo()).isEqualTo(DEFAULT_TOTAL_TECH_TRADE_NO);
        assertThat(testInsAcademicInfoTemp.getTradeTechDetails()).isEqualTo(DEFAULT_TRADE_TECH_DETAILS);
        assertThat(testInsAcademicInfoTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInsAcademicInfoTemps() throws Exception {
        // Initialize the database
        insAcademicInfoTempRepository.saveAndFlush(insAcademicInfoTemp);

        // Get all the insAcademicInfoTemps
        restInsAcademicInfoTempMockMvc.perform(get("/api/insAcademicInfoTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(insAcademicInfoTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].counselorName").value(hasItem(DEFAULT_COUNSELOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].curriculum").value(hasItem(DEFAULT_CURRICULUM.toString())))
                .andExpect(jsonPath("$.[*].totalTechTradeNo").value(hasItem(DEFAULT_TOTAL_TECH_TRADE_NO)))
                .andExpect(jsonPath("$.[*].tradeTechDetails").value(hasItem(DEFAULT_TRADE_TECH_DETAILS.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInsAcademicInfoTemp() throws Exception {
        // Initialize the database
        insAcademicInfoTempRepository.saveAndFlush(insAcademicInfoTemp);

        // Get the insAcademicInfoTemp
        restInsAcademicInfoTempMockMvc.perform(get("/api/insAcademicInfoTemps/{id}", insAcademicInfoTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(insAcademicInfoTemp.getId().intValue()))
            .andExpect(jsonPath("$.counselorName").value(DEFAULT_COUNSELOR_NAME.toString()))
            .andExpect(jsonPath("$.curriculum").value(DEFAULT_CURRICULUM.toString()))
            .andExpect(jsonPath("$.totalTechTradeNo").value(DEFAULT_TOTAL_TECH_TRADE_NO))
            .andExpect(jsonPath("$.tradeTechDetails").value(DEFAULT_TRADE_TECH_DETAILS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInsAcademicInfoTemp() throws Exception {
        // Get the insAcademicInfoTemp
        restInsAcademicInfoTempMockMvc.perform(get("/api/insAcademicInfoTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsAcademicInfoTemp() throws Exception {
        // Initialize the database
        insAcademicInfoTempRepository.saveAndFlush(insAcademicInfoTemp);

		int databaseSizeBeforeUpdate = insAcademicInfoTempRepository.findAll().size();

        // Update the insAcademicInfoTemp
        insAcademicInfoTemp.setCounselorName(UPDATED_COUNSELOR_NAME);
        insAcademicInfoTemp.setCurriculum(UPDATED_CURRICULUM);
        insAcademicInfoTemp.setTotalTechTradeNo(UPDATED_TOTAL_TECH_TRADE_NO);
        insAcademicInfoTemp.setTradeTechDetails(UPDATED_TRADE_TECH_DETAILS);
        insAcademicInfoTemp.setStatus(UPDATED_STATUS);

        restInsAcademicInfoTempMockMvc.perform(put("/api/insAcademicInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(insAcademicInfoTemp)))
                .andExpect(status().isOk());

        // Validate the InsAcademicInfoTemp in the database
        List<InsAcademicInfoTemp> insAcademicInfoTemps = insAcademicInfoTempRepository.findAll();
        assertThat(insAcademicInfoTemps).hasSize(databaseSizeBeforeUpdate);
        InsAcademicInfoTemp testInsAcademicInfoTemp = insAcademicInfoTemps.get(insAcademicInfoTemps.size() - 1);
        assertThat(testInsAcademicInfoTemp.getCounselorName()).isEqualTo(UPDATED_COUNSELOR_NAME);
        assertThat(testInsAcademicInfoTemp.getCurriculum()).isEqualTo(UPDATED_CURRICULUM);
        assertThat(testInsAcademicInfoTemp.getTotalTechTradeNo()).isEqualTo(UPDATED_TOTAL_TECH_TRADE_NO);
        assertThat(testInsAcademicInfoTemp.getTradeTechDetails()).isEqualTo(UPDATED_TRADE_TECH_DETAILS);
        assertThat(testInsAcademicInfoTemp.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInsAcademicInfoTemp() throws Exception {
        // Initialize the database
        insAcademicInfoTempRepository.saveAndFlush(insAcademicInfoTemp);

		int databaseSizeBeforeDelete = insAcademicInfoTempRepository.findAll().size();

        // Get the insAcademicInfoTemp
        restInsAcademicInfoTempMockMvc.perform(delete("/api/insAcademicInfoTemps/{id}", insAcademicInfoTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InsAcademicInfoTemp> insAcademicInfoTemps = insAcademicInfoTempRepository.findAll();
        assertThat(insAcademicInfoTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
