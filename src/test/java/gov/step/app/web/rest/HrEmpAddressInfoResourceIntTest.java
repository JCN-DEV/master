package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.HrEmpAddressInfo;
import gov.step.app.domain.enumeration.addressTypes;
import gov.step.app.repository.HrEmpAddressInfoRepository;
import gov.step.app.repository.search.HrEmpAddressInfoSearchRepository;
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
 * Test class for the HrEmpAddressInfoResource REST controller.
 *
 * @see HrEmpAddressInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HrEmpAddressInfoResourceIntTest {



    private static final addressTypes DEFAULT_ADDRESS_TYPE = addressTypes.Permanent;
    private static final addressTypes UPDATED_ADDRESS_TYPE = addressTypes.Present;
    private static final String DEFAULT_HOUSE_NUMBER = "AAAAA";
    private static final String UPDATED_HOUSE_NUMBER = "BBBBB";
    private static final String DEFAULT_ROAD_NUMBER = "AAAAA";
    private static final String UPDATED_ROAD_NUMBER = "BBBBB";
    private static final String DEFAULT_VILLAGE_NAME = "AAAAA";
    private static final String UPDATED_VILLAGE_NAME = "BBBBB";
    private static final String DEFAULT_POST_OFFICE = "AAAAA";
    private static final String UPDATED_POST_OFFICE = "BBBBB";
    private static final String DEFAULT_POLICE_STATION = "AAAAA";
    private static final String UPDATED_POLICE_STATION = "BBBBB";
    private static final String DEFAULT_DISTRICT = "AAAAA";
    private static final String UPDATED_DISTRICT = "BBBBB";
    private static final String DEFAULT_CONTACT_NUMBER = "AAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBB";

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
    private HrEmpAddressInfoRepository hrEmpAddressInfoRepository;

    @Inject
    private HrEmpAddressInfoSearchRepository hrEmpAddressInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHrEmpAddressInfoMockMvc;

    private HrEmpAddressInfo hrEmpAddressInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HrEmpAddressInfoResource hrEmpAddressInfoResource = new HrEmpAddressInfoResource();
        ReflectionTestUtils.setField(hrEmpAddressInfoResource, "hrEmpAddressInfoSearchRepository", hrEmpAddressInfoSearchRepository);
        ReflectionTestUtils.setField(hrEmpAddressInfoResource, "hrEmpAddressInfoRepository", hrEmpAddressInfoRepository);
        this.restHrEmpAddressInfoMockMvc = MockMvcBuilders.standaloneSetup(hrEmpAddressInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hrEmpAddressInfo = new HrEmpAddressInfo();
        hrEmpAddressInfo.setAddressType(DEFAULT_ADDRESS_TYPE);
        hrEmpAddressInfo.setHouseNumber(DEFAULT_HOUSE_NUMBER);
        hrEmpAddressInfo.setRoadNumber(DEFAULT_ROAD_NUMBER);
        hrEmpAddressInfo.setVillageName(DEFAULT_VILLAGE_NAME);
        hrEmpAddressInfo.setPostOffice(DEFAULT_POST_OFFICE);
        hrEmpAddressInfo.setContactNumber(DEFAULT_CONTACT_NUMBER);
        hrEmpAddressInfo.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        hrEmpAddressInfo.setCreateDate(DEFAULT_CREATE_DATE);
        hrEmpAddressInfo.setCreateBy(DEFAULT_CREATE_BY);
        hrEmpAddressInfo.setUpdateDate(DEFAULT_UPDATE_DATE);
        hrEmpAddressInfo.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createHrEmpAddressInfo() throws Exception {
        int databaseSizeBeforeCreate = hrEmpAddressInfoRepository.findAll().size();

        // Create the HrEmpAddressInfo

        restHrEmpAddressInfoMockMvc.perform(post("/api/hrEmpAddressInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAddressInfo)))
                .andExpect(status().isCreated());

        // Validate the HrEmpAddressInfo in the database
        List<HrEmpAddressInfo> hrEmpAddressInfos = hrEmpAddressInfoRepository.findAll();
        assertThat(hrEmpAddressInfos).hasSize(databaseSizeBeforeCreate + 1);
        HrEmpAddressInfo testHrEmpAddressInfo = hrEmpAddressInfos.get(hrEmpAddressInfos.size() - 1);
        assertThat(testHrEmpAddressInfo.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
        assertThat(testHrEmpAddressInfo.getHouseNumber()).isEqualTo(DEFAULT_HOUSE_NUMBER);
        assertThat(testHrEmpAddressInfo.getRoadNumber()).isEqualTo(DEFAULT_ROAD_NUMBER);
        assertThat(testHrEmpAddressInfo.getVillageName()).isEqualTo(DEFAULT_VILLAGE_NAME);
        assertThat(testHrEmpAddressInfo.getPostOffice()).isEqualTo(DEFAULT_POST_OFFICE);
        assertThat(testHrEmpAddressInfo.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testHrEmpAddressInfo.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testHrEmpAddressInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testHrEmpAddressInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testHrEmpAddressInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testHrEmpAddressInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkAddressTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAddressInfoRepository.findAll().size();
        // set the field null
        hrEmpAddressInfo.setAddressType(null);

        // Create the HrEmpAddressInfo, which fails.

        restHrEmpAddressInfoMockMvc.perform(post("/api/hrEmpAddressInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAddressInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAddressInfo> hrEmpAddressInfos = hrEmpAddressInfoRepository.findAll();
        assertThat(hrEmpAddressInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrEmpAddressInfoRepository.findAll().size();
        // set the field null
        hrEmpAddressInfo.setActiveStatus(null);

        // Create the HrEmpAddressInfo, which fails.

        restHrEmpAddressInfoMockMvc.perform(post("/api/hrEmpAddressInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAddressInfo)))
                .andExpect(status().isBadRequest());

        List<HrEmpAddressInfo> hrEmpAddressInfos = hrEmpAddressInfoRepository.findAll();
        assertThat(hrEmpAddressInfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHrEmpAddressInfos() throws Exception {
        // Initialize the database
        hrEmpAddressInfoRepository.saveAndFlush(hrEmpAddressInfo);

        // Get all the hrEmpAddressInfos
        restHrEmpAddressInfoMockMvc.perform(get("/api/hrEmpAddressInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hrEmpAddressInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
                .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].roadNumber").value(hasItem(DEFAULT_ROAD_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME.toString())))
                .andExpect(jsonPath("$.[*].postOffice").value(hasItem(DEFAULT_POST_OFFICE.toString())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getHrEmpAddressInfo() throws Exception {
        // Initialize the database
        hrEmpAddressInfoRepository.saveAndFlush(hrEmpAddressInfo);

        // Get the hrEmpAddressInfo
        restHrEmpAddressInfoMockMvc.perform(get("/api/hrEmpAddressInfos/{id}", hrEmpAddressInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hrEmpAddressInfo.getId().intValue()))
            .andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER.toString()))
            .andExpect(jsonPath("$.roadNumber").value(DEFAULT_ROAD_NUMBER.toString()))
            .andExpect(jsonPath("$.villageName").value(DEFAULT_VILLAGE_NAME.toString()))
            .andExpect(jsonPath("$.postOffice").value(DEFAULT_POST_OFFICE.toString()))
            .andExpect(jsonPath("$.policeStation").value(DEFAULT_POLICE_STATION.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHrEmpAddressInfo() throws Exception {
        // Get the hrEmpAddressInfo
        restHrEmpAddressInfoMockMvc.perform(get("/api/hrEmpAddressInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrEmpAddressInfo() throws Exception {
        // Initialize the database
        hrEmpAddressInfoRepository.saveAndFlush(hrEmpAddressInfo);

		int databaseSizeBeforeUpdate = hrEmpAddressInfoRepository.findAll().size();

        // Update the hrEmpAddressInfo
        hrEmpAddressInfo.setAddressType(UPDATED_ADDRESS_TYPE);
        hrEmpAddressInfo.setHouseNumber(UPDATED_HOUSE_NUMBER);
        hrEmpAddressInfo.setRoadNumber(UPDATED_ROAD_NUMBER);
        hrEmpAddressInfo.setVillageName(UPDATED_VILLAGE_NAME);
        hrEmpAddressInfo.setPostOffice(UPDATED_POST_OFFICE);
        hrEmpAddressInfo.setContactNumber(UPDATED_CONTACT_NUMBER);
        hrEmpAddressInfo.setActiveStatus(UPDATED_ACTIVE_STATUS);
        hrEmpAddressInfo.setCreateDate(UPDATED_CREATE_DATE);
        hrEmpAddressInfo.setCreateBy(UPDATED_CREATE_BY);
        hrEmpAddressInfo.setUpdateDate(UPDATED_UPDATE_DATE);
        hrEmpAddressInfo.setUpdateBy(UPDATED_UPDATE_BY);

        restHrEmpAddressInfoMockMvc.perform(put("/api/hrEmpAddressInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hrEmpAddressInfo)))
                .andExpect(status().isOk());

        // Validate the HrEmpAddressInfo in the database
        List<HrEmpAddressInfo> hrEmpAddressInfos = hrEmpAddressInfoRepository.findAll();
        assertThat(hrEmpAddressInfos).hasSize(databaseSizeBeforeUpdate);
        HrEmpAddressInfo testHrEmpAddressInfo = hrEmpAddressInfos.get(hrEmpAddressInfos.size() - 1);
        assertThat(testHrEmpAddressInfo.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testHrEmpAddressInfo.getHouseNumber()).isEqualTo(UPDATED_HOUSE_NUMBER);
        assertThat(testHrEmpAddressInfo.getRoadNumber()).isEqualTo(UPDATED_ROAD_NUMBER);
        assertThat(testHrEmpAddressInfo.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testHrEmpAddressInfo.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testHrEmpAddressInfo.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testHrEmpAddressInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testHrEmpAddressInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testHrEmpAddressInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testHrEmpAddressInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteHrEmpAddressInfo() throws Exception {
        // Initialize the database
        hrEmpAddressInfoRepository.saveAndFlush(hrEmpAddressInfo);

		int databaseSizeBeforeDelete = hrEmpAddressInfoRepository.findAll().size();

        // Get the hrEmpAddressInfo
        restHrEmpAddressInfoMockMvc.perform(delete("/api/hrEmpAddressInfos/{id}", hrEmpAddressInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HrEmpAddressInfo> hrEmpAddressInfos = hrEmpAddressInfoRepository.findAll();
        assertThat(hrEmpAddressInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
