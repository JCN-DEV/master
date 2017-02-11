package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import gov.step.app.domain.enumeration.accountType;

/**
 * A InstFinancialInfoTemp.
 */
@Entity
@Table(name = "inst_financial_info_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instfinancialinfotemp")
public class InstFinancialInfoTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "bank_name", nullable = true)
    private String bankName;


    @Column(name = "branch_name", nullable = true)
    private String branchName;


    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = true)
    private accountType accountType;


    @Column(name = "account_no", nullable = true)
    private String accountNo;

    @Column(name = "issue_date", nullable = true)
    private LocalDate issueDate;

    @Column(name = "expire_date", nullable = true)
    private LocalDate expireDate;


    @Column(name = "amount", precision=10, scale=2, nullable = true)
    private BigDecimal amount;

    @Column(name = "status")
    private Integer status;

    @Column(name = "decline_remarks")
    private String declineRemarks;

    @Column(name = "create_date")
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;


    @Column(name = "update_date")
    private LocalDate updateDate;


    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ManyToOne
    @JoinColumn(name = "bank_setup_id")
    private BankSetup bankSetup;

    @ManyToOne
    @JoinColumn(name = "bank_branch_id")
    private BankBranch bankBranch;

    @OneToOne
    @JoinColumn(name = "institute_financial_info_id")
    private InstituteFinancialInfo instituteFinancialInfo;

    public BankSetup getBankSetup() {
        return bankSetup;
    }

    public void setBankSetup(BankSetup bankSetup) {
        this.bankSetup = bankSetup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public accountType getAccountType() {
        return accountType;
    }

    public void setAccountType(accountType accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeclineRemarks() {
        return declineRemarks;
    }

    public void setDeclineRemarks(String declineRemarks) {
        this.declineRemarks = declineRemarks;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public InstituteFinancialInfo getInstituteFinancialInfo() {
        return instituteFinancialInfo;
    }

    public void setInstituteFinancialInfo(InstituteFinancialInfo instituteFinancialInfo) {
        this.instituteFinancialInfo = instituteFinancialInfo;
    }

    public BankBranch getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(BankBranch bankBranch) {
        this.bankBranch = bankBranch;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstFinancialInfoTemp instFinancialInfoTemp = (InstFinancialInfoTemp) o;

        if ( ! Objects.equals(id, instFinancialInfoTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstFinancialInfo{" +
            "id=" + id +
            ", bankName='" + bankName + "'" +
            ", branchName='" + branchName + "'" +
            ", accountType='" + accountType + "'" +
            ", accountNo='" + accountNo + "'" +
            ", issueDate='" + issueDate + "'" +
            ", expireDate='" + expireDate + "'" +
            ", amount='" + amount + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
