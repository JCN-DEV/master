package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlBookInfo;
import gov.step.app.repository.DlBookInfoRepository;
import gov.step.app.repository.search.DlBookInfoSearchRepository;

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
 * Test class for the DlBookInfoResource REST controller.
 *
 * @see DlBookInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlBookInfoResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_EDITION = "AAAAA";
    private static final String UPDATED_EDITION = "BBBBB";
    private static final String DEFAULT_ISBN_NO = "AAAAA";
    private static final String UPDATED_ISBN_NO = "BBBBB";
    private static final String DEFAULT_AUTHOR_NAME = "AAAAA";
    private static final String UPDATED_AUTHOR_NAME = "BBBBB";
    private static final String DEFAULT_COPYRIGHT = "AAAAA";
    private static final String UPDATED_COPYRIGHT = "BBBBB";
    private static final String DEFAULT_PUBLISHER_NAME = "AAAAA";
    private static final String UPDATED_PUBLISHER_NAME = "BBBBB";
    private static final String DEFAULT_LIBRARY_NAME = "AAAAA";
    private static final String UPDATED_LIBRARY_NAME = "BBBBB";
    private static final String DEFAULT_CALL_NO = "AAAAA";
    private static final String UPDATED_CALL_NO = "BBBBB";
    private static final String DEFAULT_TOTAL_COPIES = "AAAAA";
    private static final String UPDATED_TOTAL_COPIES = "BBBBB";

    private static final LocalDate DEFAULT_PURCHASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASE_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private DlBookInfoRepository dlBookInfoRepository;

    @Inject
    private DlBookInfoSearchRepository dlBookInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlBookInfoMockMvc;

    private DlBookInfo dlBookInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlBookInfoResource dlBookInfoResource = new DlBookInfoResource();
        ReflectionTestUtils.setField(dlBookInfoResource, "dlBookInfoRepository", dlBookInfoRepository);
        ReflectionTestUtils.setField(dlBookInfoResource, "dlBookInfoSearchRepository", dlBookInfoSearchRepository);
        this.restDlBookInfoMockMvc = MockMvcBuilders.standaloneSetup(dlBookInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlBookInfo = new DlBookInfo();
        dlBookInfo.setTitle(DEFAULT_TITLE);
        dlBookInfo.setEdition(DEFAULT_EDITION);
        dlBookInfo.setIsbnNo(DEFAULT_ISBN_NO);
        dlBookInfo.setAuthorName(DEFAULT_AUTHOR_NAME);
        dlBookInfo.setCopyright(DEFAULT_COPYRIGHT);
        dlBookInfo.setPublisherName(DEFAULT_PUBLISHER_NAME);
        dlBookInfo.setLibraryName(DEFAULT_LIBRARY_NAME);
        dlBookInfo.setCallNo(DEFAULT_CALL_NO);
        dlBookInfo.setTotalCopies(DEFAULT_TOTAL_COPIES);
        dlBookInfo.setPurchaseDate(DEFAULT_PURCHASE_DATE);
        dlBookInfo.setCreatedDate(DEFAULT_CREATED_DATE);
        dlBookInfo.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlBookInfo.setCreatedBy(DEFAULT_CREATED_BY);
        dlBookInfo.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlBookInfo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlBookInfo() throws Exception {
        int databaseSizeBeforeCreate = dlBookInfoRepository.findAll().size();

        // Create the DlBookInfo

        restDlBookInfoMockMvc.perform(post("/api/dlBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookInfo)))
                .andExpect(status().isCreated());

        // Validate the DlBookInfo in the database
        List<DlBookInfo> dlBookInfos = dlBookInfoRepository.findAll();
        assertThat(dlBookInfos).hasSize(databaseSizeBeforeCreate + 1);
        DlBookInfo testDlBookInfo = dlBookInfos.get(dlBookInfos.size() - 1);
        assertThat(testDlBookInfo.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDlBookInfo.getEdition()).isEqualTo(DEFAULT_EDITION);
        assertThat(testDlBookInfo.getIsbnNo()).isEqualTo(DEFAULT_ISBN_NO);
        assertThat(testDlBookInfo.getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME);
        assertThat(testDlBookInfo.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testDlBookInfo.getPublisherName()).isEqualTo(DEFAULT_PUBLISHER_NAME);
        assertThat(testDlBookInfo.getLibraryName()).isEqualTo(DEFAULT_LIBRARY_NAME);
        assertThat(testDlBookInfo.getCallNo()).isEqualTo(DEFAULT_CALL_NO);
        assertThat(testDlBookInfo.getTotalCopies()).isEqualTo(DEFAULT_TOTAL_COPIES);
        assertThat(testDlBookInfo.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testDlBookInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlBookInfo.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlBookInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlBookInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlBookInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlBookInfos() throws Exception {
        // Initialize the database
        dlBookInfoRepository.saveAndFlush(dlBookInfo);

        // Get all the dlBookInfos
        restDlBookInfoMockMvc.perform(get("/api/dlBookInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlBookInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION.toString())))
                .andExpect(jsonPath("$.[*].isbnNo").value(hasItem(DEFAULT_ISBN_NO.toString())))
                .andExpect(jsonPath("$.[*].authorName").value(hasItem(DEFAULT_AUTHOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT.toString())))
                .andExpect(jsonPath("$.[*].publisherName").value(hasItem(DEFAULT_PUBLISHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].libraryName").value(hasItem(DEFAULT_LIBRARY_NAME.toString())))
                .andExpect(jsonPath("$.[*].callNo").value(hasItem(DEFAULT_CALL_NO.toString())))
                .andExpect(jsonPath("$.[*].totalCopies").value(hasItem(DEFAULT_TOTAL_COPIES.toString())))
                .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlBookInfo() throws Exception {
        // Initialize the database
        dlBookInfoRepository.saveAndFlush(dlBookInfo);

        // Get the dlBookInfo
        restDlBookInfoMockMvc.perform(get("/api/dlBookInfos/{id}", dlBookInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlBookInfo.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.edition").value(DEFAULT_EDITION.toString()))
            .andExpect(jsonPath("$.isbnNo").value(DEFAULT_ISBN_NO.toString()))
            .andExpect(jsonPath("$.authorName").value(DEFAULT_AUTHOR_NAME.toString()))
            .andExpect(jsonPath("$.copyright").value(DEFAULT_COPYRIGHT.toString()))
            .andExpect(jsonPath("$.publisherName").value(DEFAULT_PUBLISHER_NAME.toString()))
            .andExpect(jsonPath("$.libraryName").value(DEFAULT_LIBRARY_NAME.toString()))
            .andExpect(jsonPath("$.callNo").value(DEFAULT_CALL_NO.toString()))
            .andExpect(jsonPath("$.totalCopies").value(DEFAULT_TOTAL_COPIES.toString()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlBookInfo() throws Exception {
        // Get the dlBookInfo
        restDlBookInfoMockMvc.perform(get("/api/dlBookInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlBookInfo() throws Exception {
        // Initialize the database
        dlBookInfoRepository.saveAndFlush(dlBookInfo);

		int databaseSizeBeforeUpdate = dlBookInfoRepository.findAll().size();

        // Update the dlBookInfo
        dlBookInfo.setTitle(UPDATED_TITLE);
        dlBookInfo.setEdition(UPDATED_EDITION);
        dlBookInfo.setIsbnNo(UPDATED_ISBN_NO);
        dlBookInfo.setAuthorName(UPDATED_AUTHOR_NAME);
        dlBookInfo.setCopyright(UPDATED_COPYRIGHT);
        dlBookInfo.setPublisherName(UPDATED_PUBLISHER_NAME);
        dlBookInfo.setLibraryName(UPDATED_LIBRARY_NAME);
        dlBookInfo.setCallNo(UPDATED_CALL_NO);
        dlBookInfo.setTotalCopies(UPDATED_TOTAL_COPIES);
        dlBookInfo.setPurchaseDate(UPDATED_PURCHASE_DATE);
        dlBookInfo.setCreatedDate(UPDATED_CREATED_DATE);
        dlBookInfo.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlBookInfo.setCreatedBy(UPDATED_CREATED_BY);
        dlBookInfo.setUpdatedBy(UPDATED_UPDATED_BY);
        dlBookInfo.setStatus(UPDATED_STATUS);

        restDlBookInfoMockMvc.perform(put("/api/dlBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlBookInfo)))
                .andExpect(status().isOk());

        // Validate the DlBookInfo in the database
        List<DlBookInfo> dlBookInfos = dlBookInfoRepository.findAll();
        assertThat(dlBookInfos).hasSize(databaseSizeBeforeUpdate);
        DlBookInfo testDlBookInfo = dlBookInfos.get(dlBookInfos.size() - 1);
        assertThat(testDlBookInfo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDlBookInfo.getEdition()).isEqualTo(UPDATED_EDITION);
        assertThat(testDlBookInfo.getIsbnNo()).isEqualTo(UPDATED_ISBN_NO);
        assertThat(testDlBookInfo.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testDlBookInfo.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testDlBookInfo.getPublisherName()).isEqualTo(UPDATED_PUBLISHER_NAME);
        assertThat(testDlBookInfo.getLibraryName()).isEqualTo(UPDATED_LIBRARY_NAME);
        assertThat(testDlBookInfo.getCallNo()).isEqualTo(UPDATED_CALL_NO);
        assertThat(testDlBookInfo.getTotalCopies()).isEqualTo(UPDATED_TOTAL_COPIES);
        assertThat(testDlBookInfo.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testDlBookInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlBookInfo.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlBookInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlBookInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlBookInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlBookInfo() throws Exception {
        // Initialize the database
        dlBookInfoRepository.saveAndFlush(dlBookInfo);

		int databaseSizeBeforeDelete = dlBookInfoRepository.findAll().size();

        // Get the dlBookInfo
        restDlBookInfoMockMvc.perform(delete("/api/dlBookInfos/{id}", dlBookInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlBookInfo> dlBookInfos = dlBookInfoRepository.findAll();
        assertThat(dlBookInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
