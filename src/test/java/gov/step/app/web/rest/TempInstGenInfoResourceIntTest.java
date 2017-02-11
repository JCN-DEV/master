package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.TempInstGenInfo;
import gov.step.app.repository.TempInstGenInfoRepository;
import gov.step.app.repository.search.TempInstGenInfoSearchRepository;

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

import gov.step.app.domain.enumeration.instType;

/**
 * Test class for the TempInstGenInfoResource REST controller.
 *
 * @see TempInstGenInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TempInstGenInfoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";


private static final instType DEFAULT_TYPE = instType.Government;
    private static final instType UPDATED_TYPE = instType.NonGovernment;
    private static final String DEFAULT_VILLAGE = "AAAAA";
    private static final String UPDATED_VILLAGE = "BBBBB";
    private static final String DEFAULT_POST_OFFICE = "AAAAA";
    private static final String UPDATED_POST_OFFICE = "BBBBB";
    private static final String DEFAULT_POST_CODE = "AAAAA";
    private static final String UPDATED_POST_CODE = "BBBBB";
    private static final String DEFAULT_LAND_PHONE = "AAAAA";
    private static final String UPDATED_LAND_PHONE = "BBBBB";
    private static final String DEFAULT_MOBILE_NO = "AAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_CONS_AREA = "AAAAA";
    private static final String UPDATED_CONS_AREA = "BBBBB";

    private static final Boolean DEFAULT_UPDATE = false;
    private static final Boolean UPDATED_UPDATE = true;

    @Inject
    private TempInstGenInfoRepository tempInstGenInfoRepository;

    @Inject
    private TempInstGenInfoSearchRepository tempInstGenInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTempInstGenInfoMockMvc;

    private TempInstGenInfo tempInstGenInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TempInstGenInfoResource tempInstGenInfoResource = new TempInstGenInfoResource();
        ReflectionTestUtils.setField(tempInstGenInfoResource, "tempInstGenInfoRepository", tempInstGenInfoRepository);
        ReflectionTestUtils.setField(tempInstGenInfoResource, "tempInstGenInfoSearchRepository", tempInstGenInfoSearchRepository);
        this.restTempInstGenInfoMockMvc = MockMvcBuilders.standaloneSetup(tempInstGenInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tempInstGenInfo = new TempInstGenInfo();
        tempInstGenInfo.setName(DEFAULT_NAME);
        tempInstGenInfo.setType(DEFAULT_TYPE);
        tempInstGenInfo.setVillage(DEFAULT_VILLAGE);
        tempInstGenInfo.setPostOffice(DEFAULT_POST_OFFICE);
        tempInstGenInfo.setPostCode(DEFAULT_POST_CODE);
        tempInstGenInfo.setLandPhone(DEFAULT_LAND_PHONE);
        tempInstGenInfo.setMobileNo(DEFAULT_MOBILE_NO);
        tempInstGenInfo.setEmail(DEFAULT_EMAIL);
        tempInstGenInfo.setConsArea(DEFAULT_CONS_AREA);
        tempInstGenInfo.setUpdate(DEFAULT_UPDATE);
    }

    @Test
    @Transactional
    public void createTempInstGenInfo() throws Exception {
        int databaseSizeBeforeCreate = tempInstGenInfoRepository.findAll().size();

        // Create the TempInstGenInfo

        restTempInstGenInfoMockMvc.perform(post("/api/tempInstGenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempInstGenInfo)))
                .andExpect(status().isCreated());

        // Validate the TempInstGenInfo in the database
        List<TempInstGenInfo> tempInstGenInfos = tempInstGenInfoRepository.findAll();
        assertThat(tempInstGenInfos).hasSize(databaseSizeBeforeCreate + 1);
        TempInstGenInfo testTempInstGenInfo = tempInstGenInfos.get(tempInstGenInfos.size() - 1);
        assertThat(testTempInstGenInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTempInstGenInfo.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTempInstGenInfo.getVillage()).isEqualTo(DEFAULT_VILLAGE);
        assertThat(testTempInstGenInfo.getPostOffice()).isEqualTo(DEFAULT_POST_OFFICE);
        assertThat(testTempInstGenInfo.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testTempInstGenInfo.getLandPhone()).isEqualTo(DEFAULT_LAND_PHONE);
        assertThat(testTempInstGenInfo.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testTempInstGenInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTempInstGenInfo.getConsArea()).isEqualTo(DEFAULT_CONS_AREA);
        assertThat(testTempInstGenInfo.getUpdate()).isEqualTo(DEFAULT_UPDATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempInstGenInfoRepository.findAll().size();
        // set the field null
        tempInstGenInfo.setName(null);

        // Create the TempInstGenInfo, which fails.

        restTempInstGenInfoMockMvc.perform(post("/api/tempInstGenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempInstGenInfo)))
                .andExpect(status().isBadRequest());

        List<TempInstGenInfo> tempInstGenInfos = tempInstGenInfoRepository.findAll();
        assertThat(tempInstGenInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempInstGenInfoRepository.findAll().size();
        // set the field null
        tempInstGenInfo.setType(null);

        // Create the TempInstGenInfo, which fails.

        restTempInstGenInfoMockMvc.perform(post("/api/tempInstGenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempInstGenInfo)))
                .andExpect(status().isBadRequest());

        List<TempInstGenInfo> tempInstGenInfos = tempInstGenInfoRepository.findAll();
        assertThat(tempInstGenInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTempInstGenInfos() throws Exception {
        // Initialize the database
        tempInstGenInfoRepository.saveAndFlush(tempInstGenInfo);

        // Get all the tempInstGenInfos
        restTempInstGenInfoMockMvc.perform(get("/api/tempInstGenInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tempInstGenInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].village").value(hasItem(DEFAULT_VILLAGE.toString())))
                .andExpect(jsonPath("$.[*].postOffice").value(hasItem(DEFAULT_POST_OFFICE.toString())))
                .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
                .andExpect(jsonPath("$.[*].landPhone").value(hasItem(DEFAULT_LAND_PHONE.toString())))
                .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].consArea").value(hasItem(DEFAULT_CONS_AREA.toString())))
                .andExpect(jsonPath("$.[*].update").value(hasItem(DEFAULT_UPDATE.booleanValue())));
    }

    @Test
    @Transactional
    public void getTempInstGenInfo() throws Exception {
        // Initialize the database
        tempInstGenInfoRepository.saveAndFlush(tempInstGenInfo);

        // Get the tempInstGenInfo
        restTempInstGenInfoMockMvc.perform(get("/api/tempInstGenInfos/{id}", tempInstGenInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tempInstGenInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.village").value(DEFAULT_VILLAGE.toString()))
            .andExpect(jsonPath("$.postOffice").value(DEFAULT_POST_OFFICE.toString()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()))
            .andExpect(jsonPath("$.landPhone").value(DEFAULT_LAND_PHONE.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.consArea").value(DEFAULT_CONS_AREA.toString()))
            .andExpect(jsonPath("$.update").value(DEFAULT_UPDATE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTempInstGenInfo() throws Exception {
        // Get the tempInstGenInfo
        restTempInstGenInfoMockMvc.perform(get("/api/tempInstGenInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTempInstGenInfo() throws Exception {
        // Initialize the database
        tempInstGenInfoRepository.saveAndFlush(tempInstGenInfo);

		int databaseSizeBeforeUpdate = tempInstGenInfoRepository.findAll().size();

        // Update the tempInstGenInfo
        tempInstGenInfo.setName(UPDATED_NAME);
        tempInstGenInfo.setType(UPDATED_TYPE);
        tempInstGenInfo.setVillage(UPDATED_VILLAGE);
        tempInstGenInfo.setPostOffice(UPDATED_POST_OFFICE);
        tempInstGenInfo.setPostCode(UPDATED_POST_CODE);
        tempInstGenInfo.setLandPhone(UPDATED_LAND_PHONE);
        tempInstGenInfo.setMobileNo(UPDATED_MOBILE_NO);
        tempInstGenInfo.setEmail(UPDATED_EMAIL);
        tempInstGenInfo.setConsArea(UPDATED_CONS_AREA);
        tempInstGenInfo.setUpdate(UPDATED_UPDATE);

        restTempInstGenInfoMockMvc.perform(put("/api/tempInstGenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempInstGenInfo)))
                .andExpect(status().isOk());

        // Validate the TempInstGenInfo in the database
        List<TempInstGenInfo> tempInstGenInfos = tempInstGenInfoRepository.findAll();
        assertThat(tempInstGenInfos).hasSize(databaseSizeBeforeUpdate);
        TempInstGenInfo testTempInstGenInfo = tempInstGenInfos.get(tempInstGenInfos.size() - 1);
        assertThat(testTempInstGenInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTempInstGenInfo.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTempInstGenInfo.getVillage()).isEqualTo(UPDATED_VILLAGE);
        assertThat(testTempInstGenInfo.getPostOffice()).isEqualTo(UPDATED_POST_OFFICE);
        assertThat(testTempInstGenInfo.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testTempInstGenInfo.getLandPhone()).isEqualTo(UPDATED_LAND_PHONE);
        assertThat(testTempInstGenInfo.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testTempInstGenInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTempInstGenInfo.getConsArea()).isEqualTo(UPDATED_CONS_AREA);
        assertThat(testTempInstGenInfo.getUpdate()).isEqualTo(UPDATED_UPDATE);
    }

    @Test
    @Transactional
    public void deleteTempInstGenInfo() throws Exception {
        // Initialize the database
        tempInstGenInfoRepository.saveAndFlush(tempInstGenInfo);

		int databaseSizeBeforeDelete = tempInstGenInfoRepository.findAll().size();

        // Get the tempInstGenInfo
        restTempInstGenInfoMockMvc.perform(delete("/api/tempInstGenInfos/{id}", tempInstGenInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TempInstGenInfo> tempInstGenInfos = tempInstGenInfoRepository.findAll();
        assertThat(tempInstGenInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
