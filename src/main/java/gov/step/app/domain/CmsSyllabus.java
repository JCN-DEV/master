package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CmsSyllabus.
 */
@Entity
@Table(name = "cms_syllabus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cmssyllabus")
public class CmsSyllabus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "syllabus")
    private byte[] syllabus;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Column(name = "syllabus_content_type")
    private String syllabusContentType;
    @Size(max = 8000)

    @Column(name = "syllabus_content")
    private String syllabusContent;


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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  byte[] getSyllabus() {
        return syllabus;
    }

    public void setSyllabus( byte[] syllabus) {
        this.syllabus = syllabus;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSyllabusContentType() {
        return syllabusContentType;
    }

    public void setSyllabusContentType(String syllabusContentType) {
        this.syllabusContentType = syllabusContentType;
    }

    public String getSyllabusContent() {
        return syllabusContent;
    }

    public void setSyllabusContent(String syllabusContent) {
        this.syllabusContent = syllabusContent;
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
        CmsSyllabus cmsSyllabus = (CmsSyllabus) o;
        return Objects.equals(id, cmsSyllabus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CmsSyllabus{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", syllabus='" + syllabus + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
