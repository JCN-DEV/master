package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.MpoCommitteePersonInfo;
import gov.step.app.repository.MpoCommitteePersonInfoRepository;
import gov.step.app.repository.search.MpoCommitteePersonInfoSearchRepository;

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
 * Test class for the MpoCommitteePersonInfoResource REST controller.
 *
 * @see MpoCommitteePersonInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MpoCommitteePersonInfoResourceIntTest {

    private static final String DEFAULT_CONTACT_NO = "AAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_ORG_NAME = "AAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DATE_CRATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CRATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    @Inject
    private MpoCommitteePersonInfoRepository mpoCommitteePersonInfoRepository;

    @Inject
    private MpoCommitteePersonInfoSearchRepository mpoCommitteePersonInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMpoCommitteePersonInfoMockMvc;

    private MpoCommitteePersonInfo mpoCommitteePersonInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MpoCommitteePersonInfoResource mpoCommitteePersonInfoResource = new MpoCommitteePersonInfoResource();
        ReflectionTestUtils.setField(mpoCommitteePersonInfoResource, "mpoCommitteePersonInfoRepository", mpoCommitteePersonInfoRepository);
        ReflectionTestUtils.setField(mpoCommitteePersonInfoResource, "mpoCommitteePersonInfoSearchRepository", mpoCommitteePersonInfoSearchRepository);
        this.restMpoCommitteePersonInfoMockMvc = MockMvcBuilders.standaloneSetup(mpoCommitteePersonInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mpoCommitteePersonInfo = new MpoCommitteePersonInfo();
        mpoCommitteePersonInfo.setContactNo(DEFAULT_CONTACT_NO);
        mpoCommitteePersonInfo.setAddress(DEFAULT_ADDRESS);
        mpoCommitteePersonInfo.setDesignation(DEFAULT_DESIGNATION);
        mpoCommitteePersonInfo.setOrgName(DEFAULT_ORG_NAME);
        mpoCommitteePersonInfo.setDateCrated(DEFAULT_DATE_CRATED);
        mpoCommitteePersonInfo.setDateModified(DEFAULT_DATE_MODIFIED);
        mpoCommitteePersonInfo.setStatus(DEFAULT_STATUS);
        mpoCommitteePersonInfo.setActivated(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void createMpoCommitteePersonInfo() throws Exception {
        int databaseSizeBeforeCreate = mpoCommitteePersonInfoRepository.findAll().size();

        // Create the MpoCommitteePersonInfo

        restMpoCommitteePersonInfoMockMvc.perform(post("/api/mpoCommitteePersonInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoCommitteePersonInfo)))
                .andExpect(status().isCreated());

        // Validate the MpoCommitteePersonInfo in the database
        List<MpoCommitteePersonInfo> mpoCommitteePersonInfos = mpoCommitteePersonInfoRepository.findAll();
        assertThat(mpoCommitteePersonInfos).hasSize(databaseSizeBeforeCreate + 1);
        MpoCommitteePersonInfo testMpoCommitteePersonInfo = mpoCommitteePersonInfos.get(mpoCommitteePersonInfos.size() - 1);
        assertThat(testMpoCommitteePersonInfo.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testMpoCommitteePersonInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMpoCommitteePersonInfo.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testMpoCommitteePersonInfo.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testMpoCommitteePersonInfo.getDateCrated()).isEqualTo(DEFAULT_DATE_CRATED);
        assertThat(testMpoCommitteePersonInfo.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testMpoCommitteePersonInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMpoCommitteePersonInfo.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllMpoCommitteePersonInfos() throws Exception {
        // Initialize the database
        mpoCommitteePersonInfoRepository.saveAndFlush(mpoCommitteePersonInfo);

        // Get all the mpoCommitteePersonInfos
        restMpoCommitteePersonInfoMockMvc.perform(get("/api/mpoCommitteePersonInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mpoCommitteePersonInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME.toString())))
                .andExpect(jsonPath("$.[*].dateCrated").value(hasItem(DEFAULT_DATE_CRATED.toString())))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
    }

    @Test
    @Transactional
    public void getMpoCommitteePersonInfo() throws Exception {
        // Initialize the database
        mpoCommitteePersonInfoRepository.saveAndFlush(mpoCommitteePersonInfo);

        // Get the mpoCommitteePersonInfo
        restMpoCommitteePersonInfoMockMvc.perform(get("/api/mpoCommitteePersonInfos/{id}", mpoCommitteePersonInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mpoCommitteePersonInfo.getId().intValue()))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME.toString()))
            .andExpect(jsonPath("$.dateCrated").value(DEFAULT_DATE_CRATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMpoCommitteePersonInfo() throws Exception {
        // Get the mpoCommitteePersonInfo
        restMpoCommitteePersonInfoMockMvc.perform(get("/api/mpoCommitteePersonInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMpoCommitteePersonInfo() throws Exception {
        // Initialize the database
        mpoCommitteePersonInfoRepository.saveAndFlush(mpoCommitteePersonInfo);

		int databaseSizeBeforeUpdate = mpoCommitteePersonInfoRepository.findAll().size();

        // Update the mpoCommitteePersonInfo
        mpoCommitteePersonInfo.setContactNo(UPDATED_CONTACT_NO);
        mpoCommitteePersonInfo.setAddress(UPDATED_ADDRESS);
        mpoCommitteePersonInfo.setDesignation(UPDATED_DESIGNATION);
        mpoCommitteePersonInfo.setOrgName(UPDATED_ORG_NAME);
        mpoCommitteePersonInfo.setDateCrated(UPDATED_DATE_CRATED);
        mpoCommitteePersonInfo.setDateModified(UPDATED_DATE_MODIFIED);
        mpoCommitteePersonInfo.setStatus(UPDATED_STATUS);
        mpoCommitteePersonInfo.setActivated(UPDATED_ACTIVATED);

        restMpoCommitteePersonInfoMockMvc.perform(put("/api/mpoCommitteePersonInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mpoCommitteePersonInfo)))
                .andExpect(status().isOk());

        // Validate the MpoCommitteePersonInfo in the database
        List<MpoCommitteePersonInfo> mpoCommitteePersonInfos = mpoCommitteePersonInfoRepository.findAll();
        assertThat(mpoCommitteePersonInfos).hasSize(databaseSizeBeforeUpdate);
        MpoCommitteePersonInfo testMpoCommitteePersonInfo = mpoCommitteePersonInfos.get(mpoCommitteePersonInfos.size() - 1);
        assertThat(testMpoCommitteePersonInfo.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testMpoCommitteePersonInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMpoCommitteePersonInfo.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testMpoCommitteePersonInfo.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testMpoCommitteePersonInfo.getDateCrated()).isEqualTo(UPDATED_DATE_CRATED);
        assertThat(testMpoCommitteePersonInfo.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testMpoCommitteePersonInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMpoCommitteePersonInfo.getActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void deleteMpoCommitteePersonInfo() throws Exception {
        // Initialize the database
        mpoCommitteePersonInfoRepository.saveAndFlush(mpoCommitteePersonInfo);

		int databaseSizeBeforeDelete = mpoCommitteePersonInfoRepository.findAll().size();

        // Get the mpoCommitteePersonInfo
        restMpoCommitteePersonInfoMockMvc.perform(delete("/api/mpoCommitteePersonInfos/{id}", mpoCommitteePersonInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MpoCommitteePersonInfo> mpoCommitteePersonInfos = mpoCommitteePersonInfoRepository.findAll();
        assertThat(mpoCommitteePersonInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
