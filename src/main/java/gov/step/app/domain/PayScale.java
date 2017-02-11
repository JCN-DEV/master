package gov.step.app.domain;

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
 * A PayScale.
 */
@Entity
@Table(name = "pay_scale",uniqueConstraints={@UniqueConstraint(columnNames={"code"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "payscale")
public class PayScale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false,unique=true)
    private String code;

    @Column(name = "pay_scale_class")
    private String payScaleClass;

    @Column(name = "grade")
    private String grade;

    @Column(name = "grade_name")
    private String gradeName;

    @Column(name = "basic_amount", precision=10, scale=2, nullable = false)
    private BigDecimal basicAmount;

    @Column(name = "house_allowance", precision=10, scale=2, nullable = false)
    private BigDecimal houseAllowance;

    @Column(name = "medical_allowance", precision=10, scale=2, nullable = false)
    private BigDecimal medicalAllowance;

    @Column(name = "welfare_amount", precision=10, scale=2, nullable = false)
    private BigDecimal welfareAmount;

    @Column(name = "retirement_amount", precision=10, scale=2, nullable = false)
    private BigDecimal retirementAmount;

    @Column(name = "payscale_date", nullable = false)
    private LocalDate payscaleDate;

    @Column(name = "d_allowance", nullable = false)
    private Double dAllowance;

    @Column(name = "incr_equivalent", nullable = false)
    private Double incrEquivalent;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPayScaleClass() {
        return payScaleClass;
    }

    public void setPayScaleClass(String payScaleClass) {
        this.payScaleClass = payScaleClass;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public BigDecimal getBasicAmount() {
        return basicAmount;
    }

    public void setBasicAmount(BigDecimal basicAmount) {
        this.basicAmount = basicAmount;
    }

    public BigDecimal getHouseAllowance() {
        return houseAllowance;
    }

    public void setHouseAllowance(BigDecimal houseAllowance) {
        this.houseAllowance = houseAllowance;
    }

    public BigDecimal getMedicalAllowance() {
        return medicalAllowance;
    }

    public void setMedicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public BigDecimal getWelfareAmount() {
        return welfareAmount;
    }

    public void setWelfareAmount(BigDecimal welfareAmount) {
        this.welfareAmount = welfareAmount;
    }

    public BigDecimal getRetirementAmount() {
        return retirementAmount;
    }

    public void setRetirementAmount(BigDecimal retirementAmount) {
        this.retirementAmount = retirementAmount;
    }

    public LocalDate getPayscaleDate() {
        return payscaleDate;
    }

    public void setPayscaleDate(LocalDate payscaleDate) {
        this.payscaleDate = payscaleDate;
    }

    public Double getdAllowance() {
        return dAllowance;
    }

    public void setdAllowance(Double dAllowance) {
        this.dAllowance = dAllowance;
    }

    public Double getIncrEquivalent() {
        return incrEquivalent;
    }

    public void setIncrEquivalent(Double incrEquivalent) {
        this.incrEquivalent = incrEquivalent;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User user) {
        this.manager = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PayScale payScale = (PayScale) o;

        if ( ! Objects.equals(id, payScale.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PayScale{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", payScaleClass='" + payScaleClass + "'" +
            ", grade='" + grade + "'" +
            ", gradeName='" + gradeName + "'" +
            ", basicAmount='" + basicAmount + "'" +
            ", houseAllowance='" + houseAllowance + "'" +
            ", medicalAllowance='" + medicalAllowance + "'" +
            ", welfareAmount='" + welfareAmount + "'" +
            ", retirementAmount='" + retirementAmount + "'" +
            ", date='" + payscaleDate + "'" +
            '}';
    }
}
