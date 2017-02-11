package gov.step.app.domain.payroll;

import gov.step.app.domain.HrGazetteSetup;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PrlHouseRentAllowInfo.
 */
@Entity
@Table(name = "prl_house_rent_allow_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlhouserentallowinfo")
public class PrlHouseRentAllowInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "basic_salary_min", precision=10, scale=2)
    private BigDecimal basicSalaryMin;

    @Column(name = "basic_salary_max", precision=10, scale=2)
    private BigDecimal basicSalaryMax;

    @Column(name = "minimum_house_rent", precision=10, scale=2)
    private BigDecimal minimumHouseRent;

    @Column(name = "rent_percentage")
    private Float rentPercentage;

    @NotNull
    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "locality_set_info_id")
    private PrlLocalitySetInfo localitySetInfo;

    @ManyToOne
    @JoinColumn(name = "gazette_info_id")
    private HrGazetteSetup gazetteInfo;


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

    public BigDecimal getBasicSalaryMin() {
        return basicSalaryMin;
    }

    public void setBasicSalaryMin(BigDecimal basicSalaryMin) {
        this.basicSalaryMin = basicSalaryMin;
    }

    public BigDecimal getBasicSalaryMax() {
        return basicSalaryMax;
    }

    public void setBasicSalaryMax(BigDecimal basicSalaryMax) {
        this.basicSalaryMax = basicSalaryMax;
    }

    public BigDecimal getMinimumHouseRent() {
        return minimumHouseRent;
    }

    public void setMinimumHouseRent(BigDecimal minimumHouseRent) {
        this.minimumHouseRent = minimumHouseRent;
    }

    public Float getRentPercentage() {
        return rentPercentage;
    }

    public void setRentPercentage(Float rentPercentage) {
        this.rentPercentage = rentPercentage;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
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

    public PrlLocalitySetInfo getLocalitySetInfo() {
        return localitySetInfo;
    }

    public void setLocalitySetInfo(PrlLocalitySetInfo PrlLocalitySetInfo) {
        this.localitySetInfo = PrlLocalitySetInfo;
    }

    public HrGazetteSetup getGazetteInfo() {
        return gazetteInfo;
    }

    public void setGazetteInfo(HrGazetteSetup HrGazetteSetup) {
        this.gazetteInfo = HrGazetteSetup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlHouseRentAllowInfo prlHouseRentAllowInfo = (PrlHouseRentAllowInfo) o;
        return Objects.equals(id, prlHouseRentAllowInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlHouseRentAllowInfo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", basicSalaryMin='" + basicSalaryMin + "'" +
            ", basicSalaryMax='" + basicSalaryMax + "'" +
            ", minimumHouseRent='" + minimumHouseRent + "'" +
            ", rentPercentage='" + rentPercentage + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
