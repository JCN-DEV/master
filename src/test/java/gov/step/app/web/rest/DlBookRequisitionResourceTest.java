package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlBookRequisition;
import gov.step.app.repository.DlBookRequisitionRepository;
import gov.step.app.repository.search.DlBookRequisitionSearchRepository;

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
 * Test class for the DlBookRequisitionResource REST controller.
 *
 * @see DlBookRequisitionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlBookRequisitionResourceTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_EDITION = "AAAAA";
    private static final String UPDATED_EDITION = "BBBBB";
    private static final String DEFAULT_AUTHOR_NAME = "AAAAA";
    private static final String UPDATED_AUTHOR_NAME = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

//    private static final Long DEFAULT_CREATE_BY = 1;
//    private static final Long UPDATED_CREATE_BY = 2;

//    private static final Integer DEFAULT_UPDATE_BY = 1;
//    private static final Integer UPDATED_UPDATE_BY = 2;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private DlBookRequisitionRepository dlBookRequisitionRepository;

    @Inject
    private DlBookRequisitionSearchRepository dlBookRequisitionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlBookRequisitionMockMvc;

    private DlBookRequisition dlBookRequisition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlBookRequisitionResource dlBookRequisitionResource = new DlBookRequisitionResource();
        ReflectionTestUtils.setField(dlBookRequisitionResource, "dlBookRequisitionRepository", dlBookRequisitionRepository);
        ReflectionTestUtils.setField(dlBookRequisitionResource, "dlBookRequisitionSearchRepository", dlBookRequisitionSearchRepository);
        this.restDlBookRequisitionMockMvc = MockMvcBuilders.standaloneSetup(dlBookRequisitionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlBookRequisition = new DlBookRequisition();
        dlBookRequisition.setTitle(DEFAULT_TITLE);
        dlBookRequisition.setEdition(DEFAULT_EDITION);
        dlBookRequisition.setAuthorName(DEFAULT_AUTHOR_NAME);
        dlBookRequisition.setCreateDate(DEFAULT_CREATE_DATE);
        dlBookRequisition.setUpdateDate(DEFAULT_UPDATE_DATE);
//        dlBookRequisition.setCreateBy(DEFAULT_CREATE_BY);
//        dlBookRequisition.setUpdateBy(DEFAULT_UPDATE_BY);
        dlBookRequisition.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlBookRequisition() throws Exception {
        int databaseSizeBeforeCreate = dlBookRequisitionRepository.findAll().size();

        // Create the DlBookRequisition

        restDlBookRequisitionMockMvc.perform(post("/api/dlBookRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookRequisition)))
                .andExpect(status().isCreated());

        // Validate the DlBookRequisition in the database
        List<DlBookRequisition> dlBookRequisitions = dlBookRequisitionRepository.findAll();
        assertThat(dlBookRequisitions).hasSize(databaseSizeBeforeCreate + 1);
        DlBookRequisition testDlBookRequisition = dlBookRequisitions.get(dlBookRequisitions.size() - 1);
        assertThat(testDlBookRequisition.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDlBookRequisition.getEdition()).isEqualTo(DEFAULT_EDITION);
        assertThat(testDlBookRequisition.getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME);
        assertThat(testDlBookRequisition.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDlBookRequisition.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
//        assertThat(testDlBookRequisition.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
//        assertThat(testDlBookRequisition.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testDlBookRequisition.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlBookRequisitions() throws Exception {
        // Initialize the database
        dlBookRequisitionRepository.saveAndFlush(dlBookRequisition);

        // Get all the dlBookRequisitions
        restDlBookRequisitionMockMvc.perform(get("/api/dlBookRequisitions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlBookRequisition.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION.toString())))
                .andExpect(jsonPath("$.[*].authorName").value(hasItem(DEFAULT_AUTHOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
//                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
//                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlBookRequisition() throws Exception {
        // Initialize the database
        dlBookRequisitionRepository.saveAndFlush(dlBookRequisition);

        // Get the dlBookRequisition
        restDlBookRequisitionMockMvc.perform(get("/api/dlBookRequisitions/{id}", dlBookRequisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlBookRequisition.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.edition").value(DEFAULT_EDITION.toString()))
            .andExpect(jsonPath("$.authorName").value(DEFAULT_AUTHOR_NAME.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
//            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY))
//            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlBookRequisition() throws Exception {
        // Get the dlBookRequisition
        restDlBookRequisitionMockMvc.perform(get("/api/dlBookRequisitions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlBookRequisition() throws Exception {
        // Initialize the database
        dlBookRequisitionRepository.saveAndFlush(dlBookRequisition);

		int databaseSizeBeforeUpdate = dlBookRequisitionRepository.findAll().size();

        // Update the dlBookRequisition
        dlBookRequisition.setTitle(UPDATED_TITLE);
        dlBookRequisition.setEdition(UPDATED_EDITION);
        dlBookRequisition.setAuthorName(UPDATED_AUTHOR_NAME);
        dlBookRequisition.setCreateDate(UPDATED_CREATE_DATE);
        dlBookRequisition.setUpdateDate(UPDATED_UPDATE_DATE);
//        dlBookRequisition.setCreateBy(UPDATED_CREATE_BY);
//        dlBookRequisition.setUpdateBy(UPDATED_UPDATE_BY);
        dlBookRequisition.setStatus(UPDATED_STATUS);

        restDlBookRequisitionMockMvc.perform(put("/api/dlBookRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookRequisition)))
                .andExpect(status().isOk());

        // Validate the DlBookRequisition in the database
        List<DlBookRequisition> dlBookRequisitions = dlBookRequisitionRepository.findAll();
        assertThat(dlBookRequisitions).hasSize(databaseSizeBeforeUpdate);
        DlBookRequisition testDlBookRequisition = dlBookRequisitions.get(dlBookRequisitions.size() - 1);
        assertThat(testDlBookRequisition.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDlBookRequisition.getEdition()).isEqualTo(UPDATED_EDITION);
        assertThat(testDlBookRequisition.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testDlBookRequisition.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDlBookRequisition.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
//        assertThat(testDlBookRequisition.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
//        assertThat(testDlBookRequisition.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testDlBookRequisition.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlBookRequisition() throws Exception {
        // Initialize the database
        dlBookRequisitionRepository.saveAndFlush(dlBookRequisition);

		int databaseSizeBeforeDelete = dlBookRequisitionRepository.findAll().size();

        // Get the dlBookRequisition
        restDlBookRequisitionMockMvc.perform(delete("/api/dlBookRequisitions/{id}", dlBookRequisition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlBookRequisition> dlBookRequisitions = dlBookRequisitionRepository.findAll();
        assertThat(dlBookRequisitions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
