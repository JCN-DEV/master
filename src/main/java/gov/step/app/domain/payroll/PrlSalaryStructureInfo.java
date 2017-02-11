package gov.step.app.domain.payroll;

import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.domain.HrGazetteSetup;
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
 * A PrlSalaryStructureInfo.
 */
@Entity
@Table(name = "prl_salary_structure_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlsalarystructureinfo")
public class PrlSalaryStructureInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "basic_amount", precision=10, scale=2)
    private BigDecimal basicAmount;

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
    @JoinColumn(name = "payscale_info_id")
    private PrlPayscaleInfo payscaleInfo;

    @ManyToOne
    @JoinColumn(name = "payscale_basic_info_id")
    private PrlPayscaleBasicInfo payscaleBasicInfo;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "gazette_info_id")
    private HrGazetteSetup gazetteInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public BigDecimal getBasicAmount() {
        return basicAmount;
    }

    public void setBasicAmount(BigDecimal basicAmount) {
        this.basicAmount = basicAmount;
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

    public PrlPayscaleInfo getPayscaleInfo() {
        return payscaleInfo;
    }

    public void setPayscaleInfo(PrlPayscaleInfo PrlPayscaleInfo) {
        this.payscaleInfo = PrlPayscaleInfo;
    }

    public PrlPayscaleBasicInfo getPayscaleBasicInfo() {
        return payscaleBasicInfo;
    }

    public void setPayscaleBasicInfo(PrlPayscaleBasicInfo PrlPayscaleBasicInfo) {
        this.payscaleBasicInfo = PrlPayscaleBasicInfo;
    }

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

    public HrGazetteSetup getGazetteInfo() {
        return gazetteInfo;
    }

    public void setGazetteInfo(HrGazetteSetup HrGazetteSetup) {
        this.gazetteInfo = HrGazetteSetup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlSalaryStructureInfo prlSalaryStructureInfo = (PrlSalaryStructureInfo) o;
        return Objects.equals(id, prlSalaryStructureInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlSalaryStructureInfo{" +
            "id=" + id +
            ", effectiveDate='" + effectiveDate + "'" +
            ", basicAmount='" + basicAmount + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
