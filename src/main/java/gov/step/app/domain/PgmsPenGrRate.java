package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PgmsPenGrRate.
 */
@Entity
@Table(name = "pgms_pen_gr_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pgmspengrrate")
public class PgmsPenGrRate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "pen_gr_set_id", nullable = false)
    private Long penGrSetId;

    @NotNull
    @Column(name = "working_year", nullable = false)
    private Long workingYear;

    @NotNull
    @Column(name = "rate_of_pen_gr", nullable = false)
    private Long rateOfPenGr;

    @Column(name = "active_status")
    private Boolean activeStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPenGrSetId() {
        return penGrSetId;
    }

    public void setPenGrSetId(Long penGrSetId) {
        this.penGrSetId = penGrSetId;
    }

    public Long getWorkingYear() {
        return workingYear;
    }

    public void setWorkingYear(Long workingYear) {
        this.workingYear = workingYear;
    }

    public Long getRateOfPenGr() {
        return rateOfPenGr;
    }

    public void setRateOfPenGr(Long rateOfPenGr) {
        this.rateOfPenGr = rateOfPenGr;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgmsPenGrRate pgmsPenGrRate = (PgmsPenGrRate) o;

        if ( ! Objects.equals(id, pgmsPenGrRate.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PgmsPenGrRate{" +
            "id=" + id +
            ", penGrSetId='" + penGrSetId + "'" +
            ", workingYear='" + workingYear + "'" +
            ", rateOfPenGr='" + rateOfPenGr + "'" +
            ", activeStatus='" + activeStatus + "'" +
            '}';
    }
}
