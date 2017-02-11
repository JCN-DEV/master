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
 * A CmsSubject.
 */
@Entity
@Table(name = "cms_subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cmssubject")
public class CmsSubject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;


    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "theory_cred_hr", nullable = false)
    private Integer theoryCredHr;

    @Column(name = "prac_cred_hr")
    private Integer pracCredHr;

    @Column(name = "total_cred_hr")
    private Integer totalCredHr;

    @NotNull
    @Column(name = "theory_con", nullable = false)
    private Integer theoryCon;

    @NotNull
    @Column(name = "theory_final", nullable = false)
    private Integer theoryFinal;

    @Column(name = "prac_con")
    private Integer pracCon;

    @Column(name = "prac_final")
    private Integer pracFinal;

    @Column(name = "total_marks")
    private Integer totalMarks;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "cms_curriculum_id")
    private CmsCurriculum cmsCurriculum;

    @ManyToOne
    @JoinColumn(name = "cms_syllabus_id")
    private CmsSyllabus cmsSyllabus;


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

    public Integer getTheoryCredHr() {
        return theoryCredHr;
    }

    public void setTheoryCredHr(Integer theoryCredHr) {
        this.theoryCredHr = theoryCredHr;
    }

    public Integer getPracCredHr() {
        return pracCredHr;
    }

    public void setPracCredHr(Integer pracCredHr) {
        this.pracCredHr = pracCredHr;
    }

    public Integer getTotalCredHr() {
        return totalCredHr;
    }

    public void setTotalCredHr(Integer totalCredHr) {
        this.totalCredHr = totalCredHr;
    }

    public Integer getTheoryCon() {
        return theoryCon;
    }

    public void setTheoryCon(Integer theoryCon) {
        this.theoryCon = theoryCon;
    }

    public Integer getTheoryFinal() {
        return theoryFinal;
    }

    public void setTheoryFinal(Integer theoryFinal) {
        this.theoryFinal = theoryFinal;
    }

    public Integer getPracCon() {
        return pracCon;
    }

    public void setPracCon(Integer pracCon) {
        this.pracCon = pracCon;
    }

    public Integer getPracFinal() {
        return pracFinal;
    }

    public void setPracFinal(Integer pracFinal) {
        this.pracFinal = pracFinal;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
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



    public CmsSyllabus getCmsSyllabus() {
        return cmsSyllabus;
    }

    public void setCmsSyllabus(CmsSyllabus cmsSyllabus) {
        this.cmsSyllabus = cmsSyllabus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CmsSubject cmsSubject = (CmsSubject) o;
        return Objects.equals(id, cmsSubject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CmsSubject{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", theoryCredHr='" + theoryCredHr + "'" +
            ", pracCredHr='" + pracCredHr + "'" +
            ", totalCredHr='" + totalCredHr + "'" +
            ", theoryCon='" + theoryCon + "'" +
            ", theoryFinal='" + theoryFinal + "'" +
            ", pracCon='" + pracCon + "'" +
            ", pracFinal='" + pracFinal + "'" +
            ", totalMarks='" + totalMarks + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
