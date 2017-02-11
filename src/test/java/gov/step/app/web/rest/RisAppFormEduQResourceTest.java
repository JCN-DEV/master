package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.RisAppFormEduQ;
import gov.step.app.repository.RisAppFormEduQRepository;
import gov.step.app.repository.search.RisAppFormEduQSearchRepository;

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
 * Test class for the RisAppFormEduQResource REST controller.
 *
 * @see RisAppFormEduQResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RisAppFormEduQResourceTest {

    private static final String DEFAULT_EXAM_NAME = "AAAAA";
    private static final String UPDATED_EXAM_NAME = "BBBBB";
    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_EDUCATIONAL_INSTITUTE = "AAAAA";
    private static final String UPDATED_EDUCATIONAL_INSTITUTE = "BBBBB";

    private static final Integer DEFAULT_PASSING_YEAR = 1;
    private static final Integer UPDATED_PASSING_YEAR = 2;
    private static final String DEFAULT_BOARD_UNIVERSITY = "AAAAA";
    private static final String UPDATED_BOARD_UNIVERSITY = "BBBBB";
    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBB";
    private static final String DEFAULT_EXPERIENCE = "AAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBB";
    private static final String DEFAULT_QOUTA = "AAAAA";
    private static final String UPDATED_QOUTA = "BBBBB";

    @Inject
    private RisAppFormEduQRepository risAppFormEduQRepository;

    @Inject
    private RisAppFormEduQSearchRepository risAppFormEduQSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRisAppFormEduQMockMvc;

    private RisAppFormEduQ risAppFormEduQ;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RisAppFormEduQResource risAppFormEduQResource = new RisAppFormEduQResource();
        ReflectionTestUtils.setField(risAppFormEduQResource, "risAppFormEduQRepository", risAppFormEduQRepository);
        ReflectionTestUtils.setField(risAppFormEduQResource, "risAppFormEduQSearchRepository", risAppFormEduQSearchRepository);
        this.restRisAppFormEduQMockMvc = MockMvcBuilders.standaloneSetup(risAppFormEduQResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        risAppFormEduQ = new RisAppFormEduQ();
        risAppFormEduQ.setExamName(DEFAULT_EXAM_NAME);
        risAppFormEduQ.setSubject(DEFAULT_SUBJECT);
        risAppFormEduQ.setEducationalInstitute(DEFAULT_EDUCATIONAL_INSTITUTE);
        risAppFormEduQ.setPassingYear(DEFAULT_PASSING_YEAR);
        risAppFormEduQ.setBoardUniversity(DEFAULT_BOARD_UNIVERSITY);
        risAppFormEduQ.setAdditionalInformation(DEFAULT_ADDITIONAL_INFORMATION);
        risAppFormEduQ.setExperience(DEFAULT_EXPERIENCE);
        risAppFormEduQ.setQouta(DEFAULT_QOUTA);
    }

    @Test
    @Transactional
    public void createRisAppFormEduQ() throws Exception {
        int databaseSizeBeforeCreate = risAppFormEduQRepository.findAll().size();

        // Create the RisAppFormEduQ

        restRisAppFormEduQMockMvc.perform(post("/api/risAppFormEduQs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risAppFormEduQ)))
                .andExpect(status().isCreated());

        // Validate the RisAppFormEduQ in the database
        List<RisAppFormEduQ> risAppFormEduQs = risAppFormEduQRepository.findAll();
        assertThat(risAppFormEduQs).hasSize(databaseSizeBeforeCreate + 1);
        RisAppFormEduQ testRisAppFormEduQ = risAppFormEduQs.get(risAppFormEduQs.size() - 1);
        assertThat(testRisAppFormEduQ.getExamName()).isEqualTo(DEFAULT_EXAM_NAME);
        assertThat(testRisAppFormEduQ.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testRisAppFormEduQ.getEducationalInstitute()).isEqualTo(DEFAULT_EDUCATIONAL_INSTITUTE);
        assertThat(testRisAppFormEduQ.getPassingYear()).isEqualTo(DEFAULT_PASSING_YEAR);
        assertThat(testRisAppFormEduQ.getBoardUniversity()).isEqualTo(DEFAULT_BOARD_UNIVERSITY);
        assertThat(testRisAppFormEduQ.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
        assertThat(testRisAppFormEduQ.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testRisAppFormEduQ.getQouta()).isEqualTo(DEFAULT_QOUTA);
    }

    @Test
    @Transactional
    public void checkExamNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = risAppFormEduQRepository.findAll().size();
        // set the field null
        risAppFormEduQ.setExamName(null);

        // Create the RisAppFormEduQ, which fails.

        restRisAppFormEduQMockMvc.perform(post("/api/risAppFormEduQs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risAppFormEduQ)))
                .andExpect(status().isBadRequest());

        List<RisAppFormEduQ> risAppFormEduQs = risAppFormEduQRepository.findAll();
        assertThat(risAppFormEduQs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRisAppFormEduQs() throws Exception {
        // Initialize the database
        risAppFormEduQRepository.saveAndFlush(risAppFormEduQ);

        // Get all the risAppFormEduQs
        restRisAppFormEduQMockMvc.perform(get("/api/risAppFormEduQs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(risAppFormEduQ.getId().intValue())))
                .andExpect(jsonPath("$.[*].examName").value(hasItem(DEFAULT_EXAM_NAME.toString())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].educationalInstitute").value(hasItem(DEFAULT_EDUCATIONAL_INSTITUTE.toString())))
                .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR)))
                .andExpect(jsonPath("$.[*].boardUniversity").value(hasItem(DEFAULT_BOARD_UNIVERSITY.toString())))
                .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())))
                .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE.toString())))
                .andExpect(jsonPath("$.[*].qouta").value(hasItem(DEFAULT_QOUTA.toString())));
    }

    @Test
    @Transactional
    public void getRisAppFormEduQ() throws Exception {
        // Initialize the database
        risAppFormEduQRepository.saveAndFlush(risAppFormEduQ);

        // Get the risAppFormEduQ
        restRisAppFormEduQMockMvc.perform(get("/api/risAppFormEduQs/{id}", risAppFormEduQ.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(risAppFormEduQ.getId().intValue()))
            .andExpect(jsonPath("$.examName").value(DEFAULT_EXAM_NAME.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.educationalInstitute").value(DEFAULT_EDUCATIONAL_INSTITUTE.toString()))
            .andExpect(jsonPath("$.passingYear").value(DEFAULT_PASSING_YEAR))
            .andExpect(jsonPath("$.boardUniversity").value(DEFAULT_BOARD_UNIVERSITY.toString()))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE.toString()))
            .andExpect(jsonPath("$.qouta").value(DEFAULT_QOUTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRisAppFormEduQ() throws Exception {
        // Get the risAppFormEduQ
        restRisAppFormEduQMockMvc.perform(get("/api/risAppFormEduQs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRisAppFormEduQ() throws Exception {
        // Initialize the database
        risAppFormEduQRepository.saveAndFlush(risAppFormEduQ);

		int databaseSizeBeforeUpdate = risAppFormEduQRepository.findAll().size();

        // Update the risAppFormEduQ
        risAppFormEduQ.setExamName(UPDATED_EXAM_NAME);
        risAppFormEduQ.setSubject(UPDATED_SUBJECT);
        risAppFormEduQ.setEducationalInstitute(UPDATED_EDUCATIONAL_INSTITUTE);
        risAppFormEduQ.setPassingYear(UPDATED_PASSING_YEAR);
        risAppFormEduQ.setBoardUniversity(UPDATED_BOARD_UNIVERSITY);
        risAppFormEduQ.setAdditionalInformation(UPDATED_ADDITIONAL_INFORMATION);
        risAppFormEduQ.setExperience(UPDATED_EXPERIENCE);
        risAppFormEduQ.setQouta(UPDATED_QOUTA);

        restRisAppFormEduQMockMvc.perform(put("/api/risAppFormEduQs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(risAppFormEduQ)))
                .andExpect(status().isOk());

        // Validate the RisAppFormEduQ in the database
        List<RisAppFormEduQ> risAppFormEduQs = risAppFormEduQRepository.findAll();
        assertThat(risAppFormEduQs).hasSize(databaseSizeBeforeUpdate);
        RisAppFormEduQ testRisAppFormEduQ = risAppFormEduQs.get(risAppFormEduQs.size() - 1);
        assertThat(testRisAppFormEduQ.getExamName()).isEqualTo(UPDATED_EXAM_NAME);
        assertThat(testRisAppFormEduQ.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testRisAppFormEduQ.getEducationalInstitute()).isEqualTo(UPDATED_EDUCATIONAL_INSTITUTE);
        assertThat(testRisAppFormEduQ.getPassingYear()).isEqualTo(UPDATED_PASSING_YEAR);
        assertThat(testRisAppFormEduQ.getBoardUniversity()).isEqualTo(UPDATED_BOARD_UNIVERSITY);
        assertThat(testRisAppFormEduQ.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
        assertThat(testRisAppFormEduQ.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testRisAppFormEduQ.getQouta()).isEqualTo(UPDATED_QOUTA);
    }

    @Test
    @Transactional
    public void deleteRisAppFormEduQ() throws Exception {
        // Initialize the database
        risAppFormEduQRepository.saveAndFlush(risAppFormEduQ);

		int databaseSizeBeforeDelete = risAppFormEduQRepository.findAll().size();

        // Get the risAppFormEduQ
        restRisAppFormEduQMockMvc.perform(delete("/api/risAppFormEduQs/{id}", risAppFormEduQ.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RisAppFormEduQ> risAppFormEduQs = risAppFormEduQRepository.findAll();
        assertThat(risAppFormEduQs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
