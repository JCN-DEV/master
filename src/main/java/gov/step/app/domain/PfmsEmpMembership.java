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
 * A PfmsEmpMembership.
 */
@Entity
@Table(name = "pfms_emp_membership")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pfmsempmembership")
public class PfmsEmpMembership implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "init_own_contribute", nullable = false)
    private Double initOwnContribute;

    @NotNull
    @Column(name = "init_own_contribute_int", nullable = false)
    private Double initOwnContributeInt;

    @NotNull
    @Column(name = "cur_own_contribute", nullable = false)
    private Double curOwnContribute;

    @NotNull
    @Column(name = "cur_own_contribute_int", nullable = false)
    private Double curOwnContributeInt;

    @NotNull
    @Column(name = "cur_own_contribute_tot", nullable = false)
    private Double curOwnContributeTot;

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

    public Double getInitOwnContribute() {
        return initOwnContribute;
    }

    public void setInitOwnContribute(Double initOwnContribute) {
        this.initOwnContribute = initOwnContribute;
    }

    public Double getInitOwnContributeInt() {
        return initOwnContributeInt;
    }

    public void setInitOwnContributeInt(Double initOwnContributeInt) {
        this.initOwnContributeInt = initOwnContributeInt;
    }

    public Double getCurOwnContribute() {
        return curOwnContribute;
    }

    public void setCurOwnContribute(Double curOwnContribute) {
        this.curOwnContribute = curOwnContribute;
    }

    public Double getCurOwnContributeInt() {
        return curOwnContributeInt;
    }

    public void setCurOwnContributeInt(Double curOwnContributeInt) {
        this.curOwnContributeInt = curOwnContributeInt;
    }

    public Double getCurOwnContributeTot() {
        return curOwnContributeTot;
    }

    public void setCurOwnContributeTot(Double curOwnContributeTot) {
        this.curOwnContributeTot = curOwnContributeTot;
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

        PfmsEmpMembership pfmsEmpMembership = (PfmsEmpMembership) o;

        if ( ! Objects.equals(id, pfmsEmpMembership.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PfmsEmpMembership{" +
            "id=" + id +
            ", initOwnContribute='" + initOwnContribute + "'" +
            ", initOwnContributeInt='" + initOwnContributeInt + "'" +
            ", curOwnContribute='" + curOwnContribute + "'" +
            ", curOwnContributeInt='" + curOwnContributeInt + "'" +
            ", curOwnContributeTot='" + curOwnContributeTot + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
