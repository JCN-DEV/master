package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstAcaInfoTemp;
import gov.step.app.repository.InstAcaInfoTempRepository;
import gov.step.app.repository.search.InstAcaInfoTempSearchRepository;

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
 * Test class for the InstAcaInfoTempResource REST controller.
 *
 * @see InstAcaInfoTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstAcaInfoTempResourceTest {

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
    private InstAcaInfoTempRepository instAcaInfoTempRepository;

    @Inject
    private InstAcaInfoTempSearchRepository instAcaInfoTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstAcaInfoTempMockMvc;

    private InstAcaInfoTemp instAcaInfoTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstAcaInfoTempResource instAcaInfoTempResource = new InstAcaInfoTempResource();
        ReflectionTestUtils.setField(instAcaInfoTempResource, "instAcaInfoTempRepository", instAcaInfoTempRepository);
        ReflectionTestUtils.setField(instAcaInfoTempResource, "instAcaInfoTempSearchRepository", instAcaInfoTempSearchRepository);
        this.restInstAcaInfoTempMockMvc = MockMvcBuilders.standaloneSetup(instAcaInfoTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instAcaInfoTemp = new InstAcaInfoTemp();
        instAcaInfoTemp.setAcademicCounselorName(DEFAULT_ACADEMIC_COUNSELOR_NAME);
        instAcaInfoTemp.setMobile(DEFAULT_MOBILE);
        instAcaInfoTemp.setCurriculum(DEFAULT_CURRICULUM);
        instAcaInfoTemp.setTotalTradeTechNo(DEFAULT_TOTAL_TRADE_TECH_NO);
        instAcaInfoTemp.setTradeTechDetails(DEFAULT_TRADE_TECH_DETAILS);
    }

    @Test
    @Transactional
    public void createInstAcaInfoTemp() throws Exception {
        int databaseSizeBeforeCreate = instAcaInfoTempRepository.findAll().size();

        // Create the InstAcaInfoTemp

        restInstAcaInfoTempMockMvc.perform(post("/api/instAcaInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAcaInfoTemp)))
                .andExpect(status().isCreated());

        // Validate the InstAcaInfoTemp in the database
        List<InstAcaInfoTemp> instAcaInfoTemps = instAcaInfoTempRepository.findAll();
        assertThat(instAcaInfoTemps).hasSize(databaseSizeBeforeCreate + 1);
        InstAcaInfoTemp testInstAcaInfoTemp = instAcaInfoTemps.get(instAcaInfoTemps.size() - 1);
        assertThat(testInstAcaInfoTemp.getAcademicCounselorName()).isEqualTo(DEFAULT_ACADEMIC_COUNSELOR_NAME);
        assertThat(testInstAcaInfoTemp.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testInstAcaInfoTemp.getCurriculum()).isEqualTo(DEFAULT_CURRICULUM);
        assertThat(testInstAcaInfoTemp.getTotalTradeTechNo()).isEqualTo(DEFAULT_TOTAL_TRADE_TECH_NO);
        assertThat(testInstAcaInfoTemp.getTradeTechDetails()).isEqualTo(DEFAULT_TRADE_TECH_DETAILS);
    }

    @Test
    @Transactional
    public void getAllInstAcaInfoTemps() throws Exception {
        // Initialize the database
        instAcaInfoTempRepository.saveAndFlush(instAcaInfoTemp);

        // Get all the instAcaInfoTemps
        restInstAcaInfoTempMockMvc.perform(get("/api/instAcaInfoTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instAcaInfoTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].academicCounselorName").value(hasItem(DEFAULT_ACADEMIC_COUNSELOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].Mobile").value(hasItem(DEFAULT_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].curriculum").value(hasItem(DEFAULT_CURRICULUM.toString())))
                .andExpect(jsonPath("$.[*].totalTradeTechNo").value(hasItem(DEFAULT_TOTAL_TRADE_TECH_NO)))
                .andExpect(jsonPath("$.[*].tradeTechDetails").value(hasItem(DEFAULT_TRADE_TECH_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getInstAcaInfoTemp() throws Exception {
        // Initialize the database
        instAcaInfoTempRepository.saveAndFlush(instAcaInfoTemp);

        // Get the instAcaInfoTemp
        restInstAcaInfoTempMockMvc.perform(get("/api/instAcaInfoTemps/{id}", instAcaInfoTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instAcaInfoTemp.getId().intValue()))
            .andExpect(jsonPath("$.academicCounselorName").value(DEFAULT_ACADEMIC_COUNSELOR_NAME.toString()))
            .andExpect(jsonPath("$.Mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.curriculum").value(DEFAULT_CURRICULUM.toString()))
            .andExpect(jsonPath("$.totalTradeTechNo").value(DEFAULT_TOTAL_TRADE_TECH_NO))
            .andExpect(jsonPath("$.tradeTechDetails").value(DEFAULT_TRADE_TECH_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstAcaInfoTemp() throws Exception {
        // Get the instAcaInfoTemp
        restInstAcaInfoTempMockMvc.perform(get("/api/instAcaInfoTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstAcaInfoTemp() throws Exception {
        // Initialize the database
        instAcaInfoTempRepository.saveAndFlush(instAcaInfoTemp);

		int databaseSizeBeforeUpdate = instAcaInfoTempRepository.findAll().size();

        // Update the instAcaInfoTemp
        instAcaInfoTemp.setAcademicCounselorName(UPDATED_ACADEMIC_COUNSELOR_NAME);
        instAcaInfoTemp.setMobile(UPDATED_MOBILE);
        instAcaInfoTemp.setCurriculum(UPDATED_CURRICULUM);
        instAcaInfoTemp.setTotalTradeTechNo(UPDATED_TOTAL_TRADE_TECH_NO);
        instAcaInfoTemp.setTradeTechDetails(UPDATED_TRADE_TECH_DETAILS);

        restInstAcaInfoTempMockMvc.perform(put("/api/instAcaInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instAcaInfoTemp)))
                .andExpect(status().isOk());

        // Validate the InstAcaInfoTemp in the database
        List<InstAcaInfoTemp> instAcaInfoTemps = instAcaInfoTempRepository.findAll();
        assertThat(instAcaInfoTemps).hasSize(databaseSizeBeforeUpdate);
        InstAcaInfoTemp testInstAcaInfoTemp = instAcaInfoTemps.get(instAcaInfoTemps.size() - 1);
        assertThat(testInstAcaInfoTemp.getAcademicCounselorName()).isEqualTo(UPDATED_ACADEMIC_COUNSELOR_NAME);
        assertThat(testInstAcaInfoTemp.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testInstAcaInfoTemp.getCurriculum()).isEqualTo(UPDATED_CURRICULUM);
        assertThat(testInstAcaInfoTemp.getTotalTradeTechNo()).isEqualTo(UPDATED_TOTAL_TRADE_TECH_NO);
        assertThat(testInstAcaInfoTemp.getTradeTechDetails()).isEqualTo(UPDATED_TRADE_TECH_DETAILS);
    }

    @Test
    @Transactional
    public void deleteInstAcaInfoTemp() throws Exception {
        // Initialize the database
        instAcaInfoTempRepository.saveAndFlush(instAcaInfoTemp);

		int databaseSizeBeforeDelete = instAcaInfoTempRepository.findAll().size();

        // Get the instAcaInfoTemp
        restInstAcaInfoTempMockMvc.perform(delete("/api/instAcaInfoTemps/{id}", instAcaInfoTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstAcaInfoTemp> instAcaInfoTemps = instAcaInfoTempRepository.findAll();
        assertThat(instAcaInfoTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
