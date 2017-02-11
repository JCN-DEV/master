package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.IisCurriculumInfoTemp;
import gov.step.app.repository.IisCurriculumInfoTempRepository;
import gov.step.app.repository.search.IisCurriculumInfoTempSearchRepository;

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
 * Test class for the IisCurriculumInfoTempResource REST controller.
 *
 * @see IisCurriculumInfoTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class IisCurriculumInfoTempResourceIntTest {


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

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private IisCurriculumInfoTempRepository iisCurriculumInfoTempRepository;

    @Inject
    private IisCurriculumInfoTempSearchRepository iisCurriculumInfoTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIisCurriculumInfoTempMockMvc;

    private IisCurriculumInfoTemp iisCurriculumInfoTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IisCurriculumInfoTempResource iisCurriculumInfoTempResource = new IisCurriculumInfoTempResource();
        ReflectionTestUtils.setField(iisCurriculumInfoTempResource, "iisCurriculumInfoTempRepository", iisCurriculumInfoTempRepository);
        ReflectionTestUtils.setField(iisCurriculumInfoTempResource, "iisCurriculumInfoTempSearchRepository", iisCurriculumInfoTempSearchRepository);
        this.restIisCurriculumInfoTempMockMvc = MockMvcBuilders.standaloneSetup(iisCurriculumInfoTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        iisCurriculumInfoTemp = new IisCurriculumInfoTemp();
        iisCurriculumInfoTemp.setFirstDate(DEFAULT_FIRST_DATE);
        iisCurriculumInfoTemp.setLastDate(DEFAULT_LAST_DATE);
        iisCurriculumInfoTemp.setMpoEnlisted(DEFAULT_MPO_ENLISTED);
        iisCurriculumInfoTemp.setRecNo(DEFAULT_REC_NO);
        iisCurriculumInfoTemp.setMpoDate(DEFAULT_MPO_DATE);
        iisCurriculumInfoTemp.setCreateDate(DEFAULT_CREATE_DATE);
        iisCurriculumInfoTemp.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createIisCurriculumInfoTemp() throws Exception {
        int databaseSizeBeforeCreate = iisCurriculumInfoTempRepository.findAll().size();

        // Create the IisCurriculumInfoTemp

        restIisCurriculumInfoTempMockMvc.perform(post("/api/iisCurriculumInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iisCurriculumInfoTemp)))
                .andExpect(status().isCreated());

        // Validate the IisCurriculumInfoTemp in the database
        List<IisCurriculumInfoTemp> iisCurriculumInfoTemps = iisCurriculumInfoTempRepository.findAll();
        assertThat(iisCurriculumInfoTemps).hasSize(databaseSizeBeforeCreate + 1);
        IisCurriculumInfoTemp testIisCurriculumInfoTemp = iisCurriculumInfoTemps.get(iisCurriculumInfoTemps.size() - 1);
        assertThat(testIisCurriculumInfoTemp.getFirstDate()).isEqualTo(DEFAULT_FIRST_DATE);
        assertThat(testIisCurriculumInfoTemp.getLastDate()).isEqualTo(DEFAULT_LAST_DATE);
        assertThat(testIisCurriculumInfoTemp.getMpoEnlisted()).isEqualTo(DEFAULT_MPO_ENLISTED);
        assertThat(testIisCurriculumInfoTemp.getRecNo()).isEqualTo(DEFAULT_REC_NO);
        assertThat(testIisCurriculumInfoTemp.getMpoDate()).isEqualTo(DEFAULT_MPO_DATE);
        assertThat(testIisCurriculumInfoTemp.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testIisCurriculumInfoTemp.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllIisCurriculumInfoTemps() throws Exception {
        // Initialize the database
        iisCurriculumInfoTempRepository.saveAndFlush(iisCurriculumInfoTemp);

        // Get all the iisCurriculumInfoTemps
        restIisCurriculumInfoTempMockMvc.perform(get("/api/iisCurriculumInfoTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(iisCurriculumInfoTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstDate").value(hasItem(DEFAULT_FIRST_DATE.toString())))
                .andExpect(jsonPath("$.[*].lastDate").value(hasItem(DEFAULT_LAST_DATE.toString())))
                .andExpect(jsonPath("$.[*].mpoEnlisted").value(hasItem(DEFAULT_MPO_ENLISTED.booleanValue())))
                .andExpect(jsonPath("$.[*].recNo").value(hasItem(DEFAULT_REC_NO.toString())))
                .andExpect(jsonPath("$.[*].mpoDate").value(hasItem(DEFAULT_MPO_DATE.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getIisCurriculumInfoTemp() throws Exception {
        // Initialize the database
        iisCurriculumInfoTempRepository.saveAndFlush(iisCurriculumInfoTemp);

        // Get the iisCurriculumInfoTemp
        restIisCurriculumInfoTempMockMvc.perform(get("/api/iisCurriculumInfoTemps/{id}", iisCurriculumInfoTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(iisCurriculumInfoTemp.getId().intValue()))
            .andExpect(jsonPath("$.firstDate").value(DEFAULT_FIRST_DATE.toString()))
            .andExpect(jsonPath("$.lastDate").value(DEFAULT_LAST_DATE.toString()))
            .andExpect(jsonPath("$.mpoEnlisted").value(DEFAULT_MPO_ENLISTED.booleanValue()))
            .andExpect(jsonPath("$.recNo").value(DEFAULT_REC_NO.toString()))
            .andExpect(jsonPath("$.mpoDate").value(DEFAULT_MPO_DATE.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIisCurriculumInfoTemp() throws Exception {
        // Get the iisCurriculumInfoTemp
        restIisCurriculumInfoTempMockMvc.perform(get("/api/iisCurriculumInfoTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIisCurriculumInfoTemp() throws Exception {
        // Initialize the database
        iisCurriculumInfoTempRepository.saveAndFlush(iisCurriculumInfoTemp);

		int databaseSizeBeforeUpdate = iisCurriculumInfoTempRepository.findAll().size();

        // Update the iisCurriculumInfoTemp
        iisCurriculumInfoTemp.setFirstDate(UPDATED_FIRST_DATE);
        iisCurriculumInfoTemp.setLastDate(UPDATED_LAST_DATE);
        iisCurriculumInfoTemp.setMpoEnlisted(UPDATED_MPO_ENLISTED);
        iisCurriculumInfoTemp.setRecNo(UPDATED_REC_NO);
        iisCurriculumInfoTemp.setMpoDate(UPDATED_MPO_DATE);
        iisCurriculumInfoTemp.setCreateDate(UPDATED_CREATE_DATE);
        iisCurriculumInfoTemp.setUpdateDate(UPDATED_UPDATE_DATE);

        restIisCurriculumInfoTempMockMvc.perform(put("/api/iisCurriculumInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iisCurriculumInfoTemp)))
                .andExpect(status().isOk());

        // Validate the IisCurriculumInfoTemp in the database
        List<IisCurriculumInfoTemp> iisCurriculumInfoTemps = iisCurriculumInfoTempRepository.findAll();
        assertThat(iisCurriculumInfoTemps).hasSize(databaseSizeBeforeUpdate);
        IisCurriculumInfoTemp testIisCurriculumInfoTemp = iisCurriculumInfoTemps.get(iisCurriculumInfoTemps.size() - 1);
        assertThat(testIisCurriculumInfoTemp.getFirstDate()).isEqualTo(UPDATED_FIRST_DATE);
        assertThat(testIisCurriculumInfoTemp.getLastDate()).isEqualTo(UPDATED_LAST_DATE);
        assertThat(testIisCurriculumInfoTemp.getMpoEnlisted()).isEqualTo(UPDATED_MPO_ENLISTED);
        assertThat(testIisCurriculumInfoTemp.getRecNo()).isEqualTo(UPDATED_REC_NO);
        assertThat(testIisCurriculumInfoTemp.getMpoDate()).isEqualTo(UPDATED_MPO_DATE);
        assertThat(testIisCurriculumInfoTemp.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testIisCurriculumInfoTemp.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deleteIisCurriculumInfoTemp() throws Exception {
        // Initialize the database
        iisCurriculumInfoTempRepository.saveAndFlush(iisCurriculumInfoTemp);

		int databaseSizeBeforeDelete = iisCurriculumInfoTempRepository.findAll().size();

        // Get the iisCurriculumInfoTemp
        restIisCurriculumInfoTempMockMvc.perform(delete("/api/iisCurriculumInfoTemps/{id}", iisCurriculumInfoTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<IisCurriculumInfoTemp> iisCurriculumInfoTemps = iisCurriculumInfoTempRepository.findAll();
        assertThat(iisCurriculumInfoTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
