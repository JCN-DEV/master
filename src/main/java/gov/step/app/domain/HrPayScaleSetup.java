package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HrPayScaleSetup.
 */
@Entity
@Table(name = "hr_pay_scale_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrpayscalesetup")
public class HrPayScaleSetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "pay_scale_code", nullable = false)
    private String payScaleCode;

    @Column(name = "basic_pay_scale", precision=10, scale=2)
    private BigDecimal basicPayScale;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayScaleCode() {
        return payScaleCode;
    }

    public void setPayScaleCode(String payScaleCode) {
        this.payScaleCode = payScaleCode;
    }

    public BigDecimal getBasicPayScale() {
        return basicPayScale;
    }

    public void setBasicPayScale(BigDecimal basicPayScale) {
        this.basicPayScale = basicPayScale;
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

    public HrGazetteSetup getGazetteInfo() {return gazetteInfo;}

    public void setGazetteInfo(HrGazetteSetup gazetteInfo) {this.gazetteInfo = gazetteInfo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrPayScaleSetup hrPayScaleSetup = (HrPayScaleSetup) o;
        return Objects.equals(id, hrPayScaleSetup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrPayScaleSetup{" +
            "id=" + id +
            ", payScaleCode='" + payScaleCode + "'" +
            ", basicPayScale='" + basicPayScale + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
