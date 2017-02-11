package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HrEmpTransferApplInfo.
 */
@Entity
@Table(name = "hr_emp_transfer_appl_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremptransferapplinfo")
public class HrEmpTransferApplInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "transfer_reason", nullable = false)
    private String transferReason;

    @NotNull
    @Column(name = "office_order_number", nullable = false)
    private String officeOrderNumber;

    @Column(name = "office_order_date")
    private LocalDate officeOrderDate;

    @NotNull
    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus;

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "selected_option")
    private Long selectedOption;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "log_comments")
    private String logComments;

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

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
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

    public String getLogComments() {
        return logComments;
    }

    public void setLogComments(String logComments) {
        this.logComments = logComments;
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

    public Long getSelectedOption() {return selectedOption;}

    public void setSelectedOption(Long selectedOption) {this.selectedOption = selectedOption;}

    public HrEmpWorkAreaDtlInfo getOrgOptionOne() {return orgOptionOne;}

    public void setOrgOptionOne(HrEmpWorkAreaDtlInfo orgOptionOne) {this.orgOptionOne = orgOptionOne;}

    public HrDesignationSetup getDesigOptionOne() {return desigOptionOne;}

    public void setDesigOptionOne(HrDesignationSetup desigOptionOne) {this.desigOptionOne = desigOptionOne;}

    public HrEmpWorkAreaDtlInfo getOrgOptionTwo() {return orgOptionTwo;}

    public void setOrgOptionTwo(HrEmpWorkAreaDtlInfo orgOptionTwo) {this.orgOptionTwo = orgOptionTwo;}

    public HrDesignationSetup getDesigOptionTwo() {return desigOptionTwo;}

    public void setDesigOptionTwo(HrDesignationSetup desigOptionTwo) {this.desigOptionTwo = desigOptionTwo;}

    public HrEmpWorkAreaDtlInfo getOrgOptionThree() {return orgOptionThree;}

    public void setOrgOptionThree(HrEmpWorkAreaDtlInfo orgOptionThree) {this.orgOptionThree = orgOptionThree;}

    public HrDesignationSetup getDesigOptionThree() {return desigOptionThree;}

    public void setDesigOptionThree(HrDesignationSetup desigOptionThree) {this.desigOptionThree = desigOptionThree;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpTransferApplInfo hrEmpTransferApplInfo = (HrEmpTransferApplInfo) o;
        return Objects.equals(id, hrEmpTransferApplInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpTransferApplInfo{" +
            "id=" + id +
            ", transferReason='" + transferReason + "'" +
            ", officeOrderNumber='" + officeOrderNumber + "'" +
            ", officeOrderDate='" + officeOrderDate + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", logId='" + logId + "'" +
            ", logStatus='" + logStatus + "'" +
            ", logComments='" + logComments + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
