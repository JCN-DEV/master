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
 * A AssetRepair.
 */
@Entity
@Table(name = "asset_repair")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetrepair")
public class AssetRepair implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ref_no", nullable = false)
    private String refNo;

    @NotNull
    @Size(max = 50)
    @Column(name = "repaired_by", length = 50, nullable = false)
    private String repairedBy;

    @Column(name = "date_of_problem", nullable = false)
    private LocalDate dateOfProblem;

    @Column(name = "repair_date")
    private String repairDate;

    @Column(name = "repair_cost", precision=10, scale=2, nullable = false)
    private BigDecimal repairCost;

    @ManyToOne
    @JoinColumn(name = "asset_repair")
    private AssetDistribution assetDistribution;

    //@ManyToOne
    //private Employee employee;

    @ManyToOne
    private AssetRecord assetCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getRepairedBy() {
        return repairedBy;
    }

    public void setRepairedBy(String repairedBy) {
        this.repairedBy = repairedBy;
    }

    public LocalDate getDateOfProblem() {
        return dateOfProblem;
    }

    public void setDateOfProblem(LocalDate dateOfProblem) {
        this.dateOfProblem = dateOfProblem;
    }

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public BigDecimal getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(BigDecimal repairCost) {
        this.repairCost = repairCost;
    }

//    public Employee getEmployee() {
//        return employee;
//    }
//
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }
//


    public AssetRecord getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(AssetRecord assetCode) {
        this.assetCode = assetCode;
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

        AssetRepair assetRepair = (AssetRepair) o;

        if ( ! Objects.equals(id, assetRepair.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AssetRepair{" +
            "id=" + id +
            ", refNo='" + refNo + "'" +
            ", repairedBy='" + repairedBy + "'" +
            ", dateOfProblem='" + dateOfProblem + "'" +
            ", repairDate='" + repairDate + "'" +
            ", repairCost='" + repairCost + "'" +
            '}';
    }
}
