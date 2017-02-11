package gov.step.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.VacancyRoleType;

/**
 * A MpoVacancyRole.
 */
@Entity
@Table(name = "mpo_vacancy_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mpovacancyrole")
public class MpoVacancyRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "crate_date")
    private LocalDate crateDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "total_trade")
    private Integer totalTrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "vacancy_role_type")
    private VacancyRoleType vacancyRoleType;

    @Column(name = "total_vacancy")
    private Integer totalVacancy;

    @ManyToOne
    private CmsCurriculum cmsCurriculum;

    @ManyToOne
    private User createBy;

    @ManyToOne
    private User updateBy;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "mpoVacancyRole")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MpoVacancyRoleTrade> mpoVacancyRoleTrades = new HashSet<>();

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

    public Integer getTotalTrade() {
        return totalTrade;
    }

    public void setTotalTrade(Integer totalTrade) {
        this.totalTrade = totalTrade;
    }

    public VacancyRoleType getVacancyRoleType() {
        return vacancyRoleType;
    }

    public void setVacancyRoleType(VacancyRoleType vacancyRoleType) {
        this.vacancyRoleType = vacancyRoleType;
    }

    public Integer getTotalVacancy() {
        return totalVacancy;
    }

    public void setTotalVacancy(Integer totalVacancy) {
        this.totalVacancy = totalVacancy;
    }

    public CmsCurriculum getCmsCurriculum() {
        return cmsCurriculum;
    }

    public void setCmsCurriculum(CmsCurriculum cmsCurriculum) {
        this.cmsCurriculum = cmsCurriculum;
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

    public Set<MpoVacancyRoleTrade> getMpoVacancyRoleTrades() {
        return mpoVacancyRoleTrades;
    }

    public void setMpoVacancyRoleTrades(Set<MpoVacancyRoleTrade> mpoVacancyRoleTrades) {
        this.mpoVacancyRoleTrades = mpoVacancyRoleTrades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MpoVacancyRole mpoVacancyRole = (MpoVacancyRole) o;

        if ( ! Objects.equals(id, mpoVacancyRole.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MpoVacancyRole{" +
            "id=" + id +
            ", crateDate='" + crateDate + "'" +
            ", updateDate='" + updateDate + "'" +
            ", status='" + status + "'" +
            ", totalTrade='" + totalTrade + "'" +
            ", vacancyRoleType='" + vacancyRoleType + "'" +
            ", totalVacancy='" + totalVacancy + "'" +
            '}';
    }
}
