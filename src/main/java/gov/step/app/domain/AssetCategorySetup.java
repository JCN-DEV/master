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
 * A AssetCategorySetup.   categoryCode
 */
@Entity
@Table(name = "asset_category_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetcategorysetup")
public class AssetCategorySetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "category_name", length = 100, nullable = false)
    private String categoryName;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status")
    private Boolean status;

    @NotNull
    @Size(max = 100)
    @Column(name = "category_code", length = 100, nullable = false)
    private String categoryCode;

    @ManyToOne
    @JoinColumn(name = "asset_type_setup_id")
    private AssetTypeSetup assetTypeSetup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public AssetTypeSetup getAssetTypeSetup() {
        return assetTypeSetup;
    }

    public void setAssetTypeSetup(AssetTypeSetup assetTypeSetup) {
        this.assetTypeSetup = assetTypeSetup;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetCategorySetup assetCategorySetup = (AssetCategorySetup) o;

        if ( ! Objects.equals(id, assetCategorySetup.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetCategorySetup{" +
            "id=" + id +
            ", categoryName='" + categoryName + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
