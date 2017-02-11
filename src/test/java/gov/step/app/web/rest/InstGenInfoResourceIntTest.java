package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstGenInfo;
import gov.step.app.repository.InstGenInfoRepository;
import gov.step.app.repository.search.InstGenInfoSearchRepository;

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

import gov.step.app.domain.enumeration.instType;

/**
 * Test class for the InstGenInfoResource REST controller.
 *
 * @see InstGenInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstGenInfoResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLICATION_DATE = LocalDate.now(ZoneId.systemDefault());


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

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private InstGenInfoSearchRepository instGenInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstGenInfoMockMvc;

    private InstGenInfo instGenInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstGenInfoResource instGenInfoResource = new InstGenInfoResource();
        ReflectionTestUtils.setField(instGenInfoResource, "instGenInfoRepository", instGenInfoRepository);
        ReflectionTestUtils.setField(instGenInfoResource, "instGenInfoSearchRepository", instGenInfoSearchRepository);
        this.restInstGenInfoMockMvc = MockMvcBuilders.standaloneSetup(instGenInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instGenInfo = new InstGenInfo();
        instGenInfo.setCode(DEFAULT_CODE);
        instGenInfo.setName(DEFAULT_NAME);
        instGenInfo.setPublicationDate(DEFAULT_PUBLICATION_DATE);
        //instGenInfo.setType(DEFAULT_TYPE);
        instGenInfo.setVillage(DEFAULT_VILLAGE);
        instGenInfo.setPostOffice(DEFAULT_POST_OFFICE);
        instGenInfo.setPostCode(DEFAULT_POST_CODE);
        instGenInfo.setLandPhone(DEFAULT_LAND_PHONE);
        instGenInfo.setMobileNo(DEFAULT_MOBILE_NO);
        instGenInfo.setEmail(DEFAULT_EMAIL);
        instGenInfo.setConsArea(DEFAULT_CONS_AREA);
        instGenInfo.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstGenInfo() throws Exception {
        int databaseSizeBeforeCreate = instGenInfoRepository.findAll().size();

        // Create the InstGenInfo

        restInstGenInfoMockMvc.perform(post("/api/instGenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGenInfo)))
                .andExpect(status().isCreated());

        // Validate the InstGenInfo in the database
        List<InstGenInfo> instGenInfos = instGenInfoRepository.findAll();
        assertThat(instGenInfos).hasSize(databaseSizeBeforeCreate + 1);
        InstGenInfo testInstGenInfo = instGenInfos.get(instGenInfos.size() - 1);
        assertThat(testInstGenInfo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInstGenInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstGenInfo.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testInstGenInfo.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInstGenInfo.getVillage()).isEqualTo(DEFAULT_VILLAGE);
        assertThat(testInstGenInfo.getPostOffice()).isEqualTo(DEFAULT_POST_OFFICE);
        assertThat(testInstGenInfo.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testInstGenInfo.getLandPhone()).isEqualTo(DEFAULT_LAND_PHONE);
        assertThat(testInstGenInfo.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testInstGenInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInstGenInfo.getConsArea()).isEqualTo(DEFAULT_CONS_AREA);
        assertThat(testInstGenInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGenInfoRepository.findAll().size();
        // set the field null
        instGenInfo.setCode(null);

        // Create the InstGenInfo, which fails.

        restInstGenInfoMockMvc.perform(post("/api/instGenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGenInfo)))
                .andExpect(status().isBadRequest());

        List<InstGenInfo> instGenInfos = instGenInfoRepository.findAll();
        assertThat(instGenInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGenInfoRepository.findAll().size();
        // set the field null
        instGenInfo.setName(null);

        // Create the InstGenInfo, which fails.

        restInstGenInfoMockMvc.perform(post("/api/instGenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGenInfo)))
                .andExpect(status().isBadRequest());

        List<InstGenInfo> instGenInfos = instGenInfoRepository.findAll();
        assertThat(instGenInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGenInfoRepository.findAll().size();
        // set the field null
        instGenInfo.setPublicationDate(null);

        // Create the InstGenInfo, which fails.

        restInstGenInfoMockMvc.perform(post("/api/instGenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGenInfo)))
                .andExpect(status().isBadRequest());

        List<InstGenInfo> instGenInfos = instGenInfoRepository.findAll();
        assertThat(instGenInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instGenInfoRepository.findAll().size();
        // set the field null
        instGenInfo.setType(null);

        // Create the InstGenInfo, which fails.

        restInstGenInfoMockMvc.perform(post("/api/instGenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGenInfo)))
                .andExpect(status().isBadRequest());

        List<InstGenInfo> instGenInfos = instGenInfoRepository.findAll();
        assertThat(instGenInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstGenInfos() throws Exception {
        // Initialize the database
        instGenInfoRepository.saveAndFlush(instGenInfo);

        // Get all the instGenInfos
        restInstGenInfoMockMvc.perform(get("/api/instGenInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instGenInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].village").value(hasItem(DEFAULT_VILLAGE.toString())))
                .andExpect(jsonPath("$.[*].postOffice").value(hasItem(DEFAULT_POST_OFFICE.toString())))
                .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
                .andExpect(jsonPath("$.[*].landPhone").value(hasItem(DEFAULT_LAND_PHONE.toString())))
                .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].consArea").value(hasItem(DEFAULT_CONS_AREA.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstGenInfo() throws Exception {
        // Initialize the database
        instGenInfoRepository.saveAndFlush(instGenInfo);

        // Get the instGenInfo
        restInstGenInfoMockMvc.perform(get("/api/instGenInfos/{id}", instGenInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instGenInfo.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.village").value(DEFAULT_VILLAGE.toString()))
            .andExpect(jsonPath("$.postOffice").value(DEFAULT_POST_OFFICE.toString()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()))
            .andExpect(jsonPath("$.landPhone").value(DEFAULT_LAND_PHONE.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.consArea").value(DEFAULT_CONS_AREA.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstGenInfo() throws Exception {
        // Get the instGenInfo
        restInstGenInfoMockMvc.perform(get("/api/instGenInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstGenInfo() throws Exception {
        // Initialize the database
        instGenInfoRepository.saveAndFlush(instGenInfo);

		int databaseSizeBeforeUpdate = instGenInfoRepository.findAll().size();

        // Update the instGenInfo
        instGenInfo.setCode(UPDATED_CODE);
        instGenInfo.setName(UPDATED_NAME);
        instGenInfo.setPublicationDate(UPDATED_PUBLICATION_DATE);
        //instGenInfo.setType(UPDATED_TYPE);
        instGenInfo.setVillage(UPDATED_VILLAGE);
        instGenInfo.setPostOffice(UPDATED_POST_OFFICE);
        instGenInfo.setPostCode(UPDATED_POST_CODE);
        instGenInfo.setLandPhone(UPDATED_LAND_PHONE);
        instGenInfo.setMobileNo(UPDATED_MOBILE_NO);
        instGenInfo.setEmail(UPDATED_EMAIL);
        instGenInfo.setConsArea(UPDATED_CONS_AREA);
        instGenInfo.setStatus(UPDATED_STATUS);

        restInstGenInfoMockMvc.perform(put("/api/instGenInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instGenInfo)))
                .andExpect(status().isOk());

        // Validate the InstGenInfo in the database
        List<InstGenInfo> instGenInfos = instGenInfoRepository.findAll();
        assertThat(instGenInfos).hasSize(databaseSizeBeforeUpdate);
        InstGenInfo testInstGenInfo = instGenInfos.get(instGenInfos.size() - 1);
        assertThat(testInstGenInfo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInstGenInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstGenInfo.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testInstGenInfo.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInstGenInfo.getVillage()).isEqualTo(UPDATED_VILLAGE);
        assertThat(testInstGenInfo.getPostOffice()).isEqualTo(UPDATED_POST_OFFICE);
        assertThat(testInstGenInfo.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testInstGenInfo.getLandPhone()).isEqualTo(UPDATED_LAND_PHONE);
        assertThat(testInstGenInfo.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testInstGenInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstGenInfo.getConsArea()).isEqualTo(UPDATED_CONS_AREA);
        assertThat(testInstGenInfo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstGenInfo() throws Exception {
        // Initialize the database
        instGenInfoRepository.saveAndFlush(instGenInfo);

		int databaseSizeBeforeDelete = instGenInfoRepository.findAll().size();

        // Get the instGenInfo
        restInstGenInfoMockMvc.perform(delete("/api/instGenInfos/{id}", instGenInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstGenInfo> instGenInfos = instGenInfoRepository.findAll();
        assertThat(instGenInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
