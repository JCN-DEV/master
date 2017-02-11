package gov.step.app.domain.payroll;

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
 * A PrlBudgetSetupInfo.
 */
@Entity
@Table(name = "prl_budget_setup_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlbudgetsetupinfo")
public class PrlBudgetSetupInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "budget_type", nullable = false)
    private String budgetType;

    @NotNull
    @Column(name = "code_type", nullable = false)
    private String codeType;

    @NotNull
    @Column(name = "budget_year", nullable = false)
    private Long budgetYear;

    @Column(name = "code_value", precision=10, scale=2)
    private BigDecimal codeValue;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "economical_code_info_id")
    private PrlEconomicalCodeInfo economicalCodeInfo;

    @ManyToOne
    @JoinColumn(name = "allowance_deduction_info_id")
    private PrlAllowDeductInfo allowanceDeductionInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public Long getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(Long budgetYear) {
        this.budgetYear = budgetYear;
    }

    public BigDecimal getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(BigDecimal codeValue) {
        this.codeValue = codeValue;
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

    public PrlEconomicalCodeInfo getEconomicalCodeInfo() {
        return economicalCodeInfo;
    }

    public void setEconomicalCodeInfo(PrlEconomicalCodeInfo PrlEconomicalCodeInfo) {
        this.economicalCodeInfo = PrlEconomicalCodeInfo;
    }

    public PrlAllowDeductInfo getAllowanceDeductionInfo() {
        return allowanceDeductionInfo;
    }

    public void setAllowanceDeductionInfo(PrlAllowDeductInfo PrlAllowDeductInfo) {
        this.allowanceDeductionInfo = PrlAllowDeductInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlBudgetSetupInfo prlBudgetSetupInfo = (PrlBudgetSetupInfo) o;
        return Objects.equals(id, prlBudgetSetupInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlBudgetSetupInfo{" +
            "id=" + id +
            ", budgetType='" + budgetType + "'" +
            ", codeType='" + codeType + "'" +
            ", budgetYear='" + budgetYear + "'" +
            ", codeValue='" + codeValue + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
