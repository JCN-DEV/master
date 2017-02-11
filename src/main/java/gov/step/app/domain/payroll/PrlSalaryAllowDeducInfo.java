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
 * A PrlSalaryAllowDeducInfo.
 */
@Entity
@Table(name = "prl_salary_allow_deduc_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlsalaryallowdeducinfo")
public class PrlSalaryAllowDeducInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "allow_deduc_type")
    private String allowDeducType;

    @Column(name = "allow_deduc_value", precision=10, scale=2)
    private BigDecimal allowDeducValue;

    @NotNull
    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "salary_structure_info_id")
    private PrlSalaryStructureInfo salaryStructureInfo;

    @ManyToOne
    @JoinColumn(name = "allow_deduc_info_id")
    private PrlAllowDeductInfo allowDeducInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllowDeducType() {
        return allowDeducType;
    }

    public void setAllowDeducType(String allowDeducType) {
        this.allowDeducType = allowDeducType;
    }

    public BigDecimal getAllowDeducValue() {
        return allowDeducValue;
    }

    public void setAllowDeducValue(BigDecimal allowDeducValue) {
        this.allowDeducValue = allowDeducValue;
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

    public PrlSalaryStructureInfo getSalaryStructureInfo() {
        return salaryStructureInfo;
    }

    public void setSalaryStructureInfo(PrlSalaryStructureInfo PrlSalaryStructureInfo) {
        this.salaryStructureInfo = PrlSalaryStructureInfo;
    }

    public PrlAllowDeductInfo getAllowDeducInfo() {
        return allowDeducInfo;
    }

    public void setAllowDeducInfo(PrlAllowDeductInfo PrlAllowDeductInfo) {
        this.allowDeducInfo = PrlAllowDeductInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlSalaryAllowDeducInfo prlSalaryAllowDeducInfo = (PrlSalaryAllowDeducInfo) o;
        return Objects.equals(id, prlSalaryAllowDeducInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlSalaryAllowDeducInfo{" +
            "id=" + id +
            ", allowDeducType='" + allowDeducType + "'" +
            ", allowDeducValue='" + allowDeducValue + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
