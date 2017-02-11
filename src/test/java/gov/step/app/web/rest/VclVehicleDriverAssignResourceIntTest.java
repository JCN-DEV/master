package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.VclVehicleDriverAssign;
import gov.step.app.repository.VclVehicleDriverAssignRepository;
import gov.step.app.repository.search.VclVehicleDriverAssignSearchRepository;

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
 * Test class for the VclVehicleDriverAssignResource REST controller.
 *
 * @see VclVehicleDriverAssignResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VclVehicleDriverAssignResourceIntTest {


    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private VclVehicleDriverAssignRepository vclVehicleDriverAssignRepository;

    @Inject
    private VclVehicleDriverAssignSearchRepository vclVehicleDriverAssignSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVclVehicleDriverAssignMockMvc;

    private VclVehicleDriverAssign vclVehicleDriverAssign;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VclVehicleDriverAssignResource vclVehicleDriverAssignResource = new VclVehicleDriverAssignResource();
        ReflectionTestUtils.setField(vclVehicleDriverAssignResource, "vclVehicleDriverAssignSearchRepository", vclVehicleDriverAssignSearchRepository);
        ReflectionTestUtils.setField(vclVehicleDriverAssignResource, "vclVehicleDriverAssignRepository", vclVehicleDriverAssignRepository);
        this.restVclVehicleDriverAssignMockMvc = MockMvcBuilders.standaloneSetup(vclVehicleDriverAssignResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vclVehicleDriverAssign = new VclVehicleDriverAssign();
        vclVehicleDriverAssign.setCreateDate(DEFAULT_CREATE_DATE);
        vclVehicleDriverAssign.setCreateBy(DEFAULT_CREATE_BY);
        vclVehicleDriverAssign.setUpdateDate(DEFAULT_UPDATE_DATE);
        vclVehicleDriverAssign.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createVclVehicleDriverAssign() throws Exception {
        int databaseSizeBeforeCreate = vclVehicleDriverAssignRepository.findAll().size();

        // Create the VclVehicleDriverAssign

        restVclVehicleDriverAssignMockMvc.perform(post("/api/vclVehicleDriverAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicleDriverAssign)))
                .andExpect(status().isCreated());

        // Validate the VclVehicleDriverAssign in the database
        List<VclVehicleDriverAssign> vclVehicleDriverAssigns = vclVehicleDriverAssignRepository.findAll();
        assertThat(vclVehicleDriverAssigns).hasSize(databaseSizeBeforeCreate + 1);
        VclVehicleDriverAssign testVclVehicleDriverAssign = vclVehicleDriverAssigns.get(vclVehicleDriverAssigns.size() - 1);
        assertThat(testVclVehicleDriverAssign.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVclVehicleDriverAssign.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testVclVehicleDriverAssign.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testVclVehicleDriverAssign.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllVclVehicleDriverAssigns() throws Exception {
        // Initialize the database
        vclVehicleDriverAssignRepository.saveAndFlush(vclVehicleDriverAssign);

        // Get all the vclVehicleDriverAssigns
        restVclVehicleDriverAssignMockMvc.perform(get("/api/vclVehicleDriverAssigns?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vclVehicleDriverAssign.getId().intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getVclVehicleDriverAssign() throws Exception {
        // Initialize the database
        vclVehicleDriverAssignRepository.saveAndFlush(vclVehicleDriverAssign);

        // Get the vclVehicleDriverAssign
        restVclVehicleDriverAssignMockMvc.perform(get("/api/vclVehicleDriverAssigns/{id}", vclVehicleDriverAssign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vclVehicleDriverAssign.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVclVehicleDriverAssign() throws Exception {
        // Get the vclVehicleDriverAssign
        restVclVehicleDriverAssignMockMvc.perform(get("/api/vclVehicleDriverAssigns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVclVehicleDriverAssign() throws Exception {
        // Initialize the database
        vclVehicleDriverAssignRepository.saveAndFlush(vclVehicleDriverAssign);

		int databaseSizeBeforeUpdate = vclVehicleDriverAssignRepository.findAll().size();

        // Update the vclVehicleDriverAssign
        vclVehicleDriverAssign.setCreateDate(UPDATED_CREATE_DATE);
        vclVehicleDriverAssign.setCreateBy(UPDATED_CREATE_BY);
        vclVehicleDriverAssign.setUpdateDate(UPDATED_UPDATE_DATE);
        vclVehicleDriverAssign.setUpdateBy(UPDATED_UPDATE_BY);

        restVclVehicleDriverAssignMockMvc.perform(put("/api/vclVehicleDriverAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclVehicleDriverAssign)))
                .andExpect(status().isOk());

        // Validate the VclVehicleDriverAssign in the database
        List<VclVehicleDriverAssign> vclVehicleDriverAssigns = vclVehicleDriverAssignRepository.findAll();
        assertThat(vclVehicleDriverAssigns).hasSize(databaseSizeBeforeUpdate);
        VclVehicleDriverAssign testVclVehicleDriverAssign = vclVehicleDriverAssigns.get(vclVehicleDriverAssigns.size() - 1);
        assertThat(testVclVehicleDriverAssign.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVclVehicleDriverAssign.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testVclVehicleDriverAssign.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testVclVehicleDriverAssign.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteVclVehicleDriverAssign() throws Exception {
        // Initialize the database
        vclVehicleDriverAssignRepository.saveAndFlush(vclVehicleDriverAssign);

		int databaseSizeBeforeDelete = vclVehicleDriverAssignRepository.findAll().size();

        // Get the vclVehicleDriverAssign
        restVclVehicleDriverAssignMockMvc.perform(delete("/api/vclVehicleDriverAssigns/{id}", vclVehicleDriverAssign.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VclVehicleDriverAssign> vclVehicleDriverAssigns = vclVehicleDriverAssignRepository.findAll();
        assertThat(vclVehicleDriverAssigns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
