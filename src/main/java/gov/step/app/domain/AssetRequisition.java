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
 * A AssetRequisition.
 */
@Entity
@Table(name = "asset_requisition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetrequisition")
public class AssetRequisition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@NotNull
    //@Column(name = "emp_id", nullable = false)
    @Column(name = "emp_id")
    private Long empId;

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    private String department;

    @Column(name = "requisition_id")
    private String requisitionId;

    @Column(name = "requisition_date")
    private LocalDate requisitionDate;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "reason_of_req")
    private String reasonOfReq;

    @Column(name = "req_status")
    private String reqStatus;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "requisition_type")
    private String requisitionType;

    @ManyToOne
    @JoinColumn(name="asset_type_id")
    private AssetTypeSetup assetType;

    @ManyToOne
    @JoinColumn(name="asset_category_id")
    private AssetCategorySetup assetCategory;

    @ManyToOne
    @JoinColumn(name="asset_record_id")
    private AssetRecord assetRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public LocalDate getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(LocalDate requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getReasonOfReq() {
        return reasonOfReq;
    }

    public void setReasonOfReq(String reasonOfReq) {
        this.reasonOfReq = reasonOfReq;
    }

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public AssetTypeSetup getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetTypeSetup AssetTypeSetup) {
        this.assetType = AssetTypeSetup;
    }

    public AssetCategorySetup getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(AssetCategorySetup AssetCategorySetup) {
        this.assetCategory = AssetCategorySetup;
    }

    public AssetRecord getAssetRecord() {
        return assetRecord;
    }

    public void setAssetRecord(AssetRecord AssetRecord) {
        this.assetRecord = AssetRecord;
    }

    public String getRequisitionType() {
        return requisitionType;
    }

    public void setRequisitionType(String requisitionType) {
        this.requisitionType = requisitionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetRequisition assetRequisition = (AssetRequisition) o;

        if ( ! Objects.equals(id, assetRequisition.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetRequisition{" +
            "id=" + id +
            ", empId='" + empId + "'" +
            ", empName='" + empName + "'" +
            ", designation='" + designation + "'" +
            ", department='" + department + "'" +
            ", requisitionId='" + requisitionId + "'" +
            ", requisitionDate='" + requisitionDate + "'" +
            ", quantity='" + quantity + "'" +
            ", reasonOfReq='" + reasonOfReq + "'" +
            ", reqStatus='" + reqStatus + "'" +
            ", status='" + status + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
