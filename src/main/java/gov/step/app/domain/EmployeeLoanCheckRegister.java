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
 * A EmployeeLoanCheckRegister.
 */
@Entity
@Table(name = "elms_loan_check_register")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employeeloancheckregister")
public class EmployeeLoanCheckRegister implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "application_type", nullable = false)
    private String applicationType;

    @NotNull
    @Column(name = "number_of_withdrawal", nullable = false)
    private Long numberOfWithdrawal;

    @NotNull
    @Column(name = "check_number", nullable = false)
    private String checkNumber;

    @NotNull
    @Column(name = "token_number", nullable = false)
    private String tokenNumber;

    @Column(name = "status")
    private Integer status;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "create_date", nullable = false)
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
    private EmployeeLoanRequisitionForm loanRequisitionForm;

    @OneToOne
    private EmployeeLoanBillRegister loanBillRegister;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Long getNumberOfWithdrawal() {
        return numberOfWithdrawal;
    }

    public void setNumberOfWithdrawal(Long numberOfWithdrawal) {
        this.numberOfWithdrawal = numberOfWithdrawal;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeLoanCheckRegister employeeLoanCheckRegister = (EmployeeLoanCheckRegister) o;

        if ( ! Objects.equals(id, employeeLoanCheckRegister.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmployeeLoanCheckRegister{" +
            "id=" + id +
            ", applicationType='" + applicationType + "'" +
            ", numberOfWithdrawal='" + numberOfWithdrawal + "'" +
            ", checkNumber='" + checkNumber + "'" +
            ", tokenNumber='" + tokenNumber + "'" +
            ", status='" + status + "'" +
            ", issueDate='" + issueDate + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
