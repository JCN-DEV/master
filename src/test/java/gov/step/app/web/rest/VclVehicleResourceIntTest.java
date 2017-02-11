package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.VclVehicle;
import gov.step.app.repository.VclVehicleRepository;
import gov.step.app.repository.search.VclVehicleSearchRepository;

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

import gov.step.app.domain.enumeration.vehicleTypes;
import gov.step.app.domain.enumeration.vehicleStatus;

/**
 * Test class for the VclVehicleResource REST controller.
 *
 * @see VclVehicleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VclVehicleResourceIntTest {

    private static final String DEFAULT_VEHICLE_NAME = "AAAAA";
    private static final String UPDATED_VEHICLE_NAME = "BBBBB";
    private static final String DEFAULT_VEHICLE_NUMBER = "AAAAA";
    private static final String UPDATED_VEHICLE_NUMBER = "BBBBB";


    private static final vehicleTypes DEFAULT_VEHICLE_TYPE = vehicleTypes.PrivateCar;
    private static final vehicleTypes UPDATED_VEHICLE_TYPE = vehicleTypes.Truck;
    private static final String DEFAULT_LICENSE_NUMBER = "AAAAA";
    private static final String UPDATED_LICENSE_NUMBER = "BBBBB";
    private static final String DEFAULT_CHASSIS_NUMBER = "AAAAA";
    private static final String UPDATED_CHASSIS_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BUYING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BUYING = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_SUPPLIER_NAME = "AAAAA";
    private static final String UPDATED_SUPPLIER_NAME = "BBBBB";

    private static final Integer DEFAULT_NO_OF_SEATS = 1;
    private static final Integer UPDATED_NO_OF_SEATS = 2;

    private static final Integer DEFAULT_PASSENGER_CAPACITY = 1;
    private static final Integer UPDATED_PASSENGER_CAPACITY = 2;


    private static final vehicleStatus DEFAULT_VEHICLE_AVAILABILITY = vehicleStatus.Available;
    private static final vehicleStatus UPDATED_VEHICLE_AVAILABILITY = vehicleStatus.Repairing;

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
    private VclVehicleRepository vclVehicleRepository;

    @Inject
    private VclVehicleSearchRepository vclVehicleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVclVehicleMockMvc;

    private VclVehicle vclVehicle;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VclVehicleResource vclVehicleResource = new VclVehicleResource();
        ReflectionTestUtils.setField(vclVehicleResource, "vclVehicleSearchRepository", vclVehicleSearchRepository);
        ReflectionTestUtils.setField(vclVehicleResource, "vclVehicleRepository", vclVehicleRepository);
        this.restVclVehicleMockMvc = MockMvcBuilders.standaloneSetup(vclVehicleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vclVehicle = new VclVehicle();
        vclVehicle.setVehicleName(DEFAULT_VEHICLE_NAME);
        vclVehicle.setVehicleNumber(DEFAULT_VEHICLE_NUMBER);
        vclVehicle.setVehicleType(DEFAULT_VEHICLE_TYPE);
        vclVehicle.setLicenseNumber(DEFAULT_LICENSE_NUMBER);
        vclVehicle.setChassisNumber(DEFAULT_CHASSIS_NUMBER);
        vclVehicle.setDateOfBuying(DEFAULT_DATE_OF_BUYING);
        vclVehicle.setSupplierName(DEFAULT_SUPPLIER_NAME);
        vclVehicle.setNoOfSeats(DEFAULT_NO_OF_SEATS);
        vclVehicle.setPassengerCapacity(DEFAULT_PASSENGER_CAPACITY);
        vclVehicle.setVehicleAvailability(DEFAULT_VEHICLE_AVAILABILITY);
        vclVehicle.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        vclVehicle.setCreateDate(DEFAULT_CREATE_DATE);
        vclVehicle.setCreateBy(DEFAULT_CREATE_BY);
        vclVehicle.setUpdateDate(DEFAULT_UPDATE_DATE);
        vclVehicle.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createVclVehicle() throws Exception {
        int databaseSizeBeforeCreate = vclVehicleRepository.findAll().size();

        // Create the VclVehicle

        restVclVehicleMockMvc.perform(post("/api/vclVehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicle)))
                .andExpect(status().isCreated());

        // Validate the VclVehicle in the database
        List<VclVehicle> vclVehicles = vclVehicleRepository.findAll();
        assertThat(vclVehicles).hasSize(databaseSizeBeforeCreate + 1);
        VclVehicle testVclVehicle = vclVehicles.get(vclVehicles.size() - 1);
        assertThat(testVclVehicle.getVehicleName()).isEqualTo(DEFAULT_VEHICLE_NAME);
        assertThat(testVclVehicle.getVehicleNumber()).isEqualTo(DEFAULT_VEHICLE_NUMBER);
        assertThat(testVclVehicle.getVehicleType()).isEqualTo(DEFAULT_VEHICLE_TYPE);
        assertThat(testVclVehicle.getLicenseNumber()).isEqualTo(DEFAULT_LICENSE_NUMBER);
        assertThat(testVclVehicle.getChassisNumber()).isEqualTo(DEFAULT_CHASSIS_NUMBER);
        assertThat(testVclVehicle.getDateOfBuying()).isEqualTo(DEFAULT_DATE_OF_BUYING);
        assertThat(testVclVehicle.getSupplierName()).isEqualTo(DEFAULT_SUPPLIER_NAME);
        assertThat(testVclVehicle.getNoOfSeats()).isEqualTo(DEFAULT_NO_OF_SEATS);
        assertThat(testVclVehicle.getPassengerCapacity()).isEqualTo(DEFAULT_PASSENGER_CAPACITY);
        assertThat(testVclVehicle.getVehicleAvailability()).isEqualTo(DEFAULT_VEHICLE_AVAILABILITY);
        assertThat(testVclVehicle.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testVclVehicle.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVclVehicle.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testVclVehicle.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testVclVehicle.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkVehicleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclVehicleRepository.findAll().size();
        // set the field null
        vclVehicle.setVehicleName(null);

        // Create the VclVehicle, which fails.

        restVclVehicleMockMvc.perform(post("/api/vclVehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicle)))
                .andExpect(status().isBadRequest());

        List<VclVehicle> vclVehicles = vclVehicleRepository.findAll();
        assertThat(vclVehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVehicleNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclVehicleRepository.findAll().size();
        // set the field null
        vclVehicle.setVehicleNumber(null);

        // Create the VclVehicle, which fails.

        restVclVehicleMockMvc.perform(post("/api/vclVehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicle)))
                .andExpect(status().isBadRequest());

        List<VclVehicle> vclVehicles = vclVehicleRepository.findAll();
        assertThat(vclVehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicenseNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclVehicleRepository.findAll().size();
        // set the field null
        vclVehicle.setLicenseNumber(null);

        // Create the VclVehicle, which fails.

        restVclVehicleMockMvc.perform(post("/api/vclVehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicle)))
                .andExpect(status().isBadRequest());

        List<VclVehicle> vclVehicles = vclVehicleRepository.findAll();
        assertThat(vclVehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChassisNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclVehicleRepository.findAll().size();
        // set the field null
        vclVehicle.setChassisNumber(null);

        // Create the VclVehicle, which fails.

        restVclVehicleMockMvc.perform(post("/api/vclVehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicle)))
                .andExpect(status().isBadRequest());

        List<VclVehicle> vclVehicles = vclVehicleRepository.findAll();
        assertThat(vclVehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBuyingIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclVehicleRepository.findAll().size();
        // set the field null
        vclVehicle.setDateOfBuying(null);

        // Create the VclVehicle, which fails.

        restVclVehicleMockMvc.perform(post("/api/vclVehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicle)))
                .andExpect(status().isBadRequest());

        List<VclVehicle> vclVehicles = vclVehicleRepository.findAll();
        assertThat(vclVehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoOfSeatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclVehicleRepository.findAll().size();
        // set the field null
        vclVehicle.setNoOfSeats(null);

        // Create the VclVehicle, which fails.

        restVclVehicleMockMvc.perform(post("/api/vclVehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicle)))
                .andExpect(status().isBadRequest());

        List<VclVehicle> vclVehicles = vclVehicleRepository.findAll();
        assertThat(vclVehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassengerCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclVehicleRepository.findAll().size();
        // set the field null
        vclVehicle.setPassengerCapacity(null);

        // Create the VclVehicle, which fails.

        restVclVehicleMockMvc.perform(post("/api/vclVehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicle)))
                .andExpect(status().isBadRequest());

        List<VclVehicle> vclVehicles = vclVehicleRepository.findAll();
        assertThat(vclVehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVclVehicles() throws Exception {
        // Initialize the database
        vclVehicleRepository.saveAndFlush(vclVehicle);

        // Get all the vclVehicles
        restVclVehicleMockMvc.perform(get("/api/vclVehicles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vclVehicle.getId().intValue())))
                .andExpect(jsonPath("$.[*].vehicleName").value(hasItem(DEFAULT_VEHICLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].vehicleNumber").value(hasItem(DEFAULT_VEHICLE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].vehicleType").value(hasItem(DEFAULT_VEHICLE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].licenseNumber").value(hasItem(DEFAULT_LICENSE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].chassisNumber").value(hasItem(DEFAULT_CHASSIS_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].dateOfBuying").value(hasItem(DEFAULT_DATE_OF_BUYING.toString())))
                .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME.toString())))
                .andExpect(jsonPath("$.[*].noOfSeats").value(hasItem(DEFAULT_NO_OF_SEATS)))
                .andExpect(jsonPath("$.[*].passengerCapacity").value(hasItem(DEFAULT_PASSENGER_CAPACITY)))
                .andExpect(jsonPath("$.[*].vehicleAvailability").value(hasItem(DEFAULT_VEHICLE_AVAILABILITY.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getVclVehicle() throws Exception {
        // Initialize the database
        vclVehicleRepository.saveAndFlush(vclVehicle);

        // Get the vclVehicle
        restVclVehicleMockMvc.perform(get("/api/vclVehicles/{id}", vclVehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vclVehicle.getId().intValue()))
            .andExpect(jsonPath("$.vehicleName").value(DEFAULT_VEHICLE_NAME.toString()))
            .andExpect(jsonPath("$.vehicleNumber").value(DEFAULT_VEHICLE_NUMBER.toString()))
            .andExpect(jsonPath("$.vehicleType").value(DEFAULT_VEHICLE_TYPE.toString()))
            .andExpect(jsonPath("$.licenseNumber").value(DEFAULT_LICENSE_NUMBER.toString()))
            .andExpect(jsonPath("$.chassisNumber").value(DEFAULT_CHASSIS_NUMBER.toString()))
            .andExpect(jsonPath("$.dateOfBuying").value(DEFAULT_DATE_OF_BUYING.toString()))
            .andExpect(jsonPath("$.supplierName").value(DEFAULT_SUPPLIER_NAME.toString()))
            .andExpect(jsonPath("$.noOfSeats").value(DEFAULT_NO_OF_SEATS))
            .andExpect(jsonPath("$.passengerCapacity").value(DEFAULT_PASSENGER_CAPACITY))
            .andExpect(jsonPath("$.vehicleAvailability").value(DEFAULT_VEHICLE_AVAILABILITY.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVclVehicle() throws Exception {
        // Get the vclVehicle
        restVclVehicleMockMvc.perform(get("/api/vclVehicles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVclVehicle() throws Exception {
        // Initialize the database
        vclVehicleRepository.saveAndFlush(vclVehicle);

		int databaseSizeBeforeUpdate = vclVehicleRepository.findAll().size();

        // Update the vclVehicle
        vclVehicle.setVehicleName(UPDATED_VEHICLE_NAME);
        vclVehicle.setVehicleNumber(UPDATED_VEHICLE_NUMBER);
        vclVehicle.setVehicleType(UPDATED_VEHICLE_TYPE);
        vclVehicle.setLicenseNumber(UPDATED_LICENSE_NUMBER);
        vclVehicle.setChassisNumber(UPDATED_CHASSIS_NUMBER);
        vclVehicle.setDateOfBuying(UPDATED_DATE_OF_BUYING);
        vclVehicle.setSupplierName(UPDATED_SUPPLIER_NAME);
        vclVehicle.setNoOfSeats(UPDATED_NO_OF_SEATS);
        vclVehicle.setPassengerCapacity(UPDATED_PASSENGER_CAPACITY);
        vclVehicle.setVehicleAvailability(UPDATED_VEHICLE_AVAILABILITY);
        vclVehicle.setActiveStatus(UPDATED_ACTIVE_STATUS);
        vclVehicle.setCreateDate(UPDATED_CREATE_DATE);
        vclVehicle.setCreateBy(UPDATED_CREATE_BY);
        vclVehicle.setUpdateDate(UPDATED_UPDATE_DATE);
        vclVehicle.setUpdateBy(UPDATED_UPDATE_BY);

        restVclVehicleMockMvc.perform(put("/api/vclVehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicle)))
                .andExpect(status().isOk());

        // Validate the VclVehicle in the database
        List<VclVehicle> vclVehicles = vclVehicleRepository.findAll();
        assertThat(vclVehicles).hasSize(databaseSizeBeforeUpdate);
        VclVehicle testVclVehicle = vclVehicles.get(vclVehicles.size() - 1);
        assertThat(testVclVehicle.getVehicleName()).isEqualTo(UPDATED_VEHICLE_NAME);
        assertThat(testVclVehicle.getVehicleNumber()).isEqualTo(UPDATED_VEHICLE_NUMBER);
        assertThat(testVclVehicle.getVehicleType()).isEqualTo(UPDATED_VEHICLE_TYPE);
        assertThat(testVclVehicle.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testVclVehicle.getChassisNumber()).isEqualTo(UPDATED_CHASSIS_NUMBER);
        assertThat(testVclVehicle.getDateOfBuying()).isEqualTo(UPDATED_DATE_OF_BUYING);
        assertThat(testVclVehicle.getSupplierName()).isEqualTo(UPDATED_SUPPLIER_NAME);
        assertThat(testVclVehicle.getNoOfSeats()).isEqualTo(UPDATED_NO_OF_SEATS);
        assertThat(testVclVehicle.getPassengerCapacity()).isEqualTo(UPDATED_PASSENGER_CAPACITY);
        assertThat(testVclVehicle.getVehicleAvailability()).isEqualTo(UPDATED_VEHICLE_AVAILABILITY);
        assertThat(testVclVehicle.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testVclVehicle.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVclVehicle.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testVclVehicle.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testVclVehicle.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteVclVehicle() throws Exception {
        // Initialize the database
        vclVehicleRepository.saveAndFlush(vclVehicle);

		int databaseSizeBeforeDelete = vclVehicleRepository.findAll().size();

        // Get the vclVehicle
        restVclVehicleMockMvc.perform(delete("/api/vclVehicles/{id}", vclVehicle.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VclVehicle> vclVehicles = vclVehicleRepository.findAll();
        assertThat(vclVehicles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
