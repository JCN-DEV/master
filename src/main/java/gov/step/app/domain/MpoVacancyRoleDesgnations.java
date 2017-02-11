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

/**
 * A MpoVacancyRoleDesgnations.
 */
@Entity
@Table(name = "mpo_vacancy_role_desgnations")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mpovacancyroledesgnations")
public class MpoVacancyRoleDesgnations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "crate_date")
    private LocalDate crateDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "total_post")
    private Integer totalPost;

    @ManyToOne
    private MpoVacancyRole mpoVacancyRole;

    @ManyToOne
    private User createBy;

    @ManyToOne
    private User updateBy;

//    @ManyToOne
//    private InstEmplDesignation instEmplDesignation;

    @ManyToOne
    @JoinColumn(name = "hr_designation_setup")
    private HrDesignationSetup designationSetup;

    @ManyToOne
    private CmsSubject cmsSubject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCrateDate() {
        return crateDate;
    }

    public void setCrateDate(LocalDate crateDate) {
        this.crateDate = crateDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(Integer totalPost) {
        this.totalPost = totalPost;
    }

    public MpoVacancyRole getMpoVacancyRole() {
        return mpoVacancyRole;
    }

    public void setMpoVacancyRole(MpoVacancyRole mpoVacancyRole) {
        this.mpoVacancyRole = mpoVacancyRole;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User user) {
        this.updateBy = user;
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

    public CmsSubject getCmsSubject() {
        return cmsSubject;
    }

    public void setCmsSubject(CmsSubject cmsSubject) {
        this.cmsSubject = cmsSubject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MpoVacancyRoleDesgnations mpoVacancyRoleDesgnations = (MpoVacancyRoleDesgnations) o;

        if ( ! Objects.equals(id, mpoVacancyRoleDesgnations.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MpoVacancyRoleDesgnations{" +
            "id=" + id +
            ", crateDate='" + crateDate + "'" +
            ", updateDate='" + updateDate + "'" +
            ", status='" + status + "'" +
            ", totalPost='" + totalPost + "'" +
            '}';
    }
}
