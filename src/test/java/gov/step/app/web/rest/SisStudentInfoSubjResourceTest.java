package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SisStudentInfoSubj;
import gov.step.app.repository.SisStudentInfoSubjRepository;
import gov.step.app.repository.search.SisStudentInfoSubjSearchRepository;

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
 * Test class for the SisStudentInfoSubjResource REST controller.
 *
 * @see SisStudentInfoSubjResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SisStudentInfoSubjResourceTest {

    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_SUBJECT_TYPE = "AAAAA";
    private static final String UPDATED_SUBJECT_TYPE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private SisStudentInfoSubjRepository sisStudentInfoSubjRepository;

    @Inject
    private SisStudentInfoSubjSearchRepository sisStudentInfoSubjSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSisStudentInfoSubjMockMvc;

    private SisStudentInfoSubj sisStudentInfoSubj;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SisStudentInfoSubjResource sisStudentInfoSubjResource = new SisStudentInfoSubjResource();
        ReflectionTestUtils.setField(sisStudentInfoSubjResource, "sisStudentInfoSubjRepository", sisStudentInfoSubjRepository);
        ReflectionTestUtils.setField(sisStudentInfoSubjResource, "sisStudentInfoSubjSearchRepository", sisStudentInfoSubjSearchRepository);
        this.restSisStudentInfoSubjMockMvc = MockMvcBuilders.standaloneSetup(sisStudentInfoSubjResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        sisStudentInfoSubj = new SisStudentInfoSubj();
        sisStudentInfoSubj.setSubject(DEFAULT_SUBJECT);
        sisStudentInfoSubj.setSubjectType(DEFAULT_SUBJECT_TYPE);
        sisStudentInfoSubj.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        sisStudentInfoSubj.setCreateDate(DEFAULT_CREATE_DATE);
        sisStudentInfoSubj.setCreateBy(DEFAULT_CREATE_BY);
        sisStudentInfoSubj.setUpdateDate(DEFAULT_UPDATE_DATE);
        sisStudentInfoSubj.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createSisStudentInfoSubj() throws Exception {
        int databaseSizeBeforeCreate = sisStudentInfoSubjRepository.findAll().size();

        // Create the SisStudentInfoSubj

        restSisStudentInfoSubjMockMvc.perform(post("/api/sisStudentInfoSubjs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisStudentInfoSubj)))
                .andExpect(status().isCreated());

        // Validate the SisStudentInfoSubj in the database
        List<SisStudentInfoSubj> sisStudentInfoSubjs = sisStudentInfoSubjRepository.findAll();
        assertThat(sisStudentInfoSubjs).hasSize(databaseSizeBeforeCreate + 1);
        SisStudentInfoSubj testSisStudentInfoSubj = sisStudentInfoSubjs.get(sisStudentInfoSubjs.size() - 1);
        assertThat(testSisStudentInfoSubj.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testSisStudentInfoSubj.getSubjectType()).isEqualTo(DEFAULT_SUBJECT_TYPE);
        assertThat(testSisStudentInfoSubj.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testSisStudentInfoSubj.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testSisStudentInfoSubj.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testSisStudentInfoSubj.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testSisStudentInfoSubj.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllSisStudentInfoSubjs() throws Exception {
        // Initialize the database
        sisStudentInfoSubjRepository.saveAndFlush(sisStudentInfoSubj);

        // Get all the sisStudentInfoSubjs
        restSisStudentInfoSubjMockMvc.perform(get("/api/sisStudentInfoSubjs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sisStudentInfoSubj.getId().intValue())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].subjectType").value(hasItem(DEFAULT_SUBJECT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getSisStudentInfoSubj() throws Exception {
        // Initialize the database
        sisStudentInfoSubjRepository.saveAndFlush(sisStudentInfoSubj);

        // Get the sisStudentInfoSubj
        restSisStudentInfoSubjMockMvc.perform(get("/api/sisStudentInfoSubjs/{id}", sisStudentInfoSubj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sisStudentInfoSubj.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.subjectType").value(DEFAULT_SUBJECT_TYPE.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSisStudentInfoSubj() throws Exception {
        // Get the sisStudentInfoSubj
        restSisStudentInfoSubjMockMvc.perform(get("/api/sisStudentInfoSubjs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSisStudentInfoSubj() throws Exception {
        // Initialize the database
        sisStudentInfoSubjRepository.saveAndFlush(sisStudentInfoSubj);

		int databaseSizeBeforeUpdate = sisStudentInfoSubjRepository.findAll().size();

        // Update the sisStudentInfoSubj
        sisStudentInfoSubj.setSubject(UPDATED_SUBJECT);
        sisStudentInfoSubj.setSubjectType(UPDATED_SUBJECT_TYPE);
        sisStudentInfoSubj.setActiveStatus(UPDATED_ACTIVE_STATUS);
        sisStudentInfoSubj.setCreateDate(UPDATED_CREATE_DATE);
        sisStudentInfoSubj.setCreateBy(UPDATED_CREATE_BY);
        sisStudentInfoSubj.setUpdateDate(UPDATED_UPDATE_DATE);
        sisStudentInfoSubj.setUpdateBy(UPDATED_UPDATE_BY);

        restSisStudentInfoSubjMockMvc.perform(put("/api/sisStudentInfoSubjs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sisStudentInfoSubj)))
                .andExpect(status().isOk());

        // Validate the SisStudentInfoSubj in the database
        List<SisStudentInfoSubj> sisStudentInfoSubjs = sisStudentInfoSubjRepository.findAll();
        assertThat(sisStudentInfoSubjs).hasSize(databaseSizeBeforeUpdate);
        SisStudentInfoSubj testSisStudentInfoSubj = sisStudentInfoSubjs.get(sisStudentInfoSubjs.size() - 1);
        assertThat(testSisStudentInfoSubj.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testSisStudentInfoSubj.getSubjectType()).isEqualTo(UPDATED_SUBJECT_TYPE);
        assertThat(testSisStudentInfoSubj.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testSisStudentInfoSubj.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testSisStudentInfoSubj.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testSisStudentInfoSubj.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testSisStudentInfoSubj.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteSisStudentInfoSubj() throws Exception {
        // Initialize the database
        sisStudentInfoSubjRepository.saveAndFlush(sisStudentInfoSubj);

		int databaseSizeBeforeDelete = sisStudentInfoSubjRepository.findAll().size();

        // Get the sisStudentInfoSubj
        restSisStudentInfoSubjMockMvc.perform(delete("/api/sisStudentInfoSubjs/{id}", sisStudentInfoSubj.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SisStudentInfoSubj> sisStudentInfoSubjs = sisStudentInfoSubjRepository.findAll();
        assertThat(sisStudentInfoSubjs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
