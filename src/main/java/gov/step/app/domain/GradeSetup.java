package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import gov.step.app.domain.enumeration.gradeClass;

import gov.step.app.domain.enumeration.gradeType;

/**
 * A GradeSetup.
 */
@Entity
@Table(name = "grade_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "gradesetup")
public class GradeSetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "no", nullable = false)
    private Integer no;

    @Column(name = "details")
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_class")
    private gradeClass gradeClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private gradeType type;

    @Column(name = "status")
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public gradeClass getGradeClass() {
        return gradeClass;
    }

    public void setGradeClass(gradeClass gradeClass) {
        this.gradeClass = gradeClass;
    }

    public gradeType getType() {
        return type;
    }

    public void setType(gradeType type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GradeSetup gradeSetup = (GradeSetup) o;

        if ( ! Objects.equals(id, gradeSetup.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GradeSetup{" +
            "id=" + id +
            ", no='" + no + "'" +
            ", details='" + details + "'" +
            ", gradeClass='" + gradeClass + "'" +
            ", type='" + type + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
