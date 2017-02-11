package gov.step.app.domain.payroll;

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
 * A PrlEmpGenSalDetailInfo.
 */
@Entity
@Table(name = "prl_emp_gen_sal_detail_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlempgensaldetailinfo")
public class PrlEmpGenSalDetailInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "allow_deduc_type")
    private String allowDeducType;

    @Column(name = "allow_deduc_id")
    private Long allowDeducId;

    @Column(name = "allow_deduc_name")
    private String allowDeducName;

    @Column(name = "allow_deduc_value", precision=10, scale=2)
    private BigDecimal allowDeducValue;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "employee_salary_info_id")
    private PrlEmpGeneratedSalInfo employeeSalaryInfo;


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

    public Long getAllowDeducId() {
        return allowDeducId;
    }

    public void setAllowDeducId(Long allowDeducId) {
        this.allowDeducId = allowDeducId;
    }

    public String getAllowDeducName() {
        return allowDeducName;
    }

    public void setAllowDeducName(String allowDeducName) {
        this.allowDeducName = allowDeducName;
    }

    public BigDecimal getAllowDeducValue() {
        return allowDeducValue;
    }

    public void setAllowDeducValue(BigDecimal allowDeducValue) {
        this.allowDeducValue = allowDeducValue;
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

    public PrlEmpGeneratedSalInfo getEmployeeSalaryInfo() {
        return employeeSalaryInfo;
    }

    public void setEmployeeSalaryInfo(PrlEmpGeneratedSalInfo PrlEmpGeneratedSalInfo) {
        this.employeeSalaryInfo = PrlEmpGeneratedSalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlEmpGenSalDetailInfo prlEmpGenSalDetailInfo = (PrlEmpGenSalDetailInfo) o;
        return Objects.equals(id, prlEmpGenSalDetailInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlEmpGenSalDetailInfo{" +
            "id=" + id +
            ", allowDeducType='" + allowDeducType + "'" +
            ", allowDeducId='" + allowDeducId + "'" +
            ", allowDeducName='" + allowDeducName + "'" +
            ", allowDeducValue='" + allowDeducValue + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
