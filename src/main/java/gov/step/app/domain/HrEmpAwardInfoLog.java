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
 * A HrEmpAwardInfoLog.
 */
@Entity
@Table(name = "hr_emp_award_info_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrempawardinfolog")
public class HrEmpAwardInfoLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "award_name")
    private String awardName;

    @Column(name = "award_area")
    private String awardArea;

    @Column(name = "award_date")
    private LocalDate awardDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "cert_number")
    private String certNumber;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardArea() {
        return awardArea;
    }

    public void setAwardArea(String awardArea) {
        this.awardArea = awardArea;
    }

    public LocalDate getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(LocalDate awardDate) {
        this.awardDate = awardDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public void setGoOrderDocContentType(String goOrderDocContentType) {this.goOrderDocContentType = goOrderDocContentType;}

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

    public String getCertNumber() {return certNumber;}

    public void setCertNumber(String certNumber) {this.certNumber = certNumber;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpAwardInfoLog hrEmpAwardInfoLog = (HrEmpAwardInfoLog) o;
        return Objects.equals(id, hrEmpAwardInfoLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpAwardInfoLog{" +
            "id=" + id +
            ", awardName='" + awardName + "'" +
            ", awardArea='" + awardArea + "'" +
            ", awardDate='" + awardDate + "'" +
            ", remarks='" + remarks + "'" +
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
