package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpWorkAreaDtlInfo;
import gov.step.app.repository.HrEmpWorkAreaDtlInfoRepository;
import gov.step.app.repository.search.HrEmpWorkAreaDtlInfoSearchRepository;
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
 * Test class for the HrEmpWorkAreaDtlInfoResource REST controller.
 *
 * @see HrEmpWorkAreaDtlInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpWorkAreaDtlInfoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_ESTABLISHMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTABLISHMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CONTACT_NUMBER = "AAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_TELEPHONE_NUMBER = "AAAAA";
    private static final String UPDATED_TELEPHONE_NUMBER = "BBBBB";
    private static final String DEFAULT_FAX_NUMBER = "AAAAA";
    private static final String UPDATED_FAX_NUMBER = "BBBBB";
    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBB";

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
    private HrEmpWorkAreaDtlInfoRepository hrEmpWorkAreaDtlInfoRepository;

    @Inject
    private HrEmpWorkAreaDtlInfoSearchRepository hrEmpWorkAreaDtlInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpWorkAreaDtlInfoMockMvc;

    private HrEmpWorkAreaDtlInfo hrEmpWorkAreaDtlInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpWorkAreaDtlInfoResource hrEmpWorkAreaDtlInfoResource = new HrEmpWorkAreaDtlInfoResource();
        ReflectionTestUtils.setField(hrEmpWorkAreaDtlInfoResource, "hrEmpWorkAreaDtlInfoSearchRepository", hrEmpWorkAreaDtlInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpWorkAreaDtlInfoResource, "hrEmpWorkAreaDtlInfoRepository", hrEmpWorkAreaDtlInfoRepository);
        this.restHrEmpWorkAreaDtlInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpWorkAreaDtlInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpWorkAreaDtlInfo = new HrEmpWorkAreaDtlInfo();
        hrEmpWorkAreaDtlInfo.setName(DEFAULT_NAME);
        hrEmpWorkAreaDtlInfo.setEstablishmentDate(DEFAULT_ESTABLISHMENT_DATE);
        hrEmpWorkAreaDtlInfo.setContactNumber(DEFAULT_CONTACT_NUMBER);
        hrEmpWorkAreaDtlInfo.setAddress(DEFAULT_ADDRESS);
        hrEmpWorkAreaDtlInfo.setTelephoneNumber(DEFAULT_TELEPHONE_NUMBER);
        hrEmpWorkAreaDtlInfo.setFaxNumber(DEFAULT_FAX_NUMBER);
        hrEmpWorkAreaDtlInfo.setEmailAddress(DEFAULT_EMAIL_ADDRESS);
        hrEmpWorkAreaDtlInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpWorkAreaDtlInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpWorkAreaDtlInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpWorkAreaDtlInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpWorkAreaDtlInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpWorkAreaDtlInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpWorkAreaDtlInfoRepository.findAll().size();

        // Create the HrEmpWorkAreaDtlInfo

        restHrEmpWorkAreaDtlInfoMockMvc.perform(post("/api/hrEmpWorkAreaDtlInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpWorkAreaDtlInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpWorkAreaDtlInfo in the database
        List<HrEmpWorkAreaDtlInfo> hrEmpWorkAreaDtlInfos = hrEmpWorkAreaDtlInfoRepository.findAll();
        assertThat(hrEmpWorkAreaDtlInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpWorkAreaDtlInfo testHrEmpWorkAreaDtlInfo = hrEmpWorkAreaDtlInfos.get(hrEmpWorkAreaDtlInfos.size() - 1);
        assertThat(testHrEmpWorkAreaDtlInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHrEmpWorkAreaDtlInfo.getEstablishmentDate()).isEqualTo(DEFAULT_ESTABLISHMENT_DATE);
        assertThat(testHrEmpWorkAreaDtlInfo.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testHrEmpWorkAreaDtlInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHrEmpWorkAreaDtlInfo.getTelephoneNumber()).isEqualTo(DEFAULT_TELEPHONE_NUMBER);
        assertThat(testHrEmpWorkAreaDtlInfo.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testHrEmpWorkAreaDtlInfo.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testHrEmpWorkAreaDtlInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpWorkAreaDtlInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpWorkAreaDtlInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpWorkAreaDtlInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpWorkAreaDtlInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpWorkAreaDtlInfoRepository.findAll().size();
        // set the field null
        hrEmpWorkAreaDtlInfo.setName(null);

        // Create the HrEmpWorkAreaDtlInfo, which fails.

        restHrEmpWorkAreaDtlInfoMockMvc.perform(post("/api/hrEmpWorkAreaDtlInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpWorkAreaDtlInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpWorkAreaDtlInfo> hrEmpWorkAreaDtlInfos = hrEmpWorkAreaDtlInfoRepository.findAll();
        assertThat(hrEmpWorkAreaDtlInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpWorkAreaDtlInfoRepository.findAll().size();
        // set the field null
        hrEmpWorkAreaDtlInfo.setActiveStatus(null);

        // Create the HrEmpWorkAreaDtlInfo, which fails.

        restHrEmpWorkAreaDtlInfoMockMvc.perform(post("/api/hrEmpWorkAreaDtlInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpWorkAreaDtlInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpWorkAreaDtlInfo> hrEmpWorkAreaDtlInfos = hrEmpWorkAreaDtlInfoRepository.findAll();
        assertThat(hrEmpWorkAreaDtlInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpWorkAreaDtlInfos() throws Exception {
        // Initialize the database
        hrEmpWorkAreaDtlInfoRepository.saveAndFlush(hrEmpWorkAreaDtlInfo);

        // Get all the hrEmpWorkAreaDtlInfos
        restHrEmpWorkAreaDtlInfoMockMvc.perform(get("/api/hrEmpWorkAreaDtlInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpWorkAreaDtlInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].establishmentDate").value(hasItem(DEFAULT_ESTABLISHMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmpWorkAreaDtlInfo() throws Exception {
        // Initialize the database
        hrEmpWorkAreaDtlInfoRepository.saveAndFlush(hrEmpWorkAreaDtlInfo);

        // Get the hrEmpWorkAreaDtlInfo
        restHrEmpWorkAreaDtlInfoMockMvc.perform(get("/api/hrEmpWorkAreaDtlInfos/{id}", hrEmpWorkAreaDtlInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpWorkAreaDtlInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.establishmentDate").value(DEFAULT_ESTABLISHMENT_DATE.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.telephoneNumber").value(DEFAULT_TELEPHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmpWorkAreaDtlInfo() throws Exception {
        // Get the hrEmpWorkAreaDtlInfo
        restHrEmpWorkAreaDtlInfoMockMvc.perform(get("/api/hrEmpWorkAreaDtlInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpWorkAreaDtlInfo() throws Exception {
        // Initialize the database
        hrEmpWorkAreaDtlInfoRepository.saveAndFlush(hrEmpWorkAreaDtlInfo);

		int databaseSizeBeforeUpdate = hrEmpWorkAreaDtlInfoRepository.findAll().size();

        // Update the hrEmpWorkAreaDtlInfo
        hrEmpWorkAreaDtlInfo.setName(UPDATED_NAME);
        hrEmpWorkAreaDtlInfo.setEstablishmentDate(UPDATED_ESTABLISHMENT_DATE);
        hrEmpWorkAreaDtlInfo.setContactNumber(UPDATED_CONTACT_NUMBER);
        hrEmpWorkAreaDtlInfo.setAddress(UPDATED_ADDRESS);
        hrEmpWorkAreaDtlInfo.setTelephoneNumber(UPDATED_TELEPHONE_NUMBER);
        hrEmpWorkAreaDtlInfo.setFaxNumber(UPDATED_FAX_NUMBER);
        hrEmpWorkAreaDtlInfo.setEmailAddress(UPDATED_EMAIL_ADDRESS);
        hrEmpWorkAreaDtlInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpWorkAreaDtlInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpWorkAreaDtlInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpWorkAreaDtlInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpWorkAreaDtlInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpWorkAreaDtlInfoMockMvc.perform(put("/api/hrEmpWorkAreaDtlInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpWorkAreaDtlInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpWorkAreaDtlInfo in the database
        List<HrEmpWorkAreaDtlInfo> hrEmpWorkAreaDtlInfos = hrEmpWorkAreaDtlInfoRepository.findAll();
        assertThat(hrEmpWorkAreaDtlInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpWorkAreaDtlInfo testHrEmpWorkAreaDtlInfo = hrEmpWorkAreaDtlInfos.get(hrEmpWorkAreaDtlInfos.size() - 1);
        assertThat(testHrEmpWorkAreaDtlInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHrEmpWorkAreaDtlInfo.getEstablishmentDate()).isEqualTo(UPDATED_ESTABLISHMENT_DATE);
        assertThat(testHrEmpWorkAreaDtlInfo.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testHrEmpWorkAreaDtlInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHrEmpWorkAreaDtlInfo.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
        assertThat(testHrEmpWorkAreaDtlInfo.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testHrEmpWorkAreaDtlInfo.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testHrEmpWorkAreaDtlInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpWorkAreaDtlInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpWorkAreaDtlInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpWorkAreaDtlInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpWorkAreaDtlInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpWorkAreaDtlInfo() throws Exception {
        // Initialize the database
        hrEmpWorkAreaDtlInfoRepository.saveAndFlush(hrEmpWorkAreaDtlInfo);

		int databaseSizeBeforeDelete = hrEmpWorkAreaDtlInfoRepository.findAll().size();

        // Get the hrEmpWorkAreaDtlInfo
        restHrEmpWorkAreaDtlInfoMockMvc.perform(delete("/api/hrEmpWorkAreaDtlInfos/{id}", hrEmpWorkAreaDtlInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpWorkAreaDtlInfo> hrEmpWorkAreaDtlInfos = hrEmpWorkAreaDtlInfoRepository.findAll();
        assertThat(hrEmpWorkAreaDtlInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
