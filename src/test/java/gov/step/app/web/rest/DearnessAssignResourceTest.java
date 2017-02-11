package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DearnessAssign;
import gov.step.app.repository.DearnessAssignRepository;
import gov.step.app.repository.search.DearnessAssignSearchRepository;

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
 * Test class for the DearnessAssignResource REST controller.
 *
 * @see DearnessAssignResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DearnessAssignResourceTest {


    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_STOP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STOP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    @Inject
    private DearnessAssignRepository dearnessAssignRepository;

    @Inject
    private DearnessAssignSearchRepository dearnessAssignSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDearnessAssignMockMvc;

    private DearnessAssign dearnessAssign;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DearnessAssignResource dearnessAssignResource = new DearnessAssignResource();
        ReflectionTestUtils.setField(dearnessAssignResource, "dearnessAssignRepository", dearnessAssignRepository);
        ReflectionTestUtils.setField(dearnessAssignResource, "dearnessAssignSearchRepository", dearnessAssignSearchRepository);
        this.restDearnessAssignMockMvc = MockMvcBuilders.standaloneSetup(dearnessAssignResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dearnessAssign = new DearnessAssign();
        dearnessAssign.setAmount(DEFAULT_AMOUNT);
        dearnessAssign.setEffectiveDate(DEFAULT_EFFECTIVE_DATE);
        dearnessAssign.setStopDate(DEFAULT_STOP_DATE);
        dearnessAssign.setStatus(DEFAULT_STATUS);
        dearnessAssign.setCreateBy(DEFAULT_CREATE_BY);
        dearnessAssign.setCreateDate(DEFAULT_CREATE_DATE);
        dearnessAssign.setUpdateBy(DEFAULT_UPDATE_BY);
        dearnessAssign.setUpdateDate(DEFAULT_UPDATE_DATE);
        dearnessAssign.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createDearnessAssign() throws Exception {
        int databaseSizeBeforeCreate = dearnessAssignRepository.findAll().size();

        // Create the DearnessAssign

        restDearnessAssignMockMvc.perform(post("/api/dearnessAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dearnessAssign)))
                .andExpect(status().isCreated());

        // Validate the DearnessAssign in the database
        List<DearnessAssign> dearnessAssigns = dearnessAssignRepository.findAll();
        assertThat(dearnessAssigns).hasSize(databaseSizeBeforeCreate + 1);
        DearnessAssign testDearnessAssign = dearnessAssigns.get(dearnessAssigns.size() - 1);
        assertThat(testDearnessAssign.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDearnessAssign.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testDearnessAssign.getStopDate()).isEqualTo(DEFAULT_STOP_DATE);
        assertThat(testDearnessAssign.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDearnessAssign.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testDearnessAssign.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDearnessAssign.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testDearnessAssign.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testDearnessAssign.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = dearnessAssignRepository.findAll().size();
        // set the field null
        dearnessAssign.setAmount(null);

        // Create the DearnessAssign, which fails.

        restDearnessAssignMockMvc.perform(post("/api/dearnessAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dearnessAssign)))
                .andExpect(status().isBadRequest());

        List<DearnessAssign> dearnessAssigns = dearnessAssignRepository.findAll();
        assertThat(dearnessAssigns).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDearnessAssigns() throws Exception {
        // Initialize the database
        dearnessAssignRepository.saveAndFlush(dearnessAssign);

        // Get all the dearnessAssigns
        restDearnessAssignMockMvc.perform(get("/api/dearnessAssigns"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dearnessAssign.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].stopDate").value(hasItem(DEFAULT_STOP_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getDearnessAssign() throws Exception {
        // Initialize the database
        dearnessAssignRepository.saveAndFlush(dearnessAssign);

        // Get the dearnessAssign
        restDearnessAssignMockMvc.perform(get("/api/dearnessAssigns/{id}", dearnessAssign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dearnessAssign.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.stopDate").value(DEFAULT_STOP_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDearnessAssign() throws Exception {
        // Get the dearnessAssign
        restDearnessAssignMockMvc.perform(get("/api/dearnessAssigns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDearnessAssign() throws Exception {
        // Initialize the database
        dearnessAssignRepository.saveAndFlush(dearnessAssign);

		int databaseSizeBeforeUpdate = dearnessAssignRepository.findAll().size();

        // Update the dearnessAssign
        dearnessAssign.setAmount(UPDATED_AMOUNT);
        dearnessAssign.setEffectiveDate(UPDATED_EFFECTIVE_DATE);
        dearnessAssign.setStopDate(UPDATED_STOP_DATE);
        dearnessAssign.setStatus(UPDATED_STATUS);
        dearnessAssign.setCreateBy(UPDATED_CREATE_BY);
        dearnessAssign.setCreateDate(UPDATED_CREATE_DATE);
        dearnessAssign.setUpdateBy(UPDATED_UPDATE_BY);
        dearnessAssign.setUpdateDate(UPDATED_UPDATE_DATE);
        dearnessAssign.setRemarks(UPDATED_REMARKS);

        restDearnessAssignMockMvc.perform(put("/api/dearnessAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dearnessAssign)))
                .andExpect(status().isOk());

        // Validate the DearnessAssign in the database
        List<DearnessAssign> dearnessAssigns = dearnessAssignRepository.findAll();
        assertThat(dearnessAssigns).hasSize(databaseSizeBeforeUpdate);
        DearnessAssign testDearnessAssign = dearnessAssigns.get(dearnessAssigns.size() - 1);
        assertThat(testDearnessAssign.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDearnessAssign.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testDearnessAssign.getStopDate()).isEqualTo(UPDATED_STOP_DATE);
        assertThat(testDearnessAssign.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDearnessAssign.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testDearnessAssign.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDearnessAssign.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testDearnessAssign.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testDearnessAssign.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteDearnessAssign() throws Exception {
        // Initialize the database
        dearnessAssignRepository.saveAndFlush(dearnessAssign);

		int databaseSizeBeforeDelete = dearnessAssignRepository.findAll().size();

        // Get the dearnessAssign
        restDearnessAssignMockMvc.perform(delete("/api/dearnessAssigns/{id}", dearnessAssign.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DearnessAssign> dearnessAssigns = dearnessAssignRepository.findAll();
        assertThat(dearnessAssigns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
