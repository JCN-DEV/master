package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AlmEmpLeaveTypeMap.
 */
@Entity
@Table(name = "alm_emp_leave_type_map")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "almempleavetypemap")
public class AlmEmpLeaveTypeMap implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "current_balance")
    private Double currentBalance;

    @Column(name = "new_balance")
    private Double newBalance;

    @Column(name = "is_addition")
    private Boolean isAddition;

    @Column(name = "reason")
    private String reason;

    @Column(name = "active_status")
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
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "alm_leave_group_id")
    private AlmLeaveGroup almLeaveGroup;

    @ManyToOne
    @JoinColumn(name = "alm_leave_type_id")
    private AlmLeaveType almLeaveType;

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

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Double newBalance) {
        this.newBalance = newBalance;
    }

    public Boolean getIsAddition() {
        return isAddition;
    }

    public void setIsAddition(Boolean isAddition) {
        this.isAddition = isAddition;
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

    public AlmLeaveGroup getAlmLeaveGroup() {
        return almLeaveGroup;
    }

    public void setAlmLeaveGroup(AlmLeaveGroup AlmLeaveGroup) {
        this.almLeaveGroup = AlmLeaveGroup;
    }

    public AlmLeaveType getAlmLeaveType() {
        return almLeaveType;
    }

    public void setAlmLeaveType(AlmLeaveType AlmLeaveType) {
        this.almLeaveType = AlmLeaveType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlmEmpLeaveTypeMap almEmpLeaveTypeMap = (AlmEmpLeaveTypeMap) o;

        if ( ! Objects.equals(id, almEmpLeaveTypeMap.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlmEmpLeaveTypeMap{" +
            "id=" + id +
            ", effectiveDate='" + effectiveDate + "'" +
            ", currentBalance='" + currentBalance + "'" +
            ", newBalance='" + newBalance + "'" +
            ", isAddition='" + isAddition + "'" +
            ", reason='" + reason + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
