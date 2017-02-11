package gov.step.app.domain;

import gov.step.app.domain.enumeration.TimeScaleLevel;
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
 * A TimeScale.
 */
@Entity
@Table(name = "time_scale")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "timescale")
public class TimeScale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private TimeScaleLevel level;

    @NotNull
    @Column(name = "class_grade", precision = 10, scale = 2, nullable = false)
    private BigDecimal classGrade;

    @Column(name = "salary_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal salaryAmount;


    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @ManyToOne
    private PayScale payScale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeScaleLevel getLevel() {
        return level;
    }

    public void setLevel(TimeScaleLevel level) {
        this.level = level;
    }

    public BigDecimal getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(BigDecimal classGrade) {
        this.classGrade = classGrade;
    }

    public BigDecimal getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(BigDecimal salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public PayScale getPayScale() {
        return payScale;
    }

    public void setPayScale(PayScale payScale) {
        this.payScale = payScale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimeScale timeScale = (TimeScale) o;

        if (!Objects.equals(id, timeScale.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TimeScale{" + "id=" + id + ", level='" + level + "'" + ", classGrade='" + classGrade + "'"
            + ", salaryAmount='" + salaryAmount + "'" + '}';
    }
}
