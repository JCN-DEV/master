package gov.step.app.domain;

import gov.step.app.domain.enumeration.cancelStatuses;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AlmEmpLeaveCancellation.
 */
@Entity
@Table(name = "alm_emp_leave_cancellation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "almempleavecancellation")
public class AlmEmpLeaveCancellation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @NotNull
    @Column(name = "request_type", nullable = false)
    private String requestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "cancel_status")
    private cancelStatuses cancelStatus;

    @NotNull
    @Column(name = "cause_of_cancellation", nullable = false)
    private String causeOfCancellation;

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

    @ManyToOne
    @JoinColumn(name = "approved_by_id")
    private HrEmployeeInfo approvedBy;

    @ManyToOne
    @JoinColumn(name = "alm_emp_leave_appl_id")
    private AlmEmpLeaveApplication almEmpLeaveApplication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public cancelStatuses getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(cancelStatuses cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getCauseOfCancellation() {
        return causeOfCancellation;
    }

    public void setCauseOfCancellation(String causeOfCancellation) {
        this.causeOfCancellation = causeOfCancellation;
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

    public HrEmployeeInfo getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(HrEmployeeInfo HrEmployeeInfo) {
        this.approvedBy = HrEmployeeInfo;
    }

    public AlmEmpLeaveApplication getAlmEmpLeaveApplication() {
        return almEmpLeaveApplication;
    }

    public void setAlmEmpLeaveApplication(AlmEmpLeaveApplication AlmEmpLeaveApplication) {
        this.almEmpLeaveApplication = AlmEmpLeaveApplication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlmEmpLeaveCancellation almEmpLeaveCancellation = (AlmEmpLeaveCancellation) o;

        if ( ! Objects.equals(id, almEmpLeaveCancellation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlmEmpLeaveCancellation{" +
            "id=" + id +
            ", requestDate='" + requestDate + "'" +
            ", requestType='" + requestType + "'" +
            ", cancelStatus='" + cancelStatus + "'" +
            ", causeOfCancellation='" + causeOfCancellation + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
