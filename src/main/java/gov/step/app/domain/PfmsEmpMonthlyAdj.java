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
 * A PfmsEmpMonthlyAdj.
 */
@Entity
@Table(name = "pfms_emp_monthly_adj")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pfmsempmonthlyadj")
public class PfmsEmpMonthlyAdj implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "adj_year", nullable = false)
    private Long adjYear;

    @NotNull
    @Column(name = "adj_month", nullable = false)
    private String adjMonth;

    @NotNull
    @Column(name = "deducted_amount", nullable = false)
    private Double deductedAmount;

    @NotNull
    @Column(name = "modified_amount", nullable = false)
    private Double modifiedAmount;

    @NotNull
    @Column(name = "reason", nullable = false)
    private String reason;

    @NotNull
    @Column(name = "active_status", nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "pfms_loan_app_id")
    private PfmsLoanApplication pfmsLoanApp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdjYear() {
        return adjYear;
    }

    public void setAdjYear(Long adjYear) {
        this.adjYear = adjYear;
    }

    public String getAdjMonth() {
        return adjMonth;
    }

    public void setAdjMonth(String adjMonth) {
        this.adjMonth = adjMonth;
    }

    public Double getDeductedAmount() {
        return deductedAmount;
    }

    public void setDeductedAmount(Double deductedAmount) {
        this.deductedAmount = deductedAmount;
    }

    public PfmsLoanApplication getPfmsLoanApp(){
        return pfmsLoanApp;
    }

    public void setPfmsLoanApp(PfmsLoanApplication pfmsLoanApp){
        this.pfmsLoanApp = pfmsLoanApp;
    }

    public Double getModifiedAmount() {
        return modifiedAmount;
    }

    public void setModifiedAmount(Double modifiedAmount) {
        this.modifiedAmount = modifiedAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

        PfmsEmpMonthlyAdj pfmsEmpMonthlyAdj = (PfmsEmpMonthlyAdj) o;

        if ( ! Objects.equals(id, pfmsEmpMonthlyAdj.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PfmsEmpMonthlyAdj{" +
            "id=" + id +
            ", adjYear='" + adjYear + "'" +
            ", adjMonth='" + adjMonth + "'" +
            ", deductedAmount='" + deductedAmount + "'" +
            ", modifiedAmount='" + modifiedAmount + "'" +
            ", reason='" + reason + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
