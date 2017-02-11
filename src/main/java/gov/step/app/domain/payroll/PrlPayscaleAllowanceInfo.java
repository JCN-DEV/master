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
 * A PrlPayscaleAllowanceInfo.
 */
@Entity
@Table(name = "prl_payscale_allowance_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlpayscaleallowanceinfo")
public class PrlPayscaleAllowanceInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fixed_basic")
    private Boolean fixedBasic;

    @Column(name = "basic_minimum", precision=10, scale=2)
    private BigDecimal basicMinimum;

    @Column(name = "basic_maximum", precision=10, scale=2)
    private BigDecimal basicMaximum;

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
    @JoinColumn(name = "allowance_info_id")
    private PrlAllowDeductInfo allowanceInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFixedBasic() {
        return fixedBasic;
    }

    public void setFixedBasic(Boolean fixedBasic) {
        this.fixedBasic = fixedBasic;
    }

    public BigDecimal getBasicMinimum() {
        return basicMinimum;
    }

    public void setBasicMinimum(BigDecimal basicMinimum) {
        this.basicMinimum = basicMinimum;
    }

    public BigDecimal getBasicMaximum() {
        return basicMaximum;
    }

    public void setBasicMaximum(BigDecimal basicMaximum) {
        this.basicMaximum = basicMaximum;
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

    public PrlAllowDeductInfo getAllowanceInfo() {
        return allowanceInfo;
    }

    public void setAllowanceInfo(PrlAllowDeductInfo PrlAllowDeductInfo) {
        this.allowanceInfo = PrlAllowDeductInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlPayscaleAllowanceInfo prlPayscaleAllowanceInfo = (PrlPayscaleAllowanceInfo) o;
        return Objects.equals(id, prlPayscaleAllowanceInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlPayscaleAllowanceInfo{" +
            "id=" + id +
            ", fixedBasic='" + fixedBasic + "'" +
            ", basicMinimum='" + basicMinimum + "'" +
            ", basicMaximum='" + basicMaximum + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
