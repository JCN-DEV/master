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
 * A AssetDistribution.
 */
@Entity
@Table(name = "asset_distribution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetdistribution")
public class AssetDistribution implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "transfer_ref", nullable = false)
    private String transferRef;

    @Column(name = "assigned_ddate", nullable = false)
    private LocalDate assignedDdate;

    @NotNull
    @Column(name = "remartks", nullable = false)
    private String remartks;

    @Column(name = "assign_date")
    private LocalDate assignDate;

    @Column(name = "assign_by")
    private Long assignBy;

    @Column(name = "types")
    private String types;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "asset_code")
    private String assetCode;

    @Column(name = "asset_name")
    private String assetName;

    @Column(name = "asset_status")
    private Boolean assetStatus;

    @Column(name = "approve_quantity")
    private Long approveQuantity;

    @Column(name = "requested_quantity")
    private Long requestedQuantity;

    @Column(name = "asset_type")
    private String assetType;


    @Column(name = "emploee_Ids")
    private Long emploeeIds;

    @Column(name = "requisiton_Id")
    private Long requisitonId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private HrEmployeeInfo hrEmployeeInfo;

    @ManyToOne
    @JoinColumn(name = "asset_record_id")
    private AssetRecord assetRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransferRef() {
        return transferRef;
    }

    public void setTransferRef(String transferRef) {
        this.transferRef = transferRef;
    }

    public LocalDate getAssignedDdate() {
        return assignedDdate;
    }

    public void setAssignedDdate(LocalDate assignedDdate) {
        this.assignedDdate = assignedDdate;
    }

    public String getRemartks() {
        return remartks;
    }

    public void setRemartks(String remartks) {
        this.remartks = remartks;
    }



    public AssetRecord getAssetRecord() {
        return assetRecord;
    }

    public void setAssetRecord(AssetRecord assetRecord) {
        this.assetRecord = assetRecord;
    }

    public HrEmployeeInfo getHrEmployeeInfo() {
        return hrEmployeeInfo;
    }

    public void setHrEmployeeInfo(HrEmployeeInfo hrEmployeeInfo) {
        this.hrEmployeeInfo = hrEmployeeInfo;
    }


    public LocalDate getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(LocalDate assignDate) {
        this.assignDate = assignDate;
    }

    public Long getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(Long assignBy) {
        this.assignBy = assignBy;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
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

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Boolean getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Boolean assetStatus) {
        this.assetStatus = assetStatus;
    }


    public Long getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Long requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Long getApproveQuantity() {
        return approveQuantity;
    }

    public void setApproveQuantity(Long approveQuantity) {
        this.approveQuantity = approveQuantity;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public Long getEmploeeIds() {
        return emploeeIds;
    }

    public void setEmploeeIds(Long emploeeIds) {
        this.emploeeIds = emploeeIds;
    }

    public Long getRequisitonId() {
        return requisitonId;
    }

    public void setRequisitonId(Long requisitonId) {
        this.requisitonId = requisitonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetDistribution assetDistribution = (AssetDistribution) o;

        if ( ! Objects.equals(id, assetDistribution.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetDistribution{" +
            "id=" + id +
            ", transferRef='" + transferRef + '\'' +
            ", assignedDdate=" + assignedDdate +
            ", remartks='" + remartks + '\'' +
            ", employeeId=" + hrEmployeeInfo +
            ", assetRecord=" + assetRecord +
            '}';
    }
}
