package gov.step.app.domain;

import gov.step.app.domain.enumeration.ApprovalStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PfmsUtmostGpfApp.
 */
@Entity
@Table(name = "pfms_utmost_gpf_app")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pfmsutmostgpfapp")
public class PfmsUtmostGpfApp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "prl_date", nullable = false)
    private LocalDate prlDate;

    @Column(name = "gpf_no")
    private String gpfNo;

    @Column(name = "last_deduction")
    private Double lastDeduction;

    @Column(name = "approval_status", nullable = false)
    private ApprovalStatus approvalStatus;

    @NotNull
    @Column(name = "deduction_date", nullable = false)
    private LocalDate deductionDate;

    @NotNull
    @Column(name = "bill_no", nullable = false)
    private String billNo;

    @NotNull
    @Column(name = "bill_date", nullable = false)
    private LocalDate billDate;

    @NotNull
    @Column(name = "token_no", nullable = false)
    private String tokenNo;

    @NotNull
    @Column(name = "token_date", nullable = false)
    private LocalDate tokenDate;

    @NotNull
    @Column(name = "withdraw_from", nullable = false)
    private String withdrawFrom;

    @NotNull
    @Column(name = "apply_date", nullable = false)
    private LocalDate applyDate;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPrlDate() {
        return prlDate;
    }

    public void setPrlDate(LocalDate prlDate) {
        this.prlDate = prlDate;
    }

    public String getGpfNo() {
        return gpfNo;
    }

    public void setGpfNo(String gpfNo) {
        this.gpfNo = gpfNo;
    }

    public ApprovalStatus getApprovalStatus(){
        return approvalStatus;
    }
    public void setApprovalStatus(ApprovalStatus approvalStatus){
        this.approvalStatus = approvalStatus;
    }


    public Double getLastDeduction() {
        return lastDeduction;
    }

    public void setLastDeduction(Double lastDeduction) {
        this.lastDeduction = lastDeduction;
    }

    public LocalDate getDeductionDate() {
        return deductionDate;
    }

    public void setDeductionDate(LocalDate deductionDate) {
        this.deductionDate = deductionDate;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }

    public LocalDate getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(LocalDate tokenDate) {
        this.tokenDate = tokenDate;
    }

    public String getWithdrawFrom() {
        return withdrawFrom;
    }

    public void setWithdrawFrom(String withdrawFrom) {
        this.withdrawFrom = withdrawFrom;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
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

        PfmsUtmostGpfApp pfmsUtmostGpfApp = (PfmsUtmostGpfApp) o;

        if ( ! Objects.equals(id, pfmsUtmostGpfApp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PfmsUtmostGpfApp{" +
            "id=" + id +
            ", prlDate='" + prlDate + "'" +
            ", gpfNo='" + gpfNo + "'" +
            ", lastDeduction='" + lastDeduction + "'" +
            ", deductionDate='" + deductionDate + "'" +
            ", billNo='" + billNo + "'" +
            ", billDate='" + billDate + "'" +
            ", tokenNo='" + tokenNo + "'" +
            ", tokenDate='" + tokenDate + "'" +
            ", withdrawFrom='" + withdrawFrom + "'" +
            ", applyDate='" + applyDate + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
