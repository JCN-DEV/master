package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstCategory;
import gov.step.app.repository.InstCategoryRepository;
import gov.step.app.repository.search.InstCategorySearchRepository;

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
 * Test class for the InstCategoryResource REST controller.
 *
 * @see InstCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstCategoryResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_P_STATUS = false;
    private static final Boolean UPDATED_P_STATUS = true;

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
    private InstCategoryRepository instCategoryRepository;

    @Inject
    private InstCategorySearchRepository instCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstCategoryMockMvc;

    private InstCategory instCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstCategoryResource instCategoryResource = new InstCategoryResource();
        ReflectionTestUtils.setField(instCategoryResource, "instCategoryRepository", instCategoryRepository);
        ReflectionTestUtils.setField(instCategoryResource, "instCategorySearchRepository", instCategorySearchRepository);
        this.restInstCategoryMockMvc = MockMvcBuilders.standaloneSetup(instCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instCategory = new InstCategory();
        instCategory.setCode(DEFAULT_CODE);
        instCategory.setName(DEFAULT_NAME);
        instCategory.setDescription(DEFAULT_DESCRIPTION);
        instCategory.setpStatus(DEFAULT_P_STATUS);
        instCategory.setCreatedDate(DEFAULT_CREATED_DATE);
        instCategory.setUpdatedDate(DEFAULT_UPDATED_DATE);
        instCategory.setCreatedBy(DEFAULT_CREATED_BY);
        instCategory.setUpdatedBy(DEFAULT_UPDATED_BY);
        instCategory.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstCategory() throws Exception {
        int databaseSizeBeforeCreate = instCategoryRepository.findAll().size();

        // Create the InstCategory

        restInstCategoryMockMvc.perform(post("/api/instCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instCategory)))
                .andExpect(status().isCreated());

        // Validate the InstCategory in the database
        List<InstCategory> instCategorys = instCategoryRepository.findAll();
        assertThat(instCategorys).hasSize(databaseSizeBeforeCreate + 1);
        InstCategory testInstCategory = instCategorys.get(instCategorys.size() - 1);
        assertThat(testInstCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInstCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInstCategory.getpStatus()).isEqualTo(DEFAULT_P_STATUS);
        assertThat(testInstCategory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInstCategory.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testInstCategory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInstCategory.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testInstCategory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstCategorys() throws Exception {
        // Initialize the database
        instCategoryRepository.saveAndFlush(instCategory);

        // Get all the instCategorys
        restInstCategoryMockMvc.perform(get("/api/instCategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].pStatus").value(hasItem(DEFAULT_P_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstCategory() throws Exception {
        // Initialize the database
        instCategoryRepository.saveAndFlush(instCategory);

        // Get the instCategory
        restInstCategoryMockMvc.perform(get("/api/instCategorys/{id}", instCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.pStatus").value(DEFAULT_P_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstCategory() throws Exception {
        // Get the instCategory
        restInstCategoryMockMvc.perform(get("/api/instCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstCategory() throws Exception {
        // Initialize the database
        instCategoryRepository.saveAndFlush(instCategory);

		int databaseSizeBeforeUpdate = instCategoryRepository.findAll().size();

        // Update the instCategory
        instCategory.setCode(UPDATED_CODE);
        instCategory.setName(UPDATED_NAME);
        instCategory.setDescription(UPDATED_DESCRIPTION);
        instCategory.setpStatus(UPDATED_P_STATUS);
        instCategory.setCreatedDate(UPDATED_CREATED_DATE);
        instCategory.setUpdatedDate(UPDATED_UPDATED_DATE);
        instCategory.setCreatedBy(UPDATED_CREATED_BY);
        instCategory.setUpdatedBy(UPDATED_UPDATED_BY);
        instCategory.setStatus(UPDATED_STATUS);

        restInstCategoryMockMvc.perform(put("/api/instCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instCategory)))
                .andExpect(status().isOk());

        // Validate the InstCategory in the database
        List<InstCategory> instCategorys = instCategoryRepository.findAll();
        assertThat(instCategorys).hasSize(databaseSizeBeforeUpdate);
        InstCategory testInstCategory = instCategorys.get(instCategorys.size() - 1);
        assertThat(testInstCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInstCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInstCategory.getpStatus()).isEqualTo(UPDATED_P_STATUS);
        assertThat(testInstCategory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInstCategory.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testInstCategory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInstCategory.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInstCategory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstCategory() throws Exception {
        // Initialize the database
        instCategoryRepository.saveAndFlush(instCategory);

		int databaseSizeBeforeDelete = instCategoryRepository.findAll().size();

        // Get the instCategory
        restInstCategoryMockMvc.perform(delete("/api/instCategorys/{id}", instCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstCategory> instCategorys = instCategoryRepository.findAll();
        assertThat(instCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
