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
 * A PgmsAppFamilyPension.
 */
@Entity
@Table(name = "pgms_app_family_pension")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pgmsappfamilypension")
public class PgmsAppFamilyPension implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "emp_name", nullable = false)
    private String empName;

    @NotNull
    @Column(name = "emp_department", nullable = false)
    private String empDepartment;

    @NotNull
    @Column(name = "emp_designation", nullable = false)
    private String empDesignation;

    @NotNull
    @Column(name = "emp_nid", nullable = false)
    private String empNid;

    @Column(name = "nominee_status")
    private Boolean nomineeStatus;

    @NotNull
    @Column(name = "nomine_name", nullable = false)
    private String nomineName;

    @NotNull
    @Column(name = "nomine_dob", nullable = false)
    private LocalDate nomineDob;

    @NotNull
    @Column(name = "nomine_gender", nullable = false)
    private String nomineGender;

    @NotNull
    @Column(name = "nomine_relation", nullable = false)
    private String nomineRelation;

    @NotNull
    @Column(name = "nomine_occupation", nullable = false)
    private String nomineOccupation;

    @NotNull
    @Column(name = "nomine_designation", nullable = false)
    private String nomineDesignation;

    @NotNull
    @Column(name = "nomine_pre_address", nullable = false)
    private String nominePreAddress;

    @NotNull
    @Column(name = "nomine_par_address", nullable = false)
    private String nomineParAddress;

    @NotNull
    @Column(name = "nomine_nid", nullable = false)
    private String nomineNid;

    @NotNull
    @Column(name = "nomine_cont_no", nullable = false)
    private Long nomineContNo;

    @NotNull
    @Column(name = "nomine_bank_name", nullable = false)
    private String nomineBankName;

    @NotNull
    @Column(name = "nomine_branch_name", nullable = false)
    private String nomineBranchName;

    @NotNull
    @Column(name = "nomine_acc_no", nullable = false)
    private String nomineAccNo;

    @NotNull
    @Column(name = "apply_date", nullable = false)
    private LocalDate applyDate;

    @Column(name = "aprv_status", nullable = false)
    private String aprvStatus;

    @Column(name = "aprv_date", nullable = false)
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
    @JoinColumn(name = "app_emp_id_id")
    private HrEmployeeInfo appEmpId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpDepartment() {
        return empDepartment;
    }

    public void setEmpDepartment(String empDepartment) {
        this.empDepartment = empDepartment;
    }

    public String getEmpDesignation() {
        return empDesignation;
    }

    public void setEmpDesignation(String empDesignation) {
        this.empDesignation = empDesignation;
    }

    public String getEmpNid() {
        return empNid;
    }

    public void setEmpNid(String empNid) {
        this.empNid = empNid;
    }

    public Boolean getNomineeStatus() {
        return nomineeStatus;
    }

    public void setNomineeStatus(Boolean nomineeStatus) {
        this.nomineeStatus = nomineeStatus;
    }

    public String getNomineName() {
        return nomineName;
    }

    public void setNomineName(String nomineName) {
        this.nomineName = nomineName;
    }

    public LocalDate getNomineDob() {
        return nomineDob;
    }

    public void setNomineDob(LocalDate nomineDob) {
        this.nomineDob = nomineDob;
    }

    public String getNomineGender() {
        return nomineGender;
    }

    public void setNomineGender(String nomineGender) {
        this.nomineGender = nomineGender;
    }

    public String getNomineRelation() {
        return nomineRelation;
    }

    public void setNomineRelation(String nomineRelation) {
        this.nomineRelation = nomineRelation;
    }

    public String getNomineOccupation() {
        return nomineOccupation;
    }

    public void setNomineOccupation(String nomineOccupation) {
        this.nomineOccupation = nomineOccupation;
    }

    public String getNomineDesignation() {
        return nomineDesignation;
    }

    public void setNomineDesignation(String nomineDesignation) {
        this.nomineDesignation = nomineDesignation;
    }

    public String getNominePreAddress() {
        return nominePreAddress;
    }

    public void setNominePreAddress(String nominePreAddress) {
        this.nominePreAddress = nominePreAddress;
    }

    public String getNomineParAddress() {
        return nomineParAddress;
    }

    public void setNomineParAddress(String nomineParAddress) {
        this.nomineParAddress = nomineParAddress;
    }

    public String getNomineNid() {
        return nomineNid;
    }

    public void setNomineNid(String nomineNid) {
        this.nomineNid = nomineNid;
    }

    public Long getNomineContNo() {
        return nomineContNo;
    }

    public void setNomineContNo(Long nomineContNo) {
        this.nomineContNo = nomineContNo;
    }

    public String getNomineBankName() {
        return nomineBankName;
    }

    public void setNomineBankName(String nomineBankName) {
        this.nomineBankName = nomineBankName;
    }

    public String getNomineBranchName() {
        return nomineBranchName;
    }

    public void setNomineBranchName(String nomineBranchName) {
        this.nomineBranchName = nomineBranchName;
    }

    public String getNomineAccNo() {
        return nomineAccNo;
    }

    public void setNomineAccNo(String nomineAccNo) {
        this.nomineAccNo = nomineAccNo;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
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

    public HrEmployeeInfo getAppEmpId() {
        return appEmpId;
    }

    public void setAppEmpId(HrEmployeeInfo HrEmployeeInfo) {
        this.appEmpId = HrEmployeeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgmsAppFamilyPension pgmsAppFamilyPension = (PgmsAppFamilyPension) o;

        if ( ! Objects.equals(id, pgmsAppFamilyPension.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PgmsAppFamilyPension{" +
            "id=" + id +
            ", empName='" + empName + "'" +
            ", empDepartment='" + empDepartment + "'" +
            ", empDesignation='" + empDesignation + "'" +
            ", empNid='" + empNid + "'" +
            ", nomineeStatus='" + nomineeStatus + "'" +
            ", nomineName='" + nomineName + "'" +
            ", nomineDob='" + nomineDob + "'" +
            ", nomineGender='" + nomineGender + "'" +
            ", nomineRelation='" + nomineRelation + "'" +
            ", nomineOccupation='" + nomineOccupation + "'" +
            ", nomineDesignation='" + nomineDesignation + "'" +
            ", nominePreAddress='" + nominePreAddress + "'" +
            ", nomineParAddress='" + nomineParAddress + "'" +
            ", nomineNid='" + nomineNid + "'" +
            ", nomineContNo='" + nomineContNo + "'" +
            ", nomineBankName='" + nomineBankName + "'" +
            ", nomineBranchName='" + nomineBranchName + "'" +
            ", nomineAccNo='" + nomineAccNo + "'" +
            ", applyDate='" + applyDate + "'" +
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
