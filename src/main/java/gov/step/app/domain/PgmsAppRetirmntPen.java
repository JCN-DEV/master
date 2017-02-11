package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PgmsAppRetirmntPen.
 */
@Entity
@Table(name = "pgms_app_retirmnt_pen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pgmsappretirmntpen")
public class PgmsAppRetirmntPen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "withdrawn_type", nullable = false)
    private String withdrawnType;

    @NotNull
    @Column(name = "application_type", nullable = false)
    private String applicationType;

    @NotNull
    @Column(name = "rcv_gr_status", nullable = false)
    private Boolean rcvGrStatus;

    @Column(name = "work_duration")
    private String workDuration;

    @NotNull
    @Column(name = "emergency_contact", nullable = false)
    private Long emergencyContact;

    @NotNull
    @Column(name = "bank_acc_status", nullable = false)
    private Boolean bankAccStatus;

    @NotNull
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @NotNull
    @Column(name = "bank_acc", nullable = false)
    private String bankAcc;

    @NotNull
    @Column(name = "bank_branch", nullable = false)
    private String bankBranch;

    @NotNull
    @Column(name = "app_date", nullable = false)
    private LocalDate appDate;

    @Column(name = "app_no")
    private String appNo;

    @Column(name = "aprv_status", nullable = false)
    private String aprvStatus;

    @Column(name = "aprv_date")
    private LocalDate aprvDate;

    @Column(name = "aprv_comment")
    private String aprvComment;

    @Column(name = "aprv_by")
    private Long aprvBy;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "inst_emp_id_id")
    private HrEmployeeInfo instEmpId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWithdrawnType() {
        return withdrawnType;
    }

    public void setWithdrawnType(String withdrawnType) {
        this.withdrawnType = withdrawnType;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Boolean getRcvGrStatus() {
        return rcvGrStatus;
    }

    public void setRcvGrStatus(Boolean rcvGrStatus) {
        this.rcvGrStatus = rcvGrStatus;
    }

    public String getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(String workDuration) {
        this.workDuration = workDuration;
    }

    public Long getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(Long emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Boolean getBankAccStatus() {
        return bankAccStatus;
    }

    public void setBankAccStatus(Boolean bankAccStatus) {
        this.bankAccStatus = bankAccStatus;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public LocalDate getAppDate() {
        return appDate;
    }

    public void setAppDate(LocalDate appDate) {
        this.appDate = appDate;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getAprvStatus() {
        return aprvStatus;
    }

    public void setAprvStatus(String aprvStatus) {
        this.aprvStatus = aprvStatus;
    }

    public LocalDate getAprvDate() {
        return aprvDate;
    }

    public void setAprvDate(LocalDate aprvDate) {
        this.aprvDate = aprvDate;
    }

    public String getAprvComment() {
        return aprvComment;
    }

    public void setAprvComment(String aprvComment) {
        this.aprvComment = aprvComment;
    }

    public Long getAprvBy() {
        return aprvBy;
    }

    public void setAprvBy(Long aprvBy) {
        this.aprvBy = aprvBy;
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

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public HrEmployeeInfo getInstEmpId() {
        return instEmpId;
    }

    public void setInstEmpId(HrEmployeeInfo HrEmployeeInfo) {
        this.instEmpId = HrEmployeeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgmsAppRetirmntPen pgmsAppRetirmntPen = (PgmsAppRetirmntPen) o;

        if ( ! Objects.equals(id, pgmsAppRetirmntPen.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PgmsAppRetirmntPen{" +
            "id=" + id +
            ", withdrawnType='" + withdrawnType + "'" +
            ", applicationType='" + applicationType + "'" +
            ", rcvGrStatus='" + rcvGrStatus + "'" +
            ", workDuration='" + workDuration + "'" +
            ", emergencyContact='" + emergencyContact + "'" +
            ", bankAccStatus='" + bankAccStatus + "'" +
            ", bankName='" + bankName + "'" +
            ", bankAcc='" + bankAcc + "'" +
            ", bankBranch='" + bankBranch + "'" +
            ", appDate='" + appDate + "'" +
            ", appNo='" + appNo + "'" +
            ", aprvStatus='" + aprvStatus + "'" +
            ", aprvDate='" + aprvDate + "'" +
            ", aprvComment='" + aprvComment + "'" +
            ", aprvBy='" + aprvBy + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateBy='" + updateBy + "'" +
            ", updateDate='" + updateDate + "'" +
            '}';
    }
}
