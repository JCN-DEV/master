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
 * A HrEmploymentInfo.
 */
@Entity
@Table(name = "hr_employment_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremploymentinfo")
public class HrEmploymentInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "present_institute", nullable = false)
    private String presentInstitute;

    @NotNull
    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;

    @NotNull
    @Column(name = "regularization_date", nullable = false)
    private LocalDate regularizationDate;

    @Column(name = "job_conf_notice_no")
    private String jobConfNoticeNo;

    @Column(name = "confirmation_date")
    private LocalDate confirmationDate;

    @Column(name = "office_order_no")
    private String officeOrderNo;

    @Column(name = "office_order_date")
    private LocalDate officeOrderDate;

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "log_comments")
    private String logComments;

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

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Long logStatus) {
        this.logStatus = logStatus;
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

    public String getLogComments() {
        return logComments;
    }

    public void setLogComments(String logComments) {
        this.logComments = logComments;
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
        HrEmploymentInfo hrEmploymentInfo = (HrEmploymentInfo) o;
        return Objects.equals(id, hrEmploymentInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmploymentInfo{" +
            "id=" + id +
            ", presentInstitute='" + presentInstitute + "'" +
            ", joiningDate='" + joiningDate + "'" +
            ", regularizationDate='" + regularizationDate + "'" +
            ", jobConfNoticeNo='" + jobConfNoticeNo + "'" +
            ", confirmationDate='" + confirmationDate + "'" +
            ", officeOrderNo='" + officeOrderNo + "'" +
            ", officeOrderDate='" + officeOrderDate + "'" +
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
