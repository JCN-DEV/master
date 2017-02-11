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
 * A EmployeeLoanRequisitionForm.
 */
@Entity
@Table(name = "ELMS_LOAN_REQUSITION_FORM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employeeloanrequisitionform")
public class EmployeeLoanRequisitionForm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @NotNull
    @Column(name = "loan_requisition_code")
    private String loanRequisitionCode;

    //@NotNull
    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;


    @Column(name = "amount")
    private Long amount;

    @Column(name = "installment")
    private Long installment;

    @Column(name = "approve_status")
    private Integer approveStatus;

    @Column(name = "status")
    private boolean status;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    private EmployeeLoanTypeSetup employeeLoanTypeSetup;

    @ManyToOne
    private EmployeeLoanRulesSetup employeeLoanRulesSetup;

    @Column(name="apply_type")
    private String applyType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanRequisitionCode() {
        return loanRequisitionCode;
    }

    public void setLoanRequisitionCode(String loanRequisitionCode) {
        this.loanRequisitionCode = loanRequisitionCode;
    }

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getInstallment() {
        return installment;
    }

    public void setInstallment(Long installment) {
        this.installment = installment;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
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

    public EmployeeLoanTypeSetup getEmployeeLoanTypeSetup() {
        return employeeLoanTypeSetup;
    }

    public void setEmployeeLoanTypeSetup(EmployeeLoanTypeSetup employeeLoanTypeSetup) {
        this.employeeLoanTypeSetup = employeeLoanTypeSetup;
    }

    public EmployeeLoanRulesSetup getEmployeeLoanRulesSetup() {
        return employeeLoanRulesSetup;
    }

    public void setEmployeeLoanRulesSetup(EmployeeLoanRulesSetup employeeLoanRulesSetup) {
        this.employeeLoanRulesSetup = employeeLoanRulesSetup;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeLoanRequisitionForm employeeLoanRequisitionForm = (EmployeeLoanRequisitionForm) o;

        if ( ! Objects.equals(id, employeeLoanRequisitionForm.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmployeeLoanRequisitionForm{" +
            "id=" + id +
            ", loanRequisitionCode='" + loanRequisitionCode + "'" +
            ", employeeInfo='" + employeeInfo + "'" +
            ", amount='" + amount + "'" +
            ", installment='" + installment + "'" +
            ", approveStatus='" + approveStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
