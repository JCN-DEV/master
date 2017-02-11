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

import gov.step.app.domain.enumeration.serviceStatus;

/**
 * A SmsServiceForward.
 */
@Entity
@Table(name = "sms_service_forward")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "smsserviceforward")
public class SmsServiceForward implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cc")
    private String cc;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_status")
    private serviceStatus serviceStatus;

    @Column(name = "comments")
    private String comments;

    @Column(name = "forward_date")
    private LocalDate forwardDate;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @ManyToOne
    @JoinColumn(name = "sms_service_complaint_id")
    private SmsServiceComplaint smsServiceComplaint;

    @ManyToOne
    @JoinColumn(name = "forwarder_id")
    private User forwarder;

    @ManyToOne
    @JoinColumn(name = "sms_service_department_id")
    private SmsServiceDepartment smsServiceDepartment;

    
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

    public serviceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(serviceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getForwardDate() {
        return forwardDate;
    }

    public void setForwardDate(LocalDate forwardDate) {
        this.forwardDate = forwardDate;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public SmsServiceComplaint getSmsServiceComplaint() {
        return smsServiceComplaint;
    }

    public void setSmsServiceComplaint(SmsServiceComplaint smsServiceComplaint) {
        this.smsServiceComplaint = smsServiceComplaint;
    }

    public User getForwarder() {
        return forwarder;
    }

    public void setForwarder(User user) {
        this.forwarder = user;
    }

    public SmsServiceDepartment getSmsServiceDepartment() {
        return smsServiceDepartment;
    }

    public void setSmsServiceDepartment(SmsServiceDepartment smsServiceDepartment) {
        this.smsServiceDepartment = smsServiceDepartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsServiceForward smsServiceForward = (SmsServiceForward) o;
        return Objects.equals(id, smsServiceForward.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SmsServiceForward{" +
            "id=" + id +
            ", cc='" + cc + "'" +
            ", serviceStatus='" + serviceStatus + "'" +
            ", comments='" + comments + "'" +
            ", forwardDate='" + forwardDate + "'" +
            ", activeStatus='" + activeStatus + "'" +
            '}';
    }
}
