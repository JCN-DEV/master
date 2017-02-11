package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MiscConfigSetup;
import gov.step.app.repository.MiscConfigSetupRepository;
import gov.step.app.repository.search.MiscConfigSetupSearchRepository;

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

import gov.step.app.domain.enumeration.miscConfigDataType;

/**
 * Test class for the MiscConfigSetupResource REST controller.
 *
 * @see MiscConfigSetupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MiscConfigSetupResourceTest {

    private static final String DEFAULT_PROPERTY_NAME = "AAAAA";
    private static final String UPDATED_PROPERTY_NAME = "BBBBB";
    private static final String DEFAULT_PROPERTY_TITLE = "AAAAA";
    private static final String UPDATED_PROPERTY_TITLE = "BBBBB";
    private static final String DEFAULT_PROPERTY_VALUE = "AAAAA";
    private static final String UPDATED_PROPERTY_VALUE = "BBBBB";


private static final miscConfigDataType DEFAULT_PROPERTY_DATA_TYPE = miscConfigDataType.String;
    private static final miscConfigDataType UPDATED_PROPERTY_DATA_TYPE = miscConfigDataType.Number;
    private static final String DEFAULT_PROPERTY_VALUE_MAX = "AAAAA";
    private static final String UPDATED_PROPERTY_VALUE_MAX = "BBBBB";
    private static final String DEFAULT_PROPERTY_DESC = "AAAAA";
    private static final String UPDATED_PROPERTY_DESC = "BBBBB";

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
    private MiscConfigSetupRepository miscConfigSetupRepository;

    @Inject
    private MiscConfigSetupSearchRepository miscConfigSetupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMiscConfigSetupMockMvc;

    private MiscConfigSetup miscConfigSetup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MiscConfigSetupResource miscConfigSetupResource = new MiscConfigSetupResource();
        ReflectionTestUtils.setField(miscConfigSetupResource, "miscConfigSetupRepository", miscConfigSetupRepository);
        ReflectionTestUtils.setField(miscConfigSetupResource, "miscConfigSetupSearchRepository", miscConfigSetupSearchRepository);
        this.restMiscConfigSetupMockMvc = MockMvcBuilders.standaloneSetup(miscConfigSetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        miscConfigSetup = new MiscConfigSetup();
        miscConfigSetup.setPropertyName(DEFAULT_PROPERTY_NAME);
        miscConfigSetup.setPropertyTitle(DEFAULT_PROPERTY_TITLE);
        miscConfigSetup.setPropertyValue(DEFAULT_PROPERTY_VALUE);
        miscConfigSetup.setPropertyDataType(DEFAULT_PROPERTY_DATA_TYPE);
        miscConfigSetup.setPropertyValueMax(DEFAULT_PROPERTY_VALUE_MAX);
        miscConfigSetup.setPropertyDesc(DEFAULT_PROPERTY_DESC);
        miscConfigSetup.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        miscConfigSetup.setCreateDate(DEFAULT_CREATE_DATE);
        miscConfigSetup.setCreateBy(DEFAULT_CREATE_BY);
        miscConfigSetup.setUpdateDate(DEFAULT_UPDATE_DATE);
        miscConfigSetup.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createMiscConfigSetup() throws Exception {
        int databaseSizeBeforeCreate = miscConfigSetupRepository.findAll().size();

        // Create the MiscConfigSetup

        restMiscConfigSetupMockMvc.perform(post("/api/miscConfigSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(miscConfigSetup)))
                .andExpect(status().isCreated());

        // Validate the MiscConfigSetup in the database
        List<MiscConfigSetup> miscConfigSetups = miscConfigSetupRepository.findAll();
        assertThat(miscConfigSetups).hasSize(databaseSizeBeforeCreate + 1);
        MiscConfigSetup testMiscConfigSetup = miscConfigSetups.get(miscConfigSetups.size() - 1);
        assertThat(testMiscConfigSetup.getPropertyName()).isEqualTo(DEFAULT_PROPERTY_NAME);
        assertThat(testMiscConfigSetup.getPropertyTitle()).isEqualTo(DEFAULT_PROPERTY_TITLE);
        assertThat(testMiscConfigSetup.getPropertyValue()).isEqualTo(DEFAULT_PROPERTY_VALUE);
        assertThat(testMiscConfigSetup.getPropertyDataType()).isEqualTo(DEFAULT_PROPERTY_DATA_TYPE);
        assertThat(testMiscConfigSetup.getPropertyValueMax()).isEqualTo(DEFAULT_PROPERTY_VALUE_MAX);
        assertThat(testMiscConfigSetup.getPropertyDesc()).isEqualTo(DEFAULT_PROPERTY_DESC);
        assertThat(testMiscConfigSetup.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testMiscConfigSetup.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testMiscConfigSetup.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testMiscConfigSetup.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testMiscConfigSetup.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkPropertyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = miscConfigSetupRepository.findAll().size();
        // set the field null
        miscConfigSetup.setPropertyName(null);

        // Create the MiscConfigSetup, which fails.

        restMiscConfigSetupMockMvc.perform(post("/api/miscConfigSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(miscConfigSetup)))
                .andExpect(status().isBadRequest());

        List<MiscConfigSetup> miscConfigSetups = miscConfigSetupRepository.findAll();
        assertThat(miscConfigSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropertyTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = miscConfigSetupRepository.findAll().size();
        // set the field null
        miscConfigSetup.setPropertyTitle(null);

        // Create the MiscConfigSetup, which fails.

        restMiscConfigSetupMockMvc.perform(post("/api/miscConfigSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(miscConfigSetup)))
                .andExpect(status().isBadRequest());

        List<MiscConfigSetup> miscConfigSetups = miscConfigSetupRepository.findAll();
        assertThat(miscConfigSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropertyValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = miscConfigSetupRepository.findAll().size();
        // set the field null
        miscConfigSetup.setPropertyValue(null);

        // Create the MiscConfigSetup, which fails.

        restMiscConfigSetupMockMvc.perform(post("/api/miscConfigSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(miscConfigSetup)))
                .andExpect(status().isBadRequest());

        List<MiscConfigSetup> miscConfigSetups = miscConfigSetupRepository.findAll();
        assertThat(miscConfigSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropertyDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = miscConfigSetupRepository.findAll().size();
        // set the field null
        miscConfigSetup.setPropertyDataType(null);

        // Create the MiscConfigSetup, which fails.

        restMiscConfigSetupMockMvc.perform(post("/api/miscConfigSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(miscConfigSetup)))
                .andExpect(status().isBadRequest());

        List<MiscConfigSetup> miscConfigSetups = miscConfigSetupRepository.findAll();
        assertThat(miscConfigSetups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMiscConfigSetups() throws Exception {
        // Initialize the database
        miscConfigSetupRepository.saveAndFlush(miscConfigSetup);

        // Get all the miscConfigSetups
        restMiscConfigSetupMockMvc.perform(get("/api/miscConfigSetups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(miscConfigSetup.getId().intValue())))
                .andExpect(jsonPath("$.[*].propertyName").value(hasItem(DEFAULT_PROPERTY_NAME.toString())))
                .andExpect(jsonPath("$.[*].propertyTitle").value(hasItem(DEFAULT_PROPERTY_TITLE.toString())))
                .andExpect(jsonPath("$.[*].propertyValue").value(hasItem(DEFAULT_PROPERTY_VALUE.toString())))
                .andExpect(jsonPath("$.[*].propertyDataType").value(hasItem(DEFAULT_PROPERTY_DATA_TYPE.toString())))
                .andExpect(jsonPath("$.[*].propertyValueMax").value(hasItem(DEFAULT_PROPERTY_VALUE_MAX.toString())))
                .andExpect(jsonPath("$.[*].propertyDesc").value(hasItem(DEFAULT_PROPERTY_DESC.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getMiscConfigSetup() throws Exception {
        // Initialize the database
        miscConfigSetupRepository.saveAndFlush(miscConfigSetup);

        // Get the miscConfigSetup
        restMiscConfigSetupMockMvc.perform(get("/api/miscConfigSetups/{id}", miscConfigSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(miscConfigSetup.getId().intValue()))
            .andExpect(jsonPath("$.propertyName").value(DEFAULT_PROPERTY_NAME.toString()))
            .andExpect(jsonPath("$.propertyTitle").value(DEFAULT_PROPERTY_TITLE.toString()))
            .andExpect(jsonPath("$.propertyValue").value(DEFAULT_PROPERTY_VALUE.toString()))
            .andExpect(jsonPath("$.propertyDataType").value(DEFAULT_PROPERTY_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.propertyValueMax").value(DEFAULT_PROPERTY_VALUE_MAX.toString()))
            .andExpect(jsonPath("$.propertyDesc").value(DEFAULT_PROPERTY_DESC.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMiscConfigSetup() throws Exception {
        // Get the miscConfigSetup
        restMiscConfigSetupMockMvc.perform(get("/api/miscConfigSetups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMiscConfigSetup() throws Exception {
        // Initialize the database
        miscConfigSetupRepository.saveAndFlush(miscConfigSetup);

		int databaseSizeBeforeUpdate = miscConfigSetupRepository.findAll().size();

        // Update the miscConfigSetup
        miscConfigSetup.setPropertyName(UPDATED_PROPERTY_NAME);
        miscConfigSetup.setPropertyTitle(UPDATED_PROPERTY_TITLE);
        miscConfigSetup.setPropertyValue(UPDATED_PROPERTY_VALUE);
        miscConfigSetup.setPropertyDataType(UPDATED_PROPERTY_DATA_TYPE);
        miscConfigSetup.setPropertyValueMax(UPDATED_PROPERTY_VALUE_MAX);
        miscConfigSetup.setPropertyDesc(UPDATED_PROPERTY_DESC);
        miscConfigSetup.setActiveStatus(UPDATED_ACTIVE_STATUS);
        miscConfigSetup.setCreateDate(UPDATED_CREATE_DATE);
        miscConfigSetup.setCreateBy(UPDATED_CREATE_BY);
        miscConfigSetup.setUpdateDate(UPDATED_UPDATE_DATE);
        miscConfigSetup.setUpdateBy(UPDATED_UPDATE_BY);

        restMiscConfigSetupMockMvc.perform(put("/api/miscConfigSetups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(miscConfigSetup)))
                .andExpect(status().isOk());

        // Validate the MiscConfigSetup in the database
        List<MiscConfigSetup> miscConfigSetups = miscConfigSetupRepository.findAll();
        assertThat(miscConfigSetups).hasSize(databaseSizeBeforeUpdate);
        MiscConfigSetup testMiscConfigSetup = miscConfigSetups.get(miscConfigSetups.size() - 1);
        assertThat(testMiscConfigSetup.getPropertyName()).isEqualTo(UPDATED_PROPERTY_NAME);
        assertThat(testMiscConfigSetup.getPropertyTitle()).isEqualTo(UPDATED_PROPERTY_TITLE);
        assertThat(testMiscConfigSetup.getPropertyValue()).isEqualTo(UPDATED_PROPERTY_VALUE);
        assertThat(testMiscConfigSetup.getPropertyDataType()).isEqualTo(UPDATED_PROPERTY_DATA_TYPE);
        assertThat(testMiscConfigSetup.getPropertyValueMax()).isEqualTo(UPDATED_PROPERTY_VALUE_MAX);
        assertThat(testMiscConfigSetup.getPropertyDesc()).isEqualTo(UPDATED_PROPERTY_DESC);
        assertThat(testMiscConfigSetup.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testMiscConfigSetup.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testMiscConfigSetup.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testMiscConfigSetup.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testMiscConfigSetup.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteMiscConfigSetup() throws Exception {
        // Initialize the database
        miscConfigSetupRepository.saveAndFlush(miscConfigSetup);

		int databaseSizeBeforeDelete = miscConfigSetupRepository.findAll().size();

        // Get the miscConfigSetup
        restMiscConfigSetupMockMvc.perform(delete("/api/miscConfigSetups/{id}", miscConfigSetup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MiscConfigSetup> miscConfigSetups = miscConfigSetupRepository.findAll();
        assertThat(miscConfigSetups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
