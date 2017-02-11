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
 * A AlmAttendanceConfiguration.
 */
@Entity
@Table(name = "alm_attendance_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "almattendanceconfiguration")
public class AlmAttendanceConfiguration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "employee_type", nullable = false)
    private String employeeType;

    @NotNull
    @Column(name = "effected_date_from", nullable = false)
    private LocalDate effectedDateFrom;

    @Column(name = "effected_date_to")
    private LocalDate effectedDateTo;

    @Column(name = "is_until_further_notice")
    private Boolean isUntilFurtherNotice;

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
    @JoinColumn(name = "alm_shift_setup_id")
    private AlmShiftSetup almShiftSetup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public LocalDate getEffectedDateFrom() {
        return effectedDateFrom;
    }

    public void setEffectedDateFrom(LocalDate effectedDateFrom) {
        this.effectedDateFrom = effectedDateFrom;
    }

    public LocalDate getEffectedDateTo() {
        return effectedDateTo;
    }

    public void setEffectedDateTo(LocalDate effectedDateTo) {
        this.effectedDateTo = effectedDateTo;
    }

    public Boolean getIsUntilFurtherNotice() {
        return isUntilFurtherNotice;
    }

    public void setIsUntilFurtherNotice(Boolean isUntilFurtherNotice) {
        this.isUntilFurtherNotice = isUntilFurtherNotice;
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

    public AlmShiftSetup getAlmShiftSetup() {
        return almShiftSetup;
    }

    public void setAlmShiftSetup(AlmShiftSetup AlmShiftSetup) {
        this.almShiftSetup = AlmShiftSetup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlmAttendanceConfiguration almAttendanceConfiguration = (AlmAttendanceConfiguration) o;

        if ( ! Objects.equals(id, almAttendanceConfiguration.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlmAttendanceConfiguration{" +
            "id=" + id +
            ", employeeType='" + employeeType + "'" +
            ", effectedDateFrom='" + effectedDateFrom + "'" +
            ", effectedDateTo='" + effectedDateTo + "'" +
            ", isUntilFurtherNotice='" + isUntilFurtherNotice + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
