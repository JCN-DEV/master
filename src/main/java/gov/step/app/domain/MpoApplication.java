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
 * A MpoApplication.
 */
@Entity
@Table(name = "mpo_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mpoapplication")
public class MpoApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mpo_application_date", nullable = false)
    private LocalDate mpoApplicationDate;

    //status maintaining by MpoApplicationStatusType.java class
    @Column(name = "status")
    private Integer status = 1;

    //application forwarded from additional director
    @Column(name = "ad_forwarded")
    private Boolean adForwarded;

    //this for director's action. 0 = no action, 1 = approved, 2 = rejected
    @Column(name = "dir_action")
    private Integer directorAction = 0;

    @Column(name = "director_comment")
    private String directorComment;

    //this status will be true when finally approved by dg
    @Column(name = "dg_final_approval")
    private Boolean dgFinalApproval = false;

    @Column(name = "dg_representative")
    private Boolean dgRepresentative = Boolean.FALSE;

    @Column(name = "gov_order")
    private Boolean govOrder =Boolean.FALSE;

    @Column(name = "memo_no")
    private String memoNo;

    @Column(name = "date_created")
    private LocalDate dateCrated;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "date_cmt_approved")
    private LocalDate committeeApproveDate;

    @Column(name = "date_deo_approved")
    private LocalDate deoApproveDate;


    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    @OneToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMpoApplicationDate() {
        return mpoApplicationDate;
    }

    public void setMpoApplicationDate(LocalDate mpoApplicationDate) {
        this.mpoApplicationDate = mpoApplicationDate;
    }

    public Boolean getDgRepresentative() {
        return dgRepresentative;
    }

    public void setDgRepresentative(Boolean dgRepresentative) {
        this.dgRepresentative = dgRepresentative;
    }

    public Boolean getGovOrder() {
        return govOrder;
    }

    public void setGovOrder(Boolean govOrder) {
        this.govOrder = govOrder;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getAdForwarded() {
        return adForwarded;
    }

    public void setAdForwarded(Boolean adForwarded) {
        this.adForwarded = adForwarded;
    }

    public Integer getDirectorAction() {
        return directorAction;
    }

    public void setDirectorAction(Integer directorAction) {
        this.directorAction = directorAction;
    }

    public String getDirectorComment() {
        return directorComment;
    }

    public void setDirectorComment(String directorComment) {
        this.directorComment = directorComment;
    }

    public Boolean getDgFinalApproval() {
        return dgFinalApproval;
    }

    public void setDgFinalApproval(Boolean dgFinalApproval) {
        this.dgFinalApproval = dgFinalApproval;
    }

    public String getMemoNo() {
        return memoNo;
    }

    public void setMemoNo(String memoNo) {
        this.memoNo = memoNo;
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

    public LocalDate getCommitteeApproveDate() {
        return committeeApproveDate;
    }

    public void setCommitteeApproveDate(LocalDate committeeApproveDate) {
        this.committeeApproveDate = committeeApproveDate;
    }

    public LocalDate getDeoApproveDate() {
        return deoApproveDate;
    }

    public void setDeoApproveDate(LocalDate deoApproveDate) {
        this.deoApproveDate = deoApproveDate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MpoApplication mpoApplication = (MpoApplication) o;

        if ( ! Objects.equals(id, mpoApplication.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MpoApplication{" +
            "id=" + id +
            ", mpoApplicationDate=" + mpoApplicationDate +
            ", status=" + status +
            ", instEmployee=" + instEmployee +
            '}';
    }
}
