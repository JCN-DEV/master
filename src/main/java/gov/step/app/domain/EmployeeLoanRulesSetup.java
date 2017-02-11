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
 * A EmployeeLoanRulesSetup.
 */
@Entity
@Table(name = "ELMS_LOAN_RULES_SETUP")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employeeloanrulessetup")
public class EmployeeLoanRulesSetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "loan_name",unique = true, nullable = false)
    private String loanName;

    @Column(name = "loan_rules_description")
    private String loanRulesDescription;

    @NotNull
    @Column(name = "maximum_withdrawal", nullable = false)
    private Long maximumWithdrawal;

    @NotNull
    @Column(name = "minimum_amount_basic", nullable = false)
    private Long minimumAmountBasic;

    @NotNull
    @Column(name = "installment", nullable = false)
    private Long installment;

    @NotNull
    @Column(name = "interest", nullable = false)
    private Long interest;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    private EmployeeLoanTypeSetup employeeLoanTypeSetup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public String getLoanRulesDescription() {
        return loanRulesDescription;
    }

    public void setLoanRulesDescription(String loanRulesDescription) {
        this.loanRulesDescription = loanRulesDescription;
    }

    public Long getMaximumWithdrawal() {
        return maximumWithdrawal;
    }

    public void setMaximumWithdrawal(Long maximumWithdrawal) {
        this.maximumWithdrawal = maximumWithdrawal;
    }

    public Long getMinimumAmountBasic() {
        return minimumAmountBasic;
    }

    public void setMinimumAmountBasic(Long minimumAmountBasic) {
        this.minimumAmountBasic = minimumAmountBasic;
    }

    public Long getInstallment() {
        return installment;
    }

    public void setInstallment(Long installment) {
        this.installment = installment;
    }

    public Long getInterest() {
        return interest;
    }

    public void setInterest(Long interest) {
        this.interest = interest;
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

    public EmployeeLoanTypeSetup getEmployeeLoanTypeSetup() {
        return employeeLoanTypeSetup;
    }

    public void setEmployeeLoanTypeSetup(EmployeeLoanTypeSetup employeeLoanTypeSetup) {
        this.employeeLoanTypeSetup = employeeLoanTypeSetup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeLoanRulesSetup employeeLoanRulesSetup = (EmployeeLoanRulesSetup) o;

        if ( ! Objects.equals(id, employeeLoanRulesSetup.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmployeeLoanRulesSetup{" +
            "id=" + id +
            ", loanName='" + loanName + "'" +
            ", loanRulesDescription='" + loanRulesDescription + "'" +
            ", maximumWithdrawal='" + maximumWithdrawal + "'" +
            ", minimumAmountBasic='" + minimumAmountBasic + "'" +
            ",installment='" +installment + "'" +
            ", interest='" + interest + "'" +
            ", status='" + status + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
