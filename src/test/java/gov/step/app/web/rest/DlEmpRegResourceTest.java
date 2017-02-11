package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.DlEmpReg;
import gov.step.app.repository.DlEmpRegRepository;
import gov.step.app.repository.search.DlEmpRegSearchRepository;

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
 * Test class for the DlEmpRegResource REST controller.
 *
 * @see DlEmpRegResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DlEmpRegResourceTest {

    private static final String DEFAULT_USER_NAME = "AAAAA";
    private static final String UPDATED_USER_NAME = "BBBBB";
    private static final String DEFAULT_USER_PW = "AAAAA";
    private static final String UPDATED_USER_PW = "BBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private DlEmpRegRepository dlEmpRegRepository;

    @Inject
    private DlEmpRegSearchRepository dlEmpRegSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDlEmpRegMockMvc;

    private DlEmpReg dlEmpReg;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DlEmpRegResource dlEmpRegResource = new DlEmpRegResource();
        ReflectionTestUtils.setField(dlEmpRegResource, "dlEmpRegRepository", dlEmpRegRepository);
        ReflectionTestUtils.setField(dlEmpRegResource, "dlEmpRegSearchRepository", dlEmpRegSearchRepository);
        this.restDlEmpRegMockMvc = MockMvcBuilders.standaloneSetup(dlEmpRegResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dlEmpReg = new DlEmpReg();
        dlEmpReg.setUserName(DEFAULT_USER_NAME);
        dlEmpReg.setUserPw(DEFAULT_USER_PW);
        dlEmpReg.setCreatedDate(DEFAULT_CREATED_DATE);
        dlEmpReg.setUpdatedDate(DEFAULT_UPDATED_DATE);
        dlEmpReg.setCreatedBy(DEFAULT_CREATED_BY);
        dlEmpReg.setUpdatedBy(DEFAULT_UPDATED_BY);
        dlEmpReg.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDlEmpReg() throws Exception {
        int databaseSizeBeforeCreate = dlEmpRegRepository.findAll().size();

        // Create the DlEmpReg

        restDlEmpRegMockMvc.perform(post("/api/dlEmpRegs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlEmpReg)))
                .andExpect(status().isCreated());

        // Validate the DlEmpReg in the database
        List<DlEmpReg> dlEmpRegs = dlEmpRegRepository.findAll();
        assertThat(dlEmpRegs).hasSize(databaseSizeBeforeCreate + 1);
        DlEmpReg testDlEmpReg = dlEmpRegs.get(dlEmpRegs.size() - 1);
        assertThat(testDlEmpReg.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testDlEmpReg.getUserPw()).isEqualTo(DEFAULT_USER_PW);
        assertThat(testDlEmpReg.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDlEmpReg.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDlEmpReg.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDlEmpReg.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDlEmpReg.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllDlEmpRegs() throws Exception {
        // Initialize the database
        dlEmpRegRepository.saveAndFlush(dlEmpReg);

        // Get all the dlEmpRegs
        restDlEmpRegMockMvc.perform(get("/api/dlEmpRegs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dlEmpReg.getId().intValue())))
                .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
                .andExpect(jsonPath("$.[*].userPw").value(hasItem(DEFAULT_USER_PW.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDlEmpReg() throws Exception {
        // Initialize the database
        dlEmpRegRepository.saveAndFlush(dlEmpReg);

        // Get the dlEmpReg
        restDlEmpRegMockMvc.perform(get("/api/dlEmpRegs/{id}", dlEmpReg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dlEmpReg.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.userPw").value(DEFAULT_USER_PW.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDlEmpReg() throws Exception {
        // Get the dlEmpReg
        restDlEmpRegMockMvc.perform(get("/api/dlEmpRegs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDlEmpReg() throws Exception {
        // Initialize the database
        dlEmpRegRepository.saveAndFlush(dlEmpReg);

		int databaseSizeBeforeUpdate = dlEmpRegRepository.findAll().size();

        // Update the dlEmpReg
        dlEmpReg.setUserName(UPDATED_USER_NAME);
        dlEmpReg.setUserPw(UPDATED_USER_PW);
        dlEmpReg.setCreatedDate(UPDATED_CREATED_DATE);
        dlEmpReg.setUpdatedDate(UPDATED_UPDATED_DATE);
        dlEmpReg.setCreatedBy(UPDATED_CREATED_BY);
        dlEmpReg.setUpdatedBy(UPDATED_UPDATED_BY);
        dlEmpReg.setStatus(UPDATED_STATUS);

        restDlEmpRegMockMvc.perform(put("/api/dlEmpRegs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dlEmpReg)))
                .andExpect(status().isOk());

        // Validate the DlEmpReg in the database
        List<DlEmpReg> dlEmpRegs = dlEmpRegRepository.findAll();
        assertThat(dlEmpRegs).hasSize(databaseSizeBeforeUpdate);
        DlEmpReg testDlEmpReg = dlEmpRegs.get(dlEmpRegs.size() - 1);
        assertThat(testDlEmpReg.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testDlEmpReg.getUserPw()).isEqualTo(UPDATED_USER_PW);
        assertThat(testDlEmpReg.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDlEmpReg.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDlEmpReg.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDlEmpReg.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDlEmpReg.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteDlEmpReg() throws Exception {
        // Initialize the database
        dlEmpRegRepository.saveAndFlush(dlEmpReg);

		int databaseSizeBeforeDelete = dlEmpRegRepository.findAll().size();

        // Get the dlEmpReg
        restDlEmpRegMockMvc.perform(delete("/api/dlEmpRegs/{id}", dlEmpReg.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DlEmpReg> dlEmpRegs = dlEmpRegRepository.findAll();
        assertThat(dlEmpRegs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
