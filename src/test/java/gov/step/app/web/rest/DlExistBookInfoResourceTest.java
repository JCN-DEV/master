package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlExistBookInfo;
import gov.step.app.repository.DlExistBookInfoRepository;
import gov.step.app.repository.search.DlExistBookInfoSearchRepository;

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
 * Test class for the DlExistBookInfoResource REST controller.
 *
 * @see DlExistBookInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlExistBookInfoResourceTest {

    private static final String DEFAULT_LIBRARY_NAME = "AAAAA";
    private static final String UPDATED_LIBRARY_NAME = "BBBBB";
    private static final String DEFAULT_SELF_NO = "AAAAA";
    private static final String UPDATED_SELF_NO = "BBBBB";
    private static final String DEFAULT_RAW_NO = "AAAAA";
    private static final String UPDATED_RAW_NO = "BBBBB";
    private static final String DEFAULT_BOOK_NAME = "AAAAA";
    private static final String UPDATED_BOOK_NAME = "BBBBB";
    private static final String DEFAULT_PUBLISHER_NAME = "AAAAA";
    private static final String UPDATED_PUBLISHER_NAME = "BBBBB";
    private static final String DEFAULT_COPYRIGHT = "AAAAA";
    private static final String UPDATED_COPYRIGHT = "BBBBB";
    private static final String DEFAULT_ISBN_NO = "AAAAA";
    private static final String UPDATED_ISBN_NO = "BBBBB";

    private static final Integer DEFAULT_TOTAL_COPIES = 1;
    private static final Integer UPDATED_TOTAL_COPIES = 2;
    private static final String DEFAULT_UPLOAD_URL = "AAAAA";
    private static final String UPDATED_UPLOAD_URL = "BBBBB";

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
    private DlExistBookInfoRepository dlExistBookInfoRepository;

    @Inject
    private DlExistBookInfoSearchRepository dlExistBookInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlExistBookInfoMockMvc;

    private DlExistBookInfo dlExistBookInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlExistBookInfoResource dlExistBookInfoResource = new DlExistBookInfoResource();
        ReflectionTestUtils.setField(dlExistBookInfoResource, "dlExistBookInfoRepository", dlExistBookInfoRepository);
        ReflectionTestUtils.setField(dlExistBookInfoResource, "dlExistBookInfoSearchRepository", dlExistBookInfoSearchRepository);
        this.restDlExistBookInfoMockMvc = MockMvcBuilders.standaloneSetup(dlExistBookInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlExistBookInfo = new DlExistBookInfo();
        dlExistBookInfo.setLibraryName(DEFAULT_LIBRARY_NAME);
        dlExistBookInfo.setSelfNo(DEFAULT_SELF_NO);
        dlExistBookInfo.setRawNo(DEFAULT_RAW_NO);
        dlExistBookInfo.setBookName(DEFAULT_BOOK_NAME);
        dlExistBookInfo.setPublisherName(DEFAULT_PUBLISHER_NAME);
        dlExistBookInfo.setCopyright(DEFAULT_COPYRIGHT);
        dlExistBookInfo.setIsbnNo(DEFAULT_ISBN_NO);
        dlExistBookInfo.setTotalCopies(DEFAULT_TOTAL_COPIES);
        dlExistBookInfo.setUploadUrl(DEFAULT_UPLOAD_URL);
        dlExistBookInfo.setCreatedDate(DEFAULT_CREATED_DATE);
        dlExistBookInfo.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlExistBookInfo.setCreatedBy(DEFAULT_CREATED_BY);
        dlExistBookInfo.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlExistBookInfo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlExistBookInfo() throws Exception {
        int databaseSizeBeforeCreate = dlExistBookInfoRepository.findAll().size();

        // Create the DlExistBookInfo

        restDlExistBookInfoMockMvc.perform(post("/api/dlExistBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistBookInfo)))
                .andExpect(status().isCreated());

        // Validate the DlExistBookInfo in the database
        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeCreate + 1);
        DlExistBookInfo testDlExistBookInfo = dlExistBookInfos.get(dlExistBookInfos.size() - 1);
        assertThat(testDlExistBookInfo.getLibraryName()).isEqualTo(DEFAULT_LIBRARY_NAME);
        assertThat(testDlExistBookInfo.getSelfNo()).isEqualTo(DEFAULT_SELF_NO);
        assertThat(testDlExistBookInfo.getRawNo()).isEqualTo(DEFAULT_RAW_NO);
        assertThat(testDlExistBookInfo.getBookName()).isEqualTo(DEFAULT_BOOK_NAME);
        assertThat(testDlExistBookInfo.getPublisherName()).isEqualTo(DEFAULT_PUBLISHER_NAME);
        assertThat(testDlExistBookInfo.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testDlExistBookInfo.getIsbnNo()).isEqualTo(DEFAULT_ISBN_NO);
        assertThat(testDlExistBookInfo.getTotalCopies()).isEqualTo(DEFAULT_TOTAL_COPIES);
        assertThat(testDlExistBookInfo.getUploadUrl()).isEqualTo(DEFAULT_UPLOAD_URL);
        assertThat(testDlExistBookInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlExistBookInfo.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlExistBookInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlExistBookInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlExistBookInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkLibraryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistBookInfoRepository.findAll().size();
        // set the field null
        dlExistBookInfo.setLibraryName(null);

        // Create the DlExistBookInfo, which fails.

        restDlExistBookInfoMockMvc.perform(post("/api/dlExistBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistBookInfo)))
                .andExpect(status().isBadRequest());

        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSelfNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistBookInfoRepository.findAll().size();
        // set the field null
        dlExistBookInfo.setSelfNo(null);

        // Create the DlExistBookInfo, which fails.

        restDlExistBookInfoMockMvc.perform(post("/api/dlExistBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistBookInfo)))
                .andExpect(status().isBadRequest());

        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRawNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistBookInfoRepository.findAll().size();
        // set the field null
        dlExistBookInfo.setRawNo(null);

        // Create the DlExistBookInfo, which fails.

        restDlExistBookInfoMockMvc.perform(post("/api/dlExistBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistBookInfo)))
                .andExpect(status().isBadRequest());

        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBookNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistBookInfoRepository.findAll().size();
        // set the field null
        dlExistBookInfo.setBookName(null);

        // Create the DlExistBookInfo, which fails.

        restDlExistBookInfoMockMvc.perform(post("/api/dlExistBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistBookInfo)))
                .andExpect(status().isBadRequest());

        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublisherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistBookInfoRepository.findAll().size();
        // set the field null
        dlExistBookInfo.setPublisherName(null);

        // Create the DlExistBookInfo, which fails.

        restDlExistBookInfoMockMvc.perform(post("/api/dlExistBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistBookInfo)))
                .andExpect(status().isBadRequest());

        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCopyrightIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistBookInfoRepository.findAll().size();
        // set the field null
        dlExistBookInfo.setCopyright(null);

        // Create the DlExistBookInfo, which fails.

        restDlExistBookInfoMockMvc.perform(post("/api/dlExistBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistBookInfo)))
                .andExpect(status().isBadRequest());

        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsbnNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistBookInfoRepository.findAll().size();
        // set the field null
        dlExistBookInfo.setIsbnNo(null);

        // Create the DlExistBookInfo, which fails.

        restDlExistBookInfoMockMvc.perform(post("/api/dlExistBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistBookInfo)))
                .andExpect(status().isBadRequest());

        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalCopiesIsRequired() throws Exception {
        int databaseSizeBeforeTest = dlExistBookInfoRepository.findAll().size();
        // set the field null
        dlExistBookInfo.setTotalCopies(null);

        // Create the DlExistBookInfo, which fails.

        restDlExistBookInfoMockMvc.perform(post("/api/dlExistBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistBookInfo)))
                .andExpect(status().isBadRequest());

        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDlExistBookInfos() throws Exception {
        // Initialize the database
        dlExistBookInfoRepository.saveAndFlush(dlExistBookInfo);

        // Get all the dlExistBookInfos
        restDlExistBookInfoMockMvc.perform(get("/api/dlExistBookInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlExistBookInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].libraryName").value(hasItem(DEFAULT_LIBRARY_NAME.toString())))
                .andExpect(jsonPath("$.[*].selfNo").value(hasItem(DEFAULT_SELF_NO.toString())))
                .andExpect(jsonPath("$.[*].rawNo").value(hasItem(DEFAULT_RAW_NO.toString())))
                .andExpect(jsonPath("$.[*].bookName").value(hasItem(DEFAULT_BOOK_NAME.toString())))
                .andExpect(jsonPath("$.[*].publisherName").value(hasItem(DEFAULT_PUBLISHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT.toString())))
                .andExpect(jsonPath("$.[*].isbnNo").value(hasItem(DEFAULT_ISBN_NO.toString())))
                .andExpect(jsonPath("$.[*].totalCopies").value(hasItem(DEFAULT_TOTAL_COPIES)))
                .andExpect(jsonPath("$.[*].uploadUrl").value(hasItem(DEFAULT_UPLOAD_URL.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlExistBookInfo() throws Exception {
        // Initialize the database
        dlExistBookInfoRepository.saveAndFlush(dlExistBookInfo);

        // Get the dlExistBookInfo
        restDlExistBookInfoMockMvc.perform(get("/api/dlExistBookInfos/{id}", dlExistBookInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlExistBookInfo.getId().intValue()))
            .andExpect(jsonPath("$.libraryName").value(DEFAULT_LIBRARY_NAME.toString()))
            .andExpect(jsonPath("$.selfNo").value(DEFAULT_SELF_NO.toString()))
            .andExpect(jsonPath("$.rawNo").value(DEFAULT_RAW_NO.toString()))
            .andExpect(jsonPath("$.bookName").value(DEFAULT_BOOK_NAME.toString()))
            .andExpect(jsonPath("$.publisherName").value(DEFAULT_PUBLISHER_NAME.toString()))
            .andExpect(jsonPath("$.copyright").value(DEFAULT_COPYRIGHT.toString()))
            .andExpect(jsonPath("$.isbnNo").value(DEFAULT_ISBN_NO.toString()))
            .andExpect(jsonPath("$.totalCopies").value(DEFAULT_TOTAL_COPIES))
            .andExpect(jsonPath("$.uploadUrl").value(DEFAULT_UPLOAD_URL.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlExistBookInfo() throws Exception {
        // Get the dlExistBookInfo
        restDlExistBookInfoMockMvc.perform(get("/api/dlExistBookInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlExistBookInfo() throws Exception {
        // Initialize the database
        dlExistBookInfoRepository.saveAndFlush(dlExistBookInfo);

		int databaseSizeBeforeUpdate = dlExistBookInfoRepository.findAll().size();

        // Update the dlExistBookInfo
        dlExistBookInfo.setLibraryName(UPDATED_LIBRARY_NAME);
        dlExistBookInfo.setSelfNo(UPDATED_SELF_NO);
        dlExistBookInfo.setRawNo(UPDATED_RAW_NO);
        dlExistBookInfo.setBookName(UPDATED_BOOK_NAME);
        dlExistBookInfo.setPublisherName(UPDATED_PUBLISHER_NAME);
        dlExistBookInfo.setCopyright(UPDATED_COPYRIGHT);
        dlExistBookInfo.setIsbnNo(UPDATED_ISBN_NO);
        dlExistBookInfo.setTotalCopies(UPDATED_TOTAL_COPIES);
        dlExistBookInfo.setUploadUrl(UPDATED_UPLOAD_URL);
        dlExistBookInfo.setCreatedDate(UPDATED_CREATED_DATE);
        dlExistBookInfo.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlExistBookInfo.setCreatedBy(UPDATED_CREATED_BY);
        dlExistBookInfo.setUpdatedBy(UPDATED_UPDATED_BY);
        dlExistBookInfo.setStatus(UPDATED_STATUS);

        restDlExistBookInfoMockMvc.perform(put("/api/dlExistBookInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlExistBookInfo)))
                .andExpect(status().isOk());

        // Validate the DlExistBookInfo in the database
        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeUpdate);
        DlExistBookInfo testDlExistBookInfo = dlExistBookInfos.get(dlExistBookInfos.size() - 1);
        assertThat(testDlExistBookInfo.getLibraryName()).isEqualTo(UPDATED_LIBRARY_NAME);
        assertThat(testDlExistBookInfo.getSelfNo()).isEqualTo(UPDATED_SELF_NO);
        assertThat(testDlExistBookInfo.getRawNo()).isEqualTo(UPDATED_RAW_NO);
        assertThat(testDlExistBookInfo.getBookName()).isEqualTo(UPDATED_BOOK_NAME);
        assertThat(testDlExistBookInfo.getPublisherName()).isEqualTo(UPDATED_PUBLISHER_NAME);
        assertThat(testDlExistBookInfo.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testDlExistBookInfo.getIsbnNo()).isEqualTo(UPDATED_ISBN_NO);
        assertThat(testDlExistBookInfo.getTotalCopies()).isEqualTo(UPDATED_TOTAL_COPIES);
        assertThat(testDlExistBookInfo.getUploadUrl()).isEqualTo(UPDATED_UPLOAD_URL);
        assertThat(testDlExistBookInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlExistBookInfo.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlExistBookInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlExistBookInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlExistBookInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlExistBookInfo() throws Exception {
        // Initialize the database
        dlExistBookInfoRepository.saveAndFlush(dlExistBookInfo);

		int databaseSizeBeforeDelete = dlExistBookInfoRepository.findAll().size();

        // Get the dlExistBookInfo
        restDlExistBookInfoMockMvc.perform(delete("/api/dlExistBookInfos/{id}", dlExistBookInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlExistBookInfo> dlExistBookInfos = dlExistBookInfoRepository.findAll();
        assertThat(dlExistBookInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
