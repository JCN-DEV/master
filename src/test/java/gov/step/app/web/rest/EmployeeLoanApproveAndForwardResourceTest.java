package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EmployeeLoanApproveAndForward;
import gov.step.app.repository.EmployeeLoanApproveAndForwardRepository;
import gov.step.app.repository.search.EmployeeLoanApproveAndForwardSearchRepository;

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
 * Test class for the EmployeeLoanApproveAndForwardResource REST controller.
 *
 * @see EmployeeLoanApproveAndForwardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeLoanApproveAndForwardResourceTest {

    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    private static final Integer DEFAULT_APPROVE_STATUS = 1;
    private static final Integer UPDATED_APPROVE_STATUS = 2;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Inject
    private EmployeeLoanApproveAndForwardRepository employeeLoanApproveAndForwardRepository;

    @Inject
    private EmployeeLoanApproveAndForwardSearchRepository employeeLoanApproveAndForwardSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeLoanApproveAndForwardMockMvc;

    private EmployeeLoanApproveAndForward employeeLoanApproveAndForward;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeLoanApproveAndForwardResource employeeLoanApproveAndForwardResource = new EmployeeLoanApproveAndForwardResource();
        ReflectionTestUtils.setField(employeeLoanApproveAndForwardResource, "employeeLoanApproveAndForwardRepository", employeeLoanApproveAndForwardRepository);
        ReflectionTestUtils.setField(employeeLoanApproveAndForwardResource, "employeeLoanApproveAndForwardSearchRepository", employeeLoanApproveAndForwardSearchRepository);
        this.restEmployeeLoanApproveAndForwardMockMvc = MockMvcBuilders.standaloneSetup(employeeLoanApproveAndForwardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employeeLoanApproveAndForward = new EmployeeLoanApproveAndForward();
        employeeLoanApproveAndForward.setComments(DEFAULT_COMMENTS);
        employeeLoanApproveAndForward.setApproveStatus(DEFAULT_APPROVE_STATUS);
        employeeLoanApproveAndForward.setCreateDate(DEFAULT_CREATE_DATE);
        employeeLoanApproveAndForward.setCreateBy(DEFAULT_CREATE_BY);
        employeeLoanApproveAndForward.setUpdateDate(DEFAULT_UPDATE_DATE);
        employeeLoanApproveAndForward.setUpdateBy(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createEmployeeLoanApproveAndForward() throws Exception {
        int databaseSizeBeforeCreate = employeeLoanApproveAndForwardRepository.findAll().size();

        // Create the EmployeeLoanApproveAndForward

        restEmployeeLoanApproveAndForwardMockMvc.perform(post("/api/employeeLoanApproveAndForwards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanApproveAndForward)))
                .andExpect(status().isCreated());

        // Validate the EmployeeLoanApproveAndForward in the database
        List<EmployeeLoanApproveAndForward> employeeLoanApproveAndForwards = employeeLoanApproveAndForwardRepository.findAll();
        assertThat(employeeLoanApproveAndForwards).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLoanApproveAndForward testEmployeeLoanApproveAndForward = employeeLoanApproveAndForwards.get(employeeLoanApproveAndForwards.size() - 1);
        assertThat(testEmployeeLoanApproveAndForward.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testEmployeeLoanApproveAndForward.getApproveStatus()).isEqualTo(DEFAULT_APPROVE_STATUS);
        assertThat(testEmployeeLoanApproveAndForward.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testEmployeeLoanApproveAndForward.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testEmployeeLoanApproveAndForward.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testEmployeeLoanApproveAndForward.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void checkCommentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanApproveAndForwardRepository.findAll().size();
        // set the field null
        employeeLoanApproveAndForward.setComments(null);

        // Create the EmployeeLoanApproveAndForward, which fails.

        restEmployeeLoanApproveAndForwardMockMvc.perform(post("/api/employeeLoanApproveAndForwards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanApproveAndForward)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanApproveAndForward> employeeLoanApproveAndForwards = employeeLoanApproveAndForwardRepository.findAll();
        assertThat(employeeLoanApproveAndForwards).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApproveStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeLoanApproveAndForwardRepository.findAll().size();
        // set the field null
        employeeLoanApproveAndForward.setApproveStatus(null);

        // Create the EmployeeLoanApproveAndForward, which fails.

        restEmployeeLoanApproveAndForwardMockMvc.perform(post("/api/employeeLoanApproveAndForwards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanApproveAndForward)))
                .andExpect(status().isBadRequest());

        List<EmployeeLoanApproveAndForward> employeeLoanApproveAndForwards = employeeLoanApproveAndForwardRepository.findAll();
        assertThat(employeeLoanApproveAndForwards).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeLoanApproveAndForwards() throws Exception {
        // Initialize the database
        employeeLoanApproveAndForwardRepository.saveAndFlush(employeeLoanApproveAndForward);

        // Get all the employeeLoanApproveAndForwards
        restEmployeeLoanApproveAndForwardMockMvc.perform(get("/api/employeeLoanApproveAndForwards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLoanApproveAndForward.getId().intValue())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].approveStatus").value(hasItem(DEFAULT_APPROVE_STATUS)))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getEmployeeLoanApproveAndForward() throws Exception {
        // Initialize the database
        employeeLoanApproveAndForwardRepository.saveAndFlush(employeeLoanApproveAndForward);

        // Get the employeeLoanApproveAndForward
        restEmployeeLoanApproveAndForwardMockMvc.perform(get("/api/employeeLoanApproveAndForwards/{id}", employeeLoanApproveAndForward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employeeLoanApproveAndForward.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.approveStatus").value(DEFAULT_APPROVE_STATUS))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeLoanApproveAndForward() throws Exception {
        // Get the employeeLoanApproveAndForward
        restEmployeeLoanApproveAndForwardMockMvc.perform(get("/api/employeeLoanApproveAndForwards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLoanApproveAndForward() throws Exception {
        // Initialize the database
        employeeLoanApproveAndForwardRepository.saveAndFlush(employeeLoanApproveAndForward);

		int databaseSizeBeforeUpdate = employeeLoanApproveAndForwardRepository.findAll().size();

        // Update the employeeLoanApproveAndForward
        employeeLoanApproveAndForward.setComments(UPDATED_COMMENTS);
        employeeLoanApproveAndForward.setApproveStatus(UPDATED_APPROVE_STATUS);
        employeeLoanApproveAndForward.setCreateDate(UPDATED_CREATE_DATE);
        employeeLoanApproveAndForward.setCreateBy(UPDATED_CREATE_BY);
        employeeLoanApproveAndForward.setUpdateDate(UPDATED_UPDATE_DATE);
        employeeLoanApproveAndForward.setUpdateBy(UPDATED_UPDATE_BY);

        restEmployeeLoanApproveAndForwardMockMvc.perform(put("/api/employeeLoanApproveAndForwards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeLoanApproveAndForward)))
                .andExpect(status().isOk());

        // Validate the EmployeeLoanApproveAndForward in the database
        List<EmployeeLoanApproveAndForward> employeeLoanApproveAndForwards = employeeLoanApproveAndForwardRepository.findAll();
        assertThat(employeeLoanApproveAndForwards).hasSize(databaseSizeBeforeUpdate);
        EmployeeLoanApproveAndForward testEmployeeLoanApproveAndForward = employeeLoanApproveAndForwards.get(employeeLoanApproveAndForwards.size() - 1);
        assertThat(testEmployeeLoanApproveAndForward.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testEmployeeLoanApproveAndForward.getApproveStatus()).isEqualTo(UPDATED_APPROVE_STATUS);
        assertThat(testEmployeeLoanApproveAndForward.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testEmployeeLoanApproveAndForward.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testEmployeeLoanApproveAndForward.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testEmployeeLoanApproveAndForward.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void deleteEmployeeLoanApproveAndForward() throws Exception {
        // Initialize the database
        employeeLoanApproveAndForwardRepository.saveAndFlush(employeeLoanApproveAndForward);

		int databaseSizeBeforeDelete = employeeLoanApproveAndForwardRepository.findAll().size();

        // Get the employeeLoanApproveAndForward
        restEmployeeLoanApproveAndForwardMockMvc.perform(delete("/api/employeeLoanApproveAndForwards/{id}", employeeLoanApproveAndForward.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeLoanApproveAndForward> employeeLoanApproveAndForwards = employeeLoanApproveAndForwardRepository.findAll();
        assertThat(employeeLoanApproveAndForwards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
