package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AssetSupplier.
 */
@Entity
@Table(name = "asset_supplier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetsupplier")
public class AssetSupplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    //@NotNull
    //@Size(max = 50)
    //@Column(name = "supplier_id", length = 50)
    @Column(name = "supplier_id")
    private String supplierId;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "products", nullable = false)
    private String products;

    @NotNull
    @Column(name = "contact_no", nullable = false)
    private String contactNo;

    @Column(name = "telephone_no")
    private String telephoneNo;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "web_site")
    private String webSite;

    @Column(name = "fax_no")
    private String faxNo;

    @Column(name = "status")
    private Boolean status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetSupplier assetSupplier = (AssetSupplier) o;

        if ( ! Objects.equals(id, assetSupplier.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetSupplier{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", supplierId='" + supplierId + "'" +
            ", address='" + address + "'" +
            ", products='" + products + "'" +
            ", contactNo='" + contactNo + "'" +
            ", telephoneNo='" + telephoneNo + "'" +
            ", email='" + email + "'" +
            ", webSite='" + webSite + "'" +
            ", faxNo='" + faxNo + "'" +
            '}';
    }
}
