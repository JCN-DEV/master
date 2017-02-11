package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.QrcodeGenLog;
import gov.step.app.repository.QrcodeGenLogRepository;
import gov.step.app.repository.search.QrcodeGenLogSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the QrcodeGenLogResource REST controller.
 *
 * @see QrcodeGenLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class QrcodeGenLogResourceIntTest {


    private static final Integer DEFAULT_REF_ID = 1;
    private static final Integer UPDATED_REF_ID = 2;
    private static final String DEFAULT_URL_LINK = "AAAAA";
    private static final String UPDATED_URL_LINK = "BBBBB";
    private static final String DEFAULT_QR_CODE_TYPE = "AAAAA";
    private static final String UPDATED_QR_CODE_TYPE = "BBBBB";
    private static final String DEFAULT_DATA_DESC = "AAAAA";
    private static final String UPDATED_DATA_DESC = "BBBBB";
    private static final String DEFAULT_GEN_CODE = "AAAAA";
    private static final String UPDATED_GEN_CODE = "BBBBB";
    private static final String DEFAULT_CREATE_BY = "AAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBB";
    private static final String DEFAULT_UPDATE_BY = "AAAAA";
    private static final String UPDATED_UPDATE_BY = "BBBBB";
    private static final String DEFAULT_APP_NAME = "AAAAA";
    private static final String UPDATED_APP_NAME = "BBBBB";

    @Inject
    private QrcodeGenLogRepository qrcodeGenLogRepository;

    @Inject
    private QrcodeGenLogSearchRepository qrcodeGenLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restQrcodeGenLogMockMvc;

    private QrcodeGenLog qrcodeGenLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QrcodeGenLogResource qrcodeGenLogResource = new QrcodeGenLogResource();
        ReflectionTestUtils.setField(qrcodeGenLogResource, "qrcodeGenLogRepository", qrcodeGenLogRepository);
        ReflectionTestUtils.setField(qrcodeGenLogResource, "qrcodeGenLogSearchRepository", qrcodeGenLogSearchRepository);
        this.restQrcodeGenLogMockMvc = MockMvcBuilders.standaloneSetup(qrcodeGenLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        qrcodeGenLog = new QrcodeGenLog();
        qrcodeGenLog.setRefId(DEFAULT_REF_ID);
        qrcodeGenLog.setUrlLink(DEFAULT_URL_LINK);
        qrcodeGenLog.setQrCodeType(DEFAULT_QR_CODE_TYPE);
        qrcodeGenLog.setDataDesc(DEFAULT_DATA_DESC);
        qrcodeGenLog.setGenCode(DEFAULT_GEN_CODE);
        qrcodeGenLog.setCreateBy(DEFAULT_CREATE_BY);
        qrcodeGenLog.setUpdateBy(DEFAULT_UPDATE_BY);
        qrcodeGenLog.setAppName(DEFAULT_APP_NAME);
    }

    @Test
    @Transactional
    public void createQrcodeGenLog() throws Exception {
        int databaseSizeBeforeCreate = qrcodeGenLogRepository.findAll().size();

        // Create the QrcodeGenLog

        restQrcodeGenLogMockMvc.perform(post("/api/qrcodeGenLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(qrcodeGenLog)))
                .andExpect(status().isCreated());

        // Validate the QrcodeGenLog in the database
        List<QrcodeGenLog> qrcodeGenLogs = qrcodeGenLogRepository.findAll();
        assertThat(qrcodeGenLogs).hasSize(databaseSizeBeforeCreate + 1);
        QrcodeGenLog testQrcodeGenLog = qrcodeGenLogs.get(qrcodeGenLogs.size() - 1);
        assertThat(testQrcodeGenLog.getRefId()).isEqualTo(DEFAULT_REF_ID);
        assertThat(testQrcodeGenLog.getUrlLink()).isEqualTo(DEFAULT_URL_LINK);
        assertThat(testQrcodeGenLog.getQrCodeType()).isEqualTo(DEFAULT_QR_CODE_TYPE);
        assertThat(testQrcodeGenLog.getDataDesc()).isEqualTo(DEFAULT_DATA_DESC);
        assertThat(testQrcodeGenLog.getGenCode()).isEqualTo(DEFAULT_GEN_CODE);
        assertThat(testQrcodeGenLog.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testQrcodeGenLog.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testQrcodeGenLog.getAppName()).isEqualTo(DEFAULT_APP_NAME);
    }

    @Test
    @Transactional
    public void getAllQrcodeGenLogs() throws Exception {
        // Initialize the database
        qrcodeGenLogRepository.saveAndFlush(qrcodeGenLog);

        // Get all the qrcodeGenLogs
        restQrcodeGenLogMockMvc.perform(get("/api/qrcodeGenLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(qrcodeGenLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].refId").value(hasItem(DEFAULT_REF_ID)))
                .andExpect(jsonPath("$.[*].urlLink").value(hasItem(DEFAULT_URL_LINK.toString())))
                .andExpect(jsonPath("$.[*].qrCodeType").value(hasItem(DEFAULT_QR_CODE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].dataDesc").value(hasItem(DEFAULT_DATA_DESC.toString())))
                .andExpect(jsonPath("$.[*].genCode").value(hasItem(DEFAULT_GEN_CODE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.toString())))
                .andExpect(jsonPath("$.[*].appName").value(hasItem(DEFAULT_APP_NAME.toString())));
    }

    @Test
    @Transactional
    public void getQrcodeGenLog() throws Exception {
        // Initialize the database
        qrcodeGenLogRepository.saveAndFlush(qrcodeGenLog);

        // Get the qrcodeGenLog
        restQrcodeGenLogMockMvc.perform(get("/api/qrcodeGenLogs/{id}", qrcodeGenLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(qrcodeGenLog.getId().intValue()))
            .andExpect(jsonPath("$.refId").value(DEFAULT_REF_ID))
            .andExpect(jsonPath("$.urlLink").value(DEFAULT_URL_LINK.toString()))
            .andExpect(jsonPath("$.qrCodeType").value(DEFAULT_QR_CODE_TYPE.toString()))
            .andExpect(jsonPath("$.dataDesc").value(DEFAULT_DATA_DESC.toString()))
            .andExpect(jsonPath("$.genCode").value(DEFAULT_GEN_CODE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.toString()))
            .andExpect(jsonPath("$.appName").value(DEFAULT_APP_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQrcodeGenLog() throws Exception {
        // Get the qrcodeGenLog
        restQrcodeGenLogMockMvc.perform(get("/api/qrcodeGenLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQrcodeGenLog() throws Exception {
        // Initialize the database
        qrcodeGenLogRepository.saveAndFlush(qrcodeGenLog);

		int databaseSizeBeforeUpdate = qrcodeGenLogRepository.findAll().size();

        // Update the qrcodeGenLog
        qrcodeGenLog.setRefId(UPDATED_REF_ID);
        qrcodeGenLog.setUrlLink(UPDATED_URL_LINK);
        qrcodeGenLog.setQrCodeType(UPDATED_QR_CODE_TYPE);
        qrcodeGenLog.setDataDesc(UPDATED_DATA_DESC);
        qrcodeGenLog.setGenCode(UPDATED_GEN_CODE);
        qrcodeGenLog.setCreateBy(UPDATED_CREATE_BY);
        qrcodeGenLog.setUpdateBy(UPDATED_UPDATE_BY);
        qrcodeGenLog.setAppName(UPDATED_APP_NAME);

        restQrcodeGenLogMockMvc.perform(put("/api/qrcodeGenLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(qrcodeGenLog)))
                .andExpect(status().isOk());

        // Validate the QrcodeGenLog in the database
        List<QrcodeGenLog> qrcodeGenLogs = qrcodeGenLogRepository.findAll();
        assertThat(qrcodeGenLogs).hasSize(databaseSizeBeforeUpdate);
        QrcodeGenLog testQrcodeGenLog = qrcodeGenLogs.get(qrcodeGenLogs.size() - 1);
        assertThat(testQrcodeGenLog.getRefId()).isEqualTo(UPDATED_REF_ID);
        assertThat(testQrcodeGenLog.getUrlLink()).isEqualTo(UPDATED_URL_LINK);
        assertThat(testQrcodeGenLog.getQrCodeType()).isEqualTo(UPDATED_QR_CODE_TYPE);
        assertThat(testQrcodeGenLog.getDataDesc()).isEqualTo(UPDATED_DATA_DESC);
        assertThat(testQrcodeGenLog.getGenCode()).isEqualTo(UPDATED_GEN_CODE);
        assertThat(testQrcodeGenLog.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testQrcodeGenLog.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testQrcodeGenLog.getAppName()).isEqualTo(UPDATED_APP_NAME);
    }

    @Test
    @Transactional
    public void deleteQrcodeGenLog() throws Exception {
        // Initialize the database
        qrcodeGenLogRepository.saveAndFlush(qrcodeGenLog);

		int databaseSizeBeforeDelete = qrcodeGenLogRepository.findAll().size();

        // Get the qrcodeGenLog
        restQrcodeGenLogMockMvc.perform(delete("/api/qrcodeGenLogs/{id}", qrcodeGenLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<QrcodeGenLog> qrcodeGenLogs = qrcodeGenLogRepository.findAll();
        assertThat(qrcodeGenLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
