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
 * A CmsSubAssign.
 */
@Entity
@Table(name = "cms_sub_assign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cmssubassign")
public class CmsSubAssign implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "exam_fee", nullable = false)
    private Integer examFee;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="cms_curriculum_id")

    private CmsCurriculum cmsCurriculum;

    @ManyToOne
    @JoinColumn(name="cms_trade_id")
    private CmsTrade cmsTrade;

    @ManyToOne
    @JoinColumn(name="cms_semester_id")

    private CmsSemester cmsSemester;

    @ManyToOne
    @JoinColumn(name="cms_syllabus_id")

    private CmsSyllabus cmsSyllabus;

    @ManyToOne
    @JoinColumn(name="cms_subject_id")

    private CmsSubject cmsSubject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExamFee() {
        return examFee;
    }

    public void setExamFee(Integer examFee) {
        this.examFee = examFee;
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

    public CmsTrade getCmsTrade() {
        return cmsTrade;
    }

    public void setCmsTrade(CmsTrade cmsTrade) {
        this.cmsTrade = cmsTrade;
    }

    public CmsSemester getCmsSemester() {
        return cmsSemester;
    }

    public void setCmsSemester(CmsSemester cmsSemester) {
        this.cmsSemester = cmsSemester;
    }

    public CmsSyllabus getCmsSyllabus() {
        return cmsSyllabus;
    }


    public void setCmsSyllabus(CmsSyllabus cmsSyllabus) {
        this.cmsSyllabus = cmsSyllabus;
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

        CmsSubAssign cmsSubAssign = (CmsSubAssign) o;

        if ( ! Objects.equals(id, cmsSubAssign.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CmsSubAssign{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", examFee='" + examFee + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
