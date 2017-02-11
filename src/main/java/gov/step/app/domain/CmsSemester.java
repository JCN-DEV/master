package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CmsSemester.
 */
@Entity
@Table(name = "cms_semester")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cmssemester")
public class CmsSemester implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*    @NotNull*/
    @Column(name = "code", nullable = true)
    private String code;

    /* @NotNull*/
    @Column(name = "name", nullable = true)
    private String name;

    @NotNull
    @Column(name = "year", nullable = false)
    private String year;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "cms_curriculum_id")
    private CmsCurriculum cmsCurriculum;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public CmsCurriculum getCmsCurriculum() {
        return cmsCurriculum;
    }

    public void setCmsCurriculum(CmsCurriculum cmsCurriculum) {
        this.cmsCurriculum = cmsCurriculum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CmsSemester cmsSemester = (CmsSemester) o;
        return Objects.equals(id, cmsSemester.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CmsSemester{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", year='" + year + "'" +
            ", duration='" + duration + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
