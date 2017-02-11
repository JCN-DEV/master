package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.Curriculum;
//TODO: THIS ENTITY IS NOT USING NOW. WE SHOULD REMOVE IT.
/**
 * A InsAcademicInfo.
 */
@Entity
@Table(name = "ins_academic_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "insacademicinfo")
public class InsAcademicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "counselor_name")
    private String counselorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "curriculum")
    private Curriculum curriculum;

    @Column(name = "total_tech_trade_no")
    private Integer totalTechTradeNo;

    @Column(name = "trade_tech_details")
    private String tradeTechDetails;

    @Column(name = "total_student")
    private Integer totalStudent;

    @Column(name = "status")
    private Integer status;

    @Column(name = "decline_remarks")
    private String declineRemarks;

    @OneToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public Integer getTotalTechTradeNo() {
        return totalTechTradeNo;
    }

    public void setTotalTechTradeNo(Integer totalTechTradeNo) {
        this.totalTechTradeNo = totalTechTradeNo;
    }

    public String getTradeTechDetails() {
        return tradeTechDetails;
    }

    public void setTradeTechDetails(String tradeTechDetails) {
        this.tradeTechDetails = tradeTechDetails;
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

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Integer getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(Integer totalStudent) {
        this.totalStudent = totalStudent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InsAcademicInfo insAcademicInfo = (InsAcademicInfo) o;

        if ( ! Objects.equals(id, insAcademicInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InsAcademicInfo{" +
            "id=" + id +
            ", counselorName='" + counselorName + "'" +
            ", curriculum='" + curriculum + "'" +
            ", totalTechTradeNo='" + totalTechTradeNo + "'" +
            ", tradeTechDetails='" + tradeTechDetails + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
