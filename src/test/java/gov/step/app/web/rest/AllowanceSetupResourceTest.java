package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.AllowanceSetup;
import gov.step.app.repository.AllowanceSetupRepository;
import gov.step.app.repository.search.AllowanceSetupSearchRepository;

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
 * Test class for the AllowanceSetupResource REST controller.
 *
 * @see AllowanceSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AllowanceSetupResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

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

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private AllowanceSetupRepository allowanceSetupRepository;

    @Inject
    private AllowanceSetupSearchRepository allowanceSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAllowanceSetupMockMvc;

    private AllowanceSetup allowanceSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AllowanceSetupResource allowanceSetupResource = new AllowanceSetupResource();
        ReflectionTestUtils.setField(allowanceSetupResource, "allowanceSetupRepository", allowanceSetupRepository);
        ReflectionTestUtils.setField(allowanceSetupResource, "allowanceSetupSearchRepository", allowanceSetupSearchRepository);
        this.restAllowanceSetupMockMvc = MockMvcBuilders.standaloneSetup(allowanceSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        allowanceSetup = new AllowanceSetup();
        allowanceSetup.setName(DEFAULT_NAME);
        allowanceSetup.setStatus(DEFAULT_STATUS);
        allowanceSetup.setCreateBy(DEFAULT_CREATE_BY);
        allowanceSetup.setCreateDate(DEFAULT_CREATE_DATE);
        allowanceSetup.setUpdateBy(DEFAULT_UPDATE_BY);
        allowanceSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        allowanceSetup.setRemarks(DEFAULT_REMARKS);
        allowanceSetup.setEffectiveDate(DEFAULT_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void createAllowanceSetup() throws Exception {
        int databaseSizeBeforeCreate = allowanceSetupRepository.findAll().size();

        // Create the AllowanceSetup

        restAllowanceSetupMockMvc.perform(post("/api/allowanceSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(allowanceSetup)))
                .andExpect(status().isCreated());

        // Validate the AllowanceSetup in the database
        List<AllowanceSetup> allowanceSetups = allowanceSetupRepository.findAll();
        assertThat(allowanceSetups).hasSize(databaseSizeBeforeCreate + 1);
        AllowanceSetup testAllowanceSetup = allowanceSetups.get(allowanceSetups.size() - 1);
        assertThat(testAllowanceSetup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAllowanceSetup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAllowanceSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testAllowanceSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testAllowanceSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testAllowanceSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testAllowanceSetup.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAllowanceSetup.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = allowanceSetupRepository.findAll().size();
        // set the field null
        allowanceSetup.setName(null);

        // Create the AllowanceSetup, which fails.

        restAllowanceSetupMockMvc.perform(post("/api/allowanceSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(allowanceSetup)))
                .andExpect(status().isBadRequest());

        List<AllowanceSetup> allowanceSetups = allowanceSetupRepository.findAll();
        assertThat(allowanceSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAllowanceSetups() throws Exception {
        // Initialize the database
        allowanceSetupRepository.saveAndFlush(allowanceSetup);

        // Get all the allowanceSetups
        restAllowanceSetupMockMvc.perform(get("/api/allowanceSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(allowanceSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAllowanceSetup() throws Exception {
        // Initialize the database
        allowanceSetupRepository.saveAndFlush(allowanceSetup);

        // Get the allowanceSetup
        restAllowanceSetupMockMvc.perform(get("/api/allowanceSetups/{id}", allowanceSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(allowanceSetup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAllowanceSetup() throws Exception {
        // Get the allowanceSetup
        restAllowanceSetupMockMvc.perform(get("/api/allowanceSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllowanceSetup() throws Exception {
        // Initialize the database
        allowanceSetupRepository.saveAndFlush(allowanceSetup);

		int databaseSizeBeforeUpdate = allowanceSetupRepository.findAll().size();

        // Update the allowanceSetup
        allowanceSetup.setName(UPDATED_NAME);
        allowanceSetup.setStatus(UPDATED_STATUS);
        allowanceSetup.setCreateBy(UPDATED_CREATE_BY);
        allowanceSetup.setCreateDate(UPDATED_CREATE_DATE);
        allowanceSetup.setUpdateBy(UPDATED_UPDATE_BY);
        allowanceSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        allowanceSetup.setRemarks(UPDATED_REMARKS);
        allowanceSetup.setEffectiveDate(UPDATED_EFFECTIVE_DATE);

        restAllowanceSetupMockMvc.perform(put("/api/allowanceSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(allowanceSetup)))
                .andExpect(status().isOk());

        // Validate the AllowanceSetup in the database
        List<AllowanceSetup> allowanceSetups = allowanceSetupRepository.findAll();
        assertThat(allowanceSetups).hasSize(databaseSizeBeforeUpdate);
        AllowanceSetup testAllowanceSetup = allowanceSetups.get(allowanceSetups.size() - 1);
        assertThat(testAllowanceSetup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAllowanceSetup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAllowanceSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testAllowanceSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testAllowanceSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testAllowanceSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testAllowanceSetup.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAllowanceSetup.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void deleteAllowanceSetup() throws Exception {
        // Initialize the database
        allowanceSetupRepository.saveAndFlush(allowanceSetup);

		int databaseSizeBeforeDelete = allowanceSetupRepository.findAll().size();

        // Get the allowanceSetup
        restAllowanceSetupMockMvc.perform(delete("/api/allowanceSetups/{id}", allowanceSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AllowanceSetup> allowanceSetups = allowanceSetupRepository.findAll();
        assertThat(allowanceSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
