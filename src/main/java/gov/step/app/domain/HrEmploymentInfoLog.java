package gov.step.app.domain;

import gov.step.app.domain.payroll.PrlLocalitySetInfo;
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
 * A HrEmploymentInfoLog.
 */
@Entity
@Table(name = "hr_employment_info_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremploymentinfolog")
public class HrEmploymentInfoLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "present_institute", nullable = false)
    private String presentInstitute;

    @NotNull
    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @NotNull
    @Column(name = "regularization_date")
    private LocalDate regularizationDate;

    @Column(name = "job_conf_notice_no")
    private String jobConfNoticeNo;

    @Column(name = "confirmation_date")
    private LocalDate confirmationDate;

    @Column(name = "office_order_no")
    private String officeOrderNo;

    @Column(name = "office_order_date")
    private LocalDate officeOrderDate;

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
    @JoinColumn(name = "employee_type_id")
    private HrEmplTypeInfo employeeType;

    @ManyToOne
    @JoinColumn(name = "present_pay_scale_id")
    private HrPayScaleSetup presentPayScale;

    @ManyToOne
    @JoinColumn(name = "locality_set_info_id")
    private PrlLocalitySetInfo localitySetInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresentInstitute() {
        return presentInstitute;
    }

    public void setPresentInstitute(String presentInstitute) {
        this.presentInstitute = presentInstitute;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public LocalDate getRegularizationDate() {
        return regularizationDate;
    }

    public void setRegularizationDate(LocalDate regularizationDate) {
        this.regularizationDate = regularizationDate;
    }

    public String getJobConfNoticeNo() {
        return jobConfNoticeNo;
    }

    public void setJobConfNoticeNo(String jobConfNoticeNo) {
        this.jobConfNoticeNo = jobConfNoticeNo;
    }

    public LocalDate getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(LocalDate confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getOfficeOrderNo() {
        return officeOrderNo;
    }

    public void setOfficeOrderNo(String officeOrderNo) {
        this.officeOrderNo = officeOrderNo;
    }

    public LocalDate getOfficeOrderDate() {
        return officeOrderDate;
    }

    public void setOfficeOrderDate(LocalDate officeOrderDate) {
        this.officeOrderDate = officeOrderDate;
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

    public HrEmplTypeInfo getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(HrEmplTypeInfo HrEmplTypeInfo) {
        this.employeeType = HrEmplTypeInfo;
    }

    public HrPayScaleSetup getPresentPayScale() {
        return presentPayScale;
    }

    public void setPresentPayScale(HrPayScaleSetup HrPayScaleSetup) {
        this.presentPayScale = HrPayScaleSetup;
    }

    public PrlLocalitySetInfo getLocalitySetInfo() {return localitySetInfo;}

    public void setLocalitySetInfo(PrlLocalitySetInfo localitySetInfo) {this.localitySetInfo = localitySetInfo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmploymentInfoLog hrEmploymentInfoLog = (HrEmploymentInfoLog) o;
        return Objects.equals(id, hrEmploymentInfoLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmploymentInfoLog{" +
            "id=" + id +
            ", presentInstitute='" + presentInstitute + "'" +
            ", joiningDate='" + joiningDate + "'" +
            ", regularizationDate='" + regularizationDate + "'" +
            ", jobConfNoticeNo='" + jobConfNoticeNo + "'" +
            ", confirmationDate='" + confirmationDate + "'" +
            ", officeOrderNo='" + officeOrderNo + "'" +
            ", officeOrderDate='" + officeOrderDate + "'" +
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
