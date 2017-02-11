package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HrEmpAuditObjectionInfo.
 */
@Entity
@Table(name = "hr_emp_audit_objection_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrempauditobjectioninfo")
public class HrEmpAuditObjectionInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "audit_year")
    private Long auditYear;

    @NotNull
    @Column(name = "paragraph_number", nullable = false)
    private String paragraphNumber;

    @NotNull
    @Column(name = "objection_headliine", nullable = false)
    private String objectionHeadliine;

    @Column(name = "objection_amount", precision=10, scale=2)
    private BigDecimal objectionAmount;

    @Column(name = "office_reply_number")
    private String officeReplyNumber;

    @Column(name = "reply_date")
    private LocalDate replyDate;

    @Column(name = "joint_meeting_number")
    private String jointMeetingNumber;

    @Column(name = "joint_meeting_date")
    private LocalDate jointMeetingDate;

    @Column(name = "is_settled")
    private Boolean isSettled;

    @Column(name = "remarks")
    private String remarks;

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
    @JoinColumn(name = "objection_type_id")
    private MiscTypeSetup objectionType;

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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Long getAuditYear() {
        return auditYear;
    }

    public void setAuditYear(Long auditYear) {
        this.auditYear = auditYear;
    }

    public String getParagraphNumber() {
        return paragraphNumber;
    }

    public void setParagraphNumber(String paragraphNumber) {
        this.paragraphNumber = paragraphNumber;
    }

    public String getObjectionHeadliine() {
        return objectionHeadliine;
    }

    public void setObjectionHeadliine(String objectionHeadliine) {
        this.objectionHeadliine = objectionHeadliine;
    }

    public BigDecimal getObjectionAmount() {
        return objectionAmount;
    }

    public void setObjectionAmount(BigDecimal objectionAmount) {
        this.objectionAmount = objectionAmount;
    }

    public String getOfficeReplyNumber() {
        return officeReplyNumber;
    }

    public void setOfficeReplyNumber(String officeReplyNumber) {
        this.officeReplyNumber = officeReplyNumber;
    }

    public LocalDate getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(LocalDate replyDate) {
        this.replyDate = replyDate;
    }

    public String getJointMeetingNumber() {
        return jointMeetingNumber;
    }

    public void setJointMeetingNumber(String jointMeetingNumber) {
        this.jointMeetingNumber = jointMeetingNumber;
    }

    public LocalDate getJointMeetingDate() {
        return jointMeetingDate;
    }

    public void setJointMeetingDate(LocalDate jointMeetingDate) {
        this.jointMeetingDate = jointMeetingDate;
    }

    public Boolean getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(Boolean isSettled) {
        this.isSettled = isSettled;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public MiscTypeSetup getObjectionType() {
        return objectionType;
    }

    public void setObjectionType(MiscTypeSetup MiscTypeSetup) {
        this.objectionType = MiscTypeSetup;
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
        HrEmpAuditObjectionInfo hrEmpAuditObjectionInfo = (HrEmpAuditObjectionInfo) o;
        return Objects.equals(id, hrEmpAuditObjectionInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpAuditObjectionInfo{" +
            "id=" + id +
            ", organizationName='" + organizationName + "'" +
            ", auditYear='" + auditYear + "'" +
            ", paragraphNumber='" + paragraphNumber + "'" +
            ", objectionHeadliine='" + objectionHeadliine + "'" +
            ", objectionAmount='" + objectionAmount + "'" +
            ", officeReplyNumber='" + officeReplyNumber + "'" +
            ", replyDate='" + replyDate + "'" +
            ", jointMeetingNumber='" + jointMeetingNumber + "'" +
            ", jointMeetingDate='" + jointMeetingDate + "'" +
            ", isSettled='" + isSettled + "'" +
            ", remarks='" + remarks + "'" +
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
