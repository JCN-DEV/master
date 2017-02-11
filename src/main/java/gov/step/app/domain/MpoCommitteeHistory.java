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
 * A MpoCommitteeHistory.
 */
@Entity
@Table(name = "mpo_committee_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mpocommitteehistory")
public class MpoCommitteeHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "month", nullable = false)
    private Integer month;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCrated;

    @Column(name = "date_modified", nullable = false)
    private LocalDate dateModified;

    @Column(name = "status")
    private Integer status;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "MPO_CMT_PRSN_INF_ID")
    private MpoCommitteePersonInfo mpoCommitteePersonInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public MpoCommitteePersonInfo getMpoCommitteePersonInfo() {
        return mpoCommitteePersonInfo;
    }

    public void setMpoCommitteePersonInfo(MpoCommitteePersonInfo mpoCommitteePersonInfo) {
        this.mpoCommitteePersonInfo = mpoCommitteePersonInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MpoCommitteeHistory mpoCommitteeHistory = (MpoCommitteeHistory) o;

        if ( ! Objects.equals(id, mpoCommitteeHistory.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MpoCommitteeHistory{" +
            "id=" + id +
            ", month='" + month + "'" +
            ", year='" + year + "'" +
            ", dateCrated='" + dateCrated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
