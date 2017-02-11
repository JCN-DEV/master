package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.IisCourseInfoTemp;
import gov.step.app.repository.IisCourseInfoTempRepository;
import gov.step.app.repository.search.IisCourseInfoTempSearchRepository;

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
 * Test class for the IisCourseInfoTempResource REST controller.
 *
 * @see IisCourseInfoTempResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class IisCourseInfoTempResourceIntTest {


    private static final LocalDate DEFAULT_PER_DATE_EDU = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PER_DATE_EDU = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PER_DATE_BTEB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PER_DATE_BTEB = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_MPO_ENLISTED = false;
    private static final Boolean UPDATED_MPO_ENLISTED = true;
    private static final String DEFAULT_DATE_MPO = "AAAAA";
    private static final String UPDATED_DATE_MPO = "BBBBB";

    private static final Integer DEFAULT_SEAT_NO = 1;
    private static final Integer UPDATED_SEAT_NO = 2;
    private static final String DEFAULT_SHIFT = "AAAAA";
    private static final String UPDATED_SHIFT = "BBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private IisCourseInfoTempRepository iisCourseInfoTempRepository;

    @Inject
    private IisCourseInfoTempSearchRepository iisCourseInfoTempSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIisCourseInfoTempMockMvc;

    private IisCourseInfoTemp iisCourseInfoTemp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IisCourseInfoTempResource iisCourseInfoTempResource = new IisCourseInfoTempResource();
        ReflectionTestUtils.setField(iisCourseInfoTempResource, "iisCourseInfoTempRepository", iisCourseInfoTempRepository);
        ReflectionTestUtils.setField(iisCourseInfoTempResource, "iisCourseInfoTempSearchRepository", iisCourseInfoTempSearchRepository);
        this.restIisCourseInfoTempMockMvc = MockMvcBuilders.standaloneSetup(iisCourseInfoTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        iisCourseInfoTemp = new IisCourseInfoTemp();
        iisCourseInfoTemp.setPerDateEdu(DEFAULT_PER_DATE_EDU);
        iisCourseInfoTemp.setPerDateBteb(DEFAULT_PER_DATE_BTEB);
        iisCourseInfoTemp.setMpoEnlisted(DEFAULT_MPO_ENLISTED);
        iisCourseInfoTemp.setDateMpo(DEFAULT_DATE_MPO);
        iisCourseInfoTemp.setSeatNo(DEFAULT_SEAT_NO);
        iisCourseInfoTemp.setShift(DEFAULT_SHIFT);
        iisCourseInfoTemp.setCreateDate(DEFAULT_CREATE_DATE);
        iisCourseInfoTemp.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createIisCourseInfoTemp() throws Exception {
        int databaseSizeBeforeCreate = iisCourseInfoTempRepository.findAll().size();

        // Create the IisCourseInfoTemp

        restIisCourseInfoTempMockMvc.perform(post("/api/iisCourseInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iisCourseInfoTemp)))
                .andExpect(status().isCreated());

        // Validate the IisCourseInfoTemp in the database
        List<IisCourseInfoTemp> iisCourseInfoTemps = iisCourseInfoTempRepository.findAll();
        assertThat(iisCourseInfoTemps).hasSize(databaseSizeBeforeCreate + 1);
        IisCourseInfoTemp testIisCourseInfoTemp = iisCourseInfoTemps.get(iisCourseInfoTemps.size() - 1);
        assertThat(testIisCourseInfoTemp.getPerDateEdu()).isEqualTo(DEFAULT_PER_DATE_EDU);
        assertThat(testIisCourseInfoTemp.getPerDateBteb()).isEqualTo(DEFAULT_PER_DATE_BTEB);
        assertThat(testIisCourseInfoTemp.getMpoEnlisted()).isEqualTo(DEFAULT_MPO_ENLISTED);
        assertThat(testIisCourseInfoTemp.getDateMpo()).isEqualTo(DEFAULT_DATE_MPO);
        assertThat(testIisCourseInfoTemp.getSeatNo()).isEqualTo(DEFAULT_SEAT_NO);
        assertThat(testIisCourseInfoTemp.getShift()).isEqualTo(DEFAULT_SHIFT);
        assertThat(testIisCourseInfoTemp.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testIisCourseInfoTemp.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllIisCourseInfoTemps() throws Exception {
        // Initialize the database
        iisCourseInfoTempRepository.saveAndFlush(iisCourseInfoTemp);

        // Get all the iisCourseInfoTemps
        restIisCourseInfoTempMockMvc.perform(get("/api/iisCourseInfoTemps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(iisCourseInfoTemp.getId().intValue())))
                .andExpect(jsonPath("$.[*].perDateEdu").value(hasItem(DEFAULT_PER_DATE_EDU.toString())))
                .andExpect(jsonPath("$.[*].perDateBteb").value(hasItem(DEFAULT_PER_DATE_BTEB.toString())))
                .andExpect(jsonPath("$.[*].mpoEnlisted").value(hasItem(DEFAULT_MPO_ENLISTED.booleanValue())))
                .andExpect(jsonPath("$.[*].dateMpo").value(hasItem(DEFAULT_DATE_MPO.toString())))
                .andExpect(jsonPath("$.[*].seatNo").value(hasItem(DEFAULT_SEAT_NO)))
                .andExpect(jsonPath("$.[*].shift").value(hasItem(DEFAULT_SHIFT.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getIisCourseInfoTemp() throws Exception {
        // Initialize the database
        iisCourseInfoTempRepository.saveAndFlush(iisCourseInfoTemp);

        // Get the iisCourseInfoTemp
        restIisCourseInfoTempMockMvc.perform(get("/api/iisCourseInfoTemps/{id}", iisCourseInfoTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(iisCourseInfoTemp.getId().intValue()))
            .andExpect(jsonPath("$.perDateEdu").value(DEFAULT_PER_DATE_EDU.toString()))
            .andExpect(jsonPath("$.perDateBteb").value(DEFAULT_PER_DATE_BTEB.toString()))
            .andExpect(jsonPath("$.mpoEnlisted").value(DEFAULT_MPO_ENLISTED.booleanValue()))
            .andExpect(jsonPath("$.dateMpo").value(DEFAULT_DATE_MPO.toString()))
            .andExpect(jsonPath("$.seatNo").value(DEFAULT_SEAT_NO))
            .andExpect(jsonPath("$.shift").value(DEFAULT_SHIFT.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIisCourseInfoTemp() throws Exception {
        // Get the iisCourseInfoTemp
        restIisCourseInfoTempMockMvc.perform(get("/api/iisCourseInfoTemps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIisCourseInfoTemp() throws Exception {
        // Initialize the database
        iisCourseInfoTempRepository.saveAndFlush(iisCourseInfoTemp);

		int databaseSizeBeforeUpdate = iisCourseInfoTempRepository.findAll().size();

        // Update the iisCourseInfoTemp
        iisCourseInfoTemp.setPerDateEdu(UPDATED_PER_DATE_EDU);
        iisCourseInfoTemp.setPerDateBteb(UPDATED_PER_DATE_BTEB);
        iisCourseInfoTemp.setMpoEnlisted(UPDATED_MPO_ENLISTED);
        iisCourseInfoTemp.setDateMpo(UPDATED_DATE_MPO);
        iisCourseInfoTemp.setSeatNo(UPDATED_SEAT_NO);
        iisCourseInfoTemp.setShift(UPDATED_SHIFT);
        iisCourseInfoTemp.setCreateDate(UPDATED_CREATE_DATE);
        iisCourseInfoTemp.setUpdateDate(UPDATED_UPDATE_DATE);

        restIisCourseInfoTempMockMvc.perform(put("/api/iisCourseInfoTemps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iisCourseInfoTemp)))
                .andExpect(status().isOk());

        // Validate the IisCourseInfoTemp in the database
        List<IisCourseInfoTemp> iisCourseInfoTemps = iisCourseInfoTempRepository.findAll();
        assertThat(iisCourseInfoTemps).hasSize(databaseSizeBeforeUpdate);
        IisCourseInfoTemp testIisCourseInfoTemp = iisCourseInfoTemps.get(iisCourseInfoTemps.size() - 1);
        assertThat(testIisCourseInfoTemp.getPerDateEdu()).isEqualTo(UPDATED_PER_DATE_EDU);
        assertThat(testIisCourseInfoTemp.getPerDateBteb()).isEqualTo(UPDATED_PER_DATE_BTEB);
        assertThat(testIisCourseInfoTemp.getMpoEnlisted()).isEqualTo(UPDATED_MPO_ENLISTED);
        assertThat(testIisCourseInfoTemp.getDateMpo()).isEqualTo(UPDATED_DATE_MPO);
        assertThat(testIisCourseInfoTemp.getSeatNo()).isEqualTo(UPDATED_SEAT_NO);
        assertThat(testIisCourseInfoTemp.getShift()).isEqualTo(UPDATED_SHIFT);
        assertThat(testIisCourseInfoTemp.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testIisCourseInfoTemp.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deleteIisCourseInfoTemp() throws Exception {
        // Initialize the database
        iisCourseInfoTempRepository.saveAndFlush(iisCourseInfoTemp);

		int databaseSizeBeforeDelete = iisCourseInfoTempRepository.findAll().size();

        // Get the iisCourseInfoTemp
        restIisCourseInfoTempMockMvc.perform(delete("/api/iisCourseInfoTemps/{id}", iisCourseInfoTemp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<IisCourseInfoTemp> iisCourseInfoTemps = iisCourseInfoTempRepository.findAll();
        assertThat(iisCourseInfoTemps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
