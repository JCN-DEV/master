package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstEmplBankInfo.
 */
@Entity
@Table(name = "inst_empl_bank_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instemplbankinfo")
public class InstEmplBankInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_branch", nullable = false)
    private String bankBranch;

    @NotNull
    @Column(name = "bank_account_no", nullable = false)
    private String bankAccountNo;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_date")
    private LocalDate dateCrated;

    @Column(name = "update_date")
    private LocalDate dateModified;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @ManyToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

    @ManyToOne
    @JoinColumn(name = "bank_setup_id")
    private BankSetup bankSetup;

    @ManyToOne
    @JoinColumn(name = "bank_branch_id")
    private BankBranch bankBranc;

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

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getDateCrated() {
        return dateCrated;
    }

    public void setDateCrated(LocalDate dateCrated) {
        this.dateCrated = dateCrated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    public BankBranch getBankBranc() {
        return bankBranc;
    }

    public void setBankBranc(BankBranch bankBranc) {
        this.bankBranc = bankBranc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstEmplBankInfo instEmplBankInfo = (InstEmplBankInfo) o;

        if ( ! Objects.equals(id, instEmplBankInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstEmplBankInfo{" +
            "id=" + id +
            ", bankName='" + bankName + "'" +
            ", bankBranch='" + bankBranch + "'" +
            ", bankAccountNo='" + bankAccountNo + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
