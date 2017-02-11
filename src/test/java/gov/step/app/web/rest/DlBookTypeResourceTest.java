package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlBookType;
import gov.step.app.repository.DlBookTypeRepository;
import gov.step.app.repository.search.DlBookTypeSearchRepository;

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
 * Test class for the DlBookTypeResource REST controller.
 *
 * @see DlBookTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlBookTypeResourceTest {

    private static final String DEFAULT_TYPE_NAME = "AAAAA";
    private static final String UPDATED_TYPE_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private DlBookTypeRepository dlBookTypeRepository;

    @Inject
    private DlBookTypeSearchRepository dlBookTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlBookTypeMockMvc;

    private DlBookType dlBookType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlBookTypeResource dlBookTypeResource = new DlBookTypeResource();
        ReflectionTestUtils.setField(dlBookTypeResource, "dlBookTypeRepository", dlBookTypeRepository);
        ReflectionTestUtils.setField(dlBookTypeResource, "dlBookTypeSearchRepository", dlBookTypeSearchRepository);
        this.restDlBookTypeMockMvc = MockMvcBuilders.standaloneSetup(dlBookTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlBookType = new DlBookType();
        dlBookType.setTypeName(DEFAULT_TYPE_NAME);
        dlBookType.setDescription(DEFAULT_DESCRIPTION);
        dlBookType.setCreatedDate(DEFAULT_CREATED_DATE);
        dlBookType.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlBookType.setCreatedBy(DEFAULT_CREATED_BY);
        dlBookType.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlBookType.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlBookType() throws Exception {
        int databaseSizeBeforeCreate = dlBookTypeRepository.findAll().size();

        // Create the DlBookType

        restDlBookTypeMockMvc.perform(post("/api/dlBookTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookType)))
                .andExpect(status().isCreated());

        // Validate the DlBookType in the database
        List<DlBookType> dlBookTypes = dlBookTypeRepository.findAll();
        assertThat(dlBookTypes).hasSize(databaseSizeBeforeCreate + 1);
        DlBookType testDlBookType = dlBookTypes.get(dlBookTypes.size() - 1);
        assertThat(testDlBookType.getTypeName()).isEqualTo(DEFAULT_TYPE_NAME);
        assertThat(testDlBookType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDlBookType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlBookType.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlBookType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlBookType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlBookType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlBookTypeRepository.findAll().size();
        // set the field null
        dlBookType.setTypeName(null);

        // Create the DlBookType, which fails.

        restDlBookTypeMockMvc.perform(post("/api/dlBookTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookType)))
                .andExpect(status().isBadRequest());

        List<DlBookType> dlBookTypes = dlBookTypeRepository.findAll();
        assertThat(dlBookTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlBookTypeRepository.findAll().size();
        // set the field null
        dlBookType.setDescription(null);

        // Create the DlBookType, which fails.

        restDlBookTypeMockMvc.perform(post("/api/dlBookTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookType)))
                .andExpect(status().isBadRequest());

        List<DlBookType> dlBookTypes = dlBookTypeRepository.findAll();
        assertThat(dlBookTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDlBookTypes() throws Exception {
        // Initialize the database
        dlBookTypeRepository.saveAndFlush(dlBookType);

        // Get all the dlBookTypes
        restDlBookTypeMockMvc.perform(get("/api/dlBookTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlBookType.getId().intValue())))
                .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlBookType() throws Exception {
        // Initialize the database
        dlBookTypeRepository.saveAndFlush(dlBookType);

        // Get the dlBookType
        restDlBookTypeMockMvc.perform(get("/api/dlBookTypes/{id}", dlBookType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlBookType.getId().intValue()))
            .andExpect(jsonPath("$.typeName").value(DEFAULT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlBookType() throws Exception {
        // Get the dlBookType
        restDlBookTypeMockMvc.perform(get("/api/dlBookTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlBookType() throws Exception {
        // Initialize the database
        dlBookTypeRepository.saveAndFlush(dlBookType);

		int databaseSizeBeforeUpdate = dlBookTypeRepository.findAll().size();

        // Update the dlBookType
        dlBookType.setTypeName(UPDATED_TYPE_NAME);
        dlBookType.setDescription(UPDATED_DESCRIPTION);
        dlBookType.setCreatedDate(UPDATED_CREATED_DATE);
        dlBookType.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlBookType.setCreatedBy(UPDATED_CREATED_BY);
        dlBookType.setUpdatedBy(UPDATED_UPDATED_BY);
        dlBookType.setStatus(UPDATED_STATUS);

        restDlBookTypeMockMvc.perform(put("/api/dlBookTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookType)))
                .andExpect(status().isOk());

        // Validate the DlBookType in the database
        List<DlBookType> dlBookTypes = dlBookTypeRepository.findAll();
        assertThat(dlBookTypes).hasSize(databaseSizeBeforeUpdate);
        DlBookType testDlBookType = dlBookTypes.get(dlBookTypes.size() - 1);
        assertThat(testDlBookType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testDlBookType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDlBookType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlBookType.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlBookType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlBookType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlBookType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlBookType() throws Exception {
        // Initialize the database
        dlBookTypeRepository.saveAndFlush(dlBookType);

		int databaseSizeBeforeDelete = dlBookTypeRepository.findAll().size();

        // Get the dlBookType
        restDlBookTypeMockMvc.perform(delete("/api/dlBookTypes/{id}", dlBookType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlBookType> dlBookTypes = dlBookTypeRepository.findAll();
        assertThat(dlBookTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
