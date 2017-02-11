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
 * A APScaleApplication.
 */
@Entity
@Table(name = "ap_scale_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "apscaleapplication")
public class APScaleApplication implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="ap_scale_application_seq")
    @SequenceGenerator(name="ap_scale_application_seq", sequenceName="ap_scale_application_seq")
    private Long id;

    @Column(name = "created_date")
    private LocalDate date;

    @Size(max = 20)
    @Column(name = "index_no", length = 20)
    private String indexNo;

    //status maintaining by MpoApplicationStatusType.java class
    @Column(name = "status")
    private Integer status;

    @Column(name = "resulation_date")
    private LocalDate resulationDate;

    @Column(name = "agenda_number")
    private Integer agendaNumber;

    @Column(name = "working_break")
    private Boolean workingBreak;

    @Column(name = "working_break_start")
    private LocalDate workingBreakStart;

    @Column(name = "working_break_end")
    private LocalDate workingBreakEnd;

    @Column(name = "disciplinary_action")
    private Boolean disciplinaryAction;

    @Column(name = "dis_action_case_no")
    private String disActionCaseNo;

    @Column(name = "dis_action_file_name")
    private String disActionFileName;

    @Column(name = "dis_action_file")
    private byte[] disActionFile;

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

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getResulationDate() {
        return resulationDate;
    }

    public void setResulationDate(LocalDate resulationDate) {
        this.resulationDate = resulationDate;
    }

    public Integer getAgendaNumber() {
        return agendaNumber;
    }

    public void setAgendaNumber(Integer agendaNumber) {
        this.agendaNumber = agendaNumber;
    }

    public Boolean getWorkingBreak() {
        return workingBreak;
    }

    public void setWorkingBreak(Boolean workingBreak) {
        this.workingBreak = workingBreak;
    }

    public LocalDate getWorkingBreakStart() {
        return workingBreakStart;
    }

    public void setWorkingBreakStart(LocalDate workingBreakStart) {
        this.workingBreakStart = workingBreakStart;
    }

    public LocalDate getWorkingBreakEnd() {
        return workingBreakEnd;
    }

    public void setWorkingBreakEnd(LocalDate workingBreakEnd) {
        this.workingBreakEnd = workingBreakEnd;
    }

    public Boolean getDisciplinaryAction() {
        return disciplinaryAction;
    }

    public void setDisciplinaryAction(Boolean disciplinaryAction) {
        this.disciplinaryAction = disciplinaryAction;
    }

    public String getDisActionCaseNo() {
        return disActionCaseNo;
    }

    public void setDisActionCaseNo(String disActionCaseNo) {
        this.disActionCaseNo = disActionCaseNo;
    }

    public String getDisActionFileName() {
        return disActionFileName;
    }

    public void setDisActionFileName(String disActionFileName) {
        this.disActionFileName = disActionFileName;
    }

    public byte[] getDisActionFile() {
        return disActionFile;
    }

    public void setDisActionFile(byte[] disActionFile) {
        this.disActionFile = disActionFile;
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

        APScaleApplication aPScaleApplication = (APScaleApplication) o;

        if ( ! Objects.equals(id, aPScaleApplication.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "APScaleApplication{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", indexNo='" + indexNo + "'" +
            ", status='" + status + "'" +
            ", resulationDate='" + resulationDate + "'" +
            ", agendaNumber='" + agendaNumber + "'" +
            ", workingBreak='" + workingBreak + "'" +
            ", workingBreakStart='" + workingBreakStart + "'" +
            ", workingBreakEnd='" + workingBreakEnd + "'" +
            ", disciplinaryAction='" + disciplinaryAction + "'" +
            ", disActionCaseNo='" + disActionCaseNo + "'" +
            ", disActionFileName='" + disActionFileName + "'" +
            '}';
    }
}
