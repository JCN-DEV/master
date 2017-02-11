package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PfmsLoanSchedule.
 */
@Entity
@Table(name = "pfms_loan_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pfmsloanschedule")
public class PfmsLoanSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "loan_year", nullable = false)
    private Long loanYear;

    @NotNull
    @Column(name = "loan_month", nullable = false)
    private String loanMonth;

    @NotNull
    @Column(name = "deducted_amount", nullable = false)
    private Double deductedAmount;

    @NotNull
    @Column(name = "installment_no", nullable = false)
    private Long installmentNo;

    @NotNull
    @Column(name = "tot_install_no", nullable = false)
    private Long totInstallNo;

    @NotNull
    @Column(name = "tot_loan_amount", nullable = false)
    private Double totLoanAmount;

    @NotNull
    @Column(name = "is_repay", nullable = false)
    private Boolean isRepay;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "reason")
    private String reason;

    @NotNull
    @Column(name = "active_status", nullable = false)
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
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "pfms_loan_app_id")
    private PfmsLoanApplication pfmsLoanApp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanYear() {
        return loanYear;
    }

    public void setLoanYear(Long loanYear) {
        this.loanYear = loanYear;
    }

    public String getLoanMonth() {
        return loanMonth;
    }

    public void setLoanMonth(String loanMonth) {
        this.loanMonth = loanMonth;
    }

    public Double getDeductedAmount() {
        return deductedAmount;
    }

    public void setDeductedAmount(Double deductedAmount) {
        this.deductedAmount = deductedAmount;
    }

    public Long getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(Long installmentNo) {
        this.installmentNo = installmentNo;
    }

    public Long getTotInstallNo() {
        return totInstallNo;
    }

    public void setTotInstallNo(Long totInstallNo) {
        this.totInstallNo = totInstallNo;
    }

    public Double getTotLoanAmount() {
        return totLoanAmount;
    }

    public void setTotLoanAmount(Double totLoanAmount) {
        this.totLoanAmount = totLoanAmount;
    }

    public Boolean getIsRepay() {
        return isRepay;
    }

    public void setIsRepay(Boolean isRepay) {
        this.isRepay = isRepay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public PfmsLoanApplication getPfmsLoanApp() {
        return pfmsLoanApp;
    }

    public void setPfmsLoanApp(PfmsLoanApplication PfmsLoanApplication) {
        this.pfmsLoanApp = PfmsLoanApplication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PfmsLoanSchedule pfmsLoanSchedule = (PfmsLoanSchedule) o;

        if ( ! Objects.equals(id, pfmsLoanSchedule.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PfmsLoanSchedule{" +
            "id=" + id +
            ", loanYear='" + loanYear + "'" +
            ", loanMonth='" + loanMonth + "'" +
            ", deductedAmount='" + deductedAmount + "'" +
            ", installmentNo='" + installmentNo + "'" +
            ", totInstallNo='" + totInstallNo + "'" +
            ", totLoanAmount='" + totLoanAmount + "'" +
            ", isRepay='" + isRepay + "'" +
            ", reason='" + reason + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
