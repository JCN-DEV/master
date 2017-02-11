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
 * A InstVacancyTemp.
 */
@Entity
@Table(name = "inst_vacancy_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instvacancytemp")
public class InstVacancyTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "status")
    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "emp_type")
    private EmpTypes empType;

    @Column(name = "total_vacancy")
    private Integer totalVacancy;

    @Column(name = "filled_up_vacancy")
    private String filledUpVacancy;


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
    private Institute institute;

    @ManyToOne
    private CmsTrade cmsTrade;

    @ManyToOne
    private CmsSubject cmsSubject;

    @ManyToOne
    private InstEmplDesignation instEmplDesignation;

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

    public String getFilledUpVacancy() {
        return filledUpVacancy;
    }

    public void setFilledUpVacancy(String filledUpVacancy) {
        this.filledUpVacancy = filledUpVacancy;
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

    public InstEmplDesignation getInstEmplDesignation() {
        return instEmplDesignation;
    }

    public void setInstEmplDesignation(InstEmplDesignation instEmplDesignation) {
        this.instEmplDesignation = instEmplDesignation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstVacancyTemp instVacancyTemp = (InstVacancyTemp) o;

        if ( ! Objects.equals(id, instVacancyTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstVacancyTemp{" +
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
