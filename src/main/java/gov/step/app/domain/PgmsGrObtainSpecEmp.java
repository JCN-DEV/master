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
 * A PgmsGrObtainSpecEmp.
 */
@Entity
@Table(name = "pgms_gr_obtain_spec_emp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pgmsgrobtainspecemp")
public class PgmsGrObtainSpecEmp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "emp_name", nullable = false)
    private String empName;

    @NotNull
    @Column(name = "emp_designation", nullable = false)
    private String empDesignation;

    @NotNull
    @Column(name = "emp_department", nullable = false)
    private String empDepartment;

    @NotNull
    @Column(name = "emp_end_date", nullable = false)
    private LocalDate empEndDate;

    @NotNull
    @Column(name = "emp_status", nullable = false)
    private Boolean empStatus;

    @NotNull
    @Column(name = "emp_wrking_year", nullable = false)
    private Long empWrkingYear;

    @NotNull
    @Column(name = "amount_as_gr", nullable = false)
    private Long amountAsGr;

    @NotNull
    @Column(name = "obtain_date", nullable = false)
    private LocalDate obtainDate;

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
    @JoinColumn(name = "inst_emp_id_id")
    private HrEmployeeInfo instEmpId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpDesignation() {
        return empDesignation;
    }

    public void setEmpDesignation(String empDesignation) {
        this.empDesignation = empDesignation;
    }

    public String getEmpDepartment() {
        return empDepartment;
    }

    public void setEmpDepartment(String empDepartment) {
        this.empDepartment = empDepartment;
    }

    public LocalDate getEmpEndDate() {
        return empEndDate;
    }

    public void setEmpEndDate(LocalDate empEndDate) {
        this.empEndDate = empEndDate;
    }

    public Boolean getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(Boolean empStatus) {
        this.empStatus = empStatus;
    }

    public Long getEmpWrkingYear() {
        return empWrkingYear;
    }

    public void setEmpWrkingYear(Long empWrkingYear) {
        this.empWrkingYear = empWrkingYear;
    }

    public Long getAmountAsGr() {
        return amountAsGr;
    }

    public void setAmountAsGr(Long amountAsGr) {
        this.amountAsGr = amountAsGr;
    }

    public LocalDate getObtainDate() {
        return obtainDate;
    }

    public void setObtainDate(LocalDate obtainDate) {
        this.obtainDate = obtainDate;
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

    public HrEmployeeInfo getInstEmpId() {
        return instEmpId;
    }

    public void setInstEmpId(HrEmployeeInfo HrEmployeeInfo) {
        this.instEmpId = HrEmployeeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgmsGrObtainSpecEmp pgmsGrObtainSpecEmp = (PgmsGrObtainSpecEmp) o;

        if ( ! Objects.equals(id, pgmsGrObtainSpecEmp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PgmsGrObtainSpecEmp{" +
            "id=" + id +
            ", empName='" + empName + "'" +
            ", empDesignation='" + empDesignation + "'" +
            ", empDepartment='" + empDepartment + "'" +
            ", empEndDate='" + empEndDate + "'" +
            ", empStatus='" + empStatus + "'" +
            ", empWrkingYear='" + empWrkingYear + "'" +
            ", amountAsGr='" + amountAsGr + "'" +
            ", obtainDate='" + obtainDate + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
