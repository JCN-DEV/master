package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AlmEmpLeaveInitialize.
 */
@Entity
@Table(name = "alm_emp_leave_initialize")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "almempleaveinitialize")
public class AlmEmpLeaveInitialize implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "year_name")
    private Long year;

    @Column(name = "leave_earned")
    private Double leaveEarned;

    @Column(name = "leave_taken")
    private Double leaveTaken;

    @Column(name = "leave_forwarded")
    private Double leaveForwarded;

    @Column(name = "leave_on_apply")
    private Double leaveOnApply;

    @Column(name = "leave_balance")
    private Double leaveBalance;

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

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Double getLeaveEarned() {
        return leaveEarned;
    }

    public void setLeaveEarned(Double leaveEarned) {
        this.leaveEarned = leaveEarned;
    }

    public Double getLeaveTaken() {
        return leaveTaken;
    }

    public void setLeaveTaken(Double leaveTaken) {
        this.leaveTaken = leaveTaken;
    }

    public Double getLeaveForwarded() {
        return leaveForwarded;
    }

    public void setLeaveForwarded(Double leaveForwarded) {
        this.leaveForwarded = leaveForwarded;
    }

    public Double getLeaveOnApply() {
        return leaveOnApply;
    }

    public void setLeaveOnApply(Double leaveOnApply) {
        this.leaveOnApply = leaveOnApply;
    }

    public Double getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(Double leaveBalance) {
        this.leaveBalance = leaveBalance;
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

        AlmEmpLeaveInitialize almEmpLeaveInitialize = (AlmEmpLeaveInitialize) o;

        if ( ! Objects.equals(id, almEmpLeaveInitialize.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlmEmpLeaveInitialize{" +
            "id=" + id +
            ", effectiveDate='" + effectiveDate + "'" +
            ", year='" + year + "'" +
            ", leaveEarned='" + leaveEarned + "'" +
            ", leaveTaken='" + leaveTaken + "'" +
            ", leaveForwarded='" + leaveForwarded + "'" +
            ", leaveOnApply='" + leaveOnApply + "'" +
            ", leaveBalance='" + leaveBalance + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}