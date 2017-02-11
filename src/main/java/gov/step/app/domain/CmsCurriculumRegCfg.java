package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CmsCurriculumRegCfg.
 */
@Entity
@Table(name = "cms_curriculum_reg_cfg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cmscurriculumregcfg")
public class CmsCurriculumRegCfg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "start_from", nullable = false)
    private String startFrom;

    @Column(name = "end_to")
    private String endTo;

    @Column(name = "status")
    private Boolean status;

    @OneToOne
    @JoinColumn(name = "cms_curriculum_id")
    private CmsCurriculum cmsCurriculum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(String startFrom) {
        this.startFrom = startFrom;
    }

    public String getEndTo() {
        return endTo;
    }

    public void setEndTo(String endTo) {
        this.endTo = endTo;
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

        CmsCurriculumRegCfg cmsCurriculumRegCfg = (CmsCurriculumRegCfg) o;

        if ( ! Objects.equals(id, cmsCurriculumRegCfg.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CmsCurriculumRegCfg{" +
            "id=" + id +
            ", startFrom='" + startFrom + "'" +
            ", endTo='" + endTo + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
