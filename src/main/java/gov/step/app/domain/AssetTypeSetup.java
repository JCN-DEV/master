package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AssetTypeSetup.
 */
@Entity
@Table(name = "asset_type_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assettypesetup")
public class AssetTypeSetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "type_name", length = 100, nullable = false)
    private String typeName;

    /*@NotNull
    @Column(name = "description", nullable = false)
    private String description;*/

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;


    @NotNull
    @Size(max = 100)
    @Column(name = "type_code", length = 100, nullable = false)
    private String typeCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetTypeSetup assetTypeSetup = (AssetTypeSetup) o;

        if ( ! Objects.equals(id, assetTypeSetup.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetTypeSetup{" +
            "id=" + id +
            ", typeName='" + typeName + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
