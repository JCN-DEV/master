package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.priority;

/**
 * A SmsServiceComplaint.
 */
@Entity
@Table(name = "sms_service_complaint")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "smsservicecomplaint")
public class SmsServiceComplaint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "previous_code")
    private String previousCode;

    @Column(name = "complaint_code")
    private String complaintCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private priority priority;

    @NotNull
    @Column(name = "complaint_name", nullable = false)
    private String complaintName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "contact_number")
    private String contactNumber;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name="complaint_doc")
    private byte[] complaintDoc;

    @Column(name = "complaint_doc_file_name")
    private String complaintDocFileName;

    @Column(name = "complaint_doc_content_type")
    private String complaintDocContentType;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @ManyToOne
    @JoinColumn(name = "sms_service_name_id")
    private SmsServiceName smsServiceName;

    @ManyToOne
    @JoinColumn(name = "sms_service_type_id")
    private SmsServiceType smsServiceType;

    @ManyToOne
    @JoinColumn(name = "sms_service_department_id")
    private SmsServiceDepartment smsServiceDepartment;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintCode() {
        return complaintCode;
    }

    public void setComplaintCode(String complaintCode) {
        this.complaintCode = complaintCode;
    }

    public String getPreviousCode() {
        return previousCode;
    }

    public void setPreviousCode(String previousCode) {
        this.previousCode = previousCode;
    }

    public priority getPriority() {
        return priority;
    }

    public void setPriority(priority priority) {
        this.priority = priority;
    }

    public String getComplaintName() {
        return complaintName;
    }

    public void setComplaintName(String complaintName) {
        this.complaintName = complaintName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getComplaintDoc() {
        return complaintDoc;
    }

    public void setComplaintDoc(byte[] complaintDoc) {
        this.complaintDoc = complaintDoc;
    }

    public String getComplaintDocContentType() {
        return complaintDocContentType;
    }

    public String getComplaintDocFileName() {
        return complaintDocFileName;
    }

    public void setComplaintDocFileName(String complaintDocFileName) {
        this.complaintDocFileName = complaintDocFileName;
    }

    public void setComplaintDocContentType(String complaintDocContentType) {
        this.complaintDocContentType = complaintDocContentType;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public SmsServiceName getSmsServiceName() {
        return smsServiceName;
    }

    public void setSmsServiceName(SmsServiceName smsServiceName) {
        this.smsServiceName = smsServiceName;
    }

    public SmsServiceType getSmsServiceType() {
        return smsServiceType;
    }

    public void setSmsServiceType(SmsServiceType smsServiceType) {
        this.smsServiceType = smsServiceType;
    }

    public SmsServiceDepartment getSmsServiceDepartment() {
        return smsServiceDepartment;
    }

    public void setSmsServiceDepartment(SmsServiceDepartment smsServiceDepartment) {
        this.smsServiceDepartment = smsServiceDepartment;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsServiceComplaint smsServiceComplaint = (SmsServiceComplaint) o;
        return Objects.equals(id, smsServiceComplaint.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SmsServiceComplaint{" +
            "id=" + id +
            ", previousCode='" + previousCode + "'" +
            ", priority='" + priority + "'" +
            ", complaintName='" + complaintName + "'" +
            ", fullName='" + fullName + "'" +
            ", emailAddress='" + emailAddress + "'" +
            ", contactNumber='" + contactNumber + "'" +
            ", description='" + description + "'" +
            ", complaintDocFileName='" + complaintDocFileName + "'" +
            ", complaintDocContentType='" + complaintDocContentType + "'" +
            ", activeStatus='" + activeStatus + "'" +
            '}';
    }
}
