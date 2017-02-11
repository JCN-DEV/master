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
 * A EmployeeLoanApproveAndForward.
 */
@Entity
@Table(name = "elms_loan_approve_forward")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employeeloanapproveandforward")
public class EmployeeLoanApproveAndForward implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comments")
    private String comments;

    @Column(name = "approve_status")
    private Integer approveStatus;

    @ManyToOne
    @JoinColumn(name = "loanRequisitionForm_id")
    private EmployeeLoanRequisitionForm loanRequisitionForm;


    @Column(name = "approve_by_authority")
    private String approveByAuthority;

    @Column(name = "forward_to_authority")
    private String forwardToAuthority;

    @Column(name="approved_loan_amount")
    private  Long approvedLoanAmount;

    @Column(name="approved_loan_installment")
    private  Integer approvedLoanInstallment;

    @Column(name = "log_comments")
    private String logComments;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
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

    public EmployeeLoanRequisitionForm getLoanRequisitionForm() {
        return loanRequisitionForm;
    }

    public void setLoanRequisitionForm(EmployeeLoanRequisitionForm loanRequisitionForm) {
        this.loanRequisitionForm = loanRequisitionForm;
    }

    public String getForwardToAuthority() {
        return forwardToAuthority;
    }

    public void setForwardToAuthority(String forwardToAuthority) {
        this.forwardToAuthority = forwardToAuthority;
    }

    public String getApproveByAuthority() {
        return approveByAuthority;
    }

    public void setApproveByAuthority(String approveByAuthority) {
        this.approveByAuthority = approveByAuthority;
    }

    public Long getApprovedLoanAmount() {
        return approvedLoanAmount;
    }

    public void setApprovedLoanAmount(Long approvedLoanAmount) {
        this.approvedLoanAmount = approvedLoanAmount;
    }

    public Integer getApprovedLoanInstallment() {
        return approvedLoanInstallment;
    }

    public void setApprovedLoanInstallment(Integer approvedLoanInstallment) {
        this.approvedLoanInstallment = approvedLoanInstallment;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getLogComments() {
        return logComments;
    }

    public void setLogComments(String logComments) {
        this.logComments = logComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeLoanApproveAndForward employeeLoanApproveAndForward = (EmployeeLoanApproveAndForward) o;

        if ( ! Objects.equals(id, employeeLoanApproveAndForward.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmployeeLoanApproveAndForward{" +
            "id=" + id +
            ", comments='" + comments + '\'' +
            ", approveStatus=" + approveStatus +
            ", loanRequisitionForm=" + loanRequisitionForm +
            ", approveByAuthority='" + approveByAuthority + '\'' +
            ", forwardToAuthority='" + forwardToAuthority + '\'' +
            ", approvedLoanAmount=" + approvedLoanAmount +
            ", approvedLoanInstallment=" + approvedLoanInstallment +
            ", logComments='" + logComments + '\'' +
            ", status=" + status +
            ", createDate=" + createDate +
            ", createBy=" + createBy +
            ", updateDate=" + updateDate +
            ", updateBy=" + updateBy +
            '}';
    }
}
