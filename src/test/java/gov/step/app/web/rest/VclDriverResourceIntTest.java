package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.VclDriver;
import gov.step.app.repository.VclDriverRepository;
import gov.step.app.repository.search.VclDriverSearchRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the VclDriverResource REST controller.
 *
 * @see VclDriverResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VclDriverResourceIntTest {

    private static final String DEFAULT_DRIVER_NAME = "AAAAA";
    private static final String UPDATED_DRIVER_NAME = "BBBBB";
    private static final String DEFAULT_LICENSE_NUMBER = "AAAAA";
    private static final String UPDATED_LICENSE_NUMBER = "BBBBB";
    private static final String DEFAULT_PRESENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PRESENT_ADDRESS = "BBBBB";
    private static final String DEFAULT_PERMANENT_ADDRESS = "AAAAA";
    private static final String UPDATED_PERMANENT_ADDRESS = "BBBBB";
    private static final String DEFAULT_MOBILE_NUMBER = "AAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBB";
    private static final String DEFAULT_EMERGENCY_NUMBER = "AAAAA";
    private static final String UPDATED_EMERGENCY_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_JOIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RETIREMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETIREMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_PHOTO_NAME = "AAAAA";
    private static final String UPDATED_PHOTO_NAME = "BBBBB";

    private static final byte[] DEFAULT_DRIVER_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DRIVER_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DRIVER_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DRIVER_PHOTO_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private VclDriverRepository vclDriverRepository;

    @Inject
    private VclDriverSearchRepository vclDriverSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVclDriverMockMvc;

    private VclDriver vclDriver;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VclDriverResource vclDriverResource = new VclDriverResource();
        ReflectionTestUtils.setField(vclDriverResource, "vclDriverSearchRepository", vclDriverSearchRepository);
        ReflectionTestUtils.setField(vclDriverResource, "vclDriverRepository", vclDriverRepository);
        this.restVclDriverMockMvc = MockMvcBuilders.standaloneSetup(vclDriverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vclDriver = new VclDriver();
        vclDriver.setDriverName(DEFAULT_DRIVER_NAME);
        vclDriver.setLicenseNumber(DEFAULT_LICENSE_NUMBER);
        vclDriver.setPresentAddress(DEFAULT_PRESENT_ADDRESS);
        vclDriver.setPermanentAddress(DEFAULT_PERMANENT_ADDRESS);
        vclDriver.setMobileNumber(DEFAULT_MOBILE_NUMBER);
        vclDriver.setEmergencyNumber(DEFAULT_EMERGENCY_NUMBER);
        vclDriver.setJoinDate(DEFAULT_JOIN_DATE);
        vclDriver.setRetirementDate(DEFAULT_RETIREMENT_DATE);
        vclDriver.setPhotoName(DEFAULT_PHOTO_NAME);
        vclDriver.setDriverPhoto(DEFAULT_DRIVER_PHOTO);
        vclDriver.setDriverPhotoContentType(DEFAULT_DRIVER_PHOTO_CONTENT_TYPE);
        vclDriver.setCreateDate(DEFAULT_CREATE_DATE);
        vclDriver.setCreateBy(DEFAULT_CREATE_BY);
        vclDriver.setUpdateDate(DEFAULT_UPDATE_DATE);
        vclDriver.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createVclDriver() throws Exception {
        int databaseSizeBeforeCreate = vclDriverRepository.findAll().size();

        // Create the VclDriver

        restVclDriverMockMvc.perform(post("/api/vclDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclDriver)))
                .andExpect(status().isCreated());

        // Validate the VclDriver in the database
        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeCreate + 1);
        VclDriver testVclDriver = vclDrivers.get(vclDrivers.size() - 1);
        assertThat(testVclDriver.getDriverName()).isEqualTo(DEFAULT_DRIVER_NAME);
        assertThat(testVclDriver.getLicenseNumber()).isEqualTo(DEFAULT_LICENSE_NUMBER);
        assertThat(testVclDriver.getPresentAddress()).isEqualTo(DEFAULT_PRESENT_ADDRESS);
        assertThat(testVclDriver.getPermanentAddress()).isEqualTo(DEFAULT_PERMANENT_ADDRESS);
        assertThat(testVclDriver.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testVclDriver.getEmergencyNumber()).isEqualTo(DEFAULT_EMERGENCY_NUMBER);
        assertThat(testVclDriver.getJoinDate()).isEqualTo(DEFAULT_JOIN_DATE);
        assertThat(testVclDriver.getRetirementDate()).isEqualTo(DEFAULT_RETIREMENT_DATE);
        assertThat(testVclDriver.getPhotoName()).isEqualTo(DEFAULT_PHOTO_NAME);
        assertThat(testVclDriver.getDriverPhoto()).isEqualTo(DEFAULT_DRIVER_PHOTO);
        assertThat(testVclDriver.getDriverPhotoContentType()).isEqualTo(DEFAULT_DRIVER_PHOTO_CONTENT_TYPE);
        assertThat(testVclDriver.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVclDriver.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testVclDriver.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testVclDriver.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkDriverNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclDriverRepository.findAll().size();
        // set the field null
        vclDriver.setDriverName(null);

        // Create the VclDriver, which fails.

        restVclDriverMockMvc.perform(post("/api/vclDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclDriver)))
                .andExpect(status().isBadRequest());

        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicenseNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclDriverRepository.findAll().size();
        // set the field null
        vclDriver.setLicenseNumber(null);

        // Create the VclDriver, which fails.

        restVclDriverMockMvc.perform(post("/api/vclDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclDriver)))
                .andExpect(status().isBadRequest());

        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresentAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclDriverRepository.findAll().size();
        // set the field null
        vclDriver.setPresentAddress(null);

        // Create the VclDriver, which fails.

        restVclDriverMockMvc.perform(post("/api/vclDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclDriver)))
                .andExpect(status().isBadRequest());

        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPermanentAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclDriverRepository.findAll().size();
        // set the field null
        vclDriver.setPermanentAddress(null);

        // Create the VclDriver, which fails.

        restVclDriverMockMvc.perform(post("/api/vclDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclDriver)))
                .andExpect(status().isBadRequest());

        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclDriverRepository.findAll().size();
        // set the field null
        vclDriver.setMobileNumber(null);

        // Create the VclDriver, which fails.

        restVclDriverMockMvc.perform(post("/api/vclDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclDriver)))
                .andExpect(status().isBadRequest());

        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmergencyNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclDriverRepository.findAll().size();
        // set the field null
        vclDriver.setEmergencyNumber(null);

        // Create the VclDriver, which fails.

        restVclDriverMockMvc.perform(post("/api/vclDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclDriver)))
                .andExpect(status().isBadRequest());

        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJoinDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclDriverRepository.findAll().size();
        // set the field null
        vclDriver.setJoinDate(null);

        // Create the VclDriver, which fails.

        restVclDriverMockMvc.perform(post("/api/vclDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclDriver)))
                .andExpect(status().isBadRequest());

        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetirementDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclDriverRepository.findAll().size();
        // set the field null
        vclDriver.setRetirementDate(null);

        // Create the VclDriver, which fails.

        restVclDriverMockMvc.perform(post("/api/vclDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclDriver)))
                .andExpect(status().isBadRequest());

        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVclDrivers() throws Exception {
        // Initialize the database
        vclDriverRepository.saveAndFlush(vclDriver);

        // Get all the vclDrivers
        restVclDriverMockMvc.perform(get("/api/vclDrivers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vclDriver.getId().intValue())))
                .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME.toString())))
                .andExpect(jsonPath("$.[*].licenseNumber").value(hasItem(DEFAULT_LICENSE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].emergencyNumber").value(hasItem(DEFAULT_EMERGENCY_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].joinDate").value(hasItem(DEFAULT_JOIN_DATE.toString())))
                .andExpect(jsonPath("$.[*].retirementDate").value(hasItem(DEFAULT_RETIREMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].photoName").value(hasItem(DEFAULT_PHOTO_NAME.toString())))
                .andExpect(jsonPath("$.[*].driverPhotoContentType").value(hasItem(DEFAULT_DRIVER_PHOTO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].driverPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_DRIVER_PHOTO))))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getVclDriver() throws Exception {
        // Initialize the database
        vclDriverRepository.saveAndFlush(vclDriver);

        // Get the vclDriver
        restVclDriverMockMvc.perform(get("/api/vclDrivers/{id}", vclDriver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vclDriver.getId().intValue()))
            .andExpect(jsonPath("$.driverName").value(DEFAULT_DRIVER_NAME.toString()))
            .andExpect(jsonPath("$.licenseNumber").value(DEFAULT_LICENSE_NUMBER.toString()))
            .andExpect(jsonPath("$.presentAddress").value(DEFAULT_PRESENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.permanentAddress").value(DEFAULT_PERMANENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER.toString()))
            .andExpect(jsonPath("$.emergencyNumber").value(DEFAULT_EMERGENCY_NUMBER.toString()))
            .andExpect(jsonPath("$.joinDate").value(DEFAULT_JOIN_DATE.toString()))
            .andExpect(jsonPath("$.retirementDate").value(DEFAULT_RETIREMENT_DATE.toString()))
            .andExpect(jsonPath("$.photoName").value(DEFAULT_PHOTO_NAME.toString()))
            .andExpect(jsonPath("$.driverPhotoContentType").value(DEFAULT_DRIVER_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.driverPhoto").value(Base64Utils.encodeToString(DEFAULT_DRIVER_PHOTO)))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVclDriver() throws Exception {
        // Get the vclDriver
        restVclDriverMockMvc.perform(get("/api/vclDrivers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVclDriver() throws Exception {
        // Initialize the database
        vclDriverRepository.saveAndFlush(vclDriver);

		int databaseSizeBeforeUpdate = vclDriverRepository.findAll().size();

        // Update the vclDriver
        vclDriver.setDriverName(UPDATED_DRIVER_NAME);
        vclDriver.setLicenseNumber(UPDATED_LICENSE_NUMBER);
        vclDriver.setPresentAddress(UPDATED_PRESENT_ADDRESS);
        vclDriver.setPermanentAddress(UPDATED_PERMANENT_ADDRESS);
        vclDriver.setMobileNumber(UPDATED_MOBILE_NUMBER);
        vclDriver.setEmergencyNumber(UPDATED_EMERGENCY_NUMBER);
        vclDriver.setJoinDate(UPDATED_JOIN_DATE);
        vclDriver.setRetirementDate(UPDATED_RETIREMENT_DATE);
        vclDriver.setPhotoName(UPDATED_PHOTO_NAME);
        vclDriver.setDriverPhoto(UPDATED_DRIVER_PHOTO);
        vclDriver.setDriverPhotoContentType(UPDATED_DRIVER_PHOTO_CONTENT_TYPE);
        vclDriver.setCreateDate(UPDATED_CREATE_DATE);
        vclDriver.setCreateBy(UPDATED_CREATE_BY);
        vclDriver.setUpdateDate(UPDATED_UPDATE_DATE);
        vclDriver.setUpdateBy(UPDATED_UPDATE_BY);

        restVclDriverMockMvc.perform(put("/api/vclDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclDriver)))
                .andExpect(status().isOk());

        // Validate the VclDriver in the database
        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeUpdate);
        VclDriver testVclDriver = vclDrivers.get(vclDrivers.size() - 1);
        assertThat(testVclDriver.getDriverName()).isEqualTo(UPDATED_DRIVER_NAME);
        assertThat(testVclDriver.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testVclDriver.getPresentAddress()).isEqualTo(UPDATED_PRESENT_ADDRESS);
        assertThat(testVclDriver.getPermanentAddress()).isEqualTo(UPDATED_PERMANENT_ADDRESS);
        assertThat(testVclDriver.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testVclDriver.getEmergencyNumber()).isEqualTo(UPDATED_EMERGENCY_NUMBER);
        assertThat(testVclDriver.getJoinDate()).isEqualTo(UPDATED_JOIN_DATE);
        assertThat(testVclDriver.getRetirementDate()).isEqualTo(UPDATED_RETIREMENT_DATE);
        assertThat(testVclDriver.getPhotoName()).isEqualTo(UPDATED_PHOTO_NAME);
        assertThat(testVclDriver.getDriverPhoto()).isEqualTo(UPDATED_DRIVER_PHOTO);
        assertThat(testVclDriver.getDriverPhotoContentType()).isEqualTo(UPDATED_DRIVER_PHOTO_CONTENT_TYPE);
        assertThat(testVclDriver.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVclDriver.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testVclDriver.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testVclDriver.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteVclDriver() throws Exception {
        // Initialize the database
        vclDriverRepository.saveAndFlush(vclDriver);

		int databaseSizeBeforeDelete = vclDriverRepository.findAll().size();

        // Get the vclDriver
        restVclDriverMockMvc.perform(delete("/api/vclDrivers/{id}", vclDriver.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VclDriver> vclDrivers = vclDriverRepository.findAll();
        assertThat(vclDrivers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
