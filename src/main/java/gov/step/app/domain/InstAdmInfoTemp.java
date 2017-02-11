package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstAdmInfoTemp.
 */
@Entity
@Table(name = "inst_adm_info_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instadminfotemp")
public class InstAdmInfoTemp implements Serializable {

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

    //status 0= pending, 1= rejected, 2 = approved
    @Column(name = "status")
    private Integer status;


    @OneToOne
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

        InstAdmInfoTemp instAdmInfoTemp = (InstAdmInfoTemp) o;

        if ( ! Objects.equals(id, instAdmInfoTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstAdmInfoTemp{" +
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
