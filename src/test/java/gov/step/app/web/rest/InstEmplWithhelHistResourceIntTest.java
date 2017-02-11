package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmplWithhelHist;
import gov.step.app.repository.InstEmplWithhelHistRepository;
import gov.step.app.repository.search.InstEmplWithhelHistSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the InstEmplWithhelHistResource REST controller.
 *
 * @see InstEmplWithhelHistResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmplWithhelHistResourceIntTest {


    private static final BigDecimal DEFAULT_WITHHELD_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_WITHHELD_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_STOP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STOP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_REMARK = "AAAAA";
    private static final String UPDATED_REMARK = "BBBBB";

    @Inject
    private InstEmplWithhelHistRepository instEmplWithhelHistRepository;

    @Inject
    private InstEmplWithhelHistSearchRepository instEmplWithhelHistSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmplWithhelHistMockMvc;

    private InstEmplWithhelHist instEmplWithhelHist;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmplWithhelHistResource instEmplWithhelHistResource = new InstEmplWithhelHistResource();
        ReflectionTestUtils.setField(instEmplWithhelHistResource, "instEmplWithhelHistRepository", instEmplWithhelHistRepository);
        ReflectionTestUtils.setField(instEmplWithhelHistResource, "instEmplWithhelHistSearchRepository", instEmplWithhelHistSearchRepository);
        this.restInstEmplWithhelHistMockMvc = MockMvcBuilders.standaloneSetup(instEmplWithhelHistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmplWithhelHist = new InstEmplWithhelHist();
        instEmplWithhelHist.setWithheldAmount(DEFAULT_WITHHELD_AMOUNT);
        instEmplWithhelHist.setStartDate(DEFAULT_START_DATE);
        instEmplWithhelHist.setStopDate(DEFAULT_STOP_DATE);
        instEmplWithhelHist.setCreatedDate(DEFAULT_CREATED_DATE);
        instEmplWithhelHist.setModifiedDate(DEFAULT_MODIFIED_DATE);
        instEmplWithhelHist.setStatus(DEFAULT_STATUS);
        instEmplWithhelHist.setRemark(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createInstEmplWithhelHist() throws Exception {
        int databaseSizeBeforeCreate = instEmplWithhelHistRepository.findAll().size();

        // Create the InstEmplWithhelHist

        restInstEmplWithhelHistMockMvc.perform(post("/api/instEmplWithhelHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplWithhelHist)))
                .andExpect(status().isCreated());

        // Validate the InstEmplWithhelHist in the database
        List<InstEmplWithhelHist> instEmplWithhelHists = instEmplWithhelHistRepository.findAll();
        assertThat(instEmplWithhelHists).hasSize(databaseSizeBeforeCreate + 1);
        InstEmplWithhelHist testInstEmplWithhelHist = instEmplWithhelHists.get(instEmplWithhelHists.size() - 1);
        assertThat(testInstEmplWithhelHist.getWithheldAmount()).isEqualTo(DEFAULT_WITHHELD_AMOUNT);
        assertThat(testInstEmplWithhelHist.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testInstEmplWithhelHist.getStopDate()).isEqualTo(DEFAULT_STOP_DATE);
        assertThat(testInstEmplWithhelHist.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInstEmplWithhelHist.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testInstEmplWithhelHist.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInstEmplWithhelHist.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void checkWithheldAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplWithhelHistRepository.findAll().size();
        // set the field null
        instEmplWithhelHist.setWithheldAmount(null);

        // Create the InstEmplWithhelHist, which fails.

        restInstEmplWithhelHistMockMvc.perform(post("/api/instEmplWithhelHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplWithhelHist)))
                .andExpect(status().isBadRequest());

        List<InstEmplWithhelHist> instEmplWithhelHists = instEmplWithhelHistRepository.findAll();
        assertThat(instEmplWithhelHists).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplWithhelHistRepository.findAll().size();
        // set the field null
        instEmplWithhelHist.setStartDate(null);

        // Create the InstEmplWithhelHist, which fails.

        restInstEmplWithhelHistMockMvc.perform(post("/api/instEmplWithhelHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplWithhelHist)))
                .andExpect(status().isBadRequest());

        List<InstEmplWithhelHist> instEmplWithhelHists = instEmplWithhelHistRepository.findAll();
        assertThat(instEmplWithhelHists).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStopDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplWithhelHistRepository.findAll().size();
        // set the field null
        instEmplWithhelHist.setStopDate(null);

        // Create the InstEmplWithhelHist, which fails.

        restInstEmplWithhelHistMockMvc.perform(post("/api/instEmplWithhelHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplWithhelHist)))
                .andExpect(status().isBadRequest());

        List<InstEmplWithhelHist> instEmplWithhelHists = instEmplWithhelHistRepository.findAll();
        assertThat(instEmplWithhelHists).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplWithhelHistRepository.findAll().size();
        // set the field null
        instEmplWithhelHist.setCreatedDate(null);

        // Create the InstEmplWithhelHist, which fails.

        restInstEmplWithhelHistMockMvc.perform(post("/api/instEmplWithhelHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplWithhelHist)))
                .andExpect(status().isBadRequest());

        List<InstEmplWithhelHist> instEmplWithhelHists = instEmplWithhelHistRepository.findAll();
        assertThat(instEmplWithhelHists).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModifiedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instEmplWithhelHistRepository.findAll().size();
        // set the field null
        instEmplWithhelHist.setModifiedDate(null);

        // Create the InstEmplWithhelHist, which fails.

        restInstEmplWithhelHistMockMvc.perform(post("/api/instEmplWithhelHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplWithhelHist)))
                .andExpect(status().isBadRequest());

        List<InstEmplWithhelHist> instEmplWithhelHists = instEmplWithhelHistRepository.findAll();
        assertThat(instEmplWithhelHists).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstEmplWithhelHists() throws Exception {
        // Initialize the database
        instEmplWithhelHistRepository.saveAndFlush(instEmplWithhelHist);

        // Get all the instEmplWithhelHists
        restInstEmplWithhelHistMockMvc.perform(get("/api/instEmplWithhelHists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmplWithhelHist.getId().intValue())))
                .andExpect(jsonPath("$.[*].withheldAmount").value(hasItem(DEFAULT_WITHHELD_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].stopDate").value(hasItem(DEFAULT_STOP_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getInstEmplWithhelHist() throws Exception {
        // Initialize the database
        instEmplWithhelHistRepository.saveAndFlush(instEmplWithhelHist);

        // Get the instEmplWithhelHist
        restInstEmplWithhelHistMockMvc.perform(get("/api/instEmplWithhelHists/{id}", instEmplWithhelHist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmplWithhelHist.getId().intValue()))
            .andExpect(jsonPath("$.withheldAmount").value(DEFAULT_WITHHELD_AMOUNT.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.stopDate").value(DEFAULT_STOP_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmplWithhelHist() throws Exception {
        // Get the instEmplWithhelHist
        restInstEmplWithhelHistMockMvc.perform(get("/api/instEmplWithhelHists/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmplWithhelHist() throws Exception {
        // Initialize the database
        instEmplWithhelHistRepository.saveAndFlush(instEmplWithhelHist);

		int databaseSizeBeforeUpdate = instEmplWithhelHistRepository.findAll().size();

        // Update the instEmplWithhelHist
        instEmplWithhelHist.setWithheldAmount(UPDATED_WITHHELD_AMOUNT);
        instEmplWithhelHist.setStartDate(UPDATED_START_DATE);
        instEmplWithhelHist.setStopDate(UPDATED_STOP_DATE);
        instEmplWithhelHist.setCreatedDate(UPDATED_CREATED_DATE);
        instEmplWithhelHist.setModifiedDate(UPDATED_MODIFIED_DATE);
        instEmplWithhelHist.setStatus(UPDATED_STATUS);
        instEmplWithhelHist.setRemark(UPDATED_REMARK);

        restInstEmplWithhelHistMockMvc.perform(put("/api/instEmplWithhelHists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmplWithhelHist)))
                .andExpect(status().isOk());

        // Validate the InstEmplWithhelHist in the database
        List<InstEmplWithhelHist> instEmplWithhelHists = instEmplWithhelHistRepository.findAll();
        assertThat(instEmplWithhelHists).hasSize(databaseSizeBeforeUpdate);
        InstEmplWithhelHist testInstEmplWithhelHist = instEmplWithhelHists.get(instEmplWithhelHists.size() - 1);
        assertThat(testInstEmplWithhelHist.getWithheldAmount()).isEqualTo(UPDATED_WITHHELD_AMOUNT);
        assertThat(testInstEmplWithhelHist.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testInstEmplWithhelHist.getStopDate()).isEqualTo(UPDATED_STOP_DATE);
        assertThat(testInstEmplWithhelHist.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInstEmplWithhelHist.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testInstEmplWithhelHist.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInstEmplWithhelHist.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void deleteInstEmplWithhelHist() throws Exception {
        // Initialize the database
        instEmplWithhelHistRepository.saveAndFlush(instEmplWithhelHist);

		int databaseSizeBeforeDelete = instEmplWithhelHistRepository.findAll().size();

        // Get the instEmplWithhelHist
        restInstEmplWithhelHistMockMvc.perform(delete("/api/instEmplWithhelHists/{id}", instEmplWithhelHist.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmplWithhelHist> instEmplWithhelHists = instEmplWithhelHistRepository.findAll();
        assertThat(instEmplWithhelHists).hasSize(databaseSizeBeforeDelete - 1);
    }
}
