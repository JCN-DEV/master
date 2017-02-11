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
 * A AlmAttendanceInformation.
 */
@Entity
@Table(name = "alm_attendance_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "almattendanceinformation")
public class AlmAttendanceInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "office_in", nullable = false)
    private LocalDate officeIn;

    @Column(name = "office_out", nullable = false)
    private LocalDate officeOut;

    @Column(name = "punch_in", nullable = false)
    private LocalDate punchIn;

    @Column(name = "punch_in_note")
    private String punchInNote;

    @Column(name = "punch_out", nullable = false)
    private LocalDate punchOut;

    @Column(name = "punch_out_note")
    private String punchOutNote;

    @Column(name = "process_date", nullable = false)
    private LocalDate processDate;

    @Column(name = "is_processed")
    private Boolean isProcessed;

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
    @JoinColumn(name = "alm_attendance_conf_id")
    private AlmAttendanceConfiguration almAttendanceConfiguration;

    @ManyToOne
    @JoinColumn(name = "alm_shift_setup_id")
    private AlmShiftSetup almShiftSetup;

    @ManyToOne
    @JoinColumn(name = "alm_attendance_status_id")
    private AlmAttendanceStatus almAttendanceStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOfficeIn() {
        return officeIn;
    }

    public void setOfficeIn(LocalDate officeIn) {
        this.officeIn = officeIn;
    }

    public LocalDate getOfficeOut() {
        return officeOut;
    }

    public void setOfficeOut(LocalDate officeOut) {
        this.officeOut = officeOut;
    }

    public LocalDate getPunchIn() {
        return punchIn;
    }

    public void setPunchIn(LocalDate punchIn) {
        this.punchIn = punchIn;
    }

    public String getPunchInNote() {
        return punchInNote;
    }

    public void setPunchInNote(String punchInNote) {
        this.punchInNote = punchInNote;
    }

    public LocalDate getPunchOut() {
        return punchOut;
    }

    public void setPunchOut(LocalDate punchOut) {
        this.punchOut = punchOut;
    }

    public String getPunchOutNote() {
        return punchOutNote;
    }

    public void setPunchOutNote(String punchOutNote) {
        this.punchOutNote = punchOutNote;
    }

    public LocalDate getProcessDate() {
        return processDate;
    }

    public void setProcessDate(LocalDate processDate) {
        this.processDate = processDate;
    }

    public Boolean getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
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

    public AlmAttendanceConfiguration getAlmAttendanceConfiguration() {
        return almAttendanceConfiguration;
    }

    public void setAlmAttendanceConfiguration(AlmAttendanceConfiguration AlmAttendanceConfiguration) {
        this.almAttendanceConfiguration = AlmAttendanceConfiguration;
    }

    public AlmShiftSetup getAlmShiftSetup() {
        return almShiftSetup;
    }

    public void setAlmShiftSetup(AlmShiftSetup AlmShiftSetup) {
        this.almShiftSetup = AlmShiftSetup;
    }

    public AlmAttendanceStatus getAlmAttendanceStatus() {
        return almAttendanceStatus;
    }

    public void setAlmAttendanceStatus(AlmAttendanceStatus AlmAttendanceStatus) {
        this.almAttendanceStatus = AlmAttendanceStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlmAttendanceInformation almAttendanceInformation = (AlmAttendanceInformation) o;

        if ( ! Objects.equals(id, almAttendanceInformation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlmAttendanceInformation{" +
            "id=" + id +
            ", officeIn='" + officeIn + "'" +
            ", officeOut='" + officeOut + "'" +
            ", punchIn='" + punchIn + "'" +
            ", punchInNote='" + punchInNote + "'" +
            ", punchOut='" + punchOut + "'" +
            ", punchOutNote='" + punchOutNote + "'" +
            ", processDate='" + processDate + "'" +
            ", isProcessed='" + isProcessed + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
