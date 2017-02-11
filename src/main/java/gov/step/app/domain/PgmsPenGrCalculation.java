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

import gov.step.app.domain.enumeration.WithdrawnType;

/**
 * A PgmsPenGrCalculation.
 */
@Entity
@Table(name = "pgms_pen_gr_calculation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pgmspengrcalculation")
public class PgmsPenGrCalculation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "withdrawn_type")
    private WithdrawnType withdrawnType;

    @NotNull
    @Column(name = "category_type", nullable = false)
    private String categoryType;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "grade_info_id")
    private HrGradeSetup gradeInfo;

    @ManyToOne
    @JoinColumn(name = "salary_info_id")
    private HrPayScaleSetup salaryInfo;

    @ManyToOne
    @JoinColumn(name = "working_year_id")
    private PgmsPenGrRate workingYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WithdrawnType getWithdrawnType() {
        return withdrawnType;
    }

    public void setWithdrawnType(WithdrawnType withdrawnType) {
        this.withdrawnType = withdrawnType;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
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

    public HrPayScaleSetup getSalaryInfo() {
        return salaryInfo;
    }

    public void setSalaryInfo(HrPayScaleSetup HrPayScaleSetup) {
        this.salaryInfo = HrPayScaleSetup;
    }

    public PgmsPenGrRate getWorkingYear() {
        return workingYear;
    }

    public void setWorkingYear(PgmsPenGrRate PgmsPenGrRate) {
        this.workingYear = PgmsPenGrRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgmsPenGrCalculation pgmsPenGrCalculation = (PgmsPenGrCalculation) o;

        if ( ! Objects.equals(id, pgmsPenGrCalculation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PgmsPenGrCalculation{" +
            "id=" + id +
            ", withdrawnType='" + withdrawnType + "'" +
            ", categoryType='" + categoryType + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
