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
 * A HrEntertainmentBenefit.
 */
@Entity
@Table(name = "hr_entertainment_benefit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrentertainmentbenefit")
public class HrEntertainmentBenefit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "eligibility_date", nullable = false)
    private LocalDate eligibilityDate;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "total_days", nullable = false)
    private Integer totalDays;

    @Column(name = "not_taken_reason")
    private String notTakenReason;

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
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "job_category_id")
    private MiscTypeSetup jobCategory;

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

    public LocalDate getEligibilityDate() {
        return eligibilityDate;
    }

    public void setEligibilityDate(LocalDate eligibilityDate) {
        this.eligibilityDate = eligibilityDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public String getNotTakenReason() {
        return notTakenReason;
    }

    public void setNotTakenReason(String notTakenReason) {
        this.notTakenReason = notTakenReason;
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
        HrEntertainmentBenefit hrEntertainmentBenefit = (HrEntertainmentBenefit) o;
        return Objects.equals(id, hrEntertainmentBenefit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEntertainmentBenefit{" +
            "id=" + id +
            ", eligibilityDate='" + eligibilityDate + "'" +
            ", amount='" + amount + "'" +
            ", totalDays='" + totalDays + "'" +
            ", notTakenReason='" + notTakenReason + "'" +
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
