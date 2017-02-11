package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpPublicationInfo;
import gov.step.app.repository.HrEmpPublicationInfoRepository;
import gov.step.app.repository.search.HrEmpPublicationInfoSearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HrEmpPublicationInfoResource REST controller.
 *
 * @see HrEmpPublicationInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpPublicationInfoResourceIntTest {

    private static final String DEFAULT_PUBLICATION_TITLE = "AAAAA";
    private static final String UPDATED_PUBLICATION_TITLE = "BBBBB";

    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLICATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final byte[] DEFAULT_PUBLICATION_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PUBLICATION_DOC = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PUBLICATION_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PUBLICATION_DOC_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_PUBLICATION_DOC_NAME = "AAAAA";
    private static final String UPDATED_PUBLICATION_DOC_NAME = "BBBBB";

    private static final Long DEFAULT_LOG_ID = 1L;
    private static final Long UPDATED_LOG_ID = 2L;

    private static final Long DEFAULT_LOG_STATUS = 1L;
    private static final Long UPDATED_LOG_STATUS = 2L;

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
    private HrEmpPublicationInfoRepository hrEmpPublicationInfoRepository;

    @Inject
    private HrEmpPublicationInfoSearchRepository hrEmpPublicationInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpPublicationInfoMockMvc;

    private HrEmpPublicationInfo hrEmpPublicationInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpPublicationInfoResource hrEmpPublicationInfoResource = new HrEmpPublicationInfoResource();
        ReflectionTestUtils.setField(hrEmpPublicationInfoResource, "hrEmpPublicationInfoSearchRepository", hrEmpPublicationInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpPublicationInfoResource, "hrEmpPublicationInfoRepository", hrEmpPublicationInfoRepository);
        this.restHrEmpPublicationInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpPublicationInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpPublicationInfo = new HrEmpPublicationInfo();
        hrEmpPublicationInfo.setPublicationTitle(DEFAULT_PUBLICATION_TITLE);
        hrEmpPublicationInfo.setPublicationDate(DEFAULT_PUBLICATION_DATE);
        hrEmpPublicationInfo.setRemarks(DEFAULT_REMARKS);
        hrEmpPublicationInfo.setPublicationDoc(DEFAULT_PUBLICATION_DOC);
        hrEmpPublicationInfo.setPublicationDocContentType(DEFAULT_PUBLICATION_DOC_CONTENT_TYPE);
        hrEmpPublicationInfo.setPublicationDocName(DEFAULT_PUBLICATION_DOC_NAME);
        hrEmpPublicationInfo.setLogId(DEFAULT_LOG_ID);
        hrEmpPublicationInfo.setLogStatus(DEFAULT_LOG_STATUS);
        hrEmpPublicationInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpPublicationInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpPublicationInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpPublicationInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpPublicationInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpPublicationInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpPublicationInfoRepository.findAll().size();

        // Create the HrEmpPublicationInfo

        restHrEmpPublicationInfoMockMvc.perform(post("/api/hrEmpPublicationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPublicationInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpPublicationInfo in the database
        List<HrEmpPublicationInfo> hrEmpPublicationInfos = hrEmpPublicationInfoRepository.findAll();
        assertThat(hrEmpPublicationInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpPublicationInfo testHrEmpPublicationInfo = hrEmpPublicationInfos.get(hrEmpPublicationInfos.size() - 1);
        assertThat(testHrEmpPublicationInfo.getPublicationTitle()).isEqualTo(DEFAULT_PUBLICATION_TITLE);
        assertThat(testHrEmpPublicationInfo.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testHrEmpPublicationInfo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testHrEmpPublicationInfo.getPublicationDoc()).isEqualTo(DEFAULT_PUBLICATION_DOC);
        assertThat(testHrEmpPublicationInfo.getPublicationDocContentType()).isEqualTo(DEFAULT_PUBLICATION_DOC_CONTENT_TYPE);
        assertThat(testHrEmpPublicationInfo.getPublicationDocName()).isEqualTo(DEFAULT_PUBLICATION_DOC_NAME);
        assertThat(testHrEmpPublicationInfo.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHrEmpPublicationInfo.getLogStatus()).isEqualTo(DEFAULT_LOG_STATUS);
        assertThat(testHrEmpPublicationInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpPublicationInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpPublicationInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpPublicationInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpPublicationInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkPublicationTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPublicationInfoRepository.findAll().size();
        // set the field null
        hrEmpPublicationInfo.setPublicationTitle(null);

        // Create the HrEmpPublicationInfo, which fails.

        restHrEmpPublicationInfoMockMvc.perform(post("/api/hrEmpPublicationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPublicationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPublicationInfo> hrEmpPublicationInfos = hrEmpPublicationInfoRepository.findAll();
        assertThat(hrEmpPublicationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPublicationInfoRepository.findAll().size();
        // set the field null
        hrEmpPublicationInfo.setPublicationDate(null);

        // Create the HrEmpPublicationInfo, which fails.

        restHrEmpPublicationInfoMockMvc.perform(post("/api/hrEmpPublicationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPublicationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPublicationInfo> hrEmpPublicationInfos = hrEmpPublicationInfoRepository.findAll();
        assertThat(hrEmpPublicationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpPublicationInfoRepository.findAll().size();
        // set the field null
        hrEmpPublicationInfo.setActiveStatus(null);

        // Create the HrEmpPublicationInfo, which fails.

        restHrEmpPublicationInfoMockMvc.perform(post("/api/hrEmpPublicationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPublicationInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpPublicationInfo> hrEmpPublicationInfos = hrEmpPublicationInfoRepository.findAll();
        assertThat(hrEmpPublicationInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpPublicationInfos() throws Exception {
        // Initialize the database
        hrEmpPublicationInfoRepository.saveAndFlush(hrEmpPublicationInfo);

        // Get all the hrEmpPublicationInfos
        restHrEmpPublicationInfoMockMvc.perform(get("/api/hrEmpPublicationInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpPublicationInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].publicationTitle").value(hasItem(DEFAULT_PUBLICATION_TITLE.toString())))
                .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].publicationDocContentType").value(hasItem(DEFAULT_PUBLICATION_DOC_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].publicationDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_PUBLICATION_DOC))))
                .andExpect(jsonPath("$.[*].publicationDocName").value(hasItem(DEFAULT_PUBLICATION_DOC_NAME.toString())))
                .andExpect(jsonPath("$.[*].logId").value(hasItem(DEFAULT_LOG_ID.intValue())))
                .andExpect(jsonPath("$.[*].logStatus").value(hasItem(DEFAULT_LOG_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmpPublicationInfo() throws Exception {
        // Initialize the database
        hrEmpPublicationInfoRepository.saveAndFlush(hrEmpPublicationInfo);

        // Get the hrEmpPublicationInfo
        restHrEmpPublicationInfoMockMvc.perform(get("/api/hrEmpPublicationInfos/{id}", hrEmpPublicationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpPublicationInfo.getId().intValue()))
            .andExpect(jsonPath("$.publicationTitle").value(DEFAULT_PUBLICATION_TITLE.toString()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.publicationDocContentType").value(DEFAULT_PUBLICATION_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.publicationDoc").value(Base64Utils.encodeToString(DEFAULT_PUBLICATION_DOC)))
            .andExpect(jsonPath("$.publicationDocName").value(DEFAULT_PUBLICATION_DOC_NAME.toString()))
            .andExpect(jsonPath("$.logId").value(DEFAULT_LOG_ID.intValue()))
            .andExpect(jsonPath("$.logStatus").value(DEFAULT_LOG_STATUS.intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmpPublicationInfo() throws Exception {
        // Get the hrEmpPublicationInfo
        restHrEmpPublicationInfoMockMvc.perform(get("/api/hrEmpPublicationInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpPublicationInfo() throws Exception {
        // Initialize the database
        hrEmpPublicationInfoRepository.saveAndFlush(hrEmpPublicationInfo);

		int databaseSizeBeforeUpdate = hrEmpPublicationInfoRepository.findAll().size();

        // Update the hrEmpPublicationInfo
        hrEmpPublicationInfo.setPublicationTitle(UPDATED_PUBLICATION_TITLE);
        hrEmpPublicationInfo.setPublicationDate(UPDATED_PUBLICATION_DATE);
        hrEmpPublicationInfo.setRemarks(UPDATED_REMARKS);
        hrEmpPublicationInfo.setPublicationDoc(UPDATED_PUBLICATION_DOC);
        hrEmpPublicationInfo.setPublicationDocContentType(UPDATED_PUBLICATION_DOC_CONTENT_TYPE);
        hrEmpPublicationInfo.setPublicationDocName(UPDATED_PUBLICATION_DOC_NAME);
        hrEmpPublicationInfo.setLogId(UPDATED_LOG_ID);
        hrEmpPublicationInfo.setLogStatus(UPDATED_LOG_STATUS);
        hrEmpPublicationInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpPublicationInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpPublicationInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpPublicationInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpPublicationInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpPublicationInfoMockMvc.perform(put("/api/hrEmpPublicationInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpPublicationInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpPublicationInfo in the database
        List<HrEmpPublicationInfo> hrEmpPublicationInfos = hrEmpPublicationInfoRepository.findAll();
        assertThat(hrEmpPublicationInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpPublicationInfo testHrEmpPublicationInfo = hrEmpPublicationInfos.get(hrEmpPublicationInfos.size() - 1);
        assertThat(testHrEmpPublicationInfo.getPublicationTitle()).isEqualTo(UPDATED_PUBLICATION_TITLE);
        assertThat(testHrEmpPublicationInfo.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testHrEmpPublicationInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testHrEmpPublicationInfo.getPublicationDoc()).isEqualTo(UPDATED_PUBLICATION_DOC);
        assertThat(testHrEmpPublicationInfo.getPublicationDocContentType()).isEqualTo(UPDATED_PUBLICATION_DOC_CONTENT_TYPE);
        assertThat(testHrEmpPublicationInfo.getPublicationDocName()).isEqualTo(UPDATED_PUBLICATION_DOC_NAME);
        assertThat(testHrEmpPublicationInfo.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHrEmpPublicationInfo.getLogStatus()).isEqualTo(UPDATED_LOG_STATUS);
        assertThat(testHrEmpPublicationInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpPublicationInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpPublicationInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpPublicationInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpPublicationInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpPublicationInfo() throws Exception {
        // Initialize the database
        hrEmpPublicationInfoRepository.saveAndFlush(hrEmpPublicationInfo);

		int databaseSizeBeforeDelete = hrEmpPublicationInfoRepository.findAll().size();

        // Get the hrEmpPublicationInfo
        restHrEmpPublicationInfoMockMvc.perform(delete("/api/hrEmpPublicationInfos/{id}", hrEmpPublicationInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpPublicationInfo> hrEmpPublicationInfos = hrEmpPublicationInfoRepository.findAll();
        assertThat(hrEmpPublicationInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
