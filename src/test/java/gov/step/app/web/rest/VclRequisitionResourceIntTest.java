package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.VclRequisition;
import gov.step.app.repository.VclRequisitionRepository;
import gov.step.app.repository.search.VclRequisitionSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.step.app.domain.enumeration.requisitionTypes;
import gov.step.app.domain.enumeration.vehicleTypes;
import gov.step.app.domain.enumeration.requisitionStatus;

/**
 * Test class for the VclRequisitionResource REST controller.
 *
 * @see VclRequisitionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VclRequisitionResourceIntTest {



    private static final requisitionTypes DEFAULT_REQUISITION_TYPE = requisitionTypes.Personal;
    private static final requisitionTypes UPDATED_REQUISITION_TYPE = requisitionTypes.Government;


    private static final vehicleTypes DEFAULT_VEHICLE_TYPE = vehicleTypes.PrivateCar;
    private static final vehicleTypes UPDATED_VEHICLE_TYPE = vehicleTypes.Truck;
    private static final String DEFAULT_SOURCE_LOCATION = "AAAAA";
    private static final String UPDATED_SOURCE_LOCATION = "BBBBB";
    private static final String DEFAULT_DESTINATION_LOCATION = "AAAAA";
    private static final String UPDATED_DESTINATION_LOCATION = "BBBBB";

    private static final LocalDate DEFAULT_EXPECTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RETURN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETURN_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_REQUISITION_CAUSE = "AAAAA";
    private static final String UPDATED_REQUISITION_CAUSE = "BBBBB";


    private static final requisitionStatus DEFAULT_REQUISITION_STATUS = requisitionStatus.Open;
    private static final requisitionStatus UPDATED_REQUISITION_STATUS = requisitionStatus.Approved;
    private static final String DEFAULT_METER_READING = "AAAAA";
    private static final String UPDATED_METER_READING = "BBBBB";
    private static final String DEFAULT_FUEL_READING = "AAAAA";
    private static final String UPDATED_FUEL_READING = "BBBBB";

    private static final BigDecimal DEFAULT_BILL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BILL_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_EXPECTED_ARRIVAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_ARRIVAL_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    private static final LocalDate DEFAULT_ACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ACTION_BY = 1L;
    private static final Long UPDATED_ACTION_BY = 2L;

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
    private VclRequisitionRepository vclRequisitionRepository;

    @Inject
    private VclRequisitionSearchRepository vclRequisitionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVclRequisitionMockMvc;

    private VclRequisition vclRequisition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VclRequisitionResource vclRequisitionResource = new VclRequisitionResource();
        ReflectionTestUtils.setField(vclRequisitionResource, "vclRequisitionSearchRepository", vclRequisitionSearchRepository);
        ReflectionTestUtils.setField(vclRequisitionResource, "vclRequisitionRepository", vclRequisitionRepository);
        this.restVclRequisitionMockMvc = MockMvcBuilders.standaloneSetup(vclRequisitionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vclRequisition = new VclRequisition();
        vclRequisition.setRequisitionType(DEFAULT_REQUISITION_TYPE);
        vclRequisition.setVehicleType(DEFAULT_VEHICLE_TYPE);
        vclRequisition.setSourceLocation(DEFAULT_SOURCE_LOCATION);
        vclRequisition.setDestinationLocation(DEFAULT_DESTINATION_LOCATION);
        vclRequisition.setExpectedDate(DEFAULT_EXPECTED_DATE);
        vclRequisition.setReturnDate(DEFAULT_RETURN_DATE);
        vclRequisition.setRequisitionCause(DEFAULT_REQUISITION_CAUSE);
        vclRequisition.setRequisitionStatus(DEFAULT_REQUISITION_STATUS);
        vclRequisition.setMeterReading(DEFAULT_METER_READING);
        vclRequisition.setFuelReading(DEFAULT_FUEL_READING);
        vclRequisition.setBillAmount(DEFAULT_BILL_AMOUNT);
        vclRequisition.setExpectedArrivalDate(DEFAULT_EXPECTED_ARRIVAL_DATE);
        vclRequisition.setComments(DEFAULT_COMMENTS);
        vclRequisition.setActionDate(DEFAULT_ACTION_DATE);
        vclRequisition.setActionBy(DEFAULT_ACTION_BY);
        vclRequisition.setActiveStatus(DEFAULT_ACTIVE_STATUS);
        vclRequisition.setCreateDate(DEFAULT_CREATE_DATE);
        vclRequisition.setCreateBy(DEFAULT_CREATE_BY);
        vclRequisition.setUpdateDate(DEFAULT_UPDATE_DATE);
        vclRequisition.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createVclRequisition() throws Exception {
        int databaseSizeBeforeCreate = vclRequisitionRepository.findAll().size();

        // Create the VclRequisition

        restVclRequisitionMockMvc.perform(post("/api/vclRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclRequisition)))
                .andExpect(status().isCreated());

        // Validate the VclRequisition in the database
        List<VclRequisition> vclRequisitions = vclRequisitionRepository.findAll();
        assertThat(vclRequisitions).hasSize(databaseSizeBeforeCreate + 1);
        VclRequisition testVclRequisition = vclRequisitions.get(vclRequisitions.size() - 1);
        assertThat(testVclRequisition.getRequisitionType()).isEqualTo(DEFAULT_REQUISITION_TYPE);
        assertThat(testVclRequisition.getVehicleType()).isEqualTo(DEFAULT_VEHICLE_TYPE);
        assertThat(testVclRequisition.getSourceLocation()).isEqualTo(DEFAULT_SOURCE_LOCATION);
        assertThat(testVclRequisition.getDestinationLocation()).isEqualTo(DEFAULT_DESTINATION_LOCATION);
        assertThat(testVclRequisition.getExpectedDate()).isEqualTo(DEFAULT_EXPECTED_DATE);
        assertThat(testVclRequisition.getReturnDate()).isEqualTo(DEFAULT_RETURN_DATE);
        assertThat(testVclRequisition.getRequisitionCause()).isEqualTo(DEFAULT_REQUISITION_CAUSE);
        assertThat(testVclRequisition.getRequisitionStatus()).isEqualTo(DEFAULT_REQUISITION_STATUS);
        assertThat(testVclRequisition.getMeterReading()).isEqualTo(DEFAULT_METER_READING);
        assertThat(testVclRequisition.getFuelReading()).isEqualTo(DEFAULT_FUEL_READING);
        assertThat(testVclRequisition.getBillAmount()).isEqualTo(DEFAULT_BILL_AMOUNT);
        assertThat(testVclRequisition.getExpectedArrivalDate()).isEqualTo(DEFAULT_EXPECTED_ARRIVAL_DATE);
        assertThat(testVclRequisition.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testVclRequisition.getActionDate()).isEqualTo(DEFAULT_ACTION_DATE);
        assertThat(testVclRequisition.getActionBy()).isEqualTo(DEFAULT_ACTION_BY);
        assertThat(testVclRequisition.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testVclRequisition.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVclRequisition.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testVclRequisition.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testVclRequisition.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkRequisitionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclRequisitionRepository.findAll().size();
        // set the field null
        vclRequisition.setRequisitionType(null);

        // Create the VclRequisition, which fails.

        restVclRequisitionMockMvc.perform(post("/api/vclRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclRequisition)))
                .andExpect(status().isBadRequest());

        List<VclRequisition> vclRequisitions = vclRequisitionRepository.findAll();
        assertThat(vclRequisitions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVehicleTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclRequisitionRepository.findAll().size();
        // set the field null
        vclRequisition.setVehicleType(null);

        // Create the VclRequisition, which fails.

        restVclRequisitionMockMvc.perform(post("/api/vclRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclRequisition)))
                .andExpect(status().isBadRequest());

        List<VclRequisition> vclRequisitions = vclRequisitionRepository.findAll();
        assertThat(vclRequisitions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSourceLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclRequisitionRepository.findAll().size();
        // set the field null
        vclRequisition.setSourceLocation(null);

        // Create the VclRequisition, which fails.

        restVclRequisitionMockMvc.perform(post("/api/vclRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclRequisition)))
                .andExpect(status().isBadRequest());

        List<VclRequisition> vclRequisitions = vclRequisitionRepository.findAll();
        assertThat(vclRequisitions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDestinationLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclRequisitionRepository.findAll().size();
        // set the field null
        vclRequisition.setDestinationLocation(null);

        // Create the VclRequisition, which fails.

        restVclRequisitionMockMvc.perform(post("/api/vclRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclRequisition)))
                .andExpect(status().isBadRequest());

        List<VclRequisition> vclRequisitions = vclRequisitionRepository.findAll();
        assertThat(vclRequisitions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpectedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclRequisitionRepository.findAll().size();
        // set the field null
        vclRequisition.setExpectedDate(null);

        // Create the VclRequisition, which fails.

        restVclRequisitionMockMvc.perform(post("/api/vclRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclRequisition)))
                .andExpect(status().isBadRequest());

        List<VclRequisition> vclRequisitions = vclRequisitionRepository.findAll();
        assertThat(vclRequisitions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReturnDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclRequisitionRepository.findAll().size();
        // set the field null
        vclRequisition.setReturnDate(null);

        // Create the VclRequisition, which fails.

        restVclRequisitionMockMvc.perform(post("/api/vclRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclRequisition)))
                .andExpect(status().isBadRequest());

        List<VclRequisition> vclRequisitions = vclRequisitionRepository.findAll();
        assertThat(vclRequisitions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequisitionStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = vclRequisitionRepository.findAll().size();
        // set the field null
        vclRequisition.setRequisitionStatus(null);

        // Create the VclRequisition, which fails.

        restVclRequisitionMockMvc.perform(post("/api/vclRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclRequisition)))
                .andExpect(status().isBadRequest());

        List<VclRequisition> vclRequisitions = vclRequisitionRepository.findAll();
        assertThat(vclRequisitions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVclRequisitions() throws Exception {
        // Initialize the database
        vclRequisitionRepository.saveAndFlush(vclRequisition);

        // Get all the vclRequisitions
        restVclRequisitionMockMvc.perform(get("/api/vclRequisitions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vclRequisition.getId().intValue())))
                .andExpect(jsonPath("$.[*].requisitionType").value(hasItem(DEFAULT_REQUISITION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].vehicleType").value(hasItem(DEFAULT_VEHICLE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].sourceLocation").value(hasItem(DEFAULT_SOURCE_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].destinationLocation").value(hasItem(DEFAULT_DESTINATION_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].expectedDate").value(hasItem(DEFAULT_EXPECTED_DATE.toString())))
                .andExpect(jsonPath("$.[*].returnDate").value(hasItem(DEFAULT_RETURN_DATE.toString())))
                .andExpect(jsonPath("$.[*].requisitionCause").value(hasItem(DEFAULT_REQUISITION_CAUSE.toString())))
                .andExpect(jsonPath("$.[*].requisitionStatus").value(hasItem(DEFAULT_REQUISITION_STATUS.toString())))
                .andExpect(jsonPath("$.[*].meterReading").value(hasItem(DEFAULT_METER_READING.toString())))
                .andExpect(jsonPath("$.[*].fuelReading").value(hasItem(DEFAULT_FUEL_READING.toString())))
                .andExpect(jsonPath("$.[*].billAmount").value(hasItem(DEFAULT_BILL_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].expectedArrivalDate").value(hasItem(DEFAULT_EXPECTED_ARRIVAL_DATE.toString())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].actionDate").value(hasItem(DEFAULT_ACTION_DATE.toString())))
                .andExpect(jsonPath("$.[*].actionBy").value(hasItem(DEFAULT_ACTION_BY.intValue())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getVclRequisition() throws Exception {
        // Initialize the database
        vclRequisitionRepository.saveAndFlush(vclRequisition);

        // Get the vclRequisition
        restVclRequisitionMockMvc.perform(get("/api/vclRequisitions/{id}", vclRequisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vclRequisition.getId().intValue()))
            .andExpect(jsonPath("$.requisitionType").value(DEFAULT_REQUISITION_TYPE.toString()))
            .andExpect(jsonPath("$.vehicleType").value(DEFAULT_VEHICLE_TYPE.toString()))
            .andExpect(jsonPath("$.sourceLocation").value(DEFAULT_SOURCE_LOCATION.toString()))
            .andExpect(jsonPath("$.destinationLocation").value(DEFAULT_DESTINATION_LOCATION.toString()))
            .andExpect(jsonPath("$.expectedDate").value(DEFAULT_EXPECTED_DATE.toString()))
            .andExpect(jsonPath("$.returnDate").value(DEFAULT_RETURN_DATE.toString()))
            .andExpect(jsonPath("$.requisitionCause").value(DEFAULT_REQUISITION_CAUSE.toString()))
            .andExpect(jsonPath("$.requisitionStatus").value(DEFAULT_REQUISITION_STATUS.toString()))
            .andExpect(jsonPath("$.meterReading").value(DEFAULT_METER_READING.toString()))
            .andExpect(jsonPath("$.fuelReading").value(DEFAULT_FUEL_READING.toString()))
            .andExpect(jsonPath("$.billAmount").value(DEFAULT_BILL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.expectedArrivalDate").value(DEFAULT_EXPECTED_ARRIVAL_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.actionDate").value(DEFAULT_ACTION_DATE.toString()))
            .andExpect(jsonPath("$.actionBy").value(DEFAULT_ACTION_BY.intValue()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVclRequisition() throws Exception {
        // Get the vclRequisition
        restVclRequisitionMockMvc.perform(get("/api/vclRequisitions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVclRequisition() throws Exception {
        // Initialize the database
        vclRequisitionRepository.saveAndFlush(vclRequisition);

		int databaseSizeBeforeUpdate = vclRequisitionRepository.findAll().size();

        // Update the vclRequisition
        vclRequisition.setRequisitionType(UPDATED_REQUISITION_TYPE);
        vclRequisition.setVehicleType(UPDATED_VEHICLE_TYPE);
        vclRequisition.setSourceLocation(UPDATED_SOURCE_LOCATION);
        vclRequisition.setDestinationLocation(UPDATED_DESTINATION_LOCATION);
        vclRequisition.setExpectedDate(UPDATED_EXPECTED_DATE);
        vclRequisition.setReturnDate(UPDATED_RETURN_DATE);
        vclRequisition.setRequisitionCause(UPDATED_REQUISITION_CAUSE);
        vclRequisition.setRequisitionStatus(UPDATED_REQUISITION_STATUS);
        vclRequisition.setMeterReading(UPDATED_METER_READING);
        vclRequisition.setFuelReading(UPDATED_FUEL_READING);
        vclRequisition.setBillAmount(UPDATED_BILL_AMOUNT);
        vclRequisition.setExpectedArrivalDate(UPDATED_EXPECTED_ARRIVAL_DATE);
        vclRequisition.setComments(UPDATED_COMMENTS);
        vclRequisition.setActionDate(UPDATED_ACTION_DATE);
        vclRequisition.setActionBy(UPDATED_ACTION_BY);
        vclRequisition.setActiveStatus(UPDATED_ACTIVE_STATUS);
        vclRequisition.setCreateDate(UPDATED_CREATE_DATE);
        vclRequisition.setCreateBy(UPDATED_CREATE_BY);
        vclRequisition.setUpdateDate(UPDATED_UPDATE_DATE);
        vclRequisition.setUpdateBy(UPDATED_UPDATE_BY);

        restVclRequisitionMockMvc.perform(put("/api/vclRequisitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclRequisition)))
                .andExpect(status().isOk());

        // Validate the VclRequisition in the database
        List<VclRequisition> vclRequisitions = vclRequisitionRepository.findAll();
        assertThat(vclRequisitions).hasSize(databaseSizeBeforeUpdate);
        VclRequisition testVclRequisition = vclRequisitions.get(vclRequisitions.size() - 1);
        assertThat(testVclRequisition.getRequisitionType()).isEqualTo(UPDATED_REQUISITION_TYPE);
        assertThat(testVclRequisition.getVehicleType()).isEqualTo(UPDATED_VEHICLE_TYPE);
        assertThat(testVclRequisition.getSourceLocation()).isEqualTo(UPDATED_SOURCE_LOCATION);
        assertThat(testVclRequisition.getDestinationLocation()).isEqualTo(UPDATED_DESTINATION_LOCATION);
        assertThat(testVclRequisition.getExpectedDate()).isEqualTo(UPDATED_EXPECTED_DATE);
        assertThat(testVclRequisition.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testVclRequisition.getRequisitionCause()).isEqualTo(UPDATED_REQUISITION_CAUSE);
        assertThat(testVclRequisition.getRequisitionStatus()).isEqualTo(UPDATED_REQUISITION_STATUS);
        assertThat(testVclRequisition.getMeterReading()).isEqualTo(UPDATED_METER_READING);
        assertThat(testVclRequisition.getFuelReading()).isEqualTo(UPDATED_FUEL_READING);
        assertThat(testVclRequisition.getBillAmount()).isEqualTo(UPDATED_BILL_AMOUNT);
        assertThat(testVclRequisition.getExpectedArrivalDate()).isEqualTo(UPDATED_EXPECTED_ARRIVAL_DATE);
        assertThat(testVclRequisition.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testVclRequisition.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testVclRequisition.getActionBy()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testVclRequisition.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testVclRequisition.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVclRequisition.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testVclRequisition.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testVclRequisition.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteVclRequisition() throws Exception {
        // Initialize the database
        vclRequisitionRepository.saveAndFlush(vclRequisition);

		int databaseSizeBeforeDelete = vclRequisitionRepository.findAll().size();

        // Get the vclRequisition
        restVclRequisitionMockMvc.perform(delete("/api/vclRequisitions/{id}", vclRequisition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VclRequisition> vclRequisitions = vclRequisitionRepository.findAll();
        assertThat(vclRequisitions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
