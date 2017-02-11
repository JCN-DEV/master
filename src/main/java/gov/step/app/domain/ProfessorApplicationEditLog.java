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
 * A ProfessorApplicationEditLog.
 */
@Entity
@Table(name = "professor_app_edit_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "professorapplicationeditlog")
public class ProfessorApplicationEditLog implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="prof_app_edit_seq")
    @SequenceGenerator(name="prof_app_edit_seq", sequenceName="prof_app_edit_seq")
    private Long id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "professor_application_id")
    private ProfessorApplication professorApplication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ProfessorApplication getProfessorApplication() {
        return professorApplication;
    }

    public void setProfessorApplication(ProfessorApplication professorApplication) {
        this.professorApplication = professorApplication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfessorApplicationEditLog professorApplicationEditLog = (ProfessorApplicationEditLog) o;

        if ( ! Objects.equals(id, professorApplicationEditLog.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProfessorApplicationEditLog{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", remarks='" + remarks + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
