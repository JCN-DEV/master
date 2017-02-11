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
 * A InstituteInfraInfo.
 */
@Entity
@Table(name = "institute_infra_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instituteinfrainfo")
public class InstituteInfraInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private Integer status;

    @OneToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @OneToOne
    @JoinColumn(name = "inst_building_id")
    private InstituteBuilding instBuilding;

    @OneToOne
    @JoinColumn(name = "inst_land_id")
    private InstituteLand instLand;

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

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public InstituteBuilding getInstBuilding() {
        return instBuilding;
    }

    public void setInstBuilding(InstituteBuilding instBuilding) {
        this.instBuilding = instBuilding;
    }

    public InstituteLand getInstLand() {
        return instLand;
    }

    public void setInstLand(InstituteLand instLand) {
        this.instLand = instLand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstituteInfraInfo instituteInfraInfo = (InstituteInfraInfo) o;

        if ( ! Objects.equals(id, instituteInfraInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstituteInfraInfo{" +
            "id=" + id +
            ", status='" + status + "'" +
            '}';
    }
}
