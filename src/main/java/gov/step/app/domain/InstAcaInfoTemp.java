package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.Curriculum;

/**
 * A InstAcaInfoTemp.
 */
@Entity
@Table(name = "inst_aca_info_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instacainfotemp")
public class InstAcaInfoTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "academic_counselor_name")
    private String academicCounselorName;

    @Column(name = "mobile")
    private String Mobile;

    @Enumerated(EnumType.STRING)
    @Column(name = "curriculum")
    private Curriculum curriculum;

    @Column(name = "total_trade_tech_no")
    private Integer totalTradeTechNo;

    @Column(name = "trade_tech_details")
    private String tradeTechDetails;


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
    private Institute institute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcademicCounselorName() {
        return academicCounselorName;
    }

    public void setAcademicCounselorName(String academicCounselorName) {
        this.academicCounselorName = academicCounselorName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public Integer getTotalTradeTechNo() {
        return totalTradeTechNo;
    }

    public void setTotalTradeTechNo(Integer totalTradeTechNo) {
        this.totalTradeTechNo = totalTradeTechNo;
    }

    public String getTradeTechDetails() {
        return tradeTechDetails;
    }

    public void setTradeTechDetails(String tradeTechDetails) {
        this.tradeTechDetails = tradeTechDetails;
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

        InstAcaInfoTemp instAcaInfoTemp = (InstAcaInfoTemp) o;

        if ( ! Objects.equals(id, instAcaInfoTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstAcaInfoTemp{" +
            "id=" + id +
            ", academicCounselorName='" + academicCounselorName + "'" +
            ", Mobile='" + Mobile + "'" +
            ", curriculum='" + curriculum + "'" +
            ", totalTradeTechNo='" + totalTradeTechNo + "'" +
            ", tradeTechDetails='" + tradeTechDetails + "'" +
            '}';
    }
}
