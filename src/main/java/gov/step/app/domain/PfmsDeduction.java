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
 * A PfmsDeduction.
 */
@Entity
@Table(name = "pfms_deduction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pfmsdeduction")
public class PfmsDeduction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @NotNull
    @Column(name = "deduction_year", nullable = false)
    private Long deductionYear;

    @NotNull
    @Column(name = "deduction_month", nullable = false)
    private String deductionMonth;

    @NotNull
    @Column(name = "deduction_amount", nullable = false)
    private Double deductionAmount;

    @NotNull
    @Column(name = "installment_no", nullable = false)
    private Long installmentNo;

    @NotNull
    @Column(name = "deduction_date", nullable = false)
    private LocalDate deductionDate;

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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public PfmsLoanApplication getPfmsLoanApp(){
        return pfmsLoanApp;
    }

    public void setPfmsLoanApp(PfmsLoanApplication pfmsLoanApp){
        this.pfmsLoanApp = pfmsLoanApp;
    }

    public Double getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(Double deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public Long getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(Long installmentNo) {
        this.installmentNo = installmentNo;
    }

    public Long getDeductionYear() {
        return deductionYear;
    }

    public void setDeductionYear(Long deductionYear) {
        this.deductionYear = deductionYear;
    }

    public String getDeductionMonth() {
        return deductionMonth;
    }

    public void setDeductionMonth(String deductionMonth) {
        this.deductionMonth = deductionMonth;
    }

    public LocalDate getDeductionDate() {
        return deductionDate;
    }

    public void setDeductionDate(LocalDate deductionDate) {
        this.deductionDate = deductionDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PfmsDeduction pfmsDeduction = (PfmsDeduction) o;

        if ( ! Objects.equals(id, pfmsDeduction.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PfmsDeduction{" +
            "id=" + id +
            ", accountNo='" + accountNo + "'" +
            ", deductionAmount='" + deductionAmount + "'" +
            ", deductionDate='" + deductionDate + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
