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
 * A HrEmpIncrementInfo.
 */
@Entity
@Table(name = "hr_emp_increment_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrempincrementinfo")
public class HrEmpIncrementInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "increment_amount", precision=10, scale=2, nullable = false)
    private BigDecimal incrementAmount;

    @NotNull
    @Column(name = "increment_date", nullable = false)
    private LocalDate incrementDate;

    @NotNull
    @Column(name = "basic", precision=10, scale=2, nullable = false)
    private BigDecimal basic;

    @NotNull
    @Column(name = "basic_date", nullable = false)
    private LocalDate basicDate;

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

    @OneToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "job_category_id")
    private MiscTypeSetup jobCategory;

    @ManyToOne
    @JoinColumn(name = "pay_scale_id")
    private HrPayScaleSetup payScale;

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "log_comments")
    private String logComments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getIncrementAmount() {
        return incrementAmount;
    }

    public void setIncrementAmount(BigDecimal incrementAmount) {
        this.incrementAmount = incrementAmount;
    }

    public LocalDate getIncrementDate() {
        return incrementDate;
    }

    public void setIncrementDate(LocalDate incrementDate) {
        this.incrementDate = incrementDate;
    }

    public BigDecimal getBasic() {
        return basic;
    }

    public void setBasic(BigDecimal basic) {
        this.basic = basic;
    }

    public LocalDate getBasicDate() {
        return basicDate;
    }

    public void setBasicDate(LocalDate basicDate) {
        this.basicDate = basicDate;
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

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

    public MiscTypeSetup getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(MiscTypeSetup MiscTypeSetup) {
        this.jobCategory = MiscTypeSetup;
    }

    public HrPayScaleSetup getPayScale() {
        return payScale;
    }

    public void setPayScale(HrPayScaleSetup HrPayScaleSetup) {
        this.payScale = HrPayScaleSetup;
    }

    public Long getLogId() { return logId;}

    public void setLogId(Long logId) {this.logId = logId;}

    public Long getLogStatus() {return logStatus;}

    public void setLogStatus(Long logStatus) {this.logStatus = logStatus;}

    public String getLogComments() {return logComments;}

    public void setLogComments(String logComments) {this.logComments = logComments;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpIncrementInfo hrEmpIncrementInfo = (HrEmpIncrementInfo) o;
        return Objects.equals(id, hrEmpIncrementInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpIncrementInfo{" +
            "id=" + id +
            ", incrementAmount='" + incrementAmount + "'" +
            ", incrementDate='" + incrementDate + "'" +
            ", basic='" + basic + "'" +
            ", basicDate='" + basicDate + "'" +
            ", logId='" + logId + "'" +
            ", logStatus='" + logStatus + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
