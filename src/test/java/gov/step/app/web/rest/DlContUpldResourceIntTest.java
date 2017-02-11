package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlContUpld;
import gov.step.app.repository.DlContUpldRepository;
import gov.step.app.repository.search.DlContUpldSearchRepository;

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
 * Test class for the DlContUpldResource REST controller.
 *
 * @see DlContUpldResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlContUpldResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_AUTH_NAME = "AAAAA";
    private static final String UPDATED_AUTH_NAME = "BBBBB";
    private static final String DEFAULT_EDITION = "AAAAA";
    private static final String UPDATED_EDITION = "BBBBB";
    private static final String DEFAULT_ISBN_NO = "AAAAA";
    private static final String UPDATED_ISBN_NO = "BBBBB";
    private static final String DEFAULT_COPYRIGHT = "AAAAA";
    private static final String UPDATED_COPYRIGHT = "BBBBB";
    private static final String DEFAULT_PUBLISHER = "AAAAA";
    private static final String UPDATED_PUBLISHER = "BBBBB";

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
    private DlContUpldRepository dlContUpldRepository;

    @Inject
    private DlContUpldSearchRepository dlContUpldSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlContUpldMockMvc;

    private DlContUpld dlContUpld;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlContUpldResource dlContUpldResource = new DlContUpldResource();
        ReflectionTestUtils.setField(dlContUpldResource, "dlContUpldRepository", dlContUpldRepository);
        ReflectionTestUtils.setField(dlContUpldResource, "dlContUpldSearchRepository", dlContUpldSearchRepository);
        this.restDlContUpldMockMvc = MockMvcBuilders.standaloneSetup(dlContUpldResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlContUpld = new DlContUpld();
        dlContUpld.setCode(DEFAULT_CODE);
        dlContUpld.setAuthName(DEFAULT_AUTH_NAME);
        dlContUpld.setEdition(DEFAULT_EDITION);
        dlContUpld.setIsbnNo(DEFAULT_ISBN_NO);
        dlContUpld.setCopyright(DEFAULT_COPYRIGHT);
        dlContUpld.setPublisher(DEFAULT_PUBLISHER);
        dlContUpld.setCreatedDate(DEFAULT_CREATED_DATE);
        dlContUpld.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlContUpld.setCreatedBy(DEFAULT_CREATED_BY);
        dlContUpld.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlContUpld.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlContUpld() throws Exception {
        int databaseSizeBeforeCreate = dlContUpldRepository.findAll().size();

        // Create the DlContUpld

        restDlContUpldMockMvc.perform(post("/api/dlContUplds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContUpld)))
                .andExpect(status().isCreated());

        // Validate the DlContUpld in the database
        List<DlContUpld> dlContUplds = dlContUpldRepository.findAll();
        assertThat(dlContUplds).hasSize(databaseSizeBeforeCreate + 1);
        DlContUpld testDlContUpld = dlContUplds.get(dlContUplds.size() - 1);
        assertThat(testDlContUpld.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDlContUpld.getAuthName()).isEqualTo(DEFAULT_AUTH_NAME);
        assertThat(testDlContUpld.getEdition()).isEqualTo(DEFAULT_EDITION);
        assertThat(testDlContUpld.getIsbnNo()).isEqualTo(DEFAULT_ISBN_NO);
        assertThat(testDlContUpld.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testDlContUpld.getPublisher()).isEqualTo(DEFAULT_PUBLISHER);
        assertThat(testDlContUpld.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlContUpld.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlContUpld.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlContUpld.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlContUpld.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlContUplds() throws Exception {
        // Initialize the database
        dlContUpldRepository.saveAndFlush(dlContUpld);

        // Get all the dlContUplds
        restDlContUpldMockMvc.perform(get("/api/dlContUplds"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlContUpld.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].authName").value(hasItem(DEFAULT_AUTH_NAME.toString())))
                .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION.toString())))
                .andExpect(jsonPath("$.[*].isbnNo").value(hasItem(DEFAULT_ISBN_NO.toString())))
                .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT.toString())))
                .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlContUpld() throws Exception {
        // Initialize the database
        dlContUpldRepository.saveAndFlush(dlContUpld);

        // Get the dlContUpld
        restDlContUpldMockMvc.perform(get("/api/dlContUplds/{id}", dlContUpld.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlContUpld.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.authName").value(DEFAULT_AUTH_NAME.toString()))
            .andExpect(jsonPath("$.edition").value(DEFAULT_EDITION.toString()))
            .andExpect(jsonPath("$.isbnNo").value(DEFAULT_ISBN_NO.toString()))
            .andExpect(jsonPath("$.copyright").value(DEFAULT_COPYRIGHT.toString()))
            .andExpect(jsonPath("$.publisher").value(DEFAULT_PUBLISHER.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlContUpld() throws Exception {
        // Get the dlContUpld
        restDlContUpldMockMvc.perform(get("/api/dlContUplds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlContUpld() throws Exception {
        // Initialize the database
        dlContUpldRepository.saveAndFlush(dlContUpld);

		int databaseSizeBeforeUpdate = dlContUpldRepository.findAll().size();

        // Update the dlContUpld
        dlContUpld.setCode(UPDATED_CODE);
        dlContUpld.setAuthName(UPDATED_AUTH_NAME);
        dlContUpld.setEdition(UPDATED_EDITION);
        dlContUpld.setIsbnNo(UPDATED_ISBN_NO);
        dlContUpld.setCopyright(UPDATED_COPYRIGHT);
        dlContUpld.setPublisher(UPDATED_PUBLISHER);
        dlContUpld.setCreatedDate(UPDATED_CREATED_DATE);
        dlContUpld.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlContUpld.setCreatedBy(UPDATED_CREATED_BY);
        dlContUpld.setUpdatedBy(UPDATED_UPDATED_BY);
        dlContUpld.setStatus(UPDATED_STATUS);

        restDlContUpldMockMvc.perform(put("/api/dlContUplds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlContUpld)))
                .andExpect(status().isOk());

        // Validate the DlContUpld in the database
        List<DlContUpld> dlContUplds = dlContUpldRepository.findAll();
        assertThat(dlContUplds).hasSize(databaseSizeBeforeUpdate);
        DlContUpld testDlContUpld = dlContUplds.get(dlContUplds.size() - 1);
        assertThat(testDlContUpld.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDlContUpld.getAuthName()).isEqualTo(UPDATED_AUTH_NAME);
        assertThat(testDlContUpld.getEdition()).isEqualTo(UPDATED_EDITION);
        assertThat(testDlContUpld.getIsbnNo()).isEqualTo(UPDATED_ISBN_NO);
        assertThat(testDlContUpld.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testDlContUpld.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testDlContUpld.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlContUpld.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlContUpld.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlContUpld.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlContUpld.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlContUpld() throws Exception {
        // Initialize the database
        dlContUpldRepository.saveAndFlush(dlContUpld);

		int databaseSizeBeforeDelete = dlContUpldRepository.findAll().size();

        // Get the dlContUpld
        restDlContUpldMockMvc.perform(delete("/api/dlContUplds/{id}", dlContUpld.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlContUpld> dlContUplds = dlContUpldRepository.findAll();
        assertThat(dlContUplds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
