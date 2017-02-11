package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.IisCurriculumInfo;
import gov.step.app.repository.IisCurriculumInfoRepository;
import gov.step.app.repository.search.IisCurriculumInfoSearchRepository;

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
 * Test class for the IisCurriculumInfoResource REST controller.
 *
 * @see IisCurriculumInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class IisCurriculumInfoResourceIntTest {


    private static final LocalDate DEFAULT_FIRST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_MPO_ENLISTED = false;
    private static final Boolean UPDATED_MPO_ENLISTED = true;
    private static final String DEFAULT_REC_NO = "AAAAA";
    private static final String UPDATED_REC_NO = "BBBBB";

    private static final LocalDate DEFAULT_MPO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MPO_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private IisCurriculumInfoRepository iisCurriculumInfoRepository;

    @Inject
    private IisCurriculumInfoSearchRepository iisCurriculumInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIisCurriculumInfoMockMvc;

    private IisCurriculumInfo iisCurriculumInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IisCurriculumInfoResource iisCurriculumInfoResource = new IisCurriculumInfoResource();
        ReflectionTestUtils.setField(iisCurriculumInfoResource, "iisCurriculumInfoRepository", iisCurriculumInfoRepository);
        ReflectionTestUtils.setField(iisCurriculumInfoResource, "iisCurriculumInfoSearchRepository", iisCurriculumInfoSearchRepository);
        this.restIisCurriculumInfoMockMvc = MockMvcBuilders.standaloneSetup(iisCurriculumInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        iisCurriculumInfo = new IisCurriculumInfo();
        iisCurriculumInfo.setFirstDate(DEFAULT_FIRST_DATE);
        iisCurriculumInfo.setLastDate(DEFAULT_LAST_DATE);
        iisCurriculumInfo.setMpoEnlisted(DEFAULT_MPO_ENLISTED);
        iisCurriculumInfo.setRecNo(DEFAULT_REC_NO);
        iisCurriculumInfo.setMpoDate(DEFAULT_MPO_DATE);
    }

    @Test
    @Transactional
    public void createIisCurriculumInfo() throws Exception {
        int databaseSizeBeforeCreate = iisCurriculumInfoRepository.findAll().size();

        // Create the IisCurriculumInfo

        restIisCurriculumInfoMockMvc.perform(post("/api/iisCurriculumInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iisCurriculumInfo)))
                .andExpect(status().isCreated());

        // Validate the IisCurriculumInfo in the database
        List<IisCurriculumInfo> iisCurriculumInfos = iisCurriculumInfoRepository.findAll();
        assertThat(iisCurriculumInfos).hasSize(databaseSizeBeforeCreate + 1);
        IisCurriculumInfo testIisCurriculumInfo = iisCurriculumInfos.get(iisCurriculumInfos.size() - 1);
        assertThat(testIisCurriculumInfo.getFirstDate()).isEqualTo(DEFAULT_FIRST_DATE);
        assertThat(testIisCurriculumInfo.getLastDate()).isEqualTo(DEFAULT_LAST_DATE);
        assertThat(testIisCurriculumInfo.getMpoEnlisted()).isEqualTo(DEFAULT_MPO_ENLISTED);
        assertThat(testIisCurriculumInfo.getRecNo()).isEqualTo(DEFAULT_REC_NO);
        assertThat(testIisCurriculumInfo.getMpoDate()).isEqualTo(DEFAULT_MPO_DATE);
    }

    @Test
    @Transactional
    public void getAllIisCurriculumInfos() throws Exception {
        // Initialize the database
        iisCurriculumInfoRepository.saveAndFlush(iisCurriculumInfo);

        // Get all the iisCurriculumInfos
        restIisCurriculumInfoMockMvc.perform(get("/api/iisCurriculumInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(iisCurriculumInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstDate").value(hasItem(DEFAULT_FIRST_DATE.toString())))
                .andExpect(jsonPath("$.[*].lastDate").value(hasItem(DEFAULT_LAST_DATE.toString())))
                .andExpect(jsonPath("$.[*].mpoEnlisted").value(hasItem(DEFAULT_MPO_ENLISTED.booleanValue())))
                .andExpect(jsonPath("$.[*].recNo").value(hasItem(DEFAULT_REC_NO.toString())))
                .andExpect(jsonPath("$.[*].mpoDate").value(hasItem(DEFAULT_MPO_DATE.toString())));
    }

    @Test
    @Transactional
    public void getIisCurriculumInfo() throws Exception {
        // Initialize the database
        iisCurriculumInfoRepository.saveAndFlush(iisCurriculumInfo);

        // Get the iisCurriculumInfo
        restIisCurriculumInfoMockMvc.perform(get("/api/iisCurriculumInfos/{id}", iisCurriculumInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(iisCurriculumInfo.getId().intValue()))
            .andExpect(jsonPath("$.firstDate").value(DEFAULT_FIRST_DATE.toString()))
            .andExpect(jsonPath("$.lastDate").value(DEFAULT_LAST_DATE.toString()))
            .andExpect(jsonPath("$.mpoEnlisted").value(DEFAULT_MPO_ENLISTED.booleanValue()))
            .andExpect(jsonPath("$.recNo").value(DEFAULT_REC_NO.toString()))
            .andExpect(jsonPath("$.mpoDate").value(DEFAULT_MPO_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIisCurriculumInfo() throws Exception {
        // Get the iisCurriculumInfo
        restIisCurriculumInfoMockMvc.perform(get("/api/iisCurriculumInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIisCurriculumInfo() throws Exception {
        // Initialize the database
        iisCurriculumInfoRepository.saveAndFlush(iisCurriculumInfo);

		int databaseSizeBeforeUpdate = iisCurriculumInfoRepository.findAll().size();

        // Update the iisCurriculumInfo
        iisCurriculumInfo.setFirstDate(UPDATED_FIRST_DATE);
        iisCurriculumInfo.setLastDate(UPDATED_LAST_DATE);
        iisCurriculumInfo.setMpoEnlisted(UPDATED_MPO_ENLISTED);
        iisCurriculumInfo.setRecNo(UPDATED_REC_NO);
        iisCurriculumInfo.setMpoDate(UPDATED_MPO_DATE);

        restIisCurriculumInfoMockMvc.perform(put("/api/iisCurriculumInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iisCurriculumInfo)))
                .andExpect(status().isOk());

        // Validate the IisCurriculumInfo in the database
        List<IisCurriculumInfo> iisCurriculumInfos = iisCurriculumInfoRepository.findAll();
        assertThat(iisCurriculumInfos).hasSize(databaseSizeBeforeUpdate);
        IisCurriculumInfo testIisCurriculumInfo = iisCurriculumInfos.get(iisCurriculumInfos.size() - 1);
        assertThat(testIisCurriculumInfo.getFirstDate()).isEqualTo(UPDATED_FIRST_DATE);
        assertThat(testIisCurriculumInfo.getLastDate()).isEqualTo(UPDATED_LAST_DATE);
        assertThat(testIisCurriculumInfo.getMpoEnlisted()).isEqualTo(UPDATED_MPO_ENLISTED);
        assertThat(testIisCurriculumInfo.getRecNo()).isEqualTo(UPDATED_REC_NO);
        assertThat(testIisCurriculumInfo.getMpoDate()).isEqualTo(UPDATED_MPO_DATE);
    }

    @Test
    @Transactional
    public void deleteIisCurriculumInfo() throws Exception {
        // Initialize the database
        iisCurriculumInfoRepository.saveAndFlush(iisCurriculumInfo);

		int databaseSizeBeforeDelete = iisCurriculumInfoRepository.findAll().size();

        // Get the iisCurriculumInfo
        restIisCurriculumInfoMockMvc.perform(delete("/api/iisCurriculumInfos/{id}", iisCurriculumInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<IisCurriculumInfo> iisCurriculumInfos = iisCurriculumInfoRepository.findAll();
        assertThat(iisCurriculumInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
