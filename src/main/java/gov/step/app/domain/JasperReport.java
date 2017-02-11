package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A JasperReport.
 */
@Entity
@Table(name = "jasper_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jasperreport")
public class JasperReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @NotNull
    @Size(max = 250)
    @Column(name = "path", length = 250, nullable = false)
    private String path;

    @Size(max = 100)
    @Column(name = "module", length = 100)
    private String module;

    @Size(max = 100)
    @Column(name = "role", length = 100)
    private String role;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "storeprocedurestatus")
    private Boolean storeprocedurestatus;

    @Column(name = "procedurename")
    private String procedurename;

    @Column(name = "listtablestatus")
    private Boolean listtablestatus;

    @Column(name = "paramtable")
    private String paramtable;

    @Column(name = "fieldname")
    private String fieldname;

    @Column(name = "displayfieldname")
    private String displayfieldname;

    @Column(name = "fieldtype")
    private String fieldtype;

    @Column(name = "wherecouse")
    private String wherecouse;

    @Column(name = "orderbyfield")
    private String orderbyfield;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStoreprocedurestatus() {
        return storeprocedurestatus;
    }

    public void setStoreprocedurestatus(Boolean storeprocedurestatus) {
        this.storeprocedurestatus = storeprocedurestatus;
    }

    public String getProcedurename() {
        return procedurename;
    }

    public void setProcedurename(String procedurename) {
        this.procedurename = procedurename;
    }

    public Boolean getListtablestatus() {
        return listtablestatus;
    }

    public void setListtablestatus(Boolean listtablestatus) {
        this.listtablestatus = listtablestatus;
    }

    public String getParamtable() {
        return paramtable;
    }

    public void setParamtable(String paramtable) {
        this.paramtable = paramtable;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getDisplayfieldname() {
        return displayfieldname;
    }

    public void setDisplayfieldname(String displayfieldname) {
        this.displayfieldname = displayfieldname;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public String getWherecouse() {
        return wherecouse;
    }

    public void setWherecouse(String wherecouse) {
        this.wherecouse = wherecouse;
    }

    public String getOrderbyfield() {
        return orderbyfield;
    }

    public void setOrderbyfield(String orderbyfield) {
        this.orderbyfield = orderbyfield;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JasperReport jasperReport = (JasperReport) o;

        if ( ! Objects.equals(id, jasperReport.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JasperReport{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", path='" + path + "'" +
            ", module='" + module + "'" +
            ", role='" + role + "'" +
            ", status='" + status + "'" +
            ", storeprocedurestatus='" + storeprocedurestatus + "'" +
            ", procedurename='" + procedurename + "'" +
            ", listtablestatus='" + listtablestatus + "'" +
            ", paramtable='" + paramtable + "'" +
            ", fieldname='" + fieldname + "'" +
            ", displayfieldname='" + displayfieldname + "'" +
            ", fieldtype='" + fieldtype + "'" +
            ", wherecouse='" + wherecouse + "'" +
            ", orderbyfield='" + orderbyfield + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            '}';
    }
}
