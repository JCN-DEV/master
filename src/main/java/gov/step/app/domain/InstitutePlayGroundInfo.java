package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstitutePlayGroundInfo.
 */
@Entity
@Table(name = "institute_play_ground_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instituteplaygroundinfo")
public class InstitutePlayGroundInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "playground_no")
    private String playgroundNo;

    @Column(name = "area")
    private String area;

    @ManyToOne
    @JoinColumn(name = "institute_infra_info_id")
    private InstituteInfraInfo instituteInfraInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaygroundNo() {
        return playgroundNo;
    }

    public void setPlaygroundNo(String playgroundNo) {
        this.playgroundNo = playgroundNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public InstituteInfraInfo getInstituteInfraInfo() {
        return instituteInfraInfo;
    }

    public void setInstituteInfraInfo(InstituteInfraInfo instituteInfraInfo) {
        this.instituteInfraInfo = instituteInfraInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstitutePlayGroundInfo institutePlayGroundInfo = (InstitutePlayGroundInfo) o;

        if ( ! Objects.equals(id, institutePlayGroundInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstitutePlayGroundInfo{" +
            "id=" + id +
            ", playgroundNo='" + playgroundNo + "'" +
            ", area='" + area + "'" +
            '}';
    }
}
