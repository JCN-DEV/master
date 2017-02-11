package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.Result;
import gov.step.app.repository.ResultRepository;
import gov.step.app.repository.search.ResultSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ResultResource REST controller.
 *
 * @see ResultResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ResultResourceTest {


    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final BigDecimal DEFAULT_RESULT = new BigDecimal(1);
    private static final BigDecimal UPDATED_RESULT = new BigDecimal(2);
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    @Inject
    private ResultRepository resultRepository;

    @Inject
    private ResultSearchRepository resultSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResultMockMvc;

    private Result result;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResultResource resultResource = new ResultResource();
        ReflectionTestUtils.setField(resultResource, "resultRepository", resultRepository);
        ReflectionTestUtils.setField(resultResource, "resultSearchRepository", resultSearchRepository);
        this.restResultMockMvc = MockMvcBuilders.standaloneSetup(resultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        result = new Result();
        result.setYear(DEFAULT_YEAR);
        result.setResult(DEFAULT_RESULT);
        result.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createResult() throws Exception {
        int databaseSizeBeforeCreate = resultRepository.findAll().size();

        // Create the Result

        restResultMockMvc.perform(post("/api/results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(result)))
                .andExpect(status().isCreated());

        // Validate the Result in the database
        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(databaseSizeBeforeCreate + 1);
        Result testResult = results.get(results.size() - 1);
        assertThat(testResult.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testResult.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testResult.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultRepository.findAll().size();
        // set the field null
        result.setYear(null);

        // Create the Result, which fails.

        restResultMockMvc.perform(post("/api/results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(result)))
                .andExpect(status().isBadRequest());

        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultRepository.findAll().size();
        // set the field null
        result.setResult(null);

        // Create the Result, which fails.

        restResultMockMvc.perform(post("/api/results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(result)))
                .andExpect(status().isBadRequest());

        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResults() throws Exception {
        // Initialize the database
        resultRepository.saveAndFlush(result);

        // Get all the results
        restResultMockMvc.perform(get("/api/results"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(result.getId().intValue())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
                .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.intValue())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getResult() throws Exception {
        // Initialize the database
        resultRepository.saveAndFlush(result);

        // Get the result
        restResultMockMvc.perform(get("/api/results/{id}", result.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(result.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResult() throws Exception {
        // Get the result
        restResultMockMvc.perform(get("/api/results/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResult() throws Exception {
        // Initialize the database
        resultRepository.saveAndFlush(result);

		int databaseSizeBeforeUpdate = resultRepository.findAll().size();

        // Update the result
        result.setYear(UPDATED_YEAR);
        result.setResult(UPDATED_RESULT);
        result.setRemarks(UPDATED_REMARKS);

        restResultMockMvc.perform(put("/api/results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(result)))
                .andExpect(status().isOk());

        // Validate the Result in the database
        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(databaseSizeBeforeUpdate);
        Result testResult = results.get(results.size() - 1);
        assertThat(testResult.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testResult.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testResult.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteResult() throws Exception {
        // Initialize the database
        resultRepository.saveAndFlush(result);

		int databaseSizeBeforeDelete = resultRepository.findAll().size();

        // Get the result
        restResultMockMvc.perform(delete("/api/results/{id}", result.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(databaseSizeBeforeDelete - 1);
    }
}
