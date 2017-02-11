package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.EmpTypes;

/**
 * A InstVacancy.
 */
@Entity
@Table(name = "inst_vacancy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instvacancy")
public class InstVacancy implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="inst_vac_seq")
    @SequenceGenerator(name="inst_vac_seq", sequenceName="inst_vac_seq")
    private Long id;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    //status 0 (inactive), status 1 (active)
    @Column(name = "status")
    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "emp_type")
    private EmpTypes empType;

    @Column(name = "total_vacancy")
    private Integer totalVacancy;

    @Column(name = "filled_up_vacancy")
    private Integer filledUpVacancy;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ManyToOne
    @JoinColumn(name = "cms_trade_id")
    private CmsTrade cmsTrade;

    @ManyToOne
    @JoinColumn(name = "cms_subject_id")
    private CmsSubject cmsSubject;

//    @ManyToOne
//    @JoinColumn(name = "empl_designation_id")
//    private InstEmplDesignation instEmplDesignation;

    @ManyToOne
    @JoinColumn(name = "hr_designation_setup")
    private HrDesignationSetup designationSetup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public EmpTypes getEmpType() {
        return empType;
    }

    public void setEmpType(EmpTypes empType) {
        this.empType = empType;
    }

    public Integer getTotalVacancy() {
        return totalVacancy;
    }

    public void setTotalVacancy(Integer totalVacancy) {
        this.totalVacancy = totalVacancy;
    }

    public Integer getFilledUpVacancy() {
        return filledUpVacancy;
    }

    public void setFilledUpVacancy(Integer filledUpVacancy) {
        this.filledUpVacancy = filledUpVacancy;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public CmsTrade getCmsTrade() {
        return cmsTrade;
    }

    public void setCmsTrade(CmsTrade cmsTrade) {
        this.cmsTrade = cmsTrade;
    }

    public CmsSubject getCmsSubject() {
        return cmsSubject;
    }

    public void setCmsSubject(CmsSubject cmsSubject) {
        this.cmsSubject = cmsSubject;
    }

//    public InstEmplDesignation getInstEmplDesignation() {
//        return instEmplDesignation;
//    }
//
//    public void setInstEmplDesignation(InstEmplDesignation instEmplDesignation) {
//        this.instEmplDesignation = instEmplDesignation;
//    }

    public HrDesignationSetup getDesignationSetup() {
        return designationSetup;
    }

    public void setDesignationSetup(HrDesignationSetup designationSetup) {
        this.designationSetup = designationSetup;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstVacancy instVacancy = (InstVacancy) o;

        if ( ! Objects.equals(id, instVacancy.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstVacancy{" +
            "id=" + id +
            ", dateCreated='" + dateCreated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", status='" + status + "'" +
            ", empType='" + empType + "'" +
            ", totalVacancy='" + totalVacancy + "'" +
            ", filledUpVacancy='" + filledUpVacancy + "'" +
            '}';
    }
}
