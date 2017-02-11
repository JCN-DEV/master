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
 * A PfmsRegister.
 */
@Entity
@Table(name = "pfms_register")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pfmsregister")
public class PfmsRegister implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "application_type", nullable = false)
    private String applicationType;

    @NotNull
    @Column(name = "is_bill_register", nullable = false)
    private Boolean isBillRegister;

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "bill_issue_date", nullable = false)
    private LocalDate billIssueDate;

    @Column(name = "bill_receiver_name")
    private String billReceiverName;

    @Column(name = "bill_place")
    private String billPlace;

    @Column(name = "bill_date", nullable = false)
    private LocalDate billDate;

    @Column(name = "no_of_withdrawal")
    private Long noOfWithdrawal;

    @Column(name = "check_no")
    private String checkNo;

    @Column(name = "check_date", nullable = false)
    private LocalDate checkDate;

    @Column(name = "check_token_no")
    private String checkTokenNo;

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
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Boolean getIsBillRegister() {
        return isBillRegister;
    }

    public void setIsBillRegister(Boolean isBillRegister) {
        this.isBillRegister = isBillRegister;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public LocalDate getBillIssueDate() {
        return billIssueDate;
    }

    public void setBillIssueDate(LocalDate billIssueDate) {
        this.billIssueDate = billIssueDate;
    }

    public String getBillReceiverName() {
        return billReceiverName;
    }

    public void setBillReceiverName(String billReceiverName) {
        this.billReceiverName = billReceiverName;
    }

    public String getBillPlace() {
        return billPlace;
    }

    public void setBillPlace(String billPlace) {
        this.billPlace = billPlace;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public Long getNoOfWithdrawal() {
        return noOfWithdrawal;
    }

    public void setNoOfWithdrawal(Long noOfWithdrawal) {
        this.noOfWithdrawal = noOfWithdrawal;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public LocalDate getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDate checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckTokenNo() {
        return checkTokenNo;
    }

    public void setCheckTokenNo(String checkTokenNo) {
        this.checkTokenNo = checkTokenNo;
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

        PfmsRegister pfmsRegister = (PfmsRegister) o;

        if ( ! Objects.equals(id, pfmsRegister.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PfmsRegister{" +
            "id=" + id +
            ", applicationType='" + applicationType + "'" +
            ", isBillRegister='" + isBillRegister + "'" +
            ", billNo='" + billNo + "'" +
            ", billIssueDate='" + billIssueDate + "'" +
            ", billReceiverName='" + billReceiverName + "'" +
            ", billPlace='" + billPlace + "'" +
            ", billDate='" + billDate + "'" +
            ", noOfWithdrawal='" + noOfWithdrawal + "'" +
            ", checkNo='" + checkNo + "'" +
            ", checkDate='" + checkDate + "'" +
            ", checkTokenNo='" + checkTokenNo + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
