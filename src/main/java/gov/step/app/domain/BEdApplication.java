package gov.step.app.domain;

import gov.step.app.web.rest.util.AttachmentUtil;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BEdApplication.
 */
@Entity
@Table(name = "b_ed_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bedapplication")
public class BEdApplication implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="b_ed_application_seq")
    @SequenceGenerator(name="b_ed_application_seq", sequenceName="b_ed_application_seq")
    private Long id;

    @Column(name = "created_date")
    private LocalDate createdDate;

    //status maintaining by MpoApplicationStatusType.java class
    @Column(name = "status")
    private Integer status;

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

    @Column(name = "date_cmt_approved")
    private LocalDate committeeApproveDate;

    @Column(name = "date_deo_approved")
    private LocalDate deoApproveDate;

    @ManyToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

    @PrePersist
    protected void onCreate() {
        this.setCreatedDate(LocalDate.now());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BEdApplication bEdApplication = (BEdApplication) o;

        if ( ! Objects.equals(id, bEdApplication.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BEdApplication{" +
            "id=" + id +
            ", created_date='" + createdDate + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
