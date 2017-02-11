package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlBookCat;
import gov.step.app.repository.DlBookCatRepository;
import gov.step.app.repository.search.DlBookCatSearchRepository;

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
 * Test class for the DlBookCatResource REST controller.
 *
 * @see DlBookCatResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlBookCatResourceTest {

    private static final String DEFAULT_CAT_NAME = "AAAAA";
    private static final String UPDATED_CAT_NAME = "BBBBB";
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
    private DlBookCatRepository dlBookCatRepository;

    @Inject
    private DlBookCatSearchRepository dlBookCatSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlBookCatMockMvc;

    private DlBookCat dlBookCat;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlBookCatResource dlBookCatResource = new DlBookCatResource();
        ReflectionTestUtils.setField(dlBookCatResource, "dlBookCatRepository", dlBookCatRepository);
        ReflectionTestUtils.setField(dlBookCatResource, "dlBookCatSearchRepository", dlBookCatSearchRepository);
        this.restDlBookCatMockMvc = MockMvcBuilders.standaloneSetup(dlBookCatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlBookCat = new DlBookCat();
        dlBookCat.setCatName(DEFAULT_CAT_NAME);
        dlBookCat.setDescription(DEFAULT_DESCRIPTION);
        dlBookCat.setCreatedDate(DEFAULT_CREATED_DATE);
        dlBookCat.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlBookCat.setCreatedBy(DEFAULT_CREATED_BY);
        dlBookCat.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlBookCat.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlBookCat() throws Exception {
        int databaseSizeBeforeCreate = dlBookCatRepository.findAll().size();

        // Create the DlBookCat

        restDlBookCatMockMvc.perform(post("/api/dlBookCats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookCat)))
                .andExpect(status().isCreated());

        // Validate the DlBookCat in the database
        List<DlBookCat> dlBookCats = dlBookCatRepository.findAll();
        assertThat(dlBookCats).hasSize(databaseSizeBeforeCreate + 1);
        DlBookCat testDlBookCat = dlBookCats.get(dlBookCats.size() - 1);
        assertThat(testDlBookCat.getCatName()).isEqualTo(DEFAULT_CAT_NAME);
        assertThat(testDlBookCat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDlBookCat.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlBookCat.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlBookCat.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlBookCat.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlBookCat.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCatNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlBookCatRepository.findAll().size();
        // set the field null
        dlBookCat.setCatName(null);

        // Create the DlBookCat, which fails.

        restDlBookCatMockMvc.perform(post("/api/dlBookCats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookCat)))
                .andExpect(status().isBadRequest());

        List<DlBookCat> dlBookCats = dlBookCatRepository.findAll();
        assertThat(dlBookCats).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlBookCatRepository.findAll().size();
        // set the field null
        dlBookCat.setDescription(null);

        // Create the DlBookCat, which fails.

        restDlBookCatMockMvc.perform(post("/api/dlBookCats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookCat)))
                .andExpect(status().isBadRequest());

        List<DlBookCat> dlBookCats = dlBookCatRepository.findAll();
        assertThat(dlBookCats).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDlBookCats() throws Exception {
        // Initialize the database
        dlBookCatRepository.saveAndFlush(dlBookCat);

        // Get all the dlBookCats
        restDlBookCatMockMvc.perform(get("/api/dlBookCats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlBookCat.getId().intValue())))
                .andExpect(jsonPath("$.[*].catName").value(hasItem(DEFAULT_CAT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlBookCat() throws Exception {
        // Initialize the database
        dlBookCatRepository.saveAndFlush(dlBookCat);

        // Get the dlBookCat
        restDlBookCatMockMvc.perform(get("/api/dlBookCats/{id}", dlBookCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlBookCat.getId().intValue()))
            .andExpect(jsonPath("$.catName").value(DEFAULT_CAT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlBookCat() throws Exception {
        // Get the dlBookCat
        restDlBookCatMockMvc.perform(get("/api/dlBookCats/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlBookCat() throws Exception {
        // Initialize the database
        dlBookCatRepository.saveAndFlush(dlBookCat);

		int databaseSizeBeforeUpdate = dlBookCatRepository.findAll().size();

        // Update the dlBookCat
        dlBookCat.setCatName(UPDATED_CAT_NAME);
        dlBookCat.setDescription(UPDATED_DESCRIPTION);
        dlBookCat.setCreatedDate(UPDATED_CREATED_DATE);
        dlBookCat.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlBookCat.setCreatedBy(UPDATED_CREATED_BY);
        dlBookCat.setUpdatedBy(UPDATED_UPDATED_BY);
        dlBookCat.setStatus(UPDATED_STATUS);

        restDlBookCatMockMvc.perform(put("/api/dlBookCats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookCat)))
                .andExpect(status().isOk());

        // Validate the DlBookCat in the database
        List<DlBookCat> dlBookCats = dlBookCatRepository.findAll();
        assertThat(dlBookCats).hasSize(databaseSizeBeforeUpdate);
        DlBookCat testDlBookCat = dlBookCats.get(dlBookCats.size() - 1);
        assertThat(testDlBookCat.getCatName()).isEqualTo(UPDATED_CAT_NAME);
        assertThat(testDlBookCat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDlBookCat.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlBookCat.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlBookCat.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlBookCat.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlBookCat.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlBookCat() throws Exception {
        // Initialize the database
        dlBookCatRepository.saveAndFlush(dlBookCat);

		int databaseSizeBeforeDelete = dlBookCatRepository.findAll().size();

        // Get the dlBookCat
        restDlBookCatMockMvc.perform(delete("/api/dlBookCats/{id}", dlBookCat.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlBookCat> dlBookCats = dlBookCatRepository.findAll();
        assertThat(dlBookCats).hasSize(databaseSizeBeforeDelete - 1);
    }
}
