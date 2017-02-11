package gov.step.app.domain.payroll;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PrlGeneratedSalaryInfo.
 */
@Entity
@Table(name = "prl_generated_salary_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlgeneratedsalaryinfo")
public class PrlGeneratedSalaryInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "year_name")
    private Long yearName;

    @Column(name = "month_name")
    private String monthName;

    @Column(name = "salary_type")
    private String salaryType;

    @Column(name = "process_date")
    private LocalDate processDate;

    @NotNull
    @Column(name = "disburse_status", nullable = false)
    private String disburseStatus;

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

    public Long getYearName() {
        return yearName;
    }

    public void setYearName(Long yearName) {
        this.yearName = yearName;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public LocalDate getProcessDate() {
        return processDate;
    }

    public void setProcessDate(LocalDate processDate) {
        this.processDate = processDate;
    }

    public String getDisburseStatus() {
        return disburseStatus;
    }

    public void setDisburseStatus(String disburseStatus) {
        this.disburseStatus = disburseStatus;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlGeneratedSalaryInfo prlGeneratedSalaryInfo = (PrlGeneratedSalaryInfo) o;
        return Objects.equals(id, prlGeneratedSalaryInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlGeneratedSalaryInfo{" +
            "id=" + id +
            ", yearName='" + yearName + "'" +
            ", monthName='" + monthName + "'" +
            ", salaryType='" + salaryType + "'" +
            ", processDate='" + processDate + "'" +
            ", disburseStatus='" + disburseStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
