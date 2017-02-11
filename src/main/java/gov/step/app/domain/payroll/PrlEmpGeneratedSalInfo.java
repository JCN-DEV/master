package gov.step.app.domain.payroll;

import gov.step.app.domain.HrEmployeeInfo;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PrlEmpGeneratedSalInfo.
 */
@Entity
@Table(name = "prl_emp_generated_sal_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlempgeneratedsalinfo")
public class PrlEmpGeneratedSalInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "basic_amount", precision=10, scale=2)
    private BigDecimal basicAmount;

    @Column(name = "gross_amount", precision=10, scale=2)
    private BigDecimal grossAmount;

    @Column(name = "payable_amount", precision=10, scale=2)
    private BigDecimal payableAmount;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "disburse_status")
    private String disburseStatus;

    @Column(name = "is_disbursable")
    private String isDisbursable;

    @ManyToOne
    @JoinColumn(name = "salary_info_id")
    private PrlGeneratedSalaryInfo salaryInfo;

    @ManyToOne
    @JoinColumn(name = "salary_structure_info_id")
    private PrlSalaryStructureInfo salaryStructureInfo;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBasicAmount() {
        return basicAmount;
    }

    public void setBasicAmount(BigDecimal basicAmount) {
        this.basicAmount = basicAmount;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
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

    public PrlGeneratedSalaryInfo getSalaryInfo() {
        return salaryInfo;
    }

    public void setSalaryInfo(PrlGeneratedSalaryInfo PrlGeneratedSalaryInfo) {
        this.salaryInfo = PrlGeneratedSalaryInfo;
    }

    public PrlSalaryStructureInfo getSalaryStructureInfo() {
        return salaryStructureInfo;
    }

    public void setSalaryStructureInfo(PrlSalaryStructureInfo PrlSalaryStructureInfo) {
        this.salaryStructureInfo = PrlSalaryStructureInfo;
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
        PrlEmpGeneratedSalInfo prlEmpGeneratedSalInfo = (PrlEmpGeneratedSalInfo) o;
        return Objects.equals(id, prlEmpGeneratedSalInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlEmpGeneratedSalInfo{" +
            "id=" + id +
            ", basicAmount='" + basicAmount + "'" +
            ", grossAmount='" + grossAmount + "'" +
            ", payableAmount='" + payableAmount + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
