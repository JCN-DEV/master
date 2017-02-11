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
 * A InstituteShopInfo.
 */
@Entity
@Table(name = "institute_shop_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instituteshopinfo")
public class InstituteShopInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name_or_number")
    private String nameOrNumber;

    @Column(name = "building_name_or_number")
    private String buildingNameOrNumber;

    @Column(name = "length")
    private String length;

    @Column(name = "width")
    private String width;

    @ManyToOne
    @JoinColumn(name = "institute_infra_info_id")
    private InstituteInfraInfo instituteInfraInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOrNumber() {
        return nameOrNumber;
    }

    public void setNameOrNumber(String nameOrNumber) {
        this.nameOrNumber = nameOrNumber;
    }

    public String getBuildingNameOrNumber() {
        return buildingNameOrNumber;
    }

    public void setBuildingNameOrNumber(String buildingNameOrNumber) {
        this.buildingNameOrNumber = buildingNameOrNumber;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
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

        InstituteShopInfo instituteShopInfo = (InstituteShopInfo) o;

        if ( ! Objects.equals(id, instituteShopInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstituteShopInfo{" +
            "id=" + id +
            ", nameOrNumber='" + nameOrNumber + "'" +
            ", buildingNameOrNumber='" + buildingNameOrNumber + "'" +
            ", length='" + length + "'" +
            ", width='" + width + "'" +
            '}';
    }
}
