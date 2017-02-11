package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HrEmpTrainingInfo.
 */
@Entity
@Table(name = "hr_emp_training_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremptraininginfo")
public class HrEmpTrainingInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "institute_name", nullable = false)
    private String instituteName;

    @NotNull
    @Column(name = "course_title", nullable = false)
    private String courseTitle;

    @NotNull
    @Column(name = "duration_from", nullable = false)
    private LocalDate durationFrom;

    @NotNull
    @Column(name = "duration_to", nullable = false)
    private LocalDate durationTo;

    @NotNull
    @Column(name = "result", nullable = false)
    private String result;

    @NotNull
    @Column(name = "office_order_no", nullable = false)
    private String officeOrderNo;

    @Column(name = "job_category")
    private String jobCategory;

    @Column(name = "country")
    private String country;

    @Lob
    @Column(name = "go_order_doc")
    private byte[] goOrderDoc;

    @Column(name = "go_order_doc_content_type")
    private String goOrderDocContentType;

    @Column(name = "go_order_doc_name")
    private String goOrderDocName;

    @Lob
    @Column(name = "cert_doc")
    private byte[] certDoc;

    @Column(name = "cert_doc_content_type")
    private String certDocContentType;

    @Column(name = "cert_doc_name")
    private String certDocName;

    @NotNull
    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private MiscTypeSetup trainingType;

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "log_comments")
    private String logComments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public LocalDate getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(LocalDate durationFrom) {
        this.durationFrom = durationFrom;
    }

    public LocalDate getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(LocalDate durationTo) {
        this.durationTo = durationTo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOfficeOrderNo() {
        return officeOrderNo;
    }

    public void setOfficeOrderNo(String officeOrderNo) {
        this.officeOrderNo = officeOrderNo;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public byte[] getGoOrderDoc() {
        return goOrderDoc;
    }

    public void setGoOrderDoc(byte[] goOrderDoc) {
        this.goOrderDoc = goOrderDoc;
    }

    public String getGoOrderDocContentType() {
        return goOrderDocContentType;
    }

    public void setGoOrderDocContentType(String goOrderDocContentType) {
        this.goOrderDocContentType = goOrderDocContentType;
    }

    public String getGoOrderDocName() {
        return goOrderDocName;
    }

    public void setGoOrderDocName(String goOrderDocName) {
        this.goOrderDocName = goOrderDocName;
    }

    public byte[] getCertDoc() {
        return certDoc;
    }

    public void setCertDoc(byte[] certDoc) {
        this.certDoc = certDoc;
    }

    public String getCertDocContentType() {
        return certDocContentType;
    }

    public void setCertDocContentType(String certDocContentType) {
        this.certDocContentType = certDocContentType;
    }

    public String getCertDocName() {
        return certDocName;
    }

    public void setCertDocName(String certDocName) {
        this.certDocName = certDocName;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

    public MiscTypeSetup getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(MiscTypeSetup MiscTypeSetup) {
        this.trainingType = MiscTypeSetup;
    }

    public Long getLogId() { return logId;}

    public void setLogId(Long logId) {this.logId = logId;}

    public Long getLogStatus() {return logStatus;}

    public void setLogStatus(Long logStatus) {this.logStatus = logStatus;}

    public String getLogComments() {return logComments;}

    public void setLogComments(String logComments) {this.logComments = logComments;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpTrainingInfo hrEmpTrainingInfo = (HrEmpTrainingInfo) o;
        return Objects.equals(id, hrEmpTrainingInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpTrainingInfo{" +
            "id=" + id +
            ", instituteName='" + instituteName + "'" +
            ", courseTitle='" + courseTitle + "'" +
            ", durationFrom='" + durationFrom + "'" +
            ", durationTo='" + durationTo + "'" +
            ", result='" + result + "'" +
            ", officeOrderNo='" + officeOrderNo + "'" +
            ", jobCategory='" + jobCategory + "'" +
            ", country='" + country + "'" +
            ", goOrderDoc='" + goOrderDoc + "'" +
            ", goOrderDocContentType='" + goOrderDocContentType + "'" +
            ", goOrderDocName='" + goOrderDocName + "'" +
            ", certDoc='" + certDoc + "'" +
            ", certDocContentType='" + certDocContentType + "'" +
            ", certDocName='" + certDocName + "'" +
            ", logId='" + logId + "'" +
            ", logStatus='" + logStatus + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
