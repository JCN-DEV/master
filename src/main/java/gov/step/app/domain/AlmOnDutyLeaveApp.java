package gov.step.app.domain;

import gov.step.app.domain.enumeration.applicationLeaveStatuses;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AlmOnDutyLeaveApp.
 */
@Entity
@Table(name = "alm_on_duty_leave_app")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "almondutyleaveapp")
public class AlmOnDutyLeaveApp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @NotNull
    @Column(name = "duty_date", nullable = false)
    private LocalDate dutyDate;

    @NotNull
    @Column(name = "duty_in_time_hour", nullable = false)
    private String dutyInTimeHour;

    @NotNull
    @Column(name = "duty_in_time_min", nullable = false)
    private String dutyInTimeMin;

    @NotNull
    @Column(name = "duty_out_time_hour", nullable = false)
    private String dutyOutTimeHour;

    @NotNull
    @Column(name = "duty_out_time_min", nullable = false)
    private String dutyOutTimeMin;

    @NotNull
    @Column(name = "is_more_day", nullable = false)
    private Boolean isMoreDay;

    @Column(name = "end_duty_date")
    private LocalDate endDutyDate;

    @Column(name = "reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_leave_status")
    private applicationLeaveStatuses applicationLeaveStatus;

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
    @JoinColumn(name = "alm_duty_side_id")
    private AlmDutySide almDutySide;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public LocalDate getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(LocalDate dutyDate) {
        this.dutyDate = dutyDate;
    }

    public applicationLeaveStatuses getApplicationLeaveStatus() {
        return applicationLeaveStatus;
    }

    public void setApplicationLeaveStatus(applicationLeaveStatuses applicationLeaveStatus) {
        this.applicationLeaveStatus = applicationLeaveStatus;
    }

    public String getDutyInTimeHour() {
        return dutyInTimeHour;
    }

    public void setDutyInTimeHour(String dutyInTimeHour) {
        this.dutyInTimeHour = dutyInTimeHour;
    }

    public String getDutyInTimeMin() {
        return dutyInTimeMin;
    }

    public void setDutyInTimeMin(String dutyInTimeMin) {
        this.dutyInTimeMin = dutyInTimeMin;
    }

    public String getDutyOutTimeHour() {
        return dutyOutTimeHour;
    }

    public void setDutyOutTimeHour(String dutyOutTimeHour) {
        this.dutyOutTimeHour = dutyOutTimeHour;
    }

    public String getDutyOutTimeMin() {
        return dutyOutTimeMin;
    }

    public void setDutyOutTimeMin(String dutyOutTimeMin) {
        this.dutyOutTimeMin = dutyOutTimeMin;
    }

    public LocalDate getEndDutyDate() {
        return endDutyDate;
    }

    public void setEndDutyDate(LocalDate endDutyDate) {
        this.endDutyDate = endDutyDate;
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
    public Boolean getIsMoreDay() {
        return isMoreDay;
    }

    public void setIsMoreDay(Boolean isMoreDay) {
        this.isMoreDay = isMoreDay;
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

    public AlmDutySide getAlmDutySide() {
        return almDutySide;
    }

    public void setAlmDutySide(AlmDutySide AlmDutySide) {
        this.almDutySide = AlmDutySide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlmOnDutyLeaveApp almOnDutyLeaveApp = (AlmOnDutyLeaveApp) o;

        if ( ! Objects.equals(id, almOnDutyLeaveApp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlmOnDutyLeaveApp{" +
            "id=" + id +
            ", applicationDate='" + applicationDate + "'" +
            ", dutyDate='" + dutyDate + "'" +
            ", dutyInTimeHour='" + dutyInTimeHour + "'" +
            ", dutyInTimeMin='" + dutyInTimeMin + "'" +
            ", dutyOutTimeHour='" + dutyOutTimeHour + "'" +
            ", dutyOutTimeMin='" + dutyOutTimeMin + "'" +
            ", isMoreDay='" + isMoreDay + "'" +
            ", endDutyDate='" + endDutyDate + "'" +
            ", reason='" + reason + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
