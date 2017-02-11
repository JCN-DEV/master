package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.CareerInformation;
import gov.step.app.repository.CareerInformationRepository;
import gov.step.app.repository.search.CareerInformationSearchRepository;

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


/**
 * Test class for the CareerInformationResource REST controller.
 *
 * @see CareerInformationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CareerInformationResourceTest {

    private static final String DEFAULT_OBJECTIVES = "AAA";
    private static final String UPDATED_OBJECTIVES = "BBB";
    private static final String DEFAULT_KEYWORD = "AA";
    private static final String UPDATED_KEYWORD = "BB";

    private static final BigDecimal DEFAULT_PRESENT_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRESENT_SALARY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXPECTED_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_SALARY = new BigDecimal(2);
    private static final String DEFAULT_LOOK_FOR_NATURE = "AAA";
    private static final String UPDATED_LOOK_FOR_NATURE = "BBB";

    private static final LocalDate DEFAULT_AVAILABLE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AVAILABLE_FROM = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private CareerInformationRepository careerInformationRepository;

    @Inject
    private CareerInformationSearchRepository careerInformationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCareerInformationMockMvc;

    private CareerInformation careerInformation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CareerInformationResource careerInformationResource = new CareerInformationResource();
        ReflectionTestUtils.setField(careerInformationResource, "careerInformationRepository", careerInformationRepository);
        ReflectionTestUtils.setField(careerInformationResource, "careerInformationSearchRepository", careerInformationSearchRepository);
        this.restCareerInformationMockMvc = MockMvcBuilders.standaloneSetup(careerInformationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        careerInformation = new CareerInformation();
        careerInformation.setObjectives(DEFAULT_OBJECTIVES);
        careerInformation.setKeyword(DEFAULT_KEYWORD);
        careerInformation.setPresentSalary(DEFAULT_PRESENT_SALARY);
        careerInformation.setExpectedSalary(DEFAULT_EXPECTED_SALARY);
        careerInformation.setLookForNature(DEFAULT_LOOK_FOR_NATURE);
        careerInformation.setAvailableFrom(DEFAULT_AVAILABLE_FROM);
    }

    @Test
    @Transactional
    public void createCareerInformation() throws Exception {
        int databaseSizeBeforeCreate = careerInformationRepository.findAll().size();

        // Create the CareerInformation

        restCareerInformationMockMvc.perform(post("/api/careerInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerInformation)))
                .andExpect(status().isCreated());

        // Validate the CareerInformation in the database
        List<CareerInformation> careerInformations = careerInformationRepository.findAll();
        assertThat(careerInformations).hasSize(databaseSizeBeforeCreate + 1);
        CareerInformation testCareerInformation = careerInformations.get(careerInformations.size() - 1);
        assertThat(testCareerInformation.getObjectives()).isEqualTo(DEFAULT_OBJECTIVES);
        assertThat(testCareerInformation.getKeyword()).isEqualTo(DEFAULT_KEYWORD);
        assertThat(testCareerInformation.getPresentSalary()).isEqualTo(DEFAULT_PRESENT_SALARY);
        assertThat(testCareerInformation.getExpectedSalary()).isEqualTo(DEFAULT_EXPECTED_SALARY);
        assertThat(testCareerInformation.getLookForNature()).isEqualTo(DEFAULT_LOOK_FOR_NATURE);
        assertThat(testCareerInformation.getAvailableFrom()).isEqualTo(DEFAULT_AVAILABLE_FROM);
    }

    @Test
    @Transactional
    public void checkObjectivesIsRequired() throws Exception {
        int databaseSizeBeforeTest = careerInformationRepository.findAll().size();
        // set the field null
        careerInformation.setObjectives(null);

        // Create the CareerInformation, which fails.

        restCareerInformationMockMvc.perform(post("/api/careerInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerInformation)))
                .andExpect(status().isBadRequest());

        List<CareerInformation> careerInformations = careerInformationRepository.findAll();
        assertThat(careerInformations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeywordIsRequired() throws Exception {
        int databaseSizeBeforeTest = careerInformationRepository.findAll().size();
        // set the field null
        careerInformation.setKeyword(null);

        // Create the CareerInformation, which fails.

        restCareerInformationMockMvc.perform(post("/api/careerInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerInformation)))
                .andExpect(status().isBadRequest());

        List<CareerInformation> careerInformations = careerInformationRepository.findAll();
        assertThat(careerInformations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLookForNatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = careerInformationRepository.findAll().size();
        // set the field null
        careerInformation.setLookForNature(null);

        // Create the CareerInformation, which fails.

        restCareerInformationMockMvc.perform(post("/api/careerInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerInformation)))
                .andExpect(status().isBadRequest());

        List<CareerInformation> careerInformations = careerInformationRepository.findAll();
        assertThat(careerInformations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCareerInformations() throws Exception {
        // Initialize the database
        careerInformationRepository.saveAndFlush(careerInformation);

        // Get all the careerInformations
        restCareerInformationMockMvc.perform(get("/api/careerInformations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(careerInformation.getId().intValue())))
                .andExpect(jsonPath("$.[*].objectives").value(hasItem(DEFAULT_OBJECTIVES.toString())))
                .andExpect(jsonPath("$.[*].keyword").value(hasItem(DEFAULT_KEYWORD.toString())))
                .andExpect(jsonPath("$.[*].presentSalary").value(hasItem(DEFAULT_PRESENT_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].expectedSalary").value(hasItem(DEFAULT_EXPECTED_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].lookForNature").value(hasItem(DEFAULT_LOOK_FOR_NATURE.toString())))
                .andExpect(jsonPath("$.[*].availableFrom").value(hasItem(DEFAULT_AVAILABLE_FROM.toString())));
    }

    @Test
    @Transactional
    public void getCareerInformation() throws Exception {
        // Initialize the database
        careerInformationRepository.saveAndFlush(careerInformation);

        // Get the careerInformation
        restCareerInformationMockMvc.perform(get("/api/careerInformations/{id}", careerInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(careerInformation.getId().intValue()))
            .andExpect(jsonPath("$.objectives").value(DEFAULT_OBJECTIVES.toString()))
            .andExpect(jsonPath("$.keyword").value(DEFAULT_KEYWORD.toString()))
            .andExpect(jsonPath("$.presentSalary").value(DEFAULT_PRESENT_SALARY.intValue()))
            .andExpect(jsonPath("$.expectedSalary").value(DEFAULT_EXPECTED_SALARY.intValue()))
            .andExpect(jsonPath("$.lookForNature").value(DEFAULT_LOOK_FOR_NATURE.toString()))
            .andExpect(jsonPath("$.availableFrom").value(DEFAULT_AVAILABLE_FROM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCareerInformation() throws Exception {
        // Get the careerInformation
        restCareerInformationMockMvc.perform(get("/api/careerInformations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareerInformation() throws Exception {
        // Initialize the database
        careerInformationRepository.saveAndFlush(careerInformation);

		int databaseSizeBeforeUpdate = careerInformationRepository.findAll().size();

        // Update the careerInformation
        careerInformation.setObjectives(UPDATED_OBJECTIVES);
        careerInformation.setKeyword(UPDATED_KEYWORD);
        careerInformation.setPresentSalary(UPDATED_PRESENT_SALARY);
        careerInformation.setExpectedSalary(UPDATED_EXPECTED_SALARY);
        careerInformation.setLookForNature(UPDATED_LOOK_FOR_NATURE);
        careerInformation.setAvailableFrom(UPDATED_AVAILABLE_FROM);

        restCareerInformationMockMvc.perform(put("/api/careerInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerInformation)))
                .andExpect(status().isOk());

        // Validate the CareerInformation in the database
        List<CareerInformation> careerInformations = careerInformationRepository.findAll();
        assertThat(careerInformations).hasSize(databaseSizeBeforeUpdate);
        CareerInformation testCareerInformation = careerInformations.get(careerInformations.size() - 1);
        assertThat(testCareerInformation.getObjectives()).isEqualTo(UPDATED_OBJECTIVES);
        assertThat(testCareerInformation.getKeyword()).isEqualTo(UPDATED_KEYWORD);
        assertThat(testCareerInformation.getPresentSalary()).isEqualTo(UPDATED_PRESENT_SALARY);
        assertThat(testCareerInformation.getExpectedSalary()).isEqualTo(UPDATED_EXPECTED_SALARY);
        assertThat(testCareerInformation.getLookForNature()).isEqualTo(UPDATED_LOOK_FOR_NATURE);
        assertThat(testCareerInformation.getAvailableFrom()).isEqualTo(UPDATED_AVAILABLE_FROM);
    }

    @Test
    @Transactional
    public void deleteCareerInformation() throws Exception {
        // Initialize the database
        careerInformationRepository.saveAndFlush(careerInformation);

		int databaseSizeBeforeDelete = careerInformationRepository.findAll().size();

        // Get the careerInformation
        restCareerInformationMockMvc.perform(delete("/api/careerInformations/{id}", careerInformation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CareerInformation> careerInformations = careerInformationRepository.findAll();
        assertThat(careerInformations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
