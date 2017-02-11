package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AssetDestroy.
 */
@Entity
@Table(name = "asset_destroy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetdestroy")
public class AssetDestroy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "transfer_reference", nullable = false)
    private String transferReference;

    @Column(name = "destroy_date", nullable = false)
    private LocalDate destroyDate;

    @Column(name = "used_period")
    private Integer usedPeriod;

    @Column(name = "cause_of_date", nullable = false)
    private LocalDate causeOfDate;

    @OneToOne
    @JoinColumn(name = "asset_distribution_id")
    private AssetDistribution assetDistribution;

    @ManyToOne
    private AssetRecord assetCode;

    public AssetRecord getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(AssetRecord assetCode) {
        this.assetCode = assetCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransferReference() {
        return transferReference;
    }

    public void setTransferReference(String transferReference) {
        this.transferReference = transferReference;
    }

    public LocalDate getDestroyDate() {
        return destroyDate;
    }

    public void setDestroyDate(LocalDate destroyDate) {
        this.destroyDate = destroyDate;
    }

    public Integer getUsedPeriod() {
        return usedPeriod;
    }

    public void setUsedPeriod(Integer usedPeriod) {
        this.usedPeriod = usedPeriod;
    }

    public LocalDate getCauseOfDate() {
        return causeOfDate;
    }

    public void setCauseOfDate(LocalDate causeOfDate) {
        this.causeOfDate = causeOfDate;
    }

    public AssetDistribution getAssetDistribution() {
        return assetDistribution;
    }

    public void setAssetDistribution(AssetDistribution assetDistribution) {
        this.assetDistribution = assetDistribution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetDestroy assetDestroy = (AssetDestroy) o;

        if ( ! Objects.equals(id, assetDestroy.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetDestroy{" +
            "id=" + id +
            ", transferReference='" + transferReference + "'" +
            ", destroyDate='" + destroyDate + "'" +
            ", usedPeriod='" + usedPeriod + "'" +
            ", causeOfDate='" + causeOfDate + "'" +
            '}';
    }
}
