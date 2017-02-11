package gov.step.app.domain;

import gov.step.app.domain.enumeration.ApprovalStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PfmsLoanApplication.
 */
@Entity
@Table(name = "pfms_loan_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pfmsloanapplication")
public class PfmsLoanApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "loan_amount", nullable = false)
    private Double loanAmount;

    @NotNull
    @Column(name = "no_of_installment", nullable = false)
    private Long noOfInstallment;

    @NotNull
    @Column(name = "reason_of_withdrawal", nullable = false)
    private String reasonOfWithdrawal;

    @Column(name = "is_repay_first_withdraw")
    private Boolean isRepayFirstWithdraw;           // if loan completed this field will be true.

    @Column(name = "is_disburse_loan")
    private Boolean isDisburseLoan;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "approval_status", nullable = false)
    private ApprovalStatus approvalStatus;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Long getNoOfInstallment() {
        return noOfInstallment;
    }

    public void setNoOfInstallment(Long noOfInstallment) {
        this.noOfInstallment = noOfInstallment;
    }

    public String getReasonOfWithdrawal() {
        return reasonOfWithdrawal;
    }

    public void setReasonOfWithdrawal(String reasonOfWithdrawal) {
        this.reasonOfWithdrawal = reasonOfWithdrawal;
    }

    public Boolean getIsDisburseLoan() {
        return isDisburseLoan;
    }

    public void setIsDisburseLoan(Boolean isDisburseLoan) {
        this.isDisburseLoan = isDisburseLoan;
    }

    public Boolean getIsRepayFirstWithdraw() {
        return isRepayFirstWithdraw;
    }

    public void setIsRepayFirstWithdraw(Boolean isRepayFirstWithdraw) {
        this.isRepayFirstWithdraw = isRepayFirstWithdraw;
    }

    public ApprovalStatus getApprovalStatus(){
        return approvalStatus;
    }
    public void setApprovalStatus(ApprovalStatus approvalStatus){
        this.approvalStatus = approvalStatus;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PfmsLoanApplication pfmsLoanApplication = (PfmsLoanApplication) o;

        if ( ! Objects.equals(id, pfmsLoanApplication.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PfmsLoanApplication{" +
            "id=" + id +
            ", loanAmount='" + loanAmount + "'" +
            ", noOfInstallment='" + noOfInstallment + "'" +
            ", reasonOfWithdrawal='" + reasonOfWithdrawal + "'" +
            ", isRepayFirstWithdraw='" + isRepayFirstWithdraw + "'" +
            ", applicationDate='" + applicationDate + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
