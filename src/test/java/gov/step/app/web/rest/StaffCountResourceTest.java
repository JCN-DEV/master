package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.StaffCount;
import gov.step.app.repository.StaffCountRepository;
import gov.step.app.repository.search.StaffCountSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.step.app.domain.enumeration.EmployeeType;

/**
 * Test class for the StaffCountResource REST controller.
 *
 * @see StaffCountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StaffCountResourceTest {



private static final EmployeeType DEFAULT_TYPE = EmployeeType.Approved;
    private static final EmployeeType UPDATED_TYPE = EmployeeType.CurrentlyWorking;

    private static final Integer DEFAULT_NUMBER_OF_PRINCIPAL = 1;
    private static final Integer UPDATED_NUMBER_OF_PRINCIPAL = 2;

    private static final Integer DEFAULT_NUMBER_OF_MALE_TEACHER = 1;
    private static final Integer UPDATED_NUMBER_OF_MALE_TEACHER = 2;

    private static final Integer DEFAULT_NUMBER_OF_FEMALE_TEACHER = 1;
    private static final Integer UPDATED_NUMBER_OF_FEMALE_TEACHER = 2;

    private static final Integer DEFAULT_NUMBER_OF_DEMONSTRATOR = 1;
    private static final Integer UPDATED_NUMBER_OF_DEMONSTRATOR = 2;

    private static final Integer DEFAULT_NUMBER_OF_ASSISTANT_LIBRARIAN = 1;
    private static final Integer UPDATED_NUMBER_OF_ASSISTANT_LIBRARIAN = 2;

    private static final Integer DEFAULT_NUMBER_OF_LAB_ASSISTANT = 1;
    private static final Integer UPDATED_NUMBER_OF_LAB_ASSISTANT = 2;

    private static final Integer DEFAULT_NUMBER_OF_SCIENCE_LAB_ASSISTANT = 1;
    private static final Integer UPDATED_NUMBER_OF_SCIENCE_LAB_ASSISTANT = 2;

    private static final Integer DEFAULT_THIRD_CLASS = 1;
    private static final Integer UPDATED_THIRD_CLASS = 2;

    private static final Integer DEFAULT_FOURTH_CLASS = 1;
    private static final Integer UPDATED_FOURTH_CLASS = 2;

    private static final Integer DEFAULT_NUMBER_OF_FEMALE_AVAILABLE_BY_QUOTA = 1;
    private static final Integer UPDATED_NUMBER_OF_FEMALE_AVAILABLE_BY_QUOTA = 2;

    @Inject
    private StaffCountRepository staffCountRepository;

    @Inject
    private StaffCountSearchRepository staffCountSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStaffCountMockMvc;

    private StaffCount staffCount;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StaffCountResource staffCountResource = new StaffCountResource();
        ReflectionTestUtils.setField(staffCountResource, "staffCountRepository", staffCountRepository);
        ReflectionTestUtils.setField(staffCountResource, "staffCountSearchRepository", staffCountSearchRepository);
        this.restStaffCountMockMvc = MockMvcBuilders.standaloneSetup(staffCountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        staffCount = new StaffCount();
        staffCount.setType(DEFAULT_TYPE);
        staffCount.setNumberOfPrincipal(DEFAULT_NUMBER_OF_PRINCIPAL);
        staffCount.setNumberOfMaleTeacher(DEFAULT_NUMBER_OF_MALE_TEACHER);
        staffCount.setNumberOfFemaleTeacher(DEFAULT_NUMBER_OF_FEMALE_TEACHER);
        staffCount.setNumberOfDemonstrator(DEFAULT_NUMBER_OF_DEMONSTRATOR);
        staffCount.setNumberOfAssistantLibrarian(DEFAULT_NUMBER_OF_ASSISTANT_LIBRARIAN);
        staffCount.setNumberOfLabAssistant(DEFAULT_NUMBER_OF_LAB_ASSISTANT);
        staffCount.setNumberOfScienceLabAssistant(DEFAULT_NUMBER_OF_SCIENCE_LAB_ASSISTANT);
        staffCount.setThirdClass(DEFAULT_THIRD_CLASS);
        staffCount.setFourthClass(DEFAULT_FOURTH_CLASS);
        staffCount.setNumberOfFemaleAvailableByQuota(DEFAULT_NUMBER_OF_FEMALE_AVAILABLE_BY_QUOTA);
    }

    @Test
    @Transactional
    public void createStaffCount() throws Exception {
        int databaseSizeBeforeCreate = staffCountRepository.findAll().size();

        // Create the StaffCount

        restStaffCountMockMvc.perform(post("/api/staffCounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(staffCount)))
                .andExpect(status().isCreated());

        // Validate the StaffCount in the database
        List<StaffCount> staffCounts = staffCountRepository.findAll();
        assertThat(staffCounts).hasSize(databaseSizeBeforeCreate + 1);
        StaffCount testStaffCount = staffCounts.get(staffCounts.size() - 1);
        assertThat(testStaffCount.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testStaffCount.getNumberOfPrincipal()).isEqualTo(DEFAULT_NUMBER_OF_PRINCIPAL);
        assertThat(testStaffCount.getNumberOfMaleTeacher()).isEqualTo(DEFAULT_NUMBER_OF_MALE_TEACHER);
        assertThat(testStaffCount.getNumberOfFemaleTeacher()).isEqualTo(DEFAULT_NUMBER_OF_FEMALE_TEACHER);
        assertThat(testStaffCount.getNumberOfDemonstrator()).isEqualTo(DEFAULT_NUMBER_OF_DEMONSTRATOR);
        assertThat(testStaffCount.getNumberOfAssistantLibrarian()).isEqualTo(DEFAULT_NUMBER_OF_ASSISTANT_LIBRARIAN);
        assertThat(testStaffCount.getNumberOfLabAssistant()).isEqualTo(DEFAULT_NUMBER_OF_LAB_ASSISTANT);
        assertThat(testStaffCount.getNumberOfScienceLabAssistant()).isEqualTo(DEFAULT_NUMBER_OF_SCIENCE_LAB_ASSISTANT);
        assertThat(testStaffCount.getThirdClass()).isEqualTo(DEFAULT_THIRD_CLASS);
        assertThat(testStaffCount.getFourthClass()).isEqualTo(DEFAULT_FOURTH_CLASS);
        assertThat(testStaffCount.getNumberOfFemaleAvailableByQuota()).isEqualTo(DEFAULT_NUMBER_OF_FEMALE_AVAILABLE_BY_QUOTA);
    }

    @Test
    @Transactional
    public void getAllStaffCounts() throws Exception {
        // Initialize the database
        staffCountRepository.saveAndFlush(staffCount);

        // Get all the staffCounts
        restStaffCountMockMvc.perform(get("/api/staffCounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(staffCount.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].numberOfPrincipal").value(hasItem(DEFAULT_NUMBER_OF_PRINCIPAL)))
                .andExpect(jsonPath("$.[*].numberOfMaleTeacher").value(hasItem(DEFAULT_NUMBER_OF_MALE_TEACHER)))
                .andExpect(jsonPath("$.[*].numberOfFemaleTeacher").value(hasItem(DEFAULT_NUMBER_OF_FEMALE_TEACHER)))
                .andExpect(jsonPath("$.[*].numberOfDemonstrator").value(hasItem(DEFAULT_NUMBER_OF_DEMONSTRATOR)))
                .andExpect(jsonPath("$.[*].numberOfAssistantLibrarian").value(hasItem(DEFAULT_NUMBER_OF_ASSISTANT_LIBRARIAN)))
                .andExpect(jsonPath("$.[*].numberOfLabAssistant").value(hasItem(DEFAULT_NUMBER_OF_LAB_ASSISTANT)))
                .andExpect(jsonPath("$.[*].numberOfScienceLabAssistant").value(hasItem(DEFAULT_NUMBER_OF_SCIENCE_LAB_ASSISTANT)))
                .andExpect(jsonPath("$.[*].thirdClass").value(hasItem(DEFAULT_THIRD_CLASS)))
                .andExpect(jsonPath("$.[*].fourthClass").value(hasItem(DEFAULT_FOURTH_CLASS)))
                .andExpect(jsonPath("$.[*].numberOfFemaleAvailableByQuota").value(hasItem(DEFAULT_NUMBER_OF_FEMALE_AVAILABLE_BY_QUOTA)));
    }

    @Test
    @Transactional
    public void getStaffCount() throws Exception {
        // Initialize the database
        staffCountRepository.saveAndFlush(staffCount);

        // Get the staffCount
        restStaffCountMockMvc.perform(get("/api/staffCounts/{id}", staffCount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(staffCount.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.numberOfPrincipal").value(DEFAULT_NUMBER_OF_PRINCIPAL))
            .andExpect(jsonPath("$.numberOfMaleTeacher").value(DEFAULT_NUMBER_OF_MALE_TEACHER))
            .andExpect(jsonPath("$.numberOfFemaleTeacher").value(DEFAULT_NUMBER_OF_FEMALE_TEACHER))
            .andExpect(jsonPath("$.numberOfDemonstrator").value(DEFAULT_NUMBER_OF_DEMONSTRATOR))
            .andExpect(jsonPath("$.numberOfAssistantLibrarian").value(DEFAULT_NUMBER_OF_ASSISTANT_LIBRARIAN))
            .andExpect(jsonPath("$.numberOfLabAssistant").value(DEFAULT_NUMBER_OF_LAB_ASSISTANT))
            .andExpect(jsonPath("$.numberOfScienceLabAssistant").value(DEFAULT_NUMBER_OF_SCIENCE_LAB_ASSISTANT))
            .andExpect(jsonPath("$.thirdClass").value(DEFAULT_THIRD_CLASS))
            .andExpect(jsonPath("$.fourthClass").value(DEFAULT_FOURTH_CLASS))
            .andExpect(jsonPath("$.numberOfFemaleAvailableByQuota").value(DEFAULT_NUMBER_OF_FEMALE_AVAILABLE_BY_QUOTA));
    }

    @Test
    @Transactional
    public void getNonExistingStaffCount() throws Exception {
        // Get the staffCount
        restStaffCountMockMvc.perform(get("/api/staffCounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaffCount() throws Exception {
        // Initialize the database
        staffCountRepository.saveAndFlush(staffCount);

		int databaseSizeBeforeUpdate = staffCountRepository.findAll().size();

        // Update the staffCount
        staffCount.setType(UPDATED_TYPE);
        staffCount.setNumberOfPrincipal(UPDATED_NUMBER_OF_PRINCIPAL);
        staffCount.setNumberOfMaleTeacher(UPDATED_NUMBER_OF_MALE_TEACHER);
        staffCount.setNumberOfFemaleTeacher(UPDATED_NUMBER_OF_FEMALE_TEACHER);
        staffCount.setNumberOfDemonstrator(UPDATED_NUMBER_OF_DEMONSTRATOR);
        staffCount.setNumberOfAssistantLibrarian(UPDATED_NUMBER_OF_ASSISTANT_LIBRARIAN);
        staffCount.setNumberOfLabAssistant(UPDATED_NUMBER_OF_LAB_ASSISTANT);
        staffCount.setNumberOfScienceLabAssistant(UPDATED_NUMBER_OF_SCIENCE_LAB_ASSISTANT);
        staffCount.setThirdClass(UPDATED_THIRD_CLASS);
        staffCount.setFourthClass(UPDATED_FOURTH_CLASS);
        staffCount.setNumberOfFemaleAvailableByQuota(UPDATED_NUMBER_OF_FEMALE_AVAILABLE_BY_QUOTA);

        restStaffCountMockMvc.perform(put("/api/staffCounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(staffCount)))
                .andExpect(status().isOk());

        // Validate the StaffCount in the database
        List<StaffCount> staffCounts = staffCountRepository.findAll();
        assertThat(staffCounts).hasSize(databaseSizeBeforeUpdate);
        StaffCount testStaffCount = staffCounts.get(staffCounts.size() - 1);
        assertThat(testStaffCount.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testStaffCount.getNumberOfPrincipal()).isEqualTo(UPDATED_NUMBER_OF_PRINCIPAL);
        assertThat(testStaffCount.getNumberOfMaleTeacher()).isEqualTo(UPDATED_NUMBER_OF_MALE_TEACHER);
        assertThat(testStaffCount.getNumberOfFemaleTeacher()).isEqualTo(UPDATED_NUMBER_OF_FEMALE_TEACHER);
        assertThat(testStaffCount.getNumberOfDemonstrator()).isEqualTo(UPDATED_NUMBER_OF_DEMONSTRATOR);
        assertThat(testStaffCount.getNumberOfAssistantLibrarian()).isEqualTo(UPDATED_NUMBER_OF_ASSISTANT_LIBRARIAN);
        assertThat(testStaffCount.getNumberOfLabAssistant()).isEqualTo(UPDATED_NUMBER_OF_LAB_ASSISTANT);
        assertThat(testStaffCount.getNumberOfScienceLabAssistant()).isEqualTo(UPDATED_NUMBER_OF_SCIENCE_LAB_ASSISTANT);
        assertThat(testStaffCount.getThirdClass()).isEqualTo(UPDATED_THIRD_CLASS);
        assertThat(testStaffCount.getFourthClass()).isEqualTo(UPDATED_FOURTH_CLASS);
        assertThat(testStaffCount.getNumberOfFemaleAvailableByQuota()).isEqualTo(UPDATED_NUMBER_OF_FEMALE_AVAILABLE_BY_QUOTA);
    }

    @Test
    @Transactional
    public void deleteStaffCount() throws Exception {
        // Initialize the database
        staffCountRepository.saveAndFlush(staffCount);

		int databaseSizeBeforeDelete = staffCountRepository.findAll().size();

        // Get the staffCount
        restStaffCountMockMvc.perform(delete("/api/staffCounts/{id}", staffCount.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StaffCount> staffCounts = staffCountRepository.findAll();
        assertThat(staffCounts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
