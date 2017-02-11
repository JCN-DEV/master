package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AssetAuctionInformation.
 */
@Entity
@Table(name = "asset_auction_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetauctioninformation")
public class AssetAuctionInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "last_repair_date")
    private LocalDate lastRepairDate;

    @Column(name = "is_auction_before")
    private Boolean isAuctionBefore;

    @NotNull
    @Column(name = "ref_no", nullable = false)
    private String refNo;

    @ManyToOne
    private AssetRecord assetCode;

    public AssetRecord getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(AssetRecord assetCode) {
        this.assetCode = assetCode;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    @ManyToOne
    @JoinColumn(name = "asset_distribution_id")
    private AssetDistribution assetDistribution;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLastRepairDate() {
        return lastRepairDate;
    }

    public void setLastRepairDate(LocalDate lastRepairDate) {
        this.lastRepairDate = lastRepairDate;
    }

    public Boolean getIsAuctionBefore() {
        return isAuctionBefore;
    }

    public void setIsAuctionBefore(Boolean isAuctionBefore) {
        this.isAuctionBefore = isAuctionBefore;
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

        AssetAuctionInformation assetAuctionInformation = (AssetAuctionInformation) o;

        if ( ! Objects.equals(id, assetAuctionInformation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetAuctionInformation{" +
            "id=" + id +
            ", lastRepairDate='" + lastRepairDate + "'" +
            ", isAuctionBefore='" + isAuctionBefore + "'" +
            '}';
    }
}
