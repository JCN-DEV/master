package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HrEmpTransferApplInfoLog.
 */
@Entity
@Table(name = "hr_emp_transfer_appl_info_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremptransferapplinfolog")
public class HrEmpTransferApplInfoLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "transfer_reason")
    private String transferReason;

    @Column(name = "office_order_number")
    private String officeOrderNumber;

    @Column(name = "office_order_date")
    private LocalDate officeOrderDate;

    @Column(name = "selected_option")
    private Long selectedOption;

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
    @JoinColumn(name = "org_option_one_id")
    private HrEmpWorkAreaDtlInfo orgOptionOne;

    @ManyToOne
    @JoinColumn(name = "desig_option_one_id")
    private HrDesignationSetup desigOptionOne;

    @ManyToOne
    @JoinColumn(name = "org_option_two_id")
    private HrEmpWorkAreaDtlInfo orgOptionTwo;

    @ManyToOne
    @JoinColumn(name = "desig_option_two_id")
    private HrDesignationSetup desigOptionTwo;

    @ManyToOne
    @JoinColumn(name = "org_option_three_id")
    private HrEmpWorkAreaDtlInfo orgOptionThree;

    @ManyToOne
    @JoinColumn(name = "desig_option_three_id")
    private HrDesignationSetup desigOptionThree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransferReason() {
        return transferReason;
    }

    public void setTransferReason(String transferReason) {
        this.transferReason = transferReason;
    }

    public String getOfficeOrderNumber() {
        return officeOrderNumber;
    }

    public void setOfficeOrderNumber(String officeOrderNumber) {
        this.officeOrderNumber = officeOrderNumber;
    }

    public LocalDate getOfficeOrderDate() {
        return officeOrderDate;
    }

    public void setOfficeOrderDate(LocalDate officeOrderDate) {
        this.officeOrderDate = officeOrderDate;
    }

    public Long getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Long selectedOption) {
        this.selectedOption = selectedOption;
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

    public HrEmpWorkAreaDtlInfo getOrgOptionOne() {
        return orgOptionOne;
    }

    public void setOrgOptionOne(HrEmpWorkAreaDtlInfo HrEmpWorkAreaDtlInfo) {
        this.orgOptionOne = HrEmpWorkAreaDtlInfo;
    }

    public HrDesignationSetup getDesigOptionOne() {
        return desigOptionOne;
    }

    public void setDesigOptionOne(HrDesignationSetup HrDesignationSetup) {
        this.desigOptionOne = HrDesignationSetup;
    }

    public HrEmpWorkAreaDtlInfo getOrgOptionTwo() {
        return orgOptionTwo;
    }

    public void setOrgOptionTwo(HrEmpWorkAreaDtlInfo HrEmpWorkAreaDtlInfo) {
        this.orgOptionTwo = HrEmpWorkAreaDtlInfo;
    }

    public HrDesignationSetup getDesigOptionTwo() {
        return desigOptionTwo;
    }

    public void setDesigOptionTwo(HrDesignationSetup HrDesignationSetup) {
        this.desigOptionTwo = HrDesignationSetup;
    }

    public HrEmpWorkAreaDtlInfo getOrgOptionThree() {
        return orgOptionThree;
    }

    public void setOrgOptionThree(HrEmpWorkAreaDtlInfo HrEmpWorkAreaDtlInfo) {
        this.orgOptionThree = HrEmpWorkAreaDtlInfo;
    }

    public HrDesignationSetup getDesigOptionThree() {
        return desigOptionThree;
    }

    public void setDesigOptionThree(HrDesignationSetup HrDesignationSetup) {
        this.desigOptionThree = HrDesignationSetup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpTransferApplInfoLog hrEmpTransferApplInfoLog = (HrEmpTransferApplInfoLog) o;
        return Objects.equals(id, hrEmpTransferApplInfoLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpTransferApplInfoLog{" +
            "id=" + id +
            ", transferReason='" + transferReason + "'" +
            ", officeOrderNumber='" + officeOrderNumber + "'" +
            ", officeOrderDate='" + officeOrderDate + "'" +
            ", selectedOption='" + selectedOption + "'" +
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
