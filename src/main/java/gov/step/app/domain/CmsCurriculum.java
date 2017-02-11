package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CmsCurriculum.
 */
@Entity
@Table(name = "cms_curriculum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cmscurriculum")
public class CmsCurriculum implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration_type", nullable = false)
    private String duration_type;

    @Column(name = "acronym", nullable = false)
    private String acronym;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "entry_qualification")
    private String entryQualification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDuration_type() {
        return duration_type;
    }

    public void setDuration_type(String duration_type) {
        this.duration_type = duration_type;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getEntryQualification() {
        return entryQualification;
    }

    public void setEntryQualification(String entryQualification) {
        this.entryQualification = entryQualification;
    }

    @Override
    public boolean equals(Object f) {
        if (this == f) {
            return true;
        }
        if (f == null || getClass() != f.getClass()) {
            return false;
        }

        CmsCurriculum cmsCurriculum = (CmsCurriculum) f;
        return Objects.equals(id, cmsCurriculum.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CmsCurriculum{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", duration='" + duration + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
