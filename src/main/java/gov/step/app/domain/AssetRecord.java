package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A AssetRecord.
 */
@Entity
@Table(name = "asset_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetrecord")
public class AssetRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "asset_name", nullable = false)
    private String assetName;



    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "color")
    private String color;

    @Column(name = "manufacturing_Country")
    private String manufacturingCountry;

    @Column(name = "warranty")
    private String warranty;

    @Column(name = "expire_Date", nullable = false)
    private LocalDate expireDate;

    @NotNull
    @Size(min = 3)
    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    //New Added Field for Extend
    @Column(name = "status_asset")
    private Integer statusAsset;
    @Column(name = "asset_group")
    private Integer assetGroup;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @NotNull
    @Column(name = "order_no", nullable = false)
    private String orderNo;

    @NotNull
    @Column(name = "price", precision=10, scale=2, nullable = false)
    private BigDecimal price;

    @Column(name = "status")
    private Boolean status;

    /*@NotNull
    @Size(max = 100)
    @Column(name = "record_Code", length = 100, nullable = false)*/
    @Column(name = "record_Code")
    private String recordCode;

    //@NotNull
    @Size(max = 100)
    @Column(name = "reference_no", length = 100, nullable = true)
    private String referenceNo;

    @ManyToOne
    private AssetAccuisitionSetup assetAccuisition;

    @ManyToOne
    @JoinColumn(name = "asset_category_setup_id")
    private AssetCategorySetup assetCategorySetup;

    @ManyToOne
    @JoinColumn(name = "asset_type_setup_id")
    private AssetTypeSetup assetTypeSetup;

    @ManyToOne
    @JoinColumn(name = "asset_supplier_id")
    private AssetSupplier assetSupplier;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getManufacturingCountry() {
        return manufacturingCountry;
    }

    public void setManufacturingCountry(String manufacturingCountry) {
        this.manufacturingCountry = manufacturingCountry;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }



    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public AssetCategorySetup getAssetCategorySetup() {
        return assetCategorySetup;
    }

    public void setAssetCategorySetup(AssetCategorySetup assetCategorySetup) {
        this.assetCategorySetup = assetCategorySetup;
    }

    public AssetTypeSetup getAssetTypeSetup() {
        return assetTypeSetup;
    }

    public void setAssetTypeSetup(AssetTypeSetup assetTypeSetup) {
        this.assetTypeSetup = assetTypeSetup;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public AssetSupplier getAssetSupplier() {
        return assetSupplier;
    }

    public void setAssetSupplier(AssetSupplier assetSupplier) {
        this.assetSupplier = assetSupplier;
    }

    public AssetAccuisitionSetup getAssetAccuisition() {
        return assetAccuisition;
    }

    public void setAssetAccuisition(AssetAccuisitionSetup assetAccuisition) {
        this.assetAccuisition = assetAccuisition;
    }

    public Integer getStatusAsset() {
        return statusAsset;
    }

    public void setStatusAsset(Integer statusAsset) {
        this.statusAsset = statusAsset;
    }

    public Integer getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(Integer assetGroup) {
        this.assetGroup = assetGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetRecord assetRecord = (AssetRecord) o;

        if ( ! Objects.equals(id, assetRecord.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetRecord{" +
            "id=" + id +
            ", assetName='" + assetName + "'" +
            ", vendorName='" + vendorName + "'" +
            ", purchaseDate='" + purchaseDate + "'" +
            ", orderNo='" + orderNo + "'" +
            ", price='" + price + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
