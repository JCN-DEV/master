package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.SmsServiceReply;
import gov.step.app.repository.SmsServiceReplyRepository;
import gov.step.app.repository.search.SmsServiceReplySearchRepository;

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
 * Test class for the SmsServiceReplyResource REST controller.
 *
 * @see SmsServiceReplyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SmsServiceReplyResourceIntTest {

    private static final String DEFAULT_CC = "AAAAA";
    private static final String UPDATED_CC = "BBBBB";
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    private static final LocalDate DEFAULT_REPLY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPLY_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private SmsServiceReplyRepository smsServiceReplyRepository;

    @Inject
    private SmsServiceReplySearchRepository smsServiceReplySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSmsServiceReplyMockMvc;

    private SmsServiceReply smsServiceReply;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsServiceReplyResource smsServiceReplyResource = new SmsServiceReplyResource();
        ReflectionTestUtils.setField(smsServiceReplyResource, "smsServiceReplySearchRepository", smsServiceReplySearchRepository);
        ReflectionTestUtils.setField(smsServiceReplyResource, "smsServiceReplyRepository", smsServiceReplyRepository);
        this.restSmsServiceReplyMockMvc = MockMvcBuilders.standaloneSetup(smsServiceReplyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        smsServiceReply = new SmsServiceReply();
        smsServiceReply.setCc(DEFAULT_CC);
        smsServiceReply.setComments(DEFAULT_COMMENTS);
        smsServiceReply.setReplyDate(DEFAULT_REPLY_DATE);
    }

    @Test
    @Transactional
    public void createSmsServiceReply() throws Exception {
        int databaseSizeBeforeCreate = smsServiceReplyRepository.findAll().size();

        // Create the SmsServiceReply

        restSmsServiceReplyMockMvc.perform(post("/api/smsServiceReplys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceReply)))
                .andExpect(status().isCreated());

        // Validate the SmsServiceReply in the database
        List<SmsServiceReply> smsServiceReplys = smsServiceReplyRepository.findAll();
        assertThat(smsServiceReplys).hasSize(databaseSizeBeforeCreate + 1);
        SmsServiceReply testSmsServiceReply = smsServiceReplys.get(smsServiceReplys.size() - 1);
        assertThat(testSmsServiceReply.getCc()).isEqualTo(DEFAULT_CC);
        assertThat(testSmsServiceReply.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testSmsServiceReply.getReplyDate()).isEqualTo(DEFAULT_REPLY_DATE);
    }

    @Test
    @Transactional
    public void getAllSmsServiceReplys() throws Exception {
        // Initialize the database
        smsServiceReplyRepository.saveAndFlush(smsServiceReply);

        // Get all the smsServiceReplys
        restSmsServiceReplyMockMvc.perform(get("/api/smsServiceReplys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(smsServiceReply.getId().intValue())))
                .andExpect(jsonPath("$.[*].cc").value(hasItem(DEFAULT_CC.toString())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].replyDate").value(hasItem(DEFAULT_REPLY_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSmsServiceReply() throws Exception {
        // Initialize the database
        smsServiceReplyRepository.saveAndFlush(smsServiceReply);

        // Get the smsServiceReply
        restSmsServiceReplyMockMvc.perform(get("/api/smsServiceReplys/{id}", smsServiceReply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(smsServiceReply.getId().intValue()))
            .andExpect(jsonPath("$.cc").value(DEFAULT_CC.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.replyDate").value(DEFAULT_REPLY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsServiceReply() throws Exception {
        // Get the smsServiceReply
        restSmsServiceReplyMockMvc.perform(get("/api/smsServiceReplys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsServiceReply() throws Exception {
        // Initialize the database
        smsServiceReplyRepository.saveAndFlush(smsServiceReply);

		int databaseSizeBeforeUpdate = smsServiceReplyRepository.findAll().size();

        // Update the smsServiceReply
        smsServiceReply.setCc(UPDATED_CC);
        smsServiceReply.setComments(UPDATED_COMMENTS);
        smsServiceReply.setReplyDate(UPDATED_REPLY_DATE);

        restSmsServiceReplyMockMvc.perform(put("/api/smsServiceReplys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsServiceReply)))
                .andExpect(status().isOk());

        // Validate the SmsServiceReply in the database
        List<SmsServiceReply> smsServiceReplys = smsServiceReplyRepository.findAll();
        assertThat(smsServiceReplys).hasSize(databaseSizeBeforeUpdate);
        SmsServiceReply testSmsServiceReply = smsServiceReplys.get(smsServiceReplys.size() - 1);
        assertThat(testSmsServiceReply.getCc()).isEqualTo(UPDATED_CC);
        assertThat(testSmsServiceReply.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testSmsServiceReply.getReplyDate()).isEqualTo(UPDATED_REPLY_DATE);
    }

    @Test
    @Transactional
    public void deleteSmsServiceReply() throws Exception {
        // Initialize the database
        smsServiceReplyRepository.saveAndFlush(smsServiceReply);

		int databaseSizeBeforeDelete = smsServiceReplyRepository.findAll().size();

        // Get the smsServiceReply
        restSmsServiceReplyMockMvc.perform(delete("/api/smsServiceReplys/{id}", smsServiceReply.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsServiceReply> smsServiceReplys = smsServiceReplyRepository.findAll();
        assertThat(smsServiceReplys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
