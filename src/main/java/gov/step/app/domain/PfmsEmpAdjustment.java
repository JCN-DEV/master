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
 * A PfmsEmpAdjustment.
 */
@Entity
@Table(name = "pfms_emp_adjustment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pfmsempadjustment")
public class PfmsEmpAdjustment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "is_current_balance", nullable = false)
    private Boolean isCurrentBalance;

    @NotNull
    @Column(name = "own_contribute", nullable = false)
    private Double ownContribute;

    @NotNull
    @Column(name = "own_contribute_int", nullable = false)
    private Double ownContributeInt;

    @NotNull
    @Column(name = "pre_own_contribute", nullable = false)
    private Double preOwnContribute;

    @NotNull
    @Column(name = "pre_own_contribute_int", nullable = false)
    private Double preOwnContributeInt;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCurrentBalance() {
        return isCurrentBalance;
    }

    public void setIsCurrentBalance(Boolean isCurrentBalance) {
        this.isCurrentBalance = isCurrentBalance;
    }

    public Double getOwnContribute() {
        return ownContribute;
    }

    public void setOwnContribute(Double ownContribute) {
        this.ownContribute = ownContribute;
    }

    public Double getOwnContributeInt() {
        return ownContributeInt;
    }

    public void setOwnContributeInt(Double ownContributeInt) {
        this.ownContributeInt = ownContributeInt;
    }

    public Double getPreOwnContribute() {
        return preOwnContribute;
    }

    public void setPreOwnContribute(Double preOwnContribute) {
        this.preOwnContribute = preOwnContribute;
    }

    public Double getPreOwnContributeInt() {
        return preOwnContributeInt;
    }

    public void setPreOwnContributeInt(Double preOwnContributeInt) {
        this.preOwnContributeInt = preOwnContributeInt;
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

        PfmsEmpAdjustment pfmsEmpAdjustment = (PfmsEmpAdjustment) o;

        if ( ! Objects.equals(id, pfmsEmpAdjustment.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PfmsEmpAdjustment{" +
            "id=" + id +
            ", isCurrentBalance='" + isCurrentBalance + "'" +
            ", ownContribute='" + ownContribute + "'" +
            ", ownContributeInt='" + ownContributeInt + "'" +
            ", preOwnContribute='" + preOwnContribute + "'" +
            ", preOwnContributeInt='" + preOwnContributeInt + "'" +
            ", reason='" + reason + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
