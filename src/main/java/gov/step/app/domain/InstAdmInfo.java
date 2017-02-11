package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstAdmInfo.
 */
@Entity
@Table(name = "inst_adm_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instadminfo")
public class InstAdmInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "admin_counselor_name", nullable = false)
    private String adminCounselorName;

    @Column(name = "counselor_mobile_no")
    private String counselorMobileNo;

    @NotNull
    @Column(name = "ins_head_name", nullable = false)
    private String insHeadName;

    @NotNull
    @Column(name = "ins_head_mobile_no", nullable = false)
    private String insHeadMobileNo;

    @NotNull
    @Column(name = "deo_name", nullable = false)
    private String deoName;

    @Column(name = "deo_mobile_no")
    private String deoMobileNo;

    @Column(name = "status")
    private Integer status;

    @Column(name = "decline_remarks")
    private String declineRemarks;


    @Column(name = "create_date")
    private LocalDate dateCrated;

    @Column(name = "update_date")
    private LocalDate dateModified;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    @OneToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminCounselorName() {
        return adminCounselorName;
    }

    public void setAdminCounselorName(String adminCounselorName) {
        this.adminCounselorName = adminCounselorName;
    }

    public String getCounselorMobileNo() {
        return counselorMobileNo;
    }

    public void setCounselorMobileNo(String counselorMobileNo) {
        this.counselorMobileNo = counselorMobileNo;
    }

    public String getInsHeadName() {
        return insHeadName;
    }

    public void setInsHeadName(String insHeadName) {
        this.insHeadName = insHeadName;
    }

    public String getInsHeadMobileNo() {
        return insHeadMobileNo;
    }

    public void setInsHeadMobileNo(String insHeadMobileNo) {
        this.insHeadMobileNo = insHeadMobileNo;
    }

    public String getDeoName() {
        return deoName;
    }

    public void setDeoName(String deoName) {
        this.deoName = deoName;
    }

    public String getDeoMobileNo() {
        return deoMobileNo;
    }

    public void setDeoMobileNo(String deoMobileNo) {
        this.deoMobileNo = deoMobileNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeclineRemarks() {
        return declineRemarks;
    }

    public void setDeclineRemarks(String declineRemarks) {
        this.declineRemarks = declineRemarks;
    }

    public LocalDate getDateCrated() {
        return dateCrated;
    }

    public void setDateCrated(LocalDate dateCrated) {
        this.dateCrated = dateCrated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstAdmInfo instAdmInfo = (InstAdmInfo) o;

        if ( ! Objects.equals(id, instAdmInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstAdmInfo{" +
            "id=" + id +
            ", adminCounselorName='" + adminCounselorName + "'" +
            ", counselorMobileNo='" + counselorMobileNo + "'" +
            ", insHeadName='" + insHeadName + "'" +
            ", insHeadMobileNo='" + insHeadMobileNo + "'" +
            ", deoName='" + deoName + "'" +
            ", deoMobileNo='" + deoMobileNo + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
