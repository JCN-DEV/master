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
 * A EmployeeLoanMonthlyDeduction.
 */
@Entity
@Table(name = "elms_monthly_deduction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employeeloanmonthlydeduction")
public class EmployeeLoanMonthlyDeduction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "month", nullable = false)
    private Integer month;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "total_loan_amount_approved")
    private Long totalLoanAmountApproved;

    @Column(name="total_interest_amount")
    private Double totalInterestAmount;

    @Column(name="total_installment")
    private Long totalInstallment;

    @Column(name = "monthly_deduction_amount")
    private Double monthlyDeductionAmount;

    @Column(name = "modified_deduction_amount")
    private Double modifiedDeductionAmount;

    @Column(name = "modified_amount_reason")
    private String modifiedAmountReason;

    @Column(name = "modified_amount_date")
    private LocalDate modifiedAmountDate;

    @Column(name = "deduction_status")
    private Integer deductionStatus;

    @Column(name = "total_deducted_loan_amount")
    private Double totalDeductedLoanAmount;

    @Column(name = "total_deducted_interest_amount")
    private Double totalDeductedInterestAmount;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @OneToOne
    @JoinColumn(name = "loanRequisitionForm_id")
    private EmployeeLoanRequisitionForm loanRequisitionForm;

    @OneToOne
    @JoinColumn(name = "loanbillregister_id")
    private EmployeeLoanBillRegister loanBillRegister;
    @OneToOne
    @JoinColumn(name = "loanCheckRegister_id")
    private EmployeeLoanCheckRegister loanCheckRegister;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getTotalLoanAmountApproved() {
        return totalLoanAmountApproved;
    }

    public void setTotalLoanAmountApproved(Long totalLoanAmountApproved) {
        this.totalLoanAmountApproved = totalLoanAmountApproved;
    }

    public Double getTotalInterestAmount() {
        return totalInterestAmount;
    }

    public void setTotalInterestAmount(Double totalInterestAmount) {
        this.totalInterestAmount = totalInterestAmount;
    }

    public Long getTotalInstallment() {
        return totalInstallment;
    }

    public void setTotalInstallment(Long totalInstallment) {
        this.totalInstallment = totalInstallment;
    }

    public Double getMonthlyDeductionAmount() {
        return monthlyDeductionAmount;
    }

    public void setMonthlyDeductionAmount(Double monthlyDeductionAmount) {
        this.monthlyDeductionAmount = monthlyDeductionAmount;
    }

    public Double getModifiedDeductionAmount() {
        return modifiedDeductionAmount;
    }

    public void setModifiedDeductionAmount(Double modifiedDeductionAmount) {
        this.modifiedDeductionAmount = modifiedDeductionAmount;
    }

    public String getModifiedAmountReason() {
        return modifiedAmountReason;
    }

    public void setModifiedAmountReason(String modifiedAmountReason) {
        this.modifiedAmountReason = modifiedAmountReason;
    }

    public LocalDate getModifiedAmountDate() {
        return modifiedAmountDate;
    }

    public void setModifiedAmountDate(LocalDate modifiedAmountDate) {
        this.modifiedAmountDate = modifiedAmountDate;
    }

    public Double getTotalDeductedLoanAmount() {
        return totalDeductedLoanAmount;
    }

    public void setTotalDeductedLoanAmount(Double totalDeductedLoanAmount) {
        this.totalDeductedLoanAmount = totalDeductedLoanAmount;
    }

    public Double getTotalDeductedInterestAmount() {
        return totalDeductedInterestAmount;
    }

    public void setTotalDeductedInterestAmount(Double totalDeductedInterestAmount) {
        this.totalDeductedInterestAmount = totalDeductedInterestAmount;
    }

    public Integer getDeductionStatus() {
        return deductionStatus;
    }

    public void setDeductionStatus(Integer deductionStatus) {
        this.deductionStatus = deductionStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public void setEmployeeInfo(HrEmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public EmployeeLoanRequisitionForm getLoanRequisitionForm() {
        return loanRequisitionForm;
    }

    public void setLoanRequisitionForm(EmployeeLoanRequisitionForm loanRequisitionForm) {
        this.loanRequisitionForm = loanRequisitionForm;
    }

    public EmployeeLoanBillRegister getLoanBillRegister() {
        return loanBillRegister;
    }

    public void setLoanBillRegister(EmployeeLoanBillRegister loanBillRegister) {
        this.loanBillRegister = loanBillRegister;
    }

    public EmployeeLoanCheckRegister getLoanCheckRegister() {
        return loanCheckRegister;
    }

    public void setLoanCheckRegister(EmployeeLoanCheckRegister loanCheckRegister) {
        this.loanCheckRegister = loanCheckRegister;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeLoanMonthlyDeduction employeeLoanMonthlyDeduction = (EmployeeLoanMonthlyDeduction) o;

        if ( ! Objects.equals(id, employeeLoanMonthlyDeduction.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmployeeLoanMonthlyDeduction{" +
            "id=" + id +
            ", month='" + month + "'" +
            ", year='" + year + "'" +
            ", monthlyDeductionAmount='" + monthlyDeductionAmount + "'" +
            ", reason='" + reason + "'" +
            ", status='" + status + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
