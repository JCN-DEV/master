package gov.step.app.domain.payroll;

import gov.step.app.domain.HrGazetteSetup;
import gov.step.app.domain.HrGradeSetup;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A PrlPayscaleInfo.
 */
@Entity
@Table(name = "prl_payscale_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlpayscaleinfo")
public class PrlPayscaleInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "max_basic_eleg_year", precision=10, scale=2)
    private BigDecimal maxBasicElegYear;

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
    @JoinColumn(name = "grade_info_id")
    private HrGradeSetup gradeInfo;

    @ManyToOne
    @JoinColumn(name = "gazette_info_id")
    private HrGazetteSetup gazetteInfo;

    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "payscaleInfo")
    private List<PrlPayscaleBasicInfo> basicInfoList;

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

    public BigDecimal getMaxBasicElegYear() {
        return maxBasicElegYear;
    }

    public void setMaxBasicElegYear(BigDecimal maxBasicElegYear) {
        this.maxBasicElegYear = maxBasicElegYear;
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

    public HrGradeSetup getGradeInfo() {
        return gradeInfo;
    }

    public void setGradeInfo(HrGradeSetup HrGradeSetup) {
        this.gradeInfo = HrGradeSetup;
    }

    public HrGazetteSetup getGazetteInfo() {
        return gazetteInfo;
    }

    public void setGazetteInfo(HrGazetteSetup HrGazetteSetup) {this.gazetteInfo = HrGazetteSetup;}

    public List<PrlPayscaleBasicInfo> getBasicInfoList() {return basicInfoList;}

    public void setBasicInfoList(List<PrlPayscaleBasicInfo> basicInfoList) {this.basicInfoList = basicInfoList;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlPayscaleInfo prlPayscaleInfo = (PrlPayscaleInfo) o;
        return Objects.equals(id, prlPayscaleInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlPayscaleInfo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", maxBasicElegYear='" + maxBasicElegYear + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
