package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstituteEmpCount.
 */
@Entity
@Table(name = "institute_emp_count")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instituteempcount")
public class InstituteEmpCount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "total_male_teacher", precision=10, scale=2)
    private BigDecimal totalMaleTeacher;

    @Column(name = "total_female_teacher", precision=10, scale=2)
    private BigDecimal totalFemaleTeacher;

    @Column(name = "total_male_employee", precision=10, scale=2)
    private BigDecimal totalMaleEmployee;

    @Column(name = "total_female_employee", precision=10, scale=2)
    private BigDecimal totalFemaleEmployee;

    @Column(name = "total_granted", precision=10, scale=2)
    private BigDecimal totalGranted;

    @Column(name = "total_engaged", precision=10, scale=2)
    private BigDecimal totalEngaged;

    @Column(name = "status")
    private Integer status;

    @OneToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalMaleTeacher() {
        return totalMaleTeacher;
    }

    public void setTotalMaleTeacher(BigDecimal totalMaleTeacher) {
        this.totalMaleTeacher = totalMaleTeacher;
    }

    public BigDecimal getTotalFemaleTeacher() {
        return totalFemaleTeacher;
    }

    public void setTotalFemaleTeacher(BigDecimal totalFemaleTeacher) {
        this.totalFemaleTeacher = totalFemaleTeacher;
    }

    public BigDecimal getTotalMaleEmployee() {
        return totalMaleEmployee;
    }

    public void setTotalMaleEmployee(BigDecimal totalMaleEmployee) {
        this.totalMaleEmployee = totalMaleEmployee;
    }

    public BigDecimal getTotalFemaleEmployee() {
        return totalFemaleEmployee;
    }

    public void setTotalFemaleEmployee(BigDecimal totalFemaleEmployee) {
        this.totalFemaleEmployee = totalFemaleEmployee;
    }

    public BigDecimal getTotalGranted() {
        return totalGranted;
    }

    public void setTotalGranted(BigDecimal totalGranted) {
        this.totalGranted = totalGranted;
    }

    public BigDecimal getTotalEngaged() {
        return totalEngaged;
    }

    public void setTotalEngaged(BigDecimal totalEngaged) {
        this.totalEngaged = totalEngaged;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstituteEmpCount instituteEmpCount = (InstituteEmpCount) o;

        if ( ! Objects.equals(id, instituteEmpCount.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstituteEmpCount{" +
            "id=" + id +
            ", totalMaleTeacher='" + totalMaleTeacher + "'" +
            ", totalFemaleTeacher='" + totalFemaleTeacher + "'" +
            ", totalMaleEmployee='" + totalMaleEmployee + "'" +
            ", totalFemaleEmployee='" + totalFemaleEmployee + "'" +
            ", totalGranted='" + totalGranted + "'" +
            ", totalEngaged='" + totalEngaged + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
