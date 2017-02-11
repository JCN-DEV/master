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
 * A InformationCorrection.
 */
@Entity
@Table(name = "info_correction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "informationcorrection")
public class InformationCorrection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dob")
    private LocalDate dob;

    @Size(max = 200)
    @Column(name = "name", length = 200)
    private String name;

    @Size(max = 30)
    @Column(name = "index_no", length = 30)
    private String indexNo;

    @Column(name = "bank_account_no")
    private String bankAccountNo;

    @Column(name = "ad_forwarded")
    private Boolean adForwarded;

    @Column(name = "dg_final_approval")
    private Boolean dgFinalApproval;

    @Column(name = "create_date")
    private LocalDate createdDate;

    @Column(name = "update_date")
    private LocalDate modifiedDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    @Column(name = "director_comment")
    private String directorComment;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

    @ManyToOne
    @JoinColumn(name = "inst_empl_desg_id")
    private InstEmplDesignation instEmplDesignation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public Boolean getAdForwarded() {
        return adForwarded;
    }

    public void setAdForwarded(Boolean adForwarded) {
        this.adForwarded = adForwarded;
    }

    public Boolean getDgFinalApproval() {
        return dgFinalApproval;
    }

    public void setDgFinalApproval(Boolean dgFinalApproval) {
        this.dgFinalApproval = dgFinalApproval;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getDirectorComment() {
        return directorComment;
    }

    public void setDirectorComment(String directorComment) {
        this.directorComment = directorComment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public InstEmplDesignation getInstEmplDesignation() {
        return instEmplDesignation;
    }

    public void setInstEmplDesignation(InstEmplDesignation instEmplDesignation) {
        this.instEmplDesignation = instEmplDesignation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InformationCorrection informationCorrection = (InformationCorrection) o;

        if ( ! Objects.equals(id, informationCorrection.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InformationCorrection{" +
            "id=" + id +
            ", dob='" + dob + "'" +
            ", name='" + name + "'" +
            ", indexNo='" + indexNo + "'" +
            ", bankAccountNo='" + bankAccountNo + "'" +
            ", adForwarded='" + adForwarded + "'" +
            ", dgFinalApproval='" + dgFinalApproval + "'" +
            ", createdDate='" + createdDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", directorComment='" + directorComment + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
