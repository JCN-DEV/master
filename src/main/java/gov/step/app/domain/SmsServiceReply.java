package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SmsServiceReply.
 */
@Entity
@Table(name = "sms_service_reply")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "smsservicereply")
public class SmsServiceReply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cc")
    private String cc;

    @Column(name = "comments")
    private String comments;

    @Column(name = "reply_date")
    private LocalDate replyDate;

    @ManyToOne
    @JoinColumn(name = "sms_service_complaint_id")
    private SmsServiceComplaint smsServiceComplaint;

    @ManyToOne
    @JoinColumn(name = "replier_id")
    private User replier;

    @Column(name = "active_status")
    private Boolean activeStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(LocalDate replyDate) {
        this.replyDate = replyDate;
    }

    public SmsServiceComplaint getSmsServiceComplaint() {
        return smsServiceComplaint;
    }

    public void setSmsServiceComplaint(SmsServiceComplaint smsServiceComplaint) {
        this.smsServiceComplaint = smsServiceComplaint;
    }

    public User getReplier() {
        return replier;
    }

    public void setReplier(User user) {
        this.replier = user;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsServiceReply smsServiceReply = (SmsServiceReply) o;
        return Objects.equals(id, smsServiceReply.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SmsServiceReply{" +
            "id=" + id +
            ", cc='" + cc + "'" +
            ", comments='" + comments + "'" +
            ", replyDate='" + replyDate + "'" +
            '}';
    }
}
