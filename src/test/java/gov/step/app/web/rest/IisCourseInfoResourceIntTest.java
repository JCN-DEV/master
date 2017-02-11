package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.IisCourseInfo;
import gov.step.app.repository.IisCourseInfoRepository;
import gov.step.app.repository.search.IisCourseInfoSearchRepository;

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
 * Test class for the IisCourseInfoResource REST controller.
 *
 * @see IisCourseInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class IisCourseInfoResourceIntTest {


    private static final LocalDate DEFAULT_PER_DATE_EDU = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PER_DATE_EDU = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PER_DATE_BTEB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PER_DATE_BTEB = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_MPO_ENLISTED = false;
    private static final Boolean UPDATED_MPO_ENLISTED = true;
    private static final LocalDate DEFAULT_DATE_MPO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MPO = LocalDate.ofEpochDay(0L);

    private static final Integer DEFAULT_SEAT_NO = 1;
    private static final Integer UPDATED_SEAT_NO = 2;
    private static final String DEFAULT_SHIFT = "A";
    private static final String UPDATED_SHIFT = "B";

    @Inject
    private IisCourseInfoRepository iisCourseInfoRepository;

    @Inject
    private IisCourseInfoSearchRepository iisCourseInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIisCourseInfoMockMvc;

    private IisCourseInfo iisCourseInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IisCourseInfoResource iisCourseInfoResource = new IisCourseInfoResource();
        ReflectionTestUtils.setField(iisCourseInfoResource, "iisCourseInfoRepository", iisCourseInfoRepository);
        ReflectionTestUtils.setField(iisCourseInfoResource, "iisCourseInfoSearchRepository", iisCourseInfoSearchRepository);
        this.restIisCourseInfoMockMvc = MockMvcBuilders.standaloneSetup(iisCourseInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        iisCourseInfo = new IisCourseInfo();
        iisCourseInfo.setPerDateEdu(DEFAULT_PER_DATE_EDU);
        iisCourseInfo.setPerDateBteb(DEFAULT_PER_DATE_BTEB);
        iisCourseInfo.setMpoEnlisted(DEFAULT_MPO_ENLISTED);
        iisCourseInfo.setDateMpo(DEFAULT_DATE_MPO);
        iisCourseInfo.setSeatNo(DEFAULT_SEAT_NO);
        iisCourseInfo.setShift(DEFAULT_SHIFT);
    }

    @Test
    @Transactional
    public void createIisCourseInfo() throws Exception {
        int databaseSizeBeforeCreate = iisCourseInfoRepository.findAll().size();

        // Create the IisCourseInfo

        restIisCourseInfoMockMvc.perform(post("/api/iisCourseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iisCourseInfo)))
                .andExpect(status().isCreated());

        // Validate the IisCourseInfo in the database
        List<IisCourseInfo> iisCourseInfos = iisCourseInfoRepository.findAll();
        assertThat(iisCourseInfos).hasSize(databaseSizeBeforeCreate + 1);
        IisCourseInfo testIisCourseInfo = iisCourseInfos.get(iisCourseInfos.size() - 1);
        assertThat(testIisCourseInfo.getPerDateEdu()).isEqualTo(DEFAULT_PER_DATE_EDU);
        assertThat(testIisCourseInfo.getPerDateBteb()).isEqualTo(DEFAULT_PER_DATE_BTEB);
        assertThat(testIisCourseInfo.getMpoEnlisted()).isEqualTo(DEFAULT_MPO_ENLISTED);
        assertThat(testIisCourseInfo.getDateMpo()).isEqualTo(DEFAULT_DATE_MPO);
        assertThat(testIisCourseInfo.getSeatNo()).isEqualTo(DEFAULT_SEAT_NO);
        assertThat(testIisCourseInfo.getShift()).isEqualTo(DEFAULT_SHIFT);
    }

    @Test
    @Transactional
    public void getAllIisCourseInfos() throws Exception {
        // Initialize the database
        iisCourseInfoRepository.saveAndFlush(iisCourseInfo);

        // Get all the iisCourseInfos
        restIisCourseInfoMockMvc.perform(get("/api/iisCourseInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(iisCourseInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].perDateEdu").value(hasItem(DEFAULT_PER_DATE_EDU.toString())))
                .andExpect(jsonPath("$.[*].perDateBteb").value(hasItem(DEFAULT_PER_DATE_BTEB.toString())))
                .andExpect(jsonPath("$.[*].mpoEnlisted").value(hasItem(DEFAULT_MPO_ENLISTED.booleanValue())))
                .andExpect(jsonPath("$.[*].dateMpo").value(hasItem(DEFAULT_DATE_MPO.toString())))
                .andExpect(jsonPath("$.[*].seatNo").value(hasItem(DEFAULT_SEAT_NO)))
                .andExpect(jsonPath("$.[*].shift").value(hasItem(DEFAULT_SHIFT.toString())));
    }

    @Test
    @Transactional
    public void getIisCourseInfo() throws Exception {
        // Initialize the database
        iisCourseInfoRepository.saveAndFlush(iisCourseInfo);

        // Get the iisCourseInfo
        restIisCourseInfoMockMvc.perform(get("/api/iisCourseInfos/{id}", iisCourseInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(iisCourseInfo.getId().intValue()))
            .andExpect(jsonPath("$.perDateEdu").value(DEFAULT_PER_DATE_EDU.toString()))
            .andExpect(jsonPath("$.perDateBteb").value(DEFAULT_PER_DATE_BTEB.toString()))
            .andExpect(jsonPath("$.mpoEnlisted").value(DEFAULT_MPO_ENLISTED.booleanValue()))
            .andExpect(jsonPath("$.dateMpo").value(DEFAULT_DATE_MPO.toString()))
            .andExpect(jsonPath("$.seatNo").value(DEFAULT_SEAT_NO))
            .andExpect(jsonPath("$.shift").value(DEFAULT_SHIFT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIisCourseInfo() throws Exception {
        // Get the iisCourseInfo
        restIisCourseInfoMockMvc.perform(get("/api/iisCourseInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIisCourseInfo() throws Exception {
        // Initialize the database
        iisCourseInfoRepository.saveAndFlush(iisCourseInfo);

		int databaseSizeBeforeUpdate = iisCourseInfoRepository.findAll().size();

        // Update the iisCourseInfo
        iisCourseInfo.setPerDateEdu(UPDATED_PER_DATE_EDU);
        iisCourseInfo.setPerDateBteb(UPDATED_PER_DATE_BTEB);
        iisCourseInfo.setMpoEnlisted(UPDATED_MPO_ENLISTED);
        iisCourseInfo.setDateMpo(UPDATED_DATE_MPO);
        iisCourseInfo.setSeatNo(UPDATED_SEAT_NO);
        iisCourseInfo.setShift(UPDATED_SHIFT);

        restIisCourseInfoMockMvc.perform(put("/api/iisCourseInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iisCourseInfo)))
                .andExpect(status().isOk());

        // Validate the IisCourseInfo in the database
        List<IisCourseInfo> iisCourseInfos = iisCourseInfoRepository.findAll();
        assertThat(iisCourseInfos).hasSize(databaseSizeBeforeUpdate);
        IisCourseInfo testIisCourseInfo = iisCourseInfos.get(iisCourseInfos.size() - 1);
        assertThat(testIisCourseInfo.getPerDateEdu()).isEqualTo(UPDATED_PER_DATE_EDU);
        assertThat(testIisCourseInfo.getPerDateBteb()).isEqualTo(UPDATED_PER_DATE_BTEB);
        assertThat(testIisCourseInfo.getMpoEnlisted()).isEqualTo(UPDATED_MPO_ENLISTED);
        assertThat(testIisCourseInfo.getDateMpo()).isEqualTo(UPDATED_DATE_MPO);
        assertThat(testIisCourseInfo.getSeatNo()).isEqualTo(UPDATED_SEAT_NO);
        assertThat(testIisCourseInfo.getShift()).isEqualTo(UPDATED_SHIFT);
    }

    @Test
    @Transactional
    public void deleteIisCourseInfo() throws Exception {
        // Initialize the database
        iisCourseInfoRepository.saveAndFlush(iisCourseInfo);

		int databaseSizeBeforeDelete = iisCourseInfoRepository.findAll().size();

        // Get the iisCourseInfo
        restIisCourseInfoMockMvc.perform(delete("/api/iisCourseInfos/{id}", iisCourseInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<IisCourseInfo> iisCourseInfos = iisCourseInfoRepository.findAll();
        assertThat(iisCourseInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
