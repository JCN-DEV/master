package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.VclEmployeeAssign;
import gov.step.app.repository.VclEmployeeAssignRepository;
import gov.step.app.repository.search.VclEmployeeAssignSearchRepository;

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
 * Test class for the VclEmployeeAssignResource REST controller.
 *
 * @see VclEmployeeAssignResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VclEmployeeAssignResourceIntTest {


    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private VclEmployeeAssignRepository vclEmployeeAssignRepository;

    @Inject
    private VclEmployeeAssignSearchRepository vclEmployeeAssignSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVclEmployeeAssignMockMvc;

    private VclEmployeeAssign vclEmployeeAssign;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VclEmployeeAssignResource vclEmployeeAssignResource = new VclEmployeeAssignResource();
        ReflectionTestUtils.setField(vclEmployeeAssignResource, "vclEmployeeAssignSearchRepository", vclEmployeeAssignSearchRepository);
        ReflectionTestUtils.setField(vclEmployeeAssignResource, "vclEmployeeAssignRepository", vclEmployeeAssignRepository);
        this.restVclEmployeeAssignMockMvc = MockMvcBuilders.standaloneSetup(vclEmployeeAssignResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vclEmployeeAssign = new VclEmployeeAssign();
        vclEmployeeAssign.setCreateDate(DEFAULT_CREATE_DATE);
        vclEmployeeAssign.setCreateBy(DEFAULT_CREATE_BY);
        vclEmployeeAssign.setUpdateDate(DEFAULT_UPDATE_DATE);
        vclEmployeeAssign.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createVclEmployeeAssign() throws Exception {
        int databaseSizeBeforeCreate = vclEmployeeAssignRepository.findAll().size();

        // Create the VclEmployeeAssign

        restVclEmployeeAssignMockMvc.perform(post("/api/vclEmployeeAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclEmployeeAssign)))
                .andExpect(status().isCreated());

        // Validate the VclEmployeeAssign in the database
        List<VclEmployeeAssign> vclEmployeeAssigns = vclEmployeeAssignRepository.findAll();
        assertThat(vclEmployeeAssigns).hasSize(databaseSizeBeforeCreate + 1);
        VclEmployeeAssign testVclEmployeeAssign = vclEmployeeAssigns.get(vclEmployeeAssigns.size() - 1);
        assertThat(testVclEmployeeAssign.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVclEmployeeAssign.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testVclEmployeeAssign.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testVclEmployeeAssign.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllVclEmployeeAssigns() throws Exception {
        // Initialize the database
        vclEmployeeAssignRepository.saveAndFlush(vclEmployeeAssign);

        // Get all the vclEmployeeAssigns
        restVclEmployeeAssignMockMvc.perform(get("/api/vclEmployeeAssigns?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vclEmployeeAssign.getId().intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getVclEmployeeAssign() throws Exception {
        // Initialize the database
        vclEmployeeAssignRepository.saveAndFlush(vclEmployeeAssign);

        // Get the vclEmployeeAssign
        restVclEmployeeAssignMockMvc.perform(get("/api/vclEmployeeAssigns/{id}", vclEmployeeAssign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vclEmployeeAssign.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVclEmployeeAssign() throws Exception {
        // Get the vclEmployeeAssign
        restVclEmployeeAssignMockMvc.perform(get("/api/vclEmployeeAssigns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVclEmployeeAssign() throws Exception {
        // Initialize the database
        vclEmployeeAssignRepository.saveAndFlush(vclEmployeeAssign);

		int databaseSizeBeforeUpdate = vclEmployeeAssignRepository.findAll().size();

        // Update the vclEmployeeAssign
        vclEmployeeAssign.setCreateDate(UPDATED_CREATE_DATE);
        vclEmployeeAssign.setCreateBy(UPDATED_CREATE_BY);
        vclEmployeeAssign.setUpdateDate(UPDATED_UPDATE_DATE);
        vclEmployeeAssign.setUpdateBy(UPDATED_UPDATE_BY);

        restVclEmployeeAssignMockMvc.perform(put("/api/vclEmployeeAssigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vclEmployeeAssign)))
                .andExpect(status().isOk());

        // Validate the VclEmployeeAssign in the database
        List<VclEmployeeAssign> vclEmployeeAssigns = vclEmployeeAssignRepository.findAll();
        assertThat(vclEmployeeAssigns).hasSize(databaseSizeBeforeUpdate);
        VclEmployeeAssign testVclEmployeeAssign = vclEmployeeAssigns.get(vclEmployeeAssigns.size() - 1);
        assertThat(testVclEmployeeAssign.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVclEmployeeAssign.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testVclEmployeeAssign.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testVclEmployeeAssign.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteVclEmployeeAssign() throws Exception {
        // Initialize the database
        vclEmployeeAssignRepository.saveAndFlush(vclEmployeeAssign);

		int databaseSizeBeforeDelete = vclEmployeeAssignRepository.findAll().size();

        // Get the vclEmployeeAssign
        restVclEmployeeAssignMockMvc.perform(delete("/api/vclEmployeeAssigns/{id}", vclEmployeeAssign.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VclEmployeeAssign> vclEmployeeAssigns = vclEmployeeAssignRepository.findAll();
        assertThat(vclEmployeeAssigns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
