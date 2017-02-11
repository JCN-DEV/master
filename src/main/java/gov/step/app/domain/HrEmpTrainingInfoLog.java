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
 * A HrEmpTrainingInfoLog.
 */
@Entity
@Table(name = "hr_emp_training_info_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremptraininginfolog")
public class HrEmpTrainingInfoLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "institute_name")
    private String instituteName;

    @NotNull
    @Column(name = "course_title")
    private String courseTitle;

    @NotNull
    @Column(name = "duration_from")
    private LocalDate durationFrom;

    @NotNull
    @Column(name = "duration_to")
    private LocalDate durationTo;

    @NotNull
    @Column(name = "result")
    private String result;

    @NotNull
    @Column(name = "office_order_no")
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
    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "action_date")
    private LocalDate actionDate;

    @Column(name = "action_by")
    private Long actionBy;

    @Column(name = "action_comments")
    private String actionComments;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private MiscTypeSetup trainingType;


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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Long logStatus) {
        this.logStatus = logStatus;
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

    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public Long getActionBy() {
        return actionBy;
    }

    public void setActionBy(Long actionBy) {
        this.actionBy = actionBy;
    }

    public String getActionComments() {
        return actionComments;
    }

    public void setActionComments(String actionComments) {
        this.actionComments = actionComments;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpTrainingInfoLog hrEmpTrainingInfoLog = (HrEmpTrainingInfoLog) o;
        return Objects.equals(id, hrEmpTrainingInfoLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpTrainingInfoLog{" +
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
            ", activeStatus='" + activeStatus + "'" +
            ", parentId='" + parentId + "'" +
            ", logStatus='" + logStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", actionDate='" + actionDate + "'" +
            ", actionBy='" + actionBy + "'" +
            ", actionComments='" + actionComments + "'" +
            '}';
    }
}
