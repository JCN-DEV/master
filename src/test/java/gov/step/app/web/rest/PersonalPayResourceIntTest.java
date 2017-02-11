package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PersonalPay;
import gov.step.app.repository.PersonalPayRepository;
import gov.step.app.repository.search.PersonalPaySearchRepository;

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
 * Test class for the PersonalPayResource REST controller.
 *
 * @see PersonalPayResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PersonalPayResourceIntTest {


    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private PersonalPayRepository personalPayRepository;

    @Inject
    private PersonalPaySearchRepository personalPaySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPersonalPayMockMvc;

    private PersonalPay personalPay;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonalPayResource personalPayResource = new PersonalPayResource();
        ReflectionTestUtils.setField(personalPayResource, "personalPayRepository", personalPayRepository);
        ReflectionTestUtils.setField(personalPayResource, "personalPaySearchRepository", personalPaySearchRepository);
        this.restPersonalPayMockMvc = MockMvcBuilders.standaloneSetup(personalPayResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        personalPay = new PersonalPay();
        personalPay.setEffectiveDate(DEFAULT_EFFECTIVE_DATE);
        personalPay.setDateCreated(DEFAULT_DATE_CREATED);
        personalPay.setDateModified(DEFAULT_DATE_MODIFIED);
        personalPay.setAmount(DEFAULT_AMOUNT);
        personalPay.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPersonalPay() throws Exception {
        int databaseSizeBeforeCreate = personalPayRepository.findAll().size();

        // Create the PersonalPay

        restPersonalPayMockMvc.perform(post("/api/personalPays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personalPay)))
                .andExpect(status().isCreated());

        // Validate the PersonalPay in the database
        List<PersonalPay> personalPays = personalPayRepository.findAll();
        assertThat(personalPays).hasSize(databaseSizeBeforeCreate + 1);
        PersonalPay testPersonalPay = personalPays.get(personalPays.size() - 1);
        assertThat(testPersonalPay.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testPersonalPay.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testPersonalPay.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testPersonalPay.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPersonalPay.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllPersonalPays() throws Exception {
        // Initialize the database
        personalPayRepository.saveAndFlush(personalPay);

        // Get all the personalPays
        restPersonalPayMockMvc.perform(get("/api/personalPays"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(personalPay.getId().intValue())))
                .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getPersonalPay() throws Exception {
        // Initialize the database
        personalPayRepository.saveAndFlush(personalPay);

        // Get the personalPay
        restPersonalPayMockMvc.perform(get("/api/personalPays/{id}", personalPay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(personalPay.getId().intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonalPay() throws Exception {
        // Get the personalPay
        restPersonalPayMockMvc.perform(get("/api/personalPays/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonalPay() throws Exception {
        // Initialize the database
        personalPayRepository.saveAndFlush(personalPay);

		int databaseSizeBeforeUpdate = personalPayRepository.findAll().size();

        // Update the personalPay
        personalPay.setEffectiveDate(UPDATED_EFFECTIVE_DATE);
        personalPay.setDateCreated(UPDATED_DATE_CREATED);
        personalPay.setDateModified(UPDATED_DATE_MODIFIED);
        personalPay.setAmount(UPDATED_AMOUNT);
        personalPay.setStatus(UPDATED_STATUS);

        restPersonalPayMockMvc.perform(put("/api/personalPays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personalPay)))
                .andExpect(status().isOk());

        // Validate the PersonalPay in the database
        List<PersonalPay> personalPays = personalPayRepository.findAll();
        assertThat(personalPays).hasSize(databaseSizeBeforeUpdate);
        PersonalPay testPersonalPay = personalPays.get(personalPays.size() - 1);
        assertThat(testPersonalPay.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testPersonalPay.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPersonalPay.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testPersonalPay.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPersonalPay.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deletePersonalPay() throws Exception {
        // Initialize the database
        personalPayRepository.saveAndFlush(personalPay);

		int databaseSizeBeforeDelete = personalPayRepository.findAll().size();

        // Get the personalPay
        restPersonalPayMockMvc.perform(delete("/api/personalPays/{id}", personalPay.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonalPay> personalPays = personalPayRepository.findAll();
        assertThat(personalPays).hasSize(databaseSizeBeforeDelete - 1);
    }
}
