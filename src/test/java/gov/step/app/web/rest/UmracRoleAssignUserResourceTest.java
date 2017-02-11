package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.UmracRoleAssignUser;
import gov.step.app.repository.UmracRoleAssignUserRepository;
import gov.step.app.repository.search.UmracRoleAssignUserSearchRepository;

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
 * Test class for the UmracRoleAssignUserResource REST controller.
 *
 * @see UmracRoleAssignUserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UmracRoleAssignUserResourceTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final LocalDate DEFAULT_UPDATED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private UmracRoleAssignUserRepository umracRoleAssignUserRepository;

    @Inject
    private UmracRoleAssignUserSearchRepository umracRoleAssignUserSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmracRoleAssignUserMockMvc;

    private UmracRoleAssignUser umracRoleAssignUser;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmracRoleAssignUserResource umracRoleAssignUserResource = new UmracRoleAssignUserResource();
        ReflectionTestUtils.setField(umracRoleAssignUserResource, "umracRoleAssignUserRepository", umracRoleAssignUserRepository);
        ReflectionTestUtils.setField(umracRoleAssignUserResource, "umracRoleAssignUserSearchRepository", umracRoleAssignUserSearchRepository);
        this.restUmracRoleAssignUserMockMvc = MockMvcBuilders.standaloneSetup(umracRoleAssignUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umracRoleAssignUser = new UmracRoleAssignUser();
        umracRoleAssignUser.setDescription(DEFAULT_DESCRIPTION);
        umracRoleAssignUser.setStatus(DEFAULT_STATUS);
        umracRoleAssignUser.setCreateDate(DEFAULT_CREATE_DATE);
        umracRoleAssignUser.setCreateBy(DEFAULT_CREATE_BY);
        umracRoleAssignUser.setUpdatedBy(DEFAULT_UPDATED_BY);
        umracRoleAssignUser.setUpdatedTime(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createUmracRoleAssignUser() throws Exception {
        int databaseSizeBeforeCreate = umracRoleAssignUserRepository.findAll().size();

        // Create the UmracRoleAssignUser

        restUmracRoleAssignUserMockMvc.perform(post("/api/umracRoleAssignUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracRoleAssignUser)))
                .andExpect(status().isCreated());

        // Validate the UmracRoleAssignUser in the database
        List<UmracRoleAssignUser> umracRoleAssignUsers = umracRoleAssignUserRepository.findAll();
        assertThat(umracRoleAssignUsers).hasSize(databaseSizeBeforeCreate + 1);
        UmracRoleAssignUser testUmracRoleAssignUser = umracRoleAssignUsers.get(umracRoleAssignUsers.size() - 1);
        assertThat(testUmracRoleAssignUser.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUmracRoleAssignUser.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUmracRoleAssignUser.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testUmracRoleAssignUser.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testUmracRoleAssignUser.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUmracRoleAssignUser.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllUmracRoleAssignUsers() throws Exception {
        // Initialize the database
        umracRoleAssignUserRepository.saveAndFlush(umracRoleAssignUser);

        // Get all the umracRoleAssignUsers
        restUmracRoleAssignUserMockMvc.perform(get("/api/umracRoleAssignUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umracRoleAssignUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
                .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getUmracRoleAssignUser() throws Exception {
        // Initialize the database
        umracRoleAssignUserRepository.saveAndFlush(umracRoleAssignUser);

        // Get the umracRoleAssignUser
        restUmracRoleAssignUserMockMvc.perform(get("/api/umracRoleAssignUsers/{id}", umracRoleAssignUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umracRoleAssignUser.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUmracRoleAssignUser() throws Exception {
        // Get the umracRoleAssignUser
        restUmracRoleAssignUserMockMvc.perform(get("/api/umracRoleAssignUsers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmracRoleAssignUser() throws Exception {
        // Initialize the database
        umracRoleAssignUserRepository.saveAndFlush(umracRoleAssignUser);

		int databaseSizeBeforeUpdate = umracRoleAssignUserRepository.findAll().size();

        // Update the umracRoleAssignUser
        umracRoleAssignUser.setDescription(UPDATED_DESCRIPTION);
        umracRoleAssignUser.setStatus(UPDATED_STATUS);
        umracRoleAssignUser.setCreateDate(UPDATED_CREATE_DATE);
        umracRoleAssignUser.setCreateBy(UPDATED_CREATE_BY);
        umracRoleAssignUser.setUpdatedBy(UPDATED_UPDATED_BY);
        umracRoleAssignUser.setUpdatedTime(UPDATED_UPDATED_TIME);

        restUmracRoleAssignUserMockMvc.perform(put("/api/umracRoleAssignUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umracRoleAssignUser)))
                .andExpect(status().isOk());

        // Validate the UmracRoleAssignUser in the database
        List<UmracRoleAssignUser> umracRoleAssignUsers = umracRoleAssignUserRepository.findAll();
        assertThat(umracRoleAssignUsers).hasSize(databaseSizeBeforeUpdate);
        UmracRoleAssignUser testUmracRoleAssignUser = umracRoleAssignUsers.get(umracRoleAssignUsers.size() - 1);
        assertThat(testUmracRoleAssignUser.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUmracRoleAssignUser.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUmracRoleAssignUser.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testUmracRoleAssignUser.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testUmracRoleAssignUser.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUmracRoleAssignUser.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteUmracRoleAssignUser() throws Exception {
        // Initialize the database
        umracRoleAssignUserRepository.saveAndFlush(umracRoleAssignUser);

		int databaseSizeBeforeDelete = umracRoleAssignUserRepository.findAll().size();

        // Get the umracRoleAssignUser
        restUmracRoleAssignUserMockMvc.perform(delete("/api/umracRoleAssignUsers/{id}", umracRoleAssignUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmracRoleAssignUser> umracRoleAssignUsers = umracRoleAssignUserRepository.findAll();
        assertThat(umracRoleAssignUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
